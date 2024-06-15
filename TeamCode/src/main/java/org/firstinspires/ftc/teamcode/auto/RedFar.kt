package org.firstinspires.ftc.teamcode.auto

import com.acmerobotics.dashboard.FtcDashboard
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry
import org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap
import org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry
import org.firstinspires.ftc.teamcode.RoadRunner.drive.SampleMecanumDrive

class RedFar {
    val telemetryMultiple = MultipleTelemetry(telemetry, FtcDashboard.getInstance().telemetry)
    val drive = SampleMecanumDrive(hardwareMap)
}