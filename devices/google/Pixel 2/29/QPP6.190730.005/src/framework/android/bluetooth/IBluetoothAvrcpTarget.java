/*
 * Decompiled with CFR 0.145.
 */
package android.bluetooth;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IBluetoothAvrcpTarget
extends IInterface {
    public void sendVolumeChanged(int var1) throws RemoteException;

    public static class Default
    implements IBluetoothAvrcpTarget {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void sendVolumeChanged(int n) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IBluetoothAvrcpTarget {
        private static final String DESCRIPTOR = "android.bluetooth.IBluetoothAvrcpTarget";
        static final int TRANSACTION_sendVolumeChanged = 1;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IBluetoothAvrcpTarget asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IBluetoothAvrcpTarget) {
                return (IBluetoothAvrcpTarget)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IBluetoothAvrcpTarget getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            if (n != 1) {
                return null;
            }
            return "sendVolumeChanged";
        }

        public static boolean setDefaultImpl(IBluetoothAvrcpTarget iBluetoothAvrcpTarget) {
            if (Proxy.sDefaultImpl == null && iBluetoothAvrcpTarget != null) {
                Proxy.sDefaultImpl = iBluetoothAvrcpTarget;
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
            this.sendVolumeChanged(parcel.readInt());
            parcel2.writeNoException();
            return true;
        }

        private static class Proxy
        implements IBluetoothAvrcpTarget {
            public static IBluetoothAvrcpTarget sDefaultImpl;
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
            public void sendVolumeChanged(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(1, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().sendVolumeChanged(n);
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

