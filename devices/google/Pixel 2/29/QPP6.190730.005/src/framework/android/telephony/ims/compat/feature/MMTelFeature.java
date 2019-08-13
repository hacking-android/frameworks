/*
 * Decompiled with CFR 0.145.
 */
package android.telephony.ims.compat.feature;

import android.app.PendingIntent;
import android.os.IInterface;
import android.os.Message;
import android.os.RemoteException;
import android.telephony.ims.ImsCallProfile;
import android.telephony.ims.compat.feature.ImsFeature;
import android.telephony.ims.stub.ImsEcbmImplBase;
import android.telephony.ims.stub.ImsMultiEndpointImplBase;
import android.telephony.ims.stub.ImsUtImplBase;
import com.android.ims.internal.IImsCallSession;
import com.android.ims.internal.IImsCallSessionListener;
import com.android.ims.internal.IImsConfig;
import com.android.ims.internal.IImsEcbm;
import com.android.ims.internal.IImsMMTelFeature;
import com.android.ims.internal.IImsMultiEndpoint;
import com.android.ims.internal.IImsRegistrationListener;
import com.android.ims.internal.IImsUt;

public class MMTelFeature
extends ImsFeature {
    private final IImsMMTelFeature mImsMMTelBinder = new IImsMMTelFeature.Stub(){

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void addRegistrationListener(IImsRegistrationListener iImsRegistrationListener) throws RemoteException {
            Object object = MMTelFeature.this.mLock;
            synchronized (object) {
                MMTelFeature.this.addRegistrationListener(iImsRegistrationListener);
                return;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public ImsCallProfile createCallProfile(int n, int n2, int n3) throws RemoteException {
            Object object = MMTelFeature.this.mLock;
            synchronized (object) {
                return MMTelFeature.this.createCallProfile(n, n2, n3);
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public IImsCallSession createCallSession(int n, ImsCallProfile object) throws RemoteException {
            Object object2 = MMTelFeature.this.mLock;
            synchronized (object2) {
                return MMTelFeature.this.createCallSession(n, (ImsCallProfile)object, null);
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void endSession(int n) throws RemoteException {
            Object object = MMTelFeature.this.mLock;
            synchronized (object) {
                MMTelFeature.this.endSession(n);
                return;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public IImsConfig getConfigInterface() throws RemoteException {
            Object object = MMTelFeature.this.mLock;
            synchronized (object) {
                return MMTelFeature.this.getConfigInterface();
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public IImsEcbm getEcbmInterface() throws RemoteException {
            Object object = MMTelFeature.this.mLock;
            synchronized (object) {
                ImsEcbmImplBase imsEcbmImplBase = MMTelFeature.this.getEcbmInterface();
                if (imsEcbmImplBase == null) return null;
                return imsEcbmImplBase.getImsEcbm();
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public int getFeatureStatus() throws RemoteException {
            Object object = MMTelFeature.this.mLock;
            synchronized (object) {
                return MMTelFeature.this.getFeatureState();
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public IImsMultiEndpoint getMultiEndpointInterface() throws RemoteException {
            Object object = MMTelFeature.this.mLock;
            synchronized (object) {
                ImsMultiEndpointImplBase imsMultiEndpointImplBase = MMTelFeature.this.getMultiEndpointInterface();
                if (imsMultiEndpointImplBase == null) return null;
                return imsMultiEndpointImplBase.getIImsMultiEndpoint();
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public IImsCallSession getPendingCallSession(int n, String object) throws RemoteException {
            Object object2 = MMTelFeature.this.mLock;
            synchronized (object2) {
                return MMTelFeature.this.getPendingCallSession(n, (String)object);
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public IImsUt getUtInterface() throws RemoteException {
            Object object = MMTelFeature.this.mLock;
            synchronized (object) {
                ImsUtImplBase imsUtImplBase = MMTelFeature.this.getUtInterface();
                if (imsUtImplBase == null) return null;
                return imsUtImplBase.getInterface();
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public boolean isConnected(int n, int n2) throws RemoteException {
            Object object = MMTelFeature.this.mLock;
            synchronized (object) {
                return MMTelFeature.this.isConnected(n, n2);
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public boolean isOpened() throws RemoteException {
            Object object = MMTelFeature.this.mLock;
            synchronized (object) {
                return MMTelFeature.this.isOpened();
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void removeRegistrationListener(IImsRegistrationListener iImsRegistrationListener) throws RemoteException {
            Object object = MMTelFeature.this.mLock;
            synchronized (object) {
                MMTelFeature.this.removeRegistrationListener(iImsRegistrationListener);
                return;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void setUiTTYMode(int n, Message message) throws RemoteException {
            Object object = MMTelFeature.this.mLock;
            synchronized (object) {
                MMTelFeature.this.setUiTTYMode(n, message);
                return;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public int startSession(PendingIntent pendingIntent, IImsRegistrationListener iImsRegistrationListener) throws RemoteException {
            Object object = MMTelFeature.this.mLock;
            synchronized (object) {
                return MMTelFeature.this.startSession(pendingIntent, iImsRegistrationListener);
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void turnOffIms() throws RemoteException {
            Object object = MMTelFeature.this.mLock;
            synchronized (object) {
                MMTelFeature.this.turnOffIms();
                return;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void turnOnIms() throws RemoteException {
            Object object = MMTelFeature.this.mLock;
            synchronized (object) {
                MMTelFeature.this.turnOnIms();
                return;
            }
        }
    };
    private final Object mLock = new Object();

    public void addRegistrationListener(IImsRegistrationListener iImsRegistrationListener) {
    }

    public ImsCallProfile createCallProfile(int n, int n2, int n3) {
        return null;
    }

    public IImsCallSession createCallSession(int n, ImsCallProfile imsCallProfile, IImsCallSessionListener iImsCallSessionListener) {
        return null;
    }

    public void endSession(int n) {
    }

    @Override
    public final IImsMMTelFeature getBinder() {
        return this.mImsMMTelBinder;
    }

    public IImsConfig getConfigInterface() {
        return null;
    }

    public ImsEcbmImplBase getEcbmInterface() {
        return null;
    }

    public ImsMultiEndpointImplBase getMultiEndpointInterface() {
        return null;
    }

    public IImsCallSession getPendingCallSession(int n, String string2) {
        return null;
    }

    public ImsUtImplBase getUtInterface() {
        return null;
    }

    public boolean isConnected(int n, int n2) {
        return false;
    }

    public boolean isOpened() {
        return false;
    }

    @Override
    public void onFeatureReady() {
    }

    @Override
    public void onFeatureRemoved() {
    }

    public void removeRegistrationListener(IImsRegistrationListener iImsRegistrationListener) {
    }

    public void setUiTTYMode(int n, Message message) {
    }

    public int startSession(PendingIntent pendingIntent, IImsRegistrationListener iImsRegistrationListener) {
        return 0;
    }

    public void turnOffIms() {
    }

    public void turnOnIms() {
    }

}

