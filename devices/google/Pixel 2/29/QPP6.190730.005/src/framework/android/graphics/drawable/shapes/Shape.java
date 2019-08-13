/*
 * Decompiled with CFR 0.145.
 */
package android.graphics.drawable.shapes;

import android.graphics.Canvas;
import android.graphics.Outline;
import android.graphics.Paint;
import java.util.Objects;

public abstract class Shape
implements Cloneable {
    private float mHeight;
    private float mWidth;

    public Shape clone() throws CloneNotSupportedException {
        return (Shape)super.clone();
    }

    public abstract void draw(Canvas var1, Paint var2);

    public boolean equals(Object object) {
        boolean bl = true;
        if (this == object) {
            return true;
        }
        if (object != null && this.getClass() == object.getClass()) {
            object = (Shape)object;
            if (Float.compare(((Shape)object).mWidth, this.mWidth) != 0 || Float.compare(((Shape)object).mHeight, this.mHeight) != 0) {
                bl = false;
            }
            return bl;
        }
        return false;
    }

    public final float getHeight() {
        return this.mHeight;
    }

    public void getOutline(Outline outline) {
    }

    public final float getWidth() {
        return this.mWidth;
    }

    public boolean hasAlpha() {
        return true;
    }

    public int hashCode() {
        return Objects.hash(Float.valueOf(this.mWidth), Float.valueOf(this.mHeight));
    }

    protected void onResize(float f, float f2) {
    }

    public final void resize(float f, float f2) {
        float f3 = f;
        if (f < 0.0f) {
            f3 = 0.0f;
        }
        f = f2;
        if (f2 < 0.0f) {
            f = 0.0f;
        }
        if (this.mWidth != f3 || this.mHeight != f) {
            this.mWidth = f3;
            this.mHeight = f;
            this.onResize(f3, f);
        }
    }
}

