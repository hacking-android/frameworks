/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.location;

import android.hardware.location.GeofenceHardwareMonitorEvent;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;

public interface IGeofenceHardwareMonitorCallback
extends IInterface {
    public void onMonitoringSystemChange(GeofenceHardwareMonitorEvent var1) throws RemoteException;

    public static class Default
    implements IGeofenceHardwareMonitorCallback {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void onMonitoringSystemChange(GeofenceHardwareMonitorEvent geofenceHardwareMonitorEvent) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IGeofenceHardwareMonitorCallback {
        private static final String DESCRIPTOR = "android.hardware.location.IGeofenceHardwareMonitorCallback";
        static final int TRANSACTION_onMonitoringSystemChange = 1;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IGeofenceHardwareMonitorCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IGeofenceHardwareMonitorCallback) {
                return (IGeofenceHardwareMonitorCallback)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IGeofenceHardwareMonitorCallback getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            if (n != 1) {
                return null;
            }
            return "onMonitoringSystemChange";
        }

        public static boolean setDefaultImpl(IGeofenceHardwareMonitorCallback iGeofenceHardwareMonitorCallback) {
            if (Proxy.sDefaultImpl == null && iGeofenceHardwareMonitorCallback != null) {
                Proxy.sDefaultImpl = iGeofenceHardwareMonitorCallback;
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
                if (n != 1598968902) {
                    return super.onTransact(n, (Parcel)object, parcel, n2);
                }
                parcel.writeString(DESCRIPTOR);
                return true;
            }
            ((Parcel)object).enforceInterface(DESCRIPTOR);
            object = ((Parcel)object).readInt() != 0 ? GeofenceHardwareMonitorEvent.CREATOR.createFromParcel((Parcel)object) : null;
            this.onMonitoringSystemChange((GeofenceHardwareMonitorEvent)object);
            return true;
        }

        private static class Proxy
        implements IGeofenceHardwareMonitorCallback {
            public static IGeofenceHardwareMonitorCallback sDefaultImpl;
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
            public void onMonitoringSystemChange(GeofenceHardwareMonitorEvent geofenceHardwareMonitorEvent) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (geofenceHardwareMonitorEvent != null) {
                        parcel.writeInt(1);
                        geofenceHardwareMonitorEvent.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(1, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onMonitoringSystemChange(geofenceHardwareMonitorEvent);
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

