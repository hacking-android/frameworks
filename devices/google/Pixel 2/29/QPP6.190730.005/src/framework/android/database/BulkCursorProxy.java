/*
 * Decompiled with CFR 0.145.
 */
package android.database;

import android.annotation.UnsupportedAppUsage;
import android.database.CursorWindow;
import android.database.DatabaseUtils;
import android.database.IBulkCursor;
import android.database.IContentObserver;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

final class BulkCursorProxy
implements IBulkCursor {
    private Bundle mExtras;
    @UnsupportedAppUsage
    private IBinder mRemote;

    public BulkCursorProxy(IBinder iBinder) {
        this.mRemote = iBinder;
        this.mExtras = null;
    }

    @Override
    public IBinder asBinder() {
        return this.mRemote;
    }

    @Override
    public void close() throws RemoteException {
        Parcel parcel = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
            parcel.writeInterfaceToken("android.content.IBulkCursor");
            this.mRemote.transact(7, parcel, parcel2, 0);
            DatabaseUtils.readExceptionFromParcel(parcel2);
            return;
        }
        finally {
            parcel.recycle();
            parcel2.recycle();
        }
    }

    @Override
    public void deactivate() throws RemoteException {
        Parcel parcel = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
            parcel.writeInterfaceToken("android.content.IBulkCursor");
            this.mRemote.transact(2, parcel, parcel2, 0);
            DatabaseUtils.readExceptionFromParcel(parcel2);
            return;
        }
        finally {
            parcel.recycle();
            parcel2.recycle();
        }
    }

    @Override
    public Bundle getExtras() throws RemoteException {
        if (this.mExtras == null) {
            Parcel parcel = Parcel.obtain();
            Parcel parcel2 = Parcel.obtain();
            try {
                parcel.writeInterfaceToken("android.content.IBulkCursor");
                this.mRemote.transact(5, parcel, parcel2, 0);
                DatabaseUtils.readExceptionFromParcel(parcel2);
                this.mExtras = parcel2.readBundle();
            }
            finally {
                parcel.recycle();
                parcel2.recycle();
            }
        }
        return this.mExtras;
    }

    @Override
    public CursorWindow getWindow(int n) throws RemoteException {
        Parcel parcel = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
            parcel.writeInterfaceToken("android.content.IBulkCursor");
            parcel.writeInt(n);
            this.mRemote.transact(1, parcel, parcel2, 0);
            DatabaseUtils.readExceptionFromParcel(parcel2);
            CursorWindow cursorWindow = null;
            if (parcel2.readInt() == 1) {
                cursorWindow = CursorWindow.newFromParcel(parcel2);
            }
            return cursorWindow;
        }
        finally {
            parcel.recycle();
            parcel2.recycle();
        }
    }

    @Override
    public void onMove(int n) throws RemoteException {
        Parcel parcel = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
            parcel.writeInterfaceToken("android.content.IBulkCursor");
            parcel.writeInt(n);
            this.mRemote.transact(4, parcel, parcel2, 0);
            DatabaseUtils.readExceptionFromParcel(parcel2);
            return;
        }
        finally {
            parcel.recycle();
            parcel2.recycle();
        }
    }

    @Override
    public int requery(IContentObserver iContentObserver) throws RemoteException {
        int n;
        Parcel parcel;
        Parcel parcel2;
        block4 : {
            parcel = Parcel.obtain();
            parcel2 = Parcel.obtain();
            parcel.writeInterfaceToken("android.content.IBulkCursor");
            parcel.writeStrongInterface(iContentObserver);
            boolean bl = this.mRemote.transact(3, parcel, parcel2, 0);
            DatabaseUtils.readExceptionFromParcel(parcel2);
            if (bl) break block4;
            n = -1;
        }
        n = parcel2.readInt();
        this.mExtras = parcel2.readBundle();
        return n;
        finally {
            parcel.recycle();
            parcel2.recycle();
        }
    }

    @Override
    public Bundle respond(Bundle bundle) throws RemoteException {
        Parcel parcel = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
            parcel.writeInterfaceToken("android.content.IBulkCursor");
            parcel.writeBundle(bundle);
            this.mRemote.transact(6, parcel, parcel2, 0);
            DatabaseUtils.readExceptionFromParcel(parcel2);
            bundle = parcel2.readBundle();
            return bundle;
        }
        finally {
            parcel.recycle();
            parcel2.recycle();
        }
    }
}

