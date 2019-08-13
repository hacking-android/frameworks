/*
 * Decompiled with CFR 0.145.
 */
package android.telephony.mbms;

import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public interface GroupCallCallback {
    public static final int SIGNAL_STRENGTH_UNAVAILABLE = -1;

    default public void onBroadcastSignalStrengthUpdated(int n) {
    }

    default public void onError(int n, String string2) {
    }

    default public void onGroupCallStateChanged(int n, int n2) {
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface GroupCallError {
    }

}

