/*
 * Decompiled with CFR 0.145.
 */
package android.telephony;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.Objects;

public class CellConfigLte
implements Parcelable {
    public static final Parcelable.Creator<CellConfigLte> CREATOR = new Parcelable.Creator<CellConfigLte>(){

        @Override
        public CellConfigLte createFromParcel(Parcel parcel) {
            return new CellConfigLte(parcel);
        }

        public CellConfigLte[] newArray(int n) {
            return new CellConfigLte[0];
        }
    };
    private final boolean mIsEndcAvailable;

    public CellConfigLte() {
        this.mIsEndcAvailable = false;
    }

    public CellConfigLte(android.hardware.radio.V1_4.CellConfigLte cellConfigLte) {
        this.mIsEndcAvailable = cellConfigLte.isEndcAvailable;
    }

    private CellConfigLte(Parcel parcel) {
        this.mIsEndcAvailable = parcel.readBoolean();
    }

    public CellConfigLte(CellConfigLte cellConfigLte) {
        this.mIsEndcAvailable = cellConfigLte.mIsEndcAvailable;
    }

    public CellConfigLte(boolean bl) {
        this.mIsEndcAvailable = bl;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public boolean equals(Object object) {
        boolean bl = object instanceof CellConfigLte;
        boolean bl2 = false;
        if (!bl) {
            return false;
        }
        object = (CellConfigLte)object;
        if (this.mIsEndcAvailable == ((CellConfigLte)object).mIsEndcAvailable) {
            bl2 = true;
        }
        return bl2;
    }

    public int hashCode() {
        return Objects.hash(this.mIsEndcAvailable);
    }

    boolean isEndcAvailable() {
        return this.mIsEndcAvailable;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.getClass().getName());
        stringBuilder.append(" :{");
        StringBuilder stringBuilder2 = new StringBuilder();
        stringBuilder2.append(" isEndcAvailable = ");
        stringBuilder2.append(this.mIsEndcAvailable);
        stringBuilder.append(stringBuilder2.toString());
        stringBuilder.append(" }");
        return stringBuilder.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeBoolean(this.mIsEndcAvailable);
    }

}

