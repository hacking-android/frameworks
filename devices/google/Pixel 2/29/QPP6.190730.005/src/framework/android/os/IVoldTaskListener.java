/*
 * Decompiled with CFR 0.145.
 */
package android.os;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.PersistableBundle;
import android.os.RemoteException;

public interface IVoldTaskListener
extends IInterface {
    public void onFinished(int var1, PersistableBundle var2) throws RemoteException;

    public void onStatus(int var1, PersistableBundle var2) throws RemoteException;

    public static class Default
    implements IVoldTaskListener {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void onFinished(int n, PersistableBundle persistableBundle) throws RemoteException {
        }

        @Override
        public void onStatus(int n, PersistableBundle persistableBundle) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IVoldTaskListener {
        private static final String DESCRIPTOR = "android.os.IVoldTaskListener";
        static final int TRANSACTION_onFinished = 2;
        static final int TRANSACTION_onStatus = 1;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IVoldTaskListener asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IVoldTaskListener) {
                return (IVoldTaskListener)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IVoldTaskListener getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            if (n != 1) {
                if (n != 2) {
                    return null;
                }
                return "onFinished";
            }
            return "onStatus";
        }

        public static boolean setDefaultImpl(IVoldTaskListener iVoldTaskListener) {
            if (Proxy.sDefaultImpl == null && iVoldTaskListener != null) {
                Proxy.sDefaultImpl = iVoldTaskListener;
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
                n = ((Parcel)object).readInt();
                object = ((Parcel)object).readInt() != 0 ? PersistableBundle.CREATOR.createFromParcel((Parcel)object) : null;
                this.onFinished(n, (PersistableBundle)object);
                return true;
            }
            ((Parcel)object).enforceInterface(DESCRIPTOR);
            n = ((Parcel)object).readInt();
            object = ((Parcel)object).readInt() != 0 ? PersistableBundle.CREATOR.createFromParcel((Parcel)object) : null;
            this.onStatus(n, (PersistableBundle)object);
            return true;
        }

        private static class Proxy
        implements IVoldTaskListener {
            public static IVoldTaskListener sDefaultImpl;
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
            public void onFinished(int n, PersistableBundle persistableBundle) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (persistableBundle != null) {
                        parcel.writeInt(1);
                        persistableBundle.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(2, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onFinished(n, persistableBundle);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onStatus(int n, PersistableBundle persistableBundle) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (persistableBundle != null) {
                        parcel.writeInt(1);
                        persistableBundle.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(1, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onStatus(n, persistableBundle);
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

