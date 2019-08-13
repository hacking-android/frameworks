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

public class OvalShape
extends RectShape {
    @Override
    public OvalShape clone() throws CloneNotSupportedException {
        return (OvalShape)super.clone();
    }

    @Override
    public void draw(Canvas canvas, Paint paint) {
        canvas.drawOval(this.rect(), paint);
    }

    @Override
    public void getOutline(Outline outline) {
        RectF rectF = this.rect();
        outline.setOval((int)Math.ceil(rectF.left), (int)Math.ceil(rectF.top), (int)Math.floor(rectF.right), (int)Math.floor(rectF.bottom));
    }
}

