/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.annotation.UnsupportedAppUsage
 *  android.content.Context
 *  android.os.AsyncResult
 *  android.os.Handler
 *  android.os.Looper
 *  android.os.Message
 *  android.os.PersistableBundle
 *  android.os.PowerManager
 *  android.os.PowerManager$WakeLock
 *  android.os.Registrant
 *  android.os.SystemClock
 *  android.telephony.CarrierConfigManager
 *  android.telephony.PhoneNumberUtils
 *  android.telephony.Rlog
 *  android.telephony.ServiceState
 *  android.text.TextUtils
 */
package com.android.internal.telephony;

import android.annotation.UnsupportedAppUsage;
import android.content.Context;
import android.os.AsyncResult;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.PersistableBundle;
import android.os.PowerManager;
import android.os.Registrant;
import android.os.SystemClock;
import android.telephony.CarrierConfigManager;
import android.telephony.PhoneNumberUtils;
import android.telephony.Rlog;
import android.telephony.ServiceState;
import android.text.TextUtils;
import com.android.internal.telephony.Call;
import com.android.internal.telephony.CallStateException;
import com.android.internal.telephony.CallTracker;
import com.android.internal.telephony.CommandsInterface;
import com.android.internal.telephony.Connection;
import com.android.internal.telephony.DriverCall;
import com.android.internal.telephony.GsmCdmaCall;
import com.android.internal.telephony.GsmCdmaCallTracker;
import com.android.internal.telephony.GsmCdmaPhone;
import com.android.internal.telephony.Phone;
import com.android.internal.telephony.RestrictedState;
import com.android.internal.telephony.ServiceStateTracker;
import com.android.internal.telephony.UUSInfo;
import com.android.internal.telephony.cdma.CdmaCallWaitingNotification;
import com.android.internal.telephony.metrics.TelephonyMetrics;
import com.android.internal.telephony.uicc.IccCardApplicationStatus;
import com.android.internal.telephony.uicc.UiccCardApplication;

public class GsmCdmaConnection
extends Connection {
    private static final boolean DBG = true;
    static final int EVENT_DTMF_DELAY_DONE = 5;
    static final int EVENT_DTMF_DONE = 1;
    static final int EVENT_NEXT_POST_DIAL = 3;
    static final int EVENT_PAUSE_DONE = 2;
    static final int EVENT_WAKE_LOCK_TIMEOUT = 4;
    private static final String LOG_TAG = "GsmCdmaConnection";
    public static final String OTASP_NUMBER = "*22899";
    static final int PAUSE_DELAY_MILLIS_CDMA = 2000;
    static final int PAUSE_DELAY_MILLIS_GSM = 3000;
    private static final boolean VDBG = false;
    static final int WAKE_LOCK_TIMEOUT_MILLIS = 60000;
    private int mAudioCodec = 0;
    long mDisconnectTime;
    boolean mDisconnected;
    private int mDtmfToneDelay = 0;
    Handler mHandler;
    @UnsupportedAppUsage
    int mIndex;
    private TelephonyMetrics mMetrics = TelephonyMetrics.getInstance();
    Connection mOrigConnection;
    @UnsupportedAppUsage
    GsmCdmaCallTracker mOwner;
    GsmCdmaCall mParent;
    private PowerManager.WakeLock mPartialWakeLock;
    int mPreciseCause = 0;
    UUSInfo mUusInfo;
    String mVendorCause;

    public GsmCdmaConnection(Context context, CdmaCallWaitingNotification cdmaCallWaitingNotification, GsmCdmaCallTracker gsmCdmaCallTracker, GsmCdmaCall gsmCdmaCall) {
        super(gsmCdmaCall.getPhone().getPhoneType());
        this.createWakeLock(context);
        this.acquireWakeLock();
        this.mOwner = gsmCdmaCallTracker;
        this.mHandler = new MyHandler(this.mOwner.getLooper());
        this.mAddress = cdmaCallWaitingNotification.number;
        this.mNumberPresentation = cdmaCallWaitingNotification.numberPresentation;
        this.mCnapName = cdmaCallWaitingNotification.name;
        this.mCnapNamePresentation = cdmaCallWaitingNotification.namePresentation;
        this.mIndex = -1;
        this.mIsIncoming = true;
        this.mCreateTime = System.currentTimeMillis();
        this.mConnectTime = 0L;
        this.mParent = gsmCdmaCall;
        gsmCdmaCall.attachFake(this, Call.State.WAITING);
        this.setCallRadioTech(this.mOwner.getPhone().getCsCallRadioTech());
    }

    public GsmCdmaConnection(GsmCdmaPhone gsmCdmaPhone, DriverCall driverCall, GsmCdmaCallTracker gsmCdmaCallTracker, int n) {
        super(gsmCdmaPhone.getPhoneType());
        this.createWakeLock(gsmCdmaPhone.getContext());
        this.acquireWakeLock();
        this.mOwner = gsmCdmaCallTracker;
        this.mHandler = new MyHandler(this.mOwner.getLooper());
        this.mAddress = driverCall.number;
        this.setEmergencyCallInfo(this.mOwner);
        this.mIsIncoming = driverCall.isMT;
        this.mCreateTime = System.currentTimeMillis();
        this.mCnapName = driverCall.name;
        this.mCnapNamePresentation = driverCall.namePresentation;
        this.mNumberPresentation = driverCall.numberPresentation;
        this.mUusInfo = driverCall.uusInfo;
        this.mIndex = n;
        this.mParent = this.parentFromDCState(driverCall.state);
        this.mParent.attach(this, driverCall);
        this.fetchDtmfToneDelay(gsmCdmaPhone);
        this.setAudioQuality(this.getAudioQualityFromDC(driverCall.audioQuality));
        this.setCallRadioTech(this.mOwner.getPhone().getCsCallRadioTech());
    }

    public GsmCdmaConnection(GsmCdmaPhone gsmCdmaPhone, String charSequence, GsmCdmaCallTracker object, GsmCdmaCall gsmCdmaCall, boolean bl) {
        super(gsmCdmaPhone.getPhoneType());
        this.createWakeLock(gsmCdmaPhone.getContext());
        this.acquireWakeLock();
        this.mOwner = object;
        this.mHandler = new MyHandler(this.mOwner.getLooper());
        this.mDialString = charSequence;
        object = charSequence;
        if (!this.isPhoneTypeGsm()) {
            object = new StringBuilder();
            ((StringBuilder)object).append("[GsmCdmaConn] GsmCdmaConnection: dialString=");
            ((StringBuilder)object).append(this.maskDialString((String)charSequence));
            Rlog.d((String)LOG_TAG, (String)((StringBuilder)object).toString());
            object = GsmCdmaConnection.formatDialString((String)charSequence);
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("[GsmCdmaConn] GsmCdmaConnection:formated dialString=");
            ((StringBuilder)charSequence).append(this.maskDialString((String)object));
            Rlog.d((String)LOG_TAG, (String)((StringBuilder)charSequence).toString());
        }
        this.mAddress = PhoneNumberUtils.extractNetworkPortionAlt((String)object);
        if (bl) {
            this.setEmergencyCallInfo(this.mOwner);
        }
        this.mPostDialString = PhoneNumberUtils.extractPostDialPortion((String)object);
        this.mIndex = -1;
        this.mIsIncoming = false;
        this.mCnapName = null;
        this.mCnapNamePresentation = 1;
        this.mNumberPresentation = 1;
        this.mCreateTime = System.currentTimeMillis();
        if (gsmCdmaCall != null) {
            this.mParent = gsmCdmaCall;
            if (this.isPhoneTypeGsm()) {
                gsmCdmaCall.attachFake(this, Call.State.DIALING);
            } else if (gsmCdmaCall.mState == Call.State.ACTIVE) {
                gsmCdmaCall.attachFake(this, Call.State.ACTIVE);
            } else {
                gsmCdmaCall.attachFake(this, Call.State.DIALING);
            }
        }
        this.fetchDtmfToneDelay(gsmCdmaPhone);
        this.setCallRadioTech(this.mOwner.getPhone().getCsCallRadioTech());
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @UnsupportedAppUsage
    private void acquireWakeLock() {
        PowerManager.WakeLock wakeLock = this.mPartialWakeLock;
        if (wakeLock == null) return;
        synchronized (wakeLock) {
            this.log("acquireWakeLock");
            this.mPartialWakeLock.acquire();
            return;
        }
    }

    @UnsupportedAppUsage
    private void createWakeLock(Context context) {
        this.mPartialWakeLock = ((PowerManager)context.getSystemService("power")).newWakeLock(1, LOG_TAG);
    }

    private void doDisconnect() {
        this.mIndex = -1;
        this.mDisconnectTime = System.currentTimeMillis();
        this.mDuration = SystemClock.elapsedRealtime() - this.mConnectTimeReal;
        this.mDisconnected = true;
        this.clearPostDialListeners();
    }

    static boolean equalsBaseDialString(String string, String string2) {
        boolean bl = true;
        if (!(string == null ? string2 == null : string2 != null && string.startsWith(string2))) {
            bl = false;
        }
        return bl;
    }

    static boolean equalsHandlesNulls(Object object, Object object2) {
        boolean bl = object == null ? object2 == null : object.equals(object2);
        return bl;
    }

    @UnsupportedAppUsage
    private void fetchDtmfToneDelay(GsmCdmaPhone gsmCdmaPhone) {
        PersistableBundle persistableBundle = ((CarrierConfigManager)gsmCdmaPhone.getContext().getSystemService("carrier_config")).getConfigForSubId(gsmCdmaPhone.getSubId());
        if (persistableBundle != null) {
            this.mDtmfToneDelay = persistableBundle.getInt(gsmCdmaPhone.getDtmfToneDelayKey());
        }
    }

    @UnsupportedAppUsage
    private static int findNextPCharOrNonPOrNonWCharIndex(String string, int n) {
        boolean bl;
        boolean bl2 = GsmCdmaConnection.isWait(string.charAt(n));
        int n2 = n + 1;
        int n3 = string.length();
        do {
            bl = bl2;
            if (n2 >= n3) break;
            char c = string.charAt(n2);
            if (GsmCdmaConnection.isWait(c)) {
                bl2 = true;
            }
            if (!GsmCdmaConnection.isWait(c) && !GsmCdmaConnection.isPause(c)) {
                bl = bl2;
                break;
            }
            ++n2;
        } while (true);
        if (n2 < n3 && n2 > n + 1 && !bl && GsmCdmaConnection.isPause(string.charAt(n))) {
            return n + 1;
        }
        return n2;
    }

    @UnsupportedAppUsage
    private static char findPOrWCharToAppend(String string, int n, int n2) {
        int n3 = GsmCdmaConnection.isPause(string.charAt(n)) ? 44 : 59;
        int n4 = n3;
        if (n2 > n + 1) {
            n4 = n = 59;
        }
        return (char)n4;
    }

    @UnsupportedAppUsage
    public static String formatDialString(String string) {
        if (string == null) {
            return null;
        }
        int n = string.length();
        StringBuilder stringBuilder = new StringBuilder();
        int n2 = 0;
        while (n2 < n) {
            int n3;
            char c = string.charAt(n2);
            if (!GsmCdmaConnection.isPause(c) && !GsmCdmaConnection.isWait(c)) {
                stringBuilder.append(c);
                n3 = n2;
            } else {
                n3 = n2;
                if (n2 < n - 1) {
                    int n4 = GsmCdmaConnection.findNextPCharOrNonPOrNonWCharIndex(string, n2);
                    if (n4 < n) {
                        stringBuilder.append(GsmCdmaConnection.findPOrWCharToAppend(string, n2, n4));
                        n3 = n2;
                        if (n4 > n2 + 1) {
                            n3 = n4 - 1;
                        }
                    } else {
                        n3 = n2;
                        if (n4 == n) {
                            n3 = n - 1;
                        }
                    }
                }
            }
            n2 = n3 + 1;
        }
        return PhoneNumberUtils.cdmaCheckAndProcessPlusCode((String)stringBuilder.toString());
    }

    private int getAudioQualityFromDC(int n) {
        if (n != 2 && n != 9) {
            return 1;
        }
        return 2;
    }

    private boolean isConnectingInOrOut() {
        GsmCdmaCall gsmCdmaCall = this.mParent;
        boolean bl = gsmCdmaCall == null || gsmCdmaCall == this.mOwner.mRingingCall || this.mParent.mState == Call.State.DIALING || this.mParent.mState == Call.State.ALERTING;
        return bl;
    }

    @UnsupportedAppUsage
    private static boolean isPause(char c) {
        boolean bl = c == ',';
        return bl;
    }

    @UnsupportedAppUsage
    private boolean isPhoneTypeGsm() {
        int n = this.mOwner.getPhone().getPhoneType();
        boolean bl = true;
        if (n != 1) {
            bl = false;
        }
        return bl;
    }

    @UnsupportedAppUsage
    private static boolean isWait(char c) {
        boolean bl = c == ';';
        return bl;
    }

    private static boolean isWild(char c) {
        boolean bl = c == 'N';
        return bl;
    }

    @UnsupportedAppUsage
    private void log(String string) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[GsmCdmaConn] ");
        stringBuilder.append(string);
        Rlog.d((String)LOG_TAG, (String)stringBuilder.toString());
    }

    @UnsupportedAppUsage
    private String maskDialString(String string) {
        return "<MASKED>";
    }

    private GsmCdmaCall parentFromDCState(DriverCall.State state) {
        switch (state) {
            default: {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("illegal call state: ");
                stringBuilder.append((Object)state);
                throw new RuntimeException(stringBuilder.toString());
            }
            case INCOMING: 
            case WAITING: {
                return this.mOwner.mRingingCall;
            }
            case HOLDING: {
                return this.mOwner.mBackgroundCall;
            }
            case ACTIVE: 
            case DIALING: 
            case ALERTING: 
        }
        return this.mOwner.mForegroundCall;
    }

    private void processNextPostDialChar() {
        Object object;
        int n;
        Message message;
        if (this.mPostDialState == Connection.PostDialState.CANCELLED) {
            this.releaseWakeLock();
            return;
        }
        if (this.mPostDialString != null && this.mPostDialString.length() > this.mNextPostDialChar) {
            this.setPostDialState(Connection.PostDialState.STARTED);
            object = this.mPostDialString;
            int n2 = this.mNextPostDialChar;
            this.mNextPostDialChar = n2 + 1;
            char c = ((String)object).charAt(n2);
            n = c;
            if (!this.processPostDialChar(c)) {
                this.mHandler.obtainMessage(3).sendToTarget();
                object = new StringBuilder();
                ((StringBuilder)object).append("processNextPostDialChar: c=");
                ((StringBuilder)object).append(c);
                ((StringBuilder)object).append(" isn't valid!");
                Rlog.e((String)LOG_TAG, (String)((StringBuilder)object).toString());
                return;
            }
        } else {
            int n3;
            this.setPostDialState(Connection.PostDialState.COMPLETE);
            this.releaseWakeLock();
            n = n3 = 0;
        }
        this.notifyPostDialListenersNextChar((char)n);
        object = this.mOwner.getPhone().getPostDialHandler();
        if (object != null && (message = object.messageForRegistrant()) != null) {
            object = this.mPostDialState;
            AsyncResult asyncResult = AsyncResult.forMessage((Message)message);
            asyncResult.result = this;
            asyncResult.userObj = object;
            message.arg1 = n;
            message.sendToTarget();
        }
    }

    private boolean processPostDialChar(char c) {
        block8 : {
            block5 : {
                block7 : {
                    block6 : {
                        block4 : {
                            if (!PhoneNumberUtils.is12Key((char)c)) break block4;
                            this.mOwner.mCi.sendDtmf(c, this.mHandler.obtainMessage(1));
                            break block5;
                        }
                        if (!GsmCdmaConnection.isPause(c)) break block6;
                        if (!this.isPhoneTypeGsm()) {
                            this.setPostDialState(Connection.PostDialState.PAUSE);
                        }
                        Handler handler = this.mHandler;
                        Message message = handler.obtainMessage(2);
                        long l = this.isPhoneTypeGsm() ? 3000L : 2000L;
                        handler.sendMessageDelayed(message, l);
                        break block5;
                    }
                    if (!GsmCdmaConnection.isWait(c)) break block7;
                    this.setPostDialState(Connection.PostDialState.WAIT);
                    break block5;
                }
                if (!GsmCdmaConnection.isWild(c)) break block8;
                this.setPostDialState(Connection.PostDialState.WILD);
            }
            return true;
        }
        return false;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void releaseAllWakeLocks() {
        PowerManager.WakeLock wakeLock = this.mPartialWakeLock;
        if (wakeLock == null) return;
        synchronized (wakeLock) {
            while (this.mPartialWakeLock.isHeld()) {
                this.mPartialWakeLock.release();
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void releaseWakeLock() {
        PowerManager.WakeLock wakeLock = this.mPartialWakeLock;
        if (wakeLock == null) return;
        synchronized (wakeLock) {
            if (!this.mPartialWakeLock.isHeld()) return;
            this.log("releaseWakeLock");
            this.mPartialWakeLock.release();
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void setPostDialState(Connection.PostDialState postDialState) {
        if (postDialState != Connection.PostDialState.STARTED && postDialState != Connection.PostDialState.PAUSE) {
            this.mHandler.removeMessages(4);
            this.releaseWakeLock();
        } else {
            PowerManager.WakeLock wakeLock = this.mPartialWakeLock;
            synchronized (wakeLock) {
                if (this.mPartialWakeLock.isHeld()) {
                    this.mHandler.removeMessages(4);
                } else {
                    this.acquireWakeLock();
                }
                Message message = this.mHandler.obtainMessage(4);
                this.mHandler.sendMessageDelayed(message, 60000L);
            }
        }
        this.mPostDialState = postDialState;
        this.notifyPostDialListeners();
    }

    @Override
    public void cancelPostDial() {
        this.setPostDialState(Connection.PostDialState.CANCELLED);
    }

    boolean compareTo(DriverCall driverCall) {
        boolean bl = this.mIsIncoming;
        boolean bl2 = true;
        if (!bl && !driverCall.isMT) {
            return true;
        }
        if (this.isPhoneTypeGsm() && this.mOrigConnection != null) {
            return true;
        }
        String string = PhoneNumberUtils.stringFromStringAndTOA((String)driverCall.number, (int)driverCall.TOA);
        if (this.mIsIncoming != driverCall.isMT || !GsmCdmaConnection.equalsHandlesNulls(this.mAddress, string)) {
            bl2 = false;
        }
        return bl2;
    }

    @Override
    public void deflect(String string) throws CallStateException {
        throw new CallStateException("deflect is not supported for CS");
    }

    @UnsupportedAppUsage
    int disconnectCauseFromCode(int n) {
        block34 : {
            block35 : {
                block36 : {
                    block37 : {
                        block38 : {
                            block39 : {
                                block40 : {
                                    if (n == 1) break block34;
                                    if (n == 8) break block35;
                                    if (n == 17) break block36;
                                    if (n == 19) break block37;
                                    if (n == 31) break block38;
                                    if (n == 34 || n == 44 || n == 49 || n == 58) break block39;
                                    if (n == 68) break block40;
                                    if (n == 41 || n == 42) break block39;
                                    if (n != 240) {
                                        if (n != 241) {
                                            if (n != 325) {
                                                if (n != 326) {
                                                    switch (n) {
                                                        default: {
                                                            switch (n) {
                                                                default: {
                                                                    GsmCdmaPhone gsmCdmaPhone = this.mOwner.getPhone();
                                                                    int n2 = gsmCdmaPhone.getServiceState().getState();
                                                                    Object object = gsmCdmaPhone.getUiccCardApplication();
                                                                    object = object != null ? object.getState() : IccCardApplicationStatus.AppState.APPSTATE_UNKNOWN;
                                                                    if (n2 == 3) {
                                                                        return 17;
                                                                    }
                                                                    if (!this.isEmergencyCall()) {
                                                                        if (n2 != 1 && n2 != 2) {
                                                                            if (object != IccCardApplicationStatus.AppState.APPSTATE_READY) {
                                                                                if (this.isPhoneTypeGsm()) {
                                                                                    return 19;
                                                                                }
                                                                                if (gsmCdmaPhone.mCdmaSubscriptionSource == 0) {
                                                                                    return 19;
                                                                                }
                                                                            }
                                                                        } else {
                                                                            return 18;
                                                                        }
                                                                    }
                                                                    if (this.isPhoneTypeGsm() && (n == 65535 || n == 260)) {
                                                                        if (gsmCdmaPhone.mSST.mRestrictedState.isCsRestricted()) {
                                                                            return 22;
                                                                        }
                                                                        if (gsmCdmaPhone.mSST.mRestrictedState.isCsEmergencyRestricted()) {
                                                                            return 24;
                                                                        }
                                                                        if (gsmCdmaPhone.mSST.mRestrictedState.isCsNormalRestricted()) {
                                                                            return 23;
                                                                        }
                                                                    }
                                                                    if (n == 16) {
                                                                        return 2;
                                                                    }
                                                                    return 36;
                                                                }
                                                                case 1009: {
                                                                    return 35;
                                                                }
                                                                case 1008: {
                                                                    return 34;
                                                                }
                                                                case 1007: {
                                                                    return 33;
                                                                }
                                                                case 1006: {
                                                                    return 32;
                                                                }
                                                                case 1005: {
                                                                    return 31;
                                                                }
                                                                case 1004: {
                                                                    return 30;
                                                                }
                                                                case 1003: {
                                                                    return 29;
                                                                }
                                                                case 1002: {
                                                                    return 28;
                                                                }
                                                                case 1001: {
                                                                    return 27;
                                                                }
                                                                case 1000: 
                                                            }
                                                            return 26;
                                                        }
                                                        case 246: {
                                                            return 48;
                                                        }
                                                        case 245: {
                                                            return 47;
                                                        }
                                                        case 244: {
                                                            return 46;
                                                        }
                                                        case 243: 
                                                    }
                                                    return 58;
                                                }
                                                return 64;
                                            }
                                            return 63;
                                        }
                                        return 21;
                                    }
                                    break block35;
                                }
                                return 15;
                            }
                            return 5;
                        }
                        return 65;
                    }
                    return 13;
                }
                return 4;
            }
            return 20;
        }
        return 25;
    }

    public void dispose() {
        this.clearPostDialListeners();
        GsmCdmaCall gsmCdmaCall = this.mParent;
        if (gsmCdmaCall != null) {
            gsmCdmaCall.detach(this);
        }
        this.releaseAllWakeLocks();
    }

    void fakeHoldBeforeDial() {
        GsmCdmaCall gsmCdmaCall = this.mParent;
        if (gsmCdmaCall != null) {
            gsmCdmaCall.detach(this);
        }
        this.mParent = this.mOwner.mBackgroundCall;
        this.mParent.attachFake(this, Call.State.HOLDING);
        this.onStartedHolding();
    }

    protected void finalize() {
        PowerManager.WakeLock wakeLock = this.mPartialWakeLock;
        if (wakeLock != null && wakeLock.isHeld()) {
            Rlog.e((String)LOG_TAG, (String)"UNEXPECTED; mPartialWakeLock is held when finalizing.");
        }
        this.clearPostDialListeners();
        this.releaseWakeLock();
    }

    @Override
    public GsmCdmaCall getCall() {
        return this.mParent;
    }

    @Override
    public long getDisconnectTime() {
        return this.mDisconnectTime;
    }

    int getGsmCdmaIndex() throws CallStateException {
        int n = this.mIndex;
        if (n >= 0) {
            return n + 1;
        }
        throw new CallStateException("GsmCdma index not yet assigned");
    }

    @Override
    public long getHoldDurationMillis() {
        if (this.getState() != Call.State.HOLDING) {
            return 0L;
        }
        return SystemClock.elapsedRealtime() - this.mHoldingStartTime;
    }

    @Override
    public int getNumberPresentation() {
        return this.mNumberPresentation;
    }

    @Override
    public Connection getOrigConnection() {
        return this.mOrigConnection;
    }

    @Override
    public String getOrigDialString() {
        return this.mDialString;
    }

    @Override
    public int getPreciseDisconnectCause() {
        return this.mPreciseCause;
    }

    @Override
    public String getRemainingPostDialString() {
        String string;
        String string2 = string = super.getRemainingPostDialString();
        if (!this.isPhoneTypeGsm()) {
            string2 = string;
            if (!TextUtils.isEmpty((CharSequence)string)) {
                int n = string.indexOf(59);
                int n2 = string.indexOf(44);
                if (n > 0 && (n < n2 || n2 <= 0)) {
                    string2 = string.substring(0, n);
                } else {
                    string2 = string;
                    if (n2 > 0) {
                        string2 = string.substring(0, n2);
                    }
                }
            }
        }
        return string2;
    }

    @UnsupportedAppUsage
    @Override
    public Call.State getState() {
        if (this.mDisconnected) {
            return Call.State.DISCONNECTED;
        }
        return super.getState();
    }

    @Override
    public UUSInfo getUUSInfo() {
        return this.mUusInfo;
    }

    @Override
    public String getVendorDisconnectCause() {
        return this.mVendorCause;
    }

    @Override
    public void hangup() throws CallStateException {
        if (!this.mDisconnected) {
            this.mOwner.hangup(this);
            return;
        }
        throw new CallStateException("disconnected");
    }

    @Override
    public boolean isMultiparty() {
        Connection connection = this.mOrigConnection;
        if (connection != null) {
            return connection.isMultiparty();
        }
        return false;
    }

    public boolean isOtaspCall() {
        boolean bl = this.mAddress != null && OTASP_NUMBER.equals(this.mAddress);
        return bl;
    }

    @Override
    public void migrateFrom(Connection connection) {
        if (connection == null) {
            return;
        }
        super.migrateFrom(connection);
        this.mUusInfo = connection.getUUSInfo();
        this.setUserData(connection.getUserData());
    }

    void onConnectedConnectionMigrated() {
        this.releaseWakeLock();
    }

    @UnsupportedAppUsage
    void onConnectedInOrOut() {
        this.mConnectTime = System.currentTimeMillis();
        this.mConnectTimeReal = SystemClock.elapsedRealtime();
        this.mDuration = 0L;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("onConnectedInOrOut: connectTime=");
        stringBuilder.append(this.mConnectTime);
        this.log(stringBuilder.toString());
        if (!this.mIsIncoming) {
            this.processNextPostDialChar();
        } else {
            this.releaseWakeLock();
        }
    }

    @Override
    public boolean onDisconnect(int n) {
        boolean bl = false;
        boolean bl2 = false;
        this.mCause = n;
        if (!this.mDisconnected) {
            this.doDisconnect();
            Object object = new StringBuilder();
            ((StringBuilder)object).append("onDisconnect: cause=");
            ((StringBuilder)object).append(n);
            Rlog.d((String)LOG_TAG, (String)((StringBuilder)object).toString());
            this.mOwner.getPhone().notifyDisconnect(this);
            this.notifyDisconnect(n);
            object = this.mParent;
            bl = bl2;
            if (object != null) {
                bl = ((GsmCdmaCall)object).connectionDisconnected(this);
            }
            this.mOrigConnection = null;
        }
        this.clearPostDialListeners();
        this.releaseWakeLock();
        return bl;
    }

    void onHangupLocal() {
        this.mCause = 3;
        this.mPreciseCause = 0;
        this.mVendorCause = null;
    }

    void onLocalDisconnect() {
        if (!this.mDisconnected) {
            this.doDisconnect();
            GsmCdmaCall gsmCdmaCall = this.mParent;
            if (gsmCdmaCall != null) {
                gsmCdmaCall.detach(this);
            }
        }
        this.releaseWakeLock();
    }

    void onRemoteDisconnect(int n, String string) {
        this.mPreciseCause = n;
        this.mVendorCause = string;
        this.onDisconnect(this.disconnectCauseFromCode(n));
    }

    void onStartedHolding() {
        this.mHoldingStartTime = SystemClock.elapsedRealtime();
    }

    @Override
    public void proceedAfterWaitChar() {
        if (this.mPostDialState != Connection.PostDialState.WAIT) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("GsmCdmaConnection.proceedAfterWaitChar(): Expected getPostDialState() to be WAIT but was ");
            stringBuilder.append((Object)this.mPostDialState);
            Rlog.w((String)LOG_TAG, (String)stringBuilder.toString());
            return;
        }
        this.setPostDialState(Connection.PostDialState.STARTED);
        this.processNextPostDialChar();
    }

    @Override
    public void proceedAfterWildChar(String charSequence) {
        if (this.mPostDialState != Connection.PostDialState.WILD) {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("GsmCdmaConnection.proceedAfterWaitChar(): Expected getPostDialState() to be WILD but was ");
            ((StringBuilder)charSequence).append((Object)this.mPostDialState);
            Rlog.w((String)LOG_TAG, (String)((StringBuilder)charSequence).toString());
            return;
        }
        this.setPostDialState(Connection.PostDialState.STARTED);
        charSequence = new StringBuilder((String)charSequence);
        ((StringBuilder)charSequence).append(this.mPostDialString.substring(this.mNextPostDialChar));
        this.mPostDialString = ((StringBuilder)charSequence).toString();
        this.mNextPostDialChar = 0;
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append("proceedAfterWildChar: new postDialString is ");
        ((StringBuilder)charSequence).append(this.mPostDialString);
        this.log(((StringBuilder)charSequence).toString());
        this.processNextPostDialChar();
    }

    @Override
    public void separate() throws CallStateException {
        if (!this.mDisconnected) {
            this.mOwner.separate(this);
            return;
        }
        throw new CallStateException("disconnected");
    }

    public boolean update(DriverCall object) {
        boolean bl;
        Object object2;
        int n;
        boolean bl2;
        boolean bl3;
        boolean bl4;
        int n2;
        Object object3;
        block17 : {
            block18 : {
                block16 : {
                    n2 = 0;
                    bl4 = this.isConnectingInOrOut();
                    object2 = this.getState();
                    object3 = Call.State.HOLDING;
                    bl3 = true;
                    bl = object2 == object3;
                    object2 = this.parentFromDCState(((DriverCall)object).state);
                    object3 = new StringBuilder();
                    ((StringBuilder)object3).append("parent= ");
                    ((StringBuilder)object3).append(this.mParent);
                    ((StringBuilder)object3).append(", newParent= ");
                    ((StringBuilder)object3).append(object2);
                    this.log(((StringBuilder)object3).toString());
                    if (!this.isPhoneTypeGsm() || this.mOrigConnection == null) break block16;
                    this.log("update: mOrigConnection is not null");
                    n = n2;
                    break block17;
                }
                n = n2;
                if (!this.isIncoming()) break block17;
                n = n2;
                if (GsmCdmaConnection.equalsBaseDialString(this.mAddress, ((DriverCall)object).number)) break block17;
                if (!this.mNumberConverted) break block18;
                n = n2;
                if (GsmCdmaConnection.equalsBaseDialString(this.mConvertedNumber, ((DriverCall)object).number)) break block17;
            }
            this.log("update: phone # changed!");
            this.mAddress = ((DriverCall)object).number;
            n = 1;
        }
        n2 = this.getAudioQualityFromDC(((DriverCall)object).audioQuality);
        if (this.getAudioQuality() != n2) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("update: audioQuality # changed!:  ");
            object3 = n2 == 2 ? "high" : "standard";
            stringBuilder.append((String)object3);
            this.log(stringBuilder.toString());
            this.setAudioQuality(n2);
            n = 1;
        }
        if (((DriverCall)object).audioQuality != this.mAudioCodec) {
            this.mAudioCodec = ((DriverCall)object).audioQuality;
            this.mMetrics.writeAudioCodecGsmCdma(this.mOwner.getPhone().getPhoneId(), ((DriverCall)object).audioQuality);
        }
        if (TextUtils.isEmpty((CharSequence)((DriverCall)object).name)) {
            if (!TextUtils.isEmpty((CharSequence)this.mCnapName)) {
                n = 1;
                this.mCnapName = "";
            }
        } else if (!((DriverCall)object).name.equals(this.mCnapName)) {
            n = 1;
            this.mCnapName = ((DriverCall)object).name;
        }
        object3 = new StringBuilder();
        ((StringBuilder)object3).append("--dssds----");
        ((StringBuilder)object3).append(this.mCnapName);
        this.log(((StringBuilder)object3).toString());
        this.mCnapNamePresentation = ((DriverCall)object).namePresentation;
        this.mNumberPresentation = ((DriverCall)object).numberPresentation;
        object3 = this.mParent;
        if (object2 != object3) {
            if (object3 != null) {
                ((GsmCdmaCall)object3).detach(this);
            }
            ((GsmCdmaCall)object2).attach(this, (DriverCall)object);
            this.mParent = object2;
            bl2 = true;
        } else {
            bl2 = ((GsmCdmaCall)object3).update(this, (DriverCall)object);
            bl2 = n != 0 || bl2;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("update: parent=");
        ((StringBuilder)object).append(this.mParent);
        ((StringBuilder)object).append(", hasNewParent=");
        if (object2 == this.mParent) {
            bl3 = false;
        }
        ((StringBuilder)object).append(bl3);
        ((StringBuilder)object).append(", wasConnectingInOrOut=");
        ((StringBuilder)object).append(bl4);
        ((StringBuilder)object).append(", wasHolding=");
        ((StringBuilder)object).append(bl);
        ((StringBuilder)object).append(", isConnectingInOrOut=");
        ((StringBuilder)object).append(this.isConnectingInOrOut());
        ((StringBuilder)object).append(", changed=");
        ((StringBuilder)object).append(bl2);
        this.log(((StringBuilder)object).toString());
        if (bl4 && !this.isConnectingInOrOut()) {
            this.onConnectedInOrOut();
        }
        if (bl2 && !bl && this.getState() == Call.State.HOLDING) {
            this.onStartedHolding();
        }
        return bl2;
    }

    @UnsupportedAppUsage
    public void updateParent(GsmCdmaCall gsmCdmaCall, GsmCdmaCall gsmCdmaCall2) {
        if (gsmCdmaCall2 != gsmCdmaCall) {
            if (gsmCdmaCall != null) {
                gsmCdmaCall.detach(this);
            }
            gsmCdmaCall2.attachFake(this, Call.State.ACTIVE);
            this.mParent = gsmCdmaCall2;
        }
    }

    class MyHandler
    extends Handler {
        MyHandler(Looper looper) {
            super(looper);
        }

        public void handleMessage(Message message) {
            block3 : {
                block0 : {
                    block1 : {
                        block2 : {
                            int n = message.what;
                            if (n == 1) break block0;
                            if (n == 2 || n == 3) break block1;
                            if (n == 4) break block2;
                            if (n == 5) break block1;
                            break block3;
                        }
                        GsmCdmaConnection.this.releaseWakeLock();
                        break block3;
                    }
                    GsmCdmaConnection.this.processNextPostDialChar();
                    break block3;
                }
                GsmCdmaConnection.this.mHandler.sendMessageDelayed(GsmCdmaConnection.this.mHandler.obtainMessage(5), (long)GsmCdmaConnection.this.mDtmfToneDelay);
            }
        }
    }

}

