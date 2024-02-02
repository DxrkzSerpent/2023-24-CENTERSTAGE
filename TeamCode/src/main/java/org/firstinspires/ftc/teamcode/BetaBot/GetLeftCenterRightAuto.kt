package org.firstinspires.ftc.teamcode.BetaBot

import com.acmerobotics.dashboard.FtcDashboard
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry
import com.qualcomm.robotcore.eventloop.opmode.Autonomous
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import org.firstinspires.ftc.teamcode.lib.ControllableMecanum
import org.firstinspires.ftc.teamcode.lib.TargetPositionGetter

@Autonomous
class GetLeftCenterRightAuto : LinearOpMode() {
    override fun runOpMode() {
        val tpg = TargetPositionGetter(hardwareMap, TargetPositionGetter.VisionProc.Color.BLUE)
        val telemetryMultiple = MultipleTelemetry(telemetry, FtcDashboard.getInstance().telemetry)
        val mecanum = ControllableMecanum(hardwareMap)
        waitForStart()
        while (true) {
            Thread.sleep(50)
            //tpg.alignerLoop(gamepad1, mecanum)
            tpg.telemetry(telemetryMultiple, true)
        }
    }
}