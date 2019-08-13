/*
 * Decompiled with CFR 0.145.
 */
package android.app;

import android.annotation.UnsupportedAppUsage;
import android.app.IWallpaperManagerCallback;
import android.app.WallpaperColors;
import android.app.WallpaperInfo;
import android.content.ComponentName;
import android.graphics.Rect;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.Parcelable;
import android.os.RemoteException;

public interface IWallpaperManager
extends IInterface {
    public void clearWallpaper(String var1, int var2, int var3) throws RemoteException;

    @UnsupportedAppUsage
    public int getHeightHint(int var1) throws RemoteException;

    public String getName() throws RemoteException;

    @UnsupportedAppUsage
    public ParcelFileDescriptor getWallpaper(String var1, IWallpaperManagerCallback var2, int var3, Bundle var4, int var5) throws RemoteException;

    public WallpaperColors getWallpaperColors(int var1, int var2, int var3) throws RemoteException;

    public int getWallpaperIdForUser(int var1, int var2) throws RemoteException;

    @UnsupportedAppUsage
    public WallpaperInfo getWallpaperInfo(int var1) throws RemoteException;

    @UnsupportedAppUsage
    public int getWidthHint(int var1) throws RemoteException;

    @UnsupportedAppUsage
    public boolean hasNamedWallpaper(String var1) throws RemoteException;

    public boolean isSetWallpaperAllowed(String var1) throws RemoteException;

    public boolean isWallpaperBackupEligible(int var1, int var2) throws RemoteException;

    public boolean isWallpaperSupported(String var1) throws RemoteException;

    public void registerWallpaperColorsCallback(IWallpaperManagerCallback var1, int var2, int var3) throws RemoteException;

    public void setDimensionHints(int var1, int var2, String var3, int var4) throws RemoteException;

    public void setDisplayPadding(Rect var1, String var2, int var3) throws RemoteException;

    public void setInAmbientMode(boolean var1, long var2) throws RemoteException;

    public boolean setLockWallpaperCallback(IWallpaperManagerCallback var1) throws RemoteException;

    public ParcelFileDescriptor setWallpaper(String var1, String var2, Rect var3, boolean var4, Bundle var5, int var6, IWallpaperManagerCallback var7, int var8) throws RemoteException;

    @UnsupportedAppUsage
    public void setWallpaperComponent(ComponentName var1) throws RemoteException;

    public void setWallpaperComponentChecked(ComponentName var1, String var2, int var3) throws RemoteException;

    public void settingsRestored() throws RemoteException;

    public void unregisterWallpaperColorsCallback(IWallpaperManagerCallback var1, int var2, int var3) throws RemoteException;

    public static class Default
    implements IWallpaperManager {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void clearWallpaper(String string2, int n, int n2) throws RemoteException {
        }

        @Override
        public int getHeightHint(int n) throws RemoteException {
            return 0;
        }

        @Override
        public String getName() throws RemoteException {
            return null;
        }

        @Override
        public ParcelFileDescriptor getWallpaper(String string2, IWallpaperManagerCallback iWallpaperManagerCallback, int n, Bundle bundle, int n2) throws RemoteException {
            return null;
        }

        @Override
        public WallpaperColors getWallpaperColors(int n, int n2, int n3) throws RemoteException {
            return null;
        }

        @Override
        public int getWallpaperIdForUser(int n, int n2) throws RemoteException {
            return 0;
        }

        @Override
        public WallpaperInfo getWallpaperInfo(int n) throws RemoteException {
            return null;
        }

        @Override
        public int getWidthHint(int n) throws RemoteException {
            return 0;
        }

        @Override
        public boolean hasNamedWallpaper(String string2) throws RemoteException {
            return false;
        }

        @Override
        public boolean isSetWallpaperAllowed(String string2) throws RemoteException {
            return false;
        }

        @Override
        public boolean isWallpaperBackupEligible(int n, int n2) throws RemoteException {
            return false;
        }

        @Override
        public boolean isWallpaperSupported(String string2) throws RemoteException {
            return false;
        }

        @Override
        public void registerWallpaperColorsCallback(IWallpaperManagerCallback iWallpaperManagerCallback, int n, int n2) throws RemoteException {
        }

        @Override
        public void setDimensionHints(int n, int n2, String string2, int n3) throws RemoteException {
        }

        @Override
        public void setDisplayPadding(Rect rect, String string2, int n) throws RemoteException {
        }

        @Override
        public void setInAmbientMode(boolean bl, long l) throws RemoteException {
        }

        @Override
        public boolean setLockWallpaperCallback(IWallpaperManagerCallback iWallpaperManagerCallback) throws RemoteException {
            return false;
        }

        @Override
        public ParcelFileDescriptor setWallpaper(String string2, String string3, Rect rect, boolean bl, Bundle bundle, int n, IWallpaperManagerCallback iWallpaperManagerCallback, int n2) throws RemoteException {
            return null;
        }

        @Override
        public void setWallpaperComponent(ComponentName componentName) throws RemoteException {
        }

        @Override
        public void setWallpaperComponentChecked(ComponentName componentName, String string2, int n) throws RemoteException {
        }

        @Override
        public void settingsRestored() throws RemoteException {
        }

        @Override
        public void unregisterWallpaperColorsCallback(IWallpaperManagerCallback iWallpaperManagerCallback, int n, int n2) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IWallpaperManager {
        private static final String DESCRIPTOR = "android.app.IWallpaperManager";
        static final int TRANSACTION_clearWallpaper = 7;
        static final int TRANSACTION_getHeightHint = 11;
        static final int TRANSACTION_getName = 13;
        static final int TRANSACTION_getWallpaper = 4;
        static final int TRANSACTION_getWallpaperColors = 19;
        static final int TRANSACTION_getWallpaperIdForUser = 5;
        static final int TRANSACTION_getWallpaperInfo = 6;
        static final int TRANSACTION_getWidthHint = 10;
        static final int TRANSACTION_hasNamedWallpaper = 8;
        static final int TRANSACTION_isSetWallpaperAllowed = 16;
        static final int TRANSACTION_isWallpaperBackupEligible = 17;
        static final int TRANSACTION_isWallpaperSupported = 15;
        static final int TRANSACTION_registerWallpaperColorsCallback = 20;
        static final int TRANSACTION_setDimensionHints = 9;
        static final int TRANSACTION_setDisplayPadding = 12;
        static final int TRANSACTION_setInAmbientMode = 22;
        static final int TRANSACTION_setLockWallpaperCallback = 18;
        static final int TRANSACTION_setWallpaper = 1;
        static final int TRANSACTION_setWallpaperComponent = 3;
        static final int TRANSACTION_setWallpaperComponentChecked = 2;
        static final int TRANSACTION_settingsRestored = 14;
        static final int TRANSACTION_unregisterWallpaperColorsCallback = 21;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IWallpaperManager asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IWallpaperManager) {
                return (IWallpaperManager)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IWallpaperManager getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            switch (n) {
                default: {
                    return null;
                }
                case 22: {
                    return "setInAmbientMode";
                }
                case 21: {
                    return "unregisterWallpaperColorsCallback";
                }
                case 20: {
                    return "registerWallpaperColorsCallback";
                }
                case 19: {
                    return "getWallpaperColors";
                }
                case 18: {
                    return "setLockWallpaperCallback";
                }
                case 17: {
                    return "isWallpaperBackupEligible";
                }
                case 16: {
                    return "isSetWallpaperAllowed";
                }
                case 15: {
                    return "isWallpaperSupported";
                }
                case 14: {
                    return "settingsRestored";
                }
                case 13: {
                    return "getName";
                }
                case 12: {
                    return "setDisplayPadding";
                }
                case 11: {
                    return "getHeightHint";
                }
                case 10: {
                    return "getWidthHint";
                }
                case 9: {
                    return "setDimensionHints";
                }
                case 8: {
                    return "hasNamedWallpaper";
                }
                case 7: {
                    return "clearWallpaper";
                }
                case 6: {
                    return "getWallpaperInfo";
                }
                case 5: {
                    return "getWallpaperIdForUser";
                }
                case 4: {
                    return "getWallpaper";
                }
                case 3: {
                    return "setWallpaperComponent";
                }
                case 2: {
                    return "setWallpaperComponentChecked";
                }
                case 1: 
            }
            return "setWallpaper";
        }

        public static boolean setDefaultImpl(IWallpaperManager iWallpaperManager) {
            if (Proxy.sDefaultImpl == null && iWallpaperManager != null) {
                Proxy.sDefaultImpl = iWallpaperManager;
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
            if (n != 1598968902) {
                boolean bl = false;
                switch (n) {
                    default: {
                        return super.onTransact(n, (Parcel)object, parcel, n2);
                    }
                    case 22: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        if (((Parcel)object).readInt() != 0) {
                            bl = true;
                        }
                        this.setInAmbientMode(bl, ((Parcel)object).readLong());
                        return true;
                    }
                    case 21: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.unregisterWallpaperColorsCallback(IWallpaperManagerCallback.Stub.asInterface(((Parcel)object).readStrongBinder()), ((Parcel)object).readInt(), ((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 20: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.registerWallpaperColorsCallback(IWallpaperManagerCallback.Stub.asInterface(((Parcel)object).readStrongBinder()), ((Parcel)object).readInt(), ((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 19: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getWallpaperColors(((Parcel)object).readInt(), ((Parcel)object).readInt(), ((Parcel)object).readInt());
                        parcel.writeNoException();
                        if (object != null) {
                            parcel.writeInt(1);
                            ((WallpaperColors)object).writeToParcel(parcel, 1);
                        } else {
                            parcel.writeInt(0);
                        }
                        return true;
                    }
                    case 18: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.setLockWallpaperCallback(IWallpaperManagerCallback.Stub.asInterface(((Parcel)object).readStrongBinder())) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 17: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.isWallpaperBackupEligible(((Parcel)object).readInt(), ((Parcel)object).readInt()) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 16: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.isSetWallpaperAllowed(((Parcel)object).readString()) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 15: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.isWallpaperSupported(((Parcel)object).readString()) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 14: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.settingsRestored();
                        parcel.writeNoException();
                        return true;
                    }
                    case 13: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getName();
                        parcel.writeNoException();
                        parcel.writeString((String)object);
                        return true;
                    }
                    case 12: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        Rect rect = ((Parcel)object).readInt() != 0 ? Rect.CREATOR.createFromParcel((Parcel)object) : null;
                        this.setDisplayPadding(rect, ((Parcel)object).readString(), ((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 11: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.getHeightHint(((Parcel)object).readInt());
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 10: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.getWidthHint(((Parcel)object).readInt());
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 9: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.setDimensionHints(((Parcel)object).readInt(), ((Parcel)object).readInt(), ((Parcel)object).readString(), ((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 8: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.hasNamedWallpaper(((Parcel)object).readString()) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 7: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.clearWallpaper(((Parcel)object).readString(), ((Parcel)object).readInt(), ((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 6: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getWallpaperInfo(((Parcel)object).readInt());
                        parcel.writeNoException();
                        if (object != null) {
                            parcel.writeInt(1);
                            ((WallpaperInfo)object).writeToParcel(parcel, 1);
                        } else {
                            parcel.writeInt(0);
                        }
                        return true;
                    }
                    case 5: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.getWallpaperIdForUser(((Parcel)object).readInt(), ((Parcel)object).readInt());
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 4: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        String string2 = ((Parcel)object).readString();
                        IWallpaperManagerCallback iWallpaperManagerCallback = IWallpaperManagerCallback.Stub.asInterface(((Parcel)object).readStrongBinder());
                        n = ((Parcel)object).readInt();
                        Bundle bundle = new Bundle();
                        object = this.getWallpaper(string2, iWallpaperManagerCallback, n, bundle, ((Parcel)object).readInt());
                        parcel.writeNoException();
                        if (object != null) {
                            parcel.writeInt(1);
                            ((ParcelFileDescriptor)object).writeToParcel(parcel, 1);
                        } else {
                            parcel.writeInt(0);
                        }
                        parcel.writeInt(1);
                        bundle.writeToParcel(parcel, 1);
                        return true;
                    }
                    case 3: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = ((Parcel)object).readInt() != 0 ? ComponentName.CREATOR.createFromParcel((Parcel)object) : null;
                        this.setWallpaperComponent((ComponentName)object);
                        parcel.writeNoException();
                        return true;
                    }
                    case 2: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        ComponentName componentName = ((Parcel)object).readInt() != 0 ? ComponentName.CREATOR.createFromParcel((Parcel)object) : null;
                        this.setWallpaperComponentChecked(componentName, ((Parcel)object).readString(), ((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 1: 
                }
                ((Parcel)object).enforceInterface(DESCRIPTOR);
                String string3 = ((Parcel)object).readString();
                String string4 = ((Parcel)object).readString();
                Rect rect = ((Parcel)object).readInt() != 0 ? Rect.CREATOR.createFromParcel((Parcel)object) : null;
                bl = ((Parcel)object).readInt() != 0;
                Bundle bundle = new Bundle();
                object = this.setWallpaper(string3, string4, rect, bl, bundle, ((Parcel)object).readInt(), IWallpaperManagerCallback.Stub.asInterface(((Parcel)object).readStrongBinder()), ((Parcel)object).readInt());
                parcel.writeNoException();
                if (object != null) {
                    parcel.writeInt(1);
                    ((ParcelFileDescriptor)object).writeToParcel(parcel, 1);
                } else {
                    parcel.writeInt(0);
                }
                parcel.writeInt(1);
                bundle.writeToParcel(parcel, 1);
                return true;
            }
            parcel.writeString(DESCRIPTOR);
            return true;
        }

        private static class Proxy
        implements IWallpaperManager {
            public static IWallpaperManager sDefaultImpl;
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override
            public IBinder asBinder() {
                return this.mRemote;
            }

            @Override
            public void clearWallpaper(String string2, int n, int n2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(7, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().clearWallpaper(string2, n, n2);
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
            public int getHeightHint(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(11, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        n = Stub.getDefaultImpl().getHeightHint(n);
                        return n;
                    }
                    parcel2.readException();
                    n = parcel2.readInt();
                    return n;
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
            public String getName() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(13, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        String string2 = Stub.getDefaultImpl().getName();
                        return string2;
                    }
                    parcel2.readException();
                    String string3 = parcel2.readString();
                    return string3;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
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
            public ParcelFileDescriptor getWallpaper(String object, IWallpaperManagerCallback iWallpaperManagerCallback, int n, Bundle bundle, int n2) throws RemoteException {
                Parcel parcel;
                void var1_5;
                Parcel parcel2;
                block15 : {
                    block14 : {
                        Object var8_15;
                        parcel = Parcel.obtain();
                        parcel2 = Parcel.obtain();
                        parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                        try {
                            parcel.writeString((String)object);
                            var8_15 = null;
                            IBinder iBinder = iWallpaperManagerCallback != null ? iWallpaperManagerCallback.asBinder() : null;
                            parcel.writeStrongBinder(iBinder);
                        }
                        catch (Throwable throwable) {}
                        try {
                            parcel.writeInt(n);
                        }
                        catch (Throwable throwable) {
                            break block15;
                        }
                        try {
                            parcel.writeInt(n2);
                        }
                        catch (Throwable throwable) {
                            break block15;
                        }
                        try {
                            if (!this.mRemote.transact(4, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                                object = Stub.getDefaultImpl().getWallpaper((String)object, iWallpaperManagerCallback, n, bundle, n2);
                                parcel2.recycle();
                                parcel.recycle();
                                return object;
                            }
                            parcel2.readException();
                            object = parcel2.readInt() != 0 ? ParcelFileDescriptor.CREATOR.createFromParcel(parcel2) : var8_15;
                            n = parcel2.readInt();
                            if (n == 0) break block14;
                        }
                        catch (Throwable throwable) {}
                        try {
                            bundle.readFromParcel(parcel2);
                        }
                        catch (Throwable throwable) {
                            break block15;
                        }
                    }
                    parcel2.recycle();
                    parcel.recycle();
                    return object;
                    break block15;
                    catch (Throwable throwable) {
                        // empty catch block
                    }
                }
                parcel2.recycle();
                parcel.recycle();
                throw var1_5;
            }

            @Override
            public WallpaperColors getWallpaperColors(int n, int n2, int n3) throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                block3 : {
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    try {
                        parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel.writeInt(n);
                        parcel.writeInt(n2);
                        parcel.writeInt(n3);
                        if (this.mRemote.transact(19, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block3;
                        WallpaperColors wallpaperColors = Stub.getDefaultImpl().getWallpaperColors(n, n2, n3);
                        parcel2.recycle();
                        parcel.recycle();
                        return wallpaperColors;
                    }
                    catch (Throwable throwable) {
                        parcel2.recycle();
                        parcel.recycle();
                        throw throwable;
                    }
                }
                parcel2.readException();
                WallpaperColors wallpaperColors = parcel2.readInt() != 0 ? WallpaperColors.CREATOR.createFromParcel(parcel2) : null;
                parcel2.recycle();
                parcel.recycle();
                return wallpaperColors;
            }

            @Override
            public int getWallpaperIdForUser(int n, int n2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(5, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        n = Stub.getDefaultImpl().getWallpaperIdForUser(n, n2);
                        return n;
                    }
                    parcel2.readException();
                    n = parcel2.readInt();
                    return n;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public WallpaperInfo getWallpaperInfo(int n) throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                block3 : {
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    try {
                        parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel.writeInt(n);
                        if (this.mRemote.transact(6, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block3;
                        WallpaperInfo wallpaperInfo = Stub.getDefaultImpl().getWallpaperInfo(n);
                        parcel2.recycle();
                        parcel.recycle();
                        return wallpaperInfo;
                    }
                    catch (Throwable throwable) {
                        parcel2.recycle();
                        parcel.recycle();
                        throw throwable;
                    }
                }
                parcel2.readException();
                WallpaperInfo wallpaperInfo = parcel2.readInt() != 0 ? WallpaperInfo.CREATOR.createFromParcel(parcel2) : null;
                parcel2.recycle();
                parcel.recycle();
                return wallpaperInfo;
            }

            @Override
            public int getWidthHint(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(10, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        n = Stub.getDefaultImpl().getWidthHint(n);
                        return n;
                    }
                    parcel2.readException();
                    n = parcel2.readInt();
                    return n;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public boolean hasNamedWallpaper(String string2) throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                boolean bl;
                block5 : {
                    IBinder iBinder;
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    try {
                        parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel.writeString(string2);
                        iBinder = this.mRemote;
                        bl = false;
                    }
                    catch (Throwable throwable) {
                        parcel2.recycle();
                        parcel.recycle();
                        throw throwable;
                    }
                    if (iBinder.transact(8, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().hasNamedWallpaper(string2);
                    parcel2.recycle();
                    parcel.recycle();
                    return bl;
                }
                parcel2.readException();
                int n = parcel2.readInt();
                if (n != 0) {
                    bl = true;
                }
                parcel2.recycle();
                parcel.recycle();
                return bl;
            }

            @Override
            public boolean isSetWallpaperAllowed(String string2) throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                boolean bl;
                block5 : {
                    IBinder iBinder;
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    try {
                        parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel.writeString(string2);
                        iBinder = this.mRemote;
                        bl = false;
                    }
                    catch (Throwable throwable) {
                        parcel2.recycle();
                        parcel.recycle();
                        throw throwable;
                    }
                    if (iBinder.transact(16, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().isSetWallpaperAllowed(string2);
                    parcel2.recycle();
                    parcel.recycle();
                    return bl;
                }
                parcel2.readException();
                int n = parcel2.readInt();
                if (n != 0) {
                    bl = true;
                }
                parcel2.recycle();
                parcel.recycle();
                return bl;
            }

            @Override
            public boolean isWallpaperBackupEligible(int n, int n2) throws RemoteException {
                Parcel parcel;
                boolean bl;
                Parcel parcel2;
                block5 : {
                    IBinder iBinder;
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel2.writeInt(n);
                        parcel2.writeInt(n2);
                        iBinder = this.mRemote;
                        bl = false;
                    }
                    catch (Throwable throwable) {
                        parcel.recycle();
                        parcel2.recycle();
                        throw throwable;
                    }
                    if (iBinder.transact(17, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().isWallpaperBackupEligible(n, n2);
                    parcel.recycle();
                    parcel2.recycle();
                    return bl;
                }
                parcel.readException();
                n = parcel.readInt();
                if (n != 0) {
                    bl = true;
                }
                parcel.recycle();
                parcel2.recycle();
                return bl;
            }

            @Override
            public boolean isWallpaperSupported(String string2) throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                boolean bl;
                block5 : {
                    IBinder iBinder;
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    try {
                        parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel.writeString(string2);
                        iBinder = this.mRemote;
                        bl = false;
                    }
                    catch (Throwable throwable) {
                        parcel2.recycle();
                        parcel.recycle();
                        throw throwable;
                    }
                    if (iBinder.transact(15, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().isWallpaperSupported(string2);
                    parcel2.recycle();
                    parcel.recycle();
                    return bl;
                }
                parcel2.readException();
                int n = parcel2.readInt();
                if (n != 0) {
                    bl = true;
                }
                parcel2.recycle();
                parcel.recycle();
                return bl;
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void registerWallpaperColorsCallback(IWallpaperManagerCallback iWallpaperManagerCallback, int n, int n2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iWallpaperManagerCallback != null ? iWallpaperManagerCallback.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(20, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().registerWallpaperColorsCallback(iWallpaperManagerCallback, n, n2);
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
            public void setDimensionHints(int n, int n2, String string2, int n3) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    parcel.writeString(string2);
                    parcel.writeInt(n3);
                    if (!this.mRemote.transact(9, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setDimensionHints(n, n2, string2, n3);
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
            public void setDisplayPadding(Rect rect, String string2, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (rect != null) {
                        parcel.writeInt(1);
                        rect.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeString(string2);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(12, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setDisplayPadding(rect, string2, n);
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
            public void setInAmbientMode(boolean bl, long l) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                int n = bl ? 1 : 0;
                try {
                    parcel.writeInt(n);
                    parcel.writeLong(l);
                    if (!this.mRemote.transact(22, parcel, null, 1) && Stub.getDefaultImpl() != null) {
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
            public boolean setLockWallpaperCallback(IWallpaperManagerCallback iWallpaperManagerCallback) throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                boolean bl;
                block9 : {
                    IBinder iBinder;
                    block8 : {
                        block7 : {
                            parcel = Parcel.obtain();
                            parcel2 = Parcel.obtain();
                            try {
                                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                                if (iWallpaperManagerCallback == null) break block7;
                            }
                            catch (Throwable throwable) {
                                parcel2.recycle();
                                parcel.recycle();
                                throw throwable;
                            }
                            iBinder = iWallpaperManagerCallback.asBinder();
                            break block8;
                        }
                        iBinder = null;
                    }
                    parcel.writeStrongBinder(iBinder);
                    iBinder = this.mRemote;
                    bl = false;
                    if (iBinder.transact(18, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block9;
                    bl = Stub.getDefaultImpl().setLockWallpaperCallback(iWallpaperManagerCallback);
                    parcel2.recycle();
                    parcel.recycle();
                    return bl;
                }
                parcel2.readException();
                int n = parcel2.readInt();
                if (n != 0) {
                    bl = true;
                }
                parcel2.recycle();
                parcel.recycle();
                return bl;
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
            public ParcelFileDescriptor setWallpaper(String object, String string2, Rect rect, boolean bl, Bundle bundle, int n, IWallpaperManagerCallback iWallpaperManagerCallback, int n2) throws RemoteException {
                Parcel parcel;
                void var1_7;
                Parcel parcel2;
                block15 : {
                    block14 : {
                        parcel2 = Parcel.obtain();
                        parcel = Parcel.obtain();
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        try {
                            parcel2.writeString((String)object);
                        }
                        catch (Throwable throwable) {
                            break block15;
                        }
                        try {
                            parcel2.writeString(string2);
                            if (rect != null) {
                                parcel2.writeInt(1);
                                rect.writeToParcel(parcel2, 0);
                            } else {
                                parcel2.writeInt(0);
                            }
                            int n3 = bl ? 1 : 0;
                            parcel2.writeInt(n3);
                        }
                        catch (Throwable throwable) {}
                        try {
                            parcel2.writeInt(n);
                            IBinder iBinder = iWallpaperManagerCallback != null ? iWallpaperManagerCallback.asBinder() : null;
                            parcel2.writeStrongBinder(iBinder);
                            parcel2.writeInt(n2);
                            if (!this.mRemote.transact(1, parcel2, parcel, 0) && Stub.getDefaultImpl() != null) {
                                object = Stub.getDefaultImpl().setWallpaper((String)object, string2, rect, bl, bundle, n, iWallpaperManagerCallback, n2);
                                parcel.recycle();
                                parcel2.recycle();
                                return object;
                            }
                            parcel.readException();
                            object = parcel.readInt() != 0 ? ParcelFileDescriptor.CREATOR.createFromParcel(parcel) : null;
                            n = parcel.readInt();
                            if (n == 0) break block14;
                        }
                        catch (Throwable throwable) {}
                        try {
                            bundle.readFromParcel(parcel);
                        }
                        catch (Throwable throwable) {
                            break block15;
                        }
                    }
                    parcel.recycle();
                    parcel2.recycle();
                    return object;
                    break block15;
                    catch (Throwable throwable) {
                        // empty catch block
                    }
                }
                parcel.recycle();
                parcel2.recycle();
                throw var1_7;
            }

            @Override
            public void setWallpaperComponent(ComponentName componentName) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (componentName != null) {
                        parcel.writeInt(1);
                        componentName.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(3, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setWallpaperComponent(componentName);
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
            public void setWallpaperComponentChecked(ComponentName componentName, String string2, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (componentName != null) {
                        parcel.writeInt(1);
                        componentName.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeString(string2);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(2, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setWallpaperComponentChecked(componentName, string2, n);
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
            public void settingsRestored() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(14, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().settingsRestored();
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
            public void unregisterWallpaperColorsCallback(IWallpaperManagerCallback iWallpaperManagerCallback, int n, int n2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iWallpaperManagerCallback != null ? iWallpaperManagerCallback.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(21, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().unregisterWallpaperColorsCallback(iWallpaperManagerCallback, n, n2);
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

