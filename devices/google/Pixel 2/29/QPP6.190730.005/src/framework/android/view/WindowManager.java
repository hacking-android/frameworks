/*
 * Decompiled with CFR 0.145.
 */
package android.view;

import android.annotation.SystemApi;
import android.annotation.UnsupportedAppUsage;
import android.content.pm.ActivityInfo;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.Region;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.Log;
import android.util.proto.ProtoOutputStream;
import android.view.Display;
import android.view.Gravity;
import android.view.KeyboardShortcutGroup;
import android.view.View;
import android.view.ViewDebug;
import android.view.ViewGroup;
import android.view.ViewHierarchyEncoder;
import android.view.ViewManager;
import android.view.accessibility.AccessibilityNodeInfo;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.List;
import java.util.Objects;

public interface WindowManager
extends ViewManager {
    public static final int DOCKED_BOTTOM = 4;
    public static final int DOCKED_INVALID = -1;
    public static final int DOCKED_LEFT = 1;
    public static final int DOCKED_RIGHT = 3;
    public static final int DOCKED_TOP = 2;
    public static final String INPUT_CONSUMER_NAVIGATION = "nav_input_consumer";
    public static final String INPUT_CONSUMER_PIP = "pip_input_consumer";
    public static final String INPUT_CONSUMER_RECENTS_ANIMATION = "recents_animation_input_consumer";
    public static final String INPUT_CONSUMER_WALLPAPER = "wallpaper_input_consumer";
    public static final String PARCEL_KEY_SHORTCUTS_ARRAY = "shortcuts_array";
    public static final int REMOVE_CONTENT_MODE_DESTROY = 2;
    public static final int REMOVE_CONTENT_MODE_MOVE_TO_PRIMARY = 1;
    public static final int REMOVE_CONTENT_MODE_UNDEFINED = 0;
    public static final int TAKE_SCREENSHOT_FULLSCREEN = 1;
    public static final int TAKE_SCREENSHOT_SELECTED_REGION = 2;
    public static final int TRANSIT_ACTIVITY_CLOSE = 7;
    public static final int TRANSIT_ACTIVITY_OPEN = 6;
    public static final int TRANSIT_ACTIVITY_RELAUNCH = 18;
    public static final int TRANSIT_CRASHING_ACTIVITY_CLOSE = 26;
    public static final int TRANSIT_DOCK_TASK_FROM_RECENTS = 19;
    public static final int TRANSIT_FLAG_KEYGUARD_GOING_AWAY_NO_ANIMATION = 2;
    public static final int TRANSIT_FLAG_KEYGUARD_GOING_AWAY_TO_SHADE = 1;
    public static final int TRANSIT_FLAG_KEYGUARD_GOING_AWAY_WITH_WALLPAPER = 4;
    public static final int TRANSIT_KEYGUARD_GOING_AWAY = 20;
    public static final int TRANSIT_KEYGUARD_GOING_AWAY_ON_WALLPAPER = 21;
    public static final int TRANSIT_KEYGUARD_OCCLUDE = 22;
    public static final int TRANSIT_KEYGUARD_UNOCCLUDE = 23;
    public static final int TRANSIT_NONE = 0;
    public static final int TRANSIT_TASK_CHANGE_WINDOWING_MODE = 27;
    public static final int TRANSIT_TASK_CLOSE = 9;
    public static final int TRANSIT_TASK_IN_PLACE = 17;
    public static final int TRANSIT_TASK_OPEN = 8;
    public static final int TRANSIT_TASK_OPEN_BEHIND = 16;
    public static final int TRANSIT_TASK_TO_BACK = 11;
    public static final int TRANSIT_TASK_TO_FRONT = 10;
    public static final int TRANSIT_TRANSLUCENT_ACTIVITY_CLOSE = 25;
    public static final int TRANSIT_TRANSLUCENT_ACTIVITY_OPEN = 24;
    public static final int TRANSIT_UNSET = -1;
    public static final int TRANSIT_WALLPAPER_CLOSE = 12;
    public static final int TRANSIT_WALLPAPER_INTRA_CLOSE = 15;
    public static final int TRANSIT_WALLPAPER_INTRA_OPEN = 14;
    public static final int TRANSIT_WALLPAPER_OPEN = 13;

    @SystemApi
    public Region getCurrentImeTouchRegion();

    public Display getDefaultDisplay();

    public void removeViewImmediate(View var1);

    public void requestAppKeyboardShortcuts(KeyboardShortcutsReceiver var1, int var2);

    default public void setShouldShowIme(int n, boolean bl) {
    }

    default public void setShouldShowSystemDecors(int n, boolean bl) {
    }

    default public void setShouldShowWithInsecureKeyguard(int n, boolean bl) {
    }

    default public boolean shouldShowIme(int n) {
        return false;
    }

    default public boolean shouldShowSystemDecors(int n) {
        return false;
    }

    public static class BadTokenException
    extends RuntimeException {
        public BadTokenException() {
        }

        public BadTokenException(String string2) {
            super(string2);
        }
    }

    public static class InvalidDisplayException
    extends RuntimeException {
        public InvalidDisplayException() {
        }

        public InvalidDisplayException(String string2) {
            super(string2);
        }
    }

    public static interface KeyboardShortcutsReceiver {
        public void onKeyboardShortcutsReceived(List<KeyboardShortcutGroup> var1);
    }

    public static class LayoutParams
    extends ViewGroup.LayoutParams
    implements Parcelable {
        public static final int ACCESSIBILITY_ANCHOR_CHANGED = 16777216;
        public static final int ACCESSIBILITY_TITLE_CHANGED = 33554432;
        public static final int ALPHA_CHANGED = 128;
        public static final int ANIMATION_CHANGED = 16;
        public static final float BRIGHTNESS_OVERRIDE_FULL = 1.0f;
        public static final float BRIGHTNESS_OVERRIDE_NONE = -1.0f;
        public static final float BRIGHTNESS_OVERRIDE_OFF = 0.0f;
        public static final int BUTTON_BRIGHTNESS_CHANGED = 8192;
        public static final int COLOR_MODE_CHANGED = 67108864;
        public static final Parcelable.Creator<LayoutParams> CREATOR = new Parcelable.Creator<LayoutParams>(){

            @Override
            public LayoutParams createFromParcel(Parcel parcel) {
                return new LayoutParams(parcel);
            }

            public LayoutParams[] newArray(int n) {
                return new LayoutParams[n];
            }
        };
        public static final int DIM_AMOUNT_CHANGED = 32;
        public static final int EVERYTHING_CHANGED = -1;
        public static final int FIRST_APPLICATION_WINDOW = 1;
        public static final int FIRST_SUB_WINDOW = 1000;
        public static final int FIRST_SYSTEM_WINDOW = 2000;
        public static final int FLAGS_CHANGED = 4;
        public static final int FLAG_ALLOW_LOCK_WHILE_SCREEN_ON = 1;
        public static final int FLAG_ALT_FOCUSABLE_IM = 131072;
        @Deprecated
        public static final int FLAG_BLUR_BEHIND = 4;
        public static final int FLAG_DIM_BEHIND = 2;
        @Deprecated
        public static final int FLAG_DISMISS_KEYGUARD = 4194304;
        @Deprecated
        public static final int FLAG_DITHER = 4096;
        public static final int FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS = Integer.MIN_VALUE;
        public static final int FLAG_FORCE_NOT_FULLSCREEN = 2048;
        public static final int FLAG_FULLSCREEN = 1024;
        public static final int FLAG_HARDWARE_ACCELERATED = 16777216;
        public static final int FLAG_IGNORE_CHEEK_PRESSES = 32768;
        public static final int FLAG_KEEP_SCREEN_ON = 128;
        public static final int FLAG_LAYOUT_ATTACHED_IN_DECOR = 1073741824;
        public static final int FLAG_LAYOUT_INSET_DECOR = 65536;
        public static final int FLAG_LAYOUT_IN_OVERSCAN = 33554432;
        public static final int FLAG_LAYOUT_IN_SCREEN = 256;
        public static final int FLAG_LAYOUT_NO_LIMITS = 512;
        public static final int FLAG_LOCAL_FOCUS_MODE = 268435456;
        public static final int FLAG_NOT_FOCUSABLE = 8;
        public static final int FLAG_NOT_TOUCHABLE = 16;
        public static final int FLAG_NOT_TOUCH_MODAL = 32;
        public static final int FLAG_SCALED = 16384;
        public static final int FLAG_SECURE = 8192;
        public static final int FLAG_SHOW_WALLPAPER = 1048576;
        @Deprecated
        public static final int FLAG_SHOW_WHEN_LOCKED = 524288;
        @UnsupportedAppUsage
        public static final int FLAG_SLIPPERY = 536870912;
        public static final int FLAG_SPLIT_TOUCH = 8388608;
        @Deprecated
        public static final int FLAG_TOUCHABLE_WHEN_WAKING = 64;
        public static final int FLAG_TRANSLUCENT_NAVIGATION = 134217728;
        public static final int FLAG_TRANSLUCENT_STATUS = 67108864;
        @Deprecated
        public static final int FLAG_TURN_SCREEN_ON = 2097152;
        public static final int FLAG_WATCH_OUTSIDE_TOUCH = 262144;
        public static final int FORMAT_CHANGED = 8;
        public static final int INPUT_FEATURES_CHANGED = 65536;
        public static final int INPUT_FEATURE_DISABLE_POINTER_GESTURES = 1;
        @UnsupportedAppUsage
        public static final int INPUT_FEATURE_DISABLE_USER_ACTIVITY = 4;
        public static final int INPUT_FEATURE_NO_INPUT_CHANNEL = 2;
        public static final int INVALID_WINDOW_TYPE = -1;
        public static final int LAST_APPLICATION_WINDOW = 99;
        public static final int LAST_SUB_WINDOW = 1999;
        public static final int LAST_SYSTEM_WINDOW = 2999;
        public static final int LAYOUT_CHANGED = 1;
        @Deprecated
        public static final int LAYOUT_IN_DISPLAY_CUTOUT_MODE_ALWAYS = 1;
        public static final int LAYOUT_IN_DISPLAY_CUTOUT_MODE_DEFAULT = 0;
        public static final int LAYOUT_IN_DISPLAY_CUTOUT_MODE_NEVER = 2;
        public static final int LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES = 1;
        public static final int MEMORY_TYPE_CHANGED = 256;
        @Deprecated
        public static final int MEMORY_TYPE_GPU = 2;
        @Deprecated
        public static final int MEMORY_TYPE_HARDWARE = 1;
        @Deprecated
        public static final int MEMORY_TYPE_NORMAL = 0;
        @Deprecated
        public static final int MEMORY_TYPE_PUSH_BUFFERS = 3;
        public static final int NEEDS_MENU_KEY_CHANGED = 4194304;
        @UnsupportedAppUsage
        public static final int NEEDS_MENU_SET_FALSE = 2;
        @UnsupportedAppUsage
        public static final int NEEDS_MENU_SET_TRUE = 1;
        public static final int NEEDS_MENU_UNSET = 0;
        public static final int PREFERRED_DISPLAY_MODE_ID = 8388608;
        public static final int PREFERRED_REFRESH_RATE_CHANGED = 2097152;
        public static final int PRIVATE_FLAGS_CHANGED = 131072;
        public static final int PRIVATE_FLAG_COLOR_SPACE_AGNOSTIC = 16777216;
        public static final int PRIVATE_FLAG_COMPATIBLE_WINDOW = 128;
        public static final int PRIVATE_FLAG_DISABLE_WALLPAPER_TOUCH_EVENTS = 2048;
        public static final int PRIVATE_FLAG_FAKE_HARDWARE_ACCELERATED = 1;
        public static final int PRIVATE_FLAG_FORCE_DECOR_VIEW_VISIBILITY = 16384;
        public static final int PRIVATE_FLAG_FORCE_DRAW_BAR_BACKGROUNDS = 131072;
        public static final int PRIVATE_FLAG_FORCE_HARDWARE_ACCELERATED = 2;
        public static final int PRIVATE_FLAG_FORCE_STATUS_BAR_VISIBLE_TRANSPARENT = 4096;
        public static final int PRIVATE_FLAG_INHERIT_TRANSLUCENT_DECOR = 512;
        public static final int PRIVATE_FLAG_IS_ROUNDED_CORNERS_OVERLAY = 1048576;
        public static final int PRIVATE_FLAG_IS_SCREEN_DECOR = 4194304;
        public static final int PRIVATE_FLAG_KEYGUARD = 1024;
        public static final int PRIVATE_FLAG_LAYOUT_CHILD_WINDOW_IN_PARENT_FRAME = 65536;
        public static final int PRIVATE_FLAG_NO_MOVE_ANIMATION = 64;
        public static final int PRIVATE_FLAG_PRESERVE_GEOMETRY = 8192;
        @UnsupportedAppUsage
        public static final int PRIVATE_FLAG_SHOW_FOR_ALL_USERS = 16;
        public static final int PRIVATE_FLAG_STATUS_FORCE_SHOW_NAVIGATION = 8388608;
        public static final int PRIVATE_FLAG_SUSTAINED_PERFORMANCE_MODE = 262144;
        public static final int PRIVATE_FLAG_SYSTEM_ERROR = 256;
        public static final int PRIVATE_FLAG_WANTS_OFFSET_NOTIFICATIONS = 4;
        public static final int PRIVATE_FLAG_WILL_NOT_REPLACE_ON_RELAUNCH = 32768;
        public static final int ROTATION_ANIMATION_CHANGED = 4096;
        public static final int ROTATION_ANIMATION_CROSSFADE = 1;
        public static final int ROTATION_ANIMATION_JUMPCUT = 2;
        public static final int ROTATION_ANIMATION_ROTATE = 0;
        public static final int ROTATION_ANIMATION_SEAMLESS = 3;
        public static final int ROTATION_ANIMATION_UNSPECIFIED = -1;
        public static final int SCREEN_BRIGHTNESS_CHANGED = 2048;
        public static final int SCREEN_ORIENTATION_CHANGED = 1024;
        public static final int SOFT_INPUT_ADJUST_NOTHING = 48;
        public static final int SOFT_INPUT_ADJUST_PAN = 32;
        public static final int SOFT_INPUT_ADJUST_RESIZE = 16;
        public static final int SOFT_INPUT_ADJUST_UNSPECIFIED = 0;
        public static final int SOFT_INPUT_IS_FORWARD_NAVIGATION = 256;
        public static final int SOFT_INPUT_MASK_ADJUST = 240;
        public static final int SOFT_INPUT_MASK_STATE = 15;
        public static final int SOFT_INPUT_MODE_CHANGED = 512;
        public static final int SOFT_INPUT_STATE_ALWAYS_HIDDEN = 3;
        public static final int SOFT_INPUT_STATE_ALWAYS_VISIBLE = 5;
        public static final int SOFT_INPUT_STATE_HIDDEN = 2;
        public static final int SOFT_INPUT_STATE_UNCHANGED = 1;
        public static final int SOFT_INPUT_STATE_UNSPECIFIED = 0;
        public static final int SOFT_INPUT_STATE_VISIBLE = 4;
        public static final int SURFACE_INSETS_CHANGED = 1048576;
        @SystemApi
        public static final int SYSTEM_FLAG_HIDE_NON_SYSTEM_OVERLAY_WINDOWS = 524288;
        public static final int SYSTEM_UI_LISTENER_CHANGED = 32768;
        public static final int SYSTEM_UI_VISIBILITY_CHANGED = 16384;
        public static final int TITLE_CHANGED = 64;
        public static final int TRANSLUCENT_FLAGS_CHANGED = 524288;
        public static final int TYPE_ACCESSIBILITY_OVERLAY = 2032;
        public static final int TYPE_APPLICATION = 2;
        public static final int TYPE_APPLICATION_ABOVE_SUB_PANEL = 1005;
        public static final int TYPE_APPLICATION_ATTACHED_DIALOG = 1003;
        public static final int TYPE_APPLICATION_MEDIA = 1001;
        @UnsupportedAppUsage
        public static final int TYPE_APPLICATION_MEDIA_OVERLAY = 1004;
        public static final int TYPE_APPLICATION_OVERLAY = 2038;
        public static final int TYPE_APPLICATION_PANEL = 1000;
        public static final int TYPE_APPLICATION_STARTING = 3;
        public static final int TYPE_APPLICATION_SUB_PANEL = 1002;
        public static final int TYPE_BASE_APPLICATION = 1;
        public static final int TYPE_BOOT_PROGRESS = 2021;
        public static final int TYPE_CHANGED = 2;
        @UnsupportedAppUsage
        public static final int TYPE_DISPLAY_OVERLAY = 2026;
        public static final int TYPE_DOCK_DIVIDER = 2034;
        public static final int TYPE_DRAG = 2016;
        public static final int TYPE_DRAWN_APPLICATION = 4;
        public static final int TYPE_DREAM = 2023;
        public static final int TYPE_INPUT_CONSUMER = 2022;
        public static final int TYPE_INPUT_METHOD = 2011;
        public static final int TYPE_INPUT_METHOD_DIALOG = 2012;
        public static final int TYPE_KEYGUARD = 2004;
        public static final int TYPE_KEYGUARD_DIALOG = 2009;
        public static final int TYPE_MAGNIFICATION_OVERLAY = 2027;
        public static final int TYPE_NAVIGATION_BAR = 2019;
        public static final int TYPE_NAVIGATION_BAR_PANEL = 2024;
        @Deprecated
        public static final int TYPE_PHONE = 2002;
        public static final int TYPE_POINTER = 2018;
        public static final int TYPE_PRESENTATION = 2037;
        @Deprecated
        public static final int TYPE_PRIORITY_PHONE = 2007;
        public static final int TYPE_PRIVATE_PRESENTATION = 2030;
        public static final int TYPE_QS_DIALOG = 2035;
        public static final int TYPE_SCREENSHOT = 2036;
        public static final int TYPE_SEARCH_BAR = 2001;
        @UnsupportedAppUsage
        public static final int TYPE_SECURE_SYSTEM_OVERLAY = 2015;
        public static final int TYPE_STATUS_BAR = 2000;
        public static final int TYPE_STATUS_BAR_PANEL = 2014;
        public static final int TYPE_STATUS_BAR_SUB_PANEL = 2017;
        @Deprecated
        public static final int TYPE_SYSTEM_ALERT = 2003;
        public static final int TYPE_SYSTEM_DIALOG = 2008;
        @Deprecated
        public static final int TYPE_SYSTEM_ERROR = 2010;
        @Deprecated
        public static final int TYPE_SYSTEM_OVERLAY = 2006;
        @Deprecated
        public static final int TYPE_TOAST = 2005;
        public static final int TYPE_VOICE_INTERACTION = 2031;
        public static final int TYPE_VOICE_INTERACTION_STARTING = 2033;
        public static final int TYPE_VOLUME_OVERLAY = 2020;
        public static final int TYPE_WALLPAPER = 2013;
        public static final int USER_ACTIVITY_TIMEOUT_CHANGED = 262144;
        public long accessibilityIdOfAnchor;
        public CharSequence accessibilityTitle;
        public float alpha;
        public float buttonBrightness;
        public float dimAmount;
        @ViewDebug.ExportedProperty(flagMapping={@ViewDebug.FlagToString(equals=1, mask=1, name="ALLOW_LOCK_WHILE_SCREEN_ON"), @ViewDebug.FlagToString(equals=2, mask=2, name="DIM_BEHIND"), @ViewDebug.FlagToString(equals=4, mask=4, name="BLUR_BEHIND"), @ViewDebug.FlagToString(equals=8, mask=8, name="NOT_FOCUSABLE"), @ViewDebug.FlagToString(equals=16, mask=16, name="NOT_TOUCHABLE"), @ViewDebug.FlagToString(equals=32, mask=32, name="NOT_TOUCH_MODAL"), @ViewDebug.FlagToString(equals=64, mask=64, name="TOUCHABLE_WHEN_WAKING"), @ViewDebug.FlagToString(equals=128, mask=128, name="KEEP_SCREEN_ON"), @ViewDebug.FlagToString(equals=256, mask=256, name="LAYOUT_IN_SCREEN"), @ViewDebug.FlagToString(equals=512, mask=512, name="LAYOUT_NO_LIMITS"), @ViewDebug.FlagToString(equals=1024, mask=1024, name="FULLSCREEN"), @ViewDebug.FlagToString(equals=2048, mask=2048, name="FORCE_NOT_FULLSCREEN"), @ViewDebug.FlagToString(equals=4096, mask=4096, name="DITHER"), @ViewDebug.FlagToString(equals=8192, mask=8192, name="SECURE"), @ViewDebug.FlagToString(equals=16384, mask=16384, name="SCALED"), @ViewDebug.FlagToString(equals=32768, mask=32768, name="IGNORE_CHEEK_PRESSES"), @ViewDebug.FlagToString(equals=65536, mask=65536, name="LAYOUT_INSET_DECOR"), @ViewDebug.FlagToString(equals=131072, mask=131072, name="ALT_FOCUSABLE_IM"), @ViewDebug.FlagToString(equals=262144, mask=262144, name="WATCH_OUTSIDE_TOUCH"), @ViewDebug.FlagToString(equals=524288, mask=524288, name="SHOW_WHEN_LOCKED"), @ViewDebug.FlagToString(equals=1048576, mask=1048576, name="SHOW_WALLPAPER"), @ViewDebug.FlagToString(equals=2097152, mask=2097152, name="TURN_SCREEN_ON"), @ViewDebug.FlagToString(equals=4194304, mask=4194304, name="DISMISS_KEYGUARD"), @ViewDebug.FlagToString(equals=8388608, mask=8388608, name="SPLIT_TOUCH"), @ViewDebug.FlagToString(equals=16777216, mask=16777216, name="HARDWARE_ACCELERATED"), @ViewDebug.FlagToString(equals=33554432, mask=33554432, name="LOCAL_FOCUS_MODE"), @ViewDebug.FlagToString(equals=67108864, mask=67108864, name="TRANSLUCENT_STATUS"), @ViewDebug.FlagToString(equals=134217728, mask=134217728, name="TRANSLUCENT_NAVIGATION"), @ViewDebug.FlagToString(equals=268435456, mask=268435456, name="LOCAL_FOCUS_MODE"), @ViewDebug.FlagToString(equals=536870912, mask=536870912, name="FLAG_SLIPPERY"), @ViewDebug.FlagToString(equals=1073741824, mask=1073741824, name="FLAG_LAYOUT_ATTACHED_IN_DECOR"), @ViewDebug.FlagToString(equals=Integer.MIN_VALUE, mask=Integer.MIN_VALUE, name="DRAWS_SYSTEM_BAR_BACKGROUNDS")}, formatToHexString=true)
        public int flags;
        public int format;
        public int gravity;
        public boolean hasManualSurfaceInsets;
        @UnsupportedAppUsage
        public boolean hasSystemUiListeners;
        @UnsupportedAppUsage
        public long hideTimeoutMilliseconds;
        public float horizontalMargin;
        @ViewDebug.ExportedProperty
        public float horizontalWeight;
        @UnsupportedAppUsage
        public int inputFeatures;
        public int layoutInDisplayCutoutMode;
        private int mColorMode;
        private int[] mCompatibilityParamsBackup;
        private CharSequence mTitle;
        @Deprecated
        public int memoryType;
        @UnsupportedAppUsage
        public int needsMenuKey;
        public String packageName;
        public int preferredDisplayModeId;
        @Deprecated
        public float preferredRefreshRate;
        public boolean preservePreviousSurfaceInsets;
        @ViewDebug.ExportedProperty(flagMapping={@ViewDebug.FlagToString(equals=1, mask=1, name="FAKE_HARDWARE_ACCELERATED"), @ViewDebug.FlagToString(equals=2, mask=2, name="FORCE_HARDWARE_ACCELERATED"), @ViewDebug.FlagToString(equals=4, mask=4, name="WANTS_OFFSET_NOTIFICATIONS"), @ViewDebug.FlagToString(equals=16, mask=16, name="SHOW_FOR_ALL_USERS"), @ViewDebug.FlagToString(equals=64, mask=64, name="NO_MOVE_ANIMATION"), @ViewDebug.FlagToString(equals=128, mask=128, name="COMPATIBLE_WINDOW"), @ViewDebug.FlagToString(equals=256, mask=256, name="SYSTEM_ERROR"), @ViewDebug.FlagToString(equals=512, mask=512, name="INHERIT_TRANSLUCENT_DECOR"), @ViewDebug.FlagToString(equals=1024, mask=1024, name="KEYGUARD"), @ViewDebug.FlagToString(equals=2048, mask=2048, name="DISABLE_WALLPAPER_TOUCH_EVENTS"), @ViewDebug.FlagToString(equals=4096, mask=4096, name="FORCE_STATUS_BAR_VISIBLE_TRANSPARENT"), @ViewDebug.FlagToString(equals=8192, mask=8192, name="PRESERVE_GEOMETRY"), @ViewDebug.FlagToString(equals=16384, mask=16384, name="FORCE_DECOR_VIEW_VISIBILITY"), @ViewDebug.FlagToString(equals=32768, mask=32768, name="WILL_NOT_REPLACE_ON_RELAUNCH"), @ViewDebug.FlagToString(equals=65536, mask=65536, name="LAYOUT_CHILD_WINDOW_IN_PARENT_FRAME"), @ViewDebug.FlagToString(equals=131072, mask=131072, name="FORCE_DRAW_STATUS_BAR_BACKGROUND"), @ViewDebug.FlagToString(equals=262144, mask=262144, name="SUSTAINED_PERFORMANCE_MODE"), @ViewDebug.FlagToString(equals=524288, mask=524288, name="HIDE_NON_SYSTEM_OVERLAY_WINDOWS"), @ViewDebug.FlagToString(equals=1048576, mask=1048576, name="IS_ROUNDED_CORNERS_OVERLAY"), @ViewDebug.FlagToString(equals=4194304, mask=4194304, name="IS_SCREEN_DECOR"), @ViewDebug.FlagToString(equals=8388608, mask=8388608, name="STATUS_FORCE_SHOW_NAVIGATION"), @ViewDebug.FlagToString(equals=16777216, mask=16777216, name="COLOR_SPACE_AGNOSTIC")})
        public int privateFlags;
        public int rotationAnimation;
        public float screenBrightness;
        public int screenOrientation;
        public int softInputMode;
        @UnsupportedAppUsage
        public int subtreeSystemUiVisibility;
        public final Rect surfaceInsets;
        public int systemUiVisibility;
        public IBinder token;
        @ViewDebug.ExportedProperty(mapping={@ViewDebug.IntToString(from=1, to="BASE_APPLICATION"), @ViewDebug.IntToString(from=2, to="APPLICATION"), @ViewDebug.IntToString(from=3, to="APPLICATION_STARTING"), @ViewDebug.IntToString(from=4, to="DRAWN_APPLICATION"), @ViewDebug.IntToString(from=1000, to="APPLICATION_PANEL"), @ViewDebug.IntToString(from=1001, to="APPLICATION_MEDIA"), @ViewDebug.IntToString(from=1002, to="APPLICATION_SUB_PANEL"), @ViewDebug.IntToString(from=1005, to="APPLICATION_ABOVE_SUB_PANEL"), @ViewDebug.IntToString(from=1003, to="APPLICATION_ATTACHED_DIALOG"), @ViewDebug.IntToString(from=1004, to="APPLICATION_MEDIA_OVERLAY"), @ViewDebug.IntToString(from=2000, to="STATUS_BAR"), @ViewDebug.IntToString(from=2001, to="SEARCH_BAR"), @ViewDebug.IntToString(from=2002, to="PHONE"), @ViewDebug.IntToString(from=2003, to="SYSTEM_ALERT"), @ViewDebug.IntToString(from=2005, to="TOAST"), @ViewDebug.IntToString(from=2006, to="SYSTEM_OVERLAY"), @ViewDebug.IntToString(from=2007, to="PRIORITY_PHONE"), @ViewDebug.IntToString(from=2008, to="SYSTEM_DIALOG"), @ViewDebug.IntToString(from=2009, to="KEYGUARD_DIALOG"), @ViewDebug.IntToString(from=2010, to="SYSTEM_ERROR"), @ViewDebug.IntToString(from=2011, to="INPUT_METHOD"), @ViewDebug.IntToString(from=2012, to="INPUT_METHOD_DIALOG"), @ViewDebug.IntToString(from=2013, to="WALLPAPER"), @ViewDebug.IntToString(from=2014, to="STATUS_BAR_PANEL"), @ViewDebug.IntToString(from=2015, to="SECURE_SYSTEM_OVERLAY"), @ViewDebug.IntToString(from=2016, to="DRAG"), @ViewDebug.IntToString(from=2017, to="STATUS_BAR_SUB_PANEL"), @ViewDebug.IntToString(from=2018, to="POINTER"), @ViewDebug.IntToString(from=2019, to="NAVIGATION_BAR"), @ViewDebug.IntToString(from=2020, to="VOLUME_OVERLAY"), @ViewDebug.IntToString(from=2021, to="BOOT_PROGRESS"), @ViewDebug.IntToString(from=2022, to="INPUT_CONSUMER"), @ViewDebug.IntToString(from=2023, to="DREAM"), @ViewDebug.IntToString(from=2024, to="NAVIGATION_BAR_PANEL"), @ViewDebug.IntToString(from=2026, to="DISPLAY_OVERLAY"), @ViewDebug.IntToString(from=2027, to="MAGNIFICATION_OVERLAY"), @ViewDebug.IntToString(from=2037, to="PRESENTATION"), @ViewDebug.IntToString(from=2030, to="PRIVATE_PRESENTATION"), @ViewDebug.IntToString(from=2031, to="VOICE_INTERACTION"), @ViewDebug.IntToString(from=2033, to="VOICE_INTERACTION_STARTING"), @ViewDebug.IntToString(from=2034, to="DOCK_DIVIDER"), @ViewDebug.IntToString(from=2035, to="QS_DIALOG"), @ViewDebug.IntToString(from=2036, to="SCREENSHOT"), @ViewDebug.IntToString(from=2038, to="APPLICATION_OVERLAY")})
        public int type;
        @UnsupportedAppUsage
        public long userActivityTimeout;
        public float verticalMargin;
        @ViewDebug.ExportedProperty
        public float verticalWeight;
        public int windowAnimations;
        @ViewDebug.ExportedProperty
        public int x;
        @ViewDebug.ExportedProperty
        public int y;

        public LayoutParams() {
            super(-1, -1);
            this.needsMenuKey = 0;
            this.surfaceInsets = new Rect();
            this.preservePreviousSurfaceInsets = true;
            this.alpha = 1.0f;
            this.dimAmount = 1.0f;
            this.screenBrightness = -1.0f;
            this.buttonBrightness = -1.0f;
            this.rotationAnimation = 0;
            this.token = null;
            this.packageName = null;
            this.screenOrientation = -1;
            this.layoutInDisplayCutoutMode = 0;
            this.userActivityTimeout = -1L;
            this.accessibilityIdOfAnchor = AccessibilityNodeInfo.UNDEFINED_NODE_ID;
            this.hideTimeoutMilliseconds = -1L;
            this.mColorMode = 0;
            this.mCompatibilityParamsBackup = null;
            this.mTitle = null;
            this.type = 2;
            this.format = -1;
        }

        public LayoutParams(int n) {
            super(-1, -1);
            this.needsMenuKey = 0;
            this.surfaceInsets = new Rect();
            this.preservePreviousSurfaceInsets = true;
            this.alpha = 1.0f;
            this.dimAmount = 1.0f;
            this.screenBrightness = -1.0f;
            this.buttonBrightness = -1.0f;
            this.rotationAnimation = 0;
            this.token = null;
            this.packageName = null;
            this.screenOrientation = -1;
            this.layoutInDisplayCutoutMode = 0;
            this.userActivityTimeout = -1L;
            this.accessibilityIdOfAnchor = AccessibilityNodeInfo.UNDEFINED_NODE_ID;
            this.hideTimeoutMilliseconds = -1L;
            this.mColorMode = 0;
            this.mCompatibilityParamsBackup = null;
            this.mTitle = null;
            this.type = n;
            this.format = -1;
        }

        public LayoutParams(int n, int n2) {
            super(-1, -1);
            this.needsMenuKey = 0;
            this.surfaceInsets = new Rect();
            this.preservePreviousSurfaceInsets = true;
            this.alpha = 1.0f;
            this.dimAmount = 1.0f;
            this.screenBrightness = -1.0f;
            this.buttonBrightness = -1.0f;
            this.rotationAnimation = 0;
            this.token = null;
            this.packageName = null;
            this.screenOrientation = -1;
            this.layoutInDisplayCutoutMode = 0;
            this.userActivityTimeout = -1L;
            this.accessibilityIdOfAnchor = AccessibilityNodeInfo.UNDEFINED_NODE_ID;
            this.hideTimeoutMilliseconds = -1L;
            this.mColorMode = 0;
            this.mCompatibilityParamsBackup = null;
            this.mTitle = null;
            this.type = n;
            this.flags = n2;
            this.format = -1;
        }

        public LayoutParams(int n, int n2, int n3) {
            super(-1, -1);
            this.needsMenuKey = 0;
            this.surfaceInsets = new Rect();
            this.preservePreviousSurfaceInsets = true;
            this.alpha = 1.0f;
            this.dimAmount = 1.0f;
            this.screenBrightness = -1.0f;
            this.buttonBrightness = -1.0f;
            this.rotationAnimation = 0;
            this.token = null;
            this.packageName = null;
            this.screenOrientation = -1;
            this.layoutInDisplayCutoutMode = 0;
            this.userActivityTimeout = -1L;
            this.accessibilityIdOfAnchor = AccessibilityNodeInfo.UNDEFINED_NODE_ID;
            this.hideTimeoutMilliseconds = -1L;
            this.mColorMode = 0;
            this.mCompatibilityParamsBackup = null;
            this.mTitle = null;
            this.type = n;
            this.flags = n2;
            this.format = n3;
        }

        public LayoutParams(int n, int n2, int n3, int n4, int n5) {
            super(n, n2);
            this.needsMenuKey = 0;
            this.surfaceInsets = new Rect();
            this.preservePreviousSurfaceInsets = true;
            this.alpha = 1.0f;
            this.dimAmount = 1.0f;
            this.screenBrightness = -1.0f;
            this.buttonBrightness = -1.0f;
            this.rotationAnimation = 0;
            this.token = null;
            this.packageName = null;
            this.screenOrientation = -1;
            this.layoutInDisplayCutoutMode = 0;
            this.userActivityTimeout = -1L;
            this.accessibilityIdOfAnchor = AccessibilityNodeInfo.UNDEFINED_NODE_ID;
            this.hideTimeoutMilliseconds = -1L;
            this.mColorMode = 0;
            this.mCompatibilityParamsBackup = null;
            this.mTitle = null;
            this.type = n3;
            this.flags = n4;
            this.format = n5;
        }

        public LayoutParams(int n, int n2, int n3, int n4, int n5, int n6, int n7) {
            super(n, n2);
            this.needsMenuKey = 0;
            this.surfaceInsets = new Rect();
            this.preservePreviousSurfaceInsets = true;
            this.alpha = 1.0f;
            this.dimAmount = 1.0f;
            this.screenBrightness = -1.0f;
            this.buttonBrightness = -1.0f;
            this.rotationAnimation = 0;
            this.token = null;
            this.packageName = null;
            this.screenOrientation = -1;
            this.layoutInDisplayCutoutMode = 0;
            this.userActivityTimeout = -1L;
            this.accessibilityIdOfAnchor = AccessibilityNodeInfo.UNDEFINED_NODE_ID;
            this.hideTimeoutMilliseconds = -1L;
            this.mColorMode = 0;
            this.mCompatibilityParamsBackup = null;
            this.mTitle = null;
            this.x = n3;
            this.y = n4;
            this.type = n5;
            this.flags = n6;
            this.format = n7;
        }

        public LayoutParams(Parcel parcel) {
            boolean bl = false;
            this.needsMenuKey = 0;
            this.surfaceInsets = new Rect();
            this.preservePreviousSurfaceInsets = true;
            this.alpha = 1.0f;
            this.dimAmount = 1.0f;
            this.screenBrightness = -1.0f;
            this.buttonBrightness = -1.0f;
            this.rotationAnimation = 0;
            this.token = null;
            this.packageName = null;
            this.screenOrientation = -1;
            this.layoutInDisplayCutoutMode = 0;
            this.userActivityTimeout = -1L;
            this.accessibilityIdOfAnchor = AccessibilityNodeInfo.UNDEFINED_NODE_ID;
            this.hideTimeoutMilliseconds = -1L;
            this.mColorMode = 0;
            this.mCompatibilityParamsBackup = null;
            this.mTitle = null;
            this.width = parcel.readInt();
            this.height = parcel.readInt();
            this.x = parcel.readInt();
            this.y = parcel.readInt();
            this.type = parcel.readInt();
            this.flags = parcel.readInt();
            this.privateFlags = parcel.readInt();
            this.softInputMode = parcel.readInt();
            this.layoutInDisplayCutoutMode = parcel.readInt();
            this.gravity = parcel.readInt();
            this.horizontalMargin = parcel.readFloat();
            this.verticalMargin = parcel.readFloat();
            this.format = parcel.readInt();
            this.windowAnimations = parcel.readInt();
            this.alpha = parcel.readFloat();
            this.dimAmount = parcel.readFloat();
            this.screenBrightness = parcel.readFloat();
            this.buttonBrightness = parcel.readFloat();
            this.rotationAnimation = parcel.readInt();
            this.token = parcel.readStrongBinder();
            this.packageName = parcel.readString();
            this.mTitle = TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel);
            this.screenOrientation = parcel.readInt();
            this.preferredRefreshRate = parcel.readFloat();
            this.preferredDisplayModeId = parcel.readInt();
            this.systemUiVisibility = parcel.readInt();
            this.subtreeSystemUiVisibility = parcel.readInt();
            boolean bl2 = parcel.readInt() != 0;
            this.hasSystemUiListeners = bl2;
            this.inputFeatures = parcel.readInt();
            this.userActivityTimeout = parcel.readLong();
            this.surfaceInsets.left = parcel.readInt();
            this.surfaceInsets.top = parcel.readInt();
            this.surfaceInsets.right = parcel.readInt();
            this.surfaceInsets.bottom = parcel.readInt();
            bl2 = parcel.readInt() != 0;
            this.hasManualSurfaceInsets = bl2;
            bl2 = bl;
            if (parcel.readInt() != 0) {
                bl2 = true;
            }
            this.preservePreviousSurfaceInsets = bl2;
            this.needsMenuKey = parcel.readInt();
            this.accessibilityIdOfAnchor = parcel.readLong();
            this.accessibilityTitle = TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel);
            this.mColorMode = parcel.readInt();
            this.hideTimeoutMilliseconds = parcel.readLong();
        }

        private static String inputFeatureToString(int n) {
            if (n != 1) {
                if (n != 2) {
                    if (n != 4) {
                        return Integer.toString(n);
                    }
                    return "DISABLE_USER_ACTIVITY";
                }
                return "NO_INPUT_CHANNEL";
            }
            return "DISABLE_POINTER_GESTURES";
        }

        public static boolean isSystemAlertWindowType(int n) {
            return n == 2002 || n == 2003 || n == 2006 || n == 2007 || n == 2010 || n == 2038;
        }

        private static String layoutInDisplayCutoutModeToString(int n) {
            if (n != 0) {
                if (n != 1) {
                    if (n != 2) {
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("unknown(");
                        stringBuilder.append(n);
                        stringBuilder.append(")");
                        return stringBuilder.toString();
                    }
                    return "never";
                }
                return "always";
            }
            return "default";
        }

        public static boolean mayUseInputMethod(int n) {
            return (n &= 131080) == 0 || n == 131080;
        }

        private static String rotationAnimationToString(int n) {
            if (n != -1) {
                if (n != 0) {
                    if (n != 1) {
                        if (n != 2) {
                            if (n != 3) {
                                return Integer.toString(n);
                            }
                            return "SEAMLESS";
                        }
                        return "JUMPCUT";
                    }
                    return "CROSSFADE";
                }
                return "ROTATE";
            }
            return "UNSPECIFIED";
        }

        private static String softInputModeToString(int n) {
            StringBuilder stringBuilder = new StringBuilder();
            int n2 = n & 15;
            if (n2 != 0) {
                stringBuilder.append("state=");
                if (n2 != 1) {
                    if (n2 != 2) {
                        if (n2 != 3) {
                            if (n2 != 4) {
                                if (n2 != 5) {
                                    stringBuilder.append(n2);
                                } else {
                                    stringBuilder.append("always_visible");
                                }
                            } else {
                                stringBuilder.append("visible");
                            }
                        } else {
                            stringBuilder.append("always_hidden");
                        }
                    } else {
                        stringBuilder.append("hidden");
                    }
                } else {
                    stringBuilder.append("unchanged");
                }
                stringBuilder.append(' ');
            }
            if ((n2 = n & 240) != 0) {
                stringBuilder.append("adjust=");
                if (n2 != 16) {
                    if (n2 != 32) {
                        if (n2 != 48) {
                            stringBuilder.append(n2);
                        } else {
                            stringBuilder.append("nothing");
                        }
                    } else {
                        stringBuilder.append("pan");
                    }
                } else {
                    stringBuilder.append("resize");
                }
                stringBuilder.append(' ');
            }
            if ((n & 256) != 0) {
                stringBuilder.append("forwardNavigation");
                stringBuilder.append(' ');
            }
            stringBuilder.deleteCharAt(stringBuilder.length() - 1);
            return stringBuilder.toString();
        }

        @UnsupportedAppUsage
        void backup() {
            int[] arrn;
            int[] arrn2 = arrn = this.mCompatibilityParamsBackup;
            if (arrn == null) {
                this.mCompatibilityParamsBackup = arrn2 = new int[4];
            }
            arrn2[0] = this.x;
            arrn2[1] = this.y;
            arrn2[2] = this.width;
            arrn2[3] = this.height;
        }

        public final int copyFrom(LayoutParams layoutParams) {
            CharSequence charSequence;
            int n;
            int n2;
            int n3;
            int n4;
            block44 : {
                block43 : {
                    n = 0;
                    if (this.width != layoutParams.width) {
                        this.width = layoutParams.width;
                        n = false | true;
                    }
                    n3 = n;
                    if (this.height != layoutParams.height) {
                        this.height = layoutParams.height;
                        n3 = n | true;
                    }
                    n4 = this.x;
                    n2 = layoutParams.x;
                    n = n3;
                    if (n4 != n2) {
                        this.x = n2;
                        n = n3 | true;
                    }
                    n4 = this.y;
                    n2 = layoutParams.y;
                    n3 = n;
                    if (n4 != n2) {
                        this.y = n2;
                        n3 = n | true;
                    }
                    float f = this.horizontalWeight;
                    float f2 = layoutParams.horizontalWeight;
                    n = n3;
                    if (f != f2) {
                        this.horizontalWeight = f2;
                        n = n3 | true;
                    }
                    f = this.verticalWeight;
                    f2 = layoutParams.verticalWeight;
                    n3 = n;
                    if (f != f2) {
                        this.verticalWeight = f2;
                        n3 = n | true;
                    }
                    f2 = this.horizontalMargin;
                    f = layoutParams.horizontalMargin;
                    n = n3;
                    if (f2 != f) {
                        this.horizontalMargin = f;
                        n = n3 | true;
                    }
                    f2 = this.verticalMargin;
                    f = layoutParams.verticalMargin;
                    n3 = n;
                    if (f2 != f) {
                        this.verticalMargin = f;
                        n3 = n | true;
                    }
                    n4 = this.type;
                    n2 = layoutParams.type;
                    n = n3;
                    if (n4 != n2) {
                        this.type = n2;
                        n = n3 | 2;
                    }
                    n4 = this.flags;
                    n2 = layoutParams.flags;
                    n3 = n;
                    if (n4 != n2) {
                        n3 = n;
                        if ((201326592 & (n4 ^ n2)) != 0) {
                            n3 = n | 524288;
                        }
                        this.flags = layoutParams.flags;
                        n3 |= 4;
                    }
                    n2 = this.privateFlags;
                    n4 = layoutParams.privateFlags;
                    n = n3;
                    if (n2 != n4) {
                        this.privateFlags = n4;
                        n = n3 | 131072;
                    }
                    n3 = this.softInputMode;
                    n4 = layoutParams.softInputMode;
                    n2 = n;
                    if (n3 != n4) {
                        this.softInputMode = n4;
                        n2 = n | 512;
                    }
                    n = this.layoutInDisplayCutoutMode;
                    n4 = layoutParams.layoutInDisplayCutoutMode;
                    n3 = n2;
                    if (n != n4) {
                        this.layoutInDisplayCutoutMode = n4;
                        n3 = n2 | 1;
                    }
                    n4 = this.gravity;
                    n2 = layoutParams.gravity;
                    n = n3;
                    if (n4 != n2) {
                        this.gravity = n2;
                        n = n3 | 1;
                    }
                    n4 = this.format;
                    n2 = layoutParams.format;
                    n3 = n;
                    if (n4 != n2) {
                        this.format = n2;
                        n3 = n | 8;
                    }
                    n2 = this.windowAnimations;
                    n4 = layoutParams.windowAnimations;
                    n = n3;
                    if (n2 != n4) {
                        this.windowAnimations = n4;
                        n = n3 | 16;
                    }
                    if (this.token == null) {
                        this.token = layoutParams.token;
                    }
                    if (this.packageName == null) {
                        this.packageName = layoutParams.packageName;
                    }
                    n3 = n;
                    if (!Objects.equals(this.mTitle, layoutParams.mTitle)) {
                        charSequence = layoutParams.mTitle;
                        n3 = n;
                        if (charSequence != null) {
                            this.mTitle = charSequence;
                            n3 = n | 64;
                        }
                    }
                    f = this.alpha;
                    f2 = layoutParams.alpha;
                    n2 = n3;
                    if (f != f2) {
                        this.alpha = f2;
                        n2 = n3 | 128;
                    }
                    f = this.dimAmount;
                    f2 = layoutParams.dimAmount;
                    n = n2;
                    if (f != f2) {
                        this.dimAmount = f2;
                        n = n2 | 32;
                    }
                    f2 = this.screenBrightness;
                    f = layoutParams.screenBrightness;
                    n2 = n;
                    if (f2 != f) {
                        this.screenBrightness = f;
                        n2 = n | 2048;
                    }
                    f2 = this.buttonBrightness;
                    f = layoutParams.buttonBrightness;
                    n3 = n2;
                    if (f2 != f) {
                        this.buttonBrightness = f;
                        n3 = n2 | 8192;
                    }
                    n4 = this.rotationAnimation;
                    n2 = layoutParams.rotationAnimation;
                    n = n3;
                    if (n4 != n2) {
                        this.rotationAnimation = n2;
                        n = n3 | 4096;
                    }
                    n4 = this.screenOrientation;
                    n3 = layoutParams.screenOrientation;
                    n2 = n;
                    if (n4 != n3) {
                        this.screenOrientation = n3;
                        n2 = n | 1024;
                    }
                    f = this.preferredRefreshRate;
                    f2 = layoutParams.preferredRefreshRate;
                    n3 = n2;
                    if (f != f2) {
                        this.preferredRefreshRate = f2;
                        n3 = n2 | 2097152;
                    }
                    n2 = this.preferredDisplayModeId;
                    n4 = layoutParams.preferredDisplayModeId;
                    n = n3;
                    if (n2 != n4) {
                        this.preferredDisplayModeId = n4;
                        n = n3 | 8388608;
                    }
                    if (this.systemUiVisibility != layoutParams.systemUiVisibility) break block43;
                    n2 = n;
                    if (this.subtreeSystemUiVisibility == layoutParams.subtreeSystemUiVisibility) break block44;
                }
                this.systemUiVisibility = layoutParams.systemUiVisibility;
                this.subtreeSystemUiVisibility = layoutParams.subtreeSystemUiVisibility;
                n2 = n | 16384;
            }
            boolean bl = this.hasSystemUiListeners;
            boolean bl2 = layoutParams.hasSystemUiListeners;
            n3 = n2;
            if (bl != bl2) {
                this.hasSystemUiListeners = bl2;
                n3 = n2 | 32768;
            }
            n4 = this.inputFeatures;
            n2 = layoutParams.inputFeatures;
            n = n3;
            if (n4 != n2) {
                this.inputFeatures = n2;
                n = n3 | 65536;
            }
            long l = this.userActivityTimeout;
            long l2 = layoutParams.userActivityTimeout;
            n2 = n;
            if (l != l2) {
                this.userActivityTimeout = l2;
                n2 = n | 262144;
            }
            n3 = n2;
            if (!this.surfaceInsets.equals(layoutParams.surfaceInsets)) {
                this.surfaceInsets.set(layoutParams.surfaceInsets);
                n3 = n2 | 1048576;
            }
            bl = this.hasManualSurfaceInsets;
            bl2 = layoutParams.hasManualSurfaceInsets;
            n = n3;
            if (bl != bl2) {
                this.hasManualSurfaceInsets = bl2;
                n = n3 | 1048576;
            }
            bl2 = this.preservePreviousSurfaceInsets;
            bl = layoutParams.preservePreviousSurfaceInsets;
            n3 = n;
            if (bl2 != bl) {
                this.preservePreviousSurfaceInsets = bl;
                n3 = n | 1048576;
            }
            n4 = this.needsMenuKey;
            n2 = layoutParams.needsMenuKey;
            n = n3;
            if (n4 != n2) {
                this.needsMenuKey = n2;
                n = n3 | 4194304;
            }
            l = this.accessibilityIdOfAnchor;
            l2 = layoutParams.accessibilityIdOfAnchor;
            n3 = n;
            if (l != l2) {
                this.accessibilityIdOfAnchor = l2;
                n3 = n | 16777216;
            }
            n = n3;
            if (!Objects.equals(this.accessibilityTitle, layoutParams.accessibilityTitle)) {
                charSequence = layoutParams.accessibilityTitle;
                n = n3;
                if (charSequence != null) {
                    this.accessibilityTitle = charSequence;
                    n = n3 | 33554432;
                }
            }
            n2 = this.mColorMode;
            n4 = layoutParams.mColorMode;
            n3 = n;
            if (n2 != n4) {
                this.mColorMode = n4;
                n3 = n | 67108864;
            }
            this.hideTimeoutMilliseconds = layoutParams.hideTimeoutMilliseconds;
            return n3;
        }

        @Override
        public String debug(String charSequence) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append((String)charSequence);
            stringBuilder.append("Contents of ");
            stringBuilder.append(this);
            stringBuilder.append(":");
            Log.d("Debug", stringBuilder.toString());
            Log.d("Debug", super.debug(""));
            Log.d("Debug", "");
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("WindowManager.LayoutParams={title=");
            ((StringBuilder)charSequence).append((Object)this.mTitle);
            ((StringBuilder)charSequence).append("}");
            Log.d("Debug", ((StringBuilder)charSequence).toString());
            return "";
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public void dumpDimensions(StringBuilder stringBuilder) {
            stringBuilder.append('(');
            stringBuilder.append(this.x);
            stringBuilder.append(',');
            stringBuilder.append(this.y);
            stringBuilder.append(")(");
            int n = this.width;
            String string2 = "wrap";
            String string3 = n == -1 ? "fill" : (this.width == -2 ? "wrap" : String.valueOf(this.width));
            stringBuilder.append(string3);
            stringBuilder.append('x');
            string3 = this.height == -1 ? "fill" : (this.height == -2 ? string2 : String.valueOf(this.height));
            stringBuilder.append(string3);
            stringBuilder.append(")");
        }

        @Override
        protected void encodeProperties(ViewHierarchyEncoder viewHierarchyEncoder) {
            super.encodeProperties(viewHierarchyEncoder);
            viewHierarchyEncoder.addProperty("x", this.x);
            viewHierarchyEncoder.addProperty("y", this.y);
            viewHierarchyEncoder.addProperty("horizontalWeight", this.horizontalWeight);
            viewHierarchyEncoder.addProperty("verticalWeight", this.verticalWeight);
            viewHierarchyEncoder.addProperty("type", this.type);
            viewHierarchyEncoder.addProperty("flags", this.flags);
        }

        public int getColorMode() {
            return this.mColorMode;
        }

        public final CharSequence getTitle() {
            CharSequence charSequence = this.mTitle;
            if (charSequence == null) {
                charSequence = "";
            }
            return charSequence;
        }

        @SystemApi
        public final long getUserActivityTimeout() {
            return this.userActivityTimeout;
        }

        public boolean isFullscreen() {
            boolean bl = this.x == 0 && this.y == 0 && this.width == -1 && this.height == -1;
            return bl;
        }

        @UnsupportedAppUsage
        void restore() {
            int[] arrn = this.mCompatibilityParamsBackup;
            if (arrn != null) {
                this.x = arrn[0];
                this.y = arrn[1];
                this.width = arrn[2];
                this.height = arrn[3];
            }
        }

        public void scale(float f) {
            this.x = (int)((float)this.x * f + 0.5f);
            this.y = (int)((float)this.y * f + 0.5f);
            if (this.width > 0) {
                this.width = (int)((float)this.width * f + 0.5f);
            }
            if (this.height > 0) {
                this.height = (int)((float)this.height * f + 0.5f);
            }
        }

        public void setColorMode(int n) {
            this.mColorMode = n;
        }

        public final void setSurfaceInsets(View object, boolean bl, boolean bl2) {
            int n = (int)Math.ceil(((View)object).getZ() * 2.0f);
            if (n == 0) {
                this.surfaceInsets.set(0, 0, 0, 0);
            } else {
                object = this.surfaceInsets;
                ((Rect)object).set(Math.max(n, ((Rect)object).left), Math.max(n, this.surfaceInsets.top), Math.max(n, this.surfaceInsets.right), Math.max(n, this.surfaceInsets.bottom));
            }
            this.hasManualSurfaceInsets = bl;
            this.preservePreviousSurfaceInsets = bl2;
        }

        public final void setTitle(CharSequence charSequence) {
            CharSequence charSequence2 = charSequence;
            if (charSequence == null) {
                charSequence2 = "";
            }
            this.mTitle = TextUtils.stringOrSpannedString(charSequence2);
        }

        @SystemApi
        public final void setUserActivityTimeout(long l) {
            this.userActivityTimeout = l;
        }

        public String toString() {
            return this.toString("");
        }

        public String toString(String string2) {
            StringBuilder stringBuilder = new StringBuilder(256);
            stringBuilder.append('{');
            this.dumpDimensions(stringBuilder);
            if (this.horizontalMargin != 0.0f) {
                stringBuilder.append(" hm=");
                stringBuilder.append(this.horizontalMargin);
            }
            if (this.verticalMargin != 0.0f) {
                stringBuilder.append(" vm=");
                stringBuilder.append(this.verticalMargin);
            }
            if (this.gravity != 0) {
                stringBuilder.append(" gr=");
                stringBuilder.append(Gravity.toString(this.gravity));
            }
            if (this.softInputMode != 0) {
                stringBuilder.append(" sim={");
                stringBuilder.append(LayoutParams.softInputModeToString(this.softInputMode));
                stringBuilder.append('}');
            }
            if (this.layoutInDisplayCutoutMode != 0) {
                stringBuilder.append(" layoutInDisplayCutoutMode=");
                stringBuilder.append(LayoutParams.layoutInDisplayCutoutModeToString(this.layoutInDisplayCutoutMode));
            }
            stringBuilder.append(" ty=");
            stringBuilder.append(ViewDebug.intToString(LayoutParams.class, "type", this.type));
            if (this.format != -1) {
                stringBuilder.append(" fmt=");
                stringBuilder.append(PixelFormat.formatToString(this.format));
            }
            if (this.windowAnimations != 0) {
                stringBuilder.append(" wanim=0x");
                stringBuilder.append(Integer.toHexString(this.windowAnimations));
            }
            if (this.screenOrientation != -1) {
                stringBuilder.append(" or=");
                stringBuilder.append(ActivityInfo.screenOrientationToString(this.screenOrientation));
            }
            if (this.alpha != 1.0f) {
                stringBuilder.append(" alpha=");
                stringBuilder.append(this.alpha);
            }
            if (this.screenBrightness != -1.0f) {
                stringBuilder.append(" sbrt=");
                stringBuilder.append(this.screenBrightness);
            }
            if (this.buttonBrightness != -1.0f) {
                stringBuilder.append(" bbrt=");
                stringBuilder.append(this.buttonBrightness);
            }
            if (this.rotationAnimation != 0) {
                stringBuilder.append(" rotAnim=");
                stringBuilder.append(LayoutParams.rotationAnimationToString(this.rotationAnimation));
            }
            if (this.preferredRefreshRate != 0.0f) {
                stringBuilder.append(" preferredRefreshRate=");
                stringBuilder.append(this.preferredRefreshRate);
            }
            if (this.preferredDisplayModeId != 0) {
                stringBuilder.append(" preferredDisplayMode=");
                stringBuilder.append(this.preferredDisplayModeId);
            }
            if (this.hasSystemUiListeners) {
                stringBuilder.append(" sysuil=");
                stringBuilder.append(this.hasSystemUiListeners);
            }
            if (this.inputFeatures != 0) {
                stringBuilder.append(" if=");
                stringBuilder.append(LayoutParams.inputFeatureToString(this.inputFeatures));
            }
            if (this.userActivityTimeout >= 0L) {
                stringBuilder.append(" userActivityTimeout=");
                stringBuilder.append(this.userActivityTimeout);
            }
            if (this.surfaceInsets.left != 0 || this.surfaceInsets.top != 0 || this.surfaceInsets.right != 0 || this.surfaceInsets.bottom != 0 || this.hasManualSurfaceInsets || !this.preservePreviousSurfaceInsets) {
                stringBuilder.append(" surfaceInsets=");
                stringBuilder.append(this.surfaceInsets);
                if (this.hasManualSurfaceInsets) {
                    stringBuilder.append(" (manual)");
                }
                if (!this.preservePreviousSurfaceInsets) {
                    stringBuilder.append(" (!preservePreviousSurfaceInsets)");
                }
            }
            if (this.needsMenuKey == 1) {
                stringBuilder.append(" needsMenuKey");
            }
            if (this.mColorMode != 0) {
                stringBuilder.append(" colorMode=");
                stringBuilder.append(ActivityInfo.colorModeToString(this.mColorMode));
            }
            stringBuilder.append(System.lineSeparator());
            stringBuilder.append(string2);
            stringBuilder.append("  fl=");
            stringBuilder.append(ViewDebug.flagsToString(LayoutParams.class, "flags", this.flags));
            if (this.privateFlags != 0) {
                stringBuilder.append(System.lineSeparator());
                stringBuilder.append(string2);
                stringBuilder.append("  pfl=");
                stringBuilder.append(ViewDebug.flagsToString(LayoutParams.class, "privateFlags", this.privateFlags));
            }
            if (this.systemUiVisibility != 0) {
                stringBuilder.append(System.lineSeparator());
                stringBuilder.append(string2);
                stringBuilder.append("  sysui=");
                stringBuilder.append(ViewDebug.flagsToString(View.class, "mSystemUiVisibility", this.systemUiVisibility));
            }
            if (this.subtreeSystemUiVisibility != 0) {
                stringBuilder.append(System.lineSeparator());
                stringBuilder.append(string2);
                stringBuilder.append("  vsysui=");
                stringBuilder.append(ViewDebug.flagsToString(View.class, "mSystemUiVisibility", this.subtreeSystemUiVisibility));
            }
            stringBuilder.append('}');
            return stringBuilder.toString();
        }

        @Override
        public void writeToParcel(Parcel parcel, int n) {
            parcel.writeInt(this.width);
            parcel.writeInt(this.height);
            parcel.writeInt(this.x);
            parcel.writeInt(this.y);
            parcel.writeInt(this.type);
            parcel.writeInt(this.flags);
            parcel.writeInt(this.privateFlags);
            parcel.writeInt(this.softInputMode);
            parcel.writeInt(this.layoutInDisplayCutoutMode);
            parcel.writeInt(this.gravity);
            parcel.writeFloat(this.horizontalMargin);
            parcel.writeFloat(this.verticalMargin);
            parcel.writeInt(this.format);
            parcel.writeInt(this.windowAnimations);
            parcel.writeFloat(this.alpha);
            parcel.writeFloat(this.dimAmount);
            parcel.writeFloat(this.screenBrightness);
            parcel.writeFloat(this.buttonBrightness);
            parcel.writeInt(this.rotationAnimation);
            parcel.writeStrongBinder(this.token);
            parcel.writeString(this.packageName);
            TextUtils.writeToParcel(this.mTitle, parcel, n);
            parcel.writeInt(this.screenOrientation);
            parcel.writeFloat(this.preferredRefreshRate);
            parcel.writeInt(this.preferredDisplayModeId);
            parcel.writeInt(this.systemUiVisibility);
            parcel.writeInt(this.subtreeSystemUiVisibility);
            parcel.writeInt((int)this.hasSystemUiListeners);
            parcel.writeInt(this.inputFeatures);
            parcel.writeLong(this.userActivityTimeout);
            parcel.writeInt(this.surfaceInsets.left);
            parcel.writeInt(this.surfaceInsets.top);
            parcel.writeInt(this.surfaceInsets.right);
            parcel.writeInt(this.surfaceInsets.bottom);
            parcel.writeInt((int)this.hasManualSurfaceInsets);
            parcel.writeInt((int)this.preservePreviousSurfaceInsets);
            parcel.writeInt(this.needsMenuKey);
            parcel.writeLong(this.accessibilityIdOfAnchor);
            TextUtils.writeToParcel(this.accessibilityTitle, parcel, n);
            parcel.writeInt(this.mColorMode);
            parcel.writeLong(this.hideTimeoutMilliseconds);
        }

        public void writeToProto(ProtoOutputStream protoOutputStream, long l) {
            l = protoOutputStream.start(l);
            protoOutputStream.write(1120986464257L, this.type);
            protoOutputStream.write(1120986464258L, this.x);
            protoOutputStream.write(1120986464259L, this.y);
            protoOutputStream.write(1120986464260L, this.width);
            protoOutputStream.write(1120986464261L, this.height);
            protoOutputStream.write(1108101562374L, this.horizontalMargin);
            protoOutputStream.write(1108101562375L, this.verticalMargin);
            protoOutputStream.write(1120986464264L, this.gravity);
            protoOutputStream.write(1120986464265L, this.softInputMode);
            protoOutputStream.write(1159641169930L, this.format);
            protoOutputStream.write(1120986464267L, this.windowAnimations);
            protoOutputStream.write(1108101562380L, this.alpha);
            protoOutputStream.write(1108101562381L, this.screenBrightness);
            protoOutputStream.write(1108101562382L, this.buttonBrightness);
            protoOutputStream.write(1159641169935L, this.rotationAnimation);
            protoOutputStream.write(1108101562384L, this.preferredRefreshRate);
            protoOutputStream.write(1120986464273L, this.preferredDisplayModeId);
            protoOutputStream.write(1133871366162L, this.hasSystemUiListeners);
            protoOutputStream.write(1155346202643L, this.inputFeatures);
            protoOutputStream.write(1112396529684L, this.userActivityTimeout);
            protoOutputStream.write(1159641169942L, this.needsMenuKey);
            protoOutputStream.write(1159641169943L, this.mColorMode);
            protoOutputStream.write(1155346202648L, this.flags);
            protoOutputStream.write(1155346202650L, this.privateFlags);
            protoOutputStream.write(1155346202651L, this.systemUiVisibility);
            protoOutputStream.write(1155346202652L, this.subtreeSystemUiVisibility);
            protoOutputStream.end(l);
        }

        @Retention(value=RetentionPolicy.SOURCE)
        static @interface LayoutInDisplayCutoutMode {
        }

        @Retention(value=RetentionPolicy.SOURCE)
        public static @interface SoftInputModeFlags {
        }

        @SystemApi
        @Retention(value=RetentionPolicy.SOURCE)
        public static @interface SystemFlags {
        }

    }

    public static @interface RemoveContentMode {
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface TransitionFlags {
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface TransitionType {
    }

}

