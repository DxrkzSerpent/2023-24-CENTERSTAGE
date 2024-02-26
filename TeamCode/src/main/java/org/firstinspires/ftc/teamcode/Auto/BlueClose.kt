package org.firstinspires.ftc.teamcode.Auto

import com.acmerobotics.dashboard.FtcDashboard
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry
import com.acmerobotics.roadrunner.geometry.Pose2d
import com.acmerobotics.roadrunner.geometry.Vector2d
import com.qualcomm.robotcore.eventloop.opmode.Autonomous
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import org.firstinspires.ftc.teamcode.RoadRunner.drive.SampleMecanumDrive
import org.firstinspires.ftc.teamcode.lib.DepoSlides
import org.firstinspires.ftc.teamcode.lib.Deposit
import org.firstinspires.ftc.teamcode.lib.Intake
import org.firstinspires.ftc.teamcode.lib.TargetPositionGetter
import org.firstinspires.ftc.teamcode.lib.Tilt


@Autonomous
class BlueClose : LinearOpMode() {
    override fun runOpMode() {
        val telemetryMultiple = MultipleTelemetry(telemetry, FtcDashboard.getInstance().telemetry)
        val drive = SampleMecanumDrive(hardwareMap)
        val intake = Intake(hardwareMap)
        val deposit = Deposit(hardwareMap)
        val depoSlide = DepoSlides(hardwareMap)
        val tilt = Tilt(hardwareMap)
        val cv =  TargetPositionGetter(hardwareMap, TargetPositionGetter.VisionProc.Color.BLUE)
        val blueClose = Pose2d(14.75, 61.9, Math.toRadians(-90.0))
        val closeLeft = drive.trajectorySequenceBuilder(blueClose)
            .lineToSplineHeading(
                Pose2d(
                    8.0,
                    34.0,
                    Math.toRadians(-115.0)
                )
            )
            .waitSeconds(0.2)
            .lineToSplineHeading(
                Pose2d(
                    15.0,
                    50.0,
                    Math.toRadians(0.0)
                )
            )
            .splineToConstantHeading(
                Vector2d(45.0, 42.0),
                Math.toRadians(330.0)
            )
            .waitSeconds(1.0)
            .strafeLeft(18.0)
            .forward(10.0)
            .build()
        val closeCenter = drive.trajectorySequenceBuilder(blueClose)
            .lineToSplineHeading(Pose2d(15.0, 33.0, Math.toRadians(-90.0)))
            .addDisplacementMarker {
                tilt.tiltTransfer()
                deposit.transferPosition()
                depoSlide.transferPos()
            }
            .waitSeconds(0.2)
            .addDisplacementMarker {
                deposit.closeClaw()
            }
            .lineToSplineHeading(Pose2d(15.0, 45.0, Math.toRadians(0.0)))
            .addDisplacementMarker {
                deposit.placingPosition()
                depoSlide.tape1()
            }
            .lineToConstantHeading(Vector2d(45.0, 35.0),)
            .addDisplacementMarker {
                deposit.idlePosition()
                depoSlide.reset()
            }
            .waitSeconds(5.0)
            .strafeLeft(20.0)
            .forward(15.0)
            .build()
        val closeRight = drive.trajectorySequenceBuilder(blueClose)
            .lineToSplineHeading(
                Pose2d(
                    26.0,
                    34.0,
                    Math.toRadians(-90.0)
                )
            )
            .waitSeconds(0.2)
            .lineToSplineHeading(
                Pose2d(
                    25.0,
                    45.0,
                    Math.toRadians(0.0)
                )
            )
            .lineToConstantHeading(
                Vector2d(45.0, 30.0),
            )
            .waitSeconds(1.0)
            .strafeLeft(30.0)
            .forward(10.0)
            .build()


        drive.poseEstimate = blueClose
        cv.doDetect(telemetryMultiple)
        telemetry.update()

        waitForStart()
        if (cv.lcr() == TargetPositionGetter.LCR.Center)
            drive.followTrajectorySequenceAsync(closeCenter)
        else if (cv.lcr() == TargetPositionGetter.LCR.Right)
            drive.followTrajectorySequenceAsync(closeRight)
        else if (cv.lcr() == TargetPositionGetter.LCR.Left)
            drive.followTrajectorySequenceAsync(closeLeft)

        if (isStopRequested) return

        while (opModeIsActive()) {
            drive.update()
            depoSlide.update()
        }
    }
}
