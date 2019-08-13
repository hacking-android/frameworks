/*
 * Decompiled with CFR 0.145.
 */
package android.location;

import android.annotation.SystemApi;
import android.annotation.UnsupportedAppUsage;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.SystemClock;
import android.util.Printer;
import android.util.TimeUtils;
import java.text.DecimalFormat;
import java.util.StringTokenizer;

public class Location
implements Parcelable {
    public static final Parcelable.Creator<Location> CREATOR;
    public static final String EXTRA_COARSE_LOCATION = "coarseLocation";
    public static final String EXTRA_NO_GPS_LOCATION = "noGPSLocation";
    public static final int FORMAT_DEGREES = 0;
    public static final int FORMAT_MINUTES = 1;
    public static final int FORMAT_SECONDS = 2;
    private static final int HAS_ALTITUDE_MASK = 1;
    private static final int HAS_BEARING_ACCURACY_MASK = 128;
    private static final int HAS_BEARING_MASK = 4;
    private static final int HAS_ELAPSED_REALTIME_UNCERTAINTY_MASK = 256;
    private static final int HAS_HORIZONTAL_ACCURACY_MASK = 8;
    private static final int HAS_MOCK_PROVIDER_MASK = 16;
    private static final int HAS_SPEED_ACCURACY_MASK = 64;
    private static final int HAS_SPEED_MASK = 2;
    private static final int HAS_VERTICAL_ACCURACY_MASK = 32;
    private static ThreadLocal<BearingDistanceCache> sBearingDistanceCache;
    private double mAltitude = 0.0;
    private float mBearing = 0.0f;
    private float mBearingAccuracyDegrees = 0.0f;
    @UnsupportedAppUsage
    private long mElapsedRealtimeNanos = 0L;
    private double mElapsedRealtimeUncertaintyNanos = 0.0;
    private Bundle mExtras = null;
    private int mFieldsMask = 0;
    private float mHorizontalAccuracyMeters = 0.0f;
    private double mLatitude = 0.0;
    private double mLongitude = 0.0;
    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    private String mProvider;
    private float mSpeed = 0.0f;
    private float mSpeedAccuracyMetersPerSecond = 0.0f;
    private long mTime = 0L;
    private float mVerticalAccuracyMeters = 0.0f;

    static {
        sBearingDistanceCache = new ThreadLocal<BearingDistanceCache>(){

            @Override
            protected BearingDistanceCache initialValue() {
                return new BearingDistanceCache();
            }
        };
        CREATOR = new Parcelable.Creator<Location>(){

            @Override
            public Location createFromParcel(Parcel parcel) {
                Location location = new Location(parcel.readString());
                location.mTime = parcel.readLong();
                location.mElapsedRealtimeNanos = parcel.readLong();
                location.mElapsedRealtimeUncertaintyNanos = parcel.readDouble();
                location.mFieldsMask = parcel.readInt();
                location.mLatitude = parcel.readDouble();
                location.mLongitude = parcel.readDouble();
                location.mAltitude = parcel.readDouble();
                location.mSpeed = parcel.readFloat();
                location.mBearing = parcel.readFloat();
                location.mHorizontalAccuracyMeters = parcel.readFloat();
                location.mVerticalAccuracyMeters = parcel.readFloat();
                location.mSpeedAccuracyMetersPerSecond = parcel.readFloat();
                location.mBearingAccuracyDegrees = parcel.readFloat();
                location.mExtras = Bundle.setDefusable(parcel.readBundle(), true);
                return location;
            }

            public Location[] newArray(int n) {
                return new Location[n];
            }
        };
    }

    public Location(Location location) {
        this.set(location);
    }

    public Location(String string2) {
        this.mProvider = string2;
    }

    private static void computeDistanceAndBearing(double d, double d2, double d3, double d4, BearingDistanceCache bearingDistanceCache) {
        double d5 = d * 0.017453292519943295;
        double d6 = d3 * 0.017453292519943295;
        double d7 = d2 * 0.017453292519943295;
        double d8 = 0.017453292519943295 * d4;
        double d9 = (6378137.0 - 6356752.3142) / 6378137.0;
        double d10 = (6378137.0 * 6378137.0 - 6356752.3142 * 6356752.3142) / (6356752.3142 * 6356752.3142);
        double d11 = d8 - d7;
        d = 0.0;
        d3 = Math.atan((1.0 - d9) * Math.tan(d5));
        d2 = Math.atan((1.0 - d9) * Math.tan(d6));
        double d12 = Math.cos(d3);
        double d13 = Math.cos(d2);
        double d14 = Math.sin(d3);
        double d15 = Math.sin(d2);
        double d16 = d12 * d13;
        double d17 = d14 * d15;
        d2 = 0.0;
        d3 = 0.0;
        double d18 = 0.0;
        d4 = 0.0;
        double d19 = 0.0;
        double d20 = d11;
        int n = 0;
        do {
            double d21 = d20;
            if (n >= 20) break;
            d4 = Math.cos(d21);
            d19 = Math.sin(d21);
            d2 = d13 * d19;
            d = d12 * d15 - d14 * d13 * d4;
            double d22 = Math.sqrt(d2 * d2 + d * d);
            double d23 = d17 + d16 * d4;
            d2 = Math.atan2(d22, d23);
            d18 = 0.0;
            d20 = d22 == 0.0 ? 0.0 : d16 * d19 / d22;
            d3 = 1.0 - d20 * d20;
            if (d3 != 0.0) {
                d18 = d23 - d17 * 2.0 / d3;
            }
            double d24 = d3 * d10;
            d = d24 / 16384.0 * (((320.0 - 175.0 * d24) * d24 - 768.0) * d24 + 4096.0) + 1.0;
            d24 = d24 / 1024.0 * (((74.0 - 47.0 * d24) * d24 - 128.0) * d24 + 256.0);
            double d25 = d9 / 16.0 * d3 * ((4.0 - 3.0 * d3) * d9 + 4.0);
            d3 = d18 * d18;
            d3 = d24 * d22 * (d18 + d24 / 4.0 * ((d3 * 2.0 - 1.0) * d23 - d24 / 6.0 * d18 * (d22 * 4.0 * d22 - 3.0) * (4.0 * d3 - 3.0)));
            d20 = d11 + (1.0 - d25) * d9 * d20 * (d2 + d25 * d22 * (d18 + d25 * d23 * (2.0 * d18 * d18 - 1.0)));
            if (Math.abs((d20 - d21) / d20) < 1.0E-12) break;
            ++n;
            d18 = d22;
        } while (true);
        bearingDistanceCache.mDistance = (float)(6356752.3142 * d * (d2 - d3));
        bearingDistanceCache.mInitialBearing = (float)((double)((float)Math.atan2(d13 * d19, d12 * d15 - d14 * d13 * d4)) * 57.29577951308232);
        bearingDistanceCache.mFinalBearing = (float)((double)((float)Math.atan2(d12 * d19, -d14 * d13 + d12 * d15 * d4)) * 57.29577951308232);
        bearingDistanceCache.mLat1 = d5;
        bearingDistanceCache.mLat2 = d6;
        bearingDistanceCache.mLon1 = d7;
        bearingDistanceCache.mLon2 = d8;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public static double convert(String var0) {
        block16 : {
            block15 : {
                block14 : {
                    block13 : {
                        if (var0 == null) throw new NullPointerException("coordinate");
                        if (var0.charAt(0) == '-') {
                            var0 = var0.substring(1);
                            var1_1 = true;
                        } else {
                            var1_1 = false;
                        }
                        var2_2 = new StringTokenizer(var0, ":");
                        var3_3 = var2_2.countTokens();
                        if (var3_3 < 1) {
                            var4_9 = new StringBuilder();
                            var4_9.append("coordinate=");
                            var4_9.append(var0);
                            throw new IllegalArgumentException(var4_9.toString());
                        }
                        var4_4 = var2_2.nextToken();
                        if (var3_3 != 1) break block13;
                        try {
                            var5_10 = Double.parseDouble((String)var4_4);
                            if (var1_1 == false) return var5_10;
                        }
                        catch (NumberFormatException var4_5) {}
                        return -var5_10;
                    }
                    var7_12 = var2_2.nextToken();
                    var8_13 = Integer.parseInt((String)var4_4);
                    var5_11 = 0.0;
                    var3_3 = 0;
                    var9_14 = var2_2.hasMoreTokens();
                    if (!var9_14) break block14;
                    var10_15 = Integer.parseInt((String)var7_12);
                    var5_11 = Double.parseDouble(var2_2.nextToken());
                    var3_3 = 1;
                    break block15;
                    break block16;
                }
                var10_15 = Double.parseDouble((String)var7_12);
            }
            var12_16 = var1_1 != false && var8_13 == 180 && var10_15 == 0.0 && var5_11 == 0.0;
            if ((double)var8_13 < 0.0 || var8_13 > 179 && !var12_16) ** GOTO lbl64
            if (var10_15 < 0.0 || var10_15 >= 60.0 || var3_3 != 0 && var10_15 > 59.0) ** GOTO lbl57
            if (!(var5_11 < 0.0) && !(var5_11 >= 60.0)) {
                var5_11 = ((double)var8_13 * 3600.0 + 60.0 * var10_15 + var5_11) / 3600.0;
                if (var1_1 == false) return var5_11;
                return -var5_11;
            }
            try {
                var4_4 = new StringBuilder();
                var4_4.append("coordinate=");
                var4_4.append(var0);
                var7_12 = new IllegalArgumentException(var4_4.toString());
                throw var7_12;
lbl57: // 1 sources:
                var7_12 = new StringBuilder();
                var7_12.append("coordinate=");
                var7_12.append(var0);
                var4_4 = new IllegalArgumentException(var7_12.toString());
                throw var4_4;
lbl64: // 1 sources:
                var7_12 = new StringBuilder();
                var7_12.append("coordinate=");
                var7_12.append(var0);
                var4_4 = new IllegalArgumentException(var7_12.toString());
                throw var4_4;
            }
            catch (NumberFormatException var4_6) {}
            break block16;
            catch (NumberFormatException var4_7) {
                // empty catch block
            }
        }
        var4_8 = new StringBuilder();
        var4_8.append("coordinate=");
        var4_8.append(var0);
        throw new IllegalArgumentException(var4_8.toString());
    }

    public static String convert(double d, int n) {
        block6 : {
            DecimalFormat decimalFormat;
            StringBuilder stringBuilder;
            block8 : {
                double d2;
                block7 : {
                    if (d < -180.0 || d > 180.0 || Double.isNaN(d)) break block6;
                    if (n != 0 && n != 1 && n != 2) {
                        StringBuilder stringBuilder2 = new StringBuilder();
                        stringBuilder2.append("outputType=");
                        stringBuilder2.append(n);
                        throw new IllegalArgumentException(stringBuilder2.toString());
                    }
                    stringBuilder = new StringBuilder();
                    d2 = d;
                    if (d < 0.0) {
                        stringBuilder.append('-');
                        d2 = -d;
                    }
                    decimalFormat = new DecimalFormat("###.#####");
                    if (n == 1) break block7;
                    d = d2;
                    if (n != 2) break block8;
                }
                int n2 = (int)Math.floor(d2);
                stringBuilder.append(n2);
                stringBuilder.append(':');
                d = d2 = (d2 - (double)n2) * 60.0;
                if (n == 2) {
                    n = (int)Math.floor(d2);
                    stringBuilder.append(n);
                    stringBuilder.append(':');
                    d = (d2 - (double)n) * 60.0;
                }
            }
            stringBuilder.append(decimalFormat.format(d));
            return stringBuilder.toString();
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("coordinate=");
        stringBuilder.append(d);
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    public static void distanceBetween(double d, double d2, double d3, double d4, float[] arrf) {
        if (arrf != null && arrf.length >= 1) {
            BearingDistanceCache bearingDistanceCache = sBearingDistanceCache.get();
            Location.computeDistanceAndBearing(d, d2, d3, d4, bearingDistanceCache);
            arrf[0] = bearingDistanceCache.mDistance;
            if (arrf.length > 1) {
                arrf[1] = bearingDistanceCache.mInitialBearing;
                if (arrf.length > 2) {
                    arrf[2] = bearingDistanceCache.mFinalBearing;
                }
            }
            return;
        }
        throw new IllegalArgumentException("results is null or has length < 1");
    }

    public float bearingTo(Location location) {
        BearingDistanceCache bearingDistanceCache = sBearingDistanceCache.get();
        if (this.mLatitude != bearingDistanceCache.mLat1 || this.mLongitude != bearingDistanceCache.mLon1 || location.mLatitude != bearingDistanceCache.mLat2 || location.mLongitude != bearingDistanceCache.mLon2) {
            Location.computeDistanceAndBearing(this.mLatitude, this.mLongitude, location.mLatitude, location.mLongitude, bearingDistanceCache);
        }
        return bearingDistanceCache.mInitialBearing;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public float distanceTo(Location location) {
        BearingDistanceCache bearingDistanceCache = sBearingDistanceCache.get();
        if (this.mLatitude != bearingDistanceCache.mLat1 || this.mLongitude != bearingDistanceCache.mLon1 || location.mLatitude != bearingDistanceCache.mLat2 || location.mLongitude != bearingDistanceCache.mLon2) {
            Location.computeDistanceAndBearing(this.mLatitude, this.mLongitude, location.mLatitude, location.mLongitude, bearingDistanceCache);
        }
        return bearingDistanceCache.mDistance;
    }

    public void dump(Printer printer, String string2) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(string2);
        stringBuilder.append(this.toString());
        printer.println(stringBuilder.toString());
    }

    public float getAccuracy() {
        return this.mHorizontalAccuracyMeters;
    }

    public double getAltitude() {
        return this.mAltitude;
    }

    public float getBearing() {
        return this.mBearing;
    }

    public float getBearingAccuracyDegrees() {
        return this.mBearingAccuracyDegrees;
    }

    public long getElapsedRealtimeNanos() {
        return this.mElapsedRealtimeNanos;
    }

    public double getElapsedRealtimeUncertaintyNanos() {
        return this.mElapsedRealtimeUncertaintyNanos;
    }

    public Location getExtraLocation(String string2) {
        Bundle bundle = this.mExtras;
        if (bundle != null && (string2 = bundle.getParcelable(string2)) instanceof Location) {
            return (Location)((Object)string2);
        }
        return null;
    }

    public Bundle getExtras() {
        return this.mExtras;
    }

    public double getLatitude() {
        return this.mLatitude;
    }

    public double getLongitude() {
        return this.mLongitude;
    }

    public String getProvider() {
        return this.mProvider;
    }

    public float getSpeed() {
        return this.mSpeed;
    }

    public float getSpeedAccuracyMetersPerSecond() {
        return this.mSpeedAccuracyMetersPerSecond;
    }

    public long getTime() {
        return this.mTime;
    }

    public float getVerticalAccuracyMeters() {
        return this.mVerticalAccuracyMeters;
    }

    public boolean hasAccuracy() {
        boolean bl = (this.mFieldsMask & 8) != 0;
        return bl;
    }

    public boolean hasAltitude() {
        int n = this.mFieldsMask;
        boolean bl = true;
        if ((n & 1) == 0) {
            bl = false;
        }
        return bl;
    }

    public boolean hasBearing() {
        boolean bl = (this.mFieldsMask & 4) != 0;
        return bl;
    }

    public boolean hasBearingAccuracy() {
        boolean bl = (this.mFieldsMask & 128) != 0;
        return bl;
    }

    public boolean hasElapsedRealtimeUncertaintyNanos() {
        boolean bl = (this.mFieldsMask & 256) != 0;
        return bl;
    }

    public boolean hasSpeed() {
        boolean bl = (this.mFieldsMask & 2) != 0;
        return bl;
    }

    public boolean hasSpeedAccuracy() {
        boolean bl = (this.mFieldsMask & 64) != 0;
        return bl;
    }

    public boolean hasVerticalAccuracy() {
        boolean bl = (this.mFieldsMask & 32) != 0;
        return bl;
    }

    @SystemApi
    public boolean isComplete() {
        if (this.mProvider == null) {
            return false;
        }
        if (!this.hasAccuracy()) {
            return false;
        }
        if (this.mTime == 0L) {
            return false;
        }
        return this.mElapsedRealtimeNanos != 0L;
    }

    public boolean isFromMockProvider() {
        boolean bl = (this.mFieldsMask & 16) != 0;
        return bl;
    }

    @SystemApi
    public void makeComplete() {
        if (this.mProvider == null) {
            this.mProvider = "?";
        }
        if (!this.hasAccuracy()) {
            this.mFieldsMask |= 8;
            this.mHorizontalAccuracyMeters = 100.0f;
        }
        if (this.mTime == 0L) {
            this.mTime = System.currentTimeMillis();
        }
        if (this.mElapsedRealtimeNanos == 0L) {
            this.mElapsedRealtimeNanos = SystemClock.elapsedRealtimeNanos();
        }
    }

    @Deprecated
    public void removeAccuracy() {
        this.mHorizontalAccuracyMeters = 0.0f;
        this.mFieldsMask &= -9;
    }

    @Deprecated
    public void removeAltitude() {
        this.mAltitude = 0.0;
        this.mFieldsMask &= -2;
    }

    @Deprecated
    public void removeBearing() {
        this.mBearing = 0.0f;
        this.mFieldsMask &= -5;
    }

    @Deprecated
    public void removeBearingAccuracy() {
        this.mBearingAccuracyDegrees = 0.0f;
        this.mFieldsMask &= -129;
    }

    @Deprecated
    public void removeSpeed() {
        this.mSpeed = 0.0f;
        this.mFieldsMask &= -3;
    }

    @Deprecated
    public void removeSpeedAccuracy() {
        this.mSpeedAccuracyMetersPerSecond = 0.0f;
        this.mFieldsMask &= -65;
    }

    @Deprecated
    public void removeVerticalAccuracy() {
        this.mVerticalAccuracyMeters = 0.0f;
        this.mFieldsMask &= -33;
    }

    public void reset() {
        this.mProvider = null;
        this.mTime = 0L;
        this.mElapsedRealtimeNanos = 0L;
        this.mElapsedRealtimeUncertaintyNanos = 0.0;
        this.mFieldsMask = 0;
        this.mLatitude = 0.0;
        this.mLongitude = 0.0;
        this.mAltitude = 0.0;
        this.mSpeed = 0.0f;
        this.mBearing = 0.0f;
        this.mHorizontalAccuracyMeters = 0.0f;
        this.mVerticalAccuracyMeters = 0.0f;
        this.mSpeedAccuracyMetersPerSecond = 0.0f;
        this.mBearingAccuracyDegrees = 0.0f;
        this.mExtras = null;
    }

    public void set(Location parcelable) {
        this.mProvider = parcelable.mProvider;
        this.mTime = parcelable.mTime;
        this.mElapsedRealtimeNanos = parcelable.mElapsedRealtimeNanos;
        this.mElapsedRealtimeUncertaintyNanos = parcelable.mElapsedRealtimeUncertaintyNanos;
        this.mFieldsMask = parcelable.mFieldsMask;
        this.mLatitude = parcelable.mLatitude;
        this.mLongitude = parcelable.mLongitude;
        this.mAltitude = parcelable.mAltitude;
        this.mSpeed = parcelable.mSpeed;
        this.mBearing = parcelable.mBearing;
        this.mHorizontalAccuracyMeters = parcelable.mHorizontalAccuracyMeters;
        this.mVerticalAccuracyMeters = parcelable.mVerticalAccuracyMeters;
        this.mSpeedAccuracyMetersPerSecond = parcelable.mSpeedAccuracyMetersPerSecond;
        this.mBearingAccuracyDegrees = parcelable.mBearingAccuracyDegrees;
        parcelable = parcelable.mExtras;
        parcelable = parcelable == null ? null : new Bundle((Bundle)parcelable);
        this.mExtras = parcelable;
    }

    public void setAccuracy(float f) {
        this.mHorizontalAccuracyMeters = f;
        this.mFieldsMask |= 8;
    }

    public void setAltitude(double d) {
        this.mAltitude = d;
        this.mFieldsMask |= 1;
    }

    public void setBearing(float f) {
        float f2;
        do {
            if (!(f < 0.0f)) break;
            f += 360.0f;
        } while (true);
        for (f2 = f; f2 >= 360.0f; f2 -= 360.0f) {
        }
        this.mBearing = f2;
        this.mFieldsMask |= 4;
    }

    public void setBearingAccuracyDegrees(float f) {
        this.mBearingAccuracyDegrees = f;
        this.mFieldsMask |= 128;
    }

    public void setElapsedRealtimeNanos(long l) {
        this.mElapsedRealtimeNanos = l;
    }

    public void setElapsedRealtimeUncertaintyNanos(double d) {
        this.mElapsedRealtimeUncertaintyNanos = d;
        this.mFieldsMask |= 256;
    }

    @UnsupportedAppUsage
    public void setExtraLocation(String string2, Location location) {
        if (this.mExtras == null) {
            this.mExtras = new Bundle();
        }
        this.mExtras.putParcelable(string2, location);
    }

    public void setExtras(Bundle bundle) {
        bundle = bundle == null ? null : new Bundle(bundle);
        this.mExtras = bundle;
    }

    @SystemApi
    public void setIsFromMockProvider(boolean bl) {
        this.mFieldsMask = bl ? (this.mFieldsMask |= 16) : (this.mFieldsMask &= -17);
    }

    public void setLatitude(double d) {
        this.mLatitude = d;
    }

    public void setLongitude(double d) {
        this.mLongitude = d;
    }

    public void setProvider(String string2) {
        this.mProvider = string2;
    }

    public void setSpeed(float f) {
        this.mSpeed = f;
        this.mFieldsMask |= 2;
    }

    public void setSpeedAccuracyMetersPerSecond(float f) {
        this.mSpeedAccuracyMetersPerSecond = f;
        this.mFieldsMask |= 64;
    }

    public void setTime(long l) {
        this.mTime = l;
    }

    public void setVerticalAccuracyMeters(float f) {
        this.mVerticalAccuracyMeters = f;
        this.mFieldsMask |= 32;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Location[");
        stringBuilder.append(this.mProvider);
        stringBuilder.append(String.format(" %.6f,%.6f", this.mLatitude, this.mLongitude));
        if (this.hasAccuracy()) {
            stringBuilder.append(String.format(" hAcc=%.0f", Float.valueOf(this.mHorizontalAccuracyMeters)));
        } else {
            stringBuilder.append(" hAcc=???");
        }
        if (this.mTime == 0L) {
            stringBuilder.append(" t=?!?");
        }
        if (this.mElapsedRealtimeNanos == 0L) {
            stringBuilder.append(" et=?!?");
        } else {
            stringBuilder.append(" et=");
            TimeUtils.formatDuration(this.mElapsedRealtimeNanos / 1000000L, stringBuilder);
        }
        if (this.hasElapsedRealtimeUncertaintyNanos()) {
            stringBuilder.append(" etAcc=");
            TimeUtils.formatDuration((long)(this.mElapsedRealtimeUncertaintyNanos / 1000000.0), stringBuilder);
        }
        if (this.hasAltitude()) {
            stringBuilder.append(" alt=");
            stringBuilder.append(this.mAltitude);
        }
        if (this.hasSpeed()) {
            stringBuilder.append(" vel=");
            stringBuilder.append(this.mSpeed);
        }
        if (this.hasBearing()) {
            stringBuilder.append(" bear=");
            stringBuilder.append(this.mBearing);
        }
        if (this.hasVerticalAccuracy()) {
            stringBuilder.append(String.format(" vAcc=%.0f", Float.valueOf(this.mVerticalAccuracyMeters)));
        } else {
            stringBuilder.append(" vAcc=???");
        }
        if (this.hasSpeedAccuracy()) {
            stringBuilder.append(String.format(" sAcc=%.0f", Float.valueOf(this.mSpeedAccuracyMetersPerSecond)));
        } else {
            stringBuilder.append(" sAcc=???");
        }
        if (this.hasBearingAccuracy()) {
            stringBuilder.append(String.format(" bAcc=%.0f", Float.valueOf(this.mBearingAccuracyDegrees)));
        } else {
            stringBuilder.append(" bAcc=???");
        }
        if (this.isFromMockProvider()) {
            stringBuilder.append(" mock");
        }
        if (this.mExtras != null) {
            stringBuilder.append(" {");
            stringBuilder.append(this.mExtras);
            stringBuilder.append('}');
        }
        stringBuilder.append(']');
        return stringBuilder.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeString(this.mProvider);
        parcel.writeLong(this.mTime);
        parcel.writeLong(this.mElapsedRealtimeNanos);
        parcel.writeDouble(this.mElapsedRealtimeUncertaintyNanos);
        parcel.writeInt(this.mFieldsMask);
        parcel.writeDouble(this.mLatitude);
        parcel.writeDouble(this.mLongitude);
        parcel.writeDouble(this.mAltitude);
        parcel.writeFloat(this.mSpeed);
        parcel.writeFloat(this.mBearing);
        parcel.writeFloat(this.mHorizontalAccuracyMeters);
        parcel.writeFloat(this.mVerticalAccuracyMeters);
        parcel.writeFloat(this.mSpeedAccuracyMetersPerSecond);
        parcel.writeFloat(this.mBearingAccuracyDegrees);
        parcel.writeBundle(this.mExtras);
    }

    private static class BearingDistanceCache {
        private float mDistance = 0.0f;
        private float mFinalBearing = 0.0f;
        private float mInitialBearing = 0.0f;
        private double mLat1 = 0.0;
        private double mLat2 = 0.0;
        private double mLon1 = 0.0;
        private double mLon2 = 0.0;

        private BearingDistanceCache() {
        }
    }

}

