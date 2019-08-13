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
public class AnticipateInterpolator
extends BaseInterpolator
implements NativeInterpolatorFactory {
    private final float mTension;

    public AnticipateInterpolator() {
        this.mTension = 2.0f;
    }

    public AnticipateInterpolator(float f) {
        this.mTension = f;
    }

    public AnticipateInterpolator(Context context, AttributeSet attributeSet) {
        this(context.getResources(), context.getTheme(), attributeSet);
    }

    public AnticipateInterpolator(Resources object, Resources.Theme theme, AttributeSet attributeSet) {
        object = theme != null ? theme.obtainStyledAttributes(attributeSet, R.styleable.AnticipateInterpolator, 0, 0) : ((Resources)object).obtainAttributes(attributeSet, R.styleable.AnticipateInterpolator);
        this.mTension = ((TypedArray)object).getFloat(0, 2.0f);
        this.setChangingConfiguration(((TypedArray)object).getChangingConfigurations());
        ((TypedArray)object).recycle();
    }

    @Override
    public long createNativeInterpolator() {
        return NativeInterpolatorFactoryHelper.createAnticipateInterpolator(this.mTension);
    }

    @Override
    public float getInterpolation(float f) {
        float f2 = this.mTension;
        return f * f * ((1.0f + f2) * f - f2);
    }
}

