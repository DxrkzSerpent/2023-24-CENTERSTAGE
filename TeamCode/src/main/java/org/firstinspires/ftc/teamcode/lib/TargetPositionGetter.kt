package org.firstinspires.ftc.teamcode.lib

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.Size
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry
import com.qualcomm.robotcore.hardware.Gamepad
import com.qualcomm.robotcore.hardware.HardwareMap
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName
import org.firstinspires.ftc.robotcore.internal.camera.calibration.CameraCalibration
import org.firstinspires.ftc.vision.VisionPortal
import org.firstinspires.ftc.vision.VisionProcessor
import org.opencv.core.Core
import org.opencv.core.CvType
import org.opencv.core.Mat
import org.opencv.core.MatOfPoint
import org.opencv.core.Rect
import org.opencv.core.Scalar
import org.opencv.imgproc.Imgproc
import java.util.LinkedList


class TargetPositionGetter(hardwareMap: HardwareMap) {
    class VisionProc : VisionProcessor {
        override fun init(width: Int, height: Int, calibration: CameraCalibration?) {
        }
        enum class Color {
            RED, BLUE, YELLOW, GREEN
        }

        var size = -1
        var color = Color.YELLOW
        var tele: MultipleTelemetry? = null
        var hierarchyOutput = Mat()
        //var hsvMat = Mat()
        var thresholdMat = Mat()
        var currentDetection : Rect? = null
        var contours: List<MatOfPoint> = LinkedList()
        var sensitivity = 10.0
        override fun processFrame(frame: Mat?, captureTimeNanos: Long): Rect? {
            try {
                contours = LinkedList()

                thresholdMat = Mat.zeros(frame!!.size(), CvType.CV_8U)
                var out = Mat()
                var hsvMat = Mat()
                Imgproc.cvtColor(frame, hsvMat, Imgproc.COLOR_RGB2HSV)
                if (color == Color.BLUE) {
                    // Detect blue color
                    Core.inRange(
                        hsvMat,
                        Scalar(100.0, 100.0, 100.0),
                        Scalar(140.0, 255.0, 255.0),
                        out
                    )
                } else if (Color.RED == color) {
                    // Detect red color
                    var lowRed = Mat()
                    var highRed = Mat()
                    Core.inRange(
                        hsvMat,
                        Scalar(0.0, 100.0, 100.0),
                        Scalar(10.0, 255.0, 255.0),
                        lowRed
                    )
                    Core.inRange(
                        hsvMat,
                        Scalar(160.0, 100.0, 100.0),
                        Scalar(180.0, 255.0, 255.0),
                        highRed
                    )
                    Core.add(lowRed, highRed, out)
                } else if (Color.YELLOW == color) {
                    // Detect yellow color
                    Core.inRange(
                        hsvMat,
                        Scalar(20.0, 100.0, 100.0),
                        Scalar(30.0, 255.0, 255.0),
                        out
                    )
                } else if (Color.GREEN == color) {
                    // Detect green color
                    Core.inRange(
                        hsvMat,
                        Scalar(40.0, 100.0, 100.0),
                        Scalar(80.0, 255.0, 255.0),
                        out
                    )
                } else if (color == Color.GREEN) {
                    // Detect green color
                    Core.inRange(
                        hsvMat,
                        Scalar(40.0, 100.0, 100.0),
                        Scalar(80.0, 255.0, 255.0),
                        out
                    )
                }

                Imgproc.findContours(
                    out,
                    contours,
                    hierarchyOutput,
                    Imgproc.RETR_TREE,
                    Imgproc.CHAIN_APPROX_SIMPLE
                )

                val boundRects = arrayOfNulls<Rect>(contours.size)
                contours.forEachIndexed { i, contour ->
                    boundRects[i] = Imgproc.boundingRect(contour)
                }

                Imgproc.drawContours(frame, contours, -1, Scalar(255.0, 0.0, 0.0), 2)

                var largestCont: MatOfPoint? = null
                var largestContArea = 0

                for (cont in contours) {
                    val area = cont.toList().size
                    if (area > largestContArea) {
                        largestCont = cont
                        largestContArea = area
                    }
                }
                if (largestCont != null) {
                    var x1 = Double.MAX_VALUE//find lowest
                    var y1 = Double.MAX_VALUE
                    var x2 = Double.MIN_VALUE//find highest
                    var y2 = Double.MIN_VALUE
                    for (point in largestCont.toList()) {
                        if (point.x < x1) {
                            x1 = point.x
                        }
                        if (point.y < y1) {
                            x2 = point.x
                        }
                        if (point.x > x2) {
                            x2 = point.x
                        }
                        if (point.y > y2) {
                            y2 = point.y
                        }
                    }
                    currentDetection = Rect(x1.toInt(), y1.toInt(), (x2 - x1).toInt(), (y2 - y1).toInt())
                    size = largestContArea
                }
                /*if (tele != null) {
                    tele!!.addData("Time", System.currentTimeMillis())
                    tele!!.addData(
                        "100,100rgb=",
                        "h:" + hsvMat.get(100, 100)[0] + "s:" + hsvMat.get(
                            100,
                            100
                        )[1] + "thing:" + hsvMat.get(100, 100)[2]
                    )
                    tele!!.addData("Contours", contours.size)
                    tele!!.addData("Largest Contour", largestContArea)
                    tele!!.addData("100,100thresh=", out.get(100, 100)[0])
                    tele!!.addData("sens", sensitivity)
                    sensitivity = (sensitivity + 0.1) % 30
                }*/
                return currentDetection
            } catch (e: Exception) {
                e.printStackTrace()
                throw java.lang.Exception(e.toString() + "||" + e.stackTraceToString())
            }
        }

        override fun onDrawFrame(
            canvas: Canvas?,
            onscreenWidth: Int,
            onscreenHeight: Int,
            scaleBmpPxToCanvasPx: Float,
            scaleCanvasDensity: Float,
            userContext: Any?
        ) {
            val p = Paint()
            p.color = android.graphics.Color.MAGENTA
            val l = currentDetection
            if (l != null && canvas != null) {
                canvas.drawRoundRect(l.x.toFloat(), l.y.toFloat(), l.x.toFloat() + l.width.toFloat(), l.y.toFloat() + l.height.toFloat(), 1.0F, 1.0F, p)
            }
        }

    }
    val proc = VisionProc()
    val visionPortal = VisionPortal.Builder()
        .setCamera(hardwareMap.get(WebcamName::class.java, "Webcam 1"))
        .addProcessor(proc)
        .setCameraResolution(Size(640, 480))
        .setStreamFormat(VisionPortal.StreamFormat.MJPEG)
        .enableLiveView(true)
        //.setCamera
        .setAutoStopLiveView(true)
        .build();
    init {
        visionPortal.setProcessorEnabled(proc, true)
        //fully enable vision portal on Driver Hub
    }
    fun alignerLoop(gamepad: Gamepad, mecanum: ControllableMecanum) {
        if (gamepad.dpad_left) {
            proc.color = VisionProc.Color.RED
        } else if (gamepad.dpad_right) {
            proc.color = VisionProc.Color.BLUE
        } else if (gamepad.dpad_up) {
            proc.color = VisionProc.Color.YELLOW
        } else if (gamepad.dpad_down) {
            proc.color = VisionProc.Color.GREEN
        }
        /*Thread.sleep(50)
        if (currentDetection!!.isEmpty()) {
            mecanum.turn = 0.0F
            return
        }
        val cd = currentDetections!![0]

        if (cd.ftcPose.roll > 0.5) {
            mecanum.turn = 0.1F
        } else if (cd.ftcPose.roll < -0.5){
            mecanum.turn = -0.1F
        }
        if (cd.ftcPose.bearing > 0.5) {
            mecanum.x = 0.1F
        } else if (cd.ftcPose.bearing < -0.5){
            mecanum.x = -0.1F
        }*/
    }
    fun telemetry(telemetry: MultipleTelemetry, doing: Boolean) {
        proc.tele = telemetry
        telemetry.addData("RectSize", proc.size)
        if (proc.currentDetection != null && proc.size > 150) {
            telemetry.addData("Rect", proc.currentDetection!!.toString())
            val cd = proc.currentDetection!!
            val x = cd.x + cd.width/2
            val width = 640
            if (x < width/2) {
                telemetry.addData("Direction", "Left")
            } else if (x > width/2) {
                telemetry.addData("Direction", "Center")
            } else {
                telemetry.addData("Direction", "???")
            }
        } else {
            telemetry.addData("Direction", "RIGHT")
        }
        telemetry.addData("Rect", proc.currentDetection)

        telemetry.update()
    }
}