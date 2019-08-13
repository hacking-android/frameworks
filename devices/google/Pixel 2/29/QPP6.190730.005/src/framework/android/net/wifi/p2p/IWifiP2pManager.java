/*
 * Decompiled with CFR 0.145.
 */
package android.net.wifi.p2p;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Messenger;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;

public interface IWifiP2pManager
extends IInterface {
    public void checkConfigureWifiDisplayPermission() throws RemoteException;

    public void close(IBinder var1) throws RemoteException;

    public Messenger getMessenger(IBinder var1) throws RemoteException;

    public Messenger getP2pStateMachineMessenger() throws RemoteException;

    public void setMiracastMode(int var1) throws RemoteException;

    public static class Default
    implements IWifiP2pManager {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void checkConfigureWifiDisplayPermission() throws RemoteException {
        }

        @Override
        public void close(IBinder iBinder) throws RemoteException {
        }

        @Override
        public Messenger getMessenger(IBinder iBinder) throws RemoteException {
            return null;
        }

        @Override
        public Messenger getP2pStateMachineMessenger() throws RemoteException {
            return null;
        }

        @Override
        public void setMiracastMode(int n) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IWifiP2pManager {
        private static final String DESCRIPTOR = "android.net.wifi.p2p.IWifiP2pManager";
        static final int TRANSACTION_checkConfigureWifiDisplayPermission = 5;
        static final int TRANSACTION_close = 3;
        static final int TRANSACTION_getMessenger = 1;
        static final int TRANSACTION_getP2pStateMachineMessenger = 2;
        static final int TRANSACTION_setMiracastMode = 4;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IWifiP2pManager asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IWifiP2pManager) {
                return (IWifiP2pManager)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IWifiP2pManager getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            if (n != 1) {
                if (n != 2) {
                    if (n != 3) {
                        if (n != 4) {
                            if (n != 5) {
                                return null;
                            }
                            return "checkConfigureWifiDisplayPermission";
                        }
                        return "setMiracastMode";
                    }
                    return "close";
                }
                return "getP2pStateMachineMessenger";
            }
            return "getMessenger";
        }

        public static boolean setDefaultImpl(IWifiP2pManager iWifiP2pManager) {
            if (Proxy.sDefaultImpl == null && iWifiP2pManager != null) {
                Proxy.sDefaultImpl = iWifiP2pManager;
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
                    if (n != 3) {
                        if (n != 4) {
                            if (n != 5) {
                                if (n != 1598968902) {
                                    return super.onTransact(n, (Parcel)object, parcel, n2);
                                }
                                parcel.writeString(DESCRIPTOR);
                                return true;
                            }
                            ((Parcel)object).enforceInterface(DESCRIPTOR);
                            this.checkConfigureWifiDisplayPermission();
                            parcel.writeNoException();
                            return true;
                        }
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.setMiracastMode(((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    ((Parcel)object).enforceInterface(DESCRIPTOR);
                    this.close(((Parcel)object).readStrongBinder());
                    return true;
                }
                ((Parcel)object).enforceInterface(DESCRIPTOR);
                object = this.getP2pStateMachineMessenger();
                parcel.writeNoException();
                if (object != null) {
                    parcel.writeInt(1);
                    ((Messenger)object).writeToParcel(parcel, 1);
                } else {
                    parcel.writeInt(0);
                }
                return true;
            }
            ((Parcel)object).enforceInterface(DESCRIPTOR);
            object = this.getMessenger(((Parcel)object).readStrongBinder());
            parcel.writeNoException();
            if (object != null) {
                parcel.writeInt(1);
                ((Messenger)object).writeToParcel(parcel, 1);
            } else {
                parcel.writeInt(0);
            }
            return true;
        }

        private static class Proxy
        implements IWifiP2pManager {
            public static IWifiP2pManager sDefaultImpl;
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override
            public IBinder asBinder() {
                return this.mRemote;
            }

            @Override
            public void checkConfigureWifiDisplayPermission() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(5, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().checkConfigureWifiDisplayPermission();
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
            public void close(IBinder iBinder) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeStrongBinder(iBinder);
                    if (!this.mRemote.transact(3, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().close(iBinder);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            @Override
            public Messenger getMessenger(IBinder object) throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                block3 : {
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    try {
                        parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel.writeStrongBinder((IBinder)object);
                        if (this.mRemote.transact(1, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block3;
                        object = Stub.getDefaultImpl().getMessenger((IBinder)object);
                        parcel2.recycle();
                        parcel.recycle();
                        return object;
                    }
                    catch (Throwable throwable) {
                        parcel2.recycle();
                        parcel.recycle();
                        throw throwable;
                    }
                }
                parcel2.readException();
                object = parcel2.readInt() != 0 ? Messenger.CREATOR.createFromParcel(parcel2) : null;
                parcel2.recycle();
                parcel.recycle();
                return object;
            }

            @Override
            public Messenger getP2pStateMachineMessenger() throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                block3 : {
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        if (this.mRemote.transact(2, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block3;
                        Messenger messenger = Stub.getDefaultImpl().getP2pStateMachineMessenger();
                        parcel.recycle();
                        parcel2.recycle();
                        return messenger;
                    }
                    catch (Throwable throwable) {
                        parcel.recycle();
                        parcel2.recycle();
                        throw throwable;
                    }
                }
                parcel.readException();
                Messenger messenger = parcel.readInt() != 0 ? Messenger.CREATOR.createFromParcel(parcel) : null;
                parcel.recycle();
                parcel2.recycle();
                return messenger;
            }

            @Override
            public void setMiracastMode(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(4, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setMiracastMode(n);
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

