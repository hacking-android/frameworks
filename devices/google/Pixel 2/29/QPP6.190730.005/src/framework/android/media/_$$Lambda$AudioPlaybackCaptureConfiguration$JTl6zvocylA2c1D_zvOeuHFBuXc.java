/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.media.-$
 *  android.media.-$$Lambda
 *  android.media.-$$Lambda$AudioPlaybackCaptureConfiguration
 *  android.media.-$$Lambda$AudioPlaybackCaptureConfiguration$JTl6zvocylA2c1D_zvOeuHFBuXc
 */
package android.media;

import android.media.-$;
import android.media.AudioPlaybackCaptureConfiguration;
import android.media.audiopolicy.AudioMixingRule;
import java.util.function.ToIntFunction;

public final class _$$Lambda$AudioPlaybackCaptureConfiguration$JTl6zvocylA2c1D_zvOeuHFBuXc
implements ToIntFunction {
    public static final /* synthetic */ -$.Lambda.AudioPlaybackCaptureConfiguration.JTl6zvocylA2c1D_zvOeuHFBuXc INSTANCE;

    static /* synthetic */ {
        INSTANCE = new _$$Lambda$AudioPlaybackCaptureConfiguration$JTl6zvocylA2c1D_zvOeuHFBuXc();
    }

    private /* synthetic */ _$$Lambda$AudioPlaybackCaptureConfiguration$JTl6zvocylA2c1D_zvOeuHFBuXc() {
    }

    public final int applyAsInt(Object object) {
        return AudioPlaybackCaptureConfiguration.lambda$getMatchingUsages$0((AudioMixingRule.AudioMixMatchCriterion)object);
    }
}

