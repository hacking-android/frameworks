/*
 * Decompiled with CFR 0.145.
 */
package android.nfc;

import android.annotation.UnsupportedAppUsage;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;

public interface INfcAdapterExtras
extends IInterface {
    @UnsupportedAppUsage
    public void authenticate(String var1, byte[] var2) throws RemoteException;

    @UnsupportedAppUsage
    public Bundle close(String var1, IBinder var2) throws RemoteException;

    @UnsupportedAppUsage
    public int getCardEmulationRoute(String var1) throws RemoteException;

    @UnsupportedAppUsage
    public String getDriverName(String var1) throws RemoteException;

    @UnsupportedAppUsage
    public Bundle open(String var1, IBinder var2) throws RemoteException;

    @UnsupportedAppUsage
    public void setCardEmulationRoute(String var1, int var2) throws RemoteException;

    @UnsupportedAppUsage
    public Bundle transceive(String var1, byte[] var2) throws RemoteException;

    public static class Default
    implements INfcAdapterExtras {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void authenticate(String string2, byte[] arrby) throws RemoteException {
        }

        @Override
        public Bundle close(String string2, IBinder iBinder) throws RemoteException {
            return null;
        }

        @Override
        public int getCardEmulationRoute(String string2) throws RemoteException {
            return 0;
        }

        @Override
        public String getDriverName(String string2) throws RemoteException {
            return null;
        }

        @Override
        public Bundle open(String string2, IBinder iBinder) throws RemoteException {
            return null;
        }

        @Override
        public void setCardEmulationRoute(String string2, int n) throws RemoteException {
        }

        @Override
        public Bundle transceive(String string2, byte[] arrby) throws RemoteException {
            return null;
        }
    }

    public static abstract class Stub
    extends Binder
    implements INfcAdapterExtras {
        private static final String DESCRIPTOR = "android.nfc.INfcAdapterExtras";
        static final int TRANSACTION_authenticate = 6;
        static final int TRANSACTION_close = 2;
        static final int TRANSACTION_getCardEmulationRoute = 4;
        static final int TRANSACTION_getDriverName = 7;
        static final int TRANSACTION_open = 1;
        static final int TRANSACTION_setCardEmulationRoute = 5;
        static final int TRANSACTION_transceive = 3;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static INfcAdapterExtras asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof INfcAdapterExtras) {
                return (INfcAdapterExtras)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static INfcAdapterExtras getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            switch (n) {
                default: {
                    return null;
                }
                case 7: {
                    return "getDriverName";
                }
                case 6: {
                    return "authenticate";
                }
                case 5: {
                    return "setCardEmulationRoute";
                }
                case 4: {
                    return "getCardEmulationRoute";
                }
                case 3: {
                    return "transceive";
                }
                case 2: {
                    return "close";
                }
                case 1: 
            }
            return "open";
        }

        public static boolean setDefaultImpl(INfcAdapterExtras iNfcAdapterExtras) {
            if (Proxy.sDefaultImpl == null && iNfcAdapterExtras != null) {
                Proxy.sDefaultImpl = iNfcAdapterExtras;
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
                    case 7: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getDriverName(((Parcel)object).readString());
                        parcel.writeNoException();
                        parcel.writeString((String)object);
                        return true;
                    }
                    case 6: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.authenticate(((Parcel)object).readString(), ((Parcel)object).createByteArray());
                        parcel.writeNoException();
                        return true;
                    }
                    case 5: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.setCardEmulationRoute(((Parcel)object).readString(), ((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 4: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.getCardEmulationRoute(((Parcel)object).readString());
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 3: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.transceive(((Parcel)object).readString(), ((Parcel)object).createByteArray());
                        parcel.writeNoException();
                        if (object != null) {
                            parcel.writeInt(1);
                            ((Bundle)object).writeToParcel(parcel, 1);
                        } else {
                            parcel.writeInt(0);
                        }
                        return true;
                    }
                    case 2: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.close(((Parcel)object).readString(), ((Parcel)object).readStrongBinder());
                        parcel.writeNoException();
                        if (object != null) {
                            parcel.writeInt(1);
                            ((Bundle)object).writeToParcel(parcel, 1);
                        } else {
                            parcel.writeInt(0);
                        }
                        return true;
                    }
                    case 1: 
                }
                ((Parcel)object).enforceInterface(DESCRIPTOR);
                object = this.open(((Parcel)object).readString(), ((Parcel)object).readStrongBinder());
                parcel.writeNoException();
                if (object != null) {
                    parcel.writeInt(1);
                    ((Bundle)object).writeToParcel(parcel, 1);
                } else {
                    parcel.writeInt(0);
                }
                return true;
            }
            parcel.writeString(DESCRIPTOR);
            return true;
        }

        private static class Proxy
        implements INfcAdapterExtras {
            public static INfcAdapterExtras sDefaultImpl;
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override
            public IBinder asBinder() {
                return this.mRemote;
            }

            @Override
            public void authenticate(String string2, byte[] arrby) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    parcel.writeByteArray(arrby);
                    if (!this.mRemote.transact(6, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().authenticate(string2, arrby);
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
            public Bundle close(String object, IBinder iBinder) throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                block3 : {
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel2.writeString((String)object);
                        parcel2.writeStrongBinder(iBinder);
                        if (this.mRemote.transact(2, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block3;
                        object = Stub.getDefaultImpl().close((String)object, iBinder);
                        parcel.recycle();
                        parcel2.recycle();
                        return object;
                    }
                    catch (Throwable throwable) {
                        parcel.recycle();
                        parcel2.recycle();
                        throw throwable;
                    }
                }
                parcel.readException();
                object = parcel.readInt() != 0 ? Bundle.CREATOR.createFromParcel(parcel) : null;
                parcel.recycle();
                parcel2.recycle();
                return object;
            }

            @Override
            public int getCardEmulationRoute(String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(4, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        int n = Stub.getDefaultImpl().getCardEmulationRoute(string2);
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
            public String getDriverName(String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(7, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        string2 = Stub.getDefaultImpl().getDriverName(string2);
                        return string2;
                    }
                    parcel2.readException();
                    string2 = parcel2.readString();
                    return string2;
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
            public Bundle open(String object, IBinder iBinder) throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                block3 : {
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel2.writeString((String)object);
                        parcel2.writeStrongBinder(iBinder);
                        if (this.mRemote.transact(1, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block3;
                        object = Stub.getDefaultImpl().open((String)object, iBinder);
                        parcel.recycle();
                        parcel2.recycle();
                        return object;
                    }
                    catch (Throwable throwable) {
                        parcel.recycle();
                        parcel2.recycle();
                        throw throwable;
                    }
                }
                parcel.readException();
                object = parcel.readInt() != 0 ? Bundle.CREATOR.createFromParcel(parcel) : null;
                parcel.recycle();
                parcel2.recycle();
                return object;
            }

            @Override
            public void setCardEmulationRoute(String string2, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(5, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setCardEmulationRoute(string2, n);
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
            public Bundle transceive(String object, byte[] arrby) throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                block3 : {
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel2.writeString((String)object);
                        parcel2.writeByteArray(arrby);
                        if (this.mRemote.transact(3, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block3;
                        object = Stub.getDefaultImpl().transceive((String)object, arrby);
                        parcel.recycle();
                        parcel2.recycle();
                        return object;
                    }
                    catch (Throwable throwable) {
                        parcel.recycle();
                        parcel2.recycle();
                        throw throwable;
                    }
                }
                parcel.readException();
                object = parcel.readInt() != 0 ? Bundle.CREATOR.createFromParcel(parcel) : null;
                parcel.recycle();
                parcel2.recycle();
                return object;
            }
        }

    }

}

