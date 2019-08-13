/*
 * Decompiled with CFR 0.145.
 */
package android.app.admin;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface StartInstallingUpdateCallback
extends IInterface {
    public void onStartInstallingUpdateError(int var1, String var2) throws RemoteException;

    public static class Default
    implements StartInstallingUpdateCallback {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void onStartInstallingUpdateError(int n, String string2) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements StartInstallingUpdateCallback {
        private static final String DESCRIPTOR = "android.app.admin.StartInstallingUpdateCallback";
        static final int TRANSACTION_onStartInstallingUpdateError = 1;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static StartInstallingUpdateCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof StartInstallingUpdateCallback) {
                return (StartInstallingUpdateCallback)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static StartInstallingUpdateCallback getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            if (n != 1) {
                return null;
            }
            return "onStartInstallingUpdateError";
        }

        public static boolean setDefaultImpl(StartInstallingUpdateCallback startInstallingUpdateCallback) {
            if (Proxy.sDefaultImpl == null && startInstallingUpdateCallback != null) {
                Proxy.sDefaultImpl = startInstallingUpdateCallback;
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
            this.onStartInstallingUpdateError(parcel.readInt(), parcel.readString());
            return true;
        }

        private static class Proxy
        implements StartInstallingUpdateCallback {
            public static StartInstallingUpdateCallback sDefaultImpl;
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
            public void onStartInstallingUpdateError(int n, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(1, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onStartInstallingUpdateError(n, string2);
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

