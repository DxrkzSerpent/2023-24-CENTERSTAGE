package org.firstinspires.ftc.teamcode.controllers

import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min
import kotlin.math.withSign

/**
 * PID controller with various feedforward components.
 */
class PIDFController
/**
 * Feedforward parameters `kV`, `kA`, and `kStatic` correspond with a basic
 * kinematic model of DC motors. The general function `kF` computes a custom feedforward
 * term for other plants.
 *
 * @param pid traditional PID coefficients
 * @param kV feedforward velocity gain
 * @param kA feedforward acceleration gain
 * @param kStatic additive feedforward constant
 * @param kF custom feedforward that depends on position and velocity
 */ @JvmOverloads constructor(
        private val pid: PIDCoefficients,
        private val kV: Double = 0.0,
        private val kA: Double = 0.0,
        private val kStatic: Double = 0.0,
        private val kF: FeedforwardFun = object : FeedforwardFun {
            override fun compute(position: Double, velocity: Double?): Double {
                return 0.0
            }
        }
) {
    class PIDCoefficients {
        var kP = 0.0
        var kI = 0.0
        var kD = 0.0
    }

    interface FeedforwardFun {
        fun compute(position: Double, velocity: Double?): Double
    }

    private var errorSum = 0.0
    private var lastUpdateTs: Long = 0
    private var inputBounded = false
    private var minInput = 0.0
    private var maxInput = 0.0
    private var outputBounded = false
    private var minOutput = 0.0
    private var maxOutput = 0.0

    /**
     * Target position (that is, the controller setpoint).
     */
    private var targetPosition = 0.0

    /**
     * Target velocity.
     */
    private var targetVelocity = 0.0

    /**
     * Target acceleration.
     */
    private var targetAcceleration = 0.0

    /**
     * Error computed in the last call to [.update]
     */
    private var lastError = 0.0

    constructor(
            pid: PIDCoefficients,
            kF: FeedforwardFun
    ) : this(pid, 0.0, 0.0, 0.0, kF)

    /**
     * Sets bound on the input of the controller. When computing the error, the min and max are
     * treated as the same value. (Imagine taking the segment of the real line between min and max
     * and attaching the endpoints.)
     *
     * @param min minimum input
     * @param max maximum input
     */
    fun setInputBounds(min: Double, max: Double) {
        if (min < max) {
            inputBounded = true
            minInput = min
            maxInput = max
        }
    }

    /**
     * Sets bounds on the output of the controller.
     *
     * @param min minimum output
     * @param max maximum output
     */
    fun setOutputBounds(min: Double, max: Double) {
        if (min < max) {
            outputBounded = true
            minOutput = min
            maxOutput = max
        }
    }

    private fun getPositionError(measuredPosition: Double): Double {
        var error = targetPosition - measuredPosition
        if (inputBounded) {
            val inputRange = maxInput - minInput
            while (abs(error) > inputRange / 2.0) {
                error -= inputRange.withSign(error)
            }
        }
        return error
    }

    /**
     * Run a single iteration of the controller.
     *
     * @param timestamp measurement timestamp as given by [System.nanoTime]
     * @param measuredPosition measured position (feedback)
     * @param measuredVelocity measured velocity
     */
    @JvmOverloads
    fun update(
            timestamp: Long,
            measuredPosition: Double,
            measuredVelocity: Double? = null
    ): Double {
        val error = getPositionError(measuredPosition)
        if (lastUpdateTs == 0L) {
            lastError = error
            lastUpdateTs = timestamp
            return 0.0
        }
        val dt = (timestamp - lastUpdateTs).toDouble()
        errorSum += 0.5 * (error + lastError) * dt
        val errorDeriv = (error - lastError) / dt
        lastError = error
        lastUpdateTs = timestamp
        val velError: Double = if (measuredVelocity == null) {
            errorDeriv
        } else {
            targetVelocity - measuredVelocity
        }
        val baseOutput = pid.kP * error + pid.kI * errorSum + pid.kD * velError + kV * targetVelocity + kA * targetAcceleration +
                kF.compute(measuredPosition, measuredVelocity)
        var output = 0.0
        if (abs(baseOutput) > 1e-6) {
            output = baseOutput + kStatic.withSign(baseOutput)
        }
        return if (outputBounded) {
            max(minOutput, min(output, maxOutput))
        } else output
    }

    fun update(
            measuredPosition: Double
    ): Double {
        return update(System.nanoTime(), measuredPosition, null)
    }

    /**
     * Reset the controller's integral sum.
     */
    fun reset() {
        errorSum = 0.0
        lastError = 0.0
        lastUpdateTs = 0
    }
}