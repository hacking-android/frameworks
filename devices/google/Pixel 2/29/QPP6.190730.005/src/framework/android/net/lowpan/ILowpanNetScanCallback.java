/*
 * Decompiled with CFR 0.145.
 */
package android.net.lowpan;

import android.net.lowpan.LowpanBeaconInfo;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;

public interface ILowpanNetScanCallback
extends IInterface {
    public void onNetScanBeacon(LowpanBeaconInfo var1) throws RemoteException;

    public void onNetScanFinished() throws RemoteException;

    public static class Default
    implements ILowpanNetScanCallback {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void onNetScanBeacon(LowpanBeaconInfo lowpanBeaconInfo) throws RemoteException {
        }

        @Override
        public void onNetScanFinished() throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements ILowpanNetScanCallback {
        private static final String DESCRIPTOR = "android.net.lowpan.ILowpanNetScanCallback";
        static final int TRANSACTION_onNetScanBeacon = 1;
        static final int TRANSACTION_onNetScanFinished = 2;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static ILowpanNetScanCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof ILowpanNetScanCallback) {
                return (ILowpanNetScanCallback)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static ILowpanNetScanCallback getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            if (n != 1) {
                if (n != 2) {
                    return null;
                }
                return "onNetScanFinished";
            }
            return "onNetScanBeacon";
        }

        public static boolean setDefaultImpl(ILowpanNetScanCallback iLowpanNetScanCallback) {
            if (Proxy.sDefaultImpl == null && iLowpanNetScanCallback != null) {
                Proxy.sDefaultImpl = iLowpanNetScanCallback;
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
                    if (n != 1598968902) {
                        return super.onTransact(n, (Parcel)object, parcel, n2);
                    }
                    parcel.writeString(DESCRIPTOR);
                    return true;
                }
                ((Parcel)object).enforceInterface(DESCRIPTOR);
                this.onNetScanFinished();
                return true;
            }
            ((Parcel)object).enforceInterface(DESCRIPTOR);
            object = ((Parcel)object).readInt() != 0 ? LowpanBeaconInfo.CREATOR.createFromParcel((Parcel)object) : null;
            this.onNetScanBeacon((LowpanBeaconInfo)object);
            return true;
        }

        private static class Proxy
        implements ILowpanNetScanCallback {
            public static ILowpanNetScanCallback sDefaultImpl;
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
            public void onNetScanBeacon(LowpanBeaconInfo lowpanBeaconInfo) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (lowpanBeaconInfo != null) {
                        parcel.writeInt(1);
                        lowpanBeaconInfo.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(1, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onNetScanBeacon(lowpanBeaconInfo);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onNetScanFinished() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(2, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onNetScanFinished();
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

