package org.firstinspires.ftc.teamcode.opmode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.hardware.camera.BuiltinCameraDirection;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;

import java.util.List;

@Autonomous(name="auto", group="opmode")
public class AutoOp extends LinearOpMode {

    private AprilTagProcessor aprilTag;

    private VisionPortal visionPortal;

    public float xLocation;
    public float yLocation;
    public float rotation;



    public void runOpMode() {
        //        If it has a consistent starting position then we can start the class with 3 lines
//        defining the starting position, if not then maybe it’ll have the same starting rotation?
//Does 2 things: estimates position and rotation based on movement (of joystick or robot?)
////        and updates them to be truly accurate whenever an april tag is in view
////   While testing do something like if x or y or rotation == null print(“shits fucked”)
////        (probably separate if/then statements for each variable to differentiate which ones busted)
////        (also maybe do the same thing if the reported x or y value is one that would position it outside of the arena? Or if 0 < rotation < 360)
////
        //initAprilTag();
        while (opModeIsActive()) {
            /*telemetryAprilTag();

            // Push telemetry to the Driver Station.
            telemetry.update();

            // Save CPU resources; can resume streaming when needed.
            if (gamepad1.dpad_down) {
                visionPortal.stopStreaming();
                telemetry.addLine("dpad down");
            } else if (gamepad1.dpad_up) {
                visionPortal.resumeStreaming();
                telemetry.addLine("dpad up");
            }

            // if see april tag
            //    update location

//            Function: probably multiple if/then forever loops that define the x/y coord range and domain
//        of different areas of the arena?
//        Ex. if whatever < x < whatever and whatever < y <whatever in_center = true
//        So like if carrying_brick == true goTo(teamZone)
/* public void runOpMode()
    x = whatever
    y= whatever
    rotation = 0? i guess?



 *
 */
        }


    }
    private void initAprilTag() {

        // Create the AprilTag processor the easy way.
        aprilTag = AprilTagProcessor.easyCreateWithDefaults();

        // Create the vision portal the easy way.

            visionPortal = VisionPortal.easyCreateWithDefaults(
                    hardwareMap.get(WebcamName.class, "Webcam 1"), aprilTag);



    }
    private void telemetryAprilTag() {

        List<AprilTagDetection> currentDetections = aprilTag.getDetections();
        telemetry.addData("# AprilTags Detected", currentDetections.size());

        // Step through the list of detections and display info for each one.
        for (AprilTagDetection detection : currentDetections) {
            if (detection.metadata != null) {
                telemetry.addLine(String.format("\n==== (ID %d) %s", detection.id, detection.metadata.name));
                telemetry.addLine(String.format("XYZ %6.1f %6.1f %6.1f  (inch)", detection.ftcPose.x, detection.ftcPose.y, detection.ftcPose.z));
                telemetry.addLine(String.format("PRY %6.1f %6.1f %6.1f  (deg)", detection.ftcPose.pitch, detection.ftcPose.roll, detection.ftcPose.yaw));
                telemetry.addLine(String.format("RBE %6.1f %6.1f %6.1f  (inch, deg, deg)", detection.ftcPose.range, detection.ftcPose.bearing, detection.ftcPose.elevation));
            } else {
                telemetry.addLine(String.format("\n==== (ID %d) Unknown", detection.id));
                telemetry.addLine(String.format("Center %6.0f %6.0f   (pixels)", detection.center.x, detection.center.y));
            }
        }   // end for() loop

        // Add "key" information to telemetry
        telemetry.addLine("\nkey:\nXYZ = X (Right), Y (Forward), Z (Up) dist.");
        telemetry.addLine("PRY = Pitch, Roll & Yaw (XYZ Rotation)");
        telemetry.addLine("RBE = Range, Bearing & Elevation");
    }

    public static double xAdditional(double angle, double length) {
        return (length) * (Math.cos(angle));
    }

    public static double yAdditional(double angle, double length) {
        return (length) * (Math.sin(angle));
    }

    public double xPos(double xAdditional) {
        return
    }

    public double yPos(double yAdditional) {

    }

    public static int apriltagposx(int id) {
        if(id == 11) return 24;
        if(id == 12) return 0;
        if(id == 13) return 24;
        if(id == 14) return 120;
        if(id == 15) return 144;
        if(id == 16) return 120;
    }

    public static int apriltagposy(int id) {
        if(id == 11) return 0;
        if(id == 12) return 72;
        if(id == 13) return 144;
        if(id == 14) return 144;
        if(id == 15) return 72;
        if(id == 16) return 0;
    }

}


//3 variable positioning system (x, y, rotation)
//
//
//
//
//
//
//        I'll also need to figure out how the rotation works
