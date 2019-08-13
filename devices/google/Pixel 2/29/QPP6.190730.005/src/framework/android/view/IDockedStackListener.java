/*
 * Decompiled with CFR 0.145.
 */
package android.view;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IDockedStackListener
extends IInterface {
    public void onAdjustedForImeChanged(boolean var1, long var2) throws RemoteException;

    public void onDividerVisibilityChanged(boolean var1) throws RemoteException;

    public void onDockSideChanged(int var1) throws RemoteException;

    public void onDockedStackExistsChanged(boolean var1) throws RemoteException;

    public void onDockedStackMinimizedChanged(boolean var1, long var2, boolean var4) throws RemoteException;

    public static class Default
    implements IDockedStackListener {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void onAdjustedForImeChanged(boolean bl, long l) throws RemoteException {
        }

        @Override
        public void onDividerVisibilityChanged(boolean bl) throws RemoteException {
        }

        @Override
        public void onDockSideChanged(int n) throws RemoteException {
        }

        @Override
        public void onDockedStackExistsChanged(boolean bl) throws RemoteException {
        }

        @Override
        public void onDockedStackMinimizedChanged(boolean bl, long l, boolean bl2) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IDockedStackListener {
        private static final String DESCRIPTOR = "android.view.IDockedStackListener";
        static final int TRANSACTION_onAdjustedForImeChanged = 4;
        static final int TRANSACTION_onDividerVisibilityChanged = 1;
        static final int TRANSACTION_onDockSideChanged = 5;
        static final int TRANSACTION_onDockedStackExistsChanged = 2;
        static final int TRANSACTION_onDockedStackMinimizedChanged = 3;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IDockedStackListener asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IDockedStackListener) {
                return (IDockedStackListener)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IDockedStackListener getDefaultImpl() {
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
                            return "onDockSideChanged";
                        }
                        return "onAdjustedForImeChanged";
                    }
                    return "onDockedStackMinimizedChanged";
                }
                return "onDockedStackExistsChanged";
            }
            return "onDividerVisibilityChanged";
        }

        public static boolean setDefaultImpl(IDockedStackListener iDockedStackListener) {
            if (Proxy.sDefaultImpl == null && iDockedStackListener != null) {
                Proxy.sDefaultImpl = iDockedStackListener;
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
                            this.onDockSideChanged(parcel.readInt());
                            return true;
                        }
                        parcel.enforceInterface(DESCRIPTOR);
                        if (parcel.readInt() != 0) {
                            bl4 = true;
                        }
                        this.onAdjustedForImeChanged(bl4, parcel.readLong());
                        return true;
                    }
                    parcel.enforceInterface(DESCRIPTOR);
                    bl4 = parcel.readInt() != 0;
                    long l = parcel.readLong();
                    if (parcel.readInt() != 0) {
                        bl = true;
                    }
                    this.onDockedStackMinimizedChanged(bl4, l, bl);
                    return true;
                }
                parcel.enforceInterface(DESCRIPTOR);
                bl4 = bl2;
                if (parcel.readInt() != 0) {
                    bl4 = true;
                }
                this.onDockedStackExistsChanged(bl4);
                return true;
            }
            parcel.enforceInterface(DESCRIPTOR);
            bl4 = bl3;
            if (parcel.readInt() != 0) {
                bl4 = true;
            }
            this.onDividerVisibilityChanged(bl4);
            return true;
        }

        private static class Proxy
        implements IDockedStackListener {
            public static IDockedStackListener sDefaultImpl;
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
            public void onAdjustedForImeChanged(boolean bl, long l) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                int n = bl ? 1 : 0;
                try {
                    parcel.writeInt(n);
                    parcel.writeLong(l);
                    if (!this.mRemote.transact(4, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onAdjustedForImeChanged(bl, l);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onDividerVisibilityChanged(boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                int n = bl ? 1 : 0;
                try {
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(1, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onDividerVisibilityChanged(bl);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onDockSideChanged(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(5, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onDockSideChanged(n);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onDockedStackExistsChanged(boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                int n = bl ? 1 : 0;
                try {
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(2, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onDockedStackExistsChanged(bl);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onDockedStackMinimizedChanged(boolean bl, long l, boolean bl2) throws RemoteException {
                Parcel parcel;
                int n;
                block6 : {
                    parcel = Parcel.obtain();
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    int n2 = 0;
                    n = bl ? 1 : 0;
                    parcel.writeInt(n);
                    parcel.writeLong(l);
                    n = n2;
                    if (!bl2) break block6;
                    n = 1;
                }
                try {
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(3, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onDockedStackMinimizedChanged(bl, l, bl2);
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

