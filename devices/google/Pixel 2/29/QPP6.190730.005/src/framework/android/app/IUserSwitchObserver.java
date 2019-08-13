/*
 * Decompiled with CFR 0.145.
 */
package android.app;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.IRemoteCallback;
import android.os.Parcel;
import android.os.RemoteException;

public interface IUserSwitchObserver
extends IInterface {
    public void onForegroundProfileSwitch(int var1) throws RemoteException;

    public void onLockedBootComplete(int var1) throws RemoteException;

    public void onUserSwitchComplete(int var1) throws RemoteException;

    public void onUserSwitching(int var1, IRemoteCallback var2) throws RemoteException;

    public static class Default
    implements IUserSwitchObserver {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void onForegroundProfileSwitch(int n) throws RemoteException {
        }

        @Override
        public void onLockedBootComplete(int n) throws RemoteException {
        }

        @Override
        public void onUserSwitchComplete(int n) throws RemoteException {
        }

        @Override
        public void onUserSwitching(int n, IRemoteCallback iRemoteCallback) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IUserSwitchObserver {
        private static final String DESCRIPTOR = "android.app.IUserSwitchObserver";
        static final int TRANSACTION_onForegroundProfileSwitch = 3;
        static final int TRANSACTION_onLockedBootComplete = 4;
        static final int TRANSACTION_onUserSwitchComplete = 2;
        static final int TRANSACTION_onUserSwitching = 1;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IUserSwitchObserver asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IUserSwitchObserver) {
                return (IUserSwitchObserver)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IUserSwitchObserver getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            if (n != 1) {
                if (n != 2) {
                    if (n != 3) {
                        if (n != 4) {
                            return null;
                        }
                        return "onLockedBootComplete";
                    }
                    return "onForegroundProfileSwitch";
                }
                return "onUserSwitchComplete";
            }
            return "onUserSwitching";
        }

        public static boolean setDefaultImpl(IUserSwitchObserver iUserSwitchObserver) {
            if (Proxy.sDefaultImpl == null && iUserSwitchObserver != null) {
                Proxy.sDefaultImpl = iUserSwitchObserver;
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
                        this.onLockedBootComplete(parcel.readInt());
                        return true;
                    }
                    parcel.enforceInterface(DESCRIPTOR);
                    this.onForegroundProfileSwitch(parcel.readInt());
                    return true;
                }
                parcel.enforceInterface(DESCRIPTOR);
                this.onUserSwitchComplete(parcel.readInt());
                return true;
            }
            parcel.enforceInterface(DESCRIPTOR);
            this.onUserSwitching(parcel.readInt(), IRemoteCallback.Stub.asInterface(parcel.readStrongBinder()));
            return true;
        }

        private static class Proxy
        implements IUserSwitchObserver {
            public static IUserSwitchObserver sDefaultImpl;
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
            public void onForegroundProfileSwitch(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(3, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onForegroundProfileSwitch(n);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onLockedBootComplete(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(4, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onLockedBootComplete(n);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onUserSwitchComplete(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(2, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onUserSwitchComplete(n);
                        return;
                    }
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
            public void onUserSwitching(int n, IRemoteCallback iRemoteCallback) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    IBinder iBinder = iRemoteCallback != null ? iRemoteCallback.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (this.mRemote.transact(1, parcel, null, 1)) return;
                    if (Stub.getDefaultImpl() == null) return;
                    Stub.getDefaultImpl().onUserSwitching(n, iRemoteCallback);
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }
        }

    }

}

