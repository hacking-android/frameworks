/*
 * Decompiled with CFR 0.145.
 */
package android.view;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IWindowFocusObserver
extends IInterface {
    public void focusGained(IBinder var1) throws RemoteException;

    public void focusLost(IBinder var1) throws RemoteException;

    public static class Default
    implements IWindowFocusObserver {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void focusGained(IBinder iBinder) throws RemoteException {
        }

        @Override
        public void focusLost(IBinder iBinder) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IWindowFocusObserver {
        private static final String DESCRIPTOR = "android.view.IWindowFocusObserver";
        static final int TRANSACTION_focusGained = 1;
        static final int TRANSACTION_focusLost = 2;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IWindowFocusObserver asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IWindowFocusObserver) {
                return (IWindowFocusObserver)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IWindowFocusObserver getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            if (n != 1) {
                if (n != 2) {
                    return null;
                }
                return "focusLost";
            }
            return "focusGained";
        }

        public static boolean setDefaultImpl(IWindowFocusObserver iWindowFocusObserver) {
            if (Proxy.sDefaultImpl == null && iWindowFocusObserver != null) {
                Proxy.sDefaultImpl = iWindowFocusObserver;
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
                this.focusLost(parcel.readStrongBinder());
                parcel2.writeNoException();
                return true;
            }
            parcel.enforceInterface(DESCRIPTOR);
            this.focusGained(parcel.readStrongBinder());
            parcel2.writeNoException();
            return true;
        }

        private static class Proxy
        implements IWindowFocusObserver {
            public static IWindowFocusObserver sDefaultImpl;
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override
            public IBinder asBinder() {
                return this.mRemote;
            }

            @Override
            public void focusGained(IBinder iBinder) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeStrongBinder(iBinder);
                    if (!this.mRemote.transact(1, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().focusGained(iBinder);
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

            @Override
            public void focusLost(IBinder iBinder) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeStrongBinder(iBinder);
                    if (!this.mRemote.transact(2, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().focusLost(iBinder);
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

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }
        }

    }

}

