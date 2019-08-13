/*
 * Decompiled with CFR 0.145.
 */
package android.telephony;

import android.annotation.SystemApi;
import android.os.RemoteException;
import android.telephony.INetworkServiceCallback;
import android.telephony.NetworkRegistrationInfo;
import android.telephony.Rlog;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.ref.WeakReference;

@SystemApi
public class NetworkServiceCallback {
    public static final int RESULT_ERROR_BUSY = 3;
    public static final int RESULT_ERROR_FAILED = 5;
    public static final int RESULT_ERROR_ILLEGAL_STATE = 4;
    public static final int RESULT_ERROR_INVALID_ARG = 2;
    public static final int RESULT_ERROR_UNSUPPORTED = 1;
    public static final int RESULT_SUCCESS = 0;
    private static final String mTag = NetworkServiceCallback.class.getSimpleName();
    private final WeakReference<INetworkServiceCallback> mCallback;

    public NetworkServiceCallback(INetworkServiceCallback iNetworkServiceCallback) {
        this.mCallback = new WeakReference<INetworkServiceCallback>(iNetworkServiceCallback);
    }

    public void onRequestNetworkRegistrationInfoComplete(int n, NetworkRegistrationInfo networkRegistrationInfo) {
        INetworkServiceCallback iNetworkServiceCallback = (INetworkServiceCallback)this.mCallback.get();
        if (iNetworkServiceCallback != null) {
            try {
                iNetworkServiceCallback.onRequestNetworkRegistrationInfoComplete(n, networkRegistrationInfo);
            }
            catch (RemoteException remoteException) {
                Rlog.e(mTag, "Failed to onRequestNetworkRegistrationInfoComplete on the remote");
            }
        } else {
            Rlog.e(mTag, "Weak reference of callback is null.");
        }
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface Result {
    }

}

