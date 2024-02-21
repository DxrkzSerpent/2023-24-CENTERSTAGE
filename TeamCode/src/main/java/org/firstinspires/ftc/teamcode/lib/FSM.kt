package org.firstinspires.ftc.teamcode.lib

import com.qualcomm.robotcore.hardware.Gamepad
import com.qualcomm.robotcore.hardware.HardwareMap
import com.qualcomm.robotcore.util.ElapsedTime
import org.firstinspires.ftc.teamcode.lib.SlidePIDConfig.target
import org.firstinspires.ftc.robotcore.external.Telemetry
import kotlin.math.abs


class FSM(hardwareMap: HardwareMap) {
    enum class State {
        START, INTAKE, TRANSFER, DEPOSIT, RETRACT
    }

    private var currentGamepad2 = Gamepad()
    private var previousGamepad2 = Gamepad()
    private val slides = DepoSlides(hardwareMap)
    private val arm = Deposit(hardwareMap)
    private val intake = Intake(hardwareMap)
    private val tilt = Tilt(hardwareMap)
    private var transferTimer = ElapsedTime()
    private var depositTimer = ElapsedTime()
    private var v4bState: State = State.START
    private var intakeState: Intake.IntakeState = Intake.IntakeState.REST
    private val depositDelay = 1.0
    private val transferDelay = 1.5

    init {
        depositTimer.reset()
        transferTimer.reset()
        arm.idlePosition()
    }

    fun telemetry(telemetry: Telemetry) {
        telemetry.addData("FSM", v4bState)
    }

    fun fsmLoop(gamepad: Gamepad) {
        intake.intake.power = intakeState.intakePower
        when(v4bState) {
            State.START -> {
                arm.idlePosition()
                if (gamepad.a) {
                    v4bState = State.INTAKE
                }
            } State.INTAKE -> {
                if (currentGamepad2.a && !previousGamepad2.a)
                    intake.intakeOn()
                else if (gamepad.x)
                    intake.intakeReverse()
                else
                    intake.intakeOff()

                if (gamepad.right_trigger > 0.1 && tilt.Ltilt.position > 0.04)
                    tilt.tiltDown()
                else if (gamepad.left_trigger > 0.1 && tilt.Ltilt.position < 0.3)
                    tilt.tiltUp()

                if (gamepad.b) {
                transferTimer.reset()
                v4bState = State.TRANSFER
                }
            } State.TRANSFER -> {
                tilt.tiltTransfer()
                if (transferTimer.seconds() >= transferDelay)
                    arm.pickupPosition()
                if (gamepad.b)
                    arm.closeClaw()
                if (gamepad.y) {
                    arm.transferPosition()
                    v4bState = State.DEPOSIT
                    target = Presets.TRANSFER.tape
                }
            } State.DEPOSIT -> {
                if (abs(Presets.TRANSFER.tape - slides.slidePos) > 20) {
                    arm.placingPosition()
                } else if (gamepad.dpad_down) {
                    arm.placingPosition()
                } else if (gamepad.dpad_left) {
                    arm.diffyLeft()
                } else if (gamepad.dpad_right) {
                    arm.diffyRight()
                } else if (gamepad.dpad_up) {
                    //
                } else if (gamepad.y) {
                    slides.tape2()
                } else if (gamepad.b) {
                    slides.tape1()
                } else if (gamepad.x) {
                    slides.tape3()
                } else if (gamepad.a) {
                    arm.claw.position = 0.0
                    target = Presets.RESET.tape
                    v4bState = State.RETRACT
                    depositTimer.reset()
                }
            } State.RETRACT -> {
                if ((depositTimer.seconds()) >= depositDelay) {
                    arm.idlePosition()
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
