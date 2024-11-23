package org.firstinspires.ftc.teamcode.opmode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.apache.commons.math3.util.MathArrays;
import org.firstinspires.ftc.teamcode.*;
import org.firstinspires.ftc.teamcode.core.*;
import org.firstinspires.ftc.teamcode.subsystems.HingedLift;
import org.firstinspires.ftc.teamcode.subsystems.HingedLift.Position;
import org.firstinspires.ftc.teamcode.subsystems.SampleClaw;
import org.firstinspires.ftc.teamcode.subsystems.SampleClaw.*;

@TeleOp(name="tele systems test", group="Linear Opmode")
//@Disabled
public class SysTest extends LinearOpMode {


    private ElapsedTime runtime = new ElapsedTime();
    private MecanumDrive drive;
    private SampleClaw claw;
    private HingedLift lift;

    public static final ClawPosition[] pos = {ClawPosition.BASKET, ClawPosition.CHAMBER, ClawPosition.DOWN, ClawPosition.FWD_COLLECT, ClawPosition.REAR_COLLECT};

    @Override
    public void runOpMode() {
        claw = new SampleClaw(hardwareMap.get(Servo.class, "clawXservo"), hardwareMap.crservo.get("clawYservo"), hardwareMap.get(Servo.class, "clawZservo"));
        drive = new MecanumDrive(
                hardwareMap.get(DcMotor.class, "leftFront"),
                hardwareMap.get(DcMotor.class, "rightFront"),
                hardwareMap.get(DcMotor.class, "leftRear"),
                hardwareMap.get(DcMotor.class, "rightRear")
        );

        int current = 0;

        Button up = new Button();
        Button dn = new Button();

        /*lift = new HingedLift(hardwareMap.get(DcMotor.class, "hinge"),
                hardwareMap.get(DcMotor.class, "lift"));*/
        LiftMotor lift = new LiftMotor(hardwareMap.get(DcMotor.class, "lift"), 0, 1000);
        //LiftMotor hinge = new LiftMotor(hardwareMap.get(DcMotor.class, "hinge"), 0, 1000);
        DcMotor hinge = hardwareMap.get(DcMotor.class, "hinge");

        //drive.dampenRoll = false;
        Button dampenRoll = new Button();
        Button highBasket = new Button();

        drive.setDirection(HardwareConstants.driveDirs);

        // Wait for the game to start (driver presses PLAY)
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        waitForStart();
        claw.setPosition(ClawPosition.DOWN);
        claw.setClosed(true);
        runtime.reset();
        double lastmillis = runtime.milliseconds();
        lift.powermul = 0.5;

        lift.setPosition(200);

        //lift.setPosition(Position.DOWN);

        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {
            double axial   = -gamepad1.left_stick_y;  // pushing stick forward gives negative value
            double lateral =  gamepad1.left_stick_x;
            double yaw     =  gamepad1.right_stick_x;

            drive.setPower(lateral, axial, yaw);

            highBasket.update(gamepad2.dpad_right);

            if(highBasket.pressed())
                lift.setPosition(300);
            else if(highBasket.released()){
                lift.setPosition(0);
            }
            lift.update();

            up.update(gamepad2.dpad_up);
            dn.update(gamepad2.dpad_down);

            if(up.pressed())
                current = Math.min(current + 1, pos.length - 1);
            if(dn.pressed())
                current = Math.max(current - 1, 0);

            claw.setPosition(pos[current]);
            claw.setClosed(gamepad2.right_bumper);

            /*telemetry.addData("Roll Dampening", drive.dampenRoll ? "on" : "off");
            telemetry.addData("hinge pos", lift.hinge.getPosition());
            telemetry.addData("hinge target", lift.hinge.getTarget());
            telemetry.addData("hinge vel", lift.hinge.getVel());
            telemetry.addData("lift pos", lift.lift.getPosition());
            telemetry.addData("hinge power", lift.hinge.getPower());
            */

            /*double hpow = gamepad2.left_stick_y;
            double lpow = gamepad2.right_stick_y;
            hinge.setPower(hpow > 0.25 ? hpow : 0);
            lift.setPower(lpow > 0.25 ? lpow : 0);*/

            hinge.setPower(gamepad2.left_stick_y);

            telemetry.addData("lift pos", hinge.getCurrentPosition());

            telemetry.addData("b", gamepad2.left_stick_y);
            telemetry.update();
            lastmillis = runtime.milliseconds();
        }
    }
}
