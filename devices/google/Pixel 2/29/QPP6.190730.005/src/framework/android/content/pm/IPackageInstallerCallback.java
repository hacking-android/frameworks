/*
 * Decompiled with CFR 0.145.
 */
package android.content.pm;

import android.annotation.UnsupportedAppUsage;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IPackageInstallerCallback
extends IInterface {
    @UnsupportedAppUsage
    public void onSessionActiveChanged(int var1, boolean var2) throws RemoteException;

    @UnsupportedAppUsage
    public void onSessionBadgingChanged(int var1) throws RemoteException;

    @UnsupportedAppUsage
    public void onSessionCreated(int var1) throws RemoteException;

    @UnsupportedAppUsage
    public void onSessionFinished(int var1, boolean var2) throws RemoteException;

    @UnsupportedAppUsage
    public void onSessionProgressChanged(int var1, float var2) throws RemoteException;

    public static class Default
    implements IPackageInstallerCallback {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void onSessionActiveChanged(int n, boolean bl) throws RemoteException {
        }

        @Override
        public void onSessionBadgingChanged(int n) throws RemoteException {
        }

        @Override
        public void onSessionCreated(int n) throws RemoteException {
        }

        @Override
        public void onSessionFinished(int n, boolean bl) throws RemoteException {
        }

        @Override
        public void onSessionProgressChanged(int n, float f) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IPackageInstallerCallback {
        private static final String DESCRIPTOR = "android.content.pm.IPackageInstallerCallback";
        static final int TRANSACTION_onSessionActiveChanged = 3;
        static final int TRANSACTION_onSessionBadgingChanged = 2;
        static final int TRANSACTION_onSessionCreated = 1;
        static final int TRANSACTION_onSessionFinished = 5;
        static final int TRANSACTION_onSessionProgressChanged = 4;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IPackageInstallerCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IPackageInstallerCallback) {
                return (IPackageInstallerCallback)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IPackageInstallerCallback getDefaultImpl() {
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
                            return "onSessionFinished";
                        }
                        return "onSessionProgressChanged";
                    }
                    return "onSessionActiveChanged";
                }
                return "onSessionBadgingChanged";
            }
            return "onSessionCreated";
        }

        public static boolean setDefaultImpl(IPackageInstallerCallback iPackageInstallerCallback) {
            if (Proxy.sDefaultImpl == null && iPackageInstallerCallback != null) {
                Proxy.sDefaultImpl = iPackageInstallerCallback;
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
                    boolean bl = false;
                    boolean bl2 = false;
                    if (n != 3) {
                        if (n != 4) {
                            if (n != 5) {
                                if (n != 1598968902) {
                                    return super.onTransact(n, parcel, parcel2, n2);
                                }
                                parcel2.writeString(DESCRIPTOR);
                                return true;
                            }
                            parcel.enforceInterface(DESCRIPTOR);
                            n = parcel.readInt();
                            if (parcel.readInt() != 0) {
                                bl2 = true;
                            }
                            this.onSessionFinished(n, bl2);
                            return true;
                        }
                        parcel.enforceInterface(DESCRIPTOR);
                        this.onSessionProgressChanged(parcel.readInt(), parcel.readFloat());
                        return true;
                    }
                    parcel.enforceInterface(DESCRIPTOR);
                    n = parcel.readInt();
                    bl2 = bl;
                    if (parcel.readInt() != 0) {
                        bl2 = true;
                    }
                    this.onSessionActiveChanged(n, bl2);
                    return true;
                }
                parcel.enforceInterface(DESCRIPTOR);
                this.onSessionBadgingChanged(parcel.readInt());
                return true;
            }
            parcel.enforceInterface(DESCRIPTOR);
            this.onSessionCreated(parcel.readInt());
            return true;
        }

        private static class Proxy
        implements IPackageInstallerCallback {
            public static IPackageInstallerCallback sDefaultImpl;
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
            public void onSessionActiveChanged(int n, boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                parcel.writeInt(n);
                int n2 = bl ? 1 : 0;
                try {
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(3, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onSessionActiveChanged(n, bl);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onSessionBadgingChanged(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(2, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onSessionBadgingChanged(n);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onSessionCreated(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(1, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onSessionCreated(n);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onSessionFinished(int n, boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                parcel.writeInt(n);
                int n2 = bl ? 1 : 0;
                try {
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(5, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onSessionFinished(n, bl);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onSessionProgressChanged(int n, float f) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeFloat(f);
                    if (!this.mRemote.transact(4, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onSessionProgressChanged(n, f);
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

