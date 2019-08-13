/*
 * Decompiled with CFR 0.145.
 */
package android.view.animation;

import android.annotation.UnsupportedAppUsage;
import android.graphics.Matrix;
import android.graphics.Rect;
import java.io.PrintWriter;

public class Transformation {
    public static final int TYPE_ALPHA = 1;
    public static final int TYPE_BOTH = 3;
    public static final int TYPE_IDENTITY = 0;
    public static final int TYPE_MATRIX = 2;
    protected float mAlpha;
    private Rect mClipRect = new Rect();
    private boolean mHasClipRect;
    protected Matrix mMatrix;
    protected int mTransformationType;

    public Transformation() {
        this.clear();
    }

    public void clear() {
        Matrix matrix = this.mMatrix;
        if (matrix == null) {
            this.mMatrix = new Matrix();
        } else {
            matrix.reset();
        }
        this.mClipRect.setEmpty();
        this.mHasClipRect = false;
        this.mAlpha = 1.0f;
        this.mTransformationType = 3;
    }

    public void compose(Transformation object) {
        this.mAlpha *= ((Transformation)object).getAlpha();
        this.mMatrix.preConcat(((Transformation)object).getMatrix());
        if (((Transformation)object).mHasClipRect) {
            object = ((Transformation)object).getClipRect();
            if (this.mHasClipRect) {
                this.setClipRect(this.mClipRect.left + ((Rect)object).left, this.mClipRect.top + ((Rect)object).top, this.mClipRect.right + ((Rect)object).right, this.mClipRect.bottom + ((Rect)object).bottom);
            } else {
                this.setClipRect((Rect)object);
            }
        }
    }

    public float getAlpha() {
        return this.mAlpha;
    }

    public Rect getClipRect() {
        return this.mClipRect;
    }

    public Matrix getMatrix() {
        return this.mMatrix;
    }

    public int getTransformationType() {
        return this.mTransformationType;
    }

    public boolean hasClipRect() {
        return this.mHasClipRect;
    }

    public void postCompose(Transformation object) {
        this.mAlpha *= ((Transformation)object).getAlpha();
        this.mMatrix.postConcat(((Transformation)object).getMatrix());
        if (((Transformation)object).mHasClipRect) {
            object = ((Transformation)object).getClipRect();
            if (this.mHasClipRect) {
                this.setClipRect(this.mClipRect.left + ((Rect)object).left, this.mClipRect.top + ((Rect)object).top, this.mClipRect.right + ((Rect)object).right, this.mClipRect.bottom + ((Rect)object).bottom);
            } else {
                this.setClipRect((Rect)object);
            }
        }
    }

    @UnsupportedAppUsage
    public void printShortString(PrintWriter printWriter) {
        printWriter.print("{alpha=");
        printWriter.print(this.mAlpha);
        printWriter.print(" matrix=");
        this.mMatrix.printShortString(printWriter);
        printWriter.print('}');
    }

    public void set(Transformation transformation) {
        this.mAlpha = transformation.getAlpha();
        this.mMatrix.set(transformation.getMatrix());
        if (transformation.mHasClipRect) {
            this.setClipRect(transformation.getClipRect());
        } else {
            this.mHasClipRect = false;
            this.mClipRect.setEmpty();
        }
        this.mTransformationType = transformation.getTransformationType();
    }

    public void setAlpha(float f) {
        this.mAlpha = f;
    }

    public void setClipRect(int n, int n2, int n3, int n4) {
        this.mClipRect.set(n, n2, n3, n4);
        this.mHasClipRect = true;
    }

    public void setClipRect(Rect rect) {
        this.setClipRect(rect.left, rect.top, rect.right, rect.bottom);
    }

    public void setTransformationType(int n) {
        this.mTransformationType = n;
    }

    public String toShortString() {
        StringBuilder stringBuilder = new StringBuilder(64);
        this.toShortString(stringBuilder);
        return stringBuilder.toString();
    }

    public void toShortString(StringBuilder stringBuilder) {
        stringBuilder.append("{alpha=");
        stringBuilder.append(this.mAlpha);
        stringBuilder.append(" matrix=");
        this.mMatrix.toShortString(stringBuilder);
        stringBuilder.append('}');
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder(64);
        stringBuilder.append("Transformation");
        this.toShortString(stringBuilder);
        return stringBuilder.toString();
    }
}

