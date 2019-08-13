/*
 * Decompiled with CFR 0.145.
 */
package android.telephony.cdma;

import android.annotation.UnsupportedAppUsage;
import android.os.Bundle;
import android.telephony.CellLocation;

public class CdmaCellLocation
extends CellLocation {
    public static final int INVALID_LAT_LONG = Integer.MAX_VALUE;
    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    private int mBaseStationId = -1;
    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    private int mBaseStationLatitude = Integer.MAX_VALUE;
    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    private int mBaseStationLongitude = Integer.MAX_VALUE;
    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    private int mNetworkId = -1;
    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    private int mSystemId = -1;

    public CdmaCellLocation() {
        this.mBaseStationId = -1;
        this.mBaseStationLatitude = Integer.MAX_VALUE;
        this.mBaseStationLongitude = Integer.MAX_VALUE;
        this.mSystemId = -1;
        this.mNetworkId = -1;
    }

    public CdmaCellLocation(Bundle bundle) {
        this.mBaseStationId = bundle.getInt("baseStationId", this.mBaseStationId);
        this.mBaseStationLatitude = bundle.getInt("baseStationLatitude", this.mBaseStationLatitude);
        this.mBaseStationLongitude = bundle.getInt("baseStationLongitude", this.mBaseStationLongitude);
        this.mSystemId = bundle.getInt("systemId", this.mSystemId);
        this.mNetworkId = bundle.getInt("networkId", this.mNetworkId);
    }

    public static double convertQuartSecToDecDegrees(int n) {
        if (!Double.isNaN(n) && n >= -2592000 && n <= 2592000) {
            return (double)n / 14400.0;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Invalid coordiante value:");
        stringBuilder.append(n);
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    private static boolean equalsHandlesNulls(Object object, Object object2) {
        boolean bl = object == null ? object2 == null : object.equals(object2);
        return bl;
    }

    public boolean equals(Object object) {
        CdmaCellLocation cdmaCellLocation;
        boolean bl;
        block3 : {
            bl = false;
            try {
                cdmaCellLocation = (CdmaCellLocation)object;
                if (object != null) break block3;
                return false;
            }
            catch (ClassCastException classCastException) {
                return false;
            }
        }
        if (CdmaCellLocation.equalsHandlesNulls(this.mBaseStationId, cdmaCellLocation.mBaseStationId) && CdmaCellLocation.equalsHandlesNulls(this.mBaseStationLatitude, cdmaCellLocation.mBaseStationLatitude) && CdmaCellLocation.equalsHandlesNulls(this.mBaseStationLongitude, cdmaCellLocation.mBaseStationLongitude) && CdmaCellLocation.equalsHandlesNulls(this.mSystemId, cdmaCellLocation.mSystemId) && CdmaCellLocation.equalsHandlesNulls(this.mNetworkId, cdmaCellLocation.mNetworkId)) {
            bl = true;
        }
        return bl;
    }

    @Override
    public void fillInNotifierBundle(Bundle bundle) {
        bundle.putInt("baseStationId", this.mBaseStationId);
        bundle.putInt("baseStationLatitude", this.mBaseStationLatitude);
        bundle.putInt("baseStationLongitude", this.mBaseStationLongitude);
        bundle.putInt("systemId", this.mSystemId);
        bundle.putInt("networkId", this.mNetworkId);
    }

    public int getBaseStationId() {
        return this.mBaseStationId;
    }

    public int getBaseStationLatitude() {
        return this.mBaseStationLatitude;
    }

    public int getBaseStationLongitude() {
        return this.mBaseStationLongitude;
    }

    public int getNetworkId() {
        return this.mNetworkId;
    }

    public int getSystemId() {
        return this.mSystemId;
    }

    public int hashCode() {
        return this.mBaseStationId ^ this.mBaseStationLatitude ^ this.mBaseStationLongitude ^ this.mSystemId ^ this.mNetworkId;
    }

    @Override
    public boolean isEmpty() {
        boolean bl = this.mBaseStationId == -1 && this.mBaseStationLatitude == Integer.MAX_VALUE && this.mBaseStationLongitude == Integer.MAX_VALUE && this.mSystemId == -1 && this.mNetworkId == -1;
        return bl;
    }

    public void setCellLocationData(int n, int n2, int n3) {
        this.mBaseStationId = n;
        this.mBaseStationLatitude = n2;
        this.mBaseStationLongitude = n3;
    }

    public void setCellLocationData(int n, int n2, int n3, int n4, int n5) {
        this.mBaseStationId = n;
        this.mBaseStationLatitude = n2;
        this.mBaseStationLongitude = n3;
        this.mSystemId = n4;
        this.mNetworkId = n5;
    }

    @Override
    public void setStateInvalid() {
        this.mBaseStationId = -1;
        this.mBaseStationLatitude = Integer.MAX_VALUE;
        this.mBaseStationLongitude = Integer.MAX_VALUE;
        this.mSystemId = -1;
        this.mNetworkId = -1;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[");
        stringBuilder.append(this.mBaseStationId);
        stringBuilder.append(",");
        stringBuilder.append(this.mBaseStationLatitude);
        stringBuilder.append(",");
        stringBuilder.append(this.mBaseStationLongitude);
        stringBuilder.append(",");
        stringBuilder.append(this.mSystemId);
        stringBuilder.append(",");
        stringBuilder.append(this.mNetworkId);
        stringBuilder.append("]");
        return stringBuilder.toString();
    }
}

