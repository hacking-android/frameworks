/*
 * Decompiled with CFR 0.145.
 */
package android.telephony.ims.stub;

import android.annotation.SystemApi;
import android.os.RemoteException;
import android.util.Log;
import com.android.ims.internal.IImsEcbm;
import com.android.ims.internal.IImsEcbmListener;

@SystemApi
public class ImsEcbmImplBase {
    private static final String TAG = "ImsEcbmImplBase";
    private IImsEcbm mImsEcbm = new IImsEcbm.Stub(){

        @Override
        public void exitEmergencyCallbackMode() {
            ImsEcbmImplBase.this.exitEmergencyCallbackMode();
        }

        @Override
        public void setListener(IImsEcbmListener iImsEcbmListener) {
            ImsEcbmImplBase.this.mListener = iImsEcbmListener;
        }
    };
    private IImsEcbmListener mListener;

    public final void enteredEcbm() {
        Log.d(TAG, "Entered ECBM.");
        IImsEcbmListener iImsEcbmListener = this.mListener;
        if (iImsEcbmListener != null) {
            try {
                iImsEcbmListener.enteredECBM();
            }
            catch (RemoteException remoteException) {
                throw new RuntimeException(remoteException);
            }
        }
    }

    public void exitEmergencyCallbackMode() {
        Log.d(TAG, "exitEmergencyCallbackMode() not implemented");
    }

    public final void exitedEcbm() {
        Log.d(TAG, "Exited ECBM.");
        IImsEcbmListener iImsEcbmListener = this.mListener;
        if (iImsEcbmListener != null) {
            try {
                iImsEcbmListener.exitedECBM();
            }
            catch (RemoteException remoteException) {
                throw new RuntimeException(remoteException);
            }
        }
    }

    public IImsEcbm getImsEcbm() {
        return this.mImsEcbm;
    }

}

