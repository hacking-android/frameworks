/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.os.IBinder
 *  android.os.RemoteException
 *  android.telephony.Rlog
 *  android.telephony.ims.ImsExternalCallState
 *  com.android.ims.ImsException
 *  com.android.ims.internal.IImsExternalCallStateListener
 *  com.android.ims.internal.IImsExternalCallStateListener$Stub
 *  com.android.ims.internal.IImsMultiEndpoint
 */
package com.android.ims;

import android.os.IBinder;
import android.os.RemoteException;
import android.telephony.Rlog;
import android.telephony.ims.ImsExternalCallState;
import com.android.ims.ImsException;
import com.android.ims.ImsExternalCallStateListener;
import com.android.ims.internal.IImsExternalCallStateListener;
import com.android.ims.internal.IImsMultiEndpoint;
import java.util.List;

public class ImsMultiEndpoint {
    private static final boolean DBG = true;
    private static final String TAG = "ImsMultiEndpoint";
    private final IImsMultiEndpoint mImsMultiendpoint;

    public ImsMultiEndpoint(IImsMultiEndpoint iImsMultiEndpoint) {
        Rlog.d((String)TAG, (String)"ImsMultiEndpoint created");
        this.mImsMultiendpoint = iImsMultiEndpoint;
    }

    public boolean isBinderAlive() {
        return this.mImsMultiendpoint.asBinder().isBinderAlive();
    }

    public void setExternalCallStateListener(ImsExternalCallStateListener imsExternalCallStateListener) throws ImsException {
        try {
            Rlog.d((String)TAG, (String)"setExternalCallStateListener");
            IImsMultiEndpoint iImsMultiEndpoint = this.mImsMultiendpoint;
            ImsExternalCallStateListenerProxy imsExternalCallStateListenerProxy = new ImsExternalCallStateListenerProxy(imsExternalCallStateListener);
            iImsMultiEndpoint.setListener((IImsExternalCallStateListener)imsExternalCallStateListenerProxy);
            return;
        }
        catch (RemoteException remoteException) {
            throw new ImsException("setExternalCallStateListener could not be set.", (Throwable)remoteException, 106);
        }
    }

    private class ImsExternalCallStateListenerProxy
    extends IImsExternalCallStateListener.Stub {
        private ImsExternalCallStateListener mListener;

        public ImsExternalCallStateListenerProxy(ImsExternalCallStateListener imsExternalCallStateListener) {
            this.mListener = imsExternalCallStateListener;
        }

        public void onImsExternalCallStateUpdate(List<ImsExternalCallState> list) {
            Rlog.d((String)ImsMultiEndpoint.TAG, (String)"onImsExternalCallStateUpdate");
            ImsExternalCallStateListener imsExternalCallStateListener = this.mListener;
            if (imsExternalCallStateListener != null) {
                imsExternalCallStateListener.onImsExternalCallStateUpdate(list);
            }
        }
    }

}

