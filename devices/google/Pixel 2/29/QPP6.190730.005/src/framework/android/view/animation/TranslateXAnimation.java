/*
 * Decompiled with CFR 0.145.
 */
package android.view.animation;

import android.graphics.Matrix;
import android.view.animation.Transformation;
import android.view.animation.TranslateAnimation;

public class TranslateXAnimation
extends TranslateAnimation {
    float[] mTmpValues = new float[9];

    public TranslateXAnimation(float f, float f2) {
        super(f, f2, 0.0f, 0.0f);
    }

    public TranslateXAnimation(int n, float f, int n2, float f2) {
        super(n, f, n2, f2, 0, 0.0f, 0, 0.0f);
    }

    @Override
    protected void applyTransformation(float f, Transformation transformation) {
        transformation.getMatrix().getValues(this.mTmpValues);
        float f2 = this.mFromXDelta;
        float f3 = this.mToXDelta;
        float f4 = this.mFromXDelta;
        transformation.getMatrix().setTranslate(f2 + (f3 - f4) * f, this.mTmpValues[5]);
    }
}

