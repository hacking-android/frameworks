/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.app;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IAppOpsNotedCallback
extends IInterface {
    public void opNoted(int var1, int var2, String var3, int var4) throws RemoteException;

    public static class Default
    implements IAppOpsNotedCallback {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void opNoted(int n, int n2, String string2, int n3) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IAppOpsNotedCallback {
        private static final String DESCRIPTOR = "com.android.internal.app.IAppOpsNotedCallback";
        static final int TRANSACTION_opNoted = 1;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IAppOpsNotedCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IAppOpsNotedCallback) {
                return (IAppOpsNotedCallback)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IAppOpsNotedCallback getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            if (n != 1) {
                return null;
            }
            return "opNoted";
        }

        public static boolean setDefaultImpl(IAppOpsNotedCallback iAppOpsNotedCallback) {
            if (Proxy.sDefaultImpl == null && iAppOpsNotedCallback != null) {
                Proxy.sDefaultImpl = iAppOpsNotedCallback;
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
            this.opNoted(parcel.readInt(), parcel.readInt(), parcel.readString(), parcel.readInt());
            return true;
        }

        private static class Proxy
        implements IAppOpsNotedCallback {
            public static IAppOpsNotedCallback sDefaultImpl;
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
            public void opNoted(int n, int n2, String string2, int n3) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    parcel.writeString(string2);
                    parcel.writeInt(n3);
                    if (!this.mRemote.transact(1, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().opNoted(n, n2, string2, n3);
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

