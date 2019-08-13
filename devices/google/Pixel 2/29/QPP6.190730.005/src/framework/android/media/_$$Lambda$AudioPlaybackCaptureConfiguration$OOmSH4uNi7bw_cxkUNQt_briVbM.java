/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.media.-$
 *  android.media.-$$Lambda
 *  android.media.-$$Lambda$AudioPlaybackCaptureConfiguration
 *  android.media.-$$Lambda$AudioPlaybackCaptureConfiguration$OOmSH4uNi7bw-cxkUNQt_briVbM
 */
package android.media;

import android.media.-$;
import android.media.AudioPlaybackCaptureConfiguration;
import android.media.audiopolicy.AudioMixingRule;
import java.util.function.ToIntFunction;

public final class _$$Lambda$AudioPlaybackCaptureConfiguration$OOmSH4uNi7bw_cxkUNQt_briVbM
implements ToIntFunction {
    public static final /* synthetic */ -$.Lambda.AudioPlaybackCaptureConfiguration.OOmSH4uNi7bw-cxkUNQt_briVbM INSTANCE;

    static /* synthetic */ {
        INSTANCE = new _$$Lambda$AudioPlaybackCaptureConfiguration$OOmSH4uNi7bw_cxkUNQt_briVbM();
    }

    private /* synthetic */ _$$Lambda$AudioPlaybackCaptureConfiguration$OOmSH4uNi7bw_cxkUNQt_briVbM() {
    }

    public final int applyAsInt(Object object) {
        return AudioPlaybackCaptureConfiguration.lambda$getExcludeUids$3((AudioMixingRule.AudioMixMatchCriterion)object);
    }
}

