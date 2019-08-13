/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.media.audiopolicy.-$
 *  android.media.audiopolicy.-$$Lambda
 *  android.media.audiopolicy.-$$Lambda$AudioPolicy
 *  android.media.audiopolicy.-$$Lambda$AudioPolicy$-ztOT0FT3tzGMUr4lm1gv6dBE4c
 */
package android.media.audiopolicy;

import android.annotation.SystemApi;
import android.app.ActivityManager;
import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioDeviceInfo;
import android.media.AudioFocusInfo;
import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.AudioTrack;
import android.media.IAudioService;
import android.media.audiopolicy.-$;
import android.media.audiopolicy.AudioMix;
import android.media.audiopolicy.AudioPolicyConfig;
import android.media.audiopolicy.IAudioPolicyCallback;
import android.media.audiopolicy._$$Lambda$AudioPolicy$_ztOT0FT3tzGMUr4lm1gv6dBE4c;
import android.media.projection.IMediaProjection;
import android.media.projection.MediaProjection;
import android.os.Binder;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.util.Log;
import android.util.Slog;
import com.android.internal.annotations.GuardedBy;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Stream;

@SystemApi
public class AudioPolicy {
    private static final boolean DEBUG = false;
    public static final int FOCUS_POLICY_DUCKING_DEFAULT = 0;
    public static final int FOCUS_POLICY_DUCKING_IN_APP = 0;
    public static final int FOCUS_POLICY_DUCKING_IN_POLICY = 1;
    private static final int MSG_FOCUS_ABANDON = 5;
    private static final int MSG_FOCUS_GRANT = 1;
    private static final int MSG_FOCUS_LOSS = 2;
    private static final int MSG_FOCUS_REQUEST = 4;
    private static final int MSG_MIX_STATE_UPDATE = 3;
    private static final int MSG_POLICY_STATUS_CHANGE = 0;
    private static final int MSG_VOL_ADJUST = 6;
    public static final int POLICY_STATUS_REGISTERED = 2;
    public static final int POLICY_STATUS_UNREGISTERED = 1;
    private static final String TAG = "AudioPolicy";
    private static IAudioService sService;
    @GuardedBy(value={"mLock"})
    private ArrayList<WeakReference<AudioRecord>> mCaptors;
    private AudioPolicyConfig mConfig;
    private Context mContext;
    private final EventHandler mEventHandler;
    private AudioPolicyFocusListener mFocusListener;
    @GuardedBy(value={"mLock"})
    private ArrayList<WeakReference<AudioTrack>> mInjectors;
    private boolean mIsFocusPolicy;
    private boolean mIsTestFocusPolicy;
    private final Object mLock = new Object();
    private final IAudioPolicyCallback mPolicyCb = new IAudioPolicyCallback.Stub(){

        @Override
        public void notifyAudioFocusAbandon(AudioFocusInfo audioFocusInfo) {
            AudioPolicy.this.sendMsg(5, audioFocusInfo, 0);
        }

        @Override
        public void notifyAudioFocusGrant(AudioFocusInfo audioFocusInfo, int n) {
            AudioPolicy.this.sendMsg(1, audioFocusInfo, n);
        }

        @Override
        public void notifyAudioFocusLoss(AudioFocusInfo audioFocusInfo, boolean bl) {
            AudioPolicy.this.sendMsg(2, audioFocusInfo, (int)bl);
        }

        @Override
        public void notifyAudioFocusRequest(AudioFocusInfo audioFocusInfo, int n) {
            AudioPolicy.this.sendMsg(4, audioFocusInfo, n);
        }

        @Override
        public void notifyMixStateUpdate(String string2, int n) {
            for (AudioMix audioMix : AudioPolicy.this.mConfig.getMixes()) {
                if (!audioMix.getRegistration().equals(string2)) continue;
                audioMix.mMixState = n;
                AudioPolicy.this.sendMsg(3, audioMix, 0);
            }
        }

        @Override
        public void notifyVolumeAdjust(int n) {
            AudioPolicy.this.sendMsg(6, null, n);
        }
    };
    private final MediaProjection mProjection;
    private String mRegistrationId;
    private int mStatus;
    private AudioPolicyStatusListener mStatusListener;
    private final AudioPolicyVolumeCallback mVolCb;

    private AudioPolicy(AudioPolicyConfig object, Context context, Looper looper, AudioPolicyFocusListener audioPolicyFocusListener, AudioPolicyStatusListener audioPolicyStatusListener, boolean bl, boolean bl2, AudioPolicyVolumeCallback audioPolicyVolumeCallback, MediaProjection mediaProjection) {
        this.mConfig = object;
        this.mStatus = 1;
        this.mContext = context;
        object = looper;
        if (looper == null) {
            object = Looper.getMainLooper();
        }
        if (object != null) {
            this.mEventHandler = new EventHandler(this, (Looper)object);
        } else {
            this.mEventHandler = null;
            Log.e(TAG, "No event handler due to looper without a thread");
        }
        this.mFocusListener = audioPolicyFocusListener;
        this.mStatusListener = audioPolicyStatusListener;
        this.mIsFocusPolicy = bl;
        this.mIsTestFocusPolicy = bl2;
        this.mVolCb = audioPolicyVolumeCallback;
        this.mProjection = mediaProjection;
    }

    private static String addressForTag(AudioMix audioMix) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("addr=");
        stringBuilder.append(audioMix.getRegistration());
        return stringBuilder.toString();
    }

    private int checkCallingOrSelfPermission(String string2) {
        Context context = this.mContext;
        if (context != null) {
            return context.checkCallingOrSelfPermission(string2);
        }
        Slog.v(TAG, "Null context, checking permission via ActivityManager");
        int n = Binder.getCallingPid();
        int n2 = Binder.getCallingUid();
        try {
            n2 = ActivityManager.getService().checkPermission(string2, n, n2);
            return n2;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    private void checkMixReadyToUse(AudioMix object, boolean bl) throws IllegalArgumentException {
        if (object == null) {
            object = bl ? "Invalid null AudioMix for AudioTrack creation" : "Invalid null AudioMix for AudioRecord creation";
            throw new IllegalArgumentException((String)object);
        }
        if (this.mConfig.mMixes.contains(object)) {
            if ((((AudioMix)object).getRouteFlags() & 2) == 2) {
                if (bl && ((AudioMix)object).getMixType() != 1) {
                    throw new IllegalArgumentException("Invalid AudioMix: not defined for being a recording source");
                }
                if (!bl && ((AudioMix)object).getMixType() != 0) {
                    throw new IllegalArgumentException("Invalid AudioMix: not defined for capturing playback");
                }
                return;
            }
            throw new IllegalArgumentException("Invalid AudioMix: not defined for loop back");
        }
        throw new IllegalArgumentException("Invalid mix: not part of this policy");
    }

    private static IAudioService getService() {
        IAudioService iAudioService = sService;
        if (iAudioService != null) {
            return iAudioService;
        }
        sService = IAudioService.Stub.asInterface(ServiceManager.getService("audio"));
        return sService;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private boolean isLoopbackRenderPolicy() {
        Object object = this.mLock;
        synchronized (object) {
            return this.mConfig.mMixes.stream().allMatch(_$$Lambda$AudioPolicy$_ztOT0FT3tzGMUr4lm1gv6dBE4c.INSTANCE);
        }
    }

    static /* synthetic */ boolean lambda$isLoopbackRenderPolicy$0(AudioMix audioMix) {
        boolean bl = audioMix.getRouteFlags() == 3;
        return bl;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void onPolicyStatusChange() {
        AudioPolicyStatusListener audioPolicyStatusListener;
        Object object = this.mLock;
        synchronized (object) {
            if (this.mStatusListener == null) {
                return;
            }
            audioPolicyStatusListener = this.mStatusListener;
        }
        audioPolicyStatusListener.onStatusChange();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private boolean policyReadyToUse() {
        block7 : {
            StringBuilder stringBuilder;
            Object object = this.mLock;
            synchronized (object) {
                if (this.mStatus != 2) {
                    Log.e(TAG, "Cannot use unregistered AudioPolicy");
                    return false;
                }
                if (this.mRegistrationId == null) {
                    Log.e(TAG, "Cannot use unregistered AudioPolicy");
                    return false;
                }
            }
            boolean bl = this.checkCallingOrSelfPermission("android.permission.MODIFY_AUDIO_ROUTING") == 0;
            try {
                boolean bl2;
                boolean bl3 = this.mProjection != null && (bl2 = this.mProjection.getProjection().canProjectAudio());
                if (this.isLoopbackRenderPolicy() && bl3 || bl) break block7;
                stringBuilder = new StringBuilder();
                stringBuilder.append("Cannot use AudioPolicy for pid ");
            }
            catch (RemoteException remoteException) {
                Log.e(TAG, "Failed to check if MediaProjection#canProjectAudio");
                throw remoteException.rethrowFromSystemServer();
            }
            stringBuilder.append(Binder.getCallingPid());
            stringBuilder.append(" / uid ");
            stringBuilder.append(Binder.getCallingUid());
            stringBuilder.append(", needs MODIFY_AUDIO_ROUTING or MediaProjection that can project audio.");
            Slog.w(TAG, stringBuilder.toString());
            return false;
        }
        return true;
    }

    private void sendMsg(int n) {
        EventHandler eventHandler = this.mEventHandler;
        if (eventHandler != null) {
            eventHandler.sendEmptyMessage(n);
        }
    }

    private void sendMsg(int n, Object object, int n2) {
        EventHandler eventHandler = this.mEventHandler;
        if (eventHandler != null) {
            eventHandler.sendMessage(eventHandler.obtainMessage(n, n2, 0, object));
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public int attachMixes(List<AudioMix> object) {
        if (object == null) {
            throw new IllegalArgumentException("Illegal null list of AudioMix");
        }
        Object object2 = this.mLock;
        synchronized (object2) {
            if (this.mStatus != 2) {
                object = new IllegalStateException("Cannot alter unregistered AudioPolicy");
                throw object;
            }
            ArrayList<AudioMix> arrayList = new ArrayList<AudioMix>(object.size());
            Object object3 = object.iterator();
            while (object3.hasNext()) {
                object = (AudioMix)object3.next();
                if (object == null) {
                    object = new IllegalArgumentException("Illegal null AudioMix in attachMixes");
                    throw object;
                }
                arrayList.add((AudioMix)object);
            }
            object = new AudioPolicyConfig(arrayList);
            object3 = AudioPolicy.getService();
            try {
                int n = object3.addMixForPolicy((AudioPolicyConfig)object, this.cb());
                if (n == 0) {
                    this.mConfig.add(arrayList);
                }
                return n;
            }
            catch (RemoteException remoteException) {
                Log.e(TAG, "Dead object in attachMixes", remoteException);
                return -1;
            }
        }
    }

    public IAudioPolicyCallback cb() {
        return this.mPolicyCb;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public AudioRecord createAudioRecordSink(AudioMix object) throws IllegalArgumentException {
        if (!this.policyReadyToUse()) {
            Log.e(TAG, "Cannot create AudioRecord sink for AudioMix");
            return null;
        }
        this.checkMixReadyToUse((AudioMix)object, false);
        Object object2 = new AudioFormat.Builder(((AudioMix)object).getFormat()).setChannelMask(AudioFormat.inChannelMaskFromOutChannelMask(((AudioMix)object).getFormat().getChannelMask())).build();
        object2 = new AudioRecord(new AudioAttributes.Builder().setInternalCapturePreset(8).addTag(AudioPolicy.addressForTag((AudioMix)object)).addTag("fixedVolume").build(), (AudioFormat)object2, AudioRecord.getMinBufferSize(((AudioMix)object).getFormat().getSampleRate(), 12, ((AudioMix)object).getFormat().getEncoding()), 0);
        object = this.mLock;
        synchronized (object) {
            ArrayList<WeakReference<AudioRecord>> arrayList;
            if (this.mCaptors == null) {
                this.mCaptors = arrayList = new ArrayList<WeakReference<AudioRecord>>(1);
            }
            arrayList = this.mCaptors;
            WeakReference<Object> weakReference = new WeakReference<Object>(object2);
            arrayList.add(weakReference);
            return object2;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public AudioTrack createAudioTrackSource(AudioMix object) throws IllegalArgumentException {
        if (!this.policyReadyToUse()) {
            Log.e(TAG, "Cannot create AudioTrack source for AudioMix");
            return null;
        }
        this.checkMixReadyToUse((AudioMix)object, true);
        AudioTrack audioTrack = new AudioTrack(new AudioAttributes.Builder().setUsage(15).addTag(AudioPolicy.addressForTag((AudioMix)object)).build(), ((AudioMix)object).getFormat(), AudioTrack.getMinBufferSize(((AudioMix)object).getFormat().getSampleRate(), ((AudioMix)object).getFormat().getChannelMask(), ((AudioMix)object).getFormat().getEncoding()), 1, 0);
        object = this.mLock;
        synchronized (object) {
            ArrayList<WeakReference<AudioTrack>> arrayList;
            if (this.mInjectors == null) {
                this.mInjectors = arrayList = new ArrayList<WeakReference<AudioTrack>>(1);
            }
            arrayList = this.mInjectors;
            WeakReference<AudioTrack> weakReference = new WeakReference<AudioTrack>(audioTrack);
            arrayList.add(weakReference);
            return audioTrack;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public int detachMixes(List<AudioMix> object) {
        if (object == null) {
            throw new IllegalArgumentException("Illegal null list of AudioMix");
        }
        Object object2 = this.mLock;
        synchronized (object2) {
            Object object3;
            if (this.mStatus != 2) {
                object = new IllegalStateException("Cannot alter unregistered AudioPolicy");
                throw object;
            }
            ArrayList<AudioMix> arrayList = new ArrayList<AudioMix>(object.size());
            object = object.iterator();
            while (object.hasNext()) {
                object3 = (AudioMix)object.next();
                if (object3 == null) {
                    object = new IllegalArgumentException("Illegal null AudioMix in detachMixes");
                    throw object;
                }
                arrayList.add((AudioMix)object3);
            }
            object3 = new AudioPolicyConfig(arrayList);
            object = AudioPolicy.getService();
            try {
                int n = object.removeMixForPolicy((AudioPolicyConfig)object3, this.cb());
                if (n == 0) {
                    this.mConfig.remove(arrayList);
                }
                return n;
            }
            catch (RemoteException remoteException) {
                Log.e(TAG, "Dead object in detachMixes", remoteException);
                return -1;
            }
        }
    }

    public AudioPolicyConfig getConfig() {
        return this.mConfig;
    }

    public int getFocusDuckingBehavior() {
        return this.mConfig.mDuckingPolicy;
    }

    public MediaProjection getMediaProjection() {
        return this.mProjection;
    }

    public int getStatus() {
        return this.mStatus;
    }

    public boolean hasFocusListener() {
        boolean bl = this.mFocusListener != null;
        return bl;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void invalidateCaptorsAndInjectors() {
        if (!this.policyReadyToUse()) {
            return;
        }
        Object object = this.mLock;
        synchronized (object) {
            Object object2;
            Object object3;
            if (this.mInjectors != null) {
                object2 = this.mInjectors.iterator();
                while (object2.hasNext() && (object3 = (AudioTrack)object2.next().get()) != null) {
                    ((AudioTrack)object3).stop();
                    ((AudioTrack)object3).flush();
                }
            }
            if (this.mCaptors != null) {
                object3 = this.mCaptors.iterator();
                while (object3.hasNext() && (object2 = (AudioRecord)object3.next().get()) != null) {
                    ((AudioRecord)object2).stop();
                }
            }
            return;
        }
    }

    public boolean isFocusPolicy() {
        return this.mIsFocusPolicy;
    }

    public boolean isTestFocusPolicy() {
        return this.mIsTestFocusPolicy;
    }

    public boolean isVolumeController() {
        boolean bl = this.mVolCb != null;
        return bl;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @SystemApi
    public boolean removeUidDeviceAffinity(int n) {
        Object object = this.mLock;
        synchronized (object) {
            if (this.mStatus != 2) {
                IllegalStateException illegalStateException = new IllegalStateException("Cannot use unregistered AudioPolicy");
                throw illegalStateException;
            }
            IAudioService iAudioService = AudioPolicy.getService();
            boolean bl = false;
            try {
                n = iAudioService.removeUidDeviceAffinity(this.cb(), n);
                if (n != 0) return bl;
                return true;
            }
            catch (RemoteException remoteException) {
                Log.e(TAG, "Dead object in removeUidDeviceAffinity", remoteException);
                return false;
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public int setFocusDuckingBehavior(int n) throws IllegalArgumentException, IllegalStateException {
        if (n != 0 && n != 1) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Invalid ducking behavior ");
            stringBuilder.append(n);
            throw new IllegalArgumentException(stringBuilder.toString());
        }
        Object object = this.mLock;
        synchronized (object) {
            if (this.mStatus != 2) {
                IllegalStateException illegalStateException = new IllegalStateException("Cannot change ducking behavior for unregistered policy");
                throw illegalStateException;
            }
            if (n == 1 && this.mFocusListener == null) {
                IllegalStateException illegalStateException = new IllegalStateException("Cannot handle ducking without an audio focus listener");
                throw illegalStateException;
            }
            IAudioService iAudioService = AudioPolicy.getService();
            try {
                int n2 = iAudioService.setFocusPropertiesForPolicy(n, this.cb());
                if (n2 == 0) {
                    this.mConfig.mDuckingPolicy = n;
                }
                return n2;
            }
            catch (RemoteException remoteException) {
                Log.e(TAG, "Dead object in setFocusPropertiesForPolicy for behavior", remoteException);
                return -1;
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void setRegistration(String string2) {
        Object object = this.mLock;
        synchronized (object) {
            this.mRegistrationId = string2;
            this.mConfig.setRegistration(string2);
            this.mStatus = string2 != null ? 2 : 1;
        }
        this.sendMsg(0);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @SystemApi
    public boolean setUidDeviceAffinity(int n, List<AudioDeviceInfo> object) {
        if (object == null) {
            throw new IllegalArgumentException("Illegal null list of audio devices");
        }
        Object object2 = this.mLock;
        synchronized (object2) {
            if (this.mStatus != 2) {
                object = new IllegalStateException("Cannot use unregistered AudioPolicy");
                throw object;
            }
            int[] arrn = new int[object.size()];
            String[] arrstring = new String[object.size()];
            int n2 = 0;
            object = object.iterator();
            while (object.hasNext()) {
                AudioDeviceInfo audioDeviceInfo = (AudioDeviceInfo)object.next();
                if (audioDeviceInfo == null) {
                    object = new IllegalArgumentException("Illegal null AudioDeviceInfo in setUidDeviceAffinity");
                    throw object;
                }
                arrn[n2] = AudioDeviceInfo.convertDeviceTypeToInternalDevice(audioDeviceInfo.getType());
                arrstring[n2] = audioDeviceInfo.getAddress();
                ++n2;
            }
            object = AudioPolicy.getService();
            boolean bl = false;
            try {
                n = object.setUidDeviceAffinity(this.cb(), n, arrn, arrstring);
                if (n != 0) return bl;
                return true;
            }
            catch (RemoteException remoteException) {
                Log.e(TAG, "Dead object in setUidDeviceAffinity", remoteException);
                return false;
            }
        }
    }

    public String toLogFriendlyString() {
        String string2 = new String("android.media.audiopolicy.AudioPolicy:\n");
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(string2);
        stringBuilder.append("config=");
        stringBuilder.append(this.mConfig.toLogFriendlyString());
        return stringBuilder.toString();
    }

    public static abstract class AudioPolicyFocusListener {
        public void onAudioFocusAbandon(AudioFocusInfo audioFocusInfo) {
        }

        public void onAudioFocusGrant(AudioFocusInfo audioFocusInfo, int n) {
        }

        public void onAudioFocusLoss(AudioFocusInfo audioFocusInfo, boolean bl) {
        }

        public void onAudioFocusRequest(AudioFocusInfo audioFocusInfo, int n) {
        }
    }

    public static abstract class AudioPolicyStatusListener {
        public void onMixStateUpdate(AudioMix audioMix) {
        }

        public void onStatusChange() {
        }
    }

    public static abstract class AudioPolicyVolumeCallback {
        public void onVolumeAdjustment(int n) {
        }
    }

    public static class Builder {
        private Context mContext;
        private AudioPolicyFocusListener mFocusListener;
        private boolean mIsFocusPolicy = false;
        private boolean mIsTestFocusPolicy = false;
        private Looper mLooper;
        private ArrayList<AudioMix> mMixes = new ArrayList();
        private MediaProjection mProjection;
        private AudioPolicyStatusListener mStatusListener;
        private AudioPolicyVolumeCallback mVolCb;

        public Builder(Context context) {
            this.mContext = context;
        }

        public Builder addMix(AudioMix audioMix) throws IllegalArgumentException {
            if (audioMix != null) {
                this.mMixes.add(audioMix);
                return this;
            }
            throw new IllegalArgumentException("Illegal null AudioMix argument");
        }

        public AudioPolicy build() {
            if (this.mStatusListener != null) {
                for (AudioMix audioMix : this.mMixes) {
                    audioMix.mCallbackFlags |= 1;
                }
            }
            if (this.mIsFocusPolicy && this.mFocusListener == null) {
                throw new IllegalStateException("Cannot be a focus policy without an AudioPolicyFocusListener");
            }
            return new AudioPolicy(new AudioPolicyConfig(this.mMixes), this.mContext, this.mLooper, this.mFocusListener, this.mStatusListener, this.mIsFocusPolicy, this.mIsTestFocusPolicy, this.mVolCb, this.mProjection);
        }

        public void setAudioPolicyFocusListener(AudioPolicyFocusListener audioPolicyFocusListener) {
            this.mFocusListener = audioPolicyFocusListener;
        }

        public void setAudioPolicyStatusListener(AudioPolicyStatusListener audioPolicyStatusListener) {
            this.mStatusListener = audioPolicyStatusListener;
        }

        public Builder setAudioPolicyVolumeCallback(AudioPolicyVolumeCallback audioPolicyVolumeCallback) {
            if (audioPolicyVolumeCallback != null) {
                this.mVolCb = audioPolicyVolumeCallback;
                return this;
            }
            throw new IllegalArgumentException("Invalid null volume callback");
        }

        public Builder setIsAudioFocusPolicy(boolean bl) {
            this.mIsFocusPolicy = bl;
            return this;
        }

        public Builder setIsTestFocusPolicy(boolean bl) {
            this.mIsTestFocusPolicy = bl;
            return this;
        }

        public Builder setLooper(Looper looper) throws IllegalArgumentException {
            if (looper != null) {
                this.mLooper = looper;
                return this;
            }
            throw new IllegalArgumentException("Illegal null Looper argument");
        }

        public Builder setMediaProjection(MediaProjection mediaProjection) {
            if (mediaProjection != null) {
                this.mProjection = mediaProjection;
                return this;
            }
            throw new IllegalArgumentException("Invalid null volume callback");
        }
    }

    private class EventHandler
    extends Handler {
        public EventHandler(AudioPolicy audioPolicy2, Looper looper) {
            super(looper);
        }

        @Override
        public void handleMessage(Message message) {
            switch (message.what) {
                default: {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Unknown event ");
                    stringBuilder.append(message.what);
                    Log.e(AudioPolicy.TAG, stringBuilder.toString());
                    break;
                }
                case 6: {
                    if (AudioPolicy.this.mVolCb != null) {
                        AudioPolicy.this.mVolCb.onVolumeAdjustment(message.arg1);
                        break;
                    }
                    Log.e(AudioPolicy.TAG, "Invalid null volume event");
                    break;
                }
                case 5: {
                    if (AudioPolicy.this.mFocusListener != null) {
                        AudioPolicy.this.mFocusListener.onAudioFocusAbandon((AudioFocusInfo)message.obj);
                        break;
                    }
                    Log.e(AudioPolicy.TAG, "Invalid null focus listener for focus abandon event");
                    break;
                }
                case 4: {
                    if (AudioPolicy.this.mFocusListener != null) {
                        AudioPolicy.this.mFocusListener.onAudioFocusRequest((AudioFocusInfo)message.obj, message.arg1);
                        break;
                    }
                    Log.e(AudioPolicy.TAG, "Invalid null focus listener for focus request event");
                    break;
                }
                case 3: {
                    if (AudioPolicy.this.mStatusListener == null) break;
                    AudioPolicy.this.mStatusListener.onMixStateUpdate((AudioMix)message.obj);
                    break;
                }
                case 2: {
                    if (AudioPolicy.this.mFocusListener == null) break;
                    AudioPolicyFocusListener audioPolicyFocusListener = AudioPolicy.this.mFocusListener;
                    AudioFocusInfo audioFocusInfo = (AudioFocusInfo)message.obj;
                    boolean bl = message.arg1 != 0;
                    audioPolicyFocusListener.onAudioFocusLoss(audioFocusInfo, bl);
                    break;
                }
                case 1: {
                    if (AudioPolicy.this.mFocusListener == null) break;
                    AudioPolicy.this.mFocusListener.onAudioFocusGrant((AudioFocusInfo)message.obj, message.arg1);
                    break;
                }
                case 0: {
                    AudioPolicy.this.onPolicyStatusChange();
                }
            }
        }
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface PolicyStatus {
    }

}

