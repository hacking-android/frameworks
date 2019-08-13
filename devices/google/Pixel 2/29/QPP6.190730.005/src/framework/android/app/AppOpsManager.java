/*
 * Decompiled with CFR 0.145.
 */
package android.app;

import android.annotation.SystemApi;
import android.annotation.UnsupportedAppUsage;
import android.app.ActivityManager;
import android.app.ActivityThread;
import android.app._$$Lambda$AppOpsManager$4Zbi7CSLEt0nvOmfJBVYtJkauTQ;
import android.app._$$Lambda$AppOpsManager$5k42P8tID8pwpGFZvo7VQyru20E;
import android.app._$$Lambda$AppOpsManager$HistoricalOp$DkVcBvqB32SMHlxw0sWQPh3GL1A;
import android.app._$$Lambda$AppOpsManager$HistoricalOp$HUOLFYs8TiaQIOXcrq6JzjxA6gs;
import android.app._$$Lambda$AppOpsManager$HistoricalOp$Vs6pDL0wjOBTquwNnreWVbPQrn4;
import android.app._$$Lambda$AppOpsManager$VfNXTtJaOeEFrdIj0oDWr_N9nks;
import android.app._$$Lambda$AppOpsManager$frSyqmhVUmNbhMckfMS3PSwTMlw;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.pm.ParceledListSlice;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Process;
import android.os.RemoteCallback;
import android.os.RemoteException;
import android.util.ArrayMap;
import android.util.LongSparseArray;
import android.util.LongSparseLongArray;
import android.util.SparseArray;
import com.android.internal.annotations.GuardedBy;
import com.android.internal.annotations.Immutable;
import com.android.internal.app.IAppOpsActiveCallback;
import com.android.internal.app.IAppOpsCallback;
import com.android.internal.app.IAppOpsNotedCallback;
import com.android.internal.app.IAppOpsService;
import com.android.internal.util.ArrayUtils;
import com.android.internal.util.Preconditions;
import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Executor;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class AppOpsManager {
    private static final String DEBUG_LOGGING_ENABLE_PROP = "appops.logging_enabled";
    private static final String DEBUG_LOGGING_OPS_PROP = "appops.logging_ops";
    private static final String DEBUG_LOGGING_PACKAGES_PROP = "appops.logging_packages";
    private static final String DEBUG_LOGGING_TAG = "AppOpsManager";
    private static final int FLAGS_MASK = -1;
    public static final int HISTORICAL_MODE_DISABLED = 0;
    public static final int HISTORICAL_MODE_ENABLED_ACTIVE = 1;
    public static final int HISTORICAL_MODE_ENABLED_PASSIVE = 2;
    public static final String KEY_HISTORICAL_OPS = "historical_ops";
    public static final int MAX_PRIORITY_UID_STATE = 100;
    public static final int MIN_PRIORITY_UID_STATE = 700;
    public static final int MODE_ALLOWED = 0;
    public static final int MODE_DEFAULT = 3;
    public static final int MODE_ERRORED = 2;
    public static final int MODE_FOREGROUND = 4;
    public static final int MODE_IGNORED = 1;
    public static final String[] MODE_NAMES = new String[]{"allow", "ignore", "deny", "default", "foreground"};
    @SystemApi
    public static final String OPSTR_ACCEPT_HANDOVER = "android:accept_handover";
    @SystemApi
    public static final String OPSTR_ACCESS_ACCESSIBILITY = "android:access_accessibility";
    @SystemApi
    public static final String OPSTR_ACCESS_NOTIFICATIONS = "android:access_notifications";
    @SystemApi
    public static final String OPSTR_ACTIVATE_VPN = "android:activate_vpn";
    public static final String OPSTR_ACTIVITY_RECOGNITION = "android:activity_recognition";
    public static final String OPSTR_ADD_VOICEMAIL = "android:add_voicemail";
    public static final String OPSTR_ANSWER_PHONE_CALLS = "android:answer_phone_calls";
    @SystemApi
    public static final String OPSTR_ASSIST_SCREENSHOT = "android:assist_screenshot";
    @SystemApi
    public static final String OPSTR_ASSIST_STRUCTURE = "android:assist_structure";
    @SystemApi
    public static final String OPSTR_AUDIO_ACCESSIBILITY_VOLUME = "android:audio_accessibility_volume";
    @SystemApi
    public static final String OPSTR_AUDIO_ALARM_VOLUME = "android:audio_alarm_volume";
    @SystemApi
    public static final String OPSTR_AUDIO_BLUETOOTH_VOLUME = "android:audio_bluetooth_volume";
    @SystemApi
    public static final String OPSTR_AUDIO_MASTER_VOLUME = "android:audio_master_volume";
    @SystemApi
    public static final String OPSTR_AUDIO_MEDIA_VOLUME = "android:audio_media_volume";
    @SystemApi
    public static final String OPSTR_AUDIO_NOTIFICATION_VOLUME = "android:audio_notification_volume";
    @SystemApi
    public static final String OPSTR_AUDIO_RING_VOLUME = "android:audio_ring_volume";
    @SystemApi
    public static final String OPSTR_AUDIO_VOICE_VOLUME = "android:audio_voice_volume";
    @SystemApi
    public static final String OPSTR_BIND_ACCESSIBILITY_SERVICE = "android:bind_accessibility_service";
    public static final String OPSTR_BLUETOOTH_SCAN = "android:bluetooth_scan";
    public static final String OPSTR_BODY_SENSORS = "android:body_sensors";
    public static final String OPSTR_CALL_PHONE = "android:call_phone";
    public static final String OPSTR_CAMERA = "android:camera";
    @SystemApi
    public static final String OPSTR_CHANGE_WIFI_STATE = "android:change_wifi_state";
    public static final String OPSTR_COARSE_LOCATION = "android:coarse_location";
    public static final String OPSTR_FINE_LOCATION = "android:fine_location";
    @SystemApi
    public static final String OPSTR_GET_ACCOUNTS = "android:get_accounts";
    public static final String OPSTR_GET_USAGE_STATS = "android:get_usage_stats";
    @SystemApi
    public static final String OPSTR_GPS = "android:gps";
    @SystemApi
    public static final String OPSTR_INSTANT_APP_START_FOREGROUND = "android:instant_app_start_foreground";
    @SystemApi
    public static final String OPSTR_LEGACY_STORAGE = "android:legacy_storage";
    @SystemApi
    public static final String OPSTR_MANAGE_IPSEC_TUNNELS = "android:manage_ipsec_tunnels";
    public static final String OPSTR_MOCK_LOCATION = "android:mock_location";
    public static final String OPSTR_MONITOR_HIGH_POWER_LOCATION = "android:monitor_location_high_power";
    public static final String OPSTR_MONITOR_LOCATION = "android:monitor_location";
    @SystemApi
    public static final String OPSTR_MUTE_MICROPHONE = "android:mute_microphone";
    @SystemApi
    public static final String OPSTR_NEIGHBORING_CELLS = "android:neighboring_cells";
    public static final String OPSTR_PICTURE_IN_PICTURE = "android:picture_in_picture";
    @SystemApi
    public static final String OPSTR_PLAY_AUDIO = "android:play_audio";
    @SystemApi
    public static final String OPSTR_POST_NOTIFICATION = "android:post_notification";
    public static final String OPSTR_PROCESS_OUTGOING_CALLS = "android:process_outgoing_calls";
    @SystemApi
    public static final String OPSTR_PROJECT_MEDIA = "android:project_media";
    public static final String OPSTR_READ_CALENDAR = "android:read_calendar";
    public static final String OPSTR_READ_CALL_LOG = "android:read_call_log";
    public static final String OPSTR_READ_CELL_BROADCASTS = "android:read_cell_broadcasts";
    @SystemApi
    public static final String OPSTR_READ_CLIPBOARD = "android:read_clipboard";
    public static final String OPSTR_READ_CONTACTS = "android:read_contacts";
    public static final String OPSTR_READ_DEVICE_IDENTIFIERS = "android:read_device_identifiers";
    public static final String OPSTR_READ_EXTERNAL_STORAGE = "android:read_external_storage";
    @SystemApi
    public static final String OPSTR_READ_ICC_SMS = "android:read_icc_sms";
    public static final String OPSTR_READ_MEDIA_AUDIO = "android:read_media_audio";
    public static final String OPSTR_READ_MEDIA_IMAGES = "android:read_media_images";
    public static final String OPSTR_READ_MEDIA_VIDEO = "android:read_media_video";
    public static final String OPSTR_READ_PHONE_NUMBERS = "android:read_phone_numbers";
    public static final String OPSTR_READ_PHONE_STATE = "android:read_phone_state";
    public static final String OPSTR_READ_SMS = "android:read_sms";
    @SystemApi
    public static final String OPSTR_RECEIVE_EMERGENCY_BROADCAST = "android:receive_emergency_broadcast";
    public static final String OPSTR_RECEIVE_MMS = "android:receive_mms";
    public static final String OPSTR_RECEIVE_SMS = "android:receive_sms";
    public static final String OPSTR_RECEIVE_WAP_PUSH = "android:receive_wap_push";
    public static final String OPSTR_RECORD_AUDIO = "android:record_audio";
    @SystemApi
    public static final String OPSTR_REQUEST_DELETE_PACKAGES = "android:request_delete_packages";
    @SystemApi
    public static final String OPSTR_REQUEST_INSTALL_PACKAGES = "android:request_install_packages";
    @SystemApi
    public static final String OPSTR_RUN_ANY_IN_BACKGROUND = "android:run_any_in_background";
    @SystemApi
    public static final String OPSTR_RUN_IN_BACKGROUND = "android:run_in_background";
    public static final String OPSTR_SEND_SMS = "android:send_sms";
    public static final String OPSTR_SMS_FINANCIAL_TRANSACTIONS = "android:sms_financial_transactions";
    @SystemApi
    public static final String OPSTR_START_FOREGROUND = "android:start_foreground";
    public static final String OPSTR_SYSTEM_ALERT_WINDOW = "android:system_alert_window";
    @SystemApi
    public static final String OPSTR_TAKE_AUDIO_FOCUS = "android:take_audio_focus";
    @SystemApi
    public static final String OPSTR_TAKE_MEDIA_BUTTONS = "android:take_media_buttons";
    @SystemApi
    public static final String OPSTR_TOAST_WINDOW = "android:toast_window";
    @SystemApi
    public static final String OPSTR_TURN_SCREEN_ON = "android:turn_screen_on";
    public static final String OPSTR_USE_BIOMETRIC = "android:use_biometric";
    public static final String OPSTR_USE_FINGERPRINT = "android:use_fingerprint";
    public static final String OPSTR_USE_SIP = "android:use_sip";
    @SystemApi
    public static final String OPSTR_VIBRATE = "android:vibrate";
    @SystemApi
    public static final String OPSTR_WAKE_LOCK = "android:wake_lock";
    @SystemApi
    public static final String OPSTR_WIFI_SCAN = "android:wifi_scan";
    public static final String OPSTR_WRITE_CALENDAR = "android:write_calendar";
    public static final String OPSTR_WRITE_CALL_LOG = "android:write_call_log";
    @SystemApi
    public static final String OPSTR_WRITE_CLIPBOARD = "android:write_clipboard";
    public static final String OPSTR_WRITE_CONTACTS = "android:write_contacts";
    public static final String OPSTR_WRITE_EXTERNAL_STORAGE = "android:write_external_storage";
    @SystemApi
    public static final String OPSTR_WRITE_ICC_SMS = "android:write_icc_sms";
    public static final String OPSTR_WRITE_MEDIA_AUDIO = "android:write_media_audio";
    public static final String OPSTR_WRITE_MEDIA_IMAGES = "android:write_media_images";
    public static final String OPSTR_WRITE_MEDIA_VIDEO = "android:write_media_video";
    public static final String OPSTR_WRITE_SETTINGS = "android:write_settings";
    @SystemApi
    public static final String OPSTR_WRITE_SMS = "android:write_sms";
    @SystemApi
    public static final String OPSTR_WRITE_WALLPAPER = "android:write_wallpaper";
    @UnsupportedAppUsage
    public static final int OP_ACCEPT_HANDOVER = 74;
    public static final int OP_ACCESS_ACCESSIBILITY = 88;
    @UnsupportedAppUsage
    public static final int OP_ACCESS_NOTIFICATIONS = 25;
    @UnsupportedAppUsage
    public static final int OP_ACTIVATE_VPN = 47;
    public static final int OP_ACTIVITY_RECOGNITION = 79;
    @UnsupportedAppUsage
    public static final int OP_ADD_VOICEMAIL = 52;
    @UnsupportedAppUsage
    public static final int OP_ANSWER_PHONE_CALLS = 69;
    @UnsupportedAppUsage
    public static final int OP_ASSIST_SCREENSHOT = 50;
    @UnsupportedAppUsage
    public static final int OP_ASSIST_STRUCTURE = 49;
    @UnsupportedAppUsage
    public static final int OP_AUDIO_ACCESSIBILITY_VOLUME = 64;
    @UnsupportedAppUsage
    public static final int OP_AUDIO_ALARM_VOLUME = 37;
    @UnsupportedAppUsage
    public static final int OP_AUDIO_BLUETOOTH_VOLUME = 39;
    @UnsupportedAppUsage
    public static final int OP_AUDIO_MASTER_VOLUME = 33;
    @UnsupportedAppUsage
    public static final int OP_AUDIO_MEDIA_VOLUME = 36;
    @UnsupportedAppUsage
    public static final int OP_AUDIO_NOTIFICATION_VOLUME = 38;
    @UnsupportedAppUsage
    public static final int OP_AUDIO_RING_VOLUME = 35;
    @UnsupportedAppUsage
    public static final int OP_AUDIO_VOICE_VOLUME = 34;
    @UnsupportedAppUsage
    public static final int OP_BIND_ACCESSIBILITY_SERVICE = 73;
    @UnsupportedAppUsage
    public static final int OP_BLUETOOTH_SCAN = 77;
    @UnsupportedAppUsage
    public static final int OP_BODY_SENSORS = 56;
    @UnsupportedAppUsage
    public static final int OP_CALL_PHONE = 13;
    @UnsupportedAppUsage
    public static final int OP_CAMERA = 26;
    @UnsupportedAppUsage
    public static final int OP_CHANGE_WIFI_STATE = 71;
    public static final int OP_COARSE_LOCATION = 0;
    @UnsupportedAppUsage
    public static final int OP_FINE_LOCATION = 1;
    @SystemApi
    public static final int OP_FLAGS_ALL = 31;
    @SystemApi
    public static final int OP_FLAGS_ALL_TRUSTED = 13;
    @SystemApi
    public static final int OP_FLAG_SELF = 1;
    @SystemApi
    public static final int OP_FLAG_TRUSTED_PROXIED = 8;
    @SystemApi
    public static final int OP_FLAG_TRUSTED_PROXY = 2;
    @SystemApi
    public static final int OP_FLAG_UNTRUSTED_PROXIED = 16;
    @SystemApi
    public static final int OP_FLAG_UNTRUSTED_PROXY = 4;
    @UnsupportedAppUsage
    public static final int OP_GET_ACCOUNTS = 62;
    @UnsupportedAppUsage
    public static final int OP_GET_USAGE_STATS = 43;
    @UnsupportedAppUsage
    public static final int OP_GPS = 2;
    @UnsupportedAppUsage
    public static final int OP_INSTANT_APP_START_FOREGROUND = 68;
    public static final int OP_LEGACY_STORAGE = 87;
    @UnsupportedAppUsage
    public static final int OP_MANAGE_IPSEC_TUNNELS = 75;
    @UnsupportedAppUsage
    public static final int OP_MOCK_LOCATION = 58;
    @UnsupportedAppUsage
    public static final int OP_MONITOR_HIGH_POWER_LOCATION = 42;
    @UnsupportedAppUsage
    public static final int OP_MONITOR_LOCATION = 41;
    @UnsupportedAppUsage
    public static final int OP_MUTE_MICROPHONE = 44;
    @UnsupportedAppUsage
    public static final int OP_NEIGHBORING_CELLS = 12;
    @UnsupportedAppUsage
    public static final int OP_NONE = -1;
    @UnsupportedAppUsage
    public static final int OP_PICTURE_IN_PICTURE = 67;
    @UnsupportedAppUsage
    public static final int OP_PLAY_AUDIO = 28;
    @UnsupportedAppUsage
    public static final int OP_POST_NOTIFICATION = 11;
    @UnsupportedAppUsage
    public static final int OP_PROCESS_OUTGOING_CALLS = 54;
    @UnsupportedAppUsage
    public static final int OP_PROJECT_MEDIA = 46;
    @UnsupportedAppUsage
    public static final int OP_READ_CALENDAR = 8;
    @UnsupportedAppUsage
    public static final int OP_READ_CALL_LOG = 6;
    @UnsupportedAppUsage
    public static final int OP_READ_CELL_BROADCASTS = 57;
    @UnsupportedAppUsage
    public static final int OP_READ_CLIPBOARD = 29;
    @UnsupportedAppUsage
    public static final int OP_READ_CONTACTS = 4;
    public static final int OP_READ_DEVICE_IDENTIFIERS = 89;
    @UnsupportedAppUsage
    public static final int OP_READ_EXTERNAL_STORAGE = 59;
    @UnsupportedAppUsage
    public static final int OP_READ_ICC_SMS = 21;
    public static final int OP_READ_MEDIA_AUDIO = 81;
    public static final int OP_READ_MEDIA_IMAGES = 85;
    public static final int OP_READ_MEDIA_VIDEO = 83;
    @UnsupportedAppUsage
    public static final int OP_READ_PHONE_NUMBERS = 65;
    @UnsupportedAppUsage
    public static final int OP_READ_PHONE_STATE = 51;
    @UnsupportedAppUsage
    public static final int OP_READ_SMS = 14;
    @UnsupportedAppUsage
    public static final int OP_RECEIVE_EMERGECY_SMS = 17;
    @UnsupportedAppUsage
    public static final int OP_RECEIVE_MMS = 18;
    @UnsupportedAppUsage
    public static final int OP_RECEIVE_SMS = 16;
    @UnsupportedAppUsage
    public static final int OP_RECEIVE_WAP_PUSH = 19;
    public static final int OP_RECORD_AUDIO = 27;
    @UnsupportedAppUsage
    public static final int OP_REQUEST_DELETE_PACKAGES = 72;
    @UnsupportedAppUsage
    public static final int OP_REQUEST_INSTALL_PACKAGES = 66;
    @UnsupportedAppUsage
    public static final int OP_RUN_ANY_IN_BACKGROUND = 70;
    @UnsupportedAppUsage
    public static final int OP_RUN_IN_BACKGROUND = 63;
    @UnsupportedAppUsage
    public static final int OP_SEND_SMS = 20;
    public static final int OP_SMS_FINANCIAL_TRANSACTIONS = 80;
    public static final int OP_START_FOREGROUND = 76;
    public static final int OP_SYSTEM_ALERT_WINDOW = 24;
    @UnsupportedAppUsage
    public static final int OP_TAKE_AUDIO_FOCUS = 32;
    @UnsupportedAppUsage
    public static final int OP_TAKE_MEDIA_BUTTONS = 31;
    @UnsupportedAppUsage
    public static final int OP_TOAST_WINDOW = 45;
    @UnsupportedAppUsage
    public static final int OP_TURN_SCREEN_ON = 61;
    public static final int OP_USE_BIOMETRIC = 78;
    @UnsupportedAppUsage
    public static final int OP_USE_FINGERPRINT = 55;
    @UnsupportedAppUsage
    public static final int OP_USE_SIP = 53;
    @UnsupportedAppUsage
    public static final int OP_VIBRATE = 3;
    @UnsupportedAppUsage
    public static final int OP_WAKE_LOCK = 40;
    @UnsupportedAppUsage
    public static final int OP_WIFI_SCAN = 10;
    @UnsupportedAppUsage
    public static final int OP_WRITE_CALENDAR = 9;
    @UnsupportedAppUsage
    public static final int OP_WRITE_CALL_LOG = 7;
    @UnsupportedAppUsage
    public static final int OP_WRITE_CLIPBOARD = 30;
    @UnsupportedAppUsage
    public static final int OP_WRITE_CONTACTS = 5;
    @UnsupportedAppUsage
    public static final int OP_WRITE_EXTERNAL_STORAGE = 60;
    @UnsupportedAppUsage
    public static final int OP_WRITE_ICC_SMS = 22;
    public static final int OP_WRITE_MEDIA_AUDIO = 82;
    public static final int OP_WRITE_MEDIA_IMAGES = 86;
    public static final int OP_WRITE_MEDIA_VIDEO = 84;
    @UnsupportedAppUsage
    public static final int OP_WRITE_SETTINGS = 23;
    @UnsupportedAppUsage
    public static final int OP_WRITE_SMS = 15;
    @UnsupportedAppUsage
    public static final int OP_WRITE_WALLPAPER = 48;
    private static final int[] RUNTIME_AND_APPOP_PERMISSIONS_OPS;
    public static final int[] UID_STATES;
    @SystemApi
    public static final int UID_STATE_BACKGROUND = 600;
    @SystemApi
    public static final int UID_STATE_CACHED = 700;
    @SystemApi
    public static final int UID_STATE_FOREGROUND = 500;
    @SystemApi
    public static final int UID_STATE_FOREGROUND_SERVICE = 400;
    @SystemApi
    public static final int UID_STATE_FOREGROUND_SERVICE_LOCATION = 300;
    public static final int UID_STATE_MAX_LAST_NON_RESTRICTED = 400;
    private static final int UID_STATE_OFFSET = 31;
    @SystemApi
    public static final int UID_STATE_PERSISTENT = 100;
    @SystemApi
    public static final int UID_STATE_TOP = 200;
    public static final int WATCH_FOREGROUND_CHANGES = 1;
    @UnsupportedAppUsage
    public static final int _NUM_OP = 90;
    private static boolean[] sOpAllowSystemRestrictionBypass;
    private static int[] sOpDefaultMode;
    private static boolean[] sOpDisableReset;
    private static String[] sOpNames;
    @UnsupportedAppUsage
    private static String[] sOpPerms;
    private static String[] sOpRestrictions;
    private static HashMap<String, Integer> sOpStrToOp;
    private static String[] sOpToString;
    private static int[] sOpToSwitch;
    private static HashMap<String, Integer> sPermToOp;
    static IBinder sToken;
    @GuardedBy(value={"mActiveWatchers"})
    private final ArrayMap<OnOpActiveChangedListener, IAppOpsActiveCallback> mActiveWatchers = new ArrayMap();
    final Context mContext;
    @GuardedBy(value={"mModeWatchers"})
    private final ArrayMap<OnOpChangedListener, IAppOpsCallback> mModeWatchers = new ArrayMap();
    @GuardedBy(value={"mNotedWatchers"})
    private final ArrayMap<OnOpNotedListener, IAppOpsNotedCallback> mNotedWatchers = new ArrayMap();
    @UnsupportedAppUsage
    final IAppOpsService mService;

    static {
        UID_STATES = new int[]{100, 200, 300, 400, 500, 600, 700};
        RUNTIME_AND_APPOP_PERMISSIONS_OPS = new int[]{4, 5, 62, 8, 9, 20, 16, 14, 19, 18, 57, 59, 60, 0, 1, 51, 65, 13, 6, 7, 52, 53, 54, 69, 74, 27, 26, 56, 79, 81, 82, 83, 84, 85, 86, 25, 24, 23, 66, 76, 80};
        sOpToSwitch = new int[]{0, 0, 0, 3, 4, 5, 6, 7, 8, 9, 0, 11, 0, 13, 14, 15, 16, 16, 18, 19, 20, 14, 15, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 0, 0, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, 62, 63, 64, 65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 0, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89};
        sOpToString = new String[]{OPSTR_COARSE_LOCATION, OPSTR_FINE_LOCATION, OPSTR_GPS, OPSTR_VIBRATE, OPSTR_READ_CONTACTS, OPSTR_WRITE_CONTACTS, OPSTR_READ_CALL_LOG, OPSTR_WRITE_CALL_LOG, OPSTR_READ_CALENDAR, OPSTR_WRITE_CALENDAR, OPSTR_WIFI_SCAN, OPSTR_POST_NOTIFICATION, OPSTR_NEIGHBORING_CELLS, OPSTR_CALL_PHONE, OPSTR_READ_SMS, OPSTR_WRITE_SMS, OPSTR_RECEIVE_SMS, OPSTR_RECEIVE_EMERGENCY_BROADCAST, OPSTR_RECEIVE_MMS, OPSTR_RECEIVE_WAP_PUSH, OPSTR_SEND_SMS, OPSTR_READ_ICC_SMS, OPSTR_WRITE_ICC_SMS, OPSTR_WRITE_SETTINGS, OPSTR_SYSTEM_ALERT_WINDOW, OPSTR_ACCESS_NOTIFICATIONS, OPSTR_CAMERA, OPSTR_RECORD_AUDIO, OPSTR_PLAY_AUDIO, OPSTR_READ_CLIPBOARD, OPSTR_WRITE_CLIPBOARD, OPSTR_TAKE_MEDIA_BUTTONS, OPSTR_TAKE_AUDIO_FOCUS, OPSTR_AUDIO_MASTER_VOLUME, OPSTR_AUDIO_VOICE_VOLUME, OPSTR_AUDIO_RING_VOLUME, OPSTR_AUDIO_MEDIA_VOLUME, OPSTR_AUDIO_ALARM_VOLUME, OPSTR_AUDIO_NOTIFICATION_VOLUME, OPSTR_AUDIO_BLUETOOTH_VOLUME, OPSTR_WAKE_LOCK, OPSTR_MONITOR_LOCATION, OPSTR_MONITOR_HIGH_POWER_LOCATION, OPSTR_GET_USAGE_STATS, OPSTR_MUTE_MICROPHONE, OPSTR_TOAST_WINDOW, OPSTR_PROJECT_MEDIA, OPSTR_ACTIVATE_VPN, OPSTR_WRITE_WALLPAPER, OPSTR_ASSIST_STRUCTURE, OPSTR_ASSIST_SCREENSHOT, OPSTR_READ_PHONE_STATE, OPSTR_ADD_VOICEMAIL, OPSTR_USE_SIP, OPSTR_PROCESS_OUTGOING_CALLS, OPSTR_USE_FINGERPRINT, OPSTR_BODY_SENSORS, OPSTR_READ_CELL_BROADCASTS, OPSTR_MOCK_LOCATION, OPSTR_READ_EXTERNAL_STORAGE, OPSTR_WRITE_EXTERNAL_STORAGE, OPSTR_TURN_SCREEN_ON, OPSTR_GET_ACCOUNTS, OPSTR_RUN_IN_BACKGROUND, OPSTR_AUDIO_ACCESSIBILITY_VOLUME, OPSTR_READ_PHONE_NUMBERS, OPSTR_REQUEST_INSTALL_PACKAGES, OPSTR_PICTURE_IN_PICTURE, OPSTR_INSTANT_APP_START_FOREGROUND, OPSTR_ANSWER_PHONE_CALLS, OPSTR_RUN_ANY_IN_BACKGROUND, OPSTR_CHANGE_WIFI_STATE, OPSTR_REQUEST_DELETE_PACKAGES, OPSTR_BIND_ACCESSIBILITY_SERVICE, OPSTR_ACCEPT_HANDOVER, OPSTR_MANAGE_IPSEC_TUNNELS, OPSTR_START_FOREGROUND, OPSTR_BLUETOOTH_SCAN, OPSTR_USE_BIOMETRIC, OPSTR_ACTIVITY_RECOGNITION, OPSTR_SMS_FINANCIAL_TRANSACTIONS, OPSTR_READ_MEDIA_AUDIO, OPSTR_WRITE_MEDIA_AUDIO, OPSTR_READ_MEDIA_VIDEO, OPSTR_WRITE_MEDIA_VIDEO, OPSTR_READ_MEDIA_IMAGES, OPSTR_WRITE_MEDIA_IMAGES, OPSTR_LEGACY_STORAGE, OPSTR_ACCESS_ACCESSIBILITY, OPSTR_READ_DEVICE_IDENTIFIERS};
        sOpNames = new String[]{"COARSE_LOCATION", "FINE_LOCATION", "GPS", "VIBRATE", "READ_CONTACTS", "WRITE_CONTACTS", "READ_CALL_LOG", "WRITE_CALL_LOG", "READ_CALENDAR", "WRITE_CALENDAR", "WIFI_SCAN", "POST_NOTIFICATION", "NEIGHBORING_CELLS", "CALL_PHONE", "READ_SMS", "WRITE_SMS", "RECEIVE_SMS", "RECEIVE_EMERGECY_SMS", "RECEIVE_MMS", "RECEIVE_WAP_PUSH", "SEND_SMS", "READ_ICC_SMS", "WRITE_ICC_SMS", "WRITE_SETTINGS", "SYSTEM_ALERT_WINDOW", "ACCESS_NOTIFICATIONS", "CAMERA", "RECORD_AUDIO", "PLAY_AUDIO", "READ_CLIPBOARD", "WRITE_CLIPBOARD", "TAKE_MEDIA_BUTTONS", "TAKE_AUDIO_FOCUS", "AUDIO_MASTER_VOLUME", "AUDIO_VOICE_VOLUME", "AUDIO_RING_VOLUME", "AUDIO_MEDIA_VOLUME", "AUDIO_ALARM_VOLUME", "AUDIO_NOTIFICATION_VOLUME", "AUDIO_BLUETOOTH_VOLUME", "WAKE_LOCK", "MONITOR_LOCATION", "MONITOR_HIGH_POWER_LOCATION", "GET_USAGE_STATS", "MUTE_MICROPHONE", "TOAST_WINDOW", "PROJECT_MEDIA", "ACTIVATE_VPN", "WRITE_WALLPAPER", "ASSIST_STRUCTURE", "ASSIST_SCREENSHOT", "READ_PHONE_STATE", "ADD_VOICEMAIL", "USE_SIP", "PROCESS_OUTGOING_CALLS", "USE_FINGERPRINT", "BODY_SENSORS", "READ_CELL_BROADCASTS", "MOCK_LOCATION", "READ_EXTERNAL_STORAGE", "WRITE_EXTERNAL_STORAGE", "TURN_ON_SCREEN", "GET_ACCOUNTS", "RUN_IN_BACKGROUND", "AUDIO_ACCESSIBILITY_VOLUME", "READ_PHONE_NUMBERS", "REQUEST_INSTALL_PACKAGES", "PICTURE_IN_PICTURE", "INSTANT_APP_START_FOREGROUND", "ANSWER_PHONE_CALLS", "RUN_ANY_IN_BACKGROUND", "CHANGE_WIFI_STATE", "REQUEST_DELETE_PACKAGES", "BIND_ACCESSIBILITY_SERVICE", "ACCEPT_HANDOVER", "MANAGE_IPSEC_TUNNELS", "START_FOREGROUND", "BLUETOOTH_SCAN", "USE_BIOMETRIC", "ACTIVITY_RECOGNITION", "SMS_FINANCIAL_TRANSACTIONS", "READ_MEDIA_AUDIO", "WRITE_MEDIA_AUDIO", "READ_MEDIA_VIDEO", "WRITE_MEDIA_VIDEO", "READ_MEDIA_IMAGES", "WRITE_MEDIA_IMAGES", "LEGACY_STORAGE", "ACCESS_ACCESSIBILITY", "READ_DEVICE_IDENTIFIERS"};
        sOpPerms = new String[]{"android.permission.ACCESS_COARSE_LOCATION", "android.permission.ACCESS_FINE_LOCATION", null, "android.permission.VIBRATE", "android.permission.READ_CONTACTS", "android.permission.WRITE_CONTACTS", "android.permission.READ_CALL_LOG", "android.permission.WRITE_CALL_LOG", "android.permission.READ_CALENDAR", "android.permission.WRITE_CALENDAR", "android.permission.ACCESS_WIFI_STATE", null, null, "android.permission.CALL_PHONE", "android.permission.READ_SMS", null, "android.permission.RECEIVE_SMS", "android.permission.RECEIVE_EMERGENCY_BROADCAST", "android.permission.RECEIVE_MMS", "android.permission.RECEIVE_WAP_PUSH", "android.permission.SEND_SMS", "android.permission.READ_SMS", null, "android.permission.WRITE_SETTINGS", "android.permission.SYSTEM_ALERT_WINDOW", "android.permission.ACCESS_NOTIFICATIONS", "android.permission.CAMERA", "android.permission.RECORD_AUDIO", null, null, null, null, null, null, null, null, null, null, null, null, "android.permission.WAKE_LOCK", null, null, "android.permission.PACKAGE_USAGE_STATS", null, null, null, null, null, null, null, "android.permission.READ_PHONE_STATE", "com.android.voicemail.permission.ADD_VOICEMAIL", "android.permission.USE_SIP", "android.permission.PROCESS_OUTGOING_CALLS", "android.permission.USE_FINGERPRINT", "android.permission.BODY_SENSORS", "android.permission.READ_CELL_BROADCASTS", null, "android.permission.READ_EXTERNAL_STORAGE", "android.permission.WRITE_EXTERNAL_STORAGE", null, "android.permission.GET_ACCOUNTS", null, null, "android.permission.READ_PHONE_NUMBERS", "android.permission.REQUEST_INSTALL_PACKAGES", null, "android.permission.INSTANT_APP_FOREGROUND_SERVICE", "android.permission.ANSWER_PHONE_CALLS", null, "android.permission.CHANGE_WIFI_STATE", "android.permission.REQUEST_DELETE_PACKAGES", "android.permission.BIND_ACCESSIBILITY_SERVICE", "android.permission.ACCEPT_HANDOVER", null, "android.permission.FOREGROUND_SERVICE", null, "android.permission.USE_BIOMETRIC", "android.permission.ACTIVITY_RECOGNITION", "android.permission.SMS_FINANCIAL_TRANSACTIONS", null, null, null, null, null, null, null, null, null};
        sOpRestrictions = new String[]{"no_share_location", "no_share_location", "no_share_location", null, null, null, "no_outgoing_calls", "no_outgoing_calls", null, null, "no_share_location", null, null, null, "no_sms", "no_sms", "no_sms", null, "no_sms", null, "no_sms", "no_sms", "no_sms", null, "no_create_windows", null, "no_camera", "no_record_audio", null, null, null, null, null, "no_adjust_volume", "no_adjust_volume", "no_adjust_volume", "no_adjust_volume", "no_adjust_volume", "no_adjust_volume", "no_adjust_volume", null, "no_share_location", "no_share_location", null, "no_unmute_microphone", "no_create_windows", null, null, "no_wallpaper", null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, "no_adjust_volume", null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, "no_sms", null, null, null, null, null, null, null, null, null};
        sOpAllowSystemRestrictionBypass = new boolean[]{true, true, false, false, false, false, false, false, false, false, true, false, false, false, false, false, false, false, false, false, false, false, false, false, true, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true, false, false, false, false, false, false, false, false, false, false, false, false};
        Object object = 0;
        sOpDefaultMode = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 3, AppOpsManager.getSystemAlertWindowDefault(), 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 3, 0, 3, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 3, 0, 2, 0, 2, 0, 2, 3, 0, 2};
        sOpDisableReset = new boolean[]{false, false, false, false, false, false, false, false, false, false, false, false, false, false, true, true, true, false, false, true, true, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false};
        sOpStrToOp = new HashMap();
        sPermToOp = new HashMap();
        if (sOpToSwitch.length == 90) {
            if (sOpToString.length == 90) {
                if (sOpNames.length == 90) {
                    if (sOpPerms.length == 90) {
                        if (sOpDefaultMode.length == 90) {
                            if (sOpDisableReset.length == 90) {
                                if (sOpRestrictions.length == 90) {
                                    if (sOpAllowSystemRestrictionBypass.length == 90) {
                                        Object[] arrobject;
                                        int n;
                                        for (n = 0; n < 90; ++n) {
                                            arrobject = sOpToString;
                                            if (arrobject[n] == null) continue;
                                            sOpStrToOp.put(arrobject[n], n);
                                        }
                                        arrobject = RUNTIME_AND_APPOP_PERMISSIONS_OPS;
                                        int n2 = arrobject.length;
                                        for (n = object; n < n2; ++n) {
                                            String[] arrstring = sOpPerms;
                                            object = arrobject[n];
                                            if (arrstring[object] == null) continue;
                                            sPermToOp.put(arrstring[object], (Integer)object);
                                        }
                                        return;
                                    }
                                    StringBuilder stringBuilder = new StringBuilder();
                                    stringBuilder.append("sOpAllowSYstemRestrictionsBypass length ");
                                    stringBuilder.append(sOpRestrictions.length);
                                    stringBuilder.append(" should be ");
                                    stringBuilder.append(90);
                                    throw new IllegalStateException(stringBuilder.toString());
                                }
                                StringBuilder stringBuilder = new StringBuilder();
                                stringBuilder.append("sOpRestrictions length ");
                                stringBuilder.append(sOpRestrictions.length);
                                stringBuilder.append(" should be ");
                                stringBuilder.append(90);
                                throw new IllegalStateException(stringBuilder.toString());
                            }
                            StringBuilder stringBuilder = new StringBuilder();
                            stringBuilder.append("sOpDisableReset length ");
                            stringBuilder.append(sOpDisableReset.length);
                            stringBuilder.append(" should be ");
                            stringBuilder.append(90);
                            throw new IllegalStateException(stringBuilder.toString());
                        }
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("sOpDefaultMode length ");
                        stringBuilder.append(sOpDefaultMode.length);
                        stringBuilder.append(" should be ");
                        stringBuilder.append(90);
                        throw new IllegalStateException(stringBuilder.toString());
                    }
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("sOpPerms length ");
                    stringBuilder.append(sOpPerms.length);
                    stringBuilder.append(" should be ");
                    stringBuilder.append(90);
                    throw new IllegalStateException(stringBuilder.toString());
                }
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("sOpNames length ");
                stringBuilder.append(sOpNames.length);
                stringBuilder.append(" should be ");
                stringBuilder.append(90);
                throw new IllegalStateException(stringBuilder.toString());
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("sOpToString length ");
            stringBuilder.append(sOpToString.length);
            stringBuilder.append(" should be ");
            stringBuilder.append(90);
            throw new IllegalStateException(stringBuilder.toString());
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("sOpToSwitch length ");
        stringBuilder.append(sOpToSwitch.length);
        stringBuilder.append(" should be ");
        stringBuilder.append(90);
        throw new IllegalStateException(stringBuilder.toString());
    }

    AppOpsManager(Context context, IAppOpsService iAppOpsService) {
        this.mContext = context;
        this.mService = iAppOpsService;
    }

    private String buildSecurityExceptionMsg(int n, int n2, String string2) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(string2);
        stringBuilder.append(" from uid ");
        stringBuilder.append(n2);
        stringBuilder.append(" not allowed to perform ");
        stringBuilder.append(sOpNames[n]);
        return stringBuilder.toString();
    }

    private static LongSparseArray<Object> collectKeys(LongSparseLongArray longSparseLongArray, LongSparseArray<Object> longSparseArray) {
        LongSparseArray<Object> longSparseArray2 = longSparseArray;
        if (longSparseLongArray != null) {
            LongSparseArray<Object> longSparseArray3 = longSparseArray;
            if (longSparseArray == null) {
                longSparseArray3 = new LongSparseArray();
            }
            int n = longSparseLongArray.size();
            int n2 = 0;
            do {
                longSparseArray2 = longSparseArray3;
                if (n2 >= n) break;
                longSparseArray3.put(longSparseLongArray.keyAt(n2), null);
                ++n2;
            } while (true);
        }
        return longSparseArray2;
    }

    public static int extractFlagsFromKey(long l) {
        return (int)(-1L & l);
    }

    public static int extractUidStateFromKey(long l) {
        return (int)(l >> 31);
    }

    private static long findFirstNonNegativeForFlagsInStates(LongSparseLongArray longSparseLongArray, int n, int n2, int n3) {
        if (longSparseLongArray == null) {
            return -1L;
        }
        while (n != 0) {
            int n4 = 1 << Integer.numberOfTrailingZeros(n);
            int n5 = n & n4;
            for (int n6 : UID_STATES) {
                long l;
                if (n6 < n2 || n6 > n3 || (l = longSparseLongArray.get(AppOpsManager.makeKey(n6, n4))) < 0L) continue;
                return l;
            }
            n = n5;
        }
        return -1L;
    }

    private static String findFirstNonNullForFlagsInStates(LongSparseArray<String> longSparseArray, int n, int n2, int n3) {
        if (longSparseArray == null) {
            return null;
        }
        while (n != 0) {
            int n4 = 1 << Integer.numberOfTrailingZeros(n);
            int n5 = n & n4;
            for (int n6 : UID_STATES) {
                String string2;
                if (n6 < n2 || n6 > n3 || (string2 = longSparseArray.get(AppOpsManager.makeKey(n6, n4))) == null) continue;
                return string2;
            }
            n = n5;
        }
        return null;
    }

    public static String flagsToString(int n) {
        StringBuilder stringBuilder = new StringBuilder();
        while (n != 0) {
            int n2 = 1 << Integer.numberOfTrailingZeros(n);
            n &= n2;
            if (stringBuilder.length() > 0) {
                stringBuilder.append('|');
            }
            stringBuilder.append(AppOpsManager.getFlagName(n2));
        }
        return stringBuilder.toString();
    }

    public static final String getFlagName(int n) {
        if (n != 1) {
            if (n != 2) {
                if (n != 4) {
                    if (n != 8) {
                        if (n != 16) {
                            return "unknown";
                        }
                        return "upd";
                    }
                    return "tpd";
                }
                return "up";
            }
            return "tp";
        }
        return "s";
    }

    public static int getNumOps() {
        return 90;
    }

    @SystemApi
    public static String[] getOpStrs() {
        String[] arrstring = sOpToString;
        return Arrays.copyOf(arrstring, arrstring.length);
    }

    private static int getSystemAlertWindowDefault() {
        Object object = ActivityThread.currentApplication();
        if (object == null) {
            return 3;
        }
        object = ((Context)object).getPackageManager();
        if (ActivityManager.isLowRamDeviceStatic() && !((PackageManager)object).hasSystemFeature("android.software.leanback", 0)) {
            return 1;
        }
        return 3;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @UnsupportedAppUsage
    public static IBinder getToken(IAppOpsService object) {
        synchronized (AppOpsManager.class) {
            if (sToken != null) {
                return sToken;
            }
            try {
                Binder binder = new Binder();
                sToken = object.getToken(binder);
                return sToken;
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
    }

    public static String getUidStateName(int n) {
        if (n != 100) {
            if (n != 200) {
                if (n != 300) {
                    if (n != 400) {
                        if (n != 500) {
                            if (n != 600) {
                                if (n != 700) {
                                    return "unknown";
                                }
                                return "cch";
                            }
                            return "bg";
                        }
                        return "fg";
                    }
                    return "fgsvc";
                }
                return "fgsvcl";
            }
            return "top";
        }
        return "pers";
    }

    public static String historicalModeToString(int n) {
        if (n != 0) {
            if (n != 1) {
                if (n != 2) {
                    return "UNKNOWN";
                }
                return "HISTORICAL_MODE_ENABLED_PASSIVE";
            }
            return "HISTORICAL_MODE_ENABLED_ACTIVE";
        }
        return "HISTORICAL_MODE_DISABLED";
    }

    public static String keyToString(long l) {
        int n = AppOpsManager.extractUidStateFromKey(l);
        int n2 = AppOpsManager.extractFlagsFromKey(l);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[");
        stringBuilder.append(AppOpsManager.getUidStateName(n));
        stringBuilder.append("-");
        stringBuilder.append(AppOpsManager.flagsToString(n2));
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    static /* synthetic */ void lambda$getHistoricalOps$0(Consumer consumer, HistoricalOps historicalOps) {
        consumer.accept(historicalOps);
    }

    static /* synthetic */ void lambda$getHistoricalOps$1(Executor executor, Consumer consumer, Bundle parcelable) {
        parcelable = (HistoricalOps)parcelable.getParcelable(KEY_HISTORICAL_OPS);
        long l = Binder.clearCallingIdentity();
        try {
            _$$Lambda$AppOpsManager$frSyqmhVUmNbhMckfMS3PSwTMlw _$$Lambda$AppOpsManager$frSyqmhVUmNbhMckfMS3PSwTMlw = new _$$Lambda$AppOpsManager$frSyqmhVUmNbhMckfMS3PSwTMlw(consumer, (HistoricalOps)parcelable);
            executor.execute(_$$Lambda$AppOpsManager$frSyqmhVUmNbhMckfMS3PSwTMlw);
            return;
        }
        finally {
            Binder.restoreCallingIdentity(l);
        }
    }

    static /* synthetic */ void lambda$getHistoricalOpsFromDiskRaw$2(Consumer consumer, HistoricalOps historicalOps) {
        consumer.accept(historicalOps);
    }

    static /* synthetic */ void lambda$getHistoricalOpsFromDiskRaw$3(Executor executor, Consumer consumer, Bundle parcelable) {
        parcelable = (HistoricalOps)parcelable.getParcelable(KEY_HISTORICAL_OPS);
        long l = Binder.clearCallingIdentity();
        try {
            _$$Lambda$AppOpsManager$VfNXTtJaOeEFrdIj0oDWr_N9nks _$$Lambda$AppOpsManager$VfNXTtJaOeEFrdIj0oDWr_N9nks = new _$$Lambda$AppOpsManager$VfNXTtJaOeEFrdIj0oDWr_N9nks(consumer, (HistoricalOps)parcelable);
            executor.execute(_$$Lambda$AppOpsManager$VfNXTtJaOeEFrdIj0oDWr_N9nks);
            return;
        }
        finally {
            Binder.restoreCallingIdentity(l);
        }
    }

    public static long makeKey(int n, int n2) {
        return (long)n << 31 | (long)n2;
    }

    private static long maxForFlagsInStates(LongSparseLongArray longSparseLongArray, int n, int n2, int n3) {
        if (longSparseLongArray == null) {
            return 0L;
        }
        long l = 0L;
        while (n3 != 0) {
            int n4 = 1 << Integer.numberOfTrailingZeros(n3);
            int n5 = n3 & n4;
            for (int n6 : UID_STATES) {
                long l2 = l;
                if (n6 >= n) {
                    l2 = n6 > n2 ? l : Math.max(l, longSparseLongArray.get(AppOpsManager.makeKey(n6, n4)));
                }
                l = l2;
            }
            n3 = n5;
        }
        return l;
    }

    public static String modeToName(int n) {
        Object object;
        if (n >= 0 && n < ((String[])(object = MODE_NAMES)).length) {
            return object[n];
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("mode=");
        ((StringBuilder)object).append(n);
        return ((StringBuilder)object).toString();
    }

    public static boolean opAllowSystemBypassRestriction(int n) {
        return sOpAllowSystemRestrictionBypass[n];
    }

    public static boolean opAllowsReset(int n) {
        return sOpDisableReset[n] ^ true;
    }

    public static int opToDefaultMode(int n) {
        return sOpDefaultMode[n];
    }

    @SystemApi
    public static int opToDefaultMode(String string2) {
        return AppOpsManager.opToDefaultMode(AppOpsManager.strOpToOp(string2));
    }

    @UnsupportedAppUsage
    public static String opToName(int n) {
        if (n == -1) {
            return "NONE";
        }
        Object object = sOpNames;
        if (n < ((String[])object).length) {
            object = object[n];
        } else {
            object = new StringBuilder();
            ((StringBuilder)object).append("Unknown(");
            ((StringBuilder)object).append(n);
            ((StringBuilder)object).append(")");
            object = ((StringBuilder)object).toString();
        }
        return object;
    }

    public static String opToPermission(int n) {
        return sOpPerms[n];
    }

    @SystemApi
    public static String opToPermission(String string2) {
        return AppOpsManager.opToPermission(AppOpsManager.strOpToOp(string2));
    }

    public static String opToPublicName(int n) {
        return sOpToString[n];
    }

    public static String opToRestriction(int n) {
        return sOpRestrictions[n];
    }

    @UnsupportedAppUsage
    public static int opToSwitch(int n) {
        return sOpToSwitch[n];
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static int parseHistoricalMode(String string2) {
        int n = string2.hashCode();
        if (n != 155185419) {
            if (n != 885538210) return 0;
            if (!string2.equals("HISTORICAL_MODE_ENABLED_PASSIVE")) return 0;
            return 2;
        }
        if (!string2.equals("HISTORICAL_MODE_ENABLED_ACTIVE")) return 0;
        return 1;
    }

    public static String permissionToOp(String object) {
        if ((object = sPermToOp.get(object)) == null) {
            return null;
        }
        return sOpToString[(Integer)object];
    }

    public static int permissionToOpCode(String object) {
        int n = (object = sPermToOp.get(object)) != null ? (Integer)object : -1;
        return n;
    }

    private static LongSparseLongArray readLongSparseLongArrayFromParcel(Parcel parcel) {
        int n = parcel.readInt();
        if (n < 0) {
            return null;
        }
        LongSparseLongArray longSparseLongArray = new LongSparseLongArray(n);
        for (int i = 0; i < n; ++i) {
            longSparseLongArray.append(parcel.readLong(), parcel.readLong());
        }
        return longSparseLongArray;
    }

    private static LongSparseArray<String> readLongSparseStringArrayFromParcel(Parcel parcel) {
        int n = parcel.readInt();
        if (n < 0) {
            return null;
        }
        LongSparseArray<String> longSparseArray = new LongSparseArray<String>(n);
        for (int i = 0; i < n; ++i) {
            longSparseArray.append(parcel.readLong(), parcel.readString());
        }
        return longSparseArray;
    }

    public static int resolveFirstUnrestrictedUidState(int n) {
        if (n != 0 && n != 1 && n != 41 && n != 42) {
            return 400;
        }
        return 300;
    }

    public static int resolveLastRestrictedUidState(int n) {
        if (n != 0 && n != 1) {
            return 500;
        }
        return 400;
    }

    public static int strDebugOpToOp(String string2) {
        Object object;
        for (int i = 0; i < ((String[])(object = sOpNames)).length; ++i) {
            if (!object[i].equals(string2)) continue;
            return i;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Unknown operation string: ");
        ((StringBuilder)object).append(string2);
        throw new IllegalArgumentException(((StringBuilder)object).toString());
    }

    public static int strOpToOp(String string2) {
        Serializable serializable = sOpStrToOp.get(string2);
        if (serializable != null) {
            return (Integer)serializable;
        }
        serializable = new StringBuilder();
        ((StringBuilder)serializable).append("Unknown operation string: ");
        ((StringBuilder)serializable).append(string2);
        throw new IllegalArgumentException(((StringBuilder)serializable).toString());
    }

    private static long sumForFlagsInStates(LongSparseLongArray longSparseLongArray, int n, int n2, int n3) {
        if (longSparseLongArray == null) {
            return 0L;
        }
        long l = 0L;
        while (n3 != 0) {
            int n4 = 1 << Integer.numberOfTrailingZeros(n3);
            int n5 = n3 & n4;
            for (int n6 : UID_STATES) {
                long l2 = l;
                if (n6 >= n) {
                    l2 = n6 > n2 ? l : l + longSparseLongArray.get(AppOpsManager.makeKey(n6, n4));
                }
                l = l2;
            }
            n3 = n5;
        }
        return l;
    }

    public static String uidStateToString(int n) {
        if (n != 100) {
            if (n != 200) {
                if (n != 300) {
                    if (n != 400) {
                        if (n != 500) {
                            if (n != 600) {
                                if (n != 700) {
                                    return "UNKNOWN";
                                }
                                return "UID_STATE_CACHED";
                            }
                            return "UID_STATE_BACKGROUND";
                        }
                        return "UID_STATE_FOREGROUND";
                    }
                    return "UID_STATE_FOREGROUND_SERVICE";
                }
                return "UID_STATE_FOREGROUND_SERVICE_LOCATION";
            }
            return "UID_STATE_TOP";
        }
        return "UID_STATE_PERSISTENT";
    }

    private static void writeLongSparseLongArrayToParcel(LongSparseLongArray longSparseLongArray, Parcel parcel) {
        if (longSparseLongArray != null) {
            int n = longSparseLongArray.size();
            parcel.writeInt(n);
            for (int i = 0; i < n; ++i) {
                parcel.writeLong(longSparseLongArray.keyAt(i));
                parcel.writeLong(longSparseLongArray.valueAt(i));
            }
        } else {
            parcel.writeInt(-1);
        }
    }

    private static void writeLongSparseStringArrayToParcel(LongSparseArray<String> longSparseArray, Parcel parcel) {
        if (longSparseArray != null) {
            int n = longSparseArray.size();
            parcel.writeInt(n);
            for (int i = 0; i < n; ++i) {
                parcel.writeLong(longSparseArray.keyAt(i));
                parcel.writeString(longSparseArray.valueAt(i));
            }
        } else {
            parcel.writeInt(-1);
        }
    }

    public void addHistoricalOps(HistoricalOps historicalOps) {
        try {
            this.mService.addHistoricalOps(historicalOps);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public int checkAudioOp(int n, int n2, int n3, String string2) {
        block3 : {
            try {
                n2 = this.mService.checkAudioOperation(n, n2, n3, string2);
                if (n2 == 2) break block3;
                return n2;
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
        SecurityException securityException = new SecurityException(this.buildSecurityExceptionMsg(n, n3, string2));
        throw securityException;
    }

    public int checkAudioOpNoThrow(int n, int n2, int n3, String string2) {
        try {
            n = this.mService.checkAudioOperation(n, n2, n3, string2);
            return n;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @UnsupportedAppUsage
    public int checkOp(int n, int n2, String string2) {
        block3 : {
            try {
                int n3 = this.mService.checkOperation(n, n2, string2);
                if (n3 == 2) break block3;
                return n3;
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
        SecurityException securityException = new SecurityException(this.buildSecurityExceptionMsg(n, n2, string2));
        throw securityException;
    }

    @Deprecated
    public int checkOp(String string2, int n, String string3) {
        return this.checkOp(AppOpsManager.strOpToOp(string2), n, string3);
    }

    @UnsupportedAppUsage
    public int checkOpNoThrow(int n, int n2, String string2) {
        try {
            n = this.mService.checkOperation(n, n2, string2);
            if (n == 4) {
                n = 0;
            }
            return n;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @Deprecated
    public int checkOpNoThrow(String string2, int n, String string3) {
        return this.checkOpNoThrow(AppOpsManager.strOpToOp(string2), n, string3);
    }

    public void checkPackage(int n, String string2) {
        try {
            if (this.mService.checkPackage(n, string2) == 0) {
                return;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Package ");
            stringBuilder.append(string2);
            stringBuilder.append(" does not belong to ");
            stringBuilder.append(n);
            SecurityException securityException = new SecurityException(stringBuilder.toString());
            throw securityException;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public void clearHistory() {
        try {
            this.mService.clearHistory();
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public void finishOp(int n) {
        this.finishOp(n, Process.myUid(), this.mContext.getOpPackageName());
    }

    public void finishOp(int n, int n2, String string2) {
        try {
            this.mService.finishOperation(AppOpsManager.getToken(this.mService), n, n2, string2);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public void finishOp(String string2, int n, String string3) {
        this.finishOp(AppOpsManager.strOpToOp(string2), n, string3);
    }

    @SystemApi
    public void getHistoricalOps(HistoricalOpsRequest object, Executor executor, Consumer<HistoricalOps> consumer) {
        Preconditions.checkNotNull(executor, "executor cannot be null");
        Preconditions.checkNotNull(consumer, "callback cannot be null");
        try {
            IAppOpsService iAppOpsService = this.mService;
            int n = ((HistoricalOpsRequest)object).mUid;
            String string2 = ((HistoricalOpsRequest)object).mPackageName;
            List list = ((HistoricalOpsRequest)object).mOpNames;
            long l = ((HistoricalOpsRequest)object).mBeginTimeMillis;
            long l2 = ((HistoricalOpsRequest)object).mEndTimeMillis;
            int n2 = ((HistoricalOpsRequest)object).mFlags;
            object = new _$$Lambda$AppOpsManager$4Zbi7CSLEt0nvOmfJBVYtJkauTQ(executor, consumer);
            RemoteCallback remoteCallback = new RemoteCallback((RemoteCallback.OnResultListener)object);
            iAppOpsService.getHistoricalOps(n, string2, list, l, l2, n2, remoteCallback);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public void getHistoricalOpsFromDiskRaw(HistoricalOpsRequest object, Executor executor, Consumer<HistoricalOps> consumer) {
        Preconditions.checkNotNull(executor, "executor cannot be null");
        Preconditions.checkNotNull(consumer, "callback cannot be null");
        try {
            IAppOpsService iAppOpsService = this.mService;
            int n = ((HistoricalOpsRequest)object).mUid;
            String string2 = ((HistoricalOpsRequest)object).mPackageName;
            List list = ((HistoricalOpsRequest)object).mOpNames;
            long l = ((HistoricalOpsRequest)object).mBeginTimeMillis;
            long l2 = ((HistoricalOpsRequest)object).mEndTimeMillis;
            int n2 = ((HistoricalOpsRequest)object).mFlags;
            object = new _$$Lambda$AppOpsManager$5k42P8tID8pwpGFZvo7VQyru20E(executor, consumer);
            RemoteCallback remoteCallback = new RemoteCallback((RemoteCallback.OnResultListener)object);
            iAppOpsService.getHistoricalOpsFromDiskRaw(n, string2, list, l, l2, n2, remoteCallback);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @SystemApi
    @Deprecated
    public List<PackageOps> getOpsForPackage(int n, String object, int[] arrn) {
        try {
            object = this.mService.getOpsForPackage(n, (String)object, arrn);
            return object;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public List<PackageOps> getOpsForPackage(int n, String list, String ... arrstring) {
        block5 : {
            int[] arrn = null;
            if (arrstring != null) {
                int[] arrn2 = new int[arrstring.length];
                int n2 = 0;
                do {
                    arrn = arrn2;
                    if (n2 >= arrstring.length) break;
                    arrn2[n2] = AppOpsManager.strOpToOp(arrstring[n2]);
                    ++n2;
                } while (true);
            }
            try {
                list = this.mService.getOpsForPackage(n, (String)((Object)list), arrn);
                if (list != null) break block5;
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
            list = Collections.emptyList();
            return list;
        }
        return list;
    }

    @UnsupportedAppUsage
    public List<PackageOps> getPackagesForOps(int[] object) {
        try {
            object = this.mService.getPackagesForOps((int[])object);
            return object;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public List<PackageOps> getPackagesForOps(String[] object) {
        int n = ((String[])object).length;
        int[] arrn = new int[n];
        for (int i = 0; i < n; ++i) {
            arrn[i] = sOpStrToOp.get(object[i]);
        }
        object = this.getPackagesForOps(arrn);
        if (object == null) {
            object = Collections.emptyList();
        }
        return object;
    }

    public boolean isOperationActive(int n, int n2, String string2) {
        try {
            boolean bl = this.mService.isOperationActive(n, n2, string2);
            return bl;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @UnsupportedAppUsage
    public int noteOp(int n) {
        return this.noteOp(n, Process.myUid(), this.mContext.getOpPackageName());
    }

    @UnsupportedAppUsage
    public int noteOp(int n, int n2, String string2) {
        int n3 = this.noteOpNoThrow(n, n2, string2);
        if (n3 != 2) {
            return n3;
        }
        throw new SecurityException(this.buildSecurityExceptionMsg(n, n2, string2));
    }

    public int noteOp(String string2, int n, String string3) {
        return this.noteOp(AppOpsManager.strOpToOp(string2), n, string3);
    }

    @UnsupportedAppUsage
    public int noteOpNoThrow(int n, int n2, String string2) {
        try {
            n = this.mService.noteOperation(n, n2, string2);
            return n;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public int noteOpNoThrow(String string2, int n, String string3) {
        return this.noteOpNoThrow(AppOpsManager.strOpToOp(string2), n, string3);
    }

    @UnsupportedAppUsage
    public int noteProxyOp(int n, String string2) {
        int n2 = this.noteProxyOpNoThrow(n, string2);
        if (n2 != 2) {
            return n2;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Proxy package ");
        stringBuilder.append(this.mContext.getOpPackageName());
        stringBuilder.append(" from uid ");
        stringBuilder.append(Process.myUid());
        stringBuilder.append(" or calling package ");
        stringBuilder.append(string2);
        stringBuilder.append(" from uid ");
        stringBuilder.append(Binder.getCallingUid());
        stringBuilder.append(" not allowed to perform ");
        stringBuilder.append(sOpNames[n]);
        throw new SecurityException(stringBuilder.toString());
    }

    public int noteProxyOp(String string2, String string3) {
        return this.noteProxyOp(AppOpsManager.strOpToOp(string2), string3);
    }

    public int noteProxyOpNoThrow(int n, String string2) {
        return this.noteProxyOpNoThrow(n, string2, Binder.getCallingUid());
    }

    public int noteProxyOpNoThrow(int n, String string2, int n2) {
        try {
            n = this.mService.noteProxyOperation(n, Process.myUid(), this.mContext.getOpPackageName(), n2, string2);
            return n;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public int noteProxyOpNoThrow(String string2, String string3) {
        return this.noteProxyOpNoThrow(AppOpsManager.strOpToOp(string2), string3);
    }

    public int noteProxyOpNoThrow(String string2, String string3, int n) {
        return this.noteProxyOpNoThrow(AppOpsManager.strOpToOp(string2), string3, n);
    }

    public void offsetHistory(long l) {
        try {
            this.mService.offsetHistory(l);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public void reloadNonHistoricalState() {
        try {
            this.mService.reloadNonHistoricalState();
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @UnsupportedAppUsage
    public void resetAllModes() {
        try {
            this.mService.resetAllModes(this.mContext.getUserId(), null);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public void resetHistoryParameters() {
        try {
            this.mService.resetHistoryParameters();
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public void setHistoryParameters(int n, long l, int n2) {
        try {
            this.mService.setHistoryParameters(n, l, n2);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public void setMode(int n, int n2, String string2, int n3) {
        try {
            this.mService.setMode(n, n2, string2, n3);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public void setMode(String string2, int n, String string3, int n2) {
        try {
            this.mService.setMode(AppOpsManager.strOpToOp(string2), n, string3, n2);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @UnsupportedAppUsage
    public void setRestriction(int n, int n2, int n3, String[] arrstring) {
        try {
            int n4 = Binder.getCallingUid();
            this.mService.setAudioRestriction(n, n2, n4, n3, arrstring);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public void setUidMode(int n, int n2, int n3) {
        try {
            this.mService.setUidMode(n, n2, n3);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public void setUidMode(String string2, int n, int n2) {
        try {
            this.mService.setUidMode(AppOpsManager.strOpToOp(string2), n, n2);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public void setUserRestriction(int n, boolean bl, IBinder iBinder) {
        this.setUserRestriction(n, bl, iBinder, null);
    }

    public void setUserRestriction(int n, boolean bl, IBinder iBinder, String[] arrstring) {
        this.setUserRestrictionForUser(n, bl, iBinder, arrstring, this.mContext.getUserId());
    }

    public void setUserRestrictionForUser(int n, boolean bl, IBinder iBinder, String[] arrstring, int n2) {
        try {
            this.mService.setUserRestriction(n, bl, iBinder, n2, arrstring);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public int startOp(int n) {
        return this.startOp(n, Process.myUid(), this.mContext.getOpPackageName());
    }

    public int startOp(int n, int n2, String string2) {
        return this.startOp(n, n2, string2, false);
    }

    public int startOp(int n, int n2, String string2, boolean bl) {
        int n3 = this.startOpNoThrow(n, n2, string2, bl);
        if (n3 != 2) {
            return n3;
        }
        throw new SecurityException(this.buildSecurityExceptionMsg(n, n2, string2));
    }

    public int startOp(String string2, int n, String string3) {
        return this.startOp(AppOpsManager.strOpToOp(string2), n, string3);
    }

    public int startOpNoThrow(int n, int n2, String string2) {
        return this.startOpNoThrow(n, n2, string2, false);
    }

    public int startOpNoThrow(int n, int n2, String string2, boolean bl) {
        try {
            n = this.mService.startOperation(AppOpsManager.getToken(this.mService), n, n2, string2, bl);
            return n;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public int startOpNoThrow(String string2, int n, String string3) {
        return this.startOpNoThrow(AppOpsManager.strOpToOp(string2), n, string3);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void startWatchingActive(int[] arrn, final OnOpActiveChangedListener onOpActiveChangedListener) {
        IAppOpsActiveCallback.Stub stub;
        Preconditions.checkNotNull(arrn, "ops cannot be null");
        Preconditions.checkNotNull(onOpActiveChangedListener, "callback cannot be null");
        ArrayMap<OnOpActiveChangedListener, IAppOpsActiveCallback> arrayMap = this.mActiveWatchers;
        synchronized (arrayMap) {
            if (this.mActiveWatchers.get(onOpActiveChangedListener) != null) {
                return;
            }
            stub = new IAppOpsActiveCallback.Stub(){

                @Override
                public void opActiveChanged(int n, int n2, String string2, boolean bl) {
                    onOpActiveChangedListener.onOpActiveChanged(n, n2, string2, bl);
                }
            };
            this.mActiveWatchers.put(onOpActiveChangedListener, stub);
        }
        try {
            this.mService.startWatchingActive(arrn, stub);
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
    public void startWatchingMode(int n, String string2, int n2, final OnOpChangedListener onOpChangedListener) {
        ArrayMap<OnOpChangedListener, IAppOpsCallback> arrayMap = this.mModeWatchers;
        synchronized (arrayMap) {
            IAppOpsCallback.Stub stub;
            IAppOpsCallback.Stub stub2 = stub = this.mModeWatchers.get(onOpChangedListener);
            if (stub == null) {
                stub2 = new IAppOpsCallback.Stub(){

                    @Override
                    public void opChanged(int n, int n2, String string2) {
                        OnOpChangedListener onOpChangedListener2 = onOpChangedListener;
                        if (onOpChangedListener2 instanceof OnOpChangedInternalListener) {
                            ((OnOpChangedInternalListener)onOpChangedListener2).onOpChanged(n, string2);
                        }
                        if (sOpToString[n] != null) {
                            onOpChangedListener.onOpChanged(sOpToString[n], string2);
                        }
                    }
                };
                this.mModeWatchers.put(onOpChangedListener, stub2);
            }
            try {
                this.mService.startWatchingModeWithFlags(n, string2, n2, stub2);
                return;
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
    }

    public void startWatchingMode(int n, String string2, OnOpChangedListener onOpChangedListener) {
        this.startWatchingMode(n, string2, 0, onOpChangedListener);
    }

    public void startWatchingMode(String string2, String string3, int n, OnOpChangedListener onOpChangedListener) {
        this.startWatchingMode(AppOpsManager.strOpToOp(string2), string3, n, onOpChangedListener);
    }

    public void startWatchingMode(String string2, String string3, OnOpChangedListener onOpChangedListener) {
        this.startWatchingMode(AppOpsManager.strOpToOp(string2), string3, onOpChangedListener);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void startWatchingNoted(int[] arrn, final OnOpNotedListener onOpNotedListener) {
        IAppOpsNotedCallback.Stub stub;
        ArrayMap<OnOpNotedListener, IAppOpsNotedCallback> arrayMap = this.mNotedWatchers;
        synchronized (arrayMap) {
            if (this.mNotedWatchers.get(onOpNotedListener) != null) {
                return;
            }
            stub = new IAppOpsNotedCallback.Stub(){

                @Override
                public void opNoted(int n, int n2, String string2, int n3) {
                    onOpNotedListener.onOpNoted(n, n2, string2, n3);
                }
            };
            this.mNotedWatchers.put(onOpNotedListener, stub);
        }
        try {
            this.mService.startWatchingNoted(arrn, stub);
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
    public void stopWatchingActive(OnOpActiveChangedListener object) {
        ArrayMap<OnOpActiveChangedListener, IAppOpsActiveCallback> arrayMap = this.mActiveWatchers;
        synchronized (arrayMap) {
            object = this.mActiveWatchers.remove(object);
            if (object != null) {
                try {
                    this.mService.stopWatchingActive((IAppOpsActiveCallback)object);
                }
                catch (RemoteException remoteException) {
                    throw remoteException.rethrowFromSystemServer();
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
    public void stopWatchingMode(OnOpChangedListener object) {
        ArrayMap<OnOpChangedListener, IAppOpsCallback> arrayMap = this.mModeWatchers;
        synchronized (arrayMap) {
            object = this.mModeWatchers.remove(object);
            if (object != null) {
                try {
                    this.mService.stopWatchingMode((IAppOpsCallback)object);
                }
                catch (RemoteException remoteException) {
                    throw remoteException.rethrowFromSystemServer();
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
    public void stopWatchingNoted(OnOpNotedListener object) {
        ArrayMap<OnOpNotedListener, IAppOpsNotedCallback> arrayMap = this.mNotedWatchers;
        synchronized (arrayMap) {
            object = this.mNotedWatchers.get(object);
            if (object != null) {
                try {
                    this.mService.stopWatchingNoted((IAppOpsNotedCallback)object);
                }
                catch (RemoteException remoteException) {
                    throw remoteException.rethrowFromSystemServer();
                }
            }
            return;
        }
    }

    public int unsafeCheckOp(String string2, int n, String string3) {
        return this.checkOp(AppOpsManager.strOpToOp(string2), n, string3);
    }

    public int unsafeCheckOpNoThrow(String string2, int n, String string3) {
        return this.checkOpNoThrow(AppOpsManager.strOpToOp(string2), n, string3);
    }

    public int unsafeCheckOpRaw(String string2, int n, String string3) {
        try {
            n = this.mService.checkOperationRaw(AppOpsManager.strOpToOp(string2), n, string3);
            return n;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public int unsafeCheckOpRawNoThrow(String string2, int n, String string3) {
        try {
            n = this.mService.checkOperationRaw(AppOpsManager.strOpToOp(string2), n, string3);
            return n;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @Retention(value=RetentionPolicy.SOURCE)
    @Target(value={ElementType.METHOD, ElementType.PARAMETER, ElementType.FIELD})
    public static @interface DataBucketKey {
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface HistoricalMode {
    }

    @SystemApi
    public static final class HistoricalOp
    implements Parcelable {
        public static final Parcelable.Creator<HistoricalOp> CREATOR = new Parcelable.Creator<HistoricalOp>(){

            @Override
            public HistoricalOp createFromParcel(Parcel parcel) {
                return new HistoricalOp(parcel);
            }

            public HistoricalOp[] newArray(int n) {
                return new HistoricalOp[n];
            }
        };
        private LongSparseLongArray mAccessCount;
        private LongSparseLongArray mAccessDuration;
        private final int mOp;
        private LongSparseLongArray mRejectCount;

        public HistoricalOp(int n) {
            this.mOp = n;
        }

        private HistoricalOp(HistoricalOp object) {
            this.mOp = ((HistoricalOp)object).mOp;
            LongSparseLongArray longSparseLongArray = ((HistoricalOp)object).mAccessCount;
            if (longSparseLongArray != null) {
                this.mAccessCount = longSparseLongArray.clone();
            }
            if ((longSparseLongArray = ((HistoricalOp)object).mRejectCount) != null) {
                this.mRejectCount = longSparseLongArray.clone();
            }
            if ((object = ((HistoricalOp)object).mAccessDuration) != null) {
                this.mAccessDuration = ((LongSparseLongArray)object).clone();
            }
        }

        private HistoricalOp(Parcel parcel) {
            this.mOp = parcel.readInt();
            this.mAccessCount = AppOpsManager.readLongSparseLongArrayFromParcel(parcel);
            this.mRejectCount = AppOpsManager.readLongSparseLongArrayFromParcel(parcel);
            this.mAccessDuration = AppOpsManager.readLongSparseLongArrayFromParcel(parcel);
        }

        private void accept(HistoricalOpsVisitor historicalOpsVisitor) {
            historicalOpsVisitor.visitHistoricalOp(this);
        }

        private void filter(double d) {
            HistoricalOp.scale(this.mAccessCount, d);
            HistoricalOp.scale(this.mRejectCount, d);
            HistoricalOp.scale(this.mAccessDuration, d);
        }

        private LongSparseLongArray getOrCreateAccessCount() {
            if (this.mAccessCount == null) {
                this.mAccessCount = new LongSparseLongArray();
            }
            return this.mAccessCount;
        }

        private LongSparseLongArray getOrCreateAccessDuration() {
            if (this.mAccessDuration == null) {
                this.mAccessDuration = new LongSparseLongArray();
            }
            return this.mAccessDuration;
        }

        private LongSparseLongArray getOrCreateRejectCount() {
            if (this.mRejectCount == null) {
                this.mRejectCount = new LongSparseLongArray();
            }
            return this.mRejectCount;
        }

        private boolean hasData(LongSparseLongArray longSparseLongArray) {
            boolean bl = longSparseLongArray != null && longSparseLongArray.size() > 0;
            return bl;
        }

        private void increaseAccessCount(int n, int n2, long l) {
            this.increaseCount(this.getOrCreateAccessCount(), n, n2, l);
        }

        private void increaseAccessDuration(int n, int n2, long l) {
            this.increaseCount(this.getOrCreateAccessDuration(), n, n2, l);
        }

        private void increaseCount(LongSparseLongArray longSparseLongArray, int n, int n2, long l) {
            while (n2 != 0) {
                int n3 = 1 << Integer.numberOfTrailingZeros(n2);
                n2 &= n3;
                long l2 = AppOpsManager.makeKey(n, n3);
                longSparseLongArray.put(l2, longSparseLongArray.get(l2) + l);
            }
        }

        private void increaseRejectCount(int n, int n2, long l) {
            this.increaseCount(this.getOrCreateRejectCount(), n, n2, l);
        }

        private boolean isEmpty() {
            boolean bl = !this.hasData(this.mAccessCount) && !this.hasData(this.mRejectCount) && !this.hasData(this.mAccessDuration);
            return bl;
        }

        public static /* synthetic */ LongSparseLongArray lambda$DkVcBvqB32SMHlxw0sWQPh3GL1A(HistoricalOp historicalOp) {
            return historicalOp.getOrCreateRejectCount();
        }

        public static /* synthetic */ LongSparseLongArray lambda$HUOLFYs8TiaQIOXcrq6JzjxA6gs(HistoricalOp historicalOp) {
            return historicalOp.getOrCreateAccessCount();
        }

        public static /* synthetic */ LongSparseLongArray lambda$Vs6pDL0wjOBTquwNnreWVbPQrn4(HistoricalOp historicalOp) {
            return historicalOp.getOrCreateAccessDuration();
        }

        private void merge(HistoricalOp historicalOp) {
            HistoricalOp.merge(new _$$Lambda$AppOpsManager$HistoricalOp$HUOLFYs8TiaQIOXcrq6JzjxA6gs(this), historicalOp.mAccessCount);
            HistoricalOp.merge(new _$$Lambda$AppOpsManager$HistoricalOp$DkVcBvqB32SMHlxw0sWQPh3GL1A(this), historicalOp.mRejectCount);
            HistoricalOp.merge(new _$$Lambda$AppOpsManager$HistoricalOp$Vs6pDL0wjOBTquwNnreWVbPQrn4(this), historicalOp.mAccessDuration);
        }

        private static void merge(Supplier<LongSparseLongArray> supplier, LongSparseLongArray longSparseLongArray) {
            if (longSparseLongArray != null) {
                int n = longSparseLongArray.size();
                for (int i = 0; i < n; ++i) {
                    LongSparseLongArray longSparseLongArray2 = supplier.get();
                    long l = longSparseLongArray.keyAt(i);
                    long l2 = longSparseLongArray.valueAt(i);
                    longSparseLongArray2.put(l, longSparseLongArray2.get(l) + l2);
                }
            }
        }

        private static void scale(LongSparseLongArray longSparseLongArray, double d) {
            if (longSparseLongArray != null) {
                int n = longSparseLongArray.size();
                for (int i = 0; i < n; ++i) {
                    longSparseLongArray.put(longSparseLongArray.keyAt(i), (long)HistoricalOps.round((double)longSparseLongArray.valueAt(i) * d));
                }
            }
        }

        private HistoricalOp splice(double d) {
            HistoricalOp historicalOp = new HistoricalOp(this.mOp);
            LongSparseLongArray longSparseLongArray = this.mAccessCount;
            Objects.requireNonNull(historicalOp);
            HistoricalOp.splice(longSparseLongArray, new _$$Lambda$AppOpsManager$HistoricalOp$HUOLFYs8TiaQIOXcrq6JzjxA6gs(historicalOp), d);
            longSparseLongArray = this.mRejectCount;
            Objects.requireNonNull(historicalOp);
            HistoricalOp.splice(longSparseLongArray, new _$$Lambda$AppOpsManager$HistoricalOp$DkVcBvqB32SMHlxw0sWQPh3GL1A(historicalOp), d);
            longSparseLongArray = this.mAccessDuration;
            Objects.requireNonNull(historicalOp);
            HistoricalOp.splice(longSparseLongArray, new _$$Lambda$AppOpsManager$HistoricalOp$Vs6pDL0wjOBTquwNnreWVbPQrn4(historicalOp), d);
            return historicalOp;
        }

        private static void splice(LongSparseLongArray longSparseLongArray, Supplier<LongSparseLongArray> supplier, double d) {
            if (longSparseLongArray != null) {
                int n = longSparseLongArray.size();
                for (int i = 0; i < n; ++i) {
                    long l = longSparseLongArray.keyAt(i);
                    long l2 = longSparseLongArray.valueAt(i);
                    long l3 = Math.round((double)l2 * d);
                    if (l3 <= 0L) continue;
                    supplier.get().put(l, l3);
                    longSparseLongArray.put(l, l2 - l3);
                }
            }
        }

        public LongSparseArray<Object> collectKeys() {
            LongSparseArray longSparseArray = AppOpsManager.collectKeys(this.mAccessCount, null);
            longSparseArray = AppOpsManager.collectKeys(this.mRejectCount, longSparseArray);
            return AppOpsManager.collectKeys(this.mAccessDuration, longSparseArray);
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public boolean equals(Object object) {
            if (this == object) {
                return true;
            }
            if (object != null && this.getClass() == object.getClass()) {
                object = (HistoricalOp)object;
                if (this.mOp != ((HistoricalOp)object).mOp) {
                    return false;
                }
                if (!Objects.equals(this.mAccessCount, ((HistoricalOp)object).mAccessCount)) {
                    return false;
                }
                if (!Objects.equals(this.mRejectCount, ((HistoricalOp)object).mRejectCount)) {
                    return false;
                }
                return Objects.equals(this.mAccessDuration, ((HistoricalOp)object).mAccessDuration);
            }
            return false;
        }

        public long getAccessCount(int n, int n2, int n3) {
            return AppOpsManager.sumForFlagsInStates(this.mAccessCount, n, n2, n3);
        }

        public long getAccessDuration(int n, int n2, int n3) {
            return AppOpsManager.sumForFlagsInStates(this.mAccessDuration, n, n2, n3);
        }

        public long getBackgroundAccessCount(int n) {
            return AppOpsManager.sumForFlagsInStates(this.mAccessCount, AppOpsManager.resolveLastRestrictedUidState(this.mOp), 700, n);
        }

        public long getBackgroundAccessDuration(int n) {
            return AppOpsManager.sumForFlagsInStates(this.mAccessDuration, AppOpsManager.resolveLastRestrictedUidState(this.mOp), 700, n);
        }

        public long getBackgroundRejectCount(int n) {
            return AppOpsManager.sumForFlagsInStates(this.mRejectCount, AppOpsManager.resolveLastRestrictedUidState(this.mOp), 700, n);
        }

        public long getForegroundAccessCount(int n) {
            return AppOpsManager.sumForFlagsInStates(this.mAccessCount, 100, AppOpsManager.resolveFirstUnrestrictedUidState(this.mOp), n);
        }

        public long getForegroundAccessDuration(int n) {
            return AppOpsManager.sumForFlagsInStates(this.mAccessDuration, 100, AppOpsManager.resolveFirstUnrestrictedUidState(this.mOp), n);
        }

        public long getForegroundRejectCount(int n) {
            return AppOpsManager.sumForFlagsInStates(this.mRejectCount, 100, AppOpsManager.resolveFirstUnrestrictedUidState(this.mOp), n);
        }

        public int getOpCode() {
            return this.mOp;
        }

        public String getOpName() {
            return sOpToString[this.mOp];
        }

        public long getRejectCount(int n, int n2, int n3) {
            return AppOpsManager.sumForFlagsInStates(this.mRejectCount, n, n2, n3);
        }

        public int hashCode() {
            return ((this.mOp * 31 + Objects.hashCode(this.mAccessCount)) * 31 + Objects.hashCode(this.mRejectCount)) * 31 + Objects.hashCode(this.mAccessDuration);
        }

        @Override
        public void writeToParcel(Parcel parcel, int n) {
            parcel.writeInt(this.mOp);
            AppOpsManager.writeLongSparseLongArrayToParcel(this.mAccessCount, parcel);
            AppOpsManager.writeLongSparseLongArrayToParcel(this.mRejectCount, parcel);
            AppOpsManager.writeLongSparseLongArrayToParcel(this.mAccessDuration, parcel);
        }

    }

    @SystemApi
    public static final class HistoricalOps
    implements Parcelable {
        public static final Parcelable.Creator<HistoricalOps> CREATOR = new Parcelable.Creator<HistoricalOps>(){

            @Override
            public HistoricalOps createFromParcel(Parcel parcel) {
                return new HistoricalOps(parcel);
            }

            public HistoricalOps[] newArray(int n) {
                return new HistoricalOps[n];
            }
        };
        private long mBeginTimeMillis;
        private long mEndTimeMillis;
        private SparseArray<HistoricalUidOps> mHistoricalUidOps;

        public HistoricalOps(long l, long l2) {
            boolean bl = l <= l2;
            Preconditions.checkState(bl);
            this.mBeginTimeMillis = l;
            this.mEndTimeMillis = l2;
        }

        public HistoricalOps(HistoricalOps historicalOps) {
            this.mBeginTimeMillis = historicalOps.mBeginTimeMillis;
            this.mEndTimeMillis = historicalOps.mEndTimeMillis;
            boolean bl = this.mBeginTimeMillis <= this.mEndTimeMillis;
            Preconditions.checkState(bl);
            if (historicalOps.mHistoricalUidOps != null) {
                int n = historicalOps.getUidCount();
                for (int i = 0; i < n; ++i) {
                    HistoricalUidOps historicalUidOps = new HistoricalUidOps(historicalOps.getUidOpsAt(i));
                    if (this.mHistoricalUidOps == null) {
                        this.mHistoricalUidOps = new SparseArray(n);
                    }
                    this.mHistoricalUidOps.put(historicalUidOps.getUid(), historicalUidOps);
                }
            }
        }

        private HistoricalOps(Parcel list) {
            this.mBeginTimeMillis = ((Parcel)((Object)list)).readLong();
            this.mEndTimeMillis = ((Parcel)((Object)list)).readLong();
            int[] arrn = ((Parcel)((Object)list)).createIntArray();
            if (!ArrayUtils.isEmpty(arrn)) {
                if ((list = (list = (ParceledListSlice)((Parcel)((Object)list)).readParcelable(HistoricalOps.class.getClassLoader())) != null ? ((ParceledListSlice)((Object)list)).getList() : null) == null) {
                    return;
                }
                for (int i = 0; i < arrn.length; ++i) {
                    if (this.mHistoricalUidOps == null) {
                        this.mHistoricalUidOps = new SparseArray();
                    }
                    this.mHistoricalUidOps.put(arrn[i], (HistoricalUidOps)list.get(i));
                }
            }
        }

        private HistoricalUidOps getOrCreateHistoricalUidOps(int n) {
            HistoricalUidOps historicalUidOps;
            if (this.mHistoricalUidOps == null) {
                this.mHistoricalUidOps = new SparseArray();
            }
            HistoricalUidOps historicalUidOps2 = historicalUidOps = this.mHistoricalUidOps.get(n);
            if (historicalUidOps == null) {
                historicalUidOps2 = new HistoricalUidOps(n);
                this.mHistoricalUidOps.put(n, historicalUidOps2);
            }
            return historicalUidOps2;
        }

        public static double round(double d) {
            return new BigDecimal(d).setScale(0, RoundingMode.HALF_UP).doubleValue();
        }

        private HistoricalOps splice(double d, boolean bl) {
            long l;
            long l2;
            if (bl) {
                l2 = this.mBeginTimeMillis;
                this.mBeginTimeMillis = l = (long)((double)this.mBeginTimeMillis + (double)this.getDurationMillis() * d);
            } else {
                l2 = (long)((double)this.mEndTimeMillis - (double)this.getDurationMillis() * d);
                l = this.mEndTimeMillis;
                this.mEndTimeMillis = l2;
            }
            HistoricalOps historicalOps = null;
            int n = this.getUidCount();
            for (int i = 0; i < n; ++i) {
                HistoricalUidOps historicalUidOps = this.getUidOpsAt(i).splice(d);
                HistoricalOps historicalOps2 = historicalOps;
                if (historicalUidOps != null) {
                    historicalOps2 = historicalOps;
                    if (historicalOps == null) {
                        historicalOps2 = new HistoricalOps(l2, l);
                    }
                    if (historicalOps2.mHistoricalUidOps == null) {
                        historicalOps2.mHistoricalUidOps = new SparseArray();
                    }
                    historicalOps2.mHistoricalUidOps.put(historicalUidOps.getUid(), historicalUidOps);
                }
                historicalOps = historicalOps2;
            }
            return historicalOps;
        }

        public void accept(HistoricalOpsVisitor historicalOpsVisitor) {
            historicalOpsVisitor.visitHistoricalOps(this);
            int n = this.getUidCount();
            for (int i = 0; i < n; ++i) {
                this.getUidOpsAt(i).accept(historicalOpsVisitor);
            }
        }

        public void clearHistory(int n, String string2) {
            HistoricalUidOps historicalUidOps = this.getOrCreateHistoricalUidOps(n);
            historicalUidOps.clearHistory(string2);
            if (historicalUidOps.isEmpty()) {
                this.mHistoricalUidOps.remove(n);
            }
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public boolean equals(Object object) {
            if (this == object) {
                return true;
            }
            if (object != null && this.getClass() == object.getClass()) {
                object = (HistoricalOps)object;
                if (this.mBeginTimeMillis != ((HistoricalOps)object).mBeginTimeMillis) {
                    return false;
                }
                if (this.mEndTimeMillis != ((HistoricalOps)object).mEndTimeMillis) {
                    return false;
                }
                SparseArray<HistoricalUidOps> sparseArray = this.mHistoricalUidOps;
                return !(sparseArray == null ? ((HistoricalOps)object).mHistoricalUidOps != null : !sparseArray.equals(((HistoricalOps)object).mHistoricalUidOps));
            }
            return false;
        }

        public void filter(int n, String string2, String[] arrstring, long l, long l2) {
            long l3 = this.getDurationMillis();
            this.mBeginTimeMillis = Math.max(this.mBeginTimeMillis, l);
            this.mEndTimeMillis = Math.min(this.mEndTimeMillis, l2);
            double d = Math.min((double)(l2 - l) / (double)l3, 1.0);
            for (int i = this.getUidCount() - 1; i >= 0; --i) {
                HistoricalUidOps historicalUidOps = this.mHistoricalUidOps.valueAt(i);
                if (n != -1 && n != historicalUidOps.getUid()) {
                    this.mHistoricalUidOps.removeAt(i);
                    continue;
                }
                historicalUidOps.filter(string2, arrstring, d);
            }
        }

        public long getBeginTimeMillis() {
            return this.mBeginTimeMillis;
        }

        public long getDurationMillis() {
            return this.mEndTimeMillis - this.mBeginTimeMillis;
        }

        public long getEndTimeMillis() {
            return this.mEndTimeMillis;
        }

        public int getUidCount() {
            SparseArray<HistoricalUidOps> sparseArray = this.mHistoricalUidOps;
            if (sparseArray == null) {
                return 0;
            }
            return sparseArray.size();
        }

        public HistoricalUidOps getUidOps(int n) {
            SparseArray<HistoricalUidOps> sparseArray = this.mHistoricalUidOps;
            if (sparseArray == null) {
                return null;
            }
            return sparseArray.get(n);
        }

        public HistoricalUidOps getUidOpsAt(int n) {
            SparseArray<HistoricalUidOps> sparseArray = this.mHistoricalUidOps;
            if (sparseArray != null) {
                return sparseArray.valueAt(n);
            }
            throw new IndexOutOfBoundsException();
        }

        public int hashCode() {
            long l = this.mBeginTimeMillis;
            return (int)(l ^ l >>> 32) * 31 + this.mHistoricalUidOps.hashCode();
        }

        public void increaseAccessCount(int n, int n2, String string2, int n3, int n4, long l) {
            this.getOrCreateHistoricalUidOps(n2).increaseAccessCount(n, string2, n3, n4, l);
        }

        public void increaseAccessDuration(int n, int n2, String string2, int n3, int n4, long l) {
            this.getOrCreateHistoricalUidOps(n2).increaseAccessDuration(n, string2, n3, n4, l);
        }

        public void increaseRejectCount(int n, int n2, String string2, int n3, int n4, long l) {
            this.getOrCreateHistoricalUidOps(n2).increaseRejectCount(n, string2, n3, n4, l);
        }

        public boolean isEmpty() {
            if (this.getBeginTimeMillis() >= this.getEndTimeMillis()) {
                return true;
            }
            for (int i = this.getUidCount() - 1; i >= 0; --i) {
                if (this.mHistoricalUidOps.valueAt(i).isEmpty()) continue;
                return false;
            }
            return true;
        }

        public void merge(HistoricalOps historicalOps) {
            this.mBeginTimeMillis = Math.min(this.mBeginTimeMillis, historicalOps.mBeginTimeMillis);
            this.mEndTimeMillis = Math.max(this.mEndTimeMillis, historicalOps.mEndTimeMillis);
            int n = historicalOps.getUidCount();
            for (int i = 0; i < n; ++i) {
                HistoricalUidOps historicalUidOps = historicalOps.getUidOpsAt(i);
                HistoricalUidOps historicalUidOps2 = this.getUidOps(historicalUidOps.getUid());
                if (historicalUidOps2 != null) {
                    historicalUidOps2.merge(historicalUidOps);
                    continue;
                }
                if (this.mHistoricalUidOps == null) {
                    this.mHistoricalUidOps = new SparseArray();
                }
                this.mHistoricalUidOps.put(historicalUidOps.getUid(), historicalUidOps);
            }
        }

        public void offsetBeginAndEndTime(long l) {
            this.mBeginTimeMillis += l;
            this.mEndTimeMillis += l;
        }

        public void setBeginAndEndTime(long l, long l2) {
            this.mBeginTimeMillis = l;
            this.mEndTimeMillis = l2;
        }

        public void setBeginTime(long l) {
            this.mBeginTimeMillis = l;
        }

        public void setEndTime(long l) {
            this.mEndTimeMillis = l;
        }

        public HistoricalOps spliceFromBeginning(double d) {
            return this.splice(d, true);
        }

        public HistoricalOps spliceFromEnd(double d) {
            return this.splice(d, false);
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(this.getClass().getSimpleName());
            stringBuilder.append("[from:");
            stringBuilder.append(this.mBeginTimeMillis);
            stringBuilder.append(" to:");
            stringBuilder.append(this.mEndTimeMillis);
            stringBuilder.append("]");
            return stringBuilder.toString();
        }

        @Override
        public void writeToParcel(Parcel parcel, int n) {
            parcel.writeLong(this.mBeginTimeMillis);
            parcel.writeLong(this.mEndTimeMillis);
            Cloneable cloneable = this.mHistoricalUidOps;
            if (cloneable != null) {
                int n2;
                int n3 = ((SparseArray)cloneable).size();
                parcel.writeInt(n3);
                for (n2 = 0; n2 < n3; ++n2) {
                    parcel.writeInt(this.mHistoricalUidOps.keyAt(n2));
                }
                cloneable = new ArrayList(n3);
                for (n2 = 0; n2 < n3; ++n2) {
                    cloneable.add((HistoricalUidOps)this.mHistoricalUidOps.valueAt(n2));
                }
                parcel.writeParcelable(new ParceledListSlice<HistoricalUidOps>((List<HistoricalUidOps>)((Object)cloneable)), n);
            } else {
                parcel.writeInt(-1);
            }
        }

    }

    @SystemApi
    @Immutable
    public static final class HistoricalOpsRequest {
        private final long mBeginTimeMillis;
        private final long mEndTimeMillis;
        private final int mFlags;
        private final List<String> mOpNames;
        private final String mPackageName;
        private final int mUid;

        private HistoricalOpsRequest(int n, String string2, List<String> list, long l, long l2, int n2) {
            this.mUid = n;
            this.mPackageName = string2;
            this.mOpNames = list;
            this.mBeginTimeMillis = l;
            this.mEndTimeMillis = l2;
            this.mFlags = n2;
        }

        @SystemApi
        public static final class Builder {
            private final long mBeginTimeMillis;
            private final long mEndTimeMillis;
            private int mFlags = 31;
            private List<String> mOpNames;
            private String mPackageName;
            private int mUid = -1;

            public Builder(long l, long l2) {
                boolean bl = l >= 0L && l < l2;
                Preconditions.checkArgument(bl, "beginTimeMillis must be non negative and lesser than endTimeMillis");
                this.mBeginTimeMillis = l;
                this.mEndTimeMillis = l2;
            }

            public HistoricalOpsRequest build() {
                return new HistoricalOpsRequest(this.mUid, this.mPackageName, this.mOpNames, this.mBeginTimeMillis, this.mEndTimeMillis, this.mFlags);
            }

            public Builder setFlags(int n) {
                Preconditions.checkFlagsArgument(n, 31);
                this.mFlags = n;
                return this;
            }

            public Builder setOpNames(List<String> list) {
                if (list != null) {
                    int n = list.size();
                    for (int i = 0; i < n; ++i) {
                        boolean bl = AppOpsManager.strOpToOp(list.get(i)) != -1;
                        Preconditions.checkArgument(bl);
                    }
                }
                this.mOpNames = list;
                return this;
            }

            public Builder setPackageName(String string2) {
                this.mPackageName = string2;
                return this;
            }

            public Builder setUid(int n) {
                boolean bl = n == -1 || n >= 0;
                Preconditions.checkArgument(bl, "uid must be -1 or non negative");
                this.mUid = n;
                return this;
            }
        }

    }

    public static interface HistoricalOpsVisitor {
        public void visitHistoricalOp(HistoricalOp var1);

        public void visitHistoricalOps(HistoricalOps var1);

        public void visitHistoricalPackageOps(HistoricalPackageOps var1);

        public void visitHistoricalUidOps(HistoricalUidOps var1);
    }

    @SystemApi
    public static final class HistoricalPackageOps
    implements Parcelable {
        public static final Parcelable.Creator<HistoricalPackageOps> CREATOR = new Parcelable.Creator<HistoricalPackageOps>(){

            @Override
            public HistoricalPackageOps createFromParcel(Parcel parcel) {
                return new HistoricalPackageOps(parcel);
            }

            public HistoricalPackageOps[] newArray(int n) {
                return new HistoricalPackageOps[n];
            }
        };
        private ArrayMap<String, HistoricalOp> mHistoricalOps;
        private final String mPackageName;

        private HistoricalPackageOps(HistoricalPackageOps historicalPackageOps) {
            this.mPackageName = historicalPackageOps.mPackageName;
            int n = historicalPackageOps.getOpCount();
            for (int i = 0; i < n; ++i) {
                HistoricalOp historicalOp = new HistoricalOp(historicalPackageOps.getOpAt(i));
                if (this.mHistoricalOps == null) {
                    this.mHistoricalOps = new ArrayMap(n);
                }
                this.mHistoricalOps.put(historicalOp.getOpName(), historicalOp);
            }
        }

        private HistoricalPackageOps(Parcel parcel) {
            this.mPackageName = parcel.readString();
            this.mHistoricalOps = parcel.createTypedArrayMap(HistoricalOp.CREATOR);
        }

        public HistoricalPackageOps(String string2) {
            this.mPackageName = string2;
        }

        private void accept(HistoricalOpsVisitor historicalOpsVisitor) {
            historicalOpsVisitor.visitHistoricalPackageOps(this);
            int n = this.getOpCount();
            for (int i = 0; i < n; ++i) {
                this.getOpAt(i).accept(historicalOpsVisitor);
            }
        }

        private void filter(String[] arrstring, double d) {
            for (int i = this.getOpCount() - 1; i >= 0; --i) {
                HistoricalOp historicalOp = this.mHistoricalOps.valueAt(i);
                if (arrstring != null && !ArrayUtils.contains(arrstring, historicalOp.getOpName())) {
                    this.mHistoricalOps.removeAt(i);
                    continue;
                }
                historicalOp.filter(d);
            }
        }

        private HistoricalOp getOrCreateHistoricalOp(int n) {
            HistoricalOp historicalOp;
            if (this.mHistoricalOps == null) {
                this.mHistoricalOps = new ArrayMap();
            }
            String string2 = sOpToString[n];
            HistoricalOp historicalOp2 = historicalOp = this.mHistoricalOps.get(string2);
            if (historicalOp == null) {
                historicalOp2 = new HistoricalOp(n);
                this.mHistoricalOps.put(string2, historicalOp2);
            }
            return historicalOp2;
        }

        private void increaseAccessCount(int n, int n2, int n3, long l) {
            this.getOrCreateHistoricalOp(n).increaseAccessCount(n2, n3, l);
        }

        private void increaseAccessDuration(int n, int n2, int n3, long l) {
            this.getOrCreateHistoricalOp(n).increaseAccessDuration(n2, n3, l);
        }

        private void increaseRejectCount(int n, int n2, int n3, long l) {
            this.getOrCreateHistoricalOp(n).increaseRejectCount(n2, n3, l);
        }

        private boolean isEmpty() {
            for (int i = this.getOpCount() - 1; i >= 0; --i) {
                if (this.mHistoricalOps.valueAt(i).isEmpty()) continue;
                return false;
            }
            return true;
        }

        private void merge(HistoricalPackageOps historicalPackageOps) {
            int n = historicalPackageOps.getOpCount();
            for (int i = 0; i < n; ++i) {
                HistoricalOp historicalOp = historicalPackageOps.getOpAt(i);
                HistoricalOp historicalOp2 = this.getOp(historicalOp.getOpName());
                if (historicalOp2 != null) {
                    historicalOp2.merge(historicalOp);
                    continue;
                }
                if (this.mHistoricalOps == null) {
                    this.mHistoricalOps = new ArrayMap();
                }
                this.mHistoricalOps.put(historicalOp.getOpName(), historicalOp);
            }
        }

        private HistoricalPackageOps splice(double d) {
            HistoricalPackageOps historicalPackageOps = null;
            int n = this.getOpCount();
            for (int i = 0; i < n; ++i) {
                HistoricalOp historicalOp = this.getOpAt(i).splice(d);
                HistoricalPackageOps historicalPackageOps2 = historicalPackageOps;
                if (historicalOp != null) {
                    historicalPackageOps2 = historicalPackageOps;
                    if (historicalPackageOps == null) {
                        historicalPackageOps2 = new HistoricalPackageOps(this.mPackageName);
                    }
                    if (historicalPackageOps2.mHistoricalOps == null) {
                        historicalPackageOps2.mHistoricalOps = new ArrayMap();
                    }
                    historicalPackageOps2.mHistoricalOps.put(historicalOp.getOpName(), historicalOp);
                }
                historicalPackageOps = historicalPackageOps2;
            }
            return historicalPackageOps;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public boolean equals(Object arrayMap) {
            if (this == arrayMap) {
                return true;
            }
            if (arrayMap != null && this.getClass() == arrayMap.getClass()) {
                HistoricalPackageOps historicalPackageOps = (HistoricalPackageOps)((Object)arrayMap);
                if (!this.mPackageName.equals(historicalPackageOps.mPackageName)) {
                    return false;
                }
                arrayMap = this.mHistoricalOps;
                return !(arrayMap == null ? historicalPackageOps.mHistoricalOps != null : !arrayMap.equals(historicalPackageOps.mHistoricalOps));
            }
            return false;
        }

        public HistoricalOp getOp(String string2) {
            ArrayMap<String, HistoricalOp> arrayMap = this.mHistoricalOps;
            if (arrayMap == null) {
                return null;
            }
            return arrayMap.get(string2);
        }

        public HistoricalOp getOpAt(int n) {
            ArrayMap<String, HistoricalOp> arrayMap = this.mHistoricalOps;
            if (arrayMap != null) {
                return arrayMap.valueAt(n);
            }
            throw new IndexOutOfBoundsException();
        }

        public int getOpCount() {
            ArrayMap<String, HistoricalOp> arrayMap = this.mHistoricalOps;
            if (arrayMap == null) {
                return 0;
            }
            return arrayMap.size();
        }

        public String getPackageName() {
            return this.mPackageName;
        }

        public int hashCode() {
            ArrayMap<String, HistoricalOp> arrayMap = this.mPackageName;
            int n = 0;
            int n2 = arrayMap != null ? ((String)((Object)arrayMap)).hashCode() : 0;
            arrayMap = this.mHistoricalOps;
            if (arrayMap != null) {
                n = arrayMap.hashCode();
            }
            return n2 * 31 + n;
        }

        @Override
        public void writeToParcel(Parcel parcel, int n) {
            parcel.writeString(this.mPackageName);
            parcel.writeTypedArrayMap(this.mHistoricalOps, n);
        }

    }

    @SystemApi
    public static final class HistoricalUidOps
    implements Parcelable {
        public static final Parcelable.Creator<HistoricalUidOps> CREATOR = new Parcelable.Creator<HistoricalUidOps>(){

            @Override
            public HistoricalUidOps createFromParcel(Parcel parcel) {
                return new HistoricalUidOps(parcel);
            }

            public HistoricalUidOps[] newArray(int n) {
                return new HistoricalUidOps[n];
            }
        };
        private ArrayMap<String, HistoricalPackageOps> mHistoricalPackageOps;
        private final int mUid;

        public HistoricalUidOps(int n) {
            this.mUid = n;
        }

        private HistoricalUidOps(HistoricalUidOps historicalUidOps) {
            this.mUid = historicalUidOps.mUid;
            int n = historicalUidOps.getPackageCount();
            for (int i = 0; i < n; ++i) {
                HistoricalPackageOps historicalPackageOps = new HistoricalPackageOps(historicalUidOps.getPackageOpsAt(i));
                if (this.mHistoricalPackageOps == null) {
                    this.mHistoricalPackageOps = new ArrayMap(n);
                }
                this.mHistoricalPackageOps.put(historicalPackageOps.getPackageName(), historicalPackageOps);
            }
        }

        private HistoricalUidOps(Parcel parcel) {
            this.mUid = parcel.readInt();
            this.mHistoricalPackageOps = parcel.createTypedArrayMap(HistoricalPackageOps.CREATOR);
        }

        private void accept(HistoricalOpsVisitor historicalOpsVisitor) {
            historicalOpsVisitor.visitHistoricalUidOps(this);
            int n = this.getPackageCount();
            for (int i = 0; i < n; ++i) {
                this.getPackageOpsAt(i).accept(historicalOpsVisitor);
            }
        }

        private void clearHistory(String string2) {
            ArrayMap<String, HistoricalPackageOps> arrayMap = this.mHistoricalPackageOps;
            if (arrayMap != null) {
                arrayMap.remove(string2);
            }
        }

        private void filter(String string2, String[] arrstring, double d) {
            for (int i = this.getPackageCount() - 1; i >= 0; --i) {
                HistoricalPackageOps historicalPackageOps = this.getPackageOpsAt(i);
                if (string2 != null && !string2.equals(historicalPackageOps.getPackageName())) {
                    this.mHistoricalPackageOps.removeAt(i);
                    continue;
                }
                historicalPackageOps.filter(arrstring, d);
            }
        }

        private HistoricalPackageOps getOrCreateHistoricalPackageOps(String string2) {
            HistoricalPackageOps historicalPackageOps;
            if (this.mHistoricalPackageOps == null) {
                this.mHistoricalPackageOps = new ArrayMap();
            }
            HistoricalPackageOps historicalPackageOps2 = historicalPackageOps = this.mHistoricalPackageOps.get(string2);
            if (historicalPackageOps == null) {
                historicalPackageOps2 = new HistoricalPackageOps(string2);
                this.mHistoricalPackageOps.put(string2, historicalPackageOps2);
            }
            return historicalPackageOps2;
        }

        private void increaseAccessCount(int n, String string2, int n2, int n3, long l) {
            this.getOrCreateHistoricalPackageOps(string2).increaseAccessCount(n, n2, n3, l);
        }

        private void increaseAccessDuration(int n, String string2, int n2, int n3, long l) {
            this.getOrCreateHistoricalPackageOps(string2).increaseAccessDuration(n, n2, n3, l);
        }

        private void increaseRejectCount(int n, String string2, int n2, int n3, long l) {
            this.getOrCreateHistoricalPackageOps(string2).increaseRejectCount(n, n2, n3, l);
        }

        private boolean isEmpty() {
            for (int i = this.getPackageCount() - 1; i >= 0; --i) {
                if (this.mHistoricalPackageOps.valueAt(i).isEmpty()) continue;
                return false;
            }
            return true;
        }

        private void merge(HistoricalUidOps historicalUidOps) {
            int n = historicalUidOps.getPackageCount();
            for (int i = 0; i < n; ++i) {
                HistoricalPackageOps historicalPackageOps = historicalUidOps.getPackageOpsAt(i);
                HistoricalPackageOps historicalPackageOps2 = this.getPackageOps(historicalPackageOps.getPackageName());
                if (historicalPackageOps2 != null) {
                    historicalPackageOps2.merge(historicalPackageOps);
                    continue;
                }
                if (this.mHistoricalPackageOps == null) {
                    this.mHistoricalPackageOps = new ArrayMap();
                }
                this.mHistoricalPackageOps.put(historicalPackageOps.getPackageName(), historicalPackageOps);
            }
        }

        private HistoricalUidOps splice(double d) {
            HistoricalUidOps historicalUidOps = null;
            int n = this.getPackageCount();
            for (int i = 0; i < n; ++i) {
                HistoricalPackageOps historicalPackageOps = this.getPackageOpsAt(i).splice(d);
                HistoricalUidOps historicalUidOps2 = historicalUidOps;
                if (historicalPackageOps != null) {
                    historicalUidOps2 = historicalUidOps;
                    if (historicalUidOps == null) {
                        historicalUidOps2 = new HistoricalUidOps(this.mUid);
                    }
                    if (historicalUidOps2.mHistoricalPackageOps == null) {
                        historicalUidOps2.mHistoricalPackageOps = new ArrayMap();
                    }
                    historicalUidOps2.mHistoricalPackageOps.put(historicalPackageOps.getPackageName(), historicalPackageOps);
                }
                historicalUidOps = historicalUidOps2;
            }
            return historicalUidOps;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public boolean equals(Object object) {
            if (this == object) {
                return true;
            }
            if (object != null && this.getClass() == object.getClass()) {
                object = (HistoricalUidOps)object;
                if (this.mUid != ((HistoricalUidOps)object).mUid) {
                    return false;
                }
                ArrayMap<String, HistoricalPackageOps> arrayMap = this.mHistoricalPackageOps;
                return !(arrayMap == null ? ((HistoricalUidOps)object).mHistoricalPackageOps != null : !arrayMap.equals(((HistoricalUidOps)object).mHistoricalPackageOps));
            }
            return false;
        }

        public int getPackageCount() {
            ArrayMap<String, HistoricalPackageOps> arrayMap = this.mHistoricalPackageOps;
            if (arrayMap == null) {
                return 0;
            }
            return arrayMap.size();
        }

        public HistoricalPackageOps getPackageOps(String string2) {
            ArrayMap<String, HistoricalPackageOps> arrayMap = this.mHistoricalPackageOps;
            if (arrayMap == null) {
                return null;
            }
            return arrayMap.get(string2);
        }

        public HistoricalPackageOps getPackageOpsAt(int n) {
            ArrayMap<String, HistoricalPackageOps> arrayMap = this.mHistoricalPackageOps;
            if (arrayMap != null) {
                return arrayMap.valueAt(n);
            }
            throw new IndexOutOfBoundsException();
        }

        public int getUid() {
            return this.mUid;
        }

        public int hashCode() {
            int n = this.mUid;
            ArrayMap<String, HistoricalPackageOps> arrayMap = this.mHistoricalPackageOps;
            int n2 = arrayMap != null ? arrayMap.hashCode() : 0;
            return n * 31 + n2;
        }

        @Override
        public void writeToParcel(Parcel parcel, int n) {
            parcel.writeInt(this.mUid);
            parcel.writeTypedArrayMap(this.mHistoricalPackageOps, n);
        }

    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface Mode {
    }

    public static interface OnOpActiveChangedListener {
        public void onOpActiveChanged(int var1, int var2, String var3, boolean var4);
    }

    public static class OnOpChangedInternalListener
    implements OnOpChangedListener {
        public void onOpChanged(int n, String string2) {
        }

        @Override
        public void onOpChanged(String string2, String string3) {
        }
    }

    public static interface OnOpChangedListener {
        public void onOpChanged(String var1, String var2);
    }

    public static interface OnOpNotedListener {
        public void onOpNoted(int var1, int var2, String var3, int var4);
    }

    @SystemApi
    @Immutable
    public static final class OpEntry
    implements Parcelable {
        public static final Parcelable.Creator<OpEntry> CREATOR = new Parcelable.Creator<OpEntry>(){

            @Override
            public OpEntry createFromParcel(Parcel parcel) {
                return new OpEntry(parcel);
            }

            public OpEntry[] newArray(int n) {
                return new OpEntry[n];
            }
        };
        private final LongSparseLongArray mAccessTimes;
        private final LongSparseLongArray mDurations;
        private final int mMode;
        private final int mOp;
        private final LongSparseArray<String> mProxyPackageNames;
        private final LongSparseLongArray mProxyUids;
        private final LongSparseLongArray mRejectTimes;
        private final boolean mRunning;

        public OpEntry(int n, int n2) {
            this.mOp = n;
            this.mMode = n2;
            this.mRunning = false;
            this.mAccessTimes = null;
            this.mRejectTimes = null;
            this.mDurations = null;
            this.mProxyUids = null;
            this.mProxyPackageNames = null;
        }

        public OpEntry(int n, boolean bl, int n2, LongSparseLongArray longSparseLongArray, LongSparseLongArray longSparseLongArray2, LongSparseLongArray longSparseLongArray3, LongSparseLongArray longSparseLongArray4, LongSparseArray<String> longSparseArray) {
            this.mOp = n;
            this.mRunning = bl;
            this.mMode = n2;
            this.mAccessTimes = longSparseLongArray;
            this.mRejectTimes = longSparseLongArray2;
            this.mDurations = longSparseLongArray3;
            this.mProxyUids = longSparseLongArray4;
            this.mProxyPackageNames = longSparseArray;
        }

        OpEntry(Parcel parcel) {
            this.mOp = parcel.readInt();
            this.mMode = parcel.readInt();
            this.mRunning = parcel.readBoolean();
            this.mAccessTimes = AppOpsManager.readLongSparseLongArrayFromParcel(parcel);
            this.mRejectTimes = AppOpsManager.readLongSparseLongArrayFromParcel(parcel);
            this.mDurations = AppOpsManager.readLongSparseLongArrayFromParcel(parcel);
            this.mProxyUids = AppOpsManager.readLongSparseLongArrayFromParcel(parcel);
            this.mProxyPackageNames = AppOpsManager.readLongSparseStringArrayFromParcel(parcel);
        }

        public LongSparseArray<Object> collectKeys() {
            LongSparseArray longSparseArray = AppOpsManager.collectKeys(this.mAccessTimes, null);
            longSparseArray = AppOpsManager.collectKeys(this.mRejectTimes, longSparseArray);
            return AppOpsManager.collectKeys(this.mDurations, longSparseArray);
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public long getDuration() {
            return this.getLastDuration(100, 700, 31);
        }

        public long getLastAccessBackgroundTime(int n) {
            return AppOpsManager.maxForFlagsInStates(this.mAccessTimes, AppOpsManager.resolveLastRestrictedUidState(this.mOp), 700, n);
        }

        public long getLastAccessForegroundTime(int n) {
            return AppOpsManager.maxForFlagsInStates(this.mAccessTimes, 100, AppOpsManager.resolveFirstUnrestrictedUidState(this.mOp), n);
        }

        public long getLastAccessTime(int n) {
            return AppOpsManager.maxForFlagsInStates(this.mAccessTimes, 100, 700, n);
        }

        public long getLastAccessTime(int n, int n2, int n3) {
            return AppOpsManager.maxForFlagsInStates(this.mAccessTimes, n, n2, n3);
        }

        public long getLastBackgroundDuration(int n) {
            return AppOpsManager.sumForFlagsInStates(this.mDurations, AppOpsManager.resolveLastRestrictedUidState(this.mOp), 700, n);
        }

        public long getLastDuration(int n, int n2, int n3) {
            return AppOpsManager.sumForFlagsInStates(this.mDurations, n, n2, n3);
        }

        public long getLastForegroundDuration(int n) {
            return AppOpsManager.sumForFlagsInStates(this.mDurations, 100, AppOpsManager.resolveFirstUnrestrictedUidState(this.mOp), n);
        }

        public long getLastRejectBackgroundTime(int n) {
            return AppOpsManager.maxForFlagsInStates(this.mRejectTimes, AppOpsManager.resolveLastRestrictedUidState(this.mOp), 700, n);
        }

        public long getLastRejectForegroundTime(int n) {
            return AppOpsManager.maxForFlagsInStates(this.mRejectTimes, 100, AppOpsManager.resolveFirstUnrestrictedUidState(this.mOp), n);
        }

        public long getLastRejectTime(int n) {
            return AppOpsManager.maxForFlagsInStates(this.mRejectTimes, 100, 700, n);
        }

        public long getLastRejectTime(int n, int n2, int n3) {
            return AppOpsManager.maxForFlagsInStates(this.mRejectTimes, n, n2, n3);
        }

        public int getMode() {
            return this.mMode;
        }

        @UnsupportedAppUsage
        public int getOp() {
            return this.mOp;
        }

        public String getOpStr() {
            return sOpToString[this.mOp];
        }

        public String getProxyPackageName() {
            return AppOpsManager.findFirstNonNullForFlagsInStates(this.mProxyPackageNames, 100, 700, 31);
        }

        public String getProxyPackageName(int n, int n2) {
            return AppOpsManager.findFirstNonNullForFlagsInStates(this.mProxyPackageNames, n, n, n2);
        }

        public int getProxyUid() {
            return (int)AppOpsManager.findFirstNonNegativeForFlagsInStates(this.mDurations, 100, 700, 31);
        }

        public int getProxyUid(int n, int n2) {
            return (int)AppOpsManager.findFirstNonNegativeForFlagsInStates(this.mDurations, n, n, n2);
        }

        @UnsupportedAppUsage
        public long getRejectTime() {
            return this.getLastRejectTime(31);
        }

        @UnsupportedAppUsage
        public long getTime() {
            return this.getLastAccessTime(31);
        }

        public boolean isRunning() {
            return this.mRunning;
        }

        @Override
        public void writeToParcel(Parcel parcel, int n) {
            parcel.writeInt(this.mOp);
            parcel.writeInt(this.mMode);
            parcel.writeBoolean(this.mRunning);
            AppOpsManager.writeLongSparseLongArrayToParcel(this.mAccessTimes, parcel);
            AppOpsManager.writeLongSparseLongArrayToParcel(this.mRejectTimes, parcel);
            AppOpsManager.writeLongSparseLongArrayToParcel(this.mDurations, parcel);
            AppOpsManager.writeLongSparseLongArrayToParcel(this.mProxyUids, parcel);
            AppOpsManager.writeLongSparseStringArrayToParcel(this.mProxyPackageNames, parcel);
        }

    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface OpFlags {
    }

    @SystemApi
    public static final class PackageOps
    implements Parcelable {
        public static final Parcelable.Creator<PackageOps> CREATOR = new Parcelable.Creator<PackageOps>(){

            @Override
            public PackageOps createFromParcel(Parcel parcel) {
                return new PackageOps(parcel);
            }

            public PackageOps[] newArray(int n) {
                return new PackageOps[n];
            }
        };
        private final List<OpEntry> mEntries;
        private final String mPackageName;
        private final int mUid;

        PackageOps(Parcel parcel) {
            this.mPackageName = parcel.readString();
            this.mUid = parcel.readInt();
            this.mEntries = new ArrayList<OpEntry>();
            int n = parcel.readInt();
            for (int i = 0; i < n; ++i) {
                this.mEntries.add(OpEntry.CREATOR.createFromParcel(parcel));
            }
        }

        @UnsupportedAppUsage
        public PackageOps(String string2, int n, List<OpEntry> list) {
            this.mPackageName = string2;
            this.mUid = n;
            this.mEntries = list;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public List<OpEntry> getOps() {
            return this.mEntries;
        }

        public String getPackageName() {
            return this.mPackageName;
        }

        public int getUid() {
            return this.mUid;
        }

        @Override
        public void writeToParcel(Parcel parcel, int n) {
            parcel.writeString(this.mPackageName);
            parcel.writeInt(this.mUid);
            parcel.writeInt(this.mEntries.size());
            for (int i = 0; i < this.mEntries.size(); ++i) {
                this.mEntries.get(i).writeToParcel(parcel, n);
            }
        }

    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface UidState {
    }

}

