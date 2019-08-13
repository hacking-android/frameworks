/*
 * Decompiled with CFR 0.145.
 */
package android.view.accessibility;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IAccessibilityManagerClient
extends IInterface {
    public void notifyServicesStateChanged(long var1) throws RemoteException;

    public void setRelevantEventTypes(int var1) throws RemoteException;

    public void setState(int var1) throws RemoteException;

    public static class Default
    implements IAccessibilityManagerClient {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void notifyServicesStateChanged(long l) throws RemoteException {
        }

        @Override
        public void setRelevantEventTypes(int n) throws RemoteException {
        }

        @Override
        public void setState(int n) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IAccessibilityManagerClient {
        private static final String DESCRIPTOR = "android.view.accessibility.IAccessibilityManagerClient";
        static final int TRANSACTION_notifyServicesStateChanged = 2;
        static final int TRANSACTION_setRelevantEventTypes = 3;
        static final int TRANSACTION_setState = 1;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IAccessibilityManagerClient asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IAccessibilityManagerClient) {
                return (IAccessibilityManagerClient)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IAccessibilityManagerClient getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            if (n != 1) {
                if (n != 2) {
                    if (n != 3) {
                        return null;
                    }
                    return "setRelevantEventTypes";
                }
                return "notifyServicesStateChanged";
            }
            return "setState";
        }

        public static boolean setDefaultImpl(IAccessibilityManagerClient iAccessibilityManagerClient) {
            if (Proxy.sDefaultImpl == null && iAccessibilityManagerClient != null) {
                Proxy.sDefaultImpl = iAccessibilityManagerClient;
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
            if (n != 1) {
                if (n != 2) {
                    if (n != 3) {
                        if (n != 1598968902) {
                            return super.onTransact(n, parcel, parcel2, n2);
                        }
                        parcel2.writeString(DESCRIPTOR);
                        return true;
                    }
                    parcel.enforceInterface(DESCRIPTOR);
                    this.setRelevantEventTypes(parcel.readInt());
                    return true;
                }
                parcel.enforceInterface(DESCRIPTOR);
                this.notifyServicesStateChanged(parcel.readLong());
                return true;
            }
            parcel.enforceInterface(DESCRIPTOR);
            this.setState(parcel.readInt());
            return true;
        }

        private static class Proxy
        implements IAccessibilityManagerClient {
            public static IAccessibilityManagerClient sDefaultImpl;
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            @Override
            public void notifyServicesStateChanged(long l) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeLong(l);
                    if (!this.mRemote.transact(2, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().notifyServicesStateChanged(l);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void setRelevantEventTypes(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(3, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setRelevantEventTypes(n);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void setState(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(1, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setState(n);
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

