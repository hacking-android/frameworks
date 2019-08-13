/*
 * Decompiled with CFR 0.145.
 */
package android.print;

import android.content.pm.ParceledListSlice;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;

public interface IPrinterDiscoveryObserver
extends IInterface {
    public void onPrintersAdded(ParceledListSlice var1) throws RemoteException;

    public void onPrintersRemoved(ParceledListSlice var1) throws RemoteException;

    public static class Default
    implements IPrinterDiscoveryObserver {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void onPrintersAdded(ParceledListSlice parceledListSlice) throws RemoteException {
        }

        @Override
        public void onPrintersRemoved(ParceledListSlice parceledListSlice) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IPrinterDiscoveryObserver {
        private static final String DESCRIPTOR = "android.print.IPrinterDiscoveryObserver";
        static final int TRANSACTION_onPrintersAdded = 1;
        static final int TRANSACTION_onPrintersRemoved = 2;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IPrinterDiscoveryObserver asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IPrinterDiscoveryObserver) {
                return (IPrinterDiscoveryObserver)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IPrinterDiscoveryObserver getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            if (n != 1) {
                if (n != 2) {
                    return null;
                }
                return "onPrintersRemoved";
            }
            return "onPrintersAdded";
        }

        public static boolean setDefaultImpl(IPrinterDiscoveryObserver iPrinterDiscoveryObserver) {
            if (Proxy.sDefaultImpl == null && iPrinterDiscoveryObserver != null) {
                Proxy.sDefaultImpl = iPrinterDiscoveryObserver;
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
        public boolean onTransact(int n, Parcel parceledListSlice, Parcel parcel, int n2) throws RemoteException {
            if (n != 1) {
                if (n != 2) {
                    if (n != 1598968902) {
                        return super.onTransact(n, (Parcel)((Object)parceledListSlice), parcel, n2);
                    }
                    parcel.writeString(DESCRIPTOR);
                    return true;
                }
                ((Parcel)((Object)parceledListSlice)).enforceInterface(DESCRIPTOR);
                parceledListSlice = ((Parcel)((Object)parceledListSlice)).readInt() != 0 ? (ParceledListSlice)ParceledListSlice.CREATOR.createFromParcel((Parcel)((Object)parceledListSlice)) : null;
                this.onPrintersRemoved(parceledListSlice);
                return true;
            }
            ((Parcel)((Object)parceledListSlice)).enforceInterface(DESCRIPTOR);
            parceledListSlice = ((Parcel)((Object)parceledListSlice)).readInt() != 0 ? (ParceledListSlice)ParceledListSlice.CREATOR.createFromParcel((Parcel)((Object)parceledListSlice)) : null;
            this.onPrintersAdded(parceledListSlice);
            return true;
        }

        private static class Proxy
        implements IPrinterDiscoveryObserver {
            public static IPrinterDiscoveryObserver sDefaultImpl;
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
            public void onPrintersAdded(ParceledListSlice parceledListSlice) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (parceledListSlice != null) {
                        parcel.writeInt(1);
                        parceledListSlice.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(1, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onPrintersAdded(parceledListSlice);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onPrintersRemoved(ParceledListSlice parceledListSlice) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (parceledListSlice != null) {
                        parcel.writeInt(1);
                        parceledListSlice.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(2, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onPrintersRemoved(parceledListSlice);
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

