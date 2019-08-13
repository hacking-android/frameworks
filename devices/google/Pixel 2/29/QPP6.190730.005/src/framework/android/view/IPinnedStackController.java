/*
 * Decompiled with CFR 0.145.
 */
package android.view;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IPinnedStackController
extends IInterface {
    public int getDisplayRotation() throws RemoteException;

    public void setIsMinimized(boolean var1) throws RemoteException;

    public void setMinEdgeSize(int var1) throws RemoteException;

    public static class Default
    implements IPinnedStackController {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public int getDisplayRotation() throws RemoteException {
            return 0;
        }

        @Override
        public void setIsMinimized(boolean bl) throws RemoteException {
        }

        @Override
        public void setMinEdgeSize(int n) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IPinnedStackController {
        private static final String DESCRIPTOR = "android.view.IPinnedStackController";
        static final int TRANSACTION_getDisplayRotation = 3;
        static final int TRANSACTION_setIsMinimized = 1;
        static final int TRANSACTION_setMinEdgeSize = 2;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IPinnedStackController asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IPinnedStackController) {
                return (IPinnedStackController)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IPinnedStackController getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            if (n != 1) {
                if (n != 2) {
                    if (n != 3) {
                        return null;
                    }
                    return "getDisplayRotation";
                }
                return "setMinEdgeSize";
            }
            return "setIsMinimized";
        }

        public static boolean setDefaultImpl(IPinnedStackController iPinnedStackController) {
            if (Proxy.sDefaultImpl == null && iPinnedStackController != null) {
                Proxy.sDefaultImpl = iPinnedStackController;
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
                    if (n != 3) {
                        if (n != 1598968902) {
                            return super.onTransact(n, parcel, parcel2, n2);
                        }
                        parcel2.writeString(DESCRIPTOR);
                        return true;
                    }
                    parcel.enforceInterface(DESCRIPTOR);
                    n = this.getDisplayRotation();
                    parcel2.writeNoException();
                    parcel2.writeInt(n);
                    return true;
                }
                parcel.enforceInterface(DESCRIPTOR);
                this.setMinEdgeSize(parcel.readInt());
                return true;
            }
            parcel.enforceInterface(DESCRIPTOR);
            boolean bl = parcel.readInt() != 0;
            this.setIsMinimized(bl);
            return true;
        }

        private static class Proxy
        implements IPinnedStackController {
            public static IPinnedStackController sDefaultImpl;
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override
            public IBinder asBinder() {
                return this.mRemote;
            }

            @Override
            public int getDisplayRotation() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(3, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        int n = Stub.getDefaultImpl().getDisplayRotation();
                        return n;
                    }
                    parcel2.readException();
                    int n = parcel2.readInt();
                    return n;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            @Override
            public void setIsMinimized(boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                int n = bl ? 1 : 0;
                try {
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(1, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setIsMinimized(bl);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void setMinEdgeSize(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(2, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setMinEdgeSize(n);
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

