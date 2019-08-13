/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.annotation.UnsupportedAppUsage
 *  android.content.Context
 *  android.os.AsyncResult
 *  android.os.Handler
 *  android.os.Message
 *  android.os.Registrant
 *  android.os.RegistrantList
 *  android.telephony.PhoneNumberUtils
 *  android.telephony.Rlog
 *  android.telephony.ServiceState
 *  com.android.internal.telephony.PhoneConstants
 *  com.android.internal.telephony.PhoneConstants$State
 */
package com.android.internal.telephony;

import android.annotation.UnsupportedAppUsage;
import android.content.Context;
import android.os.AsyncResult;
import android.os.Handler;
import android.os.Message;
import android.os.Registrant;
import android.os.RegistrantList;
import android.telephony.PhoneNumberUtils;
import android.telephony.Rlog;
import android.telephony.ServiceState;
import com.android.internal.telephony.Call;
import com.android.internal.telephony.CallStateException;
import com.android.internal.telephony.Connection;
import com.android.internal.telephony.MmiCode;
import com.android.internal.telephony.Phone;
import com.android.internal.telephony.PhoneConstants;
import com.android.internal.telephony.PhoneInternalInterface;
import com.android.internal.telephony.UUSInfo;
import com.android.internal.telephony.sip.SipPhone;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class CallManager {
    private static final boolean DBG = true;
    private static final int EVENT_CALL_WAITING = 108;
    private static final int EVENT_CDMA_OTA_STATUS_CHANGE = 111;
    private static final int EVENT_DISCONNECT = 100;
    private static final int EVENT_DISPLAY_INFO = 109;
    private static final int EVENT_ECM_TIMER_RESET = 115;
    private static final int EVENT_INCOMING_RING = 104;
    private static final int EVENT_IN_CALL_VOICE_PRIVACY_OFF = 107;
    private static final int EVENT_IN_CALL_VOICE_PRIVACY_ON = 106;
    private static final int EVENT_MMI_COMPLETE = 114;
    private static final int EVENT_MMI_INITIATE = 113;
    private static final int EVENT_NEW_RINGING_CONNECTION = 102;
    private static final int EVENT_ONHOLD_TONE = 120;
    private static final int EVENT_POST_DIAL_CHARACTER = 119;
    private static final int EVENT_PRECISE_CALL_STATE_CHANGED = 101;
    private static final int EVENT_RESEND_INCALL_MUTE = 112;
    private static final int EVENT_RINGBACK_TONE = 105;
    private static final int EVENT_SERVICE_STATE_CHANGED = 118;
    private static final int EVENT_SIGNAL_INFO = 110;
    private static final int EVENT_SUBSCRIPTION_INFO_READY = 116;
    private static final int EVENT_SUPP_SERVICE_FAILED = 117;
    private static final int EVENT_TTY_MODE_RECEIVED = 122;
    private static final int EVENT_UNKNOWN_CONNECTION = 103;
    private static final CallManager INSTANCE = new CallManager();
    private static final String LOG_TAG = "CallManager";
    private static final boolean VDBG = false;
    @UnsupportedAppUsage
    private final ArrayList<Call> mBackgroundCalls = new ArrayList();
    protected final RegistrantList mCallWaitingRegistrants = new RegistrantList();
    protected final RegistrantList mCdmaOtaStatusChangeRegistrants = new RegistrantList();
    private Phone mDefaultPhone = null;
    protected final RegistrantList mDisconnectRegistrants = new RegistrantList();
    protected final RegistrantList mDisplayInfoRegistrants = new RegistrantList();
    protected final RegistrantList mEcmTimerResetRegistrants = new RegistrantList();
    @UnsupportedAppUsage
    private final ArrayList<Connection> mEmptyConnections = new ArrayList();
    @UnsupportedAppUsage
    private final ArrayList<Call> mForegroundCalls = new ArrayList();
    private final HashMap<Phone, CallManagerHandler> mHandlerMap = new HashMap();
    protected final RegistrantList mInCallVoicePrivacyOffRegistrants = new RegistrantList();
    protected final RegistrantList mInCallVoicePrivacyOnRegistrants = new RegistrantList();
    protected final RegistrantList mIncomingRingRegistrants = new RegistrantList();
    protected final RegistrantList mMmiCompleteRegistrants = new RegistrantList();
    protected final RegistrantList mMmiInitiateRegistrants = new RegistrantList();
    protected final RegistrantList mMmiRegistrants = new RegistrantList();
    protected final RegistrantList mNewRingingConnectionRegistrants = new RegistrantList();
    protected final RegistrantList mOnHoldToneRegistrants = new RegistrantList();
    @UnsupportedAppUsage
    private final ArrayList<Phone> mPhones = new ArrayList();
    protected final RegistrantList mPostDialCharacterRegistrants = new RegistrantList();
    protected final RegistrantList mPreciseCallStateRegistrants = new RegistrantList();
    private Object mRegistrantidentifier = new Object();
    protected final RegistrantList mResendIncallMuteRegistrants = new RegistrantList();
    protected final RegistrantList mRingbackToneRegistrants = new RegistrantList();
    @UnsupportedAppUsage
    private final ArrayList<Call> mRingingCalls = new ArrayList();
    protected final RegistrantList mServiceStateChangedRegistrants = new RegistrantList();
    protected final RegistrantList mSignalInfoRegistrants = new RegistrantList();
    private boolean mSpeedUpAudioForMtCall = false;
    protected final RegistrantList mSubscriptionInfoReadyRegistrants = new RegistrantList();
    protected final RegistrantList mSuppServiceFailedRegistrants = new RegistrantList();
    protected final RegistrantList mTtyModeReceivedRegistrants = new RegistrantList();
    protected final RegistrantList mUnknownConnectionRegistrants = new RegistrantList();

    private CallManager() {
    }

    @UnsupportedAppUsage
    private boolean canDial(Phone object) {
        int n = object.getServiceState().getState();
        int n2 = ((Phone)object).getSubId();
        boolean bl = this.hasActiveRingingCall();
        Call.State state = this.getActiveFgCallState(n2);
        boolean bl2 = n != 3 && !bl && (state == Call.State.ACTIVE || state == Call.State.IDLE || state == Call.State.DISCONNECTED || state == Call.State.ALERTING);
        if (!bl2) {
            object = new StringBuilder();
            ((StringBuilder)object).append("canDial serviceState=");
            ((StringBuilder)object).append(n);
            ((StringBuilder)object).append(" hasRingingCall=");
            ((StringBuilder)object).append(bl);
            ((StringBuilder)object).append(" fgCallState=");
            ((StringBuilder)object).append((Object)state);
            Rlog.d((String)LOG_TAG, (String)((StringBuilder)object).toString());
        }
        return bl2;
    }

    @UnsupportedAppUsage
    private Context getContext() {
        Phone phone = this.getDefaultPhone();
        phone = phone == null ? null : phone.getContext();
        return phone;
    }

    private Call getFirstActiveCall(ArrayList<Call> object) {
        object = ((ArrayList)object).iterator();
        while (object.hasNext()) {
            Call call = (Call)object.next();
            if (call.isIdle()) continue;
            return call;
        }
        return null;
    }

    private Call getFirstActiveCall(ArrayList<Call> object, int n) {
        Iterator<Call> iterator = ((ArrayList)object).iterator();
        while (iterator.hasNext()) {
            object = iterator.next();
            if (((Call)object).isIdle() || ((Call)object).getPhone().getSubId() != n && !(((Call)object).getPhone() instanceof SipPhone)) continue;
            return object;
        }
        return null;
    }

    private Call getFirstCallOfState(ArrayList<Call> object, Call.State state) {
        Iterator<Call> iterator = ((ArrayList)object).iterator();
        while (iterator.hasNext()) {
            object = iterator.next();
            if (((Call)object).getState() != state) continue;
            return object;
        }
        return null;
    }

    private Call getFirstCallOfState(ArrayList<Call> object, Call.State state, int n) {
        object = ((ArrayList)object).iterator();
        while (object.hasNext()) {
            Call call = (Call)object.next();
            if (call.getState() != state && call.getPhone().getSubId() != n && !(call.getPhone() instanceof SipPhone)) continue;
            return call;
        }
        return null;
    }

    private Call getFirstNonIdleCall(List<Call> object) {
        List<Call> list = null;
        Iterator<Call> iterator = object.iterator();
        while (iterator.hasNext()) {
            Call call = iterator.next();
            if (!call.isIdle()) {
                return call;
            }
            object = list;
            if (call.getState() != Call.State.IDLE) {
                object = list;
                if (list == null) {
                    object = call;
                }
            }
            list = object;
        }
        return list;
    }

    private Call getFirstNonIdleCall(List<Call> list, int n) {
        Object object = null;
        Iterator<Call> iterator = list.iterator();
        list = object;
        while (iterator.hasNext()) {
            block8 : {
                Call call;
                block7 : {
                    call = iterator.next();
                    if (call.getPhone().getSubId() == n) break block7;
                    object = list;
                    if (!(call.getPhone() instanceof SipPhone)) break block8;
                }
                if (!call.isIdle()) {
                    return call;
                }
                object = list;
                if (call.getState() != Call.State.IDLE) {
                    object = list;
                    if (list == null) {
                        object = call;
                    }
                }
            }
            list = object;
        }
        return list;
    }

    @UnsupportedAppUsage
    public static CallManager getInstance() {
        return INSTANCE;
    }

    private Phone getPhone(int n) {
        Phone phone;
        Phone phone2 = null;
        Iterator<Phone> iterator = this.mPhones.iterator();
        do {
            phone = phone2;
        } while (iterator.hasNext() && ((phone = iterator.next()).getSubId() != n || phone.getPhoneType() == 5));
        return phone;
    }

    private boolean hasMoreThanOneHoldingCall(int n) {
        int n2 = 0;
        for (Call call : this.mBackgroundCalls) {
            int n3;
            block5 : {
                block6 : {
                    n3 = n2;
                    if (call.getState() != Call.State.HOLDING) break block5;
                    if (call.getPhone().getSubId() == n) break block6;
                    n3 = n2;
                    if (!(call.getPhone() instanceof SipPhone)) break block5;
                }
                n3 = ++n2;
                if (n2 > 1) {
                    return true;
                }
            }
            n2 = n3;
        }
        return false;
    }

    @UnsupportedAppUsage
    private boolean hasMoreThanOneRingingCall() {
        int n = 0;
        Iterator<Call> iterator = this.mRingingCalls.iterator();
        while (iterator.hasNext()) {
            int n2 = n++;
            if (iterator.next().getState().isRinging()) {
                n2 = n;
                if (n > 1) {
                    return true;
                }
            }
            n = n2;
        }
        return false;
    }

    @UnsupportedAppUsage
    private boolean hasMoreThanOneRingingCall(int n) {
        int n2 = 0;
        for (Call call : this.mRingingCalls) {
            int n3;
            block5 : {
                block6 : {
                    n3 = n2;
                    if (!call.getState().isRinging()) break block5;
                    if (call.getPhone().getSubId() == n) break block6;
                    n3 = n2;
                    if (!(call.getPhone() instanceof SipPhone)) break block5;
                }
                n3 = ++n2;
                if (n2 > 1) {
                    return true;
                }
            }
            n2 = n3;
        }
        return false;
    }

    private void registerForPhoneStates(Phone phone) {
        if (this.mHandlerMap.get(phone) != null) {
            Rlog.d((String)LOG_TAG, (String)"This phone has already been registered.");
            return;
        }
        CallManagerHandler callManagerHandler = new CallManagerHandler();
        this.mHandlerMap.put(phone, callManagerHandler);
        phone.registerForPreciseCallStateChanged(callManagerHandler, 101, this.mRegistrantidentifier);
        phone.registerForDisconnect(callManagerHandler, 100, this.mRegistrantidentifier);
        phone.registerForNewRingingConnection(callManagerHandler, 102, this.mRegistrantidentifier);
        phone.registerForUnknownConnection(callManagerHandler, 103, this.mRegistrantidentifier);
        phone.registerForIncomingRing(callManagerHandler, 104, this.mRegistrantidentifier);
        phone.registerForRingbackTone(callManagerHandler, 105, this.mRegistrantidentifier);
        phone.registerForInCallVoicePrivacyOn(callManagerHandler, 106, this.mRegistrantidentifier);
        phone.registerForInCallVoicePrivacyOff(callManagerHandler, 107, this.mRegistrantidentifier);
        phone.registerForDisplayInfo(callManagerHandler, 109, this.mRegistrantidentifier);
        phone.registerForSignalInfo(callManagerHandler, 110, this.mRegistrantidentifier);
        phone.registerForResendIncallMute(callManagerHandler, 112, this.mRegistrantidentifier);
        phone.registerForMmiInitiate(callManagerHandler, 113, this.mRegistrantidentifier);
        phone.registerForMmiComplete(callManagerHandler, 114, this.mRegistrantidentifier);
        phone.registerForSuppServiceFailed(callManagerHandler, 117, this.mRegistrantidentifier);
        phone.registerForServiceStateChanged(callManagerHandler, 118, this.mRegistrantidentifier);
        phone.setOnPostDialCharacter(callManagerHandler, 119, null);
        phone.registerForCdmaOtaStatusChange(callManagerHandler, 111, null);
        phone.registerForSubscriptionInfoReady(callManagerHandler, 116, null);
        phone.registerForCallWaiting(callManagerHandler, 108, null);
        phone.registerForEcmTimerReset(callManagerHandler, 115, null);
        phone.registerForOnHoldTone(callManagerHandler, 120, null);
        phone.registerForSuppServiceFailed(callManagerHandler, 117, null);
        phone.registerForTtyModeReceived(callManagerHandler, 122, null);
    }

    private void unregisterForPhoneStates(Phone phone) {
        CallManagerHandler callManagerHandler = this.mHandlerMap.get(phone);
        if (callManagerHandler == null) {
            Rlog.e((String)LOG_TAG, (String)"Could not find Phone handler for unregistration");
            return;
        }
        this.mHandlerMap.remove(phone);
        phone.unregisterForPreciseCallStateChanged(callManagerHandler);
        phone.unregisterForDisconnect(callManagerHandler);
        phone.unregisterForNewRingingConnection(callManagerHandler);
        phone.unregisterForUnknownConnection(callManagerHandler);
        phone.unregisterForIncomingRing(callManagerHandler);
        phone.unregisterForRingbackTone(callManagerHandler);
        phone.unregisterForInCallVoicePrivacyOn(callManagerHandler);
        phone.unregisterForInCallVoicePrivacyOff(callManagerHandler);
        phone.unregisterForDisplayInfo(callManagerHandler);
        phone.unregisterForSignalInfo(callManagerHandler);
        phone.unregisterForResendIncallMute(callManagerHandler);
        phone.unregisterForMmiInitiate(callManagerHandler);
        phone.unregisterForMmiComplete(callManagerHandler);
        phone.unregisterForSuppServiceFailed(callManagerHandler);
        phone.unregisterForServiceStateChanged(callManagerHandler);
        phone.unregisterForTtyModeReceived(callManagerHandler);
        phone.setOnPostDialCharacter(null, 119, null);
        phone.unregisterForCdmaOtaStatusChange(callManagerHandler);
        phone.unregisterForSubscriptionInfoReady(callManagerHandler);
        phone.unregisterForCallWaiting(callManagerHandler);
        phone.unregisterForEcmTimerReset(callManagerHandler);
        phone.unregisterForOnHoldTone(callManagerHandler);
        phone.unregisterForSuppServiceFailed(callManagerHandler);
    }

    public boolean canConference(Call call) {
        Phone phone = null;
        Object object = null;
        if (this.hasActiveFgCall()) {
            phone = this.getActiveFgCall().getPhone();
        }
        if (call != null) {
            object = call.getPhone();
        }
        return object.getClass().equals(phone.getClass());
    }

    @UnsupportedAppUsage
    public boolean canConference(Call call, int n) {
        Phone phone = null;
        Object object = null;
        if (this.hasActiveFgCall(n)) {
            phone = this.getActiveFgCall(n).getPhone();
        }
        if (call != null) {
            object = call.getPhone();
        }
        return object.getClass().equals(phone.getClass());
    }

    public boolean canTransfer(Call call) {
        Phone phone = null;
        Phone phone2 = null;
        if (this.hasActiveFgCall()) {
            phone = this.getActiveFgCall().getPhone();
        }
        if (call != null) {
            phone2 = call.getPhone();
        }
        boolean bl = phone2 == phone && phone.canTransfer();
        return bl;
    }

    public boolean canTransfer(Call call, int n) {
        Phone phone = null;
        Phone phone2 = null;
        if (this.hasActiveFgCall(n)) {
            phone = this.getActiveFgCall(n).getPhone();
        }
        if (call != null) {
            phone2 = call.getPhone();
        }
        boolean bl = phone2 == phone && phone.canTransfer();
        return bl;
    }

    public void clearDisconnected() {
        Iterator<Phone> iterator = this.mPhones.iterator();
        while (iterator.hasNext()) {
            iterator.next().clearDisconnected();
        }
    }

    public void clearDisconnected(int n) {
        for (Phone phone : this.mPhones) {
            if (phone.getSubId() != n) continue;
            phone.clearDisconnected();
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @UnsupportedAppUsage
    public void conference(Call call) throws CallStateException {
        Phone phone = this.getFgPhone(call.getPhone().getSubId());
        if (phone != null) {
            if (phone instanceof SipPhone) {
                ((SipPhone)phone).conference(call);
                return;
            } else {
                if (!this.canConference(call)) throw new CallStateException("Can't conference foreground and selected background call");
                phone.conference();
            }
            return;
        } else {
            Rlog.d((String)LOG_TAG, (String)"conference: fgPhone=null");
        }
    }

    public Connection dial(Phone phone, String string, int n) throws CallStateException {
        int n2 = phone.getSubId();
        if (!this.canDial(phone)) {
            if (phone.handleInCallMmiCommands(PhoneNumberUtils.stripSeparators((String)string))) {
                return null;
            }
            throw new CallStateException("cannot dial in current state");
        }
        if (this.hasActiveFgCall(n2)) {
            Phone phone2 = this.getActiveFgCall(n2).getPhone();
            boolean bl = phone2.getBackgroundCall().isIdle();
            boolean bl2 = true;
            Object object = new StringBuilder();
            ((StringBuilder)object).append("hasBgCall: ");
            ((StringBuilder)object).append(bl ^= true);
            ((StringBuilder)object).append(" sameChannel:");
            if (phone2 != phone) {
                bl2 = false;
            }
            ((StringBuilder)object).append(bl2);
            Rlog.d((String)LOG_TAG, (String)((StringBuilder)object).toString());
            object = phone.getImsPhone();
            if (phone2 != phone && (object == null || object != phone2)) {
                if (bl) {
                    Rlog.d((String)LOG_TAG, (String)"Hangup");
                    this.getActiveFgCall(n2).hangup();
                } else {
                    Rlog.d((String)LOG_TAG, (String)"Switch");
                    phone2.switchHoldingAndActive();
                }
            }
        }
        return phone.dial(string, ((PhoneInternalInterface.DialArgs.Builder)new PhoneInternalInterface.DialArgs.Builder().setVideoState(n)).build());
    }

    public Connection dial(Phone phone, String string, UUSInfo uUSInfo, int n) throws CallStateException {
        return phone.dial(string, ((PhoneInternalInterface.DialArgs.Builder)((PhoneInternalInterface.DialArgs.Builder)new PhoneInternalInterface.DialArgs.Builder().setUusInfo(uUSInfo)).setVideoState(n)).build());
    }

    public void explicitCallTransfer(Call call) throws CallStateException {
        if (this.canTransfer(call)) {
            call.getPhone().explicitCallTransfer();
        }
    }

    public Call getActiveFgCall() {
        Call call = this.getFirstNonIdleCall(this.mForegroundCalls);
        Object object = call;
        if (call == null) {
            object = this.mDefaultPhone;
            object = object == null ? null : object.getForegroundCall();
        }
        return object;
    }

    @UnsupportedAppUsage
    public Call getActiveFgCall(int n) {
        Call call = this.getFirstNonIdleCall(this.mForegroundCalls, n);
        Object object = call;
        if (call == null) {
            object = this.getPhone(n);
            object = object == null ? null : object.getForegroundCall();
        }
        return object;
    }

    public Call.State getActiveFgCallState() {
        Call call = this.getActiveFgCall();
        if (call != null) {
            return call.getState();
        }
        return Call.State.IDLE;
    }

    @UnsupportedAppUsage
    public Call.State getActiveFgCallState(int n) {
        Call call = this.getActiveFgCall(n);
        if (call != null) {
            return call.getState();
        }
        return Call.State.IDLE;
    }

    @UnsupportedAppUsage
    public List<Call> getBackgroundCalls() {
        return Collections.unmodifiableList(this.mBackgroundCalls);
    }

    @UnsupportedAppUsage
    public List<Connection> getBgCallConnections() {
        Call call = this.getFirstActiveBgCall();
        if (call != null) {
            return call.getConnections();
        }
        return this.mEmptyConnections;
    }

    @UnsupportedAppUsage
    public Phone getBgPhone() {
        return this.getFirstActiveBgCall().getPhone();
    }

    @UnsupportedAppUsage
    public Phone getDefaultPhone() {
        return this.mDefaultPhone;
    }

    @UnsupportedAppUsage
    public List<Connection> getFgCallConnections() {
        Call call = this.getActiveFgCall();
        if (call != null) {
            return call.getConnections();
        }
        return this.mEmptyConnections;
    }

    public List<Connection> getFgCallConnections(int n) {
        Call call = this.getActiveFgCall(n);
        if (call != null) {
            return call.getConnections();
        }
        return this.mEmptyConnections;
    }

    @UnsupportedAppUsage
    public Phone getFgPhone() {
        return this.getActiveFgCall().getPhone();
    }

    @UnsupportedAppUsage
    public Phone getFgPhone(int n) {
        return this.getActiveFgCall(n).getPhone();
    }

    @UnsupportedAppUsage
    public Call getFirstActiveBgCall() {
        Call call = this.getFirstNonIdleCall(this.mBackgroundCalls);
        Object object = call;
        if (call == null) {
            object = this.mDefaultPhone;
            object = object == null ? null : object.getBackgroundCall();
        }
        return object;
    }

    @UnsupportedAppUsage
    public Call getFirstActiveBgCall(int n) {
        Call call;
        Phone phone = this.getPhone(n);
        if (this.hasMoreThanOneHoldingCall(n)) {
            return phone.getBackgroundCall();
        }
        Call call2 = call = this.getFirstNonIdleCall(this.mBackgroundCalls, n);
        if (call == null) {
            call2 = phone == null ? null : phone.getBackgroundCall();
        }
        return call2;
    }

    @UnsupportedAppUsage
    public Call getFirstActiveRingingCall() {
        Call call = this.getFirstNonIdleCall(this.mRingingCalls);
        Object object = call;
        if (call == null) {
            object = this.mDefaultPhone;
            object = object == null ? null : object.getRingingCall();
        }
        return object;
    }

    @UnsupportedAppUsage
    public Call getFirstActiveRingingCall(int n) {
        Call call;
        Phone phone = this.getPhone(n);
        Call call2 = call = this.getFirstNonIdleCall(this.mRingingCalls, n);
        if (call == null) {
            call2 = phone == null ? null : phone.getRingingCall();
        }
        return call2;
    }

    public List<Call> getForegroundCalls() {
        return Collections.unmodifiableList(this.mForegroundCalls);
    }

    public boolean getMute() {
        if (this.hasActiveFgCall()) {
            return this.getActiveFgCall().getPhone().getMute();
        }
        if (this.hasActiveBgCall()) {
            return this.getFirstActiveBgCall().getPhone().getMute();
        }
        return false;
    }

    public List<? extends MmiCode> getPendingMmiCodes(Phone phone) {
        Rlog.e((String)LOG_TAG, (String)"getPendingMmiCodes not implemented");
        return null;
    }

    @UnsupportedAppUsage
    public Phone getPhoneInCall() {
        Phone phone = !this.getFirstActiveRingingCall().isIdle() ? this.getFirstActiveRingingCall().getPhone() : (!this.getActiveFgCall().isIdle() ? this.getActiveFgCall().getPhone() : this.getFirstActiveBgCall().getPhone());
        return phone;
    }

    public Object getRegistrantIdentifier() {
        return this.mRegistrantidentifier;
    }

    @UnsupportedAppUsage
    public List<Call> getRingingCalls() {
        return Collections.unmodifiableList(this.mRingingCalls);
    }

    @UnsupportedAppUsage
    public Phone getRingingPhone() {
        return this.getFirstActiveRingingCall().getPhone();
    }

    public Phone getRingingPhone(int n) {
        return this.getFirstActiveRingingCall(n).getPhone();
    }

    public int getServiceState() {
        int n;
        int n2 = 1;
        Iterator<Phone> iterator = this.mPhones.iterator();
        do {
            block8 : {
                int n3;
                block6 : {
                    block7 : {
                        n = n2;
                        if (!iterator.hasNext()) break;
                        n3 = iterator.next().getServiceState().getState();
                        if (n3 == 0) {
                            n = n3;
                            break;
                        }
                        if (n3 != 1) break block6;
                        if (n2 == 2) break block7;
                        n = n2;
                        if (n2 != 3) break block8;
                    }
                    n = n3;
                    break block8;
                }
                n = n2;
                if (n3 == 2) {
                    n = n2;
                    if (n2 == 3) {
                        n = n3;
                    }
                }
            }
            n2 = n;
        } while (true);
        return n;
    }

    public int getServiceState(int n) {
        int n2;
        int n3 = 1;
        Iterator<Phone> iterator = this.mPhones.iterator();
        do {
            int n4;
            block5 : {
                block6 : {
                    block7 : {
                        n2 = n3;
                        if (!iterator.hasNext()) break;
                        Phone phone = iterator.next();
                        n4 = n3;
                        if (phone.getSubId() != n) break block5;
                        n2 = phone.getServiceState().getState();
                        if (n2 == 0) break;
                        if (n2 != 1) break block6;
                        if (n3 == 2) break block7;
                        n4 = n3;
                        if (n3 != 3) break block5;
                    }
                    n4 = n2;
                    break block5;
                }
                n4 = n3;
                if (n2 == 2) {
                    n4 = n3;
                    if (n3 == 3) {
                        n4 = n2;
                    }
                }
            }
            n3 = n4;
        } while (true);
        return n2;
    }

    @UnsupportedAppUsage
    public PhoneConstants.State getState() {
        PhoneConstants.State state = PhoneConstants.State.IDLE;
        for (Phone phone : this.mPhones) {
            PhoneConstants.State state2;
            if (phone.getState() == PhoneConstants.State.RINGING) {
                state2 = PhoneConstants.State.RINGING;
            } else {
                state2 = state;
                if (phone.getState() == PhoneConstants.State.OFFHOOK) {
                    state2 = state;
                    if (state == PhoneConstants.State.IDLE) {
                        state2 = PhoneConstants.State.OFFHOOK;
                    }
                }
            }
            state = state2;
        }
        return state;
    }

    @UnsupportedAppUsage
    public PhoneConstants.State getState(int n) {
        PhoneConstants.State state = PhoneConstants.State.IDLE;
        for (Phone phone : this.mPhones) {
            PhoneConstants.State state2 = state;
            if (phone.getSubId() == n) {
                if (phone.getState() == PhoneConstants.State.RINGING) {
                    state2 = PhoneConstants.State.RINGING;
                } else {
                    state2 = state;
                    if (phone.getState() == PhoneConstants.State.OFFHOOK) {
                        state2 = state;
                        if (state == PhoneConstants.State.IDLE) {
                            state2 = PhoneConstants.State.OFFHOOK;
                        }
                    }
                }
            }
            state = state2;
        }
        return state;
    }

    @UnsupportedAppUsage
    public boolean hasActiveBgCall() {
        boolean bl = this.getFirstActiveCall(this.mBackgroundCalls) != null;
        return bl;
    }

    @UnsupportedAppUsage
    public boolean hasActiveBgCall(int n) {
        boolean bl = this.getFirstActiveCall(this.mBackgroundCalls, n) != null;
        return bl;
    }

    @UnsupportedAppUsage
    public boolean hasActiveFgCall() {
        boolean bl = this.getFirstActiveCall(this.mForegroundCalls) != null;
        return bl;
    }

    @UnsupportedAppUsage
    public boolean hasActiveFgCall(int n) {
        boolean bl = this.getFirstActiveCall(this.mForegroundCalls, n) != null;
        return bl;
    }

    public boolean hasActiveRingingCall() {
        boolean bl = this.getFirstActiveCall(this.mRingingCalls) != null;
        return bl;
    }

    @UnsupportedAppUsage
    public boolean hasActiveRingingCall(int n) {
        boolean bl = this.getFirstActiveCall(this.mRingingCalls, n) != null;
        return bl;
    }

    public boolean hasDisconnectedBgCall() {
        boolean bl = this.getFirstCallOfState(this.mBackgroundCalls, Call.State.DISCONNECTED) != null;
        return bl;
    }

    public boolean hasDisconnectedBgCall(int n) {
        boolean bl = this.getFirstCallOfState(this.mBackgroundCalls, Call.State.DISCONNECTED, n) != null;
        return bl;
    }

    public boolean hasDisconnectedFgCall() {
        boolean bl = this.getFirstCallOfState(this.mForegroundCalls, Call.State.DISCONNECTED) != null;
        return bl;
    }

    public boolean hasDisconnectedFgCall(int n) {
        boolean bl = this.getFirstCallOfState(this.mForegroundCalls, Call.State.DISCONNECTED, n) != null;
        return bl;
    }

    public void registerForCallWaiting(Handler handler, int n, Object object) {
        this.mCallWaitingRegistrants.addUnique(handler, n, object);
    }

    public void registerForCdmaOtaStatusChange(Handler handler, int n, Object object) {
        this.mCdmaOtaStatusChangeRegistrants.addUnique(handler, n, object);
    }

    @UnsupportedAppUsage
    public void registerForDisconnect(Handler handler, int n, Object object) {
        this.mDisconnectRegistrants.addUnique(handler, n, object);
    }

    public void registerForDisplayInfo(Handler handler, int n, Object object) {
        this.mDisplayInfoRegistrants.addUnique(handler, n, object);
    }

    public void registerForEcmTimerReset(Handler handler, int n, Object object) {
        this.mEcmTimerResetRegistrants.addUnique(handler, n, object);
    }

    public void registerForInCallVoicePrivacyOff(Handler handler, int n, Object object) {
        this.mInCallVoicePrivacyOffRegistrants.addUnique(handler, n, object);
    }

    public void registerForInCallVoicePrivacyOn(Handler handler, int n, Object object) {
        this.mInCallVoicePrivacyOnRegistrants.addUnique(handler, n, object);
    }

    public void registerForIncomingRing(Handler handler, int n, Object object) {
        this.mIncomingRingRegistrants.addUnique(handler, n, object);
    }

    public void registerForMmiComplete(Handler handler, int n, Object object) {
        Rlog.d((String)LOG_TAG, (String)"registerForMmiComplete");
        this.mMmiCompleteRegistrants.addUnique(handler, n, object);
    }

    public void registerForMmiInitiate(Handler handler, int n, Object object) {
        this.mMmiInitiateRegistrants.addUnique(handler, n, object);
    }

    @UnsupportedAppUsage
    public void registerForNewRingingConnection(Handler handler, int n, Object object) {
        this.mNewRingingConnectionRegistrants.addUnique(handler, n, object);
    }

    public void registerForOnHoldTone(Handler handler, int n, Object object) {
        this.mOnHoldToneRegistrants.addUnique(handler, n, object);
    }

    public void registerForPostDialCharacter(Handler handler, int n, Object object) {
        this.mPostDialCharacterRegistrants.addUnique(handler, n, object);
    }

    @UnsupportedAppUsage
    public void registerForPreciseCallStateChanged(Handler handler, int n, Object object) {
        this.mPreciseCallStateRegistrants.addUnique(handler, n, object);
    }

    public void registerForResendIncallMute(Handler handler, int n, Object object) {
        this.mResendIncallMuteRegistrants.addUnique(handler, n, object);
    }

    public void registerForRingbackTone(Handler handler, int n, Object object) {
        this.mRingbackToneRegistrants.addUnique(handler, n, object);
    }

    public void registerForServiceStateChanged(Handler handler, int n, Object object) {
        this.mServiceStateChangedRegistrants.addUnique(handler, n, object);
    }

    public void registerForSignalInfo(Handler handler, int n, Object object) {
        this.mSignalInfoRegistrants.addUnique(handler, n, object);
    }

    public void registerForSubscriptionInfoReady(Handler handler, int n, Object object) {
        this.mSubscriptionInfoReadyRegistrants.addUnique(handler, n, object);
    }

    public void registerForSuppServiceFailed(Handler handler, int n, Object object) {
        this.mSuppServiceFailedRegistrants.addUnique(handler, n, object);
    }

    public void registerForTtyModeReceived(Handler handler, int n, Object object) {
        this.mTtyModeReceivedRegistrants.addUnique(handler, n, object);
    }

    public void registerForUnknownConnection(Handler handler, int n, Object object) {
        this.mUnknownConnectionRegistrants.addUnique(handler, n, object);
    }

    @UnsupportedAppUsage
    public boolean registerPhone(Phone phone) {
        if (phone != null && !this.mPhones.contains(phone)) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("registerPhone(");
            stringBuilder.append(phone.getPhoneName());
            stringBuilder.append(" ");
            stringBuilder.append(phone);
            stringBuilder.append(")");
            Rlog.d((String)LOG_TAG, (String)stringBuilder.toString());
            if (this.mPhones.isEmpty()) {
                this.mDefaultPhone = phone;
            }
            this.mPhones.add(phone);
            this.mRingingCalls.add(phone.getRingingCall());
            this.mBackgroundCalls.add(phone.getBackgroundCall());
            this.mForegroundCalls.add(phone.getForegroundCall());
            this.registerForPhoneStates(phone);
            return true;
        }
        return false;
    }

    public void rejectCall(Call call) throws CallStateException {
        call.getPhone().rejectCall();
    }

    public boolean sendBurstDtmf(String string, int n, int n2, Message message) {
        if (this.hasActiveFgCall()) {
            this.getActiveFgCall().getPhone().sendBurstDtmf(string, n, n2, message);
            return true;
        }
        return false;
    }

    public boolean sendDtmf(char c) {
        boolean bl = false;
        if (this.hasActiveFgCall()) {
            this.getActiveFgCall().getPhone().sendDtmf(c);
            bl = true;
        }
        return bl;
    }

    public boolean sendUssdResponse(Phone phone, String string) {
        Rlog.e((String)LOG_TAG, (String)"sendUssdResponse not implemented");
        return false;
    }

    public void setEchoSuppressionEnabled() {
        if (this.hasActiveFgCall()) {
            this.getActiveFgCall().getPhone().setEchoSuppressionEnabled();
        }
    }

    public void setMute(boolean bl) {
        if (this.hasActiveFgCall()) {
            this.getActiveFgCall().getPhone().setMute(bl);
        }
    }

    public boolean startDtmf(char c) {
        boolean bl = false;
        if (this.hasActiveFgCall()) {
            this.getActiveFgCall().getPhone().startDtmf(c);
            bl = true;
        }
        return bl;
    }

    public void stopDtmf() {
        if (this.hasActiveFgCall()) {
            this.getFgPhone().stopDtmf();
        }
    }

    public void unregisterForCallWaiting(Handler handler) {
        this.mCallWaitingRegistrants.remove(handler);
    }

    public void unregisterForCdmaOtaStatusChange(Handler handler) {
        this.mCdmaOtaStatusChangeRegistrants.remove(handler);
    }

    @UnsupportedAppUsage
    public void unregisterForDisconnect(Handler handler) {
        this.mDisconnectRegistrants.remove(handler);
    }

    public void unregisterForDisplayInfo(Handler handler) {
        this.mDisplayInfoRegistrants.remove(handler);
    }

    public void unregisterForEcmTimerReset(Handler handler) {
        this.mEcmTimerResetRegistrants.remove(handler);
    }

    public void unregisterForInCallVoicePrivacyOff(Handler handler) {
        this.mInCallVoicePrivacyOffRegistrants.remove(handler);
    }

    public void unregisterForInCallVoicePrivacyOn(Handler handler) {
        this.mInCallVoicePrivacyOnRegistrants.remove(handler);
    }

    public void unregisterForIncomingRing(Handler handler) {
        this.mIncomingRingRegistrants.remove(handler);
    }

    public void unregisterForMmiComplete(Handler handler) {
        this.mMmiCompleteRegistrants.remove(handler);
    }

    public void unregisterForMmiInitiate(Handler handler) {
        this.mMmiInitiateRegistrants.remove(handler);
    }

    @UnsupportedAppUsage
    public void unregisterForNewRingingConnection(Handler handler) {
        this.mNewRingingConnectionRegistrants.remove(handler);
    }

    public void unregisterForOnHoldTone(Handler handler) {
        this.mOnHoldToneRegistrants.remove(handler);
    }

    public void unregisterForPostDialCharacter(Handler handler) {
        this.mPostDialCharacterRegistrants.remove(handler);
    }

    @UnsupportedAppUsage
    public void unregisterForPreciseCallStateChanged(Handler handler) {
        this.mPreciseCallStateRegistrants.remove(handler);
    }

    public void unregisterForResendIncallMute(Handler handler) {
        this.mResendIncallMuteRegistrants.remove(handler);
    }

    public void unregisterForRingbackTone(Handler handler) {
        this.mRingbackToneRegistrants.remove(handler);
    }

    public void unregisterForServiceStateChanged(Handler handler) {
        this.mServiceStateChangedRegistrants.remove(handler);
    }

    public void unregisterForSignalInfo(Handler handler) {
        this.mSignalInfoRegistrants.remove(handler);
    }

    public void unregisterForSubscriptionInfoReady(Handler handler) {
        this.mSubscriptionInfoReadyRegistrants.remove(handler);
    }

    public void unregisterForSuppServiceFailed(Handler handler) {
        this.mSuppServiceFailedRegistrants.remove(handler);
    }

    public void unregisterForTtyModeReceived(Handler handler) {
        this.mTtyModeReceivedRegistrants.remove(handler);
    }

    public void unregisterForUnknownConnection(Handler handler) {
        this.mUnknownConnectionRegistrants.remove(handler);
    }

    @UnsupportedAppUsage
    public void unregisterPhone(Phone phone) {
        if (phone != null && this.mPhones.contains(phone)) {
            Object object = new StringBuilder();
            ((StringBuilder)object).append("unregisterPhone(");
            ((StringBuilder)object).append(phone.getPhoneName());
            ((StringBuilder)object).append(" ");
            ((StringBuilder)object).append(phone);
            ((StringBuilder)object).append(")");
            Rlog.d((String)LOG_TAG, (String)((StringBuilder)object).toString());
            object = phone.getImsPhone();
            if (object != null) {
                this.unregisterPhone((Phone)object);
            }
            this.mPhones.remove(phone);
            this.mRingingCalls.remove(phone.getRingingCall());
            this.mBackgroundCalls.remove(phone.getBackgroundCall());
            this.mForegroundCalls.remove(phone.getForegroundCall());
            this.unregisterForPhoneStates(phone);
            if (phone == this.mDefaultPhone) {
                this.mDefaultPhone = this.mPhones.isEmpty() ? null : this.mPhones.get(0);
            }
        }
    }

    private class CallManagerHandler
    extends Handler {
        private CallManagerHandler() {
        }

        public void handleMessage(Message object) {
            switch (((Message)object).what) {
                default: {
                    break;
                }
                case 122: {
                    CallManager.this.mTtyModeReceivedRegistrants.notifyRegistrants((AsyncResult)((Message)object).obj);
                    break;
                }
                case 120: {
                    CallManager.this.mOnHoldToneRegistrants.notifyRegistrants((AsyncResult)((Message)object).obj);
                    break;
                }
                case 119: {
                    for (int i = 0; i < CallManager.this.mPostDialCharacterRegistrants.size(); ++i) {
                        Message message = ((Registrant)CallManager.this.mPostDialCharacterRegistrants.get(i)).messageForRegistrant();
                        message.obj = ((Message)object).obj;
                        message.arg1 = ((Message)object).arg1;
                        message.sendToTarget();
                    }
                    break;
                }
                case 118: {
                    CallManager.this.mServiceStateChangedRegistrants.notifyRegistrants((AsyncResult)((Message)object).obj);
                    break;
                }
                case 117: {
                    CallManager.this.mSuppServiceFailedRegistrants.notifyRegistrants((AsyncResult)((Message)object).obj);
                    break;
                }
                case 116: {
                    CallManager.this.mSubscriptionInfoReadyRegistrants.notifyRegistrants((AsyncResult)((Message)object).obj);
                    break;
                }
                case 115: {
                    CallManager.this.mEcmTimerResetRegistrants.notifyRegistrants((AsyncResult)((Message)object).obj);
                    break;
                }
                case 114: {
                    Rlog.d((String)CallManager.LOG_TAG, (String)"CallManager: handleMessage (EVENT_MMI_COMPLETE)");
                    CallManager.this.mMmiCompleteRegistrants.notifyRegistrants((AsyncResult)((Message)object).obj);
                    break;
                }
                case 113: {
                    CallManager.this.mMmiInitiateRegistrants.notifyRegistrants((AsyncResult)((Message)object).obj);
                    break;
                }
                case 112: {
                    CallManager.this.mResendIncallMuteRegistrants.notifyRegistrants((AsyncResult)((Message)object).obj);
                    break;
                }
                case 111: {
                    CallManager.this.mCdmaOtaStatusChangeRegistrants.notifyRegistrants((AsyncResult)((Message)object).obj);
                    break;
                }
                case 110: {
                    CallManager.this.mSignalInfoRegistrants.notifyRegistrants((AsyncResult)((Message)object).obj);
                    break;
                }
                case 109: {
                    CallManager.this.mDisplayInfoRegistrants.notifyRegistrants((AsyncResult)((Message)object).obj);
                    break;
                }
                case 108: {
                    CallManager.this.mCallWaitingRegistrants.notifyRegistrants((AsyncResult)((Message)object).obj);
                    break;
                }
                case 107: {
                    CallManager.this.mInCallVoicePrivacyOffRegistrants.notifyRegistrants((AsyncResult)((Message)object).obj);
                    break;
                }
                case 106: {
                    CallManager.this.mInCallVoicePrivacyOnRegistrants.notifyRegistrants((AsyncResult)((Message)object).obj);
                    break;
                }
                case 105: {
                    CallManager.this.mRingbackToneRegistrants.notifyRegistrants((AsyncResult)((Message)object).obj);
                    break;
                }
                case 104: {
                    if (CallManager.this.hasActiveFgCall()) break;
                    CallManager.this.mIncomingRingRegistrants.notifyRegistrants((AsyncResult)((Message)object).obj);
                    break;
                }
                case 103: {
                    CallManager.this.mUnknownConnectionRegistrants.notifyRegistrants((AsyncResult)((Message)object).obj);
                    break;
                }
                case 102: {
                    Connection connection = (Connection)((AsyncResult)object.obj).result;
                    int n = connection.getCall().getPhone().getSubId();
                    if (!CallManager.this.getActiveFgCallState(n).isDialing() && !CallManager.this.hasMoreThanOneRingingCall()) {
                        CallManager.this.mNewRingingConnectionRegistrants.notifyRegistrants((AsyncResult)((Message)object).obj);
                        break;
                    }
                    try {
                        object = new StringBuilder();
                        ((StringBuilder)object).append("silently drop incoming call: ");
                        ((StringBuilder)object).append(connection.getCall());
                        Rlog.d((String)CallManager.LOG_TAG, (String)((StringBuilder)object).toString());
                        connection.getCall().hangup();
                    }
                    catch (CallStateException callStateException) {
                        Rlog.w((String)CallManager.LOG_TAG, (String)"new ringing connection", (Throwable)callStateException);
                    }
                    break;
                }
                case 101: {
                    CallManager.this.mPreciseCallStateRegistrants.notifyRegistrants((AsyncResult)((Message)object).obj);
                    break;
                }
                case 100: {
                    CallManager.this.mDisconnectRegistrants.notifyRegistrants((AsyncResult)((Message)object).obj);
                }
            }
        }
    }

}

