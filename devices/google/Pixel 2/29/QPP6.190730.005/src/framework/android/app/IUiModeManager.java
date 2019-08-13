/*
 * Decompiled with CFR 0.145.
 */
package android.app;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IUiModeManager
extends IInterface {
    public void disableCarMode(int var1) throws RemoteException;

    public void enableCarMode(int var1) throws RemoteException;

    public int getCurrentModeType() throws RemoteException;

    public int getNightMode() throws RemoteException;

    public boolean isNightModeLocked() throws RemoteException;

    public boolean isUiModeLocked() throws RemoteException;

    public void setNightMode(int var1) throws RemoteException;

    public static class Default
    implements IUiModeManager {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void disableCarMode(int n) throws RemoteException {
        }

        @Override
        public void enableCarMode(int n) throws RemoteException {
        }

        @Override
        public int getCurrentModeType() throws RemoteException {
            return 0;
        }

        @Override
        public int getNightMode() throws RemoteException {
            return 0;
        }

        @Override
        public boolean isNightModeLocked() throws RemoteException {
            return false;
        }

        @Override
        public boolean isUiModeLocked() throws RemoteException {
            return false;
        }

        @Override
        public void setNightMode(int n) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IUiModeManager {
        private static final String DESCRIPTOR = "android.app.IUiModeManager";
        static final int TRANSACTION_disableCarMode = 2;
        static final int TRANSACTION_enableCarMode = 1;
        static final int TRANSACTION_getCurrentModeType = 3;
        static final int TRANSACTION_getNightMode = 5;
        static final int TRANSACTION_isNightModeLocked = 7;
        static final int TRANSACTION_isUiModeLocked = 6;
        static final int TRANSACTION_setNightMode = 4;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IUiModeManager asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IUiModeManager) {
                return (IUiModeManager)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IUiModeManager getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            switch (n) {
                default: {
                    return null;
                }
                case 7: {
                    return "isNightModeLocked";
                }
                case 6: {
                    return "isUiModeLocked";
                }
                case 5: {
                    return "getNightMode";
                }
                case 4: {
                    return "setNightMode";
                }
                case 3: {
                    return "getCurrentModeType";
                }
                case 2: {
                    return "disableCarMode";
                }
                case 1: 
            }
            return "enableCarMode";
        }

        public static boolean setDefaultImpl(IUiModeManager iUiModeManager) {
            if (Proxy.sDefaultImpl == null && iUiModeManager != null) {
                Proxy.sDefaultImpl = iUiModeManager;
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
                    case 7: {
                        parcel.enforceInterface(DESCRIPTOR);
                        n = this.isNightModeLocked() ? 1 : 0;
                        parcel2.writeNoException();
                        parcel2.writeInt(n);
                        return true;
                    }
                    case 6: {
                        parcel.enforceInterface(DESCRIPTOR);
                        n = this.isUiModeLocked() ? 1 : 0;
                        parcel2.writeNoException();
                        parcel2.writeInt(n);
                        return true;
                    }
                    case 5: {
                        parcel.enforceInterface(DESCRIPTOR);
                        n = this.getNightMode();
                        parcel2.writeNoException();
                        parcel2.writeInt(n);
                        return true;
                    }
                    case 4: {
                        parcel.enforceInterface(DESCRIPTOR);
                        this.setNightMode(parcel.readInt());
                        parcel2.writeNoException();
                        return true;
                    }
                    case 3: {
                        parcel.enforceInterface(DESCRIPTOR);
                        n = this.getCurrentModeType();
                        parcel2.writeNoException();
                        parcel2.writeInt(n);
                        return true;
                    }
                    case 2: {
                        parcel.enforceInterface(DESCRIPTOR);
                        this.disableCarMode(parcel.readInt());
                        parcel2.writeNoException();
                        return true;
                    }
                    case 1: 
                }
                parcel.enforceInterface(DESCRIPTOR);
                this.enableCarMode(parcel.readInt());
                parcel2.writeNoException();
                return true;
            }
            parcel2.writeString(DESCRIPTOR);
            return true;
        }

        private static class Proxy
        implements IUiModeManager {
            public static IUiModeManager sDefaultImpl;
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override
            public IBinder asBinder() {
                return this.mRemote;
            }

            @Override
            public void disableCarMode(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(2, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().disableCarMode(n);
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
            public void enableCarMode(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(1, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().enableCarMode(n);
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
            public int getCurrentModeType() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(3, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        int n = Stub.getDefaultImpl().getCurrentModeType();
                        return n;
                    }
                    parcel2.readException();
                    int n = parcel2.readInt();
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
            public int getNightMode() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(5, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        int n = Stub.getDefaultImpl().getNightMode();
                        return n;
                    }
                    parcel2.readException();
                    int n = parcel2.readInt();
                    return n;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public boolean isNightModeLocked() throws RemoteException {
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
                    bl = Stub.getDefaultImpl().isNightModeLocked();
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
            public boolean isUiModeLocked() throws RemoteException {
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
                    bl = Stub.getDefaultImpl().isUiModeLocked();
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
            public void setNightMode(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(4, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setNightMode(n);
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

