/*
 * Decompiled with CFR 0.145.
 */
package android.net.wifi;

import android.annotation.SystemApi;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@SystemApi
public abstract class EasyConnectStatusCallback {
    public static final int EASY_CONNECT_EVENT_FAILURE_AUTHENTICATION = -2;
    public static final int EASY_CONNECT_EVENT_FAILURE_BUSY = -5;
    public static final int EASY_CONNECT_EVENT_FAILURE_CONFIGURATION = -4;
    public static final int EASY_CONNECT_EVENT_FAILURE_GENERIC = -7;
    public static final int EASY_CONNECT_EVENT_FAILURE_INVALID_NETWORK = -9;
    public static final int EASY_CONNECT_EVENT_FAILURE_INVALID_URI = -1;
    public static final int EASY_CONNECT_EVENT_FAILURE_NOT_COMPATIBLE = -3;
    public static final int EASY_CONNECT_EVENT_FAILURE_NOT_SUPPORTED = -8;
    public static final int EASY_CONNECT_EVENT_FAILURE_TIMEOUT = -6;
    public static final int EASY_CONNECT_EVENT_PROGRESS_AUTHENTICATION_SUCCESS = 0;
    public static final int EASY_CONNECT_EVENT_PROGRESS_RESPONSE_PENDING = 1;
    public static final int EASY_CONNECT_EVENT_SUCCESS_CONFIGURATION_SENT = 0;

    public abstract void onConfiguratorSuccess(int var1);

    public abstract void onEnrolleeSuccess(int var1);

    public abstract void onFailure(int var1);

    public abstract void onProgress(int var1);

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface EasyConnectFailureStatusCode {
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface EasyConnectProgressStatusCode {
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface EasyConnectSuccessStatusCode {
    }

}

