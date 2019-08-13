/*
 * Decompiled with CFR 0.145.
 */
package android.media.tv;

import android.annotation.UnsupportedAppUsage;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface ITvRemoteServiceInput
extends IInterface {
    @UnsupportedAppUsage
    public void clearInputBridge(IBinder var1) throws RemoteException;

    @UnsupportedAppUsage
    public void closeInputBridge(IBinder var1) throws RemoteException;

    @UnsupportedAppUsage
    public void openInputBridge(IBinder var1, String var2, int var3, int var4, int var5) throws RemoteException;

    @UnsupportedAppUsage
    public void sendKeyDown(IBinder var1, int var2) throws RemoteException;

    @UnsupportedAppUsage
    public void sendKeyUp(IBinder var1, int var2) throws RemoteException;

    @UnsupportedAppUsage
    public void sendPointerDown(IBinder var1, int var2, int var3, int var4) throws RemoteException;

    @UnsupportedAppUsage
    public void sendPointerSync(IBinder var1) throws RemoteException;

    @UnsupportedAppUsage
    public void sendPointerUp(IBinder var1, int var2) throws RemoteException;

    @UnsupportedAppUsage
    public void sendTimestamp(IBinder var1, long var2) throws RemoteException;

    public static class Default
    implements ITvRemoteServiceInput {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void clearInputBridge(IBinder iBinder) throws RemoteException {
        }

        @Override
        public void closeInputBridge(IBinder iBinder) throws RemoteException {
        }

        @Override
        public void openInputBridge(IBinder iBinder, String string2, int n, int n2, int n3) throws RemoteException {
        }

        @Override
        public void sendKeyDown(IBinder iBinder, int n) throws RemoteException {
        }

        @Override
        public void sendKeyUp(IBinder iBinder, int n) throws RemoteException {
        }

        @Override
        public void sendPointerDown(IBinder iBinder, int n, int n2, int n3) throws RemoteException {
        }

        @Override
        public void sendPointerSync(IBinder iBinder) throws RemoteException {
        }

        @Override
        public void sendPointerUp(IBinder iBinder, int n) throws RemoteException {
        }

        @Override
        public void sendTimestamp(IBinder iBinder, long l) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements ITvRemoteServiceInput {
        private static final String DESCRIPTOR = "android.media.tv.ITvRemoteServiceInput";
        static final int TRANSACTION_clearInputBridge = 3;
        static final int TRANSACTION_closeInputBridge = 2;
        static final int TRANSACTION_openInputBridge = 1;
        static final int TRANSACTION_sendKeyDown = 5;
        static final int TRANSACTION_sendKeyUp = 6;
        static final int TRANSACTION_sendPointerDown = 7;
        static final int TRANSACTION_sendPointerSync = 9;
        static final int TRANSACTION_sendPointerUp = 8;
        static final int TRANSACTION_sendTimestamp = 4;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static ITvRemoteServiceInput asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof ITvRemoteServiceInput) {
                return (ITvRemoteServiceInput)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static ITvRemoteServiceInput getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            switch (n) {
                default: {
                    return null;
                }
                case 9: {
                    return "sendPointerSync";
                }
                case 8: {
                    return "sendPointerUp";
                }
                case 7: {
                    return "sendPointerDown";
                }
                case 6: {
                    return "sendKeyUp";
                }
                case 5: {
                    return "sendKeyDown";
                }
                case 4: {
                    return "sendTimestamp";
                }
                case 3: {
                    return "clearInputBridge";
                }
                case 2: {
                    return "closeInputBridge";
                }
                case 1: 
            }
            return "openInputBridge";
        }

        public static boolean setDefaultImpl(ITvRemoteServiceInput iTvRemoteServiceInput) {
            if (Proxy.sDefaultImpl == null && iTvRemoteServiceInput != null) {
                Proxy.sDefaultImpl = iTvRemoteServiceInput;
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
                    case 9: {
                        parcel.enforceInterface(DESCRIPTOR);
                        this.sendPointerSync(parcel.readStrongBinder());
                        return true;
                    }
                    case 8: {
                        parcel.enforceInterface(DESCRIPTOR);
                        this.sendPointerUp(parcel.readStrongBinder(), parcel.readInt());
                        return true;
                    }
                    case 7: {
                        parcel.enforceInterface(DESCRIPTOR);
                        this.sendPointerDown(parcel.readStrongBinder(), parcel.readInt(), parcel.readInt(), parcel.readInt());
                        return true;
                    }
                    case 6: {
                        parcel.enforceInterface(DESCRIPTOR);
                        this.sendKeyUp(parcel.readStrongBinder(), parcel.readInt());
                        return true;
                    }
                    case 5: {
                        parcel.enforceInterface(DESCRIPTOR);
                        this.sendKeyDown(parcel.readStrongBinder(), parcel.readInt());
                        return true;
                    }
                    case 4: {
                        parcel.enforceInterface(DESCRIPTOR);
                        this.sendTimestamp(parcel.readStrongBinder(), parcel.readLong());
                        return true;
                    }
                    case 3: {
                        parcel.enforceInterface(DESCRIPTOR);
                        this.clearInputBridge(parcel.readStrongBinder());
                        return true;
                    }
                    case 2: {
                        parcel.enforceInterface(DESCRIPTOR);
                        this.closeInputBridge(parcel.readStrongBinder());
                        return true;
                    }
                    case 1: 
                }
                parcel.enforceInterface(DESCRIPTOR);
                this.openInputBridge(parcel.readStrongBinder(), parcel.readString(), parcel.readInt(), parcel.readInt(), parcel.readInt());
                return true;
            }
            parcel2.writeString(DESCRIPTOR);
            return true;
        }

        private static class Proxy
        implements ITvRemoteServiceInput {
            public static ITvRemoteServiceInput sDefaultImpl;
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override
            public IBinder asBinder() {
                return this.mRemote;
            }

            @Override
            public void clearInputBridge(IBinder iBinder) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeStrongBinder(iBinder);
                    if (!this.mRemote.transact(3, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().clearInputBridge(iBinder);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void closeInputBridge(IBinder iBinder) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeStrongBinder(iBinder);
                    if (!this.mRemote.transact(2, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().closeInputBridge(iBinder);
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
            public void openInputBridge(IBinder iBinder, String string2, int n, int n2, int n3) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeStrongBinder(iBinder);
                    parcel.writeString(string2);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    parcel.writeInt(n3);
                    if (!this.mRemote.transact(1, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().openInputBridge(iBinder, string2, n, n2, n3);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void sendKeyDown(IBinder iBinder, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeStrongBinder(iBinder);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(5, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().sendKeyDown(iBinder, n);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void sendKeyUp(IBinder iBinder, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeStrongBinder(iBinder);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(6, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().sendKeyUp(iBinder, n);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void sendPointerDown(IBinder iBinder, int n, int n2, int n3) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeStrongBinder(iBinder);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    parcel.writeInt(n3);
                    if (!this.mRemote.transact(7, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().sendPointerDown(iBinder, n, n2, n3);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void sendPointerSync(IBinder iBinder) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeStrongBinder(iBinder);
                    if (!this.mRemote.transact(9, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().sendPointerSync(iBinder);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void sendPointerUp(IBinder iBinder, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeStrongBinder(iBinder);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(8, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().sendPointerUp(iBinder, n);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void sendTimestamp(IBinder iBinder, long l) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeStrongBinder(iBinder);
                    parcel.writeLong(l);
                    if (!this.mRemote.transact(4, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().sendTimestamp(iBinder, l);
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

