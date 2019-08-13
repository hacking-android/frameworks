/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.annotation.UnsupportedAppUsage
 *  android.app.BroadcastOptions
 *  android.content.BroadcastReceiver
 *  android.content.ContentResolver
 *  android.content.Context
 *  android.content.Intent
 *  android.content.IntentFilter
 *  android.content.SharedPreferences
 *  android.content.SharedPreferences$Editor
 *  android.content.res.Resources
 *  android.net.LinkProperties
 *  android.net.NetworkCapabilities
 *  android.net.NetworkStats
 *  android.net.Uri
 *  android.net.wifi.WifiManager
 *  android.os.AsyncResult
 *  android.os.Build
 *  android.os.Bundle
 *  android.os.Handler
 *  android.os.Looper
 *  android.os.Message
 *  android.os.PersistableBundle
 *  android.os.Registrant
 *  android.os.RegistrantList
 *  android.os.SystemClock
 *  android.os.SystemProperties
 *  android.os.WorkSource
 *  android.preference.PreferenceManager
 *  android.provider.Settings
 *  android.provider.Settings$Global
 *  android.provider.Settings$SettingNotFoundException
 *  android.telephony.CarrierConfigManager
 *  android.telephony.CarrierRestrictionRules
 *  android.telephony.CellInfo
 *  android.telephony.CellLocation
 *  android.telephony.ClientRequestStats
 *  android.telephony.ImsiEncryptionInfo
 *  android.telephony.PhysicalChannelConfig
 *  android.telephony.RadioAccessFamily
 *  android.telephony.Rlog
 *  android.telephony.ServiceState
 *  android.telephony.SignalStrength
 *  android.telephony.SubscriptionManager
 *  android.telephony.TelephonyManager
 *  android.telephony.data.ApnSetting
 *  android.text.TextUtils
 *  android.util.LocalLog
 *  android.util.SparseArray
 *  com.android.ims.ImsCall
 *  com.android.ims.ImsManager
 *  com.android.internal.telephony.OperatorInfo
 *  com.android.internal.telephony.PhoneConstants
 *  com.android.internal.telephony.PhoneConstants$DataState
 *  com.android.internal.telephony.PhoneConstants$State
 */
package com.android.internal.telephony;

import android.annotation.UnsupportedAppUsage;
import android.app.BroadcastOptions;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.net.LinkProperties;
import android.net.NetworkCapabilities;
import android.net.NetworkStats;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.AsyncResult;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.PersistableBundle;
import android.os.Registrant;
import android.os.RegistrantList;
import android.os.SystemClock;
import android.os.SystemProperties;
import android.os.WorkSource;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.telephony.CarrierConfigManager;
import android.telephony.CarrierRestrictionRules;
import android.telephony.CellInfo;
import android.telephony.CellLocation;
import android.telephony.ClientRequestStats;
import android.telephony.ImsiEncryptionInfo;
import android.telephony.PhysicalChannelConfig;
import android.telephony.RadioAccessFamily;
import android.telephony.Rlog;
import android.telephony.ServiceState;
import android.telephony.SignalStrength;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.telephony.data.ApnSetting;
import android.text.TextUtils;
import android.util.LocalLog;
import android.util.SparseArray;
import com.android.ims.ImsCall;
import com.android.ims.ImsManager;
import com.android.internal.telephony.AppSmsManager;
import com.android.internal.telephony.Call;
import com.android.internal.telephony.CallManager;
import com.android.internal.telephony.CallStateException;
import com.android.internal.telephony.CallTracker;
import com.android.internal.telephony.CarrierActionAgent;
import com.android.internal.telephony.CarrierResolver;
import com.android.internal.telephony.CarrierSignalAgent;
import com.android.internal.telephony.CommandException;
import com.android.internal.telephony.CommandsInterface;
import com.android.internal.telephony.Connection;
import com.android.internal.telephony.DeviceStateMonitor;
import com.android.internal.telephony.HalVersion;
import com.android.internal.telephony.IccCard;
import com.android.internal.telephony.IccSmsInterfaceManager;
import com.android.internal.telephony.OperatorInfo;
import com.android.internal.telephony.PhoneConstants;
import com.android.internal.telephony.PhoneFactory;
import com.android.internal.telephony.PhoneInternalInterface;
import com.android.internal.telephony.PhoneNotifier;
import com.android.internal.telephony.RIL;
import com.android.internal.telephony.RadioCapability;
import com.android.internal.telephony.ServiceStateTracker;
import com.android.internal.telephony.SimActivationTracker;
import com.android.internal.telephony.SmsStorageMonitor;
import com.android.internal.telephony.SmsUsageMonitor;
import com.android.internal.telephony.SubscriptionController;
import com.android.internal.telephony.TelephonyComponentFactory;
import com.android.internal.telephony.TelephonyTester;
import com.android.internal.telephony.dataconnection.DataConnectionReasons;
import com.android.internal.telephony.dataconnection.DataEnabledSettings;
import com.android.internal.telephony.dataconnection.DcTracker;
import com.android.internal.telephony.dataconnection.TransportManager;
import com.android.internal.telephony.emergency.EmergencyNumberTracker;
import com.android.internal.telephony.imsphone.ImsPhoneCall;
import com.android.internal.telephony.test.SimulatedRadioControl;
import com.android.internal.telephony.uicc.IccCardApplicationStatus;
import com.android.internal.telephony.uicc.IccFileHandler;
import com.android.internal.telephony.uicc.IccRecords;
import com.android.internal.telephony.uicc.IsimRecords;
import com.android.internal.telephony.uicc.UiccCard;
import com.android.internal.telephony.uicc.UiccCardApplication;
import com.android.internal.telephony.uicc.UiccController;
import com.android.internal.telephony.uicc.UsimServiceTable;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;

public abstract class Phone
extends Handler
implements PhoneInternalInterface {
    private static final int ALREADY_IN_AUTO_SELECTION = 1;
    private static final String CDMA_NON_ROAMING_LIST_OVERRIDE_PREFIX = "cdma_non_roaming_list_";
    private static final String CDMA_ROAMING_LIST_OVERRIDE_PREFIX = "cdma_roaming_list_";
    public static final String CF_ID = "cf_id_key";
    public static final String CF_STATUS = "cf_status_key";
    public static final String CLIR_KEY = "clir_key";
    public static final String CS_FALLBACK = "cs_fallback";
    public static final String DATA_DISABLED_ON_BOOT_KEY = "disabled_on_boot_key";
    public static final String DATA_ROAMING_IS_USER_SETTING_KEY = "data_roaming_is_user_setting_key";
    private static final int DEFAULT_REPORT_INTERVAL_MS = 200;
    private static final String DNS_SERVER_CHECK_DISABLED_KEY = "dns_server_check_disabled_key";
    private static final int EMERGENCY_SMS_NO_TIME_RECORDED = -1;
    private static final int EMERGENCY_SMS_TIMER_MAX_MS = 300000;
    private static final int EVENT_ALL_DATA_DISCONNECTED = 52;
    protected static final int EVENT_CALL_RING = 14;
    private static final int EVENT_CALL_RING_CONTINUE = 15;
    protected static final int EVENT_CARRIER_CONFIG_CHANGED = 43;
    protected static final int EVENT_CDMA_SUBSCRIPTION_SOURCE_CHANGED = 27;
    private static final int EVENT_CHECK_FOR_NETWORK_AUTOMATIC = 38;
    private static final int EVENT_CONFIG_LCE = 37;
    protected static final int EVENT_DEVICE_PROVISIONED_CHANGE = 49;
    protected static final int EVENT_DEVICE_PROVISIONING_DATA_SETTING_CHANGE = 50;
    protected static final int EVENT_EMERGENCY_CALLBACK_MODE_ENTER = 25;
    protected static final int EVENT_EXIT_EMERGENCY_CALLBACK_RESPONSE = 26;
    protected static final int EVENT_GET_AVAILABLE_NETWORKS_DONE = 51;
    protected static final int EVENT_GET_BASEBAND_VERSION_DONE = 6;
    protected static final int EVENT_GET_CALL_FORWARD_DONE = 13;
    protected static final int EVENT_GET_DEVICE_IDENTITY_DONE = 21;
    protected static final int EVENT_GET_IMEISV_DONE = 10;
    protected static final int EVENT_GET_IMEI_DONE = 9;
    protected static final int EVENT_GET_RADIO_CAPABILITY = 35;
    private static final int EVENT_GET_SIM_STATUS_DONE = 11;
    private static final int EVENT_ICC_CHANGED = 30;
    protected static final int EVENT_ICC_RECORD_EVENTS = 29;
    private static final int EVENT_INITIATE_SILENT_REDIAL = 32;
    protected static final int EVENT_LAST = 52;
    private static final int EVENT_MMI_DONE = 4;
    protected static final int EVENT_MODEM_RESET = 45;
    protected static final int EVENT_NV_READY = 23;
    protected static final int EVENT_RADIO_AVAILABLE = 1;
    private static final int EVENT_RADIO_NOT_AVAILABLE = 33;
    protected static final int EVENT_RADIO_OFF_OR_NOT_AVAILABLE = 8;
    protected static final int EVENT_RADIO_ON = 5;
    protected static final int EVENT_RADIO_STATE_CHANGED = 47;
    protected static final int EVENT_REGISTERED_TO_NETWORK = 19;
    protected static final int EVENT_REQUEST_VOICE_RADIO_TECH_DONE = 40;
    protected static final int EVENT_RIL_CONNECTED = 41;
    protected static final int EVENT_RUIM_RECORDS_LOADED = 22;
    protected static final int EVENT_SET_CALL_FORWARD_DONE = 12;
    protected static final int EVENT_SET_CARRIER_DATA_ENABLED = 48;
    protected static final int EVENT_SET_CLIR_COMPLETE = 18;
    private static final int EVENT_SET_ENHANCED_VP = 24;
    protected static final int EVENT_SET_NETWORK_AUTOMATIC = 28;
    private static final int EVENT_SET_NETWORK_AUTOMATIC_COMPLETE = 17;
    private static final int EVENT_SET_NETWORK_MANUAL_COMPLETE = 16;
    protected static final int EVENT_SET_ROAMING_PREFERENCE_DONE = 44;
    protected static final int EVENT_SET_VM_NUMBER_DONE = 20;
    protected static final int EVENT_SIM_RECORDS_LOADED = 3;
    private static final int EVENT_SRVCC_STATE_CHANGED = 31;
    protected static final int EVENT_SS = 36;
    protected static final int EVENT_SSN = 2;
    private static final int EVENT_UNSOL_OEM_HOOK_RAW = 34;
    protected static final int EVENT_UPDATE_PHONE_OBJECT = 42;
    protected static final int EVENT_USSD = 7;
    protected static final int EVENT_VOICE_RADIO_TECH_CHANGED = 39;
    protected static final int EVENT_VRS_OR_RAT_CHANGED = 46;
    public static final String EXTRA_KEY_ALERT_MESSAGE = "alertMessage";
    public static final String EXTRA_KEY_ALERT_SHOW = "alertShow";
    public static final String EXTRA_KEY_ALERT_TITLE = "alertTitle";
    public static final String EXTRA_KEY_NOTIFICATION_MESSAGE = "notificationMessage";
    private static final String GSM_NON_ROAMING_LIST_OVERRIDE_PREFIX = "gsm_non_roaming_list_";
    private static final String GSM_ROAMING_LIST_OVERRIDE_PREFIX = "gsm_roaming_list_";
    private static final boolean LCE_PULL_MODE = true;
    private static final String LOG_TAG = "Phone";
    public static final String NETWORK_SELECTION_KEY = "network_selection_key";
    public static final String NETWORK_SELECTION_NAME_KEY = "network_selection_name_key";
    public static final String NETWORK_SELECTION_SHORT_KEY = "network_selection_short_key";
    private static final String VM_COUNT = "vm_count_key";
    private static final String VM_ID = "vm_id_key";
    protected static final Object lockForRadioTechnologyChange = new Object();
    protected final int USSD_MAX_QUEUE;
    private final String mActionAttached;
    private final String mActionDetached;
    private final RegistrantList mAllDataDisconnectedRegistrants = new RegistrantList();
    private final AppSmsManager mAppSmsManager;
    private int mCallRingContinueToken;
    private int mCallRingDelay;
    protected CarrierActionAgent mCarrierActionAgent;
    protected CarrierResolver mCarrierResolver;
    protected CarrierSignalAgent mCarrierSignalAgent;
    private final RegistrantList mCellInfoRegistrants = new RegistrantList();
    @UnsupportedAppUsage
    public CommandsInterface mCi;
    @UnsupportedAppUsage
    protected final Context mContext;
    protected DataEnabledSettings mDataEnabledSettings;
    protected final SparseArray<DcTracker> mDcTrackers = new SparseArray();
    protected DeviceStateMonitor mDeviceStateMonitor;
    protected final RegistrantList mDisconnectRegistrants = new RegistrantList();
    private boolean mDnsCheckDisabled;
    private boolean mDoesRilSendMultipleCallRing;
    protected final RegistrantList mEmergencyCallToggledRegistrants = new RegistrantList();
    private final RegistrantList mHandoverRegistrants = new RegistrantList();
    @UnsupportedAppUsage
    protected final AtomicReference<IccRecords> mIccRecords = new AtomicReference();
    private BroadcastReceiver mImsIntentReceiver = new BroadcastReceiver(){

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public void onReceive(Context object, Intent intent) {
            object = new StringBuilder();
            ((StringBuilder)object).append("mImsIntentReceiver: action ");
            ((StringBuilder)object).append(intent.getAction());
            Rlog.d((String)Phone.LOG_TAG, (String)((StringBuilder)object).toString());
            if (intent.hasExtra("android:phone_id")) {
                int n = intent.getIntExtra("android:phone_id", -1);
                object = new StringBuilder();
                ((StringBuilder)object).append("mImsIntentReceiver: extraPhoneId = ");
                ((StringBuilder)object).append(n);
                Rlog.d((String)Phone.LOG_TAG, (String)((StringBuilder)object).toString());
                if (n == -1 || n != Phone.this.getPhoneId()) {
                    return;
                }
            }
            object = lockForRadioTechnologyChange;
            synchronized (object) {
                if (intent.getAction().equals("com.android.ims.IMS_SERVICE_UP")) {
                    Phone.this.mImsServiceReady = true;
                    Phone.this.updateImsPhone();
                    ImsManager.getInstance((Context)Phone.this.mContext, (int)Phone.this.mPhoneId).updateImsServiceConfig(false);
                } else if (intent.getAction().equals("com.android.ims.IMS_SERVICE_DOWN")) {
                    Phone.this.mImsServiceReady = false;
                    Phone.this.updateImsPhone();
                }
                return;
            }
        }
    };
    @UnsupportedAppUsage
    protected Phone mImsPhone = null;
    private boolean mImsServiceReady = false;
    private final RegistrantList mIncomingRingRegistrants = new RegistrantList();
    protected boolean mIsPhoneInEcmState = false;
    protected boolean mIsVideoCapable = false;
    private boolean mIsVoiceCapable = true;
    private int mLceStatus = -1;
    protected final LocalLog mLocalLog;
    private Looper mLooper;
    protected final RegistrantList mMmiCompleteRegistrants = new RegistrantList();
    @UnsupportedAppUsage
    protected final RegistrantList mMmiRegistrants = new RegistrantList();
    private String mName;
    private final RegistrantList mNewRingingConnectionRegistrants = new RegistrantList();
    @UnsupportedAppUsage
    protected PhoneNotifier mNotifier;
    @UnsupportedAppUsage
    protected int mPhoneId;
    protected Registrant mPostDialHandler;
    private final RegistrantList mPreciseCallStateRegistrants = new RegistrantList();
    private final AtomicReference<RadioCapability> mRadioCapability = new AtomicReference();
    protected final RegistrantList mRadioOffOrNotAvailableRegistrants = new RegistrantList();
    private final RegistrantList mServiceStateRegistrants = new RegistrantList();
    private SimActivationTracker mSimActivationTracker;
    protected final RegistrantList mSimRecordsLoadedRegistrants = new RegistrantList();
    protected SimulatedRadioControl mSimulatedRadioControl;
    @UnsupportedAppUsage
    public SmsStorageMonitor mSmsStorageMonitor;
    public SmsUsageMonitor mSmsUsageMonitor;
    protected final RegistrantList mSuppServiceFailedRegistrants = new RegistrantList();
    protected TelephonyComponentFactory mTelephonyComponentFactory;
    TelephonyTester mTelephonyTester;
    private volatile long mTimeLastEmergencySmsSentMs = -1L;
    protected TransportManager mTransportManager;
    @UnsupportedAppUsage
    protected AtomicReference<UiccCardApplication> mUiccApplication = new AtomicReference();
    @UnsupportedAppUsage
    protected UiccController mUiccController = null;
    private boolean mUnitTestMode;
    protected final RegistrantList mUnknownConnectionRegistrants = new RegistrantList();
    private final RegistrantList mVideoCapabilityChangedRegistrants = new RegistrantList();
    protected int mVmCount = 0;

    protected Phone(String string, PhoneNotifier phoneNotifier, Context context, CommandsInterface commandsInterface, boolean bl) {
        this(string, phoneNotifier, context, commandsInterface, bl, Integer.MAX_VALUE, TelephonyComponentFactory.getInstance());
    }

    protected Phone(String object, PhoneNotifier object2, Context context, CommandsInterface commandsInterface, boolean bl, int n, TelephonyComponentFactory telephonyComponentFactory) {
        this.USSD_MAX_QUEUE = 10;
        this.mPhoneId = n;
        this.mName = object;
        this.mNotifier = object2;
        this.mContext = context;
        this.mLooper = Looper.myLooper();
        this.mCi = commandsInterface;
        object = new StringBuilder();
        ((StringBuilder)object).append(this.getClass().getPackage().getName());
        ((StringBuilder)object).append(".action_detached");
        this.mActionDetached = ((StringBuilder)object).toString();
        object = new StringBuilder();
        ((StringBuilder)object).append(this.getClass().getPackage().getName());
        ((StringBuilder)object).append(".action_attached");
        this.mActionAttached = ((StringBuilder)object).toString();
        this.mAppSmsManager = telephonyComponentFactory.inject(AppSmsManager.class.getName()).makeAppSmsManager(context);
        this.mLocalLog = new LocalLog(64);
        if (Build.IS_DEBUGGABLE) {
            this.mTelephonyTester = new TelephonyTester(this);
        }
        this.setUnitTestMode(bl);
        this.mDnsCheckDisabled = PreferenceManager.getDefaultSharedPreferences((Context)context).getBoolean(DNS_SERVER_CHECK_DISABLED_KEY, false);
        this.mCi.setOnCallRing(this, 14, null);
        this.mIsVoiceCapable = this.mContext.getResources().getBoolean(17891570);
        this.mDoesRilSendMultipleCallRing = SystemProperties.getBoolean((String)"ro.telephony.call_ring.multiple", (boolean)true);
        object = new StringBuilder();
        ((StringBuilder)object).append("mDoesRilSendMultipleCallRing=");
        ((StringBuilder)object).append(this.mDoesRilSendMultipleCallRing);
        Rlog.d((String)LOG_TAG, (String)((StringBuilder)object).toString());
        this.mCallRingDelay = SystemProperties.getInt((String)"ro.telephony.call_ring.delay", (int)3000);
        object = new StringBuilder();
        ((StringBuilder)object).append("mCallRingDelay=");
        ((StringBuilder)object).append(this.mCallRingDelay);
        Rlog.d((String)LOG_TAG, (String)((StringBuilder)object).toString());
        if (this.getPhoneType() == 5) {
            return;
        }
        object = Phone.getLocaleFromCarrierProperties(this.mContext);
        if (object != null && !TextUtils.isEmpty((CharSequence)((Locale)object).getCountry())) {
            object2 = ((Locale)object).getCountry();
            try {
                Settings.Global.getInt((ContentResolver)this.mContext.getContentResolver(), (String)"wifi_country_code");
            }
            catch (Settings.SettingNotFoundException settingNotFoundException) {
                ((WifiManager)this.mContext.getSystemService("wifi")).setCountryCode((String)object2);
            }
        }
        this.mTelephonyComponentFactory = telephonyComponentFactory;
        this.mSmsStorageMonitor = this.mTelephonyComponentFactory.inject(SmsStorageMonitor.class.getName()).makeSmsStorageMonitor(this);
        this.mSmsUsageMonitor = this.mTelephonyComponentFactory.inject(SmsUsageMonitor.class.getName()).makeSmsUsageMonitor(context);
        this.mUiccController = UiccController.getInstance();
        this.mUiccController.registerForIccChanged(this, 30, null);
        this.mSimActivationTracker = this.mTelephonyComponentFactory.inject(SimActivationTracker.class.getName()).makeSimActivationTracker(this);
        if (this.getPhoneType() != 3) {
            this.mCi.registerForSrvccStateChanged(this, 31, null);
        }
        this.mCi.setOnUnsolOemHookRaw(this, 34, null);
        this.mCi.startLceService(200, true, this.obtainMessage(37));
    }

    private void checkCorrectThread(Handler handler) {
        if (handler.getLooper() == this.mLooper) {
            return;
        }
        throw new RuntimeException("com.android.internal.telephony.Phone must be used from within one thread");
    }

    public static void checkWfcWifiOnlyModeBeforeDial(Phone phone, int n, Context context) throws CallStateException {
        if (phone != null && phone.isWifiCallingEnabled() || (n = (phone = ImsManager.getInstance((Context)context, (int)n)).isWfcEnabledByPlatform() && phone.isWfcEnabledByUser() && phone.getWfcMode() == 0 ? 1 : 0) == 0) {
            return;
        }
        throw new CallStateException(1, "WFC Wi-Fi Only Mode: IMS not registered");
    }

    private void clearSavedNetworkSelection() {
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences((Context)this.getContext()).edit();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(NETWORK_SELECTION_KEY);
        stringBuilder.append(this.getSubId());
        editor = editor.remove(stringBuilder.toString());
        stringBuilder = new StringBuilder();
        stringBuilder.append(NETWORK_SELECTION_NAME_KEY);
        stringBuilder.append(this.getSubId());
        editor = editor.remove(stringBuilder.toString());
        stringBuilder = new StringBuilder();
        stringBuilder.append(NETWORK_SELECTION_SHORT_KEY);
        stringBuilder.append(this.getSubId());
        editor.remove(stringBuilder.toString()).commit();
    }

    private int getCallForwardingIndicatorFromSharedPref() {
        int n = 0;
        int n2 = this.getSubId();
        if (SubscriptionManager.isValidSubscriptionId((int)n2)) {
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences((Context)this.mContext);
            CharSequence charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append(CF_STATUS);
            ((StringBuilder)charSequence).append(n2);
            int n3 = sharedPreferences.getInt(((StringBuilder)charSequence).toString(), -1);
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("getCallForwardingIndicatorFromSharedPref: for subId ");
            ((StringBuilder)charSequence).append(n2);
            ((StringBuilder)charSequence).append("= ");
            ((StringBuilder)charSequence).append(n3);
            Rlog.d((String)LOG_TAG, (String)((StringBuilder)charSequence).toString());
            n = n3;
            if (n3 == -1) {
                charSequence = sharedPreferences.getString(CF_ID, null);
                n = n3;
                if (charSequence != null) {
                    if (((String)charSequence).equals(this.getSubscriberId())) {
                        boolean bl = false;
                        n = sharedPreferences.getInt(CF_STATUS, 0);
                        if (n == 1) {
                            bl = true;
                        }
                        this.setCallForwardingIndicatorInSharedPref(bl);
                        charSequence = new StringBuilder();
                        ((StringBuilder)charSequence).append("getCallForwardingIndicatorFromSharedPref: ");
                        ((StringBuilder)charSequence).append(n);
                        Rlog.d((String)LOG_TAG, (String)((StringBuilder)charSequence).toString());
                    } else {
                        Rlog.d((String)LOG_TAG, (String)"getCallForwardingIndicatorFromSharedPref: returning DISABLED as status for matching subscriberId not found");
                        n = n3;
                    }
                    sharedPreferences = sharedPreferences.edit();
                    sharedPreferences.remove(CF_ID);
                    sharedPreferences.remove(CF_STATUS);
                    sharedPreferences.apply();
                }
            }
        } else {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("getCallForwardingIndicatorFromSharedPref: invalid subId ");
            stringBuilder.append(n2);
            Rlog.e((String)LOG_TAG, (String)stringBuilder.toString());
        }
        return n;
    }

    protected static boolean getInEcmMode() {
        return SystemProperties.getBoolean((String)"ril.cdma.inecmmode", (boolean)false);
    }

    private static Locale getLocaleFromCarrierProperties(Context arrcharSequence) {
        String string = SystemProperties.get((String)"ro.carrier");
        if (string != null && string.length() != 0 && !"unknown".equals(string)) {
            arrcharSequence = arrcharSequence.getResources().getTextArray(17235974);
            for (int i = 0; i < arrcharSequence.length; i += 3) {
                if (!string.equals(arrcharSequence[i].toString())) continue;
                return Locale.forLanguageTag(arrcharSequence[i + 1].toString().replace('_', '-'));
            }
            return null;
        }
        return null;
    }

    private boolean getRoamingOverrideHelper(String object, String string) {
        String string2 = this.getIccSerialNumber();
        if (!TextUtils.isEmpty((CharSequence)string2) && !TextUtils.isEmpty((CharSequence)string)) {
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences((Context)this.mContext);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append((String)object);
            stringBuilder.append(string2);
            object = sharedPreferences.getStringSet(stringBuilder.toString(), null);
            if (object == null) {
                return false;
            }
            return object.contains(string);
        }
        return false;
    }

    private OperatorInfo getSavedNetworkSelection() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences((Context)this.getContext());
        CharSequence charSequence = new StringBuilder();
        charSequence.append(NETWORK_SELECTION_KEY);
        charSequence.append(this.getSubId());
        charSequence = sharedPreferences.getString(charSequence.toString(), "");
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(NETWORK_SELECTION_NAME_KEY);
        stringBuilder.append(this.getSubId());
        String string = sharedPreferences.getString(stringBuilder.toString(), "");
        stringBuilder = new StringBuilder();
        stringBuilder.append(NETWORK_SELECTION_SHORT_KEY);
        stringBuilder.append(this.getSubId());
        return new OperatorInfo(string, sharedPreferences.getString(stringBuilder.toString(), ""), (String)charSequence);
    }

    @UnsupportedAppUsage
    private static int getVideoState(Call object) {
        int n = 0;
        if ((object = ((Call)object).getEarliestConnection()) != null) {
            n = ((Connection)object).getVideoState();
        }
        return n;
    }

    private void handleSetSelectNetwork(AsyncResult asyncResult) {
        if (!(asyncResult.userObj instanceof NetworkSelectMessage)) {
            Rlog.e((String)LOG_TAG, (String)"unexpected result from user object.");
            return;
        }
        NetworkSelectMessage networkSelectMessage = (NetworkSelectMessage)asyncResult.userObj;
        if (networkSelectMessage.message != null) {
            AsyncResult.forMessage((Message)networkSelectMessage.message, (Object)asyncResult.result, (Throwable)asyncResult.exception);
            networkSelectMessage.message.sendToTarget();
        }
    }

    private void handleSrvccStateChanged(int[] object) {
        Rlog.d((String)LOG_TAG, (String)"handleSrvccStateChanged");
        ArrayList<Connection> arrayList = null;
        Phone phone = this.mImsPhone;
        Call.SrvccState srvccState = Call.SrvccState.NONE;
        if (object != null && ((int[])object).length != 0) {
            int n = object[0];
            if (n != 0) {
                if (n != 1) {
                    if (n != 2 && n != 3) {
                        return;
                    }
                    object = Call.SrvccState.FAILED;
                } else {
                    object = Call.SrvccState.COMPLETED;
                    if (phone != null) {
                        phone.notifySrvccState((Call.SrvccState)((Object)object));
                    } else {
                        Rlog.d((String)LOG_TAG, (String)"HANDOVER_COMPLETED: mImsPhone null");
                    }
                }
            } else {
                object = Call.SrvccState.STARTED;
                if (phone != null) {
                    arrayList = phone.getHandoverConnection();
                    this.migrateFrom(phone);
                } else {
                    Rlog.d((String)LOG_TAG, (String)"HANDOVER_STARTED: mImsPhone null");
                }
            }
            this.getCallTracker().notifySrvccState((Call.SrvccState)((Object)object), arrayList);
            this.notifySrvccStateChanged(n);
        }
    }

    private boolean isVideoCallOrConference(Call call) {
        boolean bl = call.isMultiparty();
        boolean bl2 = true;
        if (bl) {
            return true;
        }
        if (call instanceof ImsPhoneCall) {
            if ((call = ((ImsPhoneCall)call).getImsCall()) == null || !call.isVideoCall() && !call.wasVideoCall()) {
                bl2 = false;
            }
            return bl2;
        }
        return false;
    }

    private void notifyIncomingRing() {
        if (!this.mIsVoiceCapable) {
            return;
        }
        AsyncResult asyncResult = new AsyncResult(null, (Object)this, null);
        this.mIncomingRingRegistrants.notifyRegistrants(asyncResult);
    }

    private void notifyMessageWaitingIndicator() {
        if (!this.mIsVoiceCapable) {
            return;
        }
        this.mNotifier.notifyMessageWaitingChanged(this);
    }

    private void onCheckForNetworkSelectionModeAutomatic(Message object) {
        AsyncResult asyncResult = (AsyncResult)object.obj;
        Message message = (Message)asyncResult.userObj;
        boolean bl = true;
        boolean bl2 = true;
        boolean bl3 = bl;
        if (asyncResult.exception == null) {
            bl3 = bl;
            if (asyncResult.result != null) {
                try {
                    int n = ((int[])asyncResult.result)[0];
                    bl3 = bl2;
                    if (n == 0) {
                        bl3 = false;
                    }
                }
                catch (Exception exception) {
                    bl3 = bl;
                }
            }
        }
        object = new NetworkSelectMessage();
        object.message = message;
        object.operatorNumeric = "";
        object.operatorAlphaLong = "";
        object.operatorAlphaShort = "";
        if (bl3) {
            asyncResult = this.obtainMessage(17, object);
            this.mCi.setNetworkSelectionModeAutomatic((Message)asyncResult);
        } else {
            Rlog.d((String)LOG_TAG, (String)"setNetworkSelectionModeAutomatic - already auto, ignoring");
            if (object.message != null) {
                object.message.arg1 = 1;
            }
            asyncResult.userObj = object;
            this.handleSetSelectNetwork(asyncResult);
        }
        this.updateSavedNetworkOperator((NetworkSelectMessage)object);
    }

    private void restoreSavedNetworkSelection(Message message) {
        OperatorInfo operatorInfo = this.getSavedNetworkSelection();
        if (operatorInfo != null && !TextUtils.isEmpty((CharSequence)operatorInfo.getOperatorNumeric())) {
            this.selectNetworkManually(operatorInfo, true, message);
        } else {
            this.setNetworkSelectionModeAutomatic(message);
        }
    }

    private void sendIncomingCallRingNotification(int n) {
        if (this.mIsVoiceCapable && !this.mDoesRilSendMultipleCallRing && n == this.mCallRingContinueToken) {
            Rlog.d((String)LOG_TAG, (String)"Sending notifyIncomingRing");
            this.notifyIncomingRing();
            this.sendMessageDelayed(this.obtainMessage(15, n, 0), (long)this.mCallRingDelay);
        } else {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Ignoring ring notification request, mDoesRilSendMultipleCallRing=");
            stringBuilder.append(this.mDoesRilSendMultipleCallRing);
            stringBuilder.append(" token=");
            stringBuilder.append(n);
            stringBuilder.append(" mCallRingContinueToken=");
            stringBuilder.append(this.mCallRingContinueToken);
            stringBuilder.append(" mIsVoiceCapable=");
            stringBuilder.append(this.mIsVoiceCapable);
            Rlog.d((String)LOG_TAG, (String)stringBuilder.toString());
        }
    }

    private void setCallForwardingIndicatorInSharedPref(boolean bl) {
        int n = bl ? 1 : 0;
        int n2 = this.getSubId();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("setCallForwardingIndicatorInSharedPref: Storing status = ");
        stringBuilder.append(n);
        stringBuilder.append(" in pref ");
        stringBuilder.append(CF_STATUS);
        stringBuilder.append(n2);
        Rlog.i((String)LOG_TAG, (String)stringBuilder.toString());
        stringBuilder = PreferenceManager.getDefaultSharedPreferences((Context)this.mContext).edit();
        StringBuilder stringBuilder2 = new StringBuilder();
        stringBuilder2.append(CF_STATUS);
        stringBuilder2.append(n2);
        stringBuilder.putInt(stringBuilder2.toString(), n);
        stringBuilder.apply();
    }

    private void setRoamingOverrideHelper(List<String> list, String string, String string2) {
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences((Context)this.mContext).edit();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(string);
        stringBuilder.append(string2);
        string = stringBuilder.toString();
        if (list != null && !list.isEmpty()) {
            editor.putStringSet(string, new HashSet<String>(list)).commit();
        } else {
            editor.remove(string).commit();
        }
    }

    private void setUnitTestMode(boolean bl) {
        this.mUnitTestMode = bl;
    }

    private void updateImsPhone() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("updateImsPhone mImsServiceReady=");
        stringBuilder.append(this.mImsServiceReady);
        Rlog.d((String)LOG_TAG, (String)stringBuilder.toString());
        if (this.mImsServiceReady && this.mImsPhone == null) {
            this.mImsPhone = PhoneFactory.makeImsPhone(this.mNotifier, this);
            CallManager.getInstance().registerPhone(this.mImsPhone);
            this.mImsPhone.registerForSilentRedial(this, 32, null);
        } else if (!this.mImsServiceReady && this.mImsPhone != null) {
            CallManager.getInstance().unregisterPhone(this.mImsPhone);
            this.mImsPhone.unregisterForSilentRedial(this);
            this.mImsPhone.dispose();
            this.mImsPhone = null;
        }
    }

    private void updateSavedNetworkOperator(NetworkSelectMessage object) {
        int n = this.getSubId();
        if (SubscriptionManager.isValidSubscriptionId((int)n)) {
            SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences((Context)this.getContext()).edit();
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(NETWORK_SELECTION_KEY);
            stringBuilder.append(n);
            editor.putString(stringBuilder.toString(), ((NetworkSelectMessage)object).operatorNumeric);
            stringBuilder = new StringBuilder();
            stringBuilder.append(NETWORK_SELECTION_NAME_KEY);
            stringBuilder.append(n);
            editor.putString(stringBuilder.toString(), ((NetworkSelectMessage)object).operatorAlphaLong);
            stringBuilder = new StringBuilder();
            stringBuilder.append(NETWORK_SELECTION_SHORT_KEY);
            stringBuilder.append(n);
            editor.putString(stringBuilder.toString(), ((NetworkSelectMessage)object).operatorAlphaShort);
            if (!editor.commit()) {
                Rlog.e((String)LOG_TAG, (String)"failed to commit network selection preference");
            }
        } else {
            object = new StringBuilder();
            ((StringBuilder)object).append("Cannot update network selection preference due to invalid subId ");
            ((StringBuilder)object).append(n);
            Rlog.e((String)LOG_TAG, (String)((StringBuilder)object).toString());
        }
    }

    public boolean areAllDataDisconnected() {
        int[] arrn = this.mTransportManager;
        if (arrn != null) {
            for (int n : arrn.getAvailableTransports()) {
                if (this.getDcTracker(n) == null || this.getDcTracker(n).isDisconnected()) continue;
                return false;
            }
        }
        return true;
    }

    public void callEndCleanupHandOverCallIfAny() {
    }

    public void cancelUSSD(Message message) {
    }

    public void carrierActionReportDefaultNetworkStatus(boolean bl) {
        this.mCarrierActionAgent.carrierActionReportDefaultNetworkStatus(bl);
    }

    public void carrierActionResetAll() {
        this.mCarrierActionAgent.carrierActionReset();
    }

    public void carrierActionSetMeteredApnsEnabled(boolean bl) {
        this.mCarrierActionAgent.carrierActionSetMeteredApnsEnabled(bl);
    }

    public void carrierActionSetRadioEnabled(boolean bl) {
        this.mCarrierActionAgent.carrierActionSetRadioEnabled(bl);
    }

    protected Connection dialInternal(String string, PhoneInternalInterface.DialArgs dialArgs) throws CallStateException {
        return null;
    }

    public void disableDnsCheck(boolean bl) {
        this.mDnsCheckDisabled = bl;
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences((Context)this.getContext()).edit();
        editor.putBoolean(DNS_SERVER_CHECK_DISABLED_KEY, bl);
        editor.apply();
    }

    @UnsupportedAppUsage
    public void dispose() {
    }

    public void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] arrstring) {
        Object object = new StringBuilder();
        ((StringBuilder)object).append("Phone: subId=");
        ((StringBuilder)object).append(this.getSubId());
        printWriter.println(((StringBuilder)object).toString());
        object = new StringBuilder();
        ((StringBuilder)object).append(" mPhoneId=");
        ((StringBuilder)object).append(this.mPhoneId);
        printWriter.println(((StringBuilder)object).toString());
        object = new StringBuilder();
        ((StringBuilder)object).append(" mCi=");
        ((StringBuilder)object).append(this.mCi);
        printWriter.println(((StringBuilder)object).toString());
        object = new StringBuilder();
        ((StringBuilder)object).append(" mDnsCheckDisabled=");
        ((StringBuilder)object).append(this.mDnsCheckDisabled);
        printWriter.println(((StringBuilder)object).toString());
        object = new StringBuilder();
        ((StringBuilder)object).append(" mDoesRilSendMultipleCallRing=");
        ((StringBuilder)object).append(this.mDoesRilSendMultipleCallRing);
        printWriter.println(((StringBuilder)object).toString());
        object = new StringBuilder();
        ((StringBuilder)object).append(" mCallRingContinueToken=");
        ((StringBuilder)object).append(this.mCallRingContinueToken);
        printWriter.println(((StringBuilder)object).toString());
        object = new StringBuilder();
        ((StringBuilder)object).append(" mCallRingDelay=");
        ((StringBuilder)object).append(this.mCallRingDelay);
        printWriter.println(((StringBuilder)object).toString());
        object = new StringBuilder();
        ((StringBuilder)object).append(" mIsVoiceCapable=");
        ((StringBuilder)object).append(this.mIsVoiceCapable);
        printWriter.println(((StringBuilder)object).toString());
        object = new StringBuilder();
        ((StringBuilder)object).append(" mIccRecords=");
        ((StringBuilder)object).append(this.mIccRecords.get());
        printWriter.println(((StringBuilder)object).toString());
        object = new StringBuilder();
        ((StringBuilder)object).append(" mUiccApplication=");
        ((StringBuilder)object).append(this.mUiccApplication.get());
        printWriter.println(((StringBuilder)object).toString());
        object = new StringBuilder();
        ((StringBuilder)object).append(" mSmsStorageMonitor=");
        ((StringBuilder)object).append((Object)this.mSmsStorageMonitor);
        printWriter.println(((StringBuilder)object).toString());
        object = new StringBuilder();
        ((StringBuilder)object).append(" mSmsUsageMonitor=");
        ((StringBuilder)object).append(this.mSmsUsageMonitor);
        printWriter.println(((StringBuilder)object).toString());
        printWriter.flush();
        object = new StringBuilder();
        ((StringBuilder)object).append(" mLooper=");
        ((StringBuilder)object).append((Object)this.mLooper);
        printWriter.println(((StringBuilder)object).toString());
        object = new StringBuilder();
        ((StringBuilder)object).append(" mContext=");
        ((StringBuilder)object).append((Object)this.mContext);
        printWriter.println(((StringBuilder)object).toString());
        object = new StringBuilder();
        ((StringBuilder)object).append(" mNotifier=");
        ((StringBuilder)object).append(this.mNotifier);
        printWriter.println(((StringBuilder)object).toString());
        object = new StringBuilder();
        ((StringBuilder)object).append(" mSimulatedRadioControl=");
        ((StringBuilder)object).append(this.mSimulatedRadioControl);
        printWriter.println(((StringBuilder)object).toString());
        object = new StringBuilder();
        ((StringBuilder)object).append(" mUnitTestMode=");
        ((StringBuilder)object).append(this.mUnitTestMode);
        printWriter.println(((StringBuilder)object).toString());
        object = new StringBuilder();
        ((StringBuilder)object).append(" isDnsCheckDisabled()=");
        ((StringBuilder)object).append(this.isDnsCheckDisabled());
        printWriter.println(((StringBuilder)object).toString());
        object = new StringBuilder();
        ((StringBuilder)object).append(" getUnitTestMode()=");
        ((StringBuilder)object).append(this.getUnitTestMode());
        printWriter.println(((StringBuilder)object).toString());
        object = new StringBuilder();
        ((StringBuilder)object).append(" getState()=");
        ((StringBuilder)object).append((Object)this.getState());
        printWriter.println(((StringBuilder)object).toString());
        object = new StringBuilder();
        ((StringBuilder)object).append(" getIccSerialNumber()=");
        ((StringBuilder)object).append(this.getIccSerialNumber());
        printWriter.println(((StringBuilder)object).toString());
        object = new StringBuilder();
        ((StringBuilder)object).append(" getIccRecordsLoaded()=");
        ((StringBuilder)object).append(this.getIccRecordsLoaded());
        printWriter.println(((StringBuilder)object).toString());
        object = new StringBuilder();
        ((StringBuilder)object).append(" getMessageWaitingIndicator()=");
        ((StringBuilder)object).append(this.getMessageWaitingIndicator());
        printWriter.println(((StringBuilder)object).toString());
        object = new StringBuilder();
        ((StringBuilder)object).append(" getCallForwardingIndicator()=");
        ((StringBuilder)object).append(this.getCallForwardingIndicator());
        printWriter.println(((StringBuilder)object).toString());
        object = new StringBuilder();
        ((StringBuilder)object).append(" isInEmergencyCall()=");
        ((StringBuilder)object).append(this.isInEmergencyCall());
        printWriter.println(((StringBuilder)object).toString());
        printWriter.flush();
        object = new StringBuilder();
        ((StringBuilder)object).append(" isInEcm()=");
        ((StringBuilder)object).append(this.isInEcm());
        printWriter.println(((StringBuilder)object).toString());
        object = new StringBuilder();
        ((StringBuilder)object).append(" getPhoneName()=");
        ((StringBuilder)object).append(this.getPhoneName());
        printWriter.println(((StringBuilder)object).toString());
        object = new StringBuilder();
        ((StringBuilder)object).append(" getPhoneType()=");
        ((StringBuilder)object).append(this.getPhoneType());
        printWriter.println(((StringBuilder)object).toString());
        object = new StringBuilder();
        ((StringBuilder)object).append(" getVoiceMessageCount()=");
        ((StringBuilder)object).append(this.getVoiceMessageCount());
        printWriter.println(((StringBuilder)object).toString());
        object = new StringBuilder();
        ((StringBuilder)object).append(" getActiveApnTypes()=");
        ((StringBuilder)object).append(this.getActiveApnTypes());
        printWriter.println(((StringBuilder)object).toString());
        object = new StringBuilder();
        ((StringBuilder)object).append(" needsOtaServiceProvisioning=");
        ((StringBuilder)object).append(this.needsOtaServiceProvisioning());
        printWriter.println(((StringBuilder)object).toString());
        object = new StringBuilder();
        ((StringBuilder)object).append(" isInEmergencySmsMode=");
        ((StringBuilder)object).append(this.isInEmergencySmsMode());
        printWriter.println(((StringBuilder)object).toString());
        printWriter.flush();
        printWriter.println("++++++++++++++++++++++++++++++++");
        object = this.mImsPhone;
        if (object != null) {
            try {
                ((Phone)object).dump(fileDescriptor, printWriter, arrstring);
            }
            catch (Exception exception) {
                exception.printStackTrace();
            }
            printWriter.flush();
            printWriter.println("++++++++++++++++++++++++++++++++");
        }
        if ((object = this.mTransportManager) != null) {
            object = ((TransportManager)((Object)object)).getAvailableTransports();
            int n = ((Object)object).length;
            for (int i = 0; i < n; ++i) {
                Object object2 = object[i];
                if (this.getDcTracker((int)object2) == null) continue;
                this.getDcTracker((int)object2).dump(fileDescriptor, printWriter, arrstring);
                printWriter.flush();
                printWriter.println("++++++++++++++++++++++++++++++++");
            }
        }
        if (this.getServiceStateTracker() != null) {
            try {
                this.getServiceStateTracker().dump(fileDescriptor, printWriter, arrstring);
            }
            catch (Exception exception) {
                exception.printStackTrace();
            }
            printWriter.flush();
            printWriter.println("++++++++++++++++++++++++++++++++");
        }
        if (this.getEmergencyNumberTracker() != null) {
            try {
                this.getEmergencyNumberTracker().dump(fileDescriptor, printWriter, arrstring);
            }
            catch (Exception exception) {
                exception.printStackTrace();
            }
            printWriter.flush();
            printWriter.println("++++++++++++++++++++++++++++++++");
        }
        if ((object = this.mCarrierResolver) != null) {
            try {
                ((CarrierResolver)((Object)object)).dump(fileDescriptor, printWriter, arrstring);
            }
            catch (Exception exception) {
                exception.printStackTrace();
            }
            printWriter.flush();
            printWriter.println("++++++++++++++++++++++++++++++++");
        }
        if ((object = this.mCarrierActionAgent) != null) {
            try {
                ((CarrierActionAgent)((Object)object)).dump(fileDescriptor, printWriter, arrstring);
            }
            catch (Exception exception) {
                exception.printStackTrace();
            }
            printWriter.flush();
            printWriter.println("++++++++++++++++++++++++++++++++");
        }
        if ((object = this.mCarrierSignalAgent) != null) {
            try {
                ((CarrierSignalAgent)((Object)object)).dump(fileDescriptor, printWriter, arrstring);
            }
            catch (Exception exception) {
                exception.printStackTrace();
            }
            printWriter.flush();
            printWriter.println("++++++++++++++++++++++++++++++++");
        }
        if (this.getCallTracker() != null) {
            try {
                this.getCallTracker().dump(fileDescriptor, printWriter, arrstring);
            }
            catch (Exception exception) {
                exception.printStackTrace();
            }
            printWriter.flush();
            printWriter.println("++++++++++++++++++++++++++++++++");
        }
        if ((object = this.mSimActivationTracker) != null) {
            try {
                ((SimActivationTracker)object).dump(fileDescriptor, printWriter, arrstring);
            }
            catch (Exception exception) {
                exception.printStackTrace();
            }
            printWriter.flush();
            printWriter.println("++++++++++++++++++++++++++++++++");
        }
        if (this.mDeviceStateMonitor != null) {
            printWriter.println("DeviceStateMonitor:");
            this.mDeviceStateMonitor.dump(fileDescriptor, printWriter, arrstring);
            printWriter.println("++++++++++++++++++++++++++++++++");
        }
        if ((object = this.mTransportManager) != null) {
            ((TransportManager)((Object)object)).dump(fileDescriptor, printWriter, arrstring);
        }
        if ((object = this.mCi) != null && object instanceof RIL) {
            try {
                ((RIL)object).dump(fileDescriptor, printWriter, arrstring);
            }
            catch (Exception exception) {
                exception.printStackTrace();
            }
            printWriter.flush();
            printWriter.println("++++++++++++++++++++++++++++++++");
        }
        printWriter.println("Phone Local Log: ");
        object = this.mLocalLog;
        if (object != null) {
            try {
                object.dump(fileDescriptor, printWriter, arrstring);
            }
            catch (Exception exception) {
                exception.printStackTrace();
            }
            printWriter.flush();
            printWriter.println("++++++++++++++++++++++++++++++++");
        }
    }

    public void enableEnhancedVoicePrivacy(boolean bl, Message message) {
    }

    @UnsupportedAppUsage
    public void exitEmergencyCallbackMode() {
    }

    public String getActionAttached() {
        return this.mActionAttached;
    }

    public String getActionDetached() {
        return this.mActionDetached;
    }

    public String getActiveApnHost(String string) {
        int n;
        TransportManager transportManager = this.mTransportManager;
        if (transportManager != null && this.getDcTracker(n = transportManager.getCurrentTransport(ApnSetting.getApnTypesBitmaskFromString((String)string))) != null) {
            return this.getDcTracker(n).getActiveApnString(string);
        }
        return null;
    }

    @UnsupportedAppUsage
    public String[] getActiveApnTypes() {
        if (this.mTransportManager != null) {
            ArrayList<String> arrayList = new ArrayList<String>();
            for (int n : this.mTransportManager.getAvailableTransports()) {
                if (this.getDcTracker(n) == null) continue;
                arrayList.addAll(Arrays.asList(this.getDcTracker(n).getActiveApnTypes()));
            }
            return arrayList.toArray(new String[arrayList.size()]);
        }
        return null;
    }

    public List<CellInfo> getAllCellInfo() {
        return this.getServiceStateTracker().getAllCellInfo();
    }

    public void getAllowedCarriers(Message message, WorkSource workSource) {
        this.mCi.getAllowedCarriers(message, workSource);
    }

    public AppSmsManager getAppSmsManager() {
        return this.mAppSmsManager;
    }

    public boolean getCallForwardingIndicator() {
        int n = this.getPhoneType();
        boolean bl = false;
        if (n == 2) {
            Rlog.e((String)LOG_TAG, (String)"getCallForwardingIndicator: not possible in CDMA");
            return false;
        }
        Object object = this.mIccRecords.get();
        n = -1;
        if (object != null) {
            n = ((IccRecords)object).getVoiceCallForwardingFlag();
        }
        int n2 = n;
        if (n == -1) {
            n2 = this.getCallForwardingIndicatorFromSharedPref();
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("getCallForwardingIndicator: iccForwardingFlag=");
        object = object != null ? Integer.valueOf(((IccRecords)object).getVoiceCallForwardingFlag()) : "null";
        stringBuilder.append(object);
        stringBuilder.append(", sharedPrefFlag=");
        stringBuilder.append(this.getCallForwardingIndicatorFromSharedPref());
        Rlog.v((String)LOG_TAG, (String)stringBuilder.toString());
        if (n2 == 1) {
            bl = true;
        }
        return bl;
    }

    @UnsupportedAppUsage
    public CallTracker getCallTracker() {
        return null;
    }

    public CarrierActionAgent getCarrierActionAgent() {
        return this.mCarrierActionAgent;
    }

    public int getCarrierId() {
        return -1;
    }

    public int getCarrierIdListVersion() {
        return -1;
    }

    @Override
    public ImsiEncryptionInfo getCarrierInfoForImsiEncryption(int n) {
        return null;
    }

    public String getCarrierName() {
        return null;
    }

    public CarrierSignalAgent getCarrierSignalAgent() {
        return this.mCarrierSignalAgent;
    }

    public int getCdmaEriIconIndex() {
        return -1;
    }

    public int getCdmaEriIconMode() {
        return -1;
    }

    public String getCdmaEriText() {
        return "GSM nw, no ERI";
    }

    public String getCdmaMin() {
        return null;
    }

    public String getCdmaPrlVersion() {
        return null;
    }

    @UnsupportedAppUsage
    public CellLocation getCellLocation() {
        return this.getServiceStateTracker().getCellLocation();
    }

    public void getCellLocation(WorkSource workSource, Message message) {
        this.getServiceStateTracker().requestCellLocation(workSource, message);
    }

    public List<ClientRequestStats> getClientRequestStats() {
        return this.mCi.getClientRequestStats();
    }

    @UnsupportedAppUsage
    public Context getContext() {
        return this.mContext;
    }

    public Uri[] getCurrentSubscriberUris() {
        return null;
    }

    public IccCardApplicationStatus.AppType getCurrentUiccAppType() {
        UiccCardApplication uiccCardApplication = this.mUiccApplication.get();
        if (uiccCardApplication != null) {
            return uiccCardApplication.getType();
        }
        return IccCardApplicationStatus.AppType.APPTYPE_UNKNOWN;
    }

    public int getDataActivationState() {
        return this.mSimActivationTracker.getDataActivationState();
    }

    @UnsupportedAppUsage
    public PhoneConstants.DataState getDataConnectionState() {
        return this.getDataConnectionState("default");
    }

    @Override
    public PhoneConstants.DataState getDataConnectionState(String string) {
        return PhoneConstants.DataState.DISCONNECTED;
    }

    public DataEnabledSettings getDataEnabledSettings() {
        return this.mDataEnabledSettings;
    }

    public DcTracker getDcTracker(int n) {
        return (DcTracker)((Object)this.mDcTrackers.get(n));
    }

    public Phone getDefaultPhone() {
        return this;
    }

    public EmergencyNumberTracker getEmergencyNumberTracker() {
        return null;
    }

    public void getEnhancedVoicePrivacy(Message message) {
    }

    public String getFullIccSerialNumber() {
        Object object = this.mIccRecords.get();
        object = object != null ? ((IccRecords)object).getFullIccId() : null;
        return object;
    }

    public HalVersion getHalVersion() {
        CommandsInterface commandsInterface = this.mCi;
        if (commandsInterface != null && commandsInterface instanceof RIL) {
            return ((RIL)commandsInterface).getHalVersion();
        }
        return RIL.RADIO_HAL_VERSION_UNKNOWN;
    }

    public Handler getHandler() {
        return this;
    }

    public ArrayList<Connection> getHandoverConnection() {
        return null;
    }

    @UnsupportedAppUsage
    public IccCard getIccCard() {
        return null;
    }

    @UnsupportedAppUsage
    public IccFileHandler getIccFileHandler() {
        Object object = this.mUiccApplication.get();
        if (object == null) {
            Rlog.d((String)LOG_TAG, (String)"getIccFileHandler: uiccApplication == null, return null");
            object = null;
        } else {
            object = ((UiccCardApplication)object).getIccFileHandler();
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("getIccFileHandler: fh=");
        stringBuilder.append(object);
        Rlog.d((String)LOG_TAG, (String)stringBuilder.toString());
        return object;
    }

    public IccRecords getIccRecords() {
        return this.mIccRecords.get();
    }

    public boolean getIccRecordsLoaded() {
        IccRecords iccRecords = this.mIccRecords.get();
        boolean bl = iccRecords != null ? iccRecords.getRecordsLoaded() : false;
        return bl;
    }

    @UnsupportedAppUsage
    public String getIccSerialNumber() {
        Object object = this.mIccRecords.get();
        object = object != null ? ((IccRecords)object).getIccId() : null;
        return object;
    }

    @UnsupportedAppUsage
    public IccSmsInterfaceManager getIccSmsInterfaceManager() {
        return null;
    }

    @UnsupportedAppUsage
    public Phone getImsPhone() {
        return this.mImsPhone;
    }

    public int getImsRegistrationTech() {
        Object object = this.mImsPhone;
        int n = -1;
        if (object != null) {
            n = ((Phone)object).getImsRegistrationTech();
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("getImsRegistrationTechnology =");
        ((StringBuilder)object).append(n);
        Rlog.d((String)LOG_TAG, (String)((StringBuilder)object).toString());
        return n;
    }

    @UnsupportedAppUsage
    public IsimRecords getIsimRecords() {
        Rlog.e((String)LOG_TAG, (String)"getIsimRecords() is only supported on LTE devices");
        return null;
    }

    public int getLceStatus() {
        return this.mLceStatus;
    }

    public LinkProperties getLinkProperties(String string) {
        int n;
        TransportManager transportManager = this.mTransportManager;
        if (transportManager != null && this.getDcTracker(n = transportManager.getCurrentTransport(ApnSetting.getApnTypesBitmaskFromString((String)string))) != null) {
            return this.getDcTracker(n).getLinkProperties(string);
        }
        return null;
    }

    public Locale getLocaleFromSimAndCarrierPrefs() {
        IccRecords iccRecords = this.mIccRecords.get();
        if (iccRecords != null && iccRecords.getSimLanguage() != null) {
            return new Locale(iccRecords.getSimLanguage());
        }
        return Phone.getLocaleFromCarrierProperties(this.mContext);
    }

    public int getLteOnCdmaMode() {
        return this.mCi.getLteOnCdmaMode();
    }

    public int getMNOCarrierId() {
        return -1;
    }

    public boolean getMessageWaitingIndicator() {
        boolean bl = this.mVmCount != 0;
        return bl;
    }

    public void getModemActivityInfo(Message message, WorkSource workSource) {
        this.mCi.getModemActivityInfo(message, workSource);
    }

    public String getModemUuId() {
        Object object = this.getRadioCapability();
        object = object == null ? "" : ((RadioCapability)object).getLogicalModemUuid();
        return object;
    }

    @UnsupportedAppUsage
    public String getMsisdn() {
        return null;
    }

    @UnsupportedAppUsage
    public String getNai() {
        return null;
    }

    public NetworkCapabilities getNetworkCapabilities(String string) {
        int n;
        TransportManager transportManager = this.mTransportManager;
        if (transportManager != null && this.getDcTracker(n = transportManager.getCurrentTransport(ApnSetting.getApnTypesBitmaskFromString((String)string))) != null) {
            return this.getDcTracker(n).getNetworkCapabilities(string);
        }
        return null;
    }

    public void getNetworkSelectionMode(Message message) {
        this.mCi.getNetworkSelectionMode(message);
    }

    public String[] getPcscfAddress(String string) {
        int n;
        TransportManager transportManager = this.mTransportManager;
        if (transportManager != null && this.getDcTracker(n = transportManager.getCurrentTransport(ApnSetting.getApnTypesBitmaskFromString((String)string))) != null) {
            return this.getDcTracker(n).getPcscfAddress(string);
        }
        return null;
    }

    @UnsupportedAppUsage
    public int getPhoneId() {
        return this.mPhoneId;
    }

    @UnsupportedAppUsage
    public String getPhoneName() {
        return this.mName;
    }

    @UnsupportedAppUsage
    public abstract int getPhoneType();

    public String getPlmn() {
        return null;
    }

    public Registrant getPostDialHandler() {
        return this.mPostDialHandler;
    }

    public void getPreferredNetworkType(Message message) {
        this.mCi.getPreferredNetworkType(message);
    }

    public int getRadioAccessFamily() {
        RadioCapability radioCapability = this.getRadioCapability();
        int n = radioCapability == null ? 0 : radioCapability.getRadioAccessFamily();
        return n;
    }

    public RadioCapability getRadioCapability() {
        return this.mRadioCapability.get();
    }

    public int getRadioPowerState() {
        return this.mCi.getRadioState();
    }

    @UnsupportedAppUsage
    public ServiceStateTracker getServiceStateTracker() {
        return null;
    }

    public SignalStrength getSignalStrength() {
        ServiceStateTracker serviceStateTracker = this.getServiceStateTracker();
        if (serviceStateTracker == null) {
            return new SignalStrength();
        }
        return serviceStateTracker.getSignalStrength();
    }

    public SimulatedRadioControl getSimulatedRadioControl() {
        return this.mSimulatedRadioControl;
    }

    @UnsupportedAppUsage
    public void getSmscAddress(Message message) {
        this.mCi.getSmscAddress(message);
    }

    public int getSpecificCarrierId() {
        return -1;
    }

    public String getSpecificCarrierName() {
        return null;
    }

    @UnsupportedAppUsage
    public abstract PhoneConstants.State getState();

    protected int getStoredVoiceMessageCount() {
        int n = 0;
        int n2 = 0;
        int n3 = 0;
        int n4 = this.getSubId();
        if (SubscriptionManager.isValidSubscriptionId((int)n4)) {
            Object object = PreferenceManager.getDefaultSharedPreferences((Context)this.mContext);
            CharSequence charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("vm_count_key");
            ((StringBuilder)charSequence).append(n4);
            n2 = object.getInt(((StringBuilder)charSequence).toString(), -2);
            if (n2 != -2) {
                n = n2;
                object = new StringBuilder();
                ((StringBuilder)object).append("getStoredVoiceMessageCount: from preference for subId ");
                ((StringBuilder)object).append(n4);
                ((StringBuilder)object).append("= ");
                ((StringBuilder)object).append(n);
                Rlog.d((String)"Phone", (String)((StringBuilder)object).toString());
            } else {
                charSequence = object.getString("vm_id_key", null);
                if (charSequence != null) {
                    String string = this.getSubscriberId();
                    if (string != null && string.equals(charSequence)) {
                        n = object.getInt("vm_count_key", 0);
                        this.setVoiceMessageCount(n);
                        charSequence = new StringBuilder();
                        ((StringBuilder)charSequence).append("getStoredVoiceMessageCount: from preference = ");
                        ((StringBuilder)charSequence).append(n);
                        Rlog.d((String)"Phone", (String)((StringBuilder)charSequence).toString());
                    } else {
                        Rlog.d((String)"Phone", (String)"getStoredVoiceMessageCount: returning 0 as count for matching subscriberId not found");
                        n = n3;
                    }
                    object = object.edit();
                    object.remove("vm_id_key");
                    object.remove("vm_count_key");
                    object.apply();
                }
            }
        } else {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("getStoredVoiceMessageCount: invalid subId ");
            stringBuilder.append(n4);
            Rlog.e((String)"Phone", (String)stringBuilder.toString());
            n = n2;
        }
        return n;
    }

    @UnsupportedAppUsage
    public int getSubId() {
        if (SubscriptionController.getInstance() == null) {
            Rlog.e((String)"Phone", (String)"SubscriptionController.getInstance = null! Returning default subId");
            return Integer.MAX_VALUE;
        }
        return SubscriptionController.getInstance().getSubIdUsingPhoneId(this.mPhoneId);
    }

    @UnsupportedAppUsage
    public String getSystemProperty(String string, String string2) {
        if (this.getUnitTestMode()) {
            return null;
        }
        return SystemProperties.get((String)string, (String)string2);
    }

    public TransportManager getTransportManager() {
        return null;
    }

    @UnsupportedAppUsage
    public UiccCard getUiccCard() {
        return this.mUiccController.getUiccCard(this.mPhoneId);
    }

    public boolean getUnitTestMode() {
        return this.mUnitTestMode;
    }

    public UsimServiceTable getUsimServiceTable() {
        Object object = this.mIccRecords.get();
        object = object != null ? ((IccRecords)object).getUsimServiceTable() : null;
        return object;
    }

    public int getVoiceActivationState() {
        return this.mSimActivationTracker.getVoiceActivationState();
    }

    public int getVoiceMessageCount() {
        return this.mVmCount;
    }

    public int getVoicePhoneServiceState() {
        Phone phone = this.mImsPhone;
        if (phone != null && phone.getServiceState().getState() == 0) {
            return 0;
        }
        return this.getServiceState().getState();
    }

    public NetworkStats getVtDataUsage(boolean bl) {
        Phone phone = this.mImsPhone;
        if (phone == null) {
            return null;
        }
        return phone.getVtDataUsage(bl);
    }

    protected void handleExitEmergencyCallbackMode() {
    }

    public void handleMessage(Message object) {
        block19 : {
            block26 : {
                block20 : {
                    block21 : {
                        block22 : {
                            block23 : {
                                block24 : {
                                    block25 : {
                                        int n = ((Message)object).what;
                                        if (n == 16 || n == 17) break block19;
                                        n = ((Message)object).what;
                                        if (n == 14) break block20;
                                        if (n == 15) break block21;
                                        if (n == 34) break block22;
                                        if (n == 52) break block23;
                                        if (n == 37) break block24;
                                        if (n == 38) break block25;
                                        switch (n) {
                                            default: {
                                                throw new RuntimeException("unexpected event not handled");
                                            }
                                            case 32: {
                                                Rlog.d((String)"Phone", (String)"Event EVENT_INITIATE_SILENT_REDIAL Received");
                                                object = (AsyncResult)((Message)object).obj;
                                                if (((AsyncResult)object).exception == null && ((AsyncResult)object).result != null) {
                                                    object = (String)((AsyncResult)object).result;
                                                    if (TextUtils.isEmpty((CharSequence)object)) {
                                                        return;
                                                    }
                                                    try {
                                                        PhoneInternalInterface.DialArgs.Builder builder = new PhoneInternalInterface.DialArgs.Builder();
                                                        this.dialInternal((String)object, builder.build());
                                                    }
                                                    catch (CallStateException callStateException) {
                                                        object = new StringBuilder();
                                                        ((StringBuilder)object).append("silent redial failed: ");
                                                        ((StringBuilder)object).append(callStateException);
                                                        Rlog.e((String)"Phone", (String)((StringBuilder)object).toString());
                                                    }
                                                    break;
                                                }
                                                break block26;
                                            }
                                            case 31: {
                                                object = (AsyncResult)((Message)object).obj;
                                                if (((AsyncResult)object).exception == null) {
                                                    this.handleSrvccStateChanged((int[])((AsyncResult)object).result);
                                                    break;
                                                }
                                                StringBuilder stringBuilder = new StringBuilder();
                                                stringBuilder.append("Srvcc exception: ");
                                                stringBuilder.append(((AsyncResult)object).exception);
                                                Rlog.e((String)"Phone", (String)stringBuilder.toString());
                                                break;
                                            }
                                            case 30: {
                                                this.onUpdateIccAvailability();
                                                break;
                                            }
                                        }
                                        break block26;
                                    }
                                    this.onCheckForNetworkSelectionModeAutomatic((Message)object);
                                    break block26;
                                }
                                object = (AsyncResult)((Message)object).obj;
                                if (((AsyncResult)object).exception != null) {
                                    StringBuilder stringBuilder = new StringBuilder();
                                    stringBuilder.append("config LCE service failed: ");
                                    stringBuilder.append(((AsyncResult)object).exception);
                                    Rlog.d((String)"Phone", (String)stringBuilder.toString());
                                } else {
                                    this.mLceStatus = (Integer)((ArrayList)((AsyncResult)object).result).get(0);
                                }
                                break block26;
                            }
                            if (this.areAllDataDisconnected()) {
                                this.mAllDataDisconnectedRegistrants.notifyRegistrants();
                            }
                            break block26;
                        }
                        object = (AsyncResult)((Message)object).obj;
                        if (((AsyncResult)object).exception == null) {
                            object = (AsyncResult)((AsyncResult)object).result;
                            this.mNotifier.notifyOemHookRawEventForSubscriber(this, (byte[])object);
                        } else {
                            StringBuilder stringBuilder = new StringBuilder();
                            stringBuilder.append("OEM hook raw exception: ");
                            stringBuilder.append(((AsyncResult)object).exception);
                            Rlog.e((String)"Phone", (String)stringBuilder.toString());
                        }
                        break block26;
                    }
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Event EVENT_CALL_RING_CONTINUE Received state=");
                    stringBuilder.append((Object)this.getState());
                    Rlog.d((String)"Phone", (String)stringBuilder.toString());
                    if (this.getState() == PhoneConstants.State.RINGING) {
                        this.sendIncomingCallRingNotification(((Message)object).arg1);
                    }
                    break block26;
                }
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Event EVENT_CALL_RING Received state=");
                stringBuilder.append((Object)this.getState());
                Rlog.d((String)"Phone", (String)stringBuilder.toString());
                if (((AsyncResult)object.obj).exception == null) {
                    object = this.getState();
                    if (!(this.mDoesRilSendMultipleCallRing || object != PhoneConstants.State.RINGING && object != PhoneConstants.State.IDLE)) {
                        ++this.mCallRingContinueToken;
                        this.sendIncomingCallRingNotification(this.mCallRingContinueToken);
                    } else {
                        this.notifyIncomingRing();
                    }
                }
            }
            return;
        }
        this.handleSetSelectNetwork((AsyncResult)((Message)object).obj);
    }

    public boolean hasMatchedTetherApnSetting() {
        if (this.getDcTracker(1) != null) {
            return this.getDcTracker(1).hasMatchedTetherApnSetting();
        }
        return false;
    }

    @Deprecated
    @UnsupportedAppUsage
    public void invokeOemRilRequestRaw(byte[] arrby, Message message) {
        this.mCi.invokeOemRilRequestRaw(arrby, message);
    }

    @Deprecated
    @UnsupportedAppUsage
    public void invokeOemRilRequestStrings(String[] arrstring, Message message) {
        this.mCi.invokeOemRilRequestStrings(arrstring, message);
    }

    public boolean isCdmaSubscriptionAppPresent() {
        return false;
    }

    public boolean isConcurrentVoiceAndDataAllowed() {
        ServiceStateTracker serviceStateTracker = this.getServiceStateTracker();
        boolean bl = serviceStateTracker == null ? false : serviceStateTracker.isConcurrentVoiceAndDataAllowed();
        return bl;
    }

    @UnsupportedAppUsage
    public boolean isCspPlmnEnabled() {
        return false;
    }

    public boolean isDataAllowed(int n) {
        return this.isDataAllowed(n, null);
    }

    public boolean isDataAllowed(int n, DataConnectionReasons dataConnectionReasons) {
        TransportManager transportManager = this.mTransportManager;
        if (transportManager != null && this.getDcTracker(n = transportManager.getCurrentTransport(n)) != null) {
            return this.getDcTracker(n).isDataAllowed(dataConnectionReasons);
        }
        return false;
    }

    public boolean isDnsCheckDisabled() {
        return this.mDnsCheckDisabled;
    }

    public boolean isImsAvailable() {
        Phone phone = this.mImsPhone;
        if (phone == null) {
            return false;
        }
        return phone.isImsAvailable();
    }

    public boolean isImsCapabilityAvailable(int n, int n2) {
        Object object = this.mImsPhone;
        boolean bl = false;
        if (object != null) {
            bl = ((Phone)object).isImsCapabilityAvailable(n, n2);
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("isImsRegistered =");
        ((StringBuilder)object).append(bl);
        Rlog.d((String)"Phone", (String)((StringBuilder)object).toString());
        return bl;
    }

    public boolean isImsRegistered() {
        Object object = this.mImsPhone;
        boolean bl = false;
        if (object != null) {
            bl = object.isImsRegistered();
        } else {
            object = this.getServiceStateTracker();
            if (object != null) {
                bl = object.isImsRegistered();
            }
        }
        object = new StringBuilder();
        object.append("isImsRegistered =");
        object.append(bl);
        Rlog.d((String)"Phone", (String)object.toString());
        return bl;
    }

    public boolean isImsUseEnabled() {
        ImsManager imsManager = ImsManager.getInstance((Context)this.mContext, (int)this.mPhoneId);
        boolean bl = imsManager.isVolteEnabledByPlatform() && imsManager.isEnhanced4gLteModeSettingEnabledByUser() || imsManager.isWfcEnabledByPlatform() && imsManager.isWfcEnabledByUser() && imsManager.isNonTtyOrTtyOnVolteEnabled();
        return bl;
    }

    public boolean isImsVideoCallOrConferencePresent() {
        boolean bl = false;
        Object object = this.mImsPhone;
        if (object != null) {
            bl = this.isVideoCallOrConference(object.getForegroundCall()) || this.isVideoCallOrConference(this.mImsPhone.getBackgroundCall()) || this.isVideoCallOrConference(this.mImsPhone.getRingingCall());
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("isImsVideoCallOrConferencePresent: ");
        ((StringBuilder)object).append(bl);
        Rlog.d((String)"Phone", (String)((StringBuilder)object).toString());
        return bl;
    }

    public boolean isInEcm() {
        return this.mIsPhoneInEcmState;
    }

    public boolean isInEmergencyCall() {
        return false;
    }

    public boolean isInEmergencySmsMode() {
        long l = this.mTimeLastEmergencySmsSentMs;
        boolean bl = false;
        if (l == -1L) {
            return false;
        }
        PersistableBundle persistableBundle = ((CarrierConfigManager)this.getContext().getSystemService("carrier_config")).getConfigForSubId(this.getSubId());
        if (persistableBundle == null) {
            return false;
        }
        int n = persistableBundle.getInt("emergency_sms_mode_timer_ms_int", 0);
        if (n == 0) {
            return false;
        }
        int n2 = n;
        if (n > 300000) {
            n2 = 300000;
        }
        if (SystemClock.elapsedRealtime() <= (long)n2 + l) {
            bl = true;
        }
        if (!bl) {
            this.mTimeLastEmergencySmsSentMs = -1L;
        } else {
            this.mLocalLog.log("isInEmergencySmsMode: queried while eSMS mode is active.");
        }
        return bl;
    }

    protected boolean isMatchGid(String string) {
        String string2 = this.getGroupIdLevel1();
        int n = string.length();
        return !TextUtils.isEmpty((CharSequence)string2) && string2.length() >= n && string2.substring(0, n).equalsIgnoreCase(string);
    }

    public boolean isMccMncMarkedAsNonRoaming(String string) {
        return this.getRoamingOverrideHelper("gsm_non_roaming_list_", string);
    }

    public boolean isMccMncMarkedAsRoaming(String string) {
        return this.getRoamingOverrideHelper("gsm_roaming_list_", string);
    }

    public boolean isMinInfoReady() {
        return false;
    }

    public boolean isOtaSpNumber(String string) {
        return false;
    }

    public boolean isRadioAvailable() {
        boolean bl = this.mCi.getRadioState() != 2;
        return bl;
    }

    public boolean isRadioOn() {
        int n = this.mCi.getRadioState();
        boolean bl = true;
        if (n != 1) {
            bl = false;
        }
        return bl;
    }

    public boolean isShuttingDown() {
        return this.getServiceStateTracker().isDeviceShuttingDown();
    }

    public boolean isSidMarkedAsNonRoaming(int n) {
        return this.getRoamingOverrideHelper("cdma_non_roaming_list_", Integer.toString(n));
    }

    public boolean isSidMarkedAsRoaming(int n) {
        return this.getRoamingOverrideHelper("cdma_roaming_list_", Integer.toString(n));
    }

    @UnsupportedAppUsage
    public boolean isUtEnabled() {
        Phone phone = this.mImsPhone;
        if (phone != null) {
            return phone.isUtEnabled();
        }
        return false;
    }

    @UnsupportedAppUsage
    public boolean isVideoEnabled() {
        Phone phone = this.mImsPhone;
        if (phone != null) {
            return phone.isVideoEnabled();
        }
        return false;
    }

    @UnsupportedAppUsage
    public boolean isVolteEnabled() {
        Object object = this.mImsPhone;
        boolean bl = false;
        if (object != null) {
            bl = ((Phone)object).isVolteEnabled();
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("isImsRegistered =");
        ((StringBuilder)object).append(bl);
        Rlog.d((String)"Phone", (String)((StringBuilder)object).toString());
        return bl;
    }

    @UnsupportedAppUsage
    public boolean isWifiCallingEnabled() {
        Object object = this.mImsPhone;
        boolean bl = false;
        if (object != null) {
            bl = ((Phone)object).isWifiCallingEnabled();
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("isWifiCallingEnabled =");
        ((StringBuilder)object).append(bl);
        Rlog.d((String)"Phone", (String)((StringBuilder)object).toString());
        return bl;
    }

    protected void migrate(RegistrantList registrantList, RegistrantList registrantList2) {
        registrantList2.removeCleared();
        int n = registrantList2.size();
        for (int i = 0; i < n; ++i) {
            Message message = ((Registrant)registrantList2.get(i)).messageForRegistrant();
            if (message != null) {
                if (message.obj == CallManager.getInstance().getRegistrantIdentifier()) continue;
                registrantList.add((Registrant)registrantList2.get(i));
                continue;
            }
            Rlog.d((String)"Phone", (String)"msg is null");
        }
    }

    protected void migrateFrom(Phone phone) {
        this.migrate(this.mHandoverRegistrants, phone.mHandoverRegistrants);
        this.migrate(this.mPreciseCallStateRegistrants, phone.mPreciseCallStateRegistrants);
        this.migrate(this.mNewRingingConnectionRegistrants, phone.mNewRingingConnectionRegistrants);
        this.migrate(this.mIncomingRingRegistrants, phone.mIncomingRingRegistrants);
        this.migrate(this.mDisconnectRegistrants, phone.mDisconnectRegistrants);
        this.migrate(this.mServiceStateRegistrants, phone.mServiceStateRegistrants);
        this.migrate(this.mMmiCompleteRegistrants, phone.mMmiCompleteRegistrants);
        this.migrate(this.mMmiRegistrants, phone.mMmiRegistrants);
        this.migrate(this.mUnknownConnectionRegistrants, phone.mUnknownConnectionRegistrants);
        this.migrate(this.mSuppServiceFailedRegistrants, phone.mSuppServiceFailedRegistrants);
        this.migrate(this.mCellInfoRegistrants, phone.mCellInfoRegistrants);
        if (phone.isInEmergencyCall()) {
            this.setIsInEmergencyCall();
        }
    }

    @UnsupportedAppUsage
    public boolean needsOtaServiceProvisioning() {
        return false;
    }

    public void notifyCallForwardingIndicator() {
    }

    public void notifyCellInfo(List<CellInfo> list) {
        AsyncResult asyncResult = new AsyncResult(null, list, null);
        this.mCellInfoRegistrants.notifyRegistrants(asyncResult);
        this.mNotifier.notifyCellInfo(this, list);
    }

    public void notifyDataActivationStateChanged(int n) {
        this.mNotifier.notifyDataActivationStateChanged(this, n);
    }

    public void notifyDataActivity() {
        this.mNotifier.notifyDataActivity(this);
    }

    public void notifyDataConnection() {
        String[] arrstring = this.getActiveApnTypes();
        if (arrstring != null) {
            for (String string : arrstring) {
                this.mNotifier.notifyDataConnection(this, string, this.getDataConnectionState(string));
            }
        }
    }

    public void notifyDataConnection(String string) {
        this.mNotifier.notifyDataConnection(this, string, this.getDataConnectionState(string));
    }

    public void notifyDataConnectionFailed(String string) {
        this.mNotifier.notifyDataConnectionFailed(this, string);
    }

    protected void notifyDisconnectP(Connection connection) {
        connection = new AsyncResult(null, (Object)connection, null);
        this.mDisconnectRegistrants.notifyRegistrants((AsyncResult)connection);
    }

    public void notifyEmergencyNumberList() {
        this.mNotifier.notifyEmergencyNumberList(this);
    }

    public void notifyForVideoCapabilityChanged(boolean bl) {
        this.mIsVideoCapable = bl;
        AsyncResult asyncResult = new AsyncResult(null, (Object)bl, null);
        this.mVideoCapabilityChangedRegistrants.notifyRegistrants(asyncResult);
    }

    public void notifyHandoverStateChanged(Connection connection) {
        connection = new AsyncResult(null, (Object)connection, null);
        this.mHandoverRegistrants.notifyRegistrants((AsyncResult)connection);
    }

    public void notifyNewRingingConnectionP(Connection connection) {
        if (!this.mIsVoiceCapable) {
            return;
        }
        connection = new AsyncResult(null, (Object)connection, null);
        this.mNewRingingConnectionRegistrants.notifyRegistrants((AsyncResult)connection);
    }

    @UnsupportedAppUsage
    public void notifyOtaspChanged(int n) {
        this.mNotifier.notifyOtaspChanged(this, n);
    }

    public void notifyPhysicalChannelConfiguration(List<PhysicalChannelConfig> list) {
        this.mNotifier.notifyPhysicalChannelConfiguration(this, list);
    }

    protected void notifyPreciseCallStateChangedP() {
        AsyncResult asyncResult = new AsyncResult(null, (Object)this, null);
        this.mPreciseCallStateRegistrants.notifyRegistrants(asyncResult);
        this.mNotifier.notifyPreciseCallState(this);
    }

    public void notifyPreciseDataConnectionFailed(String string, String string2, int n) {
        this.mNotifier.notifyPreciseDataConnectionFailed(this, string, string2, n);
    }

    protected void notifyServiceStateChangedP(ServiceState serviceState) {
        serviceState = new AsyncResult(null, (Object)serviceState, null);
        this.mServiceStateRegistrants.notifyRegistrants((AsyncResult)serviceState);
        this.mNotifier.notifyServiceState(this);
    }

    public void notifySignalStrength() {
        this.mNotifier.notifySignalStrength(this);
    }

    public void notifySmsSent(String string) {
        TelephonyManager telephonyManager = (TelephonyManager)this.getContext().getSystemService("phone");
        if (telephonyManager != null && telephonyManager.isEmergencyNumber(string)) {
            this.mLocalLog.log("Emergency SMS detected, recording time.");
            this.mTimeLastEmergencySmsSentMs = SystemClock.elapsedRealtime();
        }
    }

    public void notifySrvccState(Call.SrvccState srvccState) {
    }

    public void notifySrvccStateChanged(int n) {
        this.mNotifier.notifySrvccStateChanged(this, n);
    }

    public void notifyUnknownConnectionP(Connection connection) {
        this.mUnknownConnectionRegistrants.notifyResult((Object)connection);
    }

    public void notifyUserMobileDataStateChanged(boolean bl) {
        this.mNotifier.notifyUserMobileDataStateChanged(this, bl);
    }

    public void notifyVoiceActivationStateChanged(int n) {
        this.mNotifier.notifyVoiceActivationStateChanged(this, n);
    }

    public void nvReadItem(int n, Message message, WorkSource workSource) {
        this.mCi.nvReadItem(n, message, workSource);
    }

    public void nvWriteCdmaPrl(byte[] arrby, Message message) {
        this.mCi.nvWriteCdmaPrl(arrby, message);
    }

    public void nvWriteItem(int n, String string, Message message, WorkSource workSource) {
        this.mCi.nvWriteItem(n, string, message, workSource);
    }

    protected abstract void onUpdateIccAvailability();

    public void queryAvailableBandMode(Message message) {
        this.mCi.queryAvailableBandMode(message);
    }

    public void queryCdmaRoamingPreference(Message message) {
        this.mCi.queryCdmaRoamingPreference(message);
    }

    public void queryTTYMode(Message message) {
        this.mCi.queryTTYMode(message);
    }

    public void radioCapabilityUpdated(RadioCapability radioCapability) {
        this.mRadioCapability.set(radioCapability);
        if (SubscriptionManager.isValidSubscriptionId((int)this.getSubId())) {
            this.sendSubscriptionSettings(this.mContext.getResources().getBoolean(17891615) ^ true);
        }
    }

    public void rebootModem(Message message) {
        this.mCi.nvResetConfig(1, message);
    }

    public void registerFoT53ClirlInfo(Handler handler, int n, Object object) {
        this.mCi.registerFoT53ClirlInfo(handler, n, object);
    }

    public void registerForAllDataDisconnected(Handler arrn, int n) {
        this.mAllDataDisconnectedRegistrants.addUnique((Handler)arrn, n, null);
        arrn = this.mTransportManager;
        if (arrn != null) {
            for (int n2 : arrn.getAvailableTransports()) {
                if (this.getDcTracker(n2) == null || this.getDcTracker(n2).isDisconnected()) continue;
                this.getDcTracker(n2).registerForAllDataDisconnected(this, 52);
            }
        }
    }

    public void registerForCallWaiting(Handler handler, int n, Object object) {
    }

    public void registerForCdmaOtaStatusChange(Handler handler, int n, Object object) {
    }

    public void registerForCellInfo(Handler handler, int n, Object object) {
        this.mCellInfoRegistrants.add(handler, n, object);
    }

    @UnsupportedAppUsage
    public void registerForDisconnect(Handler handler, int n, Object object) {
        this.checkCorrectThread(handler);
        this.mDisconnectRegistrants.addUnique(handler, n, object);
    }

    public void registerForDisplayInfo(Handler handler, int n, Object object) {
        this.mCi.registerForDisplayInfo(handler, n, object);
    }

    @UnsupportedAppUsage
    public void registerForEcmTimerReset(Handler handler, int n, Object object) {
    }

    public void registerForEmergencyCallToggle(Handler handler, int n, Object object) {
        handler = new Registrant(handler, n, object);
        this.mEmergencyCallToggledRegistrants.add((Registrant)handler);
    }

    public void registerForHandoverStateChanged(Handler handler, int n, Object object) {
        this.checkCorrectThread(handler);
        this.mHandoverRegistrants.addUnique(handler, n, object);
    }

    public void registerForInCallVoicePrivacyOff(Handler handler, int n, Object object) {
        this.mCi.registerForInCallVoicePrivacyOff(handler, n, object);
    }

    public void registerForInCallVoicePrivacyOn(Handler handler, int n, Object object) {
        this.mCi.registerForInCallVoicePrivacyOn(handler, n, object);
    }

    @UnsupportedAppUsage
    public void registerForIncomingRing(Handler handler, int n, Object object) {
        this.checkCorrectThread(handler);
        this.mIncomingRingRegistrants.addUnique(handler, n, object);
    }

    public void registerForLineControlInfo(Handler handler, int n, Object object) {
        this.mCi.registerForLineControlInfo(handler, n, object);
    }

    @UnsupportedAppUsage
    public void registerForMmiComplete(Handler handler, int n, Object object) {
        this.checkCorrectThread(handler);
        this.mMmiCompleteRegistrants.addUnique(handler, n, object);
    }

    @UnsupportedAppUsage
    public void registerForMmiInitiate(Handler handler, int n, Object object) {
        this.checkCorrectThread(handler);
        this.mMmiRegistrants.addUnique(handler, n, object);
    }

    @UnsupportedAppUsage
    public void registerForNewRingingConnection(Handler handler, int n, Object object) {
        this.checkCorrectThread(handler);
        this.mNewRingingConnectionRegistrants.addUnique(handler, n, object);
    }

    public void registerForNumberInfo(Handler handler, int n, Object object) {
        this.mCi.registerForNumberInfo(handler, n, object);
    }

    public void registerForOnHoldTone(Handler handler, int n, Object object) {
    }

    @UnsupportedAppUsage
    public void registerForPreciseCallStateChanged(Handler handler, int n, Object object) {
        this.checkCorrectThread(handler);
        this.mPreciseCallStateRegistrants.addUnique(handler, n, object);
    }

    public void registerForRadioCapabilityChanged(Handler handler, int n, Object object) {
        this.mCi.registerForRadioCapabilityChanged(handler, n, object);
    }

    public void registerForRadioOffOrNotAvailable(Handler handler, int n, Object object) {
        this.mRadioOffOrNotAvailableRegistrants.addUnique(handler, n, object);
    }

    public void registerForRedirectedNumberInfo(Handler handler, int n, Object object) {
        this.mCi.registerForRedirectedNumberInfo(handler, n, object);
    }

    public void registerForResendIncallMute(Handler handler, int n, Object object) {
        this.mCi.registerForResendIncallMute(handler, n, object);
    }

    @UnsupportedAppUsage
    public void registerForRingbackTone(Handler handler, int n, Object object) {
        this.mCi.registerForRingbackTone(handler, n, object);
    }

    @UnsupportedAppUsage
    public void registerForServiceStateChanged(Handler handler, int n, Object object) {
        this.mServiceStateRegistrants.add(handler, n, object);
    }

    public void registerForSignalInfo(Handler handler, int n, Object object) {
        this.mCi.registerForSignalInfo(handler, n, object);
    }

    public void registerForSilentRedial(Handler handler, int n, Object object) {
    }

    @UnsupportedAppUsage
    public void registerForSimRecordsLoaded(Handler handler, int n, Object object) {
    }

    public void registerForSubscriptionInfoReady(Handler handler, int n, Object object) {
    }

    public void registerForSuppServiceFailed(Handler handler, int n, Object object) {
        this.checkCorrectThread(handler);
        this.mSuppServiceFailedRegistrants.addUnique(handler, n, object);
    }

    public void registerForT53AudioControlInfo(Handler handler, int n, Object object) {
        this.mCi.registerForT53AudioControlInfo(handler, n, object);
    }

    public void registerForTtyModeReceived(Handler handler, int n, Object object) {
    }

    @UnsupportedAppUsage
    public void registerForUnknownConnection(Handler handler, int n, Object object) {
        this.checkCorrectThread(handler);
        this.mUnknownConnectionRegistrants.addUnique(handler, n, object);
    }

    public void registerForVideoCapabilityChanged(Handler handler, int n, Object object) {
        this.checkCorrectThread(handler);
        this.mVideoCapabilityChangedRegistrants.addUnique(handler, n, object);
        this.notifyForVideoCapabilityChanged(this.mIsVideoCapable);
    }

    public void requestCellInfoUpdate(WorkSource workSource, Message message) {
        this.getServiceStateTracker().requestAllCellInfo(workSource, message);
    }

    @Override
    public void resetCarrierKeysForImsiEncryption() {
    }

    public void resetModemConfig(Message message) {
        this.mCi.nvResetConfig(3, message);
    }

    public void resolveSubscriptionCarrierId(String string) {
    }

    public void saveClirSetting(int n) {
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences((Context)this.getContext()).edit();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("clir_key");
        stringBuilder.append(this.getPhoneId());
        editor.putInt(stringBuilder.toString(), n);
        stringBuilder = new StringBuilder();
        stringBuilder.append("saveClirSetting: clir_key");
        stringBuilder.append(this.getPhoneId());
        stringBuilder.append("=");
        stringBuilder.append(n);
        Rlog.i((String)"Phone", (String)stringBuilder.toString());
        if (!editor.commit()) {
            Rlog.e((String)"Phone", (String)"Failed to commit CLIR preference");
        }
    }

    @UnsupportedAppUsage
    public void selectNetworkManually(OperatorInfo operatorInfo, boolean bl, Message message) {
        NetworkSelectMessage networkSelectMessage = new NetworkSelectMessage();
        networkSelectMessage.message = message;
        networkSelectMessage.operatorNumeric = operatorInfo.getOperatorNumeric();
        networkSelectMessage.operatorAlphaLong = operatorInfo.getOperatorAlphaLong();
        networkSelectMessage.operatorAlphaShort = operatorInfo.getOperatorAlphaShort();
        message = this.obtainMessage(16, (Object)networkSelectMessage);
        this.mCi.setNetworkSelectionModeManual(operatorInfo.getOperatorNumeric(), message);
        if (bl) {
            this.updateSavedNetworkOperator(networkSelectMessage);
        } else {
            this.clearSavedNetworkSelection();
        }
    }

    public void sendBurstDtmf(String string, int n, int n2, Message message) {
    }

    public void sendDialerSpecialCode(String string) {
        if (!TextUtils.isEmpty((CharSequence)string)) {
            BroadcastOptions broadcastOptions = BroadcastOptions.makeBasic();
            broadcastOptions.setBackgroundActivityStartsAllowed(true);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("android_secret_code://");
            stringBuilder.append(string);
            stringBuilder = new Intent("android.provider.Telephony.SECRET_CODE", Uri.parse((String)stringBuilder.toString()));
            stringBuilder.addFlags(16777216);
            this.mContext.sendBroadcast((Intent)stringBuilder, null, broadcastOptions.toBundle());
            stringBuilder = new StringBuilder();
            stringBuilder.append("android_secret_code://");
            stringBuilder.append(string);
            string = new Intent("android.telephony.action.SECRET_CODE", Uri.parse((String)stringBuilder.toString()));
            string.addFlags(16777216);
            this.mContext.sendBroadcast((Intent)string, null, broadcastOptions.toBundle());
        }
    }

    public abstract void sendEmergencyCallStateChange(boolean var1);

    public void sendSubscriptionSettings(boolean bl) {
        this.setPreferredNetworkType(PhoneFactory.calculatePreferredNetworkType(this.mContext, this.getSubId()), null);
        if (bl) {
            this.restoreSavedNetworkSelection(null);
        }
    }

    public void setAllowedCarriers(CarrierRestrictionRules carrierRestrictionRules, Message message, WorkSource workSource) {
        this.mCi.setAllowedCarriers(carrierRestrictionRules, message, workSource);
    }

    public void setBandMode(int n, Message message) {
        this.mCi.setBandMode(n, message);
    }

    public abstract void setBroadcastEmergencyCallStateChanges(boolean var1);

    @Override
    public void setCarrierInfoForImsiEncryption(ImsiEncryptionInfo imsiEncryptionInfo) {
    }

    public void setCarrierTestOverride(String string, String string2, String string3, String string4, String string5, String string6, String string7, String string8, String string9) {
    }

    public void setCdmaRoamingPreference(int n, Message message) {
        this.mCi.setCdmaRoamingPreference(n, message);
    }

    public void setCdmaSubscription(int n, Message message) {
        this.mCi.setCdmaSubscriptionSource(n, message);
    }

    public void setCellInfoListRate(int n, WorkSource workSource) {
        this.mCi.setCellInfoListRate(n, null, workSource);
    }

    public void setCellInfoMinInterval(int n) {
        this.getServiceStateTracker().setCellInfoMinInterval(n);
    }

    public void setDataActivationState(int n) {
        this.mSimActivationTracker.setDataActivationState(n);
    }

    public void setEchoSuppressionEnabled() {
    }

    public void setGlobalSystemProperty(String string, String string2) {
        if (this.getUnitTestMode()) {
            return;
        }
        TelephonyManager.setTelephonyProperty((String)string, (String)string2);
    }

    public void setImsRegistrationState(boolean bl) {
    }

    public void setIsInEcm(boolean bl) {
        this.setGlobalSystemProperty("ril.cdma.inecmmode", String.valueOf(bl));
        this.mIsPhoneInEcmState = bl;
    }

    protected void setIsInEmergencyCall() {
    }

    public void setLinkCapacityReportingCriteria(int[] arrn, int[] arrn2, int n) {
    }

    @UnsupportedAppUsage
    public void setNetworkSelectionModeAutomatic(Message message) {
        Rlog.d((String)"Phone", (String)"setNetworkSelectionModeAutomatic, querying current mode");
        Message message2 = this.obtainMessage(38);
        message2.obj = message;
        this.mCi.getNetworkSelectionMode(message2);
    }

    @UnsupportedAppUsage
    public void setOnEcbModeExitResponse(Handler handler, int n, Object object) {
    }

    @UnsupportedAppUsage
    public void setOnPostDialCharacter(Handler handler, int n, Object object) {
        this.mPostDialHandler = new Registrant(handler, n, object);
    }

    public boolean setOperatorBrandOverride(String string) {
        return false;
    }

    protected void setPhoneName(String string) {
        this.mName = string;
    }

    @UnsupportedAppUsage
    public void setPreferredNetworkType(int n, Message message) {
        int n2 = this.getRadioAccessFamily();
        int n3 = RadioAccessFamily.getRafFromNetworkType((int)n);
        if (n2 != 0 && n3 != 0) {
            int n4 = RadioAccessFamily.getNetworkTypeFromRaf((int)(n3 & n2));
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("setPreferredNetworkType: networkType = ");
            stringBuilder.append(n);
            stringBuilder.append(" modemRaf = ");
            stringBuilder.append(n2);
            stringBuilder.append(" rafFromType = ");
            stringBuilder.append(n3);
            stringBuilder.append(" filteredType = ");
            stringBuilder.append(n4);
            Rlog.d((String)"Phone", (String)stringBuilder.toString());
            this.mCi.setPreferredNetworkType(n4, message);
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("setPreferredNetworkType: Abort, unknown RAF: ");
        stringBuilder.append(n2);
        stringBuilder.append(" ");
        stringBuilder.append(n3);
        Rlog.d((String)"Phone", (String)stringBuilder.toString());
        if (message != null) {
            AsyncResult.forMessage((Message)message, null, (Throwable)new CommandException(CommandException.Error.GENERIC_FAILURE));
            message.sendToTarget();
        }
    }

    protected void setPreferredNetworkTypeIfSimLoaded() {
        if (SubscriptionManager.isValidSubscriptionId((int)this.getSubId())) {
            this.setPreferredNetworkType(PhoneFactory.calculatePreferredNetworkType(this.mContext, this.getSubId()), null);
        }
    }

    public void setRadioCapability(RadioCapability radioCapability, Message message) {
        this.mCi.setRadioCapability(radioCapability, message);
    }

    public void setRadioIndicationUpdateMode(int n, int n2) {
        DeviceStateMonitor deviceStateMonitor = this.mDeviceStateMonitor;
        if (deviceStateMonitor != null) {
            deviceStateMonitor.setIndicationUpdateMode(n, n2);
        }
    }

    public boolean setRoamingOverride(List<String> object, List<String> list, List<String> list2, List<String> list3) {
        String string = this.getIccSerialNumber();
        if (TextUtils.isEmpty((CharSequence)string)) {
            return false;
        }
        this.setRoamingOverrideHelper((List<String>)object, "gsm_roaming_list_", string);
        this.setRoamingOverrideHelper(list, "gsm_non_roaming_list_", string);
        this.setRoamingOverrideHelper(list2, "cdma_roaming_list_", string);
        this.setRoamingOverrideHelper(list3, "cdma_non_roaming_list_", string);
        object = this.getServiceStateTracker();
        if (object != null) {
            ((ServiceStateTracker)((Object)object)).pollState();
        }
        return true;
    }

    public void setSignalStrengthReportingCriteria(int[] arrn, int n) {
    }

    public void setSimPowerState(int n, WorkSource workSource) {
        this.mCi.setSimCardPower(n, null, workSource);
    }

    @UnsupportedAppUsage
    public void setSmscAddress(String string, Message message) {
        this.mCi.setSmscAddress(string, message);
    }

    public void setSystemProperty(String string, String string2) {
        if (this.getUnitTestMode()) {
            return;
        }
        TelephonyManager.setTelephonyProperty((int)this.mPhoneId, (String)string, (String)string2);
    }

    public void setTTYMode(int n, Message message) {
        this.mCi.setTTYMode(n, message);
    }

    public void setUiTTYMode(int n, Message message) {
        Rlog.d((String)"Phone", (String)"unexpected setUiTTYMode method call");
    }

    public void setVoiceActivationState(int n) {
        this.mSimActivationTracker.setVoiceActivationState(n);
    }

    public void setVoiceCallForwardingFlag(int n, boolean bl, String string) {
        this.setCallForwardingIndicatorInSharedPref(bl);
        IccRecords iccRecords = this.mIccRecords.get();
        if (iccRecords != null) {
            iccRecords.setVoiceCallForwardingFlag(n, bl, string);
        }
    }

    protected void setVoiceCallForwardingFlag(IccRecords iccRecords, int n, boolean bl, String string) {
        this.setCallForwardingIndicatorInSharedPref(bl);
        iccRecords.setVoiceCallForwardingFlag(n, bl, string);
    }

    public void setVoiceMessageCount(int n) {
        Object object;
        this.mVmCount = n;
        int n2 = this.getSubId();
        if (SubscriptionManager.isValidSubscriptionId((int)n2)) {
            object = new StringBuilder();
            ((StringBuilder)object).append("setVoiceMessageCount: Storing Voice Mail Count = ");
            ((StringBuilder)object).append(n);
            ((StringBuilder)object).append(" for mVmCountKey = ");
            ((StringBuilder)object).append("vm_count_key");
            ((StringBuilder)object).append(n2);
            ((StringBuilder)object).append(" in preferences.");
            Rlog.d((String)"Phone", (String)((StringBuilder)object).toString());
            SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences((Context)this.mContext).edit();
            object = new StringBuilder();
            ((StringBuilder)object).append("vm_count_key");
            ((StringBuilder)object).append(n2);
            editor.putInt(((StringBuilder)object).toString(), n);
            editor.apply();
        } else {
            object = new StringBuilder();
            ((StringBuilder)object).append("setVoiceMessageCount in sharedPreference: invalid subId ");
            ((StringBuilder)object).append(n2);
            Rlog.e((String)"Phone", (String)((StringBuilder)object).toString());
        }
        object = UiccController.getInstance().getIccRecords(this.mPhoneId, 1);
        if (object != null) {
            Rlog.d((String)"Phone", (String)"setVoiceMessageCount: updating SIM Records");
            ((IccRecords)object).setVoiceMessageWaiting(1, n);
        } else {
            Rlog.d((String)"Phone", (String)"setVoiceMessageCount: SIM Records not found");
        }
        this.notifyMessageWaitingIndicator();
    }

    public void setVoiceMessageWaiting(int n, int n2) {
        Rlog.e((String)"Phone", (String)"Error! This function should never be executed, inactive Phone.");
    }

    public void shutdownRadio() {
        this.getServiceStateTracker().requestShutdown();
    }

    public void startLceAfterRadioIsAvailable() {
        this.mCi.startLceService(200, true, this.obtainMessage(37));
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void startMonitoringImsService() {
        if (this.getPhoneType() == 3) {
            return;
        }
        Object object = lockForRadioTechnologyChange;
        synchronized (object) {
            IntentFilter intentFilter = new IntentFilter();
            ImsManager imsManager = ImsManager.getInstance((Context)this.mContext, (int)this.getPhoneId());
            if (imsManager != null && !imsManager.isDynamicBinding()) {
                intentFilter.addAction("com.android.ims.IMS_SERVICE_UP");
                intentFilter.addAction("com.android.ims.IMS_SERVICE_DOWN");
                this.mContext.registerReceiver(this.mImsIntentReceiver, intentFilter);
            }
            if (imsManager != null && (imsManager.isDynamicBinding() || imsManager.isServiceAvailable())) {
                this.mImsServiceReady = true;
                this.updateImsPhone();
            }
            return;
        }
    }

    public void startRingbackTone() {
    }

    public void stopRingbackTone() {
    }

    public boolean supportsConversionOfCdmaCallerIdMmiCodesWhileRoaming() {
        PersistableBundle persistableBundle = ((CarrierConfigManager)this.getContext().getSystemService("carrier_config")).getConfigForSubId(this.getSubId());
        if (persistableBundle != null) {
            return persistableBundle.getBoolean("convert_cdma_caller_id_mmi_codes_while_roaming_on_3gpp_bool", false);
        }
        return false;
    }

    public void unregisterForAllDataDisconnected(Handler handler) {
        this.mAllDataDisconnectedRegistrants.remove(handler);
    }

    public void unregisterForCallWaiting(Handler handler) {
    }

    public void unregisterForCdmaOtaStatusChange(Handler handler) {
    }

    public void unregisterForCellInfo(Handler handler) {
        this.mCellInfoRegistrants.remove(handler);
    }

    @UnsupportedAppUsage
    public void unregisterForDisconnect(Handler handler) {
        this.mDisconnectRegistrants.remove(handler);
    }

    public void unregisterForDisplayInfo(Handler handler) {
        this.mCi.unregisterForDisplayInfo(handler);
    }

    @UnsupportedAppUsage
    public void unregisterForEcmTimerReset(Handler handler) {
    }

    public void unregisterForEmergencyCallToggle(Handler handler) {
        this.mEmergencyCallToggledRegistrants.remove(handler);
    }

    public void unregisterForHandoverStateChanged(Handler handler) {
        this.mHandoverRegistrants.remove(handler);
    }

    public void unregisterForInCallVoicePrivacyOff(Handler handler) {
        this.mCi.unregisterForInCallVoicePrivacyOff(handler);
    }

    public void unregisterForInCallVoicePrivacyOn(Handler handler) {
        this.mCi.unregisterForInCallVoicePrivacyOn(handler);
    }

    @UnsupportedAppUsage
    public void unregisterForIncomingRing(Handler handler) {
        this.mIncomingRingRegistrants.remove(handler);
    }

    public void unregisterForLineControlInfo(Handler handler) {
        this.mCi.unregisterForLineControlInfo(handler);
    }

    @UnsupportedAppUsage
    public void unregisterForMmiComplete(Handler handler) {
        this.checkCorrectThread(handler);
        this.mMmiCompleteRegistrants.remove(handler);
    }

    @UnsupportedAppUsage
    public void unregisterForMmiInitiate(Handler handler) {
        this.mMmiRegistrants.remove(handler);
    }

    @UnsupportedAppUsage
    public void unregisterForNewRingingConnection(Handler handler) {
        this.mNewRingingConnectionRegistrants.remove(handler);
    }

    public void unregisterForNumberInfo(Handler handler) {
        this.mCi.unregisterForNumberInfo(handler);
    }

    public void unregisterForOnHoldTone(Handler handler) {
    }

    @UnsupportedAppUsage
    public void unregisterForPreciseCallStateChanged(Handler handler) {
        this.mPreciseCallStateRegistrants.remove(handler);
    }

    public void unregisterForRadioCapabilityChanged(Handler handler) {
        this.mCi.unregisterForRadioCapabilityChanged(this);
    }

    public void unregisterForRadioOffOrNotAvailable(Handler handler) {
        this.mRadioOffOrNotAvailableRegistrants.remove(handler);
    }

    public void unregisterForRedirectedNumberInfo(Handler handler) {
        this.mCi.unregisterForRedirectedNumberInfo(handler);
    }

    public void unregisterForResendIncallMute(Handler handler) {
        this.mCi.unregisterForResendIncallMute(handler);
    }

    @UnsupportedAppUsage
    public void unregisterForRingbackTone(Handler handler) {
        this.mCi.unregisterForRingbackTone(handler);
    }

    @UnsupportedAppUsage
    public void unregisterForServiceStateChanged(Handler handler) {
        this.mServiceStateRegistrants.remove(handler);
    }

    public void unregisterForSignalInfo(Handler handler) {
        this.mCi.unregisterForSignalInfo(handler);
    }

    public void unregisterForSilentRedial(Handler handler) {
    }

    @UnsupportedAppUsage
    public void unregisterForSimRecordsLoaded(Handler handler) {
    }

    public void unregisterForSubscriptionInfoReady(Handler handler) {
    }

    public void unregisterForSuppServiceFailed(Handler handler) {
        this.mSuppServiceFailedRegistrants.remove(handler);
    }

    public void unregisterForT53AudioControlInfo(Handler handler) {
        this.mCi.unregisterForT53AudioControlInfo(handler);
    }

    public void unregisterForT53ClirInfo(Handler handler) {
        this.mCi.unregisterForT53ClirInfo(handler);
    }

    public void unregisterForTtyModeReceived(Handler handler) {
    }

    @UnsupportedAppUsage
    public void unregisterForUnknownConnection(Handler handler) {
        this.mUnknownConnectionRegistrants.remove(handler);
    }

    public void unregisterForVideoCapabilityChanged(Handler handler) {
        this.mVideoCapabilityChangedRegistrants.remove(handler);
    }

    @UnsupportedAppUsage
    public void unsetOnEcbModeExitResponse(Handler handler) {
    }

    public boolean updateCurrentCarrierInProvider() {
        return false;
    }

    public void updateDataConnectionTracker() {
        int[] arrn = this.mTransportManager;
        if (arrn != null) {
            for (int n : arrn.getAvailableTransports()) {
                if (this.getDcTracker(n) == null) continue;
                this.getDcTracker(n).update();
            }
        }
    }

    public void updatePhoneObject(int n) {
    }

    public void updateVoiceMail() {
        Rlog.e((String)"Phone", (String)"updateVoiceMail() should be overridden");
    }

    private static class NetworkSelectMessage {
        public Message message;
        public String operatorAlphaLong;
        public String operatorAlphaShort;
        public String operatorNumeric;

        private NetworkSelectMessage() {
        }
    }

}

