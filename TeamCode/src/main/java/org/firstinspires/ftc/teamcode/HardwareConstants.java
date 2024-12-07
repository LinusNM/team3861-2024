package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotorSimple.Direction;

public class HardwareConstants {
    public static final Direction[] driveDirs = {Direction.FORWARD, Direction.FORWARD, Direction.FORWARD, Direction.REVERSE};
    public static final double yawSens = 0.5;
    public static final double motionThreshold = 500; // millis

    // arm segment lengths
    public static final double len1 = 18, len2 = 18;

    public static final int[][] liftPositions = {{0, 0}, {750, 500}};

    public static final int liftMaxVel = 600;

    public static final int liftmax = 500, hingemax = 500, hingepeak = 250;
}
