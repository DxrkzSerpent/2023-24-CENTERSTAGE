package org.firstinspires.ftc.teamcode.lib.quickauto

import com.acmerobotics.dashboard.FtcDashboard
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry
import com.qualcomm.robotcore.eventloop.opmode.Autonomous
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.hardware.DcMotorEx
import org.firstinspires.ftc.teamcode.lib.ControllableMecanum

@Autonomous
class RedBackstage : QuickAutoData() {
    override fun runOpMode() {
        val cm = ControllableMecanum(hardwareMap)
        waitForStart()
        cm.x = 0.25F
        Thread.sleep(2500)
        cm.x = 0.0F
        //red right blue left
    }
}