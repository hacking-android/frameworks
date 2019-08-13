/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.content.ComponentName
 *  android.content.Context
 *  android.content.Intent
 *  android.content.ServiceConnection
 *  android.content.pm.IPackageManager
 *  android.content.pm.IPackageManager$Stub
 *  android.os.Handler
 *  android.os.HandlerThread
 *  android.os.IBinder
 *  android.os.IInterface
 *  android.os.Looper
 *  android.os.RemoteException
 *  android.os.ServiceManager
 *  android.telephony.ims.ImsService
 *  android.telephony.ims.ImsService$Listener
 *  android.telephony.ims.aidl.IImsConfig
 *  android.telephony.ims.aidl.IImsMmTelFeature
 *  android.telephony.ims.aidl.IImsRcsFeature
 *  android.telephony.ims.aidl.IImsRegistration
 *  android.telephony.ims.aidl.IImsServiceController
 *  android.telephony.ims.aidl.IImsServiceController$Stub
 *  android.telephony.ims.aidl.IImsServiceControllerListener
 *  android.telephony.ims.stub.ImsFeatureConfiguration
 *  android.telephony.ims.stub.ImsFeatureConfiguration$FeatureSlotPair
 *  android.util.Log
 *  com.android.ims.internal.IImsFeatureStatusCallback
 *  com.android.ims.internal.IImsFeatureStatusCallback$Stub
 *  com.android.ims.internal.IImsServiceFeatureCallback
 *  com.android.internal.annotations.VisibleForTesting
 *  com.android.internal.telephony.ExponentialBackoff
 */
package com.android.internal.telephony.ims;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.IPackageManager;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Looper;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.telephony.ims.ImsService;
import android.telephony.ims.aidl.IImsConfig;
import android.telephony.ims.aidl.IImsMmTelFeature;
import android.telephony.ims.aidl.IImsRcsFeature;
import android.telephony.ims.aidl.IImsRegistration;
import android.telephony.ims.aidl.IImsServiceController;
import android.telephony.ims.aidl.IImsServiceControllerListener;
import android.telephony.ims.stub.ImsFeatureConfiguration;
import android.util.Log;
import com.android.ims.internal.IImsFeatureStatusCallback;
import com.android.ims.internal.IImsServiceFeatureCallback;
import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.telephony.ExponentialBackoff;
import com.android.internal.telephony.ims._$$Lambda$ImsServiceController$8NvoVXkZRS5LCradATGpNMBXAqg;
import com.android.internal.telephony.ims._$$Lambda$ImsServiceController$rO36xbdAp6IQ5hFqLNNXDJPMers;
import com.android.internal.telephony.ims._$$Lambda$ImsServiceController$w3xbtqEhKr7IY81qFuw0e94p84Y;
import java.io.Serializable;
import java.util.AbstractSet;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class ImsServiceController {
    private static final String LOG_TAG = "ImsServiceController";
    private static final int REBIND_MAXIMUM_DELAY_MS = 60000;
    private static final int REBIND_START_DELAY_MS = 2000;
    private ExponentialBackoff mBackoff;
    private ImsServiceControllerCallbacks mCallbacks;
    private final ComponentName mComponentName;
    protected final Context mContext;
    private ImsService.Listener mFeatureChangedListener = new ImsService.Listener(){

        public void onUpdateSupportedImsFeatures(ImsFeatureConfiguration imsFeatureConfiguration) {
            if (ImsServiceController.this.mCallbacks == null) {
                return;
            }
            ImsServiceController.this.mCallbacks.imsServiceFeaturesChanged(imsFeatureConfiguration, ImsServiceController.this);
        }
    };
    private Set<ImsFeatureStatusCallback> mFeatureStatusCallbacks = new HashSet<ImsFeatureStatusCallback>();
    private final HandlerThread mHandlerThread = new HandlerThread("ImsServiceControllerHandler");
    private IImsServiceController mIImsServiceController;
    private HashSet<ImsFeatureContainer> mImsFeatureBinders = new HashSet();
    private HashSet<ImsFeatureConfiguration.FeatureSlotPair> mImsFeatures;
    private ImsServiceConnection mImsServiceConnection;
    private Set<IImsServiceFeatureCallback> mImsStatusCallbacks = ConcurrentHashMap.newKeySet();
    private boolean mIsBinding = false;
    private boolean mIsBound = false;
    protected final Object mLock = new Object();
    private final IPackageManager mPackageManager;
    private RebindRetry mRebindRetry = new RebindRetry(){

        @Override
        public long getMaximumDelay() {
            return 60000L;
        }

        @Override
        public long getStartDelay() {
            return 2000L;
        }
    };
    private Runnable mRestartImsServiceRunnable = new Runnable(){

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void run() {
            Object object = ImsServiceController.this.mLock;
            synchronized (object) {
                if (ImsServiceController.this.mIsBound) {
                    return;
                }
                ImsServiceController.this.bind(ImsServiceController.this.mImsFeatures);
                return;
            }
        }
    };

    public ImsServiceController(Context context, ComponentName componentName, ImsServiceControllerCallbacks imsServiceControllerCallbacks) {
        this.mContext = context;
        this.mComponentName = componentName;
        this.mCallbacks = imsServiceControllerCallbacks;
        this.mHandlerThread.start();
        this.mBackoff = new ExponentialBackoff(this.mRebindRetry.getStartDelay(), this.mRebindRetry.getMaximumDelay(), 2, this.mHandlerThread.getLooper(), this.mRestartImsServiceRunnable);
        this.mPackageManager = IPackageManager.Stub.asInterface((IBinder)ServiceManager.getService((String)"package"));
    }

    @VisibleForTesting
    public ImsServiceController(Context context, ComponentName componentName, ImsServiceControllerCallbacks imsServiceControllerCallbacks, Handler handler, RebindRetry rebindRetry) {
        this.mContext = context;
        this.mComponentName = componentName;
        this.mCallbacks = imsServiceControllerCallbacks;
        this.mBackoff = new ExponentialBackoff(rebindRetry.getStartDelay(), rebindRetry.getMaximumDelay(), 2, handler, this.mRestartImsServiceRunnable);
        this.mPackageManager = null;
    }

    private void addImsFeatureBinder(int n, int n2, IInterface iInterface) {
        this.mImsFeatureBinders.add(new ImsFeatureContainer(n, n2, iInterface));
    }

    private void addImsServiceFeature(ImsFeatureConfiguration.FeatureSlotPair featureSlotPair) throws RemoteException {
        if (this.isServiceControllerAvailable() && this.mCallbacks != null) {
            if (featureSlotPair.featureType != 0) {
                ImsFeatureStatusCallback imsFeatureStatusCallback = new ImsFeatureStatusCallback(featureSlotPair.slotId, featureSlotPair.featureType);
                this.mFeatureStatusCallbacks.add(imsFeatureStatusCallback);
                imsFeatureStatusCallback = this.createImsFeature(featureSlotPair.slotId, featureSlotPair.featureType, imsFeatureStatusCallback.getCallback());
                this.addImsFeatureBinder(featureSlotPair.slotId, featureSlotPair.featureType, (IInterface)imsFeatureStatusCallback);
                this.mCallbacks.imsServiceFeatureCreated(featureSlotPair.slotId, featureSlotPair.featureType, this);
            } else {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("supports emergency calling on slot ");
                stringBuilder.append(featureSlotPair.slotId);
                Log.i((String)LOG_TAG, (String)stringBuilder.toString());
            }
            this.sendImsFeatureCreatedCallback(featureSlotPair.slotId, featureSlotPair.featureType);
            return;
        }
        Log.w((String)LOG_TAG, (String)"addImsServiceFeature called with null values.");
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void cleanUpService() {
        Object object = this.mLock;
        synchronized (object) {
            this.mImsServiceConnection = null;
            this.setServiceController(null);
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void cleanupAllFeatures() {
        Object object = this.mLock;
        synchronized (object) {
            Iterator<ImsFeatureConfiguration.FeatureSlotPair> iterator = this.mImsFeatures.iterator();
            do {
                if (!iterator.hasNext()) {
                    this.removeImsServiceFeatureCallbacks();
                    return;
                }
                this.removeImsServiceFeature(iterator.next());
            } while (true);
        }
    }

    private ImsFeatureContainer getImsFeatureContainer(int n, int n2) {
        return this.mImsFeatureBinders.stream().filter(new _$$Lambda$ImsServiceController$w3xbtqEhKr7IY81qFuw0e94p84Y(n, n2)).findFirst().orElse(null);
    }

    private void grantPermissionsToService() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Granting Runtime permissions to:");
        stringBuilder.append((Object)this.getComponentName());
        Log.i((String)LOG_TAG, (String)stringBuilder.toString());
        String string = this.mComponentName.getPackageName();
        try {
            if (this.mPackageManager != null) {
                stringBuilder = this.mPackageManager;
                int n = this.mContext.getUserId();
                stringBuilder.grantDefaultPermissionsToEnabledImsServices(new String[]{string}, n);
            }
        }
        catch (RemoteException remoteException) {
            Log.w((String)LOG_TAG, (String)"Unable to grant permissions, binder died.");
        }
    }

    static /* synthetic */ boolean lambda$getImsFeatureContainer$2(int n, int n2, ImsFeatureContainer imsFeatureContainer) {
        boolean bl = imsFeatureContainer.slotId == n && imsFeatureContainer.featureType == n2;
        return bl;
    }

    static /* synthetic */ boolean lambda$removeImsFeatureBinder$1(int n, int n2, ImsFeatureContainer imsFeatureContainer) {
        boolean bl = imsFeatureContainer.slotId == n && imsFeatureContainer.featureType == n2;
        return bl;
    }

    static /* synthetic */ boolean lambda$removeImsServiceFeature$0(ImsFeatureConfiguration.FeatureSlotPair featureSlotPair, ImsFeatureStatusCallback imsFeatureStatusCallback) {
        boolean bl = imsFeatureStatusCallback.mSlotId == featureSlotPair.slotId && imsFeatureStatusCallback.mFeatureType == featureSlotPair.featureType;
        return bl;
    }

    private void removeImsFeatureBinder(int n, int n2) {
        ImsFeatureContainer imsFeatureContainer = this.mImsFeatureBinders.stream().filter(new _$$Lambda$ImsServiceController$rO36xbdAp6IQ5hFqLNNXDJPMers(n, n2)).findFirst().orElse(null);
        if (imsFeatureContainer != null) {
            this.mImsFeatureBinders.remove(imsFeatureContainer);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void removeImsServiceFeature(ImsFeatureConfiguration.FeatureSlotPair featureSlotPair) {
        if (this.isServiceControllerAvailable() && this.mCallbacks != null) {
            if (featureSlotPair.featureType != 0) {
                Object object = this.mFeatureStatusCallbacks.stream().filter(new _$$Lambda$ImsServiceController$8NvoVXkZRS5LCradATGpNMBXAqg(featureSlotPair)).findFirst();
                IImsFeatureStatusCallback iImsFeatureStatusCallback = null;
                if ((object = (ImsFeatureStatusCallback)((Optional)object).orElse(null)) != null) {
                    this.mFeatureStatusCallbacks.remove(object);
                }
                this.removeImsFeatureBinder(featureSlotPair.slotId, featureSlotPair.featureType);
                this.mCallbacks.imsServiceFeatureRemoved(featureSlotPair.slotId, featureSlotPair.featureType, this);
                try {
                    int n = featureSlotPair.slotId;
                    int n2 = featureSlotPair.featureType;
                    if (object != null) {
                        iImsFeatureStatusCallback = ((ImsFeatureStatusCallback)object).getCallback();
                    }
                    this.removeImsFeature(n, n2, iImsFeatureStatusCallback);
                }
                catch (RemoteException remoteException) {
                    object = new StringBuilder();
                    ((StringBuilder)object).append("Couldn't remove feature {");
                    ((StringBuilder)object).append(featureSlotPair.featureType);
                    ((StringBuilder)object).append("}, connection is down: ");
                    ((StringBuilder)object).append(remoteException.getMessage());
                    Log.i((String)LOG_TAG, (String)((StringBuilder)object).toString());
                }
            } else {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("doesn't support emergency calling on slot ");
                stringBuilder.append(featureSlotPair.slotId);
                Log.i((String)LOG_TAG, (String)stringBuilder.toString());
            }
            this.sendImsFeatureRemovedCallback(featureSlotPair.slotId, featureSlotPair.featureType);
            return;
        }
        Log.w((String)LOG_TAG, (String)"removeImsServiceFeature called with null values.");
    }

    private void sendImsFeatureCreatedCallback(int n, int n2) {
        Iterator<IImsServiceFeatureCallback> iterator = this.mImsStatusCallbacks.iterator();
        while (iterator.hasNext()) {
            Object object = iterator.next();
            try {
                object.imsFeatureCreated(n, n2);
            }
            catch (RemoteException remoteException) {
                object = new StringBuilder();
                ((StringBuilder)object).append("sendImsFeatureCreatedCallback: Binder died, removing callback. Exception:");
                ((StringBuilder)object).append(remoteException.getMessage());
                Log.w((String)LOG_TAG, (String)((StringBuilder)object).toString());
                iterator.remove();
            }
        }
    }

    private void sendImsFeatureRemovedCallback(int n, int n2) {
        Iterator<IImsServiceFeatureCallback> iterator = this.mImsStatusCallbacks.iterator();
        while (iterator.hasNext()) {
            Object object = iterator.next();
            try {
                object.imsFeatureRemoved(n, n2);
            }
            catch (RemoteException remoteException) {
                object = new StringBuilder();
                ((StringBuilder)object).append("sendImsFeatureRemovedCallback: Binder died, removing callback. Exception:");
                ((StringBuilder)object).append(remoteException.getMessage());
                Log.w((String)LOG_TAG, (String)((StringBuilder)object).toString());
                iterator.remove();
            }
        }
    }

    private void sendImsFeatureStatusChanged(int n, int n2, int n3) {
        Iterator<IImsServiceFeatureCallback> iterator = this.mImsStatusCallbacks.iterator();
        while (iterator.hasNext()) {
            Object object = iterator.next();
            try {
                object.imsStatusChanged(n, n2, n3);
            }
            catch (RemoteException remoteException) {
                object = new StringBuilder();
                ((StringBuilder)object).append("sendImsFeatureStatusChanged: Binder died, removing callback. Exception:");
                ((StringBuilder)object).append(remoteException.getMessage());
                Log.w((String)LOG_TAG, (String)((StringBuilder)object).toString());
                iterator.remove();
            }
        }
    }

    private void startDelayedRebindToService() {
        this.mBackoff.start();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void addImsServiceFeatureCallback(IImsServiceFeatureCallback iImsServiceFeatureCallback) {
        this.mImsStatusCallbacks.add(iImsServiceFeatureCallback);
        Object object = this.mLock;
        synchronized (object) {
            boolean bl;
            if (this.mImsFeatures != null && !(bl = this.mImsFeatures.isEmpty())) {
                try {
                    for (ImsFeatureConfiguration.FeatureSlotPair featureSlotPair : this.mImsFeatures) {
                        iImsServiceFeatureCallback.imsFeatureCreated(featureSlotPair.slotId, featureSlotPair.featureType);
                    }
                }
                catch (RemoteException remoteException) {
                    Log.w((String)LOG_TAG, (String)"addImsServiceFeatureCallback: exception notifying callback");
                }
                return;
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public boolean bind(HashSet<ImsFeatureConfiguration.FeatureSlotPair> intent) {
        Object object = this.mLock;
        synchronized (object) {
            if (!this.mIsBound && !this.mIsBinding) {
                this.mIsBinding = true;
                this.mImsFeatures = intent;
                this.grantPermissionsToService();
                intent = new Intent(this.getServiceInterface());
                intent = intent.setComponent(this.mComponentName);
                Object object2 = new ImsServiceConnection();
                this.mImsServiceConnection = object2;
                object2 = new StringBuilder();
                ((StringBuilder)object2).append("Binding ImsService:");
                ((StringBuilder)object2).append((Object)this.mComponentName);
                Log.i((String)LOG_TAG, (String)((StringBuilder)object2).toString());
                try {
                    boolean bl = this.startBindToService(intent, this.mImsServiceConnection, 67108929);
                    if (!bl) {
                        this.mIsBinding = false;
                        this.mBackoff.notifyFailed();
                    }
                    return bl;
                }
                catch (Exception exception) {
                    this.mBackoff.notifyFailed();
                    object2 = new StringBuilder();
                    ((StringBuilder)object2).append("Error binding (");
                    ((StringBuilder)object2).append((Object)this.mComponentName);
                    ((StringBuilder)object2).append(") with exception: ");
                    ((StringBuilder)object2).append(exception.getMessage());
                    ((StringBuilder)object2).append(", rebinding in ");
                    ((StringBuilder)object2).append(this.mBackoff.getCurrentDelay());
                    ((StringBuilder)object2).append(" ms");
                    Log.e((String)LOG_TAG, (String)((StringBuilder)object2).toString());
                    return false;
                }
            }
            return false;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void changeImsServiceFeatures(HashSet<ImsFeatureConfiguration.FeatureSlotPair> object) throws RemoteException {
        Object object2 = this.mLock;
        synchronized (object2) {
            Serializable serializable = new StringBuilder();
            serializable.append("Features changed (");
            serializable.append(this.mImsFeatures);
            serializable.append("->");
            serializable.append(object);
            serializable.append(") for ImsService: ");
            serializable.append((Object)this.mComponentName);
            Log.i((String)LOG_TAG, (String)serializable.toString());
            serializable = new HashSet(this.mImsFeatures);
            this.mImsFeatures = object;
            if (this.mIsBound) {
                object = new HashSet(this.mImsFeatures);
                ((AbstractSet)object).removeAll((Collection<?>)((Object)serializable));
                object = ((HashSet)object).iterator();
                while (object.hasNext()) {
                    this.addImsServiceFeature((ImsFeatureConfiguration.FeatureSlotPair)object.next());
                }
                object = new HashSet(serializable);
                ((AbstractSet)object).removeAll(this.mImsFeatures);
                object = ((HashSet)object).iterator();
                while (object.hasNext()) {
                    this.removeImsServiceFeature((ImsFeatureConfiguration.FeatureSlotPair)object.next());
                }
            }
            return;
        }
    }

    protected IInterface createImsFeature(int n, int n2, IImsFeatureStatusCallback iImsFeatureStatusCallback) throws RemoteException {
        if (n2 != 1) {
            if (n2 != 2) {
                return null;
            }
            return this.mIImsServiceController.createRcsFeature(n, iImsFeatureStatusCallback);
        }
        return this.mIImsServiceController.createMmTelFeature(n, iImsFeatureStatusCallback);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    public void disableIms(int n) {
        block5 : {
            try {
                Object object = this.mLock;
                // MONITORENTER : object
                if (!this.isServiceControllerAvailable()) break block5;
            }
            catch (RemoteException remoteException) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Couldn't disable IMS: ");
                stringBuilder.append(remoteException.getMessage());
                Log.w((String)LOG_TAG, (String)stringBuilder.toString());
            }
            this.mIImsServiceController.disableIms(n);
        }
        // MONITOREXIT : object
        return;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    public void enableIms(int n) {
        block5 : {
            try {
                Object object = this.mLock;
                // MONITORENTER : object
                if (!this.isServiceControllerAvailable()) break block5;
            }
            catch (RemoteException remoteException) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Couldn't enable IMS: ");
                stringBuilder.append(remoteException.getMessage());
                Log.w((String)LOG_TAG, (String)stringBuilder.toString());
            }
            this.mIImsServiceController.enableIms(n);
        }
        // MONITOREXIT : object
        return;
    }

    public ComponentName getComponentName() {
        return this.mComponentName;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public IImsConfig getConfig(int n) throws RemoteException {
        Object object = this.mLock;
        synchronized (object) {
            if (!this.isServiceControllerAvailable()) return null;
            return this.mIImsServiceController.getConfig(n);
        }
    }

    @VisibleForTesting
    public IImsServiceController getImsServiceController() {
        return this.mIImsServiceController;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public IImsMmTelFeature getMmTelFeature(int n) {
        Object object = this.mLock;
        synchronized (object) {
            Object object2 = this.getImsFeatureContainer(n, 1);
            if (object2 != null) return ((ImsFeatureContainer)object2).resolve(IImsMmTelFeature.class);
            object2 = new StringBuilder();
            ((StringBuilder)object2).append("Requested null MMTelFeature on slot ");
            ((StringBuilder)object2).append(n);
            Log.w((String)LOG_TAG, (String)((StringBuilder)object2).toString());
            return null;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public IImsRcsFeature getRcsFeature(int n) {
        Object object = this.mLock;
        synchronized (object) {
            Object object2 = this.getImsFeatureContainer(n, 2);
            if (object2 != null) return ((ImsFeatureContainer)object2).resolve(IImsRcsFeature.class);
            object2 = new StringBuilder();
            ((StringBuilder)object2).append("Requested null RcsFeature on slot ");
            ((StringBuilder)object2).append(n);
            Log.w((String)LOG_TAG, (String)((StringBuilder)object2).toString());
            return null;
        }
    }

    @VisibleForTesting
    public long getRebindDelay() {
        return this.mBackoff.getCurrentDelay();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public IImsRegistration getRegistration(int n) throws RemoteException {
        Object object = this.mLock;
        synchronized (object) {
            if (!this.isServiceControllerAvailable()) return null;
            return this.mIImsServiceController.getRegistration(n);
        }
    }

    protected String getServiceInterface() {
        return "android.telephony.ims.ImsService";
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public boolean isBound() {
        Object object = this.mLock;
        synchronized (object) {
            return this.mIsBound;
        }
    }

    protected boolean isServiceControllerAvailable() {
        boolean bl = this.mIImsServiceController != null;
        return bl;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    protected void notifyImsServiceReady() throws RemoteException {
        Object object = this.mLock;
        synchronized (object) {
            if (this.isServiceControllerAvailable()) {
                Log.d((String)LOG_TAG, (String)"notifyImsServiceReady");
                this.mIImsServiceController.setListener((IImsServiceControllerListener)this.mFeatureChangedListener);
                this.mIImsServiceController.notifyImsServiceReadyForFeatureCreation();
            }
            return;
        }
    }

    protected void removeImsFeature(int n, int n2, IImsFeatureStatusCallback iImsFeatureStatusCallback) throws RemoteException {
        this.mIImsServiceController.removeImsFeature(n, n2, iImsFeatureStatusCallback);
    }

    @VisibleForTesting
    public void removeImsServiceFeatureCallbacks() {
        this.mImsStatusCallbacks.clear();
    }

    protected void setServiceController(IBinder iBinder) {
        this.mIImsServiceController = IImsServiceController.Stub.asInterface((IBinder)iBinder);
    }

    protected boolean startBindToService(Intent intent, ImsServiceConnection imsServiceConnection, int n) {
        return this.mContext.bindService(intent, (ServiceConnection)imsServiceConnection, n);
    }

    @VisibleForTesting
    public void stopBackoffTimerForTesting() {
        this.mBackoff.stop();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void unbind() throws RemoteException {
        Object object = this.mLock;
        synchronized (object) {
            this.mBackoff.stop();
            if (this.mImsServiceConnection == null) {
                return;
            }
            Serializable serializable = new HashSet();
            this.changeImsServiceFeatures((HashSet<ImsFeatureConfiguration.FeatureSlotPair>)serializable);
            this.removeImsServiceFeatureCallbacks();
            serializable = new StringBuilder();
            ((StringBuilder)serializable).append("Unbinding ImsService: ");
            ((StringBuilder)serializable).append((Object)this.mComponentName);
            Log.i((String)LOG_TAG, (String)((StringBuilder)serializable).toString());
            this.mContext.unbindService((ServiceConnection)this.mImsServiceConnection);
            this.cleanUpService();
            return;
        }
    }

    private class ImsFeatureContainer {
        public int featureType;
        private IInterface mBinder;
        public int slotId;

        ImsFeatureContainer(int n, int n2, IInterface iInterface) {
            this.slotId = n;
            this.featureType = n2;
            this.mBinder = iInterface;
        }

        public boolean equals(Object object) {
            boolean bl = true;
            if (this == object) {
                return true;
            }
            if (object != null && this.getClass() == object.getClass()) {
                ImsFeatureContainer imsFeatureContainer = (ImsFeatureContainer)object;
                if (this.slotId != imsFeatureContainer.slotId) {
                    return false;
                }
                if (this.featureType != imsFeatureContainer.featureType) {
                    return false;
                }
                object = this.mBinder;
                if (object != null) {
                    bl = object.equals((Object)imsFeatureContainer.mBinder);
                } else if (imsFeatureContainer.mBinder != null) {
                    bl = false;
                }
                return bl;
            }
            return false;
        }

        public int hashCode() {
            int n = this.slotId;
            int n2 = this.featureType;
            IInterface iInterface = this.mBinder;
            int n3 = iInterface != null ? iInterface.hashCode() : 0;
            return (n * 31 + n2) * 31 + n3;
        }

        public <T extends IInterface> T resolve(Class<T> class_) {
            return (T)((IInterface)class_.cast((Object)this.mBinder));
        }
    }

    private class ImsFeatureStatusCallback {
        private final IImsFeatureStatusCallback mCallback = new IImsFeatureStatusCallback.Stub(){

            public void notifyImsFeatureStatus(int n) throws RemoteException {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("notifyImsFeatureStatus: slot=");
                stringBuilder.append(ImsFeatureStatusCallback.this.mSlotId);
                stringBuilder.append(", feature=");
                stringBuilder.append(ImsFeatureStatusCallback.this.mFeatureType);
                stringBuilder.append(", status=");
                stringBuilder.append(n);
                Log.i((String)ImsServiceController.LOG_TAG, (String)stringBuilder.toString());
                ImsServiceController.this.sendImsFeatureStatusChanged(ImsFeatureStatusCallback.this.mSlotId, ImsFeatureStatusCallback.this.mFeatureType, n);
            }
        };
        private int mFeatureType;
        private int mSlotId;

        ImsFeatureStatusCallback(int n, int n2) {
            this.mSlotId = n;
            this.mFeatureType = n2;
        }

        public IImsFeatureStatusCallback getCallback() {
            return this.mCallback;
        }

    }

    class ImsServiceConnection
    implements ServiceConnection {
        ImsServiceConnection() {
        }

        private void cleanupConnection() {
            ImsServiceController.this.cleanupAllFeatures();
            ImsServiceController.this.cleanUpService();
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public void onBindingDied(ComponentName componentName) {
            Object object = ImsServiceController.this.mLock;
            synchronized (object) {
                ImsServiceController.this.mIsBinding = false;
                ImsServiceController.this.mIsBound = false;
            }
            this.cleanupConnection();
            object = new StringBuilder();
            ((StringBuilder)object).append("ImsService(");
            ((StringBuilder)object).append((Object)componentName);
            ((StringBuilder)object).append("): onBindingDied. Starting rebind...");
            Log.w((String)ImsServiceController.LOG_TAG, (String)((StringBuilder)object).toString());
            ImsServiceController.this.startDelayedRebindToService();
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public void onServiceConnected(ComponentName componentName, IBinder iBinder2) {
            ImsServiceController.this.mBackoff.stop();
            Object object = ImsServiceController.this.mLock;
            synchronized (object) {
                ImsServiceController.this.mIsBound = true;
                ImsServiceController.this.mIsBinding = false;
                Object object2 = new StringBuilder();
                ((StringBuilder)object2).append("ImsService(");
                ((StringBuilder)object2).append((Object)componentName);
                ((StringBuilder)object2).append("): onServiceConnected with binder: ");
                ((StringBuilder)object2).append((Object)iBinder2);
                Log.d((String)ImsServiceController.LOG_TAG, (String)((StringBuilder)object2).toString());
                if (iBinder2 != null) {
                    try {
                        ImsServiceController.this.setServiceController(iBinder2);
                        ImsServiceController.this.notifyImsServiceReady();
                        for (IBinder iBinder2 : ImsServiceController.this.mImsFeatures) {
                            ImsServiceController.this.addImsServiceFeature((ImsFeatureConfiguration.FeatureSlotPair)iBinder2);
                        }
                    }
                    catch (RemoteException remoteException) {
                        ImsServiceController.this.mIsBound = false;
                        ImsServiceController.this.mIsBinding = false;
                        this.cleanupConnection();
                        ImsServiceController.this.startDelayedRebindToService();
                        object2 = new StringBuilder();
                        ((StringBuilder)object2).append("ImsService(");
                        ((StringBuilder)object2).append((Object)componentName);
                        ((StringBuilder)object2).append(") RemoteException:");
                        ((StringBuilder)object2).append(remoteException.getMessage());
                        Log.e((String)ImsServiceController.LOG_TAG, (String)((StringBuilder)object2).toString());
                    }
                }
                return;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public void onServiceDisconnected(ComponentName componentName) {
            Object object = ImsServiceController.this.mLock;
            synchronized (object) {
                ImsServiceController.this.mIsBinding = false;
            }
            this.cleanupConnection();
            object = new StringBuilder();
            ((StringBuilder)object).append("ImsService(");
            ((StringBuilder)object).append((Object)componentName);
            ((StringBuilder)object).append("): onServiceDisconnected. Waiting...");
            Log.w((String)ImsServiceController.LOG_TAG, (String)((StringBuilder)object).toString());
        }
    }

    public static interface ImsServiceControllerCallbacks {
        public void imsServiceFeatureCreated(int var1, int var2, ImsServiceController var3);

        public void imsServiceFeatureRemoved(int var1, int var2, ImsServiceController var3);

        public void imsServiceFeaturesChanged(ImsFeatureConfiguration var1, ImsServiceController var2);
    }

    @VisibleForTesting
    public static interface RebindRetry {
        public long getMaximumDelay();

        public long getStartDelay();
    }

}

