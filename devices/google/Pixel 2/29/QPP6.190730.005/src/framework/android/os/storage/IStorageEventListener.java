/*
 * Decompiled with CFR 0.145.
 */
package android.os.storage;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import android.os.storage.DiskInfo;
import android.os.storage.VolumeInfo;
import android.os.storage.VolumeRecord;

public interface IStorageEventListener
extends IInterface {
    public void onDiskDestroyed(DiskInfo var1) throws RemoteException;

    public void onDiskScanned(DiskInfo var1, int var2) throws RemoteException;

    public void onStorageStateChanged(String var1, String var2, String var3) throws RemoteException;

    public void onUsbMassStorageConnectionChanged(boolean var1) throws RemoteException;

    public void onVolumeForgotten(String var1) throws RemoteException;

    public void onVolumeRecordChanged(VolumeRecord var1) throws RemoteException;

    public void onVolumeStateChanged(VolumeInfo var1, int var2, int var3) throws RemoteException;

    public static class Default
    implements IStorageEventListener {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void onDiskDestroyed(DiskInfo diskInfo) throws RemoteException {
        }

        @Override
        public void onDiskScanned(DiskInfo diskInfo, int n) throws RemoteException {
        }

        @Override
        public void onStorageStateChanged(String string2, String string3, String string4) throws RemoteException {
        }

        @Override
        public void onUsbMassStorageConnectionChanged(boolean bl) throws RemoteException {
        }

        @Override
        public void onVolumeForgotten(String string2) throws RemoteException {
        }

        @Override
        public void onVolumeRecordChanged(VolumeRecord volumeRecord) throws RemoteException {
        }

        @Override
        public void onVolumeStateChanged(VolumeInfo volumeInfo, int n, int n2) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IStorageEventListener {
        private static final String DESCRIPTOR = "android.os.storage.IStorageEventListener";
        static final int TRANSACTION_onDiskDestroyed = 7;
        static final int TRANSACTION_onDiskScanned = 6;
        static final int TRANSACTION_onStorageStateChanged = 2;
        static final int TRANSACTION_onUsbMassStorageConnectionChanged = 1;
        static final int TRANSACTION_onVolumeForgotten = 5;
        static final int TRANSACTION_onVolumeRecordChanged = 4;
        static final int TRANSACTION_onVolumeStateChanged = 3;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IStorageEventListener asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IStorageEventListener) {
                return (IStorageEventListener)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IStorageEventListener getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            switch (n) {
                default: {
                    return null;
                }
                case 7: {
                    return "onDiskDestroyed";
                }
                case 6: {
                    return "onDiskScanned";
                }
                case 5: {
                    return "onVolumeForgotten";
                }
                case 4: {
                    return "onVolumeRecordChanged";
                }
                case 3: {
                    return "onVolumeStateChanged";
                }
                case 2: {
                    return "onStorageStateChanged";
                }
                case 1: 
            }
            return "onUsbMassStorageConnectionChanged";
        }

        public static boolean setDefaultImpl(IStorageEventListener iStorageEventListener) {
            if (Proxy.sDefaultImpl == null && iStorageEventListener != null) {
                Proxy.sDefaultImpl = iStorageEventListener;
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
                switch (n) {
                    default: {
                        return super.onTransact(n, (Parcel)object, (Parcel)object2, n2);
                    }
                    case 7: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = ((Parcel)object).readInt() != 0 ? DiskInfo.CREATOR.createFromParcel((Parcel)object) : null;
                        this.onDiskDestroyed((DiskInfo)object);
                        return true;
                    }
                    case 6: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object2 = ((Parcel)object).readInt() != 0 ? DiskInfo.CREATOR.createFromParcel((Parcel)object) : null;
                        this.onDiskScanned((DiskInfo)object2, ((Parcel)object).readInt());
                        return true;
                    }
                    case 5: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.onVolumeForgotten(((Parcel)object).readString());
                        return true;
                    }
                    case 4: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = ((Parcel)object).readInt() != 0 ? VolumeRecord.CREATOR.createFromParcel((Parcel)object) : null;
                        this.onVolumeRecordChanged((VolumeRecord)object);
                        return true;
                    }
                    case 3: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object2 = ((Parcel)object).readInt() != 0 ? VolumeInfo.CREATOR.createFromParcel((Parcel)object) : null;
                        this.onVolumeStateChanged((VolumeInfo)object2, ((Parcel)object).readInt(), ((Parcel)object).readInt());
                        return true;
                    }
                    case 2: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.onStorageStateChanged(((Parcel)object).readString(), ((Parcel)object).readString(), ((Parcel)object).readString());
                        return true;
                    }
                    case 1: 
                }
                ((Parcel)object).enforceInterface(DESCRIPTOR);
                boolean bl = ((Parcel)object).readInt() != 0;
                this.onUsbMassStorageConnectionChanged(bl);
                return true;
            }
            ((Parcel)object2).writeString(DESCRIPTOR);
            return true;
        }

        private static class Proxy
        implements IStorageEventListener {
            public static IStorageEventListener sDefaultImpl;
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
            public void onDiskDestroyed(DiskInfo diskInfo) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (diskInfo != null) {
                        parcel.writeInt(1);
                        diskInfo.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(7, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onDiskDestroyed(diskInfo);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onDiskScanned(DiskInfo diskInfo, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (diskInfo != null) {
                        parcel.writeInt(1);
                        diskInfo.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(6, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onDiskScanned(diskInfo, n);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onStorageStateChanged(String string2, String string3, String string4) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    parcel.writeString(string3);
                    parcel.writeString(string4);
                    if (!this.mRemote.transact(2, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onStorageStateChanged(string2, string3, string4);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onUsbMassStorageConnectionChanged(boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                int n = bl ? 1 : 0;
                try {
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(1, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onUsbMassStorageConnectionChanged(bl);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onVolumeForgotten(String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(5, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onVolumeForgotten(string2);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onVolumeRecordChanged(VolumeRecord volumeRecord) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (volumeRecord != null) {
                        parcel.writeInt(1);
                        volumeRecord.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(4, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onVolumeRecordChanged(volumeRecord);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onVolumeStateChanged(VolumeInfo volumeInfo, int n, int n2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (volumeInfo != null) {
                        parcel.writeInt(1);
                        volumeInfo.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(3, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onVolumeStateChanged(volumeInfo, n, n2);
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

