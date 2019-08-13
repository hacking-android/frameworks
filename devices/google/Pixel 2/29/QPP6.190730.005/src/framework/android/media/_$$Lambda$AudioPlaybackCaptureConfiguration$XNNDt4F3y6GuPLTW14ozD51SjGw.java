/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.media.-$
 *  android.media.-$$Lambda
 *  android.media.-$$Lambda$AudioPlaybackCaptureConfiguration
 *  android.media.-$$Lambda$AudioPlaybackCaptureConfiguration$XNNDt4F3y6GuPLTW14ozD51SjGw
 */
package android.media;

import android.media.-$;
import android.media.AudioPlaybackCaptureConfiguration;
import android.media.audiopolicy.AudioMixingRule;
import java.util.function.ToIntFunction;

public final class _$$Lambda$AudioPlaybackCaptureConfiguration$XNNDt4F3y6GuPLTW14ozD51SjGw
implements ToIntFunction {
    public static final /* synthetic */ -$.Lambda.AudioPlaybackCaptureConfiguration.XNNDt4F3y6GuPLTW14ozD51SjGw INSTANCE;

    static /* synthetic */ {
        INSTANCE = new _$$Lambda$AudioPlaybackCaptureConfiguration$XNNDt4F3y6GuPLTW14ozD51SjGw();
    }

    private /* synthetic */ _$$Lambda$AudioPlaybackCaptureConfiguration$XNNDt4F3y6GuPLTW14ozD51SjGw() {
    }

    public final int applyAsInt(Object object) {
        return AudioPlaybackCaptureConfiguration.lambda$getExcludeUsages$2((AudioMixingRule.AudioMixMatchCriterion)object);
    }
}

