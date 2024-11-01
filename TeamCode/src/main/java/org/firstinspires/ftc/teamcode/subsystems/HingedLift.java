package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.HardwareConstants;
import org.firstinspires.ftc.teamcode.core.LiftMotor;
import org.firstinspires.ftc.teamcode.subsystems.SampleClaw.*;


public class HingedLift {
    public enum RunPriority {
        LIFT,
        HINGE,
        BOTH
    }

    public enum Position {
        DOWN(0, 0, RunPriority.LIFT, ClawPosition.DOWN),
        HIGH_BASKET(1000, 800, RunPriority.HINGE, ClawPosition.BASKET),
        LOW_CHAMBER(400, 750, RunPriority.HINGE, ClawPosition.CHAMBER),
        HIGH_CHAMBER(800, 750, RunPriority.HINGE, ClawPosition.CHAMBER),
        LOW_BASKET(500, 800, RunPriority.HINGE, ClawPosition.BASKET),
        HANG(700, 750, RunPriority.HINGE, ClawPosition.DOWN),
        FWD_COLLECT(300, 0, RunPriority.BOTH, ClawPosition.FWD_COLLECT),
        REAR_COLLECT(0, 1300, RunPriority.BOTH, ClawPosition.REAR_COLLECT),
        FREE;

        public RunPriority priority;
        public SampleClaw.ClawPosition clawpos;
        public Integer liftpos;
        public Integer hingepos;

        Position(int liftpos, int hingepos, RunPriority priority, ClawPosition clawpos){
            this.hingepos = hingepos;
            this.liftpos = liftpos;
            this.clawpos = clawpos;
            this.priority = priority;
        }

        Position(int liftpos, int hingepos) {
            this(liftpos, hingepos, RunPriority.BOTH, ClawPosition.DOWN);
        }

        Position() {
            clawpos = ClawPosition.DOWN;
        }
    }
    private Position currentPos;

    public LiftMotor hinge, lift;
    private int[][] positions;

    public HingedLift(DcMotor hinge, DcMotor lift) {
        this.hinge = new LiftMotor(hinge, 0, 1000);
        this.lift = new LiftMotor(lift, 0, 500);
        positions = HardwareConstants.liftPositions;
    }

    public void setPosition(Position position) {
        int foo = 0;
        currentPos = position;
        if (position == Position.FREE) { // all exception positions here
            hinge.setPosition(hinge.getPosition());
            lift.setPosition(lift.getPosition());
            return;
        }
        hinge.setPosition(position.hingepos);
        lift.setPosition(position.liftpos);
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
