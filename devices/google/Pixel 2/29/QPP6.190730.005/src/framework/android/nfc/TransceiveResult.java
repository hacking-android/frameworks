/*
 * Decompiled with CFR 0.145.
 */
package android.nfc;

import android.nfc.TagLostException;
import android.os.Parcel;
import android.os.Parcelable;
import java.io.IOException;

public final class TransceiveResult
implements Parcelable {
    public static final Parcelable.Creator<TransceiveResult> CREATOR = new Parcelable.Creator<TransceiveResult>(){

        @Override
        public TransceiveResult createFromParcel(Parcel arrby) {
            int n = arrby.readInt();
            if (n == 0) {
                byte[] arrby2 = new byte[arrby.readInt()];
                arrby.readByteArray(arrby2);
                arrby = arrby2;
            } else {
                arrby = null;
            }
            return new TransceiveResult(n, arrby);
        }

        public TransceiveResult[] newArray(int n) {
            return new TransceiveResult[n];
        }
    };
    public static final int RESULT_EXCEEDED_LENGTH = 3;
    public static final int RESULT_FAILURE = 1;
    public static final int RESULT_SUCCESS = 0;
    public static final int RESULT_TAGLOST = 2;
    final byte[] mResponseData;
    final int mResult;

    public TransceiveResult(int n, byte[] arrby) {
        this.mResult = n;
        this.mResponseData = arrby;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public byte[] getResponseOrThrow() throws IOException {
        int n = this.mResult;
        if (n != 0) {
            if (n != 2) {
                if (n != 3) {
                    throw new IOException("Transceive failed");
                }
                throw new IOException("Transceive length exceeds supported maximum");
            }
            throw new TagLostException("Tag was lost.");
        }
        return this.mResponseData;
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeInt(this.mResult);
        if (this.mResult == 0) {
            parcel.writeInt(this.mResponseData.length);
            parcel.writeByteArray(this.mResponseData);
        }
    }

}

