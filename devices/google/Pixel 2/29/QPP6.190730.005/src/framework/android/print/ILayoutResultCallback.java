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
import android.print.PrintDocumentInfo;
import android.text.TextUtils;

public interface ILayoutResultCallback
extends IInterface {
    public void onLayoutCanceled(int var1) throws RemoteException;

    public void onLayoutFailed(CharSequence var1, int var2) throws RemoteException;

    public void onLayoutFinished(PrintDocumentInfo var1, boolean var2, int var3) throws RemoteException;

    public void onLayoutStarted(ICancellationSignal var1, int var2) throws RemoteException;

    public static class Default
    implements ILayoutResultCallback {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void onLayoutCanceled(int n) throws RemoteException {
        }

        @Override
        public void onLayoutFailed(CharSequence charSequence, int n) throws RemoteException {
        }

        @Override
        public void onLayoutFinished(PrintDocumentInfo printDocumentInfo, boolean bl, int n) throws RemoteException {
        }

        @Override
        public void onLayoutStarted(ICancellationSignal iCancellationSignal, int n) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements ILayoutResultCallback {
        private static final String DESCRIPTOR = "android.print.ILayoutResultCallback";
        static final int TRANSACTION_onLayoutCanceled = 4;
        static final int TRANSACTION_onLayoutFailed = 3;
        static final int TRANSACTION_onLayoutFinished = 2;
        static final int TRANSACTION_onLayoutStarted = 1;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static ILayoutResultCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof ILayoutResultCallback) {
                return (ILayoutResultCallback)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static ILayoutResultCallback getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            if (n != 1) {
                if (n != 2) {
                    if (n != 3) {
                        if (n != 4) {
                            return null;
                        }
                        return "onLayoutCanceled";
                    }
                    return "onLayoutFailed";
                }
                return "onLayoutFinished";
            }
            return "onLayoutStarted";
        }

        public static boolean setDefaultImpl(ILayoutResultCallback iLayoutResultCallback) {
            if (Proxy.sDefaultImpl == null && iLayoutResultCallback != null) {
                Proxy.sDefaultImpl = iLayoutResultCallback;
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
                        this.onLayoutCanceled(parcel.readInt());
                        return true;
                    }
                    parcel.enforceInterface(DESCRIPTOR);
                    object = parcel.readInt() != 0 ? TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel) : null;
                    this.onLayoutFailed((CharSequence)object, parcel.readInt());
                    return true;
                }
                parcel.enforceInterface(DESCRIPTOR);
                object = parcel.readInt() != 0 ? PrintDocumentInfo.CREATOR.createFromParcel(parcel) : null;
                boolean bl = parcel.readInt() != 0;
                this.onLayoutFinished((PrintDocumentInfo)object, bl, parcel.readInt());
                return true;
            }
            parcel.enforceInterface(DESCRIPTOR);
            this.onLayoutStarted(ICancellationSignal.Stub.asInterface(parcel.readStrongBinder()), parcel.readInt());
            return true;
        }

        private static class Proxy
        implements ILayoutResultCallback {
            public static ILayoutResultCallback sDefaultImpl;
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
            public void onLayoutCanceled(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(4, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onLayoutCanceled(n);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onLayoutFailed(CharSequence charSequence, int n) throws RemoteException {
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
                        Stub.getDefaultImpl().onLayoutFailed(charSequence, n);
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
            public void onLayoutFinished(PrintDocumentInfo printDocumentInfo, boolean bl, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    int n2 = 0;
                    if (printDocumentInfo != null) {
                        parcel.writeInt(1);
                        printDocumentInfo.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (bl) {
                        n2 = 1;
                    }
                    parcel.writeInt(n2);
                    parcel.writeInt(n);
                    if (this.mRemote.transact(2, parcel, null, 1)) return;
                    if (Stub.getDefaultImpl() == null) return;
                    Stub.getDefaultImpl().onLayoutFinished(printDocumentInfo, bl, n);
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
            public void onLayoutStarted(ICancellationSignal iCancellationSignal, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iCancellationSignal != null ? iCancellationSignal.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    parcel.writeInt(n);
                    if (this.mRemote.transact(1, parcel, null, 1)) return;
                    if (Stub.getDefaultImpl() == null) return;
                    Stub.getDefaultImpl().onLayoutStarted(iCancellationSignal, n);
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }
        }

    }

}

