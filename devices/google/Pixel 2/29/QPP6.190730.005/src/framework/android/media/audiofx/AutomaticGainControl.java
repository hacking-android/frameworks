/*
 * Decompiled with CFR 0.145.
 */
package android.media.audiofx;

import android.media.audiofx.AudioEffect;
import android.util.Log;
import java.util.UUID;

public class AutomaticGainControl
extends AudioEffect {
    private static final String TAG = "AutomaticGainControl";

    private AutomaticGainControl(int n) throws IllegalArgumentException, UnsupportedOperationException, RuntimeException {
        super(EFFECT_TYPE_AGC, EFFECT_TYPE_NULL, 0, n);
    }

    public static AutomaticGainControl create(int n) {
        Object var1_1 = null;
        AutomaticGainControl automaticGainControl = null;
        try {
            AutomaticGainControl automaticGainControl2;
            automaticGainControl = automaticGainControl2 = new AutomaticGainControl(n);
        }
        catch (RuntimeException runtimeException) {
            Log.w(TAG, "not enough memory");
            automaticGainControl = var1_1;
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
        return automaticGainControl;
    }

    public static boolean isAvailable() {
        return AudioEffect.isEffectTypeAvailable(AudioEffect.EFFECT_TYPE_AGC);
    }
}

