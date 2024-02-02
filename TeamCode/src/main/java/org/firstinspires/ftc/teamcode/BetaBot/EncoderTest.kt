package org.firstinspires.ftc.teamcode.BetaBot

import com.acmerobotics.dashboard.FtcDashboard
import com.acmerobotics.dashboard.config.Config
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry
import com.acmerobotics.roadrunner.Action
import com.acmerobotics.roadrunner.Pose2d
import com.acmerobotics.roadrunner.TrajectoryActionBuilder
import com.acmerobotics.roadrunner.Vector2d
import com.acmerobotics.roadrunner.ftc.runBlocking
import com.qualcomm.robotcore.eventloop.opmode.Autonomous
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import org.firstinspires.ftc.teamcode.RoadRunner.MecanumDrive
import org.firstinspires.ftc.teamcode.lib.TargetPositionGetter

@Autonomous
class EncoderTest : LinearOpMode() {
    fun path(tab: TrajectoryActionBuilder, lcr: TargetPositionGetter.LCR) : Action {
        return when (lcr) {
            TargetPositionGetter.LCR.Left -> tab.splineTo(Vector2d(EncoderTestParams.leftx, EncoderTestParams.lefty), EncoderTestParams.leftheading)
            TargetPositionGetter.LCR.Center -> tab.splineTo(Vector2d(EncoderTestParams.centerx, EncoderTestParams.centery), EncoderTestParams.centerheading)
            TargetPositionGetter.LCR.Right -> tab.splineTo(Vector2d(EncoderTestParams.rightx, EncoderTestParams.righty), EncoderTestParams.rightheading)
        }.build()
    }
    override fun runOpMode() {
        val telemetryMultiple = MultipleTelemetry(telemetry, FtcDashboard.getInstance().telemetry)
        telemetryMultiple.addData("PARAMS.alwaysleft", EncoderTestParams.alwaysleft)
        val drive = MecanumDrive(hardwareMap, Pose2d(Vector2d(EncoderTestParams.startx, EncoderTestParams.starty), EncoderTestParams.startheading))
        val tpg = TargetPositionGetter(hardwareMap, TargetPositionGetter.VisionProc.Color.YELLOW)
        waitForStart()
        telemetryMultiple.addLine("Waiting for Target Position Getter")
        telemetryMultiple.update()
        val lcr = if (EncoderTestParams.alwaysleft) TargetPositionGetter.LCR.Left else tpg.doDetect(telemetryMultiple)
        telemetryMultiple.addLine("Got data from Target Position Getter")
        telemetryMultiple.addData("POSE" , "x${drive.pose.position.x}y${drive.pose.position.y}imag${drive.pose.heading.imag}real${drive.pose.heading.real}")
        telemetryMultiple.update()
        runBlocking(path(drive.actionBuilder(drive.pose), lcr))
        telemetryMultiple.addLine("MOVED")
        telemetryMultiple.addData("POSE" , "x${drive.pose.position.x}y${drive.pose.position.y}imag${drive.pose.heading.imag}real${drive.pose.heading.real}")
        telemetryMultiple.update()
        Thread.sleep(Long.MAX_VALUE)
        //see: https://discord.com/channels/225450307654647808/1198976141487243284/1199021485508001982 use imu for drift
    }
}