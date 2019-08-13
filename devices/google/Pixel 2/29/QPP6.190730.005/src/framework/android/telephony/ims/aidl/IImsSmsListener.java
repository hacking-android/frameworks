/*
 * Decompiled with CFR 0.145.
 */
package android.telephony.ims.aidl;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IImsSmsListener
extends IInterface {
    public void onSendSmsResult(int var1, int var2, int var3, int var4) throws RemoteException;

    public void onSmsReceived(int var1, String var2, byte[] var3) throws RemoteException;

    public void onSmsStatusReportReceived(int var1, int var2, String var3, byte[] var4) throws RemoteException;

    public static class Default
    implements IImsSmsListener {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void onSendSmsResult(int n, int n2, int n3, int n4) throws RemoteException {
        }

        @Override
        public void onSmsReceived(int n, String string2, byte[] arrby) throws RemoteException {
        }

        @Override
        public void onSmsStatusReportReceived(int n, int n2, String string2, byte[] arrby) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IImsSmsListener {
        private static final String DESCRIPTOR = "android.telephony.ims.aidl.IImsSmsListener";
        static final int TRANSACTION_onSendSmsResult = 1;
        static final int TRANSACTION_onSmsReceived = 3;
        static final int TRANSACTION_onSmsStatusReportReceived = 2;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IImsSmsListener asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IImsSmsListener) {
                return (IImsSmsListener)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IImsSmsListener getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            if (n != 1) {
                if (n != 2) {
                    if (n != 3) {
                        return null;
                    }
                    return "onSmsReceived";
                }
                return "onSmsStatusReportReceived";
            }
            return "onSendSmsResult";
        }

        public static boolean setDefaultImpl(IImsSmsListener iImsSmsListener) {
            if (Proxy.sDefaultImpl == null && iImsSmsListener != null) {
                Proxy.sDefaultImpl = iImsSmsListener;
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
                    this.onSmsReceived(parcel.readInt(), parcel.readString(), parcel.createByteArray());
                    return true;
                }
                parcel.enforceInterface(DESCRIPTOR);
                this.onSmsStatusReportReceived(parcel.readInt(), parcel.readInt(), parcel.readString(), parcel.createByteArray());
                return true;
            }
            parcel.enforceInterface(DESCRIPTOR);
            this.onSendSmsResult(parcel.readInt(), parcel.readInt(), parcel.readInt(), parcel.readInt());
            return true;
        }

        private static class Proxy
        implements IImsSmsListener {
            public static IImsSmsListener sDefaultImpl;
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
            public void onSendSmsResult(int n, int n2, int n3, int n4) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    parcel.writeInt(n3);
                    parcel.writeInt(n4);
                    if (!this.mRemote.transact(1, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onSendSmsResult(n, n2, n3, n4);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onSmsReceived(int n, String string2, byte[] arrby) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeString(string2);
                    parcel.writeByteArray(arrby);
                    if (!this.mRemote.transact(3, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onSmsReceived(n, string2, arrby);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onSmsStatusReportReceived(int n, int n2, String string2, byte[] arrby) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    parcel.writeString(string2);
                    parcel.writeByteArray(arrby);
                    if (!this.mRemote.transact(2, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onSmsStatusReportReceived(n, n2, string2, arrby);
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

