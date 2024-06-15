package org.firstinspires.ftc.teamcode.lib

import com.qualcomm.robotcore.hardware.Gamepad
import com.qualcomm.robotcore.hardware.HardwareMap
import com.qualcomm.robotcore.hardware.Servo
import org.firstinspires.ftc.robotcore.external.Telemetry

class Tilt(hardwareMap: HardwareMap) {
    val Rtilt: Servo
    val Ltilt: Servo

    init {
        Rtilt = hardwareMap.get(Servo::class.java, "Rtilt")
        Ltilt = hardwareMap.get(Servo::class.java, "Ltilt")
        Rtilt.direction = Servo.Direction.REVERSE
        Rtilt.position = 0.05
        Ltilt.position = 0.05

    }

    fun telemetry(telemetry: Telemetry) {
        telemetry.addData("tilt Right", Rtilt.position)
        telemetry.addData("tilt Left", Ltilt.position)
    }

    fun tiltDown() {
        Rtilt.position = 0.05
        Ltilt.position = 0.05
    }
    fun tiltUp() {
        Ltilt.position += 0.005
        Rtilt.position += 0.005
    }

    fun tiltTransfer() {
        Ltilt.position = 0.33
        Rtilt.position = 0.33
    }
    fun tiltLoop(gamepad: Gamepad) {
        if (gamepad.right_trigger > 0.1 && Ltilt.position > 0.04)
            tiltDown()
        else if (gamepad.left_trigger > 0.1 && Ltilt.position < 0.3)
           tiltUp()
    }
}