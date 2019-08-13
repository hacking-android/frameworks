/*
 * Decompiled with CFR 0.145.
 */
package android.net.lowpan;

import android.net.lowpan.ILowpanInterface;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface ILowpanManagerListener
extends IInterface {
    public void onInterfaceAdded(ILowpanInterface var1) throws RemoteException;

    public void onInterfaceRemoved(ILowpanInterface var1) throws RemoteException;

    public static class Default
    implements ILowpanManagerListener {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void onInterfaceAdded(ILowpanInterface iLowpanInterface) throws RemoteException {
        }

        @Override
        public void onInterfaceRemoved(ILowpanInterface iLowpanInterface) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements ILowpanManagerListener {
        private static final String DESCRIPTOR = "android.net.lowpan.ILowpanManagerListener";
        static final int TRANSACTION_onInterfaceAdded = 1;
        static final int TRANSACTION_onInterfaceRemoved = 2;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static ILowpanManagerListener asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof ILowpanManagerListener) {
                return (ILowpanManagerListener)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static ILowpanManagerListener getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            if (n != 1) {
                if (n != 2) {
                    return null;
                }
                return "onInterfaceRemoved";
            }
            return "onInterfaceAdded";
        }

        public static boolean setDefaultImpl(ILowpanManagerListener iLowpanManagerListener) {
            if (Proxy.sDefaultImpl == null && iLowpanManagerListener != null) {
                Proxy.sDefaultImpl = iLowpanManagerListener;
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
                if (n != 2) {
                    if (n != 1598968902) {
                        return super.onTransact(n, parcel, parcel2, n2);
                    }
                    parcel2.writeString(DESCRIPTOR);
                    return true;
                }
                parcel.enforceInterface(DESCRIPTOR);
                this.onInterfaceRemoved(ILowpanInterface.Stub.asInterface(parcel.readStrongBinder()));
                return true;
            }
            parcel.enforceInterface(DESCRIPTOR);
            this.onInterfaceAdded(ILowpanInterface.Stub.asInterface(parcel.readStrongBinder()));
            return true;
        }

        private static class Proxy
        implements ILowpanManagerListener {
            public static ILowpanManagerListener sDefaultImpl;
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

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void onInterfaceAdded(ILowpanInterface iLowpanInterface) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iLowpanInterface != null ? iLowpanInterface.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (this.mRemote.transact(1, parcel, null, 1)) return;
                    if (Stub.getDefaultImpl() == null) return;
                    Stub.getDefaultImpl().onInterfaceAdded(iLowpanInterface);
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void onInterfaceRemoved(ILowpanInterface iLowpanInterface) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iLowpanInterface != null ? iLowpanInterface.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (this.mRemote.transact(2, parcel, null, 1)) return;
                    if (Stub.getDefaultImpl() == null) return;
                    Stub.getDefaultImpl().onInterfaceRemoved(iLowpanInterface);
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }
        }

    }

}

