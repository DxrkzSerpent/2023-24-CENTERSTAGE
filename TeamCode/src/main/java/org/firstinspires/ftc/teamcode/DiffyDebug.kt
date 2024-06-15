package org.firstinspires.ftc.teamcode

import com.acmerobotics.dashboard.FtcDashboard
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry
import com.outoftheboxrobotics.photoncore.Photon
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import com.qualcomm.robotcore.exception.RobotCoreException
import org.firstinspires.ftc.teamcode.lib.DepoSlides
import org.firstinspires.ftc.teamcode.lib.Deposit
import org.firstinspires.ftc.teamcode.lib.DiffyDebug
import org.firstinspires.ftc.teamcode.lib.Drone
import org.firstinspires.ftc.teamcode.lib.Intake
import org.firstinspires.ftc.teamcode.lib.Mecanum
import org.firstinspires.ftc.teamcode.lib.Tilt

@Photon
@TeleOp
class DiffyDebug: LinearOpMode() {
    @Throws(InterruptedException::class)
    override fun runOpMode() {
        val telemetryMultiple = MultipleTelemetry(telemetry, FtcDashboard.getInstance().telemetry)
        val intake = Intake(hardwareMap)
        val deposit = DiffyDebug(hardwareMap)
        val currentGP2 = intake.currentGamepad2
        val previousGP2 = intake.previousGamepad2
        val depoSlide = DepoSlides(hardwareMap)
        //val fsm = FSM(hardwareMap)
        val tilt = Tilt(hardwareMap)

        waitForStart()

        while (opModeIsActive()) {
            try {
                previousGP2.copy(currentGP2)
                currentGP2.copy(gamepad2)
            } catch (_: RobotCoreException) {
            }


            tilt.tiltLoop(gamepad2)
            deposit.depositLoop(gamepad2)
            deposit.diffyTurning(gamepad1)
            intake.intakeLoop(gamepad2)
            depoSlide.slideLoop(gamepad2)


            intake.telemetry(telemetryMultiple)
            deposit.telemetry(telemetryMultiple)
            depoSlide.telemetry(telemetryMultiple)
            tilt.telemetry(telemetryMultiple)

            telemetryMultiple.update()
        }
    }
}