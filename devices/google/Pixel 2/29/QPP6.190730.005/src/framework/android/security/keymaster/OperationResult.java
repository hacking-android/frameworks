/*
 * Decompiled with CFR 0.145.
 */
package android.security.keymaster;

import android.annotation.UnsupportedAppUsage;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import android.security.keymaster.KeymasterArguments;

public class OperationResult
implements Parcelable {
    @UnsupportedAppUsage
    public static final Parcelable.Creator<OperationResult> CREATOR = new Parcelable.Creator<OperationResult>(){

        @Override
        public OperationResult createFromParcel(Parcel parcel) {
            return new OperationResult(parcel);
        }

        public OperationResult[] newArray(int n) {
            return new OperationResult[n];
        }
    };
    public final int inputConsumed;
    public final long operationHandle;
    public final KeymasterArguments outParams;
    public final byte[] output;
    public final int resultCode;
    public final IBinder token;

    public OperationResult(int n) {
        this(n, null, 0L, 0, null, null);
    }

    public OperationResult(int n, IBinder iBinder, long l, int n2, byte[] arrby, KeymasterArguments keymasterArguments) {
        this.resultCode = n;
        this.token = iBinder;
        this.operationHandle = l;
        this.inputConsumed = n2;
        this.output = arrby;
        this.outParams = keymasterArguments;
    }

    protected OperationResult(Parcel parcel) {
        this.resultCode = parcel.readInt();
        this.token = parcel.readStrongBinder();
        this.operationHandle = parcel.readLong();
        this.inputConsumed = parcel.readInt();
        this.output = parcel.createByteArray();
        this.outParams = KeymasterArguments.CREATOR.createFromParcel(parcel);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeInt(this.resultCode);
        parcel.writeStrongBinder(this.token);
        parcel.writeLong(this.operationHandle);
        parcel.writeInt(this.inputConsumed);
        parcel.writeByteArray(this.output);
        this.outParams.writeToParcel(parcel, n);
    }

}

