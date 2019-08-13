/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 *  android.telephony.Rlog
 *  com.android.internal.telephony.uicc.IccUtils
 */
package com.android.internal.telephony.uicc;

import android.os.Parcel;
import android.os.Parcelable;
import android.telephony.Rlog;
import com.android.internal.telephony.uicc.IccUtils;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Arrays;
import java.util.Objects;

public class PlmnActRecord
implements Parcelable {
    public static final int ACCESS_TECH_CDMA2000_1XRTT = 16;
    public static final int ACCESS_TECH_CDMA2000_HRPD = 32;
    public static final int ACCESS_TECH_EUTRAN = 16384;
    public static final int ACCESS_TECH_GSM = 128;
    public static final int ACCESS_TECH_GSM_COMPACT = 64;
    public static final int ACCESS_TECH_RESERVED = 16143;
    public static final int ACCESS_TECH_UTRAN = 32768;
    public static final Parcelable.Creator<PlmnActRecord> CREATOR = new Parcelable.Creator<PlmnActRecord>(){

        public PlmnActRecord createFromParcel(Parcel parcel) {
            return new PlmnActRecord(parcel.readString(), parcel.readInt());
        }

        public PlmnActRecord[] newArray(int n) {
            return new PlmnActRecord[n];
        }
    };
    public static final int ENCODED_LENGTH = 5;
    private static final String LOG_TAG = "PlmnActRecord";
    private static final boolean VDBG = false;
    public final int accessTechs;
    public final String plmn;

    public PlmnActRecord(String string, int n) {
        this.plmn = string;
        this.accessTechs = n;
    }

    public PlmnActRecord(byte[] arrby, int n) {
        this.plmn = IccUtils.bcdPlmnToString((byte[])arrby, (int)n);
        this.accessTechs = Byte.toUnsignedInt(arrby[n + 3]) << 8 | Byte.toUnsignedInt(arrby[n + 4]);
    }

    private String accessTechString() {
        int n;
        if (this.accessTechs == 0) {
            return "NONE";
        }
        StringBuilder stringBuilder = new StringBuilder();
        if ((this.accessTechs & 32768) != 0) {
            stringBuilder.append("UTRAN|");
        }
        if ((this.accessTechs & 16384) != 0) {
            stringBuilder.append("EUTRAN|");
        }
        if ((this.accessTechs & 128) != 0) {
            stringBuilder.append("GSM|");
        }
        if ((this.accessTechs & 64) != 0) {
            stringBuilder.append("GSM_COMPACT|");
        }
        if ((this.accessTechs & 32) != 0) {
            stringBuilder.append("CDMA2000_HRPD|");
        }
        if ((this.accessTechs & 16) != 0) {
            stringBuilder.append("CDMA2000_1XRTT|");
        }
        if (((n = this.accessTechs) & 16143) != 0) {
            stringBuilder.append(String.format("UNKNOWN:%x|", n & 16143));
        }
        return stringBuilder.substring(0, stringBuilder.length() - 1);
    }

    public static PlmnActRecord[] getRecords(byte[] object) {
        if (object != null && ((byte[])object).length != 0 && ((byte[])object).length % 5 == 0) {
            int n = ((byte[])object).length / 5;
            PlmnActRecord[] arrplmnActRecord = new PlmnActRecord[n];
            for (int i = 0; i < n; ++i) {
                arrplmnActRecord[i] = new PlmnActRecord((byte[])object, i * 5);
            }
            return arrplmnActRecord;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Malformed PlmnActRecord, bytes: ");
        object = object != null ? Arrays.toString(object) : null;
        stringBuilder.append((String)object);
        Rlog.e((String)LOG_TAG, (String)stringBuilder.toString());
        return null;
    }

    public int describeContents() {
        return 0;
    }

    public boolean equals(Object object) {
        boolean bl = object instanceof PlmnActRecord;
        boolean bl2 = false;
        if (!bl) {
            return false;
        }
        object = (PlmnActRecord)object;
        bl = bl2;
        if (this.plmn.equals(((PlmnActRecord)object).plmn)) {
            bl = bl2;
            if (this.accessTechs == ((PlmnActRecord)object).accessTechs) {
                bl = true;
            }
        }
        return bl;
    }

    public byte[] getBytes() {
        byte[] arrby = new byte[5];
        IccUtils.stringToBcdPlmn((String)this.plmn, (byte[])arrby, (int)0);
        int n = this.accessTechs;
        arrby[3] = (byte)(n >> 8);
        arrby[4] = (byte)n;
        return arrby;
    }

    public int hashCode() {
        return Objects.hash(this.plmn, this.accessTechs);
    }

    public String toString() {
        return String.format("{PLMN=%s,AccessTechs=%s}", this.plmn, this.accessTechString());
    }

    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeString(this.plmn);
        parcel.writeInt(this.accessTechs);
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface AccessTech {
    }

}

