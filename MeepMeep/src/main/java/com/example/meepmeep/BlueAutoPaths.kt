package com.example.meepmeep

import com.acmerobotics.roadrunner.geometry.Pose2d
import com.acmerobotics.roadrunner.geometry.Vector2d
import com.noahbres.meepmeep.MeepMeep
import com.noahbres.meepmeep.MeepMeep.Background
import com.noahbres.meepmeep.core.colorscheme.scheme.ColorSchemeBlueDark
import com.noahbres.meepmeep.core.colorscheme.scheme.ColorSchemeBlueLight
import com.noahbres.meepmeep.core.colorscheme.scheme.ColorSchemeRedDark
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder
import com.noahbres.meepmeep.roadrunner.DriveShim

object BlueAutoPaths {
    @JvmStatic
    fun main(args: Array<String>) {
        val meepMeep = MeepMeep(800, 60)
        val farRight =
            DefaultBotBuilder(meepMeep) // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(45.0, 50.0, Math.toRadians(150.0), Math.toRadians(155.0), 14.0)
                .setDimensions(16.0, 18.0)
                .setColorScheme(ColorSchemeBlueDark())
                .followTrajectorySequence { drive: DriveShim ->
                    drive.trajectorySequenceBuilder(
                        Pose2d(
                            -39.0,
                            60.0,
                            Math.toRadians(-90.0)
                        )
                    )
                        .lineToSplineHeading(
                            Pose2d(
                                -34.9,
                                32.0,
                                Math.toRadians(5.0)
                            )
                        )
                        .lineToLinearHeading(
                            Pose2d(
                                -58.0,
                                35.0,
                                Math.toRadians(0.0)
                            )
                        )
                        .waitSeconds(1.0)
                        .lineToSplineHeading(
                            Pose2d(
                                -35.0,
                                58.5,
                                Math.toRadians(0.0)
                            )
                        )
                        .forward(50.0)
                        .waitSeconds(8.0)
                        .splineToConstantHeading(
                            Vector2d(45.0, 30.0),
                            Math.toRadians(330.0)
                        )
                        .waitSeconds(1.0)
                        .strafeLeft(30.0)
                        .forward(10.0)
                        .build()
                }
        val farCenter =
            DefaultBotBuilder(meepMeep) // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(45.0, 50.0, Math.toRadians(150.0), Math.toRadians(155.0), 14.0)
                .setDimensions(16.0, 18.0)
                .setColorScheme(ColorSchemeBlueDark())
                .followTrajectorySequence { drive: DriveShim ->
                    drive.trajectorySequenceBuilder(
                        Pose2d(
                            -39.0,
                            61.9,
                            Math.toRadians(-90.0)
                        )
                    )
                        .lineToSplineHeading(
                            Pose2d(
                                -34.9,
                                32.0,
                                Math.toRadians(-90.0)
                            )
                        )
                        .back(5.0)
                        .lineToLinearHeading(
                            Pose2d(
                                -55.0,
                                35.0,
                                Math.toRadians(0.0)
                            )
                        )
                        .back(5.0)
                        .waitSeconds(1.0)
                        .lineToSplineHeading(
                            Pose2d(
                                -35.0,
                                58.5,
                                Math.toRadians(0.0)
                            )
                        )
                        .forward(50.0)
                        .waitSeconds(8.0)
                        .splineToConstantHeading(
                            Vector2d(45.0, 35.0),
                            Math.toRadians(0.0)
                        )
                        .waitSeconds(1.0)
                        .strafeLeft(25.0)
                        .forward(10.0)
                        .build()
                }
        val farLeft =
            DefaultBotBuilder(meepMeep) // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(45.0, 50.0, Math.toRadians(150.0), Math.toRadians(155.0), 14.0)
                .setDimensions(16.0, 18.0)
                .setColorScheme(ColorSchemeBlueLight())
                .followTrajectorySequence { drive: DriveShim ->
                    drive.trajectorySequenceBuilder(
                        Pose2d(
                            -39.0,
                            60.0,
                            Math.toRadians(-90.0)
                        )
                    )
                        .lineToSplineHeading(
                            Pose2d(
                                -35.9,
                                25.0,
                                Math.toRadians(-180.0)
                            )
                        )
                        .back(5.0)
                        .lineToLinearHeading(
                            Pose2d(
                                -33.0,
                                40.0,
                                Math.toRadians(0.0)
                            )
                        )
                        .lineToLinearHeading(
                            Pose2d(
                                -58.0,
                                35.0,
                                Math.toRadians(0.0)
                            )
                        )
                        .waitSeconds(1.0)
                        .lineToSplineHeading(
                            Pose2d(
                                -35.0,
                                58.5,
                                Math.toRadians(0.0)
                            )
                        )
                        .forward(50.0)
                        .waitSeconds(8.0)
                        .splineToConstantHeading(
                            Vector2d(45.0, 42.0),
                            Math.toRadians(330.0)
                        )
                        .waitSeconds(1.0)
                        .strafeLeft(18.0)
                        .forward(10.0)
                        .build()
                }
        val closeRight =
            DefaultBotBuilder(meepMeep) // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(45.0, 50.0, Math.toRadians(150.0), Math.toRadians(155.0), 14.0)
                .setDimensions(16.0, 18.0)
                .setColorScheme(ColorSchemeBlueDark())
                .followTrajectorySequence { drive: DriveShim ->
                    drive.trajectorySequenceBuilder(
                        Pose2d(
                            14.75,
                            60.0,
                            Math.toRadians(-90.0)
                        )
                    )
                        .lineToSplineHeading(Pose2d(8.0, 34.0, Math.toRadians(-115.0)))
                        .waitSeconds(0.2)
                        .back(3.0)
                        .lineToSplineHeading(Pose2d(15.0, 35.0, Math.toRadians(0.0)))
                        .splineToConstantHeading(Vector2d(49.0, 25.0), 0.0)
                        .addDisplacementMarker {
                        }
                        .forward(0.1)
                        .back(3.0)
                        .addDisplacementMarker {
                        }
                        .waitSeconds(2.0)
                        .strafeLeft(32.0)
                        .forward(10.0)
                        .build()
                }
        val closeCenter =
            DefaultBotBuilder(meepMeep) // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(45.0, 50.0, Math.toRadians(150.0), Math.toRadians(155.0), 14.0)
                .setDimensions(16.0, 18.0)
                .setColorScheme(ColorSchemeBlueDark())
                .followTrajectorySequence { drive: DriveShim ->
                    drive.trajectorySequenceBuilder(
                        Pose2d(
                            14.75,
                            61.9,
                            Math.toRadians(-90.0)
                        )
                    )
                        .lineToSplineHeading(
                            Pose2d(
                                15.0,
                                31.0,
                                Math.toRadians(-90.0)
                            )
                        )
                        .waitSeconds(0.2)
                        .lineToSplineHeading(
                            Pose2d(
                                15.0,
                                45.0,
                                Math.toRadians(0.0)
                            )
                        )
                        .lineToConstantHeading(
                            Vector2d(45.0, 35.0),
                        )
                        .waitSeconds(1.0)
                        .strafeLeft(25.0)
                        .forward(10.0)
                        .build()
                }
        val closeLeft =
            DefaultBotBuilder(meepMeep) // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(45.0, 50.0, Math.toRadians(150.0), Math.toRadians(155.0), 14.0)
                .setDimensions(16.0, 18.0)
                .setColorScheme(ColorSchemeBlueDark())
                .followTrajectorySequence { drive: DriveShim ->
                    drive.trajectorySequenceBuilder(
                        Pose2d(
                            14.75,
                            61.9,
                            Math.toRadians(-90.0)
                        )
                    )
                        .lineToSplineHeading(
                            Pose2d(
                                8.0,
                                34.0,
                                Math.toRadians(-115.0)
                            )
                        )
                        .waitSeconds(0.2)
                        .lineToSplineHeading(
                            Pose2d(
                                15.0,
                                50.0,
                                Math.toRadians(0.0)
                            )
                        )
                        .splineToConstantHeading(
                            Vector2d(45.0, 42.0),
                            Math.toRadians(330.0)
                        )
                        .waitSeconds(1.0)
                        .strafeLeft(18.0)
                        .forward(10.0)
                        .build()
                }
        meepMeep.setBackground(Background.FIELD_CENTERSTAGE_JUICE_DARK)
            .setDarkMode(true)
            .setBackgroundAlpha(0.95f)
           // .addEntity(farRight)
            //.addEntity(farCenter)
            //.addEntity(farLeft)
            .addEntity(closeRight)
            //.addEntity(closeCenter)
            //.addEntity(closeLeft)
            .start()
    }
}