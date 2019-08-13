/*
 * Decompiled with CFR 0.145.
 */
package android.view;

import android.animation.TimeInterpolator;
import android.graphics.RecordingCanvas;
import android.graphics.RenderNode;
import android.view.RenderNodeAnimator;
import com.android.internal.view.animation.FallbackLUTInterpolator;
import com.android.internal.view.animation.NativeInterpolatorFactory;
import com.android.internal.view.animation.NativeInterpolatorFactoryHelper;

public class RenderNodeAnimatorSetHelper {
    public static long createNativeInterpolator(TimeInterpolator timeInterpolator, long l) {
        if (timeInterpolator == null) {
            return NativeInterpolatorFactoryHelper.createLinearInterpolator();
        }
        if (RenderNodeAnimator.isNativeInterpolator(timeInterpolator)) {
            return ((NativeInterpolatorFactory)((Object)timeInterpolator)).createNativeInterpolator();
        }
        return FallbackLUTInterpolator.createNativeInterpolator(timeInterpolator, l);
    }

    public static RenderNode getTarget(RecordingCanvas recordingCanvas) {
        return recordingCanvas.mNode;
    }
}

