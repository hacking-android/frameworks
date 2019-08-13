/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.location;

import android.content.Context;
import android.hardware.location.ActivityChangedEvent;
import android.hardware.location.ActivityRecognitionEvent;
import android.hardware.location.IActivityRecognitionHardware;
import android.hardware.location.IActivityRecognitionHardwareSink;
import android.os.IInterface;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.text.TextUtils;
import android.util.Log;

public class ActivityRecognitionHardware
extends IActivityRecognitionHardware.Stub {
    private static final boolean DEBUG = Log.isLoggable("ActivityRecognitionHW", 3);
    private static final String ENFORCE_HW_PERMISSION_MESSAGE = "Permission 'android.permission.LOCATION_HARDWARE' not granted to access ActivityRecognitionHardware";
    private static final int EVENT_TYPE_COUNT = 3;
    private static final int EVENT_TYPE_DISABLED = 0;
    private static final int EVENT_TYPE_ENABLED = 1;
    private static final String HARDWARE_PERMISSION = "android.permission.LOCATION_HARDWARE";
    private static final int INVALID_ACTIVITY_TYPE = -1;
    private static final int NATIVE_SUCCESS_RESULT = 0;
    private static final String TAG = "ActivityRecognitionHW";
    private static ActivityRecognitionHardware sSingletonInstance;
    private static final Object sSingletonInstanceLock;
    private final Context mContext;
    private final SinkList mSinks = new SinkList();
    private final String[] mSupportedActivities;
    private final int mSupportedActivitiesCount;
    private final int[][] mSupportedActivitiesEnabledEvents;

    static {
        sSingletonInstanceLock = new Object();
        ActivityRecognitionHardware.nativeClassInit();
    }

    private ActivityRecognitionHardware(Context context) {
        this.nativeInitialize();
        this.mContext = context;
        this.mSupportedActivities = this.fetchSupportedActivities();
        this.mSupportedActivitiesCount = this.mSupportedActivities.length;
        this.mSupportedActivitiesEnabledEvents = new int[this.mSupportedActivitiesCount][3];
    }

    private void checkPermissions() {
        this.mContext.enforceCallingPermission(HARDWARE_PERMISSION, ENFORCE_HW_PERMISSION_MESSAGE);
    }

    private String[] fetchSupportedActivities() {
        String[] arrstring = this.nativeGetSupportedActivities();
        if (arrstring != null) {
            return arrstring;
        }
        return new String[0];
    }

    private String getActivityName(int n) {
        String[] arrstring;
        if (n >= 0 && n < (arrstring = this.mSupportedActivities).length) {
            return arrstring[n];
        }
        Log.e(TAG, String.format("Invalid ActivityType: %d, SupportedActivities: %d", n, this.mSupportedActivities.length));
        return null;
    }

    private int getActivityType(String string2) {
        if (TextUtils.isEmpty(string2)) {
            return -1;
        }
        int n = this.mSupportedActivities.length;
        for (int i = 0; i < n; ++i) {
            if (!string2.equals(this.mSupportedActivities[i])) continue;
            return i;
        }
        return -1;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static ActivityRecognitionHardware getInstance(Context object) {
        Object object2 = sSingletonInstanceLock;
        synchronized (object2) {
            ActivityRecognitionHardware activityRecognitionHardware;
            if (sSingletonInstance != null) return sSingletonInstance;
            sSingletonInstance = activityRecognitionHardware = new ActivityRecognitionHardware((Context)object);
            return sSingletonInstance;
        }
    }

    public static boolean isSupported() {
        return ActivityRecognitionHardware.nativeIsSupported();
    }

    private static native void nativeClassInit();

    private native int nativeDisableActivityEvent(int var1, int var2);

    private native int nativeEnableActivityEvent(int var1, int var2, long var3);

    private native int nativeFlush();

    private native String[] nativeGetSupportedActivities();

    private native void nativeInitialize();

    private static native boolean nativeIsSupported();

    private native void nativeRelease();

    private void onActivityChanged(Event[] object) {
        if (object != null && ((Event[])object).length != 0) {
            int n;
            int n2 = ((Event[])object).length;
            Object object2 = new ActivityRecognitionEvent[n2];
            for (n = 0; n < n2; ++n) {
                Event event = object[n];
                object2[n] = new ActivityRecognitionEvent(this.getActivityName(event.activity), event.type, event.timestamp);
            }
            object = new ActivityChangedEvent((ActivityRecognitionEvent[])object2);
            n2 = this.mSinks.beginBroadcast();
            for (n = 0; n < n2; ++n) {
                object2 = (IActivityRecognitionHardwareSink)this.mSinks.getBroadcastItem(n);
                try {
                    object2.onActivityChanged((ActivityChangedEvent)object);
                    continue;
                }
                catch (RemoteException remoteException) {
                    Log.e("ActivityRecognitionHW", "Error delivering activity changed event.", remoteException);
                }
            }
            this.mSinks.finishBroadcast();
            return;
        }
        if (DEBUG) {
            Log.d("ActivityRecognitionHW", "No events to broadcast for onActivityChanged.");
        }
    }

    @Override
    public boolean disableActivityEvent(String string2, int n) {
        this.checkPermissions();
        int n2 = this.getActivityType(string2);
        if (n2 == -1) {
            return false;
        }
        if (this.nativeDisableActivityEvent(n2, n) == 0) {
            this.mSupportedActivitiesEnabledEvents[n2][n] = 0;
            return true;
        }
        return false;
    }

    @Override
    public boolean enableActivityEvent(String string2, int n, long l) {
        this.checkPermissions();
        int n2 = this.getActivityType(string2);
        if (n2 == -1) {
            return false;
        }
        if (this.nativeEnableActivityEvent(n2, n, l) == 0) {
            this.mSupportedActivitiesEnabledEvents[n2][n] = 1;
            return true;
        }
        return false;
    }

    @Override
    public boolean flush() {
        this.checkPermissions();
        boolean bl = this.nativeFlush() == 0;
        return bl;
    }

    @Override
    public String[] getSupportedActivities() {
        this.checkPermissions();
        return this.mSupportedActivities;
    }

    @Override
    public boolean isActivitySupported(String string2) {
        this.checkPermissions();
        boolean bl = this.getActivityType(string2) != -1;
        return bl;
    }

    @Override
    public boolean registerSink(IActivityRecognitionHardwareSink iActivityRecognitionHardwareSink) {
        this.checkPermissions();
        return this.mSinks.register(iActivityRecognitionHardwareSink);
    }

    @Override
    public boolean unregisterSink(IActivityRecognitionHardwareSink iActivityRecognitionHardwareSink) {
        this.checkPermissions();
        return this.mSinks.unregister(iActivityRecognitionHardwareSink);
    }

    private static class Event {
        public int activity;
        public long timestamp;
        public int type;

        private Event() {
        }
    }

    private class SinkList
    extends RemoteCallbackList<IActivityRecognitionHardwareSink> {
        private SinkList() {
        }

        private void disableActivityEventIfEnabled(int n, int n2) {
            if (ActivityRecognitionHardware.this.mSupportedActivitiesEnabledEvents[n][n2] != 1) {
                return;
            }
            int n3 = ActivityRecognitionHardware.this.nativeDisableActivityEvent(n, n2);
            ActivityRecognitionHardware.access$400((ActivityRecognitionHardware)ActivityRecognitionHardware.this)[n][n2] = 0;
            Log.e(ActivityRecognitionHardware.TAG, String.format("DisableActivityEvent: activityType=%d, eventType=%d, result=%d", n, n2, n3));
        }

        @Override
        public void onCallbackDied(IActivityRecognitionHardwareSink object) {
            int n = ActivityRecognitionHardware.this.mSinks.getRegisteredCallbackCount();
            if (DEBUG) {
                object = new StringBuilder();
                ((StringBuilder)object).append("RegisteredCallbackCount: ");
                ((StringBuilder)object).append(n);
                Log.d(ActivityRecognitionHardware.TAG, ((StringBuilder)object).toString());
            }
            if (n != 0) {
                return;
            }
            for (n = 0; n < ActivityRecognitionHardware.this.mSupportedActivitiesCount; ++n) {
                for (int i = 0; i < 3; ++i) {
                    this.disableActivityEventIfEnabled(n, i);
                }
            }
        }
    }

}

