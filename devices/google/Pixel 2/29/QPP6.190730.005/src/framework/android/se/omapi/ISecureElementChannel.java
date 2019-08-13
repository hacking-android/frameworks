/*
 * Decompiled with CFR 0.145.
 */
package android.se.omapi;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface ISecureElementChannel
extends IInterface {
    public void close() throws RemoteException;

    public byte[] getSelectResponse() throws RemoteException;

    public boolean isBasicChannel() throws RemoteException;

    public boolean isClosed() throws RemoteException;

    public boolean selectNext() throws RemoteException;

    public byte[] transmit(byte[] var1) throws RemoteException;

    public static class Default
    implements ISecureElementChannel {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void close() throws RemoteException {
        }

        @Override
        public byte[] getSelectResponse() throws RemoteException {
            return null;
        }

        @Override
        public boolean isBasicChannel() throws RemoteException {
            return false;
        }

        @Override
        public boolean isClosed() throws RemoteException {
            return false;
        }

        @Override
        public boolean selectNext() throws RemoteException {
            return false;
        }

        @Override
        public byte[] transmit(byte[] arrby) throws RemoteException {
            return null;
        }
    }

    public static abstract class Stub
    extends Binder
    implements ISecureElementChannel {
        private static final String DESCRIPTOR = "android.se.omapi.ISecureElementChannel";
        static final int TRANSACTION_close = 1;
        static final int TRANSACTION_getSelectResponse = 4;
        static final int TRANSACTION_isBasicChannel = 3;
        static final int TRANSACTION_isClosed = 2;
        static final int TRANSACTION_selectNext = 6;
        static final int TRANSACTION_transmit = 5;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static ISecureElementChannel asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof ISecureElementChannel) {
                return (ISecureElementChannel)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static ISecureElementChannel getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            switch (n) {
                default: {
                    return null;
                }
                case 6: {
                    return "selectNext";
                }
                case 5: {
                    return "transmit";
                }
                case 4: {
                    return "getSelectResponse";
                }
                case 3: {
                    return "isBasicChannel";
                }
                case 2: {
                    return "isClosed";
                }
                case 1: 
            }
            return "close";
        }

        public static boolean setDefaultImpl(ISecureElementChannel iSecureElementChannel) {
            if (Proxy.sDefaultImpl == null && iSecureElementChannel != null) {
                Proxy.sDefaultImpl = iSecureElementChannel;
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
                    case 6: {
                        arrby.enforceInterface(DESCRIPTOR);
                        n = this.selectNext() ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 5: {
                        arrby.enforceInterface(DESCRIPTOR);
                        arrby = this.transmit(arrby.createByteArray());
                        parcel.writeNoException();
                        parcel.writeByteArray(arrby);
                        return true;
                    }
                    case 4: {
                        arrby.enforceInterface(DESCRIPTOR);
                        arrby = this.getSelectResponse();
                        parcel.writeNoException();
                        parcel.writeByteArray(arrby);
                        return true;
                    }
                    case 3: {
                        arrby.enforceInterface(DESCRIPTOR);
                        n = this.isBasicChannel() ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 2: {
                        arrby.enforceInterface(DESCRIPTOR);
                        n = this.isClosed() ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 1: 
                }
                arrby.enforceInterface(DESCRIPTOR);
                this.close();
                parcel.writeNoException();
                return true;
            }
            parcel.writeString(DESCRIPTOR);
            return true;
        }

        private static class Proxy
        implements ISecureElementChannel {
            public static ISecureElementChannel sDefaultImpl;
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override
            public IBinder asBinder() {
                return this.mRemote;
            }

            @Override
            public void close() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(1, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().close();
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

            @Override
            public byte[] getSelectResponse() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(4, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        byte[] arrby = Stub.getDefaultImpl().getSelectResponse();
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
            public boolean isBasicChannel() throws RemoteException {
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
                    if (iBinder.transact(3, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().isBasicChannel();
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
            public boolean isClosed() throws RemoteException {
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
                    if (iBinder.transact(2, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().isClosed();
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
            public boolean selectNext() throws RemoteException {
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
                    if (iBinder.transact(6, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().selectNext();
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
            public byte[] transmit(byte[] arrby) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeByteArray(arrby);
                    if (!this.mRemote.transact(5, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        arrby = Stub.getDefaultImpl().transmit(arrby);
                        return arrby;
                    }
                    parcel2.readException();
                    arrby = parcel2.createByteArray();
                    return arrby;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }
        }

    }

}

