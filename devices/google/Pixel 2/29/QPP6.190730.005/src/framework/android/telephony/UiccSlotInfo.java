/*
 * Decompiled with CFR 0.145.
 */
package android.telephony;

import android.annotation.SystemApi;
import android.os.Parcel;
import android.os.Parcelable;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Objects;

@SystemApi
public class UiccSlotInfo
implements Parcelable {
    public static final int CARD_STATE_INFO_ABSENT = 1;
    public static final int CARD_STATE_INFO_ERROR = 3;
    public static final int CARD_STATE_INFO_PRESENT = 2;
    public static final int CARD_STATE_INFO_RESTRICTED = 4;
    public static final Parcelable.Creator<UiccSlotInfo> CREATOR = new Parcelable.Creator<UiccSlotInfo>(){

        @Override
        public UiccSlotInfo createFromParcel(Parcel parcel) {
            return new UiccSlotInfo(parcel);
        }

        public UiccSlotInfo[] newArray(int n) {
            return new UiccSlotInfo[n];
        }
    };
    private final String mCardId;
    private final int mCardStateInfo;
    private final boolean mIsActive;
    private final boolean mIsEuicc;
    private final boolean mIsExtendedApduSupported;
    private final boolean mIsRemovable;
    private final int mLogicalSlotIdx;

    private UiccSlotInfo(Parcel parcel) {
        byte by = parcel.readByte();
        boolean bl = true;
        boolean bl2 = by != 0;
        this.mIsActive = bl2;
        bl2 = parcel.readByte() != 0;
        this.mIsEuicc = bl2;
        this.mCardId = parcel.readString();
        this.mCardStateInfo = parcel.readInt();
        this.mLogicalSlotIdx = parcel.readInt();
        bl2 = parcel.readByte() != 0;
        this.mIsExtendedApduSupported = bl2;
        bl2 = parcel.readByte() != 0 ? bl : false;
        this.mIsRemovable = bl2;
    }

    @Deprecated
    public UiccSlotInfo(boolean bl, boolean bl2, String string2, int n, int n2, boolean bl3) {
        this.mIsActive = bl;
        this.mIsEuicc = bl2;
        this.mCardId = string2;
        this.mCardStateInfo = n;
        this.mLogicalSlotIdx = n2;
        this.mIsExtendedApduSupported = bl3;
        this.mIsRemovable = false;
    }

    public UiccSlotInfo(boolean bl, boolean bl2, String string2, int n, int n2, boolean bl3, boolean bl4) {
        this.mIsActive = bl;
        this.mIsEuicc = bl2;
        this.mCardId = string2;
        this.mCardStateInfo = n;
        this.mLogicalSlotIdx = n2;
        this.mIsExtendedApduSupported = bl3;
        this.mIsRemovable = bl4;
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
            object = (UiccSlotInfo)object;
            if (this.mIsActive != ((UiccSlotInfo)object).mIsActive || this.mIsEuicc != ((UiccSlotInfo)object).mIsEuicc || !Objects.equals(this.mCardId, ((UiccSlotInfo)object).mCardId) || this.mCardStateInfo != ((UiccSlotInfo)object).mCardStateInfo || this.mLogicalSlotIdx != ((UiccSlotInfo)object).mLogicalSlotIdx || this.mIsExtendedApduSupported != ((UiccSlotInfo)object).mIsExtendedApduSupported || this.mIsRemovable != ((UiccSlotInfo)object).mIsRemovable) {
                bl = false;
            }
            return bl;
        }
        return false;
    }

    public String getCardId() {
        return this.mCardId;
    }

    public int getCardStateInfo() {
        return this.mCardStateInfo;
    }

    public boolean getIsActive() {
        return this.mIsActive;
    }

    public boolean getIsEuicc() {
        return this.mIsEuicc;
    }

    public boolean getIsExtendedApduSupported() {
        return this.mIsExtendedApduSupported;
    }

    public int getLogicalSlotIdx() {
        return this.mLogicalSlotIdx;
    }

    public int hashCode() {
        return ((((((1 * 31 + this.mIsActive) * 31 + this.mIsEuicc) * 31 + Objects.hashCode(this.mCardId)) * 31 + this.mCardStateInfo) * 31 + this.mLogicalSlotIdx) * 31 + this.mIsExtendedApduSupported) * 31 + this.mIsRemovable;
    }

    public boolean isRemovable() {
        return this.mIsRemovable;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("UiccSlotInfo (mIsActive=");
        stringBuilder.append(this.mIsActive);
        stringBuilder.append(", mIsEuicc=");
        stringBuilder.append(this.mIsEuicc);
        stringBuilder.append(", mCardId=");
        stringBuilder.append(this.mCardId);
        stringBuilder.append(", cardState=");
        stringBuilder.append(this.mCardStateInfo);
        stringBuilder.append(", phoneId=");
        stringBuilder.append(this.mLogicalSlotIdx);
        stringBuilder.append(", mIsExtendedApduSupported=");
        stringBuilder.append(this.mIsExtendedApduSupported);
        stringBuilder.append(", mIsRemovable=");
        stringBuilder.append(this.mIsRemovable);
        stringBuilder.append(")");
        return stringBuilder.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeByte((byte)(this.mIsActive ? 1 : 0));
        parcel.writeByte((byte)(this.mIsEuicc ? 1 : 0));
        parcel.writeString(this.mCardId);
        parcel.writeInt(this.mCardStateInfo);
        parcel.writeInt(this.mLogicalSlotIdx);
        parcel.writeByte((byte)(this.mIsExtendedApduSupported ? 1 : 0));
        parcel.writeByte((byte)(this.mIsRemovable ? 1 : 0));
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface CardStateInfo {
    }

}

