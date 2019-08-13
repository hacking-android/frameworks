/*
 * Decompiled with CFR 0.145.
 */
package android.net.lowpan;

import android.net.IpPrefix;
import android.net.lowpan.ILowpanInterface;
import android.net.lowpan.ILowpanInterfaceListener;
import android.net.lowpan.LowpanBeaconInfo;
import android.net.lowpan.LowpanIdentity;
import android.net.lowpan._$$Lambda$LowpanCommissioningSession$InternalCallback$TrrmDykqIWeXNdgrXO7t2_rqCTo;
import android.net.lowpan._$$Lambda$LowpanCommissioningSession$jqpl_iUq_e7YuWqkG33P8PNe7Ag;
import android.os.DeadObjectException;
import android.os.Handler;
import android.os.Looper;
import android.os.RemoteException;

public class LowpanCommissioningSession {
    private final LowpanBeaconInfo mBeaconInfo;
    private final ILowpanInterface mBinder;
    private Callback mCallback = null;
    private Handler mHandler;
    private final ILowpanInterfaceListener mInternalCallback = new InternalCallback();
    private volatile boolean mIsClosed = false;
    private final Looper mLooper;

    LowpanCommissioningSession(ILowpanInterface object, LowpanBeaconInfo lowpanBeaconInfo, Looper looper) {
        this.mBinder = object;
        this.mBeaconInfo = lowpanBeaconInfo;
        this.mLooper = looper;
        object = this.mLooper;
        this.mHandler = object != null ? new Handler((Looper)object) : new Handler();
        try {
            this.mBinder.addListener(this.mInternalCallback);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowAsRuntimeException();
        }
    }

    private void lockedCleanup() {
        if (!this.mIsClosed) {
            try {
                this.mBinder.removeListener(this.mInternalCallback);
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowAsRuntimeException();
            }
            catch (DeadObjectException deadObjectException) {
                // empty catch block
            }
            if (this.mCallback != null) {
                this.mHandler.post(new _$$Lambda$LowpanCommissioningSession$jqpl_iUq_e7YuWqkG33P8PNe7Ag(this));
            }
        }
        this.mCallback = null;
        this.mIsClosed = true;
    }

    public void close() {
        synchronized (this) {
            block8 : {
                boolean bl = this.mIsClosed;
                if (bl) break block8;
                try {
                    this.mBinder.closeCommissioningSession();
                    this.lockedCleanup();
                }
                catch (RemoteException remoteException) {
                    throw remoteException.rethrowAsRuntimeException();
                }
                catch (DeadObjectException deadObjectException) {
                    // empty catch block
                }
            }
            return;
            finally {
            }
        }
    }

    public LowpanBeaconInfo getBeaconInfo() {
        return this.mBeaconInfo;
    }

    public /* synthetic */ void lambda$lockedCleanup$0$LowpanCommissioningSession() {
        this.mCallback.onClosed();
    }

    public void sendToCommissioner(byte[] arrby) {
        if (!this.mIsClosed) {
            try {
                this.mBinder.sendToCommissioner(arrby);
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowAsRuntimeException();
            }
            catch (DeadObjectException deadObjectException) {
                // empty catch block
            }
        }
    }

    public void setCallback(Callback callback, Handler handler) {
        synchronized (this) {
            if (!this.mIsClosed) {
                this.mHandler = handler != null ? handler : (this.mLooper != null ? (handler = new Handler(this.mLooper)) : (handler = new Handler()));
                this.mCallback = callback;
            }
            return;
        }
    }

    public static abstract class Callback {
        public void onClosed() {
        }

        public void onReceiveFromCommissioner(byte[] arrby) {
        }
    }

    private class InternalCallback
    extends ILowpanInterfaceListener.Stub {
        private InternalCallback() {
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public /* synthetic */ void lambda$onReceiveFromCommissioner$0$LowpanCommissioningSession$InternalCallback(byte[] arrby) {
            LowpanCommissioningSession lowpanCommissioningSession = LowpanCommissioningSession.this;
            synchronized (lowpanCommissioningSession) {
                if (!LowpanCommissioningSession.this.mIsClosed && LowpanCommissioningSession.this.mCallback != null) {
                    LowpanCommissioningSession.this.mCallback.onReceiveFromCommissioner(arrby);
                }
                return;
            }
        }

        @Override
        public void onConnectedChanged(boolean bl) {
        }

        @Override
        public void onEnabledChanged(boolean bl) {
        }

        @Override
        public void onLinkAddressAdded(String string2) {
        }

        @Override
        public void onLinkAddressRemoved(String string2) {
        }

        @Override
        public void onLinkNetworkAdded(IpPrefix ipPrefix) {
        }

        @Override
        public void onLinkNetworkRemoved(IpPrefix ipPrefix) {
        }

        @Override
        public void onLowpanIdentityChanged(LowpanIdentity lowpanIdentity) {
        }

        @Override
        public void onReceiveFromCommissioner(byte[] arrby) {
            LowpanCommissioningSession.this.mHandler.post(new _$$Lambda$LowpanCommissioningSession$InternalCallback$TrrmDykqIWeXNdgrXO7t2_rqCTo(this, arrby));
        }

        @Override
        public void onRoleChanged(String string2) {
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void onStateChanged(String string2) {
            if (LowpanCommissioningSession.this.mIsClosed) return;
            int n = -1;
            int n2 = string2.hashCode();
            if (n2 != -1548612125) {
                if (n2 == 97204770 && string2.equals("fault")) {
                    n = 1;
                }
            } else if (string2.equals("offline")) {
                n = 0;
            }
            if (n != 0 && n != 1) {
                return;
            }
            LowpanCommissioningSession lowpanCommissioningSession = LowpanCommissioningSession.this;
            synchronized (lowpanCommissioningSession) {
                LowpanCommissioningSession.this.lockedCleanup();
                return;
            }
        }

        @Override
        public void onUpChanged(boolean bl) {
        }
    }

}

