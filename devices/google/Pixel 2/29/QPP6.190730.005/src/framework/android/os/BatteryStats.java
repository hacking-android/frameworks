/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.os.-$
 *  android.os.-$$Lambda
 *  android.os.-$$Lambda$IyvVQC-0mKtsfXbnO0kDL64hrk0
 *  android.os.-$$Lambda$q1UvBdLgHRZVzc68BxdksTmbuCw
 */
package android.os;

import android.annotation.UnsupportedAppUsage;
import android.app.ActivityManager;
import android.app.job.JobParameters;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.os.-$;
import android.os.Parcel;
import android.os.ParcelFormatException;
import android.os.Parcelable;
import android.os.SystemClock;
import android.os.UserHandle;
import android.os._$$Lambda$IyvVQC_0mKtsfXbnO0kDL64hrk0;
import android.os._$$Lambda$q1UvBdLgHRZVzc68BxdksTmbuCw;
import android.telephony.SignalStrength;
import android.text.format.DateFormat;
import android.util.ArrayMap;
import android.util.LongSparseArray;
import android.util.MutableBoolean;
import android.util.Pair;
import android.util.Printer;
import android.util.SparseArray;
import android.util.SparseIntArray;
import android.util.TimeUtils;
import android.util.proto.ProtoOutputStream;
import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.os.BatterySipper;
import com.android.internal.os.BatteryStatsHelper;
import com.android.internal.os.PowerProfile;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Formatter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public abstract class BatteryStats
implements Parcelable {
    private static final String AGGREGATED_WAKELOCK_DATA = "awl";
    public static final int AGGREGATED_WAKE_TYPE_PARTIAL = 20;
    private static final String APK_DATA = "apk";
    private static final String AUDIO_DATA = "aud";
    public static final int AUDIO_TURNED_ON = 15;
    private static final String BATTERY_DATA = "bt";
    private static final String BATTERY_DISCHARGE_DATA = "dc";
    private static final String BATTERY_LEVEL_DATA = "lv";
    private static final int BATTERY_STATS_CHECKIN_VERSION = 9;
    private static final String BLUETOOTH_CONTROLLER_DATA = "ble";
    private static final String BLUETOOTH_MISC_DATA = "blem";
    public static final int BLUETOOTH_SCAN_ON = 19;
    public static final int BLUETOOTH_UNOPTIMIZED_SCAN_ON = 21;
    private static final long BYTES_PER_GB = 0x40000000L;
    private static final long BYTES_PER_KB = 1024L;
    private static final long BYTES_PER_MB = 0x100000L;
    private static final String CAMERA_DATA = "cam";
    public static final int CAMERA_TURNED_ON = 17;
    private static final String CELLULAR_CONTROLLER_NAME = "Cellular";
    private static final String CHARGE_STEP_DATA = "csd";
    private static final String CHARGE_TIME_REMAIN_DATA = "ctr";
    static final int CHECKIN_VERSION = 34;
    private static final String CPU_DATA = "cpu";
    private static final String CPU_TIMES_AT_FREQ_DATA = "ctf";
    private static final String DATA_CONNECTION_COUNT_DATA = "dcc";
    static final String[] DATA_CONNECTION_NAMES;
    public static final int DATA_CONNECTION_NONE = 0;
    public static final int DATA_CONNECTION_OTHER = 21;
    private static final String DATA_CONNECTION_TIME_DATA = "dct";
    public static final int DEVICE_IDLE_MODE_DEEP = 2;
    public static final int DEVICE_IDLE_MODE_LIGHT = 1;
    public static final int DEVICE_IDLE_MODE_OFF = 0;
    private static final String DISCHARGE_STEP_DATA = "dsd";
    private static final String DISCHARGE_TIME_REMAIN_DATA = "dtr";
    public static final int DUMP_CHARGED_ONLY = 2;
    public static final int DUMP_DAILY_ONLY = 4;
    public static final int DUMP_DEVICE_WIFI_ONLY = 64;
    public static final int DUMP_HISTORY_ONLY = 8;
    public static final int DUMP_INCLUDE_HISTORY = 16;
    public static final int DUMP_VERBOSE = 32;
    private static final String FLASHLIGHT_DATA = "fla";
    public static final int FLASHLIGHT_TURNED_ON = 16;
    public static final int FOREGROUND_ACTIVITY = 10;
    private static final String FOREGROUND_ACTIVITY_DATA = "fg";
    public static final int FOREGROUND_SERVICE = 22;
    private static final String FOREGROUND_SERVICE_DATA = "fgs";
    public static final int FULL_WIFI_LOCK = 5;
    private static final String GLOBAL_BLUETOOTH_CONTROLLER_DATA = "gble";
    private static final String GLOBAL_CPU_FREQ_DATA = "gcf";
    private static final String GLOBAL_MODEM_CONTROLLER_DATA = "gmcd";
    private static final String GLOBAL_NETWORK_DATA = "gn";
    private static final String GLOBAL_WIFI_CONTROLLER_DATA = "gwfcd";
    private static final String GLOBAL_WIFI_DATA = "gwfl";
    private static final String HISTORY_DATA = "h";
    public static final String[] HISTORY_EVENT_CHECKIN_NAMES;
    public static final IntToString[] HISTORY_EVENT_INT_FORMATTERS;
    public static final String[] HISTORY_EVENT_NAMES;
    public static final BitDescription[] HISTORY_STATE2_DESCRIPTIONS;
    public static final BitDescription[] HISTORY_STATE_DESCRIPTIONS;
    private static final String HISTORY_STRING_POOL = "hsp";
    public static final int JOB = 14;
    private static final String JOBS_DEFERRED_DATA = "jbd";
    private static final String JOB_COMPLETION_DATA = "jbc";
    private static final String JOB_DATA = "jb";
    public static final long[] JOB_FRESHNESS_BUCKETS;
    private static final String KERNEL_WAKELOCK_DATA = "kwl";
    private static final boolean LOCAL_LOGV = false;
    public static final int MAX_TRACKED_SCREEN_STATE = 4;
    public static final double MILLISECONDS_IN_HOUR = 3600000.0;
    private static final String MISC_DATA = "m";
    private static final String MODEM_CONTROLLER_DATA = "mcd";
    public static final int NETWORK_BT_RX_DATA = 4;
    public static final int NETWORK_BT_TX_DATA = 5;
    private static final String NETWORK_DATA = "nt";
    public static final int NETWORK_MOBILE_BG_RX_DATA = 6;
    public static final int NETWORK_MOBILE_BG_TX_DATA = 7;
    public static final int NETWORK_MOBILE_RX_DATA = 0;
    public static final int NETWORK_MOBILE_TX_DATA = 1;
    public static final int NETWORK_WIFI_BG_RX_DATA = 8;
    public static final int NETWORK_WIFI_BG_TX_DATA = 9;
    public static final int NETWORK_WIFI_RX_DATA = 2;
    public static final int NETWORK_WIFI_TX_DATA = 3;
    @UnsupportedAppUsage
    public static final int NUM_DATA_CONNECTION_TYPES = 22;
    public static final int NUM_NETWORK_ACTIVITY_TYPES = 10;
    @UnsupportedAppUsage
    public static final int NUM_SCREEN_BRIGHTNESS_BINS = 5;
    public static final int NUM_WIFI_SIGNAL_STRENGTH_BINS = 5;
    public static final int NUM_WIFI_STATES = 8;
    public static final int NUM_WIFI_SUPPL_STATES = 13;
    private static final String POWER_USE_ITEM_DATA = "pwi";
    private static final String POWER_USE_SUMMARY_DATA = "pws";
    private static final String PROCESS_DATA = "pr";
    public static final int PROCESS_STATE = 12;
    private static final String RESOURCE_POWER_MANAGER_DATA = "rpm";
    public static final String RESULT_RECEIVER_CONTROLLER_KEY = "controller_activity";
    public static final int SCREEN_BRIGHTNESS_BRIGHT = 4;
    public static final int SCREEN_BRIGHTNESS_DARK = 0;
    private static final String SCREEN_BRIGHTNESS_DATA = "br";
    public static final int SCREEN_BRIGHTNESS_DIM = 1;
    public static final int SCREEN_BRIGHTNESS_LIGHT = 3;
    public static final int SCREEN_BRIGHTNESS_MEDIUM = 2;
    static final String[] SCREEN_BRIGHTNESS_NAMES;
    static final String[] SCREEN_BRIGHTNESS_SHORT_NAMES;
    protected static final boolean SCREEN_OFF_RPM_STATS_ENABLED = false;
    public static final int SENSOR = 3;
    private static final String SENSOR_DATA = "sr";
    public static final String SERVICE_NAME = "batterystats";
    private static final String SIGNAL_SCANNING_TIME_DATA = "sst";
    private static final String SIGNAL_STRENGTH_COUNT_DATA = "sgc";
    private static final String SIGNAL_STRENGTH_TIME_DATA = "sgt";
    private static final String STATE_TIME_DATA = "st";
    @Deprecated
    @UnsupportedAppUsage
    public static final int STATS_CURRENT = 1;
    public static final int STATS_SINCE_CHARGED = 0;
    @Deprecated
    public static final int STATS_SINCE_UNPLUGGED = 2;
    private static final String[] STAT_NAMES;
    public static final long STEP_LEVEL_INITIAL_MODE_MASK = 0xFF000000000000L;
    public static final int STEP_LEVEL_INITIAL_MODE_SHIFT = 48;
    public static final long STEP_LEVEL_LEVEL_MASK = 0xFF0000000000L;
    public static final int STEP_LEVEL_LEVEL_SHIFT = 40;
    public static final int[] STEP_LEVEL_MODES_OF_INTEREST;
    public static final int STEP_LEVEL_MODE_DEVICE_IDLE = 8;
    public static final String[] STEP_LEVEL_MODE_LABELS;
    public static final int STEP_LEVEL_MODE_POWER_SAVE = 4;
    public static final int STEP_LEVEL_MODE_SCREEN_STATE = 3;
    public static final int[] STEP_LEVEL_MODE_VALUES;
    public static final long STEP_LEVEL_MODIFIED_MODE_MASK = -72057594037927936L;
    public static final int STEP_LEVEL_MODIFIED_MODE_SHIFT = 56;
    public static final long STEP_LEVEL_TIME_MASK = 0xFFFFFFFFFFL;
    public static final int SYNC = 13;
    private static final String SYNC_DATA = "sy";
    private static final String TAG = "BatteryStats";
    private static final String UID_DATA = "uid";
    @VisibleForTesting
    public static final String UID_TIMES_TYPE_ALL = "A";
    private static final String USER_ACTIVITY_DATA = "ua";
    private static final String VERSION_DATA = "vers";
    private static final String VIBRATOR_DATA = "vib";
    public static final int VIBRATOR_ON = 9;
    private static final String VIDEO_DATA = "vid";
    public static final int VIDEO_TURNED_ON = 8;
    private static final String WAKELOCK_DATA = "wl";
    private static final String WAKEUP_ALARM_DATA = "wua";
    private static final String WAKEUP_REASON_DATA = "wr";
    public static final int WAKE_TYPE_DRAW = 18;
    public static final int WAKE_TYPE_FULL = 1;
    @UnsupportedAppUsage
    public static final int WAKE_TYPE_PARTIAL = 0;
    public static final int WAKE_TYPE_WINDOW = 2;
    public static final int WIFI_AGGREGATE_MULTICAST_ENABLED = 23;
    public static final int WIFI_BATCHED_SCAN = 11;
    private static final String WIFI_CONTROLLER_DATA = "wfcd";
    private static final String WIFI_CONTROLLER_NAME = "WiFi";
    private static final String WIFI_DATA = "wfl";
    private static final String WIFI_MULTICAST_DATA = "wmc";
    public static final int WIFI_MULTICAST_ENABLED = 7;
    private static final String WIFI_MULTICAST_TOTAL_DATA = "wmct";
    public static final int WIFI_RUNNING = 4;
    public static final int WIFI_SCAN = 6;
    private static final String WIFI_SIGNAL_STRENGTH_COUNT_DATA = "wsgc";
    private static final String WIFI_SIGNAL_STRENGTH_TIME_DATA = "wsgt";
    private static final String WIFI_STATE_COUNT_DATA = "wsc";
    static final String[] WIFI_STATE_NAMES;
    public static final int WIFI_STATE_OFF = 0;
    public static final int WIFI_STATE_OFF_SCANNING = 1;
    public static final int WIFI_STATE_ON_CONNECTED_P2P = 5;
    public static final int WIFI_STATE_ON_CONNECTED_STA = 4;
    public static final int WIFI_STATE_ON_CONNECTED_STA_P2P = 6;
    public static final int WIFI_STATE_ON_DISCONNECTED = 3;
    public static final int WIFI_STATE_ON_NO_NETWORKS = 2;
    public static final int WIFI_STATE_SOFT_AP = 7;
    private static final String WIFI_STATE_TIME_DATA = "wst";
    public static final int WIFI_SUPPL_STATE_ASSOCIATED = 7;
    public static final int WIFI_SUPPL_STATE_ASSOCIATING = 6;
    public static final int WIFI_SUPPL_STATE_AUTHENTICATING = 5;
    public static final int WIFI_SUPPL_STATE_COMPLETED = 10;
    private static final String WIFI_SUPPL_STATE_COUNT_DATA = "wssc";
    public static final int WIFI_SUPPL_STATE_DISCONNECTED = 1;
    public static final int WIFI_SUPPL_STATE_DORMANT = 11;
    public static final int WIFI_SUPPL_STATE_FOUR_WAY_HANDSHAKE = 8;
    public static final int WIFI_SUPPL_STATE_GROUP_HANDSHAKE = 9;
    public static final int WIFI_SUPPL_STATE_INACTIVE = 3;
    public static final int WIFI_SUPPL_STATE_INTERFACE_DISABLED = 2;
    public static final int WIFI_SUPPL_STATE_INVALID = 0;
    static final String[] WIFI_SUPPL_STATE_NAMES;
    public static final int WIFI_SUPPL_STATE_SCANNING = 4;
    static final String[] WIFI_SUPPL_STATE_SHORT_NAMES;
    private static final String WIFI_SUPPL_STATE_TIME_DATA = "wsst";
    public static final int WIFI_SUPPL_STATE_UNINITIALIZED = 12;
    private static final IntToString sIntToString;
    private static final IntToString sUidToString;
    private final StringBuilder mFormatBuilder = new StringBuilder(32);
    private final Formatter mFormatter = new Formatter(this.mFormatBuilder);

    static {
        STAT_NAMES = new String[]{"l", "c", "u"};
        JOB_FRESHNESS_BUCKETS = new long[]{3600000L, 7200000L, 14400000L, 28800000L, Long.MAX_VALUE};
        SCREEN_BRIGHTNESS_NAMES = new String[]{"dark", "dim", "medium", "light", "bright"};
        SCREEN_BRIGHTNESS_SHORT_NAMES = new String[]{"0", "1", "2", "3", "4"};
        DATA_CONNECTION_NAMES = new String[]{"none", "gprs", "edge", "umts", "cdma", "evdo_0", "evdo_A", "1xrtt", "hsdpa", "hsupa", "hspa", "iden", "evdo_b", "lte", "ehrpd", "hspap", "gsm", "td_scdma", "iwlan", "lte_ca", "nr", "other"};
        WIFI_SUPPL_STATE_NAMES = new String[]{"invalid", "disconn", "disabled", "inactive", "scanning", "authenticating", "associating", "associated", "4-way-handshake", "group-handshake", "completed", "dormant", "uninit"};
        WIFI_SUPPL_STATE_SHORT_NAMES = new String[]{"inv", "dsc", "dis", "inact", "scan", "auth", "ascing", "asced", "4-way", "group", "compl", "dorm", "uninit"};
        BitDescription bitDescription = new BitDescription(Integer.MIN_VALUE, "running", "r");
        BitDescription bitDescription2 = new BitDescription(1073741824, "wake_lock", "w");
        Object object = new BitDescription(8388608, "sensor", "s");
        BitDescription bitDescription3 = new BitDescription(536870912, "gps", "g");
        BitDescription bitDescription4 = new BitDescription(268435456, "wifi_full_lock", "Wl");
        BitDescription bitDescription5 = new BitDescription(134217728, "wifi_scan", "Ws");
        BitDescription bitDescription6 = new BitDescription(65536, "wifi_multicast", "Wm");
        BitDescription bitDescription7 = new BitDescription(67108864, "wifi_radio", "Wr");
        BitDescription bitDescription8 = new BitDescription(33554432, "mobile_radio", "Pr");
        BitDescription bitDescription9 = new BitDescription(2097152, "phone_scanning", "Psc");
        BitDescription bitDescription10 = new BitDescription(4194304, "audio", "a");
        Object object2 = new BitDescription(1048576, "screen", "S");
        BitDescription bitDescription11 = new BitDescription(524288, "plugged", "BP");
        BitDescription bitDescription12 = new BitDescription(262144, "screen_doze", "Sd");
        String[] arrstring = DATA_CONNECTION_NAMES;
        HISTORY_STATE_DESCRIPTIONS = new BitDescription[]{bitDescription, bitDescription2, object, bitDescription3, bitDescription4, bitDescription5, bitDescription6, bitDescription7, bitDescription8, bitDescription9, bitDescription10, object2, bitDescription11, bitDescription12, new BitDescription(15872, 9, "data_conn", "Pcn", arrstring, arrstring), new BitDescription(448, 6, "phone_state", "Pst", new String[]{"in", "out", "emergency", "off"}, new String[]{"in", "out", "em", "off"}), new BitDescription(56, 3, "phone_signal_strength", "Pss", SignalStrength.SIGNAL_STRENGTH_NAMES, new String[]{"0", "1", "2", "3", "4"}), new BitDescription(7, 0, "brightness", "Sb", SCREEN_BRIGHTNESS_NAMES, SCREEN_BRIGHTNESS_SHORT_NAMES)};
        HISTORY_STATE2_DESCRIPTIONS = new BitDescription[]{new BitDescription(Integer.MIN_VALUE, "power_save", "ps"), new BitDescription(1073741824, "video", "v"), new BitDescription(536870912, "wifi_running", "Ww"), new BitDescription(268435456, "wifi", "W"), new BitDescription(134217728, "flashlight", "fl"), new BitDescription(100663296, 25, "device_idle", "di", new String[]{"off", "light", "full", "???"}, new String[]{"off", "light", "full", "???"}), new BitDescription(16777216, "charging", "ch"), new BitDescription(262144, "usb_data", "Ud"), new BitDescription(8388608, "phone_in_call", "Pcl"), new BitDescription(4194304, "bluetooth", "b"), new BitDescription(112, 4, "wifi_signal_strength", "Wss", new String[]{"0", "1", "2", "3", "4"}, new String[]{"0", "1", "2", "3", "4"}), new BitDescription(15, 0, "wifi_suppl", "Wsp", WIFI_SUPPL_STATE_NAMES, WIFI_SUPPL_STATE_SHORT_NAMES), new BitDescription(2097152, "camera", "ca"), new BitDescription(1048576, "ble_scan", "bles"), new BitDescription(524288, "cellular_high_tx_power", "Chtp"), new BitDescription(128, 7, "gps_signal_quality", "Gss", new String[]{"poor", "good"}, new String[]{"poor", "good"})};
        HISTORY_EVENT_NAMES = new String[]{"null", "proc", FOREGROUND_ACTIVITY_DATA, "top", "sync", "wake_lock_in", "job", "user", "userfg", "conn", "active", "pkginst", "pkgunin", "alarm", "stats", "pkginactive", "pkgactive", "tmpwhitelist", "screenwake", "wakeupap", "longwake", "est_capacity"};
        HISTORY_EVENT_CHECKIN_NAMES = new String[]{"Enl", "Epr", "Efg", "Etp", "Esy", "Ewl", "Ejb", "Eur", "Euf", "Ecn", "Eac", "Epi", "Epu", "Eal", "Est", "Eai", "Eaa", "Etw", "Esw", "Ewa", "Elw", "Eec"};
        sUidToString = _$$Lambda$IyvVQC_0mKtsfXbnO0kDL64hrk0.INSTANCE;
        sIntToString = _$$Lambda$q1UvBdLgHRZVzc68BxdksTmbuCw.INSTANCE;
        object2 = sUidToString;
        object = sIntToString;
        HISTORY_EVENT_INT_FORMATTERS = new IntToString[]{object2, object2, object2, object2, object2, object2, object2, object2, object2, object2, object2, object, object2, object2, object2, object2, object2, object2, object2, object2, object2, object};
        WIFI_STATE_NAMES = new String[]{"off", "scanning", "no_net", "disconn", "sta", "p2p", "sta_p2p", "soft_ap"};
        STEP_LEVEL_MODES_OF_INTEREST = new int[]{7, 15, 11, 7, 7, 7, 7, 7, 15, 11};
        STEP_LEVEL_MODE_VALUES = new int[]{0, 4, 8, 1, 5, 2, 6, 3, 7, 11};
        STEP_LEVEL_MODE_LABELS = new String[]{"screen off", "screen off power save", "screen off device idle", "screen on", "screen on power save", "screen doze", "screen doze power save", "screen doze-suspend", "screen doze-suspend power save", "screen doze-suspend device idle"};
    }

    private static long computeWakeLock(Timer timer, long l, int n) {
        if (timer != null) {
            return (500L + timer.getTotalTimeLocked(l, n)) / 1000L;
        }
        return 0L;
    }

    private static boolean controllerActivityHasData(ControllerActivityCounter arrlongCounter, int n) {
        if (arrlongCounter == null) {
            return false;
        }
        if (arrlongCounter.getIdleTimeCounter().getCountLocked(n) == 0L && arrlongCounter.getRxTimeCounter().getCountLocked(n) == 0L && arrlongCounter.getPowerCounter().getCountLocked(n) == 0L && arrlongCounter.getMonitoredRailChargeConsumedMaMs().getCountLocked(n) == 0L) {
            arrlongCounter = arrlongCounter.getTxTimeCounters();
            int n2 = arrlongCounter.length;
            for (int i = 0; i < n2; ++i) {
                if (arrlongCounter[i].getCountLocked(n) == 0L) continue;
                return true;
            }
            return false;
        }
        return true;
    }

    /*
     * WARNING - void declaration
     */
    private static final void dumpControllerActivityLine(PrintWriter printWriter, int n, String object2, String arrlongCounter, ControllerActivityCounter controllerActivityCounter, int n2) {
        void var5_7;
        LongCounter[] arrlongCounter2;
        void var4_6;
        if (!BatteryStats.controllerActivityHasData((ControllerActivityCounter)var4_6, (int)var5_7)) {
            return;
        }
        BatteryStats.dumpLineHeader(printWriter, n, (String)object2, (String)arrlongCounter2);
        printWriter.print(",");
        printWriter.print(var4_6.getIdleTimeCounter().getCountLocked((int)var5_7));
        printWriter.print(",");
        printWriter.print(var4_6.getRxTimeCounter().getCountLocked((int)var5_7));
        printWriter.print(",");
        printWriter.print((double)var4_6.getPowerCounter().getCountLocked((int)var5_7) / 3600000.0);
        printWriter.print(",");
        printWriter.print((double)var4_6.getMonitoredRailChargeConsumedMaMs().getCountLocked((int)var5_7) / 3600000.0);
        for (LongCounter longCounter : var4_6.getTxTimeCounters()) {
            printWriter.print(",");
            printWriter.print(longCounter.getCountLocked((int)var5_7));
        }
        printWriter.println();
    }

    private static void dumpControllerActivityProto(ProtoOutputStream protoOutputStream, long l, ControllerActivityCounter arrlongCounter, int n) {
        if (!BatteryStats.controllerActivityHasData((ControllerActivityCounter)arrlongCounter, n)) {
            return;
        }
        long l2 = protoOutputStream.start(l);
        protoOutputStream.write(1112396529665L, arrlongCounter.getIdleTimeCounter().getCountLocked(n));
        protoOutputStream.write(1112396529666L, arrlongCounter.getRxTimeCounter().getCountLocked(n));
        protoOutputStream.write(1112396529667L, (double)arrlongCounter.getPowerCounter().getCountLocked(n) / 3600000.0);
        protoOutputStream.write(1103806595077L, (double)arrlongCounter.getMonitoredRailChargeConsumedMaMs().getCountLocked(n) / 3600000.0);
        arrlongCounter = arrlongCounter.getTxTimeCounters();
        for (int i = 0; i < arrlongCounter.length; ++i) {
            LongCounter longCounter = arrlongCounter[i];
            l = protoOutputStream.start(2246267895812L);
            protoOutputStream.write(1120986464257L, i);
            protoOutputStream.write(1112396529666L, longCounter.getCountLocked(n));
            protoOutputStream.end(l);
        }
        protoOutputStream.end(l2);
    }

    private void dumpDailyLevelStepSummary(PrintWriter printWriter, String string2, String string3, LevelStepTracker levelStepTracker, StringBuilder stringBuilder, int[] arrn) {
        int[] arrn2;
        if (levelStepTracker == null) {
            return;
        }
        long l = levelStepTracker.computeTimeEstimate(0L, 0L, arrn);
        if (l >= 0L) {
            printWriter.print(string2);
            printWriter.print(string3);
            printWriter.print(" total time: ");
            stringBuilder.setLength(0);
            BatteryStats.formatTimeMs(stringBuilder, l);
            printWriter.print(stringBuilder);
            printWriter.print(" (from ");
            printWriter.print(arrn[0]);
            printWriter.println(" steps)");
        }
        for (int i = 0; i < (arrn2 = STEP_LEVEL_MODES_OF_INTEREST).length; ++i) {
            l = levelStepTracker.computeTimeEstimate(arrn2[i], STEP_LEVEL_MODE_VALUES[i], arrn);
            if (l <= 0L) continue;
            printWriter.print(string2);
            printWriter.print(string3);
            printWriter.print(" ");
            printWriter.print(STEP_LEVEL_MODE_LABELS[i]);
            printWriter.print(" time: ");
            stringBuilder.setLength(0);
            BatteryStats.formatTimeMs(stringBuilder, l);
            printWriter.print(stringBuilder);
            printWriter.print(" (from ");
            printWriter.print(arrn[0]);
            printWriter.println(" steps)");
        }
    }

    private void dumpDailyPackageChanges(PrintWriter printWriter, String string2, ArrayList<PackageChange> arrayList) {
        if (arrayList == null) {
            return;
        }
        printWriter.print(string2);
        printWriter.println("Package changes:");
        for (int i = 0; i < arrayList.size(); ++i) {
            PackageChange packageChange = arrayList.get(i);
            if (packageChange.mUpdate) {
                printWriter.print(string2);
                printWriter.print("  Update ");
                printWriter.print(packageChange.mPackageName);
                printWriter.print(" vers=");
                printWriter.println(packageChange.mVersionCode);
                continue;
            }
            printWriter.print(string2);
            printWriter.print("  Uninstall ");
            printWriter.println(packageChange.mPackageName);
        }
    }

    private static void dumpDurationSteps(ProtoOutputStream protoOutputStream, long l, LevelStepTracker levelStepTracker) {
        if (levelStepTracker == null) {
            return;
        }
        int n = levelStepTracker.mNumStepDurations;
        for (int i = 0; i < n; ++i) {
            long l2 = protoOutputStream.start(l);
            protoOutputStream.write(1112396529665L, levelStepTracker.getDurationAt(i));
            protoOutputStream.write(1120986464258L, levelStepTracker.getLevelAt(i));
            long l3 = levelStepTracker.getInitModeAt(i);
            long l4 = levelStepTracker.getModModeAt(i);
            int n2 = 0;
            int n3 = 2;
            int n4 = 1;
            if ((l4 & 3L) == 0L) {
                n2 = (int)(3L & l3) + 1;
                n2 = n2 != 1 ? (n2 != 2 ? (n2 != 3 ? (n2 != 4 ? 5 : 4) : 3) : 1) : 2;
            }
            protoOutputStream.write(1159641169923L, n2);
            n2 = 0;
            if ((l4 & 4L) == 0L) {
                n2 = (4L & l3) != 0L ? n4 : 2;
            }
            protoOutputStream.write(1159641169924L, n2);
            n2 = 0;
            if ((l4 & 8L) == 0L) {
                n2 = (8L & l3) != 0L ? n3 : 3;
            }
            protoOutputStream.write(1159641169925L, n2);
            protoOutputStream.end(l2);
        }
    }

    private static boolean dumpDurationSteps(PrintWriter printWriter, String string2, String string3, LevelStepTracker levelStepTracker, boolean bl) {
        Object object = levelStepTracker;
        if (object == null) {
            return false;
        }
        int n = ((LevelStepTracker)object).mNumStepDurations;
        if (n <= 0) {
            return false;
        }
        if (!bl) {
            printWriter.println(string3);
        }
        String[] arrstring = new String[5];
        int n2 = 0;
        do {
            object = levelStepTracker;
            if (n2 >= n) break;
            long l = ((LevelStepTracker)object).getDurationAt(n2);
            int n3 = ((LevelStepTracker)object).getLevelAt(n2);
            long l2 = ((LevelStepTracker)object).getInitModeAt(n2);
            long l3 = ((LevelStepTracker)object).getModModeAt(n2);
            if (bl) {
                arrstring[0] = Long.toString(l);
                arrstring[1] = Integer.toString(n3);
                arrstring[2] = (l3 & 3L) == 0L ? ((n3 = (int)(l2 & 3L) + 1) != 1 ? (n3 != 2 ? (n3 != 3 ? (n3 != 4 ? "?" : "sds") : "sd") : "s+") : "s-") : "";
                if ((l3 & 4L) == 0L) {
                    object = (l2 & 4L) != 0L ? "p+" : "p-";
                    arrstring[3] = object;
                } else {
                    arrstring[3] = "";
                }
                if ((l3 & 8L) == 0L) {
                    object = (8L & l2) != 0L ? "i+" : "i-";
                    arrstring[4] = object;
                } else {
                    arrstring[4] = "";
                }
                BatteryStats.dumpLine(printWriter, 0, "i", string3, arrstring);
            } else {
                printWriter.print(string2);
                printWriter.print("#");
                printWriter.print(n2);
                printWriter.print(": ");
                TimeUtils.formatDuration(l, printWriter);
                printWriter.print(" to ");
                printWriter.print(n3);
                int n4 = 0;
                if ((l3 & 3L) == 0L) {
                    printWriter.print(" (");
                    n3 = (int)(l2 & 3L) + 1;
                    if (n3 != 1) {
                        if (n3 != 2) {
                            if (n3 != 3) {
                                if (n3 != 4) {
                                    printWriter.print("screen-?");
                                } else {
                                    printWriter.print("screen-doze-suspend");
                                }
                            } else {
                                printWriter.print("screen-doze");
                            }
                        } else {
                            printWriter.print("screen-on");
                        }
                    } else {
                        printWriter.print("screen-off");
                    }
                    n4 = 1;
                }
                String string4 = ", ";
                n3 = n4;
                if ((l3 & 4L) == 0L) {
                    object = n4 != 0 ? ", " : " (";
                    printWriter.print((String)object);
                    object = (l2 & 4L) != 0L ? "power-save-on" : "power-save-off";
                    printWriter.print((String)object);
                    n3 = 1;
                }
                n4 = n3;
                if ((l3 & 8L) == 0L) {
                    object = n3 != 0 ? string4 : " (";
                    printWriter.print((String)object);
                    object = (8L & l2) != 0L ? "device-idle-on" : "device-idle-off";
                    printWriter.print((String)object);
                    n4 = 1;
                }
                if (n4 != 0) {
                    printWriter.print(")");
                }
                printWriter.println();
            }
            ++n2;
        } while (true);
        return true;
    }

    private void dumpHistoryLocked(PrintWriter printWriter, int n, long l, boolean bl) {
        Object object;
        HistoryPrinter historyPrinter = new HistoryPrinter();
        HistoryItem historyItem = new HistoryItem();
        long l2 = -1L;
        long l3 = -1L;
        int n2 = 0;
        String string2 = null;
        while (this.getNextHistoryLocked(historyItem)) {
            int n3;
            boolean bl2;
            l2 = historyItem.time;
            if (l3 < 0L) {
                l3 = l2;
            }
            if (historyItem.time < l) continue;
            if (l >= 0L && n2 == 0) {
                if (historyItem.cmd != 5 && historyItem.cmd != 7 && historyItem.cmd != 4 && historyItem.cmd != 8) {
                    if (historyItem.currentTime != 0L) {
                        n2 = 1;
                        n3 = historyItem.cmd;
                        historyItem.cmd = (byte)5;
                        bl2 = (n & 32) != 0;
                        historyPrinter.printNextItem(printWriter, historyItem, l3, bl, bl2);
                        historyItem.cmd = (byte)n3;
                    }
                } else {
                    n2 = 1;
                    bl2 = (n & 32) != 0;
                    historyPrinter.printNextItem(printWriter, historyItem, l3, bl, bl2);
                    historyItem.cmd = (byte)(false ? 1 : 0);
                }
                object = string2;
                n3 = n2;
                if (string2 != null) {
                    if (historyItem.cmd != 0) {
                        bl2 = (n & 32) != 0;
                        historyPrinter.printNextItem(printWriter, historyItem, l3, bl, bl2);
                        historyItem.cmd = (byte)(false ? 1 : 0);
                    }
                    int n4 = historyItem.eventCode;
                    object = historyItem.eventTag;
                    historyItem.eventTag = new HistoryTag();
                    for (n3 = 0; n3 < 22; ++n3) {
                        HashMap<String, SparseIntArray> hashMap = ((HistoryEventTracker)((Object)string2)).getStateForEvent(n3);
                        if (hashMap == null) continue;
                        for (Map.Entry<String, SparseIntArray> entry : hashMap.entrySet()) {
                            SparseIntArray sparseIntArray = entry.getValue();
                            hashMap = object;
                            object = sparseIntArray;
                            for (int i = 0; i < ((SparseIntArray)object).size(); ++i) {
                                historyItem.eventCode = n3;
                                historyItem.eventTag.string = entry.getKey();
                                historyItem.eventTag.uid = ((SparseIntArray)object).keyAt(i);
                                historyItem.eventTag.poolIdx = ((SparseIntArray)object).valueAt(i);
                                bl2 = (n & 32) != 0;
                                historyPrinter.printNextItem(printWriter, historyItem, l3, bl, bl2);
                                historyItem.wakeReasonTag = null;
                                historyItem.wakelockTag = null;
                            }
                            object = hashMap;
                        }
                    }
                    historyItem.eventCode = n4;
                    historyItem.eventTag = object;
                    object = null;
                    n3 = n2;
                }
            } else {
                n3 = n2;
                object = string2;
            }
            bl2 = (n & 32) != 0;
            historyPrinter.printNextItem(printWriter, historyItem, l3, bl, bl2);
            n2 = n3;
            string2 = object;
        }
        if (l >= 0L) {
            this.commitCurrentHistoryBatchLocked();
            object = bl ? "NEXT: " : "  NEXT: ";
            printWriter.print((String)object);
            printWriter.println(1L + l2);
        }
    }

    @UnsupportedAppUsage
    private static final void dumpLine(PrintWriter printWriter, int n, String object2, String string2, Object ... arrobject) {
        BatteryStats.dumpLineHeader(printWriter, n, (String)object2, string2);
        for (Object object2 : arrobject) {
            printWriter.print(',');
            printWriter.print(object2);
        }
        printWriter.println();
    }

    private static final void dumpLineHeader(PrintWriter printWriter, int n, String string2, String string3) {
        printWriter.print(9);
        printWriter.print(',');
        printWriter.print(n);
        printWriter.print(',');
        printWriter.print(string2);
        printWriter.print(',');
        printWriter.print(string3);
    }

    private void dumpProtoAppsLocked(ProtoOutputStream protoOutputStream, BatteryStatsHelper sparseArray, List<ApplicationInfo> object) {
        int n;
        int n2;
        Object object2;
        Object object3;
        boolean bl = false;
        long l = SystemClock.uptimeMillis() * 1000L;
        long l2 = SystemClock.elapsedRealtime();
        long l3 = l2 * 1000L;
        long l4 = this.getBatteryUptime(l);
        Object object4 = new SparseArray();
        if (object != null) {
            for (n = 0; n < object.size(); ++n) {
                object2 = object.get(n);
                n2 = UserHandle.getAppId(((ApplicationInfo)object2).uid);
                object3 = (ArrayList)((SparseArray)object4).get(n2);
                if (object3 == null) {
                    object3 = new ArrayList();
                    ((SparseArray)object4).put(n2, object3);
                }
                ((ArrayList)object3).add((String)((ApplicationInfo)object2).packageName);
            }
        }
        object3 = new SparseArray();
        if ((sparseArray = ((BatteryStatsHelper)((Object)sparseArray)).getUsageList()) != null) {
            for (n = 0; n < sparseArray.size(); ++n) {
                object = (BatterySipper)sparseArray.get(n);
                if (((BatterySipper)object).drainType != BatterySipper.DrainType.APP) continue;
                ((SparseArray)object3).put(((BatterySipper)object).uidObj.getUid(), object);
            }
        }
        SparseArray<? extends Uid> sparseArray2 = this.getUidStats();
        int n3 = sparseArray2.size();
        SparseArray sparseArray3 = sparseArray;
        sparseArray = object4;
        for (n2 = 0; n2 < n3; ++n2) {
            Object object5;
            int n4;
            Object object6;
            long l5;
            Object object7;
            long l6;
            int n5;
            Object object8;
            long l7;
            long l8 = protoOutputStream.start(2246267895813L);
            Object object9 = sparseArray2.valueAt(n2);
            int n6 = sparseArray2.keyAt(n2);
            protoOutputStream.write(1120986464257L, n6);
            n = UserHandle.getAppId(n6);
            object4 = (ArrayList)sparseArray.get(n);
            if (object4 == null) {
                object4 = new ArrayList();
            }
            object = ((Uid)object9).getPackageStats();
            for (n = object.size() - 1; n >= 0; --n) {
                object2 = ((ArrayMap)object).keyAt(n);
                object8 = ((ArrayMap)object).valueAt(n).getServiceStats();
                if (((ArrayMap)object8).size() == 0) continue;
                l5 = protoOutputStream.start(2246267895810L);
                protoOutputStream.write(1138166333441L, (String)object2);
                ((ArrayList)object4).remove(object2);
                for (n5 = object8.size() - 1; n5 >= 0; --n5) {
                    object7 = ((ArrayMap)object8).valueAt(n5);
                    l7 = BatteryStats.roundUsToMs(((Uid.Pkg.Serv)object7).getStartTime(l4, 0));
                    object5 = ((Uid.Pkg.Serv)object7).getStarts(0);
                    n4 = ((Uid.Pkg.Serv)object7).getLaunches(0);
                    if (l7 == 0L && object5 == 0 && n4 == 0) continue;
                    l6 = protoOutputStream.start(2246267895810L);
                    protoOutputStream.write(1138166333441L, (String)((ArrayMap)object8).keyAt(n5));
                    protoOutputStream.write(1112396529666L, l7);
                    protoOutputStream.write(1120986464259L, (int)object5);
                    protoOutputStream.write(1120986464260L, n4);
                    protoOutputStream.end(l6);
                }
                protoOutputStream.end(l5);
            }
            l5 = l3;
            object4 = ((ArrayList)object4).iterator();
            while (object4.hasNext()) {
                object2 = (String)object4.next();
                l3 = protoOutputStream.start(2246267895810L);
                protoOutputStream.write(1138166333441L, (String)object2);
                protoOutputStream.end(l3);
            }
            if (((Uid)object9).getAggregatedPartialWakelockTimer() != null) {
                object4 = ((Uid)object9).getAggregatedPartialWakelockTimer();
                l3 = l2;
                l6 = ((Timer)object4).getTotalDurationMsLocked(l3);
                l3 = (object4 = ((Timer)object4).getSubTimer()) != null ? ((Timer)object4).getTotalDurationMsLocked(l3) : 0L;
                l7 = protoOutputStream.start(1146756268056L);
                protoOutputStream.write(1112396529665L, l6);
                protoOutputStream.write(1112396529666L, l3);
                protoOutputStream.end(l7);
            }
            object4 = ((Uid)object9).getAudioTurnedOnTimer();
            l3 = l8;
            object7 = object3;
            BatteryStats.dumpTimer(protoOutputStream, 1146756268040L, (Timer)object4, l5, 0);
            BatteryStats.dumpControllerActivityProto(protoOutputStream, 1146756268035L, ((Uid)object9).getBluetoothControllerActivity(), 0);
            object3 = ((Uid)object9).getBluetoothScanTimer();
            if (object3 != null) {
                l8 = protoOutputStream.start(1146756268038L);
                BatteryStats.dumpTimer(protoOutputStream, 1146756268033L, (Timer)object3, l5, 0);
                BatteryStats.dumpTimer(protoOutputStream, 1146756268034L, ((Uid)object9).getBluetoothScanBackgroundTimer(), l5, 0);
                BatteryStats.dumpTimer(protoOutputStream, 1146756268035L, ((Uid)object9).getBluetoothUnoptimizedScanTimer(), l5, 0);
                BatteryStats.dumpTimer(protoOutputStream, 1146756268036L, ((Uid)object9).getBluetoothUnoptimizedScanBackgroundTimer(), l5, 0);
                n = ((Uid)object9).getBluetoothScanResultCounter() != null ? ((Uid)object9).getBluetoothScanResultCounter().getCountLocked(0) : 0;
                protoOutputStream.write(1120986464261L, n);
                n = ((Uid)object9).getBluetoothScanResultBgCounter() != null ? ((Uid)object9).getBluetoothScanResultBgCounter().getCountLocked(0) : 0;
                protoOutputStream.write(1120986464262L, n);
                protoOutputStream.end(l8);
            }
            BatteryStats.dumpTimer(protoOutputStream, 1146756268041L, ((Uid)object9).getCameraTurnedOnTimer(), l5, 0);
            l6 = protoOutputStream.start(1146756268039L);
            protoOutputStream.write(1112396529665L, BatteryStats.roundUsToMs(((Uid)object9).getUserCpuTimeUs(0)));
            protoOutputStream.write(1112396529666L, BatteryStats.roundUsToMs(((Uid)object9).getSystemCpuTimeUs(0)));
            object2 = this.getCpuFreqs();
            if (object2 != null) {
                object6 = ((Uid)object9).getCpuFreqTimes(0);
                if (object6 != null && ((long[])object6).length == ((long[])object2).length) {
                    object4 = object8 = ((Uid)object9).getScreenOffCpuFreqTimes(0);
                    if (object8 == null) {
                        object4 = new long[((long[])object6).length];
                    }
                    for (n = 0; n < ((long[])object6).length; ++n) {
                        l8 = protoOutputStream.start(2246267895811L);
                        protoOutputStream.write(1120986464257L, n + 1);
                        protoOutputStream.write(1112396529666L, object6[n]);
                        protoOutputStream.write(1112396529667L, object4[n]);
                        protoOutputStream.end(l8);
                    }
                    object8 = object3;
                    object4 = object;
                    l8 = l3;
                } else {
                    object8 = object3;
                    object4 = object;
                    l8 = l3;
                }
            } else {
                l8 = l3;
                object4 = object;
                object8 = object3;
            }
            object3 = object9;
            object = object7;
            for (n = 0; n < 7; ++n) {
                object6 = ((Uid)object3).getCpuFreqTimes(0, n);
                if (object6 != null && ((long[])object6).length == ((long[])object2).length) {
                    object7 = object9 = ((Uid)object3).getScreenOffCpuFreqTimes(0, n);
                    if (object9 == null) {
                        object7 = new long[((long[])object6).length];
                    }
                    l3 = protoOutputStream.start(2246267895812L);
                    protoOutputStream.write(1159641169921L, n);
                    for (n5 = 0; n5 < ((long[])object6).length; ++n5) {
                        l7 = protoOutputStream.start(2246267895810L);
                        protoOutputStream.write(1120986464257L, n5 + 1);
                        protoOutputStream.write(1112396529666L, object6[n5]);
                        protoOutputStream.write(1112396529667L, object7[n5]);
                        protoOutputStream.end(l7);
                    }
                    object7 = object;
                    object = object3;
                    object3 = object2;
                    object2 = object8;
                    protoOutputStream.end(l3);
                    object8 = object3;
                    object3 = object7;
                } else {
                    object7 = object;
                    object = object3;
                    object9 = object2;
                    object2 = object8;
                    object3 = object7;
                    object8 = object9;
                }
                object7 = object8;
                object8 = object2;
                object2 = object3;
                object3 = object;
                object = object2;
                object2 = object7;
            }
            protoOutputStream.end(l6);
            object2 = ((Uid)object3).getFlashlightTurnedOnTimer();
            l3 = l6;
            BatteryStats.dumpTimer(protoOutputStream, 1146756268042L, (Timer)object2, l5, 0);
            BatteryStats.dumpTimer(protoOutputStream, 1146756268043L, ((Uid)object3).getForegroundActivityTimer(), l5, 0);
            BatteryStats.dumpTimer(protoOutputStream, 1146756268044L, ((Uid)object3).getForegroundServiceTimer(), l5, 0);
            object8 = ((Uid)object3).getJobCompletionStats();
            object2 = new int[]{0, 1, 2, 3, 4};
            for (n = 0; n < ((ArrayMap)object8).size(); ++n) {
                object7 = (SparseIntArray)((ArrayMap)object8).valueAt(n);
                if (object7 == null) continue;
                l6 = protoOutputStream.start(2246267895824L);
                protoOutputStream.write(1138166333441L, (String)((ArrayMap)object8).keyAt(n));
                n4 = ((Object)object2).length;
                for (n5 = 0; n5 < n4; ++n5) {
                    object5 = object2[n5];
                    l7 = protoOutputStream.start(2246267895810L);
                    protoOutputStream.write(1159641169921L, (int)object5);
                    protoOutputStream.write(1120986464258L, ((SparseIntArray)object7).get((int)object5, 0));
                    protoOutputStream.end(l7);
                }
                protoOutputStream.end(l6);
            }
            object7 = ((Uid)object3).getJobStats();
            for (n = object7.size() - 1; n >= 0; --n) {
                object6 = (Timer)((ArrayMap)object7).valueAt(n);
                object9 = ((Timer)object6).getSubTimer();
                l3 = protoOutputStream.start(2246267895823L);
                protoOutputStream.write(1138166333441L, (String)((ArrayMap)object7).keyAt(n));
                BatteryStats.dumpTimer(protoOutputStream, 1146756268034L, (Timer)object6, l5, 0);
                BatteryStats.dumpTimer(protoOutputStream, 1146756268035L, (Timer)object9, l5, 0);
                protoOutputStream.end(l3);
            }
            BatteryStats.dumpControllerActivityProto(protoOutputStream, 1146756268036L, ((Uid)object3).getModemControllerActivity(), 0);
            l6 = protoOutputStream.start(1146756268049L);
            protoOutputStream.write(1112396529665L, ((Uid)object3).getNetworkActivityBytes(0, 0));
            protoOutputStream.write(1112396529666L, ((Uid)object3).getNetworkActivityBytes(1, 0));
            protoOutputStream.write(1112396529667L, ((Uid)object3).getNetworkActivityBytes(2, 0));
            protoOutputStream.write(1112396529668L, ((Uid)object3).getNetworkActivityBytes(3, 0));
            protoOutputStream.write(1112396529669L, ((Uid)object3).getNetworkActivityBytes(4, 0));
            protoOutputStream.write(1112396529670L, ((Uid)object3).getNetworkActivityBytes(5, 0));
            protoOutputStream.write(1112396529671L, ((Uid)object3).getNetworkActivityPackets(0, 0));
            protoOutputStream.write(1112396529672L, ((Uid)object3).getNetworkActivityPackets(1, 0));
            protoOutputStream.write(1112396529673L, ((Uid)object3).getNetworkActivityPackets(2, 0));
            protoOutputStream.write(1112396529674L, ((Uid)object3).getNetworkActivityPackets(3, 0));
            protoOutputStream.write(1112396529675L, BatteryStats.roundUsToMs(((Uid)object3).getMobileRadioActiveTime(0)));
            protoOutputStream.write(1120986464268L, ((Uid)object3).getMobileRadioActiveCount(0));
            protoOutputStream.write(1120986464269L, ((Uid)object3).getMobileRadioApWakeupCount(0));
            protoOutputStream.write(1120986464270L, ((Uid)object3).getWifiRadioApWakeupCount(0));
            protoOutputStream.write(1112396529679L, ((Uid)object3).getNetworkActivityBytes(6, 0));
            protoOutputStream.write(1112396529680L, ((Uid)object3).getNetworkActivityBytes(7, 0));
            protoOutputStream.write(1112396529681L, ((Uid)object3).getNetworkActivityBytes(8, 0));
            protoOutputStream.write(1112396529682L, ((Uid)object3).getNetworkActivityBytes(9, 0));
            protoOutputStream.write(1112396529683L, ((Uid)object3).getNetworkActivityPackets(6, 0));
            protoOutputStream.write(1112396529684L, ((Uid)object3).getNetworkActivityPackets(7, 0));
            protoOutputStream.write(1112396529685L, ((Uid)object3).getNetworkActivityPackets(8, 0));
            protoOutputStream.write(1112396529686L, ((Uid)object3).getNetworkActivityPackets(9, 0));
            protoOutputStream.end(l6);
            n = n6;
            object7 = (BatterySipper)((SparseArray)object).get(n);
            if (object7 != null) {
                l3 = protoOutputStream.start(1146756268050L);
                protoOutputStream.write(0x10100000001L, ((BatterySipper)object7).totalPowerMah);
                protoOutputStream.write(1133871366146L, ((BatterySipper)object7).shouldHide);
                protoOutputStream.write(1103806595075L, ((BatterySipper)object7).screenPowerMah);
                protoOutputStream.write(1103806595076L, ((BatterySipper)object7).proportionalSmearMah);
                protoOutputStream.end(l3);
            }
            object9 = ((Uid)object3).getProcessStats();
            object2 = object8;
            for (n5 = object9.size() - 1; n5 >= 0; --n5) {
                object8 = (Uid.Proc)((ArrayMap)object9).valueAt(n5);
                l3 = protoOutputStream.start(2246267895827L);
                protoOutputStream.write(1138166333441L, (String)((ArrayMap)object9).keyAt(n5));
                protoOutputStream.write(1112396529666L, ((Uid.Proc)object8).getUserTime(0));
                protoOutputStream.write(1112396529667L, ((Uid.Proc)object8).getSystemTime(0));
                protoOutputStream.write(1112396529668L, ((Uid.Proc)object8).getForegroundTime(0));
                protoOutputStream.write(1120986464261L, ((Uid.Proc)object8).getStarts(0));
                protoOutputStream.write(1120986464262L, ((Uid.Proc)object8).getNumAnrs(0));
                protoOutputStream.write(1120986464263L, ((Uid.Proc)object8).getNumCrashes(0));
                protoOutputStream.end(l3);
            }
            object8 = ((Uid)object3).getSensorStats();
            object2 = object7;
            for (n5 = 0; n5 < ((SparseArray)object8).size(); ++n5) {
                object9 = (Uid.Sensor)((SparseArray)object8).valueAt(n5);
                object7 = ((Uid.Sensor)object9).getSensorTime();
                if (object7 == null) continue;
                object9 = ((Uid.Sensor)object9).getSensorBackgroundTime();
                n6 = ((SparseArray)object8).keyAt(n5);
                l3 = protoOutputStream.start(2246267895829L);
                protoOutputStream.write(1120986464257L, n6);
                BatteryStats.dumpTimer(protoOutputStream, 1146756268034L, (Timer)object7, l5, 0);
                BatteryStats.dumpTimer(protoOutputStream, 1146756268035L, (Timer)object9, l5, 0);
                protoOutputStream.end(l3);
            }
            l3 = l5;
            for (n = 0; n < 7; ++n) {
                l7 = BatteryStats.roundUsToMs(((Uid)object3).getProcessStateTime(n, l3, 0));
                if (l7 == 0L) continue;
                l5 = protoOutputStream.start(2246267895828L);
                protoOutputStream.write(1159641169921L, n);
                protoOutputStream.write(1112396529666L, l7);
                protoOutputStream.end(l5);
            }
            object2 = ((Uid)object3).getSyncStats();
            for (n = object2.size() - 1; n >= 0; --n) {
                object7 = (Timer)((ArrayMap)object2).valueAt(n);
                object8 = ((Timer)object7).getSubTimer();
                l5 = protoOutputStream.start(2246267895830L);
                protoOutputStream.write(1138166333441L, (String)((ArrayMap)object2).keyAt(n));
                BatteryStats.dumpTimer(protoOutputStream, 1146756268034L, (Timer)object7, l3, 0);
                BatteryStats.dumpTimer(protoOutputStream, 1146756268035L, (Timer)object8, l3, 0);
                protoOutputStream.end(l5);
            }
            if (((Uid)object3).hasUserActivity()) {
                for (n = 0; n < Uid.NUM_USER_ACTIVITY_TYPES; ++n) {
                    n5 = ((Uid)object3).getUserActivityCount(n, 0);
                    if (n5 == 0) continue;
                    l5 = protoOutputStream.start(2246267895831L);
                    protoOutputStream.write(1159641169921L, n);
                    protoOutputStream.write(1120986464258L, n5);
                    protoOutputStream.end(l5);
                }
            }
            BatteryStats.dumpTimer(protoOutputStream, 1146756268045L, ((Uid)object3).getVibratorOnTimer(), l3, 0);
            BatteryStats.dumpTimer(protoOutputStream, 1146756268046L, ((Uid)object3).getVideoTurnedOnTimer(), l3, 0);
            object2 = ((Uid)object3).getWakelockStats();
            l5 = l6;
            for (n = object2.size() - 1; n >= 0; --n) {
                object8 = (Uid.Wakelock)((ArrayMap)object2).valueAt(n);
                l6 = protoOutputStream.start(2246267895833L);
                protoOutputStream.write(1138166333441L, (String)((ArrayMap)object2).keyAt(n));
                BatteryStats.dumpTimer(protoOutputStream, 1146756268034L, ((Uid.Wakelock)object8).getWakeTime(1), l3, 0);
                object7 = ((Uid.Wakelock)object8).getWakeTime(0);
                if (object7 != null) {
                    BatteryStats.dumpTimer(protoOutputStream, 1146756268035L, (Timer)object7, l3, 0);
                    BatteryStats.dumpTimer(protoOutputStream, 1146756268036L, ((Timer)object7).getSubTimer(), l3, 0);
                }
                BatteryStats.dumpTimer(protoOutputStream, 1146756268037L, ((Uid.Wakelock)object8).getWakeTime(2), l3, 0);
                protoOutputStream.end(l6);
            }
            BatteryStats.dumpTimer(protoOutputStream, 1146756268060L, ((Uid)object3).getMulticastWakelockStats(), l3, 0);
            for (n = object4.size() - 1; n >= 0; --n) {
                object2 = ((Uid.Pkg)((ArrayMap)object4).valueAt(n)).getWakeupAlarmStats();
                for (n5 = object2.size() - 1; n5 >= 0; --n5) {
                    l5 = protoOutputStream.start(2246267895834L);
                    protoOutputStream.write(1138166333441L, (String)((ArrayMap)object2).keyAt(n5));
                    protoOutputStream.write(1120986464258L, ((Counter)((ArrayMap)object2).valueAt(n5)).getCountLocked(0));
                    protoOutputStream.end(l5);
                }
            }
            BatteryStats.dumpControllerActivityProto(protoOutputStream, 1146756268037L, ((Uid)object3).getWifiControllerActivity(), 0);
            l5 = protoOutputStream.start(1146756268059L);
            protoOutputStream.write(1112396529665L, BatteryStats.roundUsToMs(((Uid)object3).getFullWifiLockTime(l3, 0)));
            BatteryStats.dumpTimer(protoOutputStream, 1146756268035L, ((Uid)object3).getWifiScanTimer(), l3, 0);
            protoOutputStream.write(1112396529666L, BatteryStats.roundUsToMs(((Uid)object3).getWifiRunningTime(l3, 0)));
            BatteryStats.dumpTimer(protoOutputStream, 1146756268036L, ((Uid)object3).getWifiScanBackgroundTimer(), l3, 0);
            protoOutputStream.end(l5);
            protoOutputStream.end(l8);
            object3 = object;
        }
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void dumpProtoHistoryLocked(ProtoOutputStream var1_1, int var2_3, long var3_4) {
        if (!this.startIteratingHistoryLocked()) {
            return;
        }
        var1_1.write(1120986464257L, 34);
        var1_1.write(1112396529666L, this.getParcelVersion());
        var1_1.write(1138166333443L, this.getStartPlatformVersion());
        var1_1.write(1138166333444L, this.getEndPlatformVersion());
        try {
            for (var5_5 = 0; var5_5 < this.getHistoryStringPoolSize(); ++var5_5) {
                var6_6 = var1_1.start(2246267895813L);
                var1_1.write(1120986464257L, var5_5);
                var1_1.write(1120986464258L, this.getHistoryTagPoolUid(var5_5));
                var1_1.write(1138166333443L, this.getHistoryTagPoolString(var5_5));
                var1_1.end(var6_6);
            }
            var8_7 = new HistoryPrinter();
            var9_8 = new HistoryItem();
            var10_9 = -1L;
            var6_6 = -1L;
            var5_5 = 0;
            var12_10 = null;
            block5 : do {
                if (!this.getNextHistoryLocked(var9_8)) ** GOTO lbl-1000
                var10_9 = var9_8.time;
                if (var6_6 < 0L) {
                    var6_6 = var10_9;
                }
                if (var9_8.time < var3_4) continue;
                if (var3_4 < 0L || var5_5 != 0) ** GOTO lbl89
                if (var9_8.cmd != 5 && var9_8.cmd != 7 && var9_8.cmd != 4 && var9_8.cmd != 8) {
                    if (var9_8.currentTime != 0L) {
                        var5_5 = var9_8.cmd;
                        var9_8.cmd = (byte)5;
                        var13_11 = (var2_3 & 32) != 0;
                        var8_7.printNextItem(var1_1, var9_8, var6_6, var13_11);
                        var9_8.cmd = (byte)var5_5;
                        var5_5 = 1;
                    }
                } else {
                    var5_5 = 1;
                    var13_11 = (var2_3 & 32) != 0;
                    var8_7.printNextItem(var1_1, var9_8, var6_6, var13_11);
                    var9_8.cmd = (byte)(false ? 1 : 0);
                }
                if (var12_10 == null) ** GOTO lbl89
                if (var9_8.cmd != 0) {
                    var13_11 = (var2_3 & 32) != 0;
                    var8_7.printNextItem(var1_1, var9_8, var6_6, var13_11);
                    var9_8.cmd = (byte)(false ? 1 : 0);
                }
                var14_12 = var9_8.eventCode;
                var15_13 = var9_8.eventTag;
                var16_14 = new HistoryTag();
                var9_8.eventTag = var16_14;
                var16_14 = var12_10;
                var12_10 = var15_13;
                ** GOTO lbl63
lbl-1000: // 1 sources:
                {
                    if (var3_4 >= 0L) {
                        this.commitCurrentHistoryBatchLocked();
                        var12_10 = new StringBuilder();
                        var12_10.append("NEXT: ");
                        var12_10.append(1L + var10_9);
                        var1_1.write(2237677961222L, var12_10.toString());
                    }
                    this.finishIteratingHistoryLocked();
                    return;
lbl63: // 2 sources:
                    block6 : for (var17_15 = 0; var17_15 < 22; ++var17_15) {
                        block17 : {
                            var15_13 = var16_14.getStateForEvent(var17_15);
                            if (var15_13 != null) break block17;
                            var15_13 = var16_14;
                            var16_14 = var12_10;
                            var12_10 = var15_13;
                            ** GOTO lbl81
                        }
                        var18_16 = var15_13.entrySet().iterator();
                        do {
                            if (var18_16.hasNext()) {
                                var19_17 = var18_16.next();
                                var15_13 = (SparseIntArray)var19_17.getValue();
                                var20_18 = 0;
                                break block5;
                            }
                            var15_13 = var12_10;
                            var12_10 = var16_14;
                            var16_14 = var15_13;
lbl81: // 2 sources:
                            var15_13 = var12_10;
                            var12_10 = var16_14;
                            var16_14 = var15_13;
                            continue block6;
                            break;
                        } while (true);
                    }
                    ** try [egrp 2[TRYBLOCK] [18 : 672->780)] { 
lbl86: // 1 sources:
                    var9_8.eventCode = var14_12;
                    var9_8.eventTag = var12_10;
                    var12_10 = null;
lbl89: // 3 sources:
                    var13_11 = (var2_3 & 32) != 0;
                    var8_7.printNextItem(var1_1, var9_8, var6_6, var13_11);
                    continue;
                }
                break;
            } while (true);
            do {
                if (var20_18 >= var15_13.size()) ** continue;
                var9_8.eventCode = var17_15;
                var9_8.eventTag.string = (String)var19_17.getKey();
                var9_8.eventTag.uid = var15_13.keyAt(var20_18);
                var9_8.eventTag.poolIdx = var15_13.valueAt(var20_18);
                var13_11 = (var2_3 & 32) != 0;
                var8_7.printNextItem(var1_1, var9_8, var6_6, var13_11);
                var9_8.wakeReasonTag = null;
                var9_8.wakelockTag = null;
                ++var20_18;
            } while (true);
        }
lbl104: // 2 sources:
        catch (Throwable var1_2) {
            this.finishIteratingHistoryLocked();
            throw var1_2;
        }
    }

    private void dumpProtoSystemLocked(ProtoOutputStream protoOutputStream, BatteryStatsHelper object4) {
        long l;
        int n;
        Map.Entry<String, ? extends Timer> entry;
        long l2 = protoOutputStream.start(1146756268038L);
        long l3 = SystemClock.uptimeMillis() * 1000L;
        long l4 = SystemClock.elapsedRealtime() * 1000L;
        long l5 = protoOutputStream.start(1146756268033L);
        protoOutputStream.write(1112396529665L, this.getStartClockTime());
        protoOutputStream.write(1112396529666L, this.getStartCount());
        protoOutputStream.write(1112396529667L, this.computeRealtime(l4, 0) / 1000L);
        protoOutputStream.write(1112396529668L, this.computeUptime(l3, 0) / 1000L);
        protoOutputStream.write(1112396529669L, this.computeBatteryRealtime(l4, 0) / 1000L);
        protoOutputStream.write(1112396529670L, this.computeBatteryUptime(l3, 0) / 1000L);
        protoOutputStream.write(1112396529671L, this.computeBatteryScreenOffRealtime(l4, 0) / 1000L);
        protoOutputStream.write(1112396529672L, this.computeBatteryScreenOffUptime(l3, 0) / 1000L);
        protoOutputStream.write(1112396529673L, this.getScreenDozeTime(l4, 0) / 1000L);
        protoOutputStream.write(1112396529674L, this.getEstimatedBatteryCapacity());
        protoOutputStream.write(1112396529675L, this.getMinLearnedBatteryCapacity());
        protoOutputStream.write(1112396529676L, this.getMaxLearnedBatteryCapacity());
        protoOutputStream.end(l5);
        l5 = protoOutputStream.start(1146756268034L);
        protoOutputStream.write(1120986464257L, this.getLowDischargeAmountSinceCharge());
        protoOutputStream.write(1120986464258L, this.getHighDischargeAmountSinceCharge());
        protoOutputStream.write(1120986464259L, this.getDischargeAmountScreenOnSinceCharge());
        protoOutputStream.write(1120986464260L, this.getDischargeAmountScreenOffSinceCharge());
        protoOutputStream.write(1120986464261L, this.getDischargeAmountScreenDozeSinceCharge());
        protoOutputStream.write(1112396529670L, this.getUahDischarge(0) / 1000L);
        protoOutputStream.write(1112396529671L, this.getUahDischargeScreenOff(0) / 1000L);
        protoOutputStream.write(1112396529672L, this.getUahDischargeScreenDoze(0) / 1000L);
        protoOutputStream.write(1112396529673L, this.getUahDischargeLightDoze(0) / 1000L);
        protoOutputStream.write(1112396529674L, this.getUahDischargeDeepDoze(0) / 1000L);
        protoOutputStream.end(l5);
        l3 = this.computeChargeTimeRemaining(l4);
        if (l3 >= 0L) {
            protoOutputStream.write(1112396529667L, l3 / 1000L);
        } else {
            l3 = this.computeBatteryTimeRemaining(l4);
            if (l3 >= 0L) {
                protoOutputStream.write(1112396529668L, l3 / 1000L);
            } else {
                protoOutputStream.write(1112396529668L, -1);
            }
        }
        BatteryStats.dumpDurationSteps(protoOutputStream, 2246267895813L, this.getChargeLevelStepTracker());
        int n2 = 0;
        do {
            boolean bl = true;
            if (n2 >= 22) break;
            if (n2 != 0) {
                bl = false;
            }
            n = n2 == 21 ? 0 : n2;
            l = protoOutputStream.start(2246267895816L);
            if (bl) {
                protoOutputStream.write(1133871366146L, bl);
            } else {
                protoOutputStream.write(1159641169921L, n);
            }
            Timer timer = this.getPhoneDataConnectionTimer(n2);
            BatteryStats.dumpTimer(protoOutputStream, 1146756268035L, timer, l4, 0);
            protoOutputStream.end(l);
            ++n2;
        } while (true);
        BatteryStats.dumpDurationSteps(protoOutputStream, 2246267895814L, this.getDischargeLevelStepTracker());
        long[] arrl = this.getCpuFreqs();
        if (arrl != null) {
            n = arrl.length;
            for (n2 = 0; n2 < n; ++n2) {
                protoOutputStream.write(2211908157447L, arrl[n2]);
            }
        }
        BatteryStats.dumpControllerActivityProto(protoOutputStream, 1146756268041L, this.getBluetoothControllerActivity(), 0);
        BatteryStats.dumpControllerActivityProto(protoOutputStream, 1146756268042L, this.getModemControllerActivity(), 0);
        l5 = protoOutputStream.start(1146756268044L);
        protoOutputStream.write(1112396529665L, this.getNetworkActivityBytes(0, 0));
        protoOutputStream.write(1112396529666L, this.getNetworkActivityBytes(1, 0));
        protoOutputStream.write(1112396529669L, this.getNetworkActivityPackets(0, 0));
        protoOutputStream.write(1112396529670L, this.getNetworkActivityPackets(1, 0));
        protoOutputStream.write(1112396529667L, this.getNetworkActivityBytes(2, 0));
        protoOutputStream.write(1112396529668L, this.getNetworkActivityBytes(3, 0));
        protoOutputStream.write(1112396529671L, this.getNetworkActivityPackets(2, 0));
        protoOutputStream.write(1112396529672L, this.getNetworkActivityPackets(3, 0));
        protoOutputStream.write(1112396529673L, this.getNetworkActivityBytes(4, 0));
        protoOutputStream.write(1112396529674L, this.getNetworkActivityBytes(5, 0));
        protoOutputStream.end(l5);
        BatteryStats.dumpControllerActivityProto(protoOutputStream, 1146756268043L, this.getWifiControllerActivity(), 0);
        l5 = protoOutputStream.start(1146756268045L);
        protoOutputStream.write(1112396529665L, this.getWifiOnTime(l4, 0) / 1000L);
        protoOutputStream.write(1112396529666L, this.getGlobalWifiRunningTime(l4, 0) / 1000L);
        protoOutputStream.end(l5);
        for (Map.Entry<String, ? extends Timer> entry2 : this.getKernelWakelockStats().entrySet()) {
            l = protoOutputStream.start(2246267895822L);
            protoOutputStream.write(1138166333441L, entry2.getKey());
            BatteryStats.dumpTimer(protoOutputStream, 1146756268034L, entry2.getValue(), l4, 0);
            protoOutputStream.end(l);
        }
        SparseArray<? extends Uid> sparseArray = this.getUidStats();
        l5 = 0L;
        l3 = 0L;
        for (n2 = 0; n2 < sparseArray.size(); ++n2) {
            entry = sparseArray.valueAt(n2);
            entry = ((Uid)((Object)entry)).getWakelockStats();
            l = l5;
            for (n = entry.size() - 1; n >= 0; --n) {
                Uid.Wakelock wakelock = ((ArrayMap)((Object)entry)).valueAt(n);
                Timer timer = wakelock.getWakeTime(1);
                if (timer != null) {
                    l += timer.getTotalTimeLocked(l4, 0);
                }
                timer = wakelock.getWakeTime(0);
                l5 = l3;
                if (timer != null) {
                    l5 = l3 + timer.getTotalTimeLocked(l4, 0);
                }
                l3 = l5;
            }
            l5 = l;
        }
        l = protoOutputStream.start(1146756268047L);
        protoOutputStream.write(1112396529665L, this.getScreenOnTime(l4, 0) / 1000L);
        protoOutputStream.write(1112396529666L, this.getPhoneOnTime(l4, 0) / 1000L);
        protoOutputStream.write(1112396529667L, l5 / 1000L);
        protoOutputStream.write(1112396529668L, l3 / 1000L);
        protoOutputStream.write(1112396529669L, this.getMobileRadioActiveTime(l4, 0) / 1000L);
        protoOutputStream.write(1112396529670L, this.getMobileRadioActiveAdjustedTime(0) / 1000L);
        protoOutputStream.write(1120986464263L, this.getMobileRadioActiveCount(0));
        protoOutputStream.write(1120986464264L, this.getMobileRadioActiveUnknownTime(0) / 1000L);
        protoOutputStream.write(1112396529673L, this.getInteractiveTime(l4, 0) / 1000L);
        protoOutputStream.write(1112396529674L, this.getPowerSaveModeEnabledTime(l4, 0) / 1000L);
        protoOutputStream.write(1120986464267L, this.getNumConnectivityChange(0));
        protoOutputStream.write(1112396529676L, this.getDeviceIdleModeTime(2, l4, 0) / 1000L);
        protoOutputStream.write(1120986464269L, this.getDeviceIdleModeCount(2, 0));
        protoOutputStream.write(1112396529678L, this.getDeviceIdlingTime(2, l4, 0) / 1000L);
        protoOutputStream.write(1120986464271L, this.getDeviceIdlingCount(2, 0));
        protoOutputStream.write(1112396529680L, this.getLongestDeviceIdleModeTime(2));
        protoOutputStream.write(1112396529681L, this.getDeviceIdleModeTime(1, l4, 0) / 1000L);
        protoOutputStream.write(1120986464274L, this.getDeviceIdleModeCount(1, 0));
        protoOutputStream.write(1112396529683L, this.getDeviceIdlingTime(1, l4, 0) / 1000L);
        protoOutputStream.write(1120986464276L, this.getDeviceIdlingCount(1, 0));
        protoOutputStream.write(1112396529685L, this.getLongestDeviceIdleModeTime(1));
        protoOutputStream.end(l);
        l5 = this.getWifiMulticastWakelockTime(l4, 0);
        int n3 = this.getWifiMulticastWakelockCount(0);
        l3 = protoOutputStream.start(1146756268055L);
        protoOutputStream.write(1112396529665L, l5 / 1000L);
        protoOutputStream.write(1120986464258L, n3);
        protoOutputStream.end(l3);
        List<BatterySipper> list = ((BatteryStatsHelper)object4).getUsageList();
        if (list != null) {
            block21 : for (n = 0; n < list.size(); ++n) {
                entry = list.get(n);
                n2 = 0;
                int n4 = 0;
                switch (((BatterySipper)entry).drainType) {
                    default: {
                        break;
                    }
                    case MEMORY: {
                        n2 = 12;
                        break;
                    }
                    case CAMERA: {
                        n2 = 11;
                        break;
                    }
                    case OVERCOUNTED: {
                        n2 = 10;
                        break;
                    }
                    case UNACCOUNTED: {
                        n2 = 9;
                        break;
                    }
                    case USER: {
                        n2 = 8;
                        n4 = UserHandle.getUid(((BatterySipper)entry).userId, 0);
                        break;
                    }
                    case APP: {
                        continue block21;
                    }
                    case FLASHLIGHT: {
                        n2 = 6;
                        break;
                    }
                    case SCREEN: {
                        n2 = 7;
                        break;
                    }
                    case BLUETOOTH: {
                        n2 = 5;
                        break;
                    }
                    case WIFI: {
                        n2 = 4;
                        break;
                    }
                    case PHONE: {
                        n2 = 3;
                        break;
                    }
                    case CELL: {
                        n2 = 2;
                        break;
                    }
                    case IDLE: {
                        n2 = 1;
                        break;
                    }
                    case AMBIENT_DISPLAY: {
                        n2 = 13;
                    }
                }
                l5 = protoOutputStream.start(2246267895825L);
                protoOutputStream.write(1159641169921L, n2);
                protoOutputStream.write(1120986464258L, n4);
                protoOutputStream.write(1103806595075L, ((BatterySipper)entry).totalPowerMah);
                protoOutputStream.write(1133871366148L, ((BatterySipper)entry).shouldHide);
                protoOutputStream.write(1103806595077L, ((BatterySipper)entry).screenPowerMah);
                protoOutputStream.write(1103806595078L, ((BatterySipper)entry).proportionalSmearMah);
                protoOutputStream.end(l5);
            }
        }
        l3 = protoOutputStream.start(1146756268050L);
        protoOutputStream.write(0x10100000001L, ((BatteryStatsHelper)object4).getPowerProfile().getBatteryCapacity());
        protoOutputStream.write(1103806595074L, ((BatteryStatsHelper)object4).getComputedPower());
        protoOutputStream.write(1103806595075L, ((BatteryStatsHelper)object4).getMinDrainedPower());
        protoOutputStream.write(1103806595076L, ((BatteryStatsHelper)object4).getMaxDrainedPower());
        protoOutputStream.end(l3);
        Map<String, ? extends Timer> map = this.getRpmStats();
        Map<String, ? extends Timer> map2 = this.getScreenOffRpmStats();
        Iterator<Map.Entry<String, ? extends Timer>> iterator = map.entrySet().iterator();
        n2 = n3;
        while (iterator.hasNext()) {
            entry = iterator.next();
            l3 = protoOutputStream.start(2246267895827L);
            protoOutputStream.write(1138166333441L, entry.getKey());
            BatteryStats.dumpTimer(protoOutputStream, 1146756268034L, entry.getValue(), l4, 0);
            BatteryStats.dumpTimer(protoOutputStream, 1146756268035L, map2.get(entry.getKey()), l4, 0);
            protoOutputStream.end(l3);
        }
        for (n2 = 0; n2 < 5; ++n2) {
            l3 = protoOutputStream.start(2246267895828L);
            protoOutputStream.write(1159641169921L, n2);
            BatteryStats.dumpTimer(protoOutputStream, 1146756268034L, this.getScreenBrightnessTimer(n2), l4, 0);
            protoOutputStream.end(l3);
        }
        BatteryStats.dumpTimer(protoOutputStream, 1146756268053L, this.getPhoneSignalScanningTimer(), l4, 0);
        for (n2 = 0; n2 < 5; ++n2) {
            l3 = protoOutputStream.start(2246267895824L);
            protoOutputStream.write(1159641169921L, n2);
            BatteryStats.dumpTimer(protoOutputStream, 1146756268034L, this.getPhoneSignalStrengthTimer(n2), l4, 0);
            protoOutputStream.end(l3);
        }
        for (Map.Entry<String, ? extends Timer> entry3 : this.getWakeupReasonStats().entrySet()) {
            l3 = protoOutputStream.start(2246267895830L);
            protoOutputStream.write(1138166333441L, entry3.getKey());
            BatteryStats.dumpTimer(protoOutputStream, 1146756268034L, entry3.getValue(), l4, 0);
            protoOutputStream.end(l3);
        }
        for (n2 = 0; n2 < 5; ++n2) {
            l3 = protoOutputStream.start(2246267895832L);
            protoOutputStream.write(1159641169921L, n2);
            BatteryStats.dumpTimer(protoOutputStream, 1146756268034L, this.getWifiSignalStrengthTimer(n2), l4, 0);
            protoOutputStream.end(l3);
        }
        for (n2 = 0; n2 < 8; ++n2) {
            l3 = protoOutputStream.start(2246267895833L);
            protoOutputStream.write(1159641169921L, n2);
            BatteryStats.dumpTimer(protoOutputStream, 1146756268034L, this.getWifiStateTimer(n2), l4, 0);
            protoOutputStream.end(l3);
        }
        for (n2 = 0; n2 < 13; ++n2) {
            l3 = protoOutputStream.start(2246267895834L);
            protoOutputStream.write(1159641169921L, n2);
            BatteryStats.dumpTimer(protoOutputStream, 1146756268034L, this.getWifiSupplStateTimer(n2), l4, 0);
            protoOutputStream.end(l3);
        }
        protoOutputStream.end(l2);
    }

    private static boolean dumpTimeEstimate(PrintWriter printWriter, String charSequence, String string2, String string3, long l) {
        if (l < 0L) {
            return false;
        }
        printWriter.print((String)charSequence);
        printWriter.print(string2);
        printWriter.print(string3);
        charSequence = new StringBuilder(64);
        BatteryStats.formatTimeMs((StringBuilder)charSequence, l);
        printWriter.print(charSequence);
        printWriter.println();
        return true;
    }

    private static void dumpTimer(ProtoOutputStream protoOutputStream, long l, Timer timer, long l2, int n) {
        if (timer == null) {
            return;
        }
        long l3 = BatteryStats.roundUsToMs(timer.getTotalTimeLocked(l2, n));
        n = timer.getCountLocked(n);
        long l4 = timer.getMaxDurationMsLocked(l2 / 1000L);
        long l5 = timer.getCurrentDurationMsLocked(l2 / 1000L);
        l2 = timer.getTotalDurationMsLocked(l2 / 1000L);
        if (l3 != 0L || n != 0 || l4 != -1L || l5 != -1L || l2 != -1L) {
            l = protoOutputStream.start(l);
            protoOutputStream.write(1112396529665L, l3);
            protoOutputStream.write(1112396529666L, n);
            if (l4 != -1L) {
                protoOutputStream.write(1112396529667L, l4);
            }
            if (l5 != -1L) {
                protoOutputStream.write(1112396529668L, l5);
            }
            if (l2 != -1L) {
                protoOutputStream.write(1112396529669L, l2);
            }
            protoOutputStream.end(l);
        }
    }

    private static final void dumpTimer(PrintWriter printWriter, int n, String string2, String string3, Timer timer, long l, int n2) {
        if (timer != null) {
            l = BatteryStats.roundUsToMs(timer.getTotalTimeLocked(l, n2));
            n2 = timer.getCountLocked(n2);
            if (l != 0L || n2 != 0) {
                BatteryStats.dumpLine(printWriter, n, string2, string3, l, n2);
            }
        }
    }

    public static final void formatTimeMs(StringBuilder stringBuilder, long l) {
        long l2 = l / 1000L;
        BatteryStats.formatTimeRaw(stringBuilder, l2);
        stringBuilder.append(l - 1000L * l2);
        stringBuilder.append("ms ");
    }

    public static final void formatTimeMsNoSpace(StringBuilder stringBuilder, long l) {
        long l2 = l / 1000L;
        BatteryStats.formatTimeRaw(stringBuilder, l2);
        stringBuilder.append(l - 1000L * l2);
        stringBuilder.append("ms");
    }

    private static final void formatTimeRaw(StringBuilder stringBuilder, long l) {
        long l2;
        long l3 = l / 86400L;
        if (l3 != 0L) {
            stringBuilder.append(l3);
            stringBuilder.append("d ");
        }
        if ((l2 = (l - (l3 = l3 * 60L * 60L * 24L)) / 3600L) != 0L || l3 != 0L) {
            stringBuilder.append(l2);
            stringBuilder.append("h ");
        }
        l3 += l2 * 60L * 60L;
        l2 = (l - l3) / 60L;
        if (l2 != 0L || l3 != 0L) {
            stringBuilder.append(l2);
            stringBuilder.append("m ");
        }
        if (l != 0L || (l3 += 60L * l2) != 0L) {
            stringBuilder.append(l - l3);
            stringBuilder.append("s ");
        }
    }

    public static int mapToInternalProcessState(int n) {
        if (n == 21) {
            return 21;
        }
        if (n == 2) {
            return 0;
        }
        if (ActivityManager.isForegroundService(n)) {
            return 1;
        }
        if (n <= 7) {
            return 2;
        }
        if (n <= 12) {
            return 3;
        }
        if (n <= 13) {
            return 4;
        }
        if (n <= 14) {
            return 5;
        }
        return 6;
    }

    static void printBitDescriptions(StringBuilder stringBuilder, int n, int n2, HistoryTag historyTag, BitDescription[] object, boolean bl) {
        int n3 = n ^ n2;
        if (n3 == 0) {
            return;
        }
        int n4 = 0;
        for (n = 0; n < ((BitDescription[])object).length; ++n) {
            BitDescription bitDescription = object[n];
            int n5 = n4;
            if ((bitDescription.mask & n3) != 0) {
                String string2 = bl ? " " : ",";
                stringBuilder.append(string2);
                if (bitDescription.shift < 0) {
                    string2 = (bitDescription.mask & n2) != 0 ? "+" : "-";
                    stringBuilder.append(string2);
                    string2 = bl ? bitDescription.name : bitDescription.shortName;
                    stringBuilder.append(string2);
                    n5 = n4;
                    if (bitDescription.mask == 1073741824) {
                        n5 = n4;
                        if (historyTag != null) {
                            n5 = 1;
                            stringBuilder.append("=");
                            if (bl) {
                                UserHandle.formatUid(stringBuilder, historyTag.uid);
                                stringBuilder.append(":\"");
                                stringBuilder.append(historyTag.string);
                                stringBuilder.append("\"");
                            } else {
                                stringBuilder.append(historyTag.poolIdx);
                            }
                        }
                    }
                } else {
                    string2 = bl ? bitDescription.name : bitDescription.shortName;
                    stringBuilder.append(string2);
                    stringBuilder.append("=");
                    n5 = (bitDescription.mask & n2) >> bitDescription.shift;
                    if (bitDescription.values != null && n5 >= 0 && n5 < bitDescription.values.length) {
                        string2 = bl ? bitDescription.values[n5] : bitDescription.shortValues[n5];
                        stringBuilder.append(string2);
                        n5 = n4;
                    } else {
                        stringBuilder.append(n5);
                        n5 = n4;
                    }
                }
            }
            n4 = n5;
        }
        if (n4 == 0 && historyTag != null) {
            object = bl ? " wake_lock=" : ",w=";
            stringBuilder.append((String)object);
            if (bl) {
                UserHandle.formatUid(stringBuilder, historyTag.uid);
                stringBuilder.append(":\"");
                stringBuilder.append(historyTag.string);
                stringBuilder.append("\"");
            } else {
                stringBuilder.append(historyTag.poolIdx);
            }
        }
    }

    private final void printControllerActivity(PrintWriter printWriter, StringBuilder stringBuilder, String string2, String string3, ControllerActivityCounter controllerActivityCounter, int n) {
        block7 : {
            int n2;
            long l = controllerActivityCounter.getIdleTimeCounter().getCountLocked(n);
            long l2 = controllerActivityCounter.getRxTimeCounter().getCountLocked(n);
            long l3 = controllerActivityCounter.getPowerCounter().getCountLocked(n);
            long l4 = controllerActivityCounter.getMonitoredRailChargeConsumedMaMs().getCountLocked(n);
            long l5 = this.computeBatteryRealtime(SystemClock.elapsedRealtime() * 1000L, n) / 1000L;
            long l6 = 0L;
            Object[] arrobject = controllerActivityCounter.getTxTimeCounters();
            int n3 = arrobject.length;
            for (n2 = 0; n2 < n3; ++n2) {
                l6 += arrobject[n2].getCountLocked(n);
            }
            if (string3.equals(WIFI_CONTROLLER_NAME)) {
                long l7 = controllerActivityCounter.getScanTimeCounter().getCountLocked(n);
                stringBuilder.setLength(0);
                stringBuilder.append(string2);
                stringBuilder.append("     ");
                stringBuilder.append(string3);
                stringBuilder.append(" Scan time:  ");
                BatteryStats.formatTimeMs(stringBuilder, l7);
                stringBuilder.append("(");
                stringBuilder.append(this.formatRatioLocked(l7, l5));
                stringBuilder.append(")");
                printWriter.println(stringBuilder.toString());
                l6 = l5 - (l + l2 + l6);
                stringBuilder.setLength(0);
                stringBuilder.append(string2);
                stringBuilder.append("     ");
                stringBuilder.append(string3);
                stringBuilder.append(" Sleep time:  ");
                BatteryStats.formatTimeMs(stringBuilder, l6);
                stringBuilder.append("(");
                stringBuilder.append(this.formatRatioLocked(l6, l5));
                stringBuilder.append(")");
                printWriter.println(stringBuilder.toString());
            }
            if (string3.equals(CELLULAR_CONTROLLER_NAME)) {
                l6 = controllerActivityCounter.getSleepTimeCounter().getCountLocked(n);
                stringBuilder.setLength(0);
                stringBuilder.append(string2);
                stringBuilder.append("     ");
                stringBuilder.append(string3);
                stringBuilder.append(" Sleep time:  ");
                BatteryStats.formatTimeMs(stringBuilder, l6);
                stringBuilder.append("(");
                stringBuilder.append(this.formatRatioLocked(l6, l5));
                stringBuilder.append(")");
                printWriter.println(stringBuilder.toString());
            }
            n3 = n;
            stringBuilder.setLength(0);
            stringBuilder.append(string2);
            stringBuilder.append("     ");
            stringBuilder.append(string3);
            stringBuilder.append(" Idle time:   ");
            BatteryStats.formatTimeMs(stringBuilder, l);
            stringBuilder.append("(");
            stringBuilder.append(this.formatRatioLocked(l, l5));
            stringBuilder.append(")");
            printWriter.println(stringBuilder.toString());
            stringBuilder.setLength(0);
            stringBuilder.append(string2);
            stringBuilder.append("     ");
            stringBuilder.append(string3);
            stringBuilder.append(" Rx time:     ");
            BatteryStats.formatTimeMs(stringBuilder, l2);
            stringBuilder.append("(");
            stringBuilder.append(this.formatRatioLocked(l2, l5));
            stringBuilder.append(")");
            printWriter.println(stringBuilder.toString());
            stringBuilder.setLength(0);
            stringBuilder.append(string2);
            stringBuilder.append("     ");
            stringBuilder.append(string3);
            stringBuilder.append(" Tx time:     ");
            n = string3.hashCode() == -851952246 && string3.equals(CELLULAR_CONTROLLER_NAME) ? 0 : -1;
            arrobject = n != 0 ? new String[]{"[0]", "[1]", "[2]", "[3]", "[4]"} : new String[]{"   less than 0dBm: ", "   0dBm to 8dBm: ", "   8dBm to 15dBm: ", "   15dBm to 20dBm: ", "   above 20dBm: "};
            n2 = Math.min(controllerActivityCounter.getTxTimeCounters().length, arrobject.length);
            if (n2 > 1) {
                printWriter.println(stringBuilder.toString());
                for (n = 0; n < n2; ++n) {
                    l6 = controllerActivityCounter.getTxTimeCounters()[n].getCountLocked(n3);
                    stringBuilder.setLength(0);
                    stringBuilder.append(string2);
                    stringBuilder.append("    ");
                    stringBuilder.append((String)arrobject[n]);
                    stringBuilder.append(" ");
                    BatteryStats.formatTimeMs(stringBuilder, l6);
                    stringBuilder.append("(");
                    stringBuilder.append(this.formatRatioLocked(l6, l5));
                    stringBuilder.append(")");
                    printWriter.println(stringBuilder.toString());
                }
            } else {
                l6 = controllerActivityCounter.getTxTimeCounters()[0].getCountLocked(n3);
                BatteryStats.formatTimeMs(stringBuilder, l6);
                stringBuilder.append("(");
                stringBuilder.append(this.formatRatioLocked(l6, l5));
                stringBuilder.append(")");
                printWriter.println(stringBuilder.toString());
            }
            if (l3 > 0L) {
                stringBuilder.setLength(0);
                stringBuilder.append(string2);
                stringBuilder.append("     ");
                stringBuilder.append(string3);
                stringBuilder.append(" Battery drain: ");
                stringBuilder.append(BatteryStatsHelper.makemAh((double)l3 / 3600000.0));
                stringBuilder.append("mAh");
                printWriter.println(stringBuilder.toString());
            }
            if (l4 <= 0L) break block7;
            stringBuilder.setLength(0);
            stringBuilder.append(string2);
            stringBuilder.append("     ");
            stringBuilder.append(string3);
            stringBuilder.append(" Monitored rail energy drain: ");
            stringBuilder.append(new DecimalFormat("#.##").format((double)l4 / 3600000.0));
            stringBuilder.append(" mAh");
            printWriter.println(stringBuilder.toString());
        }
    }

    private final void printControllerActivityIfInteresting(PrintWriter printWriter, StringBuilder stringBuilder, String string2, String string3, ControllerActivityCounter controllerActivityCounter, int n) {
        if (BatteryStats.controllerActivityHasData(controllerActivityCounter, n)) {
            this.printControllerActivity(printWriter, stringBuilder, string2, string3, controllerActivityCounter, n);
        }
    }

    private void printSizeValue(PrintWriter printWriter, long l) {
        float f = l;
        String string2 = "";
        float f2 = f;
        if (f >= 10240.0f) {
            string2 = "KB";
            f2 = f / 1024.0f;
        }
        f = f2;
        if (f2 >= 10240.0f) {
            string2 = "MB";
            f = f2 / 1024.0f;
        }
        f2 = f;
        if (f >= 10240.0f) {
            string2 = "GB";
            f2 = f / 1024.0f;
        }
        f = f2;
        if (f2 >= 10240.0f) {
            string2 = "TB";
            f = f2 / 1024.0f;
        }
        f2 = f;
        if (f >= 10240.0f) {
            string2 = "PB";
            f2 = f / 1024.0f;
        }
        printWriter.print((int)f2);
        printWriter.print(string2);
    }

    private static final boolean printTimer(PrintWriter printWriter, StringBuilder stringBuilder, Timer timer, long l, int n, String string2, String string3) {
        if (timer != null) {
            long l2 = (timer.getTotalTimeLocked(l, n) + 500L) / 1000L;
            n = timer.getCountLocked(n);
            if (l2 != 0L) {
                stringBuilder.setLength(0);
                stringBuilder.append(string2);
                stringBuilder.append("    ");
                stringBuilder.append(string3);
                stringBuilder.append(": ");
                BatteryStats.formatTimeMs(stringBuilder, l2);
                stringBuilder.append("realtime (");
                stringBuilder.append(n);
                stringBuilder.append(" times)");
                l2 = timer.getMaxDurationMsLocked(l / 1000L);
                if (l2 >= 0L) {
                    stringBuilder.append(" max=");
                    stringBuilder.append(l2);
                }
                if (timer.isRunningLocked()) {
                    if ((l = timer.getCurrentDurationMsLocked(l / 1000L)) >= 0L) {
                        stringBuilder.append(" (running for ");
                        stringBuilder.append(l);
                        stringBuilder.append("ms)");
                    } else {
                        stringBuilder.append(" (running)");
                    }
                }
                printWriter.println(stringBuilder.toString());
                return true;
            }
        }
        return false;
    }

    private static final String printWakeLock(StringBuilder stringBuilder, Timer timer, long l, String string2, int n, String string3) {
        if (timer != null) {
            long l2 = BatteryStats.computeWakeLock(timer, l, n);
            n = timer.getCountLocked(n);
            if (l2 != 0L) {
                stringBuilder.append(string3);
                BatteryStats.formatTimeMs(stringBuilder, l2);
                if (string2 != null) {
                    stringBuilder.append(string2);
                    stringBuilder.append(' ');
                }
                stringBuilder.append('(');
                stringBuilder.append(n);
                stringBuilder.append(" times)");
                long l3 = timer.getMaxDurationMsLocked(l / 1000L);
                if (l3 >= 0L) {
                    stringBuilder.append(" max=");
                    stringBuilder.append(l3);
                }
                if ((l3 = timer.getTotalDurationMsLocked(l / 1000L)) > l2) {
                    stringBuilder.append(" actual=");
                    stringBuilder.append(l3);
                }
                if (timer.isRunningLocked()) {
                    if ((l = timer.getCurrentDurationMsLocked(l / 1000L)) >= 0L) {
                        stringBuilder.append(" (running for ");
                        stringBuilder.append(l);
                        stringBuilder.append("ms)");
                    } else {
                        stringBuilder.append(" (running)");
                    }
                }
                return ", ";
            }
        }
        return string3;
    }

    private static final String printWakeLockCheckin(StringBuilder stringBuilder, Timer object, long l, String string2, int n, String string3) {
        long l2;
        int n2 = 0;
        long l3 = 0L;
        long l4 = 0L;
        long l5 = 0L;
        if (object != null) {
            l2 = ((Timer)object).getTotalTimeLocked(l, n);
            n = ((Timer)object).getCountLocked(n);
            l5 = ((Timer)object).getCurrentDurationMsLocked(l / 1000L);
            l3 = ((Timer)object).getMaxDurationMsLocked(l / 1000L);
            l4 = ((Timer)object).getTotalDurationMsLocked(l / 1000L);
            l = l5;
            l5 = l4;
        } else {
            l2 = 0L;
            l = l4;
            n = n2;
        }
        stringBuilder.append(string3);
        stringBuilder.append((l2 + 500L) / 1000L);
        stringBuilder.append(',');
        if (string2 != null) {
            object = new StringBuilder();
            ((StringBuilder)object).append(string2);
            ((StringBuilder)object).append(",");
            object = ((StringBuilder)object).toString();
        } else {
            object = "";
        }
        stringBuilder.append((String)object);
        stringBuilder.append(n);
        stringBuilder.append(',');
        stringBuilder.append(l);
        stringBuilder.append(',');
        stringBuilder.append(l3);
        if (string2 != null) {
            stringBuilder.append(',');
            stringBuilder.append(l5);
        }
        return ",";
    }

    private void printmAh(PrintWriter printWriter, double d) {
        printWriter.print(BatteryStatsHelper.makemAh(d));
    }

    private void printmAh(StringBuilder stringBuilder, double d) {
        stringBuilder.append(BatteryStatsHelper.makemAh(d));
    }

    private static long roundUsToMs(long l) {
        return (500L + l) / 1000L;
    }

    public abstract void commitCurrentHistoryBatchLocked();

    @UnsupportedAppUsage
    public abstract long computeBatteryRealtime(long var1, int var3);

    public abstract long computeBatteryScreenOffRealtime(long var1, int var3);

    public abstract long computeBatteryScreenOffUptime(long var1, int var3);

    @UnsupportedAppUsage
    public abstract long computeBatteryTimeRemaining(long var1);

    @UnsupportedAppUsage
    public abstract long computeBatteryUptime(long var1, int var3);

    @UnsupportedAppUsage
    public abstract long computeChargeTimeRemaining(long var1);

    public abstract long computeRealtime(long var1, int var3);

    public abstract long computeUptime(long var1, int var3);

    public final void dumpCheckinLocked(Context context, PrintWriter printWriter, int n, int n2) {
        this.dumpCheckinLocked(context, printWriter, n, n2, BatteryStatsHelper.checkWifiOnly(context));
    }

    public final void dumpCheckinLocked(Context object, PrintWriter printWriter, int n, int n2, boolean bl) {
        Object object2;
        Object object3;
        Object object6;
        int n4;
        int n3;
        Object object7 = 0;
        if (n != 0) {
            object = STAT_NAMES[n];
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("ERROR: BatteryStats.dumpCheckin called for which type ");
            stringBuilder.append(n);
            stringBuilder.append(" but only STATS_SINCE_CHARGED is supported.");
            BatteryStats.dumpLine(printWriter, 0, (String)object, "err", stringBuilder.toString());
            return;
        }
        long l = SystemClock.uptimeMillis() * 1000L;
        long l2 = SystemClock.elapsedRealtime();
        long l3 = l2 * 1000L;
        long l4 = this.getBatteryUptime(l);
        long l5 = this.computeBatteryUptime(l, n);
        long l6 = this.computeBatteryRealtime(l3, n);
        long l7 = this.computeBatteryScreenOffUptime(l, n);
        long l8 = this.computeBatteryScreenOffRealtime(l3, n);
        long l9 = this.computeRealtime(l3, n);
        long l10 = this.computeUptime(l, n);
        long l11 = this.getScreenOnTime(l3, n);
        long l12 = this.getScreenDozeTime(l3, n);
        long l13 = this.getInteractiveTime(l3, n);
        long l14 = this.getPowerSaveModeEnabledTime(l3, n);
        long l15 = this.getDeviceIdleModeTime(1, l3, n);
        long l16 = this.getDeviceIdleModeTime(2, l3, n);
        long l17 = this.getDeviceIdlingTime(1, l3, n);
        long l18 = this.getDeviceIdlingTime(2, l3, n);
        int n5 = this.getNumConnectivityChange(n);
        long l19 = this.getPhoneOnTime(l3, n);
        long l20 = this.getUahDischarge(n);
        long l21 = this.getUahDischargeScreenOff(n);
        long l22 = this.getUahDischargeScreenDoze(n);
        long l23 = this.getUahDischargeLightDoze(n);
        l = this.getUahDischargeDeepDoze(n);
        Object[] arrobject = new StringBuilder(128);
        Object object8 = this.getUidStats();
        int n6 = ((SparseArray)object8).size();
        String string2 = STAT_NAMES[n];
        Object object9 = n == 0 ? Integer.valueOf(this.getStartCount()) : "N/A";
        BatteryStats.dumpLine(printWriter, 0, string2, "bt", object9, l6 / 1000L, l5 / 1000L, l9 / 1000L, l10 / 1000L, this.getStartClockTime(), l8 / 1000L, l7 / 1000L, this.getEstimatedBatteryCapacity(), this.getMinLearnedBatteryCapacity(), this.getMaxLearnedBatteryCapacity(), l12 / 1000L);
        l7 = 0L;
        l9 = 0L;
        object9 = object8;
        for (n4 = 0; n4 < n6; ++n4) {
            object8 = ((SparseArray)object9).valueAt(n4);
            object2 = ((Uid)object8).getWakelockStats();
            for (n3 = object2.size() - 1; n3 >= 0; --n3) {
                object6 = ((ArrayMap)object2).valueAt(n3);
                Timer object52 = ((Uid.Wakelock)object6).getWakeTime(1);
                l5 = l9;
                if (object52 != null) {
                    l5 = l9 + object52.getTotalTimeLocked(l3, n);
                }
                object6 = ((Uid.Wakelock)object6).getWakeTime(0);
                l6 = l7;
                if (object6 != null) {
                    l6 = l7 + ((Timer)object6).getTotalTimeLocked(l3, n);
                }
                l9 = l5;
                l7 = l6;
            }
        }
        n4 = n6;
        BatteryStats.dumpLine(printWriter, 0, string2, "gn", this.getNetworkActivityBytes(0, n), this.getNetworkActivityBytes(1, n), this.getNetworkActivityBytes(2, n), this.getNetworkActivityBytes(3, n), this.getNetworkActivityPackets(0, n), this.getNetworkActivityPackets(1, n), this.getNetworkActivityPackets(2, n), this.getNetworkActivityPackets(3, n), this.getNetworkActivityBytes(4, n), this.getNetworkActivityBytes(5, n));
        object8 = this.getModemControllerActivity();
        l6 = l4;
        l5 = l3;
        BatteryStats.dumpControllerActivityLine(printWriter, 0, string2, "gmcd", (ControllerActivityCounter)object8, n);
        l3 = this.getWifiOnTime(l5, n);
        l4 = this.getGlobalWifiRunningTime(l5, n);
        BatteryStats.dumpLine(printWriter, 0, string2, "gwfl", l3 / 1000L, l4 / 1000L, object7, object7, object7);
        BatteryStats.dumpControllerActivityLine(printWriter, 0, string2, "gwfcd", this.getWifiControllerActivity(), n);
        BatteryStats.dumpControllerActivityLine(printWriter, 0, string2, "gble", this.getBluetoothControllerActivity(), n);
        BatteryStats.dumpLine(printWriter, 0, string2, "m", l11 / 1000L, l19 / 1000L, l9 / 1000L, l7 / 1000L, this.getMobileRadioActiveTime(l5, n) / 1000L, this.getMobileRadioActiveAdjustedTime(n) / 1000L, l13 / 1000L, l14 / 1000L, n5, l16 / 1000L, this.getDeviceIdleModeCount(2, n), l18 / 1000L, this.getDeviceIdlingCount(2, n), this.getMobileRadioActiveCount(n), this.getMobileRadioActiveUnknownTime(n) / 1000L, l15 / 1000L, this.getDeviceIdleModeCount(1, n), l17 / 1000L, this.getDeviceIdlingCount(1, n), this.getLongestDeviceIdleModeTime(1), this.getLongestDeviceIdleModeTime(2));
        object8 = new Object[5];
        for (n6 = 0; n6 < 5; ++n6) {
            object8[n6] = this.getScreenBrightnessTime(n6, l5, n) / 1000L;
        }
        BatteryStats.dumpLine(printWriter, 0, string2, "br", (Object[])object8);
        object8 = new Object[5];
        for (n6 = 0; n6 < 5; ++n6) {
            object8[n6] = this.getPhoneSignalStrengthTime(n6, l5, n) / 1000L;
        }
        BatteryStats.dumpLine(printWriter, 0, string2, "sgt", (Object[])object8);
        BatteryStats.dumpLine(printWriter, 0, string2, "sst", this.getPhoneSignalScanningTime(l5, n) / 1000L);
        for (n6 = 0; n6 < 5; ++n6) {
            object8[n6] = this.getPhoneSignalStrengthCount(n6, n);
        }
        BatteryStats.dumpLine(printWriter, 0, string2, "sgc", (Object[])object8);
        object8 = new Object[22];
        for (n6 = 0; n6 < 22; ++n6) {
            object8[n6] = this.getPhoneDataConnectionTime(n6, l5, n) / 1000L;
        }
        BatteryStats.dumpLine(printWriter, 0, string2, "dct", (Object[])object8);
        for (n6 = 0; n6 < 22; ++n6) {
            object8[n6] = this.getPhoneDataConnectionCount(n6, n);
        }
        BatteryStats.dumpLine(printWriter, 0, string2, "dcc", (Object[])object8);
        object8 = new Object[8];
        for (n6 = 0; n6 < 8; ++n6) {
            object8[n6] = this.getWifiStateTime(n6, l5, n) / 1000L;
        }
        BatteryStats.dumpLine(printWriter, 0, string2, "wst", (Object[])object8);
        for (n6 = 0; n6 < 8; ++n6) {
            object8[n6] = this.getWifiStateCount(n6, n);
        }
        BatteryStats.dumpLine(printWriter, 0, string2, "wsc", (Object[])object8);
        object8 = new Object[13];
        for (n6 = 0; n6 < 13; ++n6) {
            object8[n6] = this.getWifiSupplStateTime(n6, l5, n) / 1000L;
        }
        BatteryStats.dumpLine(printWriter, 0, string2, "wsst", (Object[])object8);
        for (n6 = 0; n6 < 13; ++n6) {
            object8[n6] = this.getWifiSupplStateCount(n6, n);
        }
        BatteryStats.dumpLine(printWriter, 0, string2, "wssc", (Object[])object8);
        object2 = new Object[5];
        for (n6 = 0; n6 < 5; ++n6) {
            object2[n6] = this.getWifiSignalStrengthTime(n6, l5, n) / 1000L;
        }
        BatteryStats.dumpLine(printWriter, 0, string2, "wsgt", (Object[])object2);
        for (n6 = 0; n6 < 5; ++n6) {
            object2[n6] = this.getWifiSignalStrengthCount(n6, n);
        }
        BatteryStats.dumpLine(printWriter, 0, string2, "wsgc", (Object[])object2);
        l9 = this.getWifiMulticastWakelockTime(l5, n);
        n6 = this.getWifiMulticastWakelockCount(n);
        BatteryStats.dumpLine(printWriter, 0, string2, "wmct", l9 / 1000L, n6);
        int n7 = this.getLowDischargeAmountSinceCharge();
        n3 = this.getHighDischargeAmountSinceCharge();
        n5 = this.getDischargeAmountScreenOnSinceCharge();
        n6 = 2;
        BatteryStats.dumpLine(printWriter, 0, string2, "dc", n7, n3, n5, this.getDischargeAmountScreenOffSinceCharge(), l20 / 1000L, l21 / 1000L, this.getDischargeAmountScreenDozeSinceCharge(), l22 / 1000L, l23 / 1000L, l / 1000L);
        object6 = "\"";
        if (n2 < 0) {
            object8 = this.getKernelWakelockStats();
            if (object8.size() > 0) {
                Iterator<Map.Entry<K, V>> iterator = object8.entrySet().iterator();
                object8 = object7;
                object7 = object6;
                while (iterator.hasNext()) {
                    Map.Entry<K, V> entry = iterator.next();
                    arrobject.setLength(0);
                    BatteryStats.printWakeLockCheckin((StringBuilder)arrobject, (Timer)entry.getValue(), l5, null, n, "");
                    object3 = new Object[n6];
                    object6 = new StringBuilder();
                    ((StringBuilder)object6).append((String)object7);
                    ((StringBuilder)object6).append((String)entry.getKey());
                    ((StringBuilder)object6).append((String)object7);
                    object3[0] = ((StringBuilder)object6).toString();
                    object3[1] = arrobject.toString();
                    BatteryStats.dumpLine(printWriter, 0, string2, "kwl", (Object[])object3);
                }
                object2 = object8;
                object8 = object7;
                l9 = l5;
                object7 = object2;
            } else {
                object8 = "\"";
                l9 = l5;
            }
            object2 = this.getWakeupReasonStats();
            if (object2.size() > 0) {
                for (Map.Entry<K, V> entry : object2.entrySet()) {
                    l5 = ((Timer)entry.getValue()).getTotalTimeLocked(l9, n);
                    n6 = ((Timer)entry.getValue()).getCountLocked(n);
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append((String)object8);
                    stringBuilder.append((String)entry.getKey());
                    stringBuilder.append((String)object8);
                    BatteryStats.dumpLine(printWriter, 0, string2, "wr", stringBuilder.toString(), (l5 + 500L) / 1000L, n6);
                }
                object6 = object7;
            } else {
                object6 = object7;
            }
        } else {
            object8 = "\"";
            object6 = object7;
            l9 = l5;
        }
        object3 = this.getRpmStats();
        object7 = this.getScreenOffRpmStats();
        if (object3.size() > 0) {
            for (Map.Entry entry : object3.entrySet()) {
                arrobject.setLength(0);
                Timer timer = (Timer)entry.getValue();
                l5 = (timer.getTotalTimeLocked(l9, n) + 500L) / 1000L;
                n6 = timer.getCountLocked(n);
                Timer timer2 = (Timer)object7.get(entry.getKey());
                if (timer2 != null) {
                    l7 = (timer2.getTotalTimeLocked(l9, n) + 500L) / 1000L;
                }
                if (timer2 != null) {
                    timer2.getCountLocked(n);
                }
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append((String)object8);
                stringBuilder.append((String)entry.getKey());
                stringBuilder.append((String)object8);
                BatteryStats.dumpLine(printWriter, 0, string2, "rpm", stringBuilder.toString(), l5, n6);
            }
        }
        BatteryStatsHelper batteryStatsHelper = new BatteryStatsHelper((Context)object, false, bl);
        batteryStatsHelper.create(this);
        batteryStatsHelper.refreshStats(n, -1);
        object2 = batteryStatsHelper.getUsageList();
        if (object2 != null && object2.size() > 0) {
            BatteryStats.dumpLine(printWriter, 0, string2, "pws", BatteryStatsHelper.makemAh(batteryStatsHelper.getPowerProfile().getBatteryCapacity()), BatteryStatsHelper.makemAh(batteryStatsHelper.getComputedPower()), BatteryStatsHelper.makemAh(batteryStatsHelper.getMinDrainedPower()), BatteryStatsHelper.makemAh(batteryStatsHelper.getMaxDrainedPower()));
            n6 = 0;
            for (n3 = 0; n3 < object2.size(); ++n3) {
                BatterySipper batterySipper = (BatterySipper)object2.get(n3);
                switch (batterySipper.drainType) {
                    default: {
                        object = "???";
                        break;
                    }
                    case MEMORY: {
                        object = "memory";
                        break;
                    }
                    case CAMERA: {
                        object = "camera";
                        break;
                    }
                    case OVERCOUNTED: {
                        object = "over";
                        break;
                    }
                    case UNACCOUNTED: {
                        object = "unacc";
                        break;
                    }
                    case USER: {
                        n6 = UserHandle.getUid(batterySipper.userId, 0);
                        object = "user";
                        break;
                    }
                    case APP: {
                        n6 = batterySipper.uidObj.getUid();
                        object = "uid";
                        break;
                    }
                    case FLASHLIGHT: {
                        object = "flashlight";
                        break;
                    }
                    case SCREEN: {
                        object = "scrn";
                        break;
                    }
                    case BLUETOOTH: {
                        object = "blue";
                        break;
                    }
                    case WIFI: {
                        object = "wifi";
                        break;
                    }
                    case PHONE: {
                        object = "phone";
                        break;
                    }
                    case CELL: {
                        object = "cell";
                        break;
                    }
                    case IDLE: {
                        object = "idle";
                        break;
                    }
                    case AMBIENT_DISPLAY: {
                        object = "ambi";
                    }
                }
                BatteryStats.dumpLine(printWriter, n6, string2, "pwi", object, BatteryStatsHelper.makemAh(batterySipper.totalPowerMah), (int)batterySipper.shouldHide, BatteryStatsHelper.makemAh(batterySipper.screenPowerMah), BatteryStatsHelper.makemAh(batterySipper.proportionalSmearMah));
            }
            object = object2;
        } else {
            object = object2;
        }
        Object object4 = this.getCpuFreqs();
        if (object4 != null) {
            arrobject.setLength(0);
            for (n6 = 0; n6 < ((long[])object4).length; ++n6) {
                StringBuilder stringBuilder = new StringBuilder();
                object2 = n6 == 0 ? "" : ",";
                stringBuilder.append((String)object2);
                stringBuilder.append(object4[n6]);
                arrobject.append(stringBuilder.toString());
            }
            BatteryStats.dumpLine(printWriter, 0, string2, "gcf", arrobject.toString());
        }
        SparseArray<? extends Uid> sparseArray = object9;
        Object object5 = object;
        l5 = l2;
        object9 = arrobject;
        arrobject = object3;
        object = object4;
        for (n6 = 0; n6 < n4; ++n6) {
            int n8 = sparseArray.keyAt(n6);
            if (n2 >= 0 && n8 != n2) {
                object2 = object;
                object = object8;
                l2 = l9;
                l9 = l6;
                l6 = l5;
                object8 = object9;
                object9 = object7;
                object7 = object2;
                l5 = l2;
            } else {
                int n9;
                int n10;
                Object object10;
                Object object11 = sparseArray.valueAt(n6);
                l23 = ((Uid)object11).getNetworkActivityBytes(0, n);
                l19 = ((Uid)object11).getNetworkActivityBytes(1, n);
                long l24 = ((Uid)object11).getNetworkActivityBytes(2, n);
                l7 = ((Uid)object11).getNetworkActivityBytes(3, n);
                l3 = ((Uid)object11).getNetworkActivityPackets(0, n);
                l4 = ((Uid)object11).getNetworkActivityPackets(1, n);
                l11 = ((Uid)object11).getMobileRadioActiveTime(n);
                n3 = ((Uid)object11).getMobileRadioActiveCount(n);
                l8 = ((Uid)object11).getMobileRadioApWakeupCount(n);
                l2 = ((Uid)object11).getNetworkActivityPackets(2, n);
                l10 = ((Uid)object11).getNetworkActivityPackets(3, n);
                l = ((Uid)object11).getWifiRadioApWakeupCount(n);
                l21 = ((Uid)object11).getNetworkActivityBytes(4, n);
                l22 = ((Uid)object11).getNetworkActivityBytes(5, n);
                l18 = ((Uid)object11).getNetworkActivityBytes(6, n);
                l13 = ((Uid)object11).getNetworkActivityBytes(7, n);
                l14 = ((Uid)object11).getNetworkActivityBytes(8, n);
                l15 = ((Uid)object11).getNetworkActivityBytes(9, n);
                l17 = ((Uid)object11).getNetworkActivityPackets(6, n);
                l12 = ((Uid)object11).getNetworkActivityPackets(7, n);
                l16 = ((Uid)object11).getNetworkActivityPackets(8, n);
                l20 = ((Uid)object11).getNetworkActivityPackets(9, n);
                if (l23 > 0L || l19 > 0L || l24 > 0L || l7 > 0L || l3 > 0L || l4 > 0L || l2 > 0L || l10 > 0L || l11 > 0L || n3 > 0 || l21 > 0L || l22 > 0L || l8 > 0L || l > 0L || l18 > 0L || l13 > 0L || l14 > 0L || l15 > 0L || l17 > 0L || l12 > 0L || l16 > 0L || l20 > 0L) {
                    BatteryStats.dumpLine(printWriter, n8, string2, "nt", l23, l19, l24, l7, l3, l4, l2, l10, l11, n3, l21, l22, l8, l, l18, l13, l14, l15, l17, l12, l16, l20);
                }
                BatteryStats.dumpControllerActivityLine(printWriter, n8, string2, "mcd", ((Uid)object11).getModemControllerActivity(), n);
                l3 = ((Uid)object11).getFullWifiLockTime(l9, n);
                l2 = ((Uid)object11).getWifiScanTime(l9, n);
                n5 = ((Uid)object11).getWifiScanCount(n);
                n3 = ((Uid)object11).getWifiScanBackgroundCount(n);
                l = (((Uid)object11).getWifiScanActualTime(l9) + 500L) / 1000L;
                l4 = (((Uid)object11).getWifiScanBackgroundTime(l9) + 500L) / 1000L;
                l7 = ((Uid)object11).getWifiRunningTime(l9, n);
                if (l3 != 0L || l2 != 0L || n5 != 0 || n3 != 0 || l != 0L || l4 != 0L || l7 != 0L) {
                    BatteryStats.dumpLine(printWriter, n8, string2, "wfl", l3, l2, l7, n5, object6, object6, object6, n3, l, l4);
                }
                BatteryStats.dumpControllerActivityLine(printWriter, n8, string2, "wfcd", ((Uid)object11).getWifiControllerActivity(), n);
                object4 = ((Uid)object11).getBluetoothScanTimer();
                if (object4 != null && (l20 = (((Timer)object4).getTotalTimeLocked(l9, n) + 500L) / 1000L) != 0L) {
                    n10 = ((Timer)object4).getCountLocked(n);
                    object2 = ((Uid)object11).getBluetoothScanBackgroundTimer();
                    n3 = object2 != null ? ((Timer)object2).getCountLocked(n) : 0;
                    l = l5;
                    l22 = ((Timer)object4).getTotalDurationMsLocked(l);
                    l2 = object2 != null ? ((Timer)object2).getTotalDurationMsLocked(l) : 0L;
                    n5 = ((Uid)object11).getBluetoothScanResultCounter() != null ? ((Uid)object11).getBluetoothScanResultCounter().getCountLocked(n) : 0;
                    n7 = ((Uid)object11).getBluetoothScanResultBgCounter() != null ? ((Uid)object11).getBluetoothScanResultBgCounter().getCountLocked(n) : 0;
                    object2 = ((Uid)object11).getBluetoothUnoptimizedScanTimer();
                    l7 = object2 != null ? ((Timer)object2).getTotalDurationMsLocked(l) : 0L;
                    l3 = object2 != null ? ((Timer)object2).getMaxDurationMsLocked(l) : 0L;
                    object2 = ((Uid)object11).getBluetoothUnoptimizedScanBackgroundTimer();
                    l4 = object2 != null ? ((Timer)object2).getTotalDurationMsLocked(l) : 0L;
                    l = object2 != null ? ((Timer)object2).getMaxDurationMsLocked(l) : 0L;
                    BatteryStats.dumpLine(printWriter, n8, string2, "blem", l20, n10, n3, l22, l2, n5, n7, l7, l4, l3, l);
                }
                object3 = object8;
                object8 = ((Uid)object11).getBluetoothControllerActivity();
                BatteryStats.dumpControllerActivityLine(printWriter, n8, string2, "ble", (ControllerActivityCounter)object8, n);
                if (((Uid)object11).hasUserActivity()) {
                    object8 = new Object[Uid.NUM_USER_ACTIVITY_TYPES];
                    n5 = 0;
                    for (n3 = 0; n3 < Uid.NUM_USER_ACTIVITY_TYPES; ++n3) {
                        n7 = ((Uid)object11).getUserActivityCount(n3, n);
                        object8[n3] = n7;
                        if (n7 == 0) continue;
                        n5 = 1;
                    }
                    if (n5 != 0) {
                        BatteryStats.dumpLine(printWriter, n8, string2, "ua", (Object[])object8);
                    }
                }
                if (((Uid)object11).getAggregatedPartialWakelockTimer() != null) {
                    object8 = ((Uid)object11).getAggregatedPartialWakelockTimer();
                    l7 = ((Timer)object8).getTotalDurationMsLocked(l5);
                    l2 = (object8 = ((Timer)object8).getSubTimer()) != null ? ((Timer)object8).getTotalDurationMsLocked(l5) : 0L;
                    BatteryStats.dumpLine(printWriter, n8, string2, "awl", l7, l2);
                }
                Object object12 = ((Uid)object11).getWakelockStats();
                for (n3 = object12.size() - 1; n3 >= 0; --n3) {
                    object2 = ((ArrayMap)object12).valueAt(n3);
                    ((StringBuilder)object9).setLength(0);
                    object10 = BatteryStats.printWakeLockCheckin((StringBuilder)object9, ((Uid.Wakelock)object2).getWakeTime(1), l9, "f", n, "");
                    object8 = ((Uid.Wakelock)object2).getWakeTime(0);
                    object10 = BatteryStats.printWakeLockCheckin((StringBuilder)object9, (Timer)object8, l9, "p", n, (String)object10);
                    object8 = object8 != null ? ((Timer)object8).getSubTimer() : null;
                    object8 = BatteryStats.printWakeLockCheckin((StringBuilder)object9, (Timer)object8, l9, "bp", n, (String)object10);
                    BatteryStats.printWakeLockCheckin((StringBuilder)object9, ((Uid.Wakelock)object2).getWakeTime(2), l9, "w", n, (String)object8);
                    if (((StringBuilder)object9).length() <= 0) continue;
                    object2 = object8 = (String)((ArrayMap)object12).keyAt(n3);
                    if (((String)object8).indexOf(44) >= 0) {
                        object2 = ((String)object8).replace(',', '_');
                    }
                    object8 = object2;
                    if (((String)object2).indexOf(10) >= 0) {
                        object8 = ((String)object2).replace('\n', '_');
                    }
                    object2 = object8;
                    if (((String)object8).indexOf(13) >= 0) {
                        object2 = ((String)object8).replace('\r', '_');
                    }
                    BatteryStats.dumpLine(printWriter, n8, string2, "wl", object2, ((StringBuilder)object9).toString());
                }
                object8 = object7;
                object10 = ((Uid)object11).getMulticastWakelockStats();
                if (object10 != null) {
                    l2 = ((Timer)object10).getTotalTimeLocked(l9, n) / 1000L;
                    n3 = ((Timer)object10).getCountLocked(n);
                    if (l2 > 0L) {
                        BatteryStats.dumpLine(printWriter, n8, string2, "wmc", l2, n3);
                    }
                }
                object4 = ((Uid)object11).getSyncStats();
                object7 = object3;
                object2 = object12;
                object3 = object10;
                for (n3 = object4.size() - 1; n3 >= 0; --n3) {
                    object12 = (Timer)((ArrayMap)object4).valueAt(n3);
                    l7 = (((Timer)object12).getTotalTimeLocked(l9, n) + 500L) / 1000L;
                    n7 = ((Timer)object12).getCountLocked(n);
                    l2 = (object12 = ((Timer)object12).getSubTimer()) != null ? ((Timer)object12).getTotalDurationMsLocked(l5) : -1L;
                    n5 = object12 != null ? ((Timer)object12).getCountLocked(n) : -1;
                    if (l7 == 0L) continue;
                    object10 = new StringBuilder();
                    object12 = object7;
                    ((StringBuilder)object10).append((String)object12);
                    ((StringBuilder)object10).append((String)((ArrayMap)object4).keyAt(n3));
                    ((StringBuilder)object10).append((String)object12);
                    BatteryStats.dumpLine(printWriter, n8, string2, "sy", ((StringBuilder)object10).toString(), l7, n7, l2, n5);
                }
                object2 = ((Uid)object11).getJobStats();
                for (n3 = object2.size() - 1; n3 >= 0; --n3) {
                    object3 = (Timer)((ArrayMap)object2).valueAt(n3);
                    l7 = (((Timer)object3).getTotalTimeLocked(l9, n) + 500L) / 1000L;
                    n7 = ((Timer)object3).getCountLocked(n);
                    l2 = (object3 = ((Timer)object3).getSubTimer()) != null ? ((Timer)object3).getTotalDurationMsLocked(l5) : -1L;
                    n5 = object3 != null ? ((Timer)object3).getCountLocked(n) : -1;
                    if (l7 == 0L) continue;
                    object3 = new StringBuilder();
                    ((StringBuilder)object3).append((String)object7);
                    ((StringBuilder)object3).append((String)((ArrayMap)object2).keyAt(n3));
                    ((StringBuilder)object3).append((String)object7);
                    BatteryStats.dumpLine(printWriter, n8, string2, "jb", ((StringBuilder)object3).toString(), l7, n7, l2, n5);
                }
                object4 = ((Uid)object11).getJobCompletionStats();
                for (n3 = object4.size() - 1; n3 >= 0; --n3) {
                    object2 = (SparseIntArray)((ArrayMap)object4).valueAt(n3);
                    if (object2 == null) continue;
                    object3 = new StringBuilder();
                    ((StringBuilder)object3).append((String)object7);
                    ((StringBuilder)object3).append((String)((ArrayMap)object4).keyAt(n3));
                    ((StringBuilder)object3).append((String)object7);
                    BatteryStats.dumpLine(printWriter, n8, string2, "jbc", ((StringBuilder)object3).toString(), ((SparseIntArray)object2).get(0, 0), ((SparseIntArray)object2).get(1, 0), ((SparseIntArray)object2).get(2, 0), ((SparseIntArray)object2).get(3, 0), ((SparseIntArray)object2).get(4, 0));
                }
                ((Uid)object11).getDeferredJobsCheckinLineLocked((StringBuilder)object9, n);
                if (((StringBuilder)object9).length() > 0) {
                    BatteryStats.dumpLine(printWriter, n8, string2, "jbd", ((StringBuilder)object9).toString());
                }
                object3 = ((Uid)object11).getFlashlightTurnedOnTimer();
                l2 = l5;
                object2 = object7;
                BatteryStats.dumpTimer(printWriter, n8, string2, "fla", (Timer)object3, l9, n);
                BatteryStats.dumpTimer(printWriter, n8, string2, "cam", ((Uid)object11).getCameraTurnedOnTimer(), l9, n);
                BatteryStats.dumpTimer(printWriter, n8, string2, "vid", ((Uid)object11).getVideoTurnedOnTimer(), l9, n);
                BatteryStats.dumpTimer(printWriter, n8, string2, "aud", ((Uid)object11).getAudioTurnedOnTimer(), l9, n);
                object7 = ((Uid)object11).getSensorStats();
                n3 = ((SparseArray)object7).size();
                l5 = l9;
                for (n5 = 0; n5 < n3; ++n5) {
                    object4 = (Uid.Sensor)((SparseArray)object7).valueAt(n5);
                    n10 = ((SparseArray)object7).keyAt(n5);
                    object3 = ((Uid.Sensor)object4).getSensorTime();
                    if (object3 == null || (l7 = (((Timer)object3).getTotalTimeLocked(l5, n) + 500L) / 1000L) == 0L) continue;
                    n9 = ((Timer)object3).getCountLocked(n);
                    n7 = (object4 = ((Uid.Sensor)object4).getSensorBackgroundTime()) != null ? ((Timer)object4).getCountLocked(n) : 0;
                    l3 = ((Timer)object3).getTotalDurationMsLocked(l2);
                    l9 = object4 != null ? ((Timer)object4).getTotalDurationMsLocked(l2) : 0L;
                    BatteryStats.dumpLine(printWriter, n8, string2, "sr", n10, l7, n9, n7, l3, l9);
                }
                BatteryStats.dumpTimer(printWriter, n8, string2, "vib", ((Uid)object11).getVibratorOnTimer(), l5, n);
                BatteryStats.dumpTimer(printWriter, n8, string2, "fg", ((Uid)object11).getForegroundActivityTimer(), l5, n);
                BatteryStats.dumpTimer(printWriter, n8, string2, "fgs", ((Uid)object11).getForegroundServiceTimer(), l5, n);
                object7 = new Object[7];
                l9 = 0L;
                for (n3 = 0; n3 < 7; ++n3) {
                    l7 = ((Uid)object11).getProcessStateTime(n3, l5, n);
                    l9 += l7;
                    object7[n3] = (l7 + 500L) / 1000L;
                }
                if (l9 > 0L) {
                    BatteryStats.dumpLine(printWriter, n8, string2, "st", (Object[])object7);
                }
                l3 = ((Uid)object11).getUserCpuTimeUs(n);
                l7 = ((Uid)object11).getSystemCpuTimeUs(n);
                if (l3 > 0L || l7 > 0L) {
                    BatteryStats.dumpLine(printWriter, n8, string2, "cpu", l3 / 1000L, l7 / 1000L, object6);
                }
                if (object != null) {
                    object3 = ((Uid)object11).getCpuFreqTimes(n);
                    if (object3 != null && ((Object)object3).length == ((Object)object).length) {
                        ((StringBuilder)object9).setLength(0);
                        for (n3 = 0; n3 < ((Object)object3).length; ++n3) {
                            object4 = new StringBuilder();
                            object7 = n3 == 0 ? "" : ",";
                            ((StringBuilder)object4).append((String)object7);
                            ((StringBuilder)object4).append((long)object3[n3]);
                            ((StringBuilder)object9).append(((StringBuilder)object4).toString());
                        }
                        object7 = ((Uid)object11).getScreenOffCpuFreqTimes(n);
                        if (object7 != null) {
                            for (n3 = 0; n3 < ((Object)object7).length; ++n3) {
                                object4 = new StringBuilder();
                                ((StringBuilder)object4).append(",");
                                ((StringBuilder)object4).append((long)object7[n3]);
                                ((StringBuilder)object9).append(((StringBuilder)object4).toString());
                            }
                        } else {
                            l9 = l5;
                            n3 = 0;
                            do {
                                l5 = l9;
                                if (n3 >= ((Object)object3).length) break;
                                ((StringBuilder)object9).append(",0");
                                ++n3;
                            } while (true);
                        }
                        BatteryStats.dumpLine(printWriter, n8, string2, "ctf", "A", ((Object)object3).length, ((StringBuilder)object9).toString());
                    }
                    for (n3 = 0; n3 < 7; ++n3) {
                        object3 = ((Uid)object11).getCpuFreqTimes(n, n3);
                        if (object3 == null || ((Object)object3).length != ((Object)object).length) continue;
                        ((StringBuilder)object9).setLength(0);
                        for (n5 = 0; n5 < ((Object)object3).length; ++n5) {
                            object4 = new StringBuilder();
                            object7 = n5 == 0 ? "" : ",";
                            ((StringBuilder)object4).append((String)object7);
                            ((StringBuilder)object4).append((long)object3[n5]);
                            ((StringBuilder)object9).append(((StringBuilder)object4).toString());
                        }
                        object7 = ((Uid)object11).getScreenOffCpuFreqTimes(n, n3);
                        if (object7 != null) {
                            for (n5 = 0; n5 < ((Object)object7).length; ++n5) {
                                object4 = new StringBuilder();
                                ((StringBuilder)object4).append(",");
                                ((StringBuilder)object4).append((long)object7[n5]);
                                ((StringBuilder)object9).append(((StringBuilder)object4).toString());
                            }
                        } else {
                            object7 = object;
                            n5 = 0;
                            do {
                                object = object7;
                                if (n5 >= ((Object)object3).length) break;
                                ((StringBuilder)object9).append(",0");
                                ++n5;
                            } while (true);
                        }
                        BatteryStats.dumpLine(printWriter, n8, string2, "ctf", Uid.UID_PROCESS_TYPES[n3], ((Object)object3).length, ((StringBuilder)object9).toString());
                    }
                    object7 = object;
                } else {
                    object7 = object;
                }
                object3 = ((Uid)object11).getProcessStats();
                object = object2;
                l9 = l2;
                for (n3 = object3.size() - 1; n3 >= 0; --n3) {
                    object2 = (Uid.Proc)((ArrayMap)object3).valueAt(n3);
                    l3 = ((Uid.Proc)object2).getUserTime(n);
                    l2 = ((Uid.Proc)object2).getSystemTime(n);
                    l7 = ((Uid.Proc)object2).getForegroundTime(n);
                    n10 = ((Uid.Proc)object2).getStarts(n);
                    n7 = ((Uid.Proc)object2).getNumCrashes(n);
                    n5 = ((Uid.Proc)object2).getNumAnrs(n);
                    if (l3 == 0L && l2 == 0L && l7 == 0L && n10 == 0 && n5 == 0 && n7 == 0) continue;
                    object4 = new StringBuilder();
                    object2 = object;
                    ((StringBuilder)object4).append((String)object2);
                    ((StringBuilder)object4).append((String)((ArrayMap)object3).keyAt(n3));
                    ((StringBuilder)object4).append((String)object2);
                    BatteryStats.dumpLine(printWriter, n8, string2, "pr", ((StringBuilder)object4).toString(), l3, l2, l7, n10, n5, n7);
                }
                object2 = object9;
                object12 = ((Uid)object11).getPackageStats();
                object9 = object3;
                for (n3 = object12.size() - 1; n3 >= 0; --n3) {
                    object3 = (Uid.Pkg)((ArrayMap)object12).valueAt(n3);
                    n5 = 0;
                    object4 = ((Uid.Pkg)object3).getWakeupAlarmStats();
                    for (n7 = object4.size() - 1; n7 >= 0; --n7) {
                        n10 = ((Counter)((ArrayMap)object4).valueAt(n7)).getCountLocked(n);
                        n5 += n10;
                        BatteryStats.dumpLine(printWriter, n8, string2, "wua", ((String)((ArrayMap)object4).keyAt(n7)).replace(',', '_'), n10);
                    }
                    object10 = ((Uid.Pkg)object3).getServiceStats();
                    for (n7 = object10.size() - 1; n7 >= 0; --n7) {
                        object11 = ((ArrayMap)object10).valueAt(n7);
                        l2 = ((Uid.Pkg.Serv)object11).getStartTime(l6, n);
                        n10 = ((Uid.Pkg.Serv)object11).getStarts(n);
                        n9 = ((Uid.Pkg.Serv)object11).getLaunches(n);
                        if (l2 == 0L && n10 == 0 && n9 == 0) continue;
                        BatteryStats.dumpLine(printWriter, n8, string2, "apk", n5, ((ArrayMap)object12).keyAt(n3), ((ArrayMap)object10).keyAt(n7), l2 / 1000L, n10, n9);
                    }
                }
                l2 = l6;
                l6 = l9;
                object9 = object8;
                object8 = object2;
                l9 = l2;
            }
            l2 = l9;
            object3 = object8;
            object2 = object9;
            l9 = l5;
            object8 = object;
            l5 = l6;
            object = object7;
            object9 = object3;
            l6 = l2;
            object7 = object2;
        }
    }

    public void dumpCheckinLocked(Context context, PrintWriter printWriter, List<ApplicationInfo> object, int n, long l) {
        int n2;
        this.prepareForDumpLocked();
        BatteryStats.dumpLine(printWriter, 0, "i", "vers", 34, this.getParcelVersion(), this.getStartPlatformVersion(), this.getEndPlatformVersion());
        this.getHistoryBaseTime();
        SystemClock.elapsedRealtime();
        if ((n & 24) != 0 && this.startIteratingHistoryLocked()) {
            n2 = 0;
            do {
                if (n2 >= this.getHistoryStringPoolSize()) break;
                printWriter.print(9);
                printWriter.print(',');
                printWriter.print("hsp");
                printWriter.print(',');
                printWriter.print(n2);
                printWriter.print(",");
                printWriter.print(this.getHistoryTagPoolUid(n2));
                printWriter.print(",\"");
                printWriter.print(this.getHistoryTagPoolString(n2).replace("\\", "\\\\").replace("\"", "\\\""));
                printWriter.print("\"");
                printWriter.println();
                ++n2;
            } while (true);
            try {
                this.dumpHistoryLocked(printWriter, n, l, true);
            }
            catch (Throwable throwable) {
                throw throwable;
            }
            finally {
                this.finishIteratingHistoryLocked();
            }
        }
        if ((n & 8) != 0) {
            return;
        }
        if (object != null) {
            Object object2;
            Object object3;
            Object object4 = new SparseArray<E>();
            for (n2 = 0; n2 < object.size(); ++n2) {
                ApplicationInfo applicationInfo = object.get(n2);
                object2 = (Pair)((SparseArray)object4).get(UserHandle.getAppId(applicationInfo.uid));
                object3 = object2;
                if (object2 == null) {
                    object3 = new Pair<ArrayList<E>, MutableBoolean>(new ArrayList<E>(), new MutableBoolean(false));
                    ((SparseArray)object4).put(UserHandle.getAppId(applicationInfo.uid), object3);
                }
                ((ArrayList)object3.first).add(applicationInfo.packageName);
            }
            object2 = this.getUidStats();
            int n3 = ((SparseArray)object2).size();
            object3 = new String[2];
            object = object4;
            for (n2 = 0; n2 < n3; ++n2) {
                int n4 = UserHandle.getAppId(((SparseArray)object2).keyAt(n2));
                object4 = (Pair)((SparseArray)object).get(n4);
                if (object4 == null || ((MutableBoolean)object4.second).value) continue;
                ((MutableBoolean)object4.second).value = true;
                for (int i = 0; i < ((ArrayList)((Pair)object4).first).size(); ++i) {
                    object3[0] = Integer.toString(n4);
                    object3[1] = (String)((ArrayList)((Pair)object4).first).get(i);
                    BatteryStats.dumpLine(printWriter, 0, "i", "uid", object3);
                }
            }
        }
        if ((n & 4) == 0) {
            BatteryStats.dumpDurationSteps(printWriter, "", "dsd", this.getDischargeLevelStepTracker(), true);
            object = new String[1];
            l = this.computeBatteryTimeRemaining(SystemClock.elapsedRealtime() * 1000L);
            if (l >= 0L) {
                object[0] = Long.toString(l);
                BatteryStats.dumpLine(printWriter, 0, "i", "dtr", (Object[])object);
            }
            BatteryStats.dumpDurationSteps(printWriter, "", "csd", this.getChargeLevelStepTracker(), true);
            l = this.computeChargeTimeRemaining(1000L * SystemClock.elapsedRealtime());
            if (l >= 0L) {
                object[0] = Long.toString(l);
                BatteryStats.dumpLine(printWriter, 0, "i", "ctr", (Object[])object);
            }
            boolean bl = (n & 64) != 0;
            this.dumpCheckinLocked(context, printWriter, 0, -1, bl);
        }
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public void dumpLocked(Context context, PrintWriter printWriter, int n, int n2, long l) {
        block40 : {
            this.prepareForDumpLocked();
            bl2 = (n & 14) != 0;
            if ((n & 8) != 0 || !bl2) {
                block37 : {
                    l2 = this.getHistoryTotalSize();
                    l3 = this.getHistoryUsedSize();
                    if (this.startIteratingHistoryLocked()) {
                        block38 : {
                            printWriter.print("Battery History (");
                            printWriter.print(100L * l3 / l2);
                            printWriter.print("% used, ");
                            this.printSizeValue(printWriter, l3);
                            printWriter.print(" used of ");
                            this.printSizeValue(printWriter, l2);
                            printWriter.print(", ");
                            printWriter.print(this.getHistoryStringPoolSize());
                            printWriter.print(" strings using ");
                            this.printSizeValue(printWriter, this.getHistoryStringPoolBytes());
                            printWriter.println("):");
                            try {
                                this.dumpHistoryLocked(printWriter, n, l, false);
                                printWriter.println();
                                this.finishIteratingHistoryLocked();
                                break block37;
                            }
                            catch (Throwable throwable) {
                                break block38;
                            }
                            catch (Throwable throwable) {
                                // empty catch block
                            }
                        }
                        this.finishIteratingHistoryLocked();
                        throw var1_4;
                    }
                }
                if (this.startIteratingOldHistoryLocked()) {
                    try {
                        object /* !! */  = new HistoryItem();
                        printWriter.println("Old battery History:");
                        object2 = new HistoryPrinter();
                        l = -1L;
                        while (this.getNextOldHistoryLocked((HistoryItem)object /* !! */ )) {
                            if (l < 0L) {
                                l = object /* !! */ .time;
                            }
                            bl = (n & 32) != 0;
                            object2.printNextItem(printWriter, (HistoryItem)object /* !! */ , l, false, bl);
                        }
                        printWriter.println();
                    }
                    finally {
                        this.finishIteratingOldHistoryLocked();
                    }
                }
            }
            if (bl2 && (n & 6) == 0) {
                return;
            }
            if (bl2) ** GOTO lbl67
            arrn = this.getUidStats();
            n4 = arrn.size();
            n3 = 0;
            l3 = SystemClock.elapsedRealtime();
            i = 0;
            do {
                block42 : {
                    block43 : {
                        block41 : {
                            if (i >= n4) break block41;
                            object /* !! */  = arrn.valueAt(i).getPidStats();
                            n5 = n3;
                            if (object /* !! */  == null) break block42;
                            break block43;
                        }
                        if (n3 != 0) {
                            printWriter.println();
                        }
lbl67: // 4 sources:
                        if (!bl2 || (n & 2) != 0) {
                            if (BatteryStats.dumpDurationSteps(printWriter, "  ", "Discharge step durations:", this.getDischargeLevelStepTracker(), false)) {
                                l = this.computeBatteryTimeRemaining(SystemClock.elapsedRealtime() * 1000L);
                                if (l >= 0L) {
                                    printWriter.print("  Estimated discharge time remaining: ");
                                    TimeUtils.formatDuration(l / 1000L, printWriter);
                                    printWriter.println();
                                }
                                object /* !! */  = this.getDischargeLevelStepTracker();
                                for (n3 = 0; n3 < ((int[])(object2 = BatteryStats.STEP_LEVEL_MODES_OF_INTEREST)).length; ++n3) {
                                    BatteryStats.dumpTimeEstimate(printWriter, "  Estimated ", BatteryStats.STEP_LEVEL_MODE_LABELS[n3], " time: ", object /* !! */ .computeTimeEstimate(object2[n3], BatteryStats.STEP_LEVEL_MODE_VALUES[n3], null));
                                }
                                printWriter.println();
                            }
                            if (BatteryStats.dumpDurationSteps(printWriter, "  ", "Charge step durations:", this.getChargeLevelStepTracker(), false)) {
                                l = this.computeChargeTimeRemaining(SystemClock.elapsedRealtime() * 1000L);
                                if (l >= 0L) {
                                    printWriter.print("  Estimated charge time remaining: ");
                                    TimeUtils.formatDuration(l / 1000L, printWriter);
                                    printWriter.println();
                                }
                                printWriter.println();
                            }
                        }
                        if (bl2 && (n & 4) == 0) {
                            bl = false;
                            break block40;
                        }
                        printWriter.println("Daily stats:");
                        printWriter.print("  Current start time: ");
                        printWriter.println(DateFormat.format((CharSequence)"yyyy-MM-dd-HH-mm-ss", this.getCurrentDailyStartTime()).toString());
                        printWriter.print("  Next min deadline: ");
                        printWriter.println(DateFormat.format((CharSequence)"yyyy-MM-dd-HH-mm-ss", this.getNextMinDailyDeadline()).toString());
                        printWriter.print("  Next max deadline: ");
                        printWriter.println(DateFormat.format((CharSequence)"yyyy-MM-dd-HH-mm-ss", this.getNextMaxDailyDeadline()).toString());
                        stringBuilder = new StringBuilder(64);
                        object /* !! */  = new int[1];
                        object2 = this.getDailyDischargeLevelStepTracker();
                        object3 = this.getDailyChargeLevelStepTracker();
                        arrayList = this.getDailyPackageChanges();
                        if (object2.mNumStepDurations > 0 || object3.mNumStepDurations > 0 || arrayList != null) {
                            if ((n & 4) == 0 && bl2) {
                                printWriter.println("  Current daily steps:");
                                this.dumpDailyLevelStepSummary(printWriter, "    ", "Discharge", (LevelStepTracker)object2, stringBuilder, object /* !! */ );
                                this.dumpDailyLevelStepSummary(printWriter, "    ", "Charge", (LevelStepTracker)object3, stringBuilder, object /* !! */ );
                            } else {
                                bl = false;
                                arrn = object /* !! */ ;
                                object4 = object2;
                                if (BatteryStats.dumpDurationSteps(printWriter, "    ", "  Current daily discharge step durations:", (LevelStepTracker)object4, bl)) {
                                    this.dumpDailyLevelStepSummary(printWriter, "      ", "Discharge", (LevelStepTracker)object4, stringBuilder, arrn);
                                }
                                if (BatteryStats.dumpDurationSteps(printWriter, "    ", "  Current daily charge step durations:", (LevelStepTracker)object3, bl)) {
                                    this.dumpDailyLevelStepSummary(printWriter, "      ", "Charge", (LevelStepTracker)object3, stringBuilder, arrn);
                                }
                                this.dumpDailyPackageChanges(printWriter, "    ", arrayList);
                            }
                        }
                        object3 = "yyyy-MM-dd-HH-mm-ss";
                        bl = false;
                        arrn = object /* !! */ ;
                        n3 = 0;
                        object /* !! */  = object3;
                        break;
                    }
                    n6 = 0;
                    do {
                        n5 = n3;
                        if (n6 >= object /* !! */ .size()) break;
                        object2 = (Uid.Pid)object /* !! */ .valueAt(n6);
                        n5 = n3;
                        if (n3 == 0) {
                            printWriter.println("Per-PID Stats:");
                            n5 = 1;
                        }
                        l2 = object2.mWakeSumMs;
                        l = object2.mWakeNesting > 0 ? l3 - object2.mWakeStartMs : 0L;
                        printWriter.print("  PID ");
                        printWriter.print(object /* !! */ .keyAt(n6));
                        printWriter.print(" wake time: ");
                        TimeUtils.formatDuration(l2 + l, printWriter);
                        printWriter.println("");
                        ++n6;
                        n3 = n5;
                    } while (true);
                }
                ++i;
                n3 = n5;
            } while (true);
            while ((object3 = this.getDailyItemLocked(n3)) != null) {
                if ((n & 4) != 0) {
                    printWriter.println();
                }
                printWriter.print("  Daily from ");
                printWriter.print(DateFormat.format((CharSequence)object /* !! */ , object3.mStartTime).toString());
                printWriter.print(" to ");
                printWriter.print(DateFormat.format((CharSequence)object /* !! */ , object3.mEndTime).toString());
                printWriter.println(":");
                if ((n & 4) == 0 && bl2) {
                    this.dumpDailyLevelStepSummary(printWriter, "    ", "Discharge", object3.mDischargeSteps, stringBuilder, arrn);
                    this.dumpDailyLevelStepSummary(printWriter, "    ", "Charge", object3.mChargeSteps, stringBuilder, arrn);
                } else {
                    if (BatteryStats.dumpDurationSteps(printWriter, "      ", "    Discharge step durations:", object3.mDischargeSteps, false)) {
                        this.dumpDailyLevelStepSummary(printWriter, "        ", "Discharge", object3.mDischargeSteps, stringBuilder, arrn);
                    }
                    if (BatteryStats.dumpDurationSteps(printWriter, "      ", "    Charge step durations:", object3.mChargeSteps, false)) {
                        this.dumpDailyLevelStepSummary(printWriter, "        ", "Charge", object3.mChargeSteps, stringBuilder, arrn);
                    }
                    this.dumpDailyPackageChanges(printWriter, "    ", object3.mPackageChanges);
                }
                bl = false;
                ++n3;
            }
            printWriter.println();
        }
        if (bl2) {
            if ((n & 2) == 0) return;
        }
        printWriter.println("Statistics since last charge:");
        object2 = new StringBuilder();
        object2.append("  System starts: ");
        object2.append(this.getStartCount());
        object2.append(", currently on battery: ");
        object2.append(this.getIsOnBattery());
        printWriter.println(object2.toString());
        if ((n & 64) != 0) {
            bl = true;
        }
        this.dumpLocked(context, printWriter, "", 0, n2, bl);
        printWriter.println();
    }

    public final void dumpLocked(Context context, PrintWriter printWriter, String string2, int n, int n2) {
        this.dumpLocked(context, printWriter, string2, n, n2, BatteryStatsHelper.checkWifiOnly(context));
    }

    public final void dumpLocked(Context object, PrintWriter printWriter, String string2, int n, int n2, boolean bl) {
        int n3;
        Object object8;
        Object object2;
        Object object5;
        Object object3;
        Object object6;
        Object object4;
        long l;
        if (n != 0) {
            object = new StringBuilder();
            ((StringBuilder)object).append("ERROR: BatteryStats.dump called for which type ");
            ((StringBuilder)object).append(n);
            ((StringBuilder)object).append(" but only STATS_SINCE_CHARGED is supported");
            printWriter.println(((StringBuilder)object).toString());
            return;
        }
        long l2 = SystemClock.uptimeMillis() * 1000L;
        long l3 = SystemClock.elapsedRealtime() * 1000L;
        long l4 = (l3 + 500L) / 1000L;
        long l5 = this.getBatteryUptime(l2);
        long l6 = this.computeBatteryUptime(l2, n);
        long l7 = this.computeBatteryRealtime(l3, n);
        long l8 = this.computeRealtime(l3, n);
        long l9 = this.computeUptime(l2, n);
        long l10 = this.computeBatteryScreenOffUptime(l2, n);
        long l11 = this.computeBatteryScreenOffRealtime(l3, n);
        long l12 = this.computeBatteryTimeRemaining(l3);
        long l13 = this.computeChargeTimeRemaining(l3);
        l2 = this.getScreenDozeTime(l3, n);
        Object object9 = new StringBuilder(128);
        Object object10 = this.getUidStats();
        int n4 = ((SparseArray)object10).size();
        int n5 = this.getEstimatedBatteryCapacity();
        if (n5 > 0) {
            ((StringBuilder)object9).setLength(0);
            ((StringBuilder)object9).append(string2);
            ((StringBuilder)object9).append("  Estimated battery capacity: ");
            ((StringBuilder)object9).append(BatteryStatsHelper.makemAh(n5));
            ((StringBuilder)object9).append(" mAh");
            printWriter.println(((StringBuilder)object9).toString());
        }
        if ((n5 = this.getMinLearnedBatteryCapacity()) > 0) {
            ((StringBuilder)object9).setLength(0);
            ((StringBuilder)object9).append(string2);
            ((StringBuilder)object9).append("  Min learned battery capacity: ");
            ((StringBuilder)object9).append(BatteryStatsHelper.makemAh(n5 / 1000));
            ((StringBuilder)object9).append(" mAh");
            printWriter.println(((StringBuilder)object9).toString());
        }
        if ((n5 = this.getMaxLearnedBatteryCapacity()) > 0) {
            ((StringBuilder)object9).setLength(0);
            ((StringBuilder)object9).append(string2);
            ((StringBuilder)object9).append("  Max learned battery capacity: ");
            ((StringBuilder)object9).append(BatteryStatsHelper.makemAh(n5 / 1000));
            ((StringBuilder)object9).append(" mAh");
            printWriter.println(((StringBuilder)object9).toString());
        }
        ((StringBuilder)object9).setLength(0);
        ((StringBuilder)object9).append(string2);
        ((StringBuilder)object9).append("  Time on battery: ");
        BatteryStats.formatTimeMs((StringBuilder)object9, l7 / 1000L);
        ((StringBuilder)object9).append("(");
        ((StringBuilder)object9).append(this.formatRatioLocked(l7, l8));
        ((StringBuilder)object9).append(") realtime, ");
        BatteryStats.formatTimeMs((StringBuilder)object9, l6 / 1000L);
        ((StringBuilder)object9).append("(");
        ((StringBuilder)object9).append(this.formatRatioLocked(l6, l7));
        ((StringBuilder)object9).append(") uptime");
        printWriter.println(((StringBuilder)object9).toString());
        ((StringBuilder)object9).setLength(0);
        ((StringBuilder)object9).append(string2);
        ((StringBuilder)object9).append("  Time on battery screen off: ");
        BatteryStats.formatTimeMs((StringBuilder)object9, l11 / 1000L);
        ((StringBuilder)object9).append("(");
        ((StringBuilder)object9).append(this.formatRatioLocked(l11, l7));
        ((StringBuilder)object9).append(") realtime, ");
        BatteryStats.formatTimeMs((StringBuilder)object9, l10 / 1000L);
        ((StringBuilder)object9).append("(");
        ((StringBuilder)object9).append(this.formatRatioLocked(l10, l7));
        ((StringBuilder)object9).append(") uptime");
        printWriter.println(((StringBuilder)object9).toString());
        ((StringBuilder)object9).setLength(0);
        ((StringBuilder)object9).append(string2);
        ((StringBuilder)object9).append("  Time on battery screen doze: ");
        BatteryStats.formatTimeMs((StringBuilder)object9, l2 / 1000L);
        ((StringBuilder)object9).append("(");
        ((StringBuilder)object9).append(this.formatRatioLocked(l2, l7));
        ((StringBuilder)object9).append(")");
        printWriter.println(((StringBuilder)object9).toString());
        ((StringBuilder)object9).setLength(0);
        ((StringBuilder)object9).append(string2);
        ((StringBuilder)object9).append("  Total run time: ");
        BatteryStats.formatTimeMs((StringBuilder)object9, l8 / 1000L);
        ((StringBuilder)object9).append("realtime, ");
        BatteryStats.formatTimeMs((StringBuilder)object9, l9 / 1000L);
        ((StringBuilder)object9).append("uptime");
        printWriter.println(((StringBuilder)object9).toString());
        if (l12 >= 0L) {
            ((StringBuilder)object9).setLength(0);
            ((StringBuilder)object9).append(string2);
            ((StringBuilder)object9).append("  Battery time remaining: ");
            BatteryStats.formatTimeMs((StringBuilder)object9, l12 / 1000L);
            printWriter.println(((StringBuilder)object9).toString());
        }
        if (l13 >= 0L) {
            ((StringBuilder)object9).setLength(0);
            ((StringBuilder)object9).append(string2);
            ((StringBuilder)object9).append("  Charge time remaining: ");
            BatteryStats.formatTimeMs((StringBuilder)object9, l13 / 1000L);
            printWriter.println(((StringBuilder)object9).toString());
        }
        if ((l6 = this.getUahDischarge(n)) >= 0L) {
            ((StringBuilder)object9).setLength(0);
            ((StringBuilder)object9).append(string2);
            ((StringBuilder)object9).append("  Discharge: ");
            ((StringBuilder)object9).append(BatteryStatsHelper.makemAh((double)l6 / 1000.0));
            ((StringBuilder)object9).append(" mAh");
            printWriter.println(((StringBuilder)object9).toString());
        }
        if ((l8 = this.getUahDischargeScreenOff(n)) >= 0L) {
            ((StringBuilder)object9).setLength(0);
            ((StringBuilder)object9).append(string2);
            ((StringBuilder)object9).append("  Screen off discharge: ");
            ((StringBuilder)object9).append(BatteryStatsHelper.makemAh((double)l8 / 1000.0));
            ((StringBuilder)object9).append(" mAh");
            printWriter.println(((StringBuilder)object9).toString());
        }
        if ((l2 = this.getUahDischargeScreenDoze(n)) >= 0L) {
            ((StringBuilder)object9).setLength(0);
            ((StringBuilder)object9).append(string2);
            ((StringBuilder)object9).append("  Screen doze discharge: ");
            ((StringBuilder)object9).append(BatteryStatsHelper.makemAh((double)l2 / 1000.0));
            ((StringBuilder)object9).append(" mAh");
            printWriter.println(((StringBuilder)object9).toString());
        }
        if ((l2 = l6 - l8) >= 0L) {
            ((StringBuilder)object9).setLength(0);
            ((StringBuilder)object9).append(string2);
            ((StringBuilder)object9).append("  Screen on discharge: ");
            ((StringBuilder)object9).append(BatteryStatsHelper.makemAh((double)l2 / 1000.0));
            ((StringBuilder)object9).append(" mAh");
            printWriter.println(((StringBuilder)object9).toString());
        }
        if ((l2 = this.getUahDischargeLightDoze(n)) >= 0L) {
            ((StringBuilder)object9).setLength(0);
            ((StringBuilder)object9).append(string2);
            ((StringBuilder)object9).append("  Device light doze discharge: ");
            ((StringBuilder)object9).append(BatteryStatsHelper.makemAh((double)l2 / 1000.0));
            ((StringBuilder)object9).append(" mAh");
            printWriter.println(((StringBuilder)object9).toString());
        }
        if ((l2 = this.getUahDischargeDeepDoze(n)) >= 0L) {
            ((StringBuilder)object9).setLength(0);
            ((StringBuilder)object9).append(string2);
            ((StringBuilder)object9).append("  Device deep doze discharge: ");
            ((StringBuilder)object9).append(BatteryStatsHelper.makemAh((double)l2 / 1000.0));
            ((StringBuilder)object9).append(" mAh");
            printWriter.println(((StringBuilder)object9).toString());
        }
        printWriter.print("  Start clock time: ");
        printWriter.println(DateFormat.format((CharSequence)"yyyy-MM-dd-HH-mm-ss", this.getStartClockTime()).toString());
        long l14 = this.getScreenOnTime(l3, n);
        l2 = this.getInteractiveTime(l3, n);
        l12 = this.getPowerSaveModeEnabledTime(l3, n);
        l9 = this.getDeviceIdleModeTime(1, l3, n);
        l13 = this.getDeviceIdleModeTime(2, l3, n);
        l11 = this.getDeviceIdlingTime(1, l3, n);
        l10 = this.getDeviceIdlingTime(2, l3, n);
        l6 = this.getPhoneOnTime(l3, n);
        this.getGlobalWifiRunningTime(l3, n);
        this.getWifiOnTime(l3, n);
        ((StringBuilder)object9).setLength(0);
        ((StringBuilder)object9).append(string2);
        ((StringBuilder)object9).append("  Screen on: ");
        BatteryStats.formatTimeMs((StringBuilder)object9, l14 / 1000L);
        ((StringBuilder)object9).append("(");
        ((StringBuilder)object9).append(this.formatRatioLocked(l14, l7));
        ((StringBuilder)object9).append(") ");
        ((StringBuilder)object9).append(this.getScreenOnCount(n));
        ((StringBuilder)object9).append("x, Interactive: ");
        BatteryStats.formatTimeMs((StringBuilder)object9, l2 / 1000L);
        ((StringBuilder)object9).append("(");
        ((StringBuilder)object9).append(this.formatRatioLocked(l2, l7));
        Object object11 = ")";
        ((StringBuilder)object9).append((String)object11);
        printWriter.println(((StringBuilder)object9).toString());
        ((StringBuilder)object9).setLength(0);
        ((StringBuilder)object9).append(string2);
        ((StringBuilder)object9).append("  Screen brightnesses:");
        int n6 = 0;
        n5 = 0;
        do {
            object6 = " ";
            if (n5 >= 5) break;
            l = this.getScreenBrightnessTime(n5, l3, n);
            if (l != 0L) {
                ((StringBuilder)object9).append("\n    ");
                ((StringBuilder)object9).append(string2);
                ((StringBuilder)object9).append(SCREEN_BRIGHTNESS_NAMES[n5]);
                ((StringBuilder)object9).append(" ");
                BatteryStats.formatTimeMs((StringBuilder)object9, l / 1000L);
                ((StringBuilder)object9).append("(");
                ((StringBuilder)object9).append(this.formatRatioLocked(l, l14));
                ((StringBuilder)object9).append((String)object11);
                n6 = 1;
            }
            ++n5;
        } while (true);
        if (n6 == 0) {
            ((StringBuilder)object9).append(" (no activity)");
        }
        printWriter.println(((StringBuilder)object9).toString());
        if (l12 != 0L) {
            ((StringBuilder)object9).setLength(0);
            ((StringBuilder)object9).append(string2);
            ((StringBuilder)object9).append("  Power save mode enabled: ");
            BatteryStats.formatTimeMs((StringBuilder)object9, l12 / 1000L);
            ((StringBuilder)object9).append("(");
            ((StringBuilder)object9).append(this.formatRatioLocked(l12, l7));
            ((StringBuilder)object9).append((String)object11);
            printWriter.println(((StringBuilder)object9).toString());
        }
        l2 = l7;
        if (l11 != 0L) {
            ((StringBuilder)object9).setLength(0);
            ((StringBuilder)object9).append(string2);
            ((StringBuilder)object9).append("  Device light idling: ");
            BatteryStats.formatTimeMs((StringBuilder)object9, l11 / 1000L);
            ((StringBuilder)object9).append("(");
            ((StringBuilder)object9).append(this.formatRatioLocked(l11, l2));
            ((StringBuilder)object9).append(") ");
            ((StringBuilder)object9).append(this.getDeviceIdlingCount(1, n));
            ((StringBuilder)object9).append("x");
            printWriter.println(((StringBuilder)object9).toString());
        }
        Object object12 = ") ";
        if (l9 != 0L) {
            ((StringBuilder)object9).setLength(0);
            ((StringBuilder)object9).append(string2);
            ((StringBuilder)object9).append("  Idle mode light time: ");
            BatteryStats.formatTimeMs((StringBuilder)object9, l9 / 1000L);
            ((StringBuilder)object9).append("(");
            ((StringBuilder)object9).append(this.formatRatioLocked(l9, l2));
            ((StringBuilder)object9).append(") ");
            ((StringBuilder)object9).append(this.getDeviceIdleModeCount(1, n));
            ((StringBuilder)object9).append("x");
            ((StringBuilder)object9).append(" -- longest ");
            BatteryStats.formatTimeMs((StringBuilder)object9, this.getLongestDeviceIdleModeTime(1));
            printWriter.println(((StringBuilder)object9).toString());
        }
        if (l10 != 0L) {
            ((StringBuilder)object9).setLength(0);
            ((StringBuilder)object9).append(string2);
            ((StringBuilder)object9).append("  Device full idling: ");
            BatteryStats.formatTimeMs((StringBuilder)object9, l10 / 1000L);
            ((StringBuilder)object9).append("(");
            ((StringBuilder)object9).append(this.formatRatioLocked(l10, l2));
            ((StringBuilder)object9).append(") ");
            ((StringBuilder)object9).append(this.getDeviceIdlingCount(2, n));
            ((StringBuilder)object9).append("x");
            printWriter.println(((StringBuilder)object9).toString());
        }
        if (l13 != 0L) {
            ((StringBuilder)object9).setLength(0);
            ((StringBuilder)object9).append(string2);
            ((StringBuilder)object9).append("  Idle mode full time: ");
            BatteryStats.formatTimeMs((StringBuilder)object9, l13 / 1000L);
            ((StringBuilder)object9).append("(");
            ((StringBuilder)object9).append(this.formatRatioLocked(l13, l2));
            ((StringBuilder)object9).append(") ");
            ((StringBuilder)object9).append(this.getDeviceIdleModeCount(2, n));
            ((StringBuilder)object9).append("x");
            ((StringBuilder)object9).append(" -- longest ");
            BatteryStats.formatTimeMs((StringBuilder)object9, this.getLongestDeviceIdleModeTime(2));
            printWriter.println(((StringBuilder)object9).toString());
        }
        if (l6 != 0L) {
            ((StringBuilder)object9).setLength(0);
            ((StringBuilder)object9).append(string2);
            ((StringBuilder)object9).append("  Active phone call: ");
            BatteryStats.formatTimeMs((StringBuilder)object9, l6 / 1000L);
            ((StringBuilder)object9).append("(");
            ((StringBuilder)object9).append(this.formatRatioLocked(l6, l2));
            ((StringBuilder)object9).append(") ");
            ((StringBuilder)object9).append(this.getPhoneOnCount(n));
            ((StringBuilder)object9).append("x");
        }
        if ((n5 = this.getNumConnectivityChange(n)) != 0) {
            printWriter.print(string2);
            printWriter.print("  Connectivity changes: ");
            printWriter.println(n5);
        }
        Object object13 = new ArrayList<TimerEntry>();
        l7 = 0L;
        l6 = 0L;
        for (n6 = 0; n6 < n4; ++n6) {
            n3 = n4;
            object5 = ((SparseArray)object10).valueAt(n6);
            object2 = object5.getWakelockStats();
            l9 = l6;
            for (n4 = object2.size() - 1; n4 >= 0; --n4) {
                object8 = ((ArrayMap)object2).valueAt(n4);
                object3 = ((Uid.Wakelock)object8).getWakeTime(1);
                l6 = l9;
                if (object3 != null) {
                    l6 = l9 + ((Timer)object3).getTotalTimeLocked(l3, n);
                }
                if ((object8 = ((Uid.Wakelock)object8).getWakeTime(0)) != null && (l9 = ((Timer)object8).getTotalTimeLocked(l3, n)) > 0L) {
                    if (n2 < 0) {
                        ((ArrayList)object13).add(new TimerEntry((String)((ArrayMap)object2).keyAt(n4), object5.getUid(), (Timer)object8, l9));
                    }
                    l7 += l9;
                }
                l9 = l6;
            }
            n4 = n3;
            l6 = l9;
        }
        n6 = n5;
        l13 = this.getNetworkActivityBytes(0, n);
        l9 = this.getNetworkActivityBytes(1, n);
        long l15 = this.getNetworkActivityBytes(2, n);
        long l16 = this.getNetworkActivityBytes(3, n);
        l14 = this.getNetworkActivityPackets(0, n);
        long l17 = this.getNetworkActivityPackets(1, n);
        long l18 = this.getNetworkActivityPackets(2, n);
        l = this.getNetworkActivityPackets(3, n);
        long l19 = this.getNetworkActivityBytes(4, n);
        l10 = this.getNetworkActivityBytes(5, n);
        if (l6 != 0L) {
            ((StringBuilder)object9).setLength(0);
            ((StringBuilder)object9).append(string2);
            ((StringBuilder)object9).append("  Total full wakelock time: ");
            BatteryStats.formatTimeMsNoSpace((StringBuilder)object9, (l6 + 500L) / 1000L);
            printWriter.println(((StringBuilder)object9).toString());
        }
        if (l7 != 0L) {
            ((StringBuilder)object9).setLength(0);
            ((StringBuilder)object9).append(string2);
            ((StringBuilder)object9).append("  Total partial wakelock time: ");
            BatteryStats.formatTimeMsNoSpace((StringBuilder)object9, (l7 + 500L) / 1000L);
            printWriter.println(((StringBuilder)object9).toString());
        }
        l7 = this.getWifiMulticastWakelockTime(l3, n);
        n5 = this.getWifiMulticastWakelockCount(n);
        if (l7 != 0L) {
            ((StringBuilder)object9).setLength(0);
            ((StringBuilder)object9).append(string2);
            ((StringBuilder)object9).append("  Total WiFi Multicast wakelock Count: ");
            ((StringBuilder)object9).append(n5);
            printWriter.println(((StringBuilder)object9).toString());
            ((StringBuilder)object9).setLength(0);
            ((StringBuilder)object9).append(string2);
            ((StringBuilder)object9).append("  Total WiFi Multicast wakelock time: ");
            BatteryStats.formatTimeMsNoSpace((StringBuilder)object9, (l7 + 500L) / 1000L);
            printWriter.println(((StringBuilder)object9).toString());
        }
        printWriter.println("");
        printWriter.print(string2);
        ((StringBuilder)object9).setLength(0);
        ((StringBuilder)object9).append(string2);
        ((StringBuilder)object9).append("  CONNECTIVITY POWER SUMMARY START");
        printWriter.println(((StringBuilder)object9).toString());
        printWriter.print(string2);
        ((StringBuilder)object9).setLength(0);
        ((StringBuilder)object9).append(string2);
        ((StringBuilder)object9).append("  Logging duration for connectivity statistics: ");
        BatteryStats.formatTimeMs((StringBuilder)object9, l2 / 1000L);
        printWriter.println(((StringBuilder)object9).toString());
        ((StringBuilder)object9).setLength(0);
        ((StringBuilder)object9).append(string2);
        ((StringBuilder)object9).append("  Cellular Statistics:");
        printWriter.println(((StringBuilder)object9).toString());
        printWriter.print(string2);
        ((StringBuilder)object9).setLength(0);
        ((StringBuilder)object9).append(string2);
        ((StringBuilder)object9).append("     Cellular kernel active time: ");
        l7 = this.getMobileRadioActiveTime(l3, n);
        BatteryStats.formatTimeMs((StringBuilder)object9, l7 / 1000L);
        ((StringBuilder)object9).append("(");
        ((StringBuilder)object9).append(this.formatRatioLocked(l7, l2));
        ((StringBuilder)object9).append((String)object11);
        printWriter.println(((StringBuilder)object9).toString());
        object2 = this.getModemControllerActivity();
        object8 = object10;
        l6 = l3;
        object5 = "(";
        n5 = n4;
        this.printControllerActivity(printWriter, (StringBuilder)object9, string2, "Cellular", (ControllerActivityCounter)object2, n);
        printWriter.print("     Cellular data received: ");
        printWriter.println(this.formatBytesLocked(l13));
        printWriter.print("     Cellular data sent: ");
        printWriter.println(this.formatBytesLocked(l9));
        printWriter.print("     Cellular packets received: ");
        printWriter.println(l14);
        printWriter.print("     Cellular packets sent: ");
        l3 = l17;
        printWriter.println(l3);
        ((StringBuilder)object9).setLength(0);
        ((StringBuilder)object9).append(string2);
        ((StringBuilder)object9).append("     Cellular Radio Access Technology:");
        n4 = 0;
        object10 = object12;
        object12 = object5;
        for (n3 = 0; n3 < 22; ++n3) {
            l17 = this.getPhoneDataConnectionTime(n3, l6, n);
            if (l17 == 0L) continue;
            ((StringBuilder)object9).append("\n       ");
            ((StringBuilder)object9).append(string2);
            object5 = DATA_CONNECTION_NAMES;
            n4 = 1;
            object5 = n3 < ((String[])object5).length ? object5[n3] : "ERROR";
            ((StringBuilder)object9).append((String)object5);
            ((StringBuilder)object9).append((String)object6);
            BatteryStats.formatTimeMs((StringBuilder)object9, l17 / 1000L);
            ((StringBuilder)object9).append((String)object12);
            ((StringBuilder)object9).append(this.formatRatioLocked(l17, l2));
            ((StringBuilder)object9).append((String)object10);
        }
        l13 = l3;
        l14 = l9;
        if (n4 == 0) {
            ((StringBuilder)object9).append(" (no activity)");
        }
        printWriter.println(((StringBuilder)object9).toString());
        ((StringBuilder)object9).setLength(0);
        ((StringBuilder)object9).append(string2);
        ((StringBuilder)object9).append("     Cellular Rx signal strength (RSRP):");
        object5 = new String[]{"very poor (less than -128dBm): ", "poor (-128dBm to -118dBm): ", "moderate (-118dBm to -108dBm): ", "good (-108dBm to -98dBm): ", "great (greater than -98dBm): "};
        n4 = Math.min(5, ((String[])object5).length);
        int n7 = 0;
        for (n3 = 0; n3 < n4; ++n3) {
            l9 = this.getPhoneSignalStrengthTime(n3, l6, n);
            if (l9 == 0L) continue;
            ((StringBuilder)object9).append("\n       ");
            ((StringBuilder)object9).append(string2);
            n7 = 1;
            ((StringBuilder)object9).append(object5[n3]);
            ((StringBuilder)object9).append((String)object6);
            BatteryStats.formatTimeMs((StringBuilder)object9, l9 / 1000L);
            ((StringBuilder)object9).append((String)object12);
            ((StringBuilder)object9).append(this.formatRatioLocked(l9, l2));
            ((StringBuilder)object9).append((String)object10);
        }
        if (n7 == 0) {
            ((StringBuilder)object9).append(" (no activity)");
        }
        printWriter.println(((StringBuilder)object9).toString());
        printWriter.print(string2);
        ((StringBuilder)object9).setLength(0);
        ((StringBuilder)object9).append(string2);
        ((StringBuilder)object9).append("  Wifi Statistics:");
        printWriter.println(((StringBuilder)object9).toString());
        printWriter.print(string2);
        ((StringBuilder)object9).setLength(0);
        ((StringBuilder)object9).append(string2);
        ((StringBuilder)object9).append("     Wifi kernel active time: ");
        l9 = this.getWifiActiveTime(l6, n);
        BatteryStats.formatTimeMs((StringBuilder)object9, l9 / 1000L);
        ((StringBuilder)object9).append((String)object12);
        ((StringBuilder)object9).append(this.formatRatioLocked(l9, l2));
        ((StringBuilder)object9).append((String)object11);
        printWriter.println(((StringBuilder)object9).toString());
        object5 = this.getWifiControllerActivity();
        l17 = l6;
        this.printControllerActivity(printWriter, (StringBuilder)object9, string2, "WiFi", (ControllerActivityCounter)object5, n);
        printWriter.print("     Wifi data received: ");
        printWriter.println(this.formatBytesLocked(l15));
        printWriter.print("     Wifi data sent: ");
        l3 = l16;
        printWriter.println(this.formatBytesLocked(l3));
        printWriter.print("     Wifi packets received: ");
        l9 = l18;
        printWriter.println(l9);
        printWriter.print("     Wifi packets sent: ");
        l6 = l;
        printWriter.println(l6);
        ((StringBuilder)object9).setLength(0);
        ((StringBuilder)object9).append(string2);
        ((StringBuilder)object9).append("     Wifi states:");
        n3 = 0;
        for (n4 = 0; n4 < 8; ++n4) {
            l = this.getWifiStateTime(n4, l17, n);
            if (l == 0L) continue;
            ((StringBuilder)object9).append("\n       ");
            ((StringBuilder)object9).append(WIFI_STATE_NAMES[n4]);
            ((StringBuilder)object9).append((String)object6);
            n3 = 1;
            BatteryStats.formatTimeMs((StringBuilder)object9, l / 1000L);
            ((StringBuilder)object9).append((String)object12);
            ((StringBuilder)object9).append(this.formatRatioLocked(l, l2));
            ((StringBuilder)object9).append((String)object10);
        }
        l = l6;
        if (n3 == 0) {
            ((StringBuilder)object9).append(" (no activity)");
        }
        printWriter.println(((StringBuilder)object9).toString());
        ((StringBuilder)object9).setLength(0);
        ((StringBuilder)object9).append(string2);
        ((StringBuilder)object9).append("     Wifi supplicant states:");
        n3 = 0;
        l6 = l17;
        for (n4 = 0; n4 < 13; ++n4) {
            l17 = this.getWifiSupplStateTime(n4, l6, n);
            if (l17 == 0L) continue;
            ((StringBuilder)object9).append("\n       ");
            n3 = 1;
            ((StringBuilder)object9).append(WIFI_SUPPL_STATE_NAMES[n4]);
            ((StringBuilder)object9).append((String)object6);
            BatteryStats.formatTimeMs((StringBuilder)object9, l17 / 1000L);
            ((StringBuilder)object9).append((String)object12);
            ((StringBuilder)object9).append(this.formatRatioLocked(l17, l2));
            ((StringBuilder)object9).append((String)object10);
        }
        if (n3 == 0) {
            ((StringBuilder)object9).append(" (no activity)");
        }
        printWriter.println(((StringBuilder)object9).toString());
        ((StringBuilder)object9).setLength(0);
        ((StringBuilder)object9).append(string2);
        ((StringBuilder)object9).append("     Wifi Rx signal strength (RSSI):");
        object5 = new String[]{"very poor (less than -88.75dBm): ", "poor (-88.75 to -77.5dBm): ", "moderate (-77.5dBm to -66.25dBm): ", "good (-66.25dBm to -55dBm): ", "great (greater than -55dBm): "};
        n4 = Math.min(5, ((String[])object5).length);
        n7 = 0;
        for (n3 = 0; n3 < n4; ++n3) {
            l17 = this.getWifiSignalStrengthTime(n3, l6, n);
            if (l17 == 0L) continue;
            ((StringBuilder)object9).append("\n    ");
            ((StringBuilder)object9).append(string2);
            ((StringBuilder)object9).append("     ");
            ((StringBuilder)object9).append(object5[n3]);
            BatteryStats.formatTimeMs((StringBuilder)object9, l17 / 1000L);
            ((StringBuilder)object9).append((String)object12);
            ((StringBuilder)object9).append(this.formatRatioLocked(l17, l2));
            ((StringBuilder)object9).append((String)object10);
            n7 = 1;
        }
        if (n7 == 0) {
            ((StringBuilder)object9).append(" (no activity)");
        }
        printWriter.println(((StringBuilder)object9).toString());
        printWriter.print(string2);
        ((StringBuilder)object9).setLength(0);
        ((StringBuilder)object9).append(string2);
        ((StringBuilder)object9).append("  GPS Statistics:");
        printWriter.println(((StringBuilder)object9).toString());
        ((StringBuilder)object9).setLength(0);
        ((StringBuilder)object9).append(string2);
        ((StringBuilder)object9).append("     GPS signal quality (Top 4 Average CN0):");
        object2 = new String[]{"poor (less than 20 dBHz): ", "good (greater than 20 dBHz): "};
        n4 = Math.min(2, ((String[])object2).length);
        for (n3 = 0; n3 < n4; ++n3) {
            l17 = this.getGpsSignalQualityTime(n3, l6, n);
            ((StringBuilder)object9).append("\n    ");
            ((StringBuilder)object9).append(string2);
            ((StringBuilder)object9).append("  ");
            ((StringBuilder)object9).append(object2[n3]);
            BatteryStats.formatTimeMs((StringBuilder)object9, l17 / 1000L);
            ((StringBuilder)object9).append((String)object12);
            ((StringBuilder)object9).append(this.formatRatioLocked(l17, l2));
            ((StringBuilder)object9).append((String)object10);
        }
        object3 = object5;
        printWriter.println(((StringBuilder)object9).toString());
        l17 = this.getGpsBatteryDrainMaMs();
        if (l17 > 0L) {
            printWriter.print(string2);
            ((StringBuilder)object9).setLength(0);
            ((StringBuilder)object9).append(string2);
            ((StringBuilder)object9).append("     GPS Battery Drain: ");
            ((StringBuilder)object9).append(new DecimalFormat("#.##").format((double)l17 / 3600000.0));
            ((StringBuilder)object9).append("mAh");
            printWriter.println(((StringBuilder)object9).toString());
        }
        printWriter.print(string2);
        ((StringBuilder)object9).setLength(0);
        ((StringBuilder)object9).append(string2);
        ((StringBuilder)object9).append("  CONNECTIVITY POWER SUMMARY END");
        printWriter.println(((StringBuilder)object9).toString());
        printWriter.println("");
        printWriter.print(string2);
        printWriter.print("  Bluetooth total received: ");
        printWriter.print(this.formatBytesLocked(l19));
        printWriter.print(", sent: ");
        printWriter.println(this.formatBytesLocked(l10));
        l17 = this.getBluetoothScanTime(l6, n) / 1000L;
        ((StringBuilder)object9).setLength(0);
        ((StringBuilder)object9).append(string2);
        ((StringBuilder)object9).append("  Bluetooth scan time: ");
        BatteryStats.formatTimeMs((StringBuilder)object9, l17);
        printWriter.println(((StringBuilder)object9).toString());
        object5 = this.getBluetoothControllerActivity();
        this.printControllerActivity(printWriter, (StringBuilder)object9, string2, "Bluetooth", (ControllerActivityCounter)object5, n);
        printWriter.println();
        printWriter.print(string2);
        printWriter.println("  Device battery use since last full charge");
        printWriter.print(string2);
        printWriter.print("    Amount discharged (lower bound): ");
        printWriter.println(this.getLowDischargeAmountSinceCharge());
        printWriter.print(string2);
        printWriter.print("    Amount discharged (upper bound): ");
        printWriter.println(this.getHighDischargeAmountSinceCharge());
        printWriter.print(string2);
        printWriter.print("    Amount discharged while screen on: ");
        printWriter.println(this.getDischargeAmountScreenOnSinceCharge());
        printWriter.print(string2);
        printWriter.print("    Amount discharged while screen off: ");
        printWriter.println(this.getDischargeAmountScreenOffSinceCharge());
        printWriter.print(string2);
        printWriter.print("    Amount discharged while screen doze: ");
        printWriter.println(this.getDischargeAmountScreenDozeSinceCharge());
        printWriter.println();
        Object object14 = new BatteryStatsHelper((Context)object, false, bl);
        ((BatteryStatsHelper)object14).create(this);
        ((BatteryStatsHelper)object14).refreshStats(n, -1);
        object = ((BatteryStatsHelper)object14).getUsageList();
        if (object != null && object.size() > 0) {
            printWriter.print(string2);
            printWriter.println("  Estimated power use (mAh):");
            printWriter.print(string2);
            printWriter.print("    Capacity: ");
            this.printmAh(printWriter, ((BatteryStatsHelper)object14).getPowerProfile().getBatteryCapacity());
            printWriter.print(", Computed drain: ");
            this.printmAh(printWriter, ((BatteryStatsHelper)object14).getComputedPower());
            printWriter.print(", actual drain: ");
            this.printmAh(printWriter, ((BatteryStatsHelper)object14).getMinDrainedPower());
            if (((BatteryStatsHelper)object14).getMinDrainedPower() != ((BatteryStatsHelper)object14).getMaxDrainedPower()) {
                printWriter.print("-");
                this.printmAh(printWriter, ((BatteryStatsHelper)object14).getMaxDrainedPower());
            }
            printWriter.println();
            for (n4 = 0; n4 < object.size(); ++n4) {
                object5 = (BatterySipper)object.get(n4);
                printWriter.print(string2);
                switch (object5.drainType) {
                    default: {
                        printWriter.print("    ???: ");
                        break;
                    }
                    case CAMERA: {
                        printWriter.print("    Camera: ");
                        break;
                    }
                    case OVERCOUNTED: {
                        printWriter.print("    Over-counted: ");
                        break;
                    }
                    case UNACCOUNTED: {
                        printWriter.print("    Unaccounted: ");
                        break;
                    }
                    case USER: {
                        printWriter.print("    User ");
                        printWriter.print(object5.userId);
                        printWriter.print(": ");
                        break;
                    }
                    case APP: {
                        printWriter.print("    Uid ");
                        UserHandle.formatUid(printWriter, object5.uidObj.getUid());
                        printWriter.print(": ");
                        break;
                    }
                    case FLASHLIGHT: {
                        printWriter.print("    Flashlight: ");
                        break;
                    }
                    case SCREEN: {
                        printWriter.print("    Screen: ");
                        break;
                    }
                    case BLUETOOTH: {
                        printWriter.print("    Bluetooth: ");
                        break;
                    }
                    case WIFI: {
                        printWriter.print("    Wifi: ");
                        break;
                    }
                    case PHONE: {
                        printWriter.print("    Phone calls: ");
                        break;
                    }
                    case CELL: {
                        printWriter.print("    Cell standby: ");
                        break;
                    }
                    case IDLE: {
                        printWriter.print("    Idle: ");
                        break;
                    }
                    case AMBIENT_DISPLAY: {
                        printWriter.print("    Ambient display: ");
                    }
                }
                this.printmAh(printWriter, object5.totalPowerMah);
                if (object5.usagePowerMah != object5.totalPowerMah) {
                    printWriter.print(" (");
                    if (object5.usagePowerMah != 0.0) {
                        printWriter.print(" usage=");
                        this.printmAh(printWriter, object5.usagePowerMah);
                    }
                    if (object5.cpuPowerMah != 0.0) {
                        printWriter.print(" cpu=");
                        this.printmAh(printWriter, object5.cpuPowerMah);
                    }
                    if (object5.wakeLockPowerMah != 0.0) {
                        printWriter.print(" wake=");
                        this.printmAh(printWriter, object5.wakeLockPowerMah);
                    }
                    if (object5.mobileRadioPowerMah != 0.0) {
                        printWriter.print(" radio=");
                        this.printmAh(printWriter, object5.mobileRadioPowerMah);
                    }
                    if (object5.wifiPowerMah != 0.0) {
                        printWriter.print(" wifi=");
                        this.printmAh(printWriter, object5.wifiPowerMah);
                    }
                    if (object5.bluetoothPowerMah != 0.0) {
                        printWriter.print(" bt=");
                        this.printmAh(printWriter, object5.bluetoothPowerMah);
                    }
                    if (object5.gpsPowerMah != 0.0) {
                        printWriter.print(" gps=");
                        this.printmAh(printWriter, object5.gpsPowerMah);
                    }
                    if (object5.sensorPowerMah != 0.0) {
                        printWriter.print(" sensor=");
                        this.printmAh(printWriter, object5.sensorPowerMah);
                    }
                    if (object5.cameraPowerMah != 0.0) {
                        printWriter.print(" camera=");
                        this.printmAh(printWriter, object5.cameraPowerMah);
                    }
                    if (object5.flashlightPowerMah != 0.0) {
                        printWriter.print(" flash=");
                        this.printmAh(printWriter, object5.flashlightPowerMah);
                    }
                    printWriter.print(" )");
                }
                if (object5.totalSmearedPowerMah != object5.totalPowerMah) {
                    printWriter.print(" Including smearing: ");
                    this.printmAh(printWriter, object5.totalSmearedPowerMah);
                    printWriter.print(" (");
                    if (object5.screenPowerMah != 0.0) {
                        printWriter.print(" screen=");
                        this.printmAh(printWriter, object5.screenPowerMah);
                    }
                    if (object5.proportionalSmearMah != 0.0) {
                        printWriter.print(" proportional=");
                        this.printmAh(printWriter, object5.proportionalSmearMah);
                    }
                    printWriter.print(" )");
                }
                if (object5.shouldHide) {
                    printWriter.print(" Excluded from smearing");
                }
                printWriter.println();
            }
            printWriter.println();
        }
        if ((object = ((BatteryStatsHelper)object14).getMobilemsppList()) != null && object.size() > 0) {
            printWriter.print(string2);
            printWriter.println("  Per-app mobile ms per packet:");
            l18 = 0L;
            for (n4 = 0; n4 < object.size(); ++n4) {
                object5 = (BatterySipper)object.get(n4);
                ((StringBuilder)object9).setLength(0);
                ((StringBuilder)object9).append(string2);
                ((StringBuilder)object9).append("    Uid ");
                UserHandle.formatUid((StringBuilder)object9, object5.uidObj.getUid());
                ((StringBuilder)object9).append(": ");
                ((StringBuilder)object9).append(BatteryStatsHelper.makemAh(object5.mobilemspp));
                ((StringBuilder)object9).append(" (");
                ((StringBuilder)object9).append(object5.mobileRxPackets + object5.mobileTxPackets);
                ((StringBuilder)object9).append(" packets over ");
                BatteryStats.formatTimeMsNoSpace((StringBuilder)object9, object5.mobileActive);
                ((StringBuilder)object9).append((String)object10);
                ((StringBuilder)object9).append(object5.mobileActiveCount);
                ((StringBuilder)object9).append("x");
                printWriter.println(((StringBuilder)object9).toString());
                l18 += object5.mobileActive;
            }
            ((StringBuilder)object9).setLength(0);
            ((StringBuilder)object9).append(string2);
            ((StringBuilder)object9).append("    TOTAL TIME: ");
            BatteryStats.formatTimeMs((StringBuilder)object9, l18);
            ((StringBuilder)object9).append((String)object12);
            ((StringBuilder)object9).append(this.formatRatioLocked(l18, l2));
            ((StringBuilder)object9).append((String)object11);
            printWriter.println(((StringBuilder)object9).toString());
            printWriter.println();
            object5 = object;
        } else {
            object5 = object;
        }
        object = object11;
        object11 = new Comparator<TimerEntry>(){

            @Override
            public int compare(TimerEntry timerEntry, TimerEntry timerEntry2) {
                long l = timerEntry.mTime;
                long l2 = timerEntry2.mTime;
                if (l < l2) {
                    return 1;
                }
                if (l > l2) {
                    return -1;
                }
                return 0;
            }
        };
        if (n2 < 0) {
            Map<String, ? extends Timer> map2 = this.getKernelWakelockStats();
            if (map2.size() > 0) {
                ArrayList<TimerEntry> arrayList = new ArrayList<TimerEntry>();
                for (Map.Entry<String, ? extends Timer> entry : map2.entrySet()) {
                    object4 = entry.getValue();
                    l18 = BatteryStats.computeWakeLock((Timer)object4, l6, n);
                    if (l18 <= 0L) continue;
                    arrayList.add(new TimerEntry(entry.getKey(), 0, (Timer)object4, l18));
                }
                if (arrayList.size() > 0) {
                    object14 = object11;
                    Collections.sort(arrayList, object14);
                    printWriter.print(string2);
                    printWriter.println("  All kernel wake locks:");
                    object11 = object;
                    object = object14;
                    object14 = arrayList;
                    for (n4 = 0; n4 < ((ArrayList)object14).size(); ++n4) {
                        TimerEntry timerEntry = (TimerEntry)((ArrayList)object14).get(n4);
                        ((StringBuilder)object9).setLength(0);
                        ((StringBuilder)object9).append(string2);
                        ((StringBuilder)object9).append("  Kernel Wake lock ");
                        ((StringBuilder)object9).append(timerEntry.mName);
                        if (BatteryStats.printWakeLock((StringBuilder)object9, timerEntry.mTimer, l6, null, n, ": ").equals(": ")) continue;
                        ((StringBuilder)object9).append(" realtime");
                        printWriter.println(((StringBuilder)object9).toString());
                    }
                    printWriter.println();
                } else {
                    object14 = object11;
                    object11 = object;
                    object = object14;
                }
            } else {
                object14 = object;
                object = object11;
                object11 = object14;
            }
            if (((ArrayList)object13).size() > 0) {
                Collections.sort(object13, object);
                printWriter.print(string2);
                printWriter.println("  All partial wake locks:");
                for (n4 = 0; n4 < ((ArrayList)object13).size(); ++n4) {
                    object14 = (TimerEntry)((ArrayList)object13).get(n4);
                    ((StringBuilder)object9).setLength(0);
                    ((StringBuilder)object9).append("  Wake lock ");
                    UserHandle.formatUid((StringBuilder)object9, ((TimerEntry)object14).mId);
                    ((StringBuilder)object9).append((String)object6);
                    ((StringBuilder)object9).append(((TimerEntry)object14).mName);
                    BatteryStats.printWakeLock((StringBuilder)object9, ((TimerEntry)object14).mTimer, l6, null, n, ": ");
                    ((StringBuilder)object9).append(" realtime");
                    printWriter.println(((StringBuilder)object9).toString());
                }
                ((ArrayList)object13).clear();
                printWriter.println();
            }
            if ((object13 = this.getWakeupReasonStats()).size() > 0) {
                printWriter.print(string2);
                printWriter.println("  All wakeup reasons:");
                object14 = new ArrayList<E>();
                for (Map.Entry<K, V> entry : object13.entrySet()) {
                    object13 = (Timer)entry.getValue();
                    ((ArrayList)object14).add(new TimerEntry((String)entry.getKey(), 0, (Timer)object13, ((Timer)object13).getCountLocked(n)));
                }
                object13 = object;
                Collections.sort(object14, object13);
                object = object14;
                for (n4 = 0; n4 < ((ArrayList)object).size(); ++n4) {
                    object14 = (TimerEntry)((ArrayList)object).get(n4);
                    ((StringBuilder)object9).setLength(0);
                    ((StringBuilder)object9).append(string2);
                    ((StringBuilder)object9).append("  Wakeup reason ");
                    ((StringBuilder)object9).append(((TimerEntry)object14).mName);
                    BatteryStats.printWakeLock((StringBuilder)object9, ((TimerEntry)object14).mTimer, l6, null, n, ": ");
                    ((StringBuilder)object9).append(" realtime");
                    printWriter.println(((StringBuilder)object9).toString());
                }
                printWriter.println();
                object = object11;
            } else {
                object = object11;
            }
        }
        if (((LongSparseArray)(object13 = this.getKernelMemoryStats())).size() > 0) {
            printWriter.println("  Memory Stats");
            for (n4 = 0; n4 < ((LongSparseArray)object13).size(); ++n4) {
                ((StringBuilder)object9).setLength(0);
                ((StringBuilder)object9).append("  Bandwidth ");
                ((StringBuilder)object9).append(((LongSparseArray)object13).keyAt(n4));
                ((StringBuilder)object9).append(" Time ");
                ((StringBuilder)object9).append(((Timer)((LongSparseArray)object13).valueAt(n4)).getTotalTimeLocked(l6, n));
                printWriter.println(((StringBuilder)object9).toString());
            }
            printWriter.println();
        }
        if ((object11 = this.getRpmStats()).size() > 0) {
            printWriter.print(string2);
            printWriter.println("  Resource Power Manager Stats");
            if (object11.size() > 0) {
                for (Map.Entry entry : object11.entrySet()) {
                    object14 = (String)entry.getKey();
                    BatteryStats.printTimer(printWriter, (StringBuilder)object9, (Timer)entry.getValue(), l6, n, string2, object14);
                }
                l3 = l6;
                l6 = l2;
                object11 = object10;
                object10 = object;
                l9 = l7;
                l2 = l3;
                object = object9;
                l7 = l6;
            } else {
                l9 = l7;
                l7 = l2;
                object11 = object10;
                object10 = object;
                object = object9;
                l2 = l6;
            }
            printWriter.println();
            l3 = l7;
        } else {
            l3 = l2;
            object11 = object10;
            object10 = object;
            object = object9;
            l2 = l6;
            l9 = l7;
        }
        object5 = this.getCpuFreqs();
        if (object5 != null) {
            object9 = object;
            ((StringBuilder)object9).setLength(0);
            ((StringBuilder)object9).append("  CPU freqs:");
            for (n4 = 0; n4 < ((String[])object5).length; ++n4) {
                object13 = new StringBuilder();
                ((StringBuilder)object13).append((String)object6);
                ((StringBuilder)object13).append((long)object5[n4]);
                ((StringBuilder)object9).append(((StringBuilder)object13).toString());
            }
            printWriter.println(((StringBuilder)object9).toString());
            printWriter.println();
        }
        object3 = object;
        object9 = object6;
        object13 = printWriter;
        n3 = n5;
        object6 = object11;
        object = object10;
        l7 = l4;
        l6 = l5;
        object2 = object8;
        object10 = object3;
        object11 = object13;
        for (n7 = 0; n7 < n3; ++n7) {
            n5 = ((SparseArray)object2).keyAt(n7);
            if (n2 >= 0 && n5 != n2 && n5 != 1000) {
                l8 = l2;
                object13 = object12;
                object8 = object9;
                object9 = object10;
                l2 = l6;
                l6 = l7;
                object10 = object;
                object12 = object6;
                object = object9;
                object9 = object11;
                l7 = l9;
                object6 = object10;
                object11 = object13;
                l9 = l8;
                object10 = object8;
            } else {
                object8 = (Uid)((SparseArray)object2).valueAt(n7);
                printWriter.print(string2);
                ((PrintWriter)object11).print("  ");
                UserHandle.formatUid((PrintWriter)object11, n5);
                ((PrintWriter)object11).println(":");
                n5 = 0;
                long l20 = ((Uid)object8).getNetworkActivityBytes(0, n);
                long l21 = ((Uid)object8).getNetworkActivityBytes(1, n);
                l15 = ((Uid)object8).getNetworkActivityBytes(2, n);
                l19 = ((Uid)object8).getNetworkActivityBytes(3, n);
                l10 = ((Uid)object8).getNetworkActivityBytes(4, n);
                l13 = ((Uid)object8).getNetworkActivityBytes(5, n);
                l8 = ((Uid)object8).getNetworkActivityPackets(0, n);
                long l22 = ((Uid)object8).getNetworkActivityPackets(1, n);
                l11 = ((Uid)object8).getNetworkActivityPackets(2, n);
                l18 = ((Uid)object8).getNetworkActivityPackets(3, n);
                l16 = ((Uid)object8).getMobileRadioActiveTime(n);
                int n8 = ((Uid)object8).getMobileRadioActiveCount(n);
                l5 = ((Uid)object8).getFullWifiLockTime(l2, n);
                l4 = ((Uid)object8).getWifiScanTime(l2, n);
                n4 = ((Uid)object8).getWifiScanCount(n);
                n6 = ((Uid)object8).getWifiScanBackgroundCount(n);
                l17 = ((Uid)object8).getWifiScanActualTime(l2);
                l = ((Uid)object8).getWifiScanBackgroundTime(l2);
                l12 = ((Uid)object8).getWifiRunningTime(l2, n);
                long l23 = ((Uid)object8).getMobileRadioApWakeupCount(n);
                l14 = ((Uid)object8).getWifiRadioApWakeupCount(n);
                if (l20 > 0L || l21 > 0L || l8 > 0L || l22 > 0L) {
                    printWriter.print(string2);
                    ((PrintWriter)object11).print("    Mobile network: ");
                    object13 = this;
                    ((PrintWriter)object11).print(((BatteryStats)object13).formatBytesLocked(l20));
                    ((PrintWriter)object11).print(" received, ");
                    ((PrintWriter)object11).print(((BatteryStats)object13).formatBytesLocked(l21));
                    ((PrintWriter)object11).print(" sent (packets ");
                    ((PrintWriter)object11).print(l8);
                    ((PrintWriter)object11).print(" received, ");
                    ((PrintWriter)object11).print(l22);
                    ((PrintWriter)object11).println(" sent)");
                }
                if (l16 > 0L || n8 > 0) {
                    object13 = object10;
                    ((StringBuilder)object13).setLength(0);
                    ((StringBuilder)object13).append(string2);
                    ((StringBuilder)object13).append("    Mobile radio active: ");
                    BatteryStats.formatTimeMs((StringBuilder)object13, l16 / 1000L);
                    ((StringBuilder)object13).append((String)object12);
                    ((StringBuilder)object13).append(this.formatRatioLocked(l16, l9));
                    ((StringBuilder)object13).append((String)object6);
                    ((StringBuilder)object13).append(n8);
                    ((StringBuilder)object13).append("x");
                    if ((l8 += l22) == 0L) {
                        l8 = 1L;
                    }
                    ((StringBuilder)object13).append(" @ ");
                    ((StringBuilder)object13).append(BatteryStatsHelper.makemAh((double)(l16 / 1000L) / (double)l8));
                    ((StringBuilder)object13).append(" mspp");
                    ((PrintWriter)object11).println(((StringBuilder)object13).toString());
                }
                object13 = object10;
                object10 = string2;
                if (l23 > 0L) {
                    ((StringBuilder)object13).setLength(0);
                    ((StringBuilder)object13).append((String)object10);
                    ((StringBuilder)object13).append("    Mobile radio AP wakeups: ");
                    ((StringBuilder)object13).append(l23);
                    ((PrintWriter)object11).println(((StringBuilder)object13).toString());
                }
                object3 = new StringBuilder();
                ((StringBuilder)object3).append((String)object10);
                ((StringBuilder)object3).append("  ");
                object3 = ((StringBuilder)object3).toString();
                object14 = ((Uid)object8).getModemControllerActivity();
                l8 = l9;
                object10 = object8;
                this.printControllerActivityIfInteresting(printWriter, (StringBuilder)object13, (String)object3, "Cellular", (ControllerActivityCounter)object14, n);
                if (l15 > 0L || l19 > 0L || l11 > 0L || l18 > 0L) {
                    printWriter.print(string2);
                    ((PrintWriter)object11).print("    Wi-Fi network: ");
                    ((PrintWriter)object11).print(this.formatBytesLocked(l15));
                    ((PrintWriter)object11).print(" received, ");
                    ((PrintWriter)object11).print(this.formatBytesLocked(l19));
                    ((PrintWriter)object11).print(" sent (packets ");
                    ((PrintWriter)object11).print(l11);
                    ((PrintWriter)object11).print(" received, ");
                    ((PrintWriter)object11).print(l18);
                    ((PrintWriter)object11).println(" sent)");
                }
                if (l5 == 0L && (l11 = l4) == 0L && n4 == 0 && n6 == 0 && l17 == 0L && l == 0L && (l9 = l12) == 0L) {
                    object3 = object11;
                    l4 = l9;
                } else {
                    l9 = l4;
                    object11 = object13;
                    ((StringBuilder)object11).setLength(0);
                    object8 = string2;
                    ((StringBuilder)object11).append((String)object8);
                    ((StringBuilder)object11).append("    Wifi Running: ");
                    BatteryStats.formatTimeMs((StringBuilder)object11, l12 / 1000L);
                    object14 = object12;
                    ((StringBuilder)object11).append((String)object14);
                    ((StringBuilder)object11).append(this.formatRatioLocked(l12, l3));
                    ((StringBuilder)object11).append(")\n");
                    ((StringBuilder)object11).append((String)object8);
                    ((StringBuilder)object11).append("    Full Wifi Lock: ");
                    l4 = l12;
                    BatteryStats.formatTimeMs((StringBuilder)object11, l5 / 1000L);
                    ((StringBuilder)object11).append((String)object14);
                    ((StringBuilder)object11).append(this.formatRatioLocked(l5, l3));
                    ((StringBuilder)object11).append(")\n");
                    ((StringBuilder)object11).append((String)object8);
                    ((StringBuilder)object11).append("    Wifi Scan (blamed): ");
                    BatteryStats.formatTimeMs((StringBuilder)object11, l9 / 1000L);
                    ((StringBuilder)object11).append((String)object14);
                    ((StringBuilder)object11).append(this.formatRatioLocked(l9, l3));
                    object3 = object6;
                    ((StringBuilder)object11).append((String)object3);
                    ((StringBuilder)object11).append(n4);
                    ((StringBuilder)object11).append("x\n");
                    ((StringBuilder)object11).append((String)object8);
                    ((StringBuilder)object11).append("    Wifi Scan (actual): ");
                    BatteryStats.formatTimeMs((StringBuilder)object11, l17 / 1000L);
                    ((StringBuilder)object11).append((String)object14);
                    l12 = l2;
                    ((StringBuilder)object11).append(this.formatRatioLocked(l17, this.computeBatteryRealtime(l12, 0)));
                    ((StringBuilder)object11).append((String)object3);
                    ((StringBuilder)object11).append(n4);
                    ((StringBuilder)object11).append("x\n");
                    ((StringBuilder)object11).append((String)object8);
                    ((StringBuilder)object11).append("    Background Wifi Scan: ");
                    BatteryStats.formatTimeMs((StringBuilder)object11, l / 1000L);
                    ((StringBuilder)object11).append((String)object14);
                    ((StringBuilder)object11).append(this.formatRatioLocked(l, this.computeBatteryRealtime(l12, 0)));
                    ((StringBuilder)object11).append((String)object3);
                    ((StringBuilder)object11).append(n6);
                    ((StringBuilder)object11).append("x");
                    object11 = ((StringBuilder)object11).toString();
                    object3 = printWriter;
                    ((PrintWriter)object3).println((String)object11);
                    l11 = l9;
                }
                object8 = object6;
                object14 = string2;
                l5 = l2;
                if (l14 > 0L) {
                    ((StringBuilder)object13).setLength(0);
                    ((StringBuilder)object13).append((String)object14);
                    ((StringBuilder)object13).append("    WiFi AP wakeups: ");
                    ((StringBuilder)object13).append(l14);
                    ((PrintWriter)object3).println(((StringBuilder)object13).toString());
                }
                ArrayMap<String, ? extends Timer> arrayMap = object12;
                object12 = new StringBuilder();
                ((StringBuilder)object12).append((String)object14);
                ((StringBuilder)object12).append("  ");
                this.printControllerActivityIfInteresting(printWriter, (StringBuilder)object13, ((StringBuilder)object12).toString(), "WiFi", ((Uid)object10).getWifiControllerActivity(), n);
                if (l10 > 0L || l13 > 0L) {
                    printWriter.print(string2);
                    ((PrintWriter)object3).print("    Bluetooth network: ");
                    ((PrintWriter)object3).print(this.formatBytesLocked(l10));
                    ((PrintWriter)object3).print(" received, ");
                    ((PrintWriter)object3).print(this.formatBytesLocked(l13));
                    ((PrintWriter)object3).println(" sent");
                }
                l9 = l13;
                l4 = l10;
                Timer timer = ((Uid)object10).getBluetoothScanTimer();
                if (timer != null && (l14 = (timer.getTotalTimeLocked(l5, n) + 500L) / 1000L) != 0L) {
                    n8 = timer.getCountLocked(n);
                    object6 = ((Uid)object10).getBluetoothScanBackgroundTimer();
                    n5 = object6 != null ? ((Timer)object6).getCountLocked(n) : 0;
                    l = timer.getTotalDurationMsLocked(l7);
                    l12 = object6 != null ? ((Timer)object6).getTotalDurationMsLocked(l7) : 0L;
                    n4 = ((Uid)object10).getBluetoothScanResultCounter() != null ? ((Uid)object10).getBluetoothScanResultCounter().getCountLocked(n) : 0;
                    n6 = ((Uid)object10).getBluetoothScanResultBgCounter() != null ? ((Uid)object10).getBluetoothScanResultBgCounter().getCountLocked(n) : 0;
                    Timer timer2 = ((Uid)object10).getBluetoothUnoptimizedScanTimer();
                    l11 = timer2 != null ? timer2.getTotalDurationMsLocked(l7) : 0L;
                    l10 = timer2 != null ? timer2.getMaxDurationMsLocked(l7) : 0L;
                    object11 = ((Uid)object10).getBluetoothUnoptimizedScanBackgroundTimer();
                    l2 = object11 != null ? ((Timer)object11).getTotalDurationMsLocked(l7) : 0L;
                    l13 = object11 != null ? ((Timer)object11).getMaxDurationMsLocked(l7) : 0L;
                    object12 = object13;
                    ((StringBuilder)object12).setLength(0);
                    if (l != l14) {
                        ((StringBuilder)object12).append((String)object14);
                        ((StringBuilder)object12).append("    Bluetooth Scan (total blamed realtime): ");
                        BatteryStats.formatTimeMs((StringBuilder)object12, l14);
                        ((StringBuilder)object12).append(" (");
                        ((StringBuilder)object12).append(n8);
                        ((StringBuilder)object12).append(" times)");
                        if (timer.isRunningLocked()) {
                            ((StringBuilder)object12).append(" (currently running)");
                        }
                        ((StringBuilder)object12).append("\n");
                    }
                    ((StringBuilder)object12).append((String)object14);
                    ((StringBuilder)object12).append("    Bluetooth Scan (total actual realtime): ");
                    BatteryStats.formatTimeMs((StringBuilder)object12, l);
                    ((StringBuilder)object12).append(" (");
                    ((StringBuilder)object12).append(n8);
                    ((StringBuilder)object12).append(" times)");
                    if (timer.isRunningLocked()) {
                        ((StringBuilder)object12).append(" (currently running)");
                    }
                    ((StringBuilder)object12).append("\n");
                    if (l12 > 0L || n5 > 0) {
                        ((StringBuilder)object12).append((String)object14);
                        ((StringBuilder)object12).append("    Bluetooth Scan (background realtime): ");
                        BatteryStats.formatTimeMs((StringBuilder)object12, l12);
                        ((StringBuilder)object12).append(" (");
                        ((StringBuilder)object12).append(n5);
                        ((StringBuilder)object12).append(" times)");
                        if (object6 != null && ((Timer)object6).isRunningLocked()) {
                            ((StringBuilder)object12).append(" (currently running in background)");
                        }
                        ((StringBuilder)object12).append("\n");
                    }
                    ((StringBuilder)object12).append((String)object14);
                    ((StringBuilder)object12).append("    Bluetooth Scan Results: ");
                    ((StringBuilder)object12).append(n4);
                    ((StringBuilder)object12).append(" (");
                    ((StringBuilder)object12).append(n6);
                    ((StringBuilder)object12).append(" in background)");
                    if (l11 > 0L || l2 > 0L) {
                        ((StringBuilder)object12).append("\n");
                        ((StringBuilder)object12).append((String)object14);
                        ((StringBuilder)object12).append("    Unoptimized Bluetooth Scan (realtime): ");
                        BatteryStats.formatTimeMs((StringBuilder)object12, l11);
                        ((StringBuilder)object12).append(" (max ");
                        BatteryStats.formatTimeMs((StringBuilder)object12, l10);
                        object6 = object;
                        ((StringBuilder)object12).append((String)object6);
                        if (timer2 != null && timer2.isRunningLocked()) {
                            ((StringBuilder)object12).append(" (currently running unoptimized)");
                        }
                        if (object11 != null && l2 > 0L) {
                            ((StringBuilder)object12).append("\n");
                            ((StringBuilder)object12).append((String)object14);
                            ((StringBuilder)object12).append("    Unoptimized Bluetooth Scan (background realtime): ");
                            BatteryStats.formatTimeMs((StringBuilder)object12, l2);
                            ((StringBuilder)object12).append(" (max ");
                            BatteryStats.formatTimeMs((StringBuilder)object12, l13);
                            ((StringBuilder)object12).append((String)object6);
                            if (((Timer)object11).isRunningLocked()) {
                                ((StringBuilder)object12).append(" (currently running unoptimized in background)");
                            }
                        }
                    }
                    ((PrintWriter)object3).println(((StringBuilder)object12).toString());
                    n5 = 1;
                }
                l10 = l9;
                l11 = l4;
                object4 = " times)";
                Object object7 = object13;
                object11 = object;
                bl = ((Uid)object10).hasUserActivity();
                object = ", ";
                if (bl) {
                    n4 = 0;
                    for (n6 = 0; n6 < Uid.NUM_USER_ACTIVITY_TYPES; ++n6) {
                        n8 = ((Uid)object10).getUserActivityCount(n6, n);
                        if (n8 == 0) continue;
                        if (n4 == 0) {
                            ((StringBuilder)object7).setLength(0);
                            ((StringBuilder)object7).append("    User activity: ");
                            n4 = 1;
                        } else {
                            ((StringBuilder)object7).append(", ");
                        }
                        ((StringBuilder)object7).append(n8);
                        ((StringBuilder)object7).append((String)object9);
                        ((StringBuilder)object7).append(Uid.USER_ACTIVITY_TYPES[n6]);
                    }
                    object6 = object9;
                    object12 = object10;
                    if (n4 != 0) {
                        ((PrintWriter)object3).println(((StringBuilder)object7).toString());
                        object6 = object9;
                        object12 = object10;
                    }
                } else {
                    object12 = object10;
                    object6 = object9;
                }
                n8 = n;
                object9 = ((Uid)object12).getWakelockStats();
                n4 = 0;
                n6 = ((ArrayMap)object9).size() - 1;
                l9 = 0L;
                l4 = 0L;
                l2 = 0L;
                l12 = 0L;
                object10 = object4;
                while (n6 >= 0) {
                    object4 = (Uid.Wakelock)((ArrayMap)object9).valueAt(n6);
                    ((StringBuilder)object7).setLength(0);
                    ((StringBuilder)object7).append((String)object14);
                    ((StringBuilder)object7).append("    Wake lock ");
                    ((StringBuilder)object7).append((String)((ArrayMap)object9).keyAt(n6));
                    String string3 = BatteryStats.printWakeLock((StringBuilder)object7, ((Uid.Wakelock)object4).getWakeTime(1), l5, "full", n, ": ");
                    object13 = ((Uid.Wakelock)object4).getWakeTime(0);
                    string3 = BatteryStats.printWakeLock((StringBuilder)object7, (Timer)object13, l5, "partial", n, string3);
                    object13 = object13 != null ? ((Timer)object13).getSubTimer() : null;
                    object13 = BatteryStats.printWakeLock((StringBuilder)object7, (Timer)object13, l5, "background partial", n, string3);
                    object13 = BatteryStats.printWakeLock((StringBuilder)object7, ((Uid.Wakelock)object4).getWakeTime(2), l5, "window", n, (String)object13);
                    BatteryStats.printWakeLock((StringBuilder)object7, ((Uid.Wakelock)object4).getWakeTime(18), l5, "draw", n, (String)object13);
                    ((StringBuilder)object7).append(" realtime");
                    ((PrintWriter)object3).println(((StringBuilder)object7).toString());
                    n5 = 1;
                    l14 = BatteryStats.computeWakeLock(((Uid.Wakelock)object4).getWakeTime(1), l5, n8);
                    l13 = BatteryStats.computeWakeLock(((Uid.Wakelock)object4).getWakeTime(0), l5, n8);
                    l9 += BatteryStats.computeWakeLock(((Uid.Wakelock)object4).getWakeTime(2), l5, n8);
                    l4 += BatteryStats.computeWakeLock(((Uid.Wakelock)object4).getWakeTime(18), l5, n8);
                    ++n4;
                    l12 = l14 + l12;
                    --n6;
                    l2 += l13;
                }
                object13 = object11;
                object11 = object6;
                n8 = n4;
                l11 = l9;
                if (n8 > 1) {
                    if (((Uid)object12).getAggregatedPartialWakelockTimer() != null) {
                        object6 = ((Uid)object12).getAggregatedPartialWakelockTimer();
                        l13 = ((Timer)object6).getTotalDurationMsLocked(l7);
                        l9 = (object6 = ((Timer)object6).getSubTimer()) != null ? ((Timer)object6).getTotalDurationMsLocked(l7) : 0L;
                        l10 = l9;
                        l9 = l13;
                    } else {
                        l9 = 0L;
                        l10 = 0L;
                    }
                    if (l9 != 0L || l10 != 0L || l12 != 0L || l2 != 0L || l11 != 0L) {
                        ((StringBuilder)object7).setLength(0);
                        ((StringBuilder)object7).append((String)object14);
                        ((StringBuilder)object7).append("    TOTAL wake: ");
                        n6 = 0;
                        if (l12 != 0L) {
                            n6 = 1;
                            BatteryStats.formatTimeMs((StringBuilder)object7, l12);
                            ((StringBuilder)object7).append("full");
                        }
                        if (l2 != 0L) {
                            if (n6 != 0) {
                                ((StringBuilder)object7).append((String)object);
                            }
                            BatteryStats.formatTimeMs((StringBuilder)object7, l2);
                            ((StringBuilder)object7).append("blamed partial");
                            n6 = 1;
                        }
                        object6 = object;
                        if (l9 != 0L) {
                            if (n6 != 0) {
                                ((StringBuilder)object7).append((String)object6);
                            }
                            n6 = 1;
                            BatteryStats.formatTimeMs((StringBuilder)object7, l9);
                            ((StringBuilder)object7).append("actual partial");
                        }
                        n4 = n6;
                        if (l10 != 0L) {
                            if (n6 != 0) {
                                ((StringBuilder)object7).append((String)object6);
                            }
                            n4 = 1;
                            BatteryStats.formatTimeMs((StringBuilder)object7, l10);
                            ((StringBuilder)object7).append("actual background partial");
                        }
                        n6 = n4;
                        if (l11 != 0L) {
                            if (n4 != 0) {
                                ((StringBuilder)object7).append((String)object6);
                            }
                            n6 = 1;
                            BatteryStats.formatTimeMs((StringBuilder)object7, l11);
                            ((StringBuilder)object7).append("window");
                        }
                        if (l4 != 0L) {
                            if (n6 != 0) {
                                ((StringBuilder)object7).append(",");
                            }
                            BatteryStats.formatTimeMs((StringBuilder)object7, l4);
                            ((StringBuilder)object7).append("draw");
                        }
                        ((StringBuilder)object7).append(" realtime");
                        ((PrintWriter)object3).println(((StringBuilder)object7).toString());
                    }
                }
                object9 = object12;
                l9 = l2;
                object6 = object;
                object12 = ((Uid)object9).getMulticastWakelockStats();
                if (object12 != null) {
                    n4 = n;
                    l2 = ((Timer)object12).getTotalTimeLocked(l5, n4);
                    n4 = ((Timer)object12).getCountLocked(n4);
                    if (l2 > 0L) {
                        ((StringBuilder)object7).setLength(0);
                        ((StringBuilder)object7).append((String)object14);
                        ((StringBuilder)object7).append("    WiFi Multicast Wakelock");
                        ((StringBuilder)object7).append(" count = ");
                        ((StringBuilder)object7).append(n4);
                        ((StringBuilder)object7).append(" time = ");
                        BatteryStats.formatTimeMsNoSpace((StringBuilder)object7, (l2 + 500L) / 1000L);
                        ((PrintWriter)object3).println(((StringBuilder)object7).toString());
                    }
                }
                l2 = l5;
                int n9 = n;
                ArrayMap<String, ? extends Timer> arrayMap2 = ((Uid)object9).getSyncStats();
                object = object10;
                object10 = arrayMap2;
                for (n4 = arrayMap2.size() - 1; n4 >= 0; --n4) {
                    Timer timer3 = (Timer)((ArrayMap)object10).valueAt(n4);
                    l4 = (timer3.getTotalTimeLocked(l2, n9) + 500L) / 1000L;
                    n6 = timer3.getCountLocked(n9);
                    Timer timer4 = timer3.getSubTimer();
                    l5 = timer4 != null ? timer4.getTotalDurationMsLocked(l7) : -1L;
                    n5 = timer4 != null ? timer4.getCountLocked(n9) : -1;
                    ((StringBuilder)object7).setLength(0);
                    ((StringBuilder)object7).append((String)object14);
                    ((StringBuilder)object7).append("    Sync ");
                    ((StringBuilder)object7).append((String)((ArrayMap)object10).keyAt(n4));
                    ((StringBuilder)object7).append(": ");
                    if (l4 != 0L) {
                        BatteryStats.formatTimeMs((StringBuilder)object7, l4);
                        ((StringBuilder)object7).append("realtime (");
                        ((StringBuilder)object7).append(n6);
                        Object object15 = object;
                        ((StringBuilder)object7).append((String)object15);
                        if (l5 > 0L) {
                            ((StringBuilder)object7).append((String)object6);
                            BatteryStats.formatTimeMs((StringBuilder)object7, l5);
                            ((StringBuilder)object7).append("background (");
                            ((StringBuilder)object7).append(n5);
                            ((StringBuilder)object7).append((String)object15);
                        }
                    } else {
                        ((StringBuilder)object7).append("(not used)");
                    }
                    ((PrintWriter)object3).println(((StringBuilder)object7).toString());
                    n5 = 1;
                }
                object12 = ((Uid)object9).getJobStats();
                for (n4 = object12.size() - 1; n4 >= 0; --n4) {
                    object10 = ((ArrayMap)object12).valueAt(n4);
                    l5 = (((Timer)object10).getTotalTimeLocked(l2, n9) + 500L) / 1000L;
                    n6 = ((Timer)object10).getCountLocked(n9);
                    l9 = (object10 = ((Timer)object10).getSubTimer()) != null ? ((Timer)object10).getTotalDurationMsLocked(l7) : -1L;
                    n5 = object10 != null ? ((Timer)object10).getCountLocked(n9) : -1;
                    ((StringBuilder)object7).setLength(0);
                    ((StringBuilder)object7).append((String)object14);
                    ((StringBuilder)object7).append("    Job ");
                    ((StringBuilder)object7).append((String)((ArrayMap)object12).keyAt(n4));
                    ((StringBuilder)object7).append(": ");
                    if (l5 != 0L) {
                        BatteryStats.formatTimeMs((StringBuilder)object7, l5);
                        ((StringBuilder)object7).append("realtime (");
                        ((StringBuilder)object7).append(n6);
                        ((StringBuilder)object7).append((String)object);
                        if (l9 > 0L) {
                            ((StringBuilder)object7).append((String)object6);
                            BatteryStats.formatTimeMs((StringBuilder)object7, l9);
                            ((StringBuilder)object7).append("background (");
                            ((StringBuilder)object7).append(n5);
                            ((StringBuilder)object7).append((String)object);
                        }
                    } else {
                        ((StringBuilder)object7).append("(not used)");
                    }
                    ((PrintWriter)object3).println(((StringBuilder)object7).toString());
                    n5 = 1;
                }
                ArrayMap<String, SparseIntArray> arrayMap3 = ((Uid)object9).getJobCompletionStats();
                object10 = arrayMap;
                object12 = object11;
                for (n4 = arrayMap3.size() - 1; n4 >= 0; --n4) {
                    object11 = arrayMap3.valueAt(n4);
                    if (object11 == null) continue;
                    printWriter.print(string2);
                    ((PrintWriter)object3).print("    Job Completions ");
                    ((PrintWriter)object3).print(arrayMap3.keyAt(n4));
                    ((PrintWriter)object3).print(":");
                    for (n6 = 0; n6 < ((SparseIntArray)object11).size(); ++n6) {
                        ((PrintWriter)object3).print((String)object12);
                        ((PrintWriter)object3).print(JobParameters.getReasonName(((SparseIntArray)object11).keyAt(n6)));
                        ((PrintWriter)object3).print((String)object10);
                        ((PrintWriter)object3).print(((SparseIntArray)object11).valueAt(n6));
                        ((PrintWriter)object3).print("x)");
                    }
                    printWriter.println();
                }
                ((Uid)object9).getDeferredJobsLineLocked((StringBuilder)object7, n9);
                if (((StringBuilder)object7).length() > 0) {
                    ((PrintWriter)object3).print("    Jobs deferred on launch ");
                    ((PrintWriter)object3).println(((StringBuilder)object7).toString());
                }
                object4 = ((Uid)object9).getFlashlightTurnedOnTimer();
                Object object16 = object7;
                object11 = printWriter;
                Object object17 = object9;
                l9 = l7;
                object3 = object10;
                l7 = l2;
                n8 = n5 | BatteryStats.printTimer(printWriter, (StringBuilder)object7, (Timer)object4, l2, n, string2, "Flashlight") | BatteryStats.printTimer(printWriter, (StringBuilder)object16, ((Uid)object17).getCameraTurnedOnTimer(), l7, n, string2, "Camera") | BatteryStats.printTimer(printWriter, (StringBuilder)object16, ((Uid)object17).getVideoTurnedOnTimer(), l7, n, string2, "Video") | BatteryStats.printTimer(printWriter, (StringBuilder)object16, ((Uid)object17).getAudioTurnedOnTimer(), l7, n, string2, "Audio");
                SparseArray<? extends Uid.Sensor> sparseArray = ((Uid)object17).getSensorStats();
                n5 = sparseArray.size();
                object10 = object16;
                l2 = l9;
                for (n4 = 0; n4 < n5; ++n4) {
                    Uid.Sensor sensor = sparseArray.valueAt(n4);
                    sparseArray.keyAt(n4);
                    ((StringBuilder)object10).setLength(0);
                    ((StringBuilder)object10).append((String)object14);
                    ((StringBuilder)object10).append("    Sensor ");
                    n6 = sensor.getHandle();
                    if (n6 == -10000) {
                        ((StringBuilder)object10).append("GPS");
                    } else {
                        ((StringBuilder)object10).append(n6);
                    }
                    ((StringBuilder)object10).append(": ");
                    object9 = sensor.getSensorTime();
                    if (object9 != null) {
                        l5 = (((Timer)object9).getTotalTimeLocked(l7, n9) + 500L) / 1000L;
                        n8 = ((Timer)object9).getCountLocked(n9);
                        object14 = sensor.getSensorBackgroundTime();
                        n6 = object14 != null ? ((Timer)object14).getCountLocked(n9) : 0;
                        l4 = ((Timer)object9).getTotalDurationMsLocked(l2);
                        l9 = object14 != null ? ((Timer)object14).getTotalDurationMsLocked(l2) : 0L;
                        if (l5 != 0L) {
                            if (l4 != l5) {
                                BatteryStats.formatTimeMs((StringBuilder)object10, l5);
                                ((StringBuilder)object10).append("blamed realtime, ");
                            }
                            BatteryStats.formatTimeMs((StringBuilder)object10, l4);
                            ((StringBuilder)object10).append("realtime (");
                            ((StringBuilder)object10).append(n8);
                            ((StringBuilder)object10).append((String)object);
                            if (l9 != 0L || n6 > 0) {
                                ((StringBuilder)object10).append((String)object6);
                                BatteryStats.formatTimeMs((StringBuilder)object10, l9);
                                ((StringBuilder)object10).append("background (");
                                ((StringBuilder)object10).append(n6);
                                ((StringBuilder)object10).append((String)object);
                            }
                        } else {
                            ((StringBuilder)object10).append("(not used)");
                        }
                    } else {
                        ((StringBuilder)object10).append("(not used)");
                    }
                    ((PrintWriter)object11).println(((StringBuilder)object10).toString());
                    object14 = string2;
                    n8 = 1;
                }
                l5 = l2;
                object9 = object6;
                object = ((Uid)object17).getVibratorOnTimer();
                object14 = object10;
                n6 = n5;
                object6 = sparseArray;
                n5 = n8 | BatteryStats.printTimer(printWriter, (StringBuilder)object10, (Timer)object, l7, n, string2, "Vibrator") | BatteryStats.printTimer(printWriter, (StringBuilder)object14, ((Uid)object17).getForegroundActivityTimer(), l7, n, string2, "Foreground activities") | BatteryStats.printTimer(printWriter, (StringBuilder)object14, ((Uid)object17).getForegroundServiceTimer(), l7, n, string2, "Foreground services");
                l2 = 0L;
                object = object17;
                for (n4 = 0; n4 < 7; ++n4) {
                    l9 = ((Uid)object).getProcessStateTime(n4, l7, n9);
                    if (l9 <= 0L) continue;
                    ((StringBuilder)object14).setLength(0);
                    ((StringBuilder)object14).append(string2);
                    ((StringBuilder)object14).append("    ");
                    ((StringBuilder)object14).append(Uid.PROCESS_STATE_NAMES[n4]);
                    ((StringBuilder)object14).append(" for: ");
                    BatteryStats.formatTimeMs((StringBuilder)object14, (l9 + 500L) / 1000L);
                    ((PrintWriter)object11).println(((StringBuilder)object14).toString());
                    n5 = 1;
                    l2 += l9;
                }
                Object object18 = object;
                l4 = l7;
                if (l2 > 0L) {
                    ((StringBuilder)object14).setLength(0);
                    ((StringBuilder)object14).append(string2);
                    ((StringBuilder)object14).append("    Total running: ");
                    BatteryStats.formatTimeMs((StringBuilder)object14, (l2 + 500L) / 1000L);
                    ((PrintWriter)object11).println(((StringBuilder)object14).toString());
                }
                l7 = ((Uid)object18).getUserCpuTimeUs(n9);
                l2 = ((Uid)object18).getSystemCpuTimeUs(n9);
                if (l7 > 0L || l2 > 0L) {
                    ((StringBuilder)object14).setLength(0);
                    ((StringBuilder)object14).append(string2);
                    ((StringBuilder)object14).append("    Total cpu time: u=");
                    BatteryStats.formatTimeMs((StringBuilder)object14, l7 / 1000L);
                    ((StringBuilder)object14).append("s=");
                    BatteryStats.formatTimeMs((StringBuilder)object14, l2 / 1000L);
                    ((PrintWriter)object11).println(((StringBuilder)object14).toString());
                }
                object10 = ((Uid)object18).getCpuFreqTimes(n9);
                if (object10 != null) {
                    ((StringBuilder)object14).setLength(0);
                    ((StringBuilder)object14).append("    Total cpu time per freq:");
                    for (n4 = 0; n4 < ((Object)object10).length; ++n4) {
                        object = new StringBuilder();
                        ((StringBuilder)object).append((String)object12);
                        ((StringBuilder)object).append((long)object10[n4]);
                        ((StringBuilder)object14).append(((StringBuilder)object).toString());
                    }
                    object = object12;
                    ((PrintWriter)object11).println(((StringBuilder)object14).toString());
                } else {
                    object = object12;
                }
                object12 = ((Uid)object18).getScreenOffCpuFreqTimes(n9);
                if (object12 != null) {
                    ((StringBuilder)object14).setLength(0);
                    ((StringBuilder)object14).append("    Total screen-off cpu time per freq:");
                    for (n4 = 0; n4 < ((long[])object12).length; ++n4) {
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append((String)object);
                        stringBuilder.append(object12[n4]);
                        ((StringBuilder)object14).append(stringBuilder.toString());
                    }
                    ((PrintWriter)object11).println(((StringBuilder)object14).toString());
                }
                for (n4 = 0; n4 < 7; ++n4) {
                    long[] arrl;
                    long[] arrl2 = ((Uid)object18).getCpuFreqTimes(n9, n4);
                    if (arrl2 != null) {
                        ((StringBuilder)object14).setLength(0);
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("    Cpu times per freq at state ");
                        stringBuilder.append(Uid.PROCESS_STATE_NAMES[n4]);
                        stringBuilder.append(":");
                        ((StringBuilder)object14).append(stringBuilder.toString());
                        for (n8 = 0; n8 < arrl2.length; ++n8) {
                            StringBuilder stringBuilder2 = new StringBuilder();
                            stringBuilder2.append((String)object);
                            stringBuilder2.append(arrl2[n8]);
                            ((StringBuilder)object14).append(stringBuilder2.toString());
                        }
                        ((PrintWriter)object11).println(((StringBuilder)object14).toString());
                    }
                    if ((arrl = ((Uid)object18).getScreenOffCpuFreqTimes(n9, n4)) == null) continue;
                    ((StringBuilder)object14).setLength(0);
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("   Screen-off cpu times per freq at state ");
                    stringBuilder.append(Uid.PROCESS_STATE_NAMES[n4]);
                    stringBuilder.append(":");
                    ((StringBuilder)object14).append(stringBuilder.toString());
                    for (n8 = 0; n8 < arrl.length; ++n8) {
                        StringBuilder stringBuilder3 = new StringBuilder();
                        stringBuilder3.append((String)object);
                        stringBuilder3.append(arrl[n8]);
                        ((StringBuilder)object14).append(stringBuilder3.toString());
                    }
                    ((PrintWriter)object11).println(((StringBuilder)object14).toString());
                }
                ArrayMap<String, ? extends Uid.Proc> arrayMap4 = ((Uid)object18).getProcessStats();
                n8 = arrayMap4.size() - 1;
                object12 = object;
                object = object9;
                object10 = object11;
                do {
                    n4 = n;
                    if (n8 < 0) break;
                    object10 = arrayMap4.valueAt(n8);
                    l9 = ((Uid.Proc)object10).getUserTime(n4);
                    l7 = ((Uid.Proc)object10).getSystemTime(n4);
                    l2 = ((Uid.Proc)object10).getForegroundTime(n4);
                    n9 = ((Uid.Proc)object10).getStarts(n4);
                    int n10 = ((Uid.Proc)object10).getNumCrashes(n4);
                    int n11 = ((Uid.Proc)object10).getNumAnrs(n4);
                    n4 = n4 == 0 ? ((Uid.Proc)object10).countExcessivePowers() : 0;
                    if (l9 != 0L || l7 != 0L || l2 != 0L || n9 != 0 || n4 != 0 || n10 != 0 || n11 != 0) {
                        ((StringBuilder)object14).setLength(0);
                        ((StringBuilder)object14).append(string2);
                        ((StringBuilder)object14).append("    Proc ");
                        ((StringBuilder)object14).append(arrayMap4.keyAt(n8));
                        ((StringBuilder)object14).append(":\n");
                        ((StringBuilder)object14).append(string2);
                        ((StringBuilder)object14).append("      CPU: ");
                        BatteryStats.formatTimeMs((StringBuilder)object14, l9);
                        ((StringBuilder)object14).append("usr + ");
                        BatteryStats.formatTimeMs((StringBuilder)object14, l7);
                        ((StringBuilder)object14).append("krn ; ");
                        BatteryStats.formatTimeMs((StringBuilder)object14, l2);
                        ((StringBuilder)object14).append("fg");
                        if (n9 != 0 || n10 != 0 || n11 != 0) {
                            ((StringBuilder)object14).append("\n");
                            ((StringBuilder)object14).append(string2);
                            ((StringBuilder)object14).append("      ");
                            n5 = 0;
                            if (n9 != 0) {
                                n5 = 1;
                                ((StringBuilder)object14).append(n9);
                                ((StringBuilder)object14).append(" starts");
                            }
                            n9 = n5;
                            if (n10 != 0) {
                                if (n5 != 0) {
                                    ((StringBuilder)object14).append((String)object);
                                }
                                n9 = 1;
                                ((StringBuilder)object14).append(n10);
                                ((StringBuilder)object14).append(" crashes");
                            }
                            if (n11 != 0) {
                                if (n9 != 0) {
                                    ((StringBuilder)object14).append((String)object);
                                }
                                ((StringBuilder)object14).append(n11);
                                ((StringBuilder)object14).append(" anrs");
                            }
                        }
                        object9 = ((StringBuilder)object14).toString();
                        object11 = printWriter;
                        ((PrintWriter)object11).println((String)object9);
                        n5 = n10;
                        for (n9 = 0; n9 < n4; ++n9) {
                            object9 = ((Uid.Proc)object10).getExcessivePower(n9);
                            if (object9 == null) continue;
                            printWriter.print(string2);
                            ((PrintWriter)object11).print("      * Killed for ");
                            if (((Uid.Proc.ExcessivePower)object9).type == 2) {
                                ((PrintWriter)object11).print("cpu");
                            } else {
                                ((PrintWriter)object11).print("unknown");
                            }
                            ((PrintWriter)object11).print(" use: ");
                            TimeUtils.formatDuration(((Uid.Proc.ExcessivePower)object9).usedTime, (PrintWriter)object11);
                            ((PrintWriter)object11).print(" over ");
                            TimeUtils.formatDuration(((Uid.Proc.ExcessivePower)object9).overTime, (PrintWriter)object11);
                            if (((Uid.Proc.ExcessivePower)object9).overTime == 0L) continue;
                            ((PrintWriter)object11).print(" (");
                            ((PrintWriter)object11).print(((Uid.Proc.ExcessivePower)object9).usedTime * 100L / ((Uid.Proc.ExcessivePower)object9).overTime);
                            ((PrintWriter)object11).println("%)");
                        }
                        n5 = 1;
                    }
                    --n8;
                    object10 = printWriter;
                } while (true);
                Object object19 = object10;
                Object object20 = object12;
                object = ((Uid)object18).getPackageStats();
                for (n4 = object.size() - 1; n4 >= 0; --n4) {
                    printWriter.print(string2);
                    ((PrintWriter)object19).print("    Apk ");
                    ((PrintWriter)object19).print((String)((ArrayMap)object).keyAt(n4));
                    ((PrintWriter)object19).println(":");
                    n5 = 0;
                    object10 = (Uid.Pkg)((ArrayMap)object).valueAt(n4);
                    object12 = ((Uid.Pkg)object10).getWakeupAlarmStats();
                    for (n6 = object12.size() - 1; n6 >= 0; --n6) {
                        printWriter.print(string2);
                        ((PrintWriter)object19).print("      Wakeup alarm ");
                        ((PrintWriter)object19).print((String)((ArrayMap)object12).keyAt(n6));
                        ((PrintWriter)object19).print(": ");
                        ((PrintWriter)object19).print(((Counter)((ArrayMap)object12).valueAt(n6)).getCountLocked(n));
                        ((PrintWriter)object19).println(" times");
                        n5 = 1;
                    }
                    object6 = ((Uid.Pkg)object10).getServiceStats();
                    for (n6 = object6.size() - 1; n6 >= 0; --n6) {
                        object11 = (Uid.Pkg.Serv)((ArrayMap)object6).valueAt(n6);
                        l7 = ((Uid.Pkg.Serv)object11).getStartTime(l6, n);
                        n8 = ((Uid.Pkg.Serv)object11).getStarts(n);
                        n9 = ((Uid.Pkg.Serv)object11).getLaunches(n);
                        if (l7 == 0L && n8 == 0 && n9 == 0) continue;
                        ((StringBuilder)object14).setLength(0);
                        ((StringBuilder)object14).append(string2);
                        ((StringBuilder)object14).append("      Service ");
                        ((StringBuilder)object14).append((String)((ArrayMap)object6).keyAt(n6));
                        ((StringBuilder)object14).append(":\n");
                        ((StringBuilder)object14).append(string2);
                        ((StringBuilder)object14).append("        Created for: ");
                        BatteryStats.formatTimeMs((StringBuilder)object14, l7 / 1000L);
                        ((StringBuilder)object14).append("uptime\n");
                        ((StringBuilder)object14).append(string2);
                        ((StringBuilder)object14).append("        Starts: ");
                        ((StringBuilder)object14).append(n8);
                        ((StringBuilder)object14).append(", launches: ");
                        ((StringBuilder)object14).append(n9);
                        ((PrintWriter)object19).println(((StringBuilder)object14).toString());
                        n5 = 1;
                    }
                    if (n5 == 0) {
                        printWriter.print(string2);
                        ((PrintWriter)object19).println("      (nothing executed)");
                    }
                    n5 = 1;
                }
                l12 = l6;
                object = object14;
                object9 = object19;
                l2 = l12;
                l7 = l8;
                object6 = object13;
                object12 = object8;
                object11 = object3;
                l9 = l4;
                l6 = l5;
                object10 = object20;
                if (n5 == 0) {
                    printWriter.print(string2);
                    ((PrintWriter)object19).println("    (nothing executed)");
                    object10 = object20;
                    l6 = l5;
                    l9 = l4;
                    object11 = object3;
                    object12 = object8;
                    object6 = object13;
                    l7 = l8;
                    l2 = l12;
                    object9 = object19;
                    object = object14;
                }
            }
            l8 = l2;
            object13 = object12;
            object12 = object11;
            l2 = l9;
            l5 = l6;
            l9 = l7;
            object11 = object9;
            object9 = object10;
            object10 = object;
            l6 = l8;
            l7 = l5;
            object = object6;
            object6 = object13;
        }
    }

    public void dumpProtoLocked(Context object, FileDescriptor object2, List<ApplicationInfo> list, int n, long l) {
        object2 = new ProtoOutputStream((FileDescriptor)object2);
        this.prepareForDumpLocked();
        if ((n & 24) != 0) {
            this.dumpProtoHistoryLocked((ProtoOutputStream)object2, n, l);
            ((ProtoOutputStream)object2).flush();
            return;
        }
        l = ((ProtoOutputStream)object2).start(1146756268033L);
        ((ProtoOutputStream)object2).write(1120986464257L, 34);
        ((ProtoOutputStream)object2).write(1112396529666L, this.getParcelVersion());
        ((ProtoOutputStream)object2).write(1138166333443L, this.getStartPlatformVersion());
        ((ProtoOutputStream)object2).write(1138166333444L, this.getEndPlatformVersion());
        if ((n & 4) == 0) {
            boolean bl = (n & 64) != 0;
            object = new BatteryStatsHelper((Context)object, false, bl);
            ((BatteryStatsHelper)object).create(this);
            ((BatteryStatsHelper)object).refreshStats(0, -1);
            this.dumpProtoAppsLocked((ProtoOutputStream)object2, (BatteryStatsHelper)object, list);
            this.dumpProtoSystemLocked((ProtoOutputStream)object2, (BatteryStatsHelper)object);
        }
        ((ProtoOutputStream)object2).end(l);
        ((ProtoOutputStream)object2).flush();
    }

    public abstract void finishIteratingHistoryLocked();

    public abstract void finishIteratingOldHistoryLocked();

    final String formatBytesLocked(long l) {
        this.mFormatBuilder.setLength(0);
        if (l < 1024L) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(l);
            stringBuilder.append("B");
            return stringBuilder.toString();
        }
        if (l < 0x100000L) {
            this.mFormatter.format("%.2fKB", (double)l / 1024.0);
            return this.mFormatBuilder.toString();
        }
        if (l < 0x40000000L) {
            this.mFormatter.format("%.2fMB", (double)l / 1048576.0);
            return this.mFormatBuilder.toString();
        }
        this.mFormatter.format("%.2fGB", (double)l / 1.073741824E9);
        return this.mFormatBuilder.toString();
    }

    public final String formatRatioLocked(long l, long l2) {
        if (l2 == 0L) {
            return "--%";
        }
        float f = (float)l / (float)l2;
        this.mFormatBuilder.setLength(0);
        this.mFormatter.format("%.1f%%", Float.valueOf(f * 100.0f));
        return this.mFormatBuilder.toString();
    }

    public abstract long getBatteryRealtime(long var1);

    @UnsupportedAppUsage
    public abstract long getBatteryUptime(long var1);

    public abstract ControllerActivityCounter getBluetoothControllerActivity();

    public abstract long getBluetoothScanTime(long var1, int var3);

    public abstract long getCameraOnTime(long var1, int var3);

    public abstract LevelStepTracker getChargeLevelStepTracker();

    public abstract long[] getCpuFreqs();

    public abstract long getCurrentDailyStartTime();

    public abstract LevelStepTracker getDailyChargeLevelStepTracker();

    public abstract LevelStepTracker getDailyDischargeLevelStepTracker();

    public abstract DailyItem getDailyItemLocked(int var1);

    public abstract ArrayList<PackageChange> getDailyPackageChanges();

    public abstract int getDeviceIdleModeCount(int var1, int var2);

    public abstract long getDeviceIdleModeTime(int var1, long var2, int var4);

    public abstract int getDeviceIdlingCount(int var1, int var2);

    public abstract long getDeviceIdlingTime(int var1, long var2, int var4);

    public abstract int getDischargeAmount(int var1);

    public abstract int getDischargeAmountScreenDoze();

    public abstract int getDischargeAmountScreenDozeSinceCharge();

    public abstract int getDischargeAmountScreenOff();

    public abstract int getDischargeAmountScreenOffSinceCharge();

    public abstract int getDischargeAmountScreenOn();

    public abstract int getDischargeAmountScreenOnSinceCharge();

    public abstract int getDischargeCurrentLevel();

    public abstract LevelStepTracker getDischargeLevelStepTracker();

    public abstract int getDischargeStartLevel();

    public abstract String getEndPlatformVersion();

    public abstract int getEstimatedBatteryCapacity();

    public abstract long getFlashlightOnCount(int var1);

    public abstract long getFlashlightOnTime(long var1, int var3);

    @UnsupportedAppUsage
    public abstract long getGlobalWifiRunningTime(long var1, int var3);

    public abstract long getGpsBatteryDrainMaMs();

    public abstract long getGpsSignalQualityTime(int var1, long var2, int var4);

    public abstract int getHighDischargeAmountSinceCharge();

    public abstract long getHistoryBaseTime();

    public abstract int getHistoryStringPoolBytes();

    public abstract int getHistoryStringPoolSize();

    public abstract String getHistoryTagPoolString(int var1);

    public abstract int getHistoryTagPoolUid(int var1);

    public abstract int getHistoryTotalSize();

    public abstract int getHistoryUsedSize();

    public abstract long getInteractiveTime(long var1, int var3);

    public abstract boolean getIsOnBattery();

    public abstract LongSparseArray<? extends Timer> getKernelMemoryStats();

    public abstract Map<String, ? extends Timer> getKernelWakelockStats();

    public abstract long getLongestDeviceIdleModeTime(int var1);

    public abstract int getLowDischargeAmountSinceCharge();

    public abstract int getMaxLearnedBatteryCapacity();

    public abstract int getMinLearnedBatteryCapacity();

    public abstract long getMobileRadioActiveAdjustedTime(int var1);

    public abstract int getMobileRadioActiveCount(int var1);

    public abstract long getMobileRadioActiveTime(long var1, int var3);

    public abstract int getMobileRadioActiveUnknownCount(int var1);

    public abstract long getMobileRadioActiveUnknownTime(int var1);

    public abstract ControllerActivityCounter getModemControllerActivity();

    public abstract long getNetworkActivityBytes(int var1, int var2);

    public abstract long getNetworkActivityPackets(int var1, int var2);

    @UnsupportedAppUsage
    public abstract boolean getNextHistoryLocked(HistoryItem var1);

    public abstract long getNextMaxDailyDeadline();

    public abstract long getNextMinDailyDeadline();

    public abstract boolean getNextOldHistoryLocked(HistoryItem var1);

    public abstract int getNumConnectivityChange(int var1);

    public abstract int getParcelVersion();

    public abstract int getPhoneDataConnectionCount(int var1, int var2);

    public abstract long getPhoneDataConnectionTime(int var1, long var2, int var4);

    public abstract Timer getPhoneDataConnectionTimer(int var1);

    public abstract int getPhoneOnCount(int var1);

    @UnsupportedAppUsage
    public abstract long getPhoneOnTime(long var1, int var3);

    public abstract long getPhoneSignalScanningTime(long var1, int var3);

    public abstract Timer getPhoneSignalScanningTimer();

    public abstract int getPhoneSignalStrengthCount(int var1, int var2);

    @UnsupportedAppUsage
    public abstract long getPhoneSignalStrengthTime(int var1, long var2, int var4);

    protected abstract Timer getPhoneSignalStrengthTimer(int var1);

    public abstract int getPowerSaveModeEnabledCount(int var1);

    public abstract long getPowerSaveModeEnabledTime(long var1, int var3);

    public abstract Map<String, ? extends Timer> getRpmStats();

    @UnsupportedAppUsage
    public abstract long getScreenBrightnessTime(int var1, long var2, int var4);

    public abstract Timer getScreenBrightnessTimer(int var1);

    public abstract int getScreenDozeCount(int var1);

    public abstract long getScreenDozeTime(long var1, int var3);

    public abstract Map<String, ? extends Timer> getScreenOffRpmStats();

    public abstract int getScreenOnCount(int var1);

    @UnsupportedAppUsage
    public abstract long getScreenOnTime(long var1, int var3);

    public abstract long getStartClockTime();

    public abstract int getStartCount();

    public abstract String getStartPlatformVersion();

    public abstract long getUahDischarge(int var1);

    public abstract long getUahDischargeDeepDoze(int var1);

    public abstract long getUahDischargeLightDoze(int var1);

    public abstract long getUahDischargeScreenDoze(int var1);

    public abstract long getUahDischargeScreenOff(int var1);

    @UnsupportedAppUsage
    public abstract SparseArray<? extends Uid> getUidStats();

    public abstract Map<String, ? extends Timer> getWakeupReasonStats();

    public abstract long getWifiActiveTime(long var1, int var3);

    public abstract ControllerActivityCounter getWifiControllerActivity();

    public abstract int getWifiMulticastWakelockCount(int var1);

    public abstract long getWifiMulticastWakelockTime(long var1, int var3);

    @UnsupportedAppUsage
    public abstract long getWifiOnTime(long var1, int var3);

    public abstract int getWifiSignalStrengthCount(int var1, int var2);

    public abstract long getWifiSignalStrengthTime(int var1, long var2, int var4);

    public abstract Timer getWifiSignalStrengthTimer(int var1);

    public abstract int getWifiStateCount(int var1, int var2);

    public abstract long getWifiStateTime(int var1, long var2, int var4);

    public abstract Timer getWifiStateTimer(int var1);

    public abstract int getWifiSupplStateCount(int var1, int var2);

    public abstract long getWifiSupplStateTime(int var1, long var2, int var4);

    public abstract Timer getWifiSupplStateTimer(int var1);

    public abstract boolean hasBluetoothActivityReporting();

    public abstract boolean hasModemActivityReporting();

    public abstract boolean hasWifiActivityReporting();

    public void prepareForDumpLocked() {
    }

    @UnsupportedAppUsage
    public abstract boolean startIteratingHistoryLocked();

    public abstract boolean startIteratingOldHistoryLocked();

    public abstract void writeToParcelWithoutUids(Parcel var1, int var2);

    public static final class BitDescription {
        public final int mask;
        public final String name;
        public final int shift;
        public final String shortName;
        public final String[] shortValues;
        public final String[] values;

        public BitDescription(int n, int n2, String string2, String string3, String[] arrstring, String[] arrstring2) {
            this.mask = n;
            this.shift = n2;
            this.name = string2;
            this.shortName = string3;
            this.values = arrstring;
            this.shortValues = arrstring2;
        }

        public BitDescription(int n, String string2, String string3) {
            this.mask = n;
            this.shift = -1;
            this.name = string2;
            this.shortName = string3;
            this.values = null;
            this.shortValues = null;
        }
    }

    public static abstract class ControllerActivityCounter {
        public abstract LongCounter getIdleTimeCounter();

        public abstract LongCounter getMonitoredRailChargeConsumedMaMs();

        public abstract LongCounter getPowerCounter();

        public abstract LongCounter getRxTimeCounter();

        public abstract LongCounter getScanTimeCounter();

        public abstract LongCounter getSleepTimeCounter();

        public abstract LongCounter[] getTxTimeCounters();
    }

    public static abstract class Counter {
        @UnsupportedAppUsage
        public abstract int getCountLocked(int var1);

        public abstract void logState(Printer var1, String var2);
    }

    public static final class DailyItem {
        public LevelStepTracker mChargeSteps;
        public LevelStepTracker mDischargeSteps;
        public long mEndTime;
        public ArrayList<PackageChange> mPackageChanges;
        public long mStartTime;
    }

    public static final class HistoryEventTracker {
        private final HashMap<String, SparseIntArray>[] mActiveEvents = new HashMap[22];

        public HashMap<String, SparseIntArray> getStateForEvent(int n) {
            return this.mActiveEvents[n];
        }

        public void removeEvents(int n) {
            this.mActiveEvents[-49153 & n] = null;
        }

        public boolean updateState(int n, String string2, int n2, int n3) {
            block8 : {
                block7 : {
                    if ((32768 & n) == 0) break block7;
                    Cloneable cloneable = this.mActiveEvents[n &= -49153];
                    HashMap<String, SparseIntArray> hashMap = cloneable;
                    if (cloneable == null) {
                        hashMap = new HashMap();
                        this.mActiveEvents[n] = hashMap;
                    }
                    SparseIntArray sparseIntArray = hashMap.get(string2);
                    cloneable = sparseIntArray;
                    if (sparseIntArray == null) {
                        cloneable = new SparseIntArray();
                        hashMap.put(string2, (SparseIntArray)cloneable);
                    }
                    if (((SparseIntArray)cloneable).indexOfKey(n2) >= 0) {
                        return false;
                    }
                    ((SparseIntArray)cloneable).put(n2, n3);
                    break block8;
                }
                if ((n & 16384) == 0) break block8;
                HashMap<String, SparseIntArray> hashMap = this.mActiveEvents[n & -49153];
                if (hashMap == null) {
                    return false;
                }
                SparseIntArray sparseIntArray = hashMap.get(string2);
                if (sparseIntArray == null) {
                    return false;
                }
                n = sparseIntArray.indexOfKey(n2);
                if (n < 0) {
                    return false;
                }
                sparseIntArray.removeAt(n);
                if (sparseIntArray.size() <= 0) {
                    hashMap.remove(string2);
                }
            }
            return true;
        }
    }

    public static final class HistoryItem
    implements Parcelable {
        public static final byte CMD_CURRENT_TIME = 5;
        public static final byte CMD_NULL = -1;
        public static final byte CMD_OVERFLOW = 6;
        public static final byte CMD_RESET = 7;
        public static final byte CMD_SHUTDOWN = 8;
        public static final byte CMD_START = 4;
        @UnsupportedAppUsage
        public static final byte CMD_UPDATE = 0;
        public static final int EVENT_ACTIVE = 10;
        public static final int EVENT_ALARM = 13;
        public static final int EVENT_ALARM_FINISH = 16397;
        public static final int EVENT_ALARM_START = 32781;
        public static final int EVENT_COLLECT_EXTERNAL_STATS = 14;
        public static final int EVENT_CONNECTIVITY_CHANGED = 9;
        public static final int EVENT_COUNT = 22;
        public static final int EVENT_FLAG_FINISH = 16384;
        public static final int EVENT_FLAG_START = 32768;
        public static final int EVENT_FOREGROUND = 2;
        public static final int EVENT_FOREGROUND_FINISH = 16386;
        public static final int EVENT_FOREGROUND_START = 32770;
        public static final int EVENT_JOB = 6;
        public static final int EVENT_JOB_FINISH = 16390;
        public static final int EVENT_JOB_START = 32774;
        public static final int EVENT_LONG_WAKE_LOCK = 20;
        public static final int EVENT_LONG_WAKE_LOCK_FINISH = 16404;
        public static final int EVENT_LONG_WAKE_LOCK_START = 32788;
        public static final int EVENT_NONE = 0;
        public static final int EVENT_PACKAGE_ACTIVE = 16;
        public static final int EVENT_PACKAGE_INACTIVE = 15;
        public static final int EVENT_PACKAGE_INSTALLED = 11;
        public static final int EVENT_PACKAGE_UNINSTALLED = 12;
        public static final int EVENT_PROC = 1;
        public static final int EVENT_PROC_FINISH = 16385;
        public static final int EVENT_PROC_START = 32769;
        public static final int EVENT_SCREEN_WAKE_UP = 18;
        public static final int EVENT_SYNC = 4;
        public static final int EVENT_SYNC_FINISH = 16388;
        public static final int EVENT_SYNC_START = 32772;
        public static final int EVENT_TEMP_WHITELIST = 17;
        public static final int EVENT_TEMP_WHITELIST_FINISH = 16401;
        public static final int EVENT_TEMP_WHITELIST_START = 32785;
        public static final int EVENT_TOP = 3;
        public static final int EVENT_TOP_FINISH = 16387;
        public static final int EVENT_TOP_START = 32771;
        public static final int EVENT_TYPE_MASK = -49153;
        public static final int EVENT_USER_FOREGROUND = 8;
        public static final int EVENT_USER_FOREGROUND_FINISH = 16392;
        public static final int EVENT_USER_FOREGROUND_START = 32776;
        public static final int EVENT_USER_RUNNING = 7;
        public static final int EVENT_USER_RUNNING_FINISH = 16391;
        public static final int EVENT_USER_RUNNING_START = 32775;
        public static final int EVENT_WAKEUP_AP = 19;
        public static final int EVENT_WAKE_LOCK = 5;
        public static final int EVENT_WAKE_LOCK_FINISH = 16389;
        public static final int EVENT_WAKE_LOCK_START = 32773;
        public static final int MOST_INTERESTING_STATES = 1835008;
        public static final int MOST_INTERESTING_STATES2 = -1749024768;
        public static final int SETTLE_TO_ZERO_STATES = -1900544;
        public static final int SETTLE_TO_ZERO_STATES2 = 1748959232;
        public static final int STATE2_BLUETOOTH_ON_FLAG = 4194304;
        public static final int STATE2_BLUETOOTH_SCAN_FLAG = 1048576;
        public static final int STATE2_CAMERA_FLAG = 2097152;
        public static final int STATE2_CELLULAR_HIGH_TX_POWER_FLAG = 524288;
        public static final int STATE2_CHARGING_FLAG = 16777216;
        public static final int STATE2_DEVICE_IDLE_MASK = 100663296;
        public static final int STATE2_DEVICE_IDLE_SHIFT = 25;
        public static final int STATE2_FLASHLIGHT_FLAG = 134217728;
        public static final int STATE2_GPS_SIGNAL_QUALITY_MASK = 128;
        public static final int STATE2_GPS_SIGNAL_QUALITY_SHIFT = 7;
        public static final int STATE2_PHONE_IN_CALL_FLAG = 8388608;
        public static final int STATE2_POWER_SAVE_FLAG = Integer.MIN_VALUE;
        public static final int STATE2_USB_DATA_LINK_FLAG = 262144;
        public static final int STATE2_VIDEO_ON_FLAG = 1073741824;
        public static final int STATE2_WIFI_ON_FLAG = 268435456;
        public static final int STATE2_WIFI_RUNNING_FLAG = 536870912;
        public static final int STATE2_WIFI_SIGNAL_STRENGTH_MASK = 112;
        public static final int STATE2_WIFI_SIGNAL_STRENGTH_SHIFT = 4;
        public static final int STATE2_WIFI_SUPPL_STATE_MASK = 15;
        public static final int STATE2_WIFI_SUPPL_STATE_SHIFT = 0;
        public static final int STATE_AUDIO_ON_FLAG = 4194304;
        public static final int STATE_BATTERY_PLUGGED_FLAG = 524288;
        public static final int STATE_BRIGHTNESS_MASK = 7;
        public static final int STATE_BRIGHTNESS_SHIFT = 0;
        public static final int STATE_CPU_RUNNING_FLAG = Integer.MIN_VALUE;
        public static final int STATE_DATA_CONNECTION_MASK = 15872;
        public static final int STATE_DATA_CONNECTION_SHIFT = 9;
        public static final int STATE_GPS_ON_FLAG = 536870912;
        public static final int STATE_MOBILE_RADIO_ACTIVE_FLAG = 33554432;
        public static final int STATE_PHONE_SCANNING_FLAG = 2097152;
        public static final int STATE_PHONE_SIGNAL_STRENGTH_MASK = 56;
        public static final int STATE_PHONE_SIGNAL_STRENGTH_SHIFT = 3;
        public static final int STATE_PHONE_STATE_MASK = 448;
        public static final int STATE_PHONE_STATE_SHIFT = 6;
        private static final int STATE_RESERVED_0 = 16777216;
        public static final int STATE_SCREEN_DOZE_FLAG = 262144;
        public static final int STATE_SCREEN_ON_FLAG = 1048576;
        public static final int STATE_SENSOR_ON_FLAG = 8388608;
        public static final int STATE_WAKE_LOCK_FLAG = 1073741824;
        public static final int STATE_WIFI_FULL_LOCK_FLAG = 268435456;
        public static final int STATE_WIFI_MULTICAST_ON_FLAG = 65536;
        public static final int STATE_WIFI_RADIO_ACTIVE_FLAG = 67108864;
        public static final int STATE_WIFI_SCAN_FLAG = 134217728;
        public int batteryChargeUAh;
        @UnsupportedAppUsage
        public byte batteryHealth;
        @UnsupportedAppUsage
        public byte batteryLevel;
        @UnsupportedAppUsage
        public byte batteryPlugType;
        @UnsupportedAppUsage
        public byte batteryStatus;
        public short batteryTemperature;
        @UnsupportedAppUsage
        public char batteryVoltage;
        @UnsupportedAppUsage
        public byte cmd = (byte)-1;
        public long currentTime;
        public int eventCode;
        public HistoryTag eventTag;
        public final HistoryTag localEventTag = new HistoryTag();
        public final HistoryTag localWakeReasonTag = new HistoryTag();
        public final HistoryTag localWakelockTag = new HistoryTag();
        public double modemRailChargeMah;
        public HistoryItem next;
        public int numReadInts;
        @UnsupportedAppUsage
        public int states;
        @UnsupportedAppUsage
        public int states2;
        public HistoryStepDetails stepDetails;
        @UnsupportedAppUsage
        public long time;
        public HistoryTag wakeReasonTag;
        public HistoryTag wakelockTag;
        public double wifiRailChargeMah;

        @UnsupportedAppUsage
        public HistoryItem() {
        }

        public HistoryItem(long l, Parcel parcel) {
            this.time = l;
            this.numReadInts = 2;
            this.readFromParcel(parcel);
        }

        private void setToCommon(HistoryItem historyItem) {
            this.batteryLevel = historyItem.batteryLevel;
            this.batteryStatus = historyItem.batteryStatus;
            this.batteryHealth = historyItem.batteryHealth;
            this.batteryPlugType = historyItem.batteryPlugType;
            this.batteryTemperature = historyItem.batteryTemperature;
            this.batteryVoltage = historyItem.batteryVoltage;
            this.batteryChargeUAh = historyItem.batteryChargeUAh;
            this.modemRailChargeMah = historyItem.modemRailChargeMah;
            this.wifiRailChargeMah = historyItem.wifiRailChargeMah;
            this.states = historyItem.states;
            this.states2 = historyItem.states2;
            if (historyItem.wakelockTag != null) {
                this.wakelockTag = this.localWakelockTag;
                this.wakelockTag.setTo(historyItem.wakelockTag);
            } else {
                this.wakelockTag = null;
            }
            if (historyItem.wakeReasonTag != null) {
                this.wakeReasonTag = this.localWakeReasonTag;
                this.wakeReasonTag.setTo(historyItem.wakeReasonTag);
            } else {
                this.wakeReasonTag = null;
            }
            this.eventCode = historyItem.eventCode;
            if (historyItem.eventTag != null) {
                this.eventTag = this.localEventTag;
                this.eventTag.setTo(historyItem.eventTag);
            } else {
                this.eventTag = null;
            }
            this.currentTime = historyItem.currentTime;
        }

        public void clear() {
            this.time = 0L;
            this.cmd = (byte)-1;
            this.batteryLevel = (byte)(false ? 1 : 0);
            this.batteryStatus = (byte)(false ? 1 : 0);
            this.batteryHealth = (byte)(false ? 1 : 0);
            this.batteryPlugType = (byte)(false ? 1 : 0);
            this.batteryTemperature = (short)(false ? 1 : 0);
            this.batteryVoltage = (char)(false ? 1 : 0);
            this.batteryChargeUAh = 0;
            this.modemRailChargeMah = 0.0;
            this.wifiRailChargeMah = 0.0;
            this.states = 0;
            this.states2 = 0;
            this.wakelockTag = null;
            this.wakeReasonTag = null;
            this.eventCode = 0;
            this.eventTag = null;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public boolean isDeltaData() {
            boolean bl = this.cmd == 0;
            return bl;
        }

        public void readFromParcel(Parcel parcel) {
            int n = parcel.dataPosition();
            int n2 = parcel.readInt();
            this.cmd = (byte)(n2 & 255);
            this.batteryLevel = (byte)(n2 >> 8 & 255);
            this.batteryStatus = (byte)(n2 >> 16 & 15);
            this.batteryHealth = (byte)(n2 >> 20 & 15);
            this.batteryPlugType = (byte)(n2 >> 24 & 15);
            int n3 = parcel.readInt();
            this.batteryTemperature = (short)(n3 & 65535);
            this.batteryVoltage = (char)(65535 & n3 >> 16);
            this.batteryChargeUAh = parcel.readInt();
            this.modemRailChargeMah = parcel.readDouble();
            this.wifiRailChargeMah = parcel.readDouble();
            this.states = parcel.readInt();
            this.states2 = parcel.readInt();
            if ((268435456 & n2) != 0) {
                this.wakelockTag = this.localWakelockTag;
                this.wakelockTag.readFromParcel(parcel);
            } else {
                this.wakelockTag = null;
            }
            if ((536870912 & n2) != 0) {
                this.wakeReasonTag = this.localWakeReasonTag;
                this.wakeReasonTag.readFromParcel(parcel);
            } else {
                this.wakeReasonTag = null;
            }
            if ((1073741824 & n2) != 0) {
                this.eventCode = parcel.readInt();
                this.eventTag = this.localEventTag;
                this.eventTag.readFromParcel(parcel);
            } else {
                this.eventCode = 0;
                this.eventTag = null;
            }
            n3 = this.cmd;
            this.currentTime = n3 != 5 && n3 != 7 ? 0L : parcel.readLong();
            this.numReadInts += (parcel.dataPosition() - n) / 4;
        }

        public boolean same(HistoryItem object) {
            if (this.sameNonEvent((HistoryItem)object) && this.eventCode == ((HistoryItem)object).eventCode) {
                HistoryTag historyTag = this.wakelockTag;
                HistoryTag historyTag2 = ((HistoryItem)object).wakelockTag;
                if (historyTag != historyTag2) {
                    if (historyTag != null && historyTag2 != null) {
                        if (!historyTag.equals(historyTag2)) {
                            return false;
                        }
                    } else {
                        return false;
                    }
                }
                if ((historyTag = this.wakeReasonTag) != (historyTag2 = ((HistoryItem)object).wakeReasonTag)) {
                    if (historyTag != null && historyTag2 != null) {
                        if (!historyTag.equals(historyTag2)) {
                            return false;
                        }
                    } else {
                        return false;
                    }
                }
                if ((historyTag = this.eventTag) != (object = ((HistoryItem)object).eventTag)) {
                    if (historyTag != null && object != null) {
                        if (!historyTag.equals(object)) {
                            return false;
                        }
                    } else {
                        return false;
                    }
                }
                return true;
            }
            return false;
        }

        public boolean sameNonEvent(HistoryItem historyItem) {
            boolean bl = this.batteryLevel == historyItem.batteryLevel && this.batteryStatus == historyItem.batteryStatus && this.batteryHealth == historyItem.batteryHealth && this.batteryPlugType == historyItem.batteryPlugType && this.batteryTemperature == historyItem.batteryTemperature && this.batteryVoltage == historyItem.batteryVoltage && this.batteryChargeUAh == historyItem.batteryChargeUAh && this.modemRailChargeMah == historyItem.modemRailChargeMah && this.wifiRailChargeMah == historyItem.wifiRailChargeMah && this.states == historyItem.states && this.states2 == historyItem.states2 && this.currentTime == historyItem.currentTime;
            return bl;
        }

        public void setTo(long l, byte by, HistoryItem historyItem) {
            this.time = l;
            this.cmd = by;
            this.setToCommon(historyItem);
        }

        public void setTo(HistoryItem historyItem) {
            this.time = historyItem.time;
            this.cmd = historyItem.cmd;
            this.setToCommon(historyItem);
        }

        @Override
        public void writeToParcel(Parcel parcel, int n) {
            parcel.writeLong(this.time);
            byte by = this.cmd;
            byte by2 = this.batteryLevel;
            byte by3 = this.batteryStatus;
            byte by4 = this.batteryHealth;
            byte by5 = this.batteryPlugType;
            HistoryTag historyTag = this.wakelockTag;
            int n2 = 0;
            int n3 = historyTag != null ? 268435456 : 0;
            int n4 = this.wakeReasonTag != null ? 536870912 : 0;
            if (this.eventCode != 0) {
                n2 = 1073741824;
            }
            parcel.writeInt(by & 255 | by2 << 8 & 65280 | by3 << 16 & 983040 | by4 << 20 & 15728640 | by5 << 24 & 251658240 | n3 | n4 | n2);
            parcel.writeInt(this.batteryTemperature & 65535 | this.batteryVoltage << 16 & -65536);
            parcel.writeInt(this.batteryChargeUAh);
            parcel.writeDouble(this.modemRailChargeMah);
            parcel.writeDouble(this.wifiRailChargeMah);
            parcel.writeInt(this.states);
            parcel.writeInt(this.states2);
            historyTag = this.wakelockTag;
            if (historyTag != null) {
                historyTag.writeToParcel(parcel, n);
            }
            if ((historyTag = this.wakeReasonTag) != null) {
                historyTag.writeToParcel(parcel, n);
            }
            if ((n3 = this.eventCode) != 0) {
                parcel.writeInt(n3);
                this.eventTag.writeToParcel(parcel, n);
            }
            if ((n = (int)this.cmd) == 5 || n == 7) {
                parcel.writeLong(this.currentTime);
            }
        }
    }

    public static class HistoryPrinter {
        long lastTime = -1L;
        int oldChargeMAh = -1;
        int oldHealth = -1;
        int oldLevel = -1;
        double oldModemRailChargeMah = -1.0;
        int oldPlug = -1;
        int oldState = 0;
        int oldState2 = 0;
        int oldStatus = -1;
        int oldTemp = -1;
        int oldVolt = -1;
        double oldWifiRailChargeMah = -1.0;

        private String printNextItem(HistoryItem historyItem, long l, boolean bl, boolean bl2) {
            StringBuilder stringBuilder = new StringBuilder();
            if (!bl) {
                stringBuilder.append("  ");
                TimeUtils.formatDuration(historyItem.time - l, stringBuilder, 19);
                stringBuilder.append(" (");
                stringBuilder.append(historyItem.numReadInts);
                stringBuilder.append(") ");
            } else {
                stringBuilder.append(9);
                stringBuilder.append(',');
                stringBuilder.append(BatteryStats.HISTORY_DATA);
                stringBuilder.append(',');
                if (this.lastTime < 0L) {
                    stringBuilder.append(historyItem.time - l);
                } else {
                    stringBuilder.append(historyItem.time - this.lastTime);
                }
                this.lastTime = historyItem.time;
            }
            if (historyItem.cmd == 4) {
                if (bl) {
                    stringBuilder.append(":");
                }
                stringBuilder.append("START\n");
                this.reset();
            } else {
                int n = historyItem.cmd;
                String string2 = " ";
                if (n != 5 && historyItem.cmd != 7) {
                    if (historyItem.cmd == 8) {
                        if (bl) {
                            stringBuilder.append(":");
                        }
                        stringBuilder.append("SHUTDOWN\n");
                    } else if (historyItem.cmd == 6) {
                        if (bl) {
                            stringBuilder.append(":");
                        }
                        stringBuilder.append("*OVERFLOW*\n");
                    } else {
                        Object object;
                        if (!bl) {
                            if (historyItem.batteryLevel < 10) {
                                stringBuilder.append("00");
                            } else if (historyItem.batteryLevel < 100) {
                                stringBuilder.append("0");
                            }
                            stringBuilder.append(historyItem.batteryLevel);
                            if (bl2) {
                                stringBuilder.append(" ");
                                if (historyItem.states >= 0) {
                                    if (historyItem.states < 16) {
                                        stringBuilder.append("0000000");
                                    } else if (historyItem.states < 256) {
                                        stringBuilder.append("000000");
                                    } else if (historyItem.states < 4096) {
                                        stringBuilder.append("00000");
                                    } else if (historyItem.states < 65536) {
                                        stringBuilder.append("0000");
                                    } else if (historyItem.states < 1048576) {
                                        stringBuilder.append("000");
                                    } else if (historyItem.states < 16777216) {
                                        stringBuilder.append("00");
                                    } else if (historyItem.states < 268435456) {
                                        stringBuilder.append("0");
                                    }
                                }
                                stringBuilder.append(Integer.toHexString(historyItem.states));
                            }
                        } else if (this.oldLevel != historyItem.batteryLevel) {
                            this.oldLevel = historyItem.batteryLevel;
                            stringBuilder.append(",Bl=");
                            stringBuilder.append(historyItem.batteryLevel);
                        }
                        if (this.oldStatus != historyItem.batteryStatus) {
                            this.oldStatus = historyItem.batteryStatus;
                            object = bl ? ",Bs=" : " status=";
                            stringBuilder.append((String)object);
                            n = this.oldStatus;
                            if (n != 1) {
                                if (n != 2) {
                                    if (n != 3) {
                                        if (n != 4) {
                                            if (n != 5) {
                                                stringBuilder.append(n);
                                            } else {
                                                object = bl ? "f" : "full";
                                                stringBuilder.append((String)object);
                                            }
                                        } else {
                                            object = bl ? "n" : "not-charging";
                                            stringBuilder.append((String)object);
                                        }
                                    } else {
                                        object = bl ? "d" : "discharging";
                                        stringBuilder.append((String)object);
                                    }
                                } else {
                                    object = bl ? "c" : "charging";
                                    stringBuilder.append((String)object);
                                }
                            } else {
                                object = bl ? "?" : "unknown";
                                stringBuilder.append((String)object);
                            }
                        }
                        if (this.oldHealth != historyItem.batteryHealth) {
                            this.oldHealth = historyItem.batteryHealth;
                            object = bl ? ",Bh=" : " health=";
                            stringBuilder.append((String)object);
                            n = this.oldHealth;
                            switch (n) {
                                default: {
                                    stringBuilder.append(n);
                                    break;
                                }
                                case 7: {
                                    object = bl ? "c" : "cold";
                                    stringBuilder.append((String)object);
                                    break;
                                }
                                case 6: {
                                    object = bl ? "f" : "failure";
                                    stringBuilder.append((String)object);
                                    break;
                                }
                                case 5: {
                                    object = bl ? "v" : "over-voltage";
                                    stringBuilder.append((String)object);
                                    break;
                                }
                                case 4: {
                                    object = bl ? "d" : "dead";
                                    stringBuilder.append((String)object);
                                    break;
                                }
                                case 3: {
                                    object = bl ? BatteryStats.HISTORY_DATA : "overheat";
                                    stringBuilder.append((String)object);
                                    break;
                                }
                                case 2: {
                                    object = bl ? "g" : "good";
                                    stringBuilder.append((String)object);
                                    break;
                                }
                                case 1: {
                                    object = bl ? "?" : "unknown";
                                    stringBuilder.append((String)object);
                                }
                            }
                        }
                        if (this.oldPlug != historyItem.batteryPlugType) {
                            this.oldPlug = historyItem.batteryPlugType;
                            object = bl ? ",Bp=" : " plug=";
                            stringBuilder.append((String)object);
                            n = this.oldPlug;
                            if (n != 0) {
                                if (n != 1) {
                                    if (n != 2) {
                                        if (n != 4) {
                                            stringBuilder.append(n);
                                        } else {
                                            object = bl ? "w" : "wireless";
                                            stringBuilder.append((String)object);
                                        }
                                    } else {
                                        object = bl ? "u" : "usb";
                                        stringBuilder.append((String)object);
                                    }
                                } else {
                                    object = bl ? "a" : "ac";
                                    stringBuilder.append((String)object);
                                }
                            } else {
                                object = bl ? "n" : "none";
                                stringBuilder.append((String)object);
                            }
                        }
                        if (this.oldTemp != historyItem.batteryTemperature) {
                            this.oldTemp = historyItem.batteryTemperature;
                            object = bl ? ",Bt=" : " temp=";
                            stringBuilder.append((String)object);
                            stringBuilder.append(this.oldTemp);
                        }
                        if (this.oldVolt != historyItem.batteryVoltage) {
                            this.oldVolt = historyItem.batteryVoltage;
                            object = bl ? ",Bv=" : " volt=";
                            stringBuilder.append((String)object);
                            stringBuilder.append(this.oldVolt);
                        }
                        if (this.oldChargeMAh != (n = historyItem.batteryChargeUAh / 1000)) {
                            this.oldChargeMAh = n;
                            object = bl ? ",Bcc=" : " charge=";
                            stringBuilder.append((String)object);
                            stringBuilder.append(this.oldChargeMAh);
                        }
                        if (this.oldModemRailChargeMah != historyItem.modemRailChargeMah) {
                            this.oldModemRailChargeMah = historyItem.modemRailChargeMah;
                            object = bl ? ",Mrc=" : " modemRailChargemAh=";
                            stringBuilder.append((String)object);
                            stringBuilder.append(new DecimalFormat("#.##").format(this.oldModemRailChargeMah));
                        }
                        if (this.oldWifiRailChargeMah != historyItem.wifiRailChargeMah) {
                            this.oldWifiRailChargeMah = historyItem.wifiRailChargeMah;
                            object = bl ? ",Wrc=" : " wifiRailChargemAh=";
                            stringBuilder.append((String)object);
                            stringBuilder.append(new DecimalFormat("#.##").format(this.oldWifiRailChargeMah));
                        }
                        BatteryStats.printBitDescriptions(stringBuilder, this.oldState, historyItem.states, historyItem.wakelockTag, HISTORY_STATE_DESCRIPTIONS, bl ^ true);
                        BatteryStats.printBitDescriptions(stringBuilder, this.oldState2, historyItem.states2, null, HISTORY_STATE2_DESCRIPTIONS, bl ^ true);
                        if (historyItem.wakeReasonTag != null) {
                            if (bl) {
                                stringBuilder.append(",wr=");
                                stringBuilder.append(historyItem.wakeReasonTag.poolIdx);
                            } else {
                                stringBuilder.append(" wake_reason=");
                                stringBuilder.append(historyItem.wakeReasonTag.uid);
                                stringBuilder.append(":\"");
                                stringBuilder.append(historyItem.wakeReasonTag.string);
                                stringBuilder.append("\"");
                            }
                        }
                        if (historyItem.eventCode != 0) {
                            object = string2;
                            if (bl) {
                                object = ",";
                            }
                            stringBuilder.append((String)object);
                            if ((historyItem.eventCode & 32768) != 0) {
                                stringBuilder.append("+");
                            } else if ((historyItem.eventCode & 16384) != 0) {
                                stringBuilder.append("-");
                            }
                            object = bl ? HISTORY_EVENT_CHECKIN_NAMES : HISTORY_EVENT_NAMES;
                            n = historyItem.eventCode & -49153;
                            if (n >= 0 && n < ((String[])object).length) {
                                stringBuilder.append(object[n]);
                            } else {
                                object = bl ? "Ev" : "event";
                                stringBuilder.append((String)object);
                                stringBuilder.append(n);
                            }
                            stringBuilder.append("=");
                            if (bl) {
                                stringBuilder.append(historyItem.eventTag.poolIdx);
                            } else {
                                stringBuilder.append(HISTORY_EVENT_INT_FORMATTERS[n].applyAsString(historyItem.eventTag.uid));
                                stringBuilder.append(":\"");
                                stringBuilder.append(historyItem.eventTag.string);
                                stringBuilder.append("\"");
                            }
                        }
                        stringBuilder.append("\n");
                        if (historyItem.stepDetails != null) {
                            if (!bl) {
                                stringBuilder.append("                 Details: cpu=");
                                stringBuilder.append(historyItem.stepDetails.userTime);
                                stringBuilder.append("u+");
                                stringBuilder.append(historyItem.stepDetails.systemTime);
                                stringBuilder.append("s");
                                if (historyItem.stepDetails.appCpuUid1 >= 0) {
                                    stringBuilder.append(" (");
                                    this.printStepCpuUidDetails(stringBuilder, historyItem.stepDetails.appCpuUid1, historyItem.stepDetails.appCpuUTime1, historyItem.stepDetails.appCpuSTime1);
                                    if (historyItem.stepDetails.appCpuUid2 >= 0) {
                                        stringBuilder.append(", ");
                                        this.printStepCpuUidDetails(stringBuilder, historyItem.stepDetails.appCpuUid2, historyItem.stepDetails.appCpuUTime2, historyItem.stepDetails.appCpuSTime2);
                                    }
                                    if (historyItem.stepDetails.appCpuUid3 >= 0) {
                                        stringBuilder.append(", ");
                                        this.printStepCpuUidDetails(stringBuilder, historyItem.stepDetails.appCpuUid3, historyItem.stepDetails.appCpuUTime3, historyItem.stepDetails.appCpuSTime3);
                                    }
                                    stringBuilder.append(')');
                                }
                                stringBuilder.append("\n");
                                stringBuilder.append("                          /proc/stat=");
                                stringBuilder.append(historyItem.stepDetails.statUserTime);
                                stringBuilder.append(" usr, ");
                                stringBuilder.append(historyItem.stepDetails.statSystemTime);
                                stringBuilder.append(" sys, ");
                                stringBuilder.append(historyItem.stepDetails.statIOWaitTime);
                                stringBuilder.append(" io, ");
                                stringBuilder.append(historyItem.stepDetails.statIrqTime);
                                stringBuilder.append(" irq, ");
                                stringBuilder.append(historyItem.stepDetails.statSoftIrqTime);
                                stringBuilder.append(" sirq, ");
                                stringBuilder.append(historyItem.stepDetails.statIdlTime);
                                stringBuilder.append(" idle");
                                int n2 = historyItem.stepDetails.statUserTime + historyItem.stepDetails.statSystemTime + historyItem.stepDetails.statIOWaitTime + historyItem.stepDetails.statIrqTime + historyItem.stepDetails.statSoftIrqTime;
                                n = historyItem.stepDetails.statIdlTime + n2;
                                if (n > 0) {
                                    stringBuilder.append(" (");
                                    stringBuilder.append(String.format("%.1f%%", Float.valueOf((float)n2 / (float)n * 100.0f)));
                                    stringBuilder.append(" of ");
                                    object = new StringBuilder(64);
                                    BatteryStats.formatTimeMsNoSpace((StringBuilder)object, n * 10);
                                    stringBuilder.append((CharSequence)object);
                                    stringBuilder.append(")");
                                }
                                stringBuilder.append(", PlatformIdleStat ");
                                stringBuilder.append(historyItem.stepDetails.statPlatformIdleState);
                                stringBuilder.append("\n");
                                stringBuilder.append(", SubsystemPowerState ");
                                stringBuilder.append(historyItem.stepDetails.statSubsystemPowerState);
                                stringBuilder.append("\n");
                            } else {
                                stringBuilder.append(9);
                                stringBuilder.append(',');
                                stringBuilder.append(BatteryStats.HISTORY_DATA);
                                stringBuilder.append(",0,Dcpu=");
                                stringBuilder.append(historyItem.stepDetails.userTime);
                                stringBuilder.append(":");
                                stringBuilder.append(historyItem.stepDetails.systemTime);
                                if (historyItem.stepDetails.appCpuUid1 >= 0) {
                                    this.printStepCpuUidCheckinDetails(stringBuilder, historyItem.stepDetails.appCpuUid1, historyItem.stepDetails.appCpuUTime1, historyItem.stepDetails.appCpuSTime1);
                                    if (historyItem.stepDetails.appCpuUid2 >= 0) {
                                        this.printStepCpuUidCheckinDetails(stringBuilder, historyItem.stepDetails.appCpuUid2, historyItem.stepDetails.appCpuUTime2, historyItem.stepDetails.appCpuSTime2);
                                    }
                                    if (historyItem.stepDetails.appCpuUid3 >= 0) {
                                        this.printStepCpuUidCheckinDetails(stringBuilder, historyItem.stepDetails.appCpuUid3, historyItem.stepDetails.appCpuUTime3, historyItem.stepDetails.appCpuSTime3);
                                    }
                                }
                                stringBuilder.append("\n");
                                stringBuilder.append(9);
                                stringBuilder.append(',');
                                stringBuilder.append(BatteryStats.HISTORY_DATA);
                                stringBuilder.append(",0,Dpst=");
                                stringBuilder.append(historyItem.stepDetails.statUserTime);
                                stringBuilder.append(',');
                                stringBuilder.append(historyItem.stepDetails.statSystemTime);
                                stringBuilder.append(',');
                                stringBuilder.append(historyItem.stepDetails.statIOWaitTime);
                                stringBuilder.append(',');
                                stringBuilder.append(historyItem.stepDetails.statIrqTime);
                                stringBuilder.append(',');
                                stringBuilder.append(historyItem.stepDetails.statSoftIrqTime);
                                stringBuilder.append(',');
                                stringBuilder.append(historyItem.stepDetails.statIdlTime);
                                stringBuilder.append(',');
                                if (historyItem.stepDetails.statPlatformIdleState != null) {
                                    stringBuilder.append(historyItem.stepDetails.statPlatformIdleState);
                                    if (historyItem.stepDetails.statSubsystemPowerState != null) {
                                        stringBuilder.append(',');
                                    }
                                }
                                if (historyItem.stepDetails.statSubsystemPowerState != null) {
                                    stringBuilder.append(historyItem.stepDetails.statSubsystemPowerState);
                                }
                                stringBuilder.append("\n");
                            }
                        }
                        this.oldState = historyItem.states;
                        this.oldState2 = historyItem.states2;
                    }
                } else {
                    if (bl) {
                        stringBuilder.append(":");
                    }
                    if (historyItem.cmd == 7) {
                        stringBuilder.append("RESET:");
                        this.reset();
                    }
                    stringBuilder.append("TIME:");
                    if (bl) {
                        stringBuilder.append(historyItem.currentTime);
                        stringBuilder.append("\n");
                    } else {
                        stringBuilder.append(" ");
                        stringBuilder.append(DateFormat.format((CharSequence)"yyyy-MM-dd-HH-mm-ss", historyItem.currentTime).toString());
                        stringBuilder.append("\n");
                    }
                }
            }
            return stringBuilder.toString();
        }

        private void printStepCpuUidCheckinDetails(StringBuilder stringBuilder, int n, int n2, int n3) {
            stringBuilder.append('/');
            stringBuilder.append(n);
            stringBuilder.append(":");
            stringBuilder.append(n2);
            stringBuilder.append(":");
            stringBuilder.append(n3);
        }

        private void printStepCpuUidDetails(StringBuilder stringBuilder, int n, int n2, int n3) {
            UserHandle.formatUid(stringBuilder, n);
            stringBuilder.append("=");
            stringBuilder.append(n2);
            stringBuilder.append("u+");
            stringBuilder.append(n3);
            stringBuilder.append("s");
        }

        public void printNextItem(ProtoOutputStream protoOutputStream, HistoryItem arrstring, long l, boolean bl) {
            arrstring = this.printNextItem((HistoryItem)arrstring, l, true, bl).split("\n");
            int n = arrstring.length;
            for (int i = 0; i < n; ++i) {
                protoOutputStream.write(2237677961222L, arrstring[i]);
            }
        }

        public void printNextItem(PrintWriter printWriter, HistoryItem historyItem, long l, boolean bl, boolean bl2) {
            printWriter.print(this.printNextItem(historyItem, l, bl, bl2));
        }

        void reset() {
            this.oldState2 = 0;
            this.oldState = 0;
            this.oldLevel = -1;
            this.oldStatus = -1;
            this.oldHealth = -1;
            this.oldPlug = -1;
            this.oldTemp = -1;
            this.oldVolt = -1;
            this.oldChargeMAh = -1;
            this.oldModemRailChargeMah = -1.0;
            this.oldWifiRailChargeMah = -1.0;
        }
    }

    public static final class HistoryStepDetails {
        public int appCpuSTime1;
        public int appCpuSTime2;
        public int appCpuSTime3;
        public int appCpuUTime1;
        public int appCpuUTime2;
        public int appCpuUTime3;
        public int appCpuUid1;
        public int appCpuUid2;
        public int appCpuUid3;
        public int statIOWaitTime;
        public int statIdlTime;
        public int statIrqTime;
        public String statPlatformIdleState;
        public int statSoftIrqTime;
        public String statSubsystemPowerState;
        public int statSystemTime;
        public int statUserTime;
        public int systemTime;
        public int userTime;

        public HistoryStepDetails() {
            this.clear();
        }

        public void clear() {
            this.systemTime = 0;
            this.userTime = 0;
            this.appCpuUid3 = -1;
            this.appCpuUid2 = -1;
            this.appCpuUid1 = -1;
            this.appCpuSTime3 = 0;
            this.appCpuUTime3 = 0;
            this.appCpuSTime2 = 0;
            this.appCpuUTime2 = 0;
            this.appCpuSTime1 = 0;
            this.appCpuUTime1 = 0;
        }

        public void readFromParcel(Parcel parcel) {
            this.userTime = parcel.readInt();
            this.systemTime = parcel.readInt();
            this.appCpuUid1 = parcel.readInt();
            this.appCpuUTime1 = parcel.readInt();
            this.appCpuSTime1 = parcel.readInt();
            this.appCpuUid2 = parcel.readInt();
            this.appCpuUTime2 = parcel.readInt();
            this.appCpuSTime2 = parcel.readInt();
            this.appCpuUid3 = parcel.readInt();
            this.appCpuUTime3 = parcel.readInt();
            this.appCpuSTime3 = parcel.readInt();
            this.statUserTime = parcel.readInt();
            this.statSystemTime = parcel.readInt();
            this.statIOWaitTime = parcel.readInt();
            this.statIrqTime = parcel.readInt();
            this.statSoftIrqTime = parcel.readInt();
            this.statIdlTime = parcel.readInt();
            this.statPlatformIdleState = parcel.readString();
            this.statSubsystemPowerState = parcel.readString();
        }

        public void writeToParcel(Parcel parcel) {
            parcel.writeInt(this.userTime);
            parcel.writeInt(this.systemTime);
            parcel.writeInt(this.appCpuUid1);
            parcel.writeInt(this.appCpuUTime1);
            parcel.writeInt(this.appCpuSTime1);
            parcel.writeInt(this.appCpuUid2);
            parcel.writeInt(this.appCpuUTime2);
            parcel.writeInt(this.appCpuSTime2);
            parcel.writeInt(this.appCpuUid3);
            parcel.writeInt(this.appCpuUTime3);
            parcel.writeInt(this.appCpuSTime3);
            parcel.writeInt(this.statUserTime);
            parcel.writeInt(this.statSystemTime);
            parcel.writeInt(this.statIOWaitTime);
            parcel.writeInt(this.statIrqTime);
            parcel.writeInt(this.statSoftIrqTime);
            parcel.writeInt(this.statIdlTime);
            parcel.writeString(this.statPlatformIdleState);
            parcel.writeString(this.statSubsystemPowerState);
        }
    }

    public static final class HistoryTag {
        public int poolIdx;
        public String string;
        public int uid;

        public boolean equals(Object object) {
            if (this == object) {
                return true;
            }
            if (object != null && this.getClass() == object.getClass()) {
                object = (HistoryTag)object;
                if (this.uid != ((HistoryTag)object).uid) {
                    return false;
                }
                return this.string.equals(((HistoryTag)object).string);
            }
            return false;
        }

        public int hashCode() {
            return this.string.hashCode() * 31 + this.uid;
        }

        public void readFromParcel(Parcel parcel) {
            this.string = parcel.readString();
            this.uid = parcel.readInt();
            this.poolIdx = -1;
        }

        public void setTo(HistoryTag historyTag) {
            this.string = historyTag.string;
            this.uid = historyTag.uid;
            this.poolIdx = historyTag.poolIdx;
        }

        public void setTo(String string2, int n) {
            this.string = string2;
            this.uid = n;
            this.poolIdx = -1;
        }

        public void writeToParcel(Parcel parcel, int n) {
            parcel.writeString(this.string);
            parcel.writeInt(this.uid);
        }
    }

    @FunctionalInterface
    public static interface IntToString {
        public String applyAsString(int var1);
    }

    public static final class LevelStepTracker {
        public long mLastStepTime = -1L;
        public int mNumStepDurations;
        public final long[] mStepDurations;

        public LevelStepTracker(int n) {
            this.mStepDurations = new long[n];
        }

        public LevelStepTracker(int n, long[] arrl) {
            this.mNumStepDurations = n;
            this.mStepDurations = new long[n];
            System.arraycopy(arrl, 0, this.mStepDurations, 0, n);
        }

        private void appendHex(long l, int n, StringBuilder stringBuilder) {
            int n2 = 0;
            int n3 = n;
            n = n2;
            while (n3 >= 0) {
                n2 = (int)(l >> n3 & 15L);
                n3 -= 4;
                if (n == 0 && n2 == 0) continue;
                n = 1;
                if (n2 >= 0 && n2 <= 9) {
                    stringBuilder.append((char)(n2 + 48));
                    continue;
                }
                stringBuilder.append((char)(n2 + 97 - 10));
            }
        }

        public void addLevelSteps(int n, long l, long l2) {
            int n2 = this.mNumStepDurations;
            long l3 = this.mLastStepTime;
            int n3 = n2;
            if (l3 >= 0L) {
                n3 = n2;
                if (n > 0) {
                    long[] arrl = this.mStepDurations;
                    l3 = l2 - l3;
                    for (n3 = 0; n3 < n; ++n3) {
                        System.arraycopy(arrl, 0, arrl, 1, arrl.length - 1);
                        long l4 = l3 / (long)(n - n3);
                        long l5 = l3 - l4;
                        l3 = l4;
                        if (l4 > 0xFFFFFFFFFFL) {
                            l3 = 0xFFFFFFFFFFL;
                        }
                        arrl[0] = l3 | l;
                        l3 = l5;
                    }
                    n3 = n = n2 + n;
                    if (n > arrl.length) {
                        n3 = arrl.length;
                    }
                }
            }
            this.mNumStepDurations = n3;
            this.mLastStepTime = l2;
        }

        public void clearTime() {
            this.mLastStepTime = -1L;
        }

        public long computeTimeEstimate(long l, long l2, int[] arrn) {
            long[] arrl = this.mStepDurations;
            int n = this.mNumStepDurations;
            if (n <= 0) {
                return -1L;
            }
            long l3 = 0L;
            int n2 = 0;
            for (int i = 0; i < n; ++i) {
                long l4 = arrl[i];
                long l5 = l3;
                int n3 = n2;
                if (((arrl[i] & -72057594037927936L) >> 56 & l) == 0L) {
                    l5 = l3;
                    n3 = n2;
                    if (((l4 & 0xFF000000000000L) >> 48 & l) == l2) {
                        n3 = n2 + 1;
                        l5 = l3 + (arrl[i] & 0xFFFFFFFFFFL);
                    }
                }
                l3 = l5;
                n2 = n3;
            }
            if (n2 <= 0) {
                return -1L;
            }
            if (arrn != null) {
                arrn[0] = n2;
            }
            return l3 / (long)n2 * 100L;
        }

        public long computeTimePerLevel() {
            long[] arrl = this.mStepDurations;
            int n = this.mNumStepDurations;
            if (n <= 0) {
                return -1L;
            }
            long l = 0L;
            for (int i = 0; i < n; ++i) {
                l += arrl[i] & 0xFFFFFFFFFFL;
            }
            return l / (long)n;
        }

        public void decodeEntryAt(int n, String string2) {
            long l;
            long l2;
            int n2;
            char c;
            int n3 = string2.length();
            int n4 = 0;
            long l3 = 0L;
            while (n4 < n3 && (n2 = string2.charAt(n4)) != 45) {
                ++n4;
                l = n2 != 68 ? (n2 != 70 ? (n2 != 73 ? (n2 != 90 ? (n2 != 100 ? (n2 != 102 ? (n2 != 105 ? (n2 != 122 ? (n2 != 79 ? (n2 != 80 ? (n2 != 111 ? (n2 != 112 ? l3 : l3 | 0x4000000000000L) : l3 | 0x1000000000000L) : l3 | 0x400000000000000L) : l3 | 0x100000000000000L) : l3 | 0x3000000000000L) : l3 | 0x8000000000000L) : l3 | 0L) : l3 | 0x2000000000000L) : l3 | 0x300000000000000L) : l3 | 0x800000000000000L) : l3 | 0L) : l3 | 0x200000000000000L;
                l3 = l;
            }
            ++n4;
            l = 0L;
            while (n4 < n3 && (c = string2.charAt(n4)) != '-') {
                n2 = n4 + 1;
                l2 = l << 4;
                if (c >= '0' && c <= '9') {
                    l = l2 + (long)(c - 48);
                    n4 = n2;
                    continue;
                }
                if (c >= 'a' && c <= 'f') {
                    l = l2 + (long)(c - 97 + 10);
                    n4 = n2;
                    continue;
                }
                n4 = n2;
                l = l2;
                if (c < 'A') continue;
                n4 = n2;
                l = l2;
                if (c > 'F') continue;
                l = l2 + (long)(c - 65 + 10);
                n4 = n2;
            }
            ++n4;
            l2 = 0L;
            while (n4 < n3 && (n2 = string2.charAt(n4)) != 45) {
                ++n4;
                l2 <<= 4;
                if (n2 >= 48 && n2 <= 57) {
                    l2 += (long)(n2 - 48);
                    continue;
                }
                if (n2 >= 97 && n2 <= 102) {
                    l2 += (long)(n2 - 97 + 10);
                    continue;
                }
                if (n2 < 65 || n2 > 70) continue;
                l2 += (long)(n2 - 65 + 10);
            }
            this.mStepDurations[n] = 0xFFFFFFFFFFL & l2 | (l3 | l << 40 & 0xFF0000000000L);
        }

        public void encodeEntryAt(int n, StringBuilder stringBuilder) {
            long l = this.mStepDurations[n];
            n = (int)((0xFF0000000000L & l) >> 40);
            int n2 = (int)((0xFF000000000000L & l) >> 48);
            int n3 = (int)((-72057594037927936L & l) >> 56);
            int n4 = (n2 & 3) + 1;
            if (n4 != 1) {
                if (n4 != 2) {
                    if (n4 != 3) {
                        if (n4 == 4) {
                            stringBuilder.append('z');
                        }
                    } else {
                        stringBuilder.append('d');
                    }
                } else {
                    stringBuilder.append('o');
                }
            } else {
                stringBuilder.append('f');
            }
            if ((n2 & 4) != 0) {
                stringBuilder.append('p');
            }
            if ((n2 & 8) != 0) {
                stringBuilder.append('i');
            }
            if ((n4 = (n3 & 3) + 1) != 1) {
                if (n4 != 2) {
                    if (n4 != 3) {
                        if (n4 == 4) {
                            stringBuilder.append('Z');
                        }
                    } else {
                        stringBuilder.append('D');
                    }
                } else {
                    stringBuilder.append('O');
                }
            } else {
                stringBuilder.append('F');
            }
            if ((n3 & 4) != 0) {
                stringBuilder.append('P');
            }
            if ((n3 & 8) != 0) {
                stringBuilder.append('I');
            }
            stringBuilder.append('-');
            this.appendHex(n, 4, stringBuilder);
            stringBuilder.append('-');
            this.appendHex(0xFFFFFFFFFFL & l, 36, stringBuilder);
        }

        public long getDurationAt(int n) {
            return this.mStepDurations[n] & 0xFFFFFFFFFFL;
        }

        public int getInitModeAt(int n) {
            return (int)((this.mStepDurations[n] & 0xFF000000000000L) >> 48);
        }

        public int getLevelAt(int n) {
            return (int)((this.mStepDurations[n] & 0xFF0000000000L) >> 40);
        }

        public int getModModeAt(int n) {
            return (int)((this.mStepDurations[n] & -72057594037927936L) >> 56);
        }

        public void init() {
            this.mLastStepTime = -1L;
            this.mNumStepDurations = 0;
        }

        public void readFromParcel(Parcel object) {
            int n = ((Parcel)object).readInt();
            if (n <= this.mStepDurations.length) {
                this.mNumStepDurations = n;
                for (int i = 0; i < n; ++i) {
                    this.mStepDurations[i] = ((Parcel)object).readLong();
                }
                return;
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("more step durations than available: ");
            ((StringBuilder)object).append(n);
            throw new ParcelFormatException(((StringBuilder)object).toString());
        }

        public void writeToParcel(Parcel parcel) {
            int n = this.mNumStepDurations;
            parcel.writeInt(n);
            for (int i = 0; i < n; ++i) {
                parcel.writeLong(this.mStepDurations[i]);
            }
        }
    }

    public static abstract class LongCounter {
        public abstract long getCountLocked(int var1);

        public abstract void logState(Printer var1, String var2);
    }

    public static abstract class LongCounterArray {
        public abstract long[] getCountsLocked(int var1);

        public abstract void logState(Printer var1, String var2);
    }

    public static final class PackageChange {
        public String mPackageName;
        public boolean mUpdate;
        public long mVersionCode;
    }

    public static abstract class Timer {
        @UnsupportedAppUsage
        public abstract int getCountLocked(int var1);

        public long getCurrentDurationMsLocked(long l) {
            return -1L;
        }

        public long getMaxDurationMsLocked(long l) {
            return -1L;
        }

        public Timer getSubTimer() {
            return null;
        }

        public abstract long getTimeSinceMarkLocked(long var1);

        public long getTotalDurationMsLocked(long l) {
            return -1L;
        }

        @UnsupportedAppUsage
        public abstract long getTotalTimeLocked(long var1, int var3);

        public boolean isRunningLocked() {
            return false;
        }

        public abstract void logState(Printer var1, String var2);
    }

    static final class TimerEntry {
        final int mId;
        final String mName;
        final long mTime;
        final Timer mTimer;

        TimerEntry(String string2, int n, Timer timer, long l) {
            this.mName = string2;
            this.mId = n;
            this.mTimer = timer;
            this.mTime = l;
        }
    }

    public static abstract class Uid {
        public static final int[] CRITICAL_PROC_STATES;
        public static final int NUM_PROCESS_STATE = 7;
        public static final int NUM_USER_ACTIVITY_TYPES;
        public static final int NUM_WIFI_BATCHED_SCAN_BINS = 5;
        public static final int PROCESS_STATE_BACKGROUND = 3;
        public static final int PROCESS_STATE_CACHED = 6;
        public static final int PROCESS_STATE_FOREGROUND = 2;
        public static final int PROCESS_STATE_FOREGROUND_SERVICE = 1;
        public static final int PROCESS_STATE_HEAVY_WEIGHT = 5;
        static final String[] PROCESS_STATE_NAMES;
        public static final int PROCESS_STATE_TOP = 0;
        public static final int PROCESS_STATE_TOP_SLEEPING = 4;
        @VisibleForTesting
        public static final String[] UID_PROCESS_TYPES;
        static final String[] USER_ACTIVITY_TYPES;

        static {
            PROCESS_STATE_NAMES = new String[]{"Top", "Fg Service", "Foreground", "Background", "Top Sleeping", "Heavy Weight", "Cached"};
            UID_PROCESS_TYPES = new String[]{"T", "FS", "F", "B", "TS", "HW", "C"};
            CRITICAL_PROC_STATES = new int[]{0, 3, 4, 1, 2};
            USER_ACTIVITY_TYPES = new String[]{"other", "button", "touch", "accessibility", "attention"};
            NUM_USER_ACTIVITY_TYPES = USER_ACTIVITY_TYPES.length;
        }

        public abstract Timer getAggregatedPartialWakelockTimer();

        @UnsupportedAppUsage
        public abstract Timer getAudioTurnedOnTimer();

        public abstract ControllerActivityCounter getBluetoothControllerActivity();

        public abstract Timer getBluetoothScanBackgroundTimer();

        public abstract Counter getBluetoothScanResultBgCounter();

        public abstract Counter getBluetoothScanResultCounter();

        public abstract Timer getBluetoothScanTimer();

        public abstract Timer getBluetoothUnoptimizedScanBackgroundTimer();

        public abstract Timer getBluetoothUnoptimizedScanTimer();

        public abstract Timer getCameraTurnedOnTimer();

        public abstract long getCpuActiveTime();

        public abstract long[] getCpuClusterTimes();

        public abstract long[] getCpuFreqTimes(int var1);

        public abstract long[] getCpuFreqTimes(int var1, int var2);

        public abstract void getDeferredJobsCheckinLineLocked(StringBuilder var1, int var2);

        public abstract void getDeferredJobsLineLocked(StringBuilder var1, int var2);

        public abstract Timer getFlashlightTurnedOnTimer();

        public abstract Timer getForegroundActivityTimer();

        public abstract Timer getForegroundServiceTimer();

        @UnsupportedAppUsage
        public abstract long getFullWifiLockTime(long var1, int var3);

        public abstract ArrayMap<String, SparseIntArray> getJobCompletionStats();

        public abstract ArrayMap<String, ? extends Timer> getJobStats();

        public abstract int getMobileRadioActiveCount(int var1);

        @UnsupportedAppUsage
        public abstract long getMobileRadioActiveTime(int var1);

        public abstract long getMobileRadioApWakeupCount(int var1);

        public abstract ControllerActivityCounter getModemControllerActivity();

        public abstract Timer getMulticastWakelockStats();

        @UnsupportedAppUsage
        public abstract long getNetworkActivityBytes(int var1, int var2);

        public abstract long getNetworkActivityPackets(int var1, int var2);

        @UnsupportedAppUsage
        public abstract ArrayMap<String, ? extends Pkg> getPackageStats();

        public abstract SparseArray<? extends Pid> getPidStats();

        public abstract long getProcessStateTime(int var1, long var2, int var4);

        public abstract Timer getProcessStateTimer(int var1);

        @UnsupportedAppUsage
        public abstract ArrayMap<String, ? extends Proc> getProcessStats();

        public abstract long[] getScreenOffCpuFreqTimes(int var1);

        public abstract long[] getScreenOffCpuFreqTimes(int var1, int var2);

        @UnsupportedAppUsage
        public abstract SparseArray<? extends Sensor> getSensorStats();

        public abstract ArrayMap<String, ? extends Timer> getSyncStats();

        public abstract long getSystemCpuTimeUs(int var1);

        public abstract long getTimeAtCpuSpeed(int var1, int var2, int var3);

        @UnsupportedAppUsage
        public abstract int getUid();

        public abstract int getUserActivityCount(int var1, int var2);

        public abstract long getUserCpuTimeUs(int var1);

        public abstract Timer getVibratorOnTimer();

        @UnsupportedAppUsage
        public abstract Timer getVideoTurnedOnTimer();

        @UnsupportedAppUsage
        public abstract ArrayMap<String, ? extends Wakelock> getWakelockStats();

        public abstract int getWifiBatchedScanCount(int var1, int var2);

        @UnsupportedAppUsage
        public abstract long getWifiBatchedScanTime(int var1, long var2, int var4);

        public abstract ControllerActivityCounter getWifiControllerActivity();

        @UnsupportedAppUsage
        public abstract long getWifiMulticastTime(long var1, int var3);

        public abstract long getWifiRadioApWakeupCount(int var1);

        @UnsupportedAppUsage
        public abstract long getWifiRunningTime(long var1, int var3);

        public abstract long getWifiScanActualTime(long var1);

        public abstract int getWifiScanBackgroundCount(int var1);

        public abstract long getWifiScanBackgroundTime(long var1);

        public abstract Timer getWifiScanBackgroundTimer();

        public abstract int getWifiScanCount(int var1);

        @UnsupportedAppUsage
        public abstract long getWifiScanTime(long var1, int var3);

        public abstract Timer getWifiScanTimer();

        public abstract boolean hasNetworkActivity();

        public abstract boolean hasUserActivity();

        public abstract void noteActivityPausedLocked(long var1);

        public abstract void noteActivityResumedLocked(long var1);

        public abstract void noteFullWifiLockAcquiredLocked(long var1);

        public abstract void noteFullWifiLockReleasedLocked(long var1);

        public abstract void noteUserActivityLocked(int var1);

        public abstract void noteWifiBatchedScanStartedLocked(int var1, long var2);

        public abstract void noteWifiBatchedScanStoppedLocked(long var1);

        public abstract void noteWifiMulticastDisabledLocked(long var1);

        public abstract void noteWifiMulticastEnabledLocked(long var1);

        public abstract void noteWifiRunningLocked(long var1);

        public abstract void noteWifiScanStartedLocked(long var1);

        public abstract void noteWifiScanStoppedLocked(long var1);

        public abstract void noteWifiStoppedLocked(long var1);

        public class Pid {
            public int mWakeNesting;
            public long mWakeStartMs;
            public long mWakeSumMs;
        }

        public static abstract class Pkg {
            @UnsupportedAppUsage
            public abstract ArrayMap<String, ? extends Serv> getServiceStats();

            @UnsupportedAppUsage
            public abstract ArrayMap<String, ? extends Counter> getWakeupAlarmStats();

            public static abstract class Serv {
                @UnsupportedAppUsage
                public abstract int getLaunches(int var1);

                @UnsupportedAppUsage
                public abstract long getStartTime(long var1, int var3);

                @UnsupportedAppUsage
                public abstract int getStarts(int var1);
            }

        }

        public static abstract class Proc {
            @UnsupportedAppUsage
            public abstract int countExcessivePowers();

            @UnsupportedAppUsage
            public abstract ExcessivePower getExcessivePower(int var1);

            @UnsupportedAppUsage
            public abstract long getForegroundTime(int var1);

            public abstract int getNumAnrs(int var1);

            public abstract int getNumCrashes(int var1);

            @UnsupportedAppUsage
            public abstract int getStarts(int var1);

            @UnsupportedAppUsage
            public abstract long getSystemTime(int var1);

            @UnsupportedAppUsage
            public abstract long getUserTime(int var1);

            public abstract boolean isActive();

            public static class ExcessivePower {
                public static final int TYPE_CPU = 2;
                public static final int TYPE_WAKE = 1;
                @UnsupportedAppUsage
                public long overTime;
                @UnsupportedAppUsage
                public int type;
                @UnsupportedAppUsage
                public long usedTime;
            }

        }

        public static abstract class Sensor {
            @UnsupportedAppUsage
            public static final int GPS = -10000;

            @UnsupportedAppUsage
            public abstract int getHandle();

            public abstract Timer getSensorBackgroundTime();

            @UnsupportedAppUsage
            public abstract Timer getSensorTime();
        }

        public static abstract class Wakelock {
            @UnsupportedAppUsage
            public abstract Timer getWakeTime(int var1);
        }

    }

}

