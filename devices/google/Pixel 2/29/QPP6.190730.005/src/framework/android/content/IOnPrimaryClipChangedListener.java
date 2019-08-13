/*
 * Decompiled with CFR 0.145.
 */
package android.content;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IOnPrimaryClipChangedListener
extends IInterface {
    public void dispatchPrimaryClipChanged() throws RemoteException;

    public static class Default
    implements IOnPrimaryClipChangedListener {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void dispatchPrimaryClipChanged() throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IOnPrimaryClipChangedListener {
        private static final String DESCRIPTOR = "android.content.IOnPrimaryClipChangedListener";
        static final int TRANSACTION_dispatchPrimaryClipChanged = 1;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IOnPrimaryClipChangedListener asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IOnPrimaryClipChangedListener) {
                return (IOnPrimaryClipChangedListener)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IOnPrimaryClipChangedListener getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            if (n != 1) {
                return null;
            }
            return "dispatchPrimaryClipChanged";
        }

        public static boolean setDefaultImpl(IOnPrimaryClipChangedListener iOnPrimaryClipChangedListener) {
            if (Proxy.sDefaultImpl == null && iOnPrimaryClipChangedListener != null) {
                Proxy.sDefaultImpl = iOnPrimaryClipChangedListener;
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
            this.dispatchPrimaryClipChanged();
            return true;
        }

        private static class Proxy
        implements IOnPrimaryClipChangedListener {
            public static IOnPrimaryClipChangedListener sDefaultImpl;
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override
            public IBinder asBinder() {
                return this.mRemote;
            }

            @Override
            public void dispatchPrimaryClipChanged() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(1, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().dispatchPrimaryClipChanged();
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

