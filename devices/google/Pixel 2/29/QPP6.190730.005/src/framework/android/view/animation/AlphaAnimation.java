/*
 * Decompiled with CFR 0.145.
 */
package android.view.animation;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import com.android.internal.R;

public class AlphaAnimation
extends Animation {
    private float mFromAlpha;
    private float mToAlpha;

    public AlphaAnimation(float f, float f2) {
        this.mFromAlpha = f;
        this.mToAlpha = f2;
    }

    public AlphaAnimation(Context object, AttributeSet attributeSet) {
        super((Context)object, attributeSet);
        object = ((Context)object).obtainStyledAttributes(attributeSet, R.styleable.AlphaAnimation);
        this.mFromAlpha = ((TypedArray)object).getFloat(0, 1.0f);
        this.mToAlpha = ((TypedArray)object).getFloat(1, 1.0f);
        ((TypedArray)object).recycle();
    }

    @Override
    protected void applyTransformation(float f, Transformation transformation) {
        float f2 = this.mFromAlpha;
        transformation.setAlpha((this.mToAlpha - f2) * f + f2);
    }

    @Override
    public boolean hasAlpha() {
        return true;
    }

    @Override
    public boolean willChangeBounds() {
        return false;
    }

    @Override
    public boolean willChangeTransformationMatrix() {
        return false;
    }
}

