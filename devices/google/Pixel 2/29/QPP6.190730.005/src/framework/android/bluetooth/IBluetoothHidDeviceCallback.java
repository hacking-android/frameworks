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

public interface IBluetoothHidDeviceCallback
extends IInterface {
    public void onAppStatusChanged(BluetoothDevice var1, boolean var2) throws RemoteException;

    public void onConnectionStateChanged(BluetoothDevice var1, int var2) throws RemoteException;

    public void onGetReport(BluetoothDevice var1, byte var2, byte var3, int var4) throws RemoteException;

    public void onInterruptData(BluetoothDevice var1, byte var2, byte[] var3) throws RemoteException;

    public void onSetProtocol(BluetoothDevice var1, byte var2) throws RemoteException;

    public void onSetReport(BluetoothDevice var1, byte var2, byte var3, byte[] var4) throws RemoteException;

    public void onVirtualCableUnplug(BluetoothDevice var1) throws RemoteException;

    public static class Default
    implements IBluetoothHidDeviceCallback {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void onAppStatusChanged(BluetoothDevice bluetoothDevice, boolean bl) throws RemoteException {
        }

        @Override
        public void onConnectionStateChanged(BluetoothDevice bluetoothDevice, int n) throws RemoteException {
        }

        @Override
        public void onGetReport(BluetoothDevice bluetoothDevice, byte by, byte by2, int n) throws RemoteException {
        }

        @Override
        public void onInterruptData(BluetoothDevice bluetoothDevice, byte by, byte[] arrby) throws RemoteException {
        }

        @Override
        public void onSetProtocol(BluetoothDevice bluetoothDevice, byte by) throws RemoteException {
        }

        @Override
        public void onSetReport(BluetoothDevice bluetoothDevice, byte by, byte by2, byte[] arrby) throws RemoteException {
        }

        @Override
        public void onVirtualCableUnplug(BluetoothDevice bluetoothDevice) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IBluetoothHidDeviceCallback {
        private static final String DESCRIPTOR = "android.bluetooth.IBluetoothHidDeviceCallback";
        static final int TRANSACTION_onAppStatusChanged = 1;
        static final int TRANSACTION_onConnectionStateChanged = 2;
        static final int TRANSACTION_onGetReport = 3;
        static final int TRANSACTION_onInterruptData = 6;
        static final int TRANSACTION_onSetProtocol = 5;
        static final int TRANSACTION_onSetReport = 4;
        static final int TRANSACTION_onVirtualCableUnplug = 7;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IBluetoothHidDeviceCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IBluetoothHidDeviceCallback) {
                return (IBluetoothHidDeviceCallback)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IBluetoothHidDeviceCallback getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            switch (n) {
                default: {
                    return null;
                }
                case 7: {
                    return "onVirtualCableUnplug";
                }
                case 6: {
                    return "onInterruptData";
                }
                case 5: {
                    return "onSetProtocol";
                }
                case 4: {
                    return "onSetReport";
                }
                case 3: {
                    return "onGetReport";
                }
                case 2: {
                    return "onConnectionStateChanged";
                }
                case 1: 
            }
            return "onAppStatusChanged";
        }

        public static boolean setDefaultImpl(IBluetoothHidDeviceCallback iBluetoothHidDeviceCallback) {
            if (Proxy.sDefaultImpl == null && iBluetoothHidDeviceCallback != null) {
                Proxy.sDefaultImpl = iBluetoothHidDeviceCallback;
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
            if (n != 1598968902) {
                switch (n) {
                    default: {
                        return super.onTransact(n, (Parcel)object, parcel, n2);
                    }
                    case 7: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = ((Parcel)object).readInt() != 0 ? BluetoothDevice.CREATOR.createFromParcel((Parcel)object) : null;
                        this.onVirtualCableUnplug((BluetoothDevice)object);
                        parcel.writeNoException();
                        return true;
                    }
                    case 6: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        BluetoothDevice bluetoothDevice = ((Parcel)object).readInt() != 0 ? BluetoothDevice.CREATOR.createFromParcel((Parcel)object) : null;
                        this.onInterruptData(bluetoothDevice, ((Parcel)object).readByte(), ((Parcel)object).createByteArray());
                        parcel.writeNoException();
                        return true;
                    }
                    case 5: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        BluetoothDevice bluetoothDevice = ((Parcel)object).readInt() != 0 ? BluetoothDevice.CREATOR.createFromParcel((Parcel)object) : null;
                        this.onSetProtocol(bluetoothDevice, ((Parcel)object).readByte());
                        parcel.writeNoException();
                        return true;
                    }
                    case 4: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        BluetoothDevice bluetoothDevice = ((Parcel)object).readInt() != 0 ? BluetoothDevice.CREATOR.createFromParcel((Parcel)object) : null;
                        this.onSetReport(bluetoothDevice, ((Parcel)object).readByte(), ((Parcel)object).readByte(), ((Parcel)object).createByteArray());
                        parcel.writeNoException();
                        return true;
                    }
                    case 3: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        BluetoothDevice bluetoothDevice = ((Parcel)object).readInt() != 0 ? BluetoothDevice.CREATOR.createFromParcel((Parcel)object) : null;
                        this.onGetReport(bluetoothDevice, ((Parcel)object).readByte(), ((Parcel)object).readByte(), ((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 2: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        BluetoothDevice bluetoothDevice = ((Parcel)object).readInt() != 0 ? BluetoothDevice.CREATOR.createFromParcel((Parcel)object) : null;
                        this.onConnectionStateChanged(bluetoothDevice, ((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 1: 
                }
                ((Parcel)object).enforceInterface(DESCRIPTOR);
                BluetoothDevice bluetoothDevice = ((Parcel)object).readInt() != 0 ? BluetoothDevice.CREATOR.createFromParcel((Parcel)object) : null;
                boolean bl = ((Parcel)object).readInt() != 0;
                this.onAppStatusChanged(bluetoothDevice, bl);
                parcel.writeNoException();
                return true;
            }
            parcel.writeString(DESCRIPTOR);
            return true;
        }

        private static class Proxy
        implements IBluetoothHidDeviceCallback {
            public static IBluetoothHidDeviceCallback sDefaultImpl;
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

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void onAppStatusChanged(BluetoothDevice bluetoothDevice, boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (bluetoothDevice != null) {
                        parcel.writeInt(1);
                        bluetoothDevice.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    int n = bl ? 1 : 0;
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(1, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onAppStatusChanged(bluetoothDevice, bl);
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

            @Override
            public void onConnectionStateChanged(BluetoothDevice bluetoothDevice, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (bluetoothDevice != null) {
                        parcel.writeInt(1);
                        bluetoothDevice.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(2, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onConnectionStateChanged(bluetoothDevice, n);
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

            @Override
            public void onGetReport(BluetoothDevice bluetoothDevice, byte by, byte by2, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (bluetoothDevice != null) {
                        parcel.writeInt(1);
                        bluetoothDevice.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeByte(by);
                    parcel.writeByte(by2);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(3, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onGetReport(bluetoothDevice, by, by2, n);
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

            @Override
            public void onInterruptData(BluetoothDevice bluetoothDevice, byte by, byte[] arrby) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (bluetoothDevice != null) {
                        parcel.writeInt(1);
                        bluetoothDevice.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeByte(by);
                    parcel.writeByteArray(arrby);
                    if (!this.mRemote.transact(6, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onInterruptData(bluetoothDevice, by, arrby);
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

            @Override
            public void onSetProtocol(BluetoothDevice bluetoothDevice, byte by) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (bluetoothDevice != null) {
                        parcel.writeInt(1);
                        bluetoothDevice.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeByte(by);
                    if (!this.mRemote.transact(5, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onSetProtocol(bluetoothDevice, by);
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

            @Override
            public void onSetReport(BluetoothDevice bluetoothDevice, byte by, byte by2, byte[] arrby) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (bluetoothDevice != null) {
                        parcel.writeInt(1);
                        bluetoothDevice.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeByte(by);
                    parcel.writeByte(by2);
                    parcel.writeByteArray(arrby);
                    if (!this.mRemote.transact(4, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onSetReport(bluetoothDevice, by, by2, arrby);
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

            @Override
            public void onVirtualCableUnplug(BluetoothDevice bluetoothDevice) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (bluetoothDevice != null) {
                        parcel.writeInt(1);
                        bluetoothDevice.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(7, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onVirtualCableUnplug(bluetoothDevice);
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

