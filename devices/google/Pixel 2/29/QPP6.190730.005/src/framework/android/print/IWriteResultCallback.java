/*
 * Decompiled with CFR 0.145.
 */
package android.print;

import android.os.Binder;
import android.os.IBinder;
import android.os.ICancellationSignal;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import android.print.PageRange;
import android.text.TextUtils;

public interface IWriteResultCallback
extends IInterface {
    public void onWriteCanceled(int var1) throws RemoteException;

    public void onWriteFailed(CharSequence var1, int var2) throws RemoteException;

    public void onWriteFinished(PageRange[] var1, int var2) throws RemoteException;

    public void onWriteStarted(ICancellationSignal var1, int var2) throws RemoteException;

    public static class Default
    implements IWriteResultCallback {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void onWriteCanceled(int n) throws RemoteException {
        }

        @Override
        public void onWriteFailed(CharSequence charSequence, int n) throws RemoteException {
        }

        @Override
        public void onWriteFinished(PageRange[] arrpageRange, int n) throws RemoteException {
        }

        @Override
        public void onWriteStarted(ICancellationSignal iCancellationSignal, int n) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IWriteResultCallback {
        private static final String DESCRIPTOR = "android.print.IWriteResultCallback";
        static final int TRANSACTION_onWriteCanceled = 4;
        static final int TRANSACTION_onWriteFailed = 3;
        static final int TRANSACTION_onWriteFinished = 2;
        static final int TRANSACTION_onWriteStarted = 1;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IWriteResultCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IWriteResultCallback) {
                return (IWriteResultCallback)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IWriteResultCallback getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            if (n != 1) {
                if (n != 2) {
                    if (n != 3) {
                        if (n != 4) {
                            return null;
                        }
                        return "onWriteCanceled";
                    }
                    return "onWriteFailed";
                }
                return "onWriteFinished";
            }
            return "onWriteStarted";
        }

        public static boolean setDefaultImpl(IWriteResultCallback iWriteResultCallback) {
            if (Proxy.sDefaultImpl == null && iWriteResultCallback != null) {
                Proxy.sDefaultImpl = iWriteResultCallback;
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
                if (n != 2) {
                    if (n != 3) {
                        if (n != 4) {
                            if (n != 1598968902) {
                                return super.onTransact(n, parcel, (Parcel)object, n2);
                            }
                            ((Parcel)object).writeString(DESCRIPTOR);
                            return true;
                        }
                        parcel.enforceInterface(DESCRIPTOR);
                        this.onWriteCanceled(parcel.readInt());
                        return true;
                    }
                    parcel.enforceInterface(DESCRIPTOR);
                    object = parcel.readInt() != 0 ? TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel) : null;
                    this.onWriteFailed((CharSequence)object, parcel.readInt());
                    return true;
                }
                parcel.enforceInterface(DESCRIPTOR);
                this.onWriteFinished(parcel.createTypedArray(PageRange.CREATOR), parcel.readInt());
                return true;
            }
            parcel.enforceInterface(DESCRIPTOR);
            this.onWriteStarted(ICancellationSignal.Stub.asInterface(parcel.readStrongBinder()), parcel.readInt());
            return true;
        }

        private static class Proxy
        implements IWriteResultCallback {
            public static IWriteResultCallback sDefaultImpl;
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
            public void onWriteCanceled(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(4, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onWriteCanceled(n);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onWriteFailed(CharSequence charSequence, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (charSequence != null) {
                        parcel.writeInt(1);
                        TextUtils.writeToParcel(charSequence, parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(3, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onWriteFailed(charSequence, n);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onWriteFinished(PageRange[] arrpageRange, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeTypedArray((Parcelable[])arrpageRange, 0);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(2, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onWriteFinished(arrpageRange, n);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void onWriteStarted(ICancellationSignal iCancellationSignal, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iCancellationSignal != null ? iCancellationSignal.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    parcel.writeInt(n);
                    if (this.mRemote.transact(1, parcel, null, 1)) return;
                    if (Stub.getDefaultImpl() == null) return;
                    Stub.getDefaultImpl().onWriteStarted(iCancellationSignal, n);
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }
        }

    }

}

