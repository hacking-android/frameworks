/*
 * Decompiled with CFR 0.145.
 */
package android.telephony.ims.feature;

import android.annotation.SystemApi;
import android.content.Context;
import android.os.IInterface;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.telephony.ims.aidl.IImsCapabilityCallback;
import android.telephony.ims.feature.CapabilityChangeRequest;
import android.util.Log;
import com.android.ims.internal.IImsFeatureStatusCallback;
import com.android.internal.annotations.VisibleForTesting;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Collections;
import java.util.Iterator;
import java.util.Set;
import java.util.WeakHashMap;

@SystemApi
public abstract class ImsFeature {
    public static final String ACTION_IMS_SERVICE_DOWN = "com.android.ims.IMS_SERVICE_DOWN";
    public static final String ACTION_IMS_SERVICE_UP = "com.android.ims.IMS_SERVICE_UP";
    public static final int CAPABILITY_ERROR_GENERIC = -1;
    public static final int CAPABILITY_SUCCESS = 0;
    public static final String EXTRA_PHONE_ID = "android:phone_id";
    public static final int FEATURE_EMERGENCY_MMTEL = 0;
    public static final int FEATURE_INVALID = -1;
    public static final int FEATURE_MAX = 3;
    public static final int FEATURE_MMTEL = 1;
    public static final int FEATURE_RCS = 2;
    private static final String LOG_TAG = "ImsFeature";
    public static final int STATE_INITIALIZING = 1;
    public static final int STATE_READY = 2;
    public static final int STATE_UNAVAILABLE = 0;
    private final RemoteCallbackList<IImsCapabilityCallback> mCapabilityCallbacks = new RemoteCallbackList();
    private Capabilities mCapabilityStatus = new Capabilities();
    protected Context mContext;
    protected final Object mLock = new Object();
    private int mSlotId = -1;
    private int mState = 0;
    private final Set<IImsFeatureStatusCallback> mStatusCallbacks = Collections.newSetFromMap(new WeakHashMap());

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void notifyFeatureState(int n) {
        Object object = this.mLock;
        synchronized (object) {
            Iterator<IImsFeatureStatusCallback> iterator = this.mStatusCallbacks.iterator();
            while (iterator.hasNext()) {
                Object object2 = iterator.next();
                try {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("notifying ImsFeatureState=");
                    stringBuilder.append(n);
                    Log.i(LOG_TAG, stringBuilder.toString());
                    object2.notifyImsFeatureStatus(n);
                }
                catch (RemoteException remoteException) {
                    iterator.remove();
                    object2 = new StringBuilder();
                    ((StringBuilder)object2).append("Couldn't notify feature state: ");
                    ((StringBuilder)object2).append(remoteException.getMessage());
                    Log.w(LOG_TAG, ((StringBuilder)object2).toString());
                    continue;
                }
                break;
            }
            return;
        }
    }

    public final void addCapabilityCallback(IImsCapabilityCallback object) {
        this.mCapabilityCallbacks.register((IImsCapabilityCallback)object);
        try {
            object.onCapabilitiesStatusChanged(this.queryCapabilityStatus().mCapabilities);
        }
        catch (RemoteException remoteException) {
            object = new StringBuilder();
            ((StringBuilder)object).append("addCapabilityCallback: error accessing callback: ");
            ((StringBuilder)object).append(remoteException.getMessage());
            Log.w(LOG_TAG, ((StringBuilder)object).toString());
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    @VisibleForTesting
    public void addImsFeatureStatusCallback(IImsFeatureStatusCallback iImsFeatureStatusCallback) {
        try {
            iImsFeatureStatusCallback.notifyImsFeatureStatus(this.getFeatureState());
            Object object = this.mLock;
            // MONITORENTER : object
            this.mStatusCallbacks.add(iImsFeatureStatusCallback);
        }
        catch (RemoteException remoteException) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Couldn't notify feature state: ");
            stringBuilder.append(remoteException.getMessage());
            Log.w(LOG_TAG, stringBuilder.toString());
        }
        // MONITOREXIT : object
        return;
    }

    public abstract void changeEnabledCapabilities(CapabilityChangeRequest var1, CapabilityCallbackProxy var2);

    protected abstract IInterface getBinder();

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public int getFeatureState() {
        Object object = this.mLock;
        synchronized (object) {
            return this.mState;
        }
    }

    public final void initialize(Context context, int n) {
        this.mContext = context;
        this.mSlotId = n;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    protected final void notifyCapabilitiesStatusChanged(Capabilities capabilities) {
        Throwable throwable22;
        Object object = this.mLock;
        synchronized (object) {
            this.mCapabilityStatus = capabilities.copy();
        }
        int n = this.mCapabilityCallbacks.beginBroadcast();
        int n2 = 0;
        do {
            if (n2 >= n) {
                this.mCapabilityCallbacks.finishBroadcast();
                return;
            }
            try {
                try {
                    this.mCapabilityCallbacks.getBroadcastItem(n2).onCapabilitiesStatusChanged(capabilities.mCapabilities);
                }
                catch (RemoteException remoteException) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append(remoteException);
                    stringBuilder.append(" notifyCapabilitiesStatusChanged() - Skipping callback.");
                    Log.w("ImsFeature", stringBuilder.toString());
                }
                ++n2;
                continue;
            }
            catch (Throwable throwable22) {}
            break;
        } while (true);
        this.mCapabilityCallbacks.finishBroadcast();
        throw throwable22;
    }

    public abstract void onFeatureReady();

    public abstract void onFeatureRemoved();

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @VisibleForTesting
    public Capabilities queryCapabilityStatus() {
        Object object = this.mLock;
        synchronized (object) {
            return this.mCapabilityStatus.copy();
        }
    }

    public final void removeCapabilityCallback(IImsCapabilityCallback iImsCapabilityCallback) {
        this.mCapabilityCallbacks.unregister(iImsCapabilityCallback);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @VisibleForTesting
    public void removeImsFeatureStatusCallback(IImsFeatureStatusCallback iImsFeatureStatusCallback) {
        Object object = this.mLock;
        synchronized (object) {
            this.mStatusCallbacks.remove(iImsFeatureStatusCallback);
            return;
        }
    }

    @VisibleForTesting
    public final void requestChangeEnabledCapabilities(CapabilityChangeRequest capabilityChangeRequest, IImsCapabilityCallback iImsCapabilityCallback) {
        if (capabilityChangeRequest != null) {
            this.changeEnabledCapabilities(capabilityChangeRequest, new CapabilityCallbackProxy(iImsCapabilityCallback));
            return;
        }
        throw new IllegalArgumentException("ImsFeature#requestChangeEnabledCapabilities called with invalid params.");
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public final void setFeatureState(int n) {
        Object object = this.mLock;
        synchronized (object) {
            if (this.mState != n) {
                this.mState = n;
                this.notifyFeatureState(n);
            }
            return;
        }
    }

    @SystemApi
    public static class Capabilities {
        protected int mCapabilities = 0;

        public Capabilities() {
        }

        protected Capabilities(int n) {
            this.mCapabilities = n;
        }

        public void addCapabilities(int n) {
            this.mCapabilities |= n;
        }

        public Capabilities copy() {
            return new Capabilities(this.mCapabilities);
        }

        public boolean equals(Object object) {
            boolean bl = true;
            if (this == object) {
                return true;
            }
            if (!(object instanceof Capabilities)) {
                return false;
            }
            object = (Capabilities)object;
            if (this.mCapabilities != ((Capabilities)object).mCapabilities) {
                bl = false;
            }
            return bl;
        }

        public int getMask() {
            return this.mCapabilities;
        }

        public int hashCode() {
            return this.mCapabilities;
        }

        public boolean isCapable(int n) {
            boolean bl = (this.mCapabilities & n) == n;
            return bl;
        }

        public void removeCapabilities(int n) {
            this.mCapabilities &= n;
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Capabilities: ");
            stringBuilder.append(Integer.toBinaryString(this.mCapabilities));
            return stringBuilder.toString();
        }
    }

    protected static class CapabilityCallbackProxy {
        private final IImsCapabilityCallback mCallback;

        public CapabilityCallbackProxy(IImsCapabilityCallback iImsCapabilityCallback) {
            this.mCallback = iImsCapabilityCallback;
        }

        public void onChangeCapabilityConfigurationError(int n, int n2, int n3) {
            IImsCapabilityCallback iImsCapabilityCallback = this.mCallback;
            if (iImsCapabilityCallback == null) {
                return;
            }
            try {
                iImsCapabilityCallback.onChangeCapabilityConfigurationError(n, n2, n3);
            }
            catch (RemoteException remoteException) {
                Log.e(ImsFeature.LOG_TAG, "onChangeCapabilityConfigurationError called on dead binder.");
            }
        }
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface FeatureType {
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface ImsCapabilityError {
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface ImsState {
    }

}

