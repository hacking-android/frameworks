/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.location;

import android.hardware.location.GeofenceHardwareRequestParcelable;
import android.hardware.location.IGeofenceHardwareCallback;
import android.hardware.location.IGeofenceHardwareMonitorCallback;
import android.location.IFusedGeofenceHardware;
import android.location.IGpsGeofenceHardware;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;

public interface IGeofenceHardware
extends IInterface {
    public boolean addCircularFence(int var1, GeofenceHardwareRequestParcelable var2, IGeofenceHardwareCallback var3) throws RemoteException;

    public int[] getMonitoringTypes() throws RemoteException;

    public int getStatusOfMonitoringType(int var1) throws RemoteException;

    public boolean pauseGeofence(int var1, int var2) throws RemoteException;

    public boolean registerForMonitorStateChangeCallback(int var1, IGeofenceHardwareMonitorCallback var2) throws RemoteException;

    public boolean removeGeofence(int var1, int var2) throws RemoteException;

    public boolean resumeGeofence(int var1, int var2, int var3) throws RemoteException;

    public void setFusedGeofenceHardware(IFusedGeofenceHardware var1) throws RemoteException;

    public void setGpsGeofenceHardware(IGpsGeofenceHardware var1) throws RemoteException;

    public boolean unregisterForMonitorStateChangeCallback(int var1, IGeofenceHardwareMonitorCallback var2) throws RemoteException;

    public static class Default
    implements IGeofenceHardware {
        @Override
        public boolean addCircularFence(int n, GeofenceHardwareRequestParcelable geofenceHardwareRequestParcelable, IGeofenceHardwareCallback iGeofenceHardwareCallback) throws RemoteException {
            return false;
        }

        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public int[] getMonitoringTypes() throws RemoteException {
            return null;
        }

        @Override
        public int getStatusOfMonitoringType(int n) throws RemoteException {
            return 0;
        }

        @Override
        public boolean pauseGeofence(int n, int n2) throws RemoteException {
            return false;
        }

        @Override
        public boolean registerForMonitorStateChangeCallback(int n, IGeofenceHardwareMonitorCallback iGeofenceHardwareMonitorCallback) throws RemoteException {
            return false;
        }

        @Override
        public boolean removeGeofence(int n, int n2) throws RemoteException {
            return false;
        }

        @Override
        public boolean resumeGeofence(int n, int n2, int n3) throws RemoteException {
            return false;
        }

        @Override
        public void setFusedGeofenceHardware(IFusedGeofenceHardware iFusedGeofenceHardware) throws RemoteException {
        }

        @Override
        public void setGpsGeofenceHardware(IGpsGeofenceHardware iGpsGeofenceHardware) throws RemoteException {
        }

        @Override
        public boolean unregisterForMonitorStateChangeCallback(int n, IGeofenceHardwareMonitorCallback iGeofenceHardwareMonitorCallback) throws RemoteException {
            return false;
        }
    }

    public static abstract class Stub
    extends Binder
    implements IGeofenceHardware {
        private static final String DESCRIPTOR = "android.hardware.location.IGeofenceHardware";
        static final int TRANSACTION_addCircularFence = 5;
        static final int TRANSACTION_getMonitoringTypes = 3;
        static final int TRANSACTION_getStatusOfMonitoringType = 4;
        static final int TRANSACTION_pauseGeofence = 7;
        static final int TRANSACTION_registerForMonitorStateChangeCallback = 9;
        static final int TRANSACTION_removeGeofence = 6;
        static final int TRANSACTION_resumeGeofence = 8;
        static final int TRANSACTION_setFusedGeofenceHardware = 2;
        static final int TRANSACTION_setGpsGeofenceHardware = 1;
        static final int TRANSACTION_unregisterForMonitorStateChangeCallback = 10;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IGeofenceHardware asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IGeofenceHardware) {
                return (IGeofenceHardware)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IGeofenceHardware getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            switch (n) {
                default: {
                    return null;
                }
                case 10: {
                    return "unregisterForMonitorStateChangeCallback";
                }
                case 9: {
                    return "registerForMonitorStateChangeCallback";
                }
                case 8: {
                    return "resumeGeofence";
                }
                case 7: {
                    return "pauseGeofence";
                }
                case 6: {
                    return "removeGeofence";
                }
                case 5: {
                    return "addCircularFence";
                }
                case 4: {
                    return "getStatusOfMonitoringType";
                }
                case 3: {
                    return "getMonitoringTypes";
                }
                case 2: {
                    return "setFusedGeofenceHardware";
                }
                case 1: 
            }
            return "setGpsGeofenceHardware";
        }

        public static boolean setDefaultImpl(IGeofenceHardware iGeofenceHardware) {
            if (Proxy.sDefaultImpl == null && iGeofenceHardware != null) {
                Proxy.sDefaultImpl = iGeofenceHardware;
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
        public boolean onTransact(int n, Parcel arrn, Parcel parcel, int n2) throws RemoteException {
            if (n != 1598968902) {
                switch (n) {
                    default: {
                        return super.onTransact(n, (Parcel)arrn, parcel, n2);
                    }
                    case 10: {
                        arrn.enforceInterface(DESCRIPTOR);
                        n = this.unregisterForMonitorStateChangeCallback(arrn.readInt(), IGeofenceHardwareMonitorCallback.Stub.asInterface(arrn.readStrongBinder())) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 9: {
                        arrn.enforceInterface(DESCRIPTOR);
                        n = this.registerForMonitorStateChangeCallback(arrn.readInt(), IGeofenceHardwareMonitorCallback.Stub.asInterface(arrn.readStrongBinder())) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 8: {
                        arrn.enforceInterface(DESCRIPTOR);
                        n = this.resumeGeofence(arrn.readInt(), arrn.readInt(), arrn.readInt()) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 7: {
                        arrn.enforceInterface(DESCRIPTOR);
                        n = this.pauseGeofence(arrn.readInt(), arrn.readInt()) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 6: {
                        arrn.enforceInterface(DESCRIPTOR);
                        n = this.removeGeofence(arrn.readInt(), arrn.readInt()) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 5: {
                        arrn.enforceInterface(DESCRIPTOR);
                        n = arrn.readInt();
                        GeofenceHardwareRequestParcelable geofenceHardwareRequestParcelable = arrn.readInt() != 0 ? GeofenceHardwareRequestParcelable.CREATOR.createFromParcel((Parcel)arrn) : null;
                        n = this.addCircularFence(n, geofenceHardwareRequestParcelable, IGeofenceHardwareCallback.Stub.asInterface(arrn.readStrongBinder())) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 4: {
                        arrn.enforceInterface(DESCRIPTOR);
                        n = this.getStatusOfMonitoringType(arrn.readInt());
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 3: {
                        arrn.enforceInterface(DESCRIPTOR);
                        arrn = this.getMonitoringTypes();
                        parcel.writeNoException();
                        parcel.writeIntArray(arrn);
                        return true;
                    }
                    case 2: {
                        arrn.enforceInterface(DESCRIPTOR);
                        this.setFusedGeofenceHardware(IFusedGeofenceHardware.Stub.asInterface(arrn.readStrongBinder()));
                        parcel.writeNoException();
                        return true;
                    }
                    case 1: 
                }
                arrn.enforceInterface(DESCRIPTOR);
                this.setGpsGeofenceHardware(IGpsGeofenceHardware.Stub.asInterface(arrn.readStrongBinder()));
                parcel.writeNoException();
                return true;
            }
            parcel.writeString(DESCRIPTOR);
            return true;
        }

        private static class Proxy
        implements IGeofenceHardware {
            public static IGeofenceHardware sDefaultImpl;
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public boolean addCircularFence(int n, GeofenceHardwareRequestParcelable geofenceHardwareRequestParcelable, IGeofenceHardwareCallback iGeofenceHardwareCallback) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    boolean bl = true;
                    if (geofenceHardwareRequestParcelable != null) {
                        parcel.writeInt(1);
                        geofenceHardwareRequestParcelable.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    IBinder iBinder = iGeofenceHardwareCallback != null ? iGeofenceHardwareCallback.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (!this.mRemote.transact(5, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        bl = Stub.getDefaultImpl().addCircularFence(n, geofenceHardwareRequestParcelable, iGeofenceHardwareCallback);
                        parcel2.recycle();
                        parcel.recycle();
                        return bl;
                    }
                    parcel2.readException();
                    n = parcel2.readInt();
                    if (n == 0) {
                        bl = false;
                    }
                    parcel2.recycle();
                    parcel.recycle();
                    return bl;
                }
                catch (Throwable throwable) {
                    parcel2.recycle();
                    parcel.recycle();
                    throw throwable;
                }
            }

            @Override
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            @Override
            public int[] getMonitoringTypes() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(3, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        int[] arrn = Stub.getDefaultImpl().getMonitoringTypes();
                        return arrn;
                    }
                    parcel2.readException();
                    int[] arrn = parcel2.createIntArray();
                    return arrn;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public int getStatusOfMonitoringType(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(4, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        n = Stub.getDefaultImpl().getStatusOfMonitoringType(n);
                        return n;
                    }
                    parcel2.readException();
                    n = parcel2.readInt();
                    return n;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public boolean pauseGeofence(int n, int n2) throws RemoteException {
                Parcel parcel;
                boolean bl;
                Parcel parcel2;
                block5 : {
                    IBinder iBinder;
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel2.writeInt(n);
                        parcel2.writeInt(n2);
                        iBinder = this.mRemote;
                        bl = false;
                    }
                    catch (Throwable throwable) {
                        parcel.recycle();
                        parcel2.recycle();
                        throw throwable;
                    }
                    if (iBinder.transact(7, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().pauseGeofence(n, n2);
                    parcel.recycle();
                    parcel2.recycle();
                    return bl;
                }
                parcel.readException();
                n = parcel.readInt();
                if (n != 0) {
                    bl = true;
                }
                parcel.recycle();
                parcel2.recycle();
                return bl;
            }

            @Override
            public boolean registerForMonitorStateChangeCallback(int n, IGeofenceHardwareMonitorCallback iGeofenceHardwareMonitorCallback) throws RemoteException {
                Parcel parcel;
                boolean bl;
                Parcel parcel2;
                block9 : {
                    IBinder iBinder;
                    block8 : {
                        block7 : {
                            parcel2 = Parcel.obtain();
                            parcel = Parcel.obtain();
                            try {
                                parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                                parcel2.writeInt(n);
                                if (iGeofenceHardwareMonitorCallback == null) break block7;
                            }
                            catch (Throwable throwable) {
                                parcel.recycle();
                                parcel2.recycle();
                                throw throwable;
                            }
                            iBinder = iGeofenceHardwareMonitorCallback.asBinder();
                            break block8;
                        }
                        iBinder = null;
                    }
                    parcel2.writeStrongBinder(iBinder);
                    iBinder = this.mRemote;
                    bl = false;
                    if (iBinder.transact(9, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block9;
                    bl = Stub.getDefaultImpl().registerForMonitorStateChangeCallback(n, iGeofenceHardwareMonitorCallback);
                    parcel.recycle();
                    parcel2.recycle();
                    return bl;
                }
                parcel.readException();
                n = parcel.readInt();
                if (n != 0) {
                    bl = true;
                }
                parcel.recycle();
                parcel2.recycle();
                return bl;
            }

            @Override
            public boolean removeGeofence(int n, int n2) throws RemoteException {
                Parcel parcel;
                boolean bl;
                Parcel parcel2;
                block5 : {
                    IBinder iBinder;
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel2.writeInt(n);
                        parcel2.writeInt(n2);
                        iBinder = this.mRemote;
                        bl = false;
                    }
                    catch (Throwable throwable) {
                        parcel.recycle();
                        parcel2.recycle();
                        throw throwable;
                    }
                    if (iBinder.transact(6, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().removeGeofence(n, n2);
                    parcel.recycle();
                    parcel2.recycle();
                    return bl;
                }
                parcel.readException();
                n = parcel.readInt();
                if (n != 0) {
                    bl = true;
                }
                parcel.recycle();
                parcel2.recycle();
                return bl;
            }

            @Override
            public boolean resumeGeofence(int n, int n2, int n3) throws RemoteException {
                boolean bl;
                Parcel parcel;
                Parcel parcel2;
                block5 : {
                    IBinder iBinder;
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    try {
                        parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel.writeInt(n);
                        parcel.writeInt(n2);
                        parcel.writeInt(n3);
                        iBinder = this.mRemote;
                        bl = false;
                    }
                    catch (Throwable throwable) {
                        parcel2.recycle();
                        parcel.recycle();
                        throw throwable;
                    }
                    if (iBinder.transact(8, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().resumeGeofence(n, n2, n3);
                    parcel2.recycle();
                    parcel.recycle();
                    return bl;
                }
                parcel2.readException();
                n = parcel2.readInt();
                if (n != 0) {
                    bl = true;
                }
                parcel2.recycle();
                parcel.recycle();
                return bl;
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void setFusedGeofenceHardware(IFusedGeofenceHardware iFusedGeofenceHardware) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iFusedGeofenceHardware != null ? iFusedGeofenceHardware.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (!this.mRemote.transact(2, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setFusedGeofenceHardware(iFusedGeofenceHardware);
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
            public void setGpsGeofenceHardware(IGpsGeofenceHardware iGpsGeofenceHardware) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iGpsGeofenceHardware != null ? iGpsGeofenceHardware.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (!this.mRemote.transact(1, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setGpsGeofenceHardware(iGpsGeofenceHardware);
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
            public boolean unregisterForMonitorStateChangeCallback(int n, IGeofenceHardwareMonitorCallback iGeofenceHardwareMonitorCallback) throws RemoteException {
                Parcel parcel;
                boolean bl;
                Parcel parcel2;
                block9 : {
                    IBinder iBinder;
                    block8 : {
                        block7 : {
                            parcel2 = Parcel.obtain();
                            parcel = Parcel.obtain();
                            try {
                                parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                                parcel2.writeInt(n);
                                if (iGeofenceHardwareMonitorCallback == null) break block7;
                            }
                            catch (Throwable throwable) {
                                parcel.recycle();
                                parcel2.recycle();
                                throw throwable;
                            }
                            iBinder = iGeofenceHardwareMonitorCallback.asBinder();
                            break block8;
                        }
                        iBinder = null;
                    }
                    parcel2.writeStrongBinder(iBinder);
                    iBinder = this.mRemote;
                    bl = false;
                    if (iBinder.transact(10, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block9;
                    bl = Stub.getDefaultImpl().unregisterForMonitorStateChangeCallback(n, iGeofenceHardwareMonitorCallback);
                    parcel.recycle();
                    parcel2.recycle();
                    return bl;
                }
                parcel.readException();
                n = parcel.readInt();
                if (n != 0) {
                    bl = true;
                }
                parcel.recycle();
                parcel2.recycle();
                return bl;
            }
        }

    }

}

