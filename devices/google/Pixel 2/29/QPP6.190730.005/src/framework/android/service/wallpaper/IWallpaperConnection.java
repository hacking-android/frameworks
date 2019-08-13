/*
 * Decompiled with CFR 0.145.
 */
package android.service.wallpaper;

import android.app.WallpaperColors;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.Parcelable;
import android.os.RemoteException;
import android.service.wallpaper.IWallpaperEngine;

public interface IWallpaperConnection
extends IInterface {
    public void attachEngine(IWallpaperEngine var1, int var2) throws RemoteException;

    public void engineShown(IWallpaperEngine var1) throws RemoteException;

    public void onWallpaperColorsChanged(WallpaperColors var1, int var2) throws RemoteException;

    public ParcelFileDescriptor setWallpaper(String var1) throws RemoteException;

    public static class Default
    implements IWallpaperConnection {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void attachEngine(IWallpaperEngine iWallpaperEngine, int n) throws RemoteException {
        }

        @Override
        public void engineShown(IWallpaperEngine iWallpaperEngine) throws RemoteException {
        }

        @Override
        public void onWallpaperColorsChanged(WallpaperColors wallpaperColors, int n) throws RemoteException {
        }

        @Override
        public ParcelFileDescriptor setWallpaper(String string2) throws RemoteException {
            return null;
        }
    }

    public static abstract class Stub
    extends Binder
    implements IWallpaperConnection {
        private static final String DESCRIPTOR = "android.service.wallpaper.IWallpaperConnection";
        static final int TRANSACTION_attachEngine = 1;
        static final int TRANSACTION_engineShown = 2;
        static final int TRANSACTION_onWallpaperColorsChanged = 4;
        static final int TRANSACTION_setWallpaper = 3;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IWallpaperConnection asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IWallpaperConnection) {
                return (IWallpaperConnection)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IWallpaperConnection getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            if (n != 1) {
                if (n != 2) {
                    if (n != 3) {
                        if (n != 4) {
                            return null;
                        }
                        return "onWallpaperColorsChanged";
                    }
                    return "setWallpaper";
                }
                return "engineShown";
            }
            return "attachEngine";
        }

        public static boolean setDefaultImpl(IWallpaperConnection iWallpaperConnection) {
            if (Proxy.sDefaultImpl == null && iWallpaperConnection != null) {
                Proxy.sDefaultImpl = iWallpaperConnection;
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
                        if (n != 4) {
                            if (n != 1598968902) {
                                return super.onTransact(n, (Parcel)object, parcel, n2);
                            }
                            parcel.writeString(DESCRIPTOR);
                            return true;
                        }
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        WallpaperColors wallpaperColors = ((Parcel)object).readInt() != 0 ? WallpaperColors.CREATOR.createFromParcel((Parcel)object) : null;
                        this.onWallpaperColorsChanged(wallpaperColors, ((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    ((Parcel)object).enforceInterface(DESCRIPTOR);
                    object = this.setWallpaper(((Parcel)object).readString());
                    parcel.writeNoException();
                    if (object != null) {
                        parcel.writeInt(1);
                        ((ParcelFileDescriptor)object).writeToParcel(parcel, 1);
                    } else {
                        parcel.writeInt(0);
                    }
                    return true;
                }
                ((Parcel)object).enforceInterface(DESCRIPTOR);
                this.engineShown(IWallpaperEngine.Stub.asInterface(((Parcel)object).readStrongBinder()));
                parcel.writeNoException();
                return true;
            }
            ((Parcel)object).enforceInterface(DESCRIPTOR);
            this.attachEngine(IWallpaperEngine.Stub.asInterface(((Parcel)object).readStrongBinder()), ((Parcel)object).readInt());
            parcel.writeNoException();
            return true;
        }

        private static class Proxy
        implements IWallpaperConnection {
            public static IWallpaperConnection sDefaultImpl;
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override
            public IBinder asBinder() {
                return this.mRemote;
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void attachEngine(IWallpaperEngine iWallpaperEngine, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iWallpaperEngine != null ? iWallpaperEngine.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(1, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().attachEngine(iWallpaperEngine, n);
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

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void engineShown(IWallpaperEngine iWallpaperEngine) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iWallpaperEngine != null ? iWallpaperEngine.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (!this.mRemote.transact(2, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().engineShown(iWallpaperEngine);
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

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            @Override
            public void onWallpaperColorsChanged(WallpaperColors wallpaperColors, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (wallpaperColors != null) {
                        parcel.writeInt(1);
                        wallpaperColors.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(4, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onWallpaperColorsChanged(wallpaperColors, n);
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

            @Override
            public ParcelFileDescriptor setWallpaper(String object) throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                block3 : {
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    try {
                        parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel.writeString((String)object);
                        if (this.mRemote.transact(3, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block3;
                        object = Stub.getDefaultImpl().setWallpaper((String)object);
                        parcel2.recycle();
                        parcel.recycle();
                        return object;
                    }
                    catch (Throwable throwable) {
                        parcel2.recycle();
                        parcel.recycle();
                        throw throwable;
                    }
                }
                parcel2.readException();
                object = parcel2.readInt() != 0 ? ParcelFileDescriptor.CREATOR.createFromParcel(parcel2) : null;
                parcel2.recycle();
                parcel.recycle();
                return object;
            }
        }

    }

}

