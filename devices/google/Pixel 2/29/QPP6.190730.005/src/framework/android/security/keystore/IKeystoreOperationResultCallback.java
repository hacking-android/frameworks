/*
 * Decompiled with CFR 0.145.
 */
package android.security.keystore;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import android.security.keymaster.OperationResult;

public interface IKeystoreOperationResultCallback
extends IInterface {
    public void onFinished(OperationResult var1) throws RemoteException;

    public static class Default
    implements IKeystoreOperationResultCallback {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void onFinished(OperationResult operationResult) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IKeystoreOperationResultCallback {
        private static final String DESCRIPTOR = "android.security.keystore.IKeystoreOperationResultCallback";
        static final int TRANSACTION_onFinished = 1;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IKeystoreOperationResultCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IKeystoreOperationResultCallback) {
                return (IKeystoreOperationResultCallback)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IKeystoreOperationResultCallback getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            if (n != 1) {
                return null;
            }
            return "onFinished";
        }

        public static boolean setDefaultImpl(IKeystoreOperationResultCallback iKeystoreOperationResultCallback) {
            if (Proxy.sDefaultImpl == null && iKeystoreOperationResultCallback != null) {
                Proxy.sDefaultImpl = iKeystoreOperationResultCallback;
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
                if (n != 1598968902) {
                    return super.onTransact(n, (Parcel)object, parcel, n2);
                }
                parcel.writeString(DESCRIPTOR);
                return true;
            }
            ((Parcel)object).enforceInterface(DESCRIPTOR);
            object = ((Parcel)object).readInt() != 0 ? OperationResult.CREATOR.createFromParcel((Parcel)object) : null;
            this.onFinished((OperationResult)object);
            return true;
        }

        private static class Proxy
        implements IKeystoreOperationResultCallback {
            public static IKeystoreOperationResultCallback sDefaultImpl;
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
            public void onFinished(OperationResult operationResult) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (operationResult != null) {
                        parcel.writeInt(1);
                        operationResult.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(1, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onFinished(operationResult);
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

