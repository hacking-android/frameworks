/*
 * Decompiled with CFR 0.145.
 */
package android.os;

import android.annotation.UnsupportedAppUsage;
import android.os.IBinder;
import android.os.IPermissionController;
import android.os.IServiceManager;
import android.os.Parcel;
import android.os.RemoteException;
import java.util.ArrayList;

class ServiceManagerProxy
implements IServiceManager {
    @UnsupportedAppUsage
    private IBinder mRemote;

    public ServiceManagerProxy(IBinder iBinder) {
        this.mRemote = iBinder;
    }

    @Override
    public void addService(String string2, IBinder iBinder, boolean bl, int n) throws RemoteException {
        Parcel parcel = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        parcel.writeInterfaceToken("android.os.IServiceManager");
        parcel.writeString(string2);
        parcel.writeStrongBinder(iBinder);
        parcel.writeInt((int)bl);
        parcel.writeInt(n);
        this.mRemote.transact(3, parcel, parcel2, 0);
        parcel2.recycle();
        parcel.recycle();
    }

    @Override
    public IBinder asBinder() {
        return this.mRemote;
    }

    @Override
    public IBinder checkService(String object) throws RemoteException {
        Parcel parcel = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        parcel.writeInterfaceToken("android.os.IServiceManager");
        parcel.writeString((String)object);
        this.mRemote.transact(2, parcel, parcel2, 0);
        object = parcel2.readStrongBinder();
        parcel2.recycle();
        parcel.recycle();
        return object;
    }

    @UnsupportedAppUsage
    @Override
    public IBinder getService(String object) throws RemoteException {
        Parcel parcel = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        parcel.writeInterfaceToken("android.os.IServiceManager");
        parcel.writeString((String)object);
        this.mRemote.transact(1, parcel, parcel2, 0);
        object = parcel2.readStrongBinder();
        parcel2.recycle();
        parcel.recycle();
        return object;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    @Override
    public String[] listServices(int var1_1) throws RemoteException {
        var2_2 = new ArrayList<String>();
        var3_3 = 0;
        do {
            var4_4 = Parcel.obtain();
            var5_5 = Parcel.obtain();
            var4_4.writeInterfaceToken("android.os.IServiceManager");
            var4_4.writeInt(var3_3);
            var4_4.writeInt(var1_1);
            ++var3_3;
            var6_7 = this.mRemote.transact(4, var4_4, (Parcel)var5_5, 0);
            ** if (!var6_7) goto lbl-1000
lbl-1000: // 1 sources:
            {
                var2_2.add(var5_5.readString());
                var5_5.recycle();
                var4_4.recycle();
                continue;
            }
lbl-1000: // 1 sources:
            {
            }
            break;
        } while (true);
        catch (RuntimeException var5_6) {
            // empty catch block
        }
        var5_5 = new String[var2_2.size()];
        var2_2.toArray(var5_5);
        return var5_5;
    }

    @Override
    public void setPermissionController(IPermissionController iPermissionController) throws RemoteException {
        Parcel parcel = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        parcel.writeInterfaceToken("android.os.IServiceManager");
        parcel.writeStrongBinder(iPermissionController.asBinder());
        this.mRemote.transact(6, parcel, parcel2, 0);
        parcel2.recycle();
        parcel.recycle();
    }
}

