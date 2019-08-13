/*
 * Decompiled with CFR 0.145.
 */
package android.os;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IMaintenanceActivityListener
extends IInterface {
    public void onMaintenanceActivityChanged(boolean var1) throws RemoteException;

    public static class Default
    implements IMaintenanceActivityListener {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void onMaintenanceActivityChanged(boolean bl) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IMaintenanceActivityListener {
        private static final String DESCRIPTOR = "android.os.IMaintenanceActivityListener";
        static final int TRANSACTION_onMaintenanceActivityChanged = 1;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IMaintenanceActivityListener asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IMaintenanceActivityListener) {
                return (IMaintenanceActivityListener)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IMaintenanceActivityListener getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            if (n != 1) {
                return null;
            }
            return "onMaintenanceActivityChanged";
        }

        public static boolean setDefaultImpl(IMaintenanceActivityListener iMaintenanceActivityListener) {
            if (Proxy.sDefaultImpl == null && iMaintenanceActivityListener != null) {
                Proxy.sDefaultImpl = iMaintenanceActivityListener;
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
        public boolean onTransact(int n, Parcel parcel, Parcel parcel2, int n2) throws RemoteException {
            if (n != 1) {
                if (n != 1598968902) {
                    return super.onTransact(n, parcel, parcel2, n2);
                }
                parcel2.writeString(DESCRIPTOR);
                return true;
            }
            parcel.enforceInterface(DESCRIPTOR);
            boolean bl = parcel.readInt() != 0;
            this.onMaintenanceActivityChanged(bl);
            return true;
        }

        private static class Proxy
        implements IMaintenanceActivityListener {
            public static IMaintenanceActivityListener sDefaultImpl;
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
            public void onMaintenanceActivityChanged(boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                int n = bl ? 1 : 0;
                try {
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(1, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onMaintenanceActivityChanged(bl);
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

