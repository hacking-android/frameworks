/*
 * Decompiled with CFR 0.145.
 */
package android.telephony.mbms;

import android.telephony.mbms.StreamingServiceInfo;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.List;

public class MbmsStreamingSessionCallback {
    public void onError(int n, String string2) {
    }

    public void onMiddlewareReady() {
    }

    public void onStreamingServicesUpdated(List<StreamingServiceInfo> list) {
    }

    @Retention(value=RetentionPolicy.SOURCE)
    private static @interface StreamingError {
    }

}

