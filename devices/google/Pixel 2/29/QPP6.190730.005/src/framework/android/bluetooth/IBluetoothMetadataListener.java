/*
 * Decompiled with CFR 0.145.
 */
package android.bluetooth;

import android.bluetooth.BluetoothDevice;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;

public interface IBluetoothMetadataListener
extends IInterface {
    public void onMetadataChanged(BluetoothDevice var1, int var2, byte[] var3) throws RemoteException;

    public static class Default
    implements IBluetoothMetadataListener {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void onMetadataChanged(BluetoothDevice bluetoothDevice, int n, byte[] arrby) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IBluetoothMetadataListener {
        private static final String DESCRIPTOR = "android.bluetooth.IBluetoothMetadataListener";
        static final int TRANSACTION_onMetadataChanged = 1;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IBluetoothMetadataListener asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IBluetoothMetadataListener) {
                return (IBluetoothMetadataListener)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IBluetoothMetadataListener getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            if (n != 1) {
                return null;
            }
            return "onMetadataChanged";
        }

        public static boolean setDefaultImpl(IBluetoothMetadataListener iBluetoothMetadataListener) {
            if (Proxy.sDefaultImpl == null && iBluetoothMetadataListener != null) {
                Proxy.sDefaultImpl = iBluetoothMetadataListener;
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
        public boolean onTransact(int n, Parcel parcel, Parcel object, int n2) throws RemoteException {
            if (n != 1) {
                if (n != 1598968902) {
                    return super.onTransact(n, parcel, (Parcel)object, n2);
                }
                ((Parcel)object).writeString(DESCRIPTOR);
                return true;
            }
            parcel.enforceInterface(DESCRIPTOR);
            object = parcel.readInt() != 0 ? BluetoothDevice.CREATOR.createFromParcel(parcel) : null;
            this.onMetadataChanged((BluetoothDevice)object, parcel.readInt(), parcel.createByteArray());
            return true;
        }

        private static class Proxy
        implements IBluetoothMetadataListener {
            public static IBluetoothMetadataListener sDefaultImpl;
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
            public void onMetadataChanged(BluetoothDevice bluetoothDevice, int n, byte[] arrby) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (bluetoothDevice != null) {
                        parcel.writeInt(1);
                        bluetoothDevice.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeInt(n);
                    parcel.writeByteArray(arrby);
                    if (!this.mRemote.transact(1, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onMetadataChanged(bluetoothDevice, n, arrby);
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

