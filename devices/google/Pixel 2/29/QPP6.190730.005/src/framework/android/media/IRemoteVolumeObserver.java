/*
 * Decompiled with CFR 0.145.
 */
package android.media;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IRemoteVolumeObserver
extends IInterface {
    public void dispatchRemoteVolumeUpdate(int var1, int var2) throws RemoteException;

    public static class Default
    implements IRemoteVolumeObserver {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void dispatchRemoteVolumeUpdate(int n, int n2) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IRemoteVolumeObserver {
        private static final String DESCRIPTOR = "android.media.IRemoteVolumeObserver";
        static final int TRANSACTION_dispatchRemoteVolumeUpdate = 1;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IRemoteVolumeObserver asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IRemoteVolumeObserver) {
                return (IRemoteVolumeObserver)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IRemoteVolumeObserver getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            if (n != 1) {
                return null;
            }
            return "dispatchRemoteVolumeUpdate";
        }

        public static boolean setDefaultImpl(IRemoteVolumeObserver iRemoteVolumeObserver) {
            if (Proxy.sDefaultImpl == null && iRemoteVolumeObserver != null) {
                Proxy.sDefaultImpl = iRemoteVolumeObserver;
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
            if (n != 1) {
                if (n != 1598968902) {
                    return super.onTransact(n, parcel, parcel2, n2);
                }
                parcel2.writeString(DESCRIPTOR);
                return true;
            }
            parcel.enforceInterface(DESCRIPTOR);
            this.dispatchRemoteVolumeUpdate(parcel.readInt(), parcel.readInt());
            return true;
        }

        private static class Proxy
        implements IRemoteVolumeObserver {
            public static IRemoteVolumeObserver sDefaultImpl;
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override
            public IBinder asBinder() {
                return this.mRemote;
            }

            @Override
            public void dispatchRemoteVolumeUpdate(int n, int n2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(1, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().dispatchRemoteVolumeUpdate(n, n2);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }
        }

    }

}

