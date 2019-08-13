/*
 * Decompiled with CFR 0.145.
 */
package android.media;

import android.annotation.SystemApi;
import android.annotation.UnsupportedAppUsage;
import android.app.ActivityThread;
import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioDeviceInfo;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioPlaybackCaptureConfiguration;
import android.media.AudioRecordingConfiguration;
import android.media.AudioRecordingMonitor;
import android.media.AudioRecordingMonitorClient;
import android.media.AudioRecordingMonitorImpl;
import android.media.AudioRouting;
import android.media.AudioTimestamp;
import android.media.IAudioService;
import android.media.MediaRecorder;
import android.media.MediaSyncEvent;
import android.media.MicrophoneDirection;
import android.media.MicrophoneInfo;
import android.media.NativeRoutingEventHandlerDelegate;
import android.media.audiopolicy.AudioMix;
import android.media.audiopolicy.AudioPolicy;
import android.media.projection.MediaProjection;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.PersistableBundle;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.util.ArrayMap;
import android.util.Log;
import android.util.Pair;
import com.android.internal.annotations.GuardedBy;
import com.android.internal.util.Preconditions;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.ref.WeakReference;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Executor;

public class AudioRecord
implements AudioRouting,
MicrophoneDirection,
AudioRecordingMonitor,
AudioRecordingMonitorClient {
    private static final int AUDIORECORD_ERROR_SETUP_INVALIDCHANNELMASK = -17;
    private static final int AUDIORECORD_ERROR_SETUP_INVALIDFORMAT = -18;
    private static final int AUDIORECORD_ERROR_SETUP_INVALIDSOURCE = -19;
    private static final int AUDIORECORD_ERROR_SETUP_NATIVEINITFAILED = -20;
    private static final int AUDIORECORD_ERROR_SETUP_ZEROFRAMECOUNT = -16;
    public static final int ERROR = -1;
    public static final int ERROR_BAD_VALUE = -2;
    public static final int ERROR_DEAD_OBJECT = -6;
    public static final int ERROR_INVALID_OPERATION = -3;
    private static final int NATIVE_EVENT_MARKER = 2;
    private static final int NATIVE_EVENT_NEW_POS = 3;
    public static final int READ_BLOCKING = 0;
    public static final int READ_NON_BLOCKING = 1;
    public static final int RECORDSTATE_RECORDING = 3;
    public static final int RECORDSTATE_STOPPED = 1;
    public static final int STATE_INITIALIZED = 1;
    public static final int STATE_UNINITIALIZED = 0;
    public static final String SUBMIX_FIXED_VOLUME = "fixedVolume";
    public static final int SUCCESS = 0;
    private static final String TAG = "android.media.AudioRecord";
    @UnsupportedAppUsage
    private AudioAttributes mAudioAttributes;
    private AudioPolicy mAudioCapturePolicy;
    private int mAudioFormat;
    private int mChannelCount;
    private int mChannelIndexMask;
    private int mChannelMask;
    private NativeEventHandler mEventHandler = null;
    private final IBinder mICallBack = new Binder();
    @UnsupportedAppUsage
    private Looper mInitializationLooper = null;
    private boolean mIsSubmixFullVolume = false;
    private int mNativeBufferSizeInBytes = 0;
    @UnsupportedAppUsage
    private long mNativeCallbackCookie;
    @UnsupportedAppUsage
    private long mNativeDeviceCallback;
    @UnsupportedAppUsage
    private long mNativeRecorderInJavaObj;
    private OnRecordPositionUpdateListener mPositionListener = null;
    private final Object mPositionListenerLock = new Object();
    private AudioDeviceInfo mPreferredDevice = null;
    private int mRecordSource;
    AudioRecordingMonitorImpl mRecordingInfoImpl = new AudioRecordingMonitorImpl(this);
    private int mRecordingState = 1;
    private final Object mRecordingStateLock = new Object();
    @GuardedBy(value={"mRoutingChangeListeners"})
    private ArrayMap<AudioRouting.OnRoutingChangedListener, NativeRoutingEventHandlerDelegate> mRoutingChangeListeners = new ArrayMap();
    private int mSampleRate;
    private int mSessionId = 0;
    private int mState = 0;

    public AudioRecord(int n, int n2, int n3, int n4, int n5) throws IllegalArgumentException {
        this(new AudioAttributes.Builder().setInternalCapturePreset(n).build(), new AudioFormat.Builder().setChannelMask(AudioRecord.getChannelMaskFromLegacyConfig(n3, true)).setEncoding(n4).setSampleRate(n2).build(), n5, 0);
    }

    AudioRecord(long l) {
        this.mNativeRecorderInJavaObj = 0L;
        this.mNativeCallbackCookie = 0L;
        this.mNativeDeviceCallback = 0L;
        if (l != 0L) {
            this.deferred_connect(l);
        } else {
            this.mState = 0;
        }
    }

    @SystemApi
    public AudioRecord(AudioAttributes object, AudioFormat arrn, int n, int n2) throws IllegalArgumentException {
        this.mRecordingState = 1;
        if (object != null) {
            if (arrn != null) {
                Object object2 = Looper.myLooper();
                this.mInitializationLooper = object2;
                if (object2 == null) {
                    this.mInitializationLooper = Looper.getMainLooper();
                }
                if (((AudioAttributes)object).getCapturePreset() == 8) {
                    object2 = new AudioAttributes.Builder();
                    for (String string2 : ((AudioAttributes)object).getTags()) {
                        if (string2.equalsIgnoreCase(SUBMIX_FIXED_VOLUME)) {
                            this.mIsSubmixFullVolume = true;
                            Log.v(TAG, "Will record from REMOTE_SUBMIX at full fixed volume");
                            continue;
                        }
                        ((AudioAttributes.Builder)object2).addTag(string2);
                    }
                    ((AudioAttributes.Builder)object2).setInternalCapturePreset(((AudioAttributes)object).getCapturePreset());
                    this.mAudioAttributes = ((AudioAttributes.Builder)object2).build();
                } else {
                    this.mAudioAttributes = object;
                }
                int n3 = arrn.getSampleRate();
                if (n3 == 0) {
                    n3 = 0;
                }
                int n4 = (arrn.getPropertySetMask() & 1) != 0 ? arrn.getEncoding() : 1;
                this.audioParamCheck(((AudioAttributes)object).getCapturePreset(), n3, n4);
                if ((arrn.getPropertySetMask() & 8) != 0) {
                    this.mChannelIndexMask = arrn.getChannelIndexMask();
                    this.mChannelCount = arrn.getChannelCount();
                }
                if ((arrn.getPropertySetMask() & 4) != 0) {
                    this.mChannelMask = AudioRecord.getChannelMaskFromLegacyConfig(arrn.getChannelMask(), false);
                    this.mChannelCount = arrn.getChannelCount();
                } else if (this.mChannelIndexMask == 0) {
                    this.mChannelMask = AudioRecord.getChannelMaskFromLegacyConfig(1, false);
                    this.mChannelCount = AudioFormat.channelCountFromInChannelMask(this.mChannelMask);
                }
                this.audioBuffSizeCheck(n);
                arrn = new int[]{this.mSampleRate};
                object = new int[]{n2};
                n = this.native_setup(new WeakReference<AudioRecord>(this), this.mAudioAttributes, arrn, this.mChannelMask, this.mChannelIndexMask, this.mAudioFormat, this.mNativeBufferSizeInBytes, (int[])object, this.getCurrentOpPackageName(), 0L);
                if (n != 0) {
                    object = new StringBuilder();
                    ((StringBuilder)object).append("Error code ");
                    ((StringBuilder)object).append(n);
                    ((StringBuilder)object).append(" when initializing native AudioRecord object.");
                    AudioRecord.loge(((StringBuilder)object).toString());
                    return;
                }
                this.mSampleRate = arrn[0];
                this.mSessionId = (int)object[0];
                this.mState = 1;
                return;
            }
            throw new IllegalArgumentException("Illegal null AudioFormat");
        }
        throw new IllegalArgumentException("Illegal null AudioAttributes");
    }

    private void audioBuffSizeCheck(int n) throws IllegalArgumentException {
        int n2 = this.mChannelCount * AudioFormat.getBytesPerSample(this.mAudioFormat);
        if (n % n2 == 0 && n >= 1) {
            this.mNativeBufferSizeInBytes = n;
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Invalid audio buffer size ");
        stringBuilder.append(n);
        stringBuilder.append(" (frame size ");
        stringBuilder.append(n2);
        stringBuilder.append(")");
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    private void audioParamCheck(int n, int n2, int n3) throws IllegalArgumentException {
        if (n >= 0 && (n <= MediaRecorder.getAudioSourceMax() || n == 1998 || n == 1997 || n == 1999)) {
            this.mRecordSource = n;
            if (n2 >= 4000 && n2 <= 192000 || n2 == 0) {
                this.mSampleRate = n2;
                if (n3 != 1) {
                    if (n3 != 2 && n3 != 3 && n3 != 4) {
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("Unsupported sample encoding ");
                        stringBuilder.append(n3);
                        stringBuilder.append(". Should be ENCODING_PCM_8BIT, ENCODING_PCM_16BIT, or ENCODING_PCM_FLOAT.");
                        throw new IllegalArgumentException(stringBuilder.toString());
                    }
                    this.mAudioFormat = n3;
                } else {
                    this.mAudioFormat = 2;
                }
                return;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(n2);
            stringBuilder.append("Hz is not a supported sample rate.");
            throw new IllegalArgumentException(stringBuilder.toString());
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Invalid audio source ");
        stringBuilder.append(n);
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void broadcastRoutingChange() {
        AudioManager.resetAudioPortGeneration();
        ArrayMap<AudioRouting.OnRoutingChangedListener, NativeRoutingEventHandlerDelegate> arrayMap = this.mRoutingChangeListeners;
        synchronized (arrayMap) {
            Iterator<NativeRoutingEventHandlerDelegate> iterator = this.mRoutingChangeListeners.values().iterator();
            while (iterator.hasNext()) {
                iterator.next().notifyClient();
            }
            return;
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private static int getChannelMaskFromLegacyConfig(int n, boolean bl) {
        int n2;
        block2 : {
            block0 : {
                block1 : {
                    if (n == 1 || n == 2) break block0;
                    if (n == 3 || n == 12) break block1;
                    if (n == 16) break block0;
                    if (n != 48) throw new IllegalArgumentException("Unsupported channel configuration.");
                    n2 = n;
                    break block2;
                }
                n2 = 12;
                break block2;
            }
            n2 = 16;
        }
        if (bl || n != 2 && n != 3) return n2;
        throw new IllegalArgumentException("Unsupported deprecated configuration.");
    }

    private String getCurrentOpPackageName() {
        CharSequence charSequence = ActivityThread.currentOpPackageName();
        if (charSequence != null) {
            return charSequence;
        }
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append("uid:");
        ((StringBuilder)charSequence).append(Binder.getCallingUid());
        return ((StringBuilder)charSequence).toString();
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    public static int getMinBufferSize(int var0, int var1_1, int var2_2) {
        block5 : {
            block3 : {
                block4 : {
                    if (var1_1 == 1 || var1_1 == 2) break block3;
                    if (var1_1 == 3 || var1_1 == 12) break block4;
                    if (var1_1 != 16) {
                        if (var1_1 != 48) {
                            AudioRecord.loge("getMinBufferSize(): Invalid channel configuration.");
                            return -2;
                        } else {
                            ** GOTO lbl8
                        }
                    }
                    break block3;
                }
                var1_1 = 2;
                break block5;
            }
            var1_1 = 1;
        }
        var0 = AudioRecord.native_get_min_buff_size(var0, var1_1, var2_2);
        if (var0 == 0) {
            return -2;
        }
        if (var0 != -1) return var0;
        return -1;
    }

    private void handleFullVolumeRec(boolean bl) {
        if (!this.mIsSubmixFullVolume) {
            return;
        }
        IAudioService iAudioService = IAudioService.Stub.asInterface(ServiceManager.getService("audio"));
        try {
            iAudioService.forceRemoteSubmixFullVolume(bl, this.mICallBack);
        }
        catch (RemoteException remoteException) {
            Log.e(TAG, "Error talking to AudioService when handling full submix volume", remoteException);
        }
    }

    private static void logd(String string2) {
        Log.d(TAG, string2);
    }

    private static void loge(String string2) {
        Log.e(TAG, string2);
    }

    private final native void native_disableDeviceCallback();

    private final native void native_enableDeviceCallback();

    private final native void native_finalize();

    private native PersistableBundle native_getMetrics();

    private native int native_getPortId();

    private final native int native_getRoutedDeviceId();

    private final native int native_get_active_microphones(ArrayList<MicrophoneInfo> var1);

    private final native int native_get_buffer_size_in_frames();

    private final native int native_get_marker_pos();

    private static final native int native_get_min_buff_size(int var0, int var1, int var2);

    private final native int native_get_pos_update_period();

    private final native int native_get_timestamp(AudioTimestamp var1, int var2);

    private final native int native_read_in_byte_array(byte[] var1, int var2, int var3, boolean var4);

    private final native int native_read_in_direct_buffer(Object var1, int var2, boolean var3);

    private final native int native_read_in_float_array(float[] var1, int var2, int var3, boolean var4);

    private final native int native_read_in_short_array(short[] var1, int var2, int var3, boolean var4);

    private final native boolean native_setInputDevice(int var1);

    private final native int native_set_marker_pos(int var1);

    private final native int native_set_pos_update_period(int var1);

    private native int native_set_preferred_microphone_direction(int var1);

    private native int native_set_preferred_microphone_field_dimension(float var1);

    @UnsupportedAppUsage
    private final native int native_setup(Object var1, Object var2, int[] var3, int var4, int var5, int var6, int var7, int[] var8, String var9, long var10);

    private final native int native_start(int var1, int var2);

    private final native void native_stop();

    @UnsupportedAppUsage
    private static void postEventFromNative(Object object, int n, int n2, int n3, Object object2) {
        if ((object = (AudioRecord)((WeakReference)object).get()) == null) {
            return;
        }
        if (n == 1000) {
            AudioRecord.super.broadcastRoutingChange();
            return;
        }
        NativeEventHandler nativeEventHandler = ((AudioRecord)object).mEventHandler;
        if (nativeEventHandler != null) {
            object2 = nativeEventHandler.obtainMessage(n, n2, n3, object2);
            ((AudioRecord)object).mEventHandler.sendMessage((Message)object2);
        }
    }

    @GuardedBy(value={"mRoutingChangeListeners"})
    private void testDisableNativeRoutingCallbacksLocked() {
        if (this.mRoutingChangeListeners.size() == 0) {
            this.native_disableDeviceCallback();
        }
    }

    @GuardedBy(value={"mRoutingChangeListeners"})
    private void testEnableNativeRoutingCallbacksLocked() {
        if (this.mRoutingChangeListeners.size() == 0) {
            this.native_enableDeviceCallback();
        }
    }

    private void unregisterAudioPolicyOnRelease(AudioPolicy audioPolicy) {
        this.mAudioCapturePolicy = audioPolicy;
    }

    @Deprecated
    public void addOnRoutingChangedListener(OnRoutingChangedListener onRoutingChangedListener, Handler handler) {
        this.addOnRoutingChangedListener((AudioRouting.OnRoutingChangedListener)onRoutingChangedListener, handler);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void addOnRoutingChangedListener(AudioRouting.OnRoutingChangedListener onRoutingChangedListener, Handler handler) {
        ArrayMap<AudioRouting.OnRoutingChangedListener, NativeRoutingEventHandlerDelegate> arrayMap = this.mRoutingChangeListeners;
        synchronized (arrayMap) {
            if (onRoutingChangedListener != null && !this.mRoutingChangeListeners.containsKey(onRoutingChangedListener)) {
                this.testEnableNativeRoutingCallbacksLocked();
                ArrayMap<AudioRouting.OnRoutingChangedListener, NativeRoutingEventHandlerDelegate> arrayMap2 = this.mRoutingChangeListeners;
                if (handler == null) {
                    handler = new Handler(this.mInitializationLooper);
                }
                NativeRoutingEventHandlerDelegate nativeRoutingEventHandlerDelegate = new NativeRoutingEventHandlerDelegate(this, onRoutingChangedListener, handler);
                arrayMap2.put(onRoutingChangedListener, nativeRoutingEventHandlerDelegate);
            }
            return;
        }
    }

    void deferred_connect(long l) {
        if (this.mState != 1) {
            int[] arrn = new int[]{0};
            WeakReference<AudioRecord> weakReference = new WeakReference<AudioRecord>(this);
            CharSequence charSequence = ActivityThread.currentOpPackageName();
            int n = this.native_setup(weakReference, null, new int[]{0}, 0, 0, 0, 0, arrn, (String)charSequence, l);
            if (n != 0) {
                charSequence = new StringBuilder();
                ((StringBuilder)charSequence).append("Error code ");
                ((StringBuilder)charSequence).append(n);
                ((StringBuilder)charSequence).append(" when initializing native AudioRecord object.");
                AudioRecord.loge(((StringBuilder)charSequence).toString());
                return;
            }
            this.mSessionId = arrn[0];
            this.mState = 1;
        }
    }

    protected void finalize() {
        this.release();
    }

    public List<MicrophoneInfo> getActiveMicrophones() throws IOException {
        ArrayList<Pair<Integer, Integer>> arrayList;
        ArrayList<MicrophoneInfo> arrayList2 = new ArrayList<MicrophoneInfo>();
        int n = this.native_get_active_microphones(arrayList2);
        if (n != 0) {
            if (n != -3) {
                arrayList = new StringBuilder();
                ((StringBuilder)((Object)arrayList)).append("getActiveMicrophones failed:");
                ((StringBuilder)((Object)arrayList)).append(n);
                Log.e("android.media.AudioRecord", ((StringBuilder)((Object)arrayList)).toString());
            }
            Log.i("android.media.AudioRecord", "getActiveMicrophones failed, fallback on routed device info");
        }
        AudioManager.setPortIdForMicrophones(arrayList2);
        if (arrayList2.size() == 0 && (arrayList = this.getRoutedDevice()) != null) {
            MicrophoneInfo microphoneInfo = AudioManager.microphoneInfoFromAudioDeviceInfo((AudioDeviceInfo)((Object)arrayList));
            arrayList = new ArrayList<Pair<Integer, Integer>>();
            for (n = 0; n < this.mChannelCount; ++n) {
                arrayList.add(new Pair<Integer, Integer>(n, 1));
            }
            microphoneInfo.setChannelMapping(arrayList);
            arrayList2.add(microphoneInfo);
        }
        return arrayList2;
    }

    @Override
    public AudioRecordingConfiguration getActiveRecordingConfiguration() {
        return this.mRecordingInfoImpl.getActiveRecordingConfiguration();
    }

    public int getAudioFormat() {
        return this.mAudioFormat;
    }

    public int getAudioSessionId() {
        return this.mSessionId;
    }

    public int getAudioSource() {
        return this.mRecordSource;
    }

    public int getBufferSizeInFrames() {
        return this.native_get_buffer_size_in_frames();
    }

    public int getChannelConfiguration() {
        return this.mChannelMask;
    }

    public int getChannelCount() {
        return this.mChannelCount;
    }

    public AudioFormat getFormat() {
        AudioFormat.Builder builder = new AudioFormat.Builder().setSampleRate(this.mSampleRate).setEncoding(this.mAudioFormat);
        int n = this.mChannelMask;
        if (n != 0) {
            builder.setChannelMask(n);
        }
        if ((n = this.mChannelIndexMask) != 0) {
            builder.setChannelIndexMask(n);
        }
        return builder.build();
    }

    public PersistableBundle getMetrics() {
        return this.native_getMetrics();
    }

    public int getNotificationMarkerPosition() {
        return this.native_get_marker_pos();
    }

    @Override
    public int getPortId() {
        return this.native_getPortId();
    }

    public int getPositionNotificationPeriod() {
        return this.native_get_pos_update_period();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public AudioDeviceInfo getPreferredDevice() {
        synchronized (this) {
            return this.mPreferredDevice;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public int getRecordingState() {
        Object object = this.mRecordingStateLock;
        synchronized (object) {
            return this.mRecordingState;
        }
    }

    @Override
    public AudioDeviceInfo getRoutedDevice() {
        int n = this.native_getRoutedDeviceId();
        if (n == 0) {
            return null;
        }
        AudioDeviceInfo[] arraudioDeviceInfo = AudioManager.getDevicesStatic(1);
        for (int i = 0; i < arraudioDeviceInfo.length; ++i) {
            if (arraudioDeviceInfo[i].getId() != n) continue;
            return arraudioDeviceInfo[i];
        }
        return null;
    }

    public int getSampleRate() {
        return this.mSampleRate;
    }

    public int getState() {
        return this.mState;
    }

    public int getTimestamp(AudioTimestamp audioTimestamp, int n) {
        if (audioTimestamp != null && (n == 1 || n == 0)) {
            return this.native_get_timestamp(audioTimestamp, n);
        }
        throw new IllegalArgumentException();
    }

    @UnsupportedAppUsage
    public final native void native_release();

    public int read(ByteBuffer byteBuffer, int n) {
        return this.read(byteBuffer, n, 0);
    }

    public int read(ByteBuffer byteBuffer, int n, int n2) {
        int n3 = this.mState;
        boolean bl = true;
        if (n3 != 1) {
            return -3;
        }
        if (n2 != 0 && n2 != 1) {
            Log.e("android.media.AudioRecord", "AudioRecord.read() called with invalid blocking mode");
            return -2;
        }
        if (byteBuffer != null && n >= 0) {
            if (n2 != 0) {
                bl = false;
            }
            return this.native_read_in_direct_buffer(byteBuffer, n, bl);
        }
        return -2;
    }

    public int read(byte[] arrby, int n, int n2) {
        return this.read(arrby, n, n2, 0);
    }

    public int read(byte[] arrby, int n, int n2, int n3) {
        int n4 = this.mState;
        boolean bl = true;
        if (n4 == 1 && this.mAudioFormat != 4) {
            if (n3 != 0 && n3 != 1) {
                Log.e("android.media.AudioRecord", "AudioRecord.read() called with invalid blocking mode");
                return -2;
            }
            if (arrby != null && n >= 0 && n2 >= 0 && n + n2 >= 0 && n + n2 <= arrby.length) {
                if (n3 != 0) {
                    bl = false;
                }
                return this.native_read_in_byte_array(arrby, n, n2, bl);
            }
            return -2;
        }
        return -3;
    }

    public int read(float[] arrf, int n, int n2, int n3) {
        if (this.mState == 0) {
            Log.e("android.media.AudioRecord", "AudioRecord.read() called in invalid state STATE_UNINITIALIZED");
            return -3;
        }
        if (this.mAudioFormat != 4) {
            Log.e("android.media.AudioRecord", "AudioRecord.read(float[] ...) requires format ENCODING_PCM_FLOAT");
            return -3;
        }
        boolean bl = true;
        if (n3 != 0 && n3 != 1) {
            Log.e("android.media.AudioRecord", "AudioRecord.read() called with invalid blocking mode");
            return -2;
        }
        if (arrf != null && n >= 0 && n2 >= 0 && n + n2 >= 0 && n + n2 <= arrf.length) {
            if (n3 != 0) {
                bl = false;
            }
            return this.native_read_in_float_array(arrf, n, n2, bl);
        }
        return -2;
    }

    public int read(short[] arrs, int n, int n2) {
        return this.read(arrs, n, n2, 0);
    }

    public int read(short[] arrs, int n, int n2, int n3) {
        int n4 = this.mState;
        boolean bl = true;
        if (n4 == 1 && this.mAudioFormat != 4) {
            if (n3 != 0 && n3 != 1) {
                Log.e("android.media.AudioRecord", "AudioRecord.read() called with invalid blocking mode");
                return -2;
            }
            if (arrs != null && n >= 0 && n2 >= 0 && n + n2 >= 0 && n + n2 <= arrs.length) {
                if (n3 != 0) {
                    bl = false;
                }
                return this.native_read_in_short_array(arrs, n, n2, bl);
            }
            return -2;
        }
        return -3;
    }

    @Override
    public void registerAudioRecordingCallback(Executor executor, AudioManager.AudioRecordingCallback audioRecordingCallback) {
        this.mRecordingInfoImpl.registerAudioRecordingCallback(executor, audioRecordingCallback);
    }

    public void release() {
        try {
            this.stop();
        }
        catch (IllegalStateException illegalStateException) {
            // empty catch block
        }
        AudioPolicy audioPolicy = this.mAudioCapturePolicy;
        if (audioPolicy != null) {
            AudioManager.unregisterAudioPolicyAsyncStatic(audioPolicy);
            this.mAudioCapturePolicy = null;
        }
        this.native_release();
        this.mState = 0;
    }

    @Deprecated
    public void removeOnRoutingChangedListener(OnRoutingChangedListener onRoutingChangedListener) {
        this.removeOnRoutingChangedListener((AudioRouting.OnRoutingChangedListener)onRoutingChangedListener);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void removeOnRoutingChangedListener(AudioRouting.OnRoutingChangedListener onRoutingChangedListener) {
        ArrayMap<AudioRouting.OnRoutingChangedListener, NativeRoutingEventHandlerDelegate> arrayMap = this.mRoutingChangeListeners;
        synchronized (arrayMap) {
            if (this.mRoutingChangeListeners.containsKey(onRoutingChangedListener)) {
                this.mRoutingChangeListeners.remove(onRoutingChangedListener);
                this.testDisableNativeRoutingCallbacksLocked();
            }
            return;
        }
    }

    public int setNotificationMarkerPosition(int n) {
        if (this.mState == 0) {
            return -3;
        }
        return this.native_set_marker_pos(n);
    }

    public int setPositionNotificationPeriod(int n) {
        if (this.mState == 0) {
            return -3;
        }
        return this.native_set_pos_update_period(n);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public boolean setPreferredDevice(AudioDeviceInfo audioDeviceInfo) {
        boolean bl;
        int n = 0;
        if (audioDeviceInfo != null && !audioDeviceInfo.isSource()) {
            return false;
        }
        if (audioDeviceInfo != null) {
            n = audioDeviceInfo.getId();
        }
        if (!(bl = this.native_setInputDevice(n))) return bl;
        synchronized (this) {
            this.mPreferredDevice = audioDeviceInfo;
            return bl;
        }
    }

    @Override
    public boolean setPreferredMicrophoneDirection(int n) {
        boolean bl = this.native_set_preferred_microphone_direction(n) == 0;
        return bl;
    }

    @Override
    public boolean setPreferredMicrophoneFieldDimension(float f) {
        boolean bl = true;
        boolean bl2 = f >= -1.0f && f <= 1.0f;
        Preconditions.checkArgument(bl2, "Argument must fall between -1 & 1 (inclusive)");
        bl2 = this.native_set_preferred_microphone_field_dimension(f) == 0 ? bl : false;
        return bl2;
    }

    public void setRecordPositionUpdateListener(OnRecordPositionUpdateListener onRecordPositionUpdateListener) {
        this.setRecordPositionUpdateListener(onRecordPositionUpdateListener, null);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void setRecordPositionUpdateListener(OnRecordPositionUpdateListener object, Handler handler) {
        Object object2 = this.mPositionListenerLock;
        synchronized (object2) {
            this.mPositionListener = object;
            this.mEventHandler = object != null ? (handler != null ? (object = new NativeEventHandler(this, handler.getLooper())) : (object = new NativeEventHandler(this, this.mInitializationLooper))) : null;
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void startRecording() throws IllegalStateException {
        if (this.mState != 1) {
            throw new IllegalStateException("startRecording() called on an uninitialized AudioRecord.");
        }
        Object object = this.mRecordingStateLock;
        synchronized (object) {
            if (this.native_start(0, 0) == 0) {
                this.handleFullVolumeRec(true);
                this.mRecordingState = 3;
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void startRecording(MediaSyncEvent mediaSyncEvent) throws IllegalStateException {
        if (this.mState != 1) {
            throw new IllegalStateException("startRecording() called on an uninitialized AudioRecord.");
        }
        Object object = this.mRecordingStateLock;
        synchronized (object) {
            if (this.native_start(mediaSyncEvent.getType(), mediaSyncEvent.getAudioSessionId()) == 0) {
                this.handleFullVolumeRec(true);
                this.mRecordingState = 3;
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void stop() throws IllegalStateException {
        if (this.mState == 1) {
            Object object = this.mRecordingStateLock;
            synchronized (object) {
                this.handleFullVolumeRec(false);
                this.native_stop();
                this.mRecordingState = 1;
                return;
            }
        }
        throw new IllegalStateException("stop() called on an uninitialized AudioRecord.");
    }

    @Override
    public void unregisterAudioRecordingCallback(AudioManager.AudioRecordingCallback audioRecordingCallback) {
        this.mRecordingInfoImpl.unregisterAudioRecordingCallback(audioRecordingCallback);
    }

    public static class Builder {
        private static final String ERROR_MESSAGE_SOURCE_MISMATCH = "Cannot both set audio source and set playback capture config";
        private AudioAttributes mAttributes;
        private AudioPlaybackCaptureConfiguration mAudioPlaybackCaptureConfiguration;
        private int mBufferSizeInBytes;
        private AudioFormat mFormat;
        private int mSessionId = 0;

        private AudioRecord buildAudioPlaybackCaptureRecord() {
            Object object = this.mAudioPlaybackCaptureConfiguration.createAudioMix(this.mFormat);
            Object object2 = this.mAudioPlaybackCaptureConfiguration.getMediaProjection();
            if (AudioManager.registerAudioPolicyStatic((AudioPolicy)(object2 = new AudioPolicy.Builder(null).setMediaProjection((MediaProjection)object2).addMix((AudioMix)object).build())) == 0) {
                if ((object = ((AudioPolicy)object2).createAudioRecordSink((AudioMix)object)) != null) {
                    ((AudioRecord)object).unregisterAudioPolicyOnRelease((AudioPolicy)object2);
                    return object;
                }
                throw new UnsupportedOperationException("Cannot create AudioRecord");
            }
            throw new UnsupportedOperationException("Error: could not register audio policy");
        }

        public AudioRecord build() throws UnsupportedOperationException {
            Object object;
            block10 : {
                if (this.mAudioPlaybackCaptureConfiguration != null) {
                    return this.buildAudioPlaybackCaptureRecord();
                }
                object = this.mFormat;
                if (object == null) {
                    this.mFormat = new AudioFormat.Builder().setEncoding(2).setChannelMask(16).build();
                } else {
                    if (((AudioFormat)object).getEncoding() == 0) {
                        this.mFormat = new AudioFormat.Builder(this.mFormat).setEncoding(2).build();
                    }
                    if (this.mFormat.getChannelMask() == 0 && this.mFormat.getChannelIndexMask() == 0) {
                        this.mFormat = new AudioFormat.Builder(this.mFormat).setChannelMask(16).build();
                    }
                }
                if (this.mAttributes == null) {
                    this.mAttributes = new AudioAttributes.Builder().setInternalCapturePreset(0).build();
                }
                try {
                    if (this.mBufferSizeInBytes == 0) {
                        int n = this.mFormat.getChannelCount();
                        object = this.mFormat;
                        this.mBufferSizeInBytes = n * AudioFormat.getBytesPerSample(this.mFormat.getEncoding());
                    }
                    if (((AudioRecord)(object = new AudioRecord(this.mAttributes, this.mFormat, this.mBufferSizeInBytes, this.mSessionId))).getState() == 0) break block10;
                    return object;
                }
                catch (IllegalArgumentException illegalArgumentException) {
                    throw new UnsupportedOperationException(illegalArgumentException.getMessage());
                }
            }
            object = new UnsupportedOperationException("Cannot create AudioRecord");
            throw object;
        }

        @SystemApi
        public Builder setAudioAttributes(AudioAttributes audioAttributes) throws IllegalArgumentException {
            if (audioAttributes != null) {
                if (audioAttributes.getCapturePreset() != -1) {
                    this.mAttributes = audioAttributes;
                    return this;
                }
                throw new IllegalArgumentException("No valid capture preset in AudioAttributes argument");
            }
            throw new IllegalArgumentException("Illegal null AudioAttributes argument");
        }

        public Builder setAudioFormat(AudioFormat audioFormat) throws IllegalArgumentException {
            if (audioFormat != null) {
                this.mFormat = audioFormat;
                return this;
            }
            throw new IllegalArgumentException("Illegal null AudioFormat argument");
        }

        public Builder setAudioPlaybackCaptureConfig(AudioPlaybackCaptureConfiguration audioPlaybackCaptureConfiguration) {
            Preconditions.checkNotNull(audioPlaybackCaptureConfiguration, "Illegal null AudioPlaybackCaptureConfiguration argument");
            boolean bl = this.mAttributes == null;
            Preconditions.checkState(bl, ERROR_MESSAGE_SOURCE_MISMATCH);
            this.mAudioPlaybackCaptureConfiguration = audioPlaybackCaptureConfiguration;
            return this;
        }

        public Builder setAudioSource(int n) throws IllegalArgumentException {
            boolean bl = this.mAudioPlaybackCaptureConfiguration == null;
            Preconditions.checkState(bl, ERROR_MESSAGE_SOURCE_MISMATCH);
            if (n >= 0 && n <= MediaRecorder.getAudioSourceMax()) {
                this.mAttributes = new AudioAttributes.Builder().setInternalCapturePreset(n).build();
                return this;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Invalid audio source ");
            stringBuilder.append(n);
            throw new IllegalArgumentException(stringBuilder.toString());
        }

        public Builder setBufferSizeInBytes(int n) throws IllegalArgumentException {
            if (n > 0) {
                this.mBufferSizeInBytes = n;
                return this;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Invalid buffer size ");
            stringBuilder.append(n);
            throw new IllegalArgumentException(stringBuilder.toString());
        }

        @SystemApi
        public Builder setSessionId(int n) throws IllegalArgumentException {
            if (n >= 0) {
                this.mSessionId = n;
                return this;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Invalid session ID ");
            stringBuilder.append(n);
            throw new IllegalArgumentException(stringBuilder.toString());
        }
    }

    public static final class MetricsConstants {
        public static final String ATTRIBUTES = "android.media.audiorecord.attributes";
        public static final String CHANNELS = "android.media.audiorecord.channels";
        public static final String CHANNEL_MASK = "android.media.audiorecord.channelMask";
        public static final String DURATION_MS = "android.media.audiorecord.durationMs";
        public static final String ENCODING = "android.media.audiorecord.encoding";
        public static final String FRAME_COUNT = "android.media.audiorecord.frameCount";
        @Deprecated
        public static final String LATENCY = "android.media.audiorecord.latency";
        private static final String MM_PREFIX = "android.media.audiorecord.";
        public static final String PORT_ID = "android.media.audiorecord.portId";
        public static final String SAMPLERATE = "android.media.audiorecord.samplerate";
        public static final String SOURCE = "android.media.audiorecord.source";
        public static final String START_COUNT = "android.media.audiorecord.startCount";

        private MetricsConstants() {
        }
    }

    private class NativeEventHandler
    extends Handler {
        private final AudioRecord mAudioRecord;

        NativeEventHandler(AudioRecord audioRecord2, Looper looper) {
            super(looper);
            this.mAudioRecord = audioRecord2;
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void handleMessage(Message message) {
            OnRecordPositionUpdateListener onRecordPositionUpdateListener;
            Object object = AudioRecord.this.mPositionListenerLock;
            synchronized (object) {
                onRecordPositionUpdateListener = this.mAudioRecord.mPositionListener;
            }
            int n = message.what;
            if (n == 2) {
                if (onRecordPositionUpdateListener == null) return;
                onRecordPositionUpdateListener.onMarkerReached(this.mAudioRecord);
                return;
            }
            if (n != 3) {
                object = new StringBuilder();
                ((StringBuilder)object).append("Unknown native event type: ");
                ((StringBuilder)object).append(message.what);
                AudioRecord.loge(((StringBuilder)object).toString());
                return;
            }
            if (onRecordPositionUpdateListener == null) return;
            onRecordPositionUpdateListener.onPeriodicNotification(this.mAudioRecord);
        }
    }

    public static interface OnRecordPositionUpdateListener {
        public void onMarkerReached(AudioRecord var1);

        public void onPeriodicNotification(AudioRecord var1);
    }

    @Deprecated
    public static interface OnRoutingChangedListener
    extends AudioRouting.OnRoutingChangedListener {
        public void onRoutingChanged(AudioRecord var1);

        @Override
        default public void onRoutingChanged(AudioRouting audioRouting) {
            if (audioRouting instanceof AudioRecord) {
                this.onRoutingChanged((AudioRecord)audioRouting);
            }
        }
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface ReadMode {
    }

}

