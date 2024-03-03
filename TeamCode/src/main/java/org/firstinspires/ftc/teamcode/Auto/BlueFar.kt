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
        val redClose = Pose2d(11.5, -60.0, Math.toRadians(90.0))
        val blueClose = Pose2d(14.75, 61.9, Math.toRadians(-90.0))
        val closeRight = drive.trajectorySequenceBuilder(blueClose)
            .lineToSplineHeading(Pose2d(8.0, 34.0, Math.toRadians(-115.0)))
            .back(5.0)
            .build()
        val closeCenter = drive.trajectorySequenceBuilder(blueClose)
            .lineToSplineHeading(Pose2d(15.0, 33.0, Math.toRadians(-90.0)))
            .waitSeconds(0.2)
            .build()
        val closeLeft = drive.trajectorySequenceBuilder(blueClose)
            .lineToSplineHeading(Pose2d(26.0, 34.0, Math.toRadians(-90.0)))
            .waitSeconds(0.2)
            .build()

        drive.poseEstimate = blueClose
        val cv =  TargetPositionGetter(hardwareMap, TargetPositionGetter.VisionProc.Color.BLUE)

        waitForStart()
        cv.doDetect(telemetryMultiple)
        telemetry.update()
        if (cv.lcr() == TargetPositionGetter.LCR.Center)
            drive.followTrajectorySequenceAsync(closeCenter)
        else if (cv.lcr() == TargetPositionGetter.LCR.Right)
            drive.followTrajectorySequenceAsync(closeRight)
        else if (cv.lcr() == TargetPositionGetter.LCR.Left)
            drive.followTrajectorySequenceAsync(closeLeft)

        if (isStopRequested) return

        while (opModeIsActive()) {
            drive.update()
        }
    }
}
