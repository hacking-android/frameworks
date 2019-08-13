/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  java.nio.NioUtils
 */
package android.media;

import android.annotation.UnsupportedAppUsage;
import android.media.AudioAttributes;
import android.media.AudioDeviceInfo;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioPresentation;
import android.media.AudioRouting;
import android.media.AudioSystem;
import android.media.AudioTimestamp;
import android.media.NativeRoutingEventHandlerDelegate;
import android.media.PlaybackParams;
import android.media.PlayerBase;
import android.media.VolumeAutomation;
import android.media.VolumeShaper;
import android.media._$$Lambda$AudioTrack$StreamEventHandler$IUDediua4qA5AgKwU3zNCXA7jQo;
import android.media._$$Lambda$AudioTrack$StreamEventHandler$_3NLz6Sbq0z_YUytzGW6tVjPCao;
import android.media._$$Lambda$AudioTrack$StreamEventHandler$uWnWUbk1g3MhAY3NoSFc6o37wsk;
import android.os.Binder;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.os.PersistableBundle;
import android.util.ArrayMap;
import android.util.Log;
import com.android.internal.annotations.GuardedBy;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.ref.WeakReference;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.NioUtils;
import java.util.AbstractSequentialList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.concurrent.Executor;

public class AudioTrack
extends PlayerBase
implements AudioRouting,
VolumeAutomation {
    private static final int AUDIO_OUTPUT_FLAG_DEEP_BUFFER = 8;
    private static final int AUDIO_OUTPUT_FLAG_FAST = 4;
    public static final int ERROR = -1;
    public static final int ERROR_BAD_VALUE = -2;
    public static final int ERROR_DEAD_OBJECT = -6;
    public static final int ERROR_INVALID_OPERATION = -3;
    private static final int ERROR_NATIVESETUP_AUDIOSYSTEM = -16;
    private static final int ERROR_NATIVESETUP_INVALIDCHANNELMASK = -17;
    private static final int ERROR_NATIVESETUP_INVALIDFORMAT = -18;
    private static final int ERROR_NATIVESETUP_INVALIDSTREAMTYPE = -19;
    private static final int ERROR_NATIVESETUP_NATIVEINITFAILED = -20;
    public static final int ERROR_WOULD_BLOCK = -7;
    private static final float GAIN_MAX = 1.0f;
    private static final float GAIN_MIN = 0.0f;
    private static final float HEADER_V2_SIZE_BYTES = 20.0f;
    public static final int MODE_STATIC = 0;
    public static final int MODE_STREAM = 1;
    private static final int NATIVE_EVENT_CAN_WRITE_MORE_DATA = 9;
    private static final int NATIVE_EVENT_MARKER = 3;
    private static final int NATIVE_EVENT_NEW_IAUDIOTRACK = 6;
    private static final int NATIVE_EVENT_NEW_POS = 4;
    private static final int NATIVE_EVENT_STREAM_END = 7;
    public static final int PERFORMANCE_MODE_LOW_LATENCY = 1;
    public static final int PERFORMANCE_MODE_NONE = 0;
    public static final int PERFORMANCE_MODE_POWER_SAVING = 2;
    public static final int PLAYSTATE_PAUSED = 2;
    private static final int PLAYSTATE_PAUSED_STOPPING = 5;
    public static final int PLAYSTATE_PLAYING = 3;
    public static final int PLAYSTATE_STOPPED = 1;
    private static final int PLAYSTATE_STOPPING = 4;
    public static final int STATE_INITIALIZED = 1;
    public static final int STATE_NO_STATIC_DATA = 2;
    public static final int STATE_UNINITIALIZED = 0;
    public static final int SUCCESS = 0;
    private static final int SUPPORTED_OUT_CHANNELS = 7420;
    private static final String TAG = "android.media.AudioTrack";
    public static final int WRITE_BLOCKING = 0;
    public static final int WRITE_NON_BLOCKING = 1;
    private int mAudioFormat;
    private int mAvSyncBytesRemaining = 0;
    private ByteBuffer mAvSyncHeader = null;
    private int mChannelConfiguration = 4;
    private int mChannelCount = 1;
    private int mChannelIndexMask = 0;
    private int mChannelMask = 4;
    private AudioAttributes mConfiguredAudioAttributes;
    private int mDataLoadMode = 1;
    private NativePositionEventHandlerDelegate mEventHandlerDelegate;
    private final Looper mInitializationLooper;
    @UnsupportedAppUsage
    private long mJniData;
    private int mNativeBufferSizeInBytes = 0;
    private int mNativeBufferSizeInFrames = 0;
    @UnsupportedAppUsage
    protected long mNativeTrackInJavaObj;
    private int mOffloadDelayFrames = 0;
    private boolean mOffloadEosPending = false;
    private int mOffloadPaddingFrames = 0;
    private boolean mOffloaded = false;
    private int mOffset = 0;
    private int mPlayState = 1;
    private final Object mPlayStateLock = new Object();
    private AudioDeviceInfo mPreferredDevice = null;
    @GuardedBy(value={"mRoutingChangeListeners"})
    private ArrayMap<AudioRouting.OnRoutingChangedListener, NativeRoutingEventHandlerDelegate> mRoutingChangeListeners = new ArrayMap();
    private int mSampleRate;
    private int mSessionId = 0;
    private int mState = 0;
    @GuardedBy(value={"mStreamEventCbLock"})
    private LinkedList<StreamEventCbInfo> mStreamEventCbInfoList = new LinkedList();
    private final Object mStreamEventCbLock = new Object();
    private volatile StreamEventHandler mStreamEventHandler;
    private HandlerThread mStreamEventHandlerThread;
    @UnsupportedAppUsage
    private int mStreamType = 3;

    public AudioTrack(int n, int n2, int n3, int n4, int n5, int n6) throws IllegalArgumentException {
        this(n, n2, n3, n4, n5, n6, 0);
    }

    public AudioTrack(int n, int n2, int n3, int n4, int n5, int n6, int n7) throws IllegalArgumentException {
        this(new AudioAttributes.Builder().setLegacyStreamType(n).build(), new AudioFormat.Builder().setChannelMask(n3).setEncoding(n4).setSampleRate(n2).build(), n5, n6, n7);
        AudioTrack.deprecateStreamTypeForPlayback(n, "AudioTrack", "AudioTrack()");
    }

    AudioTrack(long l) {
        super(new AudioAttributes.Builder().build(), 1);
        Looper looper;
        this.mNativeTrackInJavaObj = 0L;
        this.mJniData = 0L;
        Looper looper2 = looper = Looper.myLooper();
        if (looper == null) {
            looper2 = Looper.getMainLooper();
        }
        this.mInitializationLooper = looper2;
        if (l != 0L) {
            this.baseRegisterPlayer();
            this.deferred_connect(l);
        } else {
            this.mState = 0;
        }
    }

    public AudioTrack(AudioAttributes audioAttributes, AudioFormat audioFormat, int n, int n2, int n3) throws IllegalArgumentException {
        this(audioAttributes, audioFormat, n, n2, n3, false);
    }

    private AudioTrack(AudioAttributes object, AudioFormat arrn, int n, int n2, int n3, boolean bl) throws IllegalArgumentException {
        super((AudioAttributes)object, 1);
        this.mConfiguredAudioAttributes = object;
        if (arrn != null) {
            int n4;
            if (AudioTrack.shouldEnablePowerSaving(this.mAttributes, (AudioFormat)arrn, n, n2)) {
                this.mAttributes = new AudioAttributes.Builder(this.mAttributes).replaceFlags((this.mAttributes.getAllFlags() | 512) & -257).build();
            }
            if ((object = Looper.myLooper()) == null) {
                object = Looper.getMainLooper();
            }
            if ((n4 = arrn.getSampleRate()) == 0) {
                n4 = 0;
            }
            int n5 = (arrn.getPropertySetMask() & 8) != 0 ? arrn.getChannelIndexMask() : 0;
            int n6 = (4 & arrn.getPropertySetMask()) != 0 ? arrn.getChannelMask() : (n5 == 0 ? 12 : 0);
            int n7 = (arrn.getPropertySetMask() & 1) != 0 ? arrn.getEncoding() : 1;
            this.audioParamCheck(n4, n6, n5, n7, n2);
            this.mOffloaded = bl;
            this.mStreamType = -1;
            this.audioBuffSizeCheck(n);
            this.mInitializationLooper = object;
            if (n3 >= 0) {
                arrn = new int[]{this.mSampleRate};
                object = new int[]{n3};
                n = this.native_setup(new WeakReference<AudioTrack>(this), this.mAttributes, arrn, this.mChannelMask, this.mChannelIndexMask, this.mAudioFormat, this.mNativeBufferSizeInBytes, this.mDataLoadMode, (int[])object, 0L, bl);
                if (n != 0) {
                    object = new StringBuilder();
                    ((StringBuilder)object).append("Error code ");
                    ((StringBuilder)object).append(n);
                    ((StringBuilder)object).append(" when initializing AudioTrack.");
                    AudioTrack.loge(((StringBuilder)object).toString());
                    return;
                }
                this.mSampleRate = arrn[0];
                this.mSessionId = (int)object[0];
                if ((this.mAttributes.getFlags() & 16) != 0) {
                    n = AudioFormat.isEncodingLinearFrames(this.mAudioFormat) ? this.mChannelCount * AudioFormat.getBytesPerSample(this.mAudioFormat) : 1;
                    this.mOffset = (int)Math.ceil(20.0f / (float)n) * n;
                }
                this.mState = this.mDataLoadMode == 0 ? 2 : 1;
                this.baseRegisterPlayer();
                return;
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("Invalid audio session ID: ");
            ((StringBuilder)object).append(n3);
            throw new IllegalArgumentException(((StringBuilder)object).toString());
        }
        throw new IllegalArgumentException("Illegal null AudioFormat");
    }

    private void audioBuffSizeCheck(int n) {
        int n2 = AudioFormat.isEncodingLinearFrames(this.mAudioFormat) ? this.mChannelCount * AudioFormat.getBytesPerSample(this.mAudioFormat) : 1;
        if (n % n2 == 0 && n >= 1) {
            this.mNativeBufferSizeInBytes = n;
            this.mNativeBufferSizeInFrames = n / n2;
            return;
        }
        throw new IllegalArgumentException("Invalid audio buffer size.");
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    private void audioParamCheck(int var1_1, int var2_2, int var3_3, int var4_4, int var5_5) {
        block15 : {
            block14 : {
                if ((var1_1 < 4000 || var1_1 > 192000) && var1_1 != 0) {
                    var6_7 = new StringBuilder();
                    var6_7.append(var1_1);
                    var6_7.append("Hz is not a supported sample rate.");
                    throw new IllegalArgumentException(var6_7.toString());
                }
                this.mSampleRate = var1_1;
                if (var4_4 == 13) {
                    if (var2_2 != 12) throw new IllegalArgumentException("ENCODING_IEC61937 must be configured as CHANNEL_OUT_STEREO");
                }
                this.mChannelConfiguration = var2_2;
                if (var2_2 == 1 || var2_2 == 2) break block14;
                if (var2_2 == 3) ** GOTO lbl-1000
                if (var2_2 != 4) {
                    ** if (var2_2 == 12) goto lbl-1000
lbl-1000: // 1 sources:
                    {
                        if (var2_2 == 0 && var3_3 != 0) {
                            this.mChannelCount = 0;
                        } else {
                            if (AudioTrack.isMultichannelConfigSupported((int)var2_2) == false) throw new IllegalArgumentException((String)"Unsupported channel configuration.");
                            this.mChannelMask = var2_2;
                            this.mChannelCount = AudioFormat.channelCountFromOutChannelMask((int)var2_2);
                        }
                        ** GOTO lbl30
                    }
                }
                break block14;
lbl-1000: // 2 sources:
                {
                    this.mChannelCount = 2;
                    this.mChannelMask = 12;
                }
                break block15;
            }
            this.mChannelCount = 1;
            this.mChannelMask = 4;
        }
        this.mChannelIndexMask = var3_3;
        if (this.mChannelIndexMask != 0) {
            if (((1 << AudioSystem.OUT_CHANNEL_COUNT_MAX) - 1 & var3_3) != 0) {
                var6_6 = new StringBuilder();
                var6_6.append("Unsupported channel index configuration ");
                var6_6.append(var3_3);
                throw new IllegalArgumentException(var6_6.toString());
            }
            var1_1 = Integer.bitCount(var3_3);
            var2_2 = this.mChannelCount;
            if (var2_2 == 0) {
                this.mChannelCount = var1_1;
            } else if (var2_2 != var1_1) throw new IllegalArgumentException("Channel count must match");
        }
        var1_1 = var4_4;
        if (var4_4 == 1) {
            var1_1 = 2;
        }
        if (AudioFormat.isPublicEncoding(var1_1) == false) throw new IllegalArgumentException("Unsupported audio encoding.");
        this.mAudioFormat = var1_1;
        if (var5_5 != 1) {
            if (var5_5 != 0) throw new IllegalArgumentException("Invalid mode.");
        }
        if (var5_5 != 1) {
            if (AudioFormat.isEncodingLinearPcm(this.mAudioFormat) == false) throw new IllegalArgumentException("Invalid mode.");
        }
        this.mDataLoadMode = var5_5;
    }

    @GuardedBy(value={"mStreamEventCbLock"})
    private void beginStreamEventHandling() {
        if (this.mStreamEventHandlerThread == null) {
            this.mStreamEventHandlerThread = new HandlerThread("android.media.AudioTrack.StreamEvent");
            this.mStreamEventHandlerThread.start();
            Looper looper = this.mStreamEventHandlerThread.getLooper();
            if (looper != null) {
                this.mStreamEventHandler = new StreamEventHandler(looper);
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private boolean blockUntilOffloadDrain(int n) {
        Object object = this.mPlayStateLock;
        synchronized (object) {
            while (this.mPlayState == 4 || this.mPlayState == 5) {
                if (n == 1) {
                    return false;
                }
                try {
                    this.mPlayStateLock.wait();
                }
                catch (InterruptedException interruptedException) {
                }
            }
            return true;
        }
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

    private static float clampGainOrLevel(float f) {
        if (!Float.isNaN(f)) {
            float f2;
            if (f < 0.0f) {
                f2 = 0.0f;
            } else {
                f2 = f;
                if (f > 1.0f) {
                    f2 = 1.0f;
                }
            }
            return f2;
        }
        throw new IllegalArgumentException();
    }

    @GuardedBy(value={"mStreamEventCbLock"})
    private void endStreamEventHandling() {
        HandlerThread handlerThread = this.mStreamEventHandlerThread;
        if (handlerThread != null) {
            handlerThread.quit();
            this.mStreamEventHandlerThread = null;
        }
    }

    public static float getMaxVolume() {
        return 1.0f;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    public static int getMinBufferSize(int var0, int var1_1, int var2_2) {
        block7 : {
            block6 : {
                if (var1_1 == 2) break block6;
                if (var1_1 == 3) ** GOTO lbl-1000
                if (var1_1 != 4) {
                    ** if (var1_1 == 12) goto lbl-1000
lbl-1000: // 1 sources:
                    {
                        if (!AudioTrack.isMultichannelConfigSupported((int)var1_1)) {
                            AudioTrack.loge((String)"getMinBufferSize(): Invalid channel configuration.");
                            return -2;
                        } else {
                            ** GOTO lbl8
                        }
lbl8: // 2 sources:
                        var1_1 = AudioFormat.channelCountFromOutChannelMask((int)var1_1);
                        ** GOTO lbl15
                    }
                }
                break block6;
lbl-1000: // 2 sources:
                {
                    var1_1 = 2;
                }
                break block7;
            }
            var1_1 = 1;
        }
        if (!AudioFormat.isPublicEncoding(var2_2)) {
            AudioTrack.loge("getMinBufferSize(): Invalid audio format.");
            return -2;
        }
        if (var0 >= 4000 && var0 <= 192000) {
            if ((var0 = AudioTrack.native_get_min_buff_size(var0, var1_1, var2_2)) > 0) return var0;
            AudioTrack.loge("getMinBufferSize(): error querying hardware");
            return -1;
        }
        var3_3 = new StringBuilder();
        var3_3.append("getMinBufferSize(): ");
        var3_3.append(var0);
        var3_3.append(" Hz is not a supported sample rate.");
        AudioTrack.loge(var3_3.toString());
        return -2;
    }

    public static float getMinVolume() {
        return 0.0f;
    }

    public static int getNativeOutputSampleRate(int n) {
        return AudioTrack.native_get_output_sample_rate(n);
    }

    public static boolean isDirectPlaybackSupported(AudioFormat audioFormat, AudioAttributes audioAttributes) {
        if (audioFormat != null) {
            if (audioAttributes != null) {
                return AudioTrack.native_is_direct_output_supported(audioFormat.getEncoding(), audioFormat.getSampleRate(), audioFormat.getChannelMask(), audioFormat.getChannelIndexMask(), audioAttributes.getContentType(), audioAttributes.getUsage(), audioAttributes.getFlags());
            }
            throw new IllegalArgumentException("Illegal null AudioAttributes argument");
        }
        throw new IllegalArgumentException("Illegal null AudioFormat argument");
    }

    private static boolean isMultichannelConfigSupported(int n) {
        if ((n & 7420) != n) {
            AudioTrack.loge("Channel configuration features unsupported channels");
            return false;
        }
        int n2 = AudioFormat.channelCountFromOutChannelMask(n);
        if (n2 > AudioSystem.OUT_CHANNEL_COUNT_MAX) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Channel configuration contains too many channels ");
            stringBuilder.append(n2);
            stringBuilder.append(">");
            stringBuilder.append(AudioSystem.OUT_CHANNEL_COUNT_MAX);
            AudioTrack.loge(stringBuilder.toString());
            return false;
        }
        if ((n & 12) != 12) {
            AudioTrack.loge("Front channels must be present in multichannel configurations");
            return false;
        }
        if ((n & 192) != 0 && (n & 192) != 192) {
            AudioTrack.loge("Rear channels can't be used independently");
            return false;
        }
        if ((n & 6144) != 0 && (n & 6144) != 6144) {
            AudioTrack.loge("Side channels can't be used independently");
            return false;
        }
        return true;
    }

    private static void logd(String string2) {
        Log.d(TAG, string2);
    }

    private static void loge(String string2) {
        Log.e(TAG, string2);
    }

    private native int native_applyVolumeShaper(VolumeShaper.Configuration var1, VolumeShaper.Operation var2);

    private final native int native_attachAuxEffect(int var1);

    private final native void native_disableDeviceCallback();

    private final native void native_enableDeviceCallback();

    private final native void native_finalize();

    private final native void native_flush();

    private native PersistableBundle native_getMetrics();

    private native int native_getPortId();

    private final native int native_getRoutedDeviceId();

    private native VolumeShaper.State native_getVolumeShaperState(int var1);

    private final native int native_get_buffer_capacity_frames();

    private final native int native_get_buffer_size_frames();

    private final native int native_get_flags();

    private final native int native_get_latency();

    private final native int native_get_marker_pos();

    private static final native int native_get_min_buff_size(int var0, int var1, int var2);

    private static final native int native_get_output_sample_rate(int var0);

    private final native PlaybackParams native_get_playback_params();

    private final native int native_get_playback_rate();

    private final native int native_get_pos_update_period();

    private final native int native_get_position();

    private final native int native_get_timestamp(long[] var1);

    private final native int native_get_underrun_count();

    private static native boolean native_is_direct_output_supported(int var0, int var1, int var2, int var3, int var4, int var5, int var6);

    private final native void native_pause();

    private final native int native_reload_static();

    private final native int native_setAuxEffectSendLevel(float var1);

    private final native boolean native_setOutputDevice(int var1);

    private final native int native_setPresentation(int var1, int var2);

    private final native void native_setVolume(float var1, float var2);

    private final native int native_set_buffer_size_frames(int var1);

    private native void native_set_delay_padding(int var1, int var2);

    private final native int native_set_loop(int var1, int var2, int var3);

    private final native int native_set_marker_pos(int var1);

    private final native void native_set_playback_params(PlaybackParams var1);

    private final native int native_set_playback_rate(int var1);

    private final native int native_set_pos_update_period(int var1);

    private final native int native_set_position(int var1);

    private final native int native_setup(Object var1, Object var2, int[] var3, int var4, int var5, int var6, int var7, int var8, int[] var9, long var10, boolean var12);

    private final native void native_start();

    private final native void native_stop();

    private final native int native_write_byte(byte[] var1, int var2, int var3, int var4, boolean var5);

    private final native int native_write_float(float[] var1, int var2, int var3, int var4, boolean var5);

    private final native int native_write_native_bytes(ByteBuffer var1, int var2, int var3, int var4, boolean var5);

    private final native int native_write_short(short[] var1, int var2, int var3, int var4, boolean var5);

    @UnsupportedAppUsage
    private static void postEventFromNative(Object object, int n, int n2, int n3, Object object2) {
        if ((object = (AudioTrack)((WeakReference)object).get()) == null) {
            return;
        }
        if (n == 1000) {
            AudioTrack.super.broadcastRoutingChange();
            return;
        }
        if (n != 9 && n != 6 && n != 7) {
            object = ((AudioTrack)object).mEventHandlerDelegate;
            if (object != null && (object = ((NativePositionEventHandlerDelegate)object).getHandler()) != null) {
                ((Handler)object).sendMessage(((Handler)object).obtainMessage(n, n2, n3, object2));
            }
            return;
        }
        ((AudioTrack)object).handleStreamEventFromNative(n, n2);
    }

    private static boolean shouldEnablePowerSaving(AudioAttributes audioAttributes, AudioFormat audioFormat, int n, int n2) {
        if (audioAttributes != null && (audioAttributes.getAllFlags() != 0 || audioAttributes.getUsage() != 1 || audioAttributes.getContentType() != 0 && audioAttributes.getContentType() != 2 && audioAttributes.getContentType() != 3)) {
            return false;
        }
        if (audioFormat != null && audioFormat.getSampleRate() != 0 && AudioFormat.isEncodingLinearPcm(audioFormat.getEncoding()) && AudioFormat.isValidEncoding(audioFormat.getEncoding()) && audioFormat.getChannelCount() >= 1) {
            long l;
            if (n2 != 1) {
                return false;
            }
            return n == 0 || (long)n >= (l = (long)audioFormat.getChannelCount() * 100L * (long)AudioFormat.getBytesPerSample(audioFormat.getEncoding()) * (long)audioFormat.getSampleRate() / 1000L);
        }
        return false;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void startImpl() {
        Object object = this.mPlayStateLock;
        synchronized (object) {
            this.baseStart();
            this.native_start();
            if (this.mPlayState == 5) {
                this.mPlayState = 4;
            } else {
                this.mPlayState = 3;
                this.mOffloadEosPending = false;
            }
            return;
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

    @Deprecated
    public void addOnRoutingChangedListener(OnRoutingChangedListener onRoutingChangedListener, Handler handler) {
        this.addOnRoutingChangedListener((AudioRouting.OnRoutingChangedListener)onRoutingChangedListener, handler);
    }

    public int attachAuxEffect(int n) {
        if (this.mState == 0) {
            return -3;
        }
        return this.native_attachAuxEffect(n);
    }

    @Override
    public VolumeShaper createVolumeShaper(VolumeShaper.Configuration configuration) {
        return new VolumeShaper(configuration, this);
    }

    @UnsupportedAppUsage
    void deferred_connect(long l) {
        if (this.mState != 1) {
            Object object = new int[]{0};
            int n = this.native_setup(new WeakReference<AudioTrack>(this), null, new int[]{0}, 0, 0, 0, 0, 0, (int[])object, l, false);
            if (n != 0) {
                object = new StringBuilder();
                ((StringBuilder)object).append("Error code ");
                ((StringBuilder)object).append(n);
                ((StringBuilder)object).append(" when initializing AudioTrack.");
                AudioTrack.loge(((StringBuilder)object).toString());
                return;
            }
            this.mSessionId = object[0];
            this.mState = 1;
        }
    }

    protected void finalize() {
        this.baseRelease();
        this.native_finalize();
    }

    public void flush() {
        if (this.mState == 1) {
            this.native_flush();
            this.mAvSyncHeader = null;
            this.mAvSyncBytesRemaining = 0;
        }
    }

    public AudioAttributes getAudioAttributes() {
        AudioAttributes audioAttributes;
        if (this.mState != 0 && (audioAttributes = this.mConfiguredAudioAttributes) != null) {
            return audioAttributes;
        }
        throw new IllegalStateException("track not initialized");
    }

    public int getAudioFormat() {
        return this.mAudioFormat;
    }

    public int getAudioSessionId() {
        return this.mSessionId;
    }

    public int getBufferCapacityInFrames() {
        return this.native_get_buffer_capacity_frames();
    }

    public int getBufferSizeInFrames() {
        return this.native_get_buffer_size_frames();
    }

    public int getChannelConfiguration() {
        return this.mChannelConfiguration;
    }

    public int getChannelCount() {
        return this.mChannelCount;
    }

    public AudioFormat getFormat() {
        AudioFormat.Builder builder = new AudioFormat.Builder().setSampleRate(this.mSampleRate).setEncoding(this.mAudioFormat);
        int n = this.mChannelConfiguration;
        if (n != 0) {
            builder.setChannelMask(n);
        }
        if ((n = this.mChannelIndexMask) != 0) {
            builder.setChannelIndexMask(n);
        }
        return builder.build();
    }

    @UnsupportedAppUsage(trackingBug=130237544L)
    public int getLatency() {
        return this.native_get_latency();
    }

    public PersistableBundle getMetrics() {
        return this.native_getMetrics();
    }

    @Deprecated
    protected int getNativeFrameCount() {
        return this.native_get_buffer_capacity_frames();
    }

    public int getNotificationMarkerPosition() {
        return this.native_get_marker_pos();
    }

    public int getOffloadDelay() {
        if (this.mOffloaded) {
            if (this.mState != 0) {
                return this.mOffloadDelayFrames;
            }
            throw new IllegalStateException("Illegal query of delay on uninitialized track");
        }
        throw new IllegalStateException("Illegal query of delay on non-offloaded track");
    }

    public int getOffloadPadding() {
        if (this.mOffloaded) {
            if (this.mState != 0) {
                return this.mOffloadPaddingFrames;
            }
            throw new IllegalStateException("Illegal query of padding on uninitialized track");
        }
        throw new IllegalStateException("Illegal query of padding on non-offloaded track");
    }

    public int getPerformanceMode() {
        int n = this.native_get_flags();
        if ((n & 4) != 0) {
            return 1;
        }
        if ((n & 8) != 0) {
            return 2;
        }
        return 0;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public int getPlayState() {
        Object object = this.mPlayStateLock;
        synchronized (object) {
            int n = this.mPlayState;
            if (n == 4) {
                return 3;
            }
            if (n == 5) return 2;
            return this.mPlayState;
        }
    }

    public int getPlaybackHeadPosition() {
        return this.native_get_position();
    }

    public PlaybackParams getPlaybackParams() {
        return this.native_get_playback_params();
    }

    public int getPlaybackRate() {
        return this.native_get_playback_rate();
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

    @Override
    public AudioDeviceInfo getRoutedDevice() {
        int n = this.native_getRoutedDeviceId();
        if (n == 0) {
            return null;
        }
        AudioDeviceInfo[] arraudioDeviceInfo = AudioManager.getDevicesStatic(2);
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

    public int getStreamType() {
        return this.mStreamType;
    }

    public boolean getTimestamp(AudioTimestamp audioTimestamp) {
        if (audioTimestamp != null) {
            long[] arrl = new long[2];
            if (this.native_get_timestamp(arrl) != 0) {
                return false;
            }
            audioTimestamp.framePosition = arrl[0];
            audioTimestamp.nanoTime = arrl[1];
            return true;
        }
        throw new IllegalArgumentException();
    }

    public int getTimestampWithStatus(AudioTimestamp audioTimestamp) {
        if (audioTimestamp != null) {
            long[] arrl = new long[2];
            int n = this.native_get_timestamp(arrl);
            audioTimestamp.framePosition = arrl[0];
            audioTimestamp.nanoTime = arrl[1];
            return n;
        }
        throw new IllegalArgumentException();
    }

    public int getUnderrunCount() {
        return this.native_get_underrun_count();
    }

    void handleStreamEventFromNative(int n, int n2) {
        if (this.mStreamEventHandler == null) {
            return;
        }
        if (n != 6) {
            if (n != 7) {
                if (n == 9) {
                    this.mStreamEventHandler.removeMessages(9);
                    this.mStreamEventHandler.sendMessage(this.mStreamEventHandler.obtainMessage(9, n2, 0));
                }
            } else {
                this.mStreamEventHandler.sendMessage(this.mStreamEventHandler.obtainMessage(7));
            }
        } else {
            this.mStreamEventHandler.sendMessage(this.mStreamEventHandler.obtainMessage(6));
        }
    }

    public boolean isOffloadedPlayback() {
        return this.mOffloaded;
    }

    @UnsupportedAppUsage
    public final native void native_release();

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void pause() throws IllegalStateException {
        if (this.mState != 1) {
            throw new IllegalStateException("pause() called on uninitialized AudioTrack.");
        }
        Object object = this.mPlayStateLock;
        synchronized (object) {
            this.native_pause();
            this.basePause();
            this.mPlayState = this.mPlayState == 4 ? 5 : 2;
            return;
        }
    }

    public void play() throws IllegalStateException {
        if (this.mState == 1) {
            final int n = this.getStartDelayMs();
            if (n == 0) {
                this.startImpl();
            } else {
                new Thread(){

                    @Override
                    public void run() {
                        try {
                            Thread.sleep(n);
                        }
                        catch (InterruptedException interruptedException) {
                            interruptedException.printStackTrace();
                        }
                        AudioTrack.this.baseSetStartDelayMs(0);
                        try {
                            AudioTrack.this.startImpl();
                        }
                        catch (IllegalStateException illegalStateException) {
                            // empty catch block
                        }
                    }
                }.start();
            }
            return;
        }
        throw new IllegalStateException("play() called on uninitialized AudioTrack.");
    }

    @Override
    int playerApplyVolumeShaper(VolumeShaper.Configuration configuration, VolumeShaper.Operation operation) {
        return this.native_applyVolumeShaper(configuration, operation);
    }

    @Override
    VolumeShaper.State playerGetVolumeShaperState(int n) {
        return this.native_getVolumeShaperState(n);
    }

    @Override
    void playerPause() {
        this.pause();
    }

    @Override
    int playerSetAuxEffectSendLevel(boolean bl, float f) {
        if (bl) {
            f = 0.0f;
        }
        int n = this.native_setAuxEffectSendLevel(AudioTrack.clampGainOrLevel(f)) == 0 ? 0 : -1;
        return n;
    }

    @Override
    void playerSetVolume(boolean bl, float f, float f2) {
        float f3 = 0.0f;
        if (bl) {
            f = 0.0f;
        }
        f = AudioTrack.clampGainOrLevel(f);
        if (bl) {
            f2 = f3;
        }
        this.native_setVolume(f, AudioTrack.clampGainOrLevel(f2));
    }

    @Override
    void playerStart() {
        this.play();
    }

    @Override
    void playerStop() {
        this.stop();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void registerStreamEventCallback(Executor object, StreamEventCallback streamEventCallback) {
        if (streamEventCallback == null) {
            throw new IllegalArgumentException("Illegal null StreamEventCallback");
        }
        if (!this.mOffloaded) {
            throw new IllegalStateException("Cannot register StreamEventCallback on non-offloaded AudioTrack");
        }
        if (object == null) {
            throw new IllegalArgumentException("Illegal null Executor for the StreamEventCallback");
        }
        Object object2 = this.mStreamEventCbLock;
        synchronized (object2) {
            Object object3 = this.mStreamEventCbInfoList.iterator();
            do {
                if (object3.hasNext()) continue;
                this.beginStreamEventHandling();
                LinkedList<StreamEventCbInfo> linkedList = this.mStreamEventCbInfoList;
                object3 = new StreamEventCbInfo((Executor)object, streamEventCallback);
                linkedList.add((StreamEventCbInfo)object3);
                return;
            } while (((StreamEventCbInfo)object3.next()).mStreamEventCb != streamEventCallback);
            object = new IllegalArgumentException("StreamEventCallback already registered");
            throw object;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void release() {
        Object object = this.mStreamEventCbLock;
        synchronized (object) {
            this.endStreamEventHandling();
        }
        try {
            this.stop();
        }
        catch (IllegalStateException illegalStateException) {
            // empty catch block
        }
        this.baseRelease();
        this.native_release();
        object = this.mPlayStateLock;
        synchronized (object) {
            this.mState = 0;
            this.mPlayState = 1;
            this.mPlayStateLock.notify();
            return;
        }
    }

    public int reloadStaticData() {
        if (this.mDataLoadMode != 1 && this.mState == 1) {
            return this.native_reload_static();
        }
        return -3;
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
            }
            this.testDisableNativeRoutingCallbacksLocked();
            return;
        }
    }

    @Deprecated
    public void removeOnRoutingChangedListener(OnRoutingChangedListener onRoutingChangedListener) {
        this.removeOnRoutingChangedListener((AudioRouting.OnRoutingChangedListener)onRoutingChangedListener);
    }

    public int setAuxEffectSendLevel(float f) {
        if (this.mState == 0) {
            return -3;
        }
        return this.baseSetAuxEffectSendLevel(f);
    }

    public int setBufferSizeInFrames(int n) {
        if (this.mDataLoadMode != 0 && this.mState != 0) {
            if (n < 0) {
                return -2;
            }
            return this.native_set_buffer_size_frames(n);
        }
        return -3;
    }

    public int setLoopPoints(int n, int n2, int n3) {
        if (this.mDataLoadMode != 1 && this.mState != 0 && this.getPlayState() != 3) {
            int n4;
            if (n3 == 0 || n >= 0 && n < (n4 = this.mNativeBufferSizeInFrames) && n < n2 && n2 <= n4) {
                return this.native_set_loop(n, n2, n3);
            }
            return -2;
        }
        return -3;
    }

    public int setNotificationMarkerPosition(int n) {
        if (this.mState == 0) {
            return -3;
        }
        return this.native_set_marker_pos(n);
    }

    public void setOffloadDelayPadding(int n, int n2) {
        if (n2 >= 0) {
            if (n >= 0) {
                if (this.mOffloaded) {
                    if (this.mState != 0) {
                        this.mOffloadDelayFrames = n;
                        this.mOffloadPaddingFrames = n2;
                        this.native_set_delay_padding(n, n2);
                        return;
                    }
                    throw new IllegalStateException("Uninitialized track");
                }
                throw new IllegalStateException("Illegal use of delay/padding on non-offloaded track");
            }
            throw new IllegalArgumentException("Illegal negative delay");
        }
        throw new IllegalArgumentException("Illegal negative padding");
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void setOffloadEndOfStream() {
        if (!this.mOffloaded) {
            throw new IllegalStateException("EOS not supported on non-offloaded track");
        }
        if (this.mState == 0) {
            throw new IllegalStateException("Uninitialized track");
        }
        if (this.mPlayState != 3) {
            throw new IllegalStateException("EOS not supported if not playing");
        }
        Object object = this.mStreamEventCbLock;
        synchronized (object) {
            if (this.mStreamEventCbInfoList.size() != 0) {
                // MONITOREXIT [3, 5] lbl10 : MonitorExitStatement: MONITOREXIT : var1_1
                object = this.mPlayStateLock;
                synchronized (object) {
                    this.native_stop();
                    this.mOffloadEosPending = true;
                    this.mPlayState = 4;
                    return;
                }
            }
            IllegalStateException illegalStateException = new IllegalStateException("EOS not supported without StreamEventCallback");
            throw illegalStateException;
        }
    }

    public int setPlaybackHeadPosition(int n) {
        if (this.mDataLoadMode != 1 && this.mState != 0 && this.getPlayState() != 3) {
            if (n >= 0 && n <= this.mNativeBufferSizeInFrames) {
                return this.native_set_position(n);
            }
            return -2;
        }
        return -3;
    }

    public void setPlaybackParams(PlaybackParams playbackParams) {
        if (playbackParams != null) {
            this.native_set_playback_params(playbackParams);
            return;
        }
        throw new IllegalArgumentException("params is null");
    }

    public void setPlaybackPositionUpdateListener(OnPlaybackPositionUpdateListener onPlaybackPositionUpdateListener) {
        this.setPlaybackPositionUpdateListener(onPlaybackPositionUpdateListener, null);
    }

    public void setPlaybackPositionUpdateListener(OnPlaybackPositionUpdateListener onPlaybackPositionUpdateListener, Handler handler) {
        this.mEventHandlerDelegate = onPlaybackPositionUpdateListener != null ? new NativePositionEventHandlerDelegate(this, onPlaybackPositionUpdateListener, handler) : null;
    }

    public int setPlaybackRate(int n) {
        if (this.mState != 1) {
            return -3;
        }
        if (n <= 0) {
            return -2;
        }
        return this.native_set_playback_rate(n);
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
        if (audioDeviceInfo != null && !audioDeviceInfo.isSink()) {
            return false;
        }
        if (audioDeviceInfo != null) {
            n = audioDeviceInfo.getId();
        }
        if (!(bl = this.native_setOutputDevice(n))) return bl;
        synchronized (this) {
            this.mPreferredDevice = audioDeviceInfo;
            return bl;
        }
    }

    public int setPresentation(AudioPresentation audioPresentation) {
        if (audioPresentation != null) {
            return this.native_setPresentation(audioPresentation.getPresentationId(), audioPresentation.getProgramId());
        }
        throw new IllegalArgumentException("audio presentation is null");
    }

    @Deprecated
    protected void setState(int n) {
        this.mState = n;
    }

    @Deprecated
    public int setStereoVolume(float f, float f2) {
        if (this.mState == 0) {
            return -3;
        }
        this.baseSetVolume(f, f2);
        return 0;
    }

    public int setVolume(float f) {
        return this.setStereoVolume(f, f);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void stop() throws IllegalStateException {
        if (this.mState != 1) {
            throw new IllegalStateException("stop() called on uninitialized AudioTrack.");
        }
        Object object = this.mPlayStateLock;
        synchronized (object) {
            this.native_stop();
            this.baseStop();
            if (this.mOffloaded && this.mPlayState != 5) {
                this.mPlayState = 4;
            } else {
                this.mPlayState = 1;
                this.mOffloadEosPending = false;
                this.mAvSyncHeader = null;
                this.mAvSyncBytesRemaining = 0;
                this.mPlayStateLock.notify();
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void unregisterStreamEventCallback(StreamEventCallback object) {
        if (object == null) {
            throw new IllegalArgumentException("Illegal null StreamEventCallback");
        }
        if (!this.mOffloaded) {
            throw new IllegalStateException("No StreamEventCallback on non-offloaded AudioTrack");
        }
        Object object2 = this.mStreamEventCbLock;
        synchronized (object2) {
            StreamEventCbInfo streamEventCbInfo;
            Iterator<E> iterator = this.mStreamEventCbInfoList.iterator();
            do {
                if (!iterator.hasNext()) {
                    object = new IllegalArgumentException("StreamEventCallback was not registered");
                    throw object;
                }
                streamEventCbInfo = (StreamEventCbInfo)iterator.next();
            } while (streamEventCbInfo.mStreamEventCb != object);
            this.mStreamEventCbInfoList.remove(streamEventCbInfo);
            if (this.mStreamEventCbInfoList.size() == 0) {
                this.endStreamEventHandling();
            }
            return;
        }
    }

    public int write(ByteBuffer object, int n, int n2) {
        if (this.mState == 0) {
            Log.e("android.media.AudioTrack", "AudioTrack.write() called in invalid state STATE_UNINITIALIZED");
            return -3;
        }
        if (n2 != 0 && n2 != 1) {
            Log.e("android.media.AudioTrack", "AudioTrack.write() called with invalid blocking mode");
            return -2;
        }
        if (object != null && n >= 0 && n <= ((Buffer)object).remaining()) {
            if (!this.blockUntilOffloadDrain(n2)) {
                return 0;
            }
            if (((ByteBuffer)object).isDirect()) {
                int n3 = ((Buffer)object).position();
                int n4 = this.mAudioFormat;
                boolean bl = n2 == 0;
                n = this.native_write_native_bytes((ByteBuffer)object, n3, n, n4, bl);
            } else {
                byte[] arrby = NioUtils.unsafeArray((ByteBuffer)object);
                int n5 = NioUtils.unsafeArrayOffset((ByteBuffer)object);
                int n6 = ((Buffer)object).position();
                int n7 = this.mAudioFormat;
                boolean bl = n2 == 0;
                n = this.native_write_byte(arrby, n6 + n5, n, n7, bl);
            }
            if (this.mDataLoadMode == 0 && this.mState == 2 && n > 0) {
                this.mState = 1;
            }
            if (n > 0) {
                ((Buffer)object).position(((Buffer)object).position() + n);
            }
            return n;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("AudioTrack.write() called with invalid size (");
        ((StringBuilder)object).append(n);
        ((StringBuilder)object).append(") value");
        Log.e("android.media.AudioTrack", ((StringBuilder)object).toString());
        return -2;
    }

    public int write(ByteBuffer object, int n, int n2, long l) {
        if (this.mState == 0) {
            Log.e("android.media.AudioTrack", "AudioTrack.write() called in invalid state STATE_UNINITIALIZED");
            return -3;
        }
        if (n2 != 0 && n2 != 1) {
            Log.e("android.media.AudioTrack", "AudioTrack.write() called with invalid blocking mode");
            return -2;
        }
        if (this.mDataLoadMode != 1) {
            Log.e("android.media.AudioTrack", "AudioTrack.write() with timestamp called for non-streaming mode track");
            return -3;
        }
        if ((this.mAttributes.getFlags() & 16) == 0) {
            Log.d("android.media.AudioTrack", "AudioTrack.write() called on a regular AudioTrack. Ignoring pts...");
            return this.write((ByteBuffer)object, n, n2);
        }
        if (object != null && n >= 0 && n <= ((Buffer)object).remaining()) {
            if (!this.blockUntilOffloadDrain(n2)) {
                return 0;
            }
            if (this.mAvSyncHeader == null) {
                this.mAvSyncHeader = ByteBuffer.allocate(this.mOffset);
                this.mAvSyncHeader.order(ByteOrder.BIG_ENDIAN);
                this.mAvSyncHeader.putInt(1431633922);
            }
            if (this.mAvSyncBytesRemaining == 0) {
                this.mAvSyncHeader.putInt(4, n);
                this.mAvSyncHeader.putLong(8, l);
                this.mAvSyncHeader.putInt(16, this.mOffset);
                this.mAvSyncHeader.position(0);
                this.mAvSyncBytesRemaining = n;
            }
            if (this.mAvSyncHeader.remaining() != 0) {
                ByteBuffer byteBuffer = this.mAvSyncHeader;
                int n3 = this.write(byteBuffer, byteBuffer.remaining(), n2);
                if (n3 < 0) {
                    Log.e("android.media.AudioTrack", "AudioTrack.write() could not write timestamp header!");
                    this.mAvSyncHeader = null;
                    this.mAvSyncBytesRemaining = 0;
                    return n3;
                }
                if (this.mAvSyncHeader.remaining() > 0) {
                    Log.v("android.media.AudioTrack", "AudioTrack.write() partial timestamp header written.");
                    return 0;
                }
            }
            if ((n = this.write((ByteBuffer)object, Math.min(this.mAvSyncBytesRemaining, n), n2)) < 0) {
                Log.e("android.media.AudioTrack", "AudioTrack.write() could not write audio data!");
                this.mAvSyncHeader = null;
                this.mAvSyncBytesRemaining = 0;
                return n;
            }
            this.mAvSyncBytesRemaining -= n;
            return n;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("AudioTrack.write() called with invalid size (");
        ((StringBuilder)object).append(n);
        ((StringBuilder)object).append(") value");
        Log.e("android.media.AudioTrack", ((StringBuilder)object).toString());
        return -2;
    }

    public int write(byte[] arrby, int n, int n2) {
        return this.write(arrby, n, n2, 0);
    }

    public int write(byte[] arrby, int n, int n2, int n3) {
        if (this.mState != 0 && this.mAudioFormat != 4) {
            if (n3 != 0 && n3 != 1) {
                Log.e("android.media.AudioTrack", "AudioTrack.write() called with invalid blocking mode");
                return -2;
            }
            if (arrby != null && n >= 0 && n2 >= 0 && n + n2 >= 0 && n + n2 <= arrby.length) {
                if (!this.blockUntilOffloadDrain(n3)) {
                    return 0;
                }
                int n4 = this.mAudioFormat;
                boolean bl = n3 == 0;
                n = this.native_write_byte(arrby, n, n2, n4, bl);
                if (this.mDataLoadMode == 0 && this.mState == 2 && n > 0) {
                    this.mState = 1;
                }
                return n;
            }
            return -2;
        }
        return -3;
    }

    public int write(float[] arrf, int n, int n2, int n3) {
        if (this.mState == 0) {
            Log.e("android.media.AudioTrack", "AudioTrack.write() called in invalid state STATE_UNINITIALIZED");
            return -3;
        }
        if (this.mAudioFormat != 4) {
            Log.e("android.media.AudioTrack", "AudioTrack.write(float[] ...) requires format ENCODING_PCM_FLOAT");
            return -3;
        }
        if (n3 != 0 && n3 != 1) {
            Log.e("android.media.AudioTrack", "AudioTrack.write() called with invalid blocking mode");
            return -2;
        }
        if (arrf != null && n >= 0 && n2 >= 0 && n + n2 >= 0 && n + n2 <= arrf.length) {
            if (!this.blockUntilOffloadDrain(n3)) {
                return 0;
            }
            int n4 = this.mAudioFormat;
            boolean bl = n3 == 0;
            n = this.native_write_float(arrf, n, n2, n4, bl);
            if (this.mDataLoadMode == 0 && this.mState == 2 && n > 0) {
                this.mState = 1;
            }
            return n;
        }
        Log.e("android.media.AudioTrack", "AudioTrack.write() called with invalid array, offset, or size");
        return -2;
    }

    public int write(short[] arrs, int n, int n2) {
        return this.write(arrs, n, n2, 0);
    }

    public int write(short[] arrs, int n, int n2, int n3) {
        if (this.mState != 0 && this.mAudioFormat != 4) {
            if (n3 != 0 && n3 != 1) {
                Log.e("android.media.AudioTrack", "AudioTrack.write() called with invalid blocking mode");
                return -2;
            }
            if (arrs != null && n >= 0 && n2 >= 0 && n + n2 >= 0 && n + n2 <= arrs.length) {
                if (!this.blockUntilOffloadDrain(n3)) {
                    return 0;
                }
                int n4 = this.mAudioFormat;
                boolean bl = n3 == 0;
                n = this.native_write_short(arrs, n, n2, n4, bl);
                if (this.mDataLoadMode == 0 && this.mState == 2 && n > 0) {
                    this.mState = 1;
                }
                return n;
            }
            return -2;
        }
        return -3;
    }

    public static class Builder {
        private AudioAttributes mAttributes;
        private int mBufferSizeInBytes;
        private AudioFormat mFormat;
        private int mMode = 1;
        private boolean mOffload = false;
        private int mPerformanceMode = 0;
        private int mSessionId = 0;

        public AudioTrack build() throws UnsupportedOperationException {
            Object object;
            block14 : {
                int n;
                block18 : {
                    block17 : {
                        block15 : {
                            block16 : {
                                if (this.mAttributes == null) {
                                    this.mAttributes = new AudioAttributes.Builder().setUsage(1).build();
                                }
                                if ((n = this.mPerformanceMode) == 0) break block15;
                                if (n == 1) break block16;
                                if (n == 2) break block17;
                                break block18;
                            }
                            this.mAttributes = new AudioAttributes.Builder(this.mAttributes).replaceFlags((this.mAttributes.getAllFlags() | 256) & -513).build();
                            break block18;
                        }
                        if (!AudioTrack.shouldEnablePowerSaving(this.mAttributes, this.mFormat, this.mBufferSizeInBytes, this.mMode)) break block18;
                    }
                    this.mAttributes = new AudioAttributes.Builder(this.mAttributes).replaceFlags((this.mAttributes.getAllFlags() | 512) & -257).build();
                }
                if (this.mFormat == null) {
                    this.mFormat = new AudioFormat.Builder().setChannelMask(12).setEncoding(1).build();
                }
                if (this.mOffload) {
                    if (this.mPerformanceMode != 1) {
                        if (!AudioSystem.isOffloadSupported(this.mFormat, this.mAttributes)) {
                            throw new UnsupportedOperationException("Cannot create AudioTrack, offload format / attributes not supported");
                        }
                    } else {
                        throw new UnsupportedOperationException("Offload and low latency modes are incompatible");
                    }
                }
                try {
                    if (this.mMode == 1 && this.mBufferSizeInBytes == 0) {
                        n = this.mFormat.getChannelCount();
                        object = this.mFormat;
                        this.mBufferSizeInBytes = n * AudioFormat.getBytesPerSample(this.mFormat.getEncoding());
                    }
                    if (((AudioTrack)(object = new AudioTrack(this.mAttributes, this.mFormat, this.mBufferSizeInBytes, this.mMode, this.mSessionId, this.mOffload))).getState() == 0) break block14;
                    return object;
                }
                catch (IllegalArgumentException illegalArgumentException) {
                    throw new UnsupportedOperationException(illegalArgumentException.getMessage());
                }
            }
            object = new UnsupportedOperationException("Cannot create AudioTrack");
            throw object;
        }

        public Builder setAudioAttributes(AudioAttributes audioAttributes) throws IllegalArgumentException {
            if (audioAttributes != null) {
                this.mAttributes = audioAttributes;
                return this;
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

        public Builder setOffloadedPlayback(boolean bl) {
            this.mOffload = bl;
            return this;
        }

        public Builder setPerformanceMode(int n) {
            if (n != 0 && n != 1 && n != 2) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Invalid performance mode ");
                stringBuilder.append(n);
                throw new IllegalArgumentException(stringBuilder.toString());
            }
            this.mPerformanceMode = n;
            return this;
        }

        public Builder setSessionId(int n) throws IllegalArgumentException {
            if (n != 0 && n < 1) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Invalid audio session ID ");
                stringBuilder.append(n);
                throw new IllegalArgumentException(stringBuilder.toString());
            }
            this.mSessionId = n;
            return this;
        }

        public Builder setTransferMode(int n) throws IllegalArgumentException {
            if (n != 0 && n != 1) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Invalid transfer mode ");
                stringBuilder.append(n);
                throw new IllegalArgumentException(stringBuilder.toString());
            }
            this.mMode = n;
            return this;
        }
    }

    public static final class MetricsConstants {
        public static final String ATTRIBUTES = "android.media.audiotrack.attributes";
        @Deprecated
        public static final String CHANNELMASK = "android.media.audiorecord.channelmask";
        public static final String CHANNEL_MASK = "android.media.audiotrack.channelMask";
        public static final String CONTENTTYPE = "android.media.audiotrack.type";
        public static final String ENCODING = "android.media.audiotrack.encoding";
        public static final String FRAME_COUNT = "android.media.audiotrack.frameCount";
        private static final String MM_PREFIX = "android.media.audiotrack.";
        public static final String PORT_ID = "android.media.audiotrack.portId";
        @Deprecated
        public static final String SAMPLERATE = "android.media.audiorecord.samplerate";
        public static final String SAMPLE_RATE = "android.media.audiotrack.sampleRate";
        public static final String STREAMTYPE = "android.media.audiotrack.streamtype";
        public static final String USAGE = "android.media.audiotrack.usage";

        private MetricsConstants() {
        }
    }

    private class NativePositionEventHandlerDelegate {
        private final Handler mHandler;

        NativePositionEventHandlerDelegate(final AudioTrack audioTrack2, final OnPlaybackPositionUpdateListener onPlaybackPositionUpdateListener, Handler object) {
            object = object != null ? ((Handler)object).getLooper() : AudioTrack.this.mInitializationLooper;
            this.mHandler = object != null ? new Handler((Looper)object){

                @Override
                public void handleMessage(Message object) {
                    if (audioTrack2 == null) {
                        return;
                    }
                    int n = ((Message)object).what;
                    if (n != 3) {
                        if (n != 4) {
                            StringBuilder stringBuilder = new StringBuilder();
                            stringBuilder.append("Unknown native event type: ");
                            stringBuilder.append(((Message)object).what);
                            AudioTrack.loge(stringBuilder.toString());
                        } else {
                            object = onPlaybackPositionUpdateListener;
                            if (object != null) {
                                object.onPeriodicNotification(audioTrack2);
                            }
                        }
                    } else {
                        object = onPlaybackPositionUpdateListener;
                        if (object != null) {
                            object.onMarkerReached(audioTrack2);
                        }
                    }
                }
            } : null;
        }

        Handler getHandler() {
            return this.mHandler;
        }

    }

    public static interface OnPlaybackPositionUpdateListener {
        public void onMarkerReached(AudioTrack var1);

        public void onPeriodicNotification(AudioTrack var1);
    }

    @Deprecated
    public static interface OnRoutingChangedListener
    extends AudioRouting.OnRoutingChangedListener {
        @Override
        default public void onRoutingChanged(AudioRouting audioRouting) {
            if (audioRouting instanceof AudioTrack) {
                this.onRoutingChanged((AudioTrack)audioRouting);
            }
        }

        public void onRoutingChanged(AudioTrack var1);
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface PerformanceMode {
    }

    public static abstract class StreamEventCallback {
        public void onDataRequest(AudioTrack audioTrack, int n) {
        }

        public void onPresentationEnded(AudioTrack audioTrack) {
        }

        public void onTearDown(AudioTrack audioTrack) {
        }
    }

    private static class StreamEventCbInfo {
        final StreamEventCallback mStreamEventCb;
        final Executor mStreamEventExec;

        StreamEventCbInfo(Executor executor, StreamEventCallback streamEventCallback) {
            this.mStreamEventExec = executor;
            this.mStreamEventCb = streamEventCallback;
        }
    }

    private class StreamEventHandler
    extends Handler {
        StreamEventHandler(Looper looper) {
            super(looper);
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void handleMessage(Message message) {
            Object object;
            Iterator iterator = AudioTrack.this.mStreamEventCbLock;
            synchronized (iterator) {
                if (message.what == 7) {
                    object = AudioTrack.this.mPlayStateLock;
                    synchronized (object) {
                        if (AudioTrack.this.mPlayState == 4) {
                            if (AudioTrack.this.mOffloadEosPending) {
                                AudioTrack.this.native_start();
                                AudioTrack.this.mPlayState = 3;
                            } else {
                                AudioTrack.this.mAvSyncHeader = null;
                                AudioTrack.this.mAvSyncBytesRemaining = 0;
                                AudioTrack.this.mPlayState = 1;
                            }
                            AudioTrack.this.mOffloadEosPending = false;
                            AudioTrack.this.mPlayStateLock.notify();
                        }
                    }
                }
                if (AudioTrack.this.mStreamEventCbInfoList.size() == 0) {
                    return;
                }
                object = new LinkedList(AudioTrack.this.mStreamEventCbInfoList);
            }
            long l = Binder.clearCallingIdentity();
            try {
                iterator = ((AbstractSequentialList)object).iterator();
                while (iterator.hasNext()) {
                    Runnable runnable;
                    Executor executor;
                    object = (StreamEventCbInfo)iterator.next();
                    int n = message.what;
                    if (n != 6) {
                        if (n != 7) {
                            if (n != 9) continue;
                            executor = ((StreamEventCbInfo)object).mStreamEventExec;
                            runnable = new _$$Lambda$AudioTrack$StreamEventHandler$IUDediua4qA5AgKwU3zNCXA7jQo(this, (StreamEventCbInfo)object, message);
                            executor.execute(runnable);
                            continue;
                        }
                        executor = ((StreamEventCbInfo)object).mStreamEventExec;
                        runnable = new _$$Lambda$AudioTrack$StreamEventHandler$_3NLz6Sbq0z_YUytzGW6tVjPCao(this, (StreamEventCbInfo)object);
                        executor.execute(runnable);
                        continue;
                    }
                    executor = ((StreamEventCbInfo)object).mStreamEventExec;
                    runnable = new _$$Lambda$AudioTrack$StreamEventHandler$uWnWUbk1g3MhAY3NoSFc6o37wsk(this, (StreamEventCbInfo)object);
                    executor.execute(runnable);
                }
                return;
            }
            finally {
                Binder.restoreCallingIdentity(l);
            }
        }

        public /* synthetic */ void lambda$handleMessage$0$AudioTrack$StreamEventHandler(StreamEventCbInfo streamEventCbInfo, Message message) {
            streamEventCbInfo.mStreamEventCb.onDataRequest(AudioTrack.this, message.arg1);
        }

        public /* synthetic */ void lambda$handleMessage$1$AudioTrack$StreamEventHandler(StreamEventCbInfo streamEventCbInfo) {
            streamEventCbInfo.mStreamEventCb.onTearDown(AudioTrack.this);
        }

        public /* synthetic */ void lambda$handleMessage$2$AudioTrack$StreamEventHandler(StreamEventCbInfo streamEventCbInfo) {
            streamEventCbInfo.mStreamEventCb.onPresentationEnded(AudioTrack.this);
        }
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface TransferMode {
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface WriteMode {
    }

}

