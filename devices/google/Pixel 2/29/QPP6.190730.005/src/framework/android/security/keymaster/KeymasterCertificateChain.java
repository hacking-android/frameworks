/*
 * Decompiled with CFR 0.145.
 */
package android.security.keymaster;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class KeymasterCertificateChain
implements Parcelable {
    public static final Parcelable.Creator<KeymasterCertificateChain> CREATOR = new Parcelable.Creator<KeymasterCertificateChain>(){

        @Override
        public KeymasterCertificateChain createFromParcel(Parcel parcel) {
            return new KeymasterCertificateChain(parcel);
        }

        public KeymasterCertificateChain[] newArray(int n) {
            return new KeymasterCertificateChain[n];
        }
    };
    private List<byte[]> mCertificates;

    public KeymasterCertificateChain() {
        this.mCertificates = null;
    }

    private KeymasterCertificateChain(Parcel parcel) {
        this.readFromParcel(parcel);
    }

    public KeymasterCertificateChain(List<byte[]> list) {
        this.mCertificates = list;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public List<byte[]> getCertificates() {
        return this.mCertificates;
    }

    public void readFromParcel(Parcel parcel) {
        int n = parcel.readInt();
        this.mCertificates = new ArrayList<byte[]>(n);
        for (int i = 0; i < n; ++i) {
            this.mCertificates.add(parcel.createByteArray());
        }
    }

    public void shallowCopyFrom(KeymasterCertificateChain keymasterCertificateChain) {
        this.mCertificates = keymasterCertificateChain.mCertificates;
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        List<byte[]> list = this.mCertificates;
        if (list == null) {
            parcel.writeInt(0);
        } else {
            parcel.writeInt(list.size());
            list = this.mCertificates.iterator();
            while (list.hasNext()) {
                parcel.writeByteArray((byte[])list.next());
            }
        }
    }

}

