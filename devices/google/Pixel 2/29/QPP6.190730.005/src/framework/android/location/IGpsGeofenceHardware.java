/*
 * Decompiled with CFR 0.145.
 */
package android.location;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IGpsGeofenceHardware
extends IInterface {
    public boolean addCircularHardwareGeofence(int var1, double var2, double var4, double var6, int var8, int var9, int var10, int var11) throws RemoteException;

    public boolean isHardwareGeofenceSupported() throws RemoteException;

    public boolean pauseHardwareGeofence(int var1) throws RemoteException;

    public boolean removeHardwareGeofence(int var1) throws RemoteException;

    public boolean resumeHardwareGeofence(int var1, int var2) throws RemoteException;

    public static class Default
    implements IGpsGeofenceHardware {
        @Override
        public boolean addCircularHardwareGeofence(int n, double d, double d2, double d3, int n2, int n3, int n4, int n5) throws RemoteException {
            return false;
        }

        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public boolean isHardwareGeofenceSupported() throws RemoteException {
            return false;
        }

        @Override
        public boolean pauseHardwareGeofence(int n) throws RemoteException {
            return false;
        }

        @Override
        public boolean removeHardwareGeofence(int n) throws RemoteException {
            return false;
        }

        @Override
        public boolean resumeHardwareGeofence(int n, int n2) throws RemoteException {
            return false;
        }
    }

    public static abstract class Stub
    extends Binder
    implements IGpsGeofenceHardware {
        private static final String DESCRIPTOR = "android.location.IGpsGeofenceHardware";
        static final int TRANSACTION_addCircularHardwareGeofence = 2;
        static final int TRANSACTION_isHardwareGeofenceSupported = 1;
        static final int TRANSACTION_pauseHardwareGeofence = 4;
        static final int TRANSACTION_removeHardwareGeofence = 3;
        static final int TRANSACTION_resumeHardwareGeofence = 5;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IGpsGeofenceHardware asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IGpsGeofenceHardware) {
                return (IGpsGeofenceHardware)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IGpsGeofenceHardware getDefaultImpl() {
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
                            return "resumeHardwareGeofence";
                        }
                        return "pauseHardwareGeofence";
                    }
                    return "removeHardwareGeofence";
                }
                return "addCircularHardwareGeofence";
            }
            return "isHardwareGeofenceSupported";
        }

        public static boolean setDefaultImpl(IGpsGeofenceHardware iGpsGeofenceHardware) {
            if (Proxy.sDefaultImpl == null && iGpsGeofenceHardware != null) {
                Proxy.sDefaultImpl = iGpsGeofenceHardware;
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
                            n = this.resumeHardwareGeofence(parcel.readInt(), parcel.readInt()) ? 1 : 0;
                            parcel2.writeNoException();
                            parcel2.writeInt(n);
                            return true;
                        }
                        parcel.enforceInterface(DESCRIPTOR);
                        n = this.pauseHardwareGeofence(parcel.readInt()) ? 1 : 0;
                        parcel2.writeNoException();
                        parcel2.writeInt(n);
                        return true;
                    }
                    parcel.enforceInterface(DESCRIPTOR);
                    n = this.removeHardwareGeofence(parcel.readInt()) ? 1 : 0;
                    parcel2.writeNoException();
                    parcel2.writeInt(n);
                    return true;
                }
                parcel.enforceInterface(DESCRIPTOR);
                n = this.addCircularHardwareGeofence(parcel.readInt(), parcel.readDouble(), parcel.readDouble(), parcel.readDouble(), parcel.readInt(), parcel.readInt(), parcel.readInt(), parcel.readInt()) ? 1 : 0;
                parcel2.writeNoException();
                parcel2.writeInt(n);
                return true;
            }
            parcel.enforceInterface(DESCRIPTOR);
            n = this.isHardwareGeofenceSupported() ? 1 : 0;
            parcel2.writeNoException();
            parcel2.writeInt(n);
            return true;
        }

        private static class Proxy
        implements IGpsGeofenceHardware {
            public static IGpsGeofenceHardware sDefaultImpl;
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            /*
             * Loose catch block
             * WARNING - void declaration
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             * Lifted jumps to return sites
             */
            @Override
            public boolean addCircularHardwareGeofence(int n, double d, double d2, double d3, int n2, int n3, int n4, int n5) throws RemoteException {
                Parcel parcel;
                void var14_14;
                Parcel parcel2;
                block7 : {
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    try {
                        parcel.writeInt(n);
                        parcel.writeDouble(d);
                        parcel.writeDouble(d2);
                        parcel.writeDouble(d3);
                        parcel.writeInt(n2);
                        parcel.writeInt(n3);
                        parcel.writeInt(n4);
                        parcel.writeInt(n5);
                        IBinder iBinder = this.mRemote;
                        boolean bl = false;
                        if (!iBinder.transact(2, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                            bl = Stub.getDefaultImpl().addCircularHardwareGeofence(n, d, d2, d3, n2, n3, n4, n5);
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
                    catch (Throwable throwable) {}
                    break block7;
                    catch (Throwable throwable) {
                        // empty catch block
                    }
                }
                parcel2.recycle();
                parcel.recycle();
                throw var14_14;
            }

            @Override
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            @Override
            public boolean isHardwareGeofenceSupported() throws RemoteException {
                boolean bl;
                Parcel parcel;
                Parcel parcel2;
                block5 : {
                    IBinder iBinder;
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        iBinder = this.mRemote;
                        bl = false;
                    }
                    catch (Throwable throwable) {
                        parcel.recycle();
                        parcel2.recycle();
                        throw throwable;
                    }
                    if (iBinder.transact(1, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().isHardwareGeofenceSupported();
                    parcel.recycle();
                    parcel2.recycle();
                    return bl;
                }
                parcel.readException();
                int n = parcel.readInt();
                if (n != 0) {
                    bl = true;
                }
                parcel.recycle();
                parcel2.recycle();
                return bl;
            }

            @Override
            public boolean pauseHardwareGeofence(int n) throws RemoteException {
                Parcel parcel;
                boolean bl;
                Parcel parcel2;
                block5 : {
                    IBinder iBinder;
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    try {
                        parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel.writeInt(n);
                        iBinder = this.mRemote;
                        bl = false;
                    }
                    catch (Throwable throwable) {
                        parcel2.recycle();
                        parcel.recycle();
                        throw throwable;
                    }
                    if (iBinder.transact(4, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().pauseHardwareGeofence(n);
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

            @Override
            public boolean removeHardwareGeofence(int n) throws RemoteException {
                Parcel parcel;
                boolean bl;
                Parcel parcel2;
                block5 : {
                    IBinder iBinder;
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    try {
                        parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel.writeInt(n);
                        iBinder = this.mRemote;
                        bl = false;
                    }
                    catch (Throwable throwable) {
                        parcel2.recycle();
                        parcel.recycle();
                        throw throwable;
                    }
                    if (iBinder.transact(3, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().removeHardwareGeofence(n);
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

            @Override
            public boolean resumeHardwareGeofence(int n, int n2) throws RemoteException {
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
                    if (iBinder.transact(5, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().resumeHardwareGeofence(n, n2);
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

