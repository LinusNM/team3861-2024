package org.firstinspires.ftc.teamcode.opmode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.apache.commons.math3.util.MathArrays;
import org.firstinspires.ftc.teamcode.*;
import org.firstinspires.ftc.teamcode.core.*;
import org.firstinspires.ftc.teamcode.subsystems.HingedLift;
import org.firstinspires.ftc.teamcode.subsystems.HingedLift.Position;

@TeleOp(name="tele systems test", group="Linear Opmode")
//@Disabled
public class SysTest extends LinearOpMode {

    private ElapsedTime runtime = new ElapsedTime();
    private MecanumDrive drive;
    private HingedLift lift;

    @Override
    public void runOpMode() {
        drive = new FastMecanum(
                hardwareMap.get(DcMotor.class, "leftFront"),
                hardwareMap.get(DcMotor.class, "rightFront"),
                hardwareMap.get(DcMotor.class, "leftRear"),
                hardwareMap.get(DcMotor.class, "rightRear")
        );

        /*lift = new HingedLift(hardwareMap.get(DcMotor.class, "hinge"),
                hardwareMap.get(DcMotor.class, "lift"));*/
        LiftMotor lift = new LiftMotor(hardwareMap.get(DcMotor.class, "lift"));
        LiftMotor hinge = new LiftMotor(hardwareMap.get(DcMotor.class, "hinge"));

        //drive.dampenRoll = false;
        Button dampenRoll = new Button();
        Button highBasket = new Button();

        drive.setDirection(HardwareConstants.driveDirs);

        // Wait for the game to start (driver presses PLAY)
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        waitForStart();
        runtime.reset();
        double lastmillis = runtime.milliseconds();

        //lift.setPosition(Position.DOWN);

        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {
            double axial   = -gamepad1.left_stick_y;  // pushing stick forward gives negative value
            double lateral =  gamepad1.left_stick_x;
            double yaw     =  gamepad1.right_stick_x;

            drive.setPower(lateral, axial, yaw);

            highBasket.update(gamepad2.dpad_up);

            if(highBasket.pressed())
                hinge.setPosition(750);
            else if(highBasket.released()){
                hinge.setPosition(10);
            }
            hinge.update();

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



            telemetry.addData("hinge pos", hinge.getPosition());
            telemetry.addData("lift pos", lift.getPosition());
            telemetry.addData("hinge target vel", hinge.foo);
            telemetry.addData("hinge target", hinge.getTarget());
            telemetry.addData("b", gamepad2.dpad_up);
            telemetry.update();
            lastmillis = runtime.milliseconds();
        }
    }
}
