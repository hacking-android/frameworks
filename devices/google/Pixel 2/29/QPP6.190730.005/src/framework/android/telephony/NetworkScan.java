/*
 * Decompiled with CFR 0.145.
 */
package android.telephony;

import android.os.RemoteException;
import android.os.ServiceManager;
import android.telephony.Rlog;
import com.android.internal.telephony.ITelephony;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class NetworkScan {
    public static final int ERROR_INTERRUPTED = 10002;
    public static final int ERROR_INVALID_SCAN = 2;
    public static final int ERROR_INVALID_SCANID = 10001;
    public static final int ERROR_MODEM_ERROR = 1;
    public static final int ERROR_MODEM_UNAVAILABLE = 3;
    public static final int ERROR_RADIO_INTERFACE_ERROR = 10000;
    public static final int ERROR_UNSUPPORTED = 4;
    public static final int SUCCESS = 0;
    private static final String TAG = "NetworkScan";
    private final int mScanId;
    private final int mSubId;

    public NetworkScan(int n, int n2) {
        this.mScanId = n;
        this.mSubId = n2;
    }

    private ITelephony getITelephony() {
        return ITelephony.Stub.asInterface(ServiceManager.getService("phone"));
    }

    @Deprecated
    public void stop() throws RemoteException {
        try {
            this.stopScan();
            return;
        }
        catch (RuntimeException runtimeException) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Failed to stop the network scan with id ");
            stringBuilder.append(this.mScanId);
            throw new RemoteException(stringBuilder.toString());
        }
    }

    public void stopScan() {
        ITelephony iTelephony = this.getITelephony();
        if (iTelephony == null) {
            Rlog.e(TAG, "Failed to get the ITelephony instance.");
        }
        try {
            iTelephony.stopNetworkScan(this.mSubId, this.mScanId);
        }
        catch (RuntimeException runtimeException) {
            Rlog.e(TAG, "stopNetworkScan  RuntimeException", runtimeException);
        }
        catch (RemoteException remoteException) {
            Rlog.e(TAG, "stopNetworkScan  RemoteException", remoteException);
        }
        catch (IllegalArgumentException illegalArgumentException) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("stopNetworkScan - no active scan for ScanID=");
            stringBuilder.append(this.mScanId);
            Rlog.d(TAG, stringBuilder.toString());
        }
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface ScanErrorCode {
    }

}

