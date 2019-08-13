/*
 * Decompiled with CFR 0.145.
 */
package android.net;

import android.annotation.SystemApi;
import android.net.ICaptivePortal;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;

public class CaptivePortal
implements Parcelable {
    @SystemApi
    public static final int APP_RETURN_DISMISSED = 0;
    @SystemApi
    public static final int APP_RETURN_UNWANTED = 1;
    @SystemApi
    public static final int APP_RETURN_WANTED_AS_IS = 2;
    public static final Parcelable.Creator<CaptivePortal> CREATOR = new Parcelable.Creator<CaptivePortal>(){

        @Override
        public CaptivePortal createFromParcel(Parcel parcel) {
            return new CaptivePortal(parcel.readStrongBinder());
        }

        public CaptivePortal[] newArray(int n) {
            return new CaptivePortal[n];
        }
    };
    private final IBinder mBinder;

    public CaptivePortal(IBinder iBinder) {
        this.mBinder = iBinder;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public void ignoreNetwork() {
        try {
            ICaptivePortal.Stub.asInterface(this.mBinder).appResponse(1);
        }
        catch (RemoteException remoteException) {
            // empty catch block
        }
    }

    @SystemApi
    public void logEvent(int n, String string2) {
        try {
            ICaptivePortal.Stub.asInterface(this.mBinder).logEvent(n, string2);
        }
        catch (RemoteException remoteException) {
            // empty catch block
        }
    }

    public void reportCaptivePortalDismissed() {
        try {
            ICaptivePortal.Stub.asInterface(this.mBinder).appResponse(0);
        }
        catch (RemoteException remoteException) {
            // empty catch block
        }
    }

    @SystemApi
    public void useNetwork() {
        try {
            ICaptivePortal.Stub.asInterface(this.mBinder).appResponse(2);
        }
        catch (RemoteException remoteException) {
            // empty catch block
        }
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeStrongBinder(this.mBinder);
    }

}

