/*
 * Decompiled with CFR 0.145.
 */
package android.telecom;

import android.app.Service;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.telecom.PhoneAccountHandle;
import com.android.internal.os.SomeArgs;
import com.android.internal.telecom.ICallRedirectionAdapter;
import com.android.internal.telecom.ICallRedirectionService;

public abstract class CallRedirectionService
extends Service {
    private static final int MSG_PLACE_CALL = 1;
    public static final String SERVICE_INTERFACE = "android.telecom.CallRedirectionService";
    private ICallRedirectionAdapter mCallRedirectionAdapter;
    private final Handler mHandler = new Handler(Looper.getMainLooper()){

        @Override
        public void handleMessage(Message message) {
            SomeArgs someArgs;
            if (message.what == 1) {
                someArgs = (SomeArgs)message.obj;
                CallRedirectionService.this.mCallRedirectionAdapter = (ICallRedirectionAdapter)someArgs.arg1;
                CallRedirectionService.this.onPlaceCall((Uri)someArgs.arg2, (PhoneAccountHandle)someArgs.arg3, (Boolean)someArgs.arg4);
            }
            return;
            finally {
                someArgs.recycle();
            }
        }
    };

    public final void cancelCall() {
        try {
            this.mCallRedirectionAdapter.cancelCall();
        }
        catch (RemoteException remoteException) {
            remoteException.rethrowAsRuntimeException();
        }
    }

    @Override
    public final IBinder onBind(Intent intent) {
        return new CallRedirectionBinder();
    }

    public abstract void onPlaceCall(Uri var1, PhoneAccountHandle var2, boolean var3);

    @Override
    public final boolean onUnbind(Intent intent) {
        return false;
    }

    public final void placeCallUnmodified() {
        try {
            this.mCallRedirectionAdapter.placeCallUnmodified();
        }
        catch (RemoteException remoteException) {
            remoteException.rethrowAsRuntimeException();
        }
    }

    public final void redirectCall(Uri uri, PhoneAccountHandle phoneAccountHandle, boolean bl) {
        try {
            this.mCallRedirectionAdapter.redirectCall(uri, phoneAccountHandle, bl);
        }
        catch (RemoteException remoteException) {
            remoteException.rethrowAsRuntimeException();
        }
    }

    private final class CallRedirectionBinder
    extends ICallRedirectionService.Stub {
        private CallRedirectionBinder() {
        }

        @Override
        public void placeCall(ICallRedirectionAdapter iCallRedirectionAdapter, Uri uri, PhoneAccountHandle phoneAccountHandle, boolean bl) {
            SomeArgs someArgs = SomeArgs.obtain();
            someArgs.arg1 = iCallRedirectionAdapter;
            someArgs.arg2 = uri;
            someArgs.arg3 = phoneAccountHandle;
            someArgs.arg4 = bl;
            CallRedirectionService.this.mHandler.obtainMessage(1, someArgs).sendToTarget();
        }
    }

}

