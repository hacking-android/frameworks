/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.annotation.UnsupportedAppUsage
 *  android.net.Uri
 *  android.os.Bundle
 *  android.os.Handler
 *  android.os.SystemClock
 *  android.telecom.ConferenceParticipant
 *  android.telecom.Connection
 *  android.telecom.Connection$VideoProvider
 *  android.telephony.Rlog
 *  android.telephony.emergency.EmergencyNumber
 */
package com.android.internal.telephony;

import android.annotation.UnsupportedAppUsage;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.telecom.ConferenceParticipant;
import android.telecom.Connection;
import android.telephony.Rlog;
import android.telephony.emergency.EmergencyNumber;
import com.android.internal.telephony.Call;
import com.android.internal.telephony.CallStateException;
import com.android.internal.telephony.CallTracker;
import com.android.internal.telephony.Phone;
import com.android.internal.telephony.UUSInfo;
import com.android.internal.telephony.emergency.EmergencyNumberTracker;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

public abstract class Connection {
    public static final int AUDIO_QUALITY_HIGH_DEFINITION = 2;
    public static final int AUDIO_QUALITY_STANDARD = 1;
    @UnsupportedAppUsage
    private static String LOG_TAG = "Connection";
    private static final String TAG = "Connection";
    @UnsupportedAppUsage
    protected String mAddress;
    private boolean mAllowAddCallDuringVideoCall;
    private boolean mAnsweringDisconnectsActiveCall;
    private boolean mAudioModeIsVoip;
    private int mAudioQuality;
    private int mCallRadioTech = 0;
    private int mCallSubstate;
    protected int mCause = 0;
    @UnsupportedAppUsage
    protected String mCnapName;
    @UnsupportedAppUsage
    protected int mCnapNamePresentation = 1;
    protected long mConnectTime;
    protected long mConnectTimeReal;
    private int mConnectionCapabilities;
    protected String mConvertedNumber;
    protected long mCreateTime;
    @UnsupportedAppUsage
    protected String mDialString;
    @UnsupportedAppUsage
    protected long mDuration;
    private EmergencyNumber mEmergencyNumberInfo;
    private Bundle mExtras;
    private boolean mHasKnownUserIntentEmergency;
    protected long mHoldingStartTime;
    private boolean mIsEmergencyCall;
    @UnsupportedAppUsage
    protected boolean mIsIncoming;
    private boolean mIsNetworkIdentifiedEmergencyCall;
    private boolean mIsPulledCall = false;
    public Set<Listener> mListeners = new CopyOnWriteArraySet<Listener>();
    protected int mNextPostDialChar;
    protected boolean mNumberConverted = false;
    @UnsupportedAppUsage
    protected int mNumberPresentation = 1;
    protected Connection mOrigConnection;
    private int mPhoneType;
    private List<PostDialListener> mPostDialListeners = new ArrayList<PostDialListener>();
    protected PostDialState mPostDialState = PostDialState.NOT_STARTED;
    protected String mPostDialString;
    public Call.State mPreHandoverState = Call.State.IDLE;
    private int mPulledDialogId;
    private String mTelecomCallId;
    Object mUserData;
    private Connection.VideoProvider mVideoProvider;
    private int mVideoState;

    @UnsupportedAppUsage
    protected Connection(int n) {
        this.mPhoneType = n;
    }

    public static int addCapability(int n, int n2) {
        return n | n2;
    }

    public static int removeCapability(int n, int n2) {
        return n2 & n;
    }

    public void addListener(Listener listener) {
        this.mListeners.add(listener);
    }

    public void addPostDialListener(PostDialListener postDialListener) {
        if (!this.mPostDialListeners.contains(postDialListener)) {
            this.mPostDialListeners.add(postDialListener);
        }
    }

    public abstract void cancelPostDial();

    protected final void clearPostDialListeners() {
        List<PostDialListener> list = this.mPostDialListeners;
        if (list != null) {
            list.clear();
        }
    }

    public void clearUserData() {
        this.mUserData = null;
    }

    public abstract void deflect(String var1) throws CallStateException;

    @UnsupportedAppUsage
    public String getAddress() {
        return this.mAddress;
    }

    public boolean getAudioModeIsVoip() {
        return this.mAudioModeIsVoip;
    }

    public int getAudioQuality() {
        return this.mAudioQuality;
    }

    @UnsupportedAppUsage
    public abstract Call getCall();

    public int getCallRadioTech() {
        return this.mCallRadioTech;
    }

    public int getCallSubstate() {
        return this.mCallSubstate;
    }

    public String getCnapName() {
        return this.mCnapName;
    }

    public int getCnapNamePresentation() {
        return this.mCnapNamePresentation;
    }

    public List<ConferenceParticipant> getConferenceParticipants() {
        Call call = this.getCall();
        if (call == null) {
            return null;
        }
        return call.getConferenceParticipants();
    }

    @UnsupportedAppUsage
    public long getConnectTime() {
        return this.mConnectTime;
    }

    public long getConnectTimeReal() {
        return this.mConnectTimeReal;
    }

    public int getConnectionCapabilities() {
        return this.mConnectionCapabilities;
    }

    public Bundle getConnectionExtras() {
        Object object = this.mExtras;
        object = object == null ? null : new Bundle(object);
        return object;
    }

    @UnsupportedAppUsage
    public long getCreateTime() {
        return this.mCreateTime;
    }

    @UnsupportedAppUsage
    public int getDisconnectCause() {
        return this.mCause;
    }

    @UnsupportedAppUsage
    public abstract long getDisconnectTime();

    @UnsupportedAppUsage
    public long getDurationMillis() {
        if (this.mConnectTimeReal == 0L) {
            return 0L;
        }
        long l = this.mDuration;
        if (l == 0L) {
            return SystemClock.elapsedRealtime() - this.mConnectTimeReal;
        }
        return l;
    }

    public EmergencyNumber getEmergencyNumberInfo() {
        return this.mEmergencyNumberInfo;
    }

    public abstract long getHoldDurationMillis();

    public long getHoldingStartTime() {
        return this.mHoldingStartTime;
    }

    public abstract int getNumberPresentation();

    public Connection getOrigConnection() {
        return this.mOrigConnection;
    }

    public String getOrigDialString() {
        return null;
    }

    public int getPhoneType() {
        return this.mPhoneType;
    }

    public PostDialState getPostDialState() {
        return this.mPostDialState;
    }

    public abstract int getPreciseDisconnectCause();

    public int getPulledDialogId() {
        return this.mPulledDialogId;
    }

    public String getRemainingPostDialString() {
        String string;
        int n;
        int n2;
        if (this.mPostDialState != PostDialState.CANCELLED && this.mPostDialState != PostDialState.COMPLETE && (string = this.mPostDialString) != null && (n = string.length()) > (n2 = this.mNextPostDialChar)) {
            return this.mPostDialString.substring(n2);
        }
        return "";
    }

    @UnsupportedAppUsage
    public Call.State getState() {
        Call call = this.getCall();
        if (call == null) {
            return Call.State.IDLE;
        }
        return call.getState();
    }

    public Call.State getStateBeforeHandover() {
        return this.mPreHandoverState;
    }

    public String getTelecomCallId() {
        return this.mTelecomCallId;
    }

    public abstract UUSInfo getUUSInfo();

    @UnsupportedAppUsage
    public Object getUserData() {
        return this.mUserData;
    }

    public abstract String getVendorDisconnectCause();

    public Connection.VideoProvider getVideoProvider() {
        return this.mVideoProvider;
    }

    public int getVideoState() {
        return this.mVideoState;
    }

    @UnsupportedAppUsage
    public abstract void hangup() throws CallStateException;

    public boolean hasCapabilities(int n) {
        boolean bl = (this.mConnectionCapabilities & n) == n;
        return bl;
    }

    public boolean hasKnownUserIntentEmergency() {
        return this.mHasKnownUserIntentEmergency;
    }

    public boolean isActiveCallDisconnectedOnAnswer() {
        return this.mAnsweringDisconnectsActiveCall;
    }

    @UnsupportedAppUsage
    public boolean isAlive() {
        return this.getState().isAlive();
    }

    public boolean isConferenceHost() {
        return false;
    }

    public boolean isEmergencyCall() {
        return this.mIsEmergencyCall;
    }

    @UnsupportedAppUsage
    public boolean isIncoming() {
        return this.mIsIncoming;
    }

    public boolean isMemberOfPeerConference() {
        return false;
    }

    public abstract boolean isMultiparty();

    public boolean isNetworkIdentifiedEmergencyCall() {
        return this.mIsNetworkIdentifiedEmergencyCall;
    }

    public boolean isPulledCall() {
        return this.mIsPulledCall;
    }

    public boolean isRinging() {
        return this.getState().isRinging();
    }

    public boolean isWifi() {
        boolean bl = this.getCallRadioTech() == 18;
        return bl;
    }

    public void migrateFrom(Connection connection) {
        if (connection == null) {
            return;
        }
        this.mListeners = connection.mListeners;
        this.mDialString = connection.getOrigDialString();
        this.mCreateTime = connection.getCreateTime();
        this.mConnectTime = connection.getConnectTime();
        this.mConnectTimeReal = connection.getConnectTimeReal();
        this.mHoldingStartTime = connection.getHoldingStartTime();
        this.mOrigConnection = connection.getOrigConnection();
        this.mPostDialString = connection.mPostDialString;
        this.mNextPostDialChar = connection.mNextPostDialChar;
        this.mPostDialState = connection.mPostDialState;
        this.mIsEmergencyCall = connection.isEmergencyCall();
        this.mEmergencyNumberInfo = connection.getEmergencyNumberInfo();
        this.mHasKnownUserIntentEmergency = connection.hasKnownUserIntentEmergency();
    }

    protected void notifyDisconnect(int n) {
        Object object = new StringBuilder();
        ((StringBuilder)object).append("notifyDisconnect: callId=");
        ((StringBuilder)object).append(this.getTelecomCallId());
        ((StringBuilder)object).append(", reason=");
        ((StringBuilder)object).append(n);
        Rlog.i((String)"Connection", (String)((StringBuilder)object).toString());
        object = this.mListeners.iterator();
        while (object.hasNext()) {
            ((Listener)object.next()).onDisconnect(n);
        }
    }

    protected final void notifyPostDialListeners() {
        if (this.getPostDialState() == PostDialState.WAIT) {
            Iterator<PostDialListener> iterator = new ArrayList<PostDialListener>(this.mPostDialListeners).iterator();
            while (iterator.hasNext()) {
                iterator.next().onPostDialWait();
            }
        }
    }

    protected final void notifyPostDialListenersNextChar(char c) {
        Iterator<PostDialListener> iterator = new ArrayList<PostDialListener>(this.mPostDialListeners).iterator();
        while (iterator.hasNext()) {
            iterator.next().onPostDialChar(c);
        }
    }

    public void onCallPullFailed(Connection connection) {
        Iterator<Listener> iterator = this.mListeners.iterator();
        while (iterator.hasNext()) {
            iterator.next().onCallPullFailed(connection);
        }
    }

    public void onConferenceMergeFailed() {
        Iterator<Listener> iterator = this.mListeners.iterator();
        while (iterator.hasNext()) {
            iterator.next().onConferenceMergedFailed();
        }
    }

    public void onConnectionEvent(String string, Bundle bundle) {
        Iterator<Listener> iterator = this.mListeners.iterator();
        while (iterator.hasNext()) {
            iterator.next().onConnectionEvent(string, bundle);
        }
    }

    public boolean onDisconnect(int n) {
        return false;
    }

    public void onDisconnectConferenceParticipant(Uri uri) {
    }

    public void onExitedEcmMode() {
        Iterator<Listener> iterator = this.mListeners.iterator();
        while (iterator.hasNext()) {
            iterator.next().onExitedEcmMode();
        }
    }

    public void onHandoverToWifiFailed() {
        Iterator<Listener> iterator = this.mListeners.iterator();
        while (iterator.hasNext()) {
            iterator.next().onHandoverToWifiFailed();
        }
    }

    public void onOriginalConnectionReplaced(Connection connection) {
        Iterator<Listener> iterator = this.mListeners.iterator();
        while (iterator.hasNext()) {
            iterator.next().onOriginalConnectionReplaced(connection);
        }
    }

    public void onRttInitiated() {
        Iterator<Listener> iterator = this.mListeners.iterator();
        while (iterator.hasNext()) {
            iterator.next().onRttInitiated();
        }
    }

    public void onRttModifyRequestReceived() {
        Iterator<Listener> iterator = this.mListeners.iterator();
        while (iterator.hasNext()) {
            iterator.next().onRttModifyRequestReceived();
        }
    }

    public void onRttModifyResponseReceived(int n) {
        Iterator<Listener> iterator = this.mListeners.iterator();
        while (iterator.hasNext()) {
            iterator.next().onRttModifyResponseReceived(n);
        }
    }

    public void onRttTerminated() {
        Iterator<Listener> iterator = this.mListeners.iterator();
        while (iterator.hasNext()) {
            iterator.next().onRttTerminated();
        }
    }

    public abstract void proceedAfterWaitChar();

    public abstract void proceedAfterWildChar(String var1);

    public void pullExternalCall() {
    }

    public final void removeListener(Listener listener) {
        this.mListeners.remove(listener);
    }

    public final void removePostDialListener(PostDialListener postDialListener) {
        this.mPostDialListeners.remove(postDialListener);
    }

    public void resetConnectionTime() {
        int n = this.mPhoneType;
        if (n == 6 || n == 2) {
            this.mConnectTime = System.currentTimeMillis();
            this.mConnectTimeReal = SystemClock.elapsedRealtime();
            this.mDuration = 0L;
        }
    }

    public abstract void separate() throws CallStateException;

    public void setActiveCallDisconnectedOnAnswer(boolean bl) {
        this.mAnsweringDisconnectsActiveCall = bl;
    }

    public void setAddress(String string, int n) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("setAddress = ");
        stringBuilder.append(string);
        Rlog.i((String)"Connection", (String)stringBuilder.toString());
        this.mAddress = string;
        this.mNumberPresentation = n;
    }

    public void setAllowAddCallDuringVideoCall(boolean bl) {
        this.mAllowAddCallDuringVideoCall = bl;
    }

    public void setAudioModeIsVoip(boolean bl) {
        this.mAudioModeIsVoip = bl;
    }

    public void setAudioQuality(int n) {
        this.mAudioQuality = n;
        Iterator<Listener> iterator = this.mListeners.iterator();
        while (iterator.hasNext()) {
            iterator.next().onAudioQualityChanged(this.mAudioQuality);
        }
    }

    public void setCallRadioTech(int n) {
        if (this.mCallRadioTech == n) {
            return;
        }
        this.mCallRadioTech = n;
        Iterator<Listener> iterator = this.mListeners.iterator();
        while (iterator.hasNext()) {
            iterator.next().onCallRadioTechChanged(n);
        }
    }

    public void setCallSubstate(int n) {
        this.mCallSubstate = n;
        Iterator<Listener> iterator = this.mListeners.iterator();
        while (iterator.hasNext()) {
            iterator.next().onCallSubstateChanged(this.mCallSubstate);
        }
    }

    public void setConnectTime(long l) {
        this.mConnectTime = l;
    }

    public void setConnectTimeReal(long l) {
        this.mConnectTimeReal = l;
    }

    public void setConnectionCapabilities(int n) {
        if (this.mConnectionCapabilities != n) {
            this.mConnectionCapabilities = n;
            Iterator<Listener> iterator = this.mListeners.iterator();
            while (iterator.hasNext()) {
                iterator.next().onConnectionCapabilitiesChanged(this.mConnectionCapabilities);
            }
        }
    }

    public void setConnectionExtras(Bundle object) {
        if (object != null) {
            this.mExtras = new Bundle((Bundle)object);
            int n = this.mExtras.size();
            this.mExtras = this.mExtras.filterValues();
            int n2 = this.mExtras.size();
            if (n2 != n) {
                object = new StringBuilder();
                ((StringBuilder)object).append("setConnectionExtras: filtering ");
                ((StringBuilder)object).append(n - n2);
                ((StringBuilder)object).append(" invalid extras.");
                Rlog.i((String)"Connection", (String)((StringBuilder)object).toString());
            }
        } else {
            this.mExtras = null;
        }
        object = this.mListeners.iterator();
        while (object.hasNext()) {
            ((Listener)object.next()).onExtrasChanged(this.mExtras);
        }
    }

    public void setConverted(String string) {
        this.mNumberConverted = true;
        this.mConvertedNumber = this.mAddress;
        this.mAddress = string;
        this.mDialString = string;
    }

    public void setDialString(String string) {
        this.mDialString = string;
    }

    public void setEmergencyCallInfo(CallTracker handler) {
        if (handler != null) {
            if ((handler = handler.getPhone()) != null) {
                if ((handler = handler.getEmergencyNumberTracker()) != null) {
                    if ((handler = handler.getEmergencyNumber(this.mAddress)) != null) {
                        this.mIsEmergencyCall = true;
                        this.mEmergencyNumberInfo = handler;
                    } else {
                        Rlog.e((String)"Connection", (String)"setEmergencyCallInfo: emergency number is null");
                    }
                } else {
                    Rlog.e((String)"Connection", (String)"setEmergencyCallInfo: emergency number tracker is null");
                }
            } else {
                Rlog.e((String)"Connection", (String)"setEmergencyCallInfo: phone is null");
            }
        } else {
            Rlog.e((String)"Connection", (String)"setEmergencyCallInfo: call tracker is null");
        }
    }

    public void setHasKnownUserIntentEmergency(boolean bl) {
        this.mHasKnownUserIntentEmergency = bl;
    }

    public void setIsIncoming(boolean bl) {
        this.mIsIncoming = bl;
    }

    public void setIsNetworkIdentifiedEmergencyCall(boolean bl) {
        this.mIsNetworkIdentifiedEmergencyCall = bl;
        Iterator<Listener> iterator = this.mListeners.iterator();
        while (iterator.hasNext()) {
            iterator.next().onIsNetworkEmergencyCallChanged(bl);
        }
    }

    public void setIsPulledCall(boolean bl) {
        this.mIsPulledCall = bl;
    }

    public void setPulledDialogId(int n) {
        this.mPulledDialogId = n;
    }

    public void setTelecomCallId(String string) {
        this.mTelecomCallId = string;
    }

    public void setUserData(Object object) {
        this.mUserData = object;
    }

    public void setVideoProvider(Connection.VideoProvider object) {
        this.mVideoProvider = object;
        object = this.mListeners.iterator();
        while (object.hasNext()) {
            ((Listener)object.next()).onVideoProviderChanged(this.mVideoProvider);
        }
    }

    @UnsupportedAppUsage
    public void setVideoState(int n) {
        this.mVideoState = n;
        Iterator<Listener> iterator = this.mListeners.iterator();
        while (iterator.hasNext()) {
            iterator.next().onVideoStateChanged(this.mVideoState);
        }
    }

    public boolean shouldAllowAddCallDuringVideoCall() {
        return this.mAllowAddCallDuringVideoCall;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder(128);
        CharSequence charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append(" callId: ");
        ((StringBuilder)charSequence).append(this.getTelecomCallId());
        stringBuilder.append(((StringBuilder)charSequence).toString());
        StringBuilder stringBuilder2 = new StringBuilder();
        stringBuilder2.append(" isExternal: ");
        charSequence = (this.mConnectionCapabilities & 16) == 16 ? "Y" : "N";
        stringBuilder2.append((String)charSequence);
        stringBuilder.append(stringBuilder2.toString());
        if (Rlog.isLoggable((String)LOG_TAG, (int)3)) {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("addr: ");
            ((StringBuilder)charSequence).append(this.getAddress());
            stringBuilder.append(((StringBuilder)charSequence).toString());
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append(" pres.: ");
            ((StringBuilder)charSequence).append(this.getNumberPresentation());
            stringBuilder.append(((StringBuilder)charSequence).toString());
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append(" dial: ");
            ((StringBuilder)charSequence).append(this.getOrigDialString());
            stringBuilder.append(((StringBuilder)charSequence).toString());
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append(" postdial: ");
            ((StringBuilder)charSequence).append(this.getRemainingPostDialString());
            stringBuilder.append(((StringBuilder)charSequence).toString());
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append(" cnap name: ");
            ((StringBuilder)charSequence).append(this.getCnapName());
            stringBuilder.append(((StringBuilder)charSequence).toString());
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("(");
            ((StringBuilder)charSequence).append(this.getCnapNamePresentation());
            ((StringBuilder)charSequence).append(")");
            stringBuilder.append(((StringBuilder)charSequence).toString());
        }
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append(" incoming: ");
        ((StringBuilder)charSequence).append(this.isIncoming());
        stringBuilder.append(((StringBuilder)charSequence).toString());
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append(" state: ");
        ((StringBuilder)charSequence).append((Object)((Object)this.getState()));
        stringBuilder.append(((StringBuilder)charSequence).toString());
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append(" post dial state: ");
        ((StringBuilder)charSequence).append((Object)((Object)this.getPostDialState()));
        stringBuilder.append(((StringBuilder)charSequence).toString());
        return stringBuilder.toString();
    }

    public void updateConferenceParticipants(List<ConferenceParticipant> list) {
        Iterator<Listener> iterator = this.mListeners.iterator();
        while (iterator.hasNext()) {
            iterator.next().onConferenceParticipantsChanged(list);
        }
    }

    public void updateMultipartyState(boolean bl) {
        Iterator<Listener> iterator = this.mListeners.iterator();
        while (iterator.hasNext()) {
            iterator.next().onMultipartyStateChanged(bl);
        }
    }

    public static class Capability {
        public static final int IS_EXTERNAL_CONNECTION = 16;
        public static final int IS_PULLABLE = 32;
        public static final int SUPPORTS_DOWNGRADE_TO_VOICE_LOCAL = 1;
        public static final int SUPPORTS_DOWNGRADE_TO_VOICE_REMOTE = 2;
        public static final int SUPPORTS_VT_LOCAL_BIDIRECTIONAL = 4;
        public static final int SUPPORTS_VT_REMOTE_BIDIRECTIONAL = 8;
    }

    public static interface Listener {
        public void onAudioQualityChanged(int var1);

        public void onCallPullFailed(Connection var1);

        public void onCallRadioTechChanged(int var1);

        public void onCallSubstateChanged(int var1);

        public void onConferenceMergedFailed();

        public void onConferenceParticipantsChanged(List<ConferenceParticipant> var1);

        public void onConnectionCapabilitiesChanged(int var1);

        public void onConnectionEvent(String var1, Bundle var2);

        public void onDisconnect(int var1);

        public void onExitedEcmMode();

        public void onExtrasChanged(Bundle var1);

        public void onHandoverToWifiFailed();

        public void onIsNetworkEmergencyCallChanged(boolean var1);

        public void onMultipartyStateChanged(boolean var1);

        public void onOriginalConnectionReplaced(Connection var1);

        public void onRttInitiated();

        public void onRttModifyRequestReceived();

        public void onRttModifyResponseReceived(int var1);

        public void onRttTerminated();

        public void onVideoProviderChanged(Connection.VideoProvider var1);

        public void onVideoStateChanged(int var1);
    }

    public static abstract class ListenerBase
    implements Listener {
        @Override
        public void onAudioQualityChanged(int n) {
        }

        @Override
        public void onCallPullFailed(Connection connection) {
        }

        @Override
        public void onCallRadioTechChanged(int n) {
        }

        @Override
        public void onCallSubstateChanged(int n) {
        }

        @Override
        public void onConferenceMergedFailed() {
        }

        @Override
        public void onConferenceParticipantsChanged(List<ConferenceParticipant> list) {
        }

        @Override
        public void onConnectionCapabilitiesChanged(int n) {
        }

        @Override
        public void onConnectionEvent(String string, Bundle bundle) {
        }

        @Override
        public void onDisconnect(int n) {
        }

        @Override
        public void onExitedEcmMode() {
        }

        @Override
        public void onExtrasChanged(Bundle bundle) {
        }

        @Override
        public void onHandoverToWifiFailed() {
        }

        @Override
        public void onIsNetworkEmergencyCallChanged(boolean bl) {
        }

        @Override
        public void onMultipartyStateChanged(boolean bl) {
        }

        @Override
        public void onOriginalConnectionReplaced(Connection connection) {
        }

        @Override
        public void onRttInitiated() {
        }

        @Override
        public void onRttModifyRequestReceived() {
        }

        @Override
        public void onRttModifyResponseReceived(int n) {
        }

        @Override
        public void onRttTerminated() {
        }

        @Override
        public void onVideoProviderChanged(Connection.VideoProvider videoProvider) {
        }

        @Override
        public void onVideoStateChanged(int n) {
        }
    }

    public static interface PostDialListener {
        public void onPostDialChar(char var1);

        public void onPostDialWait();
    }

    public static enum PostDialState {
        NOT_STARTED,
        STARTED,
        WAIT,
        WILD,
        COMPLETE,
        CANCELLED,
        PAUSE;
        
    }

}

