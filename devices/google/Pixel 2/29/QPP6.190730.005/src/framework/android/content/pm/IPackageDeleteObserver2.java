/*
 * Decompiled with CFR 0.145.
 */
package android.content.pm;

import android.annotation.UnsupportedAppUsage;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;

public interface IPackageDeleteObserver2
extends IInterface {
    @UnsupportedAppUsage
    public void onPackageDeleted(String var1, int var2, String var3) throws RemoteException;

    public void onUserActionRequired(Intent var1) throws RemoteException;

    public static class Default
    implements IPackageDeleteObserver2 {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void onPackageDeleted(String string2, int n, String string3) throws RemoteException {
        }

        @Override
        public void onUserActionRequired(Intent intent) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IPackageDeleteObserver2 {
        private static final String DESCRIPTOR = "android.content.pm.IPackageDeleteObserver2";
        static final int TRANSACTION_onPackageDeleted = 2;
        static final int TRANSACTION_onUserActionRequired = 1;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IPackageDeleteObserver2 asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IPackageDeleteObserver2) {
                return (IPackageDeleteObserver2)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IPackageDeleteObserver2 getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            if (n != 1) {
                if (n != 2) {
                    return null;
                }
                return "onPackageDeleted";
            }
            return "onUserActionRequired";
        }

        public static boolean setDefaultImpl(IPackageDeleteObserver2 iPackageDeleteObserver2) {
            if (Proxy.sDefaultImpl == null && iPackageDeleteObserver2 != null) {
                Proxy.sDefaultImpl = iPackageDeleteObserver2;
                return true;
            }
            return false;
        }

        @Override
        public IBinder asBinder() {
            return this;
        }

        @Override
        public String getTransactionName(int n) {
            return Stub.getDefaultTransactionName(n);
        }

        @Override
        public boolean onTransact(int n, Parcel object, Parcel parcel, int n2) throws RemoteException {
            if (n != 1) {
                if (n != 2) {
                    if (n != 1598968902) {
                        return super.onTransact(n, (Parcel)object, parcel, n2);
                    }
                    parcel.writeString(DESCRIPTOR);
                    return true;
                }
                ((Parcel)object).enforceInterface(DESCRIPTOR);
                this.onPackageDeleted(((Parcel)object).readString(), ((Parcel)object).readInt(), ((Parcel)object).readString());
                return true;
            }
            ((Parcel)object).enforceInterface(DESCRIPTOR);
            object = ((Parcel)object).readInt() != 0 ? Intent.CREATOR.createFromParcel((Parcel)object) : null;
            this.onUserActionRequired((Intent)object);
            return true;
        }

        private static class Proxy
        implements IPackageDeleteObserver2 {
            public static IPackageDeleteObserver2 sDefaultImpl;
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            @Override
            public void onPackageDeleted(String string2, int n, String string3) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    parcel.writeInt(n);
                    parcel.writeString(string3);
                    if (!this.mRemote.transact(2, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onPackageDeleted(string2, n, string3);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onUserActionRequired(Intent intent) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (intent != null) {
                        parcel.writeInt(1);
                        intent.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(1, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onUserActionRequired(intent);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }
        }

    }

}

