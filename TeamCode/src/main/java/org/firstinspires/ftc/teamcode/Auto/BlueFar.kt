package org.firstinspires.ftc.teamcode.Auto

import com.acmerobotics.dashboard.FtcDashboard
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry
import com.acmerobotics.roadrunner.geometry.Pose2d
import com.acmerobotics.roadrunner.geometry.Vector2d
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import org.firstinspires.ftc.teamcode.RoadRunner.drive.SampleMecanumDrive
import org.firstinspires.ftc.teamcode.lib.DepoSlides
import org.firstinspires.ftc.teamcode.lib.Deposit
import org.firstinspires.ftc.teamcode.lib.Intake
import org.firstinspires.ftc.teamcode.lib.TargetPositionGetter
import org.firstinspires.ftc.teamcode.lib.Tilt

class BlueFar: LinearOpMode() {
    override fun runOpMode() {
        val telemetryMultiple = MultipleTelemetry(telemetry, FtcDashboard.getInstance().telemetry)
        val drive = SampleMecanumDrive(hardwareMap)
        val intake = Intake(hardwareMap)
        val deposit = Deposit(hardwareMap)
        val depoSlide = DepoSlides(hardwareMap)
        val tilt = Tilt(hardwareMap)
        val cv = TargetPositionGetter(hardwareMap, TargetPositionGetter.VisionProc.Color.BLUE)
        val blueFar = Pose2d(39.0, 60.0, Math.toRadians(-90.0))
        val farLeft = drive.trajectorySequenceBuilder(blueFar)
            .lineToSplineHeading(Pose2d(-35.9, 25.0, Math.toRadians(-180.0)))
            .back(5.0)
            .lineToLinearHeading(Pose2d(-33.0, 40.0, Math.toRadians(0.0)))
            .lineToLinearHeading(Pose2d(-58.0, 35.0, Math.toRadians(0.0)))
            .waitSeconds(1.0)
            .lineToSplineHeading(Pose2d(-35.0, 58.5, Math.toRadians(0.0)))
            .forward(50.0)
            .waitSeconds(8.0)
            .splineToConstantHeading(Vector2d(45.0, 42.0), Math.toRadians(330.0))
            .waitSeconds(1.0)
            .strafeLeft(18.0)
            .forward(10.0)
            .build()
        val farCenter = drive.trajectorySequenceBuilder(blueFar)
            .lineToSplineHeading(Pose2d(-34.9, 32.0, Math.toRadians(-90.0)))
            .back(5.0)
            .lineToLinearHeading(Pose2d(-55.0, 35.0, Math.toRadians(0.0)))
            .back(5.0)
            .waitSeconds(1.0)
            .lineToSplineHeading(Pose2d(-35.0, 58.5, Math.toRadians(0.0)))
            .forward(50.0)
            .waitSeconds(8.0)
            .splineToConstantHeading(Vector2d(45.0, 35.0), Math.toRadians(0.0))
            .waitSeconds(1.0)
            .strafeLeft(25.0)
            .forward(10.0)
            .build()
        val farRight = drive.trajectorySequenceBuilder(blueFar)
            .lineToSplineHeading(Pose2d(-34.9, 32.0, Math.toRadians(5.0)))
            .lineToLinearHeading(Pose2d(-58.0, 35.0, Math.toRadians(0.0)))
            .waitSeconds(1.0)
            .lineToSplineHeading(Pose2d(-35.0, 58.5, Math.toRadians(0.0)))
            .forward(50.0)
            .waitSeconds(8.0)
            .splineToConstantHeading(Vector2d(45.0, 30.0), Math.toRadians(330.0))
            .waitSeconds(1.0)
            .strafeLeft(30.0)
            .forward(10.0)
            .build()

        drive.poseEstimate = blueFar
        cv.doDetect(telemetryMultiple)
        telemetry.update()

        waitForStart()
        if (cv.lcr() == TargetPositionGetter.LCR.Center)
            drive.followTrajectorySequenceAsync(farCenter)
        else if (cv.lcr() == TargetPositionGetter.LCR.Right)
            drive.followTrajectorySequenceAsync(farRight)
        else if (cv.lcr() == TargetPositionGetter.LCR.Left)
            drive.followTrajectorySequenceAsync(farLeft)

        if (isStopRequested) return

        while (opModeIsActive()) {
            drive.update()
            depoSlide.update()
        }
    }
}
