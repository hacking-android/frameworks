/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.net.LinkProperties
 *  android.os.AsyncResult
 *  android.os.Handler
 *  android.os.Message
 *  android.os.RegistrantList
 *  android.os.ResultReceiver
 *  android.os.SystemProperties
 *  android.telephony.NetworkScanRequest
 *  android.telephony.Rlog
 *  android.telephony.ServiceState
 *  android.telephony.SignalStrength
 *  com.android.internal.telephony.OperatorInfo
 *  com.android.internal.telephony.PhoneConstants
 *  com.android.internal.telephony.PhoneConstants$DataState
 *  com.android.internal.telephony.PhoneConstants$State
 */
package com.android.internal.telephony.sip;

import android.content.Context;
import android.net.LinkProperties;
import android.os.AsyncResult;
import android.os.Handler;
import android.os.Message;
import android.os.RegistrantList;
import android.os.ResultReceiver;
import android.os.SystemProperties;
import android.telephony.NetworkScanRequest;
import android.telephony.Rlog;
import android.telephony.ServiceState;
import android.telephony.SignalStrength;
import com.android.internal.telephony.Call;
import com.android.internal.telephony.CommandsInterface;
import com.android.internal.telephony.Connection;
import com.android.internal.telephony.IccCard;
import com.android.internal.telephony.IccPhoneBookInterfaceManager;
import com.android.internal.telephony.MmiCode;
import com.android.internal.telephony.OperatorInfo;
import com.android.internal.telephony.Phone;
import com.android.internal.telephony.PhoneConstants;
import com.android.internal.telephony.PhoneInternalInterface;
import com.android.internal.telephony.PhoneNotifier;
import com.android.internal.telephony.sip.SipCommandInterface;
import com.android.internal.telephony.uicc.IccFileHandler;
import java.util.ArrayList;
import java.util.List;

abstract class SipPhoneBase
extends Phone {
    private static final String LOG_TAG = "SipPhoneBase";
    private RegistrantList mRingbackRegistrants = new RegistrantList();
    private PhoneConstants.State mState = PhoneConstants.State.IDLE;

    public SipPhoneBase(String string, Context context, PhoneNotifier phoneNotifier) {
        super(string, phoneNotifier, context, new SipCommandInterface(context), false);
    }

    @Override
    public void activateCellBroadcastSms(int n, Message message) {
        Rlog.e((String)LOG_TAG, (String)"Error! This functionality is not implemented for SIP.");
    }

    public boolean canDial() {
        boolean bl;
        block2 : {
            int n = this.getServiceState().getState();
            CharSequence charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("canDial(): serviceState = ");
            ((StringBuilder)charSequence).append(n);
            Rlog.v((String)LOG_TAG, (String)((StringBuilder)charSequence).toString());
            bl = false;
            if (n == 3) {
                return false;
            }
            charSequence = SystemProperties.get((String)"ro.telephony.disable-call", (String)"false");
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("canDial(): disableCall = ");
            stringBuilder.append((String)charSequence);
            Rlog.v((String)LOG_TAG, (String)stringBuilder.toString());
            if (((String)charSequence).equals("true")) {
                return false;
            }
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("canDial(): ringingCall: ");
            ((StringBuilder)charSequence).append((Object)this.getRingingCall().getState());
            Rlog.v((String)LOG_TAG, (String)((StringBuilder)charSequence).toString());
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("canDial(): foregndCall: ");
            ((StringBuilder)charSequence).append((Object)this.getForegroundCall().getState());
            Rlog.v((String)LOG_TAG, (String)((StringBuilder)charSequence).toString());
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("canDial(): backgndCall: ");
            ((StringBuilder)charSequence).append((Object)this.getBackgroundCall().getState());
            Rlog.v((String)LOG_TAG, (String)((StringBuilder)charSequence).toString());
            if (this.getRingingCall().isRinging() || this.getForegroundCall().getState().isAlive() && this.getBackgroundCall().getState().isAlive()) break block2;
            bl = true;
        }
        return bl;
    }

    public boolean disableDataConnectivity() {
        return false;
    }

    @Override
    public void disableLocationUpdates() {
    }

    public boolean enableDataConnectivity() {
        return false;
    }

    @Override
    public void enableLocationUpdates() {
    }

    @Override
    public void getAvailableNetworks(Message message) {
    }

    @Override
    public abstract Call getBackgroundCall();

    @Override
    public void getCallBarring(String string, String string2, Message message, int n) {
    }

    @Override
    public boolean getCallForwardingIndicator() {
        return false;
    }

    @Override
    public void getCallForwardingOption(int n, Message message) {
    }

    @Override
    public void getCallWaiting(Message message) {
        AsyncResult.forMessage((Message)message, null, null);
        message.sendToTarget();
    }

    @Override
    public void getCellBroadcastSmsConfig(Message message) {
        Rlog.e((String)"SipPhoneBase", (String)"Error! This functionality is not implemented for SIP.");
    }

    @Override
    public PhoneInternalInterface.DataActivityState getDataActivityState() {
        return PhoneInternalInterface.DataActivityState.NONE;
    }

    @Override
    public PhoneConstants.DataState getDataConnectionState() {
        return PhoneConstants.DataState.DISCONNECTED;
    }

    @Override
    public boolean getDataRoamingEnabled() {
        return false;
    }

    @Override
    public String getDeviceId() {
        return null;
    }

    @Override
    public String getDeviceSvn() {
        return null;
    }

    @Override
    public String getEsn() {
        Rlog.e((String)"SipPhoneBase", (String)"[SipPhone] getEsn() is a CDMA method");
        return "0";
    }

    @Override
    public abstract Call getForegroundCall();

    @Override
    public String getGroupIdLevel1() {
        return null;
    }

    @Override
    public String getGroupIdLevel2() {
        return null;
    }

    @Override
    public IccCard getIccCard() {
        return null;
    }

    @Override
    public IccFileHandler getIccFileHandler() {
        return null;
    }

    @Override
    public IccPhoneBookInterfaceManager getIccPhoneBookInterfaceManager() {
        return null;
    }

    @Override
    public boolean getIccRecordsLoaded() {
        return false;
    }

    @Override
    public String getIccSerialNumber() {
        return null;
    }

    @Override
    public String getImei() {
        return null;
    }

    @Override
    public String getLine1AlphaTag() {
        return null;
    }

    @Override
    public String getLine1Number() {
        return null;
    }

    @Override
    public LinkProperties getLinkProperties(String string) {
        return null;
    }

    @Override
    public String getMeid() {
        Rlog.e((String)"SipPhoneBase", (String)"[SipPhone] getMeid() is a CDMA method");
        return "0";
    }

    @Override
    public boolean getMessageWaitingIndicator() {
        return false;
    }

    @Override
    public void getOutgoingCallerIdDisplay(Message message) {
        AsyncResult.forMessage((Message)message, null, null);
        message.sendToTarget();
    }

    @Override
    public List<? extends MmiCode> getPendingMmiCodes() {
        return new ArrayList<E>(0);
    }

    @Override
    public int getPhoneType() {
        return 3;
    }

    @Override
    public abstract Call getRingingCall();

    @Override
    public ServiceState getServiceState() {
        ServiceState serviceState = new ServiceState();
        serviceState.setVoiceRegState(0);
        return serviceState;
    }

    @Override
    public SignalStrength getSignalStrength() {
        return new SignalStrength();
    }

    @Override
    public PhoneConstants.State getState() {
        return this.mState;
    }

    @Override
    public String getSubscriberId() {
        return null;
    }

    @Override
    public String getVoiceMailAlphaTag() {
        return null;
    }

    @Override
    public String getVoiceMailNumber() {
        return null;
    }

    @Override
    public boolean handleInCallMmiCommands(String string) {
        return false;
    }

    @Override
    public boolean handlePinMmi(String string) {
        return false;
    }

    @Override
    public boolean handleUssdRequest(String string, ResultReceiver resultReceiver) {
        return false;
    }

    @Override
    public boolean isDataAllowed(int n) {
        return false;
    }

    boolean isInCall() {
        Call.State state = this.getForegroundCall().getState();
        Call.State state2 = this.getBackgroundCall().getState();
        Call.State state3 = this.getRingingCall().getState();
        boolean bl = state.isAlive() || state2.isAlive() || state3.isAlive();
        return bl;
    }

    @Override
    public boolean isUserDataEnabled() {
        return false;
    }

    @Override
    public boolean isVideoEnabled() {
        return false;
    }

    void migrateFrom(SipPhoneBase sipPhoneBase) {
        super.migrateFrom(sipPhoneBase);
        this.migrate(this.mRingbackRegistrants, sipPhoneBase.mRingbackRegistrants);
    }

    @Override
    public boolean needsOtaServiceProvisioning() {
        return false;
    }

    @Override
    public void notifyCallForwardingIndicator() {
        this.mNotifier.notifyCallForwardingChanged(this);
    }

    void notifyDisconnect(Connection connection) {
        this.mDisconnectRegistrants.notifyResult((Object)connection);
    }

    void notifyNewRingingConnection(Connection connection) {
        super.notifyNewRingingConnectionP(connection);
    }

    void notifyPhoneStateChanged() {
    }

    void notifyPreciseCallStateChanged() {
        super.notifyPreciseCallStateChangedP();
    }

    void notifyServiceStateChanged(ServiceState serviceState) {
        super.notifyServiceStateChangedP(serviceState);
    }

    void notifySuppServiceFailed(PhoneInternalInterface.SuppService suppService) {
        this.mSuppServiceFailedRegistrants.notifyResult((Object)((Object)suppService));
    }

    void notifyUnknownConnection() {
        this.mUnknownConnectionRegistrants.notifyResult((Object)this);
    }

    @Override
    protected void onUpdateIccAvailability() {
    }

    @Override
    public void registerForRingbackTone(Handler handler, int n, Object object) {
        this.mRingbackRegistrants.addUnique(handler, n, object);
    }

    @Override
    public void registerForSuppServiceNotification(Handler handler, int n, Object object) {
    }

    @Override
    public void saveClirSetting(int n) {
    }

    @Override
    public void selectNetworkManually(OperatorInfo operatorInfo, boolean bl, Message message) {
    }

    @Override
    public void sendEmergencyCallStateChange(boolean bl) {
    }

    @Override
    public void sendUssdResponse(String string) {
    }

    @Override
    public void setBroadcastEmergencyCallStateChanges(boolean bl) {
    }

    @Override
    public void setCallBarring(String string, boolean bl, String string2, Message message, int n) {
    }

    @Override
    public void setCallForwardingOption(int n, int n2, String string, int n3, Message message) {
    }

    @Override
    public void setCallWaiting(boolean bl, Message message) {
        Rlog.e((String)"SipPhoneBase", (String)"call waiting not supported");
    }

    @Override
    public void setCellBroadcastSmsConfig(int[] arrn, Message message) {
        Rlog.e((String)"SipPhoneBase", (String)"Error! This functionality is not implemented for SIP.");
    }

    @Override
    public void setDataRoamingEnabled(boolean bl) {
    }

    @Override
    public boolean setLine1Number(String string, String string2, Message message) {
        return false;
    }

    @Override
    public void setNetworkSelectionModeAutomatic(Message message) {
    }

    @Override
    public void setOnPostDialCharacter(Handler handler, int n, Object object) {
    }

    @Override
    public void setOutgoingCallerIdDisplay(int n, Message message) {
        AsyncResult.forMessage((Message)message, null, null);
        message.sendToTarget();
    }

    @Override
    public void setRadioPower(boolean bl) {
    }

    @Override
    public void setVoiceMailNumber(String string, String string2, Message message) {
        AsyncResult.forMessage((Message)message, null, null);
        message.sendToTarget();
    }

    @Override
    public void startNetworkScan(NetworkScanRequest networkScanRequest, Message message) {
    }

    @Override
    public void startRingbackTone() {
        AsyncResult asyncResult = new AsyncResult(null, (Object)Boolean.TRUE, null);
        this.mRingbackRegistrants.notifyRegistrants(asyncResult);
    }

    @Override
    public void stopNetworkScan(Message message) {
    }

    @Override
    public void stopRingbackTone() {
        AsyncResult asyncResult = new AsyncResult(null, (Object)Boolean.FALSE, null);
        this.mRingbackRegistrants.notifyRegistrants(asyncResult);
    }

    @Override
    public void unregisterForRingbackTone(Handler handler) {
        this.mRingbackRegistrants.remove(handler);
    }

    @Override
    public void unregisterForSuppServiceNotification(Handler handler) {
    }

    void updatePhoneState() {
        Object object = this.mState;
        this.mState = this.getRingingCall().isRinging() ? PhoneConstants.State.RINGING : (this.getForegroundCall().isIdle() && this.getBackgroundCall().isIdle() ? PhoneConstants.State.IDLE : PhoneConstants.State.OFFHOOK);
        if (this.mState != object) {
            object = new StringBuilder();
            ((StringBuilder)object).append(" ^^^ new phone state: ");
            ((StringBuilder)object).append((Object)this.mState);
            Rlog.d((String)"SipPhoneBase", (String)((StringBuilder)object).toString());
            this.notifyPhoneStateChanged();
        }
    }

    @Override
    public void updateServiceLocation() {
    }
}

