/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.policy;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IKeyguardStateCallback
extends IInterface {
    public void onHasLockscreenWallpaperChanged(boolean var1) throws RemoteException;

    public void onInputRestrictedStateChanged(boolean var1) throws RemoteException;

    public void onShowingStateChanged(boolean var1) throws RemoteException;

    public void onSimSecureStateChanged(boolean var1) throws RemoteException;

    public void onTrustedChanged(boolean var1) throws RemoteException;

    public static class Default
    implements IKeyguardStateCallback {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void onHasLockscreenWallpaperChanged(boolean bl) throws RemoteException {
        }

        @Override
        public void onInputRestrictedStateChanged(boolean bl) throws RemoteException {
        }

        @Override
        public void onShowingStateChanged(boolean bl) throws RemoteException {
        }

        @Override
        public void onSimSecureStateChanged(boolean bl) throws RemoteException {
        }

        @Override
        public void onTrustedChanged(boolean bl) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IKeyguardStateCallback {
        private static final String DESCRIPTOR = "com.android.internal.policy.IKeyguardStateCallback";
        static final int TRANSACTION_onHasLockscreenWallpaperChanged = 5;
        static final int TRANSACTION_onInputRestrictedStateChanged = 3;
        static final int TRANSACTION_onShowingStateChanged = 1;
        static final int TRANSACTION_onSimSecureStateChanged = 2;
        static final int TRANSACTION_onTrustedChanged = 4;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IKeyguardStateCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IKeyguardStateCallback) {
                return (IKeyguardStateCallback)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IKeyguardStateCallback getDefaultImpl() {
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
                            return "onHasLockscreenWallpaperChanged";
                        }
                        return "onTrustedChanged";
                    }
                    return "onInputRestrictedStateChanged";
                }
                return "onSimSecureStateChanged";
            }
            return "onShowingStateChanged";
        }

        public static boolean setDefaultImpl(IKeyguardStateCallback iKeyguardStateCallback) {
            if (Proxy.sDefaultImpl == null && iKeyguardStateCallback != null) {
                Proxy.sDefaultImpl = iKeyguardStateCallback;
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
            boolean bl = false;
            boolean bl2 = false;
            boolean bl3 = false;
            boolean bl4 = false;
            boolean bl5 = false;
            if (n != 1) {
                if (n != 2) {
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
                            if (parcel.readInt() != 0) {
                                bl5 = true;
                            }
                            this.onHasLockscreenWallpaperChanged(bl5);
                            parcel2.writeNoException();
                            return true;
                        }
                        parcel.enforceInterface(DESCRIPTOR);
                        bl5 = bl;
                        if (parcel.readInt() != 0) {
                            bl5 = true;
                        }
                        this.onTrustedChanged(bl5);
                        parcel2.writeNoException();
                        return true;
                    }
                    parcel.enforceInterface(DESCRIPTOR);
                    bl5 = bl2;
                    if (parcel.readInt() != 0) {
                        bl5 = true;
                    }
                    this.onInputRestrictedStateChanged(bl5);
                    parcel2.writeNoException();
                    return true;
                }
                parcel.enforceInterface(DESCRIPTOR);
                bl5 = bl3;
                if (parcel.readInt() != 0) {
                    bl5 = true;
                }
                this.onSimSecureStateChanged(bl5);
                parcel2.writeNoException();
                return true;
            }
            parcel.enforceInterface(DESCRIPTOR);
            bl5 = bl4;
            if (parcel.readInt() != 0) {
                bl5 = true;
            }
            this.onShowingStateChanged(bl5);
            parcel2.writeNoException();
            return true;
        }

        private static class Proxy
        implements IKeyguardStateCallback {
            public static IKeyguardStateCallback sDefaultImpl;
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
            public void onHasLockscreenWallpaperChanged(boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                int n = bl ? 1 : 0;
                try {
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(5, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onHasLockscreenWallpaperChanged(bl);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void onInputRestrictedStateChanged(boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                int n = bl ? 1 : 0;
                try {
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(3, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onInputRestrictedStateChanged(bl);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void onShowingStateChanged(boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                int n = bl ? 1 : 0;
                try {
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(1, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onShowingStateChanged(bl);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void onSimSecureStateChanged(boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                int n = bl ? 1 : 0;
                try {
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(2, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onSimSecureStateChanged(bl);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void onTrustedChanged(boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                int n = bl ? 1 : 0;
                try {
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(4, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onTrustedChanged(bl);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }
        }

    }

}

