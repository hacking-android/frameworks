/*
 * Decompiled with CFR 0.145.
 */
package android.net;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface ISocketKeepaliveCallback
extends IInterface {
    public void onDataReceived() throws RemoteException;

    public void onError(int var1) throws RemoteException;

    public void onStarted(int var1) throws RemoteException;

    public void onStopped() throws RemoteException;

    public static class Default
    implements ISocketKeepaliveCallback {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void onDataReceived() throws RemoteException {
        }

        @Override
        public void onError(int n) throws RemoteException {
        }

        @Override
        public void onStarted(int n) throws RemoteException {
        }

        @Override
        public void onStopped() throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements ISocketKeepaliveCallback {
        private static final String DESCRIPTOR = "android.net.ISocketKeepaliveCallback";
        static final int TRANSACTION_onDataReceived = 4;
        static final int TRANSACTION_onError = 3;
        static final int TRANSACTION_onStarted = 1;
        static final int TRANSACTION_onStopped = 2;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static ISocketKeepaliveCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof ISocketKeepaliveCallback) {
                return (ISocketKeepaliveCallback)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static ISocketKeepaliveCallback getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            if (n != 1) {
                if (n != 2) {
                    if (n != 3) {
                        if (n != 4) {
                            return null;
                        }
                        return "onDataReceived";
                    }
                    return "onError";
                }
                return "onStopped";
            }
            return "onStarted";
        }

        public static boolean setDefaultImpl(ISocketKeepaliveCallback iSocketKeepaliveCallback) {
            if (Proxy.sDefaultImpl == null && iSocketKeepaliveCallback != null) {
                Proxy.sDefaultImpl = iSocketKeepaliveCallback;
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
                        if (n != 4) {
                            if (n != 1598968902) {
                                return super.onTransact(n, parcel, parcel2, n2);
                            }
                            parcel2.writeString(DESCRIPTOR);
                            return true;
                        }
                        parcel.enforceInterface(DESCRIPTOR);
                        this.onDataReceived();
                        return true;
                    }
                    parcel.enforceInterface(DESCRIPTOR);
                    this.onError(parcel.readInt());
                    return true;
                }
                parcel.enforceInterface(DESCRIPTOR);
                this.onStopped();
                return true;
            }
            parcel.enforceInterface(DESCRIPTOR);
            this.onStarted(parcel.readInt());
            return true;
        }

        private static class Proxy
        implements ISocketKeepaliveCallback {
            public static ISocketKeepaliveCallback sDefaultImpl;
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
            public void onDataReceived() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(4, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onDataReceived();
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onError(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(3, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onError(n);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onStarted(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(1, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onStarted(n);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onStopped() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(2, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onStopped();
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

