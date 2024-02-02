package org.firstinspires.ftc.teamcode.BetaBot

import com.acmerobotics.dashboard.FtcDashboard
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry
import com.acmerobotics.roadrunner.Pose2d
import com.acmerobotics.roadrunner.Vector2d
import com.acmerobotics.roadrunner.ftc.runBlocking
import com.qualcomm.robotcore.eventloop.opmode.Autonomous
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import org.firstinspires.ftc.teamcode.RoadRunner.MecanumDrive
import org.firstinspires.ftc.teamcode.lib.TargetPositionGetter

@Autonomous
class MovingAroundRoadrunnerTest : LinearOpMode() {
    private fun lcr(tm: MultipleTelemetry) : TargetPositionGetter.LCR {
        val tpg = TargetPositionGetter(hardwareMap, TargetPositionGetter.VisionProc.Color.YELLOW)
        val t = tpg.doDetect(tm)
        tpg.close()
        return t
    }
    override fun runOpMode() {
        val mecanumDrive = MecanumDrive(hardwareMap, Pose2d(Vector2d(0.0, 0.0), 0.0))
        val telemetryMultiple = MultipleTelemetry(telemetry, FtcDashboard.getInstance().telemetry)
        telemetryMultiple.update()
        waitForStart()
        val lcr = lcr(telemetryMultiple)
        while (true) {
            runBlocking(
                    mecanumDrive.actionBuilder(mecanumDrive.pose)
                            //.
                            .build())
            telemetryMultiple.update()
        }
    }
}