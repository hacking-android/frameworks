/*
 * Decompiled with CFR 0.145.
 */
package android.view.animation;

import android.annotation.UnsupportedAppUsage;
import android.graphics.Matrix;
import android.view.animation.Transformation;
import android.view.animation.TranslateAnimation;

public class TranslateYAnimation
extends TranslateAnimation {
    float[] mTmpValues = new float[9];

    public TranslateYAnimation(float f, float f2) {
        super(0.0f, 0.0f, f, f2);
    }

    @UnsupportedAppUsage
    public TranslateYAnimation(int n, float f, int n2, float f2) {
        super(0, 0.0f, 0, 0.0f, n, f, n2, f2);
    }

    @Override
    protected void applyTransformation(float f, Transformation transformation) {
        transformation.getMatrix().getValues(this.mTmpValues);
        float f2 = this.mFromYDelta;
        float f3 = this.mToYDelta;
        float f4 = this.mFromYDelta;
        transformation.getMatrix().setTranslate(this.mTmpValues[2], f2 + (f3 - f4) * f);
    }
}

