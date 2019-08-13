/*
 * Decompiled with CFR 0.145.
 */
package android.view;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IGraphicsStatsCallback
extends IInterface {
    public void onRotateGraphicsStatsBuffer() throws RemoteException;

    public static class Default
    implements IGraphicsStatsCallback {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void onRotateGraphicsStatsBuffer() throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IGraphicsStatsCallback {
        private static final String DESCRIPTOR = "android.view.IGraphicsStatsCallback";
        static final int TRANSACTION_onRotateGraphicsStatsBuffer = 1;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IGraphicsStatsCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IGraphicsStatsCallback) {
                return (IGraphicsStatsCallback)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IGraphicsStatsCallback getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            if (n != 1) {
                return null;
            }
            return "onRotateGraphicsStatsBuffer";
        }

        public static boolean setDefaultImpl(IGraphicsStatsCallback iGraphicsStatsCallback) {
            if (Proxy.sDefaultImpl == null && iGraphicsStatsCallback != null) {
                Proxy.sDefaultImpl = iGraphicsStatsCallback;
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
            this.onRotateGraphicsStatsBuffer();
            return true;
        }

        private static class Proxy
        implements IGraphicsStatsCallback {
            public static IGraphicsStatsCallback sDefaultImpl;
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
            public void onRotateGraphicsStatsBuffer() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(1, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onRotateGraphicsStatsBuffer();
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

