package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.Servo;

public class ActiveIntake {
    public Servo x;
    public CRServo drive;

    private double passivepower = 0.2;

    public ActiveIntake(Servo x, CRServo drive) {
        this.x = x;
        this.drive = drive;
    }

    public void setPosition(boolean position) {
        x.setPosition(position ? 0 : 0.8);
    }

    public void setRunning(double running) {
        drive.setPower(running);
    }
}
