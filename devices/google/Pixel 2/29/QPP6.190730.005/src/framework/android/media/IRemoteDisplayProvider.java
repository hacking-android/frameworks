/*
 * Decompiled with CFR 0.145.
 */
package android.media;

import android.media.IRemoteDisplayCallback;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IRemoteDisplayProvider
extends IInterface {
    public void adjustVolume(String var1, int var2) throws RemoteException;

    public void connect(String var1) throws RemoteException;

    public void disconnect(String var1) throws RemoteException;

    public void setCallback(IRemoteDisplayCallback var1) throws RemoteException;

    public void setDiscoveryMode(int var1) throws RemoteException;

    public void setVolume(String var1, int var2) throws RemoteException;

    public static class Default
    implements IRemoteDisplayProvider {
        @Override
        public void adjustVolume(String string2, int n) throws RemoteException {
        }

        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void connect(String string2) throws RemoteException {
        }

        @Override
        public void disconnect(String string2) throws RemoteException {
        }

        @Override
        public void setCallback(IRemoteDisplayCallback iRemoteDisplayCallback) throws RemoteException {
        }

        @Override
        public void setDiscoveryMode(int n) throws RemoteException {
        }

        @Override
        public void setVolume(String string2, int n) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IRemoteDisplayProvider {
        private static final String DESCRIPTOR = "android.media.IRemoteDisplayProvider";
        static final int TRANSACTION_adjustVolume = 6;
        static final int TRANSACTION_connect = 3;
        static final int TRANSACTION_disconnect = 4;
        static final int TRANSACTION_setCallback = 1;
        static final int TRANSACTION_setDiscoveryMode = 2;
        static final int TRANSACTION_setVolume = 5;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IRemoteDisplayProvider asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IRemoteDisplayProvider) {
                return (IRemoteDisplayProvider)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IRemoteDisplayProvider getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            switch (n) {
                default: {
                    return null;
                }
                case 6: {
                    return "adjustVolume";
                }
                case 5: {
                    return "setVolume";
                }
                case 4: {
                    return "disconnect";
                }
                case 3: {
                    return "connect";
                }
                case 2: {
                    return "setDiscoveryMode";
                }
                case 1: 
            }
            return "setCallback";
        }

        public static boolean setDefaultImpl(IRemoteDisplayProvider iRemoteDisplayProvider) {
            if (Proxy.sDefaultImpl == null && iRemoteDisplayProvider != null) {
                Proxy.sDefaultImpl = iRemoteDisplayProvider;
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
            if (n != 1598968902) {
                switch (n) {
                    default: {
                        return super.onTransact(n, parcel, parcel2, n2);
                    }
                    case 6: {
                        parcel.enforceInterface(DESCRIPTOR);
                        this.adjustVolume(parcel.readString(), parcel.readInt());
                        return true;
                    }
                    case 5: {
                        parcel.enforceInterface(DESCRIPTOR);
                        this.setVolume(parcel.readString(), parcel.readInt());
                        return true;
                    }
                    case 4: {
                        parcel.enforceInterface(DESCRIPTOR);
                        this.disconnect(parcel.readString());
                        return true;
                    }
                    case 3: {
                        parcel.enforceInterface(DESCRIPTOR);
                        this.connect(parcel.readString());
                        return true;
                    }
                    case 2: {
                        parcel.enforceInterface(DESCRIPTOR);
                        this.setDiscoveryMode(parcel.readInt());
                        return true;
                    }
                    case 1: 
                }
                parcel.enforceInterface(DESCRIPTOR);
                this.setCallback(IRemoteDisplayCallback.Stub.asInterface(parcel.readStrongBinder()));
                return true;
            }
            parcel2.writeString(DESCRIPTOR);
            return true;
        }

        private static class Proxy
        implements IRemoteDisplayProvider {
            public static IRemoteDisplayProvider sDefaultImpl;
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override
            public void adjustVolume(String string2, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(6, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().adjustVolume(string2, n);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public IBinder asBinder() {
                return this.mRemote;
            }

            @Override
            public void connect(String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(3, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().connect(string2);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void disconnect(String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(4, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().disconnect(string2);
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

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void setCallback(IRemoteDisplayCallback iRemoteDisplayCallback) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iRemoteDisplayCallback != null ? iRemoteDisplayCallback.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (this.mRemote.transact(1, parcel, null, 1)) return;
                    if (Stub.getDefaultImpl() == null) return;
                    Stub.getDefaultImpl().setCallback(iRemoteDisplayCallback);
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void setDiscoveryMode(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(2, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setDiscoveryMode(n);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void setVolume(String string2, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(5, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setVolume(string2, n);
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

