/*
 * Decompiled with CFR 0.145.
 */
package android.location;

import android.location.Criteria;
import com.android.internal.location.ProviderProperties;

public class LocationProvider {
    @Deprecated
    public static final int AVAILABLE = 2;
    public static final String BAD_CHARS_REGEX = "[^a-zA-Z0-9]";
    @Deprecated
    public static final int OUT_OF_SERVICE = 0;
    @Deprecated
    public static final int TEMPORARILY_UNAVAILABLE = 1;
    private final String mName;
    private final ProviderProperties mProperties;

    public LocationProvider(String string2, ProviderProperties object) {
        if (!string2.matches(BAD_CHARS_REGEX)) {
            this.mName = string2;
            this.mProperties = object;
            return;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("provider name contains illegal character: ");
        ((StringBuilder)object).append(string2);
        throw new IllegalArgumentException(((StringBuilder)object).toString());
    }

    public static boolean propertiesMeetCriteria(String string2, ProviderProperties providerProperties, Criteria criteria) {
        if ("passive".equals(string2)) {
            return false;
        }
        if (providerProperties == null) {
            return false;
        }
        if (criteria.getAccuracy() != 0 && criteria.getAccuracy() < providerProperties.mAccuracy) {
            return false;
        }
        if (criteria.getPowerRequirement() != 0 && criteria.getPowerRequirement() < providerProperties.mPowerRequirement) {
            return false;
        }
        if (criteria.isAltitudeRequired() && !providerProperties.mSupportsAltitude) {
            return false;
        }
        if (criteria.isSpeedRequired() && !providerProperties.mSupportsSpeed) {
            return false;
        }
        if (criteria.isBearingRequired() && !providerProperties.mSupportsBearing) {
            return false;
        }
        return criteria.isCostAllowed() || !providerProperties.mHasMonetaryCost;
    }

    public int getAccuracy() {
        return this.mProperties.mAccuracy;
    }

    public String getName() {
        return this.mName;
    }

    public int getPowerRequirement() {
        return this.mProperties.mPowerRequirement;
    }

    public boolean hasMonetaryCost() {
        return this.mProperties.mHasMonetaryCost;
    }

    public boolean meetsCriteria(Criteria criteria) {
        return LocationProvider.propertiesMeetCriteria(this.mName, this.mProperties, criteria);
    }

    public boolean requiresCell() {
        return this.mProperties.mRequiresCell;
    }

    public boolean requiresNetwork() {
        return this.mProperties.mRequiresNetwork;
    }

    public boolean requiresSatellite() {
        return this.mProperties.mRequiresSatellite;
    }

    public boolean supportsAltitude() {
        return this.mProperties.mSupportsAltitude;
    }

    public boolean supportsBearing() {
        return this.mProperties.mSupportsBearing;
    }

    public boolean supportsSpeed() {
        return this.mProperties.mSupportsSpeed;
    }
}

