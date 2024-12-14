package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import org.firstinspires.ftc.teamcode.HardwareConstants;
import org.firstinspires.ftc.teamcode.roadrunner.util.Encoder;

public class ManualLift { // completely different approach, no liftmotor
    public DcMotor hinge;
    public DcMotor lift;

    private int liftmin = 0, liftmax, hingemin = 0, hingemax;

    private double deadzone = 0.05;

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
    }

    public void incrementPosition(double position, double extension) {
        setPosition((int)(position + hinge.getCurrentPosition()), (int)(extension + lift.getCurrentPosition()));
    }

    private double hingeCPrad;
    private double liftCPrev = 384.5;
    private double liftPulleyRadius = 1.91;
    private double liftCPcm = liftCPrev * (1 / (2 * Math.PI * liftPulleyRadius)); // counts per cm

    public double getLiftExtension() {
        return lift.getCurrentPosition()/liftCPcm;
    }

    private double hingeStartAngle;

    public double getHingeAngle() {
        return hinge.getCurrentPosition()/hingeCPrad + hingeStartAngle;
    }


    private double maxFront = 27; // inches
    private double maxBack = 10;
    public int liftMax() {
        double angle = getHingeAngle();
        if(angle > Math.PI/4) { // back
            return 1;
        }
        return (int)(maxFront/Math.cos(angle));
    }

    public void updateManual(double hinge_axis, double lift_axis) {
        if(getLiftExtension() > 8) { // centimeters
            hinge_axis = 0;
        }
        if(Math.abs(hinge_axis) >= deadzone) {
            hinge.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            hinge.setPower(hinge_axis * 0.8);
        }
        else if (Math.abs(hingeEncoder.getCorrectedVelocity()) >= 300) {
            hinge.setPower(-0.8 * pol(hingeEncoder.getCorrectedVelocity()));
        }
        else {
            hinge.setTargetPosition((int)(hinge.getCurrentPosition() + (hingeEncoder.getCorrectedVelocity() * 0.1)));
            hinge.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        }

        if(Math.abs(lift_axis) < deadzone) {
            lift.setTargetPosition(lift.getCurrentPosition());
            lift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            return;
        }
        //double liftpos = lift.getCurrentPosition();
        lift.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        lift.setPower(0.5 * lift_axis);
    }

    private int pol(double a) {
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
