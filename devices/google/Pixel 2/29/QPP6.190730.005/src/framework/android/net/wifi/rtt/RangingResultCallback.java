/*
 * Decompiled with CFR 0.145.
 */
package android.net.wifi.rtt;

import android.net.wifi.rtt.RangingResult;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.List;

public abstract class RangingResultCallback {
    public static final int STATUS_CODE_FAIL = 1;
    public static final int STATUS_CODE_FAIL_RTT_NOT_AVAILABLE = 2;

    public abstract void onRangingFailure(int var1);

    public abstract void onRangingResults(List<RangingResult> var1);

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface RangingOperationStatus {
    }

}

