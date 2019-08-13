/*
 * Decompiled with CFR 0.145.
 */
package android.app;

import android.content.pm.ParceledListSlice;
import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;

public interface IUriGrantsManager
extends IInterface {
    public void clearGrantedUriPermissions(String var1, int var2) throws RemoteException;

    public ParceledListSlice getGrantedUriPermissions(String var1, int var2) throws RemoteException;

    public ParceledListSlice getUriPermissions(String var1, boolean var2, boolean var3) throws RemoteException;

    public void grantUriPermissionFromOwner(IBinder var1, int var2, String var3, Uri var4, int var5, int var6, int var7) throws RemoteException;

    public void releasePersistableUriPermission(Uri var1, int var2, String var3, int var4) throws RemoteException;

    public void takePersistableUriPermission(Uri var1, int var2, String var3, int var4) throws RemoteException;

    public static class Default
    implements IUriGrantsManager {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void clearGrantedUriPermissions(String string2, int n) throws RemoteException {
        }

        @Override
        public ParceledListSlice getGrantedUriPermissions(String string2, int n) throws RemoteException {
            return null;
        }

        @Override
        public ParceledListSlice getUriPermissions(String string2, boolean bl, boolean bl2) throws RemoteException {
            return null;
        }

        @Override
        public void grantUriPermissionFromOwner(IBinder iBinder, int n, String string2, Uri uri, int n2, int n3, int n4) throws RemoteException {
        }

        @Override
        public void releasePersistableUriPermission(Uri uri, int n, String string2, int n2) throws RemoteException {
        }

        @Override
        public void takePersistableUriPermission(Uri uri, int n, String string2, int n2) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IUriGrantsManager {
        private static final String DESCRIPTOR = "android.app.IUriGrantsManager";
        static final int TRANSACTION_clearGrantedUriPermissions = 5;
        static final int TRANSACTION_getGrantedUriPermissions = 4;
        static final int TRANSACTION_getUriPermissions = 6;
        static final int TRANSACTION_grantUriPermissionFromOwner = 3;
        static final int TRANSACTION_releasePersistableUriPermission = 2;
        static final int TRANSACTION_takePersistableUriPermission = 1;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IUriGrantsManager asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IUriGrantsManager) {
                return (IUriGrantsManager)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IUriGrantsManager getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            switch (n) {
                default: {
                    return null;
                }
                case 6: {
                    return "getUriPermissions";
                }
                case 5: {
                    return "clearGrantedUriPermissions";
                }
                case 4: {
                    return "getGrantedUriPermissions";
                }
                case 3: {
                    return "grantUriPermissionFromOwner";
                }
                case 2: {
                    return "releasePersistableUriPermission";
                }
                case 1: 
            }
            return "takePersistableUriPermission";
        }

        public static boolean setDefaultImpl(IUriGrantsManager iUriGrantsManager) {
            if (Proxy.sDefaultImpl == null && iUriGrantsManager != null) {
                Proxy.sDefaultImpl = iUriGrantsManager;
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
                switch (n) {
                    default: {
                        return super.onTransact(n, (Parcel)object, parcel, n2);
                    }
                    case 6: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        String string2 = ((Parcel)object).readString();
                        boolean bl = ((Parcel)object).readInt() != 0;
                        boolean bl2 = ((Parcel)object).readInt() != 0;
                        object = this.getUriPermissions(string2, bl, bl2);
                        parcel.writeNoException();
                        if (object != null) {
                            parcel.writeInt(1);
                            ((ParceledListSlice)object).writeToParcel(parcel, 1);
                        } else {
                            parcel.writeInt(0);
                        }
                        return true;
                    }
                    case 5: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.clearGrantedUriPermissions(((Parcel)object).readString(), ((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 4: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getGrantedUriPermissions(((Parcel)object).readString(), ((Parcel)object).readInt());
                        parcel.writeNoException();
                        if (object != null) {
                            parcel.writeInt(1);
                            ((ParceledListSlice)object).writeToParcel(parcel, 1);
                        } else {
                            parcel.writeInt(0);
                        }
                        return true;
                    }
                    case 3: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        IBinder iBinder = ((Parcel)object).readStrongBinder();
                        n = ((Parcel)object).readInt();
                        String string3 = ((Parcel)object).readString();
                        Uri uri = ((Parcel)object).readInt() != 0 ? Uri.CREATOR.createFromParcel((Parcel)object) : null;
                        this.grantUriPermissionFromOwner(iBinder, n, string3, uri, ((Parcel)object).readInt(), ((Parcel)object).readInt(), ((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 2: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        Uri uri = ((Parcel)object).readInt() != 0 ? Uri.CREATOR.createFromParcel((Parcel)object) : null;
                        this.releasePersistableUriPermission(uri, ((Parcel)object).readInt(), ((Parcel)object).readString(), ((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 1: 
                }
                ((Parcel)object).enforceInterface(DESCRIPTOR);
                Uri uri = ((Parcel)object).readInt() != 0 ? Uri.CREATOR.createFromParcel((Parcel)object) : null;
                this.takePersistableUriPermission(uri, ((Parcel)object).readInt(), ((Parcel)object).readString(), ((Parcel)object).readInt());
                parcel.writeNoException();
                return true;
            }
            parcel.writeString(DESCRIPTOR);
            return true;
        }

        private static class Proxy
        implements IUriGrantsManager {
            public static IUriGrantsManager sDefaultImpl;
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override
            public IBinder asBinder() {
                return this.mRemote;
            }

            @Override
            public void clearGrantedUriPermissions(String string2, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(5, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().clearGrantedUriPermissions(string2, n);
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
            public ParceledListSlice getGrantedUriPermissions(String parceledListSlice, int n) throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                block3 : {
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel2.writeString((String)((Object)parceledListSlice));
                        parcel2.writeInt(n);
                        if (this.mRemote.transact(4, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block3;
                        parceledListSlice = Stub.getDefaultImpl().getGrantedUriPermissions((String)((Object)parceledListSlice), n);
                        parcel.recycle();
                        parcel2.recycle();
                        return parceledListSlice;
                    }
                    catch (Throwable throwable) {
                        parcel.recycle();
                        parcel2.recycle();
                        throw throwable;
                    }
                }
                parcel.readException();
                parceledListSlice = parcel.readInt() != 0 ? (ParceledListSlice)ParceledListSlice.CREATOR.createFromParcel(parcel) : null;
                parcel.recycle();
                parcel2.recycle();
                return parceledListSlice;
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            @Override
            public ParceledListSlice getUriPermissions(String parceledListSlice, boolean bl, boolean bl2) throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                block5 : {
                    int n;
                    int n2;
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    try {
                        parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel.writeString((String)((Object)parceledListSlice));
                        n2 = 1;
                        n = bl ? 1 : 0;
                    }
                    catch (Throwable throwable) {
                        parcel2.recycle();
                        parcel.recycle();
                        throw throwable;
                    }
                    parcel.writeInt(n);
                    n = bl2 ? n2 : 0;
                    parcel.writeInt(n);
                    if (this.mRemote.transact(6, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block5;
                    parceledListSlice = Stub.getDefaultImpl().getUriPermissions((String)((Object)parceledListSlice), bl, bl2);
                    parcel2.recycle();
                    parcel.recycle();
                    return parceledListSlice;
                }
                parcel2.readException();
                parceledListSlice = parcel2.readInt() != 0 ? (ParceledListSlice)ParceledListSlice.CREATOR.createFromParcel(parcel2) : null;
                parcel2.recycle();
                parcel.recycle();
                return parceledListSlice;
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
            public void grantUriPermissionFromOwner(IBinder iBinder, int n, String string2, Uri uri, int n2, int n3, int n4) throws RemoteException {
                Parcel parcel;
                void var1_7;
                Parcel parcel2;
                block14 : {
                    block13 : {
                        parcel = Parcel.obtain();
                        parcel2 = Parcel.obtain();
                        parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                        try {
                            parcel.writeStrongBinder(iBinder);
                        }
                        catch (Throwable throwable) {
                            break block14;
                        }
                        try {
                            parcel.writeInt(n);
                        }
                        catch (Throwable throwable) {
                            break block14;
                        }
                        try {
                            parcel.writeString(string2);
                            if (uri != null) {
                                parcel.writeInt(1);
                                uri.writeToParcel(parcel, 0);
                                break block13;
                            }
                            parcel.writeInt(0);
                        }
                        catch (Throwable throwable) {}
                    }
                    try {
                        parcel.writeInt(n2);
                        parcel.writeInt(n3);
                        parcel.writeInt(n4);
                        if (!this.mRemote.transact(3, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                            Stub.getDefaultImpl().grantUriPermissionFromOwner(iBinder, n, string2, uri, n2, n3, n4);
                            parcel2.recycle();
                            parcel.recycle();
                            return;
                        }
                        parcel2.readException();
                        parcel2.recycle();
                        parcel.recycle();
                        return;
                    }
                    catch (Throwable throwable) {}
                    break block14;
                    catch (Throwable throwable) {
                        // empty catch block
                    }
                }
                parcel2.recycle();
                parcel.recycle();
                throw var1_7;
            }

            @Override
            public void releasePersistableUriPermission(Uri uri, int n, String string2, int n2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (uri != null) {
                        parcel.writeInt(1);
                        uri.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeInt(n);
                    parcel.writeString(string2);
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(2, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().releasePersistableUriPermission(uri, n, string2, n2);
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
            public void takePersistableUriPermission(Uri uri, int n, String string2, int n2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (uri != null) {
                        parcel.writeInt(1);
                        uri.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeInt(n);
                    parcel.writeString(string2);
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(1, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().takePersistableUriPermission(uri, n, string2, n2);
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

