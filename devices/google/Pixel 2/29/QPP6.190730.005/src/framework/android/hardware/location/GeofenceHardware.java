/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.location;

import android.annotation.SystemApi;
import android.annotation.UnsupportedAppUsage;
import android.hardware.location.GeofenceHardwareCallback;
import android.hardware.location.GeofenceHardwareMonitorCallback;
import android.hardware.location.GeofenceHardwareMonitorEvent;
import android.hardware.location.GeofenceHardwareRequest;
import android.hardware.location.GeofenceHardwareRequestParcelable;
import android.hardware.location.IGeofenceHardware;
import android.hardware.location.IGeofenceHardwareCallback;
import android.hardware.location.IGeofenceHardwareMonitorCallback;
import android.location.Location;
import android.os.Build;
import android.os.RemoteException;
import java.lang.ref.WeakReference;
import java.util.HashMap;

@SystemApi
public final class GeofenceHardware {
    public static final int GEOFENCE_ENTERED = 1;
    public static final int GEOFENCE_ERROR_ID_EXISTS = 2;
    public static final int GEOFENCE_ERROR_ID_UNKNOWN = 3;
    public static final int GEOFENCE_ERROR_INSUFFICIENT_MEMORY = 6;
    public static final int GEOFENCE_ERROR_INVALID_TRANSITION = 4;
    public static final int GEOFENCE_ERROR_TOO_MANY_GEOFENCES = 1;
    public static final int GEOFENCE_EXITED = 2;
    public static final int GEOFENCE_FAILURE = 5;
    public static final int GEOFENCE_SUCCESS = 0;
    public static final int GEOFENCE_UNCERTAIN = 4;
    public static final int MONITORING_TYPE_FUSED_HARDWARE = 1;
    public static final int MONITORING_TYPE_GPS_HARDWARE = 0;
    public static final int MONITOR_CURRENTLY_AVAILABLE = 0;
    public static final int MONITOR_CURRENTLY_UNAVAILABLE = 1;
    public static final int MONITOR_UNSUPPORTED = 2;
    static final int NUM_MONITORS = 2;
    public static final int SOURCE_TECHNOLOGY_BLUETOOTH = 16;
    public static final int SOURCE_TECHNOLOGY_CELL = 8;
    public static final int SOURCE_TECHNOLOGY_GNSS = 1;
    public static final int SOURCE_TECHNOLOGY_SENSORS = 4;
    public static final int SOURCE_TECHNOLOGY_WIFI = 2;
    private HashMap<GeofenceHardwareCallback, GeofenceHardwareCallbackWrapper> mCallbacks = new HashMap();
    private HashMap<GeofenceHardwareMonitorCallback, GeofenceHardwareMonitorCallbackWrapper> mMonitorCallbacks = new HashMap();
    private IGeofenceHardware mService;

    @UnsupportedAppUsage
    public GeofenceHardware(IGeofenceHardware iGeofenceHardware) {
        this.mService = iGeofenceHardware;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private GeofenceHardwareCallbackWrapper getCallbackWrapper(GeofenceHardwareCallback geofenceHardwareCallback) {
        HashMap<GeofenceHardwareCallback, GeofenceHardwareCallbackWrapper> hashMap = this.mCallbacks;
        synchronized (hashMap) {
            GeofenceHardwareCallbackWrapper geofenceHardwareCallbackWrapper;
            GeofenceHardwareCallbackWrapper geofenceHardwareCallbackWrapper2 = geofenceHardwareCallbackWrapper = this.mCallbacks.get(geofenceHardwareCallback);
            if (geofenceHardwareCallbackWrapper == null) {
                geofenceHardwareCallbackWrapper2 = new GeofenceHardwareCallbackWrapper(geofenceHardwareCallback);
                this.mCallbacks.put(geofenceHardwareCallback, geofenceHardwareCallbackWrapper2);
            }
            return geofenceHardwareCallbackWrapper2;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private GeofenceHardwareMonitorCallbackWrapper getMonitorCallbackWrapper(GeofenceHardwareMonitorCallback geofenceHardwareMonitorCallback) {
        HashMap<GeofenceHardwareMonitorCallback, GeofenceHardwareMonitorCallbackWrapper> hashMap = this.mMonitorCallbacks;
        synchronized (hashMap) {
            GeofenceHardwareMonitorCallbackWrapper geofenceHardwareMonitorCallbackWrapper;
            GeofenceHardwareMonitorCallbackWrapper geofenceHardwareMonitorCallbackWrapper2 = geofenceHardwareMonitorCallbackWrapper = this.mMonitorCallbacks.get(geofenceHardwareMonitorCallback);
            if (geofenceHardwareMonitorCallbackWrapper == null) {
                geofenceHardwareMonitorCallbackWrapper2 = new GeofenceHardwareMonitorCallbackWrapper(geofenceHardwareMonitorCallback);
                this.mMonitorCallbacks.put(geofenceHardwareMonitorCallback, geofenceHardwareMonitorCallbackWrapper2);
            }
            return geofenceHardwareMonitorCallbackWrapper2;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void removeCallback(GeofenceHardwareCallback geofenceHardwareCallback) {
        HashMap<GeofenceHardwareCallback, GeofenceHardwareCallbackWrapper> hashMap = this.mCallbacks;
        synchronized (hashMap) {
            this.mCallbacks.remove(geofenceHardwareCallback);
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void removeMonitorCallback(GeofenceHardwareMonitorCallback geofenceHardwareMonitorCallback) {
        HashMap<GeofenceHardwareMonitorCallback, GeofenceHardwareMonitorCallbackWrapper> hashMap = this.mMonitorCallbacks;
        synchronized (hashMap) {
            this.mMonitorCallbacks.remove(geofenceHardwareMonitorCallback);
            return;
        }
    }

    public boolean addGeofence(int n, int n2, GeofenceHardwareRequest object, GeofenceHardwareCallback geofenceHardwareCallback) {
        try {
            if (((GeofenceHardwareRequest)object).getType() == 0) {
                IGeofenceHardware iGeofenceHardware = this.mService;
                GeofenceHardwareRequestParcelable geofenceHardwareRequestParcelable = new GeofenceHardwareRequestParcelable(n, (GeofenceHardwareRequest)object);
                return iGeofenceHardware.addCircularFence(n2, geofenceHardwareRequestParcelable, this.getCallbackWrapper(geofenceHardwareCallback));
            }
            object = new IllegalArgumentException("Geofence Request type not supported");
            throw object;
        }
        catch (RemoteException remoteException) {
            return false;
        }
    }

    public int[] getMonitoringTypes() {
        try {
            int[] arrn = this.mService.getMonitoringTypes();
            return arrn;
        }
        catch (RemoteException remoteException) {
            return new int[0];
        }
    }

    public int getStatusOfMonitoringType(int n) {
        try {
            n = this.mService.getStatusOfMonitoringType(n);
            return n;
        }
        catch (RemoteException remoteException) {
            return 2;
        }
    }

    public boolean pauseGeofence(int n, int n2) {
        try {
            boolean bl = this.mService.pauseGeofence(n, n2);
            return bl;
        }
        catch (RemoteException remoteException) {
            return false;
        }
    }

    public boolean registerForMonitorStateChangeCallback(int n, GeofenceHardwareMonitorCallback geofenceHardwareMonitorCallback) {
        try {
            boolean bl = this.mService.registerForMonitorStateChangeCallback(n, this.getMonitorCallbackWrapper(geofenceHardwareMonitorCallback));
            return bl;
        }
        catch (RemoteException remoteException) {
            return false;
        }
    }

    public boolean removeGeofence(int n, int n2) {
        try {
            boolean bl = this.mService.removeGeofence(n, n2);
            return bl;
        }
        catch (RemoteException remoteException) {
            return false;
        }
    }

    public boolean resumeGeofence(int n, int n2, int n3) {
        try {
            boolean bl = this.mService.resumeGeofence(n, n2, n3);
            return bl;
        }
        catch (RemoteException remoteException) {
            return false;
        }
    }

    public boolean unregisterForMonitorStateChangeCallback(int n, GeofenceHardwareMonitorCallback geofenceHardwareMonitorCallback) {
        boolean bl;
        boolean bl2;
        block3 : {
            bl2 = false;
            bl = this.mService.unregisterForMonitorStateChangeCallback(n, this.getMonitorCallbackWrapper(geofenceHardwareMonitorCallback));
            if (!bl) break block3;
            bl2 = bl;
            try {
                this.removeMonitorCallback(geofenceHardwareMonitorCallback);
            }
            catch (RemoteException remoteException) {
                // empty catch block
            }
        }
        bl2 = bl;
        return bl2;
    }

    class GeofenceHardwareCallbackWrapper
    extends IGeofenceHardwareCallback.Stub {
        private WeakReference<GeofenceHardwareCallback> mCallback;

        GeofenceHardwareCallbackWrapper(GeofenceHardwareCallback geofenceHardwareCallback) {
            this.mCallback = new WeakReference<GeofenceHardwareCallback>(geofenceHardwareCallback);
        }

        @Override
        public void onGeofenceAdd(int n, int n2) {
            GeofenceHardwareCallback geofenceHardwareCallback = (GeofenceHardwareCallback)this.mCallback.get();
            if (geofenceHardwareCallback != null) {
                geofenceHardwareCallback.onGeofenceAdd(n, n2);
            }
        }

        @Override
        public void onGeofencePause(int n, int n2) {
            GeofenceHardwareCallback geofenceHardwareCallback = (GeofenceHardwareCallback)this.mCallback.get();
            if (geofenceHardwareCallback != null) {
                geofenceHardwareCallback.onGeofencePause(n, n2);
            }
        }

        @Override
        public void onGeofenceRemove(int n, int n2) {
            GeofenceHardwareCallback geofenceHardwareCallback = (GeofenceHardwareCallback)this.mCallback.get();
            if (geofenceHardwareCallback != null) {
                geofenceHardwareCallback.onGeofenceRemove(n, n2);
                GeofenceHardware.this.removeCallback(geofenceHardwareCallback);
            }
        }

        @Override
        public void onGeofenceResume(int n, int n2) {
            GeofenceHardwareCallback geofenceHardwareCallback = (GeofenceHardwareCallback)this.mCallback.get();
            if (geofenceHardwareCallback != null) {
                geofenceHardwareCallback.onGeofenceResume(n, n2);
            }
        }

        @Override
        public void onGeofenceTransition(int n, int n2, Location location, long l, int n3) {
            GeofenceHardwareCallback geofenceHardwareCallback = (GeofenceHardwareCallback)this.mCallback.get();
            if (geofenceHardwareCallback != null) {
                geofenceHardwareCallback.onGeofenceTransition(n, n2, location, l, n3);
            }
        }
    }

    class GeofenceHardwareMonitorCallbackWrapper
    extends IGeofenceHardwareMonitorCallback.Stub {
        private WeakReference<GeofenceHardwareMonitorCallback> mCallback;

        GeofenceHardwareMonitorCallbackWrapper(GeofenceHardwareMonitorCallback geofenceHardwareMonitorCallback) {
            this.mCallback = new WeakReference<GeofenceHardwareMonitorCallback>(geofenceHardwareMonitorCallback);
        }

        @Override
        public void onMonitoringSystemChange(GeofenceHardwareMonitorEvent geofenceHardwareMonitorEvent) {
            GeofenceHardwareMonitorCallback geofenceHardwareMonitorCallback = (GeofenceHardwareMonitorCallback)this.mCallback.get();
            if (geofenceHardwareMonitorCallback == null) {
                return;
            }
            int n = geofenceHardwareMonitorEvent.getMonitoringType();
            boolean bl = geofenceHardwareMonitorEvent.getMonitoringStatus() == 0;
            geofenceHardwareMonitorCallback.onMonitoringSystemChange(n, bl, geofenceHardwareMonitorEvent.getLocation());
            if (Build.VERSION.SDK_INT >= 21) {
                geofenceHardwareMonitorCallback.onMonitoringSystemChange(geofenceHardwareMonitorEvent);
            }
        }
    }

}

