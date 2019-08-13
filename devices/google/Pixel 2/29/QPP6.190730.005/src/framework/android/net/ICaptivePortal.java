/*
 * Decompiled with CFR 0.145.
 */
package android.net;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface ICaptivePortal
extends IInterface {
    public void appResponse(int var1) throws RemoteException;

    public void logEvent(int var1, String var2) throws RemoteException;

    public static class Default
    implements ICaptivePortal {
        @Override
        public void appResponse(int n) throws RemoteException {
        }

        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void logEvent(int n, String string2) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements ICaptivePortal {
        private static final String DESCRIPTOR = "android.net.ICaptivePortal";
        static final int TRANSACTION_appResponse = 1;
        static final int TRANSACTION_logEvent = 2;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static ICaptivePortal asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof ICaptivePortal) {
                return (ICaptivePortal)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static ICaptivePortal getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            if (n != 1) {
                if (n != 2) {
                    return null;
                }
                return "logEvent";
            }
            return "appResponse";
        }

        public static boolean setDefaultImpl(ICaptivePortal iCaptivePortal) {
            if (Proxy.sDefaultImpl == null && iCaptivePortal != null) {
                Proxy.sDefaultImpl = iCaptivePortal;
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
                this.logEvent(parcel.readInt(), parcel.readString());
                return true;
            }
            parcel.enforceInterface(DESCRIPTOR);
            this.appResponse(parcel.readInt());
            return true;
        }

        private static class Proxy
        implements ICaptivePortal {
            public static ICaptivePortal sDefaultImpl;
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override
            public void appResponse(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(1, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().appResponse(n);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
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
            public void logEvent(int n, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(2, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().logEvent(n, string2);
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

