/*
 * Decompiled with CFR 0.145.
 */
package android.bluetooth;

import android.bluetooth.BluetoothAvrcpPlayerSettings;
import android.bluetooth.BluetoothDevice;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import java.util.ArrayList;
import java.util.List;

public interface IBluetoothAvrcpController
extends IInterface {
    public List<BluetoothDevice> getConnectedDevices() throws RemoteException;

    public int getConnectionState(BluetoothDevice var1) throws RemoteException;

    public List<BluetoothDevice> getDevicesMatchingConnectionStates(int[] var1) throws RemoteException;

    public BluetoothAvrcpPlayerSettings getPlayerSettings(BluetoothDevice var1) throws RemoteException;

    public void sendGroupNavigationCmd(BluetoothDevice var1, int var2, int var3) throws RemoteException;

    public boolean setPlayerApplicationSetting(BluetoothAvrcpPlayerSettings var1) throws RemoteException;

    public static class Default
    implements IBluetoothAvrcpController {
        @Override
        public IBinder asBinder() {
            return null;
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

        @Override
        public BluetoothAvrcpPlayerSettings getPlayerSettings(BluetoothDevice bluetoothDevice) throws RemoteException {
            return null;
        }

        @Override
        public void sendGroupNavigationCmd(BluetoothDevice bluetoothDevice, int n, int n2) throws RemoteException {
        }

        @Override
        public boolean setPlayerApplicationSetting(BluetoothAvrcpPlayerSettings bluetoothAvrcpPlayerSettings) throws RemoteException {
            return false;
        }
    }

    public static abstract class Stub
    extends Binder
    implements IBluetoothAvrcpController {
        private static final String DESCRIPTOR = "android.bluetooth.IBluetoothAvrcpController";
        static final int TRANSACTION_getConnectedDevices = 1;
        static final int TRANSACTION_getConnectionState = 3;
        static final int TRANSACTION_getDevicesMatchingConnectionStates = 2;
        static final int TRANSACTION_getPlayerSettings = 4;
        static final int TRANSACTION_sendGroupNavigationCmd = 6;
        static final int TRANSACTION_setPlayerApplicationSetting = 5;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IBluetoothAvrcpController asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IBluetoothAvrcpController) {
                return (IBluetoothAvrcpController)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IBluetoothAvrcpController getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            switch (n) {
                default: {
                    return null;
                }
                case 6: {
                    return "sendGroupNavigationCmd";
                }
                case 5: {
                    return "setPlayerApplicationSetting";
                }
                case 4: {
                    return "getPlayerSettings";
                }
                case 3: {
                    return "getConnectionState";
                }
                case 2: {
                    return "getDevicesMatchingConnectionStates";
                }
                case 1: 
            }
            return "getConnectedDevices";
        }

        public static boolean setDefaultImpl(IBluetoothAvrcpController iBluetoothAvrcpController) {
            if (Proxy.sDefaultImpl == null && iBluetoothAvrcpController != null) {
                Proxy.sDefaultImpl = iBluetoothAvrcpController;
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
                    case 6: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        BluetoothDevice bluetoothDevice = ((Parcel)object).readInt() != 0 ? BluetoothDevice.CREATOR.createFromParcel((Parcel)object) : null;
                        this.sendGroupNavigationCmd(bluetoothDevice, ((Parcel)object).readInt(), ((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 5: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = ((Parcel)object).readInt() != 0 ? BluetoothAvrcpPlayerSettings.CREATOR.createFromParcel((Parcel)object) : null;
                        n = this.setPlayerApplicationSetting((BluetoothAvrcpPlayerSettings)object) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 4: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = ((Parcel)object).readInt() != 0 ? BluetoothDevice.CREATOR.createFromParcel((Parcel)object) : null;
                        object = this.getPlayerSettings((BluetoothDevice)object);
                        parcel.writeNoException();
                        if (object != null) {
                            parcel.writeInt(1);
                            ((BluetoothAvrcpPlayerSettings)object).writeToParcel(parcel, 1);
                        } else {
                            parcel.writeInt(0);
                        }
                        return true;
                    }
                    case 3: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = ((Parcel)object).readInt() != 0 ? BluetoothDevice.CREATOR.createFromParcel((Parcel)object) : null;
                        n = this.getConnectionState((BluetoothDevice)object);
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 2: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getDevicesMatchingConnectionStates(((Parcel)object).createIntArray());
                        parcel.writeNoException();
                        parcel.writeTypedList(object);
                        return true;
                    }
                    case 1: 
                }
                ((Parcel)object).enforceInterface(DESCRIPTOR);
                object = this.getConnectedDevices();
                parcel.writeNoException();
                parcel.writeTypedList(object);
                return true;
            }
            parcel.writeString(DESCRIPTOR);
            return true;
        }

        private static class Proxy
        implements IBluetoothAvrcpController {
            public static IBluetoothAvrcpController sDefaultImpl;
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override
            public IBinder asBinder() {
                return this.mRemote;
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

            /*
             * WARNING - void declaration
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public BluetoothAvrcpPlayerSettings getPlayerSettings(BluetoothDevice parcelable) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    void var1_5;
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (parcelable != null) {
                        parcel.writeInt(1);
                        ((BluetoothDevice)parcelable).writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(4, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        BluetoothAvrcpPlayerSettings bluetoothAvrcpPlayerSettings = Stub.getDefaultImpl().getPlayerSettings((BluetoothDevice)parcelable);
                        parcel2.recycle();
                        parcel.recycle();
                        return bluetoothAvrcpPlayerSettings;
                    }
                    parcel2.readException();
                    if (parcel2.readInt() != 0) {
                        BluetoothAvrcpPlayerSettings bluetoothAvrcpPlayerSettings = BluetoothAvrcpPlayerSettings.CREATOR.createFromParcel(parcel2);
                    } else {
                        Object var1_4 = null;
                    }
                    parcel2.recycle();
                    parcel.recycle();
                    return var1_5;
                }
                catch (Throwable throwable) {
                    parcel2.recycle();
                    parcel.recycle();
                    throw throwable;
                }
            }

            @Override
            public void sendGroupNavigationCmd(BluetoothDevice bluetoothDevice, int n, int n2) throws RemoteException {
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
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(6, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().sendGroupNavigationCmd(bluetoothDevice, n, n2);
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

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public boolean setPlayerApplicationSetting(BluetoothAvrcpPlayerSettings bluetoothAvrcpPlayerSettings) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean bl = true;
                    if (bluetoothAvrcpPlayerSettings != null) {
                        parcel.writeInt(1);
                        bluetoothAvrcpPlayerSettings.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(5, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        bl = Stub.getDefaultImpl().setPlayerApplicationSetting(bluetoothAvrcpPlayerSettings);
                        parcel2.recycle();
                        parcel.recycle();
                        return bl;
                    }
                    parcel2.readException();
                    int n = parcel2.readInt();
                    if (n == 0) {
                        bl = false;
                    }
                    parcel2.recycle();
                    parcel.recycle();
                    return bl;
                }
                catch (Throwable throwable) {
                    parcel2.recycle();
                    parcel.recycle();
                    throw throwable;
                }
            }
        }

    }

}

