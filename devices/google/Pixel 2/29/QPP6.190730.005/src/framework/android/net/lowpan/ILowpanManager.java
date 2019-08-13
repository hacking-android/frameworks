/*
 * Decompiled with CFR 0.145.
 */
package android.net.lowpan;

import android.net.lowpan.ILowpanInterface;
import android.net.lowpan.ILowpanManagerListener;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface ILowpanManager
extends IInterface {
    public static final String LOWPAN_SERVICE_NAME = "lowpan";

    public void addInterface(ILowpanInterface var1) throws RemoteException;

    public void addListener(ILowpanManagerListener var1) throws RemoteException;

    public ILowpanInterface getInterface(String var1) throws RemoteException;

    public String[] getInterfaceList() throws RemoteException;

    public void removeInterface(ILowpanInterface var1) throws RemoteException;

    public void removeListener(ILowpanManagerListener var1) throws RemoteException;

    public static class Default
    implements ILowpanManager {
        @Override
        public void addInterface(ILowpanInterface iLowpanInterface) throws RemoteException {
        }

        @Override
        public void addListener(ILowpanManagerListener iLowpanManagerListener) throws RemoteException {
        }

        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public ILowpanInterface getInterface(String string2) throws RemoteException {
            return null;
        }

        @Override
        public String[] getInterfaceList() throws RemoteException {
            return null;
        }

        @Override
        public void removeInterface(ILowpanInterface iLowpanInterface) throws RemoteException {
        }

        @Override
        public void removeListener(ILowpanManagerListener iLowpanManagerListener) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements ILowpanManager {
        private static final String DESCRIPTOR = "android.net.lowpan.ILowpanManager";
        static final int TRANSACTION_addInterface = 5;
        static final int TRANSACTION_addListener = 3;
        static final int TRANSACTION_getInterface = 1;
        static final int TRANSACTION_getInterfaceList = 2;
        static final int TRANSACTION_removeInterface = 6;
        static final int TRANSACTION_removeListener = 4;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static ILowpanManager asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof ILowpanManager) {
                return (ILowpanManager)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static ILowpanManager getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            switch (n) {
                default: {
                    return null;
                }
                case 6: {
                    return "removeInterface";
                }
                case 5: {
                    return "addInterface";
                }
                case 4: {
                    return "removeListener";
                }
                case 3: {
                    return "addListener";
                }
                case 2: {
                    return "getInterfaceList";
                }
                case 1: 
            }
            return "getInterface";
        }

        public static boolean setDefaultImpl(ILowpanManager iLowpanManager) {
            if (Proxy.sDefaultImpl == null && iLowpanManager != null) {
                Proxy.sDefaultImpl = iLowpanManager;
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
                        object.enforceInterface(DESCRIPTOR);
                        this.removeInterface(ILowpanInterface.Stub.asInterface(object.readStrongBinder()));
                        parcel.writeNoException();
                        return true;
                    }
                    case 5: {
                        object.enforceInterface(DESCRIPTOR);
                        this.addInterface(ILowpanInterface.Stub.asInterface(object.readStrongBinder()));
                        parcel.writeNoException();
                        return true;
                    }
                    case 4: {
                        object.enforceInterface(DESCRIPTOR);
                        this.removeListener(ILowpanManagerListener.Stub.asInterface(object.readStrongBinder()));
                        parcel.writeNoException();
                        return true;
                    }
                    case 3: {
                        object.enforceInterface(DESCRIPTOR);
                        this.addListener(ILowpanManagerListener.Stub.asInterface(object.readStrongBinder()));
                        parcel.writeNoException();
                        return true;
                    }
                    case 2: {
                        object.enforceInterface(DESCRIPTOR);
                        object = this.getInterfaceList();
                        parcel.writeNoException();
                        parcel.writeStringArray((String[])object);
                        return true;
                    }
                    case 1: 
                }
                object.enforceInterface(DESCRIPTOR);
                object = this.getInterface(object.readString());
                parcel.writeNoException();
                object = object != null ? object.asBinder() : null;
                parcel.writeStrongBinder((IBinder)object);
                return true;
            }
            parcel.writeString(DESCRIPTOR);
            return true;
        }

        private static class Proxy
        implements ILowpanManager {
            public static ILowpanManager sDefaultImpl;
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
            public void addInterface(ILowpanInterface iLowpanInterface) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iLowpanInterface != null ? iLowpanInterface.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (!this.mRemote.transact(5, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().addInterface(iLowpanInterface);
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
            public void addListener(ILowpanManagerListener iLowpanManagerListener) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iLowpanManagerListener != null ? iLowpanManagerListener.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (!this.mRemote.transact(3, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().addListener(iLowpanManagerListener);
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
            public ILowpanInterface getInterface(String object) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString((String)object);
                    if (!this.mRemote.transact(1, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        object = Stub.getDefaultImpl().getInterface((String)object);
                        return object;
                    }
                    parcel2.readException();
                    object = ILowpanInterface.Stub.asInterface(parcel2.readStrongBinder());
                    return object;
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
            public String[] getInterfaceList() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(2, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        String[] arrstring = Stub.getDefaultImpl().getInterfaceList();
                        return arrstring;
                    }
                    parcel2.readException();
                    String[] arrstring = parcel2.createStringArray();
                    return arrstring;
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
            public void removeInterface(ILowpanInterface iLowpanInterface) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iLowpanInterface != null ? iLowpanInterface.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (!this.mRemote.transact(6, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().removeInterface(iLowpanInterface);
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
            public void removeListener(ILowpanManagerListener iLowpanManagerListener) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iLowpanManagerListener != null ? iLowpanManagerListener.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (!this.mRemote.transact(4, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().removeListener(iLowpanManagerListener);
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

