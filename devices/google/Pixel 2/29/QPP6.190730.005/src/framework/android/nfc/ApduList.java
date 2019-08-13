/*
 * Decompiled with CFR 0.145.
 */
package android.nfc;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;
import java.util.List;

public class ApduList
implements Parcelable {
    public static final Parcelable.Creator<ApduList> CREATOR = new Parcelable.Creator<ApduList>(){

        @Override
        public ApduList createFromParcel(Parcel parcel) {
            return new ApduList(parcel);
        }

        public ApduList[] newArray(int n) {
            return new ApduList[n];
        }
    };
    private ArrayList<byte[]> commands = new ArrayList();

    public ApduList() {
    }

    private ApduList(Parcel parcel) {
        int n = parcel.readInt();
        for (int i = 0; i < n; ++i) {
            byte[] arrby = new byte[parcel.readInt()];
            parcel.readByteArray(arrby);
            this.commands.add(arrby);
        }
    }

    public void add(byte[] arrby) {
        this.commands.add(arrby);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public List<byte[]> get() {
        return this.commands;
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeInt(this.commands.size());
        for (byte[] arrby : this.commands) {
            parcel.writeInt(arrby.length);
            parcel.writeByteArray(arrby);
        }
    }

}

