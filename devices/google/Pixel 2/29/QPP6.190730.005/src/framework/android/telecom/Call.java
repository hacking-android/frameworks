/*
 * Decompiled with CFR 0.145.
 */
package android.telecom;

import android.annotation.SystemApi;
import android.annotation.UnsupportedAppUsage;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.ParcelFileDescriptor;
import android.telecom.CallbackRecord;
import android.telecom.DisconnectCause;
import android.telecom.GatewayInfo;
import android.telecom.InCallAdapter;
import android.telecom.InCallService;
import android.telecom.Log;
import android.telecom.ParcelableCall;
import android.telecom.ParcelableRttCall;
import android.telecom.Phone;
import android.telecom.PhoneAccountHandle;
import android.telecom.StatusHints;
import android.telecom.VideoCallImpl;
import android.telecom.VideoProfile;
import android.telecom._$$Lambda$Call$5JdbCgV1DP_WhiljnHJKKAJdCu0;
import android.telecom._$$Lambda$Call$JyYlHynNNc3DTrfrP5aXatJNft4;
import android.telecom._$$Lambda$Call$aPdcAxyKfpxcuraTjET8ce3xApc;
import android.telecom._$$Lambda$Call$bt1B6cq3ylYqEtzOXnJWMeJ_ojc;
import android.telecom._$$Lambda$Call$hgXdGxKbb9IRpCeFrYieOwUuElE;
import android.telecom._$$Lambda$Call$qjo4awib5yVZC_4Qe_hhqUSk7ho;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;

public final class Call {
    @Deprecated
    public static final String AVAILABLE_PHONE_ACCOUNTS = "selectPhoneAccountAccounts";
    public static final String EVENT_HANDOVER_COMPLETE = "android.telecom.event.HANDOVER_COMPLETE";
    public static final String EVENT_HANDOVER_FAILED = "android.telecom.event.HANDOVER_FAILED";
    public static final String EVENT_HANDOVER_SOURCE_DISCONNECTED = "android.telecom.event.HANDOVER_SOURCE_DISCONNECTED";
    public static final String EVENT_REQUEST_HANDOVER = "android.telecom.event.REQUEST_HANDOVER";
    public static final String EXTRA_HANDOVER_EXTRAS = "android.telecom.extra.HANDOVER_EXTRAS";
    public static final String EXTRA_HANDOVER_PHONE_ACCOUNT_HANDLE = "android.telecom.extra.HANDOVER_PHONE_ACCOUNT_HANDLE";
    public static final String EXTRA_HANDOVER_VIDEO_STATE = "android.telecom.extra.HANDOVER_VIDEO_STATE";
    public static final String EXTRA_LAST_EMERGENCY_CALLBACK_TIME_MILLIS = "android.telecom.extra.LAST_EMERGENCY_CALLBACK_TIME_MILLIS";
    public static final String EXTRA_SILENT_RINGING_REQUESTED = "android.telecom.extra.SILENT_RINGING_REQUESTED";
    public static final String EXTRA_SUGGESTED_PHONE_ACCOUNTS = "android.telecom.extra.SUGGESTED_PHONE_ACCOUNTS";
    public static final int STATE_ACTIVE = 4;
    public static final int STATE_CONNECTING = 9;
    public static final int STATE_DIALING = 1;
    public static final int STATE_DISCONNECTED = 7;
    public static final int STATE_DISCONNECTING = 10;
    public static final int STATE_HOLDING = 3;
    public static final int STATE_NEW = 0;
    @SystemApi
    @Deprecated
    public static final int STATE_PRE_DIAL_WAIT = 8;
    public static final int STATE_PULLING_CALL = 11;
    public static final int STATE_RINGING = 2;
    public static final int STATE_SELECT_PHONE_ACCOUNT = 8;
    private final List<CallbackRecord<Callback>> mCallbackRecords = new CopyOnWriteArrayList<CallbackRecord<Callback>>();
    private String mCallingPackage;
    private List<String> mCannedTextResponses = null;
    private final List<Call> mChildren = new ArrayList<Call>();
    private boolean mChildrenCached;
    private final List<String> mChildrenIds = new ArrayList<String>();
    private final List<Call> mConferenceableCalls = new ArrayList<Call>();
    private Details mDetails;
    private Bundle mExtras;
    private final InCallAdapter mInCallAdapter;
    private String mParentId = null;
    private final Phone mPhone;
    private String mRemainingPostDialSequence;
    private RttCall mRttCall;
    private int mState;
    private int mTargetSdkVersion;
    private final String mTelecomCallId;
    private final List<Call> mUnmodifiableChildren = Collections.unmodifiableList(this.mChildren);
    private final List<Call> mUnmodifiableConferenceableCalls = Collections.unmodifiableList(this.mConferenceableCalls);
    private VideoCallImpl mVideoCallImpl;

    Call(Phone phone, String string2, InCallAdapter inCallAdapter, int n, String string3, int n2) {
        this.mPhone = phone;
        this.mTelecomCallId = string2;
        this.mInCallAdapter = inCallAdapter;
        this.mState = n;
        this.mCallingPackage = string3;
        this.mTargetSdkVersion = n2;
    }

    Call(Phone phone, String string2, InCallAdapter inCallAdapter, String string3, int n) {
        this.mPhone = phone;
        this.mTelecomCallId = string2;
        this.mInCallAdapter = inCallAdapter;
        this.mState = 0;
        this.mCallingPackage = string3;
        this.mTargetSdkVersion = n;
    }

    private static boolean areBundlesEqual(Bundle bundle, Bundle bundle2) {
        boolean bl = true;
        if (bundle != null && bundle2 != null) {
            if (bundle.size() != bundle2.size()) {
                return false;
            }
            for (String string2 : bundle.keySet()) {
                if (string2 == null || Objects.equals(bundle.get(string2), bundle2.get(string2))) continue;
                return false;
            }
            return true;
        }
        if (bundle != bundle2) {
            bl = false;
        }
        return bl;
    }

    private void fireCallDestroyed() {
        if (this.mCallbackRecords.isEmpty()) {
            this.mPhone.internalRemoveCall(this);
        }
        for (final CallbackRecord<Callback> callbackRecord : this.mCallbackRecords) {
            final Callback callback = callbackRecord.getCallback();
            callbackRecord.getHandler().post(new Runnable(){

                /*
                 * Enabled aggressive block sorting
                 * Enabled unnecessary exception pruning
                 * Enabled aggressive exception aggregation
                 * Converted monitor instructions to comments
                 * Lifted jumps to return sites
                 */
                @Override
                public void run() {
                    boolean bl = false;
                    Object var2_2 = null;
                    try {
                        callback.onCallDestroyed(this);
                    }
                    catch (RuntimeException runtimeException) {
                        // empty catch block
                    }
                    Call call = Call.this;
                    // MONITORENTER : call
                    Call.this.mCallbackRecords.remove(callbackRecord);
                    if (Call.this.mCallbackRecords.isEmpty()) {
                        bl = true;
                    }
                    // MONITOREXIT : call
                    if (bl) {
                        Call.this.mPhone.internalRemoveCall(this);
                    }
                    if (var2_2 != null) throw var2_2;
                }
            });
        }
    }

    private void fireCannedTextResponsesLoaded(final List<String> list) {
        for (CallbackRecord<Callback> callbackRecord : this.mCallbackRecords) {
            final Callback callback = callbackRecord.getCallback();
            callbackRecord.getHandler().post(new Runnable(){

                @Override
                public void run() {
                    callback.onCannedTextResponsesLoaded(this, list);
                }
            });
        }
    }

    private void fireChildrenChanged(final List<Call> list) {
        for (CallbackRecord<Callback> callbackRecord : this.mCallbackRecords) {
            final Callback callback = callbackRecord.getCallback();
            callbackRecord.getHandler().post(new Runnable(){

                @Override
                public void run() {
                    callback.onChildrenChanged(this, list);
                }
            });
        }
    }

    private void fireConferenceableCallsChanged() {
        for (CallbackRecord<Callback> callbackRecord : this.mCallbackRecords) {
            final Callback callback = callbackRecord.getCallback();
            callbackRecord.getHandler().post(new Runnable(){

                @Override
                public void run() {
                    callback.onConferenceableCallsChanged(this, Call.this.mUnmodifiableConferenceableCalls);
                }
            });
        }
    }

    private void fireDetailsChanged(final Details details) {
        for (CallbackRecord<Callback> callbackRecord : this.mCallbackRecords) {
            final Callback callback = callbackRecord.getCallback();
            callbackRecord.getHandler().post(new Runnable(){

                @Override
                public void run() {
                    callback.onDetailsChanged(this, details);
                }
            });
        }
    }

    private void fireOnConnectionEvent(final String string2, final Bundle bundle) {
        for (CallbackRecord<Callback> callbackRecord : this.mCallbackRecords) {
            final Callback callback = callbackRecord.getCallback();
            callbackRecord.getHandler().post(new Runnable(){

                @Override
                public void run() {
                    callback.onConnectionEvent(this, string2, bundle);
                }
            });
        }
    }

    private void fireOnIsRttChanged(boolean bl, RttCall rttCall) {
        for (CallbackRecord<Callback> callbackRecord : this.mCallbackRecords) {
            Callback callback = callbackRecord.getCallback();
            callbackRecord.getHandler().post(new _$$Lambda$Call$5JdbCgV1DP_WhiljnHJKKAJdCu0(callback, this, bl, rttCall));
        }
    }

    private void fireOnRttModeChanged(int n) {
        for (CallbackRecord<Callback> callbackRecord : this.mCallbackRecords) {
            Callback callback = callbackRecord.getCallback();
            callbackRecord.getHandler().post(new _$$Lambda$Call$qjo4awib5yVZC_4Qe_hhqUSk7ho(callback, this, n));
        }
    }

    private void fireParentChanged(final Call call) {
        for (CallbackRecord<Callback> callbackRecord : this.mCallbackRecords) {
            final Callback callback = callbackRecord.getCallback();
            callbackRecord.getHandler().post(new Runnable(){

                @Override
                public void run() {
                    callback.onParentChanged(this, call);
                }
            });
        }
    }

    private void firePostDialWait(final String string2) {
        for (CallbackRecord<Callback> callbackRecord : this.mCallbackRecords) {
            final Callback callback = callbackRecord.getCallback();
            callbackRecord.getHandler().post(new Runnable(){

                @Override
                public void run() {
                    callback.onPostDialWait(this, string2);
                }
            });
        }
    }

    private void fireStateChanged(final int n) {
        for (CallbackRecord<Callback> callbackRecord : this.mCallbackRecords) {
            final Callback callback = callbackRecord.getCallback();
            callbackRecord.getHandler().post(new Runnable(){

                @Override
                public void run() {
                    callback.onStateChanged(this, n);
                }
            });
        }
    }

    private void fireVideoCallChanged(final InCallService.VideoCall videoCall) {
        for (CallbackRecord<Callback> callbackRecord : this.mCallbackRecords) {
            final Callback callback = callbackRecord.getCallback();
            callbackRecord.getHandler().post(new Runnable(){

                @Override
                public void run() {
                    callback.onVideoCallChanged(this, videoCall);
                }
            });
        }
    }

    static /* synthetic */ void lambda$fireOnIsRttChanged$4(Callback callback, Call call, boolean bl, RttCall rttCall) {
        callback.onRttStatusChanged(call, bl, rttCall);
    }

    static /* synthetic */ void lambda$fireOnRttModeChanged$5(Callback callback, Call call, int n) {
        callback.onRttModeChanged(call, n);
    }

    static /* synthetic */ void lambda$internalOnHandoverComplete$3(Callback callback, Call call) {
        callback.onHandoverComplete(call);
    }

    static /* synthetic */ void lambda$internalOnHandoverFailed$2(Callback callback, Call call, int n) {
        callback.onHandoverFailed(call, n);
    }

    static /* synthetic */ void lambda$internalOnRttInitiationFailure$1(Callback callback, Call call, int n) {
        callback.onRttInitiationFailure(call, n);
    }

    static /* synthetic */ void lambda$internalOnRttUpgradeRequest$0(Callback callback, Call call, int n) {
        callback.onRttRequest(call, n);
    }

    private static String stateToString(int n) {
        switch (n) {
            default: {
                Log.w(Call.class, "Unknown state %d", n);
                return "UNKNOWN";
            }
            case 10: {
                return "DISCONNECTING";
            }
            case 9: {
                return "CONNECTING";
            }
            case 8: {
                return "SELECT_PHONE_ACCOUNT";
            }
            case 7: {
                return "DISCONNECTED";
            }
            case 4: {
                return "ACTIVE";
            }
            case 3: {
                return "HOLDING";
            }
            case 2: {
                return "RINGING";
            }
            case 1: {
                return "DIALING";
            }
            case 0: 
        }
        return "NEW";
    }

    @SystemApi
    @Deprecated
    public void addListener(Listener listener) {
        this.registerCallback(listener);
    }

    public void answer(int n) {
        this.mInCallAdapter.answerCall(this.mTelecomCallId, n);
    }

    public void conference(Call call) {
        if (call != null) {
            this.mInCallAdapter.conference(this.mTelecomCallId, call.mTelecomCallId);
        }
    }

    public void deflect(Uri uri) {
        this.mInCallAdapter.deflectCall(this.mTelecomCallId, uri);
    }

    public void disconnect() {
        this.mInCallAdapter.disconnectCall(this.mTelecomCallId);
    }

    public List<String> getCannedTextResponses() {
        return this.mCannedTextResponses;
    }

    public List<Call> getChildren() {
        if (!this.mChildrenCached) {
            this.mChildrenCached = true;
            this.mChildren.clear();
            for (String string2 : this.mChildrenIds) {
                Call object = this.mPhone.internalGetCallByTelecomId(string2);
                if (object == null) {
                    this.mChildrenCached = false;
                    continue;
                }
                this.mChildren.add(object);
            }
        }
        return this.mUnmodifiableChildren;
    }

    public List<Call> getConferenceableCalls() {
        return this.mUnmodifiableConferenceableCalls;
    }

    public Details getDetails() {
        return this.mDetails;
    }

    public Call getParent() {
        String string2 = this.mParentId;
        if (string2 != null) {
            return this.mPhone.internalGetCallByTelecomId(string2);
        }
        return null;
    }

    public String getRemainingPostDialSequence() {
        return this.mRemainingPostDialSequence;
    }

    public RttCall getRttCall() {
        return this.mRttCall;
    }

    public int getState() {
        return this.mState;
    }

    public InCallService.VideoCall getVideoCall() {
        return this.mVideoCallImpl;
    }

    public void handoverTo(PhoneAccountHandle phoneAccountHandle, int n, Bundle bundle) {
        this.mInCallAdapter.handoverTo(this.mTelecomCallId, phoneAccountHandle, n, bundle);
    }

    public void hold() {
        this.mInCallAdapter.holdCall(this.mTelecomCallId);
    }

    final String internalGetCallId() {
        return this.mTelecomCallId;
    }

    final void internalOnConnectionEvent(String string2, Bundle bundle) {
        this.fireOnConnectionEvent(string2, bundle);
    }

    final void internalOnHandoverComplete() {
        for (CallbackRecord<Callback> callbackRecord : this.mCallbackRecords) {
            Callback callback = callbackRecord.getCallback();
            callbackRecord.getHandler().post(new _$$Lambda$Call$bt1B6cq3ylYqEtzOXnJWMeJ_ojc(callback, this));
        }
    }

    final void internalOnHandoverFailed(int n) {
        for (CallbackRecord<Callback> callbackRecord : this.mCallbackRecords) {
            Callback callback = callbackRecord.getCallback();
            callbackRecord.getHandler().post(new _$$Lambda$Call$aPdcAxyKfpxcuraTjET8ce3xApc(callback, this, n));
        }
    }

    final void internalOnRttInitiationFailure(int n) {
        for (CallbackRecord<Callback> callbackRecord : this.mCallbackRecords) {
            Callback callback = callbackRecord.getCallback();
            callbackRecord.getHandler().post(new _$$Lambda$Call$JyYlHynNNc3DTrfrP5aXatJNft4(callback, this, n));
        }
    }

    final void internalOnRttUpgradeRequest(int n) {
        for (CallbackRecord<Callback> callbackRecord : this.mCallbackRecords) {
            Callback callback = callbackRecord.getCallback();
            callbackRecord.getHandler().post(new _$$Lambda$Call$hgXdGxKbb9IRpCeFrYieOwUuElE(callback, this, n));
        }
    }

    final void internalSetDisconnected() {
        if (this.mState != 7) {
            this.mState = 7;
            this.fireStateChanged(this.mState);
            this.fireCallDestroyed();
        }
    }

    final void internalSetPostDialWait(String string2) {
        this.mRemainingPostDialSequence = string2;
        this.firePostDialWait(this.mRemainingPostDialSequence);
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    final void internalUpdate(ParcelableCall var1_1, Map<String, Call> var2_2) {
        var3_3 = Details.createFromParcelableCall((ParcelableCall)var1_1);
        var4_4 = Objects.equals(this.mDetails, var3_3) ^ true;
        if (var4_4) {
            this.mDetails = var3_3;
        }
        var6_6 = var5_5 = 0;
        if (this.mCannedTextResponses == null) {
            var6_6 = var5_5;
            if (var1_1.getCannedSmsResponses() != null) {
                var6_6 = var5_5;
                if (!var1_1.getCannedSmsResponses().isEmpty()) {
                    this.mCannedTextResponses = Collections.unmodifiableList(var1_1.getCannedSmsResponses());
                    var6_6 = 1;
                }
            }
        }
        var3_3 = var1_1.getVideoCallImpl(this.mCallingPackage, this.mTargetSdkVersion);
        var7_7 = var1_1.isVideoCallProviderChanged() != false && Objects.equals(this.mVideoCallImpl, var3_3) == false;
        if (var7_7) {
            this.mVideoCallImpl = var3_3;
        }
        if ((var3_3 = this.mVideoCallImpl) != null) {
            var3_3.setVideoState(this.getDetails().getVideoState());
        }
        if (var8_8 = this.mState != (var5_5 = var1_1.getState())) {
            this.mState = var5_5;
        }
        if (var9_9 = Objects.equals(this.mParentId, var3_3 = var1_1.getParentCallId()) ^ true) {
            this.mParentId = var3_3;
        }
        if (var10_10 = Objects.equals(var1_1.getChildCallIds(), this.mChildrenIds) ^ true) {
            this.mChildrenIds.clear();
            this.mChildrenIds.addAll(var1_1.getChildCallIds());
            this.mChildrenCached = false;
        }
        var3_3 = var1_1.getConferenceableCallIds();
        var11_11 = new ArrayList<Call>(var3_3.size());
        var3_3 = var3_3.iterator();
        do {
            var12_12 = var2_2;
            if (!var3_3.hasNext()) break;
            var13_13 = (String)var3_3.next();
            if (!var12_12.containsKey(var13_13)) continue;
            var11_11.add((Call)var12_12.get(var13_13));
        } while (true);
        if (!Objects.equals(this.mConferenceableCalls, var11_11)) {
            this.mConferenceableCalls.clear();
            this.mConferenceableCalls.addAll(var11_11);
            this.fireConferenceableCallsChanged();
        }
        if (!var1_1.getIsRttCallChanged()) ** GOTO lbl-1000
        var14_14 = 0;
        if (this.mDetails.hasProperty(1024)) {
            var1_1 = var1_1.getParcelableRttCall();
            var3_3 = new InputStreamReader((InputStream)new ParcelFileDescriptor.AutoCloseInputStream(var1_1.getReceiveStream()), StandardCharsets.UTF_8);
            var2_2 = new OutputStreamWriter((OutputStream)new ParcelFileDescriptor.AutoCloseOutputStream(var1_1.getTransmitStream()), StandardCharsets.UTF_8);
            var2_2 = new RttCall(this.mTelecomCallId, (InputStreamReader)var3_3, (OutputStreamWriter)var2_2, var1_1.getRttMode(), this.mInCallAdapter);
            var1_1 = this.mRttCall;
            if (var1_1 == null) {
                var14_14 = 1;
                var5_5 = 0;
            } else {
                var5_5 = var1_1.getRttAudioMode() != var2_2.getRttAudioMode() ? 1 : 0;
            }
            this.mRttCall = var2_2;
            var15_15 = var5_5;
            var5_5 = var14_14;
            var14_14 = var15_15;
        } else lbl-1000: // 2 sources:
        {
            var15_16 = 0;
            var5_5 = var16_17 = 0;
            var14_14 = var15_16;
            if (this.mRttCall != null) {
                var5_5 = var16_17;
                var14_14 = var15_16;
                if (var1_1.getParcelableRttCall() == null) {
                    var5_5 = var16_17;
                    var14_14 = var15_16;
                    if (var1_1.getIsRttCallChanged()) {
                        this.mRttCall = null;
                        var5_5 = 1;
                        var14_14 = var15_16;
                    }
                }
            }
        }
        if (var8_8) {
            this.fireStateChanged(this.mState);
        }
        if (var4_4) {
            this.fireDetailsChanged(this.mDetails);
        }
        if (var6_6 != 0) {
            this.fireCannedTextResponsesLoaded(this.mCannedTextResponses);
        }
        if (var7_7) {
            this.fireVideoCallChanged(this.mVideoCallImpl);
        }
        if (var9_9) {
            this.fireParentChanged(this.getParent());
        }
        if (var10_10) {
            this.fireChildrenChanged(this.getChildren());
        }
        if (var5_5 != 0) {
            var17_18 = this.mRttCall != null;
            this.fireOnIsRttChanged(var17_18, this.mRttCall);
        }
        if (var14_14 != 0) {
            this.fireOnRttModeChanged(this.mRttCall.getRttAudioMode());
        }
        if (this.mState != 7) return;
        this.fireCallDestroyed();
    }

    public boolean isRttActive() {
        boolean bl = this.mRttCall != null && this.mDetails.hasProperty(1024);
        return bl;
    }

    public void mergeConference() {
        this.mInCallAdapter.mergeConference(this.mTelecomCallId);
    }

    public void phoneAccountSelected(PhoneAccountHandle phoneAccountHandle, boolean bl) {
        this.mInCallAdapter.phoneAccountSelected(this.mTelecomCallId, phoneAccountHandle, bl);
    }

    public void playDtmfTone(char c) {
        this.mInCallAdapter.playDtmfTone(this.mTelecomCallId, c);
    }

    public void postDialContinue(boolean bl) {
        this.mInCallAdapter.postDialContinue(this.mTelecomCallId, bl);
    }

    public void pullExternalCall() {
        if (!this.mDetails.hasProperty(64)) {
            return;
        }
        this.mInCallAdapter.pullExternalCall(this.mTelecomCallId);
    }

    public final void putExtra(String string2, int n) {
        if (this.mExtras == null) {
            this.mExtras = new Bundle();
        }
        this.mExtras.putInt(string2, n);
        this.mInCallAdapter.putExtra(this.mTelecomCallId, string2, n);
    }

    public final void putExtra(String string2, String string3) {
        if (this.mExtras == null) {
            this.mExtras = new Bundle();
        }
        this.mExtras.putString(string2, string3);
        this.mInCallAdapter.putExtra(this.mTelecomCallId, string2, string3);
    }

    public final void putExtra(String string2, boolean bl) {
        if (this.mExtras == null) {
            this.mExtras = new Bundle();
        }
        this.mExtras.putBoolean(string2, bl);
        this.mInCallAdapter.putExtra(this.mTelecomCallId, string2, bl);
    }

    public final void putExtras(Bundle bundle) {
        if (bundle == null) {
            return;
        }
        if (this.mExtras == null) {
            this.mExtras = new Bundle();
        }
        this.mExtras.putAll(bundle);
        this.mInCallAdapter.putExtras(this.mTelecomCallId, bundle);
    }

    public void registerCallback(Callback callback) {
        this.registerCallback(callback, new Handler());
    }

    public void registerCallback(Callback callback, Handler handler) {
        this.unregisterCallback(callback);
        if (callback != null && handler != null && this.mState != 7) {
            this.mCallbackRecords.add(new CallbackRecord<Callback>(callback, handler));
        }
    }

    public void reject(boolean bl, String string2) {
        this.mInCallAdapter.rejectCall(this.mTelecomCallId, bl, string2);
    }

    public final void removeExtras(List<String> list) {
        if (this.mExtras != null) {
            for (String string2 : list) {
                this.mExtras.remove(string2);
            }
            if (this.mExtras.size() == 0) {
                this.mExtras = null;
            }
        }
        this.mInCallAdapter.removeExtras(this.mTelecomCallId, list);
    }

    public final void removeExtras(String ... arrstring) {
        this.removeExtras(Arrays.asList(arrstring));
    }

    @SystemApi
    @Deprecated
    public void removeListener(Listener listener) {
        this.unregisterCallback(listener);
    }

    public void respondToRttRequest(int n, boolean bl) {
        this.mInCallAdapter.respondToRttRequest(this.mTelecomCallId, n, bl);
    }

    public void sendCallEvent(String string2, Bundle bundle) {
        this.mInCallAdapter.sendCallEvent(this.mTelecomCallId, string2, this.mTargetSdkVersion, bundle);
    }

    public void sendRttRequest() {
        this.mInCallAdapter.sendRttRequest(this.mTelecomCallId);
    }

    public void splitFromConference() {
        this.mInCallAdapter.splitFromConference(this.mTelecomCallId);
    }

    public void stopDtmfTone() {
        this.mInCallAdapter.stopDtmfTone(this.mTelecomCallId);
    }

    public void stopRtt() {
        this.mInCallAdapter.stopRtt(this.mTelecomCallId);
    }

    public void swapConference() {
        this.mInCallAdapter.swapConference(this.mTelecomCallId);
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Call [id: ");
        stringBuilder.append(this.mTelecomCallId);
        stringBuilder.append(", state: ");
        stringBuilder.append(Call.stateToString(this.mState));
        stringBuilder.append(", details: ");
        stringBuilder.append(this.mDetails);
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    public void unhold() {
        this.mInCallAdapter.unholdCall(this.mTelecomCallId);
    }

    public void unregisterCallback(Callback callback) {
        if (callback != null && this.mState != 7) {
            for (CallbackRecord<Callback> callbackRecord : this.mCallbackRecords) {
                if (callbackRecord.getCallback() != callback) continue;
                this.mCallbackRecords.remove(callbackRecord);
                break;
            }
        }
    }

    public static abstract class Callback {
        public static final int HANDOVER_FAILURE_DEST_APP_REJECTED = 1;
        public static final int HANDOVER_FAILURE_NOT_SUPPORTED = 2;
        public static final int HANDOVER_FAILURE_ONGOING_EMERGENCY_CALL = 4;
        public static final int HANDOVER_FAILURE_UNKNOWN = 5;
        public static final int HANDOVER_FAILURE_USER_REJECTED = 3;

        public void onCallDestroyed(Call call) {
        }

        public void onCannedTextResponsesLoaded(Call call, List<String> list) {
        }

        public void onChildrenChanged(Call call, List<Call> list) {
        }

        public void onConferenceableCallsChanged(Call call, List<Call> list) {
        }

        public void onConnectionEvent(Call call, String string2, Bundle bundle) {
        }

        public void onDetailsChanged(Call call, Details details) {
        }

        public void onHandoverComplete(Call call) {
        }

        public void onHandoverFailed(Call call, int n) {
        }

        public void onParentChanged(Call call, Call call2) {
        }

        public void onPostDialWait(Call call, String string2) {
        }

        public void onRttInitiationFailure(Call call, int n) {
        }

        public void onRttModeChanged(Call call, int n) {
        }

        public void onRttRequest(Call call, int n) {
        }

        public void onRttStatusChanged(Call call, boolean bl, RttCall rttCall) {
        }

        public void onStateChanged(Call call, int n) {
        }

        public void onVideoCallChanged(Call call, InCallService.VideoCall videoCall) {
        }

        @Retention(value=RetentionPolicy.SOURCE)
        public static @interface HandoverFailureErrors {
        }

    }

    public static class Details {
        public static final int CAPABILITY_CANNOT_DOWNGRADE_VIDEO_TO_AUDIO = 4194304;
        public static final int CAPABILITY_CAN_PAUSE_VIDEO = 1048576;
        public static final int CAPABILITY_CAN_PULL_CALL = 8388608;
        public static final int CAPABILITY_CAN_SEND_RESPONSE_VIA_CONNECTION = 2097152;
        @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=119305590L)
        public static final int CAPABILITY_CAN_UPGRADE_TO_VIDEO = 524288;
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
        public static final int CAPABILITY_SUPPORT_DEFLECT = 16777216;
        public static final int CAPABILITY_SUPPORT_HOLD = 2;
        public static final int CAPABILITY_SWAP_CONFERENCE = 8;
        public static final int CAPABILITY_UNUSED_1 = 16;
        public static final int DIRECTION_INCOMING = 0;
        public static final int DIRECTION_OUTGOING = 1;
        public static final int DIRECTION_UNKNOWN = -1;
        public static final int PROPERTY_ASSISTED_DIALING_USED = 512;
        public static final int PROPERTY_CONFERENCE = 1;
        public static final int PROPERTY_EMERGENCY_CALLBACK_MODE = 4;
        public static final int PROPERTY_ENTERPRISE_CALL = 32;
        public static final int PROPERTY_GENERIC_CONFERENCE = 2;
        public static final int PROPERTY_HAS_CDMA_VOICE_PRIVACY = 128;
        public static final int PROPERTY_HIGH_DEF_AUDIO = 16;
        public static final int PROPERTY_IS_EXTERNAL_CALL = 64;
        public static final int PROPERTY_NETWORK_IDENTIFIED_EMERGENCY_CALL = 2048;
        public static final int PROPERTY_RTT = 1024;
        public static final int PROPERTY_SELF_MANAGED = 256;
        public static final int PROPERTY_VOIP_AUDIO_MODE = 4096;
        public static final int PROPERTY_WIFI = 8;
        private final PhoneAccountHandle mAccountHandle;
        private final int mCallCapabilities;
        private final int mCallDirection;
        private final int mCallProperties;
        private final String mCallerDisplayName;
        private final int mCallerDisplayNamePresentation;
        private final long mConnectTimeMillis;
        private final long mCreationTimeMillis;
        private final DisconnectCause mDisconnectCause;
        private final Bundle mExtras;
        private final GatewayInfo mGatewayInfo;
        private final Uri mHandle;
        private final int mHandlePresentation;
        private final Bundle mIntentExtras;
        private final StatusHints mStatusHints;
        private final int mSupportedAudioRoutes;
        private final String mTelecomCallId;
        private final int mVideoState;

        public Details(String string2, Uri uri, int n, String string3, int n2, PhoneAccountHandle phoneAccountHandle, int n3, int n4, DisconnectCause disconnectCause, long l, GatewayInfo gatewayInfo, int n5, StatusHints statusHints, Bundle bundle, Bundle bundle2, long l2, int n6) {
            this.mSupportedAudioRoutes = 15;
            this.mTelecomCallId = string2;
            this.mHandle = uri;
            this.mHandlePresentation = n;
            this.mCallerDisplayName = string3;
            this.mCallerDisplayNamePresentation = n2;
            this.mAccountHandle = phoneAccountHandle;
            this.mCallCapabilities = n3;
            this.mCallProperties = n4;
            this.mDisconnectCause = disconnectCause;
            this.mConnectTimeMillis = l;
            this.mGatewayInfo = gatewayInfo;
            this.mVideoState = n5;
            this.mStatusHints = statusHints;
            this.mExtras = bundle;
            this.mIntentExtras = bundle2;
            this.mCreationTimeMillis = l2;
            this.mCallDirection = n6;
        }

        public static boolean can(int n, int n2) {
            boolean bl = (n & n2) == n2;
            return bl;
        }

        public static String capabilitiesToString(int n) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("[Capabilities:");
            if (Details.can(n, 1)) {
                stringBuilder.append(" CAPABILITY_HOLD");
            }
            if (Details.can(n, 2)) {
                stringBuilder.append(" CAPABILITY_SUPPORT_HOLD");
            }
            if (Details.can(n, 4)) {
                stringBuilder.append(" CAPABILITY_MERGE_CONFERENCE");
            }
            if (Details.can(n, 8)) {
                stringBuilder.append(" CAPABILITY_SWAP_CONFERENCE");
            }
            if (Details.can(n, 32)) {
                stringBuilder.append(" CAPABILITY_RESPOND_VIA_TEXT");
            }
            if (Details.can(n, 64)) {
                stringBuilder.append(" CAPABILITY_MUTE");
            }
            if (Details.can(n, 128)) {
                stringBuilder.append(" CAPABILITY_MANAGE_CONFERENCE");
            }
            if (Details.can(n, 256)) {
                stringBuilder.append(" CAPABILITY_SUPPORTS_VT_LOCAL_RX");
            }
            if (Details.can(n, 512)) {
                stringBuilder.append(" CAPABILITY_SUPPORTS_VT_LOCAL_TX");
            }
            if (Details.can(n, 768)) {
                stringBuilder.append(" CAPABILITY_SUPPORTS_VT_LOCAL_BIDIRECTIONAL");
            }
            if (Details.can(n, 1024)) {
                stringBuilder.append(" CAPABILITY_SUPPORTS_VT_REMOTE_RX");
            }
            if (Details.can(n, 2048)) {
                stringBuilder.append(" CAPABILITY_SUPPORTS_VT_REMOTE_TX");
            }
            if (Details.can(n, 4194304)) {
                stringBuilder.append(" CAPABILITY_CANNOT_DOWNGRADE_VIDEO_TO_AUDIO");
            }
            if (Details.can(n, 3072)) {
                stringBuilder.append(" CAPABILITY_SUPPORTS_VT_REMOTE_BIDIRECTIONAL");
            }
            if (Details.can(n, 262144)) {
                stringBuilder.append(" CAPABILITY_SPEED_UP_MT_AUDIO");
            }
            if (Details.can(n, 524288)) {
                stringBuilder.append(" CAPABILITY_CAN_UPGRADE_TO_VIDEO");
            }
            if (Details.can(n, 1048576)) {
                stringBuilder.append(" CAPABILITY_CAN_PAUSE_VIDEO");
            }
            if (Details.can(n, 8388608)) {
                stringBuilder.append(" CAPABILITY_CAN_PULL_CALL");
            }
            if (Details.can(n, 16777216)) {
                stringBuilder.append(" CAPABILITY_SUPPORT_DEFLECT");
            }
            stringBuilder.append("]");
            return stringBuilder.toString();
        }

        public static Details createFromParcelableCall(ParcelableCall parcelableCall) {
            return new Details(parcelableCall.getId(), parcelableCall.getHandle(), parcelableCall.getHandlePresentation(), parcelableCall.getCallerDisplayName(), parcelableCall.getCallerDisplayNamePresentation(), parcelableCall.getAccountHandle(), parcelableCall.getCapabilities(), parcelableCall.getProperties(), parcelableCall.getDisconnectCause(), parcelableCall.getConnectTimeMillis(), parcelableCall.getGatewayInfo(), parcelableCall.getVideoState(), parcelableCall.getStatusHints(), parcelableCall.getExtras(), parcelableCall.getIntentExtras(), parcelableCall.getCreationTimeMillis(), parcelableCall.getCallDirection());
        }

        public static boolean hasProperty(int n, int n2) {
            boolean bl = (n & n2) == n2;
            return bl;
        }

        public static String propertiesToString(int n) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("[Properties:");
            if (Details.hasProperty(n, 1)) {
                stringBuilder.append(" PROPERTY_CONFERENCE");
            }
            if (Details.hasProperty(n, 2)) {
                stringBuilder.append(" PROPERTY_GENERIC_CONFERENCE");
            }
            if (Details.hasProperty(n, 8)) {
                stringBuilder.append(" PROPERTY_WIFI");
            }
            if (Details.hasProperty(n, 16)) {
                stringBuilder.append(" PROPERTY_HIGH_DEF_AUDIO");
            }
            if (Details.hasProperty(n, 4)) {
                stringBuilder.append(" PROPERTY_EMERGENCY_CALLBACK_MODE");
            }
            if (Details.hasProperty(n, 64)) {
                stringBuilder.append(" PROPERTY_IS_EXTERNAL_CALL");
            }
            if (Details.hasProperty(n, 128)) {
                stringBuilder.append(" PROPERTY_HAS_CDMA_VOICE_PRIVACY");
            }
            if (Details.hasProperty(n, 512)) {
                stringBuilder.append(" PROPERTY_ASSISTED_DIALING_USED");
            }
            if (Details.hasProperty(n, 2048)) {
                stringBuilder.append(" PROPERTY_NETWORK_IDENTIFIED_EMERGENCY_CALL");
            }
            if (Details.hasProperty(n, 1024)) {
                stringBuilder.append(" PROPERTY_RTT");
            }
            if (Details.hasProperty(n, 4096)) {
                stringBuilder.append(" PROPERTY_VOIP_AUDIO_MODE");
            }
            stringBuilder.append("]");
            return stringBuilder.toString();
        }

        public boolean can(int n) {
            return Details.can(this.mCallCapabilities, n);
        }

        public boolean equals(Object object) {
            boolean bl = object instanceof Details;
            boolean bl2 = false;
            if (bl) {
                object = (Details)object;
                if (Objects.equals(this.mHandle, ((Details)object).mHandle) && Objects.equals(this.mHandlePresentation, ((Details)object).mHandlePresentation) && Objects.equals(this.mCallerDisplayName, ((Details)object).mCallerDisplayName) && Objects.equals(this.mCallerDisplayNamePresentation, ((Details)object).mCallerDisplayNamePresentation) && Objects.equals(this.mAccountHandle, ((Details)object).mAccountHandle) && Objects.equals(this.mCallCapabilities, ((Details)object).mCallCapabilities) && Objects.equals(this.mCallProperties, ((Details)object).mCallProperties) && Objects.equals(this.mDisconnectCause, ((Details)object).mDisconnectCause) && Objects.equals(this.mConnectTimeMillis, ((Details)object).mConnectTimeMillis) && Objects.equals(this.mGatewayInfo, ((Details)object).mGatewayInfo) && Objects.equals(this.mVideoState, ((Details)object).mVideoState) && Objects.equals(this.mStatusHints, ((Details)object).mStatusHints) && Call.areBundlesEqual(this.mExtras, ((Details)object).mExtras) && Call.areBundlesEqual(this.mIntentExtras, ((Details)object).mIntentExtras) && Objects.equals(this.mCreationTimeMillis, ((Details)object).mCreationTimeMillis) && Objects.equals(this.mCallDirection, ((Details)object).mCallDirection)) {
                    bl2 = true;
                }
                return bl2;
            }
            return false;
        }

        public PhoneAccountHandle getAccountHandle() {
            return this.mAccountHandle;
        }

        public int getCallCapabilities() {
            return this.mCallCapabilities;
        }

        public int getCallDirection() {
            return this.mCallDirection;
        }

        public int getCallProperties() {
            return this.mCallProperties;
        }

        public String getCallerDisplayName() {
            return this.mCallerDisplayName;
        }

        public int getCallerDisplayNamePresentation() {
            return this.mCallerDisplayNamePresentation;
        }

        public final long getConnectTimeMillis() {
            return this.mConnectTimeMillis;
        }

        public long getCreationTimeMillis() {
            return this.mCreationTimeMillis;
        }

        public DisconnectCause getDisconnectCause() {
            return this.mDisconnectCause;
        }

        public Bundle getExtras() {
            return this.mExtras;
        }

        public GatewayInfo getGatewayInfo() {
            return this.mGatewayInfo;
        }

        public Uri getHandle() {
            return this.mHandle;
        }

        public int getHandlePresentation() {
            return this.mHandlePresentation;
        }

        public Bundle getIntentExtras() {
            return this.mIntentExtras;
        }

        public StatusHints getStatusHints() {
            return this.mStatusHints;
        }

        public int getSupportedAudioRoutes() {
            return 15;
        }

        public String getTelecomCallId() {
            return this.mTelecomCallId;
        }

        public int getVideoState() {
            return this.mVideoState;
        }

        public boolean hasProperty(int n) {
            return Details.hasProperty(this.mCallProperties, n);
        }

        public int hashCode() {
            return Objects.hash(this.mHandle, this.mHandlePresentation, this.mCallerDisplayName, this.mCallerDisplayNamePresentation, this.mAccountHandle, this.mCallCapabilities, this.mCallProperties, this.mDisconnectCause, this.mConnectTimeMillis, this.mGatewayInfo, this.mVideoState, this.mStatusHints, this.mExtras, this.mIntentExtras, this.mCreationTimeMillis, this.mCallDirection);
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("[id: ");
            stringBuilder.append(this.mTelecomCallId);
            stringBuilder.append(", pa: ");
            stringBuilder.append(this.mAccountHandle);
            stringBuilder.append(", hdl: ");
            stringBuilder.append(Log.piiHandle(this.mHandle));
            stringBuilder.append(", hdlPres: ");
            stringBuilder.append(this.mHandlePresentation);
            stringBuilder.append(", videoState: ");
            stringBuilder.append(VideoProfile.videoStateToString(this.mVideoState));
            stringBuilder.append(", caps: ");
            stringBuilder.append(Details.capabilitiesToString(this.mCallCapabilities));
            stringBuilder.append(", props: ");
            stringBuilder.append(Details.propertiesToString(this.mCallProperties));
            stringBuilder.append("]");
            return stringBuilder.toString();
        }

        @Retention(value=RetentionPolicy.SOURCE)
        public static @interface CallDirection {
        }

    }

    @SystemApi
    @Deprecated
    public static abstract class Listener
    extends Callback {
    }

    public static final class RttCall {
        private static final int READ_BUFFER_SIZE = 1000;
        public static final int RTT_MODE_FULL = 1;
        public static final int RTT_MODE_HCO = 2;
        public static final int RTT_MODE_INVALID = 0;
        public static final int RTT_MODE_VCO = 3;
        private final InCallAdapter mInCallAdapter;
        private char[] mReadBuffer = new char[1000];
        private InputStreamReader mReceiveStream;
        private int mRttMode;
        private final String mTelecomCallId;
        private OutputStreamWriter mTransmitStream;

        public RttCall(String string2, InputStreamReader inputStreamReader, OutputStreamWriter outputStreamWriter, int n, InCallAdapter inCallAdapter) {
            this.mTelecomCallId = string2;
            this.mReceiveStream = inputStreamReader;
            this.mTransmitStream = outputStreamWriter;
            this.mRttMode = n;
            this.mInCallAdapter = inCallAdapter;
        }

        public void close() {
            try {
                this.mReceiveStream.close();
            }
            catch (IOException iOException) {
                // empty catch block
            }
            try {
                this.mTransmitStream.close();
            }
            catch (IOException iOException) {
                // empty catch block
            }
        }

        public int getRttAudioMode() {
            return this.mRttMode;
        }

        public String read() {
            int n;
            block3 : {
                try {
                    n = this.mReceiveStream.read(this.mReadBuffer, 0, 1000);
                    if (n >= 0) break block3;
                    return null;
                }
                catch (IOException iOException) {
                    Log.w(this, "Exception encountered when reading from InputStreamReader: %s", iOException);
                    return null;
                }
            }
            String string2 = new String(this.mReadBuffer, 0, n);
            return string2;
        }

        public String readImmediately() throws IOException {
            if (this.mReceiveStream.ready()) {
                int n = this.mReceiveStream.read(this.mReadBuffer, 0, 1000);
                if (n < 0) {
                    return null;
                }
                return new String(this.mReadBuffer, 0, n);
            }
            return null;
        }

        public void setRttMode(int n) {
            this.mInCallAdapter.setRttMode(this.mTelecomCallId, n);
        }

        public void write(String string2) throws IOException {
            this.mTransmitStream.write(string2);
            this.mTransmitStream.flush();
        }

        @Retention(value=RetentionPolicy.SOURCE)
        public static @interface RttAudioMode {
        }

    }

}

