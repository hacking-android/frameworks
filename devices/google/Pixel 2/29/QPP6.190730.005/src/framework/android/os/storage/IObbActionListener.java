/*
 * Decompiled with CFR 0.145.
 */
package android.os.storage;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IObbActionListener
extends IInterface {
    public void onObbResult(String var1, int var2, int var3) throws RemoteException;

    public static class Default
    implements IObbActionListener {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void onObbResult(String string2, int n, int n2) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IObbActionListener {
        private static final String DESCRIPTOR = "android.os.storage.IObbActionListener";
        static final int TRANSACTION_onObbResult = 1;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IObbActionListener asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IObbActionListener) {
                return (IObbActionListener)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IObbActionListener getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            if (n != 1) {
                return null;
            }
            return "onObbResult";
        }

        public static boolean setDefaultImpl(IObbActionListener iObbActionListener) {
            if (Proxy.sDefaultImpl == null && iObbActionListener != null) {
                Proxy.sDefaultImpl = iObbActionListener;
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
            this.onObbResult(parcel.readString(), parcel.readInt(), parcel.readInt());
            return true;
        }

        private static class Proxy
        implements IObbActionListener {
            public static IObbActionListener sDefaultImpl;
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
            public void onObbResult(String string2, int n, int n2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(1, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onObbResult(string2, n, n2);
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

