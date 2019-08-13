/*
 * Decompiled with CFR 0.145.
 */
package android.telephony.ims.compat.stub;

import android.os.Bundle;
import android.os.RemoteException;
import android.telephony.ims.ImsCallForwardInfo;
import android.telephony.ims.ImsReasonInfo;
import android.telephony.ims.ImsSsData;
import android.telephony.ims.ImsSsInfo;
import com.android.ims.internal.IImsUt;
import com.android.ims.internal.IImsUtListener;

public class ImsUtListenerImplBase
extends IImsUtListener.Stub {
    @Override
    public void onSupplementaryServiceIndication(ImsSsData imsSsData) {
    }

    @Override
    public void utConfigurationCallBarringQueried(IImsUt iImsUt, int n, ImsSsInfo[] arrimsSsInfo) throws RemoteException {
    }

    @Override
    public void utConfigurationCallForwardQueried(IImsUt iImsUt, int n, ImsCallForwardInfo[] arrimsCallForwardInfo) throws RemoteException {
    }

    @Override
    public void utConfigurationCallWaitingQueried(IImsUt iImsUt, int n, ImsSsInfo[] arrimsSsInfo) throws RemoteException {
    }

    @Override
    public void utConfigurationQueried(IImsUt iImsUt, int n, Bundle bundle) throws RemoteException {
    }

    @Override
    public void utConfigurationQueryFailed(IImsUt iImsUt, int n, ImsReasonInfo imsReasonInfo) throws RemoteException {
    }

    @Override
    public void utConfigurationUpdateFailed(IImsUt iImsUt, int n, ImsReasonInfo imsReasonInfo) throws RemoteException {
    }

    @Override
    public void utConfigurationUpdated(IImsUt iImsUt, int n) throws RemoteException {
    }
}

