package org.firstinspires.ftc.teamcode.sensors;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.gamepad1;
import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.LED;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

@TeleOp(name = "Sensor: Indicator Light", group = "Sensor")
//@Disabled
public class IndicatorLight extends OpMode {

    LED frontLED_red;
    LED frontLED_green;

    public void init() {
        frontLED_green = hardwareMap.get(LED.class, "front_led_green");
        frontLED_red = hardwareMap.get(LED.class, "front_led_red");
    }

    public void loop() {
        if(ColorSensorMR.colorInt() == 0) {
            frontLED_green.on();
            frontLED_red.off();
        }
        else if(ColorSensorMR.colorInt() == 1) {
            frontLED_green.off();
            frontLED_red.on();
        }
        else if(ColorSensorMR.colorInt() == 2) {
            frontLED_green.on();
            frontLED_red.on();
        }
        else {
            frontLED_green.off();
            frontLED_red.off();
        }
    }
}
