/*
 * Decompiled with CFR 0.145.
 */
package android.os;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IVoldListener
extends IInterface {
    public void onDiskCreated(String var1, int var2) throws RemoteException;

    public void onDiskDestroyed(String var1) throws RemoteException;

    public void onDiskMetadataChanged(String var1, long var2, String var4, String var5) throws RemoteException;

    public void onDiskScanned(String var1) throws RemoteException;

    public void onVolumeCreated(String var1, int var2, String var3, String var4) throws RemoteException;

    public void onVolumeDestroyed(String var1) throws RemoteException;

    public void onVolumeInternalPathChanged(String var1, String var2) throws RemoteException;

    public void onVolumeMetadataChanged(String var1, String var2, String var3, String var4) throws RemoteException;

    public void onVolumePathChanged(String var1, String var2) throws RemoteException;

    public void onVolumeStateChanged(String var1, int var2) throws RemoteException;

    public static class Default
    implements IVoldListener {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void onDiskCreated(String string2, int n) throws RemoteException {
        }

        @Override
        public void onDiskDestroyed(String string2) throws RemoteException {
        }

        @Override
        public void onDiskMetadataChanged(String string2, long l, String string3, String string4) throws RemoteException {
        }

        @Override
        public void onDiskScanned(String string2) throws RemoteException {
        }

        @Override
        public void onVolumeCreated(String string2, int n, String string3, String string4) throws RemoteException {
        }

        @Override
        public void onVolumeDestroyed(String string2) throws RemoteException {
        }

        @Override
        public void onVolumeInternalPathChanged(String string2, String string3) throws RemoteException {
        }

        @Override
        public void onVolumeMetadataChanged(String string2, String string3, String string4, String string5) throws RemoteException {
        }

        @Override
        public void onVolumePathChanged(String string2, String string3) throws RemoteException {
        }

        @Override
        public void onVolumeStateChanged(String string2, int n) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IVoldListener {
        private static final String DESCRIPTOR = "android.os.IVoldListener";
        static final int TRANSACTION_onDiskCreated = 1;
        static final int TRANSACTION_onDiskDestroyed = 4;
        static final int TRANSACTION_onDiskMetadataChanged = 3;
        static final int TRANSACTION_onDiskScanned = 2;
        static final int TRANSACTION_onVolumeCreated = 5;
        static final int TRANSACTION_onVolumeDestroyed = 10;
        static final int TRANSACTION_onVolumeInternalPathChanged = 9;
        static final int TRANSACTION_onVolumeMetadataChanged = 7;
        static final int TRANSACTION_onVolumePathChanged = 8;
        static final int TRANSACTION_onVolumeStateChanged = 6;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IVoldListener asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IVoldListener) {
                return (IVoldListener)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IVoldListener getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            switch (n) {
                default: {
                    return null;
                }
                case 10: {
                    return "onVolumeDestroyed";
                }
                case 9: {
                    return "onVolumeInternalPathChanged";
                }
                case 8: {
                    return "onVolumePathChanged";
                }
                case 7: {
                    return "onVolumeMetadataChanged";
                }
                case 6: {
                    return "onVolumeStateChanged";
                }
                case 5: {
                    return "onVolumeCreated";
                }
                case 4: {
                    return "onDiskDestroyed";
                }
                case 3: {
                    return "onDiskMetadataChanged";
                }
                case 2: {
                    return "onDiskScanned";
                }
                case 1: 
            }
            return "onDiskCreated";
        }

        public static boolean setDefaultImpl(IVoldListener iVoldListener) {
            if (Proxy.sDefaultImpl == null && iVoldListener != null) {
                Proxy.sDefaultImpl = iVoldListener;
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
                    case 10: {
                        parcel.enforceInterface(DESCRIPTOR);
                        this.onVolumeDestroyed(parcel.readString());
                        return true;
                    }
                    case 9: {
                        parcel.enforceInterface(DESCRIPTOR);
                        this.onVolumeInternalPathChanged(parcel.readString(), parcel.readString());
                        return true;
                    }
                    case 8: {
                        parcel.enforceInterface(DESCRIPTOR);
                        this.onVolumePathChanged(parcel.readString(), parcel.readString());
                        return true;
                    }
                    case 7: {
                        parcel.enforceInterface(DESCRIPTOR);
                        this.onVolumeMetadataChanged(parcel.readString(), parcel.readString(), parcel.readString(), parcel.readString());
                        return true;
                    }
                    case 6: {
                        parcel.enforceInterface(DESCRIPTOR);
                        this.onVolumeStateChanged(parcel.readString(), parcel.readInt());
                        return true;
                    }
                    case 5: {
                        parcel.enforceInterface(DESCRIPTOR);
                        this.onVolumeCreated(parcel.readString(), parcel.readInt(), parcel.readString(), parcel.readString());
                        return true;
                    }
                    case 4: {
                        parcel.enforceInterface(DESCRIPTOR);
                        this.onDiskDestroyed(parcel.readString());
                        return true;
                    }
                    case 3: {
                        parcel.enforceInterface(DESCRIPTOR);
                        this.onDiskMetadataChanged(parcel.readString(), parcel.readLong(), parcel.readString(), parcel.readString());
                        return true;
                    }
                    case 2: {
                        parcel.enforceInterface(DESCRIPTOR);
                        this.onDiskScanned(parcel.readString());
                        return true;
                    }
                    case 1: 
                }
                parcel.enforceInterface(DESCRIPTOR);
                this.onDiskCreated(parcel.readString(), parcel.readInt());
                return true;
            }
            parcel2.writeString(DESCRIPTOR);
            return true;
        }

        private static class Proxy
        implements IVoldListener {
            public static IVoldListener sDefaultImpl;
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
            public void onDiskCreated(String string2, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(1, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onDiskCreated(string2, n);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onDiskDestroyed(String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(4, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onDiskDestroyed(string2);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onDiskMetadataChanged(String string2, long l, String string3, String string4) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    parcel.writeLong(l);
                    parcel.writeString(string3);
                    parcel.writeString(string4);
                    if (!this.mRemote.transact(3, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onDiskMetadataChanged(string2, l, string3, string4);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onDiskScanned(String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(2, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onDiskScanned(string2);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onVolumeCreated(String string2, int n, String string3, String string4) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    parcel.writeInt(n);
                    parcel.writeString(string3);
                    parcel.writeString(string4);
                    if (!this.mRemote.transact(5, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onVolumeCreated(string2, n, string3, string4);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onVolumeDestroyed(String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(10, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onVolumeDestroyed(string2);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onVolumeInternalPathChanged(String string2, String string3) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    parcel.writeString(string3);
                    if (!this.mRemote.transact(9, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onVolumeInternalPathChanged(string2, string3);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onVolumeMetadataChanged(String string2, String string3, String string4, String string5) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    parcel.writeString(string3);
                    parcel.writeString(string4);
                    parcel.writeString(string5);
                    if (!this.mRemote.transact(7, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onVolumeMetadataChanged(string2, string3, string4, string5);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onVolumePathChanged(String string2, String string3) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    parcel.writeString(string3);
                    if (!this.mRemote.transact(8, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onVolumePathChanged(string2, string3);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onVolumeStateChanged(String string2, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(6, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onVolumeStateChanged(string2, n);
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

