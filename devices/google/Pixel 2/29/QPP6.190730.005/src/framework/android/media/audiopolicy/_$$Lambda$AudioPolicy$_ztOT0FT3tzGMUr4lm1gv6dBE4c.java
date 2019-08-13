/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.media.audiopolicy.-$
 *  android.media.audiopolicy.-$$Lambda
 *  android.media.audiopolicy.-$$Lambda$AudioPolicy
 *  android.media.audiopolicy.-$$Lambda$AudioPolicy$-ztOT0FT3tzGMUr4lm1gv6dBE4c
 */
package android.media.audiopolicy;

import android.media.audiopolicy.-$;
import android.media.audiopolicy.AudioMix;
import android.media.audiopolicy.AudioPolicy;
import java.util.function.Predicate;

public final class _$$Lambda$AudioPolicy$_ztOT0FT3tzGMUr4lm1gv6dBE4c
implements Predicate {
    public static final /* synthetic */ -$.Lambda.AudioPolicy.-ztOT0FT3tzGMUr4lm1gv6dBE4c INSTANCE;

    static /* synthetic */ {
        INSTANCE = new _$$Lambda$AudioPolicy$_ztOT0FT3tzGMUr4lm1gv6dBE4c();
    }

    private /* synthetic */ _$$Lambda$AudioPolicy$_ztOT0FT3tzGMUr4lm1gv6dBE4c() {
    }

    public final boolean test(Object object) {
        return AudioPolicy.lambda$isLoopbackRenderPolicy$0((AudioMix)object);
    }
}

