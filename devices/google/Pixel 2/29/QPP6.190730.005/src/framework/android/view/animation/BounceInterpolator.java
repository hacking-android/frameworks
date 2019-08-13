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
public class BounceInterpolator
extends BaseInterpolator
implements NativeInterpolatorFactory {
    public BounceInterpolator() {
    }

    public BounceInterpolator(Context context, AttributeSet attributeSet) {
    }

    private static float bounce(float f) {
        return f * f * 8.0f;
    }

    @Override
    public long createNativeInterpolator() {
        return NativeInterpolatorFactoryHelper.createBounceInterpolator();
    }

    @Override
    public float getInterpolation(float f) {
        if ((f *= 1.1226f) < 0.3535f) {
            return BounceInterpolator.bounce(f);
        }
        if (f < 0.7408f) {
            return BounceInterpolator.bounce(f - 0.54719f) + 0.7f;
        }
        if (f < 0.9644f) {
            return BounceInterpolator.bounce(f - 0.8526f) + 0.9f;
        }
        return BounceInterpolator.bounce(f - 1.0435f) + 0.95f;
    }
}

