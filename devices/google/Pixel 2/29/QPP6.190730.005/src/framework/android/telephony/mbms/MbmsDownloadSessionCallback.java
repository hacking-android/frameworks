/*
 * Decompiled with CFR 0.145.
 */
package android.telephony.mbms;

import android.telephony.mbms.FileServiceInfo;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.List;

public class MbmsDownloadSessionCallback {
    public void onError(int n, String string2) {
    }

    public void onFileServicesUpdated(List<FileServiceInfo> list) {
    }

    public void onMiddlewareReady() {
    }

    @Retention(value=RetentionPolicy.SOURCE)
    private static @interface DownloadError {
    }

}

