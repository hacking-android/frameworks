/*
 * Decompiled with CFR 0.145.
 */
package android.filterfw.geometry;

import android.filterfw.geometry.Point;
import android.filterfw.geometry.Quad;

public class Rectangle
extends Quad {
    public Rectangle() {
    }

    public Rectangle(float f, float f2, float f3, float f4) {
        super(new Point(f, f2), new Point(f + f3, f2), new Point(f, f2 + f4), new Point(f + f3, f2 + f4));
    }

    public Rectangle(Point point, Point point2) {
        super(point, point.plus(point2.x, 0.0f), point.plus(0.0f, point2.y), point.plus(point2.x, point2.y));
    }

    private Rectangle(Point point, Point point2, Point point3, Point point4) {
        super(point, point2, point3, point4);
    }

    public static Rectangle fromCenterVerticalAxis(Point point, Point point2, Point point3) {
        Point point4 = point2.scaledTo(point3.y / 2.0f);
        point2 = point2.rotated90(1).scaledTo(point3.x / 2.0f);
        return new Rectangle(point.minus(point2).minus(point4), point.plus(point2).minus(point4), point.minus(point2).plus(point4), point.plus(point2).plus(point4));
    }

    public static Rectangle fromRotatedRect(Point point, Point point2, float f) {
        Point point3 = new Point(point.x - point2.x / 2.0f, point.y - point2.y / 2.0f);
        Point point4 = new Point(point.x + point2.x / 2.0f, point.y - point2.y / 2.0f);
        Point point5 = new Point(point.x - point2.x / 2.0f, point.y + point2.y / 2.0f);
        point2 = new Point(point.x + point2.x / 2.0f, point.y + point2.y / 2.0f);
        return new Rectangle(point3.rotatedAround(point, f), point4.rotatedAround(point, f), point5.rotatedAround(point, f), point2.rotatedAround(point, f));
    }

    public Point center() {
        return this.p0.plus(this.p1).plus(this.p2).plus(this.p3).times(0.25f);
    }

    public float getHeight() {
        return this.p2.minus(this.p0).length();
    }

    public float getWidth() {
        return this.p1.minus(this.p0).length();
    }

    @Override
    public Rectangle scaled(float f) {
        return new Rectangle(this.p0.times(f), this.p1.times(f), this.p2.times(f), this.p3.times(f));
    }

    @Override
    public Rectangle scaled(float f, float f2) {
        return new Rectangle(this.p0.mult(f, f2), this.p1.mult(f, f2), this.p2.mult(f, f2), this.p3.mult(f, f2));
    }
}

