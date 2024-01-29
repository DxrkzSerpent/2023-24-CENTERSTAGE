package org.firstinspires.ftc.teamcode.lib

import com.qualcomm.robotcore.hardware.DcMotorEx
import com.qualcomm.robotcore.hardware.DcMotorSimple
import com.qualcomm.robotcore.hardware.Gamepad
import com.qualcomm.robotcore.hardware.HardwareMap
import org.firstinspires.ftc.robotcore.external.Telemetry
class ControllableMecanum(hardwareMap: HardwareMap) {
    private val flMotor: DcMotorEx
    private val frMotor: DcMotorEx
    private val blMotor: DcMotorEx
    private val brMotor: DcMotorEx
    var s: Double = 1.0
    var y: Float = 0.0F
    var x: Float = 0.0F
    var turn: Float = 0.0F

    init {
        flMotor = hardwareMap.get(DcMotorEx::class.java, "flMotor")
        blMotor = hardwareMap.get(DcMotorEx::class.java, "blMotor")
        frMotor = hardwareMap.get(DcMotorEx::class.java, "frMotor")
        brMotor = hardwareMap.get(DcMotorEx::class.java, "brMotor")
        frMotor.direction = DcMotorSimple.Direction.REVERSE
        brMotor.direction = DcMotorSimple.Direction.REVERSE
    }

    fun telemetry(telemetry: Telemetry) {
        //telemetry.addData("Front Left Power", (y + x + turn) * s)
        //telemetry.addData("Front Right Power", (y - x + turn) * s)
        //telemetry.addData("Back Left Power", (y - x - turn) * s)
        //telemetry.addData("Back Right Power", (y + x - turn) * s)
        telemetry.addData("mech:x", x)
        telemetry.addData("mech:y", y)
        telemetry.addData("mech:turn", turn)
    }
    fun mecanumLoop(){
        flMotor.power = (y + x + turn) * s
        blMotor.power = (y - x + turn) * s
        frMotor.power = (y - x - turn) * s
        brMotor.power = (y + x - turn) * s
    }
}
