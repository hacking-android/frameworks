/*
 * Decompiled with CFR 0.145.
 */
package android.service.wallpaper;

import android.graphics.Rect;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import android.service.wallpaper.IWallpaperConnection;

public interface IWallpaperService
extends IInterface {
    public void attach(IWallpaperConnection var1, IBinder var2, int var3, boolean var4, int var5, int var6, Rect var7, int var8) throws RemoteException;

    public void detach() throws RemoteException;

    public static class Default
    implements IWallpaperService {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void attach(IWallpaperConnection iWallpaperConnection, IBinder iBinder, int n, boolean bl, int n2, int n3, Rect rect, int n4) throws RemoteException {
        }

        @Override
        public void detach() throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IWallpaperService {
        private static final String DESCRIPTOR = "android.service.wallpaper.IWallpaperService";
        static final int TRANSACTION_attach = 1;
        static final int TRANSACTION_detach = 2;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IWallpaperService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IWallpaperService) {
                return (IWallpaperService)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IWallpaperService getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            if (n != 1) {
                if (n != 2) {
                    return null;
                }
                return "detach";
            }
            return "attach";
        }

        public static boolean setDefaultImpl(IWallpaperService iWallpaperService) {
            if (Proxy.sDefaultImpl == null && iWallpaperService != null) {
                Proxy.sDefaultImpl = iWallpaperService;
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
                this.detach();
                return true;
            }
            parcel.enforceInterface(DESCRIPTOR);
            IWallpaperConnection iWallpaperConnection = IWallpaperConnection.Stub.asInterface(parcel.readStrongBinder());
            IBinder iBinder = parcel.readStrongBinder();
            n = parcel.readInt();
            boolean bl = parcel.readInt() != 0;
            int n3 = parcel.readInt();
            n2 = parcel.readInt();
            object = parcel.readInt() != 0 ? Rect.CREATOR.createFromParcel(parcel) : null;
            this.attach(iWallpaperConnection, iBinder, n, bl, n3, n2, (Rect)object, parcel.readInt());
            return true;
        }

        private static class Proxy
        implements IWallpaperService {
            public static IWallpaperService sDefaultImpl;
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override
            public IBinder asBinder() {
                return this.mRemote;
            }

            /*
             * Loose catch block
             * WARNING - void declaration
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             * Lifted jumps to return sites
             */
            @Override
            public void attach(IWallpaperConnection iWallpaperConnection, IBinder iBinder, int n, boolean bl, int n2, int n3, Rect rect, int n4) throws RemoteException {
                Parcel parcel;
                void var1_7;
                block13 : {
                    parcel = Parcel.obtain();
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder2 = iWallpaperConnection != null ? iWallpaperConnection.asBinder() : null;
                    parcel.writeStrongBinder(iBinder2);
                    try {
                        parcel.writeStrongBinder(iBinder);
                    }
                    catch (Throwable throwable) {
                        break block13;
                    }
                    try {
                        parcel.writeInt(n);
                        int n5 = bl ? 1 : 0;
                        parcel.writeInt(n5);
                    }
                    catch (Throwable throwable) {}
                    try {
                        parcel.writeInt(n2);
                    }
                    catch (Throwable throwable) {
                        break block13;
                    }
                    try {
                        parcel.writeInt(n3);
                        if (rect != null) {
                            parcel.writeInt(1);
                            rect.writeToParcel(parcel, 0);
                        } else {
                            parcel.writeInt(0);
                        }
                        parcel.writeInt(n4);
                        if (!this.mRemote.transact(1, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                            Stub.getDefaultImpl().attach(iWallpaperConnection, iBinder, n, bl, n2, n3, rect, n4);
                            parcel.recycle();
                            return;
                        }
                        parcel.recycle();
                        return;
                    }
                    catch (Throwable throwable) {}
                    break block13;
                    catch (Throwable throwable) {
                        // empty catch block
                    }
                }
                parcel.recycle();
                throw var1_7;
            }

            @Override
            public void detach() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(2, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().detach();
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }
        }

    }

}

