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
import org.firstinspires.ftc.teamcode.subsystems.ManualLift;
import org.firstinspires.ftc.teamcode.subsystems.SampleClaw;

@TeleOp(name="TeleOp", group="Linear Opmode")
//@Disabled
public class GameTele extends LinearOpMode {
    private ElapsedTime hang = new ElapsedTime();
    private ElapsedTime runtime = new ElapsedTime();
    private MecanumDrive drive;
    private ManualLift lift;

    private SampleClaw claw;

    Button up = new Button();
    Button fwd = new Button();
    Button back = new Button();
    Button dn = new Button();

    //public static SampleClaw.ClawPosition clawPos[] = {SampleClaw.ClawPosition.FWD_COLLECT, SampleClaw.ClawPosition.CHAMBER, SampleClaw.ClawPosition.BASKET, SampleClaw.ClawPosition.REAR_COLLECT};
    DcMotor winch;


    @Override
    public void runOpMode() {

        drive = new MecanumDrive(
                hardwareMap.get(DcMotor.class, "leftFront"),
                hardwareMap.get(DcMotor.class, "rightFront"),
                hardwareMap.get(DcMotor.class, "leftRear"),
                hardwareMap.get(DcMotor.class, "rightRear")
        );

        winch = hardwareMap.get(DcMotor.class, "winch");

        //claw = new SampleClaw(hardwareMap.get(Servo.class, "clawXservo"), hardwareMap.crservo.get("clawYservo"), hardwareMap.get(Servo.class, "clawZservo"));

        lift = new ManualLift(hardwareMap.get(DcMotor.class, "hinge"),
                hardwareMap.get(DcMotor.class, "lift"));

        drive.setDirection(HardwareConstants.driveDirs);

        // Wait for the game to start (driver presses PLAY)
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        waitForStart();
        runtime.reset();
        double lastmillis = runtime.milliseconds();
        claw.setPosition(SampleClaw.ClawPosition.DOWN);

        // run until the end of the match (driver presses STOP)

        double change = 0.001;
        double xpos = 0.5;
        double ypos = 0.367;
        while (opModeIsActive()) {

            if (gamepad2.dpad_down) {
                winch.setPower(1);
            }
            if (gamepad2.dpad_up) {
                winch.setPower(-1);
                //winch.setTargetPosition(400);
                //winch.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            } else {
                winch.setPower(0);
            }

            double axial = -gamepad1.left_stick_y;  // pushing stick forward gives negative value
            double lateral = gamepad1.left_stick_x;
            double yaw = gamepad1.right_stick_x;

            axial = -axial;
            lateral = -lateral;

            drive.setPower(lateral, axial, yaw);

            fwd.update(gamepad2.dpad_right);
            back.update(gamepad2.dpad_left);
            up.update(gamepad2.dpad_up);
            dn.update(gamepad2.dpad_down);
        }
        double delta = (runtime.milliseconds() - lastmillis);

        if (Math.abs(gamepad2.left_stick_y) > 0.2 || Math.abs(gamepad2.right_trigger - gamepad2.left_trigger) > 0.2)
            lift.incrementPosition(delta * (gamepad2.right_trigger - gamepad2.left_trigger) / 2, delta * gamepad2.left_stick_y / 2); // 500 cps

        lift.update();

        telemetry.addData("lift pos", lift.lift.getCurrentPosition());
        telemetry.addData("hinge pos", lift.hinge.getCurrentPosition());
        telemetry.addData("hinge vel", lift.hingeEncoder.getCorrectedVelocity());
        telemetry.addData("lift target", lift.lift.getTargetPosition());
        telemetry.addData("hinge target", lift.hingetarget);

        telemetry.update();
        lastmillis = runtime.milliseconds();
    }
}

