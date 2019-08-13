/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.location;

import android.content.Context;
import android.hardware.location.GeofenceHardwareMonitorEvent;
import android.hardware.location.GeofenceHardwareRequestParcelable;
import android.hardware.location.IGeofenceHardwareCallback;
import android.hardware.location.IGeofenceHardwareMonitorCallback;
import android.location.IFusedGeofenceHardware;
import android.location.IGpsGeofenceHardware;
import android.location.Location;
import android.os.Handler;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Message;
import android.os.Parcelable;
import android.os.PowerManager;
import android.os.RemoteException;
import android.util.Log;
import android.util.SparseArray;
import java.util.ArrayList;
import java.util.Iterator;

public final class GeofenceHardwareImpl {
    private static final int ADD_GEOFENCE_CALLBACK = 2;
    private static final int CALLBACK_ADD = 2;
    private static final int CALLBACK_REMOVE = 3;
    private static final int CAPABILITY_GNSS = 1;
    private static final boolean DEBUG = Log.isLoggable("GeofenceHardwareImpl", 3);
    private static final int FIRST_VERSION_WITH_CAPABILITIES = 2;
    private static final int GEOFENCE_CALLBACK_BINDER_DIED = 6;
    private static final int GEOFENCE_STATUS = 1;
    private static final int GEOFENCE_TRANSITION_CALLBACK = 1;
    private static final int LOCATION_HAS_ACCURACY = 16;
    private static final int LOCATION_HAS_ALTITUDE = 2;
    private static final int LOCATION_HAS_BEARING = 8;
    private static final int LOCATION_HAS_LAT_LONG = 1;
    private static final int LOCATION_HAS_SPEED = 4;
    private static final int LOCATION_INVALID = 0;
    private static final int MONITOR_CALLBACK_BINDER_DIED = 4;
    private static final int PAUSE_GEOFENCE_CALLBACK = 4;
    private static final int REAPER_GEOFENCE_ADDED = 1;
    private static final int REAPER_MONITOR_CALLBACK_ADDED = 2;
    private static final int REAPER_REMOVED = 3;
    private static final int REMOVE_GEOFENCE_CALLBACK = 3;
    private static final int RESOLUTION_LEVEL_COARSE = 2;
    private static final int RESOLUTION_LEVEL_FINE = 3;
    private static final int RESOLUTION_LEVEL_NONE = 1;
    private static final int RESUME_GEOFENCE_CALLBACK = 5;
    private static final String TAG = "GeofenceHardwareImpl";
    private static GeofenceHardwareImpl sInstance;
    private final ArrayList<IGeofenceHardwareMonitorCallback>[] mCallbacks = new ArrayList[2];
    private Handler mCallbacksHandler = new Handler(){

        @Override
        public void handleMessage(Message arrayList) {
            int n = ((Message)arrayList).what;
            if (n != 1) {
                if (n != 2) {
                    if (n != 3) {
                        if (n == 4) {
                            IGeofenceHardwareMonitorCallback iGeofenceHardwareMonitorCallback = (IGeofenceHardwareMonitorCallback)((Message)arrayList).obj;
                            if (DEBUG) {
                                StringBuilder stringBuilder = new StringBuilder();
                                stringBuilder.append("Monitor callback reaped:");
                                stringBuilder.append(iGeofenceHardwareMonitorCallback);
                                Log.d(GeofenceHardwareImpl.TAG, stringBuilder.toString());
                            }
                            if ((arrayList = GeofenceHardwareImpl.this.mCallbacks[((Message)arrayList).arg1]) != null && arrayList.contains(iGeofenceHardwareMonitorCallback)) {
                                arrayList.remove(iGeofenceHardwareMonitorCallback);
                            }
                        }
                    } else {
                        n = ((Message)arrayList).arg1;
                        IGeofenceHardwareMonitorCallback iGeofenceHardwareMonitorCallback = (IGeofenceHardwareMonitorCallback)((Message)arrayList).obj;
                        arrayList = GeofenceHardwareImpl.this.mCallbacks[n];
                        if (arrayList != null) {
                            arrayList.remove(iGeofenceHardwareMonitorCallback);
                        }
                    }
                } else {
                    n = ((Message)arrayList).arg1;
                    IGeofenceHardwareMonitorCallback iGeofenceHardwareMonitorCallback = (IGeofenceHardwareMonitorCallback)((Message)arrayList).obj;
                    ArrayList arrayList2 = GeofenceHardwareImpl.this.mCallbacks[n];
                    arrayList = arrayList2;
                    if (arrayList2 == null) {
                        GeofenceHardwareImpl.access$1100((GeofenceHardwareImpl)GeofenceHardwareImpl.this)[n] = arrayList = new ArrayList();
                    }
                    if (!arrayList.contains(iGeofenceHardwareMonitorCallback)) {
                        arrayList.add(iGeofenceHardwareMonitorCallback);
                    }
                }
            } else {
                arrayList = (GeofenceHardwareMonitorEvent)((Message)arrayList).obj;
                Object object = GeofenceHardwareImpl.this.mCallbacks[((GeofenceHardwareMonitorEvent)((Object)arrayList)).getMonitoringType()];
                if (object != null) {
                    Object object2;
                    if (DEBUG) {
                        object2 = new StringBuilder();
                        ((StringBuilder)object2).append("MonitoringSystemChangeCallback: ");
                        ((StringBuilder)object2).append(arrayList);
                        Log.d(GeofenceHardwareImpl.TAG, ((StringBuilder)object2).toString());
                    }
                    object = ((ArrayList)object).iterator();
                    while (object.hasNext()) {
                        object2 = (IGeofenceHardwareMonitorCallback)object.next();
                        try {
                            object2.onMonitoringSystemChange((GeofenceHardwareMonitorEvent)((Object)arrayList));
                        }
                        catch (RemoteException remoteException) {
                            Log.d(GeofenceHardwareImpl.TAG, "Error reporting onMonitoringSystemChange.", remoteException);
                        }
                    }
                }
                GeofenceHardwareImpl.this.releaseWakeLock();
            }
        }
    };
    private int mCapabilities;
    private final Context mContext;
    private IFusedGeofenceHardware mFusedService;
    private Handler mGeofenceHandler = new Handler(){

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         * Converted monitor instructions to comments
         * Lifted jumps to return sites
         */
        @Override
        public void handleMessage(Message object) {
            int n = ((Message)object).what;
            int n2 = 0;
            switch (n) {
                default: {
                    return;
                }
                case 6: {
                    IGeofenceHardwareCallback iGeofenceHardwareCallback = (IGeofenceHardwareCallback)((Message)object).obj;
                    if (DEBUG) {
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("Geofence callback reaped:");
                        stringBuilder.append(iGeofenceHardwareCallback);
                        Log.d(GeofenceHardwareImpl.TAG, stringBuilder.toString());
                    }
                    n = ((Message)object).arg1;
                    object = GeofenceHardwareImpl.this.mGeofences;
                    // MONITORENTER : object
                    do {
                        if (n2 >= GeofenceHardwareImpl.this.mGeofences.size()) {
                            // MONITOREXIT : object
                            return;
                        }
                        if (((IGeofenceHardwareCallback)GeofenceHardwareImpl.this.mGeofences.valueAt(n2)).equals(iGeofenceHardwareCallback)) {
                            int n3 = GeofenceHardwareImpl.this.mGeofences.keyAt(n2);
                            GeofenceHardwareImpl.this.removeGeofence(GeofenceHardwareImpl.this.mGeofences.keyAt(n2), n);
                            GeofenceHardwareImpl.this.mGeofences.remove(n3);
                        }
                        ++n2;
                    } while (true);
                }
                case 5: {
                    n2 = ((Message)object).arg1;
                    SparseArray sparseArray = GeofenceHardwareImpl.this.mGeofences;
                    // MONITORENTER : sparseArray
                    IGeofenceHardwareCallback iGeofenceHardwareCallback = (IGeofenceHardwareCallback)GeofenceHardwareImpl.this.mGeofences.get(n2);
                    // MONITOREXIT : sparseArray
                    if (iGeofenceHardwareCallback != null) {
                        try {
                            iGeofenceHardwareCallback.onGeofenceResume(n2, ((Message)object).arg2);
                        }
                        catch (RemoteException remoteException) {
                            // empty catch block
                        }
                    }
                    GeofenceHardwareImpl.this.releaseWakeLock();
                    return;
                }
                case 4: {
                    n2 = ((Message)object).arg1;
                    SparseArray sparseArray = GeofenceHardwareImpl.this.mGeofences;
                    // MONITORENTER : sparseArray
                    IGeofenceHardwareCallback iGeofenceHardwareCallback = (IGeofenceHardwareCallback)GeofenceHardwareImpl.this.mGeofences.get(n2);
                    // MONITOREXIT : sparseArray
                    if (iGeofenceHardwareCallback != null) {
                        try {
                            iGeofenceHardwareCallback.onGeofencePause(n2, ((Message)object).arg2);
                        }
                        catch (RemoteException remoteException) {
                            // empty catch block
                        }
                    }
                    GeofenceHardwareImpl.this.releaseWakeLock();
                    return;
                }
                case 3: {
                    n2 = ((Message)object).arg1;
                    Object object2 = GeofenceHardwareImpl.this.mGeofences;
                    // MONITORENTER : object2
                    Iterator iterator = (IGeofenceHardwareCallback)GeofenceHardwareImpl.this.mGeofences.get(n2);
                    // MONITOREXIT : object2
                    if (iterator != null) {
                        try {
                            iterator.onGeofenceRemove(n2, ((Message)object).arg2);
                        }
                        catch (RemoteException remoteException) {
                            // empty catch block
                        }
                        object = iterator.asBinder();
                        int n4 = 0;
                        iterator = GeofenceHardwareImpl.this.mGeofences;
                        // MONITORENTER : iterator
                        GeofenceHardwareImpl.this.mGeofences.remove(n2);
                        n = 0;
                        do {
                            n2 = n4;
                            if (n >= GeofenceHardwareImpl.this.mGeofences.size()) break;
                            if (((IGeofenceHardwareCallback)GeofenceHardwareImpl.this.mGeofences.valueAt(n)).asBinder() == object) {
                                n2 = 1;
                                break;
                            }
                            ++n;
                        } while (true);
                        // MONITOREXIT : iterator
                        if (n2 == 0) {
                            iterator = GeofenceHardwareImpl.this.mReapers.iterator();
                            while (iterator.hasNext()) {
                                object2 = (Reaper)iterator.next();
                                if (((Reaper)object2).mCallback == null || ((Reaper)object2).mCallback.asBinder() != object) continue;
                                iterator.remove();
                                ((Reaper)object2).unlinkToDeath();
                                if (!DEBUG) continue;
                                Log.d(GeofenceHardwareImpl.TAG, String.format("Removed reaper %s because binder %s is no longer needed.", object2, object));
                            }
                        }
                    }
                    GeofenceHardwareImpl.this.releaseWakeLock();
                    return;
                }
                case 2: {
                    n2 = ((Message)object).arg1;
                    SparseArray sparseArray = GeofenceHardwareImpl.this.mGeofences;
                    // MONITORENTER : sparseArray
                    IGeofenceHardwareCallback iGeofenceHardwareCallback = (IGeofenceHardwareCallback)GeofenceHardwareImpl.this.mGeofences.get(n2);
                    // MONITOREXIT : sparseArray
                    if (iGeofenceHardwareCallback != null) {
                        try {
                            iGeofenceHardwareCallback.onGeofenceAdd(n2, ((Message)object).arg2);
                        }
                        catch (RemoteException remoteException) {
                            object = new StringBuilder();
                            ((StringBuilder)object).append("Remote Exception:");
                            ((StringBuilder)object).append(remoteException);
                            Log.i(GeofenceHardwareImpl.TAG, ((StringBuilder)object).toString());
                        }
                    }
                    GeofenceHardwareImpl.this.releaseWakeLock();
                    return;
                }
                case 1: 
            }
            GeofenceTransition geofenceTransition = (GeofenceTransition)((Message)object).obj;
            object = GeofenceHardwareImpl.this.mGeofences;
            // MONITORENTER : object
            IGeofenceHardwareCallback iGeofenceHardwareCallback = (IGeofenceHardwareCallback)GeofenceHardwareImpl.this.mGeofences.get(geofenceTransition.mGeofenceId);
            if (DEBUG) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("GeofenceTransistionCallback: GPS : GeofenceId: ");
                stringBuilder.append(geofenceTransition.mGeofenceId);
                stringBuilder.append(" Transition: ");
                stringBuilder.append(geofenceTransition.mTransition);
                stringBuilder.append(" Location: ");
                stringBuilder.append(geofenceTransition.mLocation);
                stringBuilder.append(":");
                stringBuilder.append(GeofenceHardwareImpl.this.mGeofences);
                Log.d(GeofenceHardwareImpl.TAG, stringBuilder.toString());
            }
            // MONITOREXIT : object
            if (iGeofenceHardwareCallback != null) {
                try {
                    iGeofenceHardwareCallback.onGeofenceTransition(geofenceTransition.mGeofenceId, geofenceTransition.mTransition, geofenceTransition.mLocation, geofenceTransition.mTimestamp, geofenceTransition.mMonitoringType);
                }
                catch (RemoteException remoteException) {
                    // empty catch block
                }
            }
            GeofenceHardwareImpl.this.releaseWakeLock();
        }
    };
    private final SparseArray<IGeofenceHardwareCallback> mGeofences = new SparseArray();
    private IGpsGeofenceHardware mGpsService;
    private Handler mReaperHandler = new Handler(){

        @Override
        public void handleMessage(Message object) {
            block6 : {
                int n;
                block4 : {
                    block5 : {
                        n = ((Message)object).what;
                        if (n == 1) break block4;
                        if (n == 2) break block5;
                        if (n != 3) break block6;
                        object = (Reaper)((Message)object).obj;
                        GeofenceHardwareImpl.this.mReapers.remove(object);
                        break block6;
                    }
                    Object object2 = (IGeofenceHardwareMonitorCallback)((Message)object).obj;
                    n = ((Message)object).arg1;
                    object = new Reaper((IGeofenceHardwareMonitorCallback)object2, n);
                    if (GeofenceHardwareImpl.this.mReapers.contains(object)) break block6;
                    GeofenceHardwareImpl.this.mReapers.add(object);
                    object2 = object2.asBinder();
                    try {
                        object2.linkToDeath((IBinder.DeathRecipient)object, 0);
                    }
                    catch (RemoteException remoteException) {}
                    break block6;
                }
                Object object3 = (IGeofenceHardwareCallback)((Message)object).obj;
                n = ((Message)object).arg1;
                object = new Reaper((IGeofenceHardwareCallback)object3, n);
                if (GeofenceHardwareImpl.this.mReapers.contains(object)) break block6;
                GeofenceHardwareImpl.this.mReapers.add(object);
                object3 = object3.asBinder();
                try {
                    object3.linkToDeath((IBinder.DeathRecipient)object, 0);
                }
                catch (RemoteException remoteException) {}
            }
        }
    };
    private final ArrayList<Reaper> mReapers = new ArrayList();
    private int[] mSupportedMonitorTypes = new int[2];
    private int mVersion = 1;
    private PowerManager.WakeLock mWakeLock;

    private GeofenceHardwareImpl(Context context) {
        this.mContext = context;
        this.setMonitorAvailability(0, 2);
        this.setMonitorAvailability(1, 2);
    }

    private void acquireWakeLock() {
        if (this.mWakeLock == null) {
            this.mWakeLock = ((PowerManager)this.mContext.getSystemService("power")).newWakeLock(1, TAG);
        }
        this.mWakeLock.acquire();
    }

    public static GeofenceHardwareImpl getInstance(Context object) {
        synchronized (GeofenceHardwareImpl.class) {
            if (sInstance == null) {
                GeofenceHardwareImpl geofenceHardwareImpl;
                sInstance = geofenceHardwareImpl = new GeofenceHardwareImpl((Context)object);
            }
            object = sInstance;
            return object;
        }
    }

    private void releaseWakeLock() {
        if (this.mWakeLock.isHeld()) {
            this.mWakeLock.release();
        }
    }

    private void reportGeofenceOperationStatus(int n, int n2, int n3) {
        this.acquireWakeLock();
        Message message = this.mGeofenceHandler.obtainMessage(n);
        message.arg1 = n2;
        message.arg2 = n3;
        message.sendToTarget();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void setMonitorAvailability(int n, int n2) {
        int[] arrn = this.mSupportedMonitorTypes;
        synchronized (arrn) {
            this.mSupportedMonitorTypes[n] = n2;
            return;
        }
    }

    private void updateFusedHardwareAvailability() {
        boolean bl;
        block5 : {
            if (this.mVersion >= 2 && (this.mCapabilities & 1) == 0) {
                bl = false;
                break block5;
            }
            bl = true;
        }
        try {
            boolean bl2;
            bl = this.mFusedService != null ? (bl2 = this.mFusedService.isSupported()) && bl : false;
        }
        catch (RemoteException remoteException) {
            Log.e(TAG, "RemoteException calling LocationManagerService");
            bl = false;
        }
        if (bl) {
            this.setMonitorAvailability(1, 0);
        }
    }

    private void updateGpsHardwareAvailability() {
        boolean bl;
        try {
            bl = this.mGpsService.isHardwareGeofenceSupported();
        }
        catch (RemoteException remoteException) {
            Log.e(TAG, "Remote Exception calling LocationManagerService");
            bl = false;
        }
        if (bl) {
            this.setMonitorAvailability(0, 0);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    public boolean addCircularFence(int n, GeofenceHardwareRequestParcelable object, IGeofenceHardwareCallback iGeofenceHardwareCallback) {
        boolean bl;
        int n2 = ((GeofenceHardwareRequestParcelable)object).getId();
        if (DEBUG) {
            Log.d(TAG, String.format("addCircularFence: monitoringType=%d, %s", n, object));
        }
        Object object2 = this.mGeofences;
        // MONITORENTER : object2
        this.mGeofences.put(n2, iGeofenceHardwareCallback);
        // MONITOREXIT : object2
        if (n != 0) {
            if (n != 1) {
                bl = false;
            } else {
                object2 = this.mFusedService;
                if (object2 == null) {
                    return false;
                }
                try {
                    object2.addGeofences(new GeofenceHardwareRequestParcelable[]{object});
                    bl = true;
                }
                catch (RemoteException remoteException) {
                    Log.e(TAG, "AddGeofence: RemoteException calling LocationManagerService");
                    bl = false;
                }
            }
        } else {
            object2 = this.mGpsService;
            if (object2 == null) {
                return false;
            }
            try {
                bl = object2.addCircularHardwareGeofence(((GeofenceHardwareRequestParcelable)object).getId(), ((GeofenceHardwareRequestParcelable)object).getLatitude(), ((GeofenceHardwareRequestParcelable)object).getLongitude(), ((GeofenceHardwareRequestParcelable)object).getRadius(), ((GeofenceHardwareRequestParcelable)object).getLastTransition(), ((GeofenceHardwareRequestParcelable)object).getMonitorTransitions(), ((GeofenceHardwareRequestParcelable)object).getNotificationResponsiveness(), ((GeofenceHardwareRequestParcelable)object).getUnknownTimer());
            }
            catch (RemoteException remoteException) {
                Log.e(TAG, "AddGeofence: Remote Exception calling LocationManagerService");
                bl = false;
            }
        }
        if (bl) {
            object = this.mReaperHandler.obtainMessage(1, iGeofenceHardwareCallback);
            ((Message)object).arg1 = n;
            this.mReaperHandler.sendMessage((Message)object);
        } else {
            object = this.mGeofences;
            // MONITORENTER : object
            this.mGeofences.remove(n2);
            // MONITOREXIT : object
        }
        if (!DEBUG) return bl;
        object = new StringBuilder();
        ((StringBuilder)object).append("addCircularFence: Result is: ");
        ((StringBuilder)object).append(bl);
        Log.d(TAG, ((StringBuilder)object).toString());
        return bl;
    }

    int getAllowedResolutionLevel(int n, int n2) {
        if (this.mContext.checkPermission("android.permission.ACCESS_FINE_LOCATION", n, n2) == 0) {
            return 3;
        }
        if (this.mContext.checkPermission("android.permission.ACCESS_COARSE_LOCATION", n, n2) == 0) {
            return 2;
        }
        return 1;
    }

    public int getCapabilitiesForMonitoringType(int n) {
        block5 : {
            block6 : {
                block4 : {
                    if (this.mSupportedMonitorTypes[n] != 0) break block4;
                    if (n == 0) break block5;
                    if (n == 1) break block6;
                }
                return 0;
            }
            if (this.mVersion >= 2) {
                return this.mCapabilities;
            }
            return 1;
        }
        return 1;
    }

    int getMonitoringResolutionLevel(int n) {
        if (n != 0) {
            if (n != 1) {
                return 1;
            }
            return 3;
        }
        return 3;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    public int[] getMonitoringTypes() {
        int[] arrn = this.mSupportedMonitorTypes;
        // MONITORENTER : arrn
        boolean bl = this.mSupportedMonitorTypes[0] != 2;
        boolean bl2 = this.mSupportedMonitorTypes[1] != 2;
        // MONITOREXIT : arrn
        if (!bl) {
            if (!bl2) return new int[0];
            return new int[]{1};
        }
        if (!bl2) return new int[]{0};
        return new int[]{0, 1};
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public int getStatusOfMonitoringType(int n) {
        int[] arrn = this.mSupportedMonitorTypes;
        synchronized (arrn) {
            if (n < this.mSupportedMonitorTypes.length && n >= 0) {
                return this.mSupportedMonitorTypes[n];
            }
            IllegalArgumentException illegalArgumentException = new IllegalArgumentException("Unknown monitoring type");
            throw illegalArgumentException;
        }
    }

    public void onCapabilities(int n) {
        this.mCapabilities = n;
        this.updateFusedHardwareAvailability();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    public boolean pauseGeofence(int n, int n2) {
        boolean bl;
        Object object;
        if (DEBUG) {
            object = new StringBuilder();
            ((StringBuilder)object).append("Pause Geofence: GeofenceId: ");
            ((StringBuilder)object).append(n);
            Log.d(TAG, ((StringBuilder)object).toString());
        }
        object = this.mGeofences;
        // MONITORENTER : object
        if (this.mGeofences.get(n) == null) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Geofence ");
            stringBuilder.append(n);
            stringBuilder.append(" not registered.");
            IllegalArgumentException illegalArgumentException = new IllegalArgumentException(stringBuilder.toString());
            throw illegalArgumentException;
        }
        // MONITOREXIT : object
        if (n2 != 0) {
            if (n2 != 1) {
                bl = false;
            } else {
                object = this.mFusedService;
                if (object == null) {
                    return false;
                }
                try {
                    object.pauseMonitoringGeofence(n);
                    bl = true;
                }
                catch (RemoteException remoteException) {
                    Log.e(TAG, "PauseGeofence: RemoteException calling LocationManagerService");
                    bl = false;
                }
            }
        } else {
            object = this.mGpsService;
            if (object == null) {
                return false;
            }
            try {
                bl = object.pauseHardwareGeofence(n);
            }
            catch (RemoteException remoteException) {
                Log.e(TAG, "PauseGeofence: Remote Exception calling LocationManagerService");
                bl = false;
            }
        }
        if (!DEBUG) return bl;
        object = new StringBuilder();
        ((StringBuilder)object).append("pauseGeofence: Result is: ");
        ((StringBuilder)object).append(bl);
        Log.d(TAG, ((StringBuilder)object).toString());
        return bl;
    }

    public boolean registerForMonitorStateChangeCallback(int n, IGeofenceHardwareMonitorCallback object) {
        Message message = this.mReaperHandler.obtainMessage(2, object);
        message.arg1 = n;
        this.mReaperHandler.sendMessage(message);
        object = this.mCallbacksHandler.obtainMessage(2, object);
        ((Message)object).arg1 = n;
        this.mCallbacksHandler.sendMessage((Message)object);
        return true;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    public boolean removeGeofence(int n, int n2) {
        boolean bl;
        Object object;
        if (DEBUG) {
            object = new StringBuilder();
            ((StringBuilder)object).append("Remove Geofence: GeofenceId: ");
            ((StringBuilder)object).append(n);
            Log.d(TAG, ((StringBuilder)object).toString());
        }
        object = this.mGeofences;
        // MONITORENTER : object
        if (this.mGeofences.get(n) == null) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Geofence ");
            stringBuilder.append(n);
            stringBuilder.append(" not registered.");
            IllegalArgumentException illegalArgumentException = new IllegalArgumentException(stringBuilder.toString());
            throw illegalArgumentException;
        }
        // MONITOREXIT : object
        if (n2 != 0) {
            if (n2 != 1) {
                bl = false;
            } else {
                object = this.mFusedService;
                if (object == null) {
                    return false;
                }
                try {
                    object.removeGeofences(new int[]{n});
                    bl = true;
                }
                catch (RemoteException remoteException) {
                    Log.e(TAG, "RemoveGeofence: RemoteException calling LocationManagerService");
                    bl = false;
                }
            }
        } else {
            object = this.mGpsService;
            if (object == null) {
                return false;
            }
            try {
                bl = object.removeHardwareGeofence(n);
            }
            catch (RemoteException remoteException) {
                Log.e(TAG, "RemoveGeofence: Remote Exception calling LocationManagerService");
                bl = false;
            }
        }
        if (!DEBUG) return bl;
        object = new StringBuilder();
        ((StringBuilder)object).append("removeGeofence: Result is: ");
        ((StringBuilder)object).append(bl);
        Log.d(TAG, ((StringBuilder)object).toString());
        return bl;
    }

    public void reportGeofenceAddStatus(int n, int n2) {
        if (DEBUG) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("AddCallback| id:");
            stringBuilder.append(n);
            stringBuilder.append(", status:");
            stringBuilder.append(n2);
            Log.d(TAG, stringBuilder.toString());
        }
        this.reportGeofenceOperationStatus(2, n, n2);
    }

    public void reportGeofenceMonitorStatus(int n, int n2, Location parcelable, int n3) {
        this.setMonitorAvailability(n, n2);
        this.acquireWakeLock();
        parcelable = new GeofenceHardwareMonitorEvent(n, n2, n3, (Location)parcelable);
        this.mCallbacksHandler.obtainMessage(1, parcelable).sendToTarget();
    }

    public void reportGeofencePauseStatus(int n, int n2) {
        if (DEBUG) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("PauseCallbac| id:");
            stringBuilder.append(n);
            stringBuilder.append(", status");
            stringBuilder.append(n2);
            Log.d(TAG, stringBuilder.toString());
        }
        this.reportGeofenceOperationStatus(4, n, n2);
    }

    public void reportGeofenceRemoveStatus(int n, int n2) {
        if (DEBUG) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("RemoveCallback| id:");
            stringBuilder.append(n);
            stringBuilder.append(", status:");
            stringBuilder.append(n2);
            Log.d(TAG, stringBuilder.toString());
        }
        this.reportGeofenceOperationStatus(3, n, n2);
    }

    public void reportGeofenceResumeStatus(int n, int n2) {
        if (DEBUG) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("ResumeCallback| id:");
            stringBuilder.append(n);
            stringBuilder.append(", status:");
            stringBuilder.append(n2);
            Log.d(TAG, stringBuilder.toString());
        }
        this.reportGeofenceOperationStatus(5, n, n2);
    }

    public void reportGeofenceTransition(int n, Location object, int n2, long l, int n3, int n4) {
        if (object == null) {
            Log.e(TAG, String.format("Invalid Geofence Transition: location=null", new Object[0]));
            return;
        }
        if (DEBUG) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("GeofenceTransition| ");
            stringBuilder.append(object);
            stringBuilder.append(", transition:");
            stringBuilder.append(n2);
            stringBuilder.append(", transitionTimestamp:");
            stringBuilder.append(l);
            stringBuilder.append(", monitoringType:");
            stringBuilder.append(n3);
            stringBuilder.append(", sourcesUsed:");
            stringBuilder.append(n4);
            Log.d(TAG, stringBuilder.toString());
        }
        object = new GeofenceTransition(n, n2, l, (Location)object, n3, n4);
        this.acquireWakeLock();
        this.mGeofenceHandler.obtainMessage(1, object).sendToTarget();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    public boolean resumeGeofence(int n, int n2, int n3) {
        Object object;
        boolean bl;
        if (DEBUG) {
            object = new StringBuilder();
            ((StringBuilder)object).append("Resume Geofence: GeofenceId: ");
            ((StringBuilder)object).append(n);
            Log.d(TAG, ((StringBuilder)object).toString());
        }
        object = this.mGeofences;
        // MONITORENTER : object
        if (this.mGeofences.get(n) == null) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Geofence ");
            stringBuilder.append(n);
            stringBuilder.append(" not registered.");
            IllegalArgumentException illegalArgumentException = new IllegalArgumentException(stringBuilder.toString());
            throw illegalArgumentException;
        }
        // MONITOREXIT : object
        if (n2 != 0) {
            if (n2 != 1) {
                bl = false;
            } else {
                object = this.mFusedService;
                if (object == null) {
                    return false;
                }
                try {
                    object.resumeMonitoringGeofence(n, n3);
                    bl = true;
                }
                catch (RemoteException remoteException) {
                    Log.e(TAG, "ResumeGeofence: RemoteException calling LocationManagerService");
                    bl = false;
                }
            }
        } else {
            object = this.mGpsService;
            if (object == null) {
                return false;
            }
            try {
                bl = object.resumeHardwareGeofence(n, n3);
            }
            catch (RemoteException remoteException) {
                Log.e(TAG, "ResumeGeofence: Remote Exception calling LocationManagerService");
                bl = false;
            }
        }
        if (!DEBUG) return bl;
        object = new StringBuilder();
        ((StringBuilder)object).append("resumeGeofence: Result is: ");
        ((StringBuilder)object).append(bl);
        Log.d(TAG, ((StringBuilder)object).toString());
        return bl;
    }

    public void setFusedGeofenceHardware(IFusedGeofenceHardware iFusedGeofenceHardware) {
        if (this.mFusedService == null) {
            this.mFusedService = iFusedGeofenceHardware;
            this.updateFusedHardwareAvailability();
        } else if (iFusedGeofenceHardware == null) {
            this.mFusedService = null;
            Log.w(TAG, "Fused Geofence Hardware service seems to have crashed");
        } else {
            Log.e(TAG, "Error: FusedService being set again");
        }
    }

    public void setGpsHardwareGeofence(IGpsGeofenceHardware iGpsGeofenceHardware) {
        if (this.mGpsService == null) {
            this.mGpsService = iGpsGeofenceHardware;
            this.updateGpsHardwareAvailability();
        } else if (iGpsGeofenceHardware == null) {
            this.mGpsService = null;
            Log.w(TAG, "GPS Geofence Hardware service seems to have crashed");
        } else {
            Log.e(TAG, "Error: GpsService being set again.");
        }
    }

    public void setVersion(int n) {
        this.mVersion = n;
        this.updateFusedHardwareAvailability();
    }

    public boolean unregisterForMonitorStateChangeCallback(int n, IGeofenceHardwareMonitorCallback object) {
        object = this.mCallbacksHandler.obtainMessage(3, object);
        ((Message)object).arg1 = n;
        this.mCallbacksHandler.sendMessage((Message)object);
        return true;
    }

    private class GeofenceTransition {
        private int mGeofenceId;
        private Location mLocation;
        private int mMonitoringType;
        private int mSourcesUsed;
        private long mTimestamp;
        private int mTransition;

        GeofenceTransition(int n, int n2, long l, Location location, int n3, int n4) {
            this.mGeofenceId = n;
            this.mTransition = n2;
            this.mTimestamp = l;
            this.mLocation = location;
            this.mMonitoringType = n3;
            this.mSourcesUsed = n4;
        }
    }

    class Reaper
    implements IBinder.DeathRecipient {
        private IGeofenceHardwareCallback mCallback;
        private IGeofenceHardwareMonitorCallback mMonitorCallback;
        private int mMonitoringType;

        Reaper(IGeofenceHardwareCallback iGeofenceHardwareCallback, int n) {
            this.mCallback = iGeofenceHardwareCallback;
            this.mMonitoringType = n;
        }

        Reaper(IGeofenceHardwareMonitorCallback iGeofenceHardwareMonitorCallback, int n) {
            this.mMonitorCallback = iGeofenceHardwareMonitorCallback;
            this.mMonitoringType = n;
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        private boolean binderEquals(IInterface iInterface, IInterface iInterface2) {
            boolean bl = true;
            boolean bl2 = false;
            if (iInterface == null) {
                if (iInterface2 != null) return false;
                return bl;
            }
            if (iInterface2 == null) {
                return bl2;
            }
            if (iInterface.asBinder() != iInterface2.asBinder()) return bl2;
            return true;
        }

        private boolean callbackEquals(IGeofenceHardwareCallback iGeofenceHardwareCallback) {
            IGeofenceHardwareCallback iGeofenceHardwareCallback2 = this.mCallback;
            boolean bl = iGeofenceHardwareCallback2 != null && iGeofenceHardwareCallback2.asBinder() == iGeofenceHardwareCallback.asBinder();
            return bl;
        }

        private boolean unlinkToDeath() {
            IInterface iInterface = this.mMonitorCallback;
            if (iInterface != null) {
                return iInterface.asBinder().unlinkToDeath(this, 0);
            }
            iInterface = this.mCallback;
            if (iInterface != null) {
                return iInterface.asBinder().unlinkToDeath(this, 0);
            }
            return true;
        }

        @Override
        public void binderDied() {
            Message message;
            if (this.mCallback != null) {
                message = GeofenceHardwareImpl.this.mGeofenceHandler.obtainMessage(6, this.mCallback);
                message.arg1 = this.mMonitoringType;
                GeofenceHardwareImpl.this.mGeofenceHandler.sendMessage(message);
            } else if (this.mMonitorCallback != null) {
                message = GeofenceHardwareImpl.this.mCallbacksHandler.obtainMessage(4, this.mMonitorCallback);
                message.arg1 = this.mMonitoringType;
                GeofenceHardwareImpl.this.mCallbacksHandler.sendMessage(message);
            }
            message = GeofenceHardwareImpl.this.mReaperHandler.obtainMessage(3, this);
            GeofenceHardwareImpl.this.mReaperHandler.sendMessage(message);
        }

        public boolean equals(Object object) {
            boolean bl;
            block2 : {
                bl = false;
                if (object == null) {
                    return false;
                }
                if (object == this) {
                    return true;
                }
                object = (Reaper)object;
                if (!this.binderEquals(((Reaper)object).mCallback, this.mCallback) || !this.binderEquals(((Reaper)object).mMonitorCallback, this.mMonitorCallback) || ((Reaper)object).mMonitoringType != this.mMonitoringType) break block2;
                bl = true;
            }
            return bl;
        }

        public int hashCode() {
            int n;
            int n2;
            block0 : {
                IInterface iInterface = this.mCallback;
                n = 0;
                n2 = iInterface != null ? iInterface.asBinder().hashCode() : 0;
                iInterface = this.mMonitorCallback;
                if (iInterface == null) break block0;
                n = iInterface.asBinder().hashCode();
            }
            return ((17 * 31 + n2) * 31 + n) * 31 + this.mMonitoringType;
        }
    }

}

