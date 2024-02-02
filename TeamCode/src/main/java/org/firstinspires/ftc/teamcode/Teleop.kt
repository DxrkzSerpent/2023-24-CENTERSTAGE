package org.firstinspires.ftc.teamcode

import com.acmerobotics.dashboard.FtcDashboard
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import com.qualcomm.robotcore.exception.RobotCoreException
import org.firstinspires.ftc.teamcode.lib.DepoSlides
import org.firstinspires.ftc.teamcode.lib.Deposit
import org.firstinspires.ftc.teamcode.lib.Drone
import org.firstinspires.ftc.teamcode.lib.Intake
import org.firstinspires.ftc.teamcode.lib.Mecanum

@TeleOp
class TeleOp: LinearOpMode() {
    @Throws(InterruptedException::class)
    override fun runOpMode() {
        val telemetryMultiple = MultipleTelemetry(telemetry, FtcDashboard.getInstance().telemetry)
        val mecanum = Mecanum(hardwareMap)
        val drone = Drone(hardwareMap)
        val intake = Intake(hardwareMap)
        val deposit = Deposit(hardwareMap)
        val currentGP2 = intake.currentGamepad2
        val previousGP2 = intake.previousGamepad2
        val depoSlide = DepoSlides(hardwareMap)

        waitForStart()

        while (opModeIsActive()) {
            try {
                previousGP2.copy(currentGP2)
                currentGP2.copy(gamepad2)
            } catch (_: RobotCoreException) {
            }

            deposit.depositLoop(gamepad2)
            mecanum.mecanumLoop(gamepad1)
            drone.drone(gamepad2)
            intake.intakeLoop(gamepad2)
            depoSlide.slideLoop(gamepad2)

            drone.telemetry(telemetryMultiple)
            mecanum.telemetry(telemetryMultiple, gamepad1)
            intake.telemetry(telemetryMultiple)
            deposit.telemetry(telemetryMultiple)
            depoSlide.telemetry(telemetryMultiple)
            telemetryMultiple.update()
        }
    }
}