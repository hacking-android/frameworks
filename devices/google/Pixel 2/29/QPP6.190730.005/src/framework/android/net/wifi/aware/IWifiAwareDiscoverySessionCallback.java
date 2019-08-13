/*
 * Decompiled with CFR 0.145.
 */
package android.net.wifi.aware;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IWifiAwareDiscoverySessionCallback
extends IInterface {
    public void onMatch(int var1, byte[] var2, byte[] var3) throws RemoteException;

    public void onMatchWithDistance(int var1, byte[] var2, byte[] var3, int var4) throws RemoteException;

    public void onMessageReceived(int var1, byte[] var2) throws RemoteException;

    public void onMessageSendFail(int var1, int var2) throws RemoteException;

    public void onMessageSendSuccess(int var1) throws RemoteException;

    public void onSessionConfigFail(int var1) throws RemoteException;

    public void onSessionConfigSuccess() throws RemoteException;

    public void onSessionStarted(int var1) throws RemoteException;

    public void onSessionTerminated(int var1) throws RemoteException;

    public static class Default
    implements IWifiAwareDiscoverySessionCallback {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void onMatch(int n, byte[] arrby, byte[] arrby2) throws RemoteException {
        }

        @Override
        public void onMatchWithDistance(int n, byte[] arrby, byte[] arrby2, int n2) throws RemoteException {
        }

        @Override
        public void onMessageReceived(int n, byte[] arrby) throws RemoteException {
        }

        @Override
        public void onMessageSendFail(int n, int n2) throws RemoteException {
        }

        @Override
        public void onMessageSendSuccess(int n) throws RemoteException {
        }

        @Override
        public void onSessionConfigFail(int n) throws RemoteException {
        }

        @Override
        public void onSessionConfigSuccess() throws RemoteException {
        }

        @Override
        public void onSessionStarted(int n) throws RemoteException {
        }

        @Override
        public void onSessionTerminated(int n) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IWifiAwareDiscoverySessionCallback {
        private static final String DESCRIPTOR = "android.net.wifi.aware.IWifiAwareDiscoverySessionCallback";
        static final int TRANSACTION_onMatch = 5;
        static final int TRANSACTION_onMatchWithDistance = 6;
        static final int TRANSACTION_onMessageReceived = 9;
        static final int TRANSACTION_onMessageSendFail = 8;
        static final int TRANSACTION_onMessageSendSuccess = 7;
        static final int TRANSACTION_onSessionConfigFail = 3;
        static final int TRANSACTION_onSessionConfigSuccess = 2;
        static final int TRANSACTION_onSessionStarted = 1;
        static final int TRANSACTION_onSessionTerminated = 4;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IWifiAwareDiscoverySessionCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IWifiAwareDiscoverySessionCallback) {
                return (IWifiAwareDiscoverySessionCallback)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IWifiAwareDiscoverySessionCallback getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            switch (n) {
                default: {
                    return null;
                }
                case 9: {
                    return "onMessageReceived";
                }
                case 8: {
                    return "onMessageSendFail";
                }
                case 7: {
                    return "onMessageSendSuccess";
                }
                case 6: {
                    return "onMatchWithDistance";
                }
                case 5: {
                    return "onMatch";
                }
                case 4: {
                    return "onSessionTerminated";
                }
                case 3: {
                    return "onSessionConfigFail";
                }
                case 2: {
                    return "onSessionConfigSuccess";
                }
                case 1: 
            }
            return "onSessionStarted";
        }

        public static boolean setDefaultImpl(IWifiAwareDiscoverySessionCallback iWifiAwareDiscoverySessionCallback) {
            if (Proxy.sDefaultImpl == null && iWifiAwareDiscoverySessionCallback != null) {
                Proxy.sDefaultImpl = iWifiAwareDiscoverySessionCallback;
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
                    case 9: {
                        parcel.enforceInterface(DESCRIPTOR);
                        this.onMessageReceived(parcel.readInt(), parcel.createByteArray());
                        return true;
                    }
                    case 8: {
                        parcel.enforceInterface(DESCRIPTOR);
                        this.onMessageSendFail(parcel.readInt(), parcel.readInt());
                        return true;
                    }
                    case 7: {
                        parcel.enforceInterface(DESCRIPTOR);
                        this.onMessageSendSuccess(parcel.readInt());
                        return true;
                    }
                    case 6: {
                        parcel.enforceInterface(DESCRIPTOR);
                        this.onMatchWithDistance(parcel.readInt(), parcel.createByteArray(), parcel.createByteArray(), parcel.readInt());
                        return true;
                    }
                    case 5: {
                        parcel.enforceInterface(DESCRIPTOR);
                        this.onMatch(parcel.readInt(), parcel.createByteArray(), parcel.createByteArray());
                        return true;
                    }
                    case 4: {
                        parcel.enforceInterface(DESCRIPTOR);
                        this.onSessionTerminated(parcel.readInt());
                        return true;
                    }
                    case 3: {
                        parcel.enforceInterface(DESCRIPTOR);
                        this.onSessionConfigFail(parcel.readInt());
                        return true;
                    }
                    case 2: {
                        parcel.enforceInterface(DESCRIPTOR);
                        this.onSessionConfigSuccess();
                        return true;
                    }
                    case 1: 
                }
                parcel.enforceInterface(DESCRIPTOR);
                this.onSessionStarted(parcel.readInt());
                return true;
            }
            parcel2.writeString(DESCRIPTOR);
            return true;
        }

        private static class Proxy
        implements IWifiAwareDiscoverySessionCallback {
            public static IWifiAwareDiscoverySessionCallback sDefaultImpl;
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
            public void onMatch(int n, byte[] arrby, byte[] arrby2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeByteArray(arrby);
                    parcel.writeByteArray(arrby2);
                    if (!this.mRemote.transact(5, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onMatch(n, arrby, arrby2);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onMatchWithDistance(int n, byte[] arrby, byte[] arrby2, int n2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeByteArray(arrby);
                    parcel.writeByteArray(arrby2);
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(6, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onMatchWithDistance(n, arrby, arrby2, n2);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onMessageReceived(int n, byte[] arrby) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeByteArray(arrby);
                    if (!this.mRemote.transact(9, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onMessageReceived(n, arrby);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onMessageSendFail(int n, int n2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(8, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onMessageSendFail(n, n2);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onMessageSendSuccess(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(7, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onMessageSendSuccess(n);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onSessionConfigFail(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(3, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onSessionConfigFail(n);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onSessionConfigSuccess() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(2, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onSessionConfigSuccess();
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onSessionStarted(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(1, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onSessionStarted(n);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onSessionTerminated(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(4, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onSessionTerminated(n);
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

