/*
 * Decompiled with CFR 0.145.
 */
package android.telephony.mbms;

import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.List;

public interface MbmsGroupCallSessionCallback {
    default public void onAvailableSaisUpdated(List<Integer> list, List<List<Integer>> list2) {
    }

    default public void onError(int n, String string2) {
    }

    default public void onMiddlewareReady() {
    }

    default public void onServiceInterfaceAvailable(String string2, int n) {
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface GroupCallError {
    }

}

