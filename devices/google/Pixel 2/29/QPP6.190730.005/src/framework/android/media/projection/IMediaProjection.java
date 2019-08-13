/*
 * Decompiled with CFR 0.145.
 */
package android.media.projection;

import android.media.projection.IMediaProjectionCallback;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IMediaProjection
extends IInterface {
    public int applyVirtualDisplayFlags(int var1) throws RemoteException;

    public boolean canProjectAudio() throws RemoteException;

    public boolean canProjectSecureVideo() throws RemoteException;

    public boolean canProjectVideo() throws RemoteException;

    public void registerCallback(IMediaProjectionCallback var1) throws RemoteException;

    public void start(IMediaProjectionCallback var1) throws RemoteException;

    public void stop() throws RemoteException;

    public void unregisterCallback(IMediaProjectionCallback var1) throws RemoteException;

    public static class Default
    implements IMediaProjection {
        @Override
        public int applyVirtualDisplayFlags(int n) throws RemoteException {
            return 0;
        }

        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public boolean canProjectAudio() throws RemoteException {
            return false;
        }

        @Override
        public boolean canProjectSecureVideo() throws RemoteException {
            return false;
        }

        @Override
        public boolean canProjectVideo() throws RemoteException {
            return false;
        }

        @Override
        public void registerCallback(IMediaProjectionCallback iMediaProjectionCallback) throws RemoteException {
        }

        @Override
        public void start(IMediaProjectionCallback iMediaProjectionCallback) throws RemoteException {
        }

        @Override
        public void stop() throws RemoteException {
        }

        @Override
        public void unregisterCallback(IMediaProjectionCallback iMediaProjectionCallback) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IMediaProjection {
        private static final String DESCRIPTOR = "android.media.projection.IMediaProjection";
        static final int TRANSACTION_applyVirtualDisplayFlags = 6;
        static final int TRANSACTION_canProjectAudio = 3;
        static final int TRANSACTION_canProjectSecureVideo = 5;
        static final int TRANSACTION_canProjectVideo = 4;
        static final int TRANSACTION_registerCallback = 7;
        static final int TRANSACTION_start = 1;
        static final int TRANSACTION_stop = 2;
        static final int TRANSACTION_unregisterCallback = 8;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IMediaProjection asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IMediaProjection) {
                return (IMediaProjection)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IMediaProjection getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            switch (n) {
                default: {
                    return null;
                }
                case 8: {
                    return "unregisterCallback";
                }
                case 7: {
                    return "registerCallback";
                }
                case 6: {
                    return "applyVirtualDisplayFlags";
                }
                case 5: {
                    return "canProjectSecureVideo";
                }
                case 4: {
                    return "canProjectVideo";
                }
                case 3: {
                    return "canProjectAudio";
                }
                case 2: {
                    return "stop";
                }
                case 1: 
            }
            return "start";
        }

        public static boolean setDefaultImpl(IMediaProjection iMediaProjection) {
            if (Proxy.sDefaultImpl == null && iMediaProjection != null) {
                Proxy.sDefaultImpl = iMediaProjection;
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
            if (n != 1598968902) {
                switch (n) {
                    default: {
                        return super.onTransact(n, parcel, parcel2, n2);
                    }
                    case 8: {
                        parcel.enforceInterface(DESCRIPTOR);
                        this.unregisterCallback(IMediaProjectionCallback.Stub.asInterface(parcel.readStrongBinder()));
                        parcel2.writeNoException();
                        return true;
                    }
                    case 7: {
                        parcel.enforceInterface(DESCRIPTOR);
                        this.registerCallback(IMediaProjectionCallback.Stub.asInterface(parcel.readStrongBinder()));
                        parcel2.writeNoException();
                        return true;
                    }
                    case 6: {
                        parcel.enforceInterface(DESCRIPTOR);
                        n = this.applyVirtualDisplayFlags(parcel.readInt());
                        parcel2.writeNoException();
                        parcel2.writeInt(n);
                        return true;
                    }
                    case 5: {
                        parcel.enforceInterface(DESCRIPTOR);
                        n = this.canProjectSecureVideo() ? 1 : 0;
                        parcel2.writeNoException();
                        parcel2.writeInt(n);
                        return true;
                    }
                    case 4: {
                        parcel.enforceInterface(DESCRIPTOR);
                        n = this.canProjectVideo() ? 1 : 0;
                        parcel2.writeNoException();
                        parcel2.writeInt(n);
                        return true;
                    }
                    case 3: {
                        parcel.enforceInterface(DESCRIPTOR);
                        n = this.canProjectAudio() ? 1 : 0;
                        parcel2.writeNoException();
                        parcel2.writeInt(n);
                        return true;
                    }
                    case 2: {
                        parcel.enforceInterface(DESCRIPTOR);
                        this.stop();
                        parcel2.writeNoException();
                        return true;
                    }
                    case 1: 
                }
                parcel.enforceInterface(DESCRIPTOR);
                this.start(IMediaProjectionCallback.Stub.asInterface(parcel.readStrongBinder()));
                parcel2.writeNoException();
                return true;
            }
            parcel2.writeString(DESCRIPTOR);
            return true;
        }

        private static class Proxy
        implements IMediaProjection {
            public static IMediaProjection sDefaultImpl;
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override
            public int applyVirtualDisplayFlags(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(6, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        n = Stub.getDefaultImpl().applyVirtualDisplayFlags(n);
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
            public IBinder asBinder() {
                return this.mRemote;
            }

            @Override
            public boolean canProjectAudio() throws RemoteException {
                boolean bl;
                Parcel parcel;
                Parcel parcel2;
                block5 : {
                    IBinder iBinder;
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        iBinder = this.mRemote;
                        bl = false;
                    }
                    catch (Throwable throwable) {
                        parcel.recycle();
                        parcel2.recycle();
                        throw throwable;
                    }
                    if (iBinder.transact(3, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().canProjectAudio();
                    parcel.recycle();
                    parcel2.recycle();
                    return bl;
                }
                parcel.readException();
                int n = parcel.readInt();
                if (n != 0) {
                    bl = true;
                }
                parcel.recycle();
                parcel2.recycle();
                return bl;
            }

            @Override
            public boolean canProjectSecureVideo() throws RemoteException {
                boolean bl;
                Parcel parcel;
                Parcel parcel2;
                block5 : {
                    IBinder iBinder;
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        iBinder = this.mRemote;
                        bl = false;
                    }
                    catch (Throwable throwable) {
                        parcel.recycle();
                        parcel2.recycle();
                        throw throwable;
                    }
                    if (iBinder.transact(5, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().canProjectSecureVideo();
                    parcel.recycle();
                    parcel2.recycle();
                    return bl;
                }
                parcel.readException();
                int n = parcel.readInt();
                if (n != 0) {
                    bl = true;
                }
                parcel.recycle();
                parcel2.recycle();
                return bl;
            }

            @Override
            public boolean canProjectVideo() throws RemoteException {
                boolean bl;
                Parcel parcel;
                Parcel parcel2;
                block5 : {
                    IBinder iBinder;
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        iBinder = this.mRemote;
                        bl = false;
                    }
                    catch (Throwable throwable) {
                        parcel.recycle();
                        parcel2.recycle();
                        throw throwable;
                    }
                    if (iBinder.transact(4, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().canProjectVideo();
                    parcel.recycle();
                    parcel2.recycle();
                    return bl;
                }
                parcel.readException();
                int n = parcel.readInt();
                if (n != 0) {
                    bl = true;
                }
                parcel.recycle();
                parcel2.recycle();
                return bl;
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void registerCallback(IMediaProjectionCallback iMediaProjectionCallback) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iMediaProjectionCallback != null ? iMediaProjectionCallback.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (!this.mRemote.transact(7, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().registerCallback(iMediaProjectionCallback);
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
            public void start(IMediaProjectionCallback iMediaProjectionCallback) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iMediaProjectionCallback != null ? iMediaProjectionCallback.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (!this.mRemote.transact(1, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().start(iMediaProjectionCallback);
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
            public void stop() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(2, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().stop();
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
            public void unregisterCallback(IMediaProjectionCallback iMediaProjectionCallback) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iMediaProjectionCallback != null ? iMediaProjectionCallback.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (!this.mRemote.transact(8, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().unregisterCallback(iMediaProjectionCallback);
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

