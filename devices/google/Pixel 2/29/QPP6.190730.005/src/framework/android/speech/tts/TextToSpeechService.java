/*
 * Decompiled with CFR 0.145.
 */
package android.speech.tts;

import android.app.Service;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.media.AudioAttributes;
import android.net.Uri;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Looper;
import android.os.Message;
import android.os.MessageQueue;
import android.os.ParcelFileDescriptor;
import android.os.Parcelable;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.provider.Settings;
import android.speech.tts.AbstractEventLogger;
import android.speech.tts.AbstractSynthesisCallback;
import android.speech.tts.AudioPlaybackHandler;
import android.speech.tts.AudioPlaybackQueueItem;
import android.speech.tts.EventLogger;
import android.speech.tts.FileSynthesisCallback;
import android.speech.tts.ITextToSpeechCallback;
import android.speech.tts.ITextToSpeechService;
import android.speech.tts.PlaybackQueueItem;
import android.speech.tts.PlaybackSynthesisCallback;
import android.speech.tts.SilencePlaybackQueueItem;
import android.speech.tts.SynthesisCallback;
import android.speech.tts.SynthesisRequest;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TtsEngines;
import android.speech.tts.Voice;
import android.text.TextUtils;
import android.util.Log;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.Set;

public abstract class TextToSpeechService
extends Service {
    private static final boolean DBG = false;
    private static final String SYNTH_THREAD_NAME = "SynthThread";
    private static final String TAG = "TextToSpeechService";
    private AudioPlaybackHandler mAudioPlaybackHandler;
    private final ITextToSpeechService.Stub mBinder = new ITextToSpeechService.Stub(){

        private boolean checkNonNull(Object ... arrobject) {
            int n = arrobject.length;
            for (int i = 0; i < n; ++i) {
                if (arrobject[i] != null) continue;
                return false;
            }
            return true;
        }

        private String intern(String string2) {
            return string2.intern();
        }

        @Override
        public String[] getClientDefaultLanguage() {
            return TextToSpeechService.this.getSettingsLocale();
        }

        @Override
        public String getDefaultVoiceNameFor(String string2, String string3, String string4) {
            if (!this.checkNonNull(string2)) {
                return null;
            }
            int n = TextToSpeechService.this.onIsLanguageAvailable(string2, string3, string4);
            if (n != 0 && n != 1 && n != 2) {
                return null;
            }
            return TextToSpeechService.this.onGetDefaultVoiceNameFor(string2, string3, string4);
        }

        @Override
        public String[] getFeaturesForLanguage(String arrstring, String object, String string2) {
            if ((object = TextToSpeechService.this.onGetFeaturesForLanguage((String)arrstring, (String)object, string2)) != null) {
                arrstring = new String[object.size()];
                object.toArray(arrstring);
            } else {
                arrstring = new String[]{};
            }
            return arrstring;
        }

        @Override
        public String[] getLanguage() {
            return TextToSpeechService.this.onGetLanguage();
        }

        @Override
        public List<Voice> getVoices() {
            return TextToSpeechService.this.onGetVoices();
        }

        @Override
        public int isLanguageAvailable(String string2, String string3, String string4) {
            if (!this.checkNonNull(string2)) {
                return -1;
            }
            return TextToSpeechService.this.onIsLanguageAvailable(string2, string3, string4);
        }

        @Override
        public boolean isSpeaking() {
            boolean bl = TextToSpeechService.this.mSynthHandler.isSpeaking() || TextToSpeechService.this.mAudioPlaybackHandler.isSpeaking();
            return bl;
        }

        @Override
        public int loadLanguage(IBinder object, String string2, String string3, String string4) {
            if (!this.checkNonNull(string2)) {
                return -1;
            }
            int n = TextToSpeechService.this.onIsLanguageAvailable(string2, string3, string4);
            if (n == 0 || n == 1 || n == 2) {
                object = new LoadLanguageItem(object, Binder.getCallingUid(), Binder.getCallingPid(), string2, string3, string4);
                if (TextToSpeechService.this.mSynthHandler.enqueueSpeechItem(1, (SpeechItem)object) != 0) {
                    return -1;
                }
            }
            return n;
        }

        @Override
        public int loadVoice(IBinder object, String string2) {
            if (!this.checkNonNull(string2)) {
                return -1;
            }
            int n = TextToSpeechService.this.onIsValidVoiceName(string2);
            if (n == 0) {
                object = new LoadVoiceItem(object, Binder.getCallingUid(), Binder.getCallingPid(), string2);
                if (TextToSpeechService.this.mSynthHandler.enqueueSpeechItem(1, (SpeechItem)object) != 0) {
                    return -1;
                }
            }
            return n;
        }

        @Override
        public int playAudio(IBinder object, Uri uri, int n, Bundle bundle, String string2) {
            if (!this.checkNonNull(object, uri, bundle)) {
                return -1;
            }
            object = new AudioSpeechItem(object, Binder.getCallingUid(), Binder.getCallingPid(), bundle, string2, uri);
            return TextToSpeechService.this.mSynthHandler.enqueueSpeechItem(n, (SpeechItem)object);
        }

        @Override
        public int playSilence(IBinder object, long l, int n, String string2) {
            if (!this.checkNonNull(object)) {
                return -1;
            }
            object = new SilenceSpeechItem(object, Binder.getCallingUid(), Binder.getCallingPid(), string2, l);
            return TextToSpeechService.this.mSynthHandler.enqueueSpeechItem(n, (SpeechItem)object);
        }

        @Override
        public void setCallback(IBinder iBinder, ITextToSpeechCallback iTextToSpeechCallback) {
            if (!this.checkNonNull(iBinder)) {
                return;
            }
            TextToSpeechService.this.mCallbacks.setCallback(iBinder, iTextToSpeechCallback);
        }

        @Override
        public int speak(IBinder object, CharSequence charSequence, int n, Bundle bundle, String string2) {
            if (!this.checkNonNull(object, charSequence, bundle)) {
                return -1;
            }
            object = new SynthesisSpeechItem(object, Binder.getCallingUid(), Binder.getCallingPid(), bundle, string2, charSequence);
            return TextToSpeechService.this.mSynthHandler.enqueueSpeechItem(n, (SpeechItem)object);
        }

        @Override
        public int stop(IBinder iBinder) {
            if (!this.checkNonNull(iBinder)) {
                return -1;
            }
            return TextToSpeechService.this.mSynthHandler.stopForApp(iBinder);
        }

        @Override
        public int synthesizeToFileDescriptor(IBinder object, CharSequence charSequence, ParcelFileDescriptor parcelFileDescriptor, Bundle bundle, String string2) {
            if (!this.checkNonNull(object, charSequence, parcelFileDescriptor, bundle)) {
                return -1;
            }
            parcelFileDescriptor = ParcelFileDescriptor.adoptFd(parcelFileDescriptor.detachFd());
            object = new SynthesisToFileOutputStreamSpeechItem(object, Binder.getCallingUid(), Binder.getCallingPid(), bundle, string2, charSequence, new ParcelFileDescriptor.AutoCloseOutputStream(parcelFileDescriptor));
            return TextToSpeechService.this.mSynthHandler.enqueueSpeechItem(1, (SpeechItem)object);
        }
    };
    private CallbackMap mCallbacks;
    private TtsEngines mEngineHelper;
    private String mPackageName;
    private SynthHandler mSynthHandler;
    private final Object mVoicesInfoLock = new Object();

    private int getDefaultPitch() {
        return this.getSecureSettingInt("tts_default_pitch", 100);
    }

    private int getDefaultSpeechRate() {
        return this.getSecureSettingInt("tts_default_rate", 100);
    }

    private int getExpectedLanguageAvailableStatus(Locale locale) {
        int n = 2;
        if (locale.getVariant().isEmpty()) {
            n = locale.getCountry().isEmpty() ? 0 : 1;
        }
        return n;
    }

    private int getSecureSettingInt(String string2, int n) {
        return Settings.Secure.getInt(this.getContentResolver(), string2, n);
    }

    private String[] getSettingsLocale() {
        return TtsEngines.toOldLocaleStringFormat(this.mEngineHelper.getLocalePrefForEngine(this.mPackageName));
    }

    @Override
    public IBinder onBind(Intent intent) {
        if ("android.intent.action.TTS_SERVICE".equals(intent.getAction())) {
            return this.mBinder;
        }
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        String[] arrstring = new SynthThread();
        arrstring.start();
        this.mSynthHandler = new SynthHandler(arrstring.getLooper());
        this.mAudioPlaybackHandler = new AudioPlaybackHandler();
        this.mAudioPlaybackHandler.start();
        this.mEngineHelper = new TtsEngines(this);
        this.mCallbacks = new CallbackMap();
        this.mPackageName = this.getApplicationInfo().packageName;
        arrstring = this.getSettingsLocale();
        this.onLoadLanguage(arrstring[0], arrstring[1], arrstring[2]);
    }

    @Override
    public void onDestroy() {
        this.mSynthHandler.quit();
        this.mAudioPlaybackHandler.quit();
        this.mCallbacks.kill();
        super.onDestroy();
    }

    public String onGetDefaultVoiceNameFor(String object, String string2, String string3) {
        int n = this.onIsLanguageAvailable((String)object, string2, string3);
        if (n != 0) {
            if (n != 1) {
                if (n != 2) {
                    return null;
                }
                object = new Locale((String)object, string2, string3);
            } else {
                object = new Locale((String)object, string2);
            }
        } else {
            object = new Locale((String)object);
        }
        object = TtsEngines.normalizeTTSLocale((Locale)object).toLanguageTag();
        if (this.onIsValidVoiceName((String)object) == 0) {
            return object;
        }
        return null;
    }

    protected Set<String> onGetFeaturesForLanguage(String string2, String string3, String string4) {
        return new HashSet<String>();
    }

    protected abstract String[] onGetLanguage();

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public List<Voice> onGetVoices() {
        var1_1 = new ArrayList<Voice>();
        var2_2 = Locale.getAvailableLocales();
        var3_3 = var2_2.length;
        var4_4 = 0;
        do {
            var5_5 = this;
            if (var4_4 >= var3_3) return var1_1;
            var6_7 = var2_2[var4_4];
            var7_8 = var5_5.getExpectedLanguageAvailableStatus(var6_7);
            try {
                var8_9 = var5_5.onIsLanguageAvailable(var6_7.getISO3Language(), var6_7.getISO3Country(), var6_7.getVariant());
                ** if (var8_9 != var7_8) goto lbl-1000
            }
            catch (MissingResourceException var5_6) {
                // empty catch block
            }
lbl-1000: // 1 sources:
            {
                var9_10 = var5_5.onGetFeaturesForLanguage(var6_7.getISO3Language(), var6_7.getISO3Country(), var6_7.getVariant());
                var1_1.add(new Voice(var5_5.onGetDefaultVoiceNameFor(var6_7.getISO3Language(), var6_7.getISO3Country(), var6_7.getVariant()), var6_7, 300, 300, false, var9_10));
            }
lbl-1000: // 2 sources:
            {
            }
            ++var4_4;
        } while (true);
    }

    protected abstract int onIsLanguageAvailable(String var1, String var2, String var3);

    public int onIsValidVoiceName(String object) {
        if ((object = Locale.forLanguageTag((String)object)) == null) {
            return -1;
        }
        int n = this.getExpectedLanguageAvailableStatus((Locale)object);
        try {
            int n2 = this.onIsLanguageAvailable(((Locale)object).getISO3Language(), ((Locale)object).getISO3Country(), ((Locale)object).getVariant());
            if (n2 != n) {
                return -1;
            }
            return 0;
        }
        catch (MissingResourceException missingResourceException) {
            return -1;
        }
    }

    protected abstract int onLoadLanguage(String var1, String var2, String var3);

    public int onLoadVoice(String object) {
        block4 : {
            if ((object = Locale.forLanguageTag((String)object)) == null) {
                return -1;
            }
            int n = this.getExpectedLanguageAvailableStatus((Locale)object);
            try {
                if (this.onIsLanguageAvailable(((Locale)object).getISO3Language(), ((Locale)object).getISO3Country(), ((Locale)object).getVariant()) == n) break block4;
                return -1;
            }
            catch (MissingResourceException missingResourceException) {
                return -1;
            }
        }
        this.onLoadLanguage(((Locale)object).getISO3Language(), ((Locale)object).getISO3Country(), ((Locale)object).getVariant());
        return 0;
    }

    protected abstract void onStop();

    protected abstract void onSynthesizeText(SynthesisRequest var1, SynthesisCallback var2);

    static class AudioOutputParams {
        public final AudioAttributes mAudioAttributes;
        public final float mPan;
        public final int mSessionId;
        public final float mVolume;

        AudioOutputParams() {
            this.mSessionId = 0;
            this.mVolume = 1.0f;
            this.mPan = 0.0f;
            this.mAudioAttributes = null;
        }

        AudioOutputParams(int n, float f, float f2, AudioAttributes audioAttributes) {
            this.mSessionId = n;
            this.mVolume = f;
            this.mPan = f2;
            this.mAudioAttributes = audioAttributes;
        }

        static AudioOutputParams createFromParamsBundle(Bundle bundle, boolean bl) {
            if (bundle == null) {
                return new AudioOutputParams();
            }
            AudioAttributes audioAttributes = (AudioAttributes)bundle.getParcelable("audioAttributes");
            Object object = audioAttributes;
            if (audioAttributes == null) {
                int n = bundle.getInt("streamType", 3);
                object = new AudioAttributes.Builder().setLegacyStreamType(n);
                n = bl ? 1 : 4;
                object = ((AudioAttributes.Builder)object).setContentType(n).build();
            }
            return new AudioOutputParams(bundle.getInt("sessionId", 0), bundle.getFloat("volume", 1.0f), bundle.getFloat("pan", 0.0f), (AudioAttributes)object);
        }
    }

    private class AudioSpeechItem
    extends UtteranceSpeechItemWithParams {
        private final AudioPlaybackQueueItem mItem;

        public AudioSpeechItem(Object object, int n, int n2, Bundle bundle, String string2, Uri uri) {
            super(object, n, n2, bundle, string2);
            this.mItem = new AudioPlaybackQueueItem(this, this.getCallerIdentity(), TextToSpeechService.this, uri, this.getAudioParams());
        }

        @Override
        AudioOutputParams getAudioParams() {
            return AudioOutputParams.createFromParamsBundle(this.mParams, false);
        }

        @Override
        public String getUtteranceId() {
            return this.getStringParam(this.mParams, "utteranceId", null);
        }

        @Override
        public boolean isValid() {
            return true;
        }

        @Override
        protected void playImpl() {
            TextToSpeechService.this.mAudioPlaybackHandler.enqueue(this.mItem);
        }

        @Override
        protected void stopImpl() {
        }
    }

    private class CallbackMap
    extends RemoteCallbackList<ITextToSpeechCallback> {
        private final HashMap<IBinder, ITextToSpeechCallback> mCallerToCallback = new HashMap();

        private CallbackMap() {
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        private ITextToSpeechCallback getCallbackFor(Object hashMap) {
            IBinder iBinder = (IBinder)((Object)hashMap);
            hashMap = this.mCallerToCallback;
            synchronized (hashMap) {
                return this.mCallerToCallback.get(iBinder);
            }
        }

        public void dispatchOnAudioAvailable(Object object, String charSequence, byte[] arrby) {
            if ((object = this.getCallbackFor(object)) == null) {
                return;
            }
            try {
                object.onAudioAvailable((String)charSequence, arrby);
            }
            catch (RemoteException remoteException) {
                charSequence = new StringBuilder();
                ((StringBuilder)charSequence).append("Callback dispatchOnAudioAvailable(String, byte[]) failed: ");
                ((StringBuilder)charSequence).append(remoteException);
                Log.e(TextToSpeechService.TAG, ((StringBuilder)charSequence).toString());
            }
        }

        public void dispatchOnBeginSynthesis(Object object, String string2, int n, int n2, int n3) {
            if ((object = this.getCallbackFor(object)) == null) {
                return;
            }
            try {
                object.onBeginSynthesis(string2, n, n2, n3);
            }
            catch (RemoteException remoteException) {
                object = new StringBuilder();
                ((StringBuilder)object).append("Callback dispatchOnBeginSynthesis(String, int, int, int) failed: ");
                ((StringBuilder)object).append(remoteException);
                Log.e(TextToSpeechService.TAG, ((StringBuilder)object).toString());
            }
        }

        public void dispatchOnError(Object object, String string2, int n) {
            if ((object = this.getCallbackFor(object)) == null) {
                return;
            }
            try {
                object.onError(string2, n);
            }
            catch (RemoteException remoteException) {
                object = new StringBuilder();
                ((StringBuilder)object).append("Callback onError failed: ");
                ((StringBuilder)object).append(remoteException);
                Log.e(TextToSpeechService.TAG, ((StringBuilder)object).toString());
            }
        }

        public void dispatchOnRangeStart(Object object, String string2, int n, int n2, int n3) {
            if ((object = this.getCallbackFor(object)) == null) {
                return;
            }
            try {
                object.onRangeStart(string2, n, n2, n3);
            }
            catch (RemoteException remoteException) {
                object = new StringBuilder();
                ((StringBuilder)object).append("Callback dispatchOnRangeStart(String, int, int, int) failed: ");
                ((StringBuilder)object).append(remoteException);
                Log.e(TextToSpeechService.TAG, ((StringBuilder)object).toString());
            }
        }

        public void dispatchOnStart(Object object, String charSequence) {
            if ((object = this.getCallbackFor(object)) == null) {
                return;
            }
            try {
                object.onStart((String)charSequence);
            }
            catch (RemoteException remoteException) {
                charSequence = new StringBuilder();
                ((StringBuilder)charSequence).append("Callback onStart failed: ");
                ((StringBuilder)charSequence).append(remoteException);
                Log.e(TextToSpeechService.TAG, ((StringBuilder)charSequence).toString());
            }
        }

        public void dispatchOnStop(Object object, String charSequence, boolean bl) {
            if ((object = this.getCallbackFor(object)) == null) {
                return;
            }
            try {
                object.onStop((String)charSequence, bl);
            }
            catch (RemoteException remoteException) {
                charSequence = new StringBuilder();
                ((StringBuilder)charSequence).append("Callback onStop failed: ");
                ((StringBuilder)charSequence).append(remoteException);
                Log.e(TextToSpeechService.TAG, ((StringBuilder)charSequence).toString());
            }
        }

        public void dispatchOnSuccess(Object object, String charSequence) {
            if ((object = this.getCallbackFor(object)) == null) {
                return;
            }
            try {
                object.onSuccess((String)charSequence);
            }
            catch (RemoteException remoteException) {
                charSequence = new StringBuilder();
                ((StringBuilder)charSequence).append("Callback onDone failed: ");
                ((StringBuilder)charSequence).append(remoteException);
                Log.e(TextToSpeechService.TAG, ((StringBuilder)charSequence).toString());
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void kill() {
            HashMap<IBinder, ITextToSpeechCallback> hashMap = this.mCallerToCallback;
            synchronized (hashMap) {
                this.mCallerToCallback.clear();
                super.kill();
                return;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void onCallbackDied(ITextToSpeechCallback object, Object object2) {
            object2 = (IBinder)object2;
            object = this.mCallerToCallback;
            synchronized (object) {
                this.mCallerToCallback.remove(object2);
            }
            TextToSpeechService.this.mSynthHandler.stopForApp(object2);
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public void setCallback(IBinder object, ITextToSpeechCallback iTextToSpeechCallback) {
            HashMap<IBinder, ITextToSpeechCallback> hashMap = this.mCallerToCallback;
            synchronized (hashMap) {
                if (iTextToSpeechCallback != null) {
                    this.register(iTextToSpeechCallback, object);
                    object = this.mCallerToCallback.put((IBinder)object, iTextToSpeechCallback);
                } else {
                    object = this.mCallerToCallback.remove(object);
                }
                if (object != null && object != iTextToSpeechCallback) {
                    this.unregister(object);
                }
                return;
            }
        }
    }

    private class LoadLanguageItem
    extends SpeechItem {
        private final String mCountry;
        private final String mLanguage;
        private final String mVariant;

        public LoadLanguageItem(Object object, int n, int n2, String string2, String string3, String string4) {
            super(object, n, n2);
            this.mLanguage = string2;
            this.mCountry = string3;
            this.mVariant = string4;
        }

        @Override
        public boolean isValid() {
            return true;
        }

        @Override
        protected void playImpl() {
            TextToSpeechService.this.onLoadLanguage(this.mLanguage, this.mCountry, this.mVariant);
        }

        @Override
        protected void stopImpl() {
        }
    }

    private class LoadVoiceItem
    extends SpeechItem {
        private final String mVoiceName;

        public LoadVoiceItem(Object object, int n, int n2, String string2) {
            super(object, n, n2);
            this.mVoiceName = string2;
        }

        @Override
        public boolean isValid() {
            return true;
        }

        @Override
        protected void playImpl() {
            TextToSpeechService.this.onLoadVoice(this.mVoiceName);
        }

        @Override
        protected void stopImpl() {
        }
    }

    private class SilenceSpeechItem
    extends UtteranceSpeechItem {
        private final long mDuration;
        private final String mUtteranceId;

        public SilenceSpeechItem(Object object, int n, int n2, String string2, long l) {
            super(object, n, n2);
            this.mUtteranceId = string2;
            this.mDuration = l;
        }

        @Override
        public String getUtteranceId() {
            return this.mUtteranceId;
        }

        @Override
        public boolean isValid() {
            return true;
        }

        @Override
        protected void playImpl() {
            TextToSpeechService.this.mAudioPlaybackHandler.enqueue(new SilencePlaybackQueueItem(this, this.getCallerIdentity(), this.mDuration));
        }

        @Override
        protected void stopImpl() {
        }
    }

    private abstract class SpeechItem {
        private final Object mCallerIdentity;
        private final int mCallerPid;
        private final int mCallerUid;
        private boolean mStarted = false;
        private boolean mStopped = false;

        public SpeechItem(Object object, int n, int n2) {
            this.mCallerIdentity = object;
            this.mCallerUid = n;
            this.mCallerPid = n2;
        }

        public Object getCallerIdentity() {
            return this.mCallerIdentity;
        }

        public int getCallerPid() {
            return this.mCallerPid;
        }

        public int getCallerUid() {
            return this.mCallerUid;
        }

        protected boolean isStarted() {
            synchronized (this) {
                boolean bl = this.mStarted;
                return bl;
            }
        }

        protected boolean isStopped() {
            synchronized (this) {
                boolean bl = this.mStopped;
                return bl;
            }
        }

        public abstract boolean isValid();

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public void play() {
            synchronized (this) {
                if (!this.mStarted) {
                    this.mStarted = true;
                    // MONITOREXIT [2, 3] lbl4 : MonitorExitStatement: MONITOREXIT : this
                    this.playImpl();
                    return;
                }
                IllegalStateException illegalStateException = new IllegalStateException("play() called twice");
                throw illegalStateException;
            }
        }

        protected abstract void playImpl();

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public void stop() {
            synchronized (this) {
                if (!this.mStopped) {
                    this.mStopped = true;
                    // MONITOREXIT [2, 3] lbl4 : MonitorExitStatement: MONITOREXIT : this
                    this.stopImpl();
                    return;
                }
                IllegalStateException illegalStateException = new IllegalStateException("stop() called twice");
                throw illegalStateException;
            }
        }

        protected abstract void stopImpl();
    }

    private class SynthHandler
    extends Handler {
        private SpeechItem mCurrentSpeechItem;
        private int mFlushAll;
        private List<Object> mFlushedObjects;

        public SynthHandler(Looper looper) {
            super(looper);
            this.mCurrentSpeechItem = null;
            this.mFlushedObjects = new ArrayList<Object>();
            this.mFlushAll = 0;
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        private void endFlushingSpeechItems(Object object) {
            List<Object> list = this.mFlushedObjects;
            synchronized (list) {
                if (object == null) {
                    --this.mFlushAll;
                } else {
                    this.mFlushedObjects.remove(object);
                }
                return;
            }
        }

        private SpeechItem getCurrentSpeechItem() {
            synchronized (this) {
                SpeechItem speechItem = this.mCurrentSpeechItem;
                return speechItem;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        private boolean isFlushed(SpeechItem speechItem) {
            List<Object> list = this.mFlushedObjects;
            synchronized (list) {
                if (this.mFlushAll > 0) return true;
                if (!this.mFlushedObjects.contains(speechItem.getCallerIdentity())) return false;
                return true;
            }
        }

        private SpeechItem maybeRemoveCurrentSpeechItem(Object object) {
            synchronized (this) {
                if (this.mCurrentSpeechItem != null && this.mCurrentSpeechItem.getCallerIdentity() == object) {
                    object = this.mCurrentSpeechItem;
                    this.mCurrentSpeechItem = null;
                    return object;
                }
                return null;
            }
        }

        private SpeechItem removeCurrentSpeechItem() {
            synchronized (this) {
                SpeechItem speechItem = this.mCurrentSpeechItem;
                this.mCurrentSpeechItem = null;
                return speechItem;
            }
        }

        private boolean setCurrentSpeechItem(SpeechItem speechItem) {
            synchronized (this) {
                block5 : {
                    if (speechItem != null) {
                        boolean bl = this.isFlushed(speechItem);
                        if (!bl) break block5;
                        return false;
                    }
                }
                this.mCurrentSpeechItem = speechItem;
                return true;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        private void startFlushingSpeechItems(Object object) {
            List<Object> list = this.mFlushedObjects;
            synchronized (list) {
                if (object == null) {
                    ++this.mFlushAll;
                } else {
                    this.mFlushedObjects.add(object);
                }
                return;
            }
        }

        public int enqueueSpeechItem(int n, final SpeechItem speechItem) {
            UtteranceProgressDispatcher utteranceProgressDispatcher = null;
            if (speechItem instanceof UtteranceProgressDispatcher) {
                utteranceProgressDispatcher = (UtteranceProgressDispatcher)((Object)speechItem);
            }
            if (!speechItem.isValid()) {
                if (utteranceProgressDispatcher != null) {
                    utteranceProgressDispatcher.dispatchOnError(-8);
                }
                return -1;
            }
            if (n == 0) {
                this.stopForApp(speechItem.getCallerIdentity());
            } else if (n == 2) {
                this.stopAll();
            }
            Message message = Message.obtain((Handler)this, new Runnable(){

                @Override
                public void run() {
                    if (SynthHandler.this.setCurrentSpeechItem(speechItem)) {
                        speechItem.play();
                        SynthHandler.this.removeCurrentSpeechItem();
                    } else {
                        speechItem.stop();
                    }
                }
            });
            message.obj = speechItem.getCallerIdentity();
            if (this.sendMessage(message)) {
                return 0;
            }
            Log.w(TextToSpeechService.TAG, "SynthThread has quit");
            if (utteranceProgressDispatcher != null) {
                utteranceProgressDispatcher.dispatchOnError(-4);
            }
            return -1;
        }

        public boolean isSpeaking() {
            boolean bl = this.getCurrentSpeechItem() != null;
            return bl;
        }

        public void quit() {
            this.getLooper().quit();
            SpeechItem speechItem = this.removeCurrentSpeechItem();
            if (speechItem != null) {
                speechItem.stop();
            }
        }

        public int stopAll() {
            this.startFlushingSpeechItems(null);
            SpeechItem speechItem = this.removeCurrentSpeechItem();
            if (speechItem != null) {
                speechItem.stop();
            }
            TextToSpeechService.this.mAudioPlaybackHandler.stop();
            this.sendMessage(Message.obtain((Handler)this, new Runnable(){

                @Override
                public void run() {
                    SynthHandler.this.endFlushingSpeechItems(null);
                }
            }));
            return 0;
        }

        public int stopForApp(final Object object) {
            if (object == null) {
                return -1;
            }
            this.startFlushingSpeechItems(object);
            SpeechItem speechItem = this.maybeRemoveCurrentSpeechItem(object);
            if (speechItem != null) {
                speechItem.stop();
            }
            TextToSpeechService.this.mAudioPlaybackHandler.stopForApp(object);
            this.sendMessage(Message.obtain((Handler)this, new Runnable(){

                @Override
                public void run() {
                    SynthHandler.this.endFlushingSpeechItems(object);
                }
            }));
            return 0;
        }

    }

    private class SynthThread
    extends HandlerThread
    implements MessageQueue.IdleHandler {
        private boolean mFirstIdle;

        public SynthThread() {
            super(TextToSpeechService.SYNTH_THREAD_NAME, 0);
            this.mFirstIdle = true;
        }

        private void broadcastTtsQueueProcessingCompleted() {
            Intent intent = new Intent("android.speech.tts.TTS_QUEUE_PROCESSING_COMPLETED");
            TextToSpeechService.this.sendBroadcast(intent);
        }

        @Override
        protected void onLooperPrepared() {
            this.getLooper().getQueue().addIdleHandler(this);
        }

        @Override
        public boolean queueIdle() {
            if (this.mFirstIdle) {
                this.mFirstIdle = false;
            } else {
                this.broadcastTtsQueueProcessingCompleted();
            }
            return true;
        }
    }

    class SynthesisSpeechItem
    extends UtteranceSpeechItemWithParams {
        private final int mCallerUid;
        private final String[] mDefaultLocale;
        private final EventLogger mEventLogger;
        private AbstractSynthesisCallback mSynthesisCallback;
        private final SynthesisRequest mSynthesisRequest;
        private final CharSequence mText;

        public SynthesisSpeechItem(Object object, int n, int n2, Bundle bundle, String string2, CharSequence charSequence) {
            super(object, n, n2, bundle, string2);
            this.mText = charSequence;
            this.mCallerUid = n;
            this.mSynthesisRequest = new SynthesisRequest(this.mText, this.mParams);
            this.mDefaultLocale = TextToSpeechService.this.getSettingsLocale();
            this.setRequestParams(this.mSynthesisRequest);
            this.mEventLogger = new EventLogger(this.mSynthesisRequest, n, n2, TextToSpeechService.this.mPackageName);
        }

        private String getCountry() {
            if (!this.hasLanguage()) {
                return this.mDefaultLocale[1];
            }
            return this.getStringParam(this.mParams, "country", "");
        }

        private String getVariant() {
            if (!this.hasLanguage()) {
                return this.mDefaultLocale[2];
            }
            return this.getStringParam(this.mParams, "variant", "");
        }

        private void setRequestParams(SynthesisRequest synthesisRequest) {
            String string2 = this.getVoiceName();
            synthesisRequest.setLanguage(this.getLanguage(), this.getCountry(), this.getVariant());
            if (!TextUtils.isEmpty(string2)) {
                synthesisRequest.setVoiceName(this.getVoiceName());
            }
            synthesisRequest.setSpeechRate(this.getSpeechRate());
            synthesisRequest.setCallerUid(this.mCallerUid);
            synthesisRequest.setPitch(this.getPitch());
        }

        protected AbstractSynthesisCallback createSynthesisCallback() {
            return new PlaybackSynthesisCallback(this.getAudioParams(), TextToSpeechService.this.mAudioPlaybackHandler, this, this.getCallerIdentity(), this.mEventLogger, false);
        }

        public String getLanguage() {
            return this.getStringParam(this.mParams, "language", this.mDefaultLocale[0]);
        }

        public CharSequence getText() {
            return this.mText;
        }

        public String getVoiceName() {
            return this.getStringParam(this.mParams, "voiceName", "");
        }

        @Override
        public boolean isValid() {
            CharSequence charSequence = this.mText;
            if (charSequence == null) {
                Log.e(TextToSpeechService.TAG, "null synthesis text");
                return false;
            }
            if (charSequence.length() > TextToSpeech.getMaxSpeechInputLength()) {
                charSequence = new StringBuilder();
                ((StringBuilder)charSequence).append("Text too long: ");
                ((StringBuilder)charSequence).append(this.mText.length());
                ((StringBuilder)charSequence).append(" chars");
                Log.w(TextToSpeechService.TAG, ((StringBuilder)charSequence).toString());
                return false;
            }
            return true;
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        protected void playImpl() {
            AbstractSynthesisCallback abstractSynthesisCallback;
            this.mEventLogger.onRequestProcessingStart();
            synchronized (this) {
                if (this.isStopped()) {
                    return;
                }
                abstractSynthesisCallback = this.mSynthesisCallback = this.createSynthesisCallback();
            }
            TextToSpeechService.this.onSynthesizeText(this.mSynthesisRequest, abstractSynthesisCallback);
            if (abstractSynthesisCallback.hasStarted() && !abstractSynthesisCallback.hasFinished()) {
                abstractSynthesisCallback.done();
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         * Converted monitor instructions to comments
         * Lifted jumps to return sites
         */
        @Override
        protected void stopImpl() {
            // MONITORENTER : this
            AbstractSynthesisCallback abstractSynthesisCallback = this.mSynthesisCallback;
            // MONITOREXIT : this
            if (abstractSynthesisCallback != null) {
                abstractSynthesisCallback.stop();
                TextToSpeechService.this.onStop();
                return;
            }
            this.dispatchOnStop();
        }
    }

    private class SynthesisToFileOutputStreamSpeechItem
    extends SynthesisSpeechItem {
        private final FileOutputStream mFileOutputStream;

        public SynthesisToFileOutputStreamSpeechItem(Object object, int n, int n2, Bundle bundle, String string2, CharSequence charSequence, FileOutputStream fileOutputStream) {
            super(object, n, n2, bundle, string2, charSequence);
            this.mFileOutputStream = fileOutputStream;
        }

        @Override
        protected AbstractSynthesisCallback createSynthesisCallback() {
            return new FileSynthesisCallback(this.mFileOutputStream.getChannel(), this, false);
        }

        @Override
        protected void playImpl() {
            super.playImpl();
            try {
                this.mFileOutputStream.close();
            }
            catch (IOException iOException) {
                Log.w(TextToSpeechService.TAG, "Failed to close output file", iOException);
            }
        }
    }

    static interface UtteranceProgressDispatcher {
        public void dispatchOnAudioAvailable(byte[] var1);

        public void dispatchOnBeginSynthesis(int var1, int var2, int var3);

        public void dispatchOnError(int var1);

        public void dispatchOnRangeStart(int var1, int var2, int var3);

        public void dispatchOnStart();

        public void dispatchOnStop();

        public void dispatchOnSuccess();
    }

    private abstract class UtteranceSpeechItem
    extends SpeechItem
    implements UtteranceProgressDispatcher {
        public UtteranceSpeechItem(Object object, int n, int n2) {
            super(object, n, n2);
        }

        @Override
        public void dispatchOnAudioAvailable(byte[] arrby) {
            String string2 = this.getUtteranceId();
            if (string2 != null) {
                TextToSpeechService.this.mCallbacks.dispatchOnAudioAvailable(this.getCallerIdentity(), string2, arrby);
            }
        }

        @Override
        public void dispatchOnBeginSynthesis(int n, int n2, int n3) {
            String string2 = this.getUtteranceId();
            if (string2 != null) {
                TextToSpeechService.this.mCallbacks.dispatchOnBeginSynthesis(this.getCallerIdentity(), string2, n, n2, n3);
            }
        }

        @Override
        public void dispatchOnError(int n) {
            String string2 = this.getUtteranceId();
            if (string2 != null) {
                TextToSpeechService.this.mCallbacks.dispatchOnError(this.getCallerIdentity(), string2, n);
            }
        }

        @Override
        public void dispatchOnRangeStart(int n, int n2, int n3) {
            String string2 = this.getUtteranceId();
            if (string2 != null) {
                TextToSpeechService.this.mCallbacks.dispatchOnRangeStart(this.getCallerIdentity(), string2, n, n2, n3);
            }
        }

        @Override
        public void dispatchOnStart() {
            String string2 = this.getUtteranceId();
            if (string2 != null) {
                TextToSpeechService.this.mCallbacks.dispatchOnStart(this.getCallerIdentity(), string2);
            }
        }

        @Override
        public void dispatchOnStop() {
            String string2 = this.getUtteranceId();
            if (string2 != null) {
                TextToSpeechService.this.mCallbacks.dispatchOnStop(this.getCallerIdentity(), string2, this.isStarted());
            }
        }

        @Override
        public void dispatchOnSuccess() {
            String string2 = this.getUtteranceId();
            if (string2 != null) {
                TextToSpeechService.this.mCallbacks.dispatchOnSuccess(this.getCallerIdentity(), string2);
            }
        }

        float getFloatParam(Bundle bundle, String string2, float f) {
            if (bundle != null) {
                f = bundle.getFloat(string2, f);
            }
            return f;
        }

        int getIntParam(Bundle bundle, String string2, int n) {
            if (bundle != null) {
                n = bundle.getInt(string2, n);
            }
            return n;
        }

        String getStringParam(Bundle bundle, String string2, String string3) {
            if (bundle != null) {
                string3 = bundle.getString(string2, string3);
            }
            return string3;
        }

        public abstract String getUtteranceId();
    }

    private abstract class UtteranceSpeechItemWithParams
    extends UtteranceSpeechItem {
        protected final Bundle mParams;
        protected final String mUtteranceId;

        UtteranceSpeechItemWithParams(Object object, int n, int n2, Bundle bundle, String string2) {
            super(object, n, n2);
            this.mParams = bundle;
            this.mUtteranceId = string2;
        }

        AudioOutputParams getAudioParams() {
            return AudioOutputParams.createFromParamsBundle(this.mParams, true);
        }

        int getPitch() {
            return this.getIntParam(this.mParams, "pitch", TextToSpeechService.this.getDefaultPitch());
        }

        int getSpeechRate() {
            return this.getIntParam(this.mParams, "rate", TextToSpeechService.this.getDefaultSpeechRate());
        }

        @Override
        public String getUtteranceId() {
            return this.mUtteranceId;
        }

        boolean hasLanguage() {
            return TextUtils.isEmpty(this.getStringParam(this.mParams, "language", null)) ^ true;
        }
    }

}

