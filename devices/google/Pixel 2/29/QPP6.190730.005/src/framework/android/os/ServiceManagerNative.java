/*
 * Decompiled with CFR 0.145.
 */
package android.os;

import android.annotation.UnsupportedAppUsage;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.IPermissionController;
import android.os.IServiceManager;
import android.os.Parcel;
import android.os.RemoteException;
import android.os.ServiceManagerProxy;

public abstract class ServiceManagerNative
extends Binder
implements IServiceManager {
    public ServiceManagerNative() {
        this.attachInterface(this, "android.os.IServiceManager");
    }

    @UnsupportedAppUsage
    public static IServiceManager asInterface(IBinder iBinder) {
        if (iBinder == null) {
            return null;
        }
        IServiceManager iServiceManager = (IServiceManager)iBinder.queryLocalInterface("android.os.IServiceManager");
        if (iServiceManager != null) {
            return iServiceManager;
        }
        return new ServiceManagerProxy(iBinder);
    }

    @Override
    public IBinder asBinder() {
        return this;
    }

    @Override
    public boolean onTransact(int n, Parcel parcel, Parcel object, int n2) {
        block11 : {
            block7 : {
                block8 : {
                    block9 : {
                        block10 : {
                            if (n == 1) break block7;
                            if (n == 2) break block8;
                            if (n == 3) break block9;
                            if (n == 4) break block10;
                            if (n != 6) break block11;
                            parcel.enforceInterface("android.os.IServiceManager");
                            this.setPermissionController(IPermissionController.Stub.asInterface(parcel.readStrongBinder()));
                            return true;
                        }
                        parcel.enforceInterface("android.os.IServiceManager");
                        ((Parcel)object).writeStringArray(this.listServices(parcel.readInt()));
                        return true;
                    }
                    parcel.enforceInterface("android.os.IServiceManager");
                    object = parcel.readString();
                    IBinder iBinder = parcel.readStrongBinder();
                    boolean bl = parcel.readInt() != 0;
                    this.addService((String)object, iBinder, bl, parcel.readInt());
                    return true;
                }
                parcel.enforceInterface("android.os.IServiceManager");
                ((Parcel)object).writeStrongBinder(this.checkService(parcel.readString()));
                return true;
            }
            try {
                parcel.enforceInterface("android.os.IServiceManager");
                ((Parcel)object).writeStrongBinder(this.getService(parcel.readString()));
                return true;
            }
            catch (RemoteException remoteException) {
                // empty catch block
            }
        }
        return false;
    }
}

