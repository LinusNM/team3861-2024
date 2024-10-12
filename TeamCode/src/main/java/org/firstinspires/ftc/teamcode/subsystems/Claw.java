package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name="Servo Control")
public class Claw extends LinearOpMode {

    Servo myServo;

    @Override
    public void runOpMode() {

        // Initialize the servo
        myServo = hardwareMap.get(Servo.class, "clawXservo"); // Replace "servoName" with the name in your config file

        // Set the initial position
        myServo.setPosition(0.5); // 0.5 is typically the center position

        waitForStart();

        while (opModeIsActive()) {

            // Control the servo with gamepad buttons
            if (gamepad1.a) {
                myServo.setPosition(1); // Move to one extreme
            } else if (gamepad1.b) {
                myServo.setPosition(0); // Move to the other extreme
            } else {
                myServo.setPosition(0.5); // Return to center
            }

            telemetry.addData("Servo Position", myServo.getPosition());
            telemetry.update();
        }
    }
}
