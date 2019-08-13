/*
 * Decompiled with CFR 0.145.
 */
package android.bluetooth;

import android.bluetooth.IBluetooth;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IBluetoothManagerCallback
extends IInterface {
    public void onBluetoothServiceDown() throws RemoteException;

    public void onBluetoothServiceUp(IBluetooth var1) throws RemoteException;

    public void onBrEdrDown() throws RemoteException;

    public static class Default
    implements IBluetoothManagerCallback {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void onBluetoothServiceDown() throws RemoteException {
        }

        @Override
        public void onBluetoothServiceUp(IBluetooth iBluetooth) throws RemoteException {
        }

        @Override
        public void onBrEdrDown() throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IBluetoothManagerCallback {
        private static final String DESCRIPTOR = "android.bluetooth.IBluetoothManagerCallback";
        static final int TRANSACTION_onBluetoothServiceDown = 2;
        static final int TRANSACTION_onBluetoothServiceUp = 1;
        static final int TRANSACTION_onBrEdrDown = 3;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IBluetoothManagerCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IBluetoothManagerCallback) {
                return (IBluetoothManagerCallback)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IBluetoothManagerCallback getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            if (n != 1) {
                if (n != 2) {
                    if (n != 3) {
                        return null;
                    }
                    return "onBrEdrDown";
                }
                return "onBluetoothServiceDown";
            }
            return "onBluetoothServiceUp";
        }

        public static boolean setDefaultImpl(IBluetoothManagerCallback iBluetoothManagerCallback) {
            if (Proxy.sDefaultImpl == null && iBluetoothManagerCallback != null) {
                Proxy.sDefaultImpl = iBluetoothManagerCallback;
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
                if (n != 2) {
                    if (n != 3) {
                        if (n != 1598968902) {
                            return super.onTransact(n, parcel, parcel2, n2);
                        }
                        parcel2.writeString(DESCRIPTOR);
                        return true;
                    }
                    parcel.enforceInterface(DESCRIPTOR);
                    this.onBrEdrDown();
                    return true;
                }
                parcel.enforceInterface(DESCRIPTOR);
                this.onBluetoothServiceDown();
                return true;
            }
            parcel.enforceInterface(DESCRIPTOR);
            this.onBluetoothServiceUp(IBluetooth.Stub.asInterface(parcel.readStrongBinder()));
            return true;
        }

        private static class Proxy
        implements IBluetoothManagerCallback {
            public static IBluetoothManagerCallback sDefaultImpl;
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
            public void onBluetoothServiceDown() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(2, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onBluetoothServiceDown();
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void onBluetoothServiceUp(IBluetooth iBluetooth) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iBluetooth != null ? iBluetooth.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (this.mRemote.transact(1, parcel, null, 1)) return;
                    if (Stub.getDefaultImpl() == null) return;
                    Stub.getDefaultImpl().onBluetoothServiceUp(iBluetooth);
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onBrEdrDown() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(3, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onBrEdrDown();
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }
        }

    }

}

