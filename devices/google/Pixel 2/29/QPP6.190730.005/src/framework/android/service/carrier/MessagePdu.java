/*
 * Decompiled with CFR 0.145.
 */
package android.service.carrier;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public final class MessagePdu
implements Parcelable {
    public static final Parcelable.Creator<MessagePdu> CREATOR = new Parcelable.Creator<MessagePdu>(){

        @Override
        public MessagePdu createFromParcel(Parcel parcel) {
            ArrayList<byte[]> arrayList;
            int n = parcel.readInt();
            if (n == -1) {
                arrayList = null;
            } else {
                ArrayList<byte[]> arrayList2 = new ArrayList<byte[]>(n);
                int n2 = 0;
                do {
                    arrayList = arrayList2;
                    if (n2 >= n) break;
                    arrayList2.add(parcel.createByteArray());
                    ++n2;
                } while (true);
            }
            return new MessagePdu(arrayList);
        }

        public MessagePdu[] newArray(int n) {
            return new MessagePdu[n];
        }
    };
    private static final int NULL_LENGTH = -1;
    private final List<byte[]> mPduList;

    public MessagePdu(List<byte[]> list) {
        if (list != null && !list.contains(null)) {
            this.mPduList = list;
            return;
        }
        throw new IllegalArgumentException("pduList must not be null or contain nulls");
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public List<byte[]> getPdus() {
        return this.mPduList;
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        List<byte[]> list = this.mPduList;
        if (list == null) {
            parcel.writeInt(-1);
        } else {
            parcel.writeInt(list.size());
            list = this.mPduList.iterator();
            while (list.hasNext()) {
                parcel.writeByteArray((byte[])list.next());
            }
        }
    }

}

