/*
 * Decompiled with CFR 0.145.
 */
package android.telephony.ims.aidl;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IImsCapabilityCallback
extends IInterface {
    public void onCapabilitiesStatusChanged(int var1) throws RemoteException;

    public void onChangeCapabilityConfigurationError(int var1, int var2, int var3) throws RemoteException;

    public void onQueryCapabilityConfiguration(int var1, int var2, boolean var3) throws RemoteException;

    public static class Default
    implements IImsCapabilityCallback {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void onCapabilitiesStatusChanged(int n) throws RemoteException {
        }

        @Override
        public void onChangeCapabilityConfigurationError(int n, int n2, int n3) throws RemoteException {
        }

        @Override
        public void onQueryCapabilityConfiguration(int n, int n2, boolean bl) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IImsCapabilityCallback {
        private static final String DESCRIPTOR = "android.telephony.ims.aidl.IImsCapabilityCallback";
        static final int TRANSACTION_onCapabilitiesStatusChanged = 3;
        static final int TRANSACTION_onChangeCapabilityConfigurationError = 2;
        static final int TRANSACTION_onQueryCapabilityConfiguration = 1;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IImsCapabilityCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IImsCapabilityCallback) {
                return (IImsCapabilityCallback)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IImsCapabilityCallback getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            if (n != 1) {
                if (n != 2) {
                    if (n != 3) {
                        return null;
                    }
                    return "onCapabilitiesStatusChanged";
                }
                return "onChangeCapabilityConfigurationError";
            }
            return "onQueryCapabilityConfiguration";
        }

        public static boolean setDefaultImpl(IImsCapabilityCallback iImsCapabilityCallback) {
            if (Proxy.sDefaultImpl == null && iImsCapabilityCallback != null) {
                Proxy.sDefaultImpl = iImsCapabilityCallback;
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
                    this.onCapabilitiesStatusChanged(parcel.readInt());
                    return true;
                }
                parcel.enforceInterface(DESCRIPTOR);
                this.onChangeCapabilityConfigurationError(parcel.readInt(), parcel.readInt(), parcel.readInt());
                return true;
            }
            parcel.enforceInterface(DESCRIPTOR);
            n2 = parcel.readInt();
            n = parcel.readInt();
            boolean bl = parcel.readInt() != 0;
            this.onQueryCapabilityConfiguration(n2, n, bl);
            return true;
        }

        private static class Proxy
        implements IImsCapabilityCallback {
            public static IImsCapabilityCallback sDefaultImpl;
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
            public void onCapabilitiesStatusChanged(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(3, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onCapabilitiesStatusChanged(n);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onChangeCapabilityConfigurationError(int n, int n2, int n3) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    parcel.writeInt(n3);
                    if (!this.mRemote.transact(2, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onChangeCapabilityConfigurationError(n, n2, n3);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onQueryCapabilityConfiguration(int n, int n2, boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                parcel.writeInt(n);
                parcel.writeInt(n2);
                int n3 = bl ? 1 : 0;
                try {
                    parcel.writeInt(n3);
                    if (!this.mRemote.transact(1, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onQueryCapabilityConfiguration(n, n2, bl);
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

