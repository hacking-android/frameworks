/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.radio;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface ICloseHandle
extends IInterface {
    public void close() throws RemoteException;

    public static class Default
    implements ICloseHandle {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void close() throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements ICloseHandle {
        private static final String DESCRIPTOR = "android.hardware.radio.ICloseHandle";
        static final int TRANSACTION_close = 1;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static ICloseHandle asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof ICloseHandle) {
                return (ICloseHandle)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static ICloseHandle getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            if (n != 1) {
                return null;
            }
            return "close";
        }

        public static boolean setDefaultImpl(ICloseHandle iCloseHandle) {
            if (Proxy.sDefaultImpl == null && iCloseHandle != null) {
                Proxy.sDefaultImpl = iCloseHandle;
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
            this.close();
            parcel2.writeNoException();
            return true;
        }

        private static class Proxy
        implements ICloseHandle {
            public static ICloseHandle sDefaultImpl;
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
        }

    }

}

