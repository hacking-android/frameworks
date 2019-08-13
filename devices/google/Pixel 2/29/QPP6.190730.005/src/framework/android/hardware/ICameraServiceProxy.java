/*
 * Decompiled with CFR 0.145.
 */
package android.hardware;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface ICameraServiceProxy
extends IInterface {
    public static final int CAMERA_API_LEVEL_1 = 1;
    public static final int CAMERA_API_LEVEL_2 = 2;
    public static final int CAMERA_FACING_BACK = 0;
    public static final int CAMERA_FACING_EXTERNAL = 2;
    public static final int CAMERA_FACING_FRONT = 1;
    public static final int CAMERA_STATE_ACTIVE = 1;
    public static final int CAMERA_STATE_CLOSED = 3;
    public static final int CAMERA_STATE_IDLE = 2;
    public static final int CAMERA_STATE_OPEN = 0;

    public void notifyCameraState(String var1, int var2, int var3, String var4, int var5) throws RemoteException;

    public void pingForUserUpdate() throws RemoteException;

    public static class Default
    implements ICameraServiceProxy {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void notifyCameraState(String string2, int n, int n2, String string3, int n3) throws RemoteException {
        }

        @Override
        public void pingForUserUpdate() throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements ICameraServiceProxy {
        private static final String DESCRIPTOR = "android.hardware.ICameraServiceProxy";
        static final int TRANSACTION_notifyCameraState = 2;
        static final int TRANSACTION_pingForUserUpdate = 1;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static ICameraServiceProxy asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof ICameraServiceProxy) {
                return (ICameraServiceProxy)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static ICameraServiceProxy getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            if (n != 1) {
                if (n != 2) {
                    return null;
                }
                return "notifyCameraState";
            }
            return "pingForUserUpdate";
        }

        public static boolean setDefaultImpl(ICameraServiceProxy iCameraServiceProxy) {
            if (Proxy.sDefaultImpl == null && iCameraServiceProxy != null) {
                Proxy.sDefaultImpl = iCameraServiceProxy;
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
                    if (n != 1598968902) {
                        return super.onTransact(n, parcel, parcel2, n2);
                    }
                    parcel2.writeString(DESCRIPTOR);
                    return true;
                }
                parcel.enforceInterface(DESCRIPTOR);
                this.notifyCameraState(parcel.readString(), parcel.readInt(), parcel.readInt(), parcel.readString(), parcel.readInt());
                return true;
            }
            parcel.enforceInterface(DESCRIPTOR);
            this.pingForUserUpdate();
            return true;
        }

        private static class Proxy
        implements ICameraServiceProxy {
            public static ICameraServiceProxy sDefaultImpl;
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
            public void notifyCameraState(String string2, int n, int n2, String string3, int n3) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    parcel.writeString(string3);
                    parcel.writeInt(n3);
                    if (!this.mRemote.transact(2, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().notifyCameraState(string2, n, n2, string3, n3);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void pingForUserUpdate() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(1, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().pingForUserUpdate();
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

