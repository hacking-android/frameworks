/*
 * Decompiled with CFR 0.145.
 */
package android.app;

import android.annotation.UnsupportedAppUsage;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface ITransientNotification
extends IInterface {
    public void hide() throws RemoteException;

    @UnsupportedAppUsage
    public void show(IBinder var1) throws RemoteException;

    public static class Default
    implements ITransientNotification {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void hide() throws RemoteException {
        }

        @Override
        public void show(IBinder iBinder) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements ITransientNotification {
        private static final String DESCRIPTOR = "android.app.ITransientNotification";
        static final int TRANSACTION_hide = 2;
        static final int TRANSACTION_show = 1;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static ITransientNotification asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof ITransientNotification) {
                return (ITransientNotification)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static ITransientNotification getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            if (n != 1) {
                if (n != 2) {
                    return null;
                }
                return "hide";
            }
            return "show";
        }

        public static boolean setDefaultImpl(ITransientNotification iTransientNotification) {
            if (Proxy.sDefaultImpl == null && iTransientNotification != null) {
                Proxy.sDefaultImpl = iTransientNotification;
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
                this.hide();
                return true;
            }
            parcel.enforceInterface(DESCRIPTOR);
            this.show(parcel.readStrongBinder());
            return true;
        }

        private static class Proxy
        implements ITransientNotification {
            public static ITransientNotification sDefaultImpl;
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
            public void hide() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(2, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().hide();
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void show(IBinder iBinder) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeStrongBinder(iBinder);
                    if (!this.mRemote.transact(1, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().show(iBinder);
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

