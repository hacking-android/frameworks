/*
 * Decompiled with CFR 0.145.
 */
package android.media;

import android.annotation.UnsupportedAppUsage;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.media.AudioAttributes;
import android.media.AudioFormat;
import android.media.AudioPatch;
import android.media.AudioPort;
import android.media.AudioPortConfig;
import android.media.MicrophoneInfo;
import android.media.audiofx.AudioEffect;
import android.media.audiopolicy.AudioMix;
import android.util.Log;
import java.util.ArrayList;
import java.util.Map;

public class AudioSystem {
    public static final int AUDIO_FORMAT_AAC = 67108864;
    public static final int AUDIO_FORMAT_APTX = 536870912;
    public static final int AUDIO_FORMAT_APTX_HD = 553648128;
    public static final int AUDIO_FORMAT_DEFAULT = 0;
    public static final int AUDIO_FORMAT_INVALID = -1;
    public static final int AUDIO_FORMAT_LDAC = 587202560;
    public static final int AUDIO_FORMAT_SBC = 520093696;
    public static final int AUDIO_HW_SYNC_INVALID = 0;
    public static final int AUDIO_SESSION_ALLOCATE = 0;
    public static final int AUDIO_STATUS_ERROR = 1;
    public static final int AUDIO_STATUS_OK = 0;
    public static final int AUDIO_STATUS_SERVER_DIED = 100;
    public static final int BAD_VALUE = -2;
    public static final int DEAD_OBJECT = -6;
    private static final boolean DEBUG_VOLUME = false;
    public static final int DEFAULT_MUTE_STREAMS_AFFECTED = 111;
    public static int[] DEFAULT_STREAM_VOLUME;
    public static final int DEVICE_ALL_HDMI_SYSTEM_AUDIO_AND_SPEAKER = 2883586;
    public static final int DEVICE_BIT_DEFAULT = 1073741824;
    public static final int DEVICE_BIT_IN = Integer.MIN_VALUE;
    public static final int DEVICE_IN_ALL = -551550977;
    public static final int DEVICE_IN_ALL_SCO = -2147483640;
    public static final int DEVICE_IN_ALL_USB = -2113923072;
    @UnsupportedAppUsage
    public static final int DEVICE_IN_AMBIENT = -2147483646;
    public static final String DEVICE_IN_AMBIENT_NAME = "ambient";
    @UnsupportedAppUsage
    public static final int DEVICE_IN_ANLG_DOCK_HEADSET = -2147483136;
    public static final String DEVICE_IN_ANLG_DOCK_HEADSET_NAME = "analog_dock";
    @UnsupportedAppUsage
    public static final int DEVICE_IN_AUX_DIGITAL = -2147483616;
    public static final String DEVICE_IN_AUX_DIGITAL_NAME = "aux_digital";
    @UnsupportedAppUsage
    public static final int DEVICE_IN_BACK_MIC = -2147483520;
    public static final String DEVICE_IN_BACK_MIC_NAME = "back_mic";
    @UnsupportedAppUsage
    public static final int DEVICE_IN_BLUETOOTH_A2DP = -2147352576;
    public static final String DEVICE_IN_BLUETOOTH_A2DP_NAME = "bt_a2dp";
    public static final int DEVICE_IN_BLUETOOTH_BLE = -2080374784;
    public static final String DEVICE_IN_BLUETOOTH_BLE_NAME = "bt_ble";
    @UnsupportedAppUsage
    public static final int DEVICE_IN_BLUETOOTH_SCO_HEADSET = -2147483640;
    public static final String DEVICE_IN_BLUETOOTH_SCO_HEADSET_NAME = "bt_sco_hs";
    @UnsupportedAppUsage
    public static final int DEVICE_IN_BUILTIN_MIC = -2147483644;
    public static final String DEVICE_IN_BUILTIN_MIC_NAME = "mic";
    public static final int DEVICE_IN_BUS = -2146435072;
    public static final String DEVICE_IN_BUS_NAME = "bus";
    @UnsupportedAppUsage
    public static final int DEVICE_IN_COMMUNICATION = -2147483647;
    public static final String DEVICE_IN_COMMUNICATION_NAME = "communication";
    @UnsupportedAppUsage
    public static final int DEVICE_IN_DEFAULT = -1073741824;
    @UnsupportedAppUsage
    public static final int DEVICE_IN_DGTL_DOCK_HEADSET = -2147482624;
    public static final String DEVICE_IN_DGTL_DOCK_HEADSET_NAME = "digital_dock";
    public static final int DEVICE_IN_ECHO_REFERENCE = -1879048192;
    public static final String DEVICE_IN_ECHO_REFERENCE_NAME = "echo_reference";
    public static final int DEVICE_IN_FM_TUNER = -2147475456;
    public static final String DEVICE_IN_FM_TUNER_NAME = "fm_tuner";
    public static final int DEVICE_IN_HDMI = -2147483616;
    public static final int DEVICE_IN_HDMI_ARC = -2013265920;
    public static final String DEVICE_IN_HDMI_ARC_NAME = "hdmi_arc";
    public static final int DEVICE_IN_IP = -2146959360;
    public static final String DEVICE_IN_IP_NAME = "ip";
    public static final int DEVICE_IN_LINE = -2147450880;
    public static final String DEVICE_IN_LINE_NAME = "line";
    public static final int DEVICE_IN_LOOPBACK = -2147221504;
    public static final String DEVICE_IN_LOOPBACK_NAME = "loopback";
    public static final int DEVICE_IN_PROXY = -2130706432;
    public static final String DEVICE_IN_PROXY_NAME = "proxy";
    @UnsupportedAppUsage
    public static final int DEVICE_IN_REMOTE_SUBMIX = -2147483392;
    public static final String DEVICE_IN_REMOTE_SUBMIX_NAME = "remote_submix";
    public static final int DEVICE_IN_SPDIF = -2147418112;
    public static final String DEVICE_IN_SPDIF_NAME = "spdif";
    public static final int DEVICE_IN_TELEPHONY_RX = -2147483584;
    public static final String DEVICE_IN_TELEPHONY_RX_NAME = "telephony_rx";
    public static final int DEVICE_IN_TV_TUNER = -2147467264;
    public static final String DEVICE_IN_TV_TUNER_NAME = "tv_tuner";
    @UnsupportedAppUsage
    public static final int DEVICE_IN_USB_ACCESSORY = -2147481600;
    public static final String DEVICE_IN_USB_ACCESSORY_NAME = "usb_accessory";
    @UnsupportedAppUsage
    public static final int DEVICE_IN_USB_DEVICE = -2147479552;
    public static final String DEVICE_IN_USB_DEVICE_NAME = "usb_device";
    public static final int DEVICE_IN_USB_HEADSET = -2113929216;
    public static final String DEVICE_IN_USB_HEADSET_NAME = "usb_headset";
    @UnsupportedAppUsage
    public static final int DEVICE_IN_VOICE_CALL = -2147483584;
    @UnsupportedAppUsage
    public static final int DEVICE_IN_WIRED_HEADSET = -2147483632;
    public static final String DEVICE_IN_WIRED_HEADSET_NAME = "headset";
    public static final int DEVICE_NONE = 0;
    public static final int DEVICE_OUT_ALL = 1342177279;
    public static final int DEVICE_OUT_ALL_A2DP = 896;
    public static final int DEVICE_OUT_ALL_HDMI_SYSTEM_AUDIO = 2883584;
    public static final int DEVICE_OUT_ALL_SCO = 112;
    @UnsupportedAppUsage
    public static final int DEVICE_OUT_ALL_USB = 67133440;
    @UnsupportedAppUsage
    public static final int DEVICE_OUT_ANLG_DOCK_HEADSET = 2048;
    public static final String DEVICE_OUT_ANLG_DOCK_HEADSET_NAME = "analog_dock";
    @UnsupportedAppUsage
    public static final int DEVICE_OUT_AUX_DIGITAL = 1024;
    public static final String DEVICE_OUT_AUX_DIGITAL_NAME = "aux_digital";
    public static final int DEVICE_OUT_AUX_LINE = 2097152;
    public static final String DEVICE_OUT_AUX_LINE_NAME = "aux_line";
    @UnsupportedAppUsage
    public static final int DEVICE_OUT_BLUETOOTH_A2DP = 128;
    @UnsupportedAppUsage
    public static final int DEVICE_OUT_BLUETOOTH_A2DP_HEADPHONES = 256;
    public static final String DEVICE_OUT_BLUETOOTH_A2DP_HEADPHONES_NAME = "bt_a2dp_hp";
    public static final String DEVICE_OUT_BLUETOOTH_A2DP_NAME = "bt_a2dp";
    @UnsupportedAppUsage
    public static final int DEVICE_OUT_BLUETOOTH_A2DP_SPEAKER = 512;
    public static final String DEVICE_OUT_BLUETOOTH_A2DP_SPEAKER_NAME = "bt_a2dp_spk";
    @UnsupportedAppUsage
    public static final int DEVICE_OUT_BLUETOOTH_SCO = 16;
    @UnsupportedAppUsage
    public static final int DEVICE_OUT_BLUETOOTH_SCO_CARKIT = 64;
    public static final String DEVICE_OUT_BLUETOOTH_SCO_CARKIT_NAME = "bt_sco_carkit";
    @UnsupportedAppUsage
    public static final int DEVICE_OUT_BLUETOOTH_SCO_HEADSET = 32;
    public static final String DEVICE_OUT_BLUETOOTH_SCO_HEADSET_NAME = "bt_sco_hs";
    public static final String DEVICE_OUT_BLUETOOTH_SCO_NAME = "bt_sco";
    public static final int DEVICE_OUT_BUS = 16777216;
    public static final String DEVICE_OUT_BUS_NAME = "bus";
    public static final int DEVICE_OUT_DEFAULT = 1073741824;
    @UnsupportedAppUsage
    public static final int DEVICE_OUT_DGTL_DOCK_HEADSET = 4096;
    public static final String DEVICE_OUT_DGTL_DOCK_HEADSET_NAME = "digital_dock";
    @UnsupportedAppUsage
    public static final int DEVICE_OUT_EARPIECE = 1;
    public static final String DEVICE_OUT_EARPIECE_NAME = "earpiece";
    @UnsupportedAppUsage
    public static final int DEVICE_OUT_FM = 1048576;
    public static final String DEVICE_OUT_FM_NAME = "fm_transmitter";
    public static final int DEVICE_OUT_HDMI = 1024;
    public static final int DEVICE_OUT_HDMI_ARC = 262144;
    public static final String DEVICE_OUT_HDMI_ARC_NAME = "hmdi_arc";
    public static final String DEVICE_OUT_HDMI_NAME = "hdmi";
    public static final int DEVICE_OUT_HEARING_AID = 134217728;
    public static final String DEVICE_OUT_HEARING_AID_NAME = "hearing_aid_out";
    public static final int DEVICE_OUT_IP = 8388608;
    public static final String DEVICE_OUT_IP_NAME = "ip";
    public static final int DEVICE_OUT_LINE = 131072;
    public static final String DEVICE_OUT_LINE_NAME = "line";
    public static final int DEVICE_OUT_PROXY = 33554432;
    public static final String DEVICE_OUT_PROXY_NAME = "proxy";
    @UnsupportedAppUsage
    public static final int DEVICE_OUT_REMOTE_SUBMIX = 32768;
    public static final String DEVICE_OUT_REMOTE_SUBMIX_NAME = "remote_submix";
    public static final int DEVICE_OUT_SPDIF = 524288;
    public static final String DEVICE_OUT_SPDIF_NAME = "spdif";
    @UnsupportedAppUsage
    public static final int DEVICE_OUT_SPEAKER = 2;
    public static final String DEVICE_OUT_SPEAKER_NAME = "speaker";
    public static final int DEVICE_OUT_SPEAKER_SAFE = 4194304;
    public static final String DEVICE_OUT_SPEAKER_SAFE_NAME = "speaker_safe";
    @UnsupportedAppUsage
    public static final int DEVICE_OUT_TELEPHONY_TX = 65536;
    public static final String DEVICE_OUT_TELEPHONY_TX_NAME = "telephony_tx";
    @UnsupportedAppUsage
    public static final int DEVICE_OUT_USB_ACCESSORY = 8192;
    public static final String DEVICE_OUT_USB_ACCESSORY_NAME = "usb_accessory";
    @UnsupportedAppUsage
    public static final int DEVICE_OUT_USB_DEVICE = 16384;
    public static final String DEVICE_OUT_USB_DEVICE_NAME = "usb_device";
    public static final int DEVICE_OUT_USB_HEADSET = 67108864;
    public static final String DEVICE_OUT_USB_HEADSET_NAME = "usb_headset";
    @UnsupportedAppUsage
    public static final int DEVICE_OUT_WIRED_HEADPHONE = 8;
    public static final String DEVICE_OUT_WIRED_HEADPHONE_NAME = "headphone";
    @UnsupportedAppUsage
    public static final int DEVICE_OUT_WIRED_HEADSET = 4;
    public static final String DEVICE_OUT_WIRED_HEADSET_NAME = "headset";
    @UnsupportedAppUsage
    public static final int DEVICE_STATE_AVAILABLE = 1;
    @UnsupportedAppUsage
    public static final int DEVICE_STATE_UNAVAILABLE = 0;
    private static final int DYNAMIC_POLICY_EVENT_MIX_STATE_UPDATE = 0;
    public static final int ERROR = -1;
    @UnsupportedAppUsage
    public static final int FORCE_ANALOG_DOCK = 8;
    public static final int FORCE_BT_A2DP = 4;
    @UnsupportedAppUsage
    public static final int FORCE_BT_CAR_DOCK = 6;
    @UnsupportedAppUsage
    public static final int FORCE_BT_DESK_DOCK = 7;
    public static final int FORCE_BT_SCO = 3;
    public static final int FORCE_DEFAULT = 0;
    @UnsupportedAppUsage
    public static final int FORCE_DIGITAL_DOCK = 9;
    public static final int FORCE_ENCODED_SURROUND_ALWAYS = 14;
    public static final int FORCE_ENCODED_SURROUND_MANUAL = 15;
    public static final int FORCE_ENCODED_SURROUND_NEVER = 13;
    public static final int FORCE_HDMI_SYSTEM_AUDIO_ENFORCED = 12;
    public static final int FORCE_HEADPHONES = 2;
    @UnsupportedAppUsage
    public static final int FORCE_NONE = 0;
    public static final int FORCE_NO_BT_A2DP = 10;
    public static final int FORCE_SPEAKER = 1;
    public static final int FORCE_SYSTEM_ENFORCED = 11;
    public static final int FORCE_WIRED_ACCESSORY = 5;
    public static final int FOR_COMMUNICATION = 0;
    public static final int FOR_DOCK = 3;
    public static final int FOR_ENCODED_SURROUND = 6;
    public static final int FOR_HDMI_SYSTEM_AUDIO = 5;
    public static final int FOR_MEDIA = 1;
    public static final int FOR_RECORD = 2;
    public static final int FOR_SYSTEM = 4;
    public static final int FOR_VIBRATE_RINGING = 7;
    public static final int INVALID_OPERATION = -3;
    public static final String IN_VOICE_COMM_FOCUS_ID = "AudioFocus_For_Phone_Ring_And_Calls";
    public static final int MODE_CURRENT = -1;
    public static final int MODE_INVALID = -2;
    public static final int MODE_IN_CALL = 2;
    public static final int MODE_IN_COMMUNICATION = 3;
    public static final int MODE_NORMAL = 0;
    public static final int MODE_RINGTONE = 1;
    static final int NATIVE_EVENT_ROUTING_CHANGE = 1000;
    public static final int NO_INIT = -5;
    private static final int NUM_DEVICE_STATES = 1;
    public static final int NUM_FORCE_CONFIG = 16;
    private static final int NUM_FORCE_USE = 8;
    public static final int NUM_MODES = 4;
    public static final int NUM_STREAMS = 5;
    private static final int NUM_STREAM_TYPES = 11;
    public static final int OUT_CHANNEL_COUNT_MAX;
    public static final int PERMISSION_DENIED = -4;
    public static final int PHONE_STATE_INCALL = 2;
    public static final int PHONE_STATE_OFFCALL = 0;
    public static final int PHONE_STATE_RINGING = 1;
    public static final int PLATFORM_DEFAULT = 0;
    public static final int PLATFORM_TELEVISION = 2;
    public static final int PLATFORM_VOICE = 1;
    public static final int PLAY_SOUND_DELAY = 300;
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
    public static final int STREAM_ACCESSIBILITY = 10;
    public static final int STREAM_ALARM = 4;
    public static final int STREAM_BLUETOOTH_SCO = 6;
    public static final int STREAM_DEFAULT = -1;
    public static final int STREAM_DTMF = 8;
    public static final int STREAM_MUSIC = 3;
    public static final String[] STREAM_NAMES;
    public static final int STREAM_NOTIFICATION = 5;
    public static final int STREAM_RING = 2;
    public static final int STREAM_SYSTEM = 1;
    @UnsupportedAppUsage
    public static final int STREAM_SYSTEM_ENFORCED = 7;
    public static final int STREAM_TTS = 9;
    public static final int STREAM_VOICE_CALL = 0;
    public static final int SUCCESS = 0;
    public static final int SYNC_EVENT_NONE = 0;
    public static final int SYNC_EVENT_PRESENTATION_COMPLETE = 1;
    private static final String TAG = "AudioSystem";
    public static final int WOULD_BLOCK = -7;
    private static ErrorCallback mErrorCallback;
    private static DynamicPolicyCallback sDynPolicyCallback;
    private static AudioRecordingCallback sRecordingCallback;

    static {
        OUT_CHANNEL_COUNT_MAX = AudioSystem.native_get_FCC_8();
        STREAM_NAMES = new String[]{"STREAM_VOICE_CALL", "STREAM_SYSTEM", "STREAM_RING", "STREAM_MUSIC", "STREAM_ALARM", "STREAM_NOTIFICATION", "STREAM_BLUETOOTH_SCO", "STREAM_SYSTEM_ENFORCED", "STREAM_DTMF", "STREAM_TTS", "STREAM_ACCESSIBILITY"};
        DEFAULT_STREAM_VOLUME = new int[]{4, 7, 5, 5, 6, 5, 7, 7, 5, 5, 5};
    }

    public static int audioFormatToBluetoothSourceCodec(int n) {
        if (n != 67108864) {
            if (n != 520093696) {
                if (n != 536870912) {
                    if (n != 553648128) {
                        if (n != 587202560) {
                            return 1000000;
                        }
                        return 4;
                    }
                    return 3;
                }
                return 2;
            }
            return 0;
        }
        return 1;
    }

    @UnsupportedAppUsage
    public static native int checkAudioFlinger();

    public static native int createAudioPatch(AudioPatch[] var0, AudioPortConfig[] var1, AudioPortConfig[] var2);

    public static String deviceStateToString(int n) {
        if (n != 0) {
            if (n != 1) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("unknown state (");
                stringBuilder.append(n);
                stringBuilder.append(")");
                return stringBuilder.toString();
            }
            return "DEVICE_STATE_AVAILABLE";
        }
        return "DEVICE_STATE_UNAVAILABLE";
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @UnsupportedAppUsage
    private static void dynamicPolicyCallbackFromNative(int n, String charSequence, int n2) {
        DynamicPolicyCallback dynamicPolicyCallback = null;
        synchronized (AudioSystem.class) {
            if (sDynPolicyCallback != null) {
                dynamicPolicyCallback = sDynPolicyCallback;
            }
        }
        if (dynamicPolicyCallback == null) return;
        if (n != 0) {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("dynamicPolicyCallbackFromNative: unknown event ");
            ((StringBuilder)charSequence).append(n);
            Log.e("AudioSystem", ((StringBuilder)charSequence).toString());
            return;
        }
        dynamicPolicyCallback.onDynamicPolicyMixStateUpdate((String)charSequence, n2);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    @UnsupportedAppUsage
    private static void errorCallbackFromNative(int n) {
        ErrorCallback errorCallback = null;
        // MONITORENTER : android.media.AudioSystem.class
        if (mErrorCallback != null) {
            errorCallback = mErrorCallback;
        }
        // MONITOREXIT : android.media.AudioSystem.class
        if (errorCallback == null) return;
        errorCallback.onError(n);
    }

    public static String forceUseConfigToString(int n) {
        switch (n) {
            default: {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("unknown config (");
                stringBuilder.append(n);
                stringBuilder.append(")");
                return stringBuilder.toString();
            }
            case 15: {
                return "FORCE_ENCODED_SURROUND_MANUAL";
            }
            case 14: {
                return "FORCE_ENCODED_SURROUND_ALWAYS";
            }
            case 13: {
                return "FORCE_ENCODED_SURROUND_NEVER";
            }
            case 12: {
                return "FORCE_HDMI_SYSTEM_AUDIO_ENFORCED";
            }
            case 11: {
                return "FORCE_SYSTEM_ENFORCED";
            }
            case 10: {
                return "FORCE_NO_BT_A2DP";
            }
            case 9: {
                return "FORCE_DIGITAL_DOCK";
            }
            case 8: {
                return "FORCE_ANALOG_DOCK";
            }
            case 7: {
                return "FORCE_BT_DESK_DOCK";
            }
            case 6: {
                return "FORCE_BT_CAR_DOCK";
            }
            case 5: {
                return "FORCE_WIRED_ACCESSORY";
            }
            case 4: {
                return "FORCE_BT_A2DP";
            }
            case 3: {
                return "FORCE_BT_SCO";
            }
            case 2: {
                return "FORCE_HEADPHONES";
            }
            case 1: {
                return "FORCE_SPEAKER";
            }
            case 0: 
        }
        return "FORCE_NONE";
    }

    public static String forceUseUsageToString(int n) {
        switch (n) {
            default: {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("unknown usage (");
                stringBuilder.append(n);
                stringBuilder.append(")");
                return stringBuilder.toString();
            }
            case 7: {
                return "FOR_VIBRATE_RINGING";
            }
            case 6: {
                return "FOR_ENCODED_SURROUND";
            }
            case 5: {
                return "FOR_HDMI_SYSTEM_AUDIO";
            }
            case 4: {
                return "FOR_SYSTEM";
            }
            case 3: {
                return "FOR_DOCK";
            }
            case 2: {
                return "FOR_RECORD";
            }
            case 1: {
                return "FOR_MEDIA";
            }
            case 0: 
        }
        return "FOR_COMMUNICATION";
    }

    public static native int getAudioHwSyncForSession(int var0);

    public static int getDefaultStreamVolume(int n) {
        return DEFAULT_STREAM_VOLUME[n];
    }

    @UnsupportedAppUsage
    public static native int getDeviceConnectionState(int var0, String var1);

    @UnsupportedAppUsage
    public static native int getDevicesForStream(int var0);

    @UnsupportedAppUsage
    public static native int getForceUse(int var0);

    public static native int getHwOffloadEncodingFormatsSupportedForA2DP(ArrayList<Integer> var0);

    public static String getInputDeviceName(int n) {
        switch (n) {
            default: {
                return Integer.toString(n);
            }
            case -1879048192: {
                return "echo_reference";
            }
            case -2013265920: {
                return "hdmi_arc";
            }
            case -2080374784: {
                return "bt_ble";
            }
            case -2113929216: {
                return "usb_headset";
            }
            case -2130706432: {
                return "proxy";
            }
            case -2146435072: {
                return "bus";
            }
            case -2146959360: {
                return "ip";
            }
            case -2147221504: {
                return "loopback";
            }
            case -2147352576: {
                return "bt_a2dp";
            }
            case -2147418112: {
                return "spdif";
            }
            case -2147450880: {
                return "line";
            }
            case -2147467264: {
                return "tv_tuner";
            }
            case -2147475456: {
                return "fm_tuner";
            }
            case -2147479552: {
                return "usb_device";
            }
            case -2147481600: {
                return "usb_accessory";
            }
            case -2147482624: {
                return "digital_dock";
            }
            case -2147483136: {
                return "analog_dock";
            }
            case -2147483392: {
                return "remote_submix";
            }
            case -2147483520: {
                return "back_mic";
            }
            case -2147483584: {
                return "telephony_rx";
            }
            case -2147483616: {
                return "aux_digital";
            }
            case -2147483632: {
                return "headset";
            }
            case -2147483640: {
                return "bt_sco_hs";
            }
            case -2147483644: {
                return "mic";
            }
            case -2147483646: {
                return "ambient";
            }
            case -2147483647: 
        }
        return "communication";
    }

    public static native float getMasterBalance();

    public static native boolean getMasterMono();

    @UnsupportedAppUsage
    public static native boolean getMasterMute();

    public static native float getMasterVolume();

    public static native int getMaxVolumeIndexForAttributes(AudioAttributes var0);

    public static native int getMicrophones(ArrayList<MicrophoneInfo> var0);

    public static native int getMinVolumeIndexForAttributes(AudioAttributes var0);

    @UnsupportedAppUsage
    public static final int getNumStreamTypes() {
        return 11;
    }

    @UnsupportedAppUsage
    public static String getOutputDeviceName(int n) {
        if (n != 1) {
            if (n != 2) {
                switch (n) {
                    default: {
                        return Integer.toString(n);
                    }
                    case 134217728: {
                        return "hearing_aid_out";
                    }
                    case 67108864: {
                        return "usb_headset";
                    }
                    case 33554432: {
                        return "proxy";
                    }
                    case 16777216: {
                        return "bus";
                    }
                    case 8388608: {
                        return "ip";
                    }
                    case 4194304: {
                        return "speaker_safe";
                    }
                    case 2097152: {
                        return "aux_line";
                    }
                    case 1048576: {
                        return "fm_transmitter";
                    }
                    case 524288: {
                        return "spdif";
                    }
                    case 262144: {
                        return "hmdi_arc";
                    }
                    case 131072: {
                        return "line";
                    }
                    case 65536: {
                        return "telephony_tx";
                    }
                    case 32768: {
                        return "remote_submix";
                    }
                    case 16384: {
                        return "usb_device";
                    }
                    case 8192: {
                        return "usb_accessory";
                    }
                    case 4096: {
                        return "digital_dock";
                    }
                    case 2048: {
                        return "analog_dock";
                    }
                    case 1024: {
                        return "hdmi";
                    }
                    case 512: {
                        return "bt_a2dp_spk";
                    }
                    case 256: {
                        return "bt_a2dp_hp";
                    }
                    case 128: {
                        return "bt_a2dp";
                    }
                    case 64: {
                        return "bt_sco_carkit";
                    }
                    case 32: {
                        return "bt_sco_hs";
                    }
                    case 16: {
                        return "bt_sco";
                    }
                    case 8: {
                        return "headphone";
                    }
                    case 4: 
                }
                return "headset";
            }
            return "speaker";
        }
        return "earpiece";
    }

    @UnsupportedAppUsage
    public static native int getOutputLatency(int var0);

    @UnsupportedAppUsage
    public static native String getParameters(String var0);

    public static int getPlatformType(Context context) {
        if (context.getResources().getBoolean(17891570)) {
            return 1;
        }
        if (context.getPackageManager().hasSystemFeature("android.software.leanback")) {
            return 2;
        }
        return 0;
    }

    @UnsupportedAppUsage(trackingBug=134049522L)
    public static native int getPrimaryOutputFrameCount();

    @UnsupportedAppUsage(trackingBug=134049522L)
    public static native int getPrimaryOutputSamplingRate();

    public static native float getStreamVolumeDB(int var0, int var1, int var2);

    public static native int getStreamVolumeIndex(int var0, int var1);

    public static native int getSurroundFormats(Map<Integer, Boolean> var0, boolean var1);

    public static int getValueForVibrateSetting(int n, int n2, int n3) {
        return n & 3 << n2 * 2 | (n3 & 3) << n2 * 2;
    }

    public static native int getVolumeIndexForAttributes(AudioAttributes var0, int var1);

    public static native int handleDeviceConfigChange(int var0, String var1, String var2, int var3);

    @UnsupportedAppUsage
    public static native int initStreamVolume(int var0, int var1, int var2);

    public static native boolean isHapticPlaybackSupported();

    @UnsupportedAppUsage
    public static native boolean isMicrophoneMuted();

    static boolean isOffloadSupported(AudioFormat audioFormat, AudioAttributes audioAttributes) {
        return AudioSystem.native_is_offload_supported(audioFormat.getEncoding(), audioFormat.getSampleRate(), audioFormat.getChannelMask(), audioFormat.getChannelIndexMask(), audioAttributes.getVolumeControlStream());
    }

    public static boolean isSingleVolume(Context context) {
        boolean bl = context.getResources().getBoolean(17891516);
        bl = AudioSystem.getPlatformType(context) == 2 || bl;
        return bl;
    }

    @UnsupportedAppUsage
    public static native boolean isSourceActive(int var0);

    @UnsupportedAppUsage
    public static native boolean isStreamActive(int var0, int var1);

    public static native boolean isStreamActiveRemotely(int var0, int var1);

    public static native int listAudioPatches(ArrayList<AudioPatch> var0, int[] var1);

    public static native int listAudioPorts(ArrayList<AudioPort> var0, int[] var1);

    public static String modeToString(int n) {
        if (n != -2) {
            if (n != -1) {
                if (n != 0) {
                    if (n != 1) {
                        if (n != 2) {
                            if (n != 3) {
                                StringBuilder stringBuilder = new StringBuilder();
                                stringBuilder.append("unknown mode (");
                                stringBuilder.append(n);
                                stringBuilder.append(")");
                                return stringBuilder.toString();
                            }
                            return "MODE_IN_COMMUNICATION";
                        }
                        return "MODE_IN_CALL";
                    }
                    return "MODE_RINGTONE";
                }
                return "MODE_NORMAL";
            }
            return "MODE_CURRENT";
        }
        return "MODE_INVALID";
    }

    @UnsupportedAppUsage
    public static native int muteMicrophone(boolean var0);

    private static native int native_get_FCC_8();

    private static native boolean native_is_offload_supported(int var0, int var1, int var2, int var3, int var4);

    private static final native void native_register_dynamic_policy_callback();

    private static final native void native_register_recording_callback();

    public static native int newAudioPlayerId();

    public static native int newAudioRecorderId();

    public static native int newAudioSessionId();

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    @UnsupportedAppUsage
    private static void recordingCallbackFromNative(int n, int n2, int n3, int n4, int n5, int n6, boolean bl, int[] arrn, AudioEffect.Descriptor[] arrdescriptor, AudioEffect.Descriptor[] arrdescriptor2, int n7) {
        String string2;
        // MONITORENTER : android.media.AudioSystem.class
        AudioRecordingCallback audioRecordingCallback = sRecordingCallback;
        if (arrdescriptor.length != 0) {
            string2 = arrdescriptor[0].name;
        }
        string2 = arrdescriptor2.length == 0 ? "None" : arrdescriptor2[0].name;
        if (audioRecordingCallback == null) return;
        audioRecordingCallback.onRecordingConfigurationChanged(n, n2, n3, n4, n5, n6, bl, arrn, arrdescriptor, arrdescriptor2, n7, "");
    }

    public static native int registerPolicyMixes(ArrayList<AudioMix> var0, boolean var1);

    public static native int releaseAudioPatch(AudioPatch var0);

    public static native int removeUidDeviceAffinities(int var0);

    public static native int setA11yServicesUids(int[] var0);

    public static native int setAllowedCapturePolicy(int var0, int var1);

    public static native int setAssistantUid(int var0);

    public static native int setAudioPortConfig(AudioPortConfig var0);

    @UnsupportedAppUsage
    public static native int setDeviceConnectionState(int var0, int var1, String var2, String var3, int var4);

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static void setDynamicPolicyCallback(DynamicPolicyCallback dynamicPolicyCallback) {
        synchronized (AudioSystem.class) {
            sDynPolicyCallback = dynamicPolicyCallback;
            AudioSystem.native_register_dynamic_policy_callback();
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @UnsupportedAppUsage
    public static void setErrorCallback(ErrorCallback errorCallback) {
        synchronized (AudioSystem.class) {
            mErrorCallback = errorCallback;
            if (errorCallback != null) {
                errorCallback.onError(AudioSystem.checkAudioFlinger());
            }
            return;
        }
    }

    @UnsupportedAppUsage
    public static native int setForceUse(int var0, int var1);

    public static native int setLowRamDevice(boolean var0, long var1);

    public static native int setMasterBalance(float var0);

    public static native int setMasterMono(boolean var0);

    @UnsupportedAppUsage
    public static native int setMasterMute(boolean var0);

    public static native int setMasterVolume(float var0);

    @UnsupportedAppUsage
    public static native int setParameters(String var0);

    @UnsupportedAppUsage
    public static native int setPhoneState(int var0);

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static void setRecordingCallback(AudioRecordingCallback audioRecordingCallback) {
        synchronized (AudioSystem.class) {
            sRecordingCallback = audioRecordingCallback;
            AudioSystem.native_register_recording_callback();
            return;
        }
    }

    public static native int setRttEnabled(boolean var0);

    @UnsupportedAppUsage
    private static native int setStreamVolumeIndex(int var0, int var1, int var2);

    public static int setStreamVolumeIndexAS(int n, int n2, int n3) {
        return AudioSystem.setStreamVolumeIndex(n, n2, n3);
    }

    public static native int setSurroundFormatEnabled(int var0, boolean var1);

    public static native int setUidDeviceAffinities(int var0, int[] var1, String[] var2);

    public static native int setVolumeIndexForAttributes(AudioAttributes var0, int var1, int var2);

    public static native int startAudioSource(AudioPortConfig var0, AudioAttributes var1);

    public static native int stopAudioSource(int var0);

    public static String streamToString(int n) {
        Object object;
        if (n >= 0 && n < ((String[])(object = STREAM_NAMES)).length) {
            return object[n];
        }
        if (n == Integer.MIN_VALUE) {
            return "USE_DEFAULT_STREAM_TYPE";
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("UNKNOWN_STREAM_");
        ((StringBuilder)object).append(n);
        return ((StringBuilder)object).toString();
    }

    public static native int systemReady();

    public static interface AudioRecordingCallback {
        public void onRecordingConfigurationChanged(int var1, int var2, int var3, int var4, int var5, int var6, boolean var7, int[] var8, AudioEffect.Descriptor[] var9, AudioEffect.Descriptor[] var10, int var11, String var12);
    }

    public static interface DynamicPolicyCallback {
        public void onDynamicPolicyMixStateUpdate(String var1, int var2);
    }

    public static interface ErrorCallback {
        public void onError(int var1);
    }

}

