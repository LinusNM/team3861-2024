package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.HardwareConstants;
import org.firstinspires.ftc.teamcode.core.*;

public class HingedLift {
    public enum RunPriority {
        LIFT,
        HINGE,
        BOTH
    }

    public enum Position {
        DOWN(RunPriority.LIFT),
        HIGH_BASKET(RunPriority.HINGE),
        LOW_CHAMBER(RunPriority.HINGE),
        HIGH_CHAMBER(RunPriority.HINGE),
        LOW_BASKET(RunPriority.HINGE),
        HANG(RunPriority.HINGE),
        FWD_COLLECT(RunPriority.LIFT),
        REAR_COLLECT(RunPriority.BOTH),
        FREE(RunPriority.BOTH);

        public RunPriority priority;

        Position(){
            priority = RunPriority.BOTH;
        }

        Position(RunPriority priority) {
            this.priority = priority;
        }
    }
    private Position currentPos;

    public LiftMotor hinge, lift;
    private int[][] positions;

    public HingedLift(DcMotor hinge, DcMotor lift) {
        this.hinge = new LiftMotor(hinge);
        this.lift = new LiftMotor(lift);
        positions = HardwareConstants.liftPositions;
    }

    public void setPosition(Position position) {
        int foo = 0;
        currentPos = position;
        if(position == Position.DOWN)
            foo = 0;
        else if (position == Position.HIGH_BASKET)
            foo = 1;
        else if (position == Position.FREE) {
            hinge.setPosition(hinge.getPosition());
            lift.setPosition(lift.getPosition());
        }
        int[] arr = positions[foo];
        hinge.setPosition(arr[0]);
        lift.setPosition(arr[1]);
    }

    public Position getCurrentPos() {
        return currentPos;
    }

    public void update() {
        if(currentPos.priority == RunPriority.BOTH) {
            lift.update();
            hinge.update();
        }
        else if (currentPos.priority == RunPriority.LIFT){
            lift.update();
            if(!lift.isBusy())
                hinge.update();
        }
        else{
            hinge.update();
            if(!hinge.isBusy())
                lift.update();
        }
    }
}
