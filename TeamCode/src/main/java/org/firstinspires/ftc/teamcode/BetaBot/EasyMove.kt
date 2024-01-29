package org.firstinspires.ftc.teamcode.BetaBot

import com.acmerobotics.dashboard.FtcDashboard
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import org.firstinspires.ftc.teamcode.lib.Mecanum

//NO MESSING
/**
 * This made by owen to quickly move the robot back to position
 */
@TeleOp
class EasyMove : LinearOpMode() {
    override fun runOpMode() {
        val telemetryMultiple = MultipleTelemetry(telemetry, FtcDashboard.getInstance().telemetry)
        val mecanum = Mecanum(hardwareMap)
        waitForStart()
        while (true) {
            mecanum.mecanumLoop(gamepad1)
            mecanum.telemetry(telemetryMultiple, gamepad1)
            Thread.sleep(50)
        }
    }
}