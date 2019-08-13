/*
 * Decompiled with CFR 0.145.
 */
package android.service.euicc;

import android.annotation.SystemApi;
import android.os.Parcel;
import android.os.Parcelable;
import android.service.euicc.EuiccProfileInfo;
import java.util.Arrays;
import java.util.List;

@SystemApi
public final class GetEuiccProfileInfoListResult
implements Parcelable {
    public static final Parcelable.Creator<GetEuiccProfileInfoListResult> CREATOR = new Parcelable.Creator<GetEuiccProfileInfoListResult>(){

        @Override
        public GetEuiccProfileInfoListResult createFromParcel(Parcel parcel) {
            return new GetEuiccProfileInfoListResult(parcel);
        }

        public GetEuiccProfileInfoListResult[] newArray(int n) {
            return new GetEuiccProfileInfoListResult[n];
        }
    };
    private final boolean mIsRemovable;
    private final EuiccProfileInfo[] mProfiles;
    @Deprecated
    public final int result;

    public GetEuiccProfileInfoListResult(int n, EuiccProfileInfo[] object, boolean bl) {
        this.result = n;
        this.mIsRemovable = bl;
        if (this.result == 0) {
            this.mProfiles = object;
        } else {
            if (object != null && ((EuiccProfileInfo[])object).length > 0) {
                object = new StringBuilder();
                ((StringBuilder)object).append("Error result with non-empty profiles: ");
                ((StringBuilder)object).append(n);
                throw new IllegalArgumentException(((StringBuilder)object).toString());
            }
            this.mProfiles = null;
        }
    }

    private GetEuiccProfileInfoListResult(Parcel parcel) {
        this.result = parcel.readInt();
        this.mProfiles = parcel.createTypedArray(EuiccProfileInfo.CREATOR);
        this.mIsRemovable = parcel.readBoolean();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public boolean getIsRemovable() {
        return this.mIsRemovable;
    }

    public List<EuiccProfileInfo> getProfiles() {
        EuiccProfileInfo[] arreuiccProfileInfo = this.mProfiles;
        if (arreuiccProfileInfo == null) {
            return null;
        }
        return Arrays.asList(arreuiccProfileInfo);
    }

    public int getResult() {
        return this.result;
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeInt(this.result);
        parcel.writeTypedArray((Parcelable[])this.mProfiles, n);
        parcel.writeBoolean(this.mIsRemovable);
    }

}

