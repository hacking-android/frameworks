/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.annotation.UnsupportedAppUsage
 *  android.app.AlarmManager
 *  android.app.Notification
 *  android.app.Notification$BigTextStyle
 *  android.app.Notification$Builder
 *  android.app.Notification$Style
 *  android.app.NotificationManager
 *  android.app.PendingIntent
 *  android.content.BroadcastReceiver
 *  android.content.ContentResolver
 *  android.content.ContentValues
 *  android.content.Context
 *  android.content.Intent
 *  android.content.IntentFilter
 *  android.content.SharedPreferences
 *  android.content.SharedPreferences$Editor
 *  android.content.res.Resources
 *  android.net.NetworkCapabilities
 *  android.net.Uri
 *  android.os.AsyncResult
 *  android.os.BaseBundle
 *  android.os.Build
 *  android.os.Bundle
 *  android.os.Handler
 *  android.os.Looper
 *  android.os.Message
 *  android.os.ParcelUuid
 *  android.os.PersistableBundle
 *  android.os.Registrant
 *  android.os.RegistrantList
 *  android.os.SystemClock
 *  android.os.SystemProperties
 *  android.os.UserHandle
 *  android.os.WorkSource
 *  android.preference.PreferenceManager
 *  android.provider.Settings
 *  android.provider.Settings$Global
 *  android.provider.Telephony
 *  android.provider.Telephony$ServiceStateTable
 *  android.telephony.CarrierConfigManager
 *  android.telephony.CellIdentity
 *  android.telephony.CellIdentityCdma
 *  android.telephony.CellIdentityGsm
 *  android.telephony.CellIdentityLte
 *  android.telephony.CellIdentityTdscdma
 *  android.telephony.CellIdentityWcdma
 *  android.telephony.CellInfo
 *  android.telephony.CellLocation
 *  android.telephony.DataSpecificRegistrationInfo
 *  android.telephony.NetworkRegistrationInfo
 *  android.telephony.NetworkRegistrationInfo$Builder
 *  android.telephony.PhysicalChannelConfig
 *  android.telephony.Rlog
 *  android.telephony.ServiceState
 *  android.telephony.SignalStrength
 *  android.telephony.SubscriptionInfo
 *  android.telephony.SubscriptionManager
 *  android.telephony.SubscriptionManager$OnSubscriptionsChangedListener
 *  android.telephony.TelephonyManager
 *  android.telephony.VoiceSpecificRegistrationInfo
 *  android.telephony.cdma.CdmaCellLocation
 *  android.telephony.gsm.GsmCellLocation
 *  android.text.TextUtils
 *  android.util.EventLog
 *  android.util.LocalLog
 *  android.util.Pair
 *  android.util.SparseArray
 *  android.util.SparseBooleanArray
 *  android.util.StatsLog
 *  android.util.TimestampedValue
 *  com.android.internal.annotations.VisibleForTesting
 *  com.android.internal.telephony.-$
 *  com.android.internal.telephony.-$$Lambda
 *  com.android.internal.telephony.-$$Lambda$UV1wDVoVlbcxpr8zevj_aMFtUGw
 *  com.android.internal.telephony.-$$Lambda$WWHOcG5P4-jgjzPPgLwm-wN15OM
 *  com.android.internal.util.ArrayUtils
 *  com.android.internal.util.IndentingPrintWriter
 */
package com.android.internal.telephony;

import android.annotation.UnsupportedAppUsage;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.net.NetworkCapabilities;
import android.net.Uri;
import android.os.AsyncResult;
import android.os.BaseBundle;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.ParcelUuid;
import android.os.PersistableBundle;
import android.os.Registrant;
import android.os.RegistrantList;
import android.os.SystemClock;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.os.WorkSource;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.provider.Telephony;
import android.telephony.CarrierConfigManager;
import android.telephony.CellIdentity;
import android.telephony.CellIdentityCdma;
import android.telephony.CellIdentityGsm;
import android.telephony.CellIdentityLte;
import android.telephony.CellIdentityTdscdma;
import android.telephony.CellIdentityWcdma;
import android.telephony.CellInfo;
import android.telephony.CellLocation;
import android.telephony.DataSpecificRegistrationInfo;
import android.telephony.NetworkRegistrationInfo;
import android.telephony.PhysicalChannelConfig;
import android.telephony.Rlog;
import android.telephony.ServiceState;
import android.telephony.SignalStrength;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.telephony.VoiceSpecificRegistrationInfo;
import android.telephony.cdma.CdmaCellLocation;
import android.telephony.gsm.GsmCellLocation;
import android.text.TextUtils;
import android.util.EventLog;
import android.util.LocalLog;
import android.util.Pair;
import android.util.SparseArray;
import android.util.SparseBooleanArray;
import android.util.StatsLog;
import android.util.TimestampedValue;
import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.telephony.-$;
import com.android.internal.telephony.CarrierActionAgent;
import com.android.internal.telephony.CarrierServiceStateTracker;
import com.android.internal.telephony.CommandException;
import com.android.internal.telephony.CommandsInterface;
import com.android.internal.telephony.GsmCdmaCall;
import com.android.internal.telephony.GsmCdmaCallTracker;
import com.android.internal.telephony.GsmCdmaPhone;
import com.android.internal.telephony.HbpcdUtils;
import com.android.internal.telephony.LocaleTracker;
import com.android.internal.telephony.MccTable;
import com.android.internal.telephony.NetworkRegistrationManager;
import com.android.internal.telephony.NitzData;
import com.android.internal.telephony.NitzStateMachine;
import com.android.internal.telephony.Phone;
import com.android.internal.telephony.ProxyController;
import com.android.internal.telephony.RatRatcheter;
import com.android.internal.telephony.RestrictedState;
import com.android.internal.telephony.SubscriptionController;
import com.android.internal.telephony.TelephonyComponentFactory;
import com.android.internal.telephony.TelephonyTester;
import com.android.internal.telephony.TimeZoneLookupHelper;
import com.android.internal.telephony._$$Lambda$UV1wDVoVlbcxpr8zevj_aMFtUGw;
import com.android.internal.telephony._$$Lambda$WWHOcG5P4_jgjzPPgLwm_wN15OM;
import com.android.internal.telephony.cdma.CdmaSubscriptionSourceManager;
import com.android.internal.telephony.cdma.EriManager;
import com.android.internal.telephony.cdnr.CarrierDisplayNameData;
import com.android.internal.telephony.cdnr.CarrierDisplayNameResolver;
import com.android.internal.telephony.dataconnection.DataConnection;
import com.android.internal.telephony.dataconnection.DcTracker;
import com.android.internal.telephony.dataconnection.TransportManager;
import com.android.internal.telephony.metrics.TelephonyMetrics;
import com.android.internal.telephony.uicc.IccCardApplicationStatus;
import com.android.internal.telephony.uicc.IccCardStatus;
import com.android.internal.telephony.uicc.IccRecords;
import com.android.internal.telephony.uicc.RuimRecords;
import com.android.internal.telephony.uicc.SIMRecords;
import com.android.internal.telephony.uicc.UiccCard;
import com.android.internal.telephony.uicc.UiccCardApplication;
import com.android.internal.telephony.uicc.UiccController;
import com.android.internal.telephony.uicc.UiccProfile;
import com.android.internal.util.ArrayUtils;
import com.android.internal.util.IndentingPrintWriter;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.io.Serializable;
import java.io.Writer;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.TimeZone;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.function.ToIntFunction;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class ServiceStateTracker
extends Handler {
    private static final String ACTION_RADIO_OFF = "android.intent.action.ACTION_RADIO_OFF";
    public static final int CARRIER_NAME_DISPLAY_BITMASK_SHOW_PLMN = 2;
    public static final int CARRIER_NAME_DISPLAY_BITMASK_SHOW_SPN = 1;
    private static final long CELL_INFO_LIST_QUERY_TIMEOUT = 2000L;
    public static final int CS_DISABLED = 1004;
    public static final int CS_EMERGENCY_ENABLED = 1006;
    public static final int CS_ENABLED = 1003;
    public static final int CS_NORMAL_ENABLED = 1005;
    public static final int CS_NOTIFICATION = 999;
    public static final int CS_REJECT_CAUSE_DISABLED = 2002;
    public static final int CS_REJECT_CAUSE_ENABLED = 2001;
    public static final int CS_REJECT_CAUSE_NOTIFICATION = 111;
    static final boolean DBG = true;
    public static final int DEFAULT_GPRS_CHECK_PERIOD_MILLIS = 60000;
    public static final String DEFAULT_MNC = "00";
    protected static final int EVENT_ALL_DATA_DISCONNECTED = 49;
    protected static final int EVENT_CARRIER_CONFIG_CHANGED = 57;
    protected static final int EVENT_CDMA_PRL_VERSION_CHANGED = 40;
    protected static final int EVENT_CDMA_SUBSCRIPTION_SOURCE_CHANGED = 39;
    protected static final int EVENT_CELL_LOCATION_RESPONSE = 56;
    protected static final int EVENT_CHANGE_IMS_STATE = 45;
    protected static final int EVENT_CHECK_REPORT_GPRS = 22;
    protected static final int EVENT_GET_CELL_INFO_LIST = 43;
    protected static final int EVENT_GET_LOC_DONE = 15;
    protected static final int EVENT_GET_PREFERRED_NETWORK_TYPE = 19;
    protected static final int EVENT_GET_SIGNAL_STRENGTH = 3;
    public static final int EVENT_ICC_CHANGED = 42;
    protected static final int EVENT_IMS_CAPABILITY_CHANGED = 48;
    protected static final int EVENT_IMS_SERVICE_STATE_CHANGED = 53;
    protected static final int EVENT_IMS_STATE_CHANGED = 46;
    protected static final int EVENT_IMS_STATE_DONE = 47;
    protected static final int EVENT_LOCATION_UPDATES_ENABLED = 18;
    protected static final int EVENT_NETWORK_STATE_CHANGED = 2;
    protected static final int EVENT_NITZ_TIME = 11;
    protected static final int EVENT_NV_READY = 35;
    protected static final int EVENT_OTA_PROVISION_STATUS_CHANGE = 37;
    protected static final int EVENT_PHONE_TYPE_SWITCHED = 50;
    protected static final int EVENT_PHYSICAL_CHANNEL_CONFIG = 55;
    protected static final int EVENT_POLL_SIGNAL_STRENGTH = 10;
    protected static final int EVENT_POLL_STATE_CDMA_SUBSCRIPTION = 34;
    protected static final int EVENT_POLL_STATE_CS_CELLULAR_REGISTRATION = 4;
    protected static final int EVENT_POLL_STATE_NETWORK_SELECTION_MODE = 14;
    protected static final int EVENT_POLL_STATE_OPERATOR = 7;
    protected static final int EVENT_POLL_STATE_PS_CELLULAR_REGISTRATION = 5;
    protected static final int EVENT_POLL_STATE_PS_IWLAN_REGISTRATION = 6;
    protected static final int EVENT_RADIO_ON = 41;
    protected static final int EVENT_RADIO_POWER_FROM_CARRIER = 51;
    protected static final int EVENT_RADIO_POWER_OFF_DONE = 54;
    protected static final int EVENT_RADIO_STATE_CHANGED = 1;
    protected static final int EVENT_RESET_PREFERRED_NETWORK_TYPE = 21;
    protected static final int EVENT_RESTRICTED_STATE_CHANGED = 23;
    protected static final int EVENT_RUIM_READY = 26;
    protected static final int EVENT_RUIM_RECORDS_LOADED = 27;
    protected static final int EVENT_SET_PREFERRED_NETWORK_TYPE = 20;
    protected static final int EVENT_SET_RADIO_POWER_OFF = 38;
    protected static final int EVENT_SIGNAL_STRENGTH_UPDATE = 12;
    protected static final int EVENT_SIM_READY = 17;
    protected static final int EVENT_SIM_RECORDS_LOADED = 16;
    protected static final int EVENT_UNSOL_CELL_INFO_LIST = 44;
    private static final int INVALID_LTE_EARFCN = -1;
    public static final String INVALID_MCC = "000";
    static final String LOG_TAG = "SST";
    private static final int MS_PER_HOUR = 3600000;
    private static final int POLL_PERIOD_MILLIS = 20000;
    private static final String PROP_FORCE_ROAMING = "telephony.test.forceRoaming";
    public static final int PS_DISABLED = 1002;
    public static final int PS_ENABLED = 1001;
    public static final int PS_NOTIFICATION = 888;
    protected static final String REGISTRATION_DENIED_AUTH = "Authentication Failure";
    protected static final String REGISTRATION_DENIED_GEN = "General";
    public static final String UNACTIVATED_MIN2_VALUE = "000000";
    public static final String UNACTIVATED_MIN_VALUE = "1111110111";
    private static final boolean VDBG = false;
    private boolean mAlarmSwitch = false;
    private final LocalLog mAttachLog = new LocalLog(10);
    protected SparseArray<RegistrantList> mAttachedRegistrants = new SparseArray();
    private CarrierServiceStateTracker mCSST;
    private RegistrantList mCdmaForSubscriptionInfoReadyRegistrants = new RegistrantList();
    private CdmaSubscriptionSourceManager mCdmaSSM;
    private CarrierDisplayNameResolver mCdnr;
    private final LocalLog mCdnrLogs = new LocalLog(64);
    private CellIdentity mCellIdentity;
    private int mCellInfoMinIntervalMs = 2000;
    @UnsupportedAppUsage
    private CommandsInterface mCi;
    @UnsupportedAppUsage
    private final ContentResolver mCr;
    @UnsupportedAppUsage
    private String mCurDataSpn = null;
    @UnsupportedAppUsage
    private String mCurPlmn = null;
    @UnsupportedAppUsage
    private boolean mCurShowPlmn = false;
    @UnsupportedAppUsage
    private boolean mCurShowSpn = false;
    @UnsupportedAppUsage
    private String mCurSpn = null;
    private String mCurrentCarrier = null;
    private int mCurrentOtaspMode = 0;
    private SparseArray<RegistrantList> mDataRegStateOrRatChangedRegistrants = new SparseArray();
    private boolean mDataRoaming = false;
    @UnsupportedAppUsage
    private RegistrantList mDataRoamingOffRegistrants = new RegistrantList();
    @UnsupportedAppUsage
    private RegistrantList mDataRoamingOnRegistrants = new RegistrantList();
    @UnsupportedAppUsage
    private int mDefaultRoamingIndicator;
    @UnsupportedAppUsage
    private boolean mDesiredPowerState;
    protected SparseArray<RegistrantList> mDetachedRegistrants = new SparseArray();
    @UnsupportedAppUsage
    private boolean mDeviceShuttingDown = false;
    private boolean mDontPollSignalStrength = false;
    private ArrayList<Pair<Integer, Integer>> mEarfcnPairListForRsrpBoost = null;
    @UnsupportedAppUsage
    private boolean mEmergencyOnly = false;
    private final EriManager mEriManager;
    private boolean mGsmRoaming = false;
    private HbpcdUtils mHbpcdUtils = null;
    private int[] mHomeNetworkId = null;
    private int[] mHomeSystemId = null;
    @UnsupportedAppUsage
    private IccRecords mIccRecords = null;
    private RegistrantList mImsCapabilityChangedRegistrants = new RegistrantList();
    private boolean mImsRegistered = false;
    private boolean mImsRegistrationOnOff = false;
    @UnsupportedAppUsage
    private BroadcastReceiver mIntentReceiver = new BroadcastReceiver(){

        public void onReceive(Context object, Intent intent) {
            if (intent.getAction().equals("android.telephony.action.CARRIER_CONFIG_CHANGED")) {
                if (intent.getExtras().getInt("android.telephony.extra.SLOT_INDEX") == ServiceStateTracker.this.mPhone.getPhoneId()) {
                    ServiceStateTracker.this.sendEmptyMessage(57);
                }
                return;
            }
            if (!ServiceStateTracker.this.mPhone.isPhoneTypeGsm()) {
                ServiceStateTracker serviceStateTracker = ServiceStateTracker.this;
                object = new StringBuilder();
                ((StringBuilder)object).append("Ignoring intent ");
                ((StringBuilder)object).append((Object)intent);
                ((StringBuilder)object).append(" received on CDMA phone");
                serviceStateTracker.loge(((StringBuilder)object).toString());
                return;
            }
            if (intent.getAction().equals("android.intent.action.LOCALE_CHANGED")) {
                ServiceStateTracker.this.updateSpnDisplay();
            } else if (intent.getAction().equals(ServiceStateTracker.ACTION_RADIO_OFF)) {
                ServiceStateTracker.this.mAlarmSwitch = false;
                ServiceStateTracker.this.powerOffRadioSafely();
            }
        }
    };
    private boolean mIsEriTextLoaded = false;
    private boolean mIsInPrl;
    private boolean mIsMinInfoReady = false;
    private boolean mIsPendingCellInfoRequest = false;
    private boolean mIsSimReady = false;
    @UnsupportedAppUsage
    private boolean mIsSubscriptionFromRuim = false;
    private List<CellInfo> mLastCellInfoList = null;
    private long mLastCellInfoReqTime;
    private List<PhysicalChannelConfig> mLastPhysicalChannelConfigList = null;
    private SignalStrength mLastSignalStrength = null;
    private final LocaleTracker mLocaleTracker;
    private int mLteRsrpBoost = 0;
    private final Object mLteRsrpBoostLock = new Object();
    @UnsupportedAppUsage
    private int mMaxDataCalls = 1;
    private String mMdn;
    private String mMin;
    @UnsupportedAppUsage
    private RegistrantList mNetworkAttachedRegistrants = new RegistrantList();
    private RegistrantList mNetworkDetachedRegistrants = new RegistrantList();
    private CellIdentity mNewCellIdentity;
    @UnsupportedAppUsage
    private int mNewMaxDataCalls = 1;
    @UnsupportedAppUsage
    private int mNewReasonDataDenied = -1;
    private int mNewRejectCode;
    @UnsupportedAppUsage
    private ServiceState mNewSS;
    private final NitzStateMachine mNitzState;
    private Notification mNotification;
    @UnsupportedAppUsage
    private final SstSubscriptionsChangedListener mOnSubscriptionsChangedListener = new SstSubscriptionsChangedListener();
    private Pattern mOperatorNameStringPattern;
    private List<Message> mPendingCellInfoRequests = new LinkedList<Message>();
    private boolean mPendingRadioPowerOffAfterDataOff = false;
    private int mPendingRadioPowerOffAfterDataOffTag = 0;
    @UnsupportedAppUsage
    private final GsmCdmaPhone mPhone;
    private final LocalLog mPhoneTypeLog = new LocalLog(10);
    @VisibleForTesting
    public int[] mPollingContext;
    private boolean mPowerOffDelayNeed = true;
    @UnsupportedAppUsage
    private int mPreferredNetworkType;
    private int mPrevSubId = -1;
    private String mPrlVersion;
    private RegistrantList mPsRestrictDisabledRegistrants = new RegistrantList();
    private RegistrantList mPsRestrictEnabledRegistrants = new RegistrantList();
    private boolean mRadioDisabledByCarrier = false;
    private PendingIntent mRadioOffIntent = null;
    private final LocalLog mRadioPowerLog = new LocalLog(20);
    private final LocalLog mRatLog = new LocalLog(20);
    private final RatRatcheter mRatRatcheter;
    @UnsupportedAppUsage
    private int mReasonDataDenied = -1;
    private final SparseArray<NetworkRegistrationManager> mRegStateManagers = new SparseArray();
    private String mRegistrationDeniedReason;
    private int mRegistrationState = -1;
    private int mRejectCode;
    @UnsupportedAppUsage
    private boolean mReportedGprsNoReg;
    public RestrictedState mRestrictedState;
    @UnsupportedAppUsage
    private int mRoamingIndicator;
    private final LocalLog mRoamingLog = new LocalLog(10);
    @UnsupportedAppUsage
    public ServiceState mSS;
    @UnsupportedAppUsage
    private SignalStrength mSignalStrength;
    @UnsupportedAppUsage
    private boolean mSpnUpdatePending = false;
    @UnsupportedAppUsage
    private boolean mStartedGprsRegCheck;
    @UnsupportedAppUsage
    @VisibleForTesting
    public int mSubId = -1;
    @UnsupportedAppUsage
    private SubscriptionController mSubscriptionController;
    @UnsupportedAppUsage
    private SubscriptionManager mSubscriptionManager;
    private final TransportManager mTransportManager;
    @UnsupportedAppUsage
    private UiccCardApplication mUiccApplcation = null;
    @UnsupportedAppUsage
    private UiccController mUiccController = null;
    private boolean mVoiceCapable;
    private RegistrantList mVoiceRegStateOrRatChangedRegistrants = new RegistrantList();
    @UnsupportedAppUsage
    private RegistrantList mVoiceRoamingOffRegistrants = new RegistrantList();
    @UnsupportedAppUsage
    private RegistrantList mVoiceRoamingOnRegistrants = new RegistrantList();
    private boolean mWantContinuousLocationUpdates;
    private boolean mWantSingleLocationUpdate;

    public ServiceStateTracker(GsmCdmaPhone gsmCdmaPhone, CommandsInterface localLog) {
        this.mNitzState = TelephonyComponentFactory.getInstance().inject(NitzStateMachine.class.getName()).makeNitzStateMachine(gsmCdmaPhone);
        this.mPhone = gsmCdmaPhone;
        this.mCi = localLog;
        this.mCdnr = new CarrierDisplayNameResolver(this.mPhone);
        this.mEriManager = TelephonyComponentFactory.getInstance().inject(EriManager.class.getName()).makeEriManager(this.mPhone, 0);
        this.mRatRatcheter = new RatRatcheter(this.mPhone);
        this.mVoiceCapable = this.mPhone.getContext().getResources().getBoolean(17891570);
        this.mUiccController = UiccController.getInstance();
        this.mUiccController.registerForIccChanged(this, 42, null);
        this.mCi.setOnSignalStrengthUpdate(this, 12, null);
        this.mCi.registerForCellInfoList(this, 44, null);
        this.mCi.registerForPhysicalChannelConfiguration(this, 55, null);
        this.mSubscriptionController = SubscriptionController.getInstance();
        this.mSubscriptionManager = SubscriptionManager.from((Context)gsmCdmaPhone.getContext());
        this.mSubscriptionManager.addOnSubscriptionsChangedListener((SubscriptionManager.OnSubscriptionsChangedListener)this.mOnSubscriptionsChangedListener);
        this.mRestrictedState = new RestrictedState();
        this.mTransportManager = this.mPhone.getTransportManager();
        for (int n : this.mTransportManager.getAvailableTransports()) {
            this.mRegStateManagers.append(n, (Object)new NetworkRegistrationManager(n, gsmCdmaPhone));
            ((NetworkRegistrationManager)((Object)this.mRegStateManagers.get(n))).registerForNetworkRegistrationInfoChanged(this, 2, null);
        }
        this.mLocaleTracker = TelephonyComponentFactory.getInstance().inject(LocaleTracker.class.getName()).makeLocaleTracker(this.mPhone, this.mNitzState, this.getLooper());
        this.mCi.registerForImsNetworkStateChanged(this, 46, null);
        this.mCi.registerForRadioStateChanged(this, 1, null);
        this.mCi.setOnNITZTime(this, 11, null);
        this.mCr = gsmCdmaPhone.getContext().getContentResolver();
        int n = Settings.Global.getInt((ContentResolver)this.mCr, (String)"airplane_mode_on", (int)0);
        int n2 = Settings.Global.getInt((ContentResolver)this.mCr, (String)"enable_cellular_on_boot", (int)1);
        boolean bl = n2 > 0 && n <= 0;
        this.mDesiredPowerState = bl;
        localLog = this.mRadioPowerLog;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("init : airplane mode = ");
        stringBuilder.append(n);
        stringBuilder.append(" enableCellularOnBoot = ");
        stringBuilder.append(n2);
        localLog.log(stringBuilder.toString());
        this.setSignalStrengthDefaultValues();
        this.mPhone.getCarrierActionAgent().registerForCarrierAction(1, this, 51, null, false);
        localLog = this.mPhone.getContext();
        stringBuilder = new IntentFilter();
        stringBuilder.addAction("android.intent.action.LOCALE_CHANGED");
        localLog.registerReceiver(this.mIntentReceiver, (IntentFilter)stringBuilder);
        stringBuilder = new IntentFilter();
        stringBuilder.addAction(ACTION_RADIO_OFF);
        localLog.registerReceiver(this.mIntentReceiver, (IntentFilter)stringBuilder);
        stringBuilder = new IntentFilter();
        stringBuilder.addAction("android.telephony.action.CARRIER_CONFIG_CHANGED");
        localLog.registerReceiver(this.mIntentReceiver, (IntentFilter)stringBuilder);
        this.mPhone.notifyOtaspChanged(0);
        this.mCi.setOnRestrictedStateChanged(this, 23, null);
        this.updatePhoneType();
        this.mCSST = new CarrierServiceStateTracker(gsmCdmaPhone, this);
        this.registerForNetworkAttached(this.mCSST, 101, null);
        this.registerForNetworkDetached(this.mCSST, 102, null);
        this.registerForDataConnectionAttached(1, this.mCSST, 103, null);
        this.registerForDataConnectionDetached(1, this.mCSST, 104, null);
        this.registerForImsCapabilityChanged(this.mCSST, 105, null);
    }

    private void cancelAllNotifications() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("cancelAllNotifications: mPrevSubId=");
        stringBuilder.append(this.mPrevSubId);
        this.log(stringBuilder.toString());
        stringBuilder = (NotificationManager)this.mPhone.getContext().getSystemService("notification");
        if (SubscriptionManager.isValidSubscriptionId((int)this.mPrevSubId)) {
            stringBuilder.cancel(Integer.toString(this.mPrevSubId), 888);
            stringBuilder.cancel(Integer.toString(this.mPrevSubId), 999);
            stringBuilder.cancel(Integer.toString(this.mPrevSubId), 111);
        }
    }

    private void combinePsRegistrationStates(ServiceState serviceState) {
        Object object = serviceState.getNetworkRegistrationInfo(2, 2);
        NetworkRegistrationInfo networkRegistrationInfo = serviceState.getNetworkRegistrationInfo(2, 1);
        boolean bl = this.mTransportManager.isAnyApnPreferredOnIwlan();
        serviceState.setIwlanPreferred(bl);
        if (object != null && object.getAccessNetworkTechnology() == 18 && object.getRegistrationState() == 1 && bl) {
            serviceState.setDataRegState(0);
        } else if (networkRegistrationInfo != null) {
            serviceState.setDataRegState(this.regCodeToServiceState(networkRegistrationInfo.getRegistrationState()));
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("combinePsRegistrationStates: ");
        ((StringBuilder)object).append((Object)serviceState);
        this.log(((StringBuilder)object).toString());
    }

    private boolean containsEarfcnInEarfcnRange(ArrayList<Pair<Integer, Integer>> object, int n) {
        if (object != null) {
            object = ((ArrayList)object).iterator();
            while (object.hasNext()) {
                Pair pair = (Pair)object.next();
                if (n < (Integer)pair.first || n > (Integer)pair.second) continue;
                return true;
            }
        }
        return false;
    }

    private boolean currentMccEqualsSimMcc(ServiceState object) {
        boolean bl;
        String string = ((TelephonyManager)this.mPhone.getContext().getSystemService("phone")).getSimOperatorNumericForPhone(this.getPhoneId());
        object = object.getOperatorNumeric();
        boolean bl2 = true;
        try {
            bl = string.substring(0, 3).equals(((String)object).substring(0, 3));
        }
        catch (Exception exception) {
            bl = bl2;
        }
        return bl;
    }

    private void dumpCellInfoList(PrintWriter printWriter) {
        printWriter.print(" mLastCellInfoList={");
        Object object = this.mLastCellInfoList;
        if (object != null) {
            boolean bl = true;
            object = object.iterator();
            while (object.hasNext()) {
                CellInfo cellInfo = (CellInfo)object.next();
                if (!bl) {
                    printWriter.print(",");
                }
                bl = false;
                printWriter.print(cellInfo.toString());
            }
        }
        printWriter.println("}");
    }

    private void dumpEarfcnPairList(PrintWriter printWriter) {
        printWriter.print(" mEarfcnPairListForRsrpBoost={");
        ArrayList<Pair<Integer, Integer>> arrayList2 = this.mEarfcnPairListForRsrpBoost;
        if (arrayList2 != null) {
            int n = arrayList2.size();
            for (Pair<Integer, Integer> pair : this.mEarfcnPairListForRsrpBoost) {
                printWriter.print("(");
                printWriter.print(pair.first);
                printWriter.print(",");
                printWriter.print(pair.second);
                printWriter.print(")");
                if (--n == 0) continue;
                printWriter.print(",");
            }
        }
        printWriter.println("}");
    }

    @UnsupportedAppUsage
    private String fixUnknownMcc(String charSequence, int n) {
        Object object;
        boolean bl;
        if (n <= 0) {
            return charSequence;
        }
        if (this.mNitzState.getSavedTimeZoneId() != null) {
            object = TimeZone.getTimeZone(this.mNitzState.getSavedTimeZoneId());
            bl = true;
        } else {
            object = this.mNitzState.getCachedNitzData();
            if (object == null) {
                object = null;
            } else {
                object = TimeZoneLookupHelper.guessZoneByNitzStatic((NitzData)object);
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("fixUnknownMcc(): guessNitzTimeZone returned ");
                Object object2 = object == null ? object : ((TimeZone)object).getID();
                stringBuilder.append(object2);
                this.log(stringBuilder.toString());
            }
            bl = false;
        }
        int n2 = 0;
        if (object != null) {
            n2 = ((TimeZone)object).getRawOffset() / 3600000;
        }
        object = this.mNitzState.getCachedNitzData();
        int n3 = 1;
        int n4 = object != null && ((NitzData)object).isDst() ? 1 : 0;
        object = this.mHbpcdUtils;
        if ((n = ((HbpcdUtils)object).getMcc(n, n2, n4 = n4 != 0 ? n3 : 0, bl)) > 0) {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append(Integer.toString(n));
            ((StringBuilder)charSequence).append(DEFAULT_MNC);
            charSequence = ((StringBuilder)charSequence).toString();
        }
        return charSequence;
    }

    private int[] getBandwidthsFromConfigs(List<PhysicalChannelConfig> list) {
        return list.stream().map(_$$Lambda$WWHOcG5P4_jgjzPPgLwm_wN15OM.INSTANCE).mapToInt(_$$Lambda$UV1wDVoVlbcxpr8zevj_aMFtUGw.INSTANCE).toArray();
    }

    private PersistableBundle getCarrierConfig() {
        CarrierConfigManager carrierConfigManager = (CarrierConfigManager)this.mPhone.getContext().getSystemService("carrier_config");
        if (carrierConfigManager != null && (carrierConfigManager = carrierConfigManager.getConfigForSubId(this.mPhone.getSubId())) != null) {
            return carrierConfigManager;
        }
        return CarrierConfigManager.getDefaultConfig();
    }

    private static CellLocation getCellLocationFromCellInfo(List<CellInfo> cellIdentity) {
        CellIdentity cellIdentity2 = null;
        Object var2_2 = null;
        Object object = cellIdentity2;
        if (cellIdentity != null) {
            object = cellIdentity2;
            if (cellIdentity.size() > 0) {
                block6 : {
                    cellIdentity2 = null;
                    object = cellIdentity.iterator();
                    do {
                        cellIdentity = var2_2;
                        if (!object.hasNext()) break block6;
                        cellIdentity = object.next().getCellIdentity();
                        if (cellIdentity instanceof CellIdentityLte && cellIdentity2 == null) {
                            if (ServiceStateTracker.getCidFromCellIdentity(cellIdentity) == -1) continue;
                            cellIdentity2 = cellIdentity;
                            continue;
                        }
                        if (ServiceStateTracker.getCidFromCellIdentity(cellIdentity) != -1) break;
                    } while (true);
                    cellIdentity = cellIdentity.asCellLocation();
                }
                object = cellIdentity;
                if (cellIdentity == null) {
                    object = cellIdentity;
                    if (cellIdentity2 != null) {
                        object = cellIdentity2.asCellLocation();
                    }
                }
            }
        }
        return object;
    }

    private static int getCidFromCellIdentity(CellIdentity cellIdentity) {
        if (cellIdentity == null) {
            return -1;
        }
        int n = -1;
        int n2 = cellIdentity.getType();
        if (n2 != 1) {
            if (n2 != 3) {
                if (n2 != 4) {
                    if (n2 == 5) {
                        n = ((CellIdentityTdscdma)cellIdentity).getCid();
                    }
                } else {
                    n = ((CellIdentityWcdma)cellIdentity).getCid();
                }
            } else {
                n = ((CellIdentityLte)cellIdentity).getCi();
            }
        } else {
            n = ((CellIdentityGsm)cellIdentity).getCid();
        }
        n2 = n;
        if (n == Integer.MAX_VALUE) {
            n2 = -1;
        }
        return n2;
    }

    private int getLteEarfcn(CellIdentity cellIdentity) {
        int n;
        int n2 = n = -1;
        if (cellIdentity != null) {
            n2 = cellIdentity.getType() != 3 ? n : ((CellIdentityLte)cellIdentity).getEarfcn();
        }
        return n2;
    }

    private String getOperatorBrandOverride() {
        Object object = this.mPhone.getUiccCard();
        if (object == null) {
            return null;
        }
        if ((object = ((UiccCard)object).getUiccProfile()) == null) {
            return null;
        }
        return ((UiccProfile)object).getOperatorBrandOverride();
    }

    private String getOperatorNameFromEri() {
        Object object;
        block21 : {
            int n;
            Object object2;
            Object object3;
            block22 : {
                block23 : {
                    block20 : {
                        object2 = null;
                        object3 = null;
                        if (!this.mPhone.isPhoneTypeCdma()) break block20;
                        object = object2;
                        if (this.mCi.getRadioState() == 1) {
                            object = object2;
                            if (!this.mIsSubscriptionFromRuim) {
                                object = this.mSS.getVoiceRegState() == 0 ? this.mPhone.getCdmaEriText() : this.mPhone.getContext().getText(17040955).toString();
                            }
                        }
                        break block21;
                    }
                    object = object2;
                    if (!this.mPhone.isPhoneTypeCdmaLte()) break block21;
                    n = this.mUiccController.getUiccCard(this.getPhoneId()) != null && this.mUiccController.getUiccCard(this.getPhoneId()).getOperatorBrandOverride() != null ? 1 : 0;
                    object2 = object3;
                    if (n != 0) break block22;
                    object2 = object3;
                    if (this.mCi.getRadioState() != 1) break block22;
                    object2 = object3;
                    if (!this.mEriManager.isEriFileLoaded()) break block22;
                    if (!ServiceState.isLte((int)this.mSS.getRilVoiceRadioTechnology())) break block23;
                    object2 = object3;
                    if (!this.mPhone.getContext().getResources().getBoolean(17891336)) break block22;
                }
                object2 = this.mSS.getOperatorAlpha();
                if (this.mSS.getVoiceRegState() == 0) {
                    object2 = this.mPhone.getCdmaEriText();
                } else if (this.mSS.getVoiceRegState() == 3) {
                    object2 = object = this.getServiceProviderName();
                    if (TextUtils.isEmpty((CharSequence)object)) {
                        object2 = SystemProperties.get((String)"ro.cdma.home.operator.alpha");
                    }
                } else if (this.mSS.getDataRegState() != 0) {
                    object2 = this.mPhone.getContext().getText(17040955).toString();
                }
            }
            object3 = this.mUiccApplcation;
            object = object2;
            if (object3 != null) {
                object = object2;
                if (((UiccCardApplication)object3).getState() == IccCardApplicationStatus.AppState.APPSTATE_READY) {
                    object = object2;
                    if (this.mIccRecords != null) {
                        object = object2;
                        if (this.getCombinedRegState(this.mSS) == 0) {
                            object = object2;
                            if (!ServiceState.isLte((int)this.mSS.getRilVoiceRadioTechnology())) {
                                boolean bl = ((RuimRecords)this.mIccRecords).getCsimSpnDisplayCondition();
                                n = this.mSS.getCdmaEriIconIndex();
                                object = object2;
                                if (bl) {
                                    object = object2;
                                    if (n == 1) {
                                        object = object2;
                                        if (this.isInHomeSidNid(this.mSS.getCdmaSystemId(), this.mSS.getCdmaNetworkId())) {
                                            object = object2;
                                            if (this.mIccRecords != null) {
                                                object = this.getServiceProviderName();
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return object;
    }

    private void getSubscriptionInfoAndStartPollingThreads() {
        this.mCi.getCDMASubscription(this.obtainMessage(34));
        this.pollState();
    }

    private UiccCardApplication getUiccCardApplication() {
        if (this.mPhone.isPhoneTypeGsm()) {
            return this.mUiccController.getUiccCardApplication(this.mPhone.getPhoneId(), 1);
        }
        return this.mUiccController.getUiccCardApplication(this.mPhone.getPhoneId(), 2);
    }

    private void handleCdmaSubscriptionSource(int n) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Subscription Source : ");
        stringBuilder.append(n);
        this.log(stringBuilder.toString());
        boolean bl = n == 0;
        this.mIsSubscriptionFromRuim = bl;
        stringBuilder = new StringBuilder();
        stringBuilder.append("isFromRuim: ");
        stringBuilder.append(this.mIsSubscriptionFromRuim);
        this.log(stringBuilder.toString());
        this.saveCdmaSubscriptionSource(n);
        if (!this.mIsSubscriptionFromRuim) {
            this.sendMessage(this.obtainMessage(35));
        }
    }

    private boolean iccCardExists() {
        boolean bl = false;
        UiccCardApplication uiccCardApplication = this.mUiccApplcation;
        if (uiccCardApplication != null) {
            bl = uiccCardApplication.getState() != IccCardApplicationStatus.AppState.APPSTATE_UNKNOWN;
        }
        return bl;
    }

    @UnsupportedAppUsage
    private boolean isGprsConsistent(int n, int n2) {
        boolean bl = n2 != 0 || n == 0;
        return bl;
    }

    private boolean isHomeSid(int n) {
        if (this.mHomeSystemId != null) {
            int[] arrn;
            for (int i = 0; i < (arrn = this.mHomeSystemId).length; ++i) {
                if (n != arrn[i]) continue;
                return true;
            }
        }
        return false;
    }

    @UnsupportedAppUsage
    private boolean isInHomeSidNid(int n, int n2) {
        int[] arrn;
        if (this.isSidsAllZeros()) {
            return true;
        }
        if (this.mHomeSystemId.length != this.mHomeNetworkId.length) {
            return true;
        }
        if (n == 0) {
            return true;
        }
        for (int i = 0; i < (arrn = this.mHomeSystemId).length; ++i) {
            if (arrn[i] != n || (arrn = this.mHomeNetworkId)[i] != 0 && arrn[i] != 65535 && n2 != 0 && n2 != 65535 && arrn[i] != n2) continue;
            return true;
        }
        return false;
    }

    private boolean isInNetwork(BaseBundle arrstring, String string, String string2) {
        return (arrstring = arrstring.getStringArray(string2)) != null && Arrays.asList(arrstring).contains(string);
    }

    @UnsupportedAppUsage
    private boolean isInvalidOperatorNumeric(String string) {
        boolean bl = string == null || string.length() < 5 || string.startsWith(INVALID_MCC);
        return bl;
    }

    private boolean isNrPhysicalChannelConfig(PhysicalChannelConfig physicalChannelConfig) {
        boolean bl = physicalChannelConfig.getRat() == 20;
        return bl;
    }

    private boolean isNrStateChanged(NetworkRegistrationInfo networkRegistrationInfo, NetworkRegistrationInfo networkRegistrationInfo2) {
        boolean bl = true;
        boolean bl2 = true;
        if (networkRegistrationInfo != null && networkRegistrationInfo2 != null) {
            if (networkRegistrationInfo.getNrState() == networkRegistrationInfo2.getNrState()) {
                bl2 = false;
            }
            return bl2;
        }
        bl2 = networkRegistrationInfo != networkRegistrationInfo2 ? bl : false;
        return bl2;
    }

    private boolean isOperatorConsideredNonRoaming(ServiceState arrobject) {
        String string = arrobject.getOperatorNumeric();
        arrobject = this.getCarrierConfig().getStringArray("non_roaming_operator_string_array");
        if (!ArrayUtils.isEmpty((Object[])arrobject) && string != null) {
            for (Object object : arrobject) {
                if (TextUtils.isEmpty((CharSequence)object) || !string.startsWith((String)object)) continue;
                return true;
            }
            return false;
        }
        return false;
    }

    private boolean isOperatorConsideredRoaming(ServiceState object2) {
        String string = object2.getOperatorNumeric();
        Object[] arrobject = this.getCarrierConfig().getStringArray("roaming_operator_string_array");
        if (!ArrayUtils.isEmpty((Object[])arrobject) && string != null) {
            for (Object object : arrobject) {
                if (TextUtils.isEmpty((CharSequence)object) || !string.startsWith((String)object)) continue;
                return true;
            }
            return false;
        }
        return false;
    }

    private boolean isRoamIndForHomeSystem(int n) {
        int[] arrn = this.getCarrierConfig().getIntArray("cdma_enhanced_roaming_indicator_for_home_network_int_array");
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("isRoamIndForHomeSystem: homeRoamIndicators=");
        stringBuilder.append(Arrays.toString(arrn));
        this.log(stringBuilder.toString());
        if (arrn != null) {
            int n2 = arrn.length;
            for (int i = 0; i < n2; ++i) {
                if (arrn[i] != n) continue;
                return true;
            }
            stringBuilder = new StringBuilder();
            stringBuilder.append("isRoamIndForHomeSystem: No match found against list for roamInd=");
            stringBuilder.append(n);
            this.log(stringBuilder.toString());
            return false;
        }
        this.log("isRoamIndForHomeSystem: No list found");
        return false;
    }

    private boolean isRoamingBetweenOperators(boolean bl, ServiceState serviceState) {
        bl = bl && !this.isSameOperatorNameFromSimAndSS(serviceState);
        return bl;
    }

    private boolean isSameNamedOperators(ServiceState serviceState) {
        boolean bl = this.currentMccEqualsSimMcc(serviceState) && this.isSameOperatorNameFromSimAndSS(serviceState);
        return bl;
    }

    private boolean isSameOperatorNameFromSimAndSS(ServiceState object) {
        String string = ((TelephonyManager)this.mPhone.getContext().getSystemService("phone")).getSimOperatorNameForPhone(this.getPhoneId());
        String string2 = object.getOperatorAlphaLong();
        object = object.getOperatorAlphaShort();
        boolean bl = TextUtils.isEmpty((CharSequence)string);
        boolean bl2 = true;
        boolean bl3 = !bl && string.equalsIgnoreCase(string2);
        boolean bl4 = !TextUtils.isEmpty((CharSequence)string) && string.equalsIgnoreCase((String)object);
        bl = bl2;
        if (!bl3) {
            bl = bl4 ? bl2 : false;
        }
        return bl;
    }

    private boolean isSimAbsent() {
        Object object = this.mUiccController;
        boolean bl = object == null ? true : ((object = object.getUiccCard(this.mPhone.getPhoneId())) == null ? true : ((UiccCard)object).getCardState() == IccCardStatus.CardState.CARDSTATE_ABSENT);
        return bl;
    }

    private static boolean isValidLteBandwidthKhz(int n) {
        return n == 1400 || n == 3000 || n == 5000 || n == 10000 || n == 15000 || n == 20000;
    }

    private void logAttachChange() {
        this.mAttachLog.log(this.mSS.toString());
    }

    private void logPhoneTypeChange() {
        this.mPhoneTypeLog.log(Integer.toString(this.mPhone.getPhoneType()));
    }

    private void logRatChange() {
        this.mRatLog.log(this.mSS.toString());
    }

    private void logRoamingChange() {
        this.mRoamingLog.log(this.mSS.toString());
    }

    private void modemTriggeredPollState() {
        this.pollState(true);
    }

    private boolean networkCountryIsoChanged(String string, String string2) {
        if (TextUtils.isEmpty((CharSequence)string)) {
            this.log("countryIsoChanged: no new country ISO code");
            return false;
        }
        if (TextUtils.isEmpty((CharSequence)string2)) {
            this.log("countryIsoChanged: no previous country ISO code");
            return true;
        }
        return string.equals(string2) ^ true;
    }

    private void notifyCdmaSubscriptionInfoReady() {
        if (this.mCdmaForSubscriptionInfoReadyRegistrants != null) {
            this.log("CDMA_SUBSCRIPTION: call notifyRegistrants()");
            this.mCdmaForSubscriptionInfoReadyRegistrants.notifyRegistrants();
        }
    }

    private void notifySpnDisplayUpdate(CarrierDisplayNameData carrierDisplayNameData) {
        int n = this.mPhone.getSubId();
        if (!(this.mSubId == n && carrierDisplayNameData.shouldShowPlmn() == this.mCurShowPlmn && carrierDisplayNameData.shouldShowSpn() == this.mCurShowSpn && TextUtils.equals((CharSequence)carrierDisplayNameData.getSpn(), (CharSequence)this.mCurSpn) && TextUtils.equals((CharSequence)carrierDisplayNameData.getDataSpn(), (CharSequence)this.mCurDataSpn) && TextUtils.equals((CharSequence)carrierDisplayNameData.getPlmn(), (CharSequence)this.mCurPlmn))) {
            String string = String.format("updateSpnDisplay: changed sending intent, rule=%d, showPlmn='%b', plmn='%s', showSpn='%b', spn='%s', dataSpn='%s', subId='%d'", this.getCarrierNameDisplayBitmask(this.mSS), carrierDisplayNameData.shouldShowPlmn(), carrierDisplayNameData.getPlmn(), carrierDisplayNameData.shouldShowSpn(), carrierDisplayNameData.getSpn(), carrierDisplayNameData.getDataSpn(), n);
            this.mCdnrLogs.log(string);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("updateSpnDisplay: ");
            stringBuilder.append(string);
            this.log(stringBuilder.toString());
            string = new Intent("android.provider.Telephony.SPN_STRINGS_UPDATED");
            string.putExtra("showSpn", carrierDisplayNameData.shouldShowSpn());
            string.putExtra("spn", carrierDisplayNameData.getSpn());
            string.putExtra("spnData", carrierDisplayNameData.getDataSpn());
            string.putExtra("showPlmn", carrierDisplayNameData.shouldShowPlmn());
            string.putExtra("plmn", carrierDisplayNameData.getPlmn());
            SubscriptionManager.putPhoneIdAndSubIdExtra((Intent)string, (int)this.mPhone.getPhoneId());
            this.mPhone.getContext().sendStickyBroadcastAsUser((Intent)string, UserHandle.ALL);
            if (!this.mSubscriptionController.setPlmnSpn(this.mPhone.getPhoneId(), carrierDisplayNameData.shouldShowPlmn(), carrierDisplayNameData.getPlmn(), carrierDisplayNameData.shouldShowSpn(), carrierDisplayNameData.getSpn())) {
                this.mSpnUpdatePending = true;
            }
        }
        this.mSubId = n;
        this.mCurShowSpn = carrierDisplayNameData.shouldShowSpn();
        this.mCurShowPlmn = carrierDisplayNameData.shouldShowPlmn();
        this.mCurSpn = carrierDisplayNameData.getSpn();
        this.mCurDataSpn = carrierDisplayNameData.getDataSpn();
        this.mCurPlmn = carrierDisplayNameData.getPlmn();
    }

    private void onCarrierConfigChanged() {
        PersistableBundle persistableBundle = this.getCarrierConfig();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("CarrierConfigChange ");
        stringBuilder.append((Object)persistableBundle);
        this.log(stringBuilder.toString());
        this.mEriManager.loadEriFile();
        this.mCdnr.updateEfForEri(this.getOperatorNameFromEri());
        this.updateLteEarfcnLists(persistableBundle);
        this.updateReportingCriteria(persistableBundle);
        this.updateOperatorNamePattern(persistableBundle);
        this.mCdnr.updateEfFromCarrierConfig(persistableBundle);
        this.pollState();
    }

    private void onRestrictedStateChanged(AsyncResult object) {
        RestrictedState restrictedState = new RestrictedState();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("onRestrictedStateChanged: E rs ");
        stringBuilder.append(this.mRestrictedState);
        this.log(stringBuilder.toString());
        if (((AsyncResult)object).exception == null && ((AsyncResult)object).result != null) {
            int n = (Integer)((AsyncResult)object).result;
            boolean bl = false;
            boolean bl2 = (n & 1) != 0 || (n & 4) != 0;
            restrictedState.setCsEmergencyRestricted(bl2);
            object = this.mUiccApplcation;
            if (object != null && ((UiccCardApplication)object).getState() == IccCardApplicationStatus.AppState.APPSTATE_READY) {
                bl2 = (n & 2) != 0 || (n & 4) != 0;
                restrictedState.setCsNormalRestricted(bl2);
                bl2 = bl;
                if ((n & 16) != 0) {
                    bl2 = true;
                }
                restrictedState.setPsRestricted(bl2);
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("onRestrictedStateChanged: new rs ");
            ((StringBuilder)object).append(restrictedState);
            this.log(((StringBuilder)object).toString());
            if (!this.mRestrictedState.isPsRestricted() && restrictedState.isPsRestricted()) {
                this.mPsRestrictEnabledRegistrants.notifyRegistrants();
                this.setNotification(1001);
            } else if (this.mRestrictedState.isPsRestricted() && !restrictedState.isPsRestricted()) {
                this.mPsRestrictDisabledRegistrants.notifyRegistrants();
                this.setNotification(1002);
            }
            if (this.mRestrictedState.isCsRestricted()) {
                if (!restrictedState.isAnyCsRestricted()) {
                    this.setNotification(1004);
                } else if (!restrictedState.isCsNormalRestricted()) {
                    this.setNotification(1006);
                } else if (!restrictedState.isCsEmergencyRestricted()) {
                    this.setNotification(1005);
                }
            } else if (this.mRestrictedState.isCsEmergencyRestricted() && !this.mRestrictedState.isCsNormalRestricted()) {
                if (!restrictedState.isAnyCsRestricted()) {
                    this.setNotification(1004);
                } else if (restrictedState.isCsRestricted()) {
                    this.setNotification(1003);
                } else if (restrictedState.isCsNormalRestricted()) {
                    this.setNotification(1005);
                }
            } else if (!this.mRestrictedState.isCsEmergencyRestricted() && this.mRestrictedState.isCsNormalRestricted()) {
                if (!restrictedState.isAnyCsRestricted()) {
                    this.setNotification(1004);
                } else if (restrictedState.isCsRestricted()) {
                    this.setNotification(1003);
                } else if (restrictedState.isCsEmergencyRestricted()) {
                    this.setNotification(1006);
                }
            } else if (restrictedState.isCsRestricted()) {
                this.setNotification(1003);
            } else if (restrictedState.isCsEmergencyRestricted()) {
                this.setNotification(1006);
            } else if (restrictedState.isCsNormalRestricted()) {
                this.setNotification(1005);
            }
            this.mRestrictedState = restrictedState;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("onRestrictedStateChanged: X rs ");
        ((StringBuilder)object).append(this.mRestrictedState);
        this.log(((StringBuilder)object).toString());
    }

    private void pollStateDone() {
        block43 : {
            block44 : {
                Object object;
                int n;
                NetworkRegistrationInfo networkRegistrationInfo;
                int n2;
                boolean bl;
                boolean bl2;
                boolean bl3;
                boolean bl4;
                int n3;
                int n4;
                if (!this.mPhone.isPhoneTypeGsm()) {
                    this.updateRoamingState();
                }
                if (Build.IS_DEBUGGABLE && SystemProperties.getBoolean((String)PROP_FORCE_ROAMING, (boolean)false)) {
                    this.mNewSS.setVoiceRoaming(true);
                    this.mNewSS.setDataRoaming(true);
                }
                this.useDataRegStateForDataOnlyDevices();
                this.processIwlanRegistrationInfo();
                if (Build.IS_DEBUGGABLE && this.mPhone.mTelephonyTester != null) {
                    this.mPhone.mTelephonyTester.overrideServiceState(this.mNewSS);
                }
                Object object2 = new StringBuilder();
                ((StringBuilder)object2).append("Poll ServiceState done:  oldSS=[");
                ((StringBuilder)object2).append((Object)this.mSS);
                ((StringBuilder)object2).append("] newSS=[");
                ((StringBuilder)object2).append((Object)this.mNewSS);
                ((StringBuilder)object2).append("] oldMaxDataCalls=");
                ((StringBuilder)object2).append(this.mMaxDataCalls);
                ((StringBuilder)object2).append(" mNewMaxDataCalls=");
                ((StringBuilder)object2).append(this.mNewMaxDataCalls);
                ((StringBuilder)object2).append(" oldReasonDataDenied=");
                ((StringBuilder)object2).append(this.mReasonDataDenied);
                ((StringBuilder)object2).append(" mNewReasonDataDenied=");
                ((StringBuilder)object2).append(this.mNewReasonDataDenied);
                this.log(((StringBuilder)object2).toString());
                boolean bl5 = this.mSS.getVoiceRegState() != 0 && this.mNewSS.getVoiceRegState() == 0;
                boolean bl6 = this.mSS.getVoiceRegState() == 0 && this.mNewSS.getVoiceRegState() != 0;
                boolean bl7 = this.mSS.getVoiceRegState() != 3 && this.mNewSS.getVoiceRegState() == 3;
                SparseBooleanArray sparseBooleanArray = new SparseBooleanArray(this.mTransportManager.getAvailableTransports().length);
                SparseBooleanArray sparseBooleanArray2 = new SparseBooleanArray(this.mTransportManager.getAvailableTransports().length);
                SparseBooleanArray sparseBooleanArray3 = new SparseBooleanArray(this.mTransportManager.getAvailableTransports().length);
                SparseBooleanArray sparseBooleanArray4 = new SparseBooleanArray(this.mTransportManager.getAvailableTransports().length);
                int n5 = 0;
                object2 = this.mTransportManager.getAvailableTransports();
                int n6 = ((int[])object2).length;
                int n7 = 0;
                for (n4 = 0; n4 < n6; ++n4) {
                    n = object2[n4];
                    object = this.mSS.getNetworkRegistrationInfo(2, n);
                    networkRegistrationInfo = this.mNewSS.getNetworkRegistrationInfo(2, n);
                    bl3 = (object == null || !object.isInService() || bl7) && networkRegistrationInfo != null && networkRegistrationInfo.isInService();
                    sparseBooleanArray.put(n, bl3);
                    bl3 = object != null && object.isInService() && (networkRegistrationInfo == null || !networkRegistrationInfo.isInService());
                    sparseBooleanArray2.put(n, bl3);
                    n3 = object != null ? object.getAccessNetworkTechnology() : 0;
                    n2 = networkRegistrationInfo != null ? networkRegistrationInfo.getAccessNetworkTechnology() : 0;
                    bl3 = n3 != n2;
                    sparseBooleanArray3.put(n, bl3);
                    if (n3 != n2) {
                        n5 = 1;
                    }
                    n3 = object != null ? object.getRegistrationState() : 4;
                    n2 = networkRegistrationInfo != null ? networkRegistrationInfo.getRegistrationState() : 4;
                    bl3 = n3 != n2;
                    sparseBooleanArray4.put(n, bl3);
                    if (n3 == n2) continue;
                    n7 = 1;
                }
                n6 = this.mSS.getVoiceRegState() != this.mNewSS.getVoiceRegState() ? 1 : 0;
                boolean bl8 = this.mSS.getNrFrequencyRange() != this.mNewSS.getNrFrequencyRange();
                boolean bl9 = this.isNrStateChanged(this.mSS.getNetworkRegistrationInfo(2, 3), this.mNewSS.getNetworkRegistrationInfo(2, 3));
                boolean bl10 = Objects.equals((Object)this.mNewCellIdentity, (Object)this.mCellIdentity) ^ true;
                n4 = this.mNewSS.getDataRegState() == 0 ? 1 : 0;
                if (n4 != 0) {
                    this.mRatRatcheter.ratchet(this.mSS, this.mNewSS, bl10);
                }
                boolean bl11 = this.mSS.getRilVoiceRadioTechnology() != this.mNewSS.getRilVoiceRadioTechnology();
                boolean bl12 = this.mNewSS.equals((Object)this.mSS) ^ true;
                boolean bl13 = !this.mSS.getVoiceRoaming() && this.mNewSS.getVoiceRoaming();
                bl3 = this.mSS.getVoiceRoaming() && !this.mNewSS.getVoiceRoaming();
                boolean bl14 = !this.mSS.getDataRoaming() && this.mNewSS.getDataRoaming();
                boolean bl15 = this.mSS.getDataRoaming() && !this.mNewSS.getDataRoaming();
                n4 = this.mRejectCode != this.mNewRejectCode ? 1 : 0;
                boolean bl16 = this.mSS.getCssIndicator() != this.mNewSS.getCssIndicator();
                if (this.mPhone.isPhoneTypeCdmaLte()) {
                    bl = this.mNewSS.getDataRegState() == 0 && (ServiceState.isLte((int)this.mSS.getRilDataRadioTechnology()) && this.mNewSS.getRilDataRadioTechnology() == 13 || this.mSS.getRilDataRadioTechnology() == 13 && ServiceState.isLte((int)this.mNewSS.getRilDataRadioTechnology()));
                    bl4 = (ServiceState.isLte((int)this.mNewSS.getRilDataRadioTechnology()) || this.mNewSS.getRilDataRadioTechnology() == 13) && !ServiceState.isLte((int)this.mSS.getRilDataRadioTechnology()) && this.mSS.getRilDataRadioTechnology() != 13;
                    bl2 = this.mNewSS.getRilDataRadioTechnology() >= 4 && this.mNewSS.getRilDataRadioTechnology() <= 8;
                } else {
                    bl4 = false;
                    bl2 = false;
                    bl = false;
                }
                object2 = new StringBuilder();
                ((StringBuilder)object2).append("pollStateDone: hasRegistered = ");
                ((StringBuilder)object2).append(bl5);
                ((StringBuilder)object2).append(" hasDeregistered = ");
                ((StringBuilder)object2).append(bl6);
                ((StringBuilder)object2).append(" hasDataAttached = ");
                ((StringBuilder)object2).append((Object)sparseBooleanArray);
                ((StringBuilder)object2).append(" hasDataDetached = ");
                ((StringBuilder)object2).append((Object)sparseBooleanArray2);
                ((StringBuilder)object2).append(" hasDataRegStateChanged = ");
                ((StringBuilder)object2).append((Object)sparseBooleanArray4);
                ((StringBuilder)object2).append(" hasRilVoiceRadioTechnologyChanged = ");
                ((StringBuilder)object2).append(bl11);
                ((StringBuilder)object2).append(" hasRilDataRadioTechnologyChanged = ");
                ((StringBuilder)object2).append((Object)sparseBooleanArray3);
                ((StringBuilder)object2).append(" hasChanged = ");
                ((StringBuilder)object2).append(bl12);
                ((StringBuilder)object2).append(" hasVoiceRoamingOn = ");
                ((StringBuilder)object2).append(bl13);
                ((StringBuilder)object2).append(" hasVoiceRoamingOff = ");
                ((StringBuilder)object2).append(bl3);
                ((StringBuilder)object2).append(" hasDataRoamingOn =");
                ((StringBuilder)object2).append(bl14);
                ((StringBuilder)object2).append(" hasDataRoamingOff = ");
                ((StringBuilder)object2).append(bl15);
                ((StringBuilder)object2).append(" hasLocationChanged = ");
                ((StringBuilder)object2).append(bl10);
                ((StringBuilder)object2).append(" has4gHandoff = ");
                ((StringBuilder)object2).append(bl);
                ((StringBuilder)object2).append(" hasMultiApnSupport = ");
                ((StringBuilder)object2).append(bl4);
                ((StringBuilder)object2).append(" hasLostMultiApnSupport = ");
                ((StringBuilder)object2).append(bl2);
                ((StringBuilder)object2).append(" hasCssIndicatorChanged = ");
                ((StringBuilder)object2).append(bl16);
                ((StringBuilder)object2).append(" hasNrFrequencyRangeChanged = ");
                ((StringBuilder)object2).append(bl8);
                ((StringBuilder)object2).append(" hasNrStateChanged = ");
                ((StringBuilder)object2).append(bl9);
                ((StringBuilder)object2).append(" hasAirplaneModeOnlChanged = ");
                ((StringBuilder)object2).append(bl7);
                this.log(((StringBuilder)object2).toString());
                if (n6 != 0 || n7 != 0) {
                    n7 = this.mPhone.isPhoneTypeGsm() ? 50114 : 50116;
                    EventLog.writeEvent((int)n7, (Object[])new Object[]{this.mSS.getVoiceRegState(), this.mSS.getDataRegState(), this.mNewSS.getVoiceRegState(), this.mNewSS.getDataRegState()});
                }
                if (this.mPhone.isPhoneTypeGsm()) {
                    if (bl11) {
                        n7 = ServiceStateTracker.getCidFromCellIdentity(this.mNewCellIdentity);
                        EventLog.writeEvent((int)50123, (Object[])new Object[]{n7, this.mSS.getRilVoiceRadioTechnology(), this.mNewSS.getRilVoiceRadioTechnology()});
                        object2 = new StringBuilder();
                        ((StringBuilder)object2).append("RAT switched ");
                        ((StringBuilder)object2).append(ServiceState.rilRadioTechnologyToString((int)this.mSS.getRilVoiceRadioTechnology()));
                        ((StringBuilder)object2).append(" -> ");
                        ((StringBuilder)object2).append(ServiceState.rilRadioTechnologyToString((int)this.mNewSS.getRilVoiceRadioTechnology()));
                        ((StringBuilder)object2).append(" at cell ");
                        ((StringBuilder)object2).append(n7);
                        this.log(((StringBuilder)object2).toString());
                    }
                    if (bl16) {
                        this.mPhone.notifyDataConnection();
                    }
                    this.mReasonDataDenied = this.mNewReasonDataDenied;
                    this.mMaxDataCalls = this.mNewMaxDataCalls;
                    this.mRejectCode = this.mNewRejectCode;
                }
                ServiceState serviceState = new ServiceState(this.mPhone.getServiceState());
                object2 = this.mSS;
                this.mSS = this.mNewSS;
                this.mNewSS = object2;
                this.mNewSS.setStateOutOfService();
                object2 = this.mCellIdentity;
                this.mCellIdentity = this.mNewCellIdentity;
                this.mNewCellIdentity = object2;
                if (bl11) {
                    this.updatePhoneObject();
                }
                networkRegistrationInfo = (TelephonyManager)this.mPhone.getContext().getSystemService("phone");
                if (n5 != 0) {
                    networkRegistrationInfo.setDataNetworkTypeForPhone(this.mPhone.getPhoneId(), this.mSS.getRilDataRadioTechnology());
                    StatsLog.write((int)76, (int)ServiceState.rilRadioTechnologyToNetworkType((int)this.mSS.getRilDataRadioTechnology()), (int)this.mPhone.getPhoneId());
                }
                bl16 = bl3;
                if (bl5) {
                    this.mNetworkAttachedRegistrants.notifyRegistrants();
                    this.mNitzState.handleNetworkAvailable();
                }
                if (bl6) {
                    this.mNetworkDetachedRegistrants.notifyRegistrants();
                }
                if (n4 != 0) {
                    this.setNotification(2001);
                }
                if (bl12) {
                    this.updateSpnDisplay();
                    networkRegistrationInfo.setNetworkOperatorNameForPhone(this.mPhone.getPhoneId(), this.mSS.getOperatorAlpha());
                    object2 = object = this.mSS.getOperatorNumeric();
                    if (!this.mPhone.isPhoneTypeGsm()) {
                        object2 = object;
                        if (this.isInvalidOperatorNumeric((String)object)) {
                            object2 = this.fixUnknownMcc((String)object, this.mSS.getCdmaSystemId());
                        }
                    }
                    networkRegistrationInfo.setNetworkOperatorNumericForPhone(this.mPhone.getPhoneId(), (String)object2);
                    if (this.isInvalidOperatorNumeric((String)object2)) {
                        object = new StringBuilder();
                        ((StringBuilder)object).append("operatorNumeric ");
                        ((StringBuilder)object).append((String)object2);
                        ((StringBuilder)object).append(" is invalid");
                        this.log(((StringBuilder)object).toString());
                        this.mLocaleTracker.updateOperatorNumeric("");
                    } else if (this.mSS.getRilDataRadioTechnology() != 18) {
                        if (!this.mPhone.isPhoneTypeGsm()) {
                            this.setOperatorIdd((String)object2);
                        }
                        this.mLocaleTracker.updateOperatorNumeric((String)object2);
                    }
                    n5 = this.mPhone.getPhoneId();
                    bl3 = this.mPhone.isPhoneTypeGsm() ? this.mSS.getVoiceRoaming() : this.mSS.getVoiceRoaming() || this.mSS.getDataRoaming();
                    networkRegistrationInfo.setNetworkRoamingForPhone(n5, bl3);
                    this.setRoamingType(this.mSS);
                    object2 = new StringBuilder();
                    ((StringBuilder)object2).append("Broadcasting ServiceState : ");
                    ((StringBuilder)object2).append((Object)this.mSS);
                    this.log(((StringBuilder)object2).toString());
                    if (!serviceState.equals((Object)this.mPhone.getServiceState())) {
                        object2 = this.mPhone;
                        ((GsmCdmaPhone)object2).notifyServiceStateChanged(((GsmCdmaPhone)object2).getServiceState());
                    }
                    this.mPhone.getContext().getContentResolver().insert(Telephony.ServiceStateTable.getUriForSubscriptionId((int)this.mPhone.getSubId()), Telephony.ServiceStateTable.getContentValuesForServiceState((ServiceState)this.mSS));
                    TelephonyMetrics.getInstance().writeServiceStateChanged(this.mPhone.getPhoneId(), this.mSS);
                }
                n5 = 0;
                n7 = 0;
                if (bl5 || bl6) {
                    n5 = 1;
                }
                if (bl) {
                    ((RegistrantList)this.mAttachedRegistrants.get(1)).notifyRegistrants();
                    n5 = 1;
                }
                if (bl11) {
                    n7 = 1;
                    this.notifySignalStrength();
                }
                object = this.mTransportManager.getAvailableTransports();
                n2 = ((int[])object).length;
                n3 = 0;
                n4 = n7;
                object2 = networkRegistrationInfo;
                for (n7 = n3; n7 < n2; ++n7) {
                    n = object[n7];
                    if (sparseBooleanArray3.get(n)) {
                        n4 = 1;
                        this.notifySignalStrength();
                    }
                    if (sparseBooleanArray4.get(n) || sparseBooleanArray3.get(n)) {
                        this.notifyDataRegStateRilRadioTechnologyChanged(n);
                        this.mPhone.notifyDataConnection();
                    }
                    if (sparseBooleanArray.get(n)) {
                        n5 = n3 = 1;
                        if (this.mAttachedRegistrants.get(n) != null) {
                            ((RegistrantList)this.mAttachedRegistrants.get(n)).notifyRegistrants();
                            n5 = n3;
                        }
                    }
                    if (!sparseBooleanArray2.get(n)) continue;
                    if (this.mDetachedRegistrants.get(n) != null) {
                        ((RegistrantList)this.mDetachedRegistrants.get(n)).notifyRegistrants();
                    }
                    n5 = 1;
                }
                if (n5 != 0) {
                    this.logAttachChange();
                }
                if (n4 != 0) {
                    this.logRatChange();
                }
                if (n6 != 0 || bl11) {
                    this.notifyVoiceRegStateRilRadioTechnologyChanged();
                }
                if (bl13 || bl16 || bl14 || bl15) {
                    this.logRoamingChange();
                }
                if (bl13) {
                    this.mVoiceRoamingOnRegistrants.notifyRegistrants();
                }
                if (bl16) {
                    this.mVoiceRoamingOffRegistrants.notifyRegistrants();
                }
                if (bl14) {
                    this.mDataRoamingOnRegistrants.notifyRegistrants();
                }
                if (bl15) {
                    this.mDataRoamingOffRegistrants.notifyRegistrants();
                }
                if (bl10) {
                    this.mPhone.notifyLocationChanged(this.getCellLocation());
                }
                if (!this.mPhone.isPhoneTypeGsm()) break block43;
                if (this.isGprsConsistent(this.mSS.getDataRegState(), this.mSS.getVoiceRegState())) break block44;
                if (this.mStartedGprsRegCheck || this.mReportedGprsNoReg) break block43;
                this.mStartedGprsRegCheck = true;
                n5 = Settings.Global.getInt((ContentResolver)this.mPhone.getContext().getContentResolver(), (String)"gprs_register_check_period_ms", (int)60000);
                this.sendMessageDelayed(this.obtainMessage(22), (long)n5);
                break block43;
            }
            this.mReportedGprsNoReg = false;
        }
    }

    private void processIwlanRegistrationInfo() {
        NetworkRegistrationInfo networkRegistrationInfo;
        if (this.mCi.getRadioState() == 0) {
            CharSequence charSequence;
            boolean bl = false;
            this.log("set service state as POWER_OFF");
            if (18 == this.mNewSS.getRilDataRadioTechnology()) {
                charSequence = new StringBuilder();
                ((StringBuilder)charSequence).append("pollStateDone: mNewSS = ");
                ((StringBuilder)charSequence).append((Object)this.mNewSS);
                this.log(((StringBuilder)charSequence).toString());
                this.log("pollStateDone: reset iwlan RAT value");
                bl = true;
            }
            charSequence = this.mNewSS.getOperatorAlphaLong();
            this.mNewSS.setStateOff();
            if (bl) {
                this.mNewSS.setDataRegState(0);
                NetworkRegistrationInfo networkRegistrationInfo2 = new NetworkRegistrationInfo.Builder().setTransportType(2).setDomain(2).setAccessNetworkTechnology(18).setRegistrationState(1).build();
                this.mNewSS.addNetworkRegistrationInfo(networkRegistrationInfo2);
                if (this.mTransportManager.isInLegacyMode()) {
                    networkRegistrationInfo2 = new NetworkRegistrationInfo.Builder().setTransportType(1).setDomain(2).setAccessNetworkTechnology(18).setRegistrationState(1).build();
                    this.mNewSS.addNetworkRegistrationInfo(networkRegistrationInfo2);
                }
                this.mNewSS.setOperatorAlphaLong((String)charSequence);
                charSequence = new StringBuilder();
                ((StringBuilder)charSequence).append("pollStateDone: mNewSS = ");
                ((StringBuilder)charSequence).append((Object)this.mNewSS);
                this.log(((StringBuilder)charSequence).toString());
            }
            return;
        }
        if (this.mTransportManager.isInLegacyMode() && (networkRegistrationInfo = this.mNewSS.getNetworkRegistrationInfo(2, 1)) != null && networkRegistrationInfo.getAccessNetworkTechnology() == 18) {
            networkRegistrationInfo = new NetworkRegistrationInfo.Builder().setTransportType(2).setDomain(2).setRegistrationState(networkRegistrationInfo.getRegistrationState()).setAccessNetworkTechnology(18).setRejectCause(networkRegistrationInfo.getRejectCause()).setEmergencyOnly(networkRegistrationInfo.isEmergencyEnabled()).setAvailableServices(networkRegistrationInfo.getAvailableServices()).build();
            this.mNewSS.addNetworkRegistrationInfo(networkRegistrationInfo);
        }
    }

    private void queueNextSignalStrengthPoll() {
        if (this.mDontPollSignalStrength) {
            return;
        }
        UiccCard uiccCard = UiccController.getInstance().getUiccCard(this.getPhoneId());
        if (uiccCard != null && uiccCard.getCardState() != IccCardStatus.CardState.CARDSTATE_ABSENT) {
            uiccCard = this.obtainMessage();
            ((Message)uiccCard).what = 10;
            this.sendMessageDelayed((Message)uiccCard, 20000L);
            return;
        }
        this.log("Not polling signal strength due to absence of SIM");
    }

    private boolean regCodeIsRoaming(int n) {
        boolean bl = 5 == n;
        return bl;
    }

    private int regCodeToServiceState(int n) {
        return n != 1 && n != 5;
    }

    private void saveCdmaSubscriptionSource(int n) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Storing cdma subscription source: ");
        stringBuilder.append(n);
        this.log(stringBuilder.toString());
        Settings.Global.putInt((ContentResolver)this.mPhone.getContext().getContentResolver(), (String)"subscription_mode", (int)n);
        stringBuilder = new StringBuilder();
        stringBuilder.append("Read from settings: ");
        stringBuilder.append(Settings.Global.getInt((ContentResolver)this.mPhone.getContext().getContentResolver(), (String)"subscription_mode", (int)-1));
        this.log(stringBuilder.toString());
    }

    private int selectResourceForRejectCode(int n, boolean bl) {
        int n2 = 0;
        n = n != 1 ? (n != 2 ? (n != 3 ? (n != 6 ? n2 : (bl ? 17040438 : 17040437)) : (bl ? 17040440 : 17040439)) : (bl ? 17040442 : 17040441)) : (bl ? 17040436 : 17040435);
        return n;
    }

    private void setPhyCellInfoFromCellIdentity(ServiceState serviceState, CellIdentity object) {
        block12 : {
            Object object2;
            block14 : {
                CellIdentityLte cellIdentityLte;
                int n;
                block13 : {
                    if (object == null) {
                        this.log("Could not set ServiceState channel number. CellIdentity null");
                        return;
                    }
                    serviceState.setChannelNumber(object.getChannelNumber());
                    if (!(object instanceof CellIdentityLte)) break block12;
                    cellIdentityLte = (CellIdentityLte)object;
                    object = null;
                    if (!ArrayUtils.isEmpty(this.mLastPhysicalChannelConfigList)) {
                        object2 = this.getBandwidthsFromConfigs(this.mLastPhysicalChannelConfigList);
                        int n2 = ((int[])object2).length;
                        n = 0;
                        do {
                            object = object2;
                            if (n >= n2) break;
                            int n3 = object2[n];
                            if (!ServiceStateTracker.isValidLteBandwidthKhz(n3)) {
                                object = new StringBuilder();
                                ((StringBuilder)object).append("Invalid LTE Bandwidth in RegistrationState, ");
                                ((StringBuilder)object).append(n3);
                                this.loge(((StringBuilder)object).toString());
                                object = null;
                                break;
                            }
                            ++n;
                        } while (true);
                    }
                    if (object == null) break block13;
                    object2 = object;
                    if (((int[])object).length != 1) break block14;
                }
                if (ServiceStateTracker.isValidLteBandwidthKhz(n = cellIdentityLte.getBandwidth())) {
                    object2 = new int[]{n};
                } else if (n == Integer.MAX_VALUE) {
                    object2 = object;
                } else {
                    object2 = new StringBuilder();
                    ((StringBuilder)object2).append("Invalid LTE Bandwidth in RegistrationState, ");
                    ((StringBuilder)object2).append(n);
                    this.loge(((StringBuilder)object2).toString());
                    object2 = object;
                }
            }
            if (object2 != null) {
                serviceState.setCellBandwidths((int[])object2);
            }
        }
    }

    private void setRoamingOff() {
        this.mNewSS.setVoiceRoaming(false);
        this.mNewSS.setDataRoaming(false);
        this.mNewSS.setCdmaEriIconIndex(1);
    }

    private void setRoamingOn() {
        this.mNewSS.setVoiceRoaming(true);
        this.mNewSS.setDataRoaming(true);
        this.mNewSS.setCdmaEriIconIndex(0);
        this.mNewSS.setCdmaEriIconMode(0);
    }

    @UnsupportedAppUsage
    private void setSignalStrengthDefaultValues() {
        this.mSignalStrength = new SignalStrength();
    }

    private void setTimeFromNITZString(String object, long l) {
        long l2 = SystemClock.elapsedRealtime();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("NITZ: ");
        stringBuilder.append((String)object);
        stringBuilder.append(",");
        stringBuilder.append(l);
        stringBuilder.append(" start=");
        stringBuilder.append(l2);
        stringBuilder.append(" delay=");
        stringBuilder.append(l2 - l);
        Rlog.d((String)LOG_TAG, (String)stringBuilder.toString());
        object = NitzData.parse((String)object);
        if (object != null) {
            try {
                stringBuilder = new TimestampedValue(l, object);
                this.mNitzState.handleNitzReceived((TimestampedValue<NitzData>)stringBuilder);
            }
            finally {
                l = SystemClock.elapsedRealtime();
                object = new StringBuilder();
                ((StringBuilder)object).append("NITZ: end=");
                ((StringBuilder)object).append(l);
                ((StringBuilder)object).append(" dur=");
                ((StringBuilder)object).append(l - l2);
                Rlog.d((String)LOG_TAG, (String)((StringBuilder)object).toString());
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void updateLteEarfcnLists(PersistableBundle persistableBundle) {
        Object object = this.mLteRsrpBoostLock;
        synchronized (object) {
            this.mLteRsrpBoost = persistableBundle.getInt("lte_earfcns_rsrp_boost_int", 0);
            this.mEarfcnPairListForRsrpBoost = this.convertEarfcnStringArrayToPairList(persistableBundle.getStringArray("boosted_lte_earfcns_string_array"));
            return;
        }
    }

    private boolean updateNrFrequencyRangeFromPhysicalChannelConfigs(List<PhysicalChannelConfig> physicalChannelConfig, ServiceState serviceState) {
        int n = -1;
        boolean bl = false;
        int n2 = n;
        if (physicalChannelConfig != null) {
            DcTracker dcTracker = this.mPhone.getDcTracker(1);
            Iterator<PhysicalChannelConfig> iterator = physicalChannelConfig.iterator();
            do {
                n2 = n;
                if (!iterator.hasNext()) break;
                physicalChannelConfig = iterator.next();
                n2 = n;
                if (this.isNrPhysicalChannelConfig(physicalChannelConfig)) {
                    int[] arrn = physicalChannelConfig.getContextIds();
                    int n3 = arrn.length;
                    int n4 = 0;
                    do {
                        n2 = n;
                        if (n4 >= n3) break;
                        DataConnection dataConnection = dcTracker.getDataConnectionByContextId(arrn[n4]);
                        if (dataConnection != null && dataConnection.getNetworkCapabilities().hasCapability(12)) {
                            n2 = ServiceState.getBetterNRFrequencyRange((int)n, (int)physicalChannelConfig.getFrequencyRange());
                            break;
                        }
                        ++n4;
                    } while (true);
                }
                n = n2;
            } while (true);
        }
        if (n2 != serviceState.getNrFrequencyRange()) {
            bl = true;
        }
        serviceState.setNrFrequencyRange(n2);
        return bl;
    }

    private boolean updateNrStateFromPhysicalChannelConfigs(List<PhysicalChannelConfig> object, ServiceState serviceState) {
        boolean bl = true;
        NetworkRegistrationInfo networkRegistrationInfo = serviceState.getNetworkRegistrationInfo(2, 1);
        if (networkRegistrationInfo != null && object != null) {
            int n;
            int n2;
            block7 : {
                PhysicalChannelConfig physicalChannelConfig;
                n = 0;
                object = object.iterator();
                do {
                    n2 = n;
                    if (!object.hasNext()) break block7;
                } while (!this.isNrPhysicalChannelConfig(physicalChannelConfig = (PhysicalChannelConfig)object.next()) || physicalChannelConfig.getConnectionStatus() != 2);
                n2 = 1;
            }
            n = networkRegistrationInfo.getNrState();
            if (n2 != 0) {
                n2 = n;
                if (networkRegistrationInfo.getNrState() == 2) {
                    n2 = 3;
                }
            } else {
                n2 = n;
                if (networkRegistrationInfo.getNrState() == 3) {
                    n2 = 2;
                }
            }
            if (n2 == networkRegistrationInfo.getNrState()) {
                bl = false;
            }
            networkRegistrationInfo.setNrState(n2);
            serviceState.addNetworkRegistrationInfo(networkRegistrationInfo);
            return bl;
        }
        return false;
    }

    private void updateOperatorNameForCellIdentity(CellIdentity cellIdentity) {
        if (cellIdentity == null) {
            return;
        }
        cellIdentity.setOperatorAlphaLong(this.filterOperatorNameByPattern((String)cellIdentity.getOperatorAlphaLong()));
        cellIdentity.setOperatorAlphaShort(this.filterOperatorNameByPattern((String)cellIdentity.getOperatorAlphaShort()));
    }

    private void updateOperatorNameForServiceState(ServiceState object) {
        if (object == null) {
            return;
        }
        object.setOperatorName(this.filterOperatorNameByPattern(object.getOperatorAlphaLong()), this.filterOperatorNameByPattern(object.getOperatorAlphaShort()), object.getOperatorNumeric());
        object = object.getNetworkRegistrationInfoList();
        for (int i = 0; i < object.size(); ++i) {
            if (object.get(i) == null) continue;
            this.updateOperatorNameForCellIdentity(((NetworkRegistrationInfo)object.get(i)).getCellIdentity());
        }
    }

    private void updateOperatorNameFromCarrierConfig() {
        Object object;
        boolean bl;
        if (!this.mPhone.isPhoneTypeGsm() && !this.mSS.getRoaming() && !(bl = this.mUiccController.getUiccCard(this.getPhoneId()) != null && this.mUiccController.getUiccCard(this.getPhoneId()).getOperatorBrandOverride() != null) && (object = this.getCarrierConfig()).getBoolean("cdma_home_registered_plmn_name_override_bool")) {
            object = object.getString("cdma_home_registered_plmn_name_string");
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("updateOperatorNameFromCarrierConfig: changing from ");
            stringBuilder.append(this.mSS.getOperatorAlpha());
            stringBuilder.append(" to ");
            stringBuilder.append((String)object);
            this.log(stringBuilder.toString());
            stringBuilder = this.mSS;
            stringBuilder.setOperatorName((String)object, (String)object, stringBuilder.getOperatorNumeric());
        }
    }

    private void updateOperatorNamePattern(PersistableBundle object) {
        if (!TextUtils.isEmpty((CharSequence)(object = object.getString("operator_name_filter_pattern_string")))) {
            this.mOperatorNameStringPattern = Pattern.compile((String)object);
            object = new StringBuilder();
            ((StringBuilder)object).append("mOperatorNameStringPattern: ");
            ((StringBuilder)object).append(this.mOperatorNameStringPattern.toString());
            this.log(((StringBuilder)object).toString());
        }
    }

    private void updateReportingCriteria(PersistableBundle persistableBundle) {
        this.mPhone.setSignalStrengthReportingCriteria(persistableBundle.getIntArray("lte_rsrp_thresholds_int_array"), 3);
        this.mPhone.setSignalStrengthReportingCriteria(persistableBundle.getIntArray("wcdma_rscp_thresholds_int_array"), 2);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void updateServiceStateLteEarfcnBoost(ServiceState serviceState, int n) {
        Object object = this.mLteRsrpBoostLock;
        synchronized (object) {
            if (n != -1 && this.containsEarfcnInEarfcnRange(this.mEarfcnPairListForRsrpBoost, n)) {
                serviceState.setLteEarfcnRsrpBoost(this.mLteRsrpBoost);
            } else {
                serviceState.setLteEarfcnRsrpBoost(0);
            }
            return;
        }
    }

    private void updateSpnDisplayCdnr() {
        this.log("updateSpnDisplayCdnr+");
        this.notifySpnDisplayUpdate(this.mCdnr.getCarrierDisplayNameData());
        this.log("updateSpnDisplayCdnr-");
    }

    private void updateSpnDisplayLegacy() {
        Object object;
        int n;
        int n2;
        boolean bl;
        Object object2;
        Object object3;
        Object object4;
        int n3;
        boolean bl2;
        int n4;
        Object object5;
        block28 : {
            block32 : {
                block31 : {
                    int n5;
                    block30 : {
                        block29 : {
                            this.log("updateSpnDisplayLegacy+");
                            bl = false;
                            object5 = null;
                            object3 = null;
                            object4 = null;
                            n4 = this.getCombinedRegState(this.mSS);
                            if (this.mPhone.getImsPhone() == null || !this.mPhone.getImsPhone().isWifiCallingEnabled() || n4 != 0) break block28;
                            object2 = this.getCarrierConfig();
                            n2 = object2.getInt("wfc_spn_format_idx_int");
                            n3 = object2.getInt("wfc_data_spn_format_idx_int");
                            n5 = object2.getInt("wfc_flight_mode_spn_format_idx_int");
                            bl2 = object2.getBoolean("wfc_spn_use_root_locale");
                            object2 = SubscriptionManager.getResourcesForSubId((Context)this.mPhone.getContext(), (int)this.mPhone.getSubId(), (boolean)bl2).getStringArray(17236116);
                            if (n2 < 0 || n2 >= ((Object)object2).length) {
                                object5 = new StringBuilder();
                                ((StringBuilder)object5).append("updateSpnDisplay: KEY_WFC_SPN_FORMAT_IDX_INT out of bounds: ");
                                ((StringBuilder)object5).append(n2);
                                this.loge(((StringBuilder)object5).toString());
                                n2 = 0;
                            }
                            if (n3 < 0) break block29;
                            n = n3;
                            if (n3 < ((Object)object2).length) break block30;
                        }
                        object5 = new StringBuilder();
                        ((StringBuilder)object5).append("updateSpnDisplay: KEY_WFC_DATA_SPN_FORMAT_IDX_INT out of bounds: ");
                        ((StringBuilder)object5).append(n3);
                        this.loge(((StringBuilder)object5).toString());
                        n = 0;
                    }
                    if (n5 < 0) break block31;
                    n3 = n5;
                    if (n5 < ((Object)object2).length) break block32;
                }
                n3 = n2;
            }
            object5 = object2[n2];
            object3 = object2[n];
            object4 = object2[n3];
        }
        if (this.mPhone.isPhoneTypeGsm()) {
            object2 = this.mIccRecords;
            n3 = this.getCarrierNameDisplayBitmask(this.mSS);
            n2 = 0;
            n = 0;
            if (n4 != 1 && n4 != 2) {
                if (n4 == 0) {
                    object = this.mSS.getOperatorAlpha();
                    bl2 = !TextUtils.isEmpty((CharSequence)object) && (n3 & 2) == 2;
                    object2 = new StringBuilder();
                    ((StringBuilder)object2).append("updateSpnDisplay: rawPlmn = ");
                    ((StringBuilder)object2).append((String)object);
                    this.log(((StringBuilder)object2).toString());
                } else {
                    bl2 = true;
                    object = Resources.getSystem().getText(17040246).toString();
                    object2 = new StringBuilder();
                    ((StringBuilder)object2).append("updateSpnDisplay: radio is off w/ showPlmn=");
                    ((StringBuilder)object2).append(true);
                    ((StringBuilder)object2).append(" plmn=");
                    ((StringBuilder)object2).append((String)object);
                    this.log(((StringBuilder)object2).toString());
                }
            } else {
                n2 = this.mPhone.getContext().getResources().getBoolean(17891414) && !this.mIsSimReady ? 1 : 0;
                if (this.mEmergencyOnly && n2 == 0) {
                    object = Resources.getSystem().getText(17039896).toString();
                    n2 = n;
                } else {
                    object = Resources.getSystem().getText(17040246).toString();
                    n2 = 1;
                }
                object2 = new StringBuilder();
                ((StringBuilder)object2).append("updateSpnDisplay: radio is on but out of service, set plmn='");
                ((StringBuilder)object2).append((String)object);
                ((StringBuilder)object2).append("'");
                this.log(((StringBuilder)object2).toString());
                bl2 = true;
            }
            object2 = this.getServiceProviderName();
            bl = n2 == 0 && !TextUtils.isEmpty((CharSequence)object2) && (n3 & 1) == 1;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("updateSpnDisplay: rawSpn = ");
            stringBuilder.append((String)object2);
            this.log(stringBuilder.toString());
            if (!(TextUtils.isEmpty((CharSequence)object2) || TextUtils.isEmpty((CharSequence)object5) || TextUtils.isEmpty((CharSequence)object3))) {
                if (this.mSS.getVoiceRegState() == 3) {
                    object5 = object4;
                }
                object4 = ((String)object2).trim();
                object2 = String.format((String)object5, object4);
                object5 = String.format((String)object3, object4);
                bl = true;
                bl2 = false;
            } else if (!TextUtils.isEmpty((CharSequence)object) && !TextUtils.isEmpty((CharSequence)object5)) {
                object4 = String.format((String)object5, ((String)object).trim());
                object = object2;
                object5 = object2;
                object2 = object;
                object = object4;
            } else if (!(this.mSS.getVoiceRegState() == 3 || bl2 && TextUtils.equals((CharSequence)object2, (CharSequence)object))) {
                object4 = object2;
                object5 = object2;
                object2 = object4;
            } else {
                bl = false;
                object4 = null;
                object5 = object2;
                object2 = object4;
            }
        } else {
            object2 = this.getOperatorNameFromEri();
            if (object2 != null) {
                this.mSS.setOperatorAlphaLong((String)object2);
            }
            this.updateOperatorNameFromCarrierConfig();
            object2 = this.mSS.getOperatorAlpha();
            object = new StringBuilder();
            ((StringBuilder)object).append("updateSpnDisplay: cdma rawPlmn = ");
            ((StringBuilder)object).append((String)object2);
            this.log(((StringBuilder)object).toString());
            bl2 = object2 != null;
            if (!TextUtils.isEmpty((CharSequence)object2) && !TextUtils.isEmpty(object5)) {
                object = String.format((String)object5, ((String)object2).trim());
            } else {
                object = object2;
                if (this.mCi.getRadioState() == 0) {
                    object5 = new StringBuilder();
                    ((StringBuilder)object5).append("updateSpnDisplay: overwriting plmn from ");
                    ((StringBuilder)object5).append((String)object2);
                    ((StringBuilder)object5).append(" to null as radio state is off");
                    this.log(((StringBuilder)object5).toString());
                    object = null;
                }
            }
            if (n4 == 1) {
                object = Resources.getSystem().getText(17040246).toString();
                object2 = new StringBuilder();
                ((StringBuilder)object2).append("updateSpnDisplay: radio is on but out of svc, set plmn='");
                ((StringBuilder)object2).append((String)object);
                ((StringBuilder)object2).append("'");
                this.log(((StringBuilder)object2).toString());
                object2 = null;
                object5 = null;
            } else {
                object2 = null;
                object5 = null;
            }
        }
        this.notifySpnDisplayUpdate(new CarrierDisplayNameData.Builder().setSpn((String)object2).setDataSpn((String)object5).setShowSpn(bl).setPlmn((String)object).setShowPlmn(bl2).build());
        this.log("updateSpnDisplayLegacy-");
    }

    protected final boolean alwaysOnHomeNetwork(BaseBundle baseBundle) {
        return baseBundle.getBoolean("force_home_network_bool");
    }

    protected void cancelPollState() {
        this.mPollingContext = new int[1];
    }

    protected void checkCorrectThread() {
        if (Thread.currentThread() == this.getLooper().getThread()) {
            return;
        }
        throw new RuntimeException("ServiceStateTracker must be used from within one thread");
    }

    ArrayList<Pair<Integer, Integer>> convertEarfcnStringArrayToPairList(String[] arrstring) {
        ArrayList<Pair<Integer, Integer>> arrayList = new ArrayList<Pair<Integer, Integer>>();
        if (arrstring != null) {
            for (int i = 0; i < arrstring.length; ++i) {
                int n;
                Pair pair;
                int n2;
                block8 : {
                    block7 : {
                        pair = arrstring[i].split("-");
                        if (((String[])pair).length == 2) break block7;
                        return null;
                    }
                    n2 = Integer.parseInt(pair[0]);
                    n = Integer.parseInt(pair[1]);
                    if (n2 <= n) break block8;
                    return null;
                }
                try {
                    pair = new Pair((Object)n2, (Object)n);
                    arrayList.add((Pair<Integer, Integer>)pair);
                    continue;
                }
                catch (NumberFormatException numberFormatException) {
                    return null;
                }
                catch (PatternSyntaxException patternSyntaxException) {
                    return null;
                }
            }
        }
        return arrayList;
    }

    public void disableLocationUpdates() {
        this.mWantContinuousLocationUpdates = false;
        if (!this.mWantSingleLocationUpdate && !this.mWantContinuousLocationUpdates) {
            this.mCi.setLocationUpdates(false, null);
        }
    }

    protected void disableSingleLocationUpdate() {
        this.mWantSingleLocationUpdate = false;
        if (!this.mWantSingleLocationUpdate && !this.mWantContinuousLocationUpdates) {
            this.mCi.setLocationUpdates(false, null);
        }
    }

    public void dispose() {
        this.mCi.unSetOnSignalStrengthUpdate(this);
        this.mUiccController.unregisterForIccChanged(this);
        this.mCi.unregisterForCellInfoList(this);
        this.mCi.unregisterForPhysicalChannelConfiguration(this);
        this.mSubscriptionManager.removeOnSubscriptionsChangedListener((SubscriptionManager.OnSubscriptionsChangedListener)this.mOnSubscriptionsChangedListener);
        this.mCi.unregisterForImsNetworkStateChanged(this);
        this.mPhone.getCarrierActionAgent().unregisterForCarrierAction(this, 1);
        CarrierServiceStateTracker carrierServiceStateTracker = this.mCSST;
        if (carrierServiceStateTracker != null) {
            carrierServiceStateTracker.dispose();
            this.mCSST = null;
        }
    }

    public void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] arrstring) {
        printWriter.println("ServiceStateTracker:");
        Object object = new StringBuilder();
        ((StringBuilder)object).append(" mSubId=");
        ((StringBuilder)object).append(this.mSubId);
        printWriter.println(((StringBuilder)object).toString());
        object = new StringBuilder();
        ((StringBuilder)object).append(" mSS=");
        ((StringBuilder)object).append((Object)this.mSS);
        printWriter.println(((StringBuilder)object).toString());
        object = new StringBuilder();
        ((StringBuilder)object).append(" mNewSS=");
        ((StringBuilder)object).append((Object)this.mNewSS);
        printWriter.println(((StringBuilder)object).toString());
        object = new StringBuilder();
        ((StringBuilder)object).append(" mVoiceCapable=");
        ((StringBuilder)object).append(this.mVoiceCapable);
        printWriter.println(((StringBuilder)object).toString());
        object = new StringBuilder();
        ((StringBuilder)object).append(" mRestrictedState=");
        ((StringBuilder)object).append(this.mRestrictedState);
        printWriter.println(((StringBuilder)object).toString());
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(" mPollingContext=");
        stringBuilder.append(this.mPollingContext);
        stringBuilder.append(" - ");
        object = this.mPollingContext;
        object = object != null ? Integer.valueOf(object[0]) : "";
        stringBuilder.append(object);
        printWriter.println(stringBuilder.toString());
        object = new StringBuilder();
        ((StringBuilder)object).append(" mDesiredPowerState=");
        ((StringBuilder)object).append(this.mDesiredPowerState);
        printWriter.println(((StringBuilder)object).toString());
        object = new StringBuilder();
        ((StringBuilder)object).append(" mDontPollSignalStrength=");
        ((StringBuilder)object).append(this.mDontPollSignalStrength);
        printWriter.println(((StringBuilder)object).toString());
        object = new StringBuilder();
        ((StringBuilder)object).append(" mSignalStrength=");
        ((StringBuilder)object).append((Object)this.mSignalStrength);
        printWriter.println(((StringBuilder)object).toString());
        object = new StringBuilder();
        ((StringBuilder)object).append(" mLastSignalStrength=");
        ((StringBuilder)object).append((Object)this.mLastSignalStrength);
        printWriter.println(((StringBuilder)object).toString());
        object = new StringBuilder();
        ((StringBuilder)object).append(" mRestrictedState=");
        ((StringBuilder)object).append(this.mRestrictedState);
        printWriter.println(((StringBuilder)object).toString());
        object = new StringBuilder();
        ((StringBuilder)object).append(" mPendingRadioPowerOffAfterDataOff=");
        ((StringBuilder)object).append(this.mPendingRadioPowerOffAfterDataOff);
        printWriter.println(((StringBuilder)object).toString());
        object = new StringBuilder();
        ((StringBuilder)object).append(" mPendingRadioPowerOffAfterDataOffTag=");
        ((StringBuilder)object).append(this.mPendingRadioPowerOffAfterDataOffTag);
        printWriter.println(((StringBuilder)object).toString());
        object = new StringBuilder();
        ((StringBuilder)object).append(" mCellIdentity=");
        ((StringBuilder)object).append(Rlog.pii((boolean)false, (Object)this.mCellIdentity));
        printWriter.println(((StringBuilder)object).toString());
        object = new StringBuilder();
        ((StringBuilder)object).append(" mNewCellIdentity=");
        ((StringBuilder)object).append(Rlog.pii((boolean)false, (Object)this.mNewCellIdentity));
        printWriter.println(((StringBuilder)object).toString());
        object = new StringBuilder();
        ((StringBuilder)object).append(" mLastCellInfoReqTime=");
        ((StringBuilder)object).append(this.mLastCellInfoReqTime);
        printWriter.println(((StringBuilder)object).toString());
        this.dumpCellInfoList(printWriter);
        printWriter.flush();
        object = new StringBuilder();
        ((StringBuilder)object).append(" mPreferredNetworkType=");
        ((StringBuilder)object).append(this.mPreferredNetworkType);
        printWriter.println(((StringBuilder)object).toString());
        object = new StringBuilder();
        ((StringBuilder)object).append(" mMaxDataCalls=");
        ((StringBuilder)object).append(this.mMaxDataCalls);
        printWriter.println(((StringBuilder)object).toString());
        object = new StringBuilder();
        ((StringBuilder)object).append(" mNewMaxDataCalls=");
        ((StringBuilder)object).append(this.mNewMaxDataCalls);
        printWriter.println(((StringBuilder)object).toString());
        object = new StringBuilder();
        ((StringBuilder)object).append(" mReasonDataDenied=");
        ((StringBuilder)object).append(this.mReasonDataDenied);
        printWriter.println(((StringBuilder)object).toString());
        object = new StringBuilder();
        ((StringBuilder)object).append(" mNewReasonDataDenied=");
        ((StringBuilder)object).append(this.mNewReasonDataDenied);
        printWriter.println(((StringBuilder)object).toString());
        object = new StringBuilder();
        ((StringBuilder)object).append(" mGsmRoaming=");
        ((StringBuilder)object).append(this.mGsmRoaming);
        printWriter.println(((StringBuilder)object).toString());
        object = new StringBuilder();
        ((StringBuilder)object).append(" mDataRoaming=");
        ((StringBuilder)object).append(this.mDataRoaming);
        printWriter.println(((StringBuilder)object).toString());
        object = new StringBuilder();
        ((StringBuilder)object).append(" mEmergencyOnly=");
        ((StringBuilder)object).append(this.mEmergencyOnly);
        printWriter.println(((StringBuilder)object).toString());
        printWriter.flush();
        this.mNitzState.dumpState(printWriter);
        printWriter.flush();
        object = new StringBuilder();
        ((StringBuilder)object).append(" mStartedGprsRegCheck=");
        ((StringBuilder)object).append(this.mStartedGprsRegCheck);
        printWriter.println(((StringBuilder)object).toString());
        object = new StringBuilder();
        ((StringBuilder)object).append(" mReportedGprsNoReg=");
        ((StringBuilder)object).append(this.mReportedGprsNoReg);
        printWriter.println(((StringBuilder)object).toString());
        object = new StringBuilder();
        ((StringBuilder)object).append(" mNotification=");
        ((StringBuilder)object).append((Object)this.mNotification);
        printWriter.println(((StringBuilder)object).toString());
        object = new StringBuilder();
        ((StringBuilder)object).append(" mCurSpn=");
        ((StringBuilder)object).append(this.mCurSpn);
        printWriter.println(((StringBuilder)object).toString());
        object = new StringBuilder();
        ((StringBuilder)object).append(" mCurDataSpn=");
        ((StringBuilder)object).append(this.mCurDataSpn);
        printWriter.println(((StringBuilder)object).toString());
        object = new StringBuilder();
        ((StringBuilder)object).append(" mCurShowSpn=");
        ((StringBuilder)object).append(this.mCurShowSpn);
        printWriter.println(((StringBuilder)object).toString());
        object = new StringBuilder();
        ((StringBuilder)object).append(" mCurPlmn=");
        ((StringBuilder)object).append(this.mCurPlmn);
        printWriter.println(((StringBuilder)object).toString());
        object = new StringBuilder();
        ((StringBuilder)object).append(" mCurShowPlmn=");
        ((StringBuilder)object).append(this.mCurShowPlmn);
        printWriter.println(((StringBuilder)object).toString());
        printWriter.flush();
        object = new StringBuilder();
        ((StringBuilder)object).append(" mCurrentOtaspMode=");
        ((StringBuilder)object).append(this.mCurrentOtaspMode);
        printWriter.println(((StringBuilder)object).toString());
        object = new StringBuilder();
        ((StringBuilder)object).append(" mRoamingIndicator=");
        ((StringBuilder)object).append(this.mRoamingIndicator);
        printWriter.println(((StringBuilder)object).toString());
        object = new StringBuilder();
        ((StringBuilder)object).append(" mIsInPrl=");
        ((StringBuilder)object).append(this.mIsInPrl);
        printWriter.println(((StringBuilder)object).toString());
        object = new StringBuilder();
        ((StringBuilder)object).append(" mDefaultRoamingIndicator=");
        ((StringBuilder)object).append(this.mDefaultRoamingIndicator);
        printWriter.println(((StringBuilder)object).toString());
        object = new StringBuilder();
        ((StringBuilder)object).append(" mRegistrationState=");
        ((StringBuilder)object).append(this.mRegistrationState);
        printWriter.println(((StringBuilder)object).toString());
        object = new StringBuilder();
        ((StringBuilder)object).append(" mMdn=");
        ((StringBuilder)object).append(this.mMdn);
        printWriter.println(((StringBuilder)object).toString());
        object = new StringBuilder();
        ((StringBuilder)object).append(" mHomeSystemId=");
        ((StringBuilder)object).append(this.mHomeSystemId);
        printWriter.println(((StringBuilder)object).toString());
        object = new StringBuilder();
        ((StringBuilder)object).append(" mHomeNetworkId=");
        ((StringBuilder)object).append(this.mHomeNetworkId);
        printWriter.println(((StringBuilder)object).toString());
        object = new StringBuilder();
        ((StringBuilder)object).append(" mMin=");
        ((StringBuilder)object).append(this.mMin);
        printWriter.println(((StringBuilder)object).toString());
        object = new StringBuilder();
        ((StringBuilder)object).append(" mPrlVersion=");
        ((StringBuilder)object).append(this.mPrlVersion);
        printWriter.println(((StringBuilder)object).toString());
        object = new StringBuilder();
        ((StringBuilder)object).append(" mIsMinInfoReady=");
        ((StringBuilder)object).append(this.mIsMinInfoReady);
        printWriter.println(((StringBuilder)object).toString());
        object = new StringBuilder();
        ((StringBuilder)object).append(" mIsEriTextLoaded=");
        ((StringBuilder)object).append(this.mIsEriTextLoaded);
        printWriter.println(((StringBuilder)object).toString());
        object = new StringBuilder();
        ((StringBuilder)object).append(" mIsSubscriptionFromRuim=");
        ((StringBuilder)object).append(this.mIsSubscriptionFromRuim);
        printWriter.println(((StringBuilder)object).toString());
        object = new StringBuilder();
        ((StringBuilder)object).append(" mCdmaSSM=");
        ((StringBuilder)object).append((Object)this.mCdmaSSM);
        printWriter.println(((StringBuilder)object).toString());
        object = new StringBuilder();
        ((StringBuilder)object).append(" mRegistrationDeniedReason=");
        ((StringBuilder)object).append(this.mRegistrationDeniedReason);
        printWriter.println(((StringBuilder)object).toString());
        object = new StringBuilder();
        ((StringBuilder)object).append(" mCurrentCarrier=");
        ((StringBuilder)object).append(this.mCurrentCarrier);
        printWriter.println(((StringBuilder)object).toString());
        printWriter.flush();
        object = new StringBuilder();
        ((StringBuilder)object).append(" mImsRegistered=");
        ((StringBuilder)object).append(this.mImsRegistered);
        printWriter.println(((StringBuilder)object).toString());
        object = new StringBuilder();
        ((StringBuilder)object).append(" mImsRegistrationOnOff=");
        ((StringBuilder)object).append(this.mImsRegistrationOnOff);
        printWriter.println(((StringBuilder)object).toString());
        object = new StringBuilder();
        ((StringBuilder)object).append(" mAlarmSwitch=");
        ((StringBuilder)object).append(this.mAlarmSwitch);
        printWriter.println(((StringBuilder)object).toString());
        object = new StringBuilder();
        ((StringBuilder)object).append(" mRadioDisabledByCarrier");
        ((StringBuilder)object).append(this.mRadioDisabledByCarrier);
        printWriter.println(((StringBuilder)object).toString());
        object = new StringBuilder();
        ((StringBuilder)object).append(" mPowerOffDelayNeed=");
        ((StringBuilder)object).append(this.mPowerOffDelayNeed);
        printWriter.println(((StringBuilder)object).toString());
        object = new StringBuilder();
        ((StringBuilder)object).append(" mDeviceShuttingDown=");
        ((StringBuilder)object).append(this.mDeviceShuttingDown);
        printWriter.println(((StringBuilder)object).toString());
        object = new StringBuilder();
        ((StringBuilder)object).append(" mSpnUpdatePending=");
        ((StringBuilder)object).append(this.mSpnUpdatePending);
        printWriter.println(((StringBuilder)object).toString());
        object = new StringBuilder();
        ((StringBuilder)object).append(" mLteRsrpBoost=");
        ((StringBuilder)object).append(this.mLteRsrpBoost);
        printWriter.println(((StringBuilder)object).toString());
        object = new StringBuilder();
        ((StringBuilder)object).append(" mCellInfoMinIntervalMs=");
        ((StringBuilder)object).append(this.mCellInfoMinIntervalMs);
        printWriter.println(((StringBuilder)object).toString());
        object = new StringBuilder();
        ((StringBuilder)object).append(" mEriManager=");
        ((StringBuilder)object).append(this.mEriManager);
        printWriter.println(((StringBuilder)object).toString());
        this.dumpEarfcnPairList(printWriter);
        this.mLocaleTracker.dump(fileDescriptor, printWriter, arrstring);
        printWriter = new IndentingPrintWriter((Writer)printWriter, "    ");
        this.mCdnr.dump((IndentingPrintWriter)printWriter);
        printWriter.println(" Carrier Display Name update records:");
        printWriter.increaseIndent();
        this.mCdnrLogs.dump(fileDescriptor, printWriter, arrstring);
        printWriter.decreaseIndent();
        printWriter.println(" Roaming Log:");
        printWriter.increaseIndent();
        this.mRoamingLog.dump(fileDescriptor, printWriter, arrstring);
        printWriter.decreaseIndent();
        printWriter.println(" Attach Log:");
        printWriter.increaseIndent();
        this.mAttachLog.dump(fileDescriptor, printWriter, arrstring);
        printWriter.decreaseIndent();
        printWriter.println(" Phone Change Log:");
        printWriter.increaseIndent();
        this.mPhoneTypeLog.dump(fileDescriptor, printWriter, arrstring);
        printWriter.decreaseIndent();
        printWriter.println(" Rat Change Log:");
        printWriter.increaseIndent();
        this.mRatLog.dump(fileDescriptor, printWriter, arrstring);
        printWriter.decreaseIndent();
        printWriter.println(" Radio power Log:");
        printWriter.increaseIndent();
        this.mRadioPowerLog.dump(fileDescriptor, printWriter, arrstring);
        printWriter.decreaseIndent();
        this.mNitzState.dumpLogs(fileDescriptor, (IndentingPrintWriter)printWriter, arrstring);
        printWriter.flush();
    }

    public void enableLocationUpdates() {
        if (!this.mWantSingleLocationUpdate && !this.mWantContinuousLocationUpdates) {
            this.mWantContinuousLocationUpdates = true;
            this.mCi.setLocationUpdates(true, this.obtainMessage(18));
            return;
        }
    }

    public void enableSingleLocationUpdate() {
        if (!this.mWantSingleLocationUpdate && !this.mWantContinuousLocationUpdates) {
            this.mWantSingleLocationUpdate = true;
            this.mCi.setLocationUpdates(true, this.obtainMessage(18));
            return;
        }
    }

    public String filterOperatorNameByPattern(String string) {
        if (this.mOperatorNameStringPattern != null && !TextUtils.isEmpty((CharSequence)string)) {
            Matcher matcher = this.mOperatorNameStringPattern.matcher(string);
            String string2 = string;
            if (matcher.find()) {
                if (matcher.groupCount() > 0) {
                    string2 = matcher.group(1);
                } else {
                    this.log("filterOperatorNameByPattern: pattern no group");
                    string2 = string;
                }
            }
            return string2;
        }
        return string;
    }

    public List<CellInfo> getAllCellInfo() {
        return this.mLastCellInfoList;
    }

    public int getCarrierNameDisplayBitmask(ServiceState serviceState) {
        int n;
        Object object = this.getCarrierConfig();
        if (!TextUtils.isEmpty((CharSequence)this.getOperatorBrandOverride())) {
            return 1;
        }
        if (TextUtils.isEmpty((CharSequence)this.getServiceProviderName())) {
            return 2;
        }
        boolean bl = object.getBoolean("spn_display_rule_use_roaming_from_service_state_bool");
        object = this.mIccRecords;
        int n2 = object == null ? 0 : ((IccRecords)object).getCarrierNameDisplayCondition();
        if (bl) {
            bl = serviceState.getRoaming();
        } else {
            object = this.mIccRecords;
            object = object != null ? ((IccRecords)object).getHomePlmns() : null;
            bl = ArrayUtils.contains((Object[])object, (Object)serviceState.getOperatorNumeric()) ^ true;
        }
        if (bl) {
            n = 2;
            if ((n2 & 2) == 2) {
                n = 2 | 1;
            }
        } else {
            n = (n2 & 1) == 1 ? 1 | 2 : 1;
        }
        return n;
    }

    String getCdmaEriText(int n, int n2) {
        return this.mEriManager.getCdmaEriText(n, n2);
    }

    public String getCdmaMin() {
        return this.mMin;
    }

    public CellLocation getCellLocation() {
        Object object = this.mCellIdentity;
        if (object != null) {
            return object.asCellLocation();
        }
        object = ServiceStateTracker.getCellLocationFromCellInfo(this.getAllCellInfo());
        if (object != null) {
            return object;
        }
        object = this.mPhone.getPhoneType() == 2 ? new CdmaCellLocation() : new GsmCellLocation();
        return object;
    }

    protected int getCombinedRegState(ServiceState serviceState) {
        int n;
        block5 : {
            int n2;
            int n3;
            block4 : {
                n2 = serviceState.getVoiceRegState();
                n3 = serviceState.getDataRegState();
                if (n2 == 1) break block4;
                n = n2;
                if (n2 != 3) break block5;
            }
            n = n2;
            if (n3 == 0) {
                this.log("getCombinedRegState: return STATE_IN_SERVICE as Data is in service");
                n = n3;
            }
        }
        return n;
    }

    @UnsupportedAppUsage
    public int getCurrentDataConnectionState() {
        return this.mSS.getDataRegState();
    }

    @UnsupportedAppUsage
    public boolean getDesiredPowerState() {
        return this.mDesiredPowerState;
    }

    protected String getHomeOperatorNumeric() {
        String string;
        String string2 = string = ((TelephonyManager)this.mPhone.getContext().getSystemService("phone")).getSimOperatorNumericForPhone(this.mPhone.getPhoneId());
        if (!this.mPhone.isPhoneTypeGsm()) {
            string2 = string;
            if (TextUtils.isEmpty((CharSequence)string)) {
                string2 = SystemProperties.get((String)"ro.cdma.home.operator.numeric", (String)"");
            }
        }
        return string2;
    }

    public String getImsi() {
        String string = ((TelephonyManager)this.mPhone.getContext().getSystemService("phone")).getSimOperatorNumericForPhone(this.mPhone.getPhoneId());
        if (!TextUtils.isEmpty((CharSequence)string) && this.getCdmaMin() != null) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(string);
            stringBuilder.append(this.getCdmaMin());
            return stringBuilder.toString();
        }
        return null;
    }

    public LocaleTracker getLocaleTracker() {
        return this.mLocaleTracker;
    }

    public String getMdnNumber() {
        return this.mMdn;
    }

    public int getOtasp() {
        int n;
        if (!this.mPhone.getIccRecordsLoaded()) {
            this.log("getOtasp: otasp uninitialized due to sim not loaded");
            return 0;
        }
        if (this.mPhone.isPhoneTypeGsm()) {
            this.log("getOtasp: otasp not needed for GSM");
            return 3;
        }
        if (this.mIsSubscriptionFromRuim && this.mMin == null) {
            return 2;
        }
        CharSequence charSequence = this.mMin;
        if (charSequence != null && ((String)charSequence).length() >= 6) {
            n = !(this.mMin.equals(UNACTIVATED_MIN_VALUE) || this.mMin.substring(0, 6).equals(UNACTIVATED_MIN2_VALUE) || SystemProperties.getBoolean((String)"test_cdma_setup", (boolean)false)) ? 3 : 2;
        } else {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("getOtasp: bad mMin='");
            ((StringBuilder)charSequence).append(this.mMin);
            ((StringBuilder)charSequence).append("'");
            this.log(((StringBuilder)charSequence).toString());
            n = 1;
        }
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append("getOtasp: state=");
        ((StringBuilder)charSequence).append(n);
        this.log(((StringBuilder)charSequence).toString());
        return n;
    }

    protected Phone getPhone() {
        return this.mPhone;
    }

    @UnsupportedAppUsage
    protected int getPhoneId() {
        return this.mPhone.getPhoneId();
    }

    public boolean getPowerStateFromCarrier() {
        return this.mRadioDisabledByCarrier ^ true;
    }

    public String getPrlVersion() {
        return this.mPrlVersion;
    }

    public String getServiceProviderName() {
        Object object = this.getOperatorBrandOverride();
        if (!TextUtils.isEmpty((CharSequence)object)) {
            return object;
        }
        object = this.mIccRecords;
        object = object != null ? ((IccRecords)object).getServiceProviderName() : "";
        PersistableBundle persistableBundle = this.getCarrierConfig();
        if (!persistableBundle.getBoolean("carrier_name_override_bool") && !TextUtils.isEmpty((CharSequence)object)) {
            return object;
        }
        return persistableBundle.getString("carrier_name_string");
    }

    public ServiceState getServiceState() {
        return new ServiceState(this.mSS);
    }

    public SignalStrength getSignalStrength() {
        return this.mSignalStrength;
    }

    @UnsupportedAppUsage
    public String getSystemProperty(String string, String string2) {
        return TelephonyManager.getTelephonyProperty((int)this.mPhone.getPhoneId(), (String)string, (String)string2);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void handleMessage(Message object) {
        int n = ((Message)object).what;
        boolean bl = false;
        switch (n) {
            default: {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Unhandled message with number: ");
                stringBuilder.append(((Message)object).what);
                this.log(stringBuilder.toString());
                return;
            }
            case 57: {
                this.onCarrierConfigChanged();
                return;
            }
            case 56: {
                object = (AsyncResult)((Message)object).obj;
                if (object == null) {
                    this.loge("Invalid null response to getCellLocation!");
                    return;
                }
                Message message = (Message)((AsyncResult)object).userObj;
                AsyncResult.forMessage((Message)message, (Object)this.getCellLocation(), (Throwable)((AsyncResult)object).exception);
                message.sendToTarget();
                return;
            }
            case 55: {
                object = (AsyncResult)((Message)object).obj;
                if (((AsyncResult)object).exception != null) return;
                object = (List)((AsyncResult)object).result;
                this.mPhone.notifyPhysicalChannelConfiguration((List<PhysicalChannelConfig>)object);
                this.mLastPhysicalChannelConfigList = object;
                if (!(this.updateNrFrequencyRangeFromPhysicalChannelConfigs((List<PhysicalChannelConfig>)object, this.mSS) | this.updateNrStateFromPhysicalChannelConfigs((List<PhysicalChannelConfig>)object, this.mSS))) {
                    if (!RatRatcheter.updateBandwidths(this.getBandwidthsFromConfigs((List<PhysicalChannelConfig>)object), this.mSS)) return;
                }
                this.mPhone.notifyServiceStateChanged(this.mSS);
                return;
            }
            case 54: {
                this.log("EVENT_RADIO_POWER_OFF_DONE");
                if (!this.mDeviceShuttingDown) return;
                if (this.mCi.getRadioState() == 2) return;
                this.mCi.requestShutdown(null);
                return;
            }
            case 53: {
                this.log("EVENT_IMS_SERVICE_STATE_CHANGED");
                if (this.mSS.getState() == 0) return;
                object = this.mPhone;
                ((GsmCdmaPhone)object).notifyServiceStateChanged(((GsmCdmaPhone)object).getServiceState());
                return;
            }
            case 51: {
                object = (AsyncResult)((Message)object).obj;
                if (((AsyncResult)object).exception != null) return;
                bl = (Boolean)((AsyncResult)object).result;
                object = new StringBuilder();
                ((StringBuilder)object).append("EVENT_RADIO_POWER_FROM_CARRIER: ");
                ((StringBuilder)object).append(bl);
                this.log(((StringBuilder)object).toString());
                this.setRadioPowerFromCarrier(bl);
                return;
            }
            case 49: {
                n = SubscriptionManager.getDefaultDataSubscriptionId();
                ProxyController.getInstance().unregisterForAllDataDisconnected(n, this);
                synchronized (this) {
                    if (this.mPendingRadioPowerOffAfterDataOff) {
                        this.log("EVENT_ALL_DATA_DISCONNECTED, turn radio off now.");
                        this.hangupAndPowerOff();
                        this.mPendingRadioPowerOffAfterDataOff = false;
                    } else {
                        this.log("EVENT_ALL_DATA_DISCONNECTED is stale");
                    }
                    return;
                }
            }
            case 48: {
                this.log("EVENT_IMS_CAPABILITY_CHANGED");
                this.updateSpnDisplay();
                this.mImsCapabilityChangedRegistrants.notifyRegistrants();
                return;
            }
            case 47: {
                object = (AsyncResult)((Message)object).obj;
                if (((AsyncResult)object).exception != null) return;
                if (((int[])((AsyncResult)object).result)[0] == 1) {
                    bl = true;
                }
                this.mImsRegistered = bl;
                return;
            }
            case 46: {
                this.mCi.getImsRegistrationState(this.obtainMessage(47));
                return;
            }
            case 45: {
                this.log("EVENT_CHANGE_IMS_STATE:");
                this.setPowerStateToDesired();
                return;
            }
            case 43: 
            case 44: {
                List<Message> list = null;
                Serializable serializable = null;
                if (((Message)object).obj != null) {
                    object = (AsyncResult)((Message)object).obj;
                    if (((AsyncResult)object).exception != null) {
                        serializable = new StringBuilder();
                        serializable.append("EVENT_GET_CELL_INFO_LIST: error ret null, e=");
                        serializable.append(((AsyncResult)object).exception);
                        this.log(serializable.toString());
                        serializable = ((AsyncResult)object).exception;
                        object = list;
                    } else if (((AsyncResult)object).result == null) {
                        this.loge("Invalid CellInfo result");
                        object = list;
                    } else {
                        object = (List)((AsyncResult)object).result;
                        this.updateOperatorNameForCellInfo((List<CellInfo>)object);
                        this.mLastCellInfoList = object;
                        this.mPhone.notifyCellInfo((List<CellInfo>)object);
                    }
                } else {
                    if (!this.mIsPendingCellInfoRequest) {
                        return;
                    }
                    if (SystemClock.elapsedRealtime() - this.mLastCellInfoReqTime < 2000L) {
                        return;
                    }
                    this.loge("Timeout waiting for CellInfo; (everybody panic)!");
                    this.mLastCellInfoList = null;
                    object = list;
                }
                list = this.mPendingCellInfoRequests;
                synchronized (list) {
                    if (!this.mIsPendingCellInfoRequest) return;
                    this.mIsPendingCellInfoRequest = false;
                    Iterator<Message> iterator = this.mPendingCellInfoRequests.iterator();
                    do {
                        if (!iterator.hasNext()) {
                            this.mPendingCellInfoRequests.clear();
                            return;
                        }
                        Message message = iterator.next();
                        AsyncResult.forMessage((Message)message, (Object)object, (Throwable)serializable);
                        message.sendToTarget();
                    } while (true);
                }
            }
            case 42: {
                if (this.isSimAbsent()) {
                    this.log("EVENT_ICC_CHANGED: SIM absent");
                    this.cancelAllNotifications();
                    this.mMdn = null;
                    this.mMin = null;
                    this.mIsMinInfoReady = false;
                    this.mCdnr.updateEfFromRuim(null);
                    this.mCdnr.updateEfFromUsim(null);
                }
                this.onUpdateIccAvailability();
                object = this.mUiccApplcation;
                if (object == null) return;
                if (((UiccCardApplication)object).getState() == IccCardApplicationStatus.AppState.APPSTATE_READY) return;
                this.mIsSimReady = false;
                this.updateSpnDisplay();
                return;
            }
            case 40: {
                object = (AsyncResult)((Message)object).obj;
                if (((AsyncResult)object).exception != null) return;
                this.mPrlVersion = Integer.toString(((int[])((AsyncResult)object).result)[0]);
                return;
            }
            case 39: {
                this.handleCdmaSubscriptionSource(this.mCdmaSSM.getCdmaSubscriptionSource());
                return;
            }
            case 38: {
                synchronized (this) {
                    if (this.mPendingRadioPowerOffAfterDataOff && ((Message)object).arg1 == this.mPendingRadioPowerOffAfterDataOffTag) {
                        this.log("EVENT_SET_RADIO_OFF, turn radio off now.");
                        this.hangupAndPowerOff();
                        ++this.mPendingRadioPowerOffAfterDataOffTag;
                        this.mPendingRadioPowerOffAfterDataOff = false;
                    } else {
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("EVENT_SET_RADIO_OFF is stale arg1=");
                        stringBuilder.append(((Message)object).arg1);
                        stringBuilder.append("!= tag=");
                        stringBuilder.append(this.mPendingRadioPowerOffAfterDataOffTag);
                        this.log(stringBuilder.toString());
                    }
                    return;
                }
            }
            case 37: {
                object = (AsyncResult)((Message)object).obj;
                if (((AsyncResult)object).exception != null) return;
                n = ((int[])((AsyncResult)object).result)[0];
                if (n != 8) {
                    if (n != 10) return;
                }
                this.log("EVENT_OTA_PROVISION_STATUS_CHANGE: Complete, Reload MDN");
                this.mCi.getCDMASubscription(this.obtainMessage(34));
                return;
            }
            case 35: {
                this.updatePhoneObject();
                this.mCi.getNetworkSelectionMode(this.obtainMessage(14));
                this.getSubscriptionInfoAndStartPollingThreads();
                return;
            }
            case 34: {
                if (this.mPhone.isPhoneTypeGsm()) return;
                object = (AsyncResult)((Message)object).obj;
                if (((AsyncResult)object).exception != null) return;
                object = (String[])((AsyncResult)object).result;
                if (object != null && ((Object)object).length >= 5) {
                    this.mMdn = object[0];
                    this.parseSidNid((String)object[1], (String)object[2]);
                    this.mMin = object[3];
                    this.mPrlVersion = object[4];
                    object = new StringBuilder();
                    ((StringBuilder)object).append("GET_CDMA_SUBSCRIPTION: MDN=");
                    ((StringBuilder)object).append(this.mMdn);
                    this.log(((StringBuilder)object).toString());
                    this.mIsMinInfoReady = true;
                    this.updateOtaspState();
                    this.notifyCdmaSubscriptionInfoReady();
                    if (!this.mIsSubscriptionFromRuim && this.mIccRecords != null) {
                        this.log("GET_CDMA_SUBSCRIPTION set imsi in mIccRecords");
                        this.mIccRecords.setImsi(this.getImsi());
                        return;
                    }
                    this.log("GET_CDMA_SUBSCRIPTION either mIccRecords is null or NV type device - not setting Imsi in mIccRecords");
                    return;
                }
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("GET_CDMA_SUBSCRIPTION: error parsing cdmaSubscription params num=");
                stringBuilder.append(((Object)object).length);
                this.log(stringBuilder.toString());
                return;
            }
            case 27: {
                if (this.mPhone.isPhoneTypeGsm()) return;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("EVENT_RUIM_RECORDS_LOADED: what=");
                stringBuilder.append(((Message)object).what);
                this.log(stringBuilder.toString());
                this.mCdnr.updateEfFromRuim((RuimRecords)this.mIccRecords);
                this.updatePhoneObject();
                if (this.mPhone.isPhoneTypeCdma()) {
                    this.updateSpnDisplay();
                    return;
                }
                object = (RuimRecords)this.mIccRecords;
                if (object != null) {
                    this.mMdn = ((RuimRecords)object).getMdn();
                    if (((RuimRecords)object).isProvisioned()) {
                        this.mMin = ((RuimRecords)object).getMin();
                        this.parseSidNid(((RuimRecords)object).getSid(), ((RuimRecords)object).getNid());
                        this.mPrlVersion = ((RuimRecords)object).getPrlVersion();
                        this.mIsMinInfoReady = true;
                    }
                    this.updateOtaspState();
                    this.notifyCdmaSubscriptionInfoReady();
                }
                this.pollState();
                return;
            }
            case 26: {
                if (this.mPhone.getLteOnCdmaMode() == 1) {
                    this.log("Receive EVENT_RUIM_READY");
                    this.pollState();
                } else {
                    this.log("Receive EVENT_RUIM_READY and Send Request getCDMASubscription.");
                    this.getSubscriptionInfoAndStartPollingThreads();
                }
                this.mCi.getNetworkSelectionMode(this.obtainMessage(14));
                return;
            }
            case 23: {
                if (!this.mPhone.isPhoneTypeGsm()) return;
                this.log("EVENT_RESTRICTED_STATE_CHANGED");
                this.onRestrictedStateChanged((AsyncResult)((Message)object).obj);
                return;
            }
            case 22: {
                if (this.mPhone.isPhoneTypeGsm() && (object = this.mSS) != null && !this.isGprsConsistent(object.getDataRegState(), this.mSS.getVoiceRegState())) {
                    EventLog.writeEvent((int)50107, (Object[])new Object[]{this.mSS.getOperatorNumeric(), ServiceStateTracker.getCidFromCellIdentity(this.mCellIdentity)});
                    this.mReportedGprsNoReg = true;
                }
                this.mStartedGprsRegCheck = false;
                return;
            }
            case 21: {
                object = (AsyncResult)((Message)object).obj;
                if (((AsyncResult)object).userObj == null) return;
                AsyncResult.forMessage((Message)((Message)object.userObj)).exception = ((AsyncResult)object).exception;
                ((Message)((AsyncResult)object).userObj).sendToTarget();
                return;
            }
            case 20: {
                object = this.obtainMessage(21, ((AsyncResult)object.obj).userObj);
                this.mCi.setPreferredNetworkType(this.mPreferredNetworkType, (Message)object);
                return;
            }
            case 19: {
                object = (AsyncResult)((Message)object).obj;
                this.mPreferredNetworkType = ((AsyncResult)object).exception == null ? ((int[])((AsyncResult)object).result)[0] : 7;
                object = this.obtainMessage(20, ((AsyncResult)object).userObj);
                this.mCi.setPreferredNetworkType(7, (Message)object);
                return;
            }
            case 18: {
                if (((AsyncResult)object.obj).exception != null) return;
                ((NetworkRegistrationManager)((Object)this.mRegStateManagers.get(1))).requestNetworkRegistrationInfo(1, this.obtainMessage(15, null));
                return;
            }
            case 17: {
                this.mOnSubscriptionsChangedListener.mPreviousSubId.set(-1);
                this.mPrevSubId = -1;
                this.mIsSimReady = true;
                this.pollState();
                this.queueNextSignalStrengthPoll();
                return;
            }
            case 16: {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("EVENT_SIM_RECORDS_LOADED: what=");
                stringBuilder.append(((Message)object).what);
                this.log(stringBuilder.toString());
                this.updatePhoneObject();
                this.updateOtaspState();
                if (!this.mPhone.isPhoneTypeGsm()) return;
                this.mCdnr.updateEfFromUsim((SIMRecords)this.mIccRecords);
                this.updateSpnDisplay();
                return;
            }
            case 15: {
                object = (AsyncResult)((Message)object).obj;
                if (((AsyncResult)object).exception == null) {
                    object = ((NetworkRegistrationInfo)((AsyncResult)object).result).getCellIdentity();
                    this.updateOperatorNameForCellIdentity((CellIdentity)object);
                    this.mCellIdentity = object;
                    this.mPhone.notifyLocationChanged(this.getCellLocation());
                }
                this.disableSingleLocationUpdate();
                return;
            }
            case 14: {
                this.log("EVENT_POLL_STATE_NETWORK_SELECTION_MODE");
                AsyncResult asyncResult = (AsyncResult)((Message)object).obj;
                if (this.mPhone.isPhoneTypeGsm()) {
                    this.handlePollStateResult(((Message)object).what, asyncResult);
                    return;
                }
                if (asyncResult.exception == null && asyncResult.result != null) {
                    if (((int[])asyncResult.result)[0] != 1) return;
                    this.mPhone.setNetworkSelectionModeAutomatic(null);
                    return;
                }
                this.log("Unable to getNetworkSelectionMode");
                return;
            }
            case 12: {
                object = (AsyncResult)((Message)object).obj;
                this.mDontPollSignalStrength = true;
                this.onSignalStrengthResult((AsyncResult)object);
                return;
            }
            case 11: {
                object = (AsyncResult)((Message)object).obj;
                this.setTimeFromNITZString((String)((Object[])((AsyncResult)object).result)[0], (Long)((Object[])((AsyncResult)object).result)[1]);
                return;
            }
            case 10: {
                this.mCi.getSignalStrength(this.obtainMessage(3));
                return;
            }
            case 4: 
            case 5: 
            case 6: 
            case 7: {
                AsyncResult asyncResult = (AsyncResult)((Message)object).obj;
                this.handlePollStateResult(((Message)object).what, asyncResult);
                return;
            }
            case 3: {
                if (this.mCi.getRadioState() != 1) {
                    return;
                }
                this.onSignalStrengthResult((AsyncResult)((Message)object).obj);
                this.queueNextSignalStrengthPoll();
                return;
            }
            case 2: {
                this.modemTriggeredPollState();
                return;
            }
            case 1: 
            case 50: 
        }
        if (!this.mPhone.isPhoneTypeGsm() && this.mCi.getRadioState() == 1) {
            this.handleCdmaSubscriptionSource(this.mCdmaSSM.getCdmaSubscriptionSource());
            this.queueNextSignalStrengthPoll();
        }
        this.setPowerStateToDesired();
        this.modemTriggeredPollState();
    }

    protected void handlePollStateResult(int n, AsyncResult object) {
        if (((AsyncResult)object).userObj != this.mPollingContext) {
            return;
        }
        if (((AsyncResult)object).exception != null) {
            Object object2 = null;
            if (((AsyncResult)object).exception instanceof IllegalStateException) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("handlePollStateResult exception ");
                stringBuilder.append(((AsyncResult)object).exception);
                this.log(stringBuilder.toString());
            }
            if (((AsyncResult)object).exception instanceof CommandException) {
                object2 = ((CommandException)((AsyncResult)object).exception).getCommandError();
            }
            if (object2 == CommandException.Error.RADIO_NOT_AVAILABLE) {
                this.cancelPollState();
                return;
            }
            if (object2 != CommandException.Error.OP_NOT_ALLOWED_BEFORE_REG_NW) {
                object2 = new StringBuilder();
                ((StringBuilder)object2).append("RIL implementation has returned an error where it must succeed");
                ((StringBuilder)object2).append(((AsyncResult)object).exception);
                this.loge(((StringBuilder)object2).toString());
            }
        } else {
            try {
                this.handlePollStateResultMessage(n, (AsyncResult)object);
            }
            catch (RuntimeException runtimeException) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Exception while polling service state. Probably malformed RIL response.");
                stringBuilder.append(runtimeException);
                this.loge(stringBuilder.toString());
            }
        }
        object = this.mPollingContext;
        n = 0;
        object[0] = object[0] - 1;
        if (object[0] == 0) {
            this.mNewSS.setEmergencyOnly(this.mEmergencyOnly);
            this.combinePsRegistrationStates(this.mNewSS);
            this.updateOperatorNameForServiceState(this.mNewSS);
            if (this.mPhone.isPhoneTypeGsm()) {
                this.updateRoamingState();
            } else {
                boolean bl;
                boolean bl2 = bl = false;
                if (!this.isSidsAllZeros()) {
                    bl2 = bl;
                    if (this.isHomeSid(this.mNewSS.getCdmaSystemId())) {
                        bl2 = true;
                    }
                }
                if (this.mIsSubscriptionFromRuim && (bl = this.isRoamingBetweenOperators(this.mNewSS.getVoiceRoaming(), this.mNewSS)) != this.mNewSS.getVoiceRoaming()) {
                    object = new StringBuilder();
                    ((StringBuilder)object).append("isRoamingBetweenOperators=");
                    ((StringBuilder)object).append(bl);
                    ((StringBuilder)object).append(". Override CDMA voice roaming to ");
                    ((StringBuilder)object).append(bl);
                    this.log(((StringBuilder)object).toString());
                    this.mNewSS.setVoiceRoaming(bl);
                }
                if (ServiceState.isCdma((int)this.mNewSS.getRilDataRadioTechnology())) {
                    if (this.mNewSS.getVoiceRegState() == 0) {
                        n = 1;
                    }
                    if (n != 0) {
                        bl = this.mNewSS.getVoiceRoaming();
                        if (this.mNewSS.getDataRoaming() != bl) {
                            object = new StringBuilder();
                            ((StringBuilder)object).append("Data roaming != Voice roaming. Override data roaming to ");
                            ((StringBuilder)object).append(bl);
                            this.log(((StringBuilder)object).toString());
                            this.mNewSS.setDataRoaming(bl);
                        }
                    } else {
                        bl = this.isRoamIndForHomeSystem(this.mRoamingIndicator);
                        if (this.mNewSS.getDataRoaming() == bl) {
                            object = new StringBuilder();
                            ((StringBuilder)object).append("isRoamIndForHomeSystem=");
                            ((StringBuilder)object).append(bl);
                            ((StringBuilder)object).append(", override data roaming to ");
                            ((StringBuilder)object).append(bl ^ true);
                            this.log(((StringBuilder)object).toString());
                            this.mNewSS.setDataRoaming(bl ^ true);
                        }
                    }
                }
                this.mNewSS.setCdmaDefaultRoamingIndicator(this.mDefaultRoamingIndicator);
                this.mNewSS.setCdmaRoamingIndicator(this.mRoamingIndicator);
                bl = true;
                if (TextUtils.isEmpty((CharSequence)this.mPrlVersion)) {
                    bl = false;
                }
                if (bl && this.mNewSS.getRilVoiceRadioTechnology() != 0) {
                    if (!this.isSidsAllZeros()) {
                        if (!bl2 && !this.mIsInPrl) {
                            this.mNewSS.setCdmaRoamingIndicator(this.mDefaultRoamingIndicator);
                        } else if (bl2 && !this.mIsInPrl) {
                            if (ServiceState.isLte((int)this.mNewSS.getRilVoiceRadioTechnology())) {
                                this.log("Turn off roaming indicator as voice is LTE");
                                this.mNewSS.setCdmaRoamingIndicator(1);
                            } else {
                                this.mNewSS.setCdmaRoamingIndicator(2);
                            }
                        } else if (!bl2 && this.mIsInPrl) {
                            this.mNewSS.setCdmaRoamingIndicator(this.mRoamingIndicator);
                        } else {
                            n = this.mRoamingIndicator;
                            if (n <= 2) {
                                this.mNewSS.setCdmaRoamingIndicator(1);
                            } else {
                                this.mNewSS.setCdmaRoamingIndicator(n);
                            }
                        }
                    }
                } else {
                    this.log("Turn off roaming indicator if !isPrlLoaded or voice RAT is unknown");
                    this.mNewSS.setCdmaRoamingIndicator(1);
                }
                n = this.mNewSS.getCdmaRoamingIndicator();
                this.mNewSS.setCdmaEriIconIndex(this.mEriManager.getCdmaEriIconIndex(n, this.mDefaultRoamingIndicator));
                this.mNewSS.setCdmaEriIconMode(this.mEriManager.getCdmaEriIconMode(n, this.mDefaultRoamingIndicator));
                object = new StringBuilder();
                ((StringBuilder)object).append("Set CDMA Roaming Indicator to: ");
                ((StringBuilder)object).append(this.mNewSS.getCdmaRoamingIndicator());
                ((StringBuilder)object).append(". voiceRoaming = ");
                ((StringBuilder)object).append(this.mNewSS.getVoiceRoaming());
                ((StringBuilder)object).append(". dataRoaming = ");
                ((StringBuilder)object).append(this.mNewSS.getDataRoaming());
                ((StringBuilder)object).append(", isPrlLoaded = ");
                ((StringBuilder)object).append(bl);
                ((StringBuilder)object).append(". namMatch = ");
                ((StringBuilder)object).append(bl2);
                ((StringBuilder)object).append(" , mIsInPrl = ");
                ((StringBuilder)object).append(this.mIsInPrl);
                ((StringBuilder)object).append(", mRoamingIndicator = ");
                ((StringBuilder)object).append(this.mRoamingIndicator);
                ((StringBuilder)object).append(", mDefaultRoamingIndicator= ");
                ((StringBuilder)object).append(this.mDefaultRoamingIndicator);
                this.log(((StringBuilder)object).toString());
            }
            this.pollStateDone();
        }
    }

    void handlePollStateResultMessage(int n, AsyncResult object) {
        if (n != 4) {
            if (n != 5) {
                if (n != 6) {
                    if (n != 7) {
                        if (n != 14) {
                            object = new StringBuilder();
                            ((StringBuilder)object).append("handlePollStateResultMessage: Unexpected RIL response received: ");
                            ((StringBuilder)object).append(n);
                            this.loge(((StringBuilder)object).toString());
                        } else {
                            int[] arrn = (int[])((AsyncResult)object).result;
                            object = this.mNewSS;
                            boolean bl = arrn[0] == 1;
                            object.setIsManualSelection(bl);
                            if (arrn[0] == 1 && this.mPhone.shouldForceAutoNetworkSelect()) {
                                this.mPhone.setNetworkSelectionModeAutomatic(null);
                                this.log(" Forcing Automatic Network Selection, manual selection is not allowed");
                            }
                        }
                    } else {
                        String string = this.getOperatorBrandOverride();
                        this.mCdnr.updateEfForBrandOverride(string);
                        if (this.mPhone.isPhoneTypeGsm()) {
                            String[] arrstring = (String[])((AsyncResult)object).result;
                            if (arrstring != null && arrstring.length >= 3) {
                                this.mNewSS.setOperatorAlphaLongRaw(arrstring[0]);
                                this.mNewSS.setOperatorAlphaShortRaw(arrstring[1]);
                                if (string != null) {
                                    object = new StringBuilder();
                                    ((StringBuilder)object).append("EVENT_POLL_STATE_OPERATOR: use brandOverride=");
                                    ((StringBuilder)object).append(string);
                                    this.log(((StringBuilder)object).toString());
                                    this.mNewSS.setOperatorName(string, string, arrstring[2]);
                                } else {
                                    this.mNewSS.setOperatorName(arrstring[0], arrstring[1], arrstring[2]);
                                }
                            }
                        } else {
                            String[] arrstring = (String[])((AsyncResult)object).result;
                            if (arrstring != null && arrstring.length >= 3) {
                                if (arrstring[2] == null || arrstring[2].length() < 5 || "00000".equals(arrstring[2])) {
                                    arrstring[2] = SystemProperties.get((String)"ro.cdma.home.operator.numeric", (String)"00000");
                                    object = new StringBuilder();
                                    ((StringBuilder)object).append("RIL_REQUEST_OPERATOR.response[2], the numeric,  is bad. Using SystemProperties 'ro.cdma.home.operator.numeric'= ");
                                    ((StringBuilder)object).append(arrstring[2]);
                                    this.log(((StringBuilder)object).toString());
                                }
                                if (!this.mIsSubscriptionFromRuim) {
                                    this.mNewSS.setOperatorName(arrstring[0], arrstring[1], arrstring[2]);
                                } else if (string != null) {
                                    this.mNewSS.setOperatorName(string, string, arrstring[2]);
                                } else {
                                    this.mNewSS.setOperatorName(arrstring[0], arrstring[1], arrstring[2]);
                                }
                            } else {
                                this.log("EVENT_POLL_STATE_OPERATOR_CDMA: error parsing opNames");
                            }
                        }
                    }
                } else {
                    NetworkRegistrationInfo networkRegistrationInfo = (NetworkRegistrationInfo)((AsyncResult)object).result;
                    this.mNewSS.addNetworkRegistrationInfo(networkRegistrationInfo);
                    object = new StringBuilder();
                    ((StringBuilder)object).append("handlePollStateResultMessage: PS IWLAN. ");
                    ((StringBuilder)object).append((Object)networkRegistrationInfo);
                    this.log(((StringBuilder)object).toString());
                }
            } else {
                NetworkRegistrationInfo networkRegistrationInfo = (NetworkRegistrationInfo)((AsyncResult)object).result;
                this.mNewSS.addNetworkRegistrationInfo(networkRegistrationInfo);
                DataSpecificRegistrationInfo dataSpecificRegistrationInfo = networkRegistrationInfo.getDataSpecificInfo();
                n = networkRegistrationInfo.getRegistrationState();
                int n2 = this.regCodeToServiceState(n);
                int n3 = ServiceState.networkTypeToRilRadioTechnology((int)networkRegistrationInfo.getAccessNetworkTechnology());
                object = new StringBuilder();
                ((StringBuilder)object).append("handlePollStateResultMessage: PS cellular. ");
                ((StringBuilder)object).append((Object)networkRegistrationInfo);
                this.log(((StringBuilder)object).toString());
                if (n2 == 1) {
                    this.mLastPhysicalChannelConfigList = null;
                    this.updateNrFrequencyRangeFromPhysicalChannelConfigs(null, this.mNewSS);
                }
                this.updateNrStateFromPhysicalChannelConfigs(this.mLastPhysicalChannelConfigList, this.mNewSS);
                this.setPhyCellInfoFromCellIdentity(this.mNewSS, networkRegistrationInfo.getCellIdentity());
                if (this.mPhone.isPhoneTypeGsm()) {
                    this.mNewReasonDataDenied = networkRegistrationInfo.getRejectCause();
                    this.mNewMaxDataCalls = dataSpecificRegistrationInfo.maxDataCalls;
                    this.mDataRoaming = this.regCodeIsRoaming(n);
                } else if (this.mPhone.isPhoneTypeCdma()) {
                    boolean bl = this.regCodeIsRoaming(n);
                    this.mNewSS.setDataRoaming(bl);
                } else {
                    n2 = this.mSS.getRilDataRadioTechnology();
                    if (n2 == 0 && n3 != 0 || ServiceState.isCdma((int)n2) && ServiceState.isLte((int)n3) || ServiceState.isLte((int)n2) && ServiceState.isCdma((int)n3)) {
                        this.mCi.getSignalStrength(this.obtainMessage(3));
                    }
                    boolean bl = this.regCodeIsRoaming(n);
                    this.mNewSS.setDataRoaming(bl);
                }
                this.updateServiceStateLteEarfcnBoost(this.mNewSS, this.getLteEarfcn(networkRegistrationInfo.getCellIdentity()));
            }
        } else {
            object = (NetworkRegistrationInfo)((AsyncResult)object).result;
            Object object2 = object.getVoiceSpecificInfo();
            n = object.getRegistrationState();
            int n4 = ((VoiceSpecificRegistrationInfo)object2).cssSupported;
            ServiceState.networkTypeToRilRadioTechnology((int)object.getAccessNetworkTechnology());
            this.mNewSS.setVoiceRegState(this.regCodeToServiceState(n));
            this.mNewSS.setCssIndicator(n4);
            this.mNewSS.addNetworkRegistrationInfo((NetworkRegistrationInfo)object);
            this.setPhyCellInfoFromCellIdentity(this.mNewSS, object.getCellIdentity());
            int n5 = object.getRejectCause();
            this.mEmergencyOnly = object.isEmergencyEnabled();
            if (this.mPhone.isPhoneTypeGsm()) {
                this.mGsmRoaming = this.regCodeIsRoaming(n);
                this.mNewRejectCode = n5;
                this.mPhone.getContext().getResources().getBoolean(17891570);
            } else {
                n4 = ((VoiceSpecificRegistrationInfo)object2).roamingIndicator;
                int n6 = ((VoiceSpecificRegistrationInfo)object2).systemIsInPrl;
                int n7 = ((VoiceSpecificRegistrationInfo)object2).defaultRoamingIndicator;
                this.mRegistrationState = n;
                boolean bl = this.regCodeIsRoaming(n) && !this.isRoamIndForHomeSystem(n4);
                this.mNewSS.setVoiceRoaming(bl);
                this.mRoamingIndicator = n4;
                bl = n6 != 0;
                this.mIsInPrl = bl;
                this.mDefaultRoamingIndicator = n7;
                n = 0;
                object2 = object.getCellIdentity();
                if (object2 != null && object2.getType() == 2) {
                    n = ((CellIdentityCdma)object2).getSystemId();
                    n4 = ((CellIdentityCdma)object2).getNetworkId();
                } else {
                    n4 = 0;
                }
                this.mNewSS.setCdmaSystemAndNetworkId(n, n4);
                this.mRegistrationDeniedReason = n5 == 0 ? REGISTRATION_DENIED_GEN : (n5 == 1 ? REGISTRATION_DENIED_AUTH : "");
                if (this.mRegistrationState == 3) {
                    object2 = new StringBuilder();
                    ((StringBuilder)object2).append("Registration denied, ");
                    ((StringBuilder)object2).append(this.mRegistrationDeniedReason);
                    this.log(((StringBuilder)object2).toString());
                }
            }
            this.mNewCellIdentity = object.getCellIdentity();
            object2 = new StringBuilder();
            ((StringBuilder)object2).append("handlePollStateResultMessage: CS cellular. ");
            ((StringBuilder)object2).append(object);
            this.log(((StringBuilder)object2).toString());
        }
    }

    protected void hangupAndPowerOff() {
        if (!this.mPhone.isPhoneTypeGsm() || this.mPhone.isInCall()) {
            this.mPhone.mCT.mRingingCall.hangupIfAlive();
            this.mPhone.mCT.mBackgroundCall.hangupIfAlive();
            this.mPhone.mCT.mForegroundCall.hangupIfAlive();
        }
        this.mCi.setRadioPower(false, this.obtainMessage(54));
    }

    protected boolean inSameCountry(String string) {
        if (!TextUtils.isEmpty((CharSequence)string) && string.length() >= 5) {
            String string2 = this.getHomeOperatorNumeric();
            if (!TextUtils.isEmpty((CharSequence)string2) && string2.length() >= 5) {
                string = string.substring(0, 3);
                string2 = string2.substring(0, 3);
                string = MccTable.countryCodeForMcc(string);
                string2 = MccTable.countryCodeForMcc(string2);
                if (!string.isEmpty() && !string2.isEmpty()) {
                    boolean bl;
                    boolean bl2 = string2.equals(string);
                    if (bl2) {
                        return bl2;
                    }
                    if ("us".equals(string2) && "vi".equals(string)) {
                        bl = true;
                    } else {
                        bl = bl2;
                        if ("vi".equals(string2)) {
                            bl = bl2;
                            if ("us".equals(string)) {
                                bl = true;
                            }
                        }
                    }
                    return bl;
                }
                return false;
            }
            return false;
        }
        return false;
    }

    protected boolean isCallerOnDifferentThread() {
        boolean bl = Thread.currentThread() != this.getLooper().getThread();
        return bl;
    }

    @UnsupportedAppUsage
    public boolean isConcurrentVoiceAndDataAllowed() {
        int n = this.mSS.getCssIndicator();
        boolean bl = true;
        if (n == 1) {
            return true;
        }
        if (this.mPhone.isPhoneTypeGsm()) {
            if (this.mSS.getRilDataRadioTechnology() < 3) {
                bl = false;
            }
            return bl;
        }
        return false;
    }

    public boolean isDeviceShuttingDown() {
        return this.mDeviceShuttingDown;
    }

    @UnsupportedAppUsage
    public boolean isImsRegistered() {
        return this.mImsRegistered;
    }

    public boolean isMinInfoReady() {
        return this.mIsMinInfoReady;
    }

    protected final boolean isNonRoamingInCdmaNetwork(BaseBundle baseBundle, String string) {
        return this.isInNetwork(baseBundle, string, "cdma_nonroaming_networks_string_array");
    }

    protected final boolean isNonRoamingInGsmNetwork(BaseBundle baseBundle, String string) {
        return this.isInNetwork(baseBundle, string, "gsm_nonroaming_networks_string_array");
    }

    public boolean isRadioOn() {
        int n = this.mCi.getRadioState();
        boolean bl = true;
        if (n != 1) {
            bl = false;
        }
        return bl;
    }

    protected final boolean isRoamingInCdmaNetwork(BaseBundle baseBundle, String string) {
        return this.isInNetwork(baseBundle, string, "cdma_roaming_networks_string_array");
    }

    protected final boolean isRoamingInGsmNetwork(BaseBundle baseBundle, String string) {
        return this.isInNetwork(baseBundle, string, "gsm_roaming_networks_string_array");
    }

    protected boolean isSidsAllZeros() {
        if (this.mHomeSystemId != null) {
            int[] arrn;
            for (int i = 0; i < (arrn = this.mHomeSystemId).length; ++i) {
                if (arrn[i] == 0) continue;
                return false;
            }
        }
        return true;
    }

    @UnsupportedAppUsage
    protected final void log(String string) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[");
        stringBuilder.append(this.mPhone.getPhoneId());
        stringBuilder.append("] ");
        stringBuilder.append(string);
        Rlog.d((String)LOG_TAG, (String)stringBuilder.toString());
    }

    @UnsupportedAppUsage
    protected final void loge(String string) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[");
        stringBuilder.append(this.mPhone.getPhoneId());
        stringBuilder.append("] ");
        stringBuilder.append(string);
        Rlog.e((String)LOG_TAG, (String)stringBuilder.toString());
    }

    protected void notifyDataRegStateRilRadioTechnologyChanged(int n) {
        Object object = this.mSS.getNetworkRegistrationInfo(2, n);
        if (object != null) {
            int n2 = ServiceState.networkTypeToRilRadioTechnology((int)object.getAccessNetworkTechnology());
            int n3 = this.regCodeToServiceState(object.getRegistrationState());
            object = new StringBuilder();
            ((StringBuilder)object).append("notifyDataRegStateRilRadioTechnologyChanged: drs=");
            ((StringBuilder)object).append(n3);
            ((StringBuilder)object).append(" rat=");
            ((StringBuilder)object).append(n2);
            this.log(((StringBuilder)object).toString());
            object = (RegistrantList)this.mDataRegStateOrRatChangedRegistrants.get(n);
            if (object != null) {
                object.notifyResult((Object)new Pair((Object)n3, (Object)n2));
            }
        }
        this.mPhone.setSystemProperty("gsm.network.type", ServiceState.rilRadioTechnologyToString((int)this.mSS.getRilDataRadioTechnology()));
    }

    @UnsupportedAppUsage
    protected boolean notifySignalStrength() {
        boolean bl = false;
        boolean bl2 = false;
        if (!this.mSignalStrength.equals((Object)this.mLastSignalStrength)) {
            bl = bl2;
            this.mPhone.notifySignalStrength();
            bl2 = true;
            bl = true;
            try {
                this.mLastSignalStrength = this.mSignalStrength;
                bl = bl2;
            }
            catch (NullPointerException nullPointerException) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("updateSignalStrength() Phone already destroyed: ");
                stringBuilder.append(nullPointerException);
                stringBuilder.append("SignalStrength not notified");
                this.loge(stringBuilder.toString());
            }
        }
        return bl;
    }

    protected void notifyVoiceRegStateRilRadioTechnologyChanged() {
        int n = this.mSS.getRilVoiceRadioTechnology();
        int n2 = this.mSS.getVoiceRegState();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("notifyVoiceRegStateRilRadioTechnologyChanged: vrs=");
        stringBuilder.append(n2);
        stringBuilder.append(" rat=");
        stringBuilder.append(n);
        this.log(stringBuilder.toString());
        this.mVoiceRegStateOrRatChangedRegistrants.notifyResult((Object)new Pair((Object)n2, (Object)n));
    }

    public void onImsCapabilityChanged() {
        this.sendMessage(this.obtainMessage(48));
    }

    public void onImsServiceStateChanged() {
        this.sendMessage(this.obtainMessage(53));
    }

    protected boolean onSignalStrengthResult(AsyncResult asyncResult) {
        if (asyncResult.exception == null && asyncResult.result != null) {
            this.mSignalStrength = (SignalStrength)asyncResult.result;
            asyncResult = this.getCarrierConfig();
            this.mSignalStrength.updateLevel((PersistableBundle)asyncResult, this.mSS);
        } else {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("onSignalStrengthResult() Exception from RIL : ");
            stringBuilder.append(asyncResult.exception);
            this.log(stringBuilder.toString());
            this.mSignalStrength = new SignalStrength();
        }
        return this.notifySignalStrength();
    }

    protected void onUpdateIccAvailability() {
        if (this.mUiccController == null) {
            return;
        }
        Object object = this.getUiccCardApplication();
        if (this.mUiccApplcation != object) {
            IccRecords iccRecords = this.mIccRecords;
            if (iccRecords instanceof SIMRecords) {
                this.mCdnr.updateEfFromUsim(null);
            } else if (iccRecords instanceof RuimRecords) {
                this.mCdnr.updateEfFromRuim(null);
            }
            if (this.mUiccApplcation != null) {
                this.log("Removing stale icc objects.");
                this.mUiccApplcation.unregisterForReady(this);
                iccRecords = this.mIccRecords;
                if (iccRecords != null) {
                    iccRecords.unregisterForRecordsLoaded(this);
                }
                this.mIccRecords = null;
                this.mUiccApplcation = null;
            }
            if (object != null) {
                this.log("New card found");
                this.mUiccApplcation = object;
                this.mIccRecords = this.mUiccApplcation.getIccRecords();
                if (this.mPhone.isPhoneTypeGsm()) {
                    this.mUiccApplcation.registerForReady(this, 17, null);
                    object = this.mIccRecords;
                    if (object != null) {
                        ((IccRecords)object).registerForRecordsLoaded(this, 16, null);
                    }
                } else if (this.mIsSubscriptionFromRuim) {
                    this.mUiccApplcation.registerForReady(this, 26, null);
                    object = this.mIccRecords;
                    if (object != null) {
                        ((IccRecords)object).registerForRecordsLoaded(this, 27, null);
                    }
                }
            }
        }
    }

    protected void parseSidNid(String charSequence, String string) {
        int n;
        String[] arrstring;
        if (charSequence != null) {
            String[] arrstring2 = ((String)charSequence).split(",");
            this.mHomeSystemId = new int[arrstring2.length];
            for (n = 0; n < arrstring2.length; ++n) {
                try {
                    this.mHomeSystemId[n] = Integer.parseInt(arrstring2[n]);
                    continue;
                }
                catch (NumberFormatException numberFormatException) {
                    arrstring = new StringBuilder();
                    arrstring.append("error parsing system id: ");
                    arrstring.append(numberFormatException);
                    this.loge(arrstring.toString());
                }
            }
        }
        arrstring = new StringBuilder();
        arrstring.append("CDMA_SUBSCRIPTION: SID=");
        arrstring.append((String)charSequence);
        this.log(arrstring.toString());
        if (string != null) {
            arrstring = string.split(",");
            this.mHomeNetworkId = new int[arrstring.length];
            for (n = 0; n < arrstring.length; ++n) {
                try {
                    this.mHomeNetworkId[n] = Integer.parseInt(arrstring[n]);
                    continue;
                }
                catch (NumberFormatException numberFormatException) {
                    charSequence = new StringBuilder();
                    ((StringBuilder)charSequence).append("CDMA_SUBSCRIPTION: error parsing network id: ");
                    ((StringBuilder)charSequence).append(numberFormatException);
                    this.loge(((StringBuilder)charSequence).toString());
                }
            }
        }
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append("CDMA_SUBSCRIPTION: NID=");
        ((StringBuilder)charSequence).append(string);
        this.log(((StringBuilder)charSequence).toString());
    }

    @UnsupportedAppUsage
    public void pollState() {
        this.pollState(false);
    }

    /*
     * Enabled aggressive block sorting
     */
    public void pollState(boolean bl) {
        block8 : {
            int[] arrn;
            block7 : {
                block6 : {
                    this.mPollingContext = new int[1];
                    this.mPollingContext[0] = 0;
                    arrn = new StringBuilder();
                    arrn.append("pollState: modemTriggered=");
                    arrn.append(bl);
                    this.log(arrn.toString());
                    int n = this.mCi.getRadioState();
                    if (n == 0) break block6;
                    if (n == 2) {
                        this.mNewSS.setStateOutOfService();
                        this.mNewCellIdentity = null;
                        this.setSignalStrengthDefaultValues();
                        this.mNitzState.handleNetworkCountryCodeUnavailable();
                        this.pollStateDone();
                        return;
                    }
                    break block7;
                }
                this.mNewSS.setStateOff();
                this.mNewCellIdentity = null;
                this.setSignalStrengthDefaultValues();
                this.mNitzState.handleNetworkCountryCodeUnavailable();
                if (this.mDeviceShuttingDown || !bl && 18 != this.mSS.getRilDataRadioTechnology()) break block8;
            }
            arrn = this.mPollingContext;
            arrn[0] = arrn[0] + 1;
            this.mCi.getOperator(this.obtainMessage(7, (Object)arrn));
            arrn = this.mPollingContext;
            arrn[0] = arrn[0] + 1;
            ((NetworkRegistrationManager)((Object)this.mRegStateManagers.get(1))).requestNetworkRegistrationInfo(2, this.obtainMessage(5, (Object)this.mPollingContext));
            arrn = this.mPollingContext;
            arrn[0] = arrn[0] + 1;
            ((NetworkRegistrationManager)((Object)this.mRegStateManagers.get(1))).requestNetworkRegistrationInfo(1, this.obtainMessage(4, (Object)this.mPollingContext));
            if (this.mRegStateManagers.get(2) != null) {
                arrn = this.mPollingContext;
                arrn[0] = arrn[0] + 1;
                ((NetworkRegistrationManager)((Object)this.mRegStateManagers.get(2))).requestNetworkRegistrationInfo(2, this.obtainMessage(6, (Object)this.mPollingContext));
            }
            if (!this.mPhone.isPhoneTypeGsm()) return;
            arrn = this.mPollingContext;
            arrn[0] = arrn[0] + 1;
            this.mCi.getNetworkSelectionMode(this.obtainMessage(14, (Object)arrn));
            return;
        }
        this.pollStateDone();
    }

    /*
     * WARNING - combined exceptions agressively - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void powerOffRadioSafely() {
        synchronized (this) {
            if (!this.mPendingRadioPowerOffAfterDataOff) {
                int n = SubscriptionManager.getDefaultDataSubscriptionId();
                boolean bl = this.mPhone.areAllDataDisconnected();
                if (bl && (n == this.mPhone.getSubId() || n != this.mPhone.getSubId() && ProxyController.getInstance().areAllDataDisconnected(n))) {
                    for (int n2 : this.mTransportManager.getAvailableTransports()) {
                        if (this.mPhone.getDcTracker(n2) == null) continue;
                        this.mPhone.getDcTracker(n2).cleanUpAllConnections("radioTurnedOff");
                    }
                    this.log("Data disconnected, turn off radio right away.");
                    this.hangupAndPowerOff();
                } else {
                    int n3;
                    if (this.mPhone.isPhoneTypeGsm() && this.mPhone.isInCall()) {
                        this.mPhone.mCT.mRingingCall.hangupIfAlive();
                        this.mPhone.mCT.mBackgroundCall.hangupIfAlive();
                        this.mPhone.mCT.mForegroundCall.hangupIfAlive();
                    }
                    for (int n4 : this.mTransportManager.getAvailableTransports()) {
                        if (this.mPhone.getDcTracker(n4) == null) continue;
                        this.mPhone.getDcTracker(n4).cleanUpAllConnections("radioTurnedOff");
                    }
                    if (n != this.mPhone.getSubId() && !ProxyController.getInstance().areAllDataDisconnected(n)) {
                        this.log("Data is active on DDS.  Wait for all data disconnect");
                        ProxyController.getInstance().registerForAllDataDisconnected(n, this, 49);
                        this.mPendingRadioPowerOffAfterDataOff = true;
                    }
                    Message message = Message.obtain((Handler)this);
                    message.what = 38;
                    this.mPendingRadioPowerOffAfterDataOffTag = n3 = this.mPendingRadioPowerOffAfterDataOffTag + 1;
                    message.arg1 = n3;
                    if (this.sendMessageDelayed(message, 30000L)) {
                        this.log("Wait upto 30s for data to disconnect, then turn off radio.");
                        this.mPendingRadioPowerOffAfterDataOff = true;
                    } else {
                        this.log("Cannot send delayed Msg, turn off radio right away.");
                        this.hangupAndPowerOff();
                        this.mPendingRadioPowerOffAfterDataOff = false;
                    }
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
    public boolean processPendingRadioPowerOffAfterDataOff() {
        synchronized (this) {
            if (this.mPendingRadioPowerOffAfterDataOff) {
                this.log("Process pending request to turn radio off.");
                ++this.mPendingRadioPowerOffAfterDataOffTag;
                this.hangupAndPowerOff();
                this.mPendingRadioPowerOffAfterDataOff = false;
                return true;
            }
            return false;
        }
    }

    @UnsupportedAppUsage
    public void reRegisterNetwork(Message message) {
        this.mCi.getPreferredNetworkType(this.obtainMessage(19, (Object)message));
    }

    public void registerForDataConnectionAttached(int n, Handler handler, int n2, Object object) {
        handler = new Registrant(handler, n2, object);
        if (this.mAttachedRegistrants.get(n) == null) {
            this.mAttachedRegistrants.put(n, (Object)new RegistrantList());
        }
        ((RegistrantList)this.mAttachedRegistrants.get(n)).add((Registrant)handler);
        object = this.mSS;
        if (object != null && ((object = object.getNetworkRegistrationInfo(2, n)) == null || object.isInService())) {
            handler.notifyRegistrant();
        }
    }

    public void registerForDataConnectionDetached(int n, Handler handler, int n2, Object object) {
        handler = new Registrant(handler, n2, object);
        if (this.mDetachedRegistrants.get(n) == null) {
            this.mDetachedRegistrants.put(n, (Object)new RegistrantList());
        }
        ((RegistrantList)this.mDetachedRegistrants.get(n)).add((Registrant)handler);
        object = this.mSS;
        if (object != null && (object = object.getNetworkRegistrationInfo(2, n)) != null && !object.isInService()) {
            handler.notifyRegistrant();
        }
    }

    public void registerForDataRegStateOrRatChanged(int n, Handler handler, int n2, Object object) {
        handler = new Registrant(handler, n2, object);
        if (this.mDataRegStateOrRatChangedRegistrants.get(n) == null) {
            this.mDataRegStateOrRatChangedRegistrants.put(n, (Object)new RegistrantList());
        }
        ((RegistrantList)this.mDataRegStateOrRatChangedRegistrants.get(n)).add((Registrant)handler);
        this.notifyDataRegStateRilRadioTechnologyChanged(n);
    }

    public void registerForDataRoamingOff(Handler handler, int n, Object object, boolean bl) {
        handler = new Registrant(handler, n, object);
        this.mDataRoamingOffRegistrants.add((Registrant)handler);
        if (bl && !this.mSS.getDataRoaming()) {
            handler.notifyRegistrant();
        }
    }

    public void registerForDataRoamingOn(Handler handler, int n, Object object) {
        handler = new Registrant(handler, n, object);
        this.mDataRoamingOnRegistrants.add((Registrant)handler);
        if (this.mSS.getDataRoaming()) {
            handler.notifyRegistrant();
        }
    }

    public void registerForImsCapabilityChanged(Handler handler, int n, Object object) {
        handler = new Registrant(handler, n, object);
        this.mImsCapabilityChangedRegistrants.add((Registrant)handler);
    }

    public void registerForNetworkAttached(Handler handler, int n, Object object) {
        handler = new Registrant(handler, n, object);
        this.mNetworkAttachedRegistrants.add((Registrant)handler);
        if (this.mSS.getVoiceRegState() == 0) {
            handler.notifyRegistrant();
        }
    }

    public void registerForNetworkDetached(Handler handler, int n, Object object) {
        handler = new Registrant(handler, n, object);
        this.mNetworkDetachedRegistrants.add((Registrant)handler);
        if (this.mSS.getVoiceRegState() != 0) {
            handler.notifyRegistrant();
        }
    }

    public void registerForPsRestrictedDisabled(Handler handler, int n, Object object) {
        handler = new Registrant(handler, n, object);
        this.mPsRestrictDisabledRegistrants.add((Registrant)handler);
        if (this.mRestrictedState.isPsRestricted()) {
            handler.notifyRegistrant();
        }
    }

    public void registerForPsRestrictedEnabled(Handler handler, int n, Object object) {
        handler = new Registrant(handler, n, object);
        this.mPsRestrictEnabledRegistrants.add((Registrant)handler);
        if (this.mRestrictedState.isPsRestricted()) {
            handler.notifyRegistrant();
        }
    }

    public void registerForSubscriptionInfoReady(Handler handler, int n, Object object) {
        handler = new Registrant(handler, n, object);
        this.mCdmaForSubscriptionInfoReadyRegistrants.add((Registrant)handler);
        if (this.isMinInfoReady()) {
            handler.notifyRegistrant();
        }
    }

    public void registerForVoiceRegStateOrRatChanged(Handler handler, int n, Object object) {
        handler = new Registrant(handler, n, object);
        this.mVoiceRegStateOrRatChangedRegistrants.add((Registrant)handler);
        this.notifyVoiceRegStateRilRadioTechnologyChanged();
    }

    public void registerForVoiceRoamingOff(Handler handler, int n, Object object) {
        handler = new Registrant(handler, n, object);
        this.mVoiceRoamingOffRegistrants.add((Registrant)handler);
        if (!this.mSS.getVoiceRoaming()) {
            handler.notifyRegistrant();
        }
    }

    public void registerForVoiceRoamingOn(Handler handler, int n, Object object) {
        handler = new Registrant(handler, n, object);
        this.mVoiceRoamingOnRegistrants.add((Registrant)handler);
        if (this.mSS.getVoiceRoaming()) {
            handler.notifyRegistrant();
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void requestAllCellInfo(WorkSource workSource, Message message) {
        if (this.mCi.getRilVersion() < 8) {
            AsyncResult.forMessage((Message)message);
            message.sendToTarget();
            this.log("SST.requestAllCellInfo(): not implemented");
            return;
        }
        List<Message> list = this.mPendingCellInfoRequests;
        synchronized (list) {
            if (this.mIsPendingCellInfoRequest) {
                if (message != null) {
                    this.mPendingCellInfoRequests.add(message);
                }
                return;
            }
            long l = SystemClock.elapsedRealtime();
            if (l - this.mLastCellInfoReqTime < (long)this.mCellInfoMinIntervalMs) {
                if (message != null) {
                    this.log("SST.requestAllCellInfo(): return last, back to back calls");
                    AsyncResult.forMessage((Message)message, this.mLastCellInfoList, null);
                    message.sendToTarget();
                }
                return;
            }
            if (message != null) {
                this.mPendingCellInfoRequests.add(message);
            }
            this.mLastCellInfoReqTime = l;
            this.mIsPendingCellInfoRequest = true;
            message = this.obtainMessage(43);
            this.mCi.getCellInfoList(message, workSource);
            this.sendMessageDelayed(this.obtainMessage(43), 2000L);
            return;
        }
    }

    public void requestCellLocation(WorkSource workSource, Message message) {
        CellIdentity cellIdentity = this.mCellIdentity;
        if (cellIdentity != null) {
            AsyncResult.forMessage((Message)message, (Object)cellIdentity.asCellLocation(), null);
            message.sendToTarget();
            return;
        }
        this.requestAllCellInfo(workSource, this.obtainMessage(56, (Object)message));
    }

    @VisibleForTesting
    public void requestShutdown() {
        if (this.mDeviceShuttingDown) {
            return;
        }
        this.mDeviceShuttingDown = true;
        this.mDesiredPowerState = false;
        this.setPowerStateToDesired();
    }

    public void setCellInfoMinInterval(int n) {
        this.mCellInfoMinIntervalMs = n;
    }

    public void setImsRegistrationState(boolean bl) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("ImsRegistrationState - registered : ");
        stringBuilder.append(bl);
        this.log(stringBuilder.toString());
        if (this.mImsRegistrationOnOff && !bl && this.mAlarmSwitch) {
            this.mImsRegistrationOnOff = bl;
            ((AlarmManager)this.mPhone.getContext().getSystemService("alarm")).cancel(this.mRadioOffIntent);
            this.mAlarmSwitch = false;
            this.sendMessage(this.obtainMessage(45));
            return;
        }
        this.mImsRegistrationOnOff = bl;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    @VisibleForTesting
    public void setNotification(int var1_1) {
        var2_2 = var1_1;
        var3_3 = new StringBuilder();
        var3_3.append("setNotification: create notification ");
        var3_3.append(var2_2);
        this.log(var3_3.toString());
        if (!SubscriptionManager.isValidSubscriptionId((int)this.mSubId)) {
            var3_3 = new StringBuilder();
            var3_3.append("cannot setNotification on invalid subid mSubId=");
            var3_3.append(this.mSubId);
            this.loge(var3_3.toString());
            return;
        }
        var4_4 = this.mPhone.getContext();
        var3_3 = this.mSubscriptionController.getActiveSubscriptionInfo(this.mPhone.getSubId(), var4_4.getOpPackageName());
        if (!(var3_3 == null || var3_3.isOpportunistic() && var3_3.getGroupUuid() != null)) {
            if (!var4_4.getResources().getBoolean(17891569)) {
                this.log("Ignore all the notifications");
                return;
            }
            var3_3 = this.getCarrierConfig();
            if (var3_3.getBoolean("disable_voice_barring_notification_bool", false) && (var2_2 == 1003 || var2_2 == 1005 || var2_2 == 1006)) {
                this.log("Voice/emergency call barred notification disabled");
                return;
            }
            var5_5 = var3_3.getBoolean("carrier_auto_cancel_cs_notification", false);
            var3_3 = "";
            var6_6 = "";
            var1_1 = 999;
            var7_13 = 17301642;
            var8_14 = ((TelephonyManager)this.mPhone.getContext().getSystemService("phone")).getPhoneCount() > 1;
            var9_15 = this.mSubscriptionController.getSlotIndex(this.mSubId) + 1;
            if (var2_2 != 2001) {
                switch (var2_2) {
                    default: {
                        ** break;
                    }
                    case 1006: {
                        var6_7 = var4_4.getText(17039423);
                        if (var8_14) {
                            var3_3 = var4_4.getString(17039426, new Object[]{var9_15});
                            ** break;
                        }
                        var3_3 = var4_4.getText(17039425);
                        ** break;
                    }
                    case 1005: {
                        var6_8 = var4_4.getText(17039424);
                        if (var8_14) {
                            var3_3 = var4_4.getString(17039426, new Object[]{var9_15});
                            ** break;
                        }
                        var3_3 = var4_4.getText(17039425);
                        ** break;
                    }
                    case 1004: {
                        ** break;
                    }
                    case 1003: {
                        var6_9 = var4_4.getText(17039421);
                        if (var8_14) {
                            var3_3 = var4_4.getString(17039426, new Object[]{var9_15});
                            ** break;
                        }
                        var3_3 = var4_4.getText(17039425);
                        ** break;
                    }
                    case 1002: {
                        var1_1 = 888;
                        ** break;
                    }
                    case 1001: 
                }
                if ((long)SubscriptionManager.getDefaultDataSubscriptionId() != (long)this.mPhone.getSubId()) {
                    return;
                }
                var1_1 = 888;
                var6_10 = var4_4.getText(17039422);
                if (var8_14) {
                    var3_3 = var4_4.getString(17039426, new Object[]{var9_15});
                    ** break;
                }
                var3_3 = var4_4.getText(17039425);
                ** break;
lbl73: // 11 sources:
            } else {
                var1_1 = 111;
                var10_16 = this.selectResourceForRejectCode(this.mRejectCode, var8_14);
                if (var10_16 == 0) {
                    if (!var5_5) {
                        var3_3 = new StringBuilder();
                        var3_3.append("setNotification: mRejectCode=");
                        var3_3.append(this.mRejectCode);
                        var3_3.append(" is not handled.");
                        this.loge(var3_3.toString());
                        return;
                    }
                    var2_2 = 2002;
                } else {
                    var7_13 = 17303513;
                    var6_11 = var4_4.getString(var10_16, new Object[]{var9_15});
                    var3_3 = null;
                }
            }
            var11_17 = new StringBuilder();
            var11_17.append("setNotification, create notification, notifyType: ");
            var11_17.append(var2_2);
            var11_17.append(", title: ");
            var11_17.append(var6_12);
            var11_17.append(", details: ");
            var11_17.append((Object)var3_3);
            var11_17.append(", subId: ");
            var11_17.append(this.mSubId);
            this.log(var11_17.toString());
            this.mNotification = new Notification.Builder(var4_4).setWhen(System.currentTimeMillis()).setAutoCancel(true).setSmallIcon(var7_13).setTicker((CharSequence)var6_12).setColor(var4_4.getResources().getColor(17170460)).setContentTitle((CharSequence)var6_12).setStyle((Notification.Style)new Notification.BigTextStyle().bigText(var3_3)).setContentText(var3_3).setChannel("alert").build();
            var3_3 = (NotificationManager)var4_4.getSystemService("notification");
            if (var2_2 != 1002 && var2_2 != 1004 && var2_2 != 2002) {
                var7_13 = 0;
                if (this.mSS.isEmergencyOnly() && var2_2 == 1006) {
                    var2_2 = 1;
                } else if (var2_2 == 2001) {
                    var2_2 = 1;
                } else {
                    var2_2 = var7_13;
                    if (this.mSS.getState() == 0) {
                        var2_2 = 1;
                    }
                }
                if (var2_2 == 0) return;
                var3_3.notify(Integer.toString(this.mSubId), var1_1, this.mNotification);
                return;
            }
            var3_3.cancel(Integer.toString(this.mSubId), var1_1);
            return;
        }
        var3_3 = new StringBuilder();
        var3_3.append("cannot setNotification on invisible subid mSubId=");
        var3_3.append(this.mSubId);
        this.log(var3_3.toString());
    }

    @UnsupportedAppUsage
    protected void setOperatorIdd(String string) {
        if ((string = this.mHbpcdUtils.getIddByMcc(Integer.parseInt(string.substring(0, 3)))) != null && !string.isEmpty()) {
            this.mPhone.setGlobalSystemProperty("gsm.operator.idpstring", string);
        } else {
            this.mPhone.setGlobalSystemProperty("gsm.operator.idpstring", "+");
        }
    }

    protected void setPowerStateToDesired() {
        CharSequence charSequence = new StringBuilder();
        charSequence.append("mDeviceShuttingDown=");
        charSequence.append(this.mDeviceShuttingDown);
        charSequence.append(", mDesiredPowerState=");
        charSequence.append(this.mDesiredPowerState);
        charSequence.append(", getRadioState=");
        charSequence.append(this.mCi.getRadioState());
        charSequence.append(", mPowerOffDelayNeed=");
        charSequence.append(this.mPowerOffDelayNeed);
        charSequence.append(", mAlarmSwitch=");
        charSequence.append(this.mAlarmSwitch);
        charSequence.append(", mRadioDisabledByCarrier=");
        charSequence.append(this.mRadioDisabledByCarrier);
        charSequence = charSequence.toString();
        this.log((String)charSequence);
        this.mRadioPowerLog.log((String)charSequence);
        if (this.mPhone.isPhoneTypeGsm() && this.mAlarmSwitch) {
            this.log("mAlarmSwitch == true");
            ((AlarmManager)this.mPhone.getContext().getSystemService("alarm")).cancel(this.mRadioOffIntent);
            this.mAlarmSwitch = false;
        }
        if (this.mDesiredPowerState && !this.mRadioDisabledByCarrier && this.mCi.getRadioState() == 0) {
            this.mCi.setRadioPower(true, null);
        } else if ((!this.mDesiredPowerState || this.mRadioDisabledByCarrier) && this.mCi.getRadioState() == 1) {
            if (this.mPhone.isPhoneTypeGsm() && this.mPowerOffDelayNeed) {
                if (this.mImsRegistrationOnOff && !this.mAlarmSwitch) {
                    this.log("mImsRegistrationOnOff == true");
                    charSequence = this.mPhone.getContext();
                    AlarmManager alarmManager = (AlarmManager)charSequence.getSystemService("alarm");
                    this.mRadioOffIntent = PendingIntent.getBroadcast((Context)charSequence, (int)0, (Intent)new Intent(ACTION_RADIO_OFF), (int)0);
                    this.mAlarmSwitch = true;
                    this.log("Alarm setting");
                    alarmManager.set(2, SystemClock.elapsedRealtime() + 3000L, this.mRadioOffIntent);
                } else {
                    this.powerOffRadioSafely();
                }
            } else {
                this.powerOffRadioSafely();
            }
        } else if (this.mDeviceShuttingDown && this.mCi.getRadioState() != 2) {
            this.mCi.requestShutdown(null);
        }
    }

    public void setRadioPower(boolean bl) {
        this.mDesiredPowerState = bl;
        this.setPowerStateToDesired();
    }

    public void setRadioPowerFromCarrier(boolean bl) {
        this.mRadioDisabledByCarrier = bl ^ true;
        this.setPowerStateToDesired();
    }

    @UnsupportedAppUsage
    protected void setRoamingType(ServiceState serviceState) {
        int n;
        int n2;
        boolean bl = serviceState.getVoiceRegState() == 0;
        if (bl) {
            if (serviceState.getVoiceRoaming()) {
                if (this.mPhone.isPhoneTypeGsm()) {
                    if (this.inSameCountry(serviceState.getVoiceOperatorNumeric())) {
                        serviceState.setVoiceRoamingType(2);
                    } else {
                        serviceState.setVoiceRoamingType(3);
                    }
                } else {
                    int[] arrn = this.mPhone.getContext().getResources().getIntArray(17235998);
                    if (arrn != null && arrn.length > 0) {
                        serviceState.setVoiceRoamingType(2);
                        n2 = serviceState.getCdmaRoamingIndicator();
                        for (n = 0; n < arrn.length; ++n) {
                            if (n2 != arrn[n]) continue;
                            serviceState.setVoiceRoamingType(3);
                            break;
                        }
                    } else if (this.inSameCountry(serviceState.getVoiceOperatorNumeric())) {
                        serviceState.setVoiceRoamingType(2);
                    } else {
                        serviceState.setVoiceRoamingType(3);
                    }
                }
            } else {
                serviceState.setVoiceRoamingType(0);
            }
        }
        n = serviceState.getDataRegState() == 0 ? 1 : 0;
        n2 = serviceState.getRilDataRadioTechnology();
        if (n != 0) {
            if (!serviceState.getDataRoaming()) {
                serviceState.setDataRoamingType(0);
            } else if (this.mPhone.isPhoneTypeGsm()) {
                if (ServiceState.isGsm((int)n2)) {
                    if (bl) {
                        serviceState.setDataRoamingType(serviceState.getVoiceRoamingType());
                    } else {
                        serviceState.setDataRoamingType(1);
                    }
                } else {
                    serviceState.setDataRoamingType(1);
                }
            } else if (ServiceState.isCdma((int)n2)) {
                if (bl) {
                    serviceState.setDataRoamingType(serviceState.getVoiceRoamingType());
                } else {
                    serviceState.setDataRoamingType(1);
                }
            } else if (this.inSameCountry(serviceState.getDataOperatorNumeric())) {
                serviceState.setDataRoamingType(2);
            } else {
                serviceState.setDataRoamingType(3);
            }
        }
    }

    public void unregisterForDataConnectionAttached(int n, Handler handler) {
        if (this.mAttachedRegistrants.get(n) != null) {
            ((RegistrantList)this.mAttachedRegistrants.get(n)).remove(handler);
        }
    }

    public void unregisterForDataConnectionDetached(int n, Handler handler) {
        if (this.mDetachedRegistrants.get(n) != null) {
            ((RegistrantList)this.mDetachedRegistrants.get(n)).remove(handler);
        }
    }

    public void unregisterForDataRegStateOrRatChanged(int n, Handler handler) {
        if (this.mDataRegStateOrRatChangedRegistrants.get(n) != null) {
            ((RegistrantList)this.mDataRegStateOrRatChangedRegistrants.get(n)).remove(handler);
        }
    }

    public void unregisterForDataRoamingOff(Handler handler) {
        this.mDataRoamingOffRegistrants.remove(handler);
    }

    public void unregisterForDataRoamingOn(Handler handler) {
        this.mDataRoamingOnRegistrants.remove(handler);
    }

    public void unregisterForImsCapabilityChanged(Handler handler) {
        this.mImsCapabilityChangedRegistrants.remove(handler);
    }

    public void unregisterForNetworkAttached(Handler handler) {
        this.mNetworkAttachedRegistrants.remove(handler);
    }

    public void unregisterForNetworkDetached(Handler handler) {
        this.mNetworkDetachedRegistrants.remove(handler);
    }

    public void unregisterForPsRestrictedDisabled(Handler handler) {
        this.mPsRestrictDisabledRegistrants.remove(handler);
    }

    public void unregisterForPsRestrictedEnabled(Handler handler) {
        this.mPsRestrictEnabledRegistrants.remove(handler);
    }

    public void unregisterForSubscriptionInfoReady(Handler handler) {
        this.mCdmaForSubscriptionInfoReadyRegistrants.remove(handler);
    }

    public void unregisterForVoiceRegStateOrRatChanged(Handler handler) {
        this.mVoiceRegStateOrRatChangedRegistrants.remove(handler);
    }

    public void unregisterForVoiceRoamingOff(Handler handler) {
        this.mVoiceRoamingOffRegistrants.remove(handler);
    }

    public void unregisterForVoiceRoamingOn(Handler handler) {
        this.mVoiceRoamingOnRegistrants.remove(handler);
    }

    public void updateOperatorNameForCellInfo(List<CellInfo> object) {
        if (object != null && !object.isEmpty()) {
            object = object.iterator();
            while (object.hasNext()) {
                CellInfo cellInfo = (CellInfo)object.next();
                if (!cellInfo.isRegistered()) continue;
                this.updateOperatorNameForCellIdentity(cellInfo.getCellIdentity());
            }
            return;
        }
    }

    @UnsupportedAppUsage
    protected void updateOtaspState() {
        int n = this.getOtasp();
        int n2 = this.mCurrentOtaspMode;
        this.mCurrentOtaspMode = n;
        if (n2 != this.mCurrentOtaspMode) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("updateOtaspState: call notifyOtaspChanged old otaspMode=");
            stringBuilder.append(n2);
            stringBuilder.append(" new otaspMode=");
            stringBuilder.append(this.mCurrentOtaspMode);
            this.log(stringBuilder.toString());
            this.mPhone.notifyOtaspChanged(this.mCurrentOtaspMode);
        }
    }

    @UnsupportedAppUsage
    protected void updatePhoneObject() {
        if (this.mPhone.getContext().getResources().getBoolean(17891547)) {
            boolean bl = this.mSS.getVoiceRegState() == 0 || this.mSS.getVoiceRegState() == 2;
            if (!bl) {
                this.log("updatePhoneObject: Ignore update");
                return;
            }
            this.mPhone.updatePhoneObject(this.mSS.getRilVoiceRadioTechnology());
        }
    }

    @VisibleForTesting
    public void updatePhoneType() {
        int n;
        Object object = this.mSS;
        if (object != null && object.getVoiceRoaming()) {
            this.mVoiceRoamingOffRegistrants.notifyRegistrants();
        }
        if ((object = this.mSS) != null && object.getDataRoaming()) {
            this.mDataRoamingOffRegistrants.notifyRegistrants();
        }
        if ((object = this.mSS) != null && object.getVoiceRegState() == 0) {
            this.mNetworkDetachedRegistrants.notifyRegistrants();
        }
        object = this.mTransportManager.getAvailableTransports();
        int n2 = ((int[])object).length;
        int n3 = 0;
        for (n = 0; n < n2; ++n) {
            int n4 = object[n];
            ServiceState serviceState = this.mSS;
            if (serviceState == null || (serviceState = serviceState.getNetworkRegistrationInfo(2, n4)) == null || !serviceState.isInService() || this.mDetachedRegistrants.get(n4) == null) continue;
            ((RegistrantList)this.mDetachedRegistrants.get(n4)).notifyRegistrants();
        }
        this.mSS = new ServiceState();
        this.mSS.setStateOutOfService();
        this.mNewSS = new ServiceState();
        this.mNewSS.setStateOutOfService();
        this.mLastCellInfoReqTime = 0L;
        this.mLastCellInfoList = null;
        this.mSignalStrength = new SignalStrength();
        this.mStartedGprsRegCheck = false;
        this.mReportedGprsNoReg = false;
        this.mMdn = null;
        this.mMin = null;
        this.mPrlVersion = null;
        this.mIsMinInfoReady = false;
        this.mNitzState.handleNetworkCountryCodeUnavailable();
        this.mCellIdentity = null;
        this.mNewCellIdentity = null;
        this.cancelPollState();
        if (this.mPhone.isPhoneTypeGsm()) {
            object = this.mCdmaSSM;
            if (object != null) {
                object.dispose(this);
            }
            this.mCi.unregisterForCdmaPrlChanged(this);
            this.mCi.unregisterForCdmaOtaProvision(this);
            this.mPhone.unregisterForSimRecordsLoaded(this);
        } else {
            this.mPhone.registerForSimRecordsLoaded(this, 16, null);
            this.mCdmaSSM = CdmaSubscriptionSourceManager.getInstance(this.mPhone.getContext(), this.mCi, this, 39, null);
            boolean bl = this.mCdmaSSM.getCdmaSubscriptionSource() == 0;
            this.mIsSubscriptionFromRuim = bl;
            this.mCi.registerForCdmaPrlChanged(this, 40, null);
            this.mCi.registerForCdmaOtaProvision(this, 37, null);
            this.mHbpcdUtils = new HbpcdUtils(this.mPhone.getContext());
            this.updateOtaspState();
        }
        this.onUpdateIccAvailability();
        this.mPhone.setSystemProperty("gsm.network.type", ServiceState.rilRadioTechnologyToString((int)0));
        this.mCi.getSignalStrength(this.obtainMessage(3));
        this.sendMessage(this.obtainMessage(50));
        this.logPhoneTypeChange();
        this.notifyVoiceRegStateRilRadioTechnologyChanged();
        object = this.mTransportManager.getAvailableTransports();
        n2 = ((Object)object).length;
        for (n = n3; n < n2; ++n) {
            this.notifyDataRegStateRilRadioTechnologyChanged((int)object[n]);
        }
    }

    @UnsupportedAppUsage
    protected void updateRoamingState() {
        block19 : {
            Object object;
            block16 : {
                StringBuilder stringBuilder;
                boolean bl;
                block17 : {
                    block18 : {
                        object = this.getCarrierConfig();
                        boolean bl2 = this.mPhone.isPhoneTypeGsm();
                        bl = false;
                        if (!bl2) break block16;
                        if (this.mGsmRoaming || this.mDataRoaming) {
                            bl = true;
                        }
                        bl = bl2 = bl;
                        if (!this.mGsmRoaming) break block17;
                        bl = bl2;
                        if (this.isOperatorConsideredRoaming(this.mNewSS)) break block17;
                        if (this.isSameNamedOperators(this.mNewSS)) break block18;
                        bl = bl2;
                        if (!this.isOperatorConsideredNonRoaming(this.mNewSS)) break block17;
                    }
                    stringBuilder = new StringBuilder();
                    stringBuilder.append("updateRoamingState: resource override set non roaming.isSameNamedOperators=");
                    stringBuilder.append(this.isSameNamedOperators(this.mNewSS));
                    stringBuilder.append(",isOperatorConsideredNonRoaming=");
                    stringBuilder.append(this.isOperatorConsideredNonRoaming(this.mNewSS));
                    this.log(stringBuilder.toString());
                    bl = false;
                }
                if (this.alwaysOnHomeNetwork((BaseBundle)object)) {
                    this.log("updateRoamingState: carrier config override always on home network");
                    bl = false;
                } else if (this.isNonRoamingInGsmNetwork((BaseBundle)object, this.mNewSS.getOperatorNumeric())) {
                    stringBuilder = new StringBuilder();
                    stringBuilder.append("updateRoamingState: carrier config override set non roaming:");
                    stringBuilder.append(this.mNewSS.getOperatorNumeric());
                    this.log(stringBuilder.toString());
                    bl = false;
                } else if (this.isRoamingInGsmNetwork((BaseBundle)object, this.mNewSS.getOperatorNumeric())) {
                    stringBuilder = new StringBuilder();
                    stringBuilder.append("updateRoamingState: carrier config override set roaming:");
                    stringBuilder.append(this.mNewSS.getOperatorNumeric());
                    this.log(stringBuilder.toString());
                    bl = true;
                }
                this.mNewSS.setVoiceRoaming(bl);
                this.mNewSS.setDataRoaming(bl);
                break block19;
            }
            String string = Integer.toString(this.mNewSS.getCdmaSystemId());
            if (this.alwaysOnHomeNetwork((BaseBundle)object)) {
                this.log("updateRoamingState: carrier config override always on home network");
                this.setRoamingOff();
            } else if (!this.isNonRoamingInGsmNetwork((BaseBundle)object, this.mNewSS.getOperatorNumeric()) && !this.isNonRoamingInCdmaNetwork((BaseBundle)object, string)) {
                if (this.isRoamingInGsmNetwork((BaseBundle)object, this.mNewSS.getOperatorNumeric()) || this.isRoamingInCdmaNetwork((BaseBundle)object, string)) {
                    object = new StringBuilder();
                    ((StringBuilder)object).append("updateRoamingState: carrier config override set roaming:");
                    ((StringBuilder)object).append(this.mNewSS.getOperatorNumeric());
                    ((StringBuilder)object).append(", ");
                    ((StringBuilder)object).append(string);
                    this.log(((StringBuilder)object).toString());
                    this.setRoamingOn();
                }
            } else {
                object = new StringBuilder();
                ((StringBuilder)object).append("updateRoamingState: carrier config override set non-roaming:");
                ((StringBuilder)object).append(this.mNewSS.getOperatorNumeric());
                ((StringBuilder)object).append(", ");
                ((StringBuilder)object).append(string);
                this.log(((StringBuilder)object).toString());
                this.setRoamingOff();
            }
            if (Build.IS_DEBUGGABLE && SystemProperties.getBoolean((String)PROP_FORCE_ROAMING, (boolean)false)) {
                this.mNewSS.setVoiceRoaming(true);
                this.mNewSS.setDataRoaming(true);
            }
        }
    }

    @UnsupportedAppUsage
    @VisibleForTesting
    public void updateSpnDisplay() {
        if (this.getCarrierConfig().getBoolean("enable_carrier_display_name_resolver_bool")) {
            this.updateSpnDisplayCdnr();
        } else {
            this.updateSpnDisplayLegacy();
        }
    }

    @UnsupportedAppUsage
    protected void useDataRegStateForDataOnlyDevices() {
        if (!this.mVoiceCapable) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("useDataRegStateForDataOnlyDevice: VoiceRegState=");
            stringBuilder.append(this.mNewSS.getVoiceRegState());
            stringBuilder.append(" DataRegState=");
            stringBuilder.append(this.mNewSS.getDataRegState());
            this.log(stringBuilder.toString());
            stringBuilder = this.mNewSS;
            stringBuilder.setVoiceRegState(stringBuilder.getDataRegState());
        }
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface CarrierNameDisplayBitmask {
    }

    private class SstSubscriptionsChangedListener
    extends SubscriptionManager.OnSubscriptionsChangedListener {
        public final AtomicInteger mPreviousSubId = new AtomicInteger(-1);

        private SstSubscriptionsChangedListener() {
        }

        public void onSubscriptionsChanged() {
            ServiceStateTracker.this.log("SubscriptionListener.onSubscriptionInfoChanged");
            int n = ServiceStateTracker.this.mPhone.getSubId();
            ServiceStateTracker.this.mPrevSubId = this.mPreviousSubId.get();
            if (this.mPreviousSubId.getAndSet(n) != n) {
                if (SubscriptionManager.isValidSubscriptionId((int)n)) {
                    Object object = ServiceStateTracker.this.mPhone.getContext();
                    ServiceStateTracker.this.mPhone.notifyPhoneStateChanged();
                    ServiceStateTracker.this.mPhone.notifyCallForwardingIndicator();
                    boolean bl = object.getResources().getBoolean(17891615);
                    ServiceStateTracker.this.mPhone.sendSubscriptionSettings(bl ^ true);
                    ServiceStateTracker.this.mPhone.setSystemProperty("gsm.network.type", ServiceState.rilRadioTechnologyToString((int)ServiceStateTracker.this.mSS.getRilDataRadioTechnology()));
                    if (ServiceStateTracker.this.mSpnUpdatePending) {
                        ServiceStateTracker.this.mSubscriptionController.setPlmnSpn(ServiceStateTracker.this.mPhone.getPhoneId(), ServiceStateTracker.this.mCurShowPlmn, ServiceStateTracker.this.mCurPlmn, ServiceStateTracker.this.mCurShowSpn, ServiceStateTracker.this.mCurSpn);
                        ServiceStateTracker.this.mSpnUpdatePending = false;
                    }
                    SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences((Context)object);
                    CharSequence charSequence = sharedPreferences.getString("network_selection_key", "");
                    CharSequence charSequence2 = sharedPreferences.getString("network_selection_name_key", "");
                    object = sharedPreferences.getString("network_selection_short_key", "");
                    if (!(TextUtils.isEmpty((CharSequence)charSequence) && TextUtils.isEmpty((CharSequence)charSequence2) && TextUtils.isEmpty((CharSequence)object))) {
                        sharedPreferences = sharedPreferences.edit();
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("network_selection_key");
                        stringBuilder.append(n);
                        sharedPreferences.putString(stringBuilder.toString(), (String)charSequence);
                        charSequence = new StringBuilder();
                        ((StringBuilder)charSequence).append("network_selection_name_key");
                        ((StringBuilder)charSequence).append(n);
                        sharedPreferences.putString(((StringBuilder)charSequence).toString(), (String)charSequence2);
                        charSequence2 = new StringBuilder();
                        ((StringBuilder)charSequence2).append("network_selection_short_key");
                        ((StringBuilder)charSequence2).append(n);
                        sharedPreferences.putString(((StringBuilder)charSequence2).toString(), (String)object);
                        sharedPreferences.remove("network_selection_key");
                        sharedPreferences.remove("network_selection_name_key");
                        sharedPreferences.remove("network_selection_short_key");
                        sharedPreferences.commit();
                    }
                    ServiceStateTracker.this.updateSpnDisplay();
                }
                ServiceStateTracker.this.mPhone.updateVoiceMail();
            }
        }
    }

}

