package org.firstinspires.ftc.teamcode

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import org.firstinspires.ftc.teamcode.lib.Tilt

@TeleOp
class TiltTest: LinearOpMode() {
    @Throws(InterruptedException::class)
    override fun runOpMode() {
        val tilt = Tilt(hardwareMap)
        //val telemetryMultiple = MultipleTelemetry(BlocksOpModeCompanion.telemetry, FtcDashboard.getInstance().telemetry)

        waitForStart()

        while (opModeIsActive()) {
            tilt.tiltLoop(gamepad2)
            //telemetryMultiple.update()

        }
    }
}