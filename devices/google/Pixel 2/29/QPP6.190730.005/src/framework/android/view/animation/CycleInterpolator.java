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
public class CycleInterpolator
extends BaseInterpolator
implements NativeInterpolatorFactory {
    private float mCycles;

    public CycleInterpolator(float f) {
        this.mCycles = f;
    }

    public CycleInterpolator(Context context, AttributeSet attributeSet) {
        this(context.getResources(), context.getTheme(), attributeSet);
    }

    public CycleInterpolator(Resources object, Resources.Theme theme, AttributeSet attributeSet) {
        object = theme != null ? theme.obtainStyledAttributes(attributeSet, R.styleable.CycleInterpolator, 0, 0) : ((Resources)object).obtainAttributes(attributeSet, R.styleable.CycleInterpolator);
        this.mCycles = ((TypedArray)object).getFloat(0, 1.0f);
        this.setChangingConfiguration(((TypedArray)object).getChangingConfigurations());
        ((TypedArray)object).recycle();
    }

    @Override
    public long createNativeInterpolator() {
        return NativeInterpolatorFactoryHelper.createCycleInterpolator(this.mCycles);
    }

    @Override
    public float getInterpolation(float f) {
        return (float)Math.sin((double)(this.mCycles * 2.0f) * 3.141592653589793 * (double)f);
    }
}

