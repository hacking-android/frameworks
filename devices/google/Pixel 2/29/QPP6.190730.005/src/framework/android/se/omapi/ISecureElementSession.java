/*
 * Decompiled with CFR 0.145.
 */
package android.se.omapi;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.se.omapi.ISecureElementChannel;
import android.se.omapi.ISecureElementListener;

public interface ISecureElementSession
extends IInterface {
    public void close() throws RemoteException;

    public void closeChannels() throws RemoteException;

    public byte[] getAtr() throws RemoteException;

    public boolean isClosed() throws RemoteException;

    public ISecureElementChannel openBasicChannel(byte[] var1, byte var2, ISecureElementListener var3) throws RemoteException;

    public ISecureElementChannel openLogicalChannel(byte[] var1, byte var2, ISecureElementListener var3) throws RemoteException;

    public static class Default
    implements ISecureElementSession {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void close() throws RemoteException {
        }

        @Override
        public void closeChannels() throws RemoteException {
        }

        @Override
        public byte[] getAtr() throws RemoteException {
            return null;
        }

        @Override
        public boolean isClosed() throws RemoteException {
            return false;
        }

        @Override
        public ISecureElementChannel openBasicChannel(byte[] arrby, byte by, ISecureElementListener iSecureElementListener) throws RemoteException {
            return null;
        }

        @Override
        public ISecureElementChannel openLogicalChannel(byte[] arrby, byte by, ISecureElementListener iSecureElementListener) throws RemoteException {
            return null;
        }
    }

    public static abstract class Stub
    extends Binder
    implements ISecureElementSession {
        private static final String DESCRIPTOR = "android.se.omapi.ISecureElementSession";
        static final int TRANSACTION_close = 2;
        static final int TRANSACTION_closeChannels = 3;
        static final int TRANSACTION_getAtr = 1;
        static final int TRANSACTION_isClosed = 4;
        static final int TRANSACTION_openBasicChannel = 5;
        static final int TRANSACTION_openLogicalChannel = 6;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static ISecureElementSession asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof ISecureElementSession) {
                return (ISecureElementSession)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static ISecureElementSession getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            switch (n) {
                default: {
                    return null;
                }
                case 6: {
                    return "openLogicalChannel";
                }
                case 5: {
                    return "openBasicChannel";
                }
                case 4: {
                    return "isClosed";
                }
                case 3: {
                    return "closeChannels";
                }
                case 2: {
                    return "close";
                }
                case 1: 
            }
            return "getAtr";
        }

        public static boolean setDefaultImpl(ISecureElementSession iSecureElementSession) {
            if (Proxy.sDefaultImpl == null && iSecureElementSession != null) {
                Proxy.sDefaultImpl = iSecureElementSession;
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
            if (n != 1598968902) {
                ISecureElementChannel iSecureElementChannel = null;
                ISecureElementChannel iSecureElementChannel2 = null;
                switch (n) {
                    default: {
                        return super.onTransact(n, (Parcel)object, parcel, n2);
                    }
                    case 6: {
                        object.enforceInterface(DESCRIPTOR);
                        iSecureElementChannel = this.openLogicalChannel(object.createByteArray(), object.readByte(), ISecureElementListener.Stub.asInterface(object.readStrongBinder()));
                        parcel.writeNoException();
                        object = iSecureElementChannel2;
                        if (iSecureElementChannel != null) {
                            object = iSecureElementChannel.asBinder();
                        }
                        parcel.writeStrongBinder((IBinder)object);
                        return true;
                    }
                    case 5: {
                        object.enforceInterface(DESCRIPTOR);
                        iSecureElementChannel2 = this.openBasicChannel(object.createByteArray(), object.readByte(), ISecureElementListener.Stub.asInterface(object.readStrongBinder()));
                        parcel.writeNoException();
                        object = iSecureElementChannel;
                        if (iSecureElementChannel2 != null) {
                            object = iSecureElementChannel2.asBinder();
                        }
                        parcel.writeStrongBinder((IBinder)object);
                        return true;
                    }
                    case 4: {
                        object.enforceInterface(DESCRIPTOR);
                        n = this.isClosed() ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 3: {
                        object.enforceInterface(DESCRIPTOR);
                        this.closeChannels();
                        parcel.writeNoException();
                        return true;
                    }
                    case 2: {
                        object.enforceInterface(DESCRIPTOR);
                        this.close();
                        parcel.writeNoException();
                        return true;
                    }
                    case 1: 
                }
                object.enforceInterface(DESCRIPTOR);
                object = this.getAtr();
                parcel.writeNoException();
                parcel.writeByteArray((byte[])object);
                return true;
            }
            parcel.writeString(DESCRIPTOR);
            return true;
        }

        private static class Proxy
        implements ISecureElementSession {
            public static ISecureElementSession sDefaultImpl;
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
                    if (!this.mRemote.transact(2, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
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

            @Override
            public void closeChannels() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(3, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().closeChannels();
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
            public byte[] getAtr() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(1, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        byte[] arrby = Stub.getDefaultImpl().getAtr();
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

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
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
                    if (iBinder.transact(4, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block5;
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

            /*
             * WARNING - void declaration
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public ISecureElementChannel openBasicChannel(byte[] object, byte by, ISecureElementListener iSecureElementListener) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    void var2_5;
                    void var3_6;
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeByteArray((byte[])object);
                    parcel.writeByte((byte)var2_5);
                    IBinder iBinder = var3_6 != null ? var3_6.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (!this.mRemote.transact(5, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        ISecureElementChannel iSecureElementChannel = Stub.getDefaultImpl().openBasicChannel((byte[])object, (byte)var2_5, (ISecureElementListener)var3_6);
                        return iSecureElementChannel;
                    }
                    parcel2.readException();
                    ISecureElementChannel iSecureElementChannel = ISecureElementChannel.Stub.asInterface(parcel2.readStrongBinder());
                    return iSecureElementChannel;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            /*
             * WARNING - void declaration
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public ISecureElementChannel openLogicalChannel(byte[] object, byte by, ISecureElementListener iSecureElementListener) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    void var2_5;
                    void var3_6;
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeByteArray((byte[])object);
                    parcel.writeByte((byte)var2_5);
                    IBinder iBinder = var3_6 != null ? var3_6.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (!this.mRemote.transact(6, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        ISecureElementChannel iSecureElementChannel = Stub.getDefaultImpl().openLogicalChannel((byte[])object, (byte)var2_5, (ISecureElementListener)var3_6);
                        return iSecureElementChannel;
                    }
                    parcel2.readException();
                    ISecureElementChannel iSecureElementChannel = ISecureElementChannel.Stub.asInterface(parcel2.readStrongBinder());
                    return iSecureElementChannel;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }
        }

    }

}

