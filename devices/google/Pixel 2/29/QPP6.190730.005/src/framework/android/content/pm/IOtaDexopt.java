/*
 * Decompiled with CFR 0.145.
 */
package android.content.pm;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IOtaDexopt
extends IInterface {
    public void cleanup() throws RemoteException;

    public void dexoptNextPackage() throws RemoteException;

    public float getProgress() throws RemoteException;

    public boolean isDone() throws RemoteException;

    public String nextDexoptCommand() throws RemoteException;

    public void prepare() throws RemoteException;

    public static class Default
    implements IOtaDexopt {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void cleanup() throws RemoteException {
        }

        @Override
        public void dexoptNextPackage() throws RemoteException {
        }

        @Override
        public float getProgress() throws RemoteException {
            return 0.0f;
        }

        @Override
        public boolean isDone() throws RemoteException {
            return false;
        }

        @Override
        public String nextDexoptCommand() throws RemoteException {
            return null;
        }

        @Override
        public void prepare() throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IOtaDexopt {
        private static final String DESCRIPTOR = "android.content.pm.IOtaDexopt";
        static final int TRANSACTION_cleanup = 2;
        static final int TRANSACTION_dexoptNextPackage = 5;
        static final int TRANSACTION_getProgress = 4;
        static final int TRANSACTION_isDone = 3;
        static final int TRANSACTION_nextDexoptCommand = 6;
        static final int TRANSACTION_prepare = 1;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IOtaDexopt asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IOtaDexopt) {
                return (IOtaDexopt)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IOtaDexopt getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            switch (n) {
                default: {
                    return null;
                }
                case 6: {
                    return "nextDexoptCommand";
                }
                case 5: {
                    return "dexoptNextPackage";
                }
                case 4: {
                    return "getProgress";
                }
                case 3: {
                    return "isDone";
                }
                case 2: {
                    return "cleanup";
                }
                case 1: 
            }
            return "prepare";
        }

        public static boolean setDefaultImpl(IOtaDexopt iOtaDexopt) {
            if (Proxy.sDefaultImpl == null && iOtaDexopt != null) {
                Proxy.sDefaultImpl = iOtaDexopt;
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
                        object = this.nextDexoptCommand();
                        parcel.writeNoException();
                        parcel.writeString((String)object);
                        return true;
                    }
                    case 5: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.dexoptNextPackage();
                        parcel.writeNoException();
                        return true;
                    }
                    case 4: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        float f = this.getProgress();
                        parcel.writeNoException();
                        parcel.writeFloat(f);
                        return true;
                    }
                    case 3: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.isDone() ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 2: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.cleanup();
                        parcel.writeNoException();
                        return true;
                    }
                    case 1: 
                }
                ((Parcel)object).enforceInterface(DESCRIPTOR);
                this.prepare();
                parcel.writeNoException();
                return true;
            }
            parcel.writeString(DESCRIPTOR);
            return true;
        }

        private static class Proxy
        implements IOtaDexopt {
            public static IOtaDexopt sDefaultImpl;
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override
            public IBinder asBinder() {
                return this.mRemote;
            }

            @Override
            public void cleanup() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(2, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().cleanup();
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
            public void dexoptNextPackage() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(5, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().dexoptNextPackage();
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
            public float getProgress() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(4, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        float f = Stub.getDefaultImpl().getProgress();
                        return f;
                    }
                    parcel2.readException();
                    float f = parcel2.readFloat();
                    return f;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public boolean isDone() throws RemoteException {
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
                    bl = Stub.getDefaultImpl().isDone();
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
            public String nextDexoptCommand() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(6, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        String string2 = Stub.getDefaultImpl().nextDexoptCommand();
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

            @Override
            public void prepare() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(1, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().prepare();
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

