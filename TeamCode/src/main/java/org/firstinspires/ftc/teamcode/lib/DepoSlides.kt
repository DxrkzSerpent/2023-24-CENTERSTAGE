package org.firstinspires.ftc.teamcode.lib

import com.acmerobotics.dashboard.config.Config
import com.qualcomm.robotcore.hardware.DcMotorEx
import com.qualcomm.robotcore.hardware.Gamepad
import com.qualcomm.robotcore.hardware.HardwareMap
import org.firstinspires.ftc.robotcore.external.Telemetry
import org.firstinspires.ftc.teamcode.controllers.PIDCoefficients
import org.firstinspires.ftc.teamcode.controllers.PIDFController

enum class Presets(var tape: Double) {
    RESET(0.0),TAPE_1(1100.0), TAPE_2(2100.0), TAPE_3(3350.0);
}
class DepoSlides(hardwareMap: HardwareMap) {
    @Config
    companion object {
        @JvmStatic var p = 0.0
        @JvmStatic var fg = 0.0
        @JvmStatic var target = 0.0
    }

    private val slideMotor1: DcMotorEx
    private val slideMotor2: DcMotorEx
    var slidePos: Double = 0.0
    val pControl = PIDCoefficients(p)
    private val controller = PIDFController(pControl)
    private var slidePower = 0.0
    private var offset = 0
    private val high: Int = 3351
    private val low: Int = -1

    init {
        pControl.kP = 0.0
        slideMotor1 = hardwareMap.get(DcMotorEx::class.java, "slideMotor1")
        slideMotor2 = hardwareMap.get(DcMotorEx::class.java, "slideMotor2")

    }

    fun telemetry(telemetry: Telemetry) {
        telemetry.addData("Slides Max Velocity", slideMotor2.velocity)
        telemetry.addData("Slide Motor Position", slidePos)
        telemetry.addData("Target Position", target)
    }

    private fun update() {
        slidePos = slideMotor2.currentPosition.toDouble() - offset
        controller.targetPosition = target
        slidePower = controller.update(slidePos) + fg
    }

    fun slideLoop(gamepad: Gamepad) {
        update()
        target = hardStops(target.toInt(), low, high).toDouble()
        slideMotor1.power = slidePower
        slideMotor2.power = slidePower

        if (gamepad.dpad_up) {
            target += 25.0
        } else if (gamepad.dpad_down) {
            target -= 25.0
        } else if (gamepad.left_stick_button) {
            target = Presets.TAPE_3.tape
        } else if (gamepad.dpad_left) {
            target = Presets.RESET.tape
        }
    }

    private fun hardStops(value: Int, low: Int, high: Int): Int {
        return if (value < low) low + 1
        else if (value > high) high - 1
        else value
    }
}