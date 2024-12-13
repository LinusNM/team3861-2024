package org.firstinspires.ftc.teamcode.subsystems;


public class Lift {
    /*
      ALL MASS UNITS MUST BE IN KG
      ALL LENGTH UNITS MUST BE IN CM
      ALL ANGULAR UNITS MUST BE IN RADS
      ALL TIME UNITS MUST BE IN SECONDS
      ALL TORQUE UNITS MUST BE IN KG/CM
     */

    public DcMotor hinge;
    public DcMotor lift;

    private double base_mass = 0.130; // mass of base segment (metal mount for lift)
    private double slide_mass = 0.243; // mass of each slide

    private double base_len = 38.4;
    private double base_start = -2.4; // start of base relative to center of rotation and extension axis

    private double slide_len = 33.6;
    private double slide_start = 2.4; // when retracted

    private double gravity = -9.8;

    private double basecs = base_mass/base_len;
    private double slidecs = slide_mass/slide_len;

    public double inertiaMoment (double csmass, double begin, double end) {
        return (csmass/2) * ((end * end)-(begin * begin));
    }

    public double inertiaMoment(double extension) {
        return inertiaMoment(basecs, base_start, base_len) +
                        inertiaMoment(slidecs, slide_start + (extension/2), slide_len) +
                        inertiaMoment(slidecs, slide_start + extension, slide_len);
    }

    public double gravityComponent(double offset, double length, double mass, double theta) {
        return Math.cos(theta) * (offset + (length/2));
    }

    public double gravityComponent(double extension, double theta) {
        return gravityComponent(base_start, base_length, base_mass, theta) +
                gravityComponent(slide_start + (extension/2), slide_length, slide_mass, theta) +
                gravityComponent(slide_start + extension, slide_length, slide_mass, theta);
    }


    // for outside use function
    // alpha = desired angular accel
    // theta = current angle

    public double requiredTorque(double alpha, double theta, double extension) {
        return alpha * inertiaMoment(extension) + gravityComponent(extension, theta);
    }

    private double hingeCPrad;      // counts per radian
    private double hingePeakTorque; // torque at input 1, regardless of software max

    private double liftCPrev;
    private double liftPulleyRadius = 1.91;
    private double liftCPcm = liftCPrev * (1 / (2 * Math.pi * liftPulleyRadius)); // counts per cm

    private double hingeStartAngle;

    public double getHingeAngle() {
        return hinge.getCurrentPosition()/hingeCPrad + hingeStartAngle;
    }

    public double getLiftExtension() {
        return lift.getCurrentPosition()/liftCPcm;
    }

    public void commandHingeAcceleration(double alpha) {
        hinge.setPower(requiredTorque(alpha, getHingeAngle(), getLiftExtension())/hingePeakTorque);
    }
}