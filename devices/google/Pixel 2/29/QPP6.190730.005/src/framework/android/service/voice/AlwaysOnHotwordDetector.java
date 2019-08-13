/*
 * Decompiled with CFR 0.145.
 */
package android.service.voice;

import android.annotation.UnsupportedAppUsage;
import android.content.Intent;
import android.hardware.soundtrigger.IRecognitionStatusCallback;
import android.hardware.soundtrigger.KeyphraseEnrollmentInfo;
import android.hardware.soundtrigger.KeyphraseMetadata;
import android.hardware.soundtrigger.SoundTrigger;
import android.media.AudioFormat;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.os.RemoteException;
import android.service.voice.IVoiceInteractionService;
import android.util.Slog;
import com.android.internal.app.IVoiceInteractionManagerService;
import java.io.PrintWriter;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Locale;

public class AlwaysOnHotwordDetector {
    static final boolean DBG = false;
    public static final int MANAGE_ACTION_ENROLL = 0;
    public static final int MANAGE_ACTION_RE_ENROLL = 1;
    public static final int MANAGE_ACTION_UN_ENROLL = 2;
    private static final int MSG_AVAILABILITY_CHANGED = 1;
    private static final int MSG_DETECTION_ERROR = 3;
    private static final int MSG_DETECTION_PAUSE = 4;
    private static final int MSG_DETECTION_RESUME = 5;
    private static final int MSG_HOTWORD_DETECTED = 2;
    public static final int RECOGNITION_FLAG_ALLOW_MULTIPLE_TRIGGERS = 2;
    public static final int RECOGNITION_FLAG_CAPTURE_TRIGGER_AUDIO = 1;
    public static final int RECOGNITION_FLAG_NONE = 0;
    public static final int RECOGNITION_MODE_USER_IDENTIFICATION = 2;
    public static final int RECOGNITION_MODE_VOICE_TRIGGER = 1;
    public static final int STATE_HARDWARE_UNAVAILABLE = -2;
    private static final int STATE_INVALID = -3;
    public static final int STATE_KEYPHRASE_ENROLLED = 2;
    public static final int STATE_KEYPHRASE_UNENROLLED = 1;
    public static final int STATE_KEYPHRASE_UNSUPPORTED = -1;
    private static final int STATE_NOT_READY = 0;
    private static final int STATUS_ERROR = Integer.MIN_VALUE;
    private static final int STATUS_OK = 0;
    static final String TAG = "AlwaysOnHotwordDetector";
    private int mAvailability = 0;
    private final Callback mExternalCallback;
    private final Handler mHandler;
    private final SoundTriggerListener mInternalCallback;
    private final KeyphraseEnrollmentInfo mKeyphraseEnrollmentInfo;
    private final KeyphraseMetadata mKeyphraseMetadata;
    private final Locale mLocale;
    private final Object mLock = new Object();
    private final IVoiceInteractionManagerService mModelManagementService;
    private final String mText;
    private final IVoiceInteractionService mVoiceInteractionService;

    public AlwaysOnHotwordDetector(String string2, Locale locale, Callback callback, KeyphraseEnrollmentInfo keyphraseEnrollmentInfo, IVoiceInteractionService iVoiceInteractionService, IVoiceInteractionManagerService iVoiceInteractionManagerService) {
        this.mText = string2;
        this.mLocale = locale;
        this.mKeyphraseEnrollmentInfo = keyphraseEnrollmentInfo;
        this.mKeyphraseMetadata = this.mKeyphraseEnrollmentInfo.getKeyphraseMetadata(string2, locale);
        this.mExternalCallback = callback;
        this.mHandler = new MyHandler();
        this.mInternalCallback = new SoundTriggerListener(this.mHandler);
        this.mVoiceInteractionService = iVoiceInteractionService;
        this.mModelManagementService = iVoiceInteractionManagerService;
        new RefreshAvailabiltyTask().execute(new Void[0]);
    }

    private Intent getManageIntentLocked(int n) {
        int n2 = this.mAvailability;
        if (n2 != -3) {
            if (n2 != 2 && n2 != 1) {
                throw new UnsupportedOperationException("Managing the given keyphrase is not supported");
            }
            return this.mKeyphraseEnrollmentInfo.getManageKeyphraseIntent(n, this.mText, this.mLocale);
        }
        throw new IllegalStateException("getManageIntent called on an invalid detector");
    }

    private int getSupportedRecognitionModesLocked() {
        int n = this.mAvailability;
        if (n != -3) {
            if (n != 2 && n != 1) {
                throw new UnsupportedOperationException("Getting supported recognition modes for the keyphrase is not supported");
            }
            return this.mKeyphraseMetadata.recognitionModeFlags;
        }
        throw new IllegalStateException("getSupportedRecognitionModes called on an invalid detector");
    }

    private void notifyStateChangedLocked() {
        Message message = Message.obtain(this.mHandler, 1);
        message.arg1 = this.mAvailability;
        message.sendToTarget();
    }

    private int startRecognitionLocked(int n) {
        Object object;
        boolean bl = true;
        SoundTrigger.KeyphraseRecognitionExtra keyphraseRecognitionExtra = new SoundTrigger.KeyphraseRecognitionExtra(this.mKeyphraseMetadata.id, this.mKeyphraseMetadata.recognitionModeFlags, 0, new SoundTrigger.ConfidenceLevel[0]);
        boolean bl2 = (n & 1) != 0;
        if ((n & 2) == 0) {
            bl = false;
        }
        n = Integer.MIN_VALUE;
        try {
            IVoiceInteractionManagerService iVoiceInteractionManagerService = this.mModelManagementService;
            object = this.mVoiceInteractionService;
            int n2 = this.mKeyphraseMetadata.id;
            String string2 = this.mLocale.toLanguageTag();
            SoundTriggerListener soundTriggerListener = this.mInternalCallback;
            SoundTrigger.RecognitionConfig recognitionConfig = new SoundTrigger.RecognitionConfig(bl2, bl, new SoundTrigger.KeyphraseRecognitionExtra[]{keyphraseRecognitionExtra}, null);
            n = n2 = iVoiceInteractionManagerService.startRecognition((IVoiceInteractionService)object, n2, string2, soundTriggerListener, recognitionConfig);
        }
        catch (RemoteException remoteException) {
            Slog.w(TAG, "RemoteException in startRecognition!", remoteException);
        }
        if (n != 0) {
            object = new StringBuilder();
            ((StringBuilder)object).append("startRecognition() failed with error code ");
            ((StringBuilder)object).append(n);
            Slog.w(TAG, ((StringBuilder)object).toString());
        }
        return n;
    }

    private int stopRecognitionLocked() {
        int n = Integer.MIN_VALUE;
        try {
            int n2;
            n = n2 = this.mModelManagementService.stopRecognition(this.mVoiceInteractionService, this.mKeyphraseMetadata.id, this.mInternalCallback);
        }
        catch (RemoteException remoteException) {
            Slog.w(TAG, "RemoteException in stopRecognition!", remoteException);
        }
        if (n != 0) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("stopRecognition() failed with error code ");
            stringBuilder.append(n);
            Slog.w(TAG, stringBuilder.toString());
        }
        return n;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public Intent createEnrollIntent() {
        Object object = this.mLock;
        synchronized (object) {
            return this.getManageIntentLocked(0);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public Intent createReEnrollIntent() {
        Object object = this.mLock;
        synchronized (object) {
            return this.getManageIntentLocked(1);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public Intent createUnEnrollIntent() {
        Object object = this.mLock;
        synchronized (object) {
            return this.getManageIntentLocked(2);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void dump(String string2, PrintWriter printWriter) {
        Object object = this.mLock;
        synchronized (object) {
            printWriter.print(string2);
            printWriter.print("Text=");
            printWriter.println(this.mText);
            printWriter.print(string2);
            printWriter.print("Locale=");
            printWriter.println(this.mLocale);
            printWriter.print(string2);
            printWriter.print("Availability=");
            printWriter.println(this.mAvailability);
            printWriter.print(string2);
            printWriter.print("KeyphraseMetadata=");
            printWriter.println(this.mKeyphraseMetadata);
            printWriter.print(string2);
            printWriter.print("EnrollmentInfo=");
            printWriter.println(this.mKeyphraseEnrollmentInfo);
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public int getSupportedRecognitionModes() {
        Object object = this.mLock;
        synchronized (object) {
            return this.getSupportedRecognitionModesLocked();
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    void invalidate() {
        Object object = this.mLock;
        synchronized (object) {
            this.mAvailability = -3;
            this.notifyStateChangedLocked();
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    void onSoundModelsChanged() {
        Object object = this.mLock;
        synchronized (object) {
            if (this.mAvailability != -3 && this.mAvailability != -2 && this.mAvailability != -1) {
                this.stopRecognitionLocked();
                RefreshAvailabiltyTask refreshAvailabiltyTask = new RefreshAvailabiltyTask();
                refreshAvailabiltyTask.execute(new Void[0]);
                return;
            }
            Slog.w(TAG, "Received onSoundModelsChanged for an unsupported keyphrase/config");
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public boolean startRecognition(int n) {
        Object object = this.mLock;
        synchronized (object) {
            if (this.mAvailability == -3) {
                IllegalStateException illegalStateException = new IllegalStateException("startRecognition called on an invalid detector");
                throw illegalStateException;
            }
            if (this.mAvailability != 2) {
                UnsupportedOperationException unsupportedOperationException = new UnsupportedOperationException("Recognition for the given keyphrase is not supported");
                throw unsupportedOperationException;
            }
            if (this.startRecognitionLocked(n) != 0) return false;
            return true;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public boolean stopRecognition() {
        Object object = this.mLock;
        synchronized (object) {
            if (this.mAvailability == -3) {
                IllegalStateException illegalStateException = new IllegalStateException("stopRecognition called on an invalid detector");
                throw illegalStateException;
            }
            if (this.mAvailability != 2) {
                UnsupportedOperationException unsupportedOperationException = new UnsupportedOperationException("Recognition for the given keyphrase is not supported");
                throw unsupportedOperationException;
            }
            if (this.stopRecognitionLocked() != 0) return false;
            return true;
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

        public byte[] getTriggerAudio() {
            if (this.mTriggerAvailable) {
                return this.mData;
            }
            return null;
        }
    }

    @Retention(value=RetentionPolicy.SOURCE)
    private static @interface ManageActions {
    }

    class MyHandler
    extends Handler {
        MyHandler() {
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void handleMessage(Message message) {
            Object object = AlwaysOnHotwordDetector.this.mLock;
            synchronized (object) {
                if (AlwaysOnHotwordDetector.this.mAvailability == -3) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Received message: ");
                    stringBuilder.append(message.what);
                    stringBuilder.append(" for an invalid detector");
                    Slog.w(AlwaysOnHotwordDetector.TAG, stringBuilder.toString());
                    return;
                }
            }
            int n = message.what;
            if (n == 1) {
                AlwaysOnHotwordDetector.this.mExternalCallback.onAvailabilityChanged(message.arg1);
                return;
            }
            if (n == 2) {
                AlwaysOnHotwordDetector.this.mExternalCallback.onDetected((EventPayload)message.obj);
                return;
            }
            if (n == 3) {
                AlwaysOnHotwordDetector.this.mExternalCallback.onError();
                return;
            }
            if (n == 4) {
                AlwaysOnHotwordDetector.this.mExternalCallback.onRecognitionPaused();
                return;
            }
            if (n != 5) {
                super.handleMessage(message);
                return;
            }
            AlwaysOnHotwordDetector.this.mExternalCallback.onRecognitionResumed();
        }
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface RecognitionFlags {
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface RecognitionModes {
    }

    class RefreshAvailabiltyTask
    extends AsyncTask<Void, Void, Void> {
        RefreshAvailabiltyTask() {
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        private int internalGetInitialAvailability() {
            Object object = AlwaysOnHotwordDetector.this.mLock;
            synchronized (object) {
                if (AlwaysOnHotwordDetector.this.mAvailability == -3) {
                    return -3;
                }
            }
            Object object2 = null;
            try {
                object2 = object = AlwaysOnHotwordDetector.this.mModelManagementService.getDspModuleProperties(AlwaysOnHotwordDetector.this.mVoiceInteractionService);
            }
            catch (RemoteException remoteException) {
                Slog.w(AlwaysOnHotwordDetector.TAG, "RemoteException in getDspProperties!", remoteException);
            }
            if (object2 == null) {
                return -2;
            }
            if (AlwaysOnHotwordDetector.this.mKeyphraseMetadata == null) {
                return -1;
            }
            return 0;
        }

        private boolean internalGetIsEnrolled(int n, Locale locale) {
            try {
                boolean bl = AlwaysOnHotwordDetector.this.mModelManagementService.isEnrolledForKeyphrase(AlwaysOnHotwordDetector.this.mVoiceInteractionService, n, locale.toLanguageTag());
                return bl;
            }
            catch (RemoteException remoteException) {
                Slog.w(AlwaysOnHotwordDetector.TAG, "RemoteException in listRegisteredKeyphraseSoundModels!", remoteException);
                return false;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public Void doInBackground(Void ... arrvoid) {
            int n;
            block5 : {
                block4 : {
                    int n2 = this.internalGetInitialAvailability();
                    if (n2 == 0 || n2 == 1) break block4;
                    n = n2;
                    if (n2 != 2) break block5;
                }
                n = !this.internalGetIsEnrolled(AlwaysOnHotwordDetector.access$400((AlwaysOnHotwordDetector)AlwaysOnHotwordDetector.this).id, AlwaysOnHotwordDetector.this.mLocale) ? 1 : 2;
            }
            Object object = AlwaysOnHotwordDetector.this.mLock;
            synchronized (object) {
                AlwaysOnHotwordDetector.this.mAvailability = n;
                AlwaysOnHotwordDetector.this.notifyStateChangedLocked();
                return null;
            }
        }
    }

    static final class SoundTriggerListener
    extends IRecognitionStatusCallback.Stub {
        private final Handler mHandler;

        public SoundTriggerListener(Handler handler) {
            this.mHandler = handler;
        }

        @Override
        public void onError(int n) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("onError: ");
            stringBuilder.append(n);
            Slog.i(AlwaysOnHotwordDetector.TAG, stringBuilder.toString());
            this.mHandler.sendEmptyMessage(3);
        }

        @Override
        public void onGenericSoundTriggerDetected(SoundTrigger.GenericRecognitionEvent genericRecognitionEvent) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Generic sound trigger event detected at AOHD: ");
            stringBuilder.append(genericRecognitionEvent);
            Slog.w(AlwaysOnHotwordDetector.TAG, stringBuilder.toString());
        }

        @Override
        public void onKeyphraseDetected(SoundTrigger.KeyphraseRecognitionEvent keyphraseRecognitionEvent) {
            Slog.i(AlwaysOnHotwordDetector.TAG, "onDetected");
            Message.obtain(this.mHandler, 2, new EventPayload(keyphraseRecognitionEvent.triggerInData, keyphraseRecognitionEvent.captureAvailable, keyphraseRecognitionEvent.captureFormat, keyphraseRecognitionEvent.captureSession, keyphraseRecognitionEvent.data)).sendToTarget();
        }

        @Override
        public void onRecognitionPaused() {
            Slog.i(AlwaysOnHotwordDetector.TAG, "onRecognitionPaused");
            this.mHandler.sendEmptyMessage(4);
        }

        @Override
        public void onRecognitionResumed() {
            Slog.i(AlwaysOnHotwordDetector.TAG, "onRecognitionResumed");
            this.mHandler.sendEmptyMessage(5);
        }
    }

}

