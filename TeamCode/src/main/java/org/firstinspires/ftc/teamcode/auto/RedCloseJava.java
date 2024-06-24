package org.firstinspires.ftc.teamcode.auto;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.acmerobotics.roadrunner.geometry.Vector2d;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.RoadRunner.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.RoadRunner.trajectorysequence.TrajectorySequence;
import org.firstinspires.ftc.teamcode.lib.Deposit;
import org.firstinspires.ftc.teamcode.lib.Intake;
import org.firstinspires.ftc.teamcode.lib.Tilt;
import org.firstinspires.ftc.teamcode.lib.vision.PropProcessor;
import org.firstinspires.ftc.teamcode.lib.vision.RedPropProcessor;
import org.firstinspires.ftc.vision.VisionPortal;

@Autonomous
public class RedCloseJava extends LinearOpMode {
    private PropProcessor.Location location = PropProcessor.Location.MIDDLE;
    private RedPropProcessor redPropProcessor;
    private VisionPortal visionPortal;

    Intake intake;
    Tilt tilt;
    Deposit deposit;
    SampleMecanumDrive drive;
    
    @Override
    public void runOpMode(){
        tilt = new Tilt(hardwareMap);
        deposit = new Deposit(hardwareMap);
        drive = new SampleMecanumDrive(hardwareMap);
        redPropProcessor = new RedPropProcessor(telemetry);
        visionPortal = VisionPortal.easyCreateWithDefaults(
                hardwareMap.get(WebcamName.class, "Webcam 1"), redPropProcessor);
       Pose2d redclose = new Pose2d(11.5, -60.0, Math.toRadians(90.0));
       drive.setPoseEstimate(redclose);

       TrajectorySequence closeLeft = drive.trajectorySequenceBuilder(redclose)
               .forward(3.0)
               .lineToSplineHeading(new Pose2d(14.0, -34.0, Math.toRadians(180.0)))
               .forward(0.5)
               .waitSeconds(0.2)
               .back(2.0)
               .lineToSplineHeading(new Pose2d(25.0, -33, Math.toRadians(0.00)))
               .addDisplacementMarker(()-> {
                    deposit.placingPosition();
               })
               .lineToConstantHeading(new Vector2d(53.5, -33.0))
               .addDisplacementMarker(() -> {
                   deposit.openClaw();
               })
               .forward(0.1)
               .back(5.0)
               .waitSeconds(1.0)
               .addDisplacementMarker(()-> {
                   deposit.idlePosition();
               })
               .strafeRight(34.0)
               .forward(10.0)
               .build();

        TrajectorySequence closeCenter = drive.trajectorySequenceBuilder(redclose)
                .lineToSplineHeading(new Pose2d(14.5, -31.0, Math.toRadians(90.0)))
                .waitSeconds(0.2)
                .lineToSplineHeading(new Pose2d(25.0, -46.0, Math.toRadians(0.0)))
                .lineToConstantHeading(new Vector2d(53.5, -29.0))
                .addDisplacementMarker(()-> {
                    deposit.openClaw();
                })
                .forward(0.1)
                .back(5.0)
                .waitSeconds(1.0)
                .strafeRight(26.0)
                .addDisplacementMarker(()-> {
                    deposit.idlePosition();
                })
                .forward(10.0)
                .build();

        TrajectorySequence closeRight = drive.trajectorySequenceBuilder(redclose)
                .lineToSplineHeading(new Pose2d(21.5, -34.0, Math.toRadians(90.0)))
                .back(3.0)
                .addDisplacementMarker(()-> {
                    deposit.placingPosition();
                })
                .waitSeconds(0.2)
                .lineToLinearHeading(new Pose2d(25.0, -45.0, Math.toRadians(0.0)))
                .lineToConstantHeading(new Vector2d(53.5, -42.0))
                .addDisplacementMarker(()-> {
                    deposit.openClaw();
                })
                .forward(0.1)
                .back(6.0)
                .waitSeconds(1.0)
                .strafeRight(18.0)
                .addDisplacementMarker(()-> {
                    deposit.idlePosition();
                })
                .forward(10.0)
                .build();

        tilt.getLtilt().setPosition(0.05);
        tilt.getRtilt().setPosition(0.05);

        deposit.getClaw().setPosition(1.0);

        while(!isStarted()){
            location = redPropProcessor.getLocation();
            telemetry.update();
        }
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
