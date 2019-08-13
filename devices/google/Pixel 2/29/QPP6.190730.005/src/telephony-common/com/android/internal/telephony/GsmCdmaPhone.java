/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.annotation.UnsupportedAppUsage
 *  android.app.ActivityManager
 *  android.content.BroadcastReceiver
 *  android.content.ContentResolver
 *  android.content.ContentValues
 *  android.content.Context
 *  android.content.Intent
 *  android.content.IntentFilter
 *  android.content.SharedPreferences
 *  android.content.SharedPreferences$Editor
 *  android.content.res.Resources
 *  android.database.SQLException
 *  android.net.Uri
 *  android.os.AsyncResult
 *  android.os.Bundle
 *  android.os.Handler
 *  android.os.Message
 *  android.os.Parcelable
 *  android.os.PersistableBundle
 *  android.os.PowerManager
 *  android.os.PowerManager$WakeLock
 *  android.os.Registrant
 *  android.os.RegistrantList
 *  android.os.ResultReceiver
 *  android.os.SystemProperties
 *  android.os.WorkSource
 *  android.preference.PreferenceManager
 *  android.provider.Settings
 *  android.provider.Settings$Global
 *  android.provider.Settings$Secure
 *  android.provider.Telephony
 *  android.provider.Telephony$Carriers
 *  android.telecom.TelecomManager
 *  android.telecom.VideoProfile
 *  android.telephony.CarrierConfigManager
 *  android.telephony.CellLocation
 *  android.telephony.ImsiEncryptionInfo
 *  android.telephony.NetworkScanRequest
 *  android.telephony.PhoneNumberUtils
 *  android.telephony.Rlog
 *  android.telephony.ServiceState
 *  android.telephony.SubscriptionInfo
 *  android.telephony.SubscriptionManager
 *  android.telephony.TelephonyManager
 *  android.telephony.UssdResponse
 *  android.telephony.data.ApnSetting
 *  android.text.TextUtils
 *  android.util.Log
 *  android.util.Pair
 *  android.util.SparseArray
 *  com.android.ims.ImsManager
 *  com.android.internal.annotations.VisibleForTesting
 *  com.android.internal.telephony.DctConstants
 *  com.android.internal.telephony.DctConstants$Activity
 *  com.android.internal.telephony.DctConstants$State
 *  com.android.internal.telephony.IccCardConstants
 *  com.android.internal.telephony.IccCardConstants$State
 *  com.android.internal.telephony.OperatorInfo
 *  com.android.internal.telephony.OperatorInfo$State
 *  com.android.internal.telephony.PhoneConstants
 *  com.android.internal.telephony.PhoneConstants$DataState
 *  com.android.internal.telephony.PhoneConstants$State
 *  com.android.internal.util.ArrayUtils
 */
package com.android.internal.telephony;

import android.annotation.UnsupportedAppUsage;
import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.database.SQLException;
import android.net.Uri;
import android.os.AsyncResult;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.os.PersistableBundle;
import android.os.PowerManager;
import android.os.Registrant;
import android.os.RegistrantList;
import android.os.ResultReceiver;
import android.os.SystemProperties;
import android.os.WorkSource;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.provider.Telephony;
import android.telecom.TelecomManager;
import android.telecom.VideoProfile;
import android.telephony.CarrierConfigManager;
import android.telephony.CellLocation;
import android.telephony.ImsiEncryptionInfo;
import android.telephony.NetworkScanRequest;
import android.telephony.PhoneNumberUtils;
import android.telephony.Rlog;
import android.telephony.ServiceState;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.telephony.UssdResponse;
import android.telephony.data.ApnSetting;
import android.text.TextUtils;
import android.util.Log;
import android.util.Pair;
import android.util.SparseArray;
import com.android.ims.ImsManager;
import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.telephony.Call;
import com.android.internal.telephony.CallForwardInfo;
import com.android.internal.telephony.CallStateException;
import com.android.internal.telephony.CallTracker;
import com.android.internal.telephony.CarrierActionAgent;
import com.android.internal.telephony.CarrierInfoManager;
import com.android.internal.telephony.CarrierKeyDownloadManager;
import com.android.internal.telephony.CarrierResolver;
import com.android.internal.telephony.CarrierSignalAgent;
import com.android.internal.telephony.CommandsInterface;
import com.android.internal.telephony.Connection;
import com.android.internal.telephony.DctConstants;
import com.android.internal.telephony.DeviceStateMonitor;
import com.android.internal.telephony.GsmCdmaCall;
import com.android.internal.telephony.GsmCdmaCallTracker;
import com.android.internal.telephony.GsmCdmaConnection;
import com.android.internal.telephony.IccCard;
import com.android.internal.telephony.IccCardConstants;
import com.android.internal.telephony.IccPhoneBookInterfaceManager;
import com.android.internal.telephony.IccSmsInterfaceManager;
import com.android.internal.telephony.MccTable;
import com.android.internal.telephony.MmiCode;
import com.android.internal.telephony.OperatorInfo;
import com.android.internal.telephony.Phone;
import com.android.internal.telephony.PhoneConstants;
import com.android.internal.telephony.PhoneInternalInterface;
import com.android.internal.telephony.PhoneNotifier;
import com.android.internal.telephony.RadioCapability;
import com.android.internal.telephony.ServiceStateTracker;
import com.android.internal.telephony.SettingsObserver;
import com.android.internal.telephony.SubscriptionController;
import com.android.internal.telephony.TelephonyComponentFactory;
import com.android.internal.telephony.UUSInfo;
import com.android.internal.telephony.cdma.CdmaMmiCode;
import com.android.internal.telephony.cdma.CdmaSubscriptionSourceManager;
import com.android.internal.telephony.dataconnection.DataEnabledSettings;
import com.android.internal.telephony.dataconnection.DcTracker;
import com.android.internal.telephony.dataconnection.TransportManager;
import com.android.internal.telephony.emergency.EmergencyNumberTracker;
import com.android.internal.telephony.gsm.GsmMmiCode;
import com.android.internal.telephony.gsm.SuppServiceNotification;
import com.android.internal.telephony.test.SimulatedRadioControl;
import com.android.internal.telephony.uicc.IccCardApplicationStatus;
import com.android.internal.telephony.uicc.IccException;
import com.android.internal.telephony.uicc.IccRecords;
import com.android.internal.telephony.uicc.IccVmNotSupportedException;
import com.android.internal.telephony.uicc.IsimRecords;
import com.android.internal.telephony.uicc.IsimUiccRecords;
import com.android.internal.telephony.uicc.RuimRecords;
import com.android.internal.telephony.uicc.SIMRecords;
import com.android.internal.telephony.uicc.UiccCard;
import com.android.internal.telephony.uicc.UiccCardApplication;
import com.android.internal.telephony.uicc.UiccController;
import com.android.internal.telephony.uicc.UiccProfile;
import com.android.internal.telephony.uicc.UiccSlot;
import com.android.internal.util.ArrayUtils;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GsmCdmaPhone
extends Phone {
    public static final int CANCEL_ECM_TIMER = 1;
    private static final boolean DBG = true;
    private static final int DEFAULT_ECM_EXIT_TIMER_VALUE = 300000;
    private static final int INVALID_SYSTEM_SELECTION_CODE = -1;
    private static final String IS683A_FEATURE_CODE = "*228";
    private static final int IS683A_FEATURE_CODE_NUM_DIGITS = 4;
    private static final int IS683A_SYS_SEL_CODE_NUM_DIGITS = 2;
    private static final int IS683A_SYS_SEL_CODE_OFFSET = 4;
    private static final int IS683_CONST_1900MHZ_A_BLOCK = 2;
    private static final int IS683_CONST_1900MHZ_B_BLOCK = 3;
    private static final int IS683_CONST_1900MHZ_C_BLOCK = 4;
    private static final int IS683_CONST_1900MHZ_D_BLOCK = 5;
    private static final int IS683_CONST_1900MHZ_E_BLOCK = 6;
    private static final int IS683_CONST_1900MHZ_F_BLOCK = 7;
    private static final int IS683_CONST_800MHZ_A_BAND = 0;
    private static final int IS683_CONST_800MHZ_B_BAND = 1;
    public static final String LOG_TAG = "GsmCdmaPhone";
    private static final String PREFIX_WPS = "*272";
    public static final String PROPERTY_CDMA_HOME_OPERATOR_NUMERIC = "ro.cdma.home.operator.numeric";
    private static final int REPORTING_HYSTERESIS_DB = 2;
    private static final int REPORTING_HYSTERESIS_KBPS = 50;
    private static final int REPORTING_HYSTERESIS_MILLIS = 3000;
    public static final int RESTART_ECM_TIMER = 0;
    private static final boolean VDBG = false;
    private static final String VM_NUMBER = "vm_number_key";
    private static final String VM_NUMBER_CDMA = "vm_number_key_cdma";
    private static final String VM_SIM_IMSI = "vm_sim_imsi_key";
    private static final int[] VOICE_PS_CALL_RADIO_TECHNOLOGY;
    private static Pattern pOtaSpNumSchema;
    private boolean mBroadcastEmergencyCallStateChanges;
    private BroadcastReceiver mBroadcastReceiver;
    private CarrierKeyDownloadManager mCDM;
    private CarrierInfoManager mCIM;
    @UnsupportedAppUsage
    public GsmCdmaCallTracker mCT;
    private String mCarrierOtaSpNumSchema;
    private CdmaSubscriptionSourceManager mCdmaSSM;
    public int mCdmaSubscriptionSource;
    @UnsupportedAppUsage
    private Registrant mEcmExitRespRegistrant;
    private final RegistrantList mEcmTimerResetRegistrants;
    public EmergencyNumberTracker mEmergencyNumberTracker;
    private String mEsn;
    private Runnable mExitEcmRunnable;
    private IccPhoneBookInterfaceManager mIccPhoneBookIntManager;
    @UnsupportedAppUsage
    private IccSmsInterfaceManager mIccSmsInterfaceManager;
    private String mImei;
    private String mImeiSv;
    @UnsupportedAppUsage
    private IsimUiccRecords mIsimUiccRecords;
    private String mMeid;
    @UnsupportedAppUsage
    private ArrayList<MmiCode> mPendingMMIs;
    private int mPrecisePhoneType;
    private boolean mResetModemOnRadioTechnologyChange;
    private int mRilVersion;
    @UnsupportedAppUsage
    public ServiceStateTracker mSST;
    private final SettingsObserver mSettingsObserver;
    private SIMRecords mSimRecords;
    private RegistrantList mSsnRegistrants;
    private String mVmNumber;
    private PowerManager.WakeLock mWakeLock;

    static {
        pOtaSpNumSchema = Pattern.compile("[,\\s]+");
        VOICE_PS_CALL_RADIO_TECHNOLOGY = new int[]{14, 19, 18};
    }

    public GsmCdmaPhone(Context context, CommandsInterface commandsInterface, PhoneNotifier phoneNotifier, int n, int n2, TelephonyComponentFactory telephonyComponentFactory) {
        this(context, commandsInterface, phoneNotifier, false, n, n2, telephonyComponentFactory);
    }

    public GsmCdmaPhone(Context object, CommandsInterface arrn, PhoneNotifier phoneNotifier, boolean bl, int n, int n2, TelephonyComponentFactory telephonyComponentFactory) {
        String string = n2 == 1 ? "GSM" : "CDMA";
        super(string, phoneNotifier, (Context)object, (CommandsInterface)arrn, bl, n, telephonyComponentFactory);
        this.mSsnRegistrants = new RegistrantList();
        this.mCdmaSubscriptionSource = -1;
        this.mExitEcmRunnable = new Runnable(){

            @Override
            public void run() {
                GsmCdmaPhone.this.exitEmergencyCallbackMode();
            }
        };
        this.mPendingMMIs = new ArrayList();
        this.mEcmTimerResetRegistrants = new RegistrantList();
        this.mResetModemOnRadioTechnologyChange = false;
        this.mBroadcastEmergencyCallStateChanges = false;
        this.mBroadcastReceiver = new BroadcastReceiver(){

            public void onReceive(Context object, Intent intent) {
                block1 : {
                    block2 : {
                        block0 : {
                            object = new StringBuilder();
                            ((StringBuilder)object).append("mBroadcastReceiver: action ");
                            ((StringBuilder)object).append(intent.getAction());
                            Rlog.d((String)GsmCdmaPhone.LOG_TAG, (String)((StringBuilder)object).toString());
                            object = intent.getAction();
                            if (!"android.telephony.action.CARRIER_CONFIG_CHANGED".equals(object)) break block0;
                            if (GsmCdmaPhone.this.mPhoneId != intent.getIntExtra("android.telephony.extra.SLOT_INDEX", -1)) break block1;
                            object = GsmCdmaPhone.this;
                            object.sendMessage(object.obtainMessage(43));
                            break block1;
                        }
                        if (!"android.telecom.action.CURRENT_TTY_MODE_CHANGED".equals(object)) break block2;
                        int n = intent.getIntExtra("android.telecom.intent.extra.CURRENT_TTY_MODE", 0);
                        GsmCdmaPhone.this.updateTtyMode(n);
                        break block1;
                    }
                    if (!"android.telecom.action.TTY_PREFERRED_MODE_CHANGED".equals(object)) break block1;
                    int n = intent.getIntExtra("android.telecom.intent.extra.TTY_PREFERRED", 0);
                    GsmCdmaPhone.this.updateUiTtyMode(n);
                }
            }
        };
        this.mPrecisePhoneType = n2;
        this.initOnce((CommandsInterface)arrn);
        this.initRatSpecific(n2);
        this.mCarrierActionAgent = this.mTelephonyComponentFactory.inject(CarrierActionAgent.class.getName()).makeCarrierActionAgent(this);
        this.mCarrierSignalAgent = this.mTelephonyComponentFactory.inject(CarrierSignalAgent.class.getName()).makeCarrierSignalAgent(this);
        this.mTransportManager = this.mTelephonyComponentFactory.inject(TransportManager.class.getName()).makeTransportManager(this);
        this.mSST = this.mTelephonyComponentFactory.inject(ServiceStateTracker.class.getName()).makeServiceStateTracker(this, this.mCi);
        this.mEmergencyNumberTracker = this.mTelephonyComponentFactory.inject(EmergencyNumberTracker.class.getName()).makeEmergencyNumberTracker(this, this.mCi);
        this.mDataEnabledSettings = this.mTelephonyComponentFactory.inject(DataEnabledSettings.class.getName()).makeDataEnabledSettings(this);
        for (int n3 : this.mTransportManager.getAvailableTransports()) {
            this.mDcTrackers.put(n3, (Object)this.mTelephonyComponentFactory.inject(DcTracker.class.getName()).makeDcTracker(this, n3));
        }
        this.mCarrierResolver = this.mTelephonyComponentFactory.inject(CarrierResolver.class.getName()).makeCarrierResolver(this);
        this.getCarrierActionAgent().registerForCarrierAction(0, this, 48, null, false);
        this.mSST.registerForNetworkAttached(this, 19, null);
        this.mDeviceStateMonitor = this.mTelephonyComponentFactory.inject(DeviceStateMonitor.class.getName()).makeDeviceStateMonitor(this);
        this.mSST.registerForVoiceRegStateOrRatChanged(this, 46, null);
        this.mSettingsObserver = new SettingsObserver((Context)object, this);
        this.mSettingsObserver.observe(Settings.Global.getUriFor((String)"device_provisioned"), 49);
        this.mSettingsObserver.observe(Settings.Global.getUriFor((String)"device_provisioning_mobile_data"), 50);
        this.loadTtyMode();
        object = new StringBuilder();
        ((StringBuilder)object).append("GsmCdmaPhone: constructor: sub = ");
        ((StringBuilder)object).append(this.mPhoneId);
        this.logd(((StringBuilder)object).toString());
    }

    private static boolean checkOtaSpNumBasedOnSysSelCode(int n, String[] arrstring) {
        int n2;
        int n3;
        boolean bl;
        boolean bl2 = false;
        boolean bl3 = false;
        try {
            n3 = Integer.parseInt(arrstring[1]);
            n2 = 0;
        }
        catch (NumberFormatException numberFormatException) {
            Rlog.e((String)LOG_TAG, (String)"checkOtaSpNumBasedOnSysSelCode, error", (Throwable)numberFormatException);
            bl = bl2;
        }
        do {
            bl = bl3;
            if (n2 >= n3) break;
            if (!TextUtils.isEmpty((CharSequence)arrstring[n2 + 2]) && !TextUtils.isEmpty((CharSequence)arrstring[n2 + 3])) {
                int n4 = Integer.parseInt(arrstring[n2 + 2]);
                int n5 = Integer.parseInt(arrstring[n2 + 3]);
                if (n >= n4 && n <= n5) {
                    bl = true;
                    break;
                }
            }
            ++n2;
            continue;
            break;
        } while (true);
        return bl;
    }

    private static int extractSelCodeFromOtaSpNum(String charSequence) {
        int n;
        int n2 = ((String)charSequence).length();
        int n3 = n = -1;
        if (((String)charSequence).regionMatches(0, IS683A_FEATURE_CODE, 0, 4)) {
            n3 = n;
            if (n2 >= 6) {
                n3 = Integer.parseInt(((String)charSequence).substring(4, 6));
            }
        }
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append("extractSelCodeFromOtaSpNum ");
        ((StringBuilder)charSequence).append(n3);
        Rlog.d((String)LOG_TAG, (String)((StringBuilder)charSequence).toString());
        return n3;
    }

    private int getCsCallRadioTech(int n, int n2) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("getCsCallRadioTech, current vrs=");
        stringBuilder.append(n);
        stringBuilder.append(", vrat=");
        stringBuilder.append(n2);
        this.logd(stringBuilder.toString());
        int n3 = n2;
        if (n != 0 || ArrayUtils.contains((int[])VOICE_PS_CALL_RADIO_TECHNOLOGY, (int)n2)) {
            n3 = 0;
        }
        stringBuilder = new StringBuilder();
        stringBuilder.append("getCsCallRadioTech, result calcVrat=");
        stringBuilder.append(n3);
        this.logd(stringBuilder.toString());
        return n3;
    }

    private String getOperatorNumeric() {
        StringBuilder stringBuilder = null;
        Object object = null;
        if (this.isPhoneTypeGsm()) {
            IccRecords iccRecords = (IccRecords)this.mIccRecords.get();
            if (iccRecords != null) {
                object = iccRecords.getOperatorNumeric();
            }
        } else {
            Object object2 = null;
            int n = this.mCdmaSubscriptionSource;
            if (n == 1) {
                object = SystemProperties.get((String)PROPERTY_CDMA_HOME_OPERATOR_NUMERIC);
            } else {
                object = stringBuilder;
                if (n == 0) {
                    object = (UiccCardApplication)this.mUiccApplication.get();
                    if (object != null && ((UiccCardApplication)object).getType() == IccCardApplicationStatus.AppType.APPTYPE_RUIM) {
                        this.logd("Legacy RUIM app present");
                        object2 = (IccRecords)this.mIccRecords.get();
                    } else {
                        object2 = this.mSimRecords;
                    }
                    if (object2 != null && object2 == this.mSimRecords) {
                        object = ((IccRecords)object2).getOperatorNumeric();
                    } else {
                        IccRecords iccRecords = (IccRecords)this.mIccRecords.get();
                        object = stringBuilder;
                        object2 = iccRecords;
                        if (iccRecords != null) {
                            object = stringBuilder;
                            object2 = iccRecords;
                            if (iccRecords instanceof RuimRecords) {
                                object = ((RuimRecords)iccRecords).getRUIMOperatorNumeric();
                                object2 = iccRecords;
                            }
                        }
                    }
                }
            }
            if (object == null) {
                stringBuilder = new StringBuilder();
                stringBuilder.append("getOperatorNumeric: Cannot retrieve operatorNumeric: mCdmaSubscriptionSource = ");
                stringBuilder.append(this.mCdmaSubscriptionSource);
                stringBuilder.append(" mIccRecords = ");
                object2 = object2 != null ? Boolean.valueOf(((IccRecords)object2).getRecordsLoaded()) : null;
                stringBuilder.append(object2);
                this.loge(stringBuilder.toString());
            }
            object2 = new StringBuilder();
            ((StringBuilder)object2).append("getOperatorNumeric: mCdmaSubscriptionSource = ");
            ((StringBuilder)object2).append(this.mCdmaSubscriptionSource);
            ((StringBuilder)object2).append(" operatorNumeric = ");
            ((StringBuilder)object2).append((String)object);
            this.logd(((StringBuilder)object2).toString());
        }
        return object;
    }

    private UiccProfile getUiccProfile() {
        return UiccController.getInstance().getUiccProfileForPhone(this.mPhoneId);
    }

    private String getVmSimImsi() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences((Context)this.getContext());
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(VM_SIM_IMSI);
        stringBuilder.append(this.getPhoneId());
        return sharedPreferences.getString(stringBuilder.toString(), null);
    }

    private boolean handleCallDeflectionIncallSupplementaryService(String string) {
        if (string.length() > 1) {
            return false;
        }
        if (this.getRingingCall().getState() != Call.State.IDLE) {
            this.logd("MmiCode 0: rejectCall");
            try {
                this.mCT.rejectCall();
            }
            catch (CallStateException callStateException) {
                Rlog.d((String)LOG_TAG, (String)"reject failed", (Throwable)callStateException);
                this.notifySuppServiceFailed(PhoneInternalInterface.SuppService.REJECT);
            }
        } else if (this.getBackgroundCall().getState() != Call.State.IDLE) {
            this.logd("MmiCode 0: hangupWaitingOrBackground");
            this.mCT.hangupWaitingOrBackground();
        }
        return true;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private boolean handleCallHoldIncallSupplementaryService(String charSequence) {
        int n = ((String)charSequence).length();
        if (n > 2) {
            return false;
        }
        Object object = this.getForegroundCall();
        if (n > 1) {
            try {
                n = ((String)charSequence).charAt(1) - 48;
                object = this.mCT.getConnectionByIndex((GsmCdmaCall)object, n);
                if (object != null && n >= 1 && n <= 19) {
                    charSequence = new StringBuilder();
                    ((StringBuilder)charSequence).append("MmiCode 2: separate call ");
                    ((StringBuilder)charSequence).append(n);
                    this.logd(((StringBuilder)charSequence).toString());
                    this.mCT.separate((GsmCdmaConnection)object);
                    return true;
                }
                charSequence = new StringBuilder();
                ((StringBuilder)charSequence).append("separate: invalid call index ");
                ((StringBuilder)charSequence).append(n);
                this.logd(((StringBuilder)charSequence).toString());
                this.notifySuppServiceFailed(PhoneInternalInterface.SuppService.SEPARATE);
                return true;
            }
            catch (CallStateException callStateException) {
                Rlog.d((String)LOG_TAG, (String)"separate failed", (Throwable)callStateException);
                this.notifySuppServiceFailed(PhoneInternalInterface.SuppService.SEPARATE);
                return true;
            }
        }
        try {
            if (this.getRingingCall().getState() != Call.State.IDLE) {
                this.logd("MmiCode 2: accept ringing call");
                this.mCT.acceptCall();
                return true;
            }
            this.logd("MmiCode 2: switchWaitingOrHoldingAndActive");
            this.mCT.switchWaitingOrHoldingAndActive();
            return true;
        }
        catch (CallStateException callStateException) {
            Rlog.d((String)LOG_TAG, (String)"switch failed", (Throwable)callStateException);
            this.notifySuppServiceFailed(PhoneInternalInterface.SuppService.SWITCH);
        }
        return true;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private boolean handleCallWaitingIncallSupplementaryService(String var1_1) {
        var2_3 = var1_1.length();
        if (var2_3 > 2) {
            return false;
        }
        var3_4 = this.getForegroundCall();
        if (var2_3 <= 1) ** GOTO lbl18
        try {
            var2_3 = var1_1.charAt(1) - 48;
            if (var2_3 < 1) return true;
            if (var2_3 > 19) return true;
            var1_1 = new StringBuilder();
            var1_1.append("MmiCode 1: hangupConnectionByIndex ");
            var1_1.append(var2_3);
            this.logd(var1_1.toString());
            this.mCT.hangupConnectionByIndex((GsmCdmaCall)var3_4, var2_3);
            return true;
lbl18: // 1 sources:
            if (var3_4.getState() != Call.State.IDLE) {
                this.logd("MmiCode 1: hangup foreground");
                this.mCT.hangup((GsmCdmaCall)var3_4);
                return true;
            }
            this.logd("MmiCode 1: switchWaitingOrHoldingAndActive");
            this.mCT.switchWaitingOrHoldingAndActive();
            return true;
        }
        catch (CallStateException var1_2) {
            Rlog.d((String)"GsmCdmaPhone", (String)"hangup failed", (Throwable)var1_2);
            this.notifySuppServiceFailed(PhoneInternalInterface.SuppService.HANGUP);
        }
        return true;
    }

    private boolean handleCcbsIncallSupplementaryService(String string) {
        if (string.length() > 1) {
            return false;
        }
        Rlog.i((String)LOG_TAG, (String)"MmiCode 5: CCBS not supported!");
        this.notifySuppServiceFailed(PhoneInternalInterface.SuppService.UNKNOWN);
        return true;
    }

    private void handleCfuQueryResult(CallForwardInfo[] arrcallForwardInfo) {
        if ((IccRecords)this.mIccRecords.get() != null) {
            boolean bl = false;
            if (arrcallForwardInfo != null && arrcallForwardInfo.length != 0) {
                int n = arrcallForwardInfo.length;
                for (int i = 0; i < n; ++i) {
                    if ((arrcallForwardInfo[i].serviceClass & 1) == 0) continue;
                    if (arrcallForwardInfo[i].status == 1) {
                        bl = true;
                    }
                    this.setVoiceCallForwardingFlag(1, bl, arrcallForwardInfo[i].number);
                    break;
                }
            } else {
                this.setVoiceCallForwardingFlag(1, false, null);
            }
        }
    }

    private boolean handleEctIncallSupplementaryService(String string) {
        if (string.length() != 1) {
            return false;
        }
        this.logd("MmiCode 4: explicit call transfer");
        this.explicitCallTransfer();
        return true;
    }

    private void handleEnterEmergencyCallbackMode(Message object) {
        object = new StringBuilder();
        ((StringBuilder)object).append("handleEnterEmergencyCallbackMode, isInEcm()=");
        ((StringBuilder)object).append(this.isInEcm());
        Rlog.d((String)LOG_TAG, (String)((StringBuilder)object).toString());
        if (!this.isInEcm()) {
            this.setIsInEcm(true);
            this.sendEmergencyCallbackModeChange();
            long l = SystemProperties.getLong((String)"ro.cdma.ecmexittimer", (long)300000L);
            this.postDelayed(this.mExitEcmRunnable, l);
            this.mWakeLock.acquire();
        }
    }

    private void handleExitEmergencyCallbackMode(Message message) {
        message = (AsyncResult)message.obj;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("handleExitEmergencyCallbackMode,ar.exception , isInEcm=");
        stringBuilder.append(message.exception);
        stringBuilder.append(this.isInEcm());
        Rlog.d((String)LOG_TAG, (String)stringBuilder.toString());
        this.removeCallbacks(this.mExitEcmRunnable);
        stringBuilder = this.mEcmExitRespRegistrant;
        if (stringBuilder != null) {
            stringBuilder.notifyRegistrant((AsyncResult)message);
        }
        if (message.exception == null) {
            if (this.isInEcm()) {
                this.setIsInEcm(false);
            }
            if (this.mWakeLock.isHeld()) {
                this.mWakeLock.release();
            }
            this.sendEmergencyCallbackModeChange();
            this.mDataEnabledSettings.setInternalDataEnabled(true);
            this.notifyEmergencyCallRegistrants(false);
        }
    }

    private boolean handleMultipartyIncallSupplementaryService(String string) {
        if (string.length() > 1) {
            return false;
        }
        this.logd("MmiCode 3: merge calls");
        this.conference();
        return true;
    }

    private void handleRadioAvailable() {
        this.mCi.getBasebandVersion(this.obtainMessage(6));
        this.mCi.getDeviceIdentity(this.obtainMessage(21));
        this.mCi.getRadioCapability(this.obtainMessage(35));
        this.startLceAfterRadioIsAvailable();
    }

    private void handleRadioOffOrNotAvailable() {
        if (this.isPhoneTypeGsm()) {
            for (int i = this.mPendingMMIs.size() - 1; i >= 0; --i) {
                if (!((GsmMmiCode)this.mPendingMMIs.get(i)).isPendingUSSD()) continue;
                ((GsmMmiCode)this.mPendingMMIs.get(i)).onUssdFinishedError();
            }
        }
        this.mRadioOffOrNotAvailableRegistrants.notifyRegistrants();
    }

    private void handleRadioOn() {
        this.mCi.getVoiceRadioTechnology(this.obtainMessage(40));
        if (!this.isPhoneTypeGsm()) {
            this.mCdmaSubscriptionSource = this.mCdmaSSM.getCdmaSubscriptionSource();
        }
        this.setPreferredNetworkTypeIfSimLoaded();
    }

    private void handleRadioPowerStateChange() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("handleRadioPowerStateChange, state= ");
        stringBuilder.append(this.mCi.getRadioState());
        Rlog.d((String)LOG_TAG, (String)stringBuilder.toString());
        this.mNotifier.notifyRadioPowerStateChanged(this, this.mCi.getRadioState());
    }

    private void initOnce(CommandsInterface commandsInterface) {
        if (commandsInterface instanceof SimulatedRadioControl) {
            this.mSimulatedRadioControl = (SimulatedRadioControl)((Object)commandsInterface);
        }
        this.mCT = this.mTelephonyComponentFactory.inject(GsmCdmaCallTracker.class.getName()).makeGsmCdmaCallTracker(this);
        this.mIccPhoneBookIntManager = this.mTelephonyComponentFactory.inject(IccPhoneBookInterfaceManager.class.getName()).makeIccPhoneBookInterfaceManager(this);
        this.mWakeLock = ((PowerManager)this.mContext.getSystemService("power")).newWakeLock(1, LOG_TAG);
        this.mIccSmsInterfaceManager = this.mTelephonyComponentFactory.inject(IccSmsInterfaceManager.class.getName()).makeIccSmsInterfaceManager(this);
        this.mCi.registerForAvailable(this, 1, null);
        this.mCi.registerForOffOrNotAvailable(this, 8, null);
        this.mCi.registerForOn(this, 5, null);
        this.mCi.registerForRadioStateChanged(this, 47, null);
        this.mCi.setOnSuppServiceNotification(this, 2, null);
        this.mCi.setOnUSSD(this, 7, null);
        this.mCi.setOnSs(this, 36, null);
        this.mCdmaSSM = this.mTelephonyComponentFactory.inject(CdmaSubscriptionSourceManager.class.getName()).getCdmaSubscriptionSourceManagerInstance(this.mContext, this.mCi, this, 27, null);
        this.mCi.setEmergencyCallbackMode(this, 25, null);
        this.mCi.registerForExitEmergencyCallbackMode(this, 26, null);
        this.mCi.registerForModemReset(this, 45, null);
        this.mCarrierOtaSpNumSchema = TelephonyManager.from((Context)this.mContext).getOtaSpNumberSchemaForPhone(this.getPhoneId(), "");
        this.mResetModemOnRadioTechnologyChange = SystemProperties.getBoolean((String)"persist.radio.reset_on_switch", (boolean)false);
        this.mCi.registerForRilConnected(this, 41, null);
        this.mCi.registerForVoiceRadioTechChanged(this, 39, null);
        commandsInterface = new IntentFilter("android.telephony.action.CARRIER_CONFIG_CHANGED");
        commandsInterface.addAction("android.telecom.action.CURRENT_TTY_MODE_CHANGED");
        commandsInterface.addAction("android.telecom.action.TTY_PREFERRED_MODE_CHANGED");
        this.mContext.registerReceiver(this.mBroadcastReceiver, (IntentFilter)commandsInterface);
        this.mCDM = new CarrierKeyDownloadManager(this);
        this.mCIM = new CarrierInfoManager();
    }

    private void initRatSpecific(int n) {
        this.mPendingMMIs.clear();
        this.mIccPhoneBookIntManager.updateIccRecords(null);
        this.mEsn = null;
        this.mMeid = null;
        this.mPrecisePhoneType = n;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Precise phone type ");
        stringBuilder.append(this.mPrecisePhoneType);
        this.logd(stringBuilder.toString());
        stringBuilder = TelephonyManager.from((Context)this.mContext);
        Object object = this.getUiccProfile();
        if (this.isPhoneTypeGsm()) {
            this.mCi.setPhoneType(1);
            stringBuilder.setPhoneType(this.getPhoneId(), 1);
            if (object != null) {
                ((UiccProfile)object).setVoiceRadioTech(3);
            }
        } else {
            this.mCdmaSubscriptionSource = this.mCdmaSSM.getCdmaSubscriptionSource();
            this.mIsPhoneInEcmState = GsmCdmaPhone.getInEcmMode();
            if (this.mIsPhoneInEcmState) {
                this.mCi.exitEmergencyCallbackMode(this.obtainMessage(26));
            }
            this.mCi.setPhoneType(2);
            stringBuilder.setPhoneType(this.getPhoneId(), 2);
            if (object != null) {
                ((UiccProfile)object).setVoiceRadioTech(6);
            }
            CharSequence charSequence = SystemProperties.get((String)"ro.cdma.home.operator.alpha");
            object = SystemProperties.get((String)PROPERTY_CDMA_HOME_OPERATOR_NUMERIC);
            StringBuilder stringBuilder2 = new StringBuilder();
            stringBuilder2.append("init: operatorAlpha='");
            stringBuilder2.append((String)charSequence);
            stringBuilder2.append("' operatorNumeric='");
            stringBuilder2.append((String)object);
            stringBuilder2.append("'");
            this.logd(stringBuilder2.toString());
            if (!TextUtils.isEmpty((CharSequence)charSequence)) {
                stringBuilder2 = new StringBuilder();
                stringBuilder2.append("init: set 'gsm.sim.operator.alpha' to operator='");
                stringBuilder2.append((String)charSequence);
                stringBuilder2.append("'");
                this.logd(stringBuilder2.toString());
                stringBuilder.setSimOperatorNameForPhone(this.mPhoneId, (String)charSequence);
            }
            if (!TextUtils.isEmpty((CharSequence)object)) {
                charSequence = new StringBuilder();
                ((StringBuilder)charSequence).append("init: set 'gsm.sim.operator.numeric' to operator='");
                ((StringBuilder)charSequence).append((String)object);
                ((StringBuilder)charSequence).append("'");
                this.logd(((StringBuilder)charSequence).toString());
                charSequence = new StringBuilder();
                ((StringBuilder)charSequence).append("update icc_operator_numeric=");
                ((StringBuilder)charSequence).append((String)object);
                this.logd(((StringBuilder)charSequence).toString());
                stringBuilder.setSimOperatorNumericForPhone(this.mPhoneId, (String)object);
                SubscriptionController.getInstance().setMccMnc((String)object, this.getSubId());
                this.setIsoCountryProperty((String)object);
                stringBuilder = new StringBuilder();
                stringBuilder.append("update mccmnc=");
                stringBuilder.append((String)object);
                this.logd(stringBuilder.toString());
                MccTable.updateMccMncConfiguration(this.mContext, (String)object);
            }
            this.updateCurrentCarrierInProvider((String)object);
        }
    }

    private boolean isCarrierOtaSpNum(String charSequence) {
        boolean bl = false;
        boolean bl2 = false;
        boolean bl3 = false;
        boolean bl4 = false;
        int n = GsmCdmaPhone.extractSelCodeFromOtaSpNum((String)charSequence);
        if (n == -1) {
            return false;
        }
        if (!TextUtils.isEmpty((CharSequence)this.mCarrierOtaSpNumSchema)) {
            String[] arrstring = pOtaSpNumSchema.matcher(this.mCarrierOtaSpNumSchema);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("isCarrierOtaSpNum,schema");
            stringBuilder.append(this.mCarrierOtaSpNumSchema);
            Rlog.d((String)LOG_TAG, (String)stringBuilder.toString());
            if (arrstring.find()) {
                arrstring = pOtaSpNumSchema.split(this.mCarrierOtaSpNumSchema);
                if (!TextUtils.isEmpty((CharSequence)arrstring[0]) && arrstring[0].equals("SELC")) {
                    if (n != -1) {
                        bl4 = GsmCdmaPhone.checkOtaSpNumBasedOnSysSelCode(n, arrstring);
                    } else {
                        Rlog.d((String)LOG_TAG, (String)"isCarrierOtaSpNum,sysSelCodeInt is invalid");
                        bl4 = bl;
                    }
                } else if (!TextUtils.isEmpty((CharSequence)arrstring[0]) && arrstring[0].equals("FC")) {
                    n = Integer.parseInt(arrstring[1]);
                    if (((String)charSequence).regionMatches(0, arrstring[2], 0, n)) {
                        bl4 = true;
                    } else {
                        Rlog.d((String)LOG_TAG, (String)"isCarrierOtaSpNum,not otasp number");
                    }
                } else {
                    charSequence = new StringBuilder();
                    ((StringBuilder)charSequence).append("isCarrierOtaSpNum,ota schema not supported");
                    ((StringBuilder)charSequence).append(arrstring[0]);
                    Rlog.d((String)LOG_TAG, (String)((StringBuilder)charSequence).toString());
                    bl4 = bl;
                }
            } else {
                charSequence = new StringBuilder();
                ((StringBuilder)charSequence).append("isCarrierOtaSpNum,ota schema pattern not right");
                ((StringBuilder)charSequence).append(this.mCarrierOtaSpNumSchema);
                Rlog.d((String)LOG_TAG, (String)((StringBuilder)charSequence).toString());
                bl4 = bl2;
            }
        } else {
            Rlog.d((String)LOG_TAG, (String)"isCarrierOtaSpNum,ota schema pattern empty");
            bl4 = bl3;
        }
        return bl4;
    }

    @UnsupportedAppUsage
    private boolean isCfEnable(int n) {
        boolean bl;
        boolean bl2 = bl = true;
        if (n != 1) {
            bl2 = n == 3 ? bl : false;
        }
        return bl2;
    }

    private boolean isImsUtEnabledOverCdma() {
        boolean bl = this.isPhoneTypeCdmaLte() && this.mImsPhone != null && this.mImsPhone.isUtEnabled();
        return bl;
    }

    private static boolean isIs683OtaSpDialStr(String string) {
        boolean bl = false;
        if (string.length() == 4) {
            if (string.equals(IS683A_FEATURE_CODE)) {
                bl = true;
            }
        } else {
            switch (GsmCdmaPhone.extractSelCodeFromOtaSpNum(string)) {
                default: {
                    break;
                }
                case 0: 
                case 1: 
                case 2: 
                case 3: 
                case 4: 
                case 5: 
                case 6: 
                case 7: {
                    bl = true;
                }
            }
        }
        return bl;
    }

    @UnsupportedAppUsage
    private boolean isManualSelProhibitedInGlobalMode() {
        boolean bl;
        Object object;
        block2 : {
            block3 : {
                boolean bl2 = false;
                object = this.getContext().getResources().getString(17040889);
                bl = bl2;
                if (TextUtils.isEmpty((CharSequence)object)) break block2;
                object = ((String)object).split(";");
                bl = bl2;
                if (object == null) break block2;
                if (((Object)object).length == 1 && ((String)object[0]).equalsIgnoreCase("true")) break block3;
                bl = bl2;
                if (((Object)object).length != 2) break block2;
                bl = bl2;
                if (TextUtils.isEmpty((CharSequence)object[1])) break block2;
                bl = bl2;
                if (!((String)object[0]).equalsIgnoreCase("true")) break block2;
                bl = bl2;
                if (!this.isMatchGid((String)object[1])) break block2;
            }
            bl = true;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("isManualNetSelAllowedInGlobal in current carrier is ");
        ((StringBuilder)object).append(bl);
        this.logd(((StringBuilder)object).toString());
        return bl;
    }

    @UnsupportedAppUsage
    private boolean isValidCommandInterfaceCFAction(int n) {
        return n == 0 || n == 1 || n == 3 || n == 4;
    }

    @UnsupportedAppUsage
    private boolean isValidCommandInterfaceCFReason(int n) {
        return n == 0 || n == 1 || n == 2 || n == 3 || n == 4 || n == 5;
    }

    private void loadTtyMode() {
        int n = 0;
        TelecomManager telecomManager = TelecomManager.from((Context)this.mContext);
        if (telecomManager != null) {
            n = telecomManager.getCurrentTtyMode();
        }
        this.updateTtyMode(n);
        this.updateUiTtyMode(Settings.Secure.getInt((ContentResolver)this.mContext.getContentResolver(), (String)"preferred_tty_mode", (int)0));
    }

    @UnsupportedAppUsage
    private void logd(String string) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[");
        stringBuilder.append(this.mPhoneId);
        stringBuilder.append("] ");
        stringBuilder.append(string);
        Rlog.d((String)LOG_TAG, (String)stringBuilder.toString());
    }

    @UnsupportedAppUsage
    private void loge(String string) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[");
        stringBuilder.append(this.mPhoneId);
        stringBuilder.append("] ");
        stringBuilder.append(string);
        Rlog.e((String)LOG_TAG, (String)stringBuilder.toString());
    }

    private void logi(String string) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[");
        stringBuilder.append(this.mPhoneId);
        stringBuilder.append("] ");
        stringBuilder.append(string);
        Rlog.i((String)LOG_TAG, (String)stringBuilder.toString());
    }

    private ServiceState mergeServiceStates(ServiceState serviceState, ServiceState serviceState2) {
        if (serviceState2.getVoiceRegState() != 0) {
            return serviceState;
        }
        if (serviceState2.getRilDataRadioTechnology() == 18) {
            serviceState2 = new ServiceState(serviceState);
            serviceState2.setVoiceRegState(serviceState.getDataRegState());
            serviceState2.setEmergencyOnly(false);
            return serviceState2;
        }
        return serviceState;
    }

    private void onIncomingUSSD(int n, String string) {
        GsmMmiCode gsmMmiCode;
        if (!this.isPhoneTypeGsm()) {
            this.loge("onIncomingUSSD: not expected on GSM");
        }
        boolean bl = false;
        boolean bl2 = n == 1;
        boolean bl3 = n != 0 && n != 1;
        if (n == 2) {
            bl = true;
        }
        GsmMmiCode gsmMmiCode2 = null;
        n = 0;
        int n2 = this.mPendingMMIs.size();
        do {
            gsmMmiCode = gsmMmiCode2;
            if (n >= n2) break;
            if (((GsmMmiCode)this.mPendingMMIs.get(n)).isPendingUSSD()) {
                gsmMmiCode = (GsmMmiCode)this.mPendingMMIs.get(n);
                break;
            }
            ++n;
        } while (true);
        if (gsmMmiCode != null) {
            if (bl) {
                gsmMmiCode.onUssdRelease();
            } else if (bl3) {
                gsmMmiCode.onUssdFinishedError();
            } else {
                gsmMmiCode.onUssdFinished(string, bl2);
            }
        } else if (!bl3 && string != null) {
            this.onNetworkInitiatedUssd(GsmMmiCode.newNetworkInitiatedUssd(string, bl2, this, (UiccCardApplication)this.mUiccApplication.get()));
        }
    }

    private void onNetworkInitiatedUssd(MmiCode mmiCode) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("onNetworkInitiatedUssd: mmi=");
        stringBuilder.append(mmiCode);
        Rlog.v((String)LOG_TAG, (String)stringBuilder.toString());
        this.mMmiCompleteRegistrants.notifyRegistrants(new AsyncResult(null, (Object)mmiCode, null));
    }

    private void onVoiceRegStateOrRatChanged(int n, int n2) {
        this.logd("onVoiceRegStateOrRatChanged");
        this.mCT.dispatchCsCallRadioTech(this.getCsCallRadioTech(n, n2));
    }

    private void phoneObjectUpdater(int n) {
        boolean bl;
        int n2;
        Object object;
        boolean bl2;
        block15 : {
            block16 : {
                int n3;
                block17 : {
                    block14 : {
                        object = new StringBuilder();
                        ((StringBuilder)object).append("phoneObjectUpdater: newVoiceRadioTech=");
                        ((StringBuilder)object).append(n);
                        this.logd(((StringBuilder)object).toString());
                        if (ServiceState.isLte((int)n)) break block14;
                        n2 = n;
                        if (n != 0) break block15;
                    }
                    if ((object = ((CarrierConfigManager)this.getContext().getSystemService("carrier_config")).getConfigForSubId(this.getSubId())) == null) break block16;
                    n3 = object.getInt("volte_replacement_rat_int");
                    object = new StringBuilder();
                    ((StringBuilder)object).append("phoneObjectUpdater: volteReplacementRat=");
                    ((StringBuilder)object).append(n3);
                    this.logd(((StringBuilder)object).toString());
                    n2 = n;
                    if (n3 == 0) break block15;
                    if (ServiceState.isGsm((int)n3)) break block17;
                    n2 = n;
                    if (!this.isCdmaSubscriptionAppPresent()) break block15;
                }
                n2 = n3;
                break block15;
            }
            this.loge("phoneObjectUpdater: didn't get volteReplacementRat from carrier config");
            n2 = n;
        }
        if (this.mRilVersion == 6 && this.getLteOnCdmaMode() == 1) {
            if (this.getPhoneType() == 2) {
                object = new StringBuilder();
                ((StringBuilder)object).append("phoneObjectUpdater: LTE ON CDMA property is set. Use CDMA Phone newVoiceRadioTech=");
                ((StringBuilder)object).append(n2);
                ((StringBuilder)object).append(" mActivePhone=");
                ((StringBuilder)object).append(this.getPhoneName());
                this.logd(((StringBuilder)object).toString());
                return;
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("phoneObjectUpdater: LTE ON CDMA property is set. Switch to CDMALTEPhone newVoiceRadioTech=");
            ((StringBuilder)object).append(n2);
            ((StringBuilder)object).append(" mActivePhone=");
            ((StringBuilder)object).append(this.getPhoneName());
            this.logd(((StringBuilder)object).toString());
            n = 6;
        } else {
            if (this.isShuttingDown()) {
                this.logd("Device is shutting down. No need to switch phone now.");
                return;
            }
            bl2 = ServiceState.isCdma((int)n2);
            bl = ServiceState.isGsm((int)n2);
            if (bl2 && this.getPhoneType() == 2 || bl && this.getPhoneType() == 1) {
                object = new StringBuilder();
                ((StringBuilder)object).append("phoneObjectUpdater: No change ignore, newVoiceRadioTech=");
                ((StringBuilder)object).append(n2);
                ((StringBuilder)object).append(" mActivePhone=");
                ((StringBuilder)object).append(this.getPhoneName());
                this.logd(((StringBuilder)object).toString());
                return;
            }
            n = n2;
            if (!bl2) {
                n = n2;
                if (!bl) {
                    object = new StringBuilder();
                    ((StringBuilder)object).append("phoneObjectUpdater: newVoiceRadioTech=");
                    ((StringBuilder)object).append(n2);
                    ((StringBuilder)object).append(" doesn't match either CDMA or GSM - error! No phone change");
                    this.loge(((StringBuilder)object).toString());
                    return;
                }
            }
        }
        if (n == 0) {
            object = new StringBuilder();
            ((StringBuilder)object).append("phoneObjectUpdater: Unknown rat ignore,  newVoiceRadioTech=Unknown. mActivePhone=");
            ((StringBuilder)object).append(this.getPhoneName());
            this.logd(((StringBuilder)object).toString());
            return;
        }
        bl2 = bl = false;
        if (this.mResetModemOnRadioTechnologyChange) {
            bl2 = bl;
            if (this.mCi.getRadioState() == 1) {
                bl2 = true;
                this.logd("phoneObjectUpdater: Setting Radio Power to Off");
                this.mCi.setRadioPower(false, null);
            }
        }
        this.switchVoiceRadioTech(n);
        if (this.mResetModemOnRadioTechnologyChange && bl2) {
            this.logd("phoneObjectUpdater: Resetting Radio");
            this.mCi.setRadioPower(bl2, null);
        }
        if ((object = this.getUiccProfile()) != null) {
            ((UiccProfile)object).setVoiceRadioTech(n);
        }
        object = new Intent("android.intent.action.RADIO_TECHNOLOGY");
        object.putExtra("phoneName", this.getPhoneName());
        SubscriptionManager.putPhoneIdAndSubIdExtra((Intent)object, (int)this.mPhoneId);
        ActivityManager.broadcastStickyIntent((Intent)object, (int)-1);
    }

    private void processIccRecordEvents(int n) {
        if (n == 1) {
            this.logi("processIccRecordEvents: EVENT_CFI");
            this.notifyCallForwardingIndicator();
        }
    }

    private void registerForIccRecordEvents() {
        IccRecords iccRecords = (IccRecords)this.mIccRecords.get();
        if (iccRecords == null) {
            return;
        }
        if (this.isPhoneTypeGsm()) {
            iccRecords.registerForNetworkSelectionModeAutomatic(this, 28, null);
            iccRecords.registerForRecordsEvents(this, 29, null);
            iccRecords.registerForRecordsLoaded(this, 3, null);
        } else {
            iccRecords.registerForRecordsLoaded(this, 22, null);
            if (this.isPhoneTypeCdmaLte()) {
                iccRecords.registerForRecordsLoaded(this, 3, null);
            }
        }
    }

    private void sendEmergencyCallbackModeChange() {
        Intent intent = new Intent("android.intent.action.EMERGENCY_CALLBACK_MODE_CHANGED");
        intent.putExtra("phoneinECMState", this.isInEcm());
        SubscriptionManager.putPhoneIdAndSubIdExtra((Intent)intent, (int)this.getPhoneId());
        ActivityManager.broadcastStickyIntent((Intent)intent, (int)-1);
        this.logi("sendEmergencyCallbackModeChange");
    }

    private void sendUssdResponse(String string, CharSequence charSequence, int n, ResultReceiver resultReceiver) {
        string = new UssdResponse(string, charSequence);
        charSequence = new Bundle();
        charSequence.putParcelable("USSD_RESPONSE", (Parcelable)string);
        resultReceiver.send(n, (Bundle)charSequence);
    }

    private void setIsoCountryProperty(String string) {
        TelephonyManager telephonyManager = TelephonyManager.from((Context)this.mContext);
        if (TextUtils.isEmpty((CharSequence)string)) {
            this.logd("setIsoCountryProperty: clear 'gsm.sim.operator.iso-country'");
            telephonyManager.setSimCountryIsoForPhone(this.mPhoneId, "");
            SubscriptionController.getInstance().setCountryIso("", this.getSubId());
        } else {
            CharSequence charSequence = "";
            try {
                string = MccTable.countryCodeForMcc(string.substring(0, 3));
            }
            catch (StringIndexOutOfBoundsException stringIndexOutOfBoundsException) {
                Rlog.e((String)LOG_TAG, (String)"setIsoCountryProperty: countryCodeForMcc error", (Throwable)stringIndexOutOfBoundsException);
                string = charSequence;
            }
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("setIsoCountryProperty: set 'gsm.sim.operator.iso-country' to iso=");
            ((StringBuilder)charSequence).append(string);
            this.logd(((StringBuilder)charSequence).toString());
            telephonyManager.setSimCountryIsoForPhone(this.mPhoneId, string);
            SubscriptionController.getInstance().setCountryIso(string, this.getSubId());
        }
    }

    private void setVmSimImsi(String string) {
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences((Context)this.getContext()).edit();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(VM_SIM_IMSI);
        stringBuilder.append(this.getPhoneId());
        editor.putString(stringBuilder.toString(), string);
        editor.apply();
    }

    private void storeVoiceMailNumber(String string) {
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences((Context)this.getContext()).edit();
        if (this.isPhoneTypeGsm()) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(VM_NUMBER);
            stringBuilder.append(this.getPhoneId());
            editor.putString(stringBuilder.toString(), string);
            editor.apply();
            this.setVmSimImsi(this.getSubscriberId());
        } else {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(VM_NUMBER_CDMA);
            stringBuilder.append(this.getPhoneId());
            editor.putString(stringBuilder.toString(), string);
            editor.apply();
        }
    }

    private void switchPhoneType(int n) {
        this.removeCallbacks(this.mExitEcmRunnable);
        this.initRatSpecific(n);
        this.mSST.updatePhoneType();
        String string = n == 1 ? "GSM" : "CDMA";
        this.setPhoneName(string);
        this.onUpdateIccAvailability();
        this.unregisterForIccRecordEvents();
        this.registerForIccRecordEvents();
        this.mCT.updatePhoneType();
        n = this.mCi.getRadioState();
        if (n != 2) {
            this.handleRadioAvailable();
            if (n == 1) {
                this.handleRadioOn();
            }
        }
        if (n != 1) {
            this.handleRadioOffOrNotAvailable();
        }
    }

    private void switchVoiceRadioTech(int n) {
        Object object;
        block7 : {
            block6 : {
                block5 : {
                    object = this.getPhoneName();
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Switching Voice Phone : ");
                    stringBuilder.append((String)object);
                    stringBuilder.append(" >>> ");
                    object = ServiceState.isGsm((int)n) ? "GSM" : "CDMA";
                    stringBuilder.append((String)object);
                    this.logd(stringBuilder.toString());
                    if (!ServiceState.isCdma((int)n)) break block5;
                    object = this.mUiccController.getUiccCardApplication(this.mPhoneId, 2);
                    if (object != null && ((UiccCardApplication)object).getType() == IccCardApplicationStatus.AppType.APPTYPE_RUIM) {
                        this.switchPhoneType(2);
                    } else {
                        this.switchPhoneType(6);
                    }
                    break block6;
                }
                if (!ServiceState.isGsm((int)n)) break block7;
                this.switchPhoneType(1);
            }
            return;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("deleteAndCreatePhone: newVoiceRadioTech=");
        ((StringBuilder)object).append(n);
        ((StringBuilder)object).append(" is not CDMA or GSM (error) - aborting!");
        this.loge(((StringBuilder)object).toString());
    }

    @UnsupportedAppUsage
    private void syncClirSetting() {
        Object object = PreferenceManager.getDefaultSharedPreferences((Context)this.getContext());
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("clir_key");
        stringBuilder.append(this.getPhoneId());
        int n = object.getInt(stringBuilder.toString(), -1);
        object = new StringBuilder();
        ((StringBuilder)object).append("syncClirSetting: clir_key");
        ((StringBuilder)object).append(this.getPhoneId());
        ((StringBuilder)object).append("=");
        ((StringBuilder)object).append(n);
        Rlog.i((String)LOG_TAG, (String)((StringBuilder)object).toString());
        if (n >= 0) {
            this.mCi.setCLIR(n, null);
        }
    }

    private static int telecomModeToPhoneMode(int n) {
        return n == 1 || n == 2 || n == 3;
    }

    private void unregisterForIccRecordEvents() {
        IccRecords iccRecords = (IccRecords)this.mIccRecords.get();
        if (iccRecords == null) {
            return;
        }
        iccRecords.unregisterForNetworkSelectionModeAutomatic(this);
        iccRecords.unregisterForRecordsEvents(this);
        iccRecords.unregisterForRecordsLoaded(this);
    }

    private boolean updateCurrentCarrierInProvider(String string) {
        if (!(this.isPhoneTypeCdma() || this.isPhoneTypeCdmaLte() && this.mUiccController.getUiccCardApplication(this.mPhoneId, 1) == null)) {
            this.logd("updateCurrentCarrierInProvider not updated X retVal=true");
            return true;
        }
        this.logd("CDMAPhone: updateCurrentCarrierInProvider called");
        if (!TextUtils.isEmpty((CharSequence)string)) {
            try {
                Uri uri = Uri.withAppendedPath((Uri)Telephony.Carriers.CONTENT_URI, (String)"current");
                ContentValues contentValues = new ContentValues();
                contentValues.put("numeric", string);
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("updateCurrentCarrierInProvider from system: numeric=");
                stringBuilder.append(string);
                this.logd(stringBuilder.toString());
                this.getContext().getContentResolver().insert(uri, contentValues);
                stringBuilder = new StringBuilder();
                stringBuilder.append("update mccmnc=");
                stringBuilder.append(string);
                this.logd(stringBuilder.toString());
                MccTable.updateMccMncConfiguration(this.mContext, string);
                return true;
            }
            catch (SQLException sQLException) {
                Rlog.e((String)LOG_TAG, (String)"Can't store current operator", (Throwable)sQLException);
            }
        }
        return false;
    }

    private void updateTtyMode(int n) {
        this.logi(String.format("updateTtyMode ttyMode=%d", n));
        this.setTTYMode(GsmCdmaPhone.telecomModeToPhoneMode(n), null);
    }

    private void updateUiTtyMode(int n) {
        this.logi(String.format("updateUiTtyMode ttyMode=%d", n));
        this.setUiTTYMode(GsmCdmaPhone.telecomModeToPhoneMode(n), null);
    }

    @Override
    public void acceptCall(int n) throws CallStateException {
        Phone phone = this.mImsPhone;
        if (phone != null && phone.getRingingCall().isRinging()) {
            phone.acceptCall(n);
        } else {
            this.mCT.acceptCall();
        }
    }

    @Override
    public void activateCellBroadcastSms(int n, Message message) {
        this.loge("[GsmCdmaPhone] activateCellBroadcastSms() is obsolete; use SmsManager");
        message.sendToTarget();
    }

    @Override
    public boolean canConference() {
        if (this.mImsPhone != null && this.mImsPhone.canConference()) {
            return true;
        }
        if (this.isPhoneTypeGsm()) {
            return this.mCT.canConference();
        }
        this.loge("canConference: not possible in CDMA");
        return false;
    }

    @Override
    public boolean canTransfer() {
        if (this.isPhoneTypeGsm()) {
            return this.mCT.canTransfer();
        }
        this.loge("canTransfer: not possible in CDMA");
        return false;
    }

    public void changeCallBarringPassword(String string, String string2, String string3, Message message) {
        if (this.isPhoneTypeGsm()) {
            this.mCi.changeBarringPassword(string, string2, string3, message);
        } else {
            this.loge("changeCallBarringPassword: not possible in CDMA");
        }
    }

    @Override
    public void clearDisconnected() {
        this.mCT.clearDisconnected();
    }

    @Override
    public void conference() {
        if (this.mImsPhone != null && this.mImsPhone.canConference()) {
            this.logd("conference() - delegated to IMS phone");
            try {
                this.mImsPhone.conference();
            }
            catch (CallStateException callStateException) {
                this.loge(callStateException.toString());
            }
            return;
        }
        if (this.isPhoneTypeGsm()) {
            this.mCT.conference();
        } else {
            this.loge("conference: not possible in CDMA");
        }
    }

    @Override
    public Connection dial(String object, PhoneInternalInterface.DialArgs dialArgs) throws CallStateException {
        if (!this.isPhoneTypeGsm() && dialArgs.uusInfo != null) {
            throw new CallStateException("Sending UUS information NOT supported in CDMA!");
        }
        boolean bl = PhoneNumberUtils.isEmergencyNumber((int)this.getSubId(), (String)object);
        Phone phone = this.mImsPhone;
        Object object2 = (CarrierConfigManager)this.mContext.getSystemService("carrier_config");
        boolean bl2 = object2.getConfigForSubId(this.getSubId()).getBoolean("carrier_use_ims_first_for_emergency_bool");
        boolean bl3 = false;
        boolean bl4 = object != null ? ((String)object).startsWith(PREFIX_WPS) : false;
        boolean bl5 = object2.getConfigForSubId(this.getSubId()).getBoolean("support_wps_over_ims_bool");
        boolean bl6 = this.isImsUseEnabled() && phone != null && (phone.isVolteEnabled() || phone.isWifiCallingEnabled() || phone.isVideoEnabled() && VideoProfile.isVideo((int)dialArgs.videoState)) && phone.getServiceState().getState() == 0 && (!bl4 || bl5);
        bl2 = phone != null && bl && bl2 && ImsManager.getInstance((Context)this.mContext, (int)this.mPhoneId).isNonTtyOrTtyOnVolteEnabled() && phone.isImsAvailable();
        object2 = PhoneNumberUtils.extractNetworkPortionAlt((String)PhoneNumberUtils.stripSeparators((String)object));
        boolean bl7 = (object2.startsWith("*") || object2.startsWith("#")) && object2.endsWith("#");
        boolean bl8 = bl3;
        if (phone != null) {
            bl8 = bl3;
            if (phone.isUtEnabled()) {
                bl8 = true;
            }
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("useImsForCall=");
        stringBuilder.append(bl6);
        stringBuilder.append(", useImsForEmergency=");
        stringBuilder.append(bl2);
        stringBuilder.append(", useImsForUt=");
        stringBuilder.append(bl8);
        stringBuilder.append(", isUt=");
        stringBuilder.append(bl7);
        stringBuilder.append(", isWpsCall=");
        stringBuilder.append(bl4);
        stringBuilder.append(", allowWpsOverIms=");
        stringBuilder.append(bl5);
        stringBuilder.append(", imsPhone=");
        stringBuilder.append(phone);
        stringBuilder.append(", imsPhone.isVolteEnabled()=");
        CharSequence charSequence = "N/A";
        object2 = phone != null ? Boolean.valueOf(phone.isVolteEnabled()) : "N/A";
        stringBuilder.append(object2);
        stringBuilder.append(", imsPhone.isVowifiEnabled()=");
        object2 = phone != null ? Boolean.valueOf(phone.isWifiCallingEnabled()) : "N/A";
        stringBuilder.append(object2);
        stringBuilder.append(", imsPhone.isVideoEnabled()=");
        object2 = phone != null ? Boolean.valueOf(phone.isVideoEnabled()) : "N/A";
        stringBuilder.append(object2);
        stringBuilder.append(", imsPhone.getServiceState().getState()=");
        object2 = charSequence;
        if (phone != null) {
            object2 = phone.getServiceState().getState();
        }
        stringBuilder.append(object2);
        this.logd(stringBuilder.toString());
        Phone.checkWfcWifiOnlyModeBeforeDial(this.mImsPhone, this.mPhoneId, this.mContext);
        if (bl6 && !bl7 || bl7 && bl8 || bl2) {
            try {
                this.logd("Trying IMS PS call");
                object2 = phone.dial((String)object, dialArgs);
                return object2;
            }
            catch (CallStateException callStateException) {
                charSequence = new StringBuilder();
                ((StringBuilder)charSequence).append("IMS PS call exception ");
                ((StringBuilder)charSequence).append(callStateException);
                ((StringBuilder)charSequence).append("useImsForCall =");
                ((StringBuilder)charSequence).append(bl6);
                ((StringBuilder)charSequence).append(", imsPhone =");
                ((StringBuilder)charSequence).append(phone);
                this.logd(((StringBuilder)charSequence).toString());
                if (!"cs_fallback".equals(callStateException.getMessage()) && !bl) {
                    object = new CallStateException(callStateException.getError(), callStateException.getMessage());
                    ((Throwable)object).setStackTrace(callStateException.getStackTrace());
                    throw object;
                }
                charSequence = new StringBuilder();
                ((StringBuilder)charSequence).append("IMS call failed with Exception: ");
                ((StringBuilder)charSequence).append(callStateException.getMessage());
                ((StringBuilder)charSequence).append(". Falling back to CS.");
                this.logi(((StringBuilder)charSequence).toString());
            }
        }
        if ((object2 = this.mSST) != null && object2.mSS.getState() == 1 && this.mSST.mSS.getDataRegState() != 0 && !bl) {
            throw new CallStateException("cannot dial in current state");
        }
        object2 = this.mSST;
        if (!(object2 == null || object2.mSS.getState() != 3 || VideoProfile.isVideo((int)dialArgs.videoState) || bl || bl7 && bl8)) {
            throw new CallStateException(2, "cannot dial voice call in airplane mode");
        }
        object2 = this.mSST;
        if (!(object2 == null || object2.mSS.getState() != 1 || this.mSST.mSS.getDataRegState() == 0 && ServiceState.isLte((int)this.mSST.mSS.getRilDataRadioTechnology()) || VideoProfile.isVideo((int)dialArgs.videoState) || bl)) {
            throw new CallStateException(1, "cannot dial voice call in out of service");
        }
        this.logd("Trying (non-IMS) CS call");
        if (this.isPhoneTypeGsm()) {
            return this.dialInternal((String)object, ((PhoneInternalInterface.DialArgs.Builder)new PhoneInternalInterface.DialArgs.Builder().setIntentExtras(dialArgs.intentExtras)).build());
        }
        return this.dialInternal((String)object, dialArgs);
    }

    @Override
    protected Connection dialInternal(String string, PhoneInternalInterface.DialArgs dialArgs) throws CallStateException {
        return this.dialInternal(string, dialArgs, null);
    }

    protected Connection dialInternal(String string, PhoneInternalInterface.DialArgs dialArgs, ResultReceiver object) throws CallStateException {
        string = PhoneNumberUtils.stripSeparators((String)string);
        if (this.isPhoneTypeGsm()) {
            if (this.handleInCallMmiCommands(string)) {
                return null;
            }
            GsmMmiCode gsmMmiCode = GsmMmiCode.newFromDialString(PhoneNumberUtils.extractNetworkPortionAlt((String)string), this, (UiccCardApplication)this.mUiccApplication.get(), (ResultReceiver)object);
            object = new StringBuilder();
            ((StringBuilder)object).append("dialInternal: dialing w/ mmi '");
            ((StringBuilder)object).append(gsmMmiCode);
            ((StringBuilder)object).append("'...");
            this.logd(((StringBuilder)object).toString());
            if (gsmMmiCode == null) {
                return this.mCT.dialGsm(string, dialArgs.uusInfo, dialArgs.intentExtras);
            }
            if (gsmMmiCode.isTemporaryModeCLIR()) {
                return this.mCT.dialGsm(gsmMmiCode.mDialingNumber, gsmMmiCode.getCLIRMode(), dialArgs.uusInfo, dialArgs.intentExtras);
            }
            this.mPendingMMIs.add(gsmMmiCode);
            this.mMmiRegistrants.notifyRegistrants(new AsyncResult(null, (Object)gsmMmiCode, null));
            gsmMmiCode.processCode();
            return null;
        }
        return this.mCT.dial(string, dialArgs.intentExtras);
    }

    @Override
    public void disableLocationUpdates() {
        this.mSST.disableLocationUpdates();
    }

    @Override
    public void dump(FileDescriptor object, PrintWriter printWriter, String[] arrstring) {
        printWriter.println("GsmCdmaPhone extends:");
        super.dump((FileDescriptor)object, printWriter, arrstring);
        object = new StringBuilder();
        ((StringBuilder)object).append(" mPrecisePhoneType=");
        ((StringBuilder)object).append(this.mPrecisePhoneType);
        printWriter.println(((StringBuilder)object).toString());
        object = new StringBuilder();
        ((StringBuilder)object).append(" mCT=");
        ((StringBuilder)object).append((Object)this.mCT);
        printWriter.println(((StringBuilder)object).toString());
        object = new StringBuilder();
        ((StringBuilder)object).append(" mSST=");
        ((StringBuilder)object).append((Object)this.mSST);
        printWriter.println(((StringBuilder)object).toString());
        object = new StringBuilder();
        ((StringBuilder)object).append(" mPendingMMIs=");
        ((StringBuilder)object).append(this.mPendingMMIs);
        printWriter.println(((StringBuilder)object).toString());
        object = new StringBuilder();
        ((StringBuilder)object).append(" mIccPhoneBookIntManager=");
        ((StringBuilder)object).append(this.mIccPhoneBookIntManager);
        printWriter.println(((StringBuilder)object).toString());
        object = new StringBuilder();
        ((StringBuilder)object).append(" mCdmaSSM=");
        ((StringBuilder)object).append((Object)this.mCdmaSSM);
        printWriter.println(((StringBuilder)object).toString());
        object = new StringBuilder();
        ((StringBuilder)object).append(" mCdmaSubscriptionSource=");
        ((StringBuilder)object).append(this.mCdmaSubscriptionSource);
        printWriter.println(((StringBuilder)object).toString());
        object = new StringBuilder();
        ((StringBuilder)object).append(" mWakeLock=");
        ((StringBuilder)object).append((Object)this.mWakeLock);
        printWriter.println(((StringBuilder)object).toString());
        object = new StringBuilder();
        ((StringBuilder)object).append(" isInEcm()=");
        ((StringBuilder)object).append(this.isInEcm());
        printWriter.println(((StringBuilder)object).toString());
        object = new StringBuilder();
        ((StringBuilder)object).append(" mCarrierOtaSpNumSchema=");
        ((StringBuilder)object).append(this.mCarrierOtaSpNumSchema);
        printWriter.println(((StringBuilder)object).toString());
        if (!this.isPhoneTypeGsm()) {
            object = new StringBuilder();
            ((StringBuilder)object).append(" getCdmaEriIconIndex()=");
            ((StringBuilder)object).append(this.getCdmaEriIconIndex());
            printWriter.println(((StringBuilder)object).toString());
            object = new StringBuilder();
            ((StringBuilder)object).append(" getCdmaEriIconMode()=");
            ((StringBuilder)object).append(this.getCdmaEriIconMode());
            printWriter.println(((StringBuilder)object).toString());
            object = new StringBuilder();
            ((StringBuilder)object).append(" getCdmaEriText()=");
            ((StringBuilder)object).append(this.getCdmaEriText());
            printWriter.println(((StringBuilder)object).toString());
            object = new StringBuilder();
            ((StringBuilder)object).append(" isMinInfoReady()=");
            ((StringBuilder)object).append(this.isMinInfoReady());
            printWriter.println(((StringBuilder)object).toString());
        }
        object = new StringBuilder();
        ((StringBuilder)object).append(" isCspPlmnEnabled()=");
        ((StringBuilder)object).append(this.isCspPlmnEnabled());
        printWriter.println(((StringBuilder)object).toString());
        printWriter.flush();
    }

    @Override
    public void enableEnhancedVoicePrivacy(boolean bl, Message message) {
        if (this.isPhoneTypeGsm()) {
            this.loge("enableEnhancedVoicePrivacy: not expected on GSM");
        } else {
            this.mCi.setPreferredVoicePrivacy(bl, message);
        }
    }

    @Override
    public void enableLocationUpdates() {
        this.mSST.enableLocationUpdates();
    }

    @UnsupportedAppUsage
    @Override
    public void exitEmergencyCallbackMode() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("exitEmergencyCallbackMode: mImsPhone=");
        stringBuilder.append(this.mImsPhone);
        stringBuilder.append(" isPhoneTypeGsm=");
        stringBuilder.append(this.isPhoneTypeGsm());
        Rlog.d((String)LOG_TAG, (String)stringBuilder.toString());
        if (this.isPhoneTypeGsm()) {
            if (this.mImsPhone != null) {
                this.mImsPhone.exitEmergencyCallbackMode();
            }
        } else {
            if (this.mWakeLock.isHeld()) {
                this.mWakeLock.release();
            }
            this.mCi.exitEmergencyCallbackMode(this.obtainMessage(26));
        }
    }

    @Override
    public void explicitCallTransfer() {
        if (this.isPhoneTypeGsm()) {
            this.mCT.explicitCallTransfer();
        } else {
            this.loge("explicitCallTransfer: not possible in CDMA");
        }
    }

    protected void finalize() {
        this.logd("GsmCdmaPhone finalized");
        PowerManager.WakeLock wakeLock = this.mWakeLock;
        if (wakeLock != null && wakeLock.isHeld()) {
            Rlog.e((String)LOG_TAG, (String)"UNEXPECTED; mWakeLock is held when finalizing.");
            this.mWakeLock.release();
        }
    }

    @Override
    public void getAvailableNetworks(Message message) {
        if (!this.isPhoneTypeGsm() && !this.isPhoneTypeCdmaLte()) {
            this.loge("getAvailableNetworks: not possible in CDMA");
        } else {
            message = this.obtainMessage(51, (Object)message);
            this.mCi.getAvailableNetworks(message);
        }
    }

    @Override
    public GsmCdmaCall getBackgroundCall() {
        return this.mCT.mBackgroundCall;
    }

    @Override
    public void getCallBarring(String string, String string2, Message message, int n) {
        if (this.isPhoneTypeGsm()) {
            Phone phone = this.mImsPhone;
            if (phone != null && phone.isUtEnabled()) {
                phone.getCallBarring(string, string2, message, n);
                return;
            }
            this.mCi.queryFacilityLock(string, string2, n, message);
        } else {
            this.loge("getCallBarringOption: not possible in CDMA");
        }
    }

    @Override
    public void getCallForwardingOption(int n, Message message) {
        if (!this.isPhoneTypeGsm() && !this.isImsUtEnabledOverCdma()) {
            this.loge("getCallForwardingOption: not possible in CDMA without IMS");
        } else {
            Phone phone = this.mImsPhone;
            if (phone != null && (phone.getServiceState().getState() == 0 || phone.isUtEnabled())) {
                phone.getCallForwardingOption(n, message);
                return;
            }
            if (this.isValidCommandInterfaceCFReason(n)) {
                this.logd("requesting call forwarding query.");
                if (n == 0) {
                    message = this.obtainMessage(13, (Object)message);
                }
                this.mCi.queryCallForwardStatus(n, 1, null, message);
            }
        }
    }

    @UnsupportedAppUsage
    @Override
    public CallTracker getCallTracker() {
        return this.mCT;
    }

    @Override
    public void getCallWaiting(Message message) {
        if (!this.isPhoneTypeGsm() && !this.isImsUtEnabledOverCdma()) {
            this.mCi.queryCallWaiting(1, message);
        } else {
            Phone phone = this.mImsPhone;
            if (phone != null && (phone.getServiceState().getState() == 0 || phone.isUtEnabled())) {
                phone.getCallWaiting(message);
                return;
            }
            this.mCi.queryCallWaiting(0, message);
        }
    }

    @Override
    public int getCarrierId() {
        return this.mCarrierResolver.getCarrierId();
    }

    @Override
    public int getCarrierIdListVersion() {
        return this.mCarrierResolver.getCarrierListVersion();
    }

    @Override
    public ImsiEncryptionInfo getCarrierInfoForImsiEncryption(int n) {
        return CarrierInfoManager.getCarrierInfoForImsiEncryption(n, this.mContext);
    }

    @Override
    public String getCarrierName() {
        return this.mCarrierResolver.getCarrierName();
    }

    @Override
    public int getCdmaEriIconIndex() {
        if (this.isPhoneTypeGsm()) {
            return super.getCdmaEriIconIndex();
        }
        return this.getServiceState().getCdmaEriIconIndex();
    }

    @Override
    public int getCdmaEriIconMode() {
        if (this.isPhoneTypeGsm()) {
            return super.getCdmaEriIconMode();
        }
        return this.getServiceState().getCdmaEriIconMode();
    }

    @UnsupportedAppUsage
    @Override
    public String getCdmaEriText() {
        if (this.isPhoneTypeGsm()) {
            return super.getCdmaEriText();
        }
        int n = this.getServiceState().getCdmaRoamingIndicator();
        int n2 = this.getServiceState().getCdmaDefaultRoamingIndicator();
        return this.mSST.getCdmaEriText(n, n2);
    }

    @Override
    public String getCdmaMin() {
        return this.mSST.getCdmaMin();
    }

    @Override
    public String getCdmaPrlVersion() {
        return this.mSST.getPrlVersion();
    }

    @Override
    public void getCellBroadcastSmsConfig(Message message) {
        this.loge("[GsmCdmaPhone] getCellBroadcastSmsConfig() is obsolete; use SmsManager");
        message.sendToTarget();
    }

    @Override
    public void getCellLocation(WorkSource workSource, Message message) {
        this.mSST.requestCellLocation(workSource, message);
    }

    public String getCountryIso() {
        int n = this.getSubId();
        SubscriptionInfo subscriptionInfo = SubscriptionManager.from((Context)this.getContext()).getActiveSubscriptionInfo(n);
        if (subscriptionInfo == null) {
            return null;
        }
        return subscriptionInfo.getCountryIso().toUpperCase();
    }

    public int getCsCallRadioTech() {
        int n = 0;
        ServiceStateTracker serviceStateTracker = this.mSST;
        if (serviceStateTracker != null) {
            n = this.getCsCallRadioTech(serviceStateTracker.mSS.getVoiceRegState(), this.mSST.mSS.getRilVoiceRadioTechnology());
        }
        return n;
    }

    @Override
    public PhoneInternalInterface.DataActivityState getDataActivityState() {
        PhoneInternalInterface.DataActivityState dataActivityState;
        PhoneInternalInterface.DataActivityState dataActivityState2 = dataActivityState = PhoneInternalInterface.DataActivityState.NONE;
        if (this.mSST.getCurrentDataConnectionState() == 0) {
            dataActivityState2 = dataActivityState;
            if (this.getDcTracker(1) != null) {
                int n = 3.$SwitchMap$com$android$internal$telephony$DctConstants$Activity[this.getDcTracker(1).getActivity().ordinal()];
                dataActivityState2 = n != 1 ? (n != 2 ? (n != 3 ? (n != 4 ? PhoneInternalInterface.DataActivityState.NONE : PhoneInternalInterface.DataActivityState.DORMANT) : PhoneInternalInterface.DataActivityState.DATAINANDOUT) : PhoneInternalInterface.DataActivityState.DATAOUT) : PhoneInternalInterface.DataActivityState.DATAIN;
            }
        }
        return dataActivityState2;
    }

    @Override
    public PhoneConstants.DataState getDataConnectionState(String string) {
        PhoneConstants.DataState dataState = PhoneConstants.DataState.DISCONNECTED;
        Object object = this.mSST;
        if (object == null) {
            dataState = PhoneConstants.DataState.DISCONNECTED;
        } else if (object.getCurrentDataConnectionState() != 0 && (this.isPhoneTypeCdma() || this.isPhoneTypeCdmaLte() || this.isPhoneTypeGsm() && !string.equals("emergency"))) {
            dataState = PhoneConstants.DataState.DISCONNECTED;
        } else {
            int n = this.mTransportManager.getCurrentTransport(ApnSetting.getApnTypesBitmaskFromString((String)string));
            if (this.getDcTracker(n) != null) {
                dataState = (n = 3.$SwitchMap$com$android$internal$telephony$DctConstants$State[this.getDcTracker(n).getState(string).ordinal()]) != 1 && n != 2 ? (n != 3 ? PhoneConstants.DataState.DISCONNECTED : PhoneConstants.DataState.CONNECTING) : (this.mCT.mState != PhoneConstants.State.IDLE && !this.mSST.isConcurrentVoiceAndDataAllowed() ? PhoneConstants.DataState.SUSPENDED : PhoneConstants.DataState.CONNECTED);
            }
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("getDataConnectionState apnType=");
        ((StringBuilder)object).append(string);
        ((StringBuilder)object).append(" ret=");
        ((StringBuilder)object).append((Object)dataState);
        this.logd(((StringBuilder)object).toString());
        return dataState;
    }

    @Override
    public boolean getDataRoamingEnabled() {
        if (this.getDcTracker(1) != null) {
            return this.getDcTracker(1).getDataRoamingEnabled();
        }
        return false;
    }

    @Override
    public String getDeviceId() {
        String string;
        block6 : {
            block5 : {
                if (this.isPhoneTypeGsm()) {
                    return this.mImei;
                }
                if (((CarrierConfigManager)this.mContext.getSystemService("carrier_config")).getConfigForSubId(this.getSubId()).getBoolean("force_imei_bool")) {
                    return this.mImei;
                }
                String string2 = this.getMeid();
                if (string2 == null) break block5;
                string = string2;
                if (!string2.matches("^0*$")) break block6;
            }
            this.loge("getDeviceId(): MEID is not initialized use ESN");
            string = this.getEsn();
        }
        return string;
    }

    @Override
    public String getDeviceSvn() {
        if (!this.isPhoneTypeGsm() && !this.isPhoneTypeCdmaLte()) {
            this.loge("getDeviceSvn(): return 0");
            return "0";
        }
        return this.mImeiSv;
    }

    public String getDtmfToneDelayKey() {
        String string = this.isPhoneTypeGsm() ? "gsm_dtmf_tone_delay_int" : "cdma_dtmf_tone_delay_int";
        return string;
    }

    @Override
    public EmergencyNumberTracker getEmergencyNumberTracker() {
        return this.mEmergencyNumberTracker;
    }

    @Override
    public void getEnhancedVoicePrivacy(Message message) {
        if (this.isPhoneTypeGsm()) {
            this.loge("getEnhancedVoicePrivacy: not expected on GSM");
        } else {
            this.mCi.getPreferredVoicePrivacy(message);
        }
    }

    @UnsupportedAppUsage
    @Override
    public String getEsn() {
        if (this.isPhoneTypeGsm()) {
            this.loge("[GsmCdmaPhone] getEsn() is a CDMA method");
            return "0";
        }
        return this.mEsn;
    }

    @Override
    public GsmCdmaCall getForegroundCall() {
        return this.mCT.mForegroundCall;
    }

    @Override
    public String getFullIccSerialNumber() {
        IccRecords iccRecords = (IccRecords)this.mIccRecords.get();
        Object object = iccRecords;
        if (!this.isPhoneTypeGsm()) {
            object = iccRecords;
            if (iccRecords == null) {
                object = this.mUiccController.getIccRecords(this.mPhoneId, 1);
            }
        }
        object = object != null ? ((IccRecords)object).getFullIccId() : null;
        return object;
    }

    @Override
    public String getGroupIdLevel1() {
        boolean bl = this.isPhoneTypeGsm();
        Object object = null;
        if (bl) {
            IccRecords iccRecords = (IccRecords)this.mIccRecords.get();
            if (iccRecords != null) {
                object = iccRecords.getGid1();
            }
            return object;
        }
        if (this.isPhoneTypeCdma()) {
            this.loge("GID1 is not available in CDMA");
            return null;
        }
        object = this.mSimRecords;
        object = object != null ? ((IccRecords)object).getGid1() : "";
        return object;
    }

    @Override
    public String getGroupIdLevel2() {
        boolean bl = this.isPhoneTypeGsm();
        Object object = null;
        if (bl) {
            IccRecords iccRecords = (IccRecords)this.mIccRecords.get();
            if (iccRecords != null) {
                object = iccRecords.getGid2();
            }
            return object;
        }
        if (this.isPhoneTypeCdma()) {
            this.loge("GID2 is not available in CDMA");
            return null;
        }
        object = this.mSimRecords;
        object = object != null ? ((IccRecords)object).getGid2() : "";
        return object;
    }

    @Override
    public IccCard getIccCard() {
        Object object = this.getUiccProfile();
        if (object != null) {
            return object;
        }
        object = this.mUiccController.getUiccSlotForPhone(this.mPhoneId);
        if (object != null && !((UiccSlot)((Object)object)).isStateUnknown()) {
            return new IccCard(IccCardConstants.State.ABSENT);
        }
        return new IccCard(IccCardConstants.State.UNKNOWN);
    }

    @Override
    public IccPhoneBookInterfaceManager getIccPhoneBookInterfaceManager() {
        return this.mIccPhoneBookIntManager;
    }

    @Override
    public boolean getIccRecordsLoaded() {
        UiccProfile uiccProfile = this.getUiccProfile();
        boolean bl = uiccProfile != null && uiccProfile.getIccRecordsLoaded();
        return bl;
    }

    @Override
    public String getIccSerialNumber() {
        IccRecords iccRecords = (IccRecords)this.mIccRecords.get();
        Object object = iccRecords;
        if (!this.isPhoneTypeGsm()) {
            object = iccRecords;
            if (iccRecords == null) {
                object = this.mUiccController.getIccRecords(this.mPhoneId, 1);
            }
        }
        object = object != null ? ((IccRecords)object).getIccId() : null;
        return object;
    }

    @Override
    public IccSmsInterfaceManager getIccSmsInterfaceManager() {
        return this.mIccSmsInterfaceManager;
    }

    @Override
    public String getImei() {
        return this.mImei;
    }

    @Override
    public IsimRecords getIsimRecords() {
        return this.mIsimUiccRecords;
    }

    @Override
    public String getLine1AlphaTag() {
        boolean bl = this.isPhoneTypeGsm();
        String string = null;
        if (bl) {
            IccRecords iccRecords = (IccRecords)this.mIccRecords.get();
            if (iccRecords != null) {
                string = iccRecords.getMsisdnAlphaTag();
            }
            return string;
        }
        this.loge("getLine1AlphaTag: not possible in CDMA");
        return null;
    }

    @UnsupportedAppUsage
    @Override
    public String getLine1Number() {
        boolean bl = this.isPhoneTypeGsm();
        IccRecords iccRecords = null;
        Object object = null;
        if (bl) {
            iccRecords = (IccRecords)this.mIccRecords.get();
            if (iccRecords != null) {
                object = iccRecords.getMsisdnNumber();
            }
            return object;
        }
        if (((CarrierConfigManager)this.mContext.getSystemService("carrier_config")).getConfigForSubId(this.getSubId()).getBoolean("use_usim_bool")) {
            SIMRecords sIMRecords = this.mSimRecords;
            object = iccRecords;
            if (sIMRecords != null) {
                object = sIMRecords.getMsisdnNumber();
            }
            return object;
        }
        return this.mSST.getMdnNumber();
    }

    @Override
    public int getLteOnCdmaMode() {
        int n = super.getLteOnCdmaMode();
        UiccCardApplication uiccCardApplication = this.mUiccController.getUiccCardApplication(this.mPhoneId, 2);
        if (uiccCardApplication != null && uiccCardApplication.getType() == IccCardApplicationStatus.AppType.APPTYPE_RUIM && n == 1) {
            return 0;
        }
        return n;
    }

    @Override
    public int getMNOCarrierId() {
        return this.mCarrierResolver.getMnoCarrierId();
    }

    @Override
    public String getMeid() {
        return this.mMeid;
    }

    @Override
    public String getMsisdn() {
        boolean bl = this.isPhoneTypeGsm();
        IccRecords iccRecords = null;
        Object object = null;
        if (bl) {
            iccRecords = (IccRecords)this.mIccRecords.get();
            if (iccRecords != null) {
                object = iccRecords.getMsisdnNumber();
            }
            return object;
        }
        if (this.isPhoneTypeCdmaLte()) {
            SIMRecords sIMRecords = this.mSimRecords;
            object = iccRecords;
            if (sIMRecords != null) {
                object = sIMRecords.getMsisdnNumber();
            }
            return object;
        }
        this.loge("getMsisdn: not expected on CDMA");
        return null;
    }

    @Override
    public boolean getMute() {
        return this.mCT.getMute();
    }

    @Override
    public String getNai() {
        Object object = this.mUiccController.getIccRecords(this.mPhoneId, 2);
        if (Log.isLoggable((String)LOG_TAG, (int)2)) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("IccRecords is ");
            stringBuilder.append(object);
            Rlog.v((String)LOG_TAG, (String)stringBuilder.toString());
        }
        object = object != null ? ((IccRecords)object).getNAI() : null;
        return object;
    }

    @Override
    public void getOutgoingCallerIdDisplay(Message message) {
        if (this.isPhoneTypeGsm()) {
            Phone phone = this.mImsPhone;
            if (phone != null && (phone.getServiceState().getState() == 0 || phone.isUtEnabled())) {
                phone.getOutgoingCallerIdDisplay(message);
                return;
            }
            this.mCi.getCLIR(message);
        } else {
            this.loge("getOutgoingCallerIdDisplay: not possible in CDMA");
        }
    }

    @Override
    public List<? extends MmiCode> getPendingMmiCodes() {
        return this.mPendingMMIs;
    }

    @UnsupportedAppUsage
    @Override
    public int getPhoneType() {
        if (this.mPrecisePhoneType == 1) {
            return 1;
        }
        return 2;
    }

    @Override
    public String getPlmn() {
        boolean bl = this.isPhoneTypeGsm();
        IccRecords iccRecords = null;
        Object object = null;
        if (bl) {
            iccRecords = (IccRecords)this.mIccRecords.get();
            if (iccRecords != null) {
                object = iccRecords.getPnnHomeName();
            }
            return object;
        }
        if (this.isPhoneTypeCdma()) {
            this.loge("Plmn is not available in CDMA");
            return null;
        }
        SIMRecords sIMRecords = this.mSimRecords;
        object = iccRecords;
        if (sIMRecords != null) {
            object = sIMRecords.getPnnHomeName();
        }
        return object;
    }

    @Override
    public Call getRingingCall() {
        Phone phone = this.mImsPhone;
        if (phone != null && phone.getRingingCall().isRinging()) {
            return phone.getRingingCall();
        }
        return this.mCT.mRingingCall;
    }

    @UnsupportedAppUsage
    @Override
    public ServiceState getServiceState() {
        ServiceStateTracker serviceStateTracker = this.mSST;
        if ((serviceStateTracker == null || serviceStateTracker.mSS.getState() != 0) && this.mImsPhone != null) {
            serviceStateTracker = this.mSST;
            serviceStateTracker = serviceStateTracker == null ? new ServiceState() : serviceStateTracker.mSS;
            return this.mergeServiceStates((ServiceState)serviceStateTracker, this.mImsPhone.getServiceState());
        }
        serviceStateTracker = this.mSST;
        if (serviceStateTracker != null) {
            return serviceStateTracker.mSS;
        }
        return new ServiceState();
    }

    @Override
    public ServiceStateTracker getServiceStateTracker() {
        return this.mSST;
    }

    @Override
    public int getSpecificCarrierId() {
        return this.mCarrierResolver.getSpecificCarrierId();
    }

    @Override
    public String getSpecificCarrierName() {
        return this.mCarrierResolver.getSpecificCarrierName();
    }

    @UnsupportedAppUsage
    @Override
    public PhoneConstants.State getState() {
        PhoneConstants.State state;
        if (this.mImsPhone != null && (state = this.mImsPhone.getState()) != PhoneConstants.State.IDLE) {
            return state;
        }
        return this.mCT.mState;
    }

    @Override
    public String getSubscriberId() {
        String string = null;
        if (this.isPhoneTypeCdma()) {
            string = this.mSST.getImsi();
        } else {
            IccRecords iccRecords = this.mUiccController.getIccRecords(this.mPhoneId, 1);
            if (iccRecords != null) {
                string = iccRecords.getIMSI();
            }
        }
        return string;
    }

    @UnsupportedAppUsage
    @Override
    public String getSystemProperty(String string, String string2) {
        if (this.getUnitTestMode()) {
            return null;
        }
        return TelephonyManager.getTelephonyProperty((int)this.mPhoneId, (String)string, (String)string2);
    }

    @Override
    public TransportManager getTransportManager() {
        return this.mTransportManager;
    }

    public UiccCardApplication getUiccCardApplication() {
        if (this.isPhoneTypeGsm()) {
            return this.mUiccController.getUiccCardApplication(this.mPhoneId, 1);
        }
        return this.mUiccController.getUiccCardApplication(this.mPhoneId, 2);
    }

    @Override
    public String getVoiceMailAlphaTag() {
        Object object = "";
        if (this.isPhoneTypeGsm()) {
            object = (IccRecords)this.mIccRecords.get();
            object = object != null ? ((IccRecords)object).getVoiceMailAlphaTag() : "";
        }
        if (object != null && ((String)object).length() != 0) {
            return object;
        }
        return this.mContext.getText(17039364).toString();
    }

    @Override
    public String getVoiceMailNumber() {
        Object object;
        Object object2;
        Object object3;
        if (this.isPhoneTypeGsm()) {
            object2 = (IccRecords)this.mIccRecords.get();
            object2 = object2 != null ? ((IccRecords)object2).getVoiceMailNumber() : "";
            object3 = object2;
            if (TextUtils.isEmpty((CharSequence)object2)) {
                object3 = PreferenceManager.getDefaultSharedPreferences((Context)this.getContext());
                object2 = new StringBuilder();
                ((StringBuilder)object2).append(VM_NUMBER);
                ((StringBuilder)object2).append(this.getPhoneId());
                object3 = object3.getString(((StringBuilder)object2).toString(), null);
            }
        } else {
            object2 = PreferenceManager.getDefaultSharedPreferences((Context)this.getContext());
            object3 = new StringBuilder();
            ((StringBuilder)object3).append(VM_NUMBER_CDMA);
            ((StringBuilder)object3).append(this.getPhoneId());
            object3 = object2.getString(((StringBuilder)object3).toString(), null);
        }
        object2 = object3;
        if (TextUtils.isEmpty((CharSequence)object3)) {
            PersistableBundle persistableBundle = ((CarrierConfigManager)this.getContext().getSystemService("carrier_config")).getConfigForSubId(this.getSubId());
            object2 = object3;
            if (persistableBundle != null) {
                object = persistableBundle.getString("default_vm_number_string");
                object2 = persistableBundle.getString("default_vm_number_roaming_string");
                if (TextUtils.isEmpty((CharSequence)object2) || !this.mSST.mSS.getRoaming()) {
                    object2 = object3;
                    if (!TextUtils.isEmpty((CharSequence)object)) {
                        object2 = object;
                    }
                }
            }
        }
        object3 = object2;
        if (TextUtils.isEmpty((CharSequence)object2)) {
            object = ((CarrierConfigManager)this.getContext().getSystemService("carrier_config")).getConfigForSubId(this.getSubId());
            object3 = object2;
            if (object != null) {
                object3 = object2;
                if (object.getBoolean("config_telephony_use_own_number_for_voicemail_bool")) {
                    object3 = this.getLine1Number();
                }
            }
        }
        return object3;
    }

    @VisibleForTesting
    public PowerManager.WakeLock getWakeLock() {
        return this.mWakeLock;
    }

    @UnsupportedAppUsage
    @Override
    public boolean handleInCallMmiCommands(String string) throws CallStateException {
        if (!this.isPhoneTypeGsm()) {
            this.loge("method handleInCallMmiCommands is NOT supported in CDMA!");
            return false;
        }
        Phone phone = this.mImsPhone;
        if (phone != null && phone.getServiceState().getState() == 0) {
            return phone.handleInCallMmiCommands(string);
        }
        if (!this.isInCall()) {
            return false;
        }
        if (TextUtils.isEmpty((CharSequence)string)) {
            return false;
        }
        boolean bl = false;
        switch (string.charAt(0)) {
            default: {
                break;
            }
            case '5': {
                bl = this.handleCcbsIncallSupplementaryService(string);
                break;
            }
            case '4': {
                bl = this.handleEctIncallSupplementaryService(string);
                break;
            }
            case '3': {
                bl = this.handleMultipartyIncallSupplementaryService(string);
                break;
            }
            case '2': {
                bl = this.handleCallHoldIncallSupplementaryService(string);
                break;
            }
            case '1': {
                bl = this.handleCallWaitingIncallSupplementaryService(string);
                break;
            }
            case '0': {
                bl = this.handleCallDeflectionIncallSupplementaryService(string);
            }
        }
        return bl;
    }

    @Override
    public void handleMessage(Message object) {
        int n = ((Message)object).what;
        boolean bl = false;
        switch (n) {
            default: {
                super.handleMessage((Message)object);
                break;
            }
            case 51: {
                Object object2;
                object = (AsyncResult)((Message)object).obj;
                if (((AsyncResult)object).exception == null && ((AsyncResult)object).result != null && this.mSST != null) {
                    List list2 = (List)((AsyncResult)object).result;
                    object2 = new ArrayList();
                    for (List list2 : list2) {
                        if (OperatorInfo.State.CURRENT == list2.getState()) {
                            object2.add(new OperatorInfo(this.mSST.filterOperatorNameByPattern(list2.getOperatorAlphaLong()), this.mSST.filterOperatorNameByPattern(list2.getOperatorAlphaShort()), list2.getOperatorNumeric(), list2.getState()));
                            continue;
                        }
                        object2.add(list2);
                    }
                    ((AsyncResult)object).result = object2;
                }
                if ((object2 = (Message)((AsyncResult)object).userObj) == null) break;
                AsyncResult.forMessage((Message)object2, (Object)((AsyncResult)object).result, (Throwable)((AsyncResult)object).exception);
                object2.sendToTarget();
                break;
            }
            case 50: {
                this.mDataEnabledSettings.updateProvisioningDataEnabled();
                break;
            }
            case 49: {
                this.mDataEnabledSettings.updateProvisionedChanged();
                break;
            }
            case 48: {
                bl = (Boolean)((AsyncResult)object.obj).result;
                this.mDataEnabledSettings.setCarrierDataEnabled(bl);
                break;
            }
            case 47: {
                this.logd("EVENT EVENT_RADIO_STATE_CHANGED");
                this.handleRadioPowerStateChange();
                break;
            }
            case 46: {
                object = (Pair)((AsyncResult)object.obj).result;
                this.onVoiceRegStateOrRatChanged((Integer)((Pair)object).first, (Integer)((Pair)object).second);
                break;
            }
            case 45: {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Event EVENT_MODEM_RESET Received isInEcm = ");
                stringBuilder.append(this.isInEcm());
                stringBuilder.append(" isPhoneTypeGsm = ");
                stringBuilder.append(this.isPhoneTypeGsm());
                stringBuilder.append(" mImsPhone = ");
                stringBuilder.append(this.mImsPhone);
                this.logd(stringBuilder.toString());
                if (!this.isInEcm()) break;
                if (this.isPhoneTypeGsm()) {
                    if (this.mImsPhone == null) break;
                    this.mImsPhone.handleExitEmergencyCallbackMode();
                    break;
                }
                this.handleExitEmergencyCallbackMode((Message)object);
                break;
            }
            case 44: {
                this.logd("cdma_roaming_mode change is done");
                break;
            }
            case 43: {
                if (!this.mContext.getResources().getBoolean(17891547)) {
                    this.mCi.getVoiceRadioTechnology(this.obtainMessage(40));
                }
                if ((object = ImsManager.getInstance((Context)this.mContext, (int)this.mPhoneId)).isServiceAvailable()) {
                    object.updateImsServiceConfig(true);
                } else {
                    this.logd("ImsManager is not available to update CarrierConfig.");
                }
                object = ((CarrierConfigManager)this.getContext().getSystemService("carrier_config")).getConfigForSubId(this.getSubId());
                if (object != null) {
                    bl = object.getBoolean("broadcast_emergency_call_state_changes_bool");
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("broadcastEmergencyCallStateChanges = ");
                    stringBuilder.append(bl);
                    this.logd(stringBuilder.toString());
                    this.setBroadcastEmergencyCallStateChanges(bl);
                } else {
                    this.loge("didn't get broadcastEmergencyCallStateChanges from carrier config");
                }
                if (object != null) {
                    n = object.getInt("cdma_roaming_mode_int");
                    int n2 = Settings.Global.getInt((ContentResolver)this.getContext().getContentResolver(), (String)"roaming_settings", (int)-1);
                    if (n != -1) {
                        if (n == 0 || n == 1 || n == 2) {
                            object = new StringBuilder();
                            ((StringBuilder)object).append("cdma_roaming_mode is going to changed to ");
                            ((StringBuilder)object).append(n);
                            this.logd(((StringBuilder)object).toString());
                            this.setCdmaRoamingPreference(n, this.obtainMessage(44));
                            break;
                        }
                    } else if (n2 != n) {
                        object = new StringBuilder();
                        ((StringBuilder)object).append("cdma_roaming_mode is going to changed to ");
                        ((StringBuilder)object).append(n2);
                        this.logd(((StringBuilder)object).toString());
                        this.setCdmaRoamingPreference(n2, this.obtainMessage(44));
                    }
                    object = new StringBuilder();
                    ((StringBuilder)object).append("Invalid cdma_roaming_mode settings: ");
                    ((StringBuilder)object).append(n);
                    this.loge(((StringBuilder)object).toString());
                    break;
                }
                this.loge("didn't get the cdma_roaming_mode changes from the carrier config.");
                break;
            }
            case 42: {
                this.phoneObjectUpdater(((Message)object).arg1);
                break;
            }
            case 41: {
                object = (AsyncResult)((Message)object).obj;
                if (((AsyncResult)object).exception == null && ((AsyncResult)object).result != null) {
                    this.mRilVersion = (Integer)((AsyncResult)object).result;
                    break;
                }
                this.logd("Unexpected exception on EVENT_RIL_CONNECTED");
                this.mRilVersion = -1;
                break;
            }
            case 39: 
            case 40: {
                String string = ((Message)object).what == 39 ? "EVENT_VOICE_RADIO_TECH_CHANGED" : "EVENT_REQUEST_VOICE_RADIO_TECH_DONE";
                AsyncResult asyncResult = (AsyncResult)((Message)object).obj;
                if (asyncResult.exception == null) {
                    if (asyncResult.result != null && ((int[])asyncResult.result).length != 0) {
                        n = ((int[])asyncResult.result)[0];
                        object = new StringBuilder();
                        ((StringBuilder)object).append(string);
                        ((StringBuilder)object).append(": newVoiceTech=");
                        ((StringBuilder)object).append(n);
                        this.logd(((StringBuilder)object).toString());
                        this.phoneObjectUpdater(n);
                        break;
                    }
                    object = new StringBuilder();
                    ((StringBuilder)object).append(string);
                    ((StringBuilder)object).append(": has no tech!");
                    this.loge(((StringBuilder)object).toString());
                    break;
                }
                object = new StringBuilder();
                ((StringBuilder)object).append(string);
                ((StringBuilder)object).append(": exception=");
                ((StringBuilder)object).append(asyncResult.exception);
                this.loge(((StringBuilder)object).toString());
                break;
            }
            case 36: {
                object = (AsyncResult)((Message)object).obj;
                this.logd("Event EVENT_SS received");
                if (!this.isPhoneTypeGsm()) break;
                new GsmMmiCode(this, (UiccCardApplication)this.mUiccApplication.get()).processSsData((AsyncResult)object);
                break;
            }
            case 35: {
                Object object3 = (AsyncResult)((Message)object).obj;
                object = (RadioCapability)((AsyncResult)object3).result;
                if (((AsyncResult)object3).exception != null) {
                    Rlog.d((String)LOG_TAG, (String)"get phone radio capability fail, no need to change mRadioCapability");
                } else {
                    this.radioCapabilityUpdated((RadioCapability)object);
                }
                object3 = new StringBuilder();
                ((StringBuilder)object3).append("EVENT_GET_RADIO_CAPABILITY: phone rc: ");
                ((StringBuilder)object3).append(object);
                Rlog.d((String)LOG_TAG, (String)((StringBuilder)object3).toString());
                break;
            }
            case 29: {
                this.processIccRecordEvents((Integer)((AsyncResult)object.obj).result);
                break;
            }
            case 28: {
                object = (AsyncResult)((Message)object).obj;
                if (this.mSST.mSS.getIsManualSelection()) {
                    this.setNetworkSelectionModeAutomatic((Message)((AsyncResult)object).result);
                    this.logd("SET_NETWORK_SELECTION_AUTOMATIC: set to automatic");
                    break;
                }
                this.logd("SET_NETWORK_SELECTION_AUTOMATIC: already automatic, ignore");
                break;
            }
            case 27: {
                this.logd("EVENT_CDMA_SUBSCRIPTION_SOURCE_CHANGED");
                this.mCdmaSubscriptionSource = this.mCdmaSSM.getCdmaSubscriptionSource();
                break;
            }
            case 26: {
                this.handleExitEmergencyCallbackMode((Message)object);
                break;
            }
            case 25: {
                this.handleEnterEmergencyCallbackMode((Message)object);
                break;
            }
            case 22: {
                this.logd("Event EVENT_RUIM_RECORDS_LOADED Received");
                this.updateCurrentCarrierInProvider();
                break;
            }
            case 21: {
                object = (AsyncResult)((Message)object).obj;
                if (((AsyncResult)object).exception != null) break;
                object = (String[])((AsyncResult)object).result;
                this.mImei = object[0];
                this.mImeiSv = object[1];
                this.mEsn = object[2];
                this.mMeid = object[3];
                break;
            }
            case 20: {
                Message message;
                object = (AsyncResult)((Message)object).obj;
                if (this.isPhoneTypeGsm() && IccVmNotSupportedException.class.isInstance(((AsyncResult)object).exception) || !this.isPhoneTypeGsm() && IccException.class.isInstance(((AsyncResult)object).exception)) {
                    this.storeVoiceMailNumber(this.mVmNumber);
                    ((AsyncResult)object).exception = null;
                }
                if ((message = (Message)((AsyncResult)object).userObj) == null) break;
                AsyncResult.forMessage((Message)message, (Object)((AsyncResult)object).result, (Throwable)((AsyncResult)object).exception);
                message.sendToTarget();
                break;
            }
            case 19: {
                this.logd("Event EVENT_REGISTERED_TO_NETWORK Received");
                if (!this.isPhoneTypeGsm()) break;
                this.syncClirSetting();
                break;
            }
            case 18: {
                AsyncResult asyncResult = (AsyncResult)((Message)object).obj;
                if (asyncResult.exception == null) {
                    this.saveClirSetting(((Message)object).arg1);
                }
                if ((object = (Message)asyncResult.userObj) == null) break;
                AsyncResult.forMessage((Message)object, (Object)asyncResult.result, (Throwable)asyncResult.exception);
                object.sendToTarget();
                break;
            }
            case 13: {
                Message message;
                object = (AsyncResult)((Message)object).obj;
                if (((AsyncResult)object).exception == null) {
                    this.handleCfuQueryResult((CallForwardInfo[])((AsyncResult)object).result);
                }
                if ((message = (Message)((AsyncResult)object).userObj) == null) break;
                AsyncResult.forMessage((Message)message, (Object)((AsyncResult)object).result, (Throwable)((AsyncResult)object).exception);
                message.sendToTarget();
                break;
            }
            case 12: {
                AsyncResult asyncResult = (AsyncResult)((Message)object).obj;
                IccRecords iccRecords = (IccRecords)this.mIccRecords.get();
                Cfu cfu = (Cfu)asyncResult.userObj;
                if (asyncResult.exception == null && iccRecords != null) {
                    if (((Message)object).arg1 == 1) {
                        bl = true;
                    }
                    this.setVoiceCallForwardingFlag(1, bl, cfu.mSetCfNumber);
                }
                if (cfu.mOnComplete == null) break;
                AsyncResult.forMessage((Message)cfu.mOnComplete, (Object)asyncResult.result, (Throwable)asyncResult.exception);
                cfu.mOnComplete.sendToTarget();
                break;
            }
            case 10: {
                object = (AsyncResult)((Message)object).obj;
                if (((AsyncResult)object).exception != null) break;
                this.mImeiSv = (String)((AsyncResult)object).result;
                break;
            }
            case 9: {
                object = (AsyncResult)((Message)object).obj;
                if (((AsyncResult)object).exception != null) break;
                this.mImei = (String)((AsyncResult)object).result;
                break;
            }
            case 8: {
                this.logd("Event EVENT_RADIO_OFF_OR_NOT_AVAILABLE Received");
                this.handleRadioOffOrNotAvailable();
                break;
            }
            case 7: {
                object = (String[])((AsyncResult)object.obj).result;
                if (((String[])object).length <= 1) break;
                try {
                    this.onIncomingUSSD(Integer.parseInt((String)object[0]), (String)object[1]);
                }
                catch (NumberFormatException numberFormatException) {
                    Rlog.w((String)LOG_TAG, (String)"error parsing USSD");
                }
                break;
            }
            case 6: {
                AsyncResult asyncResult = (AsyncResult)((Message)object).obj;
                if (asyncResult.exception != null) break;
                object = new StringBuilder();
                ((StringBuilder)object).append("Baseband version: ");
                ((StringBuilder)object).append(asyncResult.result);
                this.logd(((StringBuilder)object).toString());
                TelephonyManager.from((Context)this.mContext).setBasebandVersionForPhone(this.getPhoneId(), (String)asyncResult.result);
                break;
            }
            case 5: {
                this.logd("Event EVENT_RADIO_ON Received");
                this.handleRadioOn();
                break;
            }
            case 3: {
                this.updateCurrentCarrierInProvider();
                object = this.getVmSimImsi();
                String string = this.getSubscriberId();
                if (!(this.isPhoneTypeGsm() && object == null || string == null || string.equals(object))) {
                    this.storeVoiceMailNumber(null);
                    this.setVmSimImsi(null);
                }
                this.updateVoiceMail();
                this.mSimRecordsLoadedRegistrants.notifyRegistrants();
                break;
            }
            case 2: {
                this.logd("Event EVENT_SSN Received");
                if (!this.isPhoneTypeGsm()) break;
                AsyncResult asyncResult = (AsyncResult)((Message)object).obj;
                object = (SuppServiceNotification)asyncResult.result;
                this.mSsnRegistrants.notifyRegistrants(asyncResult);
                break;
            }
            case 1: {
                this.handleRadioAvailable();
            }
        }
    }

    @Override
    public boolean handlePinMmi(String object) {
        object = this.isPhoneTypeGsm() ? GsmMmiCode.newFromDialString((String)object, this, (UiccCardApplication)this.mUiccApplication.get()) : CdmaMmiCode.newFromDialString((String)object, this, (UiccCardApplication)this.mUiccApplication.get());
        if (object != null && object.isPinPukCommand()) {
            this.mPendingMMIs.add((MmiCode)object);
            this.mMmiRegistrants.notifyRegistrants(new AsyncResult(null, object, null));
            try {
                object.processCode();
            }
            catch (CallStateException callStateException) {
                // empty catch block
            }
            return true;
        }
        this.loge("Mmi is null or unrecognized!");
        return false;
    }

    public void handleTimerInEmergencyCallbackMode(int n) {
        if (n != 0) {
            if (n != 1) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("handleTimerInEmergencyCallbackMode, unsupported action ");
                stringBuilder.append(n);
                Rlog.e((String)LOG_TAG, (String)stringBuilder.toString());
            } else {
                this.removeCallbacks(this.mExitEcmRunnable);
                this.mEcmTimerResetRegistrants.notifyResult((Object)Boolean.TRUE);
            }
        } else {
            long l = SystemProperties.getLong((String)"ro.cdma.ecmexittimer", (long)300000L);
            this.postDelayed(this.mExitEcmRunnable, l);
            this.mEcmTimerResetRegistrants.notifyResult((Object)Boolean.FALSE);
        }
    }

    @Override
    public boolean handleUssdRequest(String string, ResultReceiver object) {
        if (this.isPhoneTypeGsm() && this.mPendingMMIs.size() <= 0) {
            Object object2 = this.mImsPhone;
            if (object2 != null && (object2.getServiceState().getState() == 0 || ((Phone)object2).isUtEnabled())) {
                try {
                    this.logd("handleUssdRequest: attempting over IMS");
                    boolean bl = object2.handleUssdRequest(string, (ResultReceiver)object);
                    return bl;
                }
                catch (CallStateException callStateException) {
                    if (!"cs_fallback".equals(callStateException.getMessage())) {
                        return false;
                    }
                    this.logd("handleUssdRequest: fallback to CS required");
                }
            }
            try {
                object2 = new PhoneInternalInterface.DialArgs.Builder();
                this.dialInternal(string, ((PhoneInternalInterface.DialArgs.Builder)object2).build(), (ResultReceiver)object);
                return true;
            }
            catch (Exception exception) {
                object = new StringBuilder();
                ((StringBuilder)object).append("handleUssdRequest: exception");
                ((StringBuilder)object).append(exception);
                this.logd(((StringBuilder)object).toString());
                return false;
            }
        }
        this.sendUssdResponse(string, null, -1, (ResultReceiver)object);
        return true;
    }

    @Override
    public boolean isCdmaSubscriptionAppPresent() {
        UiccCardApplication uiccCardApplication = this.mUiccController.getUiccCardApplication(this.mPhoneId, 2);
        boolean bl = uiccCardApplication != null && (uiccCardApplication.getType() == IccCardApplicationStatus.AppType.APPTYPE_CSIM || uiccCardApplication.getType() == IccCardApplicationStatus.AppType.APPTYPE_RUIM);
        return bl;
    }

    @Override
    public boolean isCspPlmnEnabled() {
        IccRecords iccRecords = (IccRecords)this.mIccRecords.get();
        boolean bl = iccRecords != null ? iccRecords.isCspPlmnEnabled() : false;
        return bl;
    }

    @UnsupportedAppUsage
    public boolean isInCall() {
        Call.State state = this.getForegroundCall().getState();
        Call.State state2 = this.getBackgroundCall().getState();
        Call.State state3 = this.getRingingCall().getState();
        boolean bl = state.isAlive() || state2.isAlive() || state3.isAlive();
        return bl;
    }

    @Override
    public boolean isInEmergencyCall() {
        if (this.isPhoneTypeGsm()) {
            return false;
        }
        return this.mCT.isInEmergencyCall();
    }

    @Override
    public boolean isInEmergencySmsMode() {
        boolean bl = super.isInEmergencySmsMode() || this.mImsPhone != null && this.mImsPhone.isInEmergencySmsMode();
        return bl;
    }

    @Override
    public boolean isMinInfoReady() {
        return this.mSST.isMinInfoReady();
    }

    public boolean isNotificationOfWfcCallRequired(String string) {
        Object object = ((CarrierConfigManager)this.mContext.getSystemService("carrier_config")).getConfigForSubId(this.getSubId());
        boolean bl = true;
        boolean bl2 = object != null && object.getBoolean("notify_international_call_on_wfc_bool");
        if (!bl2) {
            return false;
        }
        object = this.mImsPhone;
        boolean bl3 = PhoneNumberUtils.isEmergencyNumber((int)this.getSubId(), (String)string);
        if (!this.isImsUseEnabled() || object == null || ((Phone)object).isVolteEnabled() || !((Phone)object).isWifiCallingEnabled() || bl3 || !PhoneNumberUtils.isInternationalNumber((String)string, (String)this.getCountryIso())) {
            bl = false;
        }
        return bl;
    }

    @Override
    public boolean isOtaSpNumber(String charSequence) {
        if (this.isPhoneTypeGsm()) {
            return super.isOtaSpNumber((String)charSequence);
        }
        boolean bl = false;
        if ((charSequence = PhoneNumberUtils.extractNetworkPortionAlt((String)charSequence)) != null) {
            boolean bl2;
            bl = bl2 = GsmCdmaPhone.isIs683OtaSpDialStr((String)charSequence);
            if (!bl2) {
                bl = this.isCarrierOtaSpNum((String)charSequence);
            }
        }
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append("isOtaSpNumber ");
        ((StringBuilder)charSequence).append(bl);
        Rlog.d((String)LOG_TAG, (String)((StringBuilder)charSequence).toString());
        return bl;
    }

    public boolean isPhoneTypeCdma() {
        boolean bl = this.mPrecisePhoneType == 2;
        return bl;
    }

    public boolean isPhoneTypeCdmaLte() {
        boolean bl = this.mPrecisePhoneType == 6;
        return bl;
    }

    @UnsupportedAppUsage
    public boolean isPhoneTypeGsm() {
        int n = this.mPrecisePhoneType;
        boolean bl = true;
        if (n != 1) {
            bl = false;
        }
        return bl;
    }

    @Override
    public boolean isUserDataEnabled() {
        if (this.mDataEnabledSettings.isProvisioning()) {
            return this.mDataEnabledSettings.isProvisioningDataEnabled();
        }
        return this.mDataEnabledSettings.isUserDataEnabled();
    }

    @Override
    public boolean isUtEnabled() {
        Phone phone = this.mImsPhone;
        if (phone != null) {
            return phone.isUtEnabled();
        }
        this.logd("isUtEnabled: called for GsmCdma");
        return false;
    }

    @Override
    public boolean needsOtaServiceProvisioning() {
        boolean bl = this.isPhoneTypeGsm();
        boolean bl2 = false;
        if (bl) {
            return false;
        }
        if (this.mSST.getOtasp() != 3) {
            bl2 = true;
        }
        return bl2;
    }

    @Override
    public void notifyCallForwardingIndicator() {
        this.mNotifier.notifyCallForwardingChanged(this);
    }

    public void notifyDisconnect(Connection connection) {
        this.mDisconnectRegistrants.notifyResult((Object)connection);
        this.mNotifier.notifyDisconnectCause(this, connection.getDisconnectCause(), connection.getPreciseDisconnectCause());
    }

    public void notifyEcbmTimerReset(Boolean bl) {
        this.mEcmTimerResetRegistrants.notifyResult((Object)bl);
    }

    public void notifyEmergencyCallRegistrants(boolean bl) {
        this.mEmergencyCallToggledRegistrants.notifyResult((Object)((int)bl));
    }

    public void notifyLocationChanged(CellLocation cellLocation) {
        this.mNotifier.notifyCellLocation(this, cellLocation);
    }

    public void notifyNewRingingConnection(Connection connection) {
        super.notifyNewRingingConnectionP(connection);
    }

    public void notifyPhoneStateChanged() {
        this.mNotifier.notifyPhoneState(this);
    }

    @UnsupportedAppUsage
    public void notifyPreciseCallStateChanged() {
        super.notifyPreciseCallStateChangedP();
    }

    @UnsupportedAppUsage
    public void notifyServiceStateChanged(ServiceState serviceState) {
        super.notifyServiceStateChangedP(serviceState);
    }

    public void notifySuppServiceFailed(PhoneInternalInterface.SuppService suppService) {
        this.mSuppServiceFailedRegistrants.notifyResult((Object)suppService);
    }

    public void notifyUnknownConnection(Connection connection) {
        super.notifyUnknownConnectionP(connection);
    }

    public void onMMIDone(MmiCode mmiCode) {
        if (!(this.mPendingMMIs.remove(mmiCode) || this.isPhoneTypeGsm() && (mmiCode.isUssdRequest() || ((GsmMmiCode)mmiCode).isSsInfo()))) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("onMMIDone: invalid response or already handled; ignoring: ");
            stringBuilder.append(mmiCode);
            Rlog.i((String)LOG_TAG, (String)stringBuilder.toString());
        } else {
            Object object = mmiCode.getUssdCallbackReceiver();
            if (object != null) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("onMMIDone: invoking callback: ");
                stringBuilder.append(mmiCode);
                Rlog.i((String)LOG_TAG, (String)stringBuilder.toString());
                int n = mmiCode.getState() == MmiCode.State.COMPLETE ? 100 : -1;
                this.sendUssdResponse(mmiCode.getDialString(), mmiCode.getMessage(), n, (ResultReceiver)object);
            } else {
                object = new StringBuilder();
                ((StringBuilder)object).append("onMMIDone: notifying registrants: ");
                ((StringBuilder)object).append(mmiCode);
                Rlog.i((String)LOG_TAG, (String)((StringBuilder)object).toString());
                this.mMmiCompleteRegistrants.notifyRegistrants(new AsyncResult(null, (Object)mmiCode, null));
            }
        }
    }

    @Override
    protected void onUpdateIccAvailability() {
        Object object;
        Object object2;
        if (this.mUiccController == null) {
            return;
        }
        if (this.isPhoneTypeGsm() || this.isPhoneTypeCdmaLte()) {
            object = this.mUiccController.getUiccCardApplication(this.mPhoneId, 3);
            object2 = null;
            if (object != null) {
                object2 = (IsimUiccRecords)((UiccCardApplication)object).getIccRecords();
                this.logd("New ISIM application found");
            }
            this.mIsimUiccRecords = object2;
        }
        if ((object2 = this.mSimRecords) != null) {
            ((IccRecords)object2).unregisterForRecordsLoaded(this);
        }
        if (!this.isPhoneTypeCdmaLte() && !this.isPhoneTypeCdma()) {
            this.mSimRecords = null;
        } else {
            object = this.mUiccController.getUiccCardApplication(this.mPhoneId, 1);
            object2 = null;
            if (object != null) {
                object2 = (SIMRecords)((UiccCardApplication)object).getIccRecords();
            }
            if ((object2 = (this.mSimRecords = object2)) != null) {
                ((IccRecords)object2).registerForRecordsLoaded(this, 3, null);
            }
        }
        object2 = object = this.getUiccCardApplication();
        if (!this.isPhoneTypeGsm()) {
            object2 = object;
            if (object == null) {
                this.logd("can't find 3GPP2 application; trying APP_FAM_3GPP");
                object2 = this.mUiccController.getUiccCardApplication(this.mPhoneId, 1);
            }
        }
        if ((object = (UiccCardApplication)this.mUiccApplication.get()) != object2) {
            if (object != null) {
                this.logd("Removing stale icc objects.");
                if (this.mIccRecords.get() != null) {
                    this.unregisterForIccRecordEvents();
                    this.mIccPhoneBookIntManager.updateIccRecords(null);
                }
                this.mIccRecords.set(null);
                this.mUiccApplication.set(null);
            }
            if (object2 != null) {
                object = new StringBuilder();
                ((StringBuilder)object).append("New Uicc application found. type = ");
                ((StringBuilder)object).append((Object)((UiccCardApplication)object2).getType());
                this.logd(((StringBuilder)object).toString());
                object = ((UiccCardApplication)object2).getIccRecords();
                this.mUiccApplication.set(object2);
                this.mIccRecords.set(object);
                this.registerForIccRecordEvents();
                this.mIccPhoneBookIntManager.updateIccRecords((IccRecords)object);
                if (object != null) {
                    object = ((IccRecords)object).getOperatorNumeric();
                    object2 = new StringBuilder();
                    ((StringBuilder)object2).append("New simOperatorNumeric = ");
                    ((StringBuilder)object2).append((String)object);
                    this.logd(((StringBuilder)object2).toString());
                    if (!TextUtils.isEmpty((CharSequence)object)) {
                        TelephonyManager.from((Context)this.mContext).setSimOperatorNumericForPhone(this.mPhoneId, (String)object);
                    }
                }
                this.updateDataConnectionTracker();
            }
        }
    }

    @Override
    public void registerForCallWaiting(Handler handler, int n, Object object) {
        this.mCT.registerForCallWaiting(handler, n, object);
    }

    @Override
    public void registerForCdmaOtaStatusChange(Handler handler, int n, Object object) {
        this.mCi.registerForCdmaOtaProvision(handler, n, object);
    }

    @Override
    public void registerForEcmTimerReset(Handler handler, int n, Object object) {
        this.mEcmTimerResetRegistrants.addUnique(handler, n, object);
    }

    @Override
    public void registerForSimRecordsLoaded(Handler handler, int n, Object object) {
        this.mSimRecordsLoadedRegistrants.addUnique(handler, n, object);
    }

    @Override
    public void registerForSubscriptionInfoReady(Handler handler, int n, Object object) {
        this.mSST.registerForSubscriptionInfoReady(handler, n, object);
    }

    @Override
    public void registerForSuppServiceNotification(Handler handler, int n, Object object) {
        this.mSsnRegistrants.addUnique(handler, n, object);
        if (this.mSsnRegistrants.size() == 1) {
            this.mCi.setSuppServiceNotifications(true, null);
        }
    }

    @Override
    public void rejectCall() throws CallStateException {
        this.mCT.rejectCall();
    }

    @Override
    public void resetCarrierKeysForImsiEncryption() {
        this.mCIM.resetCarrierKeysForImsiEncryption(this.mContext, this.mPhoneId);
    }

    @Override
    public void resolveSubscriptionCarrierId(String string) {
        this.mCarrierResolver.resolveSubscriptionCarrierId(string);
    }

    @Override
    public void sendBurstDtmf(String string, int n, int n2, Message message) {
        if (this.isPhoneTypeGsm()) {
            this.loge("[GsmCdmaPhone] sendBurstDtmf() is a CDMA method");
        } else {
            boolean bl;
            boolean bl2 = true;
            int n3 = 0;
            do {
                bl = bl2;
                if (n3 >= string.length()) break;
                if (!PhoneNumberUtils.is12Key((char)string.charAt(n3))) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("sendDtmf called with invalid character '");
                    stringBuilder.append(string.charAt(n3));
                    stringBuilder.append("'");
                    Rlog.e((String)LOG_TAG, (String)stringBuilder.toString());
                    bl = false;
                    break;
                }
                ++n3;
            } while (true);
            if (this.mCT.mState == PhoneConstants.State.OFFHOOK && bl) {
                this.mCi.sendBurstDtmf(string, n, n2, message);
            }
        }
    }

    @Override
    public void sendDtmf(char c) {
        if (!PhoneNumberUtils.is12Key((char)c)) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("sendDtmf called with invalid character '");
            stringBuilder.append(c);
            stringBuilder.append("'");
            this.loge(stringBuilder.toString());
        } else if (this.mCT.mState == PhoneConstants.State.OFFHOOK) {
            this.mCi.sendDtmf(c, null);
        }
    }

    @Override
    public void sendEmergencyCallStateChange(boolean bl) {
        if (!this.isPhoneTypeCdma()) {
            this.logi("sendEmergencyCallbackModeChange - skip for non-cdma");
            return;
        }
        if (this.mBroadcastEmergencyCallStateChanges) {
            Object object = new Intent("android.intent.action.EMERGENCY_CALL_STATE_CHANGED");
            object.putExtra("phoneInEmergencyCall", bl);
            SubscriptionManager.putPhoneIdAndSubIdExtra((Intent)object, (int)this.getPhoneId());
            ActivityManager.broadcastStickyIntent((Intent)object, (int)-1);
            object = new StringBuilder();
            ((StringBuilder)object).append("sendEmergencyCallStateChange: callActive ");
            ((StringBuilder)object).append(bl);
            Rlog.d((String)LOG_TAG, (String)((StringBuilder)object).toString());
        }
    }

    @Override
    public void sendUssdResponse(String string) {
        if (this.isPhoneTypeGsm()) {
            GsmMmiCode gsmMmiCode = GsmMmiCode.newFromUssdUserInput(string, this, (UiccCardApplication)this.mUiccApplication.get());
            this.mPendingMMIs.add(gsmMmiCode);
            this.mMmiRegistrants.notifyRegistrants(new AsyncResult(null, (Object)gsmMmiCode, null));
            gsmMmiCode.sendUssd(string);
        } else {
            this.loge("sendUssdResponse: not possible in CDMA");
        }
    }

    @Override
    public void setBroadcastEmergencyCallStateChanges(boolean bl) {
        this.mBroadcastEmergencyCallStateChanges = bl;
    }

    @Override
    public void setCallBarring(String string, boolean bl, String string2, Message message, int n) {
        if (this.isPhoneTypeGsm()) {
            Phone phone = this.mImsPhone;
            if (phone != null && phone.isUtEnabled()) {
                phone.setCallBarring(string, bl, string2, message, n);
                return;
            }
            this.mCi.setFacilityLock(string, bl, string2, n, message);
        } else {
            this.loge("setCallBarringOption: not possible in CDMA");
        }
    }

    @Override
    public void setCallForwardingOption(int n, int n2, String string, int n3, Message object) {
        if (!this.isPhoneTypeGsm() && !this.isImsUtEnabledOverCdma()) {
            this.loge("setCallForwardingOption: not possible in CDMA without IMS");
        } else {
            Phone phone = this.mImsPhone;
            if (phone != null && (phone.getServiceState().getState() == 0 || phone.isUtEnabled())) {
                phone.setCallForwardingOption(n, n2, string, n3, object);
                return;
            }
            if (this.isValidCommandInterfaceCFAction(n) && this.isValidCommandInterfaceCFReason(n2)) {
                if (n2 == 0) {
                    object = new Cfu(string, (Message)object);
                    object = this.obtainMessage(12, (int)this.isCfEnable(n), 0, object);
                }
                this.mCi.setCallForward(n, n2, 1, string, n3, (Message)object);
            }
        }
    }

    @Override
    public void setCallWaiting(boolean bl, Message message) {
        if (!this.isPhoneTypeGsm() && !this.isImsUtEnabledOverCdma()) {
            this.loge("method setCallWaiting is NOT supported in CDMA without IMS!");
        } else {
            Phone phone = this.mImsPhone;
            if (phone != null && (phone.getServiceState().getState() == 0 || phone.isUtEnabled())) {
                phone.setCallWaiting(bl, message);
                return;
            }
            int n = 1;
            phone = ((CarrierConfigManager)this.getContext().getSystemService("carrier_config")).getConfigForSubId(this.getSubId());
            if (phone != null) {
                n = phone.getInt("call_waiting_service_class_int", 1);
            }
            this.mCi.setCallWaiting(bl, n, message);
        }
    }

    @Override
    public void setCarrierInfoForImsiEncryption(ImsiEncryptionInfo imsiEncryptionInfo) {
        CarrierInfoManager.setCarrierInfoForImsiEncryption(imsiEncryptionInfo, this.mContext, this.mPhoneId);
    }

    @Override
    public void setCarrierTestOverride(String string, String string2, String string3, String string4, String string5, String string6, String string7, String object, String string8) {
        this.mCarrierResolver.setTestOverrideApn(string8);
        this.mCarrierResolver.setTestOverrideCarrierPriviledgeRule((String)object);
        object = null;
        if (this.isPhoneTypeGsm()) {
            object = (IccRecords)this.mIccRecords.get();
        } else if (this.isPhoneTypeCdmaLte()) {
            object = this.mSimRecords;
        } else {
            this.loge("setCarrierTestOverride fails in CDMA only");
        }
        if (object != null) {
            ((IccRecords)object).setCarrierTestOverride(string, string2, string3, string4, string5, string6, string7);
        }
    }

    @Override
    public void setCellBroadcastSmsConfig(int[] arrn, Message message) {
        this.loge("[GsmCdmaPhone] setCellBroadcastSmsConfig() is obsolete; use SmsManager");
        message.sendToTarget();
    }

    @Override
    public void setDataRoamingEnabled(boolean bl) {
        if (this.getDcTracker(1) != null) {
            this.getDcTracker(1).setDataRoamingEnabledByUser(bl);
        }
    }

    @Override
    public void setImsRegistrationState(boolean bl) {
        this.mSST.setImsRegistrationState(bl);
    }

    @Override
    protected void setIsInEmergencyCall() {
        if (!this.isPhoneTypeGsm()) {
            this.mCT.setIsInEmergencyCall();
        }
    }

    @Override
    public boolean setLine1Number(String string, String string2, Message message) {
        if (this.isPhoneTypeGsm()) {
            IccRecords iccRecords = (IccRecords)this.mIccRecords.get();
            if (iccRecords != null) {
                iccRecords.setMsisdnNumber(string, string2, message);
                return true;
            }
            return false;
        }
        this.loge("setLine1Number: not possible in CDMA");
        return false;
    }

    @Override
    public void setLinkCapacityReportingCriteria(int[] arrn, int[] arrn2, int n) {
        this.mCi.setLinkCapacityReportingCriteria(3000, 50, 50, arrn, arrn2, n, null);
    }

    @Override
    public void setMute(boolean bl) {
        this.mCT.setMute(bl);
    }

    @UnsupportedAppUsage
    @Override
    public void setOnEcbModeExitResponse(Handler handler, int n, Object object) {
        this.mEcmExitRespRegistrant = new Registrant(handler, n, object);
    }

    @Override
    public boolean setOperatorBrandOverride(String string) {
        if (this.mUiccController == null) {
            return false;
        }
        UiccCard uiccCard = this.mUiccController.getUiccCard(this.getPhoneId());
        if (uiccCard == null) {
            return false;
        }
        boolean bl = uiccCard.setOperatorBrandOverride(string);
        if (bl) {
            TelephonyManager.from((Context)this.mContext).setSimOperatorNameForPhone(this.getPhoneId(), this.mSST.getServiceProviderName());
            this.mSST.pollState();
        }
        return bl;
    }

    @Override
    public void setOutgoingCallerIdDisplay(int n, Message message) {
        if (this.isPhoneTypeGsm()) {
            Phone phone = this.mImsPhone;
            if (phone != null && (phone.getServiceState().getState() == 0 || phone.isUtEnabled())) {
                phone.setOutgoingCallerIdDisplay(n, message);
                return;
            }
            this.mCi.setCLIR(n, this.obtainMessage(18, n, 0, (Object)message));
        } else {
            this.loge("setOutgoingCallerIdDisplay: not possible in CDMA");
        }
    }

    @Override
    public void setRadioPower(boolean bl) {
        this.mSST.setRadioPower(bl);
    }

    @Override
    public void setSignalStrengthReportingCriteria(int[] arrn, int n) {
        this.mCi.setSignalStrengthReportingCriteria(3000, 2, arrn, n, null);
    }

    @Override
    public void setTTYMode(int n, Message message) {
        super.setTTYMode(n, message);
        if (this.mImsPhone != null) {
            this.mImsPhone.setTTYMode(n, message);
        }
    }

    @Override
    public void setUiTTYMode(int n, Message message) {
        if (this.mImsPhone != null) {
            this.mImsPhone.setUiTTYMode(n, message);
        }
    }

    @Override
    public void setVoiceMailNumber(String string, String object, Message object2) {
        this.mVmNumber = object;
        Message message = this.obtainMessage(20, 0, 0, object2);
        object2 = (IccRecords)this.mIccRecords.get();
        object = object2;
        if (!this.isPhoneTypeGsm()) {
            object = object2;
            if (this.mSimRecords != null) {
                object = this.mSimRecords;
            }
        }
        if (object != null) {
            ((IccRecords)object).setVoiceMailNumber(string, this.mVmNumber, message);
        }
    }

    @Override
    public void setVoiceMessageWaiting(int n, int n2) {
        if (this.isPhoneTypeGsm()) {
            IccRecords iccRecords = (IccRecords)this.mIccRecords.get();
            if (iccRecords != null) {
                iccRecords.setVoiceMessageWaiting(n, n2);
            } else {
                this.logd("SIM Records not found, MWI not updated");
            }
        } else {
            this.setVoiceMessageCount(n2);
        }
    }

    public boolean shouldForceAutoNetworkSelect() {
        int n = Phone.PREFERRED_NT_MODE;
        int n2 = this.getSubId();
        if (!SubscriptionManager.isValidSubscriptionId((int)n2)) {
            return false;
        }
        ContentResolver contentResolver = this.mContext.getContentResolver();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("preferred_network_mode");
        stringBuilder.append(n2);
        n2 = Settings.Global.getInt((ContentResolver)contentResolver, (String)stringBuilder.toString(), (int)n);
        stringBuilder = new StringBuilder();
        stringBuilder.append("shouldForceAutoNetworkSelect in mode = ");
        stringBuilder.append(n2);
        this.logd(stringBuilder.toString());
        if (this.isManualSelProhibitedInGlobalMode() && (n2 == 10 || n2 == 7)) {
            stringBuilder = new StringBuilder();
            stringBuilder.append("Should force auto network select mode = ");
            stringBuilder.append(n2);
            this.logd(stringBuilder.toString());
            return true;
        }
        stringBuilder = new StringBuilder();
        stringBuilder.append("Should not force auto network select mode = ");
        stringBuilder.append(n2);
        this.logd(stringBuilder.toString());
        return false;
    }

    @Override
    public void startDtmf(char c) {
        if (!PhoneNumberUtils.is12Key((char)c)) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("startDtmf called with invalid character '");
            stringBuilder.append(c);
            stringBuilder.append("'");
            this.loge(stringBuilder.toString());
        } else {
            this.mCi.startDtmf(c, null);
        }
    }

    @Override
    public void startNetworkScan(NetworkScanRequest networkScanRequest, Message message) {
        this.mCi.startNetworkScan(networkScanRequest, message);
    }

    @Override
    public void stopDtmf() {
        this.mCi.stopDtmf(null);
    }

    @Override
    public void stopNetworkScan(Message message) {
        this.mCi.stopNetworkScan(message);
    }

    public boolean supports3gppCallForwardingWhileRoaming() {
        PersistableBundle persistableBundle = ((CarrierConfigManager)this.getContext().getSystemService("carrier_config")).getConfigForSubId(this.getSubId());
        if (persistableBundle != null) {
            return persistableBundle.getBoolean("support_3gpp_call_forwarding_while_roaming_bool", true);
        }
        return true;
    }

    @Override
    public void switchHoldingAndActive() throws CallStateException {
        this.mCT.switchWaitingOrHoldingAndActive();
    }

    @Override
    public void unregisterForCallWaiting(Handler handler) {
        this.mCT.unregisterForCallWaiting(handler);
    }

    @Override
    public void unregisterForCdmaOtaStatusChange(Handler handler) {
        this.mCi.unregisterForCdmaOtaProvision(handler);
    }

    @Override
    public void unregisterForEcmTimerReset(Handler handler) {
        this.mEcmTimerResetRegistrants.remove(handler);
    }

    @Override
    public void unregisterForSimRecordsLoaded(Handler handler) {
        this.mSimRecordsLoadedRegistrants.remove(handler);
    }

    @Override
    public void unregisterForSubscriptionInfoReady(Handler handler) {
        this.mSST.unregisterForSubscriptionInfoReady(handler);
    }

    @Override
    public void unregisterForSuppServiceNotification(Handler handler) {
        this.mSsnRegistrants.remove(handler);
        if (this.mSsnRegistrants.size() == 0) {
            this.mCi.setSuppServiceNotifications(false, null);
        }
    }

    @Override
    public void unsetOnEcbModeExitResponse(Handler handler) {
        this.mEcmExitRespRegistrant.clear();
    }

    @Override
    public boolean updateCurrentCarrierInProvider() {
        long l = SubscriptionManager.getDefaultDataSubscriptionId();
        String string = this.getOperatorNumeric();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("updateCurrentCarrierInProvider: mSubId = ");
        stringBuilder.append(this.getSubId());
        stringBuilder.append(" currentDds = ");
        stringBuilder.append(l);
        stringBuilder.append(" operatorNumeric = ");
        stringBuilder.append(string);
        this.logd(stringBuilder.toString());
        if (!TextUtils.isEmpty((CharSequence)string) && (long)this.getSubId() == l) {
            try {
                stringBuilder = Uri.withAppendedPath((Uri)Telephony.Carriers.CONTENT_URI, (String)"current");
                ContentValues contentValues = new ContentValues();
                contentValues.put("numeric", string);
                this.mContext.getContentResolver().insert((Uri)stringBuilder, contentValues);
                return true;
            }
            catch (SQLException sQLException) {
                Rlog.e((String)LOG_TAG, (String)"Can't store current operator", (Throwable)sQLException);
            }
        }
        return false;
    }

    @Override
    public void updatePhoneObject(int n) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("updatePhoneObject: radioTechnology=");
        stringBuilder.append(n);
        this.logd(stringBuilder.toString());
        this.sendMessage(this.obtainMessage(42, n, 0, null));
    }

    @Override
    public void updateServiceLocation() {
        this.mSST.enableSingleLocationUpdate();
    }

    @Override
    public void updateVoiceMail() {
        if (this.isPhoneTypeGsm()) {
            int n = 0;
            Object object = (IccRecords)this.mIccRecords.get();
            if (object != null) {
                n = ((IccRecords)object).getVoiceMessageCount();
            }
            int n2 = n;
            if (n == -2) {
                n2 = this.getStoredVoiceMessageCount();
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("updateVoiceMail countVoiceMessages = ");
            ((StringBuilder)object).append(n2);
            ((StringBuilder)object).append(" subId ");
            ((StringBuilder)object).append(this.getSubId());
            this.logd(((StringBuilder)object).toString());
            this.setVoiceMessageCount(n2);
        } else {
            this.setVoiceMessageCount(this.getStoredVoiceMessageCount());
        }
    }

    private static class Cfu {
        final Message mOnComplete;
        final String mSetCfNumber;

        @UnsupportedAppUsage
        Cfu(String string, Message message) {
            this.mSetCfNumber = string;
            this.mOnComplete = message;
        }
    }

}

