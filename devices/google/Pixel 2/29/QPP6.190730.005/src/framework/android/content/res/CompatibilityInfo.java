/*
 * Decompiled with CFR 0.145.
 */
package android.content.res;

import android.annotation.UnsupportedAppUsage;
import android.content.pm.ApplicationInfo;
import android.content.res.Configuration;
import android.graphics.Canvas;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.Region;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.WindowManager;

public class CompatibilityInfo
implements Parcelable {
    private static final int ALWAYS_NEEDS_COMPAT = 2;
    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    public static final Parcelable.Creator<CompatibilityInfo> CREATOR;
    @UnsupportedAppUsage
    public static final CompatibilityInfo DEFAULT_COMPATIBILITY_INFO;
    public static final int DEFAULT_NORMAL_SHORT_DIMENSION = 320;
    public static final float MAXIMUM_ASPECT_RATIO = 1.7791667f;
    private static final int NEEDS_COMPAT_RES = 16;
    private static final int NEEDS_SCREEN_COMPAT = 8;
    private static final int NEVER_NEEDS_COMPAT = 4;
    private static final int SCALING_REQUIRED = 1;
    public final int applicationDensity;
    public final float applicationInvertedScale;
    @UnsupportedAppUsage
    public final float applicationScale;
    private final int mCompatibilityFlags;

    static {
        DEFAULT_COMPATIBILITY_INFO = new CompatibilityInfo(){};
        CREATOR = new Parcelable.Creator<CompatibilityInfo>(){

            @Override
            public CompatibilityInfo createFromParcel(Parcel parcel) {
                return new CompatibilityInfo(parcel);
            }

            public CompatibilityInfo[] newArray(int n) {
                return new CompatibilityInfo[n];
            }
        };
    }

    @UnsupportedAppUsage
    private CompatibilityInfo() {
        this(4, DisplayMetrics.DENSITY_DEVICE, 1.0f, 1.0f);
    }

    private CompatibilityInfo(int n, int n2, float f, float f2) {
        this.mCompatibilityFlags = n;
        this.applicationDensity = n2;
        this.applicationScale = f;
        this.applicationInvertedScale = f2;
    }

    @UnsupportedAppUsage
    public CompatibilityInfo(ApplicationInfo applicationInfo, int n, int n2, boolean bl) {
        int n3 = 0;
        if (applicationInfo.targetSdkVersion < 26) {
            n3 = 0 | 16;
        }
        if (applicationInfo.requiresSmallestWidthDp == 0 && applicationInfo.compatibleWidthLimitDp == 0 && applicationInfo.largestWidthLimitDp == 0) {
            int n4;
            int n5;
            n2 = 0;
            int n6 = 0;
            if ((applicationInfo.flags & 2048) != 0) {
                n4 = 0 | 8;
                n5 = 1;
                n2 = n4;
                n6 = n5;
                if (!bl) {
                    n2 = n4 | 34;
                    n6 = n5;
                }
            }
            n5 = n2;
            if ((applicationInfo.flags & 524288) != 0) {
                n4 = 1;
                n5 = n2;
                n6 = n4;
                if (!bl) {
                    n5 = n2 | 34;
                    n6 = n4;
                }
            }
            n2 = n5;
            if ((applicationInfo.flags & 4096) != 0) {
                n6 = 1;
                n2 = n5 | 2;
            }
            n5 = n2;
            if (bl) {
                n5 = n2 & -3;
            }
            n2 = n3 | 8;
            n3 = n & 15;
            if (n3 != 3) {
                if (n3 == 4) {
                    n3 = n2;
                    if ((n5 & 32) != 0) {
                        n3 = n2 & -9;
                    }
                    n2 = n3;
                    if ((applicationInfo.flags & 524288) != 0) {
                        n2 = n3 | 4;
                    }
                }
            } else {
                n3 = n2;
                if ((n5 & 8) != 0) {
                    n3 = n2 & -9;
                }
                n2 = n3;
                if ((applicationInfo.flags & 2048) != 0) {
                    n2 = n3 | 4;
                }
            }
            if ((268435456 & n) != 0) {
                if ((n5 & 2) != 0) {
                    n = n2 & -9;
                } else {
                    n = n2;
                    if (n6 == 0) {
                        n = n2 | 2;
                    }
                }
            } else {
                n = n2 & -9 | 4;
            }
            if ((applicationInfo.flags & 8192) != 0) {
                this.applicationDensity = DisplayMetrics.DENSITY_DEVICE;
                this.applicationScale = 1.0f;
                this.applicationInvertedScale = 1.0f;
            } else {
                this.applicationDensity = 160;
                this.applicationScale = (float)DisplayMetrics.DENSITY_DEVICE / 160.0f;
                this.applicationInvertedScale = 1.0f / this.applicationScale;
                n |= 1;
            }
        } else {
            int n7 = applicationInfo.requiresSmallestWidthDp != 0 ? applicationInfo.requiresSmallestWidthDp : applicationInfo.compatibleWidthLimitDp;
            n = n7;
            if (n7 == 0) {
                n = applicationInfo.largestWidthLimitDp;
            }
            n7 = applicationInfo.compatibleWidthLimitDp != 0 ? applicationInfo.compatibleWidthLimitDp : n;
            int n8 = n7;
            if (n7 < n) {
                n8 = n;
            }
            n7 = applicationInfo.largestWidthLimitDp;
            if (n > 320) {
                n = n3 | 4;
            } else if (n7 != 0 && n2 > n7) {
                n = n3 | 10;
            } else if (n8 >= n2) {
                n = n3 | 4;
            } else {
                n = n3;
                if (bl) {
                    n = n3 | 8;
                }
            }
            this.applicationDensity = DisplayMetrics.DENSITY_DEVICE;
            this.applicationScale = 1.0f;
            this.applicationInvertedScale = 1.0f;
        }
        this.mCompatibilityFlags = n;
    }

    private CompatibilityInfo(Parcel parcel) {
        this.mCompatibilityFlags = parcel.readInt();
        this.applicationDensity = parcel.readInt();
        this.applicationScale = parcel.readFloat();
        this.applicationInvertedScale = parcel.readFloat();
    }

    @UnsupportedAppUsage
    public static float computeCompatibleScaling(DisplayMetrics displayMetrics, DisplayMetrics displayMetrics2) {
        float f;
        int n;
        int n2;
        int n3 = displayMetrics.noncompatWidthPixels;
        int n4 = displayMetrics.noncompatHeightPixels;
        if (n3 < n4) {
            n2 = n3;
            n = n4;
        } else {
            n2 = n4;
            n = n3;
        }
        int n5 = (int)(displayMetrics.density * 320.0f + 0.5f);
        float f2 = f = (float)n / (float)n2;
        if (f > 1.7791667f) {
            f2 = 1.7791667f;
        }
        n = (int)((float)n5 * f2 + 0.5f);
        if (n3 < n4) {
            n2 = n;
            n = n5;
            n5 = n2;
        }
        if ((f = (float)n3 / (float)n) < (f2 = (float)n4 / (float)n5)) {
            f2 = f;
        }
        f = f2;
        if (f2 < 1.0f) {
            f = 1.0f;
        }
        if (displayMetrics2 != null) {
            displayMetrics2.widthPixels = n;
            displayMetrics2.heightPixels = n5;
        }
        return f;
    }

    public boolean alwaysSupportsScreen() {
        boolean bl = (this.mCompatibilityFlags & 4) != 0;
        return bl;
    }

    public void applyToConfiguration(int n, Configuration configuration) {
        if (!this.supportsScreen()) {
            configuration.screenLayout = configuration.screenLayout & -16 | 2;
            configuration.screenWidthDp = configuration.compatScreenWidthDp;
            configuration.screenHeightDp = configuration.compatScreenHeightDp;
            configuration.smallestScreenWidthDp = configuration.compatSmallestScreenWidthDp;
        }
        configuration.densityDpi = n;
        if (this.isScalingRequired()) {
            float f = this.applicationInvertedScale;
            configuration.densityDpi = (int)((float)configuration.densityDpi * f + 0.5f);
        }
    }

    public void applyToDisplayMetrics(DisplayMetrics displayMetrics) {
        if (!this.supportsScreen()) {
            CompatibilityInfo.computeCompatibleScaling(displayMetrics, displayMetrics);
        } else {
            displayMetrics.widthPixels = displayMetrics.noncompatWidthPixels;
            displayMetrics.heightPixels = displayMetrics.noncompatHeightPixels;
        }
        if (this.isScalingRequired()) {
            float f = this.applicationInvertedScale;
            displayMetrics.density = displayMetrics.noncompatDensity * f;
            displayMetrics.densityDpi = (int)((float)displayMetrics.noncompatDensityDpi * f + 0.5f);
            displayMetrics.scaledDensity = displayMetrics.noncompatScaledDensity * f;
            displayMetrics.xdpi = displayMetrics.noncompatXdpi * f;
            displayMetrics.ydpi = displayMetrics.noncompatYdpi * f;
            displayMetrics.widthPixels = (int)((float)displayMetrics.widthPixels * f + 0.5f);
            displayMetrics.heightPixels = (int)((float)displayMetrics.heightPixels * f + 0.5f);
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public boolean equals(Object object) {
        block8 : {
            block7 : {
                block6 : {
                    if (this == object) {
                        return true;
                    }
                    try {
                        object = (CompatibilityInfo)object;
                        if (this.mCompatibilityFlags == ((CompatibilityInfo)object).mCompatibilityFlags) break block6;
                        return false;
                    }
                    catch (ClassCastException classCastException) {
                        return false;
                    }
                }
                if (this.applicationDensity == ((CompatibilityInfo)object).applicationDensity) break block7;
                return false;
            }
            if (this.applicationScale == ((CompatibilityInfo)object).applicationScale) break block8;
            return false;
        }
        float f = this.applicationInvertedScale;
        float f2 = ((CompatibilityInfo)object).applicationInvertedScale;
        return f == f2;
    }

    @UnsupportedAppUsage
    public Translator getTranslator() {
        Translator translator = this.isScalingRequired() ? new Translator() : null;
        return translator;
    }

    public int hashCode() {
        return (((17 * 31 + this.mCompatibilityFlags) * 31 + this.applicationDensity) * 31 + Float.floatToIntBits(this.applicationScale)) * 31 + Float.floatToIntBits(this.applicationInvertedScale);
    }

    @UnsupportedAppUsage
    public boolean isScalingRequired() {
        int n = this.mCompatibilityFlags;
        boolean bl = true;
        if ((n & 1) == 0) {
            bl = false;
        }
        return bl;
    }

    public boolean needsCompatResources() {
        boolean bl = (this.mCompatibilityFlags & 16) != 0;
        return bl;
    }

    public boolean neverSupportsScreen() {
        boolean bl = (this.mCompatibilityFlags & 2) != 0;
        return bl;
    }

    @UnsupportedAppUsage
    public boolean supportsScreen() {
        boolean bl = (this.mCompatibilityFlags & 8) == 0;
        return bl;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder(128);
        stringBuilder.append("{");
        stringBuilder.append(this.applicationDensity);
        stringBuilder.append("dpi");
        if (this.isScalingRequired()) {
            stringBuilder.append(" ");
            stringBuilder.append(this.applicationScale);
            stringBuilder.append("x");
        }
        if (!this.supportsScreen()) {
            stringBuilder.append(" resizing");
        }
        if (this.neverSupportsScreen()) {
            stringBuilder.append(" never-compat");
        }
        if (this.alwaysSupportsScreen()) {
            stringBuilder.append(" always-compat");
        }
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeInt(this.mCompatibilityFlags);
        parcel.writeInt(this.applicationDensity);
        parcel.writeFloat(this.applicationScale);
        parcel.writeFloat(this.applicationInvertedScale);
    }

    public class Translator {
        @UnsupportedAppUsage
        public final float applicationInvertedScale;
        @UnsupportedAppUsage
        public final float applicationScale;
        private Rect mContentInsetsBuffer = null;
        private Region mTouchableAreaBuffer = null;
        private Rect mVisibleInsetsBuffer = null;

        Translator() {
            this(compatibilityInfo.applicationScale, compatibilityInfo.applicationInvertedScale);
        }

        Translator(float f, float f2) {
            this.applicationScale = f;
            this.applicationInvertedScale = f2;
        }

        @UnsupportedAppUsage
        public Rect getTranslatedContentInsets(Rect rect) {
            if (this.mContentInsetsBuffer == null) {
                this.mContentInsetsBuffer = new Rect();
            }
            this.mContentInsetsBuffer.set(rect);
            this.translateRectInAppWindowToScreen(this.mContentInsetsBuffer);
            return this.mContentInsetsBuffer;
        }

        public Region getTranslatedTouchableArea(Region region) {
            if (this.mTouchableAreaBuffer == null) {
                this.mTouchableAreaBuffer = new Region();
            }
            this.mTouchableAreaBuffer.set(region);
            this.mTouchableAreaBuffer.scale(this.applicationScale);
            return this.mTouchableAreaBuffer;
        }

        public Rect getTranslatedVisibleInsets(Rect rect) {
            if (this.mVisibleInsetsBuffer == null) {
                this.mVisibleInsetsBuffer = new Rect();
            }
            this.mVisibleInsetsBuffer.set(rect);
            this.translateRectInAppWindowToScreen(this.mVisibleInsetsBuffer);
            return this.mVisibleInsetsBuffer;
        }

        @UnsupportedAppUsage
        public void translateCanvas(Canvas canvas) {
            if (this.applicationScale == 1.5f) {
                canvas.translate(0.0026143792f, 0.0026143792f);
            }
            float f = this.applicationScale;
            canvas.scale(f, f);
        }

        @UnsupportedAppUsage
        public void translateEventInScreenToAppWindow(MotionEvent motionEvent) {
            motionEvent.scale(this.applicationInvertedScale);
        }

        public void translateLayoutParamsInAppWindowToScreen(WindowManager.LayoutParams layoutParams) {
            layoutParams.scale(this.applicationScale);
        }

        public void translatePointInScreenToAppWindow(PointF pointF) {
            float f = this.applicationInvertedScale;
            if (f != 1.0f) {
                pointF.x *= f;
                pointF.y *= f;
            }
        }

        @UnsupportedAppUsage
        public void translateRectInAppWindowToScreen(Rect rect) {
            rect.scale(this.applicationScale);
        }

        @UnsupportedAppUsage
        public void translateRectInScreenToAppWinFrame(Rect rect) {
            rect.scale(this.applicationInvertedScale);
        }

        @UnsupportedAppUsage
        public void translateRectInScreenToAppWindow(Rect rect) {
            rect.scale(this.applicationInvertedScale);
        }

        @UnsupportedAppUsage
        public void translateRegionInWindowToScreen(Region region) {
            region.scale(this.applicationScale);
        }

        @UnsupportedAppUsage
        public void translateWindowLayout(WindowManager.LayoutParams layoutParams) {
            layoutParams.scale(this.applicationScale);
        }
    }

}

