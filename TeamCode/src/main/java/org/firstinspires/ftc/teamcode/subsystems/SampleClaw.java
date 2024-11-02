package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.Servo;

public class SampleClaw {
    public static final double yCenter = 0.367;
    public enum ClawPosition {
        CHAMBER(0,1),
        FWD_COLLECT(0.5,yCenter),
        REAR_COLLECT(0,0),
        DOWN(1,yCenter),
        BASKET(0,0);

        public double x;
        public double y;

        ClawPosition(double x, double y){
            this.x = x;
            this.y = y;
        };
    }

    public Servo xServo;
    public CRServo yServo;
    public Servo clawServo;

    public SampleClaw(Servo x, CRServo y, Servo z) {
        xServo = x;
        yServo = y;
        clawServo = z;

        setPosition(ClawPosition.DOWN);
        setClosed(true);
    }

    public void setPosition(ClawPosition pos) {
        xServo.setPosition(pos.x);
        yServo.setPower(pos.y);
    }

    public static double open = 0, closed = 1;

    public void setClosed(boolean c) {
        clawServo.setPosition(c ? closed : open);
    }
}
