/*
 * Decompiled with CFR 0.145.
 */
package android.os;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IDeviceIdentifiersPolicyService
extends IInterface {
    public String getSerial() throws RemoteException;

    public String getSerialForPackage(String var1) throws RemoteException;

    public static class Default
    implements IDeviceIdentifiersPolicyService {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public String getSerial() throws RemoteException {
            return null;
        }

        @Override
        public String getSerialForPackage(String string2) throws RemoteException {
            return null;
        }
    }

    public static abstract class Stub
    extends Binder
    implements IDeviceIdentifiersPolicyService {
        private static final String DESCRIPTOR = "android.os.IDeviceIdentifiersPolicyService";
        static final int TRANSACTION_getSerial = 1;
        static final int TRANSACTION_getSerialForPackage = 2;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IDeviceIdentifiersPolicyService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IDeviceIdentifiersPolicyService) {
                return (IDeviceIdentifiersPolicyService)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IDeviceIdentifiersPolicyService getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            if (n != 1) {
                if (n != 2) {
                    return null;
                }
                return "getSerialForPackage";
            }
            return "getSerial";
        }

        public static boolean setDefaultImpl(IDeviceIdentifiersPolicyService iDeviceIdentifiersPolicyService) {
            if (Proxy.sDefaultImpl == null && iDeviceIdentifiersPolicyService != null) {
                Proxy.sDefaultImpl = iDeviceIdentifiersPolicyService;
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
                    if (n != 1598968902) {
                        return super.onTransact(n, (Parcel)object, parcel, n2);
                    }
                    parcel.writeString(DESCRIPTOR);
                    return true;
                }
                ((Parcel)object).enforceInterface(DESCRIPTOR);
                object = this.getSerialForPackage(((Parcel)object).readString());
                parcel.writeNoException();
                parcel.writeString((String)object);
                return true;
            }
            ((Parcel)object).enforceInterface(DESCRIPTOR);
            object = this.getSerial();
            parcel.writeNoException();
            parcel.writeString((String)object);
            return true;
        }

        private static class Proxy
        implements IDeviceIdentifiersPolicyService {
            public static IDeviceIdentifiersPolicyService sDefaultImpl;
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
            public String getSerial() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(1, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        String string2 = Stub.getDefaultImpl().getSerial();
                        return string2;
                    }
                    parcel2.readException();
                    String string3 = parcel2.readString();
                    return string3;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public String getSerialForPackage(String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(2, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        string2 = Stub.getDefaultImpl().getSerialForPackage(string2);
                        return string2;
                    }
                    parcel2.readException();
                    string2 = parcel2.readString();
                    return string2;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }
        }

    }

}

