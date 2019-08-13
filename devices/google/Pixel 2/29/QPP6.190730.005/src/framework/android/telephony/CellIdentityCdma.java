/*
 * Decompiled with CFR 0.145.
 */
package android.telephony;

import android.hardware.radio.V1_2.CellIdentityOperatorNames;
import android.os.Parcel;
import android.os.Parcelable;
import android.telephony.CellIdentity;
import android.telephony.CellLocation;
import android.telephony.cdma.CdmaCellLocation;
import java.util.Objects;

public final class CellIdentityCdma
extends CellIdentity {
    private static final int BASESTATION_ID_MAX = 65535;
    public static final Parcelable.Creator<CellIdentityCdma> CREATOR;
    private static final boolean DBG = false;
    private static final int LATITUDE_MAX = 1296000;
    private static final int LATITUDE_MIN = -1296000;
    private static final int LONGITUDE_MAX = 2592000;
    private static final int LONGITUDE_MIN = -2592000;
    private static final int NETWORK_ID_MAX = 65535;
    private static final int SYSTEM_ID_MAX = 32767;
    private static final String TAG;
    private final int mBasestationId;
    private final int mLatitude;
    private final int mLongitude;
    private final int mNetworkId;
    private final int mSystemId;

    static {
        TAG = CellIdentityCdma.class.getSimpleName();
        CREATOR = new Parcelable.Creator<CellIdentityCdma>(){

            @Override
            public CellIdentityCdma createFromParcel(Parcel parcel) {
                parcel.readInt();
                return CellIdentityCdma.createFromParcelBody(parcel);
            }

            public CellIdentityCdma[] newArray(int n) {
                return new CellIdentityCdma[n];
            }
        };
    }

    public CellIdentityCdma() {
        super(TAG, 2, null, null, null, null);
        this.mNetworkId = Integer.MAX_VALUE;
        this.mSystemId = Integer.MAX_VALUE;
        this.mBasestationId = Integer.MAX_VALUE;
        this.mLongitude = Integer.MAX_VALUE;
        this.mLatitude = Integer.MAX_VALUE;
    }

    public CellIdentityCdma(int n, int n2, int n3, int n4, int n5, String string2, String string3) {
        super(TAG, 2, null, null, string2, string3);
        this.mNetworkId = CellIdentityCdma.inRangeOrUnavailable(n, 0, 65535);
        this.mSystemId = CellIdentityCdma.inRangeOrUnavailable(n2, 0, 32767);
        this.mBasestationId = CellIdentityCdma.inRangeOrUnavailable(n3, 0, 65535);
        n = CellIdentityCdma.inRangeOrUnavailable(n5, -1296000, 1296000);
        n2 = CellIdentityCdma.inRangeOrUnavailable(n4, -2592000, 2592000);
        if (!this.isNullIsland(n, n2)) {
            this.mLongitude = n2;
            this.mLatitude = n;
        } else {
            this.mLatitude = Integer.MAX_VALUE;
            this.mLongitude = Integer.MAX_VALUE;
        }
    }

    public CellIdentityCdma(android.hardware.radio.V1_0.CellIdentityCdma cellIdentityCdma) {
        this(cellIdentityCdma.networkId, cellIdentityCdma.systemId, cellIdentityCdma.baseStationId, cellIdentityCdma.longitude, cellIdentityCdma.latitude, "", "");
    }

    public CellIdentityCdma(android.hardware.radio.V1_2.CellIdentityCdma cellIdentityCdma) {
        this(cellIdentityCdma.base.networkId, cellIdentityCdma.base.systemId, cellIdentityCdma.base.baseStationId, cellIdentityCdma.base.longitude, cellIdentityCdma.base.latitude, cellIdentityCdma.operatorNames.alphaLong, cellIdentityCdma.operatorNames.alphaShort);
    }

    private CellIdentityCdma(Parcel parcel) {
        super(TAG, 2, parcel);
        this.mNetworkId = parcel.readInt();
        this.mSystemId = parcel.readInt();
        this.mBasestationId = parcel.readInt();
        this.mLongitude = parcel.readInt();
        this.mLatitude = parcel.readInt();
    }

    private CellIdentityCdma(CellIdentityCdma cellIdentityCdma) {
        this(cellIdentityCdma.mNetworkId, cellIdentityCdma.mSystemId, cellIdentityCdma.mBasestationId, cellIdentityCdma.mLongitude, cellIdentityCdma.mLatitude, cellIdentityCdma.mAlphaLong, cellIdentityCdma.mAlphaShort);
    }

    protected static CellIdentityCdma createFromParcelBody(Parcel parcel) {
        return new CellIdentityCdma(parcel);
    }

    private boolean isNullIsland(int n, int n2) {
        n = Math.abs(n);
        boolean bl = true;
        if (n > 1 || Math.abs(n2) > 1) {
            bl = false;
        }
        return bl;
    }

    @Override
    public CdmaCellLocation asCellLocation() {
        CdmaCellLocation cdmaCellLocation = new CdmaCellLocation();
        int n = this.mBasestationId;
        if (n == Integer.MAX_VALUE) {
            n = -1;
        }
        int n2 = this.mSystemId;
        if (n2 == Integer.MAX_VALUE) {
            n2 = -1;
        }
        int n3 = this.mNetworkId;
        if (n3 == Integer.MAX_VALUE) {
            n3 = -1;
        }
        cdmaCellLocation.setCellLocationData(n, this.mLatitude, this.mLongitude, n2, n3);
        return cdmaCellLocation;
    }

    CellIdentityCdma copy() {
        return new CellIdentityCdma(this);
    }

    @Override
    public boolean equals(Object object) {
        boolean bl = true;
        if (this == object) {
            return true;
        }
        if (!(object instanceof CellIdentityCdma)) {
            return false;
        }
        CellIdentityCdma cellIdentityCdma = (CellIdentityCdma)object;
        if (this.mNetworkId != cellIdentityCdma.mNetworkId || this.mSystemId != cellIdentityCdma.mSystemId || this.mBasestationId != cellIdentityCdma.mBasestationId || this.mLatitude != cellIdentityCdma.mLatitude || this.mLongitude != cellIdentityCdma.mLongitude || !super.equals(object)) {
            bl = false;
        }
        return bl;
    }

    public int getBasestationId() {
        return this.mBasestationId;
    }

    public int getLatitude() {
        return this.mLatitude;
    }

    public int getLongitude() {
        return this.mLongitude;
    }

    public int getNetworkId() {
        return this.mNetworkId;
    }

    public int getSystemId() {
        return this.mSystemId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.mNetworkId, this.mSystemId, this.mBasestationId, this.mLatitude, this.mLongitude, super.hashCode());
    }

    public CellIdentityCdma sanitizeLocationInfo() {
        return new CellIdentityCdma(Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, this.mAlphaLong, this.mAlphaShort);
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder(TAG);
        stringBuilder.append(":{ mNetworkId=");
        stringBuilder.append(this.mNetworkId);
        stringBuilder.append(" mSystemId=");
        stringBuilder.append(this.mSystemId);
        stringBuilder.append(" mBasestationId=");
        stringBuilder.append(this.mBasestationId);
        stringBuilder.append(" mLongitude=");
        stringBuilder.append(this.mLongitude);
        stringBuilder.append(" mLatitude=");
        stringBuilder.append(this.mLatitude);
        stringBuilder.append(" mAlphaLong=");
        stringBuilder.append(this.mAlphaLong);
        stringBuilder.append(" mAlphaShort=");
        stringBuilder.append(this.mAlphaShort);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        super.writeToParcel(parcel, 2);
        parcel.writeInt(this.mNetworkId);
        parcel.writeInt(this.mSystemId);
        parcel.writeInt(this.mBasestationId);
        parcel.writeInt(this.mLongitude);
        parcel.writeInt(this.mLatitude);
    }

}

