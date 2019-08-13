/*
 * Decompiled with CFR 0.145.
 */
package android.bluetooth;

import android.bluetooth.BluetoothDevice;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.ParcelUuid;
import android.os.Parcelable;
import android.os.RemoteException;

public interface IBluetoothSocketManager
extends IInterface {
    public ParcelFileDescriptor connectSocket(BluetoothDevice var1, int var2, ParcelUuid var3, int var4, int var5) throws RemoteException;

    public ParcelFileDescriptor createSocketChannel(int var1, String var2, ParcelUuid var3, int var4, int var5) throws RemoteException;

    public void requestMaximumTxDataLength(BluetoothDevice var1) throws RemoteException;

    public static class Default
    implements IBluetoothSocketManager {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public ParcelFileDescriptor connectSocket(BluetoothDevice bluetoothDevice, int n, ParcelUuid parcelUuid, int n2, int n3) throws RemoteException {
            return null;
        }

        @Override
        public ParcelFileDescriptor createSocketChannel(int n, String string2, ParcelUuid parcelUuid, int n2, int n3) throws RemoteException {
            return null;
        }

        @Override
        public void requestMaximumTxDataLength(BluetoothDevice bluetoothDevice) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IBluetoothSocketManager {
        private static final String DESCRIPTOR = "android.bluetooth.IBluetoothSocketManager";
        static final int TRANSACTION_connectSocket = 1;
        static final int TRANSACTION_createSocketChannel = 2;
        static final int TRANSACTION_requestMaximumTxDataLength = 3;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IBluetoothSocketManager asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IBluetoothSocketManager) {
                return (IBluetoothSocketManager)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IBluetoothSocketManager getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            if (n != 1) {
                if (n != 2) {
                    if (n != 3) {
                        return null;
                    }
                    return "requestMaximumTxDataLength";
                }
                return "createSocketChannel";
            }
            return "connectSocket";
        }

        public static boolean setDefaultImpl(IBluetoothSocketManager iBluetoothSocketManager) {
            if (Proxy.sDefaultImpl == null && iBluetoothSocketManager != null) {
                Proxy.sDefaultImpl = iBluetoothSocketManager;
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
                        if (n != 1598968902) {
                            return super.onTransact(n, (Parcel)object, parcel, n2);
                        }
                        parcel.writeString(DESCRIPTOR);
                        return true;
                    }
                    ((Parcel)object).enforceInterface(DESCRIPTOR);
                    object = ((Parcel)object).readInt() != 0 ? BluetoothDevice.CREATOR.createFromParcel((Parcel)object) : null;
                    this.requestMaximumTxDataLength((BluetoothDevice)object);
                    parcel.writeNoException();
                    return true;
                }
                ((Parcel)object).enforceInterface(DESCRIPTOR);
                n = ((Parcel)object).readInt();
                String string2 = ((Parcel)object).readString();
                ParcelUuid parcelUuid = ((Parcel)object).readInt() != 0 ? ParcelUuid.CREATOR.createFromParcel((Parcel)object) : null;
                object = this.createSocketChannel(n, string2, parcelUuid, ((Parcel)object).readInt(), ((Parcel)object).readInt());
                parcel.writeNoException();
                if (object != null) {
                    parcel.writeInt(1);
                    ((ParcelFileDescriptor)object).writeToParcel(parcel, 1);
                } else {
                    parcel.writeInt(0);
                }
                return true;
            }
            ((Parcel)object).enforceInterface(DESCRIPTOR);
            BluetoothDevice bluetoothDevice = ((Parcel)object).readInt() != 0 ? BluetoothDevice.CREATOR.createFromParcel((Parcel)object) : null;
            n = ((Parcel)object).readInt();
            ParcelUuid parcelUuid = ((Parcel)object).readInt() != 0 ? ParcelUuid.CREATOR.createFromParcel((Parcel)object) : null;
            object = this.connectSocket(bluetoothDevice, n, parcelUuid, ((Parcel)object).readInt(), ((Parcel)object).readInt());
            parcel.writeNoException();
            if (object != null) {
                parcel.writeInt(1);
                ((ParcelFileDescriptor)object).writeToParcel(parcel, 1);
            } else {
                parcel.writeInt(0);
            }
            return true;
        }

        private static class Proxy
        implements IBluetoothSocketManager {
            public static IBluetoothSocketManager sDefaultImpl;
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override
            public IBinder asBinder() {
                return this.mRemote;
            }

            /*
             * WARNING - void declaration
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public ParcelFileDescriptor connectSocket(BluetoothDevice parcelable, int n, ParcelUuid parcelUuid, int n2, int n3) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    void var3_8;
                    void var2_7;
                    void var4_9;
                    void var5_10;
                    void var1_5;
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (parcelable != null) {
                        parcel.writeInt(1);
                        ((BluetoothDevice)parcelable).writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeInt((int)var2_7);
                    if (var3_8 != null) {
                        parcel.writeInt(1);
                        var3_8.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeInt((int)var4_9);
                    parcel.writeInt((int)var5_10);
                    if (!this.mRemote.transact(1, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        ParcelFileDescriptor parcelFileDescriptor = Stub.getDefaultImpl().connectSocket((BluetoothDevice)parcelable, (int)var2_7, (ParcelUuid)var3_8, (int)var4_9, (int)var5_10);
                        parcel2.recycle();
                        parcel.recycle();
                        return parcelFileDescriptor;
                    }
                    parcel2.readException();
                    if (parcel2.readInt() != 0) {
                        ParcelFileDescriptor parcelFileDescriptor = ParcelFileDescriptor.CREATOR.createFromParcel(parcel2);
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

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public ParcelFileDescriptor createSocketChannel(int n, String object, ParcelUuid parcelUuid, int n2, int n3) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeString((String)object);
                    if (parcelUuid != null) {
                        parcel.writeInt(1);
                        parcelUuid.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeInt(n2);
                    parcel.writeInt(n3);
                    if (!this.mRemote.transact(2, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        object = Stub.getDefaultImpl().createSocketChannel(n, (String)object, parcelUuid, n2, n3);
                        parcel2.recycle();
                        parcel.recycle();
                        return object;
                    }
                    parcel2.readException();
                    object = parcel2.readInt() != 0 ? ParcelFileDescriptor.CREATOR.createFromParcel(parcel2) : null;
                    parcel2.recycle();
                    parcel.recycle();
                    return object;
                }
                catch (Throwable throwable) {
                    parcel2.recycle();
                    parcel.recycle();
                    throw throwable;
                }
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            @Override
            public void requestMaximumTxDataLength(BluetoothDevice bluetoothDevice) throws RemoteException {
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
                        Stub.getDefaultImpl().requestMaximumTxDataLength(bluetoothDevice);
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

