/*
 * Decompiled with CFR 0.145.
 */
package android.content.pm.permission;

import android.annotation.SystemApi;
import android.os.Parcel;
import android.os.Parcelable;

@SystemApi
@Deprecated
public final class RuntimePermissionPresentationInfo
implements Parcelable {
    public static final Parcelable.Creator<RuntimePermissionPresentationInfo> CREATOR = new Parcelable.Creator<RuntimePermissionPresentationInfo>(){

        @Override
        public RuntimePermissionPresentationInfo createFromParcel(Parcel parcel) {
            return new RuntimePermissionPresentationInfo(parcel);
        }

        public RuntimePermissionPresentationInfo[] newArray(int n) {
            return new RuntimePermissionPresentationInfo[n];
        }
    };
    private static final int FLAG_GRANTED = 1;
    private static final int FLAG_STANDARD = 2;
    private final int mFlags;
    private final CharSequence mLabel;

    private RuntimePermissionPresentationInfo(Parcel parcel) {
        this.mLabel = parcel.readCharSequence();
        this.mFlags = parcel.readInt();
    }

    public RuntimePermissionPresentationInfo(CharSequence charSequence, boolean bl, boolean bl2) {
        this.mLabel = charSequence;
        int n = 0;
        if (bl) {
            n = false | true;
        }
        int n2 = n;
        if (bl2) {
            n2 = n | 2;
        }
        this.mFlags = n2;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public CharSequence getLabel() {
        return this.mLabel;
    }

    public boolean isGranted() {
        int n = this.mFlags;
        boolean bl = true;
        if ((n & 1) == 0) {
            bl = false;
        }
        return bl;
    }

    public boolean isStandard() {
        boolean bl = (this.mFlags & 2) != 0;
        return bl;
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeCharSequence(this.mLabel);
        parcel.writeInt(this.mFlags);
    }

}

