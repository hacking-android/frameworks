/*
 * Decompiled with CFR 0.145.
 */
package android.media.soundtrigger;

import android.annotation.SystemApi;
import android.annotation.UnsupportedAppUsage;
import android.hardware.soundtrigger.IRecognitionStatusCallback;
import android.hardware.soundtrigger.SoundTrigger;
import android.media.AudioFormat;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.ParcelUuid;
import android.os.RemoteException;
import android.util.Slog;
import com.android.internal.app.ISoundTriggerService;
import java.io.PrintWriter;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.UUID;

@SystemApi
public final class SoundTriggerDetector {
    private static final boolean DBG = false;
    private static final int MSG_AVAILABILITY_CHANGED = 1;
    private static final int MSG_DETECTION_ERROR = 3;
    private static final int MSG_DETECTION_PAUSE = 4;
    private static final int MSG_DETECTION_RESUME = 5;
    private static final int MSG_SOUND_TRIGGER_DETECTED = 2;
    public static final int RECOGNITION_FLAG_ALLOW_MULTIPLE_TRIGGERS = 2;
    public static final int RECOGNITION_FLAG_CAPTURE_TRIGGER_AUDIO = 1;
    public static final int RECOGNITION_FLAG_NONE = 0;
    private static final String TAG = "SoundTriggerDetector";
    private final Callback mCallback;
    private final Handler mHandler;
    private final Object mLock = new Object();
    private final RecognitionCallback mRecognitionCallback;
    private final UUID mSoundModelId;
    private final ISoundTriggerService mSoundTriggerService;

    SoundTriggerDetector(ISoundTriggerService iSoundTriggerService, UUID uUID, Callback callback, Handler handler) {
        this.mSoundTriggerService = iSoundTriggerService;
        this.mSoundModelId = uUID;
        this.mCallback = callback;
        this.mHandler = handler == null ? new MyHandler() : new MyHandler(handler.getLooper());
        this.mRecognitionCallback = new RecognitionCallback();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    public void dump(String object, PrintWriter printWriter) {
        object = this.mLock;
        // MONITORENTER : object
        // MONITOREXIT : object
    }

    public boolean startRecognition(int n) {
        boolean bl = false;
        boolean bl2 = (n & 1) != 0;
        boolean bl3 = (n & 2) != 0;
        try {
            ISoundTriggerService iSoundTriggerService = this.mSoundTriggerService;
            ParcelUuid parcelUuid = new ParcelUuid(this.mSoundModelId);
            RecognitionCallback recognitionCallback = this.mRecognitionCallback;
            SoundTrigger.RecognitionConfig recognitionConfig = new SoundTrigger.RecognitionConfig(bl2, bl3, null, null);
            n = iSoundTriggerService.startRecognition(parcelUuid, recognitionCallback, recognitionConfig);
            bl2 = bl;
            if (n == 0) {
                bl2 = true;
            }
            return bl2;
        }
        catch (RemoteException remoteException) {
            return false;
        }
    }

    public boolean stopRecognition() {
        boolean bl = false;
        try {
            ISoundTriggerService iSoundTriggerService = this.mSoundTriggerService;
            ParcelUuid parcelUuid = new ParcelUuid(this.mSoundModelId);
            int n = iSoundTriggerService.stopRecognition(parcelUuid, this.mRecognitionCallback);
            if (n == 0) {
                bl = true;
            }
            return bl;
        }
        catch (RemoteException remoteException) {
            return false;
        }
    }

    public static abstract class Callback {
        public abstract void onAvailabilityChanged(int var1);

        public abstract void onDetected(EventPayload var1);

        public abstract void onError();

        public abstract void onRecognitionPaused();

        public abstract void onRecognitionResumed();
    }

    public static class EventPayload {
        private final AudioFormat mAudioFormat;
        private final boolean mCaptureAvailable;
        private final int mCaptureSession;
        private final byte[] mData;
        private final boolean mTriggerAvailable;

        private EventPayload(boolean bl, boolean bl2, AudioFormat audioFormat, int n, byte[] arrby) {
            this.mTriggerAvailable = bl;
            this.mCaptureAvailable = bl2;
            this.mCaptureSession = n;
            this.mAudioFormat = audioFormat;
            this.mData = arrby;
        }

        public AudioFormat getCaptureAudioFormat() {
            return this.mAudioFormat;
        }

        @UnsupportedAppUsage
        public Integer getCaptureSession() {
            if (this.mCaptureAvailable) {
                return this.mCaptureSession;
            }
            return null;
        }

        @UnsupportedAppUsage
        public byte[] getData() {
            if (!this.mTriggerAvailable) {
                return this.mData;
            }
            return null;
        }

        public byte[] getTriggerAudio() {
            if (this.mTriggerAvailable) {
                return this.mData;
            }
            return null;
        }
    }

    private class MyHandler
    extends Handler {
        MyHandler() {
        }

        MyHandler(Looper looper) {
            super(looper);
        }

        @Override
        public void handleMessage(Message message) {
            if (SoundTriggerDetector.this.mCallback == null) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Received message: ");
                stringBuilder.append(message.what);
                stringBuilder.append(" for NULL callback.");
                Slog.w(SoundTriggerDetector.TAG, stringBuilder.toString());
                return;
            }
            int n = message.what;
            if (n != 2) {
                if (n != 3) {
                    if (n != 4) {
                        if (n != 5) {
                            super.handleMessage(message);
                        } else {
                            SoundTriggerDetector.this.mCallback.onRecognitionResumed();
                        }
                    } else {
                        SoundTriggerDetector.this.mCallback.onRecognitionPaused();
                    }
                } else {
                    SoundTriggerDetector.this.mCallback.onError();
                }
            } else {
                SoundTriggerDetector.this.mCallback.onDetected((EventPayload)message.obj);
            }
        }
    }

    private class RecognitionCallback
    extends IRecognitionStatusCallback.Stub {
        private RecognitionCallback() {
        }

        @Override
        public void onError(int n) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("onError()");
            stringBuilder.append(n);
            Slog.d(SoundTriggerDetector.TAG, stringBuilder.toString());
            SoundTriggerDetector.this.mHandler.sendEmptyMessage(3);
        }

        @Override
        public void onGenericSoundTriggerDetected(SoundTrigger.GenericRecognitionEvent genericRecognitionEvent) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("onGenericSoundTriggerDetected()");
            stringBuilder.append(genericRecognitionEvent);
            Slog.d(SoundTriggerDetector.TAG, stringBuilder.toString());
            Message.obtain(SoundTriggerDetector.this.mHandler, 2, new EventPayload(genericRecognitionEvent.triggerInData, genericRecognitionEvent.captureAvailable, genericRecognitionEvent.captureFormat, genericRecognitionEvent.captureSession, genericRecognitionEvent.data)).sendToTarget();
        }

        @Override
        public void onKeyphraseDetected(SoundTrigger.KeyphraseRecognitionEvent keyphraseRecognitionEvent) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Ignoring onKeyphraseDetected() called for ");
            stringBuilder.append(keyphraseRecognitionEvent);
            Slog.e(SoundTriggerDetector.TAG, stringBuilder.toString());
        }

        @Override
        public void onRecognitionPaused() {
            Slog.d(SoundTriggerDetector.TAG, "onRecognitionPaused()");
            SoundTriggerDetector.this.mHandler.sendEmptyMessage(4);
        }

        @Override
        public void onRecognitionResumed() {
            Slog.d(SoundTriggerDetector.TAG, "onRecognitionResumed()");
            SoundTriggerDetector.this.mHandler.sendEmptyMessage(5);
        }
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface RecognitionFlags {
    }

}

