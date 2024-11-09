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

@TeleOp(name="TeleOp", group="Linear Opmode")
//@Disabled
public class GameTele extends LinearOpMode {

    private ElapsedTime runtime = new ElapsedTime();
    private MecanumDrive drive;
    private HingedLift lift;
    private SampleClaw claw;

    Button up = new Button();
    Button fwd = new Button();
    Button back = new Button();
    Button dn = new Button();

    boolean liftfwd = true;
    int liftIndex = 1;

    public static Position[] fwd_positions = {Position.FWD_COLLECT, Position.DOWN, Position.LOW_CHAMBER, Position.HIGH_CHAMBER};
    public static Position[] back_positions = {Position.REAR_COLLECT, Position.LOW_BASKET, Position.HIGH_BASKET};

    @Override
    public void runOpMode() {
        drive = new MecanumDrive(
        hardwareMap.get(DcMotor.class, "leftFront"),
        hardwareMap.get(DcMotor.class, "rightFront"),
                hardwareMap.get(DcMotor.class, "leftRear"),
        hardwareMap.get(DcMotor.class, "rightRear")
        );
        
        claw = new SampleClaw(hardwareMap.get(Servo.class, "clawXservo"), hardwareMap.crservo.get("clawYservo"), hardwareMap.get(Servo.class, "clawZservo"));
        lift = new HingedLift(hardwareMap.get(DcMotor.class, "hinge"),
                hardwareMap.get(DcMotor.class, "lift")/*,
                claw*/);

        drive.setDirection(HardwareConstants.driveDirs);

        // Wait for the game to start (driver presses PLAY)
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        waitForStart();
        runtime.reset();
        double lastmillis = runtime.milliseconds();

        lift.setPosition(Position.DOWN);
        lift.lift.powermul = 1;

        //lift.lift.setSpeed(2);

        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {
            double axial   = -gamepad1.left_stick_y;  // pushing stick forward gives negative value
            double lateral =  gamepad1.left_stick_x;
            double yaw     =  gamepad1.right_stick_x;

            drive.setPower(lateral, axial, yaw);

            fwd.update(gamepad2.dpad_right);
            back.update(gamepad2.dpad_left);
            up.update(gamepad2.dpad_up);
            dn.update(gamepad2.dpad_down);
            if(fwd.pressed())
                liftfwd = true;
            else if(back.pressed())
                liftfwd = false;
            if(up.pressed()) {
                liftIndex = liftIndex + 1;
            }
            if(dn.pressed()) {
                liftIndex = liftIndex - 1;
            }
            validateLiftPos();

            claw.clawServo.setPosition(gamepad2.left_bumper ? 1 : 0);
            claw.xServo.setPosition(gamepad2.right_stick_x);
            claw.yServo.setPower(gamepad2.right_stick_y + 0.367);

            if(/*gamepad2.right_bumper*/false) { // manual lift control
                lift.setPosition(Position.FREE);
                float x = gamepad2.left_stick_x;
                float y = gamepad2.left_stick_y;
                if(Math.abs(y) >= 0.5) {
                    lift.lift.setPosition(lift.lift.getPosition() + (y > 0 ? -60 : 60));
                }
                if(Math.abs(x) >= 0.5) {
                    lift.hinge.setPosition(lift.hinge.getPosition() + (x > 0 ? 60 : -60));
                }
            }
            else{
                lift.setPosition(liftfwd ? fwd_positions[liftIndex] : back_positions[liftIndex]);
            }
            lift.update();

            telemetry.addData("lift pos", lift.lift.getPosition());
            telemetry.addData("lift vel", lift.lift.getVel());
            telemetry.addData("lift target", lift.lift.getTarget());
            telemetry.addData("lift pos", lift.lift.getPosition());
            telemetry.addData("lift current pos", lift.getCurrentPos());
            telemetry.addData("hinge power", lift.hinge.foo);
            telemetry.addData("hinge vel", lift.hinge.getVel());
            telemetry.addData("hinge target", lift.hinge.getTarget());
            telemetry.addData("hinge pos", lift.hinge.getPosition());
            telemetry.update();
            lastmillis = runtime.milliseconds();
        }
    }

    private void validateLiftPos() {
        liftIndex = Math.min(liftIndex, liftfwd ? fwd_positions.length - 1 : back_positions.length - 1);
        liftIndex = Math.max(liftIndex, 0);
    }
}
