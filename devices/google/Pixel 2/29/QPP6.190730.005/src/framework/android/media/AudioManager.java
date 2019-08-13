/*
 * Decompiled with CFR 0.145.
 */
package android.media;

import android.annotation.SuppressLint;
import android.annotation.SystemApi;
import android.annotation.UnsupportedAppUsage;
import android.app.PendingIntent;
import android.bluetooth.BluetoothCodecConfig;
import android.bluetooth.BluetoothDevice;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.res.Resources;
import android.media.AudioAttributes;
import android.media.AudioDeviceCallback;
import android.media.AudioDeviceInfo;
import android.media.AudioDevicePort;
import android.media.AudioFocusInfo;
import android.media.AudioFocusRequest;
import android.media.AudioFormat;
import android.media.AudioGain;
import android.media.AudioGainConfig;
import android.media.AudioHandle;
import android.media.AudioPatch;
import android.media.AudioPlaybackConfiguration;
import android.media.AudioPort;
import android.media.AudioPortConfig;
import android.media.AudioPortEventHandler;
import android.media.AudioRecordingConfiguration;
import android.media.AudioSystem;
import android.media.IAudioFocusDispatcher;
import android.media.IAudioServerStateDispatcher;
import android.media.IAudioService;
import android.media.IPlaybackConfigDispatcher;
import android.media.IRecordingConfigDispatcher;
import android.media.IRingtonePlayer;
import android.media.IVolumeController;
import android.media.MicrophoneInfo;
import android.media.PlayerBase;
import android.media.RemoteControlClient;
import android.media.RemoteController;
import android.media.VolumePolicy;
import android.media._$$Lambda$AudioManager$4$7k7uSoMGULBCueASQSmf9jAil7I;
import android.media._$$Lambda$AudioManager$4$Q85LmhgKDCoq1YI14giFabZrM7A;
import android.media.audiopolicy.AudioPolicy;
import android.media.audiopolicy.AudioPolicyConfig;
import android.media.audiopolicy.AudioProductStrategy;
import android.media.audiopolicy.AudioVolumeGroup;
import android.media.audiopolicy.AudioVolumeGroupChangeHandler;
import android.media.audiopolicy.IAudioPolicyCallback;
import android.media.projection.IMediaProjection;
import android.media.projection.MediaProjection;
import android.media.session.MediaSessionLegacyHelper;
import android.net.Uri;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Process;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.SystemClock;
import android.os.UserHandle;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.Log;
import android.util.Pair;
import android.view.KeyEvent;
import com.android.internal.annotations.GuardedBy;
import com.android.internal.util.Preconditions;
import java.io.IOException;
import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executor;

public class AudioManager {
    public static final String ACTION_AUDIO_BECOMING_NOISY = "android.media.AUDIO_BECOMING_NOISY";
    public static final String ACTION_HDMI_AUDIO_PLUG = "android.media.action.HDMI_AUDIO_PLUG";
    public static final String ACTION_HEADSET_PLUG = "android.intent.action.HEADSET_PLUG";
    public static final String ACTION_MICROPHONE_MUTE_CHANGED = "android.media.action.MICROPHONE_MUTE_CHANGED";
    @Deprecated
    public static final String ACTION_SCO_AUDIO_STATE_CHANGED = "android.media.SCO_AUDIO_STATE_CHANGED";
    public static final String ACTION_SCO_AUDIO_STATE_UPDATED = "android.media.ACTION_SCO_AUDIO_STATE_UPDATED";
    public static final String ACTION_SPEAKERPHONE_STATE_CHANGED = "android.media.action.SPEAKERPHONE_STATE_CHANGED";
    public static final int ADJUST_LOWER = -1;
    public static final int ADJUST_MUTE = -100;
    public static final int ADJUST_RAISE = 1;
    public static final int ADJUST_SAME = 0;
    public static final int ADJUST_TOGGLE_MUTE = 101;
    public static final int ADJUST_UNMUTE = 100;
    public static final int AUDIOFOCUS_FLAGS_APPS = 3;
    public static final int AUDIOFOCUS_FLAGS_SYSTEM = 7;
    @SystemApi
    public static final int AUDIOFOCUS_FLAG_DELAY_OK = 1;
    @SystemApi
    public static final int AUDIOFOCUS_FLAG_LOCK = 4;
    @SystemApi
    public static final int AUDIOFOCUS_FLAG_PAUSES_ON_DUCKABLE_LOSS = 2;
    public static final int AUDIOFOCUS_GAIN = 1;
    public static final int AUDIOFOCUS_GAIN_TRANSIENT = 2;
    public static final int AUDIOFOCUS_GAIN_TRANSIENT_EXCLUSIVE = 4;
    public static final int AUDIOFOCUS_GAIN_TRANSIENT_MAY_DUCK = 3;
    public static final int AUDIOFOCUS_LOSS = -1;
    public static final int AUDIOFOCUS_LOSS_TRANSIENT = -2;
    public static final int AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK = -3;
    public static final int AUDIOFOCUS_NONE = 0;
    public static final int AUDIOFOCUS_REQUEST_DELAYED = 2;
    public static final int AUDIOFOCUS_REQUEST_FAILED = 0;
    public static final int AUDIOFOCUS_REQUEST_GRANTED = 1;
    public static final int AUDIOFOCUS_REQUEST_WAITING_FOR_EXT_POLICY = 100;
    static final int AUDIOPORT_GENERATION_INIT = 0;
    public static final int AUDIO_SESSION_ID_GENERATE = 0;
    private static final boolean DEBUG = false;
    public static final int DEVICE_IN_ANLG_DOCK_HEADSET = -2147483136;
    public static final int DEVICE_IN_BACK_MIC = -2147483520;
    public static final int DEVICE_IN_BLUETOOTH_SCO_HEADSET = -2147483640;
    public static final int DEVICE_IN_BUILTIN_MIC = -2147483644;
    public static final int DEVICE_IN_DGTL_DOCK_HEADSET = -2147482624;
    public static final int DEVICE_IN_FM_TUNER = -2147475456;
    public static final int DEVICE_IN_HDMI = -2147483616;
    public static final int DEVICE_IN_HDMI_ARC = -2013265920;
    public static final int DEVICE_IN_LINE = -2147450880;
    public static final int DEVICE_IN_LOOPBACK = -2147221504;
    public static final int DEVICE_IN_SPDIF = -2147418112;
    public static final int DEVICE_IN_TELEPHONY_RX = -2147483584;
    public static final int DEVICE_IN_TV_TUNER = -2147467264;
    public static final int DEVICE_IN_USB_ACCESSORY = -2147481600;
    public static final int DEVICE_IN_USB_DEVICE = -2147479552;
    public static final int DEVICE_IN_WIRED_HEADSET = -2147483632;
    public static final int DEVICE_NONE = 0;
    @UnsupportedAppUsage
    public static final int DEVICE_OUT_ANLG_DOCK_HEADSET = 2048;
    public static final int DEVICE_OUT_AUX_DIGITAL = 1024;
    @UnsupportedAppUsage
    public static final int DEVICE_OUT_BLUETOOTH_A2DP = 128;
    @UnsupportedAppUsage
    public static final int DEVICE_OUT_BLUETOOTH_A2DP_HEADPHONES = 256;
    @UnsupportedAppUsage
    public static final int DEVICE_OUT_BLUETOOTH_A2DP_SPEAKER = 512;
    public static final int DEVICE_OUT_BLUETOOTH_SCO = 16;
    public static final int DEVICE_OUT_BLUETOOTH_SCO_CARKIT = 64;
    @UnsupportedAppUsage
    public static final int DEVICE_OUT_BLUETOOTH_SCO_HEADSET = 32;
    public static final int DEVICE_OUT_DEFAULT = 1073741824;
    @UnsupportedAppUsage
    public static final int DEVICE_OUT_DGTL_DOCK_HEADSET = 4096;
    @UnsupportedAppUsage
    public static final int DEVICE_OUT_EARPIECE = 1;
    public static final int DEVICE_OUT_FM = 1048576;
    @UnsupportedAppUsage
    public static final int DEVICE_OUT_HDMI = 1024;
    public static final int DEVICE_OUT_HDMI_ARC = 262144;
    public static final int DEVICE_OUT_LINE = 131072;
    public static final int DEVICE_OUT_REMOTE_SUBMIX = 32768;
    public static final int DEVICE_OUT_SPDIF = 524288;
    @UnsupportedAppUsage
    public static final int DEVICE_OUT_SPEAKER = 2;
    public static final int DEVICE_OUT_TELEPHONY_TX = 65536;
    public static final int DEVICE_OUT_USB_ACCESSORY = 8192;
    public static final int DEVICE_OUT_USB_DEVICE = 16384;
    public static final int DEVICE_OUT_USB_HEADSET = 67108864;
    @UnsupportedAppUsage
    public static final int DEVICE_OUT_WIRED_HEADPHONE = 8;
    @UnsupportedAppUsage
    public static final int DEVICE_OUT_WIRED_HEADSET = 4;
    public static final int ERROR = -1;
    public static final int ERROR_BAD_VALUE = -2;
    public static final int ERROR_DEAD_OBJECT = -6;
    public static final int ERROR_INVALID_OPERATION = -3;
    public static final int ERROR_NO_INIT = -5;
    public static final int ERROR_PERMISSION_DENIED = -4;
    public static final String EXTRA_AUDIO_PLUG_STATE = "android.media.extra.AUDIO_PLUG_STATE";
    public static final String EXTRA_ENCODINGS = "android.media.extra.ENCODINGS";
    public static final String EXTRA_MASTER_VOLUME_MUTED = "android.media.EXTRA_MASTER_VOLUME_MUTED";
    public static final String EXTRA_MAX_CHANNEL_COUNT = "android.media.extra.MAX_CHANNEL_COUNT";
    public static final String EXTRA_PREV_VOLUME_STREAM_DEVICES = "android.media.EXTRA_PREV_VOLUME_STREAM_DEVICES";
    public static final String EXTRA_PREV_VOLUME_STREAM_VALUE = "android.media.EXTRA_PREV_VOLUME_STREAM_VALUE";
    public static final String EXTRA_RINGER_MODE = "android.media.EXTRA_RINGER_MODE";
    public static final String EXTRA_SCO_AUDIO_PREVIOUS_STATE = "android.media.extra.SCO_AUDIO_PREVIOUS_STATE";
    public static final String EXTRA_SCO_AUDIO_STATE = "android.media.extra.SCO_AUDIO_STATE";
    public static final String EXTRA_STREAM_VOLUME_MUTED = "android.media.EXTRA_STREAM_VOLUME_MUTED";
    public static final String EXTRA_VIBRATE_SETTING = "android.media.EXTRA_VIBRATE_SETTING";
    public static final String EXTRA_VIBRATE_TYPE = "android.media.EXTRA_VIBRATE_TYPE";
    public static final String EXTRA_VOLUME_STREAM_DEVICES = "android.media.EXTRA_VOLUME_STREAM_DEVICES";
    @UnsupportedAppUsage
    public static final String EXTRA_VOLUME_STREAM_TYPE = "android.media.EXTRA_VOLUME_STREAM_TYPE";
    public static final String EXTRA_VOLUME_STREAM_TYPE_ALIAS = "android.media.EXTRA_VOLUME_STREAM_TYPE_ALIAS";
    @UnsupportedAppUsage
    public static final String EXTRA_VOLUME_STREAM_VALUE = "android.media.EXTRA_VOLUME_STREAM_VALUE";
    private static final int EXT_FOCUS_POLICY_TIMEOUT_MS = 200;
    public static final int FLAG_ACTIVE_MEDIA_ONLY = 512;
    public static final int FLAG_ALLOW_RINGER_MODES = 2;
    public static final int FLAG_BLUETOOTH_ABS_VOLUME = 64;
    public static final int FLAG_FIXED_VOLUME = 32;
    public static final int FLAG_FROM_KEY = 4096;
    public static final int FLAG_HDMI_SYSTEM_AUDIO_VOLUME = 256;
    private static final TreeMap<Integer, String> FLAG_NAMES;
    public static final int FLAG_PLAY_SOUND = 4;
    public static final int FLAG_REMOVE_SOUND_AND_VIBRATE = 8;
    public static final int FLAG_SHOW_SILENT_HINT = 128;
    public static final int FLAG_SHOW_UI = 1;
    public static final int FLAG_SHOW_UI_WARNINGS = 1024;
    public static final int FLAG_SHOW_VIBRATE_HINT = 2048;
    public static final int FLAG_VIBRATE = 16;
    private static final String FOCUS_CLIENT_ID_STRING = "android_audio_focus_client_id";
    public static final int FX_FOCUS_NAVIGATION_DOWN = 2;
    public static final int FX_FOCUS_NAVIGATION_LEFT = 3;
    public static final int FX_FOCUS_NAVIGATION_RIGHT = 4;
    public static final int FX_FOCUS_NAVIGATION_UP = 1;
    public static final int FX_KEYPRESS_DELETE = 7;
    public static final int FX_KEYPRESS_INVALID = 9;
    public static final int FX_KEYPRESS_RETURN = 8;
    public static final int FX_KEYPRESS_SPACEBAR = 6;
    public static final int FX_KEYPRESS_STANDARD = 5;
    public static final int FX_KEY_CLICK = 0;
    public static final int GET_DEVICES_ALL = 3;
    public static final int GET_DEVICES_INPUTS = 1;
    public static final int GET_DEVICES_OUTPUTS = 2;
    public static final String INTERNAL_RINGER_MODE_CHANGED_ACTION = "android.media.INTERNAL_RINGER_MODE_CHANGED_ACTION";
    public static final String MASTER_MUTE_CHANGED_ACTION = "android.media.MASTER_MUTE_CHANGED_ACTION";
    public static final int MODE_CURRENT = -1;
    public static final int MODE_INVALID = -2;
    public static final int MODE_IN_CALL = 2;
    public static final int MODE_IN_COMMUNICATION = 3;
    public static final int MODE_NORMAL = 0;
    public static final int MODE_RINGTONE = 1;
    private static final int MSG_DEVICES_CALLBACK_REGISTERED = 0;
    private static final int MSG_DEVICES_DEVICES_ADDED = 1;
    private static final int MSG_DEVICES_DEVICES_REMOVED = 2;
    private static final int MSSG_FOCUS_CHANGE = 0;
    private static final int MSSG_PLAYBACK_CONFIG_CHANGE = 2;
    private static final int MSSG_RECORDING_CONFIG_CHANGE = 1;
    @UnsupportedAppUsage
    public static final int NUM_SOUND_EFFECTS = 10;
    @Deprecated
    public static final int NUM_STREAMS = 5;
    public static final String PROPERTY_OUTPUT_FRAMES_PER_BUFFER = "android.media.property.OUTPUT_FRAMES_PER_BUFFER";
    public static final String PROPERTY_OUTPUT_SAMPLE_RATE = "android.media.property.OUTPUT_SAMPLE_RATE";
    public static final String PROPERTY_SUPPORT_AUDIO_SOURCE_UNPROCESSED = "android.media.property.SUPPORT_AUDIO_SOURCE_UNPROCESSED";
    public static final String PROPERTY_SUPPORT_MIC_NEAR_ULTRASOUND = "android.media.property.SUPPORT_MIC_NEAR_ULTRASOUND";
    public static final String PROPERTY_SUPPORT_SPEAKER_NEAR_ULTRASOUND = "android.media.property.SUPPORT_SPEAKER_NEAR_ULTRASOUND";
    public static final int RECORDER_STATE_STARTED = 0;
    public static final int RECORDER_STATE_STOPPED = 1;
    public static final int RECORD_CONFIG_EVENT_NONE = -1;
    public static final int RECORD_CONFIG_EVENT_RELEASE = 3;
    public static final int RECORD_CONFIG_EVENT_START = 0;
    public static final int RECORD_CONFIG_EVENT_STOP = 1;
    public static final int RECORD_CONFIG_EVENT_UPDATE = 2;
    public static final int RECORD_RIID_INVALID = -1;
    public static final String RINGER_MODE_CHANGED_ACTION = "android.media.RINGER_MODE_CHANGED";
    public static final int RINGER_MODE_MAX = 2;
    public static final int RINGER_MODE_NORMAL = 2;
    public static final int RINGER_MODE_SILENT = 0;
    public static final int RINGER_MODE_VIBRATE = 1;
    @Deprecated
    public static final int ROUTE_ALL = -1;
    @Deprecated
    public static final int ROUTE_BLUETOOTH = 4;
    @Deprecated
    public static final int ROUTE_BLUETOOTH_A2DP = 16;
    @Deprecated
    public static final int ROUTE_BLUETOOTH_SCO = 4;
    @Deprecated
    public static final int ROUTE_EARPIECE = 1;
    @Deprecated
    public static final int ROUTE_HEADSET = 8;
    @Deprecated
    public static final int ROUTE_SPEAKER = 2;
    public static final int SCO_AUDIO_STATE_CONNECTED = 1;
    public static final int SCO_AUDIO_STATE_CONNECTING = 2;
    public static final int SCO_AUDIO_STATE_DISCONNECTED = 0;
    public static final int SCO_AUDIO_STATE_ERROR = -1;
    public static final int STREAM_ACCESSIBILITY = 10;
    public static final int STREAM_ALARM = 4;
    @UnsupportedAppUsage
    public static final int STREAM_BLUETOOTH_SCO = 6;
    public static final String STREAM_DEVICES_CHANGED_ACTION = "android.media.STREAM_DEVICES_CHANGED_ACTION";
    public static final int STREAM_DTMF = 8;
    public static final int STREAM_MUSIC = 3;
    public static final String STREAM_MUTE_CHANGED_ACTION = "android.media.STREAM_MUTE_CHANGED_ACTION";
    public static final int STREAM_NOTIFICATION = 5;
    public static final int STREAM_RING = 2;
    public static final int STREAM_SYSTEM = 1;
    @UnsupportedAppUsage
    public static final int STREAM_SYSTEM_ENFORCED = 7;
    @UnsupportedAppUsage
    public static final int STREAM_TTS = 9;
    public static final int STREAM_VOICE_CALL = 0;
    @SystemApi
    public static final int SUCCESS = 0;
    private static final String TAG = "AudioManager";
    public static final int USE_DEFAULT_STREAM_TYPE = Integer.MIN_VALUE;
    public static final String VIBRATE_SETTING_CHANGED_ACTION = "android.media.VIBRATE_SETTING_CHANGED";
    public static final int VIBRATE_SETTING_OFF = 0;
    public static final int VIBRATE_SETTING_ON = 1;
    public static final int VIBRATE_SETTING_ONLY_SILENT = 2;
    public static final int VIBRATE_TYPE_NOTIFICATION = 1;
    public static final int VIBRATE_TYPE_RINGER = 0;
    @UnsupportedAppUsage
    public static final String VOLUME_CHANGED_ACTION = "android.media.VOLUME_CHANGED_ACTION";
    private static final float VOLUME_MIN_DB = -758.0f;
    private static final AudioVolumeGroupChangeHandler sAudioAudioVolumeGroupChangedHandler;
    static ArrayList<AudioPatch> sAudioPatchesCached;
    private static final AudioPortEventHandler sAudioPortEventHandler;
    static Integer sAudioPortGeneration;
    static ArrayList<AudioPort> sAudioPortsCached;
    static ArrayList<AudioPort> sPreviousAudioPortsCached;
    private static IAudioService sService;
    private Context mApplicationContext;
    private final IAudioFocusDispatcher mAudioFocusDispatcher = new IAudioFocusDispatcher.Stub(){

        @Override
        public void dispatchAudioFocusChange(int n, String string2) {
            Object object = AudioManager.this.findFocusRequestInfo(string2);
            if (object != null && ((FocusRequestInfo)object).mRequest.getOnAudioFocusChangeListener() != null) {
                object = ((FocusRequestInfo)object).mHandler == null ? AudioManager.this.mServiceEventHandlerDelegate.getHandler() : ((FocusRequestInfo)object).mHandler;
                ((Handler)object).sendMessage(((Handler)object).obtainMessage(0, n, 0, string2));
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void dispatchFocusResultFromExtPolicy(int n, String object) {
            Object object2 = AudioManager.this.mFocusRequestsLock;
            synchronized (object2) {
                object = (BlockingFocusResultReceiver)AudioManager.this.mFocusRequestsAwaitingResult.remove(object);
                if (object != null) {
                    ((BlockingFocusResultReceiver)object).notifyResult(n);
                } else {
                    Log.e(AudioManager.TAG, "dispatchFocusResultFromExtPolicy found no result receiver");
                }
                return;
            }
        }
    };
    @UnsupportedAppUsage
    private final ConcurrentHashMap<String, FocusRequestInfo> mAudioFocusIdListenerMap = new ConcurrentHashMap();
    private AudioServerStateCallback mAudioServerStateCb;
    private final Object mAudioServerStateCbLock = new Object();
    private final IAudioServerStateDispatcher mAudioServerStateDispatcher = new IAudioServerStateDispatcher.Stub(){

        static /* synthetic */ void lambda$dispatchAudioServerStateChange$0(AudioServerStateCallback audioServerStateCallback) {
            audioServerStateCallback.onAudioServerUp();
        }

        static /* synthetic */ void lambda$dispatchAudioServerStateChange$1(AudioServerStateCallback audioServerStateCallback) {
            audioServerStateCallback.onAudioServerDown();
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void dispatchAudioServerStateChange(boolean bl) {
            AudioServerStateCallback audioServerStateCallback;
            Executor executor;
            Object object = AudioManager.this.mAudioServerStateCbLock;
            synchronized (object) {
                executor = AudioManager.this.mAudioServerStateExec;
                audioServerStateCallback = AudioManager.this.mAudioServerStateCb;
            }
            if (executor == null) return;
            if (audioServerStateCallback == null) {
                return;
            }
            if (bl) {
                executor.execute(new _$$Lambda$AudioManager$4$Q85LmhgKDCoq1YI14giFabZrM7A(audioServerStateCallback));
                return;
            }
            executor.execute(new _$$Lambda$AudioManager$4$7k7uSoMGULBCueASQSmf9jAil7I(audioServerStateCallback));
        }
    };
    private Executor mAudioServerStateExec;
    private int mCapturePolicy = 1;
    private final ArrayMap<AudioDeviceCallback, NativeEventHandlerDelegate> mDeviceCallbacks = new ArrayMap();
    @GuardedBy(value={"mFocusRequestsLock"})
    private HashMap<String, BlockingFocusResultReceiver> mFocusRequestsAwaitingResult;
    private final Object mFocusRequestsLock = new Object();
    private final IBinder mICallBack = new Binder();
    private Context mOriginalContext;
    private final IPlaybackConfigDispatcher mPlayCb = new IPlaybackConfigDispatcher.Stub(){

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void dispatchPlaybackConfigChange(List<AudioPlaybackConfiguration> list, boolean bl) {
            if (bl) {
                Binder.flushPendingCommands();
            }
            Object object = AudioManager.this.mPlaybackCallbackLock;
            synchronized (object) {
                if (AudioManager.this.mPlaybackCallbackList != null) {
                    for (int i = 0; i < AudioManager.this.mPlaybackCallbackList.size(); ++i) {
                        AudioPlaybackCallbackInfo audioPlaybackCallbackInfo = (AudioPlaybackCallbackInfo)AudioManager.this.mPlaybackCallbackList.get(i);
                        if (audioPlaybackCallbackInfo.mHandler == null) continue;
                        Handler handler = audioPlaybackCallbackInfo.mHandler;
                        Object object2 = new PlaybackConfigChangeCallbackData(audioPlaybackCallbackInfo.mCb, list);
                        object2 = handler.obtainMessage(2, object2);
                        audioPlaybackCallbackInfo.mHandler.sendMessage((Message)object2);
                    }
                }
                return;
            }
        }
    };
    private List<AudioPlaybackCallbackInfo> mPlaybackCallbackList;
    private final Object mPlaybackCallbackLock = new Object();
    private OnAmPortUpdateListener mPortListener = null;
    private ArrayList<AudioDevicePort> mPreviousPorts = new ArrayList();
    private final IRecordingConfigDispatcher mRecCb = new IRecordingConfigDispatcher.Stub(){

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void dispatchRecordingConfigChange(List<AudioRecordingConfiguration> list) {
            Object object = AudioManager.this.mRecordCallbackLock;
            synchronized (object) {
                if (AudioManager.this.mRecordCallbackList != null) {
                    for (int i = 0; i < AudioManager.this.mRecordCallbackList.size(); ++i) {
                        AudioRecordingCallbackInfo audioRecordingCallbackInfo = (AudioRecordingCallbackInfo)AudioManager.this.mRecordCallbackList.get(i);
                        if (audioRecordingCallbackInfo.mHandler == null) continue;
                        Object object2 = audioRecordingCallbackInfo.mHandler;
                        RecordConfigChangeCallbackData recordConfigChangeCallbackData = new RecordConfigChangeCallbackData(audioRecordingCallbackInfo.mCb, list);
                        object2 = ((Handler)object2).obtainMessage(1, recordConfigChangeCallbackData);
                        audioRecordingCallbackInfo.mHandler.sendMessage((Message)object2);
                    }
                }
                return;
            }
        }
    };
    private List<AudioRecordingCallbackInfo> mRecordCallbackList;
    private final Object mRecordCallbackLock = new Object();
    private final ServiceEventHandlerDelegate mServiceEventHandlerDelegate = new ServiceEventHandlerDelegate(null);
    private final boolean mUseFixedVolume;
    private final boolean mUseVolumeKeySounds;
    private long mVolumeKeyUpTime;

    static {
        sAudioPortEventHandler = new AudioPortEventHandler();
        sAudioAudioVolumeGroupChangedHandler = new AudioVolumeGroupChangeHandler();
        FLAG_NAMES = new TreeMap();
        FLAG_NAMES.put(1, "FLAG_SHOW_UI");
        FLAG_NAMES.put(2, "FLAG_ALLOW_RINGER_MODES");
        FLAG_NAMES.put(4, "FLAG_PLAY_SOUND");
        FLAG_NAMES.put(8, "FLAG_REMOVE_SOUND_AND_VIBRATE");
        FLAG_NAMES.put(16, "FLAG_VIBRATE");
        FLAG_NAMES.put(32, "FLAG_FIXED_VOLUME");
        FLAG_NAMES.put(64, "FLAG_BLUETOOTH_ABS_VOLUME");
        FLAG_NAMES.put(128, "FLAG_SHOW_SILENT_HINT");
        FLAG_NAMES.put(256, "FLAG_HDMI_SYSTEM_AUDIO_VOLUME");
        FLAG_NAMES.put(512, "FLAG_ACTIVE_MEDIA_ONLY");
        FLAG_NAMES.put(1024, "FLAG_SHOW_UI_WARNINGS");
        FLAG_NAMES.put(2048, "FLAG_SHOW_VIBRATE_HINT");
        FLAG_NAMES.put(4096, "FLAG_FROM_KEY");
        sAudioPortGeneration = new Integer(0);
        sAudioPortsCached = new ArrayList();
        sPreviousAudioPortsCached = new ArrayList();
        sAudioPatchesCached = new ArrayList();
    }

    @UnsupportedAppUsage
    public AudioManager() {
        this.mUseVolumeKeySounds = true;
        this.mUseFixedVolume = false;
    }

    @UnsupportedAppUsage
    public AudioManager(Context context) {
        this.setContext(context);
        this.mUseVolumeKeySounds = this.getContext().getResources().getBoolean(17891563);
        this.mUseFixedVolume = this.getContext().getResources().getBoolean(17891558);
    }

    private void addMicrophonesFromAudioDeviceInfo(ArrayList<MicrophoneInfo> arrayList, HashSet<Integer> hashSet) {
        for (AudioDeviceInfo audioDeviceInfo : AudioManager.getDevicesStatic(1)) {
            if (hashSet.contains(audioDeviceInfo.getType())) continue;
            arrayList.add(AudioManager.microphoneInfoFromAudioDeviceInfo(audioDeviceInfo));
        }
    }

    public static final String adjustToString(int n) {
        if (n != -100) {
            if (n != -1) {
                if (n != 0) {
                    if (n != 1) {
                        if (n != 100) {
                            if (n != 101) {
                                StringBuilder stringBuilder = new StringBuilder("unknown adjust mode ");
                                stringBuilder.append(n);
                                return stringBuilder.toString();
                            }
                            return "ADJUST_TOGGLE_MUTE";
                        }
                        return "ADJUST_UNMUTE";
                    }
                    return "ADJUST_RAISE";
                }
                return "ADJUST_SAME";
            }
            return "ADJUST_LOWER";
        }
        return "ADJUST_MUTE";
    }

    private void broadcastDeviceListChange_sync(Handler arraudioDeviceInfo) {
        ArrayList<AudioDevicePort> arrayList = new ArrayList<AudioDevicePort>();
        if (AudioManager.listAudioDevicePorts(arrayList) != 0) {
            return;
        }
        if (arraudioDeviceInfo != null) {
            arraudioDeviceInfo.sendMessage(Message.obtain((Handler)arraudioDeviceInfo, 0, AudioManager.infoListFromPortList(arrayList, 3)));
        } else {
            AudioDeviceInfo[] arraudioDeviceInfo2 = AudioManager.calcListDeltas(this.mPreviousPorts, arrayList, 3);
            arraudioDeviceInfo = AudioManager.calcListDeltas(arrayList, this.mPreviousPorts, 3);
            if (arraudioDeviceInfo2.length != 0 || arraudioDeviceInfo.length != 0) {
                for (int i = 0; i < this.mDeviceCallbacks.size(); ++i) {
                    Handler handler = this.mDeviceCallbacks.valueAt(i).getHandler();
                    if (handler == null) continue;
                    if (arraudioDeviceInfo.length != 0) {
                        handler.sendMessage(Message.obtain(handler, 2, arraudioDeviceInfo));
                    }
                    if (arraudioDeviceInfo2.length == 0) continue;
                    handler.sendMessage(Message.obtain(handler, 1, arraudioDeviceInfo2));
                }
            }
        }
        this.mPreviousPorts = arrayList;
    }

    private static AudioDeviceInfo[] calcListDeltas(ArrayList<AudioDevicePort> arrayList, ArrayList<AudioDevicePort> arrayList2, int n) {
        ArrayList<AudioDevicePort> arrayList3 = new ArrayList<AudioDevicePort>();
        for (int i = 0; i < arrayList2.size(); ++i) {
            boolean bl = false;
            AudioDevicePort audioDevicePort = arrayList2.get(i);
            for (int j = 0; j < arrayList.size() && !bl; ++j) {
                bl = audioDevicePort.id() == arrayList.get(j).id();
            }
            if (bl) continue;
            arrayList3.add(audioDevicePort);
        }
        return AudioManager.infoListFromPortList(arrayList3, n);
    }

    private static boolean checkFlags(AudioDevicePort audioDevicePort, int n) {
        int n2 = audioDevicePort.role();
        boolean bl = true;
        if (!(n2 == 2 && (n & 2) != 0 || audioDevicePort.role() == 1 && (n & 1) != 0)) {
            bl = false;
        }
        return bl;
    }

    private static boolean checkTypes(AudioDevicePort audioDevicePort) {
        boolean bl = AudioDeviceInfo.convertInternalDeviceToDeviceType(audioDevicePort.type()) != 0;
        return bl;
    }

    @UnsupportedAppUsage
    public static int createAudioPatch(AudioPatch[] arraudioPatch, AudioPortConfig[] arraudioPortConfig, AudioPortConfig[] arraudioPortConfig2) {
        return AudioSystem.createAudioPatch(arraudioPatch, arraudioPortConfig, arraudioPortConfig2);
    }

    private static void filterDevicePorts(ArrayList<AudioPort> arrayList, ArrayList<AudioDevicePort> arrayList2) {
        arrayList2.clear();
        for (int i = 0; i < arrayList.size(); ++i) {
            if (!(arrayList.get(i) instanceof AudioDevicePort)) continue;
            arrayList2.add((AudioDevicePort)arrayList.get(i));
        }
    }

    private FocusRequestInfo findFocusRequestInfo(String string2) {
        return this.mAudioFocusIdListenerMap.get(string2);
    }

    public static String flagsToString(int n) {
        StringBuilder stringBuilder = new StringBuilder();
        for (Map.Entry<Integer, String> entry : FLAG_NAMES.entrySet()) {
            int n2 = entry.getKey();
            int n3 = n;
            if ((n & n2) != 0) {
                if (stringBuilder.length() > 0) {
                    stringBuilder.append(',');
                }
                stringBuilder.append(entry.getValue());
                n3 = n & n2;
            }
            n = n3;
        }
        if (n != 0) {
            if (stringBuilder.length() > 0) {
                stringBuilder.append(',');
            }
            stringBuilder.append(n);
        }
        return stringBuilder.toString();
    }

    @SystemApi
    public static List<AudioProductStrategy> getAudioProductStrategies() {
        Object object = AudioManager.getService();
        try {
            object = object.getAudioProductStrategies();
            return object;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public static List<AudioVolumeGroup> getAudioVolumeGroups() {
        Object object = AudioManager.getService();
        try {
            object = object.getAudioVolumeGroups();
            return object;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    private Context getContext() {
        Context context;
        if (this.mApplicationContext == null) {
            this.setContext(this.mOriginalContext);
        }
        if ((context = this.mApplicationContext) != null) {
            return context;
        }
        return this.mOriginalContext;
    }

    public static AudioDeviceInfo[] getDevicesStatic(int n) {
        ArrayList<AudioDevicePort> arrayList = new ArrayList<AudioDevicePort>();
        if (AudioManager.listAudioDevicePorts(arrayList) != 0) {
            return new AudioDeviceInfo[0];
        }
        return AudioManager.infoListFromPortList(arrayList, n);
    }

    private String getIdForAudioFocusListener(OnAudioFocusChangeListener onAudioFocusChangeListener) {
        if (onAudioFocusChangeListener == null) {
            return new String(this.toString());
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.toString());
        stringBuilder.append(onAudioFocusChangeListener.toString());
        return new String(stringBuilder.toString());
    }

    @UnsupportedAppUsage
    private static IAudioService getService() {
        IAudioService iAudioService = sService;
        if (iAudioService != null) {
            return iAudioService;
        }
        sService = IAudioService.Stub.asInterface(ServiceManager.getService("audio"));
        return sService;
    }

    public static boolean hasHapticChannels(Uri uri) {
        try {
            boolean bl = AudioManager.getService().hasHapticChannels(uri);
            return bl;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    private boolean hasPlaybackCallback_sync(AudioPlaybackCallback audioPlaybackCallback) {
        if (this.mPlaybackCallbackList != null) {
            for (int i = 0; i < this.mPlaybackCallbackList.size(); ++i) {
                if (!audioPlaybackCallback.equals(this.mPlaybackCallbackList.get((int)i).mCb)) continue;
                return true;
            }
        }
        return false;
    }

    private boolean hasRecordCallback_sync(AudioRecordingCallback audioRecordingCallback) {
        if (this.mRecordCallbackList != null) {
            for (int i = 0; i < this.mRecordCallbackList.size(); ++i) {
                if (!audioRecordingCallback.equals(this.mRecordCallbackList.get((int)i).mCb)) continue;
                return true;
            }
        }
        return false;
    }

    private static AudioDeviceInfo[] infoListFromPortList(ArrayList<AudioDevicePort> object, int n) {
        AudioDeviceInfo[] arraudioDeviceInfo;
        int n2;
        int n3 = 0;
        Iterator<AudioDevicePort> iterator = ((ArrayList)object).iterator();
        while (iterator.hasNext()) {
            arraudioDeviceInfo = iterator.next();
            n2 = n3;
            if (AudioManager.checkTypes((AudioDevicePort)arraudioDeviceInfo)) {
                n2 = n3;
                if (AudioManager.checkFlags((AudioDevicePort)arraudioDeviceInfo, n)) {
                    n2 = n3 + 1;
                }
            }
            n3 = n2;
        }
        arraudioDeviceInfo = new AudioDeviceInfo[n3];
        n2 = 0;
        iterator = ((ArrayList)object).iterator();
        while (iterator.hasNext()) {
            object = iterator.next();
            n3 = n2;
            if (AudioManager.checkTypes((AudioDevicePort)object)) {
                n3 = n2;
                if (AudioManager.checkFlags((AudioDevicePort)object, n)) {
                    arraudioDeviceInfo[n2] = new AudioDeviceInfo((AudioDevicePort)object);
                    n3 = n2 + 1;
                }
            }
            n2 = n3;
        }
        return arraudioDeviceInfo;
    }

    public static boolean isHapticPlaybackSupported() {
        return AudioSystem.isHapticPlaybackSupported();
    }

    public static boolean isInputDevice(int n) {
        boolean bl = (n & Integer.MIN_VALUE) == Integer.MIN_VALUE;
        return bl;
    }

    public static boolean isOffloadedPlaybackSupported(AudioFormat audioFormat, AudioAttributes audioAttributes) {
        if (audioFormat != null) {
            if (audioAttributes != null) {
                return AudioSystem.isOffloadSupported(audioFormat, audioAttributes);
            }
            throw new NullPointerException("Illegal null AudioAttributes");
        }
        throw new NullPointerException("Illegal null AudioFormat");
    }

    public static boolean isOutputDevice(int n) {
        boolean bl = (Integer.MIN_VALUE & n) == 0;
        return bl;
    }

    private static boolean isPublicStreamType(int n) {
        return n == 0 || n == 1 || n == 2 || n == 3 || n == 4 || n == 5 || n == 8 || n == 10;
    }

    @UnsupportedAppUsage
    public static boolean isValidRingerMode(int n) {
        if (n >= 0 && n <= 2) {
            IAudioService iAudioService = AudioManager.getService();
            try {
                boolean bl = iAudioService.isValidRingerMode(n);
                return bl;
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
        return false;
    }

    public static int listAudioDevicePorts(ArrayList<AudioDevicePort> arrayList) {
        if (arrayList == null) {
            return -2;
        }
        ArrayList<AudioPort> arrayList2 = new ArrayList<AudioPort>();
        int n = AudioManager.updateAudioPortCache(arrayList2, null, null);
        if (n == 0) {
            AudioManager.filterDevicePorts(arrayList2, arrayList);
        }
        return n;
    }

    @UnsupportedAppUsage
    public static int listAudioPatches(ArrayList<AudioPatch> arrayList) {
        return AudioManager.updateAudioPortCache(null, arrayList, null);
    }

    @UnsupportedAppUsage
    public static int listAudioPorts(ArrayList<AudioPort> arrayList) {
        return AudioManager.updateAudioPortCache(arrayList, null, null);
    }

    public static int listPreviousAudioDevicePorts(ArrayList<AudioDevicePort> arrayList) {
        if (arrayList == null) {
            return -2;
        }
        ArrayList<AudioPort> arrayList2 = new ArrayList<AudioPort>();
        int n = AudioManager.updateAudioPortCache(null, null, arrayList2);
        if (n == 0) {
            AudioManager.filterDevicePorts(arrayList2, arrayList);
        }
        return n;
    }

    public static int listPreviousAudioPorts(ArrayList<AudioPort> arrayList) {
        return AudioManager.updateAudioPortCache(null, null, arrayList);
    }

    public static MicrophoneInfo microphoneInfoFromAudioDeviceInfo(AudioDeviceInfo audioDeviceInfo) {
        int n = audioDeviceInfo.getType();
        n = n != 15 && n != 18 ? (n == 0 ? 0 : 3) : 1;
        Object object = new StringBuilder();
        ((StringBuilder)object).append(audioDeviceInfo.getPort().name());
        ((StringBuilder)object).append(audioDeviceInfo.getId());
        object = new MicrophoneInfo(((StringBuilder)object).toString(), audioDeviceInfo.getPort().type(), audioDeviceInfo.getAddress(), n, -1, -1, MicrophoneInfo.POSITION_UNKNOWN, MicrophoneInfo.ORIENTATION_UNKNOWN, new ArrayList<Pair<Float, Float>>(), new ArrayList<Pair<Integer, Integer>>(), -3.4028235E38f, -3.4028235E38f, -3.4028235E38f, 0);
        ((MicrophoneInfo)object).setId(audioDeviceInfo.getId());
        return object;
    }

    private boolean querySoundEffectsEnabled(int n) {
        ContentResolver contentResolver = this.getContext().getContentResolver();
        boolean bl = false;
        if (Settings.System.getIntForUser(contentResolver, "sound_effects_enabled", 0, n) != 0) {
            bl = true;
        }
        return bl;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    static int registerAudioPolicyStatic(AudioPolicy audioPolicy) {
        if (audioPolicy == null) {
            throw new IllegalArgumentException("Illegal null AudioPolicy argument");
        }
        IAudioService iAudioService = AudioManager.getService();
        try {
            Object object = audioPolicy.getMediaProjection();
            AudioPolicyConfig audioPolicyConfig = audioPolicy.getConfig();
            IAudioPolicyCallback iAudioPolicyCallback = audioPolicy.cb();
            boolean bl = audioPolicy.hasFocusListener();
            boolean bl2 = audioPolicy.isFocusPolicy();
            boolean bl3 = audioPolicy.isTestFocusPolicy();
            boolean bl4 = audioPolicy.isVolumeController();
            object = object == null ? null : ((MediaProjection)object).getProjection();
            object = iAudioService.registerAudioPolicy(audioPolicyConfig, iAudioPolicyCallback, bl, bl2, bl3, bl4, (IMediaProjection)object);
            if (object == null) {
                return -1;
            }
            audioPolicy.setRegistration((String)object);
            return 0;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @UnsupportedAppUsage
    public static int releaseAudioPatch(AudioPatch audioPatch) {
        return AudioSystem.releaseAudioPatch(audioPatch);
    }

    private boolean removePlaybackCallback_sync(AudioPlaybackCallback audioPlaybackCallback) {
        if (this.mPlaybackCallbackList != null) {
            for (int i = 0; i < this.mPlaybackCallbackList.size(); ++i) {
                if (!audioPlaybackCallback.equals(this.mPlaybackCallbackList.get((int)i).mCb)) continue;
                this.mPlaybackCallbackList.remove(i);
                return true;
            }
        }
        return false;
    }

    private boolean removeRecordCallback_sync(AudioRecordingCallback audioRecordingCallback) {
        if (this.mRecordCallbackList != null) {
            for (int i = 0; i < this.mRecordCallbackList.size(); ++i) {
                if (!audioRecordingCallback.equals(this.mRecordCallbackList.get((int)i).mCb)) continue;
                this.mRecordCallbackList.remove(i);
                return true;
            }
        }
        return false;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    static int resetAudioPortGeneration() {
        Integer n = sAudioPortGeneration;
        synchronized (n) {
            int n2 = sAudioPortGeneration;
            sAudioPortGeneration = 0;
            return n2;
        }
    }

    public static int setAudioPortGain(AudioPort object, AudioGainConfig audioGainConfig) {
        if (object != null && audioGainConfig != null) {
            AudioPortConfig audioPortConfig = ((AudioPort)object).activeConfig();
            object = new AudioPortConfig((AudioPort)object, audioPortConfig.samplingRate(), audioPortConfig.channelMask(), audioPortConfig.format(), audioGainConfig);
            ((AudioPortConfig)object).mConfigMask = 8;
            return AudioSystem.setAudioPortConfig((AudioPortConfig)object);
        }
        return -2;
    }

    private void setContext(Context context) {
        this.mApplicationContext = context.getApplicationContext();
        this.mOriginalContext = this.mApplicationContext != null ? null : context;
    }

    public static void setPortIdForMicrophones(ArrayList<MicrophoneInfo> arrayList) {
        AudioDeviceInfo[] arraudioDeviceInfo = AudioManager.getDevicesStatic(1);
        for (int i = arrayList.size() - 1; i >= 0; --i) {
            Object object;
            boolean bl;
            boolean bl2 = false;
            int n = arraudioDeviceInfo.length;
            int n2 = 0;
            do {
                bl = bl2;
                if (n2 >= n) break;
                object = arraudioDeviceInfo[n2];
                if (((AudioDeviceInfo)object).getPort().type() == arrayList.get(i).getInternalDeviceType() && TextUtils.equals(((AudioDeviceInfo)object).getAddress(), arrayList.get(i).getAddress())) {
                    arrayList.get(i).setId(((AudioDeviceInfo)object).getId());
                    bl = true;
                    break;
                }
                ++n2;
            } while (true);
            if (bl) continue;
            object = new StringBuilder();
            ((StringBuilder)object).append("Failed to find port id for device with type:");
            ((StringBuilder)object).append(arrayList.get(i).getType());
            ((StringBuilder)object).append(" address:");
            ((StringBuilder)object).append(arrayList.get(i).getAddress());
            Log.i(TAG, ((StringBuilder)object).toString());
            arrayList.remove(i);
        }
    }

    static void unregisterAudioPolicyAsyncStatic(AudioPolicy audioPolicy) {
        if (audioPolicy != null) {
            IAudioService iAudioService = AudioManager.getService();
            try {
                iAudioService.unregisterAudioPolicyAsync(audioPolicy.cb());
                audioPolicy.setRegistration(null);
                return;
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
        throw new IllegalArgumentException("Illegal null AudioPolicy argument");
    }

    /*
     * WARNING - combined exceptions agressively - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    static int updateAudioPortCache(ArrayList<AudioPort> arrayList, ArrayList<AudioPatch> arrayList2, ArrayList<AudioPort> arrayList3) {
        sAudioPortEventHandler.init();
        Integer n = sAudioPortGeneration;
        synchronized (n) {
            if (sAudioPortGeneration == 0) {
                int n2;
                int n3;
                Object object = new int[1];
                int[] arrn = new int[1];
                ArrayList<AudioPort> arrayList4 = new ArrayList<AudioPort>();
                ArrayList<AudioPatch> arrayList5 = new ArrayList<AudioPatch>();
                do {
                    arrayList4.clear();
                    n3 = AudioSystem.listAudioPorts(arrayList4, arrn);
                    if (n3 != 0) {
                        Log.w(TAG, "updateAudioPortCache: listAudioPorts failed");
                        return n3;
                    }
                    arrayList5.clear();
                    n3 = AudioSystem.listAudioPatches(arrayList5, (int[])object);
                    if (n3 == 0) continue;
                    Log.w(TAG, "updateAudioPortCache: listAudioPatches failed");
                    return n3;
                } while (object[0] != arrn[0] && (arrayList == null || arrayList2 == null));
                if (object[0] != arrn[0]) {
                    return -1;
                }
                for (n3 = 0; n3 < arrayList5.size(); ++n3) {
                    for (n2 = 0; n2 < arrayList5.get(n3).sources().length; ++n2) {
                        arrayList5.get((int)n3).sources()[n2] = object = AudioManager.updatePortConfig(arrayList5.get(n3).sources()[n2], arrayList4);
                    }
                    for (n2 = 0; n2 < arrayList5.get(n3).sinks().length; ++n2) {
                        arrayList5.get((int)n3).sinks()[n2] = object = AudioManager.updatePortConfig(arrayList5.get(n3).sinks()[n2], arrayList4);
                    }
                }
                object = arrayList5.iterator();
                do {
                    if (!object.hasNext()) {
                        sPreviousAudioPortsCached = sAudioPortsCached;
                        sAudioPortsCached = arrayList4;
                        sAudioPatchesCached = arrayList5;
                        sAudioPortGeneration = arrn[0];
                        break;
                    }
                    AudioPatch audioPatch = (AudioPatch)object.next();
                    int n4 = 0;
                    AudioPortConfig[] arraudioPortConfig = audioPatch.sources();
                    int n5 = arraudioPortConfig.length;
                    n2 = 0;
                    do {
                        n3 = n4;
                        if (n2 >= n5) break;
                        if (arraudioPortConfig[n2] == null) {
                            n3 = 1;
                            break;
                        }
                        ++n2;
                    } while (true);
                    arraudioPortConfig = audioPatch.sinks();
                    n5 = arraudioPortConfig.length;
                    n4 = 0;
                    do {
                        n2 = n3;
                        if (n4 >= n5) break;
                        if (arraudioPortConfig[n4] == null) {
                            n2 = 1;
                            break;
                        }
                        ++n4;
                    } while (true);
                    if (n2 == 0) continue;
                    object.remove();
                } while (true);
            }
            if (arrayList != null) {
                arrayList.clear();
                arrayList.addAll(sAudioPortsCached);
            }
            if (arrayList2 != null) {
                arrayList2.clear();
                arrayList2.addAll(sAudioPatchesCached);
            }
            if (arrayList3 != null) {
                arrayList3.clear();
                arrayList3.addAll(sPreviousAudioPortsCached);
            }
            return 0;
        }
    }

    static AudioPortConfig updatePortConfig(AudioPortConfig object, ArrayList<AudioPort> object2) {
        AudioPort audioPort;
        Object object3 = ((AudioPortConfig)object).port();
        int n = 0;
        do {
            audioPort = object3;
            if (n >= ((ArrayList)object2).size()) break;
            if (((ArrayList)object2).get(n).handle().equals(((AudioPort)object3).handle())) {
                audioPort = (AudioPort)((ArrayList)object2).get(n);
                break;
            }
            ++n;
        } while (true);
        if (n == ((ArrayList)object2).size()) {
            object = new StringBuilder();
            ((StringBuilder)object).append("updatePortConfig port not found for handle: ");
            ((StringBuilder)object).append(audioPort.handle().id());
            Log.e(TAG, ((StringBuilder)object).toString());
            return null;
        }
        object2 = object3 = ((AudioPortConfig)object).gain();
        if (object3 != null) {
            object2 = audioPort.gain(((AudioGainConfig)object3).index()).buildConfig(((AudioGainConfig)object3).mode(), ((AudioGainConfig)object3).channelMask(), ((AudioGainConfig)object3).values(), ((AudioGainConfig)object3).rampDurationMs());
        }
        return audioPort.buildConfig(((AudioPortConfig)object).samplingRate(), ((AudioPortConfig)object).channelMask(), ((AudioPortConfig)object).format(), (AudioGainConfig)object2);
    }

    public int abandonAudioFocus(OnAudioFocusChangeListener onAudioFocusChangeListener) {
        return this.abandonAudioFocus(onAudioFocusChangeListener, null);
    }

    @SystemApi
    @SuppressLint(value={"Doclava125"})
    public int abandonAudioFocus(OnAudioFocusChangeListener onAudioFocusChangeListener, AudioAttributes audioAttributes) {
        this.unregisterAudioFocusRequest(onAudioFocusChangeListener);
        IAudioService iAudioService = AudioManager.getService();
        try {
            int n = iAudioService.abandonAudioFocus(this.mAudioFocusDispatcher, this.getIdForAudioFocusListener(onAudioFocusChangeListener), audioAttributes, this.getContext().getOpPackageName());
            return n;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @UnsupportedAppUsage
    public void abandonAudioFocusForCall() {
        IAudioService iAudioService = AudioManager.getService();
        try {
            iAudioService.abandonAudioFocus(null, "AudioFocus_For_Phone_Ring_And_Calls", null, this.getContext().getOpPackageName());
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public int abandonAudioFocusRequest(AudioFocusRequest audioFocusRequest) {
        if (audioFocusRequest != null) {
            return this.abandonAudioFocus(audioFocusRequest.getOnAudioFocusChangeListener(), audioFocusRequest.getAudioAttributes());
        }
        throw new IllegalArgumentException("Illegal null AudioFocusRequest");
    }

    public void adjustStreamVolume(int n, int n2, int n3) {
        IAudioService iAudioService = AudioManager.getService();
        try {
            iAudioService.adjustStreamVolume(n, n2, n3, this.getContext().getOpPackageName());
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public void adjustSuggestedStreamVolume(int n, int n2, int n3) {
        MediaSessionLegacyHelper.getHelper(this.getContext()).sendAdjustVolumeBy(n2, n, n3);
    }

    public void adjustVolume(int n, int n2) {
        MediaSessionLegacyHelper.getHelper(this.getContext()).sendAdjustVolumeBy(Integer.MIN_VALUE, n, n2);
    }

    public void avrcpSupportsAbsoluteVolume(String string2, boolean bl) {
        IAudioService iAudioService = AudioManager.getService();
        try {
            iAudioService.avrcpSupportsAbsoluteVolume(string2, bl);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @SystemApi
    public void clearAudioServerStateCallback() {
        Object object = this.mAudioServerStateCbLock;
        synchronized (object) {
            if (this.mAudioServerStateCb != null) {
                IAudioService iAudioService = AudioManager.getService();
                try {
                    iAudioService.unregisterAudioServerStateDispatcher(this.mAudioServerStateDispatcher);
                }
                catch (RemoteException remoteException) {
                    throw remoteException.rethrowFromSystemServer();
                }
            }
            this.mAudioServerStateExec = null;
            this.mAudioServerStateCb = null;
            return;
        }
    }

    public void disableSafeMediaVolume() {
        try {
            AudioManager.getService().disableSafeMediaVolume(this.mApplicationContext.getOpPackageName());
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public int dispatchAudioFocusChange(AudioFocusInfo audioFocusInfo, int n, AudioPolicy audioPolicy) {
        if (audioFocusInfo != null) {
            if (audioPolicy != null) {
                IAudioService iAudioService = AudioManager.getService();
                try {
                    n = iAudioService.dispatchFocusChange(audioFocusInfo, n, audioPolicy.cb());
                    return n;
                }
                catch (RemoteException remoteException) {
                    throw remoteException.rethrowFromSystemServer();
                }
            }
            throw new NullPointerException("Illegal null AudioPolicy");
        }
        throw new NullPointerException("Illegal null AudioFocusInfo");
    }

    public void dispatchMediaKeyEvent(KeyEvent keyEvent) {
        MediaSessionLegacyHelper.getHelper(this.getContext()).sendMediaButtonEvent(keyEvent, false);
    }

    @UnsupportedAppUsage
    public void forceVolumeControlStream(int n) {
        IAudioService iAudioService = AudioManager.getService();
        try {
            iAudioService.forceVolumeControlStream(n, this.mICallBack);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public int generateAudioSessionId() {
        int n = AudioSystem.newAudioSessionId();
        if (n > 0) {
            return n;
        }
        Log.e(TAG, "Failure to generate a new audio session ID");
        return -1;
    }

    public List<AudioPlaybackConfiguration> getActivePlaybackConfigurations() {
        Object object = AudioManager.getService();
        try {
            object = object.getActivePlaybackConfigurations();
            return object;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public List<AudioRecordingConfiguration> getActiveRecordingConfigurations() {
        Object object = AudioManager.getService();
        try {
            object = object.getActiveRecordingConfigurations();
            return object;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public int getAllowedCapturePolicy() {
        return this.mCapturePolicy;
    }

    public AudioDeviceInfo[] getDevices(int n) {
        return AudioManager.getDevicesStatic(n);
    }

    @UnsupportedAppUsage
    public int getDevicesForStream(int n) {
        if (n != 0 && n != 1 && n != 2 && n != 3 && n != 4 && n != 5 && n != 8 && n != 10) {
            return 0;
        }
        return AudioSystem.getDevicesForStream(n);
    }

    public int getFocusRampTimeMs(int n, AudioAttributes audioAttributes) {
        IAudioService iAudioService = AudioManager.getService();
        try {
            n = iAudioService.getFocusRampTimeMs(n, audioAttributes);
            return n;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public List<BluetoothCodecConfig> getHwOffloadEncodingFormatsSupportedForA2DP() {
        Object object = new ArrayList<Integer>();
        ArrayList<BluetoothCodecConfig> arrayList = new ArrayList<BluetoothCodecConfig>();
        int n = AudioSystem.getHwOffloadEncodingFormatsSupportedForA2DP(object);
        if (n != 0) {
            object = new StringBuilder();
            ((StringBuilder)object).append("getHwOffloadEncodingFormatsSupportedForA2DP failed:");
            ((StringBuilder)object).append(n);
            Log.e(TAG, ((StringBuilder)object).toString());
            return arrayList;
        }
        object = ((ArrayList)object).iterator();
        while (object.hasNext()) {
            n = AudioSystem.audioFormatToBluetoothSourceCodec((Integer)object.next());
            if (n == 1000000) continue;
            arrayList.add(new BluetoothCodecConfig(n));
        }
        return arrayList;
    }

    @UnsupportedAppUsage
    public int getLastAudibleStreamVolume(int n) {
        IAudioService iAudioService = AudioManager.getService();
        try {
            n = iAudioService.getLastAudibleStreamVolume(n);
            return n;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public int getMaxVolumeIndexForAttributes(AudioAttributes audioAttributes) {
        Preconditions.checkNotNull(audioAttributes, "attr must not be null");
        IAudioService iAudioService = AudioManager.getService();
        try {
            int n = iAudioService.getMaxVolumeIndexForAttributes(audioAttributes);
            return n;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public List<MicrophoneInfo> getMicrophones() throws IOException {
        ArrayList<MicrophoneInfo> arrayList = new ArrayList<MicrophoneInfo>();
        int n = AudioSystem.getMicrophones(arrayList);
        HashSet<Integer> hashSet = new HashSet<Integer>();
        hashSet.add(18);
        if (n != 0) {
            if (n != -3) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("getMicrophones failed:");
                stringBuilder.append(n);
                Log.e(TAG, stringBuilder.toString());
            }
            Log.i(TAG, "fallback on device info");
            this.addMicrophonesFromAudioDeviceInfo(arrayList, hashSet);
            return arrayList;
        }
        AudioManager.setPortIdForMicrophones(arrayList);
        hashSet.add(15);
        this.addMicrophonesFromAudioDeviceInfo(arrayList, hashSet);
        return arrayList;
    }

    @SystemApi
    public int getMinVolumeIndexForAttributes(AudioAttributes audioAttributes) {
        Preconditions.checkNotNull(audioAttributes, "attr must not be null");
        IAudioService iAudioService = AudioManager.getService();
        try {
            int n = iAudioService.getMinVolumeIndexForAttributes(audioAttributes);
            return n;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public int getMode() {
        IAudioService iAudioService = AudioManager.getService();
        try {
            int n = iAudioService.getMode();
            return n;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @UnsupportedAppUsage
    public int getOutputLatency(int n) {
        return AudioSystem.getOutputLatency(n);
    }

    public String getParameters(String string2) {
        return AudioSystem.getParameters(string2);
    }

    public String getProperty(String string2) {
        boolean bl = PROPERTY_OUTPUT_SAMPLE_RATE.equals(string2);
        Object var3_3 = null;
        Object var4_4 = null;
        if (bl) {
            int n = AudioSystem.getPrimaryOutputSamplingRate();
            string2 = var4_4;
            if (n > 0) {
                string2 = Integer.toString(n);
            }
            return string2;
        }
        if (PROPERTY_OUTPUT_FRAMES_PER_BUFFER.equals(string2)) {
            int n = AudioSystem.getPrimaryOutputFrameCount();
            string2 = var3_3;
            if (n > 0) {
                string2 = Integer.toString(n);
            }
            return string2;
        }
        if (PROPERTY_SUPPORT_MIC_NEAR_ULTRASOUND.equals(string2)) {
            return String.valueOf(this.getContext().getResources().getBoolean(17891533));
        }
        if (PROPERTY_SUPPORT_SPEAKER_NEAR_ULTRASOUND.equals(string2)) {
            return String.valueOf(this.getContext().getResources().getBoolean(17891535));
        }
        if (PROPERTY_SUPPORT_AUDIO_SOURCE_UNPROCESSED.equals(string2)) {
            return String.valueOf(this.getContext().getResources().getBoolean(17891528));
        }
        return null;
    }

    public Map<Integer, Boolean> getReportedSurroundFormats() {
        Serializable serializable = new HashMap<Integer, Boolean>();
        int n = AudioSystem.getSurroundFormats(serializable, true);
        if (n != 0) {
            serializable = new StringBuilder();
            ((StringBuilder)serializable).append("getReportedSurroundFormats failed:");
            ((StringBuilder)serializable).append(n);
            Log.e(TAG, ((StringBuilder)serializable).toString());
            return new HashMap<Integer, Boolean>();
        }
        return serializable;
    }

    public int getRingerMode() {
        IAudioService iAudioService = AudioManager.getService();
        try {
            int n = iAudioService.getRingerModeExternal();
            return n;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @UnsupportedAppUsage
    public int getRingerModeInternal() {
        try {
            int n = AudioManager.getService().getRingerModeInternal();
            return n;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public IRingtonePlayer getRingtonePlayer() {
        try {
            IRingtonePlayer iRingtonePlayer = AudioManager.getService().getRingtonePlayer();
            return iRingtonePlayer;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @Deprecated
    public int getRouting(int n) {
        return -1;
    }

    public int getStreamMaxVolume(int n) {
        IAudioService iAudioService = AudioManager.getService();
        try {
            n = iAudioService.getStreamMaxVolume(n);
            return n;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public int getStreamMinVolume(int n) {
        if (AudioManager.isPublicStreamType(n)) {
            return this.getStreamMinVolumeInt(n);
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Invalid stream type ");
        stringBuilder.append(n);
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    public int getStreamMinVolumeInt(int n) {
        IAudioService iAudioService = AudioManager.getService();
        try {
            n = iAudioService.getStreamMinVolume(n);
            return n;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public int getStreamVolume(int n) {
        IAudioService iAudioService = AudioManager.getService();
        try {
            n = iAudioService.getStreamVolume(n);
            return n;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public float getStreamVolumeDb(int n, int n2, int n3) {
        if (AudioManager.isPublicStreamType(n)) {
            if (n2 <= this.getStreamMaxVolume(n) && n2 >= this.getStreamMinVolume(n)) {
                if (AudioDeviceInfo.isValidAudioDeviceTypeOut(n3)) {
                    float f = AudioSystem.getStreamVolumeDB(n, n2, AudioDeviceInfo.convertDeviceTypeToInternalDevice(n3));
                    if (f <= -758.0f) {
                        return Float.NEGATIVE_INFINITY;
                    }
                    return f;
                }
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Invalid audio output device type ");
                stringBuilder.append(n3);
                throw new IllegalArgumentException(stringBuilder.toString());
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Invalid stream volume index ");
            stringBuilder.append(n2);
            throw new IllegalArgumentException(stringBuilder.toString());
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Invalid stream type ");
        stringBuilder.append(n);
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    public Map<Integer, Boolean> getSurroundFormats() {
        Serializable serializable = new HashMap<Integer, Boolean>();
        int n = AudioSystem.getSurroundFormats(serializable, false);
        if (n != 0) {
            serializable = new StringBuilder();
            ((StringBuilder)serializable).append("getSurroundFormats failed:");
            ((StringBuilder)serializable).append(n);
            Log.e(TAG, ((StringBuilder)serializable).toString());
            return new HashMap<Integer, Boolean>();
        }
        return serializable;
    }

    public int getUiSoundsStreamType() {
        IAudioService iAudioService = AudioManager.getService();
        try {
            int n = iAudioService.getUiSoundsStreamType();
            return n;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public int getVibrateSetting(int n) {
        IAudioService iAudioService = AudioManager.getService();
        try {
            n = iAudioService.getVibrateSetting(n);
            return n;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public int getVolumeIndexForAttributes(AudioAttributes audioAttributes) {
        Preconditions.checkNotNull(audioAttributes, "attr must not be null");
        IAudioService iAudioService = AudioManager.getService();
        try {
            int n = iAudioService.getVolumeIndexForAttributes(audioAttributes);
            return n;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public void handleBluetoothA2dpDeviceConfigChange(BluetoothDevice bluetoothDevice) {
        IAudioService iAudioService = AudioManager.getService();
        try {
            iAudioService.handleBluetoothA2dpDeviceConfigChange(bluetoothDevice);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public boolean hasRegisteredDynamicPolicy() {
        IAudioService iAudioService = AudioManager.getService();
        try {
            boolean bl = iAudioService.hasRegisteredDynamicPolicy();
            return bl;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public boolean isAudioFocusExclusive() {
        IAudioService iAudioService = AudioManager.getService();
        try {
            int n = iAudioService.getCurrentAudioFocus();
            boolean bl = n == 4;
            return bl;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public boolean isAudioServerRunning() {
        IAudioService iAudioService = AudioManager.getService();
        try {
            boolean bl = iAudioService.isAudioServerRunning();
            return bl;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public boolean isBluetoothA2dpOn() {
        if (AudioSystem.getDeviceConnectionState(128, "") == 1) {
            return true;
        }
        if (AudioSystem.getDeviceConnectionState(256, "") == 1) {
            return true;
        }
        return AudioSystem.getDeviceConnectionState(512, "") == 1;
    }

    public boolean isBluetoothScoAvailableOffCall() {
        return this.getContext().getResources().getBoolean(17891380);
    }

    public boolean isBluetoothScoOn() {
        IAudioService iAudioService = AudioManager.getService();
        try {
            boolean bl = iAudioService.isBluetoothScoOn();
            return bl;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @SystemApi
    @SuppressLint(value={"Doclava125"})
    public boolean isHdmiSystemAudioSupported() {
        try {
            boolean bl = AudioManager.getService().isHdmiSystemAudioSupported();
            return bl;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @UnsupportedAppUsage
    public boolean isMasterMute() {
        IAudioService iAudioService = AudioManager.getService();
        try {
            boolean bl = iAudioService.isMasterMute();
            return bl;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public boolean isMicrophoneMute() {
        return AudioSystem.isMicrophoneMuted();
    }

    public boolean isMusicActive() {
        return AudioSystem.isStreamActive(3, 0);
    }

    @UnsupportedAppUsage
    public boolean isMusicActiveRemotely() {
        return AudioSystem.isStreamActiveRemotely(3, 0);
    }

    @UnsupportedAppUsage
    public boolean isSilentMode() {
        boolean bl;
        int n = this.getRingerMode();
        boolean bl2 = bl = true;
        if (n != 0) {
            bl2 = n == 1 ? bl : false;
        }
        return bl2;
    }

    public boolean isSpeakerphoneOn() {
        IAudioService iAudioService = AudioManager.getService();
        try {
            boolean bl = iAudioService.isSpeakerphoneOn();
            return bl;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public boolean isStreamAffectedByMute(int n) {
        try {
            boolean bl = AudioManager.getService().isStreamAffectedByMute(n);
            return bl;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public boolean isStreamAffectedByRingerMode(int n) {
        try {
            boolean bl = AudioManager.getService().isStreamAffectedByRingerMode(n);
            return bl;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public boolean isStreamMute(int n) {
        IAudioService iAudioService = AudioManager.getService();
        try {
            boolean bl = iAudioService.isStreamMute(n);
            return bl;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public boolean isVolumeFixed() {
        return this.mUseFixedVolume;
    }

    public boolean isWiredHeadsetOn() {
        return AudioSystem.getDeviceConnectionState(4, "") != 0 || AudioSystem.getDeviceConnectionState(8, "") != 0 || AudioSystem.getDeviceConnectionState(67108864, "") != 0;
    }

    public void loadSoundEffects() {
        IAudioService iAudioService = AudioManager.getService();
        try {
            iAudioService.loadSoundEffects();
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public void notifyVolumeControllerVisible(IVolumeController iVolumeController, boolean bl) {
        try {
            AudioManager.getService().notifyVolumeControllerVisible(iVolumeController, bl);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public void playSoundEffect(int n) {
        if (n >= 0 && n < 10) {
            if (!this.querySoundEffectsEnabled(Process.myUserHandle().getIdentifier())) {
                return;
            }
            IAudioService iAudioService = AudioManager.getService();
            try {
                iAudioService.playSoundEffect(n);
                return;
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
    }

    public void playSoundEffect(int n, float f) {
        if (n >= 0 && n < 10) {
            IAudioService iAudioService = AudioManager.getService();
            try {
                iAudioService.playSoundEffectVolume(n, f);
                return;
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
    }

    public void playSoundEffect(int n, int n2) {
        if (n >= 0 && n < 10) {
            if (!this.querySoundEffectsEnabled(n2)) {
                return;
            }
            IAudioService iAudioService = AudioManager.getService();
            try {
                iAudioService.playSoundEffect(n);
                return;
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
    }

    public void preDispatchKeyEvent(KeyEvent keyEvent, int n) {
        int n2 = keyEvent.getKeyCode();
        if (n2 != 25 && n2 != 24 && n2 != 164 && this.mVolumeKeyUpTime + 300L > SystemClock.uptimeMillis()) {
            this.adjustSuggestedStreamVolume(0, n, 8);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void registerAudioDeviceCallback(AudioDeviceCallback audioDeviceCallback, Handler handler) {
        ArrayMap<AudioDeviceCallback, NativeEventHandlerDelegate> arrayMap = this.mDeviceCallbacks;
        synchronized (arrayMap) {
            if (audioDeviceCallback != null && !this.mDeviceCallbacks.containsKey(audioDeviceCallback)) {
                Object object;
                if (this.mDeviceCallbacks.size() == 0) {
                    if (this.mPortListener == null) {
                        this.mPortListener = object = new OnAmPortUpdateListener();
                    }
                    this.registerAudioPortUpdateListener(this.mPortListener);
                }
                object = new NativeEventHandlerDelegate(audioDeviceCallback, handler);
                this.mDeviceCallbacks.put(audioDeviceCallback, (NativeEventHandlerDelegate)object);
                this.broadcastDeviceListChange_sync(((NativeEventHandlerDelegate)object).getHandler());
            }
            return;
        }
    }

    public void registerAudioFocusRequest(AudioFocusRequest object) {
        Object object2 = ((AudioFocusRequest)object).getOnAudioFocusChangeListenerHandler();
        object2 = object2 == null ? null : new ServiceEventHandlerDelegate((Handler)object2).getHandler();
        object2 = new FocusRequestInfo((AudioFocusRequest)object, (Handler)object2);
        object = this.getIdForAudioFocusListener(((AudioFocusRequest)object).getOnAudioFocusChangeListener());
        this.mAudioFocusIdListenerMap.put((String)object, (FocusRequestInfo)object2);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void registerAudioPlaybackCallback(AudioPlaybackCallback audioPlaybackCallback, Handler handler) {
        if (audioPlaybackCallback == null) {
            throw new IllegalArgumentException("Illegal null AudioPlaybackCallback argument");
        }
        Object object = this.mPlaybackCallbackLock;
        synchronized (object) {
            Object object2;
            if (this.mPlaybackCallbackList == null) {
                object2 = new ArrayList();
                this.mPlaybackCallbackList = object2;
            }
            int n = this.mPlaybackCallbackList.size();
            if (!this.hasPlaybackCallback_sync(audioPlaybackCallback)) {
                List<AudioPlaybackCallbackInfo> list = this.mPlaybackCallbackList;
                object2 = new ServiceEventHandlerDelegate(handler);
                AudioPlaybackCallbackInfo audioPlaybackCallbackInfo = new AudioPlaybackCallbackInfo(audioPlaybackCallback, ((ServiceEventHandlerDelegate)object2).getHandler());
                list.add(audioPlaybackCallbackInfo);
                int n2 = this.mPlaybackCallbackList.size();
                if (n == 0 && n2 > 0) {
                    try {
                        AudioManager.getService().registerPlaybackCallback(this.mPlayCb);
                    }
                    catch (RemoteException remoteException) {
                        throw remoteException.rethrowFromSystemServer();
                    }
                }
            } else {
                Log.w(TAG, "attempt to call registerAudioPlaybackCallback() on a previouslyregistered callback");
            }
            return;
        }
    }

    @SystemApi
    public int registerAudioPolicy(AudioPolicy audioPolicy) {
        return AudioManager.registerAudioPolicyStatic(audioPolicy);
    }

    @UnsupportedAppUsage
    public void registerAudioPortUpdateListener(OnAudioPortUpdateListener onAudioPortUpdateListener) {
        sAudioPortEventHandler.init();
        sAudioPortEventHandler.registerListener(onAudioPortUpdateListener);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void registerAudioRecordingCallback(AudioRecordingCallback object, Handler handler) {
        if (object == null) {
            throw new IllegalArgumentException("Illegal null AudioRecordingCallback argument");
        }
        Object object2 = this.mRecordCallbackLock;
        synchronized (object2) {
            Object object3;
            if (this.mRecordCallbackList == null) {
                object3 = new ArrayList();
                this.mRecordCallbackList = object3;
            }
            int n = this.mRecordCallbackList.size();
            if (!this.hasRecordCallback_sync((AudioRecordingCallback)object)) {
                List<AudioRecordingCallbackInfo> list = this.mRecordCallbackList;
                ServiceEventHandlerDelegate serviceEventHandlerDelegate = new ServiceEventHandlerDelegate(handler);
                object3 = new AudioRecordingCallbackInfo((AudioRecordingCallback)object, serviceEventHandlerDelegate.getHandler());
                list.add((AudioRecordingCallbackInfo)object3);
                int n2 = this.mRecordCallbackList.size();
                if (n == 0 && n2 > 0) {
                    object = AudioManager.getService();
                    try {
                        object.registerRecordingCallback(this.mRecCb);
                    }
                    catch (RemoteException remoteException) {
                        throw remoteException.rethrowFromSystemServer();
                    }
                }
            } else {
                Log.w(TAG, "attempt to call registerAudioRecordingCallback() on a previouslyregistered callback");
            }
            return;
        }
    }

    @Deprecated
    public void registerMediaButtonEventReceiver(PendingIntent pendingIntent) {
        if (pendingIntent == null) {
            return;
        }
        this.registerMediaButtonIntent(pendingIntent, null);
    }

    @Deprecated
    public void registerMediaButtonEventReceiver(ComponentName componentName) {
        if (componentName == null) {
            return;
        }
        if (!componentName.getPackageName().equals(this.getContext().getPackageName())) {
            Log.e(TAG, "registerMediaButtonEventReceiver() error: receiver and context package names don't match");
            return;
        }
        Intent intent = new Intent("android.intent.action.MEDIA_BUTTON");
        intent.setComponent(componentName);
        this.registerMediaButtonIntent(PendingIntent.getBroadcast(this.getContext(), 0, intent, 0), componentName);
    }

    public void registerMediaButtonIntent(PendingIntent pendingIntent, ComponentName componentName) {
        if (pendingIntent == null) {
            Log.e(TAG, "Cannot call registerMediaButtonIntent() with a null parameter");
            return;
        }
        MediaSessionLegacyHelper.getHelper(this.getContext()).addMediaButtonListener(pendingIntent, componentName, this.getContext());
    }

    @Deprecated
    public void registerRemoteControlClient(RemoteControlClient remoteControlClient) {
        if (remoteControlClient != null && remoteControlClient.getRcMediaIntent() != null) {
            remoteControlClient.registerWithSession(MediaSessionLegacyHelper.getHelper(this.getContext()));
            return;
        }
    }

    @Deprecated
    public boolean registerRemoteController(RemoteController remoteController) {
        if (remoteController == null) {
            return false;
        }
        remoteController.startListeningToSessions();
        return true;
    }

    @SystemApi
    public void registerVolumeGroupCallback(Executor executor, VolumeGroupCallback volumeGroupCallback) {
        Preconditions.checkNotNull(executor, "executor must not be null");
        Preconditions.checkNotNull(volumeGroupCallback, "volume group change cb must not be null");
        sAudioAudioVolumeGroupChangedHandler.init();
        sAudioAudioVolumeGroupChangedHandler.registerListener(volumeGroupCallback);
    }

    @UnsupportedAppUsage
    public void reloadAudioSettings() {
        IAudioService iAudioService = AudioManager.getService();
        try {
            iAudioService.reloadAudioSettings();
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public int requestAudioFocus(AudioFocusRequest audioFocusRequest) {
        return this.requestAudioFocus(audioFocusRequest, null);
    }

    /*
     * Exception decompiling
     */
    @SystemApi
    public int requestAudioFocus(AudioFocusRequest var1_1, AudioPolicy var2_4) {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [1[TRYBLOCK]], but top level block is 10[CATCHBLOCK]
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.processEndingBlocks(Op04StructuredStatement.java:427)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:479)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:607)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:696)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:184)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:129)
        // org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:96)
        // org.benf.cfr.reader.entities.Method.analyse(Method.java:397)
        // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:906)
        // org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:797)
        // org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:225)
        // org.benf.cfr.reader.Driver.doJar(Driver.java:109)
        // org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:65)
        // org.benf.cfr.reader.Main.main(Main.java:48)
        throw new IllegalStateException("Decompilation failed");
    }

    public int requestAudioFocus(OnAudioFocusChangeListener onAudioFocusChangeListener, int n, int n2) {
        PlayerBase.deprecateStreamTypeForPlayback(n, TAG, "requestAudioFocus()");
        int n3 = 0;
        try {
            AudioAttributes.Builder builder = new AudioAttributes.Builder();
            n = this.requestAudioFocus(onAudioFocusChangeListener, builder.setInternalLegacyStreamType(n).build(), n2, 0);
        }
        catch (IllegalArgumentException illegalArgumentException) {
            Log.e(TAG, "Audio focus request denied due to ", illegalArgumentException);
            n = n3;
        }
        return n;
    }

    @SystemApi
    public int requestAudioFocus(OnAudioFocusChangeListener object, AudioAttributes audioAttributes, int n, int n2) throws IllegalArgumentException {
        if (n2 == (n2 & 3)) {
            return this.requestAudioFocus((OnAudioFocusChangeListener)object, audioAttributes, n, n2 & 3, null);
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Invalid flags 0x");
        ((StringBuilder)object).append(Integer.toHexString(n2).toUpperCase());
        throw new IllegalArgumentException(((StringBuilder)object).toString());
    }

    @SystemApi
    public int requestAudioFocus(OnAudioFocusChangeListener object, AudioAttributes audioAttributes, int n, int n2, AudioPolicy audioPolicy) throws IllegalArgumentException {
        if (audioAttributes != null) {
            if (AudioFocusRequest.isValidFocusGain(n)) {
                if (n2 == (n2 & 7)) {
                    boolean bl = true;
                    if ((n2 & 1) == 1 && object == null) {
                        throw new IllegalArgumentException("Illegal null focus listener when flagged as accepting delayed focus grant");
                    }
                    if ((n2 & 2) == 2 && object == null) {
                        throw new IllegalArgumentException("Illegal null focus listener when flagged as pausing instead of ducking");
                    }
                    if ((n2 & 4) == 4 && audioPolicy == null) {
                        throw new IllegalArgumentException("Illegal null audio policy when locking audio focus");
                    }
                    object = new AudioFocusRequest.Builder(n).setOnAudioFocusChangeListenerInt((OnAudioFocusChangeListener)object, null).setAudioAttributes(audioAttributes);
                    boolean bl2 = (n2 & 1) == 1;
                    object = ((AudioFocusRequest.Builder)object).setAcceptsDelayedFocusGain(bl2);
                    bl2 = (n2 & 2) == 2;
                    object = ((AudioFocusRequest.Builder)object).setWillPauseWhenDucked(bl2);
                    bl2 = (n2 & 4) == 4 ? bl : false;
                    return this.requestAudioFocus(((AudioFocusRequest.Builder)object).setLocksFocus(bl2).build(), audioPolicy);
                }
                object = new StringBuilder();
                ((StringBuilder)object).append("Illegal flags 0x");
                ((StringBuilder)object).append(Integer.toHexString(n2).toUpperCase());
                throw new IllegalArgumentException(((StringBuilder)object).toString());
            }
            throw new IllegalArgumentException("Invalid duration hint");
        }
        throw new IllegalArgumentException("Illegal null AudioAttributes argument");
    }

    @UnsupportedAppUsage
    public void requestAudioFocusForCall(int n, int n2) {
        IAudioService iAudioService = AudioManager.getService();
        try {
            AudioAttributes.Builder builder = new AudioAttributes.Builder();
            iAudioService.requestAudioFocus(builder.setInternalLegacyStreamType(n).build(), n2, this.mICallBack, null, "AudioFocus_For_Phone_Ring_And_Calls", this.getContext().getOpPackageName(), 4, null, 0);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public void setAllowedCapturePolicy(int n) {
        int n2 = AudioAttributes.capturePolicyToFlags(n, 0);
        n2 = AudioSystem.setAllowedCapturePolicy(Process.myUid(), n2);
        if (n2 != 0) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Could not setAllowedCapturePolicy: ");
            stringBuilder.append(n2);
            Log.e(TAG, stringBuilder.toString());
            return;
        }
        this.mCapturePolicy = n;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @SystemApi
    public void setAudioServerStateCallback(Executor object, AudioServerStateCallback audioServerStateCallback) {
        if (audioServerStateCallback == null) {
            throw new IllegalArgumentException("Illegal null AudioServerStateCallback");
        }
        if (object == null) {
            throw new IllegalArgumentException("Illegal null Executor for the AudioServerStateCallback");
        }
        Object object2 = this.mAudioServerStateCbLock;
        synchronized (object2) {
            if (this.mAudioServerStateCb != null) {
                object = new IllegalStateException("setAudioServerStateCallback called with already registered callabck");
                throw object;
            }
            IAudioService iAudioService = AudioManager.getService();
            try {
                iAudioService.registerAudioServerStateDispatcher(this.mAudioServerStateDispatcher);
                this.mAudioServerStateExec = object;
                this.mAudioServerStateCb = audioServerStateCallback;
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
            return;
        }
    }

    public void setBluetoothA2dpDeviceConnectionStateSuppressNoisyIntent(BluetoothDevice bluetoothDevice, int n, int n2, boolean bl, int n3) {
        IAudioService iAudioService = AudioManager.getService();
        try {
            iAudioService.setBluetoothA2dpDeviceConnectionStateSuppressNoisyIntent(bluetoothDevice, n, n2, bl, n3);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @Deprecated
    public void setBluetoothA2dpOn(boolean bl) {
    }

    public void setBluetoothHearingAidDeviceConnectionState(BluetoothDevice bluetoothDevice, int n, boolean bl, int n2) {
        IAudioService iAudioService = AudioManager.getService();
        try {
            iAudioService.setBluetoothHearingAidDeviceConnectionState(bluetoothDevice, n, bl, n2);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public void setBluetoothScoOn(boolean bl) {
        IAudioService iAudioService = AudioManager.getService();
        try {
            iAudioService.setBluetoothScoOn(bl);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public void setFocusRequestResult(AudioFocusInfo audioFocusInfo, int n, AudioPolicy audioPolicy) {
        if (audioFocusInfo != null) {
            if (audioPolicy != null) {
                IAudioService iAudioService = AudioManager.getService();
                try {
                    iAudioService.setFocusRequestResultFromExtPolicy(audioFocusInfo, n, audioPolicy.cb());
                    return;
                }
                catch (RemoteException remoteException) {
                    throw remoteException.rethrowFromSystemServer();
                }
            }
            throw new IllegalArgumentException("Illegal null AudioPolicy");
        }
        throw new IllegalArgumentException("Illegal null AudioFocusInfo");
    }

    public int setHdmiSystemAudioSupported(boolean bl) {
        try {
            int n = AudioManager.getService().setHdmiSystemAudioSupported(bl);
            return n;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @UnsupportedAppUsage
    public void setMasterMute(boolean bl, int n) {
        IAudioService iAudioService = AudioManager.getService();
        try {
            iAudioService.setMasterMute(bl, n, this.getContext().getOpPackageName(), UserHandle.getCallingUserId());
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public void setMicrophoneMute(boolean bl) {
        IAudioService iAudioService = AudioManager.getService();
        try {
            iAudioService.setMicrophoneMute(bl, this.getContext().getOpPackageName(), UserHandle.getCallingUserId());
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public void setMode(int n) {
        IAudioService iAudioService = AudioManager.getService();
        try {
            iAudioService.setMode(n, this.mICallBack, this.mApplicationContext.getOpPackageName());
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @Deprecated
    public void setParameter(String string2, String string3) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(string2);
        stringBuilder.append("=");
        stringBuilder.append(string3);
        this.setParameters(stringBuilder.toString());
    }

    public void setParameters(String string2) {
        AudioSystem.setParameters(string2);
    }

    public void setRingerMode(int n) {
        if (!AudioManager.isValidRingerMode(n)) {
            return;
        }
        IAudioService iAudioService = AudioManager.getService();
        try {
            iAudioService.setRingerModeExternal(n, this.getContext().getOpPackageName());
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @UnsupportedAppUsage
    public void setRingerModeInternal(int n) {
        try {
            AudioManager.getService().setRingerModeInternal(n, this.getContext().getOpPackageName());
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @Deprecated
    public void setRouting(int n, int n2, int n3) {
    }

    public void setSpeakerphoneOn(boolean bl) {
        IAudioService iAudioService = AudioManager.getService();
        try {
            iAudioService.setSpeakerphoneOn(bl);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @Deprecated
    public void setStreamMute(int n, boolean bl) {
        Log.w(TAG, "setStreamMute is deprecated. adjustStreamVolume should be used instead.");
        int n2 = bl ? -100 : 100;
        if (n == Integer.MIN_VALUE) {
            this.adjustSuggestedStreamVolume(n2, n, 0);
        } else {
            this.adjustStreamVolume(n, n2, 0);
        }
    }

    @Deprecated
    public void setStreamSolo(int n, boolean bl) {
        Log.w(TAG, "setStreamSolo has been deprecated. Do not use.");
    }

    public void setStreamVolume(int n, int n2, int n3) {
        IAudioService iAudioService = AudioManager.getService();
        try {
            iAudioService.setStreamVolume(n, n2, n3, this.getContext().getOpPackageName());
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public boolean setSurroundFormatEnabled(int n, boolean bl) {
        bl = AudioSystem.setSurroundFormatEnabled(n, bl) == 0;
        return bl;
    }

    public void setVibrateSetting(int n, int n2) {
        IAudioService iAudioService = AudioManager.getService();
        try {
            iAudioService.setVibrateSetting(n, n2);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public void setVolumeController(IVolumeController iVolumeController) {
        try {
            AudioManager.getService().setVolumeController(iVolumeController);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public void setVolumeIndexForAttributes(AudioAttributes audioAttributes, int n, int n2) {
        Preconditions.checkNotNull(audioAttributes, "attr must not be null");
        IAudioService iAudioService = AudioManager.getService();
        try {
            iAudioService.setVolumeIndexForAttributes(audioAttributes, n, n2, this.getContext().getOpPackageName());
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public void setVolumePolicy(VolumePolicy volumePolicy) {
        try {
            AudioManager.getService().setVolumePolicy(volumePolicy);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @UnsupportedAppUsage
    public void setWiredDeviceConnectionState(int n, int n2, String string2, String string3) {
        IAudioService iAudioService = AudioManager.getService();
        try {
            iAudioService.setWiredDeviceConnectionState(n, n2, string2, string3, this.mApplicationContext.getOpPackageName());
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @Deprecated
    public void setWiredHeadsetOn(boolean bl) {
    }

    public boolean shouldVibrate(int n) {
        IAudioService iAudioService = AudioManager.getService();
        try {
            boolean bl = iAudioService.shouldVibrate(n);
            return bl;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public void startBluetoothSco() {
        IAudioService iAudioService = AudioManager.getService();
        try {
            iAudioService.startBluetoothSco(this.mICallBack, this.getContext().getApplicationInfo().targetSdkVersion);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @UnsupportedAppUsage
    public void startBluetoothScoVirtualCall() {
        IAudioService iAudioService = AudioManager.getService();
        try {
            iAudioService.startBluetoothScoVirtualCall(this.mICallBack);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public void stopBluetoothSco() {
        IAudioService iAudioService = AudioManager.getService();
        try {
            iAudioService.stopBluetoothSco(this.mICallBack);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public void unloadSoundEffects() {
        IAudioService iAudioService = AudioManager.getService();
        try {
            iAudioService.unloadSoundEffects();
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void unregisterAudioDeviceCallback(AudioDeviceCallback audioDeviceCallback) {
        ArrayMap<AudioDeviceCallback, NativeEventHandlerDelegate> arrayMap = this.mDeviceCallbacks;
        synchronized (arrayMap) {
            if (this.mDeviceCallbacks.containsKey(audioDeviceCallback)) {
                this.mDeviceCallbacks.remove(audioDeviceCallback);
                if (this.mDeviceCallbacks.size() == 0) {
                    this.unregisterAudioPortUpdateListener(this.mPortListener);
                }
            }
            return;
        }
    }

    public void unregisterAudioFocusRequest(OnAudioFocusChangeListener onAudioFocusChangeListener) {
        this.mAudioFocusIdListenerMap.remove(this.getIdForAudioFocusListener(onAudioFocusChangeListener));
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void unregisterAudioPlaybackCallback(AudioPlaybackCallback audioPlaybackCallback) {
        if (audioPlaybackCallback == null) {
            throw new IllegalArgumentException("Illegal null AudioPlaybackCallback argument");
        }
        Object object = this.mPlaybackCallbackLock;
        synchronized (object) {
            if (this.mPlaybackCallbackList == null) {
                Log.w(TAG, "attempt to call unregisterAudioPlaybackCallback() on a callback that was never registered");
                return;
            }
            int n = this.mPlaybackCallbackList.size();
            if (this.removePlaybackCallback_sync(audioPlaybackCallback)) {
                int n2 = this.mPlaybackCallbackList.size();
                if (n > 0 && n2 == 0) {
                    try {
                        AudioManager.getService().unregisterPlaybackCallback(this.mPlayCb);
                    }
                    catch (RemoteException remoteException) {
                        throw remoteException.rethrowFromSystemServer();
                    }
                }
            } else {
                Log.w(TAG, "attempt to call unregisterAudioPlaybackCallback() on a callback already unregistered or never registered");
            }
            return;
        }
    }

    @SystemApi
    public void unregisterAudioPolicy(AudioPolicy audioPolicy) {
        Preconditions.checkNotNull(audioPolicy, "Illegal null AudioPolicy argument");
        IAudioService iAudioService = AudioManager.getService();
        try {
            audioPolicy.invalidateCaptorsAndInjectors();
            iAudioService.unregisterAudioPolicy(audioPolicy.cb());
            audioPolicy.setRegistration(null);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public void unregisterAudioPolicyAsync(AudioPolicy audioPolicy) {
        AudioManager.unregisterAudioPolicyAsyncStatic(audioPolicy);
    }

    @UnsupportedAppUsage
    public void unregisterAudioPortUpdateListener(OnAudioPortUpdateListener onAudioPortUpdateListener) {
        sAudioPortEventHandler.unregisterListener(onAudioPortUpdateListener);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void unregisterAudioRecordingCallback(AudioRecordingCallback object) {
        if (object == null) {
            throw new IllegalArgumentException("Illegal null AudioRecordingCallback argument");
        }
        Object object2 = this.mRecordCallbackLock;
        synchronized (object2) {
            if (this.mRecordCallbackList == null) {
                return;
            }
            int n = this.mRecordCallbackList.size();
            if (this.removeRecordCallback_sync((AudioRecordingCallback)object)) {
                int n2 = this.mRecordCallbackList.size();
                if (n > 0 && n2 == 0) {
                    object = AudioManager.getService();
                    try {
                        object.unregisterRecordingCallback(this.mRecCb);
                    }
                    catch (RemoteException remoteException) {
                        throw remoteException.rethrowFromSystemServer();
                    }
                }
            } else {
                Log.w(TAG, "attempt to call unregisterAudioRecordingCallback() on a callback already unregistered or never registered");
            }
            return;
        }
    }

    @Deprecated
    public void unregisterMediaButtonEventReceiver(PendingIntent pendingIntent) {
        if (pendingIntent == null) {
            return;
        }
        this.unregisterMediaButtonIntent(pendingIntent);
    }

    @Deprecated
    public void unregisterMediaButtonEventReceiver(ComponentName componentName) {
        if (componentName == null) {
            return;
        }
        Intent intent = new Intent("android.intent.action.MEDIA_BUTTON");
        intent.setComponent(componentName);
        this.unregisterMediaButtonIntent(PendingIntent.getBroadcast(this.getContext(), 0, intent, 0));
    }

    public void unregisterMediaButtonIntent(PendingIntent pendingIntent) {
        MediaSessionLegacyHelper.getHelper(this.getContext()).removeMediaButtonListener(pendingIntent);
    }

    @Deprecated
    public void unregisterRemoteControlClient(RemoteControlClient remoteControlClient) {
        if (remoteControlClient != null && remoteControlClient.getRcMediaIntent() != null) {
            remoteControlClient.unregisterWithSession(MediaSessionLegacyHelper.getHelper(this.getContext()));
            return;
        }
    }

    @Deprecated
    public void unregisterRemoteController(RemoteController remoteController) {
        if (remoteController == null) {
            return;
        }
        remoteController.stopListeningToSessions();
    }

    @SystemApi
    public void unregisterVolumeGroupCallback(VolumeGroupCallback volumeGroupCallback) {
        Preconditions.checkNotNull(volumeGroupCallback, "volume group change cb must not be null");
        sAudioAudioVolumeGroupChangedHandler.unregisterListener(volumeGroupCallback);
    }

    public static abstract class AudioPlaybackCallback {
        public void onPlaybackConfigChanged(List<AudioPlaybackConfiguration> list) {
        }
    }

    private static class AudioPlaybackCallbackInfo {
        final AudioPlaybackCallback mCb;
        final Handler mHandler;

        AudioPlaybackCallbackInfo(AudioPlaybackCallback audioPlaybackCallback, Handler handler) {
            this.mCb = audioPlaybackCallback;
            this.mHandler = handler;
        }
    }

    public static abstract class AudioRecordingCallback {
        public void onRecordingConfigChanged(List<AudioRecordingConfiguration> list) {
        }
    }

    private static class AudioRecordingCallbackInfo {
        final AudioRecordingCallback mCb;
        final Handler mHandler;

        AudioRecordingCallbackInfo(AudioRecordingCallback audioRecordingCallback, Handler handler) {
            this.mCb = audioRecordingCallback;
            this.mHandler = handler;
        }
    }

    @SystemApi
    public static abstract class AudioServerStateCallback {
        public void onAudioServerDown() {
        }

        public void onAudioServerUp() {
        }
    }

    private static final class BlockingFocusResultReceiver {
        private final String mFocusClientId;
        private int mFocusRequestResult = 0;
        private final SafeWaitObject mLock = new SafeWaitObject();
        @GuardedBy(value={"mLock"})
        private boolean mResultReceived = false;

        BlockingFocusResultReceiver(String string2) {
            this.mFocusClientId = string2;
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        void notifyResult(int n) {
            SafeWaitObject safeWaitObject = this.mLock;
            synchronized (safeWaitObject) {
                this.mResultReceived = true;
                this.mFocusRequestResult = n;
                this.mLock.safeNotify();
                return;
            }
        }

        boolean receivedResult() {
            return this.mResultReceived;
        }

        int requestResult() {
            return this.mFocusRequestResult;
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public void waitForResult(long l) {
            SafeWaitObject safeWaitObject = this.mLock;
            synchronized (safeWaitObject) {
                if (this.mResultReceived) {
                    return;
                }
                try {
                    this.mLock.safeWait(l);
                }
                catch (InterruptedException interruptedException) {
                    // empty catch block
                }
                return;
            }
        }
    }

    private static class FocusRequestInfo {
        final Handler mHandler;
        final AudioFocusRequest mRequest;

        FocusRequestInfo(AudioFocusRequest audioFocusRequest, Handler handler) {
            this.mRequest = audioFocusRequest;
            this.mHandler = handler;
        }
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface FocusRequestResult {
    }

    private class NativeEventHandlerDelegate {
        private final Handler mHandler;

        NativeEventHandlerDelegate(final AudioDeviceCallback audioDeviceCallback, Handler object) {
            object = object != null ? ((Handler)object).getLooper() : Looper.getMainLooper();
            this.mHandler = object != null ? new Handler((Looper)object){

                @Override
                public void handleMessage(Message message) {
                    int n = message.what;
                    if (n != 0 && n != 1) {
                        if (n != 2) {
                            StringBuilder stringBuilder = new StringBuilder();
                            stringBuilder.append("Unknown native event type: ");
                            stringBuilder.append(message.what);
                            Log.e(AudioManager.TAG, stringBuilder.toString());
                        } else {
                            AudioDeviceCallback audioDeviceCallback2 = audioDeviceCallback;
                            if (audioDeviceCallback2 != null) {
                                audioDeviceCallback2.onAudioDevicesRemoved((AudioDeviceInfo[])message.obj);
                            }
                        }
                    } else {
                        AudioDeviceCallback audioDeviceCallback3 = audioDeviceCallback;
                        if (audioDeviceCallback3 != null) {
                            audioDeviceCallback3.onAudioDevicesAdded((AudioDeviceInfo[])message.obj);
                        }
                    }
                }
            } : null;
        }

        Handler getHandler() {
            return this.mHandler;
        }

    }

    private class OnAmPortUpdateListener
    implements OnAudioPortUpdateListener {
        static final String TAG = "OnAmPortUpdateListener";

        private OnAmPortUpdateListener() {
        }

        @Override
        public void onAudioPatchListUpdate(AudioPatch[] arraudioPatch) {
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void onAudioPortListUpdate(AudioPort[] arraudioPort) {
            ArrayMap arrayMap = AudioManager.this.mDeviceCallbacks;
            synchronized (arrayMap) {
                AudioManager.this.broadcastDeviceListChange_sync(null);
                return;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void onServiceDied() {
            ArrayMap arrayMap = AudioManager.this.mDeviceCallbacks;
            synchronized (arrayMap) {
                AudioManager.this.broadcastDeviceListChange_sync(null);
                return;
            }
        }
    }

    public static interface OnAudioFocusChangeListener {
        public void onAudioFocusChange(int var1);
    }

    public static interface OnAudioPortUpdateListener {
        public void onAudioPatchListUpdate(AudioPatch[] var1);

        public void onAudioPortListUpdate(AudioPort[] var1);

        public void onServiceDied();
    }

    private static final class PlaybackConfigChangeCallbackData {
        final AudioPlaybackCallback mCb;
        final List<AudioPlaybackConfiguration> mConfigs;

        PlaybackConfigChangeCallbackData(AudioPlaybackCallback audioPlaybackCallback, List<AudioPlaybackConfiguration> list) {
            this.mCb = audioPlaybackCallback;
            this.mConfigs = list;
        }
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface PublicStreamTypes {
    }

    private static final class RecordConfigChangeCallbackData {
        final AudioRecordingCallback mCb;
        final List<AudioRecordingConfiguration> mConfigs;

        RecordConfigChangeCallbackData(AudioRecordingCallback audioRecordingCallback, List<AudioRecordingConfiguration> list) {
            this.mCb = audioRecordingCallback;
            this.mConfigs = list;
        }
    }

    private static final class SafeWaitObject {
        private boolean mQuit = false;

        private SafeWaitObject() {
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public void safeNotify() {
            synchronized (this) {
                this.mQuit = true;
                this.notify();
                return;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public void safeWait(long l) throws InterruptedException {
            long l2 = System.currentTimeMillis();
            synchronized (this) {
                long l3;
                while (!this.mQuit && (l3 = l2 + l - System.currentTimeMillis()) >= 0L) {
                    this.wait(l3);
                }
                return;
            }
        }
    }

    private class ServiceEventHandlerDelegate {
        private final Handler mHandler;

        ServiceEventHandlerDelegate(Handler object) {
            if (object == null) {
                Looper looper = Looper.myLooper();
                object = looper;
                if (looper == null) {
                    object = Looper.getMainLooper();
                }
            } else {
                object = ((Handler)object).getLooper();
            }
            this.mHandler = object != null ? new Handler((Looper)object){

                @Override
                public void handleMessage(Message object) {
                    int n = ((Message)object).what;
                    if (n != 0) {
                        if (n != 1) {
                            if (n != 2) {
                                StringBuilder stringBuilder = new StringBuilder();
                                stringBuilder.append("Unknown event ");
                                stringBuilder.append(((Message)object).what);
                                Log.e(AudioManager.TAG, stringBuilder.toString());
                            } else {
                                object = (PlaybackConfigChangeCallbackData)((Message)object).obj;
                                if (((PlaybackConfigChangeCallbackData)object).mCb != null) {
                                    ((PlaybackConfigChangeCallbackData)object).mCb.onPlaybackConfigChanged(((PlaybackConfigChangeCallbackData)object).mConfigs);
                                }
                            }
                        } else {
                            object = (RecordConfigChangeCallbackData)((Message)object).obj;
                            if (((RecordConfigChangeCallbackData)object).mCb != null) {
                                ((RecordConfigChangeCallbackData)object).mCb.onRecordingConfigChanged(((RecordConfigChangeCallbackData)object).mConfigs);
                            }
                        }
                    } else {
                        Object object2 = AudioManager.this.findFocusRequestInfo((String)((Message)object).obj);
                        if (object2 != null && (object2 = ((FocusRequestInfo)object2).mRequest.getOnAudioFocusChangeListener()) != null) {
                            StringBuilder stringBuilder = new StringBuilder();
                            stringBuilder.append("dispatching onAudioFocusChange(");
                            stringBuilder.append(((Message)object).arg1);
                            stringBuilder.append(") to ");
                            stringBuilder.append(((Message)object).obj);
                            Log.d(AudioManager.TAG, stringBuilder.toString());
                            object2.onAudioFocusChange(((Message)object).arg1);
                        }
                    }
                }
            } : null;
        }

        Handler getHandler() {
            return this.mHandler;
        }

    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface VolumeAdjustment {
    }

    @SystemApi
    public static abstract class VolumeGroupCallback {
        public void onAudioVolumeGroupChanged(int n, int n2) {
        }
    }

}

