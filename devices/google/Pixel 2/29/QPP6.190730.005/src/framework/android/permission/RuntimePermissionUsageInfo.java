/*
 * Decompiled with CFR 0.145.
 */
package android.permission;

import android.annotation.SystemApi;
import android.os.Parcel;
import android.os.Parcelable;
import com.android.internal.util.Preconditions;

@SystemApi
public final class RuntimePermissionUsageInfo
implements Parcelable {
    public static final Parcelable.Creator<RuntimePermissionUsageInfo> CREATOR = new Parcelable.Creator<RuntimePermissionUsageInfo>(){

        @Override
        public RuntimePermissionUsageInfo createFromParcel(Parcel parcel) {
            return new RuntimePermissionUsageInfo(parcel);
        }

        public RuntimePermissionUsageInfo[] newArray(int n) {
            return new RuntimePermissionUsageInfo[n];
        }
    };
    private final String mName;
    private final int mNumUsers;

    private RuntimePermissionUsageInfo(Parcel parcel) {
        this(parcel.readString(), parcel.readInt());
    }

    public RuntimePermissionUsageInfo(String string2, int n) {
        Preconditions.checkNotNull(string2);
        Preconditions.checkArgumentNonnegative(n);
        this.mName = string2;
        this.mNumUsers = n;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public int getAppAccessCount() {
        return this.mNumUsers;
    }

    public String getName() {
        return this.mName;
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeString(this.mName);
        parcel.writeInt(this.mNumUsers);
    }

}

