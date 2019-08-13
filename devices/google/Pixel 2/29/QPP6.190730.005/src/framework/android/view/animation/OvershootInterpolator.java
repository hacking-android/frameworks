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
public class OvershootInterpolator
extends BaseInterpolator
implements NativeInterpolatorFactory {
    private final float mTension;

    public OvershootInterpolator() {
        this.mTension = 2.0f;
    }

    public OvershootInterpolator(float f) {
        this.mTension = f;
    }

    public OvershootInterpolator(Context context, AttributeSet attributeSet) {
        this(context.getResources(), context.getTheme(), attributeSet);
    }

    public OvershootInterpolator(Resources object, Resources.Theme theme, AttributeSet attributeSet) {
        object = theme != null ? theme.obtainStyledAttributes(attributeSet, R.styleable.OvershootInterpolator, 0, 0) : ((Resources)object).obtainAttributes(attributeSet, R.styleable.OvershootInterpolator);
        this.mTension = ((TypedArray)object).getFloat(0, 2.0f);
        this.setChangingConfiguration(((TypedArray)object).getChangingConfigurations());
        ((TypedArray)object).recycle();
    }

    @Override
    public long createNativeInterpolator() {
        return NativeInterpolatorFactoryHelper.createOvershootInterpolator(this.mTension);
    }

    @Override
    public float getInterpolation(float f) {
        float f2 = this.mTension;
        return (f -= 1.0f) * f * ((f2 + 1.0f) * f + f2) + 1.0f;
    }
}

