/*
 * Decompiled with CFR 0.145.
 */
package android.media;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IMediaResourceMonitor
extends IInterface {
    public void notifyResourceGranted(int var1, int var2) throws RemoteException;

    public static class Default
    implements IMediaResourceMonitor {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void notifyResourceGranted(int n, int n2) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IMediaResourceMonitor {
        private static final String DESCRIPTOR = "android.media.IMediaResourceMonitor";
        static final int TRANSACTION_notifyResourceGranted = 1;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IMediaResourceMonitor asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IMediaResourceMonitor) {
                return (IMediaResourceMonitor)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IMediaResourceMonitor getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            if (n != 1) {
                return null;
            }
            return "notifyResourceGranted";
        }

        public static boolean setDefaultImpl(IMediaResourceMonitor iMediaResourceMonitor) {
            if (Proxy.sDefaultImpl == null && iMediaResourceMonitor != null) {
                Proxy.sDefaultImpl = iMediaResourceMonitor;
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
            this.notifyResourceGranted(parcel.readInt(), parcel.readInt());
            return true;
        }

        private static class Proxy
        implements IMediaResourceMonitor {
            public static IMediaResourceMonitor sDefaultImpl;
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
            public void notifyResourceGranted(int n, int n2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(1, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().notifyResourceGranted(n, n2);
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

