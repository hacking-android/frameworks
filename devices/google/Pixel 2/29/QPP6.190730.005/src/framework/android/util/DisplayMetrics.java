/*
 * Decompiled with CFR 0.145.
 */
package android.util;

import android.annotation.UnsupportedAppUsage;
import android.os.SystemProperties;

public class DisplayMetrics {
    public static final int DENSITY_140 = 140;
    public static final int DENSITY_180 = 180;
    public static final int DENSITY_200 = 200;
    public static final int DENSITY_220 = 220;
    public static final int DENSITY_260 = 260;
    public static final int DENSITY_280 = 280;
    public static final int DENSITY_300 = 300;
    public static final int DENSITY_340 = 340;
    public static final int DENSITY_360 = 360;
    public static final int DENSITY_400 = 400;
    public static final int DENSITY_420 = 420;
    public static final int DENSITY_440 = 440;
    public static final int DENSITY_450 = 450;
    public static final int DENSITY_560 = 560;
    public static final int DENSITY_600 = 600;
    public static final int DENSITY_DEFAULT = 160;
    public static final float DENSITY_DEFAULT_SCALE = 0.00625f;
    @Deprecated
    @UnsupportedAppUsage
    public static int DENSITY_DEVICE = 0;
    public static final int DENSITY_DEVICE_STABLE;
    public static final int DENSITY_HIGH = 240;
    public static final int DENSITY_LOW = 120;
    public static final int DENSITY_MEDIUM = 160;
    public static final int DENSITY_TV = 213;
    public static final int DENSITY_XHIGH = 320;
    public static final int DENSITY_XXHIGH = 480;
    public static final int DENSITY_XXXHIGH = 640;
    public float density;
    public int densityDpi;
    public int heightPixels;
    public float noncompatDensity;
    @UnsupportedAppUsage
    public int noncompatDensityDpi;
    @UnsupportedAppUsage
    public int noncompatHeightPixels;
    public float noncompatScaledDensity;
    @UnsupportedAppUsage
    public int noncompatWidthPixels;
    public float noncompatXdpi;
    public float noncompatYdpi;
    public float scaledDensity;
    public int widthPixels;
    public float xdpi;
    public float ydpi;

    static {
        DENSITY_DEVICE = DisplayMetrics.getDeviceDensity();
        DENSITY_DEVICE_STABLE = DisplayMetrics.getDeviceDensity();
    }

    private static int getDeviceDensity() {
        return SystemProperties.getInt("qemu.sf.lcd_density", SystemProperties.getInt("ro.sf.lcd_density", 160));
    }

    public boolean equals(DisplayMetrics displayMetrics) {
        boolean bl = this.equalsPhysical(displayMetrics) && this.scaledDensity == displayMetrics.scaledDensity && this.noncompatScaledDensity == displayMetrics.noncompatScaledDensity;
        return bl;
    }

    public boolean equals(Object object) {
        boolean bl = object instanceof DisplayMetrics && this.equals((DisplayMetrics)object);
        return bl;
    }

    public boolean equalsPhysical(DisplayMetrics displayMetrics) {
        boolean bl = displayMetrics != null && this.widthPixels == displayMetrics.widthPixels && this.heightPixels == displayMetrics.heightPixels && this.density == displayMetrics.density && this.densityDpi == displayMetrics.densityDpi && this.xdpi == displayMetrics.xdpi && this.ydpi == displayMetrics.ydpi && this.noncompatWidthPixels == displayMetrics.noncompatWidthPixels && this.noncompatHeightPixels == displayMetrics.noncompatHeightPixels && this.noncompatDensity == displayMetrics.noncompatDensity && this.noncompatDensityDpi == displayMetrics.noncompatDensityDpi && this.noncompatXdpi == displayMetrics.noncompatXdpi && this.noncompatYdpi == displayMetrics.noncompatYdpi;
        return bl;
    }

    public int hashCode() {
        return this.widthPixels * this.heightPixels * this.densityDpi;
    }

    public void setTo(DisplayMetrics displayMetrics) {
        if (this == displayMetrics) {
            return;
        }
        this.widthPixels = displayMetrics.widthPixels;
        this.heightPixels = displayMetrics.heightPixels;
        this.density = displayMetrics.density;
        this.densityDpi = displayMetrics.densityDpi;
        this.scaledDensity = displayMetrics.scaledDensity;
        this.xdpi = displayMetrics.xdpi;
        this.ydpi = displayMetrics.ydpi;
        this.noncompatWidthPixels = displayMetrics.noncompatWidthPixels;
        this.noncompatHeightPixels = displayMetrics.noncompatHeightPixels;
        this.noncompatDensity = displayMetrics.noncompatDensity;
        this.noncompatDensityDpi = displayMetrics.noncompatDensityDpi;
        this.noncompatScaledDensity = displayMetrics.noncompatScaledDensity;
        this.noncompatXdpi = displayMetrics.noncompatXdpi;
        this.noncompatYdpi = displayMetrics.noncompatYdpi;
    }

    public void setToDefaults() {
        float f;
        this.widthPixels = 0;
        this.heightPixels = 0;
        int n = DENSITY_DEVICE;
        this.density = (float)n / 160.0f;
        this.densityDpi = n;
        this.scaledDensity = f = this.density;
        this.xdpi = n;
        this.ydpi = n;
        this.noncompatWidthPixels = this.widthPixels;
        this.noncompatHeightPixels = this.heightPixels;
        this.noncompatDensity = f;
        this.noncompatDensityDpi = this.densityDpi;
        this.noncompatScaledDensity = this.scaledDensity;
        this.noncompatXdpi = this.xdpi;
        this.noncompatYdpi = this.ydpi;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("DisplayMetrics{density=");
        stringBuilder.append(this.density);
        stringBuilder.append(", width=");
        stringBuilder.append(this.widthPixels);
        stringBuilder.append(", height=");
        stringBuilder.append(this.heightPixels);
        stringBuilder.append(", scaledDensity=");
        stringBuilder.append(this.scaledDensity);
        stringBuilder.append(", xdpi=");
        stringBuilder.append(this.xdpi);
        stringBuilder.append(", ydpi=");
        stringBuilder.append(this.ydpi);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }
}

