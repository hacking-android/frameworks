/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.display;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IDisplayManagerCallback
extends IInterface {
    public void onDisplayEvent(int var1, int var2) throws RemoteException;

    public static class Default
    implements IDisplayManagerCallback {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void onDisplayEvent(int n, int n2) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IDisplayManagerCallback {
        private static final String DESCRIPTOR = "android.hardware.display.IDisplayManagerCallback";
        static final int TRANSACTION_onDisplayEvent = 1;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IDisplayManagerCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IDisplayManagerCallback) {
                return (IDisplayManagerCallback)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IDisplayManagerCallback getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            if (n != 1) {
                return null;
            }
            return "onDisplayEvent";
        }

        public static boolean setDefaultImpl(IDisplayManagerCallback iDisplayManagerCallback) {
            if (Proxy.sDefaultImpl == null && iDisplayManagerCallback != null) {
                Proxy.sDefaultImpl = iDisplayManagerCallback;
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
            this.onDisplayEvent(parcel.readInt(), parcel.readInt());
            return true;
        }

        private static class Proxy
        implements IDisplayManagerCallback {
            public static IDisplayManagerCallback sDefaultImpl;
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
            public void onDisplayEvent(int n, int n2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(1, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onDisplayEvent(n, n2);
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

