/*
 * Decompiled with CFR 0.145.
 */
package android.telephony.ims.compat.feature;

import android.annotation.UnsupportedAppUsage;
import android.content.Context;
import android.os.IInterface;
import android.os.RemoteException;
import android.util.Log;
import com.android.ims.internal.IImsFeatureStatusCallback;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Collections;
import java.util.Iterator;
import java.util.Set;
import java.util.WeakHashMap;

public abstract class ImsFeature {
    public static final int EMERGENCY_MMTEL = 0;
    public static final int INVALID = -1;
    private static final String LOG_TAG = "ImsFeature";
    public static final int MAX = 3;
    public static final int MMTEL = 1;
    public static final int RCS = 2;
    public static final int STATE_INITIALIZING = 1;
    public static final int STATE_NOT_AVAILABLE = 0;
    public static final int STATE_READY = 2;
    protected Context mContext;
    private int mSlotId = -1;
    private int mState = 0;
    private final Set<IImsFeatureStatusCallback> mStatusCallbacks = Collections.newSetFromMap(new WeakHashMap());

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void notifyFeatureState(int n) {
        Set<IImsFeatureStatusCallback> set = this.mStatusCallbacks;
        synchronized (set) {
            Iterator<IImsFeatureStatusCallback> iterator = this.mStatusCallbacks.iterator();
            while (iterator.hasNext()) {
                StringBuilder stringBuilder;
                IImsFeatureStatusCallback iImsFeatureStatusCallback = iterator.next();
                try {
                    stringBuilder = new StringBuilder();
                    stringBuilder.append("notifying ImsFeatureState=");
                    stringBuilder.append(n);
                    Log.i(LOG_TAG, stringBuilder.toString());
                    iImsFeatureStatusCallback.notifyImsFeatureStatus(n);
                }
                catch (RemoteException remoteException) {
                    iterator.remove();
                    stringBuilder = new StringBuilder();
                    stringBuilder.append("Couldn't notify feature state: ");
                    stringBuilder.append(remoteException.getMessage());
                    Log.w(LOG_TAG, stringBuilder.toString());
                    continue;
                }
                break;
            }
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
    public void addImsFeatureStatusCallback(IImsFeatureStatusCallback iImsFeatureStatusCallback) {
        if (iImsFeatureStatusCallback == null) {
            return;
        }
        try {
            iImsFeatureStatusCallback.notifyImsFeatureStatus(this.mState);
            Set<IImsFeatureStatusCallback> set = this.mStatusCallbacks;
            // MONITORENTER : set
            this.mStatusCallbacks.add(iImsFeatureStatusCallback);
        }
        catch (RemoteException remoteException) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Couldn't notify feature state: ");
            stringBuilder.append(remoteException.getMessage());
            Log.w(LOG_TAG, stringBuilder.toString());
        }
        // MONITOREXIT : set
        return;
    }

    public abstract IInterface getBinder();

    @UnsupportedAppUsage
    public int getFeatureState() {
        return this.mState;
    }

    public abstract void onFeatureReady();

    public abstract void onFeatureRemoved();

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void removeImsFeatureStatusCallback(IImsFeatureStatusCallback iImsFeatureStatusCallback) {
        if (iImsFeatureStatusCallback == null) {
            return;
        }
        Set<IImsFeatureStatusCallback> set = this.mStatusCallbacks;
        synchronized (set) {
            this.mStatusCallbacks.remove(iImsFeatureStatusCallback);
            return;
        }
    }

    public void setContext(Context context) {
        this.mContext = context;
    }

    @UnsupportedAppUsage
    protected final void setFeatureState(int n) {
        if (this.mState != n) {
            this.mState = n;
            this.notifyFeatureState(n);
        }
    }

    public void setSlotId(int n) {
        this.mSlotId = n;
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface ImsState {
    }

}

