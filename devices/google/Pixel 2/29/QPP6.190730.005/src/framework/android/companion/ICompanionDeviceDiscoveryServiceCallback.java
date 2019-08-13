/*
 * Decompiled with CFR 0.145.
 */
package android.companion;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface ICompanionDeviceDiscoveryServiceCallback
extends IInterface {
    public void onDeviceSelected(String var1, int var2, String var3) throws RemoteException;

    public void onDeviceSelectionCancel() throws RemoteException;

    public static class Default
    implements ICompanionDeviceDiscoveryServiceCallback {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void onDeviceSelected(String string2, int n, String string3) throws RemoteException {
        }

        @Override
        public void onDeviceSelectionCancel() throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements ICompanionDeviceDiscoveryServiceCallback {
        private static final String DESCRIPTOR = "android.companion.ICompanionDeviceDiscoveryServiceCallback";
        static final int TRANSACTION_onDeviceSelected = 1;
        static final int TRANSACTION_onDeviceSelectionCancel = 2;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static ICompanionDeviceDiscoveryServiceCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof ICompanionDeviceDiscoveryServiceCallback) {
                return (ICompanionDeviceDiscoveryServiceCallback)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static ICompanionDeviceDiscoveryServiceCallback getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            if (n != 1) {
                if (n != 2) {
                    return null;
                }
                return "onDeviceSelectionCancel";
            }
            return "onDeviceSelected";
        }

        public static boolean setDefaultImpl(ICompanionDeviceDiscoveryServiceCallback iCompanionDeviceDiscoveryServiceCallback) {
            if (Proxy.sDefaultImpl == null && iCompanionDeviceDiscoveryServiceCallback != null) {
                Proxy.sDefaultImpl = iCompanionDeviceDiscoveryServiceCallback;
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
                    if (n != 1598968902) {
                        return super.onTransact(n, parcel, parcel2, n2);
                    }
                    parcel2.writeString(DESCRIPTOR);
                    return true;
                }
                parcel.enforceInterface(DESCRIPTOR);
                this.onDeviceSelectionCancel();
                return true;
            }
            parcel.enforceInterface(DESCRIPTOR);
            this.onDeviceSelected(parcel.readString(), parcel.readInt(), parcel.readString());
            return true;
        }

        private static class Proxy
        implements ICompanionDeviceDiscoveryServiceCallback {
            public static ICompanionDeviceDiscoveryServiceCallback sDefaultImpl;
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
            public void onDeviceSelected(String string2, int n, String string3) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    parcel.writeInt(n);
                    parcel.writeString(string3);
                    if (!this.mRemote.transact(1, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onDeviceSelected(string2, n, string3);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onDeviceSelectionCancel() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(2, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onDeviceSelectionCancel();
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

