package org.firstinspires.ftc.teamcode.lib

import com.qualcomm.robotcore.hardware.Gamepad
import com.qualcomm.robotcore.hardware.HardwareMap
import com.qualcomm.robotcore.util.ElapsedTime
import org.firstinspires.ftc.teamcode.lib.SlidePIDConfig.target
import org.firstinspires.ftc.robotcore.external.Telemetry
import kotlin.math.abs


class FSM(hardwareMap: HardwareMap) {
    enum class State {
        V4B_START, V4B_INTAKE, V4B_DEPOSIT, V4B_RETRACT
    }

    var currentGamepad2 = Gamepad()
    var previousGamepad2 = Gamepad()
    private val slides = DepoSlides(hardwareMap)
    private val arm = Deposit(hardwareMap)
    private var armTimer = ElapsedTime()
    private var intakeTimer = ElapsedTime()
    private var v4bState: State = State.V4B_START
    private var intakeState: Intake.IntakeState = Intake.IntakeState.REST
    private val depositDelay = 2.0

    init {
        armTimer.reset()

        intakeTimer.seconds()
        arm.arm1.position = 0.2
        arm.arm2.position = 0.2
    }

    fun telemetry(telemetry: Telemetry) {
        telemetry.addData("FSM", v4bState)
        telemetry.addData("FSM timer", intakeTimer.seconds())
    }

    fun fsmLoop(gamepad: Gamepad) {
        when(v4bState) {
            State.V4B_START -> {
            } State.V4B_INTAKE -> {
            } State.V4B_DEPOSIT -> {
            } State.V4B_RETRACT -> {
            }
        }

        if (gamepad.back && v4bState != State.V4B_START)
            v4bState = State.V4B_START
    }
}
