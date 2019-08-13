/*
 * Decompiled with CFR 0.145.
 */
package android.bluetooth.le;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IAdvertisingSetCallback
extends IInterface {
    public void onAdvertisingDataSet(int var1, int var2) throws RemoteException;

    public void onAdvertisingEnabled(int var1, boolean var2, int var3) throws RemoteException;

    public void onAdvertisingParametersUpdated(int var1, int var2, int var3) throws RemoteException;

    public void onAdvertisingSetStarted(int var1, int var2, int var3) throws RemoteException;

    public void onAdvertisingSetStopped(int var1) throws RemoteException;

    public void onOwnAddressRead(int var1, int var2, String var3) throws RemoteException;

    public void onPeriodicAdvertisingDataSet(int var1, int var2) throws RemoteException;

    public void onPeriodicAdvertisingEnabled(int var1, boolean var2, int var3) throws RemoteException;

    public void onPeriodicAdvertisingParametersUpdated(int var1, int var2) throws RemoteException;

    public void onScanResponseDataSet(int var1, int var2) throws RemoteException;

    public static class Default
    implements IAdvertisingSetCallback {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void onAdvertisingDataSet(int n, int n2) throws RemoteException {
        }

        @Override
        public void onAdvertisingEnabled(int n, boolean bl, int n2) throws RemoteException {
        }

        @Override
        public void onAdvertisingParametersUpdated(int n, int n2, int n3) throws RemoteException {
        }

        @Override
        public void onAdvertisingSetStarted(int n, int n2, int n3) throws RemoteException {
        }

        @Override
        public void onAdvertisingSetStopped(int n) throws RemoteException {
        }

        @Override
        public void onOwnAddressRead(int n, int n2, String string2) throws RemoteException {
        }

        @Override
        public void onPeriodicAdvertisingDataSet(int n, int n2) throws RemoteException {
        }

        @Override
        public void onPeriodicAdvertisingEnabled(int n, boolean bl, int n2) throws RemoteException {
        }

        @Override
        public void onPeriodicAdvertisingParametersUpdated(int n, int n2) throws RemoteException {
        }

        @Override
        public void onScanResponseDataSet(int n, int n2) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IAdvertisingSetCallback {
        private static final String DESCRIPTOR = "android.bluetooth.le.IAdvertisingSetCallback";
        static final int TRANSACTION_onAdvertisingDataSet = 5;
        static final int TRANSACTION_onAdvertisingEnabled = 4;
        static final int TRANSACTION_onAdvertisingParametersUpdated = 7;
        static final int TRANSACTION_onAdvertisingSetStarted = 1;
        static final int TRANSACTION_onAdvertisingSetStopped = 3;
        static final int TRANSACTION_onOwnAddressRead = 2;
        static final int TRANSACTION_onPeriodicAdvertisingDataSet = 9;
        static final int TRANSACTION_onPeriodicAdvertisingEnabled = 10;
        static final int TRANSACTION_onPeriodicAdvertisingParametersUpdated = 8;
        static final int TRANSACTION_onScanResponseDataSet = 6;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IAdvertisingSetCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IAdvertisingSetCallback) {
                return (IAdvertisingSetCallback)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IAdvertisingSetCallback getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            switch (n) {
                default: {
                    return null;
                }
                case 10: {
                    return "onPeriodicAdvertisingEnabled";
                }
                case 9: {
                    return "onPeriodicAdvertisingDataSet";
                }
                case 8: {
                    return "onPeriodicAdvertisingParametersUpdated";
                }
                case 7: {
                    return "onAdvertisingParametersUpdated";
                }
                case 6: {
                    return "onScanResponseDataSet";
                }
                case 5: {
                    return "onAdvertisingDataSet";
                }
                case 4: {
                    return "onAdvertisingEnabled";
                }
                case 3: {
                    return "onAdvertisingSetStopped";
                }
                case 2: {
                    return "onOwnAddressRead";
                }
                case 1: 
            }
            return "onAdvertisingSetStarted";
        }

        public static boolean setDefaultImpl(IAdvertisingSetCallback iAdvertisingSetCallback) {
            if (Proxy.sDefaultImpl == null && iAdvertisingSetCallback != null) {
                Proxy.sDefaultImpl = iAdvertisingSetCallback;
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
                boolean bl = false;
                boolean bl2 = false;
                switch (n) {
                    default: {
                        return super.onTransact(n, parcel, parcel2, n2);
                    }
                    case 10: {
                        parcel.enforceInterface(DESCRIPTOR);
                        n = parcel.readInt();
                        if (parcel.readInt() != 0) {
                            bl2 = true;
                        }
                        this.onPeriodicAdvertisingEnabled(n, bl2, parcel.readInt());
                        return true;
                    }
                    case 9: {
                        parcel.enforceInterface(DESCRIPTOR);
                        this.onPeriodicAdvertisingDataSet(parcel.readInt(), parcel.readInt());
                        return true;
                    }
                    case 8: {
                        parcel.enforceInterface(DESCRIPTOR);
                        this.onPeriodicAdvertisingParametersUpdated(parcel.readInt(), parcel.readInt());
                        return true;
                    }
                    case 7: {
                        parcel.enforceInterface(DESCRIPTOR);
                        this.onAdvertisingParametersUpdated(parcel.readInt(), parcel.readInt(), parcel.readInt());
                        return true;
                    }
                    case 6: {
                        parcel.enforceInterface(DESCRIPTOR);
                        this.onScanResponseDataSet(parcel.readInt(), parcel.readInt());
                        return true;
                    }
                    case 5: {
                        parcel.enforceInterface(DESCRIPTOR);
                        this.onAdvertisingDataSet(parcel.readInt(), parcel.readInt());
                        return true;
                    }
                    case 4: {
                        parcel.enforceInterface(DESCRIPTOR);
                        n = parcel.readInt();
                        bl2 = bl;
                        if (parcel.readInt() != 0) {
                            bl2 = true;
                        }
                        this.onAdvertisingEnabled(n, bl2, parcel.readInt());
                        return true;
                    }
                    case 3: {
                        parcel.enforceInterface(DESCRIPTOR);
                        this.onAdvertisingSetStopped(parcel.readInt());
                        return true;
                    }
                    case 2: {
                        parcel.enforceInterface(DESCRIPTOR);
                        this.onOwnAddressRead(parcel.readInt(), parcel.readInt(), parcel.readString());
                        return true;
                    }
                    case 1: 
                }
                parcel.enforceInterface(DESCRIPTOR);
                this.onAdvertisingSetStarted(parcel.readInt(), parcel.readInt(), parcel.readInt());
                return true;
            }
            parcel2.writeString(DESCRIPTOR);
            return true;
        }

        private static class Proxy
        implements IAdvertisingSetCallback {
            public static IAdvertisingSetCallback sDefaultImpl;
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
            public void onAdvertisingDataSet(int n, int n2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(5, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onAdvertisingDataSet(n, n2);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onAdvertisingEnabled(int n, boolean bl, int n2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                parcel.writeInt(n);
                int n3 = bl ? 1 : 0;
                try {
                    parcel.writeInt(n3);
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(4, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onAdvertisingEnabled(n, bl, n2);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onAdvertisingParametersUpdated(int n, int n2, int n3) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    parcel.writeInt(n3);
                    if (!this.mRemote.transact(7, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onAdvertisingParametersUpdated(n, n2, n3);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onAdvertisingSetStarted(int n, int n2, int n3) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    parcel.writeInt(n3);
                    if (!this.mRemote.transact(1, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onAdvertisingSetStarted(n, n2, n3);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onAdvertisingSetStopped(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(3, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onAdvertisingSetStopped(n);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onOwnAddressRead(int n, int n2, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(2, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onOwnAddressRead(n, n2, string2);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onPeriodicAdvertisingDataSet(int n, int n2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(9, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onPeriodicAdvertisingDataSet(n, n2);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onPeriodicAdvertisingEnabled(int n, boolean bl, int n2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                parcel.writeInt(n);
                int n3 = bl ? 1 : 0;
                try {
                    parcel.writeInt(n3);
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(10, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onPeriodicAdvertisingEnabled(n, bl, n2);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onPeriodicAdvertisingParametersUpdated(int n, int n2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(8, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onPeriodicAdvertisingParametersUpdated(n, n2);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onScanResponseDataSet(int n, int n2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(6, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onScanResponseDataSet(n, n2);
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

