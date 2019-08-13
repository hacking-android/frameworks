/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.os.IBinder
 *  android.os.RemoteException
 *  android.telephony.Rlog
 *  com.android.ims.ImsException
 *  com.android.ims.internal.IImsEcbm
 *  com.android.ims.internal.IImsEcbmListener
 *  com.android.ims.internal.IImsEcbmListener$Stub
 */
package com.android.ims;

import android.os.IBinder;
import android.os.RemoteException;
import android.telephony.Rlog;
import com.android.ims.ImsEcbmStateListener;
import com.android.ims.ImsException;
import com.android.ims.internal.IImsEcbm;
import com.android.ims.internal.IImsEcbmListener;

public class ImsEcbm {
    private static final boolean DBG = true;
    private static final String TAG = "ImsEcbm";
    private final IImsEcbm miEcbm;

    public ImsEcbm(IImsEcbm iImsEcbm) {
        Rlog.d((String)TAG, (String)"ImsEcbm created");
        this.miEcbm = iImsEcbm;
    }

    public void exitEmergencyCallbackMode() throws ImsException {
        try {
            this.miEcbm.exitEmergencyCallbackMode();
            return;
        }
        catch (RemoteException remoteException) {
            throw new ImsException("exitEmergencyCallbackMode()", (Throwable)remoteException, 106);
        }
    }

    public boolean isBinderAlive() {
        return this.miEcbm.asBinder().isBinderAlive();
    }

    public void setEcbmStateListener(ImsEcbmStateListener imsEcbmStateListener) throws ImsException {
        try {
            IImsEcbm iImsEcbm = this.miEcbm;
            ImsEcbmListenerProxy imsEcbmListenerProxy = new ImsEcbmListenerProxy(imsEcbmStateListener);
            iImsEcbm.setListener((IImsEcbmListener)imsEcbmListenerProxy);
            return;
        }
        catch (RemoteException remoteException) {
            throw new ImsException("setEcbmStateListener()", (Throwable)remoteException, 106);
        }
    }

    private class ImsEcbmListenerProxy
    extends IImsEcbmListener.Stub {
        private ImsEcbmStateListener mListener;

        public ImsEcbmListenerProxy(ImsEcbmStateListener imsEcbmStateListener) {
            this.mListener = imsEcbmStateListener;
        }

        public void enteredECBM() {
            Rlog.d((String)ImsEcbm.TAG, (String)"enteredECBM ::");
            ImsEcbmStateListener imsEcbmStateListener = this.mListener;
            if (imsEcbmStateListener != null) {
                imsEcbmStateListener.onECBMEntered();
            }
        }

        public void exitedECBM() {
            Rlog.d((String)ImsEcbm.TAG, (String)"exitedECBM ::");
            ImsEcbmStateListener imsEcbmStateListener = this.mListener;
            if (imsEcbmStateListener != null) {
                imsEcbmStateListener.onECBMExited();
            }
        }
    }

}

