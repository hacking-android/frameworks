/*
 * Decompiled with CFR 0.145.
 */
package android.media.audiofx;

import android.media.audiofx.AudioEffect;
import android.util.Log;
import java.util.UUID;

public class NoiseSuppressor
extends AudioEffect {
    private static final String TAG = "NoiseSuppressor";

    private NoiseSuppressor(int n) throws IllegalArgumentException, UnsupportedOperationException, RuntimeException {
        super(EFFECT_TYPE_NS, EFFECT_TYPE_NULL, 0, n);
    }

    public static NoiseSuppressor create(int n) {
        Object var1_1 = null;
        NoiseSuppressor noiseSuppressor = null;
        try {
            NoiseSuppressor noiseSuppressor2;
            noiseSuppressor = noiseSuppressor2 = new NoiseSuppressor(n);
        }
        catch (RuntimeException runtimeException) {
            Log.w(TAG, "not enough memory");
            noiseSuppressor = var1_1;
        }
        catch (UnsupportedOperationException unsupportedOperationException) {
            Log.w(TAG, "not enough resources");
        }
        catch (IllegalArgumentException illegalArgumentException) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("not implemented on this device ");
            stringBuilder.append((Object)null);
            Log.w(TAG, stringBuilder.toString());
        }
        return noiseSuppressor;
    }

    public static boolean isAvailable() {
        return AudioEffect.isEffectTypeAvailable(AudioEffect.EFFECT_TYPE_NS);
    }
}

