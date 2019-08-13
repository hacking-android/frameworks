/*
 * Decompiled with CFR 0.145.
 */
package android.view;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IDisplayFoldListener
extends IInterface {
    public void onDisplayFoldChanged(int var1, boolean var2) throws RemoteException;

    public static class Default
    implements IDisplayFoldListener {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void onDisplayFoldChanged(int n, boolean bl) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IDisplayFoldListener {
        private static final String DESCRIPTOR = "android.view.IDisplayFoldListener";
        static final int TRANSACTION_onDisplayFoldChanged = 1;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IDisplayFoldListener asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IDisplayFoldListener) {
                return (IDisplayFoldListener)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IDisplayFoldListener getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            if (n != 1) {
                return null;
            }
            return "onDisplayFoldChanged";
        }

        public static boolean setDefaultImpl(IDisplayFoldListener iDisplayFoldListener) {
            if (Proxy.sDefaultImpl == null && iDisplayFoldListener != null) {
                Proxy.sDefaultImpl = iDisplayFoldListener;
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
            n = parcel.readInt();
            boolean bl = parcel.readInt() != 0;
            this.onDisplayFoldChanged(n, bl);
            return true;
        }

        private static class Proxy
        implements IDisplayFoldListener {
            public static IDisplayFoldListener sDefaultImpl;
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
            public void onDisplayFoldChanged(int n, boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                parcel.writeInt(n);
                int n2 = bl ? 1 : 0;
                try {
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(1, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onDisplayFoldChanged(n, bl);
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

