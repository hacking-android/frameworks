/*
 * Decompiled with CFR 0.145.
 */
package android.view;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IWallpaperVisibilityListener
extends IInterface {
    public void onWallpaperVisibilityChanged(boolean var1, int var2) throws RemoteException;

    public static class Default
    implements IWallpaperVisibilityListener {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void onWallpaperVisibilityChanged(boolean bl, int n) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IWallpaperVisibilityListener {
        private static final String DESCRIPTOR = "android.view.IWallpaperVisibilityListener";
        static final int TRANSACTION_onWallpaperVisibilityChanged = 1;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IWallpaperVisibilityListener asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IWallpaperVisibilityListener) {
                return (IWallpaperVisibilityListener)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IWallpaperVisibilityListener getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            if (n != 1) {
                return null;
            }
            return "onWallpaperVisibilityChanged";
        }

        public static boolean setDefaultImpl(IWallpaperVisibilityListener iWallpaperVisibilityListener) {
            if (Proxy.sDefaultImpl == null && iWallpaperVisibilityListener != null) {
                Proxy.sDefaultImpl = iWallpaperVisibilityListener;
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
            boolean bl = parcel.readInt() != 0;
            this.onWallpaperVisibilityChanged(bl, parcel.readInt());
            return true;
        }

        private static class Proxy
        implements IWallpaperVisibilityListener {
            public static IWallpaperVisibilityListener sDefaultImpl;
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
            public void onWallpaperVisibilityChanged(boolean bl, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                int n2 = bl ? 1 : 0;
                try {
                    parcel.writeInt(n2);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(1, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onWallpaperVisibilityChanged(bl, n);
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

