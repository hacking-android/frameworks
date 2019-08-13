/*
 * Decompiled with CFR 0.145.
 */
package android.bluetooth;

import android.bluetooth.BluetoothGattService;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import java.util.ArrayList;
import java.util.List;

public interface IBluetoothGattCallback
extends IInterface {
    public void onCharacteristicRead(String var1, int var2, int var3, byte[] var4) throws RemoteException;

    public void onCharacteristicWrite(String var1, int var2, int var3) throws RemoteException;

    public void onClientConnectionState(int var1, int var2, boolean var3, String var4) throws RemoteException;

    public void onClientRegistered(int var1, int var2) throws RemoteException;

    public void onConfigureMTU(String var1, int var2, int var3) throws RemoteException;

    public void onConnectionUpdated(String var1, int var2, int var3, int var4, int var5) throws RemoteException;

    public void onDescriptorRead(String var1, int var2, int var3, byte[] var4) throws RemoteException;

    public void onDescriptorWrite(String var1, int var2, int var3) throws RemoteException;

    public void onExecuteWrite(String var1, int var2) throws RemoteException;

    public void onNotify(String var1, int var2, byte[] var3) throws RemoteException;

    public void onPhyRead(String var1, int var2, int var3, int var4) throws RemoteException;

    public void onPhyUpdate(String var1, int var2, int var3, int var4) throws RemoteException;

    public void onReadRemoteRssi(String var1, int var2, int var3) throws RemoteException;

    public void onSearchComplete(String var1, List<BluetoothGattService> var2, int var3) throws RemoteException;

    public static class Default
    implements IBluetoothGattCallback {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void onCharacteristicRead(String string2, int n, int n2, byte[] arrby) throws RemoteException {
        }

        @Override
        public void onCharacteristicWrite(String string2, int n, int n2) throws RemoteException {
        }

        @Override
        public void onClientConnectionState(int n, int n2, boolean bl, String string2) throws RemoteException {
        }

        @Override
        public void onClientRegistered(int n, int n2) throws RemoteException {
        }

        @Override
        public void onConfigureMTU(String string2, int n, int n2) throws RemoteException {
        }

        @Override
        public void onConnectionUpdated(String string2, int n, int n2, int n3, int n4) throws RemoteException {
        }

        @Override
        public void onDescriptorRead(String string2, int n, int n2, byte[] arrby) throws RemoteException {
        }

        @Override
        public void onDescriptorWrite(String string2, int n, int n2) throws RemoteException {
        }

        @Override
        public void onExecuteWrite(String string2, int n) throws RemoteException {
        }

        @Override
        public void onNotify(String string2, int n, byte[] arrby) throws RemoteException {
        }

        @Override
        public void onPhyRead(String string2, int n, int n2, int n3) throws RemoteException {
        }

        @Override
        public void onPhyUpdate(String string2, int n, int n2, int n3) throws RemoteException {
        }

        @Override
        public void onReadRemoteRssi(String string2, int n, int n2) throws RemoteException {
        }

        @Override
        public void onSearchComplete(String string2, List<BluetoothGattService> list, int n) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IBluetoothGattCallback {
        private static final String DESCRIPTOR = "android.bluetooth.IBluetoothGattCallback";
        static final int TRANSACTION_onCharacteristicRead = 6;
        static final int TRANSACTION_onCharacteristicWrite = 7;
        static final int TRANSACTION_onClientConnectionState = 2;
        static final int TRANSACTION_onClientRegistered = 1;
        static final int TRANSACTION_onConfigureMTU = 13;
        static final int TRANSACTION_onConnectionUpdated = 14;
        static final int TRANSACTION_onDescriptorRead = 9;
        static final int TRANSACTION_onDescriptorWrite = 10;
        static final int TRANSACTION_onExecuteWrite = 8;
        static final int TRANSACTION_onNotify = 11;
        static final int TRANSACTION_onPhyRead = 4;
        static final int TRANSACTION_onPhyUpdate = 3;
        static final int TRANSACTION_onReadRemoteRssi = 12;
        static final int TRANSACTION_onSearchComplete = 5;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IBluetoothGattCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IBluetoothGattCallback) {
                return (IBluetoothGattCallback)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IBluetoothGattCallback getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            switch (n) {
                default: {
                    return null;
                }
                case 14: {
                    return "onConnectionUpdated";
                }
                case 13: {
                    return "onConfigureMTU";
                }
                case 12: {
                    return "onReadRemoteRssi";
                }
                case 11: {
                    return "onNotify";
                }
                case 10: {
                    return "onDescriptorWrite";
                }
                case 9: {
                    return "onDescriptorRead";
                }
                case 8: {
                    return "onExecuteWrite";
                }
                case 7: {
                    return "onCharacteristicWrite";
                }
                case 6: {
                    return "onCharacteristicRead";
                }
                case 5: {
                    return "onSearchComplete";
                }
                case 4: {
                    return "onPhyRead";
                }
                case 3: {
                    return "onPhyUpdate";
                }
                case 2: {
                    return "onClientConnectionState";
                }
                case 1: 
            }
            return "onClientRegistered";
        }

        public static boolean setDefaultImpl(IBluetoothGattCallback iBluetoothGattCallback) {
            if (Proxy.sDefaultImpl == null && iBluetoothGattCallback != null) {
                Proxy.sDefaultImpl = iBluetoothGattCallback;
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
            if (n != 1598968902) {
                switch (n) {
                    default: {
                        return super.onTransact(n, parcel, parcel2, n2);
                    }
                    case 14: {
                        parcel.enforceInterface(DESCRIPTOR);
                        this.onConnectionUpdated(parcel.readString(), parcel.readInt(), parcel.readInt(), parcel.readInt(), parcel.readInt());
                        return true;
                    }
                    case 13: {
                        parcel.enforceInterface(DESCRIPTOR);
                        this.onConfigureMTU(parcel.readString(), parcel.readInt(), parcel.readInt());
                        return true;
                    }
                    case 12: {
                        parcel.enforceInterface(DESCRIPTOR);
                        this.onReadRemoteRssi(parcel.readString(), parcel.readInt(), parcel.readInt());
                        return true;
                    }
                    case 11: {
                        parcel.enforceInterface(DESCRIPTOR);
                        this.onNotify(parcel.readString(), parcel.readInt(), parcel.createByteArray());
                        return true;
                    }
                    case 10: {
                        parcel.enforceInterface(DESCRIPTOR);
                        this.onDescriptorWrite(parcel.readString(), parcel.readInt(), parcel.readInt());
                        return true;
                    }
                    case 9: {
                        parcel.enforceInterface(DESCRIPTOR);
                        this.onDescriptorRead(parcel.readString(), parcel.readInt(), parcel.readInt(), parcel.createByteArray());
                        return true;
                    }
                    case 8: {
                        parcel.enforceInterface(DESCRIPTOR);
                        this.onExecuteWrite(parcel.readString(), parcel.readInt());
                        return true;
                    }
                    case 7: {
                        parcel.enforceInterface(DESCRIPTOR);
                        this.onCharacteristicWrite(parcel.readString(), parcel.readInt(), parcel.readInt());
                        return true;
                    }
                    case 6: {
                        parcel.enforceInterface(DESCRIPTOR);
                        this.onCharacteristicRead(parcel.readString(), parcel.readInt(), parcel.readInt(), parcel.createByteArray());
                        return true;
                    }
                    case 5: {
                        parcel.enforceInterface(DESCRIPTOR);
                        this.onSearchComplete(parcel.readString(), parcel.createTypedArrayList(BluetoothGattService.CREATOR), parcel.readInt());
                        return true;
                    }
                    case 4: {
                        parcel.enforceInterface(DESCRIPTOR);
                        this.onPhyRead(parcel.readString(), parcel.readInt(), parcel.readInt(), parcel.readInt());
                        return true;
                    }
                    case 3: {
                        parcel.enforceInterface(DESCRIPTOR);
                        this.onPhyUpdate(parcel.readString(), parcel.readInt(), parcel.readInt(), parcel.readInt());
                        return true;
                    }
                    case 2: {
                        parcel.enforceInterface(DESCRIPTOR);
                        n2 = parcel.readInt();
                        n = parcel.readInt();
                        boolean bl = parcel.readInt() != 0;
                        this.onClientConnectionState(n2, n, bl, parcel.readString());
                        return true;
                    }
                    case 1: 
                }
                parcel.enforceInterface(DESCRIPTOR);
                this.onClientRegistered(parcel.readInt(), parcel.readInt());
                return true;
            }
            parcel2.writeString(DESCRIPTOR);
            return true;
        }

        private static class Proxy
        implements IBluetoothGattCallback {
            public static IBluetoothGattCallback sDefaultImpl;
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
            public void onCharacteristicRead(String string2, int n, int n2, byte[] arrby) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    parcel.writeByteArray(arrby);
                    if (!this.mRemote.transact(6, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onCharacteristicRead(string2, n, n2, arrby);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onCharacteristicWrite(String string2, int n, int n2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(7, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onCharacteristicWrite(string2, n, n2);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onClientConnectionState(int n, int n2, boolean bl, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                parcel.writeInt(n);
                parcel.writeInt(n2);
                int n3 = bl ? 1 : 0;
                try {
                    parcel.writeInt(n3);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(2, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onClientConnectionState(n, n2, bl, string2);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onClientRegistered(int n, int n2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(1, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onClientRegistered(n, n2);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onConfigureMTU(String string2, int n, int n2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(13, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onConfigureMTU(string2, n, n2);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onConnectionUpdated(String string2, int n, int n2, int n3, int n4) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    parcel.writeInt(n3);
                    parcel.writeInt(n4);
                    if (!this.mRemote.transact(14, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onConnectionUpdated(string2, n, n2, n3, n4);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onDescriptorRead(String string2, int n, int n2, byte[] arrby) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    parcel.writeByteArray(arrby);
                    if (!this.mRemote.transact(9, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onDescriptorRead(string2, n, n2, arrby);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onDescriptorWrite(String string2, int n, int n2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(10, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onDescriptorWrite(string2, n, n2);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onExecuteWrite(String string2, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(8, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onExecuteWrite(string2, n);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onNotify(String string2, int n, byte[] arrby) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    parcel.writeInt(n);
                    parcel.writeByteArray(arrby);
                    if (!this.mRemote.transact(11, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onNotify(string2, n, arrby);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onPhyRead(String string2, int n, int n2, int n3) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    parcel.writeInt(n3);
                    if (!this.mRemote.transact(4, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onPhyRead(string2, n, n2, n3);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onPhyUpdate(String string2, int n, int n2, int n3) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    parcel.writeInt(n3);
                    if (!this.mRemote.transact(3, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onPhyUpdate(string2, n, n2, n3);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onReadRemoteRssi(String string2, int n, int n2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(12, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onReadRemoteRssi(string2, n, n2);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onSearchComplete(String string2, List<BluetoothGattService> list, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    parcel.writeTypedList(list);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(5, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onSearchComplete(string2, list, n);
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

