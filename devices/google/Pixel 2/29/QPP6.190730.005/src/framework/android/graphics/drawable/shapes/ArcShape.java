/*
 * Decompiled with CFR 0.145.
 */
package android.graphics.drawable.shapes;

import android.graphics.Canvas;
import android.graphics.Outline;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.shapes.RectShape;
import android.graphics.drawable.shapes.Shape;
import java.util.Objects;

public class ArcShape
extends RectShape {
    private final float mStartAngle;
    private final float mSweepAngle;

    public ArcShape(float f, float f2) {
        this.mStartAngle = f;
        this.mSweepAngle = f2;
    }

    @Override
    public ArcShape clone() throws CloneNotSupportedException {
        return (ArcShape)super.clone();
    }

    @Override
    public void draw(Canvas canvas, Paint paint) {
        canvas.drawArc(this.rect(), this.mStartAngle, this.mSweepAngle, true, paint);
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
            object = (ArcShape)object;
            if (Float.compare(((ArcShape)object).mStartAngle, this.mStartAngle) != 0 || Float.compare(((ArcShape)object).mSweepAngle, this.mSweepAngle) != 0) {
                bl = false;
            }
            return bl;
        }
        return false;
    }

    @Override
    public void getOutline(Outline outline) {
    }

    public final float getStartAngle() {
        return this.mStartAngle;
    }

    public final float getSweepAngle() {
        return this.mSweepAngle;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), Float.valueOf(this.mStartAngle), Float.valueOf(this.mSweepAngle));
    }
}

