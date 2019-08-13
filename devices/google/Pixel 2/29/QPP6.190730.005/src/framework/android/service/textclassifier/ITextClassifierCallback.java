/*
 * Decompiled with CFR 0.145.
 */
package android.service.textclassifier;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;

public interface ITextClassifierCallback
extends IInterface {
    public void onFailure() throws RemoteException;

    public void onSuccess(Bundle var1) throws RemoteException;

    public static class Default
    implements ITextClassifierCallback {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void onFailure() throws RemoteException {
        }

        @Override
        public void onSuccess(Bundle bundle) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements ITextClassifierCallback {
        private static final String DESCRIPTOR = "android.service.textclassifier.ITextClassifierCallback";
        static final int TRANSACTION_onFailure = 2;
        static final int TRANSACTION_onSuccess = 1;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static ITextClassifierCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof ITextClassifierCallback) {
                return (ITextClassifierCallback)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static ITextClassifierCallback getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            if (n != 1) {
                if (n != 2) {
                    return null;
                }
                return "onFailure";
            }
            return "onSuccess";
        }

        public static boolean setDefaultImpl(ITextClassifierCallback iTextClassifierCallback) {
            if (Proxy.sDefaultImpl == null && iTextClassifierCallback != null) {
                Proxy.sDefaultImpl = iTextClassifierCallback;
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
        public boolean onTransact(int n, Parcel object, Parcel parcel, int n2) throws RemoteException {
            if (n != 1) {
                if (n != 2) {
                    if (n != 1598968902) {
                        return super.onTransact(n, (Parcel)object, parcel, n2);
                    }
                    parcel.writeString(DESCRIPTOR);
                    return true;
                }
                ((Parcel)object).enforceInterface(DESCRIPTOR);
                this.onFailure();
                return true;
            }
            ((Parcel)object).enforceInterface(DESCRIPTOR);
            object = ((Parcel)object).readInt() != 0 ? Bundle.CREATOR.createFromParcel((Parcel)object) : null;
            this.onSuccess((Bundle)object);
            return true;
        }

        private static class Proxy
        implements ITextClassifierCallback {
            public static ITextClassifierCallback sDefaultImpl;
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
            public void onFailure() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(2, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onFailure();
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onSuccess(Bundle bundle) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (bundle != null) {
                        parcel.writeInt(1);
                        bundle.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(1, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onSuccess(bundle);
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

