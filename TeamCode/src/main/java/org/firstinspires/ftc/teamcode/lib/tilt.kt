package org.firstinspires.ftc.teamcode.lib

import com.qualcomm.robotcore.hardware.Gamepad
import com.qualcomm.robotcore.hardware.HardwareMap
import com.qualcomm.robotcore.hardware.Servo

class tilt(hardwareMap: HardwareMap) {
    val Rtilt: Servo
    val Ltilt: Servo

    init {
        Rtilt = hardwareMap.get(Servo::class.java, "Rtilt")
        Ltilt = hardwareMap.get(Servo::class.java, "Ltilt")
        Ltilt.position = 0.0
        Rtilt.position = 0.0
    }

    fun tiltLoop(gamepad: Gamepad) {
        if (gamepad.left_bumper) {
            Rtilt.position += 0.0002
            Ltilt.position -= 0.0002
        } else if (gamepad.right_bumper) {
            Ltilt.position += 0.0002
            Rtilt.position -= 0.0002
        }
    }
}