/*
 * Decompiled with CFR 0.145.
 */
package android.net.lowpan;

import android.os.Parcel;
import android.os.Parcelable;
import com.android.internal.util.HexDump;
import java.util.Arrays;
import java.util.Objects;

public class LowpanCredential
implements Parcelable {
    public static final Parcelable.Creator<LowpanCredential> CREATOR = new Parcelable.Creator<LowpanCredential>(){

        @Override
        public LowpanCredential createFromParcel(Parcel parcel) {
            LowpanCredential lowpanCredential = new LowpanCredential();
            lowpanCredential.mMasterKey = parcel.createByteArray();
            lowpanCredential.mMasterKeyIndex = parcel.readInt();
            return lowpanCredential;
        }

        public LowpanCredential[] newArray(int n) {
            return new LowpanCredential[n];
        }
    };
    public static final int UNSPECIFIED_KEY_INDEX = 0;
    private byte[] mMasterKey = null;
    private int mMasterKeyIndex = 0;

    LowpanCredential() {
    }

    private LowpanCredential(byte[] arrby) {
        this.setMasterKey(arrby);
    }

    private LowpanCredential(byte[] arrby, int n) {
        this.setMasterKey(arrby, n);
    }

    public static LowpanCredential createMasterKey(byte[] arrby) {
        return new LowpanCredential(arrby);
    }

    public static LowpanCredential createMasterKey(byte[] arrby, int n) {
        return new LowpanCredential(arrby, n);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public boolean equals(Object object) {
        boolean bl = object instanceof LowpanCredential;
        boolean bl2 = false;
        if (!bl) {
            return false;
        }
        object = (LowpanCredential)object;
        bl = bl2;
        if (Arrays.equals(this.mMasterKey, ((LowpanCredential)object).mMasterKey)) {
            bl = bl2;
            if (this.mMasterKeyIndex == ((LowpanCredential)object).mMasterKeyIndex) {
                bl = true;
            }
        }
        return bl;
    }

    public byte[] getMasterKey() {
        byte[] arrby = this.mMasterKey;
        if (arrby != null) {
            return (byte[])arrby.clone();
        }
        return null;
    }

    public int getMasterKeyIndex() {
        return this.mMasterKeyIndex;
    }

    public int hashCode() {
        return Objects.hash(Arrays.hashCode(this.mMasterKey), this.mMasterKeyIndex);
    }

    public boolean isMasterKey() {
        boolean bl = this.mMasterKey != null;
        return bl;
    }

    void setMasterKey(byte[] arrby) {
        byte[] arrby2 = arrby;
        if (arrby != null) {
            arrby2 = (byte[])arrby.clone();
        }
        this.mMasterKey = arrby2;
    }

    void setMasterKey(byte[] arrby, int n) {
        this.setMasterKey(arrby);
        this.setMasterKeyIndex(n);
    }

    void setMasterKeyIndex(int n) {
        this.mMasterKeyIndex = n;
    }

    public String toSensitiveString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("<LowpanCredential");
        if (this.isMasterKey()) {
            stringBuffer.append(" MasterKey:");
            stringBuffer.append(HexDump.toHexString(this.mMasterKey));
            if (this.mMasterKeyIndex != 0) {
                stringBuffer.append(", Index:");
                stringBuffer.append(this.mMasterKeyIndex);
            }
        } else {
            stringBuffer.append(" empty");
        }
        stringBuffer.append(">");
        return stringBuffer.toString();
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("<LowpanCredential");
        if (this.isMasterKey()) {
            stringBuffer.append(" MasterKey");
            if (this.mMasterKeyIndex != 0) {
                stringBuffer.append(", Index:");
                stringBuffer.append(this.mMasterKeyIndex);
            }
        } else {
            stringBuffer.append(" empty");
        }
        stringBuffer.append(">");
        return stringBuffer.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeByteArray(this.mMasterKey);
        parcel.writeInt(this.mMasterKeyIndex);
    }

}

