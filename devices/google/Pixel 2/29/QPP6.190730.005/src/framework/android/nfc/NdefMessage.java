/*
 * Decompiled with CFR 0.145.
 */
package android.nfc;

import android.nfc.FormatException;
import android.nfc.NdefRecord;
import android.os.Parcel;
import android.os.Parcelable;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.util.Arrays;

public final class NdefMessage
implements Parcelable {
    public static final Parcelable.Creator<NdefMessage> CREATOR = new Parcelable.Creator<NdefMessage>(){

        @Override
        public NdefMessage createFromParcel(Parcel parcel) {
            NdefRecord[] arrndefRecord = new NdefRecord[parcel.readInt()];
            parcel.readTypedArray(arrndefRecord, NdefRecord.CREATOR);
            return new NdefMessage(arrndefRecord);
        }

        public NdefMessage[] newArray(int n) {
            return new NdefMessage[n];
        }
    };
    private final NdefRecord[] mRecords;

    public NdefMessage(NdefRecord ndefRecord, NdefRecord ... arrndefRecord) {
        if (ndefRecord != null) {
            int n = arrndefRecord.length;
            for (int i = 0; i < n; ++i) {
                if (arrndefRecord[i] != null) {
                    continue;
                }
                throw new NullPointerException("record cannot be null");
            }
            NdefRecord[] arrndefRecord2 = this.mRecords = new NdefRecord[arrndefRecord.length + 1];
            arrndefRecord2[0] = ndefRecord;
            System.arraycopy(arrndefRecord, 0, arrndefRecord2, 1, arrndefRecord.length);
            return;
        }
        throw new NullPointerException("record cannot be null");
    }

    public NdefMessage(byte[] object) throws FormatException {
        if (object != null) {
            object = ByteBuffer.wrap((byte[])object);
            this.mRecords = NdefRecord.parse((ByteBuffer)object, false);
            if (((Buffer)object).remaining() <= 0) {
                return;
            }
            throw new FormatException("trailing data");
        }
        throw new NullPointerException("data is null");
    }

    public NdefMessage(NdefRecord[] arrndefRecord) {
        if (arrndefRecord.length >= 1) {
            int n = arrndefRecord.length;
            for (int i = 0; i < n; ++i) {
                if (arrndefRecord[i] != null) {
                    continue;
                }
                throw new NullPointerException("records cannot contain null");
            }
            this.mRecords = arrndefRecord;
            return;
        }
        throw new IllegalArgumentException("must have at least one record");
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null) {
            return false;
        }
        if (this.getClass() != object.getClass()) {
            return false;
        }
        object = (NdefMessage)object;
        return Arrays.equals(this.mRecords, ((NdefMessage)object).mRecords);
    }

    public int getByteArrayLength() {
        int n = 0;
        NdefRecord[] arrndefRecord = this.mRecords;
        int n2 = arrndefRecord.length;
        for (int i = 0; i < n2; ++i) {
            n += arrndefRecord[i].getByteLength();
        }
        return n;
    }

    public NdefRecord[] getRecords() {
        return this.mRecords;
    }

    public int hashCode() {
        return Arrays.hashCode(this.mRecords);
    }

    public byte[] toByteArray() {
        ByteBuffer byteBuffer = ByteBuffer.allocate(this.getByteArrayLength());
        for (int i = 0; i < this.mRecords.length; ++i) {
            boolean bl = false;
            boolean bl2 = i == 0;
            if (i == this.mRecords.length - 1) {
                bl = true;
            }
            this.mRecords[i].writeToByteBuffer(byteBuffer, bl2, bl);
        }
        return byteBuffer.array();
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("NdefMessage ");
        stringBuilder.append(Arrays.toString(this.mRecords));
        return stringBuilder.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeInt(this.mRecords.length);
        parcel.writeTypedArray((Parcelable[])this.mRecords, n);
    }

}

