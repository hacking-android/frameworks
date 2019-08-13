/*
 * Decompiled with CFR 0.145.
 */
package android.media.midi;

import android.bluetooth.BluetoothDevice;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;

public interface IBluetoothMidiService
extends IInterface {
    public IBinder addBluetoothDevice(BluetoothDevice var1) throws RemoteException;

    public static class Default
    implements IBluetoothMidiService {
        @Override
        public IBinder addBluetoothDevice(BluetoothDevice bluetoothDevice) throws RemoteException {
            return null;
        }

        @Override
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub
    extends Binder
    implements IBluetoothMidiService {
        private static final String DESCRIPTOR = "android.media.midi.IBluetoothMidiService";
        static final int TRANSACTION_addBluetoothDevice = 1;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IBluetoothMidiService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IBluetoothMidiService) {
                return (IBluetoothMidiService)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IBluetoothMidiService getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            if (n != 1) {
                return null;
            }
            return "addBluetoothDevice";
        }

        public static boolean setDefaultImpl(IBluetoothMidiService iBluetoothMidiService) {
            if (Proxy.sDefaultImpl == null && iBluetoothMidiService != null) {
                Proxy.sDefaultImpl = iBluetoothMidiService;
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
                if (n != 1598968902) {
                    return super.onTransact(n, (Parcel)object, parcel, n2);
                }
                parcel.writeString(DESCRIPTOR);
                return true;
            }
            ((Parcel)object).enforceInterface(DESCRIPTOR);
            object = ((Parcel)object).readInt() != 0 ? BluetoothDevice.CREATOR.createFromParcel((Parcel)object) : null;
            object = this.addBluetoothDevice((BluetoothDevice)object);
            parcel.writeNoException();
            parcel.writeStrongBinder((IBinder)object);
            return true;
        }

        private static class Proxy
        implements IBluetoothMidiService {
            public static IBluetoothMidiService sDefaultImpl;
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override
            public IBinder addBluetoothDevice(BluetoothDevice object) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (object != null) {
                        parcel.writeInt(1);
                        ((BluetoothDevice)object).writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(1, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        object = Stub.getDefaultImpl().addBluetoothDevice((BluetoothDevice)object);
                        return object;
                    }
                    parcel2.readException();
                    object = parcel2.readStrongBinder();
                    return object;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }
        }

    }

}

