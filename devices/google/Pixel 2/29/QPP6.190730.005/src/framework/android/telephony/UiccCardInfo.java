/*
 * Decompiled with CFR 0.145.
 */
package android.telephony;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.Objects;

public final class UiccCardInfo
implements Parcelable {
    public static final Parcelable.Creator<UiccCardInfo> CREATOR = new Parcelable.Creator<UiccCardInfo>(){

        @Override
        public UiccCardInfo createFromParcel(Parcel parcel) {
            return new UiccCardInfo(parcel);
        }

        public UiccCardInfo[] newArray(int n) {
            return new UiccCardInfo[n];
        }
    };
    private final int mCardId;
    private final String mEid;
    private final String mIccId;
    private final boolean mIsEuicc;
    private final boolean mIsRemovable;
    private final int mSlotIndex;

    private UiccCardInfo(Parcel parcel) {
        byte by = parcel.readByte();
        boolean bl = true;
        boolean bl2 = by != 0;
        this.mIsEuicc = bl2;
        this.mCardId = parcel.readInt();
        this.mEid = parcel.readString();
        this.mIccId = parcel.readString();
        this.mSlotIndex = parcel.readInt();
        bl2 = parcel.readByte() != 0 ? bl : false;
        this.mIsRemovable = bl2;
    }

    public UiccCardInfo(boolean bl, int n, String string2, String string3, int n2, boolean bl2) {
        this.mIsEuicc = bl;
        this.mCardId = n;
        this.mEid = string2;
        this.mIccId = string3;
        this.mSlotIndex = n2;
        this.mIsRemovable = bl2;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public boolean equals(Object object) {
        boolean bl = true;
        if (this == object) {
            return true;
        }
        if (object != null && this.getClass() == object.getClass()) {
            object = (UiccCardInfo)object;
            if (this.mIsEuicc != ((UiccCardInfo)object).mIsEuicc || this.mCardId != ((UiccCardInfo)object).mCardId || !Objects.equals(this.mEid, ((UiccCardInfo)object).mEid) || !Objects.equals(this.mIccId, ((UiccCardInfo)object).mIccId) || this.mSlotIndex != ((UiccCardInfo)object).mSlotIndex || this.mIsRemovable != ((UiccCardInfo)object).mIsRemovable) {
                bl = false;
            }
            return bl;
        }
        return false;
    }

    public int getCardId() {
        return this.mCardId;
    }

    public String getEid() {
        if (!this.mIsEuicc) {
            return null;
        }
        return this.mEid;
    }

    public String getIccId() {
        return this.mIccId;
    }

    public int getSlotIndex() {
        return this.mSlotIndex;
    }

    public UiccCardInfo getUnprivileged() {
        return new UiccCardInfo(this.mIsEuicc, this.mCardId, null, null, this.mSlotIndex, this.mIsRemovable);
    }

    public int hashCode() {
        return Objects.hash(this.mIsEuicc, this.mCardId, this.mEid, this.mIccId, this.mSlotIndex, this.mIsRemovable);
    }

    public boolean isEuicc() {
        return this.mIsEuicc;
    }

    public boolean isRemovable() {
        return this.mIsRemovable;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("UiccCardInfo (mIsEuicc=");
        stringBuilder.append(this.mIsEuicc);
        stringBuilder.append(", mCardId=");
        stringBuilder.append(this.mCardId);
        stringBuilder.append(", mEid=");
        stringBuilder.append(this.mEid);
        stringBuilder.append(", mIccId=");
        stringBuilder.append(this.mIccId);
        stringBuilder.append(", mSlotIndex=");
        stringBuilder.append(this.mSlotIndex);
        stringBuilder.append(", mIsRemovable=");
        stringBuilder.append(this.mIsRemovable);
        stringBuilder.append(")");
        return stringBuilder.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeByte((byte)(this.mIsEuicc ? 1 : 0));
        parcel.writeInt(this.mCardId);
        parcel.writeString(this.mEid);
        parcel.writeString(this.mIccId);
        parcel.writeInt(this.mSlotIndex);
        parcel.writeByte((byte)(this.mIsRemovable ? 1 : 0));
    }

}

