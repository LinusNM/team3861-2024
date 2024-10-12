package org.firstinspires.ftc.teamcode.opmode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
@Disabled
@Autonomous(name="auto", group="opmode")
public class AutoOp extends LinearOpMode {
    public void runOpMode() {
        //        If it has a consistent starting position then we can start the class with 3 lines
//        defining the starting position, if not then maybe it’ll have the same starting rotation?
//Does 2 things: estimates position and rotation based on movement (of joystick or robot?)
////        and updates them to be truly accurate whenever an april tag is in view
////   While testing do something like if x or y or rotation == null print(“shits fucked”)
////        (probably separate if/then statements for each variable to differentiate which ones busted)
////        (also maybe do the same thing if the reported x or y value is one that would position it outside of the arena? Or if 0 < rotation < 360)
////

        while (opModeIsActive()){
//            Function: probably multiple if/then forever loops that define the x/y coord range and domain
//        of different areas of the arena?
//        Ex. if whatever < x < whatever and whatever < y <whatever in_center = true
//        So like if carrying_brick == true goTo(teamZone)

        }


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
