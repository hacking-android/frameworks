/*
 * Decompiled with CFR 0.145.
 */
package android.app;

import android.app.ActivityThread;
import android.graphics.Rect;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.proto.ProtoInputStream;
import android.util.proto.ProtoOutputStream;
import android.util.proto.WireTypeMismatchException;
import android.view.Surface;
import java.io.IOException;
import java.lang.annotation.Annotation;

public class WindowConfiguration
implements Parcelable,
Comparable<WindowConfiguration> {
    public static final int ACTIVITY_TYPE_ASSISTANT = 4;
    public static final int ACTIVITY_TYPE_HOME = 2;
    public static final int ACTIVITY_TYPE_RECENTS = 3;
    public static final int ACTIVITY_TYPE_STANDARD = 1;
    public static final int ACTIVITY_TYPE_UNDEFINED = 0;
    private static final int ALWAYS_ON_TOP_OFF = 2;
    private static final int ALWAYS_ON_TOP_ON = 1;
    private static final int ALWAYS_ON_TOP_UNDEFINED = 0;
    public static final Parcelable.Creator<WindowConfiguration> CREATOR = new Parcelable.Creator<WindowConfiguration>(){

        @Override
        public WindowConfiguration createFromParcel(Parcel parcel) {
            return new WindowConfiguration(parcel);
        }

        public WindowConfiguration[] newArray(int n) {
            return new WindowConfiguration[n];
        }
    };
    public static final int PINNED_WINDOWING_MODE_ELEVATION_IN_DIP = 5;
    public static final int ROTATION_UNDEFINED = -1;
    public static final int WINDOWING_MODE_FREEFORM = 5;
    public static final int WINDOWING_MODE_FULLSCREEN = 1;
    public static final int WINDOWING_MODE_FULLSCREEN_OR_SPLIT_SCREEN_SECONDARY = 4;
    public static final int WINDOWING_MODE_PINNED = 2;
    public static final int WINDOWING_MODE_SPLIT_SCREEN_PRIMARY = 3;
    public static final int WINDOWING_MODE_SPLIT_SCREEN_SECONDARY = 4;
    public static final int WINDOWING_MODE_UNDEFINED = 0;
    public static final int WINDOW_CONFIG_ACTIVITY_TYPE = 8;
    public static final int WINDOW_CONFIG_ALWAYS_ON_TOP = 16;
    public static final int WINDOW_CONFIG_APP_BOUNDS = 2;
    public static final int WINDOW_CONFIG_BOUNDS = 1;
    public static final int WINDOW_CONFIG_DISPLAY_WINDOWING_MODE = 64;
    public static final int WINDOW_CONFIG_ROTATION = 32;
    public static final int WINDOW_CONFIG_WINDOWING_MODE = 4;
    @ActivityType
    private int mActivityType;
    @AlwaysOnTop
    private int mAlwaysOnTop;
    private Rect mAppBounds;
    private Rect mBounds = new Rect();
    @WindowingMode
    private int mDisplayWindowingMode;
    private int mRotation = -1;
    @WindowingMode
    private int mWindowingMode;

    public WindowConfiguration() {
        this.unset();
    }

    public WindowConfiguration(WindowConfiguration windowConfiguration) {
        this.setTo(windowConfiguration);
    }

    private WindowConfiguration(Parcel parcel) {
        this.readFromParcel(parcel);
    }

    public static String activityTypeToString(@ActivityType int n) {
        if (n != 0) {
            if (n != 1) {
                if (n != 2) {
                    if (n != 3) {
                        if (n != 4) {
                            return String.valueOf(n);
                        }
                        return "assistant";
                    }
                    return "recents";
                }
                return "home";
            }
            return "standard";
        }
        return "undefined";
    }

    public static String alwaysOnTopToString(@AlwaysOnTop int n) {
        if (n != 0) {
            if (n != 1) {
                if (n != 2) {
                    return String.valueOf(n);
                }
                return "off";
            }
            return "on";
        }
        return "undefined";
    }

    public static boolean isFloating(int n) {
        boolean bl = n == 5 || n == 2;
        return bl;
    }

    public static boolean isSplitScreenWindowingMode(int n) {
        boolean bl = n == 3 || n == 4;
        return bl;
    }

    private void readFromParcel(Parcel parcel) {
        this.mBounds = (Rect)parcel.readParcelable(Rect.class.getClassLoader());
        this.mAppBounds = (Rect)parcel.readParcelable(Rect.class.getClassLoader());
        this.mWindowingMode = parcel.readInt();
        this.mActivityType = parcel.readInt();
        this.mAlwaysOnTop = parcel.readInt();
        this.mRotation = parcel.readInt();
        this.mDisplayWindowingMode = parcel.readInt();
    }

    private void setAlwaysOnTop(@AlwaysOnTop int n) {
        this.mAlwaysOnTop = n;
    }

    public static boolean supportSplitScreenWindowingMode(int n) {
        boolean bl = n != 4;
        return bl;
    }

    public static String windowingModeToString(@WindowingMode int n) {
        if (n != 0) {
            if (n != 1) {
                if (n != 2) {
                    if (n != 3) {
                        if (n != 4) {
                            if (n != 5) {
                                return String.valueOf(n);
                            }
                            return "freeform";
                        }
                        return "split-screen-secondary";
                    }
                    return "split-screen-primary";
                }
                return "pinned";
            }
            return "fullscreen";
        }
        return "undefined";
    }

    public boolean canReceiveKeys() {
        boolean bl = this.mWindowingMode != 2;
        return bl;
    }

    public boolean canResizeTask() {
        boolean bl = this.mWindowingMode == 5;
        return bl;
    }

    @Override
    public int compareTo(WindowConfiguration windowConfiguration) {
        int n;
        if (this.mAppBounds == null && windowConfiguration.mAppBounds != null) {
            return 1;
        }
        if (this.mAppBounds != null && windowConfiguration.mAppBounds == null) {
            return -1;
        }
        Rect rect = this.mAppBounds;
        if (rect != null && windowConfiguration.mAppBounds != null) {
            n = rect.left - windowConfiguration.mAppBounds.left;
            if (n != 0) {
                return n;
            }
            n = this.mAppBounds.top - windowConfiguration.mAppBounds.top;
            if (n != 0) {
                return n;
            }
            n = this.mAppBounds.right - windowConfiguration.mAppBounds.right;
            if (n != 0) {
                return n;
            }
            n = this.mAppBounds.bottom - windowConfiguration.mAppBounds.bottom;
            if (n != 0) {
                return n;
            }
        }
        if ((n = this.mBounds.left - windowConfiguration.mBounds.left) != 0) {
            return n;
        }
        n = this.mBounds.top - windowConfiguration.mBounds.top;
        if (n != 0) {
            return n;
        }
        n = this.mBounds.right - windowConfiguration.mBounds.right;
        if (n != 0) {
            return n;
        }
        n = this.mBounds.bottom - windowConfiguration.mBounds.bottom;
        if (n != 0) {
            return n;
        }
        n = this.mWindowingMode - windowConfiguration.mWindowingMode;
        if (n != 0) {
            return n;
        }
        n = this.mActivityType - windowConfiguration.mActivityType;
        if (n != 0) {
            return n;
        }
        n = this.mAlwaysOnTop - windowConfiguration.mAlwaysOnTop;
        if (n != 0) {
            return n;
        }
        n = this.mRotation - windowConfiguration.mRotation;
        if (n != 0) {
            return n;
        }
        n = this.mDisplayWindowingMode - windowConfiguration.mDisplayWindowingMode;
        if (n != 0) {
            return n;
        }
        return n;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @WindowConfig
    public long diff(WindowConfiguration windowConfiguration, boolean bl) {
        long l;
        block36 : {
            long l2;
            block35 : {
                block34 : {
                    block33 : {
                        block32 : {
                            block31 : {
                                block30 : {
                                    block29 : {
                                        block28 : {
                                            block27 : {
                                                block25 : {
                                                    block26 : {
                                                        block24 : {
                                                            l = 0L;
                                                            if (!this.mBounds.equals(windowConfiguration.mBounds)) {
                                                                l = 0L | 1L;
                                                            }
                                                            if (bl) break block24;
                                                            l2 = l;
                                                            if (windowConfiguration.mAppBounds == null) break block25;
                                                        }
                                                        Rect rect = this.mAppBounds;
                                                        Rect rect2 = windowConfiguration.mAppBounds;
                                                        l2 = l;
                                                        if (rect == rect2) break block25;
                                                        if (rect == null) break block26;
                                                        l2 = l;
                                                        if (rect.equals(rect2)) break block25;
                                                    }
                                                    l2 = l | 2L;
                                                }
                                                if (bl) break block27;
                                                l = l2;
                                                if (windowConfiguration.mWindowingMode == 0) break block28;
                                            }
                                            l = l2;
                                            if (this.mWindowingMode != windowConfiguration.mWindowingMode) {
                                                l = l2 | 4L;
                                            }
                                        }
                                        if (bl) break block29;
                                        l2 = l;
                                        if (windowConfiguration.mActivityType == 0) break block30;
                                    }
                                    l2 = l;
                                    if (this.mActivityType != windowConfiguration.mActivityType) {
                                        l2 = l | 8L;
                                    }
                                }
                                if (bl) break block31;
                                l = l2;
                                if (windowConfiguration.mAlwaysOnTop == 0) break block32;
                            }
                            l = l2;
                            if (this.mAlwaysOnTop != windowConfiguration.mAlwaysOnTop) {
                                l = l2 | 16L;
                            }
                        }
                        if (bl) break block33;
                        l2 = l;
                        if (windowConfiguration.mRotation == -1) break block34;
                    }
                    l2 = l;
                    if (this.mRotation != windowConfiguration.mRotation) {
                        l2 = l | 32L;
                    }
                }
                if (bl) break block35;
                l = l2;
                if (windowConfiguration.mDisplayWindowingMode == 0) break block36;
            }
            l = l2;
            if (this.mDisplayWindowingMode != windowConfiguration.mDisplayWindowingMode) {
                l = l2 | 64L;
            }
        }
        return l;
    }

    public boolean equals(Object object) {
        boolean bl = false;
        if (object == null) {
            return false;
        }
        if (object == this) {
            return true;
        }
        if (!(object instanceof WindowConfiguration)) {
            return false;
        }
        if (this.compareTo((WindowConfiguration)object) == 0) {
            bl = true;
        }
        return bl;
    }

    @ActivityType
    public int getActivityType() {
        return this.mActivityType;
    }

    public Rect getAppBounds() {
        return this.mAppBounds;
    }

    public Rect getBounds() {
        return this.mBounds;
    }

    public int getRotation() {
        return this.mRotation;
    }

    @WindowingMode
    public int getWindowingMode() {
        return this.mWindowingMode;
    }

    public boolean hasMovementAnimations() {
        boolean bl = this.mWindowingMode != 2;
        return bl;
    }

    public boolean hasWindowDecorCaption() {
        int n = this.mActivityType;
        boolean bl = true;
        if (n != 1 || this.mWindowingMode != 5 && this.mDisplayWindowingMode != 5) {
            bl = false;
        }
        return bl;
    }

    public boolean hasWindowShadow() {
        return this.tasksAreFloating();
    }

    public int hashCode() {
        int n = 0;
        Rect rect = this.mAppBounds;
        if (rect != null) {
            n = 0 * 31 + rect.hashCode();
        }
        return (((((n * 31 + this.mBounds.hashCode()) * 31 + this.mWindowingMode) * 31 + this.mActivityType) * 31 + this.mAlwaysOnTop) * 31 + this.mRotation) * 31 + this.mDisplayWindowingMode;
    }

    public boolean isAlwaysOnTop() {
        boolean bl;
        int n = this.mWindowingMode;
        boolean bl2 = bl = true;
        if (n != 2) {
            bl2 = n == 5 && this.mAlwaysOnTop == 1 ? bl : false;
        }
        return bl2;
    }

    public boolean keepVisibleDeadAppWindowOnScreen() {
        boolean bl = this.mWindowingMode != 2;
        return bl;
    }

    public boolean persistTaskBounds() {
        boolean bl = this.mWindowingMode == 5;
        return bl;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void readFromProto(ProtoInputStream protoInputStream, long l) throws IOException, WireTypeMismatchException {
        l = protoInputStream.start(l);
        try {
            while (protoInputStream.nextField() != -1) {
                Rect rect;
                int n = protoInputStream.getFieldNumber();
                if (n != 1) {
                    if (n != 2) {
                        if (n != 3) {
                            if (n != 4) continue;
                            this.mBounds = rect = new Rect();
                            this.mBounds.readFromProto(protoInputStream, 1146756268036L);
                            continue;
                        }
                        this.mActivityType = protoInputStream.readInt(1120986464259L);
                        continue;
                    }
                    this.mWindowingMode = protoInputStream.readInt(1120986464258L);
                    continue;
                }
                this.mAppBounds = rect = new Rect();
                this.mAppBounds.readFromProto(protoInputStream, 1146756268033L);
            }
            return;
        }
        finally {
            protoInputStream.end(l);
        }
    }

    public void setActivityType(@ActivityType int n) {
        if (this.mActivityType == n) {
            return;
        }
        if (ActivityThread.isSystem() && this.mActivityType != 0 && n != 0) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Can't change activity type once set: ");
            stringBuilder.append(this);
            stringBuilder.append(" activityType=");
            stringBuilder.append(WindowConfiguration.activityTypeToString(n));
            throw new IllegalStateException(stringBuilder.toString());
        }
        this.mActivityType = n;
    }

    public void setAlwaysOnTop(boolean bl) {
        int n = bl ? 1 : 2;
        this.mAlwaysOnTop = n;
    }

    public void setAppBounds(int n, int n2, int n3, int n4) {
        if (this.mAppBounds == null) {
            this.mAppBounds = new Rect();
        }
        this.mAppBounds.set(n, n2, n3, n4);
    }

    public void setAppBounds(Rect rect) {
        if (rect == null) {
            this.mAppBounds = null;
            return;
        }
        this.setAppBounds(rect.left, rect.top, rect.right, rect.bottom);
    }

    public void setBounds(Rect rect) {
        if (rect == null) {
            this.mBounds.setEmpty();
            return;
        }
        this.mBounds.set(rect);
    }

    public void setDisplayWindowingMode(@WindowingMode int n) {
        this.mDisplayWindowingMode = n;
    }

    public void setRotation(int n) {
        this.mRotation = n;
    }

    public void setTo(WindowConfiguration windowConfiguration) {
        this.setBounds(windowConfiguration.mBounds);
        this.setAppBounds(windowConfiguration.mAppBounds);
        this.setWindowingMode(windowConfiguration.mWindowingMode);
        this.setActivityType(windowConfiguration.mActivityType);
        this.setAlwaysOnTop(windowConfiguration.mAlwaysOnTop);
        this.setRotation(windowConfiguration.mRotation);
        this.setDisplayWindowingMode(windowConfiguration.mDisplayWindowingMode);
    }

    public void setToDefaults() {
        this.setAppBounds(null);
        this.setBounds(null);
        this.setWindowingMode(0);
        this.setActivityType(0);
        this.setAlwaysOnTop(0);
        this.setRotation(-1);
        this.setDisplayWindowingMode(0);
    }

    public void setWindowingMode(@WindowingMode int n) {
        this.mWindowingMode = n;
    }

    public boolean supportSplitScreenWindowingMode() {
        return WindowConfiguration.supportSplitScreenWindowingMode(this.mActivityType);
    }

    public boolean tasksAreFloating() {
        return WindowConfiguration.isFloating(this.mWindowingMode);
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{ mBounds=");
        stringBuilder.append(this.mBounds);
        stringBuilder.append(" mAppBounds=");
        stringBuilder.append(this.mAppBounds);
        stringBuilder.append(" mWindowingMode=");
        stringBuilder.append(WindowConfiguration.windowingModeToString(this.mWindowingMode));
        stringBuilder.append(" mDisplayWindowingMode=");
        stringBuilder.append(WindowConfiguration.windowingModeToString(this.mDisplayWindowingMode));
        stringBuilder.append(" mActivityType=");
        stringBuilder.append(WindowConfiguration.activityTypeToString(this.mActivityType));
        stringBuilder.append(" mAlwaysOnTop=");
        stringBuilder.append(WindowConfiguration.alwaysOnTopToString(this.mAlwaysOnTop));
        stringBuilder.append(" mRotation=");
        int n = this.mRotation;
        String string2 = n == -1 ? "undefined" : Surface.rotationToString(n);
        stringBuilder.append(string2);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    public void unset() {
        this.setToDefaults();
    }

    @WindowConfig
    public int updateFrom(WindowConfiguration windowConfiguration) {
        int n;
        int n2 = n = 0;
        if (!windowConfiguration.mBounds.isEmpty()) {
            n2 = n;
            if (!windowConfiguration.mBounds.equals(this.mBounds)) {
                n2 = false | true;
                this.setBounds(windowConfiguration.mBounds);
            }
        }
        Rect rect = windowConfiguration.mAppBounds;
        n = n2;
        if (rect != null) {
            n = n2;
            if (!rect.equals(this.mAppBounds)) {
                n = n2 | 2;
                this.setAppBounds(windowConfiguration.mAppBounds);
            }
        }
        int n3 = windowConfiguration.mWindowingMode;
        n2 = n;
        if (n3 != 0) {
            n2 = n;
            if (this.mWindowingMode != n3) {
                n2 = n | 4;
                this.setWindowingMode(n3);
            }
        }
        n3 = windowConfiguration.mActivityType;
        n = n2;
        if (n3 != 0) {
            n = n2;
            if (this.mActivityType != n3) {
                n = n2 | 8;
                this.setActivityType(n3);
            }
        }
        n2 = windowConfiguration.mAlwaysOnTop;
        n3 = n;
        if (n2 != 0) {
            n3 = n;
            if (this.mAlwaysOnTop != n2) {
                n3 = n | 16;
                this.setAlwaysOnTop(n2);
            }
        }
        n = windowConfiguration.mRotation;
        n2 = n3;
        if (n != -1) {
            n2 = n3;
            if (n != this.mRotation) {
                n2 = n3 | 32;
                this.setRotation(n);
            }
        }
        n3 = windowConfiguration.mDisplayWindowingMode;
        n = n2;
        if (n3 != 0) {
            n = n2;
            if (this.mDisplayWindowingMode != n3) {
                n = n2 | 64;
                this.setDisplayWindowingMode(n3);
            }
        }
        return n;
    }

    public boolean useWindowFrameForBackdrop() {
        int n = this.mWindowingMode;
        boolean bl = n == 5 || n == 2;
        return bl;
    }

    public boolean windowsAreScaleable() {
        boolean bl = this.mWindowingMode == 2;
        return bl;
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeParcelable(this.mBounds, n);
        parcel.writeParcelable(this.mAppBounds, n);
        parcel.writeInt(this.mWindowingMode);
        parcel.writeInt(this.mActivityType);
        parcel.writeInt(this.mAlwaysOnTop);
        parcel.writeInt(this.mRotation);
        parcel.writeInt(this.mDisplayWindowingMode);
    }

    public void writeToProto(ProtoOutputStream protoOutputStream, long l) {
        l = protoOutputStream.start(l);
        Rect rect = this.mAppBounds;
        if (rect != null) {
            rect.writeToProto(protoOutputStream, 1146756268033L);
        }
        protoOutputStream.write(1120986464258L, this.mWindowingMode);
        protoOutputStream.write(1120986464259L, this.mActivityType);
        rect = this.mBounds;
        if (rect != null) {
            rect.writeToProto(protoOutputStream, 1146756268036L);
        }
        protoOutputStream.end(l);
    }

    public static @interface ActivityType {
    }

    private static @interface AlwaysOnTop {
    }

    public static @interface WindowConfig {
    }

    public static @interface WindowingMode {
    }

}

