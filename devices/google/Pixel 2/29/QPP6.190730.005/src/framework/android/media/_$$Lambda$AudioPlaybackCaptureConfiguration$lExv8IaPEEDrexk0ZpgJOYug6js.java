/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.media.-$
 *  android.media.-$$Lambda
 *  android.media.-$$Lambda$AudioPlaybackCaptureConfiguration
 *  android.media.-$$Lambda$AudioPlaybackCaptureConfiguration$lExv8IaPEEDrexk0ZpgJOYug6js
 */
package android.media;

import android.media.-$;
import android.media.AudioPlaybackCaptureConfiguration;
import android.media.audiopolicy.AudioMixingRule;
import java.util.function.ToIntFunction;

public final class _$$Lambda$AudioPlaybackCaptureConfiguration$lExv8IaPEEDrexk0ZpgJOYug6js
implements ToIntFunction {
    public static final /* synthetic */ -$.Lambda.AudioPlaybackCaptureConfiguration.lExv8IaPEEDrexk0ZpgJOYug6js INSTANCE;

    static /* synthetic */ {
        INSTANCE = new _$$Lambda$AudioPlaybackCaptureConfiguration$lExv8IaPEEDrexk0ZpgJOYug6js();
    }

    private /* synthetic */ _$$Lambda$AudioPlaybackCaptureConfiguration$lExv8IaPEEDrexk0ZpgJOYug6js() {
    }

    public final int applyAsInt(Object object) {
        return AudioPlaybackCaptureConfiguration.lambda$getMatchingUids$1((AudioMixingRule.AudioMixMatchCriterion)object);
    }
}

