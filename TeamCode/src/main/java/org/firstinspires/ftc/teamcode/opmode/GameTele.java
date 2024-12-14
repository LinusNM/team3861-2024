package org.firstinspires.ftc.teamcode.opmode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.apache.commons.math3.util.MathArrays;
import org.firstinspires.ftc.teamcode.*;
import org.firstinspires.ftc.teamcode.core.*;
import org.firstinspires.ftc.teamcode.subsystems.ActiveIntake;
import org.firstinspires.ftc.teamcode.subsystems.HingedLift;
import org.firstinspires.ftc.teamcode.subsystems.HingedLift.Position;
import org.firstinspires.ftc.teamcode.subsystems.Lift;
import org.firstinspires.ftc.teamcode.subsystems.ManualLift;
import org.firstinspires.ftc.teamcode.subsystems.SampleClaw;

@TeleOp(name="TeleOp", group="Linear Opmode")
//@Disabled
public class GameTele extends LinearOpMode {
    private ElapsedTime hang = new ElapsedTime();
    private ElapsedTime runtime = new ElapsedTime();
    private MecanumDrive drive;
    private ManualLift lift;

    private ActiveIntake intake;

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

        intake = new ActiveIntake(hardwareMap.get(Servo.class, "clawXServo"), hardwareMap.get(CRServo.class, "clawYServo"));

        lift = new ManualLift(hardwareMap.get(DcMotor.class, "hinge"),
                hardwareMap.get(DcMotor.class, "lift"));

        drive.setDirection(HardwareConstants.driveDirs);

        // Wait for the game to start (driver presses PLAY)
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        waitForStart();
        runtime.reset();
        double lastmillis = runtime.milliseconds();

        intake.setPosition(false);
        boolean ipos = false;

        // run until the end of the match (driver presses STOP)

        double change = 0.001;
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

            if(fwd.pressed()) {
                ipos = !ipos;
                intake.setPosition(ipos);
            }

            if(gamepad2.right_bumper)
                intake.setRunning(1);
            else if(gamepad2.left_bumper)
                intake.setRunning(-1);
            else
                intake.setRunning(0.5);

            double delta = (runtime.milliseconds() - lastmillis);
            
            lift.updateManual(gamepad2.left_stick_y, gamepad2.right_trigger - gamepad2.left_trigger);

            telemetry.addData("lift pos", lift.lift.getCurrentPosition());
            telemetry.addData("hinge pos", lift.hinge.getCurrentPosition());
            telemetry.addData("hinge vel", lift.hingeEncoder.getCorrectedVelocity());
            telemetry.addData("lift target", lift.lift.getTargetPosition());
            telemetry.addData("hinge target", lift.hingetarget);
            telemetry.addData("lift inc", delta * (gamepad2.right_trigger - gamepad2.left_trigger) / 2);

            telemetry.addData("hinge pos", lift.getHingeAngle());

            telemetry.update();
            lastmillis = runtime.milliseconds();
        }
    }
}

