/*
 * Decompiled with CFR 0.145.
 */
package android.view;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IWindowSessionCallback
extends IInterface {
    public void onAnimatorScaleChanged(float var1) throws RemoteException;

    public static class Default
    implements IWindowSessionCallback {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void onAnimatorScaleChanged(float f) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IWindowSessionCallback {
        private static final String DESCRIPTOR = "android.view.IWindowSessionCallback";
        static final int TRANSACTION_onAnimatorScaleChanged = 1;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IWindowSessionCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IWindowSessionCallback) {
                return (IWindowSessionCallback)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IWindowSessionCallback getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            if (n != 1) {
                return null;
            }
            return "onAnimatorScaleChanged";
        }

        public static boolean setDefaultImpl(IWindowSessionCallback iWindowSessionCallback) {
            if (Proxy.sDefaultImpl == null && iWindowSessionCallback != null) {
                Proxy.sDefaultImpl = iWindowSessionCallback;
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
            this.onAnimatorScaleChanged(parcel.readFloat());
            return true;
        }

        private static class Proxy
        implements IWindowSessionCallback {
            public static IWindowSessionCallback sDefaultImpl;
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
            public void onAnimatorScaleChanged(float f) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeFloat(f);
                    if (!this.mRemote.transact(1, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onAnimatorScaleChanged(f);
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

