/*
 * Decompiled with CFR 0.145.
 */
package android.database;

import android.database.BulkCursorProxy;
import android.database.CursorWindow;
import android.database.DatabaseUtils;
import android.database.IBulkCursor;
import android.database.IContentObserver;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public abstract class BulkCursorNative
extends Binder
implements IBulkCursor {
    public BulkCursorNative() {
        this.attachInterface(this, "android.content.IBulkCursor");
    }

    public static IBulkCursor asInterface(IBinder iBinder) {
        if (iBinder == null) {
            return null;
        }
        IBulkCursor iBulkCursor = (IBulkCursor)iBinder.queryLocalInterface("android.content.IBulkCursor");
        if (iBulkCursor != null) {
            return iBulkCursor;
        }
        return new BulkCursorProxy(iBinder);
    }

    @Override
    public IBinder asBinder() {
        return this;
    }

    /*
     * Unable to fully structure code
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public boolean onTransact(int var1_1, Parcel var2_2, Parcel var3_4, int var4_5) throws RemoteException {
        switch (var1_1) {
            default: {
                return super.onTransact(var1_1, (Parcel)var2_2, var3_4, var4_5);
            }
            case 7: {
                var2_2.enforceInterface("android.content.IBulkCursor");
                this.close();
                var3_4.writeNoException();
                return true;
            }
            case 6: {
                var2_2.enforceInterface("android.content.IBulkCursor");
                var2_2 = this.respond(var2_2.readBundle());
                var3_4.writeNoException();
                var3_4.writeBundle((Bundle)var2_2);
                return true;
            }
            case 5: {
                var2_2.enforceInterface("android.content.IBulkCursor");
                var2_2 = this.getExtras();
                var3_4.writeNoException();
                var3_4.writeBundle((Bundle)var2_2);
                return true;
            }
            case 4: {
                var2_2.enforceInterface("android.content.IBulkCursor");
                this.onMove(var2_2.readInt());
                var3_4.writeNoException();
                return true;
            }
            case 3: {
                var2_2.enforceInterface("android.content.IBulkCursor");
                var1_1 = this.requery(IContentObserver.Stub.asInterface(var2_2.readStrongBinder()));
                var3_4.writeNoException();
                var3_4.writeInt(var1_1);
                var3_4.writeBundle(this.getExtras());
                return true;
            }
            case 2: {
                var2_2.enforceInterface("android.content.IBulkCursor");
                this.deactivate();
                var3_4.writeNoException();
                return true;
            }
            case 1: {
                try {
                    var2_2.enforceInterface("android.content.IBulkCursor");
                    var2_2 = this.getWindow(var2_2.readInt());
                    var3_4.writeNoException();
                    if (var2_2 != null) ** GOTO lbl56
                }
                catch (Exception var2_3) {
                    DatabaseUtils.writeExceptionToParcel(var3_4, var2_3);
                    return true;
                }
                var3_4.writeInt(0);
                return true;
lbl56: // 1 sources:
                var3_4.writeInt(1);
                var2_2.writeToParcel(var3_4, 1);
                return true;
            }
        }
    }
}

