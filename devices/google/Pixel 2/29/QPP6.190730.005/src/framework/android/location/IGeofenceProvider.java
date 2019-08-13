/*
 * Decompiled with CFR 0.145.
 */
package android.location;

import android.annotation.UnsupportedAppUsage;
import android.hardware.location.IGeofenceHardware;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IGeofenceProvider
extends IInterface {
    @UnsupportedAppUsage
    public void setGeofenceHardware(IGeofenceHardware var1) throws RemoteException;

    public static class Default
    implements IGeofenceProvider {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void setGeofenceHardware(IGeofenceHardware iGeofenceHardware) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IGeofenceProvider {
        private static final String DESCRIPTOR = "android.location.IGeofenceProvider";
        static final int TRANSACTION_setGeofenceHardware = 1;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IGeofenceProvider asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IGeofenceProvider) {
                return (IGeofenceProvider)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IGeofenceProvider getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            if (n != 1) {
                return null;
            }
            return "setGeofenceHardware";
        }

        public static boolean setDefaultImpl(IGeofenceProvider iGeofenceProvider) {
            if (Proxy.sDefaultImpl == null && iGeofenceProvider != null) {
                Proxy.sDefaultImpl = iGeofenceProvider;
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
                if (n != 1598968902) {
                    return super.onTransact(n, parcel, parcel2, n2);
                }
                parcel2.writeString(DESCRIPTOR);
                return true;
            }
            parcel.enforceInterface(DESCRIPTOR);
            this.setGeofenceHardware(IGeofenceHardware.Stub.asInterface(parcel.readStrongBinder()));
            return true;
        }

        private static class Proxy
        implements IGeofenceProvider {
            public static IGeofenceProvider sDefaultImpl;
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

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void setGeofenceHardware(IGeofenceHardware iGeofenceHardware) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iGeofenceHardware != null ? iGeofenceHardware.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (this.mRemote.transact(1, parcel, null, 1)) return;
                    if (Stub.getDefaultImpl() == null) return;
                    Stub.getDefaultImpl().setGeofenceHardware(iGeofenceHardware);
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }
        }

    }

}

