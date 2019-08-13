/*
 * Decompiled with CFR 0.145.
 */
package android.view.inputmethod;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import android.view.inputmethod.InputConnection;

public final class InputBinding
implements Parcelable {
    public static final Parcelable.Creator<InputBinding> CREATOR = new Parcelable.Creator<InputBinding>(){

        @Override
        public InputBinding createFromParcel(Parcel parcel) {
            return new InputBinding(parcel);
        }

        public InputBinding[] newArray(int n) {
            return new InputBinding[n];
        }
    };
    static final String TAG = "InputBinding";
    final InputConnection mConnection;
    final IBinder mConnectionToken;
    final int mPid;
    final int mUid;

    InputBinding(Parcel parcel) {
        this.mConnection = null;
        this.mConnectionToken = parcel.readStrongBinder();
        this.mUid = parcel.readInt();
        this.mPid = parcel.readInt();
    }

    public InputBinding(InputConnection inputConnection, IBinder iBinder, int n, int n2) {
        this.mConnection = inputConnection;
        this.mConnectionToken = iBinder;
        this.mUid = n;
        this.mPid = n2;
    }

    public InputBinding(InputConnection inputConnection, InputBinding inputBinding) {
        this.mConnection = inputConnection;
        this.mConnectionToken = inputBinding.getConnectionToken();
        this.mUid = inputBinding.getUid();
        this.mPid = inputBinding.getPid();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public InputConnection getConnection() {
        return this.mConnection;
    }

    public IBinder getConnectionToken() {
        return this.mConnectionToken;
    }

    public int getPid() {
        return this.mPid;
    }

    public int getUid() {
        return this.mUid;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("InputBinding{");
        stringBuilder.append(this.mConnectionToken);
        stringBuilder.append(" / uid ");
        stringBuilder.append(this.mUid);
        stringBuilder.append(" / pid ");
        stringBuilder.append(this.mPid);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeStrongBinder(this.mConnectionToken);
        parcel.writeInt(this.mUid);
        parcel.writeInt(this.mPid);
    }

}

