/*
 * Decompiled with CFR 0.145.
 */
package android.speech;

import android.app.Service;
import android.content.Intent;
import android.content.PermissionChecker;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.speech.IRecognitionListener;
import android.speech.IRecognitionService;
import android.util.Log;
import java.lang.ref.WeakReference;
import java.util.Objects;

public abstract class RecognitionService
extends Service {
    private static final boolean DBG = false;
    private static final int MSG_CANCEL = 3;
    private static final int MSG_RESET = 4;
    private static final int MSG_START_LISTENING = 1;
    private static final int MSG_STOP_LISTENING = 2;
    public static final String SERVICE_INTERFACE = "android.speech.RecognitionService";
    public static final String SERVICE_META_DATA = "android.speech";
    private static final String TAG = "RecognitionService";
    private RecognitionServiceBinder mBinder = new RecognitionServiceBinder(this);
    private Callback mCurrentCallback = null;
    private final Handler mHandler = new Handler(){

        @Override
        public void handleMessage(Message object) {
            int n = ((Message)object).what;
            if (n != 1) {
                if (n != 2) {
                    if (n != 3) {
                        if (n == 4) {
                            RecognitionService.this.dispatchClearCallback();
                        }
                    } else {
                        RecognitionService.this.dispatchCancel((IRecognitionListener)((Message)object).obj);
                    }
                } else {
                    RecognitionService.this.dispatchStopListening((IRecognitionListener)((Message)object).obj);
                }
            } else {
                object = (StartListeningArgs)((Message)object).obj;
                RecognitionService.this.dispatchStartListening(((StartListeningArgs)object).mIntent, ((StartListeningArgs)object).mListener, ((StartListeningArgs)object).mCallingUid);
            }
        }
    };

    private boolean checkPermissions(IRecognitionListener iRecognitionListener) {
        if (PermissionChecker.checkCallingOrSelfPermission(this, "android.permission.RECORD_AUDIO") == 0) {
            return true;
        }
        try {
            Log.e(TAG, "call for recognition service without RECORD_AUDIO permissions");
            iRecognitionListener.onError(9);
        }
        catch (RemoteException remoteException) {
            Log.e(TAG, "sending ERROR_INSUFFICIENT_PERMISSIONS message failed", remoteException);
        }
        return false;
    }

    private void dispatchCancel(IRecognitionListener iRecognitionListener) {
        Callback callback = this.mCurrentCallback;
        if (callback != null) {
            if (callback.mListener.asBinder() != iRecognitionListener.asBinder()) {
                Log.w(TAG, "cancel called by client who did not call startListening - ignoring");
            } else {
                this.onCancel(this.mCurrentCallback);
                this.mCurrentCallback = null;
            }
        }
    }

    private void dispatchClearCallback() {
        this.mCurrentCallback = null;
    }

    private void dispatchStartListening(Intent intent, final IRecognitionListener iRecognitionListener, int n) {
        if (this.mCurrentCallback == null) {
            try {
                IBinder iBinder = iRecognitionListener.asBinder();
                IBinder.DeathRecipient deathRecipient = new IBinder.DeathRecipient(){

                    @Override
                    public void binderDied() {
                        RecognitionService.this.mHandler.sendMessage(RecognitionService.this.mHandler.obtainMessage(3, iRecognitionListener));
                    }
                };
                iBinder.linkToDeath(deathRecipient, 0);
                this.mCurrentCallback = new Callback(iRecognitionListener, n);
                this.onStartListening(intent, this.mCurrentCallback);
            }
            catch (RemoteException remoteException) {
                Log.e(TAG, "dead listener on startListening");
                return;
            }
        }
        try {
            iRecognitionListener.onError(8);
        }
        catch (RemoteException remoteException) {
            Log.d(TAG, "onError call from startListening failed");
        }
        Log.i(TAG, "concurrent startListening received - ignoring this call");
    }

    private void dispatchStopListening(IRecognitionListener iRecognitionListener) {
        try {
            if (this.mCurrentCallback == null) {
                iRecognitionListener.onError(5);
                Log.w(TAG, "stopListening called with no preceding startListening - ignoring");
            } else if (this.mCurrentCallback.mListener.asBinder() != iRecognitionListener.asBinder()) {
                iRecognitionListener.onError(8);
                Log.w(TAG, "stopListening called by other caller than startListening - ignoring");
            } else {
                this.onStopListening(this.mCurrentCallback);
            }
        }
        catch (RemoteException remoteException) {
            Log.d(TAG, "onError call from stopListening failed");
        }
    }

    @Override
    public final IBinder onBind(Intent intent) {
        return this.mBinder;
    }

    protected abstract void onCancel(Callback var1);

    @Override
    public void onDestroy() {
        this.mCurrentCallback = null;
        this.mBinder.clearReference();
        super.onDestroy();
    }

    protected abstract void onStartListening(Intent var1, Callback var2);

    protected abstract void onStopListening(Callback var1);

    public class Callback {
        private final int mCallingUid;
        private final IRecognitionListener mListener;

        private Callback(IRecognitionListener iRecognitionListener, int n) {
            this.mListener = iRecognitionListener;
            this.mCallingUid = n;
        }

        public void beginningOfSpeech() throws RemoteException {
            this.mListener.onBeginningOfSpeech();
        }

        public void bufferReceived(byte[] arrby) throws RemoteException {
            this.mListener.onBufferReceived(arrby);
        }

        public void endOfSpeech() throws RemoteException {
            this.mListener.onEndOfSpeech();
        }

        public void error(int n) throws RemoteException {
            Message.obtain(RecognitionService.this.mHandler, 4).sendToTarget();
            this.mListener.onError(n);
        }

        public int getCallingUid() {
            return this.mCallingUid;
        }

        public void partialResults(Bundle bundle) throws RemoteException {
            this.mListener.onPartialResults(bundle);
        }

        public void readyForSpeech(Bundle bundle) throws RemoteException {
            this.mListener.onReadyForSpeech(bundle);
        }

        public void results(Bundle bundle) throws RemoteException {
            Message.obtain(RecognitionService.this.mHandler, 4).sendToTarget();
            this.mListener.onResults(bundle);
        }

        public void rmsChanged(float f) throws RemoteException {
            this.mListener.onRmsChanged(f);
        }
    }

    private static final class RecognitionServiceBinder
    extends IRecognitionService.Stub {
        private final WeakReference<RecognitionService> mServiceRef;

        public RecognitionServiceBinder(RecognitionService recognitionService) {
            this.mServiceRef = new WeakReference<RecognitionService>(recognitionService);
        }

        @Override
        public void cancel(IRecognitionListener iRecognitionListener) {
            RecognitionService recognitionService = (RecognitionService)this.mServiceRef.get();
            if (recognitionService != null && recognitionService.checkPermissions(iRecognitionListener)) {
                recognitionService.mHandler.sendMessage(Message.obtain(recognitionService.mHandler, 3, iRecognitionListener));
            }
        }

        public void clearReference() {
            this.mServiceRef.clear();
        }

        @Override
        public void startListening(Intent intent, IRecognitionListener iRecognitionListener) {
            RecognitionService recognitionService = (RecognitionService)this.mServiceRef.get();
            if (recognitionService != null && recognitionService.checkPermissions(iRecognitionListener)) {
                Handler handler = recognitionService.mHandler;
                Handler handler2 = recognitionService.mHandler;
                Objects.requireNonNull(recognitionService);
                handler.sendMessage(Message.obtain(handler2, 1, recognitionService.new StartListeningArgs(intent, iRecognitionListener, Binder.getCallingUid())));
            }
        }

        @Override
        public void stopListening(IRecognitionListener iRecognitionListener) {
            RecognitionService recognitionService = (RecognitionService)this.mServiceRef.get();
            if (recognitionService != null && recognitionService.checkPermissions(iRecognitionListener)) {
                recognitionService.mHandler.sendMessage(Message.obtain(recognitionService.mHandler, 2, iRecognitionListener));
            }
        }
    }

    private class StartListeningArgs {
        public final int mCallingUid;
        public final Intent mIntent;
        public final IRecognitionListener mListener;

        public StartListeningArgs(Intent intent, IRecognitionListener iRecognitionListener, int n) {
            this.mIntent = intent;
            this.mListener = iRecognitionListener;
            this.mCallingUid = n;
        }
    }

}

