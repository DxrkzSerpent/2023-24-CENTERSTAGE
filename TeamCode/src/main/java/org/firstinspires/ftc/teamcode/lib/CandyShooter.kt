package org.firstinspires.ftc.teamcode.lib

import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.Gamepad
import org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap
import org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry

class CandyShooter {

    var leftShooter : DcMotor
    var rightShooter: DcMotor

    init {
        leftShooter = hardwareMap.get(DcMotor::class.java, "leftShooter")
        rightShooter = hardwareMap.get(DcMotor::class.java, "rightShooter")
        rightShooter.zeroPowerBehavior = DcMotor.ZeroPowerBehavior.BRAKE
        leftShooter.zeroPowerBehavior = DcMotor.ZeroPowerBehavior.BRAKE
    }


    fun control(gamepad: Gamepad){
        if(gamepad.a){
            leftShooter.power = 1.0
            rightShooter.power = 1.0
        } else {
            leftShooter.power = 0.0
            rightShooter.power = 0.0
        }

    }

    fun telem(){
        telemetry.addLine(leftShooter.power.toString())
        telemetry.addLine(rightShooter.power.toString())
    }
    }