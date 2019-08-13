/*
 * Decompiled with CFR 0.145.
 */
package android.view.animation;

import android.content.Context;
import android.util.AttributeSet;
import android.view.animation.BaseInterpolator;
import com.android.internal.view.animation.HasNativeInterpolator;
import com.android.internal.view.animation.NativeInterpolatorFactory;
import com.android.internal.view.animation.NativeInterpolatorFactoryHelper;

@HasNativeInterpolator
public class AccelerateDecelerateInterpolator
extends BaseInterpolator
implements NativeInterpolatorFactory {
    public AccelerateDecelerateInterpolator() {
    }

    public AccelerateDecelerateInterpolator(Context context, AttributeSet attributeSet) {
    }

    @Override
    public long createNativeInterpolator() {
        return NativeInterpolatorFactoryHelper.createAccelerateDecelerateInterpolator();
    }

    @Override
    public float getInterpolation(float f) {
        return (float)(Math.cos((double)(1.0f + f) * 3.141592653589793) / 2.0) + 0.5f;
    }
}

