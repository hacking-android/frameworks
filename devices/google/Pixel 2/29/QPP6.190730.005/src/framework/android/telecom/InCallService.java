/*
 * Decompiled with CFR 0.145.
 */
package android.telecom;

import android.annotation.SystemApi;
import android.app.Service;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.telecom.AudioState;
import android.telecom.Call;
import android.telecom.CallAudioState;
import android.telecom.InCallAdapter;
import android.telecom.ParcelableCall;
import android.telecom.Phone;
import android.telecom.VideoProfile;
import android.view.Surface;
import com.android.internal.os.SomeArgs;
import com.android.internal.telecom.IInCallAdapter;
import com.android.internal.telecom.IInCallService;
import java.util.Collections;
import java.util.List;

public abstract class InCallService
extends Service {
    private static final int MSG_ADD_CALL = 2;
    private static final int MSG_BRING_TO_FOREGROUND = 6;
    private static final int MSG_ON_CALL_AUDIO_STATE_CHANGED = 5;
    private static final int MSG_ON_CAN_ADD_CALL_CHANGED = 7;
    private static final int MSG_ON_CONNECTION_EVENT = 9;
    private static final int MSG_ON_HANDOVER_COMPLETE = 13;
    private static final int MSG_ON_HANDOVER_FAILED = 12;
    private static final int MSG_ON_RTT_INITIATION_FAILURE = 11;
    private static final int MSG_ON_RTT_UPGRADE_REQUEST = 10;
    private static final int MSG_SET_IN_CALL_ADAPTER = 1;
    private static final int MSG_SET_POST_DIAL_WAIT = 4;
    private static final int MSG_SILENCE_RINGER = 8;
    private static final int MSG_UPDATE_CALL = 3;
    public static final String SERVICE_INTERFACE = "android.telecom.InCallService";
    private final Handler mHandler = new Handler(Looper.getMainLooper()){

        @Override
        public void handleMessage(Message object) {
            Object object2 = InCallService.this.mPhone;
            boolean bl = true;
            boolean bl2 = true;
            if (object2 == null && ((Message)object).what != 1) {
                return;
            }
            switch (((Message)object).what) {
                default: {
                    break;
                }
                case 13: {
                    object = (String)((Message)object).obj;
                    InCallService.this.mPhone.internalOnHandoverComplete((String)object);
                    break;
                }
                case 12: {
                    object2 = (String)((Message)object).obj;
                    int n = ((Message)object).arg1;
                    InCallService.this.mPhone.internalOnHandoverFailed((String)object2, n);
                    break;
                }
                case 11: {
                    object2 = (String)((Message)object).obj;
                    int n = ((Message)object).arg1;
                    InCallService.this.mPhone.internalOnRttInitiationFailure((String)object2, n);
                    break;
                }
                case 10: {
                    object2 = (String)((Message)object).obj;
                    int n = ((Message)object).arg1;
                    InCallService.this.mPhone.internalOnRttUpgradeRequest((String)object2, n);
                    break;
                }
                case 9: {
                    object = (SomeArgs)((Message)object).obj;
                    try {
                        String string2 = (String)((SomeArgs)object).arg1;
                        object2 = (String)((SomeArgs)object).arg2;
                        Bundle bundle = (Bundle)((SomeArgs)object).arg3;
                        InCallService.this.mPhone.internalOnConnectionEvent(string2, (String)object2, bundle);
                        break;
                    }
                    finally {
                        ((SomeArgs)object).recycle();
                    }
                }
                case 8: {
                    InCallService.this.mPhone.internalSilenceRinger();
                    break;
                }
                case 7: {
                    object2 = InCallService.this.mPhone;
                    if (((Message)object).arg1 != 1) {
                        bl2 = false;
                    }
                    ((Phone)object2).internalSetCanAddCall(bl2);
                    break;
                }
                case 6: {
                    object2 = InCallService.this.mPhone;
                    bl2 = ((Message)object).arg1 == 1 ? bl : false;
                    ((Phone)object2).internalBringToForeground(bl2);
                    break;
                }
                case 5: {
                    InCallService.this.mPhone.internalCallAudioStateChanged((CallAudioState)((Message)object).obj);
                    break;
                }
                case 4: {
                    object = (SomeArgs)((Message)object).obj;
                    try {
                        object2 = (String)((SomeArgs)object).arg1;
                        String string3 = (String)((SomeArgs)object).arg2;
                        InCallService.this.mPhone.internalSetPostDialWait((String)object2, string3);
                        break;
                    }
                    finally {
                        ((SomeArgs)object).recycle();
                    }
                }
                case 3: {
                    InCallService.this.mPhone.internalUpdateCall((ParcelableCall)((Message)object).obj);
                    break;
                }
                case 2: {
                    InCallService.this.mPhone.internalAddCall((ParcelableCall)((Message)object).obj);
                    break;
                }
                case 1: {
                    object2 = InCallService.this.getApplicationContext().getOpPackageName();
                    InCallService.this.mPhone = new Phone(new InCallAdapter((IInCallAdapter)((Message)object).obj), (String)object2, InCallService.this.getApplicationContext().getApplicationInfo().targetSdkVersion);
                    InCallService.this.mPhone.addListener(InCallService.this.mPhoneListener);
                    object = InCallService.this;
                    ((InCallService)object).onPhoneCreated(((InCallService)object).mPhone);
                }
            }
        }
    };
    private Phone mPhone;
    private Phone.Listener mPhoneListener = new Phone.Listener(){

        @Override
        public void onAudioStateChanged(Phone phone, AudioState audioState) {
            InCallService.this.onAudioStateChanged(audioState);
        }

        @Override
        public void onBringToForeground(Phone phone, boolean bl) {
            InCallService.this.onBringToForeground(bl);
        }

        @Override
        public void onCallAdded(Phone phone, Call call) {
            InCallService.this.onCallAdded(call);
        }

        @Override
        public void onCallAudioStateChanged(Phone phone, CallAudioState callAudioState) {
            InCallService.this.onCallAudioStateChanged(callAudioState);
        }

        @Override
        public void onCallRemoved(Phone phone, Call call) {
            InCallService.this.onCallRemoved(call);
        }

        @Override
        public void onCanAddCallChanged(Phone phone, boolean bl) {
            InCallService.this.onCanAddCallChanged(bl);
        }

        @Override
        public void onSilenceRinger(Phone phone) {
            InCallService.this.onSilenceRinger();
        }
    };

    public final boolean canAddCall() {
        Phone phone = this.mPhone;
        boolean bl = phone == null ? false : phone.canAddCall();
        return bl;
    }

    @Deprecated
    public final AudioState getAudioState() {
        Object object = this.mPhone;
        object = object == null ? null : ((Phone)object).getAudioState();
        return object;
    }

    public final CallAudioState getCallAudioState() {
        Object object = this.mPhone;
        object = object == null ? null : ((Phone)object).getCallAudioState();
        return object;
    }

    public final List<Call> getCalls() {
        Object object = this.mPhone;
        object = object == null ? Collections.emptyList() : ((Phone)object).getCalls();
        return object;
    }

    @SystemApi
    @Deprecated
    public Phone getPhone() {
        return this.mPhone;
    }

    @Deprecated
    public void onAudioStateChanged(AudioState audioState) {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return new InCallServiceBinder();
    }

    public void onBringToForeground(boolean bl) {
    }

    public void onCallAdded(Call call) {
    }

    public void onCallAudioStateChanged(CallAudioState callAudioState) {
    }

    public void onCallRemoved(Call call) {
    }

    public void onCanAddCallChanged(boolean bl) {
    }

    public void onConnectionEvent(Call call, String string2, Bundle bundle) {
    }

    @SystemApi
    @Deprecated
    public void onPhoneCreated(Phone phone) {
    }

    @SystemApi
    @Deprecated
    public void onPhoneDestroyed(Phone phone) {
    }

    public void onSilenceRinger() {
    }

    @Override
    public boolean onUnbind(Intent object) {
        if (this.mPhone != null) {
            object = this.mPhone;
            this.mPhone = null;
            ((Phone)object).destroy();
            ((Phone)object).removeListener(this.mPhoneListener);
            this.onPhoneDestroyed((Phone)object);
        }
        return false;
    }

    public final void requestBluetoothAudio(BluetoothDevice bluetoothDevice) {
        Phone phone = this.mPhone;
        if (phone != null) {
            phone.requestBluetoothAudio(bluetoothDevice.getAddress());
        }
    }

    public final void setAudioRoute(int n) {
        Phone phone = this.mPhone;
        if (phone != null) {
            phone.setAudioRoute(n);
        }
    }

    public final void setMuted(boolean bl) {
        Phone phone = this.mPhone;
        if (phone != null) {
            phone.setMuted(bl);
        }
    }

    private final class InCallServiceBinder
    extends IInCallService.Stub {
        private InCallServiceBinder() {
        }

        @Override
        public void addCall(ParcelableCall parcelableCall) {
            InCallService.this.mHandler.obtainMessage(2, parcelableCall).sendToTarget();
        }

        @Override
        public void bringToForeground(boolean bl) {
            InCallService.this.mHandler.obtainMessage(6, (int)bl, 0).sendToTarget();
        }

        @Override
        public void onCallAudioStateChanged(CallAudioState callAudioState) {
            InCallService.this.mHandler.obtainMessage(5, callAudioState).sendToTarget();
        }

        @Override
        public void onCanAddCallChanged(boolean bl) {
            InCallService.this.mHandler.obtainMessage(7, (int)bl, 0).sendToTarget();
        }

        @Override
        public void onConnectionEvent(String string2, String string3, Bundle bundle) {
            SomeArgs someArgs = SomeArgs.obtain();
            someArgs.arg1 = string2;
            someArgs.arg2 = string3;
            someArgs.arg3 = bundle;
            InCallService.this.mHandler.obtainMessage(9, someArgs).sendToTarget();
        }

        @Override
        public void onHandoverComplete(String string2) {
            InCallService.this.mHandler.obtainMessage(13, string2).sendToTarget();
        }

        @Override
        public void onHandoverFailed(String string2, int n) {
            InCallService.this.mHandler.obtainMessage(12, n, 0, string2).sendToTarget();
        }

        @Override
        public void onRttInitiationFailure(String string2, int n) {
            InCallService.this.mHandler.obtainMessage(11, n, 0, string2).sendToTarget();
        }

        @Override
        public void onRttUpgradeRequest(String string2, int n) {
            InCallService.this.mHandler.obtainMessage(10, n, 0, string2).sendToTarget();
        }

        @Override
        public void setInCallAdapter(IInCallAdapter iInCallAdapter) {
            InCallService.this.mHandler.obtainMessage(1, iInCallAdapter).sendToTarget();
        }

        @Override
        public void setPostDial(String string2, String string3) {
        }

        @Override
        public void setPostDialWait(String string2, String string3) {
            SomeArgs someArgs = SomeArgs.obtain();
            someArgs.arg1 = string2;
            someArgs.arg2 = string3;
            InCallService.this.mHandler.obtainMessage(4, someArgs).sendToTarget();
        }

        @Override
        public void silenceRinger() {
            InCallService.this.mHandler.obtainMessage(8).sendToTarget();
        }

        @Override
        public void updateCall(ParcelableCall parcelableCall) {
            InCallService.this.mHandler.obtainMessage(3, parcelableCall).sendToTarget();
        }
    }

    public static abstract class VideoCall {
        public abstract void destroy();

        public abstract void registerCallback(Callback var1);

        public abstract void registerCallback(Callback var1, Handler var2);

        public abstract void requestCallDataUsage();

        public abstract void requestCameraCapabilities();

        public abstract void sendSessionModifyRequest(VideoProfile var1);

        public abstract void sendSessionModifyResponse(VideoProfile var1);

        public abstract void setCamera(String var1);

        public abstract void setDeviceOrientation(int var1);

        public abstract void setDisplaySurface(Surface var1);

        public abstract void setPauseImage(Uri var1);

        public abstract void setPreviewSurface(Surface var1);

        public abstract void setZoom(float var1);

        public abstract void unregisterCallback(Callback var1);

        public static abstract class Callback {
            public abstract void onCallDataUsageChanged(long var1);

            public abstract void onCallSessionEvent(int var1);

            public abstract void onCameraCapabilitiesChanged(VideoProfile.CameraCapabilities var1);

            public abstract void onPeerDimensionsChanged(int var1, int var2);

            public abstract void onSessionModifyRequestReceived(VideoProfile var1);

            public abstract void onSessionModifyResponseReceived(int var1, VideoProfile var2, VideoProfile var3);

            public abstract void onVideoQualityChanged(int var1);
        }

    }

}

