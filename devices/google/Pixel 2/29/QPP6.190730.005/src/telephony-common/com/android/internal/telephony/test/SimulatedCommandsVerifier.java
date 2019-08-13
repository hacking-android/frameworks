/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.net.KeepalivePacketData
 *  android.net.LinkProperties
 *  android.os.Handler
 *  android.os.Message
 *  android.telephony.ImsiEncryptionInfo
 *  android.telephony.NetworkScanRequest
 *  android.telephony.data.DataProfile
 *  android.telephony.emergency.EmergencyNumber
 *  com.android.internal.telephony.gsm.SmsBroadcastConfigInfo
 */
package com.android.internal.telephony.test;

import android.net.KeepalivePacketData;
import android.net.LinkProperties;
import android.os.Handler;
import android.os.Message;
import android.telephony.ImsiEncryptionInfo;
import android.telephony.NetworkScanRequest;
import android.telephony.data.DataProfile;
import android.telephony.emergency.EmergencyNumber;
import com.android.internal.telephony.CommandsInterface;
import com.android.internal.telephony.RadioCapability;
import com.android.internal.telephony.UUSInfo;
import com.android.internal.telephony.cdma.CdmaSmsBroadcastConfigInfo;
import com.android.internal.telephony.gsm.SmsBroadcastConfigInfo;

public class SimulatedCommandsVerifier
implements CommandsInterface {
    private static SimulatedCommandsVerifier sInstance;

    private SimulatedCommandsVerifier() {
    }

    public static SimulatedCommandsVerifier getInstance() {
        if (sInstance == null) {
            sInstance = new SimulatedCommandsVerifier();
        }
        return sInstance;
    }

    @Override
    public void acceptCall(Message message) {
    }

    @Override
    public void acknowledgeIncomingGsmSmsWithPdu(boolean bl, String string, Message message) {
    }

    @Override
    public void acknowledgeLastIncomingCdmaSms(boolean bl, int n, Message message) {
    }

    @Override
    public void acknowledgeLastIncomingGsmSms(boolean bl, int n, Message message) {
    }

    @Override
    public void cancelPendingUssd(Message message) {
    }

    @Override
    public void changeBarringPassword(String string, String string2, String string3, Message message) {
    }

    @Override
    public void changeIccPin(String string, String string2, Message message) {
    }

    @Override
    public void changeIccPin2(String string, String string2, Message message) {
    }

    @Override
    public void changeIccPin2ForApp(String string, String string2, String string3, Message message) {
    }

    @Override
    public void changeIccPinForApp(String string, String string2, String string3, Message message) {
    }

    @Override
    public void conference(Message message) {
    }

    @Override
    public void deactivateDataCall(int n, int n2, Message message) {
    }

    @Override
    public void deleteSmsOnRuim(int n, Message message) {
    }

    @Override
    public void deleteSmsOnSim(int n, Message message) {
    }

    @Override
    public void dial(String string, boolean bl, EmergencyNumber emergencyNumber, boolean bl2, int n, Message message) {
    }

    @Override
    public void dial(String string, boolean bl, EmergencyNumber emergencyNumber, boolean bl2, int n, UUSInfo uUSInfo, Message message) {
    }

    @Override
    public void exitEmergencyCallbackMode(Message message) {
    }

    @Override
    public void explicitCallTransfer(Message message) {
    }

    @Override
    public void getAvailableNetworks(Message message) {
    }

    @Override
    public void getBasebandVersion(Message message) {
    }

    @Override
    public void getCDMASubscription(Message message) {
    }

    @Override
    public void getCLIR(Message message) {
    }

    @Override
    public void getCdmaBroadcastConfig(Message message) {
    }

    @Override
    public void getCdmaSubscriptionSource(Message message) {
    }

    @Override
    public void getCurrentCalls(Message message) {
    }

    @Override
    public void getDataCallList(Message message) {
    }

    @Override
    public void getDataRegistrationState(Message message) {
    }

    @Override
    public void getDeviceIdentity(Message message) {
    }

    @Override
    public void getGsmBroadcastConfig(Message message) {
    }

    @Override
    public void getHardwareConfig(Message message) {
    }

    @Override
    public void getIMEI(Message message) {
    }

    @Override
    public void getIMEISV(Message message) {
    }

    @Override
    public void getIMSI(Message message) {
    }

    @Override
    public void getIMSIForApp(String string, Message message) {
    }

    @Override
    public void getIccCardStatus(Message message) {
    }

    @Override
    public void getIccSlotsStatus(Message message) {
    }

    @Override
    public void getImsRegistrationState(Message message) {
    }

    @Override
    public void getLastCallFailCause(Message message) {
    }

    @Override
    public void getLastDataCallFailCause(Message message) {
    }

    @Override
    public void getLastPdpFailCause(Message message) {
    }

    @Override
    public int getLteOnCdmaMode() {
        return 0;
    }

    @Override
    public void getMute(Message message) {
    }

    @Override
    public void getNetworkSelectionMode(Message message) {
    }

    @Override
    public void getOperator(Message message) {
    }

    @Override
    public void getPDPContextList(Message message) {
    }

    @Override
    public void getPreferredNetworkType(Message message) {
    }

    @Override
    public void getPreferredVoicePrivacy(Message message) {
    }

    @Override
    public void getRadioCapability(Message message) {
    }

    @Override
    public int getRadioState() {
        return 2;
    }

    @Override
    public int getRilVersion() {
        return 0;
    }

    @Override
    public void getSignalStrength(Message message) {
    }

    @Override
    public void getSmscAddress(Message message) {
    }

    @Override
    public void getVoiceRadioTechnology(Message message) {
    }

    @Override
    public void getVoiceRegistrationState(Message message) {
    }

    @Override
    public void handleCallSetupRequestFromSim(boolean bl, Message message) {
    }

    @Override
    public void hangupConnection(int n, Message message) {
    }

    @Override
    public void hangupForegroundResumeBackground(Message message) {
    }

    @Override
    public void hangupWaitingOrBackground(Message message) {
    }

    @Override
    public void iccCloseLogicalChannel(int n, Message message) {
    }

    @Override
    public void iccIO(int n, int n2, String string, int n3, int n4, int n5, String string2, String string3, Message message) {
    }

    @Override
    public void iccIOForApp(int n, int n2, String string, int n3, int n4, int n5, String string2, String string3, String string4, Message message) {
    }

    @Override
    public void iccOpenLogicalChannel(String string, int n, Message message) {
    }

    @Override
    public void iccTransmitApduBasicChannel(int n, int n2, int n3, int n4, int n5, String string, Message message) {
    }

    @Override
    public void iccTransmitApduLogicalChannel(int n, int n2, int n3, int n4, int n5, int n6, String string, Message message) {
    }

    @Override
    public void invokeOemRilRequestRaw(byte[] arrby, Message message) {
    }

    @Override
    public void invokeOemRilRequestStrings(String[] arrstring, Message message) {
    }

    @Override
    public void nvResetConfig(int n, Message message) {
    }

    @Override
    public void nvWriteCdmaPrl(byte[] arrby, Message message) {
    }

    @Override
    public void pullLceData(Message message) {
    }

    @Override
    public void queryAvailableBandMode(Message message) {
    }

    @Override
    public void queryCLIP(Message message) {
    }

    @Override
    public void queryCallForwardStatus(int n, int n2, String string, Message message) {
    }

    @Override
    public void queryCallWaiting(int n, Message message) {
    }

    @Override
    public void queryCdmaRoamingPreference(Message message) {
    }

    @Override
    public void queryFacilityLock(String string, String string2, int n, Message message) {
    }

    @Override
    public void queryFacilityLockForApp(String string, String string2, int n, String string3, Message message) {
    }

    @Override
    public void queryTTYMode(Message message) {
    }

    @Override
    public void registerFoT53ClirlInfo(Handler handler, int n, Object object) {
    }

    @Override
    public void registerForAvailable(Handler handler, int n, Object object) {
    }

    @Override
    public void registerForCallStateChanged(Handler handler, int n, Object object) {
    }

    @Override
    public void registerForCallWaitingInfo(Handler handler, int n, Object object) {
    }

    @Override
    public void registerForCarrierInfoForImsiEncryption(Handler handler, int n, Object object) {
    }

    @Override
    public void registerForCdmaOtaProvision(Handler handler, int n, Object object) {
    }

    @Override
    public void registerForCdmaPrlChanged(Handler handler, int n, Object object) {
    }

    @Override
    public void registerForCdmaSubscriptionChanged(Handler handler, int n, Object object) {
    }

    @Override
    public void registerForCellInfoList(Handler handler, int n, Object object) {
    }

    @Override
    public void registerForDataCallListChanged(Handler handler, int n, Object object) {
    }

    @Override
    public void registerForDisplayInfo(Handler handler, int n, Object object) {
    }

    @Override
    public void registerForEmergencyNumberList(Handler handler, int n, Object object) {
    }

    @Override
    public void registerForExitEmergencyCallbackMode(Handler handler, int n, Object object) {
    }

    @Override
    public void registerForHardwareConfigChanged(Handler handler, int n, Object object) {
    }

    @Override
    public void registerForIccRefresh(Handler handler, int n, Object object) {
    }

    @Override
    public void registerForIccSlotStatusChanged(Handler handler, int n, Object object) {
    }

    @Override
    public void registerForIccStatusChanged(Handler handler, int n, Object object) {
    }

    @Override
    public void registerForImsNetworkStateChanged(Handler handler, int n, Object object) {
    }

    @Override
    public void registerForInCallVoicePrivacyOff(Handler handler, int n, Object object) {
    }

    @Override
    public void registerForInCallVoicePrivacyOn(Handler handler, int n, Object object) {
    }

    @Override
    public void registerForLceInfo(Handler handler, int n, Object object) {
    }

    @Override
    public void registerForLineControlInfo(Handler handler, int n, Object object) {
    }

    @Override
    public void registerForModemReset(Handler handler, int n, Object object) {
    }

    @Override
    public void registerForNattKeepaliveStatus(Handler handler, int n, Object object) {
    }

    @Override
    public void registerForNetworkScanResult(Handler handler, int n, Object object) {
    }

    @Override
    public void registerForNetworkStateChanged(Handler handler, int n, Object object) {
    }

    @Override
    public void registerForNotAvailable(Handler handler, int n, Object object) {
    }

    @Override
    public void registerForNumberInfo(Handler handler, int n, Object object) {
    }

    @Override
    public void registerForOffOrNotAvailable(Handler handler, int n, Object object) {
    }

    @Override
    public void registerForOn(Handler handler, int n, Object object) {
    }

    @Override
    public void registerForPcoData(Handler handler, int n, Object object) {
    }

    @Override
    public void registerForPhysicalChannelConfiguration(Handler handler, int n, Object object) {
    }

    @Override
    public void registerForRadioCapabilityChanged(Handler handler, int n, Object object) {
    }

    @Override
    public void registerForRadioStateChanged(Handler handler, int n, Object object) {
    }

    @Override
    public void registerForRedirectedNumberInfo(Handler handler, int n, Object object) {
    }

    @Override
    public void registerForResendIncallMute(Handler handler, int n, Object object) {
    }

    @Override
    public void registerForRilConnected(Handler handler, int n, Object object) {
    }

    @Override
    public void registerForRingbackTone(Handler handler, int n, Object object) {
    }

    @Override
    public void registerForSignalInfo(Handler handler, int n, Object object) {
    }

    @Override
    public void registerForSrvccStateChanged(Handler handler, int n, Object object) {
    }

    @Override
    public void registerForSubscriptionStatusChanged(Handler handler, int n, Object object) {
    }

    @Override
    public void registerForT53AudioControlInfo(Handler handler, int n, Object object) {
    }

    @Override
    public void registerForVoiceRadioTechChanged(Handler handler, int n, Object object) {
    }

    @Override
    public void rejectCall(Message message) {
    }

    @Override
    public void reportSmsMemoryStatus(boolean bl, Message message) {
    }

    @Override
    public void reportStkServiceIsRunning(Message message) {
    }

    @Override
    public void requestIccSimAuthentication(int n, String string, String string2, Message message) {
    }

    @Override
    public void requestShutdown(Message message) {
    }

    @Override
    public void resetRadio(Message message) {
    }

    @Override
    public void sendBurstDtmf(String string, int n, int n2, Message message) {
    }

    @Override
    public void sendCDMAFeatureCode(String string, Message message) {
    }

    @Override
    public void sendCdmaSms(byte[] arrby, Message message) {
    }

    @Override
    public void sendDeviceState(int n, boolean bl, Message message) {
    }

    @Override
    public void sendDtmf(char c, Message message) {
    }

    @Override
    public void sendEnvelope(String string, Message message) {
    }

    @Override
    public void sendEnvelopeWithStatus(String string, Message message) {
    }

    @Override
    public void sendImsCdmaSms(byte[] arrby, int n, int n2, Message message) {
    }

    @Override
    public void sendImsGsmSms(String string, String string2, int n, int n2, Message message) {
    }

    @Override
    public void sendSMS(String string, String string2, Message message) {
    }

    @Override
    public void sendSMSExpectMore(String string, String string2, Message message) {
    }

    @Override
    public void sendTerminalResponse(String string, Message message) {
    }

    @Override
    public void sendUSSD(String string, Message message) {
    }

    @Override
    public void separateConnection(int n, Message message) {
    }

    @Override
    public void setBandMode(int n, Message message) {
    }

    @Override
    public void setCLIR(int n, Message message) {
    }

    @Override
    public void setCallForward(int n, int n2, int n3, String string, int n4, Message message) {
    }

    @Override
    public void setCallWaiting(boolean bl, int n, Message message) {
    }

    @Override
    public void setCarrierInfoForImsiEncryption(ImsiEncryptionInfo imsiEncryptionInfo, Message message) {
    }

    @Override
    public void setCdmaBroadcastActivation(boolean bl, Message message) {
    }

    @Override
    public void setCdmaBroadcastConfig(CdmaSmsBroadcastConfigInfo[] arrcdmaSmsBroadcastConfigInfo, Message message) {
    }

    @Override
    public void setCdmaRoamingPreference(int n, Message message) {
    }

    @Override
    public void setCdmaSubscriptionSource(int n, Message message) {
    }

    @Override
    public void setDataAllowed(boolean bl, Message message) {
    }

    @Override
    public void setDataProfile(DataProfile[] arrdataProfile, boolean bl, Message message) {
    }

    @Override
    public void setEmergencyCallbackMode(Handler handler, int n, Object object) {
    }

    @Override
    public void setFacilityLock(String string, boolean bl, String string2, int n, Message message) {
    }

    @Override
    public void setFacilityLockForApp(String string, boolean bl, String string2, int n, String string3, Message message) {
    }

    @Override
    public void setGsmBroadcastActivation(boolean bl, Message message) {
    }

    @Override
    public void setGsmBroadcastConfig(SmsBroadcastConfigInfo[] arrsmsBroadcastConfigInfo, Message message) {
    }

    @Override
    public void setInitialAttachApn(DataProfile dataProfile, boolean bl, Message message) {
    }

    @Override
    public void setLinkCapacityReportingCriteria(int n, int n2, int n3, int[] arrn, int[] arrn2, int n4, Message message) {
    }

    @Override
    public void setLocationUpdates(boolean bl, Message message) {
    }

    @Override
    public void setLogicalToPhysicalSlotMapping(int[] arrn, Message message) {
    }

    @Override
    public void setMute(boolean bl, Message message) {
    }

    @Override
    public void setNetworkSelectionModeAutomatic(Message message) {
    }

    @Override
    public void setNetworkSelectionModeManual(String string, Message message) {
    }

    @Override
    public void setOnCallRing(Handler handler, int n, Object object) {
    }

    @Override
    public void setOnCatCallSetUp(Handler handler, int n, Object object) {
    }

    @Override
    public void setOnCatCcAlphaNotify(Handler handler, int n, Object object) {
    }

    @Override
    public void setOnCatEvent(Handler handler, int n, Object object) {
    }

    @Override
    public void setOnCatProactiveCmd(Handler handler, int n, Object object) {
    }

    @Override
    public void setOnCatSessionEnd(Handler handler, int n, Object object) {
    }

    @Override
    public void setOnIccRefresh(Handler handler, int n, Object object) {
    }

    @Override
    public void setOnIccSmsFull(Handler handler, int n, Object object) {
    }

    @Override
    public void setOnNITZTime(Handler handler, int n, Object object) {
    }

    @Override
    public void setOnNewCdmaSms(Handler handler, int n, Object object) {
    }

    @Override
    public void setOnNewGsmBroadcastSms(Handler handler, int n, Object object) {
    }

    @Override
    public void setOnNewGsmSms(Handler handler, int n, Object object) {
    }

    @Override
    public void setOnRestrictedStateChanged(Handler handler, int n, Object object) {
    }

    @Override
    public void setOnSignalStrengthUpdate(Handler handler, int n, Object object) {
    }

    @Override
    public void setOnSmsOnSim(Handler handler, int n, Object object) {
    }

    @Override
    public void setOnSmsStatus(Handler handler, int n, Object object) {
    }

    @Override
    public void setOnSs(Handler handler, int n, Object object) {
    }

    @Override
    public void setOnSuppServiceNotification(Handler handler, int n, Object object) {
    }

    @Override
    public void setOnUSSD(Handler handler, int n, Object object) {
    }

    @Override
    public void setOnUnsolOemHookRaw(Handler handler, int n, Object object) {
    }

    @Override
    public void setPhoneType(int n) {
    }

    @Override
    public void setPreferredNetworkType(int n, Message message) {
    }

    @Override
    public void setPreferredVoicePrivacy(boolean bl, Message message) {
    }

    @Override
    public void setRadioCapability(RadioCapability radioCapability, Message message) {
    }

    @Override
    public void setRadioPower(boolean bl, Message message) {
    }

    @Override
    public void setSignalStrengthReportingCriteria(int n, int n2, int[] arrn, int n3, Message message) {
    }

    @Override
    public void setSmscAddress(String string, Message message) {
    }

    @Override
    public void setSuppServiceNotifications(boolean bl, Message message) {
    }

    @Override
    public void setTTYMode(int n, Message message) {
    }

    @Override
    public void setUiccSubscription(int n, int n2, int n3, int n4, Message message) {
    }

    @Override
    public void setUnsolResponseFilter(int n, Message message) {
    }

    @Override
    public void setupDataCall(int n, DataProfile dataProfile, boolean bl, boolean bl2, int n2, LinkProperties linkProperties, Message message) {
    }

    @Override
    public void startDtmf(char c, Message message) {
    }

    @Override
    public void startLceService(int n, boolean bl, Message message) {
    }

    @Override
    public void startNattKeepalive(int n, KeepalivePacketData keepalivePacketData, int n2, Message message) {
    }

    @Override
    public void startNetworkScan(NetworkScanRequest networkScanRequest, Message message) {
    }

    @Override
    public void stopDtmf(Message message) {
    }

    @Override
    public void stopLceService(Message message) {
    }

    @Override
    public void stopNattKeepalive(int n, Message message) {
    }

    @Override
    public void stopNetworkScan(Message message) {
    }

    @Override
    public void supplyIccPin(String string, Message message) {
    }

    @Override
    public void supplyIccPin2(String string, Message message) {
    }

    @Override
    public void supplyIccPin2ForApp(String string, String string2, Message message) {
    }

    @Override
    public void supplyIccPinForApp(String string, String string2, Message message) {
    }

    @Override
    public void supplyIccPuk(String string, String string2, Message message) {
    }

    @Override
    public void supplyIccPuk2(String string, String string2, Message message) {
    }

    @Override
    public void supplyIccPuk2ForApp(String string, String string2, String string3, Message message) {
    }

    @Override
    public void supplyIccPukForApp(String string, String string2, String string3, Message message) {
    }

    @Override
    public void supplyNetworkDepersonalization(String string, Message message) {
    }

    @Override
    public void switchWaitingOrHoldingAndActive(Message message) {
    }

    @Override
    public void testingEmergencyCall() {
    }

    @Override
    public void unSetOnCallRing(Handler handler) {
    }

    @Override
    public void unSetOnCatCallSetUp(Handler handler) {
    }

    @Override
    public void unSetOnCatCcAlphaNotify(Handler handler) {
    }

    @Override
    public void unSetOnCatEvent(Handler handler) {
    }

    @Override
    public void unSetOnCatProactiveCmd(Handler handler) {
    }

    @Override
    public void unSetOnCatSessionEnd(Handler handler) {
    }

    @Override
    public void unSetOnIccSmsFull(Handler handler) {
    }

    @Override
    public void unSetOnNITZTime(Handler handler) {
    }

    @Override
    public void unSetOnNewCdmaSms(Handler handler) {
    }

    @Override
    public void unSetOnNewGsmBroadcastSms(Handler handler) {
    }

    @Override
    public void unSetOnNewGsmSms(Handler handler) {
    }

    @Override
    public void unSetOnRestrictedStateChanged(Handler handler) {
    }

    @Override
    public void unSetOnSignalStrengthUpdate(Handler handler) {
    }

    @Override
    public void unSetOnSmsOnSim(Handler handler) {
    }

    @Override
    public void unSetOnSmsStatus(Handler handler) {
    }

    @Override
    public void unSetOnSs(Handler handler) {
    }

    @Override
    public void unSetOnSuppServiceNotification(Handler handler) {
    }

    @Override
    public void unSetOnUSSD(Handler handler) {
    }

    @Override
    public void unSetOnUnsolOemHookRaw(Handler handler) {
    }

    @Override
    public void unregisterForAvailable(Handler handler) {
    }

    @Override
    public void unregisterForCallStateChanged(Handler handler) {
    }

    @Override
    public void unregisterForCallWaitingInfo(Handler handler) {
    }

    @Override
    public void unregisterForCarrierInfoForImsiEncryption(Handler handler) {
    }

    @Override
    public void unregisterForCdmaOtaProvision(Handler handler) {
    }

    @Override
    public void unregisterForCdmaPrlChanged(Handler handler) {
    }

    @Override
    public void unregisterForCdmaSubscriptionChanged(Handler handler) {
    }

    @Override
    public void unregisterForCellInfoList(Handler handler) {
    }

    @Override
    public void unregisterForDataCallListChanged(Handler handler) {
    }

    @Override
    public void unregisterForDisplayInfo(Handler handler) {
    }

    @Override
    public void unregisterForEmergencyNumberList(Handler handler) {
    }

    @Override
    public void unregisterForExitEmergencyCallbackMode(Handler handler) {
    }

    @Override
    public void unregisterForHardwareConfigChanged(Handler handler) {
    }

    @Override
    public void unregisterForIccRefresh(Handler handler) {
    }

    @Override
    public void unregisterForIccSlotStatusChanged(Handler handler) {
    }

    @Override
    public void unregisterForIccStatusChanged(Handler handler) {
    }

    @Override
    public void unregisterForImsNetworkStateChanged(Handler handler) {
    }

    @Override
    public void unregisterForInCallVoicePrivacyOff(Handler handler) {
    }

    @Override
    public void unregisterForInCallVoicePrivacyOn(Handler handler) {
    }

    @Override
    public void unregisterForLceInfo(Handler handler) {
    }

    @Override
    public void unregisterForLineControlInfo(Handler handler) {
    }

    @Override
    public void unregisterForModemReset(Handler handler) {
    }

    @Override
    public void unregisterForNattKeepaliveStatus(Handler handler) {
    }

    @Override
    public void unregisterForNetworkScanResult(Handler handler) {
    }

    @Override
    public void unregisterForNetworkStateChanged(Handler handler) {
    }

    @Override
    public void unregisterForNotAvailable(Handler handler) {
    }

    @Override
    public void unregisterForNumberInfo(Handler handler) {
    }

    @Override
    public void unregisterForOffOrNotAvailable(Handler handler) {
    }

    @Override
    public void unregisterForOn(Handler handler) {
    }

    @Override
    public void unregisterForPcoData(Handler handler) {
    }

    @Override
    public void unregisterForPhysicalChannelConfiguration(Handler handler) {
    }

    @Override
    public void unregisterForRadioCapabilityChanged(Handler handler) {
    }

    @Override
    public void unregisterForRadioStateChanged(Handler handler) {
    }

    @Override
    public void unregisterForRedirectedNumberInfo(Handler handler) {
    }

    @Override
    public void unregisterForResendIncallMute(Handler handler) {
    }

    @Override
    public void unregisterForRilConnected(Handler handler) {
    }

    @Override
    public void unregisterForRingbackTone(Handler handler) {
    }

    @Override
    public void unregisterForSignalInfo(Handler handler) {
    }

    @Override
    public void unregisterForSrvccStateChanged(Handler handler) {
    }

    @Override
    public void unregisterForSubscriptionStatusChanged(Handler handler) {
    }

    @Override
    public void unregisterForT53AudioControlInfo(Handler handler) {
    }

    @Override
    public void unregisterForT53ClirInfo(Handler handler) {
    }

    @Override
    public void unregisterForVoiceRadioTechChanged(Handler handler) {
    }

    @Override
    public void unsetOnIccRefresh(Handler handler) {
    }

    @Override
    public void writeSmsToRuim(int n, String string, Message message) {
    }

    @Override
    public void writeSmsToSim(int n, String string, String string2, Message message) {
    }
}

