/*
 * Decompiled with CFR 0.145.
 */
package android.graphics.drawable.shapes;

import android.graphics.Canvas;
import android.graphics.Outline;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.shapes.Shape;
import java.util.Objects;

public class RectShape
extends Shape {
    private RectF mRect = new RectF();

    @Override
    public RectShape clone() throws CloneNotSupportedException {
        RectShape rectShape = (RectShape)super.clone();
        rectShape.mRect = new RectF(this.mRect);
        return rectShape;
    }

    @Override
    public void draw(Canvas canvas, Paint paint) {
        canvas.drawRect(this.mRect, paint);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object != null && this.getClass() == object.getClass()) {
            if (!super.equals(object)) {
                return false;
            }
            object = (RectShape)object;
            return Objects.equals(this.mRect, ((RectShape)object).mRect);
        }
        return false;
    }

    @Override
    public void getOutline(Outline outline) {
        RectF rectF = this.rect();
        outline.setRect((int)Math.ceil(rectF.left), (int)Math.ceil(rectF.top), (int)Math.floor(rectF.right), (int)Math.floor(rectF.bottom));
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), this.mRect);
    }

    @Override
    protected void onResize(float f, float f2) {
        this.mRect.set(0.0f, 0.0f, f, f2);
    }

    protected final RectF rect() {
        return this.mRect;
    }
}

