package org.firstinspires.ftc.teamcode.lib

import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.Gamepad
import com.qualcomm.robotcore.hardware.HardwareMap
import com.qualcomm.robotcore.hardware.Servo
import org.firstinspires.ftc.robotcore.external.Telemetry

class Intake(hardwareMap: HardwareMap) {
    private val intake: DcMotor
    init {
        intake = hardwareMap.get(DcMotor::class.java, "intake")
    }

    fun telemetry(telemetry: Telemetry) {
        telemetry.addData("power", intake.power)
    }

    fun intakeLoop(gamepad: Gamepad) {
        if (gamepad.a)
            intake.power = 1.0
        else if (gamepad.y)
            intake.power = -1.0
        else
            intake.power = 0.0
    }
}