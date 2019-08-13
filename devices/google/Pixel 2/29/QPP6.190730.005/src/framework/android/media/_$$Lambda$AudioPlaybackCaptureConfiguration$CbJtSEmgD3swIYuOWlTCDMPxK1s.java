/*
 * Decompiled with CFR 0.145.
 */
package android.media;

import android.media.AudioPlaybackCaptureConfiguration;
import android.media.audiopolicy.AudioMixingRule;
import java.util.function.Predicate;

public final class _$$Lambda$AudioPlaybackCaptureConfiguration$CbJtSEmgD3swIYuOWlTCDMPxK1s
implements Predicate {
    private final /* synthetic */ int f$0;

    public /* synthetic */ _$$Lambda$AudioPlaybackCaptureConfiguration$CbJtSEmgD3swIYuOWlTCDMPxK1s(int n) {
        this.f$0 = n;
    }

    public final boolean test(Object object) {
        return AudioPlaybackCaptureConfiguration.lambda$getIntPredicates$4(this.f$0, (AudioMixingRule.AudioMixMatchCriterion)object);
    }
}

