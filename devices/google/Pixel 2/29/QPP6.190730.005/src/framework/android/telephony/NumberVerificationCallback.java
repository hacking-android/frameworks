/*
 * Decompiled with CFR 0.145.
 */
package android.telephony;

import android.annotation.SystemApi;
import java.lang.annotation.Annotation;

@SystemApi
public interface NumberVerificationCallback {
    public static final int REASON_CONCURRENT_REQUESTS = 4;
    public static final int REASON_IN_ECBM = 5;
    public static final int REASON_IN_EMERGENCY_CALL = 6;
    public static final int REASON_NETWORK_NOT_AVAILABLE = 2;
    public static final int REASON_TIMED_OUT = 1;
    public static final int REASON_TOO_MANY_CALLS = 3;
    public static final int REASON_UNSPECIFIED = 0;

    default public void onCallReceived(String string2) {
    }

    default public void onVerificationFailed(@NumberVerificationFailureReason int n) {
    }

    public static @interface NumberVerificationFailureReason {
    }

}

