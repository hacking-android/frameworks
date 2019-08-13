/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.app.AlarmManager
 *  android.app.PendingIntent
 *  android.app.ProgressDialog
 *  android.content.ActivityNotFoundException
 *  android.content.BroadcastReceiver
 *  android.content.ContentResolver
 *  android.content.ContentValues
 *  android.content.Context
 *  android.content.Intent
 *  android.content.IntentFilter
 *  android.content.SharedPreferences
 *  android.content.SharedPreferences$Editor
 *  android.content.res.Resources
 *  android.content.res.Resources$NotFoundException
 *  android.database.ContentObserver
 *  android.database.Cursor
 *  android.net.ConnectivityManager
 *  android.net.LinkProperties
 *  android.net.NetworkCapabilities
 *  android.net.NetworkConfig
 *  android.net.NetworkRequest
 *  android.net.ProxyInfo
 *  android.net.TrafficStats
 *  android.net.Uri
 *  android.os.AsyncResult
 *  android.os.Build
 *  android.os.Bundle
 *  android.os.Handler
 *  android.os.HandlerThread
 *  android.os.IBinder
 *  android.os.Looper
 *  android.os.Message
 *  android.os.PersistableBundle
 *  android.os.RegistrantList
 *  android.os.ServiceManager
 *  android.os.SystemClock
 *  android.os.SystemProperties
 *  android.preference.PreferenceManager
 *  android.provider.Settings
 *  android.provider.Settings$Global
 *  android.provider.Settings$SettingNotFoundException
 *  android.provider.Settings$System
 *  android.provider.Telephony
 *  android.provider.Telephony$Carriers
 *  android.telephony.CarrierConfigManager
 *  android.telephony.CellLocation
 *  android.telephony.DataFailCause
 *  android.telephony.NetworkRegistrationInfo
 *  android.telephony.PcoData
 *  android.telephony.Rlog
 *  android.telephony.ServiceState
 *  android.telephony.SubscriptionManager
 *  android.telephony.SubscriptionManager$OnSubscriptionsChangedListener
 *  android.telephony.TelephonyManager
 *  android.telephony.cdma.CdmaCellLocation
 *  android.telephony.data.ApnSetting
 *  android.telephony.data.ApnSetting$Builder
 *  android.telephony.data.DataProfile
 *  android.telephony.data.DataProfile$Builder
 *  android.telephony.gsm.GsmCellLocation
 *  android.text.TextUtils
 *  android.util.EventLog
 *  android.util.LocalLog
 *  android.util.Pair
 *  android.util.SparseArray
 *  android.view.Window
 *  com.android.internal.annotations.VisibleForTesting
 *  com.android.internal.telephony.DctConstants
 *  com.android.internal.telephony.DctConstants$Activity
 *  com.android.internal.telephony.DctConstants$State
 *  com.android.internal.telephony.ITelephony
 *  com.android.internal.telephony.ITelephony$Stub
 *  com.android.internal.telephony.PhoneConstants
 *  com.android.internal.telephony.PhoneConstants$State
 *  com.android.internal.util.ArrayUtils
 *  com.android.internal.util.AsyncChannel
 */
package com.android.internal.telephony.dataconnection;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.LinkProperties;
import android.net.NetworkCapabilities;
import android.net.NetworkConfig;
import android.net.NetworkRequest;
import android.net.ProxyInfo;
import android.net.TrafficStats;
import android.net.Uri;
import android.os.AsyncResult;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.PersistableBundle;
import android.os.RegistrantList;
import android.os.ServiceManager;
import android.os.SystemClock;
import android.os.SystemProperties;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.provider.Telephony;
import android.telephony.CarrierConfigManager;
import android.telephony.CellLocation;
import android.telephony.DataFailCause;
import android.telephony.NetworkRegistrationInfo;
import android.telephony.PcoData;
import android.telephony.Rlog;
import android.telephony.ServiceState;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.telephony.cdma.CdmaCellLocation;
import android.telephony.data.ApnSetting;
import android.telephony.data.DataProfile;
import android.telephony.gsm.GsmCellLocation;
import android.text.TextUtils;
import android.util.EventLog;
import android.util.LocalLog;
import android.util.Pair;
import android.util.SparseArray;
import android.view.Window;
import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.telephony.CallTracker;
import com.android.internal.telephony.CarrierSignalAgent;
import com.android.internal.telephony.CommandsInterface;
import com.android.internal.telephony.DctConstants;
import com.android.internal.telephony.GsmCdmaPhone;
import com.android.internal.telephony.ITelephony;
import com.android.internal.telephony.Phone;
import com.android.internal.telephony.PhoneConstants;
import com.android.internal.telephony.PhoneFactory;
import com.android.internal.telephony.PhoneSwitcher;
import com.android.internal.telephony.ServiceStateTracker;
import com.android.internal.telephony.SettingsObserver;
import com.android.internal.telephony.dataconnection.ApnContext;
import com.android.internal.telephony.dataconnection.ApnSettingUtils;
import com.android.internal.telephony.dataconnection.DataConnection;
import com.android.internal.telephony.dataconnection.DataConnectionReasons;
import com.android.internal.telephony.dataconnection.DataEnabledSettings;
import com.android.internal.telephony.dataconnection.DataServiceManager;
import com.android.internal.telephony.dataconnection.DcController;
import com.android.internal.telephony.dataconnection.DcTesterFailBringUpAll;
import com.android.internal.telephony.dataconnection.TransportManager;
import com.android.internal.telephony.metrics.TelephonyMetrics;
import com.android.internal.telephony.test.SimulatedRadioControl;
import com.android.internal.telephony.uicc.IccRecords;
import com.android.internal.telephony.uicc.UiccController;
import com.android.internal.util.ArrayUtils;
import com.android.internal.util.AsyncChannel;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class DcTracker
extends Handler {
    static final String APN_ID = "apn_id";
    static final String DATA_COMPLETE_MSG_EXTRA_NETWORK_REQUEST = "extra_network_request";
    static final String DATA_COMPLETE_MSG_EXTRA_REQUEST_TYPE = "extra_request_type";
    static final String DATA_COMPLETE_MSG_EXTRA_SUCCESS = "extra_success";
    static final String DATA_COMPLETE_MSG_EXTRA_TRANSPORT_TYPE = "extra_transport_type";
    private static final int DATA_STALL_ALARM_AGGRESSIVE_DELAY_IN_MS_DEFAULT = 60000;
    private static final int DATA_STALL_ALARM_NON_AGGRESSIVE_DELAY_IN_MS_DEFAULT = 360000;
    private static final boolean DATA_STALL_NOT_SUSPECTED = false;
    private static final boolean DATA_STALL_SUSPECTED = true;
    private static final boolean DBG = true;
    private static final String DEBUG_PROV_APN_ALARM = "persist.debug.prov_apn_alarm";
    private static final String INTENT_DATA_STALL_ALARM = "com.android.internal.telephony.data-stall";
    private static final String INTENT_DATA_STALL_ALARM_EXTRA_TAG = "data_stall_alarm_extra_tag";
    private static final String INTENT_DATA_STALL_ALARM_EXTRA_TRANSPORT_TYPE = "data_stall_alarm_extra_transport_type";
    private static final String INTENT_PROVISIONING_APN_ALARM = "com.android.internal.telephony.provisioning_apn_alarm";
    private static final String INTENT_RECONNECT_ALARM = "com.android.internal.telephony.data-reconnect";
    private static final String INTENT_RECONNECT_ALARM_EXTRA_REASON = "reconnect_alarm_extra_reason";
    private static final String INTENT_RECONNECT_ALARM_EXTRA_TRANSPORT_TYPE = "reconnect_alarm_extra_transport_type";
    private static final String INTENT_RECONNECT_ALARM_EXTRA_TYPE = "reconnect_alarm_extra_type";
    private static final int NETWORK_TYPE_CBS = 12;
    private static final int NETWORK_TYPE_DEFAULT = 0;
    private static final int NETWORK_TYPE_DUN = 4;
    private static final int NETWORK_TYPE_EMERGENCY = 15;
    private static final int NETWORK_TYPE_FOTA = 10;
    private static final int NETWORK_TYPE_HIPRI = 5;
    private static final int NETWORK_TYPE_IA = 14;
    private static final int NETWORK_TYPE_IMS = 11;
    private static final int NETWORK_TYPE_MCX = 1001;
    private static final int NETWORK_TYPE_MMS = 2;
    private static final int NETWORK_TYPE_SUPL = 3;
    private static final int NUMBER_SENT_PACKETS_OF_HANG = 10;
    private static final int POLL_NETSTAT_MILLIS = 1000;
    private static final int POLL_NETSTAT_SCREEN_OFF_MILLIS = 600000;
    private static final int POLL_PDP_MILLIS = 5000;
    static final Uri PREFERAPN_NO_UPDATE_URI_USING_SUBID;
    private static final int PROVISIONING_APN_ALARM_DELAY_IN_MS_DEFAULT = 900000;
    private static final String PROVISIONING_APN_ALARM_TAG_EXTRA = "provisioning.apn.alarm.tag";
    private static final int PROVISIONING_SPINNER_TIMEOUT_MILLIS = 120000;
    private static final String PUPPET_MASTER_RADIO_STRESS_TEST = "gsm.defaultpdpcontext.active";
    private static final boolean RADIO_TESTS = false;
    private static final int RECOVERY_ACTION_CLEANUP = 1;
    private static final int RECOVERY_ACTION_GET_DATA_CALL_LIST = 0;
    private static final int RECOVERY_ACTION_RADIO_RESTART = 3;
    private static final int RECOVERY_ACTION_REREGISTER = 2;
    public static final int RELEASE_TYPE_DETACH = 2;
    public static final int RELEASE_TYPE_HANDOVER = 3;
    public static final int RELEASE_TYPE_NORMAL = 1;
    public static final int REQUEST_TYPE_HANDOVER = 2;
    public static final int REQUEST_TYPE_NORMAL = 1;
    private static final boolean VDBG = false;
    private static final boolean VDBG_STALL = false;
    private static int sEnableFailFastRefCounter;
    public AtomicBoolean isCleanupRequired = new AtomicBoolean(false);
    private DctConstants.Activity mActivity = DctConstants.Activity.NONE;
    private final AlarmManager mAlarmManager;
    private ArrayList<ApnSetting> mAllApnSettings = new ArrayList();
    private RegistrantList mAllDataDisconnectedRegistrants = new RegistrantList();
    private final ConcurrentHashMap<String, ApnContext> mApnContexts = new ConcurrentHashMap();
    private final SparseArray<ApnContext> mApnContextsByType = new SparseArray();
    private ApnChangeObserver mApnObserver;
    private final LocalLog mApnSettingsInitializationLog = new LocalLog(50);
    private HashMap<String, Integer> mApnToDataConnectionId = new HashMap();
    private AtomicBoolean mAttached = new AtomicBoolean(false);
    private AtomicBoolean mAutoAttachEnabled = new AtomicBoolean(false);
    private boolean mAutoAttachOnCreationConfig = false;
    private boolean mCanSetPreferApn = false;
    private final Handler mDataConnectionTracker;
    private HashMap<Integer, DataConnection> mDataConnections = new HashMap();
    private final DataEnabledSettings mDataEnabledSettings;
    private final LocalLog mDataRoamingLeakageLog = new LocalLog(50);
    private boolean mDataServiceBound = false;
    private final DataServiceManager mDataServiceManager;
    private PendingIntent mDataStallAlarmIntent = null;
    private int mDataStallAlarmTag = (int)SystemClock.elapsedRealtime();
    private volatile boolean mDataStallNoRxEnabled = true;
    private TxRxSum mDataStallTxRxSum = new TxRxSum(0L, 0L);
    private DcTesterFailBringUpAll mDcTesterFailBringUpAll;
    private DcController mDcc;
    private int mDisconnectPendingCount = 0;
    private DataStallRecoveryHandler mDsRecoveryHandler;
    private ApnSetting mEmergencyApn = null;
    private volatile boolean mFailFast = false;
    private final AtomicReference<IccRecords> mIccRecords = new AtomicReference();
    private boolean mInVoiceCall = false;
    private final BroadcastReceiver mIntentReceiver = new BroadcastReceiver(){

        public void onReceive(Context object, Intent object2) {
            object = object2.getAction();
            if (((String)object).equals("android.intent.action.SCREEN_ON")) {
                DcTracker.this.log("screen on");
                DcTracker.this.mIsScreenOn = true;
                DcTracker.this.stopNetStatPoll();
                DcTracker.this.startNetStatPoll();
                DcTracker.this.restartDataStallAlarm();
            } else if (((String)object).equals("android.intent.action.SCREEN_OFF")) {
                DcTracker.this.log("screen off");
                DcTracker.this.mIsScreenOn = false;
                DcTracker.this.stopNetStatPoll();
                DcTracker.this.startNetStatPoll();
                DcTracker.this.restartDataStallAlarm();
            } else if (((String)object).startsWith(DcTracker.INTENT_RECONNECT_ALARM)) {
                DcTracker.this.onActionIntentReconnectAlarm(object2);
            } else if (((String)object).equals(DcTracker.INTENT_DATA_STALL_ALARM)) {
                DcTracker.this.onActionIntentDataStallAlarm(object2);
            } else if (((String)object).equals(DcTracker.INTENT_PROVISIONING_APN_ALARM)) {
                DcTracker.this.log("Provisioning apn alarm");
                DcTracker.this.onActionIntentProvisioningApnAlarm(object2);
            } else if (((String)object).equals("android.telephony.action.CARRIER_CONFIG_CHANGED")) {
                DcTracker.this.log("received carrier config change");
                if (DcTracker.this.mIccRecords.get() != null && ((IccRecords)DcTracker.this.mIccRecords.get()).getRecordsLoaded()) {
                    DcTracker.this.setDefaultDataRoamingEnabled();
                }
            } else {
                object2 = DcTracker.this;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("onReceive: Unknown action=");
                stringBuilder.append((String)object);
                ((DcTracker)((Object)object2)).log(stringBuilder.toString());
            }
        }
    };
    private boolean mIsDisposed = false;
    private boolean mIsProvisioning = false;
    private boolean mIsPsRestricted = false;
    private boolean mIsScreenOn = true;
    private ArrayList<DataProfile> mLastDataProfileList = new ArrayList();
    private final String mLogTag;
    private boolean mNetStatPollEnabled = false;
    private int mNetStatPollPeriod;
    private int mNoRecvPollCount = 0;
    private final DctOnSubscriptionsChangedListener mOnSubscriptionsChangedListener = new DctOnSubscriptionsChangedListener();
    private final Phone mPhone;
    private final Runnable mPollNetStat = new Runnable(){

        @Override
        public void run() {
            DcTracker.this.updateDataActivity();
            if (DcTracker.this.mIsScreenOn) {
                DcTracker dcTracker = DcTracker.this;
                dcTracker.mNetStatPollPeriod = Settings.Global.getInt((ContentResolver)dcTracker.mResolver, (String)"pdp_watchdog_poll_interval_ms", (int)1000);
            } else {
                DcTracker dcTracker = DcTracker.this;
                dcTracker.mNetStatPollPeriod = Settings.Global.getInt((ContentResolver)dcTracker.mResolver, (String)"pdp_watchdog_long_poll_interval_ms", (int)600000);
            }
            if (DcTracker.this.mNetStatPollEnabled) {
                DcTracker.this.mDataConnectionTracker.postDelayed((Runnable)this, (long)DcTracker.this.mNetStatPollPeriod);
            }
        }
    };
    private ApnSetting mPreferredApn = null;
    private final PriorityQueue<ApnContext> mPrioritySortedApnContexts = new PriorityQueue<ApnContext>(5, new Comparator<ApnContext>(){

        @Override
        public int compare(ApnContext apnContext, ApnContext apnContext2) {
            return apnContext2.priority - apnContext.priority;
        }
    });
    private final String mProvisionActionName;
    private BroadcastReceiver mProvisionBroadcastReceiver;
    private PendingIntent mProvisioningApnAlarmIntent = null;
    private int mProvisioningApnAlarmTag = (int)SystemClock.elapsedRealtime();
    private ProgressDialog mProvisioningSpinner;
    private String mProvisioningUrl = null;
    private PendingIntent mReconnectIntent = null;
    private AsyncChannel mReplyAc = new AsyncChannel();
    private final Map<Integer, List<Message>> mRequestNetworkCompletionMsgs = new HashMap<Integer, List<Message>>();
    private int mRequestedApnType = 17;
    private boolean mReregisterOnReconnectFailure = false;
    private ContentResolver mResolver;
    private long mRxPkts;
    private long mSentSinceLastRecv;
    private final SettingsObserver mSettingsObserver;
    private DctConstants.State mState = DctConstants.State.IDLE;
    private SubscriptionManager mSubscriptionManager;
    private final TelephonyManager mTelephonyManager;
    private final int mTransportType;
    private long mTxPkts;
    private final UiccController mUiccController;
    private AtomicInteger mUniqueIdGenerator = new AtomicInteger(0);

    static {
        sEnableFailFastRefCounter = 0;
        PREFERAPN_NO_UPDATE_URI_USING_SUBID = Uri.parse((String)"content://telephony/carriers/preferapn_no_update/subId/");
    }

    @VisibleForTesting
    public DcTracker() {
        this.mLogTag = "DCT";
        this.mTelephonyManager = null;
        this.mAlarmManager = null;
        this.mPhone = null;
        this.mUiccController = null;
        this.mDataConnectionTracker = null;
        this.mProvisionActionName = null;
        this.mSettingsObserver = new SettingsObserver(null, this);
        this.mDataEnabledSettings = null;
        this.mTransportType = 0;
        this.mDataServiceManager = null;
    }

    public DcTracker(Phone phone, int n) {
        this.mPhone = phone;
        this.log("DCT.constructor");
        this.mTelephonyManager = TelephonyManager.from((Context)phone.getContext()).createForSubscriptionId(phone.getSubId());
        Object object = new StringBuilder();
        ((StringBuilder)object).append("-");
        CharSequence charSequence = n == 1 ? "C" : "I";
        ((StringBuilder)object).append((String)charSequence);
        object = ((StringBuilder)object).toString();
        charSequence = object;
        if (this.mTelephonyManager.getPhoneCount() > 1) {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append((String)object);
            ((StringBuilder)charSequence).append("-");
            ((StringBuilder)charSequence).append(this.mPhone.getPhoneId());
            charSequence = ((StringBuilder)charSequence).toString();
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("DCT");
        ((StringBuilder)object).append((String)charSequence);
        this.mLogTag = ((StringBuilder)object).toString();
        this.mTransportType = n;
        this.mDataServiceManager = new DataServiceManager(phone, n, (String)charSequence);
        this.mResolver = this.mPhone.getContext().getContentResolver();
        this.mUiccController = UiccController.getInstance();
        this.mUiccController.registerForIccChanged(this, 270369, null);
        this.mAlarmManager = (AlarmManager)this.mPhone.getContext().getSystemService("alarm");
        this.mDsRecoveryHandler = new DataStallRecoveryHandler();
        object = new IntentFilter();
        object.addAction("android.intent.action.SCREEN_ON");
        object.addAction("android.intent.action.SCREEN_OFF");
        object.addAction(INTENT_DATA_STALL_ALARM);
        object.addAction(INTENT_PROVISIONING_APN_ALARM);
        object.addAction("android.telephony.action.CARRIER_CONFIG_CHANGED");
        this.mDataEnabledSettings = this.mPhone.getDataEnabledSettings();
        this.mDataEnabledSettings.registerForDataEnabledChanged(this, 270382, null);
        this.mDataEnabledSettings.registerForDataEnabledOverrideChanged(this, 270387);
        this.mPhone.getContext().registerReceiver(this.mIntentReceiver, (IntentFilter)object, null, (Handler)this.mPhone);
        object = PreferenceManager.getDefaultSharedPreferences((Context)this.mPhone.getContext());
        this.mAutoAttachEnabled.set(object.getBoolean("disabled_on_boot_key", false));
        this.mSubscriptionManager = SubscriptionManager.from((Context)this.mPhone.getContext());
        this.mSubscriptionManager.addOnSubscriptionsChangedListener((SubscriptionManager.OnSubscriptionsChangedListener)this.mOnSubscriptionsChangedListener);
        object = new HandlerThread("DcHandlerThread");
        object.start();
        object = new Handler(object.getLooper());
        this.mDcc = DcController.makeDcc(this.mPhone, this, this.mDataServiceManager, (Handler)object, (String)charSequence);
        this.mDcTesterFailBringUpAll = new DcTesterFailBringUpAll(this.mPhone, (Handler)object);
        this.mDataConnectionTracker = this;
        this.registerForAllEvents();
        this.update();
        this.mApnObserver = new ApnChangeObserver();
        phone.getContext().getContentResolver().registerContentObserver(Telephony.Carriers.CONTENT_URI, true, (ContentObserver)this.mApnObserver);
        this.initApnContexts();
        for (ApnContext apnContext : this.mApnContexts.values()) {
            IntentFilter intentFilter = new IntentFilter();
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("com.android.internal.telephony.data-reconnect.");
            ((StringBuilder)charSequence).append(apnContext.getApnType());
            intentFilter.addAction(((StringBuilder)charSequence).toString());
            this.mPhone.getContext().registerReceiver(this.mIntentReceiver, intentFilter, null, (Handler)this.mPhone);
        }
        this.initEmergencyApnSetting();
        this.addEmergencyApnSetting();
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append("com.android.internal.telephony.PROVISION");
        ((StringBuilder)charSequence).append(phone.getPhoneId());
        this.mProvisionActionName = ((StringBuilder)charSequence).toString();
        this.mSettingsObserver = new SettingsObserver(this.mPhone.getContext(), this);
        this.registerSettingsObserver();
    }

    private ApnContext addApnContext(String string, NetworkConfig object) {
        object = new ApnContext(this.mPhone, string, this.mLogTag, (NetworkConfig)object, this);
        this.mApnContexts.put(string, (ApnContext)object);
        this.mApnContextsByType.put(ApnSetting.getApnTypesBitmaskFromString((String)string), object);
        this.mPrioritySortedApnContexts.add((ApnContext)object);
        return object;
    }

    private void addEmergencyApnSetting() {
        if (this.mEmergencyApn != null) {
            Object object = this.mAllApnSettings.iterator();
            while (object.hasNext()) {
                if (!object.next().canHandleType(512)) continue;
                this.log("addEmergencyApnSetting - E-APN setting is already present");
                return;
            }
            if (!this.mAllApnSettings.contains((Object)this.mEmergencyApn)) {
                this.mAllApnSettings.add(this.mEmergencyApn);
                object = new StringBuilder();
                ((StringBuilder)object).append("Adding emergency APN : ");
                ((StringBuilder)object).append((Object)this.mEmergencyApn);
                this.log(((StringBuilder)object).toString());
                return;
            }
        }
    }

    private void addRequestNetworkCompleteMsg(Message message, int n) {
        if (message != null) {
            List<Message> list;
            List<Message> list2 = list = this.mRequestNetworkCompletionMsgs.get(n);
            if (list == null) {
                list2 = new ArrayList<Message>();
            }
            list2.add(message);
            this.mRequestNetworkCompletionMsgs.put(n, list2);
        }
    }

    private String apnListToString(ArrayList<ApnSetting> arrayList) {
        StringBuilder stringBuilder = new StringBuilder();
        int n = arrayList.size();
        for (int i = 0; i < n; ++i) {
            stringBuilder.append('[');
            stringBuilder.append(arrayList.get(i).toString());
            stringBuilder.append(']');
        }
        return stringBuilder.toString();
    }

    private ArrayList<ApnSetting> buildWaitingApns(String object, int n) {
        boolean bl;
        ApnSetting apnSetting;
        Serializable serializable = new StringBuilder();
        ((StringBuilder)serializable).append("buildWaitingApns: E requestedApnType=");
        ((StringBuilder)serializable).append((String)object);
        this.log(((StringBuilder)serializable).toString());
        serializable = new ArrayList();
        int n2 = ApnSetting.getApnTypesBitmaskFromString((String)object);
        if (n2 == 8 && ((ArrayList)(object = this.fetchDunApns())).size() > 0) {
            Iterator<ApnSetting> iterator = ((ArrayList)object).iterator();
            while (iterator.hasNext()) {
                ((ArrayList)serializable).add(iterator.next());
                object = new StringBuilder();
                ((StringBuilder)object).append("buildWaitingApns: X added APN_TYPE_DUN apnList=");
                ((StringBuilder)object).append(serializable);
                this.log(((StringBuilder)object).toString());
            }
            return this.sortApnListByPreferred((ArrayList<ApnSetting>)serializable);
        }
        IccRecords object22 = this.mIccRecords.get();
        object = object22 != null ? object22.getOperatorNumeric() : "";
        try {
            bl = this.mPhone.getContext().getResources().getBoolean(17891415);
            bl ^= true;
        }
        catch (Resources.NotFoundException notFoundException) {
            this.log("buildWaitingApns: usePreferred NotFoundException set to true");
            bl = true;
        }
        if (bl) {
            this.mPreferredApn = this.getPreferredApn();
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("buildWaitingApns: usePreferred=");
        stringBuilder.append(bl);
        stringBuilder.append(" canSetPreferApn=");
        stringBuilder.append(this.mCanSetPreferApn);
        stringBuilder.append(" mPreferredApn=");
        stringBuilder.append((Object)this.mPreferredApn);
        stringBuilder.append(" operator=");
        stringBuilder.append((String)object);
        stringBuilder.append(" radioTech=");
        stringBuilder.append(n);
        stringBuilder.append(" IccRecords r=");
        stringBuilder.append(object22);
        this.log(stringBuilder.toString());
        if (bl && this.mCanSetPreferApn && (apnSetting = this.mPreferredApn) != null && apnSetting.canHandleType(n2)) {
            StringBuilder stringBuilder2 = new StringBuilder();
            stringBuilder2.append("buildWaitingApns: Preferred APN:");
            stringBuilder2.append((String)object);
            stringBuilder2.append(":");
            stringBuilder2.append(this.mPreferredApn.getOperatorNumeric());
            stringBuilder2.append(":");
            stringBuilder2.append((Object)this.mPreferredApn);
            this.log(stringBuilder2.toString());
            if (this.mPreferredApn.getOperatorNumeric().equals(object)) {
                if (this.mPreferredApn.canSupportNetworkType(ServiceState.rilRadioTechnologyToNetworkType((int)n))) {
                    ((ArrayList)serializable).add(this.mPreferredApn);
                    serializable = this.sortApnListByPreferred((ArrayList<ApnSetting>)serializable);
                    object = new StringBuilder();
                    ((StringBuilder)object).append("buildWaitingApns: X added preferred apnList=");
                    ((StringBuilder)object).append(serializable);
                    this.log(((StringBuilder)object).toString());
                    return serializable;
                }
                this.log("buildWaitingApns: no preferred APN");
                this.setPreferredApn(-1);
                this.mPreferredApn = null;
            } else {
                this.log("buildWaitingApns: no preferred APN");
                this.setPreferredApn(-1);
                this.mPreferredApn = null;
            }
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("buildWaitingApns: mAllApnSettings=");
        ((StringBuilder)object).append(this.mAllApnSettings);
        this.log(((StringBuilder)object).toString());
        for (ApnSetting apnSetting2 : this.mAllApnSettings) {
            if (!apnSetting2.canHandleType(n2)) continue;
            if (apnSetting2.canSupportNetworkType(ServiceState.rilRadioTechnologyToNetworkType((int)n))) {
                ((ArrayList)serializable).add(apnSetting2);
                continue;
            }
            stringBuilder = new StringBuilder();
            stringBuilder.append("buildWaitingApns: networkTypeBitmask:");
            stringBuilder.append(apnSetting2.getNetworkTypeBitmask());
            stringBuilder.append(" does not include radioTech:");
            stringBuilder.append(ServiceState.rilRadioTechnologyToString((int)n));
            this.log(stringBuilder.toString());
        }
        serializable = this.sortApnListByPreferred((ArrayList<ApnSetting>)serializable);
        object = new StringBuilder();
        ((StringBuilder)object).append("buildWaitingApns: ");
        ((StringBuilder)object).append(((ArrayList)serializable).size());
        ((StringBuilder)object).append(" APNs in the list: ");
        ((StringBuilder)object).append(serializable);
        this.log(((StringBuilder)object).toString());
        return serializable;
    }

    private void cancelReconnectAlarm(ApnContext apnContext) {
        if (apnContext == null) {
            return;
        }
        PendingIntent pendingIntent = apnContext.getReconnectIntent();
        if (pendingIntent != null) {
            ((AlarmManager)this.mPhone.getContext().getSystemService("alarm")).cancel(pendingIntent);
            apnContext.setReconnectIntent(null);
        }
    }

    /*
     * WARNING - void declaration
     */
    private void checkDataRoamingStatus(boolean bl) {
        if (!bl && !this.getDataRoamingEnabled() && this.mPhone.getServiceState().getDataRoaming()) {
            for (ApnContext apnContext : this.mApnContexts.values()) {
                void var3_7;
                if (apnContext.getState() != DctConstants.State.CONNECTED) continue;
                LocalLog localLog = this.mDataRoamingLeakageLog;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("PossibleRoamingLeakage  connection params: ");
                if (apnContext.getDataConnection() != null) {
                    DataConnection.ConnectionParams connectionParams = apnContext.getDataConnection().getConnectionParams();
                } else {
                    String string = "";
                }
                stringBuilder.append(var3_7);
                localLog.log(stringBuilder.toString());
            }
        }
    }

    private DataConnection checkForCompatibleConnectedApnContext(ApnContext object) {
        int n = ((ApnContext)object).getApnTypeBitmask();
        ArrayList<ApnSetting> arrayList = null;
        if (8 == n) {
            arrayList = this.sortApnListByPreferred(this.fetchDunApns());
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("checkForCompatibleConnectedApnContext: apnContext=");
        stringBuilder.append(object);
        this.log(stringBuilder.toString());
        stringBuilder = null;
        ApnContext apnContext = null;
        for (ApnContext apnContext2 : this.mApnContexts.values()) {
            DataConnection dataConnection = apnContext2.getDataConnection();
            Object object2 = stringBuilder;
            ApnContext apnContext3 = apnContext;
            if (dataConnection != null) {
                int n2;
                ApnSetting apnSetting = apnContext2.getApnSetting();
                object2 = new StringBuilder();
                object2.append("apnSetting: ");
                object2.append((Object)apnSetting);
                this.log(object2.toString());
                if (arrayList != null && arrayList.size() > 0) {
                    Iterator<ApnSetting> iterator = arrayList.iterator();
                    while (iterator.hasNext()) {
                        object2 = stringBuilder;
                        apnContext3 = apnContext;
                        if (iterator.next().equals((Object)apnSetting)) {
                            n2 = 5.$SwitchMap$com$android$internal$telephony$DctConstants$State[apnContext2.getState().ordinal()];
                            if (n2 != 1) {
                                if (n2 != 3) {
                                    object2 = stringBuilder;
                                    apnContext3 = apnContext;
                                } else {
                                    object2 = dataConnection;
                                    apnContext3 = apnContext2;
                                }
                            } else {
                                object = new StringBuilder();
                                ((StringBuilder)object).append("checkForCompatibleConnectedApnContext: found dun conn=");
                                ((StringBuilder)object).append((Object)dataConnection);
                                ((StringBuilder)object).append(" curApnCtx=");
                                ((StringBuilder)object).append(apnContext2);
                                this.log(((StringBuilder)object).toString());
                                return dataConnection;
                            }
                        }
                        stringBuilder = object2;
                        apnContext = apnContext3;
                    }
                    object2 = stringBuilder;
                    apnContext3 = apnContext;
                } else {
                    object2 = stringBuilder;
                    apnContext3 = apnContext;
                    if (apnSetting != null) {
                        object2 = stringBuilder;
                        apnContext3 = apnContext;
                        if (apnSetting.canHandleType(n)) {
                            n2 = 5.$SwitchMap$com$android$internal$telephony$DctConstants$State[apnContext2.getState().ordinal()];
                            if (n2 != 1) {
                                if (n2 != 3) {
                                    object2 = stringBuilder;
                                    apnContext3 = apnContext;
                                } else {
                                    object2 = dataConnection;
                                    apnContext3 = apnContext2;
                                }
                            } else {
                                object = new StringBuilder();
                                ((StringBuilder)object).append("checkForCompatibleConnectedApnContext: found canHandle conn=");
                                ((StringBuilder)object).append((Object)dataConnection);
                                ((StringBuilder)object).append(" curApnCtx=");
                                ((StringBuilder)object).append(apnContext2);
                                this.log(((StringBuilder)object).toString());
                                return dataConnection;
                            }
                        }
                    }
                }
            }
            stringBuilder = object2;
            apnContext = apnContext3;
        }
        if (stringBuilder != null) {
            object = new StringBuilder();
            ((StringBuilder)object).append("checkForCompatibleConnectedApnContext: found potential conn=");
            ((StringBuilder)object).append((Object)stringBuilder);
            ((StringBuilder)object).append(" curApnCtx=");
            ((StringBuilder)object).append(apnContext);
            this.log(((StringBuilder)object).toString());
            return stringBuilder;
        }
        stringBuilder = new StringBuilder();
        stringBuilder.append("checkForCompatibleConnectedApnContext: NO conn apnContext=");
        stringBuilder.append(object);
        this.log(stringBuilder.toString());
        return null;
    }

    private boolean cleanUpAllConnectionsInternal(boolean bl, String charSequence) {
        boolean bl2;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("cleanUpAllConnectionsInternal: detach=");
        stringBuilder.append(bl);
        stringBuilder.append(" reason=");
        stringBuilder.append((String)charSequence);
        this.log(stringBuilder.toString());
        boolean bl3 = false;
        boolean bl4 = false;
        if (!TextUtils.isEmpty((CharSequence)charSequence)) {
            bl2 = ((String)charSequence).equals("specificDisabled") || ((String)charSequence).equals("roamingOn") || ((String)charSequence).equals("carrierActionDisableMeteredApn");
            bl4 = bl2;
        }
        Iterator<ApnContext> iterator = this.mApnContexts.values().iterator();
        bl2 = bl3;
        while (iterator.hasNext()) {
            ApnContext apnContext = iterator.next();
            if (((String)charSequence).equals("SinglePdnArbitration") && apnContext.getApnType().equals("ims")) continue;
            if (this.shouldCleanUpConnection(apnContext, bl4)) {
                if (!apnContext.isDisconnected()) {
                    bl2 = true;
                }
                apnContext.setReason((String)charSequence);
                this.cleanUpConnectionInternal(bl, 2, apnContext);
                continue;
            }
            stringBuilder = new StringBuilder();
            stringBuilder.append("cleanUpAllConnectionsInternal: APN type ");
            stringBuilder.append(apnContext.getApnType());
            stringBuilder.append(" shouldn't be cleaned up.");
            this.log(stringBuilder.toString());
        }
        this.stopNetStatPoll();
        this.stopDataStallAlarm();
        this.mRequestedApnType = 17;
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append("cleanUpAllConnectionsInternal: mDisconnectPendingCount = ");
        ((StringBuilder)charSequence).append(this.mDisconnectPendingCount);
        this.log(((StringBuilder)charSequence).toString());
        if (bl && this.mDisconnectPendingCount == 0) {
            this.notifyAllDataDisconnected();
        }
        return bl2;
    }

    private void cleanUpConnectionInternal(boolean bl, int n, ApnContext apnContext) {
        if (apnContext == null) {
            this.log("cleanUpConnectionInternal: apn context is null");
            return;
        }
        Object object = apnContext.getDataConnection();
        CharSequence charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append("cleanUpConnectionInternal: detach=");
        ((StringBuilder)charSequence).append(bl);
        ((StringBuilder)charSequence).append(" reason=");
        ((StringBuilder)charSequence).append(apnContext.getReason());
        apnContext.requestLog(((StringBuilder)charSequence).toString());
        if (bl) {
            boolean bl2 = apnContext.isDisconnected();
            charSequence = "";
            if (bl2) {
                apnContext.releaseDataConnection("");
            } else if (object != null) {
                if (apnContext.getState() != DctConstants.State.DISCONNECTING) {
                    int n2;
                    int n3 = n2 = 0;
                    if ("dun".equals(apnContext.getApnType())) {
                        n3 = n2;
                        if (ServiceState.isCdma((int)this.getDataRat())) {
                            this.log("cleanUpConnectionInternal: disconnectAll DUN connection");
                            n3 = 1;
                        }
                    }
                    n2 = apnContext.getConnectionGeneration();
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("cleanUpConnectionInternal: tearing down");
                    if (n3 != 0) {
                        charSequence = " all";
                    }
                    stringBuilder.append((String)charSequence);
                    stringBuilder.append(" using gen#");
                    stringBuilder.append(n2);
                    charSequence = stringBuilder.toString();
                    stringBuilder = new StringBuilder();
                    stringBuilder.append((String)charSequence);
                    stringBuilder.append("apnContext=");
                    stringBuilder.append(apnContext);
                    this.log(stringBuilder.toString());
                    apnContext.requestLog((String)charSequence);
                    charSequence = this.obtainMessage(270351, (Object)new Pair((Object)apnContext, (Object)n2));
                    if (n3 == 0 && n != 3) {
                        ((DataConnection)((Object)object)).tearDown(apnContext, apnContext.getReason(), (Message)charSequence);
                    } else {
                        ((DataConnection)((Object)object)).tearDownAll(apnContext.getReason(), n, (Message)charSequence);
                    }
                    apnContext.setState(DctConstants.State.DISCONNECTING);
                    ++this.mDisconnectPendingCount;
                }
            } else {
                apnContext.setState(DctConstants.State.IDLE);
                apnContext.requestLog("cleanUpConnectionInternal: connected, bug no dc");
                this.mPhone.notifyDataConnection(apnContext.getApnType());
            }
        } else {
            if (object != null) {
                ((DataConnection)((Object)object)).reset();
            }
            apnContext.setState(DctConstants.State.IDLE);
            this.mPhone.notifyDataConnection(apnContext.getApnType());
            apnContext.setDataConnection(null);
        }
        if (object != null) {
            this.cancelReconnectAlarm(apnContext);
        }
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append("cleanUpConnectionInternal: X detach=");
        ((StringBuilder)charSequence).append(bl);
        ((StringBuilder)charSequence).append(" reason=");
        ((StringBuilder)charSequence).append(apnContext.getReason());
        object = ((StringBuilder)charSequence).toString();
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append((String)object);
        ((StringBuilder)charSequence).append(" apnContext=");
        ((StringBuilder)charSequence).append(apnContext);
        ((StringBuilder)charSequence).append(" dc=");
        ((StringBuilder)charSequence).append((Object)apnContext.getDataConnection());
        this.log(((StringBuilder)charSequence).toString());
    }

    private void cleanUpConnectionsOnUpdatedApns(boolean bl, String charSequence) {
        Serializable serializable = new StringBuilder();
        ((StringBuilder)serializable).append("cleanUpConnectionsOnUpdatedApns: detach=");
        ((StringBuilder)serializable).append(bl);
        this.log(((StringBuilder)serializable).toString());
        if (this.mAllApnSettings.isEmpty()) {
            this.cleanUpAllConnectionsInternal(bl, "apnChanged");
        } else {
            if (this.getDataRat() == 0) {
                return;
            }
            for (ApnContext apnContext : this.mApnContexts.values()) {
                serializable = apnContext.getWaitingApns();
                ArrayList<ApnSetting> arrayList = this.buildWaitingApns(apnContext.getApnType(), this.getDataRat());
                if (serializable == null || arrayList.size() == ((ArrayList)serializable).size() && this.containsAllApns((ArrayList<ApnSetting>)serializable, arrayList)) continue;
                apnContext.setWaitingApns(arrayList);
                if (apnContext.isDisconnected()) continue;
                apnContext.setReason((String)charSequence);
                this.cleanUpConnectionInternal(true, 2, apnContext);
            }
        }
        if (!this.isConnected()) {
            this.stopNetStatPoll();
            this.stopDataStallAlarm();
        }
        this.mRequestedApnType = 17;
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append("mDisconnectPendingCount = ");
        ((StringBuilder)charSequence).append(this.mDisconnectPendingCount);
        this.log(((StringBuilder)charSequence).toString());
        if (bl && this.mDisconnectPendingCount == 0) {
            this.notifyAllDataDisconnected();
        }
    }

    private void completeConnection(ApnContext apnContext, int n) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("completeConnection: successful, notify the world apnContext=");
        stringBuilder.append(apnContext);
        this.log(stringBuilder.toString());
        if (this.mIsProvisioning && !TextUtils.isEmpty((CharSequence)this.mProvisioningUrl)) {
            stringBuilder = new StringBuilder();
            stringBuilder.append("completeConnection: MOBILE_PROVISIONING_ACTION url=");
            stringBuilder.append(this.mProvisioningUrl);
            this.log(stringBuilder.toString());
            stringBuilder = Intent.makeMainSelectorActivity((String)"android.intent.action.MAIN", (String)"android.intent.category.APP_BROWSER");
            stringBuilder.setData(Uri.parse((String)this.mProvisioningUrl));
            stringBuilder.setFlags(272629760);
            try {
                this.mPhone.getContext().startActivity((Intent)stringBuilder);
            }
            catch (ActivityNotFoundException activityNotFoundException) {
                stringBuilder = new StringBuilder();
                stringBuilder.append("completeConnection: startActivityAsUser failed");
                stringBuilder.append((Object)activityNotFoundException);
                this.loge(stringBuilder.toString());
            }
        }
        this.mIsProvisioning = false;
        this.mProvisioningUrl = null;
        stringBuilder = this.mProvisioningSpinner;
        if (stringBuilder != null) {
            this.sendMessage(this.obtainMessage(270378, (Object)stringBuilder));
        }
        if (n != 2) {
            this.mPhone.notifyDataConnection(apnContext.getApnType());
        }
        this.startNetStatPoll();
        this.startDataStallAlarm(false);
    }

    private boolean containsAllApns(ArrayList<ApnSetting> arrayList, ArrayList<ApnSetting> object) {
        object = ((ArrayList)object).iterator();
        while (object.hasNext()) {
            boolean bl;
            block2 : {
                ApnSetting apnSetting = (ApnSetting)object.next();
                boolean bl2 = false;
                Iterator<ApnSetting> iterator = arrayList.iterator();
                do {
                    bl = bl2;
                    if (!iterator.hasNext()) break block2;
                } while (!iterator.next().equals((Object)apnSetting, this.mPhone.getServiceState().getDataRoamingFromRegistration()));
                bl = true;
            }
            if (bl) continue;
            return false;
        }
        return true;
    }

    private void createAllApnList() {
        this.mAllApnSettings.clear();
        Object object = this.mIccRecords.get();
        object = object != null ? ((IccRecords)object).getOperatorNumeric() : "";
        ContentResolver contentResolver = this.mPhone.getContext().getContentResolver();
        Uri uri = Telephony.Carriers.SIM_APN_URI;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("filtered/subId/");
        stringBuilder.append(this.mPhone.getSubId());
        stringBuilder = contentResolver.query(Uri.withAppendedPath((Uri)uri, (String)stringBuilder.toString()), null, null, null, "_id");
        if (stringBuilder != null) {
            while (stringBuilder.moveToNext()) {
                contentResolver = ApnSetting.makeApnSetting((Cursor)stringBuilder);
                if (contentResolver == null) continue;
                this.mAllApnSettings.add((ApnSetting)contentResolver);
            }
            stringBuilder.close();
        } else {
            this.log("createAllApnList: cursor is null");
            contentResolver = this.mApnSettingsInitializationLog;
            stringBuilder = new StringBuilder();
            stringBuilder.append("cursor is null for carrier, operator: ");
            stringBuilder.append((String)object);
            contentResolver.log(stringBuilder.toString());
        }
        this.addEmergencyApnSetting();
        this.dedupeApnSettings();
        if (this.mAllApnSettings.isEmpty()) {
            stringBuilder = new StringBuilder();
            stringBuilder.append("createAllApnList: No APN found for carrier, operator: ");
            stringBuilder.append((String)object);
            this.log(stringBuilder.toString());
            contentResolver = this.mApnSettingsInitializationLog;
            stringBuilder = new StringBuilder();
            stringBuilder.append("no APN found for carrier, operator: ");
            stringBuilder.append((String)object);
            contentResolver.log(stringBuilder.toString());
            this.mPreferredApn = null;
        } else {
            this.mPreferredApn = this.getPreferredApn();
            stringBuilder = this.mPreferredApn;
            if (stringBuilder != null && !stringBuilder.getOperatorNumeric().equals(object)) {
                this.mPreferredApn = null;
                this.setPreferredApn(-1);
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("createAllApnList: mPreferredApn=");
            ((StringBuilder)object).append((Object)this.mPreferredApn);
            this.log(((StringBuilder)object).toString());
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("createAllApnList: X mAllApnSettings=");
        ((StringBuilder)object).append(this.mAllApnSettings);
        this.log(((StringBuilder)object).toString());
    }

    private DataConnection createDataConnection() {
        this.log("createDataConnection E");
        int n = this.mUniqueIdGenerator.getAndIncrement();
        DataConnection dataConnection = DataConnection.makeDataConnection(this.mPhone, n, this, this.mDataServiceManager, this.mDcTesterFailBringUpAll, this.mDcc);
        this.mDataConnections.put(n, dataConnection);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("createDataConnection() X id=");
        stringBuilder.append(n);
        stringBuilder.append(" dc=");
        stringBuilder.append((Object)dataConnection);
        this.log(stringBuilder.toString());
        return dataConnection;
    }

    @VisibleForTesting
    public static DataProfile createDataProfile(ApnSetting apnSetting, int n, boolean bl) {
        int n2 = apnSetting.getNetworkTypeBitmask();
        int n3 = n2 == 0 ? 0 : (ServiceState.bearerBitmapHasCdma((int)n2) ? 2 : 1);
        return new DataProfile.Builder().setProfileId(n).setApn(apnSetting.getApnName()).setProtocolType(apnSetting.getProtocol()).setAuthType(apnSetting.getAuthType()).setUserName(apnSetting.getUser()).setPassword(apnSetting.getPassword()).setType(n3).setMaxConnectionsTime(apnSetting.getMaxConnsTime()).setMaxConnections(apnSetting.getMaxConns()).setWaitTime(apnSetting.getWaitTime()).enable(apnSetting.isEnabled()).setSupportedApnTypesBitmask(apnSetting.getApnTypeBitmask()).setRoamingProtocolType(apnSetting.getRoamingProtocol()).setBearerBitmask(n2).setMtu(apnSetting.getMtu()).setPersistent(apnSetting.isPersistent()).setPreferred(bl).build();
    }

    private static DataProfile createDataProfile(ApnSetting apnSetting, boolean bl) {
        return DcTracker.createDataProfile(apnSetting, apnSetting.getProfileId(), bl);
    }

    private void dedupeApnSettings() {
        new ArrayList();
        for (int i = 0; i < this.mAllApnSettings.size() - 1; ++i) {
            ApnSetting apnSetting = this.mAllApnSettings.get(i);
            int n = i + 1;
            while (n < this.mAllApnSettings.size()) {
                ApnSetting apnSetting2 = this.mAllApnSettings.get(n);
                if (apnSetting.similar(apnSetting2)) {
                    apnSetting = this.mergeApns(apnSetting, apnSetting2);
                    this.mAllApnSettings.set(i, apnSetting);
                    this.mAllApnSettings.remove(n);
                    continue;
                }
                ++n;
            }
        }
    }

    private void destroyDataConnections() {
        if (this.mDataConnections != null) {
            this.log("destroyDataConnections: clear mDataConnectionList");
            this.mDataConnections.clear();
        } else {
            this.log("destroyDataConnections: mDataConnecitonList is empty, ignore");
        }
    }

    private DataConnection findFreeDataConnection() {
        for (DataConnection dataConnection : this.mDataConnections.values()) {
            Object object;
            boolean bl;
            block2 : {
                boolean bl2 = false;
                object = this.mApnContexts.values().iterator();
                do {
                    bl = bl2;
                    if (!object.hasNext()) break block2;
                } while (object.next().getDataConnection() != dataConnection);
                bl = true;
            }
            if (bl) continue;
            object = new StringBuilder();
            ((StringBuilder)object).append("findFreeDataConnection: found free DataConnection=");
            ((StringBuilder)object).append((Object)dataConnection);
            this.log(((StringBuilder)object).toString());
            return dataConnection;
        }
        this.log("findFreeDataConnection: NO free DataConnection");
        return null;
    }

    private int getApnProfileID(String string) {
        if (TextUtils.equals((CharSequence)string, (CharSequence)"ims")) {
            return 2;
        }
        if (TextUtils.equals((CharSequence)string, (CharSequence)"fota")) {
            return 3;
        }
        if (TextUtils.equals((CharSequence)string, (CharSequence)"cbs")) {
            return 4;
        }
        if (TextUtils.equals((CharSequence)string, (CharSequence)"ia")) {
            return 0;
        }
        return TextUtils.equals((CharSequence)string, (CharSequence)"dun");
    }

    private int getCellLocationId() {
        int n = -1;
        CellLocation cellLocation = this.mPhone.getCellLocation();
        int n2 = n;
        if (cellLocation != null) {
            if (cellLocation instanceof GsmCellLocation) {
                n2 = ((GsmCellLocation)cellLocation).getCid();
            } else {
                n2 = n;
                if (cellLocation instanceof CdmaCellLocation) {
                    n2 = ((CdmaCellLocation)cellLocation).getBaseStationId();
                }
            }
        }
        return n2;
    }

    private int getDataRat() {
        NetworkRegistrationInfo networkRegistrationInfo = this.mPhone.getServiceState().getNetworkRegistrationInfo(2, this.mTransportType);
        if (networkRegistrationInfo != null) {
            return ServiceState.networkTypeToRilRadioTechnology((int)networkRegistrationInfo.getAccessNetworkTechnology());
        }
        return 0;
    }

    private int getPreferredApnSetId() {
        int n;
        ContentResolver contentResolver = this.mPhone.getContext().getContentResolver();
        Uri uri = Telephony.Carriers.CONTENT_URI;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("preferapnset/subId/");
        stringBuilder.append(this.mPhone.getSubId());
        uri = contentResolver.query(Uri.withAppendedPath((Uri)uri, (String)stringBuilder.toString()), new String[]{"apn_set_id"}, null, null, null);
        if (uri == null) {
            this.loge("getPreferredApnSetId: cursor is null");
            return 0;
        }
        if (uri.getCount() < 1) {
            this.loge("getPreferredApnSetId: no APNs found");
            n = 0;
        } else {
            uri.moveToFirst();
            n = uri.getInt(0);
        }
        if (!uri.isClosed()) {
            uri.close();
        }
        return n;
    }

    private IccRecords getUiccRecords(int n) {
        return this.mUiccController.getIccRecords(this.mPhone.getPhoneId(), n);
    }

    private int getVoiceRat() {
        NetworkRegistrationInfo networkRegistrationInfo = this.mPhone.getServiceState().getNetworkRegistrationInfo(1, this.mTransportType);
        if (networkRegistrationInfo != null) {
            return ServiceState.networkTypeToRilRadioTechnology((int)networkRegistrationInfo.getAccessNetworkTechnology());
        }
        return 0;
    }

    private void handlePcoData(AsyncResult asyncResult) {
        if (asyncResult.exception != null) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("PCO_DATA exception: ");
            stringBuilder.append(asyncResult.exception);
            this.loge(stringBuilder.toString());
            return;
        }
        asyncResult = (PcoData)asyncResult.result;
        Object object = new ArrayList<DataConnection>();
        Object object2 = this.mDcc.getActiveDcByCid(asyncResult.cid);
        if (object2 != null) {
            ((ArrayList)object).add(object2);
        }
        if (((ArrayList)object).size() == 0) {
            object2 = new StringBuilder();
            ((StringBuilder)object2).append("PCO_DATA for unknown cid: ");
            ((StringBuilder)object2).append(asyncResult.cid);
            ((StringBuilder)object2).append(", inferring");
            this.loge(((StringBuilder)object2).toString());
            block0 : for (DataConnection object3 : this.mDataConnections.values()) {
                int n = object3.getCid();
                if (n == asyncResult.cid) {
                    ((ArrayList)object).clear();
                    ((ArrayList)object).add(object3);
                    break;
                }
                if (n != -1) continue;
                object2 = object3.getApnContexts().iterator();
                while (object2.hasNext()) {
                    if (((ApnContext)object2.next()).getState() != DctConstants.State.CONNECTING) continue;
                    ((ArrayList)object).add(object3);
                    continue block0;
                }
            }
        }
        if (((ArrayList)object).size() == 0) {
            this.loge("PCO_DATA - couldn't infer cid");
            return;
        }
        object = ((ArrayList)object).iterator();
        while (object.hasNext() && (object2 = ((DataConnection)((Object)object.next())).getApnContexts()).size() != 0) {
            object2 = object2.iterator();
            while (object2.hasNext()) {
                String string = ((ApnContext)object2.next()).getApnType();
                Intent intent = new Intent("com.android.internal.telephony.CARRIER_SIGNAL_PCO_VALUE");
                intent.putExtra("apnType", string);
                intent.putExtra("apnProto", asyncResult.bearerProto);
                intent.putExtra("pcoId", asyncResult.pcoId);
                intent.putExtra("pcoValue", asyncResult.contents);
                this.mPhone.getCarrierSignalAgent().notifyCarrierSignalReceivers(intent);
            }
        }
    }

    private void handleStartNetStatPoll(DctConstants.Activity activity) {
        this.startNetStatPoll();
        this.startDataStallAlarm(false);
        this.setActivity(activity);
    }

    private void handleStopNetStatPoll(DctConstants.Activity activity) {
        this.stopNetStatPoll();
        this.stopDataStallAlarm();
        this.setActivity(activity);
    }

    private void initApnContexts() {
        this.log("initApnContexts: E");
        String[] arrstring = this.mPhone.getContext().getResources().getStringArray(17236090);
        int n = arrstring.length;
        block5 : for (int i = 0; i < n; ++i) {
            Object object;
            Object object2 = new NetworkConfig(arrstring[i]);
            int n2 = ((NetworkConfig)object2).type;
            if (n2 != 0) {
                if (n2 != 1001) {
                    if (n2 != 2) {
                        if (n2 != 3) {
                            if (n2 != 4) {
                                if (n2 != 5) {
                                    if (n2 != 14) {
                                        if (n2 != 15) {
                                            switch (n2) {
                                                default: {
                                                    object = new StringBuilder();
                                                    ((StringBuilder)object).append("initApnContexts: skipping unknown type=");
                                                    ((StringBuilder)object).append(((NetworkConfig)object2).type);
                                                    this.log(((StringBuilder)object).toString());
                                                    continue block5;
                                                }
                                                case 12: {
                                                    object = this.addApnContext("cbs", (NetworkConfig)object2);
                                                    break;
                                                }
                                                case 11: {
                                                    object = this.addApnContext("ims", (NetworkConfig)object2);
                                                    break;
                                                }
                                                case 10: {
                                                    object = this.addApnContext("fota", (NetworkConfig)object2);
                                                    break;
                                                }
                                            }
                                        } else {
                                            object = this.addApnContext("emergency", (NetworkConfig)object2);
                                        }
                                    } else {
                                        object = this.addApnContext("ia", (NetworkConfig)object2);
                                    }
                                } else {
                                    object = this.addApnContext("hipri", (NetworkConfig)object2);
                                }
                            } else {
                                object = this.addApnContext("dun", (NetworkConfig)object2);
                            }
                        } else {
                            object = this.addApnContext("supl", (NetworkConfig)object2);
                        }
                    } else {
                        object = this.addApnContext("mms", (NetworkConfig)object2);
                    }
                } else {
                    object = this.addApnContext("mcx", (NetworkConfig)object2);
                }
            } else {
                object = this.addApnContext("default", (NetworkConfig)object2);
            }
            object2 = new StringBuilder();
            ((StringBuilder)object2).append("initApnContexts: apnContext=");
            ((StringBuilder)object2).append(object);
            this.log(((StringBuilder)object2).toString());
        }
    }

    private void initEmergencyApnSetting() {
        Cursor cursor = this.mPhone.getContext().getContentResolver().query(Uri.withAppendedPath((Uri)Telephony.Carriers.CONTENT_URI, (String)"filtered"), null, "type=\"emergency\"", null, null);
        if (cursor != null) {
            if (cursor.getCount() > 0 && cursor.moveToFirst()) {
                this.mEmergencyApn = ApnSetting.makeApnSetting((Cursor)cursor);
            }
            cursor.close();
        }
        if (this.mEmergencyApn != null) {
            return;
        }
        this.mEmergencyApn = new ApnSetting.Builder().setEntryName("Emergency").setProtocol(2).setApnName("sos").setApnTypeBitmask(512).build();
    }

    private boolean isConnected() {
        Iterator<ApnContext> iterator = this.mApnContexts.values().iterator();
        while (iterator.hasNext()) {
            if (iterator.next().getState() != DctConstants.State.CONNECTED) continue;
            return true;
        }
        return false;
    }

    private boolean isDataRoamingFromUserAction() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences((Context)this.mPhone.getContext());
        if (!sharedPreferences.contains("data_roaming_is_user_setting_key") && Settings.Global.getInt((ContentResolver)this.mResolver, (String)"device_provisioned", (int)0) == 0) {
            sharedPreferences.edit().putBoolean("data_roaming_is_user_setting_key", false).commit();
        }
        return sharedPreferences.getBoolean("data_roaming_is_user_setting_key", true);
    }

    private boolean isHigherPriorityApnContextActive(ApnContext apnContext) {
        if (apnContext.getApnType().equals("ims")) {
            return false;
        }
        for (ApnContext apnContext2 : this.mPrioritySortedApnContexts) {
            if (apnContext2.getApnType().equals("ims")) continue;
            if (apnContext.getApnType().equalsIgnoreCase(apnContext2.getApnType())) {
                return false;
            }
            if (!apnContext2.isEnabled() || apnContext2.getState() == DctConstants.State.FAILED) continue;
            return true;
        }
        return false;
    }

    private boolean isOnlySingleDcAllowed(int n) {
        boolean bl;
        int[] arrn = null;
        CarrierConfigManager carrierConfigManager = (CarrierConfigManager)this.mPhone.getContext().getSystemService("carrier_config");
        Object object = arrn;
        if (carrierConfigManager != null) {
            carrierConfigManager = carrierConfigManager.getConfigForSubId(this.mPhone.getSubId());
            object = arrn;
            if (carrierConfigManager != null) {
                object = carrierConfigManager.getIntArray("only_single_dc_allowed_int_array");
            }
        }
        boolean bl2 = bl = false;
        if (Build.IS_DEBUGGABLE) {
            bl2 = bl;
            if (SystemProperties.getBoolean((String)"persist.telephony.test.singleDc", (boolean)false)) {
                bl2 = true;
            }
        }
        bl = bl2;
        if (object != null) {
            int n2 = 0;
            do {
                bl = bl2;
                if (n2 >= ((int[])object).length) break;
                bl = bl2;
                if (bl2) break;
                if (n == object[n2]) {
                    bl2 = true;
                }
                ++n2;
            } while (true);
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("isOnlySingleDcAllowed(");
        ((StringBuilder)object).append(n);
        ((StringBuilder)object).append("): ");
        ((StringBuilder)object).append(bl);
        this.log(((StringBuilder)object).toString());
        return bl;
    }

    private boolean isPhoneStateIdle() {
        for (int i = 0; i < this.mTelephonyManager.getPhoneCount(); ++i) {
            Object object = PhoneFactory.getPhone(i);
            if (object == null || ((Phone)object).getState() == PhoneConstants.State.IDLE) continue;
            object = new StringBuilder();
            ((StringBuilder)object).append("isPhoneStateIdle false: Voice call active on phone ");
            ((StringBuilder)object).append(i);
            this.log(((StringBuilder)object).toString());
            return false;
        }
        return true;
    }

    private boolean isProvisioningApn(String object) {
        if ((object = this.mApnContexts.get(object)) != null) {
            return ((ApnContext)object).isProvisioningApn();
        }
        return false;
    }

    private void log(String string) {
        Rlog.d((String)this.mLogTag, (String)string);
    }

    private void loge(String string) {
        Rlog.e((String)this.mLogTag, (String)string);
    }

    private ApnSetting mergeApns(ApnSetting apnSetting, ApnSetting apnSetting2) {
        int n = apnSetting.getId();
        if ((apnSetting2.getApnTypeBitmask() & 17) == 17) {
            n = apnSetting2.getId();
        }
        int n2 = apnSetting2.getApnTypeBitmask();
        int n3 = apnSetting.getApnTypeBitmask();
        Uri uri = apnSetting.getMmsc() == null ? apnSetting2.getMmsc() : apnSetting.getMmsc();
        String string = TextUtils.isEmpty((CharSequence)apnSetting.getMmsProxyAddressAsString()) ? apnSetting2.getMmsProxyAddressAsString() : apnSetting.getMmsProxyAddressAsString();
        int n4 = apnSetting.getMmsProxyPort() == -1 ? apnSetting2.getMmsProxyPort() : apnSetting.getMmsProxyPort();
        String string2 = TextUtils.isEmpty((CharSequence)apnSetting.getProxyAddressAsString()) ? apnSetting2.getProxyAddressAsString() : apnSetting.getProxyAddressAsString();
        int n5 = apnSetting.getProxyPort() == -1 ? apnSetting2.getProxyPort() : apnSetting.getProxyPort();
        int n6 = apnSetting2.getProtocol() == 2 ? apnSetting2.getProtocol() : apnSetting.getProtocol();
        int n7 = apnSetting2.getRoamingProtocol() == 2 ? apnSetting2.getRoamingProtocol() : apnSetting.getRoamingProtocol();
        int n8 = apnSetting.getNetworkTypeBitmask() != 0 && apnSetting2.getNetworkTypeBitmask() != 0 ? apnSetting.getNetworkTypeBitmask() | apnSetting2.getNetworkTypeBitmask() : 0;
        String string3 = apnSetting.getOperatorNumeric();
        String string4 = apnSetting.getEntryName();
        String string5 = apnSetting.getApnName();
        String string6 = apnSetting.getUser();
        String string7 = apnSetting.getPassword();
        int n9 = apnSetting.getAuthType();
        boolean bl = apnSetting.isEnabled();
        int n10 = apnSetting.getProfileId();
        boolean bl2 = apnSetting.isPersistent() || apnSetting2.isPersistent();
        return ApnSetting.makeApnSetting((int)n, (String)string3, (String)string4, (String)string5, (String)string2, (int)n5, (Uri)uri, (String)string, (int)n4, (String)string6, (String)string7, (int)n9, (int)(n2 | n3), (int)n6, (int)n7, (boolean)bl, (int)n8, (int)n10, (boolean)bl2, (int)apnSetting.getMaxConns(), (int)apnSetting.getWaitTime(), (int)apnSetting.getMaxConnsTime(), (int)apnSetting.getMtu(), (int)apnSetting.getMvnoType(), (String)apnSetting.getMvnoMatchData(), (int)apnSetting.getApnSetId(), (int)apnSetting.getCarrierId(), (int)apnSetting.getSkip464Xlat());
    }

    private void notifyAllDataDisconnected() {
        sEnableFailFastRefCounter = 0;
        this.mFailFast = false;
        this.mAllDataDisconnectedRegistrants.notifyRegistrants();
    }

    private void notifyNoData(int n, ApnContext apnContext) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("notifyNoData: type=");
        stringBuilder.append(apnContext.getApnType());
        this.log(stringBuilder.toString());
        if (this.isPermanentFailure(n) && !apnContext.getApnType().equals("default")) {
            this.mPhone.notifyDataConnectionFailed(apnContext.getApnType());
        }
    }

    private void onActionIntentDataStallAlarm(Intent intent) {
        int n = intent.getIntExtra("subscription", -1);
        if (SubscriptionManager.isValidSubscriptionId((int)n) && n == this.mPhone.getSubId()) {
            if (intent.getIntExtra(INTENT_DATA_STALL_ALARM_EXTRA_TRANSPORT_TYPE, 0) != this.mTransportType) {
                return;
            }
            Message message = this.obtainMessage(270353, (Object)intent.getAction());
            message.arg1 = intent.getIntExtra(INTENT_DATA_STALL_ALARM_EXTRA_TAG, 0);
            this.sendMessage(message);
            return;
        }
    }

    private void onActionIntentProvisioningApnAlarm(Intent intent) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("onActionIntentProvisioningApnAlarm: action=");
        stringBuilder.append(intent.getAction());
        this.log(stringBuilder.toString());
        stringBuilder = this.obtainMessage(270375, (Object)intent.getAction());
        ((Message)stringBuilder).arg1 = intent.getIntExtra(PROVISIONING_APN_ALARM_TAG_EXTRA, 0);
        this.sendMessage((Message)stringBuilder);
    }

    private void onActionIntentReconnectAlarm(Intent intent) {
        Message message = this.obtainMessage(270383);
        message.setData(intent.getExtras());
        this.sendMessage(message);
    }

    private void onApnChanged() {
        Object object = this.getOverallState();
        DctConstants.State state = DctConstants.State.IDLE;
        boolean bl = false;
        boolean bl2 = object == state || object == DctConstants.State.FAILED;
        object = this.mPhone;
        if (object instanceof GsmCdmaPhone) {
            ((GsmCdmaPhone)object).updateCurrentCarrierInProvider();
        }
        this.log("onApnChanged: createAllApnList and cleanUpAllConnections");
        this.createAllApnList();
        this.setDataProfilesAsNeeded();
        this.setInitialAttachApn();
        if (!bl2) {
            bl = true;
        }
        this.cleanUpConnectionsOnUpdatedApns(bl, "apnChanged");
        if (this.mPhone.getSubId() == SubscriptionManager.getDefaultDataSubscriptionId()) {
            this.setupDataOnAllConnectableApns("apnChanged", RetryFailures.ALWAYS);
        }
    }

    private void onDataConnectionAttached() {
        this.log("onDataConnectionAttached");
        this.mAttached.set(true);
        if (this.getOverallState() == DctConstants.State.CONNECTED) {
            this.log("onDataConnectionAttached: start polling notify attached");
            this.startNetStatPoll();
            this.startDataStallAlarm(false);
            this.mPhone.notifyDataConnection();
        }
        if (this.mAutoAttachOnCreationConfig) {
            this.mAutoAttachEnabled.set(true);
        }
        this.setupDataOnAllConnectableApns("dataAttached", RetryFailures.ALWAYS);
    }

    private void onDataConnectionDetached() {
        this.log("onDataConnectionDetached: stop polling and notify detached");
        this.stopNetStatPoll();
        this.stopDataStallAlarm();
        this.mPhone.notifyDataConnection();
        this.mAttached.set(false);
    }

    private void onDataEnabledChanged(boolean bl, int n) {
        CharSequence charSequence = new StringBuilder();
        charSequence.append("onDataEnabledChanged: enable=");
        charSequence.append(bl);
        charSequence.append(", enabledChangedReason=");
        charSequence.append(n);
        this.log(charSequence.toString());
        if (bl) {
            this.reevaluateDataConnections();
            this.setupDataOnAllConnectableApns("dataEnabled", RetryFailures.ALWAYS);
        } else {
            charSequence = n != 1 ? (n != 4 ? "specificDisabled" : "carrierActionDisableMeteredApn") : "dataDisabledInternal";
            this.cleanUpAllConnectionsInternal(true, (String)charSequence);
        }
    }

    private void onDataEnabledOverrideRulesChanged() {
        this.log("onDataEnabledOverrideRulesChanged");
        for (ApnContext apnContext : this.mPrioritySortedApnContexts) {
            if (this.isDataAllowed(apnContext, 1, null)) {
                if (apnContext.getDataConnection() != null) {
                    apnContext.getDataConnection().reevaluateRestrictedState();
                }
                this.setupDataOnConnectableApn(apnContext, "dataEnabledOverride", RetryFailures.ALWAYS);
                continue;
            }
            if (!this.shouldCleanUpConnection(apnContext, true)) continue;
            apnContext.setReason("dataEnabledOverride");
            this.cleanUpConnectionInternal(true, 2, apnContext);
        }
    }

    private void onDataReconnect(Bundle object) {
        CharSequence charSequence = object.getString(INTENT_RECONNECT_ALARM_EXTRA_REASON);
        String string = object.getString(INTENT_RECONNECT_ALARM_EXTRA_TYPE);
        int n = this.mPhone.getSubId();
        int n2 = object.getInt("subscription", -1);
        if (SubscriptionManager.isValidSubscriptionId((int)n2) && n2 == n) {
            if (object.getInt(INTENT_RECONNECT_ALARM_EXTRA_TRANSPORT_TYPE, 0) != this.mTransportType) {
                return;
            }
            object = this.mApnContexts.get(string);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("onDataReconnect: mState=");
            stringBuilder.append((Object)this.mState);
            stringBuilder.append(" reason=");
            stringBuilder.append((String)charSequence);
            stringBuilder.append(" apnType=");
            stringBuilder.append(string);
            stringBuilder.append(" apnContext=");
            stringBuilder.append(object);
            this.log(stringBuilder.toString());
            if (object != null && ((ApnContext)object).isEnabled()) {
                ((ApnContext)object).setReason((String)charSequence);
                string = ((ApnContext)object).getState();
                charSequence = new StringBuilder();
                ((StringBuilder)charSequence).append("onDataReconnect: apnContext state=");
                ((StringBuilder)charSequence).append((Object)string);
                this.log(((StringBuilder)charSequence).toString());
                if (string != DctConstants.State.FAILED && string != DctConstants.State.IDLE) {
                    this.log("onDataReconnect: keep associated");
                } else {
                    this.log("onDataReconnect: state is FAILED|IDLE, disassociate");
                    ((ApnContext)object).releaseDataConnection("");
                }
                this.sendMessage(this.obtainMessage(270339, object));
                ((ApnContext)object).setReconnectIntent(null);
            }
            return;
        }
    }

    private void onDataRoamingOff() {
        this.log("onDataRoamingOff");
        this.reevaluateDataConnections();
        if (!this.getDataRoamingEnabled()) {
            this.setDataProfilesAsNeeded();
            this.setInitialAttachApn();
            this.setupDataOnAllConnectableApns("roamingOff", RetryFailures.ALWAYS);
        } else {
            this.mPhone.notifyDataConnection();
        }
    }

    private void onDataRoamingOnOrSettingsChanged(int n) {
        this.log("onDataRoamingOnOrSettingsChanged");
        boolean bl = n == 270384;
        if (!this.mPhone.getServiceState().getDataRoaming()) {
            this.log("device is not roaming. ignored the request.");
            return;
        }
        this.checkDataRoamingStatus(bl);
        if (this.getDataRoamingEnabled()) {
            if (bl) {
                this.reevaluateDataConnections();
            }
            this.log("onDataRoamingOnOrSettingsChanged: setup data on roaming");
            this.setupDataOnAllConnectableApns("roamingOn", RetryFailures.ALWAYS);
            this.mPhone.notifyDataConnection();
        } else {
            this.log("onDataRoamingOnOrSettingsChanged: Tear down data connection on roaming.");
            this.cleanUpAllConnectionsInternal(true, "roamingOn");
        }
    }

    private void onDataServiceBindingChanged(boolean bl) {
        if (bl) {
            this.mDcc.start();
        } else {
            this.mDcc.dispose();
        }
        this.mDataServiceBound = bl;
    }

    private void onDataSetupComplete(ApnContext object, boolean bl, int n, int n2) {
        Object object2;
        int n3 = ApnSetting.getApnTypesBitmaskFromString((String)((ApnContext)object).getApnType());
        List<Message> list = this.mRequestNetworkCompletionMsgs.get(n3);
        if (list != null) {
            object2 = list.iterator();
            while (object2.hasNext()) {
                this.sendRequestNetworkCompleteMsg(object2.next(), bl, this.mTransportType, n2);
            }
            list.clear();
        }
        object2 = null;
        if (bl) {
            Object object3 = ((ApnContext)object).getDataConnection();
            if (object3 == null) {
                this.log("onDataSetupComplete: no connection to DC, handle as error");
                this.onDataSetupCompleteError((ApnContext)object, n2);
            } else {
                list = ((ApnContext)object).getApnSetting();
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("onDataSetupComplete: success apn=");
                object2 = list == null ? "unknown" : list.getApnName();
                stringBuilder.append((String)object2);
                this.log(stringBuilder.toString());
                if (list != null && !TextUtils.isEmpty((CharSequence)list.getProxyAddressAsString())) {
                    block26 : {
                        n = n3 = list.getProxyPort();
                        if (n3 != -1) break block26;
                        n = 8080;
                    }
                    try {
                        object2 = new ProxyInfo(list.getProxyAddressAsString(), n, null);
                        object3.setLinkPropertiesHttpProxy((ProxyInfo)object2);
                    }
                    catch (NumberFormatException numberFormatException) {
                        object3 = new StringBuilder();
                        ((StringBuilder)object3).append("onDataSetupComplete: NumberFormatException making ProxyProperties (");
                        ((StringBuilder)object3).append(list.getProxyPort());
                        ((StringBuilder)object3).append("): ");
                        ((StringBuilder)object3).append(numberFormatException);
                        this.loge(((StringBuilder)object3).toString());
                    }
                }
                if (TextUtils.equals((CharSequence)((ApnContext)object).getApnType(), (CharSequence)"default")) {
                    try {
                        SystemProperties.set((String)PUPPET_MASTER_RADIO_STRESS_TEST, (String)"true");
                    }
                    catch (RuntimeException runtimeException) {
                        this.log("Failed to set PUPPET_MASTER_RADIO_STRESS_TEST to true");
                    }
                    if (this.mCanSetPreferApn && this.mPreferredApn == null) {
                        this.log("onDataSetupComplete: PREFERRED APN is null");
                        this.mPreferredApn = list;
                        object2 = this.mPreferredApn;
                        if (object2 != null) {
                            this.setPreferredApn(object2.getId());
                        }
                    }
                } else {
                    try {
                        SystemProperties.set((String)PUPPET_MASTER_RADIO_STRESS_TEST, (String)"false");
                    }
                    catch (RuntimeException runtimeException) {
                        this.log("Failed to set PUPPET_MASTER_RADIO_STRESS_TEST to false");
                    }
                }
                ((ApnContext)object).setState(DctConstants.State.CONNECTED);
                this.checkDataRoamingStatus(false);
                bl = ((ApnContext)object).isProvisioningApn();
                object2 = ConnectivityManager.from((Context)this.mPhone.getContext());
                if (this.mProvisionBroadcastReceiver != null) {
                    this.mPhone.getContext().unregisterReceiver(this.mProvisionBroadcastReceiver);
                    this.mProvisionBroadcastReceiver = null;
                }
                if (bl && !this.mIsProvisioning) {
                    list = new StringBuilder();
                    ((StringBuilder)((Object)list)).append("onDataSetupComplete: successful, BUT send connected to prov apn as mIsProvisioning:");
                    ((StringBuilder)((Object)list)).append(this.mIsProvisioning);
                    ((StringBuilder)((Object)list)).append(" == false && (isProvisioningApn:");
                    ((StringBuilder)((Object)list)).append(bl);
                    ((StringBuilder)((Object)list)).append(" == true");
                    this.log(((StringBuilder)((Object)list)).toString());
                    this.mProvisionBroadcastReceiver = new ProvisionNotificationBroadcastReceiver(object2.getMobileProvisioningUrl(), this.mTelephonyManager.getNetworkOperatorName());
                    this.mPhone.getContext().registerReceiver(this.mProvisionBroadcastReceiver, new IntentFilter(this.mProvisionActionName));
                    object2.setProvisioningNotificationVisible(true, 0, this.mProvisionActionName);
                    this.setRadio(false);
                } else {
                    object2.setProvisioningNotificationVisible(false, 0, this.mProvisionActionName);
                    this.completeConnection((ApnContext)object, n2);
                }
                object2 = new StringBuilder();
                ((StringBuilder)object2).append("onDataSetupComplete: SETUP complete type=");
                ((StringBuilder)object2).append(((ApnContext)object).getApnType());
                this.log(((StringBuilder)object2).toString());
                if (Build.IS_DEBUGGABLE && (n = SystemProperties.getInt((String)"persist.radio.test.pco", (int)-1)) != -1) {
                    object = new StringBuilder();
                    ((StringBuilder)object).append("PCO testing: read pco value from persist.radio.test.pco ");
                    ((StringBuilder)object).append(n);
                    this.log(((StringBuilder)object).toString());
                    byte by = (byte)n;
                    object = new Intent("com.android.internal.telephony.CARRIER_SIGNAL_PCO_VALUE");
                    object.putExtra("apnType", "default");
                    object.putExtra("apnProto", "IPV4V6");
                    object.putExtra("pcoId", 65280);
                    object.putExtra("pcoValue", new byte[]{by});
                    this.mPhone.getCarrierSignalAgent().notifyCarrierSignalReceivers((Intent)object);
                }
            }
        } else {
            Object object4 = ((ApnContext)object).getApnSetting();
            list = new StringBuilder();
            ((StringBuilder)((Object)list)).append("onDataSetupComplete: error apn=");
            ((StringBuilder)((Object)list)).append(object4.getApnName());
            ((StringBuilder)((Object)list)).append(", cause=");
            ((StringBuilder)((Object)list)).append(n);
            ((StringBuilder)((Object)list)).append(", requestType=");
            ((StringBuilder)((Object)list)).append(DcTracker.requestTypeToString(n2));
            this.log(((StringBuilder)((Object)list)).toString());
            if (DataFailCause.isEventLoggable((int)n)) {
                EventLog.writeEvent((int)50105, (Object[])new Object[]{n, this.getCellLocationId(), this.mTelephonyManager.getNetworkType()});
            }
            list = ((ApnContext)object).getApnSetting();
            Phone phone = this.mPhone;
            object4 = ((ApnContext)object).getApnType();
            if (list != null) {
                object2 = list.getApnName();
            }
            phone.notifyPreciseDataConnectionFailed((String)object4, (String)object2, n);
            object2 = new Intent("com.android.internal.telephony.CARRIER_SIGNAL_REQUEST_NETWORK_FAILED");
            object2.putExtra("errorCode", n);
            object2.putExtra("apnType", ((ApnContext)object).getApnType());
            this.mPhone.getCarrierSignalAgent().notifyCarrierSignalReceivers((Intent)object2);
            if (DataFailCause.isRadioRestartFailure((Context)this.mPhone.getContext(), (int)n, (int)this.mPhone.getSubId()) || ((ApnContext)object).restartOnError(n)) {
                this.log("Modem restarted.");
                this.sendRestartRadio();
            }
            if (this.isPermanentFailure(n)) {
                object2 = new StringBuilder();
                ((StringBuilder)object2).append("cause = ");
                ((StringBuilder)object2).append(n);
                ((StringBuilder)object2).append(", mark apn as permanent failed. apn = ");
                ((StringBuilder)object2).append(list);
                this.log(((StringBuilder)object2).toString());
                ((ApnContext)object).markApnPermanentFailed((ApnSetting)list);
            }
            this.onDataSetupCompleteError((ApnContext)object, n2);
        }
    }

    private void onDataSetupCompleteError(ApnContext object, int n) {
        long l = ((ApnContext)object).getDelayForNextApn(this.mFailFast);
        if (l >= 0L) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("onDataSetupCompleteError: Try next APN. delay = ");
            stringBuilder.append(l);
            this.log(stringBuilder.toString());
            ((ApnContext)object).setState(DctConstants.State.RETRYING);
            this.startAlarmForReconnect(l, (ApnContext)object);
        } else {
            ((ApnContext)object).setState(DctConstants.State.FAILED);
            this.mPhone.notifyDataConnection(((ApnContext)object).getApnType());
            ((ApnContext)object).setDataConnection(null);
            object = new StringBuilder();
            ((StringBuilder)object).append("onDataSetupCompleteError: Stop retrying APNs. delay=");
            ((StringBuilder)object).append(l);
            ((StringBuilder)object).append(", requestType=");
            ((StringBuilder)object).append(DcTracker.requestTypeToString(n));
            this.log(((StringBuilder)object).toString());
        }
    }

    private void onDataStallAlarm(int n) {
        if (this.mDataStallAlarmTag != n) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("onDataStallAlarm: ignore, tag=");
            stringBuilder.append(n);
            stringBuilder.append(" expecting ");
            stringBuilder.append(this.mDataStallAlarmTag);
            this.log(stringBuilder.toString());
            return;
        }
        this.log("Data stall alarm");
        this.updateDataStallInfo();
        int n2 = Settings.Global.getInt((ContentResolver)this.mResolver, (String)"pdp_watchdog_trigger_packet_count", (int)10);
        boolean bl = false;
        if (this.mSentSinceLastRecv >= (long)n2) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("onDataStallAlarm: tag=");
            stringBuilder.append(n);
            stringBuilder.append(" do recovery action=");
            stringBuilder.append(this.mDsRecoveryHandler.getRecoveryAction());
            this.log(stringBuilder.toString());
            bl = true;
            this.sendMessage(this.obtainMessage(270354));
        }
        this.startDataStallAlarm(bl);
    }

    private void onDisableApn(int n, int n2) {
        Object object = (ApnContext)this.mApnContextsByType.get(n);
        if (object == null) {
            object = new StringBuilder();
            ((StringBuilder)object).append("disableApn(");
            ((StringBuilder)object).append(n);
            ((StringBuilder)object).append("): NO ApnContext");
            this.loge(((StringBuilder)object).toString());
            return;
        }
        int n3 = 0;
        CharSequence charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append("onDisableApn: apnType=");
        ((StringBuilder)charSequence).append(ApnSetting.getApnTypeString((int)n));
        ((StringBuilder)charSequence).append(", release type=");
        ((StringBuilder)charSequence).append(DcTracker.releaseTypeToString(n2));
        charSequence = ((StringBuilder)charSequence).toString();
        this.log((String)charSequence);
        ((ApnContext)object).requestLog((String)charSequence);
        n = n3;
        if (((ApnContext)object).isReady()) {
            n = n2 != 2 && n2 != 3 ? 0 : 1;
            if (((ApnContext)object).isDependencyMet()) {
                ((ApnContext)object).setReason("dataDisabledInternal");
                if ("dun".equals(((ApnContext)object).getApnType()) || ((ApnContext)object).getState() != DctConstants.State.CONNECTED) {
                    charSequence = new StringBuilder();
                    ((StringBuilder)charSequence).append("Clean up the connection. Apn type = ");
                    ((StringBuilder)charSequence).append(((ApnContext)object).getApnType());
                    ((StringBuilder)charSequence).append(", state = ");
                    ((StringBuilder)charSequence).append((Object)((ApnContext)object).getState());
                    charSequence = ((StringBuilder)charSequence).toString();
                    this.log((String)charSequence);
                    ((ApnContext)object).requestLog((String)charSequence);
                    n = 1;
                }
            } else {
                ((ApnContext)object).setReason("dependencyUnmet");
            }
        }
        ((ApnContext)object).setEnabled(false);
        if (n != 0) {
            this.cleanUpConnectionInternal(true, n2, (ApnContext)object);
        }
        if (this.isOnlySingleDcAllowed(this.getDataRat()) && !this.isHigherPriorityApnContextActive((ApnContext)object)) {
            this.log("disableApn:isOnlySingleDcAllowed true & higher priority APN disabled");
            this.setupDataOnAllConnectableApns("SinglePdnArbitration", RetryFailures.ALWAYS);
        }
    }

    private void onDisconnectDone(ApnContext apnContext) {
        int n;
        StringBuilder object2 = new StringBuilder();
        object2.append("onDisconnectDone: EVENT_DISCONNECT_DONE apnContext=");
        object2.append(apnContext);
        this.log(object2.toString());
        apnContext.setState(DctConstants.State.IDLE);
        DataConnection runtimeException = apnContext.getDataConnection();
        if (runtimeException != null && runtimeException.isInactive() && !runtimeException.hasBeenTransferred()) {
            for (String string : ApnSetting.getApnTypesStringFromBitmask((int)apnContext.getApnSetting().getApnTypeBitmask()).split(",")) {
                this.mPhone.notifyDataConnection(string);
            }
        }
        if (this.isDisconnected() && this.mPhone.getServiceStateTracker().processPendingRadioPowerOffAfterDataOff()) {
            this.log("onDisconnectDone: radio will be turned off, no retries");
            apnContext.setApnSetting(null);
            apnContext.setDataConnection(null);
            n = this.mDisconnectPendingCount;
            if (n > 0) {
                this.mDisconnectPendingCount = n - 1;
            }
            if (this.mDisconnectPendingCount == 0) {
                this.notifyAllDataDisconnected();
            }
            return;
        }
        if (this.mAttached.get() && apnContext.isReady() && this.retryAfterDisconnected(apnContext)) {
            try {
                SystemProperties.set((String)PUPPET_MASTER_RADIO_STRESS_TEST, (String)"false");
            }
            catch (RuntimeException runtimeException2) {
                this.log("Failed to set PUPPET_MASTER_RADIO_STRESS_TEST to false");
            }
            this.log("onDisconnectDone: attached, ready and retry after disconnect");
            long l = apnContext.getRetryAfterDisconnectDelay();
            if (l > 0L) {
                this.startAlarmForReconnect(l, apnContext);
            }
        } else {
            boolean bl = this.mPhone.getContext().getResources().getBoolean(17891500);
            if (apnContext.isProvisioningApn() && bl) {
                this.log("onDisconnectDone: restartRadio after provisioning");
                this.restartRadio();
            }
            apnContext.setApnSetting(null);
            apnContext.setDataConnection(null);
            if (this.isOnlySingleDcAllowed(this.getDataRat())) {
                this.log("onDisconnectDone: isOnlySigneDcAllowed true so setup single apn");
                this.setupDataOnAllConnectableApns("SinglePdnArbitration", RetryFailures.ALWAYS);
            } else {
                this.log("onDisconnectDone: not retrying");
            }
        }
        n = this.mDisconnectPendingCount;
        if (n > 0) {
            this.mDisconnectPendingCount = n - 1;
        }
        if (this.mDisconnectPendingCount == 0) {
            apnContext.setConcurrentVoiceAndDataAllowed(this.mPhone.getServiceStateTracker().isConcurrentVoiceAndDataAllowed());
            this.notifyAllDataDisconnected();
        }
    }

    private void onEnableApn(int n, int n2, Message message) {
        Object object = (ApnContext)this.mApnContextsByType.get(n);
        if (object == null) {
            object = new StringBuilder();
            ((StringBuilder)object).append("onEnableApn(");
            ((StringBuilder)object).append(n);
            ((StringBuilder)object).append("): NO ApnContext");
            this.loge(((StringBuilder)object).toString());
            this.sendRequestNetworkCompleteMsg(message, false, this.mTransportType, n2);
            return;
        }
        CharSequence charSequence = new StringBuilder();
        charSequence.append("onEnableApn: apnType=");
        charSequence.append(ApnSetting.getApnTypeString((int)n));
        charSequence.append(", request type=");
        charSequence.append(DcTracker.requestTypeToString(n2));
        charSequence = charSequence.toString();
        this.log((String)charSequence);
        ((ApnContext)object).requestLog((String)charSequence);
        if (!((ApnContext)object).isDependencyMet()) {
            ((ApnContext)object).setReason("dependencyUnmet");
            ((ApnContext)object).setEnabled(true);
            this.log("onEnableApn: dependency is not met.");
            ((ApnContext)object).requestLog("onEnableApn: dependency is not met.");
            this.sendRequestNetworkCompleteMsg(message, false, this.mTransportType, n2);
            return;
        }
        if (((ApnContext)object).isReady()) {
            charSequence = ((ApnContext)object).getState();
            switch (5.$SwitchMap$com$android$internal$telephony$DctConstants$State[charSequence.ordinal()]) {
                default: {
                    break;
                }
                case 4: 
                case 5: 
                case 6: {
                    ((ApnContext)object).setReason("dataEnabled");
                    break;
                }
                case 3: {
                    this.log("onEnableApn: 'CONNECTING' so return");
                    ((ApnContext)object).requestLog("onEnableApn state=CONNECTING, so return");
                    this.addRequestNetworkCompleteMsg(message, n);
                    return;
                }
                case 2: {
                    this.log("onEnableApn: 'DISCONNECTING' so return");
                    ((ApnContext)object).requestLog("onEnableApn state=DISCONNECTING, so return");
                    this.sendRequestNetworkCompleteMsg(message, false, this.mTransportType, n2);
                    return;
                }
                case 1: {
                    this.log("onEnableApn: 'CONNECTED' so return");
                    ((ApnContext)object).requestLog("onEnableApn state=CONNECTED, so return");
                    this.sendRequestNetworkCompleteMsg(message, true, this.mTransportType, n2);
                    return;
                }
            }
        } else {
            if (((ApnContext)object).isEnabled()) {
                ((ApnContext)object).setReason("dependencyMet");
            } else {
                ((ApnContext)object).setReason("dataEnabled");
            }
            if (((ApnContext)object).getState() == DctConstants.State.FAILED) {
                ((ApnContext)object).setState(DctConstants.State.IDLE);
            }
        }
        ((ApnContext)object).setEnabled(true);
        ((ApnContext)object).resetErrorCodeRetries();
        if (this.trySetupData((ApnContext)object, n2)) {
            this.addRequestNetworkCompleteMsg(message, n);
        } else {
            this.sendRequestNetworkCompleteMsg(message, false, this.mTransportType, n2);
        }
    }

    private void onNetworkStatusChanged(int n, String string) {
        if (!TextUtils.isEmpty((CharSequence)string)) {
            Object object = new Intent("com.android.internal.telephony.CARRIER_SIGNAL_REDIRECTED");
            object.putExtra("redirectionUrl", string);
            this.mPhone.getCarrierSignalAgent().notifyCarrierSignalReceivers((Intent)object);
            object = new StringBuilder();
            ((StringBuilder)object).append("Notify carrier signal receivers with redirectUrl: ");
            ((StringBuilder)object).append(string);
            this.log(((StringBuilder)object).toString());
        } else {
            boolean bl = n == 1;
            if (!this.mDsRecoveryHandler.isRecoveryOnBadNetworkEnabled()) {
                this.log("Skip data stall recovery on network status change with in threshold");
                return;
            }
            if (this.mTransportType != 1) {
                this.log("Skip data stall recovery on non WWAN");
                return;
            }
            this.mDsRecoveryHandler.processNetworkStatusChanged(bl);
        }
    }

    private void onRadioAvailable() {
        this.log("onRadioAvailable");
        if (this.mPhone.getSimulatedRadioControl() != null) {
            this.mPhone.notifyDataConnection();
            this.log("onRadioAvailable: We're on the simulator; assuming data is connected");
        }
        if (this.getOverallState() != DctConstants.State.IDLE) {
            this.cleanUpConnectionInternal(true, 2, null);
        }
    }

    private void onRadioOffOrNotAvailable() {
        this.mReregisterOnReconnectFailure = false;
        this.mAutoAttachEnabled.set(false);
        if (this.mPhone.getSimulatedRadioControl() != null) {
            this.log("We're on the simulator; assuming radio off is meaningless");
        } else {
            this.log("onRadioOffOrNotAvailable: is off and clean up all connections");
            this.cleanUpAllConnectionsInternal(false, "radioTurnedOff");
        }
    }

    private void onRecordsLoadedOrSubIdChanged() {
        this.log("onRecordsLoadedOrSubIdChanged: createAllApnList");
        if (this.mTransportType == 1) {
            this.mAutoAttachOnCreationConfig = this.mPhone.getContext().getResources().getBoolean(17891366);
        }
        this.createAllApnList();
        this.setDataProfilesAsNeeded();
        this.setInitialAttachApn();
        this.mPhone.notifyDataConnection();
        this.setupDataOnAllConnectableApns("simLoaded", RetryFailures.ALWAYS);
    }

    private void onSimNotReady() {
        this.log("onSimNotReady");
        this.cleanUpAllConnectionsInternal(true, "simNotReady");
        this.mAllApnSettings.clear();
        this.mAutoAttachOnCreationConfig = false;
        this.mAutoAttachEnabled.set(false);
        this.mOnSubscriptionsChangedListener.mPreviousSubId.set(-1);
        this.createAllApnList();
        this.setDataProfilesAsNeeded();
    }

    private void onUpdateIcc() {
        if (this.mUiccController == null) {
            return;
        }
        IccRecords iccRecords = this.getUiccRecords(1);
        IccRecords iccRecords2 = this.mIccRecords.get();
        if (iccRecords2 != iccRecords) {
            if (iccRecords2 != null) {
                this.log("Removing stale icc objects.");
                iccRecords2.unregisterForRecordsLoaded(this);
                this.mIccRecords.set(null);
            }
            if (iccRecords != null) {
                if (SubscriptionManager.isValidSubscriptionId((int)this.mPhone.getSubId())) {
                    this.log("New records found.");
                    this.mIccRecords.set(iccRecords);
                    iccRecords.registerForRecordsLoaded(this, 270338, null);
                }
            } else {
                this.onSimNotReady();
            }
        }
    }

    private void onVoiceCallEnded() {
        this.log("onVoiceCallEnded");
        this.mInVoiceCall = false;
        if (this.isConnected()) {
            if (!this.mPhone.getServiceStateTracker().isConcurrentVoiceAndDataAllowed()) {
                this.startNetStatPoll();
                this.startDataStallAlarm(false);
                this.mPhone.notifyDataConnection();
            } else {
                this.resetPollStats();
            }
        }
        this.setupDataOnAllConnectableApns("2GVoiceCallEnded", RetryFailures.ALWAYS);
    }

    private void onVoiceCallStarted() {
        this.log("onVoiceCallStarted");
        this.mInVoiceCall = true;
        if (this.isConnected() && !this.mPhone.getServiceStateTracker().isConcurrentVoiceAndDataAllowed()) {
            this.log("onVoiceCallStarted stop polling");
            this.stopNetStatPoll();
            this.stopDataStallAlarm();
            this.mPhone.notifyDataConnection();
        }
    }

    private void reevaluateDataConnections() {
        Iterator<DataConnection> iterator = this.mDataConnections.values().iterator();
        while (iterator.hasNext()) {
            iterator.next().reevaluateRestrictedState();
        }
    }

    private void registerForAllEvents() {
        if (this.mTransportType == 1) {
            this.mPhone.mCi.registerForAvailable(this, 270337, null);
            this.mPhone.mCi.registerForOffOrNotAvailable(this, 270342, null);
            this.mPhone.mCi.registerForPcoData(this, 270381, null);
        }
        this.mPhone.getCallTracker().registerForVoiceCallEnded(this, 270344, null);
        this.mPhone.getCallTracker().registerForVoiceCallStarted(this, 270343, null);
        this.registerServiceStateTrackerEvents();
        this.mDataServiceManager.registerForServiceBindingChanged(this, 270385, null);
    }

    private void registerSettingsObserver() {
        this.mSettingsObserver.unobserve();
        String string = "";
        if (TelephonyManager.getDefault().getSimCount() > 1) {
            string = Integer.toString(this.mPhone.getSubId());
        }
        SettingsObserver settingsObserver = this.mSettingsObserver;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("data_roaming");
        stringBuilder.append(string);
        settingsObserver.observe(Settings.Global.getUriFor((String)stringBuilder.toString()), 270384);
        this.mSettingsObserver.observe(Settings.Global.getUriFor((String)"device_provisioned"), 270386);
    }

    public static String releaseTypeToString(int n) {
        if (n != 1) {
            if (n != 2) {
                if (n != 3) {
                    return "UNKNOWN";
                }
                return "HANDOVER";
            }
            return "DETACH";
        }
        return "NORMAL";
    }

    public static String requestTypeToString(int n) {
        if (n != 1) {
            if (n != 2) {
                return "UNKNOWN";
            }
            return "HANDOVER";
        }
        return "NORMAL";
    }

    private void resetPollStats() {
        this.mTxPkts = -1L;
        this.mRxPkts = -1L;
        this.mNetStatPollPeriod = 1000;
    }

    private void restartDataStallAlarm() {
        if (!this.isConnected()) {
            return;
        }
        if (this.mDsRecoveryHandler.isAggressiveRecovery()) {
            this.log("restartDataStallAlarm: action is pending. not resetting the alarm.");
            return;
        }
        this.stopDataStallAlarm();
        this.startDataStallAlarm(false);
    }

    private void restartRadio() {
        this.log("restartRadio: ************TURN OFF RADIO**************");
        this.cleanUpAllConnectionsInternal(true, "radioTurnedOff");
        this.mPhone.getServiceStateTracker().powerOffRadioSafely();
        int n = Integer.parseInt(SystemProperties.get((String)"net.ppp.reset-by-timeout", (String)"0"));
        try {
            SystemProperties.set((String)"net.ppp.reset-by-timeout", (String)String.valueOf(n + 1));
        }
        catch (RuntimeException runtimeException) {
            this.log("Failed to set net.ppp.reset-by-timeout");
        }
    }

    private boolean retryAfterDisconnected(ApnContext apnContext) {
        boolean bl;
        block3 : {
            block2 : {
                boolean bl2 = true;
                if ("radioTurnedOff".equals(apnContext.getReason())) break block2;
                bl = bl2;
                if (!this.isOnlySingleDcAllowed(this.getDataRat())) break block3;
                bl = bl2;
                if (!this.isHigherPriorityApnContextActive(apnContext)) break block3;
            }
            bl = false;
        }
        return bl;
    }

    private void sendRequestNetworkCompleteMsg(Message message, boolean bl, int n, int n2) {
        if (message == null) {
            return;
        }
        Bundle bundle = message.getData();
        bundle.putBoolean(DATA_COMPLETE_MSG_EXTRA_SUCCESS, bl);
        bundle.putInt(DATA_COMPLETE_MSG_EXTRA_REQUEST_TYPE, n2);
        bundle.putInt(DATA_COMPLETE_MSG_EXTRA_TRANSPORT_TYPE, n);
        message.sendToTarget();
    }

    private void setActivity(DctConstants.Activity activity) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("setActivity = ");
        stringBuilder.append((Object)activity);
        this.log(stringBuilder.toString());
        this.mActivity = activity;
        this.mPhone.notifyDataActivity();
    }

    private void setDataProfilesAsNeeded() {
        this.log("setDataProfilesAsNeeded");
        ArrayList<DataProfile> arrayList = new ArrayList<DataProfile>();
        for (ApnSetting apnSetting : this.mAllApnSettings) {
            if (arrayList.contains((Object)(apnSetting = DcTracker.createDataProfile(apnSetting, apnSetting.equals((Object)this.getPreferredApn()))))) continue;
            arrayList.add((DataProfile)apnSetting);
        }
        if (!(arrayList.isEmpty() || arrayList.size() == this.mLastDataProfileList.size() && this.mLastDataProfileList.containsAll(arrayList))) {
            this.mDataServiceManager.setDataProfile(arrayList, this.mPhone.getServiceState().getDataRoamingFromRegistration(), null);
        }
    }

    private void setDataRoamingFromUserAction(boolean bl) {
        PreferenceManager.getDefaultSharedPreferences((Context)this.mPhone.getContext()).edit().putBoolean("data_roaming_is_user_setting_key", bl).commit();
    }

    private void setDefaultDataRoamingEnabled() {
        CharSequence charSequence;
        boolean bl = false;
        boolean bl2 = false;
        if (this.mTelephonyManager.getSimCount() != 1) {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("data_roaming");
            ((StringBuilder)charSequence).append(this.mPhone.getSubId());
            charSequence = ((StringBuilder)charSequence).toString();
            try {
                Settings.Global.getInt((ContentResolver)this.mResolver, (String)charSequence);
            }
            catch (Settings.SettingNotFoundException settingNotFoundException) {
                bl2 = true;
            }
        } else {
            bl2 = bl;
            if (!this.isDataRoamingFromUserAction()) {
                bl2 = true;
            }
        }
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append("setDefaultDataRoamingEnabled: useCarrierSpecificDefault ");
        ((StringBuilder)charSequence).append(bl2);
        this.log(((StringBuilder)charSequence).toString());
        if (bl2) {
            bl2 = this.mDataEnabledSettings.getDefaultDataRoamingEnabled();
            this.mDataEnabledSettings.setDataRoamingEnabled(bl2);
        }
    }

    private void setInitialAttachApn() {
        Object var1_1 = null;
        StringBuilder stringBuilder = null;
        StringBuilder stringBuilder2 = null;
        StringBuilder stringBuilder3 = null;
        StringBuilder stringBuilder4 = null;
        StringBuilder stringBuilder5 = new StringBuilder();
        stringBuilder5.append("setInitialApn: E mPreferredApn=");
        stringBuilder5.append((Object)this.mPreferredApn);
        this.log(stringBuilder5.toString());
        stringBuilder5 = this.mPreferredApn;
        if (stringBuilder5 != null && stringBuilder5.canHandleType(256)) {
            stringBuilder5 = this.mPreferredApn;
        } else {
            stringBuilder5 = var1_1;
            if (!this.mAllApnSettings.isEmpty()) {
                Iterator<ApnSetting> iterator = this.mAllApnSettings.iterator();
                do {
                    stringBuilder5 = var1_1;
                    stringBuilder = stringBuilder2;
                    stringBuilder3 = stringBuilder4;
                    if (!iterator.hasNext()) break;
                    stringBuilder5 = iterator.next();
                    stringBuilder3 = stringBuilder4;
                    if (stringBuilder4 == null) {
                        stringBuilder3 = stringBuilder4;
                        if (!stringBuilder5.canHandleType(512)) {
                            stringBuilder3 = stringBuilder5;
                            stringBuilder = new StringBuilder();
                            stringBuilder.append("setInitialApn: firstNonEmergencyApnSetting=");
                            stringBuilder.append((Object)stringBuilder3);
                            this.log(stringBuilder.toString());
                        }
                    }
                    if (stringBuilder5.canHandleType(256)) {
                        stringBuilder = new StringBuilder();
                        stringBuilder.append("setInitialApn: iaApnSetting=");
                        stringBuilder.append((Object)stringBuilder5);
                        this.log(stringBuilder.toString());
                        stringBuilder = stringBuilder2;
                        break;
                    }
                    stringBuilder = stringBuilder2;
                    if (stringBuilder2 == null) {
                        stringBuilder = stringBuilder2;
                        if (stringBuilder5.canHandleType(17)) {
                            stringBuilder2 = new StringBuilder();
                            stringBuilder2.append("setInitialApn: defaultApnSetting=");
                            stringBuilder2.append((Object)stringBuilder5);
                            this.log(stringBuilder2.toString());
                            stringBuilder = stringBuilder5;
                        }
                    }
                    stringBuilder2 = stringBuilder;
                    stringBuilder4 = stringBuilder3;
                } while (true);
            }
        }
        stringBuilder2 = null;
        if (stringBuilder5 != null) {
            this.log("setInitialAttachApn: using iaApnSetting");
        } else if (this.mPreferredApn != null) {
            this.log("setInitialAttachApn: using mPreferredApn");
            stringBuilder5 = this.mPreferredApn;
        } else if (stringBuilder != null) {
            this.log("setInitialAttachApn: using defaultApnSetting");
            stringBuilder5 = stringBuilder;
        } else {
            stringBuilder5 = stringBuilder2;
            if (stringBuilder3 != null) {
                this.log("setInitialAttachApn: using firstNonEmergencyApnSetting");
                stringBuilder5 = stringBuilder3;
            }
        }
        if (stringBuilder5 == null) {
            this.log("setInitialAttachApn: X There in no available apn");
        } else {
            stringBuilder3 = new StringBuilder();
            stringBuilder3.append("setInitialAttachApn: X selected Apn=");
            stringBuilder3.append((Object)stringBuilder5);
            this.log(stringBuilder3.toString());
            this.mDataServiceManager.setInitialAttachApn(DcTracker.createDataProfile((ApnSetting)stringBuilder5, stringBuilder5.equals((Object)this.getPreferredApn())), this.mPhone.getServiceState().getDataRoamingFromRegistration(), null);
        }
    }

    private void setPreferredApn(int n) {
        if (!this.mCanSetPreferApn) {
            this.log("setPreferredApn: X !canSEtPreferApn");
            return;
        }
        String string = Long.toString(this.mPhone.getSubId());
        Uri uri = Uri.withAppendedPath((Uri)PREFERAPN_NO_UPDATE_URI_USING_SUBID, (String)string);
        this.log("setPreferredApn: delete");
        string = this.mPhone.getContext().getContentResolver();
        string.delete(uri, null, null);
        if (n >= 0) {
            this.log("setPreferredApn: insert");
            ContentValues contentValues = new ContentValues();
            contentValues.put(APN_ID, Integer.valueOf(n));
            string.insert(uri, contentValues);
        }
    }

    private void setRadio(boolean bl) {
        ITelephony iTelephony = ITelephony.Stub.asInterface((IBinder)ServiceManager.checkService((String)"phone"));
        try {
            iTelephony.setRadio(bl);
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    private boolean setupData(ApnContext apnContext, int n, int n2) {
        Object object;
        int n3;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("setupData: apnContext=");
        stringBuilder.append(apnContext);
        stringBuilder.append(", requestType=");
        stringBuilder.append(DcTracker.requestTypeToString(n2));
        this.log(stringBuilder.toString());
        stringBuilder = new StringBuilder();
        stringBuilder.append("setupData. requestType=");
        stringBuilder.append(DcTracker.requestTypeToString(n2));
        apnContext.requestLog(stringBuilder.toString());
        DataConnection dataConnection = null;
        stringBuilder = apnContext.getNextApnSetting();
        if (stringBuilder == null) {
            this.log("setupData: return for no apn found!");
            return false;
        }
        if (stringBuilder.isPersistent()) {
            n3 = stringBuilder.getProfileId();
            if (n3 == 0) {
                n3 = this.getApnProfileID(apnContext.getApnType());
            }
        } else {
            n3 = -1;
        }
        if (!apnContext.getApnType().equals("dun") || ServiceState.isGsm((int)this.getDataRat())) {
            dataConnection = this.checkForCompatibleConnectedApnContext(apnContext);
            if (dataConnection != null && (object = dataConnection.getApnSetting()) != null) {
                stringBuilder = object;
            }
        }
        if (dataConnection == null) {
            if (this.isOnlySingleDcAllowed(n)) {
                if (this.isHigherPriorityApnContextActive(apnContext)) {
                    this.log("setupData: Higher priority ApnContext active.  Ignoring call");
                    return false;
                }
                if (!apnContext.getApnType().equals("ims") && this.cleanUpAllConnectionsInternal(true, "SinglePdnArbitration")) {
                    this.log("setupData: Some calls are disconnecting first. Wait and retry");
                    return false;
                }
                this.log("setupData: Single pdp. Continue setting up data call.");
            }
            object = this.findFreeDataConnection();
            dataConnection = object;
            if (object == null) {
                dataConnection = this.createDataConnection();
            }
            if (dataConnection == null) {
                this.log("setupData: No free DataConnection and couldn't create one, WEIRD");
                return false;
            }
        }
        int n4 = apnContext.incAndGetConnectionGeneration();
        object = new StringBuilder();
        ((StringBuilder)object).append("setupData: dc=");
        ((StringBuilder)object).append((Object)dataConnection);
        ((StringBuilder)object).append(" apnSetting=");
        ((StringBuilder)object).append((Object)stringBuilder);
        ((StringBuilder)object).append(" gen#=");
        ((StringBuilder)object).append(n4);
        this.log(((StringBuilder)object).toString());
        apnContext.setDataConnection(dataConnection);
        apnContext.setApnSetting((ApnSetting)stringBuilder);
        apnContext.setState(DctConstants.State.CONNECTING);
        this.mPhone.notifyDataConnection(apnContext.getApnType());
        stringBuilder = this.obtainMessage();
        ((Message)stringBuilder).what = 270336;
        ((Message)stringBuilder).obj = new Pair((Object)apnContext, (Object)n4);
        dataConnection.bringUp(apnContext, n3, n, (Message)stringBuilder, n4, n2, this.mPhone.getSubId());
        this.log("setupData: initing!");
        return true;
    }

    private void setupDataOnAllConnectableApns(String string, RetryFailures retryFailures) {
        Object object = new StringBuilder(120);
        for (ApnContext apnContext : this.mPrioritySortedApnContexts) {
            ((StringBuilder)object).append(apnContext.getApnType());
            ((StringBuilder)object).append(":[state=");
            ((StringBuilder)object).append((Object)apnContext.getState());
            ((StringBuilder)object).append(",enabled=");
            ((StringBuilder)object).append(apnContext.isEnabled());
            ((StringBuilder)object).append("] ");
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("setupDataOnAllConnectableApns: ");
        stringBuilder.append(string);
        stringBuilder.append(" ");
        stringBuilder.append(object);
        this.log(stringBuilder.toString());
        object = this.mPrioritySortedApnContexts.iterator();
        while (object.hasNext()) {
            this.setupDataOnConnectableApn((ApnContext)object.next(), string, retryFailures);
        }
    }

    private void setupDataOnConnectableApn(ApnContext apnContext, String string, RetryFailures retryFailures) {
        if (apnContext.getState() == DctConstants.State.FAILED || apnContext.getState() == DctConstants.State.RETRYING) {
            if (retryFailures == RetryFailures.ALWAYS) {
                apnContext.releaseDataConnection(string);
            } else if (!apnContext.isConcurrentVoiceAndDataAllowed() && this.mPhone.getServiceStateTracker().isConcurrentVoiceAndDataAllowed()) {
                apnContext.releaseDataConnection(string);
            }
        }
        if (apnContext.isConnectable()) {
            this.log("isConnectable() call trySetupData");
            apnContext.setReason(string);
            this.trySetupData(apnContext, 1);
        }
    }

    private void startAlarmForReconnect(long l, ApnContext apnContext) {
        CharSequence charSequence = apnContext.getApnType();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("com.android.internal.telephony.data-reconnect.");
        stringBuilder.append((String)charSequence);
        stringBuilder = new Intent(stringBuilder.toString());
        stringBuilder.putExtra(INTENT_RECONNECT_ALARM_EXTRA_REASON, apnContext.getReason());
        stringBuilder.putExtra(INTENT_RECONNECT_ALARM_EXTRA_TYPE, (String)charSequence);
        stringBuilder.putExtra(INTENT_RECONNECT_ALARM_EXTRA_TRANSPORT_TYPE, this.mTransportType);
        SubscriptionManager.putPhoneIdAndSubIdExtra((Intent)stringBuilder, (int)this.mPhone.getPhoneId());
        stringBuilder.addFlags(268435456);
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append("startAlarmForReconnect: delay=");
        ((StringBuilder)charSequence).append(l);
        ((StringBuilder)charSequence).append(" action=");
        ((StringBuilder)charSequence).append(stringBuilder.getAction());
        ((StringBuilder)charSequence).append(" apn=");
        ((StringBuilder)charSequence).append(apnContext);
        this.log(((StringBuilder)charSequence).toString());
        charSequence = PendingIntent.getBroadcast((Context)this.mPhone.getContext(), (int)0, (Intent)stringBuilder, (int)134217728);
        apnContext.setReconnectIntent((PendingIntent)charSequence);
        this.mAlarmManager.setExact(2, SystemClock.elapsedRealtime() + l, (PendingIntent)charSequence);
    }

    private void startDataStallAlarm(boolean bl) {
        if (this.mDsRecoveryHandler.isNoRxDataStallDetectionEnabled() && this.getOverallState() == DctConstants.State.CONNECTED) {
            int n = !(this.mIsScreenOn || bl || this.mDsRecoveryHandler.isAggressiveRecovery()) ? Settings.Global.getInt((ContentResolver)this.mResolver, (String)"data_stall_alarm_non_aggressive_delay_in_ms", (int)360000) : Settings.Global.getInt((ContentResolver)this.mResolver, (String)"data_stall_alarm_aggressive_delay_in_ms", (int)60000);
            ++this.mDataStallAlarmTag;
            Intent intent = new Intent(INTENT_DATA_STALL_ALARM);
            intent.putExtra(INTENT_DATA_STALL_ALARM_EXTRA_TAG, this.mDataStallAlarmTag);
            intent.putExtra(INTENT_DATA_STALL_ALARM_EXTRA_TRANSPORT_TYPE, this.mTransportType);
            SubscriptionManager.putPhoneIdAndSubIdExtra((Intent)intent, (int)this.mPhone.getPhoneId());
            this.mDataStallAlarmIntent = PendingIntent.getBroadcast((Context)this.mPhone.getContext(), (int)0, (Intent)intent, (int)134217728);
            this.mAlarmManager.set(3, SystemClock.elapsedRealtime() + (long)n, this.mDataStallAlarmIntent);
        }
    }

    private void startNetStatPoll() {
        Phone phone;
        if (this.getOverallState() == DctConstants.State.CONNECTED && !this.mNetStatPollEnabled) {
            this.log("startNetStatPoll");
            this.resetPollStats();
            this.mNetStatPollEnabled = true;
            this.mPollNetStat.run();
        }
        if ((phone = this.mPhone) != null) {
            phone.notifyDataActivity();
        }
    }

    private void startProvisioningApnAlarm() {
        int n;
        CharSequence charSequence;
        int n2 = n = Settings.Global.getInt((ContentResolver)this.mResolver, (String)"provisioning_apn_alarm_delay_in_ms", (int)900000);
        if (Build.IS_DEBUGGABLE) {
            charSequence = System.getProperty(DEBUG_PROV_APN_ALARM, Integer.toString(n));
            try {
                n2 = Integer.parseInt((String)charSequence);
            }
            catch (NumberFormatException numberFormatException) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("startProvisioningApnAlarm: e=");
                stringBuilder.append(numberFormatException);
                this.loge(stringBuilder.toString());
                n2 = n;
            }
        }
        ++this.mProvisioningApnAlarmTag;
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append("startProvisioningApnAlarm: tag=");
        ((StringBuilder)charSequence).append(this.mProvisioningApnAlarmTag);
        ((StringBuilder)charSequence).append(" delay=");
        ((StringBuilder)charSequence).append(n2 / 1000);
        ((StringBuilder)charSequence).append("s");
        this.log(((StringBuilder)charSequence).toString());
        charSequence = new Intent(INTENT_PROVISIONING_APN_ALARM);
        charSequence.putExtra(PROVISIONING_APN_ALARM_TAG_EXTRA, this.mProvisioningApnAlarmTag);
        this.mProvisioningApnAlarmIntent = PendingIntent.getBroadcast((Context)this.mPhone.getContext(), (int)0, (Intent)charSequence, (int)134217728);
        this.mAlarmManager.set(2, SystemClock.elapsedRealtime() + (long)n2, this.mProvisioningApnAlarmIntent);
    }

    private void stopDataStallAlarm() {
        ++this.mDataStallAlarmTag;
        PendingIntent pendingIntent = this.mDataStallAlarmIntent;
        if (pendingIntent != null) {
            this.mAlarmManager.cancel(pendingIntent);
            this.mDataStallAlarmIntent = null;
        }
    }

    private void stopNetStatPoll() {
        this.mNetStatPollEnabled = false;
        this.removeCallbacks(this.mPollNetStat);
        this.log("stopNetStatPoll");
        Phone phone = this.mPhone;
        if (phone != null) {
            phone.notifyDataActivity();
        }
    }

    private void stopProvisioningApnAlarm() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("stopProvisioningApnAlarm: current tag=");
        stringBuilder.append(this.mProvisioningApnAlarmTag);
        stringBuilder.append(" mProvsioningApnAlarmIntent=");
        stringBuilder.append((Object)this.mProvisioningApnAlarmIntent);
        this.log(stringBuilder.toString());
        ++this.mProvisioningApnAlarmTag;
        stringBuilder = this.mProvisioningApnAlarmIntent;
        if (stringBuilder != null) {
            this.mAlarmManager.cancel((PendingIntent)stringBuilder);
            this.mProvisioningApnAlarmIntent = null;
        }
    }

    private boolean trySetupData(ApnContext object, int n) {
        if (this.mPhone.getSimulatedRadioControl() != null) {
            ((ApnContext)object).setState(DctConstants.State.CONNECTED);
            this.mPhone.notifyDataConnection(((ApnContext)object).getApnType());
            this.log("trySetupData: X We're on the simulator; assuming connected retValue=true");
            return true;
        }
        Serializable serializable = new DataConnectionReasons();
        boolean bl = this.isDataAllowed((ApnContext)object, n, (DataConnectionReasons)((Object)serializable));
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("trySetupData for APN type ");
        stringBuilder.append(((ApnContext)object).getApnType());
        stringBuilder.append(", reason: ");
        stringBuilder.append(((ApnContext)object).getReason());
        stringBuilder.append(", requestType=");
        stringBuilder.append(DcTracker.requestTypeToString(n));
        stringBuilder.append(". ");
        stringBuilder.append(((DataConnectionReasons)((Object)serializable)).toString());
        serializable = stringBuilder.toString();
        this.log((String)((Object)serializable));
        ((ApnContext)object).requestLog((String)((Object)serializable));
        if (bl) {
            int n2;
            if (((ApnContext)object).getState() == DctConstants.State.FAILED) {
                this.log("trySetupData: make a FAILED ApnContext IDLE so its reusable");
                ((ApnContext)object).requestLog("trySetupData: make a FAILED ApnContext IDLE so its reusable");
                ((ApnContext)object).setState(DctConstants.State.IDLE);
            }
            int n3 = n2 = this.getDataRat();
            if (n2 == 0) {
                n3 = this.getVoiceRat();
            }
            serializable = new StringBuilder();
            ((StringBuilder)serializable).append("service state=");
            ((StringBuilder)serializable).append((Object)this.mPhone.getServiceState());
            this.log(((StringBuilder)serializable).toString());
            ((ApnContext)object).setConcurrentVoiceAndDataAllowed(this.mPhone.getServiceStateTracker().isConcurrentVoiceAndDataAllowed());
            if (((ApnContext)object).getState() == DctConstants.State.IDLE) {
                serializable = this.buildWaitingApns(((ApnContext)object).getApnType(), n3);
                if (((ArrayList)serializable).isEmpty()) {
                    this.notifyNoData(27, (ApnContext)object);
                    this.log("trySetupData: X No APN found retValue=false");
                    ((ApnContext)object).requestLog("trySetupData: X No APN found retValue=false");
                    return false;
                }
                ((ApnContext)object).setWaitingApns((ArrayList<ApnSetting>)serializable);
                serializable = new StringBuilder();
                ((StringBuilder)serializable).append("trySetupData: Create from mAllApnSettings : ");
                ((StringBuilder)serializable).append(this.apnListToString(this.mAllApnSettings));
                this.log(((StringBuilder)serializable).toString());
            }
            bl = this.setupData((ApnContext)object, n3, n);
            object = new StringBuilder();
            ((StringBuilder)object).append("trySetupData: X retValue=");
            ((StringBuilder)object).append(bl);
            this.log(((StringBuilder)object).toString());
            return bl;
        }
        serializable = new StringBuilder();
        stringBuilder = new StringBuilder();
        stringBuilder.append("trySetupData failed. apnContext = [type=");
        stringBuilder.append(((ApnContext)object).getApnType());
        stringBuilder.append(", mState=");
        stringBuilder.append((Object)((ApnContext)object).getState());
        stringBuilder.append(", apnEnabled=");
        stringBuilder.append(((ApnContext)object).isEnabled());
        stringBuilder.append(", mDependencyMet=");
        stringBuilder.append(((ApnContext)object).isDependencyMet());
        stringBuilder.append("] ");
        ((StringBuilder)serializable).append(stringBuilder.toString());
        if (!this.mDataEnabledSettings.isDataEnabled()) {
            stringBuilder = new StringBuilder();
            stringBuilder.append("isDataEnabled() = false. ");
            stringBuilder.append(this.mDataEnabledSettings);
            ((StringBuilder)serializable).append(stringBuilder.toString());
        }
        if (((ApnContext)object).getState() == DctConstants.State.RETRYING) {
            ((ApnContext)object).setState(DctConstants.State.FAILED);
            ((StringBuilder)serializable).append(" Stop retrying.");
        }
        this.log(((StringBuilder)serializable).toString());
        ((ApnContext)object).requestLog(((StringBuilder)serializable).toString());
        return false;
    }

    private void unregisterForAllEvents() {
        IccRecords iccRecords;
        if (this.mTransportType == 1) {
            this.mPhone.mCi.unregisterForAvailable(this);
            this.mPhone.mCi.unregisterForOffOrNotAvailable(this);
            this.mPhone.mCi.unregisterForPcoData(this);
        }
        if ((iccRecords = this.mIccRecords.get()) != null) {
            iccRecords.unregisterForRecordsLoaded(this);
            this.mIccRecords.set(null);
        }
        this.mPhone.getCallTracker().unregisterForVoiceCallEnded(this);
        this.mPhone.getCallTracker().unregisterForVoiceCallStarted(this);
        this.unregisterServiceStateTrackerEvents();
        this.mDataServiceManager.unregisterForServiceBindingChanged(this);
        this.mDataEnabledSettings.unregisterForDataEnabledChanged(this);
        this.mDataEnabledSettings.unregisterForDataEnabledOverrideChanged(this);
    }

    private void updateDataActivity() {
        TxRxSum txRxSum = new TxRxSum(this.mTxPkts, this.mRxPkts);
        TxRxSum txRxSum2 = new TxRxSum();
        txRxSum2.updateTotalTxRxSum();
        this.mTxPkts = txRxSum2.txPkts;
        this.mRxPkts = txRxSum2.rxPkts;
        if (this.mNetStatPollEnabled && (txRxSum.txPkts > 0L || txRxSum.rxPkts > 0L)) {
            long l = this.mTxPkts - txRxSum.txPkts;
            long l2 = this.mRxPkts - txRxSum.rxPkts;
            txRxSum = l > 0L && l2 > 0L ? DctConstants.Activity.DATAINANDOUT : (l > 0L && l2 == 0L ? DctConstants.Activity.DATAOUT : (l == 0L && l2 > 0L ? DctConstants.Activity.DATAIN : (this.mActivity == DctConstants.Activity.DORMANT ? this.mActivity : DctConstants.Activity.NONE)));
            if (this.mActivity != txRxSum && this.mIsScreenOn) {
                this.mActivity = txRxSum;
                this.mPhone.notifyDataActivity();
            }
        }
    }

    private void updateDataStallInfo() {
        Object object = new TxRxSum(this.mDataStallTxRxSum);
        this.mDataStallTxRxSum.updateTcpTxRxSum();
        long l = this.mDataStallTxRxSum.txPkts - ((TxRxSum)object).txPkts;
        long l2 = this.mDataStallTxRxSum.rxPkts - ((TxRxSum)object).rxPkts;
        if (l > 0L && l2 > 0L) {
            this.mSentSinceLastRecv = 0L;
            this.mDsRecoveryHandler.reset();
        } else if (l > 0L && l2 == 0L) {
            this.mSentSinceLastRecv = this.isPhoneStateIdle() ? (this.mSentSinceLastRecv += l) : 0L;
            object = new StringBuilder();
            ((StringBuilder)object).append("updateDataStallInfo: OUT sent=");
            ((StringBuilder)object).append(l);
            ((StringBuilder)object).append(" mSentSinceLastRecv=");
            ((StringBuilder)object).append(this.mSentSinceLastRecv);
            this.log(((StringBuilder)object).toString());
        } else if (l == 0L && l2 > 0L) {
            this.mSentSinceLastRecv = 0L;
            this.mDsRecoveryHandler.reset();
        }
    }

    public void cleanUpAllConnections(String string) {
        this.log("cleanUpAllConnections");
        Message message = this.obtainMessage(270365);
        message.obj = string;
        this.sendMessage(message);
    }

    void cleanUpConnection(ApnContext apnContext) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("cleanUpConnection: apnContext=");
        stringBuilder.append(apnContext);
        this.log(stringBuilder.toString());
        stringBuilder = this.obtainMessage(270360);
        ((Message)stringBuilder).arg2 = 0;
        ((Message)stringBuilder).obj = apnContext;
        this.sendMessage((Message)stringBuilder);
    }

    public void disableApn(int n, int n2) {
        this.sendMessage(this.obtainMessage(270350, n, n2));
    }

    public void dispose() {
        ProgressDialog progressDialog;
        this.log("DCT.dispose");
        if (this.mProvisionBroadcastReceiver != null) {
            this.mPhone.getContext().unregisterReceiver(this.mProvisionBroadcastReceiver);
            this.mProvisionBroadcastReceiver = null;
        }
        if ((progressDialog = this.mProvisioningSpinner) != null) {
            progressDialog.dismiss();
            this.mProvisioningSpinner = null;
        }
        this.cleanUpAllConnectionsInternal(true, null);
        this.mIsDisposed = true;
        this.mPhone.getContext().unregisterReceiver(this.mIntentReceiver);
        this.mUiccController.unregisterForIccChanged(this);
        this.mSettingsObserver.unobserve();
        this.mSubscriptionManager.removeOnSubscriptionsChangedListener((SubscriptionManager.OnSubscriptionsChangedListener)this.mOnSubscriptionsChangedListener);
        this.mDcc.dispose();
        this.mDcTesterFailBringUpAll.dispose();
        this.mPhone.getContext().getContentResolver().unregisterContentObserver((ContentObserver)this.mApnObserver);
        this.mApnContexts.clear();
        this.mApnContextsByType.clear();
        this.mPrioritySortedApnContexts.clear();
        this.unregisterForAllEvents();
        this.destroyDataConnections();
    }

    public void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] arrstring) {
        Object object;
        printWriter.println("DcTracker:");
        printWriter.println(" RADIO_TESTS=false");
        Object object2 = new StringBuilder();
        ((StringBuilder)object2).append(" mDataEnabledSettings=");
        ((StringBuilder)object2).append(this.mDataEnabledSettings);
        printWriter.println(((StringBuilder)object2).toString());
        object2 = new StringBuilder();
        ((StringBuilder)object2).append(" isDataAllowed=");
        ((StringBuilder)object2).append(this.isDataAllowed(null));
        printWriter.println(((StringBuilder)object2).toString());
        printWriter.flush();
        object2 = new StringBuilder();
        ((StringBuilder)object2).append(" mRequestedApnType=");
        ((StringBuilder)object2).append(this.mRequestedApnType);
        printWriter.println(((StringBuilder)object2).toString());
        object2 = new StringBuilder();
        ((StringBuilder)object2).append(" mPhone=");
        ((StringBuilder)object2).append(this.mPhone.getPhoneName());
        printWriter.println(((StringBuilder)object2).toString());
        object2 = new StringBuilder();
        ((StringBuilder)object2).append(" mActivity=");
        ((StringBuilder)object2).append((Object)this.mActivity);
        printWriter.println(((StringBuilder)object2).toString());
        object2 = new StringBuilder();
        ((StringBuilder)object2).append(" mState=");
        ((StringBuilder)object2).append((Object)this.mState);
        printWriter.println(((StringBuilder)object2).toString());
        object2 = new StringBuilder();
        ((StringBuilder)object2).append(" mTxPkts=");
        ((StringBuilder)object2).append(this.mTxPkts);
        printWriter.println(((StringBuilder)object2).toString());
        object2 = new StringBuilder();
        ((StringBuilder)object2).append(" mRxPkts=");
        ((StringBuilder)object2).append(this.mRxPkts);
        printWriter.println(((StringBuilder)object2).toString());
        object2 = new StringBuilder();
        ((StringBuilder)object2).append(" mNetStatPollPeriod=");
        ((StringBuilder)object2).append(this.mNetStatPollPeriod);
        printWriter.println(((StringBuilder)object2).toString());
        object2 = new StringBuilder();
        ((StringBuilder)object2).append(" mNetStatPollEnabled=");
        ((StringBuilder)object2).append(this.mNetStatPollEnabled);
        printWriter.println(((StringBuilder)object2).toString());
        object2 = new StringBuilder();
        ((StringBuilder)object2).append(" mDataStallTxRxSum=");
        ((StringBuilder)object2).append(this.mDataStallTxRxSum);
        printWriter.println(((StringBuilder)object2).toString());
        object2 = new StringBuilder();
        ((StringBuilder)object2).append(" mDataStallAlarmTag=");
        ((StringBuilder)object2).append(this.mDataStallAlarmTag);
        printWriter.println(((StringBuilder)object2).toString());
        object2 = new StringBuilder();
        ((StringBuilder)object2).append(" mDataStallNoRxEnabled=");
        ((StringBuilder)object2).append(this.mDataStallNoRxEnabled);
        printWriter.println(((StringBuilder)object2).toString());
        object2 = new StringBuilder();
        ((StringBuilder)object2).append(" mEmergencyApn=");
        ((StringBuilder)object2).append((Object)this.mEmergencyApn);
        printWriter.println(((StringBuilder)object2).toString());
        object2 = new StringBuilder();
        ((StringBuilder)object2).append(" mSentSinceLastRecv=");
        ((StringBuilder)object2).append(this.mSentSinceLastRecv);
        printWriter.println(((StringBuilder)object2).toString());
        object2 = new StringBuilder();
        ((StringBuilder)object2).append(" mNoRecvPollCount=");
        ((StringBuilder)object2).append(this.mNoRecvPollCount);
        printWriter.println(((StringBuilder)object2).toString());
        object2 = new StringBuilder();
        ((StringBuilder)object2).append(" mResolver=");
        ((StringBuilder)object2).append((Object)this.mResolver);
        printWriter.println(((StringBuilder)object2).toString());
        object2 = new StringBuilder();
        ((StringBuilder)object2).append(" mReconnectIntent=");
        ((StringBuilder)object2).append((Object)this.mReconnectIntent);
        printWriter.println(((StringBuilder)object2).toString());
        object2 = new StringBuilder();
        ((StringBuilder)object2).append(" mAutoAttachEnabled=");
        ((StringBuilder)object2).append(this.mAutoAttachEnabled.get());
        printWriter.println(((StringBuilder)object2).toString());
        object2 = new StringBuilder();
        ((StringBuilder)object2).append(" mIsScreenOn=");
        ((StringBuilder)object2).append(this.mIsScreenOn);
        printWriter.println(((StringBuilder)object2).toString());
        object2 = new StringBuilder();
        ((StringBuilder)object2).append(" mUniqueIdGenerator=");
        ((StringBuilder)object2).append(this.mUniqueIdGenerator);
        printWriter.println(((StringBuilder)object2).toString());
        object2 = new StringBuilder();
        ((StringBuilder)object2).append(" mDataServiceBound=");
        ((StringBuilder)object2).append(this.mDataServiceBound);
        printWriter.println(((StringBuilder)object2).toString());
        printWriter.println(" mDataRoamingLeakageLog= ");
        this.mDataRoamingLeakageLog.dump(fileDescriptor, printWriter, arrstring);
        printWriter.println(" mApnSettingsInitializationLog= ");
        this.mApnSettingsInitializationLog.dump(fileDescriptor, printWriter, arrstring);
        printWriter.flush();
        printWriter.println(" ***************************************");
        object2 = this.mDcc;
        if (object2 != null) {
            if (this.mDataServiceBound) {
                ((DcController)((Object)object2)).dump(fileDescriptor, printWriter, arrstring);
            } else {
                printWriter.println(" Can't dump mDcc because data service is not bound.");
            }
        } else {
            printWriter.println(" mDcc=null");
        }
        printWriter.println(" ***************************************");
        if (this.mDataConnections != null) {
            object = this.mDataConnections.entrySet();
            object2 = new StringBuilder();
            ((StringBuilder)object2).append(" mDataConnections: count=");
            ((StringBuilder)object2).append(object.size());
            printWriter.println(((StringBuilder)object2).toString());
            object = object.iterator();
            while (object.hasNext()) {
                object2 = (Map.Entry)object.next();
                printWriter.printf(" *** mDataConnection[%d] \n", object2.getKey());
                ((DataConnection)((Object)object2.getValue())).dump(fileDescriptor, printWriter, arrstring);
            }
        } else {
            printWriter.println("mDataConnections=null");
        }
        printWriter.println(" ***************************************");
        printWriter.flush();
        object2 = this.mApnToDataConnectionId;
        if (object2 != null) {
            object2 = ((HashMap)object2).entrySet();
            object = new StringBuilder();
            ((StringBuilder)object).append(" mApnToDataConnectonId size=");
            ((StringBuilder)object).append(object2.size());
            printWriter.println(((StringBuilder)object).toString());
            object2 = object2.iterator();
            while (object2.hasNext()) {
                object = (Map.Entry)object2.next();
                printWriter.printf(" mApnToDataConnectonId[%s]=%d\n", object.getKey(), object.getValue());
            }
        } else {
            printWriter.println("mApnToDataConnectionId=null");
        }
        printWriter.println(" ***************************************");
        printWriter.flush();
        object2 = this.mApnContexts;
        if (object2 != null) {
            object2 = ((ConcurrentHashMap)object2).entrySet();
            object = new StringBuilder();
            ((StringBuilder)object).append(" mApnContexts size=");
            ((StringBuilder)object).append(object2.size());
            printWriter.println(((StringBuilder)object).toString());
            object2 = object2.iterator();
            while (object2.hasNext()) {
                ((ApnContext)((Map.Entry)object2.next()).getValue()).dump(fileDescriptor, printWriter, arrstring);
            }
            printWriter.println(" ***************************************");
        } else {
            printWriter.println(" mApnContexts=null");
        }
        printWriter.flush();
        object2 = new StringBuilder();
        ((StringBuilder)object2).append(" mAllApnSettings size=");
        ((StringBuilder)object2).append(this.mAllApnSettings.size());
        printWriter.println(((StringBuilder)object2).toString());
        for (int i = 0; i < this.mAllApnSettings.size(); ++i) {
            printWriter.printf(" mAllApnSettings[%d]: %s\n", new Object[]{i, this.mAllApnSettings.get(i)});
        }
        printWriter.flush();
        object2 = new StringBuilder();
        ((StringBuilder)object2).append(" mPreferredApn=");
        ((StringBuilder)object2).append((Object)this.mPreferredApn);
        printWriter.println(((StringBuilder)object2).toString());
        object2 = new StringBuilder();
        ((StringBuilder)object2).append(" mIsPsRestricted=");
        ((StringBuilder)object2).append(this.mIsPsRestricted);
        printWriter.println(((StringBuilder)object2).toString());
        object2 = new StringBuilder();
        ((StringBuilder)object2).append(" mIsDisposed=");
        ((StringBuilder)object2).append(this.mIsDisposed);
        printWriter.println(((StringBuilder)object2).toString());
        object2 = new StringBuilder();
        ((StringBuilder)object2).append(" mIntentReceiver=");
        ((StringBuilder)object2).append((Object)this.mIntentReceiver);
        printWriter.println(((StringBuilder)object2).toString());
        object2 = new StringBuilder();
        ((StringBuilder)object2).append(" mReregisterOnReconnectFailure=");
        ((StringBuilder)object2).append(this.mReregisterOnReconnectFailure);
        printWriter.println(((StringBuilder)object2).toString());
        object2 = new StringBuilder();
        ((StringBuilder)object2).append(" canSetPreferApn=");
        ((StringBuilder)object2).append(this.mCanSetPreferApn);
        printWriter.println(((StringBuilder)object2).toString());
        object2 = new StringBuilder();
        ((StringBuilder)object2).append(" mApnObserver=");
        ((StringBuilder)object2).append((Object)this.mApnObserver);
        printWriter.println(((StringBuilder)object2).toString());
        object2 = new StringBuilder();
        ((StringBuilder)object2).append(" getOverallState=");
        ((StringBuilder)object2).append((Object)this.getOverallState());
        printWriter.println(((StringBuilder)object2).toString());
        object2 = new StringBuilder();
        ((StringBuilder)object2).append(" mAttached=");
        ((StringBuilder)object2).append(this.mAttached.get());
        printWriter.println(((StringBuilder)object2).toString());
        this.mDataEnabledSettings.dump(fileDescriptor, printWriter, arrstring);
        printWriter.flush();
    }

    public void enableApn(int n, int n2, Message message) {
        this.sendMessage(this.obtainMessage(270349, n, n2, (Object)message));
    }

    @VisibleForTesting
    public ArrayList<ApnSetting> fetchDunApns() {
        if (SystemProperties.getBoolean((String)"net.tethering.noprovisioning", (boolean)false)) {
            this.log("fetchDunApns: net.tethering.noprovisioning=true ret: empty list");
            return new ArrayList<ApnSetting>(0);
        }
        int n = this.getDataRat();
        Object object = new ArrayList();
        ArrayList<ApnSetting> arrayList = new ArrayList<ApnSetting>();
        Object object2 = Settings.Global.getString((ContentResolver)this.mResolver, (String)"tether_dun_apn");
        if (!TextUtils.isEmpty((CharSequence)object2)) {
            ((ArrayList)object).addAll(ApnSetting.arrayFromString((String)object2));
        }
        if (((ArrayList)object).isEmpty() && !ArrayUtils.isEmpty(this.mAllApnSettings)) {
            for (ApnSetting apnSetting : this.mAllApnSettings) {
                if (!apnSetting.canHandleType(8)) continue;
                ((ArrayList)object).add(apnSetting);
            }
        }
        object = ((ArrayList)object).iterator();
        while (object.hasNext()) {
            object2 = (ApnSetting)object.next();
            if (!object2.canSupportNetworkType(ServiceState.rilRadioTechnologyToNetworkType((int)n))) continue;
            arrayList.add((ApnSetting)object2);
        }
        return arrayList;
    }

    protected void finalize() {
        if (this.mPhone != null) {
            this.log("finalize");
        }
    }

    public String getActiveApnString(String object) {
        if ((object = this.mApnContexts.get(object)) != null && (object = ((ApnContext)object).getApnSetting()) != null) {
            return object.getApnName();
        }
        return null;
    }

    public String[] getActiveApnTypes() {
        this.log("get all active apn types");
        ArrayList<String> arrayList = new ArrayList<String>();
        for (ApnContext apnContext : this.mApnContexts.values()) {
            if (!this.mAttached.get() || !apnContext.isReady()) continue;
            arrayList.add(apnContext.getApnType());
        }
        return arrayList.toArray(new String[0]);
    }

    public DctConstants.Activity getActivity() {
        return this.mActivity;
    }

    public DataConnection getDataConnectionByApnType(String object) {
        if ((object = this.mApnContexts.get(object)) != null) {
            return ((ApnContext)object).getDataConnection();
        }
        return null;
    }

    public DataConnection getDataConnectionByContextId(int n) {
        return this.mDcc.getActiveDcByCid(n);
    }

    public boolean getDataRoamingEnabled() {
        return this.mDataEnabledSettings.getDataRoamingEnabled();
    }

    public LinkProperties getLinkProperties(String string) {
        Object object = this.mApnContexts.get(string);
        if (object != null && (object = ((ApnContext)object).getDataConnection()) != null) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("return link properties for ");
            stringBuilder.append(string);
            this.log(stringBuilder.toString());
            return ((DataConnection)((Object)object)).getLinkProperties();
        }
        this.log("return new LinkProperties");
        return new LinkProperties();
    }

    public NetworkCapabilities getNetworkCapabilities(String string) {
        DataConnection dataConnection;
        Object object = this.mApnContexts.get(string);
        if (object != null && (dataConnection = ((ApnContext)object).getDataConnection()) != null) {
            object = new StringBuilder();
            ((StringBuilder)object).append("get active pdp is not null, return NetworkCapabilities for ");
            ((StringBuilder)object).append(string);
            this.log(((StringBuilder)object).toString());
            return dataConnection.getNetworkCapabilities();
        }
        this.log("return new NetworkCapabilities");
        return new NetworkCapabilities();
    }

    public DctConstants.State getOverallState() {
        boolean bl = false;
        int n = 1;
        boolean bl2 = false;
        for (ApnContext apnContext : this.mApnContexts.values()) {
            boolean bl3 = bl;
            int n2 = n;
            if (apnContext.isEnabled()) {
                bl2 = true;
                n2 = 5.$SwitchMap$com$android$internal$telephony$DctConstants$State[apnContext.getState().ordinal()];
                if (n2 != 1 && n2 != 2) {
                    if (n2 != 3) {
                        if (n2 != 4 && n2 != 5) {
                            bl2 = true;
                            bl3 = bl;
                            n2 = n;
                        } else {
                            n2 = 0;
                            bl3 = bl;
                        }
                    } else {
                        bl3 = true;
                        n2 = 0;
                    }
                } else {
                    return DctConstants.State.CONNECTED;
                }
            }
            bl = bl3;
            n = n2;
        }
        if (!bl2) {
            return DctConstants.State.IDLE;
        }
        if (bl) {
            return DctConstants.State.CONNECTING;
        }
        if (n == 0) {
            return DctConstants.State.IDLE;
        }
        return DctConstants.State.FAILED;
    }

    public String[] getPcscfAddress(String object) {
        block10 : {
            block9 : {
                block8 : {
                    this.log("getPcscfAddress()");
                    if (object == null) {
                        this.log("apnType is null, return null");
                        return null;
                    }
                    if (!TextUtils.equals((CharSequence)object, (CharSequence)"emergency")) break block8;
                    object = (ApnContext)this.mApnContextsByType.get(512);
                    break block9;
                }
                if (!TextUtils.equals((CharSequence)object, (CharSequence)"ims")) break block10;
                object = (ApnContext)this.mApnContextsByType.get(64);
            }
            if (object == null) {
                this.log("apnContext is null, return null");
                return null;
            }
            if ((object = ((ApnContext)object).getDataConnection()) != null) {
                String[] arrstring = ((DataConnection)((Object)object)).getPcscfAddresses();
                if (arrstring != null) {
                    for (int i = 0; i < arrstring.length; ++i) {
                        object = new StringBuilder();
                        ((StringBuilder)object).append("Pcscf[");
                        ((StringBuilder)object).append(i);
                        ((StringBuilder)object).append("]: ");
                        ((StringBuilder)object).append(arrstring[i]);
                        this.log(((StringBuilder)object).toString());
                    }
                }
                return arrstring;
            }
            return null;
        }
        this.log("apnType is invalid, return null");
        return null;
    }

    ApnSetting getPreferredApn() {
        Cursor cursor = this.mAllApnSettings;
        if (cursor != null && !cursor.isEmpty()) {
            cursor = Long.toString(this.mPhone.getSubId());
            cursor = Uri.withAppendedPath((Uri)PREFERAPN_NO_UPDATE_URI_USING_SUBID, (String)cursor);
            cursor = this.mPhone.getContext().getContentResolver().query((Uri)cursor, new String[]{"_id", "name", "apn"}, null, null, "name ASC");
            this.mCanSetPreferApn = cursor != null;
            if (this.mCanSetPreferApn && cursor.getCount() > 0) {
                cursor.moveToFirst();
                int n = cursor.getInt(cursor.getColumnIndexOrThrow("_id"));
                Object object = this.mAllApnSettings.iterator();
                while (object.hasNext()) {
                    ApnSetting apnSetting = object.next();
                    if (apnSetting.getId() != n || !apnSetting.canHandleType(this.mRequestedApnType)) continue;
                    object = new StringBuilder();
                    ((StringBuilder)object).append("getPreferredApn: For APN type ");
                    ((StringBuilder)object).append(ApnSetting.getApnTypeString((int)this.mRequestedApnType));
                    ((StringBuilder)object).append(" found apnSetting ");
                    ((StringBuilder)object).append((Object)apnSetting);
                    this.log(((StringBuilder)object).toString());
                    cursor.close();
                    return apnSetting;
                }
            }
            if (cursor != null) {
                cursor.close();
            }
            this.log("getPreferredApn: X not found");
            return null;
        }
        this.log("getPreferredApn: mAllApnSettings is empty");
        return null;
    }

    public DctConstants.State getState(String object2) {
        int n = ApnSetting.getApnTypesBitmaskFromString((String)object2);
        for (DataConnection dataConnection : this.mDataConnections.values()) {
            ApnSetting apnSetting = dataConnection.getApnSetting();
            if (apnSetting == null || !apnSetting.canHandleType(n)) continue;
            if (dataConnection.isActive()) {
                return DctConstants.State.CONNECTED;
            }
            if (dataConnection.isActivating()) {
                return DctConstants.State.CONNECTING;
            }
            if (dataConnection.isInactive()) {
                return DctConstants.State.IDLE;
            }
            if (!dataConnection.isDisconnecting()) continue;
            return DctConstants.State.DISCONNECTING;
        }
        return DctConstants.State.IDLE;
    }

    public long getSubId() {
        return this.mPhone.getSubId();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void handleMessage(Message object) {
        int n = ((Message)object).what;
        int n2 = 0;
        boolean bl = true;
        switch (n) {
            default: {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Unhandled event=");
                stringBuilder.append(object);
                Rlog.e((String)"DcTracker", (String)stringBuilder.toString());
                return;
            }
            case 270387: {
                this.onDataEnabledOverrideRulesChanged();
                return;
            }
            case 270386: {
                object = PreferenceManager.getDefaultSharedPreferences((Context)this.mPhone.getContext());
                if (object.contains("data_roaming_is_user_setting_key")) return;
                object.edit().putBoolean("data_roaming_is_user_setting_key", false).commit();
                return;
            }
            case 270385: {
                this.onDataServiceBindingChanged((Boolean)((AsyncResult)object.obj).result);
                return;
            }
            case 270383: {
                this.onDataReconnect(object.getData());
                return;
            }
            case 270382: {
                object = (AsyncResult)((Message)object).obj;
                if (!(((AsyncResult)object).result instanceof Pair)) return;
                object = (Pair)((AsyncResult)object).result;
                this.onDataEnabledChanged((Boolean)((Pair)object).first, (Integer)((Pair)object).second);
                return;
            }
            case 270381: {
                this.handlePcoData((AsyncResult)((Message)object).obj);
                return;
            }
            case 270380: {
                this.onNetworkStatusChanged(((Message)object).arg1, (String)((Message)object).obj);
                return;
            }
            case 270378: {
                if (this.mProvisioningSpinner != ((Message)object).obj) return;
                this.mProvisioningSpinner.dismiss();
                this.mProvisioningSpinner = null;
                return;
            }
            case 270377: {
                if (this.getDataRat() == 0) {
                    return;
                }
                this.cleanUpConnectionsOnUpdatedApns(false, "nwTypeChanged");
                this.setupDataOnAllConnectableApns("nwTypeChanged", RetryFailures.ONLY_ON_CHANGE);
                return;
            }
            case 270376: {
                if (((Message)object).arg1 == 1) {
                    this.handleStartNetStatPoll((DctConstants.Activity)((Message)object).obj);
                    return;
                }
                if (((Message)object).arg1 != 0) return;
                this.handleStopNetStatPoll((DctConstants.Activity)((Message)object).obj);
                return;
            }
            case 270375: {
                this.log("EVENT_PROVISIONING_APN_ALARM");
                Object object2 = (ApnContext)this.mApnContextsByType.get(17);
                if (((ApnContext)object2).isProvisioningApn() && ((ApnContext)object2).isConnectedOrConnecting()) {
                    if (this.mProvisioningApnAlarmTag == ((Message)object).arg1) {
                        this.log("EVENT_PROVISIONING_APN_ALARM: Disconnecting");
                        this.mIsProvisioning = false;
                        this.mProvisioningUrl = null;
                        this.stopProvisioningApnAlarm();
                        this.cleanUpConnectionInternal(true, 2, (ApnContext)object2);
                        return;
                    }
                    object2 = new StringBuilder();
                    ((StringBuilder)object2).append("EVENT_PROVISIONING_APN_ALARM: ignore stale tag, mProvisioningApnAlarmTag:");
                    ((StringBuilder)object2).append(this.mProvisioningApnAlarmTag);
                    ((StringBuilder)object2).append(" != arg1:");
                    ((StringBuilder)object2).append(((Message)object).arg1);
                    this.log(((StringBuilder)object2).toString());
                    return;
                }
                this.log("EVENT_PROVISIONING_APN_ALARM: Not connected ignore");
                return;
            }
            case 270374: {
                boolean bl2;
                this.log("CMD_IS_PROVISIONING_APN");
                CharSequence charSequence = null;
                try {
                    Bundle bundle = object.getData();
                    if (bundle != null) {
                        charSequence = (String)bundle.get("apnType");
                    }
                    if (TextUtils.isEmpty(charSequence)) {
                        this.loge("CMD_IS_PROVISIONING_APN: apnType is empty");
                        bl2 = false;
                    } else {
                        bl2 = this.isProvisioningApn((String)charSequence);
                    }
                }
                catch (ClassCastException classCastException) {
                    this.loge("CMD_IS_PROVISIONING_APN: NO provisioning url ignoring");
                    bl2 = false;
                }
                charSequence = new StringBuilder();
                ((StringBuilder)charSequence).append("CMD_IS_PROVISIONING_APN: ret=");
                ((StringBuilder)charSequence).append(bl2);
                this.log(((StringBuilder)charSequence).toString());
                charSequence = this.mReplyAc;
                if (bl2) {
                    n2 = 1;
                }
                charSequence.replyToMessage((Message)object, 270374, n2);
                return;
            }
            case 270373: {
                object = object.getData();
                if (object != null) {
                    try {
                        this.mProvisioningUrl = (String)object.get("provisioningUrl");
                    }
                    catch (ClassCastException classCastException) {
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("CMD_ENABLE_MOBILE_PROVISIONING: provisioning url not a string");
                        stringBuilder.append(classCastException);
                        this.loge(stringBuilder.toString());
                        this.mProvisioningUrl = null;
                    }
                }
                if (TextUtils.isEmpty((CharSequence)this.mProvisioningUrl)) {
                    this.loge("CMD_ENABLE_MOBILE_PROVISIONING: provisioning url is empty, ignoring");
                    this.mIsProvisioning = false;
                    this.mProvisioningUrl = null;
                    return;
                }
                object = new StringBuilder();
                ((StringBuilder)object).append("CMD_ENABLE_MOBILE_PROVISIONING: provisioningUrl=");
                ((StringBuilder)object).append(this.mProvisioningUrl);
                this.loge(((StringBuilder)object).toString());
                this.mIsProvisioning = true;
                this.startProvisioningApnAlarm();
                return;
            }
            case 270372: {
                n = sEnableFailFastRefCounter;
                n2 = ((Message)object).arg1 == 1 ? 1 : -1;
                sEnableFailFastRefCounter = n + n2;
                object = new StringBuilder();
                ((StringBuilder)object).append("CMD_SET_ENABLE_FAIL_FAST_MOBILE_DATA:  sEnableFailFastRefCounter=");
                ((StringBuilder)object).append(sEnableFailFastRefCounter);
                this.log(((StringBuilder)object).toString());
                if (sEnableFailFastRefCounter < 0) {
                    object = new StringBuilder();
                    ((StringBuilder)object).append("CMD_SET_ENABLE_FAIL_FAST_MOBILE_DATA: sEnableFailFastRefCounter:");
                    ((StringBuilder)object).append(sEnableFailFastRefCounter);
                    ((StringBuilder)object).append(" < 0");
                    this.loge(((StringBuilder)object).toString());
                    sEnableFailFastRefCounter = 0;
                }
                boolean bl3 = sEnableFailFastRefCounter > 0;
                object = new StringBuilder();
                ((StringBuilder)object).append("CMD_SET_ENABLE_FAIL_FAST_MOBILE_DATA: enabled=");
                ((StringBuilder)object).append(bl3);
                ((StringBuilder)object).append(" sEnableFailFastRefCounter=");
                ((StringBuilder)object).append(sEnableFailFastRefCounter);
                this.log(((StringBuilder)object).toString());
                if (this.mFailFast == bl3) return;
                this.mFailFast = bl3;
                bl3 = !bl3 ? bl : false;
                this.mDataStallNoRxEnabled = bl3;
                if (this.mDsRecoveryHandler.isNoRxDataStallDetectionEnabled() && this.getOverallState() == DctConstants.State.CONNECTED && (!this.mInVoiceCall || this.mPhone.getServiceStateTracker().isConcurrentVoiceAndDataAllowed())) {
                    this.log("CMD_SET_ENABLE_FAIL_FAST_MOBILE_DATA: start data stall");
                    this.stopDataStallAlarm();
                    this.startDataStallAlarm(false);
                    return;
                }
                this.log("CMD_SET_ENABLE_FAIL_FAST_MOBILE_DATA: stop data stall");
                this.stopDataStallAlarm();
                return;
            }
            case 270371: {
                Pair pair = (Pair)((AsyncResult)object.obj).userObj;
                ApnContext apnContext = (ApnContext)pair.first;
                n2 = (Integer)pair.second;
                n = ((Message)object).arg2;
                if (apnContext.getConnectionGeneration() == n2) {
                    this.onDataSetupCompleteError(apnContext, n);
                    return;
                }
                this.loge("EVENT_DATA_SETUP_COMPLETE_ERROR: Dropped the event because generation did not match.");
                return;
            }
            case 270369: {
                this.onUpdateIcc();
                return;
            }
            case 270365: {
                if (((Message)object).obj != null && !(((Message)object).obj instanceof String)) {
                    ((Message)object).obj = null;
                }
                this.cleanUpAllConnectionsInternal(true, (String)((Message)object).obj);
                return;
            }
            case 270362: {
                this.restartRadio();
                return;
            }
            case 270360: {
                this.log("EVENT_CLEAN_UP_CONNECTION");
                this.cleanUpConnectionInternal(true, 2, (ApnContext)((Message)object).obj);
                return;
            }
            case 270359: {
                object = new StringBuilder();
                ((StringBuilder)object).append("EVENT_PS_RESTRICT_DISABLED ");
                ((StringBuilder)object).append(this.mIsPsRestricted);
                this.log(((StringBuilder)object).toString());
                this.mIsPsRestricted = false;
                if (this.isConnected()) {
                    this.startNetStatPoll();
                    this.startDataStallAlarm(false);
                    return;
                }
                if (this.mState == DctConstants.State.FAILED) {
                    this.cleanUpAllConnectionsInternal(false, "psRestrictEnabled");
                    this.mReregisterOnReconnectFailure = false;
                }
                if ((object = (ApnContext)this.mApnContextsByType.get(17)) != null) {
                    ((ApnContext)object).setReason("psRestrictEnabled");
                    this.trySetupData((ApnContext)object, 1);
                    return;
                }
                this.loge("**** Default ApnContext not found ****");
                if (Build.IS_DEBUGGABLE) throw new RuntimeException("Default ApnContext not found");
                return;
            }
            case 270358: {
                object = new StringBuilder();
                ((StringBuilder)object).append("EVENT_PS_RESTRICT_ENABLED ");
                ((StringBuilder)object).append(this.mIsPsRestricted);
                this.log(((StringBuilder)object).toString());
                this.stopNetStatPoll();
                this.stopDataStallAlarm();
                this.mIsPsRestricted = true;
                return;
            }
            case 270355: {
                this.onApnChanged();
                return;
            }
            case 270354: {
                this.mDsRecoveryHandler.doRecovery();
                return;
            }
            case 270353: {
                this.onDataStallAlarm(((Message)object).arg1);
                return;
            }
            case 270352: {
                this.onDataConnectionAttached();
                return;
            }
            case 270351: {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("EVENT_DISCONNECT_DONE msg=");
                stringBuilder.append(object);
                this.log(stringBuilder.toString());
                stringBuilder = (Pair)((AsyncResult)object.obj).userObj;
                object = (ApnContext)((Pair)stringBuilder).first;
                n2 = (Integer)((Pair)stringBuilder).second;
                if (((ApnContext)object).getConnectionGeneration() == n2) {
                    this.onDisconnectDone((ApnContext)object);
                    return;
                }
                this.loge("EVENT_DISCONNECT_DONE: Dropped the event because generation did not match.");
                return;
            }
            case 270350: {
                this.onDisableApn(((Message)object).arg1, ((Message)object).arg2);
                return;
            }
            case 270349: {
                this.onEnableApn(((Message)object).arg1, ((Message)object).arg2, (Message)((Message)object).obj);
                return;
            }
            case 270348: {
                this.onDataRoamingOff();
                return;
            }
            case 270347: 
            case 270384: {
                this.onDataRoamingOnOrSettingsChanged(((Message)object).what);
                return;
            }
            case 270345: {
                this.onDataConnectionDetached();
                return;
            }
            case 270344: {
                this.onVoiceCallEnded();
                return;
            }
            case 270343: {
                this.onVoiceCallStarted();
                return;
            }
            case 270342: {
                this.onRadioOffOrNotAvailable();
                return;
            }
            case 270339: {
                this.trySetupData((ApnContext)((Message)object).obj, 1);
                return;
            }
            case 270338: {
                n2 = this.mPhone.getSubId();
                if (SubscriptionManager.isValidSubscriptionId((int)n2)) {
                    this.onRecordsLoadedOrSubIdChanged();
                    return;
                }
                object = new StringBuilder();
                ((StringBuilder)object).append("Ignoring EVENT_RECORDS_LOADED as subId is not valid: ");
                ((StringBuilder)object).append(n2);
                this.log(((StringBuilder)object).toString());
                return;
            }
            case 270337: {
                this.onRadioAvailable();
                return;
            }
            case 270336: 
        }
        AsyncResult asyncResult = (AsyncResult)((Message)object).obj;
        Pair pair = (Pair)asyncResult.userObj;
        ApnContext apnContext = (ApnContext)pair.first;
        n2 = (Integer)pair.second;
        n = ((Message)object).arg2;
        if (apnContext.getConnectionGeneration() == n2) {
            boolean bl4 = true;
            n2 = 65536;
            if (asyncResult.exception != null) {
                bl4 = false;
                n2 = (Integer)asyncResult.result;
            }
            this.onDataSetupComplete(apnContext, bl4, n2, n);
            return;
        }
        this.loge("EVENT_DATA_SETUP_COMPLETE: Dropped the event because generation did not match.");
    }

    public boolean hasMatchedTetherApnSetting() {
        ArrayList<ApnSetting> arrayList = this.fetchDunApns();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("hasMatchedTetherApnSetting: APNs=");
        stringBuilder.append(arrayList);
        this.log(stringBuilder.toString());
        boolean bl = arrayList.size() > 0;
        return bl;
    }

    public boolean isDataAllowed(ApnContext apnContext, int n, DataConnectionReasons dataConnectionReasons) {
        DataConnectionReasons dataConnectionReasons2 = new DataConnectionReasons();
        boolean bl = this.mDataEnabledSettings.isInternalDataEnabled();
        boolean bl2 = this.mAttached.get();
        boolean bl3 = this.mPhone.getServiceStateTracker().getDesiredPowerState();
        boolean bl4 = this.mPhone.getServiceStateTracker().getPowerStateFromCarrier();
        int n2 = this.getDataRat();
        if (n2 == 18) {
            bl3 = true;
            bl4 = true;
        }
        boolean bl5 = this.mIccRecords.get() != null && this.mIccRecords.get().getRecordsLoaded();
        boolean bl6 = SubscriptionManager.isValidSubscriptionId((int)SubscriptionManager.getDefaultDataSubscriptionId());
        boolean bl7 = apnContext == null || ApnSettingUtils.isMeteredApnType(ApnSetting.getApnTypesBitmaskFromString((String)apnContext.getApnType()), this.mPhone);
        PhoneConstants.State state = PhoneConstants.State.IDLE;
        if (this.mPhone.getCallTracker() != null) {
            state = this.mPhone.getCallTracker().getState();
        }
        if (apnContext != null && apnContext.getApnType().equals("emergency") && apnContext.isConnectable()) {
            if (dataConnectionReasons != null) {
                dataConnectionReasons.add(DataConnectionReasons.DataAllowedReasonType.EMERGENCY_APN);
            }
            return true;
        }
        if (apnContext != null && !apnContext.isConnectable()) {
            dataConnectionReasons2.add(DataConnectionReasons.DataDisallowedReasonType.APN_NOT_CONNECTABLE);
        }
        if (apnContext != null && (apnContext.getApnType().equals("default") || apnContext.getApnType().equals("ia")) && this.mPhone.getTransportManager().isInLegacyMode() && n2 == 18) {
            dataConnectionReasons2.add(DataConnectionReasons.DataDisallowedReasonType.ON_IWLAN);
        }
        if (this.isEmergency()) {
            dataConnectionReasons2.add(DataConnectionReasons.DataDisallowedReasonType.IN_ECBM);
        }
        if (!bl2 && !this.shouldAutoAttach() && n != 2) {
            dataConnectionReasons2.add(DataConnectionReasons.DataDisallowedReasonType.NOT_ATTACHED);
        }
        if (!bl5) {
            dataConnectionReasons2.add(DataConnectionReasons.DataDisallowedReasonType.RECORD_NOT_LOADED);
        }
        if (state != PhoneConstants.State.IDLE && !this.mPhone.getServiceStateTracker().isConcurrentVoiceAndDataAllowed()) {
            dataConnectionReasons2.add(DataConnectionReasons.DataDisallowedReasonType.INVALID_PHONE_STATE);
            dataConnectionReasons2.add(DataConnectionReasons.DataDisallowedReasonType.CONCURRENT_VOICE_DATA_NOT_ALLOWED);
        }
        if (!bl) {
            dataConnectionReasons2.add(DataConnectionReasons.DataDisallowedReasonType.INTERNAL_DATA_DISABLED);
        }
        if (!bl6) {
            dataConnectionReasons2.add(DataConnectionReasons.DataDisallowedReasonType.DEFAULT_DATA_UNSELECTED);
        }
        if (this.mPhone.getServiceState().getDataRoaming() && !this.getDataRoamingEnabled()) {
            dataConnectionReasons2.add(DataConnectionReasons.DataDisallowedReasonType.ROAMING_DISABLED);
        }
        if (this.mIsPsRestricted) {
            dataConnectionReasons2.add(DataConnectionReasons.DataDisallowedReasonType.PS_RESTRICTED);
        }
        if (!bl3) {
            dataConnectionReasons2.add(DataConnectionReasons.DataDisallowedReasonType.UNDESIRED_POWER_STATE);
        }
        if (!bl4) {
            dataConnectionReasons2.add(DataConnectionReasons.DataDisallowedReasonType.RADIO_DISABLED_BY_CARRIER);
        }
        if (!(bl4 = apnContext == null ? this.mDataEnabledSettings.isDataEnabled() : this.mDataEnabledSettings.isDataEnabled(apnContext.getApnTypeBitmask()))) {
            dataConnectionReasons2.add(DataConnectionReasons.DataDisallowedReasonType.DATA_DISABLED);
        }
        if (dataConnectionReasons2.containsHardDisallowedReasons()) {
            if (dataConnectionReasons != null) {
                dataConnectionReasons.copyFrom(dataConnectionReasons2);
            }
            return false;
        }
        if (!dataConnectionReasons2.allowed()) {
            if (!(this.mTransportType == 2 || this.mPhone.getTransportManager().isInLegacyMode() && n2 == 18)) {
                if (this.mTransportType == 1 && !bl7) {
                    dataConnectionReasons2.add(DataConnectionReasons.DataAllowedReasonType.UNMETERED_APN);
                }
            } else {
                dataConnectionReasons2.add(DataConnectionReasons.DataAllowedReasonType.UNMETERED_APN);
            }
            if (apnContext != null && apnContext.hasRestrictedRequests(true) && !dataConnectionReasons2.allowed()) {
                dataConnectionReasons2.add(DataConnectionReasons.DataAllowedReasonType.RESTRICTED_REQUEST);
            }
        } else {
            dataConnectionReasons2.add(DataConnectionReasons.DataAllowedReasonType.NORMAL);
        }
        if (dataConnectionReasons != null) {
            dataConnectionReasons.copyFrom(dataConnectionReasons2);
        }
        return dataConnectionReasons2.allowed();
    }

    public boolean isDataAllowed(DataConnectionReasons dataConnectionReasons) {
        return this.isDataAllowed(null, 1, dataConnectionReasons);
    }

    public boolean isDisconnected() {
        Iterator<ApnContext> iterator = this.mApnContexts.values().iterator();
        while (iterator.hasNext()) {
            if (iterator.next().isDisconnected()) continue;
            return false;
        }
        return true;
    }

    boolean isEmergency() {
        boolean bl = this.mPhone.isInEcm() || this.mPhone.isInEmergencyCall();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("isEmergency: result=");
        stringBuilder.append(bl);
        this.log(stringBuilder.toString());
        return bl;
    }

    boolean isPermanentFailure(int n) {
        boolean bl = DataFailCause.isPermanentFailure((Context)this.mPhone.getContext(), (int)n, (int)this.mPhone.getSubId()) && (!this.mAttached.get() || n != -3);
        return bl;
    }

    public void registerForAllDataDisconnected(Handler handler, int n) {
        this.mAllDataDisconnectedRegistrants.addUnique(handler, n, null);
        if (this.isDisconnected()) {
            this.log("notify All Data Disconnected");
            this.notifyAllDataDisconnected();
        }
    }

    public void registerServiceStateTrackerEvents() {
        this.mPhone.getServiceStateTracker().registerForDataConnectionAttached(this.mTransportType, this, 270352, null);
        this.mPhone.getServiceStateTracker().registerForDataConnectionDetached(this.mTransportType, this, 270345, null);
        this.mPhone.getServiceStateTracker().registerForDataRoamingOn(this, 270347, null);
        this.mPhone.getServiceStateTracker().registerForDataRoamingOff(this, 270348, null, true);
        this.mPhone.getServiceStateTracker().registerForPsRestrictedEnabled(this, 270358, null);
        this.mPhone.getServiceStateTracker().registerForPsRestrictedDisabled(this, 270359, null);
        this.mPhone.getServiceStateTracker().registerForDataRegStateOrRatChanged(this.mTransportType, this, 270377, null);
    }

    public void releaseNetwork(NetworkRequest networkRequest, int n) {
        int n2 = ApnContext.getApnTypeFromNetworkRequest(networkRequest);
        ApnContext apnContext = (ApnContext)this.mApnContextsByType.get(n2);
        if (apnContext != null) {
            apnContext.releaseNetwork(networkRequest, n);
        }
    }

    public void requestNetwork(NetworkRequest networkRequest, int n, Message message) {
        int n2 = ApnContext.getApnTypeFromNetworkRequest(networkRequest);
        ApnContext apnContext = (ApnContext)this.mApnContextsByType.get(n2);
        if (apnContext != null) {
            apnContext.requestNetwork(networkRequest, n, message);
        }
    }

    void sendRestartRadio() {
        this.log("sendRestartRadio:");
        this.sendMessage(this.obtainMessage(270362));
    }

    public void sendStartNetStatPoll(DctConstants.Activity activity) {
        Message message = this.obtainMessage(270376);
        message.arg1 = 1;
        message.obj = activity;
        this.sendMessage(message);
    }

    public void sendStopNetStatPoll(DctConstants.Activity activity) {
        Message message = this.obtainMessage(270376);
        message.arg1 = 0;
        message.obj = activity;
        this.sendMessage(message);
    }

    public void setDataRoamingEnabledByUser(boolean bl) {
        this.mDataEnabledSettings.setDataRoamingEnabled(bl);
        this.setDataRoamingFromUserAction(true);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("setDataRoamingEnabledByUser: set phoneSubId=");
        stringBuilder.append(this.mPhone.getSubId());
        stringBuilder.append(" isRoaming=");
        stringBuilder.append(bl);
        this.log(stringBuilder.toString());
    }

    @VisibleForTesting
    public boolean shouldAutoAttach() {
        boolean bl = this.mAutoAttachEnabled.get();
        boolean bl2 = true;
        if (bl) {
            return true;
        }
        PhoneSwitcher phoneSwitcher = PhoneSwitcher.getInstance();
        ServiceState serviceState = this.mPhone.getServiceState();
        if (phoneSwitcher == null || serviceState == null || this.mPhone.getPhoneId() == phoneSwitcher.getPreferredDataPhoneId() || serviceState.getVoiceRegState() != 0 || serviceState.getVoiceNetworkType() == 13 || serviceState.getVoiceNetworkType() == 20) {
            bl2 = false;
        }
        return bl2;
    }

    boolean shouldCleanUpConnection(ApnContext apnContext, boolean bl) {
        block5 : {
            block7 : {
                block6 : {
                    boolean bl2 = false;
                    if (apnContext == null) {
                        return false;
                    }
                    if (!bl) {
                        return true;
                    }
                    if ((apnContext = apnContext.getApnSetting()) == null || !ApnSettingUtils.isMetered((ApnSetting)apnContext, this.mPhone)) break block5;
                    boolean bl3 = this.mPhone.getServiceState().getDataRoaming();
                    boolean bl4 = this.getDataRoamingEnabled();
                    if (this.mDataEnabledSettings.isDataEnabled(apnContext.getApnTypeBitmask()) ^ true) break block6;
                    bl = bl2;
                    if (!bl3) break block7;
                    bl = bl2;
                    if (!(bl4 ^ true)) break block7;
                }
                bl = true;
            }
            return bl;
        }
        return false;
    }

    @VisibleForTesting
    public ArrayList<ApnSetting> sortApnListByPreferred(ArrayList<ApnSetting> arrayList) {
        if (arrayList != null && arrayList.size() > 1) {
            final int n = this.getPreferredApnSetId();
            if (n != 0) {
                arrayList.sort(new Comparator<ApnSetting>(){

                    @Override
                    public int compare(ApnSetting apnSetting, ApnSetting apnSetting2) {
                        if (apnSetting.getApnSetId() == n) {
                            return -1;
                        }
                        return apnSetting2.getApnSetId() == n;
                    }
                });
            }
            return arrayList;
        }
        return arrayList;
    }

    public void unregisterForAllDataDisconnected(Handler handler) {
        this.mAllDataDisconnectedRegistrants.remove(handler);
    }

    public void unregisterServiceStateTrackerEvents() {
        this.mPhone.getServiceStateTracker().unregisterForDataConnectionAttached(this.mTransportType, this);
        this.mPhone.getServiceStateTracker().unregisterForDataConnectionDetached(this.mTransportType, this);
        this.mPhone.getServiceStateTracker().unregisterForDataRoamingOn(this);
        this.mPhone.getServiceStateTracker().unregisterForDataRoamingOff(this);
        this.mPhone.getServiceStateTracker().unregisterForPsRestrictedEnabled(this);
        this.mPhone.getServiceStateTracker().unregisterForPsRestrictedDisabled(this);
        this.mPhone.getServiceStateTracker().unregisterForDataRegStateOrRatChanged(this.mTransportType, this);
    }

    public void update() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("update sub = ");
        stringBuilder.append(this.mPhone.getSubId());
        this.log(stringBuilder.toString());
        this.log("update(): Active DDS, register for all events now!");
        this.onUpdateIcc();
        this.mAutoAttachEnabled.set(false);
        this.mPhone.updateCurrentCarrierInProvider();
    }

    private class ApnChangeObserver
    extends ContentObserver {
        public ApnChangeObserver() {
            super(DcTracker.this.mDataConnectionTracker);
        }

        public void onChange(boolean bl) {
            DcTracker dcTracker = DcTracker.this;
            dcTracker.sendMessage(dcTracker.obtainMessage(270355));
        }
    }

    private class DataStallRecoveryHandler {
        private static final int DEFAULT_MIN_DURATION_BETWEEN_RECOVERY_STEPS_IN_MS = 180000;
        private boolean mIsValidNetwork;
        private long mTimeLastRecoveryStartMs;

        public DataStallRecoveryHandler() {
            this.reset();
        }

        private void broadcastDataStallDetected(int n) {
            Intent intent = new Intent("android.intent.action.DATA_STALL_DETECTED");
            SubscriptionManager.putPhoneIdAndSubIdExtra((Intent)intent, (int)DcTracker.this.mPhone.getPhoneId());
            intent.putExtra("recoveryAction", n);
            DcTracker.this.mPhone.getContext().sendBroadcast(intent, "android.permission.READ_PRIVILEGED_PHONE_STATE");
        }

        private boolean checkRecovery() {
            long l = this.getElapsedTimeSinceRecoveryMs();
            long l2 = this.getMinDurationBetweenRecovery();
            boolean bl = false;
            if (l < l2) {
                return false;
            }
            boolean bl2 = bl;
            if (DcTracker.this.mAttached.get()) {
                bl2 = bl;
                if (DcTracker.this.isDataAllowed(null)) {
                    bl2 = true;
                }
            }
            return bl2;
        }

        private long getElapsedTimeSinceRecoveryMs() {
            return SystemClock.elapsedRealtime() - this.mTimeLastRecoveryStartMs;
        }

        private long getMinDurationBetweenRecovery() {
            return Settings.Global.getLong((ContentResolver)DcTracker.this.mResolver, (String)"min_duration_between_recovery_steps", (long)180000L);
        }

        private int getRecoveryAction() {
            return Settings.System.getInt((ContentResolver)DcTracker.this.mResolver, (String)"radio.data.stall.recovery.action", (int)0);
        }

        private boolean isRecoveryAlreadyStarted() {
            boolean bl = this.getRecoveryAction() != 0;
            return bl;
        }

        private void putRecoveryAction(int n) {
            Settings.System.putInt((ContentResolver)DcTracker.this.mResolver, (String)"radio.data.stall.recovery.action", (int)n);
        }

        private void triggerRecovery() {
            DcTracker dcTracker = DcTracker.this;
            dcTracker.sendMessage(dcTracker.obtainMessage(270354));
        }

        /*
         * Enabled aggressive block sorting
         */
        public void doRecovery() {
            if (DcTracker.this.getOverallState() == DctConstants.State.CONNECTED) {
                int n = this.getRecoveryAction();
                TelephonyMetrics.getInstance().writeDataStallEvent(DcTracker.this.mPhone.getPhoneId(), n);
                this.broadcastDataStallDetected(n);
                if (n != 0) {
                    if (n != 1) {
                        if (n != 2) {
                            if (n != 3) {
                                StringBuilder stringBuilder = new StringBuilder();
                                stringBuilder.append("doRecovery: Invalid recoveryAction=");
                                stringBuilder.append(n);
                                throw new RuntimeException(stringBuilder.toString());
                            }
                            EventLog.writeEvent((int)50121, (long)DcTracker.this.mSentSinceLastRecv);
                            DcTracker.this.log("restarting radio");
                            DcTracker.this.restartRadio();
                            this.reset();
                        } else {
                            EventLog.writeEvent((int)50120, (long)DcTracker.this.mSentSinceLastRecv);
                            DcTracker.this.log("doRecovery() re-register");
                            DcTracker.this.mPhone.getServiceStateTracker().reRegisterNetwork(null);
                            this.putRecoveryAction(3);
                        }
                    } else {
                        EventLog.writeEvent((int)50119, (long)DcTracker.this.mSentSinceLastRecv);
                        DcTracker.this.log("doRecovery() cleanup all connections");
                        DcTracker dcTracker = DcTracker.this;
                        dcTracker.cleanUpConnection((ApnContext)dcTracker.mApnContexts.get(ApnSetting.getApnTypeString((int)17)));
                        this.putRecoveryAction(2);
                    }
                } else {
                    EventLog.writeEvent((int)50118, (long)DcTracker.this.mSentSinceLastRecv);
                    DcTracker.this.log("doRecovery() get data call list");
                    DcTracker.this.mDataServiceManager.requestDataCallList(DcTracker.this.obtainMessage());
                    this.putRecoveryAction(1);
                }
                DcTracker.this.mSentSinceLastRecv = 0L;
                this.mTimeLastRecoveryStartMs = SystemClock.elapsedRealtime();
            }
        }

        public boolean isAggressiveRecovery() {
            boolean bl;
            int n = this.getRecoveryAction();
            boolean bl2 = bl = true;
            if (n != 1) {
                bl2 = bl;
                if (n != 2) {
                    bl2 = n == 3 ? bl : false;
                }
            }
            return bl2;
        }

        public boolean isNoRxDataStallDetectionEnabled() {
            boolean bl = DcTracker.this.mDataStallNoRxEnabled && !this.isRecoveryOnBadNetworkEnabled();
            return bl;
        }

        public boolean isRecoveryOnBadNetworkEnabled() {
            ContentResolver contentResolver = DcTracker.this.mResolver;
            boolean bl = true;
            if (Settings.Global.getInt((ContentResolver)contentResolver, (String)"data_stall_recovery_on_bad_network", (int)1) != 1) {
                bl = false;
            }
            return bl;
        }

        public void processNetworkStatusChanged(boolean bl) {
            if (bl) {
                this.mIsValidNetwork = true;
                this.reset();
            } else if (this.mIsValidNetwork || this.isRecoveryAlreadyStarted()) {
                this.mIsValidNetwork = false;
                if (this.checkRecovery()) {
                    DcTracker.this.log("trigger data stall recovery");
                    this.triggerRecovery();
                }
            }
        }

        public void reset() {
            this.mTimeLastRecoveryStartMs = 0L;
            this.putRecoveryAction(0);
        }
    }

    private class DctOnSubscriptionsChangedListener
    extends SubscriptionManager.OnSubscriptionsChangedListener {
        public final AtomicInteger mPreviousSubId = new AtomicInteger(-1);

        private DctOnSubscriptionsChangedListener() {
        }

        public void onSubscriptionsChanged() {
            DcTracker.this.log("SubscriptionListener.onSubscriptionInfoChanged");
            int n = DcTracker.this.mPhone.getSubId();
            if (SubscriptionManager.isValidSubscriptionId((int)n)) {
                DcTracker.this.registerSettingsObserver();
            }
            if (SubscriptionManager.isValidSubscriptionId((int)n) && this.mPreviousSubId.getAndSet(n) != n) {
                DcTracker.this.onRecordsLoadedOrSubIdChanged();
            }
        }
    }

    private class ProvisionNotificationBroadcastReceiver
    extends BroadcastReceiver {
        private final String mNetworkOperator;
        private final String mProvisionUrl;

        public ProvisionNotificationBroadcastReceiver(String string, String string2) {
            this.mNetworkOperator = string2;
            this.mProvisionUrl = string;
        }

        private void enableMobileProvisioning() {
            Message message = DcTracker.this.obtainMessage(270373);
            message.setData(Bundle.forPair((String)"provisioningUrl", (String)this.mProvisionUrl));
            DcTracker.this.sendMessage(message);
        }

        private void setEnableFailFastMobileData(int n) {
            DcTracker dcTracker = DcTracker.this;
            dcTracker.sendMessage(dcTracker.obtainMessage(270372, n, 0));
        }

        public void onReceive(Context object, Intent intent) {
            DcTracker.this.log("onReceive : ProvisionNotificationBroadcastReceiver");
            DcTracker.this.mProvisioningSpinner = new ProgressDialog(object);
            DcTracker.this.mProvisioningSpinner.setTitle((CharSequence)this.mNetworkOperator);
            DcTracker.this.mProvisioningSpinner.setMessage(object.getText(17040314));
            DcTracker.this.mProvisioningSpinner.setIndeterminate(true);
            DcTracker.this.mProvisioningSpinner.setCancelable(true);
            DcTracker.this.mProvisioningSpinner.getWindow().setType(2009);
            DcTracker.this.mProvisioningSpinner.show();
            object = DcTracker.this;
            object.sendMessageDelayed(object.obtainMessage(270378, (Object)((DcTracker)((Object)object)).mProvisioningSpinner), 120000L);
            DcTracker.this.setRadio(true);
            this.setEnableFailFastMobileData(1);
            this.enableMobileProvisioning();
        }
    }

    @Retention(value=RetentionPolicy.SOURCE)
    private static @interface RecoveryAction {
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface ReleaseNetworkType {
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface RequestNetworkType {
    }

    private static enum RetryFailures {
        ALWAYS,
        ONLY_ON_CHANGE;
        
    }

    public static class TxRxSum {
        public long rxPkts;
        public long txPkts;

        public TxRxSum() {
            this.reset();
        }

        public TxRxSum(long l, long l2) {
            this.txPkts = l;
            this.rxPkts = l2;
        }

        public TxRxSum(TxRxSum txRxSum) {
            this.txPkts = txRxSum.txPkts;
            this.rxPkts = txRxSum.rxPkts;
        }

        public void reset() {
            this.txPkts = -1L;
            this.rxPkts = -1L;
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("{txSum=");
            stringBuilder.append(this.txPkts);
            stringBuilder.append(" rxSum=");
            stringBuilder.append(this.rxPkts);
            stringBuilder.append("}");
            return stringBuilder.toString();
        }

        public void updateTcpTxRxSum() {
            this.txPkts = TrafficStats.getMobileTcpTxPackets();
            this.rxPkts = TrafficStats.getMobileTcpRxPackets();
        }

        public void updateTotalTxRxSum() {
            this.txPkts = TrafficStats.getMobileTxPackets();
            this.rxPkts = TrafficStats.getMobileRxPackets();
        }
    }

}

