/*
 * Decompiled with CFR 0.145.
 */
package android.media.audiofx;

import android.media.audiofx.AudioEffect;
import android.util.Log;
import java.util.UUID;

public class AcousticEchoCanceler
extends AudioEffect {
    private static final String TAG = "AcousticEchoCanceler";

    private AcousticEchoCanceler(int n) throws IllegalArgumentException, UnsupportedOperationException, RuntimeException {
        super(EFFECT_TYPE_AEC, EFFECT_TYPE_NULL, 0, n);
    }

    public static AcousticEchoCanceler create(int n) {
        Object var1_1 = null;
        AcousticEchoCanceler acousticEchoCanceler = null;
        try {
            AcousticEchoCanceler acousticEchoCanceler2;
            acousticEchoCanceler = acousticEchoCanceler2 = new AcousticEchoCanceler(n);
        }
        catch (RuntimeException runtimeException) {
            Log.w(TAG, "not enough memory");
            acousticEchoCanceler = var1_1;
        }
        catch (UnsupportedOperationException unsupportedOperationException) {
            Log.w(TAG, "not enough resources");
        }
        catch (IllegalArgumentException illegalArgumentException) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("not implemented on this device");
            stringBuilder.append((Object)null);
            Log.w(TAG, stringBuilder.toString());
        }
        return acousticEchoCanceler;
    }

    public static boolean isAvailable() {
        return AudioEffect.isEffectTypeAvailable(AudioEffect.EFFECT_TYPE_AEC);
    }
}

