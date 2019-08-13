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
public class DecelerateInterpolator
extends BaseInterpolator
implements NativeInterpolatorFactory {
    private float mFactor = 1.0f;

    public DecelerateInterpolator() {
    }

    public DecelerateInterpolator(float f) {
        this.mFactor = f;
    }

    public DecelerateInterpolator(Context context, AttributeSet attributeSet) {
        this(context.getResources(), context.getTheme(), attributeSet);
    }

    public DecelerateInterpolator(Resources object, Resources.Theme theme, AttributeSet attributeSet) {
        object = theme != null ? theme.obtainStyledAttributes(attributeSet, R.styleable.DecelerateInterpolator, 0, 0) : ((Resources)object).obtainAttributes(attributeSet, R.styleable.DecelerateInterpolator);
        this.mFactor = ((TypedArray)object).getFloat(0, 1.0f);
        this.setChangingConfiguration(((TypedArray)object).getChangingConfigurations());
        ((TypedArray)object).recycle();
    }

    @Override
    public long createNativeInterpolator() {
        return NativeInterpolatorFactoryHelper.createDecelerateInterpolator(this.mFactor);
    }

    @Override
    public float getInterpolation(float f) {
        float f2 = this.mFactor;
        f = f2 == 1.0f ? 1.0f - (1.0f - f) * (1.0f - f) : (float)(1.0 - Math.pow(1.0f - f, f2 * 2.0f));
        return f;
    }
}

