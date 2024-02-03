package org.firstinspires.ftc.teamcode.lib

import com.qualcomm.robotcore.hardware.Gamepad
import com.qualcomm.robotcore.hardware.HardwareMap
import com.qualcomm.robotcore.util.ElapsedTime
import org.firstinspires.ftc.teamcode.lib.SlidePIDConfig.target
import org.firstinspires.ftc.robotcore.external.Telemetry
import kotlin.math.abs


class FSM(hardwareMap: HardwareMap) {
    enum class State {
        START, INTAKE, DEPOSIT, RETRACT
    }

    var currentGamepad2 = Gamepad()
    var previousGamepad2 = Gamepad()
    private val slides = DepoSlides(hardwareMap)
    private val arm = Deposit(hardwareMap)
    private val intake = Intake(hardwareMap)
    private var armTimer = ElapsedTime()
    private var intakeTimer = ElapsedTime()
    private var v4bState: State = State.START
    private var intakeState: Intake.IntakeState = Intake.IntakeState.REST
    private val depositDelay = 1.0

    init {
        armTimer.reset()
        intakeTimer.reset()
        arm.arm1.position = 0.8
        arm.arm2.position = 0.8
    }

    fun telemetry(telemetry: Telemetry) {
        telemetry.addData("FSM", v4bState)
        telemetry.addData("FSM timer", intakeTimer.seconds())
    }

    fun fsmLoop(gamepad: Gamepad) {
        intake.intake.power = intakeState.intakePower
        when(v4bState) {
            State.START -> {
                arm.arm1.position = 0.8
                arm.arm2.position = 0.8
                if (gamepad.a) {
                    v4bState = State.INTAKE
                }
            } State.INTAKE -> {
                if (currentGamepad2.a && !previousGamepad2.a)
                    intake.intakeToggle = !intake.intakeToggle
                else if (gamepad.x)
                    intakeState = Intake.IntakeState.REVERSE
                else
                    intakeState = Intake.IntakeState.REST

                if (gamepad.y) {
                    v4bState = State.DEPOSIT
                    arm.claw.position = 0.5
                    target = Presets.TAPE_3.tape
                }
            } State.DEPOSIT -> {
                if (abs(Presets.TAPE_3.tape - slides.slidePos) > 100) {
                    arm.arm1.position = 0.0
                    arm.arm2.position = 0.0
                    arm.diffy1.position = 0.15
                    arm.diffy2.position = 0.15
                } else if (gamepad.dpad_down) {
                    target = Presets.TAPE_2.tape
                } else if (gamepad.dpad_left) {
                    target = Presets.TAPE_1.tape
                } else if (gamepad.a) {
                    arm.claw.position = 0.0
                    target = Presets.RESET.tape
                    v4bState = State.RETRACT
                    intakeTimer.reset()
                }
            } State.RETRACT -> {
                if (intakeTimer.seconds() >= depositDelay) {
                    arm.arm1.position = 0.6
                    arm.arm2.position = 0.6
                    arm.diffy1.position = 0.73
                    arm.diffy2.position = 0.73
                    target = Presets.RESET.tape
                    if (slides.slidePos < 10) {
                        v4bState = State.START
                    }
                }
            }
        }

        if (gamepad.back && v4bState != State.START)
            v4bState = State.START
    }
}
