/*
 * Decompiled with CFR 0.145.
 */
package android.os;

import android.os.Binder;
import android.os.IBinder;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.Parcelable;
import android.os.RemoteException;
import android.util.Log;
import com.android.internal.os.IShellCallback;

public class ShellCallback
implements Parcelable {
    public static final Parcelable.Creator<ShellCallback> CREATOR = new Parcelable.Creator<ShellCallback>(){

        @Override
        public ShellCallback createFromParcel(Parcel parcel) {
            return new ShellCallback(parcel);
        }

        public ShellCallback[] newArray(int n) {
            return new ShellCallback[n];
        }
    };
    static final boolean DEBUG = false;
    static final String TAG = "ShellCallback";
    final boolean mLocal;
    IShellCallback mShellCallback;

    public ShellCallback() {
        this.mLocal = true;
    }

    ShellCallback(Parcel object) {
        this.mLocal = false;
        this.mShellCallback = IShellCallback.Stub.asInterface(((Parcel)object).readStrongBinder());
        object = this.mShellCallback;
        if (object != null) {
            Binder.allowBlocking(object.asBinder());
        }
    }

    public static void writeToParcel(ShellCallback shellCallback, Parcel parcel) {
        if (shellCallback == null) {
            parcel.writeStrongBinder(null);
        } else {
            shellCallback.writeToParcel(parcel, 0);
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public ParcelFileDescriptor onOpenFile(String string2, String string3, String string4) {
        return null;
    }

    public ParcelFileDescriptor openFile(String string2, String object, String string3) {
        if (this.mLocal) {
            return this.onOpenFile(string2, (String)object, string3);
        }
        IShellCallback iShellCallback = this.mShellCallback;
        if (iShellCallback != null) {
            try {
                object = iShellCallback.openFile(string2, (String)object, string3);
                return object;
            }
            catch (RemoteException remoteException) {
                object = new StringBuilder();
                ((StringBuilder)object).append("Failure opening ");
                ((StringBuilder)object).append(string2);
                Log.w(TAG, ((StringBuilder)object).toString(), remoteException);
            }
        }
        return null;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void writeToParcel(Parcel parcel, int n) {
        synchronized (this) {
            if (this.mShellCallback == null) {
                MyShellCallback myShellCallback = new MyShellCallback();
                this.mShellCallback = myShellCallback;
            }
            parcel.writeStrongBinder(this.mShellCallback.asBinder());
            return;
        }
    }

    class MyShellCallback
    extends IShellCallback.Stub {
        MyShellCallback() {
        }

        @Override
        public ParcelFileDescriptor openFile(String string2, String string3, String string4) {
            return ShellCallback.this.onOpenFile(string2, string3, string4);
        }
    }

}

