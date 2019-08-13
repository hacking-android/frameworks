/*
 * Decompiled with CFR 0.145.
 */
package android.app;

import android.app.WallpaperColors;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;

public interface IWallpaperManagerCallback
extends IInterface {
    public void onWallpaperChanged() throws RemoteException;

    public void onWallpaperColorsChanged(WallpaperColors var1, int var2, int var3) throws RemoteException;

    public static class Default
    implements IWallpaperManagerCallback {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void onWallpaperChanged() throws RemoteException {
        }

        @Override
        public void onWallpaperColorsChanged(WallpaperColors wallpaperColors, int n, int n2) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IWallpaperManagerCallback {
        private static final String DESCRIPTOR = "android.app.IWallpaperManagerCallback";
        static final int TRANSACTION_onWallpaperChanged = 1;
        static final int TRANSACTION_onWallpaperColorsChanged = 2;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IWallpaperManagerCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IWallpaperManagerCallback) {
                return (IWallpaperManagerCallback)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IWallpaperManagerCallback getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            if (n != 1) {
                if (n != 2) {
                    return null;
                }
                return "onWallpaperColorsChanged";
            }
            return "onWallpaperChanged";
        }

        public static boolean setDefaultImpl(IWallpaperManagerCallback iWallpaperManagerCallback) {
            if (Proxy.sDefaultImpl == null && iWallpaperManagerCallback != null) {
                Proxy.sDefaultImpl = iWallpaperManagerCallback;
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
        public boolean onTransact(int n, Parcel parcel, Parcel object, int n2) throws RemoteException {
            if (n != 1) {
                if (n != 2) {
                    if (n != 1598968902) {
                        return super.onTransact(n, parcel, (Parcel)object, n2);
                    }
                    ((Parcel)object).writeString(DESCRIPTOR);
                    return true;
                }
                parcel.enforceInterface(DESCRIPTOR);
                object = parcel.readInt() != 0 ? WallpaperColors.CREATOR.createFromParcel(parcel) : null;
                this.onWallpaperColorsChanged((WallpaperColors)object, parcel.readInt(), parcel.readInt());
                return true;
            }
            parcel.enforceInterface(DESCRIPTOR);
            this.onWallpaperChanged();
            return true;
        }

        private static class Proxy
        implements IWallpaperManagerCallback {
            public static IWallpaperManagerCallback sDefaultImpl;
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
            public void onWallpaperChanged() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(1, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onWallpaperChanged();
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onWallpaperColorsChanged(WallpaperColors wallpaperColors, int n, int n2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (wallpaperColors != null) {
                        parcel.writeInt(1);
                        wallpaperColors.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(2, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onWallpaperColorsChanged(wallpaperColors, n, n2);
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

