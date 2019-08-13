/*
 * Decompiled with CFR 0.145.
 */
package android.content.pm;

import android.annotation.UnsupportedAppUsage;
import android.content.pm.ApplicationInfo;
import android.content.pm.ComponentInfo;
import android.content.res.TypedArray;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Printer;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class ActivityInfo
extends ComponentInfo
implements Parcelable {
    public static final int COLOR_MODE_DEFAULT = 0;
    public static final int COLOR_MODE_HDR = 2;
    public static final int COLOR_MODE_WIDE_COLOR_GAMUT = 1;
    public static final int CONFIG_ASSETS_PATHS = Integer.MIN_VALUE;
    public static final int CONFIG_COLOR_MODE = 16384;
    public static final int CONFIG_DENSITY = 4096;
    public static final int CONFIG_FONT_SCALE = 1073741824;
    public static final int CONFIG_KEYBOARD = 16;
    public static final int CONFIG_KEYBOARD_HIDDEN = 32;
    public static final int CONFIG_LAYOUT_DIRECTION = 8192;
    public static final int CONFIG_LOCALE = 4;
    public static final int CONFIG_MCC = 1;
    public static final int CONFIG_MNC = 2;
    public static int[] CONFIG_NATIVE_BITS = new int[]{2, 1, 4, 8, 16, 32, 64, 128, 2048, 4096, 512, 8192, 256, 16384, 65536};
    public static final int CONFIG_NAVIGATION = 64;
    public static final int CONFIG_ORIENTATION = 128;
    public static final int CONFIG_SCREEN_LAYOUT = 256;
    public static final int CONFIG_SCREEN_SIZE = 1024;
    public static final int CONFIG_SMALLEST_SCREEN_SIZE = 2048;
    public static final int CONFIG_TOUCHSCREEN = 8;
    public static final int CONFIG_UI_MODE = 512;
    public static final int CONFIG_WINDOW_CONFIGURATION = 536870912;
    public static final Parcelable.Creator<ActivityInfo> CREATOR = new Parcelable.Creator<ActivityInfo>(){

        @Override
        public ActivityInfo createFromParcel(Parcel parcel) {
            return new ActivityInfo(parcel);
        }

        public ActivityInfo[] newArray(int n) {
            return new ActivityInfo[n];
        }
    };
    public static final int DOCUMENT_LAUNCH_ALWAYS = 2;
    public static final int DOCUMENT_LAUNCH_INTO_EXISTING = 1;
    public static final int DOCUMENT_LAUNCH_NEVER = 3;
    public static final int DOCUMENT_LAUNCH_NONE = 0;
    @UnsupportedAppUsage
    public static final int FLAG_ALLOW_EMBEDDED = Integer.MIN_VALUE;
    public static final int FLAG_ALLOW_TASK_REPARENTING = 64;
    public static final int FLAG_ALWAYS_FOCUSABLE = 262144;
    public static final int FLAG_ALWAYS_RETAIN_TASK_STATE = 8;
    public static final int FLAG_AUTO_REMOVE_FROM_RECENTS = 8192;
    public static final int FLAG_CLEAR_TASK_ON_LAUNCH = 4;
    public static final int FLAG_ENABLE_VR_MODE = 32768;
    public static final int FLAG_EXCLUDE_FROM_RECENTS = 32;
    public static final int FLAG_FINISH_ON_CLOSE_SYSTEM_DIALOGS = 256;
    public static final int FLAG_FINISH_ON_TASK_LAUNCH = 2;
    public static final int FLAG_HARDWARE_ACCELERATED = 512;
    public static final int FLAG_IMMERSIVE = 2048;
    public static final int FLAG_IMPLICITLY_VISIBLE_TO_INSTANT_APP = 2097152;
    public static final int FLAG_INHERIT_SHOW_WHEN_LOCKED = 1;
    public static final int FLAG_MULTIPROCESS = 1;
    public static final int FLAG_NO_HISTORY = 128;
    public static final int FLAG_RELINQUISH_TASK_IDENTITY = 4096;
    public static final int FLAG_RESUME_WHILE_PAUSING = 16384;
    @UnsupportedAppUsage
    public static final int FLAG_SHOW_FOR_ALL_USERS = 1024;
    public static final int FLAG_SHOW_WHEN_LOCKED = 8388608;
    public static final int FLAG_SINGLE_USER = 1073741824;
    public static final int FLAG_STATE_NOT_NEEDED = 16;
    public static final int FLAG_SUPPORTS_PICTURE_IN_PICTURE = 4194304;
    public static final int FLAG_SYSTEM_USER_ONLY = 536870912;
    public static final int FLAG_TURN_SCREEN_ON = 16777216;
    public static final int FLAG_VISIBLE_TO_INSTANT_APP = 1048576;
    public static final int LAUNCH_MULTIPLE = 0;
    public static final int LAUNCH_SINGLE_INSTANCE = 3;
    public static final int LAUNCH_SINGLE_TASK = 2;
    public static final int LAUNCH_SINGLE_TOP = 1;
    public static final int LOCK_TASK_LAUNCH_MODE_ALWAYS = 2;
    public static final int LOCK_TASK_LAUNCH_MODE_DEFAULT = 0;
    public static final int LOCK_TASK_LAUNCH_MODE_IF_WHITELISTED = 3;
    public static final int LOCK_TASK_LAUNCH_MODE_NEVER = 1;
    public static final int PERSIST_ACROSS_REBOOTS = 2;
    public static final int PERSIST_NEVER = 1;
    public static final int PERSIST_ROOT_ONLY = 0;
    public static final int RESIZE_MODE_FORCE_RESIZABLE_LANDSCAPE_ONLY = 5;
    public static final int RESIZE_MODE_FORCE_RESIZABLE_PORTRAIT_ONLY = 6;
    public static final int RESIZE_MODE_FORCE_RESIZABLE_PRESERVE_ORIENTATION = 7;
    public static final int RESIZE_MODE_FORCE_RESIZEABLE = 4;
    public static final int RESIZE_MODE_RESIZEABLE = 2;
    public static final int RESIZE_MODE_RESIZEABLE_AND_PIPABLE_DEPRECATED = 3;
    public static final int RESIZE_MODE_RESIZEABLE_VIA_SDK_VERSION = 1;
    public static final int RESIZE_MODE_UNRESIZEABLE = 0;
    public static final int SCREEN_ORIENTATION_BEHIND = 3;
    public static final int SCREEN_ORIENTATION_FULL_SENSOR = 10;
    public static final int SCREEN_ORIENTATION_FULL_USER = 13;
    public static final int SCREEN_ORIENTATION_LANDSCAPE = 0;
    public static final int SCREEN_ORIENTATION_LOCKED = 14;
    public static final int SCREEN_ORIENTATION_NOSENSOR = 5;
    public static final int SCREEN_ORIENTATION_PORTRAIT = 1;
    public static final int SCREEN_ORIENTATION_REVERSE_LANDSCAPE = 8;
    public static final int SCREEN_ORIENTATION_REVERSE_PORTRAIT = 9;
    public static final int SCREEN_ORIENTATION_SENSOR = 4;
    public static final int SCREEN_ORIENTATION_SENSOR_LANDSCAPE = 6;
    public static final int SCREEN_ORIENTATION_SENSOR_PORTRAIT = 7;
    public static final int SCREEN_ORIENTATION_UNSET = -2;
    public static final int SCREEN_ORIENTATION_UNSPECIFIED = -1;
    public static final int SCREEN_ORIENTATION_USER = 2;
    public static final int SCREEN_ORIENTATION_USER_LANDSCAPE = 11;
    public static final int SCREEN_ORIENTATION_USER_PORTRAIT = 12;
    public static final int UIOPTION_SPLIT_ACTION_BAR_WHEN_NARROW = 1;
    public int colorMode = 0;
    public int configChanges;
    public int documentLaunchMode;
    public int flags;
    public int launchMode;
    public String launchToken;
    public int lockTaskLaunchMode;
    public float maxAspectRatio;
    public int maxRecents;
    public float minAspectRatio;
    public String parentActivityName;
    public String permission;
    public int persistableMode;
    public int privateFlags;
    public String requestedVrComponent;
    @UnsupportedAppUsage
    public int resizeMode = 2;
    public int rotationAnimation = -1;
    public int screenOrientation = -1;
    public int softInputMode;
    public String targetActivity;
    public String taskAffinity;
    public int theme;
    public int uiOptions = 0;
    public WindowLayout windowLayout;

    public ActivityInfo() {
    }

    public ActivityInfo(ActivityInfo activityInfo) {
        super(activityInfo);
        this.theme = activityInfo.theme;
        this.launchMode = activityInfo.launchMode;
        this.documentLaunchMode = activityInfo.documentLaunchMode;
        this.permission = activityInfo.permission;
        this.taskAffinity = activityInfo.taskAffinity;
        this.targetActivity = activityInfo.targetActivity;
        this.flags = activityInfo.flags;
        this.privateFlags = activityInfo.privateFlags;
        this.screenOrientation = activityInfo.screenOrientation;
        this.configChanges = activityInfo.configChanges;
        this.softInputMode = activityInfo.softInputMode;
        this.uiOptions = activityInfo.uiOptions;
        this.parentActivityName = activityInfo.parentActivityName;
        this.maxRecents = activityInfo.maxRecents;
        this.lockTaskLaunchMode = activityInfo.lockTaskLaunchMode;
        this.windowLayout = activityInfo.windowLayout;
        this.resizeMode = activityInfo.resizeMode;
        this.requestedVrComponent = activityInfo.requestedVrComponent;
        this.rotationAnimation = activityInfo.rotationAnimation;
        this.colorMode = activityInfo.colorMode;
        this.maxAspectRatio = activityInfo.maxAspectRatio;
        this.minAspectRatio = activityInfo.minAspectRatio;
    }

    private ActivityInfo(Parcel parcel) {
        super(parcel);
        this.theme = parcel.readInt();
        this.launchMode = parcel.readInt();
        this.documentLaunchMode = parcel.readInt();
        this.permission = parcel.readString();
        this.taskAffinity = parcel.readString();
        this.targetActivity = parcel.readString();
        this.launchToken = parcel.readString();
        this.flags = parcel.readInt();
        this.privateFlags = parcel.readInt();
        this.screenOrientation = parcel.readInt();
        this.configChanges = parcel.readInt();
        this.softInputMode = parcel.readInt();
        this.uiOptions = parcel.readInt();
        this.parentActivityName = parcel.readString();
        this.persistableMode = parcel.readInt();
        this.maxRecents = parcel.readInt();
        this.lockTaskLaunchMode = parcel.readInt();
        if (parcel.readInt() == 1) {
            this.windowLayout = new WindowLayout(parcel);
        }
        this.resizeMode = parcel.readInt();
        this.requestedVrComponent = parcel.readString();
        this.rotationAnimation = parcel.readInt();
        this.colorMode = parcel.readInt();
        this.maxAspectRatio = parcel.readFloat();
        this.minAspectRatio = parcel.readFloat();
    }

    @UnsupportedAppUsage
    public static int activityInfoConfigJavaToNative(int n) {
        int[] arrn;
        int n2 = 0;
        for (int i = 0; i < (arrn = CONFIG_NATIVE_BITS).length; ++i) {
            int n3 = n2;
            if ((1 << i & n) != 0) {
                n3 = n2 | arrn[i];
            }
            n2 = n3;
        }
        return n2;
    }

    public static int activityInfoConfigNativeToJava(int n) {
        int[] arrn;
        int n2 = 0;
        for (int i = 0; i < (arrn = CONFIG_NATIVE_BITS).length; ++i) {
            int n3 = n2;
            if ((arrn[i] & n) != 0) {
                n3 = n2 | 1 << i;
            }
            n2 = n3;
        }
        return n2;
    }

    public static String colorModeToString(int n) {
        if (n != 0) {
            if (n != 1) {
                if (n != 2) {
                    return Integer.toString(n);
                }
                return "COLOR_MODE_HDR";
            }
            return "COLOR_MODE_WIDE_COLOR_GAMUT";
        }
        return "COLOR_MODE_DEFAULT";
    }

    public static boolean isFixedOrientationLandscape(int n) {
        boolean bl = n == 0 || n == 6 || n == 8 || n == 11;
        return bl;
    }

    public static boolean isFixedOrientationPortrait(int n) {
        boolean bl;
        boolean bl2 = bl = true;
        if (n != 1) {
            bl2 = bl;
            if (n != 7) {
                bl2 = bl;
                if (n != 9) {
                    bl2 = n == 12 ? bl : false;
                }
            }
        }
        return bl2;
    }

    public static boolean isPreserveOrientationMode(int n) {
        boolean bl = n == 6 || n == 5 || n == 7;
        return bl;
    }

    @UnsupportedAppUsage
    public static boolean isResizeableMode(int n) {
        boolean bl;
        boolean bl2 = bl = true;
        if (n != 2) {
            bl2 = bl;
            if (n != 4) {
                bl2 = bl;
                if (n != 6) {
                    bl2 = bl;
                    if (n != 5) {
                        bl2 = bl;
                        if (n != 7) {
                            bl2 = n == 1 ? bl : false;
                        }
                    }
                }
            }
        }
        return bl2;
    }

    public static boolean isTranslucentOrFloating(TypedArray typedArray) {
        boolean bl = false;
        boolean bl2 = typedArray.getBoolean(5, false);
        boolean bl3 = !typedArray.hasValue(5) && typedArray.getBoolean(25, false);
        if (typedArray.getBoolean(4, false) || bl2 || bl3) {
            bl = true;
        }
        return bl;
    }

    public static final String lockTaskLaunchModeToString(int n) {
        if (n != 0) {
            if (n != 1) {
                if (n != 2) {
                    if (n != 3) {
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("unknown=");
                        stringBuilder.append(n);
                        return stringBuilder.toString();
                    }
                    return "LOCK_TASK_LAUNCH_MODE_IF_WHITELISTED";
                }
                return "LOCK_TASK_LAUNCH_MODE_ALWAYS";
            }
            return "LOCK_TASK_LAUNCH_MODE_NEVER";
        }
        return "LOCK_TASK_LAUNCH_MODE_DEFAULT";
    }

    private String persistableModeToString() {
        int n = this.persistableMode;
        if (n != 0) {
            if (n != 1) {
                if (n != 2) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("UNKNOWN=");
                    stringBuilder.append(this.persistableMode);
                    return stringBuilder.toString();
                }
                return "PERSIST_ACROSS_REBOOTS";
            }
            return "PERSIST_NEVER";
        }
        return "PERSIST_ROOT_ONLY";
    }

    public static String resizeModeToString(int n) {
        if (n != 0) {
            if (n != 1) {
                if (n != 2) {
                    if (n != 4) {
                        if (n != 5) {
                            if (n != 6) {
                                if (n != 7) {
                                    StringBuilder stringBuilder = new StringBuilder();
                                    stringBuilder.append("unknown=");
                                    stringBuilder.append(n);
                                    return stringBuilder.toString();
                                }
                                return "RESIZE_MODE_FORCE_RESIZABLE_PRESERVE_ORIENTATION";
                            }
                            return "RESIZE_MODE_FORCE_RESIZABLE_PORTRAIT_ONLY";
                        }
                        return "RESIZE_MODE_FORCE_RESIZABLE_LANDSCAPE_ONLY";
                    }
                    return "RESIZE_MODE_FORCE_RESIZEABLE";
                }
                return "RESIZE_MODE_RESIZEABLE";
            }
            return "RESIZE_MODE_RESIZEABLE_VIA_SDK_VERSION";
        }
        return "RESIZE_MODE_UNRESIZEABLE";
    }

    public static String screenOrientationToString(int n) {
        switch (n) {
            default: {
                return Integer.toString(n);
            }
            case 14: {
                return "SCREEN_ORIENTATION_LOCKED";
            }
            case 13: {
                return "SCREEN_ORIENTATION_FULL_USER";
            }
            case 12: {
                return "SCREEN_ORIENTATION_USER_PORTRAIT";
            }
            case 11: {
                return "SCREEN_ORIENTATION_USER_LANDSCAPE";
            }
            case 10: {
                return "SCREEN_ORIENTATION_FULL_SENSOR";
            }
            case 9: {
                return "SCREEN_ORIENTATION_REVERSE_PORTRAIT";
            }
            case 8: {
                return "SCREEN_ORIENTATION_REVERSE_LANDSCAPE";
            }
            case 7: {
                return "SCREEN_ORIENTATION_SENSOR_PORTRAIT";
            }
            case 6: {
                return "SCREEN_ORIENTATION_SENSOR_LANDSCAPE";
            }
            case 5: {
                return "SCREEN_ORIENTATION_NOSENSOR";
            }
            case 4: {
                return "SCREEN_ORIENTATION_SENSOR";
            }
            case 3: {
                return "SCREEN_ORIENTATION_BEHIND";
            }
            case 2: {
                return "SCREEN_ORIENTATION_USER";
            }
            case 1: {
                return "SCREEN_ORIENTATION_PORTRAIT";
            }
            case 0: {
                return "SCREEN_ORIENTATION_LANDSCAPE";
            }
            case -1: {
                return "SCREEN_ORIENTATION_UNSPECIFIED";
            }
            case -2: 
        }
        return "SCREEN_ORIENTATION_UNSET";
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public void dump(Printer printer, String string2) {
        this.dump(printer, string2, 3);
    }

    public void dump(Printer printer, String string2, int n) {
        StringBuilder stringBuilder;
        super.dumpFront(printer, string2);
        if (this.permission != null) {
            stringBuilder = new StringBuilder();
            stringBuilder.append(string2);
            stringBuilder.append("permission=");
            stringBuilder.append(this.permission);
            printer.println(stringBuilder.toString());
        }
        if ((n & 1) != 0) {
            stringBuilder = new StringBuilder();
            stringBuilder.append(string2);
            stringBuilder.append("taskAffinity=");
            stringBuilder.append(this.taskAffinity);
            stringBuilder.append(" targetActivity=");
            stringBuilder.append(this.targetActivity);
            stringBuilder.append(" persistableMode=");
            stringBuilder.append(this.persistableModeToString());
            printer.println(stringBuilder.toString());
        }
        if (this.launchMode != 0 || this.flags != 0 || this.privateFlags != 0 || this.theme != 0) {
            stringBuilder = new StringBuilder();
            stringBuilder.append(string2);
            stringBuilder.append("launchMode=");
            stringBuilder.append(this.launchMode);
            stringBuilder.append(" flags=0x");
            stringBuilder.append(Integer.toHexString(this.flags));
            stringBuilder.append(" privateFlags=0x");
            stringBuilder.append(Integer.toHexString(this.privateFlags));
            stringBuilder.append(" theme=0x");
            stringBuilder.append(Integer.toHexString(this.theme));
            printer.println(stringBuilder.toString());
        }
        if (this.screenOrientation != -1 || this.configChanges != 0 || this.softInputMode != 0) {
            stringBuilder = new StringBuilder();
            stringBuilder.append(string2);
            stringBuilder.append("screenOrientation=");
            stringBuilder.append(this.screenOrientation);
            stringBuilder.append(" configChanges=0x");
            stringBuilder.append(Integer.toHexString(this.configChanges));
            stringBuilder.append(" softInputMode=0x");
            stringBuilder.append(Integer.toHexString(this.softInputMode));
            printer.println(stringBuilder.toString());
        }
        if (this.uiOptions != 0) {
            stringBuilder = new StringBuilder();
            stringBuilder.append(string2);
            stringBuilder.append(" uiOptions=0x");
            stringBuilder.append(Integer.toHexString(this.uiOptions));
            printer.println(stringBuilder.toString());
        }
        if ((n & 1) != 0) {
            stringBuilder = new StringBuilder();
            stringBuilder.append(string2);
            stringBuilder.append("lockTaskLaunchMode=");
            stringBuilder.append(ActivityInfo.lockTaskLaunchModeToString(this.lockTaskLaunchMode));
            printer.println(stringBuilder.toString());
        }
        if (this.windowLayout != null) {
            stringBuilder = new StringBuilder();
            stringBuilder.append(string2);
            stringBuilder.append("windowLayout=");
            stringBuilder.append(this.windowLayout.width);
            stringBuilder.append("|");
            stringBuilder.append(this.windowLayout.widthFraction);
            stringBuilder.append(", ");
            stringBuilder.append(this.windowLayout.height);
            stringBuilder.append("|");
            stringBuilder.append(this.windowLayout.heightFraction);
            stringBuilder.append(", ");
            stringBuilder.append(this.windowLayout.gravity);
            printer.println(stringBuilder.toString());
        }
        stringBuilder = new StringBuilder();
        stringBuilder.append(string2);
        stringBuilder.append("resizeMode=");
        stringBuilder.append(ActivityInfo.resizeModeToString(this.resizeMode));
        printer.println(stringBuilder.toString());
        if (this.requestedVrComponent != null) {
            stringBuilder = new StringBuilder();
            stringBuilder.append(string2);
            stringBuilder.append("requestedVrComponent=");
            stringBuilder.append(this.requestedVrComponent);
            printer.println(stringBuilder.toString());
        }
        if (this.maxAspectRatio != 0.0f) {
            stringBuilder = new StringBuilder();
            stringBuilder.append(string2);
            stringBuilder.append("maxAspectRatio=");
            stringBuilder.append(this.maxAspectRatio);
            printer.println(stringBuilder.toString());
        }
        if (this.minAspectRatio != 0.0f) {
            stringBuilder = new StringBuilder();
            stringBuilder.append(string2);
            stringBuilder.append("minAspectRatio=");
            stringBuilder.append(this.minAspectRatio);
            printer.println(stringBuilder.toString());
        }
        super.dumpBack(printer, string2, n);
    }

    public int getRealConfigChanged() {
        int n = this.applicationInfo.targetSdkVersion < 13 ? this.configChanges | 1024 | 2048 : this.configChanges;
        return n;
    }

    public final int getThemeResource() {
        int n = this.theme;
        if (n == 0) {
            n = this.applicationInfo.theme;
        }
        return n;
    }

    public boolean hasFixedAspectRatio() {
        boolean bl = this.maxAspectRatio != 0.0f || this.minAspectRatio != 0.0f;
        return bl;
    }

    public boolean isFixedOrientation() {
        boolean bl = this.isFixedOrientationLandscape() || this.isFixedOrientationPortrait() || this.screenOrientation == 14;
        return bl;
    }

    boolean isFixedOrientationLandscape() {
        return ActivityInfo.isFixedOrientationLandscape(this.screenOrientation);
    }

    boolean isFixedOrientationPortrait() {
        return ActivityInfo.isFixedOrientationPortrait(this.screenOrientation);
    }

    @UnsupportedAppUsage
    public boolean supportsPictureInPicture() {
        boolean bl = (this.flags & 4194304) != 0;
        return bl;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("ActivityInfo{");
        stringBuilder.append(Integer.toHexString(System.identityHashCode(this)));
        stringBuilder.append(" ");
        stringBuilder.append(this.name);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        super.writeToParcel(parcel, n);
        parcel.writeInt(this.theme);
        parcel.writeInt(this.launchMode);
        parcel.writeInt(this.documentLaunchMode);
        parcel.writeString(this.permission);
        parcel.writeString(this.taskAffinity);
        parcel.writeString(this.targetActivity);
        parcel.writeString(this.launchToken);
        parcel.writeInt(this.flags);
        parcel.writeInt(this.privateFlags);
        parcel.writeInt(this.screenOrientation);
        parcel.writeInt(this.configChanges);
        parcel.writeInt(this.softInputMode);
        parcel.writeInt(this.uiOptions);
        parcel.writeString(this.parentActivityName);
        parcel.writeInt(this.persistableMode);
        parcel.writeInt(this.maxRecents);
        parcel.writeInt(this.lockTaskLaunchMode);
        if (this.windowLayout != null) {
            parcel.writeInt(1);
            parcel.writeInt(this.windowLayout.width);
            parcel.writeFloat(this.windowLayout.widthFraction);
            parcel.writeInt(this.windowLayout.height);
            parcel.writeFloat(this.windowLayout.heightFraction);
            parcel.writeInt(this.windowLayout.gravity);
            parcel.writeInt(this.windowLayout.minWidth);
            parcel.writeInt(this.windowLayout.minHeight);
        } else {
            parcel.writeInt(0);
        }
        parcel.writeInt(this.resizeMode);
        parcel.writeString(this.requestedVrComponent);
        parcel.writeInt(this.rotationAnimation);
        parcel.writeInt(this.colorMode);
        parcel.writeFloat(this.maxAspectRatio);
        parcel.writeFloat(this.minAspectRatio);
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface ColorMode {
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface Config {
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface ScreenOrientation {
    }

    public static final class WindowLayout {
        public final int gravity;
        public final int height;
        public final float heightFraction;
        public final int minHeight;
        public final int minWidth;
        public final int width;
        public final float widthFraction;

        public WindowLayout(int n, float f, int n2, float f2, int n3, int n4, int n5) {
            this.width = n;
            this.widthFraction = f;
            this.height = n2;
            this.heightFraction = f2;
            this.gravity = n3;
            this.minWidth = n4;
            this.minHeight = n5;
        }

        WindowLayout(Parcel parcel) {
            this.width = parcel.readInt();
            this.widthFraction = parcel.readFloat();
            this.height = parcel.readInt();
            this.heightFraction = parcel.readFloat();
            this.gravity = parcel.readInt();
            this.minWidth = parcel.readInt();
            this.minHeight = parcel.readInt();
        }

        public boolean hasSpecifiedSize() {
            boolean bl = this.width >= 0 || this.height >= 0 || this.widthFraction >= 0.0f || this.heightFraction >= 0.0f;
            return bl;
        }
    }

}

