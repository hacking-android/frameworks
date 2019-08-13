/*
 * Decompiled with CFR 0.145.
 */
package android.nfc;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface ITagRemovedCallback
extends IInterface {
    public void onTagRemoved() throws RemoteException;

    public static class Default
    implements ITagRemovedCallback {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void onTagRemoved() throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements ITagRemovedCallback {
        private static final String DESCRIPTOR = "android.nfc.ITagRemovedCallback";
        static final int TRANSACTION_onTagRemoved = 1;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static ITagRemovedCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof ITagRemovedCallback) {
                return (ITagRemovedCallback)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static ITagRemovedCallback getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            if (n != 1) {
                return null;
            }
            return "onTagRemoved";
        }

        public static boolean setDefaultImpl(ITagRemovedCallback iTagRemovedCallback) {
            if (Proxy.sDefaultImpl == null && iTagRemovedCallback != null) {
                Proxy.sDefaultImpl = iTagRemovedCallback;
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
            this.onTagRemoved();
            return true;
        }

        private static class Proxy
        implements ITagRemovedCallback {
            public static ITagRemovedCallback sDefaultImpl;
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
            public void onTagRemoved() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(1, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onTagRemoved();
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

