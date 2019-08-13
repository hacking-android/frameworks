/*
 * Decompiled with CFR 0.145.
 */
package android.telecom;

import android.annotation.SystemApi;
import android.annotation.UnsupportedAppUsage;
import android.bluetooth.BluetoothDevice;
import android.net.Uri;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.ParcelFileDescriptor;
import android.os.RemoteException;
import android.telecom.AudioState;
import android.telecom.CallAudioState;
import android.telecom.Conference;
import android.telecom.ConferenceParticipant;
import android.telecom.Conferenceable;
import android.telecom.ConnectionService;
import android.telecom.DisconnectCause;
import android.telecom.Log;
import android.telecom.PhoneAccountHandle;
import android.telecom.StatusHints;
import android.telecom.VideoProfile;
import android.telecom._$$Lambda$Connection$8xeoCKtoHEwnDqv6gbuSfOMODH0;
import android.telecom._$$Lambda$Connection$SYsjtKchY2AYvOeGveCrqxSfKTU;
import android.telecom._$$Lambda$Connection$lnfFNF0t9fPLEf01JE291g4chSk;
import android.telecom._$$Lambda$Connection$noXZvls4rxmO_SOjgkFMZLLrfSg;
import android.telephony.ServiceState;
import android.util.ArraySet;
import android.view.Surface;
import com.android.internal.os.SomeArgs;
import com.android.internal.telecom.IVideoCallback;
import com.android.internal.telecom.IVideoProvider;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.channels.Channels;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

public abstract class Connection
extends Conferenceable {
    public static final int CAPABILITY_CANNOT_DOWNGRADE_VIDEO_TO_AUDIO = 8388608;
    public static final int CAPABILITY_CAN_PAUSE_VIDEO = 1048576;
    public static final int CAPABILITY_CAN_PULL_CALL = 16777216;
    public static final int CAPABILITY_CAN_SEND_RESPONSE_VIA_CONNECTION = 4194304;
    public static final int CAPABILITY_CAN_UPGRADE_TO_VIDEO = 524288;
    public static final int CAPABILITY_CONFERENCE_HAS_NO_CHILDREN = 2097152;
    public static final int CAPABILITY_DISCONNECT_FROM_CONFERENCE = 8192;
    public static final int CAPABILITY_HOLD = 1;
    public static final int CAPABILITY_MANAGE_CONFERENCE = 128;
    public static final int CAPABILITY_MERGE_CONFERENCE = 4;
    public static final int CAPABILITY_MUTE = 64;
    public static final int CAPABILITY_RESPOND_VIA_TEXT = 32;
    public static final int CAPABILITY_SEPARATE_FROM_CONFERENCE = 4096;
    public static final int CAPABILITY_SPEED_UP_MT_AUDIO = 262144;
    public static final int CAPABILITY_SUPPORTS_VT_LOCAL_BIDIRECTIONAL = 768;
    public static final int CAPABILITY_SUPPORTS_VT_LOCAL_RX = 256;
    public static final int CAPABILITY_SUPPORTS_VT_LOCAL_TX = 512;
    public static final int CAPABILITY_SUPPORTS_VT_REMOTE_BIDIRECTIONAL = 3072;
    public static final int CAPABILITY_SUPPORTS_VT_REMOTE_RX = 1024;
    public static final int CAPABILITY_SUPPORTS_VT_REMOTE_TX = 2048;
    public static final int CAPABILITY_SUPPORT_DEFLECT = 33554432;
    public static final int CAPABILITY_SUPPORT_HOLD = 2;
    public static final int CAPABILITY_SWAP_CONFERENCE = 8;
    public static final int CAPABILITY_UNUSED = 16;
    public static final int CAPABILITY_UNUSED_2 = 16384;
    public static final int CAPABILITY_UNUSED_3 = 32768;
    public static final int CAPABILITY_UNUSED_4 = 65536;
    public static final int CAPABILITY_UNUSED_5 = 131072;
    public static final String EVENT_CALL_HOLD_FAILED = "android.telecom.event.CALL_HOLD_FAILED";
    public static final String EVENT_CALL_MERGE_FAILED = "android.telecom.event.CALL_MERGE_FAILED";
    public static final String EVENT_CALL_PULL_FAILED = "android.telecom.event.CALL_PULL_FAILED";
    public static final String EVENT_CALL_REMOTELY_HELD = "android.telecom.event.CALL_REMOTELY_HELD";
    public static final String EVENT_CALL_REMOTELY_UNHELD = "android.telecom.event.CALL_REMOTELY_UNHELD";
    public static final String EVENT_HANDOVER_COMPLETE = "android.telecom.event.HANDOVER_COMPLETE";
    public static final String EVENT_HANDOVER_FAILED = "android.telecom.event.HANDOVER_FAILED";
    public static final String EVENT_MERGE_COMPLETE = "android.telecom.event.MERGE_COMPLETE";
    public static final String EVENT_MERGE_START = "android.telecom.event.MERGE_START";
    public static final String EVENT_ON_HOLD_TONE_END = "android.telecom.event.ON_HOLD_TONE_END";
    public static final String EVENT_ON_HOLD_TONE_START = "android.telecom.event.ON_HOLD_TONE_START";
    public static final String EVENT_RTT_AUDIO_INDICATION_CHANGED = "android.telecom.event.RTT_AUDIO_INDICATION_CHANGED";
    public static final String EXTRA_ANSWERING_DROPS_FG_CALL = "android.telecom.extra.ANSWERING_DROPS_FG_CALL";
    public static final String EXTRA_ANSWERING_DROPS_FG_CALL_APP_NAME = "android.telecom.extra.ANSWERING_DROPS_FG_CALL_APP_NAME";
    public static final String EXTRA_CALL_SUBJECT = "android.telecom.extra.CALL_SUBJECT";
    public static final String EXTRA_CHILD_ADDRESS = "android.telecom.extra.CHILD_ADDRESS";
    public static final String EXTRA_DISABLE_ADD_CALL = "android.telecom.extra.DISABLE_ADD_CALL";
    public static final String EXTRA_IS_RTT_AUDIO_PRESENT = "android.telecom.extra.IS_RTT_AUDIO_PRESENT";
    public static final String EXTRA_LAST_FORWARDED_NUMBER = "android.telecom.extra.LAST_FORWARDED_NUMBER";
    public static final String EXTRA_ORIGINAL_CONNECTION_ID = "android.telecom.extra.ORIGINAL_CONNECTION_ID";
    public static final String EXTRA_SIP_INVITE = "android.telecom.extra.SIP_INVITE";
    private static final boolean PII_DEBUG = Log.isLoggable(3);
    public static final int PROPERTY_ASSISTED_DIALING_USED = 512;
    public static final int PROPERTY_EMERGENCY_CALLBACK_MODE = 1;
    public static final int PROPERTY_GENERIC_CONFERENCE = 2;
    public static final int PROPERTY_HAS_CDMA_VOICE_PRIVACY = 32;
    public static final int PROPERTY_HIGH_DEF_AUDIO = 4;
    public static final int PROPERTY_IS_DOWNGRADED_CONFERENCE = 64;
    public static final int PROPERTY_IS_EXTERNAL_CALL = 16;
    public static final int PROPERTY_IS_RTT = 256;
    public static final int PROPERTY_NETWORK_IDENTIFIED_EMERGENCY_CALL = 1024;
    public static final int PROPERTY_REMOTELY_HOSTED = 2048;
    public static final int PROPERTY_SELF_MANAGED = 128;
    public static final int PROPERTY_WIFI = 8;
    public static final int STATE_ACTIVE = 4;
    public static final int STATE_DIALING = 3;
    public static final int STATE_DISCONNECTED = 6;
    public static final int STATE_HOLDING = 5;
    public static final int STATE_INITIALIZING = 0;
    public static final int STATE_NEW = 1;
    public static final int STATE_PULLING_CALL = 7;
    public static final int STATE_RINGING = 2;
    private Uri mAddress;
    private int mAddressPresentation;
    private boolean mAudioModeIsVoip;
    private CallAudioState mCallAudioState;
    private int mCallDirection = -1;
    private String mCallerDisplayName;
    private int mCallerDisplayNamePresentation;
    private Conference mConference;
    private final Conference.Listener mConferenceDeathListener = new Conference.Listener(){

        @Override
        public void onDestroyed(Conference conference) {
            if (Connection.this.mConferenceables.remove(conference)) {
                Connection.this.fireOnConferenceableConnectionsChanged();
            }
        }
    };
    private final List<Conferenceable> mConferenceables = new ArrayList<Conferenceable>();
    private long mConnectElapsedTimeMillis = 0L;
    private long mConnectTimeMillis = 0L;
    private int mConnectionCapabilities;
    private final Listener mConnectionDeathListener = new Listener(){

        @Override
        public void onDestroyed(Connection connection) {
            if (Connection.this.mConferenceables.remove(connection)) {
                Connection.this.fireOnConferenceableConnectionsChanged();
            }
        }
    };
    private int mConnectionProperties;
    private ConnectionService mConnectionService;
    private DisconnectCause mDisconnectCause;
    private Bundle mExtras;
    private final Object mExtrasLock = new Object();
    private final Set<Listener> mListeners = Collections.newSetFromMap(new ConcurrentHashMap(8, 0.9f, 1));
    private PhoneAccountHandle mPhoneAccountHandle;
    private Set<String> mPreviousExtraKeys;
    private boolean mRingbackRequested = false;
    private int mState = 1;
    private StatusHints mStatusHints;
    private int mSupportedAudioRoutes = 15;
    private String mTelecomCallId;
    private final List<Conferenceable> mUnmodifiableConferenceables = Collections.unmodifiableList(this.mConferenceables);
    private VideoProvider mVideoProvider;
    private int mVideoState;

    public static boolean can(int n, int n2) {
        boolean bl = (n & n2) == n2;
        return bl;
    }

    public static String capabilitiesToString(int n) {
        return Connection.capabilitiesToStringInternal(n, true);
    }

    private static String capabilitiesToStringInternal(int n, boolean bl) {
        String string2;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[");
        if (bl) {
            stringBuilder.append("Capabilities:");
        }
        if (Connection.can(n, 1)) {
            string2 = bl ? " CAPABILITY_HOLD" : " hld";
            stringBuilder.append(string2);
        }
        if (Connection.can(n, 2)) {
            string2 = bl ? " CAPABILITY_SUPPORT_HOLD" : " sup_hld";
            stringBuilder.append(string2);
        }
        if (Connection.can(n, 4)) {
            string2 = bl ? " CAPABILITY_MERGE_CONFERENCE" : " mrg_cnf";
            stringBuilder.append(string2);
        }
        if (Connection.can(n, 8)) {
            string2 = bl ? " CAPABILITY_SWAP_CONFERENCE" : " swp_cnf";
            stringBuilder.append(string2);
        }
        if (Connection.can(n, 32)) {
            string2 = bl ? " CAPABILITY_RESPOND_VIA_TEXT" : " txt";
            stringBuilder.append(string2);
        }
        if (Connection.can(n, 64)) {
            string2 = bl ? " CAPABILITY_MUTE" : " mut";
            stringBuilder.append(string2);
        }
        if (Connection.can(n, 128)) {
            string2 = bl ? " CAPABILITY_MANAGE_CONFERENCE" : " mng_cnf";
            stringBuilder.append(string2);
        }
        if (Connection.can(n, 256)) {
            string2 = bl ? " CAPABILITY_SUPPORTS_VT_LOCAL_RX" : " VTlrx";
            stringBuilder.append(string2);
        }
        if (Connection.can(n, 512)) {
            string2 = bl ? " CAPABILITY_SUPPORTS_VT_LOCAL_TX" : " VTltx";
            stringBuilder.append(string2);
        }
        if (Connection.can(n, 768)) {
            string2 = bl ? " CAPABILITY_SUPPORTS_VT_LOCAL_BIDIRECTIONAL" : " VTlbi";
            stringBuilder.append(string2);
        }
        if (Connection.can(n, 1024)) {
            string2 = bl ? " CAPABILITY_SUPPORTS_VT_REMOTE_RX" : " VTrrx";
            stringBuilder.append(string2);
        }
        if (Connection.can(n, 2048)) {
            string2 = bl ? " CAPABILITY_SUPPORTS_VT_REMOTE_TX" : " VTrtx";
            stringBuilder.append(string2);
        }
        if (Connection.can(n, 3072)) {
            string2 = bl ? " CAPABILITY_SUPPORTS_VT_REMOTE_BIDIRECTIONAL" : " VTrbi";
            stringBuilder.append(string2);
        }
        if (Connection.can(n, 8388608)) {
            string2 = bl ? " CAPABILITY_CANNOT_DOWNGRADE_VIDEO_TO_AUDIO" : " !v2a";
            stringBuilder.append(string2);
        }
        if (Connection.can(n, 262144)) {
            string2 = bl ? " CAPABILITY_SPEED_UP_MT_AUDIO" : " spd_aud";
            stringBuilder.append(string2);
        }
        if (Connection.can(n, 524288)) {
            string2 = bl ? " CAPABILITY_CAN_UPGRADE_TO_VIDEO" : " a2v";
            stringBuilder.append(string2);
        }
        if (Connection.can(n, 1048576)) {
            string2 = bl ? " CAPABILITY_CAN_PAUSE_VIDEO" : " paus_VT";
            stringBuilder.append(string2);
        }
        if (Connection.can(n, 2097152)) {
            string2 = bl ? " CAPABILITY_SINGLE_PARTY_CONFERENCE" : " 1p_cnf";
            stringBuilder.append(string2);
        }
        if (Connection.can(n, 4194304)) {
            string2 = bl ? " CAPABILITY_CAN_SEND_RESPONSE_VIA_CONNECTION" : " rsp_by_con";
            stringBuilder.append(string2);
        }
        if (Connection.can(n, 16777216)) {
            string2 = bl ? " CAPABILITY_CAN_PULL_CALL" : " pull";
            stringBuilder.append(string2);
        }
        if (Connection.can(n, 33554432)) {
            string2 = bl ? " CAPABILITY_SUPPORT_DEFLECT" : " sup_def";
            stringBuilder.append(string2);
        }
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    public static String capabilitiesToStringShort(int n) {
        return Connection.capabilitiesToStringInternal(n, false);
    }

    private final void clearConferenceableList() {
        for (Conferenceable conferenceable : this.mConferenceables) {
            if (conferenceable instanceof Connection) {
                ((Connection)conferenceable).removeConnectionListener(this.mConnectionDeathListener);
                continue;
            }
            if (!(conferenceable instanceof Conference)) continue;
            ((Conference)conferenceable).removeListener(this.mConferenceDeathListener);
        }
        this.mConferenceables.clear();
    }

    public static Connection createCanceledConnection() {
        return new FailureSignalingConnection(new DisconnectCause(4));
    }

    public static Connection createFailedConnection(DisconnectCause disconnectCause) {
        return new FailureSignalingConnection(disconnectCause);
    }

    private final void fireConferenceChanged() {
        Iterator<Listener> iterator = this.mListeners.iterator();
        while (iterator.hasNext()) {
            iterator.next().onConferenceChanged(this, this.mConference);
        }
    }

    private final void fireOnConferenceableConnectionsChanged() {
        Iterator<Listener> iterator = this.mListeners.iterator();
        while (iterator.hasNext()) {
            iterator.next().onConferenceablesChanged(this, this.getConferenceables());
        }
    }

    public static String propertiesToString(int n) {
        return Connection.propertiesToStringInternal(n, true);
    }

    private static String propertiesToStringInternal(int n, boolean bl) {
        String string2;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[");
        if (bl) {
            stringBuilder.append("Properties:");
        }
        if (Connection.can(n, 128)) {
            string2 = bl ? " PROPERTY_SELF_MANAGED" : " self_mng";
            stringBuilder.append(string2);
        }
        if (Connection.can(n, 1)) {
            string2 = bl ? " PROPERTY_EMERGENCY_CALLBACK_MODE" : " ecbm";
            stringBuilder.append(string2);
        }
        if (Connection.can(n, 4)) {
            string2 = bl ? " PROPERTY_HIGH_DEF_AUDIO" : " HD";
            stringBuilder.append(string2);
        }
        if (Connection.can(n, 8)) {
            string2 = bl ? " PROPERTY_WIFI" : " wifi";
            stringBuilder.append(string2);
        }
        if (Connection.can(n, 2)) {
            string2 = bl ? " PROPERTY_GENERIC_CONFERENCE" : " gen_conf";
            stringBuilder.append(string2);
        }
        if (Connection.can(n, 16)) {
            string2 = bl ? " PROPERTY_IS_EXTERNAL_CALL" : " xtrnl";
            stringBuilder.append(string2);
        }
        if (Connection.can(n, 32)) {
            string2 = bl ? " PROPERTY_HAS_CDMA_VOICE_PRIVACY" : " priv";
            stringBuilder.append(string2);
        }
        if (Connection.can(n, 256)) {
            string2 = bl ? " PROPERTY_IS_RTT" : " rtt";
            stringBuilder.append(string2);
        }
        if (Connection.can(n, 1024)) {
            string2 = bl ? " PROPERTY_NETWORK_IDENTIFIED_EMERGENCY_CALL" : " ecall";
            stringBuilder.append(string2);
        }
        if (Connection.can(n, 2048)) {
            string2 = bl ? " PROPERTY_REMOTELY_HOSTED" : " remote_hst";
            stringBuilder.append(string2);
        }
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    public static String propertiesToStringShort(int n) {
        return Connection.propertiesToStringInternal(n, false);
    }

    private void setState(int n) {
        this.checkImmutable();
        int n2 = this.mState;
        if (n2 == 6 && n2 != n) {
            Log.d(this, "Connection already DISCONNECTED; cannot transition out of this state.", new Object[0]);
            return;
        }
        if (this.mState != n) {
            Log.d(this, "setState: %s", Connection.stateToString(n));
            this.mState = n;
            this.onStateChanged(n);
            Iterator<Listener> iterator = this.mListeners.iterator();
            while (iterator.hasNext()) {
                iterator.next().onStateChanged(this, n);
            }
        }
    }

    public static String stateToString(int n) {
        switch (n) {
            default: {
                Log.wtf(Connection.class, "Unknown state %d", n);
                return "UNKNOWN";
            }
            case 7: {
                return "PULLING_CALL";
            }
            case 6: {
                return "DISCONNECTED";
            }
            case 5: {
                return "HOLDING";
            }
            case 4: {
                return "ACTIVE";
            }
            case 3: {
                return "DIALING";
            }
            case 2: {
                return "RINGING";
            }
            case 1: {
                return "NEW";
            }
            case 0: 
        }
        return "INITIALIZING";
    }

    static String toLogSafePhoneNumber(String string2) {
        if (string2 == null) {
            return "";
        }
        if (PII_DEBUG) {
            return string2;
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < string2.length(); ++i) {
            char c = string2.charAt(i);
            if (c != '-' && c != '@' && c != '.') {
                stringBuilder.append('x');
                continue;
            }
            stringBuilder.append(c);
        }
        return stringBuilder.toString();
    }

    public void addCapability(int n) {
        this.mConnectionCapabilities |= n;
    }

    public final Connection addConnectionListener(Listener listener) {
        this.mListeners.add(listener);
        return this;
    }

    public boolean can(int n) {
        return Connection.can(this.mConnectionCapabilities, n);
    }

    public void checkImmutable() {
    }

    public final void destroy() {
        Iterator<Listener> iterator = this.mListeners.iterator();
        while (iterator.hasNext()) {
            iterator.next().onDestroyed(this);
        }
    }

    public final Uri getAddress() {
        return this.mAddress;
    }

    public final int getAddressPresentation() {
        return this.mAddressPresentation;
    }

    public final boolean getAudioModeIsVoip() {
        return this.mAudioModeIsVoip;
    }

    @SystemApi
    @Deprecated
    public final AudioState getAudioState() {
        CallAudioState callAudioState = this.mCallAudioState;
        if (callAudioState == null) {
            return null;
        }
        return new AudioState(callAudioState);
    }

    public final CallAudioState getCallAudioState() {
        return this.mCallAudioState;
    }

    public final int getCallDirection() {
        return this.mCallDirection;
    }

    public final int getCallRadioTech() {
        int n = 0;
        Bundle bundle = this.getExtras();
        if (bundle != null) {
            n = bundle.getInt("android.telecom.extra.CALL_NETWORK_TYPE", 0);
        }
        return ServiceState.networkTypeToRilRadioTechnology(n);
    }

    public final String getCallerDisplayName() {
        return this.mCallerDisplayName;
    }

    public final int getCallerDisplayNamePresentation() {
        return this.mCallerDisplayNamePresentation;
    }

    public final Conference getConference() {
        return this.mConference;
    }

    public final List<Conferenceable> getConferenceables() {
        return this.mUnmodifiableConferenceables;
    }

    public final long getConnectElapsedTimeMillis() {
        return this.mConnectElapsedTimeMillis;
    }

    public final long getConnectTimeMillis() {
        return this.mConnectTimeMillis;
    }

    public final int getConnectionCapabilities() {
        return this.mConnectionCapabilities;
    }

    public final int getConnectionProperties() {
        return this.mConnectionProperties;
    }

    public final ConnectionService getConnectionService() {
        return this.mConnectionService;
    }

    public final DisconnectCause getDisconnectCause() {
        return this.mDisconnectCause;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public final Bundle getExtras() {
        Bundle bundle = null;
        Object object = this.mExtrasLock;
        synchronized (object) {
            if (this.mExtras == null) return bundle;
            return new Bundle(this.mExtras);
        }
    }

    public PhoneAccountHandle getPhoneAccountHandle() {
        return this.mPhoneAccountHandle;
    }

    public final int getState() {
        return this.mState;
    }

    public final StatusHints getStatusHints() {
        return this.mStatusHints;
    }

    public final int getSupportedAudioRoutes() {
        return this.mSupportedAudioRoutes;
    }

    public final String getTelecomCallId() {
        return this.mTelecomCallId;
    }

    public final VideoProvider getVideoProvider() {
        return this.mVideoProvider;
    }

    public final int getVideoState() {
        return this.mVideoState;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    final void handleExtrasChanged(Bundle bundle) {
        Object var2_2 = null;
        Object object = this.mExtrasLock;
        synchronized (object) {
            this.mExtras = bundle;
            bundle = var2_2;
            if (this.mExtras != null) {
                bundle = new Bundle(this.mExtras);
            }
        }
        this.onExtrasChanged(bundle);
    }

    public void handleRttUpgradeResponse(RttTextStream rttTextStream) {
    }

    public final boolean isRingbackRequested() {
        return this.mRingbackRequested;
    }

    public /* synthetic */ void lambda$sendRemoteRttRequest$3$Connection(Listener listener) {
        listener.onRemoteRttRequest(this);
    }

    public /* synthetic */ void lambda$sendRttInitiationFailure$1$Connection(int n, Listener listener) {
        listener.onRttInitiationFailure(this, n);
    }

    public /* synthetic */ void lambda$sendRttInitiationSuccess$0$Connection(Listener listener) {
        listener.onRttInitiationSuccess(this);
    }

    public /* synthetic */ void lambda$sendRttSessionRemotelyTerminated$2$Connection(Listener listener) {
        listener.onRttSessionRemotelyTerminated(this);
    }

    protected final void notifyConferenceMergeFailed() {
        Iterator<Listener> iterator = this.mListeners.iterator();
        while (iterator.hasNext()) {
            iterator.next().onConferenceMergeFailed(this);
        }
    }

    protected void notifyConferenceStarted() {
        Iterator<Listener> iterator = this.mListeners.iterator();
        while (iterator.hasNext()) {
            iterator.next().onConferenceStarted();
        }
    }

    protected void notifyConferenceSupportedChanged(boolean bl) {
        Iterator<Listener> iterator = this.mListeners.iterator();
        while (iterator.hasNext()) {
            iterator.next().onConferenceSupportedChanged(this, bl);
        }
    }

    public void notifyPhoneAccountChanged(PhoneAccountHandle phoneAccountHandle) {
        Iterator<Listener> iterator = this.mListeners.iterator();
        while (iterator.hasNext()) {
            iterator.next().onPhoneAccountChanged(this, phoneAccountHandle);
        }
    }

    public void onAbort() {
    }

    public void onAnswer() {
        this.onAnswer(0);
    }

    public void onAnswer(int n) {
    }

    @SystemApi
    @Deprecated
    public void onAudioStateChanged(AudioState audioState) {
    }

    public void onCallAudioStateChanged(CallAudioState callAudioState) {
    }

    public void onCallEvent(String string2, Bundle bundle) {
    }

    public void onDeflect(Uri uri) {
    }

    public void onDisconnect() {
    }

    public void onDisconnectConferenceParticipant(Uri uri) {
    }

    public void onExtrasChanged(Bundle bundle) {
    }

    public void onHandoverComplete() {
    }

    public void onHold() {
    }

    public void onPlayDtmfTone(char c) {
    }

    public void onPostDialContinue(boolean bl) {
    }

    public void onPullExternalCall() {
    }

    public void onReject() {
    }

    public void onReject(String string2) {
    }

    public void onSeparate() {
    }

    public void onShowIncomingCallUi() {
    }

    public void onSilence() {
    }

    public void onStartRtt(RttTextStream rttTextStream) {
    }

    public void onStateChanged(int n) {
    }

    public void onStopDtmfTone() {
    }

    public void onStopRtt() {
    }

    public void onUnhold() {
    }

    public final void putExtra(String string2, int n) {
        Bundle bundle = new Bundle();
        bundle.putInt(string2, n);
        this.putExtras(bundle);
    }

    public final void putExtra(String string2, String string3) {
        Bundle bundle = new Bundle();
        bundle.putString(string2, string3);
        this.putExtras(bundle);
    }

    public final void putExtra(String string2, boolean bl) {
        Bundle bundle = new Bundle();
        bundle.putBoolean(string2, bl);
        this.putExtras(bundle);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public final void putExtras(Bundle bundle) {
        this.checkImmutable();
        if (bundle == null) {
            return;
        }
        Iterator<Listener> iterator = this.mExtrasLock;
        synchronized (iterator) {
            if (this.mExtras == null) {
                Bundle bundle2;
                this.mExtras = bundle2 = new Bundle();
            }
            this.mExtras.putAll(bundle);
            bundle = new Bundle(this.mExtras);
        }
        iterator = this.mListeners.iterator();
        while (iterator.hasNext()) {
            iterator.next().onExtrasChanged(this, new Bundle(bundle));
        }
        return;
    }

    public void removeCapability(int n) {
        this.mConnectionCapabilities &= n;
    }

    public final Connection removeConnectionListener(Listener listener) {
        if (listener != null) {
            this.mListeners.remove(listener);
        }
        return this;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public final void removeExtras(List<String> object) {
        List<String> list = this.mExtrasLock;
        synchronized (list) {
            if (this.mExtras != null) {
                Iterator<String> iterator = object.iterator();
                while (iterator.hasNext()) {
                    String string2 = iterator.next();
                    this.mExtras.remove(string2);
                }
            }
        }
        list = Collections.unmodifiableList(object);
        object = this.mListeners.iterator();
        while (object.hasNext()) {
            ((Listener)object.next()).onExtrasRemoved(this, list);
        }
        return;
    }

    public final void removeExtras(String ... arrstring) {
        this.removeExtras(Arrays.asList(arrstring));
    }

    public void requestBluetoothAudio(BluetoothDevice bluetoothDevice) {
        Iterator<Listener> iterator = this.mListeners.iterator();
        while (iterator.hasNext()) {
            iterator.next().onAudioRouteChanged(this, 2, bluetoothDevice.getAddress());
        }
    }

    public final void resetConference() {
        if (this.mConference != null) {
            Log.d(this, "Conference reset", new Object[0]);
            this.mConference = null;
            this.fireConferenceChanged();
        }
    }

    public final void resetConnectionTime() {
        Iterator<Listener> iterator = this.mListeners.iterator();
        while (iterator.hasNext()) {
            iterator.next().onConnectionTimeReset(this);
        }
    }

    public void sendConnectionEvent(String string2, Bundle bundle) {
        Iterator<Listener> iterator = this.mListeners.iterator();
        while (iterator.hasNext()) {
            iterator.next().onConnectionEvent(this, string2, bundle);
        }
    }

    public final void sendRemoteRttRequest() {
        this.mListeners.forEach(new _$$Lambda$Connection$lnfFNF0t9fPLEf01JE291g4chSk(this));
    }

    public final void sendRttInitiationFailure(int n) {
        this.mListeners.forEach(new _$$Lambda$Connection$noXZvls4rxmO_SOjgkFMZLLrfSg(this, n));
    }

    public final void sendRttInitiationSuccess() {
        this.mListeners.forEach(new _$$Lambda$Connection$8xeoCKtoHEwnDqv6gbuSfOMODH0(this));
    }

    public final void sendRttSessionRemotelyTerminated() {
        this.mListeners.forEach(new _$$Lambda$Connection$SYsjtKchY2AYvOeGveCrqxSfKTU(this));
    }

    public final void setActive() {
        this.checkImmutable();
        this.setRingbackRequested(false);
        this.setState(4);
    }

    public final void setAddress(Uri uri, int n) {
        this.checkImmutable();
        Log.d(this, "setAddress %s", uri);
        this.mAddress = uri;
        this.mAddressPresentation = n;
        Iterator<Listener> iterator = this.mListeners.iterator();
        while (iterator.hasNext()) {
            iterator.next().onAddressChanged(this, uri, n);
        }
    }

    public final void setAudioModeIsVoip(boolean bl) {
        this.checkImmutable();
        this.mAudioModeIsVoip = bl;
        Iterator<Listener> iterator = this.mListeners.iterator();
        while (iterator.hasNext()) {
            iterator.next().onAudioModeIsVoipChanged(this, bl);
        }
    }

    public final void setAudioRoute(int n) {
        Iterator<Listener> iterator = this.mListeners.iterator();
        while (iterator.hasNext()) {
            iterator.next().onAudioRouteChanged(this, n, null);
        }
    }

    final void setCallAudioState(CallAudioState callAudioState) {
        this.checkImmutable();
        Log.d(this, "setAudioState %s", callAudioState);
        this.mCallAudioState = callAudioState;
        this.onAudioStateChanged(this.getAudioState());
        this.onCallAudioStateChanged(callAudioState);
    }

    public void setCallDirection(int n) {
        this.mCallDirection = n;
    }

    public final void setCallRadioTech(int n) {
        this.putExtra("android.telecom.extra.CALL_NETWORK_TYPE", ServiceState.rilRadioTechnologyToNetworkType(n));
        if (this.getConference() != null) {
            this.getConference().setCallRadioTech(n);
        }
    }

    public final void setCallerDisplayName(String string2, int n) {
        this.checkImmutable();
        Log.d(this, "setCallerDisplayName %s", string2);
        this.mCallerDisplayName = string2;
        this.mCallerDisplayNamePresentation = n;
        Iterator<Listener> iterator = this.mListeners.iterator();
        while (iterator.hasNext()) {
            iterator.next().onCallerDisplayNameChanged(this, string2, n);
        }
    }

    public final boolean setConference(Conference conference) {
        this.checkImmutable();
        if (this.mConference == null) {
            this.mConference = conference;
            ConnectionService connectionService = this.mConnectionService;
            if (connectionService != null && connectionService.containsConference(conference)) {
                this.fireConferenceChanged();
            }
            return true;
        }
        return false;
    }

    public final void setConferenceableConnections(List<Connection> object) {
        this.checkImmutable();
        this.clearConferenceableList();
        object = object.iterator();
        while (object.hasNext()) {
            Connection connection = (Connection)object.next();
            if (this.mConferenceables.contains(connection)) continue;
            connection.addConnectionListener(this.mConnectionDeathListener);
            this.mConferenceables.add(connection);
        }
        this.fireOnConferenceableConnectionsChanged();
    }

    public final void setConferenceables(List<Conferenceable> object) {
        this.clearConferenceableList();
        Iterator<Conferenceable> iterator = object.iterator();
        while (iterator.hasNext()) {
            object = iterator.next();
            if (this.mConferenceables.contains(object)) continue;
            if (object instanceof Connection) {
                ((Connection)object).addConnectionListener(this.mConnectionDeathListener);
            } else if (object instanceof Conference) {
                ((Conference)object).addListener(this.mConferenceDeathListener);
            }
            this.mConferenceables.add((Conferenceable)object);
        }
        this.fireOnConferenceableConnectionsChanged();
    }

    public final void setConnectTimeMillis(long l) {
        this.mConnectTimeMillis = l;
    }

    public final void setConnectionCapabilities(int n) {
        this.checkImmutable();
        if (this.mConnectionCapabilities != n) {
            this.mConnectionCapabilities = n;
            Iterator<Listener> iterator = this.mListeners.iterator();
            while (iterator.hasNext()) {
                iterator.next().onConnectionCapabilitiesChanged(this, this.mConnectionCapabilities);
            }
        }
    }

    public final void setConnectionProperties(int n) {
        this.checkImmutable();
        if (this.mConnectionProperties != n) {
            this.mConnectionProperties = n;
            Iterator<Listener> iterator = this.mListeners.iterator();
            while (iterator.hasNext()) {
                iterator.next().onConnectionPropertiesChanged(this, this.mConnectionProperties);
            }
        }
    }

    public final void setConnectionService(ConnectionService connectionService) {
        this.checkImmutable();
        if (this.mConnectionService != null) {
            Log.e(this, (Throwable)new Exception(), "Trying to set ConnectionService on a connection which is already associated with another ConnectionService.", new Object[0]);
        } else {
            this.mConnectionService = connectionService;
        }
    }

    public final void setConnectionStartElapsedRealTime(long l) {
        this.mConnectElapsedTimeMillis = l;
    }

    public final void setDialing() {
        this.checkImmutable();
        this.setState(3);
    }

    public final void setDisconnected(DisconnectCause disconnectCause) {
        this.checkImmutable();
        this.mDisconnectCause = disconnectCause;
        this.setState(6);
        Log.d(this, "Disconnected with cause %s", disconnectCause);
        Iterator<Listener> iterator = this.mListeners.iterator();
        while (iterator.hasNext()) {
            iterator.next().onDisconnected(this, disconnectCause);
        }
    }

    public final void setExtras(Bundle bundle) {
        this.checkImmutable();
        this.putExtras(bundle);
        if (this.mPreviousExtraKeys != null) {
            ArrayList<String> arrayList = new ArrayList<String>();
            for (String string2 : this.mPreviousExtraKeys) {
                if (bundle != null && bundle.containsKey(string2)) continue;
                arrayList.add(string2);
            }
            if (!arrayList.isEmpty()) {
                this.removeExtras(arrayList);
            }
        }
        if (this.mPreviousExtraKeys == null) {
            this.mPreviousExtraKeys = new ArraySet<String>();
        }
        this.mPreviousExtraKeys.clear();
        if (bundle != null) {
            this.mPreviousExtraKeys.addAll(bundle.keySet());
        }
    }

    public final void setInitialized() {
        this.checkImmutable();
        this.setState(1);
    }

    public final void setInitializing() {
        this.checkImmutable();
        this.setState(0);
    }

    public final void setNextPostDialChar(char c) {
        this.checkImmutable();
        Iterator<Listener> iterator = this.mListeners.iterator();
        while (iterator.hasNext()) {
            iterator.next().onPostDialChar(this, c);
        }
    }

    public final void setOnHold() {
        this.checkImmutable();
        this.setState(5);
    }

    public void setPhoneAccountHandle(PhoneAccountHandle phoneAccountHandle) {
        if (this.mPhoneAccountHandle != phoneAccountHandle) {
            this.mPhoneAccountHandle = phoneAccountHandle;
            this.notifyPhoneAccountChanged(phoneAccountHandle);
        }
    }

    public final void setPostDialWait(String string2) {
        this.checkImmutable();
        Iterator<Listener> iterator = this.mListeners.iterator();
        while (iterator.hasNext()) {
            iterator.next().onPostDialWait(this, string2);
        }
    }

    public final void setPulling() {
        this.checkImmutable();
        this.setState(7);
    }

    public final void setRingbackRequested(boolean bl) {
        this.checkImmutable();
        if (this.mRingbackRequested != bl) {
            this.mRingbackRequested = bl;
            Iterator<Listener> iterator = this.mListeners.iterator();
            while (iterator.hasNext()) {
                iterator.next().onRingbackRequested(this, bl);
            }
        }
    }

    public final void setRinging() {
        this.checkImmutable();
        this.setState(2);
    }

    public final void setStatusHints(StatusHints statusHints) {
        this.checkImmutable();
        this.mStatusHints = statusHints;
        Iterator<Listener> iterator = this.mListeners.iterator();
        while (iterator.hasNext()) {
            iterator.next().onStatusHintsChanged(this, statusHints);
        }
    }

    public final void setSupportedAudioRoutes(int n) {
        if ((n & 9) != 0) {
            if (this.mSupportedAudioRoutes != n) {
                this.mSupportedAudioRoutes = n;
                Iterator<Listener> iterator = this.mListeners.iterator();
                while (iterator.hasNext()) {
                    iterator.next().onSupportedAudioRoutesChanged(this, this.mSupportedAudioRoutes);
                }
            }
            return;
        }
        throw new IllegalArgumentException("supported audio routes must include either speaker or earpiece");
    }

    public void setTelecomCallId(String string2) {
        this.mTelecomCallId = string2;
    }

    public final void setVideoProvider(VideoProvider videoProvider) {
        this.checkImmutable();
        this.mVideoProvider = videoProvider;
        Iterator<Listener> iterator = this.mListeners.iterator();
        while (iterator.hasNext()) {
            iterator.next().onVideoProviderChanged(this, videoProvider);
        }
    }

    public final void setVideoState(int n) {
        this.checkImmutable();
        Log.d(this, "setVideoState %d", n);
        this.mVideoState = n;
        Iterator<Listener> iterator = this.mListeners.iterator();
        while (iterator.hasNext()) {
            iterator.next().onVideoStateChanged(this, this.mVideoState);
        }
    }

    public final void unsetConnectionService(ConnectionService connectionService) {
        if (this.mConnectionService != connectionService) {
            Log.e(this, (Throwable)new Exception(), "Trying to remove ConnectionService from a Connection that does not belong to the ConnectionService.", new Object[0]);
        } else {
            this.mConnectionService = null;
        }
    }

    protected final void updateConferenceParticipants(List<ConferenceParticipant> list) {
        Iterator<Listener> iterator = this.mListeners.iterator();
        while (iterator.hasNext()) {
            iterator.next().onConferenceParticipantsChanged(this, list);
        }
    }

    private static class FailureSignalingConnection
    extends Connection {
        private boolean mImmutable = false;

        public FailureSignalingConnection(DisconnectCause disconnectCause) {
            this.setDisconnected(disconnectCause);
            this.mImmutable = true;
        }

        @Override
        public void checkImmutable() {
            if (!this.mImmutable) {
                return;
            }
            throw new UnsupportedOperationException("Connection is immutable");
        }
    }

    public static abstract class Listener {
        public void onAddressChanged(Connection connection, Uri uri, int n) {
        }

        public void onAudioModeIsVoipChanged(Connection connection, boolean bl) {
        }

        public void onAudioRouteChanged(Connection connection, int n, String string2) {
        }

        public void onCallerDisplayNameChanged(Connection connection, String string2, int n) {
        }

        public void onConferenceChanged(Connection connection, Conference conference) {
        }

        public void onConferenceMergeFailed(Connection connection) {
        }

        public void onConferenceParticipantsChanged(Connection connection, List<ConferenceParticipant> list) {
        }

        public void onConferenceStarted() {
        }

        public void onConferenceSupportedChanged(Connection connection, boolean bl) {
        }

        public void onConferenceablesChanged(Connection connection, List<Conferenceable> list) {
        }

        public void onConnectionCapabilitiesChanged(Connection connection, int n) {
        }

        public void onConnectionEvent(Connection connection, String string2, Bundle bundle) {
        }

        public void onConnectionPropertiesChanged(Connection connection, int n) {
        }

        public void onConnectionTimeReset(Connection connection) {
        }

        public void onDestroyed(Connection connection) {
        }

        public void onDisconnected(Connection connection, DisconnectCause disconnectCause) {
        }

        public void onExtrasChanged(Connection connection, Bundle bundle) {
        }

        public void onExtrasRemoved(Connection connection, List<String> list) {
        }

        public void onPhoneAccountChanged(Connection connection, PhoneAccountHandle phoneAccountHandle) {
        }

        public void onPostDialChar(Connection connection, char c) {
        }

        public void onPostDialWait(Connection connection, String string2) {
        }

        public void onRemoteRttRequest(Connection connection) {
        }

        public void onRingbackRequested(Connection connection, boolean bl) {
        }

        public void onRttInitiationFailure(Connection connection, int n) {
        }

        public void onRttInitiationSuccess(Connection connection) {
        }

        public void onRttSessionRemotelyTerminated(Connection connection) {
        }

        public void onStateChanged(Connection connection, int n) {
        }

        public void onStatusHintsChanged(Connection connection, StatusHints statusHints) {
        }

        public void onSupportedAudioRoutesChanged(Connection connection, int n) {
        }

        public void onVideoProviderChanged(Connection connection, VideoProvider videoProvider) {
        }

        public void onVideoStateChanged(Connection connection, int n) {
        }
    }

    public static final class RttModifyStatus {
        public static final int SESSION_MODIFY_REQUEST_FAIL = 2;
        public static final int SESSION_MODIFY_REQUEST_INVALID = 3;
        public static final int SESSION_MODIFY_REQUEST_REJECTED_BY_REMOTE = 5;
        public static final int SESSION_MODIFY_REQUEST_SUCCESS = 1;
        public static final int SESSION_MODIFY_REQUEST_TIMED_OUT = 4;

        private RttModifyStatus() {
        }
    }

    public static final class RttTextStream {
        private static final int READ_BUFFER_SIZE = 1000;
        private final ParcelFileDescriptor mFdFromInCall;
        private final ParcelFileDescriptor mFdToInCall;
        private final FileInputStream mFromInCallFileInputStream;
        private final InputStreamReader mPipeFromInCall;
        private final OutputStreamWriter mPipeToInCall;
        private char[] mReadBuffer = new char[1000];

        public RttTextStream(ParcelFileDescriptor parcelFileDescriptor, ParcelFileDescriptor parcelFileDescriptor2) {
            this.mFdFromInCall = parcelFileDescriptor2;
            this.mFdToInCall = parcelFileDescriptor;
            this.mFromInCallFileInputStream = new FileInputStream(parcelFileDescriptor2.getFileDescriptor());
            this.mPipeFromInCall = new InputStreamReader(Channels.newInputStream(Channels.newChannel(this.mFromInCallFileInputStream)));
            this.mPipeToInCall = new OutputStreamWriter(new FileOutputStream(parcelFileDescriptor.getFileDescriptor()));
        }

        public ParcelFileDescriptor getFdFromInCall() {
            return this.mFdFromInCall;
        }

        public ParcelFileDescriptor getFdToInCall() {
            return this.mFdToInCall;
        }

        public String read() throws IOException {
            int n = this.mPipeFromInCall.read(this.mReadBuffer, 0, 1000);
            if (n < 0) {
                return null;
            }
            return new String(this.mReadBuffer, 0, n);
        }

        public String readImmediately() throws IOException {
            if (this.mFromInCallFileInputStream.available() > 0) {
                return this.read();
            }
            return null;
        }

        public void write(String string2) throws IOException {
            this.mPipeToInCall.write(string2);
            this.mPipeToInCall.flush();
        }
    }

    public static abstract class VideoProvider {
        private static final int MSG_ADD_VIDEO_CALLBACK = 1;
        private static final int MSG_REMOVE_VIDEO_CALLBACK = 12;
        private static final int MSG_REQUEST_CAMERA_CAPABILITIES = 9;
        private static final int MSG_REQUEST_CONNECTION_DATA_USAGE = 10;
        private static final int MSG_SEND_SESSION_MODIFY_REQUEST = 7;
        private static final int MSG_SEND_SESSION_MODIFY_RESPONSE = 8;
        private static final int MSG_SET_CAMERA = 2;
        private static final int MSG_SET_DEVICE_ORIENTATION = 5;
        private static final int MSG_SET_DISPLAY_SURFACE = 4;
        private static final int MSG_SET_PAUSE_IMAGE = 11;
        private static final int MSG_SET_PREVIEW_SURFACE = 3;
        private static final int MSG_SET_ZOOM = 6;
        public static final int SESSION_EVENT_CAMERA_FAILURE = 5;
        private static final String SESSION_EVENT_CAMERA_FAILURE_STR = "CAMERA_FAIL";
        public static final int SESSION_EVENT_CAMERA_PERMISSION_ERROR = 7;
        private static final String SESSION_EVENT_CAMERA_PERMISSION_ERROR_STR = "CAMERA_PERMISSION_ERROR";
        public static final int SESSION_EVENT_CAMERA_READY = 6;
        private static final String SESSION_EVENT_CAMERA_READY_STR = "CAMERA_READY";
        public static final int SESSION_EVENT_RX_PAUSE = 1;
        private static final String SESSION_EVENT_RX_PAUSE_STR = "RX_PAUSE";
        public static final int SESSION_EVENT_RX_RESUME = 2;
        private static final String SESSION_EVENT_RX_RESUME_STR = "RX_RESUME";
        public static final int SESSION_EVENT_TX_START = 3;
        private static final String SESSION_EVENT_TX_START_STR = "TX_START";
        public static final int SESSION_EVENT_TX_STOP = 4;
        private static final String SESSION_EVENT_TX_STOP_STR = "TX_STOP";
        private static final String SESSION_EVENT_UNKNOWN_STR = "UNKNOWN";
        public static final int SESSION_MODIFY_REQUEST_FAIL = 2;
        public static final int SESSION_MODIFY_REQUEST_INVALID = 3;
        public static final int SESSION_MODIFY_REQUEST_REJECTED_BY_REMOTE = 5;
        public static final int SESSION_MODIFY_REQUEST_SUCCESS = 1;
        public static final int SESSION_MODIFY_REQUEST_TIMED_OUT = 4;
        private final VideoProviderBinder mBinder = new VideoProviderBinder();
        private VideoProviderHandler mMessageHandler;
        private ConcurrentHashMap<IBinder, IVideoCallback> mVideoCallbacks = new ConcurrentHashMap(8, 0.9f, 1);

        public VideoProvider() {
            this.mMessageHandler = new VideoProviderHandler(Looper.getMainLooper());
        }

        @UnsupportedAppUsage
        public VideoProvider(Looper looper) {
            this.mMessageHandler = new VideoProviderHandler(looper);
        }

        public static String sessionEventToString(int n) {
            switch (n) {
                default: {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("UNKNOWN ");
                    stringBuilder.append(n);
                    return stringBuilder.toString();
                }
                case 7: {
                    return SESSION_EVENT_CAMERA_PERMISSION_ERROR_STR;
                }
                case 6: {
                    return SESSION_EVENT_CAMERA_READY_STR;
                }
                case 5: {
                    return SESSION_EVENT_CAMERA_FAILURE_STR;
                }
                case 4: {
                    return SESSION_EVENT_TX_STOP_STR;
                }
                case 3: {
                    return SESSION_EVENT_TX_START_STR;
                }
                case 2: {
                    return SESSION_EVENT_RX_RESUME_STR;
                }
                case 1: 
            }
            return SESSION_EVENT_RX_PAUSE_STR;
        }

        public void changeCallDataUsage(long l) {
            this.setCallDataUsage(l);
        }

        public void changeCameraCapabilities(VideoProfile.CameraCapabilities cameraCapabilities) {
            ConcurrentHashMap<IBinder, IVideoCallback> concurrentHashMap = this.mVideoCallbacks;
            if (concurrentHashMap != null) {
                for (IVideoCallback iVideoCallback : concurrentHashMap.values()) {
                    try {
                        iVideoCallback.changeCameraCapabilities(cameraCapabilities);
                    }
                    catch (RemoteException remoteException) {
                        Log.w(this, "changeCameraCapabilities callback failed", remoteException);
                    }
                }
            }
        }

        public void changePeerDimensions(int n, int n2) {
            ConcurrentHashMap<IBinder, IVideoCallback> concurrentHashMap = this.mVideoCallbacks;
            if (concurrentHashMap != null) {
                for (IVideoCallback iVideoCallback : concurrentHashMap.values()) {
                    try {
                        iVideoCallback.changePeerDimensions(n, n2);
                    }
                    catch (RemoteException remoteException) {
                        Log.w(this, "changePeerDimensions callback failed", remoteException);
                    }
                }
            }
        }

        public void changeVideoQuality(int n) {
            ConcurrentHashMap<IBinder, IVideoCallback> concurrentHashMap = this.mVideoCallbacks;
            if (concurrentHashMap != null) {
                for (IVideoCallback iVideoCallback : concurrentHashMap.values()) {
                    try {
                        iVideoCallback.changeVideoQuality(n);
                    }
                    catch (RemoteException remoteException) {
                        Log.w(this, "changeVideoQuality callback failed", remoteException);
                    }
                }
            }
        }

        public final IVideoProvider getInterface() {
            return this.mBinder;
        }

        public void handleCallSessionEvent(int n) {
            ConcurrentHashMap<IBinder, IVideoCallback> concurrentHashMap = this.mVideoCallbacks;
            if (concurrentHashMap != null) {
                for (IVideoCallback iVideoCallback : concurrentHashMap.values()) {
                    try {
                        iVideoCallback.handleCallSessionEvent(n);
                    }
                    catch (RemoteException remoteException) {
                        Log.w(this, "handleCallSessionEvent callback failed", remoteException);
                    }
                }
            }
        }

        public abstract void onRequestCameraCapabilities();

        public abstract void onRequestConnectionDataUsage();

        public abstract void onSendSessionModifyRequest(VideoProfile var1, VideoProfile var2);

        public abstract void onSendSessionModifyResponse(VideoProfile var1);

        public abstract void onSetCamera(String var1);

        public void onSetCamera(String string2, String string3, int n, int n2, int n3) {
        }

        public abstract void onSetDeviceOrientation(int var1);

        public abstract void onSetDisplaySurface(Surface var1);

        public abstract void onSetPauseImage(Uri var1);

        public abstract void onSetPreviewSurface(Surface var1);

        public abstract void onSetZoom(float var1);

        public void receiveSessionModifyRequest(VideoProfile videoProfile) {
            ConcurrentHashMap<IBinder, IVideoCallback> concurrentHashMap = this.mVideoCallbacks;
            if (concurrentHashMap != null) {
                for (IVideoCallback iVideoCallback : concurrentHashMap.values()) {
                    try {
                        iVideoCallback.receiveSessionModifyRequest(videoProfile);
                    }
                    catch (RemoteException remoteException) {
                        Log.w(this, "receiveSessionModifyRequest callback failed", remoteException);
                    }
                }
            }
        }

        public void receiveSessionModifyResponse(int n, VideoProfile videoProfile, VideoProfile videoProfile2) {
            ConcurrentHashMap<IBinder, IVideoCallback> concurrentHashMap = this.mVideoCallbacks;
            if (concurrentHashMap != null) {
                for (IVideoCallback iVideoCallback : concurrentHashMap.values()) {
                    try {
                        iVideoCallback.receiveSessionModifyResponse(n, videoProfile, videoProfile2);
                    }
                    catch (RemoteException remoteException) {
                        Log.w(this, "receiveSessionModifyResponse callback failed", remoteException);
                    }
                }
            }
        }

        public void setCallDataUsage(long l) {
            ConcurrentHashMap<IBinder, IVideoCallback> concurrentHashMap = this.mVideoCallbacks;
            if (concurrentHashMap != null) {
                for (IVideoCallback iVideoCallback : concurrentHashMap.values()) {
                    try {
                        iVideoCallback.changeCallDataUsage(l);
                    }
                    catch (RemoteException remoteException) {
                        Log.w(this, "setCallDataUsage callback failed", remoteException);
                    }
                }
            }
        }

        private final class VideoProviderBinder
        extends IVideoProvider.Stub {
            private VideoProviderBinder() {
            }

            @Override
            public void addVideoCallback(IBinder iBinder) {
                VideoProvider.this.mMessageHandler.obtainMessage(1, iBinder).sendToTarget();
            }

            @Override
            public void removeVideoCallback(IBinder iBinder) {
                VideoProvider.this.mMessageHandler.obtainMessage(12, iBinder).sendToTarget();
            }

            @Override
            public void requestCallDataUsage() {
                VideoProvider.this.mMessageHandler.obtainMessage(10).sendToTarget();
            }

            @Override
            public void requestCameraCapabilities() {
                VideoProvider.this.mMessageHandler.obtainMessage(9).sendToTarget();
            }

            @Override
            public void sendSessionModifyRequest(VideoProfile videoProfile, VideoProfile videoProfile2) {
                SomeArgs someArgs = SomeArgs.obtain();
                someArgs.arg1 = videoProfile;
                someArgs.arg2 = videoProfile2;
                VideoProvider.this.mMessageHandler.obtainMessage(7, someArgs).sendToTarget();
            }

            @Override
            public void sendSessionModifyResponse(VideoProfile videoProfile) {
                VideoProvider.this.mMessageHandler.obtainMessage(8, videoProfile).sendToTarget();
            }

            @Override
            public void setCamera(String string2, String string3, int n) {
                SomeArgs someArgs = SomeArgs.obtain();
                someArgs.arg1 = string2;
                someArgs.arg2 = string3;
                someArgs.argi1 = Binder.getCallingUid();
                someArgs.argi2 = Binder.getCallingPid();
                someArgs.argi3 = n;
                VideoProvider.this.mMessageHandler.obtainMessage(2, someArgs).sendToTarget();
            }

            @Override
            public void setDeviceOrientation(int n) {
                VideoProvider.this.mMessageHandler.obtainMessage(5, n, 0).sendToTarget();
            }

            @Override
            public void setDisplaySurface(Surface surface) {
                VideoProvider.this.mMessageHandler.obtainMessage(4, surface).sendToTarget();
            }

            @Override
            public void setPauseImage(Uri uri) {
                VideoProvider.this.mMessageHandler.obtainMessage(11, uri).sendToTarget();
            }

            @Override
            public void setPreviewSurface(Surface surface) {
                VideoProvider.this.mMessageHandler.obtainMessage(3, surface).sendToTarget();
            }

            @Override
            public void setZoom(float f) {
                VideoProvider.this.mMessageHandler.obtainMessage(6, Float.valueOf(f)).sendToTarget();
            }
        }

        private final class VideoProviderHandler
        extends Handler {
            public VideoProviderHandler() {
            }

            public VideoProviderHandler(Looper looper) {
                super(looper);
            }

            @Override
            public void handleMessage(Message object) {
                switch (((Message)object).what) {
                    default: {
                        break;
                    }
                    case 12: {
                        IBinder iBinder = (IBinder)((Message)object).obj;
                        IVideoCallback.Stub.asInterface((IBinder)((Message)object).obj);
                        if (!VideoProvider.this.mVideoCallbacks.containsKey(iBinder)) {
                            Log.i(this, "removeVideoProvider - skipped; not present.", new Object[0]);
                            break;
                        }
                        VideoProvider.this.mVideoCallbacks.remove(iBinder);
                        break;
                    }
                    case 11: {
                        VideoProvider.this.onSetPauseImage((Uri)((Message)object).obj);
                        break;
                    }
                    case 10: {
                        VideoProvider.this.onRequestConnectionDataUsage();
                        break;
                    }
                    case 9: {
                        VideoProvider.this.onRequestCameraCapabilities();
                        break;
                    }
                    case 8: {
                        VideoProvider.this.onSendSessionModifyResponse((VideoProfile)((Message)object).obj);
                        break;
                    }
                    case 7: {
                        object = (SomeArgs)((Message)object).obj;
                        try {
                            VideoProvider.this.onSendSessionModifyRequest((VideoProfile)((SomeArgs)object).arg1, (VideoProfile)((SomeArgs)object).arg2);
                            break;
                        }
                        finally {
                            ((SomeArgs)object).recycle();
                        }
                    }
                    case 6: {
                        VideoProvider.this.onSetZoom(((Float)((Message)object).obj).floatValue());
                        break;
                    }
                    case 5: {
                        VideoProvider.this.onSetDeviceOrientation(((Message)object).arg1);
                        break;
                    }
                    case 4: {
                        VideoProvider.this.onSetDisplaySurface((Surface)((Message)object).obj);
                        break;
                    }
                    case 3: {
                        VideoProvider.this.onSetPreviewSurface((Surface)((Message)object).obj);
                        break;
                    }
                    case 2: {
                        object = (SomeArgs)((Message)object).obj;
                        try {
                            VideoProvider.this.onSetCamera((String)((SomeArgs)object).arg1);
                            VideoProvider.this.onSetCamera((String)((SomeArgs)object).arg1, (String)((SomeArgs)object).arg2, ((SomeArgs)object).argi1, ((SomeArgs)object).argi2, ((SomeArgs)object).argi3);
                            break;
                        }
                        finally {
                            ((SomeArgs)object).recycle();
                        }
                    }
                    case 1: {
                        IBinder iBinder = (IBinder)((Message)object).obj;
                        object = IVideoCallback.Stub.asInterface((IBinder)((Message)object).obj);
                        if (object == null) {
                            Log.w(this, "addVideoProvider - skipped; callback is null.", new Object[0]);
                            break;
                        }
                        if (VideoProvider.this.mVideoCallbacks.containsKey(iBinder)) {
                            Log.i(this, "addVideoProvider - skipped; already present.", new Object[0]);
                            break;
                        }
                        VideoProvider.this.mVideoCallbacks.put(iBinder, object);
                    }
                }
            }
        }

    }

}

