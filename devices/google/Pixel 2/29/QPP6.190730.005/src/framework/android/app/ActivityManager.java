/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  org.xmlpull.v1.XmlSerializer
 */
package android.app;

import android.annotation.SystemApi;
import android.annotation.UnsupportedAppUsage;
import android.app.Activity;
import android.app.ActivityManagerInternal;
import android.app.ActivityTaskManager;
import android.app.ActivityThread;
import android.app.AppGlobals;
import android.app.GrantedUriPermission;
import android.app.IActivityManager;
import android.app.IActivityTaskManager;
import android.app.IAppTask;
import android.app.IApplicationThread;
import android.app.IStopUserCallback;
import android.app.IUidObserver;
import android.app.Instrumentation;
import android.app.PendingIntent;
import android.app.TaskInfo;
import android.app.UriGrantsManager;
import android.app.WindowConfiguration;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.IIntentReceiver;
import android.content.IIntentSender;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.ConfigurationInfo;
import android.content.pm.IPackageDataObserver;
import android.content.pm.ParceledListSlice;
import android.content.pm.UserInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorSpace;
import android.graphics.GraphicBuffer;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Binder;
import android.os.Build;
import android.os.Bundle;
import android.os.Debug;
import android.os.Handler;
import android.os.IBinder;
import android.os.LocaleList;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.Parcelable;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.os.WorkSource;
import android.util.ArrayMap;
import android.util.DisplayMetrics;
import android.util.Singleton;
import android.util.Size;
import com.android.internal.app.LocalePicker;
import com.android.internal.os.RoSystemProperties;
import com.android.internal.os.TransferPipe;
import com.android.internal.util.FastPrintWriter;
import com.android.internal.util.MemInfoReader;
import com.android.server.LocalServices;
import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import org.xmlpull.v1.XmlSerializer;

public class ActivityManager {
    public static final String ACTION_REPORT_HEAP_LIMIT = "android.app.action.REPORT_HEAP_LIMIT";
    public static final int APP_START_MODE_DELAYED = 1;
    public static final int APP_START_MODE_DELAYED_RIGID = 2;
    public static final int APP_START_MODE_DISABLED = 3;
    public static final int APP_START_MODE_NORMAL = 0;
    public static final int ASSIST_CONTEXT_AUTOFILL = 2;
    public static final int ASSIST_CONTEXT_BASIC = 0;
    public static final int ASSIST_CONTEXT_FULL = 1;
    public static final int BROADCAST_FAILED_USER_STOPPED = -2;
    public static final int BROADCAST_STICKY_CANT_HAVE_PERMISSION = -1;
    public static final int BROADCAST_SUCCESS = 0;
    public static final int BUGREPORT_OPTION_FULL = 0;
    public static final int BUGREPORT_OPTION_INTERACTIVE = 1;
    public static final int BUGREPORT_OPTION_REMOTE = 2;
    public static final int BUGREPORT_OPTION_TELEPHONY = 4;
    public static final int BUGREPORT_OPTION_WEAR = 3;
    public static final int BUGREPORT_OPTION_WIFI = 5;
    public static final int COMPAT_MODE_ALWAYS = -1;
    public static final int COMPAT_MODE_DISABLED = 0;
    public static final int COMPAT_MODE_ENABLED = 1;
    public static final int COMPAT_MODE_NEVER = -2;
    public static final int COMPAT_MODE_TOGGLE = 2;
    public static final int COMPAT_MODE_UNKNOWN = -3;
    private static final boolean DEVELOPMENT_FORCE_LOW_RAM;
    private static final int FIRST_START_FATAL_ERROR_CODE = -100;
    private static final int FIRST_START_NON_FATAL_ERROR_CODE = 100;
    private static final int FIRST_START_SUCCESS_CODE = 0;
    public static final int FLAG_AND_LOCKED = 2;
    public static final int FLAG_AND_UNLOCKED = 4;
    public static final int FLAG_AND_UNLOCKING_OR_UNLOCKED = 8;
    public static final int FLAG_OR_STOPPED = 1;
    @UnsupportedAppUsage
    private static final Singleton<IActivityManager> IActivityManagerSingleton;
    public static final int INSTR_FLAG_DISABLE_HIDDEN_API_CHECKS = 1;
    public static final int INSTR_FLAG_MOUNT_EXTERNAL_STORAGE_FULL = 2;
    @UnsupportedAppUsage
    public static final int INTENT_SENDER_ACTIVITY = 2;
    public static final int INTENT_SENDER_ACTIVITY_RESULT = 3;
    public static final int INTENT_SENDER_BROADCAST = 1;
    public static final int INTENT_SENDER_FOREGROUND_SERVICE = 5;
    public static final int INTENT_SENDER_SERVICE = 4;
    private static final int LAST_START_FATAL_ERROR_CODE = -1;
    private static final int LAST_START_NON_FATAL_ERROR_CODE = 199;
    private static final int LAST_START_SUCCESS_CODE = 99;
    public static final int LOCK_TASK_MODE_LOCKED = 1;
    public static final int LOCK_TASK_MODE_NONE = 0;
    public static final int LOCK_TASK_MODE_PINNED = 2;
    public static final int MAX_PROCESS_STATE = 21;
    public static final String META_HOME_ALTERNATE = "android.app.home.alternate";
    public static final int MIN_PROCESS_STATE = 0;
    public static final int MOVE_TASK_NO_USER_ACTION = 2;
    public static final int MOVE_TASK_WITH_HOME = 1;
    public static final int PROCESS_STATE_BACKUP = 10;
    @UnsupportedAppUsage
    public static final int PROCESS_STATE_BOUND_FOREGROUND_SERVICE = 6;
    public static final int PROCESS_STATE_BOUND_TOP = 4;
    @UnsupportedAppUsage
    public static final int PROCESS_STATE_CACHED_ACTIVITY = 17;
    public static final int PROCESS_STATE_CACHED_ACTIVITY_CLIENT = 18;
    public static final int PROCESS_STATE_CACHED_EMPTY = 20;
    public static final int PROCESS_STATE_CACHED_RECENT = 19;
    @UnsupportedAppUsage
    public static final int PROCESS_STATE_FOREGROUND_SERVICE = 5;
    public static final int PROCESS_STATE_FOREGROUND_SERVICE_LOCATION = 3;
    public static final int PROCESS_STATE_HEAVY_WEIGHT = 14;
    @UnsupportedAppUsage
    public static final int PROCESS_STATE_HOME = 15;
    @UnsupportedAppUsage
    public static final int PROCESS_STATE_IMPORTANT_BACKGROUND = 8;
    public static final int PROCESS_STATE_IMPORTANT_FOREGROUND = 7;
    public static final int PROCESS_STATE_LAST_ACTIVITY = 16;
    public static final int PROCESS_STATE_NONEXISTENT = 21;
    public static final int PROCESS_STATE_PERSISTENT = 0;
    public static final int PROCESS_STATE_PERSISTENT_UI = 1;
    @UnsupportedAppUsage
    public static final int PROCESS_STATE_RECEIVER = 12;
    @UnsupportedAppUsage
    public static final int PROCESS_STATE_SERVICE = 11;
    @UnsupportedAppUsage
    public static final int PROCESS_STATE_TOP = 2;
    public static final int PROCESS_STATE_TOP_SLEEPING = 13;
    public static final int PROCESS_STATE_TRANSIENT_BACKGROUND = 9;
    public static final int PROCESS_STATE_UNKNOWN = -1;
    public static final int RECENT_IGNORE_UNAVAILABLE = 2;
    public static final int RECENT_WITH_EXCLUDED = 1;
    public static final int START_ABORTED = 102;
    public static final int START_ASSISTANT_HIDDEN_SESSION = -90;
    public static final int START_ASSISTANT_NOT_ACTIVE_SESSION = -89;
    public static final int START_CANCELED = -96;
    public static final int START_CLASS_NOT_FOUND = -92;
    public static final int START_DELIVERED_TO_TOP = 3;
    public static final int START_FLAG_DEBUG = 2;
    public static final int START_FLAG_NATIVE_DEBUGGING = 8;
    public static final int START_FLAG_ONLY_IF_NEEDED = 1;
    public static final int START_FLAG_TRACK_ALLOCATION = 4;
    public static final int START_FORWARD_AND_REQUEST_CONFLICT = -93;
    public static final int START_INTENT_NOT_RESOLVED = -91;
    public static final int START_NOT_ACTIVITY = -95;
    public static final int START_NOT_CURRENT_USER_ACTIVITY = -98;
    public static final int START_NOT_VOICE_COMPATIBLE = -97;
    public static final int START_PERMISSION_DENIED = -94;
    public static final int START_RETURN_INTENT_TO_CALLER = 1;
    public static final int START_RETURN_LOCK_TASK_MODE_VIOLATION = 101;
    public static final int START_SUCCESS = 0;
    public static final int START_SWITCHES_CANCELED = 100;
    public static final int START_TASK_TO_FRONT = 2;
    public static final int START_VOICE_HIDDEN_SESSION = -100;
    public static final int START_VOICE_NOT_ACTIVE_SESSION = -99;
    private static String TAG;
    public static final int UID_OBSERVER_ACTIVE = 8;
    public static final int UID_OBSERVER_CACHED = 16;
    public static final int UID_OBSERVER_GONE = 2;
    public static final int UID_OBSERVER_IDLE = 4;
    public static final int UID_OBSERVER_PROCSTATE = 1;
    public static final int USER_OP_ERROR_IS_SYSTEM = -3;
    public static final int USER_OP_ERROR_RELATED_USERS_CANNOT_STOP = -4;
    public static final int USER_OP_IS_CURRENT = -2;
    public static final int USER_OP_SUCCESS = 0;
    public static final int USER_OP_UNKNOWN_USER = -1;
    private static volatile boolean sSystemReady;
    Point mAppTaskThumbnailSize;
    @UnsupportedAppUsage
    private final Context mContext;
    final ArrayMap<OnUidImportanceListener, UidObserver> mImportanceListeners = new ArrayMap();

    static {
        TAG = "ActivityManager";
        sSystemReady = false;
        DEVELOPMENT_FORCE_LOW_RAM = SystemProperties.getBoolean("debug.force_low_ram", false);
        IActivityManagerSingleton = new Singleton<IActivityManager>(){

            @Override
            protected IActivityManager create() {
                return IActivityManager.Stub.asInterface(ServiceManager.getService("activity"));
            }
        };
    }

    @UnsupportedAppUsage
    ActivityManager(Context context, Handler handler) {
        this.mContext = context;
    }

    public static void broadcastStickyIntent(Intent intent, int n) {
        ActivityManager.broadcastStickyIntent(intent, -1, n);
    }

    public static void broadcastStickyIntent(Intent intent, int n, int n2) {
        try {
            ActivityManager.getService().broadcastIntent(null, intent, null, null, -1, null, null, null, n, null, false, true, n2);
        }
        catch (RemoteException remoteException) {
            // empty catch block
        }
    }

    @UnsupportedAppUsage
    public static int checkComponentPermission(String string2, int n, int n2, boolean bl) {
        int n3 = UserHandle.getAppId(n);
        if (n3 != 0 && n3 != 1000) {
            if (UserHandle.isIsolated(n)) {
                return -1;
            }
            if (n2 >= 0 && UserHandle.isSameApp(n, n2)) {
                return 0;
            }
            if (!bl) {
                return -1;
            }
            if (string2 == null) {
                return 0;
            }
            try {
                n = AppGlobals.getPackageManager().checkUidPermission(string2, n);
                return n;
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
        return 0;
    }

    public static int checkUidPermission(String string2, int n) {
        try {
            n = AppGlobals.getPackageManager().checkUidPermission(string2, n);
            return n;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public static void dumpPackageStateStatic(FileDescriptor fileDescriptor, String string2) {
        FastPrintWriter fastPrintWriter = new FastPrintWriter(new FileOutputStream(fileDescriptor));
        ActivityManager.dumpService(fastPrintWriter, fileDescriptor, "package", new String[]{string2});
        ((PrintWriter)fastPrintWriter).println();
        ActivityManager.dumpService(fastPrintWriter, fileDescriptor, "activity", new String[]{"-a", "package", string2});
        ((PrintWriter)fastPrintWriter).println();
        ActivityManager.dumpService(fastPrintWriter, fileDescriptor, "meminfo", new String[]{"--local", "--package", string2});
        ((PrintWriter)fastPrintWriter).println();
        ActivityManager.dumpService(fastPrintWriter, fileDescriptor, "procstats", new String[]{string2});
        ((PrintWriter)fastPrintWriter).println();
        ActivityManager.dumpService(fastPrintWriter, fileDescriptor, "usagestats", new String[]{string2});
        ((PrintWriter)fastPrintWriter).println();
        ActivityManager.dumpService(fastPrintWriter, fileDescriptor, "batterystats", new String[]{string2});
        ((PrintWriter)fastPrintWriter).flush();
    }

    private static void dumpService(PrintWriter printWriter, FileDescriptor fileDescriptor, String object, String[] arrstring) {
        printWriter.print("DUMP OF SERVICE ");
        printWriter.print((String)object);
        printWriter.println(":");
        IBinder iBinder = ServiceManager.checkService((String)object);
        if (iBinder == null) {
            printWriter.println("  (Service not found)");
            printWriter.flush();
            return;
        }
        printWriter.flush();
        if (iBinder instanceof Binder) {
            try {
                iBinder.dump(fileDescriptor, arrstring);
            }
            catch (Throwable throwable) {
                printWriter.println("Failure dumping service:");
                throwable.printStackTrace(printWriter);
                printWriter.flush();
            }
        } else {
            TransferPipe transferPipe;
            TransferPipe transferPipe2 = null;
            object = transferPipe2;
            printWriter.flush();
            object = transferPipe2;
            object = transferPipe2;
            transferPipe2 = transferPipe = new TransferPipe();
            object = transferPipe2;
            transferPipe2.setBufferPrefix("  ");
            object = transferPipe2;
            iBinder.dumpAsync(transferPipe2.getWriteFd().getFileDescriptor(), arrstring);
            object = transferPipe2;
            try {
                transferPipe2.go(fileDescriptor, 10000L);
            }
            catch (Throwable throwable) {
                if (object != null) {
                    ((TransferPipe)object).kill();
                }
                printWriter.println("Failure dumping service:");
                throwable.printStackTrace(printWriter);
            }
        }
    }

    private void ensureAppTaskThumbnailSizeLocked() {
        if (this.mAppTaskThumbnailSize == null) {
            try {
                this.mAppTaskThumbnailSize = ActivityManager.getTaskService().getAppTaskThumbnailSize();
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
    }

    @SystemApi
    public static int getCurrentUser() {
        int n;
        block4 : {
            block3 : {
                UserInfo userInfo;
                try {
                    userInfo = ActivityManager.getService().getCurrentUser();
                    if (userInfo == null) break block3;
                }
                catch (RemoteException remoteException) {
                    throw remoteException.rethrowFromSystemServer();
                }
                n = userInfo.id;
                break block4;
            }
            n = 0;
        }
        return n;
    }

    static int getLauncherLargeIconSizeInner(Context object) {
        object = ((Context)object).getResources();
        int n = ((Resources)object).getDimensionPixelSize(17104896);
        if (object.getConfiguration().smallestScreenWidthDp < 600) {
            return n;
        }
        int n2 = object.getDisplayMetrics().densityDpi;
        if (n2 != 120) {
            if (n2 != 160) {
                if (n2 != 213) {
                    if (n2 != 240) {
                        if (n2 != 320) {
                            if (n2 != 480) {
                                return (int)((float)n * 1.5f + 0.5f);
                            }
                            return n * 320 * 2 / 480;
                        }
                        return n * 480 / 320;
                    }
                    return n * 320 / 240;
                }
                return n * 320 / 240;
            }
            return n * 240 / 160;
        }
        return n * 160 / 120;
    }

    @Deprecated
    public static int getMaxNumPictureInPictureActions() {
        return 3;
    }

    @Deprecated
    @UnsupportedAppUsage
    public static int getMaxRecentTasksStatic() {
        return ActivityTaskManager.getMaxRecentTasksStatic();
    }

    public static void getMyMemoryState(RunningAppProcessInfo runningAppProcessInfo) {
        try {
            ActivityManager.getService().getMyMemoryState(runningAppProcessInfo);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @UnsupportedAppUsage
    public static IActivityManager getService() {
        return IActivityManagerSingleton.get();
    }

    private static IActivityTaskManager getTaskService() {
        return ActivityTaskManager.getService();
    }

    public static int handleIncomingUser(int n, int n2, int n3, boolean bl, boolean bl2, String string2, String string3) {
        if (UserHandle.getUserId(n2) == n3) {
            return n3;
        }
        try {
            n = ActivityManager.getService().handleIncomingUser(n, n2, n3, bl, bl2, string2, string3);
            return n;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public static boolean isForegroundService(int n) {
        boolean bl = n == 3 || n == 5;
        return bl;
    }

    @UnsupportedAppUsage
    public static boolean isHighEndGfx() {
        boolean bl = !ActivityManager.isLowRamDeviceStatic() && !RoSystemProperties.CONFIG_AVOID_GFX_ACCEL && !Resources.getSystem().getBoolean(17891368);
        return bl;
    }

    @UnsupportedAppUsage
    public static boolean isLowRamDeviceStatic() {
        boolean bl = RoSystemProperties.CONFIG_LOW_RAM || Build.IS_DEBUGGABLE && DEVELOPMENT_FORCE_LOW_RAM;
        return bl;
    }

    public static final boolean isProcStateBackground(int n) {
        boolean bl = n >= 9;
        return bl;
    }

    @Deprecated
    public static boolean isRunningInTestHarness() {
        return SystemProperties.getBoolean("ro.test_harness", false);
    }

    public static boolean isRunningInUserTestHarness() {
        return SystemProperties.getBoolean("persist.sys.test_harness", false);
    }

    public static boolean isSmallBatteryDevice() {
        return RoSystemProperties.CONFIG_SMALL_BATTERY;
    }

    public static final boolean isStartResultFatalError(int n) {
        boolean bl = -100 <= n && n <= -1;
        return bl;
    }

    public static final boolean isStartResultSuccessful(int n) {
        boolean bl = n >= 0 && n <= 99;
        return bl;
    }

    public static boolean isSystemReady() {
        if (!sSystemReady) {
            sSystemReady = ActivityThread.isSystem() ? LocalServices.getService(ActivityManagerInternal.class).isSystemReady() : true;
        }
        return sSystemReady;
    }

    public static boolean isUserAMonkey() {
        try {
            boolean bl = ActivityManager.getService().isUserAMonkey();
            return bl;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public static void logoutCurrentUser() {
        int n = ActivityManager.getCurrentUser();
        if (n != 0) {
            try {
                ActivityManager.getService().switchUser(0);
                ActivityManager.getService().stopUser(n, false, null);
            }
            catch (RemoteException remoteException) {
                remoteException.rethrowFromSystemServer();
            }
        }
    }

    public static void noteAlarmFinish(PendingIntent object, WorkSource workSource, int n, String string2) {
        IActivityManager iActivityManager;
        block5 : {
            block4 : {
                iActivityManager = ActivityManager.getService();
                if (object == null) break block4;
                object = ((PendingIntent)object).getTarget();
                break block5;
            }
            object = null;
        }
        try {
            iActivityManager.noteAlarmFinish((IIntentSender)object, workSource, n, string2);
        }
        catch (RemoteException remoteException) {
            // empty catch block
        }
    }

    public static void noteAlarmStart(PendingIntent object, WorkSource workSource, int n, String string2) {
        IActivityManager iActivityManager;
        block5 : {
            block4 : {
                iActivityManager = ActivityManager.getService();
                if (object == null) break block4;
                object = ((PendingIntent)object).getTarget();
                break block5;
            }
            object = null;
        }
        try {
            iActivityManager.noteAlarmStart((IIntentSender)object, workSource, n, string2);
        }
        catch (RemoteException remoteException) {
            // empty catch block
        }
    }

    public static void noteWakeupAlarm(PendingIntent object, WorkSource workSource, int n, String string2, String string3) {
        IActivityManager iActivityManager;
        block5 : {
            block4 : {
                iActivityManager = ActivityManager.getService();
                if (object == null) break block4;
                object = ((PendingIntent)object).getTarget();
                break block5;
            }
            object = null;
        }
        try {
            iActivityManager.noteWakeupAlarm((IIntentSender)object, workSource, n, string2, string3);
        }
        catch (RemoteException remoteException) {
            // empty catch block
        }
    }

    public static final int processStateAmToProto(int n) {
        switch (n) {
            default: {
                return 998;
            }
            case 21: {
                return 1019;
            }
            case 20: {
                return 1018;
            }
            case 19: {
                return 1017;
            }
            case 18: {
                return 1016;
            }
            case 17: {
                return 1015;
            }
            case 16: {
                return 1014;
            }
            case 15: {
                return 1013;
            }
            case 14: {
                return 1012;
            }
            case 13: {
                return 1011;
            }
            case 12: {
                return 1010;
            }
            case 11: {
                return 1009;
            }
            case 10: {
                return 1008;
            }
            case 9: {
                return 1007;
            }
            case 8: {
                return 1006;
            }
            case 7: {
                return 1005;
            }
            case 6: {
                return 1004;
            }
            case 4: {
                return 1020;
            }
            case 3: 
            case 5: {
                return 1003;
            }
            case 2: {
                return 1002;
            }
            case 1: {
                return 1001;
            }
            case 0: {
                return 1000;
            }
            case -1: 
        }
        return 999;
    }

    public static void resumeAppSwitches() throws RemoteException {
        ActivityManager.getService().resumeAppSwitches();
    }

    @SystemApi
    public static void setPersistentVrThread(int n) {
        try {
            ActivityManager.getService().setPersistentVrThread(n);
        }
        catch (RemoteException remoteException) {
            // empty catch block
        }
    }

    public static void setVrThread(int n) {
        try {
            ActivityManager.getTaskService().setVrThread(n);
        }
        catch (RemoteException remoteException) {
            // empty catch block
        }
    }

    public static int staticGetLargeMemoryClass() {
        String string2 = SystemProperties.get("dalvik.vm.heapsize", "16m");
        return Integer.parseInt(string2.substring(0, string2.length() - 1));
    }

    @UnsupportedAppUsage
    public static int staticGetMemoryClass() {
        String string2 = SystemProperties.get("dalvik.vm.heapgrowthlimit", "");
        if (string2 != null && !"".equals(string2)) {
            return Integer.parseInt(string2.substring(0, string2.length() - 1));
        }
        return ActivityManager.staticGetLargeMemoryClass();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public int addAppTask(Activity activity, Intent intent, TaskDescription taskDescription, Bitmap parcelable) {
        Bitmap bitmap;
        block12 : {
            int n;
            float f;
            int n2;
            Object object;
            block11 : {
                synchronized (this) {
                    this.ensureAppTaskThumbnailSizeLocked();
                    object = this.mAppTaskThumbnailSize;
                }
                n2 = parcelable.getWidth();
                n = parcelable.getHeight();
                if (n2 != ((Point)object).x) break block11;
                bitmap = parcelable;
                if (n == ((Point)object).y) break block12;
            }
            bitmap = Bitmap.createBitmap(((Point)object).x, ((Point)object).y, parcelable.getConfig());
            float f2 = 0.0f;
            if (((Point)object).x * n2 > ((Point)object).y * n) {
                f = (float)((Point)object).x / (float)n;
                f2 = ((float)((Point)object).y - (float)n2 * f) * 0.5f;
            } else {
                f = (float)((Point)object).y / (float)n2;
                float f3 = ((Point)object).x;
                f3 = n;
            }
            object = new Matrix();
            ((Matrix)object).setScale(f, f);
            ((Matrix)object).postTranslate((int)(0.5f + f2), 0.0f);
            Canvas canvas = new Canvas(bitmap);
            canvas.drawBitmap((Bitmap)parcelable, (Matrix)object, null);
            canvas.setBitmap(null);
        }
        parcelable = taskDescription;
        if (taskDescription == null) {
            parcelable = new TaskDescription();
        }
        try {
            return ActivityManager.getTaskService().addAppTask(activity.getActivityToken(), intent, (TaskDescription)parcelable, bitmap);
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
    @SystemApi
    public void addOnUidImportanceListener(OnUidImportanceListener onUidImportanceListener, int n) {
        synchronized (this) {
            if (this.mImportanceListeners.containsKey(onUidImportanceListener)) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Listener already registered: ");
                stringBuilder.append(onUidImportanceListener);
                IllegalArgumentException illegalArgumentException = new IllegalArgumentException(stringBuilder.toString());
                throw illegalArgumentException;
            }
            UidObserver uidObserver = new UidObserver(onUidImportanceListener, this.mContext);
            try {
                ActivityManager.getService().registerUidObserver(uidObserver, 3, RunningAppProcessInfo.importanceToProcState(n), this.mContext.getOpPackageName());
                this.mImportanceListeners.put(onUidImportanceListener, uidObserver);
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
            return;
        }
    }

    public void alwaysShowUnsupportedCompileSdkWarning(ComponentName componentName) {
        try {
            ActivityManager.getTaskService().alwaysShowUnsupportedCompileSdkWarning(componentName);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public boolean clearApplicationUserData() {
        return this.clearApplicationUserData(this.mContext.getPackageName(), null);
    }

    @UnsupportedAppUsage
    public boolean clearApplicationUserData(String string2, IPackageDataObserver iPackageDataObserver) {
        try {
            boolean bl = ActivityManager.getService().clearApplicationUserData(string2, false, iPackageDataObserver, this.mContext.getUserId());
            return bl;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @Deprecated
    public void clearGrantedUriPermissions(String string2) {
        ((UriGrantsManager)this.mContext.getSystemService("uri_grants")).clearGrantedUriPermissions(string2);
    }

    public void clearWatchHeapLimit() {
        try {
            ActivityManager.getService().setDumpHeapDebugLimit(null, 0, 0L, null);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public void dumpPackageState(FileDescriptor fileDescriptor, String string2) {
        ActivityManager.dumpPackageStateStatic(fileDescriptor, string2);
    }

    @SystemApi
    public void forceStopPackage(String string2) {
        this.forceStopPackageAsUser(string2, this.mContext.getUserId());
    }

    @UnsupportedAppUsage
    public void forceStopPackageAsUser(String string2, int n) {
        try {
            ActivityManager.getService().forceStopPackage(string2, n);
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
     */
    public Size getAppTaskThumbnailSize() {
        synchronized (this) {
            this.ensureAppTaskThumbnailSizeLocked();
            return new Size(this.mAppTaskThumbnailSize.x, this.mAppTaskThumbnailSize.y);
        }
    }

    public List<AppTask> getAppTasks() {
        List<IBinder> list;
        int n;
        ArrayList<AppTask> arrayList = new ArrayList<AppTask>();
        try {
            list = ActivityManager.getTaskService().getAppTasks(this.mContext.getPackageName());
            n = list.size();
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
        for (int i = 0; i < n; ++i) {
            arrayList.add(new AppTask(IAppTask.Stub.asInterface(list.get(i))));
        }
        return arrayList;
    }

    public ConfigurationInfo getDeviceConfigurationInfo() {
        try {
            ConfigurationInfo configurationInfo = ActivityManager.getTaskService().getDeviceConfigurationInfo();
            return configurationInfo;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public int getFrontActivityScreenCompatMode() {
        try {
            int n = ActivityManager.getTaskService().getFrontActivityScreenCompatMode();
            return n;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @Deprecated
    public ParceledListSlice<GrantedUriPermission> getGrantedUriPermissions(String string2) {
        return ((UriGrantsManager)this.mContext.getSystemService("uri_grants")).getGrantedUriPermissions(string2);
    }

    public int getLargeMemoryClass() {
        return ActivityManager.staticGetLargeMemoryClass();
    }

    public int getLauncherLargeIconDensity() {
        Resources resources = this.mContext.getResources();
        int n = resources.getDisplayMetrics().densityDpi;
        if (resources.getConfiguration().smallestScreenWidthDp < 600) {
            return n;
        }
        if (n != 120) {
            if (n != 160) {
                if (n != 213) {
                    if (n != 240) {
                        if (n != 320) {
                            if (n != 480) {
                                return (int)((float)n * 1.5f + 0.5f);
                            }
                            return 640;
                        }
                        return 480;
                    }
                    return 320;
                }
                return 320;
            }
            return 240;
        }
        return 160;
    }

    public int getLauncherLargeIconSize() {
        return ActivityManager.getLauncherLargeIconSizeInner(this.mContext);
    }

    public int getLockTaskModeState() {
        try {
            int n = ActivityManager.getTaskService().getLockTaskModeState();
            return n;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public int getMemoryClass() {
        return ActivityManager.staticGetMemoryClass();
    }

    public void getMemoryInfo(MemoryInfo memoryInfo) {
        try {
            ActivityManager.getService().getMemoryInfo(memoryInfo);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public boolean getPackageAskScreenCompat(String string2) {
        try {
            boolean bl = ActivityManager.getTaskService().getPackageAskScreenCompat(string2);
            return bl;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public int getPackageImportance(String string2) {
        try {
            int n = RunningAppProcessInfo.procStateToImportanceForClient(ActivityManager.getService().getPackageProcessState(string2, this.mContext.getOpPackageName()), this.mContext);
            return n;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public int getPackageScreenCompatMode(String string2) {
        try {
            int n = ActivityManager.getTaskService().getPackageScreenCompatMode(string2);
            return n;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public Debug.MemoryInfo[] getProcessMemoryInfo(int[] arrobject) {
        try {
            arrobject = ActivityManager.getService().getProcessMemoryInfo((int[])arrobject);
            return arrobject;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public List<ProcessErrorStateInfo> getProcessesInErrorState() {
        try {
            List<ProcessErrorStateInfo> list = ActivityManager.getService().getProcessesInErrorState();
            return list;
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
    @Deprecated
    public List<RecentTaskInfo> getRecentTasks(int n, int n2) throws SecurityException {
        if (n >= 0) {
            try {
                return ActivityManager.getTaskService().getRecentTasks(n, n2, this.mContext.getUserId()).getList();
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
        IllegalArgumentException illegalArgumentException = new IllegalArgumentException("The requested number of tasks should be >= 0");
        throw illegalArgumentException;
    }

    public List<RunningAppProcessInfo> getRunningAppProcesses() {
        try {
            List<RunningAppProcessInfo> list = ActivityManager.getService().getRunningAppProcesses();
            return list;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public List<ApplicationInfo> getRunningExternalApplications() {
        try {
            List<ApplicationInfo> list = ActivityManager.getService().getRunningExternalApplications();
            return list;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public PendingIntent getRunningServiceControlPanel(ComponentName parcelable) throws SecurityException {
        try {
            parcelable = ActivityManager.getService().getRunningServiceControlPanel((ComponentName)parcelable);
            return parcelable;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @Deprecated
    public List<RunningServiceInfo> getRunningServices(int n) throws SecurityException {
        try {
            List<RunningServiceInfo> list = ActivityManager.getService().getServices(n, 0);
            return list;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @Deprecated
    public List<RunningTaskInfo> getRunningTasks(int n) throws SecurityException {
        try {
            List<RunningTaskInfo> list = ActivityManager.getTaskService().getTasks(n);
            return list;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public Collection<Locale> getSupportedLocales() {
        ArrayList<Locale> arrayList = new ArrayList<Locale>();
        String[] arrstring = LocalePicker.getSupportedLocales(this.mContext);
        int n = arrstring.length;
        for (int i = 0; i < n; ++i) {
            arrayList.add(Locale.forLanguageTag(arrstring[i]));
        }
        return arrayList;
    }

    public long getTotalRam() {
        MemInfoReader memInfoReader = new MemInfoReader();
        memInfoReader.readMemInfo();
        return memInfoReader.getTotalSize();
    }

    @SystemApi
    public int getUidImportance(int n) {
        try {
            n = RunningAppProcessInfo.procStateToImportanceForClient(ActivityManager.getService().getUidProcessState(n, this.mContext.getOpPackageName()), this.mContext);
            return n;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public boolean isActivityStartAllowedOnDisplay(Context context, int n, Intent intent) {
        try {
            boolean bl = ActivityManager.getTaskService().isActivityStartAllowedOnDisplay(n, intent, intent.resolveTypeIfNeeded(context.getContentResolver()), context.getUserId());
            return bl;
        }
        catch (RemoteException remoteException) {
            remoteException.rethrowFromSystemServer();
            return false;
        }
    }

    public boolean isBackgroundRestricted() {
        try {
            boolean bl = ActivityManager.getService().isBackgroundRestricted(this.mContext.getOpPackageName());
            return bl;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @Deprecated
    public boolean isInLockTaskMode() {
        boolean bl = this.getLockTaskModeState() != 0;
        return bl;
    }

    public boolean isLowRamDevice() {
        return ActivityManager.isLowRamDeviceStatic();
    }

    @UnsupportedAppUsage
    public boolean isUserRunning(int n) {
        try {
            boolean bl = ActivityManager.getService().isUserRunning(n, 0);
            return bl;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public boolean isVrModePackageEnabled(ComponentName componentName) {
        try {
            boolean bl = ActivityManager.getService().isVrModePackageEnabled(componentName);
            return bl;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public void killBackgroundProcesses(String string2) {
        try {
            ActivityManager.getService().killBackgroundProcesses(string2, this.mContext.getUserId());
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public void killUid(int n, String string2) {
        try {
            ActivityManager.getService().killUid(UserHandle.getAppId(n), UserHandle.getUserId(n), string2);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public void moveTaskToFront(int n, int n2) {
        this.moveTaskToFront(n, n2, null);
    }

    public void moveTaskToFront(int n, int n2, Bundle bundle) {
        try {
            ActivityThread.ApplicationThread applicationThread = ActivityThread.currentActivityThread().getApplicationThread();
            String string2 = this.mContext.getPackageName();
            ActivityManager.getTaskService().moveTaskToFront(applicationThread, string2, n, n2, bundle);
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
     */
    @SystemApi
    public void removeOnUidImportanceListener(OnUidImportanceListener onUidImportanceListener) {
        synchronized (this) {
            Object object = this.mImportanceListeners.remove(onUidImportanceListener);
            if (object == null) {
                object = new StringBuilder();
                ((StringBuilder)object).append("Listener not registered: ");
                ((StringBuilder)object).append(onUidImportanceListener);
                IllegalArgumentException illegalArgumentException = new IllegalArgumentException(((StringBuilder)object).toString());
                throw illegalArgumentException;
            }
            try {
                ActivityManager.getService().unregisterUidObserver((IUidObserver)object);
                return;
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
    }

    @Deprecated
    public void restartPackage(String string2) {
        this.killBackgroundProcesses(string2);
    }

    public void scheduleApplicationInfoChanged(List<String> list, int n) {
        try {
            ActivityManager.getService().scheduleApplicationInfoChanged(list, n);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public void setDeviceLocales(LocaleList localeList) {
        LocalePicker.updateLocales(localeList);
    }

    public void setFrontActivityScreenCompatMode(int n) {
        try {
            ActivityManager.getTaskService().setFrontActivityScreenCompatMode(n);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public void setPackageAskScreenCompat(String string2, boolean bl) {
        try {
            ActivityManager.getTaskService().setPackageAskScreenCompat(string2, bl);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public void setPackageScreenCompatMode(String string2, int n) {
        try {
            ActivityManager.getTaskService().setPackageScreenCompatMode(string2, n);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public boolean setProcessMemoryTrimLevel(String string2, int n, int n2) {
        try {
            boolean bl = ActivityManager.getService().setProcessMemoryTrimLevel(string2, n, n2);
            return bl;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public void setWatchHeapLimit(long l) {
        try {
            ActivityManager.getService().setDumpHeapDebugLimit(null, 0, l, this.mContext.getPackageName());
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @UnsupportedAppUsage
    public boolean switchUser(int n) {
        try {
            boolean bl = ActivityManager.getService().switchUser(n);
            return bl;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public boolean switchUser(UserHandle userHandle) {
        if (userHandle != null) {
            return this.switchUser(userHandle.getIdentifier());
        }
        throw new IllegalArgumentException("UserHandle cannot be null.");
    }

    public static class AppTask {
        private IAppTask mAppTaskImpl;

        public AppTask(IAppTask iAppTask) {
            this.mAppTaskImpl = iAppTask;
        }

        public void finishAndRemoveTask() {
            try {
                this.mAppTaskImpl.finishAndRemoveTask();
                return;
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }

        public RecentTaskInfo getTaskInfo() {
            try {
                RecentTaskInfo recentTaskInfo = this.mAppTaskImpl.getTaskInfo();
                return recentTaskInfo;
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }

        public void moveToFront() {
            try {
                ActivityThread.ApplicationThread applicationThread = ActivityThread.currentActivityThread().getApplicationThread();
                String string2 = ActivityThread.currentPackageName();
                this.mAppTaskImpl.moveToFront(applicationThread, string2);
                return;
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }

        public void setExcludeFromRecents(boolean bl) {
            try {
                this.mAppTaskImpl.setExcludeFromRecents(bl);
                return;
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }

        public void startActivity(Context context, Intent intent, Bundle bundle) {
            ActivityThread activityThread = ActivityThread.currentActivityThread();
            activityThread.getInstrumentation().execStartActivityFromAppTask(context, activityThread.getApplicationThread(), this.mAppTaskImpl, intent, bundle);
        }
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface BugreportMode {
    }

    public static class MemoryInfo
    implements Parcelable {
        public static final Parcelable.Creator<MemoryInfo> CREATOR = new Parcelable.Creator<MemoryInfo>(){

            @Override
            public MemoryInfo createFromParcel(Parcel parcel) {
                return new MemoryInfo(parcel);
            }

            public MemoryInfo[] newArray(int n) {
                return new MemoryInfo[n];
            }
        };
        public long availMem;
        @UnsupportedAppUsage
        public long foregroundAppThreshold;
        @UnsupportedAppUsage
        public long hiddenAppThreshold;
        public boolean lowMemory;
        @UnsupportedAppUsage
        public long secondaryServerThreshold;
        public long threshold;
        public long totalMem;
        @UnsupportedAppUsage
        public long visibleAppThreshold;

        public MemoryInfo() {
        }

        private MemoryInfo(Parcel parcel) {
            this.readFromParcel(parcel);
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public void readFromParcel(Parcel parcel) {
            this.availMem = parcel.readLong();
            this.totalMem = parcel.readLong();
            this.threshold = parcel.readLong();
            boolean bl = parcel.readInt() != 0;
            this.lowMemory = bl;
            this.hiddenAppThreshold = parcel.readLong();
            this.secondaryServerThreshold = parcel.readLong();
            this.visibleAppThreshold = parcel.readLong();
            this.foregroundAppThreshold = parcel.readLong();
        }

        @Override
        public void writeToParcel(Parcel parcel, int n) {
            parcel.writeLong(this.availMem);
            parcel.writeLong(this.totalMem);
            parcel.writeLong(this.threshold);
            parcel.writeInt((int)this.lowMemory);
            parcel.writeLong(this.hiddenAppThreshold);
            parcel.writeLong(this.secondaryServerThreshold);
            parcel.writeLong(this.visibleAppThreshold);
            parcel.writeLong(this.foregroundAppThreshold);
        }

    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface MoveTaskFlags {
    }

    @SystemApi
    public static interface OnUidImportanceListener {
        public void onUidImportance(int var1, int var2);
    }

    public static class ProcessErrorStateInfo
    implements Parcelable {
        public static final int CRASHED = 1;
        public static final Parcelable.Creator<ProcessErrorStateInfo> CREATOR = new Parcelable.Creator<ProcessErrorStateInfo>(){

            @Override
            public ProcessErrorStateInfo createFromParcel(Parcel parcel) {
                return new ProcessErrorStateInfo(parcel);
            }

            public ProcessErrorStateInfo[] newArray(int n) {
                return new ProcessErrorStateInfo[n];
            }
        };
        public static final int NOT_RESPONDING = 2;
        public static final int NO_ERROR = 0;
        public int condition;
        public byte[] crashData = null;
        public String longMsg;
        public int pid;
        public String processName;
        public String shortMsg;
        public String stackTrace;
        public String tag;
        public int uid;

        public ProcessErrorStateInfo() {
        }

        private ProcessErrorStateInfo(Parcel parcel) {
            this.readFromParcel(parcel);
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public void readFromParcel(Parcel parcel) {
            this.condition = parcel.readInt();
            this.processName = parcel.readString();
            this.pid = parcel.readInt();
            this.uid = parcel.readInt();
            this.tag = parcel.readString();
            this.shortMsg = parcel.readString();
            this.longMsg = parcel.readString();
            this.stackTrace = parcel.readString();
        }

        @Override
        public void writeToParcel(Parcel parcel, int n) {
            parcel.writeInt(this.condition);
            parcel.writeString(this.processName);
            parcel.writeInt(this.pid);
            parcel.writeInt(this.uid);
            parcel.writeString(this.tag);
            parcel.writeString(this.shortMsg);
            parcel.writeString(this.longMsg);
            parcel.writeString(this.stackTrace);
        }

    }

    public static class RecentTaskInfo
    extends TaskInfo
    implements Parcelable {
        public static final Parcelable.Creator<RecentTaskInfo> CREATOR = new Parcelable.Creator<RecentTaskInfo>(){

            @Override
            public RecentTaskInfo createFromParcel(Parcel parcel) {
                return new RecentTaskInfo(parcel);
            }

            public RecentTaskInfo[] newArray(int n) {
                return new RecentTaskInfo[n];
            }
        };
        @Deprecated
        public int affiliatedTaskId;
        @Deprecated
        public CharSequence description;
        @Deprecated
        public int id;
        @Deprecated
        public int persistentId;

        public RecentTaskInfo() {
        }

        private RecentTaskInfo(Parcel parcel) {
            this.readFromParcel(parcel);
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public void dump(PrintWriter printWriter, String object) {
            CharSequence charSequence = WindowConfiguration.activityTypeToString(this.configuration.windowConfiguration.getActivityType());
            object = WindowConfiguration.activityTypeToString(this.configuration.windowConfiguration.getActivityType());
            printWriter.println();
            printWriter.print("   ");
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(" id=");
            stringBuilder.append(this.persistentId);
            printWriter.print(stringBuilder.toString());
            stringBuilder = new StringBuilder();
            stringBuilder.append(" stackId=");
            stringBuilder.append(this.stackId);
            printWriter.print(stringBuilder.toString());
            stringBuilder = new StringBuilder();
            stringBuilder.append(" userId=");
            stringBuilder.append(this.userId);
            printWriter.print(stringBuilder.toString());
            stringBuilder = new StringBuilder();
            stringBuilder.append(" hasTask=");
            int n = this.id;
            boolean bl = true;
            boolean bl2 = n != -1;
            stringBuilder.append(bl2);
            printWriter.print(stringBuilder.toString());
            stringBuilder = new StringBuilder();
            stringBuilder.append(" lastActiveTime=");
            stringBuilder.append(this.lastActiveTime);
            printWriter.print(stringBuilder.toString());
            printWriter.println();
            printWriter.print("   ");
            stringBuilder = new StringBuilder();
            stringBuilder.append(" baseIntent=");
            stringBuilder.append(this.baseIntent);
            printWriter.print(stringBuilder.toString());
            printWriter.println();
            printWriter.print("   ");
            stringBuilder = new StringBuilder();
            stringBuilder.append(" isExcluded=");
            bl2 = (this.baseIntent.getFlags() & 8388608) != 0;
            stringBuilder.append(bl2);
            printWriter.print(stringBuilder.toString());
            stringBuilder = new StringBuilder();
            stringBuilder.append(" activityType=");
            stringBuilder.append((String)charSequence);
            printWriter.print(stringBuilder.toString());
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append(" windowingMode=");
            ((StringBuilder)charSequence).append((String)object);
            printWriter.print(((StringBuilder)charSequence).toString());
            object = new StringBuilder();
            ((StringBuilder)object).append(" supportsSplitScreenMultiWindow=");
            ((StringBuilder)object).append(this.supportsSplitScreenMultiWindow);
            printWriter.print(((StringBuilder)object).toString());
            if (this.taskDescription != null) {
                printWriter.println();
                printWriter.print("   ");
                object = this.taskDescription;
                printWriter.print(" taskDescription {");
                charSequence = new StringBuilder();
                ((StringBuilder)charSequence).append(" colorBackground=#");
                ((StringBuilder)charSequence).append(Integer.toHexString(((TaskDescription)object).getBackgroundColor()));
                printWriter.print(((StringBuilder)charSequence).toString());
                charSequence = new StringBuilder();
                ((StringBuilder)charSequence).append(" colorPrimary=#");
                ((StringBuilder)charSequence).append(Integer.toHexString(((TaskDescription)object).getPrimaryColor()));
                printWriter.print(((StringBuilder)charSequence).toString());
                charSequence = new StringBuilder();
                ((StringBuilder)charSequence).append(" iconRes=");
                bl2 = ((TaskDescription)object).getIconResource() != 0;
                ((StringBuilder)charSequence).append(bl2);
                printWriter.print(((StringBuilder)charSequence).toString());
                charSequence = new StringBuilder();
                ((StringBuilder)charSequence).append(" iconBitmap=");
                bl2 = ((TaskDescription)object).getIconFilename() == null && ((TaskDescription)object).getInMemoryIcon() == null ? false : bl;
                ((StringBuilder)charSequence).append(bl2);
                printWriter.print(((StringBuilder)charSequence).toString());
                printWriter.println(" }");
            }
        }

        @Override
        public void readFromParcel(Parcel parcel) {
            this.id = parcel.readInt();
            this.persistentId = parcel.readInt();
            super.readFromParcel(parcel);
        }

        @Override
        public void writeToParcel(Parcel parcel, int n) {
            parcel.writeInt(this.id);
            parcel.writeInt(this.persistentId);
            super.writeToParcel(parcel, n);
        }

    }

    public static class RunningAppProcessInfo
    implements Parcelable {
        public static final Parcelable.Creator<RunningAppProcessInfo> CREATOR = new Parcelable.Creator<RunningAppProcessInfo>(){

            @Override
            public RunningAppProcessInfo createFromParcel(Parcel parcel) {
                return new RunningAppProcessInfo(parcel);
            }

            public RunningAppProcessInfo[] newArray(int n) {
                return new RunningAppProcessInfo[n];
            }
        };
        public static final int FLAG_CANT_SAVE_STATE = 1;
        @UnsupportedAppUsage
        public static final int FLAG_HAS_ACTIVITIES = 4;
        @UnsupportedAppUsage
        public static final int FLAG_PERSISTENT = 2;
        public static final int IMPORTANCE_BACKGROUND = 400;
        public static final int IMPORTANCE_CACHED = 400;
        public static final int IMPORTANCE_CANT_SAVE_STATE = 350;
        public static final int IMPORTANCE_CANT_SAVE_STATE_PRE_26 = 170;
        @Deprecated
        public static final int IMPORTANCE_EMPTY = 500;
        public static final int IMPORTANCE_FOREGROUND = 100;
        public static final int IMPORTANCE_FOREGROUND_SERVICE = 125;
        public static final int IMPORTANCE_GONE = 1000;
        public static final int IMPORTANCE_PERCEPTIBLE = 230;
        public static final int IMPORTANCE_PERCEPTIBLE_PRE_26 = 130;
        public static final int IMPORTANCE_SERVICE = 300;
        public static final int IMPORTANCE_TOP_SLEEPING = 325;
        @Deprecated
        public static final int IMPORTANCE_TOP_SLEEPING_PRE_28 = 150;
        public static final int IMPORTANCE_VISIBLE = 200;
        public static final int REASON_PROVIDER_IN_USE = 1;
        public static final int REASON_SERVICE_IN_USE = 2;
        public static final int REASON_UNKNOWN = 0;
        @UnsupportedAppUsage
        public int flags;
        public int importance;
        public int importanceReasonCode;
        public ComponentName importanceReasonComponent;
        public int importanceReasonImportance;
        public int importanceReasonPid;
        public boolean isFocused;
        public long lastActivityTime;
        public int lastTrimLevel;
        public int lru;
        public int pid;
        public String[] pkgList;
        public String processName;
        @UnsupportedAppUsage
        public int processState;
        public int uid;

        public RunningAppProcessInfo() {
            this.importance = 100;
            this.importanceReasonCode = 0;
            this.processState = 7;
            this.isFocused = false;
            this.lastActivityTime = 0L;
        }

        private RunningAppProcessInfo(Parcel parcel) {
            this.readFromParcel(parcel);
        }

        public RunningAppProcessInfo(String string2, int n, String[] arrstring) {
            this.processName = string2;
            this.pid = n;
            this.pkgList = arrstring;
            this.isFocused = false;
            this.lastActivityTime = 0L;
        }

        public static int importanceToProcState(int n) {
            if (n == 1000) {
                return 21;
            }
            if (n >= 400) {
                return 15;
            }
            if (n >= 350) {
                return 14;
            }
            if (n >= 325) {
                return 13;
            }
            if (n >= 300) {
                return 11;
            }
            if (n >= 230) {
                return 9;
            }
            if (n >= 200) {
                return 7;
            }
            if (n >= 150) {
                return 7;
            }
            if (n >= 125) {
                return 5;
            }
            return 2;
        }

        @UnsupportedAppUsage
        public static int procStateToImportance(int n) {
            if (n == 21) {
                return 1000;
            }
            if (n >= 15) {
                return 400;
            }
            if (n == 14) {
                return 350;
            }
            if (n >= 13) {
                return 325;
            }
            if (n >= 11) {
                return 300;
            }
            if (n >= 9) {
                return 230;
            }
            if (n >= 7) {
                return 200;
            }
            if (n >= 3) {
                return 125;
            }
            return 100;
        }

        public static int procStateToImportanceForClient(int n, Context context) {
            return RunningAppProcessInfo.procStateToImportanceForTargetSdk(n, context.getApplicationInfo().targetSdkVersion);
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        public static int procStateToImportanceForTargetSdk(int n, int n2) {
            n = RunningAppProcessInfo.procStateToImportance(n);
            if (n2 >= 26) return n;
            if (n == 230) return 130;
            if (n == 325) return 150;
            if (n != 350) return n;
            return 170;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public void readFromParcel(Parcel parcel) {
            this.processName = parcel.readString();
            this.pid = parcel.readInt();
            this.uid = parcel.readInt();
            this.pkgList = parcel.readStringArray();
            this.flags = parcel.readInt();
            this.lastTrimLevel = parcel.readInt();
            this.importance = parcel.readInt();
            this.lru = parcel.readInt();
            this.importanceReasonCode = parcel.readInt();
            this.importanceReasonPid = parcel.readInt();
            this.importanceReasonComponent = ComponentName.readFromParcel(parcel);
            this.importanceReasonImportance = parcel.readInt();
            this.processState = parcel.readInt();
            boolean bl = parcel.readInt() != 0;
            this.isFocused = bl;
            this.lastActivityTime = parcel.readLong();
        }

        @Override
        public void writeToParcel(Parcel parcel, int n) {
            parcel.writeString(this.processName);
            parcel.writeInt(this.pid);
            parcel.writeInt(this.uid);
            parcel.writeStringArray(this.pkgList);
            parcel.writeInt(this.flags);
            parcel.writeInt(this.lastTrimLevel);
            parcel.writeInt(this.importance);
            parcel.writeInt(this.lru);
            parcel.writeInt(this.importanceReasonCode);
            parcel.writeInt(this.importanceReasonPid);
            ComponentName.writeToParcel(this.importanceReasonComponent, parcel);
            parcel.writeInt(this.importanceReasonImportance);
            parcel.writeInt(this.processState);
            parcel.writeInt((int)this.isFocused);
            parcel.writeLong(this.lastActivityTime);
        }

        @Retention(value=RetentionPolicy.SOURCE)
        public static @interface Importance {
        }

    }

    public static class RunningServiceInfo
    implements Parcelable {
        public static final Parcelable.Creator<RunningServiceInfo> CREATOR = new Parcelable.Creator<RunningServiceInfo>(){

            @Override
            public RunningServiceInfo createFromParcel(Parcel parcel) {
                return new RunningServiceInfo(parcel);
            }

            public RunningServiceInfo[] newArray(int n) {
                return new RunningServiceInfo[n];
            }
        };
        public static final int FLAG_FOREGROUND = 2;
        public static final int FLAG_PERSISTENT_PROCESS = 8;
        public static final int FLAG_STARTED = 1;
        public static final int FLAG_SYSTEM_PROCESS = 4;
        public long activeSince;
        public int clientCount;
        public int clientLabel;
        public String clientPackage;
        public int crashCount;
        public int flags;
        public boolean foreground;
        public long lastActivityTime;
        public int pid;
        public String process;
        public long restarting;
        public ComponentName service;
        public boolean started;
        public int uid;

        public RunningServiceInfo() {
        }

        private RunningServiceInfo(Parcel parcel) {
            this.readFromParcel(parcel);
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public void readFromParcel(Parcel parcel) {
            this.service = ComponentName.readFromParcel(parcel);
            this.pid = parcel.readInt();
            this.uid = parcel.readInt();
            this.process = parcel.readString();
            int n = parcel.readInt();
            boolean bl = true;
            boolean bl2 = n != 0;
            this.foreground = bl2;
            this.activeSince = parcel.readLong();
            bl2 = parcel.readInt() != 0 ? bl : false;
            this.started = bl2;
            this.clientCount = parcel.readInt();
            this.crashCount = parcel.readInt();
            this.lastActivityTime = parcel.readLong();
            this.restarting = parcel.readLong();
            this.flags = parcel.readInt();
            this.clientPackage = parcel.readString();
            this.clientLabel = parcel.readInt();
        }

        @Override
        public void writeToParcel(Parcel parcel, int n) {
            ComponentName.writeToParcel(this.service, parcel);
            parcel.writeInt(this.pid);
            parcel.writeInt(this.uid);
            parcel.writeString(this.process);
            parcel.writeInt((int)this.foreground);
            parcel.writeLong(this.activeSince);
            parcel.writeInt((int)this.started);
            parcel.writeInt(this.clientCount);
            parcel.writeInt(this.crashCount);
            parcel.writeLong(this.lastActivityTime);
            parcel.writeLong(this.restarting);
            parcel.writeInt(this.flags);
            parcel.writeString(this.clientPackage);
            parcel.writeInt(this.clientLabel);
        }

    }

    public static class RunningTaskInfo
    extends TaskInfo
    implements Parcelable {
        public static final Parcelable.Creator<RunningTaskInfo> CREATOR = new Parcelable.Creator<RunningTaskInfo>(){

            @Override
            public RunningTaskInfo createFromParcel(Parcel parcel) {
                return new RunningTaskInfo(parcel);
            }

            public RunningTaskInfo[] newArray(int n) {
                return new RunningTaskInfo[n];
            }
        };
        @Deprecated
        public CharSequence description;
        @Deprecated
        public int id;
        @Deprecated
        public int numRunning;
        @Deprecated
        public Bitmap thumbnail;

        public RunningTaskInfo() {
        }

        private RunningTaskInfo(Parcel parcel) {
            this.readFromParcel(parcel);
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void readFromParcel(Parcel parcel) {
            this.id = parcel.readInt();
            super.readFromParcel(parcel);
        }

        @Override
        public void writeToParcel(Parcel parcel, int n) {
            parcel.writeInt(this.id);
            super.writeToParcel(parcel, n);
        }

    }

    public static class StackInfo
    implements Parcelable {
        public static final Parcelable.Creator<StackInfo> CREATOR = new Parcelable.Creator<StackInfo>(){

            @Override
            public StackInfo createFromParcel(Parcel parcel) {
                return new StackInfo(parcel);
            }

            public StackInfo[] newArray(int n) {
                return new StackInfo[n];
            }
        };
        @UnsupportedAppUsage
        public Rect bounds = new Rect();
        public final Configuration configuration = new Configuration();
        @UnsupportedAppUsage
        public int displayId;
        @UnsupportedAppUsage
        public int position;
        @UnsupportedAppUsage
        public int stackId;
        @UnsupportedAppUsage
        public Rect[] taskBounds;
        @UnsupportedAppUsage
        public int[] taskIds;
        @UnsupportedAppUsage
        public String[] taskNames;
        @UnsupportedAppUsage
        public int[] taskUserIds;
        @UnsupportedAppUsage
        public ComponentName topActivity;
        @UnsupportedAppUsage
        public int userId;
        @UnsupportedAppUsage
        public boolean visible;

        public StackInfo() {
        }

        private StackInfo(Parcel parcel) {
            this.readFromParcel(parcel);
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public void readFromParcel(Parcel parcel) {
            this.stackId = parcel.readInt();
            this.bounds = new Rect(parcel.readInt(), parcel.readInt(), parcel.readInt(), parcel.readInt());
            this.taskIds = parcel.createIntArray();
            this.taskNames = parcel.createStringArray();
            int n = parcel.readInt();
            if (n > 0) {
                this.taskBounds = new Rect[n];
                for (int i = 0; i < n; ++i) {
                    this.taskBounds[i] = new Rect();
                    this.taskBounds[i].set(parcel.readInt(), parcel.readInt(), parcel.readInt(), parcel.readInt());
                }
            } else {
                this.taskBounds = null;
            }
            this.taskUserIds = parcel.createIntArray();
            this.displayId = parcel.readInt();
            this.userId = parcel.readInt();
            boolean bl = parcel.readInt() > 0;
            this.visible = bl;
            this.position = parcel.readInt();
            if (parcel.readInt() > 0) {
                this.topActivity = ComponentName.readFromParcel(parcel);
            }
            this.configuration.readFromParcel(parcel);
        }

        public String toString() {
            return this.toString("");
        }

        @UnsupportedAppUsage
        public String toString(String string2) {
            StringBuilder stringBuilder = new StringBuilder(256);
            stringBuilder.append(string2);
            stringBuilder.append("Stack id=");
            stringBuilder.append(this.stackId);
            stringBuilder.append(" bounds=");
            stringBuilder.append(this.bounds.toShortString());
            stringBuilder.append(" displayId=");
            stringBuilder.append(this.displayId);
            stringBuilder.append(" userId=");
            stringBuilder.append(this.userId);
            stringBuilder.append("\n");
            stringBuilder.append(" configuration=");
            stringBuilder.append(this.configuration);
            stringBuilder.append("\n");
            StringBuilder stringBuilder2 = new StringBuilder();
            stringBuilder2.append(string2);
            stringBuilder2.append("  ");
            string2 = stringBuilder2.toString();
            for (int i = 0; i < this.taskIds.length; ++i) {
                stringBuilder.append(string2);
                stringBuilder.append("taskId=");
                stringBuilder.append(this.taskIds[i]);
                stringBuilder.append(": ");
                stringBuilder.append(this.taskNames[i]);
                if (this.taskBounds != null) {
                    stringBuilder.append(" bounds=");
                    stringBuilder.append(this.taskBounds[i].toShortString());
                }
                stringBuilder.append(" userId=");
                stringBuilder.append(this.taskUserIds[i]);
                stringBuilder.append(" visible=");
                stringBuilder.append(this.visible);
                if (this.topActivity != null) {
                    stringBuilder.append(" topActivity=");
                    stringBuilder.append(this.topActivity);
                }
                stringBuilder.append("\n");
            }
            return stringBuilder.toString();
        }

        @Override
        public void writeToParcel(Parcel parcel, int n) {
            parcel.writeInt(this.stackId);
            parcel.writeInt(this.bounds.left);
            parcel.writeInt(this.bounds.top);
            parcel.writeInt(this.bounds.right);
            parcel.writeInt(this.bounds.bottom);
            parcel.writeIntArray(this.taskIds);
            parcel.writeStringArray(this.taskNames);
            Rect[] arrrect = this.taskBounds;
            int n2 = arrrect == null ? 0 : arrrect.length;
            parcel.writeInt(n2);
            for (int i = 0; i < n2; ++i) {
                parcel.writeInt(this.taskBounds[i].left);
                parcel.writeInt(this.taskBounds[i].top);
                parcel.writeInt(this.taskBounds[i].right);
                parcel.writeInt(this.taskBounds[i].bottom);
            }
            parcel.writeIntArray(this.taskUserIds);
            parcel.writeInt(this.displayId);
            parcel.writeInt(this.userId);
            parcel.writeInt((int)this.visible);
            parcel.writeInt(this.position);
            if (this.topActivity != null) {
                parcel.writeInt(1);
                this.topActivity.writeToParcel(parcel, 0);
            } else {
                parcel.writeInt(0);
            }
            this.configuration.writeToParcel(parcel, n);
        }

    }

    public static class TaskDescription
    implements Parcelable {
        private static final String ATTR_TASKDESCRIPTIONCOLOR_BACKGROUND = "task_description_colorBackground";
        private static final String ATTR_TASKDESCRIPTIONCOLOR_PRIMARY = "task_description_color";
        private static final String ATTR_TASKDESCRIPTIONICON_FILENAME = "task_description_icon_filename";
        private static final String ATTR_TASKDESCRIPTIONICON_RESOURCE = "task_description_icon_resource";
        private static final String ATTR_TASKDESCRIPTIONLABEL = "task_description_label";
        public static final String ATTR_TASKDESCRIPTION_PREFIX = "task_description_";
        public static final Parcelable.Creator<TaskDescription> CREATOR = new Parcelable.Creator<TaskDescription>(){

            @Override
            public TaskDescription createFromParcel(Parcel parcel) {
                return new TaskDescription(parcel);
            }

            public TaskDescription[] newArray(int n) {
                return new TaskDescription[n];
            }
        };
        private int mColorBackground;
        private int mColorPrimary;
        private boolean mEnsureNavigationBarContrastWhenTransparent;
        private boolean mEnsureStatusBarContrastWhenTransparent;
        private Bitmap mIcon;
        private String mIconFilename;
        private int mIconRes;
        private String mLabel;
        private int mNavigationBarColor;
        private int mStatusBarColor;

        public TaskDescription() {
            this(null, null, 0, null, 0, 0, 0, 0, false, false);
        }

        public TaskDescription(TaskDescription taskDescription) {
            this.copyFrom(taskDescription);
        }

        private TaskDescription(Parcel parcel) {
            this.readFromParcel(parcel);
        }

        public TaskDescription(String string2) {
            this(string2, null, 0, null, 0, 0, 0, 0, false, false);
        }

        public TaskDescription(String string2, int n) {
            this(string2, null, n, null, 0, 0, 0, 0, false, false);
        }

        public TaskDescription(String string2, int n, int n2) {
            this(string2, null, n, null, n2, 0, 0, 0, false, false);
            if (n2 != 0 && Color.alpha(n2) != 255) {
                throw new RuntimeException("A TaskDescription's primary color should be opaque");
            }
        }

        @Deprecated
        public TaskDescription(String string2, Bitmap bitmap) {
            this(string2, bitmap, 0, null, 0, 0, 0, 0, false, false);
        }

        @Deprecated
        public TaskDescription(String string2, Bitmap bitmap, int n) {
            this(string2, bitmap, 0, null, n, 0, 0, 0, false, false);
            if (n != 0 && Color.alpha(n) != 255) {
                throw new RuntimeException("A TaskDescription's primary color should be opaque");
            }
        }

        public TaskDescription(String string2, Bitmap bitmap, int n, String string3, int n2, int n3, int n4, int n5, boolean bl, boolean bl2) {
            this.mLabel = string2;
            this.mIcon = bitmap;
            this.mIconRes = n;
            this.mIconFilename = string3;
            this.mColorPrimary = n2;
            this.mColorBackground = n3;
            this.mStatusBarColor = n4;
            this.mNavigationBarColor = n5;
            this.mEnsureStatusBarContrastWhenTransparent = bl;
            this.mEnsureNavigationBarContrastWhenTransparent = bl2;
        }

        @UnsupportedAppUsage
        public static Bitmap loadTaskDescriptionIcon(String object, int n) {
            if (object != null) {
                try {
                    object = ActivityManager.getTaskService().getTaskDescriptionIcon((String)object, n);
                    return object;
                }
                catch (RemoteException remoteException) {
                    throw remoteException.rethrowFromSystemServer();
                }
            }
            return null;
        }

        public void copyFrom(TaskDescription taskDescription) {
            this.mLabel = taskDescription.mLabel;
            this.mIcon = taskDescription.mIcon;
            this.mIconRes = taskDescription.mIconRes;
            this.mIconFilename = taskDescription.mIconFilename;
            this.mColorPrimary = taskDescription.mColorPrimary;
            this.mColorBackground = taskDescription.mColorBackground;
            this.mStatusBarColor = taskDescription.mStatusBarColor;
            this.mNavigationBarColor = taskDescription.mNavigationBarColor;
            this.mEnsureStatusBarContrastWhenTransparent = taskDescription.mEnsureStatusBarContrastWhenTransparent;
            this.mEnsureNavigationBarContrastWhenTransparent = taskDescription.mEnsureNavigationBarContrastWhenTransparent;
        }

        public void copyFromPreserveHiddenFields(TaskDescription taskDescription) {
            this.mLabel = taskDescription.mLabel;
            this.mIcon = taskDescription.mIcon;
            this.mIconRes = taskDescription.mIconRes;
            this.mIconFilename = taskDescription.mIconFilename;
            this.mColorPrimary = taskDescription.mColorPrimary;
            int n = taskDescription.mColorBackground;
            if (n != 0) {
                this.mColorBackground = n;
            }
            if ((n = taskDescription.mStatusBarColor) != 0) {
                this.mStatusBarColor = n;
            }
            if ((n = taskDescription.mNavigationBarColor) != 0) {
                this.mNavigationBarColor = n;
            }
            this.mEnsureStatusBarContrastWhenTransparent = taskDescription.mEnsureStatusBarContrastWhenTransparent;
            this.mEnsureNavigationBarContrastWhenTransparent = taskDescription.mEnsureNavigationBarContrastWhenTransparent;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
        public int getBackgroundColor() {
            return this.mColorBackground;
        }

        public boolean getEnsureNavigationBarContrastWhenTransparent() {
            return this.mEnsureNavigationBarContrastWhenTransparent;
        }

        public boolean getEnsureStatusBarContrastWhenTransparent() {
            return this.mEnsureStatusBarContrastWhenTransparent;
        }

        public Bitmap getIcon() {
            Bitmap bitmap = this.mIcon;
            if (bitmap != null) {
                return bitmap;
            }
            return TaskDescription.loadTaskDescriptionIcon(this.mIconFilename, UserHandle.myUserId());
        }

        public String getIconFilename() {
            return this.mIconFilename;
        }

        public int getIconResource() {
            return this.mIconRes;
        }

        @UnsupportedAppUsage
        public Bitmap getInMemoryIcon() {
            return this.mIcon;
        }

        public String getLabel() {
            return this.mLabel;
        }

        public int getNavigationBarColor() {
            return this.mNavigationBarColor;
        }

        public int getPrimaryColor() {
            return this.mColorPrimary;
        }

        public int getStatusBarColor() {
            return this.mStatusBarColor;
        }

        public void readFromParcel(Parcel parcel) {
            int n = parcel.readInt();
            Object var3_3 = null;
            Object object = n > 0 ? parcel.readString() : null;
            this.mLabel = object;
            object = parcel.readInt() > 0 ? Bitmap.CREATOR.createFromParcel(parcel) : null;
            this.mIcon = object;
            this.mIconRes = parcel.readInt();
            this.mColorPrimary = parcel.readInt();
            this.mColorBackground = parcel.readInt();
            this.mStatusBarColor = parcel.readInt();
            this.mNavigationBarColor = parcel.readInt();
            this.mEnsureStatusBarContrastWhenTransparent = parcel.readBoolean();
            this.mEnsureNavigationBarContrastWhenTransparent = parcel.readBoolean();
            object = var3_3;
            if (parcel.readInt() > 0) {
                object = parcel.readString();
            }
            this.mIconFilename = object;
        }

        public void restoreFromXml(String string2, String string3) {
            if (ATTR_TASKDESCRIPTIONLABEL.equals(string2)) {
                this.setLabel(string3);
            } else if (ATTR_TASKDESCRIPTIONCOLOR_PRIMARY.equals(string2)) {
                this.setPrimaryColor((int)Long.parseLong(string3, 16));
            } else if (ATTR_TASKDESCRIPTIONCOLOR_BACKGROUND.equals(string2)) {
                this.setBackgroundColor((int)Long.parseLong(string3, 16));
            } else if (ATTR_TASKDESCRIPTIONICON_FILENAME.equals(string2)) {
                this.setIconFilename(string3);
            } else if (ATTR_TASKDESCRIPTIONICON_RESOURCE.equals(string2)) {
                this.setIcon(Integer.parseInt(string3, 10));
            }
        }

        public void saveToXml(XmlSerializer xmlSerializer) throws IOException {
            int n;
            String string2 = this.mLabel;
            if (string2 != null) {
                xmlSerializer.attribute(null, ATTR_TASKDESCRIPTIONLABEL, string2);
            }
            if ((n = this.mColorPrimary) != 0) {
                xmlSerializer.attribute(null, ATTR_TASKDESCRIPTIONCOLOR_PRIMARY, Integer.toHexString(n));
            }
            if ((n = this.mColorBackground) != 0) {
                xmlSerializer.attribute(null, ATTR_TASKDESCRIPTIONCOLOR_BACKGROUND, Integer.toHexString(n));
            }
            if ((string2 = this.mIconFilename) != null) {
                xmlSerializer.attribute(null, ATTR_TASKDESCRIPTIONICON_FILENAME, string2);
            }
            if ((n = this.mIconRes) != 0) {
                xmlSerializer.attribute(null, ATTR_TASKDESCRIPTIONICON_RESOURCE, Integer.toString(n));
            }
        }

        public void setBackgroundColor(int n) {
            if (n != 0 && Color.alpha(n) != 255) {
                throw new RuntimeException("A TaskDescription's background color should be opaque");
            }
            this.mColorBackground = n;
        }

        public void setEnsureNavigationBarContrastWhenTransparent(boolean bl) {
            this.mEnsureNavigationBarContrastWhenTransparent = bl;
        }

        public void setEnsureStatusBarContrastWhenTransparent(boolean bl) {
            this.mEnsureStatusBarContrastWhenTransparent = bl;
        }

        public void setIcon(int n) {
            this.mIconRes = n;
        }

        @UnsupportedAppUsage
        public void setIcon(Bitmap bitmap) {
            this.mIcon = bitmap;
        }

        public void setIconFilename(String string2) {
            this.mIconFilename = string2;
            this.mIcon = null;
        }

        public void setLabel(String string2) {
            this.mLabel = string2;
        }

        public void setNavigationBarColor(int n) {
            this.mNavigationBarColor = n;
        }

        public void setPrimaryColor(int n) {
            if (n != 0 && Color.alpha(n) != 255) {
                throw new RuntimeException("A TaskDescription's primary color should be opaque");
            }
            this.mColorPrimary = n;
        }

        public void setStatusBarColor(int n) {
            this.mStatusBarColor = n;
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("TaskDescription Label: ");
            stringBuilder.append(this.mLabel);
            stringBuilder.append(" Icon: ");
            stringBuilder.append(this.mIcon);
            stringBuilder.append(" IconRes: ");
            stringBuilder.append(this.mIconRes);
            stringBuilder.append(" IconFilename: ");
            stringBuilder.append(this.mIconFilename);
            stringBuilder.append(" colorPrimary: ");
            stringBuilder.append(this.mColorPrimary);
            stringBuilder.append(" colorBackground: ");
            stringBuilder.append(this.mColorBackground);
            stringBuilder.append(" statusBarColor: ");
            stringBuilder.append(this.mStatusBarColor);
            boolean bl = this.mEnsureStatusBarContrastWhenTransparent;
            String string2 = " (contrast when transparent)";
            String string3 = bl ? " (contrast when transparent)" : "";
            stringBuilder.append(string3);
            stringBuilder.append(" navigationBarColor: ");
            stringBuilder.append(this.mNavigationBarColor);
            string3 = this.mEnsureNavigationBarContrastWhenTransparent ? string2 : "";
            stringBuilder.append(string3);
            return stringBuilder.toString();
        }

        @Override
        public void writeToParcel(Parcel parcel, int n) {
            if (this.mLabel == null) {
                parcel.writeInt(0);
            } else {
                parcel.writeInt(1);
                parcel.writeString(this.mLabel);
            }
            if (this.mIcon == null) {
                parcel.writeInt(0);
            } else {
                parcel.writeInt(1);
                this.mIcon.writeToParcel(parcel, 0);
            }
            parcel.writeInt(this.mIconRes);
            parcel.writeInt(this.mColorPrimary);
            parcel.writeInt(this.mColorBackground);
            parcel.writeInt(this.mStatusBarColor);
            parcel.writeInt(this.mNavigationBarColor);
            parcel.writeBoolean(this.mEnsureStatusBarContrastWhenTransparent);
            parcel.writeBoolean(this.mEnsureNavigationBarContrastWhenTransparent);
            if (this.mIconFilename == null) {
                parcel.writeInt(0);
            } else {
                parcel.writeInt(1);
                parcel.writeString(this.mIconFilename);
            }
        }

    }

    public static class TaskSnapshot
    implements Parcelable {
        public static final Parcelable.Creator<TaskSnapshot> CREATOR = new Parcelable.Creator<TaskSnapshot>(){

            @Override
            public TaskSnapshot createFromParcel(Parcel parcel) {
                return new TaskSnapshot(parcel);
            }

            public TaskSnapshot[] newArray(int n) {
                return new TaskSnapshot[n];
            }
        };
        private final ColorSpace mColorSpace;
        private final Rect mContentInsets;
        private final boolean mIsRealSnapshot;
        private final boolean mIsTranslucent;
        private final int mOrientation;
        private final boolean mReducedResolution;
        private final float mScale;
        private final GraphicBuffer mSnapshot;
        private final int mSystemUiVisibility;
        private final ComponentName mTopActivityComponent;
        private final int mWindowingMode;

        public TaskSnapshot(ComponentName componentName, GraphicBuffer graphicBuffer, ColorSpace colorSpace, int n, Rect rect, boolean bl, float f, boolean bl2, int n2, int n3, boolean bl3) {
            this.mTopActivityComponent = componentName;
            this.mSnapshot = graphicBuffer;
            if (colorSpace.getId() < 0) {
                colorSpace = ColorSpace.get(ColorSpace.Named.SRGB);
            }
            this.mColorSpace = colorSpace;
            this.mOrientation = n;
            this.mContentInsets = new Rect(rect);
            this.mReducedResolution = bl;
            this.mScale = f;
            this.mIsRealSnapshot = bl2;
            this.mWindowingMode = n2;
            this.mSystemUiVisibility = n3;
            this.mIsTranslucent = bl3;
        }

        private TaskSnapshot(Parcel parcel) {
            this.mTopActivityComponent = ComponentName.readFromParcel(parcel);
            this.mSnapshot = (GraphicBuffer)parcel.readParcelable(null);
            int n = parcel.readInt();
            ColorSpace colorSpace = n >= 0 && n < ColorSpace.Named.values().length ? ColorSpace.get(ColorSpace.Named.values()[n]) : ColorSpace.get(ColorSpace.Named.SRGB);
            this.mColorSpace = colorSpace;
            this.mOrientation = parcel.readInt();
            this.mContentInsets = (Rect)parcel.readParcelable(null);
            this.mReducedResolution = parcel.readBoolean();
            this.mScale = parcel.readFloat();
            this.mIsRealSnapshot = parcel.readBoolean();
            this.mWindowingMode = parcel.readInt();
            this.mSystemUiVisibility = parcel.readInt();
            this.mIsTranslucent = parcel.readBoolean();
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public ColorSpace getColorSpace() {
            return this.mColorSpace;
        }

        @UnsupportedAppUsage
        public Rect getContentInsets() {
            return this.mContentInsets;
        }

        @UnsupportedAppUsage
        public int getOrientation() {
            return this.mOrientation;
        }

        @UnsupportedAppUsage
        public float getScale() {
            return this.mScale;
        }

        @UnsupportedAppUsage
        public GraphicBuffer getSnapshot() {
            return this.mSnapshot;
        }

        public int getSystemUiVisibility() {
            return this.mSystemUiVisibility;
        }

        public ComponentName getTopActivityComponent() {
            return this.mTopActivityComponent;
        }

        public int getWindowingMode() {
            return this.mWindowingMode;
        }

        @UnsupportedAppUsage
        public boolean isRealSnapshot() {
            return this.mIsRealSnapshot;
        }

        @UnsupportedAppUsage
        public boolean isReducedResolution() {
            return this.mReducedResolution;
        }

        public boolean isTranslucent() {
            return this.mIsTranslucent;
        }

        public String toString() {
            Object object = this.mSnapshot;
            int n = 0;
            int n2 = object != null ? ((GraphicBuffer)object).getWidth() : 0;
            object = this.mSnapshot;
            if (object != null) {
                n = ((GraphicBuffer)object).getHeight();
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("TaskSnapshot{ mTopActivityComponent=");
            ((StringBuilder)object).append(this.mTopActivityComponent.flattenToShortString());
            ((StringBuilder)object).append(" mSnapshot=");
            ((StringBuilder)object).append(this.mSnapshot);
            ((StringBuilder)object).append(" (");
            ((StringBuilder)object).append(n2);
            ((StringBuilder)object).append("x");
            ((StringBuilder)object).append(n);
            ((StringBuilder)object).append(") mColorSpace=");
            ((StringBuilder)object).append(this.mColorSpace.toString());
            ((StringBuilder)object).append(" mOrientation=");
            ((StringBuilder)object).append(this.mOrientation);
            ((StringBuilder)object).append(" mContentInsets=");
            ((StringBuilder)object).append(this.mContentInsets.toShortString());
            ((StringBuilder)object).append(" mReducedResolution=");
            ((StringBuilder)object).append(this.mReducedResolution);
            ((StringBuilder)object).append(" mScale=");
            ((StringBuilder)object).append(this.mScale);
            ((StringBuilder)object).append(" mIsRealSnapshot=");
            ((StringBuilder)object).append(this.mIsRealSnapshot);
            ((StringBuilder)object).append(" mWindowingMode=");
            ((StringBuilder)object).append(this.mWindowingMode);
            ((StringBuilder)object).append(" mSystemUiVisibility=");
            ((StringBuilder)object).append(this.mSystemUiVisibility);
            ((StringBuilder)object).append(" mIsTranslucent=");
            ((StringBuilder)object).append(this.mIsTranslucent);
            return ((StringBuilder)object).toString();
        }

        @Override
        public void writeToParcel(Parcel parcel, int n) {
            ComponentName.writeToParcel(this.mTopActivityComponent, parcel);
            parcel.writeParcelable(this.mSnapshot, 0);
            parcel.writeInt(this.mColorSpace.getId());
            parcel.writeInt(this.mOrientation);
            parcel.writeParcelable(this.mContentInsets, 0);
            parcel.writeBoolean(this.mReducedResolution);
            parcel.writeFloat(this.mScale);
            parcel.writeBoolean(this.mIsRealSnapshot);
            parcel.writeInt(this.mWindowingMode);
            parcel.writeInt(this.mSystemUiVisibility);
            parcel.writeBoolean(this.mIsTranslucent);
        }

    }

    static final class UidObserver
    extends IUidObserver.Stub {
        final Context mContext;
        final OnUidImportanceListener mListener;

        UidObserver(OnUidImportanceListener onUidImportanceListener, Context context) {
            this.mListener = onUidImportanceListener;
            this.mContext = context;
        }

        @Override
        public void onUidActive(int n) {
        }

        @Override
        public void onUidCachedChanged(int n, boolean bl) {
        }

        @Override
        public void onUidGone(int n, boolean bl) {
            this.mListener.onUidImportance(n, 1000);
        }

        @Override
        public void onUidIdle(int n, boolean bl) {
        }

        @Override
        public void onUidStateChanged(int n, int n2, long l) {
            this.mListener.onUidImportance(n, RunningAppProcessInfo.procStateToImportanceForClient(n2, this.mContext));
        }
    }

}

