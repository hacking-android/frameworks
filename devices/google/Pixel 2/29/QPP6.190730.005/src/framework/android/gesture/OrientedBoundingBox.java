/*
 * Decompiled with CFR 0.145.
 */
package android.gesture;

import android.graphics.Matrix;
import android.graphics.Path;

public class OrientedBoundingBox {
    public final float centerX;
    public final float centerY;
    public final float height;
    public final float orientation;
    public final float squareness;
    public final float width;

    OrientedBoundingBox(float f, float f2, float f3, float f4, float f5) {
        this.orientation = f;
        this.width = f4;
        this.height = f5;
        this.centerX = f2;
        this.centerY = f3;
        f = f4 / f5;
        this.squareness = f > 1.0f ? 1.0f / f : f;
    }

    public Path toPath() {
        Path path = new Path();
        float[] arrf = new float[]{-this.width / 2.0f, this.height / 2.0f};
        Matrix matrix = new Matrix();
        matrix.setRotate(this.orientation);
        matrix.postTranslate(this.centerX, this.centerY);
        matrix.mapPoints(arrf);
        path.moveTo(arrf[0], arrf[1]);
        arrf[0] = -this.width / 2.0f;
        arrf[1] = -this.height / 2.0f;
        matrix.mapPoints(arrf);
        path.lineTo(arrf[0], arrf[1]);
        arrf[0] = this.width / 2.0f;
        arrf[1] = -this.height / 2.0f;
        matrix.mapPoints(arrf);
        path.lineTo(arrf[0], arrf[1]);
        arrf[0] = this.width / 2.0f;
        arrf[1] = this.height / 2.0f;
        matrix.mapPoints(arrf);
        path.lineTo(arrf[0], arrf[1]);
        path.close();
        return path;
    }
}

