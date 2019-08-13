/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.app;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IAppOpsActiveCallback
extends IInterface {
    public void opActiveChanged(int var1, int var2, String var3, boolean var4) throws RemoteException;

    public static class Default
    implements IAppOpsActiveCallback {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void opActiveChanged(int n, int n2, String string2, boolean bl) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IAppOpsActiveCallback {
        private static final String DESCRIPTOR = "com.android.internal.app.IAppOpsActiveCallback";
        static final int TRANSACTION_opActiveChanged = 1;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IAppOpsActiveCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IAppOpsActiveCallback) {
                return (IAppOpsActiveCallback)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IAppOpsActiveCallback getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            if (n != 1) {
                return null;
            }
            return "opActiveChanged";
        }

        public static boolean setDefaultImpl(IAppOpsActiveCallback iAppOpsActiveCallback) {
            if (Proxy.sDefaultImpl == null && iAppOpsActiveCallback != null) {
                Proxy.sDefaultImpl = iAppOpsActiveCallback;
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
            n2 = parcel.readInt();
            n = parcel.readInt();
            object = parcel.readString();
            boolean bl = parcel.readInt() != 0;
            this.opActiveChanged(n2, n, (String)object, bl);
            return true;
        }

        private static class Proxy
        implements IAppOpsActiveCallback {
            public static IAppOpsActiveCallback sDefaultImpl;
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
            public void opActiveChanged(int n, int n2, String string2, boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                parcel.writeInt(n);
                parcel.writeInt(n2);
                parcel.writeString(string2);
                int n3 = bl ? 1 : 0;
                try {
                    parcel.writeInt(n3);
                    if (!this.mRemote.transact(1, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().opActiveChanged(n, n2, string2, bl);
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

