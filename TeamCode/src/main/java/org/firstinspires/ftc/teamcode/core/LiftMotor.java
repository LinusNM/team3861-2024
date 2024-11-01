package org.firstinspires.ftc.teamcode.core;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Function;
import org.firstinspires.ftc.teamcode.HardwareConstants;
import org.firstinspires.ftc.teamcode.roadrunner.util.Encoder;

public class LiftMotor {
    private DcMotor motor;
    private Encoder encoder;
    private int targetPos;
    private int maxvel;
    private double speed;
    private double smoothPowerMultiplier = 0.01;
    private Function<Integer, Double> smoothing;
    private boolean busy = false;
    public int min;
    public int max;
    public double foo = 0;
    private ElapsedTime t = new ElapsedTime();

    public LiftMotor(DcMotor motor) {
        maxvel = 800;
        speed = 1;
        this.motor = motor;
        motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        motor.setDirection(DcMotor.Direction.FORWARD);
        encoder = new Encoder((DcMotorEx)motor);
        smoothing = (Integer i) -> {
            return new Double(-Math.min(0.005 * (double)i * i, maxvel) * ((i > 0) ? -1 : 1));
        };
        t.reset();
    }

    public LiftMotor(DcMotor motor, int min, int max) {
        this(motor);
        this.min = min;
        this.max = max;
    }

    public void setMode(DcMotor.RunMode mode) {
        motor.setTargetPosition(motor.getCurrentPosition());
        motor.setMode(mode);
    }


    public void update() {
        int posdiff = targetPos - motor.getCurrentPosition();
        if(Math.abs(posdiff) <= 20) {
            motor.setTargetPosition(targetPos);
            motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            motor.setPower(1);
            busy = false;
            return;
        }

        busy = true;
        double vel = encoder.getCorrectedVelocity();
        double targetVel = smoothing.apply(posdiff);

        double power = (targetVel - vel) * smoothPowerMultiplier * t.milliseconds();
        t.reset();
        //motor.setTargetPosition(motor.getCurrentPosition() + ((posdiff > 0) ? 30 : -30));
        motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        motor.setPower(Math.min(motor.getPower() + power, 100d));
        foo = targetVel;
    }

    public void setPosition(int pos) {
        pos = Math.min(Math.max(pos, min), max);
        motor.setTargetPosition(motor.getCurrentPosition());
        motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        targetPos = pos;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public int getPosition() {
        return encoder.getCurrentPosition();
    }

    public int getTarget() {
        return targetPos;
    }

    public void setPower(double power){
        motor.setPower(power);
    }

    public double getVel() {
        return encoder.getCorrectedVelocity();
    }

    public boolean isBusy() {
        return busy;
    }

    public double getPower() {
        return motor.getPower();
    }
}
