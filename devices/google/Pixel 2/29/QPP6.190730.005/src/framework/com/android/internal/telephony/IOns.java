/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.telephony;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import android.telephony.AvailableNetworkInfo;
import com.android.internal.telephony.ISetOpportunisticDataCallback;
import com.android.internal.telephony.IUpdateAvailableNetworksCallback;
import java.util.ArrayList;
import java.util.List;

public interface IOns
extends IInterface {
    public int getPreferredDataSubscriptionId(String var1) throws RemoteException;

    public boolean isEnabled(String var1) throws RemoteException;

    public boolean setEnable(boolean var1, String var2) throws RemoteException;

    public void setPreferredDataSubscriptionId(int var1, boolean var2, ISetOpportunisticDataCallback var3, String var4) throws RemoteException;

    public void updateAvailableNetworks(List<AvailableNetworkInfo> var1, IUpdateAvailableNetworksCallback var2, String var3) throws RemoteException;

    public static class Default
    implements IOns {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public int getPreferredDataSubscriptionId(String string2) throws RemoteException {
            return 0;
        }

        @Override
        public boolean isEnabled(String string2) throws RemoteException {
            return false;
        }

        @Override
        public boolean setEnable(boolean bl, String string2) throws RemoteException {
            return false;
        }

        @Override
        public void setPreferredDataSubscriptionId(int n, boolean bl, ISetOpportunisticDataCallback iSetOpportunisticDataCallback, String string2) throws RemoteException {
        }

        @Override
        public void updateAvailableNetworks(List<AvailableNetworkInfo> list, IUpdateAvailableNetworksCallback iUpdateAvailableNetworksCallback, String string2) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IOns {
        private static final String DESCRIPTOR = "com.android.internal.telephony.IOns";
        static final int TRANSACTION_getPreferredDataSubscriptionId = 4;
        static final int TRANSACTION_isEnabled = 2;
        static final int TRANSACTION_setEnable = 1;
        static final int TRANSACTION_setPreferredDataSubscriptionId = 3;
        static final int TRANSACTION_updateAvailableNetworks = 5;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IOns asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IOns) {
                return (IOns)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IOns getDefaultImpl() {
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
                            return "updateAvailableNetworks";
                        }
                        return "getPreferredDataSubscriptionId";
                    }
                    return "setPreferredDataSubscriptionId";
                }
                return "isEnabled";
            }
            return "setEnable";
        }

        public static boolean setDefaultImpl(IOns iOns) {
            if (Proxy.sDefaultImpl == null && iOns != null) {
                Proxy.sDefaultImpl = iOns;
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
            boolean bl = false;
            boolean bl2 = false;
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
                            this.updateAvailableNetworks(parcel.createTypedArrayList(AvailableNetworkInfo.CREATOR), IUpdateAvailableNetworksCallback.Stub.asInterface(parcel.readStrongBinder()), parcel.readString());
                            parcel2.writeNoException();
                            return true;
                        }
                        parcel.enforceInterface(DESCRIPTOR);
                        n = this.getPreferredDataSubscriptionId(parcel.readString());
                        parcel2.writeNoException();
                        parcel2.writeInt(n);
                        return true;
                    }
                    parcel.enforceInterface(DESCRIPTOR);
                    n = parcel.readInt();
                    if (parcel.readInt() != 0) {
                        bl2 = true;
                    }
                    this.setPreferredDataSubscriptionId(n, bl2, ISetOpportunisticDataCallback.Stub.asInterface(parcel.readStrongBinder()), parcel.readString());
                    parcel2.writeNoException();
                    return true;
                }
                parcel.enforceInterface(DESCRIPTOR);
                n = this.isEnabled(parcel.readString()) ? 1 : 0;
                parcel2.writeNoException();
                parcel2.writeInt(n);
                return true;
            }
            parcel.enforceInterface(DESCRIPTOR);
            bl2 = bl;
            if (parcel.readInt() != 0) {
                bl2 = true;
            }
            n = this.setEnable(bl2, parcel.readString()) ? 1 : 0;
            parcel2.writeNoException();
            parcel2.writeInt(n);
            return true;
        }

        private static class Proxy
        implements IOns {
            public static IOns sDefaultImpl;
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
            public int getPreferredDataSubscriptionId(String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(4, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        int n = Stub.getDefaultImpl().getPreferredDataSubscriptionId(string2);
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
            public boolean isEnabled(String string2) throws RemoteException {
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
                    if (iBinder.transact(2, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().isEnabled(string2);
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

            @Override
            public boolean setEnable(boolean bl, String string2) throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                boolean bl2;
                int n;
                block4 : {
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        bl2 = true;
                        n = bl ? 1 : 0;
                    }
                    catch (Throwable throwable) {
                        parcel.recycle();
                        parcel2.recycle();
                        throw throwable;
                    }
                    parcel2.writeInt(n);
                    parcel2.writeString(string2);
                    if (this.mRemote.transact(1, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block4;
                    bl = Stub.getDefaultImpl().setEnable(bl, string2);
                    parcel.recycle();
                    parcel2.recycle();
                    return bl;
                }
                parcel.readException();
                n = parcel.readInt();
                bl = n != 0 ? bl2 : false;
                parcel.recycle();
                parcel2.recycle();
                return bl;
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void setPreferredDataSubscriptionId(int n, boolean bl, ISetOpportunisticDataCallback iSetOpportunisticDataCallback, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    int n2 = bl ? 1 : 0;
                    parcel.writeInt(n2);
                    IBinder iBinder = iSetOpportunisticDataCallback != null ? iSetOpportunisticDataCallback.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(3, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setPreferredDataSubscriptionId(n, bl, iSetOpportunisticDataCallback, string2);
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
            public void updateAvailableNetworks(List<AvailableNetworkInfo> list, IUpdateAvailableNetworksCallback iUpdateAvailableNetworksCallback, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeTypedList(list);
                    IBinder iBinder = iUpdateAvailableNetworksCallback != null ? iUpdateAvailableNetworksCallback.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(5, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().updateAvailableNetworks(list, iUpdateAvailableNetworksCallback, string2);
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

