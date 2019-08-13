/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.location;

import android.annotation.UnsupportedAppUsage;
import android.location.Location;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import com.android.internal.location.ProviderProperties;
import java.util.ArrayList;
import java.util.List;

public interface ILocationProviderManager
extends IInterface {
    @UnsupportedAppUsage
    public void onReportLocation(Location var1) throws RemoteException;

    public void onSetAdditionalProviderPackages(List<String> var1) throws RemoteException;

    @UnsupportedAppUsage
    public void onSetEnabled(boolean var1) throws RemoteException;

    @UnsupportedAppUsage
    public void onSetProperties(ProviderProperties var1) throws RemoteException;

    public static class Default
    implements ILocationProviderManager {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void onReportLocation(Location location) throws RemoteException {
        }

        @Override
        public void onSetAdditionalProviderPackages(List<String> list) throws RemoteException {
        }

        @Override
        public void onSetEnabled(boolean bl) throws RemoteException {
        }

        @Override
        public void onSetProperties(ProviderProperties providerProperties) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements ILocationProviderManager {
        private static final String DESCRIPTOR = "com.android.internal.location.ILocationProviderManager";
        static final int TRANSACTION_onReportLocation = 4;
        static final int TRANSACTION_onSetAdditionalProviderPackages = 1;
        static final int TRANSACTION_onSetEnabled = 2;
        static final int TRANSACTION_onSetProperties = 3;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static ILocationProviderManager asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof ILocationProviderManager) {
                return (ILocationProviderManager)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static ILocationProviderManager getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            if (n != 1) {
                if (n != 2) {
                    if (n != 3) {
                        if (n != 4) {
                            return null;
                        }
                        return "onReportLocation";
                    }
                    return "onSetProperties";
                }
                return "onSetEnabled";
            }
            return "onSetAdditionalProviderPackages";
        }

        public static boolean setDefaultImpl(ILocationProviderManager iLocationProviderManager) {
            if (Proxy.sDefaultImpl == null && iLocationProviderManager != null) {
                Proxy.sDefaultImpl = iLocationProviderManager;
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
                            if (n != 1598968902) {
                                return super.onTransact(n, (Parcel)object, parcel, n2);
                            }
                            parcel.writeString(DESCRIPTOR);
                            return true;
                        }
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = ((Parcel)object).readInt() != 0 ? Location.CREATOR.createFromParcel((Parcel)object) : null;
                        this.onReportLocation((Location)object);
                        parcel.writeNoException();
                        return true;
                    }
                    ((Parcel)object).enforceInterface(DESCRIPTOR);
                    object = ((Parcel)object).readInt() != 0 ? ProviderProperties.CREATOR.createFromParcel((Parcel)object) : null;
                    this.onSetProperties((ProviderProperties)object);
                    parcel.writeNoException();
                    return true;
                }
                ((Parcel)object).enforceInterface(DESCRIPTOR);
                boolean bl = ((Parcel)object).readInt() != 0;
                this.onSetEnabled(bl);
                parcel.writeNoException();
                return true;
            }
            ((Parcel)object).enforceInterface(DESCRIPTOR);
            this.onSetAdditionalProviderPackages(((Parcel)object).createStringArrayList());
            parcel.writeNoException();
            return true;
        }

        private static class Proxy
        implements ILocationProviderManager {
            public static ILocationProviderManager sDefaultImpl;
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
            public void onReportLocation(Location location) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (location != null) {
                        parcel.writeInt(1);
                        location.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(4, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onReportLocation(location);
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
            public void onSetAdditionalProviderPackages(List<String> list) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeStringList(list);
                    if (!this.mRemote.transact(1, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onSetAdditionalProviderPackages(list);
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
            public void onSetEnabled(boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                int n = bl ? 1 : 0;
                try {
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(2, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onSetEnabled(bl);
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
            public void onSetProperties(ProviderProperties providerProperties) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (providerProperties != null) {
                        parcel.writeInt(1);
                        providerProperties.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(3, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onSetProperties(providerProperties);
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

