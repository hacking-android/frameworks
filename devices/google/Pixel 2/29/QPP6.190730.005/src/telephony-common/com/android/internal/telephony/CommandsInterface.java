/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.annotation.UnsupportedAppUsage
 *  android.net.KeepalivePacketData
 *  android.net.LinkProperties
 *  android.os.Handler
 *  android.os.Message
 *  android.os.WorkSource
 *  android.telephony.CarrierRestrictionRules
 *  android.telephony.ClientRequestStats
 *  android.telephony.ImsiEncryptionInfo
 *  android.telephony.NetworkScanRequest
 *  android.telephony.data.DataProfile
 *  android.telephony.emergency.EmergencyNumber
 *  com.android.internal.telephony.gsm.SmsBroadcastConfigInfo
 */
package com.android.internal.telephony;

import android.annotation.UnsupportedAppUsage;
import android.net.KeepalivePacketData;
import android.net.LinkProperties;
import android.os.Handler;
import android.os.Message;
import android.os.WorkSource;
import android.telephony.CarrierRestrictionRules;
import android.telephony.ClientRequestStats;
import android.telephony.ImsiEncryptionInfo;
import android.telephony.NetworkScanRequest;
import android.telephony.data.DataProfile;
import android.telephony.emergency.EmergencyNumber;
import com.android.internal.telephony.RadioCapability;
import com.android.internal.telephony.UUSInfo;
import com.android.internal.telephony.cdma.CdmaSmsBroadcastConfigInfo;
import com.android.internal.telephony.gsm.SmsBroadcastConfigInfo;
import java.util.List;

public interface CommandsInterface {
    public static final String CB_FACILITY_BAIC = "AI";
    public static final String CB_FACILITY_BAICr = "IR";
    public static final String CB_FACILITY_BAOC = "AO";
    public static final String CB_FACILITY_BAOIC = "OI";
    public static final String CB_FACILITY_BAOICxH = "OX";
    public static final String CB_FACILITY_BA_ALL = "AB";
    public static final String CB_FACILITY_BA_FD = "FD";
    public static final String CB_FACILITY_BA_MO = "AG";
    public static final String CB_FACILITY_BA_MT = "AC";
    public static final String CB_FACILITY_BA_SIM = "SC";
    public static final int CDMA_SMS_FAIL_CAUSE_ENCODING_PROBLEM = 96;
    public static final int CDMA_SMS_FAIL_CAUSE_INVALID_TELESERVICE_ID = 4;
    public static final int CDMA_SMS_FAIL_CAUSE_OTHER_TERMINAL_PROBLEM = 39;
    public static final int CDMA_SMS_FAIL_CAUSE_RESOURCE_SHORTAGE = 35;
    public static final int CF_ACTION_DISABLE = 0;
    public static final int CF_ACTION_ENABLE = 1;
    public static final int CF_ACTION_ERASURE = 4;
    public static final int CF_ACTION_REGISTRATION = 3;
    public static final int CF_REASON_ALL = 4;
    public static final int CF_REASON_ALL_CONDITIONAL = 5;
    public static final int CF_REASON_BUSY = 1;
    public static final int CF_REASON_NOT_REACHABLE = 3;
    public static final int CF_REASON_NO_REPLY = 2;
    public static final int CF_REASON_UNCONDITIONAL = 0;
    public static final int CLIR_DEFAULT = 0;
    public static final int CLIR_INVOCATION = 1;
    public static final int CLIR_SUPPRESSION = 2;
    public static final int GSM_SMS_FAIL_CAUSE_MEMORY_CAPACITY_EXCEEDED = 211;
    public static final int GSM_SMS_FAIL_CAUSE_UNSPECIFIED_ERROR = 255;
    public static final int GSM_SMS_FAIL_CAUSE_USIM_APP_TOOLKIT_BUSY = 212;
    public static final int GSM_SMS_FAIL_CAUSE_USIM_DATA_DOWNLOAD_ERROR = 213;
    public static final int SERVICE_CLASS_DATA = 2;
    public static final int SERVICE_CLASS_DATA_ASYNC = 32;
    public static final int SERVICE_CLASS_DATA_SYNC = 16;
    public static final int SERVICE_CLASS_FAX = 4;
    public static final int SERVICE_CLASS_MAX = 128;
    public static final int SERVICE_CLASS_NONE = 0;
    public static final int SERVICE_CLASS_PACKET = 64;
    public static final int SERVICE_CLASS_PAD = 128;
    public static final int SERVICE_CLASS_SMS = 8;
    public static final int SERVICE_CLASS_VOICE = 1;
    public static final int USSD_MODE_LOCAL_CLIENT = 3;
    public static final int USSD_MODE_NOTIFY = 0;
    public static final int USSD_MODE_NOT_SUPPORTED = 4;
    public static final int USSD_MODE_NW_RELEASE = 2;
    public static final int USSD_MODE_NW_TIMEOUT = 5;
    public static final int USSD_MODE_REQUEST = 1;

    @UnsupportedAppUsage
    public void acceptCall(Message var1);

    public void acknowledgeIncomingGsmSmsWithPdu(boolean var1, String var2, Message var3);

    @UnsupportedAppUsage
    public void acknowledgeLastIncomingCdmaSms(boolean var1, int var2, Message var3);

    @UnsupportedAppUsage
    public void acknowledgeLastIncomingGsmSms(boolean var1, int var2, Message var3);

    public void cancelPendingUssd(Message var1);

    @UnsupportedAppUsage
    public void changeBarringPassword(String var1, String var2, String var3, Message var4);

    public void changeIccPin(String var1, String var2, Message var3);

    public void changeIccPin2(String var1, String var2, Message var3);

    public void changeIccPin2ForApp(String var1, String var2, String var3, Message var4);

    public void changeIccPinForApp(String var1, String var2, String var3, Message var4);

    public void conference(Message var1);

    public void deactivateDataCall(int var1, int var2, Message var3);

    @UnsupportedAppUsage
    public void deleteSmsOnRuim(int var1, Message var2);

    @UnsupportedAppUsage
    public void deleteSmsOnSim(int var1, Message var2);

    public void dial(String var1, boolean var2, EmergencyNumber var3, boolean var4, int var5, Message var6);

    public void dial(String var1, boolean var2, EmergencyNumber var3, boolean var4, int var5, UUSInfo var6, Message var7);

    default public void enableModem(boolean bl, Message message) {
    }

    @UnsupportedAppUsage
    public void exitEmergencyCallbackMode(Message var1);

    public void explicitCallTransfer(Message var1);

    default public void getAllowedCarriers(Message message, WorkSource workSource) {
    }

    public void getAvailableNetworks(Message var1);

    @UnsupportedAppUsage
    public void getBasebandVersion(Message var1);

    @UnsupportedAppUsage
    public void getCDMASubscription(Message var1);

    public void getCLIR(Message var1);

    @UnsupportedAppUsage
    public void getCdmaBroadcastConfig(Message var1);

    public void getCdmaSubscriptionSource(Message var1);

    default public void getCellInfoList(Message message, WorkSource workSource) {
    }

    default public List<ClientRequestStats> getClientRequestStats() {
        return null;
    }

    public void getCurrentCalls(Message var1);

    @UnsupportedAppUsage
    public void getDataCallList(Message var1);

    public void getDataRegistrationState(Message var1);

    public void getDeviceIdentity(Message var1);

    public void getGsmBroadcastConfig(Message var1);

    public void getHardwareConfig(Message var1);

    public void getIMEI(Message var1);

    @UnsupportedAppUsage
    public void getIMEISV(Message var1);

    @UnsupportedAppUsage
    public void getIMSI(Message var1);

    public void getIMSIForApp(String var1, Message var2);

    @UnsupportedAppUsage
    public void getIccCardStatus(Message var1);

    public void getIccSlotsStatus(Message var1);

    public void getImsRegistrationState(Message var1);

    public void getLastCallFailCause(Message var1);

    @UnsupportedAppUsage
    public void getLastDataCallFailCause(Message var1);

    @Deprecated
    @UnsupportedAppUsage
    public void getLastPdpFailCause(Message var1);

    public int getLteOnCdmaMode();

    default public void getModemActivityInfo(Message message, WorkSource workSource) {
    }

    default public void getModemStatus(Message message) {
    }

    public void getMute(Message var1);

    @UnsupportedAppUsage
    public void getNetworkSelectionMode(Message var1);

    @UnsupportedAppUsage
    public void getOperator(Message var1);

    @Deprecated
    @UnsupportedAppUsage
    public void getPDPContextList(Message var1);

    @UnsupportedAppUsage
    public void getPreferredNetworkType(Message var1);

    public void getPreferredVoicePrivacy(Message var1);

    public void getRadioCapability(Message var1);

    public int getRadioState();

    public int getRilVersion();

    @UnsupportedAppUsage
    public void getSignalStrength(Message var1);

    @UnsupportedAppUsage
    public void getSmscAddress(Message var1);

    public void getVoiceRadioTechnology(Message var1);

    @UnsupportedAppUsage
    public void getVoiceRegistrationState(Message var1);

    @UnsupportedAppUsage
    public void handleCallSetupRequestFromSim(boolean var1, Message var2);

    public void hangupConnection(int var1, Message var2);

    public void hangupForegroundResumeBackground(Message var1);

    public void hangupWaitingOrBackground(Message var1);

    public void iccCloseLogicalChannel(int var1, Message var2);

    @UnsupportedAppUsage
    public void iccIO(int var1, int var2, String var3, int var4, int var5, int var6, String var7, String var8, Message var9);

    @UnsupportedAppUsage
    public void iccIOForApp(int var1, int var2, String var3, int var4, int var5, int var6, String var7, String var8, String var9, Message var10);

    public void iccOpenLogicalChannel(String var1, int var2, Message var3);

    public void iccTransmitApduBasicChannel(int var1, int var2, int var3, int var4, int var5, String var6, Message var7);

    public void iccTransmitApduLogicalChannel(int var1, int var2, int var3, int var4, int var5, int var6, String var7, Message var8);

    @UnsupportedAppUsage
    public void invokeOemRilRequestRaw(byte[] var1, Message var2);

    public void invokeOemRilRequestStrings(String[] var1, Message var2);

    default public void nvReadItem(int n, Message message, WorkSource workSource) {
    }

    public void nvResetConfig(int var1, Message var2);

    public void nvWriteCdmaPrl(byte[] var1, Message var2);

    default public void nvWriteItem(int n, String string, Message message, WorkSource workSource) {
    }

    public void pullLceData(Message var1);

    public void queryAvailableBandMode(Message var1);

    public void queryCLIP(Message var1);

    @UnsupportedAppUsage
    public void queryCallForwardStatus(int var1, int var2, String var3, Message var4);

    @UnsupportedAppUsage
    public void queryCallWaiting(int var1, Message var2);

    public void queryCdmaRoamingPreference(Message var1);

    @UnsupportedAppUsage
    public void queryFacilityLock(String var1, String var2, int var3, Message var4);

    public void queryFacilityLockForApp(String var1, String var2, int var3, String var4, Message var5);

    @UnsupportedAppUsage
    public void queryTTYMode(Message var1);

    public void registerFoT53ClirlInfo(Handler var1, int var2, Object var3);

    @UnsupportedAppUsage
    public void registerForAvailable(Handler var1, int var2, Object var3);

    public void registerForCallStateChanged(Handler var1, int var2, Object var3);

    public void registerForCallWaitingInfo(Handler var1, int var2, Object var3);

    public void registerForCarrierInfoForImsiEncryption(Handler var1, int var2, Object var3);

    @UnsupportedAppUsage
    public void registerForCdmaOtaProvision(Handler var1, int var2, Object var3);

    public void registerForCdmaPrlChanged(Handler var1, int var2, Object var3);

    public void registerForCdmaSubscriptionChanged(Handler var1, int var2, Object var3);

    @UnsupportedAppUsage
    public void registerForCellInfoList(Handler var1, int var2, Object var3);

    public void registerForDataCallListChanged(Handler var1, int var2, Object var3);

    public void registerForDisplayInfo(Handler var1, int var2, Object var3);

    public void registerForEmergencyNumberList(Handler var1, int var2, Object var3);

    public void registerForExitEmergencyCallbackMode(Handler var1, int var2, Object var3);

    public void registerForHardwareConfigChanged(Handler var1, int var2, Object var3);

    @UnsupportedAppUsage
    public void registerForIccRefresh(Handler var1, int var2, Object var3);

    public void registerForIccSlotStatusChanged(Handler var1, int var2, Object var3);

    public void registerForIccStatusChanged(Handler var1, int var2, Object var3);

    @UnsupportedAppUsage
    public void registerForImsNetworkStateChanged(Handler var1, int var2, Object var3);

    public void registerForInCallVoicePrivacyOff(Handler var1, int var2, Object var3);

    public void registerForInCallVoicePrivacyOn(Handler var1, int var2, Object var3);

    public void registerForLceInfo(Handler var1, int var2, Object var3);

    public void registerForLineControlInfo(Handler var1, int var2, Object var3);

    public void registerForModemReset(Handler var1, int var2, Object var3);

    public void registerForNattKeepaliveStatus(Handler var1, int var2, Object var3);

    public void registerForNetworkScanResult(Handler var1, int var2, Object var3);

    public void registerForNetworkStateChanged(Handler var1, int var2, Object var3);

    @UnsupportedAppUsage
    public void registerForNotAvailable(Handler var1, int var2, Object var3);

    public void registerForNumberInfo(Handler var1, int var2, Object var3);

    @UnsupportedAppUsage
    public void registerForOffOrNotAvailable(Handler var1, int var2, Object var3);

    @UnsupportedAppUsage
    public void registerForOn(Handler var1, int var2, Object var3);

    public void registerForPcoData(Handler var1, int var2, Object var3);

    public void registerForPhysicalChannelConfiguration(Handler var1, int var2, Object var3);

    public void registerForRadioCapabilityChanged(Handler var1, int var2, Object var3);

    @UnsupportedAppUsage
    public void registerForRadioStateChanged(Handler var1, int var2, Object var3);

    public void registerForRedirectedNumberInfo(Handler var1, int var2, Object var3);

    public void registerForResendIncallMute(Handler var1, int var2, Object var3);

    @UnsupportedAppUsage
    public void registerForRilConnected(Handler var1, int var2, Object var3);

    public void registerForRingbackTone(Handler var1, int var2, Object var3);

    public void registerForSignalInfo(Handler var1, int var2, Object var3);

    public void registerForSrvccStateChanged(Handler var1, int var2, Object var3);

    public void registerForSubscriptionStatusChanged(Handler var1, int var2, Object var3);

    public void registerForT53AudioControlInfo(Handler var1, int var2, Object var3);

    public void registerForVoiceRadioTechChanged(Handler var1, int var2, Object var3);

    public void rejectCall(Message var1);

    @UnsupportedAppUsage
    public void reportSmsMemoryStatus(boolean var1, Message var2);

    @UnsupportedAppUsage
    public void reportStkServiceIsRunning(Message var1);

    @UnsupportedAppUsage
    public void requestIccSimAuthentication(int var1, String var2, String var3, Message var4);

    @UnsupportedAppUsage
    public void requestShutdown(Message var1);

    public void resetRadio(Message var1);

    public void sendBurstDtmf(String var1, int var2, int var3, Message var4);

    public void sendCDMAFeatureCode(String var1, Message var2);

    public void sendCdmaSms(byte[] var1, Message var2);

    public void sendDeviceState(int var1, boolean var2, Message var3);

    @UnsupportedAppUsage
    public void sendDtmf(char var1, Message var2);

    @UnsupportedAppUsage
    public void sendEnvelope(String var1, Message var2);

    public void sendEnvelopeWithStatus(String var1, Message var2);

    public void sendImsCdmaSms(byte[] var1, int var2, int var3, Message var4);

    public void sendImsGsmSms(String var1, String var2, int var3, int var4, Message var5);

    public void sendSMS(String var1, String var2, Message var3);

    public void sendSMSExpectMore(String var1, String var2, Message var3);

    @UnsupportedAppUsage
    public void sendTerminalResponse(String var1, Message var2);

    public void sendUSSD(String var1, Message var2);

    public void separateConnection(int var1, Message var2);

    default public void setAllowedCarriers(CarrierRestrictionRules carrierRestrictionRules, Message message, WorkSource workSource) {
    }

    public void setBandMode(int var1, Message var2);

    public void setCLIR(int var1, Message var2);

    @UnsupportedAppUsage
    public void setCallForward(int var1, int var2, int var3, String var4, int var5, Message var6);

    @UnsupportedAppUsage
    public void setCallWaiting(boolean var1, int var2, Message var3);

    public void setCarrierInfoForImsiEncryption(ImsiEncryptionInfo var1, Message var2);

    @UnsupportedAppUsage
    public void setCdmaBroadcastActivation(boolean var1, Message var2);

    public void setCdmaBroadcastConfig(CdmaSmsBroadcastConfigInfo[] var1, Message var2);

    public void setCdmaRoamingPreference(int var1, Message var2);

    public void setCdmaSubscriptionSource(int var1, Message var2);

    default public void setCellInfoListRate(int n, Message message, WorkSource workSource) {
    }

    @UnsupportedAppUsage
    public void setDataAllowed(boolean var1, Message var2);

    public void setDataProfile(DataProfile[] var1, boolean var2, Message var3);

    @UnsupportedAppUsage
    public void setEmergencyCallbackMode(Handler var1, int var2, Object var3);

    @UnsupportedAppUsage
    public void setFacilityLock(String var1, boolean var2, String var3, int var4, Message var5);

    public void setFacilityLockForApp(String var1, boolean var2, String var3, int var4, String var5, Message var6);

    public void setGsmBroadcastActivation(boolean var1, Message var2);

    public void setGsmBroadcastConfig(SmsBroadcastConfigInfo[] var1, Message var2);

    public void setInitialAttachApn(DataProfile var1, boolean var2, Message var3);

    public void setLinkCapacityReportingCriteria(int var1, int var2, int var3, int[] var4, int[] var5, int var6, Message var7);

    public void setLocationUpdates(boolean var1, Message var2);

    public void setLogicalToPhysicalSlotMapping(int[] var1, Message var2);

    public void setMute(boolean var1, Message var2);

    @UnsupportedAppUsage
    public void setNetworkSelectionModeAutomatic(Message var1);

    @UnsupportedAppUsage
    public void setNetworkSelectionModeManual(String var1, Message var2);

    @UnsupportedAppUsage
    public void setOnCallRing(Handler var1, int var2, Object var3);

    @UnsupportedAppUsage
    public void setOnCatCallSetUp(Handler var1, int var2, Object var3);

    @UnsupportedAppUsage
    public void setOnCatCcAlphaNotify(Handler var1, int var2, Object var3);

    @UnsupportedAppUsage
    public void setOnCatEvent(Handler var1, int var2, Object var3);

    @UnsupportedAppUsage
    public void setOnCatProactiveCmd(Handler var1, int var2, Object var3);

    @UnsupportedAppUsage
    public void setOnCatSessionEnd(Handler var1, int var2, Object var3);

    @UnsupportedAppUsage
    public void setOnIccRefresh(Handler var1, int var2, Object var3);

    @UnsupportedAppUsage
    public void setOnIccSmsFull(Handler var1, int var2, Object var3);

    @UnsupportedAppUsage
    public void setOnNITZTime(Handler var1, int var2, Object var3);

    public void setOnNewCdmaSms(Handler var1, int var2, Object var3);

    @UnsupportedAppUsage
    public void setOnNewGsmBroadcastSms(Handler var1, int var2, Object var3);

    public void setOnNewGsmSms(Handler var1, int var2, Object var3);

    public void setOnRestrictedStateChanged(Handler var1, int var2, Object var3);

    @UnsupportedAppUsage
    public void setOnSignalStrengthUpdate(Handler var1, int var2, Object var3);

    @UnsupportedAppUsage
    public void setOnSmsOnSim(Handler var1, int var2, Object var3);

    @UnsupportedAppUsage
    public void setOnSmsStatus(Handler var1, int var2, Object var3);

    public void setOnSs(Handler var1, int var2, Object var3);

    @UnsupportedAppUsage
    public void setOnSuppServiceNotification(Handler var1, int var2, Object var3);

    public void setOnUSSD(Handler var1, int var2, Object var3);

    public void setOnUnsolOemHookRaw(Handler var1, int var2, Object var3);

    @UnsupportedAppUsage
    public void setPhoneType(int var1);

    @UnsupportedAppUsage
    public void setPreferredNetworkType(int var1, Message var2);

    public void setPreferredVoicePrivacy(boolean var1, Message var2);

    public void setRadioCapability(RadioCapability var1, Message var2);

    @UnsupportedAppUsage
    public void setRadioPower(boolean var1, Message var2);

    public void setSignalStrengthReportingCriteria(int var1, int var2, int[] var3, int var4, Message var5);

    default public void setSimCardPower(int n, Message message, WorkSource workSource) {
    }

    @UnsupportedAppUsage
    public void setSmscAddress(String var1, Message var2);

    public void setSuppServiceNotifications(boolean var1, Message var2);

    @UnsupportedAppUsage
    public void setTTYMode(int var1, Message var2);

    @UnsupportedAppUsage
    public void setUiccSubscription(int var1, int var2, int var3, int var4, Message var5);

    public void setUnsolResponseFilter(int var1, Message var2);

    public void setupDataCall(int var1, DataProfile var2, boolean var3, boolean var4, int var5, LinkProperties var6, Message var7);

    public void startDtmf(char var1, Message var2);

    public void startLceService(int var1, boolean var2, Message var3);

    public void startNattKeepalive(int var1, KeepalivePacketData var2, int var3, Message var4);

    public void startNetworkScan(NetworkScanRequest var1, Message var2);

    public void stopDtmf(Message var1);

    public void stopLceService(Message var1);

    public void stopNattKeepalive(int var1, Message var2);

    public void stopNetworkScan(Message var1);

    @UnsupportedAppUsage
    public void supplyIccPin(String var1, Message var2);

    public void supplyIccPin2(String var1, Message var2);

    public void supplyIccPin2ForApp(String var1, String var2, Message var3);

    public void supplyIccPinForApp(String var1, String var2, Message var3);

    public void supplyIccPuk(String var1, String var2, Message var3);

    public void supplyIccPuk2(String var1, String var2, Message var3);

    public void supplyIccPuk2ForApp(String var1, String var2, String var3, Message var4);

    public void supplyIccPukForApp(String var1, String var2, String var3, Message var4);

    public void supplyNetworkDepersonalization(String var1, Message var2);

    @UnsupportedAppUsage
    public void switchWaitingOrHoldingAndActive(Message var1);

    public void testingEmergencyCall();

    public void unSetOnCallRing(Handler var1);

    public void unSetOnCatCallSetUp(Handler var1);

    public void unSetOnCatCcAlphaNotify(Handler var1);

    public void unSetOnCatEvent(Handler var1);

    public void unSetOnCatProactiveCmd(Handler var1);

    public void unSetOnCatSessionEnd(Handler var1);

    public void unSetOnIccSmsFull(Handler var1);

    public void unSetOnNITZTime(Handler var1);

    public void unSetOnNewCdmaSms(Handler var1);

    public void unSetOnNewGsmBroadcastSms(Handler var1);

    public void unSetOnNewGsmSms(Handler var1);

    public void unSetOnRestrictedStateChanged(Handler var1);

    public void unSetOnSignalStrengthUpdate(Handler var1);

    public void unSetOnSmsOnSim(Handler var1);

    public void unSetOnSmsStatus(Handler var1);

    public void unSetOnSs(Handler var1);

    public void unSetOnSuppServiceNotification(Handler var1);

    public void unSetOnUSSD(Handler var1);

    public void unSetOnUnsolOemHookRaw(Handler var1);

    @UnsupportedAppUsage
    public void unregisterForAvailable(Handler var1);

    public void unregisterForCallStateChanged(Handler var1);

    public void unregisterForCallWaitingInfo(Handler var1);

    public void unregisterForCarrierInfoForImsiEncryption(Handler var1);

    @UnsupportedAppUsage
    public void unregisterForCdmaOtaProvision(Handler var1);

    public void unregisterForCdmaPrlChanged(Handler var1);

    public void unregisterForCdmaSubscriptionChanged(Handler var1);

    public void unregisterForCellInfoList(Handler var1);

    public void unregisterForDataCallListChanged(Handler var1);

    public void unregisterForDisplayInfo(Handler var1);

    public void unregisterForEmergencyNumberList(Handler var1);

    public void unregisterForExitEmergencyCallbackMode(Handler var1);

    public void unregisterForHardwareConfigChanged(Handler var1);

    public void unregisterForIccRefresh(Handler var1);

    public void unregisterForIccSlotStatusChanged(Handler var1);

    public void unregisterForIccStatusChanged(Handler var1);

    public void unregisterForImsNetworkStateChanged(Handler var1);

    public void unregisterForInCallVoicePrivacyOff(Handler var1);

    public void unregisterForInCallVoicePrivacyOn(Handler var1);

    public void unregisterForLceInfo(Handler var1);

    public void unregisterForLineControlInfo(Handler var1);

    public void unregisterForModemReset(Handler var1);

    public void unregisterForNattKeepaliveStatus(Handler var1);

    public void unregisterForNetworkScanResult(Handler var1);

    public void unregisterForNetworkStateChanged(Handler var1);

    public void unregisterForNotAvailable(Handler var1);

    public void unregisterForNumberInfo(Handler var1);

    @UnsupportedAppUsage
    public void unregisterForOffOrNotAvailable(Handler var1);

    @UnsupportedAppUsage
    public void unregisterForOn(Handler var1);

    public void unregisterForPcoData(Handler var1);

    public void unregisterForPhysicalChannelConfiguration(Handler var1);

    public void unregisterForRadioCapabilityChanged(Handler var1);

    public void unregisterForRadioStateChanged(Handler var1);

    public void unregisterForRedirectedNumberInfo(Handler var1);

    public void unregisterForResendIncallMute(Handler var1);

    @UnsupportedAppUsage
    public void unregisterForRilConnected(Handler var1);

    public void unregisterForRingbackTone(Handler var1);

    public void unregisterForSignalInfo(Handler var1);

    public void unregisterForSrvccStateChanged(Handler var1);

    public void unregisterForSubscriptionStatusChanged(Handler var1);

    public void unregisterForT53AudioControlInfo(Handler var1);

    public void unregisterForT53ClirInfo(Handler var1);

    @UnsupportedAppUsage
    public void unregisterForVoiceRadioTechChanged(Handler var1);

    public void unsetOnIccRefresh(Handler var1);

    @UnsupportedAppUsage
    public void writeSmsToRuim(int var1, String var2, Message var3);

    @UnsupportedAppUsage
    public void writeSmsToSim(int var1, String var2, String var3, Message var4);
}

