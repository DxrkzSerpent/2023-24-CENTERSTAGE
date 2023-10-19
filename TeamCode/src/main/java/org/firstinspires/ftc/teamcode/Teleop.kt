package org.firstinspires.ftc.teamcode

import com.acmerobotics.dashboard.FtcDashboard
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import org.firstinspires.ftc.teamcode.lib.Drone
import org.firstinspires.ftc.teamcode.lib.Mecanum

@TeleOp
class TeleOp: LinearOpMode() {
    @Throws(InterruptedException::class)
    override fun runOpMode() {
        val telemetryMultiple = MultipleTelemetry(telemetry, FtcDashboard.getInstance().telemetry)
        val mecanum = Mecanum(hardwareMap)
        val drone = Drone(hardwareMap)

        waitForStart()

        while (opModeIsActive()) {
            mecanum.mecanumLoop(gamepad1)
            drone.drone(gamepad1)

            drone.telemetry(telemetryMultiple)
            mecanum.telemetry(telemetryMultiple, gamepad2)
            telemetryMultiple.update()
        }
    }
}