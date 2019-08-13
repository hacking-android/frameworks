/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.annotation.UnsupportedAppUsage
 *  android.content.BroadcastReceiver
 *  android.content.Context
 *  android.content.Intent
 *  android.content.IntentFilter
 *  android.os.AsyncResult
 *  android.os.Bundle
 *  android.os.Handler
 *  android.os.Message
 *  android.os.PersistableBundle
 *  android.os.Registrant
 *  android.os.RegistrantList
 *  android.os.SystemProperties
 *  android.telephony.CarrierConfigManager
 *  android.telephony.CellLocation
 *  android.telephony.PhoneNumberUtils
 *  android.telephony.Rlog
 *  android.telephony.TelephonyManager
 *  android.telephony.cdma.CdmaCellLocation
 *  android.telephony.emergency.EmergencyNumber
 *  android.telephony.gsm.GsmCellLocation
 *  android.text.TextUtils
 *  android.util.EventLog
 *  com.android.internal.annotations.VisibleForTesting
 *  com.android.internal.telephony.-$
 *  com.android.internal.telephony.-$$Lambda
 *  com.android.internal.telephony.-$$Lambda$GsmCdmaCallTracker
 *  com.android.internal.telephony.-$$Lambda$GsmCdmaCallTracker$wkXwCyVPcnlqyXzSJdP2cQlpZxg
 *  com.android.internal.telephony.PhoneConstants
 *  com.android.internal.telephony.PhoneConstants$State
 */
package com.android.internal.telephony;

import android.annotation.UnsupportedAppUsage;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncResult;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.PersistableBundle;
import android.os.Registrant;
import android.os.RegistrantList;
import android.os.SystemProperties;
import android.telephony.CarrierConfigManager;
import android.telephony.CellLocation;
import android.telephony.PhoneNumberUtils;
import android.telephony.Rlog;
import android.telephony.TelephonyManager;
import android.telephony.cdma.CdmaCellLocation;
import android.telephony.emergency.EmergencyNumber;
import android.telephony.gsm.GsmCellLocation;
import android.text.TextUtils;
import android.util.EventLog;
import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.telephony.-$;
import com.android.internal.telephony.Call;
import com.android.internal.telephony.CallStateException;
import com.android.internal.telephony.CallTracker;
import com.android.internal.telephony.CommandException;
import com.android.internal.telephony.CommandsInterface;
import com.android.internal.telephony.Connection;
import com.android.internal.telephony.DriverCall;
import com.android.internal.telephony.GsmCdmaCall;
import com.android.internal.telephony.GsmCdmaConnection;
import com.android.internal.telephony.GsmCdmaPhone;
import com.android.internal.telephony.LastCallFailCause;
import com.android.internal.telephony.LocaleTracker;
import com.android.internal.telephony.Phone;
import com.android.internal.telephony.PhoneConstants;
import com.android.internal.telephony.PhoneInternalInterface;
import com.android.internal.telephony.ServiceStateTracker;
import com.android.internal.telephony.UUSInfo;
import com.android.internal.telephony._$$Lambda$GsmCdmaCallTracker$wkXwCyVPcnlqyXzSJdP2cQlpZxg;
import com.android.internal.telephony.cdma.CdmaCallWaitingNotification;
import com.android.internal.telephony.dataconnection.DataEnabledSettings;
import com.android.internal.telephony.metrics.TelephonyMetrics;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class GsmCdmaCallTracker
extends CallTracker {
    private static final boolean DBG_POLL = false;
    private static final String LOG_TAG = "GsmCdmaCallTracker";
    private static final int MAX_CONNECTIONS_CDMA = 8;
    public static final int MAX_CONNECTIONS_GSM = 19;
    private static final int MAX_CONNECTIONS_PER_CALL_CDMA = 1;
    private static final int MAX_CONNECTIONS_PER_CALL_GSM = 5;
    private static final boolean REPEAT_POLLING = false;
    private static final boolean VDBG = false;
    private int m3WayCallFlashDelay;
    @UnsupportedAppUsage
    public GsmCdmaCall mBackgroundCall = new GsmCdmaCall(this);
    private RegistrantList mCallWaitingRegistrants = new RegistrantList();
    @VisibleForTesting
    public GsmCdmaConnection[] mConnections;
    private boolean mDesiredMute = false;
    private ArrayList<GsmCdmaConnection> mDroppedDuringPoll = new ArrayList(19);
    private BroadcastReceiver mEcmExitReceiver = new BroadcastReceiver(){

        public void onReceive(Context object, Intent object2) {
            if (object2.getAction().equals("android.intent.action.EMERGENCY_CALLBACK_MODE_CHANGED")) {
                boolean bl = object2.getBooleanExtra("phoneinECMState", false);
                object = GsmCdmaCallTracker.this;
                object2 = new StringBuilder();
                ((StringBuilder)object2).append("Received ACTION_EMERGENCY_CALLBACK_MODE_CHANGED isInEcm = ");
                ((StringBuilder)object2).append(bl);
                ((GsmCdmaCallTracker)((Object)object)).log(((StringBuilder)object2).toString());
                if (!bl) {
                    object = new ArrayList();
                    object.addAll(GsmCdmaCallTracker.this.mRingingCall.getConnections());
                    object.addAll(GsmCdmaCallTracker.this.mForegroundCall.getConnections());
                    object.addAll(GsmCdmaCallTracker.this.mBackgroundCall.getConnections());
                    if (GsmCdmaCallTracker.this.mPendingMO != null) {
                        object.add(GsmCdmaCallTracker.this.mPendingMO);
                    }
                    object = object.iterator();
                    while (object.hasNext()) {
                        object2 = (Connection)object.next();
                        if (object2 == null) continue;
                        ((Connection)object2).onExitedEcmMode();
                    }
                }
            }
        }
    };
    @UnsupportedAppUsage
    public GsmCdmaCall mForegroundCall = new GsmCdmaCall(this);
    private boolean mHangupPendingMO;
    private boolean mIsEcmTimerCanceled;
    private boolean mIsInEmergencyCall;
    private TelephonyMetrics mMetrics = TelephonyMetrics.getInstance();
    private int mPendingCallClirMode;
    private boolean mPendingCallInEcm;
    @UnsupportedAppUsage
    private GsmCdmaConnection mPendingMO;
    @UnsupportedAppUsage
    private GsmCdmaPhone mPhone;
    @UnsupportedAppUsage
    public GsmCdmaCall mRingingCall = new GsmCdmaCall(this);
    @UnsupportedAppUsage
    public PhoneConstants.State mState = PhoneConstants.State.IDLE;
    private RegistrantList mVoiceCallEndedRegistrants = new RegistrantList();
    private RegistrantList mVoiceCallStartedRegistrants = new RegistrantList();

    public GsmCdmaCallTracker(GsmCdmaPhone gsmCdmaPhone) {
        this.mPhone = gsmCdmaPhone;
        this.mCi = gsmCdmaPhone.mCi;
        this.mCi.registerForCallStateChanged(this, 2, null);
        this.mCi.registerForOn(this, 9, null);
        this.mCi.registerForNotAvailable(this, 10, null);
        gsmCdmaPhone = new IntentFilter();
        gsmCdmaPhone.addAction("android.intent.action.EMERGENCY_CALLBACK_MODE_CHANGED");
        this.mPhone.getContext().registerReceiver(this.mEcmExitReceiver, (IntentFilter)gsmCdmaPhone);
        this.updatePhoneType(true);
    }

    private void checkAndEnableDataCallAfterEmergencyCallDropped() {
        if (this.mIsInEmergencyCall) {
            this.mIsInEmergencyCall = false;
            boolean bl = this.mPhone.isInEcm();
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("checkAndEnableDataCallAfterEmergencyCallDropped,inEcm=");
            stringBuilder.append(bl);
            this.log(stringBuilder.toString());
            if (!bl) {
                this.mPhone.getDataEnabledSettings().setInternalDataEnabled(true);
                this.mPhone.notifyEmergencyCallRegistrants(false);
            }
            this.mPhone.sendEmergencyCallStateChange(false);
        }
    }

    private Connection checkMtFindNewRinging(DriverCall driverCall, int n) {
        Object object;
        StringBuilder stringBuilder = null;
        if (this.mConnections[n].getCall() == this.mRingingCall) {
            object = this.mConnections[n];
            stringBuilder = new StringBuilder();
            stringBuilder.append("Notify new ring ");
            stringBuilder.append(driverCall);
            this.log(stringBuilder.toString());
        } else {
            object = new StringBuilder();
            ((StringBuilder)object).append("Phantom call appeared ");
            ((StringBuilder)object).append(driverCall);
            Rlog.e((String)LOG_TAG, (String)((StringBuilder)object).toString());
            object = stringBuilder;
            if (driverCall.state != DriverCall.State.ALERTING) {
                object = stringBuilder;
                if (driverCall.state != DriverCall.State.DIALING) {
                    this.mConnections[n].onConnectedInOrOut();
                    object = stringBuilder;
                    if (driverCall.state == DriverCall.State.HOLDING) {
                        this.mConnections[n].onStartedHolding();
                        object = stringBuilder;
                    }
                }
            }
        }
        return object;
    }

    private Connection dialCdma(String string, int n, Bundle bundle) throws CallStateException {
        this.clearDisconnected();
        boolean bl = PhoneNumberUtils.isLocalEmergencyNumber((Context)this.mPhone.getContext(), (String)string);
        this.checkForDialIssues(bl);
        Object object = (TelephonyManager)this.mPhone.getContext().getSystemService("phone");
        String string2 = object.getNetworkCountryIsoForPhone(this.mPhone.getPhoneId());
        object = object.getSimCountryIsoForPhone(this.mPhone.getPhoneId());
        boolean bl2 = !TextUtils.isEmpty((CharSequence)string2) && !TextUtils.isEmpty((CharSequence)object) && !((String)object).equals(string2);
        if (bl2) {
            if ("us".equals(object)) {
                bl2 = bl2 && !"vi".equals(string2);
            } else if ("vi".equals(object)) {
                bl2 = bl2 && !"us".equals(string2);
            }
        }
        string2 = bl2 ? this.convertNumberIfNecessary(this.mPhone, string) : string;
        boolean bl3 = this.mPhone.isInEcm();
        if (bl3 && bl) {
            this.handleEcmTimer(1);
        }
        if (this.mForegroundCall.getState() == Call.State.ACTIVE) {
            return this.dialThreeWay(string2, bundle);
        }
        this.mPendingMO = new GsmCdmaConnection(this.mPhone, this.checkForTestEmergencyNumber(string2), this, this.mForegroundCall, bl);
        if (bundle != null) {
            object = new StringBuilder();
            ((StringBuilder)object).append("dialGsm - emergency dialer: ");
            ((StringBuilder)object).append(bundle.getBoolean("android.telecom.extra.IS_USER_INTENT_EMERGENCY_CALL"));
            Rlog.d((String)LOG_TAG, (String)((StringBuilder)object).toString());
            this.mPendingMO.setHasKnownUserIntentEmergency(bundle.getBoolean("android.telecom.extra.IS_USER_INTENT_EMERGENCY_CALL"));
        }
        this.mHangupPendingMO = false;
        if (this.mPendingMO.getAddress() != null && this.mPendingMO.getAddress().length() != 0 && this.mPendingMO.getAddress().indexOf(78) < 0) {
            this.setMute(false);
            this.disableDataCallInEmergencyCall(string2);
            if (!(!bl3 || bl3 && bl)) {
                this.mPhone.exitEmergencyCallbackMode();
                this.mPhone.setOnEcbModeExitResponse(this, 14, null);
                this.mPendingCallClirMode = n;
                this.mPendingCallInEcm = true;
            } else {
                this.mCi.dial(this.mPendingMO.getAddress(), this.mPendingMO.isEmergencyCall(), this.mPendingMO.getEmergencyNumberInfo(), this.mPendingMO.hasKnownUserIntentEmergency(), n, this.obtainCompleteMessage());
            }
        } else {
            this.mPendingMO.mCause = 7;
            this.pollCallsWhenSafe();
        }
        if (this.mNumberConverted) {
            this.mPendingMO.setConverted(string);
            this.mNumberConverted = false;
        }
        this.updatePhoneState();
        this.mPhone.notifyPreciseCallStateChanged();
        return this.mPendingMO;
    }

    private Connection dialGsm(String string, int n, Bundle bundle) throws CallStateException {
        return this.dialGsm(string, n, null, bundle);
    }

    private Connection dialThreeWay(String charSequence, Bundle bundle) {
        if (!this.mForegroundCall.isIdle()) {
            this.disableDataCallInEmergencyCall((String)charSequence);
            this.mPendingMO = new GsmCdmaConnection(this.mPhone, this.checkForTestEmergencyNumber((String)charSequence), this, this.mForegroundCall, this.mIsInEmergencyCall);
            if (bundle != null) {
                charSequence = new StringBuilder();
                ((StringBuilder)charSequence).append("dialThreeWay - emergency dialer ");
                ((StringBuilder)charSequence).append(bundle.getBoolean("android.telecom.extra.IS_USER_INTENT_EMERGENCY_CALL"));
                Rlog.d((String)LOG_TAG, (String)((StringBuilder)charSequence).toString());
                this.mPendingMO.setHasKnownUserIntentEmergency(bundle.getBoolean("android.telecom.extra.IS_USER_INTENT_EMERGENCY_CALL"));
            }
            this.m3WayCallFlashDelay = (charSequence = ((CarrierConfigManager)this.mPhone.getContext().getSystemService("carrier_config")).getConfigForSubId(this.mPhone.getSubId())) != null ? charSequence.getInt("cdma_3waycall_flash_delay_int") : 0;
            if (this.m3WayCallFlashDelay > 0) {
                this.mCi.sendCDMAFeatureCode("", this.obtainMessage(20));
            } else {
                this.mCi.sendCDMAFeatureCode(this.mPendingMO.getAddress(), this.obtainMessage(16));
            }
            return this.mPendingMO;
        }
        return null;
    }

    @UnsupportedAppUsage
    private void disableDataCallInEmergencyCall(String string) {
        if (PhoneNumberUtils.isLocalEmergencyNumber((Context)this.mPhone.getContext(), (String)string)) {
            this.log("disableDataCallInEmergencyCall");
            this.setIsInEmergencyCall();
        }
    }

    private void dumpState() {
        int n;
        List<Connection> list = new StringBuilder();
        ((StringBuilder)((Object)list)).append("Phone State:");
        ((StringBuilder)((Object)list)).append((Object)this.mState);
        Rlog.i((String)LOG_TAG, (String)((StringBuilder)((Object)list)).toString());
        list = new StringBuilder();
        ((StringBuilder)((Object)list)).append("Ringing call: ");
        ((StringBuilder)((Object)list)).append(this.mRingingCall.toString());
        Rlog.i((String)LOG_TAG, (String)((StringBuilder)((Object)list)).toString());
        list = this.mRingingCall.getConnections();
        int n2 = list.size();
        for (n = 0; n < n2; ++n) {
            Rlog.i((String)LOG_TAG, (String)list.get(n).toString());
        }
        list = new StringBuilder();
        ((StringBuilder)((Object)list)).append("Foreground call: ");
        ((StringBuilder)((Object)list)).append(this.mForegroundCall.toString());
        Rlog.i((String)LOG_TAG, (String)((StringBuilder)((Object)list)).toString());
        list = this.mForegroundCall.getConnections();
        n2 = list.size();
        for (n = 0; n < n2; ++n) {
            Rlog.i((String)LOG_TAG, (String)((Object)list.get(n)).toString());
        }
        list = new StringBuilder();
        ((StringBuilder)((Object)list)).append("Background call: ");
        ((StringBuilder)((Object)list)).append(this.mBackgroundCall.toString());
        Rlog.i((String)LOG_TAG, (String)((StringBuilder)((Object)list)).toString());
        list = this.mBackgroundCall.getConnections();
        n2 = list.size();
        for (n = 0; n < n2; ++n) {
            Rlog.i((String)LOG_TAG, (String)((Object)list.get(n)).toString());
        }
    }

    @UnsupportedAppUsage
    private void fakeHoldForegroundBeforeDial() {
        List list = (List)this.mForegroundCall.mConnections.clone();
        int n = list.size();
        for (int i = 0; i < n; ++i) {
            ((GsmCdmaConnection)list.get(i)).fakeHoldBeforeDial();
        }
    }

    private void flashAndSetGenericTrue() {
        this.mCi.sendCDMAFeatureCode("", this.obtainMessage(8));
        this.mPhone.notifyPreciseCallStateChanged();
    }

    private PhoneInternalInterface.SuppService getFailedService(int n) {
        if (n != 8) {
            switch (n) {
                default: {
                    return PhoneInternalInterface.SuppService.UNKNOWN;
                }
                case 13: {
                    return PhoneInternalInterface.SuppService.TRANSFER;
                }
                case 12: {
                    return PhoneInternalInterface.SuppService.SEPARATE;
                }
                case 11: 
            }
            return PhoneInternalInterface.SuppService.CONFERENCE;
        }
        return PhoneInternalInterface.SuppService.SWITCH;
    }

    private String getNetworkCountryIso() {
        String string = "";
        Handler handler = this.mPhone;
        String string2 = string;
        if (handler != null) {
            handler = handler.getServiceStateTracker();
            string2 = string;
            if (handler != null) {
                handler = handler.getLocaleTracker();
                string2 = string;
                if (handler != null) {
                    string2 = handler.getCurrentCountry();
                }
            }
        }
        return string2;
    }

    private void handleCallWaitingInfo(CdmaCallWaitingNotification cdmaCallWaitingNotification) {
        new GsmCdmaConnection(this.mPhone.getContext(), cdmaCallWaitingNotification, this, this.mRingingCall);
        this.updatePhoneState();
        this.notifyCallWaitingInfo(cdmaCallWaitingNotification);
    }

    @UnsupportedAppUsage
    private void handleEcmTimer(int n) {
        this.mPhone.handleTimerInEmergencyCallbackMode(n);
        if (n != 0) {
            if (n != 1) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("handleEcmTimer, unsupported action ");
                stringBuilder.append(n);
                Rlog.e((String)LOG_TAG, (String)stringBuilder.toString());
            } else {
                this.mIsEcmTimerCanceled = true;
            }
        } else {
            this.mIsEcmTimerCanceled = false;
        }
    }

    private void handleRadioNotAvailable() {
        this.pollCallsWhenSafe();
    }

    private void internalClearDisconnected() {
        this.mRingingCall.clearDisconnected();
        this.mForegroundCall.clearDisconnected();
        this.mBackgroundCall.clearDisconnected();
    }

    @UnsupportedAppUsage
    private boolean isPhoneTypeGsm() {
        int n = this.mPhone.getPhoneType();
        boolean bl = true;
        if (n != 1) {
            bl = false;
        }
        return bl;
    }

    static /* synthetic */ boolean lambda$isInOtaspCall$0(Connection connection) {
        boolean bl = connection instanceof GsmCdmaConnection && ((GsmCdmaConnection)connection).isOtaspCall();
        return bl;
    }

    private void logHangupEvent(GsmCdmaCall gsmCdmaCall) {
        int n = gsmCdmaCall.mConnections.size();
        for (int i = 0; i < n; ++i) {
            int n2;
            GsmCdmaConnection gsmCdmaConnection = (GsmCdmaConnection)gsmCdmaCall.mConnections.get(i);
            try {
                n2 = gsmCdmaConnection.getGsmCdmaIndex();
            }
            catch (CallStateException callStateException) {
                n2 = -1;
            }
            this.mMetrics.writeRilHangup(this.mPhone.getPhoneId(), gsmCdmaConnection, n2, this.getNetworkCountryIso());
        }
    }

    private void notifyCallWaitingInfo(CdmaCallWaitingNotification cdmaCallWaitingNotification) {
        RegistrantList registrantList = this.mCallWaitingRegistrants;
        if (registrantList != null) {
            registrantList.notifyRegistrants(new AsyncResult(null, (Object)cdmaCallWaitingNotification, null));
        }
    }

    @UnsupportedAppUsage
    private Message obtainCompleteMessage() {
        return this.obtainCompleteMessage(4);
    }

    @UnsupportedAppUsage
    private Message obtainCompleteMessage(int n) {
        ++this.mPendingOperations;
        this.mLastRelevantPoll = null;
        this.mNeedsPoll = true;
        return this.obtainMessage(n);
    }

    private void operationComplete() {
        --this.mPendingOperations;
        if (this.mPendingOperations == 0 && this.mNeedsPoll) {
            this.mLastRelevantPoll = this.obtainMessage(1);
            this.mCi.getCurrentCalls(this.mLastRelevantPoll);
        } else if (this.mPendingOperations < 0) {
            Rlog.e((String)LOG_TAG, (String)"GsmCdmaCallTracker.pendingOperations < 0");
            this.mPendingOperations = 0;
        }
    }

    private void reset() {
        GsmCdmaConnection gsmCdmaConnection2;
        Rlog.d((String)LOG_TAG, (String)"reset");
        for (GsmCdmaConnection gsmCdmaConnection2 : this.mConnections) {
            if (gsmCdmaConnection2 == null) continue;
            gsmCdmaConnection2.onDisconnect(36);
            gsmCdmaConnection2.dispose();
        }
        gsmCdmaConnection2 = this.mPendingMO;
        if (gsmCdmaConnection2 != null) {
            gsmCdmaConnection2.onDisconnect(36);
            this.mPendingMO.dispose();
        }
        this.mConnections = null;
        this.mPendingMO = null;
        this.clearDisconnected();
    }

    private void updateMetrics(GsmCdmaConnection[] arrgsmCdmaConnection) {
        ArrayList<GsmCdmaConnection> arrayList = new ArrayList<GsmCdmaConnection>();
        for (GsmCdmaConnection gsmCdmaConnection : arrgsmCdmaConnection) {
            if (gsmCdmaConnection == null) continue;
            arrayList.add(gsmCdmaConnection);
        }
        this.mMetrics.writeRilCallList(this.mPhone.getPhoneId(), arrayList, this.getNetworkCountryIso());
    }

    @UnsupportedAppUsage
    private void updatePhoneState() {
        Object object;
        PhoneConstants.State state = this.mState;
        if (this.mRingingCall.isRinging()) {
            this.mState = PhoneConstants.State.RINGING;
        } else if (this.mPendingMO == null && this.mForegroundCall.isIdle() && this.mBackgroundCall.isIdle()) {
            object = this.mPhone.getImsPhone();
            if (this.mState == PhoneConstants.State.OFFHOOK && object != null) {
                ((Phone)object).callEndCleanupHandOverCallIfAny();
            }
            this.mState = PhoneConstants.State.IDLE;
        } else {
            this.mState = PhoneConstants.State.OFFHOOK;
        }
        if (this.mState == PhoneConstants.State.IDLE && state != this.mState) {
            this.mVoiceCallEndedRegistrants.notifyRegistrants(new AsyncResult(null, null, null));
        } else if (state == PhoneConstants.State.IDLE && state != this.mState) {
            this.mVoiceCallStartedRegistrants.notifyRegistrants(new AsyncResult(null, null, null));
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("update phone state, old=");
        ((StringBuilder)object).append((Object)state);
        ((StringBuilder)object).append(" new=");
        ((StringBuilder)object).append((Object)this.mState);
        this.log(((StringBuilder)object).toString());
        if (this.mState != state) {
            this.mPhone.notifyPhoneStateChanged();
            this.mMetrics.writePhoneState(this.mPhone.getPhoneId(), this.mState);
        }
    }

    private void updatePhoneType(boolean bl) {
        if (!bl) {
            this.reset();
            this.pollCallsWhenSafe();
        }
        if (this.mPhone.isPhoneTypeGsm()) {
            this.mConnections = new GsmCdmaConnection[19];
            this.mCi.unregisterForCallWaitingInfo(this);
            if (this.mIsInEmergencyCall) {
                this.mPhone.getDataEnabledSettings().setInternalDataEnabled(true);
            }
        } else {
            this.mConnections = new GsmCdmaConnection[8];
            this.mPendingCallInEcm = false;
            this.mIsInEmergencyCall = false;
            this.mPendingCallClirMode = 0;
            this.mIsEcmTimerCanceled = false;
            this.m3WayCallFlashDelay = 0;
            this.mCi.registerForCallWaitingInfo(this, 15, null);
        }
    }

    public void acceptCall() throws CallStateException {
        block7 : {
            block6 : {
                block5 : {
                    if (this.mRingingCall.getState() != Call.State.INCOMING) break block5;
                    Rlog.i((String)"phone", (String)"acceptCall: incoming...");
                    this.setMute(false);
                    this.mCi.acceptCall(this.obtainCompleteMessage());
                    break block6;
                }
                if (this.mRingingCall.getState() != Call.State.WAITING) break block7;
                if (this.isPhoneTypeGsm()) {
                    this.setMute(false);
                } else {
                    GsmCdmaConnection gsmCdmaConnection = (GsmCdmaConnection)this.mRingingCall.getLatestConnection();
                    gsmCdmaConnection.updateParent(this.mRingingCall, this.mForegroundCall);
                    gsmCdmaConnection.onConnectedInOrOut();
                    this.updatePhoneState();
                }
                this.switchWaitingOrHoldingAndActive();
            }
            return;
        }
        throw new CallStateException("phone not ringing");
    }

    public boolean canConference() {
        boolean bl = this.mForegroundCall.getState() == Call.State.ACTIVE && this.mBackgroundCall.getState() == Call.State.HOLDING && !this.mBackgroundCall.isFull() && !this.mForegroundCall.isFull();
        return bl;
    }

    public boolean canTransfer() {
        boolean bl = this.isPhoneTypeGsm();
        boolean bl2 = false;
        if (bl) {
            if ((this.mForegroundCall.getState() == Call.State.ACTIVE || this.mForegroundCall.getState() == Call.State.ALERTING || this.mForegroundCall.getState() == Call.State.DIALING) && this.mBackgroundCall.getState() == Call.State.HOLDING) {
                bl2 = true;
            }
            return bl2;
        }
        Rlog.e((String)LOG_TAG, (String)"canTransfer: not possible in CDMA");
        return false;
    }

    public void checkForDialIssues(boolean bl) throws CallStateException {
        String string = SystemProperties.get((String)"ro.telephony.disable-call", (String)"false");
        if (this.mCi.getRadioState() == 1) {
            if (!string.equals("true")) {
                if (this.mPendingMO == null) {
                    if (!this.mRingingCall.isRinging()) {
                        if (this.isPhoneTypeGsm() && this.mForegroundCall.getState().isAlive() && this.mBackgroundCall.getState().isAlive()) {
                            throw new CallStateException(6, "There is already a foreground and background call.");
                        }
                        if (!this.isPhoneTypeGsm() && this.mForegroundCall.getState().isAlive() && this.mForegroundCall.getState() != Call.State.ACTIVE && this.mBackgroundCall.getState().isAlive()) {
                            throw new CallStateException(6, "There is already a foreground and background call.");
                        }
                        if (!bl && this.isInOtaspCall()) {
                            throw new CallStateException(7, "OTASP provisioning is in process.");
                        }
                        return;
                    }
                    throw new CallStateException(4, "Can't call while a call is ringing.");
                }
                throw new CallStateException(3, "A call is already dialing.");
            }
            throw new CallStateException(5, "Calling disabled via ro.telephony.disable-call property");
        }
        throw new CallStateException(2, "Modem not powered");
    }

    @Override
    public void cleanupCalls() {
        this.pollCallsWhenSafe();
    }

    @UnsupportedAppUsage
    public void clearDisconnected() {
        this.internalClearDisconnected();
        this.updatePhoneState();
        this.mPhone.notifyPreciseCallStateChanged();
    }

    public void conference() {
        if (this.isPhoneTypeGsm()) {
            this.mCi.conference(this.obtainCompleteMessage(11));
        } else {
            this.flashAndSetGenericTrue();
        }
    }

    public Connection dial(String string, Bundle bundle) throws CallStateException {
        if (this.isPhoneTypeGsm()) {
            return this.dialGsm(string, 0, bundle);
        }
        return this.dialCdma(string, 0, bundle);
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public Connection dialGsm(String object, int n, UUSInfo uUSInfo, Bundle bundle) throws CallStateException {
        synchronized (this) {
            void var2_2;
            void var4_4;
            void var3_3;
            GsmCdmaConnection gsmCdmaConnection;
            this.clearDisconnected();
            boolean bl = PhoneNumberUtils.isLocalEmergencyNumber((Context)this.mPhone.getContext(), (String)object);
            this.checkForDialIssues(bl);
            CharSequence charSequence = this.convertNumberIfNecessary(this.mPhone, (String)object);
            if (this.mForegroundCall.getState() == Call.State.ACTIVE) {
                this.switchWaitingOrHoldingAndActive();
                try {
                    Thread.sleep(500L);
                }
                catch (InterruptedException interruptedException) {
                    // empty catch block
                }
                this.fakeHoldForegroundBeforeDial();
            }
            if (this.mForegroundCall.getState() != Call.State.IDLE) {
                object = new CallStateException("cannot dial in current state");
                throw object;
            }
            this.mPendingMO = gsmCdmaConnection = new GsmCdmaConnection(this.mPhone, this.checkForTestEmergencyNumber((String)charSequence), this, this.mForegroundCall, bl);
            if (var4_4 != null) {
                charSequence = new StringBuilder();
                ((StringBuilder)charSequence).append("dialGsm - emergency dialer: ");
                ((StringBuilder)charSequence).append(var4_4.getBoolean("android.telecom.extra.IS_USER_INTENT_EMERGENCY_CALL"));
                Rlog.d((String)LOG_TAG, (String)((StringBuilder)charSequence).toString());
                this.mPendingMO.setHasKnownUserIntentEmergency(var4_4.getBoolean("android.telecom.extra.IS_USER_INTENT_EMERGENCY_CALL"));
            }
            this.mHangupPendingMO = false;
            this.mMetrics.writeRilDial(this.mPhone.getPhoneId(), this.mPendingMO, (int)var2_2, (UUSInfo)var3_3);
            if (this.mPendingMO.getAddress() != null && this.mPendingMO.getAddress().length() != 0 && this.mPendingMO.getAddress().indexOf(78) < 0) {
                this.setMute(false);
                this.mCi.dial(this.mPendingMO.getAddress(), this.mPendingMO.isEmergencyCall(), this.mPendingMO.getEmergencyNumberInfo(), this.mPendingMO.hasKnownUserIntentEmergency(), (int)var2_2, (UUSInfo)var3_3, this.obtainCompleteMessage());
            } else {
                this.mPendingMO.mCause = 7;
                this.pollCallsWhenSafe();
            }
            if (this.mNumberConverted) {
                this.mPendingMO.setConverted((String)object);
                this.mNumberConverted = false;
            }
            this.updatePhoneState();
            this.mPhone.notifyPreciseCallStateChanged();
            return this.mPendingMO;
        }
    }

    public Connection dialGsm(String string, UUSInfo uUSInfo, Bundle bundle) throws CallStateException {
        return this.dialGsm(string, 0, uUSInfo, bundle);
    }

    public void dispatchCsCallRadioTech(int n) {
        GsmCdmaConnection[] arrgsmCdmaConnection = this.mConnections;
        if (arrgsmCdmaConnection == null) {
            this.log("dispatchCsCallRadioTech: mConnections is null");
            return;
        }
        for (GsmCdmaConnection gsmCdmaConnection : arrgsmCdmaConnection) {
            if (gsmCdmaConnection == null) continue;
            gsmCdmaConnection.setCallRadioTech(n);
        }
    }

    @Override
    public void dump(FileDescriptor object, PrintWriter printWriter, String[] arrstring) {
        int n;
        printWriter.println("GsmCdmaCallTracker extends:");
        super.dump((FileDescriptor)object, printWriter, arrstring);
        object = new StringBuilder();
        ((StringBuilder)object).append("mConnections: length=");
        ((StringBuilder)object).append(this.mConnections.length);
        printWriter.println(((StringBuilder)object).toString());
        for (n = 0; n < this.mConnections.length; ++n) {
            printWriter.printf("  mConnections[%d]=%s\n", n, this.mConnections[n]);
        }
        object = new StringBuilder();
        ((StringBuilder)object).append(" mVoiceCallEndedRegistrants=");
        ((StringBuilder)object).append((Object)this.mVoiceCallEndedRegistrants);
        printWriter.println(((StringBuilder)object).toString());
        object = new StringBuilder();
        ((StringBuilder)object).append(" mVoiceCallStartedRegistrants=");
        ((StringBuilder)object).append((Object)this.mVoiceCallStartedRegistrants);
        printWriter.println(((StringBuilder)object).toString());
        if (!this.isPhoneTypeGsm()) {
            object = new StringBuilder();
            ((StringBuilder)object).append(" mCallWaitingRegistrants=");
            ((StringBuilder)object).append((Object)this.mCallWaitingRegistrants);
            printWriter.println(((StringBuilder)object).toString());
        }
        object = new StringBuilder();
        ((StringBuilder)object).append(" mDroppedDuringPoll: size=");
        ((StringBuilder)object).append(this.mDroppedDuringPoll.size());
        printWriter.println(((StringBuilder)object).toString());
        for (n = 0; n < this.mDroppedDuringPoll.size(); ++n) {
            printWriter.printf("  mDroppedDuringPoll[%d]=%s\n", n, this.mDroppedDuringPoll.get(n));
        }
        object = new StringBuilder();
        ((StringBuilder)object).append(" mRingingCall=");
        ((StringBuilder)object).append(this.mRingingCall);
        printWriter.println(((StringBuilder)object).toString());
        object = new StringBuilder();
        ((StringBuilder)object).append(" mForegroundCall=");
        ((StringBuilder)object).append(this.mForegroundCall);
        printWriter.println(((StringBuilder)object).toString());
        object = new StringBuilder();
        ((StringBuilder)object).append(" mBackgroundCall=");
        ((StringBuilder)object).append(this.mBackgroundCall);
        printWriter.println(((StringBuilder)object).toString());
        object = new StringBuilder();
        ((StringBuilder)object).append(" mPendingMO=");
        ((StringBuilder)object).append(this.mPendingMO);
        printWriter.println(((StringBuilder)object).toString());
        object = new StringBuilder();
        ((StringBuilder)object).append(" mHangupPendingMO=");
        ((StringBuilder)object).append(this.mHangupPendingMO);
        printWriter.println(((StringBuilder)object).toString());
        object = new StringBuilder();
        ((StringBuilder)object).append(" mPhone=");
        ((StringBuilder)object).append(this.mPhone);
        printWriter.println(((StringBuilder)object).toString());
        object = new StringBuilder();
        ((StringBuilder)object).append(" mDesiredMute=");
        ((StringBuilder)object).append(this.mDesiredMute);
        printWriter.println(((StringBuilder)object).toString());
        object = new StringBuilder();
        ((StringBuilder)object).append(" mState=");
        ((StringBuilder)object).append((Object)this.mState);
        printWriter.println(((StringBuilder)object).toString());
        if (!this.isPhoneTypeGsm()) {
            object = new StringBuilder();
            ((StringBuilder)object).append(" mPendingCallInEcm=");
            ((StringBuilder)object).append(this.mPendingCallInEcm);
            printWriter.println(((StringBuilder)object).toString());
            object = new StringBuilder();
            ((StringBuilder)object).append(" mIsInEmergencyCall=");
            ((StringBuilder)object).append(this.mIsInEmergencyCall);
            printWriter.println(((StringBuilder)object).toString());
            object = new StringBuilder();
            ((StringBuilder)object).append(" mPendingCallClirMode=");
            ((StringBuilder)object).append(this.mPendingCallClirMode);
            printWriter.println(((StringBuilder)object).toString());
            object = new StringBuilder();
            ((StringBuilder)object).append(" mIsEcmTimerCanceled=");
            ((StringBuilder)object).append(this.mIsEcmTimerCanceled);
            printWriter.println(((StringBuilder)object).toString());
        }
    }

    public void explicitCallTransfer() {
        this.mCi.explicitCallTransfer(this.obtainCompleteMessage(13));
    }

    protected void finalize() {
        Rlog.d((String)LOG_TAG, (String)"GsmCdmaCallTracker finalized");
    }

    public GsmCdmaConnection getConnectionByIndex(GsmCdmaCall gsmCdmaCall, int n) throws CallStateException {
        int n2 = gsmCdmaCall.mConnections.size();
        for (int i = 0; i < n2; ++i) {
            GsmCdmaConnection gsmCdmaConnection = (GsmCdmaConnection)gsmCdmaCall.mConnections.get(i);
            if (gsmCdmaConnection.mDisconnected || gsmCdmaConnection.getGsmCdmaIndex() != n) continue;
            return gsmCdmaConnection;
        }
        return null;
    }

    public int getMaxConnectionsPerCall() {
        int n = this.mPhone.isPhoneTypeGsm() ? 5 : 1;
        return n;
    }

    public boolean getMute() {
        return this.mDesiredMute;
    }

    @UnsupportedAppUsage
    @Override
    public GsmCdmaPhone getPhone() {
        return this.mPhone;
    }

    @Override
    public PhoneConstants.State getState() {
        return this.mState;
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void handleMessage(Message object) {
        int n = ((Message)object).what;
        if (n == 1) {
            Rlog.d((String)LOG_TAG, (String)"Event EVENT_POLL_CALLS_RESULT Received");
            if (object != this.mLastRelevantPoll) return;
            this.mNeedsPoll = false;
            this.mLastRelevantPoll = null;
            this.handlePollCalls((AsyncResult)((Message)object).obj);
            return;
        }
        if (n != 2 && n != 3) {
            int n2;
            int n3;
            if (n == 4) {
                this.operationComplete();
                return;
            }
            if (n != 5) {
                if (n != 20) {
                    Object object2;
                    switch (n) {
                        default: {
                            StringBuilder stringBuilder = new StringBuilder();
                            stringBuilder.append("unexpected event ");
                            stringBuilder.append(((Message)object).what);
                            stringBuilder.append(" not handled by phone type ");
                            stringBuilder.append(this.mPhone.getPhoneType());
                            throw new RuntimeException(stringBuilder.toString());
                        }
                        case 16: {
                            if (!this.isPhoneTypeGsm()) {
                                if (((AsyncResult)object.obj).exception != null) return;
                                this.mPendingMO.onConnectedInOrOut();
                                this.mPendingMO = null;
                                return;
                            }
                            StringBuilder stringBuilder = new StringBuilder();
                            stringBuilder.append("unexpected event ");
                            stringBuilder.append(((Message)object).what);
                            stringBuilder.append(" not handled by phone type ");
                            stringBuilder.append(this.mPhone.getPhoneType());
                            throw new RuntimeException(stringBuilder.toString());
                        }
                        case 15: {
                            if (!this.isPhoneTypeGsm()) {
                                object = (AsyncResult)((Message)object).obj;
                                if (((AsyncResult)object).exception != null) return;
                                this.handleCallWaitingInfo((CdmaCallWaitingNotification)((AsyncResult)object).result);
                                Rlog.d((String)LOG_TAG, (String)"Event EVENT_CALL_WAITING_INFO_CDMA Received");
                                return;
                            }
                            StringBuilder stringBuilder = new StringBuilder();
                            stringBuilder.append("unexpected event ");
                            stringBuilder.append(((Message)object).what);
                            stringBuilder.append(" not handled by phone type ");
                            stringBuilder.append(this.mPhone.getPhoneType());
                            throw new RuntimeException(stringBuilder.toString());
                        }
                        case 14: {
                            if (this.isPhoneTypeGsm()) {
                                StringBuilder stringBuilder = new StringBuilder();
                                stringBuilder.append("unexpected event ");
                                stringBuilder.append(((Message)object).what);
                                stringBuilder.append(" not handled by phone type ");
                                stringBuilder.append(this.mPhone.getPhoneType());
                                throw new RuntimeException(stringBuilder.toString());
                            }
                            if (this.mPendingCallInEcm) {
                                this.mCi.dial(this.mPendingMO.getAddress(), this.mPendingMO.isEmergencyCall(), this.mPendingMO.getEmergencyNumberInfo(), this.mPendingMO.hasKnownUserIntentEmergency(), this.mPendingCallClirMode, this.obtainCompleteMessage());
                                this.mPendingCallInEcm = false;
                            }
                            this.mPhone.unsetOnEcbModeExitResponse(this);
                            return;
                        }
                        case 11: {
                            if (!this.isPhoneTypeGsm() || ((AsyncResult)object.obj).exception == null || (object2 = this.mForegroundCall.getLatestConnection()) == null) break;
                            ((Connection)object2).onConferenceMergeFailed();
                            break;
                        }
                        case 10: {
                            this.handleRadioNotAvailable();
                            return;
                        }
                        case 9: {
                            this.handleRadioAvailable();
                            return;
                        }
                        case 8: 
                        case 12: 
                        case 13: 
                    }
                    if (this.isPhoneTypeGsm()) {
                        if (((AsyncResult)object.obj).exception != null) {
                            this.mPhone.notifySuppServiceFailed(this.getFailedService(((Message)object).what));
                        }
                        this.operationComplete();
                        return;
                    }
                    if (((Message)object).what == 8) {
                        return;
                    }
                    object2 = new StringBuilder();
                    ((StringBuilder)object2).append("unexpected event ");
                    ((StringBuilder)object2).append(((Message)object).what);
                    ((StringBuilder)object2).append(" not handled by phone type ");
                    ((StringBuilder)object2).append(this.mPhone.getPhoneType());
                    throw new RuntimeException(((StringBuilder)object2).toString());
                }
                if (this.isPhoneTypeGsm()) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("unexpected event ");
                    stringBuilder.append(((Message)object).what);
                    stringBuilder.append(" not handled by phone type ");
                    stringBuilder.append(this.mPhone.getPhoneType());
                    throw new RuntimeException(stringBuilder.toString());
                }
                if (((AsyncResult)object.obj).exception == null) {
                    this.postDelayed(new Runnable(){

                        @Override
                        public void run() {
                            if (GsmCdmaCallTracker.this.mPendingMO != null) {
                                GsmCdmaCallTracker.this.mCi.sendCDMAFeatureCode(GsmCdmaCallTracker.this.mPendingMO.getAddress(), GsmCdmaCallTracker.this.obtainMessage(16));
                            }
                        }
                    }, (long)this.m3WayCallFlashDelay);
                    return;
                }
                this.mPendingMO = null;
                Rlog.w((String)LOG_TAG, (String)"exception happened on Blank Flash for 3-way call");
                return;
            }
            Object var4_10 = null;
            CellLocation cellLocation = null;
            object = (AsyncResult)((Message)object).obj;
            this.operationComplete();
            if (((AsyncResult)object).exception != null) {
                if (((AsyncResult)object).exception instanceof CommandException) {
                    object = (CommandException)((AsyncResult)object).exception;
                    n = 3.$SwitchMap$com$android$internal$telephony$CommandException$Error[((CommandException)object).getCommandError().ordinal()];
                    if (n != 1 && n != 2 && n != 3 && n != 4) {
                        n = 16;
                        object = cellLocation;
                    } else {
                        object = ((CommandException)object).getCommandError().toString();
                        n = 65535;
                    }
                } else {
                    n = 16;
                    Rlog.i((String)LOG_TAG, (String)"Exception during getLastCallFailCause, assuming normal disconnect");
                    object = var4_10;
                }
            } else {
                object = (LastCallFailCause)((AsyncResult)object).result;
                n = ((LastCallFailCause)object).causeCode;
                object = ((LastCallFailCause)object).vendorCause;
            }
            if (n == 34 || n == 41 || n == 42 || n == 44 || n == 49 || n == 58 || n == 65535) {
                cellLocation = this.mPhone.getCellLocation();
                n2 = n3 = -1;
                if (cellLocation != null) {
                    if (cellLocation instanceof GsmCellLocation) {
                        n2 = ((GsmCellLocation)cellLocation).getCid();
                    } else {
                        n2 = n3;
                        if (cellLocation instanceof CdmaCellLocation) {
                            n2 = ((CdmaCellLocation)cellLocation).getBaseStationId();
                        }
                    }
                }
                EventLog.writeEvent((int)50106, (Object[])new Object[]{n, n2, TelephonyManager.getDefault().getNetworkType()});
            }
            n2 = 0;
            n3 = this.mDroppedDuringPoll.size();
            do {
                if (n2 >= n3) {
                    this.updatePhoneState();
                    this.mPhone.notifyPreciseCallStateChanged();
                    this.mMetrics.writeRilCallList(this.mPhone.getPhoneId(), this.mDroppedDuringPoll, this.getNetworkCountryIso());
                    this.mDroppedDuringPoll.clear();
                    return;
                }
                this.mDroppedDuringPoll.get(n2).onRemoteDisconnect(n, (String)object);
                ++n2;
            } while (true);
        }
        this.pollCallsWhenSafe();
    }

    /*
     * WARNING - combined exceptions agressively - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    protected void handlePollCalls(AsyncResult object) {
        synchronized (this) {
            int n;
            Object object2;
            int n2;
            Object object3;
            if (((AsyncResult)object).exception == null) {
                object2 = (ArrayList<GsmCdmaConnection>)((AsyncResult)object).result;
            } else {
                if (!this.isCommandExceptionRadioNotAvailable(((AsyncResult)object).exception)) {
                    this.pollCallsAfterDelay();
                    return;
                }
                object2 = new ArrayList<GsmCdmaConnection>();
            }
            ArrayList<GsmCdmaConnection> arrayList = new ArrayList<GsmCdmaConnection>();
            int n3 = 0;
            int n4 = 0;
            int n5 = 0;
            int n6 = this.mHandoverConnections.size();
            int n7 = 1;
            int n8 = 0;
            int n9 = object2.size();
            object = null;
            Object object4 = null;
            Object object5 = object2;
            for (int i = 0; i < this.mConnections.length; ++i) {
                int n10;
                block67 : {
                    block69 : {
                        block71 : {
                            block72 : {
                                block70 : {
                                    Object object6;
                                    block68 : {
                                        block66 : {
                                            object3 = this.mConnections[i];
                                            if (n8 < n9) {
                                                object2 = (DriverCall)object5.get(n8);
                                                if (((DriverCall)object2).index == i + 1) {
                                                    ++n8;
                                                } else {
                                                    object2 = null;
                                                }
                                            } else {
                                                object2 = null;
                                            }
                                            if (object3 != null || object2 != null) {
                                                n7 = 0;
                                            }
                                            if (object3 != null || object2 == null) break block66;
                                            if (this.mPendingMO != null && this.mPendingMO.compareTo((DriverCall)object2)) {
                                                this.mConnections[i] = this.mPendingMO;
                                                this.mPendingMO.mIndex = i;
                                                this.mPendingMO.update((DriverCall)object2);
                                                this.mPendingMO = null;
                                                if (this.mHangupPendingMO) {
                                                    this.mHangupPendingMO = false;
                                                    if (!this.isPhoneTypeGsm() && this.mIsEcmTimerCanceled) {
                                                        this.handleEcmTimer(0);
                                                    }
                                                    try {
                                                        object = new StringBuilder();
                                                        ((StringBuilder)object).append("poll: hangupPendingMO, hangup conn ");
                                                        ((StringBuilder)object).append(i);
                                                        this.log(((StringBuilder)object).toString());
                                                        this.hangup(this.mConnections[i]);
                                                    }
                                                    catch (CallStateException callStateException) {
                                                        Rlog.e((String)LOG_TAG, (String)"unexpected error on hangup");
                                                    }
                                                    return;
                                                }
                                                n = n9;
                                                object2 = object;
                                                n9 = n5;
                                                n5 = n8;
                                            } else {
                                                object3 = new StringBuilder();
                                                ((StringBuilder)object3).append("pendingMo=");
                                                ((StringBuilder)object3).append(this.mPendingMO);
                                                ((StringBuilder)object3).append(", dc=");
                                                ((StringBuilder)object3).append(object2);
                                                this.log(((StringBuilder)object3).toString());
                                                object6 = this.mConnections;
                                                n3 = n5;
                                                object6[i] = object3 = new GsmCdmaConnection(this.mPhone, (DriverCall)object2, this, i);
                                                object3 = this.getHoConnection((DriverCall)object2);
                                                if (object3 != null) {
                                                    this.mConnections[i].migrateFrom((Connection)object3);
                                                    if (((Connection)object3).mPreHandoverState != Call.State.ACTIVE && ((Connection)object3).mPreHandoverState != Call.State.HOLDING && ((DriverCall)object2).state == DriverCall.State.ACTIVE) {
                                                        this.mConnections[i].onConnectedInOrOut();
                                                    } else {
                                                        this.mConnections[i].onConnectedConnectionMigrated();
                                                    }
                                                    this.mHandoverConnections.remove(object3);
                                                    if (this.isPhoneTypeGsm()) {
                                                        object6 = this.mHandoverConnections.iterator();
                                                        while (object6.hasNext()) {
                                                            object2 = (Connection)object6.next();
                                                            StringBuilder stringBuilder = new StringBuilder();
                                                            stringBuilder.append("HO Conn state is ");
                                                            stringBuilder.append((Object)((Connection)object2).mPreHandoverState);
                                                            Rlog.i((String)LOG_TAG, (String)stringBuilder.toString());
                                                            if (((Connection)object2).mPreHandoverState != this.mConnections[i].getState()) continue;
                                                            stringBuilder = new StringBuilder();
                                                            stringBuilder.append("Removing HO conn ");
                                                            stringBuilder.append(object3);
                                                            stringBuilder.append((Object)((Connection)object2).mPreHandoverState);
                                                            Rlog.i((String)LOG_TAG, (String)stringBuilder.toString());
                                                            object6.remove();
                                                        }
                                                        n5 = n8;
                                                        n = n9;
                                                    } else {
                                                        n = n9;
                                                        n5 = n8;
                                                    }
                                                    this.mPhone.notifyHandoverStateChanged(this.mConnections[i]);
                                                    object2 = object;
                                                    n9 = n3;
                                                } else {
                                                    n10 = n7;
                                                    int n11 = n9;
                                                    object4 = object3 = this.checkMtFindNewRinging((DriverCall)object2, i);
                                                    object2 = object;
                                                    n9 = n3;
                                                    n7 = n10;
                                                    n5 = n8;
                                                    n = n11;
                                                    if (object3 == null) {
                                                        if (this.isPhoneTypeGsm()) {
                                                            arrayList.add(this.mConnections[i]);
                                                            n9 = 1;
                                                            object4 = object3;
                                                            object2 = object;
                                                            n7 = n10;
                                                            n5 = n8;
                                                            n = n11;
                                                        } else {
                                                            object2 = this.mConnections[i];
                                                            n9 = 1;
                                                            n = n11;
                                                            n5 = n8;
                                                            n7 = n10;
                                                            object4 = object3;
                                                        }
                                                    }
                                                }
                                            }
                                            n8 = 1;
                                            object = object2;
                                            break block67;
                                        }
                                        n10 = n8;
                                        n = n9;
                                        if (object3 == null || object2 != null) break block68;
                                        if (this.isPhoneTypeGsm()) {
                                            this.mDroppedDuringPoll.add((GsmCdmaConnection)object3);
                                        } else {
                                            n8 = this.mForegroundCall.mConnections.size();
                                            for (n9 = 0; n9 < n8; ++n9) {
                                                object2 = new StringBuilder();
                                                ((StringBuilder)object2).append("adding fgCall cn ");
                                                ((StringBuilder)object2).append(n9);
                                                ((StringBuilder)object2).append(" to droppedDuringPoll");
                                                this.log(((StringBuilder)object2).toString());
                                                object2 = (GsmCdmaConnection)this.mForegroundCall.mConnections.get(n9);
                                                this.mDroppedDuringPoll.add((GsmCdmaConnection)object2);
                                            }
                                            n8 = this.mRingingCall.mConnections.size();
                                            for (n9 = 0; n9 < n8; ++n9) {
                                                object2 = new StringBuilder();
                                                ((StringBuilder)object2).append("adding rgCall cn ");
                                                ((StringBuilder)object2).append(n9);
                                                ((StringBuilder)object2).append(" to droppedDuringPoll");
                                                this.log(((StringBuilder)object2).toString());
                                                object2 = (GsmCdmaConnection)this.mRingingCall.mConnections.get(n9);
                                                this.mDroppedDuringPoll.add((GsmCdmaConnection)object2);
                                            }
                                            if (this.mIsEcmTimerCanceled) {
                                                this.handleEcmTimer(0);
                                            }
                                            this.checkAndEnableDataCallAfterEmergencyCallDropped();
                                        }
                                        this.mConnections[i] = null;
                                        break block69;
                                    }
                                    if (object3 == null || object2 == null || ((GsmCdmaConnection)object3).compareTo((DriverCall)object2) || !this.isPhoneTypeGsm()) break block70;
                                    this.mDroppedDuringPoll.add((GsmCdmaConnection)object3);
                                    object6 = this.mConnections;
                                    object6[i] = object3 = new GsmCdmaConnection(this.mPhone, (DriverCall)object2, this, i);
                                    if (this.mConnections[i].getCall() == this.mRingingCall) {
                                        object4 = this.mConnections[i];
                                    }
                                    n8 = 1;
                                    n9 = n5;
                                    n5 = n10;
                                    break block67;
                                }
                                if (object3 == null || object2 == null) break block69;
                                if (this.isPhoneTypeGsm() || ((Connection)object3).isIncoming() == ((DriverCall)object2).isMT) break block71;
                                if (!((DriverCall)object2).isMT) break block72;
                                this.mDroppedDuringPoll.add((GsmCdmaConnection)object3);
                                object4 = this.checkMtFindNewRinging((DriverCall)object2, i);
                                if (object4 == null) {
                                    object = object3;
                                    n5 = 1;
                                }
                                this.checkAndEnableDataCallAfterEmergencyCallDropped();
                                n9 = n5;
                                n8 = n3;
                                n5 = n10;
                                break block67;
                            }
                            object3 = new StringBuilder();
                            ((StringBuilder)object3).append("Error in RIL, Phantom call appeared ");
                            ((StringBuilder)object3).append(object2);
                            Rlog.e((String)LOG_TAG, (String)((StringBuilder)object3).toString());
                            break block69;
                        }
                        n2 = ((GsmCdmaConnection)object3).update((DriverCall)object2);
                        n9 = n3 == 0 && n2 == 0 ? 0 : 1;
                        n8 = n9;
                        n9 = n5;
                        n5 = n10;
                        break block67;
                    }
                    n9 = n5;
                    n5 = n10;
                    n8 = n3;
                }
                n10 = n5;
                n3 = n8;
                n5 = n9;
                n8 = n10;
                n9 = n;
            }
            n = n4;
            if (!this.isPhoneTypeGsm() && n7 != 0) {
                this.checkAndEnableDataCallAfterEmergencyCallDropped();
            }
            if (this.mPendingMO != null) {
                object2 = new StringBuilder();
                ((StringBuilder)object2).append("Pending MO dropped before poll fg state:");
                ((StringBuilder)object2).append((Object)this.mForegroundCall.getState());
                Rlog.d((String)LOG_TAG, (String)((StringBuilder)object2).toString());
                this.mDroppedDuringPoll.add(this.mPendingMO);
                this.mPendingMO = null;
                this.mHangupPendingMO = false;
                if (!this.isPhoneTypeGsm()) {
                    if (this.mPendingCallInEcm) {
                        this.mPendingCallInEcm = false;
                    }
                    this.checkAndEnableDataCallAfterEmergencyCallDropped();
                }
            }
            if (object4 != null) {
                this.mPhone.notifyNewRingingConnection((Connection)object4);
            }
            object5 = new ArrayList();
            n9 = this.mDroppedDuringPoll.size() - 1;
            n7 = n5;
            for (n5 = n9; n5 >= 0; --n5) {
                block74 : {
                    block75 : {
                        block73 : {
                            object3 = this.mDroppedDuringPoll.get(n5);
                            n8 = 0;
                            if (!((Connection)object3).isIncoming() || ((Connection)object3).getConnectTime() != 0L) break block73;
                            n9 = ((GsmCdmaConnection)object3).mCause == 3 ? 16 : 1;
                            object2 = new StringBuilder();
                            ((StringBuilder)object2).append("missed/rejected call, conn.cause=");
                            ((StringBuilder)object2).append(((GsmCdmaConnection)object3).mCause);
                            this.log(((StringBuilder)object2).toString());
                            object2 = new StringBuilder();
                            ((StringBuilder)object2).append("setting cause to ");
                            ((StringBuilder)object2).append(n9);
                            this.log(((StringBuilder)object2).toString());
                            this.mDroppedDuringPoll.remove(n5);
                            n2 = ((GsmCdmaConnection)object3).onDisconnect(n9) ? 1 : 0;
                            n8 = 1;
                            ((ArrayList)object5).add(object3);
                            n9 = n | n2;
                            break block74;
                        }
                        if (((GsmCdmaConnection)object3).mCause == 3) break block75;
                        n9 = n;
                        if (((GsmCdmaConnection)object3).mCause != 7) break block74;
                    }
                    this.mDroppedDuringPoll.remove(n5);
                    n2 = ((GsmCdmaConnection)object3).onDisconnect(((GsmCdmaConnection)object3).mCause);
                    n8 = 1;
                    ((ArrayList)object5).add(object3);
                    n9 = n | n2;
                }
                object2 = object;
                n4 = n7;
                if (!this.isPhoneTypeGsm()) {
                    object2 = object;
                    n4 = n7;
                    if (n8 != 0) {
                        object2 = object;
                        n4 = n7;
                        if (n7 != 0) {
                            object2 = object;
                            n4 = n7;
                            if (object3 == object) {
                                n4 = 0;
                                object2 = null;
                            }
                        }
                    }
                }
                object = object2;
                n = n9;
                n7 = n4;
            }
            if (((ArrayList)object5).size() > 0) {
                this.mMetrics.writeRilCallList(this.mPhone.getPhoneId(), (ArrayList<GsmCdmaConnection>)object5, this.getNetworkCountryIso());
            }
            object2 = this.mHandoverConnections.iterator();
            while (object2.hasNext()) {
                object5 = (Connection)object2.next();
                object3 = new StringBuilder();
                ((StringBuilder)object3).append("handlePollCalls - disconnect hoConn= ");
                ((StringBuilder)object3).append(object5);
                ((StringBuilder)object3).append(" hoConn.State= ");
                ((StringBuilder)object3).append((Object)((Connection)object5).getState());
                this.log(((StringBuilder)object3).toString());
                if (((Connection)object5).getState().isRinging()) {
                    ((Connection)object5).onDisconnect(1);
                } else {
                    ((Connection)object5).onDisconnect(-1);
                }
                object2.remove();
            }
            if (this.mDroppedDuringPoll.size() > 0) {
                this.mCi.getLastCallFailCause(this.obtainNoPollCompleteMessage(5));
            }
            if (false) {
                this.pollCallsAfterDelay();
            }
            if (object4 != null || n3 != 0 || n != 0) {
                this.internalClearDisconnected();
            }
            this.updatePhoneState();
            if (n7 != 0) {
                if (this.isPhoneTypeGsm()) {
                    object = arrayList.iterator();
                    while (object.hasNext()) {
                        object5 = (Connection)object.next();
                        object2 = new StringBuilder();
                        ((StringBuilder)object2).append("Notify unknown for ");
                        ((StringBuilder)object2).append(object5);
                        this.log(((StringBuilder)object2).toString());
                        this.mPhone.notifyUnknownConnection((Connection)object5);
                    }
                } else {
                    this.mPhone.notifyUnknownConnection((Connection)object);
                }
            }
            if (n3 != 0 || object4 != null || n != 0) {
                this.mPhone.notifyPreciseCallStateChanged();
                this.updateMetrics(this.mConnections);
            }
            if (n6 > 0 && this.mHandoverConnections.size() == 0 && (object = this.mPhone.getImsPhone()) != null) {
                ((Phone)object).callEndCleanupHandOverCallIfAny();
            }
            return;
        }
    }

    public void hangup(GsmCdmaCall gsmCdmaCall) throws CallStateException {
        block9 : {
            Object object;
            block13 : {
                block11 : {
                    block12 : {
                        block10 : {
                            if (gsmCdmaCall.getConnections().size() == 0) break block9;
                            object = this.mRingingCall;
                            if (gsmCdmaCall != object) break block10;
                            this.log("(ringing) hangup waiting or background");
                            this.logHangupEvent(gsmCdmaCall);
                            this.mCi.hangupWaitingOrBackground(this.obtainCompleteMessage());
                            break block11;
                        }
                        if (gsmCdmaCall != this.mForegroundCall) break block12;
                        if (gsmCdmaCall.isDialingOrAlerting()) {
                            this.log("(foregnd) hangup dialing or alerting...");
                            this.hangup((GsmCdmaConnection)gsmCdmaCall.getConnections().get(0));
                        } else if (this.isPhoneTypeGsm() && this.mRingingCall.isRinging()) {
                            this.log("hangup all conns in active/background call, without affecting ringing call");
                            this.hangupAllConnections(gsmCdmaCall);
                        } else {
                            this.logHangupEvent(gsmCdmaCall);
                            this.hangupForegroundResumeBackground();
                        }
                        break block11;
                    }
                    if (gsmCdmaCall != this.mBackgroundCall) break block13;
                    if (((Call)object).isRinging()) {
                        this.log("hangup all conns in background call");
                        this.hangupAllConnections(gsmCdmaCall);
                    } else {
                        this.hangupWaitingOrBackground();
                    }
                }
                gsmCdmaCall.onHangupLocal();
                this.mPhone.notifyPreciseCallStateChanged();
                return;
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("GsmCdmaCall ");
            ((StringBuilder)object).append(gsmCdmaCall);
            ((StringBuilder)object).append("does not belong to GsmCdmaCallTracker ");
            ((StringBuilder)object).append((Object)this);
            throw new RuntimeException(((StringBuilder)object).toString());
        }
        throw new CallStateException("no connections in call");
    }

    public void hangup(GsmCdmaConnection gsmCdmaConnection) throws CallStateException {
        if (gsmCdmaConnection.mOwner == this) {
            if (gsmCdmaConnection == this.mPendingMO) {
                this.log("hangup: set hangupPendingMO to true");
                this.mHangupPendingMO = true;
            } else {
                GsmCdmaCall gsmCdmaCall;
                Call call;
                if (!this.isPhoneTypeGsm() && (call = gsmCdmaConnection.getCall()) == (gsmCdmaCall = this.mRingingCall) && gsmCdmaCall.getState() == Call.State.WAITING) {
                    gsmCdmaConnection.onLocalDisconnect();
                    this.updatePhoneState();
                    this.mPhone.notifyPreciseCallStateChanged();
                    return;
                }
                try {
                    this.mMetrics.writeRilHangup(this.mPhone.getPhoneId(), gsmCdmaConnection, gsmCdmaConnection.getGsmCdmaIndex(), this.getNetworkCountryIso());
                    this.mCi.hangupConnection(gsmCdmaConnection.getGsmCdmaIndex(), this.obtainCompleteMessage());
                }
                catch (CallStateException callStateException) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("GsmCdmaCallTracker WARN: hangup() on absent connection ");
                    stringBuilder.append(gsmCdmaConnection);
                    Rlog.w((String)LOG_TAG, (String)stringBuilder.toString());
                }
            }
            gsmCdmaConnection.onHangupLocal();
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("GsmCdmaConnection ");
        stringBuilder.append(gsmCdmaConnection);
        stringBuilder.append("does not belong to GsmCdmaCallTracker ");
        stringBuilder.append((Object)this);
        throw new CallStateException(stringBuilder.toString());
    }

    public void hangupAllConnections(GsmCdmaCall gsmCdmaCall) {
        int n = gsmCdmaCall.mConnections.size();
        for (int i = 0; i < n; ++i) {
            Object object;
            try {
                object = (GsmCdmaConnection)gsmCdmaCall.mConnections.get(i);
                if (((GsmCdmaConnection)object).mDisconnected) continue;
                this.mMetrics.writeRilHangup(this.mPhone.getPhoneId(), (GsmCdmaConnection)object, ((GsmCdmaConnection)object).getGsmCdmaIndex(), this.getNetworkCountryIso());
                this.mCi.hangupConnection(((GsmCdmaConnection)object).getGsmCdmaIndex(), this.obtainCompleteMessage());
                continue;
            }
            catch (CallStateException callStateException) {
                object = new StringBuilder();
                ((StringBuilder)object).append("hangupConnectionByIndex caught ");
                ((StringBuilder)object).append(callStateException);
                Rlog.e((String)LOG_TAG, (String)((StringBuilder)object).toString());
                break;
            }
        }
    }

    public void hangupConnectionByIndex(GsmCdmaCall gsmCdmaCall, int n) throws CallStateException {
        int n2 = gsmCdmaCall.mConnections.size();
        for (int i = 0; i < n2; ++i) {
            GsmCdmaConnection gsmCdmaConnection = (GsmCdmaConnection)gsmCdmaCall.mConnections.get(i);
            if (gsmCdmaConnection.mDisconnected || gsmCdmaConnection.getGsmCdmaIndex() != n) continue;
            this.mMetrics.writeRilHangup(this.mPhone.getPhoneId(), gsmCdmaConnection, gsmCdmaConnection.getGsmCdmaIndex(), this.getNetworkCountryIso());
            this.mCi.hangupConnection(n, this.obtainCompleteMessage());
            return;
        }
        throw new CallStateException("no GsmCdma index found");
    }

    public void hangupForegroundResumeBackground() {
        this.log("hangupForegroundResumeBackground");
        this.mCi.hangupForegroundResumeBackground(this.obtainCompleteMessage());
    }

    public void hangupWaitingOrBackground() {
        this.log("hangupWaitingOrBackground");
        this.logHangupEvent(this.mBackgroundCall);
        this.mCi.hangupWaitingOrBackground(this.obtainCompleteMessage());
    }

    public boolean isInEmergencyCall() {
        return this.mIsInEmergencyCall;
    }

    public boolean isInOtaspCall() {
        GsmCdmaConnection gsmCdmaConnection = this.mPendingMO;
        boolean bl = gsmCdmaConnection != null && gsmCdmaConnection.isOtaspCall() || this.mForegroundCall.getConnections().stream().filter(_$$Lambda$GsmCdmaCallTracker$wkXwCyVPcnlqyXzSJdP2cQlpZxg.INSTANCE).count() > 0L;
        return bl;
    }

    @UnsupportedAppUsage
    @Override
    protected void log(String string) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[");
        stringBuilder.append(this.mPhone.getPhoneId());
        stringBuilder.append("] ");
        stringBuilder.append(string);
        Rlog.d((String)LOG_TAG, (String)stringBuilder.toString());
    }

    public void registerForCallWaiting(Handler handler, int n, Object object) {
        handler = new Registrant(handler, n, object);
        this.mCallWaitingRegistrants.add((Registrant)handler);
    }

    @Override
    public void registerForVoiceCallEnded(Handler handler, int n, Object object) {
        handler = new Registrant(handler, n, object);
        this.mVoiceCallEndedRegistrants.add((Registrant)handler);
    }

    @Override
    public void registerForVoiceCallStarted(Handler handler, int n, Object object) {
        handler = new Registrant(handler, n, object);
        this.mVoiceCallStartedRegistrants.add((Registrant)handler);
        if (this.mState != PhoneConstants.State.IDLE) {
            handler.notifyRegistrant(new AsyncResult(null, null, null));
        }
    }

    public void rejectCall() throws CallStateException {
        if (this.mRingingCall.getState().isRinging()) {
            this.mCi.rejectCall(this.obtainCompleteMessage());
            return;
        }
        throw new CallStateException("phone not ringing");
    }

    public void separate(GsmCdmaConnection gsmCdmaConnection) throws CallStateException {
        if (gsmCdmaConnection.mOwner == this) {
            try {
                this.mCi.separateConnection(gsmCdmaConnection.getGsmCdmaIndex(), this.obtainCompleteMessage(12));
            }
            catch (CallStateException callStateException) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("GsmCdmaCallTracker WARN: separate() on absent connection ");
                stringBuilder.append(gsmCdmaConnection);
                Rlog.w((String)LOG_TAG, (String)stringBuilder.toString());
            }
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("GsmCdmaConnection ");
        stringBuilder.append(gsmCdmaConnection);
        stringBuilder.append("does not belong to GsmCdmaCallTracker ");
        stringBuilder.append((Object)this);
        throw new CallStateException(stringBuilder.toString());
    }

    public void setIsInEmergencyCall() {
        this.mIsInEmergencyCall = true;
        this.mPhone.getDataEnabledSettings().setInternalDataEnabled(false);
        this.mPhone.notifyEmergencyCallRegistrants(true);
        this.mPhone.sendEmergencyCallStateChange(true);
    }

    @UnsupportedAppUsage
    public void setMute(boolean bl) {
        this.mDesiredMute = bl;
        this.mCi.setMute(this.mDesiredMute, null);
    }

    @UnsupportedAppUsage
    public void switchWaitingOrHoldingAndActive() throws CallStateException {
        if (this.mRingingCall.getState() != Call.State.INCOMING) {
            if (this.isPhoneTypeGsm()) {
                this.mCi.switchWaitingOrHoldingAndActive(this.obtainCompleteMessage(8));
            } else if (this.mForegroundCall.getConnections().size() > 1) {
                this.flashAndSetGenericTrue();
            } else {
                this.mCi.sendCDMAFeatureCode("", this.obtainMessage(8));
            }
            return;
        }
        throw new CallStateException("cannot be in the incoming state");
    }

    public void unregisterForCallWaiting(Handler handler) {
        this.mCallWaitingRegistrants.remove(handler);
    }

    @Override
    public void unregisterForVoiceCallEnded(Handler handler) {
        this.mVoiceCallEndedRegistrants.remove(handler);
    }

    @Override
    public void unregisterForVoiceCallStarted(Handler handler) {
        this.mVoiceCallStartedRegistrants.remove(handler);
    }

    public void updatePhoneType() {
        this.updatePhoneType(false);
    }

}

