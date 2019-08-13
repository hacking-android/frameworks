/*
 * Decompiled with CFR 0.145.
 */
package android.media;

import android.annotation.SystemApi;
import android.annotation.UnsupportedAppUsage;
import android.app.ActivityThread;
import android.hardware.Camera;
import android.media.AudioDeviceInfo;
import android.media.AudioManager;
import android.media.AudioRecordingConfiguration;
import android.media.AudioRecordingMonitor;
import android.media.AudioRecordingMonitorClient;
import android.media.AudioRecordingMonitorImpl;
import android.media.AudioRouting;
import android.media.CamcorderProfile;
import android.media.MediaCodec;
import android.media.MicrophoneDirection;
import android.media.MicrophoneInfo;
import android.media.NativeRoutingEventHandlerDelegate;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.PersistableBundle;
import android.util.ArrayMap;
import android.util.Log;
import android.util.Pair;
import android.view.Surface;
import com.android.internal.annotations.GuardedBy;
import com.android.internal.util.Preconditions;
import java.io.File;
import java.io.FileDescriptor;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Executor;

public class MediaRecorder
implements AudioRouting,
AudioRecordingMonitor,
AudioRecordingMonitorClient,
MicrophoneDirection {
    public static final int MEDIA_ERROR_SERVER_DIED = 100;
    public static final int MEDIA_RECORDER_ERROR_UNKNOWN = 1;
    public static final int MEDIA_RECORDER_INFO_MAX_DURATION_REACHED = 800;
    public static final int MEDIA_RECORDER_INFO_MAX_FILESIZE_APPROACHING = 802;
    public static final int MEDIA_RECORDER_INFO_MAX_FILESIZE_REACHED = 801;
    public static final int MEDIA_RECORDER_INFO_NEXT_OUTPUT_FILE_STARTED = 803;
    public static final int MEDIA_RECORDER_INFO_UNKNOWN = 1;
    public static final int MEDIA_RECORDER_TRACK_INFO_COMPLETION_STATUS = 1000;
    public static final int MEDIA_RECORDER_TRACK_INFO_DATA_KBYTES = 1009;
    public static final int MEDIA_RECORDER_TRACK_INFO_DURATION_MS = 1003;
    public static final int MEDIA_RECORDER_TRACK_INFO_ENCODED_FRAMES = 1005;
    public static final int MEDIA_RECORDER_TRACK_INFO_INITIAL_DELAY_MS = 1007;
    public static final int MEDIA_RECORDER_TRACK_INFO_LIST_END = 2000;
    public static final int MEDIA_RECORDER_TRACK_INFO_LIST_START = 1000;
    public static final int MEDIA_RECORDER_TRACK_INFO_MAX_CHUNK_DUR_MS = 1004;
    public static final int MEDIA_RECORDER_TRACK_INFO_PROGRESS_IN_TIME = 1001;
    public static final int MEDIA_RECORDER_TRACK_INFO_START_OFFSET_MS = 1008;
    public static final int MEDIA_RECORDER_TRACK_INFO_TYPE = 1002;
    public static final int MEDIA_RECORDER_TRACK_INTER_CHUNK_TIME_MS = 1006;
    private static final String TAG = "MediaRecorder";
    private int mChannelCount;
    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    private EventHandler mEventHandler;
    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    private FileDescriptor mFd;
    private File mFile;
    private long mNativeContext;
    @UnsupportedAppUsage
    private OnErrorListener mOnErrorListener;
    @UnsupportedAppUsage
    private OnInfoListener mOnInfoListener;
    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    private String mPath;
    private AudioDeviceInfo mPreferredDevice = null;
    AudioRecordingMonitorImpl mRecordingInfoImpl = new AudioRecordingMonitorImpl(this);
    @GuardedBy(value={"mRoutingChangeListeners"})
    private ArrayMap<AudioRouting.OnRoutingChangedListener, NativeRoutingEventHandlerDelegate> mRoutingChangeListeners = new ArrayMap();
    @UnsupportedAppUsage
    private Surface mSurface;

    static {
        System.loadLibrary("media_jni");
        MediaRecorder.native_init();
    }

    public MediaRecorder() {
        Object object = Looper.myLooper();
        this.mEventHandler = object != null ? new EventHandler(this, (Looper)object) : ((object = Looper.getMainLooper()) != null ? new EventHandler(this, (Looper)object) : null);
        this.mChannelCount = 1;
        object = ActivityThread.currentPackageName();
        this.native_setup(new WeakReference<MediaRecorder>(this), (String)object, ActivityThread.currentOpPackageName());
    }

    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    private native void _prepare() throws IllegalStateException, IOException;

    private native void _setNextOutputFile(FileDescriptor var1) throws IllegalStateException, IOException;

    private native void _setOutputFile(FileDescriptor var1) throws IllegalStateException, IOException;

    static /* synthetic */ long access$000(MediaRecorder mediaRecorder) {
        return mediaRecorder.mNativeContext;
    }

    static /* synthetic */ OnErrorListener access$100(MediaRecorder mediaRecorder) {
        return mediaRecorder.mOnErrorListener;
    }

    static /* synthetic */ OnInfoListener access$200(MediaRecorder mediaRecorder) {
        return mediaRecorder.mOnInfoListener;
    }

    static /* synthetic */ ArrayMap access$300(MediaRecorder mediaRecorder) {
        return mediaRecorder.mRoutingChangeListeners;
    }

    @GuardedBy(value={"mRoutingChangeListeners"})
    private void enableNativeRoutingCallbacksLocked(boolean bl) {
        if (this.mRoutingChangeListeners.size() == 0) {
            this.native_enableDeviceCallback(bl);
        }
    }

    public static final int getAudioSourceMax() {
        return 10;
    }

    public static boolean isSystemOnlyAudioSource(int n) {
        switch (n) {
            default: {
                return true;
            }
            case 0: 
            case 1: 
            case 2: 
            case 3: 
            case 4: 
            case 5: 
            case 6: 
            case 7: 
            case 9: 
            case 10: 
        }
        return false;
    }

    private final native void native_enableDeviceCallback(boolean var1);

    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    private final native void native_finalize();

    private final native int native_getActiveMicrophones(ArrayList<MicrophoneInfo> var1);

    private native PersistableBundle native_getMetrics();

    private native int native_getPortId();

    private final native int native_getRoutedDeviceId();

    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    private static final native void native_init();

    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    private native void native_reset();

    private final native boolean native_setInputDevice(int var1);

    private final native void native_setInputSurface(Surface var1);

    private native int native_setPreferredMicrophoneDirection(int var1);

    private native int native_setPreferredMicrophoneFieldDimension(float var1);

    @UnsupportedAppUsage
    private final native void native_setup(Object var1, String var2, String var3) throws IllegalStateException;

    private static void postEventFromNative(Object object, int n, int n2, int n3, Object object2) {
        if ((object = (MediaRecorder)((WeakReference)object).get()) == null) {
            return;
        }
        EventHandler eventHandler = ((MediaRecorder)object).mEventHandler;
        if (eventHandler != null) {
            object2 = eventHandler.obtainMessage(n, n2, n3, object2);
            ((MediaRecorder)object).mEventHandler.sendMessage((Message)object2);
        }
    }

    @UnsupportedAppUsage
    private native void setParameter(String var1);

    public static final String toLogFriendlyAudioSource(int n) {
        switch (n) {
            default: {
                switch (n) {
                    default: {
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("unknown source ");
                        stringBuilder.append(n);
                        return stringBuilder.toString();
                    }
                    case 1999: {
                        return "HOTWORD";
                    }
                    case 1998: {
                        return "RADIO_TUNER";
                    }
                    case 1997: 
                }
                return "ECHO_REFERENCE";
            }
            case 10: {
                return "VOICE_PERFORMANCE";
            }
            case 9: {
                return "UNPROCESSED";
            }
            case 8: {
                return "REMOTE_SUBMIX";
            }
            case 7: {
                return "VOICE_COMMUNICATION";
            }
            case 6: {
                return "VOICE_RECOGNITION";
            }
            case 5: {
                return "CAMCORDER";
            }
            case 4: {
                return "VOICE_CALL";
            }
            case 3: {
                return "VOICE_DOWNLINK";
            }
            case 2: {
                return "VOICE_UPLINK";
            }
            case 1: {
                return "MIC";
            }
            case 0: {
                return "DEFAULT";
            }
            case -1: 
        }
        return "AUDIO_SOURCE_INVALID";
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
                this.enableNativeRoutingCallbacksLocked(true);
                ArrayMap<AudioRouting.OnRoutingChangedListener, NativeRoutingEventHandlerDelegate> arrayMap2 = this.mRoutingChangeListeners;
                if (handler == null) {
                    handler = this.mEventHandler;
                }
                NativeRoutingEventHandlerDelegate nativeRoutingEventHandlerDelegate = new NativeRoutingEventHandlerDelegate(this, onRoutingChangedListener, handler);
                arrayMap2.put(onRoutingChangedListener, nativeRoutingEventHandlerDelegate);
            }
            return;
        }
    }

    protected void finalize() {
        this.native_finalize();
    }

    public List<MicrophoneInfo> getActiveMicrophones() throws IOException {
        ArrayList<Pair<Integer, Integer>> arrayList;
        ArrayList<MicrophoneInfo> arrayList2 = new ArrayList<MicrophoneInfo>();
        int n = this.native_getActiveMicrophones(arrayList2);
        if (n != 0) {
            if (n != -3) {
                arrayList = new StringBuilder();
                ((StringBuilder)((Object)arrayList)).append("getActiveMicrophones failed:");
                ((StringBuilder)((Object)arrayList)).append(n);
                Log.e("MediaRecorder", ((StringBuilder)((Object)arrayList)).toString());
            }
            Log.i("MediaRecorder", "getActiveMicrophones failed, fallback on routed device info");
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

    public native int getMaxAmplitude() throws IllegalStateException;

    public PersistableBundle getMetrics() {
        return this.native_getMetrics();
    }

    @Override
    public int getPortId() {
        return this.native_getPortId();
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
        AudioDeviceInfo[] arraudioDeviceInfo = AudioManager.getDevicesStatic(1);
        for (int i = 0; i < arraudioDeviceInfo.length; ++i) {
            if (arraudioDeviceInfo[i].getId() != n) continue;
            return arraudioDeviceInfo[i];
        }
        return null;
    }

    public native Surface getSurface();

    public native void pause() throws IllegalStateException;

    public void prepare() throws IllegalStateException, IOException {
        block12 : {
            Object object;
            block11 : {
                block10 : {
                    object = this.mPath;
                    if (object != null) {
                        object = new RandomAccessFile((String)object, "rw");
                        try {
                            this._setOutputFile(((RandomAccessFile)object).getFD());
                        }
                        finally {
                            ((RandomAccessFile)object).close();
                        }
                    }
                    object = this.mFd;
                    if (object == null) break block10;
                    this._setOutputFile((FileDescriptor)object);
                    break block11;
                }
                object = this.mFile;
                if (object == null) break block12;
                object = new RandomAccessFile((File)object, "rw");
                this._setOutputFile(((RandomAccessFile)object).getFD());
            }
            this._prepare();
            return;
            finally {
                ((RandomAccessFile)object).close();
            }
        }
        throw new IOException("No valid output file");
    }

    @Override
    public void registerAudioRecordingCallback(Executor executor, AudioManager.AudioRecordingCallback audioRecordingCallback) {
        this.mRecordingInfoImpl.registerAudioRecordingCallback(executor, audioRecordingCallback);
    }

    public native void release();

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
                this.enableNativeRoutingCallbacksLocked(false);
            }
            return;
        }
    }

    public void reset() {
        this.native_reset();
        this.mEventHandler.removeCallbacksAndMessages(null);
    }

    public native void resume() throws IllegalStateException;

    public void setAudioChannels(int n) {
        if (n > 0) {
            this.mChannelCount = n;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("audio-param-number-of-channels=");
            stringBuilder.append(n);
            this.setParameter(stringBuilder.toString());
            return;
        }
        throw new IllegalArgumentException("Number of channels is not positive");
    }

    public native void setAudioEncoder(int var1) throws IllegalStateException;

    public void setAudioEncodingBitRate(int n) {
        if (n > 0) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("audio-param-encoding-bitrate=");
            stringBuilder.append(n);
            this.setParameter(stringBuilder.toString());
            return;
        }
        throw new IllegalArgumentException("Audio encoding bit rate is not positive");
    }

    public void setAudioSamplingRate(int n) {
        if (n > 0) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("audio-param-sampling-rate=");
            stringBuilder.append(n);
            this.setParameter(stringBuilder.toString());
            return;
        }
        throw new IllegalArgumentException("Audio sampling rate is not positive");
    }

    public native void setAudioSource(int var1) throws IllegalStateException;

    public void setAuxiliaryOutputFile(FileDescriptor fileDescriptor) {
        Log.w("MediaRecorder", "setAuxiliaryOutputFile(FileDescriptor) is no longer supported.");
    }

    public void setAuxiliaryOutputFile(String string2) {
        Log.w("MediaRecorder", "setAuxiliaryOutputFile(String) is no longer supported.");
    }

    @Deprecated
    public native void setCamera(Camera var1);

    public void setCaptureRate(double d) {
        this.setParameter("time-lapse-enable=1");
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("time-lapse-fps=");
        stringBuilder.append(d);
        this.setParameter(stringBuilder.toString());
    }

    public void setInputSurface(Surface surface) {
        if (surface instanceof MediaCodec.PersistentSurface) {
            this.native_setInputSurface(surface);
            return;
        }
        throw new IllegalArgumentException("not a PersistentSurface");
    }

    public void setLocation(float f, float f2) {
        int n = (int)((double)(f * 10000.0f) + 0.5);
        int n2 = (int)((double)(10000.0f * f2) + 0.5);
        if (n <= 900000 && n >= -900000) {
            if (n2 <= 1800000 && n2 >= -1800000) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("param-geotag-latitude=");
                stringBuilder.append(n);
                this.setParameter(stringBuilder.toString());
                stringBuilder = new StringBuilder();
                stringBuilder.append("param-geotag-longitude=");
                stringBuilder.append(n2);
                this.setParameter(stringBuilder.toString());
                return;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Longitude: ");
            stringBuilder.append(f2);
            stringBuilder.append(" out of range");
            throw new IllegalArgumentException(stringBuilder.toString());
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Latitude: ");
        stringBuilder.append(f);
        stringBuilder.append(" out of range.");
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    public native void setMaxDuration(int var1) throws IllegalArgumentException;

    public native void setMaxFileSize(long var1) throws IllegalArgumentException;

    public void setNextOutputFile(File object) throws IOException {
        object = new RandomAccessFile((File)object, "rw");
        try {
            this._setNextOutputFile(((RandomAccessFile)object).getFD());
            return;
        }
        finally {
            ((RandomAccessFile)object).close();
        }
    }

    public void setNextOutputFile(FileDescriptor fileDescriptor) throws IOException {
        this._setNextOutputFile(fileDescriptor);
    }

    public void setOnErrorListener(OnErrorListener onErrorListener) {
        this.mOnErrorListener = onErrorListener;
    }

    public void setOnInfoListener(OnInfoListener onInfoListener) {
        this.mOnInfoListener = onInfoListener;
    }

    public void setOrientationHint(int n) {
        if (n != 0 && n != 90 && n != 180 && n != 270) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Unsupported angle: ");
            stringBuilder.append(n);
            throw new IllegalArgumentException(stringBuilder.toString());
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("video-param-rotation-angle-degrees=");
        stringBuilder.append(n);
        this.setParameter(stringBuilder.toString());
    }

    public void setOutputFile(File file) {
        this.mPath = null;
        this.mFd = null;
        this.mFile = file;
    }

    public void setOutputFile(FileDescriptor fileDescriptor) throws IllegalStateException {
        this.mPath = null;
        this.mFile = null;
        this.mFd = fileDescriptor;
    }

    public void setOutputFile(String string2) throws IllegalStateException {
        this.mFd = null;
        this.mFile = null;
        this.mPath = string2;
    }

    public native void setOutputFormat(int var1) throws IllegalStateException;

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
        boolean bl = this.native_setPreferredMicrophoneDirection(n) == 0;
        return bl;
    }

    @Override
    public boolean setPreferredMicrophoneFieldDimension(float f) {
        boolean bl = true;
        boolean bl2 = f >= -1.0f && f <= 1.0f;
        Preconditions.checkArgument(bl2, "Argument must fall between -1 & 1 (inclusive)");
        bl2 = this.native_setPreferredMicrophoneFieldDimension(f) == 0 ? bl : false;
        return bl2;
    }

    public void setPreviewDisplay(Surface surface) {
        this.mSurface = surface;
    }

    public void setProfile(CamcorderProfile camcorderProfile) {
        this.setOutputFormat(camcorderProfile.fileFormat);
        this.setVideoFrameRate(camcorderProfile.videoFrameRate);
        this.setVideoSize(camcorderProfile.videoFrameWidth, camcorderProfile.videoFrameHeight);
        this.setVideoEncodingBitRate(camcorderProfile.videoBitRate);
        this.setVideoEncoder(camcorderProfile.videoCodec);
        if (camcorderProfile.quality < 1000 || camcorderProfile.quality > 1007) {
            this.setAudioEncodingBitRate(camcorderProfile.audioBitRate);
            this.setAudioChannels(camcorderProfile.audioChannels);
            this.setAudioSamplingRate(camcorderProfile.audioSampleRate);
            this.setAudioEncoder(camcorderProfile.audioCodec);
        }
    }

    public native void setVideoEncoder(int var1) throws IllegalStateException;

    public void setVideoEncodingBitRate(int n) {
        if (n > 0) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("video-param-encoding-bitrate=");
            stringBuilder.append(n);
            this.setParameter(stringBuilder.toString());
            return;
        }
        throw new IllegalArgumentException("Video encoding bit rate is not positive");
    }

    public void setVideoEncodingProfileLevel(int n, int n2) {
        if (n > 0) {
            if (n2 > 0) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("video-param-encoder-profile=");
                stringBuilder.append(n);
                this.setParameter(stringBuilder.toString());
                stringBuilder = new StringBuilder();
                stringBuilder.append("video-param-encoder-level=");
                stringBuilder.append(n2);
                this.setParameter(stringBuilder.toString());
                return;
            }
            throw new IllegalArgumentException("Video encoding level is not positive");
        }
        throw new IllegalArgumentException("Video encoding profile is not positive");
    }

    public native void setVideoFrameRate(int var1) throws IllegalStateException;

    public native void setVideoSize(int var1, int var2) throws IllegalStateException;

    public native void setVideoSource(int var1) throws IllegalStateException;

    public native void start() throws IllegalStateException;

    public native void stop() throws IllegalStateException;

    @Override
    public void unregisterAudioRecordingCallback(AudioManager.AudioRecordingCallback audioRecordingCallback) {
        this.mRecordingInfoImpl.unregisterAudioRecordingCallback(audioRecordingCallback);
    }

    public final class AudioEncoder {
        public static final int AAC = 3;
        public static final int AAC_ELD = 5;
        public static final int AMR_NB = 1;
        public static final int AMR_WB = 2;
        public static final int DEFAULT = 0;
        public static final int HE_AAC = 4;
        public static final int OPUS = 7;
        public static final int VORBIS = 6;

        private AudioEncoder() {
        }
    }

    public final class AudioSource {
        public static final int AUDIO_SOURCE_INVALID = -1;
        public static final int CAMCORDER = 5;
        public static final int DEFAULT = 0;
        @SystemApi
        public static final int ECHO_REFERENCE = 1997;
        @SystemApi
        public static final int HOTWORD = 1999;
        public static final int MIC = 1;
        @SystemApi
        public static final int RADIO_TUNER = 1998;
        public static final int REMOTE_SUBMIX = 8;
        public static final int UNPROCESSED = 9;
        public static final int VOICE_CALL = 4;
        public static final int VOICE_COMMUNICATION = 7;
        public static final int VOICE_DOWNLINK = 3;
        public static final int VOICE_PERFORMANCE = 10;
        public static final int VOICE_RECOGNITION = 6;
        public static final int VOICE_UPLINK = 2;

        private AudioSource() {
        }
    }

    private class EventHandler
    extends Handler {
        private static final int MEDIA_RECORDER_AUDIO_ROUTING_CHANGED = 10000;
        private static final int MEDIA_RECORDER_EVENT_ERROR = 1;
        private static final int MEDIA_RECORDER_EVENT_INFO = 2;
        private static final int MEDIA_RECORDER_EVENT_LIST_END = 99;
        private static final int MEDIA_RECORDER_EVENT_LIST_START = 1;
        private static final int MEDIA_RECORDER_TRACK_EVENT_ERROR = 100;
        private static final int MEDIA_RECORDER_TRACK_EVENT_INFO = 101;
        private static final int MEDIA_RECORDER_TRACK_EVENT_LIST_END = 1000;
        private static final int MEDIA_RECORDER_TRACK_EVENT_LIST_START = 100;
        private MediaRecorder mMediaRecorder;

        public EventHandler(MediaRecorder mediaRecorder2, Looper looper) {
            super(looper);
            this.mMediaRecorder = mediaRecorder2;
        }

        /*
         * Unable to fully structure code
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         * Converted monitor instructions to comments
         * Lifted jumps to return sites
         */
        @Override
        public void handleMessage(Message var1_1) {
            if (MediaRecorder.access$000(this.mMediaRecorder) == 0L) {
                Log.w("MediaRecorder", "mediarecorder went away with unhandled events");
                return;
            }
            var2_2 = var1_1.what;
            if (var2_2 != 1) {
                if (var2_2 != 2) {
                    if (var2_2 != 100) {
                        if (var2_2 != 101) {
                            if (var2_2 != 10000) {
                                var3_3 = new StringBuilder();
                                var3_3.append("Unknown message type ");
                                var3_3.append(var1_1.what);
                                Log.e("MediaRecorder", var3_3.toString());
                                return;
                            }
                            AudioManager.resetAudioPortGeneration();
                            var1_1 = MediaRecorder.access$300(MediaRecorder.this);
                            // MONITORENTER : var1_1
                            var3_4 = MediaRecorder.access$300(MediaRecorder.this).values().iterator();
                            do {
                                if (!var3_4.hasNext()) {
                                    // MONITOREXIT : var1_1
                                    return;
                                }
                                ((NativeRoutingEventHandlerDelegate)var3_4.next()).notifyClient();
                            } while (true);
                        } else {
                            ** GOTO lbl-1000
                        }
                    }
                } else lbl-1000: // 3 sources:
                {
                    if (MediaRecorder.access$200(MediaRecorder.this) == null) return;
                    MediaRecorder.access$200(MediaRecorder.this).onInfo(this.mMediaRecorder, var1_1.arg1, var1_1.arg2);
                    return;
                }
            }
            if (MediaRecorder.access$100(MediaRecorder.this) == null) return;
            MediaRecorder.access$100(MediaRecorder.this).onError(this.mMediaRecorder, var1_1.arg1, var1_1.arg2);
        }
    }

    public static final class MetricsConstants {
        public static final String AUDIO_BITRATE = "android.media.mediarecorder.audio-bitrate";
        public static final String AUDIO_CHANNELS = "android.media.mediarecorder.audio-channels";
        public static final String AUDIO_SAMPLERATE = "android.media.mediarecorder.audio-samplerate";
        public static final String AUDIO_TIMESCALE = "android.media.mediarecorder.audio-timescale";
        public static final String CAPTURE_FPS = "android.media.mediarecorder.capture-fps";
        public static final String CAPTURE_FPS_ENABLE = "android.media.mediarecorder.capture-fpsenable";
        public static final String FRAMERATE = "android.media.mediarecorder.frame-rate";
        public static final String HEIGHT = "android.media.mediarecorder.height";
        public static final String MOVIE_TIMESCALE = "android.media.mediarecorder.movie-timescale";
        public static final String ROTATION = "android.media.mediarecorder.rotation";
        public static final String VIDEO_BITRATE = "android.media.mediarecorder.video-bitrate";
        public static final String VIDEO_IFRAME_INTERVAL = "android.media.mediarecorder.video-iframe-interval";
        public static final String VIDEO_LEVEL = "android.media.mediarecorder.video-encoder-level";
        public static final String VIDEO_PROFILE = "android.media.mediarecorder.video-encoder-profile";
        public static final String VIDEO_TIMESCALE = "android.media.mediarecorder.video-timescale";
        public static final String WIDTH = "android.media.mediarecorder.width";

        private MetricsConstants() {
        }
    }

    public static interface OnErrorListener {
        public void onError(MediaRecorder var1, int var2, int var3);
    }

    public static interface OnInfoListener {
        public void onInfo(MediaRecorder var1, int var2, int var3);
    }

    public final class OutputFormat {
        public static final int AAC_ADIF = 5;
        public static final int AAC_ADTS = 6;
        public static final int AMR_NB = 3;
        public static final int AMR_WB = 4;
        public static final int DEFAULT = 0;
        public static final int HEIF = 10;
        public static final int MPEG_2_TS = 8;
        public static final int MPEG_4 = 2;
        public static final int OGG = 11;
        public static final int OUTPUT_FORMAT_RTP_AVP = 7;
        public static final int RAW_AMR = 3;
        public static final int THREE_GPP = 1;
        public static final int WEBM = 9;

        private OutputFormat() {
        }
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface Source {
    }

    public final class VideoEncoder {
        public static final int DEFAULT = 0;
        public static final int H263 = 1;
        public static final int H264 = 2;
        public static final int HEVC = 5;
        public static final int MPEG_4_SP = 3;
        public static final int VP8 = 4;

        private VideoEncoder() {
        }
    }

    public final class VideoSource {
        public static final int CAMERA = 1;
        public static final int DEFAULT = 0;
        public static final int SURFACE = 2;

        private VideoSource() {
        }
    }

}

