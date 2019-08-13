/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.annotation.UnsupportedAppUsage
 *  android.app.ActivityManager
 *  android.app.Notification
 *  android.app.Notification$BigTextStyle
 *  android.app.Notification$Builder
 *  android.app.Notification$Style
 *  android.app.NotificationManager
 *  android.app.PendingIntent
 *  android.content.BroadcastReceiver
 *  android.content.Context
 *  android.content.Intent
 *  android.content.res.Resources
 *  android.net.LinkProperties
 *  android.net.NetworkStats
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
 *  android.telecom.Connection
 *  android.telecom.Connection$RttTextStream
 *  android.telephony.CallQuality
 *  android.telephony.CarrierConfigManager
 *  android.telephony.NetworkRegistrationInfo
 *  android.telephony.NetworkScanRequest
 *  android.telephony.PhoneNumberUtils
 *  android.telephony.Rlog
 *  android.telephony.ServiceState
 *  android.telephony.SignalStrength
 *  android.telephony.SubscriptionManager
 *  android.telephony.UssdResponse
 *  android.telephony.ims.ImsCallForwardInfo
 *  android.telephony.ims.ImsReasonInfo
 *  android.telephony.ims.ImsSsInfo
 *  android.text.TextUtils
 *  com.android.ims.ImsEcbm
 *  com.android.ims.ImsEcbmStateListener
 *  com.android.ims.ImsException
 *  com.android.ims.ImsManager
 *  com.android.ims.ImsUtInterface
 *  com.android.internal.annotations.VisibleForTesting
 *  com.android.internal.telephony.OperatorInfo
 *  com.android.internal.telephony.PhoneConstants
 *  com.android.internal.telephony.PhoneConstants$DataState
 *  com.android.internal.telephony.PhoneConstants$State
 */
package com.android.internal.telephony.imsphone;

import android.annotation.UnsupportedAppUsage;
import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.LinkProperties;
import android.net.NetworkStats;
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
import android.telecom.Connection;
import android.telephony.CallQuality;
import android.telephony.CarrierConfigManager;
import android.telephony.NetworkRegistrationInfo;
import android.telephony.NetworkScanRequest;
import android.telephony.PhoneNumberUtils;
import android.telephony.Rlog;
import android.telephony.ServiceState;
import android.telephony.SignalStrength;
import android.telephony.SubscriptionManager;
import android.telephony.UssdResponse;
import android.telephony.ims.ImsCallForwardInfo;
import android.telephony.ims.ImsReasonInfo;
import android.telephony.ims.ImsSsInfo;
import android.text.TextUtils;
import com.android.ims.ImsEcbm;
import com.android.ims.ImsEcbmStateListener;
import com.android.ims.ImsException;
import com.android.ims.ImsManager;
import com.android.ims.ImsUtInterface;
import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.telephony.Call;
import com.android.internal.telephony.CallForwardInfo;
import com.android.internal.telephony.CallStateException;
import com.android.internal.telephony.CallTracker;
import com.android.internal.telephony.CommandException;
import com.android.internal.telephony.Connection;
import com.android.internal.telephony.GsmCdmaPhone;
import com.android.internal.telephony.IccCard;
import com.android.internal.telephony.IccPhoneBookInterfaceManager;
import com.android.internal.telephony.MmiCode;
import com.android.internal.telephony.OperatorInfo;
import com.android.internal.telephony.Phone;
import com.android.internal.telephony.PhoneConstants;
import com.android.internal.telephony.PhoneInternalInterface;
import com.android.internal.telephony.PhoneNotifier;
import com.android.internal.telephony.ServiceStateTracker;
import com.android.internal.telephony.TelephonyComponentFactory;
import com.android.internal.telephony.UUSInfo;
import com.android.internal.telephony.dataconnection.DataConnection;
import com.android.internal.telephony.dataconnection.TransportManager;
import com.android.internal.telephony.emergency.EmergencyNumberTracker;
import com.android.internal.telephony.gsm.GsmMmiCode;
import com.android.internal.telephony.gsm.SuppServiceNotification;
import com.android.internal.telephony.imsphone.ImsExternalCallTracker;
import com.android.internal.telephony.imsphone.ImsPhoneBase;
import com.android.internal.telephony.imsphone.ImsPhoneCall;
import com.android.internal.telephony.imsphone.ImsPhoneCallTracker;
import com.android.internal.telephony.imsphone.ImsPhoneMmiCode;
import com.android.internal.telephony.imsphone.ImsPullCall;
import com.android.internal.telephony.uicc.IccFileHandler;
import com.android.internal.telephony.uicc.IccRecords;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ImsPhone
extends ImsPhoneBase {
    static final int CANCEL_ECM_TIMER = 1;
    private static final boolean DBG = true;
    private static final int DEFAULT_ECM_EXIT_TIMER_VALUE = 300000;
    private static final int EVENT_DEFAULT_PHONE_DATA_STATE_CHANGED = 59;
    private static final int EVENT_GET_CALL_BARRING_DONE = 54;
    private static final int EVENT_GET_CALL_WAITING_DONE = 56;
    private static final int EVENT_GET_CLIR_DONE = 58;
    @VisibleForTesting
    public static final int EVENT_SERVICE_STATE_CHANGED = 60;
    private static final int EVENT_SET_CALL_BARRING_DONE = 53;
    private static final int EVENT_SET_CALL_WAITING_DONE = 55;
    private static final int EVENT_SET_CLIR_DONE = 57;
    private static final int EVENT_VOICE_CALL_ENDED = 61;
    private static final String LOG_TAG = "ImsPhone";
    static final int RESTART_ECM_TIMER = 0;
    private static final boolean VDBG = false;
    @UnsupportedAppUsage
    ImsPhoneCallTracker mCT;
    private Uri[] mCurrentSubscriberUris;
    Phone mDefaultPhone;
    private Registrant mEcmExitRespRegistrant;
    private Runnable mExitEcmRunnable = new Runnable(){

        @Override
        public void run() {
            ImsPhone.this.exitEmergencyCallbackMode();
        }
    };
    ImsExternalCallTracker mExternalCallTracker;
    private ImsEcbmStateListener mImsEcbmStateListener = new ImsEcbmStateListener(){

        public void onECBMEntered() {
            ImsPhone.this.logd("onECBMEntered");
            ImsPhone.this.handleEnterEmergencyCallbackMode();
        }

        public void onECBMExited() {
            ImsPhone.this.logd("onECBMExited");
            ImsPhone.this.handleExitEmergencyCallbackMode();
        }
    };
    private boolean mImsRegistered = false;
    private String mLastDialString;
    @UnsupportedAppUsage
    private ArrayList<ImsPhoneMmiCode> mPendingMMIs = new ArrayList();
    private BroadcastReceiver mResultReceiver = new BroadcastReceiver(){

        public void onReceive(Context object, Intent object2) {
            if (this.getResultCode() == -1) {
                object = object2.getCharSequenceExtra("alertTitle");
                CharSequence charSequence = object2.getCharSequenceExtra("alertMessage");
                object2 = object2.getCharSequenceExtra("notificationMessage");
                Intent intent = new Intent("android.intent.action.MAIN");
                intent.setClassName("com.android.settings", "com.android.settings.Settings$WifiCallingSettingsActivity");
                intent.putExtra("alertShow", true);
                intent.putExtra("alertTitle", (CharSequence)object);
                intent.putExtra("alertMessage", charSequence);
                charSequence = PendingIntent.getActivity((Context)ImsPhone.this.mContext, (int)0, (Intent)intent, (int)134217728);
                object = new Notification.Builder(ImsPhone.this.mContext).setSmallIcon(17301642).setContentTitle((CharSequence)object).setContentText((CharSequence)object2).setAutoCancel(true).setContentIntent((PendingIntent)charSequence).setStyle((Notification.Style)new Notification.BigTextStyle().bigText((CharSequence)object2)).setChannelId("wfc").build();
                ((NotificationManager)ImsPhone.this.mContext.getSystemService("notification")).notify("wifi_calling", 1, (Notification)object);
            }
        }
    };
    private boolean mRoaming = false;
    @UnsupportedAppUsage
    private ServiceState mSS = new ServiceState();
    private final RegistrantList mSilentRedialRegistrants = new RegistrantList();
    private RegistrantList mSsnRegistrants = new RegistrantList();
    private PowerManager.WakeLock mWakeLock;

    public ImsPhone(Context context, PhoneNotifier phoneNotifier, Phone phone) {
        this(context, phoneNotifier, phone, false);
    }

    @VisibleForTesting
    public ImsPhone(Context arrn, PhoneNotifier phoneNotifier, Phone phone, boolean bl) {
        super(LOG_TAG, (Context)arrn, phoneNotifier, bl);
        this.mDefaultPhone = phone;
        this.mExternalCallTracker = TelephonyComponentFactory.getInstance().inject(ImsExternalCallTracker.class.getName()).makeImsExternalCallTracker(this);
        this.mCT = TelephonyComponentFactory.getInstance().inject(ImsPhoneCallTracker.class.getName()).makeImsPhoneCallTracker(this);
        this.mCT.registerPhoneStateListener(this.mExternalCallTracker);
        this.mExternalCallTracker.setCallPuller(this.mCT);
        this.mSS.setStateOff();
        this.mPhoneId = this.mDefaultPhone.getPhoneId();
        this.mWakeLock = ((PowerManager)arrn.getSystemService("power")).newWakeLock(1, LOG_TAG);
        this.mWakeLock.setReferenceCounted(false);
        if (this.mDefaultPhone.getServiceStateTracker() != null && this.mDefaultPhone.getTransportManager() != null) {
            for (int n : this.mDefaultPhone.getTransportManager().getAvailableTransports()) {
                this.mDefaultPhone.getServiceStateTracker().registerForDataRegStateOrRatChanged(n, this, 59, null);
            }
        }
        this.setServiceState(1);
        this.mDefaultPhone.registerForServiceStateChanged(this, 60, null);
    }

    private Connection dialInternal(String string, PhoneInternalInterface.DialArgs object, ResultReceiver object2) throws CallStateException {
        CharSequence charSequence = PhoneNumberUtils.stripSeparators((String)string);
        if (this.handleInCallMmiCommands((String)charSequence)) {
            return null;
        }
        object = !(object instanceof ImsDialArgs) ? ImsDialArgs.Builder.from((PhoneInternalInterface.DialArgs)object) : ImsDialArgs.Builder.from((ImsDialArgs)object);
        ((ImsDialArgs.Builder)object).setClirMode(this.mCT.getClirMode());
        if (this.mDefaultPhone.getPhoneType() == 2) {
            return this.mCT.dial(string, (ImsDialArgs)((ImsDialArgs.Builder)object).build());
        }
        charSequence = PhoneNumberUtils.extractNetworkPortionAlt((String)charSequence);
        object2 = ImsPhoneMmiCode.newFromDialString((String)charSequence, this, (ResultReceiver)object2);
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append("dialInternal: dialing w/ mmi '");
        ((StringBuilder)charSequence).append(object2);
        ((StringBuilder)charSequence).append("'...");
        this.logd(((StringBuilder)charSequence).toString());
        if (object2 == null) {
            return this.mCT.dial(string, (ImsDialArgs)((ImsDialArgs.Builder)object).build());
        }
        if (((ImsPhoneMmiCode)object2).isTemporaryModeCLIR()) {
            ((ImsDialArgs.Builder)object).setClirMode(((ImsPhoneMmiCode)object2).getCLIRMode());
            return this.mCT.dial(((ImsPhoneMmiCode)object2).getDialingNumber(), (ImsDialArgs)((ImsDialArgs.Builder)object).build());
        }
        if (((ImsPhoneMmiCode)object2).isSupportedOverImsPhone()) {
            CallStateException callStateException2;
            block7 : {
                this.mPendingMMIs.add((ImsPhoneMmiCode)object2);
                this.mMmiRegistrants.notifyRegistrants(new AsyncResult(null, object2, null));
                try {
                    ((ImsPhoneMmiCode)object2).processCode();
                }
                catch (CallStateException callStateException2) {
                    if ("cs_fallback".equals(callStateException2.getMessage())) break block7;
                }
                return null;
            }
            this.logi("dialInternal: fallback to GSM required.");
            this.mPendingMMIs.remove(object2);
            throw callStateException2;
        }
        this.logi("dialInternal: USSD not supported by IMS; fallback to CS.");
        throw new CallStateException("cs_fallback");
    }

    @UnsupportedAppUsage
    private int getActionFromCFAction(int n) {
        if (n != 0) {
            if (n != 1) {
                if (n != 3) {
                    if (n != 4) {
                        return -1;
                    }
                    return 4;
                }
                return 3;
            }
            return 1;
        }
        return 0;
    }

    private int getCBTypeFromFacility(String string) {
        if ("AO".equals(string)) {
            return 2;
        }
        if ("OI".equals(string)) {
            return 3;
        }
        if ("OX".equals(string)) {
            return 4;
        }
        if ("AI".equals(string)) {
            return 1;
        }
        if ("IR".equals(string)) {
            return 5;
        }
        if ("AB".equals(string)) {
            return 7;
        }
        if ("AG".equals(string)) {
            return 8;
        }
        if ("AC".equals(string)) {
            return 9;
        }
        return 0;
    }

    private int getCFReasonFromCondition(int n) {
        if (n != 0) {
            if (n != 1) {
                if (n != 2) {
                    if (n != 3) {
                        if (n != 4) {
                            if (n != 5) {
                                return 3;
                            }
                            return 5;
                        }
                        return 4;
                    }
                    return 3;
                }
                return 2;
            }
            return 1;
        }
        return 0;
    }

    private CallForwardInfo getCallForwardInfo(ImsCallForwardInfo imsCallForwardInfo) {
        CallForwardInfo callForwardInfo = new CallForwardInfo();
        callForwardInfo.status = imsCallForwardInfo.getStatus();
        callForwardInfo.reason = this.getCFReasonFromCondition(imsCallForwardInfo.getCondition());
        callForwardInfo.serviceClass = 1;
        callForwardInfo.toa = imsCallForwardInfo.getToA();
        callForwardInfo.number = imsCallForwardInfo.getNumber();
        callForwardInfo.timeSeconds = imsCallForwardInfo.getTimeSeconds();
        return callForwardInfo;
    }

    private CommandException getCommandException(int n, String string) {
        Object object = new StringBuilder();
        object.append("getCommandException code= ");
        object.append(n);
        object.append(", errorString= ");
        object.append(string);
        this.logd(object.toString());
        object = CommandException.Error.GENERIC_FAILURE;
        if (n != 241) {
            if (n != 801) {
                if (n != 802) {
                    switch (n) {
                        default: {
                            break;
                        }
                        case 825: {
                            object = CommandException.Error.SS_MODIFIED_TO_DIAL_VIDEO;
                            break;
                        }
                        case 824: {
                            object = CommandException.Error.SS_MODIFIED_TO_SS;
                            break;
                        }
                        case 823: {
                            object = CommandException.Error.SS_MODIFIED_TO_USSD;
                            break;
                        }
                        case 822: {
                            object = CommandException.Error.SS_MODIFIED_TO_DIAL;
                            break;
                        }
                        case 821: {
                            object = CommandException.Error.PASSWORD_INCORRECT;
                            break;
                        }
                    }
                } else {
                    object = CommandException.Error.RADIO_NOT_AVAILABLE;
                }
            } else {
                object = CommandException.Error.REQUEST_NOT_SUPPORTED;
            }
        } else {
            object = CommandException.Error.FDN_CHECK_FAILURE;
        }
        return new CommandException((CommandException.Error)((Object)object), string);
    }

    private CommandException getCommandException(Throwable throwable) {
        if (throwable instanceof ImsException) {
            throwable = this.getCommandException(((ImsException)throwable).getCode(), throwable.getMessage());
        } else {
            this.logd("getCommandException generic failure");
            throwable = new CommandException(CommandException.Error.GENERIC_FAILURE);
        }
        return throwable;
    }

    @UnsupportedAppUsage
    private int getConditionFromCFReason(int n) {
        if (n != 0) {
            if (n != 1) {
                if (n != 2) {
                    if (n != 3) {
                        if (n != 4) {
                            if (n != 5) {
                                return -1;
                            }
                            return 5;
                        }
                        return 4;
                    }
                    return 3;
                }
                return 2;
            }
            return 1;
        }
        return 0;
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
            try {
                this.mCT.hangup((ImsPhoneCall)this.getBackgroundCall());
            }
            catch (CallStateException callStateException) {
                Rlog.d((String)LOG_TAG, (String)"hangup failed", (Throwable)callStateException);
            }
        }
        return true;
    }

    private boolean handleCallHoldIncallSupplementaryService(String string) {
        int n = string.length();
        if (n > 2) {
            return false;
        }
        if (n > 1) {
            this.logd("separate not supported");
            this.notifySuppServiceFailed(PhoneInternalInterface.SuppService.SEPARATE);
        } else {
            try {
                if (this.getRingingCall().getState() != Call.State.IDLE) {
                    this.logd("MmiCode 2: accept ringing call");
                    this.mCT.acceptCall(2);
                } else {
                    this.logd("MmiCode 2: holdActiveCall");
                    this.mCT.holdActiveCall();
                }
            }
            catch (CallStateException callStateException) {
                Rlog.d((String)LOG_TAG, (String)"switch failed", (Throwable)callStateException);
                this.notifySuppServiceFailed(PhoneInternalInterface.SuppService.SWITCH);
            }
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
        var1_1 = this.getForegroundCall();
        if (var2_3 <= 1) ** GOTO lbl10
        try {
            this.logd("not support 1X SEND");
            this.notifySuppServiceFailed(PhoneInternalInterface.SuppService.HANGUP);
            return true;
lbl10: // 1 sources:
            if (var1_1.getState() != Call.State.IDLE) {
                this.logd("MmiCode 1: hangup foreground");
                this.mCT.hangup((ImsPhoneCall)var1_1);
                return true;
            }
            this.logd("MmiCode 1: holdActiveCallForWaitingCall");
            this.mCT.holdActiveCallForWaitingCall();
            return true;
        }
        catch (CallStateException var1_2) {
            Rlog.d((String)"ImsPhone", (String)"hangup failed", (Throwable)var1_2);
            this.notifySuppServiceFailed(PhoneInternalInterface.SuppService.HANGUP);
        }
        return true;
    }

    private int[] handleCbQueryResult(ImsSsInfo[] arrimsSsInfo) {
        int[] arrn = new int[]{0};
        if (arrimsSsInfo[0].getStatus() == 1) {
            arrn[0] = 1;
        }
        return arrn;
    }

    private boolean handleCcbsIncallSupplementaryService(String string) {
        if (string.length() > 1) {
            return false;
        }
        this.logi("MmiCode 5: CCBS not supported!");
        this.notifySuppServiceFailed(PhoneInternalInterface.SuppService.UNKNOWN);
        return true;
    }

    private int[] handleCwQueryResult(ImsSsInfo[] arrimsSsInfo) {
        int[] arrn = new int[2];
        arrn[0] = 0;
        if (arrimsSsInfo[0].getStatus() == 1) {
            arrn[0] = 1;
            arrn[1] = 1;
        }
        return arrn;
    }

    private boolean handleEctIncallSupplementaryService(String string) {
        if (string.length() != 1) {
            return false;
        }
        this.logd("MmiCode 4: not support explicit call transfer");
        this.notifySuppServiceFailed(PhoneInternalInterface.SuppService.TRANSFER);
        return true;
    }

    @UnsupportedAppUsage
    private void handleEnterEmergencyCallbackMode() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("handleEnterEmergencyCallbackMode,mIsPhoneInEcmState= ");
        stringBuilder.append(this.isInEcm());
        this.logd(stringBuilder.toString());
        if (!this.isInEcm()) {
            this.setIsInEcm(true);
            this.sendEmergencyCallbackModeChange();
            ((GsmCdmaPhone)this.mDefaultPhone).notifyEmergencyCallRegistrants(true);
            long l = SystemProperties.getLong((String)"ro.cdma.ecmexittimer", (long)300000L);
            this.postDelayed(this.mExitEcmRunnable, l);
            this.mWakeLock.acquire();
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

    @UnsupportedAppUsage
    private boolean isCfEnable(int n) {
        boolean bl;
        boolean bl2 = bl = true;
        if (n != 1) {
            bl2 = n == 3 ? bl : false;
        }
        return bl2;
    }

    private boolean isCsNotInServiceAndPsWwanReportingWlan(ServiceState serviceState) {
        TransportManager transportManager = this.mDefaultPhone.getTransportManager();
        boolean bl = false;
        if (transportManager != null && transportManager.isInLegacyMode()) {
            transportManager = serviceState.getNetworkRegistrationInfo(1, 1);
            if ((serviceState = serviceState.getNetworkRegistrationInfo(2, 1)) != null && transportManager != null && !transportManager.isInService() && serviceState.getAccessNetworkTechnology() == 18) {
                bl = true;
            }
            return bl;
        }
        return false;
    }

    @UnsupportedAppUsage
    private boolean isValidCommandInterfaceCFAction(int n) {
        return n == 0 || n == 1 || n == 3 || n == 4;
    }

    @UnsupportedAppUsage
    private boolean isValidCommandInterfaceCFReason(int n) {
        return n == 0 || n == 1 || n == 2 || n == 3 || n == 4 || n == 5;
    }

    private void logd(String string) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[");
        stringBuilder.append(this.mPhoneId);
        stringBuilder.append("] ");
        stringBuilder.append(string);
        Rlog.d((String)LOG_TAG, (String)stringBuilder.toString());
    }

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

    private void logv(String string) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[");
        stringBuilder.append(this.mPhoneId);
        stringBuilder.append("] ");
        stringBuilder.append(string);
        Rlog.v((String)LOG_TAG, (String)stringBuilder.toString());
    }

    private void onNetworkInitiatedUssd(ImsPhoneMmiCode imsPhoneMmiCode) {
        this.logd("onNetworkInitiatedUssd");
        this.mMmiCompleteRegistrants.notifyRegistrants(new AsyncResult(null, (Object)imsPhoneMmiCode, null));
    }

    private void processWfcDisconnectForNotification(ImsReasonInfo object) {
        Object object2 = (CarrierConfigManager)this.mContext.getSystemService("carrier_config");
        if (object2 == null) {
            this.loge("processDisconnectReason: CarrierConfigManager is not ready");
            return;
        }
        Object object3 = object2.getConfigForSubId(this.getSubId());
        if (object3 == null) {
            object = new StringBuilder();
            ((StringBuilder)object).append("processDisconnectReason: no config for subId ");
            ((StringBuilder)object).append(this.getSubId());
            this.loge(((StringBuilder)object).toString());
            return;
        }
        String[] arrstring = object3.getStringArray("wfc_operator_error_codes_string_array");
        if (arrstring == null) {
            return;
        }
        String[] arrstring2 = this.mContext.getResources().getStringArray(17236114);
        String[] arrstring3 = this.mContext.getResources().getStringArray(17236115);
        for (int i = 0; i < arrstring.length; ++i) {
            int n;
            CharSequence charSequence;
            String[] arrstring4 = arrstring[i].split("\\|");
            if (arrstring4.length != 2) {
                charSequence = new StringBuilder();
                ((StringBuilder)charSequence).append("Invalid carrier config: ");
                ((StringBuilder)charSequence).append(arrstring[i]);
                this.loge(((StringBuilder)charSequence).toString());
                continue;
            }
            if (!((ImsReasonInfo)object).mExtraMessage.startsWith(arrstring4[0]) || Character.isLetterOrDigit(arrstring4[0].charAt((n = arrstring4[0].length()) - 1)) && ((ImsReasonInfo)object).mExtraMessage.length() > n && Character.isLetterOrDigit(((ImsReasonInfo)object).mExtraMessage.charAt(n))) continue;
            charSequence = this.mContext.getText(17041223);
            n = Integer.parseInt(arrstring4[1]);
            if (n >= 0 && n < arrstring2.length && n < arrstring3.length) {
                object3 = ((ImsReasonInfo)object).mExtraMessage;
                object2 = ((ImsReasonInfo)object).mExtraMessage;
                if (!arrstring2[n].isEmpty()) {
                    object3 = String.format(arrstring2[n], ((ImsReasonInfo)object).mExtraMessage);
                }
                if (!arrstring3[n].isEmpty()) {
                    object2 = String.format(arrstring3[n], ((ImsReasonInfo)object).mExtraMessage);
                }
                object = new Intent("com.android.ims.REGISTRATION_ERROR");
                object.putExtra("alertTitle", charSequence);
                object.putExtra("alertMessage", (String)object3);
                object.putExtra("notificationMessage", (String)object2);
                this.mContext.sendOrderedBroadcast((Intent)object, null, this.mResultReceiver, null, -1, null, null);
                break;
            }
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("Invalid index: ");
            ((StringBuilder)charSequence).append(arrstring[i]);
            this.loge(((StringBuilder)charSequence).toString());
        }
    }

    private void sendEmergencyCallbackModeChange() {
        Object object = new Intent("android.intent.action.EMERGENCY_CALLBACK_MODE_CHANGED");
        object.putExtra("phoneinECMState", this.isInEcm());
        SubscriptionManager.putPhoneIdAndSubIdExtra((Intent)object, (int)this.getPhoneId());
        ActivityManager.broadcastStickyIntent((Intent)object, (int)-1);
        object = new StringBuilder();
        ((StringBuilder)object).append("sendEmergencyCallbackModeChange: isInEcm=");
        ((StringBuilder)object).append(this.isInEcm());
        this.logd(((StringBuilder)object).toString());
    }

    @UnsupportedAppUsage
    private void sendErrorResponse(Message message) {
        this.logd("sendErrorResponse");
        if (message != null) {
            AsyncResult.forMessage((Message)message, null, (Throwable)new CommandException(CommandException.Error.GENERIC_FAILURE));
            message.sendToTarget();
        }
    }

    private void sendResponse(Message message, Object object, Throwable throwable) {
        if (message != null) {
            CommandException commandException = null;
            if (throwable != null) {
                commandException = this.getCommandException(throwable);
            }
            AsyncResult.forMessage((Message)message, (Object)object, (Throwable)commandException);
            message.sendToTarget();
        }
    }

    private void sendUssdResponse(String string, CharSequence charSequence, int n, ResultReceiver resultReceiver) {
        charSequence = new UssdResponse(string, charSequence);
        string = new Bundle();
        string.putParcelable("USSD_RESPONSE", (Parcelable)charSequence);
        resultReceiver.send(n, (Bundle)string);
    }

    private void updateDataServiceState() {
        if (this.mSS != null && this.mDefaultPhone.getServiceStateTracker() != null && this.mDefaultPhone.getServiceStateTracker().mSS != null) {
            Object object2;
            ServiceState serviceState = this.mDefaultPhone.getServiceStateTracker().mSS;
            this.mSS.setDataRegState(serviceState.getDataRegState());
            for (Object object2 : serviceState.getNetworkRegistrationInfoListForDomain(2)) {
                this.mSS.addNetworkRegistrationInfo((NetworkRegistrationInfo)object2);
            }
            object2 = new StringBuilder();
            ((StringBuilder)object2).append("updateDataServiceState: defSs = ");
            ((StringBuilder)object2).append((Object)serviceState);
            ((StringBuilder)object2).append(" imsSs = ");
            ((StringBuilder)object2).append((Object)this.mSS);
            this.logd(((StringBuilder)object2).toString());
        }
    }

    private void updateRoamingState(ServiceState object) {
        if (object == null) {
            this.loge("updateRoamingState: null ServiceState!");
            return;
        }
        boolean bl = object.getRoaming();
        if (this.mRoaming == bl) {
            return;
        }
        boolean bl2 = object.getVoiceRegState() == 0 || object.getDataRegState() == 0;
        if (!bl2) {
            this.logi("updateRoamingState: we are OUT_OF_SERVICE, ignoring roaming change.");
            return;
        }
        if (this.isCsNotInServiceAndPsWwanReportingWlan((ServiceState)object)) {
            this.logi("updateRoamingState: IWLAN masking roaming, ignore roaming change.");
            return;
        }
        if (this.mCT.getState() == PhoneConstants.State.IDLE) {
            object = new StringBuilder();
            ((StringBuilder)object).append("updateRoamingState now: ");
            ((StringBuilder)object).append(bl);
            this.logd(((StringBuilder)object).toString());
            this.mRoaming = bl;
            object = ImsManager.getInstance((Context)this.mContext, (int)this.mPhoneId);
            object.setWfcMode(object.getWfcMode(bl), bl);
        } else {
            object = new StringBuilder();
            ((StringBuilder)object).append("updateRoamingState postponed: ");
            ((StringBuilder)object).append(bl);
            this.logd(((StringBuilder)object).toString());
            this.mCT.registerForVoiceCallEnded(this, 61, null);
        }
    }

    @Override
    public void acceptCall(int n) throws CallStateException {
        this.mCT.acceptCall(n);
    }

    @Override
    public void callEndCleanupHandOverCallIfAny() {
        this.mCT.callEndCleanupHandOverCallIfAny();
    }

    @Override
    public boolean canConference() {
        return this.mCT.canConference();
    }

    @Override
    public boolean canDial() {
        try {
            this.mCT.checkForDialIssues();
            return true;
        }
        catch (CallStateException callStateException) {
            return false;
        }
    }

    @Override
    public boolean canTransfer() {
        return this.mCT.canTransfer();
    }

    @Override
    public void cancelUSSD(Message message) {
        this.mCT.cancelUSSD(message);
    }

    @Override
    public void clearDisconnected() {
        this.mCT.clearDisconnected();
    }

    @Override
    public void conference() {
        this.mCT.conference();
    }

    @Override
    public Connection dial(String string, PhoneInternalInterface.DialArgs dialArgs) throws CallStateException {
        return this.dialInternal(string, dialArgs, null);
    }

    @Override
    public void dispose() {
        this.logd("dispose");
        this.mPendingMMIs.clear();
        this.mExternalCallTracker.tearDown();
        this.mCT.unregisterPhoneStateListener(this.mExternalCallTracker);
        this.mCT.unregisterForVoiceCallEnded(this);
        this.mCT.dispose();
        int[] arrn = this.mDefaultPhone;
        if (arrn != null && arrn.getServiceStateTracker() != null) {
            for (int n : this.mDefaultPhone.getTransportManager().getAvailableTransports()) {
                this.mDefaultPhone.getServiceStateTracker().unregisterForDataRegStateOrRatChanged(n, this);
            }
            this.mDefaultPhone.unregisterForServiceStateChanged(this);
        }
    }

    @Override
    public void dump(FileDescriptor object, PrintWriter printWriter, String[] arrstring) {
        printWriter.println("ImsPhone extends:");
        super.dump((FileDescriptor)object, printWriter, arrstring);
        printWriter.flush();
        printWriter.println("ImsPhone:");
        object = new StringBuilder();
        ((StringBuilder)object).append("  mDefaultPhone = ");
        ((StringBuilder)object).append(this.mDefaultPhone);
        printWriter.println(((StringBuilder)object).toString());
        object = new StringBuilder();
        ((StringBuilder)object).append("  mPendingMMIs = ");
        ((StringBuilder)object).append(this.mPendingMMIs);
        printWriter.println(((StringBuilder)object).toString());
        object = new StringBuilder();
        ((StringBuilder)object).append("  mPostDialHandler = ");
        ((StringBuilder)object).append((Object)this.mPostDialHandler);
        printWriter.println(((StringBuilder)object).toString());
        object = new StringBuilder();
        ((StringBuilder)object).append("  mSS = ");
        ((StringBuilder)object).append((Object)this.mSS);
        printWriter.println(((StringBuilder)object).toString());
        object = new StringBuilder();
        ((StringBuilder)object).append("  mWakeLock = ");
        ((StringBuilder)object).append((Object)this.mWakeLock);
        printWriter.println(((StringBuilder)object).toString());
        object = new StringBuilder();
        ((StringBuilder)object).append("  mIsPhoneInEcmState = ");
        ((StringBuilder)object).append(this.isInEcm());
        printWriter.println(((StringBuilder)object).toString());
        object = new StringBuilder();
        ((StringBuilder)object).append("  mEcmExitRespRegistrant = ");
        ((StringBuilder)object).append((Object)this.mEcmExitRespRegistrant);
        printWriter.println(((StringBuilder)object).toString());
        object = new StringBuilder();
        ((StringBuilder)object).append("  mSilentRedialRegistrants = ");
        ((StringBuilder)object).append((Object)this.mSilentRedialRegistrants);
        printWriter.println(((StringBuilder)object).toString());
        object = new StringBuilder();
        ((StringBuilder)object).append("  mImsRegistered = ");
        ((StringBuilder)object).append(this.mImsRegistered);
        printWriter.println(((StringBuilder)object).toString());
        object = new StringBuilder();
        ((StringBuilder)object).append("  mRoaming = ");
        ((StringBuilder)object).append(this.mRoaming);
        printWriter.println(((StringBuilder)object).toString());
        object = new StringBuilder();
        ((StringBuilder)object).append("  mSsnRegistrants = ");
        ((StringBuilder)object).append((Object)this.mSsnRegistrants);
        printWriter.println(((StringBuilder)object).toString());
        printWriter.flush();
    }

    @Override
    public void exitEmergencyCallbackMode() {
        if (this.mWakeLock.isHeld()) {
            this.mWakeLock.release();
        }
        this.logd("exitEmergencyCallbackMode()");
        try {
            this.mCT.getEcbmInterface().exitEmergencyCallbackMode();
        }
        catch (ImsException imsException) {
            imsException.printStackTrace();
        }
    }

    @Override
    public void explicitCallTransfer() {
        this.mCT.explicitCallTransfer();
    }

    @UnsupportedAppUsage
    @Override
    public ImsPhoneCall getBackgroundCall() {
        return this.mCT.mBackgroundCall;
    }

    public void getCallBarring(String string, Message message) {
        this.getCallBarring(string, message, 0);
    }

    public void getCallBarring(String string, Message message, int n) {
        this.getCallBarring(string, "", message, n);
    }

    @Override
    public void getCallBarring(String string, String charSequence, Message message, int n) {
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append("getCallBarring facility=");
        ((StringBuilder)charSequence).append(string);
        ((StringBuilder)charSequence).append(", serviceClass = ");
        ((StringBuilder)charSequence).append(n);
        this.logd(((StringBuilder)charSequence).toString());
        charSequence = this.obtainMessage(54, (Object)message);
        try {
            this.mCT.getUtInterface().queryCallBarring(this.getCBTypeFromFacility(string), (Message)charSequence, n);
        }
        catch (ImsException imsException) {
            this.sendErrorResponse(message, imsException);
        }
    }

    @UnsupportedAppUsage
    @Override
    public void getCallForwardingOption(int n, Message message) {
        block3 : {
            block2 : {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("getCallForwardingOption reason=");
                stringBuilder.append(n);
                this.logd(stringBuilder.toString());
                if (!this.isValidCommandInterfaceCFReason(n)) break block2;
                this.logd("requesting call forwarding query.");
                stringBuilder = this.obtainMessage(13, (Object)message);
                try {
                    this.mCT.getUtInterface().queryCallForward(this.getConditionFromCFReason(n), null, (Message)stringBuilder);
                }
                catch (ImsException imsException) {
                    this.sendErrorResponse(message, imsException);
                }
                break block3;
            }
            if (message == null) break block3;
            this.sendErrorResponse(message);
        }
    }

    @Override
    public CallTracker getCallTracker() {
        return this.mCT;
    }

    @UnsupportedAppUsage
    @Override
    public void getCallWaiting(Message message) {
        this.logd("getCallWaiting");
        Message message2 = this.obtainMessage(56, (Object)message);
        try {
            this.mCT.getUtInterface().queryCallWaiting(message2);
        }
        catch (ImsException imsException) {
            this.sendErrorResponse(message, imsException);
        }
    }

    @Override
    public Uri[] getCurrentSubscriberUris() {
        return this.mCurrentSubscriberUris;
    }

    @Override
    public Phone getDefaultPhone() {
        return this.mDefaultPhone;
    }

    @Override
    public EmergencyNumberTracker getEmergencyNumberTracker() {
        return this.mDefaultPhone.getEmergencyNumberTracker();
    }

    public ImsExternalCallTracker getExternalCallTracker() {
        return this.mExternalCallTracker;
    }

    @UnsupportedAppUsage
    @Override
    public ImsPhoneCall getForegroundCall() {
        return this.mCT.mForegroundCall;
    }

    @Override
    public ArrayList<Connection> getHandoverConnection() {
        ArrayList<Connection> arrayList = new ArrayList<Connection>();
        arrayList.addAll(((ImsPhoneCall)this.getForegroundCall()).mConnections);
        arrayList.addAll(((ImsPhoneCall)this.getBackgroundCall()).mConnections);
        arrayList.addAll(((ImsPhoneCall)this.getRingingCall()).mConnections);
        if (arrayList.size() > 0) {
            return arrayList;
        }
        return null;
    }

    @VisibleForTesting
    public ImsEcbmStateListener getImsEcbmStateListener() {
        return this.mImsEcbmStateListener;
    }

    @Override
    public int getImsRegistrationTech() {
        return this.mCT.getImsRegistrationTech();
    }

    @Override
    public boolean getMute() {
        return this.mCT.getMute();
    }

    @Override
    public void getOutgoingCallerIdDisplay(Message message) {
        this.logd("getCLIR");
        Message message2 = this.obtainMessage(58, (Object)message);
        try {
            this.mCT.getUtInterface().queryCLIR(message2);
        }
        catch (ImsException imsException) {
            this.sendErrorResponse(message, imsException);
        }
    }

    public List<? extends ImsPhoneMmiCode> getPendingMmiCodes() {
        return this.mPendingMMIs;
    }

    @Override
    public int getPhoneId() {
        return this.mDefaultPhone.getPhoneId();
    }

    @UnsupportedAppUsage
    @Override
    public ImsPhoneCall getRingingCall() {
        return this.mCT.mRingingCall;
    }

    @UnsupportedAppUsage
    @Override
    public ServiceState getServiceState() {
        return this.mSS;
    }

    @Override
    public ServiceStateTracker getServiceStateTracker() {
        return this.mDefaultPhone.getServiceStateTracker();
    }

    @UnsupportedAppUsage
    @Override
    public PhoneConstants.State getState() {
        return this.mCT.getState();
    }

    @Override
    public int getSubId() {
        return this.mDefaultPhone.getSubId();
    }

    @Override
    public NetworkStats getVtDataUsage(boolean bl) {
        return this.mCT.getVtDataUsage(bl);
    }

    @VisibleForTesting
    public PowerManager.WakeLock getWakeLock() {
        return this.mWakeLock;
    }

    public CallForwardInfo[] handleCfQueryResult(ImsCallForwardInfo[] arrimsCallForwardInfo) {
        CallForwardInfo[] arrcallForwardInfo;
        CallForwardInfo[] arrcallForwardInfo2 = arrcallForwardInfo = null;
        if (arrimsCallForwardInfo != null) {
            arrcallForwardInfo2 = arrcallForwardInfo;
            if (arrimsCallForwardInfo.length != 0) {
                arrcallForwardInfo2 = new CallForwardInfo[arrimsCallForwardInfo.length];
            }
        }
        arrcallForwardInfo = this.mDefaultPhone.getIccRecords();
        if (arrimsCallForwardInfo != null && arrimsCallForwardInfo.length != 0) {
            int n = arrimsCallForwardInfo.length;
            for (int i = 0; i < n; ++i) {
                if (arrimsCallForwardInfo[i].getCondition() == 0 && arrcallForwardInfo != null) {
                    boolean bl = arrimsCallForwardInfo[i].getStatus() == 1;
                    this.setVoiceCallForwardingFlag((IccRecords)arrcallForwardInfo, 1, bl, arrimsCallForwardInfo[i].getNumber());
                }
                arrcallForwardInfo2[i] = this.getCallForwardInfo(arrimsCallForwardInfo[i]);
            }
        } else if (arrcallForwardInfo != null) {
            this.setVoiceCallForwardingFlag((IccRecords)arrcallForwardInfo, 1, false, null);
        }
        return arrcallForwardInfo2;
    }

    @UnsupportedAppUsage
    @Override
    protected void handleExitEmergencyCallbackMode() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("handleExitEmergencyCallbackMode: mIsPhoneInEcmState = ");
        stringBuilder.append(this.isInEcm());
        this.logd(stringBuilder.toString());
        if (this.isInEcm()) {
            this.setIsInEcm(false);
        }
        this.removeCallbacks(this.mExitEcmRunnable);
        stringBuilder = this.mEcmExitRespRegistrant;
        if (stringBuilder != null) {
            stringBuilder.notifyResult((Object)Boolean.TRUE);
        }
        if (this.mWakeLock.isHeld()) {
            this.mWakeLock.release();
        }
        this.sendEmergencyCallbackModeChange();
        ((GsmCdmaPhone)this.mDefaultPhone).notifyEmergencyCallRegistrants(false);
    }

    @UnsupportedAppUsage
    @Override
    public boolean handleInCallMmiCommands(String string) {
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

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     */
    @Override
    public void handleMessage(Message object) {
        AsyncResult asyncResult = (AsyncResult)object.obj;
        int[] arrn = new StringBuilder();
        arrn.append("handleMessage what=");
        arrn.append(object.what);
        this.logd(arrn.toString());
        int n = object.what;
        if (n != 12) {
            void var1_8;
            if (n != 13) {
                switch (n) {
                    default: {
                        super.handleMessage((Message)object);
                        return;
                    }
                    case 61: {
                        this.logd("Voice call ended. Handle pending updateRoamingState.");
                        this.mCT.unregisterForVoiceCallEnded(this);
                        ServiceStateTracker serviceStateTracker = this.getDefaultPhone().getServiceStateTracker();
                        if (serviceStateTracker == null) return;
                        this.updateRoamingState(serviceStateTracker.mSS);
                        return;
                    }
                    case 60: {
                        this.updateRoamingState((ServiceState)((AsyncResult)object.obj).result);
                        return;
                    }
                    case 59: {
                        this.logd("EVENT_DEFAULT_PHONE_DATA_STATE_CHANGED");
                        this.updateDataServiceState();
                        return;
                    }
                    case 58: {
                        void var1_5;
                        arrn = (Bundle)asyncResult.result;
                        Object var1_3 = null;
                        if (arrn != null) {
                            int[] arrn2 = arrn.getIntArray("queryClir");
                        }
                        this.sendResponse((Message)asyncResult.userObj, var1_5, asyncResult.exception);
                        return;
                    }
                    case 57: {
                        if (asyncResult.exception != null) break;
                        this.saveClirSetting(object.arg1);
                        break;
                    }
                    case 54: 
                    case 56: {
                        Object var5_12 = null;
                        arrn = var5_12;
                        if (asyncResult.exception == null) {
                            if (object.what == 54) {
                                arrn = this.handleCbQueryResult((ImsSsInfo[])asyncResult.result);
                            } else {
                                arrn = var5_12;
                                if (object.what == 56) {
                                    arrn = this.handleCwQueryResult((ImsSsInfo[])asyncResult.result);
                                }
                            }
                        }
                        this.sendResponse((Message)asyncResult.userObj, arrn, asyncResult.exception);
                        return;
                    }
                    case 53: 
                    case 55: 
                }
                this.sendResponse((Message)asyncResult.userObj, null, asyncResult.exception);
                return;
            }
            Object var1_6 = null;
            if (asyncResult.exception == null) {
                CallForwardInfo[] arrcallForwardInfo = this.handleCfQueryResult((ImsCallForwardInfo[])asyncResult.result);
            }
            this.sendResponse((Message)asyncResult.userObj, var1_8, asyncResult.exception);
            return;
        }
        arrn = this.mDefaultPhone.getIccRecords();
        Cf cf = (Cf)asyncResult.userObj;
        if (cf.mIsCfu && asyncResult.exception == null && arrn != null) {
            boolean bl = object.arg1 == 1;
            this.setVoiceCallForwardingFlag((IccRecords)arrn, 1, bl, cf.mSetCfNumber);
        }
        this.sendResponse(cf.mOnComplete, null, asyncResult.exception);
    }

    void handleTimerInEmergencyCallbackMode(int n) {
        if (n != 0) {
            if (n != 1) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("handleTimerInEmergencyCallbackMode, unsupported action ");
                stringBuilder.append(n);
                this.loge(stringBuilder.toString());
            } else {
                this.removeCallbacks(this.mExitEcmRunnable);
                ((GsmCdmaPhone)this.mDefaultPhone).notifyEcbmTimerReset(Boolean.TRUE);
            }
        } else {
            long l = SystemProperties.getLong((String)"ro.cdma.ecmexittimer", (long)300000L);
            this.postDelayed(this.mExitEcmRunnable, l);
            ((GsmCdmaPhone)this.mDefaultPhone).notifyEcbmTimerReset(Boolean.FALSE);
        }
    }

    @Override
    public boolean handleUssdRequest(String string, ResultReceiver resultReceiver) throws CallStateException {
        CallStateException callStateException2;
        block4 : {
            if (this.mPendingMMIs.size() > 0) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("handleUssdRequest: queue full: ");
                stringBuilder.append(Rlog.pii((String)LOG_TAG, (Object)string));
                this.logi(stringBuilder.toString());
                this.sendUssdResponse(string, null, -1, resultReceiver);
                return true;
            }
            try {
                ImsDialArgs.Builder builder = new ImsDialArgs.Builder();
                this.dialInternal(string, builder.build(), resultReceiver);
            }
            catch (Exception exception) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Could not execute USSD ");
                stringBuilder.append(exception);
                Rlog.w((String)LOG_TAG, (String)stringBuilder.toString());
                this.sendUssdResponse(string, null, -1, resultReceiver);
                return false;
            }
            catch (CallStateException callStateException2) {
                if ("cs_fallback".equals(callStateException2.getMessage())) break block4;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Could not execute USSD ");
                stringBuilder.append(callStateException2);
                Rlog.w((String)LOG_TAG, (String)stringBuilder.toString());
                this.sendUssdResponse(string, null, -1, resultReceiver);
            }
            return true;
        }
        throw callStateException2;
    }

    public void holdActiveCall() throws CallStateException {
        this.mCT.holdActiveCall();
    }

    void initiateSilentRedial() {
        AsyncResult asyncResult = new AsyncResult(null, (Object)this.mLastDialString, null);
        this.mSilentRedialRegistrants.notifyRegistrants(asyncResult);
    }

    @Override
    public boolean isImsAvailable() {
        return this.mCT.isImsServiceReady();
    }

    @Override
    public boolean isImsCapabilityAvailable(int n, int n2) {
        return this.mCT.isImsCapabilityAvailable(n, n2);
    }

    @Override
    public boolean isImsRegistered() {
        return this.mImsRegistered;
    }

    @Override
    boolean isInCall() {
        Call.State state = this.getForegroundCall().getState();
        Call.State state2 = this.getBackgroundCall().getState();
        Call.State state3 = this.getRingingCall().getState();
        boolean bl = state.isAlive() || state2.isAlive() || state3.isAlive();
        return bl;
    }

    @Override
    public boolean isInEcm() {
        return this.mDefaultPhone.isInEcm();
    }

    @Override
    public boolean isInEmergencyCall() {
        return this.mCT.isInEmergencyCall();
    }

    @UnsupportedAppUsage
    @Override
    public boolean isUtEnabled() {
        return this.mCT.isUtEnabled();
    }

    @Override
    public boolean isVideoEnabled() {
        return this.mCT.isVideoCallEnabled();
    }

    @UnsupportedAppUsage
    @Override
    public boolean isVolteEnabled() {
        return this.mCT.isVolteEnabled();
    }

    @Override
    public boolean isWifiCallingEnabled() {
        return this.mCT.isVowifiEnabled();
    }

    @Override
    public void notifyForVideoCapabilityChanged(boolean bl) {
        this.mIsVideoCapable = bl;
        this.mDefaultPhone.notifyForVideoCapabilityChanged(bl);
    }

    public void notifyIncomingRing() {
        this.logd("notifyIncomingRing");
        this.sendMessage(this.obtainMessage(14, (Object)new AsyncResult(null, null, null)));
    }

    public void notifyNewRingingConnection(Connection connection) {
        this.mDefaultPhone.notifyNewRingingConnectionP(connection);
    }

    @Override
    public void notifySrvccState(Call.SrvccState srvccState) {
        this.mCT.notifySrvccState(srvccState);
    }

    public void notifySuppSvcNotification(SuppServiceNotification suppServiceNotification) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("notifySuppSvcNotification: suppSvc = ");
        stringBuilder.append(suppServiceNotification);
        this.logd(stringBuilder.toString());
        suppServiceNotification = new AsyncResult(null, (Object)suppServiceNotification, null);
        this.mSsnRegistrants.notifyRegistrants((AsyncResult)suppServiceNotification);
    }

    @UnsupportedAppUsage
    void notifyUnknownConnection(Connection connection) {
        this.mDefaultPhone.notifyUnknownConnectionP(connection);
    }

    public void onFeatureCapabilityChanged() {
        this.mDefaultPhone.getServiceStateTracker().onImsCapabilityChanged();
    }

    void onIncomingUSSD(int n, String string) {
        Object object = new StringBuilder();
        ((StringBuilder)object).append("onIncomingUSSD ussdMode=");
        ((StringBuilder)object).append(n);
        this.logd(((StringBuilder)object).toString());
        int n2 = 0;
        boolean bl = n == 1;
        int n3 = n2;
        if (n != 0) {
            n3 = n2;
            if (n != 1) {
                n3 = 1;
            }
        }
        Object var7_7 = null;
        n = 0;
        n2 = this.mPendingMMIs.size();
        do {
            object = var7_7;
            if (n >= n2) break;
            if (this.mPendingMMIs.get(n).isPendingUSSD()) {
                object = this.mPendingMMIs.get(n);
                break;
            }
            ++n;
        } while (true);
        if (object != null) {
            if (n3 != 0) {
                ((ImsPhoneMmiCode)object).onUssdFinishedError();
            } else {
                ((ImsPhoneMmiCode)object).onUssdFinished(string, bl);
            }
        } else if (n3 == 0 && string != null) {
            this.onNetworkInitiatedUssd(ImsPhoneMmiCode.newNetworkInitiatedUssd(string, bl, this));
        }
    }

    @UnsupportedAppUsage
    public void onMMIDone(ImsPhoneMmiCode imsPhoneMmiCode) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("onMMIDone: mmi=");
        stringBuilder.append(imsPhoneMmiCode);
        this.logd(stringBuilder.toString());
        if (this.mPendingMMIs.remove(imsPhoneMmiCode) || imsPhoneMmiCode.isUssdRequest() || imsPhoneMmiCode.isSsInfo()) {
            stringBuilder = imsPhoneMmiCode.getUssdCallbackReceiver();
            if (stringBuilder != null) {
                int n = imsPhoneMmiCode.getState() == MmiCode.State.COMPLETE ? 100 : -1;
                this.sendUssdResponse(imsPhoneMmiCode.getDialString(), imsPhoneMmiCode.getMessage(), n, (ResultReceiver)stringBuilder);
            } else {
                this.logv("onMMIDone: notifyRegistrants");
                this.mMmiCompleteRegistrants.notifyRegistrants(new AsyncResult(null, (Object)imsPhoneMmiCode, null));
            }
        }
    }

    public void processDisconnectReason(ImsReasonInfo imsReasonInfo) {
        if (imsReasonInfo.mCode == 1000 && imsReasonInfo.mExtraMessage != null && ImsManager.getInstance((Context)this.mContext, (int)this.mPhoneId).isWfcEnabledByUser()) {
            this.processWfcDisconnectForNotification(imsReasonInfo);
        }
    }

    @Override
    public void registerForSilentRedial(Handler handler, int n, Object object) {
        this.mSilentRedialRegistrants.addUnique(handler, n, object);
    }

    @Override
    public void registerForSuppServiceNotification(Handler handler, int n, Object object) {
        this.mSsnRegistrants.addUnique(handler, n, object);
    }

    @Override
    public void rejectCall() throws CallStateException {
        this.mCT.rejectCall();
    }

    @Override
    public void sendDtmf(char c) {
        if (!PhoneNumberUtils.is12Key((char)c)) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("sendDtmf called with invalid character '");
            stringBuilder.append(c);
            stringBuilder.append("'");
            this.loge(stringBuilder.toString());
        } else if (this.mCT.getState() == PhoneConstants.State.OFFHOOK) {
            this.mCT.sendDtmf(c, null);
        }
    }

    @Override
    public void sendEmergencyCallStateChange(boolean bl) {
        this.mDefaultPhone.sendEmergencyCallStateChange(bl);
    }

    @UnsupportedAppUsage
    @VisibleForTesting
    public void sendErrorResponse(Message message, Throwable throwable) {
        this.logd("sendErrorResponse");
        if (message != null) {
            AsyncResult.forMessage((Message)message, null, (Throwable)this.getCommandException(throwable));
            message.sendToTarget();
        }
    }

    public void sendUSSD(String string, Message message) {
        this.mCT.sendUSSD(string, message);
    }

    @Override
    public void sendUssdResponse(String string) {
        this.logd("sendUssdResponse");
        ImsPhoneMmiCode imsPhoneMmiCode = ImsPhoneMmiCode.newFromUssdUserInput(string, this);
        this.mPendingMMIs.add(imsPhoneMmiCode);
        this.mMmiRegistrants.notifyRegistrants(new AsyncResult(null, (Object)imsPhoneMmiCode, null));
        imsPhoneMmiCode.sendUssd(string);
    }

    @Override
    public void setBroadcastEmergencyCallStateChanges(boolean bl) {
        this.mDefaultPhone.setBroadcastEmergencyCallStateChanges(bl);
    }

    public void setCallBarring(String string, boolean bl, String string2, Message message) {
        this.setCallBarring(string, bl, string2, message, 0);
    }

    @Override
    public void setCallBarring(String string, boolean bl, String charSequence, Message message, int n) {
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append("setCallBarring facility=");
        ((StringBuilder)charSequence).append(string);
        ((StringBuilder)charSequence).append(", lockState=");
        ((StringBuilder)charSequence).append(bl);
        ((StringBuilder)charSequence).append(", serviceClass = ");
        ((StringBuilder)charSequence).append(n);
        this.logd(((StringBuilder)charSequence).toString());
        charSequence = this.obtainMessage(53, (Object)message);
        int n2 = bl ? 1 : 0;
        try {
            this.mCT.getUtInterface().updateCallBarring(this.getCBTypeFromFacility(string), n2, (Message)charSequence, null, n);
        }
        catch (ImsException imsException) {
            this.sendErrorResponse(message, imsException);
        }
    }

    @UnsupportedAppUsage
    public void setCallForwardingOption(int n, int n2, String string, int n3, int n4, Message message) {
        block3 : {
            block2 : {
                Object object = new StringBuilder();
                ((StringBuilder)object).append("setCallForwardingOption action=");
                ((StringBuilder)object).append(n);
                ((StringBuilder)object).append(", reason=");
                ((StringBuilder)object).append(n2);
                ((StringBuilder)object).append(" serviceClass=");
                ((StringBuilder)object).append(n3);
                this.logd(((StringBuilder)object).toString());
                if (!this.isValidCommandInterfaceCFAction(n) || !this.isValidCommandInterfaceCFReason(n2)) break block2;
                object = new Cf(string, GsmMmiCode.isVoiceUnconditionalForwarding(n2, n3), message);
                object = this.obtainMessage(12, (int)this.isCfEnable(n), 0, object);
                try {
                    this.mCT.getUtInterface().updateCallForward(this.getActionFromCFAction(n), this.getConditionFromCFReason(n2), string, n3, n4, (Message)object);
                }
                catch (ImsException imsException) {
                    this.sendErrorResponse(message, imsException);
                }
                break block3;
            }
            if (message == null) break block3;
            this.sendErrorResponse(message);
        }
    }

    @Override
    public void setCallForwardingOption(int n, int n2, String string, int n3, Message message) {
        this.setCallForwardingOption(n, n2, string, 1, n3, message);
    }

    public void setCallWaiting(boolean bl, int n, Message message) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("setCallWaiting enable=");
        stringBuilder.append(bl);
        this.logd(stringBuilder.toString());
        stringBuilder = this.obtainMessage(55, (Object)message);
        try {
            this.mCT.getUtInterface().updateCallWaiting(bl, n, (Message)stringBuilder);
        }
        catch (ImsException imsException) {
            this.sendErrorResponse(message, imsException);
        }
    }

    @UnsupportedAppUsage
    @Override
    public void setCallWaiting(boolean bl, Message message) {
        int n = 1;
        PersistableBundle persistableBundle = ((CarrierConfigManager)this.getContext().getSystemService("carrier_config")).getConfigForSubId(this.getSubId());
        if (persistableBundle != null) {
            n = persistableBundle.getInt("call_waiting_service_class_int", 1);
        }
        this.setCallWaiting(bl, n, message);
    }

    protected void setCurrentSubscriberUris(Uri[] arruri) {
        this.mCurrentSubscriberUris = arruri;
    }

    @UnsupportedAppUsage
    public void setImsRegistered(boolean bl) {
        this.mImsRegistered = bl;
    }

    @Override
    public void setIsInEcm(boolean bl) {
        this.mDefaultPhone.setIsInEcm(bl);
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
    public void setOutgoingCallerIdDisplay(int n, Message message) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("setCLIR action= ");
        stringBuilder.append(n);
        this.logd(stringBuilder.toString());
        stringBuilder = this.obtainMessage(57, n, 0, (Object)message);
        try {
            this.mCT.getUtInterface().updateCLIR(n, (Message)stringBuilder);
        }
        catch (ImsException imsException) {
            this.sendErrorResponse(message, imsException);
        }
    }

    @Override
    public void setRadioPower(boolean bl) {
        this.mDefaultPhone.setRadioPower(bl);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @UnsupportedAppUsage
    @VisibleForTesting
    public void setServiceState(int n) {
        boolean bl;
        synchronized (this) {
            bl = this.mSS.getVoiceRegState() != n;
            this.mSS.setVoiceRegState(n);
        }
        this.updateDataServiceState();
        if (bl && this.mDefaultPhone.getServiceStateTracker() != null) {
            this.mDefaultPhone.getServiceStateTracker().onImsServiceStateChanged();
        }
    }

    @Override
    public void setTTYMode(int n, Message message) {
        this.mCT.setTtyMode(n);
    }

    @Override
    public void setUiTTYMode(int n, Message message) {
        this.mCT.setUiTTYMode(n, message);
    }

    @Override
    public void startDtmf(char c) {
        if (!(PhoneNumberUtils.is12Key((char)c) || c >= 'A' && c <= 'D')) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("startDtmf called with invalid character '");
            stringBuilder.append(c);
            stringBuilder.append("'");
            this.loge(stringBuilder.toString());
        } else {
            this.mCT.startDtmf(c);
        }
    }

    @Override
    public void stopDtmf() {
        this.mCT.stopDtmf();
    }

    @Override
    public void switchHoldingAndActive() throws CallStateException {
        throw new UnsupportedOperationException("Use hold() and unhold() instead.");
    }

    public void unholdHeldCall() throws CallStateException {
        this.mCT.unholdHeldCall();
    }

    @Override
    public void unregisterForSilentRedial(Handler handler) {
        this.mSilentRedialRegistrants.remove(handler);
    }

    @Override
    public void unregisterForSuppServiceNotification(Handler handler) {
        this.mSsnRegistrants.remove(handler);
    }

    @Override
    public void unsetOnEcbModeExitResponse(Handler handler) {
        this.mEcmExitRespRegistrant.clear();
    }

    private static class Cf {
        final boolean mIsCfu;
        final Message mOnComplete;
        final String mSetCfNumber;

        @UnsupportedAppUsage
        Cf(String string, boolean bl, Message message) {
            this.mSetCfNumber = string;
            this.mIsCfu = bl;
            this.mOnComplete = message;
        }
    }

    public static class ImsDialArgs
    extends PhoneInternalInterface.DialArgs {
        public final int clirMode;
        public final Connection.RttTextStream rttTextStream;

        private ImsDialArgs(Builder builder) {
            super(builder);
            this.rttTextStream = builder.mRttTextStream;
            this.clirMode = builder.mClirMode;
        }

        public static class Builder
        extends PhoneInternalInterface.DialArgs.Builder<Builder> {
            private int mClirMode = 0;
            private Connection.RttTextStream mRttTextStream;

            public static Builder from(PhoneInternalInterface.DialArgs dialArgs) {
                return (Builder)((Builder)((Builder)new Builder().setUusInfo(dialArgs.uusInfo)).setVideoState(dialArgs.videoState)).setIntentExtras(dialArgs.intentExtras);
            }

            public static Builder from(ImsDialArgs imsDialArgs) {
                return ((Builder)((Builder)((Builder)new Builder().setUusInfo(imsDialArgs.uusInfo)).setVideoState(imsDialArgs.videoState)).setIntentExtras(imsDialArgs.intentExtras)).setRttTextStream(imsDialArgs.rttTextStream).setClirMode(imsDialArgs.clirMode);
            }

            @Override
            public ImsDialArgs build() {
                return new ImsDialArgs(this);
            }

            public Builder setClirMode(int n) {
                this.mClirMode = n;
                return this;
            }

            public Builder setRttTextStream(Connection.RttTextStream rttTextStream) {
                this.mRttTextStream = rttTextStream;
                return this;
            }
        }

    }

}

