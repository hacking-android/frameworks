/*
 * Decompiled with CFR 0.145.
 */
package android;

import android.annotation.SystemApi;

public final class Manifest {

    public static final class permission {
        public static final String ACCEPT_HANDOVER = "android.permission.ACCEPT_HANDOVER";
        @SystemApi
        public static final String ACCESS_AMBIENT_LIGHT_STATS = "android.permission.ACCESS_AMBIENT_LIGHT_STATS";
        public static final String ACCESS_BACKGROUND_LOCATION = "android.permission.ACCESS_BACKGROUND_LOCATION";
        @SystemApi
        public static final String ACCESS_BROADCAST_RADIO = "android.permission.ACCESS_BROADCAST_RADIO";
        @SystemApi
        public static final String ACCESS_CACHE_FILESYSTEM = "android.permission.ACCESS_CACHE_FILESYSTEM";
        public static final String ACCESS_CHECKIN_PROPERTIES = "android.permission.ACCESS_CHECKIN_PROPERTIES";
        public static final String ACCESS_COARSE_LOCATION = "android.permission.ACCESS_COARSE_LOCATION";
        public static final String ACCESS_CONTENT_PROVIDERS_EXTERNALLY = "android.permission.ACCESS_CONTENT_PROVIDERS_EXTERNALLY";
        @SystemApi
        public static final String ACCESS_DRM_CERTIFICATES = "android.permission.ACCESS_DRM_CERTIFICATES";
        public static final String ACCESS_FINE_LOCATION = "android.permission.ACCESS_FINE_LOCATION";
        @SystemApi
        @Deprecated
        public static final String ACCESS_FM_RADIO = "android.permission.ACCESS_FM_RADIO";
        public static final String ACCESS_IMS_CALL_SERVICE = "android.permission.ACCESS_IMS_CALL_SERVICE";
        public static final String ACCESS_INPUT_FLINGER = "android.permission.ACCESS_INPUT_FLINGER";
        @SystemApi
        public static final String ACCESS_INSTANT_APPS = "android.permission.ACCESS_INSTANT_APPS";
        public static final String ACCESS_KEYGUARD_SECURE_STORAGE = "android.permission.ACCESS_KEYGUARD_SECURE_STORAGE";
        public static final String ACCESS_LOCATION_EXTRA_COMMANDS = "android.permission.ACCESS_LOCATION_EXTRA_COMMANDS";
        public static final String ACCESS_LOWPAN_STATE = "android.permission.ACCESS_LOWPAN_STATE";
        public static final String ACCESS_MEDIA_LOCATION = "android.permission.ACCESS_MEDIA_LOCATION";
        @SystemApi
        public static final String ACCESS_MOCK_LOCATION = "android.permission.ACCESS_MOCK_LOCATION";
        @SystemApi
        public static final String ACCESS_MTP = "android.permission.ACCESS_MTP";
        @SystemApi
        public static final String ACCESS_NETWORK_CONDITIONS = "android.permission.ACCESS_NETWORK_CONDITIONS";
        public static final String ACCESS_NETWORK_STATE = "android.permission.ACCESS_NETWORK_STATE";
        @SystemApi
        public static final String ACCESS_NOTIFICATIONS = "android.permission.ACCESS_NOTIFICATIONS";
        public static final String ACCESS_NOTIFICATION_POLICY = "android.permission.ACCESS_NOTIFICATION_POLICY";
        public static final String ACCESS_PDB_STATE = "android.permission.ACCESS_PDB_STATE";
        @SystemApi
        public static final String ACCESS_SHARED_LIBRARIES = "android.permission.ACCESS_SHARED_LIBRARIES";
        @SystemApi
        public static final String ACCESS_SHORTCUTS = "android.permission.ACCESS_SHORTCUTS";
        @SystemApi
        public static final String ACCESS_SURFACE_FLINGER = "android.permission.ACCESS_SURFACE_FLINGER";
        public static final String ACCESS_UCE_OPTIONS_SERVICE = "android.permission.ACCESS_UCE_OPTIONS_SERVICE";
        public static final String ACCESS_UCE_PRESENCE_SERVICE = "android.permission.ACCESS_UCE_PRESENCE_SERVICE";
        public static final String ACCESS_VOICE_INTERACTION_SERVICE = "android.permission.ACCESS_VOICE_INTERACTION_SERVICE";
        public static final String ACCESS_VR_MANAGER = "android.permission.ACCESS_VR_MANAGER";
        public static final String ACCESS_VR_STATE = "android.permission.ACCESS_VR_STATE";
        public static final String ACCESS_WIFI_STATE = "android.permission.ACCESS_WIFI_STATE";
        public static final String ACCESS_WIMAX_STATE = "android.permission.ACCESS_WIMAX_STATE";
        public static final String ACCOUNT_MANAGER = "android.permission.ACCOUNT_MANAGER";
        @SystemApi
        public static final String ACTIVITY_EMBEDDING = "android.permission.ACTIVITY_EMBEDDING";
        public static final String ACTIVITY_RECOGNITION = "android.permission.ACTIVITY_RECOGNITION";
        public static final String ADD_VOICEMAIL = "com.android.voicemail.permission.ADD_VOICEMAIL";
        @SystemApi
        public static final String ADJUST_RUNTIME_PERMISSIONS_POLICY = "android.permission.ADJUST_RUNTIME_PERMISSIONS_POLICY";
        @SystemApi
        public static final String ALLOCATE_AGGRESSIVE = "android.permission.ALLOCATE_AGGRESSIVE";
        @SystemApi
        public static final String ALLOW_ANY_CODEC_FOR_PLAYBACK = "android.permission.ALLOW_ANY_CODEC_FOR_PLAYBACK";
        @SystemApi
        public static final String AMBIENT_WALLPAPER = "android.permission.AMBIENT_WALLPAPER";
        public static final String ANSWER_PHONE_CALLS = "android.permission.ANSWER_PHONE_CALLS";
        @SystemApi
        public static final String APPROVE_INCIDENT_REPORTS = "android.permission.APPROVE_INCIDENT_REPORTS";
        public static final String ASEC_ACCESS = "android.permission.ASEC_ACCESS";
        public static final String ASEC_CREATE = "android.permission.ASEC_CREATE";
        public static final String ASEC_DESTROY = "android.permission.ASEC_DESTROY";
        public static final String ASEC_MOUNT_UNMOUNT = "android.permission.ASEC_MOUNT_UNMOUNT";
        public static final String ASEC_RENAME = "android.permission.ASEC_RENAME";
        public static final String AUTHENTICATE_ACCOUNTS = "android.permission.AUTHENTICATE_ACCOUNTS";
        @SystemApi
        public static final String BACKUP = "android.permission.BACKUP";
        public static final String BATTERY_STATS = "android.permission.BATTERY_STATS";
        public static final String BIND_ACCESSIBILITY_SERVICE = "android.permission.BIND_ACCESSIBILITY_SERVICE";
        public static final String BIND_APPWIDGET = "android.permission.BIND_APPWIDGET";
        @SystemApi
        public static final String BIND_ATTENTION_SERVICE = "android.permission.BIND_ATTENTION_SERVICE";
        @SystemApi
        public static final String BIND_AUGMENTED_AUTOFILL_SERVICE = "android.permission.BIND_AUGMENTED_AUTOFILL_SERVICE";
        public static final String BIND_AUTOFILL = "android.permission.BIND_AUTOFILL";
        public static final String BIND_AUTOFILL_FIELD_CLASSIFICATION_SERVICE = "android.permission.BIND_AUTOFILL_FIELD_CLASSIFICATION_SERVICE";
        public static final String BIND_AUTOFILL_SERVICE = "android.permission.BIND_AUTOFILL_SERVICE";
        public static final String BIND_CACHE_QUOTA_SERVICE = "android.permission.BIND_CACHE_QUOTA_SERVICE";
        public static final String BIND_CALL_REDIRECTION_SERVICE = "android.permission.BIND_CALL_REDIRECTION_SERVICE";
        public static final String BIND_CARRIER_MESSAGING_CLIENT_SERVICE = "android.permission.BIND_CARRIER_MESSAGING_CLIENT_SERVICE";
        @Deprecated
        public static final String BIND_CARRIER_MESSAGING_SERVICE = "android.permission.BIND_CARRIER_MESSAGING_SERVICE";
        public static final String BIND_CARRIER_SERVICES = "android.permission.BIND_CARRIER_SERVICES";
        public static final String BIND_CHOOSER_TARGET_SERVICE = "android.permission.BIND_CHOOSER_TARGET_SERVICE";
        public static final String BIND_COMPANION_DEVICE_MANAGER_SERVICE = "android.permission.BIND_COMPANION_DEVICE_MANAGER_SERVICE";
        public static final String BIND_CONDITION_PROVIDER_SERVICE = "android.permission.BIND_CONDITION_PROVIDER_SERVICE";
        @SystemApi
        @Deprecated
        public static final String BIND_CONNECTION_SERVICE = "android.permission.BIND_CONNECTION_SERVICE";
        @SystemApi
        public static final String BIND_CONTENT_CAPTURE_SERVICE = "android.permission.BIND_CONTENT_CAPTURE_SERVICE";
        @SystemApi
        public static final String BIND_CONTENT_SUGGESTIONS_SERVICE = "android.permission.BIND_CONTENT_SUGGESTIONS_SERVICE";
        public static final String BIND_DEVICE_ADMIN = "android.permission.BIND_DEVICE_ADMIN";
        @SystemApi
        public static final String BIND_DIRECTORY_SEARCH = "android.permission.BIND_DIRECTORY_SEARCH";
        public static final String BIND_DREAM_SERVICE = "android.permission.BIND_DREAM_SERVICE";
        @SystemApi
        public static final String BIND_EUICC_SERVICE = "android.permission.BIND_EUICC_SERVICE";
        public static final String BIND_EXPLICIT_HEALTH_CHECK_SERVICE = "android.permission.BIND_EXPLICIT_HEALTH_CHECK_SERVICE";
        public static final String BIND_FINANCIAL_SMS_SERVICE = "android.permission.BIND_FINANCIAL_SMS_SERVICE";
        @SystemApi
        public static final String BIND_IMS_SERVICE = "android.permission.BIND_IMS_SERVICE";
        public static final String BIND_INCALL_SERVICE = "android.permission.BIND_INCALL_SERVICE";
        public static final String BIND_INPUT_METHOD = "android.permission.BIND_INPUT_METHOD";
        public static final String BIND_INTENT_FILTER_VERIFIER = "android.permission.BIND_INTENT_FILTER_VERIFIER";
        public static final String BIND_JOB_SERVICE = "android.permission.BIND_JOB_SERVICE";
        @SystemApi
        public static final String BIND_KEYGUARD_APPWIDGET = "android.permission.BIND_KEYGUARD_APPWIDGET";
        public static final String BIND_MIDI_DEVICE_SERVICE = "android.permission.BIND_MIDI_DEVICE_SERVICE";
        @SystemApi
        public static final String BIND_NETWORK_RECOMMENDATION_SERVICE = "android.permission.BIND_NETWORK_RECOMMENDATION_SERVICE";
        public static final String BIND_NFC_SERVICE = "android.permission.BIND_NFC_SERVICE";
        @SystemApi
        public static final String BIND_NOTIFICATION_ASSISTANT_SERVICE = "android.permission.BIND_NOTIFICATION_ASSISTANT_SERVICE";
        public static final String BIND_NOTIFICATION_LISTENER_SERVICE = "android.permission.BIND_NOTIFICATION_LISTENER_SERVICE";
        public static final String BIND_PACKAGE_VERIFIER = "android.permission.BIND_PACKAGE_VERIFIER";
        @SystemApi
        public static final String BIND_PHONE_ACCOUNT_SUGGESTION_SERVICE = "android.permission.BIND_PHONE_ACCOUNT_SUGGESTION_SERVICE";
        @SystemApi
        public static final String BIND_PRINT_RECOMMENDATION_SERVICE = "android.permission.BIND_PRINT_RECOMMENDATION_SERVICE";
        public static final String BIND_PRINT_SERVICE = "android.permission.BIND_PRINT_SERVICE";
        public static final String BIND_PRINT_SPOOLER_SERVICE = "android.permission.BIND_PRINT_SPOOLER_SERVICE";
        public static final String BIND_QUICK_SETTINGS_TILE = "android.permission.BIND_QUICK_SETTINGS_TILE";
        public static final String BIND_REMOTEVIEWS = "android.permission.BIND_REMOTEVIEWS";
        public static final String BIND_REMOTE_DISPLAY = "android.permission.BIND_REMOTE_DISPLAY";
        @SystemApi
        public static final String BIND_RESOLVER_RANKER_SERVICE = "android.permission.BIND_RESOLVER_RANKER_SERVICE";
        public static final String BIND_ROUTE_PROVIDER = "android.permission.BIND_ROUTE_PROVIDER";
        @SystemApi
        public static final String BIND_RUNTIME_PERMISSION_PRESENTER_SERVICE = "android.permission.BIND_RUNTIME_PERMISSION_PRESENTER_SERVICE";
        public static final String BIND_SCREENING_SERVICE = "android.permission.BIND_SCREENING_SERVICE";
        @SystemApi
        public static final String BIND_SETTINGS_SUGGESTIONS_SERVICE = "android.permission.BIND_SETTINGS_SUGGESTIONS_SERVICE";
        @SystemApi
        public static final String BIND_SOUND_TRIGGER_DETECTION_SERVICE = "android.permission.BIND_SOUND_TRIGGER_DETECTION_SERVICE";
        public static final String BIND_TELECOM_CONNECTION_SERVICE = "android.permission.BIND_TELECOM_CONNECTION_SERVICE";
        @SystemApi
        public static final String BIND_TELEPHONY_DATA_SERVICE = "android.permission.BIND_TELEPHONY_DATA_SERVICE";
        @SystemApi
        public static final String BIND_TELEPHONY_NETWORK_SERVICE = "android.permission.BIND_TELEPHONY_NETWORK_SERVICE";
        @SystemApi
        public static final String BIND_TEXTCLASSIFIER_SERVICE = "android.permission.BIND_TEXTCLASSIFIER_SERVICE";
        public static final String BIND_TEXT_SERVICE = "android.permission.BIND_TEXT_SERVICE";
        @SystemApi
        public static final String BIND_TRUST_AGENT = "android.permission.BIND_TRUST_AGENT";
        public static final String BIND_TV_INPUT = "android.permission.BIND_TV_INPUT";
        @SystemApi
        public static final String BIND_TV_REMOTE_SERVICE = "android.permission.BIND_TV_REMOTE_SERVICE";
        public static final String BIND_VISUAL_VOICEMAIL_SERVICE = "android.permission.BIND_VISUAL_VOICEMAIL_SERVICE";
        public static final String BIND_VOICE_INTERACTION = "android.permission.BIND_VOICE_INTERACTION";
        public static final String BIND_VPN_SERVICE = "android.permission.BIND_VPN_SERVICE";
        public static final String BIND_VR_LISTENER_SERVICE = "android.permission.BIND_VR_LISTENER_SERVICE";
        public static final String BIND_WALLPAPER = "android.permission.BIND_WALLPAPER";
        public static final String BLUETOOTH = "android.permission.BLUETOOTH";
        public static final String BLUETOOTH_ADMIN = "android.permission.BLUETOOTH_ADMIN";
        public static final String BLUETOOTH_MAP = "android.permission.BLUETOOTH_MAP";
        public static final String BLUETOOTH_PRIVILEGED = "android.permission.BLUETOOTH_PRIVILEGED";
        public static final String BLUETOOTH_STACK = "android.permission.BLUETOOTH_STACK";
        public static final String BODY_SENSORS = "android.permission.BODY_SENSORS";
        @SystemApi
        public static final String BRICK = "android.permission.BRICK";
        @SystemApi
        public static final String BRIGHTNESS_SLIDER_USAGE = "android.permission.BRIGHTNESS_SLIDER_USAGE";
        @SystemApi
        @Deprecated
        public static final String BROADCAST_NETWORK_PRIVILEGED = "android.permission.BROADCAST_NETWORK_PRIVILEGED";
        public static final String BROADCAST_PACKAGE_REMOVED = "android.permission.BROADCAST_PACKAGE_REMOVED";
        public static final String BROADCAST_SMS = "android.permission.BROADCAST_SMS";
        public static final String BROADCAST_STICKY = "android.permission.BROADCAST_STICKY";
        public static final String BROADCAST_WAP_PUSH = "android.permission.BROADCAST_WAP_PUSH";
        public static final String C2D_MESSAGE = "android.intent.category.MASTER_CLEAR.permission.C2D_MESSAGE";
        public static final String CACHE_CONTENT = "android.permission.CACHE_CONTENT";
        public static final String CALL_COMPANION_APP = "android.permission.CALL_COMPANION_APP";
        public static final String CALL_PHONE = "android.permission.CALL_PHONE";
        public static final String CALL_PRIVILEGED = "android.permission.CALL_PRIVILEGED";
        public static final String CAMERA = "android.permission.CAMERA";
        @SystemApi
        public static final String CAMERA_DISABLE_TRANSMIT_LED = "android.permission.CAMERA_DISABLE_TRANSMIT_LED";
        public static final String CAMERA_SEND_SYSTEM_EVENTS = "android.permission.CAMERA_SEND_SYSTEM_EVENTS";
        @SystemApi
        public static final String CAPTURE_AUDIO_HOTWORD = "android.permission.CAPTURE_AUDIO_HOTWORD";
        public static final String CAPTURE_AUDIO_OUTPUT = "android.permission.CAPTURE_AUDIO_OUTPUT";
        @SystemApi
        public static final String CAPTURE_MEDIA_OUTPUT = "android.permission.CAPTURE_MEDIA_OUTPUT";
        public static final String CAPTURE_SECURE_VIDEO_OUTPUT = "android.permission.CAPTURE_SECURE_VIDEO_OUTPUT";
        @SystemApi
        public static final String CAPTURE_TV_INPUT = "android.permission.CAPTURE_TV_INPUT";
        public static final String CAPTURE_VIDEO_OUTPUT = "android.permission.CAPTURE_VIDEO_OUTPUT";
        public static final String CARRIER_FILTER_SMS = "android.permission.CARRIER_FILTER_SMS";
        public static final String CHANGE_ACCESSIBILITY_VOLUME = "android.permission.CHANGE_ACCESSIBILITY_VOLUME";
        @SystemApi
        public static final String CHANGE_APP_IDLE_STATE = "android.permission.CHANGE_APP_IDLE_STATE";
        public static final String CHANGE_BACKGROUND_DATA_SETTING = "android.permission.CHANGE_BACKGROUND_DATA_SETTING";
        public static final String CHANGE_COMPONENT_ENABLED_STATE = "android.permission.CHANGE_COMPONENT_ENABLED_STATE";
        public static final String CHANGE_CONFIGURATION = "android.permission.CHANGE_CONFIGURATION";
        @SystemApi
        public static final String CHANGE_DEVICE_IDLE_TEMP_WHITELIST = "android.permission.CHANGE_DEVICE_IDLE_TEMP_WHITELIST";
        public static final String CHANGE_HDMI_CEC_ACTIVE_SOURCE = "android.permission.CHANGE_HDMI_CEC_ACTIVE_SOURCE";
        public static final String CHANGE_LOWPAN_STATE = "android.permission.CHANGE_LOWPAN_STATE";
        public static final String CHANGE_NETWORK_STATE = "android.permission.CHANGE_NETWORK_STATE";
        public static final String CHANGE_OVERLAY_PACKAGES = "android.permission.CHANGE_OVERLAY_PACKAGES";
        public static final String CHANGE_WIFI_MULTICAST_STATE = "android.permission.CHANGE_WIFI_MULTICAST_STATE";
        public static final String CHANGE_WIFI_STATE = "android.permission.CHANGE_WIFI_STATE";
        public static final String CHANGE_WIMAX_STATE = "android.permission.CHANGE_WIMAX_STATE";
        public static final String CLEAR_APP_CACHE = "android.permission.CLEAR_APP_CACHE";
        public static final String CLEAR_APP_GRANTED_URI_PERMISSIONS = "android.permission.CLEAR_APP_GRANTED_URI_PERMISSIONS";
        @SystemApi
        public static final String CLEAR_APP_USER_DATA = "android.permission.CLEAR_APP_USER_DATA";
        @SystemApi
        public static final String CONFIGURE_DISPLAY_BRIGHTNESS = "android.permission.CONFIGURE_DISPLAY_BRIGHTNESS";
        public static final String CONFIGURE_DISPLAY_COLOR_MODE = "android.permission.CONFIGURE_DISPLAY_COLOR_MODE";
        public static final String CONFIGURE_WIFI_DISPLAY = "android.permission.CONFIGURE_WIFI_DISPLAY";
        public static final String CONFIRM_FULL_BACKUP = "android.permission.CONFIRM_FULL_BACKUP";
        @SystemApi
        public static final String CONNECTIVITY_INTERNAL = "android.permission.CONNECTIVITY_INTERNAL";
        @SystemApi
        public static final String CONNECTIVITY_USE_RESTRICTED_NETWORKS = "android.permission.CONNECTIVITY_USE_RESTRICTED_NETWORKS";
        public static final String CONTROL_ALWAYS_ON_VPN = "android.permission.CONTROL_ALWAYS_ON_VPN";
        public static final String CONTROL_DISPLAY_BRIGHTNESS = "android.permission.CONTROL_DISPLAY_BRIGHTNESS";
        @SystemApi
        public static final String CONTROL_DISPLAY_COLOR_TRANSFORMS = "android.permission.CONTROL_DISPLAY_COLOR_TRANSFORMS";
        @SystemApi
        public static final String CONTROL_DISPLAY_SATURATION = "android.permission.CONTROL_DISPLAY_SATURATION";
        @SystemApi
        public static final String CONTROL_INCALL_EXPERIENCE = "android.permission.CONTROL_INCALL_EXPERIENCE";
        public static final String CONTROL_KEYGUARD = "android.permission.CONTROL_KEYGUARD";
        @SystemApi
        public static final String CONTROL_KEYGUARD_SECURE_NOTIFICATIONS = "android.permission.CONTROL_KEYGUARD_SECURE_NOTIFICATIONS";
        public static final String CONTROL_LOCATION_UPDATES = "android.permission.CONTROL_LOCATION_UPDATES";
        public static final String CONTROL_REMOTE_APP_TRANSITION_ANIMATIONS = "android.permission.CONTROL_REMOTE_APP_TRANSITION_ANIMATIONS";
        @SystemApi
        public static final String CONTROL_VPN = "android.permission.CONTROL_VPN";
        public static final String CONTROL_WIFI_DISPLAY = "android.permission.CONTROL_WIFI_DISPLAY";
        public static final String COPY_PROTECTED_DATA = "android.permission.COPY_PROTECTED_DATA";
        public static final String CREATE_USERS = "android.permission.CREATE_USERS";
        @SystemApi
        public static final String CRYPT_KEEPER = "android.permission.CRYPT_KEEPER";
        public static final String DELETE_CACHE_FILES = "android.permission.DELETE_CACHE_FILES";
        public static final String DELETE_PACKAGES = "android.permission.DELETE_PACKAGES";
        @SystemApi
        public static final String DEVICE_POWER = "android.permission.DEVICE_POWER";
        public static final String DIAGNOSTIC = "android.permission.DIAGNOSTIC";
        public static final String DISABLE_HIDDEN_API_CHECKS = "android.permission.DISABLE_HIDDEN_API_CHECKS";
        public static final String DISABLE_INPUT_DEVICE = "android.permission.DISABLE_INPUT_DEVICE";
        public static final String DISABLE_KEYGUARD = "android.permission.DISABLE_KEYGUARD";
        public static final String DISPATCH_NFC_MESSAGE = "android.permission.DISPATCH_NFC_MESSAGE";
        @SystemApi
        public static final String DISPATCH_PROVISIONING_MESSAGE = "android.permission.DISPATCH_PROVISIONING_MESSAGE";
        public static final String DUMP = "android.permission.DUMP";
        public static final String DVB_DEVICE = "android.permission.DVB_DEVICE";
        public static final String ENABLE_TEST_HARNESS_MODE = "android.permission.ENABLE_TEST_HARNESS_MODE";
        public static final String EXPAND_STATUS_BAR = "android.permission.EXPAND_STATUS_BAR";
        public static final String FACTORY_TEST = "android.permission.FACTORY_TEST";
        public static final String FILTER_EVENTS = "android.permission.FILTER_EVENTS";
        public static final String FLASHLIGHT = "android.permission.FLASHLIGHT";
        @SystemApi
        public static final String FORCE_BACK = "android.permission.FORCE_BACK";
        public static final String FORCE_PERSISTABLE_URI_PERMISSIONS = "android.permission.FORCE_PERSISTABLE_URI_PERMISSIONS";
        @SystemApi
        public static final String FORCE_STOP_PACKAGES = "android.permission.FORCE_STOP_PACKAGES";
        public static final String FOREGROUND_SERVICE = "android.permission.FOREGROUND_SERVICE";
        public static final String FRAME_STATS = "android.permission.FRAME_STATS";
        public static final String FREEZE_SCREEN = "android.permission.FREEZE_SCREEN";
        public static final String GET_ACCOUNTS = "android.permission.GET_ACCOUNTS";
        public static final String GET_ACCOUNTS_PRIVILEGED = "android.permission.GET_ACCOUNTS_PRIVILEGED";
        public static final String GET_APP_GRANTED_URI_PERMISSIONS = "android.permission.GET_APP_GRANTED_URI_PERMISSIONS";
        @SystemApi
        public static final String GET_APP_OPS_STATS = "android.permission.GET_APP_OPS_STATS";
        public static final String GET_DETAILED_TASKS = "android.permission.GET_DETAILED_TASKS";
        public static final String GET_INTENT_SENDER_INTENT = "android.permission.GET_INTENT_SENDER_INTENT";
        public static final String GET_PACKAGE_SIZE = "android.permission.GET_PACKAGE_SIZE";
        public static final String GET_PASSWORD = "android.permission.GET_PASSWORD";
        @SystemApi
        public static final String GET_PROCESS_STATE_AND_OOM_SCORE = "android.permission.GET_PROCESS_STATE_AND_OOM_SCORE";
        @SystemApi
        public static final String GET_RUNTIME_PERMISSIONS = "android.permission.GET_RUNTIME_PERMISSIONS";
        @Deprecated
        public static final String GET_TASKS = "android.permission.GET_TASKS";
        @SystemApi
        public static final String GET_TOP_ACTIVITY_INFO = "android.permission.GET_TOP_ACTIVITY_INFO";
        public static final String GLOBAL_SEARCH = "android.permission.GLOBAL_SEARCH";
        public static final String GLOBAL_SEARCH_CONTROL = "android.permission.GLOBAL_SEARCH_CONTROL";
        @SystemApi
        public static final String GRANT_PROFILE_OWNER_DEVICE_IDS_ACCESS = "android.permission.GRANT_PROFILE_OWNER_DEVICE_IDS_ACCESS";
        @SystemApi
        public static final String GRANT_RUNTIME_PERMISSIONS = "android.permission.GRANT_RUNTIME_PERMISSIONS";
        @SystemApi
        public static final String HARDWARE_TEST = "android.permission.HARDWARE_TEST";
        @SystemApi
        public static final String HDMI_CEC = "android.permission.HDMI_CEC";
        @SystemApi
        public static final String HIDE_NON_SYSTEM_OVERLAY_WINDOWS = "android.permission.HIDE_NON_SYSTEM_OVERLAY_WINDOWS";
        @SystemApi
        public static final String INJECT_EVENTS = "android.permission.INJECT_EVENTS";
        @SystemApi
        public static final String INSTALL_DYNAMIC_SYSTEM = "android.permission.INSTALL_DYNAMIC_SYSTEM";
        public static final String INSTALL_EXISTING_PACKAGES = "com.android.permission.INSTALL_EXISTING_PACKAGES";
        @SystemApi
        public static final String INSTALL_GRANT_RUNTIME_PERMISSIONS = "android.permission.INSTALL_GRANT_RUNTIME_PERMISSIONS";
        public static final String INSTALL_LOCATION_PROVIDER = "android.permission.INSTALL_LOCATION_PROVIDER";
        public static final String INSTALL_PACKAGES = "android.permission.INSTALL_PACKAGES";
        @SystemApi
        public static final String INSTALL_PACKAGE_UPDATES = "android.permission.INSTALL_PACKAGE_UPDATES";
        @SystemApi
        public static final String INSTALL_SELF_UPDATES = "android.permission.INSTALL_SELF_UPDATES";
        public static final String INSTALL_SHORTCUT = "com.android.launcher.permission.INSTALL_SHORTCUT";
        public static final String INSTANT_APP_FOREGROUND_SERVICE = "android.permission.INSTANT_APP_FOREGROUND_SERVICE";
        @SystemApi
        public static final String INTENT_FILTER_VERIFICATION_AGENT = "android.permission.INTENT_FILTER_VERIFICATION_AGENT";
        @SystemApi
        public static final String INTERACT_ACROSS_PROFILES = "android.permission.INTERACT_ACROSS_PROFILES";
        @SystemApi
        public static final String INTERACT_ACROSS_USERS = "android.permission.INTERACT_ACROSS_USERS";
        @SystemApi
        public static final String INTERACT_ACROSS_USERS_FULL = "android.permission.INTERACT_ACROSS_USERS_FULL";
        public static final String INTERNAL_DELETE_CACHE_FILES = "android.permission.INTERNAL_DELETE_CACHE_FILES";
        @SystemApi
        public static final String INTERNAL_SYSTEM_WINDOW = "android.permission.INTERNAL_SYSTEM_WINDOW";
        public static final String INTERNET = "android.permission.INTERNET";
        @SystemApi
        public static final String INVOKE_CARRIER_SETUP = "android.permission.INVOKE_CARRIER_SETUP";
        public static final String KILL_BACKGROUND_PROCESSES = "android.permission.KILL_BACKGROUND_PROCESSES";
        @SystemApi
        public static final String KILL_UID = "android.permission.KILL_UID";
        public static final String LAUNCH_TRUST_AGENT_SETTINGS = "android.permission.LAUNCH_TRUST_AGENT_SETTINGS";
        @SystemApi
        public static final String LOCAL_MAC_ADDRESS = "android.permission.LOCAL_MAC_ADDRESS";
        public static final String LOCATION_HARDWARE = "android.permission.LOCATION_HARDWARE";
        @SystemApi
        public static final String LOCK_DEVICE = "android.permission.LOCK_DEVICE";
        @SystemApi
        public static final String LOOP_RADIO = "android.permission.LOOP_RADIO";
        @SystemApi
        public static final String MANAGE_ACCESSIBILITY = "android.permission.MANAGE_ACCESSIBILITY";
        public static final String MANAGE_ACCOUNTS = "android.permission.MANAGE_ACCOUNTS";
        @SystemApi
        public static final String MANAGE_ACTIVITY_STACKS = "android.permission.MANAGE_ACTIVITY_STACKS";
        public static final String MANAGE_APPOPS = "android.permission.MANAGE_APPOPS";
        public static final String MANAGE_APP_OPS_MODES = "android.permission.MANAGE_APP_OPS_MODES";
        @SystemApi
        public static final String MANAGE_APP_OPS_RESTRICTIONS = "android.permission.MANAGE_APP_OPS_RESTRICTIONS";
        @SystemApi
        public static final String MANAGE_APP_PREDICTIONS = "android.permission.MANAGE_APP_PREDICTIONS";
        @SystemApi
        public static final String MANAGE_APP_TOKENS = "android.permission.MANAGE_APP_TOKENS";
        public static final String MANAGE_AUDIO_POLICY = "android.permission.MANAGE_AUDIO_POLICY";
        @SystemApi
        public static final String MANAGE_AUTO_FILL = "android.permission.MANAGE_AUTO_FILL";
        public static final String MANAGE_BIND_INSTANT_SERVICE = "android.permission.MANAGE_BIND_INSTANT_SERVICE";
        public static final String MANAGE_BIOMETRIC = "android.permission.MANAGE_BIOMETRIC";
        public static final String MANAGE_BIOMETRIC_DIALOG = "android.permission.MANAGE_BIOMETRIC_DIALOG";
        public static final String MANAGE_BLUETOOTH_WHEN_WIRELESS_CONSENT_REQUIRED = "android.permission.MANAGE_BLUETOOTH_WHEN_WIRELESS_CONSENT_REQUIRED";
        public static final String MANAGE_CAMERA = "android.permission.MANAGE_CAMERA";
        @SystemApi
        public static final String MANAGE_CARRIER_OEM_UNLOCK_STATE = "android.permission.MANAGE_CARRIER_OEM_UNLOCK_STATE";
        @SystemApi
        public static final String MANAGE_CA_CERTIFICATES = "android.permission.MANAGE_CA_CERTIFICATES";
        @SystemApi
        public static final String MANAGE_CONTENT_CAPTURE = "android.permission.MANAGE_CONTENT_CAPTURE";
        @SystemApi
        public static final String MANAGE_CONTENT_SUGGESTIONS = "android.permission.MANAGE_CONTENT_SUGGESTIONS";
        @SystemApi
        public static final String MANAGE_DEBUGGING = "android.permission.MANAGE_DEBUGGING";
        @SystemApi
        public static final String MANAGE_DEVICE_ADMINS = "android.permission.MANAGE_DEVICE_ADMINS";
        public static final String MANAGE_DOCUMENTS = "android.permission.MANAGE_DOCUMENTS";
        public static final String MANAGE_DYNAMIC_SYSTEM = "android.permission.MANAGE_DYNAMIC_SYSTEM";
        public static final String MANAGE_FINGERPRINT = "android.permission.MANAGE_FINGERPRINT";
        @SystemApi
        public static final String MANAGE_IPSEC_TUNNELS = "android.permission.MANAGE_IPSEC_TUNNELS";
        public static final String MANAGE_LOWPAN_INTERFACES = "android.permission.MANAGE_LOWPAN_INTERFACES";
        public static final String MANAGE_MEDIA_PROJECTION = "android.permission.MANAGE_MEDIA_PROJECTION";
        public static final String MANAGE_NETWORK_POLICY = "android.permission.MANAGE_NETWORK_POLICY";
        public static final String MANAGE_NOTIFICATIONS = "android.permission.MANAGE_NOTIFICATIONS";
        public static final String MANAGE_OWN_CALLS = "android.permission.MANAGE_OWN_CALLS";
        public static final String MANAGE_PROFILE_AND_DEVICE_OWNERS = "android.permission.MANAGE_PROFILE_AND_DEVICE_OWNERS";
        @SystemApi
        public static final String MANAGE_ROLE_HOLDERS = "android.permission.MANAGE_ROLE_HOLDERS";
        @SystemApi
        public static final String MANAGE_ROLLBACKS = "android.permission.MANAGE_ROLLBACKS";
        public static final String MANAGE_SCOPED_ACCESS_DIRECTORY_PERMISSIONS = "android.permission.MANAGE_SCOPED_ACCESS_DIRECTORY_PERMISSIONS";
        public static final String MANAGE_SENSORS = "android.permission.MANAGE_SENSORS";
        @SystemApi
        public static final String MANAGE_SENSOR_PRIVACY = "android.permission.MANAGE_SENSOR_PRIVACY";
        public static final String MANAGE_SLICE_PERMISSIONS = "android.permission.MANAGE_SLICE_PERMISSIONS";
        @SystemApi
        public static final String MANAGE_SOUND_TRIGGER = "android.permission.MANAGE_SOUND_TRIGGER";
        @SystemApi
        public static final String MANAGE_SUBSCRIPTION_PLANS = "android.permission.MANAGE_SUBSCRIPTION_PLANS";
        public static final String MANAGE_TEST_NETWORKS = "android.permission.MANAGE_TEST_NETWORKS";
        @SystemApi
        public static final String MANAGE_USB = "android.permission.MANAGE_USB";
        @SystemApi
        public static final String MANAGE_USERS = "android.permission.MANAGE_USERS";
        @SystemApi
        public static final String MANAGE_USER_OEM_UNLOCK_STATE = "android.permission.MANAGE_USER_OEM_UNLOCK_STATE";
        public static final String MANAGE_VOICE_KEYPHRASES = "android.permission.MANAGE_VOICE_KEYPHRASES";
        public static final String MANAGE_WIFI_WHEN_WIRELESS_CONSENT_REQUIRED = "android.permission.MANAGE_WIFI_WHEN_WIRELESS_CONSENT_REQUIRED";
        public static final String MASTER_CLEAR = "android.permission.MASTER_CLEAR";
        public static final String MEDIA_CONTENT_CONTROL = "android.permission.MEDIA_CONTENT_CONTROL";
        public static final String MODIFY_ACCESSIBILITY_DATA = "android.permission.MODIFY_ACCESSIBILITY_DATA";
        @SystemApi
        public static final String MODIFY_APPWIDGET_BIND_PERMISSIONS = "android.permission.MODIFY_APPWIDGET_BIND_PERMISSIONS";
        @SystemApi
        public static final String MODIFY_AUDIO_ROUTING = "android.permission.MODIFY_AUDIO_ROUTING";
        public static final String MODIFY_AUDIO_SETTINGS = "android.permission.MODIFY_AUDIO_SETTINGS";
        @SystemApi
        public static final String MODIFY_CELL_BROADCASTS = "android.permission.MODIFY_CELL_BROADCASTS";
        @SystemApi
        public static final String MODIFY_DAY_NIGHT_MODE = "android.permission.MODIFY_DAY_NIGHT_MODE";
        public static final String MODIFY_DEFAULT_AUDIO_EFFECTS = "android.permission.MODIFY_DEFAULT_AUDIO_EFFECTS";
        @SystemApi
        @Deprecated
        public static final String MODIFY_NETWORK_ACCOUNTING = "android.permission.MODIFY_NETWORK_ACCOUNTING";
        @SystemApi
        public static final String MODIFY_PARENTAL_CONTROLS = "android.permission.MODIFY_PARENTAL_CONTROLS";
        public static final String MODIFY_PHONE_STATE = "android.permission.MODIFY_PHONE_STATE";
        @SystemApi
        public static final String MODIFY_QUIET_MODE = "android.permission.MODIFY_QUIET_MODE";
        public static final String MODIFY_THEME_OVERLAY = "android.permission.MODIFY_THEME_OVERLAY";
        public static final String MONITOR_DEFAULT_SMS_PACKAGE = "android.permission.MONITOR_DEFAULT_SMS_PACKAGE";
        public static final String MONITOR_INPUT = "android.permission.MONITOR_INPUT";
        public static final String MOUNT_FORMAT_FILESYSTEMS = "android.permission.MOUNT_FORMAT_FILESYSTEMS";
        public static final String MOUNT_UNMOUNT_FILESYSTEMS = "android.permission.MOUNT_UNMOUNT_FILESYSTEMS";
        @SystemApi
        public static final String MOVE_PACKAGE = "android.permission.MOVE_PACKAGE";
        public static final String NETWORK_BYPASS_PRIVATE_DNS = "android.permission.NETWORK_BYPASS_PRIVATE_DNS";
        @SystemApi
        public static final String NETWORK_CARRIER_PROVISIONING = "android.permission.NETWORK_CARRIER_PROVISIONING";
        @SystemApi
        public static final String NETWORK_MANAGED_PROVISIONING = "android.permission.NETWORK_MANAGED_PROVISIONING";
        @SystemApi
        public static final String NETWORK_SCAN = "android.permission.NETWORK_SCAN";
        public static final String NETWORK_SETTINGS = "android.permission.NETWORK_SETTINGS";
        @SystemApi
        public static final String NETWORK_SETUP_WIZARD = "android.permission.NETWORK_SETUP_WIZARD";
        @SystemApi
        public static final String NETWORK_SIGNAL_STRENGTH_WAKEUP = "android.permission.NETWORK_SIGNAL_STRENGTH_WAKEUP";
        public static final String NETWORK_STACK = "android.permission.NETWORK_STACK";
        public static final String NET_ADMIN = "android.permission.NET_ADMIN";
        public static final String NET_TUNNELING = "android.permission.NET_TUNNELING";
        public static final String NFC = "android.permission.NFC";
        public static final String NFC_HANDOVER_STATUS = "android.permission.NFC_HANDOVER_STATUS";
        public static final String NFC_TRANSACTION_EVENT = "android.permission.NFC_TRANSACTION_EVENT";
        @SystemApi
        public static final String NOTIFICATION_DURING_SETUP = "android.permission.NOTIFICATION_DURING_SETUP";
        public static final String NOTIFY_PENDING_SYSTEM_UPDATE = "android.permission.NOTIFY_PENDING_SYSTEM_UPDATE";
        @SystemApi
        public static final String NOTIFY_TV_INPUTS = "android.permission.NOTIFY_TV_INPUTS";
        @SystemApi
        public static final String OBSERVE_APP_USAGE = "android.permission.OBSERVE_APP_USAGE";
        public static final String OBSERVE_GRANT_REVOKE_PERMISSIONS = "android.permission.OBSERVE_GRANT_REVOKE_PERMISSIONS";
        @SystemApi
        public static final String OBSERVE_ROLE_HOLDERS = "android.permission.OBSERVE_ROLE_HOLDERS";
        public static final String OEM_UNLOCK_STATE = "android.permission.OEM_UNLOCK_STATE";
        @SystemApi
        public static final String OPEN_ACCESSIBILITY_DETAILS_SETTINGS = "android.permission.OPEN_ACCESSIBILITY_DETAILS_SETTINGS";
        public static final String OPEN_APP_OPEN_BY_DEFAULT_SETTINGS = "android.permission.OPEN_APP_OPEN_BY_DEFAULT_SETTINGS";
        @SystemApi
        public static final String OVERRIDE_WIFI_CONFIG = "android.permission.OVERRIDE_WIFI_CONFIG";
        public static final String PACKAGE_ROLLBACK_AGENT = "android.permission.PACKAGE_ROLLBACK_AGENT";
        public static final String PACKAGE_USAGE_STATS = "android.permission.PACKAGE_USAGE_STATS";
        @SystemApi
        public static final String PACKAGE_VERIFICATION_AGENT = "android.permission.PACKAGE_VERIFICATION_AGENT";
        @SystemApi
        public static final String PACKET_KEEPALIVE_OFFLOAD = "android.permission.PACKET_KEEPALIVE_OFFLOAD";
        @SystemApi
        public static final String PEERS_MAC_ADDRESS = "android.permission.PEERS_MAC_ADDRESS";
        @SystemApi
        public static final String PERFORM_CDMA_PROVISIONING = "android.permission.PERFORM_CDMA_PROVISIONING";
        @SystemApi
        public static final String PERFORM_SIM_ACTIVATION = "android.permission.PERFORM_SIM_ACTIVATION";
        @Deprecated
        public static final String PERSISTENT_ACTIVITY = "android.permission.PERSISTENT_ACTIVITY";
        @SystemApi
        public static final String POWER_SAVER = "android.permission.POWER_SAVER";
        @Deprecated
        public static final String PROCESS_OUTGOING_CALLS = "android.permission.PROCESS_OUTGOING_CALLS";
        @SystemApi
        public static final String PROVIDE_RESOLVER_RANKER_SERVICE = "android.permission.PROVIDE_RESOLVER_RANKER_SERVICE";
        @SystemApi
        public static final String PROVIDE_TRUST_AGENT = "android.permission.PROVIDE_TRUST_AGENT";
        public static final String QUERY_DO_NOT_ASK_CREDENTIALS_ON_BOOT = "android.permission.QUERY_DO_NOT_ASK_CREDENTIALS_ON_BOOT";
        @SystemApi
        public static final String QUERY_TIME_ZONE_RULES = "android.permission.QUERY_TIME_ZONE_RULES";
        public static final String READ_BLOCKED_NUMBERS = "android.permission.READ_BLOCKED_NUMBERS";
        public static final String READ_CALENDAR = "android.permission.READ_CALENDAR";
        public static final String READ_CALL_LOG = "android.permission.READ_CALL_LOG";
        @SystemApi
        public static final String READ_CELL_BROADCASTS = "android.permission.READ_CELL_BROADCASTS";
        public static final String READ_CLIPBOARD_IN_BACKGROUND = "android.permission.READ_CLIPBOARD_IN_BACKGROUND";
        public static final String READ_CONTACTS = "android.permission.READ_CONTACTS";
        @SystemApi
        public static final String READ_CONTENT_RATING_SYSTEMS = "android.permission.READ_CONTENT_RATING_SYSTEMS";
        @SystemApi
        public static final String READ_DEVICE_CONFIG = "android.permission.READ_DEVICE_CONFIG";
        @SystemApi
        public static final String READ_DREAM_STATE = "android.permission.READ_DREAM_STATE";
        public static final String READ_EXTERNAL_STORAGE = "android.permission.READ_EXTERNAL_STORAGE";
        public static final String READ_FRAME_BUFFER = "android.permission.READ_FRAME_BUFFER";
        public static final String READ_HISTORY_BOOKMARKS = "com.android.browser.permission.READ_HISTORY_BOOKMARKS";
        @Deprecated
        public static final String READ_INPUT_STATE = "android.permission.READ_INPUT_STATE";
        @SystemApi
        public static final String READ_INSTALL_SESSIONS = "android.permission.READ_INSTALL_SESSIONS";
        public static final String READ_LOGS = "android.permission.READ_LOGS";
        public static final String READ_LOWPAN_CREDENTIAL = "android.permission.READ_LOWPAN_CREDENTIAL";
        @SystemApi
        public static final String READ_NETWORK_USAGE_HISTORY = "android.permission.READ_NETWORK_USAGE_HISTORY";
        @SystemApi
        public static final String READ_OEM_UNLOCK_STATE = "android.permission.READ_OEM_UNLOCK_STATE";
        public static final String READ_PHONE_NUMBERS = "android.permission.READ_PHONE_NUMBERS";
        public static final String READ_PHONE_STATE = "android.permission.READ_PHONE_STATE";
        public static final String READ_PRECISE_PHONE_STATE = "android.permission.READ_PRECISE_PHONE_STATE";
        @SystemApi
        public static final String READ_PRINT_SERVICES = "android.permission.READ_PRINT_SERVICES";
        @SystemApi
        public static final String READ_PRINT_SERVICE_RECOMMENDATIONS = "android.permission.READ_PRINT_SERVICE_RECOMMENDATIONS";
        @SystemApi
        public static final String READ_PRIVILEGED_PHONE_STATE = "android.permission.READ_PRIVILEGED_PHONE_STATE";
        public static final String READ_PROFILE = "android.permission.READ_PROFILE";
        @SystemApi
        public static final String READ_RUNTIME_PROFILES = "android.permission.READ_RUNTIME_PROFILES";
        @SystemApi
        public static final String READ_SEARCH_INDEXABLES = "android.permission.READ_SEARCH_INDEXABLES";
        public static final String READ_SMS = "android.permission.READ_SMS";
        public static final String READ_SOCIAL_STREAM = "android.permission.READ_SOCIAL_STREAM";
        public static final String READ_SYNC_SETTINGS = "android.permission.READ_SYNC_SETTINGS";
        public static final String READ_SYNC_STATS = "android.permission.READ_SYNC_STATS";
        @SystemApi
        public static final String READ_SYSTEM_UPDATE_INFO = "android.permission.READ_SYSTEM_UPDATE_INFO";
        public static final String READ_USER_DICTIONARY = "android.permission.READ_USER_DICTIONARY";
        public static final String READ_VOICEMAIL = "com.android.voicemail.permission.READ_VOICEMAIL";
        @SystemApi
        public static final String READ_WALLPAPER_INTERNAL = "android.permission.READ_WALLPAPER_INTERNAL";
        @SystemApi
        public static final String READ_WIFI_CREDENTIAL = "android.permission.READ_WIFI_CREDENTIAL";
        @SystemApi
        public static final String REAL_GET_TASKS = "android.permission.REAL_GET_TASKS";
        public static final String REBOOT = "android.permission.REBOOT";
        public static final String RECEIVE_BLUETOOTH_MAP = "android.permission.RECEIVE_BLUETOOTH_MAP";
        public static final String RECEIVE_BOOT_COMPLETED = "android.permission.RECEIVE_BOOT_COMPLETED";
        @SystemApi
        public static final String RECEIVE_DATA_ACTIVITY_CHANGE = "android.permission.RECEIVE_DATA_ACTIVITY_CHANGE";
        @SystemApi
        public static final String RECEIVE_DEVICE_CUSTOMIZATION_READY = "android.permission.RECEIVE_DEVICE_CUSTOMIZATION_READY";
        @SystemApi
        public static final String RECEIVE_EMERGENCY_BROADCAST = "android.permission.RECEIVE_EMERGENCY_BROADCAST";
        public static final String RECEIVE_MEDIA_RESOURCE_USAGE = "android.permission.RECEIVE_MEDIA_RESOURCE_USAGE";
        public static final String RECEIVE_MMS = "android.permission.RECEIVE_MMS";
        public static final String RECEIVE_SMS = "android.permission.RECEIVE_SMS";
        public static final String RECEIVE_STK_COMMANDS = "android.permission.RECEIVE_STK_COMMANDS";
        public static final String RECEIVE_WAP_PUSH = "android.permission.RECEIVE_WAP_PUSH";
        @SystemApi
        public static final String RECEIVE_WIFI_CREDENTIAL_CHANGE = "android.permission.RECEIVE_WIFI_CREDENTIAL_CHANGE";
        public static final String RECORD_AUDIO = "android.permission.RECORD_AUDIO";
        @SystemApi
        public static final String RECOVERY = "android.permission.RECOVERY";
        @SystemApi
        public static final String RECOVER_KEYSTORE = "android.permission.RECOVER_KEYSTORE";
        @SystemApi
        public static final String REGISTER_CALL_PROVIDER = "android.permission.REGISTER_CALL_PROVIDER";
        @SystemApi
        public static final String REGISTER_CONNECTION_MANAGER = "android.permission.REGISTER_CONNECTION_MANAGER";
        @SystemApi
        public static final String REGISTER_SIM_SUBSCRIPTION = "android.permission.REGISTER_SIM_SUBSCRIPTION";
        public static final String REGISTER_WINDOW_MANAGER_LISTENERS = "android.permission.REGISTER_WINDOW_MANAGER_LISTENERS";
        public static final String REMOTE_AUDIO_PLAYBACK = "android.permission.REMOTE_AUDIO_PLAYBACK";
        @SystemApi
        public static final String REMOTE_DISPLAY_PROVIDER = "android.permission.REMOTE_DISPLAY_PROVIDER";
        @SystemApi
        public static final String REMOVE_DRM_CERTIFICATES = "android.permission.REMOVE_DRM_CERTIFICATES";
        @SystemApi
        public static final String REMOVE_TASKS = "android.permission.REMOVE_TASKS";
        public static final String REORDER_TASKS = "android.permission.REORDER_TASKS";
        public static final String REQUEST_COMPANION_RUN_IN_BACKGROUND = "android.permission.REQUEST_COMPANION_RUN_IN_BACKGROUND";
        public static final String REQUEST_COMPANION_USE_DATA_IN_BACKGROUND = "android.permission.REQUEST_COMPANION_USE_DATA_IN_BACKGROUND";
        public static final String REQUEST_DELETE_PACKAGES = "android.permission.REQUEST_DELETE_PACKAGES";
        public static final String REQUEST_IGNORE_BATTERY_OPTIMIZATIONS = "android.permission.REQUEST_IGNORE_BATTERY_OPTIMIZATIONS";
        public static final String REQUEST_INCIDENT_REPORT_APPROVAL = "android.permission.REQUEST_INCIDENT_REPORT_APPROVAL";
        public static final String REQUEST_INSTALL_PACKAGES = "android.permission.REQUEST_INSTALL_PACKAGES";
        public static final String REQUEST_NETWORK_SCORES = "android.permission.REQUEST_NETWORK_SCORES";
        @SystemApi
        public static final String REQUEST_NOTIFICATION_ASSISTANT_SERVICE = "android.permission.REQUEST_NOTIFICATION_ASSISTANT_SERVICE";
        public static final String REQUEST_PASSWORD_COMPLEXITY = "android.permission.REQUEST_PASSWORD_COMPLEXITY";
        public static final String RESET_FACE_LOCKOUT = "android.permission.RESET_FACE_LOCKOUT";
        public static final String RESET_FINGERPRINT_LOCKOUT = "android.permission.RESET_FINGERPRINT_LOCKOUT";
        @SystemApi
        public static final String RESET_PASSWORD = "android.permission.RESET_PASSWORD";
        public static final String RESET_SHORTCUT_MANAGER_THROTTLING = "android.permission.RESET_SHORTCUT_MANAGER_THROTTLING";
        @Deprecated
        public static final String RESTART_PACKAGES = "android.permission.RESTART_PACKAGES";
        @SystemApi
        public static final String RESTRICTED_VR_ACCESS = "android.permission.RESTRICTED_VR_ACCESS";
        @SystemApi
        public static final String RETRIEVE_WINDOW_CONTENT = "android.permission.RETRIEVE_WINDOW_CONTENT";
        public static final String RETRIEVE_WINDOW_TOKEN = "android.permission.RETRIEVE_WINDOW_TOKEN";
        @SystemApi
        public static final String REVIEW_ACCESSIBILITY_SERVICES = "android.permission.REVIEW_ACCESSIBILITY_SERVICES";
        @SystemApi
        public static final String REVOKE_RUNTIME_PERMISSIONS = "android.permission.REVOKE_RUNTIME_PERMISSIONS";
        @Deprecated
        public static final String RUN_IN_BACKGROUND = "android.permission.RUN_IN_BACKGROUND";
        @SystemApi
        public static final String SCORE_NETWORKS = "android.permission.SCORE_NETWORKS";
        @SystemApi
        public static final String SEND_DEVICE_CUSTOMIZATION_READY = "android.permission.SEND_DEVICE_CUSTOMIZATION_READY";
        public static final String SEND_EMBMS_INTENTS = "android.permission.SEND_EMBMS_INTENTS";
        public static final String SEND_RESPOND_VIA_MESSAGE = "android.permission.SEND_RESPOND_VIA_MESSAGE";
        @SystemApi
        public static final String SEND_SHOW_SUSPENDED_APP_DETAILS = "android.permission.SEND_SHOW_SUSPENDED_APP_DETAILS";
        public static final String SEND_SMS = "android.permission.SEND_SMS";
        @SystemApi
        public static final String SEND_SMS_NO_CONFIRMATION = "android.permission.SEND_SMS_NO_CONFIRMATION";
        @SystemApi
        public static final String SERIAL_PORT = "android.permission.SERIAL_PORT";
        @SystemApi
        public static final String SET_ACTIVITY_WATCHER = "android.permission.SET_ACTIVITY_WATCHER";
        public static final String SET_ALARM = "com.android.alarm.permission.SET_ALARM";
        public static final String SET_ALWAYS_FINISH = "android.permission.SET_ALWAYS_FINISH";
        public static final String SET_ANIMATION_SCALE = "android.permission.SET_ANIMATION_SCALE";
        public static final String SET_DEBUG_APP = "android.permission.SET_DEBUG_APP";
        public static final String SET_DISPLAY_OFFSET = "android.permission.SET_DISPLAY_OFFSET";
        @SystemApi
        public static final String SET_HARMFUL_APP_WARNINGS = "android.permission.SET_HARMFUL_APP_WARNINGS";
        public static final String SET_INPUT_CALIBRATION = "android.permission.SET_INPUT_CALIBRATION";
        public static final String SET_KEYBOARD_LAYOUT = "android.permission.SET_KEYBOARD_LAYOUT";
        @SystemApi
        public static final String SET_MEDIA_KEY_LISTENER = "android.permission.SET_MEDIA_KEY_LISTENER";
        @SystemApi
        public static final String SET_ORIENTATION = "android.permission.SET_ORIENTATION";
        @SystemApi
        public static final String SET_POINTER_SPEED = "android.permission.SET_POINTER_SPEED";
        @Deprecated
        public static final String SET_PREFERRED_APPLICATIONS = "android.permission.SET_PREFERRED_APPLICATIONS";
        public static final String SET_PROCESS_LIMIT = "android.permission.SET_PROCESS_LIMIT";
        @SystemApi
        public static final String SET_SCREEN_COMPATIBILITY = "android.permission.SET_SCREEN_COMPATIBILITY";
        public static final String SET_TIME = "android.permission.SET_TIME";
        public static final String SET_TIME_ZONE = "android.permission.SET_TIME_ZONE";
        @SystemApi
        public static final String SET_VOLUME_KEY_LONG_PRESS_LISTENER = "android.permission.SET_VOLUME_KEY_LONG_PRESS_LISTENER";
        public static final String SET_WALLPAPER = "android.permission.SET_WALLPAPER";
        @SystemApi
        public static final String SET_WALLPAPER_COMPONENT = "android.permission.SET_WALLPAPER_COMPONENT";
        public static final String SET_WALLPAPER_HINTS = "android.permission.SET_WALLPAPER_HINTS";
        @SystemApi
        public static final String SHOW_KEYGUARD_MESSAGE = "android.permission.SHOW_KEYGUARD_MESSAGE";
        @SystemApi
        public static final String SHUTDOWN = "android.permission.SHUTDOWN";
        public static final String SIGNAL_PERSISTENT_PROCESSES = "android.permission.SIGNAL_PERSISTENT_PROCESSES";
        public static final String SMS_FINANCIAL_TRANSACTIONS = "android.permission.SMS_FINANCIAL_TRANSACTIONS";
        public static final String START_ACTIVITIES_FROM_BACKGROUND = "android.permission.START_ACTIVITIES_FROM_BACKGROUND";
        public static final String START_ACTIVITY_AS_CALLER = "android.permission.START_ACTIVITY_AS_CALLER";
        public static final String START_ANY_ACTIVITY = "android.permission.START_ANY_ACTIVITY";
        public static final String START_TASKS_FROM_RECENTS = "android.permission.START_TASKS_FROM_RECENTS";
        public static final String START_VIEW_PERMISSION_USAGE = "android.permission.START_VIEW_PERMISSION_USAGE";
        public static final String STATSCOMPANION = "android.permission.STATSCOMPANION";
        public static final String STATUS_BAR = "android.permission.STATUS_BAR";
        public static final String STATUS_BAR_SERVICE = "android.permission.STATUS_BAR_SERVICE";
        @SystemApi
        public static final String STOP_APP_SWITCHES = "android.permission.STOP_APP_SWITCHES";
        public static final String STORAGE_INTERNAL = "android.permission.STORAGE_INTERNAL";
        public static final String SUBSCRIBED_FEEDS_READ = "android.permission.SUBSCRIBED_FEEDS_READ";
        public static final String SUBSCRIBED_FEEDS_WRITE = "android.permission.SUBSCRIBED_FEEDS_WRITE";
        @SystemApi
        public static final String SUBSTITUTE_NOTIFICATION_APP_NAME = "android.permission.SUBSTITUTE_NOTIFICATION_APP_NAME";
        @SystemApi
        public static final String SUBSTITUTE_SHARE_TARGET_APP_NAME_AND_ICON = "android.permission.SUBSTITUTE_SHARE_TARGET_APP_NAME_AND_ICON";
        @SystemApi
        public static final String SUSPEND_APPS = "android.permission.SUSPEND_APPS";
        public static final String SYSTEM_ALERT_WINDOW = "android.permission.SYSTEM_ALERT_WINDOW";
        public static final String TABLET_MODE = "android.permission.TABLET_MODE";
        public static final String TEMPORARY_ENABLE_ACCESSIBILITY = "android.permission.TEMPORARY_ENABLE_ACCESSIBILITY";
        public static final String TEST_BLACKLISTED_PASSWORD = "android.permission.TEST_BLACKLISTED_PASSWORD";
        public static final String TEST_MANAGE_ROLLBACKS = "android.permission.TEST_MANAGE_ROLLBACKS";
        @SystemApi
        public static final String TETHER_PRIVILEGED = "android.permission.TETHER_PRIVILEGED";
        public static final String TRANSMIT_IR = "android.permission.TRANSMIT_IR";
        public static final String TRIGGER_TIME_ZONE_RULES_CHECK = "android.permission.TRIGGER_TIME_ZONE_RULES_CHECK";
        public static final String TRUST_LISTENER = "android.permission.TRUST_LISTENER";
        @SystemApi
        public static final String TV_INPUT_HARDWARE = "android.permission.TV_INPUT_HARDWARE";
        @SystemApi
        public static final String TV_VIRTUAL_REMOTE_CONTROLLER = "android.permission.TV_VIRTUAL_REMOTE_CONTROLLER";
        public static final String UNINSTALL_SHORTCUT = "com.android.launcher.permission.UNINSTALL_SHORTCUT";
        @SystemApi
        public static final String UNLIMITED_SHORTCUTS_API_CALLS = "android.permission.UNLIMITED_SHORTCUTS_API_CALLS";
        @SystemApi
        public static final String UPDATE_APP_OPS_STATS = "android.permission.UPDATE_APP_OPS_STATS";
        public static final String UPDATE_CONFIG = "android.permission.UPDATE_CONFIG";
        public static final String UPDATE_DEVICE_STATS = "android.permission.UPDATE_DEVICE_STATS";
        @SystemApi
        public static final String UPDATE_LOCK = "android.permission.UPDATE_LOCK";
        public static final String UPDATE_LOCK_TASK_PACKAGES = "android.permission.UPDATE_LOCK_TASK_PACKAGES";
        @SystemApi
        public static final String UPDATE_TIME_ZONE_RULES = "android.permission.UPDATE_TIME_ZONE_RULES";
        @SystemApi
        public static final String USER_ACTIVITY = "android.permission.USER_ACTIVITY";
        public static final String USE_BIOMETRIC = "android.permission.USE_BIOMETRIC";
        public static final String USE_BIOMETRIC_INTERNAL = "android.permission.USE_BIOMETRIC_INTERNAL";
        public static final String USE_COLORIZED_NOTIFICATIONS = "android.permission.USE_COLORIZED_NOTIFICATIONS";
        public static final String USE_CREDENTIALS = "android.permission.USE_CREDENTIALS";
        @Deprecated
        public static final String USE_DATA_IN_BACKGROUND = "android.permission.USE_DATA_IN_BACKGROUND";
        @Deprecated
        public static final String USE_FINGERPRINT = "android.permission.USE_FINGERPRINT";
        public static final String USE_FULL_SCREEN_INTENT = "android.permission.USE_FULL_SCREEN_INTENT";
        @SystemApi
        public static final String USE_RESERVED_DISK = "android.permission.USE_RESERVED_DISK";
        public static final String USE_SIP = "android.permission.USE_SIP";
        public static final String VIBRATE = "android.permission.VIBRATE";
        public static final String VIEW_INSTANT_APPS = "android.permission.VIEW_INSTANT_APPS";
        public static final String WAKE_LOCK = "android.permission.WAKE_LOCK";
        public static final String WATCH_APPOPS = "android.permission.WATCH_APPOPS";
        @SystemApi
        public static final String WHITELIST_RESTRICTED_PERMISSIONS = "android.permission.WHITELIST_RESTRICTED_PERMISSIONS";
        public static final String WIFI_SET_DEVICE_MOBILITY_STATE = "android.permission.WIFI_SET_DEVICE_MOBILITY_STATE";
        public static final String WIFI_UPDATE_USABILITY_STATS_SCORE = "android.permission.WIFI_UPDATE_USABILITY_STATS_SCORE";
        public static final String WRITE_APN_SETTINGS = "android.permission.WRITE_APN_SETTINGS";
        public static final String WRITE_BLOCKED_NUMBERS = "android.permission.WRITE_BLOCKED_NUMBERS";
        public static final String WRITE_CALENDAR = "android.permission.WRITE_CALENDAR";
        public static final String WRITE_CALL_LOG = "android.permission.WRITE_CALL_LOG";
        public static final String WRITE_CONTACTS = "android.permission.WRITE_CONTACTS";
        @SystemApi
        public static final String WRITE_DEVICE_CONFIG = "android.permission.WRITE_DEVICE_CONFIG";
        @SystemApi
        public static final String WRITE_DREAM_STATE = "android.permission.WRITE_DREAM_STATE";
        @SystemApi
        public static final String WRITE_EMBEDDED_SUBSCRIPTIONS = "android.permission.WRITE_EMBEDDED_SUBSCRIPTIONS";
        public static final String WRITE_EXTERNAL_STORAGE = "android.permission.WRITE_EXTERNAL_STORAGE";
        public static final String WRITE_GSERVICES = "android.permission.WRITE_GSERVICES";
        public static final String WRITE_HISTORY_BOOKMARKS = "com.android.browser.permission.WRITE_HISTORY_BOOKMARKS";
        @SystemApi
        public static final String WRITE_MEDIA_STORAGE = "android.permission.WRITE_MEDIA_STORAGE";
        @SystemApi
        public static final String WRITE_OBB = "android.permission.WRITE_OBB";
        public static final String WRITE_PROFILE = "android.permission.WRITE_PROFILE";
        public static final String WRITE_SECURE_SETTINGS = "android.permission.WRITE_SECURE_SETTINGS";
        public static final String WRITE_SETTINGS = "android.permission.WRITE_SETTINGS";
        public static final String WRITE_SETTINGS_HOMEPAGE_DATA = "android.permission.WRITE_SETTINGS_HOMEPAGE_DATA";
        public static final String WRITE_SMS = "android.permission.WRITE_SMS";
        public static final String WRITE_SOCIAL_STREAM = "android.permission.WRITE_SOCIAL_STREAM";
        public static final String WRITE_SYNC_SETTINGS = "android.permission.WRITE_SYNC_SETTINGS";
        public static final String WRITE_USER_DICTIONARY = "android.permission.WRITE_USER_DICTIONARY";
        public static final String WRITE_VOICEMAIL = "com.android.voicemail.permission.WRITE_VOICEMAIL";
    }

    public static final class permission_group {
        public static final String ACTIVITY_RECOGNITION = "android.permission-group.ACTIVITY_RECOGNITION";
        public static final String CALENDAR = "android.permission-group.CALENDAR";
        public static final String CALL_LOG = "android.permission-group.CALL_LOG";
        public static final String CAMERA = "android.permission-group.CAMERA";
        public static final String CONTACTS = "android.permission-group.CONTACTS";
        public static final String LOCATION = "android.permission-group.LOCATION";
        public static final String MICROPHONE = "android.permission-group.MICROPHONE";
        public static final String PHONE = "android.permission-group.PHONE";
        public static final String SENSORS = "android.permission-group.SENSORS";
        public static final String SMS = "android.permission-group.SMS";
        public static final String STORAGE = "android.permission-group.STORAGE";
        @SystemApi
        public static final String UNDEFINED = "android.permission-group.UNDEFINED";
    }

}

