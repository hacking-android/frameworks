/*
 * Decompiled with CFR 0.145.
 */
package android.telephony;

import android.annotation.UnsupportedAppUsage;
import android.hardware.radio.V1_4.CellInfo;
import android.os.Parcel;
import android.os.Parcelable;
import android.telephony.CellIdentity;
import android.telephony.CellInfoCdma;
import android.telephony.CellInfoGsm;
import android.telephony.CellInfoLte;
import android.telephony.CellInfoNr;
import android.telephony.CellInfoTdscdma;
import android.telephony.CellInfoWcdma;
import android.telephony.CellSignalStrength;
import com.android.internal.annotations.VisibleForTesting;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public abstract class CellInfo
implements Parcelable {
    public static final int CONNECTION_NONE = 0;
    public static final int CONNECTION_PRIMARY_SERVING = 1;
    public static final int CONNECTION_SECONDARY_SERVING = 2;
    public static final int CONNECTION_UNKNOWN = Integer.MAX_VALUE;
    public static final Parcelable.Creator<CellInfo> CREATOR = new Parcelable.Creator<CellInfo>(){

        @Override
        public CellInfo createFromParcel(Parcel parcel) {
            switch (parcel.readInt()) {
                default: {
                    throw new RuntimeException("Bad CellInfo Parcel");
                }
                case 6: {
                    return CellInfoNr.createFromParcelBody(parcel);
                }
                case 5: {
                    return CellInfoTdscdma.createFromParcelBody(parcel);
                }
                case 4: {
                    return CellInfoWcdma.createFromParcelBody(parcel);
                }
                case 3: {
                    return CellInfoLte.createFromParcelBody(parcel);
                }
                case 2: {
                    return CellInfoCdma.createFromParcelBody(parcel);
                }
                case 1: 
            }
            return CellInfoGsm.createFromParcelBody(parcel);
        }

        public CellInfo[] newArray(int n) {
            return new CellInfo[n];
        }
    };
    @UnsupportedAppUsage
    public static final int TIMESTAMP_TYPE_ANTENNA = 1;
    @UnsupportedAppUsage
    public static final int TIMESTAMP_TYPE_JAVA_RIL = 4;
    @UnsupportedAppUsage
    public static final int TIMESTAMP_TYPE_MODEM = 2;
    @UnsupportedAppUsage
    public static final int TIMESTAMP_TYPE_OEM_RIL = 3;
    @UnsupportedAppUsage
    public static final int TIMESTAMP_TYPE_UNKNOWN = 0;
    public static final int TYPE_CDMA = 2;
    public static final int TYPE_GSM = 1;
    public static final int TYPE_LTE = 3;
    public static final int TYPE_NR = 6;
    public static final int TYPE_TDSCDMA = 5;
    public static final int TYPE_UNKNOWN = 0;
    public static final int TYPE_WCDMA = 4;
    public static final int UNAVAILABLE = Integer.MAX_VALUE;
    public static final long UNAVAILABLE_LONG = Long.MAX_VALUE;
    private int mCellConnectionStatus;
    private boolean mRegistered;
    private long mTimeStamp;

    protected CellInfo() {
        this.mRegistered = false;
        this.mTimeStamp = Long.MAX_VALUE;
        this.mCellConnectionStatus = 0;
    }

    protected CellInfo(android.hardware.radio.V1_0.CellInfo cellInfo) {
        this.mRegistered = cellInfo.registered;
        this.mTimeStamp = cellInfo.timeStamp;
        this.mCellConnectionStatus = Integer.MAX_VALUE;
    }

    protected CellInfo(android.hardware.radio.V1_2.CellInfo cellInfo) {
        this.mRegistered = cellInfo.registered;
        this.mTimeStamp = cellInfo.timeStamp;
        this.mCellConnectionStatus = cellInfo.connectionStatus;
    }

    protected CellInfo(android.hardware.radio.V1_4.CellInfo cellInfo, long l) {
        this.mRegistered = cellInfo.isRegistered;
        this.mTimeStamp = l;
        this.mCellConnectionStatus = cellInfo.connectionStatus;
    }

    protected CellInfo(Parcel parcel) {
        int n = parcel.readInt();
        boolean bl = true;
        if (n != 1) {
            bl = false;
        }
        this.mRegistered = bl;
        this.mTimeStamp = parcel.readLong();
        this.mCellConnectionStatus = parcel.readInt();
    }

    protected CellInfo(CellInfo cellInfo) {
        this.mRegistered = cellInfo.mRegistered;
        this.mTimeStamp = cellInfo.mTimeStamp;
        this.mCellConnectionStatus = cellInfo.mCellConnectionStatus;
    }

    public static CellInfo create(android.hardware.radio.V1_0.CellInfo cellInfo) {
        if (cellInfo == null) {
            return null;
        }
        int n = cellInfo.cellInfoType;
        if (n != 1) {
            if (n != 2) {
                if (n != 3) {
                    if (n != 4) {
                        if (n != 5) {
                            return null;
                        }
                        return new CellInfoTdscdma(cellInfo);
                    }
                    return new CellInfoWcdma(cellInfo);
                }
                return new CellInfoLte(cellInfo);
            }
            return new CellInfoCdma(cellInfo);
        }
        return new CellInfoGsm(cellInfo);
    }

    public static CellInfo create(android.hardware.radio.V1_2.CellInfo cellInfo) {
        if (cellInfo == null) {
            return null;
        }
        int n = cellInfo.cellInfoType;
        if (n != 1) {
            if (n != 2) {
                if (n != 3) {
                    if (n != 4) {
                        if (n != 5) {
                            return null;
                        }
                        return new CellInfoTdscdma(cellInfo);
                    }
                    return new CellInfoWcdma(cellInfo);
                }
                return new CellInfoLte(cellInfo);
            }
            return new CellInfoCdma(cellInfo);
        }
        return new CellInfoGsm(cellInfo);
    }

    public static CellInfo create(android.hardware.radio.V1_4.CellInfo cellInfo, long l) {
        if (cellInfo == null) {
            return null;
        }
        byte by = cellInfo.info.getDiscriminator();
        if (by != 0) {
            if (by != 1) {
                if (by != 2) {
                    if (by != 3) {
                        if (by != 4) {
                            return null;
                        }
                        return new CellInfoLte(cellInfo, l);
                    }
                    return new CellInfoTdscdma(cellInfo, l);
                }
                return new CellInfoWcdma(cellInfo, l);
            }
            return new CellInfoCdma(cellInfo, l);
        }
        return new CellInfoGsm(cellInfo, l);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public boolean equals(Object object) {
        boolean bl;
        block6 : {
            boolean bl2 = false;
            if (object == null) {
                return false;
            }
            if (this == object) {
                return true;
            }
            try {
                object = (CellInfo)object;
                bl = bl2;
            }
            catch (ClassCastException classCastException) {
                return false;
            }
            if (this.mRegistered != ((CellInfo)object).mRegistered) break block6;
            bl = bl2;
            if (this.mTimeStamp != ((CellInfo)object).mTimeStamp) break block6;
            int n = this.mCellConnectionStatus;
            int n2 = ((CellInfo)object).mCellConnectionStatus;
            bl = bl2;
            if (n != n2) break block6;
            bl = true;
        }
        return bl;
    }

    public int getCellConnectionStatus() {
        return this.mCellConnectionStatus;
    }

    public abstract CellIdentity getCellIdentity();

    public abstract CellSignalStrength getCellSignalStrength();

    public long getTimeStamp() {
        return this.mTimeStamp;
    }

    public int hashCode() {
        return (this.mRegistered ^ true) * 31 + (int)(this.mTimeStamp / 1000L) * 31 + this.mCellConnectionStatus * 31;
    }

    public boolean isRegistered() {
        return this.mRegistered;
    }

    public CellInfo sanitizeLocationInfo() {
        return null;
    }

    public void setCellConnectionStatus(int n) {
        this.mCellConnectionStatus = n;
    }

    public void setRegistered(boolean bl) {
        this.mRegistered = bl;
    }

    @VisibleForTesting
    public void setTimeStamp(long l) {
        this.mTimeStamp = l;
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("mRegistered=");
        String string2 = this.mRegistered ? "YES" : "NO";
        stringBuffer.append(string2);
        stringBuffer.append(" mTimeStamp=");
        stringBuffer.append(this.mTimeStamp);
        stringBuffer.append("ns");
        stringBuffer.append(" mCellConnectionStatus=");
        stringBuffer.append(this.mCellConnectionStatus);
        return stringBuffer.toString();
    }

    @Override
    public abstract void writeToParcel(Parcel var1, int var2);

    protected void writeToParcel(Parcel parcel, int n, int n2) {
        parcel.writeInt(n2);
        parcel.writeInt((int)this.mRegistered);
        parcel.writeLong(this.mTimeStamp);
        parcel.writeInt(this.mCellConnectionStatus);
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface CellConnectionStatus {
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface Type {
    }

}

