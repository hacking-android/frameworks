/*
 * Decompiled with CFR 0.145.
 */
package android.filterfw.geometry;

import android.annotation.UnsupportedAppUsage;

public class Point {
    @UnsupportedAppUsage
    public float x;
    @UnsupportedAppUsage
    public float y;

    @UnsupportedAppUsage
    public Point() {
    }

    @UnsupportedAppUsage
    public Point(float f, float f2) {
        this.x = f;
        this.y = f2;
    }

    public boolean IsInUnitRange() {
        float f = this.x;
        boolean bl = f >= 0.0f && f <= 1.0f && (f = this.y) >= 0.0f && f <= 1.0f;
        return bl;
    }

    public float distanceTo(Point point) {
        return point.minus(this).length();
    }

    public float length() {
        return (float)Math.hypot(this.x, this.y);
    }

    public Point minus(float f, float f2) {
        return new Point(this.x - f, this.y - f2);
    }

    public Point minus(Point point) {
        return this.minus(point.x, point.y);
    }

    public Point mult(float f, float f2) {
        return new Point(this.x * f, this.y * f2);
    }

    public Point normalize() {
        return this.scaledTo(1.0f);
    }

    public Point plus(float f, float f2) {
        return new Point(this.x + f, this.y + f2);
    }

    public Point plus(Point point) {
        return this.plus(point.x, point.y);
    }

    public Point rotated(float f) {
        return new Point((float)(Math.cos(f) * (double)this.x - Math.sin(f) * (double)this.y), (float)(Math.sin(f) * (double)this.x + Math.cos(f) * (double)this.y));
    }

    public Point rotated90(int n) {
        float f = this.x;
        float f2 = this.y;
        for (int i = 0; i < n; ++i) {
            float f3 = -f;
            f = f2;
            f2 = f3;
        }
        return new Point(f, f2);
    }

    public Point rotatedAround(Point point, float f) {
        return this.minus(point).rotated(f).plus(point);
    }

    public Point scaledTo(float f) {
        return this.times(f / this.length());
    }

    public void set(float f, float f2) {
        this.x = f;
        this.y = f2;
    }

    public Point times(float f) {
        return new Point(this.x * f, this.y * f);
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("(");
        stringBuilder.append(this.x);
        stringBuilder.append(", ");
        stringBuilder.append(this.y);
        stringBuilder.append(")");
        return stringBuilder.toString();
    }
}

