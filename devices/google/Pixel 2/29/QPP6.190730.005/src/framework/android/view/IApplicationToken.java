/*
 * Decompiled with CFR 0.145.
 */
package android.view;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IApplicationToken
extends IInterface {
    public String getName() throws RemoteException;

    public static class Default
    implements IApplicationToken {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public String getName() throws RemoteException {
            return null;
        }
    }

    public static abstract class Stub
    extends Binder
    implements IApplicationToken {
        private static final String DESCRIPTOR = "android.view.IApplicationToken";
        static final int TRANSACTION_getName = 1;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IApplicationToken asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IApplicationToken) {
                return (IApplicationToken)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IApplicationToken getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            if (n != 1) {
                return null;
            }
            return "getName";
        }

        public static boolean setDefaultImpl(IApplicationToken iApplicationToken) {
            if (Proxy.sDefaultImpl == null && iApplicationToken != null) {
                Proxy.sDefaultImpl = iApplicationToken;
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
                if (n != 1598968902) {
                    return super.onTransact(n, (Parcel)object, parcel, n2);
                }
                parcel.writeString(DESCRIPTOR);
                return true;
            }
            ((Parcel)object).enforceInterface(DESCRIPTOR);
            object = this.getName();
            parcel.writeNoException();
            parcel.writeString((String)object);
            return true;
        }

        private static class Proxy
        implements IApplicationToken {
            public static IApplicationToken sDefaultImpl;
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
            public String getName() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(1, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        String string2 = Stub.getDefaultImpl().getName();
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
        }

    }

}

