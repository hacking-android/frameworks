/*
 * Decompiled with CFR 0.145.
 */
package android.view;

import android.annotation.UnsupportedAppUsage;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import android.view.IRemoteAnimationFinishedCallback;
import android.view.RemoteAnimationTarget;

public interface IRemoteAnimationRunner
extends IInterface {
    @UnsupportedAppUsage
    public void onAnimationCancelled() throws RemoteException;

    @UnsupportedAppUsage
    public void onAnimationStart(RemoteAnimationTarget[] var1, IRemoteAnimationFinishedCallback var2) throws RemoteException;

    public static class Default
    implements IRemoteAnimationRunner {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void onAnimationCancelled() throws RemoteException {
        }

        @Override
        public void onAnimationStart(RemoteAnimationTarget[] arrremoteAnimationTarget, IRemoteAnimationFinishedCallback iRemoteAnimationFinishedCallback) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IRemoteAnimationRunner {
        private static final String DESCRIPTOR = "android.view.IRemoteAnimationRunner";
        static final int TRANSACTION_onAnimationCancelled = 2;
        static final int TRANSACTION_onAnimationStart = 1;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IRemoteAnimationRunner asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IRemoteAnimationRunner) {
                return (IRemoteAnimationRunner)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IRemoteAnimationRunner getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            if (n != 1) {
                if (n != 2) {
                    return null;
                }
                return "onAnimationCancelled";
            }
            return "onAnimationStart";
        }

        public static boolean setDefaultImpl(IRemoteAnimationRunner iRemoteAnimationRunner) {
            if (Proxy.sDefaultImpl == null && iRemoteAnimationRunner != null) {
                Proxy.sDefaultImpl = iRemoteAnimationRunner;
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
                if (n != 2) {
                    if (n != 1598968902) {
                        return super.onTransact(n, parcel, parcel2, n2);
                    }
                    parcel2.writeString(DESCRIPTOR);
                    return true;
                }
                parcel.enforceInterface(DESCRIPTOR);
                this.onAnimationCancelled();
                return true;
            }
            parcel.enforceInterface(DESCRIPTOR);
            this.onAnimationStart(parcel.createTypedArray(RemoteAnimationTarget.CREATOR), IRemoteAnimationFinishedCallback.Stub.asInterface(parcel.readStrongBinder()));
            return true;
        }

        private static class Proxy
        implements IRemoteAnimationRunner {
            public static IRemoteAnimationRunner sDefaultImpl;
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
            public void onAnimationCancelled() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(2, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onAnimationCancelled();
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
            public void onAnimationStart(RemoteAnimationTarget[] arrremoteAnimationTarget, IRemoteAnimationFinishedCallback iRemoteAnimationFinishedCallback) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeTypedArray((Parcelable[])arrremoteAnimationTarget, 0);
                    IBinder iBinder = iRemoteAnimationFinishedCallback != null ? iRemoteAnimationFinishedCallback.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (this.mRemote.transact(1, parcel, null, 1)) return;
                    if (Stub.getDefaultImpl() == null) return;
                    Stub.getDefaultImpl().onAnimationStart(arrremoteAnimationTarget, iRemoteAnimationFinishedCallback);
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }
        }

    }

}

