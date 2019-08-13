/*
 * Decompiled with CFR 0.145.
 */
package android.filterfw.geometry;

import android.annotation.UnsupportedAppUsage;
import android.filterfw.geometry.Point;
import android.filterfw.geometry.Rectangle;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Quad {
    @UnsupportedAppUsage
    public Point p0;
    @UnsupportedAppUsage
    public Point p1;
    @UnsupportedAppUsage
    public Point p2;
    @UnsupportedAppUsage
    public Point p3;

    @UnsupportedAppUsage
    public Quad() {
    }

    @UnsupportedAppUsage
    public Quad(Point point, Point point2, Point point3, Point point4) {
        this.p0 = point;
        this.p1 = point2;
        this.p2 = point3;
        this.p3 = point4;
    }

    public boolean IsInUnitRange() {
        boolean bl = this.p0.IsInUnitRange() && this.p1.IsInUnitRange() && this.p2.IsInUnitRange() && this.p3.IsInUnitRange();
        return bl;
    }

    public Rectangle boundingBox() {
        List<Float> list = Arrays.asList(Float.valueOf(this.p0.x), Float.valueOf(this.p1.x), Float.valueOf(this.p2.x), Float.valueOf(this.p3.x));
        List<Float> list2 = Arrays.asList(Float.valueOf(this.p0.y), Float.valueOf(this.p1.y), Float.valueOf(this.p2.y), Float.valueOf(this.p3.y));
        float f = Collections.min(list).floatValue();
        float f2 = Collections.min(list2).floatValue();
        return new Rectangle(f, f2, Collections.max(list).floatValue() - f, Collections.max(list2).floatValue() - f2);
    }

    public float getBoundingHeight() {
        List<Float> list = Arrays.asList(Float.valueOf(this.p0.y), Float.valueOf(this.p1.y), Float.valueOf(this.p2.y), Float.valueOf(this.p3.y));
        return Collections.max(list).floatValue() - Collections.min(list).floatValue();
    }

    public float getBoundingWidth() {
        List<Float> list = Arrays.asList(Float.valueOf(this.p0.x), Float.valueOf(this.p1.x), Float.valueOf(this.p2.x), Float.valueOf(this.p3.x));
        return Collections.max(list).floatValue() - Collections.min(list).floatValue();
    }

    public Quad scaled(float f) {
        return new Quad(this.p0.times(f), this.p1.times(f), this.p2.times(f), this.p3.times(f));
    }

    public Quad scaled(float f, float f2) {
        return new Quad(this.p0.mult(f, f2), this.p1.mult(f, f2), this.p2.mult(f, f2), this.p3.mult(f, f2));
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{");
        stringBuilder.append(this.p0);
        stringBuilder.append(", ");
        stringBuilder.append(this.p1);
        stringBuilder.append(", ");
        stringBuilder.append(this.p2);
        stringBuilder.append(", ");
        stringBuilder.append(this.p3);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    public Quad translated(float f, float f2) {
        return new Quad(this.p0.plus(f, f2), this.p1.plus(f, f2), this.p2.plus(f, f2), this.p3.plus(f, f2));
    }

    public Quad translated(Point point) {
        return new Quad(this.p0.plus(point), this.p1.plus(point), this.p2.plus(point), this.p3.plus(point));
    }
}

