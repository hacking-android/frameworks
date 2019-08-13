/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.hardware.radio.V1_0.DataRegStateResult
 *  android.hardware.radio.V1_0.SetupDataCallResult
 *  android.hardware.radio.V1_0.VoiceRegStateResult
 *  android.net.KeepalivePacketData
 *  android.net.LinkProperties
 *  android.os.AsyncResult
 *  android.os.Handler
 *  android.os.HandlerThread
 *  android.os.Looper
 *  android.os.Message
 *  android.os.Parcel
 *  android.os.Parcelable$Creator
 *  android.os.Registrant
 *  android.os.RegistrantList
 *  android.os.SystemClock
 *  android.os.WorkSource
 *  android.telephony.CarrierRestrictionRules
 *  android.telephony.CellInfo
 *  android.telephony.CellInfoGsm
 *  android.telephony.CellSignalStrengthCdma
 *  android.telephony.CellSignalStrengthGsm
 *  android.telephony.CellSignalStrengthLte
 *  android.telephony.CellSignalStrengthNr
 *  android.telephony.CellSignalStrengthTdscdma
 *  android.telephony.CellSignalStrengthWcdma
 *  android.telephony.ImsiEncryptionInfo
 *  android.telephony.NetworkScanRequest
 *  android.telephony.Rlog
 *  android.telephony.SignalStrength
 *  android.telephony.data.DataProfile
 *  android.telephony.emergency.EmergencyNumber
 *  com.android.internal.annotations.VisibleForTesting
 *  com.android.internal.telephony.gsm.SmsBroadcastConfigInfo
 */
package com.android.internal.telephony.test;

import android.content.Context;
import android.hardware.radio.V1_0.DataRegStateResult;
import android.hardware.radio.V1_0.SetupDataCallResult;
import android.hardware.radio.V1_0.VoiceRegStateResult;
import android.net.KeepalivePacketData;
import android.net.LinkProperties;
import android.os.AsyncResult;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Registrant;
import android.os.RegistrantList;
import android.os.SystemClock;
import android.os.WorkSource;
import android.telephony.CarrierRestrictionRules;
import android.telephony.CellInfo;
import android.telephony.CellInfoGsm;
import android.telephony.CellSignalStrengthCdma;
import android.telephony.CellSignalStrengthGsm;
import android.telephony.CellSignalStrengthLte;
import android.telephony.CellSignalStrengthNr;
import android.telephony.CellSignalStrengthTdscdma;
import android.telephony.CellSignalStrengthWcdma;
import android.telephony.ImsiEncryptionInfo;
import android.telephony.NetworkScanRequest;
import android.telephony.Rlog;
import android.telephony.SignalStrength;
import android.telephony.data.DataProfile;
import android.telephony.emergency.EmergencyNumber;
import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.telephony.BaseCommands;
import com.android.internal.telephony.CommandException;
import com.android.internal.telephony.CommandsInterface;
import com.android.internal.telephony.DriverCall;
import com.android.internal.telephony.LastCallFailCause;
import com.android.internal.telephony.RIL;
import com.android.internal.telephony.RadioCapability;
import com.android.internal.telephony.SmsResponse;
import com.android.internal.telephony.UUSInfo;
import com.android.internal.telephony.cdma.CdmaSmsBroadcastConfigInfo;
import com.android.internal.telephony.gsm.SmsBroadcastConfigInfo;
import com.android.internal.telephony.gsm.SuppServiceNotification;
import com.android.internal.telephony.test.SimulatedCommandsVerifier;
import com.android.internal.telephony.test.SimulatedGsmCallState;
import com.android.internal.telephony.test.SimulatedRadioControl;
import com.android.internal.telephony.uicc.IccCardStatus;
import com.android.internal.telephony.uicc.IccIoResult;
import com.android.internal.telephony.uicc.IccSlotStatus;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class SimulatedCommands
extends BaseCommands
implements CommandsInterface,
SimulatedRadioControl {
    public static final int DEFAULT_PIN1_ATTEMPT = 5;
    public static final int DEFAULT_PIN2_ATTEMPT = 5;
    public static final String DEFAULT_SIM_PIN2_CODE = "5678";
    public static final String DEFAULT_SIM_PIN_CODE = "1234";
    public static final String FAKE_ESN = "1234";
    public static final String FAKE_IMEI = "012345678901234";
    public static final String FAKE_IMEISV = "99";
    public static final String FAKE_LONG_NAME = "Fake long name";
    public static final String FAKE_MCC_MNC = "310260";
    public static final String FAKE_MEID = "1234";
    public static final String FAKE_SHORT_NAME = "Fake short name";
    private static final SimFdnState INITIAL_FDN_STATE;
    private static final SimLockState INITIAL_LOCK_STATE;
    private static final String LOG_TAG = "SimulatedCommands";
    private static final String SIM_PUK2_CODE = "87654321";
    private static final String SIM_PUK_CODE = "12345678";
    private final AtomicInteger getNetworkSelectionModeCallCount;
    private AtomicBoolean mAllowed;
    private List<CellInfo> mCellInfoList;
    private int mChannelId;
    public boolean mCssSupported;
    private int mDataRadioTech;
    private int mDataRegState;
    private boolean mDcSuccess;
    public int mDefaultRoamingIndicator;
    private final AtomicInteger mGetDataRegistrationStateCallCount;
    private final AtomicInteger mGetOperatorCallCount;
    private final AtomicInteger mGetVoiceRegistrationStateCallCount;
    HandlerThread mHandlerThread;
    private IccCardStatus mIccCardStatus;
    private IccIoResult mIccIoResultForApduLogicalChannel;
    private IccSlotStatus mIccSlotStatus;
    private String mImei;
    private String mImeiSv;
    private int[] mImsRegState;
    private boolean mIsRadioPowerFailResponse;
    public int mMaxDataCalls;
    int mNetworkType;
    int mNextCallFailCause;
    int mPausedResponseCount;
    ArrayList<Message> mPausedResponses;
    int mPin1attemptsRemaining = 5;
    String mPin2Code;
    int mPin2UnlockAttempts;
    String mPinCode;
    int mPinUnlockAttempts;
    int mPuk2UnlockAttempts;
    int mPukUnlockAttempts;
    public int mReasonForDenial;
    public int mRoamingIndicator;
    private SetupDataCallResult mSetupDataCallResult;
    private boolean mShouldReturnCellInfo;
    private SignalStrength mSignalStrength;
    boolean mSimFdnEnabled;
    SimFdnState mSimFdnEnabledState;
    boolean mSimLockEnabled;
    SimLockState mSimLockedState;
    boolean mSsnNotifyOn;
    public int mSystemIsInPrl;
    private int mVoiceRadioTech;
    private int mVoiceRegState;
    SimulatedGsmCallState simulatedCallState;

    static {
        INITIAL_LOCK_STATE = SimLockState.NONE;
        INITIAL_FDN_STATE = SimFdnState.NONE;
    }

    public SimulatedCommands() {
        super(null);
        boolean bl = false;
        this.mSsnNotifyOn = false;
        this.mVoiceRegState = 1;
        this.mVoiceRadioTech = 3;
        this.mDataRegState = 1;
        this.mDataRadioTech = 3;
        this.mCellInfoList = null;
        this.mShouldReturnCellInfo = true;
        this.mChannelId = -1;
        this.mPausedResponses = new ArrayList();
        this.mNextCallFailCause = 16;
        this.mDcSuccess = true;
        this.mIsRadioPowerFailResponse = false;
        this.mGetVoiceRegistrationStateCallCount = new AtomicInteger(0);
        this.mGetDataRegistrationStateCallCount = new AtomicInteger(0);
        this.mGetOperatorCallCount = new AtomicInteger(0);
        this.getNetworkSelectionModeCallCount = new AtomicInteger(0);
        this.mAllowed = new AtomicBoolean(false);
        this.mHandlerThread = new HandlerThread(LOG_TAG);
        this.mHandlerThread.start();
        this.simulatedCallState = new SimulatedGsmCallState(this.mHandlerThread.getLooper());
        this.setRadioState(1, false);
        this.mSimLockedState = INITIAL_LOCK_STATE;
        boolean bl2 = this.mSimLockedState != SimLockState.NONE;
        this.mSimLockEnabled = bl2;
        this.mPinCode = "1234";
        this.mSimFdnEnabledState = INITIAL_FDN_STATE;
        bl2 = bl;
        if (this.mSimFdnEnabledState != SimFdnState.NONE) {
            bl2 = true;
        }
        this.mSimFdnEnabled = bl2;
        this.mPin2Code = DEFAULT_SIM_PIN2_CODE;
    }

    private CellInfoGsm getCellInfoGsm() {
        Parcel parcel = Parcel.obtain();
        parcel.writeInt(1);
        parcel.writeInt(1);
        parcel.writeInt(2);
        parcel.writeLong(1453510289108L);
        parcel.writeInt(0);
        parcel.writeInt(1);
        parcel.writeString("310");
        parcel.writeString("260");
        parcel.writeString("long");
        parcel.writeString("short");
        parcel.writeInt(123);
        parcel.writeInt(456);
        parcel.writeInt(950);
        parcel.writeInt(27);
        parcel.writeInt(99);
        parcel.writeInt(0);
        parcel.writeInt(3);
        parcel.setDataPosition(0);
        return (CellInfoGsm)CellInfoGsm.CREATOR.createFromParcel(parcel);
    }

    private boolean isSimLocked() {
        return this.mSimLockedState != SimLockState.NONE;
    }

    private void log(String string) {
        Rlog.d((String)LOG_TAG, (String)string);
    }

    private void resultFail(Message message, Object object, Throwable throwable) {
        if (message != null) {
            AsyncResult.forMessage((Message)message, (Object)object, (Throwable)throwable);
            if (this.mPausedResponseCount > 0) {
                this.mPausedResponses.add(message);
            } else {
                message.sendToTarget();
            }
        }
    }

    private void resultSuccess(Message message, Object object) {
        if (message != null) {
            AsyncResult.forMessage((Message)message).result = object;
            if (this.mPausedResponseCount > 0) {
                this.mPausedResponses.add(message);
            } else {
                message.sendToTarget();
            }
        }
    }

    private void unimplemented(Message message) {
        if (message != null) {
            AsyncResult.forMessage((Message)message).exception = new RuntimeException("Unimplemented");
            if (this.mPausedResponseCount > 0) {
                this.mPausedResponses.add(message);
            } else {
                message.sendToTarget();
            }
        }
    }

    @Override
    public void acceptCall(Message message) {
        SimulatedCommandsVerifier.getInstance().acceptCall(message);
        if (!this.simulatedCallState.onAnswer()) {
            this.resultFail(message, null, new RuntimeException("Hangup Error"));
        } else {
            this.resultSuccess(message, null);
        }
    }

    @Override
    public void acknowledgeIncomingGsmSmsWithPdu(boolean bl, String string, Message message) {
        this.unimplemented(message);
    }

    @Override
    public void acknowledgeLastIncomingCdmaSms(boolean bl, int n, Message message) {
        this.unimplemented(message);
    }

    @Override
    public void acknowledgeLastIncomingGsmSms(boolean bl, int n, Message message) {
        this.unimplemented(message);
        SimulatedCommandsVerifier.getInstance().acknowledgeLastIncomingGsmSms(bl, n, message);
    }

    @Override
    public void cancelPendingUssd(Message message) {
        this.resultSuccess(message, null);
    }

    @Override
    public void changeBarringPassword(String string, String string2, String string3, Message message) {
        this.unimplemented(message);
    }

    @Override
    public void changeIccPin(String string, String string2, Message message) {
        if (string != null && string.equals(this.mPinCode)) {
            this.mPinCode = string2;
            this.resultSuccess(message, null);
            return;
        }
        Rlog.i((String)LOG_TAG, (String)"[SimCmd] changeIccPin: pin failed!");
        this.resultFail(message, null, new CommandException(CommandException.Error.PASSWORD_INCORRECT));
    }

    @Override
    public void changeIccPin2(String string, String string2, Message message) {
        if (string != null && string.equals(this.mPin2Code)) {
            this.mPin2Code = string2;
            this.resultSuccess(message, null);
            return;
        }
        Rlog.i((String)LOG_TAG, (String)"[SimCmd] changeIccPin2: pin2 failed!");
        this.resultFail(message, null, new CommandException(CommandException.Error.PASSWORD_INCORRECT));
    }

    @Override
    public void changeIccPin2ForApp(String string, String string2, String string3, Message message) {
        this.unimplemented(message);
    }

    @Override
    public void changeIccPinForApp(String string, String string2, String string3, Message message) {
        SimulatedCommandsVerifier.getInstance().changeIccPinForApp(string, string2, string3, message);
        this.changeIccPin(string, string2, message);
    }

    @Override
    public void conference(Message message) {
        if (!this.simulatedCallState.onChld('3', '\u0000')) {
            this.resultFail(message, null, new RuntimeException("Hangup Error"));
        } else {
            this.resultSuccess(message, null);
        }
    }

    @Override
    public void deactivateDataCall(int n, int n2, Message message) {
        SimulatedCommandsVerifier.getInstance().deactivateDataCall(n, n2, message);
        this.resultSuccess(message, null);
    }

    @Override
    public void deleteSmsOnRuim(int n, Message message) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Delete RUIM message at index ");
        stringBuilder.append(n);
        Rlog.d((String)LOG_TAG, (String)stringBuilder.toString());
        this.unimplemented(message);
    }

    @Override
    public void deleteSmsOnSim(int n, Message message) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Delete message at index ");
        stringBuilder.append(n);
        Rlog.d((String)LOG_TAG, (String)stringBuilder.toString());
        this.unimplemented(message);
    }

    @Override
    public void dial(String string, boolean bl, EmergencyNumber emergencyNumber, boolean bl2, int n, Message message) {
        SimulatedCommandsVerifier.getInstance().dial(string, bl, emergencyNumber, bl2, n, message);
        this.simulatedCallState.onDial(string);
        this.resultSuccess(message, null);
    }

    @Override
    public void dial(String string, boolean bl, EmergencyNumber emergencyNumber, boolean bl2, int n, UUSInfo uUSInfo, Message message) {
        SimulatedCommandsVerifier.getInstance().dial(string, bl, emergencyNumber, bl2, n, uUSInfo, message);
        this.simulatedCallState.onDial(string);
        this.resultSuccess(message, null);
    }

    public void dispose() {
        HandlerThread handlerThread = this.mHandlerThread;
        if (handlerThread != null) {
            handlerThread.quit();
        }
    }

    @Override
    public void exitEmergencyCallbackMode(Message message) {
        this.unimplemented(message);
    }

    @Override
    public void explicitCallTransfer(Message message) {
        if (!this.simulatedCallState.onChld('4', '\u0000')) {
            this.resultFail(message, null, new RuntimeException("Hangup Error"));
        } else {
            this.resultSuccess(message, null);
        }
    }

    public void forceDataDormancy(Message message) {
        this.unimplemented(message);
    }

    @Override
    public void getAllowedCarriers(Message message, WorkSource workSource) {
        this.unimplemented(message);
    }

    @Override
    public void getAvailableNetworks(Message message) {
        this.unimplemented(message);
    }

    @Override
    public void getBasebandVersion(Message message) {
        SimulatedCommandsVerifier.getInstance().getBasebandVersion(message);
        this.resultSuccess(message, LOG_TAG);
    }

    @Override
    public void getCDMASubscription(Message message) {
        this.resultSuccess(message, new String[]{"123", "456", "789", "234", "345"});
    }

    @Override
    public void getCLIR(Message message) {
        this.unimplemented(message);
    }

    @Override
    public void getCdmaBroadcastConfig(Message message) {
        this.unimplemented(message);
    }

    @Override
    public void getCdmaSubscriptionSource(Message message) {
        this.unimplemented(message);
    }

    @Override
    public void getCellInfoList(Message message, WorkSource object) {
        synchronized (this) {
            block5 : {
                boolean bl = this.mShouldReturnCellInfo;
                if (bl) break block5;
                return;
            }
            if (this.mCellInfoList == null) {
                object = new ArrayList();
                ((ArrayList)object).add(this.getCellInfoGsm());
            }
            this.resultSuccess(message, this.mCellInfoList);
            return;
        }
    }

    @Override
    public void getCurrentCalls(Message message) {
        SimulatedCommandsVerifier.getInstance().getCurrentCalls(message);
        if (this.mState == 1 && !this.isSimLocked()) {
            this.resultSuccess(message, this.simulatedCallState.getDriverCalls());
        } else {
            this.resultFail(message, null, new CommandException(CommandException.Error.RADIO_NOT_AVAILABLE));
        }
    }

    @Override
    public void getDataCallList(Message message) {
        ArrayList<SetupDataCallResult> arrayList = new ArrayList<SetupDataCallResult>(0);
        SimulatedCommandsVerifier.getInstance().getDataCallList(message);
        SetupDataCallResult setupDataCallResult = this.mSetupDataCallResult;
        if (setupDataCallResult != null) {
            arrayList.add(setupDataCallResult);
        }
        this.resultSuccess(message, arrayList);
    }

    @Override
    public void getDataRegistrationState(Message message) {
        this.mGetDataRegistrationStateCallCount.incrementAndGet();
        DataRegStateResult dataRegStateResult = new DataRegStateResult();
        dataRegStateResult.regState = this.mDataRegState;
        dataRegStateResult.rat = this.mDataRadioTech;
        dataRegStateResult.maxDataCalls = this.mMaxDataCalls;
        dataRegStateResult.reasonDataDenied = this.mReasonForDenial;
        this.resultSuccess(message, (Object)dataRegStateResult);
    }

    @Override
    public void getDeviceIdentity(Message message) {
        SimulatedCommandsVerifier.getInstance().getDeviceIdentity(message);
        this.resultSuccess(message, new String[]{FAKE_IMEI, FAKE_IMEISV, "1234", "1234"});
    }

    @VisibleForTesting
    public int getGetDataRegistrationStateCallCount() {
        return this.mGetDataRegistrationStateCallCount.get();
    }

    @VisibleForTesting
    public int getGetNetworkSelectionModeCallCount() {
        return this.getNetworkSelectionModeCallCount.get();
    }

    @VisibleForTesting
    public int getGetOperatorCallCount() {
        this.mGetOperatorCallCount.get();
        return this.mGetOperatorCallCount.get();
    }

    @VisibleForTesting
    public int getGetVoiceRegistrationStateCallCount() {
        return this.mGetVoiceRegistrationStateCallCount.get();
    }

    @Override
    public void getGsmBroadcastConfig(Message message) {
        this.unimplemented(message);
    }

    public Handler getHandler() {
        return this.mHandlerThread.getThreadHandler();
    }

    @Override
    public void getHardwareConfig(Message message) {
        this.unimplemented(message);
    }

    @Override
    public void getIMEI(Message message) {
        SimulatedCommandsVerifier.getInstance().getIMEI(message);
        String string = this.mImei;
        if (string == null) {
            string = FAKE_IMEI;
        }
        this.resultSuccess(message, string);
    }

    @Override
    public void getIMEISV(Message message) {
        SimulatedCommandsVerifier.getInstance().getIMEISV(message);
        String string = this.mImeiSv;
        if (string == null) {
            string = FAKE_IMEISV;
        }
        this.resultSuccess(message, string);
    }

    @Override
    public void getIMSI(Message message) {
        this.getIMSIForApp(null, message);
    }

    @Override
    public void getIMSIForApp(String string, Message message) {
        this.resultSuccess(message, FAKE_IMEI);
    }

    @Override
    public void getIccCardStatus(Message message) {
        SimulatedCommandsVerifier.getInstance().getIccCardStatus(message);
        IccCardStatus iccCardStatus = this.mIccCardStatus;
        if (iccCardStatus != null) {
            this.resultSuccess(message, iccCardStatus);
        } else {
            this.resultFail(message, null, new RuntimeException("IccCardStatus not set"));
        }
    }

    @Override
    public void getIccSlotsStatus(Message message) {
        SimulatedCommandsVerifier.getInstance().getIccSlotsStatus(message);
        IccSlotStatus iccSlotStatus = this.mIccSlotStatus;
        if (iccSlotStatus != null) {
            this.resultSuccess(message, iccSlotStatus);
        } else {
            this.resultFail(message, null, new CommandException(CommandException.Error.REQUEST_NOT_SUPPORTED));
        }
    }

    @Override
    public void getImsRegistrationState(Message message) {
        if (this.mImsRegState == null) {
            this.mImsRegState = new int[]{1, 0};
        }
        this.resultSuccess(message, this.mImsRegState);
    }

    @Override
    public void getLastCallFailCause(Message message) {
        LastCallFailCause lastCallFailCause = new LastCallFailCause();
        lastCallFailCause.causeCode = this.mNextCallFailCause;
        this.resultSuccess(message, lastCallFailCause);
    }

    @Override
    public void getLastDataCallFailCause(Message message) {
        this.unimplemented(message);
    }

    @Deprecated
    @Override
    public void getLastPdpFailCause(Message message) {
        this.unimplemented(message);
    }

    @Override
    public void getModemActivityInfo(Message message, WorkSource workSource) {
        this.unimplemented(message);
    }

    @Override
    public void getMute(Message message) {
        this.unimplemented(message);
    }

    @Override
    public void getNetworkSelectionMode(Message message) {
        SimulatedCommandsVerifier.getInstance().getNetworkSelectionMode(message);
        this.getNetworkSelectionModeCallCount.incrementAndGet();
        this.resultSuccess(message, new int[]{0});
    }

    @Override
    public void getOperator(Message message) {
        this.mGetOperatorCallCount.incrementAndGet();
        this.resultSuccess(message, new String[]{FAKE_LONG_NAME, FAKE_SHORT_NAME, FAKE_MCC_MNC});
    }

    @Deprecated
    @Override
    public void getPDPContextList(Message message) {
        this.getDataCallList(message);
    }

    @Override
    public void getPreferredNetworkType(Message message) {
        SimulatedCommandsVerifier.getInstance().getPreferredNetworkType(message);
        this.resultSuccess(message, new int[]{this.mNetworkType});
    }

    @Override
    public void getPreferredVoicePrivacy(Message message) {
        this.unimplemented(message);
    }

    @Override
    public void getRadioCapability(Message message) {
        SimulatedCommandsVerifier.getInstance().getRadioCapability(message);
        this.resultSuccess(message, new RadioCapability(0, 0, 0, 65535, null, 0));
    }

    @Override
    public int getRilVersion() {
        return 11;
    }

    @Override
    public void getSignalStrength(Message message) {
        if (this.mSignalStrength == null) {
            this.mSignalStrength = new SignalStrength(new CellSignalStrengthCdma(), new CellSignalStrengthGsm(20, 0, Integer.MAX_VALUE), new CellSignalStrengthWcdma(), new CellSignalStrengthTdscdma(), new CellSignalStrengthLte(), new CellSignalStrengthNr());
        }
        this.resultSuccess(message, (Object)this.mSignalStrength);
    }

    @Override
    public void getSmscAddress(Message message) {
        this.unimplemented(message);
    }

    @Override
    public void getVoiceRadioTechnology(Message message) {
        SimulatedCommandsVerifier.getInstance().getVoiceRadioTechnology(message);
        this.resultSuccess(message, new int[]{this.mVoiceRadioTech});
    }

    @Override
    public void getVoiceRegistrationState(Message message) {
        this.mGetVoiceRegistrationStateCallCount.incrementAndGet();
        VoiceRegStateResult voiceRegStateResult = new VoiceRegStateResult();
        voiceRegStateResult.regState = this.mVoiceRegState;
        voiceRegStateResult.rat = this.mVoiceRadioTech;
        voiceRegStateResult.cssSupported = this.mCssSupported;
        voiceRegStateResult.roamingIndicator = this.mRoamingIndicator;
        voiceRegStateResult.systemIsInPrl = this.mSystemIsInPrl;
        voiceRegStateResult.defaultRoamingIndicator = this.mDefaultRoamingIndicator;
        voiceRegStateResult.reasonForDenial = this.mReasonForDenial;
        this.resultSuccess(message, (Object)voiceRegStateResult);
    }

    @Override
    public void handleCallSetupRequestFromSim(boolean bl, Message message) {
        this.resultSuccess(message, null);
    }

    @Override
    public void hangupConnection(int n, Message message) {
        if (!this.simulatedCallState.onChld('1', (char)(n + 48))) {
            Rlog.i((String)"GSM", (String)"[SimCmd] hangupConnection: resultFail");
            this.resultFail(message, null, new RuntimeException("Hangup Error"));
        } else {
            Rlog.i((String)"GSM", (String)"[SimCmd] hangupConnection: resultSuccess");
            this.resultSuccess(message, null);
        }
    }

    @Override
    public void hangupForegroundResumeBackground(Message message) {
        if (!this.simulatedCallState.onChld('1', '\u0000')) {
            this.resultFail(message, null, new RuntimeException("Hangup Error"));
        } else {
            this.resultSuccess(message, null);
        }
    }

    @Override
    public void hangupWaitingOrBackground(Message message) {
        if (!this.simulatedCallState.onChld('0', '\u0000')) {
            this.resultFail(message, null, new RuntimeException("Hangup Error"));
        } else {
            this.resultSuccess(message, null);
        }
    }

    @Override
    public void iccCloseLogicalChannel(int n, Message message) {
        this.unimplemented(message);
    }

    @Override
    public void iccIO(int n, int n2, String string, int n3, int n4, int n5, String string2, String string3, Message message) {
        this.iccIOForApp(n, n2, string, n3, n4, n5, string2, string3, null, message);
    }

    @Override
    public void iccIOForApp(int n, int n2, String string, int n3, int n4, int n5, String string2, String string3, String string4, Message message) {
        this.unimplemented(message);
    }

    @Override
    public void iccOpenLogicalChannel(String string, int n, Message message) {
        SimulatedCommandsVerifier.getInstance().iccOpenLogicalChannel(string, n, message);
        this.resultSuccess(message, new int[]{this.mChannelId});
    }

    @Override
    public void iccTransmitApduBasicChannel(int n, int n2, int n3, int n4, int n5, String string, Message message) {
        this.unimplemented(message);
    }

    @Override
    public void iccTransmitApduLogicalChannel(int n, int n2, int n3, int n4, int n5, int n6, String object, Message message) {
        SimulatedCommandsVerifier.getInstance().iccTransmitApduLogicalChannel(n, n2, n3, n4, n5, n6, (String)object, message);
        object = this.mIccIoResultForApduLogicalChannel;
        if (object != null) {
            this.resultSuccess(message, object);
        } else {
            this.resultFail(message, null, new RuntimeException("IccIoResult not set"));
        }
    }

    @Override
    public void invokeOemRilRequestRaw(byte[] arrby, Message message) {
        if (message != null) {
            AsyncResult.forMessage((Message)message).result = arrby;
            message.sendToTarget();
        }
    }

    @Override
    public void invokeOemRilRequestStrings(String[] arrstring, Message message) {
        if (message != null) {
            AsyncResult.forMessage((Message)message).result = arrstring;
            message.sendToTarget();
        }
    }

    @VisibleForTesting
    public boolean isDataAllowed() {
        return this.mAllowed.get();
    }

    public void notifyEmergencyCallbackMode() {
        if (this.mEmergencyCallbackModeRegistrant != null) {
            this.mEmergencyCallbackModeRegistrant.notifyRegistrant();
        }
    }

    public void notifyExitEmergencyCallbackMode() {
        if (this.mExitEmergencyCallbackModeRegistrants != null) {
            this.mExitEmergencyCallbackModeRegistrants.notifyRegistrants(new AsyncResult(null, null, null));
        }
    }

    public void notifyGsmBroadcastSms(Object object) {
        if (this.mGsmBroadcastSmsRegistrant != null) {
            this.mGsmBroadcastSmsRegistrant.notifyRegistrant(new AsyncResult(null, object, null));
        }
    }

    public void notifyIccSmsFull() {
        if (this.mIccSmsFullRegistrant != null) {
            this.mIccSmsFullRegistrant.notifyRegistrant();
        }
    }

    public void notifyImsNetworkStateChanged() {
        if (this.mImsNetworkStateChangedRegistrants != null) {
            this.mImsNetworkStateChangedRegistrants.notifyRegistrants();
        }
    }

    public void notifyModemReset() {
        if (this.mModemResetRegistrants != null) {
            this.mModemResetRegistrants.notifyRegistrants(new AsyncResult(null, (Object)"Test", null));
        }
    }

    @VisibleForTesting
    public void notifyNetworkStateChanged() {
        this.mNetworkStateRegistrants.notifyRegistrants();
    }

    @VisibleForTesting
    public void notifyOtaProvisionStatusChanged() {
        if (this.mOtaProvisionRegistrants != null) {
            this.mOtaProvisionRegistrants.notifyRegistrants(new AsyncResult(null, (Object)new int[]{8}, null));
        }
    }

    public void notifyRadioOn() {
        this.mOnRegistrants.notifyRegistrants();
    }

    public void notifySignalStrength() {
        if (this.mSignalStrength == null) {
            this.mSignalStrength = new SignalStrength(new CellSignalStrengthCdma(), new CellSignalStrengthGsm(20, 0, Integer.MAX_VALUE), new CellSignalStrengthWcdma(), new CellSignalStrengthTdscdma(), new CellSignalStrengthLte(), new CellSignalStrengthNr());
        }
        if (this.mSignalStrengthRegistrant != null) {
            this.mSignalStrengthRegistrant.notifyRegistrant(new AsyncResult(null, (Object)this.mSignalStrength, null));
        }
    }

    public void notifySmsStatus(Object object) {
        if (this.mSmsStatusRegistrant != null) {
            this.mSmsStatusRegistrant.notifyRegistrant(new AsyncResult(null, object, null));
        }
    }

    @Override
    public void nvReadItem(int n, Message message, WorkSource workSource) {
        this.unimplemented(message);
    }

    @Override
    public void nvResetConfig(int n, Message message) {
        this.unimplemented(message);
    }

    @Override
    public void nvWriteCdmaPrl(byte[] arrby, Message message) {
        this.unimplemented(message);
    }

    @Override
    public void nvWriteItem(int n, String string, Message message, WorkSource workSource) {
        this.unimplemented(message);
    }

    @Override
    public void pauseResponses() {
        ++this.mPausedResponseCount;
    }

    @Override
    public void progressConnectingCallState() {
        this.simulatedCallState.progressConnectingCallState();
        this.mCallStateRegistrants.notifyRegistrants();
    }

    @Override
    public void progressConnectingToActive() {
        this.simulatedCallState.progressConnectingToActive();
        this.mCallStateRegistrants.notifyRegistrants();
    }

    @Override
    public void pullLceData(Message message) {
        this.unimplemented(message);
    }

    @Override
    public void queryAvailableBandMode(Message message) {
        this.resultSuccess(message, new int[]{4, 2, 3, 4});
    }

    @Override
    public void queryCLIP(Message message) {
        this.unimplemented(message);
    }

    @Override
    public void queryCallForwardStatus(int n, int n2, String string, Message message) {
        SimulatedCommandsVerifier.getInstance().queryCallForwardStatus(n, n2, string, message);
        this.resultSuccess(message, null);
    }

    @Override
    public void queryCallWaiting(int n, Message message) {
        this.unimplemented(message);
    }

    @Override
    public void queryCdmaRoamingPreference(Message message) {
        this.unimplemented(message);
    }

    @Override
    public void queryFacilityLock(String string, String string2, int n, Message message) {
        this.queryFacilityLockForApp(string, string2, n, null, message);
    }

    @Override
    public void queryFacilityLockForApp(String string, String object, int n, String object2, Message message) {
        if (string != null && string.equals("SC")) {
            if (message != null) {
                object = new int[]{this.mSimLockEnabled ? 1 : 0};
                object2 = new StringBuilder();
                ((StringBuilder)object2).append("[SimCmd] queryFacilityLock: SIM is ");
                string = object[0] == false ? "unlocked" : "locked";
                ((StringBuilder)object2).append(string);
                Rlog.i((String)LOG_TAG, (String)((StringBuilder)object2).toString());
                this.resultSuccess(message, object);
            }
            return;
        }
        if (string != null && string.equals("FD")) {
            if (message != null) {
                object2 = new int[]{this.mSimFdnEnabled ? 1 : 0};
                object = new StringBuilder();
                ((StringBuilder)object).append("[SimCmd] queryFacilityLock: FDN is ");
                string = object2[0] == false ? "disabled" : "enabled";
                ((StringBuilder)object).append(string);
                Rlog.i((String)LOG_TAG, (String)((StringBuilder)object).toString());
                this.resultSuccess(message, object2);
            }
            return;
        }
        this.unimplemented(message);
    }

    @Override
    public void queryTTYMode(Message message) {
        this.unimplemented(message);
    }

    @Override
    public void registerForExitEmergencyCallbackMode(Handler handler, int n, Object object) {
        SimulatedCommandsVerifier.getInstance().registerForExitEmergencyCallbackMode(handler, n, object);
        super.registerForExitEmergencyCallbackMode(handler, n, object);
    }

    @Override
    public void registerForIccRefresh(Handler handler, int n, Object object) {
        super.registerForIccRefresh(handler, n, object);
        SimulatedCommandsVerifier.getInstance().registerForIccRefresh(handler, n, object);
    }

    @Override
    public void registerForLceInfo(Handler handler, int n, Object object) {
        SimulatedCommandsVerifier.getInstance().registerForLceInfo(handler, n, object);
    }

    @Override
    public void registerForModemReset(Handler handler, int n, Object object) {
        SimulatedCommandsVerifier.getInstance().registerForModemReset(handler, n, object);
        super.registerForModemReset(handler, n, object);
    }

    @Override
    public void registerForNattKeepaliveStatus(Handler handler, int n, Object object) {
        SimulatedCommandsVerifier.getInstance().registerForNattKeepaliveStatus(handler, n, object);
    }

    @Override
    public void registerForPcoData(Handler handler, int n, Object object) {
    }

    @Override
    public void rejectCall(Message message) {
        if (!this.simulatedCallState.onChld('0', '\u0000')) {
            this.resultFail(message, null, new RuntimeException("Hangup Error"));
        } else {
            this.resultSuccess(message, null);
        }
    }

    @Override
    public void reportSmsMemoryStatus(boolean bl, Message message) {
        this.resultSuccess(message, null);
        SimulatedCommandsVerifier.getInstance().reportSmsMemoryStatus(bl, message);
    }

    @Override
    public void reportStkServiceIsRunning(Message message) {
        this.resultSuccess(message, null);
    }

    @Override
    public void requestIccSimAuthentication(int n, String string, String string2, Message message) {
        this.unimplemented(message);
    }

    @Override
    public void requestShutdown(Message message) {
        this.setRadioState(2, false);
    }

    @Override
    public void resetRadio(Message message) {
        this.unimplemented(message);
    }

    @Override
    public void resumeResponses() {
        --this.mPausedResponseCount;
        if (this.mPausedResponseCount == 0) {
            int n = this.mPausedResponses.size();
            for (int i = 0; i < n; ++i) {
                this.mPausedResponses.get(i).sendToTarget();
            }
            this.mPausedResponses.clear();
        } else {
            Rlog.e((String)"GSM", (String)"SimulatedCommands.resumeResponses < 0");
        }
    }

    @Override
    public void sendBurstDtmf(String string, int n, int n2, Message message) {
        SimulatedCommandsVerifier.getInstance().sendBurstDtmf(string, n, n2, message);
        this.resultSuccess(message, null);
    }

    @Override
    public void sendCDMAFeatureCode(String string, Message message) {
        this.unimplemented(message);
    }

    @Override
    public void sendCdmaSms(byte[] arrby, Message message) {
        SimulatedCommandsVerifier.getInstance().sendCdmaSms(arrby, message);
        this.resultSuccess(message, null);
    }

    @Override
    public void sendDeviceState(int n, boolean bl, Message message) {
        SimulatedCommandsVerifier.getInstance().sendDeviceState(n, bl, message);
        this.resultSuccess(message, null);
    }

    @Override
    public void sendDtmf(char c, Message message) {
        this.resultSuccess(message, null);
    }

    @Override
    public void sendEnvelope(String string, Message message) {
        this.resultSuccess(message, null);
    }

    @Override
    public void sendEnvelopeWithStatus(String string, Message message) {
        this.resultSuccess(message, null);
    }

    @Override
    public void sendImsCdmaSms(byte[] arrby, int n, int n2, Message message) {
        SimulatedCommandsVerifier.getInstance().sendImsCdmaSms(arrby, n, n2, message);
        this.resultSuccess(message, new SmsResponse(0, null, 0));
    }

    @Override
    public void sendImsGsmSms(String string, String string2, int n, int n2, Message message) {
        SimulatedCommandsVerifier.getInstance().sendImsGsmSms(string, string2, n, n2, message);
        this.resultSuccess(message, new SmsResponse(0, null, 0));
    }

    @Override
    public void sendSMS(String string, String string2, Message message) {
        SimulatedCommandsVerifier.getInstance().sendSMS(string, string2, message);
        this.resultSuccess(message, new SmsResponse(0, null, 0));
    }

    @Override
    public void sendSMSExpectMore(String string, String string2, Message message) {
        this.unimplemented(message);
    }

    public void sendStkCcAplha(String string) {
        this.triggerIncomingStkCcAlpha(string);
    }

    @Override
    public void sendTerminalResponse(String string, Message message) {
        this.resultSuccess(message, null);
    }

    @Override
    public void sendUSSD(String string, Message message) {
        if (string.equals("#646#")) {
            this.resultSuccess(message, null);
            this.triggerIncomingUssd("0", "You have NNN minutes remaining.");
        } else {
            this.resultSuccess(message, null);
            this.triggerIncomingUssd("0", "All Done");
        }
    }

    @Override
    public void separateConnection(int n, Message message) {
        char c = (char)(n + 48);
        if (!this.simulatedCallState.onChld('2', c)) {
            this.resultFail(message, null, new RuntimeException("Hangup Error"));
        } else {
            this.resultSuccess(message, null);
        }
    }

    @Override
    public void setAllowedCarriers(CarrierRestrictionRules carrierRestrictionRules, Message message, WorkSource workSource) {
        this.unimplemented(message);
    }

    @Override
    public void setAutoProgressConnectingCall(boolean bl) {
        this.simulatedCallState.setAutoProgressConnectingCall(bl);
    }

    @Override
    public void setBandMode(int n, Message message) {
        this.resultSuccess(message, null);
    }

    @Override
    public void setCLIR(int n, Message message) {
        this.unimplemented(message);
    }

    @Override
    public void setCallForward(int n, int n2, int n3, String string, int n4, Message message) {
        SimulatedCommandsVerifier.getInstance().setCallForward(n, n2, n3, string, n4, message);
        this.resultSuccess(message, null);
    }

    @Override
    public void setCallWaiting(boolean bl, int n, Message message) {
        this.unimplemented(message);
    }

    @Override
    public void setCarrierInfoForImsiEncryption(ImsiEncryptionInfo imsiEncryptionInfo, Message message) {
        if (message != null) {
            AsyncResult.forMessage((Message)message).result = imsiEncryptionInfo;
            message.sendToTarget();
        }
    }

    @Override
    public void setCdmaBroadcastActivation(boolean bl, Message message) {
        this.unimplemented(message);
    }

    @Override
    public void setCdmaBroadcastConfig(CdmaSmsBroadcastConfigInfo[] arrcdmaSmsBroadcastConfigInfo, Message message) {
        this.unimplemented(message);
    }

    @Override
    public void setCdmaRoamingPreference(int n, Message message) {
        this.unimplemented(message);
    }

    @Override
    public void setCdmaSubscriptionSource(int n, Message message) {
        this.unimplemented(message);
    }

    public void setCellInfoList(List<CellInfo> list) {
        this.mCellInfoList = list;
    }

    public void setCellInfoListBehavior(boolean bl) {
        synchronized (this) {
            this.mShouldReturnCellInfo = bl;
            return;
        }
    }

    @Override
    public void setCellInfoListRate(int n, Message message, WorkSource workSource) {
        this.unimplemented(message);
    }

    @Override
    public void setDataAllowed(boolean bl, Message message) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("setDataAllowed = ");
        stringBuilder.append(bl);
        this.log(stringBuilder.toString());
        this.mAllowed.set(bl);
        this.resultSuccess(message, null);
    }

    public void setDataCallResult(boolean bl, SetupDataCallResult setupDataCallResult) {
        this.mSetupDataCallResult = setupDataCallResult;
        this.mDcSuccess = bl;
    }

    @Override
    public void setDataProfile(DataProfile[] arrdataProfile, boolean bl, Message message) {
    }

    public void setDataRadioTech(int n) {
        this.mDataRadioTech = n;
    }

    public void setDataRegState(int n) {
        this.mDataRegState = n;
    }

    @Override
    public void setEmergencyCallbackMode(Handler handler, int n, Object object) {
        SimulatedCommandsVerifier.getInstance().setEmergencyCallbackMode(handler, n, object);
        super.setEmergencyCallbackMode(handler, n, object);
    }

    @Override
    public void setFacilityLock(String string, boolean bl, String string2, int n, Message message) {
        this.setFacilityLockForApp(string, bl, string2, n, null, message);
    }

    @Override
    public void setFacilityLockForApp(String string, boolean bl, String string2, int n, String string3, Message message) {
        if (string != null && string.equals("SC")) {
            if (string2 != null && string2.equals(this.mPinCode)) {
                Rlog.i((String)LOG_TAG, (String)"[SimCmd] setFacilityLock: pin is valid");
                this.mSimLockEnabled = bl;
                this.resultSuccess(message, null);
                return;
            }
            Rlog.i((String)LOG_TAG, (String)"[SimCmd] setFacilityLock: pin failed!");
            this.resultFail(message, null, new CommandException(CommandException.Error.GENERIC_FAILURE));
            return;
        }
        if (string != null && string.equals("FD")) {
            if (string2 != null && string2.equals(this.mPin2Code)) {
                Rlog.i((String)LOG_TAG, (String)"[SimCmd] setFacilityLock: pin2 is valid");
                this.mSimFdnEnabled = bl;
                this.resultSuccess(message, null);
                return;
            }
            Rlog.i((String)LOG_TAG, (String)"[SimCmd] setFacilityLock: pin2 failed!");
            this.resultFail(message, null, new CommandException(CommandException.Error.GENERIC_FAILURE));
            return;
        }
        this.unimplemented(message);
    }

    @Override
    public void setGsmBroadcastActivation(boolean bl, Message message) {
        this.unimplemented(message);
    }

    @Override
    public void setGsmBroadcastConfig(SmsBroadcastConfigInfo[] arrsmsBroadcastConfigInfo, Message message) {
        this.unimplemented(message);
    }

    public void setIMEI(String string) {
        this.mImei = string;
    }

    public void setIMEISV(String string) {
        this.mImeiSv = string;
    }

    public void setIccCardStatus(IccCardStatus iccCardStatus) {
        this.mIccCardStatus = iccCardStatus;
    }

    public void setIccIoResultForApduLogicalChannel(IccIoResult iccIoResult) {
        this.mIccIoResultForApduLogicalChannel = iccIoResult;
    }

    public void setIccSlotStatus(IccSlotStatus iccSlotStatus) {
        this.mIccSlotStatus = iccSlotStatus;
    }

    public void setImsRegistrationState(int[] arrn) {
        this.mImsRegState = arrn;
    }

    @Override
    public void setInitialAttachApn(DataProfile dataProfile, boolean bl, Message message) {
    }

    @Override
    public void setLinkCapacityReportingCriteria(int n, int n2, int n3, int[] arrn, int[] arrn2, int n4, Message message) {
    }

    @Override
    public void setLocationUpdates(boolean bl, Message message) {
        SimulatedCommandsVerifier.getInstance().setLocationUpdates(bl, message);
        this.resultSuccess(message, null);
    }

    @Override
    public void setLogicalToPhysicalSlotMapping(int[] arrn, Message message) {
        this.unimplemented(message);
    }

    @Override
    public void setMute(boolean bl, Message message) {
        this.unimplemented(message);
    }

    @Override
    public void setNetworkSelectionModeAutomatic(Message message) {
        this.unimplemented(message);
    }

    @Override
    public void setNetworkSelectionModeManual(String string, Message message) {
        this.unimplemented(message);
    }

    @Override
    public void setNextCallFailCause(int n) {
        this.mNextCallFailCause = n;
    }

    @Override
    public void setNextDialFailImmediately(boolean bl) {
        this.simulatedCallState.setNextDialFailImmediately(bl);
    }

    @Override
    public void setOnRestrictedStateChanged(Handler handler, int n, Object object) {
        super.setOnRestrictedStateChanged(handler, n, object);
        SimulatedCommandsVerifier.getInstance().setOnRestrictedStateChanged(handler, n, object);
    }

    public void setOpenChannelId(int n) {
        this.mChannelId = n;
    }

    @Override
    public void setPhoneType(int n) {
    }

    public void setPin1RemainingAttempt(int n) {
        this.mPin1attemptsRemaining = n;
    }

    @Override
    public void setPreferredNetworkType(int n, Message message) {
        SimulatedCommandsVerifier.getInstance().setPreferredNetworkType(n, message);
        this.mNetworkType = n;
        this.resultSuccess(message, null);
    }

    @Override
    public void setPreferredVoicePrivacy(boolean bl, Message message) {
        this.unimplemented(message);
    }

    @Override
    public void setRadioPower(boolean bl, Message message) {
        if (this.mIsRadioPowerFailResponse) {
            this.resultFail(message, null, new RuntimeException("setRadioPower failed!"));
            return;
        }
        if (bl) {
            this.setRadioState(1, false);
        } else {
            this.setRadioState(0, false);
        }
        this.resultSuccess(message, null);
    }

    public void setRadioPowerFailResponse(boolean bl) {
        this.mIsRadioPowerFailResponse = bl;
    }

    public void setSignalStrength(SignalStrength signalStrength) {
        this.mSignalStrength = signalStrength;
    }

    @Override
    public void setSignalStrengthReportingCriteria(int n, int n2, int[] arrn, int n3, Message message) {
    }

    @Override
    public void setSimCardPower(int n, Message message, WorkSource workSource) {
    }

    @Override
    public void setSmscAddress(String string, Message message) {
        this.unimplemented(message);
    }

    @Override
    public void setSuppServiceNotifications(boolean bl, Message message) {
        this.resultSuccess(message, null);
        if (bl && this.mSsnNotifyOn) {
            Rlog.w((String)LOG_TAG, (String)"Supp Service Notifications already enabled!");
        }
        this.mSsnNotifyOn = bl;
    }

    @Override
    public void setTTYMode(int n, Message message) {
        Rlog.w((String)LOG_TAG, (String)"Not implemented in SimulatedCommands");
        this.unimplemented(message);
    }

    @Override
    public void setUnsolResponseFilter(int n, Message message) {
        SimulatedCommandsVerifier.getInstance().setUnsolResponseFilter(n, message);
        this.resultSuccess(message, null);
    }

    public void setVoiceRadioTech(int n) {
        this.mVoiceRadioTech = n;
    }

    public void setVoiceRegState(int n) {
        this.mVoiceRegState = n;
    }

    @Override
    public void setupDataCall(int n, DataProfile dataProfile, boolean bl, boolean bl2, int n2, LinkProperties linkProperties, Message message) {
        SimulatedCommandsVerifier.getInstance().setupDataCall(n, dataProfile, bl, bl2, n2, linkProperties, message);
        if (this.mSetupDataCallResult == null) {
            try {
                dataProfile = new SetupDataCallResult();
                this.mSetupDataCallResult = dataProfile;
                this.mSetupDataCallResult.status = 0;
                this.mSetupDataCallResult.suggestedRetryTime = -1;
                this.mSetupDataCallResult.cid = 1;
                this.mSetupDataCallResult.active = 2;
                this.mSetupDataCallResult.type = "IP";
                this.mSetupDataCallResult.ifname = "rmnet_data7";
                this.mSetupDataCallResult.addresses = "12.34.56.78";
                this.mSetupDataCallResult.dnses = "98.76.54.32";
                this.mSetupDataCallResult.gateways = "11.22.33.44";
                this.mSetupDataCallResult.pcscf = "fd00:976a:c305:1d::8 fd00:976a:c202:1d::7 fd00:976a:c305:1d::5";
                this.mSetupDataCallResult.mtu = 1440;
            }
            catch (Exception exception) {
                // empty catch block
            }
        }
        dataProfile = RIL.convertDataCallResult((Object)this.mSetupDataCallResult);
        if (this.mDcSuccess) {
            this.resultSuccess(message, (Object)dataProfile);
        } else {
            this.resultFail(message, (Object)dataProfile, new RuntimeException("Setup data call failed!"));
        }
    }

    @Override
    public void shutdown() {
        this.setRadioState(2, false);
        Looper looper = this.mHandlerThread.getLooper();
        if (looper != null) {
            looper.quit();
        }
    }

    @Override
    public void startDtmf(char c, Message message) {
        this.resultSuccess(message, null);
    }

    @Override
    public void startLceService(int n, boolean bl, Message message) {
        SimulatedCommandsVerifier.getInstance().startLceService(n, bl, message);
    }

    @Override
    public void startNattKeepalive(int n, KeepalivePacketData keepalivePacketData, int n2, Message message) {
        SimulatedCommandsVerifier.getInstance().startNattKeepalive(n, keepalivePacketData, n2, message);
    }

    @Override
    public void startNetworkScan(NetworkScanRequest networkScanRequest, Message message) {
        this.unimplemented(message);
    }

    @Override
    public void stopDtmf(Message message) {
        this.resultSuccess(message, null);
    }

    @Override
    public void stopLceService(Message message) {
        this.unimplemented(message);
    }

    @Override
    public void stopNattKeepalive(int n, Message message) {
        SimulatedCommandsVerifier.getInstance().stopNattKeepalive(n, message);
    }

    @Override
    public void stopNetworkScan(Message message) {
        this.unimplemented(message);
    }

    @Override
    public void supplyIccPin(String charSequence, Message message) {
        if (this.mSimLockedState != SimLockState.REQUIRE_PIN) {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("[SimCmd] supplyIccPin: wrong state, state=");
            ((StringBuilder)charSequence).append((Object)this.mSimLockedState);
            Rlog.i((String)LOG_TAG, (String)((StringBuilder)charSequence).toString());
            this.resultFail(message, null, new CommandException(CommandException.Error.PASSWORD_INCORRECT));
            return;
        }
        if (charSequence != null && ((String)charSequence).equals(this.mPinCode)) {
            Rlog.i((String)LOG_TAG, (String)"[SimCmd] supplyIccPin: success!");
            this.mPinUnlockAttempts = 0;
            this.mSimLockedState = SimLockState.NONE;
            this.mIccStatusChangedRegistrants.notifyRegistrants();
            this.resultSuccess(message, null);
            return;
        }
        if (message != null) {
            ++this.mPinUnlockAttempts;
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("[SimCmd] supplyIccPin: failed! attempt=");
            ((StringBuilder)charSequence).append(this.mPinUnlockAttempts);
            Rlog.i((String)LOG_TAG, (String)((StringBuilder)charSequence).toString());
            if (this.mPinUnlockAttempts >= 5) {
                Rlog.i((String)LOG_TAG, (String)"[SimCmd] supplyIccPin: set state to REQUIRE_PUK");
                this.mSimLockedState = SimLockState.REQUIRE_PUK;
            }
            this.resultFail(message, null, new CommandException(CommandException.Error.PASSWORD_INCORRECT));
        }
    }

    @Override
    public void supplyIccPin2(String charSequence, Message message) {
        if (this.mSimFdnEnabledState != SimFdnState.REQUIRE_PIN2) {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("[SimCmd] supplyIccPin2: wrong state, state=");
            ((StringBuilder)charSequence).append((Object)this.mSimFdnEnabledState);
            Rlog.i((String)LOG_TAG, (String)((StringBuilder)charSequence).toString());
            this.resultFail(message, null, new CommandException(CommandException.Error.PASSWORD_INCORRECT));
            return;
        }
        if (charSequence != null && ((String)charSequence).equals(this.mPin2Code)) {
            Rlog.i((String)LOG_TAG, (String)"[SimCmd] supplyIccPin2: success!");
            this.mPin2UnlockAttempts = 0;
            this.mSimFdnEnabledState = SimFdnState.NONE;
            this.resultSuccess(message, null);
            return;
        }
        if (message != null) {
            ++this.mPin2UnlockAttempts;
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("[SimCmd] supplyIccPin2: failed! attempt=");
            ((StringBuilder)charSequence).append(this.mPin2UnlockAttempts);
            Rlog.i((String)LOG_TAG, (String)((StringBuilder)charSequence).toString());
            if (this.mPin2UnlockAttempts >= 5) {
                Rlog.i((String)LOG_TAG, (String)"[SimCmd] supplyIccPin2: set state to REQUIRE_PUK2");
                this.mSimFdnEnabledState = SimFdnState.REQUIRE_PUK2;
            }
            this.resultFail(message, null, new CommandException(CommandException.Error.PASSWORD_INCORRECT));
        }
    }

    @Override
    public void supplyIccPin2ForApp(String string, String string2, Message message) {
        this.unimplemented(message);
    }

    @Override
    public void supplyIccPinForApp(String object, String string, Message message) {
        int n;
        SimulatedCommandsVerifier.getInstance().supplyIccPinForApp((String)object, string, message);
        string = this.mPinCode;
        if (string != null && string.equals(object)) {
            this.resultSuccess(message, null);
            return;
        }
        Rlog.i((String)LOG_TAG, (String)"[SimCmd] supplyIccPinForApp: pin failed!");
        object = new CommandException(CommandException.Error.PASSWORD_INCORRECT);
        this.mPin1attemptsRemaining = n = this.mPin1attemptsRemaining - 1;
        n = n < 0 ? 0 : this.mPin1attemptsRemaining;
        this.resultFail(message, new int[]{n}, (Throwable)object);
    }

    @Override
    public void supplyIccPuk(String charSequence, String string, Message message) {
        if (this.mSimLockedState != SimLockState.REQUIRE_PUK) {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("[SimCmd] supplyIccPuk: wrong state, state=");
            ((StringBuilder)charSequence).append((Object)this.mSimLockedState);
            Rlog.i((String)LOG_TAG, (String)((StringBuilder)charSequence).toString());
            this.resultFail(message, null, new CommandException(CommandException.Error.PASSWORD_INCORRECT));
            return;
        }
        if (charSequence != null && ((String)charSequence).equals(SIM_PUK_CODE)) {
            Rlog.i((String)LOG_TAG, (String)"[SimCmd] supplyIccPuk: success!");
            this.mSimLockedState = SimLockState.NONE;
            this.mPukUnlockAttempts = 0;
            this.mIccStatusChangedRegistrants.notifyRegistrants();
            this.resultSuccess(message, null);
            return;
        }
        if (message != null) {
            ++this.mPukUnlockAttempts;
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("[SimCmd] supplyIccPuk: failed! attempt=");
            ((StringBuilder)charSequence).append(this.mPukUnlockAttempts);
            Rlog.i((String)LOG_TAG, (String)((StringBuilder)charSequence).toString());
            if (this.mPukUnlockAttempts >= 10) {
                Rlog.i((String)LOG_TAG, (String)"[SimCmd] supplyIccPuk: set state to SIM_PERM_LOCKED");
                this.mSimLockedState = SimLockState.SIM_PERM_LOCKED;
            }
            this.resultFail(message, null, new CommandException(CommandException.Error.PASSWORD_INCORRECT));
        }
    }

    @Override
    public void supplyIccPuk2(String charSequence, String string, Message message) {
        if (this.mSimFdnEnabledState != SimFdnState.REQUIRE_PUK2) {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("[SimCmd] supplyIccPuk2: wrong state, state=");
            ((StringBuilder)charSequence).append((Object)this.mSimLockedState);
            Rlog.i((String)LOG_TAG, (String)((StringBuilder)charSequence).toString());
            this.resultFail(message, null, new CommandException(CommandException.Error.PASSWORD_INCORRECT));
            return;
        }
        if (charSequence != null && ((String)charSequence).equals(SIM_PUK2_CODE)) {
            Rlog.i((String)LOG_TAG, (String)"[SimCmd] supplyIccPuk2: success!");
            this.mSimFdnEnabledState = SimFdnState.NONE;
            this.mPuk2UnlockAttempts = 0;
            this.resultSuccess(message, null);
            return;
        }
        if (message != null) {
            ++this.mPuk2UnlockAttempts;
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("[SimCmd] supplyIccPuk2: failed! attempt=");
            ((StringBuilder)charSequence).append(this.mPuk2UnlockAttempts);
            Rlog.i((String)LOG_TAG, (String)((StringBuilder)charSequence).toString());
            if (this.mPuk2UnlockAttempts >= 10) {
                Rlog.i((String)LOG_TAG, (String)"[SimCmd] supplyIccPuk2: set state to SIM_PERM_LOCKED");
                this.mSimFdnEnabledState = SimFdnState.SIM_PERM_LOCKED;
            }
            this.resultFail(message, null, new CommandException(CommandException.Error.PASSWORD_INCORRECT));
        }
    }

    @Override
    public void supplyIccPuk2ForApp(String string, String string2, String string3, Message message) {
        this.unimplemented(message);
    }

    @Override
    public void supplyIccPukForApp(String string, String string2, String string3, Message message) {
        this.unimplemented(message);
    }

    @Override
    public void supplyNetworkDepersonalization(String string, Message message) {
        this.unimplemented(message);
    }

    @Override
    public void switchWaitingOrHoldingAndActive(Message message) {
        if (!this.simulatedCallState.onChld('2', '\u0000')) {
            this.resultFail(message, null, new RuntimeException("Hangup Error"));
        } else {
            this.resultSuccess(message, null);
        }
    }

    @Override
    public void triggerHangupAll() {
        this.simulatedCallState.triggerHangupAll();
        this.mCallStateRegistrants.notifyRegistrants();
    }

    @Override
    public void triggerHangupBackground() {
        this.simulatedCallState.triggerHangupBackground();
        this.mCallStateRegistrants.notifyRegistrants();
    }

    @Override
    public void triggerHangupForeground() {
        this.simulatedCallState.triggerHangupForeground();
        this.mCallStateRegistrants.notifyRegistrants();
    }

    @Override
    public void triggerIncomingSMS(String string) {
    }

    public void triggerIncomingStkCcAlpha(String string) {
        if (this.mCatCcAlphaRegistrant != null) {
            this.mCatCcAlphaRegistrant.notifyResult((Object)string);
        }
    }

    @Override
    public void triggerIncomingUssd(String string, String string2) {
        if (this.mUSSDRegistrant != null) {
            this.mUSSDRegistrant.notifyResult((Object)new String[]{string, string2});
        }
    }

    public void triggerNITZupdate(String string) {
        if (string != null) {
            this.mNITZTimeRegistrant.notifyRegistrant(new AsyncResult(null, (Object)new Object[]{string, SystemClock.elapsedRealtime()}, null));
        }
    }

    @VisibleForTesting
    public void triggerRestrictedStateChanged(int n) {
        if (this.mRestrictedStateRegistrant != null) {
            this.mRestrictedStateRegistrant.notifyRegistrant(new AsyncResult(null, (Object)n, null));
        }
    }

    @Override
    public void triggerRing(String string) {
        this.simulatedCallState.triggerRing(string);
        this.mCallStateRegistrants.notifyRegistrants();
    }

    @Override
    public void triggerSsn(int n, int n2) {
        SuppServiceNotification suppServiceNotification = new SuppServiceNotification();
        suppServiceNotification.notificationType = n;
        suppServiceNotification.code = n2;
        this.mSsnRegistrant.notifyRegistrant(new AsyncResult(null, (Object)suppServiceNotification, null));
    }

    @Override
    public void unregisterForIccRefresh(Handler handler) {
        super.unregisterForIccRefresh(handler);
        SimulatedCommandsVerifier.getInstance().unregisterForIccRefresh(handler);
    }

    @Override
    public void unregisterForLceInfo(Handler handler) {
        SimulatedCommandsVerifier.getInstance().unregisterForLceInfo(handler);
    }

    @Override
    public void unregisterForNattKeepaliveStatus(Handler handler) {
        SimulatedCommandsVerifier.getInstance().unregisterForNattKeepaliveStatus(handler);
    }

    @Override
    public void unregisterForPcoData(Handler handler) {
    }

    @Override
    public void writeSmsToRuim(int n, String charSequence, Message message) {
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append("Write SMS to RUIM with status ");
        ((StringBuilder)charSequence).append(n);
        Rlog.d((String)LOG_TAG, (String)((StringBuilder)charSequence).toString());
        this.unimplemented(message);
    }

    @Override
    public void writeSmsToSim(int n, String charSequence, String string, Message message) {
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append("Write SMS to SIM with status ");
        ((StringBuilder)charSequence).append(n);
        Rlog.d((String)LOG_TAG, (String)((StringBuilder)charSequence).toString());
        this.unimplemented(message);
    }

    private static enum SimFdnState {
        NONE,
        REQUIRE_PIN2,
        REQUIRE_PUK2,
        SIM_PERM_LOCKED;
        
    }

    private static enum SimLockState {
        NONE,
        REQUIRE_PIN,
        REQUIRE_PUK,
        SIM_PERM_LOCKED;
        
    }

}

