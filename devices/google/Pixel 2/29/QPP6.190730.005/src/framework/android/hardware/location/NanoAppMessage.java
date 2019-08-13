/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.location;

import android.annotation.SystemApi;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Arrays;

@SystemApi
public final class NanoAppMessage
implements Parcelable {
    public static final Parcelable.Creator<NanoAppMessage> CREATOR = new Parcelable.Creator<NanoAppMessage>(){

        @Override
        public NanoAppMessage createFromParcel(Parcel parcel) {
            return new NanoAppMessage(parcel);
        }

        public NanoAppMessage[] newArray(int n) {
            return new NanoAppMessage[n];
        }
    };
    private static final int DEBUG_LOG_NUM_BYTES = 16;
    private boolean mIsBroadcasted;
    private byte[] mMessageBody;
    private int mMessageType;
    private long mNanoAppId;

    private NanoAppMessage(long l, int n, byte[] arrby, boolean bl) {
        this.mNanoAppId = l;
        this.mMessageType = n;
        this.mMessageBody = arrby;
        this.mIsBroadcasted = bl;
    }

    private NanoAppMessage(Parcel parcel) {
        this.mNanoAppId = parcel.readLong();
        int n = parcel.readInt();
        boolean bl = true;
        if (n != 1) {
            bl = false;
        }
        this.mIsBroadcasted = bl;
        this.mMessageType = parcel.readInt();
        this.mMessageBody = new byte[parcel.readInt()];
        parcel.readByteArray(this.mMessageBody);
    }

    public static NanoAppMessage createMessageFromNanoApp(long l, int n, byte[] arrby, boolean bl) {
        return new NanoAppMessage(l, n, arrby, bl);
    }

    public static NanoAppMessage createMessageToNanoApp(long l, int n, byte[] arrby) {
        return new NanoAppMessage(l, n, arrby, false);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public boolean equals(Object object) {
        boolean bl = true;
        if (object == this) {
            return true;
        }
        boolean bl2 = false;
        if (object instanceof NanoAppMessage) {
            bl2 = ((NanoAppMessage)(object = (NanoAppMessage)object)).getNanoAppId() == this.mNanoAppId && ((NanoAppMessage)object).getMessageType() == this.mMessageType && ((NanoAppMessage)object).isBroadcastMessage() == this.mIsBroadcasted && Arrays.equals(((NanoAppMessage)object).getMessageBody(), this.mMessageBody) ? bl : false;
        }
        return bl2;
    }

    public byte[] getMessageBody() {
        return this.mMessageBody;
    }

    public int getMessageType() {
        return this.mMessageType;
    }

    public long getNanoAppId() {
        return this.mNanoAppId;
    }

    public boolean isBroadcastMessage() {
        return this.mIsBroadcasted;
    }

    public String toString() {
        int n = this.mMessageBody.length;
        CharSequence charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append("NanoAppMessage[type = ");
        ((StringBuilder)charSequence).append(this.mMessageType);
        ((StringBuilder)charSequence).append(", length = ");
        ((StringBuilder)charSequence).append(this.mMessageBody.length);
        ((StringBuilder)charSequence).append(" bytes, ");
        CharSequence charSequence2 = this.mIsBroadcasted ? "broadcast" : "unicast";
        ((StringBuilder)charSequence).append((String)charSequence2);
        ((StringBuilder)charSequence).append(", nanoapp = 0x");
        ((StringBuilder)charSequence).append(Long.toHexString(this.mNanoAppId));
        ((StringBuilder)charSequence).append("](");
        charSequence = ((StringBuilder)charSequence).toString();
        charSequence2 = charSequence;
        if (n > 0) {
            charSequence2 = new StringBuilder();
            ((StringBuilder)charSequence2).append((String)charSequence);
            ((StringBuilder)charSequence2).append("data = 0x");
            charSequence2 = ((StringBuilder)charSequence2).toString();
        }
        for (int i = 0; i < Math.min(n, 16); ++i) {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append((String)charSequence2);
            ((StringBuilder)charSequence).append(Byte.toHexString((byte)this.mMessageBody[i], (boolean)true));
            charSequence = ((StringBuilder)charSequence).toString();
            charSequence2 = charSequence;
            if ((i + 1) % 4 != 0) continue;
            charSequence2 = new StringBuilder();
            ((StringBuilder)charSequence2).append((String)charSequence);
            ((StringBuilder)charSequence2).append(" ");
            charSequence2 = ((StringBuilder)charSequence2).toString();
        }
        charSequence = charSequence2;
        if (n > 16) {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append((String)charSequence2);
            ((StringBuilder)charSequence).append("...");
            charSequence = ((StringBuilder)charSequence).toString();
        }
        charSequence2 = new StringBuilder();
        ((StringBuilder)charSequence2).append((String)charSequence);
        ((StringBuilder)charSequence2).append(")");
        return ((StringBuilder)charSequence2).toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeLong(this.mNanoAppId);
        parcel.writeInt((int)this.mIsBroadcasted);
        parcel.writeInt(this.mMessageType);
        parcel.writeInt(this.mMessageBody.length);
        parcel.writeByteArray(this.mMessageBody);
    }

}

