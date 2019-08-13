/*
 * Decompiled with CFR 0.145.
 */
package android.content;

import android.annotation.SystemApi;
import android.annotation.UnsupportedAppUsage;
import android.app.IApplicationThread;
import android.app.IServiceConnection;
import android.content.AutofillOptions;
import android.content.BroadcastReceiver;
import android.content.ComponentCallbacks;
import android.content.ComponentName;
import android.content.ContentCaptureOptions;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentSender;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerExecutor;
import android.os.IBinder;
import android.os.Looper;
import android.os.Process;
import android.os.UserHandle;
import android.util.AttributeSet;
import android.view.Display;
import android.view.DisplayAdjustments;
import android.view.ViewDebug;
import android.view.autofill.AutofillManager;
import android.view.contentcapture.ContentCaptureManager;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.concurrent.Executor;

public abstract class Context {
    public static final String ACCESSIBILITY_SERVICE = "accessibility";
    public static final String ACCOUNT_SERVICE = "account";
    public static final String ACTIVITY_SERVICE = "activity";
    public static final String ACTIVITY_TASK_SERVICE = "activity_task";
    public static final String ADB_SERVICE = "adb";
    public static final String ALARM_SERVICE = "alarm";
    public static final String APPWIDGET_SERVICE = "appwidget";
    public static final String APP_BINDING_SERVICE = "app_binding";
    public static final String APP_OPS_SERVICE = "appops";
    @SystemApi
    public static final String APP_PREDICTION_SERVICE = "app_prediction";
    public static final String ATTENTION_SERVICE = "attention";
    public static final String AUDIO_SERVICE = "audio";
    public static final String AUTOFILL_MANAGER_SERVICE = "autofill";
    @SystemApi
    public static final String BACKUP_SERVICE = "backup";
    public static final String BATTERY_SERVICE = "batterymanager";
    public static final int BIND_ABOVE_CLIENT = 8;
    public static final int BIND_ADJUST_WITH_ACTIVITY = 128;
    public static final int BIND_ALLOW_BACKGROUND_ACTIVITY_STARTS = 1048576;
    public static final int BIND_ALLOW_INSTANT = 4194304;
    public static final int BIND_ALLOW_OOM_MANAGEMENT = 16;
    public static final int BIND_ALLOW_WHITELIST_MANAGEMENT = 16777216;
    public static final int BIND_AUTO_CREATE = 1;
    public static final int BIND_DEBUG_UNBIND = 2;
    public static final int BIND_EXTERNAL_SERVICE = Integer.MIN_VALUE;
    public static final int BIND_FOREGROUND_SERVICE = 67108864;
    public static final int BIND_FOREGROUND_SERVICE_WHILE_AWAKE = 33554432;
    public static final int BIND_IMPORTANT = 64;
    public static final int BIND_IMPORTANT_BACKGROUND = 8388608;
    public static final int BIND_INCLUDE_CAPABILITIES = 4096;
    public static final int BIND_NOT_FOREGROUND = 4;
    public static final int BIND_NOT_PERCEPTIBLE = 256;
    public static final int BIND_NOT_VISIBLE = 1073741824;
    public static final int BIND_REDUCTION_FLAGS = 1073742128;
    public static final int BIND_RESTRICT_ASSOCIATIONS = 2097152;
    public static final int BIND_SCHEDULE_LIKE_TOP_APP = 524288;
    public static final int BIND_SHOWING_UI = 536870912;
    public static final int BIND_TREAT_LIKE_ACTIVITY = 134217728;
    public static final int BIND_VISIBLE = 268435456;
    public static final int BIND_WAIVE_PRIORITY = 32;
    public static final String BIOMETRIC_SERVICE = "biometric";
    public static final String BLUETOOTH_SERVICE = "bluetooth";
    @SystemApi
    public static final String BUGREPORT_SERVICE = "bugreport";
    public static final String CAMERA_SERVICE = "camera";
    public static final String CAPTIONING_SERVICE = "captioning";
    public static final String CARRIER_CONFIG_SERVICE = "carrier_config";
    public static final String CLIPBOARD_SERVICE = "clipboard";
    public static final String COLOR_DISPLAY_SERVICE = "color_display";
    public static final String COMPANION_DEVICE_SERVICE = "companiondevice";
    public static final String CONNECTIVITY_SERVICE = "connectivity";
    public static final String CONSUMER_IR_SERVICE = "consumer_ir";
    public static final String CONTENT_CAPTURE_MANAGER_SERVICE = "content_capture";
    @SystemApi
    public static final String CONTENT_SUGGESTIONS_SERVICE = "content_suggestions";
    @SystemApi
    public static final String CONTEXTHUB_SERVICE = "contexthub";
    public static final int CONTEXT_CREDENTIAL_PROTECTED_STORAGE = 16;
    public static final int CONTEXT_DEVICE_PROTECTED_STORAGE = 8;
    public static final int CONTEXT_IGNORE_SECURITY = 2;
    public static final int CONTEXT_INCLUDE_CODE = 1;
    public static final int CONTEXT_REGISTER_PACKAGE = 1073741824;
    public static final int CONTEXT_RESTRICTED = 4;
    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    public static final String COUNTRY_DETECTOR = "country_detector";
    public static final String CROSS_PROFILE_APPS_SERVICE = "crossprofileapps";
    public static final String DEVICE_IDENTIFIERS_SERVICE = "device_identifiers";
    public static final String DEVICE_IDLE_CONTROLLER = "deviceidle";
    public static final String DEVICE_POLICY_SERVICE = "device_policy";
    public static final String DISPLAY_SERVICE = "display";
    public static final String DOWNLOAD_SERVICE = "download";
    public static final String DROPBOX_SERVICE = "dropbox";
    public static final String DYNAMIC_SYSTEM_SERVICE = "dynamic_system";
    @UnsupportedAppUsage
    public static final String ETHERNET_SERVICE = "ethernet";
    @SystemApi
    public static final String EUICC_CARD_SERVICE = "euicc_card";
    public static final String EUICC_SERVICE = "euicc";
    public static final String FACE_SERVICE = "face";
    public static final String FINGERPRINT_SERVICE = "fingerprint";
    public static final String GATEKEEPER_SERVICE = "android.service.gatekeeper.IGateKeeperService";
    public static final String HARDWARE_PROPERTIES_SERVICE = "hardware_properties";
    @SystemApi
    public static final String HDMI_CONTROL_SERVICE = "hdmi_control";
    public static final String IDMAP_SERVICE = "idmap";
    public static final String INCIDENT_COMPANION_SERVICE = "incidentcompanion";
    public static final String INCIDENT_SERVICE = "incident";
    public static final String INPUT_METHOD_SERVICE = "input_method";
    public static final String INPUT_SERVICE = "input";
    public static final String IPSEC_SERVICE = "ipsec";
    public static final String IRIS_SERVICE = "iris";
    public static final String JOB_SCHEDULER_SERVICE = "jobscheduler";
    public static final String KEYGUARD_SERVICE = "keyguard";
    public static final String LAUNCHER_APPS_SERVICE = "launcherapps";
    public static final String LAYOUT_INFLATER_SERVICE = "layout_inflater";
    public static final String LOCATION_SERVICE = "location";
    public static final String LOWPAN_SERVICE = "lowpan";
    public static final String MEDIA_PROJECTION_SERVICE = "media_projection";
    public static final String MEDIA_ROUTER_SERVICE = "media_router";
    public static final String MEDIA_SESSION_SERVICE = "media_session";
    public static final String MIDI_SERVICE = "midi";
    public static final int MODE_APPEND = 32768;
    public static final int MODE_ENABLE_WRITE_AHEAD_LOGGING = 8;
    @Deprecated
    public static final int MODE_MULTI_PROCESS = 4;
    public static final int MODE_NO_LOCALIZED_COLLATORS = 16;
    public static final int MODE_PRIVATE = 0;
    @Deprecated
    public static final int MODE_WORLD_READABLE = 1;
    @Deprecated
    public static final int MODE_WORLD_WRITEABLE = 2;
    @SystemApi
    public static final String NETD_SERVICE = "netd";
    public static final String NETWORKMANAGEMENT_SERVICE = "network_management";
    public static final String NETWORK_POLICY_SERVICE = "netpolicy";
    @SystemApi
    public static final String NETWORK_SCORE_SERVICE = "network_score";
    public static final String NETWORK_STACK_SERVICE = "network_stack";
    public static final String NETWORK_STATS_SERVICE = "netstats";
    public static final String NETWORK_WATCHLIST_SERVICE = "network_watchlist";
    public static final String NFC_SERVICE = "nfc";
    public static final String NOTIFICATION_SERVICE = "notification";
    public static final String NSD_SERVICE = "servicediscovery";
    @SystemApi
    public static final String OEM_LOCK_SERVICE = "oem_lock";
    public static final String OVERLAY_SERVICE = "overlay";
    public static final String PERMISSION_CONTROLLER_SERVICE = "permission_controller";
    @SystemApi
    public static final String PERMISSION_SERVICE = "permission";
    @SystemApi
    public static final String PERSISTENT_DATA_BLOCK_SERVICE = "persistent_data_block";
    public static final String POWER_SERVICE = "power";
    public static final String PRINT_SERVICE = "print";
    public static final String RADIO_SERVICE = "broadcastradio";
    public static final int RECEIVER_VISIBLE_TO_INSTANT_APPS = 1;
    public static final String RECOVERY_SERVICE = "recovery";
    public static final String RESTRICTIONS_SERVICE = "restrictions";
    public static final String ROLE_CONTROLLER_SERVICE = "role_controller";
    public static final String ROLE_SERVICE = "role";
    @SystemApi
    public static final String ROLLBACK_SERVICE = "rollback";
    public static final String SEARCH_SERVICE = "search";
    @SystemApi
    public static final String SECURE_ELEMENT_SERVICE = "secure_element";
    public static final String SENSOR_PRIVACY_SERVICE = "sensor_privacy";
    public static final String SENSOR_SERVICE = "sensor";
    public static final String SERIAL_SERVICE = "serial";
    public static final String SHORTCUT_SERVICE = "shortcut";
    public static final String SIP_SERVICE = "sip";
    public static final String SLICE_SERVICE = "slice";
    public static final String SOUND_TRIGGER_SERVICE = "soundtrigger";
    public static final String STATS_COMPANION_SERVICE = "statscompanion";
    @SystemApi
    public static final String STATS_MANAGER = "stats";
    @SystemApi
    public static final String STATUS_BAR_SERVICE = "statusbar";
    public static final String STORAGE_SERVICE = "storage";
    public static final String STORAGE_STATS_SERVICE = "storagestats";
    public static final String SYSTEM_HEALTH_SERVICE = "systemhealth";
    @SystemApi
    public static final String SYSTEM_UPDATE_SERVICE = "system_update";
    public static final String TELECOM_SERVICE = "telecom";
    public static final String TELEPHONY_RCS_SERVICE = "ircs";
    public static final String TELEPHONY_SERVICE = "phone";
    public static final String TELEPHONY_SUBSCRIPTION_SERVICE = "telephony_subscription_service";
    public static final String TEST_NETWORK_SERVICE = "test_network";
    public static final String TEXT_CLASSIFICATION_SERVICE = "textclassification";
    public static final String TEXT_SERVICES_MANAGER_SERVICE = "textservices";
    public static final String THERMAL_SERVICE = "thermalservice";
    public static final String TIME_DETECTOR_SERVICE = "time_detector";
    public static final String TIME_ZONE_RULES_MANAGER_SERVICE = "timezone";
    public static final String TRUST_SERVICE = "trust";
    public static final String TV_INPUT_SERVICE = "tv_input";
    public static final String UI_MODE_SERVICE = "uimode";
    public static final String UPDATE_LOCK_SERVICE = "updatelock";
    public static final String URI_GRANTS_SERVICE = "uri_grants";
    public static final String USAGE_STATS_SERVICE = "usagestats";
    public static final String USB_SERVICE = "usb";
    public static final String USER_SERVICE = "user";
    public static final String VIBRATOR_SERVICE = "vibrator";
    public static final String VOICE_INTERACTION_MANAGER_SERVICE = "voiceinteraction";
    @SystemApi
    public static final String VR_SERVICE = "vrmanager";
    public static final String WALLPAPER_SERVICE = "wallpaper";
    public static final String WIFI_AWARE_SERVICE = "wifiaware";
    public static final String WIFI_P2P_SERVICE = "wifip2p";
    public static final String WIFI_RTT_RANGING_SERVICE = "wifirtt";
    @SystemApi
    @Deprecated
    public static final String WIFI_RTT_SERVICE = "rttmanager";
    @SystemApi
    public static final String WIFI_SCANNING_SERVICE = "wifiscanner";
    public static final String WIFI_SERVICE = "wifi";
    public static final String WINDOW_SERVICE = "window";
    private static int sLastAutofillId = -1;

    public void assertRuntimeOverlayThemable() {
        if (this.getResources() != Resources.getSystem()) {
            return;
        }
        throw new IllegalArgumentException("Non-UI context used to display UI; get a UI context from ActivityThread#getSystemUiContext()");
    }

    public boolean bindIsolatedService(Intent intent, int n, String string2, Executor executor, ServiceConnection serviceConnection) {
        throw new RuntimeException("Not implemented. Must override in a subclass.");
    }

    public boolean bindService(Intent intent, int n, Executor executor, ServiceConnection serviceConnection) {
        throw new RuntimeException("Not implemented. Must override in a subclass.");
    }

    public abstract boolean bindService(Intent var1, ServiceConnection var2, int var3);

    @UnsupportedAppUsage
    public boolean bindServiceAsUser(Intent intent, ServiceConnection serviceConnection, int n, Handler handler, UserHandle userHandle) {
        throw new RuntimeException("Not implemented. Must override in a subclass.");
    }

    @SystemApi
    public boolean bindServiceAsUser(Intent intent, ServiceConnection serviceConnection, int n, UserHandle userHandle) {
        throw new RuntimeException("Not implemented. Must override in a subclass.");
    }

    public abstract boolean canLoadUnsafeResources();

    @UnsupportedAppUsage
    public boolean canStartActivityForResult() {
        return false;
    }

    public abstract int checkCallingOrSelfPermission(String var1);

    public abstract int checkCallingOrSelfUriPermission(Uri var1, int var2);

    public abstract int checkCallingPermission(String var1);

    public abstract int checkCallingUriPermission(Uri var1, int var2);

    public abstract int checkPermission(String var1, int var2, int var3);

    @UnsupportedAppUsage
    public abstract int checkPermission(String var1, int var2, int var3, IBinder var4);

    public abstract int checkSelfPermission(String var1);

    public abstract int checkUriPermission(Uri var1, int var2, int var3, int var4);

    public abstract int checkUriPermission(Uri var1, int var2, int var3, int var4, IBinder var5);

    public abstract int checkUriPermission(Uri var1, String var2, String var3, int var4, int var5, int var6);

    @Deprecated
    public abstract void clearWallpaper() throws IOException;

    @UnsupportedAppUsage
    public abstract Context createApplicationContext(ApplicationInfo var1, int var2) throws PackageManager.NameNotFoundException;

    public abstract Context createConfigurationContext(Configuration var1);

    public abstract Context createContextForSplit(String var1) throws PackageManager.NameNotFoundException;

    @SystemApi
    public abstract Context createCredentialProtectedStorageContext();

    public abstract Context createDeviceProtectedStorageContext();

    public abstract Context createDisplayContext(Display var1);

    public abstract Context createPackageContext(String var1, int var2) throws PackageManager.NameNotFoundException;

    @SystemApi
    public Context createPackageContextAsUser(String string2, int n, UserHandle userHandle) throws PackageManager.NameNotFoundException {
        if (!Build.IS_ENG) {
            return this;
        }
        throw new IllegalStateException("createPackageContextAsUser not overridden!");
    }

    public abstract String[] databaseList();

    public abstract boolean deleteDatabase(String var1);

    public abstract boolean deleteFile(String var1);

    public abstract boolean deleteSharedPreferences(String var1);

    public abstract void enforceCallingOrSelfPermission(String var1, String var2);

    public abstract void enforceCallingOrSelfUriPermission(Uri var1, int var2, String var3);

    public abstract void enforceCallingPermission(String var1, String var2);

    public abstract void enforceCallingUriPermission(Uri var1, int var2, String var3);

    public abstract void enforcePermission(String var1, int var2, int var3, String var4);

    public abstract void enforceUriPermission(Uri var1, int var2, int var3, int var4, String var5);

    public abstract void enforceUriPermission(Uri var1, String var2, String var3, int var4, int var5, int var6, String var7);

    public abstract String[] fileList();

    public IBinder getActivityToken() {
        throw new RuntimeException("Not implemented. Must override in a subclass.");
    }

    public abstract Context getApplicationContext();

    public abstract ApplicationInfo getApplicationInfo();

    public abstract AssetManager getAssets();

    public AutofillManager.AutofillClient getAutofillClient() {
        return null;
    }

    public AutofillOptions getAutofillOptions() {
        return null;
    }

    @UnsupportedAppUsage
    public abstract String getBasePackageName();

    public abstract File getCacheDir();

    public abstract ClassLoader getClassLoader();

    public abstract File getCodeCacheDir();

    public final int getColor(int n) {
        return this.getResources().getColor(n, this.getTheme());
    }

    public final ColorStateList getColorStateList(int n) {
        return this.getResources().getColorStateList(n, this.getTheme());
    }

    public ContentCaptureManager.ContentCaptureClient getContentCaptureClient() {
        return null;
    }

    public ContentCaptureOptions getContentCaptureOptions() {
        return null;
    }

    public abstract ContentResolver getContentResolver();

    public abstract File getDataDir();

    public abstract File getDatabasePath(String var1);

    public abstract File getDir(String var1, int var2);

    public abstract Display getDisplay();

    public abstract DisplayAdjustments getDisplayAdjustments(int var1);

    public abstract int getDisplayId();

    public final Drawable getDrawable(int n) {
        return this.getResources().getDrawable(n, this.getTheme());
    }

    public abstract File getExternalCacheDir();

    public abstract File[] getExternalCacheDirs();

    public abstract File getExternalFilesDir(String var1);

    public abstract File[] getExternalFilesDirs(String var1);

    public abstract File[] getExternalMediaDirs();

    public abstract File getFileStreamPath(String var1);

    public abstract File getFilesDir();

    public IApplicationThread getIApplicationThread() {
        throw new RuntimeException("Not implemented. Must override in a subclass.");
    }

    public Executor getMainExecutor() {
        return new HandlerExecutor(new Handler(this.getMainLooper()));
    }

    public abstract Looper getMainLooper();

    public Handler getMainThreadHandler() {
        throw new RuntimeException("Not implemented. Must override in a subclass.");
    }

    public int getNextAutofillId() {
        if (sLastAutofillId == 1073741822) {
            sLastAutofillId = -1;
        }
        return ++sLastAutofillId;
    }

    public abstract File getNoBackupFilesDir();

    public abstract File getObbDir();

    public abstract File[] getObbDirs();

    public String getOpPackageName() {
        throw new RuntimeException("Not implemented. Must override in a subclass.");
    }

    public abstract String getPackageCodePath();

    public abstract PackageManager getPackageManager();

    public abstract String getPackageName();

    public abstract String getPackageResourcePath();

    @SystemApi
    public abstract File getPreloadsFileCache();

    public abstract Resources getResources();

    public IServiceConnection getServiceDispatcher(ServiceConnection serviceConnection, Handler handler, int n) {
        throw new RuntimeException("Not implemented. Must override in a subclass.");
    }

    public abstract SharedPreferences getSharedPreferences(File var1, int var2);

    public abstract SharedPreferences getSharedPreferences(String var1, int var2);

    public abstract File getSharedPreferencesPath(String var1);

    @Deprecated
    @UnsupportedAppUsage
    public File getSharedPrefsFile(String string2) {
        return this.getSharedPreferencesPath(string2);
    }

    public final String getString(int n) {
        return this.getResources().getString(n);
    }

    public final String getString(int n, Object ... arrobject) {
        return this.getResources().getString(n, arrobject);
    }

    public final <T> T getSystemService(Class<T> object) {
        object = (object = this.getSystemServiceName((Class<?>)object)) != null ? this.getSystemService((String)object) : null;
        return (T)object;
    }

    public abstract Object getSystemService(String var1);

    public abstract String getSystemServiceName(Class<?> var1);

    public final CharSequence getText(int n) {
        return this.getResources().getText(n);
    }

    @ViewDebug.ExportedProperty(deepExport=true)
    public abstract Resources.Theme getTheme();

    @UnsupportedAppUsage
    public int getThemeResId() {
        return 0;
    }

    public UserHandle getUser() {
        return Process.myUserHandle();
    }

    public int getUserId() {
        return UserHandle.myUserId();
    }

    @Deprecated
    public abstract Drawable getWallpaper();

    @Deprecated
    public abstract int getWallpaperDesiredMinimumHeight();

    @Deprecated
    public abstract int getWallpaperDesiredMinimumWidth();

    public abstract void grantUriPermission(String var1, Uri var2, int var3);

    public final boolean isAutofillCompatibilityEnabled() {
        AutofillOptions autofillOptions = this.getAutofillOptions();
        boolean bl = autofillOptions != null && autofillOptions.compatModeEnabled;
        return bl;
    }

    @SystemApi
    public abstract boolean isCredentialProtectedStorage();

    public abstract boolean isDeviceProtectedStorage();

    public boolean isRestricted() {
        return false;
    }

    public abstract boolean moveDatabaseFrom(Context var1, String var2);

    public abstract boolean moveSharedPreferencesFrom(Context var1, String var2);

    public final TypedArray obtainStyledAttributes(int n, int[] arrn) throws Resources.NotFoundException {
        return this.getTheme().obtainStyledAttributes(n, arrn);
    }

    public final TypedArray obtainStyledAttributes(AttributeSet attributeSet, int[] arrn) {
        return this.getTheme().obtainStyledAttributes(attributeSet, arrn, 0, 0);
    }

    public final TypedArray obtainStyledAttributes(AttributeSet attributeSet, int[] arrn, int n, int n2) {
        return this.getTheme().obtainStyledAttributes(attributeSet, arrn, n, n2);
    }

    public final TypedArray obtainStyledAttributes(int[] arrn) {
        return this.getTheme().obtainStyledAttributes(arrn);
    }

    public abstract FileInputStream openFileInput(String var1) throws FileNotFoundException;

    public abstract FileOutputStream openFileOutput(String var1, int var2) throws FileNotFoundException;

    public abstract SQLiteDatabase openOrCreateDatabase(String var1, int var2, SQLiteDatabase.CursorFactory var3);

    public abstract SQLiteDatabase openOrCreateDatabase(String var1, int var2, SQLiteDatabase.CursorFactory var3, DatabaseErrorHandler var4);

    @Deprecated
    public abstract Drawable peekWallpaper();

    public void registerComponentCallbacks(ComponentCallbacks componentCallbacks) {
        this.getApplicationContext().registerComponentCallbacks(componentCallbacks);
    }

    public abstract Intent registerReceiver(BroadcastReceiver var1, IntentFilter var2);

    public abstract Intent registerReceiver(BroadcastReceiver var1, IntentFilter var2, int var3);

    public abstract Intent registerReceiver(BroadcastReceiver var1, IntentFilter var2, String var3, Handler var4);

    public abstract Intent registerReceiver(BroadcastReceiver var1, IntentFilter var2, String var3, Handler var4, int var5);

    @UnsupportedAppUsage
    public abstract Intent registerReceiverAsUser(BroadcastReceiver var1, UserHandle var2, IntentFilter var3, String var4, Handler var5);

    public abstract void reloadSharedPreferences();

    @Deprecated
    public abstract void removeStickyBroadcast(Intent var1);

    @Deprecated
    public abstract void removeStickyBroadcastAsUser(Intent var1, UserHandle var2);

    public abstract void revokeUriPermission(Uri var1, int var2);

    public abstract void revokeUriPermission(String var1, Uri var2, int var3);

    public abstract void sendBroadcast(Intent var1);

    public abstract void sendBroadcast(Intent var1, String var2);

    @UnsupportedAppUsage
    public abstract void sendBroadcast(Intent var1, String var2, int var3);

    @SystemApi
    public abstract void sendBroadcast(Intent var1, String var2, Bundle var3);

    public abstract void sendBroadcastAsUser(Intent var1, UserHandle var2);

    public abstract void sendBroadcastAsUser(Intent var1, UserHandle var2, String var3);

    @UnsupportedAppUsage
    public abstract void sendBroadcastAsUser(Intent var1, UserHandle var2, String var3, int var4);

    @SystemApi
    public abstract void sendBroadcastAsUser(Intent var1, UserHandle var2, String var3, Bundle var4);

    public abstract void sendBroadcastAsUserMultiplePermissions(Intent var1, UserHandle var2, String[] var3);

    public abstract void sendBroadcastMultiplePermissions(Intent var1, String[] var2);

    public abstract void sendOrderedBroadcast(Intent var1, String var2);

    @UnsupportedAppUsage
    public abstract void sendOrderedBroadcast(Intent var1, String var2, int var3, BroadcastReceiver var4, Handler var5, int var6, String var7, Bundle var8);

    public abstract void sendOrderedBroadcast(Intent var1, String var2, BroadcastReceiver var3, Handler var4, int var5, String var6, Bundle var7);

    @SystemApi
    public abstract void sendOrderedBroadcast(Intent var1, String var2, Bundle var3, BroadcastReceiver var4, Handler var5, int var6, String var7, Bundle var8);

    @UnsupportedAppUsage
    public abstract void sendOrderedBroadcastAsUser(Intent var1, UserHandle var2, String var3, int var4, BroadcastReceiver var5, Handler var6, int var7, String var8, Bundle var9);

    @UnsupportedAppUsage
    public abstract void sendOrderedBroadcastAsUser(Intent var1, UserHandle var2, String var3, int var4, Bundle var5, BroadcastReceiver var6, Handler var7, int var8, String var9, Bundle var10);

    public abstract void sendOrderedBroadcastAsUser(Intent var1, UserHandle var2, String var3, BroadcastReceiver var4, Handler var5, int var6, String var7, Bundle var8);

    @Deprecated
    public abstract void sendStickyBroadcast(Intent var1);

    @Deprecated
    public abstract void sendStickyBroadcastAsUser(Intent var1, UserHandle var2);

    @Deprecated
    public abstract void sendStickyBroadcastAsUser(Intent var1, UserHandle var2, Bundle var3);

    @Deprecated
    public abstract void sendStickyOrderedBroadcast(Intent var1, BroadcastReceiver var2, Handler var3, int var4, String var5, Bundle var6);

    @Deprecated
    public abstract void sendStickyOrderedBroadcastAsUser(Intent var1, UserHandle var2, BroadcastReceiver var3, Handler var4, int var5, String var6, Bundle var7);

    public void setAutofillClient(AutofillManager.AutofillClient autofillClient) {
    }

    public void setAutofillOptions(AutofillOptions autofillOptions) {
    }

    public void setContentCaptureOptions(ContentCaptureOptions contentCaptureOptions) {
    }

    public abstract void setTheme(int var1);

    @Deprecated
    public abstract void setWallpaper(Bitmap var1) throws IOException;

    @Deprecated
    public abstract void setWallpaper(InputStream var1) throws IOException;

    public abstract void startActivities(Intent[] var1);

    public abstract void startActivities(Intent[] var1, Bundle var2);

    public int startActivitiesAsUser(Intent[] arrintent, Bundle bundle, UserHandle userHandle) {
        throw new RuntimeException("Not implemented. Must override in a subclass.");
    }

    public abstract void startActivity(Intent var1);

    public abstract void startActivity(Intent var1, Bundle var2);

    @UnsupportedAppUsage
    public void startActivityAsUser(Intent intent, Bundle bundle, UserHandle userHandle) {
        throw new RuntimeException("Not implemented. Must override in a subclass.");
    }

    @SystemApi
    public void startActivityAsUser(Intent intent, UserHandle userHandle) {
        throw new RuntimeException("Not implemented. Must override in a subclass.");
    }

    @UnsupportedAppUsage
    public void startActivityForResult(String string2, Intent intent, int n, Bundle bundle) {
        throw new RuntimeException("This method is only implemented for Activity-based Contexts. Check canStartActivityForResult() before calling.");
    }

    public abstract ComponentName startForegroundService(Intent var1);

    public abstract ComponentName startForegroundServiceAsUser(Intent var1, UserHandle var2);

    public abstract boolean startInstrumentation(ComponentName var1, String var2, Bundle var3);

    public abstract void startIntentSender(IntentSender var1, Intent var2, int var3, int var4, int var5) throws IntentSender.SendIntentException;

    public abstract void startIntentSender(IntentSender var1, Intent var2, int var3, int var4, int var5, Bundle var6) throws IntentSender.SendIntentException;

    public abstract ComponentName startService(Intent var1);

    @UnsupportedAppUsage
    public abstract ComponentName startServiceAsUser(Intent var1, UserHandle var2);

    public abstract boolean stopService(Intent var1);

    public abstract boolean stopServiceAsUser(Intent var1, UserHandle var2);

    public abstract void unbindService(ServiceConnection var1);

    public void unregisterComponentCallbacks(ComponentCallbacks componentCallbacks) {
        this.getApplicationContext().unregisterComponentCallbacks(componentCallbacks);
    }

    public abstract void unregisterReceiver(BroadcastReceiver var1);

    public abstract void updateDisplay(int var1);

    public void updateServiceGroup(ServiceConnection serviceConnection, int n, int n2) {
        throw new RuntimeException("Not implemented. Must override in a subclass.");
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface BindServiceFlags {
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface CreatePackageOptions {
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface DatabaseMode {
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface FileMode {
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface PreferencesMode {
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface RegisterReceiverFlags {
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface ServiceName {
    }

}

