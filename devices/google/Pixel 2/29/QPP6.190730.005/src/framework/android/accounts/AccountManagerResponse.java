/*
 * Decompiled with CFR 0.145.
 */
package android.accounts;

import android.accounts.IAccountManagerResponse;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;

public class AccountManagerResponse
implements Parcelable {
    public static final Parcelable.Creator<AccountManagerResponse> CREATOR = new Parcelable.Creator<AccountManagerResponse>(){

        @Override
        public AccountManagerResponse createFromParcel(Parcel parcel) {
            return new AccountManagerResponse(parcel);
        }

        public AccountManagerResponse[] newArray(int n) {
            return new AccountManagerResponse[n];
        }
    };
    private IAccountManagerResponse mResponse;

    public AccountManagerResponse(IAccountManagerResponse iAccountManagerResponse) {
        this.mResponse = iAccountManagerResponse;
    }

    public AccountManagerResponse(Parcel parcel) {
        this.mResponse = IAccountManagerResponse.Stub.asInterface(parcel.readStrongBinder());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public void onError(int n, String string2) {
        try {
            this.mResponse.onError(n, string2);
        }
        catch (RemoteException remoteException) {
            // empty catch block
        }
    }

    public void onResult(Bundle bundle) {
        try {
            this.mResponse.onResult(bundle);
        }
        catch (RemoteException remoteException) {
            // empty catch block
        }
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeStrongBinder(this.mResponse.asBinder());
    }

}

