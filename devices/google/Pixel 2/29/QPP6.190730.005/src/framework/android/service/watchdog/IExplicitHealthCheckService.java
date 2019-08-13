/*
 * Decompiled with CFR 0.145.
 */
package android.service.watchdog;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteCallback;
import android.os.RemoteException;

public interface IExplicitHealthCheckService
extends IInterface {
    public void cancel(String var1) throws RemoteException;

    public void getRequestedPackages(RemoteCallback var1) throws RemoteException;

    public void getSupportedPackages(RemoteCallback var1) throws RemoteException;

    public void request(String var1) throws RemoteException;

    public void setCallback(RemoteCallback var1) throws RemoteException;

    public static class Default
    implements IExplicitHealthCheckService {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void cancel(String string2) throws RemoteException {
        }

        @Override
        public void getRequestedPackages(RemoteCallback remoteCallback) throws RemoteException {
        }

        @Override
        public void getSupportedPackages(RemoteCallback remoteCallback) throws RemoteException {
        }

        @Override
        public void request(String string2) throws RemoteException {
        }

        @Override
        public void setCallback(RemoteCallback remoteCallback) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IExplicitHealthCheckService {
        private static final String DESCRIPTOR = "android.service.watchdog.IExplicitHealthCheckService";
        static final int TRANSACTION_cancel = 3;
        static final int TRANSACTION_getRequestedPackages = 5;
        static final int TRANSACTION_getSupportedPackages = 4;
        static final int TRANSACTION_request = 2;
        static final int TRANSACTION_setCallback = 1;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IExplicitHealthCheckService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IExplicitHealthCheckService) {
                return (IExplicitHealthCheckService)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IExplicitHealthCheckService getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            if (n != 1) {
                if (n != 2) {
                    if (n != 3) {
                        if (n != 4) {
                            if (n != 5) {
                                return null;
                            }
                            return "getRequestedPackages";
                        }
                        return "getSupportedPackages";
                    }
                    return "cancel";
                }
                return "request";
            }
            return "setCallback";
        }

        public static boolean setDefaultImpl(IExplicitHealthCheckService iExplicitHealthCheckService) {
            if (Proxy.sDefaultImpl == null && iExplicitHealthCheckService != null) {
                Proxy.sDefaultImpl = iExplicitHealthCheckService;
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
        public boolean onTransact(int n, Parcel object, Parcel parcel, int n2) throws RemoteException {
            if (n != 1) {
                if (n != 2) {
                    if (n != 3) {
                        if (n != 4) {
                            if (n != 5) {
                                if (n != 1598968902) {
                                    return super.onTransact(n, (Parcel)object, parcel, n2);
                                }
                                parcel.writeString(DESCRIPTOR);
                                return true;
                            }
                            ((Parcel)object).enforceInterface(DESCRIPTOR);
                            object = ((Parcel)object).readInt() != 0 ? RemoteCallback.CREATOR.createFromParcel((Parcel)object) : null;
                            this.getRequestedPackages((RemoteCallback)object);
                            return true;
                        }
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = ((Parcel)object).readInt() != 0 ? RemoteCallback.CREATOR.createFromParcel((Parcel)object) : null;
                        this.getSupportedPackages((RemoteCallback)object);
                        return true;
                    }
                    ((Parcel)object).enforceInterface(DESCRIPTOR);
                    this.cancel(((Parcel)object).readString());
                    return true;
                }
                ((Parcel)object).enforceInterface(DESCRIPTOR);
                this.request(((Parcel)object).readString());
                return true;
            }
            ((Parcel)object).enforceInterface(DESCRIPTOR);
            object = ((Parcel)object).readInt() != 0 ? RemoteCallback.CREATOR.createFromParcel((Parcel)object) : null;
            this.setCallback((RemoteCallback)object);
            return true;
        }

        private static class Proxy
        implements IExplicitHealthCheckService {
            public static IExplicitHealthCheckService sDefaultImpl;
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override
            public IBinder asBinder() {
                return this.mRemote;
            }

            @Override
            public void cancel(String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(3, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().cancel(string2);
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

            @Override
            public void getRequestedPackages(RemoteCallback remoteCallback) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (remoteCallback != null) {
                        parcel.writeInt(1);
                        remoteCallback.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(5, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().getRequestedPackages(remoteCallback);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void getSupportedPackages(RemoteCallback remoteCallback) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (remoteCallback != null) {
                        parcel.writeInt(1);
                        remoteCallback.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(4, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().getSupportedPackages(remoteCallback);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void request(String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(2, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().request(string2);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void setCallback(RemoteCallback remoteCallback) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (remoteCallback != null) {
                        parcel.writeInt(1);
                        remoteCallback.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(1, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setCallback(remoteCallback);
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

