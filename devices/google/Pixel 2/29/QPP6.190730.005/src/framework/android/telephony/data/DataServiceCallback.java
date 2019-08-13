/*
 * Decompiled with CFR 0.145.
 */
package android.telephony.data;

import android.annotation.SystemApi;
import android.os.RemoteException;
import android.telephony.Rlog;
import android.telephony.data.DataCallResponse;
import android.telephony.data.IDataServiceCallback;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.ref.WeakReference;
import java.util.List;

@SystemApi
public class DataServiceCallback {
    public static final int RESULT_ERROR_BUSY = 3;
    public static final int RESULT_ERROR_ILLEGAL_STATE = 4;
    public static final int RESULT_ERROR_INVALID_ARG = 2;
    public static final int RESULT_ERROR_UNSUPPORTED = 1;
    public static final int RESULT_SUCCESS = 0;
    private static final String TAG = DataServiceCallback.class.getSimpleName();
    private final WeakReference<IDataServiceCallback> mCallback;

    public DataServiceCallback(IDataServiceCallback iDataServiceCallback) {
        this.mCallback = new WeakReference<IDataServiceCallback>(iDataServiceCallback);
    }

    public void onDataCallListChanged(List<DataCallResponse> list) {
        IDataServiceCallback iDataServiceCallback = (IDataServiceCallback)this.mCallback.get();
        if (iDataServiceCallback != null) {
            try {
                iDataServiceCallback.onDataCallListChanged(list);
            }
            catch (RemoteException remoteException) {
                Rlog.e(TAG, "Failed to onDataCallListChanged on the remote");
            }
        }
    }

    public void onDeactivateDataCallComplete(int n) {
        IDataServiceCallback iDataServiceCallback = (IDataServiceCallback)this.mCallback.get();
        if (iDataServiceCallback != null) {
            try {
                iDataServiceCallback.onDeactivateDataCallComplete(n);
            }
            catch (RemoteException remoteException) {
                Rlog.e(TAG, "Failed to onDeactivateDataCallComplete on the remote");
            }
        }
    }

    public void onRequestDataCallListComplete(int n, List<DataCallResponse> list) {
        IDataServiceCallback iDataServiceCallback = (IDataServiceCallback)this.mCallback.get();
        if (iDataServiceCallback != null) {
            try {
                iDataServiceCallback.onRequestDataCallListComplete(n, list);
            }
            catch (RemoteException remoteException) {
                Rlog.e(TAG, "Failed to onRequestDataCallListComplete on the remote");
            }
        }
    }

    public void onSetDataProfileComplete(int n) {
        IDataServiceCallback iDataServiceCallback = (IDataServiceCallback)this.mCallback.get();
        if (iDataServiceCallback != null) {
            try {
                iDataServiceCallback.onSetDataProfileComplete(n);
            }
            catch (RemoteException remoteException) {
                Rlog.e(TAG, "Failed to onSetDataProfileComplete on the remote");
            }
        }
    }

    public void onSetInitialAttachApnComplete(int n) {
        IDataServiceCallback iDataServiceCallback = (IDataServiceCallback)this.mCallback.get();
        if (iDataServiceCallback != null) {
            try {
                iDataServiceCallback.onSetInitialAttachApnComplete(n);
            }
            catch (RemoteException remoteException) {
                Rlog.e(TAG, "Failed to onSetInitialAttachApnComplete on the remote");
            }
        }
    }

    public void onSetupDataCallComplete(int n, DataCallResponse dataCallResponse) {
        IDataServiceCallback iDataServiceCallback = (IDataServiceCallback)this.mCallback.get();
        if (iDataServiceCallback != null) {
            try {
                iDataServiceCallback.onSetupDataCallComplete(n, dataCallResponse);
            }
            catch (RemoteException remoteException) {
                Rlog.e(TAG, "Failed to onSetupDataCallComplete on the remote");
            }
        }
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface ResultCode {
    }

}

