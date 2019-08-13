/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.location;

import android.annotation.UnsupportedAppUsage;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import android.os.WorkSource;
import com.android.internal.location.ILocationProviderManager;
import com.android.internal.location.ProviderRequest;

public interface ILocationProvider
extends IInterface {
    @UnsupportedAppUsage
    public int getStatus(Bundle var1) throws RemoteException;

    @UnsupportedAppUsage
    public long getStatusUpdateTime() throws RemoteException;

    public void sendExtraCommand(String var1, Bundle var2) throws RemoteException;

    public void setLocationProviderManager(ILocationProviderManager var1) throws RemoteException;

    public void setRequest(ProviderRequest var1, WorkSource var2) throws RemoteException;

    public static class Default
    implements ILocationProvider {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public int getStatus(Bundle bundle) throws RemoteException {
            return 0;
        }

        @Override
        public long getStatusUpdateTime() throws RemoteException {
            return 0L;
        }

        @Override
        public void sendExtraCommand(String string2, Bundle bundle) throws RemoteException {
        }

        @Override
        public void setLocationProviderManager(ILocationProviderManager iLocationProviderManager) throws RemoteException {
        }

        @Override
        public void setRequest(ProviderRequest providerRequest, WorkSource workSource) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements ILocationProvider {
        private static final String DESCRIPTOR = "com.android.internal.location.ILocationProvider";
        static final int TRANSACTION_getStatus = 4;
        static final int TRANSACTION_getStatusUpdateTime = 5;
        static final int TRANSACTION_sendExtraCommand = 3;
        static final int TRANSACTION_setLocationProviderManager = 1;
        static final int TRANSACTION_setRequest = 2;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static ILocationProvider asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof ILocationProvider) {
                return (ILocationProvider)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static ILocationProvider getDefaultImpl() {
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
                            return "getStatusUpdateTime";
                        }
                        return "getStatus";
                    }
                    return "sendExtraCommand";
                }
                return "setRequest";
            }
            return "setLocationProviderManager";
        }

        public static boolean setDefaultImpl(ILocationProvider iLocationProvider) {
            if (Proxy.sDefaultImpl == null && iLocationProvider != null) {
                Proxy.sDefaultImpl = iLocationProvider;
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
        public boolean onTransact(int n, Parcel object, Parcel object2, int n2) throws RemoteException {
            if (n != 1) {
                if (n != 2) {
                    if (n != 3) {
                        if (n != 4) {
                            if (n != 5) {
                                if (n != 1598968902) {
                                    return super.onTransact(n, (Parcel)object, (Parcel)object2, n2);
                                }
                                ((Parcel)object2).writeString(DESCRIPTOR);
                                return true;
                            }
                            ((Parcel)object).enforceInterface(DESCRIPTOR);
                            long l = this.getStatusUpdateTime();
                            ((Parcel)object2).writeNoException();
                            ((Parcel)object2).writeLong(l);
                            return true;
                        }
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = new Bundle();
                        n = this.getStatus((Bundle)object);
                        ((Parcel)object2).writeNoException();
                        ((Parcel)object2).writeInt(n);
                        ((Parcel)object2).writeInt(1);
                        ((Bundle)object).writeToParcel((Parcel)object2, 1);
                        return true;
                    }
                    ((Parcel)object).enforceInterface(DESCRIPTOR);
                    object2 = ((Parcel)object).readString();
                    object = ((Parcel)object).readInt() != 0 ? Bundle.CREATOR.createFromParcel((Parcel)object) : null;
                    this.sendExtraCommand((String)object2, (Bundle)object);
                    return true;
                }
                ((Parcel)object).enforceInterface(DESCRIPTOR);
                object2 = ((Parcel)object).readInt() != 0 ? ProviderRequest.CREATOR.createFromParcel((Parcel)object) : null;
                object = ((Parcel)object).readInt() != 0 ? WorkSource.CREATOR.createFromParcel((Parcel)object) : null;
                this.setRequest((ProviderRequest)object2, (WorkSource)object);
                return true;
            }
            ((Parcel)object).enforceInterface(DESCRIPTOR);
            this.setLocationProviderManager(ILocationProviderManager.Stub.asInterface(((Parcel)object).readStrongBinder()));
            return true;
        }

        private static class Proxy
        implements ILocationProvider {
            public static ILocationProvider sDefaultImpl;
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
            public int getStatus(Bundle bundle) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(4, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        int n = Stub.getDefaultImpl().getStatus(bundle);
                        return n;
                    }
                    parcel2.readException();
                    int n = parcel2.readInt();
                    if (parcel2.readInt() != 0) {
                        bundle.readFromParcel(parcel2);
                    }
                    return n;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public long getStatusUpdateTime() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(5, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        long l = Stub.getDefaultImpl().getStatusUpdateTime();
                        return l;
                    }
                    parcel2.readException();
                    long l = parcel2.readLong();
                    return l;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void sendExtraCommand(String string2, Bundle bundle) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    if (bundle != null) {
                        parcel.writeInt(1);
                        bundle.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(3, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().sendExtraCommand(string2, bundle);
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
            public void setLocationProviderManager(ILocationProviderManager iLocationProviderManager) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iLocationProviderManager != null ? iLocationProviderManager.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (this.mRemote.transact(1, parcel, null, 1)) return;
                    if (Stub.getDefaultImpl() == null) return;
                    Stub.getDefaultImpl().setLocationProviderManager(iLocationProviderManager);
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void setRequest(ProviderRequest providerRequest, WorkSource workSource) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (providerRequest != null) {
                        parcel.writeInt(1);
                        providerRequest.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (workSource != null) {
                        parcel.writeInt(1);
                        workSource.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(2, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setRequest(providerRequest, workSource);
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

