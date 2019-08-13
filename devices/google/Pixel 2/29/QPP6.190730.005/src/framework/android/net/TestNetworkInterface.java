/*
 * Decompiled with CFR 0.145.
 */
package android.net;

import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.Parcelable;

public final class TestNetworkInterface
implements Parcelable {
    public static final Parcelable.Creator<TestNetworkInterface> CREATOR = new Parcelable.Creator<TestNetworkInterface>(){

        @Override
        public TestNetworkInterface createFromParcel(Parcel parcel) {
            return new TestNetworkInterface(parcel);
        }

        public TestNetworkInterface[] newArray(int n) {
            return new TestNetworkInterface[n];
        }
    };
    private final ParcelFileDescriptor mFileDescriptor;
    private final String mInterfaceName;

    private TestNetworkInterface(Parcel parcel) {
        this.mFileDescriptor = (ParcelFileDescriptor)parcel.readParcelable(ParcelFileDescriptor.class.getClassLoader());
        this.mInterfaceName = parcel.readString();
    }

    public TestNetworkInterface(ParcelFileDescriptor parcelFileDescriptor, String string2) {
        this.mFileDescriptor = parcelFileDescriptor;
        this.mInterfaceName = string2;
    }

    @Override
    public int describeContents() {
        int n = this.mFileDescriptor != null ? 1 : 0;
        return n;
    }

    public ParcelFileDescriptor getFileDescriptor() {
        return this.mFileDescriptor;
    }

    public String getInterfaceName() {
        return this.mInterfaceName;
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeParcelable(this.mFileDescriptor, 1);
        parcel.writeString(this.mInterfaceName);
    }

}

