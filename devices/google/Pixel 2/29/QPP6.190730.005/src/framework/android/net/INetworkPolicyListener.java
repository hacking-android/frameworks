/*
 * Decompiled with CFR 0.145.
 */
package android.net;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface INetworkPolicyListener
extends IInterface {
    public void onMeteredIfacesChanged(String[] var1) throws RemoteException;

    public void onRestrictBackgroundChanged(boolean var1) throws RemoteException;

    public void onSubscriptionOverride(int var1, int var2, int var3) throws RemoteException;

    public void onUidPoliciesChanged(int var1, int var2) throws RemoteException;

    public void onUidRulesChanged(int var1, int var2) throws RemoteException;

    public static class Default
    implements INetworkPolicyListener {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void onMeteredIfacesChanged(String[] arrstring) throws RemoteException {
        }

        @Override
        public void onRestrictBackgroundChanged(boolean bl) throws RemoteException {
        }

        @Override
        public void onSubscriptionOverride(int n, int n2, int n3) throws RemoteException {
        }

        @Override
        public void onUidPoliciesChanged(int n, int n2) throws RemoteException {
        }

        @Override
        public void onUidRulesChanged(int n, int n2) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements INetworkPolicyListener {
        private static final String DESCRIPTOR = "android.net.INetworkPolicyListener";
        static final int TRANSACTION_onMeteredIfacesChanged = 2;
        static final int TRANSACTION_onRestrictBackgroundChanged = 3;
        static final int TRANSACTION_onSubscriptionOverride = 5;
        static final int TRANSACTION_onUidPoliciesChanged = 4;
        static final int TRANSACTION_onUidRulesChanged = 1;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static INetworkPolicyListener asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof INetworkPolicyListener) {
                return (INetworkPolicyListener)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static INetworkPolicyListener getDefaultImpl() {
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
                            return "onSubscriptionOverride";
                        }
                        return "onUidPoliciesChanged";
                    }
                    return "onRestrictBackgroundChanged";
                }
                return "onMeteredIfacesChanged";
            }
            return "onUidRulesChanged";
        }

        public static boolean setDefaultImpl(INetworkPolicyListener iNetworkPolicyListener) {
            if (Proxy.sDefaultImpl == null && iNetworkPolicyListener != null) {
                Proxy.sDefaultImpl = iNetworkPolicyListener;
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
                        if (n != 4) {
                            if (n != 5) {
                                if (n != 1598968902) {
                                    return super.onTransact(n, parcel, parcel2, n2);
                                }
                                parcel2.writeString(DESCRIPTOR);
                                return true;
                            }
                            parcel.enforceInterface(DESCRIPTOR);
                            this.onSubscriptionOverride(parcel.readInt(), parcel.readInt(), parcel.readInt());
                            return true;
                        }
                        parcel.enforceInterface(DESCRIPTOR);
                        this.onUidPoliciesChanged(parcel.readInt(), parcel.readInt());
                        return true;
                    }
                    parcel.enforceInterface(DESCRIPTOR);
                    boolean bl = parcel.readInt() != 0;
                    this.onRestrictBackgroundChanged(bl);
                    return true;
                }
                parcel.enforceInterface(DESCRIPTOR);
                this.onMeteredIfacesChanged(parcel.createStringArray());
                return true;
            }
            parcel.enforceInterface(DESCRIPTOR);
            this.onUidRulesChanged(parcel.readInt(), parcel.readInt());
            return true;
        }

        private static class Proxy
        implements INetworkPolicyListener {
            public static INetworkPolicyListener sDefaultImpl;
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
            public void onMeteredIfacesChanged(String[] arrstring) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeStringArray(arrstring);
                    if (!this.mRemote.transact(2, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onMeteredIfacesChanged(arrstring);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onRestrictBackgroundChanged(boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                int n = bl ? 1 : 0;
                try {
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(3, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onRestrictBackgroundChanged(bl);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onSubscriptionOverride(int n, int n2, int n3) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    parcel.writeInt(n3);
                    if (!this.mRemote.transact(5, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onSubscriptionOverride(n, n2, n3);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onUidPoliciesChanged(int n, int n2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(4, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onUidPoliciesChanged(n, n2);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onUidRulesChanged(int n, int n2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(1, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onUidRulesChanged(n, n2);
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

