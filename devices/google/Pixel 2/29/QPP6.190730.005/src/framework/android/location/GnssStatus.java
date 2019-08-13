/*
 * Decompiled with CFR 0.145.
 */
package android.location;

import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public final class GnssStatus {
    public static final int CONSTELLATION_BEIDOU = 5;
    public static final int CONSTELLATION_COUNT = 8;
    public static final int CONSTELLATION_GALILEO = 6;
    public static final int CONSTELLATION_GLONASS = 3;
    public static final int CONSTELLATION_GPS = 1;
    public static final int CONSTELLATION_IRNSS = 7;
    public static final int CONSTELLATION_QZSS = 4;
    public static final int CONSTELLATION_SBAS = 2;
    public static final int CONSTELLATION_TYPE_MASK = 15;
    public static final int CONSTELLATION_TYPE_SHIFT_WIDTH = 4;
    public static final int CONSTELLATION_UNKNOWN = 0;
    public static final int GNSS_SV_FLAGS_HAS_ALMANAC_DATA = 2;
    public static final int GNSS_SV_FLAGS_HAS_CARRIER_FREQUENCY = 8;
    public static final int GNSS_SV_FLAGS_HAS_EPHEMERIS_DATA = 1;
    public static final int GNSS_SV_FLAGS_NONE = 0;
    public static final int GNSS_SV_FLAGS_USED_IN_FIX = 4;
    public static final int SVID_SHIFT_WIDTH = 8;
    final float[] mAzimuths;
    final float[] mCarrierFrequencies;
    final float[] mCn0DbHz;
    final float[] mElevations;
    final int mSvCount;
    final int[] mSvidWithFlags;

    public GnssStatus(int n, int[] arrn, float[] arrf, float[] arrf2, float[] arrf3, float[] arrf4) {
        this.mSvCount = n;
        this.mSvidWithFlags = arrn;
        this.mCn0DbHz = arrf;
        this.mElevations = arrf2;
        this.mAzimuths = arrf3;
        this.mCarrierFrequencies = arrf4;
    }

    public static String constellationTypeToString(int n) {
        switch (n) {
            default: {
                return Integer.toString(n);
            }
            case 7: {
                return "IRNSS";
            }
            case 6: {
                return "GALILEO";
            }
            case 5: {
                return "BEIDOU";
            }
            case 4: {
                return "QZSS";
            }
            case 3: {
                return "GLONASS";
            }
            case 2: {
                return "SBAS";
            }
            case 1: {
                return "GPS";
            }
            case 0: 
        }
        return "UNKNOWN";
    }

    public float getAzimuthDegrees(int n) {
        return this.mAzimuths[n];
    }

    public float getCarrierFrequencyHz(int n) {
        return this.mCarrierFrequencies[n];
    }

    public float getCn0DbHz(int n) {
        return this.mCn0DbHz[n];
    }

    public int getConstellationType(int n) {
        return this.mSvidWithFlags[n] >> 4 & 15;
    }

    public float getElevationDegrees(int n) {
        return this.mElevations[n];
    }

    public int getSatelliteCount() {
        return this.mSvCount;
    }

    public int getSvid(int n) {
        return this.mSvidWithFlags[n] >> 8;
    }

    public boolean hasAlmanacData(int n) {
        boolean bl = (this.mSvidWithFlags[n] & 2) != 0;
        return bl;
    }

    public boolean hasCarrierFrequencyHz(int n) {
        boolean bl = (this.mSvidWithFlags[n] & 8) != 0;
        return bl;
    }

    public boolean hasEphemerisData(int n) {
        n = this.mSvidWithFlags[n];
        boolean bl = true;
        if ((n & 1) == 0) {
            bl = false;
        }
        return bl;
    }

    public boolean usedInFix(int n) {
        boolean bl = (this.mSvidWithFlags[n] & 4) != 0;
        return bl;
    }

    public static abstract class Callback {
        public void onFirstFix(int n) {
        }

        public void onSatelliteStatusChanged(GnssStatus gnssStatus) {
        }

        public void onStarted() {
        }

        public void onStopped() {
        }
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface ConstellationType {
    }

}

