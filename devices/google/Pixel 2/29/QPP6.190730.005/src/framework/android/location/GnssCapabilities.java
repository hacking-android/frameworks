/*
 * Decompiled with CFR 0.145.
 */
package android.location;

import android.annotation.SystemApi;

@SystemApi
public final class GnssCapabilities {
    public static final long GEOFENCING = 4L;
    public static final long INVALID_CAPABILITIES = -1L;
    public static final long LOW_POWER_MODE = 1L;
    public static final long MEASUREMENTS = 8L;
    public static final long MEASUREMENT_CORRECTIONS = 32L;
    public static final long MEASUREMENT_CORRECTIONS_EXCESS_PATH_LENGTH = 128L;
    public static final long MEASUREMENT_CORRECTIONS_LOS_SATS = 64L;
    public static final long MEASUREMENT_CORRECTIONS_REFLECTING_PLANE = 256L;
    public static final long NAV_MESSAGES = 16L;
    public static final long SATELLITE_BLACKLIST = 2L;
    private final long mGnssCapabilities;

    private GnssCapabilities(long l) {
        this.mGnssCapabilities = l;
    }

    private boolean hasCapability(long l) {
        boolean bl = (this.mGnssCapabilities & l) == l;
        return bl;
    }

    public static GnssCapabilities of(long l) {
        return new GnssCapabilities(l);
    }

    public boolean hasGeofencing() {
        return this.hasCapability(4L);
    }

    public boolean hasLowPowerMode() {
        return this.hasCapability(1L);
    }

    public boolean hasMeasurementCorrections() {
        return this.hasCapability(32L);
    }

    public boolean hasMeasurementCorrectionsExcessPathLength() {
        return this.hasCapability(128L);
    }

    public boolean hasMeasurementCorrectionsLosSats() {
        return this.hasCapability(64L);
    }

    public boolean hasMeasurementCorrectionsReflectingPane() {
        return this.hasCapability(256L);
    }

    public boolean hasMeasurements() {
        return this.hasCapability(8L);
    }

    public boolean hasNavMessages() {
        return this.hasCapability(16L);
    }

    public boolean hasSatelliteBlacklist() {
        return this.hasCapability(2L);
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("GnssCapabilities: ( ");
        if (this.hasLowPowerMode()) {
            stringBuilder.append("LOW_POWER_MODE ");
        }
        if (this.hasSatelliteBlacklist()) {
            stringBuilder.append("SATELLITE_BLACKLIST ");
        }
        if (this.hasGeofencing()) {
            stringBuilder.append("GEOFENCING ");
        }
        if (this.hasMeasurements()) {
            stringBuilder.append("MEASUREMENTS ");
        }
        if (this.hasNavMessages()) {
            stringBuilder.append("NAV_MESSAGES ");
        }
        if (this.hasMeasurementCorrections()) {
            stringBuilder.append("MEASUREMENT_CORRECTIONS ");
        }
        if (this.hasMeasurementCorrectionsLosSats()) {
            stringBuilder.append("MEASUREMENT_CORRECTIONS_LOS_SATS ");
        }
        if (this.hasMeasurementCorrectionsExcessPathLength()) {
            stringBuilder.append("MEASUREMENT_CORRECTIONS_EXCESS_PATH_LENGTH ");
        }
        if (this.hasMeasurementCorrectionsReflectingPane()) {
            stringBuilder.append("MEASUREMENT_CORRECTIONS_REFLECTING_PLANE ");
        }
        stringBuilder.append(")");
        return stringBuilder.toString();
    }
}

