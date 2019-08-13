/*
 * Decompiled with CFR 0.145.
 */
package android.content.pm;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IDexModuleRegisterCallback
extends IInterface {
    public void onDexModuleRegistered(String var1, boolean var2, String var3) throws RemoteException;

    public static class Default
    implements IDexModuleRegisterCallback {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void onDexModuleRegistered(String string2, boolean bl, String string3) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IDexModuleRegisterCallback {
        private static final String DESCRIPTOR = "android.content.pm.IDexModuleRegisterCallback";
        static final int TRANSACTION_onDexModuleRegistered = 1;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IDexModuleRegisterCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IDexModuleRegisterCallback) {
                return (IDexModuleRegisterCallback)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IDexModuleRegisterCallback getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            if (n != 1) {
                return null;
            }
            return "onDexModuleRegistered";
        }

        public static boolean setDefaultImpl(IDexModuleRegisterCallback iDexModuleRegisterCallback) {
            if (Proxy.sDefaultImpl == null && iDexModuleRegisterCallback != null) {
                Proxy.sDefaultImpl = iDexModuleRegisterCallback;
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
        public boolean onTransact(int n, Parcel parcel, Parcel object, int n2) throws RemoteException {
            if (n != 1) {
                if (n != 1598968902) {
                    return super.onTransact(n, parcel, (Parcel)object, n2);
                }
                ((Parcel)object).writeString(DESCRIPTOR);
                return true;
            }
            parcel.enforceInterface(DESCRIPTOR);
            object = parcel.readString();
            boolean bl = parcel.readInt() != 0;
            this.onDexModuleRegistered((String)object, bl, parcel.readString());
            return true;
        }

        private static class Proxy
        implements IDexModuleRegisterCallback {
            public static IDexModuleRegisterCallback sDefaultImpl;
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
            public void onDexModuleRegistered(String string2, boolean bl, String string3) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                parcel.writeString(string2);
                int n = bl ? 1 : 0;
                try {
                    parcel.writeInt(n);
                    parcel.writeString(string3);
                    if (!this.mRemote.transact(1, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onDexModuleRegistered(string2, bl, string3);
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

