/*
 * Decompiled with CFR 0.145.
 */
package android.telephony;

import android.annotation.SystemApi;
import android.os.Parcel;
import android.os.Parcelable;
import android.telephony.LteVopsSupportInfo;
import java.util.Objects;

@SystemApi
public final class DataSpecificRegistrationInfo
implements Parcelable {
    public static final Parcelable.Creator<DataSpecificRegistrationInfo> CREATOR = new Parcelable.Creator<DataSpecificRegistrationInfo>(){

        @Override
        public DataSpecificRegistrationInfo createFromParcel(Parcel parcel) {
            return new DataSpecificRegistrationInfo(parcel);
        }

        public DataSpecificRegistrationInfo[] newArray(int n) {
            return new DataSpecificRegistrationInfo[n];
        }
    };
    public final boolean isDcNrRestricted;
    public final boolean isEnDcAvailable;
    public final boolean isNrAvailable;
    public boolean mIsUsingCarrierAggregation;
    private final LteVopsSupportInfo mLteVopsSupportInfo;
    public final int maxDataCalls;

    DataSpecificRegistrationInfo(int n, boolean bl, boolean bl2, boolean bl3, LteVopsSupportInfo lteVopsSupportInfo, boolean bl4) {
        this.maxDataCalls = n;
        this.isDcNrRestricted = bl;
        this.isNrAvailable = bl2;
        this.isEnDcAvailable = bl3;
        this.mLteVopsSupportInfo = lteVopsSupportInfo;
        this.mIsUsingCarrierAggregation = bl4;
    }

    private DataSpecificRegistrationInfo(Parcel parcel) {
        this.maxDataCalls = parcel.readInt();
        this.isDcNrRestricted = parcel.readBoolean();
        this.isNrAvailable = parcel.readBoolean();
        this.isEnDcAvailable = parcel.readBoolean();
        this.mLteVopsSupportInfo = LteVopsSupportInfo.CREATOR.createFromParcel(parcel);
        this.mIsUsingCarrierAggregation = parcel.readBoolean();
    }

    DataSpecificRegistrationInfo(DataSpecificRegistrationInfo dataSpecificRegistrationInfo) {
        this.maxDataCalls = dataSpecificRegistrationInfo.maxDataCalls;
        this.isDcNrRestricted = dataSpecificRegistrationInfo.isDcNrRestricted;
        this.isNrAvailable = dataSpecificRegistrationInfo.isNrAvailable;
        this.isEnDcAvailable = dataSpecificRegistrationInfo.isEnDcAvailable;
        this.mLteVopsSupportInfo = dataSpecificRegistrationInfo.mLteVopsSupportInfo;
        this.mIsUsingCarrierAggregation = dataSpecificRegistrationInfo.mIsUsingCarrierAggregation;
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
        if (!(object instanceof DataSpecificRegistrationInfo)) {
            return false;
        }
        object = (DataSpecificRegistrationInfo)object;
        if (this.maxDataCalls != ((DataSpecificRegistrationInfo)object).maxDataCalls || this.isDcNrRestricted != ((DataSpecificRegistrationInfo)object).isDcNrRestricted || this.isNrAvailable != ((DataSpecificRegistrationInfo)object).isNrAvailable || this.isEnDcAvailable != ((DataSpecificRegistrationInfo)object).isEnDcAvailable || !this.mLteVopsSupportInfo.equals(((DataSpecificRegistrationInfo)object).mLteVopsSupportInfo) || this.mIsUsingCarrierAggregation != ((DataSpecificRegistrationInfo)object).mIsUsingCarrierAggregation) {
            bl = false;
        }
        return bl;
    }

    public LteVopsSupportInfo getLteVopsSupportInfo() {
        return this.mLteVopsSupportInfo;
    }

    public int hashCode() {
        return Objects.hash(this.maxDataCalls, this.isDcNrRestricted, this.isNrAvailable, this.isEnDcAvailable, this.mLteVopsSupportInfo, this.mIsUsingCarrierAggregation);
    }

    public boolean isUsingCarrierAggregation() {
        return this.mIsUsingCarrierAggregation;
    }

    public void setIsUsingCarrierAggregation(boolean bl) {
        this.mIsUsingCarrierAggregation = bl;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.getClass().getName());
        stringBuilder.append(" :{");
        StringBuilder stringBuilder2 = new StringBuilder();
        stringBuilder2.append(" maxDataCalls = ");
        stringBuilder2.append(this.maxDataCalls);
        stringBuilder.append(stringBuilder2.toString());
        stringBuilder2 = new StringBuilder();
        stringBuilder2.append(" isDcNrRestricted = ");
        stringBuilder2.append(this.isDcNrRestricted);
        stringBuilder.append(stringBuilder2.toString());
        stringBuilder2 = new StringBuilder();
        stringBuilder2.append(" isNrAvailable = ");
        stringBuilder2.append(this.isNrAvailable);
        stringBuilder.append(stringBuilder2.toString());
        stringBuilder2 = new StringBuilder();
        stringBuilder2.append(" isEnDcAvailable = ");
        stringBuilder2.append(this.isEnDcAvailable);
        stringBuilder.append(stringBuilder2.toString());
        stringBuilder2 = new StringBuilder();
        stringBuilder2.append(" ");
        stringBuilder2.append(this.mLteVopsSupportInfo.toString());
        stringBuilder.append(stringBuilder2.toString());
        stringBuilder2 = new StringBuilder();
        stringBuilder2.append(" mIsUsingCarrierAggregation = ");
        stringBuilder2.append(this.mIsUsingCarrierAggregation);
        stringBuilder.append(stringBuilder2.toString());
        stringBuilder.append(" }");
        return stringBuilder.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeInt(this.maxDataCalls);
        parcel.writeBoolean(this.isDcNrRestricted);
        parcel.writeBoolean(this.isNrAvailable);
        parcel.writeBoolean(this.isEnDcAvailable);
        this.mLteVopsSupportInfo.writeToParcel(parcel, n);
        parcel.writeBoolean(this.mIsUsingCarrierAggregation);
    }

}

