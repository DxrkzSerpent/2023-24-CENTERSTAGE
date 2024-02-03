package org.firstinspires.ftc.teamcode.lib.quickauto

import com.acmerobotics.dashboard.FtcDashboard
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry
import com.qualcomm.robotcore.eventloop.opmode.Autonomous
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.hardware.DcMotorEx

@Autonomous
class BlueFront : QuickAutoData() {
    override fun runOpMode() {
        resetMotors()
        waitForStart()
        seekToPos(500, 500, 0, 0)//example
        seekToPos(400, 400, 0, 0)
        seekToPos(400, 200, 0, 200)
    }
}