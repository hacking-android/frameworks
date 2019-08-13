/*
 * Decompiled with CFR 0.145.
 */
package android.view.animation;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.animation.BaseInterpolator;
import com.android.internal.R;
import com.android.internal.view.animation.HasNativeInterpolator;
import com.android.internal.view.animation.NativeInterpolatorFactory;
import com.android.internal.view.animation.NativeInterpolatorFactoryHelper;

@HasNativeInterpolator
public class AnticipateOvershootInterpolator
extends BaseInterpolator
implements NativeInterpolatorFactory {
    private final float mTension;

    public AnticipateOvershootInterpolator() {
        this.mTension = 3.0f;
    }

    public AnticipateOvershootInterpolator(float f) {
        this.mTension = 1.5f * f;
    }

    public AnticipateOvershootInterpolator(float f, float f2) {
        this.mTension = f * f2;
    }

    public AnticipateOvershootInterpolator(Context context, AttributeSet attributeSet) {
        this(context.getResources(), context.getTheme(), attributeSet);
    }

    public AnticipateOvershootInterpolator(Resources object, Resources.Theme theme, AttributeSet attributeSet) {
        object = theme != null ? theme.obtainStyledAttributes(attributeSet, R.styleable.AnticipateOvershootInterpolator, 0, 0) : ((Resources)object).obtainAttributes(attributeSet, R.styleable.AnticipateOvershootInterpolator);
        this.mTension = ((TypedArray)object).getFloat(0, 2.0f) * ((TypedArray)object).getFloat(1, 1.5f);
        this.setChangingConfiguration(((TypedArray)object).getChangingConfigurations());
        ((TypedArray)object).recycle();
    }

    private static float a(float f, float f2) {
        return f * f * ((1.0f + f2) * f - f2);
    }

    private static float o(float f, float f2) {
        return f * f * ((1.0f + f2) * f + f2);
    }

    @Override
    public long createNativeInterpolator() {
        return NativeInterpolatorFactoryHelper.createAnticipateOvershootInterpolator(this.mTension);
    }

    @Override
    public float getInterpolation(float f) {
        if (f < 0.5f) {
            return AnticipateOvershootInterpolator.a(2.0f * f, this.mTension) * 0.5f;
        }
        return (AnticipateOvershootInterpolator.o(f * 2.0f - 2.0f, this.mTension) + 2.0f) * 0.5f;
    }
}

