package org.firstinspires.ftc.teamcode.BetaBot

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import org.firstinspires.ftc.teamcode.lib.CandyShooter
import org.firstinspires.ftc.teamcode.lib.ControllableMecanum
import org.firstinspires.ftc.teamcode.lib.Mecanum

class CandyShooterTeleOp : LinearOpMode() {


    val mecanum = Mecanum(hardwareMap)
    val candyShooter = CandyShooter()
    override fun runOpMode() {
        waitForStart()
        while(opModeIsActive()){
            mecanum.mecanumLoop(gamepad1)
            candyShooter.control(gamepad1)
            candyShooter.telem()
        }


    }
}