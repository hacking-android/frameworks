/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  dalvik.system.VMRuntime
 */
package android.telephony;

import android.annotation.SuppressLint;
import android.annotation.SystemApi;
import android.annotation.UnsupportedAppUsage;
import android.app.ActivityThread;
import android.app.PendingIntent;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.NetworkStats;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.BaseBundle;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.IInterface;
import android.os.Parcelable;
import android.os.ParcelableException;
import android.os.PersistableBundle;
import android.os.Process;
import android.os.RemoteException;
import android.os.ResultReceiver;
import android.os.ServiceManager;
import android.os.SystemProperties;
import android.os.WorkSource;
import android.provider.Settings;
import android.service.carrier.CarrierIdentifier;
import android.telecom.PhoneAccount;
import android.telecom.PhoneAccountHandle;
import android.telecom.TelecomManager;
import android.telephony.AnomalyReporter;
import android.telephony.AvailableNetworkInfo;
import android.telephony.CarrierConfigManager;
import android.telephony.CarrierRestrictionRules;
import android.telephony.CellInfo;
import android.telephony.CellLocation;
import android.telephony.ClientRequestStats;
import android.telephony.ICellInfoCallback;
import android.telephony.IccOpenLogicalChannelResponse;
import android.telephony.ImsiEncryptionInfo;
import android.telephony.NeighboringCellInfo;
import android.telephony.NetworkScan;
import android.telephony.NetworkScanRequest;
import android.telephony.NumberVerificationCallback;
import android.telephony.PhoneNumberRange;
import android.telephony.PhoneStateListener;
import android.telephony.RadioAccessFamily;
import android.telephony.Rlog;
import android.telephony.ServiceState;
import android.telephony.SignalStrength;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyHistogram;
import android.telephony.TelephonyScanManager;
import android.telephony.UiccCardInfo;
import android.telephony.UiccSlotInfo;
import android.telephony.UssdResponse;
import android.telephony.VisualVoicemailSmsFilterSettings;
import android.telephony._$$Lambda$TelephonyManager$1$5jj__2hbfx_RMVO7qjBdMYFfP1s;
import android.telephony._$$Lambda$TelephonyManager$1$888GQVMXufCYSJI5ivTjjUxEprI;
import android.telephony._$$Lambda$TelephonyManager$1$DUDjwoHWG36BPTvbfvZqnIO3Y88;
import android.telephony._$$Lambda$TelephonyManager$1$scMPky6lOZrCjFC3d4STbtLfpHE;
import android.telephony._$$Lambda$TelephonyManager$2$6owqHJtmTOa9dDQAz_9oKh9XFVk;
import android.telephony._$$Lambda$TelephonyManager$2$Ulw55AvQUDkoL1gDNnPVlIOb8mw;
import android.telephony._$$Lambda$TelephonyManager$2$hWPf2raNadUBIhTQLEUpRhHWKoI;
import android.telephony._$$Lambda$TelephonyManager$2$l6Pazxfi7QghMr2Z0MpduhNe6yc;
import android.telephony._$$Lambda$TelephonyManager$3$LPMNUsxM8QRYWmnzGtrEYPm5sAs;
import android.telephony._$$Lambda$TelephonyManager$3$TrNEDm6VsUgT1BQFiXGiPDtbxuA;
import android.telephony._$$Lambda$TelephonyManager$3$VM3y0XwyxZN6vR6ERQTngCQIICc;
import android.telephony._$$Lambda$TelephonyManager$3$ue1tJSNmFJObWAJcaHRYIrfBRNg;
import android.telephony._$$Lambda$TelephonyManager$4i1RRVjnCzfQvX2hIGG9K8g4DaY;
import android.telephony._$$Lambda$TelephonyManager$5$RFt1EExZlmUUXRBea_EWHl9kTkc;
import android.telephony._$$Lambda$TelephonyManager$5$dLg4hbo46SmKP0wtKbXAlS8hCpg;
import android.telephony._$$Lambda$TelephonyManager$6$1S5Pi2oZUOPIU8alAP53FlL2sjk;
import android.telephony._$$Lambda$TelephonyManager$6$AFjFk42NCFYCMG8wA5_6SCfk7No;
import android.telephony._$$Lambda$TelephonyManager$eMNW6lCcxHLvIrcBQvhUXUKuLFU;
import android.telephony._$$Lambda$TelephonyManager$qjhLNTc5_Bq4btM7q4y_F5cdAK0;
import android.telephony.emergency.EmergencyNumber;
import android.telephony.ims.aidl.IImsConfig;
import android.telephony.ims.aidl.IImsMmTelFeature;
import android.telephony.ims.aidl.IImsRcsFeature;
import android.telephony.ims.aidl.IImsRegistration;
import android.text.TextUtils;
import android.util.Log;
import android.util.Pair;
import com.android.ims.internal.IImsServiceFeatureCallback;
import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.telecom.ITelecomService;
import com.android.internal.telephony.CellNetworkScanResult;
import com.android.internal.telephony.INumberVerificationCallback;
import com.android.internal.telephony.IOns;
import com.android.internal.telephony.IPhoneStateListener;
import com.android.internal.telephony.IPhoneSubInfo;
import com.android.internal.telephony.ISetOpportunisticDataCallback;
import com.android.internal.telephony.ITelephony;
import com.android.internal.telephony.ITelephonyRegistry;
import com.android.internal.telephony.IUpdateAvailableNetworksCallback;
import com.android.internal.telephony.OperatorInfo;
import com.android.internal.telephony.PhoneConstants;
import com.android.internal.util.FunctionalUtils;
import com.android.internal.util.Preconditions;
import dalvik.system.VMRuntime;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.Executor;
import java.util.function.Consumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TelephonyManager {
    @SystemApi
    public static final String ACTION_ANOMALY_REPORTED = "android.telephony.action.ANOMALY_REPORTED";
    public static final String ACTION_CALL_DISCONNECT_CAUSE_CHANGED = "android.intent.action.CALL_DISCONNECT_CAUSE";
    public static final String ACTION_CARRIER_MESSAGING_CLIENT_SERVICE = "android.telephony.action.CARRIER_MESSAGING_CLIENT_SERVICE";
    public static final String ACTION_CONFIGURE_VOICEMAIL = "android.telephony.action.CONFIGURE_VOICEMAIL";
    public static final String ACTION_DATA_STALL_DETECTED = "android.intent.action.DATA_STALL_DETECTED";
    public static final String ACTION_EMERGENCY_ASSISTANCE = "android.telephony.action.EMERGENCY_ASSISTANCE";
    public static final String ACTION_NETWORK_COUNTRY_CHANGED = "android.telephony.action.NETWORK_COUNTRY_CHANGED";
    public static final String ACTION_PHONE_STATE_CHANGED = "android.intent.action.PHONE_STATE";
    public static final String ACTION_PRECISE_CALL_STATE_CHANGED = "android.intent.action.PRECISE_CALL_STATE";
    @Deprecated
    @UnsupportedAppUsage
    public static final String ACTION_PRECISE_DATA_CONNECTION_STATE_CHANGED = "android.intent.action.PRECISE_DATA_CONNECTION_STATE_CHANGED";
    public static final String ACTION_PRIMARY_SUBSCRIPTION_LIST_CHANGED = "android.telephony.action.PRIMARY_SUBSCRIPTION_LIST_CHANGED";
    public static final String ACTION_RESPOND_VIA_MESSAGE = "android.intent.action.RESPOND_VIA_MESSAGE";
    public static final String ACTION_SECRET_CODE = "android.telephony.action.SECRET_CODE";
    public static final String ACTION_SHOW_VOICEMAIL_NOTIFICATION = "android.telephony.action.SHOW_VOICEMAIL_NOTIFICATION";
    @SystemApi
    public static final String ACTION_SIM_APPLICATION_STATE_CHANGED = "android.telephony.action.SIM_APPLICATION_STATE_CHANGED";
    @SystemApi
    public static final String ACTION_SIM_CARD_STATE_CHANGED = "android.telephony.action.SIM_CARD_STATE_CHANGED";
    @SystemApi
    public static final String ACTION_SIM_SLOT_STATUS_CHANGED = "android.telephony.action.SIM_SLOT_STATUS_CHANGED";
    public static final String ACTION_SUBSCRIPTION_CARRIER_IDENTITY_CHANGED = "android.telephony.action.SUBSCRIPTION_CARRIER_IDENTITY_CHANGED";
    public static final String ACTION_SUBSCRIPTION_SPECIFIC_CARRIER_IDENTITY_CHANGED = "android.telephony.action.SUBSCRIPTION_SPECIFIC_CARRIER_IDENTITY_CHANGED";
    public static final int APPTYPE_CSIM = 4;
    public static final int APPTYPE_ISIM = 5;
    public static final int APPTYPE_RUIM = 3;
    public static final int APPTYPE_SIM = 1;
    public static final int APPTYPE_USIM = 2;
    public static final int AUTHTYPE_EAP_AKA = 129;
    public static final int AUTHTYPE_EAP_SIM = 128;
    public static final int CALL_STATE_IDLE = 0;
    public static final int CALL_STATE_OFFHOOK = 2;
    public static final int CALL_STATE_RINGING = 1;
    public static final int CARD_POWER_DOWN = 0;
    public static final int CARD_POWER_UP = 1;
    public static final int CARD_POWER_UP_PASS_THROUGH = 2;
    @SystemApi
    public static final int CARRIER_PRIVILEGE_STATUS_ERROR_LOADING_RULES = -2;
    @SystemApi
    public static final int CARRIER_PRIVILEGE_STATUS_HAS_ACCESS = 1;
    @SystemApi
    public static final int CARRIER_PRIVILEGE_STATUS_NO_ACCESS = 0;
    @SystemApi
    public static final int CARRIER_PRIVILEGE_STATUS_RULES_NOT_LOADED = -1;
    public static final int CDMA_ROAMING_MODE_AFFILIATED = 1;
    public static final int CDMA_ROAMING_MODE_ANY = 2;
    public static final int CDMA_ROAMING_MODE_HOME = 0;
    public static final int CDMA_ROAMING_MODE_RADIO_DEFAULT = -1;
    public static final int DATA_ACTIVITY_DORMANT = 4;
    public static final int DATA_ACTIVITY_IN = 1;
    public static final int DATA_ACTIVITY_INOUT = 3;
    public static final int DATA_ACTIVITY_NONE = 0;
    public static final int DATA_ACTIVITY_OUT = 2;
    public static final int DATA_CONNECTED = 2;
    public static final int DATA_CONNECTING = 1;
    public static final int DATA_DISCONNECTED = 0;
    public static final int DATA_SUSPENDED = 3;
    public static final int DATA_UNKNOWN = -1;
    public static final boolean EMERGENCY_ASSISTANCE_ENABLED = true;
    public static final String EVENT_CALL_FORWARDED = "android.telephony.event.EVENT_CALL_FORWARDED";
    public static final String EVENT_DOWNGRADE_DATA_DISABLED = "android.telephony.event.EVENT_DOWNGRADE_DATA_DISABLED";
    public static final String EVENT_DOWNGRADE_DATA_LIMIT_REACHED = "android.telephony.event.EVENT_DOWNGRADE_DATA_LIMIT_REACHED";
    public static final String EVENT_HANDOVER_TO_WIFI_FAILED = "android.telephony.event.EVENT_HANDOVER_TO_WIFI_FAILED";
    public static final String EVENT_HANDOVER_VIDEO_FROM_LTE_TO_WIFI = "android.telephony.event.EVENT_HANDOVER_VIDEO_FROM_LTE_TO_WIFI";
    public static final String EVENT_HANDOVER_VIDEO_FROM_WIFI_TO_LTE = "android.telephony.event.EVENT_HANDOVER_VIDEO_FROM_WIFI_TO_LTE";
    public static final String EVENT_NOTIFY_INTERNATIONAL_CALL_ON_WFC = "android.telephony.event.EVENT_NOTIFY_INTERNATIONAL_CALL_ON_WFC";
    public static final String EVENT_SUPPLEMENTARY_SERVICE_NOTIFICATION = "android.telephony.event.EVENT_SUPPLEMENTARY_SERVICE_NOTIFICATION";
    @SystemApi
    public static final String EXTRA_ANOMALY_DESCRIPTION = "android.telephony.extra.ANOMALY_DESCRIPTION";
    @SystemApi
    public static final String EXTRA_ANOMALY_ID = "android.telephony.extra.ANOMALY_ID";
    public static final String EXTRA_BACKGROUND_CALL_STATE = "background_state";
    public static final String EXTRA_CALL_VOICEMAIL_INTENT = "android.telephony.extra.CALL_VOICEMAIL_INTENT";
    public static final String EXTRA_CARRIER_ID = "android.telephony.extra.CARRIER_ID";
    public static final String EXTRA_CARRIER_NAME = "android.telephony.extra.CARRIER_NAME";
    public static final String EXTRA_DATA_APN = "apn";
    public static final String EXTRA_DATA_APN_TYPE = "apnType";
    public static final String EXTRA_DATA_FAILURE_CAUSE = "failCause";
    public static final String EXTRA_DATA_LINK_PROPERTIES_KEY = "linkProperties";
    public static final String EXTRA_DATA_NETWORK_TYPE = "networkType";
    public static final String EXTRA_DATA_STATE = "state";
    public static final String EXTRA_DEFAULT_SUBSCRIPTION_SELECT_TYPE = "android.telephony.extra.DEFAULT_SUBSCRIPTION_SELECT_TYPE";
    public static final int EXTRA_DEFAULT_SUBSCRIPTION_SELECT_TYPE_ALL = 4;
    public static final int EXTRA_DEFAULT_SUBSCRIPTION_SELECT_TYPE_DATA = 1;
    public static final int EXTRA_DEFAULT_SUBSCRIPTION_SELECT_TYPE_NONE = 0;
    public static final int EXTRA_DEFAULT_SUBSCRIPTION_SELECT_TYPE_SMS = 3;
    public static final int EXTRA_DEFAULT_SUBSCRIPTION_SELECT_TYPE_VOICE = 2;
    public static final String EXTRA_DISCONNECT_CAUSE = "disconnect_cause";
    public static final String EXTRA_FOREGROUND_CALL_STATE = "foreground_state";
    public static final String EXTRA_HIDE_PUBLIC_SETTINGS = "android.telephony.extra.HIDE_PUBLIC_SETTINGS";
    @Deprecated
    public static final String EXTRA_INCOMING_NUMBER = "incoming_number";
    public static final String EXTRA_IS_REFRESH = "android.telephony.extra.IS_REFRESH";
    public static final String EXTRA_LAUNCH_VOICEMAIL_SETTINGS_INTENT = "android.telephony.extra.LAUNCH_VOICEMAIL_SETTINGS_INTENT";
    public static final String EXTRA_NETWORK_COUNTRY = "android.telephony.extra.NETWORK_COUNTRY";
    public static final String EXTRA_NOTIFICATION_CODE = "android.telephony.extra.NOTIFICATION_CODE";
    public static final String EXTRA_NOTIFICATION_COUNT = "android.telephony.extra.NOTIFICATION_COUNT";
    public static final String EXTRA_NOTIFICATION_MESSAGE = "android.telephony.extra.NOTIFICATION_MESSAGE";
    public static final String EXTRA_NOTIFICATION_TYPE = "android.telephony.extra.NOTIFICATION_TYPE";
    public static final String EXTRA_PHONE_ACCOUNT_HANDLE = "android.telephony.extra.PHONE_ACCOUNT_HANDLE";
    public static final String EXTRA_PRECISE_DISCONNECT_CAUSE = "precise_disconnect_cause";
    public static final String EXTRA_RECOVERY_ACTION = "recoveryAction";
    public static final String EXTRA_RINGING_CALL_STATE = "ringing_state";
    public static final String EXTRA_SIM_COMBINATION_NAMES = "android.telephony.extra.SIM_COMBINATION_NAMES";
    public static final String EXTRA_SIM_COMBINATION_WARNING_TYPE = "android.telephony.extra.SIM_COMBINATION_WARNING_TYPE";
    public static final int EXTRA_SIM_COMBINATION_WARNING_TYPE_DUAL_CDMA = 1;
    public static final int EXTRA_SIM_COMBINATION_WARNING_TYPE_NONE = 0;
    @SystemApi
    public static final String EXTRA_SIM_STATE = "android.telephony.extra.SIM_STATE";
    public static final String EXTRA_SPECIFIC_CARRIER_ID = "android.telephony.extra.SPECIFIC_CARRIER_ID";
    public static final String EXTRA_SPECIFIC_CARRIER_NAME = "android.telephony.extra.SPECIFIC_CARRIER_NAME";
    public static final String EXTRA_STATE = "state";
    public static final String EXTRA_STATE_IDLE;
    public static final String EXTRA_STATE_OFFHOOK;
    public static final String EXTRA_STATE_RINGING;
    public static final String EXTRA_SUBSCRIPTION_ID = "android.telephony.extra.SUBSCRIPTION_ID";
    @SystemApi
    public static final String EXTRA_VISUAL_VOICEMAIL_ENABLED_BY_USER_BOOL = "android.telephony.extra.VISUAL_VOICEMAIL_ENABLED_BY_USER_BOOL";
    public static final String EXTRA_VOICEMAIL_NUMBER = "android.telephony.extra.VOICEMAIL_NUMBER";
    @SystemApi
    public static final String EXTRA_VOICEMAIL_SCRAMBLED_PIN_STRING = "android.telephony.extra.VOICEMAIL_SCRAMBLED_PIN_STRING";
    public static final int INDICATION_FILTER_DATA_CALL_DORMANCY_CHANGED = 4;
    public static final int INDICATION_FILTER_FULL_NETWORK_STATE = 2;
    public static final int INDICATION_FILTER_LINK_CAPACITY_ESTIMATE = 8;
    public static final int INDICATION_FILTER_PHYSICAL_CHANNEL_CONFIG = 16;
    public static final int INDICATION_FILTER_SIGNAL_STRENGTH = 1;
    public static final int INDICATION_UPDATE_MODE_IGNORE_SCREEN_OFF = 2;
    public static final int INDICATION_UPDATE_MODE_NORMAL = 1;
    public static final int KEY_TYPE_EPDG = 1;
    public static final int KEY_TYPE_WLAN = 2;
    public static final int MAX_NETWORK_TYPE = 20;
    private static final long MAX_NUMBER_VERIFICATION_TIMEOUT_MILLIS = 60000L;
    public static final String METADATA_HIDE_VOICEMAIL_SETTINGS_MENU = "android.telephony.HIDE_VOICEMAIL_SETTINGS_MENU";
    public static final String MODEM_ACTIVITY_RESULT_KEY = "controller_activity";
    public static final int MULTISIM_ALLOWED = 0;
    public static final int MULTISIM_NOT_SUPPORTED_BY_CARRIER = 2;
    public static final int MULTISIM_NOT_SUPPORTED_BY_HARDWARE = 1;
    @UnsupportedAppUsage
    public static final int NETWORK_CLASS_2_G = 1;
    @UnsupportedAppUsage
    public static final int NETWORK_CLASS_3_G = 2;
    @UnsupportedAppUsage
    public static final int NETWORK_CLASS_4_G = 3;
    public static final int NETWORK_CLASS_UNKNOWN = 0;
    public static final int NETWORK_MODE_CDMA_EVDO = 4;
    public static final int NETWORK_MODE_CDMA_NO_EVDO = 5;
    public static final int NETWORK_MODE_EVDO_NO_CDMA = 6;
    public static final int NETWORK_MODE_GLOBAL = 7;
    public static final int NETWORK_MODE_GSM_ONLY = 1;
    public static final int NETWORK_MODE_GSM_UMTS = 3;
    public static final int NETWORK_MODE_LTE_CDMA_EVDO = 8;
    public static final int NETWORK_MODE_LTE_CDMA_EVDO_GSM_WCDMA = 10;
    public static final int NETWORK_MODE_LTE_GSM_WCDMA = 9;
    public static final int NETWORK_MODE_LTE_ONLY = 11;
    public static final int NETWORK_MODE_LTE_TDSCDMA = 15;
    public static final int NETWORK_MODE_LTE_TDSCDMA_CDMA_EVDO_GSM_WCDMA = 22;
    public static final int NETWORK_MODE_LTE_TDSCDMA_GSM = 17;
    public static final int NETWORK_MODE_LTE_TDSCDMA_GSM_WCDMA = 20;
    public static final int NETWORK_MODE_LTE_TDSCDMA_WCDMA = 19;
    public static final int NETWORK_MODE_LTE_WCDMA = 12;
    public static final int NETWORK_MODE_NR_LTE = 24;
    public static final int NETWORK_MODE_NR_LTE_CDMA_EVDO = 25;
    public static final int NETWORK_MODE_NR_LTE_CDMA_EVDO_GSM_WCDMA = 27;
    public static final int NETWORK_MODE_NR_LTE_GSM_WCDMA = 26;
    public static final int NETWORK_MODE_NR_LTE_TDSCDMA = 29;
    public static final int NETWORK_MODE_NR_LTE_TDSCDMA_CDMA_EVDO_GSM_WCDMA = 33;
    public static final int NETWORK_MODE_NR_LTE_TDSCDMA_GSM = 30;
    public static final int NETWORK_MODE_NR_LTE_TDSCDMA_GSM_WCDMA = 32;
    public static final int NETWORK_MODE_NR_LTE_TDSCDMA_WCDMA = 31;
    public static final int NETWORK_MODE_NR_LTE_WCDMA = 28;
    public static final int NETWORK_MODE_NR_ONLY = 23;
    public static final int NETWORK_MODE_TDSCDMA_CDMA_EVDO_GSM_WCDMA = 21;
    public static final int NETWORK_MODE_TDSCDMA_GSM = 16;
    public static final int NETWORK_MODE_TDSCDMA_GSM_WCDMA = 18;
    public static final int NETWORK_MODE_TDSCDMA_ONLY = 13;
    public static final int NETWORK_MODE_TDSCDMA_WCDMA = 14;
    public static final int NETWORK_MODE_WCDMA_ONLY = 2;
    public static final int NETWORK_MODE_WCDMA_PREF = 0;
    public static final int NETWORK_SELECTION_MODE_AUTO = 1;
    public static final int NETWORK_SELECTION_MODE_MANUAL = 2;
    public static final int NETWORK_SELECTION_MODE_UNKNOWN = 0;
    public static final int NETWORK_TYPE_1xRTT = 7;
    @SystemApi
    public static final long NETWORK_TYPE_BITMASK_1xRTT = 64L;
    @SystemApi
    public static final long NETWORK_TYPE_BITMASK_CDMA = 8L;
    @SystemApi
    public static final long NETWORK_TYPE_BITMASK_EDGE = 2L;
    @SystemApi
    public static final long NETWORK_TYPE_BITMASK_EHRPD = 8192L;
    @SystemApi
    public static final long NETWORK_TYPE_BITMASK_EVDO_0 = 16L;
    @SystemApi
    public static final long NETWORK_TYPE_BITMASK_EVDO_A = 32L;
    @SystemApi
    public static final long NETWORK_TYPE_BITMASK_EVDO_B = 2048L;
    @SystemApi
    public static final long NETWORK_TYPE_BITMASK_GPRS = 1L;
    @SystemApi
    public static final long NETWORK_TYPE_BITMASK_GSM = 32768L;
    @SystemApi
    public static final long NETWORK_TYPE_BITMASK_HSDPA = 128L;
    @SystemApi
    public static final long NETWORK_TYPE_BITMASK_HSPA = 512L;
    @SystemApi
    public static final long NETWORK_TYPE_BITMASK_HSPAP = 16384L;
    @SystemApi
    public static final long NETWORK_TYPE_BITMASK_HSUPA = 256L;
    @SystemApi
    public static final long NETWORK_TYPE_BITMASK_IWLAN = 131072L;
    @SystemApi
    public static final long NETWORK_TYPE_BITMASK_LTE = 4096L;
    @SystemApi
    public static final long NETWORK_TYPE_BITMASK_LTE_CA = 262144L;
    @SystemApi
    public static final long NETWORK_TYPE_BITMASK_NR = 524288L;
    @SystemApi
    public static final long NETWORK_TYPE_BITMASK_TD_SCDMA = 65536L;
    @SystemApi
    public static final long NETWORK_TYPE_BITMASK_UMTS = 4L;
    @SystemApi
    public static final long NETWORK_TYPE_BITMASK_UNKNOWN = 0L;
    public static final int NETWORK_TYPE_CDMA = 4;
    public static final int NETWORK_TYPE_EDGE = 2;
    public static final int NETWORK_TYPE_EHRPD = 14;
    public static final int NETWORK_TYPE_EVDO_0 = 5;
    public static final int NETWORK_TYPE_EVDO_A = 6;
    public static final int NETWORK_TYPE_EVDO_B = 12;
    public static final int NETWORK_TYPE_GPRS = 1;
    public static final int NETWORK_TYPE_GSM = 16;
    public static final int NETWORK_TYPE_HSDPA = 8;
    public static final int NETWORK_TYPE_HSPA = 10;
    public static final int NETWORK_TYPE_HSPAP = 15;
    public static final int NETWORK_TYPE_HSUPA = 9;
    public static final int NETWORK_TYPE_IDEN = 11;
    public static final int NETWORK_TYPE_IWLAN = 18;
    public static final int NETWORK_TYPE_LTE = 13;
    @UnsupportedAppUsage
    public static final int NETWORK_TYPE_LTE_CA = 19;
    public static final int NETWORK_TYPE_NR = 20;
    public static final int NETWORK_TYPE_TD_SCDMA = 17;
    public static final int NETWORK_TYPE_UMTS = 3;
    public static final int NETWORK_TYPE_UNKNOWN = 0;
    public static final int OTASP_NEEDED = 2;
    public static final int OTASP_NOT_NEEDED = 3;
    public static final int OTASP_SIM_UNPROVISIONED = 5;
    public static final int OTASP_UNINITIALIZED = 0;
    public static final int OTASP_UNKNOWN = 1;
    public static final String PHONE_PROCESS_NAME = "com.android.phone";
    public static final int PHONE_TYPE_CDMA = 2;
    public static final int PHONE_TYPE_GSM = 1;
    public static final int PHONE_TYPE_NONE = 0;
    public static final int PHONE_TYPE_SIP = 3;
    @SystemApi
    public static final int RADIO_POWER_OFF = 0;
    @SystemApi
    public static final int RADIO_POWER_ON = 1;
    @SystemApi
    public static final int RADIO_POWER_UNAVAILABLE = 2;
    @SystemApi
    public static final int SET_CARRIER_RESTRICTION_ERROR = 2;
    @SystemApi
    public static final int SET_CARRIER_RESTRICTION_NOT_SUPPORTED = 1;
    @SystemApi
    public static final int SET_CARRIER_RESTRICTION_SUCCESS = 0;
    public static final int SET_OPPORTUNISTIC_SUB_INACTIVE_SUBSCRIPTION = 2;
    public static final int SET_OPPORTUNISTIC_SUB_SUCCESS = 0;
    public static final int SET_OPPORTUNISTIC_SUB_VALIDATION_FAILED = 1;
    @SystemApi
    public static final int SIM_ACTIVATION_STATE_ACTIVATED = 2;
    @SystemApi
    public static final int SIM_ACTIVATION_STATE_ACTIVATING = 1;
    @SystemApi
    public static final int SIM_ACTIVATION_STATE_DEACTIVATED = 3;
    @SystemApi
    public static final int SIM_ACTIVATION_STATE_RESTRICTED = 4;
    @SystemApi
    public static final int SIM_ACTIVATION_STATE_UNKNOWN = 0;
    public static final int SIM_STATE_ABSENT = 1;
    public static final int SIM_STATE_CARD_IO_ERROR = 8;
    public static final int SIM_STATE_CARD_RESTRICTED = 9;
    @SystemApi
    public static final int SIM_STATE_LOADED = 10;
    public static final int SIM_STATE_NETWORK_LOCKED = 4;
    public static final int SIM_STATE_NOT_READY = 6;
    public static final int SIM_STATE_PERM_DISABLED = 7;
    public static final int SIM_STATE_PIN_REQUIRED = 2;
    @SystemApi
    public static final int SIM_STATE_PRESENT = 11;
    public static final int SIM_STATE_PUK_REQUIRED = 3;
    public static final int SIM_STATE_READY = 5;
    public static final int SIM_STATE_UNKNOWN = 0;
    @SystemApi
    public static final int SRVCC_STATE_HANDOVER_CANCELED = 3;
    @SystemApi
    public static final int SRVCC_STATE_HANDOVER_COMPLETED = 1;
    @SystemApi
    public static final int SRVCC_STATE_HANDOVER_FAILED = 2;
    @SystemApi
    public static final int SRVCC_STATE_HANDOVER_NONE = -1;
    @SystemApi
    public static final int SRVCC_STATE_HANDOVER_STARTED = 0;
    private static final String TAG = "TelephonyManager";
    public static final int UNINITIALIZED_CARD_ID = -2;
    public static final int UNKNOWN_CARRIER_ID = -1;
    public static final int UNKNOWN_CARRIER_ID_LIST_VERSION = -1;
    public static final int UNSUPPORTED_CARD_ID = -1;
    public static final int UPDATE_AVAILABLE_NETWORKS_ABORTED = 2;
    public static final int UPDATE_AVAILABLE_NETWORKS_INVALID_ARGUMENTS = 3;
    public static final int UPDATE_AVAILABLE_NETWORKS_NO_CARRIER_PRIVILEGE = 4;
    public static final int UPDATE_AVAILABLE_NETWORKS_SUCCESS = 0;
    public static final int UPDATE_AVAILABLE_NETWORKS_UNKNOWN_FAILURE = 1;
    public static final int USSD_ERROR_SERVICE_UNAVAIL = -2;
    public static final String USSD_RESPONSE = "USSD_RESPONSE";
    public static final int USSD_RETURN_FAILURE = -1;
    public static final int USSD_RETURN_SUCCESS = 100;
    public static final String VVM_TYPE_CVVM = "vvm_type_cvvm";
    public static final String VVM_TYPE_OMTP = "vvm_type_omtp";
    private static String multiSimConfig;
    private static TelephonyManager sInstance;
    private static final String sKernelCmdLine;
    private static final String sLteOnCdmaProductType;
    private static final Pattern sProductTypePattern;
    private final Context mContext;
    private final int mSubId;
    @UnsupportedAppUsage
    private SubscriptionManager mSubscriptionManager;
    private TelephonyScanManager mTelephonyScanManager;

    static {
        multiSimConfig = SystemProperties.get("persist.radio.multisim.config");
        sInstance = new TelephonyManager();
        EXTRA_STATE_IDLE = PhoneConstants.State.IDLE.toString();
        EXTRA_STATE_RINGING = PhoneConstants.State.RINGING.toString();
        EXTRA_STATE_OFFHOOK = PhoneConstants.State.OFFHOOK.toString();
        sKernelCmdLine = TelephonyManager.getProcCmdLine();
        sProductTypePattern = Pattern.compile("\\sproduct_type\\s*=\\s*(\\w+)");
        sLteOnCdmaProductType = SystemProperties.get("telephony.lteOnCdmaProductType", "");
    }

    @UnsupportedAppUsage
    private TelephonyManager() {
        this.mContext = null;
        this.mSubId = -1;
    }

    @UnsupportedAppUsage
    public TelephonyManager(Context context) {
        this(context, Integer.MAX_VALUE);
    }

    @UnsupportedAppUsage
    public TelephonyManager(Context context, int n) {
        this.mSubId = n;
        Context context2 = context.getApplicationContext();
        this.mContext = context2 != null ? context2 : context;
        this.mSubscriptionManager = SubscriptionManager.from(this.mContext);
    }

    public static String dataStateToString(int n) {
        if (n != 0) {
            if (n != 1) {
                if (n != 2) {
                    if (n != 3) {
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("UNKNOWN(");
                        stringBuilder.append(n);
                        stringBuilder.append(")");
                        return stringBuilder.toString();
                    }
                    return "SUSPENDED";
                }
                return "CONNECTED";
            }
            return "CONNECTING";
        }
        return "DISCONNECTED";
    }

    @UnsupportedAppUsage(maxTargetSdk=28)
    public static TelephonyManager from(Context context) {
        return (TelephonyManager)context.getSystemService("phone");
    }

    private String getBasebandVersionLegacy(int n) {
        if (SubscriptionManager.isValidPhoneId(n)) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("gsm.version.baseband");
            String string2 = n == 0 ? "" : Integer.toString(n);
            stringBuilder.append(string2);
            return SystemProperties.get(stringBuilder.toString());
        }
        return null;
    }

    @Deprecated
    @UnsupportedAppUsage(maxTargetSdk=28)
    public static TelephonyManager getDefault() {
        return sInstance;
    }

    private IOns getIOns() {
        return IOns.Stub.asInterface(ServiceManager.getService("ions"));
    }

    @UnsupportedAppUsage(maxTargetSdk=28)
    private ITelephony getITelephony() {
        return ITelephony.Stub.asInterface(ServiceManager.getService("phone"));
    }

    @UnsupportedAppUsage
    public static int getIntAtIndex(ContentResolver arrstring, String string2, int n) throws Settings.SettingNotFoundException {
        if ((arrstring = Settings.Global.getString((ContentResolver)arrstring, string2)) != null) {
            arrstring = arrstring.split(",");
            if (n >= 0 && n < arrstring.length && arrstring[n] != null) {
                try {
                    n = Integer.parseInt(arrstring[n]);
                    return n;
                }
                catch (NumberFormatException numberFormatException) {
                    // empty catch block
                }
            }
        }
        throw new Settings.SettingNotFoundException(string2);
    }

    @UnsupportedAppUsage
    public static int getLteOnCdmaModeStatic() {
        int n;
        CharSequence charSequence = "";
        int n2 = n = SystemProperties.getInt("telephony.lteOnCdmaDevice", -1);
        Object object = charSequence;
        int n3 = n2;
        if (n2 == -1) {
            object = sProductTypePattern.matcher(sKernelCmdLine);
            if (((Matcher)object).find()) {
                n3 = sLteOnCdmaProductType.equals(object = ((Matcher)object).group(1)) ? 1 : 0;
            } else {
                n3 = 0;
                object = charSequence;
            }
        }
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append("getLteOnCdmaMode=");
        ((StringBuilder)charSequence).append(n3);
        ((StringBuilder)charSequence).append(" curVal=");
        ((StringBuilder)charSequence).append(n);
        ((StringBuilder)charSequence).append(" product_type='");
        ((StringBuilder)charSequence).append((String)object);
        ((StringBuilder)charSequence).append("' lteOnCdmaProductType='");
        ((StringBuilder)charSequence).append(sLteOnCdmaProductType);
        ((StringBuilder)charSequence).append("'");
        Rlog.d(TAG, ((StringBuilder)charSequence).toString());
        return n3;
    }

    @SystemApi
    public static long getMaxNumberVerificationTimeoutMillis() {
        return 60000L;
    }

    private String getNaiBySubscriberId(int n) {
        Object object;
        block5 : {
            object = this.getSubscriberInfo();
            if (object != null) break block5;
            return null;
        }
        try {
            object = object.getNaiForSubscriber(n, this.mContext.getOpPackageName());
            if (Log.isLoggable(TAG, 2)) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Nai = ");
                stringBuilder.append((String)object);
                Rlog.v(TAG, stringBuilder.toString());
            }
            return object;
        }
        catch (NullPointerException nullPointerException) {
            return null;
        }
        catch (RemoteException remoteException) {
            return null;
        }
    }

    @UnsupportedAppUsage
    public static int getNetworkClass(int n) {
        switch (n) {
            default: {
                return 0;
            }
            case 13: 
            case 18: 
            case 19: {
                return 3;
            }
            case 3: 
            case 5: 
            case 6: 
            case 8: 
            case 9: 
            case 10: 
            case 12: 
            case 14: 
            case 15: 
            case 17: {
                return 2;
            }
            case 1: 
            case 2: 
            case 4: 
            case 7: 
            case 11: 
            case 16: 
        }
        return 1;
    }

    @UnsupportedAppUsage
    public static String getNetworkTypeName(int n) {
        switch (n) {
            default: {
                return "UNKNOWN";
            }
            case 20: {
                return "NR";
            }
            case 19: {
                return "LTE_CA";
            }
            case 18: {
                return "IWLAN";
            }
            case 17: {
                return "TD_SCDMA";
            }
            case 16: {
                return "GSM";
            }
            case 15: {
                return "HSPA+";
            }
            case 14: {
                return "CDMA - eHRPD";
            }
            case 13: {
                return "LTE";
            }
            case 12: {
                return "CDMA - EvDo rev. B";
            }
            case 11: {
                return "iDEN";
            }
            case 10: {
                return "HSPA";
            }
            case 9: {
                return "HSUPA";
            }
            case 8: {
                return "HSDPA";
            }
            case 7: {
                return "CDMA - 1xRTT";
            }
            case 6: {
                return "CDMA - EvDo rev. A";
            }
            case 5: {
                return "CDMA - EvDo rev. 0";
            }
            case 4: {
                return "CDMA";
            }
            case 3: {
                return "UMTS";
            }
            case 2: {
                return "EDGE";
            }
            case 1: 
        }
        return "GPRS";
    }

    private String getOpPackageName() {
        Context context = this.mContext;
        if (context != null) {
            return context.getOpPackageName();
        }
        return ActivityThread.currentOpPackageName();
    }

    private int getPhoneId() {
        return SubscriptionManager.getPhoneId(this.getSubId());
    }

    @UnsupportedAppUsage
    private int getPhoneId(int n) {
        return SubscriptionManager.getPhoneId(this.getSubId(n));
    }

    @UnsupportedAppUsage
    public static int getPhoneType(int n) {
        switch (n) {
            default: {
                return 1;
            }
            case 11: {
                if (TelephonyManager.getLteOnCdmaModeStatic() == 1) {
                    return 2;
                }
                return 1;
            }
            case 7: 
            case 8: 
            case 21: {
                return 2;
            }
            case 4: 
            case 5: 
            case 6: {
                return 2;
            }
            case 0: 
            case 1: 
            case 2: 
            case 3: 
            case 9: 
            case 10: 
            case 12: 
            case 13: 
            case 14: 
            case 15: 
            case 16: 
            case 17: 
            case 18: 
            case 19: 
            case 20: 
            case 22: 
        }
        return 1;
    }

    private int getPhoneTypeFromNetworkType() {
        return this.getPhoneTypeFromNetworkType(this.getPhoneId());
    }

    private int getPhoneTypeFromNetworkType(int n) {
        String string2 = TelephonyManager.getTelephonyProperty(n, "ro.telephony.default_network", null);
        if (string2 != null && !string2.isEmpty()) {
            return TelephonyManager.getPhoneType(Integer.parseInt(string2));
        }
        return 0;
    }

    private int getPhoneTypeFromProperty() {
        return this.getPhoneTypeFromProperty(this.getPhoneId());
    }

    @UnsupportedAppUsage
    private int getPhoneTypeFromProperty(int n) {
        String string2 = TelephonyManager.getTelephonyProperty(n, "gsm.current.phone-type", null);
        if (string2 != null && !string2.isEmpty()) {
            return Integer.parseInt(string2);
        }
        return this.getPhoneTypeFromNetworkType(n);
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    @UnsupportedAppUsage
    private static String getProcCmdLine() {
        block12 : {
            block11 : {
                var0 = "";
                var1_3 = null;
                var2_4 /* !! */  = null;
                var3_5 = var2_4 /* !! */ ;
                var4_6 /* !! */  = var1_3;
                var3_5 = var2_4 /* !! */ ;
                var4_6 /* !! */  = var1_3;
                var5_8 = new FileInputStream("/proc/cmdline");
                var3_5 = var5_8;
                var4_6 /* !! */  = var5_8;
                var1_3 = new byte[2048];
                var3_5 = var5_8;
                var4_6 /* !! */  = var5_8;
                var6_10 = var5_8.read(var1_3);
                var4_6 /* !! */  = var0;
                if (var6_10 > 0) {
                    var3_5 = var5_8;
                    var4_6 /* !! */  = var5_8;
                    var3_5 = var5_8;
                    var4_6 /* !! */  = var5_8;
                    var4_6 /* !! */  = var2_4 /* !! */  = new String(var1_3, 0, var6_10);
                }
                var3_5 = var4_6 /* !! */ ;
                var5_8.close();
                var0 = var4_6 /* !! */ ;
lbl27: // 3 sources:
                do {
                    var3_5 = var0;
                    break block11;
                    break;
                } while (true);
                {
                    catch (IOException var0_1) {
                        var0 = var3_5;
                    }
                    ** GOTO lbl27
                    catch (Throwable var0_2) {
                        break block12;
                    }
                    catch (IOException var5_9) {}
                    var3_5 = var4_6 /* !! */ ;
                    {
                        var3_5 = var4_6 /* !! */ ;
                        var2_4 /* !! */  = new StringBuilder();
                        var3_5 = var4_6 /* !! */ ;
                        var2_4 /* !! */ .append("No /proc/cmdline exception=");
                        var3_5 = var4_6 /* !! */ ;
                        var2_4 /* !! */ .append(var5_9);
                        var3_5 = var4_6 /* !! */ ;
                        Rlog.d("TelephonyManager", var2_4 /* !! */ .toString());
                        var3_5 = var0;
                        if (var4_6 /* !! */  == null) break block11;
                        var3_5 = var0;
                    }
                    {
                        var4_6 /* !! */ .close();
                        ** continue;
                    }
                }
            }
            var0 = new StringBuilder();
            var0.append("/proc/cmdline=");
            var0.append((String)var3_5);
            Rlog.d("TelephonyManager", var0.toString());
            return var3_5;
        }
        if (var3_5 == null) throw var0_2;
        try {
            var3_5.close();
            throw var0_2;
        }
        catch (IOException var4_7) {
            // empty catch block
        }
        throw var0_2;
    }

    private int getSimStateIncludingLoaded() {
        int n = this.getSlotIndex();
        if (n < 0) {
            for (int i = 0; i < this.getPhoneCount(); ++i) {
                int n2 = this.getSimState(i);
                if (n2 == 1) continue;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("getSimState: default sim:");
                stringBuilder.append(n);
                stringBuilder.append(", sim state for slotIndex=");
                stringBuilder.append(i);
                stringBuilder.append(" is ");
                stringBuilder.append(n2);
                stringBuilder.append(", return state as unknown");
                Rlog.d(TAG, stringBuilder.toString());
                return 0;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("getSimState: default sim:");
            stringBuilder.append(n);
            stringBuilder.append(", all SIMs absent, return state as absent");
            Rlog.d(TAG, stringBuilder.toString());
            return 1;
        }
        return SubscriptionManager.getSimStateForSlotIndex(n);
    }

    private int getSubId() {
        if (SubscriptionManager.isUsableSubIdValue(this.mSubId)) {
            return this.mSubId;
        }
        return SubscriptionManager.getDefaultSubscriptionId();
    }

    @UnsupportedAppUsage
    private int getSubId(int n) {
        if (SubscriptionManager.isUsableSubIdValue(this.mSubId)) {
            return this.mSubId;
        }
        return n;
    }

    private int getSubIdForPhoneAccountHandle(PhoneAccountHandle phoneAccountHandle) {
        int n;
        block3 : {
            int n2 = -1;
            ITelecomService iTelecomService = this.getTelecomService();
            n = n2;
            if (iTelecomService == null) break block3;
            try {
                n = this.getSubIdForPhoneAccount(iTelecomService.getPhoneAccount(phoneAccountHandle));
            }
            catch (RemoteException remoteException) {
                n = n2;
            }
        }
        return n;
    }

    @UnsupportedAppUsage
    private IPhoneSubInfo getSubscriberInfo() {
        return IPhoneSubInfo.Stub.asInterface(ServiceManager.getService("iphonesubinfo"));
    }

    private ITelecomService getTelecomService() {
        return ITelecomService.Stub.asInterface(ServiceManager.getService("telecom"));
    }

    @UnsupportedAppUsage
    public static String getTelephonyProperty(int n, String string2, String string3) {
        block5 : {
            Object var3_3 = null;
            String[] arrstring = SystemProperties.get(string2);
            string2 = var3_3;
            if (arrstring != null) {
                string2 = var3_3;
                if (arrstring.length() > 0) {
                    arrstring = arrstring.split(",");
                    string2 = var3_3;
                    if (n >= 0) {
                        string2 = var3_3;
                        if (n < arrstring.length) {
                            string2 = var3_3;
                            if (arrstring[n] != null) {
                                string2 = arrstring[n];
                            }
                        }
                    }
                }
            }
            if (string2 != null) break block5;
            string2 = string3;
        }
        return string2;
    }

    @UnsupportedAppUsage
    public static String getTelephonyProperty(String string2, String string3) {
        block0 : {
            if (!TextUtils.isEmpty(string2 = SystemProperties.get(string2))) break block0;
            string2 = string3;
        }
        return string2;
    }

    private ITelephonyRegistry getTelephonyRegistry() {
        return ITelephonyRegistry.Stub.asInterface(ServiceManager.getService("telephony.registry"));
    }

    private boolean isImsiEncryptionRequired(int n, int n2) {
        Object object = (CarrierConfigManager)this.mContext.getSystemService("carrier_config");
        if (object == null) {
            return false;
        }
        if ((object = ((CarrierConfigManager)object).getConfigForSubId(n)) == null) {
            return false;
        }
        return TelephonyManager.isKeyEnabled(((BaseBundle)object).getInt("imsi_key_availability_int"), n2);
    }

    private static boolean isKeyEnabled(int n, int n2) {
        boolean bl = true;
        if ((n >> n2 - 1 & 1) != 1) {
            bl = false;
        }
        return bl;
    }

    private boolean isSystemProcess() {
        boolean bl = Process.myUid() == 1000;
        return bl;
    }

    static /* synthetic */ void lambda$requestNumberVerification$0(NumberVerificationCallback numberVerificationCallback) {
        numberVerificationCallback.onVerificationFailed(0);
    }

    static /* synthetic */ void lambda$updateAvailableNetworks$1(Consumer consumer) {
        consumer.accept(3);
    }

    static /* synthetic */ void lambda$updateAvailableNetworks$2(Executor executor, Consumer consumer) throws Exception {
        executor.execute(new _$$Lambda$TelephonyManager$qjhLNTc5_Bq4btM7q4y_F5cdAK0(consumer));
    }

    @UnsupportedAppUsage
    public static boolean putIntAtIndex(ContentResolver object, String string2, int n, int n2) {
        String string3 = "";
        String[] arrstring = null;
        CharSequence charSequence = Settings.Global.getString((ContentResolver)object, string2);
        if (n != Integer.MAX_VALUE) {
            if (n >= 0) {
                if (charSequence != null) {
                    arrstring = ((String)charSequence).split(",");
                }
                for (int i = 0; i < n; ++i) {
                    CharSequence charSequence2;
                    charSequence = charSequence2 = "";
                    if (arrstring != null) {
                        charSequence = charSequence2;
                        if (i < arrstring.length) {
                            charSequence = arrstring[i];
                        }
                    }
                    charSequence2 = new StringBuilder();
                    ((StringBuilder)charSequence2).append(string3);
                    ((StringBuilder)charSequence2).append((String)charSequence);
                    ((StringBuilder)charSequence2).append(",");
                    string3 = ((StringBuilder)charSequence2).toString();
                }
                charSequence = new StringBuilder();
                ((StringBuilder)charSequence).append(string3);
                ((StringBuilder)charSequence).append(n2);
                string3 = ((StringBuilder)charSequence).toString();
                charSequence = string3;
                if (arrstring != null) {
                    ++n;
                    do {
                        charSequence = string3;
                        if (n >= arrstring.length) break;
                        charSequence = new StringBuilder();
                        ((StringBuilder)charSequence).append(string3);
                        ((StringBuilder)charSequence).append(",");
                        ((StringBuilder)charSequence).append(arrstring[n]);
                        string3 = ((StringBuilder)charSequence).toString();
                        ++n;
                    } while (true);
                }
                return Settings.Global.putString((ContentResolver)object, string2, (String)charSequence);
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("putIntAtIndex index < 0 index=");
            ((StringBuilder)object).append(n);
            throw new IllegalArgumentException(((StringBuilder)object).toString());
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("putIntAtIndex index == MAX_VALUE index=");
        ((StringBuilder)object).append(n);
        throw new IllegalArgumentException(((StringBuilder)object).toString());
    }

    @UnsupportedAppUsage
    public static void setTelephonyProperty(int n, String string2, String charSequence) {
        int n2;
        CharSequence charSequence2 = "";
        String[] arrstring = null;
        CharSequence charSequence3 = SystemProperties.get(string2);
        String string3 = charSequence;
        if (charSequence == null) {
            string3 = "";
        }
        string3.replace(',', ' ');
        if (charSequence3 != null) {
            arrstring = ((String)charSequence3).split(",");
        }
        if (!SubscriptionManager.isValidPhoneId(n)) {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("setTelephonyProperty: invalid phoneId=");
            ((StringBuilder)charSequence).append(n);
            ((StringBuilder)charSequence).append(" property=");
            ((StringBuilder)charSequence).append(string2);
            ((StringBuilder)charSequence).append(" value: ");
            ((StringBuilder)charSequence).append(string3);
            ((StringBuilder)charSequence).append(" prop=");
            ((StringBuilder)charSequence).append((String)charSequence3);
            Rlog.d(TAG, ((StringBuilder)charSequence).toString());
            return;
        }
        charSequence = charSequence2;
        for (n2 = 0; n2 < n; ++n2) {
            charSequence2 = charSequence3 = "";
            if (arrstring != null) {
                charSequence2 = charSequence3;
                if (n2 < arrstring.length) {
                    charSequence2 = arrstring[n2];
                }
            }
            charSequence3 = new StringBuilder();
            ((StringBuilder)charSequence3).append((String)charSequence);
            ((StringBuilder)charSequence3).append((String)charSequence2);
            ((StringBuilder)charSequence3).append(",");
            charSequence = ((StringBuilder)charSequence3).toString();
        }
        charSequence2 = new StringBuilder();
        ((StringBuilder)charSequence2).append((String)charSequence);
        ((StringBuilder)charSequence2).append(string3);
        charSequence2 = charSequence = ((StringBuilder)charSequence2).toString();
        if (arrstring != null) {
            n2 = n + 1;
            do {
                charSequence2 = charSequence;
                if (n2 >= arrstring.length) break;
                charSequence2 = new StringBuilder();
                ((StringBuilder)charSequence2).append((String)charSequence);
                ((StringBuilder)charSequence2).append(",");
                ((StringBuilder)charSequence2).append(arrstring[n2]);
                charSequence = ((StringBuilder)charSequence2).toString();
                ++n2;
            } while (true);
        }
        n2 = ((String)charSequence2).length();
        try {
            int n3;
            n2 = n3 = ((String)charSequence2).getBytes("utf-8").length;
        }
        catch (UnsupportedEncodingException unsupportedEncodingException) {
            Rlog.d(TAG, "setTelephonyProperty: utf-8 not supported");
        }
        if (n2 > 91) {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("setTelephonyProperty: property too long phoneId=");
            ((StringBuilder)charSequence).append(n);
            ((StringBuilder)charSequence).append(" property=");
            ((StringBuilder)charSequence).append(string2);
            ((StringBuilder)charSequence).append(" value: ");
            ((StringBuilder)charSequence).append(string3);
            ((StringBuilder)charSequence).append(" propVal=");
            ((StringBuilder)charSequence).append((String)charSequence2);
            Rlog.d(TAG, ((StringBuilder)charSequence).toString());
            return;
        }
        SystemProperties.set(string2, (String)charSequence2);
    }

    public static void setTelephonyProperty(String string2, String charSequence) {
        String string3 = charSequence;
        if (charSequence == null) {
            string3 = "";
        }
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append("setTelephonyProperty: success property=");
        ((StringBuilder)charSequence).append(string2);
        ((StringBuilder)charSequence).append(" value: ");
        ((StringBuilder)charSequence).append(string3);
        Rlog.d(TAG, ((StringBuilder)charSequence).toString());
        SystemProperties.set(string2, string3);
    }

    @SystemApi
    @Deprecated
    public void answerRingingCall() {
    }

    @SystemApi
    @Deprecated
    public void call(String string2, String string3) {
        block3 : {
            ITelephony iTelephony = this.getITelephony();
            if (iTelephony == null) break block3;
            try {
                iTelephony.call(string2, string3);
            }
            catch (RemoteException remoteException) {
                Log.e(TAG, "Error calling ITelephony#call", remoteException);
            }
        }
    }

    public boolean canChangeDtmfToneLength() {
        block4 : {
            ITelephony iTelephony = this.getITelephony();
            if (iTelephony == null) break block4;
            try {
                boolean bl = iTelephony.canChangeDtmfToneLength(this.mSubId, this.getOpPackageName());
                return bl;
            }
            catch (SecurityException securityException) {
                Log.e(TAG, "Permission error calling ITelephony#canChangeDtmfToneLength", securityException);
            }
            catch (RemoteException remoteException) {
                Log.e(TAG, "Error calling ITelephony#canChangeDtmfToneLength", remoteException);
            }
        }
        return false;
    }

    public void carrierActionReportDefaultNetworkStatus(int n, boolean bl) {
        block3 : {
            ITelephony iTelephony = this.getITelephony();
            if (iTelephony == null) break block3;
            try {
                iTelephony.carrierActionReportDefaultNetworkStatus(n, bl);
            }
            catch (RemoteException remoteException) {
                Log.e(TAG, "Error calling ITelephony#carrierActionReportDefaultNetworkStatus", remoteException);
            }
        }
    }

    public void carrierActionResetAll(int n) {
        block3 : {
            ITelephony iTelephony = this.getITelephony();
            if (iTelephony == null) break block3;
            try {
                iTelephony.carrierActionResetAll(n);
            }
            catch (RemoteException remoteException) {
                Log.e(TAG, "Error calling ITelephony#carrierActionResetAll", remoteException);
            }
        }
    }

    public void carrierActionSetRadioEnabled(int n, boolean bl) {
        block3 : {
            ITelephony iTelephony = this.getITelephony();
            if (iTelephony == null) break block3;
            try {
                iTelephony.carrierActionSetRadioEnabled(n, bl);
            }
            catch (RemoteException remoteException) {
                Log.e(TAG, "Error calling ITelephony#carrierActionSetRadioEnabled", remoteException);
            }
        }
    }

    @SystemApi
    @SuppressLint(value={"Doclava125"})
    public int checkCarrierPrivilegesForPackage(String string2) {
        block4 : {
            ITelephony iTelephony = this.getITelephony();
            if (iTelephony == null) break block4;
            try {
                int n = iTelephony.checkCarrierPrivilegesForPackage(this.getSubId(), string2);
                return n;
            }
            catch (NullPointerException nullPointerException) {
                Rlog.e(TAG, "checkCarrierPrivilegesForPackage NPE", nullPointerException);
            }
            catch (RemoteException remoteException) {
                Rlog.e(TAG, "checkCarrierPrivilegesForPackage RemoteException", remoteException);
            }
        }
        return 0;
    }

    @SystemApi
    @SuppressLint(value={"Doclava125"})
    public int checkCarrierPrivilegesForPackageAnyPhone(String string2) {
        block4 : {
            ITelephony iTelephony = this.getITelephony();
            if (iTelephony == null) break block4;
            try {
                int n = iTelephony.checkCarrierPrivilegesForPackageAnyPhone(string2);
                return n;
            }
            catch (NullPointerException nullPointerException) {
                Rlog.e(TAG, "checkCarrierPrivilegesForPackageAnyPhone NPE", nullPointerException);
            }
            catch (RemoteException remoteException) {
                Rlog.e(TAG, "checkCarrierPrivilegesForPackageAnyPhone RemoteException", remoteException);
            }
        }
        return 0;
    }

    public TelephonyManager createForPhoneAccountHandle(PhoneAccountHandle phoneAccountHandle) {
        int n = this.getSubIdForPhoneAccountHandle(phoneAccountHandle);
        if (!SubscriptionManager.isValidSubscriptionId(n)) {
            return null;
        }
        return new TelephonyManager(this.mContext, n);
    }

    public TelephonyManager createForSubscriptionId(int n) {
        return new TelephonyManager(this.mContext, n);
    }

    @SystemApi
    @SuppressLint(value={"Doclava125"})
    public void dial(String string2) {
        block3 : {
            ITelephony iTelephony = this.getITelephony();
            if (iTelephony == null) break block3;
            try {
                iTelephony.dial(string2);
            }
            catch (RemoteException remoteException) {
                Log.e(TAG, "Error calling ITelephony#dial", remoteException);
            }
        }
    }

    @SystemApi
    public boolean disableDataConnectivity() {
        block3 : {
            ITelephony iTelephony = this.getITelephony();
            if (iTelephony == null) break block3;
            try {
                boolean bl = iTelephony.disableDataConnectivity();
                return bl;
            }
            catch (RemoteException remoteException) {
                Log.e(TAG, "Error calling ITelephony#disableDataConnectivity", remoteException);
            }
        }
        return false;
    }

    public void disableIms(int n) {
        block3 : {
            Object object = this.getITelephony();
            if (object == null) break block3;
            try {
                object.disableIms(n);
            }
            catch (RemoteException remoteException) {
                object = new StringBuilder();
                ((StringBuilder)object).append("disableIms, RemoteException: ");
                ((StringBuilder)object).append(remoteException.getMessage());
                Rlog.e(TAG, ((StringBuilder)object).toString());
            }
        }
    }

    public void disableLocationUpdates() {
        this.disableLocationUpdates(this.getSubId());
    }

    public void disableLocationUpdates(int n) {
        block4 : {
            ITelephony iTelephony = this.getITelephony();
            if (iTelephony == null) break block4;
            try {
                iTelephony.disableLocationUpdatesForSubscriber(n);
            }
            catch (NullPointerException nullPointerException) {
            }
            catch (RemoteException remoteException) {
                // empty catch block
            }
        }
    }

    public void disableVisualVoicemailSmsFilter(int n) {
        block4 : {
            ITelephony iTelephony = this.getITelephony();
            if (iTelephony == null) break block4;
            try {
                iTelephony.disableVisualVoicemailSmsFilter(this.mContext.getOpPackageName(), n);
            }
            catch (NullPointerException nullPointerException) {
            }
            catch (RemoteException remoteException) {
                // empty catch block
            }
        }
    }

    public boolean doesSwitchMultiSimConfigTriggerReboot() {
        block3 : {
            ITelephony iTelephony = this.getITelephony();
            if (iTelephony == null) break block3;
            try {
                boolean bl = iTelephony.doesSwitchMultiSimConfigTriggerReboot(this.getSubId(), this.getOpPackageName());
                return bl;
            }
            catch (RemoteException remoteException) {
                Log.e(TAG, "doesSwitchMultiSimConfigTriggerReboot RemoteException", remoteException);
            }
        }
        return false;
    }

    @SystemApi
    public boolean enableDataConnectivity() {
        block3 : {
            ITelephony iTelephony = this.getITelephony();
            if (iTelephony == null) break block3;
            try {
                boolean bl = iTelephony.enableDataConnectivity();
                return bl;
            }
            catch (RemoteException remoteException) {
                Log.e(TAG, "Error calling ITelephony#enableDataConnectivity", remoteException);
            }
        }
        return false;
    }

    public void enableIms(int n) {
        block3 : {
            Object object = this.getITelephony();
            if (object == null) break block3;
            try {
                object.enableIms(n);
            }
            catch (RemoteException remoteException) {
                object = new StringBuilder();
                ((StringBuilder)object).append("enableIms, RemoteException: ");
                ((StringBuilder)object).append(remoteException.getMessage());
                Rlog.e(TAG, ((StringBuilder)object).toString());
            }
        }
    }

    public void enableLocationUpdates() {
        this.enableLocationUpdates(this.getSubId());
    }

    public void enableLocationUpdates(int n) {
        block4 : {
            ITelephony iTelephony = this.getITelephony();
            if (iTelephony == null) break block4;
            try {
                iTelephony.enableLocationUpdatesForSubscriber(n);
            }
            catch (NullPointerException nullPointerException) {
            }
            catch (RemoteException remoteException) {
                // empty catch block
            }
        }
    }

    @SystemApi
    public boolean enableModemForSlot(int n, boolean bl) {
        boolean bl2;
        block3 : {
            boolean bl3 = false;
            bl2 = false;
            ITelephony iTelephony = this.getITelephony();
            if (iTelephony == null) break block3;
            try {
                bl2 = iTelephony.enableModemForSlot(n, bl);
            }
            catch (RemoteException remoteException) {
                Log.e(TAG, "enableModem RemoteException", remoteException);
                bl2 = bl3;
            }
        }
        return bl2;
    }

    @SystemApi
    public void enableVideoCalling(boolean bl) {
        block3 : {
            ITelephony iTelephony = this.getITelephony();
            if (iTelephony == null) break block3;
            try {
                iTelephony.enableVideoCalling(bl);
            }
            catch (RemoteException remoteException) {
                Log.e(TAG, "Error calling ITelephony#enableVideoCalling", remoteException);
            }
        }
    }

    public void enableVisualVoicemailSmsFilter(int n, VisualVoicemailSmsFilterSettings visualVoicemailSmsFilterSettings) {
        if (visualVoicemailSmsFilterSettings != null) {
            block5 : {
                ITelephony iTelephony = this.getITelephony();
                if (iTelephony == null) break block5;
                try {
                    iTelephony.enableVisualVoicemailSmsFilter(this.mContext.getOpPackageName(), n, visualVoicemailSmsFilterSettings);
                }
                catch (NullPointerException nullPointerException) {
                }
                catch (RemoteException remoteException) {
                    // empty catch block
                }
            }
            return;
        }
        throw new IllegalArgumentException("Settings cannot be null");
    }

    @SystemApi
    @Deprecated
    public boolean endCall() {
        return false;
    }

    public void factoryReset(int n) {
        block3 : {
            Object object = new StringBuilder();
            ((StringBuilder)object).append("factoryReset: subId=");
            ((StringBuilder)object).append(n);
            Log.d(TAG, ((StringBuilder)object).toString());
            object = this.getITelephony();
            if (object == null) break block3;
            try {
                object.factoryReset(n);
            }
            catch (RemoteException remoteException) {
                // empty catch block
            }
        }
    }

    public VisualVoicemailSmsFilterSettings getActiveVisualVoicemailSmsFilterSettings(int n) {
        block4 : {
            Object object = this.getITelephony();
            if (object == null) break block4;
            try {
                object = object.getActiveVisualVoicemailSmsFilterSettings(n);
                return object;
            }
            catch (NullPointerException nullPointerException) {
            }
            catch (RemoteException remoteException) {
                // empty catch block
            }
        }
        return null;
    }

    @SystemApi
    public String getAidForAppType(int n) {
        return this.getAidForAppType(this.getSubId(), n);
    }

    public String getAidForAppType(int n, int n2) {
        block3 : {
            Object object = this.getITelephony();
            if (object == null) break block3;
            try {
                object = object.getAidForAppType(n, n2);
                return object;
            }
            catch (RemoteException remoteException) {
                Log.e(TAG, "Error calling ITelephony#getAidForAppType", remoteException);
            }
        }
        return null;
    }

    public List<CellInfo> getAllCellInfo() {
        Object object;
        block4 : {
            object = this.getITelephony();
            if (object != null) break block4;
            return null;
        }
        try {
            object = object.getAllCellInfo(this.getOpPackageName());
            return object;
        }
        catch (NullPointerException nullPointerException) {
        }
        catch (RemoteException remoteException) {
            // empty catch block
        }
        return null;
    }

    @SystemApi
    @Deprecated
    public List<CarrierIdentifier> getAllowedCarriers(int n) {
        CarrierRestrictionRules carrierRestrictionRules;
        if (SubscriptionManager.isValidPhoneId(n) && (carrierRestrictionRules = this.getCarrierRestrictionRules()) != null) {
            return carrierRestrictionRules.getAllowedCarriers();
        }
        return new ArrayList<CarrierIdentifier>(0);
    }

    public CellNetworkScanResult getAvailableNetworks() {
        block4 : {
            Object object = this.getITelephony();
            if (object == null) break block4;
            try {
                object = object.getCellNetworkScanResults(this.getSubId(), this.getOpPackageName());
                return object;
            }
            catch (NullPointerException nullPointerException) {
                Rlog.e(TAG, "getAvailableNetworks NPE", nullPointerException);
            }
            catch (RemoteException remoteException) {
                Rlog.e(TAG, "getAvailableNetworks RemoteException", remoteException);
            }
        }
        return new CellNetworkScanResult(4, null);
    }

    public String getBasebandVersion() {
        return this.getBasebandVersionForPhone(this.getPhoneId());
    }

    public String getBasebandVersionForPhone(int n) {
        String string2 = this.getBasebandVersionLegacy(n);
        if (string2 != null && !string2.isEmpty()) {
            this.setBasebandVersionForPhone(n, string2);
        }
        return TelephonyManager.getTelephonyProperty(n, "gsm.version.baseband", "");
    }

    public int getCallState() {
        block3 : {
            ITelecomService iTelecomService = this.getTelecomService();
            if (iTelecomService == null) break block3;
            try {
                int n = iTelecomService.getCallState();
                return n;
            }
            catch (RemoteException remoteException) {
                Log.e(TAG, "Error calling ITelecomService#getCallState", remoteException);
            }
        }
        return 0;
    }

    @UnsupportedAppUsage
    public int getCallState(int n) {
        return this.getCallStateForSlot(SubscriptionManager.getPhoneId(n));
    }

    public int getCallStateForSlot(int n) {
        ITelephony iTelephony;
        block4 : {
            iTelephony = this.getITelephony();
            if (iTelephony != null) break block4;
            return 0;
        }
        try {
            n = iTelephony.getCallStateForSlot(n);
            return n;
        }
        catch (NullPointerException nullPointerException) {
            return 0;
        }
        catch (RemoteException remoteException) {
            return 0;
        }
    }

    public int getCardIdForDefaultEuicc() {
        ITelephony iTelephony;
        block3 : {
            try {
                iTelephony = this.getITelephony();
                if (iTelephony != null) break block3;
                return -2;
            }
            catch (RemoteException remoteException) {
                return -2;
            }
        }
        int n = iTelephony.getCardIdForDefaultEuicc(this.mSubId, this.mContext.getOpPackageName());
        return n;
    }

    public PersistableBundle getCarrierConfig() {
        return this.mContext.getSystemService(CarrierConfigManager.class).getConfigForSubId(this.getSubId());
    }

    public int getCarrierIdFromMccMnc(String string2) {
        block3 : {
            ITelephony iTelephony = this.getITelephony();
            if (iTelephony == null) break block3;
            try {
                int n = iTelephony.getCarrierIdFromMccMnc(this.getSlotIndex(), string2, false);
                return n;
            }
            catch (RemoteException remoteException) {
                // empty catch block
            }
        }
        return -1;
    }

    public int getCarrierIdFromSimMccMnc() {
        block3 : {
            ITelephony iTelephony = this.getITelephony();
            if (iTelephony == null) break block3;
            try {
                int n = iTelephony.getCarrierIdFromMccMnc(this.getSlotIndex(), this.getSimOperator(), true);
                return n;
            }
            catch (RemoteException remoteException) {
                // empty catch block
            }
        }
        return -1;
    }

    public int getCarrierIdListVersion() {
        block3 : {
            ITelephony iTelephony = this.getITelephony();
            if (iTelephony == null) break block3;
            try {
                int n = iTelephony.getCarrierIdListVersion(this.getSubId());
                return n;
            }
            catch (RemoteException remoteException) {
                // empty catch block
            }
        }
        return -1;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public ImsiEncryptionInfo getCarrierInfoForImsiEncryption(int n) {
        try {
            Object object = this.getSubscriberInfo();
            if (object == null) {
                Rlog.e(TAG, "IMSI error: Subscriber Info is null");
                return null;
            }
            int n2 = this.getSubId(SubscriptionManager.getDefaultDataSubscriptionId());
            if (n != 1 && n != 2) {
                object = new IllegalArgumentException("IMSI error: Invalid key type");
                throw object;
            }
            if ((object = object.getCarrierInfoForImsiEncryption(n2, n, this.mContext.getOpPackageName())) != null) return object;
            if (!this.isImsiEncryptionRequired(n2, n)) {
                return object;
            }
            Rlog.e(TAG, "IMSI error: key is required but not found");
            object = new IllegalArgumentException("IMSI error: key is required but not found");
            throw object;
        }
        catch (NullPointerException nullPointerException) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("getCarrierInfoForImsiEncryption NullPointerException");
            stringBuilder.append(nullPointerException);
            Rlog.e(TAG, stringBuilder.toString());
            return null;
        }
        catch (RemoteException remoteException) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("getCarrierInfoForImsiEncryption RemoteException");
            stringBuilder.append(remoteException);
            Rlog.e(TAG, stringBuilder.toString());
        }
        return null;
    }

    @SystemApi
    public List<String> getCarrierPackageNamesForIntent(Intent intent) {
        return this.getCarrierPackageNamesForIntentAndPhone(intent, this.getPhoneId());
    }

    @SystemApi
    public List<String> getCarrierPackageNamesForIntentAndPhone(Intent object, int n) {
        block4 : {
            ITelephony iTelephony = this.getITelephony();
            if (iTelephony == null) break block4;
            try {
                object = iTelephony.getCarrierPackageNamesForIntentAndPhone((Intent)object, n);
                return object;
            }
            catch (NullPointerException nullPointerException) {
                Rlog.e(TAG, "getCarrierPackageNamesForIntentAndPhone NPE", nullPointerException);
            }
            catch (RemoteException remoteException) {
                Rlog.e(TAG, "getCarrierPackageNamesForIntentAndPhone RemoteException", remoteException);
            }
        }
        return null;
    }

    @SystemApi
    public CarrierRestrictionRules getCarrierRestrictionRules() {
        block4 : {
            Object object = this.getITelephony();
            if (object == null) break block4;
            try {
                object = object.getAllowedCarriers();
                return object;
            }
            catch (NullPointerException nullPointerException) {
                Log.e(TAG, "Error calling ITelephony#getAllowedCarriers", nullPointerException);
            }
            catch (RemoteException remoteException) {
                Log.e(TAG, "Error calling ITelephony#getAllowedCarriers", remoteException);
            }
        }
        return null;
    }

    public int getCdmaEriIconIndex() {
        return this.getCdmaEriIconIndex(this.getSubId());
    }

    @UnsupportedAppUsage
    public int getCdmaEriIconIndex(int n) {
        ITelephony iTelephony;
        block4 : {
            iTelephony = this.getITelephony();
            if (iTelephony != null) break block4;
            return -1;
        }
        try {
            n = iTelephony.getCdmaEriIconIndexForSubscriber(n, this.getOpPackageName());
            return n;
        }
        catch (NullPointerException nullPointerException) {
            return -1;
        }
        catch (RemoteException remoteException) {
            return -1;
        }
    }

    public int getCdmaEriIconMode() {
        return this.getCdmaEriIconMode(this.getSubId());
    }

    @UnsupportedAppUsage
    public int getCdmaEriIconMode(int n) {
        ITelephony iTelephony;
        block4 : {
            iTelephony = this.getITelephony();
            if (iTelephony != null) break block4;
            return -1;
        }
        try {
            n = iTelephony.getCdmaEriIconModeForSubscriber(n, this.getOpPackageName());
            return n;
        }
        catch (NullPointerException nullPointerException) {
            return -1;
        }
        catch (RemoteException remoteException) {
            return -1;
        }
    }

    public String getCdmaEriText() {
        return this.getCdmaEriText(this.getSubId());
    }

    @UnsupportedAppUsage
    public String getCdmaEriText(int n) {
        Object object;
        block4 : {
            object = this.getITelephony();
            if (object != null) break block4;
            return null;
        }
        try {
            object = object.getCdmaEriTextForSubscriber(n, this.getOpPackageName());
            return object;
        }
        catch (NullPointerException nullPointerException) {
            return null;
        }
        catch (RemoteException remoteException) {
            return null;
        }
    }

    @SystemApi
    public String getCdmaMdn() {
        return this.getCdmaMdn(this.getSubId());
    }

    @SystemApi
    public String getCdmaMdn(int n) {
        Object object;
        block4 : {
            object = this.getITelephony();
            if (object != null) break block4;
            return null;
        }
        try {
            object = object.getCdmaMdn(n);
            return object;
        }
        catch (NullPointerException nullPointerException) {
            return null;
        }
        catch (RemoteException remoteException) {
            return null;
        }
    }

    @SystemApi
    public String getCdmaMin() {
        return this.getCdmaMin(this.getSubId());
    }

    @SystemApi
    public String getCdmaMin(int n) {
        Object object;
        block4 : {
            object = this.getITelephony();
            if (object != null) break block4;
            return null;
        }
        try {
            object = object.getCdmaMin(n);
            return object;
        }
        catch (NullPointerException nullPointerException) {
            return null;
        }
        catch (RemoteException remoteException) {
            return null;
        }
    }

    @SystemApi
    public String getCdmaPrlVersion() {
        return this.getCdmaPrlVersion(this.getSubId());
    }

    public String getCdmaPrlVersion(int n) {
        block3 : {
            Object object = this.getITelephony();
            if (object == null) break block3;
            try {
                object = object.getCdmaPrlVersion(n);
                return object;
            }
            catch (RemoteException remoteException) {
                Log.e(TAG, "Error calling ITelephony#getCdmaPrlVersion", remoteException);
            }
        }
        return null;
    }

    public int getCdmaRoamingMode() {
        int n;
        block3 : {
            int n2 = -1;
            ITelephony iTelephony = this.getITelephony();
            n = n2;
            if (iTelephony == null) break block3;
            try {
                n = iTelephony.getCdmaRoamingMode(this.getSubId());
            }
            catch (RemoteException remoteException) {
                Log.e(TAG, "Error calling ITelephony#getCdmaRoamingMode", remoteException);
                n = n2;
            }
        }
        return n;
    }

    @Deprecated
    public CellLocation getCellLocation() {
        block9 : {
            block10 : {
                Object object;
                block8 : {
                    try {
                        object = this.getITelephony();
                        if (object != null) break block8;
                    }
                    catch (RemoteException remoteException) {
                        object = new StringBuilder();
                        ((StringBuilder)object).append("getCellLocation returning null due to RemoteException ");
                        ((StringBuilder)object).append(remoteException);
                        Rlog.d(TAG, ((StringBuilder)object).toString());
                        return null;
                    }
                    Rlog.d(TAG, "getCellLocation returning null because telephony is null");
                    return null;
                }
                object = object.getCellLocation(this.mContext.getOpPackageName());
                if (object == null) break block9;
                if (((BaseBundle)object).isEmpty()) break block9;
                if ((object = CellLocation.newFromBundle((Bundle)object)) == null) break block10;
                if (((CellLocation)object).isEmpty()) break block10;
                return object;
            }
            Rlog.d(TAG, "getCellLocation returning null because CellLocation is empty or phone type doesn't match CellLocation type");
            return null;
        }
        Rlog.d(TAG, "getCellLocation returning null because CellLocation is unavailable");
        return null;
    }

    public List<String> getCertsFromCarrierPrivilegeAccessRules() {
        block3 : {
            Object object = this.getITelephony();
            if (object == null) break block3;
            try {
                object = object.getCertsFromCarrierPrivilegeAccessRules(this.getSubId());
                return object;
            }
            catch (RemoteException remoteException) {
                // empty catch block
            }
        }
        return null;
    }

    public List<ClientRequestStats> getClientRequestStats(int n) {
        block3 : {
            Object object = this.getITelephony();
            if (object == null) break block3;
            try {
                object = object.getClientRequestStats(this.getOpPackageName(), n);
                return object;
            }
            catch (RemoteException remoteException) {
                Log.e(TAG, "Error calling ITelephony#getClientRequestStats", remoteException);
            }
        }
        return null;
    }

    @SystemApi
    public int getCurrentPhoneType() {
        return this.getCurrentPhoneType(this.getSubId());
    }

    @SystemApi
    public int getCurrentPhoneType(int n) {
        n = n == -1 ? 0 : SubscriptionManager.getPhoneId(n);
        return this.getCurrentPhoneTypeForSlot(n);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public int getCurrentPhoneTypeForSlot(int n) {
        ITelephony iTelephony = this.getITelephony();
        if (iTelephony == null) return this.getPhoneTypeFromProperty(n);
        try {
            return iTelephony.getActivePhoneTypeForSlot(n);
        }
        catch (NullPointerException nullPointerException) {
            return this.getPhoneTypeFromProperty(n);
        }
        catch (RemoteException remoteException) {
            return this.getPhoneTypeFromProperty(n);
        }
    }

    @SystemApi
    public int getDataActivationState() {
        return this.getDataActivationState(this.getSubId());
    }

    public int getDataActivationState(int n) {
        block4 : {
            ITelephony iTelephony = this.getITelephony();
            if (iTelephony == null) break block4;
            try {
                n = iTelephony.getDataActivationState(n, this.getOpPackageName());
                return n;
            }
            catch (NullPointerException nullPointerException) {
            }
            catch (RemoteException remoteException) {
                // empty catch block
            }
        }
        return 0;
    }

    public int getDataActivity() {
        ITelephony iTelephony;
        block4 : {
            iTelephony = this.getITelephony();
            if (iTelephony != null) break block4;
            return 0;
        }
        try {
            int n = iTelephony.getDataActivity();
            return n;
        }
        catch (NullPointerException nullPointerException) {
            return 0;
        }
        catch (RemoteException remoteException) {
            return 0;
        }
    }

    @SystemApi
    @Deprecated
    public boolean getDataEnabled() {
        return this.isDataEnabled();
    }

    @SystemApi
    @Deprecated
    public boolean getDataEnabled(int n) {
        boolean bl;
        block4 : {
            boolean bl2 = false;
            boolean bl3 = false;
            ITelephony iTelephony = this.getITelephony();
            bl = bl3;
            if (iTelephony == null) break block4;
            try {
                bl = iTelephony.isUserDataEnabled(n);
            }
            catch (NullPointerException nullPointerException) {
                bl = bl2;
            }
            catch (RemoteException remoteException) {
                Log.e(TAG, "Error calling ITelephony#isUserDataEnabled", remoteException);
                bl = bl3;
            }
        }
        return bl;
    }

    public int getDataNetworkType() {
        return this.getDataNetworkType(this.getSubId(SubscriptionManager.getDefaultDataSubscriptionId()));
    }

    @UnsupportedAppUsage(maxTargetSdk=28)
    public int getDataNetworkType(int n) {
        block4 : {
            ITelephony iTelephony = this.getITelephony();
            if (iTelephony == null) break block4;
            try {
                n = iTelephony.getDataNetworkTypeForSubscriber(n, this.getOpPackageName());
                return n;
            }
            catch (NullPointerException nullPointerException) {
                return 0;
            }
            catch (RemoteException remoteException) {
                return 0;
            }
        }
        return 0;
    }

    public int getDataState() {
        ITelephony iTelephony;
        block4 : {
            iTelephony = this.getITelephony();
            if (iTelephony != null) break block4;
            return 0;
        }
        try {
            int n = iTelephony.getDataState();
            return n;
        }
        catch (NullPointerException nullPointerException) {
            return 0;
        }
        catch (RemoteException remoteException) {
            return 0;
        }
    }

    @Deprecated
    public String getDeviceId() {
        Object object;
        block4 : {
            object = this.getITelephony();
            if (object != null) break block4;
            return null;
        }
        try {
            object = object.getDeviceId(this.mContext.getOpPackageName());
            return object;
        }
        catch (NullPointerException nullPointerException) {
            return null;
        }
        catch (RemoteException remoteException) {
            return null;
        }
    }

    @Deprecated
    public String getDeviceId(int n) {
        Object object;
        block4 : {
            object = this.getSubscriberInfo();
            if (object != null) break block4;
            return null;
        }
        try {
            object = object.getDeviceIdForPhone(n, this.mContext.getOpPackageName());
            return object;
        }
        catch (NullPointerException nullPointerException) {
            return null;
        }
        catch (RemoteException remoteException) {
            return null;
        }
    }

    public String getDeviceSoftwareVersion() {
        return this.getDeviceSoftwareVersion(this.getSlotIndex());
    }

    @UnsupportedAppUsage
    public String getDeviceSoftwareVersion(int n) {
        Object object = this.getITelephony();
        if (object == null) {
            return null;
        }
        try {
            object = object.getDeviceSoftwareVersionForSlot(n, this.getOpPackageName());
            return object;
        }
        catch (NullPointerException nullPointerException) {
            return null;
        }
        catch (RemoteException remoteException) {
            return null;
        }
    }

    @SystemApi
    public boolean getEmergencyCallbackMode() {
        return this.getEmergencyCallbackMode(this.getSubId());
    }

    public boolean getEmergencyCallbackMode(int n) {
        ITelephony iTelephony;
        block3 : {
            try {
                iTelephony = this.getITelephony();
                if (iTelephony != null) break block3;
                return false;
            }
            catch (RemoteException remoteException) {
                Log.e(TAG, "Error calling ITelephony#getEmergencyCallbackMode", remoteException);
                return false;
            }
        }
        boolean bl = iTelephony.getEmergencyCallbackMode(n);
        return bl;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public Map<Integer, List<EmergencyNumber>> getEmergencyNumberList() {
        HashMap<Integer, List<EmergencyNumber>> hashMap = new HashMap<Integer, List<EmergencyNumber>>();
        try {
            Object object = this.getITelephony();
            if (object != null) {
                return object.getEmergencyNumberList(this.mContext.getOpPackageName());
            }
            object = new IllegalStateException("telephony service is null.");
            throw object;
        }
        catch (RemoteException remoteException) {
            Log.e(TAG, "getEmergencyNumberList RemoteException", remoteException);
            remoteException.rethrowAsRuntimeException();
            return hashMap;
        }
    }

    public Map<Integer, List<EmergencyNumber>> getEmergencyNumberList(int n) {
        Map<Integer, List<EmergencyNumber>> map;
        Object object;
        Map<Integer, List<EmergencyNumber>> map2;
        block16 : {
            block17 : {
                map = new HashMap<Integer, List<EmergencyNumber>>();
                map2 = map;
                object = this.getITelephony();
                if (object == null) break block16;
                map2 = map;
                map = object.getEmergencyNumberList(this.mContext.getOpPackageName());
                if (map == null) break block17;
                map2 = map;
                Iterator<Integer> iterator = map.keySet().iterator();
                block14 : do {
                    map2 = map;
                    if (!iterator.hasNext()) break;
                    map2 = map;
                    List<EmergencyNumber> list = map.get(iterator.next());
                    map2 = map;
                    Iterator<EmergencyNumber> iterator2 = list.iterator();
                    do {
                        map2 = map;
                        if (!iterator2.hasNext()) continue block14;
                        map2 = map;
                        object = iterator2.next();
                        map2 = map;
                        if (((EmergencyNumber)object).isInEmergencyServiceCategories(n)) continue;
                        map2 = map;
                        list.remove(object);
                        continue;
                        break;
                    } while (true);
                    break;
                } while (true);
            }
            return map;
        }
        map2 = map;
        map2 = map;
        try {
            object = new IllegalStateException("telephony service is null.");
            map2 = map;
        }
        catch (RemoteException remoteException) {
            Log.e(TAG, "getEmergencyNumberList with Categories RemoteException", remoteException);
            remoteException.rethrowAsRuntimeException();
            return map2;
        }
        throw object;
    }

    public String getEsn() {
        return this.getEsn(this.getSubId());
    }

    public String getEsn(int n) {
        block3 : {
            Object object = this.getITelephony();
            if (object == null) break block3;
            try {
                object = object.getEsn(n);
                return object;
            }
            catch (RemoteException remoteException) {
                Log.e(TAG, "Error calling ITelephony#getEsn", remoteException);
            }
        }
        return null;
    }

    public String[] getForbiddenPlmns() {
        return this.getForbiddenPlmns(this.getSubId(), 2);
    }

    public String[] getForbiddenPlmns(int n, int n2) {
        String[] arrstring;
        block4 : {
            arrstring = this.getITelephony();
            if (arrstring != null) break block4;
            return null;
        }
        try {
            arrstring = arrstring.getForbiddenPlmns(n, n2, this.mContext.getOpPackageName());
            return arrstring;
        }
        catch (NullPointerException nullPointerException) {
            return null;
        }
        catch (RemoteException remoteException) {
            return null;
        }
    }

    public String getGroupIdLevel1() {
        Object object;
        block4 : {
            object = this.getSubscriberInfo();
            if (object != null) break block4;
            return null;
        }
        try {
            object = object.getGroupIdLevel1ForSubscriber(this.getSubId(), this.mContext.getOpPackageName());
            return object;
        }
        catch (NullPointerException nullPointerException) {
            return null;
        }
        catch (RemoteException remoteException) {
            return null;
        }
    }

    @UnsupportedAppUsage
    public String getGroupIdLevel1(int n) {
        Object object;
        block4 : {
            object = this.getSubscriberInfo();
            if (object != null) break block4;
            return null;
        }
        try {
            object = object.getGroupIdLevel1ForSubscriber(n, this.mContext.getOpPackageName());
            return object;
        }
        catch (NullPointerException nullPointerException) {
            return null;
        }
        catch (RemoteException remoteException) {
            return null;
        }
    }

    @UnsupportedAppUsage
    public String getIccAuthentication(int n, int n2, int n3, String string2) {
        IPhoneSubInfo iPhoneSubInfo;
        block4 : {
            iPhoneSubInfo = this.getSubscriberInfo();
            if (iPhoneSubInfo != null) break block4;
            return null;
        }
        try {
            string2 = iPhoneSubInfo.getIccSimChallengeResponse(n, n2, n3, string2);
            return string2;
        }
        catch (NullPointerException nullPointerException) {
            return null;
        }
        catch (RemoteException remoteException) {
            return null;
        }
    }

    public String getIccAuthentication(int n, int n2, String string2) {
        return this.getIccAuthentication(this.getSubId(), n, n2, string2);
    }

    public String getImei() {
        return this.getImei(this.getSlotIndex());
    }

    public String getImei(int n) {
        Object object = this.getITelephony();
        if (object == null) {
            return null;
        }
        try {
            object = object.getImeiForSlot(n, this.getOpPackageName());
            return object;
        }
        catch (NullPointerException nullPointerException) {
            return null;
        }
        catch (RemoteException remoteException) {
            return null;
        }
    }

    @UnsupportedAppUsage
    public IImsConfig getImsConfig(int n, int n2) {
        block3 : {
            Object object = this.getITelephony();
            if (object == null) break block3;
            try {
                object = object.getImsConfig(n, n2);
                return object;
            }
            catch (RemoteException remoteException) {
                object = new StringBuilder();
                ((StringBuilder)object).append("getImsRegistration, RemoteException: ");
                ((StringBuilder)object).append(remoteException.getMessage());
                Rlog.e(TAG, ((StringBuilder)object).toString());
            }
        }
        return null;
    }

    public IImsMmTelFeature getImsMmTelFeatureAndListen(int n, IImsServiceFeatureCallback object) {
        block3 : {
            ITelephony iTelephony = this.getITelephony();
            if (iTelephony == null) break block3;
            try {
                object = iTelephony.getMmTelFeatureAndListen(n, (IImsServiceFeatureCallback)object);
                return object;
            }
            catch (RemoteException remoteException) {
                object = new StringBuilder();
                ((StringBuilder)object).append("getImsMmTelFeatureAndListen, RemoteException: ");
                ((StringBuilder)object).append(remoteException.getMessage());
                Rlog.e(TAG, ((StringBuilder)object).toString());
            }
        }
        return null;
    }

    public IImsRcsFeature getImsRcsFeatureAndListen(int n, IImsServiceFeatureCallback object) {
        block3 : {
            ITelephony iTelephony = this.getITelephony();
            if (iTelephony == null) break block3;
            try {
                object = iTelephony.getRcsFeatureAndListen(n, (IImsServiceFeatureCallback)object);
                return object;
            }
            catch (RemoteException remoteException) {
                object = new StringBuilder();
                ((StringBuilder)object).append("getImsRcsFeatureAndListen, RemoteException: ");
                ((StringBuilder)object).append(remoteException.getMessage());
                Rlog.e(TAG, ((StringBuilder)object).toString());
            }
        }
        return null;
    }

    public int getImsRegTechnologyForMmTel() {
        try {
            int n = this.getITelephony().getImsRegTechnologyForMmTel(this.getSubId());
            return n;
        }
        catch (RemoteException remoteException) {
            return -1;
        }
    }

    @UnsupportedAppUsage
    public IImsRegistration getImsRegistration(int n, int n2) {
        block3 : {
            IInterface iInterface = this.getITelephony();
            if (iInterface == null) break block3;
            try {
                iInterface = iInterface.getImsRegistration(n, n2);
                return iInterface;
            }
            catch (RemoteException remoteException) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("getImsRegistration, RemoteException: ");
                stringBuilder.append(remoteException.getMessage());
                Rlog.e(TAG, stringBuilder.toString());
            }
        }
        return null;
    }

    @SystemApi
    public String getIsimDomain() {
        Object object;
        block4 : {
            object = this.getSubscriberInfo();
            if (object != null) break block4;
            return null;
        }
        try {
            object = object.getIsimDomain(this.getSubId());
            return object;
        }
        catch (NullPointerException nullPointerException) {
            return null;
        }
        catch (RemoteException remoteException) {
            return null;
        }
    }

    @UnsupportedAppUsage
    public String getIsimImpi() {
        Object object;
        block4 : {
            object = this.getSubscriberInfo();
            if (object != null) break block4;
            return null;
        }
        try {
            object = object.getIsimImpi(this.getSubId());
            return object;
        }
        catch (NullPointerException nullPointerException) {
            return null;
        }
        catch (RemoteException remoteException) {
            return null;
        }
    }

    @UnsupportedAppUsage
    public String[] getIsimImpu() {
        String[] arrstring;
        block4 : {
            arrstring = this.getSubscriberInfo();
            if (arrstring != null) break block4;
            return null;
        }
        try {
            arrstring = arrstring.getIsimImpu(this.getSubId());
            return arrstring;
        }
        catch (NullPointerException nullPointerException) {
            return null;
        }
        catch (RemoteException remoteException) {
            return null;
        }
    }

    @SystemApi
    public String getIsimIst() {
        Object object;
        block4 : {
            object = this.getSubscriberInfo();
            if (object != null) break block4;
            return null;
        }
        try {
            object = object.getIsimIst(this.getSubId());
            return object;
        }
        catch (NullPointerException nullPointerException) {
            return null;
        }
        catch (RemoteException remoteException) {
            return null;
        }
    }

    @UnsupportedAppUsage
    public String[] getIsimPcscf() {
        String[] arrstring;
        block4 : {
            arrstring = this.getSubscriberInfo();
            if (arrstring != null) break block4;
            return null;
        }
        try {
            arrstring = arrstring.getIsimPcscf(this.getSubId());
            return arrstring;
        }
        catch (NullPointerException nullPointerException) {
            return null;
        }
        catch (RemoteException remoteException) {
            return null;
        }
    }

    public String getLine1AlphaTag() {
        return this.getLine1AlphaTag(this.getSubId());
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @UnsupportedAppUsage
    public String getLine1AlphaTag(int n) {
        Object object;
        IPhoneSubInfo iPhoneSubInfo = null;
        IPhoneSubInfo iPhoneSubInfo2 = null;
        try {
            ITelephony iTelephony = this.getITelephony();
            object = iPhoneSubInfo2;
            if (iTelephony != null) {
                object = iTelephony.getLine1AlphaTagForDisplay(n, this.getOpPackageName());
            }
        }
        catch (NullPointerException nullPointerException) {
            object = iPhoneSubInfo;
        }
        catch (RemoteException remoteException) {
            object = iPhoneSubInfo2;
        }
        if (object != null) {
            return object;
        }
        try {
            object = this.getSubscriberInfo();
            if (object != null) return object.getLine1AlphaTagForSubscriber(n, this.getOpPackageName());
            return null;
        }
        catch (NullPointerException nullPointerException) {
            return null;
        }
        catch (RemoteException remoteException) {
            return null;
        }
    }

    public String getLine1Number() {
        return this.getLine1Number(this.getSubId());
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @UnsupportedAppUsage
    public String getLine1Number(int n) {
        Object object;
        IPhoneSubInfo iPhoneSubInfo = null;
        IPhoneSubInfo iPhoneSubInfo2 = null;
        try {
            ITelephony iTelephony = this.getITelephony();
            object = iPhoneSubInfo2;
            if (iTelephony != null) {
                object = iTelephony.getLine1NumberForDisplay(n, this.mContext.getOpPackageName());
            }
        }
        catch (NullPointerException nullPointerException) {
            object = iPhoneSubInfo;
        }
        catch (RemoteException remoteException) {
            object = iPhoneSubInfo2;
        }
        if (object != null) {
            return object;
        }
        try {
            object = this.getSubscriberInfo();
            if (object != null) return object.getLine1NumberForSubscriber(n, this.mContext.getOpPackageName());
            return null;
        }
        catch (NullPointerException nullPointerException) {
            return null;
        }
        catch (RemoteException remoteException) {
            return null;
        }
    }

    public String getLocaleFromDefaultSim() {
        block3 : {
            Object object = this.getITelephony();
            if (object == null) break block3;
            try {
                object = object.getSimLocaleForSubscriber(this.getSubId());
                return object;
            }
            catch (RemoteException remoteException) {
                // empty catch block
            }
        }
        return null;
    }

    @SystemApi
    public Map<Integer, Integer> getLogicalToPhysicalSlotMapping() {
        HashMap<Integer, Integer> hashMap;
        block6 : {
            int n;
            hashMap = new HashMap<Integer, Integer>();
            int[] arrn = this.getITelephony();
            if (arrn == null) break block6;
            try {
                arrn = arrn.getSlotsMapping();
                n = 0;
            }
            catch (RemoteException remoteException) {
                Log.e(TAG, "getSlotsMapping RemoteException", remoteException);
            }
            do {
                if (n < arrn.length) {
                    hashMap.put(n, arrn[n]);
                    ++n;
                    continue;
                }
                break;
            } while (true);
        }
        return hashMap;
    }

    @UnsupportedAppUsage
    public int getLteOnCdmaMode() {
        return this.getLteOnCdmaMode(this.getSubId());
    }

    @UnsupportedAppUsage
    public int getLteOnCdmaMode(int n) {
        ITelephony iTelephony;
        block4 : {
            iTelephony = this.getITelephony();
            if (iTelephony != null) break block4;
            return -1;
        }
        try {
            n = iTelephony.getLteOnCdmaModeForSubscriber(n, this.getOpPackageName());
            return n;
        }
        catch (NullPointerException nullPointerException) {
            return -1;
        }
        catch (RemoteException remoteException) {
            return -1;
        }
    }

    public String getManufacturerCode() {
        return this.getManufacturerCode(this.getSlotIndex());
    }

    public String getManufacturerCode(int n) {
        Object object = this.getITelephony();
        if (object == null) {
            return null;
        }
        try {
            object = object.getManufacturerCodeForSlot(n);
            return object;
        }
        catch (NullPointerException nullPointerException) {
            return null;
        }
        catch (RemoteException remoteException) {
            return null;
        }
    }

    public String getMeid() {
        return this.getMeid(this.getSlotIndex());
    }

    public String getMeid(int n) {
        Object object = this.getITelephony();
        if (object == null) {
            return null;
        }
        try {
            object = object.getMeidForSlot(n, this.getOpPackageName());
            return object;
        }
        catch (NullPointerException nullPointerException) {
            return null;
        }
        catch (RemoteException remoteException) {
            return null;
        }
    }

    @UnsupportedAppUsage
    public String[] getMergedSubscriberIds() {
        block4 : {
            String[] arrstring = this.getITelephony();
            if (arrstring == null) break block4;
            try {
                arrstring = arrstring.getMergedSubscriberIds(this.getSubId(), this.getOpPackageName());
                return arrstring;
            }
            catch (NullPointerException nullPointerException) {
            }
            catch (RemoteException remoteException) {
                // empty catch block
            }
        }
        return null;
    }

    public String getMmsUAProfUrl() {
        block4 : {
            Object object = this.getITelephony();
            if (object == null) break block4;
            try {
                object = object.getMmsUAProfUrl(this.getSubId());
                return object;
            }
            catch (NullPointerException nullPointerException) {
            }
            catch (RemoteException remoteException) {
                // empty catch block
            }
        }
        return null;
    }

    public String getMmsUserAgent() {
        block4 : {
            Object object = this.getITelephony();
            if (object == null) break block4;
            try {
                object = object.getMmsUserAgent(this.getSubId());
                return object;
            }
            catch (NullPointerException nullPointerException) {
            }
            catch (RemoteException remoteException) {
                // empty catch block
            }
        }
        return null;
    }

    @UnsupportedAppUsage
    public String getMsisdn() {
        return this.getMsisdn(this.getSubId());
    }

    @UnsupportedAppUsage(maxTargetSdk=28)
    public String getMsisdn(int n) {
        Object object;
        block4 : {
            object = this.getSubscriberInfo();
            if (object != null) break block4;
            return null;
        }
        try {
            object = object.getMsisdnForSubscriber(n, this.getOpPackageName());
            return object;
        }
        catch (NullPointerException nullPointerException) {
            return null;
        }
        catch (RemoteException remoteException) {
            return null;
        }
    }

    @UnsupportedAppUsage
    public MultiSimVariants getMultiSimConfiguration() {
        String string2 = SystemProperties.get("persist.radio.multisim.config");
        if (string2.equals("dsds")) {
            return MultiSimVariants.DSDS;
        }
        if (string2.equals("dsda")) {
            return MultiSimVariants.DSDA;
        }
        if (string2.equals("tsts")) {
            return MultiSimVariants.TSTS;
        }
        return MultiSimVariants.UNKNOWN;
    }

    public String getNai() {
        return this.getNaiBySubscriberId(this.getSubId());
    }

    @UnsupportedAppUsage
    public String getNai(int n) {
        int[] arrn = SubscriptionManager.getSubId(n);
        if (arrn == null) {
            return null;
        }
        return this.getNaiBySubscriberId(arrn[0]);
    }

    @Deprecated
    public List<NeighboringCellInfo> getNeighboringCellInfo() {
        Object object;
        block4 : {
            object = this.getITelephony();
            if (object != null) break block4;
            return null;
        }
        try {
            object = object.getNeighboringCellInfo(this.mContext.getOpPackageName());
            return object;
        }
        catch (NullPointerException nullPointerException) {
            return null;
        }
        catch (RemoteException remoteException) {
            return null;
        }
    }

    public String getNetworkCountryIso() {
        return this.getNetworkCountryIsoForPhone(this.getPhoneId());
    }

    @UnsupportedAppUsage
    public String getNetworkCountryIso(int n) {
        return this.getNetworkCountryIsoForPhone(this.getPhoneId(n));
    }

    @UnsupportedAppUsage
    public String getNetworkCountryIsoForPhone(int n) {
        Object object;
        block3 : {
            try {
                object = this.getITelephony();
                if (object != null) break block3;
                return "";
            }
            catch (RemoteException remoteException) {
                return "";
            }
        }
        object = object.getNetworkCountryIsoForPhone(n);
        return object;
    }

    public String getNetworkOperator() {
        return this.getNetworkOperatorForPhone(this.getPhoneId());
    }

    @UnsupportedAppUsage(maxTargetSdk=28)
    public String getNetworkOperator(int n) {
        return this.getNetworkOperatorForPhone(SubscriptionManager.getPhoneId(n));
    }

    @UnsupportedAppUsage
    public String getNetworkOperatorForPhone(int n) {
        return TelephonyManager.getTelephonyProperty(n, "gsm.operator.numeric", "");
    }

    public String getNetworkOperatorName() {
        return this.getNetworkOperatorName(this.getSubId());
    }

    @UnsupportedAppUsage(maxTargetSdk=28)
    public String getNetworkOperatorName(int n) {
        return TelephonyManager.getTelephonyProperty(SubscriptionManager.getPhoneId(n), "gsm.operator.alpha", "");
    }

    public int getNetworkSelectionMode() {
        int n;
        block3 : {
            int n2 = 0;
            n = 0;
            ITelephony iTelephony = this.getITelephony();
            if (iTelephony == null) break block3;
            try {
                n = iTelephony.getNetworkSelectionMode(this.getSubId());
            }
            catch (RemoteException remoteException) {
                Rlog.e(TAG, "getNetworkSelectionMode RemoteException", remoteException);
                n = n2;
            }
        }
        return n;
    }

    public String getNetworkSpecifier() {
        return String.valueOf(this.getSubId());
    }

    public int getNetworkType() {
        return this.getNetworkType(this.getSubId(SubscriptionManager.getDefaultDataSubscriptionId()));
    }

    @UnsupportedAppUsage(maxTargetSdk=28)
    public int getNetworkType(int n) {
        block4 : {
            ITelephony iTelephony = this.getITelephony();
            if (iTelephony == null) break block4;
            try {
                n = iTelephony.getNetworkTypeForSubscriber(n, this.getOpPackageName());
                return n;
            }
            catch (NullPointerException nullPointerException) {
                return 0;
            }
            catch (RemoteException remoteException) {
                return 0;
            }
        }
        return 0;
    }

    @UnsupportedAppUsage
    public String getNetworkTypeName() {
        return TelephonyManager.getNetworkTypeName(this.getNetworkType());
    }

    public int getNumberOfModemsWithSimultaneousDataConnections() {
        block3 : {
            ITelephony iTelephony = this.getITelephony();
            if (iTelephony == null) break block3;
            try {
                int n = iTelephony.getNumberOfModemsWithSimultaneousDataConnections(this.getSubId(), this.getOpPackageName());
                return n;
            }
            catch (RemoteException remoteException) {
                // empty catch block
            }
        }
        return 0;
    }

    public String getOtaSpNumberSchema(String string2) {
        return this.getOtaSpNumberSchemaForPhone(this.getPhoneId(), string2);
    }

    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    public String getOtaSpNumberSchemaForPhone(int n, String string2) {
        if (SubscriptionManager.isValidPhoneId(n)) {
            return TelephonyManager.getTelephonyProperty(n, "ro.cdma.otaspnumschema", string2);
        }
        return string2;
    }

    public List<String> getPackagesWithCarrierPrivileges() {
        block4 : {
            Object object = this.getITelephony();
            if (object == null) break block4;
            try {
                object = object.getPackagesWithCarrierPrivileges(this.getPhoneId());
                return object;
            }
            catch (NullPointerException nullPointerException) {
                Rlog.e(TAG, "getPackagesWithCarrierPrivileges NPE", nullPointerException);
            }
            catch (RemoteException remoteException) {
                Rlog.e(TAG, "getPackagesWithCarrierPrivileges RemoteException", remoteException);
            }
        }
        return Collections.EMPTY_LIST;
    }

    public List<String> getPackagesWithCarrierPrivilegesForAllPhones() {
        block4 : {
            Object object = this.getITelephony();
            if (object == null) break block4;
            try {
                object = object.getPackagesWithCarrierPrivilegesForAllPhones();
                return object;
            }
            catch (NullPointerException nullPointerException) {
                Rlog.e(TAG, "getPackagesWithCarrierPrivilegesForAllPhones NPE", nullPointerException);
            }
            catch (RemoteException remoteException) {
                Rlog.e(TAG, "getPackagesWithCarrierPrivilegesForAllPhones RemoteException", remoteException);
            }
        }
        return Collections.EMPTY_LIST;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public String[] getPcscfAddress(String arrstring) {
        try {
            ITelephony iTelephony = this.getITelephony();
            if (iTelephony != null) return iTelephony.getPcscfAddress((String)arrstring, this.getOpPackageName());
        }
        catch (RemoteException remoteException) {
            return new String[0];
        }
        return new String[0];
    }

    public PhoneAccountHandle getPhoneAccountHandleForSubscriptionId(int n) {
        PhoneAccountHandle phoneAccountHandle;
        block3 : {
            PhoneAccountHandle phoneAccountHandle2 = null;
            phoneAccountHandle = null;
            ITelephony iTelephony = this.getITelephony();
            if (iTelephony == null) break block3;
            try {
                phoneAccountHandle = iTelephony.getPhoneAccountHandleForSubscriptionId(n);
            }
            catch (RemoteException remoteException) {
                phoneAccountHandle = phoneAccountHandle2;
            }
        }
        return phoneAccountHandle;
    }

    public int getPhoneCount() {
        int n = 1;
        int n2 = 7.$SwitchMap$android$telephony$TelephonyManager$MultiSimVariants[this.getMultiSimConfiguration().ordinal()];
        if (n2 != 1) {
            if (n2 != 2 && n2 != 3) {
                if (n2 == 4) {
                    n = 3;
                }
            } else {
                n = 2;
            }
        } else {
            Object object = this.mContext;
            object = object == null ? null : (ConnectivityManager)((Context)object).getSystemService("connectivity");
            n = !this.isVoiceCapable() && !this.isSmsCapable() && object != null && !((ConnectivityManager)object).isNetworkSupported(0) ? 0 : 1;
        }
        return n;
    }

    public int getPhoneType() {
        if (!this.isVoiceCapable()) {
            return 0;
        }
        return this.getCurrentPhoneType();
    }

    @UnsupportedAppUsage
    public int getPreferredNetworkType(int n) {
        block3 : {
            ITelephony iTelephony = this.getITelephony();
            if (iTelephony == null) break block3;
            try {
                n = iTelephony.getPreferredNetworkType(n);
                return n;
            }
            catch (RemoteException remoteException) {
                Rlog.e(TAG, "getPreferredNetworkType RemoteException", remoteException);
            }
        }
        return -1;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @SystemApi
    public long getPreferredNetworkTypeBitmask() {
        block2 : {
            int n;
            try {
                ITelephony iTelephony = this.getITelephony();
                if (iTelephony == null) break block2;
                n = RadioAccessFamily.getRafFromNetworkType(iTelephony.getPreferredNetworkType(this.getSubId()));
            }
            catch (RemoteException remoteException) {
                Rlog.e(TAG, "getPreferredNetworkTypeBitmask RemoteException", remoteException);
            }
            return n;
        }
        return 0L;
    }

    public int getPreferredOpportunisticDataSubscription() {
        int n;
        block3 : {
            Object object = this.mContext;
            object = object != null ? ((Context)object).getOpPackageName() : "<unknown>";
            int n2 = -1;
            IOns iOns = this.getIOns();
            n = n2;
            if (iOns == null) break block3;
            try {
                n = iOns.getPreferredDataSubscriptionId((String)object);
            }
            catch (RemoteException remoteException) {
                Rlog.e(TAG, "getPreferredDataSubscriptionId RemoteException", remoteException);
                n = n2;
            }
        }
        return n;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public Pair<Integer, Integer> getRadioHalVersion() {
        ITelephony iTelephony = this.getITelephony();
        if (iTelephony == null) return new Pair<Integer, Integer>(-1, -1);
        int n = iTelephony.getRadioHalVersion();
        if (n != -1) return new Pair<Integer, Integer>(n / 100, n % 100);
        try {
            return new Pair<Integer, Integer>(-1, -1);
        }
        catch (RemoteException remoteException) {
            Log.e(TAG, "getRadioHalVersion() RemoteException", remoteException);
        }
        return new Pair<Integer, Integer>(-1, -1);
    }

    @SystemApi
    public int getRadioPowerState() {
        block3 : {
            ITelephony iTelephony = this.getITelephony();
            if (iTelephony == null) break block3;
            try {
                int n = iTelephony.getRadioPowerState(this.getSlotIndex(), this.mContext.getOpPackageName());
                return n;
            }
            catch (RemoteException remoteException) {
                // empty catch block
            }
        }
        return 2;
    }

    public ServiceState getServiceState() {
        return this.getServiceStateForSubscriber(this.getSubId());
    }

    @UnsupportedAppUsage(maxTargetSdk=28)
    public ServiceState getServiceStateForSubscriber(int n) {
        block4 : {
            Object object = this.getITelephony();
            if (object == null) break block4;
            try {
                object = object.getServiceStateForSubscriber(n, this.getOpPackageName());
                return object;
            }
            catch (NullPointerException nullPointerException) {
                UUID uUID = UUID.fromString("a3ab0b9d-f2aa-4baf-911d-7096c0d4645a");
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("getServiceStateForSubscriber ");
                stringBuilder.append(n);
                stringBuilder.append(" NPE");
                AnomalyReporter.reportAnomaly(uUID, stringBuilder.toString());
            }
            catch (RemoteException remoteException) {
                Log.e(TAG, "Error calling ITelephony#getServiceStateForSubscriber", remoteException);
            }
        }
        return null;
    }

    public SignalStrength getSignalStrength() {
        block3 : {
            Object object = this.getITelephony();
            if (object == null) break block3;
            try {
                object = object.getSignalStrength(this.getSubId());
                return object;
            }
            catch (RemoteException remoteException) {
                Log.e(TAG, "Error calling ITelephony#getSignalStrength", remoteException);
            }
        }
        return null;
    }

    @SystemApi
    public int getSimApplicationState() {
        int n = this.getSimStateIncludingLoaded();
        if (n != 0 && n != 1) {
            if (n != 5) {
                if (n != 8 && n != 9) {
                    return n;
                }
            } else {
                return 6;
            }
        }
        return 0;
    }

    @SystemApi
    public int getSimCardState() {
        int n = this.getSimState();
        if (n != 0 && n != 1 && n != 8 && n != 9) {
            return 11;
        }
        return n;
    }

    public int getSimCarrierId() {
        block3 : {
            ITelephony iTelephony = this.getITelephony();
            if (iTelephony == null) break block3;
            try {
                int n = iTelephony.getSubscriptionCarrierId(this.getSubId());
                return n;
            }
            catch (RemoteException remoteException) {
                // empty catch block
            }
        }
        return -1;
    }

    public CharSequence getSimCarrierIdName() {
        block3 : {
            Object object = this.getITelephony();
            if (object == null) break block3;
            try {
                object = object.getSubscriptionCarrierName(this.getSubId());
                return object;
            }
            catch (RemoteException remoteException) {
                // empty catch block
            }
        }
        return null;
    }

    @UnsupportedAppUsage
    public int getSimCount() {
        if (this.isMultiSimEnabled()) {
            return this.getPhoneCount();
        }
        return 1;
    }

    public String getSimCountryIso() {
        return this.getSimCountryIsoForPhone(this.getPhoneId());
    }

    @UnsupportedAppUsage(maxTargetSdk=28)
    public String getSimCountryIso(int n) {
        return this.getSimCountryIsoForPhone(SubscriptionManager.getPhoneId(n));
    }

    @UnsupportedAppUsage
    public String getSimCountryIsoForPhone(int n) {
        return TelephonyManager.getTelephonyProperty(n, "gsm.sim.operator.iso-country", "");
    }

    @SystemApi
    public Locale getSimLocale() {
        block4 : {
            Object object = this.getITelephony();
            if (object == null) break block4;
            try {
                object = object.getSimLocaleForSubscriber(this.getSubId());
                if (!TextUtils.isEmpty((CharSequence)object)) {
                    object = Locale.forLanguageTag((String)object);
                    return object;
                }
            }
            catch (RemoteException remoteException) {
                // empty catch block
            }
        }
        return null;
    }

    public String getSimOperator() {
        return this.getSimOperatorNumeric();
    }

    @UnsupportedAppUsage(maxTargetSdk=28)
    public String getSimOperator(int n) {
        return this.getSimOperatorNumeric(n);
    }

    public String getSimOperatorName() {
        return this.getSimOperatorNameForPhone(this.getPhoneId());
    }

    @UnsupportedAppUsage(maxTargetSdk=28)
    public String getSimOperatorName(int n) {
        return this.getSimOperatorNameForPhone(SubscriptionManager.getPhoneId(n));
    }

    @UnsupportedAppUsage
    public String getSimOperatorNameForPhone(int n) {
        return TelephonyManager.getTelephonyProperty(n, "gsm.sim.operator.alpha", "");
    }

    @UnsupportedAppUsage(maxTargetSdk=28)
    public String getSimOperatorNumeric() {
        int n;
        int n2 = n = this.mSubId;
        if (!SubscriptionManager.isUsableSubIdValue(n)) {
            n2 = n = SubscriptionManager.getDefaultDataSubscriptionId();
            if (!SubscriptionManager.isUsableSubIdValue(n)) {
                n2 = n = SubscriptionManager.getDefaultSmsSubscriptionId();
                if (!SubscriptionManager.isUsableSubIdValue(n)) {
                    n2 = n = SubscriptionManager.getDefaultVoiceSubscriptionId();
                    if (!SubscriptionManager.isUsableSubIdValue(n)) {
                        n2 = SubscriptionManager.getDefaultSubscriptionId();
                    }
                }
            }
        }
        return this.getSimOperatorNumeric(n2);
    }

    @UnsupportedAppUsage(maxTargetSdk=28)
    public String getSimOperatorNumeric(int n) {
        return this.getSimOperatorNumericForPhone(SubscriptionManager.getPhoneId(n));
    }

    @UnsupportedAppUsage(maxTargetSdk=28)
    public String getSimOperatorNumericForPhone(int n) {
        return TelephonyManager.getTelephonyProperty(n, "gsm.sim.operator.numeric", "");
    }

    public String getSimSerialNumber() {
        return this.getSimSerialNumber(this.getSubId());
    }

    @UnsupportedAppUsage
    public String getSimSerialNumber(int n) {
        Object object;
        block4 : {
            object = this.getSubscriberInfo();
            if (object != null) break block4;
            return null;
        }
        try {
            object = object.getIccSerialNumberForSubscriber(n, this.mContext.getOpPackageName());
            return object;
        }
        catch (NullPointerException nullPointerException) {
            return null;
        }
        catch (RemoteException remoteException) {
            return null;
        }
    }

    public int getSimSpecificCarrierId() {
        block3 : {
            ITelephony iTelephony = this.getITelephony();
            if (iTelephony == null) break block3;
            try {
                int n = iTelephony.getSubscriptionSpecificCarrierId(this.getSubId());
                return n;
            }
            catch (RemoteException remoteException) {
                // empty catch block
            }
        }
        return -1;
    }

    public CharSequence getSimSpecificCarrierIdName() {
        block3 : {
            Object object = this.getITelephony();
            if (object == null) break block3;
            try {
                object = object.getSubscriptionSpecificCarrierName(this.getSubId());
                return object;
            }
            catch (RemoteException remoteException) {
                // empty catch block
            }
        }
        return null;
    }

    public int getSimState() {
        int n;
        int n2 = n = this.getSimStateIncludingLoaded();
        if (n == 10) {
            n2 = 5;
        }
        return n2;
    }

    public int getSimState(int n) {
        int n2;
        n = n2 = SubscriptionManager.getSimStateForSlotIndex(n);
        if (n2 == 10) {
            n = 5;
        }
        return n;
    }

    @UnsupportedAppUsage
    @VisibleForTesting
    public int getSlotIndex() {
        int n;
        int n2 = n = SubscriptionManager.getSlotIndex(this.getSubId());
        if (n == -1) {
            n2 = Integer.MAX_VALUE;
        }
        return n2;
    }

    public boolean getSmsReceiveCapable(boolean bl) {
        return this.getSmsReceiveCapableForPhone(this.getPhoneId(), bl);
    }

    public boolean getSmsReceiveCapableForPhone(int n, boolean bl) {
        if (SubscriptionManager.isValidPhoneId(n)) {
            return Boolean.parseBoolean(TelephonyManager.getTelephonyProperty(n, "telephony.sms.receive", String.valueOf(bl)));
        }
        return bl;
    }

    public boolean getSmsSendCapable(boolean bl) {
        return this.getSmsSendCapableForPhone(this.getPhoneId(), bl);
    }

    public boolean getSmsSendCapableForPhone(int n, boolean bl) {
        if (SubscriptionManager.isValidPhoneId(n)) {
            return Boolean.parseBoolean(TelephonyManager.getTelephonyProperty(n, "telephony.sms.send", String.valueOf(bl)));
        }
        return bl;
    }

    @UnsupportedAppUsage
    public int getSubIdForPhoneAccount(PhoneAccount phoneAccount) {
        int n;
        block3 : {
            int n2 = -1;
            ITelephony iTelephony = this.getITelephony();
            n = n2;
            if (iTelephony == null) break block3;
            try {
                n = iTelephony.getSubIdForPhoneAccount(phoneAccount);
            }
            catch (RemoteException remoteException) {
                n = n2;
            }
        }
        return n;
    }

    public String getSubscriberId() {
        return this.getSubscriberId(this.getSubId());
    }

    @UnsupportedAppUsage(maxTargetSdk=28)
    public String getSubscriberId(int n) {
        Object object;
        block4 : {
            object = this.getSubscriberInfo();
            if (object != null) break block4;
            return null;
        }
        try {
            object = object.getSubscriberIdForSubscriber(n, this.mContext.getOpPackageName());
            return object;
        }
        catch (NullPointerException nullPointerException) {
            return null;
        }
        catch (RemoteException remoteException) {
            return null;
        }
    }

    @SystemApi
    public long getSupportedRadioAccessFamily() {
        block4 : {
            int n;
            ITelephony iTelephony = this.getITelephony();
            if (iTelephony == null) break block4;
            try {
                n = iTelephony.getRadioAccessFamily(this.getSlotIndex(), this.getOpPackageName());
            }
            catch (NullPointerException nullPointerException) {
                return 0L;
            }
            catch (RemoteException remoteException) {
                return 0L;
            }
            return n;
        }
        return 0L;
    }

    @SystemApi
    public List<TelephonyHistogram> getTelephonyHistograms() {
        block3 : {
            Object object = this.getITelephony();
            if (object == null) break block3;
            try {
                object = object.getTelephonyHistograms();
                return object;
            }
            catch (RemoteException remoteException) {
                Log.e(TAG, "Error calling ITelephony#getTelephonyHistograms", remoteException);
            }
        }
        return null;
    }

    public boolean getTetherApnRequired() {
        return this.getTetherApnRequired(this.getSubId(SubscriptionManager.getDefaultDataSubscriptionId()));
    }

    public boolean getTetherApnRequired(int n) {
        block4 : {
            ITelephony iTelephony = this.getITelephony();
            if (iTelephony == null) break block4;
            try {
                boolean bl = iTelephony.getTetherApnRequiredForSubscriber(n);
                return bl;
            }
            catch (NullPointerException nullPointerException) {
                Rlog.e(TAG, "hasMatchedTetherApnSetting NPE", nullPointerException);
            }
            catch (RemoteException remoteException) {
                Rlog.e(TAG, "hasMatchedTetherApnSetting RemoteException", remoteException);
            }
        }
        return false;
    }

    public String getTypeAllocationCode() {
        return this.getTypeAllocationCode(this.getSlotIndex());
    }

    public String getTypeAllocationCode(int n) {
        Object object = this.getITelephony();
        if (object == null) {
            return null;
        }
        try {
            object = object.getTypeAllocationCodeForSlot(n);
            return object;
        }
        catch (NullPointerException nullPointerException) {
            return null;
        }
        catch (RemoteException remoteException) {
            return null;
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public List<UiccCardInfo> getUiccCardsInfo() {
        List<UiccCardInfo> list;
        try {
            list = this.getITelephony();
            if (list != null) return list.getUiccCardsInfo(this.mContext.getOpPackageName());
        }
        catch (RemoteException remoteException) {
            list = new StringBuilder();
            ((StringBuilder)((Object)list)).append("Error in getUiccCardsInfo: ");
            ((StringBuilder)((Object)list)).append(remoteException);
            Log.e(TAG, ((StringBuilder)((Object)list)).toString());
            return new ArrayList<UiccCardInfo>();
        }
        Log.e(TAG, "Error in getUiccCardsInfo: unable to connect to Telephony service.");
        return new ArrayList<UiccCardInfo>();
    }

    @SystemApi
    public UiccSlotInfo[] getUiccSlotsInfo() {
        UiccSlotInfo[] arruiccSlotInfo;
        block3 : {
            try {
                arruiccSlotInfo = this.getITelephony();
                if (arruiccSlotInfo != null) break block3;
                return null;
            }
            catch (RemoteException remoteException) {
                return null;
            }
        }
        arruiccSlotInfo = arruiccSlotInfo.getUiccSlotsInfo();
        return arruiccSlotInfo;
    }

    public String getVisualVoicemailPackageName() {
        block4 : {
            Object object = this.getITelephony();
            if (object == null) break block4;
            try {
                object = object.getVisualVoicemailPackageName(this.mContext.getOpPackageName(), this.getSubId());
                return object;
            }
            catch (NullPointerException nullPointerException) {
            }
            catch (RemoteException remoteException) {
                // empty catch block
            }
        }
        return null;
    }

    @SystemApi
    @SuppressLint(value={"Doclava125"})
    public Bundle getVisualVoicemailSettings() {
        block4 : {
            Object object = this.getITelephony();
            if (object == null) break block4;
            try {
                object = object.getVisualVoicemailSettings(this.mContext.getOpPackageName(), this.mSubId);
                return object;
            }
            catch (NullPointerException nullPointerException) {
            }
            catch (RemoteException remoteException) {
                // empty catch block
            }
        }
        return null;
    }

    public VisualVoicemailSmsFilterSettings getVisualVoicemailSmsFilterSettings(int n) {
        block4 : {
            Object object = this.getITelephony();
            if (object == null) break block4;
            try {
                object = object.getVisualVoicemailSmsFilterSettings(this.mContext.getOpPackageName(), n);
                return object;
            }
            catch (NullPointerException nullPointerException) {
            }
            catch (RemoteException remoteException) {
                // empty catch block
            }
        }
        return null;
    }

    @SystemApi
    public int getVoiceActivationState() {
        return this.getVoiceActivationState(this.getSubId());
    }

    public int getVoiceActivationState(int n) {
        block4 : {
            ITelephony iTelephony = this.getITelephony();
            if (iTelephony == null) break block4;
            try {
                n = iTelephony.getVoiceActivationState(n, this.getOpPackageName());
                return n;
            }
            catch (NullPointerException nullPointerException) {
            }
            catch (RemoteException remoteException) {
                // empty catch block
            }
        }
        return 0;
    }

    public String getVoiceMailAlphaTag() {
        return this.getVoiceMailAlphaTag(this.getSubId());
    }

    @UnsupportedAppUsage
    public String getVoiceMailAlphaTag(int n) {
        Object object;
        block4 : {
            object = this.getSubscriberInfo();
            if (object != null) break block4;
            return null;
        }
        try {
            object = object.getVoiceMailAlphaTagForSubscriber(n, this.getOpPackageName());
            return object;
        }
        catch (NullPointerException nullPointerException) {
            return null;
        }
        catch (RemoteException remoteException) {
            return null;
        }
    }

    public String getVoiceMailNumber() {
        return this.getVoiceMailNumber(this.getSubId());
    }

    @UnsupportedAppUsage
    public String getVoiceMailNumber(int n) {
        Object object;
        block4 : {
            object = this.getSubscriberInfo();
            if (object != null) break block4;
            return null;
        }
        try {
            object = object.getVoiceMailNumberForSubscriber(n, this.getOpPackageName());
            return object;
        }
        catch (NullPointerException nullPointerException) {
            return null;
        }
        catch (RemoteException remoteException) {
            return null;
        }
    }

    @UnsupportedAppUsage
    public int getVoiceMessageCount() {
        return this.getVoiceMessageCount(this.getSubId());
    }

    @UnsupportedAppUsage
    public int getVoiceMessageCount(int n) {
        ITelephony iTelephony;
        block4 : {
            iTelephony = this.getITelephony();
            if (iTelephony != null) break block4;
            return 0;
        }
        try {
            n = iTelephony.getVoiceMessageCountForSubscriber(n, this.getOpPackageName());
            return n;
        }
        catch (NullPointerException nullPointerException) {
            return 0;
        }
        catch (RemoteException remoteException) {
            return 0;
        }
    }

    public int getVoiceNetworkType() {
        return this.getVoiceNetworkType(this.getSubId());
    }

    @UnsupportedAppUsage(maxTargetSdk=28)
    public int getVoiceNetworkType(int n) {
        block4 : {
            ITelephony iTelephony = this.getITelephony();
            if (iTelephony == null) break block4;
            try {
                n = iTelephony.getVoiceNetworkTypeForSubscriber(n, this.getOpPackageName());
                return n;
            }
            catch (NullPointerException nullPointerException) {
                return 0;
            }
            catch (RemoteException remoteException) {
                return 0;
            }
        }
        return 0;
    }

    public Uri getVoicemailRingtoneUri(PhoneAccountHandle parcelable) {
        block3 : {
            ITelephony iTelephony = this.getITelephony();
            if (iTelephony == null) break block3;
            try {
                parcelable = iTelephony.getVoicemailRingtoneUri((PhoneAccountHandle)parcelable);
                return parcelable;
            }
            catch (RemoteException remoteException) {
                Log.e(TAG, "Error calling ITelephony#getVoicemailRingtoneUri", remoteException);
            }
        }
        return null;
    }

    public NetworkStats getVtDataUsage(int n) {
        block4 : {
            boolean bl = true;
            if (n != 1) {
                bl = false;
            }
            Object object = this.getITelephony();
            if (object == null) break block4;
            try {
                object = object.getVtDataUsage(this.getSubId(), bl);
                return object;
            }
            catch (RemoteException remoteException) {
                Log.e(TAG, "Error calling ITelephony#getVtDataUsage", remoteException);
            }
        }
        return null;
    }

    @SystemApi
    public boolean handlePinMmi(String string2) {
        block3 : {
            ITelephony iTelephony = this.getITelephony();
            if (iTelephony == null) break block3;
            try {
                boolean bl = iTelephony.handlePinMmi(string2);
                return bl;
            }
            catch (RemoteException remoteException) {
                Log.e(TAG, "Error calling ITelephony#handlePinMmi", remoteException);
            }
        }
        return false;
    }

    @SystemApi
    public boolean handlePinMmiForSubscriber(int n, String string2) {
        block3 : {
            ITelephony iTelephony = this.getITelephony();
            if (iTelephony == null) break block3;
            try {
                boolean bl = iTelephony.handlePinMmiForSubscriber(n, string2);
                return bl;
            }
            catch (RemoteException remoteException) {
                Log.e(TAG, "Error calling ITelephony#handlePinMmi", remoteException);
            }
        }
        return false;
    }

    public boolean hasCarrierPrivileges() {
        return this.hasCarrierPrivileges(this.getSubId());
    }

    public boolean hasCarrierPrivileges(int n) {
        block5 : {
            boolean bl = false;
            ITelephony iTelephony = this.getITelephony();
            if (iTelephony == null) break block5;
            try {
                n = iTelephony.getCarrierPrivilegeStatus(n);
                if (n == 1) {
                    bl = true;
                }
                return bl;
            }
            catch (NullPointerException nullPointerException) {
                Rlog.e(TAG, "hasCarrierPrivileges NPE", nullPointerException);
            }
            catch (RemoteException remoteException) {
                Rlog.e(TAG, "hasCarrierPrivileges RemoteException", remoteException);
            }
        }
        return false;
    }

    public boolean hasIccCard() {
        return this.hasIccCard(this.getSlotIndex());
    }

    @UnsupportedAppUsage
    public boolean hasIccCard(int n) {
        ITelephony iTelephony;
        block4 : {
            iTelephony = this.getITelephony();
            if (iTelephony != null) break block4;
            return false;
        }
        try {
            boolean bl = iTelephony.hasIccCardUsingSlotIndex(n);
            return bl;
        }
        catch (NullPointerException nullPointerException) {
            return false;
        }
        catch (RemoteException remoteException) {
            return false;
        }
    }

    public boolean iccCloseLogicalChannel(int n) {
        return this.iccCloseLogicalChannel(this.getSubId(), n);
    }

    public boolean iccCloseLogicalChannel(int n, int n2) {
        block4 : {
            ITelephony iTelephony = this.getITelephony();
            if (iTelephony == null) break block4;
            try {
                boolean bl = iTelephony.iccCloseLogicalChannel(n, n2);
                return bl;
            }
            catch (NullPointerException nullPointerException) {
            }
            catch (RemoteException remoteException) {
                // empty catch block
            }
        }
        return false;
    }

    @SystemApi
    public boolean iccCloseLogicalChannelBySlot(int n, int n2) {
        block4 : {
            ITelephony iTelephony = this.getITelephony();
            if (iTelephony == null) break block4;
            try {
                boolean bl = iTelephony.iccCloseLogicalChannelBySlot(n, n2);
                return bl;
            }
            catch (NullPointerException nullPointerException) {
            }
            catch (RemoteException remoteException) {
                // empty catch block
            }
        }
        return false;
    }

    public byte[] iccExchangeSimIO(int n, int n2, int n3, int n4, int n5, int n6, String arrby) {
        block4 : {
            ITelephony iTelephony = this.getITelephony();
            if (iTelephony == null) break block4;
            try {
                arrby = iTelephony.iccExchangeSimIO(n, n2, n3, n4, n5, n6, (String)arrby);
                return arrby;
            }
            catch (NullPointerException nullPointerException) {
            }
            catch (RemoteException remoteException) {
                // empty catch block
            }
        }
        return null;
    }

    public byte[] iccExchangeSimIO(int n, int n2, int n3, int n4, int n5, String string2) {
        return this.iccExchangeSimIO(this.getSubId(), n, n2, n3, n4, n5, string2);
    }

    public IccOpenLogicalChannelResponse iccOpenLogicalChannel(int n, String object, int n2) {
        block4 : {
            ITelephony iTelephony = this.getITelephony();
            if (iTelephony == null) break block4;
            try {
                object = iTelephony.iccOpenLogicalChannel(n, this.getOpPackageName(), (String)object, n2);
                return object;
            }
            catch (NullPointerException nullPointerException) {
            }
            catch (RemoteException remoteException) {
                // empty catch block
            }
        }
        return null;
    }

    @Deprecated
    public IccOpenLogicalChannelResponse iccOpenLogicalChannel(String string2) {
        return this.iccOpenLogicalChannel(this.getSubId(), string2, -1);
    }

    public IccOpenLogicalChannelResponse iccOpenLogicalChannel(String string2, int n) {
        return this.iccOpenLogicalChannel(this.getSubId(), string2, n);
    }

    @SystemApi
    public IccOpenLogicalChannelResponse iccOpenLogicalChannelBySlot(int n, String object, int n2) {
        block4 : {
            ITelephony iTelephony = this.getITelephony();
            if (iTelephony == null) break block4;
            try {
                object = iTelephony.iccOpenLogicalChannelBySlot(n, this.getOpPackageName(), (String)object, n2);
                return object;
            }
            catch (NullPointerException nullPointerException) {
            }
            catch (RemoteException remoteException) {
                // empty catch block
            }
        }
        return null;
    }

    public String iccTransmitApduBasicChannel(int n, int n2, int n3, int n4, int n5, int n6, String string2) {
        block4 : {
            ITelephony iTelephony = this.getITelephony();
            if (iTelephony == null) break block4;
            try {
                string2 = iTelephony.iccTransmitApduBasicChannel(n, this.getOpPackageName(), n2, n3, n4, n5, n6, string2);
                return string2;
            }
            catch (NullPointerException nullPointerException) {
            }
            catch (RemoteException remoteException) {
                // empty catch block
            }
        }
        return "";
    }

    public String iccTransmitApduBasicChannel(int n, int n2, int n3, int n4, int n5, String string2) {
        return this.iccTransmitApduBasicChannel(this.getSubId(), n, n2, n3, n4, n5, string2);
    }

    @SystemApi
    public String iccTransmitApduBasicChannelBySlot(int n, int n2, int n3, int n4, int n5, int n6, String string2) {
        block4 : {
            ITelephony iTelephony = this.getITelephony();
            if (iTelephony == null) break block4;
            try {
                string2 = iTelephony.iccTransmitApduBasicChannelBySlot(n, this.getOpPackageName(), n2, n3, n4, n5, n6, string2);
                return string2;
            }
            catch (NullPointerException nullPointerException) {
            }
            catch (RemoteException remoteException) {
                // empty catch block
            }
        }
        return null;
    }

    public String iccTransmitApduLogicalChannel(int n, int n2, int n3, int n4, int n5, int n6, int n7, String string2) {
        block4 : {
            ITelephony iTelephony = this.getITelephony();
            if (iTelephony == null) break block4;
            try {
                string2 = iTelephony.iccTransmitApduLogicalChannel(n, n2, n3, n4, n5, n6, n7, string2);
                return string2;
            }
            catch (NullPointerException nullPointerException) {
            }
            catch (RemoteException remoteException) {
                // empty catch block
            }
        }
        return "";
    }

    public String iccTransmitApduLogicalChannel(int n, int n2, int n3, int n4, int n5, int n6, String string2) {
        return this.iccTransmitApduLogicalChannel(this.getSubId(), n, n2, n3, n4, n5, n6, string2);
    }

    @SystemApi
    public String iccTransmitApduLogicalChannelBySlot(int n, int n2, int n3, int n4, int n5, int n6, int n7, String string2) {
        block4 : {
            ITelephony iTelephony = this.getITelephony();
            if (iTelephony == null) break block4;
            try {
                string2 = iTelephony.iccTransmitApduLogicalChannelBySlot(n, n2, n3, n4, n5, n6, n7, string2);
                return string2;
            }
            catch (NullPointerException nullPointerException) {
            }
            catch (RemoteException remoteException) {
                // empty catch block
            }
        }
        return null;
    }

    @Deprecated
    public int invokeOemRilRequestRaw(byte[] arrby, byte[] arrby2) {
        block4 : {
            ITelephony iTelephony = this.getITelephony();
            if (iTelephony == null) break block4;
            try {
                int n = iTelephony.invokeOemRilRequestRaw(arrby, arrby2);
                return n;
            }
            catch (NullPointerException nullPointerException) {
            }
            catch (RemoteException remoteException) {
                // empty catch block
            }
        }
        return -1;
    }

    public boolean isApnMetered(int n) {
        block3 : {
            ITelephony iTelephony = this.getITelephony();
            if (iTelephony == null) break block3;
            try {
                boolean bl = iTelephony.isApnMetered(n, this.getSubId());
                return bl;
            }
            catch (RemoteException remoteException) {
                if (this.isSystemProcess()) break block3;
                remoteException.rethrowAsRuntimeException();
            }
        }
        return true;
    }

    public boolean isConcurrentVoiceAndDataSupported() {
        boolean bl;
        block3 : {
            ITelephony iTelephony;
            bl = false;
            try {
                iTelephony = this.getITelephony();
                if (iTelephony == null) break block3;
            }
            catch (RemoteException remoteException) {
                Log.e(TAG, "Error calling ITelephony#isConcurrentVoiceAndDataAllowed", remoteException);
                return false;
            }
            bl = iTelephony.isConcurrentVoiceAndDataAllowed(this.getSubId());
        }
        return bl;
    }

    public boolean isDataAllowedInVoiceCall() {
        block3 : {
            ITelephony iTelephony = this.getITelephony();
            if (iTelephony == null) break block3;
            try {
                boolean bl = iTelephony.isDataAllowedInVoiceCall(this.getSubId());
                return bl;
            }
            catch (RemoteException remoteException) {
                if (this.isSystemProcess()) break block3;
                remoteException.rethrowAsRuntimeException();
            }
        }
        return false;
    }

    public boolean isDataCapable() {
        boolean bl;
        block4 : {
            boolean bl2 = false;
            boolean bl3 = false;
            int n = this.getSubId(SubscriptionManager.getDefaultDataSubscriptionId());
            ITelephony iTelephony = this.getITelephony();
            bl = bl3;
            if (iTelephony == null) break block4;
            try {
                bl = iTelephony.isDataEnabled(n);
            }
            catch (NullPointerException nullPointerException) {
                bl = bl2;
            }
            catch (RemoteException remoteException) {
                Log.e(TAG, "Error calling ITelephony#isDataEnabled", remoteException);
                bl = bl3;
            }
        }
        return bl;
    }

    @SystemApi
    public boolean isDataConnectivityPossible() {
        block3 : {
            ITelephony iTelephony = this.getITelephony();
            if (iTelephony == null) break block3;
            try {
                boolean bl = iTelephony.isDataConnectivityPossible(this.getSubId(SubscriptionManager.getDefaultDataSubscriptionId()));
                return bl;
            }
            catch (RemoteException remoteException) {
                Log.e(TAG, "Error calling ITelephony#isDataAllowed", remoteException);
            }
        }
        return false;
    }

    public boolean isDataEnabled() {
        return this.getDataEnabled(this.getSubId(SubscriptionManager.getDefaultDataSubscriptionId()));
    }

    public boolean isDataEnabledForApn(int n) {
        block3 : {
            Object object = this.mContext;
            object = object != null ? ((Context)object).getOpPackageName() : "<unknown>";
            ITelephony iTelephony = this.getITelephony();
            if (iTelephony == null) break block3;
            try {
                boolean bl = iTelephony.isDataEnabledForApn(n, this.getSubId(), (String)object);
                return bl;
            }
            catch (RemoteException remoteException) {
                if (this.isSystemProcess()) break block3;
                remoteException.rethrowAsRuntimeException();
            }
        }
        return false;
    }

    public boolean isDataRoamingEnabled() {
        boolean bl;
        block3 : {
            boolean bl2 = false;
            bl = false;
            ITelephony iTelephony = this.getITelephony();
            if (iTelephony == null) break block3;
            try {
                bl = iTelephony.isDataRoamingEnabled(this.getSubId(SubscriptionManager.getDefaultDataSubscriptionId()));
            }
            catch (RemoteException remoteException) {
                Log.e(TAG, "Error calling ITelephony#isDataRoamingEnabled", remoteException);
                bl = bl2;
            }
        }
        return bl;
    }

    @SystemApi
    public boolean isEmergencyAssistanceEnabled() {
        this.mContext.enforceCallingOrSelfPermission("android.permission.READ_PRIVILEGED_PHONE_STATE", "isEmergencyAssistanceEnabled");
        return true;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public boolean isEmergencyNumber(String object) {
        try {
            ITelephony iTelephony = this.getITelephony();
            if (iTelephony != null) {
                return iTelephony.isEmergencyNumber((String)object, true);
            }
            object = new IllegalStateException("telephony service is null.");
            throw object;
        }
        catch (RemoteException remoteException) {
            Log.e(TAG, "isEmergencyNumber RemoteException", remoteException);
            remoteException.rethrowAsRuntimeException();
            return false;
        }
    }

    public boolean isHearingAidCompatibilitySupported() {
        block4 : {
            ITelephony iTelephony = this.getITelephony();
            if (iTelephony == null) break block4;
            try {
                boolean bl = iTelephony.isHearingAidCompatibilitySupported();
                return bl;
            }
            catch (SecurityException securityException) {
                Log.e(TAG, "Permission error calling ITelephony#isHearingAidCompatibilitySupported", securityException);
            }
            catch (RemoteException remoteException) {
                Log.e(TAG, "Error calling ITelephony#isHearingAidCompatibilitySupported", remoteException);
            }
        }
        return false;
    }

    @SystemApi
    @Deprecated
    public boolean isIdle() {
        return ((TelecomManager)this.mContext.getSystemService("telecom")).isInCall() ^ true;
    }

    @UnsupportedAppUsage(maxTargetSdk=28)
    public boolean isImsRegistered() {
        try {
            boolean bl = this.getITelephony().isImsRegistered(this.getSubId());
            return bl;
        }
        catch (RemoteException | NullPointerException exception) {
            return false;
        }
    }

    public boolean isImsRegistered(int n) {
        try {
            boolean bl = this.getITelephony().isImsRegistered(n);
            return bl;
        }
        catch (RemoteException | NullPointerException exception) {
            return false;
        }
    }

    public boolean isInEmergencySmsMode() {
        block3 : {
            ITelephony iTelephony = this.getITelephony();
            if (iTelephony == null) break block3;
            try {
                boolean bl = iTelephony.isInEmergencySmsMode();
                return bl;
            }
            catch (RemoteException remoteException) {
                Rlog.e(TAG, "getNetworkSelectionMode RemoteException", remoteException);
            }
        }
        return false;
    }

    public boolean isManualNetworkSelectionAllowed() {
        block3 : {
            ITelephony iTelephony = this.getITelephony();
            if (iTelephony == null) break block3;
            try {
                boolean bl = iTelephony.isManualNetworkSelectionAllowed(this.getSubId());
                return bl;
            }
            catch (RemoteException remoteException) {
                Log.e(TAG, "Error calling ITelephony#isManualNetworkSelectionAllowed", remoteException);
            }
        }
        return true;
    }

    public boolean isModemEnabledForSlot(int n) {
        block3 : {
            ITelephony iTelephony = this.getITelephony();
            if (iTelephony == null) break block3;
            try {
                boolean bl = iTelephony.isModemEnabledForSlot(n, this.mContext.getOpPackageName());
                return bl;
            }
            catch (RemoteException remoteException) {
                Log.e(TAG, "enableModem RemoteException", remoteException);
            }
        }
        return false;
    }

    @UnsupportedAppUsage
    public boolean isMultiSimEnabled() {
        boolean bl = multiSimConfig.equals("dsds") || multiSimConfig.equals("dsda") || multiSimConfig.equals("tsts");
        return bl;
    }

    public int isMultiSimSupported() {
        block3 : {
            ITelephony iTelephony = this.getITelephony();
            if (iTelephony == null) break block3;
            try {
                int n = iTelephony.isMultiSimSupported(this.getOpPackageName());
                return n;
            }
            catch (RemoteException remoteException) {
                Log.e(TAG, "isMultiSimSupported RemoteException", remoteException);
            }
        }
        return 1;
    }

    public boolean isNetworkRoaming() {
        return this.isNetworkRoaming(this.getSubId());
    }

    @UnsupportedAppUsage
    public boolean isNetworkRoaming(int n) {
        return Boolean.parseBoolean(TelephonyManager.getTelephonyProperty(SubscriptionManager.getPhoneId(n), "gsm.operator.isroaming", null));
    }

    @SystemApi
    @Deprecated
    public boolean isOffhook() {
        return ((TelecomManager)this.mContext.getSystemService("telecom")).isInCall();
    }

    public boolean isOpportunisticNetworkEnabled() {
        boolean bl;
        block3 : {
            Object object = this.mContext;
            object = object != null ? ((Context)object).getOpPackageName() : "<unknown>";
            boolean bl2 = false;
            bl = false;
            IOns iOns = this.getIOns();
            if (iOns == null) break block3;
            try {
                bl = iOns.isEnabled((String)object);
            }
            catch (RemoteException remoteException) {
                Rlog.e(TAG, "enableOpportunisticNetwork RemoteException", remoteException);
                bl = bl2;
            }
        }
        return bl;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @SystemApi
    public boolean isPotentialEmergencyNumber(String object) {
        try {
            ITelephony iTelephony = this.getITelephony();
            if (iTelephony != null) {
                return iTelephony.isEmergencyNumber((String)object, false);
            }
            object = new IllegalStateException("telephony service is null.");
            throw object;
        }
        catch (RemoteException remoteException) {
            Log.e(TAG, "isEmergencyNumber RemoteException", remoteException);
            remoteException.rethrowAsRuntimeException();
            return false;
        }
    }

    @SystemApi
    @Deprecated
    public boolean isRadioOn() {
        block3 : {
            ITelephony iTelephony = this.getITelephony();
            if (iTelephony == null) break block3;
            try {
                boolean bl = iTelephony.isRadioOn(this.getOpPackageName());
                return bl;
            }
            catch (RemoteException remoteException) {
                Log.e(TAG, "Error calling ITelephony#isRadioOn", remoteException);
            }
        }
        return false;
    }

    @SystemApi
    @Deprecated
    public boolean isRinging() {
        return ((TelecomManager)this.mContext.getSystemService("telecom")).isRinging();
    }

    public boolean isRttSupported() {
        block4 : {
            ITelephony iTelephony = this.getITelephony();
            if (iTelephony == null) break block4;
            try {
                boolean bl = iTelephony.isRttSupported(this.mSubId);
                return bl;
            }
            catch (SecurityException securityException) {
                Log.e(TAG, "Permission error calling ITelephony#isWorldPhone", securityException);
            }
            catch (RemoteException remoteException) {
                Log.e(TAG, "Error calling ITelephony#isRttSupported", remoteException);
            }
        }
        return false;
    }

    public boolean isSmsCapable() {
        Context context = this.mContext;
        if (context == null) {
            return true;
        }
        return context.getResources().getBoolean(17891521);
    }

    @Deprecated
    public boolean isTtyModeSupported() {
        block3 : {
            TelecomManager telecomManager = TelecomManager.from(this.mContext);
            if (telecomManager == null) break block3;
            try {
                boolean bl = telecomManager.isTtySupported();
                return bl;
            }
            catch (SecurityException securityException) {
                Log.e(TAG, "Permission error calling TelecomManager#isTtySupported", securityException);
            }
        }
        return false;
    }

    @SystemApi
    public boolean isVideoCallingEnabled() {
        block3 : {
            ITelephony iTelephony = this.getITelephony();
            if (iTelephony == null) break block3;
            try {
                boolean bl = iTelephony.isVideoCallingEnabled(this.getOpPackageName());
                return bl;
            }
            catch (RemoteException remoteException) {
                Log.e(TAG, "Error calling ITelephony#isVideoCallingEnabled", remoteException);
            }
        }
        return false;
    }

    @UnsupportedAppUsage
    public boolean isVideoTelephonyAvailable() {
        try {
            boolean bl = this.getITelephony().isVideoTelephonyAvailable(this.getSubId());
            return bl;
        }
        catch (RemoteException | NullPointerException exception) {
            return false;
        }
    }

    @SystemApi
    @SuppressLint(value={"Doclava125"})
    public boolean isVisualVoicemailEnabled(PhoneAccountHandle phoneAccountHandle) {
        return false;
    }

    public boolean isVoiceCapable() {
        Context context = this.mContext;
        if (context == null) {
            return true;
        }
        return context.getResources().getBoolean(17891570);
    }

    public boolean isVoicemailVibrationEnabled(PhoneAccountHandle phoneAccountHandle) {
        block3 : {
            ITelephony iTelephony = this.getITelephony();
            if (iTelephony == null) break block3;
            try {
                boolean bl = iTelephony.isVoicemailVibrationEnabled(phoneAccountHandle);
                return bl;
            }
            catch (RemoteException remoteException) {
                Log.e(TAG, "Error calling ITelephony#isVoicemailVibrationEnabled", remoteException);
            }
        }
        return false;
    }

    @UnsupportedAppUsage
    public boolean isVolteAvailable() {
        try {
            boolean bl = this.getITelephony().isAvailable(this.getSubId(), 1, 0);
            return bl;
        }
        catch (RemoteException | NullPointerException exception) {
            return false;
        }
    }

    @UnsupportedAppUsage
    public boolean isWifiCallingAvailable() {
        try {
            boolean bl = this.getITelephony().isWifiCallingAvailable(this.getSubId());
            return bl;
        }
        catch (RemoteException | NullPointerException exception) {
            return false;
        }
    }

    public boolean isWorldPhone() {
        block4 : {
            ITelephony iTelephony = this.getITelephony();
            if (iTelephony == null) break block4;
            try {
                boolean bl = iTelephony.isWorldPhone(this.mSubId, this.getOpPackageName());
                return bl;
            }
            catch (SecurityException securityException) {
                Log.e(TAG, "Permission error calling ITelephony#isWorldPhone", securityException);
            }
            catch (RemoteException remoteException) {
                Log.e(TAG, "Error calling ITelephony#isWorldPhone", remoteException);
            }
        }
        return false;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void listen(PhoneStateListener phoneStateListener, int n) {
        if (this.mContext == null) {
            return;
        }
        try {
            boolean bl = this.getITelephony() != null;
            ITelephonyRegistry iTelephonyRegistry = this.getTelephonyRegistry();
            if (iTelephonyRegistry == null) {
                Rlog.w(TAG, "telephony registry not ready.");
                return;
            }
            int n2 = this.mSubId;
            if (VMRuntime.getRuntime().getTargetSdkVersion() >= 29) {
                int n3 = n == 0 ? -1 : n2;
                phoneStateListener.mSubId = n3;
            } else if (phoneStateListener.mSubId != null) {
                n2 = phoneStateListener.mSubId;
            }
            iTelephonyRegistry.listenForSubscriber(n2, this.getOpPackageName(), phoneStateListener.callback, n, bl);
            return;
        }
        catch (RemoteException remoteException) {
            // empty catch block
        }
    }

    @SystemApi
    public boolean needsOtaServiceProvisioning() {
        block3 : {
            ITelephony iTelephony = this.getITelephony();
            if (iTelephony == null) break block3;
            try {
                boolean bl = iTelephony.needsOtaServiceProvisioning();
                return bl;
            }
            catch (RemoteException remoteException) {
                Log.e(TAG, "Error calling ITelephony#needsOtaServiceProvisioning", remoteException);
            }
        }
        return false;
    }

    @UnsupportedAppUsage
    public String nvReadItem(int n) {
        block4 : {
            Object object = this.getITelephony();
            if (object == null) break block4;
            try {
                object = object.nvReadItem(n);
                return object;
            }
            catch (NullPointerException nullPointerException) {
                Rlog.e(TAG, "nvReadItem NPE", nullPointerException);
            }
            catch (RemoteException remoteException) {
                Rlog.e(TAG, "nvReadItem RemoteException", remoteException);
            }
        }
        return "";
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @UnsupportedAppUsage
    public boolean nvResetConfig(int n) {
        try {
            ITelephony iTelephony = this.getITelephony();
            if (iTelephony == null) return false;
            if (n == 1) {
                return iTelephony.rebootModem(this.getSlotIndex());
            }
            if (n == 3) {
                return iTelephony.resetModemConfig(this.getSlotIndex());
            }
            Rlog.e(TAG, "nvResetConfig unsupported reset type");
            return false;
        }
        catch (NullPointerException nullPointerException) {
            Rlog.e(TAG, "nvResetConfig NPE", nullPointerException);
            return false;
        }
        catch (RemoteException remoteException) {
            Rlog.e(TAG, "nvResetConfig RemoteException", remoteException);
        }
        return false;
    }

    public boolean nvWriteCdmaPrl(byte[] arrby) {
        block4 : {
            ITelephony iTelephony = this.getITelephony();
            if (iTelephony == null) break block4;
            try {
                boolean bl = iTelephony.nvWriteCdmaPrl(arrby);
                return bl;
            }
            catch (NullPointerException nullPointerException) {
                Rlog.e(TAG, "nvWriteCdmaPrl NPE", nullPointerException);
            }
            catch (RemoteException remoteException) {
                Rlog.e(TAG, "nvWriteCdmaPrl RemoteException", remoteException);
            }
        }
        return false;
    }

    public boolean nvWriteItem(int n, String string2) {
        block4 : {
            ITelephony iTelephony = this.getITelephony();
            if (iTelephony == null) break block4;
            try {
                boolean bl = iTelephony.nvWriteItem(n, string2);
                return bl;
            }
            catch (NullPointerException nullPointerException) {
                Rlog.e(TAG, "nvWriteItem NPE", nullPointerException);
            }
            catch (RemoteException remoteException) {
                Rlog.e(TAG, "nvWriteItem RemoteException", remoteException);
            }
        }
        return false;
    }

    @SystemApi
    public boolean rebootRadio() {
        block4 : {
            ITelephony iTelephony = this.getITelephony();
            if (iTelephony == null) break block4;
            try {
                boolean bl = iTelephony.rebootModem(this.getSlotIndex());
                return bl;
            }
            catch (NullPointerException nullPointerException) {
                Rlog.e(TAG, "rebootRadio NPE", nullPointerException);
            }
            catch (RemoteException remoteException) {
                Rlog.e(TAG, "rebootRadio RemoteException", remoteException);
            }
        }
        return false;
    }

    public void refreshUiccProfile() {
        try {
            this.getITelephony().refreshUiccProfile(this.mSubId);
        }
        catch (RemoteException remoteException) {
            Rlog.w(TAG, "RemoteException", remoteException);
        }
    }

    @SystemApi
    public void requestCellInfoUpdate(WorkSource workSource, final Executor executor, final CellInfoCallback cellInfoCallback) {
        ITelephony iTelephony;
        block3 : {
            iTelephony = this.getITelephony();
            if (iTelephony != null) break block3;
            return;
        }
        try {
            int n = this.getSubId();
            ICellInfoCallback.Stub stub = new ICellInfoCallback.Stub(){

                static /* synthetic */ void lambda$onCellInfo$0(CellInfoCallback cellInfoCallback2, List list) {
                    cellInfoCallback2.onCellInfo(list);
                }

                static /* synthetic */ void lambda$onCellInfo$1(Executor executor2, CellInfoCallback cellInfoCallback2, List list) throws Exception {
                    executor2.execute(new _$$Lambda$TelephonyManager$2$l6Pazxfi7QghMr2Z0MpduhNe6yc(cellInfoCallback2, list));
                }

                static /* synthetic */ void lambda$onError$2(CellInfoCallback cellInfoCallback2, int n, ParcelableException parcelableException) {
                    cellInfoCallback2.onError(n, parcelableException.getCause());
                }

                static /* synthetic */ void lambda$onError$3(Executor executor2, CellInfoCallback cellInfoCallback2, int n, ParcelableException parcelableException) throws Exception {
                    executor2.execute(new _$$Lambda$TelephonyManager$2$Ulw55AvQUDkoL1gDNnPVlIOb8mw(cellInfoCallback2, n, parcelableException));
                }

                @Override
                public void onCellInfo(List<CellInfo> list) {
                    Binder.withCleanCallingIdentity(new _$$Lambda$TelephonyManager$2$hWPf2raNadUBIhTQLEUpRhHWKoI(executor, cellInfoCallback, list));
                }

                @Override
                public void onError(int n, ParcelableException parcelableException) {
                    Binder.withCleanCallingIdentity(new _$$Lambda$TelephonyManager$2$6owqHJtmTOa9dDQAz_9oKh9XFVk(executor, cellInfoCallback, n, parcelableException));
                }
            };
            iTelephony.requestCellInfoUpdateWithWorkSource(n, stub, this.getOpPackageName(), workSource);
        }
        catch (RemoteException remoteException) {
            // empty catch block
        }
    }

    public void requestCellInfoUpdate(final Executor executor, final CellInfoCallback cellInfoCallback) {
        ITelephony iTelephony;
        block3 : {
            iTelephony = this.getITelephony();
            if (iTelephony != null) break block3;
            return;
        }
        try {
            int n = this.getSubId();
            ICellInfoCallback.Stub stub = new ICellInfoCallback.Stub(){

                static /* synthetic */ void lambda$onCellInfo$0(CellInfoCallback cellInfoCallback2, List list) {
                    cellInfoCallback2.onCellInfo(list);
                }

                static /* synthetic */ void lambda$onCellInfo$1(Executor executor2, CellInfoCallback cellInfoCallback2, List list) throws Exception {
                    executor2.execute(new _$$Lambda$TelephonyManager$1$scMPky6lOZrCjFC3d4STbtLfpHE(cellInfoCallback2, list));
                }

                static /* synthetic */ void lambda$onError$2(CellInfoCallback cellInfoCallback2, int n, ParcelableException parcelableException) {
                    cellInfoCallback2.onError(n, parcelableException.getCause());
                }

                static /* synthetic */ void lambda$onError$3(Executor executor2, CellInfoCallback cellInfoCallback2, int n, ParcelableException parcelableException) throws Exception {
                    executor2.execute(new _$$Lambda$TelephonyManager$1$DUDjwoHWG36BPTvbfvZqnIO3Y88(cellInfoCallback2, n, parcelableException));
                }

                @Override
                public void onCellInfo(List<CellInfo> list) {
                    Binder.withCleanCallingIdentity(new _$$Lambda$TelephonyManager$1$888GQVMXufCYSJI5ivTjjUxEprI(executor, cellInfoCallback, list));
                }

                @Override
                public void onError(int n, ParcelableException parcelableException) {
                    Binder.withCleanCallingIdentity(new _$$Lambda$TelephonyManager$1$5jj__2hbfx_RMVO7qjBdMYFfP1s(executor, cellInfoCallback, n, parcelableException));
                }
            };
            iTelephony.requestCellInfoUpdate(n, stub, this.getOpPackageName());
        }
        catch (RemoteException remoteException) {
            // empty catch block
        }
    }

    public void requestModemActivityInfo(ResultReceiver resultReceiver) {
        block3 : {
            ITelephony iTelephony = this.getITelephony();
            if (iTelephony == null) break block3;
            try {
                iTelephony.requestModemActivityInfo(resultReceiver);
                return;
            }
            catch (RemoteException remoteException) {
                Log.e(TAG, "Error calling ITelephony#getModemActivityInfo", remoteException);
            }
        }
        resultReceiver.send(0, null);
    }

    @Deprecated
    public NetworkScan requestNetworkScan(NetworkScanRequest networkScanRequest, TelephonyScanManager.NetworkScanCallback networkScanCallback) {
        return this.requestNetworkScan(networkScanRequest, AsyncTask.SERIAL_EXECUTOR, networkScanCallback);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public NetworkScan requestNetworkScan(NetworkScanRequest networkScanRequest, Executor executor, TelephonyScanManager.NetworkScanCallback networkScanCallback) {
        synchronized (this) {
            if (this.mTelephonyScanManager == null) {
                TelephonyScanManager telephonyScanManager;
                this.mTelephonyScanManager = telephonyScanManager = new TelephonyScanManager();
            }
            return this.mTelephonyScanManager.requestNetworkScan(this.getSubId(), networkScanRequest, executor, networkScanCallback, this.getOpPackageName());
        }
    }

    @SystemApi
    public void requestNumberVerification(PhoneNumberRange phoneNumberRange, long l, final Executor executor, final NumberVerificationCallback numberVerificationCallback) {
        if (executor != null) {
            if (numberVerificationCallback != null) {
                block5 : {
                    INumberVerificationCallback.Stub stub = new INumberVerificationCallback.Stub(){

                        static /* synthetic */ void lambda$onCallReceived$0(NumberVerificationCallback numberVerificationCallback2, String string2) {
                            numberVerificationCallback2.onCallReceived(string2);
                        }

                        static /* synthetic */ void lambda$onCallReceived$1(Executor executor2, NumberVerificationCallback numberVerificationCallback2, String string2) throws Exception {
                            executor2.execute(new _$$Lambda$TelephonyManager$3$LPMNUsxM8QRYWmnzGtrEYPm5sAs(numberVerificationCallback2, string2));
                        }

                        static /* synthetic */ void lambda$onVerificationFailed$2(NumberVerificationCallback numberVerificationCallback2, int n) {
                            numberVerificationCallback2.onVerificationFailed(n);
                        }

                        static /* synthetic */ void lambda$onVerificationFailed$3(Executor executor2, NumberVerificationCallback numberVerificationCallback2, int n) throws Exception {
                            executor2.execute(new _$$Lambda$TelephonyManager$3$VM3y0XwyxZN6vR6ERQTngCQIICc(numberVerificationCallback2, n));
                        }

                        @Override
                        public void onCallReceived(String string2) {
                            Binder.withCleanCallingIdentity(new _$$Lambda$TelephonyManager$3$ue1tJSNmFJObWAJcaHRYIrfBRNg(executor, numberVerificationCallback, string2));
                        }

                        @Override
                        public void onVerificationFailed(int n) {
                            Binder.withCleanCallingIdentity(new _$$Lambda$TelephonyManager$3$TrNEDm6VsUgT1BQFiXGiPDtbxuA(executor, numberVerificationCallback, n));
                        }
                    };
                    ITelephony iTelephony = this.getITelephony();
                    if (iTelephony == null) break block5;
                    try {
                        iTelephony.requestNumberVerification(phoneNumberRange, l, stub, this.getOpPackageName());
                    }
                    catch (RemoteException remoteException) {
                        Rlog.e(TAG, "requestNumberVerification RemoteException", remoteException);
                        executor.execute(new _$$Lambda$TelephonyManager$4i1RRVjnCzfQvX2hIGG9K8g4DaY(numberVerificationCallback));
                    }
                }
                return;
            }
            throw new NullPointerException("Callback must be non-null");
        }
        throw new NullPointerException("Executor must be non-null");
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void resetCarrierKeysForImsiEncryption() {
        try {
            Object object = this.getSubscriberInfo();
            if (object != null) {
                object.resetCarrierKeysForImsiEncryption(this.getSubId(SubscriptionManager.getDefaultDataSubscriptionId()), this.mContext.getOpPackageName());
                return;
            }
            Rlog.e(TAG, "IMSI error: Subscriber Info is null");
            if (this.isSystemProcess()) {
                return;
            }
            object = new RuntimeException("IMSI error: Subscriber Info is null");
            throw object;
        }
        catch (RemoteException remoteException) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("getCarrierInfoForImsiEncryption RemoteException");
            stringBuilder.append(remoteException);
            Rlog.e(TAG, stringBuilder.toString());
            if (this.isSystemProcess()) return;
            remoteException.rethrowAsRuntimeException();
        }
    }

    @SystemApi
    public boolean resetRadioConfig() {
        block4 : {
            ITelephony iTelephony = this.getITelephony();
            if (iTelephony == null) break block4;
            try {
                boolean bl = iTelephony.resetModemConfig(this.getSlotIndex());
                return bl;
            }
            catch (NullPointerException nullPointerException) {
                Rlog.e(TAG, "resetRadioConfig NPE", nullPointerException);
            }
            catch (RemoteException remoteException) {
                Rlog.e(TAG, "resetRadioConfig RemoteException", remoteException);
            }
        }
        return false;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void sendDialerSpecialCode(String object) {
        try {
            ITelephony iTelephony = this.getITelephony();
            if (iTelephony != null) {
                iTelephony.sendDialerSpecialCode(this.mContext.getOpPackageName(), (String)object);
                return;
            }
            if (this.isSystemProcess()) {
                return;
            }
            object = new RuntimeException("Telephony service unavailable");
            throw object;
        }
        catch (RemoteException remoteException) {
            if (this.isSystemProcess()) return;
            remoteException.rethrowAsRuntimeException();
        }
    }

    public String sendEnvelopeWithStatus(int n, String string2) {
        block4 : {
            ITelephony iTelephony = this.getITelephony();
            if (iTelephony == null) break block4;
            try {
                string2 = iTelephony.sendEnvelopeWithStatus(n, string2);
                return string2;
            }
            catch (NullPointerException nullPointerException) {
            }
            catch (RemoteException remoteException) {
                // empty catch block
            }
        }
        return "";
    }

    public String sendEnvelopeWithStatus(String string2) {
        return this.sendEnvelopeWithStatus(this.getSubId(), string2);
    }

    public void sendUssdRequest(String object, UssdResponseCallback object2, Handler object3) {
        block3 : {
            Preconditions.checkNotNull(object2, "UssdResponseCallback cannot be null.");
            object2 = new ResultReceiver((Handler)object3, (UssdResponseCallback)object2, this){
                final /* synthetic */ UssdResponseCallback val$callback;
                final /* synthetic */ TelephonyManager val$telephonyManager;
                {
                    this.val$callback = ussdResponseCallback;
                    this.val$telephonyManager = telephonyManager2;
                    super(handler);
                }

                @Override
                protected void onReceiveResult(int n, Bundle parcelable) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("USSD:");
                    stringBuilder.append(n);
                    Rlog.d(TelephonyManager.TAG, stringBuilder.toString());
                    Preconditions.checkNotNull(parcelable, "ussdResponse cannot be null.");
                    parcelable = (UssdResponse)((Bundle)parcelable).getParcelable(TelephonyManager.USSD_RESPONSE);
                    if (n == 100) {
                        this.val$callback.onReceiveUssdResponse(this.val$telephonyManager, ((UssdResponse)parcelable).getUssdRequest(), ((UssdResponse)parcelable).getReturnMessage());
                    } else {
                        this.val$callback.onReceiveUssdResponseFailed(this.val$telephonyManager, ((UssdResponse)parcelable).getUssdRequest(), n);
                    }
                }
            };
            object3 = this.getITelephony();
            if (object3 == null) break block3;
            try {
                object3.handleUssdRequest(this.getSubId(), (String)object, (ResultReceiver)object2);
            }
            catch (RemoteException remoteException) {
                Log.e(TAG, "Error calling ITelephony#sendUSSDCode", remoteException);
                UssdResponse ussdResponse = new UssdResponse((String)object, "");
                object = new Bundle();
                ((Bundle)object).putParcelable(USSD_RESPONSE, ussdResponse);
                ((ResultReceiver)object2).send(-2, (Bundle)object);
            }
        }
    }

    public void sendVisualVoicemailSms(String string2, int n, String string3, PendingIntent pendingIntent) {
        this.sendVisualVoicemailSmsForSubscriber(this.mSubId, string2, n, string3, pendingIntent);
    }

    public void sendVisualVoicemailSmsForSubscriber(int n, String string2, int n2, String string3, PendingIntent pendingIntent) {
        block3 : {
            ITelephony iTelephony = this.getITelephony();
            if (iTelephony == null) break block3;
            try {
                iTelephony.sendVisualVoicemailSmsForSubscriber(this.mContext.getOpPackageName(), n, string2, n2, string3, pendingIntent);
            }
            catch (RemoteException remoteException) {
                // empty catch block
            }
        }
    }

    @SystemApi
    public int setAllowedCarriers(int n, List<CarrierIdentifier> list) {
        if (list != null && SubscriptionManager.isValidPhoneId(n)) {
            if (this.setCarrierRestrictionRules(CarrierRestrictionRules.newBuilder().setAllowedCarriers(list).setDefaultCarrierRestriction(0).build()) == 0) {
                return list.size();
            }
            return -1;
        }
        return -1;
    }

    public void setBasebandVersion(String string2) {
        this.setBasebandVersionForPhone(this.getPhoneId(), string2);
    }

    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    public void setBasebandVersionForPhone(int n, String string2) {
        TelephonyManager.setTelephonyProperty(n, "gsm.version.baseband", string2);
    }

    @SystemApi
    public void setCarrierDataEnabled(boolean bl) {
        block3 : {
            ITelephony iTelephony = this.getITelephony();
            if (iTelephony == null) break block3;
            try {
                iTelephony.carrierActionSetMeteredApnsEnabled(this.getSubId(SubscriptionManager.getDefaultDataSubscriptionId()), bl);
            }
            catch (RemoteException remoteException) {
                Log.e(TAG, "Error calling ITelephony#setCarrierDataEnabled", remoteException);
            }
        }
    }

    public void setCarrierInfoForImsiEncryption(ImsiEncryptionInfo imsiEncryptionInfo) {
        IPhoneSubInfo iPhoneSubInfo;
        block4 : {
            iPhoneSubInfo = this.getSubscriberInfo();
            if (iPhoneSubInfo != null) break block4;
            return;
        }
        try {
            iPhoneSubInfo.setCarrierInfoForImsiEncryption(this.mSubId, this.mContext.getOpPackageName(), imsiEncryptionInfo);
            return;
        }
        catch (RemoteException remoteException) {
            Rlog.e(TAG, "setCarrierInfoForImsiEncryption RemoteException", remoteException);
            return;
        }
        catch (NullPointerException nullPointerException) {
            return;
        }
    }

    @SystemApi
    public int setCarrierRestrictionRules(CarrierRestrictionRules carrierRestrictionRules) {
        block4 : {
            ITelephony iTelephony = this.getITelephony();
            if (iTelephony == null) break block4;
            try {
                int n = iTelephony.setAllowedCarriers(carrierRestrictionRules);
                return n;
            }
            catch (NullPointerException nullPointerException) {
                Log.e(TAG, "Error calling ITelephony#setAllowedCarriers", nullPointerException);
            }
            catch (RemoteException remoteException) {
                Log.e(TAG, "Error calling ITelephony#setAllowedCarriers", remoteException);
            }
        }
        return 2;
    }

    @Deprecated
    public void setCarrierTestOverride(String string2, String string3, String string4, String string5, String string6, String string7, String string8) {
        block3 : {
            ITelephony iTelephony = this.getITelephony();
            if (iTelephony == null) break block3;
            try {
                iTelephony.setCarrierTestOverride(this.getSubId(), string2, string3, string4, string5, string6, string7, string8, null, null);
            }
            catch (RemoteException remoteException) {
                // empty catch block
            }
        }
    }

    public void setCarrierTestOverride(String string2, String string3, String string4, String string5, String string6, String string7, String string8, String string9, String string10) {
        block3 : {
            ITelephony iTelephony = this.getITelephony();
            if (iTelephony == null) break block3;
            try {
                iTelephony.setCarrierTestOverride(this.getSubId(), string2, string3, string4, string5, string6, string7, string8, string9, string10);
            }
            catch (RemoteException remoteException) {
                // empty catch block
            }
        }
    }

    public boolean setCdmaRoamingMode(int n) {
        block3 : {
            ITelephony iTelephony = this.getITelephony();
            if (iTelephony == null) break block3;
            try {
                boolean bl = iTelephony.setCdmaRoamingMode(this.getSubId(), n);
                return bl;
            }
            catch (RemoteException remoteException) {
                Log.e(TAG, "Error calling ITelephony#setCdmaRoamingMode", remoteException);
            }
        }
        return false;
    }

    public boolean setCdmaSubscriptionMode(int n) {
        block3 : {
            ITelephony iTelephony = this.getITelephony();
            if (iTelephony == null) break block3;
            try {
                boolean bl = iTelephony.setCdmaSubscriptionMode(this.getSubId(), n);
                return bl;
            }
            catch (RemoteException remoteException) {
                Log.e(TAG, "Error calling ITelephony#setCdmaSubscriptionMode", remoteException);
            }
        }
        return false;
    }

    public void setCellInfoListRate(int n) {
        block4 : {
            ITelephony iTelephony = this.getITelephony();
            if (iTelephony == null) break block4;
            try {
                iTelephony.setCellInfoListRate(n);
            }
            catch (NullPointerException nullPointerException) {
            }
            catch (RemoteException remoteException) {
                // empty catch block
            }
        }
    }

    @SystemApi
    public void setDataActivationState(int n) {
        this.setDataActivationState(this.getSubId(), n);
    }

    public void setDataActivationState(int n, int n2) {
        block4 : {
            ITelephony iTelephony = this.getITelephony();
            if (iTelephony == null) break block4;
            try {
                iTelephony.setDataActivationState(n, n2);
            }
            catch (NullPointerException nullPointerException) {
            }
            catch (RemoteException remoteException) {
                // empty catch block
            }
        }
    }

    public boolean setDataAllowedDuringVoiceCall(boolean bl) {
        block3 : {
            ITelephony iTelephony = this.getITelephony();
            if (iTelephony == null) break block3;
            try {
                bl = iTelephony.setDataAllowedDuringVoiceCall(this.getSubId(), bl);
                return bl;
            }
            catch (RemoteException remoteException) {
                if (this.isSystemProcess()) break block3;
                remoteException.rethrowAsRuntimeException();
            }
        }
        return false;
    }

    @SystemApi
    @Deprecated
    public void setDataEnabled(int n, boolean bl) {
        block3 : {
            Object object = new StringBuilder();
            ((StringBuilder)object).append("setDataEnabled: enabled=");
            ((StringBuilder)object).append(bl);
            Log.d(TAG, ((StringBuilder)object).toString());
            object = this.getITelephony();
            if (object == null) break block3;
            try {
                object.setUserDataEnabled(n, bl);
            }
            catch (RemoteException remoteException) {
                Log.e(TAG, "Error calling ITelephony#setUserDataEnabled", remoteException);
            }
        }
    }

    public void setDataEnabled(boolean bl) {
        this.setDataEnabled(this.getSubId(SubscriptionManager.getDefaultDataSubscriptionId()), bl);
    }

    public void setDataNetworkType(int n) {
        this.setDataNetworkTypeForPhone(this.getPhoneId(SubscriptionManager.getDefaultDataSubscriptionId()), n);
    }

    @UnsupportedAppUsage
    public void setDataNetworkTypeForPhone(int n, int n2) {
        if (SubscriptionManager.isValidPhoneId(n)) {
            TelephonyManager.setTelephonyProperty(n, "gsm.network.type", ServiceState.rilRadioTechnologyToString(n2));
        }
    }

    @SystemApi
    public void setDataRoamingEnabled(boolean bl) {
        block3 : {
            ITelephony iTelephony = this.getITelephony();
            if (iTelephony == null) break block3;
            try {
                iTelephony.setDataRoamingEnabled(this.getSubId(SubscriptionManager.getDefaultDataSubscriptionId()), bl);
            }
            catch (RemoteException remoteException) {
                Log.e(TAG, "Error calling ITelephony#setDataRoamingEnabled", remoteException);
            }
        }
    }

    @UnsupportedAppUsage
    public void setImsRegistrationState(boolean bl) {
        block3 : {
            ITelephony iTelephony = this.getITelephony();
            if (iTelephony == null) break block3;
            try {
                iTelephony.setImsRegistrationState(bl);
            }
            catch (RemoteException remoteException) {
                // empty catch block
            }
        }
    }

    public boolean setLine1NumberForDisplay(int n, String string2, String string3) {
        block4 : {
            ITelephony iTelephony = this.getITelephony();
            if (iTelephony == null) break block4;
            try {
                boolean bl = iTelephony.setLine1NumberForDisplayForSubscriber(n, string2, string3);
                return bl;
            }
            catch (NullPointerException nullPointerException) {
            }
            catch (RemoteException remoteException) {
                // empty catch block
            }
        }
        return false;
    }

    public boolean setLine1NumberForDisplay(String string2, String string3) {
        return this.setLine1NumberForDisplay(this.getSubId(), string2, string3);
    }

    @SystemApi
    public void setMultiSimCarrierRestriction(boolean bl) {
        block3 : {
            ITelephony iTelephony = this.getITelephony();
            if (iTelephony == null) break block3;
            try {
                iTelephony.setMultiSimCarrierRestriction(bl);
            }
            catch (RemoteException remoteException) {
                Log.e(TAG, "setMultiSimCarrierRestriction RemoteException", remoteException);
            }
        }
    }

    public void setNetworkOperatorName(String string2) {
        this.setNetworkOperatorNameForPhone(this.getPhoneId(), string2);
    }

    @UnsupportedAppUsage
    public void setNetworkOperatorNameForPhone(int n, String string2) {
        if (SubscriptionManager.isValidPhoneId(n)) {
            TelephonyManager.setTelephonyProperty(n, "gsm.operator.alpha", string2);
        }
    }

    public void setNetworkOperatorNumeric(String string2) {
        this.setNetworkOperatorNumericForPhone(this.getPhoneId(), string2);
    }

    @UnsupportedAppUsage
    public void setNetworkOperatorNumericForPhone(int n, String string2) {
        TelephonyManager.setTelephonyProperty(n, "gsm.operator.numeric", string2);
    }

    public void setNetworkRoaming(boolean bl) {
        this.setNetworkRoamingForPhone(this.getPhoneId(), bl);
    }

    @UnsupportedAppUsage
    public void setNetworkRoamingForPhone(int n, boolean bl) {
        if (SubscriptionManager.isValidPhoneId(n)) {
            String string2 = bl ? "true" : "false";
            TelephonyManager.setTelephonyProperty(n, "gsm.operator.isroaming", string2);
        }
    }

    public void setNetworkSelectionModeAutomatic() {
        block4 : {
            ITelephony iTelephony = this.getITelephony();
            if (iTelephony == null) break block4;
            try {
                iTelephony.setNetworkSelectionModeAutomatic(this.getSubId());
            }
            catch (NullPointerException nullPointerException) {
                Rlog.e(TAG, "setNetworkSelectionModeAutomatic NPE", nullPointerException);
            }
            catch (RemoteException remoteException) {
                Rlog.e(TAG, "setNetworkSelectionModeAutomatic RemoteException", remoteException);
            }
        }
    }

    public boolean setNetworkSelectionModeManual(OperatorInfo operatorInfo, boolean bl) {
        block3 : {
            ITelephony iTelephony = this.getITelephony();
            if (iTelephony == null) break block3;
            try {
                bl = iTelephony.setNetworkSelectionModeManual(this.getSubId(), operatorInfo, bl);
                return bl;
            }
            catch (RemoteException remoteException) {
                Rlog.e(TAG, "setNetworkSelectionModeManual RemoteException", remoteException);
            }
        }
        return false;
    }

    public boolean setNetworkSelectionModeManual(String string2, boolean bl) {
        return this.setNetworkSelectionModeManual(new OperatorInfo("", "", string2), bl);
    }

    public boolean setOperatorBrandOverride(int n, String string2) {
        block4 : {
            ITelephony iTelephony = this.getITelephony();
            if (iTelephony == null) break block4;
            try {
                boolean bl = iTelephony.setOperatorBrandOverride(n, string2);
                return bl;
            }
            catch (NullPointerException nullPointerException) {
                Rlog.e(TAG, "setOperatorBrandOverride NPE", nullPointerException);
            }
            catch (RemoteException remoteException) {
                Rlog.e(TAG, "setOperatorBrandOverride RemoteException", remoteException);
            }
        }
        return false;
    }

    public boolean setOperatorBrandOverride(String string2) {
        return this.setOperatorBrandOverride(this.getSubId(), string2);
    }

    public boolean setOpportunisticNetworkState(boolean bl) {
        boolean bl2;
        block3 : {
            Object object = this.mContext;
            object = object != null ? ((Context)object).getOpPackageName() : "<unknown>";
            boolean bl3 = false;
            bl2 = false;
            IOns iOns = this.getIOns();
            if (iOns == null) break block3;
            try {
                bl2 = iOns.setEnable(bl, (String)object);
            }
            catch (RemoteException remoteException) {
                Rlog.e(TAG, "enableOpportunisticNetwork RemoteException", remoteException);
                bl2 = bl3;
            }
        }
        return bl2;
    }

    public void setPhoneType(int n) {
        this.setPhoneType(this.getPhoneId(), n);
    }

    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    public void setPhoneType(int n, int n2) {
        if (SubscriptionManager.isValidPhoneId(n)) {
            TelephonyManager.setTelephonyProperty(n, "gsm.current.phone-type", String.valueOf(n2));
        }
    }

    public void setPolicyDataEnabled(boolean bl, int n) {
        block3 : {
            ITelephony iTelephony = this.getITelephony();
            if (iTelephony == null) break block3;
            try {
                iTelephony.setPolicyDataEnabled(bl, n);
            }
            catch (RemoteException remoteException) {
                Log.e(TAG, "Error calling ITelephony#setPolicyDataEnabled", remoteException);
            }
        }
    }

    @UnsupportedAppUsage
    public boolean setPreferredNetworkType(int n, int n2) {
        block3 : {
            ITelephony iTelephony = this.getITelephony();
            if (iTelephony == null) break block3;
            try {
                boolean bl = iTelephony.setPreferredNetworkType(n, n2);
                return bl;
            }
            catch (RemoteException remoteException) {
                Rlog.e(TAG, "setPreferredNetworkType RemoteException", remoteException);
            }
        }
        return false;
    }

    @SystemApi
    public boolean setPreferredNetworkTypeBitmask(long l) {
        block3 : {
            ITelephony iTelephony = this.getITelephony();
            if (iTelephony == null) break block3;
            try {
                boolean bl = iTelephony.setPreferredNetworkType(this.getSubId(), RadioAccessFamily.getNetworkTypeFromRaf((int)l));
                return bl;
            }
            catch (RemoteException remoteException) {
                Rlog.e(TAG, "setPreferredNetworkTypeBitmask RemoteException", remoteException);
            }
        }
        return false;
    }

    public boolean setPreferredNetworkTypeToGlobal() {
        return this.setPreferredNetworkTypeToGlobal(this.getSubId());
    }

    public boolean setPreferredNetworkTypeToGlobal(int n) {
        return this.setPreferredNetworkType(n, 10);
    }

    public void setPreferredOpportunisticDataSubscription(int n, boolean bl, final Executor executor, final Consumer<Integer> consumer) {
        IOns iOns;
        Object object;
        block3 : {
            object = this.mContext;
            object = object != null ? ((Context)object).getOpPackageName() : "<unknown>";
            iOns = this.getIOns();
            if (iOns != null) break block3;
            return;
        }
        try {
            ISetOpportunisticDataCallback.Stub stub = new ISetOpportunisticDataCallback.Stub(){

                static /* synthetic */ void lambda$onComplete$0(Consumer consumer2, int n) {
                    consumer2.accept(n);
                }

                static /* synthetic */ void lambda$onComplete$1(Executor executor2, Consumer consumer2, int n) throws Exception {
                    executor2.execute(new _$$Lambda$TelephonyManager$5$dLg4hbo46SmKP0wtKbXAlS8hCpg(consumer2, n));
                }

                @Override
                public void onComplete(int n) {
                    Consumer consumer2;
                    Executor executor2 = executor;
                    if (executor2 != null && (consumer2 = consumer) != null) {
                        Binder.withCleanCallingIdentity(new _$$Lambda$TelephonyManager$5$RFt1EExZlmUUXRBea_EWHl9kTkc(executor2, consumer2, n));
                        return;
                    }
                }
            };
            iOns.setPreferredDataSubscriptionId(n, bl, stub, (String)object);
        }
        catch (RemoteException remoteException) {
            Rlog.e(TAG, "setPreferredDataSubscriptionId RemoteException", remoteException);
        }
    }

    @SystemApi
    public boolean setRadio(boolean bl) {
        block3 : {
            ITelephony iTelephony = this.getITelephony();
            if (iTelephony == null) break block3;
            try {
                bl = iTelephony.setRadio(bl);
                return bl;
            }
            catch (RemoteException remoteException) {
                Log.e(TAG, "Error calling ITelephony#setRadio", remoteException);
            }
        }
        return false;
    }

    public void setRadioIndicationUpdateMode(int n, int n2) {
        block3 : {
            ITelephony iTelephony = this.getITelephony();
            if (iTelephony == null) break block3;
            try {
                iTelephony.setRadioIndicationUpdateMode(this.getSubId(), n, n2);
            }
            catch (RemoteException remoteException) {
                if (this.isSystemProcess()) break block3;
                remoteException.rethrowAsRuntimeException();
            }
        }
    }

    @SystemApi
    public boolean setRadioPower(boolean bl) {
        block3 : {
            ITelephony iTelephony = this.getITelephony();
            if (iTelephony == null) break block3;
            try {
                bl = iTelephony.setRadioPower(bl);
                return bl;
            }
            catch (RemoteException remoteException) {
                Log.e(TAG, "Error calling ITelephony#setRadioPower", remoteException);
            }
        }
        return false;
    }

    public boolean setRoamingOverride(int n, List<String> list, List<String> list2, List<String> list3, List<String> list4) {
        block4 : {
            ITelephony iTelephony = this.getITelephony();
            if (iTelephony == null) break block4;
            try {
                boolean bl = iTelephony.setRoamingOverride(n, list, list2, list3, list4);
                return bl;
            }
            catch (NullPointerException nullPointerException) {
                Rlog.e(TAG, "setRoamingOverride NPE", nullPointerException);
            }
            catch (RemoteException remoteException) {
                Rlog.e(TAG, "setRoamingOverride RemoteException", remoteException);
            }
        }
        return false;
    }

    @UnsupportedAppUsage
    public boolean setRoamingOverride(List<String> list, List<String> list2, List<String> list3, List<String> list4) {
        return this.setRoamingOverride(this.getSubId(), list, list2, list3, list4);
    }

    public void setSimCountryIso(String string2) {
        this.setSimCountryIsoForPhone(this.getPhoneId(), string2);
    }

    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    public void setSimCountryIsoForPhone(int n, String string2) {
        TelephonyManager.setTelephonyProperty(n, "gsm.sim.operator.iso-country", string2);
    }

    public void setSimOperatorName(String string2) {
        this.setSimOperatorNameForPhone(this.getPhoneId(), string2);
    }

    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    public void setSimOperatorNameForPhone(int n, String string2) {
        TelephonyManager.setTelephonyProperty(n, "gsm.sim.operator.alpha", string2);
    }

    public void setSimOperatorNumeric(String string2) {
        this.setSimOperatorNumericForPhone(this.getPhoneId(), string2);
    }

    @UnsupportedAppUsage
    public void setSimOperatorNumericForPhone(int n, String string2) {
        TelephonyManager.setTelephonyProperty(n, "gsm.sim.operator.numeric", string2);
    }

    @SystemApi
    public void setSimPowerState(int n) {
        this.setSimPowerStateForSlot(this.getSlotIndex(), n);
    }

    @SystemApi
    public void setSimPowerStateForSlot(int n, int n2) {
        block4 : {
            ITelephony iTelephony = this.getITelephony();
            if (iTelephony == null) break block4;
            try {
                iTelephony.setSimPowerStateForSlot(n, n2);
            }
            catch (SecurityException securityException) {
                Log.e(TAG, "Permission error calling ITelephony#setSimPowerStateForSlot", securityException);
            }
            catch (RemoteException remoteException) {
                Log.e(TAG, "Error calling ITelephony#setSimPowerStateForSlot", remoteException);
            }
        }
    }

    public void setSimState(String string2) {
        this.setSimStateForPhone(this.getPhoneId(), string2);
    }

    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    public void setSimStateForPhone(int n, String string2) {
        TelephonyManager.setTelephonyProperty(n, "gsm.sim.state", string2);
    }

    @SystemApi
    @SuppressLint(value={"Doclava125"})
    public void setVisualVoicemailEnabled(PhoneAccountHandle phoneAccountHandle, boolean bl) {
    }

    public void setVisualVoicemailSmsFilterSettings(VisualVoicemailSmsFilterSettings visualVoicemailSmsFilterSettings) {
        if (visualVoicemailSmsFilterSettings == null) {
            this.disableVisualVoicemailSmsFilter(this.mSubId);
        } else {
            this.enableVisualVoicemailSmsFilter(this.mSubId, visualVoicemailSmsFilterSettings);
        }
    }

    @SystemApi
    public void setVoiceActivationState(int n) {
        this.setVoiceActivationState(this.getSubId(), n);
    }

    public void setVoiceActivationState(int n, int n2) {
        block4 : {
            ITelephony iTelephony = this.getITelephony();
            if (iTelephony == null) break block4;
            try {
                iTelephony.setVoiceActivationState(n, n2);
            }
            catch (NullPointerException nullPointerException) {
            }
            catch (RemoteException remoteException) {
                // empty catch block
            }
        }
    }

    public boolean setVoiceMailNumber(int n, String string2, String string3) {
        block4 : {
            ITelephony iTelephony = this.getITelephony();
            if (iTelephony == null) break block4;
            try {
                boolean bl = iTelephony.setVoiceMailNumber(n, string2, string3);
                return bl;
            }
            catch (NullPointerException nullPointerException) {
            }
            catch (RemoteException remoteException) {
                // empty catch block
            }
        }
        return false;
    }

    public boolean setVoiceMailNumber(String string2, String string3) {
        return this.setVoiceMailNumber(this.getSubId(), string2, string3);
    }

    public void setVoicemailRingtoneUri(PhoneAccountHandle phoneAccountHandle, Uri uri) {
        block3 : {
            ITelephony iTelephony = this.getITelephony();
            if (iTelephony == null) break block3;
            try {
                iTelephony.setVoicemailRingtoneUri(this.getOpPackageName(), phoneAccountHandle, uri);
            }
            catch (RemoteException remoteException) {
                Log.e(TAG, "Error calling ITelephony#setVoicemailRingtoneUri", remoteException);
            }
        }
    }

    public void setVoicemailVibrationEnabled(PhoneAccountHandle phoneAccountHandle, boolean bl) {
        block3 : {
            ITelephony iTelephony = this.getITelephony();
            if (iTelephony == null) break block3;
            try {
                iTelephony.setVoicemailVibrationEnabled(this.getOpPackageName(), phoneAccountHandle, bl);
            }
            catch (RemoteException remoteException) {
                Log.e(TAG, "Error calling ITelephony#isVoicemailVibrationEnabled", remoteException);
            }
        }
    }

    @SystemApi
    @Deprecated
    @SuppressLint(value={"Doclava125"})
    public void silenceRinger() {
    }

    @SystemApi
    public boolean supplyPin(String string2) {
        block3 : {
            ITelephony iTelephony = this.getITelephony();
            if (iTelephony == null) break block3;
            try {
                boolean bl = iTelephony.supplyPin(string2);
                return bl;
            }
            catch (RemoteException remoteException) {
                Log.e(TAG, "Error calling ITelephony#supplyPin", remoteException);
            }
        }
        return false;
    }

    @SystemApi
    public int[] supplyPinReportResult(String arrn) {
        block3 : {
            ITelephony iTelephony = this.getITelephony();
            if (iTelephony == null) break block3;
            try {
                arrn = iTelephony.supplyPinReportResult((String)arrn);
                return arrn;
            }
            catch (RemoteException remoteException) {
                Log.e(TAG, "Error calling ITelephony#supplyPinReportResult", remoteException);
            }
        }
        return new int[0];
    }

    @SystemApi
    public boolean supplyPuk(String string2, String string3) {
        block3 : {
            ITelephony iTelephony = this.getITelephony();
            if (iTelephony == null) break block3;
            try {
                boolean bl = iTelephony.supplyPuk(string2, string3);
                return bl;
            }
            catch (RemoteException remoteException) {
                Log.e(TAG, "Error calling ITelephony#supplyPuk", remoteException);
            }
        }
        return false;
    }

    @SystemApi
    public int[] supplyPukReportResult(String arrn, String string2) {
        block3 : {
            ITelephony iTelephony = this.getITelephony();
            if (iTelephony == null) break block3;
            try {
                arrn = iTelephony.supplyPukReportResult((String)arrn, string2);
                return arrn;
            }
            catch (RemoteException remoteException) {
                Log.e(TAG, "Error calling ITelephony#]", remoteException);
            }
        }
        return new int[0];
    }

    public void switchMultiSimConfig(int n) {
        block3 : {
            ITelephony iTelephony = this.getITelephony();
            if (iTelephony == null) break block3;
            try {
                iTelephony.switchMultiSimConfig(n);
            }
            catch (RemoteException remoteException) {
                Rlog.e(TAG, "switchMultiSimConfig RemoteException", remoteException);
            }
        }
    }

    @SystemApi
    public boolean switchSlots(int[] arrn) {
        ITelephony iTelephony;
        block3 : {
            try {
                iTelephony = this.getITelephony();
                if (iTelephony != null) break block3;
                return false;
            }
            catch (RemoteException remoteException) {
                return false;
            }
        }
        boolean bl = iTelephony.switchSlots(arrn);
        return bl;
    }

    @SystemApi
    public void toggleRadioOnOff() {
        block3 : {
            ITelephony iTelephony = this.getITelephony();
            if (iTelephony == null) break block3;
            try {
                iTelephony.toggleRadioOnOff();
            }
            catch (RemoteException remoteException) {
                Log.e(TAG, "Error calling ITelephony#toggleRadioOnOff", remoteException);
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void updateAvailableNetworks(List<AvailableNetworkInfo> object, final Executor executor, final Consumer<Integer> consumer) {
        Object object2 = this.mContext;
        object2 = object2 != null ? ((Context)object2).getOpPackageName() : "<unknown>";
        try {
            IOns iOns = this.getIOns();
            if (iOns != null && object != null) {
                IUpdateAvailableNetworksCallback.Stub stub = new IUpdateAvailableNetworksCallback.Stub(){

                    static /* synthetic */ void lambda$onComplete$0(Consumer consumer2, int n) {
                        consumer2.accept(n);
                    }

                    static /* synthetic */ void lambda$onComplete$1(Executor executor2, Consumer consumer2, int n) throws Exception {
                        executor2.execute(new _$$Lambda$TelephonyManager$6$AFjFk42NCFYCMG8wA5_6SCfk7No(consumer2, n));
                    }

                    @Override
                    public void onComplete(int n) {
                        Consumer consumer2;
                        Executor executor2 = executor;
                        if (executor2 != null && (consumer2 = consumer) != null) {
                            Binder.withCleanCallingIdentity(new _$$Lambda$TelephonyManager$6$1S5Pi2oZUOPIU8alAP53FlL2sjk(executor2, consumer2, n));
                            return;
                        }
                    }
                };
                iOns.updateAvailableNetworks((List<AvailableNetworkInfo>)object, stub, (String)object2);
                return;
            }
            object = new _$$Lambda$TelephonyManager$eMNW6lCcxHLvIrcBQvhUXUKuLFU(executor, consumer);
            Binder.withCleanCallingIdentity((FunctionalUtils.ThrowingRunnable)object);
            return;
        }
        catch (RemoteException remoteException) {
            Rlog.e(TAG, "updateAvailableNetworks RemoteException", remoteException);
        }
    }

    @SystemApi
    @SuppressLint(value={"Doclava125"})
    public void updateServiceLocation() {
        block3 : {
            ITelephony iTelephony = this.getITelephony();
            if (iTelephony == null) break block3;
            try {
                iTelephony.updateServiceLocation();
            }
            catch (RemoteException remoteException) {
                Log.e(TAG, "Error calling ITelephony#updateServiceLocation", remoteException);
            }
        }
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface CallState {
    }

    public static abstract class CellInfoCallback {
        public static final int ERROR_MODEM_ERROR = 2;
        public static final int ERROR_TIMEOUT = 1;

        public abstract void onCellInfo(List<CellInfo> var1);

        public void onError(int n, Throwable throwable) {
            this.onCellInfo(new ArrayList<CellInfo>());
        }

        @Retention(value=RetentionPolicy.SOURCE)
        public static @interface CellInfoCallbackError {
        }

    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface DataState {
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface DefaultSubscriptionSelectType {
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface IndicationFilters {
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface IndicationUpdateMode {
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface IsMultiSimSupportedResult {
    }

    public static enum MultiSimVariants {
        DSDS,
        DSDA,
        TSTS,
        UNKNOWN;
        
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface NetworkSelectionMode {
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface NetworkType {
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface NetworkTypeBitMask {
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface PrefNetworkMode {
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface RadioPowerState {
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface SetCarrierRestrictionResult {
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface SetOpportunisticSubscriptionResult {
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface SimActivationState {
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface SimCombinationWarningType {
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface SrvccState {
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface UiccAppType {
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface UpdateAvailableNetworksResult {
    }

    public static abstract class UssdResponseCallback {
        public void onReceiveUssdResponse(TelephonyManager telephonyManager, String string2, CharSequence charSequence) {
        }

        public void onReceiveUssdResponseFailed(TelephonyManager telephonyManager, String string2, int n) {
        }
    }

    public static interface WifiCallingChoices {
        public static final int ALWAYS_USE = 0;
        public static final int ASK_EVERY_TIME = 1;
        public static final int NEVER_USE = 2;
    }

}

