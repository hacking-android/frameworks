/*
 * Decompiled with CFR 0.145.
 */
package android.view;

import android.annotation.UnsupportedAppUsage;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.SparseIntArray;
import android.view.InputEvent;
import android.view.KeyCharacterMap;

public class KeyEvent
extends InputEvent
implements Parcelable {
    public static final int ACTION_DOWN = 0;
    @Deprecated
    public static final int ACTION_MULTIPLE = 2;
    public static final int ACTION_UP = 1;
    public static final Parcelable.Creator<KeyEvent> CREATOR;
    static final boolean DEBUG = false;
    public static final int FLAG_CANCELED = 32;
    public static final int FLAG_CANCELED_LONG_PRESS = 256;
    public static final int FLAG_EDITOR_ACTION = 16;
    public static final int FLAG_FALLBACK = 1024;
    public static final int FLAG_FROM_SYSTEM = 8;
    public static final int FLAG_KEEP_TOUCH_MODE = 4;
    public static final int FLAG_LONG_PRESS = 128;
    public static final int FLAG_PREDISPATCH = 536870912;
    public static final int FLAG_SOFT_KEYBOARD = 2;
    public static final int FLAG_START_TRACKING = 1073741824;
    public static final int FLAG_TAINTED = Integer.MIN_VALUE;
    public static final int FLAG_TRACKING = 512;
    public static final int FLAG_VIRTUAL_HARD_KEY = 64;
    @Deprecated
    public static final int FLAG_WOKE_HERE = 1;
    public static final int KEYCODE_0 = 7;
    public static final int KEYCODE_1 = 8;
    public static final int KEYCODE_11 = 227;
    public static final int KEYCODE_12 = 228;
    public static final int KEYCODE_2 = 9;
    public static final int KEYCODE_3 = 10;
    public static final int KEYCODE_3D_MODE = 206;
    public static final int KEYCODE_4 = 11;
    public static final int KEYCODE_5 = 12;
    public static final int KEYCODE_6 = 13;
    public static final int KEYCODE_7 = 14;
    public static final int KEYCODE_8 = 15;
    public static final int KEYCODE_9 = 16;
    public static final int KEYCODE_A = 29;
    public static final int KEYCODE_ALL_APPS = 284;
    public static final int KEYCODE_ALT_LEFT = 57;
    public static final int KEYCODE_ALT_RIGHT = 58;
    public static final int KEYCODE_APOSTROPHE = 75;
    public static final int KEYCODE_APP_SWITCH = 187;
    public static final int KEYCODE_ASSIST = 219;
    public static final int KEYCODE_AT = 77;
    public static final int KEYCODE_AVR_INPUT = 182;
    public static final int KEYCODE_AVR_POWER = 181;
    public static final int KEYCODE_B = 30;
    public static final int KEYCODE_BACK = 4;
    public static final int KEYCODE_BACKSLASH = 73;
    public static final int KEYCODE_BOOKMARK = 174;
    public static final int KEYCODE_BREAK = 121;
    public static final int KEYCODE_BRIGHTNESS_DOWN = 220;
    public static final int KEYCODE_BRIGHTNESS_UP = 221;
    public static final int KEYCODE_BUTTON_1 = 188;
    public static final int KEYCODE_BUTTON_10 = 197;
    public static final int KEYCODE_BUTTON_11 = 198;
    public static final int KEYCODE_BUTTON_12 = 199;
    public static final int KEYCODE_BUTTON_13 = 200;
    public static final int KEYCODE_BUTTON_14 = 201;
    public static final int KEYCODE_BUTTON_15 = 202;
    public static final int KEYCODE_BUTTON_16 = 203;
    public static final int KEYCODE_BUTTON_2 = 189;
    public static final int KEYCODE_BUTTON_3 = 190;
    public static final int KEYCODE_BUTTON_4 = 191;
    public static final int KEYCODE_BUTTON_5 = 192;
    public static final int KEYCODE_BUTTON_6 = 193;
    public static final int KEYCODE_BUTTON_7 = 194;
    public static final int KEYCODE_BUTTON_8 = 195;
    public static final int KEYCODE_BUTTON_9 = 196;
    public static final int KEYCODE_BUTTON_A = 96;
    public static final int KEYCODE_BUTTON_B = 97;
    public static final int KEYCODE_BUTTON_C = 98;
    public static final int KEYCODE_BUTTON_L1 = 102;
    public static final int KEYCODE_BUTTON_L2 = 104;
    public static final int KEYCODE_BUTTON_MODE = 110;
    public static final int KEYCODE_BUTTON_R1 = 103;
    public static final int KEYCODE_BUTTON_R2 = 105;
    public static final int KEYCODE_BUTTON_SELECT = 109;
    public static final int KEYCODE_BUTTON_START = 108;
    public static final int KEYCODE_BUTTON_THUMBL = 106;
    public static final int KEYCODE_BUTTON_THUMBR = 107;
    public static final int KEYCODE_BUTTON_X = 99;
    public static final int KEYCODE_BUTTON_Y = 100;
    public static final int KEYCODE_BUTTON_Z = 101;
    public static final int KEYCODE_C = 31;
    public static final int KEYCODE_CALCULATOR = 210;
    public static final int KEYCODE_CALENDAR = 208;
    public static final int KEYCODE_CALL = 5;
    public static final int KEYCODE_CAMERA = 27;
    public static final int KEYCODE_CAPS_LOCK = 115;
    public static final int KEYCODE_CAPTIONS = 175;
    public static final int KEYCODE_CHANNEL_DOWN = 167;
    public static final int KEYCODE_CHANNEL_UP = 166;
    public static final int KEYCODE_CLEAR = 28;
    public static final int KEYCODE_COMMA = 55;
    public static final int KEYCODE_CONTACTS = 207;
    public static final int KEYCODE_COPY = 278;
    public static final int KEYCODE_CTRL_LEFT = 113;
    public static final int KEYCODE_CTRL_RIGHT = 114;
    public static final int KEYCODE_CUT = 277;
    public static final int KEYCODE_D = 32;
    public static final int KEYCODE_DEL = 67;
    public static final int KEYCODE_DPAD_CENTER = 23;
    public static final int KEYCODE_DPAD_DOWN = 20;
    public static final int KEYCODE_DPAD_DOWN_LEFT = 269;
    public static final int KEYCODE_DPAD_DOWN_RIGHT = 271;
    public static final int KEYCODE_DPAD_LEFT = 21;
    public static final int KEYCODE_DPAD_RIGHT = 22;
    public static final int KEYCODE_DPAD_UP = 19;
    public static final int KEYCODE_DPAD_UP_LEFT = 268;
    public static final int KEYCODE_DPAD_UP_RIGHT = 270;
    public static final int KEYCODE_DVR = 173;
    public static final int KEYCODE_E = 33;
    public static final int KEYCODE_EISU = 212;
    public static final int KEYCODE_ENDCALL = 6;
    public static final int KEYCODE_ENTER = 66;
    public static final int KEYCODE_ENVELOPE = 65;
    public static final int KEYCODE_EQUALS = 70;
    public static final int KEYCODE_ESCAPE = 111;
    public static final int KEYCODE_EXPLORER = 64;
    public static final int KEYCODE_F = 34;
    public static final int KEYCODE_F1 = 131;
    public static final int KEYCODE_F10 = 140;
    public static final int KEYCODE_F11 = 141;
    public static final int KEYCODE_F12 = 142;
    public static final int KEYCODE_F2 = 132;
    public static final int KEYCODE_F3 = 133;
    public static final int KEYCODE_F4 = 134;
    public static final int KEYCODE_F5 = 135;
    public static final int KEYCODE_F6 = 136;
    public static final int KEYCODE_F7 = 137;
    public static final int KEYCODE_F8 = 138;
    public static final int KEYCODE_F9 = 139;
    public static final int KEYCODE_FOCUS = 80;
    public static final int KEYCODE_FORWARD = 125;
    public static final int KEYCODE_FORWARD_DEL = 112;
    public static final int KEYCODE_FUNCTION = 119;
    public static final int KEYCODE_G = 35;
    public static final int KEYCODE_GRAVE = 68;
    public static final int KEYCODE_GUIDE = 172;
    public static final int KEYCODE_H = 36;
    public static final int KEYCODE_HEADSETHOOK = 79;
    public static final int KEYCODE_HELP = 259;
    public static final int KEYCODE_HENKAN = 214;
    public static final int KEYCODE_HOME = 3;
    public static final int KEYCODE_I = 37;
    public static final int KEYCODE_INFO = 165;
    public static final int KEYCODE_INSERT = 124;
    public static final int KEYCODE_J = 38;
    public static final int KEYCODE_K = 39;
    public static final int KEYCODE_KANA = 218;
    public static final int KEYCODE_KATAKANA_HIRAGANA = 215;
    public static final int KEYCODE_L = 40;
    public static final int KEYCODE_LANGUAGE_SWITCH = 204;
    public static final int KEYCODE_LAST_CHANNEL = 229;
    public static final int KEYCODE_LEFT_BRACKET = 71;
    public static final int KEYCODE_M = 41;
    public static final int KEYCODE_MANNER_MODE = 205;
    public static final int KEYCODE_MEDIA_AUDIO_TRACK = 222;
    public static final int KEYCODE_MEDIA_CLOSE = 128;
    public static final int KEYCODE_MEDIA_EJECT = 129;
    public static final int KEYCODE_MEDIA_FAST_FORWARD = 90;
    public static final int KEYCODE_MEDIA_NEXT = 87;
    public static final int KEYCODE_MEDIA_PAUSE = 127;
    public static final int KEYCODE_MEDIA_PLAY = 126;
    public static final int KEYCODE_MEDIA_PLAY_PAUSE = 85;
    public static final int KEYCODE_MEDIA_PREVIOUS = 88;
    public static final int KEYCODE_MEDIA_RECORD = 130;
    public static final int KEYCODE_MEDIA_REWIND = 89;
    public static final int KEYCODE_MEDIA_SKIP_BACKWARD = 273;
    public static final int KEYCODE_MEDIA_SKIP_FORWARD = 272;
    public static final int KEYCODE_MEDIA_STEP_BACKWARD = 275;
    public static final int KEYCODE_MEDIA_STEP_FORWARD = 274;
    public static final int KEYCODE_MEDIA_STOP = 86;
    public static final int KEYCODE_MEDIA_TOP_MENU = 226;
    public static final int KEYCODE_MENU = 82;
    public static final int KEYCODE_META_LEFT = 117;
    public static final int KEYCODE_META_RIGHT = 118;
    public static final int KEYCODE_MINUS = 69;
    public static final int KEYCODE_MOVE_END = 123;
    public static final int KEYCODE_MOVE_HOME = 122;
    public static final int KEYCODE_MUHENKAN = 213;
    public static final int KEYCODE_MUSIC = 209;
    public static final int KEYCODE_MUTE = 91;
    public static final int KEYCODE_N = 42;
    public static final int KEYCODE_NAVIGATE_IN = 262;
    public static final int KEYCODE_NAVIGATE_NEXT = 261;
    public static final int KEYCODE_NAVIGATE_OUT = 263;
    public static final int KEYCODE_NAVIGATE_PREVIOUS = 260;
    public static final int KEYCODE_NOTIFICATION = 83;
    public static final int KEYCODE_NUM = 78;
    public static final int KEYCODE_NUMPAD_0 = 144;
    public static final int KEYCODE_NUMPAD_1 = 145;
    public static final int KEYCODE_NUMPAD_2 = 146;
    public static final int KEYCODE_NUMPAD_3 = 147;
    public static final int KEYCODE_NUMPAD_4 = 148;
    public static final int KEYCODE_NUMPAD_5 = 149;
    public static final int KEYCODE_NUMPAD_6 = 150;
    public static final int KEYCODE_NUMPAD_7 = 151;
    public static final int KEYCODE_NUMPAD_8 = 152;
    public static final int KEYCODE_NUMPAD_9 = 153;
    public static final int KEYCODE_NUMPAD_ADD = 157;
    public static final int KEYCODE_NUMPAD_COMMA = 159;
    public static final int KEYCODE_NUMPAD_DIVIDE = 154;
    public static final int KEYCODE_NUMPAD_DOT = 158;
    public static final int KEYCODE_NUMPAD_ENTER = 160;
    public static final int KEYCODE_NUMPAD_EQUALS = 161;
    public static final int KEYCODE_NUMPAD_LEFT_PAREN = 162;
    public static final int KEYCODE_NUMPAD_MULTIPLY = 155;
    public static final int KEYCODE_NUMPAD_RIGHT_PAREN = 163;
    public static final int KEYCODE_NUMPAD_SUBTRACT = 156;
    public static final int KEYCODE_NUM_LOCK = 143;
    public static final int KEYCODE_O = 43;
    public static final int KEYCODE_P = 44;
    public static final int KEYCODE_PAGE_DOWN = 93;
    public static final int KEYCODE_PAGE_UP = 92;
    public static final int KEYCODE_PAIRING = 225;
    public static final int KEYCODE_PASTE = 279;
    public static final int KEYCODE_PERIOD = 56;
    public static final int KEYCODE_PICTSYMBOLS = 94;
    public static final int KEYCODE_PLUS = 81;
    public static final int KEYCODE_POUND = 18;
    public static final int KEYCODE_POWER = 26;
    public static final int KEYCODE_PROFILE_SWITCH = 288;
    public static final int KEYCODE_PROG_BLUE = 186;
    public static final int KEYCODE_PROG_GREEN = 184;
    public static final int KEYCODE_PROG_RED = 183;
    public static final int KEYCODE_PROG_YELLOW = 185;
    public static final int KEYCODE_Q = 45;
    public static final int KEYCODE_R = 46;
    public static final int KEYCODE_REFRESH = 285;
    public static final int KEYCODE_RIGHT_BRACKET = 72;
    public static final int KEYCODE_RO = 217;
    public static final int KEYCODE_S = 47;
    public static final int KEYCODE_SCROLL_LOCK = 116;
    public static final int KEYCODE_SEARCH = 84;
    public static final int KEYCODE_SEMICOLON = 74;
    public static final int KEYCODE_SETTINGS = 176;
    public static final int KEYCODE_SHIFT_LEFT = 59;
    public static final int KEYCODE_SHIFT_RIGHT = 60;
    public static final int KEYCODE_SLASH = 76;
    public static final int KEYCODE_SLEEP = 223;
    public static final int KEYCODE_SOFT_LEFT = 1;
    public static final int KEYCODE_SOFT_RIGHT = 2;
    public static final int KEYCODE_SOFT_SLEEP = 276;
    public static final int KEYCODE_SPACE = 62;
    public static final int KEYCODE_STAR = 17;
    public static final int KEYCODE_STB_INPUT = 180;
    public static final int KEYCODE_STB_POWER = 179;
    public static final int KEYCODE_STEM_1 = 265;
    public static final int KEYCODE_STEM_2 = 266;
    public static final int KEYCODE_STEM_3 = 267;
    public static final int KEYCODE_STEM_PRIMARY = 264;
    public static final int KEYCODE_SWITCH_CHARSET = 95;
    public static final int KEYCODE_SYM = 63;
    public static final int KEYCODE_SYSRQ = 120;
    public static final int KEYCODE_SYSTEM_NAVIGATION_DOWN = 281;
    public static final int KEYCODE_SYSTEM_NAVIGATION_LEFT = 282;
    public static final int KEYCODE_SYSTEM_NAVIGATION_RIGHT = 283;
    public static final int KEYCODE_SYSTEM_NAVIGATION_UP = 280;
    public static final int KEYCODE_T = 48;
    public static final int KEYCODE_TAB = 61;
    public static final int KEYCODE_THUMBS_DOWN = 287;
    public static final int KEYCODE_THUMBS_UP = 286;
    public static final int KEYCODE_TV = 170;
    public static final int KEYCODE_TV_ANTENNA_CABLE = 242;
    public static final int KEYCODE_TV_AUDIO_DESCRIPTION = 252;
    public static final int KEYCODE_TV_AUDIO_DESCRIPTION_MIX_DOWN = 254;
    public static final int KEYCODE_TV_AUDIO_DESCRIPTION_MIX_UP = 253;
    public static final int KEYCODE_TV_CONTENTS_MENU = 256;
    public static final int KEYCODE_TV_DATA_SERVICE = 230;
    public static final int KEYCODE_TV_INPUT = 178;
    public static final int KEYCODE_TV_INPUT_COMPONENT_1 = 249;
    public static final int KEYCODE_TV_INPUT_COMPONENT_2 = 250;
    public static final int KEYCODE_TV_INPUT_COMPOSITE_1 = 247;
    public static final int KEYCODE_TV_INPUT_COMPOSITE_2 = 248;
    public static final int KEYCODE_TV_INPUT_HDMI_1 = 243;
    public static final int KEYCODE_TV_INPUT_HDMI_2 = 244;
    public static final int KEYCODE_TV_INPUT_HDMI_3 = 245;
    public static final int KEYCODE_TV_INPUT_HDMI_4 = 246;
    public static final int KEYCODE_TV_INPUT_VGA_1 = 251;
    public static final int KEYCODE_TV_MEDIA_CONTEXT_MENU = 257;
    public static final int KEYCODE_TV_NETWORK = 241;
    public static final int KEYCODE_TV_NUMBER_ENTRY = 234;
    public static final int KEYCODE_TV_POWER = 177;
    public static final int KEYCODE_TV_RADIO_SERVICE = 232;
    public static final int KEYCODE_TV_SATELLITE = 237;
    public static final int KEYCODE_TV_SATELLITE_BS = 238;
    public static final int KEYCODE_TV_SATELLITE_CS = 239;
    public static final int KEYCODE_TV_SATELLITE_SERVICE = 240;
    public static final int KEYCODE_TV_TELETEXT = 233;
    public static final int KEYCODE_TV_TERRESTRIAL_ANALOG = 235;
    public static final int KEYCODE_TV_TERRESTRIAL_DIGITAL = 236;
    public static final int KEYCODE_TV_TIMER_PROGRAMMING = 258;
    public static final int KEYCODE_TV_ZOOM_MODE = 255;
    public static final int KEYCODE_U = 49;
    public static final int KEYCODE_UNKNOWN = 0;
    public static final int KEYCODE_V = 50;
    public static final int KEYCODE_VOICE_ASSIST = 231;
    public static final int KEYCODE_VOLUME_DOWN = 25;
    public static final int KEYCODE_VOLUME_MUTE = 164;
    public static final int KEYCODE_VOLUME_UP = 24;
    public static final int KEYCODE_W = 51;
    public static final int KEYCODE_WAKEUP = 224;
    public static final int KEYCODE_WINDOW = 171;
    public static final int KEYCODE_X = 52;
    public static final int KEYCODE_Y = 53;
    public static final int KEYCODE_YEN = 216;
    public static final int KEYCODE_Z = 54;
    public static final int KEYCODE_ZENKAKU_HANKAKU = 211;
    public static final int KEYCODE_ZOOM_IN = 168;
    public static final int KEYCODE_ZOOM_OUT = 169;
    private static final String LABEL_PREFIX = "KEYCODE_";
    public static final int LAST_KEYCODE = 288;
    @Deprecated
    public static final int MAX_KEYCODE = 84;
    private static final int MAX_RECYCLED = 10;
    @UnsupportedAppUsage
    private static final int META_ALL_MASK = 7827711;
    public static final int META_ALT_LEFT_ON = 16;
    @UnsupportedAppUsage
    public static final int META_ALT_LOCKED = 512;
    public static final int META_ALT_MASK = 50;
    public static final int META_ALT_ON = 2;
    public static final int META_ALT_RIGHT_ON = 32;
    public static final int META_CAPS_LOCK_ON = 1048576;
    @UnsupportedAppUsage
    public static final int META_CAP_LOCKED = 256;
    public static final int META_CTRL_LEFT_ON = 8192;
    public static final int META_CTRL_MASK = 28672;
    public static final int META_CTRL_ON = 4096;
    public static final int META_CTRL_RIGHT_ON = 16384;
    public static final int META_FUNCTION_ON = 8;
    @UnsupportedAppUsage
    private static final int META_INVALID_MODIFIER_MASK = 7343872;
    @UnsupportedAppUsage
    private static final int META_LOCK_MASK = 7340032;
    public static final int META_META_LEFT_ON = 131072;
    public static final int META_META_MASK = 458752;
    public static final int META_META_ON = 65536;
    public static final int META_META_RIGHT_ON = 262144;
    @UnsupportedAppUsage
    private static final int META_MODIFIER_MASK = 487679;
    public static final int META_NUM_LOCK_ON = 2097152;
    public static final int META_SCROLL_LOCK_ON = 4194304;
    @UnsupportedAppUsage
    public static final int META_SELECTING = 2048;
    public static final int META_SHIFT_LEFT_ON = 64;
    public static final int META_SHIFT_MASK = 193;
    public static final int META_SHIFT_ON = 1;
    public static final int META_SHIFT_RIGHT_ON = 128;
    @UnsupportedAppUsage
    private static final String[] META_SYMBOLIC_NAMES;
    @UnsupportedAppUsage
    public static final int META_SYM_LOCKED = 1024;
    public static final int META_SYM_ON = 4;
    @UnsupportedAppUsage
    private static final int META_SYNTHETIC_MASK = 3840;
    static final String TAG = "KeyEvent";
    private static final Object gRecyclerLock;
    private static KeyEvent gRecyclerTop;
    private static int gRecyclerUsed;
    @UnsupportedAppUsage
    private int mAction;
    @UnsupportedAppUsage
    private String mCharacters;
    @UnsupportedAppUsage
    private int mDeviceId;
    private int mDisplayId;
    @UnsupportedAppUsage
    private long mDownTime;
    @UnsupportedAppUsage
    private long mEventTime;
    @UnsupportedAppUsage
    private int mFlags;
    @UnsupportedAppUsage
    private int mKeyCode;
    @UnsupportedAppUsage
    private int mMetaState;
    private KeyEvent mNext;
    @UnsupportedAppUsage
    private int mRepeatCount;
    @UnsupportedAppUsage
    private int mScanCode;
    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    private int mSource;

    static {
        META_SYMBOLIC_NAMES = new String[]{"META_SHIFT_ON", "META_ALT_ON", "META_SYM_ON", "META_FUNCTION_ON", "META_ALT_LEFT_ON", "META_ALT_RIGHT_ON", "META_SHIFT_LEFT_ON", "META_SHIFT_RIGHT_ON", "META_CAP_LOCKED", "META_ALT_LOCKED", "META_SYM_LOCKED", "0x00000800", "META_CTRL_ON", "META_CTRL_LEFT_ON", "META_CTRL_RIGHT_ON", "0x00008000", "META_META_ON", "META_META_LEFT_ON", "META_META_RIGHT_ON", "0x00080000", "META_CAPS_LOCK_ON", "META_NUM_LOCK_ON", "META_SCROLL_LOCK_ON", "0x00800000", "0x01000000", "0x02000000", "0x04000000", "0x08000000", "0x10000000", "0x20000000", "0x40000000", "0x80000000"};
        gRecyclerLock = new Object();
        CREATOR = new Parcelable.Creator<KeyEvent>(){

            @Override
            public KeyEvent createFromParcel(Parcel parcel) {
                parcel.readInt();
                return KeyEvent.createFromParcelBody(parcel);
            }

            public KeyEvent[] newArray(int n) {
                return new KeyEvent[n];
            }
        };
    }

    private KeyEvent() {
    }

    public KeyEvent(int n, int n2) {
        this.mAction = n;
        this.mKeyCode = n2;
        this.mRepeatCount = 0;
        this.mDeviceId = -1;
    }

    public KeyEvent(long l, long l2, int n, int n2, int n3) {
        this.mDownTime = l;
        this.mEventTime = l2;
        this.mAction = n;
        this.mKeyCode = n2;
        this.mRepeatCount = n3;
        this.mDeviceId = -1;
    }

    public KeyEvent(long l, long l2, int n, int n2, int n3, int n4) {
        this.mDownTime = l;
        this.mEventTime = l2;
        this.mAction = n;
        this.mKeyCode = n2;
        this.mRepeatCount = n3;
        this.mMetaState = n4;
        this.mDeviceId = -1;
    }

    public KeyEvent(long l, long l2, int n, int n2, int n3, int n4, int n5, int n6) {
        this.mDownTime = l;
        this.mEventTime = l2;
        this.mAction = n;
        this.mKeyCode = n2;
        this.mRepeatCount = n3;
        this.mMetaState = n4;
        this.mDeviceId = n5;
        this.mScanCode = n6;
    }

    public KeyEvent(long l, long l2, int n, int n2, int n3, int n4, int n5, int n6, int n7) {
        this.mDownTime = l;
        this.mEventTime = l2;
        this.mAction = n;
        this.mKeyCode = n2;
        this.mRepeatCount = n3;
        this.mMetaState = n4;
        this.mDeviceId = n5;
        this.mScanCode = n6;
        this.mFlags = n7;
    }

    public KeyEvent(long l, long l2, int n, int n2, int n3, int n4, int n5, int n6, int n7, int n8) {
        this.mDownTime = l;
        this.mEventTime = l2;
        this.mAction = n;
        this.mKeyCode = n2;
        this.mRepeatCount = n3;
        this.mMetaState = n4;
        this.mDeviceId = n5;
        this.mScanCode = n6;
        this.mFlags = n7;
        this.mSource = n8;
        this.mDisplayId = -1;
    }

    public KeyEvent(long l, String string2, int n, int n2) {
        this.mDownTime = l;
        this.mEventTime = l;
        this.mCharacters = string2;
        this.mAction = 2;
        this.mKeyCode = 0;
        this.mRepeatCount = 0;
        this.mDeviceId = n;
        this.mFlags = n2;
        this.mSource = 257;
        this.mDisplayId = -1;
    }

    private KeyEvent(Parcel parcel) {
        this.mDeviceId = parcel.readInt();
        this.mSource = parcel.readInt();
        this.mDisplayId = parcel.readInt();
        this.mAction = parcel.readInt();
        this.mKeyCode = parcel.readInt();
        this.mRepeatCount = parcel.readInt();
        this.mMetaState = parcel.readInt();
        this.mScanCode = parcel.readInt();
        this.mFlags = parcel.readInt();
        this.mDownTime = parcel.readLong();
        this.mEventTime = parcel.readLong();
        this.mCharacters = parcel.readString();
    }

    public KeyEvent(KeyEvent keyEvent) {
        this.mDownTime = keyEvent.mDownTime;
        this.mEventTime = keyEvent.mEventTime;
        this.mAction = keyEvent.mAction;
        this.mKeyCode = keyEvent.mKeyCode;
        this.mRepeatCount = keyEvent.mRepeatCount;
        this.mMetaState = keyEvent.mMetaState;
        this.mDeviceId = keyEvent.mDeviceId;
        this.mSource = keyEvent.mSource;
        this.mDisplayId = keyEvent.mDisplayId;
        this.mScanCode = keyEvent.mScanCode;
        this.mFlags = keyEvent.mFlags;
        this.mCharacters = keyEvent.mCharacters;
    }

    private KeyEvent(KeyEvent keyEvent, int n) {
        this.mDownTime = keyEvent.mDownTime;
        this.mEventTime = keyEvent.mEventTime;
        this.mAction = n;
        this.mKeyCode = keyEvent.mKeyCode;
        this.mRepeatCount = keyEvent.mRepeatCount;
        this.mMetaState = keyEvent.mMetaState;
        this.mDeviceId = keyEvent.mDeviceId;
        this.mSource = keyEvent.mSource;
        this.mDisplayId = keyEvent.mDisplayId;
        this.mScanCode = keyEvent.mScanCode;
        this.mFlags = keyEvent.mFlags;
    }

    @Deprecated
    public KeyEvent(KeyEvent keyEvent, long l, int n) {
        this.mDownTime = keyEvent.mDownTime;
        this.mEventTime = l;
        this.mAction = keyEvent.mAction;
        this.mKeyCode = keyEvent.mKeyCode;
        this.mRepeatCount = n;
        this.mMetaState = keyEvent.mMetaState;
        this.mDeviceId = keyEvent.mDeviceId;
        this.mSource = keyEvent.mSource;
        this.mDisplayId = keyEvent.mDisplayId;
        this.mScanCode = keyEvent.mScanCode;
        this.mFlags = keyEvent.mFlags;
        this.mCharacters = keyEvent.mCharacters;
    }

    static /* synthetic */ int access$076(KeyEvent keyEvent, int n) {
        keyEvent.mFlags = n = keyEvent.mFlags | n;
        return n;
    }

    public static String actionToString(int n) {
        if (n != 0) {
            if (n != 1) {
                if (n != 2) {
                    return Integer.toString(n);
                }
                return "ACTION_MULTIPLE";
            }
            return "ACTION_UP";
        }
        return "ACTION_DOWN";
    }

    public static KeyEvent changeAction(KeyEvent keyEvent, int n) {
        return new KeyEvent(keyEvent, n);
    }

    public static KeyEvent changeFlags(KeyEvent keyEvent, int n) {
        keyEvent = new KeyEvent(keyEvent);
        keyEvent.mFlags = n;
        return keyEvent;
    }

    public static KeyEvent changeTimeRepeat(KeyEvent keyEvent, long l, int n) {
        return new KeyEvent(keyEvent, l, n);
    }

    public static KeyEvent changeTimeRepeat(KeyEvent keyEvent, long l, int n, int n2) {
        keyEvent = new KeyEvent(keyEvent);
        keyEvent.mEventTime = l;
        keyEvent.mRepeatCount = n;
        keyEvent.mFlags = n2;
        return keyEvent;
    }

    public static KeyEvent createFromParcelBody(Parcel parcel) {
        return new KeyEvent(parcel);
    }

    public static int getDeadChar(int n, int n2) {
        return KeyCharacterMap.getDeadChar(n, n2);
    }

    public static int getMaxKeyCode() {
        return 288;
    }

    public static int getModifierMetaStateMask() {
        return 487679;
    }

    public static final boolean isAltKey(int n) {
        boolean bl = n == 57 || n == 58;
        return bl;
    }

    @UnsupportedAppUsage
    public static final boolean isConfirmKey(int n) {
        return n == 23 || n == 62 || n == 66 || n == 160;
    }

    public static final boolean isGamepadButton(int n) {
        switch (n) {
            default: {
                switch (n) {
                    default: {
                        return false;
                    }
                    case 188: 
                    case 189: 
                    case 190: 
                    case 191: 
                    case 192: 
                    case 193: 
                    case 194: 
                    case 195: 
                    case 196: 
                    case 197: 
                    case 198: 
                    case 199: 
                    case 200: 
                    case 201: 
                    case 202: 
                    case 203: 
                }
            }
            case 96: 
            case 97: 
            case 98: 
            case 99: 
            case 100: 
            case 101: 
            case 102: 
            case 103: 
            case 104: 
            case 105: 
            case 106: 
            case 107: 
            case 108: 
            case 109: 
            case 110: 
        }
        return true;
    }

    public static final boolean isMediaSessionKey(int n) {
        if (n != 79 && n != 130 && n != 126 && n != 127) {
            switch (n) {
                default: {
                    return false;
                }
                case 85: 
                case 86: 
                case 87: 
                case 88: 
                case 89: 
                case 90: 
                case 91: 
            }
        }
        return true;
    }

    public static final boolean isMetaKey(int n) {
        boolean bl = n == 117 || n == 118;
        return bl;
    }

    public static boolean isModifierKey(int n) {
        if (n != 63 && n != 78 && n != 113 && n != 114) {
            switch (n) {
                default: {
                    switch (n) {
                        default: {
                            return false;
                        }
                        case 117: 
                        case 118: 
                        case 119: 
                    }
                }
                case 57: 
                case 58: 
                case 59: 
                case 60: 
            }
        }
        return true;
    }

    public static final boolean isSystemKey(int n) {
        if (n != 2 && n != 3 && n != 4 && n != 5 && n != 6 && n != 79 && n != 80 && n != 82 && n != 130 && n != 164 && n != 126 && n != 127) {
            switch (n) {
                default: {
                    switch (n) {
                        default: {
                            switch (n) {
                                default: {
                                    switch (n) {
                                        default: {
                                            return false;
                                        }
                                        case 280: 
                                        case 281: 
                                        case 282: 
                                        case 283: 
                                    }
                                }
                                case 220: 
                                case 221: 
                                case 222: 
                            }
                        }
                        case 84: 
                        case 85: 
                        case 86: 
                        case 87: 
                        case 88: 
                        case 89: 
                        case 90: 
                        case 91: 
                    }
                }
                case 24: 
                case 25: 
                case 26: 
                case 27: 
            }
        }
        return true;
    }

    public static final boolean isWakeKey(int n) {
        if (n != 4 && n != 82 && n != 224 && n != 225) {
            switch (n) {
                default: {
                    return false;
                }
                case 265: 
                case 266: 
                case 267: 
            }
        }
        return true;
    }

    public static int keyCodeFromString(String string2) {
        int n;
        try {
            n = Integer.parseInt(string2);
            boolean bl = KeyEvent.keyCodeIsValid(n);
            if (bl) {
                return n;
            }
        }
        catch (NumberFormatException numberFormatException) {
            // empty catch block
        }
        String string3 = string2;
        if (string2.startsWith(LABEL_PREFIX)) {
            string3 = string2.substring(LABEL_PREFIX.length());
        }
        if (KeyEvent.keyCodeIsValid(n = KeyEvent.nativeKeyCodeFromString(string3))) {
            return n;
        }
        return 0;
    }

    private static boolean keyCodeIsValid(int n) {
        boolean bl = n >= 0 && n <= 288;
        return bl;
    }

    public static String keyCodeToString(int n) {
        CharSequence charSequence;
        String string2 = KeyEvent.nativeKeyCodeToString(n);
        if (string2 != null) {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append(LABEL_PREFIX);
            ((StringBuilder)charSequence).append(string2);
            charSequence = ((StringBuilder)charSequence).toString();
        } else {
            charSequence = Integer.toString(n);
        }
        return charSequence;
    }

    private static int metaStateFilterDirectionalModifiers(int n, int n2, int n3, int n4, int n5) {
        int n6 = 1;
        boolean bl = (n2 & n3) != 0;
        int n7 = n4 | n5;
        n2 = (n2 & n7) != 0 ? n6 : 0;
        if (bl) {
            if (n2 == 0) {
                return n7 & n;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("modifiers must not contain ");
            stringBuilder.append(KeyEvent.metaStateToString(n3));
            stringBuilder.append(" combined with ");
            stringBuilder.append(KeyEvent.metaStateToString(n4));
            stringBuilder.append(" or ");
            stringBuilder.append(KeyEvent.metaStateToString(n5));
            throw new IllegalArgumentException(stringBuilder.toString());
        }
        if (n2 != 0) {
            return n3 & n;
        }
        return n;
    }

    public static boolean metaStateHasModifiers(int n, int n2) {
        if ((7343872 & n2) == 0) {
            n = KeyEvent.normalizeMetaState(n);
            boolean bl = true;
            if (KeyEvent.metaStateFilterDirectionalModifiers(KeyEvent.metaStateFilterDirectionalModifiers(KeyEvent.metaStateFilterDirectionalModifiers(KeyEvent.metaStateFilterDirectionalModifiers(n & 487679, n2, 1, 64, 128), n2, 2, 16, 32), n2, 4096, 8192, 16384), n2, 65536, 131072, 262144) != n2) {
                bl = false;
            }
            return bl;
        }
        throw new IllegalArgumentException("modifiers must not contain META_CAPS_LOCK_ON, META_NUM_LOCK_ON, META_SCROLL_LOCK_ON, META_CAP_LOCKED, META_ALT_LOCKED, META_SYM_LOCKED, or META_SELECTING");
    }

    public static boolean metaStateHasNoModifiers(int n) {
        boolean bl = (KeyEvent.normalizeMetaState(n) & 487679) == 0;
        return bl;
    }

    public static String metaStateToString(int n) {
        if (n == 0) {
            return "0";
        }
        StringBuilder stringBuilder = null;
        int n2 = 0;
        while (n != 0) {
            boolean bl = (n & 1) != 0;
            n >>>= 1;
            CharSequence charSequence = stringBuilder;
            if (bl) {
                charSequence = META_SYMBOLIC_NAMES[n2];
                if (stringBuilder == null) {
                    if (n == 0) {
                        return charSequence;
                    }
                    charSequence = new StringBuilder((String)charSequence);
                } else {
                    stringBuilder.append('|');
                    stringBuilder.append((String)charSequence);
                    charSequence = stringBuilder;
                }
            }
            ++n2;
            stringBuilder = charSequence;
        }
        return stringBuilder.toString();
    }

    private static native int nativeKeyCodeFromString(String var0);

    private static native String nativeKeyCodeToString(int var0);

    public static int normalizeMetaState(int n) {
        int n2 = n;
        if ((n & 192) != 0) {
            n2 = n | 1;
        }
        n = n2;
        if ((n2 & 48) != 0) {
            n = n2 | 2;
        }
        n2 = n;
        if ((n & 24576) != 0) {
            n2 = n | 4096;
        }
        n = n2;
        if ((393216 & n2) != 0) {
            n = n2 | 65536;
        }
        n2 = n;
        if ((n & 256) != 0) {
            n2 = n | 1048576;
        }
        n = n2;
        if ((n2 & 512) != 0) {
            n = n2 | 2;
        }
        n2 = n;
        if ((n & 1024) != 0) {
            n2 = n | 4;
        }
        return 7827711 & n2;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private static KeyEvent obtain() {
        KeyEvent keyEvent;
        Object object = gRecyclerLock;
        synchronized (object) {
            keyEvent = gRecyclerTop;
            if (keyEvent == null) {
                return new KeyEvent();
            }
            gRecyclerTop = keyEvent.mNext;
            --gRecyclerUsed;
        }
        keyEvent.mNext = null;
        keyEvent.prepareForReuse();
        return keyEvent;
    }

    public static KeyEvent obtain(long l, long l2, int n, int n2, int n3, int n4, int n5, int n6, int n7, int n8, int n9, String string2) {
        KeyEvent keyEvent = KeyEvent.obtain();
        keyEvent.mDownTime = l;
        keyEvent.mEventTime = l2;
        keyEvent.mAction = n;
        keyEvent.mKeyCode = n2;
        keyEvent.mRepeatCount = n3;
        keyEvent.mMetaState = n4;
        keyEvent.mDeviceId = n5;
        keyEvent.mScanCode = n6;
        keyEvent.mFlags = n7;
        keyEvent.mSource = n8;
        keyEvent.mDisplayId = n9;
        keyEvent.mCharacters = string2;
        return keyEvent;
    }

    @UnsupportedAppUsage
    public static KeyEvent obtain(long l, long l2, int n, int n2, int n3, int n4, int n5, int n6, int n7, int n8, String string2) {
        return KeyEvent.obtain(l, l2, n, n2, n3, n4, n5, n6, n7, n8, -1, string2);
    }

    public static KeyEvent obtain(KeyEvent keyEvent) {
        KeyEvent keyEvent2 = KeyEvent.obtain();
        keyEvent2.mDownTime = keyEvent.mDownTime;
        keyEvent2.mEventTime = keyEvent.mEventTime;
        keyEvent2.mAction = keyEvent.mAction;
        keyEvent2.mKeyCode = keyEvent.mKeyCode;
        keyEvent2.mRepeatCount = keyEvent.mRepeatCount;
        keyEvent2.mMetaState = keyEvent.mMetaState;
        keyEvent2.mDeviceId = keyEvent.mDeviceId;
        keyEvent2.mScanCode = keyEvent.mScanCode;
        keyEvent2.mFlags = keyEvent.mFlags;
        keyEvent2.mSource = keyEvent.mSource;
        keyEvent2.mDisplayId = keyEvent.mDisplayId;
        keyEvent2.mCharacters = keyEvent.mCharacters;
        return keyEvent2;
    }

    @Override
    public final void cancel() {
        this.mFlags |= 32;
    }

    @Override
    public KeyEvent copy() {
        return KeyEvent.obtain(this);
    }

    @Deprecated
    public final boolean dispatch(Callback callback) {
        return this.dispatch(callback, null, null);
    }

    public final boolean dispatch(Callback callback, DispatcherState dispatcherState, Object object) {
        boolean bl;
        int n = this.mAction;
        if (n != 0) {
            if (n != 1) {
                if (n != 2) {
                    return false;
                }
                int n2 = this.mKeyCode;
                n = this.mRepeatCount;
                if (callback.onKeyMultiple(n2, n, this)) {
                    return true;
                }
                if (n2 != 0) {
                    this.mAction = 0;
                    this.mRepeatCount = 0;
                    boolean bl2 = callback.onKeyDown(n2, this);
                    if (bl2) {
                        this.mAction = 1;
                        callback.onKeyUp(n2, this);
                    }
                    this.mAction = 2;
                    this.mRepeatCount = n;
                    return bl2;
                }
                return false;
            }
            if (dispatcherState != null) {
                dispatcherState.handleUpEvent(this);
            }
            return callback.onKeyUp(this.mKeyCode, this);
        }
        this.mFlags &= -1073741825;
        boolean bl3 = bl = callback.onKeyDown(this.mKeyCode, this);
        if (dispatcherState != null) {
            if (bl && this.mRepeatCount == 0 && (this.mFlags & 1073741824) != 0) {
                dispatcherState.startTracking(this, object);
                bl3 = bl;
            } else {
                bl3 = bl;
                if (this.isLongPress()) {
                    bl3 = bl;
                    if (dispatcherState.isTracking(this)) {
                        bl3 = bl;
                        try {
                            if (callback.onKeyLongPress(this.mKeyCode, this)) {
                                dispatcherState.performedLongPress(this);
                                bl3 = true;
                            }
                        }
                        catch (AbstractMethodError abstractMethodError) {
                            bl3 = bl;
                        }
                    }
                }
            }
        }
        return bl3;
    }

    public final int getAction() {
        return this.mAction;
    }

    @Deprecated
    public final String getCharacters() {
        return this.mCharacters;
    }

    @Override
    public final int getDeviceId() {
        return this.mDeviceId;
    }

    @Override
    public final int getDisplayId() {
        return this.mDisplayId;
    }

    public char getDisplayLabel() {
        return this.getKeyCharacterMap().getDisplayLabel(this.mKeyCode);
    }

    public final long getDownTime() {
        return this.mDownTime;
    }

    @Override
    public final long getEventTime() {
        return this.mEventTime;
    }

    @Override
    public final long getEventTimeNano() {
        return this.mEventTime * 1000000L;
    }

    public final int getFlags() {
        return this.mFlags;
    }

    public final KeyCharacterMap getKeyCharacterMap() {
        return KeyCharacterMap.load(this.mDeviceId);
    }

    public final int getKeyCode() {
        return this.mKeyCode;
    }

    @Deprecated
    public boolean getKeyData(KeyCharacterMap.KeyData keyData) {
        return this.getKeyCharacterMap().getKeyData(this.mKeyCode, keyData);
    }

    @Deprecated
    public final int getKeyboardDevice() {
        return this.mDeviceId;
    }

    public char getMatch(char[] arrc) {
        return this.getMatch(arrc, 0);
    }

    public char getMatch(char[] arrc, int n) {
        return this.getKeyCharacterMap().getMatch(this.mKeyCode, arrc, n);
    }

    public final int getMetaState() {
        return this.mMetaState;
    }

    public final int getModifiers() {
        return KeyEvent.normalizeMetaState(this.mMetaState) & 487679;
    }

    public char getNumber() {
        return this.getKeyCharacterMap().getNumber(this.mKeyCode);
    }

    public final int getRepeatCount() {
        return this.mRepeatCount;
    }

    public final int getScanCode() {
        return this.mScanCode;
    }

    @Override
    public final int getSource() {
        return this.mSource;
    }

    public int getUnicodeChar() {
        return this.getUnicodeChar(this.mMetaState);
    }

    public int getUnicodeChar(int n) {
        return this.getKeyCharacterMap().get(this.mKeyCode, n);
    }

    public final boolean hasModifiers(int n) {
        return KeyEvent.metaStateHasModifiers(this.mMetaState, n);
    }

    public final boolean hasNoModifiers() {
        return KeyEvent.metaStateHasNoModifiers(this.mMetaState);
    }

    public final boolean isAltPressed() {
        boolean bl = (this.mMetaState & 2) != 0;
        return bl;
    }

    public final boolean isCanceled() {
        boolean bl = (this.mFlags & 32) != 0;
        return bl;
    }

    public final boolean isCapsLockOn() {
        boolean bl = (this.mMetaState & 1048576) != 0;
        return bl;
    }

    public final boolean isCtrlPressed() {
        boolean bl = (this.mMetaState & 4096) != 0;
        return bl;
    }

    @Deprecated
    @UnsupportedAppUsage
    public final boolean isDown() {
        boolean bl = this.mAction == 0;
        return bl;
    }

    public final boolean isFunctionPressed() {
        boolean bl = (this.mMetaState & 8) != 0;
        return bl;
    }

    public final boolean isLongPress() {
        boolean bl = (this.mFlags & 128) != 0;
        return bl;
    }

    public final boolean isMetaPressed() {
        boolean bl = (this.mMetaState & 65536) != 0;
        return bl;
    }

    public final boolean isNumLockOn() {
        boolean bl = (this.mMetaState & 2097152) != 0;
        return bl;
    }

    public boolean isPrintingKey() {
        return this.getKeyCharacterMap().isPrintingKey(this.mKeyCode);
    }

    public final boolean isScrollLockOn() {
        boolean bl = (this.mMetaState & 4194304) != 0;
        return bl;
    }

    public final boolean isShiftPressed() {
        int n = this.mMetaState;
        boolean bl = true;
        if ((n & 1) == 0) {
            bl = false;
        }
        return bl;
    }

    public final boolean isSymPressed() {
        boolean bl = (this.mMetaState & 4) != 0;
        return bl;
    }

    public final boolean isSystem() {
        return KeyEvent.isSystemKey(this.mKeyCode);
    }

    @Override
    public final boolean isTainted() {
        boolean bl = (this.mFlags & Integer.MIN_VALUE) != 0;
        return bl;
    }

    public final boolean isTracking() {
        boolean bl = (this.mFlags & 512) != 0;
        return bl;
    }

    public final boolean isWakeKey() {
        return KeyEvent.isWakeKey(this.mKeyCode);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    @Override
    public final void recycle() {
        super.recycle();
        this.mCharacters = null;
        Object object = gRecyclerLock;
        synchronized (object) {
            if (gRecyclerUsed < 10) {
                ++gRecyclerUsed;
                this.mNext = gRecyclerTop;
                gRecyclerTop = this;
            }
            return;
        }
    }

    @Override
    public final void recycleIfNeededAfterDispatch() {
    }

    @Override
    public final void setDisplayId(int n) {
        this.mDisplayId = n;
    }

    public final void setFlags(int n) {
        this.mFlags = n;
    }

    @Override
    public final void setSource(int n) {
        this.mSource = n;
    }

    @Override
    public final void setTainted(boolean bl) {
        int n = this.mFlags;
        n = bl ? (n |= Integer.MIN_VALUE) : (n &= Integer.MAX_VALUE);
        this.mFlags = n;
    }

    public final void setTime(long l, long l2) {
        this.mDownTime = l;
        this.mEventTime = l2;
    }

    public final void startTracking() {
        this.mFlags |= 1073741824;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("KeyEvent { action=");
        stringBuilder.append(KeyEvent.actionToString(this.mAction));
        stringBuilder.append(", keyCode=");
        stringBuilder.append(KeyEvent.keyCodeToString(this.mKeyCode));
        stringBuilder.append(", scanCode=");
        stringBuilder.append(this.mScanCode);
        if (this.mCharacters != null) {
            stringBuilder.append(", characters=\"");
            stringBuilder.append(this.mCharacters);
            stringBuilder.append("\"");
        }
        stringBuilder.append(", metaState=");
        stringBuilder.append(KeyEvent.metaStateToString(this.mMetaState));
        stringBuilder.append(", flags=0x");
        stringBuilder.append(Integer.toHexString(this.mFlags));
        stringBuilder.append(", repeatCount=");
        stringBuilder.append(this.mRepeatCount);
        stringBuilder.append(", eventTime=");
        stringBuilder.append(this.mEventTime);
        stringBuilder.append(", downTime=");
        stringBuilder.append(this.mDownTime);
        stringBuilder.append(", deviceId=");
        stringBuilder.append(this.mDeviceId);
        stringBuilder.append(", source=0x");
        stringBuilder.append(Integer.toHexString(this.mSource));
        stringBuilder.append(", displayId=");
        stringBuilder.append(this.mDisplayId);
        stringBuilder.append(" }");
        return stringBuilder.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeInt(2);
        parcel.writeInt(this.mDeviceId);
        parcel.writeInt(this.mSource);
        parcel.writeInt(this.mDisplayId);
        parcel.writeInt(this.mAction);
        parcel.writeInt(this.mKeyCode);
        parcel.writeInt(this.mRepeatCount);
        parcel.writeInt(this.mMetaState);
        parcel.writeInt(this.mScanCode);
        parcel.writeInt(this.mFlags);
        parcel.writeLong(this.mDownTime);
        parcel.writeLong(this.mEventTime);
        parcel.writeString(this.mCharacters);
    }

    public static interface Callback {
        public boolean onKeyDown(int var1, KeyEvent var2);

        public boolean onKeyLongPress(int var1, KeyEvent var2);

        public boolean onKeyMultiple(int var1, int var2, KeyEvent var3);

        public boolean onKeyUp(int var1, KeyEvent var2);
    }

    public static class DispatcherState {
        SparseIntArray mActiveLongPresses = new SparseIntArray();
        int mDownKeyCode;
        Object mDownTarget;

        public void handleUpEvent(KeyEvent keyEvent) {
            int n = keyEvent.getKeyCode();
            int n2 = this.mActiveLongPresses.indexOfKey(n);
            if (n2 >= 0) {
                KeyEvent.access$076(keyEvent, 288);
                this.mActiveLongPresses.removeAt(n2);
            }
            if (this.mDownKeyCode == n) {
                KeyEvent.access$076(keyEvent, 512);
                this.mDownKeyCode = 0;
                this.mDownTarget = null;
            }
        }

        public boolean isTracking(KeyEvent keyEvent) {
            boolean bl = this.mDownKeyCode == keyEvent.getKeyCode();
            return bl;
        }

        public void performedLongPress(KeyEvent keyEvent) {
            this.mActiveLongPresses.put(keyEvent.getKeyCode(), 1);
        }

        public void reset() {
            this.mDownKeyCode = 0;
            this.mDownTarget = null;
            this.mActiveLongPresses.clear();
        }

        public void reset(Object object) {
            if (this.mDownTarget == object) {
                this.mDownKeyCode = 0;
                this.mDownTarget = null;
            }
        }

        public void startTracking(KeyEvent keyEvent, Object object) {
            if (keyEvent.getAction() == 0) {
                this.mDownKeyCode = keyEvent.getKeyCode();
                this.mDownTarget = object;
                return;
            }
            throw new IllegalArgumentException("Can only start tracking on a down event");
        }
    }

}

