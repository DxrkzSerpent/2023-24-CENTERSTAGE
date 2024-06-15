
package org.firstinspires.ftc.teamcode.lib.vision;

import android.graphics.Canvas;

import com.acmerobotics.dashboard.config.Config;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.internal.camera.calibration.CameraCalibration;
import org.firstinspires.ftc.teamcode.lib.vision.PropProcessor;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

import java.util.ArrayList;
import java.util.List;

@Config
public class RedPropProcessor extends PropProcessor {

    public static double lowY = 0.09;
    public static double lowCr = 0.1;
    public static double lowCb = -0.04;
    public static double highY = 0.45;
    public static double highCr = 0.09;
    public static double highCb = -0.04;
    public Scalar lower = new Scalar(lowY,lowCr,lowCb);
    public Scalar upper = new Scalar(highY,highCr,highCb);

    public RedPropProcessor(Telemetry telemetry) {
        this.telemetry = telemetry;
    }

    @Override
    public void init(int width, int height, CameraCalibration calibration) {
    }

    @Override
    public Object processFrame(Mat frame, long captureTimeNanos) {

        Scalar lower = new Scalar(lowY,lowCr,lowCb);
        Scalar upper = new Scalar(highY,highCr,highCb);


        Imgproc.cvtColor(frame, ycrcbMat, colorSpace.cvtCode);

        Core.inRange(ycrcbMat, lower, upper, binaryMat);

        maskedInputMat.release();

        Core.bitwise_and(frame, frame, maskedInputMat, binaryMat);


        //use binary mat from here
        List<MatOfPoint> countersList = new ArrayList<>();
        Imgproc.findContours(binaryMat, countersList, new Mat(), Imgproc.RETR_LIST, Imgproc.CHAIN_APPROX_SIMPLE);
        Imgproc.drawContours(binaryMat, countersList,0, new Scalar(255,0,0));

        Rect hat = new Rect(new Point(0,0), new Point(1,1));

        for (MatOfPoint countor : countersList)
        {

            Rect rect = Imgproc.boundingRect(countor);

            int centerY = rect.y + rect.height;
            if (rect.area() > hat.area() && centerY >= 480/2 ) {
                hat = rect;
            }

        }

        Imgproc.rectangle(maskedInputMat, hat, new Scalar(255,255,255));

        int centerX = hat.x + hat.width;
        int area = (int) hat.area();
        telemetry.addData("Area: ", area);
        telemetry.addData("CenterX: ", centerX);
        if(centerX <= 375 && area >= 200 ){ //bottom half
            location = Location.MIDDLE;
            telemetry.addData("Position:", " MIDDLE");
        }else if(centerX >= 375 && centerX <= 1200  && area >= 200){
            location = Location.RIGHT;
            telemetry.addData("Position:", " RIGHT");
        }else{
            location = Location.LEFT;
            telemetry.addData("Position:", " LEFT");
        }
        telemetry.update();

        maskedInputMat.copyTo(frame);
        return null;
    }

    @Override
    public void onDrawFrame(Canvas canvas, int onscreenWidth, int onscreenHeight, float scaleBmpPxToCanvasPx, float scaleCanvasDensity, Object userContext) {
    }

}
