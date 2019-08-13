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
 *  android.os.SystemProperties
 *  android.telephony.CallQuality
 *  android.telephony.NetworkScanRequest
 *  android.telephony.Rlog
 *  android.telephony.ServiceState
 *  android.telephony.SignalStrength
 *  android.telephony.ims.ImsReasonInfo
 *  android.util.Pair
 *  com.android.internal.annotations.VisibleForTesting
 *  com.android.internal.telephony.OperatorInfo
 *  com.android.internal.telephony.PhoneConstants
 *  com.android.internal.telephony.PhoneConstants$DataState
 *  com.android.internal.telephony.PhoneConstants$State
 */
package com.android.internal.telephony.imsphone;

import android.content.Context;
import android.net.LinkProperties;
import android.os.AsyncResult;
import android.os.Handler;
import android.os.Message;
import android.os.RegistrantList;
import android.os.SystemProperties;
import android.telephony.CallQuality;
import android.telephony.NetworkScanRequest;
import android.telephony.Rlog;
import android.telephony.ServiceState;
import android.telephony.SignalStrength;
import android.telephony.ims.ImsReasonInfo;
import android.util.Pair;
import com.android.internal.annotations.VisibleForTesting;
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
import com.android.internal.telephony.dataconnection.DataConnection;
import com.android.internal.telephony.imsphone.ImsPhoneCommandInterface;
import com.android.internal.telephony.uicc.IccFileHandler;
import java.util.ArrayList;
import java.util.List;

abstract class ImsPhoneBase
extends Phone {
    private static final String LOG_TAG = "ImsPhoneBase";
    private RegistrantList mOnHoldRegistrants = new RegistrantList();
    private RegistrantList mRingbackRegistrants = new RegistrantList();
    private PhoneConstants.State mState = PhoneConstants.State.IDLE;
    private RegistrantList mTtyModeReceivedRegistrants = new RegistrantList();

    public ImsPhoneBase(String string, Context context, PhoneNotifier phoneNotifier, boolean bl) {
        super(string, phoneNotifier, context, new ImsPhoneCommandInterface(context), bl);
    }

    @Override
    public void activateCellBroadcastSms(int n, Message message) {
        Rlog.e((String)LOG_TAG, (String)"Error! This functionality is not implemented for Volte.");
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
        Rlog.e((String)LOG_TAG, (String)"Error! This functionality is not implemented for Volte.");
    }

    public List<DataConnection> getCurrentDataConnectionList() {
        return null;
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
        Rlog.e((String)LOG_TAG, (String)"[VoltePhone] getEsn() is a CDMA method");
        return "0";
    }

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
        Rlog.e((String)LOG_TAG, (String)"[VoltePhone] getMeid() is a CDMA method");
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
        return new ArrayList(0);
    }

    @Override
    public int getPhoneType() {
        return 5;
    }

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
    public void migrateFrom(Phone phone) {
        super.migrateFrom(phone);
        this.migrate(this.mRingbackRegistrants, ((ImsPhoneBase)phone).mRingbackRegistrants);
    }

    @Override
    public boolean needsOtaServiceProvisioning() {
        return false;
    }

    @Override
    public void notifyCallForwardingIndicator() {
        this.mNotifier.notifyCallForwardingChanged(this);
    }

    public void notifyDisconnect(Connection connection) {
        this.mDisconnectRegistrants.notifyResult((Object)connection);
    }

    public void notifyImsReason(ImsReasonInfo imsReasonInfo) {
        this.mNotifier.notifyImsDisconnectCause(this, imsReasonInfo);
    }

    public void notifyPhoneStateChanged() {
        this.mNotifier.notifyPhoneState(this);
    }

    public void notifyPreciseCallStateChanged() {
        super.notifyPreciseCallStateChangedP();
    }

    void notifyServiceStateChanged(ServiceState serviceState) {
        super.notifyServiceStateChangedP(serviceState);
    }

    public void notifySuppServiceFailed(PhoneInternalInterface.SuppService suppService) {
        this.mSuppServiceFailedRegistrants.notifyResult((Object)suppService);
    }

    void notifyUnknownConnection() {
        this.mUnknownConnectionRegistrants.notifyResult((Object)this);
    }

    public void onCallQualityChanged(CallQuality callQuality, int n) {
        this.mNotifier.notifyCallQualityChanged(this, callQuality, n);
    }

    public void onTtyModeReceived(int n) {
        AsyncResult asyncResult = new AsyncResult(null, (Object)n, null);
        this.mTtyModeReceivedRegistrants.notifyRegistrants(asyncResult);
    }

    @Override
    protected void onUpdateIccAvailability() {
    }

    @Override
    public void registerForOnHoldTone(Handler handler, int n, Object object) {
        this.mOnHoldRegistrants.addUnique(handler, n, object);
    }

    @Override
    public void registerForRingbackTone(Handler handler, int n, Object object) {
        this.mRingbackRegistrants.addUnique(handler, n, object);
    }

    @Override
    public void registerForSuppServiceNotification(Handler handler, int n, Object object) {
    }

    @Override
    public void registerForTtyModeReceived(Handler handler, int n, Object object) {
        this.mTtyModeReceivedRegistrants.addUnique(handler, n, object);
    }

    @Override
    public void selectNetworkManually(OperatorInfo operatorInfo, boolean bl, Message message) {
    }

    @Override
    public void sendUssdResponse(String string) {
    }

    @Override
    public void setCallBarring(String string, boolean bl, String string2, Message message, int n) {
    }

    @Override
    public void setCallForwardingOption(int n, int n2, String string, int n3, Message message) {
    }

    @Override
    public void setCallWaiting(boolean bl, Message message) {
        Rlog.e((String)LOG_TAG, (String)"call waiting not supported");
    }

    @Override
    public void setCellBroadcastSmsConfig(int[] arrn, Message message) {
        Rlog.e((String)LOG_TAG, (String)"Error! This functionality is not implemented for Volte.");
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

    @VisibleForTesting
    public void startOnHoldTone(Connection connection) {
        connection = new Pair((Object)connection, (Object)Boolean.TRUE);
        this.mOnHoldRegistrants.notifyRegistrants(new AsyncResult(null, (Object)connection, null));
    }

    @Override
    public void startRingbackTone() {
        AsyncResult asyncResult = new AsyncResult(null, (Object)Boolean.TRUE, null);
        this.mRingbackRegistrants.notifyRegistrants(asyncResult);
    }

    @Override
    public void stopNetworkScan(Message message) {
    }

    protected void stopOnHoldTone(Connection connection) {
        connection = new Pair((Object)connection, (Object)Boolean.FALSE);
        this.mOnHoldRegistrants.notifyRegistrants(new AsyncResult(null, (Object)connection, null));
    }

    @Override
    public void stopRingbackTone() {
        AsyncResult asyncResult = new AsyncResult(null, (Object)Boolean.FALSE, null);
        this.mRingbackRegistrants.notifyRegistrants(asyncResult);
    }

    @Override
    public void unregisterForOnHoldTone(Handler handler) {
        this.mOnHoldRegistrants.remove(handler);
    }

    @Override
    public void unregisterForRingbackTone(Handler handler) {
        this.mRingbackRegistrants.remove(handler);
    }

    @Override
    public void unregisterForSuppServiceNotification(Handler handler) {
    }

    @Override
    public void unregisterForTtyModeReceived(Handler handler) {
        this.mTtyModeReceivedRegistrants.remove(handler);
    }

    void updatePhoneState() {
        Object object = this.mState;
        this.mState = this.getRingingCall().isRinging() ? PhoneConstants.State.RINGING : (this.getForegroundCall().isIdle() && this.getBackgroundCall().isIdle() ? PhoneConstants.State.IDLE : PhoneConstants.State.OFFHOOK);
        if (this.mState != object) {
            object = new StringBuilder();
            ((StringBuilder)object).append(" ^^^ new phone state: ");
            ((StringBuilder)object).append((Object)this.mState);
            Rlog.d((String)LOG_TAG, (String)((StringBuilder)object).toString());
            this.notifyPhoneStateChanged();
        }
    }

    @Override
    public void updateServiceLocation() {
    }
}

