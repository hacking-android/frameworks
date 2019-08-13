/*
 * Decompiled with CFR 0.145.
 */
package android.content.pm;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;
import java.util.List;

public final class ChangedPackages
implements Parcelable {
    public static final Parcelable.Creator<ChangedPackages> CREATOR = new Parcelable.Creator<ChangedPackages>(){

        @Override
        public ChangedPackages createFromParcel(Parcel parcel) {
            return new ChangedPackages(parcel);
        }

        public ChangedPackages[] newArray(int n) {
            return new ChangedPackages[n];
        }
    };
    private final List<String> mPackageNames;
    private final int mSequenceNumber;

    public ChangedPackages(int n, List<String> list) {
        this.mSequenceNumber = n;
        this.mPackageNames = list;
    }

    protected ChangedPackages(Parcel parcel) {
        this.mSequenceNumber = parcel.readInt();
        this.mPackageNames = parcel.createStringArrayList();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public List<String> getPackageNames() {
        return this.mPackageNames;
    }

    public int getSequenceNumber() {
        return this.mSequenceNumber;
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeInt(this.mSequenceNumber);
        parcel.writeStringList(this.mPackageNames);
    }

}

