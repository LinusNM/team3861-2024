package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.ServoController;

@TeleOp(name="Claw Control")
//@Disabled
public class Claw extends LinearOpMode {

    public static double servoMaxMin(double pos){
        if(pos < 0) pos = 0;
        else if (pos > 1) pos = 1;
        return pos;
    }

    Servo myServoX;
    CRServo myServoY;
    Servo myServoZ;

    @Override
    public void runOpMode() {

        // Initialize the servo
        myServoX = hardwareMap.get(Servo.class, "clawXservo"); // Replace "servoName" with the name in your config file
        myServoY = hardwareMap.crservo.get("clawYservo");
        myServoZ = hardwareMap.get(Servo.class, "clawZservo");

        // Set the initial position
        myServoX.setPosition(0.5); // 0.5 is typically the center position
        myServoY.setPower(0.367);
        myServoZ.setPosition(0.5);

        waitForStart();
        double change = 0.001;
        double xpos = 0.5;
        double ypos = 0.367;

        while (opModeIsActive()) {

            // x servo left - right
            if (gamepad2.right_stick_x > 0.1) {
                xpos = servoMaxMin(xpos += change);
                myServoX.setPosition(xpos); // Move to one extreme
            } else if (gamepad2.right_stick_x < -0.1) {
                xpos = servoMaxMin(xpos -= change);
                myServoX.setPosition(xpos); // Move to the other extreme
            } else {
                myServoX.setPosition(xpos); // Return to center
            }

            // y servo up - down
            if (gamepad2.right_stick_y > 0.1) {
                myServoY.setPower(1); // Move to one extreme
            } else if (gamepad2.right_stick_y < -0.1) {
                myServoY.setPower(0); // Move to the other extreme
            }
            else {
                myServoY.setPower(ypos); // Return to center
            }

            // z servo open - close
            if (gamepad2.right_bumper) {
                myServoZ.setPosition(1); // Move to one extreme
            } else if (gamepad2.left_bumper) {
                myServoZ.setPosition(0); // Move to the other extreme
            } else {
                myServoZ.setPosition(0.5); // Return to center
            }

            telemetry.addData("Servo Position X", myServoX.getPosition());
            telemetry.addData("Servo Position Y", myServoY.getPower());
            telemetry.addData("Servo Position Z", myServoZ.getPosition());
            telemetry.update();
        }
    }
}
