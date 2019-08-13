/*
 * Decompiled with CFR 0.145.
 */
package android.os.image;

import android.gsi.GsiProgress;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;

public interface IDynamicSystemService
extends IInterface {
    public boolean abort() throws RemoteException;

    public boolean commit() throws RemoteException;

    public GsiProgress getInstallationProgress() throws RemoteException;

    public boolean isEnabled() throws RemoteException;

    public boolean isInUse() throws RemoteException;

    public boolean isInstalled() throws RemoteException;

    public boolean remove() throws RemoteException;

    public boolean setEnable(boolean var1) throws RemoteException;

    public boolean startInstallation(long var1, long var3) throws RemoteException;

    public boolean write(byte[] var1) throws RemoteException;

    public static class Default
    implements IDynamicSystemService {
        @Override
        public boolean abort() throws RemoteException {
            return false;
        }

        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public boolean commit() throws RemoteException {
            return false;
        }

        @Override
        public GsiProgress getInstallationProgress() throws RemoteException {
            return null;
        }

        @Override
        public boolean isEnabled() throws RemoteException {
            return false;
        }

        @Override
        public boolean isInUse() throws RemoteException {
            return false;
        }

        @Override
        public boolean isInstalled() throws RemoteException {
            return false;
        }

        @Override
        public boolean remove() throws RemoteException {
            return false;
        }

        @Override
        public boolean setEnable(boolean bl) throws RemoteException {
            return false;
        }

        @Override
        public boolean startInstallation(long l, long l2) throws RemoteException {
            return false;
        }

        @Override
        public boolean write(byte[] arrby) throws RemoteException {
            return false;
        }
    }

    public static abstract class Stub
    extends Binder
    implements IDynamicSystemService {
        private static final String DESCRIPTOR = "android.os.image.IDynamicSystemService";
        static final int TRANSACTION_abort = 3;
        static final int TRANSACTION_commit = 10;
        static final int TRANSACTION_getInstallationProgress = 2;
        static final int TRANSACTION_isEnabled = 6;
        static final int TRANSACTION_isInUse = 4;
        static final int TRANSACTION_isInstalled = 5;
        static final int TRANSACTION_remove = 7;
        static final int TRANSACTION_setEnable = 8;
        static final int TRANSACTION_startInstallation = 1;
        static final int TRANSACTION_write = 9;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IDynamicSystemService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IDynamicSystemService) {
                return (IDynamicSystemService)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IDynamicSystemService getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            switch (n) {
                default: {
                    return null;
                }
                case 10: {
                    return "commit";
                }
                case 9: {
                    return "write";
                }
                case 8: {
                    return "setEnable";
                }
                case 7: {
                    return "remove";
                }
                case 6: {
                    return "isEnabled";
                }
                case 5: {
                    return "isInstalled";
                }
                case 4: {
                    return "isInUse";
                }
                case 3: {
                    return "abort";
                }
                case 2: {
                    return "getInstallationProgress";
                }
                case 1: 
            }
            return "startInstallation";
        }

        public static boolean setDefaultImpl(IDynamicSystemService iDynamicSystemService) {
            if (Proxy.sDefaultImpl == null && iDynamicSystemService != null) {
                Proxy.sDefaultImpl = iDynamicSystemService;
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
                    case 10: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.commit() ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 9: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.write(((Parcel)object).createByteArray()) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 8: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        if (((Parcel)object).readInt() != 0) {
                            bl = true;
                        }
                        n = this.setEnable(bl) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 7: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.remove() ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 6: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.isEnabled() ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 5: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.isInstalled() ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 4: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.isInUse() ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 3: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.abort() ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 2: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getInstallationProgress();
                        parcel.writeNoException();
                        if (object != null) {
                            parcel.writeInt(1);
                            ((GsiProgress)object).writeToParcel(parcel, 1);
                        } else {
                            parcel.writeInt(0);
                        }
                        return true;
                    }
                    case 1: 
                }
                ((Parcel)object).enforceInterface(DESCRIPTOR);
                n = this.startInstallation(((Parcel)object).readLong(), ((Parcel)object).readLong()) ? 1 : 0;
                parcel.writeNoException();
                parcel.writeInt(n);
                return true;
            }
            parcel.writeString(DESCRIPTOR);
            return true;
        }

        private static class Proxy
        implements IDynamicSystemService {
            public static IDynamicSystemService sDefaultImpl;
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override
            public boolean abort() throws RemoteException {
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
                    bl = Stub.getDefaultImpl().abort();
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
            public IBinder asBinder() {
                return this.mRemote;
            }

            @Override
            public boolean commit() throws RemoteException {
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
                    if (iBinder.transact(10, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().commit();
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
            public GsiProgress getInstallationProgress() throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                block3 : {
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        if (this.mRemote.transact(2, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block3;
                        GsiProgress gsiProgress = Stub.getDefaultImpl().getInstallationProgress();
                        parcel.recycle();
                        parcel2.recycle();
                        return gsiProgress;
                    }
                    catch (Throwable throwable) {
                        parcel.recycle();
                        parcel2.recycle();
                        throw throwable;
                    }
                }
                parcel.readException();
                GsiProgress gsiProgress = parcel.readInt() != 0 ? GsiProgress.CREATOR.createFromParcel(parcel) : null;
                parcel.recycle();
                parcel2.recycle();
                return gsiProgress;
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            @Override
            public boolean isEnabled() throws RemoteException {
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
                    if (iBinder.transact(6, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().isEnabled();
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
            public boolean isInUse() throws RemoteException {
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
                    bl = Stub.getDefaultImpl().isInUse();
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
            public boolean isInstalled() throws RemoteException {
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
                    bl = Stub.getDefaultImpl().isInstalled();
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
            public boolean remove() throws RemoteException {
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
                    if (iBinder.transact(7, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().remove();
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
            public boolean setEnable(boolean bl) throws RemoteException {
                boolean bl2;
                int n;
                Parcel parcel;
                Parcel parcel2;
                block4 : {
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    try {
                        parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                        bl2 = true;
                        n = bl ? 1 : 0;
                    }
                    catch (Throwable throwable) {
                        parcel2.recycle();
                        parcel.recycle();
                        throw throwable;
                    }
                    parcel.writeInt(n);
                    if (this.mRemote.transact(8, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block4;
                    bl = Stub.getDefaultImpl().setEnable(bl);
                    parcel2.recycle();
                    parcel.recycle();
                    return bl;
                }
                parcel2.readException();
                n = parcel2.readInt();
                bl = n != 0 ? bl2 : false;
                parcel2.recycle();
                parcel.recycle();
                return bl;
            }

            @Override
            public boolean startInstallation(long l, long l2) throws RemoteException {
                boolean bl;
                Parcel parcel;
                Parcel parcel2;
                block5 : {
                    IBinder iBinder;
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    try {
                        parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel.writeLong(l);
                        parcel.writeLong(l2);
                        iBinder = this.mRemote;
                        bl = false;
                    }
                    catch (Throwable throwable) {
                        parcel2.recycle();
                        parcel.recycle();
                        throw throwable;
                    }
                    if (iBinder.transact(1, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().startInstallation(l, l2);
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
            public boolean write(byte[] arrby) throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                boolean bl;
                block5 : {
                    IBinder iBinder;
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    try {
                        parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel.writeByteArray(arrby);
                        iBinder = this.mRemote;
                        bl = false;
                    }
                    catch (Throwable throwable) {
                        parcel2.recycle();
                        parcel.recycle();
                        throw throwable;
                    }
                    if (iBinder.transact(9, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().write(arrby);
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
        }

    }

}

