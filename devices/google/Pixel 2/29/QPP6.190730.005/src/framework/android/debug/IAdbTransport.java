/*
 * Decompiled with CFR 0.145.
 */
package android.debug;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IAdbTransport
extends IInterface {
    public void onAdbEnabled(boolean var1) throws RemoteException;

    public static class Default
    implements IAdbTransport {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void onAdbEnabled(boolean bl) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IAdbTransport {
        private static final String DESCRIPTOR = "android.debug.IAdbTransport";
        static final int TRANSACTION_onAdbEnabled = 1;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IAdbTransport asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IAdbTransport) {
                return (IAdbTransport)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IAdbTransport getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            if (n != 1) {
                return null;
            }
            return "onAdbEnabled";
        }

        public static boolean setDefaultImpl(IAdbTransport iAdbTransport) {
            if (Proxy.sDefaultImpl == null && iAdbTransport != null) {
                Proxy.sDefaultImpl = iAdbTransport;
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
            if (n != 1) {
                if (n != 1598968902) {
                    return super.onTransact(n, parcel, parcel2, n2);
                }
                parcel2.writeString(DESCRIPTOR);
                return true;
            }
            parcel.enforceInterface(DESCRIPTOR);
            boolean bl = parcel.readInt() != 0;
            this.onAdbEnabled(bl);
            parcel2.writeNoException();
            return true;
        }

        private static class Proxy
        implements IAdbTransport {
            public static IAdbTransport sDefaultImpl;
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
            public void onAdbEnabled(boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                int n = bl ? 1 : 0;
                try {
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(1, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onAdbEnabled(bl);
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
        }

    }

}

