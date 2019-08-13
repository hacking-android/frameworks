/*
 * Decompiled with CFR 0.145.
 */
package android.location;

import android.annotation.UnsupportedAppUsage;
import android.location.Location;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;

public interface ILocationListener
extends IInterface {
    @UnsupportedAppUsage
    public void onLocationChanged(Location var1) throws RemoteException;

    @UnsupportedAppUsage
    public void onProviderDisabled(String var1) throws RemoteException;

    @UnsupportedAppUsage
    public void onProviderEnabled(String var1) throws RemoteException;

    @UnsupportedAppUsage
    public void onStatusChanged(String var1, int var2, Bundle var3) throws RemoteException;

    public static class Default
    implements ILocationListener {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void onLocationChanged(Location location) throws RemoteException {
        }

        @Override
        public void onProviderDisabled(String string2) throws RemoteException {
        }

        @Override
        public void onProviderEnabled(String string2) throws RemoteException {
        }

        @Override
        public void onStatusChanged(String string2, int n, Bundle bundle) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements ILocationListener {
        private static final String DESCRIPTOR = "android.location.ILocationListener";
        static final int TRANSACTION_onLocationChanged = 1;
        static final int TRANSACTION_onProviderDisabled = 3;
        static final int TRANSACTION_onProviderEnabled = 2;
        static final int TRANSACTION_onStatusChanged = 4;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static ILocationListener asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof ILocationListener) {
                return (ILocationListener)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static ILocationListener getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            if (n != 1) {
                if (n != 2) {
                    if (n != 3) {
                        if (n != 4) {
                            return null;
                        }
                        return "onStatusChanged";
                    }
                    return "onProviderDisabled";
                }
                return "onProviderEnabled";
            }
            return "onLocationChanged";
        }

        public static boolean setDefaultImpl(ILocationListener iLocationListener) {
            if (Proxy.sDefaultImpl == null && iLocationListener != null) {
                Proxy.sDefaultImpl = iLocationListener;
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
                            if (n != 1598968902) {
                                return super.onTransact(n, (Parcel)object, (Parcel)object2, n2);
                            }
                            ((Parcel)object2).writeString(DESCRIPTOR);
                            return true;
                        }
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object2 = ((Parcel)object).readString();
                        n = ((Parcel)object).readInt();
                        object = ((Parcel)object).readInt() != 0 ? Bundle.CREATOR.createFromParcel((Parcel)object) : null;
                        this.onStatusChanged((String)object2, n, (Bundle)object);
                        return true;
                    }
                    ((Parcel)object).enforceInterface(DESCRIPTOR);
                    this.onProviderDisabled(((Parcel)object).readString());
                    return true;
                }
                ((Parcel)object).enforceInterface(DESCRIPTOR);
                this.onProviderEnabled(((Parcel)object).readString());
                return true;
            }
            ((Parcel)object).enforceInterface(DESCRIPTOR);
            object = ((Parcel)object).readInt() != 0 ? Location.CREATOR.createFromParcel((Parcel)object) : null;
            this.onLocationChanged((Location)object);
            return true;
        }

        private static class Proxy
        implements ILocationListener {
            public static ILocationListener sDefaultImpl;
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
            public void onLocationChanged(Location location) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (location != null) {
                        parcel.writeInt(1);
                        location.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(1, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onLocationChanged(location);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onProviderDisabled(String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(3, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onProviderDisabled(string2);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onProviderEnabled(String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(2, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onProviderEnabled(string2);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onStatusChanged(String string2, int n, Bundle bundle) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    parcel.writeInt(n);
                    if (bundle != null) {
                        parcel.writeInt(1);
                        bundle.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(4, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onStatusChanged(string2, n, bundle);
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

