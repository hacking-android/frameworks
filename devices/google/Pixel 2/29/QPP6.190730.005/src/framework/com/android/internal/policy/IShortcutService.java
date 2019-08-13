/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.policy;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IShortcutService
extends IInterface {
    public void notifyShortcutKeyPressed(long var1) throws RemoteException;

    public static class Default
    implements IShortcutService {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void notifyShortcutKeyPressed(long l) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IShortcutService {
        private static final String DESCRIPTOR = "com.android.internal.policy.IShortcutService";
        static final int TRANSACTION_notifyShortcutKeyPressed = 1;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IShortcutService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IShortcutService) {
                return (IShortcutService)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IShortcutService getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            if (n != 1) {
                return null;
            }
            return "notifyShortcutKeyPressed";
        }

        public static boolean setDefaultImpl(IShortcutService iShortcutService) {
            if (Proxy.sDefaultImpl == null && iShortcutService != null) {
                Proxy.sDefaultImpl = iShortcutService;
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
            this.notifyShortcutKeyPressed(parcel.readLong());
            return true;
        }

        private static class Proxy
        implements IShortcutService {
            public static IShortcutService sDefaultImpl;
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
            public void notifyShortcutKeyPressed(long l) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeLong(l);
                    if (!this.mRemote.transact(1, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().notifyShortcutKeyPressed(l);
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

