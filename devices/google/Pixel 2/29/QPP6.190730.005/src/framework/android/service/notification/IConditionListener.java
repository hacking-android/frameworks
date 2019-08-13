/*
 * Decompiled with CFR 0.145.
 */
package android.service.notification;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import android.service.notification.Condition;

public interface IConditionListener
extends IInterface {
    public void onConditionsReceived(Condition[] var1) throws RemoteException;

    public static class Default
    implements IConditionListener {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void onConditionsReceived(Condition[] arrcondition) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IConditionListener {
        private static final String DESCRIPTOR = "android.service.notification.IConditionListener";
        static final int TRANSACTION_onConditionsReceived = 1;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IConditionListener asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IConditionListener) {
                return (IConditionListener)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IConditionListener getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            if (n != 1) {
                return null;
            }
            return "onConditionsReceived";
        }

        public static boolean setDefaultImpl(IConditionListener iConditionListener) {
            if (Proxy.sDefaultImpl == null && iConditionListener != null) {
                Proxy.sDefaultImpl = iConditionListener;
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
            this.onConditionsReceived(parcel.createTypedArray(Condition.CREATOR));
            return true;
        }

        private static class Proxy
        implements IConditionListener {
            public static IConditionListener sDefaultImpl;
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
            public void onConditionsReceived(Condition[] arrcondition) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeTypedArray((Parcelable[])arrcondition, 0);
                    if (!this.mRemote.transact(1, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onConditionsReceived(arrcondition);
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

