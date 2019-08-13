/*
 * Decompiled with CFR 0.145.
 */
package android.os;

import android.os.Handler;
import android.os.IBinder;
import android.os.IMessenger;
import android.os.Message;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;

public final class Messenger
implements Parcelable {
    public static final Parcelable.Creator<Messenger> CREATOR = new Parcelable.Creator<Messenger>(){

        @Override
        public Messenger createFromParcel(Parcel object) {
            object = (object = ((Parcel)object).readStrongBinder()) != null ? new Messenger((IBinder)object) : null;
            return object;
        }

        public Messenger[] newArray(int n) {
            return new Messenger[n];
        }
    };
    private final IMessenger mTarget;

    public Messenger(Handler handler) {
        this.mTarget = handler.getIMessenger();
    }

    public Messenger(IBinder iBinder) {
        this.mTarget = IMessenger.Stub.asInterface(iBinder);
    }

    public static Messenger readMessengerOrNullFromParcel(Parcel object) {
        object = (object = ((Parcel)object).readStrongBinder()) != null ? new Messenger((IBinder)object) : null;
        return object;
    }

    public static void writeMessengerOrNullToParcel(Messenger object, Parcel parcel) {
        object = object != null ? ((Messenger)object).mTarget.asBinder() : null;
        parcel.writeStrongBinder((IBinder)object);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public boolean equals(Object object) {
        if (object == null) {
            return false;
        }
        try {
            boolean bl = this.mTarget.asBinder().equals(((Messenger)object).mTarget.asBinder());
            return bl;
        }
        catch (ClassCastException classCastException) {
            return false;
        }
    }

    public IBinder getBinder() {
        return this.mTarget.asBinder();
    }

    public int hashCode() {
        return this.mTarget.asBinder().hashCode();
    }

    public void send(Message message) throws RemoteException {
        this.mTarget.send(message);
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeStrongBinder(this.mTarget.asBinder());
    }

}

