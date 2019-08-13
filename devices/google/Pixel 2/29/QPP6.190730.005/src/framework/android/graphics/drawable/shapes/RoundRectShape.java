/*
 * Decompiled with CFR 0.145.
 */
package android.graphics.drawable.shapes;

import android.graphics.Canvas;
import android.graphics.Outline;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.drawable.shapes.RectShape;
import android.graphics.drawable.shapes.Shape;
import java.util.Arrays;
import java.util.Objects;

public class RoundRectShape
extends RectShape {
    private float[] mInnerRadii;
    private RectF mInnerRect;
    private RectF mInset;
    private float[] mOuterRadii;
    private Path mPath;

    public RoundRectShape(float[] arrf, RectF rectF, float[] arrf2) {
        if (arrf != null && arrf.length < 8) {
            throw new ArrayIndexOutOfBoundsException("outer radii must have >= 8 values");
        }
        if (arrf2 != null && arrf2.length < 8) {
            throw new ArrayIndexOutOfBoundsException("inner radii must have >= 8 values");
        }
        this.mOuterRadii = arrf;
        this.mInset = rectF;
        this.mInnerRadii = arrf2;
        if (rectF != null) {
            this.mInnerRect = new RectF();
        }
        this.mPath = new Path();
    }

    @Override
    public RoundRectShape clone() throws CloneNotSupportedException {
        RoundRectShape roundRectShape = (RoundRectShape)super.clone();
        Object object = this.mOuterRadii;
        Object var3_3 = null;
        object = object != null ? (float[])object.clone() : null;
        roundRectShape.mOuterRadii = object;
        float[] arrf = this.mInnerRadii;
        object = var3_3;
        if (arrf != null) {
            object = (float[])arrf.clone();
        }
        roundRectShape.mInnerRadii = object;
        roundRectShape.mInset = new RectF(this.mInset);
        roundRectShape.mInnerRect = new RectF(this.mInnerRect);
        roundRectShape.mPath = new Path(this.mPath);
        return roundRectShape;
    }

    @Override
    public void draw(Canvas canvas, Paint paint) {
        canvas.drawPath(this.mPath, paint);
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
            object = (RoundRectShape)object;
            if (!(Arrays.equals(this.mOuterRadii, ((RoundRectShape)object).mOuterRadii) && Objects.equals(this.mInset, ((RoundRectShape)object).mInset) && Arrays.equals(this.mInnerRadii, ((RoundRectShape)object).mInnerRadii) && Objects.equals(this.mInnerRect, ((RoundRectShape)object).mInnerRect) && Objects.equals(this.mPath, ((RoundRectShape)object).mPath))) {
                bl = false;
            }
            return bl;
        }
        return false;
    }

    @Override
    public void getOutline(Outline outline) {
        if (this.mInnerRect != null) {
            return;
        }
        float f = 0.0f;
        Object object = this.mOuterRadii;
        if (object != null) {
            float f2 = object[0];
            int n = 1;
            do {
                f = f2;
                if (n >= 8) break;
                if (this.mOuterRadii[n] != f2) {
                    outline.setConvexPath(this.mPath);
                    return;
                }
                ++n;
            } while (true);
        }
        object = this.rect();
        outline.setRoundRect((int)Math.ceil(object.left), (int)Math.ceil(object.top), (int)Math.floor(object.right), (int)Math.floor(object.bottom), f);
    }

    @Override
    public int hashCode() {
        return (Objects.hash(super.hashCode(), this.mInset, this.mInnerRect, this.mPath) * 31 + Arrays.hashCode(this.mOuterRadii)) * 31 + Arrays.hashCode(this.mInnerRadii);
    }

    @Override
    protected void onResize(float f, float f2) {
        super.onResize(f, f2);
        float[] arrf = this.rect();
        this.mPath.reset();
        Object object = this.mOuterRadii;
        if (object != null) {
            this.mPath.addRoundRect((RectF)arrf, (float[])object, Path.Direction.CW);
        } else {
            this.mPath.addRect((RectF)arrf, Path.Direction.CW);
        }
        object = this.mInnerRect;
        if (object != null) {
            ((RectF)object).set(arrf.left + this.mInset.left, arrf.top + this.mInset.top, arrf.right - this.mInset.right, arrf.bottom - this.mInset.bottom);
            if (this.mInnerRect.width() < f && this.mInnerRect.height() < f2) {
                arrf = this.mInnerRadii;
                if (arrf != null) {
                    this.mPath.addRoundRect(this.mInnerRect, arrf, Path.Direction.CCW);
                } else {
                    this.mPath.addRect(this.mInnerRect, Path.Direction.CCW);
                }
            }
        }
    }
}

