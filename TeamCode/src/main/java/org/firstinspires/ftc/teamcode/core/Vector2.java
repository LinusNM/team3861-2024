package org.firstinspires.ftc.teamcode.core;

public class Vector2 {
    public double x;
    public double y;

    public Vector2(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Vector2 add(Vector2 arg) {
        return new Vector2(x + arg.x, y + arg.y);
    }

    public Vector2 sub(Vector2 arg) {
        return new Vector2(x - arg.x, y - arg.y);
    }

    public Vector2 mul(double arg) {
        return new Vector2(arg * x, arg * y);
    }

}
