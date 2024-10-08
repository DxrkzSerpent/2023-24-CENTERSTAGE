package org.firstinspires.ftc.teamcode.lib

import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.Gamepad
import com.qualcomm.robotcore.hardware.HardwareMap
import org.firstinspires.ftc.robotcore.external.Telemetry

class Intake(hardwareMap: HardwareMap) {
    enum class IntakeState(var intakePower: Double) {
        INTAKE(-1.0), REVERSE(0.7), REST(0.0)
    }

    val intake: DcMotor
    var currentGamepad2 = Gamepad()
    var previousGamepad2 = Gamepad()
    var intakeToggle = false

    init {
        intake = hardwareMap.get(DcMotor::class.java, "intake")
    }


    fun telemetry(telemetry: Telemetry) {
        telemetry.addData("power", intake.power)
    }

    fun intakeOn() {
        intake.power = IntakeState.INTAKE.intakePower
    }

    fun intakeReverse() {
        intake.power = IntakeState.REVERSE.intakePower
    }

    fun intakeOff() {
        intake.power = IntakeState.REST.intakePower
    }
    fun intakeLoop(gamepad: Gamepad) {
        if (intakeToggle)
            intakeOn()
        else if (gamepad.left_bumper)
            intakeReverse()
        else
            intakeOff()

        if (currentGamepad2.a && !previousGamepad2.a)
            intakeToggle = !intakeToggle
    }
}