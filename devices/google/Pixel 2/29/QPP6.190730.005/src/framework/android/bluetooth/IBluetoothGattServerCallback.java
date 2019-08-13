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

public interface IBluetoothGattServerCallback
extends IInterface {
    public void onCharacteristicReadRequest(String var1, int var2, int var3, boolean var4, int var5) throws RemoteException;

    public void onCharacteristicWriteRequest(String var1, int var2, int var3, int var4, boolean var5, boolean var6, int var7, byte[] var8) throws RemoteException;

    public void onConnectionUpdated(String var1, int var2, int var3, int var4, int var5) throws RemoteException;

    public void onDescriptorReadRequest(String var1, int var2, int var3, boolean var4, int var5) throws RemoteException;

    public void onDescriptorWriteRequest(String var1, int var2, int var3, int var4, boolean var5, boolean var6, int var7, byte[] var8) throws RemoteException;

    public void onExecuteWrite(String var1, int var2, boolean var3) throws RemoteException;

    public void onMtuChanged(String var1, int var2) throws RemoteException;

    public void onNotificationSent(String var1, int var2) throws RemoteException;

    public void onPhyRead(String var1, int var2, int var3, int var4) throws RemoteException;

    public void onPhyUpdate(String var1, int var2, int var3, int var4) throws RemoteException;

    public void onServerConnectionState(int var1, int var2, boolean var3, String var4) throws RemoteException;

    public void onServerRegistered(int var1, int var2) throws RemoteException;

    public void onServiceAdded(int var1, BluetoothGattService var2) throws RemoteException;

    public static class Default
    implements IBluetoothGattServerCallback {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void onCharacteristicReadRequest(String string2, int n, int n2, boolean bl, int n3) throws RemoteException {
        }

        @Override
        public void onCharacteristicWriteRequest(String string2, int n, int n2, int n3, boolean bl, boolean bl2, int n4, byte[] arrby) throws RemoteException {
        }

        @Override
        public void onConnectionUpdated(String string2, int n, int n2, int n3, int n4) throws RemoteException {
        }

        @Override
        public void onDescriptorReadRequest(String string2, int n, int n2, boolean bl, int n3) throws RemoteException {
        }

        @Override
        public void onDescriptorWriteRequest(String string2, int n, int n2, int n3, boolean bl, boolean bl2, int n4, byte[] arrby) throws RemoteException {
        }

        @Override
        public void onExecuteWrite(String string2, int n, boolean bl) throws RemoteException {
        }

        @Override
        public void onMtuChanged(String string2, int n) throws RemoteException {
        }

        @Override
        public void onNotificationSent(String string2, int n) throws RemoteException {
        }

        @Override
        public void onPhyRead(String string2, int n, int n2, int n3) throws RemoteException {
        }

        @Override
        public void onPhyUpdate(String string2, int n, int n2, int n3) throws RemoteException {
        }

        @Override
        public void onServerConnectionState(int n, int n2, boolean bl, String string2) throws RemoteException {
        }

        @Override
        public void onServerRegistered(int n, int n2) throws RemoteException {
        }

        @Override
        public void onServiceAdded(int n, BluetoothGattService bluetoothGattService) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IBluetoothGattServerCallback {
        private static final String DESCRIPTOR = "android.bluetooth.IBluetoothGattServerCallback";
        static final int TRANSACTION_onCharacteristicReadRequest = 4;
        static final int TRANSACTION_onCharacteristicWriteRequest = 6;
        static final int TRANSACTION_onConnectionUpdated = 13;
        static final int TRANSACTION_onDescriptorReadRequest = 5;
        static final int TRANSACTION_onDescriptorWriteRequest = 7;
        static final int TRANSACTION_onExecuteWrite = 8;
        static final int TRANSACTION_onMtuChanged = 10;
        static final int TRANSACTION_onNotificationSent = 9;
        static final int TRANSACTION_onPhyRead = 12;
        static final int TRANSACTION_onPhyUpdate = 11;
        static final int TRANSACTION_onServerConnectionState = 2;
        static final int TRANSACTION_onServerRegistered = 1;
        static final int TRANSACTION_onServiceAdded = 3;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IBluetoothGattServerCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IBluetoothGattServerCallback) {
                return (IBluetoothGattServerCallback)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IBluetoothGattServerCallback getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            switch (n) {
                default: {
                    return null;
                }
                case 13: {
                    return "onConnectionUpdated";
                }
                case 12: {
                    return "onPhyRead";
                }
                case 11: {
                    return "onPhyUpdate";
                }
                case 10: {
                    return "onMtuChanged";
                }
                case 9: {
                    return "onNotificationSent";
                }
                case 8: {
                    return "onExecuteWrite";
                }
                case 7: {
                    return "onDescriptorWriteRequest";
                }
                case 6: {
                    return "onCharacteristicWriteRequest";
                }
                case 5: {
                    return "onDescriptorReadRequest";
                }
                case 4: {
                    return "onCharacteristicReadRequest";
                }
                case 3: {
                    return "onServiceAdded";
                }
                case 2: {
                    return "onServerConnectionState";
                }
                case 1: 
            }
            return "onServerRegistered";
        }

        public static boolean setDefaultImpl(IBluetoothGattServerCallback iBluetoothGattServerCallback) {
            if (Proxy.sDefaultImpl == null && iBluetoothGattServerCallback != null) {
                Proxy.sDefaultImpl = iBluetoothGattServerCallback;
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
        public boolean onTransact(int n, Parcel object, Parcel object2, int n2) throws RemoteException {
            if (n != 1598968902) {
                boolean bl = false;
                boolean bl2 = false;
                switch (n) {
                    default: {
                        return super.onTransact(n, (Parcel)object, (Parcel)object2, n2);
                    }
                    case 13: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.onConnectionUpdated(((Parcel)object).readString(), ((Parcel)object).readInt(), ((Parcel)object).readInt(), ((Parcel)object).readInt(), ((Parcel)object).readInt());
                        return true;
                    }
                    case 12: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.onPhyRead(((Parcel)object).readString(), ((Parcel)object).readInt(), ((Parcel)object).readInt(), ((Parcel)object).readInt());
                        return true;
                    }
                    case 11: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.onPhyUpdate(((Parcel)object).readString(), ((Parcel)object).readInt(), ((Parcel)object).readInt(), ((Parcel)object).readInt());
                        return true;
                    }
                    case 10: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.onMtuChanged(((Parcel)object).readString(), ((Parcel)object).readInt());
                        return true;
                    }
                    case 9: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.onNotificationSent(((Parcel)object).readString(), ((Parcel)object).readInt());
                        return true;
                    }
                    case 8: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object2 = ((Parcel)object).readString();
                        n = ((Parcel)object).readInt();
                        if (((Parcel)object).readInt() != 0) {
                            bl2 = true;
                        }
                        this.onExecuteWrite((String)object2, n, bl2);
                        return true;
                    }
                    case 7: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object2 = ((Parcel)object).readString();
                        int n3 = ((Parcel)object).readInt();
                        n = ((Parcel)object).readInt();
                        n2 = ((Parcel)object).readInt();
                        bl2 = ((Parcel)object).readInt() != 0;
                        bl = ((Parcel)object).readInt() != 0;
                        this.onDescriptorWriteRequest((String)object2, n3, n, n2, bl2, bl, ((Parcel)object).readInt(), ((Parcel)object).createByteArray());
                        return true;
                    }
                    case 6: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object2 = ((Parcel)object).readString();
                        int n4 = ((Parcel)object).readInt();
                        n = ((Parcel)object).readInt();
                        n2 = ((Parcel)object).readInt();
                        bl2 = ((Parcel)object).readInt() != 0;
                        bl = ((Parcel)object).readInt() != 0;
                        this.onCharacteristicWriteRequest((String)object2, n4, n, n2, bl2, bl, ((Parcel)object).readInt(), ((Parcel)object).createByteArray());
                        return true;
                    }
                    case 5: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object2 = ((Parcel)object).readString();
                        n = ((Parcel)object).readInt();
                        n2 = ((Parcel)object).readInt();
                        bl2 = ((Parcel)object).readInt() != 0;
                        this.onDescriptorReadRequest((String)object2, n, n2, bl2, ((Parcel)object).readInt());
                        return true;
                    }
                    case 4: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object2 = ((Parcel)object).readString();
                        n = ((Parcel)object).readInt();
                        n2 = ((Parcel)object).readInt();
                        bl2 = ((Parcel)object).readInt() != 0;
                        this.onCharacteristicReadRequest((String)object2, n, n2, bl2, ((Parcel)object).readInt());
                        return true;
                    }
                    case 3: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = ((Parcel)object).readInt();
                        object = ((Parcel)object).readInt() != 0 ? BluetoothGattService.CREATOR.createFromParcel((Parcel)object) : null;
                        this.onServiceAdded(n, (BluetoothGattService)object);
                        return true;
                    }
                    case 2: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = ((Parcel)object).readInt();
                        n2 = ((Parcel)object).readInt();
                        bl2 = bl;
                        if (((Parcel)object).readInt() != 0) {
                            bl2 = true;
                        }
                        this.onServerConnectionState(n, n2, bl2, ((Parcel)object).readString());
                        return true;
                    }
                    case 1: 
                }
                ((Parcel)object).enforceInterface(DESCRIPTOR);
                this.onServerRegistered(((Parcel)object).readInt(), ((Parcel)object).readInt());
                return true;
            }
            ((Parcel)object2).writeString(DESCRIPTOR);
            return true;
        }

        private static class Proxy
        implements IBluetoothGattServerCallback {
            public static IBluetoothGattServerCallback sDefaultImpl;
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
            public void onCharacteristicReadRequest(String string2, int n, int n2, boolean bl, int n3) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                parcel.writeString(string2);
                parcel.writeInt(n);
                parcel.writeInt(n2);
                int n4 = bl ? 1 : 0;
                try {
                    parcel.writeInt(n4);
                    parcel.writeInt(n3);
                    if (!this.mRemote.transact(4, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onCharacteristicReadRequest(string2, n, n2, bl, n3);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            /*
             * Loose catch block
             * WARNING - void declaration
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             * Lifted jumps to return sites
             */
            @Override
            public void onCharacteristicWriteRequest(String string2, int n, int n2, int n3, boolean bl, boolean bl2, int n4, byte[] arrby) throws RemoteException {
                Parcel parcel;
                void var1_8;
                block14 : {
                    parcel = Parcel.obtain();
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    try {
                        parcel.writeString(string2);
                    }
                    catch (Throwable throwable) {
                        break block14;
                    }
                    try {
                        parcel.writeInt(n);
                    }
                    catch (Throwable throwable) {
                        break block14;
                    }
                    try {
                        parcel.writeInt(n2);
                    }
                    catch (Throwable throwable) {
                        break block14;
                    }
                    try {
                        parcel.writeInt(n3);
                        int n6 = 0;
                        int n5 = bl ? 1 : 0;
                        parcel.writeInt(n5);
                        n5 = n6;
                        if (bl2) {
                            n5 = 1;
                        }
                        parcel.writeInt(n5);
                    }
                    catch (Throwable throwable) {}
                    try {
                        parcel.writeInt(n4);
                        parcel.writeByteArray(arrby);
                        if (!this.mRemote.transact(6, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                            Stub.getDefaultImpl().onCharacteristicWriteRequest(string2, n, n2, n3, bl, bl2, n4, arrby);
                            parcel.recycle();
                            return;
                        }
                        parcel.recycle();
                        return;
                    }
                    catch (Throwable throwable) {}
                    break block14;
                    catch (Throwable throwable) {
                        // empty catch block
                    }
                }
                parcel.recycle();
                throw var1_8;
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
                    if (!this.mRemote.transact(13, parcel, null, 1) && Stub.getDefaultImpl() != null) {
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
            public void onDescriptorReadRequest(String string2, int n, int n2, boolean bl, int n3) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                parcel.writeString(string2);
                parcel.writeInt(n);
                parcel.writeInt(n2);
                int n4 = bl ? 1 : 0;
                try {
                    parcel.writeInt(n4);
                    parcel.writeInt(n3);
                    if (!this.mRemote.transact(5, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onDescriptorReadRequest(string2, n, n2, bl, n3);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            /*
             * Loose catch block
             * WARNING - void declaration
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             * Lifted jumps to return sites
             */
            @Override
            public void onDescriptorWriteRequest(String string2, int n, int n2, int n3, boolean bl, boolean bl2, int n4, byte[] arrby) throws RemoteException {
                Parcel parcel;
                void var1_8;
                block14 : {
                    parcel = Parcel.obtain();
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    try {
                        parcel.writeString(string2);
                    }
                    catch (Throwable throwable) {
                        break block14;
                    }
                    try {
                        parcel.writeInt(n);
                    }
                    catch (Throwable throwable) {
                        break block14;
                    }
                    try {
                        parcel.writeInt(n2);
                    }
                    catch (Throwable throwable) {
                        break block14;
                    }
                    try {
                        parcel.writeInt(n3);
                        int n6 = 0;
                        int n5 = bl ? 1 : 0;
                        parcel.writeInt(n5);
                        n5 = n6;
                        if (bl2) {
                            n5 = 1;
                        }
                        parcel.writeInt(n5);
                    }
                    catch (Throwable throwable) {}
                    try {
                        parcel.writeInt(n4);
                        parcel.writeByteArray(arrby);
                        if (!this.mRemote.transact(7, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                            Stub.getDefaultImpl().onDescriptorWriteRequest(string2, n, n2, n3, bl, bl2, n4, arrby);
                            parcel.recycle();
                            return;
                        }
                        parcel.recycle();
                        return;
                    }
                    catch (Throwable throwable) {}
                    break block14;
                    catch (Throwable throwable) {
                        // empty catch block
                    }
                }
                parcel.recycle();
                throw var1_8;
            }

            @Override
            public void onExecuteWrite(String string2, int n, boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                parcel.writeString(string2);
                parcel.writeInt(n);
                int n2 = bl ? 1 : 0;
                try {
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(8, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onExecuteWrite(string2, n, bl);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onMtuChanged(String string2, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(10, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onMtuChanged(string2, n);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onNotificationSent(String string2, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(9, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onNotificationSent(string2, n);
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
                    if (!this.mRemote.transact(12, parcel, null, 1) && Stub.getDefaultImpl() != null) {
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
                    if (!this.mRemote.transact(11, parcel, null, 1) && Stub.getDefaultImpl() != null) {
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
            public void onServerConnectionState(int n, int n2, boolean bl, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                parcel.writeInt(n);
                parcel.writeInt(n2);
                int n3 = bl ? 1 : 0;
                try {
                    parcel.writeInt(n3);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(2, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onServerConnectionState(n, n2, bl, string2);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onServerRegistered(int n, int n2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(1, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onServerRegistered(n, n2);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onServiceAdded(int n, BluetoothGattService bluetoothGattService) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (bluetoothGattService != null) {
                        parcel.writeInt(1);
                        bluetoothGattService.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(3, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onServiceAdded(n, bluetoothGattService);
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

