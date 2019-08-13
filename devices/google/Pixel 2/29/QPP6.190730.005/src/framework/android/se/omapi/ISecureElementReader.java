/*
 * Decompiled with CFR 0.145.
 */
package android.se.omapi;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.se.omapi.ISecureElementSession;

public interface ISecureElementReader
extends IInterface {
    public void closeSessions() throws RemoteException;

    public boolean isSecureElementPresent() throws RemoteException;

    public ISecureElementSession openSession() throws RemoteException;

    public static class Default
    implements ISecureElementReader {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void closeSessions() throws RemoteException {
        }

        @Override
        public boolean isSecureElementPresent() throws RemoteException {
            return false;
        }

        @Override
        public ISecureElementSession openSession() throws RemoteException {
            return null;
        }
    }

    public static abstract class Stub
    extends Binder
    implements ISecureElementReader {
        private static final String DESCRIPTOR = "android.se.omapi.ISecureElementReader";
        static final int TRANSACTION_closeSessions = 3;
        static final int TRANSACTION_isSecureElementPresent = 1;
        static final int TRANSACTION_openSession = 2;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static ISecureElementReader asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof ISecureElementReader) {
                return (ISecureElementReader)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static ISecureElementReader getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            if (n != 1) {
                if (n != 2) {
                    if (n != 3) {
                        return null;
                    }
                    return "closeSessions";
                }
                return "openSession";
            }
            return "isSecureElementPresent";
        }

        public static boolean setDefaultImpl(ISecureElementReader iSecureElementReader) {
            if (Proxy.sDefaultImpl == null && iSecureElementReader != null) {
                Proxy.sDefaultImpl = iSecureElementReader;
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
                        if (n != 1598968902) {
                            return super.onTransact(n, (Parcel)object, parcel, n2);
                        }
                        parcel.writeString(DESCRIPTOR);
                        return true;
                    }
                    ((Parcel)object).enforceInterface(DESCRIPTOR);
                    this.closeSessions();
                    parcel.writeNoException();
                    return true;
                }
                ((Parcel)object).enforceInterface(DESCRIPTOR);
                object = this.openSession();
                parcel.writeNoException();
                object = object != null ? object.asBinder() : null;
                parcel.writeStrongBinder((IBinder)object);
                return true;
            }
            ((Parcel)object).enforceInterface(DESCRIPTOR);
            n = this.isSecureElementPresent() ? 1 : 0;
            parcel.writeNoException();
            parcel.writeInt(n);
            return true;
        }

        private static class Proxy
        implements ISecureElementReader {
            public static ISecureElementReader sDefaultImpl;
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override
            public IBinder asBinder() {
                return this.mRemote;
            }

            @Override
            public void closeSessions() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(3, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().closeSessions();
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
            public boolean isSecureElementPresent() throws RemoteException {
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
                    if (iBinder.transact(1, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().isSecureElementPresent();
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
            public ISecureElementSession openSession() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(2, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        ISecureElementSession iSecureElementSession = Stub.getDefaultImpl().openSession();
                        return iSecureElementSession;
                    }
                    parcel2.readException();
                    ISecureElementSession iSecureElementSession = ISecureElementSession.Stub.asInterface(parcel2.readStrongBinder());
                    return iSecureElementSession;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }
        }

    }

}

