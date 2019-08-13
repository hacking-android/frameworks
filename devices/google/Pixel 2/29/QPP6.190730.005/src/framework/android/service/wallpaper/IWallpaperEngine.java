/*
 * Decompiled with CFR 0.145.
 */
package android.service.wallpaper;

import android.annotation.UnsupportedAppUsage;
import android.graphics.Rect;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import android.view.MotionEvent;

public interface IWallpaperEngine
extends IInterface {
    @UnsupportedAppUsage
    public void destroy() throws RemoteException;

    @UnsupportedAppUsage
    public void dispatchPointer(MotionEvent var1) throws RemoteException;

    @UnsupportedAppUsage
    public void dispatchWallpaperCommand(String var1, int var2, int var3, int var4, Bundle var5) throws RemoteException;

    public void requestWallpaperColors() throws RemoteException;

    public void setDesiredSize(int var1, int var2) throws RemoteException;

    public void setDisplayPadding(Rect var1) throws RemoteException;

    public void setInAmbientMode(boolean var1, long var2) throws RemoteException;

    @UnsupportedAppUsage
    public void setVisibility(boolean var1) throws RemoteException;

    public static class Default
    implements IWallpaperEngine {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void destroy() throws RemoteException {
        }

        @Override
        public void dispatchPointer(MotionEvent motionEvent) throws RemoteException {
        }

        @Override
        public void dispatchWallpaperCommand(String string2, int n, int n2, int n3, Bundle bundle) throws RemoteException {
        }

        @Override
        public void requestWallpaperColors() throws RemoteException {
        }

        @Override
        public void setDesiredSize(int n, int n2) throws RemoteException {
        }

        @Override
        public void setDisplayPadding(Rect rect) throws RemoteException {
        }

        @Override
        public void setInAmbientMode(boolean bl, long l) throws RemoteException {
        }

        @Override
        public void setVisibility(boolean bl) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IWallpaperEngine {
        private static final String DESCRIPTOR = "android.service.wallpaper.IWallpaperEngine";
        static final int TRANSACTION_destroy = 8;
        static final int TRANSACTION_dispatchPointer = 5;
        static final int TRANSACTION_dispatchWallpaperCommand = 6;
        static final int TRANSACTION_requestWallpaperColors = 7;
        static final int TRANSACTION_setDesiredSize = 1;
        static final int TRANSACTION_setDisplayPadding = 2;
        static final int TRANSACTION_setInAmbientMode = 4;
        static final int TRANSACTION_setVisibility = 3;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IWallpaperEngine asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IWallpaperEngine) {
                return (IWallpaperEngine)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IWallpaperEngine getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            switch (n) {
                default: {
                    return null;
                }
                case 8: {
                    return "destroy";
                }
                case 7: {
                    return "requestWallpaperColors";
                }
                case 6: {
                    return "dispatchWallpaperCommand";
                }
                case 5: {
                    return "dispatchPointer";
                }
                case 4: {
                    return "setInAmbientMode";
                }
                case 3: {
                    return "setVisibility";
                }
                case 2: {
                    return "setDisplayPadding";
                }
                case 1: 
            }
            return "setDesiredSize";
        }

        public static boolean setDefaultImpl(IWallpaperEngine iWallpaperEngine) {
            if (Proxy.sDefaultImpl == null && iWallpaperEngine != null) {
                Proxy.sDefaultImpl = iWallpaperEngine;
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
        public boolean onTransact(int n, Parcel object, Parcel object2, int n2) throws RemoteException {
            if (n != 1598968902) {
                boolean bl = false;
                boolean bl2 = false;
                switch (n) {
                    default: {
                        return super.onTransact(n, (Parcel)object, (Parcel)object2, n2);
                    }
                    case 8: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.destroy();
                        return true;
                    }
                    case 7: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.requestWallpaperColors();
                        return true;
                    }
                    case 6: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object2 = ((Parcel)object).readString();
                        n = ((Parcel)object).readInt();
                        int n3 = ((Parcel)object).readInt();
                        n2 = ((Parcel)object).readInt();
                        object = ((Parcel)object).readInt() != 0 ? Bundle.CREATOR.createFromParcel((Parcel)object) : null;
                        this.dispatchWallpaperCommand((String)object2, n, n3, n2, (Bundle)object);
                        return true;
                    }
                    case 5: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = ((Parcel)object).readInt() != 0 ? MotionEvent.CREATOR.createFromParcel((Parcel)object) : null;
                        this.dispatchPointer((MotionEvent)object);
                        return true;
                    }
                    case 4: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        if (((Parcel)object).readInt() != 0) {
                            bl2 = true;
                        }
                        this.setInAmbientMode(bl2, ((Parcel)object).readLong());
                        return true;
                    }
                    case 3: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        bl2 = bl;
                        if (((Parcel)object).readInt() != 0) {
                            bl2 = true;
                        }
                        this.setVisibility(bl2);
                        return true;
                    }
                    case 2: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = ((Parcel)object).readInt() != 0 ? Rect.CREATOR.createFromParcel((Parcel)object) : null;
                        this.setDisplayPadding((Rect)object);
                        return true;
                    }
                    case 1: 
                }
                ((Parcel)object).enforceInterface(DESCRIPTOR);
                this.setDesiredSize(((Parcel)object).readInt(), ((Parcel)object).readInt());
                return true;
            }
            ((Parcel)object2).writeString(DESCRIPTOR);
            return true;
        }

        private static class Proxy
        implements IWallpaperEngine {
            public static IWallpaperEngine sDefaultImpl;
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override
            public IBinder asBinder() {
                return this.mRemote;
            }

            @Override
            public void destroy() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(8, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().destroy();
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void dispatchPointer(MotionEvent motionEvent) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (motionEvent != null) {
                        parcel.writeInt(1);
                        motionEvent.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(5, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().dispatchPointer(motionEvent);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void dispatchWallpaperCommand(String string2, int n, int n2, int n3, Bundle bundle) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    parcel.writeInt(n3);
                    if (bundle != null) {
                        parcel.writeInt(1);
                        bundle.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(6, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().dispatchWallpaperCommand(string2, n, n2, n3, bundle);
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

            @Override
            public void requestWallpaperColors() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(7, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().requestWallpaperColors();
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void setDesiredSize(int n, int n2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(1, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setDesiredSize(n, n2);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void setDisplayPadding(Rect rect) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (rect != null) {
                        parcel.writeInt(1);
                        rect.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(2, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setDisplayPadding(rect);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void setInAmbientMode(boolean bl, long l) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                int n = bl ? 1 : 0;
                try {
                    parcel.writeInt(n);
                    parcel.writeLong(l);
                    if (!this.mRemote.transact(4, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setInAmbientMode(bl, l);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void setVisibility(boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                int n = bl ? 1 : 0;
                try {
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(3, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setVisibility(bl);
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

