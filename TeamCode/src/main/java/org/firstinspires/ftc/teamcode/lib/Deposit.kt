package org.firstinspires.ftc.teamcode.lib

import com.qualcomm.robotcore.hardware.Gamepad
import com.qualcomm.robotcore.hardware.HardwareMap
import com.qualcomm.robotcore.hardware.Servo
import org.firstinspires.ftc.robotcore.external.Telemetry

class Deposit(hardwareMap: HardwareMap) {
    val claw: Servo
    val diffy1: Servo
    val diffy2: Servo
    val arm1: Servo
    val arm2: Servo

    init {
        claw = hardwareMap.get(Servo::class.java, "claw")
        diffy1 = hardwareMap.get(Servo::class.java, "diffy1")
        diffy2 = hardwareMap.get(Servo::class.java, "diffy2")
        diffy2.direction = Servo.Direction.REVERSE
        arm1 = hardwareMap.get(Servo::class.java, "arm1")
        arm2 = hardwareMap.get(Servo::class.java, "arm2")
    }

    fun telemetry(telemetry: Telemetry){
        telemetry.addData("diffy1 pos", diffy1.position)
        telemetry.addData("diffy2 pos", diffy2.position)
        telemetry.addData("arm1 pos", arm1.position)
        telemetry.addData("arm2 pos", arm2.position)
    }
    fun depositLoop(gamepad: Gamepad) {
        if (gamepad.y)
            claw.position = 0.0
        else if (gamepad.b)
            claw.position = 0.5

        if (gamepad.dpad_left) {
            diffy1.position = 0.73
            diffy2.position = 0.73
        } else if (gamepad.dpad_right) {
            diffy1.position = 0.15
            diffy2.position = 0.15
        }
        if (gamepad.left_bumper) {
            arm2.position += 0.02
            arm1.position += 0.02
        } else if (gamepad.right_bumper) {
            arm1.position -= 0.02
            arm2.position -= 0.02
        }
    }
}