/*
 * Decompiled with CFR 0.145.
 */
package android.view;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import android.view.AppTransitionAnimationSpec;

public interface IAppTransitionAnimationSpecsFuture
extends IInterface {
    public AppTransitionAnimationSpec[] get() throws RemoteException;

    public static class Default
    implements IAppTransitionAnimationSpecsFuture {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public AppTransitionAnimationSpec[] get() throws RemoteException {
            return null;
        }
    }

    public static abstract class Stub
    extends Binder
    implements IAppTransitionAnimationSpecsFuture {
        private static final String DESCRIPTOR = "android.view.IAppTransitionAnimationSpecsFuture";
        static final int TRANSACTION_get = 1;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IAppTransitionAnimationSpecsFuture asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IAppTransitionAnimationSpecsFuture) {
                return (IAppTransitionAnimationSpecsFuture)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IAppTransitionAnimationSpecsFuture getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            if (n != 1) {
                return null;
            }
            return "get";
        }

        public static boolean setDefaultImpl(IAppTransitionAnimationSpecsFuture iAppTransitionAnimationSpecsFuture) {
            if (Proxy.sDefaultImpl == null && iAppTransitionAnimationSpecsFuture != null) {
                Proxy.sDefaultImpl = iAppTransitionAnimationSpecsFuture;
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
        public boolean onTransact(int n, Parcel arrparcelable, Parcel parcel, int n2) throws RemoteException {
            if (n != 1) {
                if (n != 1598968902) {
                    return super.onTransact(n, (Parcel)arrparcelable, parcel, n2);
                }
                parcel.writeString(DESCRIPTOR);
                return true;
            }
            arrparcelable.enforceInterface(DESCRIPTOR);
            arrparcelable = this.get();
            parcel.writeNoException();
            parcel.writeTypedArray(arrparcelable, 1);
            return true;
        }

        private static class Proxy
        implements IAppTransitionAnimationSpecsFuture {
            public static IAppTransitionAnimationSpecsFuture sDefaultImpl;
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override
            public IBinder asBinder() {
                return this.mRemote;
            }

            @Override
            public AppTransitionAnimationSpec[] get() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(1, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        AppTransitionAnimationSpec[] arrappTransitionAnimationSpec = Stub.getDefaultImpl().get();
                        return arrappTransitionAnimationSpec;
                    }
                    parcel2.readException();
                    AppTransitionAnimationSpec[] arrappTransitionAnimationSpec = parcel2.createTypedArray(AppTransitionAnimationSpec.CREATOR);
                    return arrappTransitionAnimationSpec;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }
        }

    }

}

