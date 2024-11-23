package org.firstinspires.ftc.teamcode.sensors;
import android.app.Activity;
import android.graphics.Color;
import android.view.View;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;

/*
public class colorDetection {
    // hsvValues is an array that will hold the hue, saturation, and value information.
    public float hsvValues[] = {0F,0F,0F};
    // values is a reference to the hsvValues array.
    public final float values[] = hsvValues;
    public hardwareMap colorSensor;
    public colorDetection(hadwareMap x){
        colorSensor = x;
    }
    public static String colorDetect(float red, float green, float blue) {
        if (blue > red && blue > green){
            return "Blue";
        }
        else if (red > blue + green){
            return "Red";
        }
        else if (red < blue + green) {
            return "Yellow";
        }
        else {
            return "N/A";
        }
    }

    public static int colorInt() {
        if(colorDetect(this.red(), this.green(), this.blue()).equals("Blue")) {
            return 0;
        }
        else if(colorDetect(this.red(), this.green(), this.blue()).equals("Red")) {
            return 1;
        }
        else if(colorDetect(this.red(), this.green(), this.blue()).equals("Yellow")) {
            return 2;
        }
        else return -1;
    }


        // get a reference to the RelativeLayout so we can change the background
        // color of the Robot Controller app to match the hue detected by the RGB sensor.
        int relativeLayoutId = hardwareMap.appContext.getResources().getIdentifier("RelativeLayout", "id", hardwareMap.appContext.getPackageName());
        final View relativeLayout = ((Activity) hardwareMap.appContext).findViewById(relativeLayoutId);

        // bPrevState and bCurrState represent the previous and current state of the button.
        boolean bPrevState = false;
        boolean bCurrState = false;

        // bLedOn represents the state of the LED.
        boolean bLedOn = true;

        // get a reference to our org.firstinspires.ftc.teamcode.sensors.ColorSensor object.
        colorSensor = hardwareMap.get(ColorSensor.class, "sensor_color");

        // Set the LED in the beginning
        colorSensor.enableLed(bLedOn);

        // wait for the start button to be pressed.
        waitForStart();

        // while the OpMode is active, loop and read the RGB data.
        // Note we use opModeIsActive() as our loop condition because it is an interruptible method.
        while (opModeIsActive()) {

            // check the status of the x button on either gamepad.
            bCurrState = gamepad1.x;

            // check for button state transitions.
            if (bCurrState && (bCurrState != bPrevState))  {

                // button is transitioning to a pressed state. So Toggle LED
                bLedOn = !bLedOn;
                colorSensor.enableLed(bLedOn);
            }

            // update previous state variable.
            bPrevState = bCurrState;

            // convert the RGB values to HSV values.
            Color.RGBToHSV(colorSensor.red() * 8, colorSensor.green() * 8, colorSensor.blue() * 8, hsvValues);

            // send the info back to driver station using telemetry function.
            telemetry.addData("Clear", colorSensor.alpha());
            telemetry.addData("Red  ", colorSensor.red());
            telemetry.addData("Green", colorSensor.green());
            telemetry.addData("Blue ", colorSensor.blue());
            telemetry.addData("Color", colorDetect(colorSensor.red(), colorSensor.green(), colorSensor.blue()));
            // change the background color to match the color detected by the RGB sensor.
            // pass a reference to the hue, saturation, and value array as an argument
            // to the HSVToColor method.
            relativeLayout.post(new Runnable() {
                public void run() {
                    relativeLayout.setBackgroundColor(Color.HSVToColor(0xff, values));
                }
            });

            telemetry.update();
        }


        // Set the panel back to the default color
        relativeLayout.post(new Runnable() {
            public void run() {
                relativeLayout.setBackgroundColor(Color.WHITE);
            }
        });
    }
*/