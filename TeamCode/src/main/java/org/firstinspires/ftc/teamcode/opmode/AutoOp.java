package org.firstinspires.ftc.teamcode.opmode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.hardware.camera.BuiltinCameraDirection;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.core.Vector2;
import org.firstinspires.ftc.teamcode.core.FieldPosition;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Autonomous(name="auto", group="opmode")
public class AutoOp extends LinearOpMode {

    private AprilTagProcessor aprilTag;
    private VisionPortal visionPortal;

    // initialize with codes mapped to positions around field
    public static final HashMap<Integer, FieldPosition> tagPositions = new HashMap<>();

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
        initAprilTag();

        // Current position is origin
        xLocation = 0;
        yLocation = 0;
        rotation = 0;

        // Wait for the game to start (driver presses START)
        waitForStart();

        while (opModeIsActive()) {
            VisionPortal.CameraState cameraState = visionPortal.getCameraState();
            telemetry.addLine(String.format("Camera State: %s", cameraState.toString()));

            // Check for detected april tags and update the aprilTagLocations map
            // with any found
            List<AprilTagDetection> currentDetections = aprilTag.getDetections();
            telemetry.addData("# AprilTags Detected", currentDetections.size());

            // TODO: only keep vision portal streaming on sometimes to save CPU resources?
            // visionPortal.stopStreaming();
            // visionPortal.resumeStreaming();


            // for every seen april tag
            //    update location
            for (AprilTagDetection detection : currentDetections) {
                //TODO: translate from this to actual robot position
                if (detection.metadata != null) {
                    telemetry.addLine(String.format("\n==== (ID %d) %s", detection.id, detection.metadata.name));
                    telemetry.addLine(String.format("XYZ %6.1f %6.1f %6.1f  (inch)", detection.ftcPose.x, detection.ftcPose.y, detection.ftcPose.z));
                    telemetry.addLine(String.format("PRY %6.1f %6.1f %6.1f  (deg)", detection.ftcPose.pitch, detection.ftcPose.roll, detection.ftcPose.yaw));
                    telemetry.addLine(String.format("RBE %6.1f %6.1f %6.1f  (inch, deg, deg)", detection.ftcPose.range, detection.ftcPose.bearing, detection.ftcPose.elevation));
                } else {
                    telemetry.addLine(String.format("\n==== (ID %d) Unknown", detection.id));
                    telemetry.addLine(String.format("Center %6.0f %6.0f   (pixels)", detection.center.x, detection.center.y));
                }
            }

            // Add "key" information to telemetry
            telemetry.addLine("\nkey:\nXYZ = X (Right), Y (Forward), Z (Up) dist.");
            telemetry.addLine("PRY = Pitch, Roll & Yaw (XYZ Rotation)");
            telemetry.addLine("RBE = Range, Bearing & Elevation");

            // Push telemetry to the Driver Station.
            telemetry.update();


//            Function: probably multiple if/then forever loops that define the x/y coord range and domain
//        of different areas of the arena?
//        Ex. if whatever < x < whatever and whatever < y <whatever in_center = true
//        So like if carrying_brick == true goTo(teamZone)
/* public void runOpMode()
    x = whatever
    y= whatever
    rotation = 0? i guess?
 */
        }

        // Save more CPU resources when camera is no longer needed.
        visionPortal.close();

    }
    private void initAprilTag() {
        // Create the AprilTag processor the easy way.
        aprilTag = AprilTagProcessor.easyCreateWithDefaults();

        // Create the vision portal the easy way.
        visionPortal = VisionPortal.easyCreateWithDefaults(
            hardwareMap.get(WebcamName.class, "Webcam 1"), aprilTag);
    }


    // offset and angle are of tag relative to camera; idk how SDK gives these values, may need tweaking
    public FieldPosition calcPos(Vector2 offset, double angle, int tagID) {
        FieldPosition tagPos = tagPositions.get(Integer.valueOf(tagID));
        double theta = tagPos.angle + angle;
        Vector2 globalPos = tagPos.pos.sub(rotate(offset, theta));
        return new FieldPosition(globalPos, theta);
    }

    public Vector2 rotate(Vector2 point, double angle) { // CW around origin
        angle = -angle;
        return new Vector2(
                point.x * Math.cos(angle) + point.y * Math.sin(angle),
                point.y * Math.cos(angle) + point.x * Math.sin(angle)
        );
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
