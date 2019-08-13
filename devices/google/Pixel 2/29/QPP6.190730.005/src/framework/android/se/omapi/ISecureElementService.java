/*
 * Decompiled with CFR 0.145.
 */
package android.se.omapi;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.se.omapi.ISecureElementReader;

public interface ISecureElementService
extends IInterface {
    public ISecureElementReader getReader(String var1) throws RemoteException;

    public String[] getReaders() throws RemoteException;

    public boolean[] isNFCEventAllowed(String var1, byte[] var2, String[] var3) throws RemoteException;

    public static class Default
    implements ISecureElementService {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public ISecureElementReader getReader(String string2) throws RemoteException {
            return null;
        }

        @Override
        public String[] getReaders() throws RemoteException {
            return null;
        }

        @Override
        public boolean[] isNFCEventAllowed(String string2, byte[] arrby, String[] arrstring) throws RemoteException {
            return null;
        }
    }

    public static abstract class Stub
    extends Binder
    implements ISecureElementService {
        private static final String DESCRIPTOR = "android.se.omapi.ISecureElementService";
        static final int TRANSACTION_getReader = 2;
        static final int TRANSACTION_getReaders = 1;
        static final int TRANSACTION_isNFCEventAllowed = 3;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static ISecureElementService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof ISecureElementService) {
                return (ISecureElementService)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static ISecureElementService getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            if (n != 1) {
                if (n != 2) {
                    if (n != 3) {
                        return null;
                    }
                    return "isNFCEventAllowed";
                }
                return "getReader";
            }
            return "getReaders";
        }

        public static boolean setDefaultImpl(ISecureElementService iSecureElementService) {
            if (Proxy.sDefaultImpl == null && iSecureElementService != null) {
                Proxy.sDefaultImpl = iSecureElementService;
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
                    object = this.isNFCEventAllowed(((Parcel)object).readString(), ((Parcel)object).createByteArray(), ((Parcel)object).createStringArray());
                    parcel.writeNoException();
                    parcel.writeBooleanArray((boolean[])object);
                    return true;
                }
                ((Parcel)object).enforceInterface(DESCRIPTOR);
                object = this.getReader(((Parcel)object).readString());
                parcel.writeNoException();
                object = object != null ? object.asBinder() : null;
                parcel.writeStrongBinder((IBinder)object);
                return true;
            }
            ((Parcel)object).enforceInterface(DESCRIPTOR);
            object = this.getReaders();
            parcel.writeNoException();
            parcel.writeStringArray((String[])object);
            return true;
        }

        private static class Proxy
        implements ISecureElementService {
            public static ISecureElementService sDefaultImpl;
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
            public ISecureElementReader getReader(String object) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString((String)object);
                    if (!this.mRemote.transact(2, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        object = Stub.getDefaultImpl().getReader((String)object);
                        return object;
                    }
                    parcel2.readException();
                    object = ISecureElementReader.Stub.asInterface(parcel2.readStrongBinder());
                    return object;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public String[] getReaders() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(1, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        String[] arrstring = Stub.getDefaultImpl().getReaders();
                        return arrstring;
                    }
                    parcel2.readException();
                    String[] arrstring = parcel2.createStringArray();
                    return arrstring;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public boolean[] isNFCEventAllowed(String arrbl, byte[] arrby, String[] arrstring) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString((String)arrbl);
                    parcel.writeByteArray(arrby);
                    parcel.writeStringArray(arrstring);
                    if (!this.mRemote.transact(3, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        arrbl = Stub.getDefaultImpl().isNFCEventAllowed((String)arrbl, arrby, arrstring);
                        return arrbl;
                    }
                    parcel2.readException();
                    arrbl = parcel2.createBooleanArray();
                    return arrbl;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }
        }

    }

}

