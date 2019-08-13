/*
 * Decompiled with CFR 0.145.
 */
package android.service.carrier;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface ICarrierMessagingCallback
extends IInterface {
    public void onDownloadMmsComplete(int var1) throws RemoteException;

    public void onFilterComplete(int var1) throws RemoteException;

    public void onSendMmsComplete(int var1, byte[] var2) throws RemoteException;

    public void onSendMultipartSmsComplete(int var1, int[] var2) throws RemoteException;

    public void onSendSmsComplete(int var1, int var2) throws RemoteException;

    public static class Default
    implements ICarrierMessagingCallback {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void onDownloadMmsComplete(int n) throws RemoteException {
        }

        @Override
        public void onFilterComplete(int n) throws RemoteException {
        }

        @Override
        public void onSendMmsComplete(int n, byte[] arrby) throws RemoteException {
        }

        @Override
        public void onSendMultipartSmsComplete(int n, int[] arrn) throws RemoteException {
        }

        @Override
        public void onSendSmsComplete(int n, int n2) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements ICarrierMessagingCallback {
        private static final String DESCRIPTOR = "android.service.carrier.ICarrierMessagingCallback";
        static final int TRANSACTION_onDownloadMmsComplete = 5;
        static final int TRANSACTION_onFilterComplete = 1;
        static final int TRANSACTION_onSendMmsComplete = 4;
        static final int TRANSACTION_onSendMultipartSmsComplete = 3;
        static final int TRANSACTION_onSendSmsComplete = 2;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static ICarrierMessagingCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof ICarrierMessagingCallback) {
                return (ICarrierMessagingCallback)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static ICarrierMessagingCallback getDefaultImpl() {
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
                            return "onDownloadMmsComplete";
                        }
                        return "onSendMmsComplete";
                    }
                    return "onSendMultipartSmsComplete";
                }
                return "onSendSmsComplete";
            }
            return "onFilterComplete";
        }

        public static boolean setDefaultImpl(ICarrierMessagingCallback iCarrierMessagingCallback) {
            if (Proxy.sDefaultImpl == null && iCarrierMessagingCallback != null) {
                Proxy.sDefaultImpl = iCarrierMessagingCallback;
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
                        if (n != 4) {
                            if (n != 5) {
                                if (n != 1598968902) {
                                    return super.onTransact(n, parcel, parcel2, n2);
                                }
                                parcel2.writeString(DESCRIPTOR);
                                return true;
                            }
                            parcel.enforceInterface(DESCRIPTOR);
                            this.onDownloadMmsComplete(parcel.readInt());
                            return true;
                        }
                        parcel.enforceInterface(DESCRIPTOR);
                        this.onSendMmsComplete(parcel.readInt(), parcel.createByteArray());
                        return true;
                    }
                    parcel.enforceInterface(DESCRIPTOR);
                    this.onSendMultipartSmsComplete(parcel.readInt(), parcel.createIntArray());
                    return true;
                }
                parcel.enforceInterface(DESCRIPTOR);
                this.onSendSmsComplete(parcel.readInt(), parcel.readInt());
                return true;
            }
            parcel.enforceInterface(DESCRIPTOR);
            this.onFilterComplete(parcel.readInt());
            return true;
        }

        private static class Proxy
        implements ICarrierMessagingCallback {
            public static ICarrierMessagingCallback sDefaultImpl;
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
            public void onDownloadMmsComplete(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(5, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onDownloadMmsComplete(n);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onFilterComplete(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(1, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onFilterComplete(n);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onSendMmsComplete(int n, byte[] arrby) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeByteArray(arrby);
                    if (!this.mRemote.transact(4, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onSendMmsComplete(n, arrby);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onSendMultipartSmsComplete(int n, int[] arrn) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeIntArray(arrn);
                    if (!this.mRemote.transact(3, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onSendMultipartSmsComplete(n, arrn);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onSendSmsComplete(int n, int n2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(2, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onSendSmsComplete(n, n2);
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

