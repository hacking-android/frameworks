/*
 * Decompiled with CFR 0.145.
 */
package android.view;

import android.annotation.UnsupportedAppUsage;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IRemoteAnimationFinishedCallback
extends IInterface {
    @UnsupportedAppUsage
    public void onAnimationFinished() throws RemoteException;

    public static class Default
    implements IRemoteAnimationFinishedCallback {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void onAnimationFinished() throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IRemoteAnimationFinishedCallback {
        private static final String DESCRIPTOR = "android.view.IRemoteAnimationFinishedCallback";
        static final int TRANSACTION_onAnimationFinished = 1;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IRemoteAnimationFinishedCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IRemoteAnimationFinishedCallback) {
                return (IRemoteAnimationFinishedCallback)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IRemoteAnimationFinishedCallback getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            if (n != 1) {
                return null;
            }
            return "onAnimationFinished";
        }

        public static boolean setDefaultImpl(IRemoteAnimationFinishedCallback iRemoteAnimationFinishedCallback) {
            if (Proxy.sDefaultImpl == null && iRemoteAnimationFinishedCallback != null) {
                Proxy.sDefaultImpl = iRemoteAnimationFinishedCallback;
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
            this.onAnimationFinished();
            parcel2.writeNoException();
            return true;
        }

        private static class Proxy
        implements IRemoteAnimationFinishedCallback {
            public static IRemoteAnimationFinishedCallback sDefaultImpl;
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
            public void onAnimationFinished() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(1, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onAnimationFinished();
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }
        }

    }

}

