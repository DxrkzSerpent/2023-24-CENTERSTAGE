package org.firstinspires.ftc.teamcode.lib.quickauto

import com.acmerobotics.dashboard.FtcDashboard
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.hardware.DcMotorEx
import kotlin.math.abs

abstract class QuickAutoData : LinearOpMode() {
    val telemetryMultiple = MultipleTelemetry(telemetry, FtcDashboard.getInstance().telemetry)
    val flMotor = hardwareMap.get(DcMotorEx::class.java, "flMotor")
    val blMotor = hardwareMap.get(DcMotorEx::class.java, "blMotor")
    val frMotor = hardwareMap.get(DcMotorEx::class.java, "frMotor")
    val brMotor = hardwareMap.get(DcMotorEx::class.java, "brMotor")
    fun resetMotors() {

    }
    fun seekToPos(flt: Int, frt: Int, blt: Int, brt: Int, tolerance: Int = 200) {
        //o for offset, t for target
        while (true) {
            var bad = 0
            val flo = flt-flMotor.currentPosition
            val fro = flt-flMotor.currentPosition
            val blo = flt-flMotor.currentPosition
            val bro = flt-flMotor.currentPosition
            //if offset is positive then target is greater that pos and we need to move forward
            if (abs(flo) > tolerance) {
                bad++
                flMotor.power = if (flo > 0) 0.2 else -0.2
            } else {
                flMotor.power = 0.0
            }
            if (abs(fro) > tolerance) {
                bad++
                frMotor.power = if (fro > 0) 0.2 else -0.2
            } else {
                frMotor.power = 0.0
            }
            if (abs(blo) > tolerance) {
                bad++
                blMotor.power = if (blo > 0) 0.2 else -0.2
            } else {
                blMotor.power = 0.0
            }
            if (abs(bro) > tolerance) {
                bad++
                brMotor.power = if (bro > 0) 0.2 else -0.2
            } else {
                frMotor.power = 0.0
            }
            if (bad == 0) {
                flMotor.power = 0.0
                frMotor.power = 0.0
                blMotor.power = 0.0
                brMotor.power = 0.0
                return
            }
        }
    }
}