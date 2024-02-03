package org.firstinspires.ftc.teamcode.RoadRunner.Tuning;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry
import com.acmerobotics.roadrunner.PoseVelocity2d
import com.acmerobotics.roadrunner.TimeProfile
import com.acmerobotics.roadrunner.Vector2d
import com.acmerobotics.roadrunner.constantProfile
import com.acmerobotics.roadrunner.ftc.DriveViewFactory;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode

class ManualFeedforwardTuner(val dvf: DriveViewFactory) : LinearOpMode() {
    companion object {
        @JvmField
        var DISTANCE = 4.0
    }

    enum class Mode {
        DRIVER_MODE,
        TUNING_MODE
    }

    override fun runOpMode() {
        val telemetry = MultipleTelemetry(this.telemetry, FtcDashboard.getInstance().telemetry)

        val view = dvf.make(hardwareMap)
        val profile = TimeProfile(constantProfile(
                DISTANCE, 0.0, view.maxVel, view.minAccel, view.maxAccel).baseProfile)

        var mode = Mode.TUNING_MODE

        telemetry.addLine("Ready!")
        telemetry.update()
        telemetry.clearAll()

        waitForStart()

        if (isStopRequested) return

        var movingForwards = true
        var startTs = System.nanoTime() / 1e9

        while (!isStopRequested) {
            telemetry.addData("mode", mode)

            when (mode) {
                Mode.TUNING_MODE -> {
                    if (gamepad1.y) {
                        mode = Mode.DRIVER_MODE
                    }

                    for (i in view.forwardEncsWrapped.indices) {
                        val v = view.forwardEncsWrapped[i].getPositionAndVelocity().velocity
                        telemetry.addData("v$i", view.inPerTick * v)
                    }

                    val ts = System.nanoTime() / 1e9
                    val t = ts - startTs
                    if (t > profile.duration) {
                        movingForwards = !movingForwards
                        startTs = ts
                    }

                    var v = profile[t].drop(1)
                    if (!movingForwards) {
                        v = v.unaryMinus()
                    }
                    telemetry.addData("vref", v[0])

                    val power = view.feedforward.compute(v) / view.voltageSensor.voltage
                    view.setDrivePowers(PoseVelocity2d(Vector2d(power, 0.0), 0.0))
                }
                Mode.DRIVER_MODE -> {
                    if (gamepad1.b) {
                        view.setDrivePowers(PoseVelocity2d(//Stop
                                Vector2d(0.0,0.0),0.0
                        ))
                        mode = Mode.TUNING_MODE
                        movingForwards = true
                        startTs = System.nanoTime() / 1e9
                    }

                    view.setDrivePowers(PoseVelocity2d(
                            Vector2d(
                                    -gamepad1.left_stick_y.toDouble(),
                                    -gamepad1.left_stick_x.toDouble()
                            ),
                            -gamepad1.right_stick_x.toDouble()
                    ))
                }
            }

            telemetry.update()
        }
    }
}