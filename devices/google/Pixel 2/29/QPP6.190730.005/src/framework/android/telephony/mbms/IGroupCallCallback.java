/*
 * Decompiled with CFR 0.145.
 */
package android.telephony.mbms;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IGroupCallCallback
extends IInterface {
    public void onBroadcastSignalStrengthUpdated(int var1) throws RemoteException;

    public void onError(int var1, String var2) throws RemoteException;

    public void onGroupCallStateChanged(int var1, int var2) throws RemoteException;

    public static class Default
    implements IGroupCallCallback {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void onBroadcastSignalStrengthUpdated(int n) throws RemoteException {
        }

        @Override
        public void onError(int n, String string2) throws RemoteException {
        }

        @Override
        public void onGroupCallStateChanged(int n, int n2) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IGroupCallCallback {
        private static final String DESCRIPTOR = "android.telephony.mbms.IGroupCallCallback";
        static final int TRANSACTION_onBroadcastSignalStrengthUpdated = 3;
        static final int TRANSACTION_onError = 1;
        static final int TRANSACTION_onGroupCallStateChanged = 2;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IGroupCallCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IGroupCallCallback) {
                return (IGroupCallCallback)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IGroupCallCallback getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            if (n != 1) {
                if (n != 2) {
                    if (n != 3) {
                        return null;
                    }
                    return "onBroadcastSignalStrengthUpdated";
                }
                return "onGroupCallStateChanged";
            }
            return "onError";
        }

        public static boolean setDefaultImpl(IGroupCallCallback iGroupCallCallback) {
            if (Proxy.sDefaultImpl == null && iGroupCallCallback != null) {
                Proxy.sDefaultImpl = iGroupCallCallback;
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
                    if (n != 3) {
                        if (n != 1598968902) {
                            return super.onTransact(n, parcel, parcel2, n2);
                        }
                        parcel2.writeString(DESCRIPTOR);
                        return true;
                    }
                    parcel.enforceInterface(DESCRIPTOR);
                    this.onBroadcastSignalStrengthUpdated(parcel.readInt());
                    return true;
                }
                parcel.enforceInterface(DESCRIPTOR);
                this.onGroupCallStateChanged(parcel.readInt(), parcel.readInt());
                return true;
            }
            parcel.enforceInterface(DESCRIPTOR);
            this.onError(parcel.readInt(), parcel.readString());
            return true;
        }

        private static class Proxy
        implements IGroupCallCallback {
            public static IGroupCallCallback sDefaultImpl;
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
            public void onBroadcastSignalStrengthUpdated(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(3, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onBroadcastSignalStrengthUpdated(n);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onError(int n, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(1, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onError(n, string2);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onGroupCallStateChanged(int n, int n2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(2, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onGroupCallStateChanged(n, n2);
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

