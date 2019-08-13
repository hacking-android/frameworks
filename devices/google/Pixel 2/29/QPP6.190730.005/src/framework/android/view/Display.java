/*
 * Decompiled with CFR 0.145.
 */
package android.view;

import android.annotation.UnsupportedAppUsage;
import android.content.res.CompatibilityInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.ColorSpace;
import android.graphics.Point;
import android.graphics.Rect;
import android.hardware.display.DisplayManagerGlobal;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.SystemClock;
import android.util.DisplayMetrics;
import android.view.DisplayAddress;
import android.view.DisplayAdjustments;
import android.view.DisplayCutout;
import android.view.DisplayInfo;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Arrays;

public final class Display {
    private static final int CACHED_APP_SIZE_DURATION_MILLIS = 20;
    public static final int COLOR_MODE_ADOBE_RGB = 8;
    public static final int COLOR_MODE_BT601_525 = 3;
    public static final int COLOR_MODE_BT601_525_UNADJUSTED = 4;
    public static final int COLOR_MODE_BT601_625 = 1;
    public static final int COLOR_MODE_BT601_625_UNADJUSTED = 2;
    public static final int COLOR_MODE_BT709 = 5;
    public static final int COLOR_MODE_DCI_P3 = 6;
    public static final int COLOR_MODE_DEFAULT = 0;
    public static final int COLOR_MODE_DISPLAY_P3 = 9;
    public static final int COLOR_MODE_INVALID = -1;
    public static final int COLOR_MODE_SRGB = 7;
    private static final boolean DEBUG = false;
    public static final int DEFAULT_DISPLAY = 0;
    public static final int FLAG_CAN_SHOW_WITH_INSECURE_KEYGUARD = 32;
    public static final int FLAG_PRESENTATION = 8;
    public static final int FLAG_PRIVATE = 4;
    public static final int FLAG_ROUND = 16;
    public static final int FLAG_SCALING_DISABLED = 1073741824;
    public static final int FLAG_SECURE = 2;
    public static final int FLAG_SHOULD_SHOW_SYSTEM_DECORATIONS = 64;
    public static final int FLAG_SUPPORTS_PROTECTED_BUFFERS = 1;
    public static final int INVALID_DISPLAY = -1;
    public static final int REMOVE_MODE_DESTROY_CONTENT = 1;
    public static final int REMOVE_MODE_MOVE_CONTENT_TO_PRIMARY = 0;
    public static final int STATE_DOZE = 3;
    public static final int STATE_DOZE_SUSPEND = 4;
    public static final int STATE_OFF = 1;
    public static final int STATE_ON = 2;
    public static final int STATE_ON_SUSPEND = 6;
    public static final int STATE_UNKNOWN = 0;
    public static final int STATE_VR = 5;
    private static final String TAG = "Display";
    public static final int TYPE_BUILT_IN = 1;
    @UnsupportedAppUsage
    public static final int TYPE_HDMI = 2;
    public static final int TYPE_OVERLAY = 4;
    @UnsupportedAppUsage
    public static final int TYPE_UNKNOWN = 0;
    @UnsupportedAppUsage
    public static final int TYPE_VIRTUAL = 5;
    @UnsupportedAppUsage
    public static final int TYPE_WIFI = 3;
    private final DisplayAddress mAddress;
    private int mCachedAppHeightCompat;
    private int mCachedAppWidthCompat;
    private DisplayAdjustments mDisplayAdjustments;
    private final int mDisplayId;
    @UnsupportedAppUsage
    private DisplayInfo mDisplayInfo;
    private final int mFlags;
    private final DisplayManagerGlobal mGlobal;
    private boolean mIsValid;
    private long mLastCachedAppSizeUpdate;
    private final int mLayerStack;
    private final String mOwnerPackageName;
    private final int mOwnerUid;
    private final Resources mResources;
    private final DisplayMetrics mTempMetrics = new DisplayMetrics();
    private final int mType;

    public Display(DisplayManagerGlobal displayManagerGlobal, int n, DisplayInfo displayInfo, Resources resources) {
        this(displayManagerGlobal, n, displayInfo, null, resources);
    }

    public Display(DisplayManagerGlobal displayManagerGlobal, int n, DisplayInfo displayInfo, DisplayAdjustments displayAdjustments) {
        this(displayManagerGlobal, n, displayInfo, displayAdjustments, null);
    }

    private Display(DisplayManagerGlobal object, int n, DisplayInfo displayInfo, DisplayAdjustments displayAdjustments, Resources resources) {
        this.mGlobal = object;
        this.mDisplayId = n;
        this.mDisplayInfo = displayInfo;
        this.mResources = resources;
        object = this.mResources;
        object = object != null ? new DisplayAdjustments(((Resources)object).getConfiguration()) : (displayAdjustments != null ? new DisplayAdjustments(displayAdjustments) : null);
        this.mDisplayAdjustments = object;
        this.mIsValid = true;
        this.mLayerStack = displayInfo.layerStack;
        this.mFlags = displayInfo.flags;
        this.mType = displayInfo.type;
        this.mAddress = displayInfo.address;
        this.mOwnerUid = displayInfo.ownerUid;
        this.mOwnerPackageName = displayInfo.ownerPackageName;
    }

    public static boolean hasAccess(int n, int n2, int n3, int n4) {
        boolean bl = (n2 & 4) == 0 || n == n3 || n == 1000 || n == 0 || DisplayManagerGlobal.getInstance().isUidPresentOnDisplay(n, n4);
        return bl;
    }

    public static boolean isDozeState(int n) {
        boolean bl = n == 3 || n == 4;
        return bl;
    }

    public static boolean isSuspendedState(int n) {
        boolean bl;
        boolean bl2 = bl = true;
        if (n != 1) {
            bl2 = bl;
            if (n != 4) {
                bl2 = n == 6 ? bl : false;
            }
        }
        return bl2;
    }

    public static String stateToString(int n) {
        switch (n) {
            default: {
                return Integer.toString(n);
            }
            case 6: {
                return "ON_SUSPEND";
            }
            case 5: {
                return "VR";
            }
            case 4: {
                return "DOZE_SUSPEND";
            }
            case 3: {
                return "DOZE";
            }
            case 2: {
                return "ON";
            }
            case 1: {
                return "OFF";
            }
            case 0: 
        }
        return "UNKNOWN";
    }

    public static String typeToString(int n) {
        if (n != 0) {
            if (n != 1) {
                if (n != 2) {
                    if (n != 3) {
                        if (n != 4) {
                            if (n != 5) {
                                return Integer.toString(n);
                            }
                            return "VIRTUAL";
                        }
                        return "OVERLAY";
                    }
                    return "WIFI";
                }
                return "HDMI";
            }
            return "BUILT_IN";
        }
        return "UNKNOWN";
    }

    private void updateCachedAppSizeIfNeededLocked() {
        long l = SystemClock.uptimeMillis();
        if (l > this.mLastCachedAppSizeUpdate + 20L) {
            this.updateDisplayInfoLocked();
            this.mDisplayInfo.getAppMetrics(this.mTempMetrics, this.getDisplayAdjustments());
            this.mCachedAppWidthCompat = this.mTempMetrics.widthPixels;
            this.mCachedAppHeightCompat = this.mTempMetrics.heightPixels;
            this.mLastCachedAppSizeUpdate = l;
        }
    }

    private void updateDisplayInfoLocked() {
        DisplayInfo displayInfo = this.mGlobal.getDisplayInfo(this.mDisplayId);
        if (displayInfo == null) {
            if (this.mIsValid) {
                this.mIsValid = false;
            }
        } else {
            this.mDisplayInfo = displayInfo;
            if (!this.mIsValid) {
                this.mIsValid = true;
            }
        }
    }

    @UnsupportedAppUsage
    public DisplayAddress getAddress() {
        return this.mAddress;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public long getAppVsyncOffsetNanos() {
        synchronized (this) {
            this.updateDisplayInfoLocked();
            return this.mDisplayInfo.appVsyncOffsetNanos;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public int getColorMode() {
        synchronized (this) {
            this.updateDisplayInfoLocked();
            return this.mDisplayInfo.colorMode;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void getCurrentSizeRange(Point point, Point point2) {
        synchronized (this) {
            this.updateDisplayInfoLocked();
            point.x = this.mDisplayInfo.smallestNominalAppWidth;
            point.y = this.mDisplayInfo.smallestNominalAppHeight;
            point2.x = this.mDisplayInfo.largestNominalAppWidth;
            point2.y = this.mDisplayInfo.largestNominalAppHeight;
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public DisplayCutout getCutout() {
        synchronized (this) {
            this.updateDisplayInfoLocked();
            return this.mDisplayInfo.displayCutout;
        }
    }

    @UnsupportedAppUsage
    public DisplayAdjustments getDisplayAdjustments() {
        Object object = this.mResources;
        if (object != null && !this.mDisplayAdjustments.equals(object = ((Resources)object).getDisplayAdjustments())) {
            this.mDisplayAdjustments = new DisplayAdjustments((DisplayAdjustments)object);
        }
        return this.mDisplayAdjustments;
    }

    public int getDisplayId() {
        return this.mDisplayId;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @UnsupportedAppUsage(maxTargetSdk=28)
    public boolean getDisplayInfo(DisplayInfo displayInfo) {
        synchronized (this) {
            this.updateDisplayInfoLocked();
            displayInfo.copyFrom(this.mDisplayInfo);
            return this.mIsValid;
        }
    }

    public int getFlags() {
        return this.mFlags;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public HdrCapabilities getHdrCapabilities() {
        synchronized (this) {
            this.updateDisplayInfoLocked();
            return this.mDisplayInfo.hdrCapabilities;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Deprecated
    public int getHeight() {
        synchronized (this) {
            this.updateCachedAppSizeIfNeededLocked();
            return this.mCachedAppHeightCompat;
        }
    }

    public int getLayerStack() {
        return this.mLayerStack;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @UnsupportedAppUsage
    public int getMaximumSizeDimension() {
        synchronized (this) {
            this.updateDisplayInfoLocked();
            return Math.max(this.mDisplayInfo.logicalWidth, this.mDisplayInfo.logicalHeight);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void getMetrics(DisplayMetrics displayMetrics) {
        synchronized (this) {
            this.updateDisplayInfoLocked();
            this.mDisplayInfo.getAppMetrics(displayMetrics, this.getDisplayAdjustments());
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public Mode getMode() {
        synchronized (this) {
            this.updateDisplayInfoLocked();
            return this.mDisplayInfo.getMode();
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public String getName() {
        synchronized (this) {
            this.updateDisplayInfoLocked();
            return this.mDisplayInfo.name;
        }
    }

    @Deprecated
    public int getOrientation() {
        return this.getRotation();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void getOverscanInsets(Rect rect) {
        synchronized (this) {
            this.updateDisplayInfoLocked();
            rect.set(this.mDisplayInfo.overscanLeft, this.mDisplayInfo.overscanTop, this.mDisplayInfo.overscanRight, this.mDisplayInfo.overscanBottom);
            return;
        }
    }

    @UnsupportedAppUsage
    public String getOwnerPackageName() {
        return this.mOwnerPackageName;
    }

    public int getOwnerUid() {
        return this.mOwnerUid;
    }

    @Deprecated
    public int getPixelFormat() {
        return 1;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public ColorSpace getPreferredWideGamutColorSpace() {
        synchronized (this) {
            this.updateDisplayInfoLocked();
            if (!this.mDisplayInfo.isWideColorGamut()) return null;
            return this.mGlobal.getPreferredWideGamutColorSpace();
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public long getPresentationDeadlineNanos() {
        synchronized (this) {
            this.updateDisplayInfoLocked();
            return this.mDisplayInfo.presentationDeadlineNanos;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void getRealMetrics(DisplayMetrics displayMetrics) {
        synchronized (this) {
            this.updateDisplayInfoLocked();
            this.mDisplayInfo.getLogicalMetrics(displayMetrics, CompatibilityInfo.DEFAULT_COMPATIBILITY_INFO, null);
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void getRealSize(Point point) {
        synchronized (this) {
            this.updateDisplayInfoLocked();
            point.x = this.mDisplayInfo.logicalWidth;
            point.y = this.mDisplayInfo.logicalHeight;
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void getRectSize(Rect rect) {
        synchronized (this) {
            this.updateDisplayInfoLocked();
            this.mDisplayInfo.getAppMetrics(this.mTempMetrics, this.getDisplayAdjustments());
            rect.set(0, 0, this.mTempMetrics.widthPixels, this.mTempMetrics.heightPixels);
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public float getRefreshRate() {
        synchronized (this) {
            this.updateDisplayInfoLocked();
            return this.mDisplayInfo.getMode().getRefreshRate();
        }
    }

    public int getRemoveMode() {
        return this.mDisplayInfo.removeMode;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public int getRotation() {
        synchronized (this) {
            this.updateDisplayInfoLocked();
            return this.mDisplayInfo.rotation;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void getSize(Point point) {
        synchronized (this) {
            this.updateDisplayInfoLocked();
            this.mDisplayInfo.getAppMetrics(this.mTempMetrics, this.getDisplayAdjustments());
            point.x = this.mTempMetrics.widthPixels;
            point.y = this.mTempMetrics.heightPixels;
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public int getState() {
        synchronized (this) {
            this.updateDisplayInfoLocked();
            if (!this.mIsValid) return 0;
            return this.mDisplayInfo.state;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public int[] getSupportedColorModes() {
        synchronized (this) {
            this.updateDisplayInfoLocked();
            int[] arrn = this.mDisplayInfo.supportedColorModes;
            return Arrays.copyOf(arrn, arrn.length);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public Mode[] getSupportedModes() {
        synchronized (this) {
            this.updateDisplayInfoLocked();
            Mode[] arrmode = this.mDisplayInfo.supportedModes;
            return Arrays.copyOf(arrmode, arrmode.length);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Deprecated
    public float[] getSupportedRefreshRates() {
        synchronized (this) {
            this.updateDisplayInfoLocked();
            return this.mDisplayInfo.getDefaultRefreshRates();
        }
    }

    @UnsupportedAppUsage
    public int getType() {
        return this.mType;
    }

    public String getUniqueId() {
        return this.mDisplayInfo.uniqueId;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Deprecated
    public int getWidth() {
        synchronized (this) {
            this.updateCachedAppSizeIfNeededLocked();
            return this.mCachedAppWidthCompat;
        }
    }

    public boolean hasAccess(int n) {
        return Display.hasAccess(n, this.mFlags, this.mOwnerUid, this.mDisplayId);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public boolean isHdr() {
        synchronized (this) {
            this.updateDisplayInfoLocked();
            return this.mDisplayInfo.isHdr();
        }
    }

    public boolean isPublicPresentation() {
        boolean bl = (this.mFlags & 12) == 8;
        return bl;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public boolean isValid() {
        synchronized (this) {
            this.updateDisplayInfoLocked();
            return this.mIsValid;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public boolean isWideColorGamut() {
        synchronized (this) {
            this.updateDisplayInfoLocked();
            return this.mDisplayInfo.isWideColorGamut();
        }
    }

    public void requestColorMode(int n) {
        this.mGlobal.requestColorMode(this.mDisplayId, n);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public String toString() {
        synchronized (this) {
            this.updateDisplayInfoLocked();
            this.mDisplayInfo.getAppMetrics(this.mTempMetrics, this.getDisplayAdjustments());
            CharSequence charSequence = new StringBuilder();
            charSequence.append("Display id ");
            charSequence.append(this.mDisplayId);
            charSequence.append(": ");
            charSequence.append(this.mDisplayInfo);
            charSequence.append(", ");
            charSequence.append(this.mTempMetrics);
            charSequence.append(", isValid=");
            charSequence.append(this.mIsValid);
            return charSequence.toString();
        }
    }

    public static final class HdrCapabilities
    implements Parcelable {
        public static final Parcelable.Creator<HdrCapabilities> CREATOR = new Parcelable.Creator<HdrCapabilities>(){

            @Override
            public HdrCapabilities createFromParcel(Parcel parcel) {
                return new HdrCapabilities(parcel);
            }

            public HdrCapabilities[] newArray(int n) {
                return new HdrCapabilities[n];
            }
        };
        public static final int HDR_TYPE_DOLBY_VISION = 1;
        public static final int HDR_TYPE_HDR10 = 2;
        public static final int HDR_TYPE_HDR10_PLUS = 4;
        public static final int HDR_TYPE_HLG = 3;
        public static final float INVALID_LUMINANCE = -1.0f;
        private float mMaxAverageLuminance = -1.0f;
        private float mMaxLuminance = -1.0f;
        private float mMinLuminance = -1.0f;
        private int[] mSupportedHdrTypes = new int[0];

        public HdrCapabilities() {
        }

        private HdrCapabilities(Parcel parcel) {
            this.readFromParcel(parcel);
        }

        @UnsupportedAppUsage
        public HdrCapabilities(int[] arrn, float f, float f2, float f3) {
            this.mSupportedHdrTypes = arrn;
            this.mMaxLuminance = f;
            this.mMaxAverageLuminance = f2;
            this.mMinLuminance = f3;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public boolean equals(Object object) {
            boolean bl = true;
            if (this == object) {
                return true;
            }
            if (!(object instanceof HdrCapabilities)) {
                return false;
            }
            object = (HdrCapabilities)object;
            if (!Arrays.equals(this.mSupportedHdrTypes, ((HdrCapabilities)object).mSupportedHdrTypes) || this.mMaxLuminance != ((HdrCapabilities)object).mMaxLuminance || this.mMaxAverageLuminance != ((HdrCapabilities)object).mMaxAverageLuminance || this.mMinLuminance != ((HdrCapabilities)object).mMinLuminance) {
                bl = false;
            }
            return bl;
        }

        public float getDesiredMaxAverageLuminance() {
            return this.mMaxAverageLuminance;
        }

        public float getDesiredMaxLuminance() {
            return this.mMaxLuminance;
        }

        public float getDesiredMinLuminance() {
            return this.mMinLuminance;
        }

        public int[] getSupportedHdrTypes() {
            return this.mSupportedHdrTypes;
        }

        public int hashCode() {
            return (((23 * 17 + Arrays.hashCode(this.mSupportedHdrTypes)) * 17 + Float.floatToIntBits(this.mMaxLuminance)) * 17 + Float.floatToIntBits(this.mMaxAverageLuminance)) * 17 + Float.floatToIntBits(this.mMinLuminance);
        }

        public void readFromParcel(Parcel parcel) {
            int n = parcel.readInt();
            this.mSupportedHdrTypes = new int[n];
            for (int i = 0; i < n; ++i) {
                this.mSupportedHdrTypes[i] = parcel.readInt();
            }
            this.mMaxLuminance = parcel.readFloat();
            this.mMaxAverageLuminance = parcel.readFloat();
            this.mMinLuminance = parcel.readFloat();
        }

        @Override
        public void writeToParcel(Parcel parcel, int n) {
            int[] arrn;
            parcel.writeInt(this.mSupportedHdrTypes.length);
            for (n = 0; n < (arrn = this.mSupportedHdrTypes).length; ++n) {
                parcel.writeInt(arrn[n]);
            }
            parcel.writeFloat(this.mMaxLuminance);
            parcel.writeFloat(this.mMaxAverageLuminance);
            parcel.writeFloat(this.mMinLuminance);
        }

        @Retention(value=RetentionPolicy.SOURCE)
        public static @interface HdrType {
        }

    }

    public static final class Mode
    implements Parcelable {
        public static final Parcelable.Creator<Mode> CREATOR;
        public static final Mode[] EMPTY_ARRAY;
        private final int mHeight;
        private final int mModeId;
        private final float mRefreshRate;
        private final int mWidth;

        static {
            EMPTY_ARRAY = new Mode[0];
            CREATOR = new Parcelable.Creator<Mode>(){

                @Override
                public Mode createFromParcel(Parcel parcel) {
                    return new Mode(parcel);
                }

                public Mode[] newArray(int n) {
                    return new Mode[n];
                }
            };
        }

        @UnsupportedAppUsage
        public Mode(int n, int n2, int n3, float f) {
            this.mModeId = n;
            this.mWidth = n2;
            this.mHeight = n3;
            this.mRefreshRate = f;
        }

        private Mode(Parcel parcel) {
            this(parcel.readInt(), parcel.readInt(), parcel.readInt(), parcel.readFloat());
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public boolean equals(Object object) {
            boolean bl = true;
            if (this == object) {
                return true;
            }
            if (!(object instanceof Mode)) {
                return false;
            }
            object = (Mode)object;
            if (this.mModeId != ((Mode)object).mModeId || !this.matches(((Mode)object).mWidth, ((Mode)object).mHeight, ((Mode)object).mRefreshRate)) {
                bl = false;
            }
            return bl;
        }

        public int getModeId() {
            return this.mModeId;
        }

        public int getPhysicalHeight() {
            return this.mHeight;
        }

        public int getPhysicalWidth() {
            return this.mWidth;
        }

        public float getRefreshRate() {
            return this.mRefreshRate;
        }

        public int hashCode() {
            return (((1 * 17 + this.mModeId) * 17 + this.mWidth) * 17 + this.mHeight) * 17 + Float.floatToIntBits(this.mRefreshRate);
        }

        public boolean matches(int n, int n2, float f) {
            boolean bl = this.mWidth == n && this.mHeight == n2 && Float.floatToIntBits(this.mRefreshRate) == Float.floatToIntBits(f);
            return bl;
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder("{");
            stringBuilder.append("id=");
            stringBuilder.append(this.mModeId);
            stringBuilder.append(", width=");
            stringBuilder.append(this.mWidth);
            stringBuilder.append(", height=");
            stringBuilder.append(this.mHeight);
            stringBuilder.append(", fps=");
            stringBuilder.append(this.mRefreshRate);
            stringBuilder.append("}");
            return stringBuilder.toString();
        }

        @Override
        public void writeToParcel(Parcel parcel, int n) {
            parcel.writeInt(this.mModeId);
            parcel.writeInt(this.mWidth);
            parcel.writeInt(this.mHeight);
            parcel.writeFloat(this.mRefreshRate);
        }

    }

}

