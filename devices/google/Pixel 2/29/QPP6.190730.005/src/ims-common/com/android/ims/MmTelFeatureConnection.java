/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.os.Handler
 *  android.os.HandlerExecutor
 *  android.os.IBinder
 *  android.os.IBinder$DeathRecipient
 *  android.os.IInterface
 *  android.os.Looper
 *  android.os.Message
 *  android.os.RemoteCallbackList
 *  android.os.RemoteException
 *  android.telephony.Rlog
 *  android.telephony.SubscriptionManager
 *  android.telephony.SubscriptionManager$OnSubscriptionsChangedListener
 *  android.telephony.TelephonyManager
 *  android.telephony.ims.ImsCallProfile
 *  android.telephony.ims.aidl.IImsCapabilityCallback
 *  android.telephony.ims.aidl.IImsConfig
 *  android.telephony.ims.aidl.IImsConfigCallback
 *  android.telephony.ims.aidl.IImsMmTelFeature
 *  android.telephony.ims.aidl.IImsMmTelFeature$Stub
 *  android.telephony.ims.aidl.IImsMmTelListener
 *  android.telephony.ims.aidl.IImsRegistration
 *  android.telephony.ims.aidl.IImsRegistrationCallback
 *  android.telephony.ims.aidl.IImsSmsListener
 *  android.telephony.ims.feature.CapabilityChangeRequest
 *  android.telephony.ims.feature.MmTelFeature
 *  android.telephony.ims.feature.MmTelFeature$Listener
 *  android.telephony.ims.feature.MmTelFeature$MmTelCapabilities
 *  android.util.ArraySet
 *  android.util.Log
 *  android.util.SparseArray
 *  com.android.ims.-$
 *  com.android.ims.-$$Lambda
 *  com.android.ims.-$$Lambda$szO0o3matefQqo-6NB-dzsr9eCw
 *  com.android.ims.internal.IImsCallSession
 *  com.android.ims.internal.IImsEcbm
 *  com.android.ims.internal.IImsMultiEndpoint
 *  com.android.ims.internal.IImsServiceFeatureCallback
 *  com.android.ims.internal.IImsServiceFeatureCallback$Stub
 *  com.android.ims.internal.IImsUt
 *  com.android.internal.annotations.VisibleForTesting
 */
package com.android.ims;

import android.content.Context;
import android.os.Handler;
import android.os.HandlerExecutor;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.telephony.Rlog;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.telephony.ims.ImsCallProfile;
import android.telephony.ims.aidl.IImsCapabilityCallback;
import android.telephony.ims.aidl.IImsConfig;
import android.telephony.ims.aidl.IImsConfigCallback;
import android.telephony.ims.aidl.IImsMmTelFeature;
import android.telephony.ims.aidl.IImsMmTelListener;
import android.telephony.ims.aidl.IImsRegistration;
import android.telephony.ims.aidl.IImsRegistrationCallback;
import android.telephony.ims.aidl.IImsSmsListener;
import android.telephony.ims.feature.CapabilityChangeRequest;
import android.telephony.ims.feature.MmTelFeature;
import android.util.ArraySet;
import android.util.Log;
import android.util.SparseArray;
import com.android.ims.-$;
import com.android.ims.ImsManager;
import com.android.ims._$$Lambda$MmTelFeatureConnection$1$0SEXZe5KpKdo80CWXCfIl6qWHdQ;
import com.android.ims._$$Lambda$MmTelFeatureConnection$1$2oMo1vy7PK7RvBpj3WhQvVVnmLE;
import com.android.ims._$$Lambda$MmTelFeatureConnection$1$8CiyUe8f9BLYf_Cda_Du6JpOa_8;
import com.android.ims._$$Lambda$MmTelFeatureConnection$CallbackAdapterManager$xhSdbzmL46sv3qoJLYbOhV0PL3w;
import com.android.ims._$$Lambda$MmTelFeatureConnection$NxZFB3RppXJngUWEmxSWd3_I_s4;
import com.android.ims._$$Lambda$MmTelFeatureConnection$ij8S4RNRiQPHfppwkejp36BG78I;
import com.android.ims._$$Lambda$szO0o3matefQqo_6NB_dzsr9eCw;
import com.android.ims.internal.IImsCallSession;
import com.android.ims.internal.IImsEcbm;
import com.android.ims.internal.IImsMultiEndpoint;
import com.android.ims.internal.IImsServiceFeatureCallback;
import com.android.ims.internal.IImsUt;
import com.android.internal.annotations.VisibleForTesting;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/*
 * Illegal identifiers - consider using --renameillegalidents true
 */
public class MmTelFeatureConnection {
    protected static final String TAG = "MmTelFeatureConnection";
    private static boolean sImsSupportedOnDevice = true;
    protected IBinder mBinder;
    private final CapabilityCallbackManager mCapabilityCallbackManager;
    private IImsConfig mConfigBinder;
    private Context mContext;
    private final IBinder.DeathRecipient mDeathRecipient = new _$$Lambda$MmTelFeatureConnection$ij8S4RNRiQPHfppwkejp36BG78I(this);
    private Executor mExecutor;
    private Integer mFeatureStateCached = null;
    private volatile boolean mIsAvailable = false;
    private final IImsServiceFeatureCallback mListenerBinder = new IImsServiceFeatureCallback.Stub(){

        public void imsFeatureCreated(int n, int n2) {
            MmTelFeatureConnection.this.mExecutor.execute(new _$$Lambda$MmTelFeatureConnection$1$2oMo1vy7PK7RvBpj3WhQvVVnmLE(this, n, n2));
        }

        public void imsFeatureRemoved(int n, int n2) {
            MmTelFeatureConnection.this.mExecutor.execute(new _$$Lambda$MmTelFeatureConnection$1$8CiyUe8f9BLYf_Cda_Du6JpOa_8(this, n, n2));
        }

        public void imsStatusChanged(int n, int n2, int n3) {
            MmTelFeatureConnection.this.mExecutor.execute(new _$$Lambda$MmTelFeatureConnection$1$0SEXZe5KpKdo80CWXCfIl6qWHdQ(this, n, n2, n3));
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public /* synthetic */ void lambda$imsFeatureCreated$0$MmTelFeatureConnection$1(int n, int n2) {
            Object object = MmTelFeatureConnection.this.mLock;
            synchronized (object) {
                if (MmTelFeatureConnection.this.mSlotId != n) {
                    return;
                }
                if (n2 != 0) {
                    if (n2 == 1 && !MmTelFeatureConnection.this.mIsAvailable) {
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("MmTel enabled on slotId: ");
                        stringBuilder.append(n);
                        Log.i((String)MmTelFeatureConnection.TAG, (String)stringBuilder.toString());
                        MmTelFeatureConnection.this.mIsAvailable = true;
                    }
                } else {
                    MmTelFeatureConnection.this.mSupportsEmergencyCalling = true;
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Emergency calling enabled on slotId: ");
                    stringBuilder.append(n);
                    Log.i((String)MmTelFeatureConnection.TAG, (String)stringBuilder.toString());
                }
                return;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public /* synthetic */ void lambda$imsFeatureRemoved$1$MmTelFeatureConnection$1(int n, int n2) {
            Object object = MmTelFeatureConnection.this.mLock;
            synchronized (object) {
                if (MmTelFeatureConnection.this.mSlotId != n) {
                    return;
                }
                if (n2 != 0) {
                    if (n2 == 1) {
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("MmTel removed on slotId: ");
                        stringBuilder.append(n);
                        Log.i((String)MmTelFeatureConnection.TAG, (String)stringBuilder.toString());
                        MmTelFeatureConnection.this.onRemovedOrDied();
                    }
                } else {
                    MmTelFeatureConnection.this.mSupportsEmergencyCalling = false;
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Emergency calling disabled on slotId: ");
                    stringBuilder.append(n);
                    Log.i((String)MmTelFeatureConnection.TAG, (String)stringBuilder.toString());
                }
                return;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public /* synthetic */ void lambda$imsStatusChanged$2$MmTelFeatureConnection$1(int n, int n2, int n3) {
            Object object = MmTelFeatureConnection.this.mLock;
            synchronized (object) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("imsStatusChanged: slot: ");
                stringBuilder.append(n);
                stringBuilder.append(" feature: ");
                stringBuilder.append(n2);
                stringBuilder.append(" status: ");
                stringBuilder.append(n3);
                Log.i((String)MmTelFeatureConnection.TAG, (String)stringBuilder.toString());
                if (MmTelFeatureConnection.this.mSlotId == n && n2 == 1) {
                    MmTelFeatureConnection.this.mFeatureStateCached = n3;
                    if (MmTelFeatureConnection.this.mStatusCallback != null) {
                        MmTelFeatureConnection.this.mStatusCallback.notifyStateChanged();
                    }
                }
                return;
            }
        }
    };
    private final Object mLock = new Object();
    private final ProvisioningCallbackManager mProvisioningCallbackManager;
    private IImsRegistration mRegistrationBinder;
    private final ImsRegistrationCallbackAdapter mRegistrationCallbackManager;
    protected final int mSlotId;
    private IFeatureUpdate mStatusCallback;
    private boolean mSupportsEmergencyCalling = false;

    public MmTelFeatureConnection(Context context, int n) {
        this.mSlotId = n;
        this.mContext = context;
        if (context.getMainLooper() != null) {
            this.mExecutor = context.getMainExecutor();
        } else {
            if (Looper.myLooper() == null) {
                Looper.prepare();
            }
            this.mExecutor = new HandlerExecutor(new Handler(Looper.myLooper()));
        }
        this.mRegistrationCallbackManager = new ImsRegistrationCallbackAdapter(context, this.mLock);
        this.mCapabilityCallbackManager = new CapabilityCallbackManager(context, this.mLock);
        this.mProvisioningCallbackManager = new ProvisioningCallbackManager(context, this.mLock);
    }

    private void checkServiceIsReady() throws RemoteException {
        if (sImsSupportedOnDevice) {
            if (this.isBinderReady()) {
                return;
            }
            throw new RemoteException("ImsServiceProxy is not ready to accept commands.");
        }
        throw new RemoteException("IMS is not supported on this device.");
    }

    public static MmTelFeatureConnection create(Context object, int n) {
        MmTelFeatureConnection mmTelFeatureConnection = new MmTelFeatureConnection((Context)object, n);
        if (!ImsManager.isImsSupportedOnDevice((Context)object)) {
            sImsSupportedOnDevice = false;
            return mmTelFeatureConnection;
        }
        if ((object = MmTelFeatureConnection.getTelephonyManager((Context)object)) == null) {
            Rlog.w((String)TAG, (String)"create: TelephonyManager is null!");
            return mmTelFeatureConnection;
        }
        if ((object = object.getImsMmTelFeatureAndListen(n, mmTelFeatureConnection.getListener())) != null) {
            mmTelFeatureConnection.setBinder(object.asBinder());
            mmTelFeatureConnection.getFeatureState();
        } else {
            object = new StringBuilder();
            ((StringBuilder)object).append("create: binder is null! Slot Id: ");
            ((StringBuilder)object).append(n);
            Rlog.w((String)TAG, (String)((StringBuilder)object).toString());
        }
        return mmTelFeatureConnection;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private IImsConfig getConfig() {
        Object object = this.mLock;
        synchronized (object) {
            if (this.mConfigBinder != null) {
                return this.mConfigBinder;
            }
        }
        object = MmTelFeatureConnection.getTelephonyManager(this.mContext);
        object = object != null ? object.getImsConfig(this.mSlotId, 1) : null;
        Object object2 = this.mLock;
        synchronized (object2) {
            if (this.mConfigBinder != null) return this.mConfigBinder;
            this.mConfigBinder = object;
            return this.mConfigBinder;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private IImsRegistration getRegistration() {
        Object object = this.mLock;
        synchronized (object) {
            if (this.mRegistrationBinder != null) {
                return this.mRegistrationBinder;
            }
        }
        object = MmTelFeatureConnection.getTelephonyManager(this.mContext);
        object = object != null ? object.getImsRegistration(this.mSlotId, 1) : null;
        Object object2 = this.mLock;
        synchronized (object2) {
            if (this.mRegistrationBinder != null) return this.mRegistrationBinder;
            this.mRegistrationBinder = object;
            return this.mRegistrationBinder;
        }
    }

    private IImsMmTelFeature getServiceInterface(IBinder iBinder) {
        return IImsMmTelFeature.Stub.asInterface((IBinder)iBinder);
    }

    public static TelephonyManager getTelephonyManager(Context context) {
        return (TelephonyManager)context.getSystemService("phone");
    }

    public static /* synthetic */ void lambda$NxZFB3RppXJngUWEmxSWd3-I_s4(MmTelFeatureConnection mmTelFeatureConnection) {
        mmTelFeatureConnection.onRemovedOrDied();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void onRemovedOrDied() {
        Object object = this.mLock;
        synchronized (object) {
            this.mRegistrationCallbackManager.close();
            this.mCapabilityCallbackManager.close();
            this.mProvisioningCallbackManager.close();
            if (this.mIsAvailable) {
                this.mIsAvailable = false;
                this.mRegistrationBinder = null;
                this.mConfigBinder = null;
                if (this.mBinder != null) {
                    this.mBinder.unlinkToDeath(this.mDeathRecipient, 0);
                }
                if (this.mStatusCallback != null) {
                    this.mStatusCallback.notifyUnavailable();
                }
            }
            return;
        }
    }

    private Integer retrieveFeatureState() {
        IBinder iBinder = this.mBinder;
        if (iBinder != null) {
            int n;
            try {
                n = this.getServiceInterface(iBinder).getFeatureState();
            }
            catch (RemoteException remoteException) {
                // empty catch block
            }
            return n;
        }
        return null;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void acknowledgeSms(int n, int n2, int n3) throws RemoteException {
        Object object = this.mLock;
        synchronized (object) {
            this.checkServiceIsReady();
            this.getServiceInterface(this.mBinder).acknowledgeSms(n, n2, n3);
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void acknowledgeSmsReport(int n, int n2, int n3) throws RemoteException {
        Object object = this.mLock;
        synchronized (object) {
            this.checkServiceIsReady();
            this.getServiceInterface(this.mBinder).acknowledgeSmsReport(n, n2, n3);
            return;
        }
    }

    public void addCapabilityCallback(IImsCapabilityCallback iImsCapabilityCallback) {
        this.mCapabilityCallbackManager.addCallback(iImsCapabilityCallback);
    }

    public void addCapabilityCallbackForSubscription(IImsCapabilityCallback iImsCapabilityCallback, int n) {
        this.mCapabilityCallbackManager.addCallbackForSubscription(iImsCapabilityCallback, n);
    }

    public void addProvisioningCallbackForSubscription(IImsConfigCallback iImsConfigCallback, int n) {
        this.mProvisioningCallbackManager.addCallbackForSubscription(iImsConfigCallback, n);
    }

    public void addRegistrationCallback(IImsRegistrationCallback iImsRegistrationCallback) {
        this.mRegistrationCallbackManager.addCallback(iImsRegistrationCallback);
    }

    public void addRegistrationCallbackForSubscription(IImsRegistrationCallback iImsRegistrationCallback, int n) {
        this.mRegistrationCallbackManager.addCallbackForSubscription(iImsRegistrationCallback, n);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void changeEnabledCapabilities(CapabilityChangeRequest capabilityChangeRequest, IImsCapabilityCallback iImsCapabilityCallback) throws RemoteException {
        Object object = this.mLock;
        synchronized (object) {
            this.checkServiceIsReady();
            this.getServiceInterface(this.mBinder).changeCapabilitiesConfiguration(capabilityChangeRequest, iImsCapabilityCallback);
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    public void closeConnection() {
        block5 : {
            this.mRegistrationCallbackManager.close();
            this.mCapabilityCallbackManager.close();
            this.mProvisioningCallbackManager.close();
            try {
                Object object = this.mLock;
                // MONITORENTER : object
                if (!this.isBinderAlive()) break block5;
            }
            catch (RemoteException remoteException) {
                Log.w((String)TAG, (String)"closeConnection: couldn't remove listener!");
            }
            this.getServiceInterface(this.mBinder).setListener(null);
        }
        // MONITOREXIT : object
        return;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public ImsCallProfile createCallProfile(int n, int n2) throws RemoteException {
        Object object = this.mLock;
        synchronized (object) {
            this.checkServiceIsReady();
            return this.getServiceInterface(this.mBinder).createCallProfile(n, n2);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public IImsCallSession createCallSession(ImsCallProfile imsCallProfile) throws RemoteException {
        Object object = this.mLock;
        synchronized (object) {
            this.checkServiceIsReady();
            return this.getServiceInterface(this.mBinder).createCallSession(imsCallProfile);
        }
    }

    public IImsConfig getConfigInterface() {
        return this.getConfig();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public IImsEcbm getEcbmInterface() throws RemoteException {
        Object object = this.mLock;
        synchronized (object) {
            this.checkServiceIsReady();
            return this.getServiceInterface(this.mBinder).getEcbmInterface();
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public int getFeatureState() {
        Object object = this.mLock;
        synchronized (object) {
            if (this.isBinderAlive() && this.mFeatureStateCached != null) {
                return this.mFeatureStateCached;
            }
        }
        object = this.retrieveFeatureState();
        Object object2 = this.mLock;
        synchronized (object2) {
            if (object == null) {
                return 0;
            }
            this.mFeatureStateCached = object;
        }
        object2 = new StringBuilder();
        ((StringBuilder)object2).append("getFeatureState - returning ");
        ((StringBuilder)object2).append(object);
        Log.i((String)TAG, (String)((StringBuilder)object2).toString());
        return (Integer)object;
    }

    public IImsServiceFeatureCallback getListener() {
        return this.mListenerBinder;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public IImsMultiEndpoint getMultiEndpointInterface() throws RemoteException {
        Object object = this.mLock;
        synchronized (object) {
            this.checkServiceIsReady();
            return this.getServiceInterface(this.mBinder).getMultiEndpointInterface();
        }
    }

    public int getRegistrationTech() throws RemoteException {
        IImsRegistration iImsRegistration = this.getRegistration();
        if (iImsRegistration != null) {
            return iImsRegistration.getRegistrationTechnology();
        }
        return -1;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public String getSmsFormat() throws RemoteException {
        Object object = this.mLock;
        synchronized (object) {
            this.checkServiceIsReady();
            return this.getServiceInterface(this.mBinder).getSmsFormat();
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public IImsUt getUtInterface() throws RemoteException {
        Object object = this.mLock;
        synchronized (object) {
            this.checkServiceIsReady();
            return this.getServiceInterface(this.mBinder).getUtInterface();
        }
    }

    public boolean isBinderAlive() {
        IBinder iBinder;
        boolean bl = this.mIsAvailable && (iBinder = this.mBinder) != null && iBinder.isBinderAlive();
        return bl;
    }

    public boolean isBinderReady() {
        boolean bl = this.isBinderAlive() && this.getFeatureState() == 2;
        return bl;
    }

    public boolean isEmergencyMmTelAvailable() {
        return this.mSupportsEmergencyCalling;
    }

    public /* synthetic */ void lambda$new$0$MmTelFeatureConnection() {
        Log.w((String)TAG, (String)"DeathRecipient triggered, binder died.");
        if (this.mContext != null && Looper.getMainLooper() != null) {
            this.mContext.getMainExecutor().execute(new _$$Lambda$MmTelFeatureConnection$NxZFB3RppXJngUWEmxSWd3_I_s4(this));
            return;
        }
        this.onRemovedOrDied();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void onSmsReady() throws RemoteException {
        Object object = this.mLock;
        synchronized (object) {
            this.checkServiceIsReady();
            this.getServiceInterface(this.mBinder).onSmsReady();
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void openConnection(MmTelFeature.Listener listener) throws RemoteException {
        Object object = this.mLock;
        synchronized (object) {
            this.checkServiceIsReady();
            this.getServiceInterface(this.mBinder).setListener((IImsMmTelListener)listener);
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public MmTelFeature.MmTelCapabilities queryCapabilityStatus() throws RemoteException {
        Object object = this.mLock;
        synchronized (object) {
            this.checkServiceIsReady();
            return new MmTelFeature.MmTelCapabilities(this.getServiceInterface(this.mBinder).queryCapabilityStatus());
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void queryEnabledCapabilities(int n, int n2, IImsCapabilityCallback iImsCapabilityCallback) throws RemoteException {
        Object object = this.mLock;
        synchronized (object) {
            this.checkServiceIsReady();
            this.getServiceInterface(this.mBinder).queryCapabilityConfiguration(n, n2, iImsCapabilityCallback);
            return;
        }
    }

    public void removeCapabilityCallback(IImsCapabilityCallback iImsCapabilityCallback) {
        this.mCapabilityCallbackManager.removeCallback(iImsCapabilityCallback);
    }

    public void removeCapabilityCallbackForSubscription(IImsCapabilityCallback iImsCapabilityCallback, int n) {
        this.mCapabilityCallbackManager.removeCallbackForSubscription(iImsCapabilityCallback, n);
    }

    public void removeProvisioningCallbackForSubscription(IImsConfigCallback iImsConfigCallback, int n) {
        this.mProvisioningCallbackManager.removeCallbackForSubscription(iImsConfigCallback, n);
    }

    public void removeRegistrationCallback(IImsRegistrationCallback iImsRegistrationCallback) {
        this.mRegistrationCallbackManager.removeCallback(iImsRegistrationCallback);
    }

    public void removeRegistrationCallbackForSubscription(IImsRegistrationCallback iImsRegistrationCallback, int n) {
        this.mRegistrationCallbackManager.removeCallbackForSubscription(iImsRegistrationCallback, n);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void sendSms(int n, int n2, String string, String string2, boolean bl, byte[] arrby) throws RemoteException {
        Object object = this.mLock;
        synchronized (object) {
            this.checkServiceIsReady();
            this.getServiceInterface(this.mBinder).sendSms(n, n2, string, string2, bl, arrby);
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void setBinder(IBinder iBinder) {
        Object object = this.mLock;
        synchronized (object) {
            this.mBinder = iBinder;
            try {
                if (this.mBinder != null) {
                    this.mBinder.linkToDeath(this.mDeathRecipient, 0);
                }
            }
            catch (RemoteException remoteException) {
                // empty catch block
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void setSmsListener(IImsSmsListener iImsSmsListener) throws RemoteException {
        Object object = this.mLock;
        synchronized (object) {
            this.checkServiceIsReady();
            this.getServiceInterface(this.mBinder).setSmsListener(iImsSmsListener);
            return;
        }
    }

    public void setStatusCallback(IFeatureUpdate iFeatureUpdate) {
        this.mStatusCallback = iFeatureUpdate;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void setUiTTYMode(int n, Message message) throws RemoteException {
        Object object = this.mLock;
        synchronized (object) {
            this.checkServiceIsReady();
            this.getServiceInterface(this.mBinder).setUiTtyMode(n, message);
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public int shouldProcessCall(boolean bl, String[] arrstring) throws RemoteException {
        if (bl && !this.isEmergencyMmTelAvailable()) {
            Log.i((String)TAG, (String)"MmTel does not support emergency over IMS, fallback to CS.");
            return 1;
        }
        Object object = this.mLock;
        synchronized (object) {
            this.checkServiceIsReady();
            return this.getServiceInterface(this.mBinder).shouldProcessCall(arrstring);
        }
    }

    @VisibleForTesting
    public static abstract class CallbackAdapterManager<T extends IInterface> {
        private static final String TAG = "CallbackAdapterManager";
        private final SparseArray<Set<T>> mCallbackSubscriptionMap = new SparseArray();
        private final Context mContext;
        private final Object mLock;
        private final RemoteCallbackList<T> mRemoteCallbacks = new RemoteCallbackList();
        @VisibleForTesting
        public SubscriptionManager.OnSubscriptionsChangedListener mSubChangedListener;

        public CallbackAdapterManager(Context context, Object object) {
            this.mContext = context;
            this.mLock = object;
            if (Looper.myLooper() == null) {
                Looper.prepare();
            }
            this.mSubChangedListener = new SubscriptionManager.OnSubscriptionsChangedListener(){

                /*
                 * Enabled aggressive block sorting
                 * Enabled unnecessary exception pruning
                 * Enabled aggressive exception aggregation
                 */
                public void onSubscriptionsChanged() {
                    Object object = (SubscriptionManager)mContext.getSystemService(SubscriptionManager.class);
                    if (object == null) {
                        Log.w((String)CallbackAdapterManager.TAG, (String)"onSubscriptionsChanged: could not find SubscriptionManager.");
                        return;
                    }
                    int n = 0;
                    Object object2 = object.getActiveSubscriptionInfoList(false);
                    object = object2;
                    if (object2 == null) {
                        object = Collections.emptyList();
                    }
                    object2 = object.stream().map(_$$Lambda$szO0o3matefQqo_6NB_dzsr9eCw.INSTANCE).collect(Collectors.toSet());
                    object = mLock;
                    synchronized (object) {
                        Object object3 = new ArraySet(mCallbackSubscriptionMap.size());
                        while (n < mCallbackSubscriptionMap.size()) {
                            object3.add(mCallbackSubscriptionMap.keyAt(n));
                            ++n;
                        }
                        object3.removeAll((Collection<?>)object2);
                        object3 = object3.iterator();
                        while (object3.hasNext()) {
                            object2 = (Integer)object3.next();
                            this.removeCallbacksForSubscription((Integer)object2);
                        }
                        return;
                    }
                }
            };
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        private void clearCallbacksForAllSubscriptions() {
            Object object = this.mLock;
            synchronized (object) {
                ArrayList<Integer> arrayList = new ArrayList<Integer>();
                int n = 0;
                do {
                    if (n >= this.mCallbackSubscriptionMap.size()) {
                        _$$Lambda$MmTelFeatureConnection$CallbackAdapterManager$xhSdbzmL46sv3qoJLYbOhV0PL3w _$$Lambda$MmTelFeatureConnection$CallbackAdapterManager$xhSdbzmL46sv3qoJLYbOhV0PL3w = new _$$Lambda$MmTelFeatureConnection$CallbackAdapterManager$xhSdbzmL46sv3qoJLYbOhV0PL3w(this);
                        arrayList.forEach(_$$Lambda$MmTelFeatureConnection$CallbackAdapterManager$xhSdbzmL46sv3qoJLYbOhV0PL3w);
                        return;
                    }
                    arrayList.add(this.mCallbackSubscriptionMap.keyAt(n));
                    ++n;
                } while (true);
            }
        }

        public static /* synthetic */ void lambda$xhSdbzmL46sv3qoJLYbOhV0PL3w(CallbackAdapterManager callbackAdapterManager, int n) {
            callbackAdapterManager.removeCallbacksForSubscription(n);
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        private void linkCallbackToSubscription(T t, int n) {
            Object object = this.mLock;
            synchronized (object) {
                Set set;
                if (this.mCallbackSubscriptionMap.size() == 0) {
                    this.registerForSubscriptionsChanged();
                }
                Set set2 = set = (Set)this.mCallbackSubscriptionMap.get(n);
                if (set == null) {
                    set2 = new ArraySet();
                    this.mCallbackSubscriptionMap.put(n, (Object)set2);
                }
                set2.add(t);
                return;
            }
        }

        private void registerForSubscriptionsChanged() {
            SubscriptionManager subscriptionManager = (SubscriptionManager)this.mContext.getSystemService(SubscriptionManager.class);
            if (subscriptionManager != null) {
                subscriptionManager.addOnSubscriptionsChangedListener(this.mSubChangedListener);
            } else {
                Log.w((String)TAG, (String)"registerForSubscriptionsChanged: could not find SubscriptionManager.");
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        private void removeCallbacksForSubscription(int n) {
            if (!SubscriptionManager.isValidSubscriptionId((int)n)) {
                return;
            }
            Object object = this.mLock;
            synchronized (object) {
                Object object2 = (Set)this.mCallbackSubscriptionMap.get(n);
                if (object2 == null) {
                    return;
                }
                this.mCallbackSubscriptionMap.remove(n);
                object2 = object2.iterator();
                while (object2.hasNext()) {
                    this.removeCallback((T)((IInterface)object2.next()));
                }
                if (this.mCallbackSubscriptionMap.size() == 0) {
                    this.unregisterForSubscriptionsChanged();
                }
                return;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        private void unlinkCallbackFromSubscription(T t, int n) {
            Object object = this.mLock;
            synchronized (object) {
                Set set = (Set)this.mCallbackSubscriptionMap.get(n);
                if (set != null) {
                    set.remove(t);
                    if (set.isEmpty()) {
                        this.mCallbackSubscriptionMap.remove(n);
                    }
                }
                if (this.mCallbackSubscriptionMap.size() == 0) {
                    this.unregisterForSubscriptionsChanged();
                }
                return;
            }
        }

        private void unregisterForSubscriptionsChanged() {
            SubscriptionManager subscriptionManager = (SubscriptionManager)this.mContext.getSystemService(SubscriptionManager.class);
            if (subscriptionManager != null) {
                subscriptionManager.removeOnSubscriptionsChangedListener(this.mSubChangedListener);
            } else {
                Log.w((String)TAG, (String)"unregisterForSubscriptionsChanged: could not find SubscriptionManager.");
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public final void addCallback(T t) {
            Object object = this.mLock;
            synchronized (object) {
                this.registerCallback(t);
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Local callback added: ");
                stringBuilder.append(t);
                Log.i((String)TAG, (String)stringBuilder.toString());
                this.mRemoteCallbacks.register(t);
                return;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public final void addCallbackForSubscription(T t, int n) {
            if (!SubscriptionManager.isValidSubscriptionId((int)n)) {
                return;
            }
            Object object = this.mLock;
            synchronized (object) {
                this.addCallback(t);
                this.linkCallbackToSubscription(t, n);
                return;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public final void close() {
            Object object = this.mLock;
            synchronized (object) {
                int n = this.mRemoteCallbacks.getRegisteredCallbackCount() - 1;
                do {
                    if (n < 0) {
                        this.clearCallbacksForAllSubscriptions();
                        Log.i((String)TAG, (String)"Closing connection and clearing callbacks");
                        return;
                    }
                    IInterface iInterface = this.mRemoteCallbacks.getRegisteredCallbackItem(n);
                    this.unregisterCallback((T)iInterface);
                    this.mRemoteCallbacks.unregister(iInterface);
                    --n;
                } while (true);
            }
        }

        public abstract void registerCallback(T var1);

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public final void removeCallback(T t) {
            Object object = new StringBuilder();
            ((StringBuilder)object).append("Local callback removed: ");
            ((StringBuilder)object).append(t);
            Log.i((String)"CallbackAdapterManager", (String)((StringBuilder)object).toString());
            object = this.mLock;
            synchronized (object) {
                if (this.mRemoteCallbacks.unregister(t)) {
                    this.unregisterCallback(t);
                }
                return;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public final void removeCallbackForSubscription(T t, int n) {
            if (!SubscriptionManager.isValidSubscriptionId((int)n)) {
                return;
            }
            Object object = this.mLock;
            synchronized (object) {
                this.removeCallback(t);
                this.unlinkCallbackFromSubscription(t, n);
                return;
            }
        }

        public abstract void unregisterCallback(T var1);

    }

    private class CapabilityCallbackManager
    extends CallbackAdapterManager<IImsCapabilityCallback> {
        public CapabilityCallbackManager(Context context, Object object) {
            super(context, object);
        }

        /*
         * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
         * Loose catch block
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         * Converted monitor instructions to comments
         * Lifted jumps to return sites
         */
        @Override
        public void registerCallback(IImsCapabilityCallback iImsCapabilityCallback) {
            block7 : {
                Object object = MmTelFeatureConnection.this.mLock;
                // MONITORENTER : object
                MmTelFeatureConnection.this.checkServiceIsReady();
                IImsMmTelFeature iImsMmTelFeature = MmTelFeatureConnection.this.getServiceInterface(MmTelFeatureConnection.this.mBinder);
                // MONITOREXIT : object
                if (iImsMmTelFeature == null) break block7;
                try {
                    iImsMmTelFeature.addCapabilityCallback(iImsCapabilityCallback);
                    return;
                }
                catch (RemoteException remoteException) {
                    throw new IllegalStateException(" CapabilityCallbackManager - MmTelFeature binder is null.");
                }
            }
            Log.w((String)MmTelFeatureConnection.TAG, (String)"CapabilityCallbackManager, register: Couldn't get binder");
            throw new IllegalStateException("CapabilityCallbackManager: MmTelFeature is not available!");
            {
                catch (Throwable throwable) {
                    throw throwable;
                }
                catch (RemoteException remoteException) {}
                {
                    IllegalStateException illegalStateException = new IllegalStateException("CapabilityCallbackManager - MmTelFeature binder is dead.");
                    throw illegalStateException;
                }
            }
        }

        /*
         * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
         * Loose catch block
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         * Converted monitor instructions to comments
         * Lifted jumps to return sites
         */
        @Override
        public void unregisterCallback(IImsCapabilityCallback iImsCapabilityCallback) {
            block7 : {
                Object object = MmTelFeatureConnection.this.mLock;
                // MONITORENTER : object
                MmTelFeatureConnection.this.checkServiceIsReady();
                IImsMmTelFeature iImsMmTelFeature = MmTelFeatureConnection.this.getServiceInterface(MmTelFeatureConnection.this.mBinder);
                // MONITOREXIT : object
                if (iImsMmTelFeature == null) break block7;
                try {
                    iImsMmTelFeature.removeCapabilityCallback(iImsCapabilityCallback);
                    return;
                }
                catch (RemoteException remoteException) {
                    Log.w((String)MmTelFeatureConnection.TAG, (String)"CapabilityCallbackManager, unregister: Binder is dead.");
                    return;
                }
            }
            Log.w((String)MmTelFeatureConnection.TAG, (String)"CapabilityCallbackManager, unregister: binder is null.");
            return;
            {
                catch (Throwable throwable) {
                    throw throwable;
                }
                catch (RemoteException remoteException) {}
                {
                    Log.w((String)MmTelFeatureConnection.TAG, (String)"CapabilityCallbackManager, unregister: couldn't get binder.");
                    // MONITOREXIT : object
                    return;
                }
            }
        }
    }

    public static interface IFeatureUpdate {
        public void notifyStateChanged();

        public void notifyUnavailable();
    }

    private class ImsRegistrationCallbackAdapter
    extends CallbackAdapterManager<IImsRegistrationCallback> {
        public ImsRegistrationCallbackAdapter(Context context, Object object) {
            super(context, object);
        }

        @Override
        public void registerCallback(IImsRegistrationCallback iImsRegistrationCallback) {
            IImsRegistration iImsRegistration = MmTelFeatureConnection.this.getRegistration();
            if (iImsRegistration != null) {
                try {
                    iImsRegistration.addRegistrationCallback(iImsRegistrationCallback);
                    return;
                }
                catch (RemoteException remoteException) {
                    throw new IllegalStateException("ImsRegistrationCallbackAdapter: MmTelFeature binder is dead.");
                }
            }
            Log.e((String)MmTelFeatureConnection.TAG, (String)"ImsRegistrationCallbackAdapter: ImsRegistration is null");
            throw new IllegalStateException("ImsRegistrationCallbackAdapter: MmTelFeature isnot available!");
        }

        @Override
        public void unregisterCallback(IImsRegistrationCallback iImsRegistrationCallback) {
            IImsRegistration iImsRegistration = MmTelFeatureConnection.this.getRegistration();
            if (iImsRegistration != null) {
                try {
                    iImsRegistration.removeRegistrationCallback(iImsRegistrationCallback);
                }
                catch (RemoteException remoteException) {
                    Log.w((String)MmTelFeatureConnection.TAG, (String)"ImsRegistrationCallbackAdapter - unregisterCallback: couldn't remove registration callback");
                }
            } else {
                Log.e((String)MmTelFeatureConnection.TAG, (String)"ImsRegistrationCallbackAdapter: ImsRegistration is null");
            }
        }
    }

    private class ProvisioningCallbackManager
    extends CallbackAdapterManager<IImsConfigCallback> {
        public ProvisioningCallbackManager(Context context, Object object) {
            super(context, object);
        }

        @Override
        public void registerCallback(IImsConfigCallback iImsConfigCallback) {
            IImsConfig iImsConfig = MmTelFeatureConnection.this.getConfigInterface();
            if (iImsConfig != null) {
                try {
                    iImsConfig.addImsConfigCallback(iImsConfigCallback);
                    return;
                }
                catch (RemoteException remoteException) {
                    throw new IllegalStateException("ImsService is not available!");
                }
            }
            Log.w((String)MmTelFeatureConnection.TAG, (String)"ProvisioningCallbackManager - couldn't register, binder is null.");
            throw new IllegalStateException("ImsConfig is not available!");
        }

        @Override
        public void unregisterCallback(IImsConfigCallback iImsConfigCallback) {
            IImsConfig iImsConfig = MmTelFeatureConnection.this.getConfigInterface();
            if (iImsConfig == null) {
                Log.w((String)MmTelFeatureConnection.TAG, (String)"ProvisioningCallbackManager - couldn't unregister, binder is null.");
                return;
            }
            try {
                iImsConfig.removeImsConfigCallback(iImsConfigCallback);
            }
            catch (RemoteException remoteException) {
                Log.w((String)MmTelFeatureConnection.TAG, (String)"ProvisioningCallbackManager - couldn't unregister, binder is dead.");
            }
        }
    }

}

