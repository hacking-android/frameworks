/*
 * Decompiled with CFR 0.145.
 */
package android.os;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;

public interface IProgressListener
extends IInterface {
    public void onFinished(int var1, Bundle var2) throws RemoteException;

    public void onProgress(int var1, int var2, Bundle var3) throws RemoteException;

    public void onStarted(int var1, Bundle var2) throws RemoteException;

    public static class Default
    implements IProgressListener {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void onFinished(int n, Bundle bundle) throws RemoteException {
        }

        @Override
        public void onProgress(int n, int n2, Bundle bundle) throws RemoteException {
        }

        @Override
        public void onStarted(int n, Bundle bundle) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IProgressListener {
        private static final String DESCRIPTOR = "android.os.IProgressListener";
        static final int TRANSACTION_onFinished = 3;
        static final int TRANSACTION_onProgress = 2;
        static final int TRANSACTION_onStarted = 1;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IProgressListener asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IProgressListener) {
                return (IProgressListener)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IProgressListener getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            if (n != 1) {
                if (n != 2) {
                    if (n != 3) {
                        return null;
                    }
                    return "onFinished";
                }
                return "onProgress";
            }
            return "onStarted";
        }

        public static boolean setDefaultImpl(IProgressListener iProgressListener) {
            if (Proxy.sDefaultImpl == null && iProgressListener != null) {
                Proxy.sDefaultImpl = iProgressListener;
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
                    object = ((Parcel)object).readInt() != 0 ? Bundle.CREATOR.createFromParcel((Parcel)object) : null;
                    this.onFinished(n, (Bundle)object);
                    return true;
                }
                ((Parcel)object).enforceInterface(DESCRIPTOR);
                n2 = ((Parcel)object).readInt();
                n = ((Parcel)object).readInt();
                object = ((Parcel)object).readInt() != 0 ? Bundle.CREATOR.createFromParcel((Parcel)object) : null;
                this.onProgress(n2, n, (Bundle)object);
                return true;
            }
            ((Parcel)object).enforceInterface(DESCRIPTOR);
            n = ((Parcel)object).readInt();
            object = ((Parcel)object).readInt() != 0 ? Bundle.CREATOR.createFromParcel((Parcel)object) : null;
            this.onStarted(n, (Bundle)object);
            return true;
        }

        private static class Proxy
        implements IProgressListener {
            public static IProgressListener sDefaultImpl;
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
            public void onFinished(int n, Bundle bundle) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (bundle != null) {
                        parcel.writeInt(1);
                        bundle.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(3, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onFinished(n, bundle);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onProgress(int n, int n2, Bundle bundle) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    if (bundle != null) {
                        parcel.writeInt(1);
                        bundle.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(2, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onProgress(n, n2, bundle);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onStarted(int n, Bundle bundle) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (bundle != null) {
                        parcel.writeInt(1);
                        bundle.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(1, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onStarted(n, bundle);
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

