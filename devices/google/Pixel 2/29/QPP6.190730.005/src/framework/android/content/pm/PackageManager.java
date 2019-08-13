/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  dalvik.system.VMRuntime
 */
package android.content.pm;

import android.annotation.SystemApi;
import android.annotation.UnsupportedAppUsage;
import android.app.AppDetailsActivity;
import android.app.PackageDeleteObserver;
import android.content.ComponentName;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentSender;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.ChangedPackages;
import android.content.pm.FeatureInfo;
import android.content.pm.IPackageDataObserver;
import android.content.pm.IPackageDeleteObserver;
import android.content.pm.IPackageStatsObserver;
import android.content.pm.InstantAppInfo;
import android.content.pm.InstrumentationInfo;
import android.content.pm.IntentFilterVerificationInfo;
import android.content.pm.KeySet;
import android.content.pm.ModuleInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageInstaller;
import android.content.pm.PackageItemInfo;
import android.content.pm.PackageParser;
import android.content.pm.PackageUserState;
import android.content.pm.PermissionGroupInfo;
import android.content.pm.PermissionInfo;
import android.content.pm.ProviderInfo;
import android.content.pm.ResolveInfo;
import android.content.pm.ServiceInfo;
import android.content.pm.SharedLibraryInfo;
import android.content.pm.SuspendDialogInfo;
import android.content.pm.VerifierDeviceIdentity;
import android.content.pm.VersionedPackage;
import android.content.pm.dex.ArtManager;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.PersistableBundle;
import android.os.RemoteException;
import android.os.UserHandle;
import android.os.storage.VolumeInfo;
import android.util.AndroidException;
import android.util.Log;
import com.android.internal.util.ArrayUtils;
import dalvik.system.VMRuntime;
import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public abstract class PackageManager {
    @SystemApi
    public static final String ACTION_REQUEST_PERMISSIONS = "android.content.pm.action.REQUEST_PERMISSIONS";
    public static final boolean APPLY_DEFAULT_TO_DEVICE_PROTECTED_STORAGE = true;
    public static final String APP_DETAILS_ACTIVITY_CLASS_NAME = AppDetailsActivity.class.getName();
    public static final int CERT_INPUT_RAW_X509 = 0;
    public static final int CERT_INPUT_SHA256 = 1;
    public static final int COMPONENT_ENABLED_STATE_DEFAULT = 0;
    public static final int COMPONENT_ENABLED_STATE_DISABLED = 2;
    public static final int COMPONENT_ENABLED_STATE_DISABLED_UNTIL_USED = 4;
    public static final int COMPONENT_ENABLED_STATE_DISABLED_USER = 3;
    public static final int COMPONENT_ENABLED_STATE_ENABLED = 1;
    public static final int DELETE_ALL_USERS = 2;
    public static final int DELETE_CHATTY = Integer.MIN_VALUE;
    public static final int DELETE_CONTRIBUTED_MEDIA = 16;
    public static final int DELETE_DONT_KILL_APP = 8;
    public static final int DELETE_FAILED_ABORTED = -5;
    public static final int DELETE_FAILED_DEVICE_POLICY_MANAGER = -2;
    public static final int DELETE_FAILED_INTERNAL_ERROR = -1;
    public static final int DELETE_FAILED_OWNER_BLOCKED = -4;
    public static final int DELETE_FAILED_USED_SHARED_LIBRARY = -6;
    public static final int DELETE_FAILED_USER_RESTRICTED = -3;
    public static final int DELETE_KEEP_DATA = 1;
    public static final int DELETE_SUCCEEDED = 1;
    public static final int DELETE_SYSTEM_APP = 4;
    public static final int DONT_KILL_APP = 1;
    public static final String EXTRA_FAILURE_EXISTING_PACKAGE = "android.content.pm.extra.FAILURE_EXISTING_PACKAGE";
    public static final String EXTRA_FAILURE_EXISTING_PERMISSION = "android.content.pm.extra.FAILURE_EXISTING_PERMISSION";
    public static final String EXTRA_INTENT_FILTER_VERIFICATION_HOSTS = "android.content.pm.extra.INTENT_FILTER_VERIFICATION_HOSTS";
    public static final String EXTRA_INTENT_FILTER_VERIFICATION_ID = "android.content.pm.extra.INTENT_FILTER_VERIFICATION_ID";
    public static final String EXTRA_INTENT_FILTER_VERIFICATION_PACKAGE_NAME = "android.content.pm.extra.INTENT_FILTER_VERIFICATION_PACKAGE_NAME";
    public static final String EXTRA_INTENT_FILTER_VERIFICATION_URI_SCHEME = "android.content.pm.extra.INTENT_FILTER_VERIFICATION_URI_SCHEME";
    public static final String EXTRA_MOVE_ID = "android.content.pm.extra.MOVE_ID";
    @SystemApi
    public static final String EXTRA_REQUEST_PERMISSIONS_NAMES = "android.content.pm.extra.REQUEST_PERMISSIONS_NAMES";
    @SystemApi
    public static final String EXTRA_REQUEST_PERMISSIONS_RESULTS = "android.content.pm.extra.REQUEST_PERMISSIONS_RESULTS";
    public static final String EXTRA_VERIFICATION_ID = "android.content.pm.extra.VERIFICATION_ID";
    public static final String EXTRA_VERIFICATION_INSTALLER_PACKAGE = "android.content.pm.extra.VERIFICATION_INSTALLER_PACKAGE";
    public static final String EXTRA_VERIFICATION_INSTALLER_UID = "android.content.pm.extra.VERIFICATION_INSTALLER_UID";
    public static final String EXTRA_VERIFICATION_INSTALL_FLAGS = "android.content.pm.extra.VERIFICATION_INSTALL_FLAGS";
    public static final String EXTRA_VERIFICATION_LONG_VERSION_CODE = "android.content.pm.extra.VERIFICATION_LONG_VERSION_CODE";
    public static final String EXTRA_VERIFICATION_PACKAGE_NAME = "android.content.pm.extra.VERIFICATION_PACKAGE_NAME";
    public static final String EXTRA_VERIFICATION_RESULT = "android.content.pm.extra.VERIFICATION_RESULT";
    public static final String EXTRA_VERIFICATION_URI = "android.content.pm.extra.VERIFICATION_URI";
    @Deprecated
    public static final String EXTRA_VERIFICATION_VERSION_CODE = "android.content.pm.extra.VERIFICATION_VERSION_CODE";
    public static final String FEATURE_ACTIVITIES_ON_SECONDARY_DISPLAYS = "android.software.activities_on_secondary_displays";
    public static final String FEATURE_ADOPTABLE_STORAGE = "android.software.adoptable_storage";
    public static final String FEATURE_APP_WIDGETS = "android.software.app_widgets";
    public static final String FEATURE_ASSIST_GESTURE = "android.hardware.sensor.assist";
    public static final String FEATURE_AUDIO_LOW_LATENCY = "android.hardware.audio.low_latency";
    public static final String FEATURE_AUDIO_OUTPUT = "android.hardware.audio.output";
    public static final String FEATURE_AUDIO_PRO = "android.hardware.audio.pro";
    public static final String FEATURE_AUTOFILL = "android.software.autofill";
    public static final String FEATURE_AUTOMOTIVE = "android.hardware.type.automotive";
    public static final String FEATURE_BACKUP = "android.software.backup";
    public static final String FEATURE_BLUETOOTH = "android.hardware.bluetooth";
    public static final String FEATURE_BLUETOOTH_LE = "android.hardware.bluetooth_le";
    @SystemApi
    public static final String FEATURE_BROADCAST_RADIO = "android.hardware.broadcastradio";
    public static final String FEATURE_CAMERA = "android.hardware.camera";
    public static final String FEATURE_CAMERA_ANY = "android.hardware.camera.any";
    public static final String FEATURE_CAMERA_AR = "android.hardware.camera.ar";
    public static final String FEATURE_CAMERA_AUTOFOCUS = "android.hardware.camera.autofocus";
    public static final String FEATURE_CAMERA_CAPABILITY_MANUAL_POST_PROCESSING = "android.hardware.camera.capability.manual_post_processing";
    public static final String FEATURE_CAMERA_CAPABILITY_MANUAL_SENSOR = "android.hardware.camera.capability.manual_sensor";
    public static final String FEATURE_CAMERA_CAPABILITY_RAW = "android.hardware.camera.capability.raw";
    public static final String FEATURE_CAMERA_EXTERNAL = "android.hardware.camera.external";
    public static final String FEATURE_CAMERA_FLASH = "android.hardware.camera.flash";
    public static final String FEATURE_CAMERA_FRONT = "android.hardware.camera.front";
    public static final String FEATURE_CAMERA_LEVEL_FULL = "android.hardware.camera.level.full";
    public static final String FEATURE_CANT_SAVE_STATE = "android.software.cant_save_state";
    public static final String FEATURE_COMPANION_DEVICE_SETUP = "android.software.companion_device_setup";
    public static final String FEATURE_CONNECTION_SERVICE = "android.software.connectionservice";
    public static final String FEATURE_CONSUMER_IR = "android.hardware.consumerir";
    public static final String FEATURE_CTS = "android.software.cts";
    public static final String FEATURE_DEVICE_ADMIN = "android.software.device_admin";
    public static final String FEATURE_DEVICE_ID_ATTESTATION = "android.software.device_id_attestation";
    public static final String FEATURE_EMBEDDED = "android.hardware.type.embedded";
    public static final String FEATURE_ETHERNET = "android.hardware.ethernet";
    public static final String FEATURE_FACE = "android.hardware.biometrics.face";
    public static final String FEATURE_FAKETOUCH = "android.hardware.faketouch";
    public static final String FEATURE_FAKETOUCH_MULTITOUCH_DISTINCT = "android.hardware.faketouch.multitouch.distinct";
    public static final String FEATURE_FAKETOUCH_MULTITOUCH_JAZZHAND = "android.hardware.faketouch.multitouch.jazzhand";
    public static final String FEATURE_FILE_BASED_ENCRYPTION = "android.software.file_based_encryption";
    public static final String FEATURE_FINGERPRINT = "android.hardware.fingerprint";
    public static final String FEATURE_FREEFORM_WINDOW_MANAGEMENT = "android.software.freeform_window_management";
    public static final String FEATURE_GAMEPAD = "android.hardware.gamepad";
    public static final String FEATURE_HDMI_CEC = "android.hardware.hdmi.cec";
    public static final String FEATURE_HIFI_SENSORS = "android.hardware.sensor.hifi_sensors";
    public static final String FEATURE_HOME_SCREEN = "android.software.home_screen";
    public static final String FEATURE_INPUT_METHODS = "android.software.input_methods";
    public static final String FEATURE_IPSEC_TUNNELS = "android.software.ipsec_tunnels";
    public static final String FEATURE_IRIS = "android.hardware.biometrics.iris";
    public static final String FEATURE_LEANBACK = "android.software.leanback";
    public static final String FEATURE_LEANBACK_ONLY = "android.software.leanback_only";
    public static final String FEATURE_LIVE_TV = "android.software.live_tv";
    public static final String FEATURE_LIVE_WALLPAPER = "android.software.live_wallpaper";
    public static final String FEATURE_LOCATION = "android.hardware.location";
    public static final String FEATURE_LOCATION_GPS = "android.hardware.location.gps";
    public static final String FEATURE_LOCATION_NETWORK = "android.hardware.location.network";
    public static final String FEATURE_LOWPAN = "android.hardware.lowpan";
    public static final String FEATURE_MANAGED_PROFILES = "android.software.managed_users";
    public static final String FEATURE_MANAGED_USERS = "android.software.managed_users";
    public static final String FEATURE_MICROPHONE = "android.hardware.microphone";
    public static final String FEATURE_MIDI = "android.software.midi";
    public static final String FEATURE_NFC = "android.hardware.nfc";
    public static final String FEATURE_NFC_ANY = "android.hardware.nfc.any";
    public static final String FEATURE_NFC_BEAM = "android.sofware.nfc.beam";
    @Deprecated
    public static final String FEATURE_NFC_HCE = "android.hardware.nfc.hce";
    public static final String FEATURE_NFC_HOST_CARD_EMULATION = "android.hardware.nfc.hce";
    public static final String FEATURE_NFC_HOST_CARD_EMULATION_NFCF = "android.hardware.nfc.hcef";
    public static final String FEATURE_NFC_OFF_HOST_CARD_EMULATION_ESE = "android.hardware.nfc.ese";
    public static final String FEATURE_NFC_OFF_HOST_CARD_EMULATION_UICC = "android.hardware.nfc.uicc";
    public static final String FEATURE_OPENGLES_EXTENSION_PACK = "android.hardware.opengles.aep";
    public static final String FEATURE_PC = "android.hardware.type.pc";
    public static final String FEATURE_PICTURE_IN_PICTURE = "android.software.picture_in_picture";
    public static final String FEATURE_PRINTING = "android.software.print";
    public static final String FEATURE_RAM_LOW = "android.hardware.ram.low";
    public static final String FEATURE_RAM_NORMAL = "android.hardware.ram.normal";
    public static final String FEATURE_SCREEN_LANDSCAPE = "android.hardware.screen.landscape";
    public static final String FEATURE_SCREEN_PORTRAIT = "android.hardware.screen.portrait";
    public static final String FEATURE_SECURELY_REMOVES_USERS = "android.software.securely_removes_users";
    public static final String FEATURE_SECURE_LOCK_SCREEN = "android.software.secure_lock_screen";
    public static final String FEATURE_SENSOR_ACCELEROMETER = "android.hardware.sensor.accelerometer";
    public static final String FEATURE_SENSOR_AMBIENT_TEMPERATURE = "android.hardware.sensor.ambient_temperature";
    public static final String FEATURE_SENSOR_BAROMETER = "android.hardware.sensor.barometer";
    public static final String FEATURE_SENSOR_COMPASS = "android.hardware.sensor.compass";
    public static final String FEATURE_SENSOR_GYROSCOPE = "android.hardware.sensor.gyroscope";
    public static final String FEATURE_SENSOR_HEART_RATE = "android.hardware.sensor.heartrate";
    public static final String FEATURE_SENSOR_HEART_RATE_ECG = "android.hardware.sensor.heartrate.ecg";
    public static final String FEATURE_SENSOR_LIGHT = "android.hardware.sensor.light";
    public static final String FEATURE_SENSOR_PROXIMITY = "android.hardware.sensor.proximity";
    public static final String FEATURE_SENSOR_RELATIVE_HUMIDITY = "android.hardware.sensor.relative_humidity";
    public static final String FEATURE_SENSOR_STEP_COUNTER = "android.hardware.sensor.stepcounter";
    public static final String FEATURE_SENSOR_STEP_DETECTOR = "android.hardware.sensor.stepdetector";
    public static final String FEATURE_SIP = "android.software.sip";
    public static final String FEATURE_SIP_VOIP = "android.software.sip.voip";
    public static final String FEATURE_STRONGBOX_KEYSTORE = "android.hardware.strongbox_keystore";
    public static final String FEATURE_TELEPHONY = "android.hardware.telephony";
    @SystemApi
    public static final String FEATURE_TELEPHONY_CARRIERLOCK = "android.hardware.telephony.carrierlock";
    public static final String FEATURE_TELEPHONY_CDMA = "android.hardware.telephony.cdma";
    public static final String FEATURE_TELEPHONY_EUICC = "android.hardware.telephony.euicc";
    public static final String FEATURE_TELEPHONY_GSM = "android.hardware.telephony.gsm";
    public static final String FEATURE_TELEPHONY_IMS = "android.hardware.telephony.ims";
    public static final String FEATURE_TELEPHONY_MBMS = "android.hardware.telephony.mbms";
    @Deprecated
    public static final String FEATURE_TELEVISION = "android.hardware.type.television";
    public static final String FEATURE_TOUCHSCREEN = "android.hardware.touchscreen";
    public static final String FEATURE_TOUCHSCREEN_MULTITOUCH = "android.hardware.touchscreen.multitouch";
    public static final String FEATURE_TOUCHSCREEN_MULTITOUCH_DISTINCT = "android.hardware.touchscreen.multitouch.distinct";
    public static final String FEATURE_TOUCHSCREEN_MULTITOUCH_JAZZHAND = "android.hardware.touchscreen.multitouch.jazzhand";
    public static final String FEATURE_USB_ACCESSORY = "android.hardware.usb.accessory";
    public static final String FEATURE_USB_HOST = "android.hardware.usb.host";
    public static final String FEATURE_VERIFIED_BOOT = "android.software.verified_boot";
    public static final String FEATURE_VOICE_RECOGNIZERS = "android.software.voice_recognizers";
    public static final String FEATURE_VR_HEADTRACKING = "android.hardware.vr.headtracking";
    @Deprecated
    public static final String FEATURE_VR_MODE = "android.software.vr.mode";
    public static final String FEATURE_VR_MODE_HIGH_PERFORMANCE = "android.hardware.vr.high_performance";
    public static final String FEATURE_VULKAN_HARDWARE_COMPUTE = "android.hardware.vulkan.compute";
    public static final String FEATURE_VULKAN_HARDWARE_LEVEL = "android.hardware.vulkan.level";
    public static final String FEATURE_VULKAN_HARDWARE_VERSION = "android.hardware.vulkan.version";
    public static final String FEATURE_WATCH = "android.hardware.type.watch";
    public static final String FEATURE_WEBVIEW = "android.software.webview";
    public static final String FEATURE_WIFI = "android.hardware.wifi";
    public static final String FEATURE_WIFI_AWARE = "android.hardware.wifi.aware";
    public static final String FEATURE_WIFI_DIRECT = "android.hardware.wifi.direct";
    public static final String FEATURE_WIFI_PASSPOINT = "android.hardware.wifi.passpoint";
    public static final String FEATURE_WIFI_RTT = "android.hardware.wifi.rtt";
    public static final int FLAGS_PERMISSION_RESTRICTION_ANY_EXEMPT = 14336;
    @SystemApi
    public static final int FLAG_PERMISSION_APPLY_RESTRICTION = 16384;
    @SystemApi
    public static final int FLAG_PERMISSION_GRANTED_BY_DEFAULT = 32;
    @SystemApi
    public static final int FLAG_PERMISSION_GRANTED_BY_ROLE = 32768;
    @SystemApi
    public static final int FLAG_PERMISSION_POLICY_FIXED = 4;
    @SystemApi
    public static final int FLAG_PERMISSION_RESTRICTION_INSTALLER_EXEMPT = 2048;
    @SystemApi
    public static final int FLAG_PERMISSION_RESTRICTION_SYSTEM_EXEMPT = 4096;
    @SystemApi
    public static final int FLAG_PERMISSION_RESTRICTION_UPGRADE_EXEMPT = 8192;
    @SystemApi
    public static final int FLAG_PERMISSION_REVIEW_REQUIRED = 64;
    @SystemApi
    public static final int FLAG_PERMISSION_REVOKE_ON_UPGRADE = 8;
    public static final int FLAG_PERMISSION_REVOKE_WHEN_REQUESTED = 128;
    @SystemApi
    public static final int FLAG_PERMISSION_SYSTEM_FIXED = 16;
    @SystemApi
    public static final int FLAG_PERMISSION_USER_FIXED = 2;
    @SystemApi
    public static final int FLAG_PERMISSION_USER_SENSITIVE_WHEN_DENIED = 512;
    @SystemApi
    public static final int FLAG_PERMISSION_USER_SENSITIVE_WHEN_GRANTED = 256;
    @SystemApi
    public static final int FLAG_PERMISSION_USER_SET = 1;
    public static final int FLAG_PERMISSION_WHITELIST_INSTALLER = 2;
    public static final int FLAG_PERMISSION_WHITELIST_SYSTEM = 1;
    public static final int FLAG_PERMISSION_WHITELIST_UPGRADE = 4;
    public static final int GET_ACTIVITIES = 1;
    public static final int GET_CONFIGURATIONS = 16384;
    @Deprecated
    public static final int GET_DISABLED_COMPONENTS = 512;
    @Deprecated
    public static final int GET_DISABLED_UNTIL_USED_COMPONENTS = 32768;
    public static final int GET_GIDS = 256;
    public static final int GET_INSTRUMENTATION = 16;
    public static final int GET_INTENT_FILTERS = 32;
    public static final int GET_META_DATA = 128;
    public static final int GET_PERMISSIONS = 4096;
    public static final int GET_PROVIDERS = 8;
    public static final int GET_RECEIVERS = 2;
    public static final int GET_RESOLVED_FILTER = 64;
    public static final int GET_SERVICES = 4;
    public static final int GET_SHARED_LIBRARY_FILES = 1024;
    @Deprecated
    public static final int GET_SIGNATURES = 64;
    public static final int GET_SIGNING_CERTIFICATES = 134217728;
    @Deprecated
    public static final int GET_UNINSTALLED_PACKAGES = 8192;
    public static final int GET_URI_PERMISSION_PATTERNS = 2048;
    public static final int INSTALL_ALLOCATE_AGGRESSIVE = 32768;
    public static final int INSTALL_ALLOW_DOWNGRADE = 1048576;
    public static final int INSTALL_ALLOW_TEST = 4;
    public static final int INSTALL_ALL_USERS = 64;
    public static final int INSTALL_ALL_WHITELIST_RESTRICTED_PERMISSIONS = 4194304;
    public static final int INSTALL_APEX = 131072;
    public static final int INSTALL_DISABLE_VERIFICATION = 524288;
    public static final int INSTALL_DONT_KILL_APP = 4096;
    public static final int INSTALL_DRY_RUN = 8388608;
    public static final int INSTALL_ENABLE_ROLLBACK = 262144;
    public static final int INSTALL_FAILED_ABORTED = -115;
    @SystemApi
    public static final int INSTALL_FAILED_ALREADY_EXISTS = -1;
    public static final int INSTALL_FAILED_BAD_DEX_METADATA = -117;
    public static final int INSTALL_FAILED_BAD_SIGNATURE = -118;
    @SystemApi
    public static final int INSTALL_FAILED_CONFLICTING_PROVIDER = -13;
    @SystemApi
    public static final int INSTALL_FAILED_CONTAINER_ERROR = -18;
    @SystemApi
    public static final int INSTALL_FAILED_CPU_ABI_INCOMPATIBLE = -16;
    @SystemApi
    public static final int INSTALL_FAILED_DEXOPT = -11;
    @SystemApi
    public static final int INSTALL_FAILED_DUPLICATE_PACKAGE = -5;
    public static final int INSTALL_FAILED_DUPLICATE_PERMISSION = -112;
    public static final int INSTALL_FAILED_INSTANT_APP_INVALID = -116;
    @SystemApi
    public static final int INSTALL_FAILED_INSUFFICIENT_STORAGE = -4;
    @SystemApi
    public static final int INSTALL_FAILED_INTERNAL_ERROR = -110;
    @SystemApi
    public static final int INSTALL_FAILED_INVALID_APK = -2;
    @SystemApi
    public static final int INSTALL_FAILED_INVALID_INSTALL_LOCATION = -19;
    @SystemApi
    public static final int INSTALL_FAILED_INVALID_URI = -3;
    @SystemApi
    public static final int INSTALL_FAILED_MEDIA_UNAVAILABLE = -20;
    @SystemApi
    public static final int INSTALL_FAILED_MISSING_FEATURE = -17;
    @SystemApi
    public static final int INSTALL_FAILED_MISSING_SHARED_LIBRARY = -9;
    public static final int INSTALL_FAILED_MISSING_SPLIT = -28;
    public static final int INSTALL_FAILED_MULTIPACKAGE_INCONSISTENCY = -120;
    @SystemApi
    public static final int INSTALL_FAILED_NEWER_SDK = -14;
    public static final int INSTALL_FAILED_NO_MATCHING_ABIS = -113;
    @SystemApi
    public static final int INSTALL_FAILED_NO_SHARED_USER = -6;
    @SystemApi
    public static final int INSTALL_FAILED_OLDER_SDK = -12;
    public static final int INSTALL_FAILED_OTHER_STAGED_SESSION_IN_PROGRESS = -119;
    @SystemApi
    public static final int INSTALL_FAILED_PACKAGE_CHANGED = -23;
    @SystemApi
    public static final int INSTALL_FAILED_PERMISSION_MODEL_DOWNGRADE = -26;
    @SystemApi
    public static final int INSTALL_FAILED_REPLACE_COULDNT_DELETE = -10;
    @SystemApi
    public static final int INSTALL_FAILED_SANDBOX_VERSION_DOWNGRADE = -27;
    @SystemApi
    public static final int INSTALL_FAILED_SHARED_USER_INCOMPATIBLE = -8;
    @SystemApi
    public static final int INSTALL_FAILED_TEST_ONLY = -15;
    public static final int INSTALL_FAILED_UID_CHANGED = -24;
    @SystemApi
    public static final int INSTALL_FAILED_UPDATE_INCOMPATIBLE = -7;
    public static final int INSTALL_FAILED_USER_RESTRICTED = -111;
    @SystemApi
    public static final int INSTALL_FAILED_VERIFICATION_FAILURE = -22;
    @SystemApi
    public static final int INSTALL_FAILED_VERIFICATION_TIMEOUT = -21;
    public static final int INSTALL_FAILED_VERSION_DOWNGRADE = -25;
    public static final int INSTALL_FAILED_WRONG_INSTALLED_VERSION = -121;
    public static final int INSTALL_FORCE_PERMISSION_PROMPT = 1024;
    public static final int INSTALL_FORCE_VOLUME_UUID = 512;
    public static final int INSTALL_FROM_ADB = 32;
    public static final int INSTALL_FULL_APP = 16384;
    public static final int INSTALL_GRANT_RUNTIME_PERMISSIONS = 256;
    public static final int INSTALL_INSTANT_APP = 2048;
    public static final int INSTALL_INTERNAL = 16;
    @SystemApi
    public static final int INSTALL_PARSE_FAILED_BAD_MANIFEST = -101;
    @SystemApi
    public static final int INSTALL_PARSE_FAILED_BAD_PACKAGE_NAME = -106;
    @SystemApi
    public static final int INSTALL_PARSE_FAILED_BAD_SHARED_USER_ID = -107;
    @SystemApi
    public static final int INSTALL_PARSE_FAILED_CERTIFICATE_ENCODING = -105;
    @SystemApi
    public static final int INSTALL_PARSE_FAILED_INCONSISTENT_CERTIFICATES = -104;
    @SystemApi
    public static final int INSTALL_PARSE_FAILED_MANIFEST_EMPTY = -109;
    @SystemApi
    public static final int INSTALL_PARSE_FAILED_MANIFEST_MALFORMED = -108;
    @SystemApi
    public static final int INSTALL_PARSE_FAILED_NOT_APK = -100;
    @SystemApi
    public static final int INSTALL_PARSE_FAILED_NO_CERTIFICATES = -103;
    @SystemApi
    public static final int INSTALL_PARSE_FAILED_UNEXPECTED_EXCEPTION = -102;
    public static final int INSTALL_REASON_DEVICE_RESTORE = 2;
    public static final int INSTALL_REASON_DEVICE_SETUP = 3;
    public static final int INSTALL_REASON_POLICY = 1;
    public static final int INSTALL_REASON_UNKNOWN = 0;
    public static final int INSTALL_REASON_USER = 4;
    @UnsupportedAppUsage
    public static final int INSTALL_REPLACE_EXISTING = 2;
    public static final int INSTALL_REQUEST_DOWNGRADE = 128;
    public static final int INSTALL_STAGED = 2097152;
    @SystemApi
    public static final int INSTALL_SUCCEEDED = 1;
    public static final int INSTALL_UNKNOWN = 0;
    public static final int INSTALL_VIRTUAL_PRELOAD = 65536;
    @SystemApi
    public static final int INTENT_FILTER_DOMAIN_VERIFICATION_STATUS_ALWAYS = 2;
    @SystemApi
    public static final int INTENT_FILTER_DOMAIN_VERIFICATION_STATUS_ALWAYS_ASK = 4;
    @SystemApi
    public static final int INTENT_FILTER_DOMAIN_VERIFICATION_STATUS_ASK = 1;
    @SystemApi
    public static final int INTENT_FILTER_DOMAIN_VERIFICATION_STATUS_NEVER = 3;
    @SystemApi
    public static final int INTENT_FILTER_DOMAIN_VERIFICATION_STATUS_UNDEFINED = 0;
    @SystemApi
    public static final int INTENT_FILTER_VERIFICATION_FAILURE = -1;
    @SystemApi
    public static final int INTENT_FILTER_VERIFICATION_SUCCESS = 1;
    @SystemApi
    @Deprecated
    public static final int MASK_PERMISSION_FLAGS = 255;
    public static final int MASK_PERMISSION_FLAGS_ALL = 64511;
    public static final int MATCH_ALL = 131072;
    @SystemApi
    public static final int MATCH_ANY_USER = 4194304;
    public static final int MATCH_APEX = 1073741824;
    @Deprecated
    public static final int MATCH_DEBUG_TRIAGED_MISSING = 268435456;
    public static final int MATCH_DEFAULT_ONLY = 65536;
    public static final int MATCH_DIRECT_BOOT_AUTO = 268435456;
    public static final int MATCH_DIRECT_BOOT_AWARE = 524288;
    public static final int MATCH_DIRECT_BOOT_UNAWARE = 262144;
    public static final int MATCH_DISABLED_COMPONENTS = 512;
    public static final int MATCH_DISABLED_UNTIL_USED_COMPONENTS = 32768;
    public static final int MATCH_EXPLICITLY_VISIBLE_ONLY = 33554432;
    @SystemApi
    public static final int MATCH_FACTORY_ONLY = 2097152;
    public static final int MATCH_HIDDEN_UNTIL_INSTALLED_COMPONENTS = 536870912;
    @SystemApi
    public static final int MATCH_INSTANT = 8388608;
    public static final int MATCH_KNOWN_PACKAGES = 4202496;
    public static final int MATCH_STATIC_SHARED_LIBRARIES = 67108864;
    public static final int MATCH_SYSTEM_ONLY = 1048576;
    public static final int MATCH_UNINSTALLED_PACKAGES = 8192;
    public static final int MATCH_VISIBLE_TO_INSTANT_APP_ONLY = 16777216;
    public static final long MAXIMUM_VERIFICATION_TIMEOUT = 3600000L;
    @Deprecated
    @UnsupportedAppUsage
    public static final int MOVE_EXTERNAL_MEDIA = 2;
    public static final int MOVE_FAILED_3RD_PARTY_NOT_ALLOWED_ON_INTERNAL = -9;
    public static final int MOVE_FAILED_DEVICE_ADMIN = -8;
    public static final int MOVE_FAILED_DOESNT_EXIST = -2;
    public static final int MOVE_FAILED_INSUFFICIENT_STORAGE = -1;
    public static final int MOVE_FAILED_INTERNAL_ERROR = -6;
    public static final int MOVE_FAILED_INVALID_LOCATION = -5;
    public static final int MOVE_FAILED_LOCKED_USER = -10;
    public static final int MOVE_FAILED_OPERATION_PENDING = -7;
    public static final int MOVE_FAILED_SYSTEM_PACKAGE = -3;
    @Deprecated
    @UnsupportedAppUsage
    public static final int MOVE_INTERNAL = 1;
    public static final int MOVE_SUCCEEDED = -100;
    public static final int NOTIFY_PACKAGE_USE_ACTIVITY = 0;
    public static final int NOTIFY_PACKAGE_USE_BACKUP = 5;
    public static final int NOTIFY_PACKAGE_USE_BROADCAST_RECEIVER = 3;
    public static final int NOTIFY_PACKAGE_USE_CONTENT_PROVIDER = 4;
    public static final int NOTIFY_PACKAGE_USE_CROSS_PACKAGE = 6;
    public static final int NOTIFY_PACKAGE_USE_FOREGROUND_SERVICE = 2;
    public static final int NOTIFY_PACKAGE_USE_INSTRUMENTATION = 7;
    public static final int NOTIFY_PACKAGE_USE_REASONS_COUNT = 8;
    public static final int NOTIFY_PACKAGE_USE_SERVICE = 1;
    @UnsupportedAppUsage
    public static final int NO_NATIVE_LIBRARIES = -114;
    public static final int ONLY_IF_NO_MATCH_FOUND = 4;
    public static final int PERMISSION_DENIED = -1;
    public static final int PERMISSION_GRANTED = 0;
    @SystemApi
    public static final int RESTRICTION_HIDE_FROM_SUGGESTIONS = 1;
    @SystemApi
    public static final int RESTRICTION_HIDE_NOTIFICATIONS = 2;
    @SystemApi
    public static final int RESTRICTION_NONE = 0;
    public static final int SIGNATURE_FIRST_NOT_SIGNED = -1;
    public static final int SIGNATURE_MATCH = 0;
    public static final int SIGNATURE_NEITHER_SIGNED = 1;
    public static final int SIGNATURE_NO_MATCH = -3;
    public static final int SIGNATURE_SECOND_NOT_SIGNED = -2;
    public static final int SIGNATURE_UNKNOWN_PACKAGE = -4;
    public static final int SKIP_CURRENT_PROFILE = 2;
    public static final String SYSTEM_SHARED_LIBRARY_SERVICES = "android.ext.services";
    public static final String SYSTEM_SHARED_LIBRARY_SHARED = "android.ext.shared";
    private static final String TAG = "PackageManager";
    public static final int VERIFICATION_ALLOW = 1;
    public static final int VERIFICATION_ALLOW_WITHOUT_SUFFICIENT = 2;
    public static final int VERIFICATION_REJECT = -1;
    public static final int VERSION_CODE_HIGHEST = -1;

    public static int deleteStatusToPublicStatus(int n) {
        switch (n) {
            default: {
                return 1;
            }
            case 1: {
                return 0;
            }
            case -1: {
                return 1;
            }
            case -2: {
                return 2;
            }
            case -3: {
                return 2;
            }
            case -4: {
                return 2;
            }
            case -5: {
                return 3;
            }
            case -6: 
        }
        return 5;
    }

    @UnsupportedAppUsage
    public static String deleteStatusToString(int n) {
        switch (n) {
            default: {
                return Integer.toString(n);
            }
            case 1: {
                return "DELETE_SUCCEEDED";
            }
            case -1: {
                return "DELETE_FAILED_INTERNAL_ERROR";
            }
            case -2: {
                return "DELETE_FAILED_DEVICE_POLICY_MANAGER";
            }
            case -3: {
                return "DELETE_FAILED_USER_RESTRICTED";
            }
            case -4: {
                return "DELETE_FAILED_OWNER_BLOCKED";
            }
            case -5: {
                return "DELETE_FAILED_ABORTED";
            }
            case -6: 
        }
        return "DELETE_FAILED_USED_SHARED_LIBRARY";
    }

    public static String deleteStatusToString(int n, String string2) {
        String string3 = PackageManager.deleteStatusToString(n);
        if (string2 != null) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(string3);
            stringBuilder.append(": ");
            stringBuilder.append(string2);
            return stringBuilder.toString();
        }
        return string3;
    }

    public static int installStatusToPublicStatus(int n) {
        if (n != -118) {
            if (n != -117) {
                if (n != -115) {
                    if (n != -28) {
                        if (n != 1) {
                            switch (n) {
                                default: {
                                    switch (n) {
                                        default: {
                                            return 1;
                                        }
                                        case -1: {
                                            return 5;
                                        }
                                        case -2: {
                                            return 4;
                                        }
                                        case -3: {
                                            return 4;
                                        }
                                        case -4: {
                                            return 6;
                                        }
                                        case -5: {
                                            return 5;
                                        }
                                        case -6: {
                                            return 5;
                                        }
                                        case -7: {
                                            return 5;
                                        }
                                        case -8: {
                                            return 5;
                                        }
                                        case -9: {
                                            return 7;
                                        }
                                        case -10: {
                                            return 5;
                                        }
                                        case -11: {
                                            return 4;
                                        }
                                        case -12: {
                                            return 7;
                                        }
                                        case -13: {
                                            return 5;
                                        }
                                        case -14: {
                                            return 7;
                                        }
                                        case -15: {
                                            return 4;
                                        }
                                        case -16: {
                                            return 7;
                                        }
                                        case -17: {
                                            return 7;
                                        }
                                        case -18: {
                                            return 6;
                                        }
                                        case -19: {
                                            return 6;
                                        }
                                        case -20: {
                                            return 6;
                                        }
                                        case -21: {
                                            return 3;
                                        }
                                        case -22: {
                                            return 3;
                                        }
                                        case -23: {
                                            return 4;
                                        }
                                        case -24: {
                                            return 4;
                                        }
                                        case -25: {
                                            return 4;
                                        }
                                        case -26: 
                                    }
                                    return 4;
                                }
                                case -100: {
                                    return 4;
                                }
                                case -101: {
                                    return 4;
                                }
                                case -102: {
                                    return 4;
                                }
                                case -103: {
                                    return 4;
                                }
                                case -104: {
                                    return 4;
                                }
                                case -105: {
                                    return 4;
                                }
                                case -106: {
                                    return 4;
                                }
                                case -107: {
                                    return 4;
                                }
                                case -108: {
                                    return 4;
                                }
                                case -109: {
                                    return 4;
                                }
                                case -110: {
                                    return 1;
                                }
                                case -111: {
                                    return 7;
                                }
                                case -112: {
                                    return 5;
                                }
                                case -113: 
                            }
                            return 7;
                        }
                        return 0;
                    }
                    return 7;
                }
                return 3;
            }
            return 4;
        }
        return 4;
    }

    @UnsupportedAppUsage
    public static String installStatusToString(int n) {
        if (n != -121) {
            if (n != -115) {
                if (n != -28) {
                    if (n != 1) {
                        if (n != -118) {
                            if (n != -117) {
                                switch (n) {
                                    default: {
                                        switch (n) {
                                            default: {
                                                return Integer.toString(n);
                                            }
                                            case -1: {
                                                return "INSTALL_FAILED_ALREADY_EXISTS";
                                            }
                                            case -2: {
                                                return "INSTALL_FAILED_INVALID_APK";
                                            }
                                            case -3: {
                                                return "INSTALL_FAILED_INVALID_URI";
                                            }
                                            case -4: {
                                                return "INSTALL_FAILED_INSUFFICIENT_STORAGE";
                                            }
                                            case -5: {
                                                return "INSTALL_FAILED_DUPLICATE_PACKAGE";
                                            }
                                            case -6: {
                                                return "INSTALL_FAILED_NO_SHARED_USER";
                                            }
                                            case -7: {
                                                return "INSTALL_FAILED_UPDATE_INCOMPATIBLE";
                                            }
                                            case -8: {
                                                return "INSTALL_FAILED_SHARED_USER_INCOMPATIBLE";
                                            }
                                            case -9: {
                                                return "INSTALL_FAILED_MISSING_SHARED_LIBRARY";
                                            }
                                            case -10: {
                                                return "INSTALL_FAILED_REPLACE_COULDNT_DELETE";
                                            }
                                            case -11: {
                                                return "INSTALL_FAILED_DEXOPT";
                                            }
                                            case -12: {
                                                return "INSTALL_FAILED_OLDER_SDK";
                                            }
                                            case -13: {
                                                return "INSTALL_FAILED_CONFLICTING_PROVIDER";
                                            }
                                            case -14: {
                                                return "INSTALL_FAILED_NEWER_SDK";
                                            }
                                            case -15: {
                                                return "INSTALL_FAILED_TEST_ONLY";
                                            }
                                            case -16: {
                                                return "INSTALL_FAILED_CPU_ABI_INCOMPATIBLE";
                                            }
                                            case -17: {
                                                return "INSTALL_FAILED_MISSING_FEATURE";
                                            }
                                            case -18: {
                                                return "INSTALL_FAILED_CONTAINER_ERROR";
                                            }
                                            case -19: {
                                                return "INSTALL_FAILED_INVALID_INSTALL_LOCATION";
                                            }
                                            case -20: {
                                                return "INSTALL_FAILED_MEDIA_UNAVAILABLE";
                                            }
                                            case -21: {
                                                return "INSTALL_FAILED_VERIFICATION_TIMEOUT";
                                            }
                                            case -22: {
                                                return "INSTALL_FAILED_VERIFICATION_FAILURE";
                                            }
                                            case -23: {
                                                return "INSTALL_FAILED_PACKAGE_CHANGED";
                                            }
                                            case -24: {
                                                return "INSTALL_FAILED_UID_CHANGED";
                                            }
                                            case -25: 
                                        }
                                        return "INSTALL_FAILED_VERSION_DOWNGRADE";
                                    }
                                    case -100: {
                                        return "INSTALL_PARSE_FAILED_NOT_APK";
                                    }
                                    case -101: {
                                        return "INSTALL_PARSE_FAILED_BAD_MANIFEST";
                                    }
                                    case -102: {
                                        return "INSTALL_PARSE_FAILED_UNEXPECTED_EXCEPTION";
                                    }
                                    case -103: {
                                        return "INSTALL_PARSE_FAILED_NO_CERTIFICATES";
                                    }
                                    case -104: {
                                        return "INSTALL_PARSE_FAILED_INCONSISTENT_CERTIFICATES";
                                    }
                                    case -105: {
                                        return "INSTALL_PARSE_FAILED_CERTIFICATE_ENCODING";
                                    }
                                    case -106: {
                                        return "INSTALL_PARSE_FAILED_BAD_PACKAGE_NAME";
                                    }
                                    case -107: {
                                        return "INSTALL_PARSE_FAILED_BAD_SHARED_USER_ID";
                                    }
                                    case -108: {
                                        return "INSTALL_PARSE_FAILED_MANIFEST_MALFORMED";
                                    }
                                    case -109: {
                                        return "INSTALL_PARSE_FAILED_MANIFEST_EMPTY";
                                    }
                                    case -110: {
                                        return "INSTALL_FAILED_INTERNAL_ERROR";
                                    }
                                    case -111: {
                                        return "INSTALL_FAILED_USER_RESTRICTED";
                                    }
                                    case -112: {
                                        return "INSTALL_FAILED_DUPLICATE_PERMISSION";
                                    }
                                    case -113: 
                                }
                                return "INSTALL_FAILED_NO_MATCHING_ABIS";
                            }
                            return "INSTALL_FAILED_BAD_DEX_METADATA";
                        }
                        return "INSTALL_FAILED_BAD_SIGNATURE";
                    }
                    return "INSTALL_SUCCEEDED";
                }
                return "INSTALL_FAILED_MISSING_SPLIT";
            }
            return "INSTALL_FAILED_ABORTED";
        }
        return "INSTALL_FAILED_WRONG_INSTALLED_VERSION";
    }

    @UnsupportedAppUsage
    public static String installStatusToString(int n, String string2) {
        String string3 = PackageManager.installStatusToString(n);
        if (string2 != null) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(string3);
            stringBuilder.append(": ");
            stringBuilder.append(string2);
            return stringBuilder.toString();
        }
        return string3;
    }

    public static boolean isMoveStatusFinished(int n) {
        boolean bl = n < 0 || n > 100;
        return bl;
    }

    public static String permissionFlagToString(int n) {
        if (n != 1) {
            if (n != 2) {
                switch (n) {
                    default: {
                        return Integer.toString(n);
                    }
                    case 32768: {
                        return "GRANTED_BY_ROLE";
                    }
                    case 16384: {
                        return "APPLY_RESTRICTION";
                    }
                    case 8192: {
                        return "RESTRICTION_UPGRADE_EXEMPT";
                    }
                    case 4096: {
                        return "RESTRICTION_SYSTEM_EXEMPT";
                    }
                    case 2048: {
                        return "RESTRICTION_INSTALLER_EXEMPT";
                    }
                    case 512: {
                        return "USER_SENSITIVE_WHEN_DENIED";
                    }
                    case 256: {
                        return "USER_SENSITIVE_WHEN_GRANTED";
                    }
                    case 128: {
                        return "REVOKE_WHEN_REQUESTED";
                    }
                    case 64: {
                        return "REVIEW_REQUIRED";
                    }
                    case 32: {
                        return "GRANTED_BY_DEFAULT";
                    }
                    case 16: {
                        return "SYSTEM_FIXED";
                    }
                    case 8: {
                        return "REVOKE_ON_UPGRADE";
                    }
                    case 4: 
                }
                return "POLICY_FIXED";
            }
            return "USER_FIXED";
        }
        return "USER_SET";
    }

    @UnsupportedAppUsage
    public abstract void addCrossProfileIntentFilter(IntentFilter var1, int var2, int var3, int var4);

    @SystemApi
    public abstract void addOnPermissionsChangeListener(OnPermissionsChangedListener var1);

    @Deprecated
    public abstract void addPackageToPreferred(String var1);

    public abstract boolean addPermission(PermissionInfo var1);

    public abstract boolean addPermissionAsync(PermissionInfo var1);

    @Deprecated
    public abstract void addPreferredActivity(IntentFilter var1, int var2, ComponentName[] var3, ComponentName var4);

    @Deprecated
    @UnsupportedAppUsage
    public void addPreferredActivityAsUser(IntentFilter intentFilter, int n, ComponentName[] arrcomponentName, ComponentName componentName, int n2) {
        throw new RuntimeException("Not implemented. Must override in a subclass.");
    }

    public boolean addWhitelistedRestrictedPermission(String string2, String string3, int n) {
        return false;
    }

    @SystemApi
    public abstract boolean arePermissionsIndividuallyControlled();

    @UnsupportedAppUsage
    public Intent buildRequestPermissionsIntent(String[] arrstring) {
        if (!ArrayUtils.isEmpty(arrstring)) {
            Intent intent = new Intent("android.content.pm.action.REQUEST_PERMISSIONS");
            intent.putExtra("android.content.pm.extra.REQUEST_PERMISSIONS_NAMES", arrstring);
            intent.setPackage(this.getPermissionControllerPackageName());
            return intent;
        }
        throw new IllegalArgumentException("permission cannot be null or empty");
    }

    public abstract boolean canRequestPackageInstalls();

    public abstract String[] canonicalToCurrentPackageNames(String[] var1);

    public abstract int checkPermission(String var1, String var2);

    public abstract int checkSignatures(int var1, int var2);

    public abstract int checkSignatures(String var1, String var2);

    @UnsupportedAppUsage
    public abstract void clearApplicationUserData(String var1, IPackageDataObserver var2);

    @UnsupportedAppUsage
    public abstract void clearCrossProfileIntentFilters(int var1);

    public abstract void clearInstantAppCookie();

    @Deprecated
    public abstract void clearPackagePreferredActivities(String var1);

    public abstract String[] currentToCanonicalPackageNames(String[] var1);

    @UnsupportedAppUsage
    public abstract void deleteApplicationCacheFiles(String var1, IPackageDataObserver var2);

    @UnsupportedAppUsage
    public abstract void deleteApplicationCacheFilesAsUser(String var1, int var2, IPackageDataObserver var3);

    @UnsupportedAppUsage
    public abstract void deletePackage(String var1, IPackageDeleteObserver var2, int var3);

    @UnsupportedAppUsage
    public abstract void deletePackageAsUser(String var1, IPackageDeleteObserver var2, int var3, int var4);

    public abstract void extendVerificationTimeout(int var1, int var2, long var3);

    @UnsupportedAppUsage
    public abstract void flushPackageRestrictionsAsUser(int var1);

    @UnsupportedAppUsage
    public void freeStorage(long l, IntentSender intentSender) {
        this.freeStorage(null, l, intentSender);
    }

    @UnsupportedAppUsage
    public abstract void freeStorage(String var1, long var2, IntentSender var4);

    @UnsupportedAppUsage
    public void freeStorageAndNotify(long l, IPackageDataObserver iPackageDataObserver) {
        this.freeStorageAndNotify(null, l, iPackageDataObserver);
    }

    @UnsupportedAppUsage
    public abstract void freeStorageAndNotify(String var1, long var2, IPackageDataObserver var4);

    public abstract Drawable getActivityBanner(ComponentName var1) throws NameNotFoundException;

    public abstract Drawable getActivityBanner(Intent var1) throws NameNotFoundException;

    public abstract Drawable getActivityIcon(ComponentName var1) throws NameNotFoundException;

    public abstract Drawable getActivityIcon(Intent var1) throws NameNotFoundException;

    public abstract ActivityInfo getActivityInfo(ComponentName var1, int var2) throws NameNotFoundException;

    public abstract Drawable getActivityLogo(ComponentName var1) throws NameNotFoundException;

    public abstract Drawable getActivityLogo(Intent var1) throws NameNotFoundException;

    @SystemApi
    public abstract List<IntentFilter> getAllIntentFilters(String var1);

    public abstract List<PermissionGroupInfo> getAllPermissionGroups(int var1);

    public String getAppPredictionServicePackageName() {
        throw new UnsupportedOperationException("getAppPredictionServicePackageName not implemented in subclass");
    }

    public abstract Drawable getApplicationBanner(ApplicationInfo var1);

    public abstract Drawable getApplicationBanner(String var1) throws NameNotFoundException;

    public abstract int getApplicationEnabledSetting(String var1);

    @UnsupportedAppUsage
    public abstract boolean getApplicationHiddenSettingAsUser(String var1, UserHandle var2);

    public abstract Drawable getApplicationIcon(ApplicationInfo var1);

    public abstract Drawable getApplicationIcon(String var1) throws NameNotFoundException;

    public abstract ApplicationInfo getApplicationInfo(String var1, int var2) throws NameNotFoundException;

    @UnsupportedAppUsage
    public abstract ApplicationInfo getApplicationInfoAsUser(String var1, int var2, int var3) throws NameNotFoundException;

    @SystemApi
    public ApplicationInfo getApplicationInfoAsUser(String string2, int n, UserHandle userHandle) throws NameNotFoundException {
        return this.getApplicationInfoAsUser(string2, n, userHandle.getIdentifier());
    }

    public abstract CharSequence getApplicationLabel(ApplicationInfo var1);

    public abstract Drawable getApplicationLogo(ApplicationInfo var1);

    public abstract Drawable getApplicationLogo(String var1) throws NameNotFoundException;

    @SystemApi
    public ArtManager getArtManager() {
        throw new UnsupportedOperationException("getArtManager not implemented in subclass");
    }

    public String getAttentionServicePackageName() {
        throw new UnsupportedOperationException("getAttentionServicePackageName not implemented in subclass");
    }

    public abstract Intent getCarLaunchIntentForPackage(String var1);

    public abstract ChangedPackages getChangedPackages(int var1);

    public abstract int getComponentEnabledSetting(ComponentName var1);

    @SystemApi
    public List<SharedLibraryInfo> getDeclaredSharedLibraries(String string2, int n) {
        throw new UnsupportedOperationException("getDeclaredSharedLibraries() not implemented in subclass");
    }

    public abstract Drawable getDefaultActivityIcon();

    @SystemApi
    public abstract String getDefaultBrowserPackageNameAsUser(int var1);

    public abstract Drawable getDrawable(String var1, int var2, ApplicationInfo var3);

    @SystemApi
    public CharSequence getHarmfulAppWarning(String string2) {
        throw new UnsupportedOperationException("getHarmfulAppWarning not implemented in subclass");
    }

    @UnsupportedAppUsage
    public abstract ComponentName getHomeActivities(List<ResolveInfo> var1);

    @SystemApi
    public String getIncidentReportApproverPackageName() {
        throw new UnsupportedOperationException("getIncidentReportApproverPackageName not implemented in subclass");
    }

    public abstract int getInstallReason(String var1, UserHandle var2);

    public abstract List<ApplicationInfo> getInstalledApplications(int var1);

    public abstract List<ApplicationInfo> getInstalledApplicationsAsUser(int var1, int var2);

    public List<ModuleInfo> getInstalledModules(int n) {
        throw new UnsupportedOperationException("getInstalledModules not implemented in subclass");
    }

    public abstract List<PackageInfo> getInstalledPackages(int var1);

    @SystemApi
    public abstract List<PackageInfo> getInstalledPackagesAsUser(int var1, int var2);

    public abstract String getInstallerPackageName(String var1);

    public abstract String getInstantAppAndroidId(String var1, UserHandle var2);

    public abstract byte[] getInstantAppCookie();

    public abstract int getInstantAppCookieMaxBytes();

    public abstract int getInstantAppCookieMaxSize();

    @SystemApi
    public abstract Drawable getInstantAppIcon(String var1);

    @SystemApi
    public abstract ComponentName getInstantAppInstallerComponent();

    @SystemApi
    public abstract ComponentName getInstantAppResolverSettingsComponent();

    @SystemApi
    public abstract List<InstantAppInfo> getInstantApps();

    public abstract InstrumentationInfo getInstrumentationInfo(ComponentName var1, int var2) throws NameNotFoundException;

    @SystemApi
    public abstract List<IntentFilterVerificationInfo> getIntentFilterVerifications(String var1);

    @SystemApi
    public abstract int getIntentVerificationStatusAsUser(String var1, int var2);

    @UnsupportedAppUsage
    public abstract KeySet getKeySetByAlias(String var1, String var2);

    public abstract Intent getLaunchIntentForPackage(String var1);

    public abstract Intent getLeanbackLaunchIntentForPackage(String var1);

    public ModuleInfo getModuleInfo(String string2, int n) throws NameNotFoundException {
        throw new UnsupportedOperationException("getModuleInfo not implemented in subclass");
    }

    @UnsupportedAppUsage
    public abstract int getMoveStatus(int var1);

    public abstract String getNameForUid(int var1);

    public abstract String[] getNamesForUids(int[] var1);

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public PackageInfo getPackageArchiveInfo(String object, int n) {
        Object object2 = new PackageParser();
        ((PackageParser)object2).setCallback(new PackageParser.CallbackImpl(this));
        object = new File((String)object);
        if ((n & 786432) == 0) {
            n |= 786432;
        }
        try {
            object = ((PackageParser)object2).parseMonolithicPackage((File)object, 0);
            if ((n & 64) != 0) {
                PackageParser.collectCertificates((PackageParser.Package)object, false);
            }
            object2 = new PackageUserState();
            return PackageParser.generatePackageInfo((PackageParser.Package)object, null, n, 0L, 0L, null, (PackageUserState)object2);
        }
        catch (PackageParser.PackageParserException packageParserException) {
            return null;
        }
    }

    @UnsupportedAppUsage
    public abstract List<VolumeInfo> getPackageCandidateVolumes(ApplicationInfo var1);

    @UnsupportedAppUsage
    public abstract VolumeInfo getPackageCurrentVolume(ApplicationInfo var1);

    public abstract int[] getPackageGids(String var1) throws NameNotFoundException;

    public abstract int[] getPackageGids(String var1, int var2) throws NameNotFoundException;

    public abstract PackageInfo getPackageInfo(VersionedPackage var1, int var2) throws NameNotFoundException;

    public abstract PackageInfo getPackageInfo(String var1, int var2) throws NameNotFoundException;

    @UnsupportedAppUsage
    public abstract PackageInfo getPackageInfoAsUser(String var1, int var2, int var3) throws NameNotFoundException;

    public abstract PackageInstaller getPackageInstaller();

    @Deprecated
    @UnsupportedAppUsage
    public void getPackageSizeInfo(String string2, IPackageStatsObserver iPackageStatsObserver) {
        this.getPackageSizeInfoAsUser(string2, this.getUserId(), iPackageStatsObserver);
    }

    @Deprecated
    @UnsupportedAppUsage
    public abstract void getPackageSizeInfoAsUser(String var1, int var2, IPackageStatsObserver var3);

    public abstract int getPackageUid(String var1, int var2) throws NameNotFoundException;

    @UnsupportedAppUsage
    public abstract int getPackageUidAsUser(String var1, int var2) throws NameNotFoundException;

    @UnsupportedAppUsage
    public abstract int getPackageUidAsUser(String var1, int var2, int var3) throws NameNotFoundException;

    public abstract String[] getPackagesForUid(int var1);

    public abstract List<PackageInfo> getPackagesHoldingPermissions(String[] var1, int var2);

    public abstract String getPermissionControllerPackageName();

    @SystemApi
    public abstract int getPermissionFlags(String var1, String var2, UserHandle var3);

    public abstract PermissionGroupInfo getPermissionGroupInfo(String var1, int var2) throws NameNotFoundException;

    public abstract PermissionInfo getPermissionInfo(String var1, int var2) throws NameNotFoundException;

    @Deprecated
    public abstract int getPreferredActivities(List<IntentFilter> var1, List<ComponentName> var2, String var3);

    @Deprecated
    public abstract List<PackageInfo> getPreferredPackages(int var1);

    public abstract List<VolumeInfo> getPrimaryStorageCandidateVolumes();

    public abstract VolumeInfo getPrimaryStorageCurrentVolume();

    public abstract ProviderInfo getProviderInfo(ComponentName var1, int var2) throws NameNotFoundException;

    public abstract ActivityInfo getReceiverInfo(ComponentName var1, int var2) throws NameNotFoundException;

    public abstract Resources getResourcesForActivity(ComponentName var1) throws NameNotFoundException;

    public abstract Resources getResourcesForApplication(ApplicationInfo var1) throws NameNotFoundException;

    public abstract Resources getResourcesForApplication(String var1) throws NameNotFoundException;

    @UnsupportedAppUsage
    public abstract Resources getResourcesForApplicationAsUser(String var1, int var2) throws NameNotFoundException;

    public abstract ServiceInfo getServiceInfo(ComponentName var1, int var2) throws NameNotFoundException;

    public abstract String getServicesSystemSharedLibraryPackageName();

    public abstract List<SharedLibraryInfo> getSharedLibraries(int var1);

    public abstract List<SharedLibraryInfo> getSharedLibrariesAsUser(int var1, int var2);

    public abstract String getSharedSystemSharedLibraryPackageName();

    @UnsupportedAppUsage
    public abstract KeySet getSigningKeySet(String var1);

    public Bundle getSuspendedPackageAppExtras() {
        throw new UnsupportedOperationException("getSuspendedPackageAppExtras not implemented");
    }

    public boolean getSyntheticAppDetailsActivityEnabled(String string2) {
        throw new UnsupportedOperationException("getSyntheticAppDetailsActivityEnabled not implemented");
    }

    public abstract FeatureInfo[] getSystemAvailableFeatures();

    public String getSystemCaptionsServicePackageName() {
        throw new UnsupportedOperationException("getSystemCaptionsServicePackageName not implemented in subclass");
    }

    public abstract String[] getSystemSharedLibraryNames();

    public String getSystemTextClassifierPackageName() {
        throw new UnsupportedOperationException("getSystemTextClassifierPackageName not implemented in subclass");
    }

    public abstract CharSequence getText(String var1, int var2, ApplicationInfo var3);

    @UnsupportedAppUsage
    public abstract int getUidForSharedUser(String var1) throws NameNotFoundException;

    @SystemApi
    public String[] getUnsuspendablePackages(String[] arrstring) {
        throw new UnsupportedOperationException("canSuspendPackages not implemented");
    }

    @UnsupportedAppUsage
    public abstract Drawable getUserBadgeForDensity(UserHandle var1, int var2);

    @UnsupportedAppUsage
    public abstract Drawable getUserBadgeForDensityNoBackground(UserHandle var1, int var2);

    public abstract Drawable getUserBadgedDrawableForDensity(Drawable var1, UserHandle var2, Rect var3, int var4);

    public abstract Drawable getUserBadgedIcon(Drawable var1, UserHandle var2);

    public abstract CharSequence getUserBadgedLabel(CharSequence var1, UserHandle var2);

    public int getUserId() {
        return UserHandle.myUserId();
    }

    public abstract VerifierDeviceIdentity getVerifierDeviceIdentity();

    public String getWellbeingPackageName() {
        throw new UnsupportedOperationException("getWellbeingPackageName not implemented in subclass");
    }

    public Set<String> getWhitelistedRestrictedPermissions(String string2, int n) {
        return Collections.emptySet();
    }

    public abstract XmlResourceParser getXml(String var1, int var2, ApplicationInfo var3);

    @SystemApi
    public abstract void grantRuntimePermission(String var1, String var2, UserHandle var3);

    public boolean hasSigningCertificate(int n, byte[] arrby, int n2) {
        throw new UnsupportedOperationException("hasSigningCertificate not implemented in subclass");
    }

    public boolean hasSigningCertificate(String string2, byte[] arrby, int n) {
        throw new UnsupportedOperationException("hasSigningCertificate not implemented in subclass");
    }

    public abstract boolean hasSystemFeature(String var1);

    public abstract boolean hasSystemFeature(String var1, int var2);

    @SystemApi
    @Deprecated
    public abstract int installExistingPackage(String var1) throws NameNotFoundException;

    @SystemApi
    @Deprecated
    public abstract int installExistingPackage(String var1, int var2) throws NameNotFoundException;

    @Deprecated
    @UnsupportedAppUsage
    public abstract int installExistingPackageAsUser(String var1, int var2) throws NameNotFoundException;

    public boolean isDeviceUpgrading() {
        return false;
    }

    public abstract boolean isInstantApp();

    public abstract boolean isInstantApp(String var1);

    @UnsupportedAppUsage
    public abstract boolean isPackageAvailable(String var1);

    public boolean isPackageStateProtected(String string2, int n) {
        throw new UnsupportedOperationException("isPackageStateProtected not implemented in subclass");
    }

    public boolean isPackageSuspended() {
        throw new UnsupportedOperationException("isPackageSuspended not implemented");
    }

    public boolean isPackageSuspended(String string2) throws NameNotFoundException {
        throw new UnsupportedOperationException("isPackageSuspended not implemented");
    }

    @UnsupportedAppUsage
    public abstract boolean isPackageSuspendedForUser(String var1, int var2);

    public abstract boolean isPermissionRevokedByPolicy(String var1, String var2);

    public abstract boolean isSafeMode();

    @UnsupportedAppUsage
    public abstract boolean isSignedBy(String var1, KeySet var2);

    @UnsupportedAppUsage
    public abstract boolean isSignedByExactly(String var1, KeySet var2);

    @UnsupportedAppUsage
    public abstract boolean isUpgrade();

    public abstract boolean isWirelessConsentModeEnabled();

    @UnsupportedAppUsage
    public abstract Drawable loadItemIcon(PackageItemInfo var1, ApplicationInfo var2);

    @UnsupportedAppUsage
    public abstract Drawable loadUnbadgedItemIcon(PackageItemInfo var1, ApplicationInfo var2);

    @UnsupportedAppUsage
    public abstract int movePackage(String var1, VolumeInfo var2);

    public abstract int movePrimaryStorage(VolumeInfo var1);

    public abstract List<ResolveInfo> queryBroadcastReceivers(Intent var1, int var2);

    @Deprecated
    @UnsupportedAppUsage
    public List<ResolveInfo> queryBroadcastReceivers(Intent intent, int n, int n2) {
        if (VMRuntime.getRuntime().getTargetSdkVersion() < 26) {
            Log.d("PackageManager", "Shame on you for calling the hidden API queryBroadcastReceivers(). Shame!");
            return this.queryBroadcastReceiversAsUser(intent, n, n2);
        }
        throw new UnsupportedOperationException("Shame on you for calling the hidden API queryBroadcastReceivers(). Shame!");
    }

    @UnsupportedAppUsage
    public abstract List<ResolveInfo> queryBroadcastReceiversAsUser(Intent var1, int var2, int var3);

    @SystemApi
    public List<ResolveInfo> queryBroadcastReceiversAsUser(Intent intent, int n, UserHandle userHandle) {
        return this.queryBroadcastReceiversAsUser(intent, n, userHandle.getIdentifier());
    }

    public abstract List<ProviderInfo> queryContentProviders(String var1, int var2, int var3);

    public List<ProviderInfo> queryContentProviders(String string2, int n, int n2, String string3) {
        return this.queryContentProviders(string2, n, n2);
    }

    public abstract List<InstrumentationInfo> queryInstrumentation(String var1, int var2);

    public abstract List<ResolveInfo> queryIntentActivities(Intent var1, int var2);

    @UnsupportedAppUsage
    public abstract List<ResolveInfo> queryIntentActivitiesAsUser(Intent var1, int var2, int var3);

    @SystemApi
    public List<ResolveInfo> queryIntentActivitiesAsUser(Intent intent, int n, UserHandle userHandle) {
        return this.queryIntentActivitiesAsUser(intent, n, userHandle.getIdentifier());
    }

    public abstract List<ResolveInfo> queryIntentActivityOptions(ComponentName var1, Intent[] var2, Intent var3, int var4);

    public abstract List<ResolveInfo> queryIntentContentProviders(Intent var1, int var2);

    @UnsupportedAppUsage
    public abstract List<ResolveInfo> queryIntentContentProvidersAsUser(Intent var1, int var2, int var3);

    @SystemApi
    public List<ResolveInfo> queryIntentContentProvidersAsUser(Intent intent, int n, UserHandle userHandle) {
        return this.queryIntentContentProvidersAsUser(intent, n, userHandle.getIdentifier());
    }

    public abstract List<ResolveInfo> queryIntentServices(Intent var1, int var2);

    @UnsupportedAppUsage
    public abstract List<ResolveInfo> queryIntentServicesAsUser(Intent var1, int var2, int var3);

    @SystemApi
    public List<ResolveInfo> queryIntentServicesAsUser(Intent intent, int n, UserHandle userHandle) {
        return this.queryIntentServicesAsUser(intent, n, userHandle.getIdentifier());
    }

    public abstract List<PermissionInfo> queryPermissionsByGroup(String var1, int var2) throws NameNotFoundException;

    @SystemApi
    public abstract void registerDexModule(String var1, DexModuleRegisterCallback var2);

    @UnsupportedAppUsage
    public abstract void registerMoveCallback(MoveCallback var1, Handler var2);

    @SystemApi
    public abstract void removeOnPermissionsChangeListener(OnPermissionsChangedListener var1);

    @Deprecated
    public abstract void removePackageFromPreferred(String var1);

    public abstract void removePermission(String var1);

    public boolean removeWhitelistedRestrictedPermission(String string2, String string3, int n) {
        return false;
    }

    @SystemApi
    public void replacePreferredActivity(IntentFilter intentFilter, int n, List<ComponentName> list, ComponentName componentName) {
        this.replacePreferredActivity(intentFilter, n, list.toArray(new ComponentName[0]), componentName);
    }

    @Deprecated
    @UnsupportedAppUsage
    public abstract void replacePreferredActivity(IntentFilter var1, int var2, ComponentName[] var3, ComponentName var4);

    @Deprecated
    @UnsupportedAppUsage
    public void replacePreferredActivityAsUser(IntentFilter intentFilter, int n, ComponentName[] arrcomponentName, ComponentName componentName, int n2) {
        throw new RuntimeException("Not implemented. Must override in a subclass.");
    }

    public abstract ResolveInfo resolveActivity(Intent var1, int var2);

    @UnsupportedAppUsage
    public abstract ResolveInfo resolveActivityAsUser(Intent var1, int var2, int var3);

    public abstract ProviderInfo resolveContentProvider(String var1, int var2);

    @UnsupportedAppUsage
    public abstract ProviderInfo resolveContentProviderAsUser(String var1, int var2, int var3);

    public abstract ResolveInfo resolveService(Intent var1, int var2);

    public abstract ResolveInfo resolveServiceAsUser(Intent var1, int var2, int var3);

    @SystemApi
    public abstract void revokeRuntimePermission(String var1, String var2, UserHandle var3);

    @SystemApi
    public void sendDeviceCustomizationReadyBroadcast() {
        throw new UnsupportedOperationException("sendDeviceCustomizationReadyBroadcast not implemented in subclass");
    }

    public abstract void setApplicationCategoryHint(String var1, int var2);

    public abstract void setApplicationEnabledSetting(String var1, int var2, int var3);

    @UnsupportedAppUsage
    public abstract boolean setApplicationHiddenSettingAsUser(String var1, boolean var2, UserHandle var3);

    public abstract void setComponentEnabledSetting(ComponentName var1, int var2, int var3);

    @SystemApi
    public abstract boolean setDefaultBrowserPackageNameAsUser(String var1, int var2);

    @SystemApi
    public String[] setDistractingPackageRestrictions(String[] arrstring, int n) {
        throw new UnsupportedOperationException("setDistractingPackageRestrictions not implemented");
    }

    @SystemApi
    public void setHarmfulAppWarning(String string2, CharSequence charSequence) {
        throw new UnsupportedOperationException("setHarmfulAppWarning not implemented in subclass");
    }

    public abstract void setInstallerPackageName(String var1, String var2);

    public abstract boolean setInstantAppCookie(byte[] var1);

    @SystemApi
    public String[] setPackagesSuspended(String[] arrstring, boolean bl, PersistableBundle persistableBundle, PersistableBundle persistableBundle2, SuspendDialogInfo suspendDialogInfo) {
        throw new UnsupportedOperationException("setPackagesSuspended not implemented");
    }

    @SystemApi
    @Deprecated
    public String[] setPackagesSuspended(String[] arrstring, boolean bl, PersistableBundle persistableBundle, PersistableBundle persistableBundle2, String string2) {
        throw new UnsupportedOperationException("setPackagesSuspended not implemented");
    }

    @SystemApi
    public void setSyntheticAppDetailsActivityEnabled(String string2, boolean bl) {
        throw new UnsupportedOperationException("setSyntheticAppDetailsActivityEnabled not implemented");
    }

    @SystemApi
    public abstract void setUpdateAvailable(String var1, boolean var2);

    @UnsupportedAppUsage
    public abstract boolean shouldShowRequestPermissionRationale(String var1);

    @UnsupportedAppUsage
    public abstract void unregisterMoveCallback(MoveCallback var1);

    public abstract void updateInstantAppCookie(byte[] var1);

    @SystemApi
    public abstract boolean updateIntentVerificationStatusAsUser(String var1, int var2, int var3);

    @SystemApi
    public abstract void updatePermissionFlags(String var1, String var2, int var3, int var4, UserHandle var5);

    @SystemApi
    public abstract void verifyIntentFilter(int var1, int var2, List<String> var3);

    public abstract void verifyPendingInstall(int var1, int var2);

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface ApplicationInfoFlags {
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface CertificateInputType {
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface ComponentInfoFlags {
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface DeleteFlags {
    }

    @SystemApi
    public static abstract class DexModuleRegisterCallback {
        public abstract void onDexModuleRegistered(String var1, boolean var2, String var3);
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface DistractionRestriction {
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface EnabledFlags {
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface EnabledState {
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface InstallFlags {
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface InstallReason {
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface InstrumentationInfoFlags {
    }

    public static class LegacyPackageDeleteObserver
    extends PackageDeleteObserver {
        private final IPackageDeleteObserver mLegacy;

        public LegacyPackageDeleteObserver(IPackageDeleteObserver iPackageDeleteObserver) {
            this.mLegacy = iPackageDeleteObserver;
        }

        @Override
        public void onPackageDeleted(String string2, int n, String object) {
            object = this.mLegacy;
            if (object == null) {
                return;
            }
            try {
                object.packageDeleted(string2, n);
            }
            catch (RemoteException remoteException) {
                // empty catch block
            }
        }
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface ModuleInfoFlags {
    }

    public static abstract class MoveCallback {
        public void onCreated(int n, Bundle bundle) {
        }

        public abstract void onStatusChanged(int var1, int var2, long var3);
    }

    public static class NameNotFoundException
    extends AndroidException {
        public NameNotFoundException() {
        }

        public NameNotFoundException(String string2) {
            super(string2);
        }
    }

    @SystemApi
    public static interface OnPermissionsChangedListener {
        public void onPermissionsChanged(int var1);
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface PackageInfoFlags {
    }

    @SystemApi
    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface PermissionFlags {
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface PermissionGroupInfoFlags {
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface PermissionInfoFlags {
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface PermissionResult {
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface PermissionWhitelistFlags {
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface ResolveInfoFlags {
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface SignatureResult {
    }

}

