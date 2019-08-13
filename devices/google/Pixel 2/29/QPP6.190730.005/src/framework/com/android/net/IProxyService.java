/*
 * Decompiled with CFR 0.145.
 */
package com.android.net;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IProxyService
extends IInterface {
    public String resolvePacFile(String var1, String var2) throws RemoteException;

    public void setPacFile(String var1) throws RemoteException;

    public void startPacSystem() throws RemoteException;

    public void stopPacSystem() throws RemoteException;

    public static class Default
    implements IProxyService {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public String resolvePacFile(String string2, String string3) throws RemoteException {
            return null;
        }

        @Override
        public void setPacFile(String string2) throws RemoteException {
        }

        @Override
        public void startPacSystem() throws RemoteException {
        }

        @Override
        public void stopPacSystem() throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IProxyService {
        private static final String DESCRIPTOR = "com.android.net.IProxyService";
        static final int TRANSACTION_resolvePacFile = 1;
        static final int TRANSACTION_setPacFile = 2;
        static final int TRANSACTION_startPacSystem = 3;
        static final int TRANSACTION_stopPacSystem = 4;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IProxyService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IProxyService) {
                return (IProxyService)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IProxyService getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            if (n != 1) {
                if (n != 2) {
                    if (n != 3) {
                        if (n != 4) {
                            return null;
                        }
                        return "stopPacSystem";
                    }
                    return "startPacSystem";
                }
                return "setPacFile";
            }
            return "resolvePacFile";
        }

        public static boolean setDefaultImpl(IProxyService iProxyService) {
            if (Proxy.sDefaultImpl == null && iProxyService != null) {
                Proxy.sDefaultImpl = iProxyService;
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
                            if (n != 1598968902) {
                                return super.onTransact(n, (Parcel)object, parcel, n2);
                            }
                            parcel.writeString(DESCRIPTOR);
                            return true;
                        }
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.stopPacSystem();
                        return true;
                    }
                    ((Parcel)object).enforceInterface(DESCRIPTOR);
                    this.startPacSystem();
                    return true;
                }
                ((Parcel)object).enforceInterface(DESCRIPTOR);
                this.setPacFile(((Parcel)object).readString());
                return true;
            }
            ((Parcel)object).enforceInterface(DESCRIPTOR);
            object = this.resolvePacFile(((Parcel)object).readString(), ((Parcel)object).readString());
            parcel.writeNoException();
            parcel.writeString((String)object);
            return true;
        }

        private static class Proxy
        implements IProxyService {
            public static IProxyService sDefaultImpl;
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
            public String resolvePacFile(String string2, String string3) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    parcel.writeString(string3);
                    if (!this.mRemote.transact(1, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        string2 = Stub.getDefaultImpl().resolvePacFile(string2, string3);
                        return string2;
                    }
                    parcel2.readException();
                    string2 = parcel2.readString();
                    return string2;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void setPacFile(String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(2, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setPacFile(string2);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void startPacSystem() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(3, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().startPacSystem();
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void stopPacSystem() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(4, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().stopPacSystem();
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

