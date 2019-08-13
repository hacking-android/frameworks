/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.annotation.UnsupportedAppUsage
 *  android.os.Bundle
 *  android.os.Handler
 *  android.os.Message
 *  android.os.ResultReceiver
 *  android.telephony.ImsiEncryptionInfo
 *  android.telephony.NetworkScanRequest
 *  android.telephony.ServiceState
 *  com.android.internal.telephony.PhoneConstants
 *  com.android.internal.telephony.PhoneConstants$DataState
 *  com.android.internal.telephony.RILConstants
 */
package com.android.internal.telephony;

import android.annotation.UnsupportedAppUsage;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.ResultReceiver;
import android.telephony.ImsiEncryptionInfo;
import android.telephony.NetworkScanRequest;
import android.telephony.ServiceState;
import com.android.internal.telephony.Call;
import com.android.internal.telephony.CallStateException;
import com.android.internal.telephony.Connection;
import com.android.internal.telephony.IccPhoneBookInterfaceManager;
import com.android.internal.telephony.MmiCode;
import com.android.internal.telephony.PhoneConstants;
import com.android.internal.telephony.RILConstants;
import com.android.internal.telephony.UUSInfo;
import java.util.List;

public interface PhoneInternalInterface {
    public static final int BM_10_800M_2 = 15;
    public static final int BM_4_450M = 10;
    public static final int BM_7_700M2 = 12;
    public static final int BM_8_1800M = 13;
    public static final int BM_9_900M = 14;
    public static final int BM_AUS2_BAND = 5;
    public static final int BM_AUS_BAND = 4;
    public static final int BM_AWS = 17;
    public static final int BM_CELL_800 = 6;
    public static final int BM_EURO_BAND = 1;
    public static final int BM_EURO_PAMR = 16;
    public static final int BM_IMT2000 = 11;
    public static final int BM_JPN_BAND = 3;
    public static final int BM_JTACS = 8;
    public static final int BM_KOREA_PCS = 9;
    public static final int BM_NUM_BAND_MODES = 19;
    public static final int BM_PCS = 7;
    public static final int BM_UNSPECIFIED = 0;
    public static final int BM_US_2500M = 18;
    public static final int BM_US_BAND = 2;
    public static final int CDMA_OTA_PROVISION_STATUS_A_KEY_EXCHANGED = 2;
    public static final int CDMA_OTA_PROVISION_STATUS_COMMITTED = 8;
    public static final int CDMA_OTA_PROVISION_STATUS_IMSI_DOWNLOADED = 6;
    public static final int CDMA_OTA_PROVISION_STATUS_MDN_DOWNLOADED = 5;
    public static final int CDMA_OTA_PROVISION_STATUS_NAM_DOWNLOADED = 4;
    public static final int CDMA_OTA_PROVISION_STATUS_OTAPA_ABORTED = 11;
    public static final int CDMA_OTA_PROVISION_STATUS_OTAPA_STARTED = 9;
    public static final int CDMA_OTA_PROVISION_STATUS_OTAPA_STOPPED = 10;
    public static final int CDMA_OTA_PROVISION_STATUS_PRL_DOWNLOADED = 7;
    public static final int CDMA_OTA_PROVISION_STATUS_SPC_RETRIES_EXCEEDED = 1;
    public static final int CDMA_OTA_PROVISION_STATUS_SPL_UNLOCKED = 0;
    public static final int CDMA_OTA_PROVISION_STATUS_SSD_UPDATED = 3;
    public static final int CDMA_RM_AFFILIATED = 1;
    public static final int CDMA_RM_ANY = 2;
    public static final int CDMA_RM_HOME = 0;
    public static final int CDMA_SUBSCRIPTION_NV = 1;
    public static final int CDMA_SUBSCRIPTION_RUIM_SIM = 0;
    public static final int CDMA_SUBSCRIPTION_UNKNOWN = -1;
    public static final boolean DEBUG_PHONE = true;
    public static final String FEATURE_ENABLE_CBS = "enableCBS";
    public static final String FEATURE_ENABLE_DUN = "enableDUN";
    public static final String FEATURE_ENABLE_DUN_ALWAYS = "enableDUNAlways";
    public static final String FEATURE_ENABLE_EMERGENCY = "enableEmergency";
    public static final String FEATURE_ENABLE_FOTA = "enableFOTA";
    public static final String FEATURE_ENABLE_HIPRI = "enableHIPRI";
    public static final String FEATURE_ENABLE_IMS = "enableIMS";
    public static final String FEATURE_ENABLE_MMS = "enableMMS";
    public static final String FEATURE_ENABLE_SUPL = "enableSUPL";
    public static final int PREFERRED_CDMA_SUBSCRIPTION = 0;
    @UnsupportedAppUsage
    public static final int PREFERRED_NT_MODE = RILConstants.PREFERRED_NETWORK_MODE;
    public static final String REASON_APN_CHANGED = "apnChanged";
    public static final String REASON_APN_FAILED = "apnFailed";
    public static final String REASON_APN_SWITCHED = "apnSwitched";
    public static final String REASON_CARRIER_ACTION_DISABLE_METERED_APN = "carrierActionDisableMeteredApn";
    public static final String REASON_CARRIER_CHANGE = "carrierChange";
    public static final String REASON_CDMA_DATA_ATTACHED = "cdmaDataAttached";
    public static final String REASON_CDMA_DATA_DETACHED = "cdmaDataDetached";
    public static final String REASON_CONNECTED = "connected";
    public static final String REASON_CSS_INDICATOR_CHANGED = "cssIndicatorChanged";
    public static final String REASON_DATA_ATTACHED = "dataAttached";
    public static final String REASON_DATA_DEPENDENCY_MET = "dependencyMet";
    public static final String REASON_DATA_DEPENDENCY_UNMET = "dependencyUnmet";
    public static final String REASON_DATA_DETACHED = "dataDetached";
    public static final String REASON_DATA_DISABLED_INTERNAL = "dataDisabledInternal";
    public static final String REASON_DATA_ENABLED = "dataEnabled";
    public static final String REASON_DATA_ENABLED_OVERRIDE = "dataEnabledOverride";
    public static final String REASON_DATA_SPECIFIC_DISABLED = "specificDisabled";
    public static final String REASON_IWLAN_AVAILABLE = "iwlanAvailable";
    public static final String REASON_LOST_DATA_CONNECTION = "lostDataConnection";
    public static final String REASON_NW_TYPE_CHANGED = "nwTypeChanged";
    public static final String REASON_PDP_RESET = "pdpReset";
    public static final String REASON_PS_RESTRICT_DISABLED = "psRestrictDisabled";
    public static final String REASON_PS_RESTRICT_ENABLED = "psRestrictEnabled";
    public static final String REASON_RADIO_TURNED_OFF = "radioTurnedOff";
    public static final String REASON_RELEASED_BY_CONNECTIVITY_SERVICE = "releasedByConnectivityService";
    public static final String REASON_RESTORE_DEFAULT_APN = "restoreDefaultApn";
    public static final String REASON_ROAMING_OFF = "roamingOff";
    public static final String REASON_ROAMING_ON = "roamingOn";
    public static final String REASON_SIM_LOADED = "simLoaded";
    public static final String REASON_SIM_NOT_READY = "simNotReady";
    public static final String REASON_SINGLE_PDN_ARBITRATION = "SinglePdnArbitration";
    public static final String REASON_VOICE_CALL_ENDED = "2GVoiceCallEnded";
    public static final String REASON_VOICE_CALL_STARTED = "2GVoiceCallStarted";
    public static final int TTY_MODE_FULL = 1;
    public static final int TTY_MODE_HCO = 2;
    public static final int TTY_MODE_OFF = 0;
    public static final int TTY_MODE_VCO = 3;

    public void acceptCall(int var1) throws CallStateException;

    public void activateCellBroadcastSms(int var1, Message var2);

    public boolean canConference();

    public boolean canTransfer();

    public void clearDisconnected();

    public void conference() throws CallStateException;

    public Connection dial(String var1, DialArgs var2) throws CallStateException;

    public void disableLocationUpdates();

    public void enableLocationUpdates();

    public void explicitCallTransfer() throws CallStateException;

    public void getAvailableNetworks(Message var1);

    public Call getBackgroundCall();

    public void getCallBarring(String var1, String var2, Message var3, int var4);

    public void getCallForwardingOption(int var1, Message var2);

    public void getCallWaiting(Message var1);

    public ImsiEncryptionInfo getCarrierInfoForImsiEncryption(int var1);

    public void getCellBroadcastSmsConfig(Message var1);

    public DataActivityState getDataActivityState();

    public PhoneConstants.DataState getDataConnectionState(String var1);

    public boolean getDataRoamingEnabled();

    public String getDeviceId();

    public String getDeviceSvn();

    public String getEsn();

    public Call getForegroundCall();

    public String getGroupIdLevel1();

    public String getGroupIdLevel2();

    public IccPhoneBookInterfaceManager getIccPhoneBookInterfaceManager();

    public String getImei();

    public String getLine1AlphaTag();

    public String getLine1Number();

    public String getMeid();

    public boolean getMute();

    public void getOutgoingCallerIdDisplay(Message var1);

    public List<? extends MmiCode> getPendingMmiCodes();

    public Call getRingingCall();

    public ServiceState getServiceState();

    public String getSubscriberId();

    public String getVoiceMailAlphaTag();

    public String getVoiceMailNumber();

    public boolean handleInCallMmiCommands(String var1) throws CallStateException;

    public boolean handlePinMmi(String var1);

    public boolean handleUssdRequest(String var1, ResultReceiver var2) throws CallStateException;

    public boolean isUserDataEnabled();

    public void registerForSuppServiceNotification(Handler var1, int var2, Object var3);

    public void rejectCall() throws CallStateException;

    public void resetCarrierKeysForImsiEncryption();

    public void sendDtmf(char var1);

    public void sendUssdResponse(String var1);

    public void setCallBarring(String var1, boolean var2, String var3, Message var4, int var5);

    public void setCallForwardingOption(int var1, int var2, String var3, int var4, Message var5);

    public void setCallWaiting(boolean var1, Message var2);

    public void setCarrierInfoForImsiEncryption(ImsiEncryptionInfo var1);

    public void setCellBroadcastSmsConfig(int[] var1, Message var2);

    public void setDataRoamingEnabled(boolean var1);

    public boolean setLine1Number(String var1, String var2, Message var3);

    public void setMute(boolean var1);

    public void setOutgoingCallerIdDisplay(int var1, Message var2);

    public void setRadioPower(boolean var1);

    public void setVoiceMailNumber(String var1, String var2, Message var3);

    public void startDtmf(char var1);

    public void startNetworkScan(NetworkScanRequest var1, Message var2);

    public void stopDtmf();

    public void stopNetworkScan(Message var1);

    public void switchHoldingAndActive() throws CallStateException;

    public void unregisterForSuppServiceNotification(Handler var1);

    public void updateServiceLocation();

    public static enum DataActivityState {
        NONE,
        DATAIN,
        DATAOUT,
        DATAINANDOUT,
        DORMANT;
        
    }

    public static class DialArgs {
        public final Bundle intentExtras;
        public final UUSInfo uusInfo;
        public final int videoState;

        protected DialArgs(Builder builder) {
            this.uusInfo = builder.mUusInfo;
            this.videoState = builder.mVideoState;
            this.intentExtras = builder.mIntentExtras;
        }

        public static class Builder<T extends Builder<T>> {
            protected Bundle mIntentExtras;
            protected UUSInfo mUusInfo;
            protected int mVideoState = 0;

            public DialArgs build() {
                return new DialArgs(this);
            }

            public T setIntentExtras(Bundle bundle) {
                this.mIntentExtras = bundle;
                return (T)this;
            }

            public T setUusInfo(UUSInfo uUSInfo) {
                this.mUusInfo = uUSInfo;
                return (T)this;
            }

            public T setVideoState(int n) {
                this.mVideoState = n;
                return (T)this;
            }
        }

    }

    public static enum SuppService {
        UNKNOWN,
        SWITCH,
        SEPARATE,
        TRANSFER,
        CONFERENCE,
        REJECT,
        HANGUP,
        RESUME,
        HOLD;
        
    }

}

