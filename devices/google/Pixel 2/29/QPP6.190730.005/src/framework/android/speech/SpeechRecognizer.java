/*
 * Decompiled with CFR 0.145.
 */
package android.speech;

import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.provider.Settings;
import android.speech.IRecognitionListener;
import android.speech.IRecognitionService;
import android.speech.RecognitionListener;
import android.text.TextUtils;
import android.util.Log;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class SpeechRecognizer {
    public static final String CONFIDENCE_SCORES = "confidence_scores";
    private static final boolean DBG = false;
    public static final int ERROR_AUDIO = 3;
    public static final int ERROR_CLIENT = 5;
    public static final int ERROR_INSUFFICIENT_PERMISSIONS = 9;
    public static final int ERROR_NETWORK = 2;
    public static final int ERROR_NETWORK_TIMEOUT = 1;
    public static final int ERROR_NO_MATCH = 7;
    public static final int ERROR_RECOGNIZER_BUSY = 8;
    public static final int ERROR_SERVER = 4;
    public static final int ERROR_SPEECH_TIMEOUT = 6;
    private static final int MSG_CANCEL = 3;
    private static final int MSG_CHANGE_LISTENER = 4;
    private static final int MSG_START = 1;
    private static final int MSG_STOP = 2;
    public static final String RESULTS_RECOGNITION = "results_recognition";
    private static final String TAG = "SpeechRecognizer";
    private Connection mConnection;
    private final Context mContext;
    private Handler mHandler = new Handler(){

        @Override
        public void handleMessage(Message message) {
            int n = message.what;
            if (n != 1) {
                if (n != 2) {
                    if (n != 3) {
                        if (n == 4) {
                            SpeechRecognizer.this.handleChangeListener((RecognitionListener)message.obj);
                        }
                    } else {
                        SpeechRecognizer.this.handleCancelMessage();
                    }
                } else {
                    SpeechRecognizer.this.handleStopMessage();
                }
            } else {
                SpeechRecognizer.this.handleStartListening((Intent)message.obj);
            }
        }
    };
    private final InternalListener mListener = new InternalListener();
    private final Queue<Message> mPendingTasks = new LinkedList<Message>();
    private IRecognitionService mService;
    private final ComponentName mServiceComponent;

    private SpeechRecognizer(Context context, ComponentName componentName) {
        this.mContext = context;
        this.mServiceComponent = componentName;
    }

    private static void checkIsCalledFromMainThread() {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            return;
        }
        throw new RuntimeException("SpeechRecognizer should be used only from the application's main thread");
    }

    private boolean checkOpenConnection() {
        if (this.mService != null) {
            return true;
        }
        this.mListener.onError(5);
        Log.e(TAG, "not connected to the recognition service");
        return false;
    }

    public static SpeechRecognizer createSpeechRecognizer(Context context) {
        return SpeechRecognizer.createSpeechRecognizer(context, null);
    }

    public static SpeechRecognizer createSpeechRecognizer(Context context, ComponentName componentName) {
        if (context != null) {
            SpeechRecognizer.checkIsCalledFromMainThread();
            return new SpeechRecognizer(context, componentName);
        }
        throw new IllegalArgumentException("Context cannot be null)");
    }

    private void handleCancelMessage() {
        if (!this.checkOpenConnection()) {
            return;
        }
        try {
            this.mService.cancel(this.mListener);
        }
        catch (RemoteException remoteException) {
            Log.e(TAG, "cancel() failed", remoteException);
            this.mListener.onError(5);
        }
    }

    private void handleChangeListener(RecognitionListener recognitionListener) {
        this.mListener.mInternalListener = recognitionListener;
    }

    private void handleStartListening(Intent intent) {
        if (!this.checkOpenConnection()) {
            return;
        }
        try {
            this.mService.startListening(intent, this.mListener);
        }
        catch (RemoteException remoteException) {
            Log.e(TAG, "startListening() failed", remoteException);
            this.mListener.onError(5);
        }
    }

    private void handleStopMessage() {
        if (!this.checkOpenConnection()) {
            return;
        }
        try {
            this.mService.stopListening(this.mListener);
        }
        catch (RemoteException remoteException) {
            Log.e(TAG, "stopListening() failed", remoteException);
            this.mListener.onError(5);
        }
    }

    public static boolean isRecognitionAvailable(Context list) {
        PackageManager packageManager = ((Context)((Object)list)).getPackageManager();
        list = new Intent("android.speech.RecognitionService");
        boolean bl = false;
        list = packageManager.queryIntentServices((Intent)((Object)list), 0);
        boolean bl2 = bl;
        if (list != null) {
            bl2 = bl;
            if (list.size() != 0) {
                bl2 = true;
            }
        }
        return bl2;
    }

    private void putMessage(Message message) {
        if (this.mService == null) {
            this.mPendingTasks.offer(message);
        } else {
            this.mHandler.sendMessage(message);
        }
    }

    public void cancel() {
        SpeechRecognizer.checkIsCalledFromMainThread();
        this.putMessage(Message.obtain(this.mHandler, 3));
    }

    public void destroy() {
        Object object = this.mService;
        if (object != null) {
            try {
                object.cancel(this.mListener);
            }
            catch (RemoteException remoteException) {
                // empty catch block
            }
        }
        if ((object = this.mConnection) != null) {
            this.mContext.unbindService((ServiceConnection)object);
        }
        this.mPendingTasks.clear();
        this.mService = null;
        this.mConnection = null;
        this.mListener.mInternalListener = null;
    }

    public void setRecognitionListener(RecognitionListener recognitionListener) {
        SpeechRecognizer.checkIsCalledFromMainThread();
        this.putMessage(Message.obtain(this.mHandler, 4, recognitionListener));
    }

    public void startListening(Intent intent) {
        if (intent != null) {
            SpeechRecognizer.checkIsCalledFromMainThread();
            if (this.mConnection == null) {
                this.mConnection = new Connection();
                Intent intent2 = new Intent("android.speech.RecognitionService");
                Object object = this.mServiceComponent;
                if (object == null) {
                    object = Settings.Secure.getString(this.mContext.getContentResolver(), "voice_recognition_service");
                    if (TextUtils.isEmpty((CharSequence)object)) {
                        Log.e(TAG, "no selected voice recognition service");
                        this.mListener.onError(5);
                        return;
                    }
                    intent2.setComponent(ComponentName.unflattenFromString((String)object));
                } else {
                    intent2.setComponent((ComponentName)object);
                }
                if (!this.mContext.bindService(intent2, this.mConnection, 1)) {
                    Log.e(TAG, "bind to recognition service failed");
                    this.mConnection = null;
                    this.mService = null;
                    this.mListener.onError(5);
                    return;
                }
            }
            this.putMessage(Message.obtain(this.mHandler, 1, intent));
            return;
        }
        throw new IllegalArgumentException("intent must not be null");
    }

    public void stopListening() {
        SpeechRecognizer.checkIsCalledFromMainThread();
        this.putMessage(Message.obtain(this.mHandler, 2));
    }

    private class Connection
    implements ServiceConnection {
        private Connection() {
        }

        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            SpeechRecognizer.this.mService = IRecognitionService.Stub.asInterface(iBinder);
            while (!SpeechRecognizer.this.mPendingTasks.isEmpty()) {
                SpeechRecognizer.this.mHandler.sendMessage((Message)SpeechRecognizer.this.mPendingTasks.poll());
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            SpeechRecognizer.this.mService = null;
            SpeechRecognizer.this.mConnection = null;
            SpeechRecognizer.this.mPendingTasks.clear();
        }
    }

    private static class InternalListener
    extends IRecognitionListener.Stub {
        private static final int MSG_BEGINNING_OF_SPEECH = 1;
        private static final int MSG_BUFFER_RECEIVED = 2;
        private static final int MSG_END_OF_SPEECH = 3;
        private static final int MSG_ERROR = 4;
        private static final int MSG_ON_EVENT = 9;
        private static final int MSG_PARTIAL_RESULTS = 7;
        private static final int MSG_READY_FOR_SPEECH = 5;
        private static final int MSG_RESULTS = 6;
        private static final int MSG_RMS_CHANGED = 8;
        private final Handler mInternalHandler = new Handler(){

            @Override
            public void handleMessage(Message message) {
                if (InternalListener.this.mInternalListener == null) {
                    return;
                }
                switch (message.what) {
                    default: {
                        break;
                    }
                    case 9: {
                        InternalListener.this.mInternalListener.onEvent(message.arg1, (Bundle)message.obj);
                        break;
                    }
                    case 8: {
                        InternalListener.this.mInternalListener.onRmsChanged(((Float)message.obj).floatValue());
                        break;
                    }
                    case 7: {
                        InternalListener.this.mInternalListener.onPartialResults((Bundle)message.obj);
                        break;
                    }
                    case 6: {
                        InternalListener.this.mInternalListener.onResults((Bundle)message.obj);
                        break;
                    }
                    case 5: {
                        InternalListener.this.mInternalListener.onReadyForSpeech((Bundle)message.obj);
                        break;
                    }
                    case 4: {
                        InternalListener.this.mInternalListener.onError((Integer)message.obj);
                        break;
                    }
                    case 3: {
                        InternalListener.this.mInternalListener.onEndOfSpeech();
                        break;
                    }
                    case 2: {
                        InternalListener.this.mInternalListener.onBufferReceived((byte[])message.obj);
                        break;
                    }
                    case 1: {
                        InternalListener.this.mInternalListener.onBeginningOfSpeech();
                    }
                }
            }
        };
        private RecognitionListener mInternalListener;

        private InternalListener() {
        }

        @Override
        public void onBeginningOfSpeech() {
            Message.obtain(this.mInternalHandler, 1).sendToTarget();
        }

        @Override
        public void onBufferReceived(byte[] arrby) {
            Message.obtain(this.mInternalHandler, 2, arrby).sendToTarget();
        }

        @Override
        public void onEndOfSpeech() {
            Message.obtain(this.mInternalHandler, 3).sendToTarget();
        }

        @Override
        public void onError(int n) {
            Message.obtain(this.mInternalHandler, 4, n).sendToTarget();
        }

        @Override
        public void onEvent(int n, Bundle bundle) {
            Message.obtain(this.mInternalHandler, 9, n, n, bundle).sendToTarget();
        }

        @Override
        public void onPartialResults(Bundle bundle) {
            Message.obtain(this.mInternalHandler, 7, bundle).sendToTarget();
        }

        @Override
        public void onReadyForSpeech(Bundle bundle) {
            Message.obtain(this.mInternalHandler, 5, bundle).sendToTarget();
        }

        @Override
        public void onResults(Bundle bundle) {
            Message.obtain(this.mInternalHandler, 6, bundle).sendToTarget();
        }

        @Override
        public void onRmsChanged(float f) {
            Message.obtain(this.mInternalHandler, 8, Float.valueOf(f)).sendToTarget();
        }

    }

}

