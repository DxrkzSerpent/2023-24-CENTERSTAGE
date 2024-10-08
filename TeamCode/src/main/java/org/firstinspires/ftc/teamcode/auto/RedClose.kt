package org.firstinspires.ftc.teamcode.auto

import com.acmerobotics.dashboard.FtcDashboard
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry
import com.acmerobotics.roadrunner.geometry.Pose2d
import com.acmerobotics.roadrunner.geometry.Vector2d
import com.qualcomm.robotcore.eventloop.opmode.Autonomous
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName
import org.firstinspires.ftc.teamcode.RoadRunner.drive.SampleMecanumDrive
import org.firstinspires.ftc.teamcode.lib.DepoSlides
import org.firstinspires.ftc.teamcode.lib.Deposit
import org.firstinspires.ftc.teamcode.lib.Intake
import org.firstinspires.ftc.teamcode.lib.TargetPositionGetter
import org.firstinspires.ftc.teamcode.lib.Tilt
import org.firstinspires.ftc.teamcode.lib.vision.PropProcessor
import org.firstinspires.ftc.teamcode.lib.vision.RedPropProcessor
import org.firstinspires.ftc.teamcode.lib.vision.Vision
import org.firstinspires.ftc.vision.VisionPortal

@Autonomous
class RedClose: LinearOpMode() {
    var location: PropProcessor.Location = PropProcessor.Location.MIDDLE
    var redPropProcessor: RedPropProcessor = RedPropProcessor(telemetry)

    val telemetryMultiple = MultipleTelemetry(telemetry, FtcDashboard.getInstance().telemetry)

    override fun runOpMode() {
        val telemetryMultiple = MultipleTelemetry(telemetry, FtcDashboard.getInstance().telemetry)
        val drive = SampleMecanumDrive(hardwareMap)
        val intake = Intake(hardwareMap)
        val deposit = Deposit(hardwareMap)
        val depoSlide = DepoSlides(hardwareMap)
        val tilt = Tilt(hardwareMap)
        val redClose = Pose2d(11.5, -60.0, Math.toRadians(90.0))
        val closeLeft = drive.trajectorySequenceBuilder(redClose)
            .forward(3.0)
            .lineToSplineHeading(Pose2d(14.0, -34.0, Math.toRadians(180.0)))
            .forward(0.5)
            .waitSeconds(0.2)
            .back(2.0)
            .lineToSplineHeading(Pose2d(25.0, -32.0, Math.toRadians(-10.0)))
            .addDisplacementMarker {
                deposit.placingPosition()
            }
            .lineToConstantHeading(Vector2d(53.5, -32.5))
            .addDisplacementMarker {
                deposit.openClaw()
            }
            .forward(0.1)
            .back(5.0)
            .waitSeconds(1.0)
            .addDisplacementMarker {
                deposit.idlePosition()
            }
            .strafeRight(34.0)
            .forward(10.0)
            .build()
        val closeCenter = drive.trajectorySequenceBuilder(redClose)
            .lineToSplineHeading(Pose2d(14.5, -31.0, Math.toRadians(90.0)))
            .waitSeconds(0.2)
            .addDisplacementMarker {
                deposit.placingPosition()
            }
            .lineToSplineHeading(Pose2d(25.0, -46.0, Math.toRadians(10.0)))
            .lineToConstantHeading(Vector2d(49.5, -32.2))
            .addDisplacementMarker {
                deposit.openClaw()
            }
            .forward(0.1)
            .back(5.0)
            .waitSeconds(1.0)
            .strafeRight(26.0)
            .addDisplacementMarker {
                deposit.idlePosition()
            }

            .forward(10.0)
            .build()
        val closeRight = drive.trajectorySequenceBuilder(redClose)
            .lineToSplineHeading(Pose2d(21.5, -34.0, Math.toRadians(90.0)))
            .back(3.0)
            .addDisplacementMarker {
                deposit.placingPosition()
            }
            .waitSeconds(0.2)
            .lineToLinearHeading(Pose2d(25.0, -45.0, Math.toRadians(-15.0)))
            .lineToConstantHeading(Vector2d(50.0, -42.0))
            .addDisplacementMarker {
                deposit.openClaw()
            }
            .forward(0.1)
            .back(6.0)
            .waitSeconds(1.0)
            .strafeRight(18.0)
            .addDisplacementMarker {
                deposit.idlePosition()
            }
            .forward(10.0)
            .build()

        tilt.tiltTransfer()
        deposit.closeClaw()
        drive.poseEstimate = redClose

        while(!isStarted()) {
            location = redPropProcessor.getLocation();
            telemetry.update();
        }


        waitForStart()
        when (location) {
            PropProcessor.Location.MIDDLE -> drive.followTrajectorySequence(closeCenter)
            PropProcessor.Location.RIGHT -> drive.followTrajectorySequence(closeRight)
            PropProcessor.Location.LEFT -> drive.followTrajectorySequence(closeLeft)
        }

        while (opModeIsActive()) {
            drive.update()
        }

    }
}
