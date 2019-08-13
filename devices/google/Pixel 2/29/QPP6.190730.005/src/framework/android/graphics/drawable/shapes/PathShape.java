/*
 * Decompiled with CFR 0.145.
 */
package android.graphics.drawable.shapes;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.drawable.shapes.Shape;
import java.util.Objects;

public class PathShape
extends Shape {
    private Path mPath;
    private float mScaleX;
    private float mScaleY;
    private final float mStdHeight;
    private final float mStdWidth;

    public PathShape(Path path, float f, float f2) {
        this.mPath = path;
        this.mStdWidth = f;
        this.mStdHeight = f2;
    }

    @Override
    public PathShape clone() throws CloneNotSupportedException {
        PathShape pathShape = (PathShape)super.clone();
        pathShape.mPath = new Path(this.mPath);
        return pathShape;
    }

    @Override
    public void draw(Canvas canvas, Paint paint) {
        canvas.save();
        canvas.scale(this.mScaleX, this.mScaleY);
        canvas.drawPath(this.mPath, paint);
        canvas.restore();
    }

    @Override
    public boolean equals(Object object) {
        boolean bl = true;
        if (this == object) {
            return true;
        }
        if (object != null && this.getClass() == object.getClass()) {
            if (!super.equals(object)) {
                return false;
            }
            object = (PathShape)object;
            if (Float.compare(((PathShape)object).mStdWidth, this.mStdWidth) != 0 || Float.compare(((PathShape)object).mStdHeight, this.mStdHeight) != 0 || Float.compare(((PathShape)object).mScaleX, this.mScaleX) != 0 || Float.compare(((PathShape)object).mScaleY, this.mScaleY) != 0 || !Objects.equals(this.mPath, ((PathShape)object).mPath)) {
                bl = false;
            }
            return bl;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), Float.valueOf(this.mStdWidth), Float.valueOf(this.mStdHeight), this.mPath, Float.valueOf(this.mScaleX), Float.valueOf(this.mScaleY));
    }

    @Override
    protected void onResize(float f, float f2) {
        this.mScaleX = f / this.mStdWidth;
        this.mScaleY = f2 / this.mStdHeight;
    }
}

