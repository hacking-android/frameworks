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
import java.util.ArrayList;
import java.util.List;

public interface IBluetoothPbap
extends IInterface {
    public void disconnect(BluetoothDevice var1) throws RemoteException;

    public List<BluetoothDevice> getConnectedDevices() throws RemoteException;

    public int getConnectionState(BluetoothDevice var1) throws RemoteException;

    public List<BluetoothDevice> getDevicesMatchingConnectionStates(int[] var1) throws RemoteException;

    public static class Default
    implements IBluetoothPbap {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void disconnect(BluetoothDevice bluetoothDevice) throws RemoteException {
        }

        @Override
        public List<BluetoothDevice> getConnectedDevices() throws RemoteException {
            return null;
        }

        @Override
        public int getConnectionState(BluetoothDevice bluetoothDevice) throws RemoteException {
            return 0;
        }

        @Override
        public List<BluetoothDevice> getDevicesMatchingConnectionStates(int[] arrn) throws RemoteException {
            return null;
        }
    }

    public static abstract class Stub
    extends Binder
    implements IBluetoothPbap {
        private static final String DESCRIPTOR = "android.bluetooth.IBluetoothPbap";
        static final int TRANSACTION_disconnect = 4;
        static final int TRANSACTION_getConnectedDevices = 1;
        static final int TRANSACTION_getConnectionState = 3;
        static final int TRANSACTION_getDevicesMatchingConnectionStates = 2;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IBluetoothPbap asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IBluetoothPbap) {
                return (IBluetoothPbap)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IBluetoothPbap getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            if (n != 1) {
                if (n != 2) {
                    if (n != 3) {
                        if (n != 4) {
                            return null;
                        }
                        return "disconnect";
                    }
                    return "getConnectionState";
                }
                return "getDevicesMatchingConnectionStates";
            }
            return "getConnectedDevices";
        }

        public static boolean setDefaultImpl(IBluetoothPbap iBluetoothPbap) {
            if (Proxy.sDefaultImpl == null && iBluetoothPbap != null) {
                Proxy.sDefaultImpl = iBluetoothPbap;
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
                    if (n != 3) {
                        if (n != 4) {
                            if (n != 1598968902) {
                                return super.onTransact(n, (Parcel)object, parcel, n2);
                            }
                            parcel.writeString(DESCRIPTOR);
                            return true;
                        }
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = ((Parcel)object).readInt() != 0 ? BluetoothDevice.CREATOR.createFromParcel((Parcel)object) : null;
                        this.disconnect((BluetoothDevice)object);
                        parcel.writeNoException();
                        return true;
                    }
                    ((Parcel)object).enforceInterface(DESCRIPTOR);
                    object = ((Parcel)object).readInt() != 0 ? BluetoothDevice.CREATOR.createFromParcel((Parcel)object) : null;
                    n = this.getConnectionState((BluetoothDevice)object);
                    parcel.writeNoException();
                    parcel.writeInt(n);
                    return true;
                }
                ((Parcel)object).enforceInterface(DESCRIPTOR);
                object = this.getDevicesMatchingConnectionStates(((Parcel)object).createIntArray());
                parcel.writeNoException();
                parcel.writeTypedList(object);
                return true;
            }
            ((Parcel)object).enforceInterface(DESCRIPTOR);
            object = this.getConnectedDevices();
            parcel.writeNoException();
            parcel.writeTypedList(object);
            return true;
        }

        private static class Proxy
        implements IBluetoothPbap {
            public static IBluetoothPbap sDefaultImpl;
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override
            public IBinder asBinder() {
                return this.mRemote;
            }

            @Override
            public void disconnect(BluetoothDevice bluetoothDevice) throws RemoteException {
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
                    if (!this.mRemote.transact(4, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().disconnect(bluetoothDevice);
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
            public List<BluetoothDevice> getConnectedDevices() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(1, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        List<BluetoothDevice> list = Stub.getDefaultImpl().getConnectedDevices();
                        return list;
                    }
                    parcel2.readException();
                    ArrayList<BluetoothDevice> arrayList = parcel2.createTypedArrayList(BluetoothDevice.CREATOR);
                    return arrayList;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public int getConnectionState(BluetoothDevice bluetoothDevice) throws RemoteException {
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
                    if (!this.mRemote.transact(3, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        int n = Stub.getDefaultImpl().getConnectionState(bluetoothDevice);
                        return n;
                    }
                    parcel2.readException();
                    int n = parcel2.readInt();
                    return n;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public List<BluetoothDevice> getDevicesMatchingConnectionStates(int[] object) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeIntArray((int[])object);
                    if (!this.mRemote.transact(2, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        object = Stub.getDefaultImpl().getDevicesMatchingConnectionStates((int[])object);
                        return object;
                    }
                    parcel2.readException();
                    object = parcel2.createTypedArrayList(BluetoothDevice.CREATOR);
                    return object;
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

