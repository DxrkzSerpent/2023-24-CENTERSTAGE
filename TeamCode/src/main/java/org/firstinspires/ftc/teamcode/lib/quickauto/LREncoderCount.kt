package org.firstinspires.ftc.teamcode.lib.quickauto

import com.acmerobotics.dashboard.FtcDashboard
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry
import com.acmerobotics.roadrunner.Pose2d
import com.acmerobotics.roadrunner.Vector2d
import com.acmerobotics.roadrunner.ftc.runBlocking
import com.qualcomm.robotcore.eventloop.opmode.Autonomous
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import com.qualcomm.robotcore.hardware.DcMotorEx
import com.qualcomm.robotcore.hardware.IMU
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit
import org.firstinspires.ftc.teamcode.RoadRunner.MecanumDrive
import org.firstinspires.ftc.teamcode.lib.ControllableMecanum

@Autonomous
class LREncoderCount : LinearOpMode() {
    override fun runOpMode() {
        val telemetryMultiple = MultipleTelemetry(telemetry, FtcDashboard.getInstance().telemetry)
        val flMotor = hardwareMap.get(DcMotorEx::class.java, "flMotor")
        val blMotor = hardwareMap.get(DcMotorEx::class.java, "blMotor")
        val frMotor = hardwareMap.get(DcMotorEx::class.java, "frMotor")
        val brMotor = hardwareMap.get(DcMotorEx::class.java, "brMotor")
        waitForStart()
        while (true) {
            Thread.sleep(50)
            telemetryMultiple.addData("flMotor", flMotor.currentPosition)
            telemetryMultiple.addData("frMotor", frMotor.currentPosition)
            telemetryMultiple.addData("blMotor", blMotor.currentPosition)
            telemetryMultiple.addData("brMotor", brMotor.currentPosition)
            telemetryMultiple.update()
        }
    }
}