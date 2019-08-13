/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.telephony;

import android.annotation.UnsupportedAppUsage;
import android.app.PendingIntent;
import android.content.Intent;
import android.net.NetworkStats;
import android.net.Uri;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Messenger;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import android.os.ResultReceiver;
import android.os.WorkSource;
import android.telecom.PhoneAccount;
import android.telecom.PhoneAccountHandle;
import android.telephony.CarrierRestrictionRules;
import android.telephony.CellInfo;
import android.telephony.ClientRequestStats;
import android.telephony.ICellInfoCallback;
import android.telephony.IccOpenLogicalChannelResponse;
import android.telephony.NeighboringCellInfo;
import android.telephony.NetworkScanRequest;
import android.telephony.PhoneNumberRange;
import android.telephony.RadioAccessFamily;
import android.telephony.ServiceState;
import android.telephony.SignalStrength;
import android.telephony.TelephonyHistogram;
import android.telephony.UiccCardInfo;
import android.telephony.UiccSlotInfo;
import android.telephony.VisualVoicemailSmsFilterSettings;
import android.telephony.emergency.EmergencyNumber;
import android.telephony.ims.aidl.IImsCapabilityCallback;
import android.telephony.ims.aidl.IImsConfig;
import android.telephony.ims.aidl.IImsConfigCallback;
import android.telephony.ims.aidl.IImsMmTelFeature;
import android.telephony.ims.aidl.IImsRcsFeature;
import android.telephony.ims.aidl.IImsRegistration;
import android.telephony.ims.aidl.IImsRegistrationCallback;
import com.android.ims.internal.IImsServiceFeatureCallback;
import com.android.internal.telephony.CellNetworkScanResult;
import com.android.internal.telephony.IIntegerConsumer;
import com.android.internal.telephony.INumberVerificationCallback;
import com.android.internal.telephony.OperatorInfo;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface ITelephony
extends IInterface {
    public void cacheMmTelCapabilityProvisioning(int var1, int var2, int var3, boolean var4) throws RemoteException;

    @UnsupportedAppUsage
    public void call(String var1, String var2) throws RemoteException;

    public boolean canChangeDtmfToneLength(int var1, String var2) throws RemoteException;

    public void carrierActionReportDefaultNetworkStatus(int var1, boolean var2) throws RemoteException;

    public void carrierActionResetAll(int var1) throws RemoteException;

    public void carrierActionSetMeteredApnsEnabled(int var1, boolean var2) throws RemoteException;

    public void carrierActionSetRadioEnabled(int var1, boolean var2) throws RemoteException;

    public int checkCarrierPrivilegesForPackage(int var1, String var2) throws RemoteException;

    public int checkCarrierPrivilegesForPackageAnyPhone(String var1) throws RemoteException;

    @UnsupportedAppUsage
    public void dial(String var1) throws RemoteException;

    @UnsupportedAppUsage
    public boolean disableDataConnectivity() throws RemoteException;

    public void disableIms(int var1) throws RemoteException;

    @UnsupportedAppUsage
    public void disableLocationUpdates() throws RemoteException;

    public void disableLocationUpdatesForSubscriber(int var1) throws RemoteException;

    public void disableVisualVoicemailSmsFilter(String var1, int var2) throws RemoteException;

    public boolean doesSwitchMultiSimConfigTriggerReboot(int var1, String var2) throws RemoteException;

    @UnsupportedAppUsage
    public boolean enableDataConnectivity() throws RemoteException;

    public void enableIms(int var1) throws RemoteException;

    @UnsupportedAppUsage
    public void enableLocationUpdates() throws RemoteException;

    public void enableLocationUpdatesForSubscriber(int var1) throws RemoteException;

    public boolean enableModemForSlot(int var1, boolean var2) throws RemoteException;

    public void enableVideoCalling(boolean var1) throws RemoteException;

    public void enableVisualVoicemailSmsFilter(String var1, int var2, VisualVoicemailSmsFilterSettings var3) throws RemoteException;

    public void enqueueSmsPickResult(String var1, IIntegerConsumer var2) throws RemoteException;

    public void factoryReset(int var1) throws RemoteException;

    @UnsupportedAppUsage
    public int getActivePhoneType() throws RemoteException;

    public int getActivePhoneTypeForSlot(int var1) throws RemoteException;

    public VisualVoicemailSmsFilterSettings getActiveVisualVoicemailSmsFilterSettings(int var1) throws RemoteException;

    public String getAidForAppType(int var1, int var2) throws RemoteException;

    public List<CellInfo> getAllCellInfo(String var1) throws RemoteException;

    public CarrierRestrictionRules getAllowedCarriers() throws RemoteException;

    public int getCalculatedPreferredNetworkType(String var1) throws RemoteException;

    @UnsupportedAppUsage
    public int getCallState() throws RemoteException;

    public int getCallStateForSlot(int var1) throws RemoteException;

    public int getCardIdForDefaultEuicc(int var1, String var2) throws RemoteException;

    public int getCarrierIdFromMccMnc(int var1, String var2, boolean var3) throws RemoteException;

    public int getCarrierIdListVersion(int var1) throws RemoteException;

    public List<String> getCarrierPackageNamesForIntentAndPhone(Intent var1, int var2) throws RemoteException;

    public int getCarrierPrivilegeStatus(int var1) throws RemoteException;

    public int getCarrierPrivilegeStatusForUid(int var1, int var2) throws RemoteException;

    public int getCdmaEriIconIndex(String var1) throws RemoteException;

    public int getCdmaEriIconIndexForSubscriber(int var1, String var2) throws RemoteException;

    public int getCdmaEriIconMode(String var1) throws RemoteException;

    public int getCdmaEriIconModeForSubscriber(int var1, String var2) throws RemoteException;

    public String getCdmaEriText(String var1) throws RemoteException;

    public String getCdmaEriTextForSubscriber(int var1, String var2) throws RemoteException;

    public String getCdmaMdn(int var1) throws RemoteException;

    public String getCdmaMin(int var1) throws RemoteException;

    public String getCdmaPrlVersion(int var1) throws RemoteException;

    public int getCdmaRoamingMode(int var1) throws RemoteException;

    public Bundle getCellLocation(String var1) throws RemoteException;

    public CellNetworkScanResult getCellNetworkScanResults(int var1, String var2) throws RemoteException;

    public List<String> getCertsFromCarrierPrivilegeAccessRules(int var1) throws RemoteException;

    public List<ClientRequestStats> getClientRequestStats(String var1, int var2) throws RemoteException;

    public int getDataActivationState(int var1, String var2) throws RemoteException;

    @UnsupportedAppUsage
    public int getDataActivity() throws RemoteException;

    @UnsupportedAppUsage
    public boolean getDataEnabled(int var1) throws RemoteException;

    public int getDataNetworkType(String var1) throws RemoteException;

    public int getDataNetworkTypeForSubscriber(int var1, String var2) throws RemoteException;

    @UnsupportedAppUsage
    public int getDataState() throws RemoteException;

    public String getDefaultSmsApp(int var1) throws RemoteException;

    public String getDeviceId(String var1) throws RemoteException;

    public String getDeviceSoftwareVersionForSlot(int var1, String var2) throws RemoteException;

    public boolean getEmergencyCallbackMode(int var1) throws RemoteException;

    public Map getEmergencyNumberList(String var1) throws RemoteException;

    public List<String> getEmergencyNumberListTestMode() throws RemoteException;

    public String getEsn(int var1) throws RemoteException;

    public String[] getForbiddenPlmns(int var1, int var2, String var3) throws RemoteException;

    public String getImeiForSlot(int var1, String var2) throws RemoteException;

    public IImsConfig getImsConfig(int var1, int var2) throws RemoteException;

    public int getImsProvisioningInt(int var1, int var2) throws RemoteException;

    public boolean getImsProvisioningStatusForCapability(int var1, int var2, int var3) throws RemoteException;

    public String getImsProvisioningString(int var1, int var2) throws RemoteException;

    public int getImsRegTechnologyForMmTel(int var1) throws RemoteException;

    public IImsRegistration getImsRegistration(int var1, int var2) throws RemoteException;

    public String getImsService(int var1, boolean var2) throws RemoteException;

    public String getLine1AlphaTagForDisplay(int var1, String var2) throws RemoteException;

    public String getLine1NumberForDisplay(int var1, String var2) throws RemoteException;

    public int getLteOnCdmaMode(String var1) throws RemoteException;

    public int getLteOnCdmaModeForSubscriber(int var1, String var2) throws RemoteException;

    public String getManufacturerCodeForSlot(int var1) throws RemoteException;

    public String getMeidForSlot(int var1, String var2) throws RemoteException;

    public String[] getMergedSubscriberIds(int var1, String var2) throws RemoteException;

    public IImsMmTelFeature getMmTelFeatureAndListen(int var1, IImsServiceFeatureCallback var2) throws RemoteException;

    public String getMmsUAProfUrl(int var1) throws RemoteException;

    public String getMmsUserAgent(int var1) throws RemoteException;

    public List<NeighboringCellInfo> getNeighboringCellInfo(String var1) throws RemoteException;

    public String getNetworkCountryIsoForPhone(int var1) throws RemoteException;

    public int getNetworkSelectionMode(int var1) throws RemoteException;

    public int getNetworkTypeForSubscriber(int var1, String var2) throws RemoteException;

    public int getNumberOfModemsWithSimultaneousDataConnections(int var1, String var2) throws RemoteException;

    public List<String> getPackagesWithCarrierPrivileges(int var1) throws RemoteException;

    public List<String> getPackagesWithCarrierPrivilegesForAllPhones() throws RemoteException;

    public String[] getPcscfAddress(String var1, String var2) throws RemoteException;

    public PhoneAccountHandle getPhoneAccountHandleForSubscriptionId(int var1) throws RemoteException;

    public int getPreferredNetworkType(int var1) throws RemoteException;

    public int getRadioAccessFamily(int var1, String var2) throws RemoteException;

    public int getRadioHalVersion() throws RemoteException;

    public int getRadioPowerState(int var1, String var2) throws RemoteException;

    public IImsRcsFeature getRcsFeatureAndListen(int var1, IImsServiceFeatureCallback var2) throws RemoteException;

    public ServiceState getServiceStateForSubscriber(int var1, String var2) throws RemoteException;

    public SignalStrength getSignalStrength(int var1) throws RemoteException;

    public String getSimLocaleForSubscriber(int var1) throws RemoteException;

    public int[] getSlotsMapping() throws RemoteException;

    public String[] getSmsApps(int var1) throws RemoteException;

    public int getSubIdForPhoneAccount(PhoneAccount var1) throws RemoteException;

    public int getSubscriptionCarrierId(int var1) throws RemoteException;

    public String getSubscriptionCarrierName(int var1) throws RemoteException;

    public int getSubscriptionSpecificCarrierId(int var1) throws RemoteException;

    public String getSubscriptionSpecificCarrierName(int var1) throws RemoteException;

    public List<TelephonyHistogram> getTelephonyHistograms() throws RemoteException;

    public boolean getTetherApnRequiredForSubscriber(int var1) throws RemoteException;

    public String getTypeAllocationCodeForSlot(int var1) throws RemoteException;

    public List<UiccCardInfo> getUiccCardsInfo(String var1) throws RemoteException;

    public UiccSlotInfo[] getUiccSlotsInfo() throws RemoteException;

    public String getVisualVoicemailPackageName(String var1, int var2) throws RemoteException;

    public Bundle getVisualVoicemailSettings(String var1, int var2) throws RemoteException;

    public VisualVoicemailSmsFilterSettings getVisualVoicemailSmsFilterSettings(String var1, int var2) throws RemoteException;

    public int getVoWiFiModeSetting(int var1) throws RemoteException;

    public int getVoWiFiRoamingModeSetting(int var1) throws RemoteException;

    public int getVoiceActivationState(int var1, String var2) throws RemoteException;

    public int getVoiceMessageCountForSubscriber(int var1, String var2) throws RemoteException;

    public int getVoiceNetworkTypeForSubscriber(int var1, String var2) throws RemoteException;

    public Uri getVoicemailRingtoneUri(PhoneAccountHandle var1) throws RemoteException;

    public NetworkStats getVtDataUsage(int var1, boolean var2) throws RemoteException;

    @UnsupportedAppUsage
    public boolean handlePinMmi(String var1) throws RemoteException;

    @UnsupportedAppUsage
    public boolean handlePinMmiForSubscriber(int var1, String var2) throws RemoteException;

    public void handleUssdRequest(int var1, String var2, ResultReceiver var3) throws RemoteException;

    @UnsupportedAppUsage
    public boolean hasIccCard() throws RemoteException;

    public boolean hasIccCardUsingSlotIndex(int var1) throws RemoteException;

    @UnsupportedAppUsage
    public boolean iccCloseLogicalChannel(int var1, int var2) throws RemoteException;

    public boolean iccCloseLogicalChannelBySlot(int var1, int var2) throws RemoteException;

    public byte[] iccExchangeSimIO(int var1, int var2, int var3, int var4, int var5, int var6, String var7) throws RemoteException;

    public IccOpenLogicalChannelResponse iccOpenLogicalChannel(int var1, String var2, String var3, int var4) throws RemoteException;

    public IccOpenLogicalChannelResponse iccOpenLogicalChannelBySlot(int var1, String var2, String var3, int var4) throws RemoteException;

    public String iccTransmitApduBasicChannel(int var1, String var2, int var3, int var4, int var5, int var6, int var7, String var8) throws RemoteException;

    public String iccTransmitApduBasicChannelBySlot(int var1, String var2, int var3, int var4, int var5, int var6, int var7, String var8) throws RemoteException;

    @UnsupportedAppUsage
    public String iccTransmitApduLogicalChannel(int var1, int var2, int var3, int var4, int var5, int var6, int var7, String var8) throws RemoteException;

    public String iccTransmitApduLogicalChannelBySlot(int var1, int var2, int var3, int var4, int var5, int var6, int var7, String var8) throws RemoteException;

    public int invokeOemRilRequestRaw(byte[] var1, byte[] var2) throws RemoteException;

    public boolean isAdvancedCallingSettingEnabled(int var1) throws RemoteException;

    public boolean isApnMetered(int var1, int var2) throws RemoteException;

    public boolean isAvailable(int var1, int var2, int var3) throws RemoteException;

    public boolean isCapable(int var1, int var2, int var3) throws RemoteException;

    public boolean isConcurrentVoiceAndDataAllowed(int var1) throws RemoteException;

    public boolean isDataAllowedInVoiceCall(int var1) throws RemoteException;

    public boolean isDataConnectivityPossible(int var1) throws RemoteException;

    public boolean isDataEnabled(int var1) throws RemoteException;

    public boolean isDataEnabledForApn(int var1, int var2, String var3) throws RemoteException;

    public boolean isDataRoamingEnabled(int var1) throws RemoteException;

    public boolean isEmergencyNumber(String var1, boolean var2) throws RemoteException;

    public boolean isHearingAidCompatibilitySupported() throws RemoteException;

    public boolean isImsRegistered(int var1) throws RemoteException;

    public boolean isInEmergencySmsMode() throws RemoteException;

    public boolean isManualNetworkSelectionAllowed(int var1) throws RemoteException;

    public boolean isMmTelCapabilityProvisionedInCache(int var1, int var2, int var3) throws RemoteException;

    public boolean isModemEnabledForSlot(int var1, String var2) throws RemoteException;

    public int isMultiSimSupported(String var1) throws RemoteException;

    public boolean isRadioOn(String var1) throws RemoteException;

    @UnsupportedAppUsage
    public boolean isRadioOnForSubscriber(int var1, String var2) throws RemoteException;

    public boolean isRttSupported(int var1) throws RemoteException;

    public boolean isTtyModeSupported() throws RemoteException;

    public boolean isTtyOverVolteEnabled(int var1) throws RemoteException;

    public boolean isUserDataEnabled(int var1) throws RemoteException;

    public boolean isVideoCallingEnabled(String var1) throws RemoteException;

    public boolean isVideoTelephonyAvailable(int var1) throws RemoteException;

    public boolean isVoWiFiRoamingSettingEnabled(int var1) throws RemoteException;

    public boolean isVoWiFiSettingEnabled(int var1) throws RemoteException;

    public boolean isVoicemailVibrationEnabled(PhoneAccountHandle var1) throws RemoteException;

    public boolean isVtSettingEnabled(int var1) throws RemoteException;

    public boolean isWifiCallingAvailable(int var1) throws RemoteException;

    public boolean isWorldPhone(int var1, String var2) throws RemoteException;

    public boolean needMobileRadioShutdown() throws RemoteException;

    public boolean needsOtaServiceProvisioning() throws RemoteException;

    public String nvReadItem(int var1) throws RemoteException;

    public boolean nvWriteCdmaPrl(byte[] var1) throws RemoteException;

    public boolean nvWriteItem(int var1, String var2) throws RemoteException;

    public boolean rebootModem(int var1) throws RemoteException;

    public void refreshUiccProfile(int var1) throws RemoteException;

    public void registerImsProvisioningChangedCallback(int var1, IImsConfigCallback var2) throws RemoteException;

    public void registerImsRegistrationCallback(int var1, IImsRegistrationCallback var2) throws RemoteException;

    public void registerMmTelCapabilityCallback(int var1, IImsCapabilityCallback var2) throws RemoteException;

    public void requestCellInfoUpdate(int var1, ICellInfoCallback var2, String var3) throws RemoteException;

    public void requestCellInfoUpdateWithWorkSource(int var1, ICellInfoCallback var2, String var3, WorkSource var4) throws RemoteException;

    public void requestModemActivityInfo(ResultReceiver var1) throws RemoteException;

    public int requestNetworkScan(int var1, NetworkScanRequest var2, Messenger var3, IBinder var4, String var5) throws RemoteException;

    public void requestNumberVerification(PhoneNumberRange var1, long var2, INumberVerificationCallback var4, String var5) throws RemoteException;

    public boolean resetModemConfig(int var1) throws RemoteException;

    public void sendDialerSpecialCode(String var1, String var2) throws RemoteException;

    public String sendEnvelopeWithStatus(int var1, String var2) throws RemoteException;

    public void sendVisualVoicemailSmsForSubscriber(String var1, int var2, String var3, int var4, String var5, PendingIntent var6) throws RemoteException;

    public void setAdvancedCallingSettingEnabled(int var1, boolean var2) throws RemoteException;

    public int setAllowedCarriers(CarrierRestrictionRules var1) throws RemoteException;

    public void setCarrierTestOverride(int var1, String var2, String var3, String var4, String var5, String var6, String var7, String var8, String var9, String var10) throws RemoteException;

    public boolean setCdmaRoamingMode(int var1, int var2) throws RemoteException;

    public boolean setCdmaSubscriptionMode(int var1, int var2) throws RemoteException;

    public void setCellInfoListRate(int var1) throws RemoteException;

    public void setDataActivationState(int var1, int var2) throws RemoteException;

    public boolean setDataAllowedDuringVoiceCall(int var1, boolean var2) throws RemoteException;

    public void setDataRoamingEnabled(int var1, boolean var2) throws RemoteException;

    public void setDefaultSmsApp(int var1, String var2) throws RemoteException;

    public int setImsProvisioningInt(int var1, int var2, int var3) throws RemoteException;

    public void setImsProvisioningStatusForCapability(int var1, int var2, int var3, boolean var4) throws RemoteException;

    public int setImsProvisioningString(int var1, int var2, String var3) throws RemoteException;

    public void setImsRegistrationState(boolean var1) throws RemoteException;

    public boolean setImsService(int var1, boolean var2, String var3) throws RemoteException;

    public boolean setLine1NumberForDisplayForSubscriber(int var1, String var2, String var3) throws RemoteException;

    public void setMultiSimCarrierRestriction(boolean var1) throws RemoteException;

    public void setNetworkSelectionModeAutomatic(int var1) throws RemoteException;

    public boolean setNetworkSelectionModeManual(int var1, OperatorInfo var2, boolean var3) throws RemoteException;

    public boolean setOperatorBrandOverride(int var1, String var2) throws RemoteException;

    public void setPolicyDataEnabled(boolean var1, int var2) throws RemoteException;

    public boolean setPreferredNetworkType(int var1, int var2) throws RemoteException;

    @UnsupportedAppUsage
    public boolean setRadio(boolean var1) throws RemoteException;

    public void setRadioCapability(RadioAccessFamily[] var1) throws RemoteException;

    public boolean setRadioForSubscriber(int var1, boolean var2) throws RemoteException;

    public void setRadioIndicationUpdateMode(int var1, int var2, int var3) throws RemoteException;

    public boolean setRadioPower(boolean var1) throws RemoteException;

    public boolean setRoamingOverride(int var1, List<String> var2, List<String> var3, List<String> var4, List<String> var5) throws RemoteException;

    public void setRttCapabilitySetting(int var1, boolean var2) throws RemoteException;

    public void setSimPowerStateForSlot(int var1, int var2) throws RemoteException;

    public void setUserDataEnabled(int var1, boolean var2) throws RemoteException;

    public void setVoWiFiModeSetting(int var1, int var2) throws RemoteException;

    public void setVoWiFiNonPersistent(int var1, boolean var2, int var3) throws RemoteException;

    public void setVoWiFiRoamingModeSetting(int var1, int var2) throws RemoteException;

    public void setVoWiFiRoamingSettingEnabled(int var1, boolean var2) throws RemoteException;

    public void setVoWiFiSettingEnabled(int var1, boolean var2) throws RemoteException;

    public void setVoiceActivationState(int var1, int var2) throws RemoteException;

    public boolean setVoiceMailNumber(int var1, String var2, String var3) throws RemoteException;

    public void setVoicemailRingtoneUri(String var1, PhoneAccountHandle var2, Uri var3) throws RemoteException;

    public void setVoicemailVibrationEnabled(String var1, PhoneAccountHandle var2, boolean var3) throws RemoteException;

    public void setVtSettingEnabled(int var1, boolean var2) throws RemoteException;

    public void shutdownMobileRadios() throws RemoteException;

    public void stopNetworkScan(int var1, int var2) throws RemoteException;

    @UnsupportedAppUsage
    public boolean supplyPin(String var1) throws RemoteException;

    public boolean supplyPinForSubscriber(int var1, String var2) throws RemoteException;

    public int[] supplyPinReportResult(String var1) throws RemoteException;

    public int[] supplyPinReportResultForSubscriber(int var1, String var2) throws RemoteException;

    public boolean supplyPuk(String var1, String var2) throws RemoteException;

    public boolean supplyPukForSubscriber(int var1, String var2, String var3) throws RemoteException;

    public int[] supplyPukReportResult(String var1, String var2) throws RemoteException;

    public int[] supplyPukReportResultForSubscriber(int var1, String var2, String var3) throws RemoteException;

    public void switchMultiSimConfig(int var1) throws RemoteException;

    public boolean switchSlots(int[] var1) throws RemoteException;

    @UnsupportedAppUsage
    public void toggleRadioOnOff() throws RemoteException;

    public void toggleRadioOnOffForSubscriber(int var1) throws RemoteException;

    public void unregisterImsProvisioningChangedCallback(int var1, IImsConfigCallback var2) throws RemoteException;

    public void unregisterImsRegistrationCallback(int var1, IImsRegistrationCallback var2) throws RemoteException;

    public void unregisterMmTelCapabilityCallback(int var1, IImsCapabilityCallback var2) throws RemoteException;

    public void updateEmergencyNumberListTestMode(int var1, EmergencyNumber var2) throws RemoteException;

    @UnsupportedAppUsage
    public void updateServiceLocation() throws RemoteException;

    public void updateServiceLocationForSubscriber(int var1) throws RemoteException;

    public static class Default
    implements ITelephony {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void cacheMmTelCapabilityProvisioning(int n, int n2, int n3, boolean bl) throws RemoteException {
        }

        @Override
        public void call(String string2, String string3) throws RemoteException {
        }

        @Override
        public boolean canChangeDtmfToneLength(int n, String string2) throws RemoteException {
            return false;
        }

        @Override
        public void carrierActionReportDefaultNetworkStatus(int n, boolean bl) throws RemoteException {
        }

        @Override
        public void carrierActionResetAll(int n) throws RemoteException {
        }

        @Override
        public void carrierActionSetMeteredApnsEnabled(int n, boolean bl) throws RemoteException {
        }

        @Override
        public void carrierActionSetRadioEnabled(int n, boolean bl) throws RemoteException {
        }

        @Override
        public int checkCarrierPrivilegesForPackage(int n, String string2) throws RemoteException {
            return 0;
        }

        @Override
        public int checkCarrierPrivilegesForPackageAnyPhone(String string2) throws RemoteException {
            return 0;
        }

        @Override
        public void dial(String string2) throws RemoteException {
        }

        @Override
        public boolean disableDataConnectivity() throws RemoteException {
            return false;
        }

        @Override
        public void disableIms(int n) throws RemoteException {
        }

        @Override
        public void disableLocationUpdates() throws RemoteException {
        }

        @Override
        public void disableLocationUpdatesForSubscriber(int n) throws RemoteException {
        }

        @Override
        public void disableVisualVoicemailSmsFilter(String string2, int n) throws RemoteException {
        }

        @Override
        public boolean doesSwitchMultiSimConfigTriggerReboot(int n, String string2) throws RemoteException {
            return false;
        }

        @Override
        public boolean enableDataConnectivity() throws RemoteException {
            return false;
        }

        @Override
        public void enableIms(int n) throws RemoteException {
        }

        @Override
        public void enableLocationUpdates() throws RemoteException {
        }

        @Override
        public void enableLocationUpdatesForSubscriber(int n) throws RemoteException {
        }

        @Override
        public boolean enableModemForSlot(int n, boolean bl) throws RemoteException {
            return false;
        }

        @Override
        public void enableVideoCalling(boolean bl) throws RemoteException {
        }

        @Override
        public void enableVisualVoicemailSmsFilter(String string2, int n, VisualVoicemailSmsFilterSettings visualVoicemailSmsFilterSettings) throws RemoteException {
        }

        @Override
        public void enqueueSmsPickResult(String string2, IIntegerConsumer iIntegerConsumer) throws RemoteException {
        }

        @Override
        public void factoryReset(int n) throws RemoteException {
        }

        @Override
        public int getActivePhoneType() throws RemoteException {
            return 0;
        }

        @Override
        public int getActivePhoneTypeForSlot(int n) throws RemoteException {
            return 0;
        }

        @Override
        public VisualVoicemailSmsFilterSettings getActiveVisualVoicemailSmsFilterSettings(int n) throws RemoteException {
            return null;
        }

        @Override
        public String getAidForAppType(int n, int n2) throws RemoteException {
            return null;
        }

        @Override
        public List<CellInfo> getAllCellInfo(String string2) throws RemoteException {
            return null;
        }

        @Override
        public CarrierRestrictionRules getAllowedCarriers() throws RemoteException {
            return null;
        }

        @Override
        public int getCalculatedPreferredNetworkType(String string2) throws RemoteException {
            return 0;
        }

        @Override
        public int getCallState() throws RemoteException {
            return 0;
        }

        @Override
        public int getCallStateForSlot(int n) throws RemoteException {
            return 0;
        }

        @Override
        public int getCardIdForDefaultEuicc(int n, String string2) throws RemoteException {
            return 0;
        }

        @Override
        public int getCarrierIdFromMccMnc(int n, String string2, boolean bl) throws RemoteException {
            return 0;
        }

        @Override
        public int getCarrierIdListVersion(int n) throws RemoteException {
            return 0;
        }

        @Override
        public List<String> getCarrierPackageNamesForIntentAndPhone(Intent intent, int n) throws RemoteException {
            return null;
        }

        @Override
        public int getCarrierPrivilegeStatus(int n) throws RemoteException {
            return 0;
        }

        @Override
        public int getCarrierPrivilegeStatusForUid(int n, int n2) throws RemoteException {
            return 0;
        }

        @Override
        public int getCdmaEriIconIndex(String string2) throws RemoteException {
            return 0;
        }

        @Override
        public int getCdmaEriIconIndexForSubscriber(int n, String string2) throws RemoteException {
            return 0;
        }

        @Override
        public int getCdmaEriIconMode(String string2) throws RemoteException {
            return 0;
        }

        @Override
        public int getCdmaEriIconModeForSubscriber(int n, String string2) throws RemoteException {
            return 0;
        }

        @Override
        public String getCdmaEriText(String string2) throws RemoteException {
            return null;
        }

        @Override
        public String getCdmaEriTextForSubscriber(int n, String string2) throws RemoteException {
            return null;
        }

        @Override
        public String getCdmaMdn(int n) throws RemoteException {
            return null;
        }

        @Override
        public String getCdmaMin(int n) throws RemoteException {
            return null;
        }

        @Override
        public String getCdmaPrlVersion(int n) throws RemoteException {
            return null;
        }

        @Override
        public int getCdmaRoamingMode(int n) throws RemoteException {
            return 0;
        }

        @Override
        public Bundle getCellLocation(String string2) throws RemoteException {
            return null;
        }

        @Override
        public CellNetworkScanResult getCellNetworkScanResults(int n, String string2) throws RemoteException {
            return null;
        }

        @Override
        public List<String> getCertsFromCarrierPrivilegeAccessRules(int n) throws RemoteException {
            return null;
        }

        @Override
        public List<ClientRequestStats> getClientRequestStats(String string2, int n) throws RemoteException {
            return null;
        }

        @Override
        public int getDataActivationState(int n, String string2) throws RemoteException {
            return 0;
        }

        @Override
        public int getDataActivity() throws RemoteException {
            return 0;
        }

        @Override
        public boolean getDataEnabled(int n) throws RemoteException {
            return false;
        }

        @Override
        public int getDataNetworkType(String string2) throws RemoteException {
            return 0;
        }

        @Override
        public int getDataNetworkTypeForSubscriber(int n, String string2) throws RemoteException {
            return 0;
        }

        @Override
        public int getDataState() throws RemoteException {
            return 0;
        }

        @Override
        public String getDefaultSmsApp(int n) throws RemoteException {
            return null;
        }

        @Override
        public String getDeviceId(String string2) throws RemoteException {
            return null;
        }

        @Override
        public String getDeviceSoftwareVersionForSlot(int n, String string2) throws RemoteException {
            return null;
        }

        @Override
        public boolean getEmergencyCallbackMode(int n) throws RemoteException {
            return false;
        }

        @Override
        public Map getEmergencyNumberList(String string2) throws RemoteException {
            return null;
        }

        @Override
        public List<String> getEmergencyNumberListTestMode() throws RemoteException {
            return null;
        }

        @Override
        public String getEsn(int n) throws RemoteException {
            return null;
        }

        @Override
        public String[] getForbiddenPlmns(int n, int n2, String string2) throws RemoteException {
            return null;
        }

        @Override
        public String getImeiForSlot(int n, String string2) throws RemoteException {
            return null;
        }

        @Override
        public IImsConfig getImsConfig(int n, int n2) throws RemoteException {
            return null;
        }

        @Override
        public int getImsProvisioningInt(int n, int n2) throws RemoteException {
            return 0;
        }

        @Override
        public boolean getImsProvisioningStatusForCapability(int n, int n2, int n3) throws RemoteException {
            return false;
        }

        @Override
        public String getImsProvisioningString(int n, int n2) throws RemoteException {
            return null;
        }

        @Override
        public int getImsRegTechnologyForMmTel(int n) throws RemoteException {
            return 0;
        }

        @Override
        public IImsRegistration getImsRegistration(int n, int n2) throws RemoteException {
            return null;
        }

        @Override
        public String getImsService(int n, boolean bl) throws RemoteException {
            return null;
        }

        @Override
        public String getLine1AlphaTagForDisplay(int n, String string2) throws RemoteException {
            return null;
        }

        @Override
        public String getLine1NumberForDisplay(int n, String string2) throws RemoteException {
            return null;
        }

        @Override
        public int getLteOnCdmaMode(String string2) throws RemoteException {
            return 0;
        }

        @Override
        public int getLteOnCdmaModeForSubscriber(int n, String string2) throws RemoteException {
            return 0;
        }

        @Override
        public String getManufacturerCodeForSlot(int n) throws RemoteException {
            return null;
        }

        @Override
        public String getMeidForSlot(int n, String string2) throws RemoteException {
            return null;
        }

        @Override
        public String[] getMergedSubscriberIds(int n, String string2) throws RemoteException {
            return null;
        }

        @Override
        public IImsMmTelFeature getMmTelFeatureAndListen(int n, IImsServiceFeatureCallback iImsServiceFeatureCallback) throws RemoteException {
            return null;
        }

        @Override
        public String getMmsUAProfUrl(int n) throws RemoteException {
            return null;
        }

        @Override
        public String getMmsUserAgent(int n) throws RemoteException {
            return null;
        }

        @Override
        public List<NeighboringCellInfo> getNeighboringCellInfo(String string2) throws RemoteException {
            return null;
        }

        @Override
        public String getNetworkCountryIsoForPhone(int n) throws RemoteException {
            return null;
        }

        @Override
        public int getNetworkSelectionMode(int n) throws RemoteException {
            return 0;
        }

        @Override
        public int getNetworkTypeForSubscriber(int n, String string2) throws RemoteException {
            return 0;
        }

        @Override
        public int getNumberOfModemsWithSimultaneousDataConnections(int n, String string2) throws RemoteException {
            return 0;
        }

        @Override
        public List<String> getPackagesWithCarrierPrivileges(int n) throws RemoteException {
            return null;
        }

        @Override
        public List<String> getPackagesWithCarrierPrivilegesForAllPhones() throws RemoteException {
            return null;
        }

        @Override
        public String[] getPcscfAddress(String string2, String string3) throws RemoteException {
            return null;
        }

        @Override
        public PhoneAccountHandle getPhoneAccountHandleForSubscriptionId(int n) throws RemoteException {
            return null;
        }

        @Override
        public int getPreferredNetworkType(int n) throws RemoteException {
            return 0;
        }

        @Override
        public int getRadioAccessFamily(int n, String string2) throws RemoteException {
            return 0;
        }

        @Override
        public int getRadioHalVersion() throws RemoteException {
            return 0;
        }

        @Override
        public int getRadioPowerState(int n, String string2) throws RemoteException {
            return 0;
        }

        @Override
        public IImsRcsFeature getRcsFeatureAndListen(int n, IImsServiceFeatureCallback iImsServiceFeatureCallback) throws RemoteException {
            return null;
        }

        @Override
        public ServiceState getServiceStateForSubscriber(int n, String string2) throws RemoteException {
            return null;
        }

        @Override
        public SignalStrength getSignalStrength(int n) throws RemoteException {
            return null;
        }

        @Override
        public String getSimLocaleForSubscriber(int n) throws RemoteException {
            return null;
        }

        @Override
        public int[] getSlotsMapping() throws RemoteException {
            return null;
        }

        @Override
        public String[] getSmsApps(int n) throws RemoteException {
            return null;
        }

        @Override
        public int getSubIdForPhoneAccount(PhoneAccount phoneAccount) throws RemoteException {
            return 0;
        }

        @Override
        public int getSubscriptionCarrierId(int n) throws RemoteException {
            return 0;
        }

        @Override
        public String getSubscriptionCarrierName(int n) throws RemoteException {
            return null;
        }

        @Override
        public int getSubscriptionSpecificCarrierId(int n) throws RemoteException {
            return 0;
        }

        @Override
        public String getSubscriptionSpecificCarrierName(int n) throws RemoteException {
            return null;
        }

        @Override
        public List<TelephonyHistogram> getTelephonyHistograms() throws RemoteException {
            return null;
        }

        @Override
        public boolean getTetherApnRequiredForSubscriber(int n) throws RemoteException {
            return false;
        }

        @Override
        public String getTypeAllocationCodeForSlot(int n) throws RemoteException {
            return null;
        }

        @Override
        public List<UiccCardInfo> getUiccCardsInfo(String string2) throws RemoteException {
            return null;
        }

        @Override
        public UiccSlotInfo[] getUiccSlotsInfo() throws RemoteException {
            return null;
        }

        @Override
        public String getVisualVoicemailPackageName(String string2, int n) throws RemoteException {
            return null;
        }

        @Override
        public Bundle getVisualVoicemailSettings(String string2, int n) throws RemoteException {
            return null;
        }

        @Override
        public VisualVoicemailSmsFilterSettings getVisualVoicemailSmsFilterSettings(String string2, int n) throws RemoteException {
            return null;
        }

        @Override
        public int getVoWiFiModeSetting(int n) throws RemoteException {
            return 0;
        }

        @Override
        public int getVoWiFiRoamingModeSetting(int n) throws RemoteException {
            return 0;
        }

        @Override
        public int getVoiceActivationState(int n, String string2) throws RemoteException {
            return 0;
        }

        @Override
        public int getVoiceMessageCountForSubscriber(int n, String string2) throws RemoteException {
            return 0;
        }

        @Override
        public int getVoiceNetworkTypeForSubscriber(int n, String string2) throws RemoteException {
            return 0;
        }

        @Override
        public Uri getVoicemailRingtoneUri(PhoneAccountHandle phoneAccountHandle) throws RemoteException {
            return null;
        }

        @Override
        public NetworkStats getVtDataUsage(int n, boolean bl) throws RemoteException {
            return null;
        }

        @Override
        public boolean handlePinMmi(String string2) throws RemoteException {
            return false;
        }

        @Override
        public boolean handlePinMmiForSubscriber(int n, String string2) throws RemoteException {
            return false;
        }

        @Override
        public void handleUssdRequest(int n, String string2, ResultReceiver resultReceiver) throws RemoteException {
        }

        @Override
        public boolean hasIccCard() throws RemoteException {
            return false;
        }

        @Override
        public boolean hasIccCardUsingSlotIndex(int n) throws RemoteException {
            return false;
        }

        @Override
        public boolean iccCloseLogicalChannel(int n, int n2) throws RemoteException {
            return false;
        }

        @Override
        public boolean iccCloseLogicalChannelBySlot(int n, int n2) throws RemoteException {
            return false;
        }

        @Override
        public byte[] iccExchangeSimIO(int n, int n2, int n3, int n4, int n5, int n6, String string2) throws RemoteException {
            return null;
        }

        @Override
        public IccOpenLogicalChannelResponse iccOpenLogicalChannel(int n, String string2, String string3, int n2) throws RemoteException {
            return null;
        }

        @Override
        public IccOpenLogicalChannelResponse iccOpenLogicalChannelBySlot(int n, String string2, String string3, int n2) throws RemoteException {
            return null;
        }

        @Override
        public String iccTransmitApduBasicChannel(int n, String string2, int n2, int n3, int n4, int n5, int n6, String string3) throws RemoteException {
            return null;
        }

        @Override
        public String iccTransmitApduBasicChannelBySlot(int n, String string2, int n2, int n3, int n4, int n5, int n6, String string3) throws RemoteException {
            return null;
        }

        @Override
        public String iccTransmitApduLogicalChannel(int n, int n2, int n3, int n4, int n5, int n6, int n7, String string2) throws RemoteException {
            return null;
        }

        @Override
        public String iccTransmitApduLogicalChannelBySlot(int n, int n2, int n3, int n4, int n5, int n6, int n7, String string2) throws RemoteException {
            return null;
        }

        @Override
        public int invokeOemRilRequestRaw(byte[] arrby, byte[] arrby2) throws RemoteException {
            return 0;
        }

        @Override
        public boolean isAdvancedCallingSettingEnabled(int n) throws RemoteException {
            return false;
        }

        @Override
        public boolean isApnMetered(int n, int n2) throws RemoteException {
            return false;
        }

        @Override
        public boolean isAvailable(int n, int n2, int n3) throws RemoteException {
            return false;
        }

        @Override
        public boolean isCapable(int n, int n2, int n3) throws RemoteException {
            return false;
        }

        @Override
        public boolean isConcurrentVoiceAndDataAllowed(int n) throws RemoteException {
            return false;
        }

        @Override
        public boolean isDataAllowedInVoiceCall(int n) throws RemoteException {
            return false;
        }

        @Override
        public boolean isDataConnectivityPossible(int n) throws RemoteException {
            return false;
        }

        @Override
        public boolean isDataEnabled(int n) throws RemoteException {
            return false;
        }

        @Override
        public boolean isDataEnabledForApn(int n, int n2, String string2) throws RemoteException {
            return false;
        }

        @Override
        public boolean isDataRoamingEnabled(int n) throws RemoteException {
            return false;
        }

        @Override
        public boolean isEmergencyNumber(String string2, boolean bl) throws RemoteException {
            return false;
        }

        @Override
        public boolean isHearingAidCompatibilitySupported() throws RemoteException {
            return false;
        }

        @Override
        public boolean isImsRegistered(int n) throws RemoteException {
            return false;
        }

        @Override
        public boolean isInEmergencySmsMode() throws RemoteException {
            return false;
        }

        @Override
        public boolean isManualNetworkSelectionAllowed(int n) throws RemoteException {
            return false;
        }

        @Override
        public boolean isMmTelCapabilityProvisionedInCache(int n, int n2, int n3) throws RemoteException {
            return false;
        }

        @Override
        public boolean isModemEnabledForSlot(int n, String string2) throws RemoteException {
            return false;
        }

        @Override
        public int isMultiSimSupported(String string2) throws RemoteException {
            return 0;
        }

        @Override
        public boolean isRadioOn(String string2) throws RemoteException {
            return false;
        }

        @Override
        public boolean isRadioOnForSubscriber(int n, String string2) throws RemoteException {
            return false;
        }

        @Override
        public boolean isRttSupported(int n) throws RemoteException {
            return false;
        }

        @Override
        public boolean isTtyModeSupported() throws RemoteException {
            return false;
        }

        @Override
        public boolean isTtyOverVolteEnabled(int n) throws RemoteException {
            return false;
        }

        @Override
        public boolean isUserDataEnabled(int n) throws RemoteException {
            return false;
        }

        @Override
        public boolean isVideoCallingEnabled(String string2) throws RemoteException {
            return false;
        }

        @Override
        public boolean isVideoTelephonyAvailable(int n) throws RemoteException {
            return false;
        }

        @Override
        public boolean isVoWiFiRoamingSettingEnabled(int n) throws RemoteException {
            return false;
        }

        @Override
        public boolean isVoWiFiSettingEnabled(int n) throws RemoteException {
            return false;
        }

        @Override
        public boolean isVoicemailVibrationEnabled(PhoneAccountHandle phoneAccountHandle) throws RemoteException {
            return false;
        }

        @Override
        public boolean isVtSettingEnabled(int n) throws RemoteException {
            return false;
        }

        @Override
        public boolean isWifiCallingAvailable(int n) throws RemoteException {
            return false;
        }

        @Override
        public boolean isWorldPhone(int n, String string2) throws RemoteException {
            return false;
        }

        @Override
        public boolean needMobileRadioShutdown() throws RemoteException {
            return false;
        }

        @Override
        public boolean needsOtaServiceProvisioning() throws RemoteException {
            return false;
        }

        @Override
        public String nvReadItem(int n) throws RemoteException {
            return null;
        }

        @Override
        public boolean nvWriteCdmaPrl(byte[] arrby) throws RemoteException {
            return false;
        }

        @Override
        public boolean nvWriteItem(int n, String string2) throws RemoteException {
            return false;
        }

        @Override
        public boolean rebootModem(int n) throws RemoteException {
            return false;
        }

        @Override
        public void refreshUiccProfile(int n) throws RemoteException {
        }

        @Override
        public void registerImsProvisioningChangedCallback(int n, IImsConfigCallback iImsConfigCallback) throws RemoteException {
        }

        @Override
        public void registerImsRegistrationCallback(int n, IImsRegistrationCallback iImsRegistrationCallback) throws RemoteException {
        }

        @Override
        public void registerMmTelCapabilityCallback(int n, IImsCapabilityCallback iImsCapabilityCallback) throws RemoteException {
        }

        @Override
        public void requestCellInfoUpdate(int n, ICellInfoCallback iCellInfoCallback, String string2) throws RemoteException {
        }

        @Override
        public void requestCellInfoUpdateWithWorkSource(int n, ICellInfoCallback iCellInfoCallback, String string2, WorkSource workSource) throws RemoteException {
        }

        @Override
        public void requestModemActivityInfo(ResultReceiver resultReceiver) throws RemoteException {
        }

        @Override
        public int requestNetworkScan(int n, NetworkScanRequest networkScanRequest, Messenger messenger, IBinder iBinder, String string2) throws RemoteException {
            return 0;
        }

        @Override
        public void requestNumberVerification(PhoneNumberRange phoneNumberRange, long l, INumberVerificationCallback iNumberVerificationCallback, String string2) throws RemoteException {
        }

        @Override
        public boolean resetModemConfig(int n) throws RemoteException {
            return false;
        }

        @Override
        public void sendDialerSpecialCode(String string2, String string3) throws RemoteException {
        }

        @Override
        public String sendEnvelopeWithStatus(int n, String string2) throws RemoteException {
            return null;
        }

        @Override
        public void sendVisualVoicemailSmsForSubscriber(String string2, int n, String string3, int n2, String string4, PendingIntent pendingIntent) throws RemoteException {
        }

        @Override
        public void setAdvancedCallingSettingEnabled(int n, boolean bl) throws RemoteException {
        }

        @Override
        public int setAllowedCarriers(CarrierRestrictionRules carrierRestrictionRules) throws RemoteException {
            return 0;
        }

        @Override
        public void setCarrierTestOverride(int n, String string2, String string3, String string4, String string5, String string6, String string7, String string8, String string9, String string10) throws RemoteException {
        }

        @Override
        public boolean setCdmaRoamingMode(int n, int n2) throws RemoteException {
            return false;
        }

        @Override
        public boolean setCdmaSubscriptionMode(int n, int n2) throws RemoteException {
            return false;
        }

        @Override
        public void setCellInfoListRate(int n) throws RemoteException {
        }

        @Override
        public void setDataActivationState(int n, int n2) throws RemoteException {
        }

        @Override
        public boolean setDataAllowedDuringVoiceCall(int n, boolean bl) throws RemoteException {
            return false;
        }

        @Override
        public void setDataRoamingEnabled(int n, boolean bl) throws RemoteException {
        }

        @Override
        public void setDefaultSmsApp(int n, String string2) throws RemoteException {
        }

        @Override
        public int setImsProvisioningInt(int n, int n2, int n3) throws RemoteException {
            return 0;
        }

        @Override
        public void setImsProvisioningStatusForCapability(int n, int n2, int n3, boolean bl) throws RemoteException {
        }

        @Override
        public int setImsProvisioningString(int n, int n2, String string2) throws RemoteException {
            return 0;
        }

        @Override
        public void setImsRegistrationState(boolean bl) throws RemoteException {
        }

        @Override
        public boolean setImsService(int n, boolean bl, String string2) throws RemoteException {
            return false;
        }

        @Override
        public boolean setLine1NumberForDisplayForSubscriber(int n, String string2, String string3) throws RemoteException {
            return false;
        }

        @Override
        public void setMultiSimCarrierRestriction(boolean bl) throws RemoteException {
        }

        @Override
        public void setNetworkSelectionModeAutomatic(int n) throws RemoteException {
        }

        @Override
        public boolean setNetworkSelectionModeManual(int n, OperatorInfo operatorInfo, boolean bl) throws RemoteException {
            return false;
        }

        @Override
        public boolean setOperatorBrandOverride(int n, String string2) throws RemoteException {
            return false;
        }

        @Override
        public void setPolicyDataEnabled(boolean bl, int n) throws RemoteException {
        }

        @Override
        public boolean setPreferredNetworkType(int n, int n2) throws RemoteException {
            return false;
        }

        @Override
        public boolean setRadio(boolean bl) throws RemoteException {
            return false;
        }

        @Override
        public void setRadioCapability(RadioAccessFamily[] arrradioAccessFamily) throws RemoteException {
        }

        @Override
        public boolean setRadioForSubscriber(int n, boolean bl) throws RemoteException {
            return false;
        }

        @Override
        public void setRadioIndicationUpdateMode(int n, int n2, int n3) throws RemoteException {
        }

        @Override
        public boolean setRadioPower(boolean bl) throws RemoteException {
            return false;
        }

        @Override
        public boolean setRoamingOverride(int n, List<String> list, List<String> list2, List<String> list3, List<String> list4) throws RemoteException {
            return false;
        }

        @Override
        public void setRttCapabilitySetting(int n, boolean bl) throws RemoteException {
        }

        @Override
        public void setSimPowerStateForSlot(int n, int n2) throws RemoteException {
        }

        @Override
        public void setUserDataEnabled(int n, boolean bl) throws RemoteException {
        }

        @Override
        public void setVoWiFiModeSetting(int n, int n2) throws RemoteException {
        }

        @Override
        public void setVoWiFiNonPersistent(int n, boolean bl, int n2) throws RemoteException {
        }

        @Override
        public void setVoWiFiRoamingModeSetting(int n, int n2) throws RemoteException {
        }

        @Override
        public void setVoWiFiRoamingSettingEnabled(int n, boolean bl) throws RemoteException {
        }

        @Override
        public void setVoWiFiSettingEnabled(int n, boolean bl) throws RemoteException {
        }

        @Override
        public void setVoiceActivationState(int n, int n2) throws RemoteException {
        }

        @Override
        public boolean setVoiceMailNumber(int n, String string2, String string3) throws RemoteException {
            return false;
        }

        @Override
        public void setVoicemailRingtoneUri(String string2, PhoneAccountHandle phoneAccountHandle, Uri uri) throws RemoteException {
        }

        @Override
        public void setVoicemailVibrationEnabled(String string2, PhoneAccountHandle phoneAccountHandle, boolean bl) throws RemoteException {
        }

        @Override
        public void setVtSettingEnabled(int n, boolean bl) throws RemoteException {
        }

        @Override
        public void shutdownMobileRadios() throws RemoteException {
        }

        @Override
        public void stopNetworkScan(int n, int n2) throws RemoteException {
        }

        @Override
        public boolean supplyPin(String string2) throws RemoteException {
            return false;
        }

        @Override
        public boolean supplyPinForSubscriber(int n, String string2) throws RemoteException {
            return false;
        }

        @Override
        public int[] supplyPinReportResult(String string2) throws RemoteException {
            return null;
        }

        @Override
        public int[] supplyPinReportResultForSubscriber(int n, String string2) throws RemoteException {
            return null;
        }

        @Override
        public boolean supplyPuk(String string2, String string3) throws RemoteException {
            return false;
        }

        @Override
        public boolean supplyPukForSubscriber(int n, String string2, String string3) throws RemoteException {
            return false;
        }

        @Override
        public int[] supplyPukReportResult(String string2, String string3) throws RemoteException {
            return null;
        }

        @Override
        public int[] supplyPukReportResultForSubscriber(int n, String string2, String string3) throws RemoteException {
            return null;
        }

        @Override
        public void switchMultiSimConfig(int n) throws RemoteException {
        }

        @Override
        public boolean switchSlots(int[] arrn) throws RemoteException {
            return false;
        }

        @Override
        public void toggleRadioOnOff() throws RemoteException {
        }

        @Override
        public void toggleRadioOnOffForSubscriber(int n) throws RemoteException {
        }

        @Override
        public void unregisterImsProvisioningChangedCallback(int n, IImsConfigCallback iImsConfigCallback) throws RemoteException {
        }

        @Override
        public void unregisterImsRegistrationCallback(int n, IImsRegistrationCallback iImsRegistrationCallback) throws RemoteException {
        }

        @Override
        public void unregisterMmTelCapabilityCallback(int n, IImsCapabilityCallback iImsCapabilityCallback) throws RemoteException {
        }

        @Override
        public void updateEmergencyNumberListTestMode(int n, EmergencyNumber emergencyNumber) throws RemoteException {
        }

        @Override
        public void updateServiceLocation() throws RemoteException {
        }

        @Override
        public void updateServiceLocationForSubscriber(int n) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements ITelephony {
        private static final String DESCRIPTOR = "com.android.internal.telephony.ITelephony";
        static final int TRANSACTION_cacheMmTelCapabilityProvisioning = 231;
        static final int TRANSACTION_call = 2;
        static final int TRANSACTION_canChangeDtmfToneLength = 133;
        static final int TRANSACTION_carrierActionReportDefaultNetworkStatus = 173;
        static final int TRANSACTION_carrierActionResetAll = 174;
        static final int TRANSACTION_carrierActionSetMeteredApnsEnabled = 171;
        static final int TRANSACTION_carrierActionSetRadioEnabled = 172;
        static final int TRANSACTION_checkCarrierPrivilegesForPackage = 117;
        static final int TRANSACTION_checkCarrierPrivilegesForPackageAnyPhone = 118;
        static final int TRANSACTION_dial = 1;
        static final int TRANSACTION_disableDataConnectivity = 28;
        static final int TRANSACTION_disableIms = 92;
        static final int TRANSACTION_disableLocationUpdates = 25;
        static final int TRANSACTION_disableLocationUpdatesForSubscriber = 26;
        static final int TRANSACTION_disableVisualVoicemailSmsFilter = 56;
        static final int TRANSACTION_doesSwitchMultiSimConfigTriggerReboot = 242;
        static final int TRANSACTION_enableDataConnectivity = 27;
        static final int TRANSACTION_enableIms = 91;
        static final int TRANSACTION_enableLocationUpdates = 23;
        static final int TRANSACTION_enableLocationUpdatesForSubscriber = 24;
        static final int TRANSACTION_enableModemForSlot = 238;
        static final int TRANSACTION_enableVideoCalling = 131;
        static final int TRANSACTION_enableVisualVoicemailSmsFilter = 55;
        static final int TRANSACTION_enqueueSmsPickResult = 248;
        static final int TRANSACTION_factoryReset = 150;
        static final int TRANSACTION_getActivePhoneType = 37;
        static final int TRANSACTION_getActivePhoneTypeForSlot = 38;
        static final int TRANSACTION_getActiveVisualVoicemailSmsFilterSettings = 58;
        static final int TRANSACTION_getAidForAppType = 160;
        static final int TRANSACTION_getAllCellInfo = 69;
        static final int TRANSACTION_getAllowedCarriers = 165;
        static final int TRANSACTION_getCalculatedPreferredNetworkType = 88;
        static final int TRANSACTION_getCallState = 33;
        static final int TRANSACTION_getCallStateForSlot = 34;
        static final int TRANSACTION_getCardIdForDefaultEuicc = 182;
        static final int TRANSACTION_getCarrierIdFromMccMnc = 170;
        static final int TRANSACTION_getCarrierIdListVersion = 193;
        static final int TRANSACTION_getCarrierPackageNamesForIntentAndPhone = 119;
        static final int TRANSACTION_getCarrierPrivilegeStatus = 115;
        static final int TRANSACTION_getCarrierPrivilegeStatusForUid = 116;
        static final int TRANSACTION_getCdmaEriIconIndex = 39;
        static final int TRANSACTION_getCdmaEriIconIndexForSubscriber = 40;
        static final int TRANSACTION_getCdmaEriIconMode = 41;
        static final int TRANSACTION_getCdmaEriIconModeForSubscriber = 42;
        static final int TRANSACTION_getCdmaEriText = 43;
        static final int TRANSACTION_getCdmaEriTextForSubscriber = 44;
        static final int TRANSACTION_getCdmaMdn = 112;
        static final int TRANSACTION_getCdmaMin = 113;
        static final int TRANSACTION_getCdmaPrlVersion = 162;
        static final int TRANSACTION_getCdmaRoamingMode = 189;
        static final int TRANSACTION_getCellLocation = 30;
        static final int TRANSACTION_getCellNetworkScanResults = 100;
        static final int TRANSACTION_getCertsFromCarrierPrivilegeAccessRules = 225;
        static final int TRANSACTION_getClientRequestStats = 177;
        static final int TRANSACTION_getDataActivationState = 50;
        static final int TRANSACTION_getDataActivity = 35;
        static final int TRANSACTION_getDataEnabled = 106;
        static final int TRANSACTION_getDataNetworkType = 62;
        static final int TRANSACTION_getDataNetworkTypeForSubscriber = 63;
        static final int TRANSACTION_getDataState = 36;
        static final int TRANSACTION_getDefaultSmsApp = 199;
        static final int TRANSACTION_getDeviceId = 142;
        static final int TRANSACTION_getDeviceSoftwareVersionForSlot = 147;
        static final int TRANSACTION_getEmergencyCallbackMode = 180;
        static final int TRANSACTION_getEmergencyNumberList = 223;
        static final int TRANSACTION_getEmergencyNumberListTestMode = 237;
        static final int TRANSACTION_getEsn = 161;
        static final int TRANSACTION_getForbiddenPlmns = 179;
        static final int TRANSACTION_getImeiForSlot = 143;
        static final int TRANSACTION_getImsConfig = 96;
        static final int TRANSACTION_getImsProvisioningInt = 232;
        static final int TRANSACTION_getImsProvisioningStatusForCapability = 229;
        static final int TRANSACTION_getImsProvisioningString = 233;
        static final int TRANSACTION_getImsRegTechnologyForMmTel = 141;
        static final int TRANSACTION_getImsRegistration = 95;
        static final int TRANSACTION_getImsService = 98;
        static final int TRANSACTION_getLine1AlphaTagForDisplay = 122;
        static final int TRANSACTION_getLine1NumberForDisplay = 121;
        static final int TRANSACTION_getLteOnCdmaMode = 67;
        static final int TRANSACTION_getLteOnCdmaModeForSubscriber = 68;
        static final int TRANSACTION_getManufacturerCodeForSlot = 146;
        static final int TRANSACTION_getMeidForSlot = 145;
        static final int TRANSACTION_getMergedSubscriberIds = 123;
        static final int TRANSACTION_getMmTelFeatureAndListen = 93;
        static final int TRANSACTION_getMmsUAProfUrl = 250;
        static final int TRANSACTION_getMmsUserAgent = 249;
        static final int TRANSACTION_getNeighboringCellInfo = 32;
        static final int TRANSACTION_getNetworkCountryIsoForPhone = 31;
        static final int TRANSACTION_getNetworkSelectionMode = 196;
        static final int TRANSACTION_getNetworkTypeForSubscriber = 61;
        static final int TRANSACTION_getNumberOfModemsWithSimultaneousDataConnections = 195;
        static final int TRANSACTION_getPackagesWithCarrierPrivileges = 158;
        static final int TRANSACTION_getPackagesWithCarrierPrivilegesForAllPhones = 159;
        static final int TRANSACTION_getPcscfAddress = 110;
        static final int TRANSACTION_getPhoneAccountHandleForSubscriptionId = 149;
        static final int TRANSACTION_getPreferredNetworkType = 89;
        static final int TRANSACTION_getRadioAccessFamily = 130;
        static final int TRANSACTION_getRadioHalVersion = 244;
        static final int TRANSACTION_getRadioPowerState = 201;
        static final int TRANSACTION_getRcsFeatureAndListen = 94;
        static final int TRANSACTION_getServiceStateForSubscriber = 153;
        static final int TRANSACTION_getSignalStrength = 181;
        static final int TRANSACTION_getSimLocaleForSubscriber = 151;
        static final int TRANSACTION_getSlotsMapping = 243;
        static final int TRANSACTION_getSmsApps = 198;
        static final int TRANSACTION_getSubIdForPhoneAccount = 148;
        static final int TRANSACTION_getSubscriptionCarrierId = 166;
        static final int TRANSACTION_getSubscriptionCarrierName = 167;
        static final int TRANSACTION_getSubscriptionSpecificCarrierId = 168;
        static final int TRANSACTION_getSubscriptionSpecificCarrierName = 169;
        static final int TRANSACTION_getTelephonyHistograms = 163;
        static final int TRANSACTION_getTetherApnRequiredForSubscriber = 90;
        static final int TRANSACTION_getTypeAllocationCodeForSlot = 144;
        static final int TRANSACTION_getUiccCardsInfo = 183;
        static final int TRANSACTION_getUiccSlotsInfo = 184;
        static final int TRANSACTION_getVisualVoicemailPackageName = 54;
        static final int TRANSACTION_getVisualVoicemailSettings = 53;
        static final int TRANSACTION_getVisualVoicemailSmsFilterSettings = 57;
        static final int TRANSACTION_getVoWiFiModeSetting = 217;
        static final int TRANSACTION_getVoWiFiRoamingModeSetting = 219;
        static final int TRANSACTION_getVoiceActivationState = 49;
        static final int TRANSACTION_getVoiceMessageCountForSubscriber = 51;
        static final int TRANSACTION_getVoiceNetworkTypeForSubscriber = 64;
        static final int TRANSACTION_getVoicemailRingtoneUri = 154;
        static final int TRANSACTION_getVtDataUsage = 175;
        static final int TRANSACTION_handlePinMmi = 13;
        static final int TRANSACTION_handlePinMmiForSubscriber = 15;
        static final int TRANSACTION_handleUssdRequest = 14;
        static final int TRANSACTION_hasIccCard = 65;
        static final int TRANSACTION_hasIccCardUsingSlotIndex = 66;
        static final int TRANSACTION_iccCloseLogicalChannel = 76;
        static final int TRANSACTION_iccCloseLogicalChannelBySlot = 75;
        static final int TRANSACTION_iccExchangeSimIO = 81;
        static final int TRANSACTION_iccOpenLogicalChannel = 74;
        static final int TRANSACTION_iccOpenLogicalChannelBySlot = 73;
        static final int TRANSACTION_iccTransmitApduBasicChannel = 80;
        static final int TRANSACTION_iccTransmitApduBasicChannelBySlot = 79;
        static final int TRANSACTION_iccTransmitApduLogicalChannel = 78;
        static final int TRANSACTION_iccTransmitApduLogicalChannelBySlot = 77;
        static final int TRANSACTION_invokeOemRilRequestRaw = 126;
        static final int TRANSACTION_isAdvancedCallingSettingEnabled = 208;
        static final int TRANSACTION_isApnMetered = 247;
        static final int TRANSACTION_isAvailable = 207;
        static final int TRANSACTION_isCapable = 206;
        static final int TRANSACTION_isConcurrentVoiceAndDataAllowed = 52;
        static final int TRANSACTION_isDataAllowedInVoiceCall = 252;
        static final int TRANSACTION_isDataConnectivityPossible = 29;
        static final int TRANSACTION_isDataEnabled = 108;
        static final int TRANSACTION_isDataEnabledForApn = 246;
        static final int TRANSACTION_isDataRoamingEnabled = 187;
        static final int TRANSACTION_isEmergencyNumber = 224;
        static final int TRANSACTION_isHearingAidCompatibilitySupported = 137;
        static final int TRANSACTION_isImsRegistered = 138;
        static final int TRANSACTION_isInEmergencySmsMode = 197;
        static final int TRANSACTION_isManualNetworkSelectionAllowed = 109;
        static final int TRANSACTION_isMmTelCapabilityProvisionedInCache = 230;
        static final int TRANSACTION_isModemEnabledForSlot = 245;
        static final int TRANSACTION_isMultiSimSupported = 240;
        static final int TRANSACTION_isRadioOn = 3;
        static final int TRANSACTION_isRadioOnForSubscriber = 4;
        static final int TRANSACTION_isRttSupported = 136;
        static final int TRANSACTION_isTtyModeSupported = 135;
        static final int TRANSACTION_isTtyOverVolteEnabled = 222;
        static final int TRANSACTION_isUserDataEnabled = 107;
        static final int TRANSACTION_isVideoCallingEnabled = 132;
        static final int TRANSACTION_isVideoTelephonyAvailable = 140;
        static final int TRANSACTION_isVoWiFiRoamingSettingEnabled = 214;
        static final int TRANSACTION_isVoWiFiSettingEnabled = 212;
        static final int TRANSACTION_isVoicemailVibrationEnabled = 156;
        static final int TRANSACTION_isVtSettingEnabled = 210;
        static final int TRANSACTION_isWifiCallingAvailable = 139;
        static final int TRANSACTION_isWorldPhone = 134;
        static final int TRANSACTION_needMobileRadioShutdown = 127;
        static final int TRANSACTION_needsOtaServiceProvisioning = 45;
        static final int TRANSACTION_nvReadItem = 83;
        static final int TRANSACTION_nvWriteCdmaPrl = 85;
        static final int TRANSACTION_nvWriteItem = 84;
        static final int TRANSACTION_rebootModem = 87;
        static final int TRANSACTION_refreshUiccProfile = 194;
        static final int TRANSACTION_registerImsProvisioningChangedCallback = 226;
        static final int TRANSACTION_registerImsRegistrationCallback = 202;
        static final int TRANSACTION_registerMmTelCapabilityCallback = 204;
        static final int TRANSACTION_requestCellInfoUpdate = 70;
        static final int TRANSACTION_requestCellInfoUpdateWithWorkSource = 71;
        static final int TRANSACTION_requestModemActivityInfo = 152;
        static final int TRANSACTION_requestNetworkScan = 101;
        static final int TRANSACTION_requestNumberVerification = 114;
        static final int TRANSACTION_resetModemConfig = 86;
        static final int TRANSACTION_sendDialerSpecialCode = 60;
        static final int TRANSACTION_sendEnvelopeWithStatus = 82;
        static final int TRANSACTION_sendVisualVoicemailSmsForSubscriber = 59;
        static final int TRANSACTION_setAdvancedCallingSettingEnabled = 209;
        static final int TRANSACTION_setAllowedCarriers = 164;
        static final int TRANSACTION_setCarrierTestOverride = 192;
        static final int TRANSACTION_setCdmaRoamingMode = 190;
        static final int TRANSACTION_setCdmaSubscriptionMode = 191;
        static final int TRANSACTION_setCellInfoListRate = 72;
        static final int TRANSACTION_setDataActivationState = 48;
        static final int TRANSACTION_setDataAllowedDuringVoiceCall = 251;
        static final int TRANSACTION_setDataRoamingEnabled = 188;
        static final int TRANSACTION_setDefaultSmsApp = 200;
        static final int TRANSACTION_setImsProvisioningInt = 234;
        static final int TRANSACTION_setImsProvisioningStatusForCapability = 228;
        static final int TRANSACTION_setImsProvisioningString = 235;
        static final int TRANSACTION_setImsRegistrationState = 111;
        static final int TRANSACTION_setImsService = 97;
        static final int TRANSACTION_setLine1NumberForDisplayForSubscriber = 120;
        static final int TRANSACTION_setMultiSimCarrierRestriction = 239;
        static final int TRANSACTION_setNetworkSelectionModeAutomatic = 99;
        static final int TRANSACTION_setNetworkSelectionModeManual = 103;
        static final int TRANSACTION_setOperatorBrandOverride = 124;
        static final int TRANSACTION_setPolicyDataEnabled = 176;
        static final int TRANSACTION_setPreferredNetworkType = 104;
        static final int TRANSACTION_setRadio = 18;
        static final int TRANSACTION_setRadioCapability = 129;
        static final int TRANSACTION_setRadioForSubscriber = 19;
        static final int TRANSACTION_setRadioIndicationUpdateMode = 186;
        static final int TRANSACTION_setRadioPower = 20;
        static final int TRANSACTION_setRoamingOverride = 125;
        static final int TRANSACTION_setRttCapabilitySetting = 221;
        static final int TRANSACTION_setSimPowerStateForSlot = 178;
        static final int TRANSACTION_setUserDataEnabled = 105;
        static final int TRANSACTION_setVoWiFiModeSetting = 218;
        static final int TRANSACTION_setVoWiFiNonPersistent = 216;
        static final int TRANSACTION_setVoWiFiRoamingModeSetting = 220;
        static final int TRANSACTION_setVoWiFiRoamingSettingEnabled = 215;
        static final int TRANSACTION_setVoWiFiSettingEnabled = 213;
        static final int TRANSACTION_setVoiceActivationState = 47;
        static final int TRANSACTION_setVoiceMailNumber = 46;
        static final int TRANSACTION_setVoicemailRingtoneUri = 155;
        static final int TRANSACTION_setVoicemailVibrationEnabled = 157;
        static final int TRANSACTION_setVtSettingEnabled = 211;
        static final int TRANSACTION_shutdownMobileRadios = 128;
        static final int TRANSACTION_stopNetworkScan = 102;
        static final int TRANSACTION_supplyPin = 5;
        static final int TRANSACTION_supplyPinForSubscriber = 6;
        static final int TRANSACTION_supplyPinReportResult = 9;
        static final int TRANSACTION_supplyPinReportResultForSubscriber = 10;
        static final int TRANSACTION_supplyPuk = 7;
        static final int TRANSACTION_supplyPukForSubscriber = 8;
        static final int TRANSACTION_supplyPukReportResult = 11;
        static final int TRANSACTION_supplyPukReportResultForSubscriber = 12;
        static final int TRANSACTION_switchMultiSimConfig = 241;
        static final int TRANSACTION_switchSlots = 185;
        static final int TRANSACTION_toggleRadioOnOff = 16;
        static final int TRANSACTION_toggleRadioOnOffForSubscriber = 17;
        static final int TRANSACTION_unregisterImsProvisioningChangedCallback = 227;
        static final int TRANSACTION_unregisterImsRegistrationCallback = 203;
        static final int TRANSACTION_unregisterMmTelCapabilityCallback = 205;
        static final int TRANSACTION_updateEmergencyNumberListTestMode = 236;
        static final int TRANSACTION_updateServiceLocation = 21;
        static final int TRANSACTION_updateServiceLocationForSubscriber = 22;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static ITelephony asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof ITelephony) {
                return (ITelephony)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static ITelephony getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            switch (n) {
                default: {
                    return null;
                }
                case 252: {
                    return "isDataAllowedInVoiceCall";
                }
                case 251: {
                    return "setDataAllowedDuringVoiceCall";
                }
                case 250: {
                    return "getMmsUAProfUrl";
                }
                case 249: {
                    return "getMmsUserAgent";
                }
                case 248: {
                    return "enqueueSmsPickResult";
                }
                case 247: {
                    return "isApnMetered";
                }
                case 246: {
                    return "isDataEnabledForApn";
                }
                case 245: {
                    return "isModemEnabledForSlot";
                }
                case 244: {
                    return "getRadioHalVersion";
                }
                case 243: {
                    return "getSlotsMapping";
                }
                case 242: {
                    return "doesSwitchMultiSimConfigTriggerReboot";
                }
                case 241: {
                    return "switchMultiSimConfig";
                }
                case 240: {
                    return "isMultiSimSupported";
                }
                case 239: {
                    return "setMultiSimCarrierRestriction";
                }
                case 238: {
                    return "enableModemForSlot";
                }
                case 237: {
                    return "getEmergencyNumberListTestMode";
                }
                case 236: {
                    return "updateEmergencyNumberListTestMode";
                }
                case 235: {
                    return "setImsProvisioningString";
                }
                case 234: {
                    return "setImsProvisioningInt";
                }
                case 233: {
                    return "getImsProvisioningString";
                }
                case 232: {
                    return "getImsProvisioningInt";
                }
                case 231: {
                    return "cacheMmTelCapabilityProvisioning";
                }
                case 230: {
                    return "isMmTelCapabilityProvisionedInCache";
                }
                case 229: {
                    return "getImsProvisioningStatusForCapability";
                }
                case 228: {
                    return "setImsProvisioningStatusForCapability";
                }
                case 227: {
                    return "unregisterImsProvisioningChangedCallback";
                }
                case 226: {
                    return "registerImsProvisioningChangedCallback";
                }
                case 225: {
                    return "getCertsFromCarrierPrivilegeAccessRules";
                }
                case 224: {
                    return "isEmergencyNumber";
                }
                case 223: {
                    return "getEmergencyNumberList";
                }
                case 222: {
                    return "isTtyOverVolteEnabled";
                }
                case 221: {
                    return "setRttCapabilitySetting";
                }
                case 220: {
                    return "setVoWiFiRoamingModeSetting";
                }
                case 219: {
                    return "getVoWiFiRoamingModeSetting";
                }
                case 218: {
                    return "setVoWiFiModeSetting";
                }
                case 217: {
                    return "getVoWiFiModeSetting";
                }
                case 216: {
                    return "setVoWiFiNonPersistent";
                }
                case 215: {
                    return "setVoWiFiRoamingSettingEnabled";
                }
                case 214: {
                    return "isVoWiFiRoamingSettingEnabled";
                }
                case 213: {
                    return "setVoWiFiSettingEnabled";
                }
                case 212: {
                    return "isVoWiFiSettingEnabled";
                }
                case 211: {
                    return "setVtSettingEnabled";
                }
                case 210: {
                    return "isVtSettingEnabled";
                }
                case 209: {
                    return "setAdvancedCallingSettingEnabled";
                }
                case 208: {
                    return "isAdvancedCallingSettingEnabled";
                }
                case 207: {
                    return "isAvailable";
                }
                case 206: {
                    return "isCapable";
                }
                case 205: {
                    return "unregisterMmTelCapabilityCallback";
                }
                case 204: {
                    return "registerMmTelCapabilityCallback";
                }
                case 203: {
                    return "unregisterImsRegistrationCallback";
                }
                case 202: {
                    return "registerImsRegistrationCallback";
                }
                case 201: {
                    return "getRadioPowerState";
                }
                case 200: {
                    return "setDefaultSmsApp";
                }
                case 199: {
                    return "getDefaultSmsApp";
                }
                case 198: {
                    return "getSmsApps";
                }
                case 197: {
                    return "isInEmergencySmsMode";
                }
                case 196: {
                    return "getNetworkSelectionMode";
                }
                case 195: {
                    return "getNumberOfModemsWithSimultaneousDataConnections";
                }
                case 194: {
                    return "refreshUiccProfile";
                }
                case 193: {
                    return "getCarrierIdListVersion";
                }
                case 192: {
                    return "setCarrierTestOverride";
                }
                case 191: {
                    return "setCdmaSubscriptionMode";
                }
                case 190: {
                    return "setCdmaRoamingMode";
                }
                case 189: {
                    return "getCdmaRoamingMode";
                }
                case 188: {
                    return "setDataRoamingEnabled";
                }
                case 187: {
                    return "isDataRoamingEnabled";
                }
                case 186: {
                    return "setRadioIndicationUpdateMode";
                }
                case 185: {
                    return "switchSlots";
                }
                case 184: {
                    return "getUiccSlotsInfo";
                }
                case 183: {
                    return "getUiccCardsInfo";
                }
                case 182: {
                    return "getCardIdForDefaultEuicc";
                }
                case 181: {
                    return "getSignalStrength";
                }
                case 180: {
                    return "getEmergencyCallbackMode";
                }
                case 179: {
                    return "getForbiddenPlmns";
                }
                case 178: {
                    return "setSimPowerStateForSlot";
                }
                case 177: {
                    return "getClientRequestStats";
                }
                case 176: {
                    return "setPolicyDataEnabled";
                }
                case 175: {
                    return "getVtDataUsage";
                }
                case 174: {
                    return "carrierActionResetAll";
                }
                case 173: {
                    return "carrierActionReportDefaultNetworkStatus";
                }
                case 172: {
                    return "carrierActionSetRadioEnabled";
                }
                case 171: {
                    return "carrierActionSetMeteredApnsEnabled";
                }
                case 170: {
                    return "getCarrierIdFromMccMnc";
                }
                case 169: {
                    return "getSubscriptionSpecificCarrierName";
                }
                case 168: {
                    return "getSubscriptionSpecificCarrierId";
                }
                case 167: {
                    return "getSubscriptionCarrierName";
                }
                case 166: {
                    return "getSubscriptionCarrierId";
                }
                case 165: {
                    return "getAllowedCarriers";
                }
                case 164: {
                    return "setAllowedCarriers";
                }
                case 163: {
                    return "getTelephonyHistograms";
                }
                case 162: {
                    return "getCdmaPrlVersion";
                }
                case 161: {
                    return "getEsn";
                }
                case 160: {
                    return "getAidForAppType";
                }
                case 159: {
                    return "getPackagesWithCarrierPrivilegesForAllPhones";
                }
                case 158: {
                    return "getPackagesWithCarrierPrivileges";
                }
                case 157: {
                    return "setVoicemailVibrationEnabled";
                }
                case 156: {
                    return "isVoicemailVibrationEnabled";
                }
                case 155: {
                    return "setVoicemailRingtoneUri";
                }
                case 154: {
                    return "getVoicemailRingtoneUri";
                }
                case 153: {
                    return "getServiceStateForSubscriber";
                }
                case 152: {
                    return "requestModemActivityInfo";
                }
                case 151: {
                    return "getSimLocaleForSubscriber";
                }
                case 150: {
                    return "factoryReset";
                }
                case 149: {
                    return "getPhoneAccountHandleForSubscriptionId";
                }
                case 148: {
                    return "getSubIdForPhoneAccount";
                }
                case 147: {
                    return "getDeviceSoftwareVersionForSlot";
                }
                case 146: {
                    return "getManufacturerCodeForSlot";
                }
                case 145: {
                    return "getMeidForSlot";
                }
                case 144: {
                    return "getTypeAllocationCodeForSlot";
                }
                case 143: {
                    return "getImeiForSlot";
                }
                case 142: {
                    return "getDeviceId";
                }
                case 141: {
                    return "getImsRegTechnologyForMmTel";
                }
                case 140: {
                    return "isVideoTelephonyAvailable";
                }
                case 139: {
                    return "isWifiCallingAvailable";
                }
                case 138: {
                    return "isImsRegistered";
                }
                case 137: {
                    return "isHearingAidCompatibilitySupported";
                }
                case 136: {
                    return "isRttSupported";
                }
                case 135: {
                    return "isTtyModeSupported";
                }
                case 134: {
                    return "isWorldPhone";
                }
                case 133: {
                    return "canChangeDtmfToneLength";
                }
                case 132: {
                    return "isVideoCallingEnabled";
                }
                case 131: {
                    return "enableVideoCalling";
                }
                case 130: {
                    return "getRadioAccessFamily";
                }
                case 129: {
                    return "setRadioCapability";
                }
                case 128: {
                    return "shutdownMobileRadios";
                }
                case 127: {
                    return "needMobileRadioShutdown";
                }
                case 126: {
                    return "invokeOemRilRequestRaw";
                }
                case 125: {
                    return "setRoamingOverride";
                }
                case 124: {
                    return "setOperatorBrandOverride";
                }
                case 123: {
                    return "getMergedSubscriberIds";
                }
                case 122: {
                    return "getLine1AlphaTagForDisplay";
                }
                case 121: {
                    return "getLine1NumberForDisplay";
                }
                case 120: {
                    return "setLine1NumberForDisplayForSubscriber";
                }
                case 119: {
                    return "getCarrierPackageNamesForIntentAndPhone";
                }
                case 118: {
                    return "checkCarrierPrivilegesForPackageAnyPhone";
                }
                case 117: {
                    return "checkCarrierPrivilegesForPackage";
                }
                case 116: {
                    return "getCarrierPrivilegeStatusForUid";
                }
                case 115: {
                    return "getCarrierPrivilegeStatus";
                }
                case 114: {
                    return "requestNumberVerification";
                }
                case 113: {
                    return "getCdmaMin";
                }
                case 112: {
                    return "getCdmaMdn";
                }
                case 111: {
                    return "setImsRegistrationState";
                }
                case 110: {
                    return "getPcscfAddress";
                }
                case 109: {
                    return "isManualNetworkSelectionAllowed";
                }
                case 108: {
                    return "isDataEnabled";
                }
                case 107: {
                    return "isUserDataEnabled";
                }
                case 106: {
                    return "getDataEnabled";
                }
                case 105: {
                    return "setUserDataEnabled";
                }
                case 104: {
                    return "setPreferredNetworkType";
                }
                case 103: {
                    return "setNetworkSelectionModeManual";
                }
                case 102: {
                    return "stopNetworkScan";
                }
                case 101: {
                    return "requestNetworkScan";
                }
                case 100: {
                    return "getCellNetworkScanResults";
                }
                case 99: {
                    return "setNetworkSelectionModeAutomatic";
                }
                case 98: {
                    return "getImsService";
                }
                case 97: {
                    return "setImsService";
                }
                case 96: {
                    return "getImsConfig";
                }
                case 95: {
                    return "getImsRegistration";
                }
                case 94: {
                    return "getRcsFeatureAndListen";
                }
                case 93: {
                    return "getMmTelFeatureAndListen";
                }
                case 92: {
                    return "disableIms";
                }
                case 91: {
                    return "enableIms";
                }
                case 90: {
                    return "getTetherApnRequiredForSubscriber";
                }
                case 89: {
                    return "getPreferredNetworkType";
                }
                case 88: {
                    return "getCalculatedPreferredNetworkType";
                }
                case 87: {
                    return "rebootModem";
                }
                case 86: {
                    return "resetModemConfig";
                }
                case 85: {
                    return "nvWriteCdmaPrl";
                }
                case 84: {
                    return "nvWriteItem";
                }
                case 83: {
                    return "nvReadItem";
                }
                case 82: {
                    return "sendEnvelopeWithStatus";
                }
                case 81: {
                    return "iccExchangeSimIO";
                }
                case 80: {
                    return "iccTransmitApduBasicChannel";
                }
                case 79: {
                    return "iccTransmitApduBasicChannelBySlot";
                }
                case 78: {
                    return "iccTransmitApduLogicalChannel";
                }
                case 77: {
                    return "iccTransmitApduLogicalChannelBySlot";
                }
                case 76: {
                    return "iccCloseLogicalChannel";
                }
                case 75: {
                    return "iccCloseLogicalChannelBySlot";
                }
                case 74: {
                    return "iccOpenLogicalChannel";
                }
                case 73: {
                    return "iccOpenLogicalChannelBySlot";
                }
                case 72: {
                    return "setCellInfoListRate";
                }
                case 71: {
                    return "requestCellInfoUpdateWithWorkSource";
                }
                case 70: {
                    return "requestCellInfoUpdate";
                }
                case 69: {
                    return "getAllCellInfo";
                }
                case 68: {
                    return "getLteOnCdmaModeForSubscriber";
                }
                case 67: {
                    return "getLteOnCdmaMode";
                }
                case 66: {
                    return "hasIccCardUsingSlotIndex";
                }
                case 65: {
                    return "hasIccCard";
                }
                case 64: {
                    return "getVoiceNetworkTypeForSubscriber";
                }
                case 63: {
                    return "getDataNetworkTypeForSubscriber";
                }
                case 62: {
                    return "getDataNetworkType";
                }
                case 61: {
                    return "getNetworkTypeForSubscriber";
                }
                case 60: {
                    return "sendDialerSpecialCode";
                }
                case 59: {
                    return "sendVisualVoicemailSmsForSubscriber";
                }
                case 58: {
                    return "getActiveVisualVoicemailSmsFilterSettings";
                }
                case 57: {
                    return "getVisualVoicemailSmsFilterSettings";
                }
                case 56: {
                    return "disableVisualVoicemailSmsFilter";
                }
                case 55: {
                    return "enableVisualVoicemailSmsFilter";
                }
                case 54: {
                    return "getVisualVoicemailPackageName";
                }
                case 53: {
                    return "getVisualVoicemailSettings";
                }
                case 52: {
                    return "isConcurrentVoiceAndDataAllowed";
                }
                case 51: {
                    return "getVoiceMessageCountForSubscriber";
                }
                case 50: {
                    return "getDataActivationState";
                }
                case 49: {
                    return "getVoiceActivationState";
                }
                case 48: {
                    return "setDataActivationState";
                }
                case 47: {
                    return "setVoiceActivationState";
                }
                case 46: {
                    return "setVoiceMailNumber";
                }
                case 45: {
                    return "needsOtaServiceProvisioning";
                }
                case 44: {
                    return "getCdmaEriTextForSubscriber";
                }
                case 43: {
                    return "getCdmaEriText";
                }
                case 42: {
                    return "getCdmaEriIconModeForSubscriber";
                }
                case 41: {
                    return "getCdmaEriIconMode";
                }
                case 40: {
                    return "getCdmaEriIconIndexForSubscriber";
                }
                case 39: {
                    return "getCdmaEriIconIndex";
                }
                case 38: {
                    return "getActivePhoneTypeForSlot";
                }
                case 37: {
                    return "getActivePhoneType";
                }
                case 36: {
                    return "getDataState";
                }
                case 35: {
                    return "getDataActivity";
                }
                case 34: {
                    return "getCallStateForSlot";
                }
                case 33: {
                    return "getCallState";
                }
                case 32: {
                    return "getNeighboringCellInfo";
                }
                case 31: {
                    return "getNetworkCountryIsoForPhone";
                }
                case 30: {
                    return "getCellLocation";
                }
                case 29: {
                    return "isDataConnectivityPossible";
                }
                case 28: {
                    return "disableDataConnectivity";
                }
                case 27: {
                    return "enableDataConnectivity";
                }
                case 26: {
                    return "disableLocationUpdatesForSubscriber";
                }
                case 25: {
                    return "disableLocationUpdates";
                }
                case 24: {
                    return "enableLocationUpdatesForSubscriber";
                }
                case 23: {
                    return "enableLocationUpdates";
                }
                case 22: {
                    return "updateServiceLocationForSubscriber";
                }
                case 21: {
                    return "updateServiceLocation";
                }
                case 20: {
                    return "setRadioPower";
                }
                case 19: {
                    return "setRadioForSubscriber";
                }
                case 18: {
                    return "setRadio";
                }
                case 17: {
                    return "toggleRadioOnOffForSubscriber";
                }
                case 16: {
                    return "toggleRadioOnOff";
                }
                case 15: {
                    return "handlePinMmiForSubscriber";
                }
                case 14: {
                    return "handleUssdRequest";
                }
                case 13: {
                    return "handlePinMmi";
                }
                case 12: {
                    return "supplyPukReportResultForSubscriber";
                }
                case 11: {
                    return "supplyPukReportResult";
                }
                case 10: {
                    return "supplyPinReportResultForSubscriber";
                }
                case 9: {
                    return "supplyPinReportResult";
                }
                case 8: {
                    return "supplyPukForSubscriber";
                }
                case 7: {
                    return "supplyPuk";
                }
                case 6: {
                    return "supplyPinForSubscriber";
                }
                case 5: {
                    return "supplyPin";
                }
                case 4: {
                    return "isRadioOnForSubscriber";
                }
                case 3: {
                    return "isRadioOn";
                }
                case 2: {
                    return "call";
                }
                case 1: 
            }
            return "dial";
        }

        public static boolean setDefaultImpl(ITelephony iTelephony) {
            if (Proxy.sDefaultImpl == null && iTelephony != null) {
                Proxy.sDefaultImpl = iTelephony;
                return true;
            }
            return false;
        }

        @Override
        public IBinder asBinder() {
            return this;
        }

        @Override
        public String getTransactionName(int n) {
            return Stub.getDefaultTransactionName(n);
        }

        @Override
        public boolean onTransact(int n, Parcel object, Parcel parcel, int n2) throws RemoteException {
            if (n != 1598968902) {
                Object object2 = null;
                String string2 = null;
                Object var7_7 = null;
                Object object3 = null;
                boolean bl = false;
                boolean bl2 = false;
                boolean bl3 = false;
                boolean bl4 = false;
                boolean bl5 = false;
                boolean bl6 = false;
                boolean bl7 = false;
                boolean bl8 = false;
                boolean bl9 = false;
                boolean bl10 = false;
                boolean bl11 = false;
                boolean bl12 = false;
                boolean bl13 = false;
                boolean bl14 = false;
                boolean bl15 = false;
                boolean bl16 = false;
                boolean bl17 = false;
                boolean bl18 = false;
                boolean bl19 = false;
                boolean bl20 = false;
                boolean bl21 = false;
                boolean bl22 = false;
                boolean bl23 = false;
                boolean bl24 = false;
                boolean bl25 = false;
                boolean bl26 = false;
                boolean bl27 = false;
                boolean bl28 = false;
                switch (n) {
                    default: {
                        return super.onTransact(n, (Parcel)object, parcel, n2);
                    }
                    case 252: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.isDataAllowedInVoiceCall(((Parcel)object).readInt()) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 251: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = ((Parcel)object).readInt();
                        if (((Parcel)object).readInt() != 0) {
                            bl28 = true;
                        }
                        n = this.setDataAllowedDuringVoiceCall(n, bl28) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 250: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getMmsUAProfUrl(((Parcel)object).readInt());
                        parcel.writeNoException();
                        parcel.writeString((String)object);
                        return true;
                    }
                    case 249: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getMmsUserAgent(((Parcel)object).readInt());
                        parcel.writeNoException();
                        parcel.writeString((String)object);
                        return true;
                    }
                    case 248: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.enqueueSmsPickResult(((Parcel)object).readString(), IIntegerConsumer.Stub.asInterface(((Parcel)object).readStrongBinder()));
                        return true;
                    }
                    case 247: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.isApnMetered(((Parcel)object).readInt(), ((Parcel)object).readInt()) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 246: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.isDataEnabledForApn(((Parcel)object).readInt(), ((Parcel)object).readInt(), ((Parcel)object).readString()) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 245: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.isModemEnabledForSlot(((Parcel)object).readInt(), ((Parcel)object).readString()) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 244: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.getRadioHalVersion();
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 243: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getSlotsMapping();
                        parcel.writeNoException();
                        parcel.writeIntArray((int[])object);
                        return true;
                    }
                    case 242: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.doesSwitchMultiSimConfigTriggerReboot(((Parcel)object).readInt(), ((Parcel)object).readString()) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 241: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.switchMultiSimConfig(((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 240: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.isMultiSimSupported(((Parcel)object).readString());
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 239: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        bl28 = bl;
                        if (((Parcel)object).readInt() != 0) {
                            bl28 = true;
                        }
                        this.setMultiSimCarrierRestriction(bl28);
                        parcel.writeNoException();
                        return true;
                    }
                    case 238: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = ((Parcel)object).readInt();
                        bl28 = bl2;
                        if (((Parcel)object).readInt() != 0) {
                            bl28 = true;
                        }
                        n = this.enableModemForSlot(n, bl28) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 237: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getEmergencyNumberListTestMode();
                        parcel.writeNoException();
                        parcel.writeStringList((List<String>)object);
                        return true;
                    }
                    case 236: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = ((Parcel)object).readInt();
                        object = ((Parcel)object).readInt() != 0 ? EmergencyNumber.CREATOR.createFromParcel((Parcel)object) : null;
                        this.updateEmergencyNumberListTestMode(n, (EmergencyNumber)object);
                        parcel.writeNoException();
                        return true;
                    }
                    case 235: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.setImsProvisioningString(((Parcel)object).readInt(), ((Parcel)object).readInt(), ((Parcel)object).readString());
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 234: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.setImsProvisioningInt(((Parcel)object).readInt(), ((Parcel)object).readInt(), ((Parcel)object).readInt());
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 233: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getImsProvisioningString(((Parcel)object).readInt(), ((Parcel)object).readInt());
                        parcel.writeNoException();
                        parcel.writeString((String)object);
                        return true;
                    }
                    case 232: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.getImsProvisioningInt(((Parcel)object).readInt(), ((Parcel)object).readInt());
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 231: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        int n3 = ((Parcel)object).readInt();
                        n2 = ((Parcel)object).readInt();
                        n = ((Parcel)object).readInt();
                        bl28 = bl3;
                        if (((Parcel)object).readInt() != 0) {
                            bl28 = true;
                        }
                        this.cacheMmTelCapabilityProvisioning(n3, n2, n, bl28);
                        parcel.writeNoException();
                        return true;
                    }
                    case 230: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.isMmTelCapabilityProvisionedInCache(((Parcel)object).readInt(), ((Parcel)object).readInt(), ((Parcel)object).readInt()) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 229: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.getImsProvisioningStatusForCapability(((Parcel)object).readInt(), ((Parcel)object).readInt(), ((Parcel)object).readInt()) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 228: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = ((Parcel)object).readInt();
                        n2 = ((Parcel)object).readInt();
                        int n4 = ((Parcel)object).readInt();
                        bl28 = bl4;
                        if (((Parcel)object).readInt() != 0) {
                            bl28 = true;
                        }
                        this.setImsProvisioningStatusForCapability(n, n2, n4, bl28);
                        parcel.writeNoException();
                        return true;
                    }
                    case 227: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.unregisterImsProvisioningChangedCallback(((Parcel)object).readInt(), IImsConfigCallback.Stub.asInterface(((Parcel)object).readStrongBinder()));
                        parcel.writeNoException();
                        return true;
                    }
                    case 226: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.registerImsProvisioningChangedCallback(((Parcel)object).readInt(), IImsConfigCallback.Stub.asInterface(((Parcel)object).readStrongBinder()));
                        parcel.writeNoException();
                        return true;
                    }
                    case 225: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getCertsFromCarrierPrivilegeAccessRules(((Parcel)object).readInt());
                        parcel.writeNoException();
                        parcel.writeStringList((List<String>)object);
                        return true;
                    }
                    case 224: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object3 = ((Parcel)object).readString();
                        bl28 = bl5;
                        if (((Parcel)object).readInt() != 0) {
                            bl28 = true;
                        }
                        n = this.isEmergencyNumber((String)object3, bl28) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 223: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getEmergencyNumberList(((Parcel)object).readString());
                        parcel.writeNoException();
                        parcel.writeMap((Map)object);
                        return true;
                    }
                    case 222: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.isTtyOverVolteEnabled(((Parcel)object).readInt()) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 221: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = ((Parcel)object).readInt();
                        bl28 = bl6;
                        if (((Parcel)object).readInt() != 0) {
                            bl28 = true;
                        }
                        this.setRttCapabilitySetting(n, bl28);
                        parcel.writeNoException();
                        return true;
                    }
                    case 220: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.setVoWiFiRoamingModeSetting(((Parcel)object).readInt(), ((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 219: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.getVoWiFiRoamingModeSetting(((Parcel)object).readInt());
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 218: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.setVoWiFiModeSetting(((Parcel)object).readInt(), ((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 217: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.getVoWiFiModeSetting(((Parcel)object).readInt());
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 216: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = ((Parcel)object).readInt();
                        bl28 = bl7;
                        if (((Parcel)object).readInt() != 0) {
                            bl28 = true;
                        }
                        this.setVoWiFiNonPersistent(n, bl28, ((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 215: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = ((Parcel)object).readInt();
                        bl28 = bl8;
                        if (((Parcel)object).readInt() != 0) {
                            bl28 = true;
                        }
                        this.setVoWiFiRoamingSettingEnabled(n, bl28);
                        parcel.writeNoException();
                        return true;
                    }
                    case 214: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.isVoWiFiRoamingSettingEnabled(((Parcel)object).readInt()) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 213: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = ((Parcel)object).readInt();
                        bl28 = bl9;
                        if (((Parcel)object).readInt() != 0) {
                            bl28 = true;
                        }
                        this.setVoWiFiSettingEnabled(n, bl28);
                        parcel.writeNoException();
                        return true;
                    }
                    case 212: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.isVoWiFiSettingEnabled(((Parcel)object).readInt()) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 211: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = ((Parcel)object).readInt();
                        bl28 = bl10;
                        if (((Parcel)object).readInt() != 0) {
                            bl28 = true;
                        }
                        this.setVtSettingEnabled(n, bl28);
                        parcel.writeNoException();
                        return true;
                    }
                    case 210: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.isVtSettingEnabled(((Parcel)object).readInt()) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 209: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = ((Parcel)object).readInt();
                        bl28 = bl11;
                        if (((Parcel)object).readInt() != 0) {
                            bl28 = true;
                        }
                        this.setAdvancedCallingSettingEnabled(n, bl28);
                        parcel.writeNoException();
                        return true;
                    }
                    case 208: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.isAdvancedCallingSettingEnabled(((Parcel)object).readInt()) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 207: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.isAvailable(((Parcel)object).readInt(), ((Parcel)object).readInt(), ((Parcel)object).readInt()) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 206: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.isCapable(((Parcel)object).readInt(), ((Parcel)object).readInt(), ((Parcel)object).readInt()) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 205: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.unregisterMmTelCapabilityCallback(((Parcel)object).readInt(), IImsCapabilityCallback.Stub.asInterface(((Parcel)object).readStrongBinder()));
                        parcel.writeNoException();
                        return true;
                    }
                    case 204: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.registerMmTelCapabilityCallback(((Parcel)object).readInt(), IImsCapabilityCallback.Stub.asInterface(((Parcel)object).readStrongBinder()));
                        parcel.writeNoException();
                        return true;
                    }
                    case 203: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.unregisterImsRegistrationCallback(((Parcel)object).readInt(), IImsRegistrationCallback.Stub.asInterface(((Parcel)object).readStrongBinder()));
                        parcel.writeNoException();
                        return true;
                    }
                    case 202: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.registerImsRegistrationCallback(((Parcel)object).readInt(), IImsRegistrationCallback.Stub.asInterface(((Parcel)object).readStrongBinder()));
                        parcel.writeNoException();
                        return true;
                    }
                    case 201: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.getRadioPowerState(((Parcel)object).readInt(), ((Parcel)object).readString());
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 200: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.setDefaultSmsApp(((Parcel)object).readInt(), ((Parcel)object).readString());
                        parcel.writeNoException();
                        return true;
                    }
                    case 199: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getDefaultSmsApp(((Parcel)object).readInt());
                        parcel.writeNoException();
                        parcel.writeString((String)object);
                        return true;
                    }
                    case 198: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getSmsApps(((Parcel)object).readInt());
                        parcel.writeNoException();
                        parcel.writeStringArray((String[])object);
                        return true;
                    }
                    case 197: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.isInEmergencySmsMode() ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 196: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.getNetworkSelectionMode(((Parcel)object).readInt());
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 195: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.getNumberOfModemsWithSimultaneousDataConnections(((Parcel)object).readInt(), ((Parcel)object).readString());
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 194: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.refreshUiccProfile(((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 193: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.getCarrierIdListVersion(((Parcel)object).readInt());
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 192: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.setCarrierTestOverride(((Parcel)object).readInt(), ((Parcel)object).readString(), ((Parcel)object).readString(), ((Parcel)object).readString(), ((Parcel)object).readString(), ((Parcel)object).readString(), ((Parcel)object).readString(), ((Parcel)object).readString(), ((Parcel)object).readString(), ((Parcel)object).readString());
                        parcel.writeNoException();
                        return true;
                    }
                    case 191: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.setCdmaSubscriptionMode(((Parcel)object).readInt(), ((Parcel)object).readInt()) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 190: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.setCdmaRoamingMode(((Parcel)object).readInt(), ((Parcel)object).readInt()) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 189: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.getCdmaRoamingMode(((Parcel)object).readInt());
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 188: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = ((Parcel)object).readInt();
                        bl28 = bl12;
                        if (((Parcel)object).readInt() != 0) {
                            bl28 = true;
                        }
                        this.setDataRoamingEnabled(n, bl28);
                        parcel.writeNoException();
                        return true;
                    }
                    case 187: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.isDataRoamingEnabled(((Parcel)object).readInt()) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 186: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.setRadioIndicationUpdateMode(((Parcel)object).readInt(), ((Parcel)object).readInt(), ((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 185: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.switchSlots(((Parcel)object).createIntArray()) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 184: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getUiccSlotsInfo();
                        parcel.writeNoException();
                        parcel.writeTypedArray((Parcelable[])object, 1);
                        return true;
                    }
                    case 183: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getUiccCardsInfo(((Parcel)object).readString());
                        parcel.writeNoException();
                        parcel.writeTypedList(object);
                        return true;
                    }
                    case 182: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.getCardIdForDefaultEuicc(((Parcel)object).readInt(), ((Parcel)object).readString());
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 181: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getSignalStrength(((Parcel)object).readInt());
                        parcel.writeNoException();
                        if (object != null) {
                            parcel.writeInt(1);
                            ((SignalStrength)object).writeToParcel(parcel, 1);
                        } else {
                            parcel.writeInt(0);
                        }
                        return true;
                    }
                    case 180: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.getEmergencyCallbackMode(((Parcel)object).readInt()) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 179: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getForbiddenPlmns(((Parcel)object).readInt(), ((Parcel)object).readInt(), ((Parcel)object).readString());
                        parcel.writeNoException();
                        parcel.writeStringArray((String[])object);
                        return true;
                    }
                    case 178: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.setSimPowerStateForSlot(((Parcel)object).readInt(), ((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 177: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getClientRequestStats(((Parcel)object).readString(), ((Parcel)object).readInt());
                        parcel.writeNoException();
                        parcel.writeTypedList(object);
                        return true;
                    }
                    case 176: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        bl28 = bl13;
                        if (((Parcel)object).readInt() != 0) {
                            bl28 = true;
                        }
                        this.setPolicyDataEnabled(bl28, ((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 175: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = ((Parcel)object).readInt();
                        bl28 = ((Parcel)object).readInt() != 0;
                        object = this.getVtDataUsage(n, bl28);
                        parcel.writeNoException();
                        if (object != null) {
                            parcel.writeInt(1);
                            ((NetworkStats)object).writeToParcel(parcel, 1);
                        } else {
                            parcel.writeInt(0);
                        }
                        return true;
                    }
                    case 174: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.carrierActionResetAll(((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 173: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = ((Parcel)object).readInt();
                        bl28 = bl14;
                        if (((Parcel)object).readInt() != 0) {
                            bl28 = true;
                        }
                        this.carrierActionReportDefaultNetworkStatus(n, bl28);
                        parcel.writeNoException();
                        return true;
                    }
                    case 172: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = ((Parcel)object).readInt();
                        bl28 = bl15;
                        if (((Parcel)object).readInt() != 0) {
                            bl28 = true;
                        }
                        this.carrierActionSetRadioEnabled(n, bl28);
                        parcel.writeNoException();
                        return true;
                    }
                    case 171: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = ((Parcel)object).readInt();
                        bl28 = bl16;
                        if (((Parcel)object).readInt() != 0) {
                            bl28 = true;
                        }
                        this.carrierActionSetMeteredApnsEnabled(n, bl28);
                        parcel.writeNoException();
                        return true;
                    }
                    case 170: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = ((Parcel)object).readInt();
                        object3 = ((Parcel)object).readString();
                        bl28 = bl17;
                        if (((Parcel)object).readInt() != 0) {
                            bl28 = true;
                        }
                        n = this.getCarrierIdFromMccMnc(n, (String)object3, bl28);
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 169: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getSubscriptionSpecificCarrierName(((Parcel)object).readInt());
                        parcel.writeNoException();
                        parcel.writeString((String)object);
                        return true;
                    }
                    case 168: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.getSubscriptionSpecificCarrierId(((Parcel)object).readInt());
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 167: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getSubscriptionCarrierName(((Parcel)object).readInt());
                        parcel.writeNoException();
                        parcel.writeString((String)object);
                        return true;
                    }
                    case 166: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.getSubscriptionCarrierId(((Parcel)object).readInt());
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 165: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getAllowedCarriers();
                        parcel.writeNoException();
                        if (object != null) {
                            parcel.writeInt(1);
                            ((CarrierRestrictionRules)object).writeToParcel(parcel, 1);
                        } else {
                            parcel.writeInt(0);
                        }
                        return true;
                    }
                    case 164: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = ((Parcel)object).readInt() != 0 ? CarrierRestrictionRules.CREATOR.createFromParcel((Parcel)object) : null;
                        n = this.setAllowedCarriers((CarrierRestrictionRules)object);
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 163: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getTelephonyHistograms();
                        parcel.writeNoException();
                        parcel.writeTypedList(object);
                        return true;
                    }
                    case 162: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getCdmaPrlVersion(((Parcel)object).readInt());
                        parcel.writeNoException();
                        parcel.writeString((String)object);
                        return true;
                    }
                    case 161: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getEsn(((Parcel)object).readInt());
                        parcel.writeNoException();
                        parcel.writeString((String)object);
                        return true;
                    }
                    case 160: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getAidForAppType(((Parcel)object).readInt(), ((Parcel)object).readInt());
                        parcel.writeNoException();
                        parcel.writeString((String)object);
                        return true;
                    }
                    case 159: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getPackagesWithCarrierPrivilegesForAllPhones();
                        parcel.writeNoException();
                        parcel.writeStringList((List<String>)object);
                        return true;
                    }
                    case 158: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getPackagesWithCarrierPrivileges(((Parcel)object).readInt());
                        parcel.writeNoException();
                        parcel.writeStringList((List<String>)object);
                        return true;
                    }
                    case 157: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object2 = ((Parcel)object).readString();
                        object3 = ((Parcel)object).readInt() != 0 ? PhoneAccountHandle.CREATOR.createFromParcel((Parcel)object) : null;
                        bl28 = bl18;
                        if (((Parcel)object).readInt() != 0) {
                            bl28 = true;
                        }
                        this.setVoicemailVibrationEnabled((String)object2, (PhoneAccountHandle)object3, bl28);
                        parcel.writeNoException();
                        return true;
                    }
                    case 156: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = ((Parcel)object).readInt() != 0 ? PhoneAccountHandle.CREATOR.createFromParcel((Parcel)object) : null;
                        n = this.isVoicemailVibrationEnabled((PhoneAccountHandle)object) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 155: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object2 = ((Parcel)object).readString();
                        object3 = ((Parcel)object).readInt() != 0 ? PhoneAccountHandle.CREATOR.createFromParcel((Parcel)object) : null;
                        object = ((Parcel)object).readInt() != 0 ? Uri.CREATOR.createFromParcel((Parcel)object) : null;
                        this.setVoicemailRingtoneUri((String)object2, (PhoneAccountHandle)object3, (Uri)object);
                        parcel.writeNoException();
                        return true;
                    }
                    case 154: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = ((Parcel)object).readInt() != 0 ? PhoneAccountHandle.CREATOR.createFromParcel((Parcel)object) : null;
                        object = this.getVoicemailRingtoneUri((PhoneAccountHandle)object);
                        parcel.writeNoException();
                        if (object != null) {
                            parcel.writeInt(1);
                            object.writeToParcel(parcel, 1);
                        } else {
                            parcel.writeInt(0);
                        }
                        return true;
                    }
                    case 153: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getServiceStateForSubscriber(((Parcel)object).readInt(), ((Parcel)object).readString());
                        parcel.writeNoException();
                        if (object != null) {
                            parcel.writeInt(1);
                            ((ServiceState)object).writeToParcel(parcel, 1);
                        } else {
                            parcel.writeInt(0);
                        }
                        return true;
                    }
                    case 152: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = ((Parcel)object).readInt() != 0 ? ResultReceiver.CREATOR.createFromParcel((Parcel)object) : null;
                        this.requestModemActivityInfo((ResultReceiver)object);
                        return true;
                    }
                    case 151: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getSimLocaleForSubscriber(((Parcel)object).readInt());
                        parcel.writeNoException();
                        parcel.writeString((String)object);
                        return true;
                    }
                    case 150: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.factoryReset(((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 149: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getPhoneAccountHandleForSubscriptionId(((Parcel)object).readInt());
                        parcel.writeNoException();
                        if (object != null) {
                            parcel.writeInt(1);
                            ((PhoneAccountHandle)object).writeToParcel(parcel, 1);
                        } else {
                            parcel.writeInt(0);
                        }
                        return true;
                    }
                    case 148: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = ((Parcel)object).readInt() != 0 ? PhoneAccount.CREATOR.createFromParcel((Parcel)object) : null;
                        n = this.getSubIdForPhoneAccount((PhoneAccount)object);
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 147: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getDeviceSoftwareVersionForSlot(((Parcel)object).readInt(), ((Parcel)object).readString());
                        parcel.writeNoException();
                        parcel.writeString((String)object);
                        return true;
                    }
                    case 146: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getManufacturerCodeForSlot(((Parcel)object).readInt());
                        parcel.writeNoException();
                        parcel.writeString((String)object);
                        return true;
                    }
                    case 145: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getMeidForSlot(((Parcel)object).readInt(), ((Parcel)object).readString());
                        parcel.writeNoException();
                        parcel.writeString((String)object);
                        return true;
                    }
                    case 144: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getTypeAllocationCodeForSlot(((Parcel)object).readInt());
                        parcel.writeNoException();
                        parcel.writeString((String)object);
                        return true;
                    }
                    case 143: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getImeiForSlot(((Parcel)object).readInt(), ((Parcel)object).readString());
                        parcel.writeNoException();
                        parcel.writeString((String)object);
                        return true;
                    }
                    case 142: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getDeviceId(((Parcel)object).readString());
                        parcel.writeNoException();
                        parcel.writeString((String)object);
                        return true;
                    }
                    case 141: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.getImsRegTechnologyForMmTel(((Parcel)object).readInt());
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 140: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.isVideoTelephonyAvailable(((Parcel)object).readInt()) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 139: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.isWifiCallingAvailable(((Parcel)object).readInt()) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 138: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.isImsRegistered(((Parcel)object).readInt()) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 137: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.isHearingAidCompatibilitySupported() ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 136: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.isRttSupported(((Parcel)object).readInt()) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 135: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.isTtyModeSupported() ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 134: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.isWorldPhone(((Parcel)object).readInt(), ((Parcel)object).readString()) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 133: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.canChangeDtmfToneLength(((Parcel)object).readInt(), ((Parcel)object).readString()) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 132: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.isVideoCallingEnabled(((Parcel)object).readString()) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 131: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        bl28 = bl19;
                        if (((Parcel)object).readInt() != 0) {
                            bl28 = true;
                        }
                        this.enableVideoCalling(bl28);
                        parcel.writeNoException();
                        return true;
                    }
                    case 130: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.getRadioAccessFamily(((Parcel)object).readInt(), ((Parcel)object).readString());
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 129: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.setRadioCapability(((Parcel)object).createTypedArray(RadioAccessFamily.CREATOR));
                        parcel.writeNoException();
                        return true;
                    }
                    case 128: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.shutdownMobileRadios();
                        parcel.writeNoException();
                        return true;
                    }
                    case 127: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.needMobileRadioShutdown() ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 126: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object3 = ((Parcel)object).createByteArray();
                        n = ((Parcel)object).readInt();
                        object = n < 0 ? null : new byte[n];
                        n = this.invokeOemRilRequestRaw((byte[])object3, (byte[])object);
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        parcel.writeByteArray((byte[])object);
                        return true;
                    }
                    case 125: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.setRoamingOverride(((Parcel)object).readInt(), ((Parcel)object).createStringArrayList(), ((Parcel)object).createStringArrayList(), ((Parcel)object).createStringArrayList(), ((Parcel)object).createStringArrayList()) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 124: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.setOperatorBrandOverride(((Parcel)object).readInt(), ((Parcel)object).readString()) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 123: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getMergedSubscriberIds(((Parcel)object).readInt(), ((Parcel)object).readString());
                        parcel.writeNoException();
                        parcel.writeStringArray((String[])object);
                        return true;
                    }
                    case 122: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getLine1AlphaTagForDisplay(((Parcel)object).readInt(), ((Parcel)object).readString());
                        parcel.writeNoException();
                        parcel.writeString((String)object);
                        return true;
                    }
                    case 121: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getLine1NumberForDisplay(((Parcel)object).readInt(), ((Parcel)object).readString());
                        parcel.writeNoException();
                        parcel.writeString((String)object);
                        return true;
                    }
                    case 120: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.setLine1NumberForDisplayForSubscriber(((Parcel)object).readInt(), ((Parcel)object).readString(), ((Parcel)object).readString()) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 119: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object3 = ((Parcel)object).readInt() != 0 ? Intent.CREATOR.createFromParcel((Parcel)object) : null;
                        object = this.getCarrierPackageNamesForIntentAndPhone((Intent)object3, ((Parcel)object).readInt());
                        parcel.writeNoException();
                        parcel.writeStringList((List<String>)object);
                        return true;
                    }
                    case 118: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.checkCarrierPrivilegesForPackageAnyPhone(((Parcel)object).readString());
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 117: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.checkCarrierPrivilegesForPackage(((Parcel)object).readInt(), ((Parcel)object).readString());
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 116: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.getCarrierPrivilegeStatusForUid(((Parcel)object).readInt(), ((Parcel)object).readInt());
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 115: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.getCarrierPrivilegeStatus(((Parcel)object).readInt());
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 114: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object3 = ((Parcel)object).readInt() != 0 ? PhoneNumberRange.CREATOR.createFromParcel((Parcel)object) : null;
                        this.requestNumberVerification((PhoneNumberRange)object3, ((Parcel)object).readLong(), INumberVerificationCallback.Stub.asInterface(((Parcel)object).readStrongBinder()), ((Parcel)object).readString());
                        parcel.writeNoException();
                        return true;
                    }
                    case 113: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getCdmaMin(((Parcel)object).readInt());
                        parcel.writeNoException();
                        parcel.writeString((String)object);
                        return true;
                    }
                    case 112: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getCdmaMdn(((Parcel)object).readInt());
                        parcel.writeNoException();
                        parcel.writeString((String)object);
                        return true;
                    }
                    case 111: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        bl28 = bl20;
                        if (((Parcel)object).readInt() != 0) {
                            bl28 = true;
                        }
                        this.setImsRegistrationState(bl28);
                        parcel.writeNoException();
                        return true;
                    }
                    case 110: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getPcscfAddress(((Parcel)object).readString(), ((Parcel)object).readString());
                        parcel.writeNoException();
                        parcel.writeStringArray((String[])object);
                        return true;
                    }
                    case 109: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.isManualNetworkSelectionAllowed(((Parcel)object).readInt()) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 108: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.isDataEnabled(((Parcel)object).readInt()) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 107: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.isUserDataEnabled(((Parcel)object).readInt()) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 106: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.getDataEnabled(((Parcel)object).readInt()) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 105: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = ((Parcel)object).readInt();
                        bl28 = bl21;
                        if (((Parcel)object).readInt() != 0) {
                            bl28 = true;
                        }
                        this.setUserDataEnabled(n, bl28);
                        parcel.writeNoException();
                        return true;
                    }
                    case 104: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.setPreferredNetworkType(((Parcel)object).readInt(), ((Parcel)object).readInt()) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 103: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = ((Parcel)object).readInt();
                        object3 = ((Parcel)object).readInt() != 0 ? OperatorInfo.CREATOR.createFromParcel((Parcel)object) : null;
                        bl28 = bl22;
                        if (((Parcel)object).readInt() != 0) {
                            bl28 = true;
                        }
                        n = this.setNetworkSelectionModeManual(n, (OperatorInfo)object3, bl28) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 102: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.stopNetworkScan(((Parcel)object).readInt(), ((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 101: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = ((Parcel)object).readInt();
                        object3 = ((Parcel)object).readInt() != 0 ? NetworkScanRequest.CREATOR.createFromParcel((Parcel)object) : null;
                        object2 = ((Parcel)object).readInt() != 0 ? Messenger.CREATOR.createFromParcel((Parcel)object) : null;
                        n = this.requestNetworkScan(n, (NetworkScanRequest)object3, (Messenger)object2, ((Parcel)object).readStrongBinder(), ((Parcel)object).readString());
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 100: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getCellNetworkScanResults(((Parcel)object).readInt(), ((Parcel)object).readString());
                        parcel.writeNoException();
                        if (object != null) {
                            parcel.writeInt(1);
                            ((CellNetworkScanResult)object).writeToParcel(parcel, 1);
                        } else {
                            parcel.writeInt(0);
                        }
                        return true;
                    }
                    case 99: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.setNetworkSelectionModeAutomatic(((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 98: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = ((Parcel)object).readInt();
                        bl28 = bl23;
                        if (((Parcel)object).readInt() != 0) {
                            bl28 = true;
                        }
                        object = this.getImsService(n, bl28);
                        parcel.writeNoException();
                        parcel.writeString((String)object);
                        return true;
                    }
                    case 97: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = ((Parcel)object).readInt();
                        bl28 = bl24;
                        if (((Parcel)object).readInt() != 0) {
                            bl28 = true;
                        }
                        n = this.setImsService(n, bl28, ((Parcel)object).readString()) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 96: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object2 = this.getImsConfig(((Parcel)object).readInt(), ((Parcel)object).readInt());
                        parcel.writeNoException();
                        object = object3;
                        if (object2 != null) {
                            object = object2.asBinder();
                        }
                        parcel.writeStrongBinder((IBinder)object);
                        return true;
                    }
                    case 95: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object3 = this.getImsRegistration(((Parcel)object).readInt(), ((Parcel)object).readInt());
                        parcel.writeNoException();
                        object = object2;
                        if (object3 != null) {
                            object = object3.asBinder();
                        }
                        parcel.writeStrongBinder((IBinder)object);
                        return true;
                    }
                    case 94: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object3 = this.getRcsFeatureAndListen(((Parcel)object).readInt(), IImsServiceFeatureCallback.Stub.asInterface(((Parcel)object).readStrongBinder()));
                        parcel.writeNoException();
                        object = string2;
                        if (object3 != null) {
                            object = object3.asBinder();
                        }
                        parcel.writeStrongBinder((IBinder)object);
                        return true;
                    }
                    case 93: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object3 = this.getMmTelFeatureAndListen(((Parcel)object).readInt(), IImsServiceFeatureCallback.Stub.asInterface(((Parcel)object).readStrongBinder()));
                        parcel.writeNoException();
                        object = var7_7;
                        if (object3 != null) {
                            object = object3.asBinder();
                        }
                        parcel.writeStrongBinder((IBinder)object);
                        return true;
                    }
                    case 92: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.disableIms(((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 91: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.enableIms(((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 90: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.getTetherApnRequiredForSubscriber(((Parcel)object).readInt()) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 89: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.getPreferredNetworkType(((Parcel)object).readInt());
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 88: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.getCalculatedPreferredNetworkType(((Parcel)object).readString());
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 87: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.rebootModem(((Parcel)object).readInt()) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 86: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.resetModemConfig(((Parcel)object).readInt()) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 85: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.nvWriteCdmaPrl(((Parcel)object).createByteArray()) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 84: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.nvWriteItem(((Parcel)object).readInt(), ((Parcel)object).readString()) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 83: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.nvReadItem(((Parcel)object).readInt());
                        parcel.writeNoException();
                        parcel.writeString((String)object);
                        return true;
                    }
                    case 82: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.sendEnvelopeWithStatus(((Parcel)object).readInt(), ((Parcel)object).readString());
                        parcel.writeNoException();
                        parcel.writeString((String)object);
                        return true;
                    }
                    case 81: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.iccExchangeSimIO(((Parcel)object).readInt(), ((Parcel)object).readInt(), ((Parcel)object).readInt(), ((Parcel)object).readInt(), ((Parcel)object).readInt(), ((Parcel)object).readInt(), ((Parcel)object).readString());
                        parcel.writeNoException();
                        parcel.writeByteArray((byte[])object);
                        return true;
                    }
                    case 80: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.iccTransmitApduBasicChannel(((Parcel)object).readInt(), ((Parcel)object).readString(), ((Parcel)object).readInt(), ((Parcel)object).readInt(), ((Parcel)object).readInt(), ((Parcel)object).readInt(), ((Parcel)object).readInt(), ((Parcel)object).readString());
                        parcel.writeNoException();
                        parcel.writeString((String)object);
                        return true;
                    }
                    case 79: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.iccTransmitApduBasicChannelBySlot(((Parcel)object).readInt(), ((Parcel)object).readString(), ((Parcel)object).readInt(), ((Parcel)object).readInt(), ((Parcel)object).readInt(), ((Parcel)object).readInt(), ((Parcel)object).readInt(), ((Parcel)object).readString());
                        parcel.writeNoException();
                        parcel.writeString((String)object);
                        return true;
                    }
                    case 78: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.iccTransmitApduLogicalChannel(((Parcel)object).readInt(), ((Parcel)object).readInt(), ((Parcel)object).readInt(), ((Parcel)object).readInt(), ((Parcel)object).readInt(), ((Parcel)object).readInt(), ((Parcel)object).readInt(), ((Parcel)object).readString());
                        parcel.writeNoException();
                        parcel.writeString((String)object);
                        return true;
                    }
                    case 77: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.iccTransmitApduLogicalChannelBySlot(((Parcel)object).readInt(), ((Parcel)object).readInt(), ((Parcel)object).readInt(), ((Parcel)object).readInt(), ((Parcel)object).readInt(), ((Parcel)object).readInt(), ((Parcel)object).readInt(), ((Parcel)object).readString());
                        parcel.writeNoException();
                        parcel.writeString((String)object);
                        return true;
                    }
                    case 76: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.iccCloseLogicalChannel(((Parcel)object).readInt(), ((Parcel)object).readInt()) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 75: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.iccCloseLogicalChannelBySlot(((Parcel)object).readInt(), ((Parcel)object).readInt()) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 74: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.iccOpenLogicalChannel(((Parcel)object).readInt(), ((Parcel)object).readString(), ((Parcel)object).readString(), ((Parcel)object).readInt());
                        parcel.writeNoException();
                        if (object != null) {
                            parcel.writeInt(1);
                            ((IccOpenLogicalChannelResponse)object).writeToParcel(parcel, 1);
                        } else {
                            parcel.writeInt(0);
                        }
                        return true;
                    }
                    case 73: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.iccOpenLogicalChannelBySlot(((Parcel)object).readInt(), ((Parcel)object).readString(), ((Parcel)object).readString(), ((Parcel)object).readInt());
                        parcel.writeNoException();
                        if (object != null) {
                            parcel.writeInt(1);
                            ((IccOpenLogicalChannelResponse)object).writeToParcel(parcel, 1);
                        } else {
                            parcel.writeInt(0);
                        }
                        return true;
                    }
                    case 72: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.setCellInfoListRate(((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 71: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = ((Parcel)object).readInt();
                        object3 = ICellInfoCallback.Stub.asInterface(((Parcel)object).readStrongBinder());
                        object2 = ((Parcel)object).readString();
                        object = ((Parcel)object).readInt() != 0 ? WorkSource.CREATOR.createFromParcel((Parcel)object) : null;
                        this.requestCellInfoUpdateWithWorkSource(n, (ICellInfoCallback)object3, (String)object2, (WorkSource)object);
                        parcel.writeNoException();
                        return true;
                    }
                    case 70: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.requestCellInfoUpdate(((Parcel)object).readInt(), ICellInfoCallback.Stub.asInterface(((Parcel)object).readStrongBinder()), ((Parcel)object).readString());
                        parcel.writeNoException();
                        return true;
                    }
                    case 69: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getAllCellInfo(((Parcel)object).readString());
                        parcel.writeNoException();
                        parcel.writeTypedList(object);
                        return true;
                    }
                    case 68: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.getLteOnCdmaModeForSubscriber(((Parcel)object).readInt(), ((Parcel)object).readString());
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 67: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.getLteOnCdmaMode(((Parcel)object).readString());
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 66: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.hasIccCardUsingSlotIndex(((Parcel)object).readInt()) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 65: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.hasIccCard() ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 64: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.getVoiceNetworkTypeForSubscriber(((Parcel)object).readInt(), ((Parcel)object).readString());
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 63: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.getDataNetworkTypeForSubscriber(((Parcel)object).readInt(), ((Parcel)object).readString());
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 62: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.getDataNetworkType(((Parcel)object).readString());
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 61: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.getNetworkTypeForSubscriber(((Parcel)object).readInt(), ((Parcel)object).readString());
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 60: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.sendDialerSpecialCode(((Parcel)object).readString(), ((Parcel)object).readString());
                        parcel.writeNoException();
                        return true;
                    }
                    case 59: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object2 = ((Parcel)object).readString();
                        n2 = ((Parcel)object).readInt();
                        string2 = ((Parcel)object).readString();
                        n = ((Parcel)object).readInt();
                        object3 = ((Parcel)object).readString();
                        object = ((Parcel)object).readInt() != 0 ? PendingIntent.CREATOR.createFromParcel((Parcel)object) : null;
                        this.sendVisualVoicemailSmsForSubscriber((String)object2, n2, string2, n, (String)object3, (PendingIntent)object);
                        parcel.writeNoException();
                        return true;
                    }
                    case 58: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getActiveVisualVoicemailSmsFilterSettings(((Parcel)object).readInt());
                        parcel.writeNoException();
                        if (object != null) {
                            parcel.writeInt(1);
                            ((VisualVoicemailSmsFilterSettings)object).writeToParcel(parcel, 1);
                        } else {
                            parcel.writeInt(0);
                        }
                        return true;
                    }
                    case 57: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getVisualVoicemailSmsFilterSettings(((Parcel)object).readString(), ((Parcel)object).readInt());
                        parcel.writeNoException();
                        if (object != null) {
                            parcel.writeInt(1);
                            ((VisualVoicemailSmsFilterSettings)object).writeToParcel(parcel, 1);
                        } else {
                            parcel.writeInt(0);
                        }
                        return true;
                    }
                    case 56: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.disableVisualVoicemailSmsFilter(((Parcel)object).readString(), ((Parcel)object).readInt());
                        return true;
                    }
                    case 55: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object3 = ((Parcel)object).readString();
                        n = ((Parcel)object).readInt();
                        object = ((Parcel)object).readInt() != 0 ? VisualVoicemailSmsFilterSettings.CREATOR.createFromParcel((Parcel)object) : null;
                        this.enableVisualVoicemailSmsFilter((String)object3, n, (VisualVoicemailSmsFilterSettings)object);
                        parcel.writeNoException();
                        return true;
                    }
                    case 54: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getVisualVoicemailPackageName(((Parcel)object).readString(), ((Parcel)object).readInt());
                        parcel.writeNoException();
                        parcel.writeString((String)object);
                        return true;
                    }
                    case 53: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getVisualVoicemailSettings(((Parcel)object).readString(), ((Parcel)object).readInt());
                        parcel.writeNoException();
                        if (object != null) {
                            parcel.writeInt(1);
                            ((Bundle)object).writeToParcel(parcel, 1);
                        } else {
                            parcel.writeInt(0);
                        }
                        return true;
                    }
                    case 52: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.isConcurrentVoiceAndDataAllowed(((Parcel)object).readInt()) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 51: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.getVoiceMessageCountForSubscriber(((Parcel)object).readInt(), ((Parcel)object).readString());
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 50: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.getDataActivationState(((Parcel)object).readInt(), ((Parcel)object).readString());
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 49: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.getVoiceActivationState(((Parcel)object).readInt(), ((Parcel)object).readString());
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 48: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.setDataActivationState(((Parcel)object).readInt(), ((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 47: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.setVoiceActivationState(((Parcel)object).readInt(), ((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 46: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.setVoiceMailNumber(((Parcel)object).readInt(), ((Parcel)object).readString(), ((Parcel)object).readString()) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 45: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.needsOtaServiceProvisioning() ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 44: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getCdmaEriTextForSubscriber(((Parcel)object).readInt(), ((Parcel)object).readString());
                        parcel.writeNoException();
                        parcel.writeString((String)object);
                        return true;
                    }
                    case 43: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getCdmaEriText(((Parcel)object).readString());
                        parcel.writeNoException();
                        parcel.writeString((String)object);
                        return true;
                    }
                    case 42: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.getCdmaEriIconModeForSubscriber(((Parcel)object).readInt(), ((Parcel)object).readString());
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 41: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.getCdmaEriIconMode(((Parcel)object).readString());
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 40: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.getCdmaEriIconIndexForSubscriber(((Parcel)object).readInt(), ((Parcel)object).readString());
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 39: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.getCdmaEriIconIndex(((Parcel)object).readString());
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 38: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.getActivePhoneTypeForSlot(((Parcel)object).readInt());
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 37: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.getActivePhoneType();
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 36: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.getDataState();
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 35: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.getDataActivity();
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 34: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.getCallStateForSlot(((Parcel)object).readInt());
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 33: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.getCallState();
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 32: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getNeighboringCellInfo(((Parcel)object).readString());
                        parcel.writeNoException();
                        parcel.writeTypedList(object);
                        return true;
                    }
                    case 31: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getNetworkCountryIsoForPhone(((Parcel)object).readInt());
                        parcel.writeNoException();
                        parcel.writeString((String)object);
                        return true;
                    }
                    case 30: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getCellLocation(((Parcel)object).readString());
                        parcel.writeNoException();
                        if (object != null) {
                            parcel.writeInt(1);
                            ((Bundle)object).writeToParcel(parcel, 1);
                        } else {
                            parcel.writeInt(0);
                        }
                        return true;
                    }
                    case 29: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.isDataConnectivityPossible(((Parcel)object).readInt()) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 28: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.disableDataConnectivity() ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 27: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.enableDataConnectivity() ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 26: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.disableLocationUpdatesForSubscriber(((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 25: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.disableLocationUpdates();
                        parcel.writeNoException();
                        return true;
                    }
                    case 24: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.enableLocationUpdatesForSubscriber(((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 23: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.enableLocationUpdates();
                        parcel.writeNoException();
                        return true;
                    }
                    case 22: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.updateServiceLocationForSubscriber(((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 21: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.updateServiceLocation();
                        parcel.writeNoException();
                        return true;
                    }
                    case 20: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        bl28 = bl25;
                        if (((Parcel)object).readInt() != 0) {
                            bl28 = true;
                        }
                        n = this.setRadioPower(bl28) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 19: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = ((Parcel)object).readInt();
                        bl28 = bl26;
                        if (((Parcel)object).readInt() != 0) {
                            bl28 = true;
                        }
                        n = this.setRadioForSubscriber(n, bl28) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 18: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        bl28 = bl27;
                        if (((Parcel)object).readInt() != 0) {
                            bl28 = true;
                        }
                        n = this.setRadio(bl28) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 17: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.toggleRadioOnOffForSubscriber(((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 16: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.toggleRadioOnOff();
                        parcel.writeNoException();
                        return true;
                    }
                    case 15: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.handlePinMmiForSubscriber(((Parcel)object).readInt(), ((Parcel)object).readString()) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 14: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = ((Parcel)object).readInt();
                        object3 = ((Parcel)object).readString();
                        object = ((Parcel)object).readInt() != 0 ? ResultReceiver.CREATOR.createFromParcel((Parcel)object) : null;
                        this.handleUssdRequest(n, (String)object3, (ResultReceiver)object);
                        parcel.writeNoException();
                        return true;
                    }
                    case 13: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.handlePinMmi(((Parcel)object).readString()) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 12: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.supplyPukReportResultForSubscriber(((Parcel)object).readInt(), ((Parcel)object).readString(), ((Parcel)object).readString());
                        parcel.writeNoException();
                        parcel.writeIntArray((int[])object);
                        return true;
                    }
                    case 11: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.supplyPukReportResult(((Parcel)object).readString(), ((Parcel)object).readString());
                        parcel.writeNoException();
                        parcel.writeIntArray((int[])object);
                        return true;
                    }
                    case 10: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.supplyPinReportResultForSubscriber(((Parcel)object).readInt(), ((Parcel)object).readString());
                        parcel.writeNoException();
                        parcel.writeIntArray((int[])object);
                        return true;
                    }
                    case 9: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.supplyPinReportResult(((Parcel)object).readString());
                        parcel.writeNoException();
                        parcel.writeIntArray((int[])object);
                        return true;
                    }
                    case 8: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.supplyPukForSubscriber(((Parcel)object).readInt(), ((Parcel)object).readString(), ((Parcel)object).readString()) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 7: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.supplyPuk(((Parcel)object).readString(), ((Parcel)object).readString()) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 6: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.supplyPinForSubscriber(((Parcel)object).readInt(), ((Parcel)object).readString()) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 5: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.supplyPin(((Parcel)object).readString()) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 4: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.isRadioOnForSubscriber(((Parcel)object).readInt(), ((Parcel)object).readString()) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 3: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.isRadioOn(((Parcel)object).readString()) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 2: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.call(((Parcel)object).readString(), ((Parcel)object).readString());
                        parcel.writeNoException();
                        return true;
                    }
                    case 1: 
                }
                ((Parcel)object).enforceInterface(DESCRIPTOR);
                this.dial(((Parcel)object).readString());
                parcel.writeNoException();
                return true;
            }
            parcel.writeString(DESCRIPTOR);
            return true;
        }

        private static class Proxy
        implements ITelephony {
            public static ITelephony sDefaultImpl;
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override
            public IBinder asBinder() {
                return this.mRemote;
            }

            @Override
            public void cacheMmTelCapabilityProvisioning(int n, int n2, int n3, boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                parcel.writeInt(n);
                parcel.writeInt(n2);
                parcel.writeInt(n3);
                int n4 = bl ? 1 : 0;
                try {
                    parcel.writeInt(n4);
                    if (!this.mRemote.transact(231, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().cacheMmTelCapabilityProvisioning(n, n2, n3, bl);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void call(String string2, String string3) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    parcel.writeString(string3);
                    if (!this.mRemote.transact(2, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().call(string2, string3);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public boolean canChangeDtmfToneLength(int n, String string2) throws RemoteException {
                Parcel parcel;
                boolean bl;
                Parcel parcel2;
                block5 : {
                    IBinder iBinder;
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel2.writeInt(n);
                        parcel2.writeString(string2);
                        iBinder = this.mRemote;
                        bl = false;
                    }
                    catch (Throwable throwable) {
                        parcel.recycle();
                        parcel2.recycle();
                        throw throwable;
                    }
                    if (iBinder.transact(133, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().canChangeDtmfToneLength(n, string2);
                    parcel.recycle();
                    parcel2.recycle();
                    return bl;
                }
                parcel.readException();
                n = parcel.readInt();
                if (n != 0) {
                    bl = true;
                }
                parcel.recycle();
                parcel2.recycle();
                return bl;
            }

            @Override
            public void carrierActionReportDefaultNetworkStatus(int n, boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                parcel.writeInt(n);
                int n2 = bl ? 1 : 0;
                try {
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(173, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().carrierActionReportDefaultNetworkStatus(n, bl);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void carrierActionResetAll(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(174, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().carrierActionResetAll(n);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void carrierActionSetMeteredApnsEnabled(int n, boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                parcel.writeInt(n);
                int n2 = bl ? 1 : 0;
                try {
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(171, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().carrierActionSetMeteredApnsEnabled(n, bl);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void carrierActionSetRadioEnabled(int n, boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                parcel.writeInt(n);
                int n2 = bl ? 1 : 0;
                try {
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(172, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().carrierActionSetRadioEnabled(n, bl);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public int checkCarrierPrivilegesForPackage(int n, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(117, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        n = Stub.getDefaultImpl().checkCarrierPrivilegesForPackage(n, string2);
                        return n;
                    }
                    parcel2.readException();
                    n = parcel2.readInt();
                    return n;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public int checkCarrierPrivilegesForPackageAnyPhone(String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(118, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        int n = Stub.getDefaultImpl().checkCarrierPrivilegesForPackageAnyPhone(string2);
                        return n;
                    }
                    parcel2.readException();
                    int n = parcel2.readInt();
                    return n;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void dial(String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(1, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().dial(string2);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public boolean disableDataConnectivity() throws RemoteException {
                boolean bl;
                Parcel parcel;
                Parcel parcel2;
                block5 : {
                    IBinder iBinder;
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        iBinder = this.mRemote;
                        bl = false;
                    }
                    catch (Throwable throwable) {
                        parcel.recycle();
                        parcel2.recycle();
                        throw throwable;
                    }
                    if (iBinder.transact(28, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().disableDataConnectivity();
                    parcel.recycle();
                    parcel2.recycle();
                    return bl;
                }
                parcel.readException();
                int n = parcel.readInt();
                if (n != 0) {
                    bl = true;
                }
                parcel.recycle();
                parcel2.recycle();
                return bl;
            }

            @Override
            public void disableIms(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(92, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().disableIms(n);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void disableLocationUpdates() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(25, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().disableLocationUpdates();
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void disableLocationUpdatesForSubscriber(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(26, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().disableLocationUpdatesForSubscriber(n);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void disableVisualVoicemailSmsFilter(String string2, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(56, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().disableVisualVoicemailSmsFilter(string2, n);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public boolean doesSwitchMultiSimConfigTriggerReboot(int n, String string2) throws RemoteException {
                Parcel parcel;
                boolean bl;
                Parcel parcel2;
                block5 : {
                    IBinder iBinder;
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel2.writeInt(n);
                        parcel2.writeString(string2);
                        iBinder = this.mRemote;
                        bl = false;
                    }
                    catch (Throwable throwable) {
                        parcel.recycle();
                        parcel2.recycle();
                        throw throwable;
                    }
                    if (iBinder.transact(242, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().doesSwitchMultiSimConfigTriggerReboot(n, string2);
                    parcel.recycle();
                    parcel2.recycle();
                    return bl;
                }
                parcel.readException();
                n = parcel.readInt();
                if (n != 0) {
                    bl = true;
                }
                parcel.recycle();
                parcel2.recycle();
                return bl;
            }

            @Override
            public boolean enableDataConnectivity() throws RemoteException {
                boolean bl;
                Parcel parcel;
                Parcel parcel2;
                block5 : {
                    IBinder iBinder;
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        iBinder = this.mRemote;
                        bl = false;
                    }
                    catch (Throwable throwable) {
                        parcel.recycle();
                        parcel2.recycle();
                        throw throwable;
                    }
                    if (iBinder.transact(27, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().enableDataConnectivity();
                    parcel.recycle();
                    parcel2.recycle();
                    return bl;
                }
                parcel.readException();
                int n = parcel.readInt();
                if (n != 0) {
                    bl = true;
                }
                parcel.recycle();
                parcel2.recycle();
                return bl;
            }

            @Override
            public void enableIms(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(91, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().enableIms(n);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void enableLocationUpdates() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(23, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().enableLocationUpdates();
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void enableLocationUpdatesForSubscriber(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(24, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().enableLocationUpdatesForSubscriber(n);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public boolean enableModemForSlot(int n, boolean bl) throws RemoteException {
                Parcel parcel;
                boolean bl2;
                Parcel parcel2;
                block4 : {
                    int n2;
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel2.writeInt(n);
                        bl2 = true;
                        n2 = bl ? 1 : 0;
                    }
                    catch (Throwable throwable) {
                        parcel.recycle();
                        parcel2.recycle();
                        throw throwable;
                    }
                    parcel2.writeInt(n2);
                    if (this.mRemote.transact(238, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block4;
                    bl = Stub.getDefaultImpl().enableModemForSlot(n, bl);
                    parcel.recycle();
                    parcel2.recycle();
                    return bl;
                }
                parcel.readException();
                n = parcel.readInt();
                bl = n != 0 ? bl2 : false;
                parcel.recycle();
                parcel2.recycle();
                return bl;
            }

            @Override
            public void enableVideoCalling(boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                int n = bl ? 1 : 0;
                try {
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(131, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().enableVideoCalling(bl);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void enableVisualVoicemailSmsFilter(String string2, int n, VisualVoicemailSmsFilterSettings visualVoicemailSmsFilterSettings) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    parcel.writeInt(n);
                    if (visualVoicemailSmsFilterSettings != null) {
                        parcel.writeInt(1);
                        visualVoicemailSmsFilterSettings.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(55, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().enableVisualVoicemailSmsFilter(string2, n, visualVoicemailSmsFilterSettings);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void enqueueSmsPickResult(String string2, IIntegerConsumer iIntegerConsumer) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    IBinder iBinder = iIntegerConsumer != null ? iIntegerConsumer.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (this.mRemote.transact(248, parcel, null, 1)) return;
                    if (Stub.getDefaultImpl() == null) return;
                    Stub.getDefaultImpl().enqueueSmsPickResult(string2, iIntegerConsumer);
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void factoryReset(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(150, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().factoryReset(n);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public int getActivePhoneType() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(37, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        int n = Stub.getDefaultImpl().getActivePhoneType();
                        return n;
                    }
                    parcel2.readException();
                    int n = parcel2.readInt();
                    return n;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public int getActivePhoneTypeForSlot(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(38, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        n = Stub.getDefaultImpl().getActivePhoneTypeForSlot(n);
                        return n;
                    }
                    parcel2.readException();
                    n = parcel2.readInt();
                    return n;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public VisualVoicemailSmsFilterSettings getActiveVisualVoicemailSmsFilterSettings(int n) throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                block3 : {
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    try {
                        parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel.writeInt(n);
                        if (this.mRemote.transact(58, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block3;
                        VisualVoicemailSmsFilterSettings visualVoicemailSmsFilterSettings = Stub.getDefaultImpl().getActiveVisualVoicemailSmsFilterSettings(n);
                        parcel2.recycle();
                        parcel.recycle();
                        return visualVoicemailSmsFilterSettings;
                    }
                    catch (Throwable throwable) {
                        parcel2.recycle();
                        parcel.recycle();
                        throw throwable;
                    }
                }
                parcel2.readException();
                VisualVoicemailSmsFilterSettings visualVoicemailSmsFilterSettings = parcel2.readInt() != 0 ? VisualVoicemailSmsFilterSettings.CREATOR.createFromParcel(parcel2) : null;
                parcel2.recycle();
                parcel.recycle();
                return visualVoicemailSmsFilterSettings;
            }

            @Override
            public String getAidForAppType(int n, int n2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(160, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        String string2 = Stub.getDefaultImpl().getAidForAppType(n, n2);
                        return string2;
                    }
                    parcel2.readException();
                    String string3 = parcel2.readString();
                    return string3;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public List<CellInfo> getAllCellInfo(String arrayList) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString((String)((Object)arrayList));
                    if (!this.mRemote.transact(69, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        arrayList = Stub.getDefaultImpl().getAllCellInfo((String)((Object)arrayList));
                        return arrayList;
                    }
                    parcel2.readException();
                    arrayList = parcel2.createTypedArrayList(CellInfo.CREATOR);
                    return arrayList;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public CarrierRestrictionRules getAllowedCarriers() throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                block3 : {
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        if (this.mRemote.transact(165, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block3;
                        CarrierRestrictionRules carrierRestrictionRules = Stub.getDefaultImpl().getAllowedCarriers();
                        parcel.recycle();
                        parcel2.recycle();
                        return carrierRestrictionRules;
                    }
                    catch (Throwable throwable) {
                        parcel.recycle();
                        parcel2.recycle();
                        throw throwable;
                    }
                }
                parcel.readException();
                CarrierRestrictionRules carrierRestrictionRules = parcel.readInt() != 0 ? CarrierRestrictionRules.CREATOR.createFromParcel(parcel) : null;
                parcel.recycle();
                parcel2.recycle();
                return carrierRestrictionRules;
            }

            @Override
            public int getCalculatedPreferredNetworkType(String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(88, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        int n = Stub.getDefaultImpl().getCalculatedPreferredNetworkType(string2);
                        return n;
                    }
                    parcel2.readException();
                    int n = parcel2.readInt();
                    return n;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public int getCallState() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(33, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        int n = Stub.getDefaultImpl().getCallState();
                        return n;
                    }
                    parcel2.readException();
                    int n = parcel2.readInt();
                    return n;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public int getCallStateForSlot(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(34, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        n = Stub.getDefaultImpl().getCallStateForSlot(n);
                        return n;
                    }
                    parcel2.readException();
                    n = parcel2.readInt();
                    return n;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public int getCardIdForDefaultEuicc(int n, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(182, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        n = Stub.getDefaultImpl().getCardIdForDefaultEuicc(n, string2);
                        return n;
                    }
                    parcel2.readException();
                    n = parcel2.readInt();
                    return n;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public int getCarrierIdFromMccMnc(int n, String string2, boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                parcel.writeInt(n);
                parcel.writeString(string2);
                int n2 = bl ? 1 : 0;
                try {
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(170, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        n = Stub.getDefaultImpl().getCarrierIdFromMccMnc(n, string2, bl);
                        return n;
                    }
                    parcel2.readException();
                    n = parcel2.readInt();
                    return n;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public int getCarrierIdListVersion(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(193, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        n = Stub.getDefaultImpl().getCarrierIdListVersion(n);
                        return n;
                    }
                    parcel2.readException();
                    n = parcel2.readInt();
                    return n;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public List<String> getCarrierPackageNamesForIntentAndPhone(Intent object, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (object != null) {
                        parcel.writeInt(1);
                        ((Intent)object).writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(119, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        object = Stub.getDefaultImpl().getCarrierPackageNamesForIntentAndPhone((Intent)object, n);
                        return object;
                    }
                    parcel2.readException();
                    object = parcel2.createStringArrayList();
                    return object;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public int getCarrierPrivilegeStatus(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(115, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        n = Stub.getDefaultImpl().getCarrierPrivilegeStatus(n);
                        return n;
                    }
                    parcel2.readException();
                    n = parcel2.readInt();
                    return n;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public int getCarrierPrivilegeStatusForUid(int n, int n2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(116, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        n = Stub.getDefaultImpl().getCarrierPrivilegeStatusForUid(n, n2);
                        return n;
                    }
                    parcel2.readException();
                    n = parcel2.readInt();
                    return n;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public int getCdmaEriIconIndex(String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(39, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        int n = Stub.getDefaultImpl().getCdmaEriIconIndex(string2);
                        return n;
                    }
                    parcel2.readException();
                    int n = parcel2.readInt();
                    return n;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public int getCdmaEriIconIndexForSubscriber(int n, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(40, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        n = Stub.getDefaultImpl().getCdmaEriIconIndexForSubscriber(n, string2);
                        return n;
                    }
                    parcel2.readException();
                    n = parcel2.readInt();
                    return n;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public int getCdmaEriIconMode(String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(41, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        int n = Stub.getDefaultImpl().getCdmaEriIconMode(string2);
                        return n;
                    }
                    parcel2.readException();
                    int n = parcel2.readInt();
                    return n;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public int getCdmaEriIconModeForSubscriber(int n, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(42, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        n = Stub.getDefaultImpl().getCdmaEriIconModeForSubscriber(n, string2);
                        return n;
                    }
                    parcel2.readException();
                    n = parcel2.readInt();
                    return n;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public String getCdmaEriText(String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(43, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        string2 = Stub.getDefaultImpl().getCdmaEriText(string2);
                        return string2;
                    }
                    parcel2.readException();
                    string2 = parcel2.readString();
                    return string2;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public String getCdmaEriTextForSubscriber(int n, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(44, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        string2 = Stub.getDefaultImpl().getCdmaEriTextForSubscriber(n, string2);
                        return string2;
                    }
                    parcel2.readException();
                    string2 = parcel2.readString();
                    return string2;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public String getCdmaMdn(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(112, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        String string2 = Stub.getDefaultImpl().getCdmaMdn(n);
                        return string2;
                    }
                    parcel2.readException();
                    String string3 = parcel2.readString();
                    return string3;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public String getCdmaMin(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(113, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        String string2 = Stub.getDefaultImpl().getCdmaMin(n);
                        return string2;
                    }
                    parcel2.readException();
                    String string3 = parcel2.readString();
                    return string3;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public String getCdmaPrlVersion(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(162, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        String string2 = Stub.getDefaultImpl().getCdmaPrlVersion(n);
                        return string2;
                    }
                    parcel2.readException();
                    String string3 = parcel2.readString();
                    return string3;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public int getCdmaRoamingMode(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(189, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        n = Stub.getDefaultImpl().getCdmaRoamingMode(n);
                        return n;
                    }
                    parcel2.readException();
                    n = parcel2.readInt();
                    return n;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public Bundle getCellLocation(String object) throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                block3 : {
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    try {
                        parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel.writeString((String)object);
                        if (this.mRemote.transact(30, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block3;
                        object = Stub.getDefaultImpl().getCellLocation((String)object);
                        parcel2.recycle();
                        parcel.recycle();
                        return object;
                    }
                    catch (Throwable throwable) {
                        parcel2.recycle();
                        parcel.recycle();
                        throw throwable;
                    }
                }
                parcel2.readException();
                object = parcel2.readInt() != 0 ? Bundle.CREATOR.createFromParcel(parcel2) : null;
                parcel2.recycle();
                parcel.recycle();
                return object;
            }

            @Override
            public CellNetworkScanResult getCellNetworkScanResults(int n, String object) throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                block3 : {
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel2.writeInt(n);
                        parcel2.writeString((String)object);
                        if (this.mRemote.transact(100, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block3;
                        object = Stub.getDefaultImpl().getCellNetworkScanResults(n, (String)object);
                        parcel.recycle();
                        parcel2.recycle();
                        return object;
                    }
                    catch (Throwable throwable) {
                        parcel.recycle();
                        parcel2.recycle();
                        throw throwable;
                    }
                }
                parcel.readException();
                object = parcel.readInt() != 0 ? CellNetworkScanResult.CREATOR.createFromParcel(parcel) : null;
                parcel.recycle();
                parcel2.recycle();
                return object;
            }

            @Override
            public List<String> getCertsFromCarrierPrivilegeAccessRules(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(225, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        List<String> list = Stub.getDefaultImpl().getCertsFromCarrierPrivilegeAccessRules(n);
                        return list;
                    }
                    parcel2.readException();
                    ArrayList<String> arrayList = parcel2.createStringArrayList();
                    return arrayList;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public List<ClientRequestStats> getClientRequestStats(String arrayList, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString((String)((Object)arrayList));
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(177, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        arrayList = Stub.getDefaultImpl().getClientRequestStats((String)((Object)arrayList), n);
                        return arrayList;
                    }
                    parcel2.readException();
                    arrayList = parcel2.createTypedArrayList(ClientRequestStats.CREATOR);
                    return arrayList;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public int getDataActivationState(int n, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(50, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        n = Stub.getDefaultImpl().getDataActivationState(n, string2);
                        return n;
                    }
                    parcel2.readException();
                    n = parcel2.readInt();
                    return n;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public int getDataActivity() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(35, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        int n = Stub.getDefaultImpl().getDataActivity();
                        return n;
                    }
                    parcel2.readException();
                    int n = parcel2.readInt();
                    return n;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public boolean getDataEnabled(int n) throws RemoteException {
                Parcel parcel;
                boolean bl;
                Parcel parcel2;
                block5 : {
                    IBinder iBinder;
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    try {
                        parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel.writeInt(n);
                        iBinder = this.mRemote;
                        bl = false;
                    }
                    catch (Throwable throwable) {
                        parcel2.recycle();
                        parcel.recycle();
                        throw throwable;
                    }
                    if (iBinder.transact(106, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().getDataEnabled(n);
                    parcel2.recycle();
                    parcel.recycle();
                    return bl;
                }
                parcel2.readException();
                n = parcel2.readInt();
                if (n != 0) {
                    bl = true;
                }
                parcel2.recycle();
                parcel.recycle();
                return bl;
            }

            @Override
            public int getDataNetworkType(String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(62, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        int n = Stub.getDefaultImpl().getDataNetworkType(string2);
                        return n;
                    }
                    parcel2.readException();
                    int n = parcel2.readInt();
                    return n;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public int getDataNetworkTypeForSubscriber(int n, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(63, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        n = Stub.getDefaultImpl().getDataNetworkTypeForSubscriber(n, string2);
                        return n;
                    }
                    parcel2.readException();
                    n = parcel2.readInt();
                    return n;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public int getDataState() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(36, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        int n = Stub.getDefaultImpl().getDataState();
                        return n;
                    }
                    parcel2.readException();
                    int n = parcel2.readInt();
                    return n;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public String getDefaultSmsApp(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(199, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        String string2 = Stub.getDefaultImpl().getDefaultSmsApp(n);
                        return string2;
                    }
                    parcel2.readException();
                    String string3 = parcel2.readString();
                    return string3;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public String getDeviceId(String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(142, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        string2 = Stub.getDefaultImpl().getDeviceId(string2);
                        return string2;
                    }
                    parcel2.readException();
                    string2 = parcel2.readString();
                    return string2;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public String getDeviceSoftwareVersionForSlot(int n, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(147, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        string2 = Stub.getDefaultImpl().getDeviceSoftwareVersionForSlot(n, string2);
                        return string2;
                    }
                    parcel2.readException();
                    string2 = parcel2.readString();
                    return string2;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public boolean getEmergencyCallbackMode(int n) throws RemoteException {
                Parcel parcel;
                boolean bl;
                Parcel parcel2;
                block5 : {
                    IBinder iBinder;
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    try {
                        parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel.writeInt(n);
                        iBinder = this.mRemote;
                        bl = false;
                    }
                    catch (Throwable throwable) {
                        parcel2.recycle();
                        parcel.recycle();
                        throw throwable;
                    }
                    if (iBinder.transact(180, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().getEmergencyCallbackMode(n);
                    parcel2.recycle();
                    parcel.recycle();
                    return bl;
                }
                parcel2.readException();
                n = parcel2.readInt();
                if (n != 0) {
                    bl = true;
                }
                parcel2.recycle();
                parcel.recycle();
                return bl;
            }

            @Override
            public Map getEmergencyNumberList(String object) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString((String)object);
                    if (!this.mRemote.transact(223, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        object = Stub.getDefaultImpl().getEmergencyNumberList((String)object);
                        return object;
                    }
                    parcel2.readException();
                    object = parcel2.readHashMap(this.getClass().getClassLoader());
                    return object;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public List<String> getEmergencyNumberListTestMode() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(237, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        List<String> list = Stub.getDefaultImpl().getEmergencyNumberListTestMode();
                        return list;
                    }
                    parcel2.readException();
                    ArrayList<String> arrayList = parcel2.createStringArrayList();
                    return arrayList;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public String getEsn(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(161, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        String string2 = Stub.getDefaultImpl().getEsn(n);
                        return string2;
                    }
                    parcel2.readException();
                    String string3 = parcel2.readString();
                    return string3;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public String[] getForbiddenPlmns(int n, int n2, String arrstring) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    parcel.writeString((String)arrstring);
                    if (!this.mRemote.transact(179, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        arrstring = Stub.getDefaultImpl().getForbiddenPlmns(n, n2, (String)arrstring);
                        return arrstring;
                    }
                    parcel2.readException();
                    arrstring = parcel2.createStringArray();
                    return arrstring;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public String getImeiForSlot(int n, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(143, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        string2 = Stub.getDefaultImpl().getImeiForSlot(n, string2);
                        return string2;
                    }
                    parcel2.readException();
                    string2 = parcel2.readString();
                    return string2;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public IImsConfig getImsConfig(int n, int n2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(96, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        IImsConfig iImsConfig = Stub.getDefaultImpl().getImsConfig(n, n2);
                        return iImsConfig;
                    }
                    parcel2.readException();
                    IImsConfig iImsConfig = IImsConfig.Stub.asInterface(parcel2.readStrongBinder());
                    return iImsConfig;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public int getImsProvisioningInt(int n, int n2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(232, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        n = Stub.getDefaultImpl().getImsProvisioningInt(n, n2);
                        return n;
                    }
                    parcel2.readException();
                    n = parcel2.readInt();
                    return n;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public boolean getImsProvisioningStatusForCapability(int n, int n2, int n3) throws RemoteException {
                boolean bl;
                Parcel parcel;
                Parcel parcel2;
                block5 : {
                    IBinder iBinder;
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    try {
                        parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel.writeInt(n);
                        parcel.writeInt(n2);
                        parcel.writeInt(n3);
                        iBinder = this.mRemote;
                        bl = false;
                    }
                    catch (Throwable throwable) {
                        parcel2.recycle();
                        parcel.recycle();
                        throw throwable;
                    }
                    if (iBinder.transact(229, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().getImsProvisioningStatusForCapability(n, n2, n3);
                    parcel2.recycle();
                    parcel.recycle();
                    return bl;
                }
                parcel2.readException();
                n = parcel2.readInt();
                if (n != 0) {
                    bl = true;
                }
                parcel2.recycle();
                parcel.recycle();
                return bl;
            }

            @Override
            public String getImsProvisioningString(int n, int n2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(233, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        String string2 = Stub.getDefaultImpl().getImsProvisioningString(n, n2);
                        return string2;
                    }
                    parcel2.readException();
                    String string3 = parcel2.readString();
                    return string3;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public int getImsRegTechnologyForMmTel(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(141, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        n = Stub.getDefaultImpl().getImsRegTechnologyForMmTel(n);
                        return n;
                    }
                    parcel2.readException();
                    n = parcel2.readInt();
                    return n;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public IImsRegistration getImsRegistration(int n, int n2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(95, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        IImsRegistration iImsRegistration = Stub.getDefaultImpl().getImsRegistration(n, n2);
                        return iImsRegistration;
                    }
                    parcel2.readException();
                    IImsRegistration iImsRegistration = IImsRegistration.Stub.asInterface(parcel2.readStrongBinder());
                    return iImsRegistration;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public String getImsService(int n, boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                parcel.writeInt(n);
                int n2 = bl ? 1 : 0;
                try {
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(98, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        String string2 = Stub.getDefaultImpl().getImsService(n, bl);
                        return string2;
                    }
                    parcel2.readException();
                    String string3 = parcel2.readString();
                    return string3;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            @Override
            public String getLine1AlphaTagForDisplay(int n, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(122, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        string2 = Stub.getDefaultImpl().getLine1AlphaTagForDisplay(n, string2);
                        return string2;
                    }
                    parcel2.readException();
                    string2 = parcel2.readString();
                    return string2;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public String getLine1NumberForDisplay(int n, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(121, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        string2 = Stub.getDefaultImpl().getLine1NumberForDisplay(n, string2);
                        return string2;
                    }
                    parcel2.readException();
                    string2 = parcel2.readString();
                    return string2;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public int getLteOnCdmaMode(String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(67, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        int n = Stub.getDefaultImpl().getLteOnCdmaMode(string2);
                        return n;
                    }
                    parcel2.readException();
                    int n = parcel2.readInt();
                    return n;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public int getLteOnCdmaModeForSubscriber(int n, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(68, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        n = Stub.getDefaultImpl().getLteOnCdmaModeForSubscriber(n, string2);
                        return n;
                    }
                    parcel2.readException();
                    n = parcel2.readInt();
                    return n;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public String getManufacturerCodeForSlot(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(146, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        String string2 = Stub.getDefaultImpl().getManufacturerCodeForSlot(n);
                        return string2;
                    }
                    parcel2.readException();
                    String string3 = parcel2.readString();
                    return string3;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public String getMeidForSlot(int n, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(145, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        string2 = Stub.getDefaultImpl().getMeidForSlot(n, string2);
                        return string2;
                    }
                    parcel2.readException();
                    string2 = parcel2.readString();
                    return string2;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public String[] getMergedSubscriberIds(int n, String arrstring) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeString((String)arrstring);
                    if (!this.mRemote.transact(123, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        arrstring = Stub.getDefaultImpl().getMergedSubscriberIds(n, (String)arrstring);
                        return arrstring;
                    }
                    parcel2.readException();
                    arrstring = parcel2.createStringArray();
                    return arrstring;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public IImsMmTelFeature getMmTelFeatureAndListen(int n, IImsServiceFeatureCallback iInterface) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    IBinder iBinder = iInterface != null ? iInterface.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (!this.mRemote.transact(93, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        IImsMmTelFeature iImsMmTelFeature = Stub.getDefaultImpl().getMmTelFeatureAndListen(n, (IImsServiceFeatureCallback)iInterface);
                        return iImsMmTelFeature;
                    }
                    parcel2.readException();
                    IImsMmTelFeature iImsMmTelFeature = IImsMmTelFeature.Stub.asInterface(parcel2.readStrongBinder());
                    return iImsMmTelFeature;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public String getMmsUAProfUrl(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(250, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        String string2 = Stub.getDefaultImpl().getMmsUAProfUrl(n);
                        return string2;
                    }
                    parcel2.readException();
                    String string3 = parcel2.readString();
                    return string3;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public String getMmsUserAgent(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(249, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        String string2 = Stub.getDefaultImpl().getMmsUserAgent(n);
                        return string2;
                    }
                    parcel2.readException();
                    String string3 = parcel2.readString();
                    return string3;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public List<NeighboringCellInfo> getNeighboringCellInfo(String arrayList) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString((String)((Object)arrayList));
                    if (!this.mRemote.transact(32, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        arrayList = Stub.getDefaultImpl().getNeighboringCellInfo((String)((Object)arrayList));
                        return arrayList;
                    }
                    parcel2.readException();
                    arrayList = parcel2.createTypedArrayList(NeighboringCellInfo.CREATOR);
                    return arrayList;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public String getNetworkCountryIsoForPhone(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(31, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        String string2 = Stub.getDefaultImpl().getNetworkCountryIsoForPhone(n);
                        return string2;
                    }
                    parcel2.readException();
                    String string3 = parcel2.readString();
                    return string3;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public int getNetworkSelectionMode(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(196, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        n = Stub.getDefaultImpl().getNetworkSelectionMode(n);
                        return n;
                    }
                    parcel2.readException();
                    n = parcel2.readInt();
                    return n;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public int getNetworkTypeForSubscriber(int n, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(61, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        n = Stub.getDefaultImpl().getNetworkTypeForSubscriber(n, string2);
                        return n;
                    }
                    parcel2.readException();
                    n = parcel2.readInt();
                    return n;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public int getNumberOfModemsWithSimultaneousDataConnections(int n, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(195, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        n = Stub.getDefaultImpl().getNumberOfModemsWithSimultaneousDataConnections(n, string2);
                        return n;
                    }
                    parcel2.readException();
                    n = parcel2.readInt();
                    return n;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public List<String> getPackagesWithCarrierPrivileges(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(158, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        List<String> list = Stub.getDefaultImpl().getPackagesWithCarrierPrivileges(n);
                        return list;
                    }
                    parcel2.readException();
                    ArrayList<String> arrayList = parcel2.createStringArrayList();
                    return arrayList;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public List<String> getPackagesWithCarrierPrivilegesForAllPhones() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(159, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        List<String> list = Stub.getDefaultImpl().getPackagesWithCarrierPrivilegesForAllPhones();
                        return list;
                    }
                    parcel2.readException();
                    ArrayList<String> arrayList = parcel2.createStringArrayList();
                    return arrayList;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public String[] getPcscfAddress(String arrstring, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString((String)arrstring);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(110, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        arrstring = Stub.getDefaultImpl().getPcscfAddress((String)arrstring, string2);
                        return arrstring;
                    }
                    parcel2.readException();
                    arrstring = parcel2.createStringArray();
                    return arrstring;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public PhoneAccountHandle getPhoneAccountHandleForSubscriptionId(int n) throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                block3 : {
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    try {
                        parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel.writeInt(n);
                        if (this.mRemote.transact(149, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block3;
                        PhoneAccountHandle phoneAccountHandle = Stub.getDefaultImpl().getPhoneAccountHandleForSubscriptionId(n);
                        parcel2.recycle();
                        parcel.recycle();
                        return phoneAccountHandle;
                    }
                    catch (Throwable throwable) {
                        parcel2.recycle();
                        parcel.recycle();
                        throw throwable;
                    }
                }
                parcel2.readException();
                PhoneAccountHandle phoneAccountHandle = parcel2.readInt() != 0 ? PhoneAccountHandle.CREATOR.createFromParcel(parcel2) : null;
                parcel2.recycle();
                parcel.recycle();
                return phoneAccountHandle;
            }

            @Override
            public int getPreferredNetworkType(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(89, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        n = Stub.getDefaultImpl().getPreferredNetworkType(n);
                        return n;
                    }
                    parcel2.readException();
                    n = parcel2.readInt();
                    return n;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public int getRadioAccessFamily(int n, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(130, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        n = Stub.getDefaultImpl().getRadioAccessFamily(n, string2);
                        return n;
                    }
                    parcel2.readException();
                    n = parcel2.readInt();
                    return n;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public int getRadioHalVersion() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(244, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        int n = Stub.getDefaultImpl().getRadioHalVersion();
                        return n;
                    }
                    parcel2.readException();
                    int n = parcel2.readInt();
                    return n;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public int getRadioPowerState(int n, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(201, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        n = Stub.getDefaultImpl().getRadioPowerState(n, string2);
                        return n;
                    }
                    parcel2.readException();
                    n = parcel2.readInt();
                    return n;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public IImsRcsFeature getRcsFeatureAndListen(int n, IImsServiceFeatureCallback iInterface) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    IBinder iBinder = iInterface != null ? iInterface.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (!this.mRemote.transact(94, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        IImsRcsFeature iImsRcsFeature = Stub.getDefaultImpl().getRcsFeatureAndListen(n, (IImsServiceFeatureCallback)iInterface);
                        return iImsRcsFeature;
                    }
                    parcel2.readException();
                    IImsRcsFeature iImsRcsFeature = IImsRcsFeature.Stub.asInterface(parcel2.readStrongBinder());
                    return iImsRcsFeature;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public ServiceState getServiceStateForSubscriber(int n, String object) throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                block3 : {
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel2.writeInt(n);
                        parcel2.writeString((String)object);
                        if (this.mRemote.transact(153, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block3;
                        object = Stub.getDefaultImpl().getServiceStateForSubscriber(n, (String)object);
                        parcel.recycle();
                        parcel2.recycle();
                        return object;
                    }
                    catch (Throwable throwable) {
                        parcel.recycle();
                        parcel2.recycle();
                        throw throwable;
                    }
                }
                parcel.readException();
                object = parcel.readInt() != 0 ? ServiceState.CREATOR.createFromParcel(parcel) : null;
                parcel.recycle();
                parcel2.recycle();
                return object;
            }

            @Override
            public SignalStrength getSignalStrength(int n) throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                block3 : {
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    try {
                        parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel.writeInt(n);
                        if (this.mRemote.transact(181, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block3;
                        SignalStrength signalStrength = Stub.getDefaultImpl().getSignalStrength(n);
                        parcel2.recycle();
                        parcel.recycle();
                        return signalStrength;
                    }
                    catch (Throwable throwable) {
                        parcel2.recycle();
                        parcel.recycle();
                        throw throwable;
                    }
                }
                parcel2.readException();
                SignalStrength signalStrength = parcel2.readInt() != 0 ? SignalStrength.CREATOR.createFromParcel(parcel2) : null;
                parcel2.recycle();
                parcel.recycle();
                return signalStrength;
            }

            @Override
            public String getSimLocaleForSubscriber(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(151, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        String string2 = Stub.getDefaultImpl().getSimLocaleForSubscriber(n);
                        return string2;
                    }
                    parcel2.readException();
                    String string3 = parcel2.readString();
                    return string3;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public int[] getSlotsMapping() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(243, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        int[] arrn = Stub.getDefaultImpl().getSlotsMapping();
                        return arrn;
                    }
                    parcel2.readException();
                    int[] arrn = parcel2.createIntArray();
                    return arrn;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public String[] getSmsApps(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(198, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        String[] arrstring = Stub.getDefaultImpl().getSmsApps(n);
                        return arrstring;
                    }
                    parcel2.readException();
                    String[] arrstring = parcel2.createStringArray();
                    return arrstring;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public int getSubIdForPhoneAccount(PhoneAccount phoneAccount) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (phoneAccount != null) {
                        parcel.writeInt(1);
                        phoneAccount.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(148, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        int n = Stub.getDefaultImpl().getSubIdForPhoneAccount(phoneAccount);
                        return n;
                    }
                    parcel2.readException();
                    int n = parcel2.readInt();
                    return n;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public int getSubscriptionCarrierId(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(166, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        n = Stub.getDefaultImpl().getSubscriptionCarrierId(n);
                        return n;
                    }
                    parcel2.readException();
                    n = parcel2.readInt();
                    return n;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public String getSubscriptionCarrierName(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(167, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        String string2 = Stub.getDefaultImpl().getSubscriptionCarrierName(n);
                        return string2;
                    }
                    parcel2.readException();
                    String string3 = parcel2.readString();
                    return string3;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public int getSubscriptionSpecificCarrierId(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(168, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        n = Stub.getDefaultImpl().getSubscriptionSpecificCarrierId(n);
                        return n;
                    }
                    parcel2.readException();
                    n = parcel2.readInt();
                    return n;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public String getSubscriptionSpecificCarrierName(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(169, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        String string2 = Stub.getDefaultImpl().getSubscriptionSpecificCarrierName(n);
                        return string2;
                    }
                    parcel2.readException();
                    String string3 = parcel2.readString();
                    return string3;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public List<TelephonyHistogram> getTelephonyHistograms() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(163, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        List<TelephonyHistogram> list = Stub.getDefaultImpl().getTelephonyHistograms();
                        return list;
                    }
                    parcel2.readException();
                    ArrayList<TelephonyHistogram> arrayList = parcel2.createTypedArrayList(TelephonyHistogram.CREATOR);
                    return arrayList;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public boolean getTetherApnRequiredForSubscriber(int n) throws RemoteException {
                Parcel parcel;
                boolean bl;
                Parcel parcel2;
                block5 : {
                    IBinder iBinder;
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    try {
                        parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel.writeInt(n);
                        iBinder = this.mRemote;
                        bl = false;
                    }
                    catch (Throwable throwable) {
                        parcel2.recycle();
                        parcel.recycle();
                        throw throwable;
                    }
                    if (iBinder.transact(90, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().getTetherApnRequiredForSubscriber(n);
                    parcel2.recycle();
                    parcel.recycle();
                    return bl;
                }
                parcel2.readException();
                n = parcel2.readInt();
                if (n != 0) {
                    bl = true;
                }
                parcel2.recycle();
                parcel.recycle();
                return bl;
            }

            @Override
            public String getTypeAllocationCodeForSlot(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(144, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        String string2 = Stub.getDefaultImpl().getTypeAllocationCodeForSlot(n);
                        return string2;
                    }
                    parcel2.readException();
                    String string3 = parcel2.readString();
                    return string3;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public List<UiccCardInfo> getUiccCardsInfo(String arrayList) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString((String)((Object)arrayList));
                    if (!this.mRemote.transact(183, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        arrayList = Stub.getDefaultImpl().getUiccCardsInfo((String)((Object)arrayList));
                        return arrayList;
                    }
                    parcel2.readException();
                    arrayList = parcel2.createTypedArrayList(UiccCardInfo.CREATOR);
                    return arrayList;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public UiccSlotInfo[] getUiccSlotsInfo() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(184, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        UiccSlotInfo[] arruiccSlotInfo = Stub.getDefaultImpl().getUiccSlotsInfo();
                        return arruiccSlotInfo;
                    }
                    parcel2.readException();
                    UiccSlotInfo[] arruiccSlotInfo = parcel2.createTypedArray(UiccSlotInfo.CREATOR);
                    return arruiccSlotInfo;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public String getVisualVoicemailPackageName(String string2, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(54, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        string2 = Stub.getDefaultImpl().getVisualVoicemailPackageName(string2, n);
                        return string2;
                    }
                    parcel2.readException();
                    string2 = parcel2.readString();
                    return string2;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public Bundle getVisualVoicemailSettings(String object, int n) throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                block3 : {
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel2.writeString((String)object);
                        parcel2.writeInt(n);
                        if (this.mRemote.transact(53, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block3;
                        object = Stub.getDefaultImpl().getVisualVoicemailSettings((String)object, n);
                        parcel.recycle();
                        parcel2.recycle();
                        return object;
                    }
                    catch (Throwable throwable) {
                        parcel.recycle();
                        parcel2.recycle();
                        throw throwable;
                    }
                }
                parcel.readException();
                object = parcel.readInt() != 0 ? Bundle.CREATOR.createFromParcel(parcel) : null;
                parcel.recycle();
                parcel2.recycle();
                return object;
            }

            @Override
            public VisualVoicemailSmsFilterSettings getVisualVoicemailSmsFilterSettings(String object, int n) throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                block3 : {
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel2.writeString((String)object);
                        parcel2.writeInt(n);
                        if (this.mRemote.transact(57, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block3;
                        object = Stub.getDefaultImpl().getVisualVoicemailSmsFilterSettings((String)object, n);
                        parcel.recycle();
                        parcel2.recycle();
                        return object;
                    }
                    catch (Throwable throwable) {
                        parcel.recycle();
                        parcel2.recycle();
                        throw throwable;
                    }
                }
                parcel.readException();
                object = parcel.readInt() != 0 ? VisualVoicemailSmsFilterSettings.CREATOR.createFromParcel(parcel) : null;
                parcel.recycle();
                parcel2.recycle();
                return object;
            }

            @Override
            public int getVoWiFiModeSetting(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(217, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        n = Stub.getDefaultImpl().getVoWiFiModeSetting(n);
                        return n;
                    }
                    parcel2.readException();
                    n = parcel2.readInt();
                    return n;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public int getVoWiFiRoamingModeSetting(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(219, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        n = Stub.getDefaultImpl().getVoWiFiRoamingModeSetting(n);
                        return n;
                    }
                    parcel2.readException();
                    n = parcel2.readInt();
                    return n;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public int getVoiceActivationState(int n, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(49, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        n = Stub.getDefaultImpl().getVoiceActivationState(n, string2);
                        return n;
                    }
                    parcel2.readException();
                    n = parcel2.readInt();
                    return n;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public int getVoiceMessageCountForSubscriber(int n, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(51, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        n = Stub.getDefaultImpl().getVoiceMessageCountForSubscriber(n, string2);
                        return n;
                    }
                    parcel2.readException();
                    n = parcel2.readInt();
                    return n;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public int getVoiceNetworkTypeForSubscriber(int n, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(64, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        n = Stub.getDefaultImpl().getVoiceNetworkTypeForSubscriber(n, string2);
                        return n;
                    }
                    parcel2.readException();
                    n = parcel2.readInt();
                    return n;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            /*
             * WARNING - void declaration
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public Uri getVoicemailRingtoneUri(PhoneAccountHandle parcelable) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    void var1_5;
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (parcelable != null) {
                        parcel.writeInt(1);
                        ((PhoneAccountHandle)parcelable).writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(154, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Uri uri = Stub.getDefaultImpl().getVoicemailRingtoneUri((PhoneAccountHandle)parcelable);
                        parcel2.recycle();
                        parcel.recycle();
                        return uri;
                    }
                    parcel2.readException();
                    if (parcel2.readInt() != 0) {
                        Uri uri = Uri.CREATOR.createFromParcel(parcel2);
                    } else {
                        Object var1_4 = null;
                    }
                    parcel2.recycle();
                    parcel.recycle();
                    return var1_5;
                }
                catch (Throwable throwable) {
                    parcel2.recycle();
                    parcel.recycle();
                    throw throwable;
                }
            }

            @Override
            public NetworkStats getVtDataUsage(int n, boolean bl) throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                block4 : {
                    int n2;
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel2.writeInt(n);
                        n2 = bl ? 1 : 0;
                    }
                    catch (Throwable throwable) {
                        parcel.recycle();
                        parcel2.recycle();
                        throw throwable;
                    }
                    parcel2.writeInt(n2);
                    if (this.mRemote.transact(175, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block4;
                    NetworkStats networkStats = Stub.getDefaultImpl().getVtDataUsage(n, bl);
                    parcel.recycle();
                    parcel2.recycle();
                    return networkStats;
                }
                parcel.readException();
                NetworkStats networkStats = parcel.readInt() != 0 ? NetworkStats.CREATOR.createFromParcel(parcel) : null;
                parcel.recycle();
                parcel2.recycle();
                return networkStats;
            }

            @Override
            public boolean handlePinMmi(String string2) throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                boolean bl;
                block5 : {
                    IBinder iBinder;
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    try {
                        parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel.writeString(string2);
                        iBinder = this.mRemote;
                        bl = false;
                    }
                    catch (Throwable throwable) {
                        parcel2.recycle();
                        parcel.recycle();
                        throw throwable;
                    }
                    if (iBinder.transact(13, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().handlePinMmi(string2);
                    parcel2.recycle();
                    parcel.recycle();
                    return bl;
                }
                parcel2.readException();
                int n = parcel2.readInt();
                if (n != 0) {
                    bl = true;
                }
                parcel2.recycle();
                parcel.recycle();
                return bl;
            }

            @Override
            public boolean handlePinMmiForSubscriber(int n, String string2) throws RemoteException {
                Parcel parcel;
                boolean bl;
                Parcel parcel2;
                block5 : {
                    IBinder iBinder;
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel2.writeInt(n);
                        parcel2.writeString(string2);
                        iBinder = this.mRemote;
                        bl = false;
                    }
                    catch (Throwable throwable) {
                        parcel.recycle();
                        parcel2.recycle();
                        throw throwable;
                    }
                    if (iBinder.transact(15, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().handlePinMmiForSubscriber(n, string2);
                    parcel.recycle();
                    parcel2.recycle();
                    return bl;
                }
                parcel.readException();
                n = parcel.readInt();
                if (n != 0) {
                    bl = true;
                }
                parcel.recycle();
                parcel2.recycle();
                return bl;
            }

            @Override
            public void handleUssdRequest(int n, String string2, ResultReceiver resultReceiver) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeString(string2);
                    if (resultReceiver != null) {
                        parcel.writeInt(1);
                        resultReceiver.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(14, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().handleUssdRequest(n, string2, resultReceiver);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public boolean hasIccCard() throws RemoteException {
                boolean bl;
                Parcel parcel;
                Parcel parcel2;
                block5 : {
                    IBinder iBinder;
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        iBinder = this.mRemote;
                        bl = false;
                    }
                    catch (Throwable throwable) {
                        parcel.recycle();
                        parcel2.recycle();
                        throw throwable;
                    }
                    if (iBinder.transact(65, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().hasIccCard();
                    parcel.recycle();
                    parcel2.recycle();
                    return bl;
                }
                parcel.readException();
                int n = parcel.readInt();
                if (n != 0) {
                    bl = true;
                }
                parcel.recycle();
                parcel2.recycle();
                return bl;
            }

            @Override
            public boolean hasIccCardUsingSlotIndex(int n) throws RemoteException {
                Parcel parcel;
                boolean bl;
                Parcel parcel2;
                block5 : {
                    IBinder iBinder;
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    try {
                        parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel.writeInt(n);
                        iBinder = this.mRemote;
                        bl = false;
                    }
                    catch (Throwable throwable) {
                        parcel2.recycle();
                        parcel.recycle();
                        throw throwable;
                    }
                    if (iBinder.transact(66, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().hasIccCardUsingSlotIndex(n);
                    parcel2.recycle();
                    parcel.recycle();
                    return bl;
                }
                parcel2.readException();
                n = parcel2.readInt();
                if (n != 0) {
                    bl = true;
                }
                parcel2.recycle();
                parcel.recycle();
                return bl;
            }

            @Override
            public boolean iccCloseLogicalChannel(int n, int n2) throws RemoteException {
                Parcel parcel;
                boolean bl;
                Parcel parcel2;
                block5 : {
                    IBinder iBinder;
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel2.writeInt(n);
                        parcel2.writeInt(n2);
                        iBinder = this.mRemote;
                        bl = false;
                    }
                    catch (Throwable throwable) {
                        parcel.recycle();
                        parcel2.recycle();
                        throw throwable;
                    }
                    if (iBinder.transact(76, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().iccCloseLogicalChannel(n, n2);
                    parcel.recycle();
                    parcel2.recycle();
                    return bl;
                }
                parcel.readException();
                n = parcel.readInt();
                if (n != 0) {
                    bl = true;
                }
                parcel.recycle();
                parcel2.recycle();
                return bl;
            }

            @Override
            public boolean iccCloseLogicalChannelBySlot(int n, int n2) throws RemoteException {
                Parcel parcel;
                boolean bl;
                Parcel parcel2;
                block5 : {
                    IBinder iBinder;
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel2.writeInt(n);
                        parcel2.writeInt(n2);
                        iBinder = this.mRemote;
                        bl = false;
                    }
                    catch (Throwable throwable) {
                        parcel.recycle();
                        parcel2.recycle();
                        throw throwable;
                    }
                    if (iBinder.transact(75, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().iccCloseLogicalChannelBySlot(n, n2);
                    parcel.recycle();
                    parcel2.recycle();
                    return bl;
                }
                parcel.readException();
                n = parcel.readInt();
                if (n != 0) {
                    bl = true;
                }
                parcel.recycle();
                parcel2.recycle();
                return bl;
            }

            /*
             * Loose catch block
             * WARNING - void declaration
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             * Lifted jumps to return sites
             */
            @Override
            public byte[] iccExchangeSimIO(int n, int n2, int n3, int n4, int n5, int n6, String arrby) throws RemoteException {
                void var7_14;
                Parcel parcel;
                Parcel parcel2;
                block14 : {
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    try {
                        parcel.writeInt(n);
                    }
                    catch (Throwable throwable) {
                        break block14;
                    }
                    try {
                        parcel.writeInt(n2);
                    }
                    catch (Throwable throwable) {
                        break block14;
                    }
                    try {
                        parcel.writeInt(n3);
                    }
                    catch (Throwable throwable) {
                        break block14;
                    }
                    try {
                        parcel.writeInt(n4);
                    }
                    catch (Throwable throwable) {
                        break block14;
                    }
                    try {
                        parcel.writeInt(n5);
                        parcel.writeInt(n6);
                        parcel.writeString((String)arrby);
                        if (!this.mRemote.transact(81, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                            arrby = Stub.getDefaultImpl().iccExchangeSimIO(n, n2, n3, n4, n5, n6, (String)arrby);
                            parcel2.recycle();
                            parcel.recycle();
                            return arrby;
                        }
                        parcel2.readException();
                        arrby = parcel2.createByteArray();
                        parcel2.recycle();
                        parcel.recycle();
                        return arrby;
                    }
                    catch (Throwable throwable) {}
                    break block14;
                    catch (Throwable throwable) {
                        // empty catch block
                    }
                }
                parcel2.recycle();
                parcel.recycle();
                throw var7_14;
            }

            @Override
            public IccOpenLogicalChannelResponse iccOpenLogicalChannel(int n, String object, String string2, int n2) throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                block3 : {
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel2.writeInt(n);
                        parcel2.writeString((String)object);
                        parcel2.writeString(string2);
                        parcel2.writeInt(n2);
                        if (this.mRemote.transact(74, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block3;
                        object = Stub.getDefaultImpl().iccOpenLogicalChannel(n, (String)object, string2, n2);
                        parcel.recycle();
                        parcel2.recycle();
                        return object;
                    }
                    catch (Throwable throwable) {
                        parcel.recycle();
                        parcel2.recycle();
                        throw throwable;
                    }
                }
                parcel.readException();
                object = parcel.readInt() != 0 ? IccOpenLogicalChannelResponse.CREATOR.createFromParcel(parcel) : null;
                parcel.recycle();
                parcel2.recycle();
                return object;
            }

            @Override
            public IccOpenLogicalChannelResponse iccOpenLogicalChannelBySlot(int n, String object, String string2, int n2) throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                block3 : {
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel2.writeInt(n);
                        parcel2.writeString((String)object);
                        parcel2.writeString(string2);
                        parcel2.writeInt(n2);
                        if (this.mRemote.transact(73, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block3;
                        object = Stub.getDefaultImpl().iccOpenLogicalChannelBySlot(n, (String)object, string2, n2);
                        parcel.recycle();
                        parcel2.recycle();
                        return object;
                    }
                    catch (Throwable throwable) {
                        parcel.recycle();
                        parcel2.recycle();
                        throw throwable;
                    }
                }
                parcel.readException();
                object = parcel.readInt() != 0 ? IccOpenLogicalChannelResponse.CREATOR.createFromParcel(parcel) : null;
                parcel.recycle();
                parcel2.recycle();
                return object;
            }

            /*
             * Loose catch block
             * WARNING - void declaration
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             * Lifted jumps to return sites
             */
            @Override
            public String iccTransmitApduBasicChannel(int n, String string2, int n2, int n3, int n4, int n5, int n6, String string3) throws RemoteException {
                Parcel parcel;
                void var2_8;
                Parcel parcel2;
                block12 : {
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                    try {
                        parcel2.writeInt(n);
                    }
                    catch (Throwable throwable) {
                        break block12;
                    }
                    try {
                        parcel2.writeString(string2);
                    }
                    catch (Throwable throwable) {
                        break block12;
                    }
                    try {
                        parcel2.writeInt(n2);
                    }
                    catch (Throwable throwable) {
                        break block12;
                    }
                    try {
                        parcel2.writeInt(n3);
                        parcel2.writeInt(n4);
                        parcel2.writeInt(n5);
                        parcel2.writeInt(n6);
                        parcel2.writeString(string3);
                        if (!this.mRemote.transact(80, parcel2, parcel, 0) && Stub.getDefaultImpl() != null) {
                            string2 = Stub.getDefaultImpl().iccTransmitApduBasicChannel(n, string2, n2, n3, n4, n5, n6, string3);
                            parcel.recycle();
                            parcel2.recycle();
                            return string2;
                        }
                        parcel.readException();
                        string2 = parcel.readString();
                        parcel.recycle();
                        parcel2.recycle();
                        return string2;
                    }
                    catch (Throwable throwable) {}
                    break block12;
                    catch (Throwable throwable) {
                        // empty catch block
                    }
                }
                parcel.recycle();
                parcel2.recycle();
                throw var2_8;
            }

            /*
             * Loose catch block
             * WARNING - void declaration
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             * Lifted jumps to return sites
             */
            @Override
            public String iccTransmitApduBasicChannelBySlot(int n, String string2, int n2, int n3, int n4, int n5, int n6, String string3) throws RemoteException {
                Parcel parcel;
                void var2_8;
                Parcel parcel2;
                block12 : {
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                    try {
                        parcel2.writeInt(n);
                    }
                    catch (Throwable throwable) {
                        break block12;
                    }
                    try {
                        parcel2.writeString(string2);
                    }
                    catch (Throwable throwable) {
                        break block12;
                    }
                    try {
                        parcel2.writeInt(n2);
                    }
                    catch (Throwable throwable) {
                        break block12;
                    }
                    try {
                        parcel2.writeInt(n3);
                        parcel2.writeInt(n4);
                        parcel2.writeInt(n5);
                        parcel2.writeInt(n6);
                        parcel2.writeString(string3);
                        if (!this.mRemote.transact(79, parcel2, parcel, 0) && Stub.getDefaultImpl() != null) {
                            string2 = Stub.getDefaultImpl().iccTransmitApduBasicChannelBySlot(n, string2, n2, n3, n4, n5, n6, string3);
                            parcel.recycle();
                            parcel2.recycle();
                            return string2;
                        }
                        parcel.readException();
                        string2 = parcel.readString();
                        parcel.recycle();
                        parcel2.recycle();
                        return string2;
                    }
                    catch (Throwable throwable) {}
                    break block12;
                    catch (Throwable throwable) {
                        // empty catch block
                    }
                }
                parcel.recycle();
                parcel2.recycle();
                throw var2_8;
            }

            /*
             * Loose catch block
             * WARNING - void declaration
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             * Lifted jumps to return sites
             */
            @Override
            public String iccTransmitApduLogicalChannel(int n, int n2, int n3, int n4, int n5, int n6, int n7, String string2) throws RemoteException {
                Parcel parcel;
                void var8_14;
                Parcel parcel2;
                block12 : {
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                    try {
                        parcel2.writeInt(n);
                    }
                    catch (Throwable throwable) {
                        break block12;
                    }
                    try {
                        parcel2.writeInt(n2);
                    }
                    catch (Throwable throwable) {
                        break block12;
                    }
                    try {
                        parcel2.writeInt(n3);
                    }
                    catch (Throwable throwable) {
                        break block12;
                    }
                    try {
                        parcel2.writeInt(n4);
                        parcel2.writeInt(n5);
                        parcel2.writeInt(n6);
                        parcel2.writeInt(n7);
                        parcel2.writeString(string2);
                        if (!this.mRemote.transact(78, parcel2, parcel, 0) && Stub.getDefaultImpl() != null) {
                            string2 = Stub.getDefaultImpl().iccTransmitApduLogicalChannel(n, n2, n3, n4, n5, n6, n7, string2);
                            parcel.recycle();
                            parcel2.recycle();
                            return string2;
                        }
                        parcel.readException();
                        string2 = parcel.readString();
                        parcel.recycle();
                        parcel2.recycle();
                        return string2;
                    }
                    catch (Throwable throwable) {}
                    break block12;
                    catch (Throwable throwable) {
                        // empty catch block
                    }
                }
                parcel.recycle();
                parcel2.recycle();
                throw var8_14;
            }

            /*
             * Loose catch block
             * WARNING - void declaration
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             * Lifted jumps to return sites
             */
            @Override
            public String iccTransmitApduLogicalChannelBySlot(int n, int n2, int n3, int n4, int n5, int n6, int n7, String string2) throws RemoteException {
                Parcel parcel;
                void var8_14;
                Parcel parcel2;
                block12 : {
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                    try {
                        parcel2.writeInt(n);
                    }
                    catch (Throwable throwable) {
                        break block12;
                    }
                    try {
                        parcel2.writeInt(n2);
                    }
                    catch (Throwable throwable) {
                        break block12;
                    }
                    try {
                        parcel2.writeInt(n3);
                    }
                    catch (Throwable throwable) {
                        break block12;
                    }
                    try {
                        parcel2.writeInt(n4);
                        parcel2.writeInt(n5);
                        parcel2.writeInt(n6);
                        parcel2.writeInt(n7);
                        parcel2.writeString(string2);
                        if (!this.mRemote.transact(77, parcel2, parcel, 0) && Stub.getDefaultImpl() != null) {
                            string2 = Stub.getDefaultImpl().iccTransmitApduLogicalChannelBySlot(n, n2, n3, n4, n5, n6, n7, string2);
                            parcel.recycle();
                            parcel2.recycle();
                            return string2;
                        }
                        parcel.readException();
                        string2 = parcel.readString();
                        parcel.recycle();
                        parcel2.recycle();
                        return string2;
                    }
                    catch (Throwable throwable) {}
                    break block12;
                    catch (Throwable throwable) {
                        // empty catch block
                    }
                }
                parcel.recycle();
                parcel2.recycle();
                throw var8_14;
            }

            @Override
            public int invokeOemRilRequestRaw(byte[] arrby, byte[] arrby2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeByteArray(arrby);
                    if (arrby2 == null) {
                        parcel.writeInt(-1);
                    } else {
                        parcel.writeInt(arrby2.length);
                    }
                    if (!this.mRemote.transact(126, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        int n = Stub.getDefaultImpl().invokeOemRilRequestRaw(arrby, arrby2);
                        return n;
                    }
                    parcel2.readException();
                    int n = parcel2.readInt();
                    parcel2.readByteArray(arrby2);
                    return n;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public boolean isAdvancedCallingSettingEnabled(int n) throws RemoteException {
                Parcel parcel;
                boolean bl;
                Parcel parcel2;
                block5 : {
                    IBinder iBinder;
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    try {
                        parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel.writeInt(n);
                        iBinder = this.mRemote;
                        bl = false;
                    }
                    catch (Throwable throwable) {
                        parcel2.recycle();
                        parcel.recycle();
                        throw throwable;
                    }
                    if (iBinder.transact(208, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().isAdvancedCallingSettingEnabled(n);
                    parcel2.recycle();
                    parcel.recycle();
                    return bl;
                }
                parcel2.readException();
                n = parcel2.readInt();
                if (n != 0) {
                    bl = true;
                }
                parcel2.recycle();
                parcel.recycle();
                return bl;
            }

            @Override
            public boolean isApnMetered(int n, int n2) throws RemoteException {
                Parcel parcel;
                boolean bl;
                Parcel parcel2;
                block5 : {
                    IBinder iBinder;
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel2.writeInt(n);
                        parcel2.writeInt(n2);
                        iBinder = this.mRemote;
                        bl = false;
                    }
                    catch (Throwable throwable) {
                        parcel.recycle();
                        parcel2.recycle();
                        throw throwable;
                    }
                    if (iBinder.transact(247, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().isApnMetered(n, n2);
                    parcel.recycle();
                    parcel2.recycle();
                    return bl;
                }
                parcel.readException();
                n = parcel.readInt();
                if (n != 0) {
                    bl = true;
                }
                parcel.recycle();
                parcel2.recycle();
                return bl;
            }

            @Override
            public boolean isAvailable(int n, int n2, int n3) throws RemoteException {
                boolean bl;
                Parcel parcel;
                Parcel parcel2;
                block5 : {
                    IBinder iBinder;
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    try {
                        parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel.writeInt(n);
                        parcel.writeInt(n2);
                        parcel.writeInt(n3);
                        iBinder = this.mRemote;
                        bl = false;
                    }
                    catch (Throwable throwable) {
                        parcel2.recycle();
                        parcel.recycle();
                        throw throwable;
                    }
                    if (iBinder.transact(207, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().isAvailable(n, n2, n3);
                    parcel2.recycle();
                    parcel.recycle();
                    return bl;
                }
                parcel2.readException();
                n = parcel2.readInt();
                if (n != 0) {
                    bl = true;
                }
                parcel2.recycle();
                parcel.recycle();
                return bl;
            }

            @Override
            public boolean isCapable(int n, int n2, int n3) throws RemoteException {
                boolean bl;
                Parcel parcel;
                Parcel parcel2;
                block5 : {
                    IBinder iBinder;
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    try {
                        parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel.writeInt(n);
                        parcel.writeInt(n2);
                        parcel.writeInt(n3);
                        iBinder = this.mRemote;
                        bl = false;
                    }
                    catch (Throwable throwable) {
                        parcel2.recycle();
                        parcel.recycle();
                        throw throwable;
                    }
                    if (iBinder.transact(206, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().isCapable(n, n2, n3);
                    parcel2.recycle();
                    parcel.recycle();
                    return bl;
                }
                parcel2.readException();
                n = parcel2.readInt();
                if (n != 0) {
                    bl = true;
                }
                parcel2.recycle();
                parcel.recycle();
                return bl;
            }

            @Override
            public boolean isConcurrentVoiceAndDataAllowed(int n) throws RemoteException {
                Parcel parcel;
                boolean bl;
                Parcel parcel2;
                block5 : {
                    IBinder iBinder;
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    try {
                        parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel.writeInt(n);
                        iBinder = this.mRemote;
                        bl = false;
                    }
                    catch (Throwable throwable) {
                        parcel2.recycle();
                        parcel.recycle();
                        throw throwable;
                    }
                    if (iBinder.transact(52, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().isConcurrentVoiceAndDataAllowed(n);
                    parcel2.recycle();
                    parcel.recycle();
                    return bl;
                }
                parcel2.readException();
                n = parcel2.readInt();
                if (n != 0) {
                    bl = true;
                }
                parcel2.recycle();
                parcel.recycle();
                return bl;
            }

            @Override
            public boolean isDataAllowedInVoiceCall(int n) throws RemoteException {
                Parcel parcel;
                boolean bl;
                Parcel parcel2;
                block5 : {
                    IBinder iBinder;
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    try {
                        parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel.writeInt(n);
                        iBinder = this.mRemote;
                        bl = false;
                    }
                    catch (Throwable throwable) {
                        parcel2.recycle();
                        parcel.recycle();
                        throw throwable;
                    }
                    if (iBinder.transact(252, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().isDataAllowedInVoiceCall(n);
                    parcel2.recycle();
                    parcel.recycle();
                    return bl;
                }
                parcel2.readException();
                n = parcel2.readInt();
                if (n != 0) {
                    bl = true;
                }
                parcel2.recycle();
                parcel.recycle();
                return bl;
            }

            @Override
            public boolean isDataConnectivityPossible(int n) throws RemoteException {
                Parcel parcel;
                boolean bl;
                Parcel parcel2;
                block5 : {
                    IBinder iBinder;
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    try {
                        parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel.writeInt(n);
                        iBinder = this.mRemote;
                        bl = false;
                    }
                    catch (Throwable throwable) {
                        parcel2.recycle();
                        parcel.recycle();
                        throw throwable;
                    }
                    if (iBinder.transact(29, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().isDataConnectivityPossible(n);
                    parcel2.recycle();
                    parcel.recycle();
                    return bl;
                }
                parcel2.readException();
                n = parcel2.readInt();
                if (n != 0) {
                    bl = true;
                }
                parcel2.recycle();
                parcel.recycle();
                return bl;
            }

            @Override
            public boolean isDataEnabled(int n) throws RemoteException {
                Parcel parcel;
                boolean bl;
                Parcel parcel2;
                block5 : {
                    IBinder iBinder;
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    try {
                        parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel.writeInt(n);
                        iBinder = this.mRemote;
                        bl = false;
                    }
                    catch (Throwable throwable) {
                        parcel2.recycle();
                        parcel.recycle();
                        throw throwable;
                    }
                    if (iBinder.transact(108, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().isDataEnabled(n);
                    parcel2.recycle();
                    parcel.recycle();
                    return bl;
                }
                parcel2.readException();
                n = parcel2.readInt();
                if (n != 0) {
                    bl = true;
                }
                parcel2.recycle();
                parcel.recycle();
                return bl;
            }

            @Override
            public boolean isDataEnabledForApn(int n, int n2, String string2) throws RemoteException {
                Parcel parcel;
                boolean bl;
                Parcel parcel2;
                block5 : {
                    IBinder iBinder;
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    try {
                        parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel.writeInt(n);
                        parcel.writeInt(n2);
                        parcel.writeString(string2);
                        iBinder = this.mRemote;
                        bl = false;
                    }
                    catch (Throwable throwable) {
                        parcel2.recycle();
                        parcel.recycle();
                        throw throwable;
                    }
                    if (iBinder.transact(246, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().isDataEnabledForApn(n, n2, string2);
                    parcel2.recycle();
                    parcel.recycle();
                    return bl;
                }
                parcel2.readException();
                n = parcel2.readInt();
                if (n != 0) {
                    bl = true;
                }
                parcel2.recycle();
                parcel.recycle();
                return bl;
            }

            @Override
            public boolean isDataRoamingEnabled(int n) throws RemoteException {
                Parcel parcel;
                boolean bl;
                Parcel parcel2;
                block5 : {
                    IBinder iBinder;
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    try {
                        parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel.writeInt(n);
                        iBinder = this.mRemote;
                        bl = false;
                    }
                    catch (Throwable throwable) {
                        parcel2.recycle();
                        parcel.recycle();
                        throw throwable;
                    }
                    if (iBinder.transact(187, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().isDataRoamingEnabled(n);
                    parcel2.recycle();
                    parcel.recycle();
                    return bl;
                }
                parcel2.readException();
                n = parcel2.readInt();
                if (n != 0) {
                    bl = true;
                }
                parcel2.recycle();
                parcel.recycle();
                return bl;
            }

            @Override
            public boolean isEmergencyNumber(String string2, boolean bl) throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                boolean bl2;
                int n;
                block4 : {
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel2.writeString(string2);
                        bl2 = true;
                        n = bl ? 1 : 0;
                    }
                    catch (Throwable throwable) {
                        parcel.recycle();
                        parcel2.recycle();
                        throw throwable;
                    }
                    parcel2.writeInt(n);
                    if (this.mRemote.transact(224, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block4;
                    bl = Stub.getDefaultImpl().isEmergencyNumber(string2, bl);
                    parcel.recycle();
                    parcel2.recycle();
                    return bl;
                }
                parcel.readException();
                n = parcel.readInt();
                bl = n != 0 ? bl2 : false;
                parcel.recycle();
                parcel2.recycle();
                return bl;
            }

            @Override
            public boolean isHearingAidCompatibilitySupported() throws RemoteException {
                boolean bl;
                Parcel parcel;
                Parcel parcel2;
                block5 : {
                    IBinder iBinder;
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        iBinder = this.mRemote;
                        bl = false;
                    }
                    catch (Throwable throwable) {
                        parcel.recycle();
                        parcel2.recycle();
                        throw throwable;
                    }
                    if (iBinder.transact(137, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().isHearingAidCompatibilitySupported();
                    parcel.recycle();
                    parcel2.recycle();
                    return bl;
                }
                parcel.readException();
                int n = parcel.readInt();
                if (n != 0) {
                    bl = true;
                }
                parcel.recycle();
                parcel2.recycle();
                return bl;
            }

            @Override
            public boolean isImsRegistered(int n) throws RemoteException {
                Parcel parcel;
                boolean bl;
                Parcel parcel2;
                block5 : {
                    IBinder iBinder;
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    try {
                        parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel.writeInt(n);
                        iBinder = this.mRemote;
                        bl = false;
                    }
                    catch (Throwable throwable) {
                        parcel2.recycle();
                        parcel.recycle();
                        throw throwable;
                    }
                    if (iBinder.transact(138, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().isImsRegistered(n);
                    parcel2.recycle();
                    parcel.recycle();
                    return bl;
                }
                parcel2.readException();
                n = parcel2.readInt();
                if (n != 0) {
                    bl = true;
                }
                parcel2.recycle();
                parcel.recycle();
                return bl;
            }

            @Override
            public boolean isInEmergencySmsMode() throws RemoteException {
                boolean bl;
                Parcel parcel;
                Parcel parcel2;
                block5 : {
                    IBinder iBinder;
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        iBinder = this.mRemote;
                        bl = false;
                    }
                    catch (Throwable throwable) {
                        parcel.recycle();
                        parcel2.recycle();
                        throw throwable;
                    }
                    if (iBinder.transact(197, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().isInEmergencySmsMode();
                    parcel.recycle();
                    parcel2.recycle();
                    return bl;
                }
                parcel.readException();
                int n = parcel.readInt();
                if (n != 0) {
                    bl = true;
                }
                parcel.recycle();
                parcel2.recycle();
                return bl;
            }

            @Override
            public boolean isManualNetworkSelectionAllowed(int n) throws RemoteException {
                Parcel parcel;
                boolean bl;
                Parcel parcel2;
                block5 : {
                    IBinder iBinder;
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    try {
                        parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel.writeInt(n);
                        iBinder = this.mRemote;
                        bl = false;
                    }
                    catch (Throwable throwable) {
                        parcel2.recycle();
                        parcel.recycle();
                        throw throwable;
                    }
                    if (iBinder.transact(109, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().isManualNetworkSelectionAllowed(n);
                    parcel2.recycle();
                    parcel.recycle();
                    return bl;
                }
                parcel2.readException();
                n = parcel2.readInt();
                if (n != 0) {
                    bl = true;
                }
                parcel2.recycle();
                parcel.recycle();
                return bl;
            }

            @Override
            public boolean isMmTelCapabilityProvisionedInCache(int n, int n2, int n3) throws RemoteException {
                boolean bl;
                Parcel parcel;
                Parcel parcel2;
                block5 : {
                    IBinder iBinder;
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    try {
                        parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel.writeInt(n);
                        parcel.writeInt(n2);
                        parcel.writeInt(n3);
                        iBinder = this.mRemote;
                        bl = false;
                    }
                    catch (Throwable throwable) {
                        parcel2.recycle();
                        parcel.recycle();
                        throw throwable;
                    }
                    if (iBinder.transact(230, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().isMmTelCapabilityProvisionedInCache(n, n2, n3);
                    parcel2.recycle();
                    parcel.recycle();
                    return bl;
                }
                parcel2.readException();
                n = parcel2.readInt();
                if (n != 0) {
                    bl = true;
                }
                parcel2.recycle();
                parcel.recycle();
                return bl;
            }

            @Override
            public boolean isModemEnabledForSlot(int n, String string2) throws RemoteException {
                Parcel parcel;
                boolean bl;
                Parcel parcel2;
                block5 : {
                    IBinder iBinder;
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel2.writeInt(n);
                        parcel2.writeString(string2);
                        iBinder = this.mRemote;
                        bl = false;
                    }
                    catch (Throwable throwable) {
                        parcel.recycle();
                        parcel2.recycle();
                        throw throwable;
                    }
                    if (iBinder.transact(245, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().isModemEnabledForSlot(n, string2);
                    parcel.recycle();
                    parcel2.recycle();
                    return bl;
                }
                parcel.readException();
                n = parcel.readInt();
                if (n != 0) {
                    bl = true;
                }
                parcel.recycle();
                parcel2.recycle();
                return bl;
            }

            @Override
            public int isMultiSimSupported(String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(240, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        int n = Stub.getDefaultImpl().isMultiSimSupported(string2);
                        return n;
                    }
                    parcel2.readException();
                    int n = parcel2.readInt();
                    return n;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public boolean isRadioOn(String string2) throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                boolean bl;
                block5 : {
                    IBinder iBinder;
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    try {
                        parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel.writeString(string2);
                        iBinder = this.mRemote;
                        bl = false;
                    }
                    catch (Throwable throwable) {
                        parcel2.recycle();
                        parcel.recycle();
                        throw throwable;
                    }
                    if (iBinder.transact(3, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().isRadioOn(string2);
                    parcel2.recycle();
                    parcel.recycle();
                    return bl;
                }
                parcel2.readException();
                int n = parcel2.readInt();
                if (n != 0) {
                    bl = true;
                }
                parcel2.recycle();
                parcel.recycle();
                return bl;
            }

            @Override
            public boolean isRadioOnForSubscriber(int n, String string2) throws RemoteException {
                Parcel parcel;
                boolean bl;
                Parcel parcel2;
                block5 : {
                    IBinder iBinder;
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel2.writeInt(n);
                        parcel2.writeString(string2);
                        iBinder = this.mRemote;
                        bl = false;
                    }
                    catch (Throwable throwable) {
                        parcel.recycle();
                        parcel2.recycle();
                        throw throwable;
                    }
                    if (iBinder.transact(4, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().isRadioOnForSubscriber(n, string2);
                    parcel.recycle();
                    parcel2.recycle();
                    return bl;
                }
                parcel.readException();
                n = parcel.readInt();
                if (n != 0) {
                    bl = true;
                }
                parcel.recycle();
                parcel2.recycle();
                return bl;
            }

            @Override
            public boolean isRttSupported(int n) throws RemoteException {
                Parcel parcel;
                boolean bl;
                Parcel parcel2;
                block5 : {
                    IBinder iBinder;
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    try {
                        parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel.writeInt(n);
                        iBinder = this.mRemote;
                        bl = false;
                    }
                    catch (Throwable throwable) {
                        parcel2.recycle();
                        parcel.recycle();
                        throw throwable;
                    }
                    if (iBinder.transact(136, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().isRttSupported(n);
                    parcel2.recycle();
                    parcel.recycle();
                    return bl;
                }
                parcel2.readException();
                n = parcel2.readInt();
                if (n != 0) {
                    bl = true;
                }
                parcel2.recycle();
                parcel.recycle();
                return bl;
            }

            @Override
            public boolean isTtyModeSupported() throws RemoteException {
                boolean bl;
                Parcel parcel;
                Parcel parcel2;
                block5 : {
                    IBinder iBinder;
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        iBinder = this.mRemote;
                        bl = false;
                    }
                    catch (Throwable throwable) {
                        parcel.recycle();
                        parcel2.recycle();
                        throw throwable;
                    }
                    if (iBinder.transact(135, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().isTtyModeSupported();
                    parcel.recycle();
                    parcel2.recycle();
                    return bl;
                }
                parcel.readException();
                int n = parcel.readInt();
                if (n != 0) {
                    bl = true;
                }
                parcel.recycle();
                parcel2.recycle();
                return bl;
            }

            @Override
            public boolean isTtyOverVolteEnabled(int n) throws RemoteException {
                Parcel parcel;
                boolean bl;
                Parcel parcel2;
                block5 : {
                    IBinder iBinder;
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    try {
                        parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel.writeInt(n);
                        iBinder = this.mRemote;
                        bl = false;
                    }
                    catch (Throwable throwable) {
                        parcel2.recycle();
                        parcel.recycle();
                        throw throwable;
                    }
                    if (iBinder.transact(222, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().isTtyOverVolteEnabled(n);
                    parcel2.recycle();
                    parcel.recycle();
                    return bl;
                }
                parcel2.readException();
                n = parcel2.readInt();
                if (n != 0) {
                    bl = true;
                }
                parcel2.recycle();
                parcel.recycle();
                return bl;
            }

            @Override
            public boolean isUserDataEnabled(int n) throws RemoteException {
                Parcel parcel;
                boolean bl;
                Parcel parcel2;
                block5 : {
                    IBinder iBinder;
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    try {
                        parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel.writeInt(n);
                        iBinder = this.mRemote;
                        bl = false;
                    }
                    catch (Throwable throwable) {
                        parcel2.recycle();
                        parcel.recycle();
                        throw throwable;
                    }
                    if (iBinder.transact(107, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().isUserDataEnabled(n);
                    parcel2.recycle();
                    parcel.recycle();
                    return bl;
                }
                parcel2.readException();
                n = parcel2.readInt();
                if (n != 0) {
                    bl = true;
                }
                parcel2.recycle();
                parcel.recycle();
                return bl;
            }

            @Override
            public boolean isVideoCallingEnabled(String string2) throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                boolean bl;
                block5 : {
                    IBinder iBinder;
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    try {
                        parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel.writeString(string2);
                        iBinder = this.mRemote;
                        bl = false;
                    }
                    catch (Throwable throwable) {
                        parcel2.recycle();
                        parcel.recycle();
                        throw throwable;
                    }
                    if (iBinder.transact(132, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().isVideoCallingEnabled(string2);
                    parcel2.recycle();
                    parcel.recycle();
                    return bl;
                }
                parcel2.readException();
                int n = parcel2.readInt();
                if (n != 0) {
                    bl = true;
                }
                parcel2.recycle();
                parcel.recycle();
                return bl;
            }

            @Override
            public boolean isVideoTelephonyAvailable(int n) throws RemoteException {
                Parcel parcel;
                boolean bl;
                Parcel parcel2;
                block5 : {
                    IBinder iBinder;
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    try {
                        parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel.writeInt(n);
                        iBinder = this.mRemote;
                        bl = false;
                    }
                    catch (Throwable throwable) {
                        parcel2.recycle();
                        parcel.recycle();
                        throw throwable;
                    }
                    if (iBinder.transact(140, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().isVideoTelephonyAvailable(n);
                    parcel2.recycle();
                    parcel.recycle();
                    return bl;
                }
                parcel2.readException();
                n = parcel2.readInt();
                if (n != 0) {
                    bl = true;
                }
                parcel2.recycle();
                parcel.recycle();
                return bl;
            }

            @Override
            public boolean isVoWiFiRoamingSettingEnabled(int n) throws RemoteException {
                Parcel parcel;
                boolean bl;
                Parcel parcel2;
                block5 : {
                    IBinder iBinder;
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    try {
                        parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel.writeInt(n);
                        iBinder = this.mRemote;
                        bl = false;
                    }
                    catch (Throwable throwable) {
                        parcel2.recycle();
                        parcel.recycle();
                        throw throwable;
                    }
                    if (iBinder.transact(214, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().isVoWiFiRoamingSettingEnabled(n);
                    parcel2.recycle();
                    parcel.recycle();
                    return bl;
                }
                parcel2.readException();
                n = parcel2.readInt();
                if (n != 0) {
                    bl = true;
                }
                parcel2.recycle();
                parcel.recycle();
                return bl;
            }

            @Override
            public boolean isVoWiFiSettingEnabled(int n) throws RemoteException {
                Parcel parcel;
                boolean bl;
                Parcel parcel2;
                block5 : {
                    IBinder iBinder;
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    try {
                        parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel.writeInt(n);
                        iBinder = this.mRemote;
                        bl = false;
                    }
                    catch (Throwable throwable) {
                        parcel2.recycle();
                        parcel.recycle();
                        throw throwable;
                    }
                    if (iBinder.transact(212, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().isVoWiFiSettingEnabled(n);
                    parcel2.recycle();
                    parcel.recycle();
                    return bl;
                }
                parcel2.readException();
                n = parcel2.readInt();
                if (n != 0) {
                    bl = true;
                }
                parcel2.recycle();
                parcel.recycle();
                return bl;
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public boolean isVoicemailVibrationEnabled(PhoneAccountHandle phoneAccountHandle) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean bl = true;
                    if (phoneAccountHandle != null) {
                        parcel.writeInt(1);
                        phoneAccountHandle.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(156, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        bl = Stub.getDefaultImpl().isVoicemailVibrationEnabled(phoneAccountHandle);
                        parcel2.recycle();
                        parcel.recycle();
                        return bl;
                    }
                    parcel2.readException();
                    int n = parcel2.readInt();
                    if (n == 0) {
                        bl = false;
                    }
                    parcel2.recycle();
                    parcel.recycle();
                    return bl;
                }
                catch (Throwable throwable) {
                    parcel2.recycle();
                    parcel.recycle();
                    throw throwable;
                }
            }

            @Override
            public boolean isVtSettingEnabled(int n) throws RemoteException {
                Parcel parcel;
                boolean bl;
                Parcel parcel2;
                block5 : {
                    IBinder iBinder;
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    try {
                        parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel.writeInt(n);
                        iBinder = this.mRemote;
                        bl = false;
                    }
                    catch (Throwable throwable) {
                        parcel2.recycle();
                        parcel.recycle();
                        throw throwable;
                    }
                    if (iBinder.transact(210, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().isVtSettingEnabled(n);
                    parcel2.recycle();
                    parcel.recycle();
                    return bl;
                }
                parcel2.readException();
                n = parcel2.readInt();
                if (n != 0) {
                    bl = true;
                }
                parcel2.recycle();
                parcel.recycle();
                return bl;
            }

            @Override
            public boolean isWifiCallingAvailable(int n) throws RemoteException {
                Parcel parcel;
                boolean bl;
                Parcel parcel2;
                block5 : {
                    IBinder iBinder;
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    try {
                        parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel.writeInt(n);
                        iBinder = this.mRemote;
                        bl = false;
                    }
                    catch (Throwable throwable) {
                        parcel2.recycle();
                        parcel.recycle();
                        throw throwable;
                    }
                    if (iBinder.transact(139, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().isWifiCallingAvailable(n);
                    parcel2.recycle();
                    parcel.recycle();
                    return bl;
                }
                parcel2.readException();
                n = parcel2.readInt();
                if (n != 0) {
                    bl = true;
                }
                parcel2.recycle();
                parcel.recycle();
                return bl;
            }

            @Override
            public boolean isWorldPhone(int n, String string2) throws RemoteException {
                Parcel parcel;
                boolean bl;
                Parcel parcel2;
                block5 : {
                    IBinder iBinder;
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel2.writeInt(n);
                        parcel2.writeString(string2);
                        iBinder = this.mRemote;
                        bl = false;
                    }
                    catch (Throwable throwable) {
                        parcel.recycle();
                        parcel2.recycle();
                        throw throwable;
                    }
                    if (iBinder.transact(134, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().isWorldPhone(n, string2);
                    parcel.recycle();
                    parcel2.recycle();
                    return bl;
                }
                parcel.readException();
                n = parcel.readInt();
                if (n != 0) {
                    bl = true;
                }
                parcel.recycle();
                parcel2.recycle();
                return bl;
            }

            @Override
            public boolean needMobileRadioShutdown() throws RemoteException {
                boolean bl;
                Parcel parcel;
                Parcel parcel2;
                block5 : {
                    IBinder iBinder;
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        iBinder = this.mRemote;
                        bl = false;
                    }
                    catch (Throwable throwable) {
                        parcel.recycle();
                        parcel2.recycle();
                        throw throwable;
                    }
                    if (iBinder.transact(127, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().needMobileRadioShutdown();
                    parcel.recycle();
                    parcel2.recycle();
                    return bl;
                }
                parcel.readException();
                int n = parcel.readInt();
                if (n != 0) {
                    bl = true;
                }
                parcel.recycle();
                parcel2.recycle();
                return bl;
            }

            @Override
            public boolean needsOtaServiceProvisioning() throws RemoteException {
                boolean bl;
                Parcel parcel;
                Parcel parcel2;
                block5 : {
                    IBinder iBinder;
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        iBinder = this.mRemote;
                        bl = false;
                    }
                    catch (Throwable throwable) {
                        parcel.recycle();
                        parcel2.recycle();
                        throw throwable;
                    }
                    if (iBinder.transact(45, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().needsOtaServiceProvisioning();
                    parcel.recycle();
                    parcel2.recycle();
                    return bl;
                }
                parcel.readException();
                int n = parcel.readInt();
                if (n != 0) {
                    bl = true;
                }
                parcel.recycle();
                parcel2.recycle();
                return bl;
            }

            @Override
            public String nvReadItem(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(83, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        String string2 = Stub.getDefaultImpl().nvReadItem(n);
                        return string2;
                    }
                    parcel2.readException();
                    String string3 = parcel2.readString();
                    return string3;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public boolean nvWriteCdmaPrl(byte[] arrby) throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                boolean bl;
                block5 : {
                    IBinder iBinder;
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    try {
                        parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel.writeByteArray(arrby);
                        iBinder = this.mRemote;
                        bl = false;
                    }
                    catch (Throwable throwable) {
                        parcel2.recycle();
                        parcel.recycle();
                        throw throwable;
                    }
                    if (iBinder.transact(85, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().nvWriteCdmaPrl(arrby);
                    parcel2.recycle();
                    parcel.recycle();
                    return bl;
                }
                parcel2.readException();
                int n = parcel2.readInt();
                if (n != 0) {
                    bl = true;
                }
                parcel2.recycle();
                parcel.recycle();
                return bl;
            }

            @Override
            public boolean nvWriteItem(int n, String string2) throws RemoteException {
                Parcel parcel;
                boolean bl;
                Parcel parcel2;
                block5 : {
                    IBinder iBinder;
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel2.writeInt(n);
                        parcel2.writeString(string2);
                        iBinder = this.mRemote;
                        bl = false;
                    }
                    catch (Throwable throwable) {
                        parcel.recycle();
                        parcel2.recycle();
                        throw throwable;
                    }
                    if (iBinder.transact(84, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().nvWriteItem(n, string2);
                    parcel.recycle();
                    parcel2.recycle();
                    return bl;
                }
                parcel.readException();
                n = parcel.readInt();
                if (n != 0) {
                    bl = true;
                }
                parcel.recycle();
                parcel2.recycle();
                return bl;
            }

            @Override
            public boolean rebootModem(int n) throws RemoteException {
                Parcel parcel;
                boolean bl;
                Parcel parcel2;
                block5 : {
                    IBinder iBinder;
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    try {
                        parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel.writeInt(n);
                        iBinder = this.mRemote;
                        bl = false;
                    }
                    catch (Throwable throwable) {
                        parcel2.recycle();
                        parcel.recycle();
                        throw throwable;
                    }
                    if (iBinder.transact(87, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().rebootModem(n);
                    parcel2.recycle();
                    parcel.recycle();
                    return bl;
                }
                parcel2.readException();
                n = parcel2.readInt();
                if (n != 0) {
                    bl = true;
                }
                parcel2.recycle();
                parcel.recycle();
                return bl;
            }

            @Override
            public void refreshUiccProfile(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(194, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().refreshUiccProfile(n);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void registerImsProvisioningChangedCallback(int n, IImsConfigCallback iImsConfigCallback) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    IBinder iBinder = iImsConfigCallback != null ? iImsConfigCallback.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (!this.mRemote.transact(226, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().registerImsProvisioningChangedCallback(n, iImsConfigCallback);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void registerImsRegistrationCallback(int n, IImsRegistrationCallback iImsRegistrationCallback) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    IBinder iBinder = iImsRegistrationCallback != null ? iImsRegistrationCallback.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (!this.mRemote.transact(202, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().registerImsRegistrationCallback(n, iImsRegistrationCallback);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void registerMmTelCapabilityCallback(int n, IImsCapabilityCallback iImsCapabilityCallback) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    IBinder iBinder = iImsCapabilityCallback != null ? iImsCapabilityCallback.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (!this.mRemote.transact(204, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().registerMmTelCapabilityCallback(n, iImsCapabilityCallback);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void requestCellInfoUpdate(int n, ICellInfoCallback iCellInfoCallback, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    IBinder iBinder = iCellInfoCallback != null ? iCellInfoCallback.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(70, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().requestCellInfoUpdate(n, iCellInfoCallback, string2);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void requestCellInfoUpdateWithWorkSource(int n, ICellInfoCallback iCellInfoCallback, String string2, WorkSource workSource) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    IBinder iBinder = iCellInfoCallback != null ? iCellInfoCallback.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    parcel.writeString(string2);
                    if (workSource != null) {
                        parcel.writeInt(1);
                        workSource.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(71, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().requestCellInfoUpdateWithWorkSource(n, iCellInfoCallback, string2, workSource);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void requestModemActivityInfo(ResultReceiver resultReceiver) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (resultReceiver != null) {
                        parcel.writeInt(1);
                        resultReceiver.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(152, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().requestModemActivityInfo(resultReceiver);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public int requestNetworkScan(int n, NetworkScanRequest networkScanRequest, Messenger messenger, IBinder iBinder, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (networkScanRequest != null) {
                        parcel.writeInt(1);
                        networkScanRequest.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (messenger != null) {
                        parcel.writeInt(1);
                        messenger.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeStrongBinder(iBinder);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(101, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        n = Stub.getDefaultImpl().requestNetworkScan(n, networkScanRequest, messenger, iBinder, string2);
                        return n;
                    }
                    parcel2.readException();
                    n = parcel2.readInt();
                    return n;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void requestNumberVerification(PhoneNumberRange phoneNumberRange, long l, INumberVerificationCallback iNumberVerificationCallback, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (phoneNumberRange != null) {
                        parcel.writeInt(1);
                        phoneNumberRange.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeLong(l);
                    IBinder iBinder = iNumberVerificationCallback != null ? iNumberVerificationCallback.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(114, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().requestNumberVerification(phoneNumberRange, l, iNumberVerificationCallback, string2);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public boolean resetModemConfig(int n) throws RemoteException {
                Parcel parcel;
                boolean bl;
                Parcel parcel2;
                block5 : {
                    IBinder iBinder;
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    try {
                        parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel.writeInt(n);
                        iBinder = this.mRemote;
                        bl = false;
                    }
                    catch (Throwable throwable) {
                        parcel2.recycle();
                        parcel.recycle();
                        throw throwable;
                    }
                    if (iBinder.transact(86, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().resetModemConfig(n);
                    parcel2.recycle();
                    parcel.recycle();
                    return bl;
                }
                parcel2.readException();
                n = parcel2.readInt();
                if (n != 0) {
                    bl = true;
                }
                parcel2.recycle();
                parcel.recycle();
                return bl;
            }

            @Override
            public void sendDialerSpecialCode(String string2, String string3) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    parcel.writeString(string3);
                    if (!this.mRemote.transact(60, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().sendDialerSpecialCode(string2, string3);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public String sendEnvelopeWithStatus(int n, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(82, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        string2 = Stub.getDefaultImpl().sendEnvelopeWithStatus(n, string2);
                        return string2;
                    }
                    parcel2.readException();
                    string2 = parcel2.readString();
                    return string2;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            /*
             * Loose catch block
             * WARNING - void declaration
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             * Lifted jumps to return sites
             */
            @Override
            public void sendVisualVoicemailSmsForSubscriber(String string2, int n, String string3, int n2, String string4, PendingIntent pendingIntent) throws RemoteException {
                Parcel parcel;
                void var1_8;
                Parcel parcel2;
                block16 : {
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                    try {
                        parcel2.writeString(string2);
                    }
                    catch (Throwable throwable) {
                        break block16;
                    }
                    try {
                        parcel2.writeInt(n);
                    }
                    catch (Throwable throwable) {
                        break block16;
                    }
                    try {
                        parcel2.writeString(string3);
                    }
                    catch (Throwable throwable) {
                        break block16;
                    }
                    try {
                        parcel2.writeInt(n2);
                    }
                    catch (Throwable throwable) {
                        break block16;
                    }
                    try {
                        parcel2.writeString(string4);
                        if (pendingIntent != null) {
                            parcel2.writeInt(1);
                            pendingIntent.writeToParcel(parcel2, 0);
                        } else {
                            parcel2.writeInt(0);
                        }
                        if (!this.mRemote.transact(59, parcel2, parcel, 0) && Stub.getDefaultImpl() != null) {
                            Stub.getDefaultImpl().sendVisualVoicemailSmsForSubscriber(string2, n, string3, n2, string4, pendingIntent);
                            parcel.recycle();
                            parcel2.recycle();
                            return;
                        }
                        parcel.readException();
                        parcel.recycle();
                        parcel2.recycle();
                        return;
                    }
                    catch (Throwable throwable) {}
                    break block16;
                    catch (Throwable throwable) {
                        // empty catch block
                    }
                }
                parcel.recycle();
                parcel2.recycle();
                throw var1_8;
            }

            @Override
            public void setAdvancedCallingSettingEnabled(int n, boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                parcel.writeInt(n);
                int n2 = bl ? 1 : 0;
                try {
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(209, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setAdvancedCallingSettingEnabled(n, bl);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public int setAllowedCarriers(CarrierRestrictionRules carrierRestrictionRules) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (carrierRestrictionRules != null) {
                        parcel.writeInt(1);
                        carrierRestrictionRules.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(164, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        int n = Stub.getDefaultImpl().setAllowedCarriers(carrierRestrictionRules);
                        return n;
                    }
                    parcel2.readException();
                    int n = parcel2.readInt();
                    return n;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            /*
             * Loose catch block
             * WARNING - void declaration
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             * Lifted jumps to return sites
             */
            @Override
            public void setCarrierTestOverride(int n, String string2, String string3, String string4, String string5, String string6, String string7, String string8, String string9, String string10) throws RemoteException {
                void var2_6;
                Parcel parcel2;
                Parcel parcel;
                block8 : {
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    try {
                        parcel.writeInt(n);
                    }
                    catch (Throwable throwable) {
                        break block8;
                    }
                    try {
                        parcel.writeString(string2);
                        parcel.writeString(string3);
                        parcel.writeString(string4);
                        parcel.writeString(string5);
                        parcel.writeString(string6);
                        parcel.writeString(string7);
                        parcel.writeString(string8);
                        parcel.writeString(string9);
                        parcel.writeString(string10);
                        if (!this.mRemote.transact(192, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                            Stub.getDefaultImpl().setCarrierTestOverride(n, string2, string3, string4, string5, string6, string7, string8, string9, string10);
                            parcel2.recycle();
                            parcel.recycle();
                            return;
                        }
                        parcel2.readException();
                        parcel2.recycle();
                        parcel.recycle();
                        return;
                    }
                    catch (Throwable throwable) {}
                    break block8;
                    catch (Throwable throwable) {
                        // empty catch block
                    }
                }
                parcel2.recycle();
                parcel.recycle();
                throw var2_6;
            }

            @Override
            public boolean setCdmaRoamingMode(int n, int n2) throws RemoteException {
                Parcel parcel;
                boolean bl;
                Parcel parcel2;
                block5 : {
                    IBinder iBinder;
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel2.writeInt(n);
                        parcel2.writeInt(n2);
                        iBinder = this.mRemote;
                        bl = false;
                    }
                    catch (Throwable throwable) {
                        parcel.recycle();
                        parcel2.recycle();
                        throw throwable;
                    }
                    if (iBinder.transact(190, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().setCdmaRoamingMode(n, n2);
                    parcel.recycle();
                    parcel2.recycle();
                    return bl;
                }
                parcel.readException();
                n = parcel.readInt();
                if (n != 0) {
                    bl = true;
                }
                parcel.recycle();
                parcel2.recycle();
                return bl;
            }

            @Override
            public boolean setCdmaSubscriptionMode(int n, int n2) throws RemoteException {
                Parcel parcel;
                boolean bl;
                Parcel parcel2;
                block5 : {
                    IBinder iBinder;
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel2.writeInt(n);
                        parcel2.writeInt(n2);
                        iBinder = this.mRemote;
                        bl = false;
                    }
                    catch (Throwable throwable) {
                        parcel.recycle();
                        parcel2.recycle();
                        throw throwable;
                    }
                    if (iBinder.transact(191, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().setCdmaSubscriptionMode(n, n2);
                    parcel.recycle();
                    parcel2.recycle();
                    return bl;
                }
                parcel.readException();
                n = parcel.readInt();
                if (n != 0) {
                    bl = true;
                }
                parcel.recycle();
                parcel2.recycle();
                return bl;
            }

            @Override
            public void setCellInfoListRate(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(72, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setCellInfoListRate(n);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void setDataActivationState(int n, int n2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(48, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setDataActivationState(n, n2);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public boolean setDataAllowedDuringVoiceCall(int n, boolean bl) throws RemoteException {
                Parcel parcel;
                boolean bl2;
                Parcel parcel2;
                block4 : {
                    int n2;
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel2.writeInt(n);
                        bl2 = true;
                        n2 = bl ? 1 : 0;
                    }
                    catch (Throwable throwable) {
                        parcel.recycle();
                        parcel2.recycle();
                        throw throwable;
                    }
                    parcel2.writeInt(n2);
                    if (this.mRemote.transact(251, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block4;
                    bl = Stub.getDefaultImpl().setDataAllowedDuringVoiceCall(n, bl);
                    parcel.recycle();
                    parcel2.recycle();
                    return bl;
                }
                parcel.readException();
                n = parcel.readInt();
                bl = n != 0 ? bl2 : false;
                parcel.recycle();
                parcel2.recycle();
                return bl;
            }

            @Override
            public void setDataRoamingEnabled(int n, boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                parcel.writeInt(n);
                int n2 = bl ? 1 : 0;
                try {
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(188, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setDataRoamingEnabled(n, bl);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void setDefaultSmsApp(int n, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(200, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setDefaultSmsApp(n, string2);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public int setImsProvisioningInt(int n, int n2, int n3) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    parcel.writeInt(n3);
                    if (!this.mRemote.transact(234, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        n = Stub.getDefaultImpl().setImsProvisioningInt(n, n2, n3);
                        return n;
                    }
                    parcel2.readException();
                    n = parcel2.readInt();
                    return n;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void setImsProvisioningStatusForCapability(int n, int n2, int n3, boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                parcel.writeInt(n);
                parcel.writeInt(n2);
                parcel.writeInt(n3);
                int n4 = bl ? 1 : 0;
                try {
                    parcel.writeInt(n4);
                    if (!this.mRemote.transact(228, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setImsProvisioningStatusForCapability(n, n2, n3, bl);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public int setImsProvisioningString(int n, int n2, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(235, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        n = Stub.getDefaultImpl().setImsProvisioningString(n, n2, string2);
                        return n;
                    }
                    parcel2.readException();
                    n = parcel2.readInt();
                    return n;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void setImsRegistrationState(boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                int n = bl ? 1 : 0;
                try {
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(111, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setImsRegistrationState(bl);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public boolean setImsService(int n, boolean bl, String string2) throws RemoteException {
                Parcel parcel;
                boolean bl2;
                Parcel parcel2;
                block4 : {
                    int n2;
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    try {
                        parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel.writeInt(n);
                        bl2 = true;
                        n2 = bl ? 1 : 0;
                    }
                    catch (Throwable throwable) {
                        parcel2.recycle();
                        parcel.recycle();
                        throw throwable;
                    }
                    parcel.writeInt(n2);
                    parcel.writeString(string2);
                    if (this.mRemote.transact(97, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block4;
                    bl = Stub.getDefaultImpl().setImsService(n, bl, string2);
                    parcel2.recycle();
                    parcel.recycle();
                    return bl;
                }
                parcel2.readException();
                n = parcel2.readInt();
                bl = n != 0 ? bl2 : false;
                parcel2.recycle();
                parcel.recycle();
                return bl;
            }

            @Override
            public boolean setLine1NumberForDisplayForSubscriber(int n, String string2, String string3) throws RemoteException {
                Parcel parcel;
                boolean bl;
                Parcel parcel2;
                block5 : {
                    IBinder iBinder;
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    try {
                        parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel.writeInt(n);
                        parcel.writeString(string2);
                        parcel.writeString(string3);
                        iBinder = this.mRemote;
                        bl = false;
                    }
                    catch (Throwable throwable) {
                        parcel2.recycle();
                        parcel.recycle();
                        throw throwable;
                    }
                    if (iBinder.transact(120, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().setLine1NumberForDisplayForSubscriber(n, string2, string3);
                    parcel2.recycle();
                    parcel.recycle();
                    return bl;
                }
                parcel2.readException();
                n = parcel2.readInt();
                if (n != 0) {
                    bl = true;
                }
                parcel2.recycle();
                parcel.recycle();
                return bl;
            }

            @Override
            public void setMultiSimCarrierRestriction(boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                int n = bl ? 1 : 0;
                try {
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(239, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setMultiSimCarrierRestriction(bl);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void setNetworkSelectionModeAutomatic(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(99, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setNetworkSelectionModeAutomatic(n);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public boolean setNetworkSelectionModeManual(int n, OperatorInfo operatorInfo, boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    boolean bl2 = true;
                    if (operatorInfo != null) {
                        parcel.writeInt(1);
                        operatorInfo.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    int n2 = bl ? 1 : 0;
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(103, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        bl = Stub.getDefaultImpl().setNetworkSelectionModeManual(n, operatorInfo, bl);
                        parcel2.recycle();
                        parcel.recycle();
                        return bl;
                    }
                    parcel2.readException();
                    n = parcel2.readInt();
                    bl = n != 0 ? bl2 : false;
                    parcel2.recycle();
                    parcel.recycle();
                    return bl;
                }
                catch (Throwable throwable) {
                    parcel2.recycle();
                    parcel.recycle();
                    throw throwable;
                }
            }

            @Override
            public boolean setOperatorBrandOverride(int n, String string2) throws RemoteException {
                Parcel parcel;
                boolean bl;
                Parcel parcel2;
                block5 : {
                    IBinder iBinder;
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel2.writeInt(n);
                        parcel2.writeString(string2);
                        iBinder = this.mRemote;
                        bl = false;
                    }
                    catch (Throwable throwable) {
                        parcel.recycle();
                        parcel2.recycle();
                        throw throwable;
                    }
                    if (iBinder.transact(124, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().setOperatorBrandOverride(n, string2);
                    parcel.recycle();
                    parcel2.recycle();
                    return bl;
                }
                parcel.readException();
                n = parcel.readInt();
                if (n != 0) {
                    bl = true;
                }
                parcel.recycle();
                parcel2.recycle();
                return bl;
            }

            @Override
            public void setPolicyDataEnabled(boolean bl, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                int n2 = bl ? 1 : 0;
                try {
                    parcel.writeInt(n2);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(176, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setPolicyDataEnabled(bl, n);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public boolean setPreferredNetworkType(int n, int n2) throws RemoteException {
                Parcel parcel;
                boolean bl;
                Parcel parcel2;
                block5 : {
                    IBinder iBinder;
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel2.writeInt(n);
                        parcel2.writeInt(n2);
                        iBinder = this.mRemote;
                        bl = false;
                    }
                    catch (Throwable throwable) {
                        parcel.recycle();
                        parcel2.recycle();
                        throw throwable;
                    }
                    if (iBinder.transact(104, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().setPreferredNetworkType(n, n2);
                    parcel.recycle();
                    parcel2.recycle();
                    return bl;
                }
                parcel.readException();
                n = parcel.readInt();
                if (n != 0) {
                    bl = true;
                }
                parcel.recycle();
                parcel2.recycle();
                return bl;
            }

            @Override
            public boolean setRadio(boolean bl) throws RemoteException {
                boolean bl2;
                int n;
                Parcel parcel;
                Parcel parcel2;
                block4 : {
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    try {
                        parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                        bl2 = true;
                        n = bl ? 1 : 0;
                    }
                    catch (Throwable throwable) {
                        parcel2.recycle();
                        parcel.recycle();
                        throw throwable;
                    }
                    parcel.writeInt(n);
                    if (this.mRemote.transact(18, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block4;
                    bl = Stub.getDefaultImpl().setRadio(bl);
                    parcel2.recycle();
                    parcel.recycle();
                    return bl;
                }
                parcel2.readException();
                n = parcel2.readInt();
                bl = n != 0 ? bl2 : false;
                parcel2.recycle();
                parcel.recycle();
                return bl;
            }

            @Override
            public void setRadioCapability(RadioAccessFamily[] arrradioAccessFamily) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeTypedArray((Parcelable[])arrradioAccessFamily, 0);
                    if (!this.mRemote.transact(129, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setRadioCapability(arrradioAccessFamily);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public boolean setRadioForSubscriber(int n, boolean bl) throws RemoteException {
                Parcel parcel;
                boolean bl2;
                Parcel parcel2;
                block4 : {
                    int n2;
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel2.writeInt(n);
                        bl2 = true;
                        n2 = bl ? 1 : 0;
                    }
                    catch (Throwable throwable) {
                        parcel.recycle();
                        parcel2.recycle();
                        throw throwable;
                    }
                    parcel2.writeInt(n2);
                    if (this.mRemote.transact(19, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block4;
                    bl = Stub.getDefaultImpl().setRadioForSubscriber(n, bl);
                    parcel.recycle();
                    parcel2.recycle();
                    return bl;
                }
                parcel.readException();
                n = parcel.readInt();
                bl = n != 0 ? bl2 : false;
                parcel.recycle();
                parcel2.recycle();
                return bl;
            }

            @Override
            public void setRadioIndicationUpdateMode(int n, int n2, int n3) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    parcel.writeInt(n3);
                    if (!this.mRemote.transact(186, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setRadioIndicationUpdateMode(n, n2, n3);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public boolean setRadioPower(boolean bl) throws RemoteException {
                boolean bl2;
                int n;
                Parcel parcel;
                Parcel parcel2;
                block4 : {
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    try {
                        parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                        bl2 = true;
                        n = bl ? 1 : 0;
                    }
                    catch (Throwable throwable) {
                        parcel2.recycle();
                        parcel.recycle();
                        throw throwable;
                    }
                    parcel.writeInt(n);
                    if (this.mRemote.transact(20, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block4;
                    bl = Stub.getDefaultImpl().setRadioPower(bl);
                    parcel2.recycle();
                    parcel.recycle();
                    return bl;
                }
                parcel2.readException();
                n = parcel2.readInt();
                bl = n != 0 ? bl2 : false;
                parcel2.recycle();
                parcel.recycle();
                return bl;
            }

            /*
             * Loose catch block
             * WARNING - void declaration
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             * Lifted jumps to return sites
             */
            @Override
            public boolean setRoamingOverride(int n, List<String> list, List<String> list2, List<String> list3, List<String> list4) throws RemoteException {
                void var2_10;
                Parcel parcel;
                Parcel parcel2;
                block17 : {
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                    try {
                        parcel2.writeInt(n);
                    }
                    catch (Throwable throwable) {
                        break block17;
                    }
                    try {
                        parcel2.writeStringList(list);
                    }
                    catch (Throwable throwable) {
                        break block17;
                    }
                    try {
                        parcel2.writeStringList(list2);
                    }
                    catch (Throwable throwable) {
                        break block17;
                    }
                    try {
                        parcel2.writeStringList(list3);
                    }
                    catch (Throwable throwable) {
                        break block17;
                    }
                    try {
                        parcel2.writeStringList(list4);
                    }
                    catch (Throwable throwable) {
                        break block17;
                    }
                    try {
                        IBinder iBinder = this.mRemote;
                        boolean bl = false;
                        if (!iBinder.transact(125, parcel2, parcel, 0) && Stub.getDefaultImpl() != null) {
                            bl = Stub.getDefaultImpl().setRoamingOverride(n, list, list2, list3, list4);
                            parcel.recycle();
                            parcel2.recycle();
                            return bl;
                        }
                        parcel.readException();
                        n = parcel.readInt();
                        if (n != 0) {
                            bl = true;
                        }
                        parcel.recycle();
                        parcel2.recycle();
                        return bl;
                    }
                    catch (Throwable throwable) {}
                    break block17;
                    catch (Throwable throwable) {
                        // empty catch block
                    }
                }
                parcel.recycle();
                parcel2.recycle();
                throw var2_10;
            }

            @Override
            public void setRttCapabilitySetting(int n, boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                parcel.writeInt(n);
                int n2 = bl ? 1 : 0;
                try {
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(221, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setRttCapabilitySetting(n, bl);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void setSimPowerStateForSlot(int n, int n2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(178, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setSimPowerStateForSlot(n, n2);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void setUserDataEnabled(int n, boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                parcel.writeInt(n);
                int n2 = bl ? 1 : 0;
                try {
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(105, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setUserDataEnabled(n, bl);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void setVoWiFiModeSetting(int n, int n2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(218, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setVoWiFiModeSetting(n, n2);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void setVoWiFiNonPersistent(int n, boolean bl, int n2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                parcel.writeInt(n);
                int n3 = bl ? 1 : 0;
                try {
                    parcel.writeInt(n3);
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(216, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setVoWiFiNonPersistent(n, bl, n2);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void setVoWiFiRoamingModeSetting(int n, int n2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(220, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setVoWiFiRoamingModeSetting(n, n2);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void setVoWiFiRoamingSettingEnabled(int n, boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                parcel.writeInt(n);
                int n2 = bl ? 1 : 0;
                try {
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(215, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setVoWiFiRoamingSettingEnabled(n, bl);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void setVoWiFiSettingEnabled(int n, boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                parcel.writeInt(n);
                int n2 = bl ? 1 : 0;
                try {
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(213, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setVoWiFiSettingEnabled(n, bl);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void setVoiceActivationState(int n, int n2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(47, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setVoiceActivationState(n, n2);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public boolean setVoiceMailNumber(int n, String string2, String string3) throws RemoteException {
                Parcel parcel;
                boolean bl;
                Parcel parcel2;
                block5 : {
                    IBinder iBinder;
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    try {
                        parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel.writeInt(n);
                        parcel.writeString(string2);
                        parcel.writeString(string3);
                        iBinder = this.mRemote;
                        bl = false;
                    }
                    catch (Throwable throwable) {
                        parcel2.recycle();
                        parcel.recycle();
                        throw throwable;
                    }
                    if (iBinder.transact(46, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().setVoiceMailNumber(n, string2, string3);
                    parcel2.recycle();
                    parcel.recycle();
                    return bl;
                }
                parcel2.readException();
                n = parcel2.readInt();
                if (n != 0) {
                    bl = true;
                }
                parcel2.recycle();
                parcel.recycle();
                return bl;
            }

            @Override
            public void setVoicemailRingtoneUri(String string2, PhoneAccountHandle phoneAccountHandle, Uri uri) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    if (phoneAccountHandle != null) {
                        parcel.writeInt(1);
                        phoneAccountHandle.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (uri != null) {
                        parcel.writeInt(1);
                        uri.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(155, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setVoicemailRingtoneUri(string2, phoneAccountHandle, uri);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void setVoicemailVibrationEnabled(String string2, PhoneAccountHandle phoneAccountHandle, boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    int n = 1;
                    if (phoneAccountHandle != null) {
                        parcel.writeInt(1);
                        phoneAccountHandle.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!bl) {
                        n = 0;
                    }
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(157, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setVoicemailVibrationEnabled(string2, phoneAccountHandle, bl);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void setVtSettingEnabled(int n, boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                parcel.writeInt(n);
                int n2 = bl ? 1 : 0;
                try {
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(211, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setVtSettingEnabled(n, bl);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void shutdownMobileRadios() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(128, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().shutdownMobileRadios();
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void stopNetworkScan(int n, int n2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(102, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().stopNetworkScan(n, n2);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public boolean supplyPin(String string2) throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                boolean bl;
                block5 : {
                    IBinder iBinder;
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    try {
                        parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel.writeString(string2);
                        iBinder = this.mRemote;
                        bl = false;
                    }
                    catch (Throwable throwable) {
                        parcel2.recycle();
                        parcel.recycle();
                        throw throwable;
                    }
                    if (iBinder.transact(5, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().supplyPin(string2);
                    parcel2.recycle();
                    parcel.recycle();
                    return bl;
                }
                parcel2.readException();
                int n = parcel2.readInt();
                if (n != 0) {
                    bl = true;
                }
                parcel2.recycle();
                parcel.recycle();
                return bl;
            }

            @Override
            public boolean supplyPinForSubscriber(int n, String string2) throws RemoteException {
                Parcel parcel;
                boolean bl;
                Parcel parcel2;
                block5 : {
                    IBinder iBinder;
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel2.writeInt(n);
                        parcel2.writeString(string2);
                        iBinder = this.mRemote;
                        bl = false;
                    }
                    catch (Throwable throwable) {
                        parcel.recycle();
                        parcel2.recycle();
                        throw throwable;
                    }
                    if (iBinder.transact(6, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().supplyPinForSubscriber(n, string2);
                    parcel.recycle();
                    parcel2.recycle();
                    return bl;
                }
                parcel.readException();
                n = parcel.readInt();
                if (n != 0) {
                    bl = true;
                }
                parcel.recycle();
                parcel2.recycle();
                return bl;
            }

            @Override
            public int[] supplyPinReportResult(String arrn) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString((String)arrn);
                    if (!this.mRemote.transact(9, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        arrn = Stub.getDefaultImpl().supplyPinReportResult((String)arrn);
                        return arrn;
                    }
                    parcel2.readException();
                    arrn = parcel2.createIntArray();
                    return arrn;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public int[] supplyPinReportResultForSubscriber(int n, String arrn) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeString((String)arrn);
                    if (!this.mRemote.transact(10, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        arrn = Stub.getDefaultImpl().supplyPinReportResultForSubscriber(n, (String)arrn);
                        return arrn;
                    }
                    parcel2.readException();
                    arrn = parcel2.createIntArray();
                    return arrn;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public boolean supplyPuk(String string2, String string3) throws RemoteException {
                Parcel parcel;
                boolean bl;
                Parcel parcel2;
                block5 : {
                    IBinder iBinder;
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel2.writeString(string2);
                        parcel2.writeString(string3);
                        iBinder = this.mRemote;
                        bl = false;
                    }
                    catch (Throwable throwable) {
                        parcel.recycle();
                        parcel2.recycle();
                        throw throwable;
                    }
                    if (iBinder.transact(7, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().supplyPuk(string2, string3);
                    parcel.recycle();
                    parcel2.recycle();
                    return bl;
                }
                parcel.readException();
                int n = parcel.readInt();
                if (n != 0) {
                    bl = true;
                }
                parcel.recycle();
                parcel2.recycle();
                return bl;
            }

            @Override
            public boolean supplyPukForSubscriber(int n, String string2, String string3) throws RemoteException {
                Parcel parcel;
                boolean bl;
                Parcel parcel2;
                block5 : {
                    IBinder iBinder;
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    try {
                        parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel.writeInt(n);
                        parcel.writeString(string2);
                        parcel.writeString(string3);
                        iBinder = this.mRemote;
                        bl = false;
                    }
                    catch (Throwable throwable) {
                        parcel2.recycle();
                        parcel.recycle();
                        throw throwable;
                    }
                    if (iBinder.transact(8, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().supplyPukForSubscriber(n, string2, string3);
                    parcel2.recycle();
                    parcel.recycle();
                    return bl;
                }
                parcel2.readException();
                n = parcel2.readInt();
                if (n != 0) {
                    bl = true;
                }
                parcel2.recycle();
                parcel.recycle();
                return bl;
            }

            @Override
            public int[] supplyPukReportResult(String arrn, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString((String)arrn);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(11, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        arrn = Stub.getDefaultImpl().supplyPukReportResult((String)arrn, string2);
                        return arrn;
                    }
                    parcel2.readException();
                    arrn = parcel2.createIntArray();
                    return arrn;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public int[] supplyPukReportResultForSubscriber(int n, String arrn, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeString((String)arrn);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(12, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        arrn = Stub.getDefaultImpl().supplyPukReportResultForSubscriber(n, (String)arrn, string2);
                        return arrn;
                    }
                    parcel2.readException();
                    arrn = parcel2.createIntArray();
                    return arrn;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void switchMultiSimConfig(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(241, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().switchMultiSimConfig(n);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public boolean switchSlots(int[] arrn) throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                boolean bl;
                block5 : {
                    IBinder iBinder;
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    try {
                        parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel.writeIntArray(arrn);
                        iBinder = this.mRemote;
                        bl = false;
                    }
                    catch (Throwable throwable) {
                        parcel2.recycle();
                        parcel.recycle();
                        throw throwable;
                    }
                    if (iBinder.transact(185, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().switchSlots(arrn);
                    parcel2.recycle();
                    parcel.recycle();
                    return bl;
                }
                parcel2.readException();
                int n = parcel2.readInt();
                if (n != 0) {
                    bl = true;
                }
                parcel2.recycle();
                parcel.recycle();
                return bl;
            }

            @Override
            public void toggleRadioOnOff() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(16, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().toggleRadioOnOff();
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void toggleRadioOnOffForSubscriber(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(17, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().toggleRadioOnOffForSubscriber(n);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void unregisterImsProvisioningChangedCallback(int n, IImsConfigCallback iImsConfigCallback) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    IBinder iBinder = iImsConfigCallback != null ? iImsConfigCallback.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (!this.mRemote.transact(227, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().unregisterImsProvisioningChangedCallback(n, iImsConfigCallback);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void unregisterImsRegistrationCallback(int n, IImsRegistrationCallback iImsRegistrationCallback) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    IBinder iBinder = iImsRegistrationCallback != null ? iImsRegistrationCallback.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (!this.mRemote.transact(203, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().unregisterImsRegistrationCallback(n, iImsRegistrationCallback);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void unregisterMmTelCapabilityCallback(int n, IImsCapabilityCallback iImsCapabilityCallback) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    IBinder iBinder = iImsCapabilityCallback != null ? iImsCapabilityCallback.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (!this.mRemote.transact(205, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().unregisterMmTelCapabilityCallback(n, iImsCapabilityCallback);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void updateEmergencyNumberListTestMode(int n, EmergencyNumber emergencyNumber) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (emergencyNumber != null) {
                        parcel.writeInt(1);
                        emergencyNumber.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(236, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().updateEmergencyNumberListTestMode(n, emergencyNumber);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void updateServiceLocation() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(21, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().updateServiceLocation();
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void updateServiceLocationForSubscriber(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(22, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().updateServiceLocationForSubscriber(n);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }
        }

    }

}

