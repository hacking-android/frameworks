/*
 * Decompiled with CFR 0.145.
 */
package android.service.persistentdata;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IPersistentDataBlockService
extends IInterface {
    public int getDataBlockSize() throws RemoteException;

    public int getFlashLockState() throws RemoteException;

    public long getMaximumDataBlockSize() throws RemoteException;

    public boolean getOemUnlockEnabled() throws RemoteException;

    public boolean hasFrpCredentialHandle() throws RemoteException;

    public byte[] read() throws RemoteException;

    public void setOemUnlockEnabled(boolean var1) throws RemoteException;

    public void wipe() throws RemoteException;

    public int write(byte[] var1) throws RemoteException;

    public static class Default
    implements IPersistentDataBlockService {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public int getDataBlockSize() throws RemoteException {
            return 0;
        }

        @Override
        public int getFlashLockState() throws RemoteException {
            return 0;
        }

        @Override
        public long getMaximumDataBlockSize() throws RemoteException {
            return 0L;
        }

        @Override
        public boolean getOemUnlockEnabled() throws RemoteException {
            return false;
        }

        @Override
        public boolean hasFrpCredentialHandle() throws RemoteException {
            return false;
        }

        @Override
        public byte[] read() throws RemoteException {
            return null;
        }

        @Override
        public void setOemUnlockEnabled(boolean bl) throws RemoteException {
        }

        @Override
        public void wipe() throws RemoteException {
        }

        @Override
        public int write(byte[] arrby) throws RemoteException {
            return 0;
        }
    }

    public static abstract class Stub
    extends Binder
    implements IPersistentDataBlockService {
        private static final String DESCRIPTOR = "android.service.persistentdata.IPersistentDataBlockService";
        static final int TRANSACTION_getDataBlockSize = 4;
        static final int TRANSACTION_getFlashLockState = 8;
        static final int TRANSACTION_getMaximumDataBlockSize = 5;
        static final int TRANSACTION_getOemUnlockEnabled = 7;
        static final int TRANSACTION_hasFrpCredentialHandle = 9;
        static final int TRANSACTION_read = 2;
        static final int TRANSACTION_setOemUnlockEnabled = 6;
        static final int TRANSACTION_wipe = 3;
        static final int TRANSACTION_write = 1;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IPersistentDataBlockService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IPersistentDataBlockService) {
                return (IPersistentDataBlockService)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IPersistentDataBlockService getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            switch (n) {
                default: {
                    return null;
                }
                case 9: {
                    return "hasFrpCredentialHandle";
                }
                case 8: {
                    return "getFlashLockState";
                }
                case 7: {
                    return "getOemUnlockEnabled";
                }
                case 6: {
                    return "setOemUnlockEnabled";
                }
                case 5: {
                    return "getMaximumDataBlockSize";
                }
                case 4: {
                    return "getDataBlockSize";
                }
                case 3: {
                    return "wipe";
                }
                case 2: {
                    return "read";
                }
                case 1: 
            }
            return "write";
        }

        public static boolean setDefaultImpl(IPersistentDataBlockService iPersistentDataBlockService) {
            if (Proxy.sDefaultImpl == null && iPersistentDataBlockService != null) {
                Proxy.sDefaultImpl = iPersistentDataBlockService;
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
        public boolean onTransact(int n, Parcel arrby, Parcel parcel, int n2) throws RemoteException {
            if (n != 1598968902) {
                switch (n) {
                    default: {
                        return super.onTransact(n, (Parcel)arrby, parcel, n2);
                    }
                    case 9: {
                        arrby.enforceInterface(DESCRIPTOR);
                        n = this.hasFrpCredentialHandle() ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 8: {
                        arrby.enforceInterface(DESCRIPTOR);
                        n = this.getFlashLockState();
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 7: {
                        arrby.enforceInterface(DESCRIPTOR);
                        n = this.getOemUnlockEnabled() ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 6: {
                        arrby.enforceInterface(DESCRIPTOR);
                        boolean bl = arrby.readInt() != 0;
                        this.setOemUnlockEnabled(bl);
                        parcel.writeNoException();
                        return true;
                    }
                    case 5: {
                        arrby.enforceInterface(DESCRIPTOR);
                        long l = this.getMaximumDataBlockSize();
                        parcel.writeNoException();
                        parcel.writeLong(l);
                        return true;
                    }
                    case 4: {
                        arrby.enforceInterface(DESCRIPTOR);
                        n = this.getDataBlockSize();
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 3: {
                        arrby.enforceInterface(DESCRIPTOR);
                        this.wipe();
                        parcel.writeNoException();
                        return true;
                    }
                    case 2: {
                        arrby.enforceInterface(DESCRIPTOR);
                        arrby = this.read();
                        parcel.writeNoException();
                        parcel.writeByteArray(arrby);
                        return true;
                    }
                    case 1: 
                }
                arrby.enforceInterface(DESCRIPTOR);
                n = this.write(arrby.createByteArray());
                parcel.writeNoException();
                parcel.writeInt(n);
                return true;
            }
            parcel.writeString(DESCRIPTOR);
            return true;
        }

        private static class Proxy
        implements IPersistentDataBlockService {
            public static IPersistentDataBlockService sDefaultImpl;
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override
            public IBinder asBinder() {
                return this.mRemote;
            }

            @Override
            public int getDataBlockSize() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(4, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        int n = Stub.getDefaultImpl().getDataBlockSize();
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

            @Override
            public int getFlashLockState() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(8, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        int n = Stub.getDefaultImpl().getFlashLockState();
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
            public long getMaximumDataBlockSize() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(5, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        long l = Stub.getDefaultImpl().getMaximumDataBlockSize();
                        return l;
                    }
                    parcel2.readException();
                    long l = parcel2.readLong();
                    return l;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public boolean getOemUnlockEnabled() throws RemoteException {
                boolean bl;
                Parcel parcel;
                Parcel parcel2;
                block5 : {
                    IBinder iBinder;
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        iBinder = this.mRemote;
                        bl = false;
                    }
                    catch (Throwable throwable) {
                        parcel.recycle();
                        parcel2.recycle();
                        throw throwable;
                    }
                    if (iBinder.transact(7, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().getOemUnlockEnabled();
                    parcel.recycle();
                    parcel2.recycle();
                    return bl;
                }
                parcel.readException();
                int n = parcel.readInt();
                if (n != 0) {
                    bl = true;
                }
                parcel.recycle();
                parcel2.recycle();
                return bl;
            }

            @Override
            public boolean hasFrpCredentialHandle() throws RemoteException {
                boolean bl;
                Parcel parcel;
                Parcel parcel2;
                block5 : {
                    IBinder iBinder;
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        iBinder = this.mRemote;
                        bl = false;
                    }
                    catch (Throwable throwable) {
                        parcel.recycle();
                        parcel2.recycle();
                        throw throwable;
                    }
                    if (iBinder.transact(9, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().hasFrpCredentialHandle();
                    parcel.recycle();
                    parcel2.recycle();
                    return bl;
                }
                parcel.readException();
                int n = parcel.readInt();
                if (n != 0) {
                    bl = true;
                }
                parcel.recycle();
                parcel2.recycle();
                return bl;
            }

            @Override
            public byte[] read() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(2, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        byte[] arrby = Stub.getDefaultImpl().read();
                        return arrby;
                    }
                    parcel2.readException();
                    byte[] arrby = parcel2.createByteArray();
                    return arrby;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void setOemUnlockEnabled(boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                int n = bl ? 1 : 0;
                try {
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(6, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setOemUnlockEnabled(bl);
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
            public void wipe() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(3, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().wipe();
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
            public int write(byte[] arrby) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeByteArray(arrby);
                    if (!this.mRemote.transact(1, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        int n = Stub.getDefaultImpl().write(arrby);
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
        }

    }

}

