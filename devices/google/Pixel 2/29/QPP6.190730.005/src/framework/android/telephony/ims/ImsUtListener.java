/*
 * Decompiled with CFR 0.145.
 */
package android.telephony.ims;

import android.annotation.SystemApi;
import android.os.Bundle;
import android.os.RemoteException;
import android.telephony.ims.ImsCallForwardInfo;
import android.telephony.ims.ImsReasonInfo;
import android.telephony.ims.ImsSsData;
import android.telephony.ims.ImsSsInfo;
import android.util.Log;
import com.android.ims.internal.IImsUt;
import com.android.ims.internal.IImsUtListener;

@SystemApi
public class ImsUtListener {
    private static final String LOG_TAG = "ImsUtListener";
    private IImsUtListener mServiceInterface;

    public ImsUtListener(IImsUtListener iImsUtListener) {
        this.mServiceInterface = iImsUtListener;
    }

    public void onSupplementaryServiceIndication(ImsSsData imsSsData) {
        try {
            this.mServiceInterface.onSupplementaryServiceIndication(imsSsData);
        }
        catch (RemoteException remoteException) {
            Log.w(LOG_TAG, "onSupplementaryServiceIndication: remote exception");
        }
    }

    public void onUtConfigurationCallBarringQueried(int n, ImsSsInfo[] arrimsSsInfo) {
        try {
            this.mServiceInterface.utConfigurationCallBarringQueried(null, n, arrimsSsInfo);
        }
        catch (RemoteException remoteException) {
            Log.w(LOG_TAG, "utConfigurationCallBarringQueried: remote exception");
        }
    }

    public void onUtConfigurationCallForwardQueried(int n, ImsCallForwardInfo[] arrimsCallForwardInfo) {
        try {
            this.mServiceInterface.utConfigurationCallForwardQueried(null, n, arrimsCallForwardInfo);
        }
        catch (RemoteException remoteException) {
            Log.w(LOG_TAG, "utConfigurationCallForwardQueried: remote exception");
        }
    }

    public void onUtConfigurationCallWaitingQueried(int n, ImsSsInfo[] arrimsSsInfo) {
        try {
            this.mServiceInterface.utConfigurationCallWaitingQueried(null, n, arrimsSsInfo);
        }
        catch (RemoteException remoteException) {
            Log.w(LOG_TAG, "utConfigurationCallWaitingQueried: remote exception");
        }
    }

    public void onUtConfigurationQueried(int n, Bundle bundle) {
        try {
            this.mServiceInterface.utConfigurationQueried(null, n, bundle);
        }
        catch (RemoteException remoteException) {
            Log.w(LOG_TAG, "utConfigurationQueried: remote exception");
        }
    }

    public void onUtConfigurationQueryFailed(int n, ImsReasonInfo imsReasonInfo) {
        try {
            this.mServiceInterface.utConfigurationQueryFailed(null, n, imsReasonInfo);
        }
        catch (RemoteException remoteException) {
            Log.w(LOG_TAG, "utConfigurationQueryFailed: remote exception");
        }
    }

    public void onUtConfigurationUpdateFailed(int n, ImsReasonInfo imsReasonInfo) {
        try {
            this.mServiceInterface.utConfigurationUpdateFailed(null, n, imsReasonInfo);
        }
        catch (RemoteException remoteException) {
            Log.w(LOG_TAG, "utConfigurationUpdateFailed: remote exception");
        }
    }

    public void onUtConfigurationUpdated(int n) {
        try {
            this.mServiceInterface.utConfigurationUpdated(null, n);
        }
        catch (RemoteException remoteException) {
            Log.w(LOG_TAG, "utConfigurationUpdated: remote exception");
        }
    }
}

