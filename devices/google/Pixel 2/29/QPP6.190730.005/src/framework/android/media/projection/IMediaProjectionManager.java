/*
 * Decompiled with CFR 0.145.
 */
package android.media.projection;

import android.annotation.UnsupportedAppUsage;
import android.media.projection.IMediaProjection;
import android.media.projection.IMediaProjectionWatcherCallback;
import android.media.projection.MediaProjectionInfo;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;

public interface IMediaProjectionManager
extends IInterface {
    public void addCallback(IMediaProjectionWatcherCallback var1) throws RemoteException;

    public IMediaProjection createProjection(int var1, String var2, int var3, boolean var4) throws RemoteException;

    public MediaProjectionInfo getActiveProjectionInfo() throws RemoteException;

    @UnsupportedAppUsage
    public boolean hasProjectionPermission(int var1, String var2) throws RemoteException;

    public boolean isValidMediaProjection(IMediaProjection var1) throws RemoteException;

    public void removeCallback(IMediaProjectionWatcherCallback var1) throws RemoteException;

    public void stopActiveProjection() throws RemoteException;

    public static class Default
    implements IMediaProjectionManager {
        @Override
        public void addCallback(IMediaProjectionWatcherCallback iMediaProjectionWatcherCallback) throws RemoteException {
        }

        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public IMediaProjection createProjection(int n, String string2, int n2, boolean bl) throws RemoteException {
            return null;
        }

        @Override
        public MediaProjectionInfo getActiveProjectionInfo() throws RemoteException {
            return null;
        }

        @Override
        public boolean hasProjectionPermission(int n, String string2) throws RemoteException {
            return false;
        }

        @Override
        public boolean isValidMediaProjection(IMediaProjection iMediaProjection) throws RemoteException {
            return false;
        }

        @Override
        public void removeCallback(IMediaProjectionWatcherCallback iMediaProjectionWatcherCallback) throws RemoteException {
        }

        @Override
        public void stopActiveProjection() throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IMediaProjectionManager {
        private static final String DESCRIPTOR = "android.media.projection.IMediaProjectionManager";
        static final int TRANSACTION_addCallback = 6;
        static final int TRANSACTION_createProjection = 2;
        static final int TRANSACTION_getActiveProjectionInfo = 4;
        static final int TRANSACTION_hasProjectionPermission = 1;
        static final int TRANSACTION_isValidMediaProjection = 3;
        static final int TRANSACTION_removeCallback = 7;
        static final int TRANSACTION_stopActiveProjection = 5;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IMediaProjectionManager asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IMediaProjectionManager) {
                return (IMediaProjectionManager)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IMediaProjectionManager getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            switch (n) {
                default: {
                    return null;
                }
                case 7: {
                    return "removeCallback";
                }
                case 6: {
                    return "addCallback";
                }
                case 5: {
                    return "stopActiveProjection";
                }
                case 4: {
                    return "getActiveProjectionInfo";
                }
                case 3: {
                    return "isValidMediaProjection";
                }
                case 2: {
                    return "createProjection";
                }
                case 1: 
            }
            return "hasProjectionPermission";
        }

        public static boolean setDefaultImpl(IMediaProjectionManager iMediaProjectionManager) {
            if (Proxy.sDefaultImpl == null && iMediaProjectionManager != null) {
                Proxy.sDefaultImpl = iMediaProjectionManager;
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
                    case 7: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.removeCallback(IMediaProjectionWatcherCallback.Stub.asInterface(((Parcel)object).readStrongBinder()));
                        parcel.writeNoException();
                        return true;
                    }
                    case 6: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.addCallback(IMediaProjectionWatcherCallback.Stub.asInterface(((Parcel)object).readStrongBinder()));
                        parcel.writeNoException();
                        return true;
                    }
                    case 5: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.stopActiveProjection();
                        parcel.writeNoException();
                        return true;
                    }
                    case 4: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getActiveProjectionInfo();
                        parcel.writeNoException();
                        if (object != null) {
                            parcel.writeInt(1);
                            ((MediaProjectionInfo)object).writeToParcel(parcel, 1);
                        } else {
                            parcel.writeInt(0);
                        }
                        return true;
                    }
                    case 3: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.isValidMediaProjection(IMediaProjection.Stub.asInterface(((Parcel)object).readStrongBinder())) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 2: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = ((Parcel)object).readInt();
                        String string2 = ((Parcel)object).readString();
                        n2 = ((Parcel)object).readInt();
                        if (((Parcel)object).readInt() != 0) {
                            bl = true;
                        }
                        object = this.createProjection(n, string2, n2, bl);
                        parcel.writeNoException();
                        object = object != null ? object.asBinder() : null;
                        parcel.writeStrongBinder((IBinder)object);
                        return true;
                    }
                    case 1: 
                }
                ((Parcel)object).enforceInterface(DESCRIPTOR);
                n = this.hasProjectionPermission(((Parcel)object).readInt(), ((Parcel)object).readString()) ? 1 : 0;
                parcel.writeNoException();
                parcel.writeInt(n);
                return true;
            }
            parcel.writeString(DESCRIPTOR);
            return true;
        }

        private static class Proxy
        implements IMediaProjectionManager {
            public static IMediaProjectionManager sDefaultImpl;
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void addCallback(IMediaProjectionWatcherCallback iMediaProjectionWatcherCallback) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iMediaProjectionWatcherCallback != null ? iMediaProjectionWatcherCallback.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (!this.mRemote.transact(6, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().addCallback(iMediaProjectionWatcherCallback);
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
            public IBinder asBinder() {
                return this.mRemote;
            }

            @Override
            public IMediaProjection createProjection(int n, String object, int n2, boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                parcel.writeInt(n);
                parcel.writeString((String)object);
                parcel.writeInt(n2);
                int n3 = bl ? 1 : 0;
                try {
                    parcel.writeInt(n3);
                    if (!this.mRemote.transact(2, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        object = Stub.getDefaultImpl().createProjection(n, (String)object, n2, bl);
                        return object;
                    }
                    parcel2.readException();
                    object = IMediaProjection.Stub.asInterface(parcel2.readStrongBinder());
                    return object;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public MediaProjectionInfo getActiveProjectionInfo() throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                block3 : {
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        if (this.mRemote.transact(4, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block3;
                        MediaProjectionInfo mediaProjectionInfo = Stub.getDefaultImpl().getActiveProjectionInfo();
                        parcel.recycle();
                        parcel2.recycle();
                        return mediaProjectionInfo;
                    }
                    catch (Throwable throwable) {
                        parcel.recycle();
                        parcel2.recycle();
                        throw throwable;
                    }
                }
                parcel.readException();
                MediaProjectionInfo mediaProjectionInfo = parcel.readInt() != 0 ? MediaProjectionInfo.CREATOR.createFromParcel(parcel) : null;
                parcel.recycle();
                parcel2.recycle();
                return mediaProjectionInfo;
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            @Override
            public boolean hasProjectionPermission(int n, String string2) throws RemoteException {
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
                        parcel2.writeString(string2);
                        iBinder = this.mRemote;
                        bl = false;
                    }
                    catch (Throwable throwable) {
                        parcel.recycle();
                        parcel2.recycle();
                        throw throwable;
                    }
                    if (iBinder.transact(1, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().hasProjectionPermission(n, string2);
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
            public boolean isValidMediaProjection(IMediaProjection iMediaProjection) throws RemoteException {
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
                                if (iMediaProjection == null) break block7;
                            }
                            catch (Throwable throwable) {
                                parcel2.recycle();
                                parcel.recycle();
                                throw throwable;
                            }
                            iBinder = iMediaProjection.asBinder();
                            break block8;
                        }
                        iBinder = null;
                    }
                    parcel.writeStrongBinder(iBinder);
                    iBinder = this.mRemote;
                    bl = false;
                    if (iBinder.transact(3, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block9;
                    bl = Stub.getDefaultImpl().isValidMediaProjection(iMediaProjection);
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
            public void removeCallback(IMediaProjectionWatcherCallback iMediaProjectionWatcherCallback) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iMediaProjectionWatcherCallback != null ? iMediaProjectionWatcherCallback.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (!this.mRemote.transact(7, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().removeCallback(iMediaProjectionWatcherCallback);
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
            public void stopActiveProjection() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(5, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().stopActiveProjection();
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

