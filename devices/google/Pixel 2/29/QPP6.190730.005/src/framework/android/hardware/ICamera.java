/*
 * Decompiled with CFR 0.145.
 */
package android.hardware;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface ICamera
extends IInterface {
    public void disconnect() throws RemoteException;

    public static class Default
    implements ICamera {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void disconnect() throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements ICamera {
        private static final String DESCRIPTOR = "android.hardware.ICamera";
        static final int TRANSACTION_disconnect = 1;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static ICamera asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof ICamera) {
                return (ICamera)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static ICamera getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            if (n != 1) {
                return null;
            }
            return "disconnect";
        }

        public static boolean setDefaultImpl(ICamera iCamera) {
            if (Proxy.sDefaultImpl == null && iCamera != null) {
                Proxy.sDefaultImpl = iCamera;
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
            this.disconnect();
            parcel2.writeNoException();
            return true;
        }

        private static class Proxy
        implements ICamera {
            public static ICamera sDefaultImpl;
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override
            public IBinder asBinder() {
                return this.mRemote;
            }

            @Override
            public void disconnect() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(1, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().disconnect();
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

