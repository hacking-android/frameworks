/*
 * Decompiled with CFR 0.145.
 */
package android.print;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IPrintServicesChangeListener
extends IInterface {
    public void onPrintServicesChanged() throws RemoteException;

    public static class Default
    implements IPrintServicesChangeListener {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void onPrintServicesChanged() throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IPrintServicesChangeListener {
        private static final String DESCRIPTOR = "android.print.IPrintServicesChangeListener";
        static final int TRANSACTION_onPrintServicesChanged = 1;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IPrintServicesChangeListener asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IPrintServicesChangeListener) {
                return (IPrintServicesChangeListener)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IPrintServicesChangeListener getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            if (n != 1) {
                return null;
            }
            return "onPrintServicesChanged";
        }

        public static boolean setDefaultImpl(IPrintServicesChangeListener iPrintServicesChangeListener) {
            if (Proxy.sDefaultImpl == null && iPrintServicesChangeListener != null) {
                Proxy.sDefaultImpl = iPrintServicesChangeListener;
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
            this.onPrintServicesChanged();
            return true;
        }

        private static class Proxy
        implements IPrintServicesChangeListener {
            public static IPrintServicesChangeListener sDefaultImpl;
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
            public void onPrintServicesChanged() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(1, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onPrintServicesChanged();
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

