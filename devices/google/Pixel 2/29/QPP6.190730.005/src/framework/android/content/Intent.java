/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  org.xmlpull.v1.XmlPullParser
 *  org.xmlpull.v1.XmlPullParserException
 *  org.xmlpull.v1.XmlSerializer
 */
package android.content;

import android.annotation.SystemApi;
import android.annotation.UnsupportedAppUsage;
import android.app.AppGlobals;
import android.content.ClipData;
import android.content.ClipDescription;
import android.content.ComponentName;
import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.Context;
import android.content.IntentSender;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.pm.ServiceInfo;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.net.Uri;
import android.os.BaseBundle;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Process;
import android.os.ShellCommand;
import android.os.StrictMode;
import android.os.UserHandle;
import android.os.storage.StorageManager;
import android.text.TextUtils;
import android.util.ArraySet;
import android.util.AttributeSet;
import android.util.Log;
import android.util.proto.ProtoOutputStream;
import com.android.internal.R;
import com.android.internal.util.XmlUtils;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Set;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

public class Intent
implements Parcelable,
Cloneable {
    public static final String ACTION_ADVANCED_SETTINGS_CHANGED = "android.intent.action.ADVANCED_SETTINGS";
    public static final String ACTION_AIRPLANE_MODE_CHANGED = "android.intent.action.AIRPLANE_MODE";
    @UnsupportedAppUsage
    public static final String ACTION_ALARM_CHANGED = "android.intent.action.ALARM_CHANGED";
    public static final String ACTION_ALL_APPS = "android.intent.action.ALL_APPS";
    public static final String ACTION_ANSWER = "android.intent.action.ANSWER";
    public static final String ACTION_APPLICATION_PREFERENCES = "android.intent.action.APPLICATION_PREFERENCES";
    public static final String ACTION_APPLICATION_RESTRICTIONS_CHANGED = "android.intent.action.APPLICATION_RESTRICTIONS_CHANGED";
    public static final String ACTION_APP_ERROR = "android.intent.action.APP_ERROR";
    public static final String ACTION_ASSIST = "android.intent.action.ASSIST";
    public static final String ACTION_ATTACH_DATA = "android.intent.action.ATTACH_DATA";
    public static final String ACTION_BATTERY_CHANGED = "android.intent.action.BATTERY_CHANGED";
    @SystemApi
    public static final String ACTION_BATTERY_LEVEL_CHANGED = "android.intent.action.BATTERY_LEVEL_CHANGED";
    public static final String ACTION_BATTERY_LOW = "android.intent.action.BATTERY_LOW";
    public static final String ACTION_BATTERY_OKAY = "android.intent.action.BATTERY_OKAY";
    public static final String ACTION_BOOT_COMPLETED = "android.intent.action.BOOT_COMPLETED";
    public static final String ACTION_BUG_REPORT = "android.intent.action.BUG_REPORT";
    public static final String ACTION_CALL = "android.intent.action.CALL";
    public static final String ACTION_CALL_BUTTON = "android.intent.action.CALL_BUTTON";
    @SystemApi
    public static final String ACTION_CALL_EMERGENCY = "android.intent.action.CALL_EMERGENCY";
    @SystemApi
    public static final String ACTION_CALL_PRIVILEGED = "android.intent.action.CALL_PRIVILEGED";
    public static final String ACTION_CAMERA_BUTTON = "android.intent.action.CAMERA_BUTTON";
    public static final String ACTION_CANCEL_ENABLE_ROLLBACK = "android.intent.action.CANCEL_ENABLE_ROLLBACK";
    public static final String ACTION_CARRIER_SETUP = "android.intent.action.CARRIER_SETUP";
    public static final String ACTION_CHOOSER = "android.intent.action.CHOOSER";
    public static final String ACTION_CLEAR_DNS_CACHE = "android.intent.action.CLEAR_DNS_CACHE";
    public static final String ACTION_CLOSE_SYSTEM_DIALOGS = "android.intent.action.CLOSE_SYSTEM_DIALOGS";
    public static final String ACTION_CONFIGURATION_CHANGED = "android.intent.action.CONFIGURATION_CHANGED";
    public static final String ACTION_CREATE_DOCUMENT = "android.intent.action.CREATE_DOCUMENT";
    public static final String ACTION_CREATE_SHORTCUT = "android.intent.action.CREATE_SHORTCUT";
    public static final String ACTION_DATE_CHANGED = "android.intent.action.DATE_CHANGED";
    public static final String ACTION_DEFAULT = "android.intent.action.VIEW";
    public static final String ACTION_DEFINE = "android.intent.action.DEFINE";
    public static final String ACTION_DELETE = "android.intent.action.DELETE";
    @SystemApi
    public static final String ACTION_DEVICE_CUSTOMIZATION_READY = "android.intent.action.DEVICE_CUSTOMIZATION_READY";
    @SystemApi
    @Deprecated
    public static final String ACTION_DEVICE_INITIALIZATION_WIZARD = "android.intent.action.DEVICE_INITIALIZATION_WIZARD";
    public static final String ACTION_DEVICE_LOCKED_CHANGED = "android.intent.action.DEVICE_LOCKED_CHANGED";
    @Deprecated
    public static final String ACTION_DEVICE_STORAGE_FULL = "android.intent.action.DEVICE_STORAGE_FULL";
    @Deprecated
    public static final String ACTION_DEVICE_STORAGE_LOW = "android.intent.action.DEVICE_STORAGE_LOW";
    @Deprecated
    public static final String ACTION_DEVICE_STORAGE_NOT_FULL = "android.intent.action.DEVICE_STORAGE_NOT_FULL";
    @Deprecated
    public static final String ACTION_DEVICE_STORAGE_OK = "android.intent.action.DEVICE_STORAGE_OK";
    public static final String ACTION_DIAL = "android.intent.action.DIAL";
    public static final String ACTION_DISMISS_KEYBOARD_SHORTCUTS = "com.android.intent.action.DISMISS_KEYBOARD_SHORTCUTS";
    public static final String ACTION_DISTRACTING_PACKAGES_CHANGED = "android.intent.action.DISTRACTING_PACKAGES_CHANGED";
    public static final String ACTION_DOCK_ACTIVE = "android.intent.action.DOCK_ACTIVE";
    public static final String ACTION_DOCK_EVENT = "android.intent.action.DOCK_EVENT";
    public static final String ACTION_DOCK_IDLE = "android.intent.action.DOCK_IDLE";
    public static final String ACTION_DREAMING_STARTED = "android.intent.action.DREAMING_STARTED";
    public static final String ACTION_DREAMING_STOPPED = "android.intent.action.DREAMING_STOPPED";
    public static final String ACTION_DYNAMIC_SENSOR_CHANGED = "android.intent.action.DYNAMIC_SENSOR_CHANGED";
    public static final String ACTION_EDIT = "android.intent.action.EDIT";
    public static final String ACTION_EXTERNAL_APPLICATIONS_AVAILABLE = "android.intent.action.EXTERNAL_APPLICATIONS_AVAILABLE";
    public static final String ACTION_EXTERNAL_APPLICATIONS_UNAVAILABLE = "android.intent.action.EXTERNAL_APPLICATIONS_UNAVAILABLE";
    @SystemApi
    public static final String ACTION_FACTORY_RESET = "android.intent.action.FACTORY_RESET";
    public static final String ACTION_FACTORY_TEST = "android.intent.action.FACTORY_TEST";
    public static final String ACTION_GET_CONTENT = "android.intent.action.GET_CONTENT";
    public static final String ACTION_GET_RESTRICTION_ENTRIES = "android.intent.action.GET_RESTRICTION_ENTRIES";
    @SystemApi
    public static final String ACTION_GLOBAL_BUTTON = "android.intent.action.GLOBAL_BUTTON";
    public static final String ACTION_GTALK_SERVICE_CONNECTED = "android.intent.action.GTALK_CONNECTED";
    public static final String ACTION_GTALK_SERVICE_DISCONNECTED = "android.intent.action.GTALK_DISCONNECTED";
    public static final String ACTION_HEADSET_PLUG = "android.intent.action.HEADSET_PLUG";
    public static final String ACTION_IDLE_MAINTENANCE_END = "android.intent.action.ACTION_IDLE_MAINTENANCE_END";
    public static final String ACTION_IDLE_MAINTENANCE_START = "android.intent.action.ACTION_IDLE_MAINTENANCE_START";
    @SystemApi
    public static final String ACTION_INCIDENT_REPORT_READY = "android.intent.action.INCIDENT_REPORT_READY";
    public static final String ACTION_INPUT_METHOD_CHANGED = "android.intent.action.INPUT_METHOD_CHANGED";
    public static final String ACTION_INSERT = "android.intent.action.INSERT";
    public static final String ACTION_INSERT_OR_EDIT = "android.intent.action.INSERT_OR_EDIT";
    public static final String ACTION_INSTALL_FAILURE = "android.intent.action.INSTALL_FAILURE";
    @SystemApi
    public static final String ACTION_INSTALL_INSTANT_APP_PACKAGE = "android.intent.action.INSTALL_INSTANT_APP_PACKAGE";
    @Deprecated
    public static final String ACTION_INSTALL_PACKAGE = "android.intent.action.INSTALL_PACKAGE";
    @SystemApi
    public static final String ACTION_INSTANT_APP_RESOLVER_SETTINGS = "android.intent.action.INSTANT_APP_RESOLVER_SETTINGS";
    @SystemApi
    public static final String ACTION_INTENT_FILTER_NEEDS_VERIFICATION = "android.intent.action.INTENT_FILTER_NEEDS_VERIFICATION";
    public static final String ACTION_LOCALE_CHANGED = "android.intent.action.LOCALE_CHANGED";
    public static final String ACTION_LOCKED_BOOT_COMPLETED = "android.intent.action.LOCKED_BOOT_COMPLETED";
    public static final String ACTION_MAIN = "android.intent.action.MAIN";
    public static final String ACTION_MANAGED_PROFILE_ADDED = "android.intent.action.MANAGED_PROFILE_ADDED";
    public static final String ACTION_MANAGED_PROFILE_AVAILABLE = "android.intent.action.MANAGED_PROFILE_AVAILABLE";
    public static final String ACTION_MANAGED_PROFILE_REMOVED = "android.intent.action.MANAGED_PROFILE_REMOVED";
    public static final String ACTION_MANAGED_PROFILE_UNAVAILABLE = "android.intent.action.MANAGED_PROFILE_UNAVAILABLE";
    public static final String ACTION_MANAGED_PROFILE_UNLOCKED = "android.intent.action.MANAGED_PROFILE_UNLOCKED";
    @SystemApi
    public static final String ACTION_MANAGE_APP_PERMISSION = "android.intent.action.MANAGE_APP_PERMISSION";
    @SystemApi
    public static final String ACTION_MANAGE_APP_PERMISSIONS = "android.intent.action.MANAGE_APP_PERMISSIONS";
    @SystemApi
    public static final String ACTION_MANAGE_DEFAULT_APP = "android.intent.action.MANAGE_DEFAULT_APP";
    public static final String ACTION_MANAGE_NETWORK_USAGE = "android.intent.action.MANAGE_NETWORK_USAGE";
    public static final String ACTION_MANAGE_PACKAGE_STORAGE = "android.intent.action.MANAGE_PACKAGE_STORAGE";
    @SystemApi
    public static final String ACTION_MANAGE_PERMISSIONS = "android.intent.action.MANAGE_PERMISSIONS";
    @SystemApi
    public static final String ACTION_MANAGE_PERMISSION_APPS = "android.intent.action.MANAGE_PERMISSION_APPS";
    @SystemApi
    public static final String ACTION_MANAGE_SPECIAL_APP_ACCESSES = "android.intent.action.MANAGE_SPECIAL_APP_ACCESSES";
    @SystemApi
    @Deprecated
    public static final String ACTION_MASTER_CLEAR = "android.intent.action.MASTER_CLEAR";
    @SystemApi
    public static final String ACTION_MASTER_CLEAR_NOTIFICATION = "android.intent.action.MASTER_CLEAR_NOTIFICATION";
    public static final String ACTION_MEDIA_BAD_REMOVAL = "android.intent.action.MEDIA_BAD_REMOVAL";
    public static final String ACTION_MEDIA_BUTTON = "android.intent.action.MEDIA_BUTTON";
    public static final String ACTION_MEDIA_CHECKING = "android.intent.action.MEDIA_CHECKING";
    public static final String ACTION_MEDIA_EJECT = "android.intent.action.MEDIA_EJECT";
    public static final String ACTION_MEDIA_MOUNTED = "android.intent.action.MEDIA_MOUNTED";
    public static final String ACTION_MEDIA_NOFS = "android.intent.action.MEDIA_NOFS";
    public static final String ACTION_MEDIA_REMOVED = "android.intent.action.MEDIA_REMOVED";
    public static final String ACTION_MEDIA_RESOURCE_GRANTED = "android.intent.action.MEDIA_RESOURCE_GRANTED";
    public static final String ACTION_MEDIA_SCANNER_FINISHED = "android.intent.action.MEDIA_SCANNER_FINISHED";
    @Deprecated
    public static final String ACTION_MEDIA_SCANNER_SCAN_FILE = "android.intent.action.MEDIA_SCANNER_SCAN_FILE";
    public static final String ACTION_MEDIA_SCANNER_STARTED = "android.intent.action.MEDIA_SCANNER_STARTED";
    public static final String ACTION_MEDIA_SHARED = "android.intent.action.MEDIA_SHARED";
    public static final String ACTION_MEDIA_UNMOUNTABLE = "android.intent.action.MEDIA_UNMOUNTABLE";
    public static final String ACTION_MEDIA_UNMOUNTED = "android.intent.action.MEDIA_UNMOUNTED";
    public static final String ACTION_MEDIA_UNSHARED = "android.intent.action.MEDIA_UNSHARED";
    public static final String ACTION_MY_PACKAGE_REPLACED = "android.intent.action.MY_PACKAGE_REPLACED";
    public static final String ACTION_MY_PACKAGE_SUSPENDED = "android.intent.action.MY_PACKAGE_SUSPENDED";
    public static final String ACTION_MY_PACKAGE_UNSUSPENDED = "android.intent.action.MY_PACKAGE_UNSUSPENDED";
    @Deprecated
    public static final String ACTION_NEW_OUTGOING_CALL = "android.intent.action.NEW_OUTGOING_CALL";
    public static final String ACTION_OPEN_DOCUMENT = "android.intent.action.OPEN_DOCUMENT";
    public static final String ACTION_OPEN_DOCUMENT_TREE = "android.intent.action.OPEN_DOCUMENT_TREE";
    public static final String ACTION_OVERLAY_CHANGED = "android.intent.action.OVERLAY_CHANGED";
    public static final String ACTION_PACKAGES_SUSPENDED = "android.intent.action.PACKAGES_SUSPENDED";
    public static final String ACTION_PACKAGES_UNSUSPENDED = "android.intent.action.PACKAGES_UNSUSPENDED";
    public static final String ACTION_PACKAGE_ADDED = "android.intent.action.PACKAGE_ADDED";
    public static final String ACTION_PACKAGE_CHANGED = "android.intent.action.PACKAGE_CHANGED";
    public static final String ACTION_PACKAGE_DATA_CLEARED = "android.intent.action.PACKAGE_DATA_CLEARED";
    public static final String ACTION_PACKAGE_ENABLE_ROLLBACK = "android.intent.action.PACKAGE_ENABLE_ROLLBACK";
    public static final String ACTION_PACKAGE_FIRST_LAUNCH = "android.intent.action.PACKAGE_FIRST_LAUNCH";
    public static final String ACTION_PACKAGE_FULLY_REMOVED = "android.intent.action.PACKAGE_FULLY_REMOVED";
    @Deprecated
    public static final String ACTION_PACKAGE_INSTALL = "android.intent.action.PACKAGE_INSTALL";
    public static final String ACTION_PACKAGE_NEEDS_VERIFICATION = "android.intent.action.PACKAGE_NEEDS_VERIFICATION";
    public static final String ACTION_PACKAGE_REMOVED = "android.intent.action.PACKAGE_REMOVED";
    public static final String ACTION_PACKAGE_REPLACED = "android.intent.action.PACKAGE_REPLACED";
    public static final String ACTION_PACKAGE_RESTARTED = "android.intent.action.PACKAGE_RESTARTED";
    public static final String ACTION_PACKAGE_VERIFIED = "android.intent.action.PACKAGE_VERIFIED";
    public static final String ACTION_PASTE = "android.intent.action.PASTE";
    @SystemApi
    public static final String ACTION_PENDING_INCIDENT_REPORTS_CHANGED = "android.intent.action.PENDING_INCIDENT_REPORTS_CHANGED";
    public static final String ACTION_PICK = "android.intent.action.PICK";
    public static final String ACTION_PICK_ACTIVITY = "android.intent.action.PICK_ACTIVITY";
    public static final String ACTION_POWER_CONNECTED = "android.intent.action.ACTION_POWER_CONNECTED";
    public static final String ACTION_POWER_DISCONNECTED = "android.intent.action.ACTION_POWER_DISCONNECTED";
    public static final String ACTION_POWER_USAGE_SUMMARY = "android.intent.action.POWER_USAGE_SUMMARY";
    public static final String ACTION_PREFERRED_ACTIVITY_CHANGED = "android.intent.action.ACTION_PREFERRED_ACTIVITY_CHANGED";
    @SystemApi
    public static final String ACTION_PRE_BOOT_COMPLETED = "android.intent.action.PRE_BOOT_COMPLETED";
    public static final String ACTION_PROCESS_TEXT = "android.intent.action.PROCESS_TEXT";
    public static final String ACTION_PROVIDER_CHANGED = "android.intent.action.PROVIDER_CHANGED";
    @SystemApi
    public static final String ACTION_QUERY_PACKAGE_RESTART = "android.intent.action.QUERY_PACKAGE_RESTART";
    public static final String ACTION_QUICK_CLOCK = "android.intent.action.QUICK_CLOCK";
    public static final String ACTION_QUICK_VIEW = "android.intent.action.QUICK_VIEW";
    public static final String ACTION_REBOOT = "android.intent.action.REBOOT";
    public static final String ACTION_REMOTE_INTENT = "com.google.android.c2dm.intent.RECEIVE";
    public static final String ACTION_REQUEST_SHUTDOWN = "com.android.internal.intent.action.REQUEST_SHUTDOWN";
    @SystemApi
    public static final String ACTION_RESOLVE_INSTANT_APP_PACKAGE = "android.intent.action.RESOLVE_INSTANT_APP_PACKAGE";
    @SystemApi
    public static final String ACTION_REVIEW_ACCESSIBILITY_SERVICES = "android.intent.action.REVIEW_ACCESSIBILITY_SERVICES";
    @SystemApi
    public static final String ACTION_REVIEW_ONGOING_PERMISSION_USAGE = "android.intent.action.REVIEW_ONGOING_PERMISSION_USAGE";
    @SystemApi
    public static final String ACTION_REVIEW_PERMISSIONS = "android.intent.action.REVIEW_PERMISSIONS";
    @SystemApi
    public static final String ACTION_REVIEW_PERMISSION_USAGE = "android.intent.action.REVIEW_PERMISSION_USAGE";
    @SystemApi
    public static final String ACTION_ROLLBACK_COMMITTED = "android.intent.action.ROLLBACK_COMMITTED";
    public static final String ACTION_RUN = "android.intent.action.RUN";
    public static final String ACTION_SCREEN_OFF = "android.intent.action.SCREEN_OFF";
    public static final String ACTION_SCREEN_ON = "android.intent.action.SCREEN_ON";
    public static final String ACTION_SEARCH = "android.intent.action.SEARCH";
    public static final String ACTION_SEARCH_LONG_PRESS = "android.intent.action.SEARCH_LONG_PRESS";
    public static final String ACTION_SEND = "android.intent.action.SEND";
    public static final String ACTION_SENDTO = "android.intent.action.SENDTO";
    public static final String ACTION_SEND_MULTIPLE = "android.intent.action.SEND_MULTIPLE";
    @SystemApi
    @Deprecated
    public static final String ACTION_SERVICE_STATE = "android.intent.action.SERVICE_STATE";
    public static final String ACTION_SETTING_RESTORED = "android.os.action.SETTING_RESTORED";
    public static final String ACTION_SET_WALLPAPER = "android.intent.action.SET_WALLPAPER";
    public static final String ACTION_SHOW_APP_INFO = "android.intent.action.SHOW_APP_INFO";
    public static final String ACTION_SHOW_BRIGHTNESS_DIALOG = "com.android.intent.action.SHOW_BRIGHTNESS_DIALOG";
    public static final String ACTION_SHOW_KEYBOARD_SHORTCUTS = "com.android.intent.action.SHOW_KEYBOARD_SHORTCUTS";
    @SystemApi
    public static final String ACTION_SHOW_SUSPENDED_APP_DETAILS = "android.intent.action.SHOW_SUSPENDED_APP_DETAILS";
    public static final String ACTION_SHUTDOWN = "android.intent.action.ACTION_SHUTDOWN";
    @SystemApi
    @Deprecated
    public static final String ACTION_SIM_STATE_CHANGED = "android.intent.action.SIM_STATE_CHANGED";
    @SystemApi
    public static final String ACTION_SPLIT_CONFIGURATION_CHANGED = "android.intent.action.SPLIT_CONFIGURATION_CHANGED";
    public static final String ACTION_SYNC = "android.intent.action.SYNC";
    public static final String ACTION_SYSTEM_TUTORIAL = "android.intent.action.SYSTEM_TUTORIAL";
    public static final String ACTION_THERMAL_EVENT = "android.intent.action.THERMAL_EVENT";
    public static final String ACTION_TIMEZONE_CHANGED = "android.intent.action.TIMEZONE_CHANGED";
    public static final String ACTION_TIME_CHANGED = "android.intent.action.TIME_SET";
    public static final String ACTION_TIME_TICK = "android.intent.action.TIME_TICK";
    public static final String ACTION_TRANSLATE = "android.intent.action.TRANSLATE";
    public static final String ACTION_UID_REMOVED = "android.intent.action.UID_REMOVED";
    @Deprecated
    public static final String ACTION_UMS_CONNECTED = "android.intent.action.UMS_CONNECTED";
    @Deprecated
    public static final String ACTION_UMS_DISCONNECTED = "android.intent.action.UMS_DISCONNECTED";
    @Deprecated
    public static final String ACTION_UNINSTALL_PACKAGE = "android.intent.action.UNINSTALL_PACKAGE";
    @SystemApi
    public static final String ACTION_UPGRADE_SETUP = "android.intent.action.UPGRADE_SETUP";
    @SystemApi
    public static final String ACTION_USER_ADDED = "android.intent.action.USER_ADDED";
    public static final String ACTION_USER_BACKGROUND = "android.intent.action.USER_BACKGROUND";
    public static final String ACTION_USER_FOREGROUND = "android.intent.action.USER_FOREGROUND";
    public static final String ACTION_USER_INFO_CHANGED = "android.intent.action.USER_INFO_CHANGED";
    public static final String ACTION_USER_INITIALIZE = "android.intent.action.USER_INITIALIZE";
    public static final String ACTION_USER_PRESENT = "android.intent.action.USER_PRESENT";
    @SystemApi
    public static final String ACTION_USER_REMOVED = "android.intent.action.USER_REMOVED";
    public static final String ACTION_USER_STARTED = "android.intent.action.USER_STARTED";
    public static final String ACTION_USER_STARTING = "android.intent.action.USER_STARTING";
    public static final String ACTION_USER_STOPPED = "android.intent.action.USER_STOPPED";
    public static final String ACTION_USER_STOPPING = "android.intent.action.USER_STOPPING";
    @UnsupportedAppUsage
    public static final String ACTION_USER_SWITCHED = "android.intent.action.USER_SWITCHED";
    public static final String ACTION_USER_UNLOCKED = "android.intent.action.USER_UNLOCKED";
    public static final String ACTION_VIEW = "android.intent.action.VIEW";
    public static final String ACTION_VIEW_LOCUS = "android.intent.action.VIEW_LOCUS";
    public static final String ACTION_VIEW_PERMISSION_USAGE = "android.intent.action.VIEW_PERMISSION_USAGE";
    @SystemApi
    public static final String ACTION_VOICE_ASSIST = "android.intent.action.VOICE_ASSIST";
    public static final String ACTION_VOICE_COMMAND = "android.intent.action.VOICE_COMMAND";
    @Deprecated
    public static final String ACTION_WALLPAPER_CHANGED = "android.intent.action.WALLPAPER_CHANGED";
    public static final String ACTION_WEB_SEARCH = "android.intent.action.WEB_SEARCH";
    private static final String ATTR_ACTION = "action";
    private static final String ATTR_CATEGORY = "category";
    private static final String ATTR_COMPONENT = "component";
    private static final String ATTR_DATA = "data";
    private static final String ATTR_FLAGS = "flags";
    private static final String ATTR_IDENTIFIER = "ident";
    private static final String ATTR_TYPE = "type";
    public static final String CATEGORY_ALTERNATIVE = "android.intent.category.ALTERNATIVE";
    public static final String CATEGORY_APP_BROWSER = "android.intent.category.APP_BROWSER";
    public static final String CATEGORY_APP_CALCULATOR = "android.intent.category.APP_CALCULATOR";
    public static final String CATEGORY_APP_CALENDAR = "android.intent.category.APP_CALENDAR";
    public static final String CATEGORY_APP_CONTACTS = "android.intent.category.APP_CONTACTS";
    public static final String CATEGORY_APP_EMAIL = "android.intent.category.APP_EMAIL";
    public static final String CATEGORY_APP_FILES = "android.intent.category.APP_FILES";
    public static final String CATEGORY_APP_GALLERY = "android.intent.category.APP_GALLERY";
    public static final String CATEGORY_APP_MAPS = "android.intent.category.APP_MAPS";
    public static final String CATEGORY_APP_MARKET = "android.intent.category.APP_MARKET";
    public static final String CATEGORY_APP_MESSAGING = "android.intent.category.APP_MESSAGING";
    public static final String CATEGORY_APP_MUSIC = "android.intent.category.APP_MUSIC";
    public static final String CATEGORY_BROWSABLE = "android.intent.category.BROWSABLE";
    public static final String CATEGORY_CAR_DOCK = "android.intent.category.CAR_DOCK";
    public static final String CATEGORY_CAR_LAUNCHER = "android.intent.category.CAR_LAUNCHER";
    public static final String CATEGORY_CAR_MODE = "android.intent.category.CAR_MODE";
    public static final String CATEGORY_DEFAULT = "android.intent.category.DEFAULT";
    public static final String CATEGORY_DESK_DOCK = "android.intent.category.DESK_DOCK";
    public static final String CATEGORY_DEVELOPMENT_PREFERENCE = "android.intent.category.DEVELOPMENT_PREFERENCE";
    public static final String CATEGORY_EMBED = "android.intent.category.EMBED";
    public static final String CATEGORY_FRAMEWORK_INSTRUMENTATION_TEST = "android.intent.category.FRAMEWORK_INSTRUMENTATION_TEST";
    public static final String CATEGORY_HE_DESK_DOCK = "android.intent.category.HE_DESK_DOCK";
    public static final String CATEGORY_HOME = "android.intent.category.HOME";
    public static final String CATEGORY_HOME_MAIN = "android.intent.category.HOME_MAIN";
    public static final String CATEGORY_INFO = "android.intent.category.INFO";
    public static final String CATEGORY_LAUNCHER = "android.intent.category.LAUNCHER";
    public static final String CATEGORY_LAUNCHER_APP = "android.intent.category.LAUNCHER_APP";
    public static final String CATEGORY_LEANBACK_LAUNCHER = "android.intent.category.LEANBACK_LAUNCHER";
    @SystemApi
    public static final String CATEGORY_LEANBACK_SETTINGS = "android.intent.category.LEANBACK_SETTINGS";
    public static final String CATEGORY_LE_DESK_DOCK = "android.intent.category.LE_DESK_DOCK";
    public static final String CATEGORY_MONKEY = "android.intent.category.MONKEY";
    public static final String CATEGORY_OPENABLE = "android.intent.category.OPENABLE";
    public static final String CATEGORY_PREFERENCE = "android.intent.category.PREFERENCE";
    public static final String CATEGORY_SAMPLE_CODE = "android.intent.category.SAMPLE_CODE";
    public static final String CATEGORY_SECONDARY_HOME = "android.intent.category.SECONDARY_HOME";
    public static final String CATEGORY_SELECTED_ALTERNATIVE = "android.intent.category.SELECTED_ALTERNATIVE";
    public static final String CATEGORY_SETUP_WIZARD = "android.intent.category.SETUP_WIZARD";
    public static final String CATEGORY_TAB = "android.intent.category.TAB";
    public static final String CATEGORY_TEST = "android.intent.category.TEST";
    public static final String CATEGORY_TYPED_OPENABLE = "android.intent.category.TYPED_OPENABLE";
    public static final String CATEGORY_UNIT_TEST = "android.intent.category.UNIT_TEST";
    public static final String CATEGORY_VOICE = "android.intent.category.VOICE";
    public static final String CATEGORY_VR_HOME = "android.intent.category.VR_HOME";
    private static final int COPY_MODE_ALL = 0;
    private static final int COPY_MODE_FILTER = 1;
    private static final int COPY_MODE_HISTORY = 2;
    public static final Parcelable.Creator<Intent> CREATOR = new Parcelable.Creator<Intent>(){

        @Override
        public Intent createFromParcel(Parcel parcel) {
            return new Intent(parcel);
        }

        public Intent[] newArray(int n) {
            return new Intent[n];
        }
    };
    public static final String EXTRA_ALARM_COUNT = "android.intent.extra.ALARM_COUNT";
    public static final String EXTRA_ALLOW_MULTIPLE = "android.intent.extra.ALLOW_MULTIPLE";
    @Deprecated
    public static final String EXTRA_ALLOW_REPLACE = "android.intent.extra.ALLOW_REPLACE";
    public static final String EXTRA_ALTERNATE_INTENTS = "android.intent.extra.ALTERNATE_INTENTS";
    public static final String EXTRA_ASSIST_CONTEXT = "android.intent.extra.ASSIST_CONTEXT";
    public static final String EXTRA_ASSIST_INPUT_DEVICE_ID = "android.intent.extra.ASSIST_INPUT_DEVICE_ID";
    public static final String EXTRA_ASSIST_INPUT_HINT_KEYBOARD = "android.intent.extra.ASSIST_INPUT_HINT_KEYBOARD";
    public static final String EXTRA_ASSIST_PACKAGE = "android.intent.extra.ASSIST_PACKAGE";
    public static final String EXTRA_ASSIST_UID = "android.intent.extra.ASSIST_UID";
    public static final String EXTRA_AUTO_LAUNCH_SINGLE_CHOICE = "android.intent.extra.AUTO_LAUNCH_SINGLE_CHOICE";
    public static final String EXTRA_BCC = "android.intent.extra.BCC";
    public static final String EXTRA_BUG_REPORT = "android.intent.extra.BUG_REPORT";
    @SystemApi
    public static final String EXTRA_CALLING_PACKAGE = "android.intent.extra.CALLING_PACKAGE";
    public static final String EXTRA_CC = "android.intent.extra.CC";
    @SystemApi
    @Deprecated
    public static final String EXTRA_CDMA_DEFAULT_ROAMING_INDICATOR = "cdmaDefaultRoamingIndicator";
    @SystemApi
    @Deprecated
    public static final String EXTRA_CDMA_ROAMING_INDICATOR = "cdmaRoamingIndicator";
    @Deprecated
    public static final String EXTRA_CHANGED_COMPONENT_NAME = "android.intent.extra.changed_component_name";
    public static final String EXTRA_CHANGED_COMPONENT_NAME_LIST = "android.intent.extra.changed_component_name_list";
    public static final String EXTRA_CHANGED_PACKAGE_LIST = "android.intent.extra.changed_package_list";
    public static final String EXTRA_CHANGED_UID_LIST = "android.intent.extra.changed_uid_list";
    public static final String EXTRA_CHOOSER_REFINEMENT_INTENT_SENDER = "android.intent.extra.CHOOSER_REFINEMENT_INTENT_SENDER";
    public static final String EXTRA_CHOOSER_TARGETS = "android.intent.extra.CHOOSER_TARGETS";
    public static final String EXTRA_CHOSEN_COMPONENT = "android.intent.extra.CHOSEN_COMPONENT";
    public static final String EXTRA_CHOSEN_COMPONENT_INTENT_SENDER = "android.intent.extra.CHOSEN_COMPONENT_INTENT_SENDER";
    public static final String EXTRA_CLIENT_INTENT = "android.intent.extra.client_intent";
    public static final String EXTRA_CLIENT_LABEL = "android.intent.extra.client_label";
    public static final String EXTRA_COMPONENT_NAME = "android.intent.extra.COMPONENT_NAME";
    public static final String EXTRA_CONTENT_ANNOTATIONS = "android.intent.extra.CONTENT_ANNOTATIONS";
    public static final String EXTRA_CONTENT_QUERY = "android.intent.extra.CONTENT_QUERY";
    @SystemApi
    @Deprecated
    public static final String EXTRA_CSS_INDICATOR = "cssIndicator";
    @SystemApi
    @Deprecated
    public static final String EXTRA_DATA_OPERATOR_ALPHA_LONG = "data-operator-alpha-long";
    @SystemApi
    @Deprecated
    public static final String EXTRA_DATA_OPERATOR_ALPHA_SHORT = "data-operator-alpha-short";
    @SystemApi
    @Deprecated
    public static final String EXTRA_DATA_OPERATOR_NUMERIC = "data-operator-numeric";
    @SystemApi
    @Deprecated
    public static final String EXTRA_DATA_RADIO_TECH = "dataRadioTechnology";
    @SystemApi
    @Deprecated
    public static final String EXTRA_DATA_REG_STATE = "dataRegState";
    public static final String EXTRA_DATA_REMOVED = "android.intent.extra.DATA_REMOVED";
    @SystemApi
    @Deprecated
    public static final String EXTRA_DATA_ROAMING_TYPE = "dataRoamingType";
    public static final String EXTRA_DISTRACTION_RESTRICTIONS = "android.intent.extra.distraction_restrictions";
    public static final String EXTRA_DOCK_STATE = "android.intent.extra.DOCK_STATE";
    public static final int EXTRA_DOCK_STATE_CAR = 2;
    public static final int EXTRA_DOCK_STATE_DESK = 1;
    public static final int EXTRA_DOCK_STATE_HE_DESK = 4;
    public static final int EXTRA_DOCK_STATE_LE_DESK = 3;
    public static final int EXTRA_DOCK_STATE_UNDOCKED = 0;
    public static final String EXTRA_DONT_KILL_APP = "android.intent.extra.DONT_KILL_APP";
    public static final String EXTRA_DURATION_MILLIS = "android.intent.extra.DURATION_MILLIS";
    public static final String EXTRA_EMAIL = "android.intent.extra.EMAIL";
    @SystemApi
    @Deprecated
    public static final String EXTRA_EMERGENCY_ONLY = "emergencyOnly";
    public static final String EXTRA_EXCLUDE_COMPONENTS = "android.intent.extra.EXCLUDE_COMPONENTS";
    @SystemApi
    public static final String EXTRA_FORCE_FACTORY_RESET = "android.intent.extra.FORCE_FACTORY_RESET";
    @Deprecated
    public static final String EXTRA_FORCE_MASTER_CLEAR = "android.intent.extra.FORCE_MASTER_CLEAR";
    public static final String EXTRA_FROM_STORAGE = "android.intent.extra.FROM_STORAGE";
    public static final String EXTRA_HTML_TEXT = "android.intent.extra.HTML_TEXT";
    public static final String EXTRA_INDEX = "android.intent.extra.INDEX";
    public static final String EXTRA_INITIAL_INTENTS = "android.intent.extra.INITIAL_INTENTS";
    public static final String EXTRA_INSTALLER_PACKAGE_NAME = "android.intent.extra.INSTALLER_PACKAGE_NAME";
    public static final String EXTRA_INSTALL_RESULT = "android.intent.extra.INSTALL_RESULT";
    @SystemApi
    public static final String EXTRA_INSTANT_APP_ACTION = "android.intent.extra.INSTANT_APP_ACTION";
    @SystemApi
    public static final String EXTRA_INSTANT_APP_BUNDLES = "android.intent.extra.INSTANT_APP_BUNDLES";
    @SystemApi
    public static final String EXTRA_INSTANT_APP_EXTRAS = "android.intent.extra.INSTANT_APP_EXTRAS";
    @SystemApi
    public static final String EXTRA_INSTANT_APP_FAILURE = "android.intent.extra.INSTANT_APP_FAILURE";
    @SystemApi
    public static final String EXTRA_INSTANT_APP_HOSTNAME = "android.intent.extra.INSTANT_APP_HOSTNAME";
    @SystemApi
    public static final String EXTRA_INSTANT_APP_SUCCESS = "android.intent.extra.INSTANT_APP_SUCCESS";
    @SystemApi
    public static final String EXTRA_INSTANT_APP_TOKEN = "android.intent.extra.INSTANT_APP_TOKEN";
    public static final String EXTRA_INTENT = "android.intent.extra.INTENT";
    @SystemApi
    @Deprecated
    public static final String EXTRA_IS_DATA_ROAMING_FROM_REGISTRATION = "isDataRoamingFromRegistration";
    @SystemApi
    @Deprecated
    public static final String EXTRA_IS_USING_CARRIER_AGGREGATION = "isUsingCarrierAggregation";
    public static final String EXTRA_KEY_CONFIRM = "android.intent.extra.KEY_CONFIRM";
    public static final String EXTRA_KEY_EVENT = "android.intent.extra.KEY_EVENT";
    public static final String EXTRA_LAUNCHER_EXTRAS = "android.intent.extra.LAUNCHER_EXTRAS";
    public static final String EXTRA_LOCAL_ONLY = "android.intent.extra.LOCAL_ONLY";
    public static final String EXTRA_LOCUS_ID = "android.intent.extra.LOCUS_ID";
    @SystemApi
    public static final String EXTRA_LONG_VERSION_CODE = "android.intent.extra.LONG_VERSION_CODE";
    @SystemApi
    @Deprecated
    public static final String EXTRA_LTE_EARFCN_RSRP_BOOST = "LteEarfcnRsrpBoost";
    @SystemApi
    @Deprecated
    public static final String EXTRA_MANUAL = "manual";
    public static final String EXTRA_MEDIA_RESOURCE_TYPE = "android.intent.extra.MEDIA_RESOURCE_TYPE";
    public static final int EXTRA_MEDIA_RESOURCE_TYPE_AUDIO_CODEC = 1;
    public static final int EXTRA_MEDIA_RESOURCE_TYPE_VIDEO_CODEC = 0;
    public static final String EXTRA_MIME_TYPES = "android.intent.extra.MIME_TYPES";
    @SystemApi
    @Deprecated
    public static final String EXTRA_NETWORK_ID = "networkId";
    public static final String EXTRA_NOT_UNKNOWN_SOURCE = "android.intent.extra.NOT_UNKNOWN_SOURCE";
    @SystemApi
    @Deprecated
    public static final String EXTRA_OPERATOR_ALPHA_LONG = "operator-alpha-long";
    @SystemApi
    @Deprecated
    public static final String EXTRA_OPERATOR_ALPHA_SHORT = "operator-alpha-short";
    @SystemApi
    @Deprecated
    public static final String EXTRA_OPERATOR_NUMERIC = "operator-numeric";
    @SystemApi
    public static final String EXTRA_ORIGINATING_UID = "android.intent.extra.ORIGINATING_UID";
    public static final String EXTRA_ORIGINATING_URI = "android.intent.extra.ORIGINATING_URI";
    @SystemApi
    public static final String EXTRA_PACKAGES = "android.intent.extra.PACKAGES";
    public static final String EXTRA_PACKAGE_NAME = "android.intent.extra.PACKAGE_NAME";
    @SystemApi
    public static final String EXTRA_PERMISSION_GROUP_NAME = "android.intent.extra.PERMISSION_GROUP_NAME";
    @SystemApi
    public static final String EXTRA_PERMISSION_NAME = "android.intent.extra.PERMISSION_NAME";
    public static final String EXTRA_PHONE_NUMBER = "android.intent.extra.PHONE_NUMBER";
    public static final String EXTRA_PROCESS_TEXT = "android.intent.extra.PROCESS_TEXT";
    public static final String EXTRA_PROCESS_TEXT_READONLY = "android.intent.extra.PROCESS_TEXT_READONLY";
    @Deprecated
    public static final String EXTRA_QUICK_VIEW_ADVANCED = "android.intent.extra.QUICK_VIEW_ADVANCED";
    public static final String EXTRA_QUICK_VIEW_FEATURES = "android.intent.extra.QUICK_VIEW_FEATURES";
    public static final String EXTRA_QUIET_MODE = "android.intent.extra.QUIET_MODE";
    @SystemApi
    public static final String EXTRA_REASON = "android.intent.extra.REASON";
    public static final String EXTRA_REFERRER = "android.intent.extra.REFERRER";
    public static final String EXTRA_REFERRER_NAME = "android.intent.extra.REFERRER_NAME";
    @SystemApi
    public static final String EXTRA_REMOTE_CALLBACK = "android.intent.extra.REMOTE_CALLBACK";
    public static final String EXTRA_REMOTE_INTENT_TOKEN = "android.intent.extra.remote_intent_token";
    public static final String EXTRA_REMOVED_FOR_ALL_USERS = "android.intent.extra.REMOVED_FOR_ALL_USERS";
    public static final String EXTRA_REPLACEMENT_EXTRAS = "android.intent.extra.REPLACEMENT_EXTRAS";
    public static final String EXTRA_REPLACING = "android.intent.extra.REPLACING";
    public static final String EXTRA_RESTRICTIONS_BUNDLE = "android.intent.extra.restrictions_bundle";
    public static final String EXTRA_RESTRICTIONS_INTENT = "android.intent.extra.restrictions_intent";
    public static final String EXTRA_RESTRICTIONS_LIST = "android.intent.extra.restrictions_list";
    @SystemApi
    public static final String EXTRA_RESULT_NEEDED = "android.intent.extra.RESULT_NEEDED";
    public static final String EXTRA_RESULT_RECEIVER = "android.intent.extra.RESULT_RECEIVER";
    public static final String EXTRA_RETURN_RESULT = "android.intent.extra.RETURN_RESULT";
    @SystemApi
    public static final String EXTRA_ROLE_NAME = "android.intent.extra.ROLE_NAME";
    public static final String EXTRA_SERVICE_STATE = "android.intent.extra.SERVICE_STATE";
    public static final String EXTRA_SETTING_NAME = "setting_name";
    public static final String EXTRA_SETTING_NEW_VALUE = "new_value";
    public static final String EXTRA_SETTING_PREVIOUS_VALUE = "previous_value";
    public static final String EXTRA_SETTING_RESTORED_FROM_SDK_INT = "restored_from_sdk_int";
    @Deprecated
    public static final String EXTRA_SHORTCUT_ICON = "android.intent.extra.shortcut.ICON";
    @Deprecated
    public static final String EXTRA_SHORTCUT_ICON_RESOURCE = "android.intent.extra.shortcut.ICON_RESOURCE";
    public static final String EXTRA_SHORTCUT_ID = "android.intent.extra.shortcut.ID";
    @Deprecated
    public static final String EXTRA_SHORTCUT_INTENT = "android.intent.extra.shortcut.INTENT";
    @Deprecated
    public static final String EXTRA_SHORTCUT_NAME = "android.intent.extra.shortcut.NAME";
    public static final String EXTRA_SHUTDOWN_USERSPACE_ONLY = "android.intent.extra.SHUTDOWN_USERSPACE_ONLY";
    public static final String EXTRA_SIM_ACTIVATION_RESPONSE = "android.intent.extra.SIM_ACTIVATION_RESPONSE";
    public static final String EXTRA_SPLIT_NAME = "android.intent.extra.SPLIT_NAME";
    public static final String EXTRA_STREAM = "android.intent.extra.STREAM";
    public static final String EXTRA_SUBJECT = "android.intent.extra.SUBJECT";
    public static final String EXTRA_SUSPENDED_PACKAGE_EXTRAS = "android.intent.extra.SUSPENDED_PACKAGE_EXTRAS";
    @SystemApi
    @Deprecated
    public static final String EXTRA_SYSTEM_ID = "systemId";
    public static final String EXTRA_TASK_ID = "android.intent.extra.TASK_ID";
    public static final String EXTRA_TEMPLATE = "android.intent.extra.TEMPLATE";
    public static final String EXTRA_TEXT = "android.intent.extra.TEXT";
    public static final String EXTRA_THERMAL_STATE = "android.intent.extra.THERMAL_STATE";
    public static final int EXTRA_THERMAL_STATE_EXCEEDED = 2;
    public static final int EXTRA_THERMAL_STATE_NORMAL = 0;
    public static final int EXTRA_THERMAL_STATE_WARNING = 1;
    public static final String EXTRA_TIME_PREF_24_HOUR_FORMAT = "android.intent.extra.TIME_PREF_24_HOUR_FORMAT";
    public static final int EXTRA_TIME_PREF_VALUE_USE_12_HOUR = 0;
    public static final int EXTRA_TIME_PREF_VALUE_USE_24_HOUR = 1;
    public static final int EXTRA_TIME_PREF_VALUE_USE_LOCALE_DEFAULT = 2;
    public static final String EXTRA_TITLE = "android.intent.extra.TITLE";
    public static final String EXTRA_UID = "android.intent.extra.UID";
    public static final String EXTRA_UNINSTALL_ALL_USERS = "android.intent.extra.UNINSTALL_ALL_USERS";
    @SystemApi
    public static final String EXTRA_UNKNOWN_INSTANT_APP = "android.intent.extra.UNKNOWN_INSTANT_APP";
    public static final String EXTRA_USER = "android.intent.extra.USER";
    public static final String EXTRA_USER_HANDLE = "android.intent.extra.user_handle";
    public static final String EXTRA_USER_ID = "android.intent.extra.USER_ID";
    public static final String EXTRA_USER_REQUESTED_SHUTDOWN = "android.intent.extra.USER_REQUESTED_SHUTDOWN";
    @SystemApi
    public static final String EXTRA_VERIFICATION_BUNDLE = "android.intent.extra.VERIFICATION_BUNDLE";
    @Deprecated
    public static final String EXTRA_VERSION_CODE = "android.intent.extra.VERSION_CODE";
    @SystemApi
    @Deprecated
    public static final String EXTRA_VOICE_RADIO_TECH = "radioTechnology";
    @SystemApi
    @Deprecated
    public static final String EXTRA_VOICE_REG_STATE = "voiceRegState";
    @SystemApi
    @Deprecated
    public static final String EXTRA_VOICE_ROAMING_TYPE = "voiceRoamingType";
    public static final String EXTRA_WIPE_ESIMS = "com.android.internal.intent.extra.WIPE_ESIMS";
    public static final String EXTRA_WIPE_EXTERNAL_STORAGE = "android.intent.extra.WIPE_EXTERNAL_STORAGE";
    public static final int FILL_IN_ACTION = 1;
    public static final int FILL_IN_CATEGORIES = 4;
    public static final int FILL_IN_CLIP_DATA = 128;
    public static final int FILL_IN_COMPONENT = 8;
    public static final int FILL_IN_DATA = 2;
    public static final int FILL_IN_IDENTIFIER = 256;
    public static final int FILL_IN_PACKAGE = 16;
    public static final int FILL_IN_SELECTOR = 64;
    public static final int FILL_IN_SOURCE_BOUNDS = 32;
    public static final int FLAG_ACTIVITY_BROUGHT_TO_FRONT = 4194304;
    public static final int FLAG_ACTIVITY_CLEAR_TASK = 32768;
    public static final int FLAG_ACTIVITY_CLEAR_TOP = 67108864;
    @Deprecated
    public static final int FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET = 524288;
    public static final int FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS = 8388608;
    public static final int FLAG_ACTIVITY_FORWARD_RESULT = 33554432;
    public static final int FLAG_ACTIVITY_LAUNCHED_FROM_HISTORY = 1048576;
    public static final int FLAG_ACTIVITY_LAUNCH_ADJACENT = 4096;
    public static final int FLAG_ACTIVITY_MATCH_EXTERNAL = 2048;
    public static final int FLAG_ACTIVITY_MULTIPLE_TASK = 134217728;
    public static final int FLAG_ACTIVITY_NEW_DOCUMENT = 524288;
    public static final int FLAG_ACTIVITY_NEW_TASK = 268435456;
    public static final int FLAG_ACTIVITY_NO_ANIMATION = 65536;
    public static final int FLAG_ACTIVITY_NO_HISTORY = 1073741824;
    public static final int FLAG_ACTIVITY_NO_USER_ACTION = 262144;
    public static final int FLAG_ACTIVITY_PREVIOUS_IS_TOP = 16777216;
    public static final int FLAG_ACTIVITY_REORDER_TO_FRONT = 131072;
    public static final int FLAG_ACTIVITY_RESET_TASK_IF_NEEDED = 2097152;
    public static final int FLAG_ACTIVITY_RETAIN_IN_RECENTS = 8192;
    public static final int FLAG_ACTIVITY_SINGLE_TOP = 536870912;
    public static final int FLAG_ACTIVITY_TASK_ON_HOME = 16384;
    public static final int FLAG_DEBUG_LOG_RESOLUTION = 8;
    @Deprecated
    public static final int FLAG_DEBUG_TRIAGED_MISSING = 256;
    public static final int FLAG_DIRECT_BOOT_AUTO = 256;
    public static final int FLAG_EXCLUDE_STOPPED_PACKAGES = 16;
    public static final int FLAG_FROM_BACKGROUND = 4;
    public static final int FLAG_GRANT_PERSISTABLE_URI_PERMISSION = 64;
    public static final int FLAG_GRANT_PREFIX_URI_PERMISSION = 128;
    public static final int FLAG_GRANT_READ_URI_PERMISSION = 1;
    public static final int FLAG_GRANT_WRITE_URI_PERMISSION = 2;
    public static final int FLAG_IGNORE_EPHEMERAL = 512;
    public static final int FLAG_INCLUDE_STOPPED_PACKAGES = 32;
    public static final int FLAG_RECEIVER_BOOT_UPGRADE = 33554432;
    public static final int FLAG_RECEIVER_EXCLUDE_BACKGROUND = 8388608;
    public static final int FLAG_RECEIVER_FOREGROUND = 268435456;
    public static final int FLAG_RECEIVER_FROM_SHELL = 4194304;
    public static final int FLAG_RECEIVER_INCLUDE_BACKGROUND = 16777216;
    public static final int FLAG_RECEIVER_NO_ABORT = 134217728;
    public static final int FLAG_RECEIVER_OFFLOAD = Integer.MIN_VALUE;
    public static final int FLAG_RECEIVER_REGISTERED_ONLY = 1073741824;
    @UnsupportedAppUsage
    public static final int FLAG_RECEIVER_REGISTERED_ONLY_BEFORE_BOOT = 67108864;
    public static final int FLAG_RECEIVER_REPLACE_PENDING = 536870912;
    public static final int FLAG_RECEIVER_VISIBLE_TO_INSTANT_APPS = 2097152;
    public static final int IMMUTABLE_FLAGS = 195;
    public static final String METADATA_DOCK_HOME = "android.dock_home";
    @SystemApi
    public static final String METADATA_SETUP_VERSION = "android.SETUP_VERSION";
    private static final String TAG = "Intent";
    private static final String TAG_CATEGORIES = "categories";
    private static final String TAG_EXTRA = "extra";
    public static final int URI_ALLOW_UNSAFE = 4;
    public static final int URI_ANDROID_APP_SCHEME = 2;
    public static final int URI_INTENT_SCHEME = 1;
    private String mAction;
    private ArraySet<String> mCategories;
    private ClipData mClipData;
    private ComponentName mComponent;
    private int mContentUserHint = -2;
    private Uri mData;
    @UnsupportedAppUsage
    private Bundle mExtras;
    private int mFlags;
    private String mIdentifier;
    private String mLaunchToken;
    private String mPackage;
    private Intent mSelector;
    private Rect mSourceBounds;
    private String mType;

    public Intent() {
    }

    public Intent(Context context, Class<?> class_) {
        this.mComponent = new ComponentName(context, class_);
    }

    public Intent(Intent intent) {
        this(intent, 0);
    }

    private Intent(Intent parcelable, int n) {
        this.mAction = ((Intent)parcelable).mAction;
        this.mData = ((Intent)parcelable).mData;
        this.mType = ((Intent)parcelable).mType;
        this.mIdentifier = ((Intent)parcelable).mIdentifier;
        this.mPackage = ((Intent)parcelable).mPackage;
        this.mComponent = ((Intent)parcelable).mComponent;
        Object object = ((Intent)parcelable).mCategories;
        if (object != null) {
            this.mCategories = new ArraySet<String>((ArraySet<String>)object);
        }
        if (n != 1) {
            this.mFlags = ((Intent)parcelable).mFlags;
            this.mContentUserHint = ((Intent)parcelable).mContentUserHint;
            this.mLaunchToken = ((Intent)parcelable).mLaunchToken;
            object = ((Intent)parcelable).mSourceBounds;
            if (object != null) {
                this.mSourceBounds = new Rect((Rect)object);
            }
            if ((object = ((Intent)parcelable).mSelector) != null) {
                this.mSelector = new Intent((Intent)object);
            }
            if (n != 2) {
                object = ((Intent)parcelable).mExtras;
                if (object != null) {
                    this.mExtras = new Bundle((Bundle)object);
                }
                if ((parcelable = ((Intent)parcelable).mClipData) != null) {
                    this.mClipData = new ClipData((ClipData)parcelable);
                }
            } else {
                parcelable = ((Intent)parcelable).mExtras;
                if (parcelable != null && !((BaseBundle)((Object)parcelable)).maybeIsEmpty()) {
                    this.mExtras = Bundle.STRIPPED;
                }
            }
        }
    }

    protected Intent(Parcel parcel) {
        this.readFromParcel(parcel);
    }

    public Intent(String string2) {
        this.setAction(string2);
    }

    public Intent(String string2, Uri uri) {
        this.setAction(string2);
        this.mData = uri;
    }

    public Intent(String string2, Uri uri, Context context, Class<?> class_) {
        this.setAction(string2);
        this.mData = uri;
        this.mComponent = new ComponentName(context, class_);
    }

    public static Intent createChooser(Intent intent, CharSequence charSequence) {
        return Intent.createChooser(intent, charSequence, null);
    }

    public static Intent createChooser(Intent arrstring, CharSequence object, IntentSender object2) {
        int n;
        Intent intent = new Intent(ACTION_CHOOSER);
        intent.putExtra(EXTRA_INTENT, (Parcelable)arrstring);
        if (object != null) {
            intent.putExtra(EXTRA_TITLE, (CharSequence)object);
        }
        if (object2 != null) {
            intent.putExtra(EXTRA_CHOSEN_COMPONENT_INTENT_SENDER, (Parcelable)object2);
        }
        if ((n = arrstring.getFlags() & 195) != 0) {
            object2 = arrstring.getClipData();
            object = object2;
            if (object2 == null) {
                object = object2;
                if (arrstring.getData() != null) {
                    object2 = new ClipData.Item(arrstring.getData());
                    if (arrstring.getType() != null) {
                        object = new String[]{arrstring.getType()};
                        arrstring = object;
                    } else {
                        arrstring = new String[]{};
                    }
                    object = new ClipData(null, arrstring, (ClipData.Item)object2);
                }
            }
            if (object != null) {
                intent.setClipData((ClipData)object);
                intent.addFlags(n);
            }
        }
        return intent;
    }

    public static String dockStateToString(int n) {
        if (n != 0) {
            if (n != 1) {
                if (n != 2) {
                    if (n != 3) {
                        if (n != 4) {
                            return Integer.toString(n);
                        }
                        return "EXTRA_DOCK_STATE_HE_DESK";
                    }
                    return "EXTRA_DOCK_STATE_LE_DESK";
                }
                return "EXTRA_DOCK_STATE_CAR";
            }
            return "EXTRA_DOCK_STATE_DESK";
        }
        return "EXTRA_DOCK_STATE_UNDOCKED";
    }

    @Deprecated
    public static Intent getIntent(String string2) throws URISyntaxException {
        return Intent.parseUri(string2, 0);
    }

    public static Intent getIntentOld(String string2) throws URISyntaxException {
        return Intent.getIntentOld(string2, 0);
    }

    /*
     * Exception decompiling
     */
    private static Intent getIntentOld(String var0, int var1_1) throws URISyntaxException {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [6[CASE]], but top level block is 3[TRYBLOCK]
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.processEndingBlocks(Op04StructuredStatement.java:427)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:479)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:607)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:696)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:184)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:129)
        // org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:96)
        // org.benf.cfr.reader.entities.Method.analyse(Method.java:397)
        // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:906)
        // org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:797)
        // org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:225)
        // org.benf.cfr.reader.Driver.doJar(Driver.java:109)
        // org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:65)
        // org.benf.cfr.reader.Main.main(Main.java:48)
        throw new IllegalStateException("Decompilation failed");
    }

    public static boolean isAccessUriMode(int n) {
        boolean bl = (n & 3) != 0;
        return bl;
    }

    private static ClipData.Item makeClipItem(ArrayList<Uri> object, ArrayList<CharSequence> object2, ArrayList<String> object3, int n) {
        object = object != null ? ((ArrayList)object).get(n) : null;
        object2 = object2 != null ? ((ArrayList)object2).get(n) : null;
        object3 = object3 != null ? ((ArrayList)object3).get(n) : null;
        return new ClipData.Item((CharSequence)object2, (String)object3, null, (Uri)object);
    }

    public static Intent makeMainActivity(ComponentName componentName) {
        Intent intent = new Intent(ACTION_MAIN);
        intent.setComponent(componentName);
        intent.addCategory(CATEGORY_LAUNCHER);
        return intent;
    }

    public static Intent makeMainSelectorActivity(String string2, String string3) {
        Intent intent = new Intent(ACTION_MAIN);
        intent.addCategory(CATEGORY_LAUNCHER);
        Intent intent2 = new Intent();
        intent2.setAction(string2);
        intent2.addCategory(string3);
        intent.setSelector(intent2);
        return intent;
    }

    public static Intent makeRestartActivityTask(ComponentName parcelable) {
        parcelable = Intent.makeMainActivity((ComponentName)parcelable);
        ((Intent)parcelable).addFlags(268468224);
        return parcelable;
    }

    public static String normalizeMimeType(String string2) {
        if (string2 == null) {
            return null;
        }
        String string3 = string2.trim().toLowerCase(Locale.ROOT);
        int n = string3.indexOf(59);
        string2 = string3;
        if (n != -1) {
            string2 = string3.substring(0, n);
        }
        return string2;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    @UnsupportedAppUsage
    public static Intent parseCommandArgs(ShellCommand var0, CommandOptionHandler var1_3) throws URISyntaxException {
        var2_4 = new Intent();
        var3_5 = null;
        var4_9 = null;
        var5_10 = 0;
        var6_11 = var2_4;
        while ((var7_12 = var0.getNextOption()) != null) {
            block148 : {
                block147 : {
                    var8_13 = -1;
                    switch (var7_12.hashCode()) {
                        case 1816558127: {
                            if (!var7_12.equals("--grant-write-uri-permission")) break;
                            var8_13 = 26;
                            ** break;
                        }
                        case 1765369476: {
                            if (!var7_12.equals("--activity-multiple-task")) break;
                            var8_13 = 37;
                            ** break;
                        }
                        case 1742380566: {
                            if (!var7_12.equals("--grant-read-uri-permission")) break;
                            var8_13 = 25;
                            ** break;
                        }
                        case 1652786753: {
                            if (!var7_12.equals("--receiver-foreground")) break;
                            var8_13 = 50;
                            ** break;
                        }
                        case 1453225122: {
                            if (!var7_12.equals("--receiver-no-abort")) break;
                            var8_13 = 51;
                            ** break;
                        }
                        case 1398403374: {
                            if (!var7_12.equals("--activity-launched-from-history")) break;
                            var8_13 = 36;
                            ** break;
                        }
                        case 1353919836: {
                            if (!var7_12.equals("--activity-clear-when-task-reset")) break;
                            var8_13 = 34;
                            ** break;
                        }
                        case 1332992761: {
                            if (!var7_12.equals("--esal")) break;
                            var8_13 = 20;
                            ** break;
                        }
                        case 1332986034: {
                            if (!var7_12.equals("--elal")) break;
                            var8_13 = 15;
                            ** break;
                        }
                        case 1332983151: {
                            if (!var7_12.equals("--eial")) break;
                            var8_13 = 12;
                            ** break;
                        }
                        case 1332980268: {
                            if (!var7_12.equals("--efal")) break;
                            var8_13 = 18;
                            ** break;
                        }
                        case 1207327103: {
                            if (!var7_12.equals("--selector")) break;
                            var8_13 = 53;
                            ** break;
                        }
                        case 1110195121: {
                            if (!var7_12.equals("--activity-match-external")) break;
                            var8_13 = 47;
                            ** break;
                        }
                        case 775126336: {
                            if (!var7_12.equals("--receiver-replace-pending")) break;
                            var8_13 = 49;
                            ** break;
                        }
                        case 749648146: {
                            if (!var7_12.equals("--include-stopped-packages")) break;
                            var8_13 = 30;
                            ** break;
                        }
                        case 580418080: {
                            if (!var7_12.equals("--exclude-stopped-packages")) break;
                            var8_13 = 29;
                            ** break;
                        }
                        case 527014976: {
                            if (!var7_12.equals("--grant-persistable-uri-permission")) break;
                            var8_13 = 27;
                            ** break;
                        }
                        case 438531630: {
                            if (!var7_12.equals("--activity-single-top")) break;
                            var8_13 = 44;
                            ** break;
                        }
                        case 436286937: {
                            if (!var7_12.equals("--receiver-registered-only")) break;
                            var8_13 = 48;
                            ** break;
                        }
                        case 429439306: {
                            if (!var7_12.equals("--activity-no-user-action")) break;
                            var8_13 = 40;
                            ** break;
                        }
                        case 236677687: {
                            if (!var7_12.equals("--activity-clear-top")) break;
                            var8_13 = 33;
                            ** break;
                        }
                        case 190913209: {
                            if (!var7_12.equals("--activity-reset-task-if-needed")) break;
                            var8_13 = 43;
                            ** break;
                        }
                        case 88747734: {
                            if (!var7_12.equals("--activity-no-animation")) break;
                            var8_13 = 38;
                            ** break;
                        }
                        case 69120454: {
                            if (!var7_12.equals("--activity-exclude-from-recents")) break;
                            var8_13 = 35;
                            ** break;
                        }
                        case 42999776: {
                            if (!var7_12.equals("--esn")) break;
                            var8_13 = 7;
                            ** break;
                        }
                        case 42999763: {
                            if (!var7_12.equals("--esa")) break;
                            var8_13 = 19;
                            ** break;
                        }
                        case 42999546: {
                            if (!var7_12.equals("--ela")) break;
                            var8_13 = 14;
                            ** break;
                        }
                        case 42999453: {
                            if (!var7_12.equals("--eia")) break;
                            var8_13 = 11;
                            ** break;
                        }
                        case 42999360: {
                            if (!var7_12.equals("--efa")) break;
                            var8_13 = 17;
                            ** break;
                        }
                        case 42999280: {
                            if (!var7_12.equals("--ecn")) break;
                            var8_13 = 10;
                            ** break;
                        }
                        case 1387093: {
                            if (!var7_12.equals("--ez")) break;
                            var8_13 = 21;
                            ** break;
                        }
                        case 1387088: {
                            if (!var7_12.equals("--eu")) break;
                            var8_13 = 9;
                            ** break;
                        }
                        case 1387086: {
                            if (!var7_12.equals("--es")) break;
                            var8_13 = 6;
                            ** break;
                        }
                        case 1387079: {
                            if (!var7_12.equals("--el")) break;
                            var8_13 = 13;
                            ** break;
                        }
                        case 1387076: {
                            if (!var7_12.equals("--ei")) break;
                            var8_13 = 8;
                            ** break;
                        }
                        case 1387073: {
                            if (!var7_12.equals("--ef")) break;
                            var8_13 = 16;
                            ** break;
                        }
                        case 1511: {
                            if (!var7_12.equals("-t")) break;
                            var8_13 = 2;
                            ** break;
                        }
                        case 1507: {
                            if (!var7_12.equals("-p")) break;
                            var8_13 = 23;
                            ** break;
                        }
                        case 1505: {
                            if (!var7_12.equals("-n")) break;
                            var8_13 = 22;
                            ** break;
                        }
                        case 1500: {
                            if (!var7_12.equals("-i")) break;
                            var8_13 = 3;
                            ** break;
                        }
                        case 1497: {
                            if (!var7_12.equals("-f")) break;
                            var8_13 = 24;
                            ** break;
                        }
                        case 1496: {
                            if (!var7_12.equals("-e")) break;
                            var8_13 = 5;
                            ** break;
                        }
                        case 1495: {
                            if (!var7_12.equals("-d")) break;
                            var8_13 = 1;
                            ** break;
                        }
                        case 1494: {
                            if (!var7_12.equals("-c")) break;
                            var8_13 = 4;
                            ** break;
                        }
                        case 1492: {
                            if (!var7_12.equals("-a")) break;
                            var8_13 = 0;
                            ** break;
                        }
                        case -780160399: {
                            if (!var7_12.equals("--receiver-include-background")) break;
                            var8_13 = 52;
                            ** break;
                        }
                        case -792169302: {
                            if (!var7_12.equals("--activity-previous-is-top")) break;
                            var8_13 = 41;
                            ** break;
                        }
                        case -833172539: {
                            if (!var7_12.equals("--activity-brought-to-front")) break;
                            var8_13 = 32;
                            ** break;
                        }
                        case -848214457: {
                            if (!var7_12.equals("--activity-reorder-to-front")) break;
                            var8_13 = 42;
                            ** break;
                        }
                        case -1069446353: {
                            if (!var7_12.equals("--debug-log-resolution")) break;
                            var8_13 = 31;
                            ** break;
                        }
                        case -1252939549: {
                            if (!var7_12.equals("--activity-clear-task")) break;
                            var8_13 = 45;
                            ** break;
                        }
                        case -1630559130: {
                            if (!var7_12.equals("--activity-no-history")) break;
                            var8_13 = 39;
                            ** break;
                        }
                        case -2118172637: {
                            if (!var7_12.equals("--activity-task-on-home")) break;
                            var8_13 = 46;
                            ** break;
                        }
                        case -2147394086: {
                            if (!var7_12.equals("--grant-prefix-uri-permission")) break;
                            var8_13 = 28;
                            break block147;
                        }
                    }
                    ** break;
                }
                switch (var8_13) {
                    default: {
                        if (var1_3 != null && var1_3.handleOption((String)var7_12, (ShellCommand)var0)) {
                            var8_13 = var5_10;
                            break;
                        }
                        break block148;
                    }
                    case 53: {
                        var6_11.setDataAndType((Uri)var3_6, (String)var4_9);
                        var6_11 = new Intent();
                        var8_13 = var5_10;
                        break;
                    }
                    case 52: {
                        var6_11.addFlags(16777216);
                        var8_13 = var5_10;
                        break;
                    }
                    case 51: {
                        var6_11.addFlags(134217728);
                        var8_13 = var5_10;
                        break;
                    }
                    case 50: {
                        var6_11.addFlags(268435456);
                        var8_13 = var5_10;
                        break;
                    }
                    case 49: {
                        var6_11.addFlags(536870912);
                        var8_13 = var5_10;
                        break;
                    }
                    case 48: {
                        var6_11.addFlags(1073741824);
                        var8_13 = var5_10;
                        break;
                    }
                    case 47: {
                        var6_11.addFlags(2048);
                        var8_13 = var5_10;
                        break;
                    }
                    case 46: {
                        var6_11.addFlags(16384);
                        var8_13 = var5_10;
                        break;
                    }
                    case 45: {
                        var6_11.addFlags(32768);
                        var8_13 = var5_10;
                        break;
                    }
                    case 44: {
                        var6_11.addFlags(536870912);
                        var8_13 = var5_10;
                        break;
                    }
                    case 43: {
                        var6_11.addFlags(2097152);
                        var8_13 = var5_10;
                        break;
                    }
                    case 42: {
                        var6_11.addFlags(131072);
                        var8_13 = var5_10;
                        break;
                    }
                    case 41: {
                        var6_11.addFlags(16777216);
                        var8_13 = var5_10;
                        break;
                    }
                    case 40: {
                        var6_11.addFlags(262144);
                        var8_13 = var5_10;
                        break;
                    }
                    case 39: {
                        var6_11.addFlags(1073741824);
                        var8_13 = var5_10;
                        break;
                    }
                    case 38: {
                        var6_11.addFlags(65536);
                        var8_13 = var5_10;
                        break;
                    }
                    case 37: {
                        var6_11.addFlags(134217728);
                        var8_13 = var5_10;
                        break;
                    }
                    case 36: {
                        var6_11.addFlags(1048576);
                        var8_13 = var5_10;
                        break;
                    }
                    case 35: {
                        var6_11.addFlags(8388608);
                        var8_13 = var5_10;
                        break;
                    }
                    case 34: {
                        var6_11.addFlags(524288);
                        var8_13 = var5_10;
                        break;
                    }
                    case 33: {
                        var6_11.addFlags(67108864);
                        var8_13 = var5_10;
                        break;
                    }
                    case 32: {
                        var6_11.addFlags(4194304);
                        var8_13 = var5_10;
                        break;
                    }
                    case 31: {
                        var6_11.addFlags(8);
                        var8_13 = var5_10;
                        break;
                    }
                    case 30: {
                        var6_11.addFlags(32);
                        var8_13 = var5_10;
                        break;
                    }
                    case 29: {
                        var6_11.addFlags(16);
                        var8_13 = var5_10;
                        break;
                    }
                    case 28: {
                        var6_11.addFlags(128);
                        var8_13 = var5_10;
                        break;
                    }
                    case 27: {
                        var6_11.addFlags(64);
                        var8_13 = var5_10;
                        break;
                    }
                    case 26: {
                        var6_11.addFlags(2);
                        var8_13 = var5_10;
                        break;
                    }
                    case 25: {
                        var6_11.addFlags(1);
                        var8_13 = var5_10;
                        break;
                    }
                    case 24: {
                        var6_11.setFlags(Integer.decode(var0.getNextArgRequired()));
                        var8_13 = var5_10;
                        break;
                    }
                    case 23: {
                        var6_11.setPackage(var0.getNextArgRequired());
                        var8_13 = var5_10;
                        if (var6_11 != var2_4) break;
                        var8_13 = 1;
                        break;
                    }
                    case 22: {
                        var7_12 = var0.getNextArgRequired();
                        var9_14 = ComponentName.unflattenFromString((String)var7_12);
                        if (var9_14 == null) {
                            var0 = new StringBuilder();
                            var0.append("Bad component name: ");
                            var0.append((String)var7_12);
                            throw new IllegalArgumentException(var0.toString());
                        }
                        var6_11.setComponent((ComponentName)var9_14);
                        var8_13 = var5_10;
                        if (var6_11 != var2_4) break;
                        var8_13 = 1;
                        break;
                    }
                    case 21: {
                        var9_14 = var0.getNextArgRequired();
                        var7_12 = var0.getNextArgRequired().toLowerCase();
                        if (!"true".equals(var7_12) && !"t".equals(var7_12)) {
                            if (!"false".equals(var7_12) && !"f".equals(var7_12)) {
                                try {
                                    var8_13 = Integer.decode((String)var7_12);
                                    if (var8_13 != 0) {
                                        var10_15 = true;
                                    }
                                    var10_15 = false;
                                }
                                catch (NumberFormatException var0_1) {
                                    var0_2 = new StringBuilder();
                                    var0_2.append("Invalid boolean value: ");
                                    var0_2.append((String)var7_12);
                                    throw new IllegalArgumentException(var0_2.toString());
                                }
                            } else {
                                var10_15 = false;
                            }
                        } else {
                            var10_15 = true;
                        }
                        var6_11.putExtra((String)var9_14, var10_15);
                        var8_13 = var5_10;
                        break;
                    }
                    case 20: {
                        var7_12 = var0.getNextArgRequired();
                        var9_14 = var0.getNextArgRequired().split("(?<!\\\\),");
                        var11_16 = new ArrayList<E>(((String[])var9_14).length);
                        for (var8_13 = 0; var8_13 < ((String[])var9_14).length; ++var8_13) {
                            var11_16.add(var9_14[var8_13]);
                        }
                        var6_11.putExtra((String)var7_12, (Serializable)var11_16);
                        var8_13 = 1;
                        break;
                    }
                    case 19: {
                        var6_11.putExtra(var0.getNextArgRequired(), var0.getNextArgRequired().split("(?<!\\\\),"));
                        var8_13 = 1;
                        break;
                    }
                    case 18: {
                        var11_16 = var0.getNextArgRequired();
                        var9_14 = var0.getNextArgRequired().split(",");
                        var7_12 = new ArrayList<E>(((String[])var9_14).length);
                        for (var8_13 = 0; var8_13 < ((String[])var9_14).length; ++var8_13) {
                            var7_12.add(Float.valueOf(var9_14[var8_13]));
                        }
                        var6_11.putExtra((String)var11_16, (Serializable)var7_12);
                        var8_13 = 1;
                        break;
                    }
                    case 17: {
                        var7_12 = var0.getNextArgRequired();
                        var11_16 = var0.getNextArgRequired().split(",");
                        var9_14 = new float[((String[])var11_16).length];
                        for (var8_13 = 0; var8_13 < ((String[])var11_16).length; ++var8_13) {
                            var9_14[var8_13] = (String)Float.valueOf(var11_16[var8_13]).floatValue();
                        }
                        var6_11.putExtra((String)var7_12, (float[])var9_14);
                        var8_13 = 1;
                        break;
                    }
                    case 16: {
                        var6_11.putExtra(var0.getNextArgRequired(), Float.valueOf(var0.getNextArgRequired()));
                        var8_13 = 1;
                        break;
                    }
                    case 15: {
                        var9_14 = var0.getNextArgRequired();
                        var11_16 = var0.getNextArgRequired().split(",");
                        var7_12 = new ArrayList<E>(((String[])var11_16).length);
                        for (var8_13 = 0; var8_13 < ((String[])var11_16).length; ++var8_13) {
                            var7_12.add(Long.valueOf(var11_16[var8_13]));
                        }
                        var6_11.putExtra((String)var9_14, (Serializable)var7_12);
                        var8_13 = 1;
                        break;
                    }
                    case 14: {
                        var7_12 = var0.getNextArgRequired();
                        var11_16 = var0.getNextArgRequired().split(",");
                        var9_14 = new long[((String[])var11_16).length];
                        for (var8_13 = 0; var8_13 < ((String[])var11_16).length; ++var8_13) {
                            var9_14[var8_13] = (String)Long.valueOf(var11_16[var8_13]);
                        }
                        var6_11.putExtra((String)var7_12, (long[])var9_14);
                        var8_13 = 1;
                        break;
                    }
                    case 13: {
                        var6_11.putExtra(var0.getNextArgRequired(), Long.valueOf(var0.getNextArgRequired()));
                        var8_13 = var5_10;
                        break;
                    }
                    case 12: {
                        var7_12 = var0.getNextArgRequired();
                        var11_16 = var0.getNextArgRequired().split(",");
                        var9_14 = new ArrayList<E>(((String[])var11_16).length);
                        for (var8_13 = 0; var8_13 < ((String[])var11_16).length; ++var8_13) {
                            var9_14.add(Integer.decode(var11_16[var8_13]));
                        }
                        var6_11.putExtra((String)var7_12, (Serializable)var9_14);
                        var8_13 = var5_10;
                        break;
                    }
                    case 11: {
                        var9_14 = var0.getNextArgRequired();
                        var7_12 = var0.getNextArgRequired().split(",");
                        var11_16 = new int[((String[])var7_12).length];
                        for (var8_13 = 0; var8_13 < ((String[])var7_12).length; ++var8_13) {
                            var11_16[var8_13] = Integer.decode(var7_12[var8_13]);
                        }
                        var6_11.putExtra((String)var9_14, (int[])var11_16);
                        var8_13 = var5_10;
                        break;
                    }
                    case 10: {
                        var9_14 = var0.getNextArgRequired();
                        var7_12 = var0.getNextArgRequired();
                        var11_16 = ComponentName.unflattenFromString((String)var7_12);
                        if (var11_16 == null) {
                            var0 = new StringBuilder();
                            var0.append("Bad component name: ");
                            var0.append((String)var7_12);
                            throw new IllegalArgumentException(var0.toString());
                        }
                        var6_11.putExtra((String)var9_14, (Parcelable)var11_16);
                        var8_13 = var5_10;
                        break;
                    }
                    case 9: {
                        var6_11.putExtra(var0.getNextArgRequired(), Uri.parse(var0.getNextArgRequired()));
                        var8_13 = var5_10;
                        break;
                    }
                    case 8: {
                        var6_11.putExtra(var0.getNextArgRequired(), Integer.decode(var0.getNextArgRequired()));
                        var8_13 = var5_10;
                        break;
                    }
                    case 7: {
                        var6_11.putExtra(var0.getNextArgRequired(), (String)null);
                        var8_13 = var5_10;
                        break;
                    }
                    case 5: 
                    case 6: {
                        var6_11.putExtra(var0.getNextArgRequired(), var0.getNextArgRequired());
                        var8_13 = var5_10;
                        break;
                    }
                    case 4: {
                        var6_11.addCategory(var0.getNextArgRequired());
                        if (var6_11 == var2_4) {
                            var8_13 = 1;
                            break;
                        }
                        var8_13 = var5_10;
                        break;
                    }
                    case 3: {
                        var6_11.setIdentifier(var0.getNextArgRequired());
                        if (var6_11 == var2_4) {
                            var8_13 = 1;
                            break;
                        }
                        var8_13 = var5_10;
                        break;
                    }
                    case 2: {
                        var4_9 = var0.getNextArgRequired();
                        if (var6_11 == var2_4) {
                            var8_13 = 1;
                            break;
                        }
                        var8_13 = var5_10;
                        break;
                    }
                    case 1: {
                        var3_7 = Uri.parse(var0.getNextArgRequired());
                        if (var6_11 == var2_4) {
                            var8_13 = 1;
                            break;
                        }
                        var8_13 = var5_10;
                        break;
                    }
                    case 0: {
                        var6_11.setAction(var0.getNextArgRequired());
                        var8_13 = var6_11 == var2_4 ? 1 : var5_10;
                    }
                }
                var5_10 = var8_13;
                continue;
            }
            var0 = new StringBuilder();
            var0.append("Unknown option: ");
            var0.append((String)var7_12);
            throw new IllegalArgumentException(var0.toString());
        }
        var6_11.setDataAndType((Uri)var3_6, (String)var4_9);
        var8_13 = var6_11 != var2_4 ? 1 : 0;
        var1_3 = var6_11;
        if (var8_13 != 0) {
            var2_4.setSelector((Intent)var6_11);
            var1_3 = var2_4;
        }
        var6_11 = var0.getNextArg();
        var0 = null;
        if (var6_11 == null) {
            if (var8_13 != 0) {
                var0 = new Intent("android.intent.action.MAIN");
                var0.addCategory("android.intent.category.LAUNCHER");
            }
        } else if (var6_11.indexOf(58) >= 0) {
            var0 = Intent.parseUri((String)var6_11, 7);
        } else if (var6_11.indexOf(47) >= 0) {
            var0 = new Intent("android.intent.action.MAIN");
            var0.addCategory("android.intent.category.LAUNCHER");
            var0.setComponent(ComponentName.unflattenFromString((String)var6_11));
        } else {
            var0 = new Intent("android.intent.action.MAIN");
            var0.addCategory("android.intent.category.LAUNCHER");
            var0.setPackage((String)var6_11);
        }
        if (var0 != null) {
            var3_8 = var1_3.getExtras();
            var4_9 = null;
            var1_3.replaceExtras((Bundle)var4_9);
            var6_11 = var0.getExtras();
            var0.replaceExtras((Bundle)var4_9);
            if (var1_3.getAction() != null && var0.getCategories() != null) {
                var4_9 = new HashSet<String>(var0.getCategories()).iterator();
                while (var4_9.hasNext()) {
                    var0.removeCategory(var4_9.next());
                }
            }
            var1_3.fillIn((Intent)var0, 72);
            if (var3_8 == null) {
                var0 = var6_11;
            } else {
                var0 = var3_8;
                if (var6_11 != null) {
                    var6_11.putAll(var3_8);
                    var0 = var6_11;
                }
            }
            var1_3.replaceExtras((Bundle)var0);
            var5_10 = 1;
        }
        if (var5_10 == 0) throw new IllegalArgumentException("No intent supplied");
        return var1_3;
    }

    public static Intent parseIntent(Resources resources, XmlPullParser xmlPullParser, AttributeSet attributeSet) throws XmlPullParserException, IOException {
        int n;
        Intent intent = new Intent();
        TypedArray typedArray = resources.obtainAttributes(attributeSet, R.styleable.Intent);
        intent.setAction(typedArray.getString(2));
        Object object = typedArray.getString(3);
        String string2 = typedArray.getString(1);
        object = object != null ? Uri.parse((String)object) : null;
        intent.setDataAndType((Uri)object, string2);
        intent.setIdentifier(typedArray.getString(5));
        string2 = typedArray.getString(0);
        object = typedArray.getString(4);
        if (string2 != null && object != null) {
            intent.setComponent(new ComponentName(string2, (String)object));
        }
        typedArray.recycle();
        int n2 = xmlPullParser.getDepth();
        while ((n = xmlPullParser.next()) != 1 && (n != 3 || xmlPullParser.getDepth() > n2)) {
            if (n == 3 || n == 4) continue;
            object = xmlPullParser.getName();
            if (((String)object).equals(TAG_CATEGORIES)) {
                typedArray = resources.obtainAttributes(attributeSet, R.styleable.IntentCategory);
                object = typedArray.getString(0);
                typedArray.recycle();
                if (object != null) {
                    intent.addCategory((String)object);
                }
                XmlUtils.skipCurrentTag(xmlPullParser);
                continue;
            }
            if (((String)object).equals(TAG_EXTRA)) {
                if (intent.mExtras == null) {
                    intent.mExtras = new Bundle();
                }
                resources.parseBundleExtra(TAG_EXTRA, attributeSet, intent.mExtras);
                XmlUtils.skipCurrentTag(xmlPullParser);
                continue;
            }
            XmlUtils.skipCurrentTag(xmlPullParser);
        }
        return intent;
    }

    /*
     * Exception decompiling
     */
    public static Intent parseUri(String var0, int var1_1) throws URISyntaxException {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [8[UNCONDITIONALDOLOOP]], but top level block is 3[TRYBLOCK]
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.processEndingBlocks(Op04StructuredStatement.java:427)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:479)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:607)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:696)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:184)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:129)
        // org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:96)
        // org.benf.cfr.reader.entities.Method.analyse(Method.java:397)
        // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:906)
        // org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:797)
        // org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:225)
        // org.benf.cfr.reader.Driver.doJar(Driver.java:109)
        // org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:65)
        // org.benf.cfr.reader.Main.main(Main.java:48)
        throw new IllegalStateException("Decompilation failed");
    }

    @UnsupportedAppUsage
    public static void printIntentArgsHelp(PrintWriter printWriter, String string2) {
        for (String string3 : new String[]{"<INTENT> specifications include these flags and arguments:", "    [-a <ACTION>] [-d <DATA_URI>] [-t <MIME_TYPE>] [-i <IDENTIFIER>]", "    [-c <CATEGORY> [-c <CATEGORY>] ...]", "    [-n <COMPONENT_NAME>]", "    [-e|--es <EXTRA_KEY> <EXTRA_STRING_VALUE> ...]", "    [--esn <EXTRA_KEY> ...]", "    [--ez <EXTRA_KEY> <EXTRA_BOOLEAN_VALUE> ...]", "    [--ei <EXTRA_KEY> <EXTRA_INT_VALUE> ...]", "    [--el <EXTRA_KEY> <EXTRA_LONG_VALUE> ...]", "    [--ef <EXTRA_KEY> <EXTRA_FLOAT_VALUE> ...]", "    [--eu <EXTRA_KEY> <EXTRA_URI_VALUE> ...]", "    [--ecn <EXTRA_KEY> <EXTRA_COMPONENT_NAME_VALUE>]", "    [--eia <EXTRA_KEY> <EXTRA_INT_VALUE>[,<EXTRA_INT_VALUE...]]", "        (mutiple extras passed as Integer[])", "    [--eial <EXTRA_KEY> <EXTRA_INT_VALUE>[,<EXTRA_INT_VALUE...]]", "        (mutiple extras passed as List<Integer>)", "    [--ela <EXTRA_KEY> <EXTRA_LONG_VALUE>[,<EXTRA_LONG_VALUE...]]", "        (mutiple extras passed as Long[])", "    [--elal <EXTRA_KEY> <EXTRA_LONG_VALUE>[,<EXTRA_LONG_VALUE...]]", "        (mutiple extras passed as List<Long>)", "    [--efa <EXTRA_KEY> <EXTRA_FLOAT_VALUE>[,<EXTRA_FLOAT_VALUE...]]", "        (mutiple extras passed as Float[])", "    [--efal <EXTRA_KEY> <EXTRA_FLOAT_VALUE>[,<EXTRA_FLOAT_VALUE...]]", "        (mutiple extras passed as List<Float>)", "    [--esa <EXTRA_KEY> <EXTRA_STRING_VALUE>[,<EXTRA_STRING_VALUE...]]", "        (mutiple extras passed as String[]; to embed a comma into a string,", "         escape it using \"\\,\")", "    [--esal <EXTRA_KEY> <EXTRA_STRING_VALUE>[,<EXTRA_STRING_VALUE...]]", "        (mutiple extras passed as List<String>; to embed a comma into a string,", "         escape it using \"\\,\")", "    [-f <FLAG>]", "    [--grant-read-uri-permission] [--grant-write-uri-permission]", "    [--grant-persistable-uri-permission] [--grant-prefix-uri-permission]", "    [--debug-log-resolution] [--exclude-stopped-packages]", "    [--include-stopped-packages]", "    [--activity-brought-to-front] [--activity-clear-top]", "    [--activity-clear-when-task-reset] [--activity-exclude-from-recents]", "    [--activity-launched-from-history] [--activity-multiple-task]", "    [--activity-no-animation] [--activity-no-history]", "    [--activity-no-user-action] [--activity-previous-is-top]", "    [--activity-reorder-to-front] [--activity-reset-task-if-needed]", "    [--activity-single-top] [--activity-clear-task]", "    [--activity-task-on-home] [--activity-match-external]", "    [--receiver-registered-only] [--receiver-replace-pending]", "    [--receiver-foreground] [--receiver-no-abort]", "    [--receiver-include-background]", "    [--selector]", "    [<URI> | <PACKAGE> | <COMPONENT>]"}) {
            printWriter.print(string2);
            printWriter.println(string3);
        }
    }

    public static Intent restoreFromXml(XmlPullParser xmlPullParser) throws IOException, XmlPullParserException {
        CharSequence charSequence;
        CharSequence charSequence2;
        int n;
        Intent intent = new Intent();
        int n2 = xmlPullParser.getDepth();
        for (n = xmlPullParser.getAttributeCount() - 1; n >= 0; --n) {
            charSequence2 = xmlPullParser.getAttributeName(n);
            charSequence = xmlPullParser.getAttributeValue(n);
            if (ATTR_ACTION.equals(charSequence2)) {
                intent.setAction((String)charSequence);
                continue;
            }
            if (ATTR_DATA.equals(charSequence2)) {
                intent.setData(Uri.parse((String)charSequence));
                continue;
            }
            if (ATTR_TYPE.equals(charSequence2)) {
                intent.setType((String)charSequence);
                continue;
            }
            if (ATTR_IDENTIFIER.equals(charSequence2)) {
                intent.setIdentifier((String)charSequence);
                continue;
            }
            if (ATTR_COMPONENT.equals(charSequence2)) {
                intent.setComponent(ComponentName.unflattenFromString((String)charSequence));
                continue;
            }
            if (ATTR_FLAGS.equals(charSequence2)) {
                intent.setFlags(Integer.parseInt((String)charSequence, 16));
                continue;
            }
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("restoreFromXml: unknown attribute=");
            ((StringBuilder)charSequence).append((String)charSequence2);
            Log.e(TAG, ((StringBuilder)charSequence).toString());
        }
        while ((n = xmlPullParser.next()) != 1 && (n != 3 || xmlPullParser.getDepth() < n2)) {
            if (n != 2) continue;
            charSequence = xmlPullParser.getName();
            if (TAG_CATEGORIES.equals(charSequence)) {
                for (n = xmlPullParser.getAttributeCount() - 1; n >= 0; --n) {
                    intent.addCategory(xmlPullParser.getAttributeValue(n));
                }
                continue;
            }
            charSequence2 = new StringBuilder();
            ((StringBuilder)charSequence2).append("restoreFromXml: unknown name=");
            ((StringBuilder)charSequence2).append((String)charSequence);
            Log.w(TAG, ((StringBuilder)charSequence2).toString());
            XmlUtils.skipCurrentTag(xmlPullParser);
        }
        return intent;
    }

    private void toUriFragment(StringBuilder stringBuilder, String object, String object2, String string2, int n) {
        StringBuilder stringBuilder2 = new StringBuilder(128);
        this.toUriInner(stringBuilder2, (String)object, (String)object2, string2, n);
        if (this.mSelector != null) {
            stringBuilder2.append("SEL;");
            object2 = this.mSelector;
            object = ((Intent)object2).mData;
            object = object != null ? ((Uri)object).getScheme() : null;
            Intent.super.toUriInner(stringBuilder2, (String)object, null, null, n);
        }
        if (stringBuilder2.length() > 0) {
            stringBuilder.append("#Intent;");
            stringBuilder.append(stringBuilder2);
            stringBuilder.append("end");
        }
    }

    private void toUriInner(StringBuilder stringBuilder, String object4, String object2, String object3, int n) {
        Bundle bundle;
        Iterator<String> iterator;
        int n2;
        Object object;
        String string2;
        String string3;
        if (object4 != null) {
            stringBuilder.append("scheme=");
            stringBuilder.append((String)object4);
            stringBuilder.append(';');
        }
        if ((string3 = this.mAction) != null && !string3.equals(object)) {
            stringBuilder.append("action=");
            stringBuilder.append(Uri.encode(this.mAction));
            stringBuilder.append(';');
        }
        if (this.mCategories != null) {
            for (n2 = 0; n2 < this.mCategories.size(); ++n2) {
                stringBuilder.append("category=");
                stringBuilder.append(Uri.encode(this.mCategories.valueAt(n2)));
                stringBuilder.append(';');
            }
        }
        if (this.mType != null) {
            stringBuilder.append("type=");
            stringBuilder.append(Uri.encode(this.mType, "/"));
            stringBuilder.append(';');
        }
        if (this.mIdentifier != null) {
            stringBuilder.append("identifier=");
            stringBuilder.append(Uri.encode(this.mIdentifier, "/"));
            stringBuilder.append(';');
        }
        if (this.mFlags != 0) {
            stringBuilder.append("launchFlags=0x");
            stringBuilder.append(Integer.toHexString(this.mFlags));
            stringBuilder.append(';');
        }
        if ((string2 = this.mPackage) != null && !string2.equals(iterator)) {
            stringBuilder.append("package=");
            stringBuilder.append(Uri.encode(this.mPackage));
            stringBuilder.append(';');
        }
        if (this.mComponent != null) {
            stringBuilder.append("component=");
            stringBuilder.append(Uri.encode(this.mComponent.flattenToShortString(), "/"));
            stringBuilder.append(';');
        }
        if (this.mSourceBounds != null) {
            stringBuilder.append("sourceBounds=");
            stringBuilder.append(Uri.encode(this.mSourceBounds.flattenToString()));
            stringBuilder.append(';');
        }
        if ((bundle = this.mExtras) != null) {
            for (String string4 : bundle.keySet()) {
                object = this.mExtras.get(string4);
                int n3 = object instanceof String ? (n2 = 83) : (object instanceof Boolean ? (n2 = 66) : (object instanceof Byte ? (n2 = 98) : (object instanceof Character ? (n2 = 99) : (object instanceof Double ? (n2 = 100) : (object instanceof Float ? (n2 = 102) : (object instanceof Integer ? (n2 = 105) : (object instanceof Long ? (n2 = 108) : (object instanceof Short ? (n2 = 115) : (n2 = 0)))))))));
                if (n3 == 0) continue;
                stringBuilder.append((char)n3);
                stringBuilder.append('.');
                stringBuilder.append(Uri.encode(string4));
                stringBuilder.append('=');
                stringBuilder.append(Uri.encode(object.toString()));
                stringBuilder.append(';');
            }
        }
    }

    private void writeToProtoWithoutFieldId(ProtoOutputStream protoOutputStream, boolean bl, boolean bl2, boolean bl3, boolean bl4) {
        int n;
        Object object = this.mAction;
        if (object != null) {
            protoOutputStream.write(1138166333441L, (String)object);
        }
        if ((object = this.mCategories) != null) {
            object = ((ArraySet)object).iterator();
            while (object.hasNext()) {
                protoOutputStream.write(2237677961218L, (String)object.next());
            }
        }
        if ((object = this.mData) != null) {
            object = bl ? ((Uri)object).toSafeString() : ((Uri)object).toString();
            protoOutputStream.write(1138166333443L, (String)object);
        }
        if ((object = this.mType) != null) {
            protoOutputStream.write(1138166333444L, (String)object);
        }
        if ((object = this.mIdentifier) != null) {
            protoOutputStream.write(1138166333453L, (String)object);
        }
        if (this.mFlags != 0) {
            object = new StringBuilder();
            ((StringBuilder)object).append("0x");
            ((StringBuilder)object).append(Integer.toHexString(this.mFlags));
            protoOutputStream.write(1138166333445L, ((StringBuilder)object).toString());
        }
        if ((object = this.mPackage) != null) {
            protoOutputStream.write(1138166333446L, (String)object);
        }
        if (bl2 && (object = this.mComponent) != null) {
            ((ComponentName)object).writeToProto(protoOutputStream, 1146756268039L);
        }
        if ((object = this.mSourceBounds) != null) {
            protoOutputStream.write(1138166333448L, ((Rect)object).toShortString());
        }
        if (this.mClipData != null) {
            object = new StringBuilder();
            if (bl4) {
                this.mClipData.toShortString((StringBuilder)object);
            } else {
                this.mClipData.toShortStringShortItems((StringBuilder)object, false);
            }
            protoOutputStream.write(1138166333449L, ((StringBuilder)object).toString());
        }
        if (bl3 && (object = this.mExtras) != null) {
            protoOutputStream.write(1138166333450L, ((Bundle)object).toShortString());
        }
        if ((n = this.mContentUserHint) != 0) {
            protoOutputStream.write(1120986464267L, n);
        }
        if ((object = this.mSelector) != null) {
            protoOutputStream.write(1138166333452L, ((Intent)object).toShortString(bl, bl2, bl3, bl4));
        }
    }

    public Intent addCategory(String string2) {
        if (this.mCategories == null) {
            this.mCategories = new ArraySet();
        }
        this.mCategories.add(string2.intern());
        return this;
    }

    public Intent addFlags(int n) {
        this.mFlags |= n;
        return this;
    }

    public boolean canStripForHistory() {
        Bundle bundle = this.mExtras;
        boolean bl = bundle != null && bundle.isParcelled() || this.mClipData != null;
        return bl;
    }

    public Object clone() {
        return new Intent(this);
    }

    public Intent cloneFilter() {
        return new Intent(this, 1);
    }

    @Override
    public int describeContents() {
        Bundle bundle = this.mExtras;
        int n = bundle != null ? bundle.describeContents() : 0;
        return n;
    }

    public int fillIn(Intent intent, int n) {
        Object object;
        int n2;
        int n3;
        block62 : {
            int n4;
            block63 : {
                int n5;
                block60 : {
                    block61 : {
                        block58 : {
                            block59 : {
                                block56 : {
                                    block57 : {
                                        block54 : {
                                            block55 : {
                                                block52 : {
                                                    block53 : {
                                                        block51 : {
                                                            block49 : {
                                                                block50 : {
                                                                    n4 = 0;
                                                                    n5 = 0;
                                                                    n2 = n4;
                                                                    if (intent.mAction == null) break block49;
                                                                    if (this.mAction == null) break block50;
                                                                    n2 = n4;
                                                                    if ((n & 1) == 0) break block49;
                                                                }
                                                                this.mAction = intent.mAction;
                                                                n2 = false | true;
                                                            }
                                                            if (intent.mData != null) break block51;
                                                            n3 = n2;
                                                            n4 = n5;
                                                            if (intent.mType == null) break block52;
                                                        }
                                                        if (this.mData == null && this.mType == null) break block53;
                                                        n3 = n2;
                                                        n4 = n5;
                                                        if ((n & 2) == 0) break block52;
                                                    }
                                                    this.mData = intent.mData;
                                                    this.mType = intent.mType;
                                                    n3 = n2 | 2;
                                                    n4 = 1;
                                                }
                                                n2 = n3;
                                                if (intent.mIdentifier == null) break block54;
                                                if (this.mIdentifier == null) break block55;
                                                n2 = n3;
                                                if ((n & 256) == 0) break block54;
                                            }
                                            this.mIdentifier = intent.mIdentifier;
                                            n2 = n3 | 256;
                                        }
                                        n3 = n2;
                                        if (intent.mCategories == null) break block56;
                                        if (this.mCategories == null) break block57;
                                        n3 = n2;
                                        if ((n & 4) == 0) break block56;
                                    }
                                    if ((object = intent.mCategories) != null) {
                                        this.mCategories = new ArraySet(object);
                                    }
                                    n3 = n2 | 4;
                                }
                                n2 = n3;
                                if (intent.mPackage == null) break block58;
                                if (this.mPackage == null) break block59;
                                n2 = n3;
                                if ((n & 16) == 0) break block58;
                            }
                            n2 = n3;
                            if (this.mSelector == null) {
                                this.mPackage = intent.mPackage;
                                n2 = n3 | 16;
                            }
                        }
                        object = intent.mSelector;
                        n3 = n2;
                        if (object != null) {
                            n3 = n2;
                            if ((n & 64) != 0) {
                                n3 = n2;
                                if (this.mPackage == null) {
                                    this.mSelector = new Intent((Intent)object);
                                    this.mPackage = null;
                                    n3 = n2 | 64;
                                }
                            }
                        }
                        n5 = n3;
                        n2 = n4;
                        if (intent.mClipData == null) break block60;
                        if (this.mClipData == null) break block61;
                        n5 = n3;
                        n2 = n4;
                        if ((n & 128) == 0) break block60;
                    }
                    this.mClipData = intent.mClipData;
                    n5 = n3 | 128;
                    n2 = 1;
                }
                object = intent.mComponent;
                n4 = n5;
                if (object != null) {
                    n4 = n5;
                    if ((n & 8) != 0) {
                        this.mComponent = object;
                        n4 = n5 | 8;
                    }
                }
                this.mFlags |= intent.mFlags;
                n3 = n4;
                if (intent.mSourceBounds == null) break block62;
                if (this.mSourceBounds == null) break block63;
                n3 = n4;
                if ((n & 32) == 0) break block62;
            }
            this.mSourceBounds = new Rect(intent.mSourceBounds);
            n3 = n4 | 32;
        }
        if (this.mExtras == null) {
            object = intent.mExtras;
            n = n2;
            if (object != null) {
                this.mExtras = new Bundle((Bundle)object);
                n = 1;
            }
        } else {
            Bundle bundle = intent.mExtras;
            n = n2;
            if (bundle != null) {
                try {
                    object = new Bundle(bundle);
                    ((Bundle)object).putAll(this.mExtras);
                    this.mExtras = object;
                    n = 1;
                }
                catch (RuntimeException runtimeException) {
                    Log.w(TAG, "Failure filling in extras", runtimeException);
                    n = n2;
                }
            }
        }
        if (n != 0 && this.mContentUserHint == -2 && (n = intent.mContentUserHint) != -2) {
            this.mContentUserHint = n;
        }
        return n3;
    }

    public boolean filterEquals(Intent intent) {
        if (intent == null) {
            return false;
        }
        if (!Objects.equals(this.mAction, intent.mAction)) {
            return false;
        }
        if (!Objects.equals(this.mData, intent.mData)) {
            return false;
        }
        if (!Objects.equals(this.mType, intent.mType)) {
            return false;
        }
        if (!Objects.equals(this.mIdentifier, intent.mIdentifier)) {
            return false;
        }
        if (!Objects.equals(this.mPackage, intent.mPackage)) {
            return false;
        }
        if (!Objects.equals(this.mComponent, intent.mComponent)) {
            return false;
        }
        return Objects.equals(this.mCategories, intent.mCategories);
    }

    public int filterHashCode() {
        int n = 0;
        Object object = this.mAction;
        if (object != null) {
            n = 0 + ((String)object).hashCode();
        }
        object = this.mData;
        int n2 = n;
        if (object != null) {
            n2 = n + ((Uri)object).hashCode();
        }
        object = this.mType;
        n = n2;
        if (object != null) {
            n = n2 + ((String)object).hashCode();
        }
        object = this.mIdentifier;
        n2 = n;
        if (object != null) {
            n2 = n + ((String)object).hashCode();
        }
        object = this.mPackage;
        n = n2;
        if (object != null) {
            n = n2 + ((String)object).hashCode();
        }
        object = this.mComponent;
        n2 = n;
        if (object != null) {
            n2 = n + ((ComponentName)object).hashCode();
        }
        object = this.mCategories;
        n = n2;
        if (object != null) {
            n = n2 + ((ArraySet)object).hashCode();
        }
        return n;
    }

    public void fixUris(int n) {
        Object object = this.getData();
        if (object != null) {
            this.mData = ContentProvider.maybeAddUserId((Uri)object, n);
        }
        if ((object = this.mClipData) != null) {
            ((ClipData)object).fixUris(n);
        }
        if (ACTION_SEND.equals(object = this.getAction())) {
            object = (Uri)this.getParcelableExtra(EXTRA_STREAM);
            if (object != null) {
                this.putExtra(EXTRA_STREAM, ContentProvider.maybeAddUserId((Uri)object, n));
            }
        } else if (ACTION_SEND_MULTIPLE.equals(object)) {
            ArrayList<T> arrayList = this.getParcelableArrayListExtra(EXTRA_STREAM);
            if (arrayList != null) {
                object = new ArrayList<Uri>();
                for (int i = 0; i < arrayList.size(); ++i) {
                    ((ArrayList)object).add(ContentProvider.maybeAddUserId((Uri)arrayList.get(i), n));
                }
                this.putParcelableArrayListExtra(EXTRA_STREAM, (ArrayList<? extends Parcelable>)object);
            }
        } else if (("android.media.action.IMAGE_CAPTURE".equals(object) || "android.media.action.IMAGE_CAPTURE_SECURE".equals(object) || "android.media.action.VIDEO_CAPTURE".equals(object)) && (object = (Uri)this.getParcelableExtra("output")) != null) {
            this.putExtra("output", ContentProvider.maybeAddUserId(object, n));
        }
    }

    public String getAction() {
        return this.mAction;
    }

    public boolean[] getBooleanArrayExtra(String object) {
        Bundle bundle = this.mExtras;
        object = bundle == null ? null : bundle.getBooleanArray((String)object);
        return object;
    }

    public boolean getBooleanExtra(String string2, boolean bl) {
        Bundle bundle = this.mExtras;
        if (bundle != null) {
            bl = bundle.getBoolean(string2, bl);
        }
        return bl;
    }

    public Bundle getBundleExtra(String object) {
        Bundle bundle = this.mExtras;
        object = bundle == null ? null : bundle.getBundle((String)object);
        return object;
    }

    public byte[] getByteArrayExtra(String object) {
        Bundle bundle = this.mExtras;
        object = bundle == null ? null : bundle.getByteArray((String)object);
        return object;
    }

    public byte getByteExtra(String string2, byte by) {
        byte by2;
        byte by3;
        Bundle bundle = this.mExtras;
        by = bundle == null ? (by2 = by) : (by3 = bundle.getByte(string2, by).byteValue());
        return by;
    }

    public Set<String> getCategories() {
        return this.mCategories;
    }

    public char[] getCharArrayExtra(String object) {
        Bundle bundle = this.mExtras;
        object = bundle == null ? null : bundle.getCharArray((String)object);
        return object;
    }

    public char getCharExtra(String string2, char c) {
        char c2;
        char c3;
        Bundle bundle = this.mExtras;
        c = bundle == null ? (c3 = c) : (c2 = bundle.getChar(string2, c));
        return c;
    }

    public CharSequence[] getCharSequenceArrayExtra(String object) {
        Bundle bundle = this.mExtras;
        object = bundle == null ? null : bundle.getCharSequenceArray((String)object);
        return object;
    }

    public ArrayList<CharSequence> getCharSequenceArrayListExtra(String object) {
        Bundle bundle = this.mExtras;
        object = bundle == null ? null : bundle.getCharSequenceArrayList((String)object);
        return object;
    }

    public CharSequence getCharSequenceExtra(String charSequence) {
        Bundle bundle = this.mExtras;
        charSequence = bundle == null ? null : bundle.getCharSequence((String)charSequence);
        return charSequence;
    }

    public ClipData getClipData() {
        return this.mClipData;
    }

    public ComponentName getComponent() {
        return this.mComponent;
    }

    public int getContentUserHint() {
        return this.mContentUserHint;
    }

    public Uri getData() {
        return this.mData;
    }

    public String getDataString() {
        Object object = this.mData;
        object = object != null ? ((Uri)object).toString() : null;
        return object;
    }

    public double[] getDoubleArrayExtra(String object) {
        Bundle bundle = this.mExtras;
        object = bundle == null ? null : bundle.getDoubleArray((String)object);
        return object;
    }

    public double getDoubleExtra(String string2, double d) {
        Bundle bundle = this.mExtras;
        if (bundle != null) {
            d = bundle.getDouble(string2, d);
        }
        return d;
    }

    @Deprecated
    @UnsupportedAppUsage
    public Object getExtra(String string2) {
        return this.getExtra(string2, null);
    }

    @Deprecated
    @UnsupportedAppUsage
    public Object getExtra(String object, Object object2) {
        Object object3 = object2;
        Bundle bundle = this.mExtras;
        object2 = object3;
        if (bundle != null) {
            object = bundle.get((String)object);
            object2 = object3;
            if (object != null) {
                object2 = object;
            }
        }
        return object2;
    }

    public Bundle getExtras() {
        Bundle bundle = this.mExtras;
        bundle = bundle != null ? new Bundle(bundle) : null;
        return bundle;
    }

    public int getFlags() {
        return this.mFlags;
    }

    public float[] getFloatArrayExtra(String object) {
        Bundle bundle = this.mExtras;
        object = bundle == null ? null : bundle.getFloatArray((String)object);
        return object;
    }

    public float getFloatExtra(String string2, float f) {
        Bundle bundle = this.mExtras;
        if (bundle != null) {
            f = bundle.getFloat(string2, f);
        }
        return f;
    }

    @Deprecated
    @UnsupportedAppUsage
    public IBinder getIBinderExtra(String object) {
        Bundle bundle = this.mExtras;
        object = bundle == null ? null : bundle.getIBinder((String)object);
        return object;
    }

    public String getIdentifier() {
        return this.mIdentifier;
    }

    public int[] getIntArrayExtra(String object) {
        Bundle bundle = this.mExtras;
        object = bundle == null ? null : bundle.getIntArray((String)object);
        return object;
    }

    public int getIntExtra(String string2, int n) {
        Bundle bundle = this.mExtras;
        if (bundle != null) {
            n = bundle.getInt(string2, n);
        }
        return n;
    }

    public ArrayList<Integer> getIntegerArrayListExtra(String object) {
        Bundle bundle = this.mExtras;
        object = bundle == null ? null : bundle.getIntegerArrayList((String)object);
        return object;
    }

    public String getLaunchToken() {
        return this.mLaunchToken;
    }

    public long[] getLongArrayExtra(String object) {
        Bundle bundle = this.mExtras;
        object = bundle == null ? null : bundle.getLongArray((String)object);
        return object;
    }

    public long getLongExtra(String string2, long l) {
        Bundle bundle = this.mExtras;
        if (bundle != null) {
            l = bundle.getLong(string2, l);
        }
        return l;
    }

    public String getPackage() {
        return this.mPackage;
    }

    public Parcelable[] getParcelableArrayExtra(String object) {
        Bundle bundle = this.mExtras;
        object = bundle == null ? null : bundle.getParcelableArray((String)object);
        return object;
    }

    public <T extends Parcelable> ArrayList<T> getParcelableArrayListExtra(String object) {
        Bundle bundle = this.mExtras;
        object = bundle == null ? null : bundle.getParcelableArrayList((String)object);
        return object;
    }

    public <T extends Parcelable> T getParcelableExtra(String string2) {
        Bundle bundle = this.mExtras;
        string2 = bundle == null ? null : bundle.getParcelable(string2);
        return (T)string2;
    }

    public String getScheme() {
        Object object = this.mData;
        object = object != null ? ((Uri)object).getScheme() : null;
        return object;
    }

    public Intent getSelector() {
        return this.mSelector;
    }

    public Serializable getSerializableExtra(String object) {
        Bundle bundle = this.mExtras;
        object = bundle == null ? null : bundle.getSerializable((String)object);
        return object;
    }

    public short[] getShortArrayExtra(String object) {
        Bundle bundle = this.mExtras;
        object = bundle == null ? null : bundle.getShortArray((String)object);
        return object;
    }

    public short getShortExtra(String string2, short s) {
        short s2;
        short s3;
        Bundle bundle = this.mExtras;
        s = bundle == null ? (s2 = s) : (s3 = bundle.getShort(string2, s));
        return s;
    }

    public Rect getSourceBounds() {
        return this.mSourceBounds;
    }

    public String[] getStringArrayExtra(String object) {
        Bundle bundle = this.mExtras;
        object = bundle == null ? null : bundle.getStringArray((String)object);
        return object;
    }

    public ArrayList<String> getStringArrayListExtra(String object) {
        Bundle bundle = this.mExtras;
        object = bundle == null ? null : bundle.getStringArrayList((String)object);
        return object;
    }

    public String getStringExtra(String string2) {
        Bundle bundle = this.mExtras;
        string2 = bundle == null ? null : bundle.getString(string2);
        return string2;
    }

    public String getType() {
        return this.mType;
    }

    public boolean hasCategory(String string2) {
        ArraySet<String> arraySet = this.mCategories;
        boolean bl = arraySet != null && arraySet.contains(string2);
        return bl;
    }

    public boolean hasExtra(String string2) {
        Bundle bundle = this.mExtras;
        boolean bl = bundle != null && bundle.containsKey(string2);
        return bl;
    }

    public boolean hasFileDescriptors() {
        Bundle bundle = this.mExtras;
        boolean bl = bundle != null && bundle.hasFileDescriptors();
        return bl;
    }

    public boolean hasWebURI() {
        Object object = this.getData();
        boolean bl = false;
        if (object == null) {
            return false;
        }
        object = this.getScheme();
        if (TextUtils.isEmpty((CharSequence)object)) {
            return false;
        }
        if (((String)object).equals("http") || ((String)object).equals("https")) {
            bl = true;
        }
        return bl;
    }

    public boolean isDocument() {
        boolean bl = (this.mFlags & 524288) == 524288;
        return bl;
    }

    @UnsupportedAppUsage
    public boolean isExcludingStopped() {
        boolean bl = (this.mFlags & 48) == 16;
        return bl;
    }

    public boolean isWebIntent() {
        boolean bl = "android.intent.action.VIEW".equals(this.mAction) && this.hasWebURI();
        return bl;
    }

    public Intent maybeStripForHistory() {
        if (!this.canStripForHistory()) {
            return this;
        }
        return new Intent(this, 2);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public boolean migrateExtraStreamToClipData() {
        Object object = this.mExtras;
        int n = 0;
        if (object != null && ((BaseBundle)object).isParcelled()) {
            return false;
        }
        if (this.getClipData() != null) {
            return false;
        }
        object = this.getAction();
        if (ACTION_CHOOSER.equals(object)) {
            boolean bl;
            boolean bl2;
            Parcelable[] arrparcelable;
            boolean bl3;
            block35 : {
                bl2 = false;
                bl3 = false;
                object = (Intent)this.getParcelableExtra(EXTRA_INTENT);
                if (object == null) break block35;
                try {
                    bl3 = ((Intent)object).migrateExtraStreamToClipData();
                    bl3 = false | bl3;
                }
                catch (ClassCastException classCastException) {
                    bl3 = bl2;
                }
            }
            bl2 = bl3;
            try {
                arrparcelable = this.getParcelableArrayExtra(EXTRA_INITIAL_INTENTS);
                bl = bl3;
                if (arrparcelable == null) return bl;
            }
            catch (ClassCastException classCastException) {
                // empty catch block
            }
            do {
                block36 : {
                    bl = bl3;
                    bl2 = bl3;
                    if (n >= arrparcelable.length) return bl;
                    bl2 = bl3;
                    object = (Intent)arrparcelable[n];
                    bl2 = bl3;
                    if (object == null) break block36;
                    bl2 = bl3;
                    bl = ((Intent)object).migrateExtraStreamToClipData();
                    bl2 = bl3 | bl;
                }
                ++n;
                bl3 = bl2;
            } while (true);
            return bl2;
        }
        if (ACTION_SEND.equals(object)) {
            Uri uri;
            String string2;
            block37 : {
                try {
                    uri = (Uri)this.getParcelableExtra(EXTRA_STREAM);
                    object = this.getCharSequenceExtra(EXTRA_TEXT);
                    string2 = this.getStringExtra(EXTRA_HTML_TEXT);
                    if (uri != null || object != null) break block37;
                    if (string2 == null) return false;
                }
                catch (ClassCastException classCastException) {
                    return false;
                }
            }
            String string3 = this.getType();
            ClipData.Item item = new ClipData.Item((CharSequence)object, string2, null, uri);
            ClipData clipData = new ClipData(null, new String[]{string3}, item);
            this.setClipData(clipData);
            this.addFlags(1);
            return true;
        }
        if (ACTION_SEND_MULTIPLE.equals(object)) {
            ArrayList<String> arrayList;
            ArrayList<Uri> arrayList2;
            int n2;
            block38 : {
                arrayList2 = this.getParcelableArrayListExtra(EXTRA_STREAM);
                object = this.getCharSequenceArrayListExtra(EXTRA_TEXT);
                arrayList = this.getStringArrayListExtra(EXTRA_HTML_TEXT);
                n2 = -1;
                if (arrayList2 == null) break block38;
                n2 = arrayList2.size();
            }
            n = n2;
            if (object != null) {
                block39 : {
                    if (n2 >= 0) {
                        if (n2 == ((ArrayList)object).size()) break block39;
                        return false;
                    }
                }
                n = ((ArrayList)object).size();
            }
            n2 = n;
            if (arrayList != null) {
                block40 : {
                    if (n >= 0) {
                        if (n == arrayList.size()) break block40;
                        return false;
                    }
                }
                n2 = arrayList.size();
            }
            if (n2 <= 0) return false;
            String string4 = this.getType();
            ClipData.Item item = Intent.makeClipItem(arrayList2, (ArrayList<CharSequence>)object, arrayList, 0);
            ClipData clipData = new ClipData(null, new String[]{string4}, item);
            for (n = 1; n < n2; ++n) {
                clipData.addItem(Intent.makeClipItem(arrayList2, (ArrayList<CharSequence>)object, arrayList, n));
                continue;
            }
            try {
                this.setClipData(clipData);
                this.addFlags(1);
                return true;
            }
            catch (ClassCastException classCastException) {
                return false;
            }
        }
        if (!"android.media.action.IMAGE_CAPTURE".equals(object) && !"android.media.action.IMAGE_CAPTURE_SECURE".equals(object)) {
            if (!"android.media.action.VIDEO_CAPTURE".equals(object)) return false;
        }
        try {
            object = (Uri)this.getParcelableExtra("output");
            if (object == null) return false;
        }
        catch (ClassCastException classCastException) {
            return false;
        }
        this.setClipData(ClipData.newRawUri("", (Uri)object));
        this.addFlags(3);
        return true;
    }

    public void prepareToEnterProcess() {
        this.setDefusable(true);
        Parcelable parcelable = this.mSelector;
        if (parcelable != null) {
            ((Intent)parcelable).prepareToEnterProcess();
        }
        if ((parcelable = this.mClipData) != null) {
            ((ClipData)parcelable).prepareToEnterProcess();
        }
        if (this.mContentUserHint != -2 && UserHandle.getAppId(Process.myUid()) != 1000) {
            this.fixUris(this.mContentUserHint);
            this.mContentUserHint = -2;
        }
    }

    @UnsupportedAppUsage
    public void prepareToLeaveProcess(Context context) {
        ComponentName componentName = this.mComponent;
        boolean bl = componentName == null || !Objects.equals(componentName.getPackageName(), context.getPackageName());
        this.prepareToLeaveProcess(bl);
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    public void prepareToLeaveProcess(boolean var1_1) {
        block29 : {
            block31 : {
                block30 : {
                    block27 : {
                        block28 : {
                            var2_2 = 0;
                            this.setAllowFds(false);
                            var3_3 = this.mSelector;
                            if (var3_3 != null) {
                                var3_3.prepareToLeaveProcess(var1_1);
                            }
                            if ((var3_3 = this.mClipData) != null) {
                                var3_3.prepareToLeaveProcess(var1_1, this.getFlags());
                            }
                            if ((var3_3 = this.mExtras) != null && !var3_3.isParcelled() && (var3_3 = this.mExtras.get("android.intent.extra.INTENT")) instanceof Intent) {
                                ((Intent)var3_3).prepareToLeaveProcess(var1_1);
                            }
                            if (this.mAction == null || this.mData == null || !StrictMode.vmFileUriExposureEnabled() || !var1_1) break block27;
                            var3_3 = this.mAction;
                            switch (var3_3.hashCode()) {
                                case 2045140818: {
                                    if (!var3_3.equals("android.intent.action.MEDIA_BAD_REMOVAL")) break;
                                    var4_4 = 7;
                                    break block28;
                                }
                                case 1964681210: {
                                    if (!var3_3.equals("android.intent.action.MEDIA_CHECKING")) break;
                                    var4_4 = 2;
                                    break block28;
                                }
                                case 1920444806: {
                                    if (!var3_3.equals("android.intent.action.PACKAGE_VERIFIED")) break;
                                    var4_4 = 14;
                                    break block28;
                                }
                                case 1599438242: {
                                    if (!var3_3.equals("android.intent.action.PACKAGE_ENABLE_ROLLBACK")) break;
                                    var4_4 = 15;
                                    break block28;
                                }
                                case 1431947322: {
                                    if (!var3_3.equals("android.intent.action.MEDIA_UNMOUNTABLE")) break;
                                    var4_4 = 8;
                                    break block28;
                                }
                                case 1412829408: {
                                    if (!var3_3.equals("android.intent.action.MEDIA_SCANNER_STARTED")) break;
                                    var4_4 = 10;
                                    break block28;
                                }
                                case 852070077: {
                                    if (!var3_3.equals("android.intent.action.MEDIA_SCANNER_SCAN_FILE")) break;
                                    var4_4 = 12;
                                    break block28;
                                }
                                case 582421979: {
                                    if (!var3_3.equals("android.intent.action.PACKAGE_NEEDS_VERIFICATION")) break;
                                    var4_4 = 13;
                                    break block28;
                                }
                                case 410719838: {
                                    if (!var3_3.equals("android.intent.action.MEDIA_UNSHARED")) break;
                                    var4_4 = 6;
                                    break block28;
                                }
                                case 257177710: {
                                    if (!var3_3.equals("android.intent.action.MEDIA_NOFS")) break;
                                    var4_4 = 3;
                                    break block28;
                                }
                                case -625887599: {
                                    if (!var3_3.equals("android.intent.action.MEDIA_EJECT")) break;
                                    var4_4 = 9;
                                    break block28;
                                }
                                case -963871873: {
                                    if (!var3_3.equals("android.intent.action.MEDIA_UNMOUNTED")) break;
                                    var4_4 = 1;
                                    break block28;
                                }
                                case -1142424621: {
                                    if (!var3_3.equals("android.intent.action.MEDIA_SCANNER_FINISHED")) break;
                                    var4_4 = 11;
                                    break block28;
                                }
                                case -1514214344: {
                                    if (!var3_3.equals("android.intent.action.MEDIA_MOUNTED")) break;
                                    var4_4 = 4;
                                    break block28;
                                }
                                case -1665311200: {
                                    if (!var3_3.equals("android.intent.action.MEDIA_REMOVED")) break;
                                    var4_4 = 0;
                                    break block28;
                                }
                                case -1823790459: {
                                    if (!var3_3.equals("android.intent.action.MEDIA_SHARED")) break;
                                    var4_4 = 5;
                                    break block28;
                                }
                            }
                            ** break;
lbl78: // 1 sources:
                            var4_4 = -1;
                        }
                        switch (var4_4) {
                            default: {
                                this.mData.checkFileUriExposed("Intent.getData()");
                            }
                            case 0: 
                            case 1: 
                            case 2: 
                            case 3: 
                            case 4: 
                            case 5: 
                            case 6: 
                            case 7: 
                            case 8: 
                            case 9: 
                            case 10: 
                            case 11: 
                            case 12: 
                            case 13: 
                            case 14: 
                            case 15: 
                        }
                    }
                    if (this.mAction == null || this.mData == null || !StrictMode.vmContentUriWithoutPermissionEnabled() || !var1_1) break block29;
                    var3_3 = this.mAction;
                    var4_4 = var3_3.hashCode();
                    if (var4_4 == -577088908) break block30;
                    if (var4_4 != 1662413067 || !var3_3.equals("android.intent.action.PROVIDER_CHANGED")) ** GOTO lbl-1000
                    var4_4 = var2_2;
                    break block31;
                }
                if (var3_3.equals("android.provider.action.QUICK_CONTACT")) {
                    var4_4 = 1;
                } else lbl-1000: // 2 sources:
                {
                    var4_4 = -1;
                }
            }
            if (var4_4 != 0 && var4_4 != 1) {
                this.mData.checkContentUriWithoutPermission("Intent.getData()", this.getFlags());
            }
        }
        if ("android.intent.action.MEDIA_SCANNER_SCAN_FILE".equals(this.mAction) == false) return;
        var3_3 = this.mData;
        if (var3_3 == null) return;
        if ("file".equals(var3_3.getScheme()) == false) return;
        if (var1_1 == false) return;
        var5_5 = AppGlobals.getInitialApplication().getSystemService(StorageManager.class);
        var3_3 = new File(this.mData.getPath());
        if (Objects.equals(var3_3, var6_6 = var5_5.translateAppToSystem((File)var3_3, Process.myPid(), Process.myUid())) != false) return;
        var5_5 = new StringBuilder();
        var5_5.append("Translated ");
        var5_5.append(var3_3);
        var5_5.append(" to ");
        var5_5.append(var6_6);
        Log.v("Intent", var5_5.toString());
        this.mData = Uri.fromFile(var6_6);
    }

    public void prepareToLeaveUser(int n) {
        if (this.mContentUserHint == -2) {
            this.mContentUserHint = n;
        }
    }

    public Intent putCharSequenceArrayListExtra(String string2, ArrayList<CharSequence> arrayList) {
        if (this.mExtras == null) {
            this.mExtras = new Bundle();
        }
        this.mExtras.putCharSequenceArrayList(string2, arrayList);
        return this;
    }

    public Intent putExtra(String string2, byte by) {
        if (this.mExtras == null) {
            this.mExtras = new Bundle();
        }
        this.mExtras.putByte(string2, by);
        return this;
    }

    public Intent putExtra(String string2, char c) {
        if (this.mExtras == null) {
            this.mExtras = new Bundle();
        }
        this.mExtras.putChar(string2, c);
        return this;
    }

    public Intent putExtra(String string2, double d) {
        if (this.mExtras == null) {
            this.mExtras = new Bundle();
        }
        this.mExtras.putDouble(string2, d);
        return this;
    }

    public Intent putExtra(String string2, float f) {
        if (this.mExtras == null) {
            this.mExtras = new Bundle();
        }
        this.mExtras.putFloat(string2, f);
        return this;
    }

    public Intent putExtra(String string2, int n) {
        if (this.mExtras == null) {
            this.mExtras = new Bundle();
        }
        this.mExtras.putInt(string2, n);
        return this;
    }

    public Intent putExtra(String string2, long l) {
        if (this.mExtras == null) {
            this.mExtras = new Bundle();
        }
        this.mExtras.putLong(string2, l);
        return this;
    }

    public Intent putExtra(String string2, Bundle bundle) {
        if (this.mExtras == null) {
            this.mExtras = new Bundle();
        }
        this.mExtras.putBundle(string2, bundle);
        return this;
    }

    @Deprecated
    @UnsupportedAppUsage
    public Intent putExtra(String string2, IBinder iBinder) {
        if (this.mExtras == null) {
            this.mExtras = new Bundle();
        }
        this.mExtras.putIBinder(string2, iBinder);
        return this;
    }

    public Intent putExtra(String string2, Parcelable parcelable) {
        if (this.mExtras == null) {
            this.mExtras = new Bundle();
        }
        this.mExtras.putParcelable(string2, parcelable);
        return this;
    }

    public Intent putExtra(String string2, Serializable serializable) {
        if (this.mExtras == null) {
            this.mExtras = new Bundle();
        }
        this.mExtras.putSerializable(string2, serializable);
        return this;
    }

    public Intent putExtra(String string2, CharSequence charSequence) {
        if (this.mExtras == null) {
            this.mExtras = new Bundle();
        }
        this.mExtras.putCharSequence(string2, charSequence);
        return this;
    }

    public Intent putExtra(String string2, String string3) {
        if (this.mExtras == null) {
            this.mExtras = new Bundle();
        }
        this.mExtras.putString(string2, string3);
        return this;
    }

    public Intent putExtra(String string2, short s) {
        if (this.mExtras == null) {
            this.mExtras = new Bundle();
        }
        this.mExtras.putShort(string2, s);
        return this;
    }

    public Intent putExtra(String string2, boolean bl) {
        if (this.mExtras == null) {
            this.mExtras = new Bundle();
        }
        this.mExtras.putBoolean(string2, bl);
        return this;
    }

    public Intent putExtra(String string2, byte[] arrby) {
        if (this.mExtras == null) {
            this.mExtras = new Bundle();
        }
        this.mExtras.putByteArray(string2, arrby);
        return this;
    }

    public Intent putExtra(String string2, char[] arrc) {
        if (this.mExtras == null) {
            this.mExtras = new Bundle();
        }
        this.mExtras.putCharArray(string2, arrc);
        return this;
    }

    public Intent putExtra(String string2, double[] arrd) {
        if (this.mExtras == null) {
            this.mExtras = new Bundle();
        }
        this.mExtras.putDoubleArray(string2, arrd);
        return this;
    }

    public Intent putExtra(String string2, float[] arrf) {
        if (this.mExtras == null) {
            this.mExtras = new Bundle();
        }
        this.mExtras.putFloatArray(string2, arrf);
        return this;
    }

    public Intent putExtra(String string2, int[] arrn) {
        if (this.mExtras == null) {
            this.mExtras = new Bundle();
        }
        this.mExtras.putIntArray(string2, arrn);
        return this;
    }

    public Intent putExtra(String string2, long[] arrl) {
        if (this.mExtras == null) {
            this.mExtras = new Bundle();
        }
        this.mExtras.putLongArray(string2, arrl);
        return this;
    }

    public Intent putExtra(String string2, Parcelable[] arrparcelable) {
        if (this.mExtras == null) {
            this.mExtras = new Bundle();
        }
        this.mExtras.putParcelableArray(string2, arrparcelable);
        return this;
    }

    public Intent putExtra(String string2, CharSequence[] arrcharSequence) {
        if (this.mExtras == null) {
            this.mExtras = new Bundle();
        }
        this.mExtras.putCharSequenceArray(string2, arrcharSequence);
        return this;
    }

    public Intent putExtra(String string2, String[] arrstring) {
        if (this.mExtras == null) {
            this.mExtras = new Bundle();
        }
        this.mExtras.putStringArray(string2, arrstring);
        return this;
    }

    public Intent putExtra(String string2, short[] arrs) {
        if (this.mExtras == null) {
            this.mExtras = new Bundle();
        }
        this.mExtras.putShortArray(string2, arrs);
        return this;
    }

    public Intent putExtra(String string2, boolean[] arrbl) {
        if (this.mExtras == null) {
            this.mExtras = new Bundle();
        }
        this.mExtras.putBooleanArray(string2, arrbl);
        return this;
    }

    public Intent putExtras(Intent parcelable) {
        parcelable = parcelable.mExtras;
        if (parcelable != null) {
            Bundle bundle = this.mExtras;
            if (bundle == null) {
                this.mExtras = new Bundle((Bundle)parcelable);
            } else {
                bundle.putAll((Bundle)parcelable);
            }
        }
        return this;
    }

    public Intent putExtras(Bundle bundle) {
        if (this.mExtras == null) {
            this.mExtras = new Bundle();
        }
        this.mExtras.putAll(bundle);
        return this;
    }

    public Intent putIntegerArrayListExtra(String string2, ArrayList<Integer> arrayList) {
        if (this.mExtras == null) {
            this.mExtras = new Bundle();
        }
        this.mExtras.putIntegerArrayList(string2, arrayList);
        return this;
    }

    public Intent putParcelableArrayListExtra(String string2, ArrayList<? extends Parcelable> arrayList) {
        if (this.mExtras == null) {
            this.mExtras = new Bundle();
        }
        this.mExtras.putParcelableArrayList(string2, arrayList);
        return this;
    }

    public Intent putStringArrayListExtra(String string2, ArrayList<String> arrayList) {
        if (this.mExtras == null) {
            this.mExtras = new Bundle();
        }
        this.mExtras.putStringArrayList(string2, arrayList);
        return this;
    }

    public void readFromParcel(Parcel parcel) {
        int n;
        this.setAction(parcel.readString());
        this.mData = Uri.CREATOR.createFromParcel(parcel);
        this.mType = parcel.readString();
        this.mIdentifier = parcel.readString();
        this.mFlags = parcel.readInt();
        this.mPackage = parcel.readString();
        this.mComponent = ComponentName.readFromParcel(parcel);
        if (parcel.readInt() != 0) {
            this.mSourceBounds = Rect.CREATOR.createFromParcel(parcel);
        }
        if ((n = parcel.readInt()) > 0) {
            this.mCategories = new ArraySet();
            for (int i = 0; i < n; ++i) {
                this.mCategories.add(parcel.readString().intern());
            }
        } else {
            this.mCategories = null;
        }
        if (parcel.readInt() != 0) {
            this.mSelector = new Intent(parcel);
        }
        if (parcel.readInt() != 0) {
            this.mClipData = new ClipData(parcel);
        }
        this.mContentUserHint = parcel.readInt();
        this.mExtras = parcel.readBundle();
    }

    public void removeCategory(String string2) {
        ArraySet<String> arraySet = this.mCategories;
        if (arraySet != null) {
            arraySet.remove(string2);
            if (this.mCategories.size() == 0) {
                this.mCategories = null;
            }
        }
    }

    public void removeExtra(String string2) {
        Bundle bundle = this.mExtras;
        if (bundle != null) {
            bundle.remove(string2);
            if (this.mExtras.size() == 0) {
                this.mExtras = null;
            }
        }
    }

    public void removeFlags(int n) {
        this.mFlags &= n;
    }

    public void removeUnsafeExtras() {
        Bundle bundle = this.mExtras;
        if (bundle != null) {
            this.mExtras = bundle.filterValues();
        }
    }

    public Intent replaceExtras(Intent parcelable) {
        parcelable = parcelable.mExtras;
        parcelable = parcelable != null ? new Bundle((Bundle)parcelable) : null;
        this.mExtras = parcelable;
        return this;
    }

    public Intent replaceExtras(Bundle bundle) {
        bundle = bundle != null ? new Bundle(bundle) : null;
        this.mExtras = bundle;
        return this;
    }

    public ComponentName resolveActivity(PackageManager object) {
        ComponentName componentName = this.mComponent;
        if (componentName != null) {
            return componentName;
        }
        if ((object = ((PackageManager)object).resolveActivity(this, 65536)) != null) {
            return new ComponentName(object.activityInfo.applicationInfo.packageName, object.activityInfo.name);
        }
        return null;
    }

    public ActivityInfo resolveActivityInfo(PackageManager object, int n) {
        Object var3_4 = null;
        ResolveInfo resolveInfo = null;
        ComponentName componentName = this.mComponent;
        if (componentName != null) {
            try {
                object = ((PackageManager)object).getActivityInfo(componentName, n);
            }
            catch (PackageManager.NameNotFoundException nameNotFoundException) {
                object = resolveInfo;
            }
        } else {
            resolveInfo = ((PackageManager)object).resolveActivity(this, 65536 | n);
            object = var3_4;
            if (resolveInfo != null) {
                object = resolveInfo.activityInfo;
            }
        }
        return object;
    }

    @UnsupportedAppUsage
    public ComponentName resolveSystemService(PackageManager object, int n) {
        Parcelable parcelable = this.mComponent;
        if (parcelable != null) {
            return parcelable;
        }
        List<ResolveInfo> list = ((PackageManager)object).queryIntentServices(this, n);
        if (list == null) {
            return null;
        }
        object = null;
        for (n = 0; n < list.size(); ++n) {
            parcelable = list.get(n);
            if ((parcelable.serviceInfo.applicationInfo.flags & 1) == 0) continue;
            parcelable = new ComponentName(parcelable.serviceInfo.applicationInfo.packageName, parcelable.serviceInfo.name);
            if (object == null) {
                object = parcelable;
                continue;
            }
            list = new StringBuilder();
            ((StringBuilder)((Object)list)).append("Multiple system services handle ");
            ((StringBuilder)((Object)list)).append(this);
            ((StringBuilder)((Object)list)).append(": ");
            ((StringBuilder)((Object)list)).append(object);
            ((StringBuilder)((Object)list)).append(", ");
            ((StringBuilder)((Object)list)).append(parcelable);
            throw new IllegalStateException(((StringBuilder)((Object)list)).toString());
        }
        return object;
    }

    public String resolveType(ContentResolver contentResolver) {
        Object object = this.mType;
        if (object != null) {
            return object;
        }
        object = this.mData;
        if (object != null && "content".equals(((Uri)object).getScheme())) {
            return contentResolver.getType(this.mData);
        }
        return null;
    }

    public String resolveType(Context context) {
        return this.resolveType(context.getContentResolver());
    }

    public String resolveTypeIfNeeded(ContentResolver contentResolver) {
        if (this.mComponent != null) {
            return this.mType;
        }
        return this.resolveType(contentResolver);
    }

    public void saveToXml(XmlSerializer xmlSerializer) throws IOException {
        Object object = this.mAction;
        if (object != null) {
            xmlSerializer.attribute(null, ATTR_ACTION, (String)object);
        }
        if ((object = this.mData) != null) {
            xmlSerializer.attribute(null, ATTR_DATA, ((Uri)object).toString());
        }
        if ((object = this.mType) != null) {
            xmlSerializer.attribute(null, ATTR_TYPE, (String)object);
        }
        if ((object = this.mIdentifier) != null) {
            xmlSerializer.attribute(null, ATTR_IDENTIFIER, (String)object);
        }
        if ((object = this.mComponent) != null) {
            xmlSerializer.attribute(null, ATTR_COMPONENT, ((ComponentName)object).flattenToShortString());
        }
        xmlSerializer.attribute(null, ATTR_FLAGS, Integer.toHexString(this.getFlags()));
        if (this.mCategories != null) {
            xmlSerializer.startTag(null, TAG_CATEGORIES);
            for (int i = this.mCategories.size() - 1; i >= 0; --i) {
                xmlSerializer.attribute(null, ATTR_CATEGORY, this.mCategories.valueAt(i));
            }
            xmlSerializer.endTag(null, TAG_CATEGORIES);
        }
    }

    public Intent setAction(String string2) {
        string2 = string2 != null ? string2.intern() : null;
        this.mAction = string2;
        return this;
    }

    @UnsupportedAppUsage
    public void setAllowFds(boolean bl) {
        Bundle bundle = this.mExtras;
        if (bundle != null) {
            bundle.setAllowFds(bl);
        }
    }

    public Intent setClass(Context context, Class<?> class_) {
        this.mComponent = new ComponentName(context, class_);
        return this;
    }

    public Intent setClassName(Context context, String string2) {
        this.mComponent = new ComponentName(context, string2);
        return this;
    }

    public Intent setClassName(String string2, String string3) {
        this.mComponent = new ComponentName(string2, string3);
        return this;
    }

    public void setClipData(ClipData clipData) {
        this.mClipData = clipData;
    }

    public Intent setComponent(ComponentName componentName) {
        this.mComponent = componentName;
        return this;
    }

    public Intent setData(Uri uri) {
        this.mData = uri;
        this.mType = null;
        return this;
    }

    public Intent setDataAndNormalize(Uri uri) {
        return this.setData(uri.normalizeScheme());
    }

    public Intent setDataAndType(Uri uri, String string2) {
        this.mData = uri;
        this.mType = string2;
        return this;
    }

    public Intent setDataAndTypeAndNormalize(Uri uri, String string2) {
        return this.setDataAndType(uri.normalizeScheme(), Intent.normalizeMimeType(string2));
    }

    public void setDefusable(boolean bl) {
        Bundle bundle = this.mExtras;
        if (bundle != null) {
            bundle.setDefusable(bl);
        }
    }

    public void setExtrasClassLoader(ClassLoader classLoader) {
        Bundle bundle = this.mExtras;
        if (bundle != null) {
            bundle.setClassLoader(classLoader);
        }
    }

    public Intent setFlags(int n) {
        this.mFlags = n;
        return this;
    }

    public Intent setIdentifier(String string2) {
        this.mIdentifier = string2;
        return this;
    }

    public void setLaunchToken(String string2) {
        this.mLaunchToken = string2;
    }

    public Intent setPackage(String string2) {
        if (string2 != null && this.mSelector != null) {
            throw new IllegalArgumentException("Can't set package name when selector is already set");
        }
        this.mPackage = string2;
        return this;
    }

    public void setSelector(Intent intent) {
        if (intent != this) {
            if (intent != null && this.mPackage != null) {
                throw new IllegalArgumentException("Can't set selector when package name is already set");
            }
            this.mSelector = intent;
            return;
        }
        throw new IllegalArgumentException("Intent being set as a selector of itself");
    }

    public void setSourceBounds(Rect rect) {
        this.mSourceBounds = rect != null ? new Rect(rect) : null;
    }

    public Intent setType(String string2) {
        this.mData = null;
        this.mType = string2;
        return this;
    }

    public Intent setTypeAndNormalize(String string2) {
        return this.setType(Intent.normalizeMimeType(string2));
    }

    @UnsupportedAppUsage
    public String toInsecureString() {
        StringBuilder stringBuilder = new StringBuilder(128);
        stringBuilder.append("Intent { ");
        this.toShortString(stringBuilder, false, true, true, false);
        stringBuilder.append(" }");
        return stringBuilder.toString();
    }

    public String toInsecureStringWithClip() {
        StringBuilder stringBuilder = new StringBuilder(128);
        stringBuilder.append("Intent { ");
        this.toShortString(stringBuilder, false, true, true, true);
        stringBuilder.append(" }");
        return stringBuilder.toString();
    }

    public String toShortString(boolean bl, boolean bl2, boolean bl3, boolean bl4) {
        StringBuilder stringBuilder = new StringBuilder(128);
        this.toShortString(stringBuilder, bl, bl2, bl3, bl4);
        return stringBuilder.toString();
    }

    public void toShortString(StringBuilder stringBuilder, boolean bl, boolean bl2, boolean bl3, boolean bl4) {
        int n = 1;
        if (this.mAction != null) {
            stringBuilder.append("act=");
            stringBuilder.append(this.mAction);
            n = 0;
        }
        int n2 = n;
        if (this.mCategories != null) {
            if (n == 0) {
                stringBuilder.append(' ');
            }
            n = 0;
            stringBuilder.append("cat=[");
            for (n2 = 0; n2 < this.mCategories.size(); ++n2) {
                if (n2 > 0) {
                    stringBuilder.append(',');
                }
                stringBuilder.append(this.mCategories.valueAt(n2));
            }
            stringBuilder.append("]");
            n2 = n;
        }
        n = n2;
        if (this.mData != null) {
            if (n2 == 0) {
                stringBuilder.append(' ');
            }
            n = 0;
            stringBuilder.append("dat=");
            if (bl) {
                stringBuilder.append(this.mData.toSafeString());
            } else {
                stringBuilder.append(this.mData);
            }
        }
        n2 = n;
        if (this.mType != null) {
            if (n == 0) {
                stringBuilder.append(' ');
            }
            n2 = 0;
            stringBuilder.append("typ=");
            stringBuilder.append(this.mType);
        }
        n = n2;
        if (this.mIdentifier != null) {
            if (n2 == 0) {
                stringBuilder.append(' ');
            }
            n = 0;
            stringBuilder.append("id=");
            stringBuilder.append(this.mIdentifier);
        }
        n2 = n;
        if (this.mFlags != 0) {
            if (n == 0) {
                stringBuilder.append(' ');
            }
            n2 = 0;
            stringBuilder.append("flg=0x");
            stringBuilder.append(Integer.toHexString(this.mFlags));
        }
        n = n2;
        if (this.mPackage != null) {
            if (n2 == 0) {
                stringBuilder.append(' ');
            }
            n = 0;
            stringBuilder.append("pkg=");
            stringBuilder.append(this.mPackage);
        }
        int n3 = n;
        if (bl2) {
            n3 = n;
            if (this.mComponent != null) {
                if (n == 0) {
                    stringBuilder.append(' ');
                }
                n3 = 0;
                stringBuilder.append("cmp=");
                stringBuilder.append(this.mComponent.flattenToShortString());
            }
        }
        n2 = n3;
        if (this.mSourceBounds != null) {
            if (n3 == 0) {
                stringBuilder.append(' ');
            }
            n2 = 0;
            stringBuilder.append("bnds=");
            stringBuilder.append(this.mSourceBounds.toShortString());
        }
        n = n2;
        if (this.mClipData != null) {
            if (n2 == 0) {
                stringBuilder.append(' ');
            }
            stringBuilder.append("clip={");
            if (bl4) {
                this.mClipData.toShortString(stringBuilder);
            } else {
                boolean bl5 = this.mClipData.getDescription() != null ? this.mClipData.getDescription().toShortStringTypesOnly(stringBuilder) ^ true : true;
                this.mClipData.toShortStringShortItems(stringBuilder, bl5);
            }
            n = 0;
            stringBuilder.append('}');
        }
        n2 = n;
        if (bl3) {
            n2 = n;
            if (this.mExtras != null) {
                if (n == 0) {
                    stringBuilder.append(' ');
                }
                n2 = 0;
                stringBuilder.append("(has extras)");
            }
        }
        if (this.mContentUserHint != -2) {
            if (n2 == 0) {
                stringBuilder.append(' ');
            }
            stringBuilder.append("u=");
            stringBuilder.append(this.mContentUserHint);
        }
        if (this.mSelector != null) {
            stringBuilder.append(" sel=");
            this.mSelector.toShortString(stringBuilder, bl, bl2, bl3, bl4);
            stringBuilder.append("}");
        }
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder(128);
        stringBuilder.append("Intent { ");
        this.toShortString(stringBuilder, true, true, true, false);
        stringBuilder.append(" }");
        return stringBuilder.toString();
    }

    @Deprecated
    public String toURI() {
        return this.toUri(0);
    }

    public String toUri(int n) {
        StringBuilder stringBuilder = new StringBuilder(128);
        if ((n & 2) != 0) {
            if (this.mPackage != null) {
                stringBuilder.append("android-app://");
                stringBuilder.append(this.mPackage);
                Object object = null;
                Object object2 = this.mData;
                if (object2 != null) {
                    object = object2 = ((Uri)object2).getScheme();
                    if (object2 != null) {
                        stringBuilder.append('/');
                        stringBuilder.append((String)object2);
                        String string2 = this.mData.getEncodedAuthority();
                        object = object2;
                        if (string2 != null) {
                            stringBuilder.append('/');
                            stringBuilder.append(string2);
                            object = this.mData.getEncodedPath();
                            if (object != null) {
                                stringBuilder.append((String)object);
                            }
                            if ((object = this.mData.getEncodedQuery()) != null) {
                                stringBuilder.append('?');
                                stringBuilder.append((String)object);
                            }
                            string2 = this.mData.getEncodedFragment();
                            object = object2;
                            if (string2 != null) {
                                stringBuilder.append('#');
                                stringBuilder.append(string2);
                                object = object2;
                            }
                        }
                    }
                }
                object = object == null ? ACTION_MAIN : "android.intent.action.VIEW";
                this.toUriFragment(stringBuilder, null, (String)object, this.mPackage, n);
                return stringBuilder.toString();
            }
            StringBuilder stringBuilder2 = new StringBuilder();
            stringBuilder2.append("Intent must include an explicit package name to build an android-app: ");
            stringBuilder2.append(this);
            throw new IllegalArgumentException(stringBuilder2.toString());
        }
        Object object = null;
        Object object3 = null;
        Object object4 = null;
        Object object5 = this.mData;
        if (object5 != null) {
            object5 = ((Uri)object5).toString();
            object = object4;
            object3 = object5;
            if ((n & 1) != 0) {
                int n2 = ((String)object5).length();
                int n3 = 0;
                do {
                    object = object4;
                    object3 = object5;
                    if (n3 >= n2) break;
                    char c = ((String)object5).charAt(n3);
                    if (!(c >= 'a' && c <= 'z' || c >= 'A' && c <= 'Z' || c >= '0' && c <= '9' || c == '.' || c == '-' || c == '+')) {
                        object = object4;
                        object3 = object5;
                        if (c != ':') break;
                        object = object4;
                        object3 = object5;
                        if (n3 <= 0) break;
                        object = ((String)object5).substring(0, n3);
                        stringBuilder.append("intent:");
                        object3 = ((String)object5).substring(n3 + 1);
                        break;
                    }
                    ++n3;
                } while (true);
            }
            stringBuilder.append((String)object3);
        } else if ((n & 1) != 0) {
            stringBuilder.append("intent:");
            object = object3;
        }
        this.toUriFragment(stringBuilder, (String)object, "android.intent.action.VIEW", null, n);
        return stringBuilder.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeString(this.mAction);
        Uri.writeToParcel(parcel, this.mData);
        parcel.writeString(this.mType);
        parcel.writeString(this.mIdentifier);
        parcel.writeInt(this.mFlags);
        parcel.writeString(this.mPackage);
        ComponentName.writeToParcel(this.mComponent, parcel);
        if (this.mSourceBounds != null) {
            parcel.writeInt(1);
            this.mSourceBounds.writeToParcel(parcel, n);
        } else {
            parcel.writeInt(0);
        }
        ArraySet<String> arraySet = this.mCategories;
        if (arraySet != null) {
            int n2 = arraySet.size();
            parcel.writeInt(n2);
            for (int i = 0; i < n2; ++i) {
                parcel.writeString(this.mCategories.valueAt(i));
            }
        } else {
            parcel.writeInt(0);
        }
        if (this.mSelector != null) {
            parcel.writeInt(1);
            this.mSelector.writeToParcel(parcel, n);
        } else {
            parcel.writeInt(0);
        }
        if (this.mClipData != null) {
            parcel.writeInt(1);
            this.mClipData.writeToParcel(parcel, n);
        } else {
            parcel.writeInt(0);
        }
        parcel.writeInt(this.mContentUserHint);
        parcel.writeBundle(this.mExtras);
    }

    public void writeToProto(ProtoOutputStream protoOutputStream) {
        this.writeToProtoWithoutFieldId(protoOutputStream, true, true, true, false);
    }

    public void writeToProto(ProtoOutputStream protoOutputStream, long l) {
        this.writeToProto(protoOutputStream, l, true, true, true, false);
    }

    public void writeToProto(ProtoOutputStream protoOutputStream, long l, boolean bl, boolean bl2, boolean bl3, boolean bl4) {
        l = protoOutputStream.start(l);
        this.writeToProtoWithoutFieldId(protoOutputStream, bl, bl2, bl3, bl4);
        protoOutputStream.end(l);
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface AccessUriMode {
    }

    public static interface CommandOptionHandler {
        public boolean handleOption(String var1, ShellCommand var2);
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface CopyMode {
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface FillInFlags {
    }

    public static final class FilterComparison {
        private final int mHashCode;
        private final Intent mIntent;

        public FilterComparison(Intent intent) {
            this.mIntent = intent;
            this.mHashCode = intent.filterHashCode();
        }

        public boolean equals(Object object) {
            if (object instanceof FilterComparison) {
                object = ((FilterComparison)object).mIntent;
                return this.mIntent.filterEquals((Intent)object);
            }
            return false;
        }

        public Intent getIntent() {
            return this.mIntent;
        }

        public int hashCode() {
            return this.mHashCode;
        }
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface Flags {
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface GrantUriMode {
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface MutableFlags {
    }

    public static class ShortcutIconResource
    implements Parcelable {
        public static final Parcelable.Creator<ShortcutIconResource> CREATOR = new Parcelable.Creator<ShortcutIconResource>(){

            @Override
            public ShortcutIconResource createFromParcel(Parcel parcel) {
                ShortcutIconResource shortcutIconResource = new ShortcutIconResource();
                shortcutIconResource.packageName = parcel.readString();
                shortcutIconResource.resourceName = parcel.readString();
                return shortcutIconResource;
            }

            public ShortcutIconResource[] newArray(int n) {
                return new ShortcutIconResource[n];
            }
        };
        public String packageName;
        public String resourceName;

        public static ShortcutIconResource fromContext(Context context, int n) {
            ShortcutIconResource shortcutIconResource = new ShortcutIconResource();
            shortcutIconResource.packageName = context.getPackageName();
            shortcutIconResource.resourceName = context.getResources().getResourceName(n);
            return shortcutIconResource;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public String toString() {
            return this.resourceName;
        }

        @Override
        public void writeToParcel(Parcel parcel, int n) {
            parcel.writeString(this.packageName);
            parcel.writeString(this.resourceName);
        }

    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface UriFlags {
    }

}

