/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  libcore.timezone.ZoneInfoDB
 */
package android.app;

import android.annotation.SystemApi;
import android.annotation.UnsupportedAppUsage;
import android.app.IAlarmCompleteListener;
import android.app.IAlarmListener;
import android.app.IAlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import android.os.WorkSource;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.Log;
import android.util.proto.ProtoOutputStream;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import libcore.timezone.ZoneInfoDB;

public class AlarmManager {
    public static final String ACTION_NEXT_ALARM_CLOCK_CHANGED = "android.app.action.NEXT_ALARM_CLOCK_CHANGED";
    public static final int ELAPSED_REALTIME = 3;
    public static final int ELAPSED_REALTIME_WAKEUP = 2;
    public static final int FLAG_ALLOW_WHILE_IDLE = 4;
    @UnsupportedAppUsage
    public static final int FLAG_ALLOW_WHILE_IDLE_UNRESTRICTED = 8;
    @UnsupportedAppUsage
    public static final int FLAG_IDLE_UNTIL = 16;
    @UnsupportedAppUsage
    public static final int FLAG_STANDALONE = 1;
    @UnsupportedAppUsage
    public static final int FLAG_WAKE_FROM_IDLE = 2;
    public static final long INTERVAL_DAY = 86400000L;
    public static final long INTERVAL_FIFTEEN_MINUTES = 900000L;
    public static final long INTERVAL_HALF_DAY = 43200000L;
    public static final long INTERVAL_HALF_HOUR = 1800000L;
    public static final long INTERVAL_HOUR = 3600000L;
    public static final int RTC = 1;
    public static final int RTC_WAKEUP = 0;
    private static final String TAG = "AlarmManager";
    @UnsupportedAppUsage
    public static final long WINDOW_EXACT = 0L;
    @UnsupportedAppUsage
    public static final long WINDOW_HEURISTIC = -1L;
    private static ArrayMap<OnAlarmListener, ListenerWrapper> sWrappers;
    private final boolean mAlwaysExact;
    private final Context mContext;
    private final Handler mMainThreadHandler;
    private final String mPackageName;
    @UnsupportedAppUsage
    private final IAlarmManager mService;
    private final int mTargetSdkVersion;

    AlarmManager(IAlarmManager iAlarmManager, Context context) {
        this.mService = iAlarmManager;
        this.mContext = context;
        this.mPackageName = context.getPackageName();
        this.mTargetSdkVersion = context.getApplicationInfo().targetSdkVersion;
        boolean bl = this.mTargetSdkVersion < 19;
        this.mAlwaysExact = bl;
        this.mMainThreadHandler = new Handler(context.getMainLooper());
    }

    private long legacyExactLength() {
        long l = this.mAlwaysExact ? 0L : -1L;
        return l;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    private void setImpl(int n, long l, long l2, long l3, int n2, PendingIntent pendingIntent, OnAlarmListener onAlarmListener, String string2, Handler handler, WorkSource workSource, AlarmClockInfo alarmClockInfo) {
        Object object;
        if (l < 0L) {
            l = 0L;
        }
        if (onAlarmListener != null) {
            // MONITORENTER : android.app.AlarmManager.class
            if (sWrappers == null) {
                object = new ArrayMap();
                sWrappers = object;
            }
            ListenerWrapper listenerWrapper = sWrappers.get(onAlarmListener);
            object = listenerWrapper;
            if (listenerWrapper == null) {
                object = new ListenerWrapper(onAlarmListener);
                sWrappers.put(onAlarmListener, (ListenerWrapper)object);
            }
            // MONITOREXIT : android.app.AlarmManager.class
            if (handler == null) {
                handler = this.mMainThreadHandler;
            }
            ((ListenerWrapper)object).setHandler(handler);
        } else {
            object = null;
        }
        try {
            this.mService.set(this.mPackageName, n, l, l2, l3, n2, pendingIntent, (IAlarmListener)object, string2, workSource, alarmClockInfo);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    public void cancel(OnAlarmListener onAlarmListener) {
        if (onAlarmListener == null) throw new NullPointerException("cancel() called with a null OnAlarmListener");
        Object object = null;
        // MONITORENTER : android.app.AlarmManager.class
        if (sWrappers != null) {
            object = sWrappers.get(onAlarmListener);
        }
        // MONITOREXIT : android.app.AlarmManager.class
        if (object == null) {
            object = new StringBuilder();
            ((StringBuilder)object).append("Unrecognized alarm listener ");
            ((StringBuilder)object).append(onAlarmListener);
            Log.w(TAG, ((StringBuilder)object).toString());
            return;
        }
        ((ListenerWrapper)object).cancel();
    }

    public void cancel(PendingIntent pendingIntent) {
        if (pendingIntent == null) {
            if (this.mTargetSdkVersion < 24) {
                Log.e(TAG, "cancel() called with a null PendingIntent");
                return;
            }
            throw new NullPointerException("cancel() called with a null PendingIntent");
        }
        try {
            this.mService.remove(pendingIntent, null);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public AlarmClockInfo getNextAlarmClock() {
        return this.getNextAlarmClock(this.mContext.getUserId());
    }

    public AlarmClockInfo getNextAlarmClock(int n) {
        try {
            AlarmClockInfo alarmClockInfo = this.mService.getNextAlarmClock(n);
            return alarmClockInfo;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public long getNextWakeFromIdleTime() {
        try {
            long l = this.mService.getNextWakeFromIdleTime();
            return l;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public void set(int n, long l, long l2, long l3, OnAlarmListener onAlarmListener, Handler handler, WorkSource workSource) {
        this.setImpl(n, l, l2, l3, 0, null, onAlarmListener, null, handler, workSource, null);
    }

    @SystemApi
    public void set(int n, long l, long l2, long l3, PendingIntent pendingIntent, WorkSource workSource) {
        this.setImpl(n, l, l2, l3, 0, pendingIntent, null, null, null, workSource, null);
    }

    @UnsupportedAppUsage
    public void set(int n, long l, long l2, long l3, String string2, OnAlarmListener onAlarmListener, Handler handler, WorkSource workSource) {
        this.setImpl(n, l, l2, l3, 0, null, onAlarmListener, string2, handler, workSource, null);
    }

    public void set(int n, long l, PendingIntent pendingIntent) {
        this.setImpl(n, l, this.legacyExactLength(), 0L, 0, pendingIntent, null, null, null, null, null);
    }

    public void set(int n, long l, String string2, OnAlarmListener onAlarmListener, Handler handler) {
        this.setImpl(n, l, this.legacyExactLength(), 0L, 0, null, onAlarmListener, string2, handler, null, null);
    }

    public void setAlarmClock(AlarmClockInfo alarmClockInfo, PendingIntent pendingIntent) {
        this.setImpl(0, alarmClockInfo.getTriggerTime(), 0L, 0L, 0, pendingIntent, null, null, null, null, alarmClockInfo);
    }

    public void setAndAllowWhileIdle(int n, long l, PendingIntent pendingIntent) {
        this.setImpl(n, l, -1L, 0L, 4, pendingIntent, null, null, null, null, null);
    }

    public void setExact(int n, long l, PendingIntent pendingIntent) {
        this.setImpl(n, l, 0L, 0L, 0, pendingIntent, null, null, null, null, null);
    }

    public void setExact(int n, long l, String string2, OnAlarmListener onAlarmListener, Handler handler) {
        this.setImpl(n, l, 0L, 0L, 0, null, onAlarmListener, string2, handler, null, null);
    }

    public void setExactAndAllowWhileIdle(int n, long l, PendingIntent pendingIntent) {
        this.setImpl(n, l, 0L, 0L, 4, pendingIntent, null, null, null, null, null);
    }

    public void setIdleUntil(int n, long l, String string2, OnAlarmListener onAlarmListener, Handler handler) {
        this.setImpl(n, l, 0L, 0L, 16, null, onAlarmListener, string2, handler, null, null);
    }

    public void setInexactRepeating(int n, long l, long l2, PendingIntent pendingIntent) {
        this.setImpl(n, l, -1L, l2, 0, pendingIntent, null, null, null, null, null);
    }

    public void setRepeating(int n, long l, long l2, PendingIntent pendingIntent) {
        this.setImpl(n, l, this.legacyExactLength(), l2, 0, pendingIntent, null, null, null, null, null);
    }

    public void setTime(long l) {
        try {
            this.mService.setTime(l);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public void setTimeZone(String string2) {
        if (TextUtils.isEmpty(string2)) {
            return;
        }
        if (this.mTargetSdkVersion >= 23) {
            boolean bl = false;
            try {
                boolean bl2;
                bl = bl2 = ZoneInfoDB.getInstance().hasTimeZone(string2);
            }
            catch (IOException iOException) {
                // empty catch block
            }
            if (!bl) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Timezone: ");
                stringBuilder.append(string2);
                stringBuilder.append(" is not an Olson ID");
                throw new IllegalArgumentException(stringBuilder.toString());
            }
        }
        try {
            this.mService.setTimeZone(string2);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public void setWindow(int n, long l, long l2, PendingIntent pendingIntent) {
        this.setImpl(n, l, l2, 0L, 0, pendingIntent, null, null, null, null, null);
    }

    public void setWindow(int n, long l, long l2, String string2, OnAlarmListener onAlarmListener, Handler handler) {
        this.setImpl(n, l, l2, 0L, 0, null, onAlarmListener, string2, handler, null, null);
    }

    public static final class AlarmClockInfo
    implements Parcelable {
        public static final Parcelable.Creator<AlarmClockInfo> CREATOR = new Parcelable.Creator<AlarmClockInfo>(){

            @Override
            public AlarmClockInfo createFromParcel(Parcel parcel) {
                return new AlarmClockInfo(parcel);
            }

            public AlarmClockInfo[] newArray(int n) {
                return new AlarmClockInfo[n];
            }
        };
        private final PendingIntent mShowIntent;
        private final long mTriggerTime;

        public AlarmClockInfo(long l, PendingIntent pendingIntent) {
            this.mTriggerTime = l;
            this.mShowIntent = pendingIntent;
        }

        AlarmClockInfo(Parcel parcel) {
            this.mTriggerTime = parcel.readLong();
            this.mShowIntent = (PendingIntent)parcel.readParcelable(PendingIntent.class.getClassLoader());
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public PendingIntent getShowIntent() {
            return this.mShowIntent;
        }

        public long getTriggerTime() {
            return this.mTriggerTime;
        }

        @Override
        public void writeToParcel(Parcel parcel, int n) {
            parcel.writeLong(this.mTriggerTime);
            parcel.writeParcelable(this.mShowIntent, n);
        }

        public void writeToProto(ProtoOutputStream protoOutputStream, long l) {
            l = protoOutputStream.start(l);
            protoOutputStream.write(1112396529665L, this.mTriggerTime);
            PendingIntent pendingIntent = this.mShowIntent;
            if (pendingIntent != null) {
                pendingIntent.writeToProto(protoOutputStream, 1146756268034L);
            }
            protoOutputStream.end(l);
        }

    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface AlarmType {
    }

    final class ListenerWrapper
    extends IAlarmListener.Stub
    implements Runnable {
        IAlarmCompleteListener mCompletion;
        Handler mHandler;
        final OnAlarmListener mListener;

        public ListenerWrapper(OnAlarmListener onAlarmListener) {
            this.mListener = onAlarmListener;
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public void cancel() {
            try {
                AlarmManager.this.mService.remove(null, this);
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
            synchronized (AlarmManager.class) {
                if (sWrappers != null) {
                    sWrappers.remove(this.mListener);
                }
                return;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void doAlarm(IAlarmCompleteListener iAlarmCompleteListener) {
            this.mCompletion = iAlarmCompleteListener;
            synchronized (AlarmManager.class) {
                if (sWrappers != null) {
                    sWrappers.remove(this.mListener);
                }
            }
            this.mHandler.post(this);
        }

        @Override
        public void run() {
            try {
                this.mListener.onAlarm();
                return;
            }
            finally {
                try {
                    this.mCompletion.alarmComplete(this);
                }
                catch (Exception exception) {
                    Log.e(AlarmManager.TAG, "Unable to report completion to Alarm Manager!", exception);
                }
            }
        }

        public void setHandler(Handler handler) {
            this.mHandler = handler;
        }
    }

    public static interface OnAlarmListener {
        public void onAlarm();
    }

}

