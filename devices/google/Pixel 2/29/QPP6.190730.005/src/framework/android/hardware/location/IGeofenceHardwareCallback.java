/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.location;

import android.location.Location;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;

public interface IGeofenceHardwareCallback
extends IInterface {
    public void onGeofenceAdd(int var1, int var2) throws RemoteException;

    public void onGeofencePause(int var1, int var2) throws RemoteException;

    public void onGeofenceRemove(int var1, int var2) throws RemoteException;

    public void onGeofenceResume(int var1, int var2) throws RemoteException;

    public void onGeofenceTransition(int var1, int var2, Location var3, long var4, int var6) throws RemoteException;

    public static class Default
    implements IGeofenceHardwareCallback {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void onGeofenceAdd(int n, int n2) throws RemoteException {
        }

        @Override
        public void onGeofencePause(int n, int n2) throws RemoteException {
        }

        @Override
        public void onGeofenceRemove(int n, int n2) throws RemoteException {
        }

        @Override
        public void onGeofenceResume(int n, int n2) throws RemoteException {
        }

        @Override
        public void onGeofenceTransition(int n, int n2, Location location, long l, int n3) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IGeofenceHardwareCallback {
        private static final String DESCRIPTOR = "android.hardware.location.IGeofenceHardwareCallback";
        static final int TRANSACTION_onGeofenceAdd = 2;
        static final int TRANSACTION_onGeofencePause = 4;
        static final int TRANSACTION_onGeofenceRemove = 3;
        static final int TRANSACTION_onGeofenceResume = 5;
        static final int TRANSACTION_onGeofenceTransition = 1;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IGeofenceHardwareCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IGeofenceHardwareCallback) {
                return (IGeofenceHardwareCallback)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IGeofenceHardwareCallback getDefaultImpl() {
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
                            return "onGeofenceResume";
                        }
                        return "onGeofencePause";
                    }
                    return "onGeofenceRemove";
                }
                return "onGeofenceAdd";
            }
            return "onGeofenceTransition";
        }

        public static boolean setDefaultImpl(IGeofenceHardwareCallback iGeofenceHardwareCallback) {
            if (Proxy.sDefaultImpl == null && iGeofenceHardwareCallback != null) {
                Proxy.sDefaultImpl = iGeofenceHardwareCallback;
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
        public boolean onTransact(int n, Parcel parcel, Parcel object, int n2) throws RemoteException {
            if (n != 1) {
                if (n != 2) {
                    if (n != 3) {
                        if (n != 4) {
                            if (n != 5) {
                                if (n != 1598968902) {
                                    return super.onTransact(n, parcel, (Parcel)object, n2);
                                }
                                ((Parcel)object).writeString(DESCRIPTOR);
                                return true;
                            }
                            parcel.enforceInterface(DESCRIPTOR);
                            this.onGeofenceResume(parcel.readInt(), parcel.readInt());
                            return true;
                        }
                        parcel.enforceInterface(DESCRIPTOR);
                        this.onGeofencePause(parcel.readInt(), parcel.readInt());
                        return true;
                    }
                    parcel.enforceInterface(DESCRIPTOR);
                    this.onGeofenceRemove(parcel.readInt(), parcel.readInt());
                    return true;
                }
                parcel.enforceInterface(DESCRIPTOR);
                this.onGeofenceAdd(parcel.readInt(), parcel.readInt());
                return true;
            }
            parcel.enforceInterface(DESCRIPTOR);
            n2 = parcel.readInt();
            n = parcel.readInt();
            object = parcel.readInt() != 0 ? Location.CREATOR.createFromParcel(parcel) : null;
            this.onGeofenceTransition(n2, n, (Location)object, parcel.readLong(), parcel.readInt());
            return true;
        }

        private static class Proxy
        implements IGeofenceHardwareCallback {
            public static IGeofenceHardwareCallback sDefaultImpl;
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
            public void onGeofenceAdd(int n, int n2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(2, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onGeofenceAdd(n, n2);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onGeofencePause(int n, int n2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(4, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onGeofencePause(n, n2);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onGeofenceRemove(int n, int n2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(3, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onGeofenceRemove(n, n2);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onGeofenceResume(int n, int n2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(5, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onGeofenceResume(n, n2);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
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
            public void onGeofenceTransition(int n, int n2, Location location, long l, int n3) throws RemoteException {
                Parcel parcel;
                void var3_10;
                block15 : {
                    block14 : {
                        parcel = Parcel.obtain();
                        parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                        try {
                            parcel.writeInt(n);
                        }
                        catch (Throwable throwable) {
                            break block15;
                        }
                        try {
                            parcel.writeInt(n2);
                            if (location != null) {
                                parcel.writeInt(1);
                                location.writeToParcel(parcel, 0);
                                break block14;
                            }
                            parcel.writeInt(0);
                        }
                        catch (Throwable throwable) {}
                    }
                    try {
                        parcel.writeLong(l);
                    }
                    catch (Throwable throwable) {
                        break block15;
                    }
                    try {
                        parcel.writeInt(n3);
                    }
                    catch (Throwable throwable) {
                        break block15;
                    }
                    try {
                        if (!this.mRemote.transact(1, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                            Stub.getDefaultImpl().onGeofenceTransition(n, n2, location, l, n3);
                            parcel.recycle();
                            return;
                        }
                        parcel.recycle();
                        return;
                    }
                    catch (Throwable throwable) {}
                    break block15;
                    catch (Throwable throwable) {
                        // empty catch block
                    }
                }
                parcel.recycle();
                throw var3_10;
            }
        }

    }

}

