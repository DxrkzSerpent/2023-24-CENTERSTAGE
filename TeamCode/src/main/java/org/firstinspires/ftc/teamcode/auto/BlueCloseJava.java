package org.firstinspires.ftc.teamcode.auto;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.RoadRunner.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.RoadRunner.trajectorysequence.TrajectorySequence;
import org.firstinspires.ftc.teamcode.lib.Deposit;
import org.firstinspires.ftc.teamcode.lib.Intake;
import org.firstinspires.ftc.teamcode.lib.Tilt;
import org.firstinspires.ftc.teamcode.lib.vision.BluePropProcessor;
import org.firstinspires.ftc.teamcode.lib.vision.PropProcessor;
import org.firstinspires.ftc.teamcode.lib.vision.RedPropProcessor;
import org.firstinspires.ftc.vision.VisionPortal;

@Autonomous
public class BlueCloseJava extends LinearOpMode {
    private PropProcessor.Location location = PropProcessor.Location.MIDDLE;
    private BluePropProcessor bluePropProcessor;
    private VisionPortal visionPortal;

    Intake intake;
    Tilt tilt;
    Deposit deposit;
    SampleMecanumDrive drive;

    @Override
    public void runOpMode() {
        tilt = new Tilt(hardwareMap);
        deposit = new Deposit(hardwareMap);
        drive = new SampleMecanumDrive(hardwareMap);
        bluePropProcessor = new BluePropProcessor(telemetry);
        visionPortal = VisionPortal.easyCreateWithDefaults(
                hardwareMap.get(WebcamName.class, "Webcam 1"), bluePropProcessor);
        Pose2d blueClose = new Pose2d(14.75, 61.9, Math.toRadians(-90.0));
        drive.setPoseEstimate(blueClose);

        TrajectorySequence closeRight = drive.trajectorySequenceBuilder(blueClose)
                .lineToSplineHeading(new Pose2d(8.0, 34.0, Math.toRadians(-115.0)))
                .waitSeconds(0.2)
                .back(4.0)
                .addDisplacementMarker(()-> {
                    deposit.getArm1().setPosition(0.3);
                    deposit.getArm2().setPosition(0.3);
                })
                .lineToSplineHeading(new Pose2d(15.0, 36.0, Math.toRadians(0.0)))
                .addDisplacementMarker(()-> {
                    deposit.placingPosition();
                })
                .splineToConstantHeading(new Vector2d(53.5, 23.0), Math.toRadians(330.0))
                .addDisplacementMarker(()-> {
                    deposit.openClaw();
                })
                .forward(0.1)
                .back(3.0)
                .addDisplacementMarker(()-> {
                    deposit.idlePosition();
                })
                .waitSeconds(2.0)
                .strafeLeft(35.0)
                .forward(10.0)
                .build();

        TrajectorySequence closeCenter = drive.trajectorySequenceBuilder(blueClose)
                .lineToSplineHeading(new Pose2d(15.0, 33.0, Math.toRadians(-90.0)))
                .waitSeconds(0.2)
                .addDisplacementMarker(()-> {
                    deposit.placingPosition();
                })
                .lineToSplineHeading(new Pose2d(15.0, 45.0, Math.toRadians(0.0)))
                .lineToConstantHeading(new Vector2d(53.5, 32.5))
                .addDisplacementMarker(() -> {
                    deposit.openClaw();
                })
                .forward(0.1)
                .back(3.0)
                .addDisplacementMarker(()-> {
                    deposit.idlePosition();
                })
                .waitSeconds(2.0)
                .strafeLeft(23.0)
                .forward(15.0)
                .build();

        TrajectorySequence closeLeft = drive.trajectorySequenceBuilder(blueClose)
                .lineToSplineHeading(new Pose2d(26.0, 34.0, Math.toRadians(-90.0)))
                .waitSeconds(0.2)
                .addDisplacementMarker(()-> {
                deposit.placingPosition();
                })
                .lineToSplineHeading(new Pose2d(25.0, 45.0, Math.toRadians(0.0)))
                .lineToConstantHeading(new Vector2d(53.5, 40.0))
                .addDisplacementMarker(()-> {
                    deposit.openClaw();
                })
                .forward(0.1)
                .back(3.0)
                .addDisplacementMarker(()-> {
                    deposit.idlePosition();
                })
                .waitSeconds(2.0)
                .strafeLeft(20.0)
                .forward(10.0)
                .build();

        tilt.getLtilt().setPosition(0.05);
        tilt.getRtilt().setPosition(0.05);

        deposit.getClaw().setPosition(1.0);

        while(!isStarted()){
            location = bluePropProcessor.getLocation();
            telemetry.update();
        }
        telemetry.update();
        waitForStart();
        switch(location){
            case LEFT:
                drive.followTrajectorySequenceAsync(closeLeft);
                break;
            case MIDDLE:
                drive.followTrajectorySequenceAsync(closeCenter);
                break;
            case RIGHT:
                drive.followTrajectorySequenceAsync(closeRight);
                break;
        }

        while(opModeIsActive()){
            drive.update();
        }


    }
}