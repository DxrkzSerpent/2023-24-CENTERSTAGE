package org.firstinspires.ftc.teamcode.lib

import com.qualcomm.robotcore.hardware.Gamepad
import com.qualcomm.robotcore.hardware.HardwareMap
import com.qualcomm.robotcore.hardware.Servo
import org.firstinspires.ftc.robotcore.external.Telemetry

class Drone(hardwareMap: HardwareMap) {
    private val drone: Servo

    init {
        drone = hardwareMap.get(Servo::class.java, "drone")
        drone.position = 0.0
    }

    fun telemetry(telemetry: Telemetry) {
        telemetry.addData( "Drone Servo Pos", drone.position.toString())
    }

    fun drone(gamepad: Gamepad) {
        if (gamepad.left_bumper) {
            drone.position = 1.0
        } else if (gamepad.right_bumper) {
            drone.position = 0.0
        }
    }
}