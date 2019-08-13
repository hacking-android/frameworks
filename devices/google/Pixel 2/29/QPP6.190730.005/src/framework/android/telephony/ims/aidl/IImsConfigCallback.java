/*
 * Decompiled with CFR 0.145.
 */
package android.telephony.ims.aidl;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IImsConfigCallback
extends IInterface {
    public void onIntConfigChanged(int var1, int var2) throws RemoteException;

    public void onStringConfigChanged(int var1, String var2) throws RemoteException;

    public static class Default
    implements IImsConfigCallback {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void onIntConfigChanged(int n, int n2) throws RemoteException {
        }

        @Override
        public void onStringConfigChanged(int n, String string2) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IImsConfigCallback {
        private static final String DESCRIPTOR = "android.telephony.ims.aidl.IImsConfigCallback";
        static final int TRANSACTION_onIntConfigChanged = 1;
        static final int TRANSACTION_onStringConfigChanged = 2;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IImsConfigCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IImsConfigCallback) {
                return (IImsConfigCallback)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IImsConfigCallback getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            if (n != 1) {
                if (n != 2) {
                    return null;
                }
                return "onStringConfigChanged";
            }
            return "onIntConfigChanged";
        }

        public static boolean setDefaultImpl(IImsConfigCallback iImsConfigCallback) {
            if (Proxy.sDefaultImpl == null && iImsConfigCallback != null) {
                Proxy.sDefaultImpl = iImsConfigCallback;
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
                    if (n != 1598968902) {
                        return super.onTransact(n, parcel, parcel2, n2);
                    }
                    parcel2.writeString(DESCRIPTOR);
                    return true;
                }
                parcel.enforceInterface(DESCRIPTOR);
                this.onStringConfigChanged(parcel.readInt(), parcel.readString());
                return true;
            }
            parcel.enforceInterface(DESCRIPTOR);
            this.onIntConfigChanged(parcel.readInt(), parcel.readInt());
            return true;
        }

        private static class Proxy
        implements IImsConfigCallback {
            public static IImsConfigCallback sDefaultImpl;
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
            public void onIntConfigChanged(int n, int n2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(1, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onIntConfigChanged(n, n2);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onStringConfigChanged(int n, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(2, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onStringConfigChanged(n, string2);
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

