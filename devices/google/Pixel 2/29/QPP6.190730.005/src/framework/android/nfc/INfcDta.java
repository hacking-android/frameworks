/*
 * Decompiled with CFR 0.145.
 */
package android.nfc;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface INfcDta
extends IInterface {
    public void disableClient() throws RemoteException;

    public void disableDta() throws RemoteException;

    public void disableServer() throws RemoteException;

    public boolean enableClient(String var1, int var2, int var3, int var4) throws RemoteException;

    public void enableDta() throws RemoteException;

    public boolean enableServer(String var1, int var2, int var3, int var4, int var5) throws RemoteException;

    public boolean registerMessageService(String var1) throws RemoteException;

    public static class Default
    implements INfcDta {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void disableClient() throws RemoteException {
        }

        @Override
        public void disableDta() throws RemoteException {
        }

        @Override
        public void disableServer() throws RemoteException {
        }

        @Override
        public boolean enableClient(String string2, int n, int n2, int n3) throws RemoteException {
            return false;
        }

        @Override
        public void enableDta() throws RemoteException {
        }

        @Override
        public boolean enableServer(String string2, int n, int n2, int n3, int n4) throws RemoteException {
            return false;
        }

        @Override
        public boolean registerMessageService(String string2) throws RemoteException {
            return false;
        }
    }

    public static abstract class Stub
    extends Binder
    implements INfcDta {
        private static final String DESCRIPTOR = "android.nfc.INfcDta";
        static final int TRANSACTION_disableClient = 6;
        static final int TRANSACTION_disableDta = 2;
        static final int TRANSACTION_disableServer = 4;
        static final int TRANSACTION_enableClient = 5;
        static final int TRANSACTION_enableDta = 1;
        static final int TRANSACTION_enableServer = 3;
        static final int TRANSACTION_registerMessageService = 7;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static INfcDta asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof INfcDta) {
                return (INfcDta)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static INfcDta getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            switch (n) {
                default: {
                    return null;
                }
                case 7: {
                    return "registerMessageService";
                }
                case 6: {
                    return "disableClient";
                }
                case 5: {
                    return "enableClient";
                }
                case 4: {
                    return "disableServer";
                }
                case 3: {
                    return "enableServer";
                }
                case 2: {
                    return "disableDta";
                }
                case 1: 
            }
            return "enableDta";
        }

        public static boolean setDefaultImpl(INfcDta iNfcDta) {
            if (Proxy.sDefaultImpl == null && iNfcDta != null) {
                Proxy.sDefaultImpl = iNfcDta;
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
                        n = this.registerMessageService(parcel.readString()) ? 1 : 0;
                        parcel2.writeNoException();
                        parcel2.writeInt(n);
                        return true;
                    }
                    case 6: {
                        parcel.enforceInterface(DESCRIPTOR);
                        this.disableClient();
                        parcel2.writeNoException();
                        return true;
                    }
                    case 5: {
                        parcel.enforceInterface(DESCRIPTOR);
                        n = this.enableClient(parcel.readString(), parcel.readInt(), parcel.readInt(), parcel.readInt()) ? 1 : 0;
                        parcel2.writeNoException();
                        parcel2.writeInt(n);
                        return true;
                    }
                    case 4: {
                        parcel.enforceInterface(DESCRIPTOR);
                        this.disableServer();
                        parcel2.writeNoException();
                        return true;
                    }
                    case 3: {
                        parcel.enforceInterface(DESCRIPTOR);
                        n = this.enableServer(parcel.readString(), parcel.readInt(), parcel.readInt(), parcel.readInt(), parcel.readInt()) ? 1 : 0;
                        parcel2.writeNoException();
                        parcel2.writeInt(n);
                        return true;
                    }
                    case 2: {
                        parcel.enforceInterface(DESCRIPTOR);
                        this.disableDta();
                        parcel2.writeNoException();
                        return true;
                    }
                    case 1: 
                }
                parcel.enforceInterface(DESCRIPTOR);
                this.enableDta();
                parcel2.writeNoException();
                return true;
            }
            parcel2.writeString(DESCRIPTOR);
            return true;
        }

        private static class Proxy
        implements INfcDta {
            public static INfcDta sDefaultImpl;
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override
            public IBinder asBinder() {
                return this.mRemote;
            }

            @Override
            public void disableClient() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(6, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().disableClient();
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
            public void disableDta() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(2, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().disableDta();
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
            public void disableServer() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(4, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().disableServer();
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
            public boolean enableClient(String string2, int n, int n2, int n3) throws RemoteException {
                boolean bl;
                Parcel parcel;
                Parcel parcel2;
                block5 : {
                    IBinder iBinder;
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel2.writeString(string2);
                        parcel2.writeInt(n);
                        parcel2.writeInt(n2);
                        parcel2.writeInt(n3);
                        iBinder = this.mRemote;
                        bl = false;
                    }
                    catch (Throwable throwable) {
                        parcel.recycle();
                        parcel2.recycle();
                        throw throwable;
                    }
                    if (iBinder.transact(5, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().enableClient(string2, n, n2, n3);
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
            public void enableDta() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(1, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().enableDta();
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
             * Loose catch block
             * WARNING - void declaration
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             * Lifted jumps to return sites
             */
            @Override
            public boolean enableServer(String string2, int n, int n2, int n3, int n4) throws RemoteException {
                Parcel parcel;
                void var1_9;
                Parcel parcel2;
                block17 : {
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                    try {
                        parcel2.writeString(string2);
                    }
                    catch (Throwable throwable) {
                        break block17;
                    }
                    try {
                        parcel2.writeInt(n);
                    }
                    catch (Throwable throwable) {
                        break block17;
                    }
                    try {
                        parcel2.writeInt(n2);
                    }
                    catch (Throwable throwable) {
                        break block17;
                    }
                    try {
                        parcel2.writeInt(n3);
                    }
                    catch (Throwable throwable) {
                        break block17;
                    }
                    try {
                        parcel2.writeInt(n4);
                    }
                    catch (Throwable throwable) {
                        break block17;
                    }
                    try {
                        IBinder iBinder = this.mRemote;
                        boolean bl = false;
                        if (!iBinder.transact(3, parcel2, parcel, 0) && Stub.getDefaultImpl() != null) {
                            bl = Stub.getDefaultImpl().enableServer(string2, n, n2, n3, n4);
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
                    catch (Throwable throwable) {}
                    break block17;
                    catch (Throwable throwable) {
                        // empty catch block
                    }
                }
                parcel.recycle();
                parcel2.recycle();
                throw var1_9;
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            @Override
            public boolean registerMessageService(String string2) throws RemoteException {
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
                    if (iBinder.transact(7, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().registerMessageService(string2);
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

