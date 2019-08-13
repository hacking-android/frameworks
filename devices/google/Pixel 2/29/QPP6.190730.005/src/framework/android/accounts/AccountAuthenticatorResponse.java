/*
 * Decompiled with CFR 0.145.
 */
package android.accounts;

import android.accounts.AccountManager;
import android.accounts.IAccountAuthenticatorResponse;
import android.annotation.UnsupportedAppUsage;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import android.util.Log;
import java.util.Set;

public class AccountAuthenticatorResponse
implements Parcelable {
    public static final Parcelable.Creator<AccountAuthenticatorResponse> CREATOR = new Parcelable.Creator<AccountAuthenticatorResponse>(){

        @Override
        public AccountAuthenticatorResponse createFromParcel(Parcel parcel) {
            return new AccountAuthenticatorResponse(parcel);
        }

        public AccountAuthenticatorResponse[] newArray(int n) {
            return new AccountAuthenticatorResponse[n];
        }
    };
    private static final String TAG = "AccountAuthenticator";
    private IAccountAuthenticatorResponse mAccountAuthenticatorResponse;

    @UnsupportedAppUsage
    public AccountAuthenticatorResponse(IAccountAuthenticatorResponse iAccountAuthenticatorResponse) {
        this.mAccountAuthenticatorResponse = iAccountAuthenticatorResponse;
    }

    public AccountAuthenticatorResponse(Parcel parcel) {
        this.mAccountAuthenticatorResponse = IAccountAuthenticatorResponse.Stub.asInterface(parcel.readStrongBinder());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public void onError(int n, String string2) {
        if (Log.isLoggable(TAG, 2)) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("AccountAuthenticatorResponse.onError: ");
            stringBuilder.append(n);
            stringBuilder.append(", ");
            stringBuilder.append(string2);
            Log.v(TAG, stringBuilder.toString());
        }
        try {
            this.mAccountAuthenticatorResponse.onError(n, string2);
        }
        catch (RemoteException remoteException) {
            // empty catch block
        }
    }

    public void onRequestContinued() {
        if (Log.isLoggable(TAG, 2)) {
            Log.v(TAG, "AccountAuthenticatorResponse.onRequestContinued");
        }
        try {
            this.mAccountAuthenticatorResponse.onRequestContinued();
        }
        catch (RemoteException remoteException) {
            // empty catch block
        }
    }

    public void onResult(Bundle bundle) {
        if (Log.isLoggable(TAG, 2)) {
            bundle.keySet();
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("AccountAuthenticatorResponse.onResult: ");
            stringBuilder.append(AccountManager.sanitizeResult(bundle));
            Log.v(TAG, stringBuilder.toString());
        }
        try {
            this.mAccountAuthenticatorResponse.onResult(bundle);
        }
        catch (RemoteException remoteException) {
            // empty catch block
        }
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeStrongBinder(this.mAccountAuthenticatorResponse.asBinder());
    }

}

