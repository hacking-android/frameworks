/*
 * Decompiled with CFR 0.145.
 */
package android.telecom;

import android.annotation.SystemApi;
import android.annotation.UnsupportedAppUsage;
import android.os.Bundle;
import android.telecom.AudioState;
import android.telecom.Call;
import android.telecom.CallAudioState;
import android.telecom.InCallAdapter;
import android.telecom.InCallService;
import android.telecom.Log;
import android.telecom.ParcelableCall;
import android.util.ArrayMap;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;

@SystemApi
@Deprecated
public final class Phone {
    private CallAudioState mCallAudioState;
    private final Map<String, Call> mCallByTelecomCallId = new ArrayMap<String, Call>();
    private final String mCallingPackage;
    private final List<Call> mCalls = new CopyOnWriteArrayList<Call>();
    private boolean mCanAddCall = true;
    private final InCallAdapter mInCallAdapter;
    private final List<Listener> mListeners = new CopyOnWriteArrayList<Listener>();
    private final int mTargetSdkVersion;
    private final List<Call> mUnmodifiableCalls = Collections.unmodifiableList(this.mCalls);

    Phone(InCallAdapter inCallAdapter, String string2, int n) {
        this.mInCallAdapter = inCallAdapter;
        this.mCallingPackage = string2;
        this.mTargetSdkVersion = n;
    }

    private void checkCallTree(ParcelableCall parcelableCall) {
        if (parcelableCall.getChildCallIds() != null) {
            for (int i = 0; i < parcelableCall.getChildCallIds().size(); ++i) {
                if (this.mCallByTelecomCallId.containsKey(parcelableCall.getChildCallIds().get(i))) continue;
                Log.wtf((Object)this, "ParcelableCall %s has nonexistent child %s", parcelableCall.getId(), parcelableCall.getChildCallIds().get(i));
            }
        }
    }

    private void fireBringToForeground(boolean bl) {
        Iterator<Listener> iterator = this.mListeners.iterator();
        while (iterator.hasNext()) {
            iterator.next().onBringToForeground(this, bl);
        }
    }

    private void fireCallAdded(Call call) {
        Iterator<Listener> iterator = this.mListeners.iterator();
        while (iterator.hasNext()) {
            iterator.next().onCallAdded(this, call);
        }
    }

    private void fireCallAudioStateChanged(CallAudioState callAudioState) {
        for (Listener listener : this.mListeners) {
            listener.onCallAudioStateChanged(this, callAudioState);
            listener.onAudioStateChanged(this, new AudioState(callAudioState));
        }
    }

    private void fireCallRemoved(Call call) {
        Iterator<Listener> iterator = this.mListeners.iterator();
        while (iterator.hasNext()) {
            iterator.next().onCallRemoved(this, call);
        }
    }

    private void fireCanAddCallChanged(boolean bl) {
        Iterator<Listener> iterator = this.mListeners.iterator();
        while (iterator.hasNext()) {
            iterator.next().onCanAddCallChanged(this, bl);
        }
    }

    private void fireSilenceRinger() {
        Iterator<Listener> iterator = this.mListeners.iterator();
        while (iterator.hasNext()) {
            iterator.next().onSilenceRinger(this);
        }
    }

    public final void addListener(Listener listener) {
        this.mListeners.add(listener);
    }

    public final boolean canAddCall() {
        return this.mCanAddCall;
    }

    final void destroy() {
        for (Call call : this.mCalls) {
            InCallService.VideoCall videoCall = call.getVideoCall();
            if (videoCall != null) {
                videoCall.destroy();
            }
            if (call.getState() == 7) continue;
            call.internalSetDisconnected();
        }
    }

    @Deprecated
    public final AudioState getAudioState() {
        return new AudioState(this.mCallAudioState);
    }

    public final CallAudioState getCallAudioState() {
        return this.mCallAudioState;
    }

    public final List<Call> getCalls() {
        return this.mUnmodifiableCalls;
    }

    final void internalAddCall(ParcelableCall parcelableCall) {
        Call call = new Call(this, parcelableCall.getId(), this.mInCallAdapter, parcelableCall.getState(), this.mCallingPackage, this.mTargetSdkVersion);
        this.mCallByTelecomCallId.put(parcelableCall.getId(), call);
        this.mCalls.add(call);
        this.checkCallTree(parcelableCall);
        call.internalUpdate(parcelableCall, this.mCallByTelecomCallId);
        this.fireCallAdded(call);
    }

    final void internalBringToForeground(boolean bl) {
        this.fireBringToForeground(bl);
    }

    final void internalCallAudioStateChanged(CallAudioState callAudioState) {
        if (!Objects.equals(this.mCallAudioState, callAudioState)) {
            this.mCallAudioState = callAudioState;
            this.fireCallAudioStateChanged(callAudioState);
        }
    }

    final Call internalGetCallByTelecomId(String string2) {
        return this.mCallByTelecomCallId.get(string2);
    }

    final void internalOnConnectionEvent(String object, String string2, Bundle bundle) {
        if ((object = this.mCallByTelecomCallId.get(object)) != null) {
            ((Call)object).internalOnConnectionEvent(string2, bundle);
        }
    }

    final void internalOnHandoverComplete(String object) {
        if ((object = this.mCallByTelecomCallId.get(object)) != null) {
            ((Call)object).internalOnHandoverComplete();
        }
    }

    final void internalOnHandoverFailed(String object, int n) {
        if ((object = this.mCallByTelecomCallId.get(object)) != null) {
            ((Call)object).internalOnHandoverFailed(n);
        }
    }

    final void internalOnRttInitiationFailure(String object, int n) {
        if ((object = this.mCallByTelecomCallId.get(object)) != null) {
            ((Call)object).internalOnRttInitiationFailure(n);
        }
    }

    final void internalOnRttUpgradeRequest(String object, int n) {
        if ((object = this.mCallByTelecomCallId.get(object)) != null) {
            ((Call)object).internalOnRttUpgradeRequest(n);
        }
    }

    final void internalRemoveCall(Call call) {
        this.mCallByTelecomCallId.remove(call.internalGetCallId());
        this.mCalls.remove(call);
        InCallService.VideoCall videoCall = call.getVideoCall();
        if (videoCall != null) {
            videoCall.destroy();
        }
        this.fireCallRemoved(call);
    }

    final void internalSetCanAddCall(boolean bl) {
        if (this.mCanAddCall != bl) {
            this.mCanAddCall = bl;
            this.fireCanAddCallChanged(bl);
        }
    }

    final void internalSetPostDialWait(String object, String string2) {
        if ((object = this.mCallByTelecomCallId.get(object)) != null) {
            ((Call)object).internalSetPostDialWait(string2);
        }
    }

    final void internalSilenceRinger() {
        this.fireSilenceRinger();
    }

    final void internalUpdateCall(ParcelableCall parcelableCall) {
        Call call = this.mCallByTelecomCallId.get(parcelableCall.getId());
        if (call != null) {
            this.checkCallTree(parcelableCall);
            call.internalUpdate(parcelableCall, this.mCallByTelecomCallId);
        }
    }

    public final void removeListener(Listener listener) {
        if (listener != null) {
            this.mListeners.remove(listener);
        }
    }

    public void requestBluetoothAudio(String string2) {
        this.mInCallAdapter.requestBluetoothAudio(string2);
    }

    public final void setAudioRoute(int n) {
        this.mInCallAdapter.setAudioRoute(n);
    }

    public final void setMuted(boolean bl) {
        this.mInCallAdapter.mute(bl);
    }

    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=127403196L)
    public final void setProximitySensorOff(boolean bl) {
        this.mInCallAdapter.turnProximitySensorOff(bl);
    }

    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=127403196L)
    public final void setProximitySensorOn() {
        this.mInCallAdapter.turnProximitySensorOn();
    }

    public static abstract class Listener {
        @Deprecated
        public void onAudioStateChanged(Phone phone, AudioState audioState) {
        }

        public void onBringToForeground(Phone phone, boolean bl) {
        }

        public void onCallAdded(Phone phone, Call call) {
        }

        public void onCallAudioStateChanged(Phone phone, CallAudioState callAudioState) {
        }

        public void onCallRemoved(Phone phone, Call call) {
        }

        public void onCanAddCallChanged(Phone phone, boolean bl) {
        }

        public void onSilenceRinger(Phone phone) {
        }
    }

}

