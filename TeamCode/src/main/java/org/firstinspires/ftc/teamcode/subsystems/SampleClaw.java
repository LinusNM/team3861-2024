package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.Servo;

public class SampleClaw {
    public static final double yCenter = 0.367;
    public enum ClawPosition {
        CHAMBER(0,0),
        FWD_COLLECT(1,1),
        REAR_COLLECT(0,0),
        DOWN(0,yCenter),
        BASKET(0,0);

        public double x;
        public double y;

        ClawPosition(double x, double y){
            this.x = x;
            this.y = y;
        };
    }

    Servo xServo;
    CRServo yServo;
    Servo clawServo;

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
