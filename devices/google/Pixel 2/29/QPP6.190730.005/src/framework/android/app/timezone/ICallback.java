/*
 * Decompiled with CFR 0.145.
 */
package android.app.timezone;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface ICallback
extends IInterface {
    public void onFinished(int var1) throws RemoteException;

    public static class Default
    implements ICallback {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void onFinished(int n) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements ICallback {
        private static final String DESCRIPTOR = "android.app.timezone.ICallback";
        static final int TRANSACTION_onFinished = 1;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static ICallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof ICallback) {
                return (ICallback)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static ICallback getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            if (n != 1) {
                return null;
            }
            return "onFinished";
        }

        public static boolean setDefaultImpl(ICallback iCallback) {
            if (Proxy.sDefaultImpl == null && iCallback != null) {
                Proxy.sDefaultImpl = iCallback;
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
            this.onFinished(parcel.readInt());
            return true;
        }

        private static class Proxy
        implements ICallback {
            public static ICallback sDefaultImpl;
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
            public void onFinished(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(1, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onFinished(n);
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

