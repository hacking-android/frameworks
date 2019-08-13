/*
 * Decompiled with CFR 0.145.
 */
package android.service.autofill;

import android.os.Binder;
import android.os.IBinder;
import android.os.ICancellationSignal;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import android.service.autofill.FillResponse;
import android.text.TextUtils;

public interface IFillCallback
extends IInterface {
    public void onCancellable(ICancellationSignal var1) throws RemoteException;

    public void onFailure(int var1, CharSequence var2) throws RemoteException;

    public void onSuccess(FillResponse var1) throws RemoteException;

    public static class Default
    implements IFillCallback {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void onCancellable(ICancellationSignal iCancellationSignal) throws RemoteException {
        }

        @Override
        public void onFailure(int n, CharSequence charSequence) throws RemoteException {
        }

        @Override
        public void onSuccess(FillResponse fillResponse) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IFillCallback {
        private static final String DESCRIPTOR = "android.service.autofill.IFillCallback";
        static final int TRANSACTION_onCancellable = 1;
        static final int TRANSACTION_onFailure = 3;
        static final int TRANSACTION_onSuccess = 2;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IFillCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IFillCallback) {
                return (IFillCallback)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IFillCallback getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            if (n != 1) {
                if (n != 2) {
                    if (n != 3) {
                        return null;
                    }
                    return "onFailure";
                }
                return "onSuccess";
            }
            return "onCancellable";
        }

        public static boolean setDefaultImpl(IFillCallback iFillCallback) {
            if (Proxy.sDefaultImpl == null && iFillCallback != null) {
                Proxy.sDefaultImpl = iFillCallback;
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
                    if (n != 3) {
                        if (n != 1598968902) {
                            return super.onTransact(n, (Parcel)object, parcel, n2);
                        }
                        parcel.writeString(DESCRIPTOR);
                        return true;
                    }
                    ((Parcel)object).enforceInterface(DESCRIPTOR);
                    n = ((Parcel)object).readInt();
                    object = ((Parcel)object).readInt() != 0 ? TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel((Parcel)object) : null;
                    this.onFailure(n, (CharSequence)object);
                    return true;
                }
                ((Parcel)object).enforceInterface(DESCRIPTOR);
                object = ((Parcel)object).readInt() != 0 ? FillResponse.CREATOR.createFromParcel((Parcel)object) : null;
                this.onSuccess((FillResponse)object);
                return true;
            }
            ((Parcel)object).enforceInterface(DESCRIPTOR);
            this.onCancellable(ICancellationSignal.Stub.asInterface(((Parcel)object).readStrongBinder()));
            return true;
        }

        private static class Proxy
        implements IFillCallback {
            public static IFillCallback sDefaultImpl;
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

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void onCancellable(ICancellationSignal iCancellationSignal) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iCancellationSignal != null ? iCancellationSignal.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (this.mRemote.transact(1, parcel, null, 1)) return;
                    if (Stub.getDefaultImpl() == null) return;
                    Stub.getDefaultImpl().onCancellable(iCancellationSignal);
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onFailure(int n, CharSequence charSequence) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (charSequence != null) {
                        parcel.writeInt(1);
                        TextUtils.writeToParcel(charSequence, parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(3, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onFailure(n, charSequence);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onSuccess(FillResponse fillResponse) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (fillResponse != null) {
                        parcel.writeInt(1);
                        fillResponse.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(2, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onSuccess(fillResponse);
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

