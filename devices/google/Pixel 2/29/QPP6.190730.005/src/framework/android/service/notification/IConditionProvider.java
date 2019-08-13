/*
 * Decompiled with CFR 0.145.
 */
package android.service.notification;

import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;

public interface IConditionProvider
extends IInterface {
    public void onConnected() throws RemoteException;

    public void onSubscribe(Uri var1) throws RemoteException;

    public void onUnsubscribe(Uri var1) throws RemoteException;

    public static class Default
    implements IConditionProvider {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void onConnected() throws RemoteException {
        }

        @Override
        public void onSubscribe(Uri uri) throws RemoteException {
        }

        @Override
        public void onUnsubscribe(Uri uri) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IConditionProvider {
        private static final String DESCRIPTOR = "android.service.notification.IConditionProvider";
        static final int TRANSACTION_onConnected = 1;
        static final int TRANSACTION_onSubscribe = 2;
        static final int TRANSACTION_onUnsubscribe = 3;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IConditionProvider asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IConditionProvider) {
                return (IConditionProvider)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IConditionProvider getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            if (n != 1) {
                if (n != 2) {
                    if (n != 3) {
                        return null;
                    }
                    return "onUnsubscribe";
                }
                return "onSubscribe";
            }
            return "onConnected";
        }

        public static boolean setDefaultImpl(IConditionProvider iConditionProvider) {
            if (Proxy.sDefaultImpl == null && iConditionProvider != null) {
                Proxy.sDefaultImpl = iConditionProvider;
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
                    object = ((Parcel)object).readInt() != 0 ? Uri.CREATOR.createFromParcel((Parcel)object) : null;
                    this.onUnsubscribe((Uri)object);
                    return true;
                }
                ((Parcel)object).enforceInterface(DESCRIPTOR);
                object = ((Parcel)object).readInt() != 0 ? Uri.CREATOR.createFromParcel((Parcel)object) : null;
                this.onSubscribe((Uri)object);
                return true;
            }
            ((Parcel)object).enforceInterface(DESCRIPTOR);
            this.onConnected();
            return true;
        }

        private static class Proxy
        implements IConditionProvider {
            public static IConditionProvider sDefaultImpl;
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
            public void onConnected() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(1, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onConnected();
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onSubscribe(Uri uri) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (uri != null) {
                        parcel.writeInt(1);
                        uri.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(2, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onSubscribe(uri);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onUnsubscribe(Uri uri) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (uri != null) {
                        parcel.writeInt(1);
                        uri.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(3, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onUnsubscribe(uri);
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

