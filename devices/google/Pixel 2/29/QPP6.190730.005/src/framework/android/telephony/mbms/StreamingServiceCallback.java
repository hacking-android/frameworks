/*
 * Decompiled with CFR 0.145.
 */
package android.telephony.mbms;

import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class StreamingServiceCallback {
    public static final int SIGNAL_STRENGTH_UNAVAILABLE = -1;

    public void onBroadcastSignalStrengthUpdated(int n) {
    }

    public void onError(int n, String string2) {
    }

    public void onMediaDescriptionUpdated() {
    }

    public void onStreamMethodUpdated(int n) {
    }

    public void onStreamStateUpdated(int n, int n2) {
    }

    @Retention(value=RetentionPolicy.SOURCE)
    private static @interface StreamingServiceError {
    }

}

