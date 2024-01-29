package org.firstinspires.ftc.teamcode.BetaBot

import com.acmerobotics.dashboard.FtcDashboard
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry
import com.qualcomm.robotcore.eventloop.opmode.Autonomous
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import org.firstinspires.ftc.teamcode.lib.ControllableMecanum
import org.firstinspires.ftc.teamcode.lib.TargetPositionGetter

@Autonomous
class WheelTest : LinearOpMode() {
    override fun runOpMode() {
        val telemetryMultiple = MultipleTelemetry(telemetry, FtcDashboard.getInstance().telemetry)
        //hardwareMap.forEach {
        //    telemetryMultiple.addData(it.deviceName, it.javaClass)
        //}
        telemetryMultiple.update()
        val mecanum = ControllableMecanum(hardwareMap)
        waitForStart()
        while (true) {
            val pow = 0.5
            Thread.sleep(50)
            mecanum.flMotor.power = pow
            Thread.sleep(500)
            mecanum.flMotor.power = 0.0
            Thread.sleep(500)
            mecanum.frMotor.power = pow
            Thread.sleep(500)
            mecanum.frMotor.power = 0.0
            Thread.sleep(500)
            mecanum.blMotor.power = pow
            Thread.sleep(500)
            mecanum.blMotor.power = 0.0
            Thread.sleep(500)
            mecanum.brMotor.power = pow
            Thread.sleep(500)
            mecanum.brMotor.power = 0.0
            Thread.sleep(500)
            //---------------------------
            mecanum.brMotor.power = -pow
            Thread.sleep(500)
            mecanum.brMotor.power = 0.0
            Thread.sleep(500)
            mecanum.blMotor.power = -pow
            Thread.sleep(500)
            mecanum.blMotor.power = 0.0
            Thread.sleep(500)
            mecanum.frMotor.power = -pow
            Thread.sleep(500)
            mecanum.frMotor.power = 0.0
            Thread.sleep(500)
            mecanum.flMotor.power = -pow
            Thread.sleep(500)
            mecanum.flMotor.power = 0.0
            Thread.sleep(500)
            telemetryMultiple.addData("1", mecanum.frMotor.currentPosition)
            telemetryMultiple.addData("2", mecanum.flMotor.currentPosition)
            telemetryMultiple.update()
        }
    }
}