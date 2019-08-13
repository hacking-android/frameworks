/*
 * Decompiled with CFR 0.145.
 */
package android.os;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IIncidentAuthListener
extends IInterface {
    public void onReportApproved() throws RemoteException;

    public void onReportDenied() throws RemoteException;

    public static class Default
    implements IIncidentAuthListener {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void onReportApproved() throws RemoteException {
        }

        @Override
        public void onReportDenied() throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IIncidentAuthListener {
        private static final String DESCRIPTOR = "android.os.IIncidentAuthListener";
        static final int TRANSACTION_onReportApproved = 1;
        static final int TRANSACTION_onReportDenied = 2;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IIncidentAuthListener asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IIncidentAuthListener) {
                return (IIncidentAuthListener)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IIncidentAuthListener getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            if (n != 1) {
                if (n != 2) {
                    return null;
                }
                return "onReportDenied";
            }
            return "onReportApproved";
        }

        public static boolean setDefaultImpl(IIncidentAuthListener iIncidentAuthListener) {
            if (Proxy.sDefaultImpl == null && iIncidentAuthListener != null) {
                Proxy.sDefaultImpl = iIncidentAuthListener;
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
                if (n != 2) {
                    if (n != 1598968902) {
                        return super.onTransact(n, parcel, parcel2, n2);
                    }
                    parcel2.writeString(DESCRIPTOR);
                    return true;
                }
                parcel.enforceInterface(DESCRIPTOR);
                this.onReportDenied();
                return true;
            }
            parcel.enforceInterface(DESCRIPTOR);
            this.onReportApproved();
            return true;
        }

        private static class Proxy
        implements IIncidentAuthListener {
            public static IIncidentAuthListener sDefaultImpl;
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
            public void onReportApproved() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(1, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onReportApproved();
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onReportDenied() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(2, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onReportDenied();
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

