/*
 * Decompiled with CFR 0.145.
 */
package android.bluetooth;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IBluetoothCallback
extends IInterface {
    public void onBluetoothStateChange(int var1, int var2) throws RemoteException;

    public static class Default
    implements IBluetoothCallback {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void onBluetoothStateChange(int n, int n2) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IBluetoothCallback {
        private static final String DESCRIPTOR = "android.bluetooth.IBluetoothCallback";
        static final int TRANSACTION_onBluetoothStateChange = 1;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IBluetoothCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IBluetoothCallback) {
                return (IBluetoothCallback)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IBluetoothCallback getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            if (n != 1) {
                return null;
            }
            return "onBluetoothStateChange";
        }

        public static boolean setDefaultImpl(IBluetoothCallback iBluetoothCallback) {
            if (Proxy.sDefaultImpl == null && iBluetoothCallback != null) {
                Proxy.sDefaultImpl = iBluetoothCallback;
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
            this.onBluetoothStateChange(parcel.readInt(), parcel.readInt());
            parcel2.writeNoException();
            return true;
        }

        private static class Proxy
        implements IBluetoothCallback {
            public static IBluetoothCallback sDefaultImpl;
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
            public void onBluetoothStateChange(int n, int n2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(1, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onBluetoothStateChange(n, n2);
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

