/*
 * Decompiled with CFR 0.145.
 */
package android.view;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import android.view.IInputMonitorHost;
import android.view.InputChannel;

public final class InputMonitor
implements Parcelable {
    public static final Parcelable.Creator<InputMonitor> CREATOR = new Parcelable.Creator<InputMonitor>(){

        @Override
        public InputMonitor createFromParcel(Parcel parcel) {
            return new InputMonitor(parcel);
        }

        public InputMonitor[] newArray(int n) {
            return new InputMonitor[n];
        }
    };
    private static final boolean DEBUG = false;
    private static final String TAG = "InputMonitor";
    private final InputChannel mChannel;
    private final IInputMonitorHost mHost;
    private final String mName;

    public InputMonitor(Parcel parcel) {
        this.mName = parcel.readString();
        this.mChannel = (InputChannel)parcel.readParcelable(null);
        this.mHost = IInputMonitorHost.Stub.asInterface(parcel.readStrongBinder());
    }

    public InputMonitor(String string2, InputChannel inputChannel, IInputMonitorHost iInputMonitorHost) {
        this.mName = string2;
        this.mChannel = inputChannel;
        this.mHost = iInputMonitorHost;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public void dispose() {
        this.mChannel.dispose();
        try {
            this.mHost.dispose();
        }
        catch (RemoteException remoteException) {
            remoteException.rethrowFromSystemServer();
        }
    }

    public InputChannel getInputChannel() {
        return this.mChannel;
    }

    public String getName() {
        return this.mName;
    }

    public void pilferPointers() {
        try {
            this.mHost.pilferPointers();
        }
        catch (RemoteException remoteException) {
            remoteException.rethrowFromSystemServer();
        }
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("InputMonitor{mName=");
        stringBuilder.append(this.mName);
        stringBuilder.append(", mChannel=");
        stringBuilder.append(this.mChannel);
        stringBuilder.append(", mHost=");
        stringBuilder.append(this.mHost);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeString(this.mName);
        parcel.writeParcelable(this.mChannel, n);
        parcel.writeStrongBinder(this.mHost.asBinder());
    }

}

