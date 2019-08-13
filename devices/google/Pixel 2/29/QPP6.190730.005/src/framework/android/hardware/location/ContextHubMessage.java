/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.location;

import android.annotation.SystemApi;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Arrays;

@SystemApi
@Deprecated
public class ContextHubMessage
implements Parcelable {
    public static final Parcelable.Creator<ContextHubMessage> CREATOR = new Parcelable.Creator<ContextHubMessage>(){

        @Override
        public ContextHubMessage createFromParcel(Parcel parcel) {
            return new ContextHubMessage(parcel);
        }

        public ContextHubMessage[] newArray(int n) {
            return new ContextHubMessage[n];
        }
    };
    private static final int DEBUG_LOG_NUM_BYTES = 16;
    private byte[] mData;
    private int mType;
    private int mVersion;

    public ContextHubMessage(int n, int n2, byte[] arrby) {
        this.mType = n;
        this.mVersion = n2;
        this.mData = Arrays.copyOf(arrby, arrby.length);
    }

    private ContextHubMessage(Parcel parcel) {
        this.mType = parcel.readInt();
        this.mVersion = parcel.readInt();
        this.mData = new byte[parcel.readInt()];
        parcel.readByteArray(this.mData);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public byte[] getData() {
        byte[] arrby = this.mData;
        return Arrays.copyOf(arrby, arrby.length);
    }

    public int getMsgType() {
        return this.mType;
    }

    public int getVersion() {
        return this.mVersion;
    }

    public void setMsgData(byte[] arrby) {
        this.mData = Arrays.copyOf(arrby, arrby.length);
    }

    public void setMsgType(int n) {
        this.mType = n;
    }

    public void setVersion(int n) {
        this.mVersion = n;
    }

    public String toString() {
        int n = this.mData.length;
        CharSequence charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append("ContextHubMessage[type = ");
        ((StringBuilder)charSequence).append(this.mType);
        ((StringBuilder)charSequence).append(", length = ");
        ((StringBuilder)charSequence).append(this.mData.length);
        ((StringBuilder)charSequence).append(" bytes](");
        CharSequence charSequence2 = ((StringBuilder)charSequence).toString();
        charSequence = charSequence2;
        if (n > 0) {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append((String)charSequence2);
            ((StringBuilder)charSequence).append("data = 0x");
            charSequence = ((StringBuilder)charSequence).toString();
        }
        for (int i = 0; i < Math.min(n, 16); ++i) {
            charSequence2 = new StringBuilder();
            ((StringBuilder)charSequence2).append((String)charSequence);
            ((StringBuilder)charSequence2).append(Byte.toHexString((byte)this.mData[i], (boolean)true));
            charSequence2 = ((StringBuilder)charSequence2).toString();
            charSequence = charSequence2;
            if ((i + 1) % 4 != 0) continue;
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append((String)charSequence2);
            ((StringBuilder)charSequence).append(" ");
            charSequence = ((StringBuilder)charSequence).toString();
        }
        charSequence2 = charSequence;
        if (n > 16) {
            charSequence2 = new StringBuilder();
            ((StringBuilder)charSequence2).append((String)charSequence);
            ((StringBuilder)charSequence2).append("...");
            charSequence2 = ((StringBuilder)charSequence2).toString();
        }
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append((String)charSequence2);
        ((StringBuilder)charSequence).append(")");
        return ((StringBuilder)charSequence).toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeInt(this.mType);
        parcel.writeInt(this.mVersion);
        parcel.writeInt(this.mData.length);
        parcel.writeByteArray(this.mData);
    }

}

