/*
 * Decompiled with CFR 0.145.
 */
package android.hardware;

import android.content.Context;
import android.hardware.ISensorPrivacyListener;
import android.hardware.ISensorPrivacyManager;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.util.ArrayMap;
import com.android.internal.annotations.GuardedBy;

public final class SensorPrivacyManager {
    @GuardedBy(value={"sInstanceLock"})
    private static SensorPrivacyManager sInstance;
    private static final Object sInstanceLock;
    private final Context mContext;
    private final ArrayMap<OnSensorPrivacyChangedListener, ISensorPrivacyListener> mListeners;
    private final ISensorPrivacyManager mService;

    static {
        sInstanceLock = new Object();
    }

    private SensorPrivacyManager(Context context, ISensorPrivacyManager iSensorPrivacyManager) {
        this.mContext = context;
        this.mService = iSensorPrivacyManager;
        this.mListeners = new ArrayMap();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static SensorPrivacyManager getInstance(Context object) {
        Object object2 = sInstanceLock;
        synchronized (object2) {
            Object object3 = sInstance;
            if (object3 != null) return sInstance;
            try {
                SensorPrivacyManager sensorPrivacyManager;
                object3 = ISensorPrivacyManager.Stub.asInterface(ServiceManager.getServiceOrThrow("sensor_privacy"));
                sInstance = sensorPrivacyManager = new SensorPrivacyManager((Context)object, (ISensorPrivacyManager)object3);
                return sInstance;
            }
            catch (ServiceManager.ServiceNotFoundException serviceNotFoundException) {
                object = new IllegalStateException(serviceNotFoundException);
                throw object;
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void addSensorPrivacyListener(final OnSensorPrivacyChangedListener onSensorPrivacyChangedListener) {
        ArrayMap<OnSensorPrivacyChangedListener, ISensorPrivacyListener> arrayMap = this.mListeners;
        synchronized (arrayMap) {
            ISensorPrivacyListener.Stub stub;
            ISensorPrivacyListener.Stub stub2 = stub = this.mListeners.get(onSensorPrivacyChangedListener);
            if (stub == null) {
                stub2 = new ISensorPrivacyListener.Stub(){

                    @Override
                    public void onSensorPrivacyChanged(boolean bl) {
                        onSensorPrivacyChangedListener.onSensorPrivacyChanged(bl);
                    }
                };
                this.mListeners.put(onSensorPrivacyChangedListener, stub2);
            }
            try {
                this.mService.addSensorPrivacyListener(stub2);
                return;
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
    }

    public boolean isSensorPrivacyEnabled() {
        try {
            boolean bl = this.mService.isSensorPrivacyEnabled();
            return bl;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void removeSensorPrivacyListener(OnSensorPrivacyChangedListener object) {
        ArrayMap<OnSensorPrivacyChangedListener, ISensorPrivacyListener> arrayMap = this.mListeners;
        synchronized (arrayMap) {
            object = this.mListeners.get(object);
            if (object != null) {
                this.mListeners.remove(object);
                try {
                    this.mService.removeSensorPrivacyListener((ISensorPrivacyListener)object);
                }
                catch (RemoteException remoteException) {
                    throw remoteException.rethrowFromSystemServer();
                }
            }
            return;
        }
    }

    public void setSensorPrivacy(boolean bl) {
        try {
            this.mService.setSensorPrivacy(bl);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public static interface OnSensorPrivacyChangedListener {
        public void onSensorPrivacyChanged(boolean var1);
    }

}

