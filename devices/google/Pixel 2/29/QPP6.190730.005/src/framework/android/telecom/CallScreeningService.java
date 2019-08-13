/*
 * Decompiled with CFR 0.145.
 */
package android.telecom;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.telecom.Call;
import android.telecom.Log;
import android.telecom.ParcelableCall;
import com.android.internal.os.SomeArgs;
import com.android.internal.telecom.ICallScreeningAdapter;
import com.android.internal.telecom.ICallScreeningService;

public abstract class CallScreeningService
extends Service {
    private static final int MSG_SCREEN_CALL = 1;
    public static final String SERVICE_INTERFACE = "android.telecom.CallScreeningService";
    private ICallScreeningAdapter mCallScreeningAdapter;
    private final Handler mHandler = new Handler(Looper.getMainLooper()){

        @Override
        public void handleMessage(Message object) {
            if (((Message)object).what == 1) {
                object = (SomeArgs)((Message)object).obj;
                CallScreeningService.this.mCallScreeningAdapter = (ICallScreeningAdapter)((SomeArgs)object).arg1;
                CallScreeningService.this.onScreenCall(Call.Details.createFromParcelableCall((ParcelableCall)((SomeArgs)object).arg2));
            }
            return;
            finally {
                ((SomeArgs)object).recycle();
            }
        }
    };

    @Override
    public IBinder onBind(Intent intent) {
        Log.v(this, "onBind", new Object[0]);
        return new CallScreeningBinder();
    }

    public abstract void onScreenCall(Call.Details var1);

    @Override
    public boolean onUnbind(Intent intent) {
        Log.v(this, "onUnbind", new Object[0]);
        return false;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public final void respondToCall(Call.Details object, CallResponse object2) {
        try {
            if (((CallResponse)object2).getDisallowCall()) {
                ICallScreeningAdapter iCallScreeningAdapter = this.mCallScreeningAdapter;
                object = ((Call.Details)object).getTelecomCallId();
                boolean bl = ((CallResponse)object2).getRejectCall();
                boolean bl2 = ((CallResponse)object2).getSkipCallLog();
                boolean bl3 = false;
                bl2 = !bl2;
                if (!((CallResponse)object2).getSkipNotification()) {
                    bl3 = true;
                }
                object2 = new ComponentName(this.getPackageName(), this.getClass().getName());
                iCallScreeningAdapter.disallowCall((String)object, bl, bl2, bl3, (ComponentName)object2);
                return;
            }
            if (((CallResponse)object2).getSilenceCall()) {
                this.mCallScreeningAdapter.silenceCall(((Call.Details)object).getTelecomCallId());
                return;
            }
            this.mCallScreeningAdapter.allowCall(((Call.Details)object).getTelecomCallId());
            return;
        }
        catch (RemoteException remoteException) {
            // empty catch block
        }
    }

    public static class CallResponse {
        private final boolean mShouldDisallowCall;
        private final boolean mShouldRejectCall;
        private final boolean mShouldSilenceCall;
        private final boolean mShouldSkipCallLog;
        private final boolean mShouldSkipNotification;

        private CallResponse(boolean bl, boolean bl2, boolean bl3, boolean bl4, boolean bl5) {
            if (!bl && (bl2 || bl4 || bl5)) {
                throw new IllegalStateException("Invalid response state for allowed call.");
            }
            this.mShouldDisallowCall = bl;
            this.mShouldRejectCall = bl2;
            this.mShouldSkipCallLog = bl4;
            this.mShouldSkipNotification = bl5;
            this.mShouldSilenceCall = bl3;
        }

        public boolean getDisallowCall() {
            return this.mShouldDisallowCall;
        }

        public boolean getRejectCall() {
            return this.mShouldRejectCall;
        }

        public boolean getSilenceCall() {
            return this.mShouldSilenceCall;
        }

        public boolean getSkipCallLog() {
            return this.mShouldSkipCallLog;
        }

        public boolean getSkipNotification() {
            return this.mShouldSkipNotification;
        }

        public static class Builder {
            private boolean mShouldDisallowCall;
            private boolean mShouldRejectCall;
            private boolean mShouldSilenceCall;
            private boolean mShouldSkipCallLog;
            private boolean mShouldSkipNotification;

            public CallResponse build() {
                return new CallResponse(this.mShouldDisallowCall, this.mShouldRejectCall, this.mShouldSilenceCall, this.mShouldSkipCallLog, this.mShouldSkipNotification);
            }

            public Builder setDisallowCall(boolean bl) {
                this.mShouldDisallowCall = bl;
                return this;
            }

            public Builder setRejectCall(boolean bl) {
                this.mShouldRejectCall = bl;
                return this;
            }

            public Builder setSilenceCall(boolean bl) {
                this.mShouldSilenceCall = bl;
                return this;
            }

            public Builder setSkipCallLog(boolean bl) {
                this.mShouldSkipCallLog = bl;
                return this;
            }

            public Builder setSkipNotification(boolean bl) {
                this.mShouldSkipNotification = bl;
                return this;
            }
        }

    }

    private final class CallScreeningBinder
    extends ICallScreeningService.Stub {
        private CallScreeningBinder() {
        }

        @Override
        public void screenCall(ICallScreeningAdapter iCallScreeningAdapter, ParcelableCall parcelableCall) {
            Log.v(this, "screenCall", new Object[0]);
            SomeArgs someArgs = SomeArgs.obtain();
            someArgs.arg1 = iCallScreeningAdapter;
            someArgs.arg2 = parcelableCall;
            CallScreeningService.this.mHandler.obtainMessage(1, someArgs).sendToTarget();
        }
    }

}

