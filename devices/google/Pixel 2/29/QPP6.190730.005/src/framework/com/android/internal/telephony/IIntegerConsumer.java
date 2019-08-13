/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.telephony;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IIntegerConsumer
extends IInterface {
    public void accept(int var1) throws RemoteException;

    public static class Default
    implements IIntegerConsumer {
        @Override
        public void accept(int n) throws RemoteException {
        }

        @Override
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub
    extends Binder
    implements IIntegerConsumer {
        private static final String DESCRIPTOR = "com.android.internal.telephony.IIntegerConsumer";
        static final int TRANSACTION_accept = 1;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IIntegerConsumer asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IIntegerConsumer) {
                return (IIntegerConsumer)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IIntegerConsumer getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            if (n != 1) {
                return null;
            }
            return "accept";
        }

        public static boolean setDefaultImpl(IIntegerConsumer iIntegerConsumer) {
            if (Proxy.sDefaultImpl == null && iIntegerConsumer != null) {
                Proxy.sDefaultImpl = iIntegerConsumer;
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
            this.accept(parcel.readInt());
            return true;
        }

        private static class Proxy
        implements IIntegerConsumer {
            public static IIntegerConsumer sDefaultImpl;
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override
            public void accept(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(1, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().accept(n);
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

