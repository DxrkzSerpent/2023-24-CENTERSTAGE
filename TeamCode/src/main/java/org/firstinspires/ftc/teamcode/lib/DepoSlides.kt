package org.firstinspires.ftc.teamcode.lib

import com.acmerobotics.dashboard.config.Config
import com.qualcomm.robotcore.hardware.DcMotorEx
import com.qualcomm.robotcore.hardware.DcMotorSimple
import com.qualcomm.robotcore.hardware.Gamepad
import com.qualcomm.robotcore.hardware.HardwareMap
import org.firstinspires.ftc.robotcore.external.Telemetry
import org.firstinspires.ftc.teamcode.controllers.PIDCoefficients
import org.firstinspires.ftc.teamcode.controllers.PIDFController
import org.firstinspires.ftc.teamcode.lib.SlidePIDConfig.fg
import org.firstinspires.ftc.teamcode.lib.SlidePIDConfig.p
import org.firstinspires.ftc.teamcode.lib.SlidePIDConfig.target

enum class Presets(var tape: Double) {
    RESET(0.0), TRANSFER(800.0), TAPE_1(500.0), TAPE_2(1500.0), TAPE_3(2400.0);
}
@Config
object SlidePIDConfig {
    @JvmField var p: Double = 0.01
    @JvmField var fg: Double = 0.15
    @JvmField var target: Double = 0.0
}
class DepoSlides(hardwareMap: HardwareMap) {

    val slideMotor1: DcMotorEx
    val slideMotor2: DcMotorEx
    var slidePos: Double = 0.0
    private val pControl = PIDCoefficients(p)
    private val controller = PIDFController(pControl)
    var slidePower = 0.0
    private var offset = 0
    private val high: Int = 2380
    private val low: Int = -200

    init {
        slideMotor1 = hardwareMap.get(DcMotorEx::class.java, "lSlide")
        slideMotor2 = hardwareMap.get(DcMotorEx::class.java, "rSlide")
        slideMotor2.direction = DcMotorSimple.Direction.REVERSE
        offset = slideMotor1.currentPosition
        slidePos = slideMotor1.currentPosition.toDouble() - offset
        target = 0.0
    }

    fun telemetry(telemetry: Telemetry) {
        telemetry.addData("Slides Max Velocity", slideMotor2.velocity)
        telemetry.addData("Slide Motor Position", slidePos)
        telemetry.addData("Target Position", target)
        telemetry.addData("power",slideMotor1.power)
    }

     fun update() {
        slidePos = slideMotor1.currentPosition.toDouble() - offset
        controller.targetPosition = target
        slidePower = controller.update(slidePos) + fg
    }

    private fun increaseTarget() {
        target += 15.0
    }

    private fun decreaseTarget() {
        target -= 15.0
    }

    fun reset(){
        target = Presets.RESET.tape
    }
    fun transferPos(){
        target = Presets.TRANSFER.tape
    }
    fun tape1() {
        target = Presets.TAPE_1.tape
    }
    fun tape2() {
        target = Presets.TAPE_2.tape
    }
    fun tape3() {
        target = Presets.TAPE_3.tape
    }
    fun slideLoop(gamepad: Gamepad) {
        update()
        target = hardStops(target.toInt(), low, high).toDouble()
        slideMotor1.power = slidePower
        slideMotor2.power = slidePower

        if (gamepad.dpad_up)
            increaseTarget()
        else if (gamepad.dpad_down)
            decreaseTarget()
        else if (gamepad.left_stick_button)
            reset()
    }

    private fun hardStops(value: Int, low: Int, high: Int): Int {
        return if (value < low) low + 1
        else if (value > high) high - 1
        else value
    }
}