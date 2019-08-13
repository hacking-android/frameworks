/*
 * Decompiled with CFR 0.145.
 */
package android.os;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.PersistableBundle;
import android.os.RemoteException;

public interface ISystemUpdateManager
extends IInterface {
    public Bundle retrieveSystemUpdateInfo() throws RemoteException;

    public void updateSystemUpdateInfo(PersistableBundle var1) throws RemoteException;

    public static class Default
    implements ISystemUpdateManager {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public Bundle retrieveSystemUpdateInfo() throws RemoteException {
            return null;
        }

        @Override
        public void updateSystemUpdateInfo(PersistableBundle persistableBundle) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements ISystemUpdateManager {
        private static final String DESCRIPTOR = "android.os.ISystemUpdateManager";
        static final int TRANSACTION_retrieveSystemUpdateInfo = 1;
        static final int TRANSACTION_updateSystemUpdateInfo = 2;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static ISystemUpdateManager asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof ISystemUpdateManager) {
                return (ISystemUpdateManager)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static ISystemUpdateManager getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            if (n != 1) {
                if (n != 2) {
                    return null;
                }
                return "updateSystemUpdateInfo";
            }
            return "retrieveSystemUpdateInfo";
        }

        public static boolean setDefaultImpl(ISystemUpdateManager iSystemUpdateManager) {
            if (Proxy.sDefaultImpl == null && iSystemUpdateManager != null) {
                Proxy.sDefaultImpl = iSystemUpdateManager;
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
                object = ((Parcel)object).readInt() != 0 ? PersistableBundle.CREATOR.createFromParcel((Parcel)object) : null;
                this.updateSystemUpdateInfo((PersistableBundle)object);
                parcel.writeNoException();
                return true;
            }
            ((Parcel)object).enforceInterface(DESCRIPTOR);
            object = this.retrieveSystemUpdateInfo();
            parcel.writeNoException();
            if (object != null) {
                parcel.writeInt(1);
                ((Bundle)object).writeToParcel(parcel, 1);
            } else {
                parcel.writeInt(0);
            }
            return true;
        }

        private static class Proxy
        implements ISystemUpdateManager {
            public static ISystemUpdateManager sDefaultImpl;
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
            public Bundle retrieveSystemUpdateInfo() throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                block3 : {
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        if (this.mRemote.transact(1, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block3;
                        Bundle bundle = Stub.getDefaultImpl().retrieveSystemUpdateInfo();
                        parcel.recycle();
                        parcel2.recycle();
                        return bundle;
                    }
                    catch (Throwable throwable) {
                        parcel.recycle();
                        parcel2.recycle();
                        throw throwable;
                    }
                }
                parcel.readException();
                Bundle bundle = parcel.readInt() != 0 ? Bundle.CREATOR.createFromParcel(parcel) : null;
                parcel.recycle();
                parcel2.recycle();
                return bundle;
            }

            @Override
            public void updateSystemUpdateInfo(PersistableBundle persistableBundle) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (persistableBundle != null) {
                        parcel.writeInt(1);
                        persistableBundle.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(2, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().updateSystemUpdateInfo(persistableBundle);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }
        }

    }

}

