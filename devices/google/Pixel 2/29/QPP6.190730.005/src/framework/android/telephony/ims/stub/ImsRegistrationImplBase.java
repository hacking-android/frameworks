/*
 * Decompiled with CFR 0.145.
 */
package android.telephony.ims.stub;

import android.annotation.SystemApi;
import android.net.Uri;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.telephony.ims.ImsReasonInfo;
import android.telephony.ims.aidl.IImsRegistration;
import android.telephony.ims.aidl.IImsRegistrationCallback;
import android.telephony.ims.stub._$$Lambda$ImsRegistrationImplBase$cWwTXSDsk_bWPbsDJYI__DUBMnE;
import android.telephony.ims.stub._$$Lambda$ImsRegistrationImplBase$s7PspXVbCf1Q_WSzodP2glP9TjI;
import android.telephony.ims.stub._$$Lambda$ImsRegistrationImplBase$sbjuTvW_brOSWMR74UInSZEIQB0;
import android.telephony.ims.stub._$$Lambda$ImsRegistrationImplBase$wDtW65cPmn_jF6dfimhBTfdg1kI;
import android.telephony.ims.stub._$$Lambda$ImsRegistrationImplBase$wwtkoeOtGwMjG5I0_ZTfjNpGU_s;
import android.util.Log;
import com.android.internal.annotations.VisibleForTesting;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.function.Consumer;

@SystemApi
public class ImsRegistrationImplBase {
    private static final String LOG_TAG = "ImsRegistrationImplBase";
    private static final int REGISTRATION_STATE_NOT_REGISTERED = 0;
    private static final int REGISTRATION_STATE_REGISTERED = 2;
    private static final int REGISTRATION_STATE_REGISTERING = 1;
    private static final int REGISTRATION_STATE_UNKNOWN = -1;
    public static final int REGISTRATION_TECH_IWLAN = 1;
    public static final int REGISTRATION_TECH_LTE = 0;
    public static final int REGISTRATION_TECH_NONE = -1;
    private final IImsRegistration mBinder = new IImsRegistration.Stub(){

        @Override
        public void addRegistrationCallback(IImsRegistrationCallback iImsRegistrationCallback) throws RemoteException {
            ImsRegistrationImplBase.this.addRegistrationCallback(iImsRegistrationCallback);
        }

        @Override
        public int getRegistrationTechnology() throws RemoteException {
            return ImsRegistrationImplBase.this.getConnectionType();
        }

        @Override
        public void removeRegistrationCallback(IImsRegistrationCallback iImsRegistrationCallback) throws RemoteException {
            ImsRegistrationImplBase.this.removeRegistrationCallback(iImsRegistrationCallback);
        }
    };
    private final RemoteCallbackList<IImsRegistrationCallback> mCallbacks = new RemoteCallbackList();
    private int mConnectionType = -1;
    private ImsReasonInfo mLastDisconnectCause = new ImsReasonInfo();
    private final Object mLock = new Object();
    private int mRegistrationState = -1;

    private void addRegistrationCallback(IImsRegistrationCallback iImsRegistrationCallback) throws RemoteException {
        this.mCallbacks.register(iImsRegistrationCallback);
        this.updateNewCallbackWithState(iImsRegistrationCallback);
    }

    static /* synthetic */ void lambda$onDeregistered$2(ImsReasonInfo imsReasonInfo, IImsRegistrationCallback object) {
        try {
            object.onDeregistered(imsReasonInfo);
        }
        catch (RemoteException remoteException) {
            object = new StringBuilder();
            ((StringBuilder)object).append(remoteException);
            ((StringBuilder)object).append(" onRegistrationDisconnected() - Skipping callback.");
            Log.w(LOG_TAG, ((StringBuilder)object).toString());
        }
    }

    static /* synthetic */ void lambda$onRegistered$0(int n, IImsRegistrationCallback object) {
        try {
            object.onRegistered(n);
        }
        catch (RemoteException remoteException) {
            object = new StringBuilder();
            ((StringBuilder)object).append(remoteException);
            ((StringBuilder)object).append(" onRegistrationConnected() - Skipping callback.");
            Log.w(LOG_TAG, ((StringBuilder)object).toString());
        }
    }

    static /* synthetic */ void lambda$onRegistering$1(int n, IImsRegistrationCallback iImsRegistrationCallback) {
        try {
            iImsRegistrationCallback.onRegistering(n);
        }
        catch (RemoteException remoteException) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(remoteException);
            stringBuilder.append(" onRegistrationProcessing() - Skipping callback.");
            Log.w(LOG_TAG, stringBuilder.toString());
        }
    }

    static /* synthetic */ void lambda$onSubscriberAssociatedUriChanged$4(Uri[] object, IImsRegistrationCallback iImsRegistrationCallback) {
        try {
            iImsRegistrationCallback.onSubscriberAssociatedUriChanged((Uri[])object);
        }
        catch (RemoteException remoteException) {
            object = new StringBuilder();
            ((StringBuilder)object).append(remoteException);
            ((StringBuilder)object).append(" onSubscriberAssociatedUriChanged() - Skipping callback.");
            Log.w(LOG_TAG, ((StringBuilder)object).toString());
        }
    }

    static /* synthetic */ void lambda$onTechnologyChangeFailed$3(int n, ImsReasonInfo object, IImsRegistrationCallback iImsRegistrationCallback) {
        try {
            iImsRegistrationCallback.onTechnologyChangeFailed(n, (ImsReasonInfo)object);
        }
        catch (RemoteException remoteException) {
            object = new StringBuilder();
            ((StringBuilder)object).append(remoteException);
            ((StringBuilder)object).append(" onRegistrationChangeFailed() - Skipping callback.");
            Log.w(LOG_TAG, ((StringBuilder)object).toString());
        }
    }

    private void removeRegistrationCallback(IImsRegistrationCallback iImsRegistrationCallback) {
        this.mCallbacks.unregister(iImsRegistrationCallback);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    private void updateNewCallbackWithState(IImsRegistrationCallback iImsRegistrationCallback) throws RemoteException {
        Object object = this.mLock;
        // MONITORENTER : object
        int n = this.mRegistrationState;
        ImsReasonInfo imsReasonInfo = this.mLastDisconnectCause;
        // MONITOREXIT : object
        if (n == 0) {
            iImsRegistrationCallback.onDeregistered(imsReasonInfo);
            return;
        }
        if (n == 1) {
            iImsRegistrationCallback.onRegistering(this.getConnectionType());
            return;
        }
        if (n != 2) {
            return;
        }
        iImsRegistrationCallback.onRegistered(this.getConnectionType());
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void updateToDisconnectedState(ImsReasonInfo imsReasonInfo) {
        Object object = this.mLock;
        synchronized (object) {
            this.updateToState(-1, 0);
            if (imsReasonInfo != null) {
                this.mLastDisconnectCause = imsReasonInfo;
            } else {
                Log.w(LOG_TAG, "updateToDisconnectedState: no ImsReasonInfo provided.");
                this.mLastDisconnectCause = imsReasonInfo = new ImsReasonInfo();
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void updateToState(int n, int n2) {
        Object object = this.mLock;
        synchronized (object) {
            this.mConnectionType = n;
            this.mRegistrationState = n2;
            this.mLastDisconnectCause = null;
            return;
        }
    }

    public final IImsRegistration getBinder() {
        return this.mBinder;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @VisibleForTesting
    public final int getConnectionType() {
        Object object = this.mLock;
        synchronized (object) {
            return this.mConnectionType;
        }
    }

    public final void onDeregistered(ImsReasonInfo imsReasonInfo) {
        this.updateToDisconnectedState(imsReasonInfo);
        this.mCallbacks.broadcast(new _$$Lambda$ImsRegistrationImplBase$s7PspXVbCf1Q_WSzodP2glP9TjI(imsReasonInfo));
    }

    public final void onRegistered(int n) {
        this.updateToState(n, 2);
        this.mCallbacks.broadcast(new _$$Lambda$ImsRegistrationImplBase$cWwTXSDsk_bWPbsDJYI__DUBMnE(n));
    }

    public final void onRegistering(int n) {
        this.updateToState(n, 1);
        this.mCallbacks.broadcast(new _$$Lambda$ImsRegistrationImplBase$sbjuTvW_brOSWMR74UInSZEIQB0(n));
    }

    public final void onSubscriberAssociatedUriChanged(Uri[] arruri) {
        this.mCallbacks.broadcast(new _$$Lambda$ImsRegistrationImplBase$wwtkoeOtGwMjG5I0_ZTfjNpGU_s(arruri));
    }

    public final void onTechnologyChangeFailed(int n, ImsReasonInfo imsReasonInfo) {
        this.mCallbacks.broadcast(new _$$Lambda$ImsRegistrationImplBase$wDtW65cPmn_jF6dfimhBTfdg1kI(n, imsReasonInfo));
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface ImsRegistrationTech {
    }

}

