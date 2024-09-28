package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.HardwareConstants;
import org.firstinspires.ftc.teamcode.core.*;

public class HingedLift {
    public enum Position {
        DOWN,
        HIGH_BASKET,
        LOW_CHAMBER,
        HIGH_CHAMBER,
        LOW_BASKET,
        HANG,
        FWD_COLLECT,
        REAR_COLLECT,
        FREE
    }
    private Position currentPos;

    public LiftMotor hinge, lift;
    private int[][] positions;
    private int maxVel;

    public HingedLift(DcMotor hinge, DcMotor lift) {
        this.hinge = new LiftMotor(hinge);
        this.lift = new LiftMotor(lift);
        positions = HardwareConstants.liftPositions;
        maxVel = HardwareConstants.liftMaxVel;

    }

    public void setPosition(Position position) {
        int foo = 0;
        if(position == Position.DOWN)
            foo = 0;
        else if (position == Position.HIGH_BASKET)
            foo = 1;
        int[] arr = positions[foo];
        hinge.setPosition(arr[0]);
        lift.setPosition(arr[1]);
        currentPos = position;
    }

    public Position getCurrentPos() {
        return currentPos;
    }
}
