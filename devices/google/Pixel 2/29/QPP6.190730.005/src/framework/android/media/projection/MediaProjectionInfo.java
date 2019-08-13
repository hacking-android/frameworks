/*
 * Decompiled with CFR 0.145.
 */
package android.media.projection;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.UserHandle;
import java.util.Objects;

public final class MediaProjectionInfo
implements Parcelable {
    public static final Parcelable.Creator<MediaProjectionInfo> CREATOR = new Parcelable.Creator<MediaProjectionInfo>(){

        @Override
        public MediaProjectionInfo createFromParcel(Parcel parcel) {
            return new MediaProjectionInfo(parcel);
        }

        public MediaProjectionInfo[] newArray(int n) {
            return new MediaProjectionInfo[n];
        }
    };
    private final String mPackageName;
    private final UserHandle mUserHandle;

    public MediaProjectionInfo(Parcel parcel) {
        this.mPackageName = parcel.readString();
        this.mUserHandle = UserHandle.readFromParcel(parcel);
    }

    public MediaProjectionInfo(String string2, UserHandle userHandle) {
        this.mPackageName = string2;
        this.mUserHandle = userHandle;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public boolean equals(Object object) {
        boolean bl = object instanceof MediaProjectionInfo;
        boolean bl2 = false;
        if (bl) {
            object = (MediaProjectionInfo)object;
            if (Objects.equals(((MediaProjectionInfo)object).mPackageName, this.mPackageName) && Objects.equals(((MediaProjectionInfo)object).mUserHandle, this.mUserHandle)) {
                bl2 = true;
            }
            return bl2;
        }
        return false;
    }

    public String getPackageName() {
        return this.mPackageName;
    }

    public UserHandle getUserHandle() {
        return this.mUserHandle;
    }

    public int hashCode() {
        return Objects.hash(this.mPackageName, this.mUserHandle);
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("MediaProjectionInfo{mPackageName=");
        stringBuilder.append(this.mPackageName);
        stringBuilder.append(", mUserHandle=");
        stringBuilder.append(this.mUserHandle);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeString(this.mPackageName);
        UserHandle.writeToParcel(this.mUserHandle, parcel);
    }

}

