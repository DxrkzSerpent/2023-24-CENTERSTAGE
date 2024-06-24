package org.firstinspires.ftc.teamcode.auto

import com.acmerobotics.dashboard.FtcDashboard
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry
import com.acmerobotics.roadrunner.geometry.Pose2d
import com.acmerobotics.roadrunner.geometry.Vector2d
import com.qualcomm.robotcore.eventloop.opmode.Autonomous
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import org.firstinspires.ftc.teamcode.RoadRunner.drive.SampleMecanumDrive
import org.firstinspires.ftc.teamcode.lib.DepoSlides
import org.firstinspires.ftc.teamcode.lib.Deposit
import org.firstinspires.ftc.teamcode.lib.Tilt
import org.firstinspires.ftc.teamcode.lib.vision.BluePropProcessor
import org.firstinspires.ftc.teamcode.lib.vision.PropProcessor.Location


@Autonomous
class BlueClose : LinearOpMode() {
    private var location: Location = Location.MIDDLE
    private val bluePropProcessor: BluePropProcessor = BluePropProcessor(telemetry)
    val telemetryMultiple = MultipleTelemetry(telemetry, FtcDashboard.getInstance().telemetry)

    override fun runOpMode() {
        val drive = SampleMecanumDrive(hardwareMap)
        val deposit = Deposit(hardwareMap)
        val depoSlide = DepoSlides(hardwareMap)
        val tilt = Tilt(hardwareMap)
        val blueClose = Pose2d(14.75, 61.9, Math.toRadians(-90.0))
        val closeRight = drive.trajectorySequenceBuilder(blueClose)
            .lineToSplineHeading(
                Pose2d(8.0, 34.0, Math.toRadians(-115.0)))
            .waitSeconds(0.2)
            .back(4.0)
            .addDisplacementMarker {
                deposit.arm1.position = 0.3
                deposit.arm2.position = 0.3
            }
            .lineToSplineHeading(Pose2d(15.0, 36.0, Math.toRadians(0.0)))
            .addDisplacementMarker {
                deposit.placingPosition()
            }
            .splineToConstantHeading(Vector2d(50.0, 23.0), Math.toRadians(330.0))
            .addDisplacementMarker {
                deposit.openClaw()
            }
            .forward(0.1)
            .back(3.0)
            .addDisplacementMarker {
                deposit.idlePosition()
            }
            .waitSeconds(2.0)
            .strafeLeft(35.0)
            .forward(10.0)
            .build()
        val closeCenter = drive.trajectorySequenceBuilder(blueClose)
            .lineToSplineHeading(Pose2d(15.0, 33.0, Math.toRadians(-90.0)))
            .waitSeconds(0.2)
            .addDisplacementMarker {
                deposit.placingPosition()
            }
            .lineToSplineHeading(Pose2d(15.0, 45.0, Math.toRadians(0.0)))
            .lineToConstantHeading(Vector2d(50.0, 32.5))
            .addDisplacementMarker {
                deposit.openClaw()
            }
            .forward(0.1)
            .back(3.0)
            .addDisplacementMarker {
                deposit.idlePosition()
            }
            .waitSeconds(2.0)
            .strafeLeft(23.0)
            .forward(15.0)
            .build()
        val closeLeft = drive.trajectorySequenceBuilder(blueClose)
            .lineToSplineHeading(Pose2d(26.0, 34.0, Math.toRadians(-90.0)))
            .waitSeconds(0.2)
            .addDisplacementMarker {
                deposit.placingPosition()
            }
            .lineToSplineHeading(Pose2d(25.0, 45.0, Math.toRadians(0.0)))
            .lineToConstantHeading(Vector2d(50.0, 40.0))
            .addDisplacementMarker {
                deposit.openClaw()
            }
            .forward(0.1)
            .back(3.0)
            .addDisplacementMarker {
                deposit.idlePosition()
            }
            .waitSeconds(2.0)
            .strafeLeft(20.0)
            .forward(10.0)
            .build()


        tilt.tiltTransfer()
        deposit.closeClaw()
        drive.poseEstimate = blueClose

        while(!isStarted){
            location = bluePropProcessor.location
            telemetry.update()
        }

        waitForStart()
        when (location) {
            Location.MIDDLE -> drive.followTrajectorySequenceAsync(closeCenter)
            Location.RIGHT -> drive.followTrajectorySequenceAsync(closeRight)
            Location.LEFT -> drive.followTrajectorySequenceAsync(closeLeft)
        }

        while (opModeIsActive()) {
            drive.update()
            depoSlide.update()
        }
    }
}
