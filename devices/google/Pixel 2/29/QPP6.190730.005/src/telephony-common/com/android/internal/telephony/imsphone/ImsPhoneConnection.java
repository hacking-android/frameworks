/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.annotation.UnsupportedAppUsage
 *  android.content.Context
 *  android.content.res.Resources
 *  android.net.Uri
 *  android.os.AsyncResult
 *  android.os.Bundle
 *  android.os.Handler
 *  android.os.Looper
 *  android.os.Message
 *  android.os.Messenger
 *  android.os.PersistableBundle
 *  android.os.PowerManager
 *  android.os.PowerManager$WakeLock
 *  android.os.Registrant
 *  android.os.SystemClock
 *  android.telecom.Connection
 *  android.telecom.Connection$RttTextStream
 *  android.telecom.Connection$VideoProvider
 *  android.telecom.VideoProfile
 *  android.telephony.CarrierConfigManager
 *  android.telephony.PhoneNumberUtils
 *  android.telephony.Rlog
 *  android.telephony.ims.ImsCallProfile
 *  android.telephony.ims.ImsCallSession
 *  android.telephony.ims.ImsStreamMediaProfile
 *  android.text.TextUtils
 *  com.android.ims.ImsCall
 *  com.android.ims.ImsException
 *  com.android.ims.internal.ImsVideoCallProviderWrapper
 *  com.android.ims.internal.ImsVideoCallProviderWrapper$ImsVideoProviderWrapperCallback
 */
package com.android.internal.telephony.imsphone;

import android.annotation.UnsupportedAppUsage;
import android.content.Context;
import android.content.res.Resources;
import android.net.Uri;
import android.os.AsyncResult;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.Messenger;
import android.os.PersistableBundle;
import android.os.PowerManager;
import android.os.Registrant;
import android.os.SystemClock;
import android.telecom.Connection;
import android.telecom.VideoProfile;
import android.telephony.CarrierConfigManager;
import android.telephony.PhoneNumberUtils;
import android.telephony.Rlog;
import android.telephony.ims.ImsCallProfile;
import android.telephony.ims.ImsCallSession;
import android.telephony.ims.ImsStreamMediaProfile;
import android.text.TextUtils;
import com.android.ims.ImsCall;
import com.android.ims.ImsException;
import com.android.ims.internal.ImsVideoCallProviderWrapper;
import com.android.internal.telephony.Call;
import com.android.internal.telephony.CallStateException;
import com.android.internal.telephony.CallTracker;
import com.android.internal.telephony.Connection;
import com.android.internal.telephony.Phone;
import com.android.internal.telephony.UUSInfo;
import com.android.internal.telephony.imsphone.ImsPhone;
import com.android.internal.telephony.imsphone.ImsPhoneCall;
import com.android.internal.telephony.imsphone.ImsPhoneCallTracker;
import com.android.internal.telephony.imsphone.ImsRttTextHandler;
import com.android.internal.telephony.imsphone._$$Lambda$ImsPhoneConnection$gXYXXIQcibrbO2gQqP7d18avaBI;
import com.android.internal.telephony.metrics.TelephonyMetrics;
import java.util.Objects;
import java.util.Set;

public class ImsPhoneConnection
extends Connection
implements ImsVideoCallProviderWrapper.ImsVideoProviderWrapperCallback {
    private static final boolean DBG = true;
    private static final int EVENT_DTMF_DELAY_DONE = 5;
    private static final int EVENT_DTMF_DONE = 1;
    private static final int EVENT_NEXT_POST_DIAL = 3;
    private static final int EVENT_PAUSE_DONE = 2;
    private static final int EVENT_WAKE_LOCK_TIMEOUT = 4;
    private static final String LOG_TAG = "ImsPhoneConnection";
    private static final int PAUSE_DELAY_MILLIS = 3000;
    private static final int WAKE_LOCK_TIMEOUT_MILLIS = 60000;
    private int mAudioCodec = 0;
    private long mConferenceConnectTime = 0L;
    private long mDisconnectTime;
    @UnsupportedAppUsage
    private boolean mDisconnected;
    private int mDtmfToneDelay = 0;
    private Bundle mExtras = new Bundle();
    private Handler mHandler;
    private final Messenger mHandlerMessenger;
    private ImsCall mImsCall;
    private ImsVideoCallProviderWrapper mImsVideoCallProviderWrapper;
    private boolean mIsEmergency = false;
    private boolean mIsLocalVideoCapable = true;
    private boolean mIsMergeInProcess = false;
    private boolean mIsRttEnabledForCall = false;
    private TelephonyMetrics mMetrics = TelephonyMetrics.getInstance();
    @UnsupportedAppUsage
    private ImsPhoneCallTracker mOwner;
    @UnsupportedAppUsage
    private ImsPhoneCall mParent;
    private PowerManager.WakeLock mPartialWakeLock;
    private int mPreciseDisconnectCause = 0;
    private ImsRttTextHandler mRttTextHandler;
    private Connection.RttTextStream mRttTextStream;
    private boolean mShouldIgnoreVideoStateChanges = false;
    private UUSInfo mUusInfo;

    public ImsPhoneConnection(Phone phone, ImsCall object, ImsPhoneCallTracker object2, ImsPhoneCall imsPhoneCall, boolean bl) {
        super(5);
        this.createWakeLock(phone.getContext());
        this.acquireWakeLock();
        this.mOwner = object2;
        this.mHandler = new MyHandler(this.mOwner.getLooper());
        this.mHandlerMessenger = new Messenger(this.mHandler);
        this.mImsCall = object;
        if (object != null && object.getCallProfile() != null) {
            this.mAddress = object.getCallProfile().getCallExtra("oi");
            this.mCnapName = object.getCallProfile().getCallExtra("cna");
            this.mNumberPresentation = ImsCallProfile.OIRToPresentation((int)object.getCallProfile().getCallExtraInt("oir"));
            this.mCnapNamePresentation = ImsCallProfile.OIRToPresentation((int)object.getCallProfile().getCallExtraInt("cnap"));
            this.updateMediaCapabilities((ImsCall)object);
        } else {
            this.mNumberPresentation = 3;
            this.mCnapNamePresentation = 3;
        }
        this.mIsIncoming = bl ^ true;
        this.mCreateTime = System.currentTimeMillis();
        this.mUusInfo = null;
        this.updateExtras((ImsCall)object);
        this.mParent = imsPhoneCall;
        object2 = this.mParent;
        object = this.mIsIncoming ? Call.State.INCOMING : Call.State.DIALING;
        ((ImsPhoneCall)object2).attach(this, (Call.State)((Object)object));
        this.fetchDtmfToneDelay(phone);
        if (phone.getContext().getResources().getBoolean(17891568)) {
            this.setAudioModeIsVoip(true);
        }
    }

    public ImsPhoneConnection(Phone phone, String string, ImsPhoneCallTracker imsPhoneCallTracker, ImsPhoneCall imsPhoneCall, boolean bl) {
        super(5);
        this.createWakeLock(phone.getContext());
        this.acquireWakeLock();
        this.mOwner = imsPhoneCallTracker;
        this.mHandler = new MyHandler(this.mOwner.getLooper());
        this.mHandlerMessenger = new Messenger(this.mHandler);
        this.mDialString = string;
        this.mAddress = PhoneNumberUtils.extractNetworkPortionAlt((String)string);
        this.mPostDialString = PhoneNumberUtils.extractPostDialPortion((String)string);
        this.mIsIncoming = false;
        this.mCnapName = null;
        this.mCnapNamePresentation = 1;
        this.mNumberPresentation = 1;
        this.mCreateTime = System.currentTimeMillis();
        this.mParent = imsPhoneCall;
        imsPhoneCall.attachFake(this, Call.State.DIALING);
        this.mIsEmergency = bl;
        if (bl) {
            this.setEmergencyCallInfo(this.mOwner);
        }
        this.fetchDtmfToneDelay(phone);
        if (phone.getContext().getResources().getBoolean(17891568)) {
            this.setAudioModeIsVoip(true);
        }
    }

    @UnsupportedAppUsage
    private void acquireWakeLock() {
        Rlog.d((String)LOG_TAG, (String)"acquireWakeLock");
        this.mPartialWakeLock.acquire();
    }

    private int applyLocalCallCapabilities(ImsCallProfile imsCallProfile, int n) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("applyLocalCallCapabilities - localProfile = ");
        stringBuilder.append((Object)imsCallProfile);
        Rlog.i((String)LOG_TAG, (String)stringBuilder.toString());
        n = ImsPhoneConnection.removeCapability(n, 4);
        if (!this.mIsLocalVideoCapable) {
            Rlog.i((String)LOG_TAG, (String)"applyLocalCallCapabilities - disabling video (overidden)");
            return n;
        }
        int n2 = imsCallProfile.mCallType;
        if (n2 == 3 || n2 == 4) {
            n = ImsPhoneConnection.addCapability(n, 4);
        }
        return n;
    }

    private static int applyRemoteCallCapabilities(ImsCallProfile imsCallProfile, int n) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("applyRemoteCallCapabilities - remoteProfile = ");
        stringBuilder.append((Object)imsCallProfile);
        Rlog.w((String)LOG_TAG, (String)stringBuilder.toString());
        n = ImsPhoneConnection.removeCapability(n, 8);
        int n2 = imsCallProfile.mCallType;
        if (n2 == 3 || n2 == 4) {
            n = ImsPhoneConnection.addCapability(n, 8);
        }
        return n;
    }

    private static boolean areBundlesEqual(Bundle bundle, Bundle bundle2) {
        boolean bl = true;
        if (bundle != null && bundle2 != null) {
            if (bundle.size() != bundle2.size()) {
                return false;
            }
            for (String string : bundle.keySet()) {
                if (string == null || Objects.equals(bundle.get(string), bundle2.get(string))) continue;
                return false;
            }
            return true;
        }
        if (bundle != bundle2) {
            bl = false;
        }
        return bl;
    }

    private void createRttTextHandler() {
        this.mRttTextHandler = new ImsRttTextHandler(Looper.getMainLooper(), new _$$Lambda$ImsPhoneConnection$gXYXXIQcibrbO2gQqP7d18avaBI(this));
        this.mRttTextHandler.initialize(this.mRttTextStream);
    }

    @UnsupportedAppUsage
    private void createWakeLock(Context context) {
        this.mPartialWakeLock = ((PowerManager)context.getSystemService("power")).newWakeLock(1, LOG_TAG);
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

    private void fetchDtmfToneDelay(Phone phone) {
        if ((phone = ((CarrierConfigManager)phone.getContext().getSystemService("carrier_config")).getConfigForSubId(phone.getSubId())) != null) {
            this.mDtmfToneDelay = phone.getInt("ims_dtmf_tone_delay_int");
        }
    }

    private int getAudioQualityFromCallProfile(ImsCallProfile imsCallProfile, ImsCallProfile imsCallProfile2) {
        int n = 1;
        if (imsCallProfile != null && imsCallProfile2 != null && imsCallProfile.mMediaProfile != null) {
            int n2 = imsCallProfile.mMediaProfile.mAudioQuality;
            int n3 = 0;
            n2 = n2 != 18 && imsCallProfile.mMediaProfile.mAudioQuality != 19 && imsCallProfile.mMediaProfile.mAudioQuality != 20 ? 0 : 1;
            n2 = (imsCallProfile.mMediaProfile.mAudioQuality == 2 || imsCallProfile.mMediaProfile.mAudioQuality == 6 || n2 != 0) && imsCallProfile2.getRestrictCause() == 0 ? 1 : n3;
            if (n2 != 0) {
                n = 2;
            }
            return n;
        }
        return 1;
    }

    private void processNextPostDialChar() {
        CharSequence charSequence;
        int n;
        if (this.mPostDialState == Connection.PostDialState.CANCELLED) {
            return;
        }
        if (this.mPostDialString != null && this.mPostDialString.length() > this.mNextPostDialChar) {
            this.setPostDialState(Connection.PostDialState.STARTED);
            charSequence = this.mPostDialString;
            int n2 = this.mNextPostDialChar;
            this.mNextPostDialChar = n2 + 1;
            char c = ((String)charSequence).charAt(n2);
            n = c;
            if (!this.processPostDialChar(c)) {
                this.mHandler.obtainMessage(3).sendToTarget();
                charSequence = new StringBuilder();
                ((StringBuilder)charSequence).append("processNextPostDialChar: c=");
                ((StringBuilder)charSequence).append(c);
                ((StringBuilder)charSequence).append(" isn't valid!");
                Rlog.e((String)LOG_TAG, (String)((StringBuilder)charSequence).toString());
                return;
            }
        } else {
            int n3;
            this.setPostDialState(Connection.PostDialState.COMPLETE);
            n = n3 = 0;
        }
        this.notifyPostDialListenersNextChar((char)n);
        charSequence = this.mOwner.mPhone.getPostDialHandler();
        if (charSequence != null && (charSequence = charSequence.messageForRegistrant()) != null) {
            Connection.PostDialState postDialState = this.mPostDialState;
            AsyncResult asyncResult = AsyncResult.forMessage((Message)charSequence);
            asyncResult.result = this;
            asyncResult.userObj = postDialState;
            ((Message)charSequence).arg1 = n;
            charSequence.sendToTarget();
        }
    }

    private boolean processPostDialChar(char c) {
        block6 : {
            block3 : {
                block5 : {
                    block4 : {
                        block2 : {
                            if (!PhoneNumberUtils.is12Key((char)c)) break block2;
                            Message message = this.mHandler.obtainMessage(1);
                            message.replyTo = this.mHandlerMessenger;
                            this.mOwner.sendDtmf(c, message);
                            break block3;
                        }
                        if (c != ',') break block4;
                        Handler handler = this.mHandler;
                        handler.sendMessageDelayed(handler.obtainMessage(2), 3000L);
                        break block3;
                    }
                    if (c != ';') break block5;
                    this.setPostDialState(Connection.PostDialState.WAIT);
                    break block3;
                }
                if (c != 'N') break block6;
                this.setPostDialState(Connection.PostDialState.WILD);
            }
            return true;
        }
        return false;
    }

    private void setPostDialState(Connection.PostDialState postDialState) {
        if (this.mPostDialState != Connection.PostDialState.STARTED && postDialState == Connection.PostDialState.STARTED) {
            this.acquireWakeLock();
            Message message = this.mHandler.obtainMessage(4);
            this.mHandler.sendMessageDelayed(message, 60000L);
        } else if (this.mPostDialState == Connection.PostDialState.STARTED && postDialState != Connection.PostDialState.STARTED) {
            this.mHandler.removeMessages(4);
            this.releaseWakeLock();
        }
        this.mPostDialState = postDialState;
        this.notifyPostDialListeners();
    }

    private void updateEmergencyCallFromExtras(Bundle bundle) {
        if (bundle.getBoolean("e_call")) {
            this.setIsNetworkIdentifiedEmergencyCall(true);
        }
    }

    private void updateImsCallRatFromExtras(Bundle bundle) {
        if (bundle.containsKey("CallRadioTech") || bundle.containsKey("callRadioTech")) {
            bundle = this.getImsCall();
            int n = 0;
            if (bundle != null) {
                n = bundle.getRadioTechnology();
            }
            this.setCallRadioTech(n);
        }
    }

    private void updateVideoState(int n) {
        ImsVideoCallProviderWrapper imsVideoCallProviderWrapper = this.mImsVideoCallProviderWrapper;
        if (imsVideoCallProviderWrapper != null) {
            imsVideoCallProviderWrapper.onVideoStateChanged(n);
        }
        this.setVideoState(n);
    }

    @Override
    public void cancelPostDial() {
        this.setPostDialState(Connection.PostDialState.CANCELLED);
    }

    public void changeParent(ImsPhoneCall imsPhoneCall) {
        this.mParent = imsPhoneCall;
    }

    public void changeToPausedState() {
        int n = this.getVideoState() | 4;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("ImsPhoneConnection: changeToPausedState - setting paused bit; newVideoState=");
        stringBuilder.append(VideoProfile.videoStateToString((int)n));
        Rlog.i((String)LOG_TAG, (String)stringBuilder.toString());
        this.updateVideoState(n);
        this.mShouldIgnoreVideoStateChanges = true;
    }

    public void changeToUnPausedState() {
        int n = this.getVideoState() & -5;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("ImsPhoneConnection: changeToUnPausedState - unsetting paused bit; newVideoState=");
        stringBuilder.append(VideoProfile.videoStateToString((int)n));
        Rlog.i((String)LOG_TAG, (String)stringBuilder.toString());
        this.updateVideoState(n);
        this.mShouldIgnoreVideoStateChanges = false;
    }

    @Override
    public void deflect(String object) throws CallStateException {
        if (this.mParent.getState().isRinging()) {
            try {
                if (this.mImsCall != null) {
                    this.mImsCall.deflect((String)object);
                    return;
                }
                object = new CallStateException("no valid ims call to deflect");
                throw object;
            }
            catch (ImsException imsException) {
                throw new CallStateException("cannot deflect call");
            }
        }
        throw new CallStateException("phone not ringing");
    }

    public void dispose() {
    }

    protected void finalize() {
        this.releaseWakeLock();
    }

    @UnsupportedAppUsage
    @Override
    public ImsPhoneCall getCall() {
        return this.mParent;
    }

    public long getConferenceConnectTime() {
        return this.mConferenceConnectTime;
    }

    @Override
    public long getDisconnectTime() {
        return this.mDisconnectTime;
    }

    @Override
    public long getHoldDurationMillis() {
        if (this.getState() != Call.State.HOLDING) {
            return 0L;
        }
        return SystemClock.elapsedRealtime() - this.mHoldingStartTime;
    }

    @Override
    public long getHoldingStartTime() {
        return this.mHoldingStartTime;
    }

    public ImsCall getImsCall() {
        synchronized (this) {
            ImsCall imsCall = this.mImsCall;
            return imsCall;
        }
    }

    @Override
    public int getNumberPresentation() {
        return this.mNumberPresentation;
    }

    @Override
    public Connection getOrigConnection() {
        return null;
    }

    @Override
    public String getOrigDialString() {
        return this.mDialString;
    }

    @UnsupportedAppUsage
    public ImsPhoneCallTracker getOwner() {
        return this.mOwner;
    }

    @Override
    public int getPreciseDisconnectCause() {
        return this.mPreciseDisconnectCause;
    }

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
        return null;
    }

    public void handleMergeComplete() {
        this.mIsMergeInProcess = false;
        this.onConnectionEvent("android.telecom.event.MERGE_COMPLETE", null);
    }

    public void handleMergeStart() {
        this.mIsMergeInProcess = true;
        this.onConnectionEvent("android.telecom.event.MERGE_START", null);
    }

    @Override
    public void hangup() throws CallStateException {
        if (!this.mDisconnected) {
            this.mOwner.hangup(this);
            return;
        }
        throw new CallStateException("disconnected");
    }

    public boolean hasRttTextStream() {
        boolean bl = this.mRttTextStream != null;
        return bl;
    }

    @Override
    public boolean isConferenceHost() {
        synchronized (this) {
            boolean bl;
            bl = this.mImsCall != null && (bl = this.mImsCall.isConferenceHost());
            return bl;
        }
    }

    protected boolean isEmergency() {
        return this.mIsEmergency;
    }

    @Override
    public boolean isMemberOfPeerConference() {
        return this.isConferenceHost() ^ true;
    }

    @UnsupportedAppUsage
    @Override
    public boolean isMultiparty() {
        synchronized (this) {
            boolean bl;
            bl = this.mImsCall != null && (bl = this.mImsCall.isMultiparty());
            return bl;
        }
    }

    public boolean isRttEnabledForCall() {
        return this.mIsRttEnabledForCall;
    }

    public /* synthetic */ void lambda$createRttTextHandler$0$ImsPhoneConnection(String string) {
        ImsCall imsCall = this.getImsCall();
        if (imsCall != null) {
            imsCall.sendRttMessage(string);
        }
    }

    void onConnectedInOrOut() {
        this.mConnectTime = System.currentTimeMillis();
        this.mConnectTimeReal = SystemClock.elapsedRealtime();
        this.mDuration = 0L;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("onConnectedInOrOut: connectTime=");
        stringBuilder.append(this.mConnectTime);
        Rlog.d((String)LOG_TAG, (String)stringBuilder.toString());
        if (!this.mIsIncoming) {
            this.processNextPostDialChar();
        }
        this.releaseWakeLock();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @UnsupportedAppUsage
    public boolean onDisconnect() {
        boolean bl = false;
        if (!this.mDisconnected) {
            this.mDisconnectTime = System.currentTimeMillis();
            this.mDuration = SystemClock.elapsedRealtime() - this.mConnectTimeReal;
            this.mDisconnected = true;
            this.mOwner.mPhone.notifyDisconnect(this);
            this.notifyDisconnect(this.mCause);
            ImsPhoneCall imsPhoneCall = this.mParent;
            if (imsPhoneCall != null) {
                bl = imsPhoneCall.connectionDisconnected(this);
            } else {
                Rlog.d((String)LOG_TAG, (String)"onDisconnect: no parent");
                bl = false;
            }
            synchronized (this) {
                if (this.mRttTextHandler != null) {
                    this.mRttTextHandler.tearDown();
                }
                if (this.mImsCall != null) {
                    this.mImsCall.close();
                }
                this.mImsCall = null;
            }
        }
        this.releaseWakeLock();
        return bl;
    }

    @Override
    public boolean onDisconnect(int n) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("onDisconnect: cause=");
        stringBuilder.append(n);
        Rlog.d((String)LOG_TAG, (String)stringBuilder.toString());
        if (this.mCause != 3 || n == 16) {
            this.mCause = n;
        }
        return this.onDisconnect();
    }

    @Override
    public void onDisconnectConferenceParticipant(Uri uri) {
        ImsCall imsCall = this.getImsCall();
        if (imsCall == null) {
            return;
        }
        try {
            imsCall.removeParticipants(new String[]{uri.toString()});
        }
        catch (ImsException imsException) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("onDisconnectConferenceParticipant: no session in place. Failed to disconnect endpoint = ");
            stringBuilder.append((Object)uri);
            Rlog.e((String)LOG_TAG, (String)stringBuilder.toString());
        }
    }

    void onHangupLocal() {
        this.mCause = 3;
    }

    public void onReceiveSessionModifyResponse(int n, VideoProfile videoProfile, VideoProfile videoProfile2) {
        if (n == 1 && this.mShouldIgnoreVideoStateChanges) {
            int n2 = this.getVideoState();
            int n3 = (n2 ^ (n = videoProfile2.getVideoState())) & 3;
            if (n3 == 0) {
                return;
            }
            n = n2 & (n3 & n2) | n3 & n;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("onReceiveSessionModifyResponse : received ");
            stringBuilder.append(VideoProfile.videoStateToString((int)videoProfile.getVideoState()));
            stringBuilder.append(" / ");
            stringBuilder.append(VideoProfile.videoStateToString((int)videoProfile2.getVideoState()));
            stringBuilder.append(" while paused ; sending new videoState = ");
            stringBuilder.append(VideoProfile.videoStateToString((int)n));
            Rlog.d((String)LOG_TAG, (String)stringBuilder.toString());
            this.setVideoState(n);
        }
    }

    public void onRttAudioIndicatorChanged(ImsStreamMediaProfile imsStreamMediaProfile) {
        Bundle bundle = new Bundle();
        bundle.putBoolean("android.telecom.extra.IS_RTT_AUDIO_PRESENT", imsStreamMediaProfile.isReceivingRttAudio());
        this.onConnectionEvent("android.telecom.event.RTT_AUDIO_INDICATION_CHANGED", bundle);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void onRttMessageReceived(String string) {
        synchronized (this) {
            if (this.mRttTextHandler == null) {
                Rlog.w((String)LOG_TAG, (String)"onRttMessageReceived: RTT text handler not available. Attempting to create one.");
                if (this.mRttTextStream == null) {
                    Rlog.e((String)LOG_TAG, (String)"onRttMessageReceived: Unable to process incoming message. No textstream available");
                    return;
                }
                this.createRttTextHandler();
            }
        }
        this.mRttTextHandler.sendToInCall(string);
    }

    void onStartedHolding() {
        this.mHoldingStartTime = SystemClock.elapsedRealtime();
    }

    public void pauseVideo(int n) {
        ImsVideoCallProviderWrapper imsVideoCallProviderWrapper = this.mImsVideoCallProviderWrapper;
        if (imsVideoCallProviderWrapper == null) {
            return;
        }
        imsVideoCallProviderWrapper.pauseVideo(this.getVideoState(), n);
    }

    @Override
    public void proceedAfterWaitChar() {
        if (this.mPostDialState != Connection.PostDialState.WAIT) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("ImsPhoneConnection.proceedAfterWaitChar(): Expected getPostDialState() to be WAIT but was ");
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
            ((StringBuilder)charSequence).append("ImsPhoneConnection.proceedAfterWaitChar(): Expected getPostDialState() to be WILD but was ");
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
        Rlog.d((String)LOG_TAG, (String)((StringBuilder)charSequence).toString());
        this.processNextPostDialChar();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    void releaseWakeLock() {
        PowerManager.WakeLock wakeLock = this.mPartialWakeLock;
        if (wakeLock == null) return;
        synchronized (wakeLock) {
            if (!this.mPartialWakeLock.isHeld()) return;
            Rlog.d((String)LOG_TAG, (String)"releaseWakeLock");
            this.mPartialWakeLock.release();
            return;
        }
    }

    public void resumeVideo(int n) {
        ImsVideoCallProviderWrapper imsVideoCallProviderWrapper = this.mImsVideoCallProviderWrapper;
        if (imsVideoCallProviderWrapper == null) {
            return;
        }
        imsVideoCallProviderWrapper.resumeVideo(this.getVideoState(), n);
    }

    public void sendRttModifyResponse(Connection.RttTextStream rttTextStream) {
        boolean bl = rttTextStream != null;
        ImsCall imsCall = this.getImsCall();
        if (imsCall != null) {
            imsCall.sendRttModifyResponse(bl);
            if (bl) {
                this.setCurrentRttTextStream(rttTextStream);
            } else {
                Rlog.e((String)LOG_TAG, (String)"sendRttModifyResponse: foreground call has no connections");
            }
        }
    }

    @Override
    public void separate() throws CallStateException {
        throw new CallStateException("not supported");
    }

    public void setConferenceConnectTime(long l) {
        this.mConferenceConnectTime = l;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void setCurrentRttTextStream(Connection.RttTextStream rttTextStream) {
        synchronized (this) {
            this.mRttTextStream = rttTextStream;
            if (this.mRttTextHandler == null && this.mIsRttEnabledForCall) {
                Rlog.i((String)LOG_TAG, (String)"setCurrentRttTextStream: Creating a text handler");
                this.createRttTextHandler();
            }
            return;
        }
    }

    public void setDisconnectCause(int n) {
        this.mCause = n;
    }

    public void setImsCall(ImsCall imsCall) {
        synchronized (this) {
            this.mImsCall = imsCall;
            return;
        }
    }

    public void setLocalVideoCapable(boolean bl) {
        this.mIsLocalVideoCapable = bl;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("setLocalVideoCapable: mIsLocalVideoCapable = ");
        stringBuilder.append(this.mIsLocalVideoCapable);
        stringBuilder.append("; updating local video availability.");
        Rlog.i((String)LOG_TAG, (String)stringBuilder.toString());
        this.updateMediaCapabilities(this.getImsCall());
    }

    public void setPreciseDisconnectCause(int n) {
        this.mPreciseDisconnectCause = n;
    }

    @Override
    public void setVideoProvider(Connection.VideoProvider videoProvider) {
        super.setVideoProvider(videoProvider);
        if (videoProvider instanceof ImsVideoCallProviderWrapper) {
            this.mImsVideoCallProviderWrapper = (ImsVideoCallProviderWrapper)videoProvider;
        }
    }

    public void startRtt(Connection.RttTextStream rttTextStream) {
        if (this.getImsCall() != null) {
            this.getImsCall().sendRttModifyRequest(true);
            this.setCurrentRttTextStream(rttTextStream);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void startRttTextProcessing() {
        synchronized (this) {
            if (this.mRttTextStream == null) {
                Rlog.w((String)LOG_TAG, (String)"startRttTextProcessing: no RTT text stream. Ignoring.");
                return;
            }
            if (this.mRttTextHandler != null) {
                Rlog.w((String)LOG_TAG, (String)"startRttTextProcessing: RTT text handler already exists");
                return;
            }
            this.createRttTextHandler();
            return;
        }
    }

    public void stopRtt() {
        this.getImsCall().sendRttModifyRequest(false);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[ImsPhoneConnection objId: ");
        stringBuilder.append(System.identityHashCode(this));
        stringBuilder.append(" telecomCallID: ");
        stringBuilder.append(this.getTelecomCallId());
        stringBuilder.append(" address: ");
        stringBuilder.append(Rlog.pii((String)LOG_TAG, (Object)this.getAddress()));
        stringBuilder.append(" ImsCall: ");
        synchronized (this) {
            if (this.mImsCall == null) {
                stringBuilder.append("null");
            } else {
                stringBuilder.append((Object)this.mImsCall);
            }
        }
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    public boolean update(ImsCall imsCall, Call.State state) {
        Call.State state2 = Call.State.ACTIVE;
        boolean bl = false;
        if (state == state2) {
            if (imsCall.isPendingHold()) {
                Rlog.w((String)LOG_TAG, (String)"update : state is ACTIVE, but call is pending hold, skipping");
                return false;
            }
            if (this.mParent.getState().isRinging() || this.mParent.getState().isDialing()) {
                this.onConnectedInOrOut();
            }
            if (this.mParent.getState().isRinging() || this.mParent == this.mOwner.mBackgroundCall) {
                this.mParent.detach(this);
                this.mParent = this.mOwner.mForegroundCall;
                this.mParent.attach(this);
            }
        } else if (state == Call.State.HOLDING) {
            this.onStartedHolding();
        }
        boolean bl2 = this.mParent.update(this, imsCall, state);
        boolean bl3 = this.updateAddressDisplay(imsCall);
        boolean bl4 = this.updateMediaCapabilities(imsCall);
        boolean bl5 = this.updateExtras(imsCall);
        if (bl2 || bl3 || bl4 || bl5) {
            bl = true;
        }
        return bl;
    }

    public boolean updateAddressDisplay(ImsCall object) {
        if (object == null) {
            return false;
        }
        boolean bl = false;
        boolean bl2 = false;
        Object object2 = object.getCallProfile();
        boolean bl3 = bl;
        if (object2 != null) {
            bl3 = bl;
            if (this.isIncoming()) {
                object = object2.getCallExtra("oi");
                String string = object2.getCallExtra("cna");
                int n = ImsCallProfile.OIRToPresentation((int)object2.getCallExtraInt("oir"));
                int n2 = ImsCallProfile.OIRToPresentation((int)object2.getCallExtraInt("cnap"));
                object2 = new StringBuilder();
                ((StringBuilder)object2).append("updateAddressDisplay: callId = ");
                ((StringBuilder)object2).append(this.getTelecomCallId());
                ((StringBuilder)object2).append(" address = ");
                ((StringBuilder)object2).append(Rlog.pii((String)LOG_TAG, (Object)object));
                ((StringBuilder)object2).append(" name = ");
                ((StringBuilder)object2).append(Rlog.pii((String)LOG_TAG, (Object)string));
                ((StringBuilder)object2).append(" nump = ");
                ((StringBuilder)object2).append(n);
                ((StringBuilder)object2).append(" namep = ");
                ((StringBuilder)object2).append(n2);
                Rlog.d((String)LOG_TAG, (String)((StringBuilder)object2).toString());
                bl3 = bl;
                if (!this.mIsMergeInProcess) {
                    bl3 = bl2;
                    if (!ImsPhoneConnection.equalsBaseDialString(this.mAddress, (String)object)) {
                        this.mAddress = object;
                        bl3 = true;
                    }
                    if (TextUtils.isEmpty((CharSequence)string)) {
                        if (!TextUtils.isEmpty((CharSequence)this.mCnapName)) {
                            this.mCnapName = "";
                            bl3 = true;
                        }
                    } else if (!string.equals(this.mCnapName)) {
                        this.mCnapName = string;
                        bl3 = true;
                    }
                    if (this.mNumberPresentation != n) {
                        this.mNumberPresentation = n;
                        bl3 = true;
                    }
                    if (this.mCnapNamePresentation != n2) {
                        this.mCnapNamePresentation = n2;
                        bl3 = true;
                    }
                }
            }
        }
        return bl3;
    }

    boolean updateExtras(ImsCall object) {
        boolean bl;
        if (object == null) {
            return false;
        }
        if ((object = (object = object.getCallProfile()) != null ? object.mCallExtras : null) == null) {
            Rlog.d((String)LOG_TAG, (String)"Call profile extras are null.");
        }
        if (bl = ImsPhoneConnection.areBundlesEqual((Bundle)object, this.mExtras) ^ true) {
            this.updateImsCallRatFromExtras((Bundle)object);
            this.updateEmergencyCallFromExtras((Bundle)object);
            this.mExtras.clear();
            this.mExtras.putAll((Bundle)object);
            this.setConnectionExtras(this.mExtras);
        }
        return bl;
    }

    public boolean updateMediaCapabilities(ImsCall imsCall) {
        boolean bl;
        boolean bl2;
        block80 : {
            StringBuilder stringBuilder;
            ImsCallProfile imsCallProfile;
            boolean bl3;
            int n;
            block79 : {
                block78 : {
                    block77 : {
                        block76 : {
                            int n2;
                            block75 : {
                                block74 : {
                                    block73 : {
                                        block72 : {
                                            block66 : {
                                                block71 : {
                                                    block67 : {
                                                        block70 : {
                                                            boolean bl4;
                                                            boolean bl5;
                                                            block69 : {
                                                                block68 : {
                                                                    if (imsCall == null) {
                                                                        return false;
                                                                    }
                                                                    bl3 = false;
                                                                    bl = false;
                                                                    bl5 = false;
                                                                    bl4 = false;
                                                                    bl2 = bl5;
                                                                    imsCallProfile = imsCall.getCallProfile();
                                                                    if (imsCallProfile == null) break block66;
                                                                    bl2 = bl5;
                                                                    n2 = this.getVideoState();
                                                                    bl2 = bl5;
                                                                    n = ImsCallProfile.getVideoStateFromImsCallProfile((ImsCallProfile)imsCallProfile);
                                                                    if (n2 == n) break block67;
                                                                    bl2 = bl5;
                                                                    if (!VideoProfile.isPaused((int)n2)) break block68;
                                                                    bl2 = bl5;
                                                                    if (VideoProfile.isPaused((int)n)) break block68;
                                                                    bl2 = bl5;
                                                                    this.mShouldIgnoreVideoStateChanges = false;
                                                                }
                                                                bl2 = bl5;
                                                                if (this.mShouldIgnoreVideoStateChanges) break block69;
                                                                bl2 = bl5;
                                                                this.updateVideoState(n);
                                                                bl = true;
                                                                break block70;
                                                            }
                                                            bl2 = bl5;
                                                            Rlog.d((String)LOG_TAG, (String)"updateMediaCapabilities - ignoring video state change due to paused state.");
                                                            bl = bl4;
                                                        }
                                                        bl3 = bl;
                                                        bl2 = bl;
                                                        if (VideoProfile.isPaused((int)n2)) break block67;
                                                        bl3 = bl;
                                                        bl2 = bl;
                                                        if (!VideoProfile.isPaused((int)n)) break block67;
                                                        bl2 = bl;
                                                        this.mShouldIgnoreVideoStateChanges = true;
                                                        bl3 = bl;
                                                    }
                                                    bl = bl3;
                                                    bl2 = bl3;
                                                    if (imsCallProfile.mMediaProfile == null) break block66;
                                                    bl2 = bl3;
                                                    this.mIsRttEnabledForCall = imsCallProfile.mMediaProfile.isRttCall();
                                                    bl2 = bl3;
                                                    if (!this.mIsRttEnabledForCall) break block71;
                                                    bl2 = bl3;
                                                    if (this.mRttTextHandler != null) break block71;
                                                    bl2 = bl3;
                                                    bl2 = bl3;
                                                    stringBuilder = new StringBuilder();
                                                    bl2 = bl3;
                                                    stringBuilder.append("updateMediaCapabilities -- turning RTT on, profile=");
                                                    bl2 = bl3;
                                                    stringBuilder.append((Object)imsCallProfile);
                                                    bl2 = bl3;
                                                    Rlog.d((String)LOG_TAG, (String)stringBuilder.toString());
                                                    bl2 = bl3;
                                                    this.startRttTextProcessing();
                                                    bl2 = bl3;
                                                    this.onRttInitiated();
                                                    bl = true;
                                                    break block66;
                                                }
                                                bl = bl3;
                                                bl2 = bl3;
                                                if (this.mIsRttEnabledForCall) break block66;
                                                bl = bl3;
                                                bl2 = bl3;
                                                if (this.mRttTextHandler == null) break block66;
                                                bl2 = bl3;
                                                bl2 = bl3;
                                                stringBuilder = new StringBuilder();
                                                bl2 = bl3;
                                                stringBuilder.append("updateMediaCapabilities -- turning RTT off, profile=");
                                                bl2 = bl3;
                                                stringBuilder.append((Object)imsCallProfile);
                                                bl2 = bl3;
                                                Rlog.d((String)LOG_TAG, (String)stringBuilder.toString());
                                                bl2 = bl3;
                                                this.mRttTextHandler.tearDown();
                                                bl2 = bl3;
                                                this.mRttTextHandler = null;
                                                bl2 = bl3;
                                                this.onRttTerminated();
                                                bl = true;
                                            }
                                            bl2 = bl;
                                            n = this.getConnectionCapabilities();
                                            bl2 = bl;
                                            if (!this.mOwner.isCarrierDowngradeOfVtCallSupported()) break block72;
                                            bl2 = bl;
                                            n2 = ImsPhoneConnection.addCapability(n, 3);
                                            break block73;
                                        }
                                        bl2 = bl;
                                        n2 = ImsPhoneConnection.removeCapability(n, 3);
                                    }
                                    bl2 = bl;
                                    imsCallProfile = imsCall.getLocalCallProfile();
                                    bl2 = bl;
                                    bl2 = bl;
                                    stringBuilder = new StringBuilder();
                                    bl2 = bl;
                                    stringBuilder.append("update localCallProfile=");
                                    bl2 = bl;
                                    stringBuilder.append((Object)imsCallProfile);
                                    bl2 = bl;
                                    Rlog.v((String)LOG_TAG, (String)stringBuilder.toString());
                                    n = n2;
                                    if (imsCallProfile == null) break block74;
                                    bl2 = bl;
                                    n = this.applyLocalCallCapabilities(imsCallProfile, n2);
                                }
                                bl2 = bl;
                                stringBuilder = imsCall.getRemoteCallProfile();
                                bl2 = bl;
                                bl2 = bl;
                                StringBuilder stringBuilder2 = new StringBuilder();
                                bl2 = bl;
                                stringBuilder2.append("update remoteCallProfile=");
                                bl2 = bl;
                                stringBuilder2.append((Object)stringBuilder);
                                bl2 = bl;
                                Rlog.v((String)LOG_TAG, (String)stringBuilder2.toString());
                                n2 = n;
                                if (stringBuilder == null) break block75;
                                bl2 = bl;
                                n2 = ImsPhoneConnection.applyRemoteCallCapabilities((ImsCallProfile)stringBuilder, n);
                            }
                            bl3 = bl;
                            bl2 = bl;
                            if (this.getConnectionCapabilities() == n2) break block76;
                            bl2 = bl;
                            this.setConnectionCapabilities(n2);
                            bl3 = true;
                        }
                        bl2 = bl3;
                        if (this.mOwner.isViLteDataMetered()) break block77;
                        bl2 = bl3;
                        Rlog.v((String)LOG_TAG, (String)"data is not metered");
                        break block78;
                    }
                    bl2 = bl3;
                    if (this.mImsVideoCallProviderWrapper == null) break block78;
                    bl2 = bl3;
                    this.mImsVideoCallProviderWrapper.setIsVideoEnabled(this.hasCapabilities(4));
                }
                if (imsCallProfile != null) {
                    bl2 = bl3;
                    if (imsCallProfile.mMediaProfile.mAudioQuality == this.mAudioCodec) break block79;
                    bl2 = bl3;
                    this.mAudioCodec = imsCallProfile.mMediaProfile.mAudioQuality;
                    bl2 = bl3;
                    this.mMetrics.writeAudioCodecIms(this.mOwner.mPhone.getPhoneId(), imsCall.getCallSession());
                }
            }
            bl2 = bl3;
            n = this.getAudioQualityFromCallProfile(imsCallProfile, (ImsCallProfile)stringBuilder);
            bl = bl3;
            bl2 = bl3;
            if (this.getAudioQuality() == n) break block80;
            bl2 = bl3;
            try {
                this.setAudioQuality(n);
                bl = true;
            }
            catch (ImsException imsException) {
                // empty catch block
            }
        }
        bl2 = bl;
        return bl2;
    }

    public boolean wasVideoPausedFromSource(int n) {
        ImsVideoCallProviderWrapper imsVideoCallProviderWrapper = this.mImsVideoCallProviderWrapper;
        if (imsVideoCallProviderWrapper == null) {
            return false;
        }
        return imsVideoCallProviderWrapper.wasVideoPausedFromSource(n);
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
                        ImsPhoneConnection.this.releaseWakeLock();
                        break block3;
                    }
                    ImsPhoneConnection.this.processNextPostDialChar();
                    break block3;
                }
                ImsPhoneConnection.this.mHandler.sendMessageDelayed(ImsPhoneConnection.this.mHandler.obtainMessage(5), (long)ImsPhoneConnection.this.mDtmfToneDelay);
            }
        }
    }

}

