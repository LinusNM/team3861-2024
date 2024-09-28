package org.firstinspires.ftc.teamcode.core;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.robotcore.external.Function;
import org.firstinspires.ftc.teamcode.roadrunner.util.Encoder;

public class LiftMotor {
    private DcMotor motor;
    private Encoder encoder;
    private int targetPos;
    private double smoothPowerMultiplier = 0.01;
    private Function<Integer, Double> smoothing;

    public LiftMotor(DcMotor motor) {
        this.motor = motor;
        motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        motor.setDirection(DcMotor.Direction.FORWARD);
        encoder = new Encoder((DcMotorEx)motor);
        smoothing = (Integer i) -> {
            return new Double(Math.min(0.005 * (double)i * i, 600));
        };
    }

    public void setMode(DcMotor.RunMode mode) {
        motor.setMode(mode);
    }

    public void update() {
        int posdiff = targetPos - motor.getCurrentPosition();
        if(posdiff <= 20) {
            motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            motor.setPower(1);
            return;
        }
        double vel = encoder.getCorrectedVelocity();
        double targetVel = smoothing.apply(posdiff);

        double power = (targetVel - vel) * smoothPowerMultiplier;
        motor.setTargetPosition(motor.getCurrentPosition() + ((power > 0) ? 10 : -10));
        motor.setPower(power);
    }

    public void setPosition(int pos) {
        motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        targetPos = pos;
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
}
