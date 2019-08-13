/*
 * Decompiled with CFR 0.145.
 */
package android.security;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IKeyChainAliasCallback
extends IInterface {
    public void alias(String var1) throws RemoteException;

    public static class Default
    implements IKeyChainAliasCallback {
        @Override
        public void alias(String string2) throws RemoteException {
        }

        @Override
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub
    extends Binder
    implements IKeyChainAliasCallback {
        private static final String DESCRIPTOR = "android.security.IKeyChainAliasCallback";
        static final int TRANSACTION_alias = 1;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IKeyChainAliasCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IKeyChainAliasCallback) {
                return (IKeyChainAliasCallback)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IKeyChainAliasCallback getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            if (n != 1) {
                return null;
            }
            return "alias";
        }

        public static boolean setDefaultImpl(IKeyChainAliasCallback iKeyChainAliasCallback) {
            if (Proxy.sDefaultImpl == null && iKeyChainAliasCallback != null) {
                Proxy.sDefaultImpl = iKeyChainAliasCallback;
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
            this.alias(parcel.readString());
            return true;
        }

        private static class Proxy
        implements IKeyChainAliasCallback {
            public static IKeyChainAliasCallback sDefaultImpl;
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override
            public void alias(String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(1, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().alias(string2);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }
        }

    }

}

