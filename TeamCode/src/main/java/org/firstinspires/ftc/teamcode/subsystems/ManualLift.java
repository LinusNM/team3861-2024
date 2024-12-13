package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import org.firstinspires.ftc.teamcode.HardwareConstants;
import org.firstinspires.ftc.teamcode.roadrunner.util.Encoder;

public class ManualLift { // completely different approach, no liftmotor
    public DcMotor hinge;
    public DcMotor lift;

    private int liftmin = 0, liftmax, hingemin = 0, hingemax;

    private int hingeMaxVel = 600, hingeMinVel = 200;

    private static final int peak = 250;

    public int hingetarget = 0;

    public Encoder hingeEncoder;
    // test test typing
    public ManualLift(DcMotor hinge, DcMotor lift) {
        this.hinge= hinge;
        this.lift = lift;
        liftmax = 1000;//HardwareConstants.liftmax;
        hingemax = 2000;//HardwareConstants.hingemax;

        hingeEncoder = new Encoder((DcMotorEx)hinge);
    }

    public void setPosition(int position, int extension) { //in counts
        lift.setTargetPosition(clamp(extension, liftmin, liftmax));
        lift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        lift.setPower(0.5);

        hingetarget = position;
        hinge.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        update();
    }

    public void incrementPosition(double position, double extension) {
        setPosition((int)(position + hinge.getCurrentPosition()), (int)(extension + lift.getCurrentPosition()));
    }

    public void update() {
        int cpos = hinge.getCurrentPosition();
        int posdiff = hingetarget - cpos;
        double vel = hingeEncoder.getCorrectedVelocity();

        if(posdiff < (vel/4)) {
            hinge.setTargetPosition(hingetarget);
            hinge.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            hinge.setPower(1);
            return;
        }

        if(Math.abs(vel) > hingemax) {
            hinge.setPower(-0.5 * pol((int)vel));
        }
        else if(Math.abs(vel) < hingemin) {
            hinge.setPower(0.5 * pol((int)vel));
        }
        else if(Math.abs(hinge.getCurrentPosition() - peak) <= 300 || ((cpos - peak) * (hingetarget - peak) <= 0)) {
            hinge.setPower(pol(posdiff) * 0.75);
        }
        else {
            hinge.setPower(0);
        }
    }

    private int pol(int a) {
        if(a > 0)
            return 1;
        if (a<0)
            return -1;
        return 0;
    }

    private int clamp(int v, int a, int b) {
        return Math.max(a, Math.min(v, b));
    }
}
