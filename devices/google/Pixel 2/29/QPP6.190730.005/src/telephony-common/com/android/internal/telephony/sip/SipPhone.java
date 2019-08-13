/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.media.AudioManager
 *  android.net.LinkProperties
 *  android.net.rtp.AudioGroup
 *  android.net.sip.SipAudioCall
 *  android.net.sip.SipAudioCall$Listener
 *  android.net.sip.SipErrorCode
 *  android.net.sip.SipException
 *  android.net.sip.SipManager
 *  android.net.sip.SipProfile
 *  android.net.sip.SipProfile$Builder
 *  android.os.AsyncResult
 *  android.os.Handler
 *  android.os.Message
 *  android.os.ResultReceiver
 *  android.telephony.NetworkScanRequest
 *  android.telephony.PhoneNumberUtils
 *  android.telephony.Rlog
 *  android.telephony.ServiceState
 *  android.telephony.SignalStrength
 *  android.text.TextUtils
 *  com.android.internal.telephony.OperatorInfo
 *  com.android.internal.telephony.PhoneConstants
 *  com.android.internal.telephony.PhoneConstants$DataState
 *  com.android.internal.telephony.PhoneConstants$State
 */
package com.android.internal.telephony.sip;

import android.content.Context;
import android.media.AudioManager;
import android.net.LinkProperties;
import android.net.rtp.AudioGroup;
import android.net.sip.SipAudioCall;
import android.net.sip.SipErrorCode;
import android.net.sip.SipException;
import android.net.sip.SipManager;
import android.net.sip.SipProfile;
import android.os.AsyncResult;
import android.os.Handler;
import android.os.Message;
import android.os.ResultReceiver;
import android.telephony.NetworkScanRequest;
import android.telephony.PhoneNumberUtils;
import android.telephony.Rlog;
import android.telephony.ServiceState;
import android.telephony.SignalStrength;
import android.text.TextUtils;
import com.android.internal.telephony.Call;
import com.android.internal.telephony.CallStateException;
import com.android.internal.telephony.Connection;
import com.android.internal.telephony.IccCard;
import com.android.internal.telephony.IccPhoneBookInterfaceManager;
import com.android.internal.telephony.MmiCode;
import com.android.internal.telephony.OperatorInfo;
import com.android.internal.telephony.Phone;
import com.android.internal.telephony.PhoneConstants;
import com.android.internal.telephony.PhoneInternalInterface;
import com.android.internal.telephony.PhoneNotifier;
import com.android.internal.telephony.sip.SipCallBase;
import com.android.internal.telephony.sip.SipConnectionBase;
import com.android.internal.telephony.sip.SipPhoneBase;
import com.android.internal.telephony.uicc.IccFileHandler;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;

public class SipPhone
extends SipPhoneBase {
    private static final boolean DBG = true;
    private static final String LOG_TAG = "SipPhone";
    private static final int TIMEOUT_ANSWER_CALL = 8;
    private static final int TIMEOUT_HOLD_CALL = 15;
    private static final long TIMEOUT_HOLD_PROCESSING = 1000L;
    private static final int TIMEOUT_MAKE_CALL = 15;
    private static final boolean VDBG = false;
    private SipCall mBackgroundCall;
    private SipCall mForegroundCall;
    private SipProfile mProfile;
    private SipCall mRingingCall;
    private SipManager mSipManager;
    private long mTimeOfLastValidHoldRequest;

    SipPhone(Context context, PhoneNotifier object, SipProfile sipProfile) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("SIP:");
        stringBuilder.append(sipProfile.getUriString());
        super(stringBuilder.toString(), context, (PhoneNotifier)object);
        this.mRingingCall = new SipCall();
        this.mForegroundCall = new SipCall();
        this.mBackgroundCall = new SipCall();
        this.mTimeOfLastValidHoldRequest = System.currentTimeMillis();
        object = new StringBuilder();
        ((StringBuilder)object).append("new SipPhone: ");
        ((StringBuilder)object).append(SipPhone.hidePii(sipProfile.getUriString()));
        this.log(((StringBuilder)object).toString());
        this.mRingingCall = new SipCall();
        this.mForegroundCall = new SipCall();
        this.mBackgroundCall = new SipCall();
        this.mProfile = sipProfile;
        this.mSipManager = SipManager.newInstance((Context)context);
    }

    private Connection dialInternal(String object, int n) throws CallStateException {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("dialInternal: dialString=");
        stringBuilder.append(SipPhone.hidePii((String)object));
        this.log(stringBuilder.toString());
        this.clearDisconnected();
        if (this.canDial()) {
            if (this.mForegroundCall.getState() == Call.State.ACTIVE) {
                this.switchHoldingAndActive();
            }
            if (this.mForegroundCall.getState() == Call.State.IDLE) {
                this.mForegroundCall.setMute(false);
                try {
                    object = this.mForegroundCall.dial((String)object);
                    return object;
                }
                catch (SipException sipException) {
                    this.loge("dialInternal: ", (Exception)((Object)sipException));
                    object = new StringBuilder();
                    ((StringBuilder)object).append("dial error: ");
                    ((StringBuilder)object).append((Object)sipException);
                    throw new CallStateException(((StringBuilder)object).toString());
                }
            }
            throw new CallStateException("cannot dial in current state");
        }
        throw new CallStateException("dialInternal: cannot dial in current state");
    }

    private static Call.State getCallStateFrom(SipAudioCall object) {
        if (object.isOnHold()) {
            return Call.State.HOLDING;
        }
        int n = object.getState();
        if (n != 0) {
            switch (n) {
                default: {
                    object = new StringBuilder();
                    ((StringBuilder)object).append("illegal connection state: ");
                    ((StringBuilder)object).append(n);
                    SipPhone.slog(((StringBuilder)object).toString());
                    return Call.State.DISCONNECTED;
                }
                case 8: {
                    return Call.State.ACTIVE;
                }
                case 7: {
                    return Call.State.DISCONNECTING;
                }
                case 6: {
                    return Call.State.ALERTING;
                }
                case 5: {
                    return Call.State.DIALING;
                }
                case 3: 
                case 4: 
            }
            return Call.State.INCOMING;
        }
        return Call.State.IDLE;
    }

    private String getSipDomain(SipProfile object) {
        if (((String)(object = object.getSipDomain())).endsWith(":5060")) {
            return ((String)object).substring(0, ((String)object).length() - 5);
        }
        return object;
    }

    private String getUriString(SipProfile sipProfile) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(sipProfile.getUserName());
        stringBuilder.append("@");
        stringBuilder.append(this.getSipDomain(sipProfile));
        return stringBuilder.toString();
    }

    public static String hidePii(String string) {
        return "xxxxx";
    }

    private boolean isHoldTimeoutExpired() {
        synchronized (this) {
            long l = System.currentTimeMillis();
            if (l - this.mTimeOfLastValidHoldRequest > 1000L) {
                this.mTimeOfLastValidHoldRequest = l;
                return true;
            }
            return false;
        }
    }

    private void log(String string) {
        Rlog.d((String)LOG_TAG, (String)string);
    }

    private void loge(String string) {
        Rlog.e((String)LOG_TAG, (String)string);
    }

    private void loge(String string, Exception exception) {
        Rlog.e((String)LOG_TAG, (String)string, (Throwable)exception);
    }

    private static void slog(String string) {
        Rlog.d((String)LOG_TAG, (String)string);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void acceptCall(int n) throws CallStateException {
        synchronized (SipPhone.class) {
            if (this.mRingingCall.getState() != Call.State.INCOMING && this.mRingingCall.getState() != Call.State.WAITING) {
                this.log("acceptCall: throw CallStateException(\"phone not ringing\")");
                CallStateException callStateException = new CallStateException("phone not ringing");
                throw callStateException;
            }
            this.log("acceptCall: accepting");
            this.mRingingCall.setMute(false);
            this.mRingingCall.acceptCall();
            return;
        }
    }

    @Override
    public boolean canConference() {
        this.log("canConference: ret=true");
        return true;
    }

    @Override
    public boolean canTransfer() {
        return false;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void clearDisconnected() {
        synchronized (SipPhone.class) {
            this.mRingingCall.clearDisconnected();
            this.mForegroundCall.clearDisconnected();
            this.mBackgroundCall.clearDisconnected();
            this.updatePhoneState();
            this.notifyPreciseCallStateChanged();
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void conference() throws CallStateException {
        synchronized (SipPhone.class) {
            if (this.mForegroundCall.getState() == Call.State.ACTIVE && this.mForegroundCall.getState() == Call.State.ACTIVE) {
                this.log("conference: merge fg & bg");
                this.mForegroundCall.merge(this.mBackgroundCall);
                return;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("wrong state to merge calls: fg=");
            stringBuilder.append((Object)this.mForegroundCall.getState());
            stringBuilder.append(", bg=");
            stringBuilder.append((Object)this.mBackgroundCall.getState());
            CallStateException callStateException = new CallStateException(stringBuilder.toString());
            throw callStateException;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void conference(Call call) throws CallStateException {
        synchronized (SipPhone.class) {
            if (call instanceof SipCall) {
                this.mForegroundCall.merge((SipCall)call);
                return;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("expect ");
            stringBuilder.append(SipCall.class);
            stringBuilder.append(", cannot merge with ");
            stringBuilder.append(call.getClass());
            CallStateException callStateException = new CallStateException(stringBuilder.toString());
            throw callStateException;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public Connection dial(String object, PhoneInternalInterface.DialArgs dialArgs) throws CallStateException {
        synchronized (SipPhone.class) {
            return this.dialInternal((String)object, dialArgs.videoState);
        }
    }

    public boolean equals(SipPhone sipPhone) {
        return this.getSipUri().equals(sipPhone.getSipUri());
    }

    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (!(object instanceof SipPhone)) {
            return false;
        }
        object = (SipPhone)object;
        return this.mProfile.getUriString().equals(((SipPhone)object).mProfile.getUriString());
    }

    @Override
    public void explicitCallTransfer() {
    }

    @Override
    public Call getBackgroundCall() {
        return this.mBackgroundCall;
    }

    @Override
    public void getCallWaiting(Message message) {
        AsyncResult.forMessage((Message)message, null, null);
        message.sendToTarget();
    }

    @Override
    public Call getForegroundCall() {
        return this.mForegroundCall;
    }

    @Override
    public boolean getMute() {
        boolean bl = this.mForegroundCall.getState().isAlive() ? this.mForegroundCall.getMute() : this.mBackgroundCall.getMute();
        return bl;
    }

    @Override
    public void getOutgoingCallerIdDisplay(Message message) {
        AsyncResult.forMessage((Message)message, null, null);
        message.sendToTarget();
    }

    @Override
    public Call getRingingCall() {
        return this.mRingingCall;
    }

    @Override
    public ServiceState getServiceState() {
        return super.getServiceState();
    }

    public String getSipUri() {
        return this.mProfile.getUriString();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void rejectCall() throws CallStateException {
        synchronized (SipPhone.class) {
            if (this.mRingingCall.getState().isRinging()) {
                this.log("rejectCall: rejecting");
                this.mRingingCall.rejectCall();
                return;
            }
            this.log("rejectCall: throw CallStateException(\"phone not ringing\")");
            CallStateException callStateException = new CallStateException("phone not ringing");
            throw callStateException;
        }
    }

    public void sendBurstDtmf(String string) {
        this.loge("sendBurstDtmf() is a CDMA method");
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void sendDtmf(char c) {
        if (!PhoneNumberUtils.is12Key((char)c)) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("sendDtmf called with invalid character '");
            stringBuilder.append(c);
            stringBuilder.append("'");
            this.loge(stringBuilder.toString());
            return;
        }
        if (!this.mForegroundCall.getState().isAlive()) return;
        synchronized (SipPhone.class) {
            this.mForegroundCall.sendDtmf(c);
            return;
        }
    }

    @Override
    public void setCallWaiting(boolean bl, Message message) {
        this.loge("call waiting not supported");
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void setEchoSuppressionEnabled() {
        synchronized (SipPhone.class) {
            if (((AudioManager)this.mContext.getSystemService("audio")).getParameters("ec_supported").contains("off")) {
                this.mForegroundCall.setAudioGroupMode();
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void setMute(boolean bl) {
        synchronized (SipPhone.class) {
            this.mForegroundCall.setMute(bl);
            return;
        }
    }

    @Override
    public void setOutgoingCallerIdDisplay(int n, Message message) {
        AsyncResult.forMessage((Message)message, null, null);
        message.sendToTarget();
    }

    @Override
    public void startDtmf(char c) {
        if (!PhoneNumberUtils.is12Key((char)c)) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("startDtmf called with invalid character '");
            stringBuilder.append(c);
            stringBuilder.append("'");
            this.loge(stringBuilder.toString());
        } else {
            this.sendDtmf(c);
        }
    }

    @Override
    public void stopDtmf() {
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void switchHoldingAndActive() throws CallStateException {
        if (!this.isHoldTimeoutExpired()) {
            this.log("switchHoldingAndActive: Disregarded! Under 1000 ms...");
            return;
        }
        this.log("switchHoldingAndActive: switch fg and bg");
        synchronized (SipPhone.class) {
            this.mForegroundCall.switchWith(this.mBackgroundCall);
            if (this.mBackgroundCall.getState().isAlive()) {
                this.mBackgroundCall.hold();
            }
            if (this.mForegroundCall.getState().isAlive()) {
                this.mForegroundCall.unhold();
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public Connection takeIncomingCall(Object object) {
        synchronized (SipPhone.class) {
            if (!(object instanceof SipAudioCall)) {
                this.log("takeIncomingCall: ret=null, not a SipAudioCall");
                return null;
            }
            if (this.mRingingCall.getState().isAlive()) {
                this.log("takeIncomingCall: ret=null, ringingCall not alive");
                return null;
            }
            if (this.mForegroundCall.getState().isAlive() && this.mBackgroundCall.getState().isAlive()) {
                this.log("takeIncomingCall: ret=null, foreground and background both alive");
                return null;
            }
            try {
                SipAudioCall sipAudioCall = (SipAudioCall)object;
                object = new StringBuilder();
                ((StringBuilder)object).append("takeIncomingCall: taking call from: ");
                ((StringBuilder)object).append(SipPhone.hidePii(sipAudioCall.getPeerProfile().getUriString()));
                this.log(((StringBuilder)object).toString());
                if (sipAudioCall.getLocalProfile().getUriString().equals(this.mProfile.getUriString())) {
                    boolean bl = this.mForegroundCall.getState().isAlive();
                    object = this.mRingingCall.initIncomingCall(sipAudioCall, bl);
                    if (sipAudioCall.getState() == 3) return object;
                    this.log("    takeIncomingCall: call cancelled !!");
                    this.mRingingCall.reset();
                    return null;
                }
            }
            catch (Exception exception) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("    takeIncomingCall: exception e=");
                stringBuilder.append(exception);
                this.log(stringBuilder.toString());
                this.mRingingCall.reset();
            }
            this.log("takeIncomingCall: NOT taking !!");
            return null;
        }
    }

    private abstract class SipAudioCallAdapter
    extends SipAudioCall.Listener {
        private static final boolean SACA_DBG = true;
        private static final String SACA_TAG = "SipAudioCallAdapter";

        private SipAudioCallAdapter() {
        }

        private void log(String string) {
            Rlog.d((String)SACA_TAG, (String)string);
        }

        public void onCallBusy(SipAudioCall sipAudioCall) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("onCallBusy: call=");
            stringBuilder.append((Object)sipAudioCall);
            this.log(stringBuilder.toString());
            this.onCallEnded(4);
        }

        protected abstract void onCallEnded(int var1);

        public void onCallEnded(SipAudioCall sipAudioCall) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("onCallEnded: call=");
            stringBuilder.append((Object)sipAudioCall);
            this.log(stringBuilder.toString());
            int n = sipAudioCall.isInCall() ? 2 : 1;
            this.onCallEnded(n);
        }

        protected abstract void onError(int var1);

        public void onError(SipAudioCall sipAudioCall, int n, String string) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("onError: call=");
            stringBuilder.append((Object)sipAudioCall);
            stringBuilder.append(" code=");
            stringBuilder.append(SipErrorCode.toString((int)n));
            stringBuilder.append(": ");
            stringBuilder.append(string);
            this.log(stringBuilder.toString());
            switch (n) {
                default: {
                    this.onError(36);
                    break;
                }
                case -2: {
                    this.onError(12);
                    break;
                }
                case -5: 
                case -3: {
                    this.onError(13);
                    break;
                }
                case -6: {
                    this.onError(7);
                    break;
                }
                case -7: {
                    this.onError(8);
                    break;
                }
                case -8: {
                    this.onError(10);
                    break;
                }
                case -10: {
                    this.onError(14);
                    break;
                }
                case -11: {
                    this.onError(11);
                    break;
                }
                case -12: {
                    this.onError(9);
                }
            }
        }
    }

    private class SipCall
    extends SipCallBase {
        private static final boolean SC_DBG = true;
        private static final String SC_TAG = "SipCall";
        private static final boolean SC_VDBG = false;

        private SipCall() {
        }

        private void add(SipConnection sipConnection) {
            this.log("add:");
            Call call = sipConnection.getCall();
            if (call == this) {
                return;
            }
            if (call != null) {
                ((SipCall)call).mConnections.remove(sipConnection);
            }
            this.mConnections.add(sipConnection);
            sipConnection.changeOwner(this);
        }

        private int convertDtmf(char c) {
            int n = c - 48;
            if (n >= 0 && n <= 9) {
                return n;
            }
            if (c != '#') {
                if (c != '*') {
                    switch (c) {
                        default: {
                            StringBuilder stringBuilder = new StringBuilder();
                            stringBuilder.append("invalid DTMF char: ");
                            stringBuilder.append((int)c);
                            throw new IllegalArgumentException(stringBuilder.toString());
                        }
                        case 'D': {
                            return 15;
                        }
                        case 'C': {
                            return 14;
                        }
                        case 'B': {
                            return 13;
                        }
                        case 'A': 
                    }
                    return 12;
                }
                return 10;
            }
            return 11;
        }

        private AudioGroup getAudioGroup() {
            if (this.mConnections.isEmpty()) {
                return null;
            }
            return ((SipConnection)this.mConnections.get(0)).getAudioGroup();
        }

        private boolean isSpeakerOn() {
            return ((AudioManager)SipPhone.this.mContext.getSystemService("audio")).isSpeakerphoneOn();
        }

        private void log(String string) {
            Rlog.d((String)SC_TAG, (String)string);
        }

        private void takeOver(SipCall object) {
            this.log("takeOver");
            this.mConnections = ((SipCall)object).mConnections;
            this.mState = ((SipCall)object).mState;
            object = this.mConnections.iterator();
            while (object.hasNext()) {
                ((SipConnection)((Connection)object.next())).changeOwner(this);
            }
        }

        void acceptCall() throws CallStateException {
            this.log("acceptCall: accepting");
            if (this == SipPhone.this.mRingingCall) {
                if (this.mConnections.size() == 1) {
                    ((SipConnection)this.mConnections.get(0)).acceptCall();
                    return;
                }
                throw new CallStateException("acceptCall() in a conf call");
            }
            throw new CallStateException("acceptCall() in a non-ringing call");
        }

        Connection dial(String string) throws SipException {
            Object object = new StringBuilder();
            ((StringBuilder)object).append("dial: num=");
            ((StringBuilder)object).append("xxx");
            this.log(((StringBuilder)object).toString());
            object = string;
            CharSequence charSequence = object;
            if (!((String)object).contains("@")) {
                charSequence = new StringBuilder();
                ((StringBuilder)charSequence).append(SipPhone.this.mProfile.getUserName());
                ((StringBuilder)charSequence).append("@");
                String string2 = Pattern.quote(((StringBuilder)charSequence).toString());
                String string3 = SipPhone.this.mProfile.getUriString();
                charSequence = new StringBuilder();
                ((StringBuilder)charSequence).append((String)object);
                ((StringBuilder)charSequence).append("@");
                charSequence = string3.replaceFirst(string2, ((StringBuilder)charSequence).toString());
            }
            try {
                object = new SipProfile.Builder((String)charSequence);
                charSequence = object.build();
                object = new SipConnection(this, (SipProfile)charSequence, string);
                ((SipConnection)object).dial();
                this.mConnections.add(object);
                this.setState(Call.State.DIALING);
                return object;
            }
            catch (ParseException parseException) {
                throw new SipException("dial", (Throwable)parseException);
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public List<Connection> getConnections() {
            synchronized (SipPhone.class) {
                return this.mConnections;
            }
        }

        boolean getMute() {
            boolean bl = this.mConnections.isEmpty();
            boolean bl2 = false;
            if (!bl) {
                bl2 = ((SipConnection)this.mConnections.get(0)).getMute();
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("getMute: ret=");
            stringBuilder.append(bl2);
            this.log(stringBuilder.toString());
            return bl2;
        }

        @Override
        public Phone getPhone() {
            return SipPhone.this;
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void hangup() throws CallStateException {
            synchronized (SipPhone.class) {
                if (!this.mState.isAlive()) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("hangup: dead call ");
                    stringBuilder.append((Object)this.getState());
                    stringBuilder.append(": ");
                    stringBuilder.append(this);
                    stringBuilder.append(" on phone ");
                    stringBuilder.append(this.getPhone());
                    this.log(stringBuilder.toString());
                } else {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("hangup: call ");
                    stringBuilder.append((Object)this.getState());
                    stringBuilder.append(": ");
                    stringBuilder.append(this);
                    stringBuilder.append(" on phone ");
                    stringBuilder.append(this.getPhone());
                    this.log(stringBuilder.toString());
                    this.setState(Call.State.DISCONNECTING);
                    stringBuilder = null;
                    for (Connection connection : this.mConnections) {
                        try {
                            connection.hangup();
                        }
                        catch (CallStateException callStateException) {}
                    }
                    if (stringBuilder != null) {
                        throw stringBuilder;
                    }
                }
                return;
            }
        }

        void hold() throws CallStateException {
            this.log("hold:");
            this.setState(Call.State.HOLDING);
            Iterator iterator = this.mConnections.iterator();
            while (iterator.hasNext()) {
                ((SipConnection)((Connection)iterator.next())).hold();
            }
            this.setAudioGroupMode();
        }

        SipConnection initIncomingCall(SipAudioCall sipAudioCall, boolean bl) {
            Object object = sipAudioCall.getPeerProfile();
            SipConnection sipConnection = new SipConnection(this, (SipProfile)object);
            this.mConnections.add(sipConnection);
            object = bl ? Call.State.WAITING : Call.State.INCOMING;
            sipConnection.initIncomingCall(sipAudioCall, (Call.State)((Object)object));
            this.setState((Call.State)((Object)object));
            SipPhone.this.notifyNewRingingConnectionP(sipConnection);
            return sipConnection;
        }

        void merge(SipCall sipCall) throws CallStateException {
            this.log("merge:");
            AudioGroup audioGroup = this.getAudioGroup();
            Connection[] arrconnection = sipCall.mConnections.toArray(new Connection[sipCall.mConnections.size()]);
            int n = arrconnection.length;
            for (int i = 0; i < n; ++i) {
                SipConnection sipConnection = (SipConnection)arrconnection[i];
                this.add(sipConnection);
                if (sipConnection.getState() != Call.State.HOLDING) continue;
                sipConnection.unhold(audioGroup);
            }
            sipCall.setState(Call.State.IDLE);
        }

        void onConnectionEnded(SipConnection sipConnection) {
            Object object = new StringBuilder();
            ((StringBuilder)object).append("onConnectionEnded: conn=");
            ((StringBuilder)object).append(sipConnection);
            this.log(((StringBuilder)object).toString());
            if (this.mState != Call.State.DISCONNECTED) {
                boolean bl;
                block3 : {
                    boolean bl2 = true;
                    object = new StringBuilder();
                    ((StringBuilder)object).append("---check connections: ");
                    ((StringBuilder)object).append(this.mConnections.size());
                    this.log(((StringBuilder)object).toString());
                    Iterator iterator = this.mConnections.iterator();
                    do {
                        bl = bl2;
                        if (!iterator.hasNext()) break block3;
                        object = (Connection)iterator.next();
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("   state=");
                        stringBuilder.append((Object)((Connection)object).getState());
                        stringBuilder.append(": ");
                        stringBuilder.append(object);
                        this.log(stringBuilder.toString());
                    } while (((Connection)object).getState() == Call.State.DISCONNECTED);
                    bl = false;
                }
                if (bl) {
                    this.setState(Call.State.DISCONNECTED);
                }
            }
            SipPhone.this.notifyDisconnectP(sipConnection);
        }

        void onConnectionStateChanged(SipConnection sipConnection) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("onConnectionStateChanged: conn=");
            stringBuilder.append(sipConnection);
            this.log(stringBuilder.toString());
            if (this.mState != Call.State.ACTIVE) {
                this.setState(sipConnection.getState());
            }
        }

        void rejectCall() throws CallStateException {
            this.log("rejectCall:");
            this.hangup();
        }

        void reset() {
            this.log("reset");
            this.mConnections.clear();
            this.setState(Call.State.IDLE);
        }

        void sendDtmf(char c) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("sendDtmf: c=");
            stringBuilder.append(c);
            this.log(stringBuilder.toString());
            stringBuilder = this.getAudioGroup();
            if (stringBuilder == null) {
                stringBuilder = new StringBuilder();
                stringBuilder.append("sendDtmf: audioGroup == null, ignore c=");
                stringBuilder.append(c);
                this.log(stringBuilder.toString());
                return;
            }
            stringBuilder.sendDtmf(this.convertDtmf(c));
        }

        void setAudioGroupMode() {
            AudioGroup audioGroup = this.getAudioGroup();
            if (audioGroup == null) {
                this.log("setAudioGroupMode: audioGroup == null ignore");
                return;
            }
            int n = audioGroup.getMode();
            if (this.mState == Call.State.HOLDING) {
                audioGroup.setMode(0);
            } else if (this.getMute()) {
                audioGroup.setMode(1);
            } else if (this.isSpeakerOn()) {
                audioGroup.setMode(3);
            } else {
                audioGroup.setMode(2);
            }
            this.log(String.format("setAudioGroupMode change: %d --> %d", n, audioGroup.getMode()));
        }

        void setMute(boolean bl) {
            Object object = new StringBuilder();
            ((StringBuilder)object).append("setMute: muted=");
            ((StringBuilder)object).append(bl);
            this.log(((StringBuilder)object).toString());
            object = this.mConnections.iterator();
            while (object.hasNext()) {
                ((SipConnection)((Connection)object.next())).setMute(bl);
            }
        }

        @Override
        protected void setState(Call.State state) {
            if (this.mState != state) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("setState: cur state");
                stringBuilder.append((Object)this.mState);
                stringBuilder.append(" --> ");
                stringBuilder.append((Object)state);
                stringBuilder.append(": ");
                stringBuilder.append(this);
                stringBuilder.append(": on phone ");
                stringBuilder.append(this.getPhone());
                stringBuilder.append(" ");
                stringBuilder.append(this.mConnections.size());
                this.log(stringBuilder.toString());
                if (state == Call.State.ALERTING) {
                    this.mState = state;
                    SipPhone.this.startRingbackTone();
                } else if (this.mState == Call.State.ALERTING) {
                    SipPhone.this.stopRingbackTone();
                }
                this.mState = state;
                SipPhone.this.updatePhoneState();
                SipPhone.this.notifyPreciseCallStateChanged();
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        void switchWith(SipCall sipCall) {
            this.log("switchWith");
            synchronized (SipPhone.class) {
                SipCall sipCall2 = new SipCall();
                sipCall2.takeOver(this);
                this.takeOver(sipCall);
                sipCall.takeOver(sipCall2);
                return;
            }
        }

        void unhold() throws CallStateException {
            this.log("unhold:");
            this.setState(Call.State.ACTIVE);
            AudioGroup audioGroup = new AudioGroup();
            Iterator iterator = this.mConnections.iterator();
            while (iterator.hasNext()) {
                ((SipConnection)((Connection)iterator.next())).unhold(audioGroup);
            }
            this.setAudioGroupMode();
        }
    }

    private class SipConnection
    extends SipConnectionBase {
        private static final boolean SCN_DBG = true;
        private static final String SCN_TAG = "SipConnection";
        private SipAudioCallAdapter mAdapter;
        private boolean mIncoming;
        private String mOriginalNumber;
        private SipCall mOwner;
        private SipProfile mPeer;
        private SipAudioCall mSipAudioCall;
        private Call.State mState;

        public SipConnection(SipCall sipCall, SipProfile sipProfile) {
            this(sipCall, sipProfile, sipPhone.getUriString(sipProfile));
        }

        public SipConnection(SipCall sipCall, SipProfile sipProfile, String string) {
            super(string);
            this.mState = Call.State.IDLE;
            this.mIncoming = false;
            this.mAdapter = new SipAudioCallAdapter(){

                /*
                 * Enabled aggressive block sorting
                 * Enabled unnecessary exception pruning
                 * Enabled aggressive exception aggregation
                 */
                @Override
                protected void onCallEnded(int n) {
                    if (SipConnection.this.getDisconnectCause() != 3) {
                        SipConnection.this.setDisconnectCause(n);
                    }
                    synchronized (SipPhone.class) {
                        CharSequence charSequence;
                        SipConnection.this.setState(Call.State.DISCONNECTED);
                        SipAudioCall sipAudioCall = SipConnection.this.mSipAudioCall;
                        SipConnection.this.mSipAudioCall = null;
                        if (sipAudioCall == null) {
                            charSequence = "";
                        } else {
                            charSequence = new StringBuilder();
                            ((StringBuilder)charSequence).append(sipAudioCall.getState());
                            ((StringBuilder)charSequence).append(", ");
                            charSequence = ((StringBuilder)charSequence).toString();
                        }
                        SipConnection sipConnection = SipConnection.this;
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("[SipAudioCallAdapter] onCallEnded: ");
                        stringBuilder.append(SipPhone.hidePii(SipConnection.this.mPeer.getUriString()));
                        stringBuilder.append(": ");
                        stringBuilder.append((String)charSequence);
                        stringBuilder.append("cause: ");
                        stringBuilder.append(SipConnection.this.getDisconnectCause());
                        stringBuilder.append(", on phone ");
                        stringBuilder.append(SipConnection.this.getPhone());
                        sipConnection.log(stringBuilder.toString());
                        if (sipAudioCall != null) {
                            sipAudioCall.setListener(null);
                            sipAudioCall.close();
                        }
                        SipConnection.this.mOwner.onConnectionEnded(SipConnection.this);
                        return;
                    }
                }

                public void onCallEstablished(SipAudioCall sipAudioCall) {
                    this.onChanged(sipAudioCall);
                    if (SipConnection.this.mState == Call.State.ACTIVE) {
                        sipAudioCall.startAudio();
                    }
                }

                public void onCallHeld(SipAudioCall sipAudioCall) {
                    this.onChanged(sipAudioCall);
                    if (SipConnection.this.mState == Call.State.HOLDING) {
                        sipAudioCall.startAudio();
                    }
                }

                /*
                 * Enabled aggressive block sorting
                 * Enabled unnecessary exception pruning
                 * Enabled aggressive exception aggregation
                 */
                public void onChanged(SipAudioCall object) {
                    synchronized (SipPhone.class) {
                        Object object2;
                        object = SipPhone.getCallStateFrom((SipAudioCall)object);
                        if (SipConnection.this.mState == object) {
                            return;
                        }
                        if (object == Call.State.INCOMING) {
                            SipConnection.this.setState(SipConnection.this.mOwner.getState());
                        } else {
                            if (SipConnection.this.mOwner == SipPhone.this.mRingingCall) {
                                Call.State state = SipPhone.this.mRingingCall.getState();
                                if (state == (object2 = Call.State.WAITING)) {
                                    try {
                                        SipPhone.this.switchHoldingAndActive();
                                    }
                                    catch (CallStateException callStateException) {
                                        this.onCallEnded(3);
                                        return;
                                    }
                                }
                                SipPhone.this.mForegroundCall.switchWith(SipPhone.this.mRingingCall);
                            }
                            SipConnection.this.setState((Call.State)((Object)object));
                        }
                        SipConnection.this.mOwner.onConnectionStateChanged(SipConnection.this);
                        object2 = SipConnection.this;
                        object = new StringBuilder();
                        ((StringBuilder)object).append("onChanged: ");
                        ((StringBuilder)object).append(SipPhone.hidePii(SipConnection.this.mPeer.getUriString()));
                        ((StringBuilder)object).append(": ");
                        ((StringBuilder)object).append((Object)SipConnection.this.mState);
                        ((StringBuilder)object).append(" on phone ");
                        ((StringBuilder)object).append(SipConnection.this.getPhone());
                        ((SipConnection)object2).log(((StringBuilder)object).toString());
                        return;
                    }
                }

                @Override
                protected void onError(int n) {
                    SipConnection sipConnection = SipConnection.this;
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("onError: ");
                    stringBuilder.append(n);
                    sipConnection.log(stringBuilder.toString());
                    this.onCallEnded(n);
                }
            };
            this.mOwner = sipCall;
            this.mPeer = sipProfile;
            this.mOriginalNumber = string;
        }

        private void log(String string) {
            Rlog.d((String)SCN_TAG, (String)string);
        }

        void acceptCall() throws CallStateException {
            try {
                this.mSipAudioCall.answerCall(8);
                return;
            }
            catch (SipException sipException) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("acceptCall(): ");
                stringBuilder.append((Object)sipException);
                throw new CallStateException(stringBuilder.toString());
            }
        }

        void changeOwner(SipCall sipCall) {
            this.mOwner = sipCall;
        }

        @Override
        public void deflect(String string) throws CallStateException {
            throw new CallStateException("deflect is not supported for SipPhone");
        }

        void dial() throws SipException {
            this.setState(Call.State.DIALING);
            this.mSipAudioCall = SipPhone.this.mSipManager.makeAudioCall(SipPhone.this.mProfile, this.mPeer, null, 15);
            this.mSipAudioCall.setListener((SipAudioCall.Listener)this.mAdapter);
        }

        @Override
        public String getAddress() {
            return this.mOriginalNumber;
        }

        AudioGroup getAudioGroup() {
            SipAudioCall sipAudioCall = this.mSipAudioCall;
            if (sipAudioCall == null) {
                return null;
            }
            return sipAudioCall.getAudioGroup();
        }

        @Override
        public SipCall getCall() {
            return this.mOwner;
        }

        @Override
        public String getCnapName() {
            String string;
            block0 : {
                string = this.mPeer.getDisplayName();
                if (!TextUtils.isEmpty((CharSequence)string)) break block0;
                string = null;
            }
            return string;
        }

        boolean getMute() {
            SipAudioCall sipAudioCall = this.mSipAudioCall;
            boolean bl = sipAudioCall == null ? false : sipAudioCall.isMuted();
            return bl;
        }

        @Override
        public int getNumberPresentation() {
            return 1;
        }

        @Override
        protected Phone getPhone() {
            return this.mOwner.getPhone();
        }

        @Override
        public Call.State getState() {
            return this.mState;
        }

        /*
         * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
         * Loose catch block
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         * Converted monitor instructions to comments
         * Lifted jumps to return sites
         */
        @Override
        public void hangup() throws CallStateException {
            Throwable throwable2222;
            // MONITORENTER : com.android.internal.telephony.sip.SipPhone.class
            Object object = new StringBuilder();
            ((StringBuilder)object).append("hangup: conn=");
            ((StringBuilder)object).append(SipPhone.hidePii(this.mPeer.getUriString()));
            ((StringBuilder)object).append(": ");
            ((StringBuilder)object).append((Object)this.mState);
            ((StringBuilder)object).append(": on phone ");
            ((StringBuilder)object).append(this.getPhone().getPhoneName());
            this.log(((StringBuilder)object).toString());
            if (!this.mState.isAlive()) {
                // MONITOREXIT : com.android.internal.telephony.sip.SipPhone.class
                return;
            }
            int n = 3;
            object = this.mSipAudioCall;
            if (object != null) {
                object.setListener(null);
                object.endCall();
            }
            object = this.mAdapter;
            if (this.mState == Call.State.INCOMING || this.mState == Call.State.WAITING) {
                n = 16;
            }
            ((SipAudioCallAdapter)((Object)object)).onCallEnded(n);
            return;
            {
                catch (Throwable throwable2222) {
                }
                catch (SipException sipException) {}
                {
                    object = new StringBuilder();
                    ((StringBuilder)object).append("hangup(): ");
                    ((StringBuilder)object).append((Object)sipException);
                    CallStateException callStateException = new CallStateException(((StringBuilder)object).toString());
                    throw callStateException;
                }
            }
            SipAudioCallAdapter sipAudioCallAdapter = this.mAdapter;
            if (this.mState == Call.State.INCOMING || this.mState == Call.State.WAITING) {
                n = 16;
            }
            sipAudioCallAdapter.onCallEnded(n);
            throw throwable2222;
        }

        void hold() throws CallStateException {
            this.setState(Call.State.HOLDING);
            try {
                this.mSipAudioCall.holdCall(15);
                return;
            }
            catch (SipException sipException) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("hold(): ");
                stringBuilder.append((Object)sipException);
                throw new CallStateException(stringBuilder.toString());
            }
        }

        void initIncomingCall(SipAudioCall sipAudioCall, Call.State state) {
            this.setState(state);
            this.mSipAudioCall = sipAudioCall;
            sipAudioCall.setListener((SipAudioCall.Listener)this.mAdapter);
            this.mIncoming = true;
        }

        @Override
        public boolean isIncoming() {
            return this.mIncoming;
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void separate() throws CallStateException {
            synchronized (SipPhone.class) {
                SipCall sipCall = this.getPhone() == SipPhone.this ? (SipCall)SipPhone.this.getBackgroundCall() : (SipCall)SipPhone.this.getForegroundCall();
                if (sipCall.getState() == Call.State.IDLE) {
                    Object object = new StringBuilder();
                    ((StringBuilder)object).append("separate: conn=");
                    ((StringBuilder)object).append(this.mPeer.getUriString());
                    ((StringBuilder)object).append(" from ");
                    ((StringBuilder)object).append(this.mOwner);
                    ((StringBuilder)object).append(" back to ");
                    ((StringBuilder)object).append(sipCall);
                    this.log(((StringBuilder)object).toString());
                    object = this.getPhone();
                    AudioGroup audioGroup = sipCall.getAudioGroup();
                    sipCall.add(this);
                    this.mSipAudioCall.setAudioGroup(audioGroup);
                    object.switchHoldingAndActive();
                    sipCall = (SipCall)SipPhone.this.getForegroundCall();
                    this.mSipAudioCall.startAudio();
                    sipCall.onConnectionStateChanged(this);
                    return;
                }
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("cannot put conn back to a call in non-idle state: ");
                stringBuilder.append((Object)sipCall.getState());
                CallStateException callStateException = new CallStateException(stringBuilder.toString());
                throw callStateException;
            }
        }

        void setMute(boolean bl) {
            Object object = this.mSipAudioCall;
            if (object != null && bl != object.isMuted()) {
                object = new StringBuilder();
                ((StringBuilder)object).append("setState: prev muted=");
                ((StringBuilder)object).append(bl ^ true);
                ((StringBuilder)object).append(" new muted=");
                ((StringBuilder)object).append(bl);
                this.log(((StringBuilder)object).toString());
                this.mSipAudioCall.toggleMute();
            }
        }

        @Override
        protected void setState(Call.State state) {
            if (state == this.mState) {
                return;
            }
            super.setState(state);
            this.mState = state;
        }

        void unhold(AudioGroup object) throws CallStateException {
            this.mSipAudioCall.setAudioGroup((AudioGroup)object);
            this.setState(Call.State.ACTIVE);
            try {
                this.mSipAudioCall.continueCall(15);
                return;
            }
            catch (SipException sipException) {
                object = new StringBuilder();
                ((StringBuilder)object).append("unhold(): ");
                ((StringBuilder)object).append((Object)sipException);
                throw new CallStateException(((StringBuilder)object).toString());
            }
        }

    }

}

