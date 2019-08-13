/*
 * Decompiled with CFR 0.145.
 */
package android.net.wifi;

import android.net.wifi.INetworkRequestUserSelectionCallback;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import java.util.ArrayList;
import java.util.List;

public interface INetworkRequestMatchCallback
extends IInterface {
    public void onAbort() throws RemoteException;

    public void onMatch(List<ScanResult> var1) throws RemoteException;

    public void onUserSelectionCallbackRegistration(INetworkRequestUserSelectionCallback var1) throws RemoteException;

    public void onUserSelectionConnectFailure(WifiConfiguration var1) throws RemoteException;

    public void onUserSelectionConnectSuccess(WifiConfiguration var1) throws RemoteException;

    public static class Default
    implements INetworkRequestMatchCallback {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void onAbort() throws RemoteException {
        }

        @Override
        public void onMatch(List<ScanResult> list) throws RemoteException {
        }

        @Override
        public void onUserSelectionCallbackRegistration(INetworkRequestUserSelectionCallback iNetworkRequestUserSelectionCallback) throws RemoteException {
        }

        @Override
        public void onUserSelectionConnectFailure(WifiConfiguration wifiConfiguration) throws RemoteException {
        }

        @Override
        public void onUserSelectionConnectSuccess(WifiConfiguration wifiConfiguration) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements INetworkRequestMatchCallback {
        private static final String DESCRIPTOR = "android.net.wifi.INetworkRequestMatchCallback";
        static final int TRANSACTION_onAbort = 2;
        static final int TRANSACTION_onMatch = 3;
        static final int TRANSACTION_onUserSelectionCallbackRegistration = 1;
        static final int TRANSACTION_onUserSelectionConnectFailure = 5;
        static final int TRANSACTION_onUserSelectionConnectSuccess = 4;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static INetworkRequestMatchCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof INetworkRequestMatchCallback) {
                return (INetworkRequestMatchCallback)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static INetworkRequestMatchCallback getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            if (n != 1) {
                if (n != 2) {
                    if (n != 3) {
                        if (n != 4) {
                            if (n != 5) {
                                return null;
                            }
                            return "onUserSelectionConnectFailure";
                        }
                        return "onUserSelectionConnectSuccess";
                    }
                    return "onMatch";
                }
                return "onAbort";
            }
            return "onUserSelectionCallbackRegistration";
        }

        public static boolean setDefaultImpl(INetworkRequestMatchCallback iNetworkRequestMatchCallback) {
            if (Proxy.sDefaultImpl == null && iNetworkRequestMatchCallback != null) {
                Proxy.sDefaultImpl = iNetworkRequestMatchCallback;
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
            if (n != 1) {
                if (n != 2) {
                    if (n != 3) {
                        if (n != 4) {
                            if (n != 5) {
                                if (n != 1598968902) {
                                    return super.onTransact(n, (Parcel)object, parcel, n2);
                                }
                                parcel.writeString(DESCRIPTOR);
                                return true;
                            }
                            ((Parcel)object).enforceInterface(DESCRIPTOR);
                            object = ((Parcel)object).readInt() != 0 ? WifiConfiguration.CREATOR.createFromParcel((Parcel)object) : null;
                            this.onUserSelectionConnectFailure((WifiConfiguration)object);
                            return true;
                        }
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = ((Parcel)object).readInt() != 0 ? WifiConfiguration.CREATOR.createFromParcel((Parcel)object) : null;
                        this.onUserSelectionConnectSuccess((WifiConfiguration)object);
                        return true;
                    }
                    ((Parcel)object).enforceInterface(DESCRIPTOR);
                    this.onMatch(((Parcel)object).createTypedArrayList(ScanResult.CREATOR));
                    return true;
                }
                ((Parcel)object).enforceInterface(DESCRIPTOR);
                this.onAbort();
                return true;
            }
            ((Parcel)object).enforceInterface(DESCRIPTOR);
            this.onUserSelectionCallbackRegistration(INetworkRequestUserSelectionCallback.Stub.asInterface(((Parcel)object).readStrongBinder()));
            return true;
        }

        private static class Proxy
        implements INetworkRequestMatchCallback {
            public static INetworkRequestMatchCallback sDefaultImpl;
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
            public void onAbort() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(2, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onAbort();
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onMatch(List<ScanResult> list) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeTypedList(list);
                    if (!this.mRemote.transact(3, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onMatch(list);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void onUserSelectionCallbackRegistration(INetworkRequestUserSelectionCallback iNetworkRequestUserSelectionCallback) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iNetworkRequestUserSelectionCallback != null ? iNetworkRequestUserSelectionCallback.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (this.mRemote.transact(1, parcel, null, 1)) return;
                    if (Stub.getDefaultImpl() == null) return;
                    Stub.getDefaultImpl().onUserSelectionCallbackRegistration(iNetworkRequestUserSelectionCallback);
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onUserSelectionConnectFailure(WifiConfiguration wifiConfiguration) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (wifiConfiguration != null) {
                        parcel.writeInt(1);
                        wifiConfiguration.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(5, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onUserSelectionConnectFailure(wifiConfiguration);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onUserSelectionConnectSuccess(WifiConfiguration wifiConfiguration) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (wifiConfiguration != null) {
                        parcel.writeInt(1);
                        wifiConfiguration.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(4, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onUserSelectionConnectSuccess(wifiConfiguration);
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

