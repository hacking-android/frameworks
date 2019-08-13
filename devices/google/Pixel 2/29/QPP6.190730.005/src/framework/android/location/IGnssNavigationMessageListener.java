/*
 * Decompiled with CFR 0.145.
 */
package android.location;

import android.location.GnssNavigationMessage;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;

public interface IGnssNavigationMessageListener
extends IInterface {
    public void onGnssNavigationMessageReceived(GnssNavigationMessage var1) throws RemoteException;

    public void onStatusChanged(int var1) throws RemoteException;

    public static class Default
    implements IGnssNavigationMessageListener {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void onGnssNavigationMessageReceived(GnssNavigationMessage gnssNavigationMessage) throws RemoteException {
        }

        @Override
        public void onStatusChanged(int n) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IGnssNavigationMessageListener {
        private static final String DESCRIPTOR = "android.location.IGnssNavigationMessageListener";
        static final int TRANSACTION_onGnssNavigationMessageReceived = 1;
        static final int TRANSACTION_onStatusChanged = 2;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IGnssNavigationMessageListener asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IGnssNavigationMessageListener) {
                return (IGnssNavigationMessageListener)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IGnssNavigationMessageListener getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            if (n != 1) {
                if (n != 2) {
                    return null;
                }
                return "onStatusChanged";
            }
            return "onGnssNavigationMessageReceived";
        }

        public static boolean setDefaultImpl(IGnssNavigationMessageListener iGnssNavigationMessageListener) {
            if (Proxy.sDefaultImpl == null && iGnssNavigationMessageListener != null) {
                Proxy.sDefaultImpl = iGnssNavigationMessageListener;
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
                    if (n != 1598968902) {
                        return super.onTransact(n, (Parcel)object, parcel, n2);
                    }
                    parcel.writeString(DESCRIPTOR);
                    return true;
                }
                ((Parcel)object).enforceInterface(DESCRIPTOR);
                this.onStatusChanged(((Parcel)object).readInt());
                return true;
            }
            ((Parcel)object).enforceInterface(DESCRIPTOR);
            object = ((Parcel)object).readInt() != 0 ? GnssNavigationMessage.CREATOR.createFromParcel((Parcel)object) : null;
            this.onGnssNavigationMessageReceived((GnssNavigationMessage)object);
            return true;
        }

        private static class Proxy
        implements IGnssNavigationMessageListener {
            public static IGnssNavigationMessageListener sDefaultImpl;
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
            public void onGnssNavigationMessageReceived(GnssNavigationMessage gnssNavigationMessage) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (gnssNavigationMessage != null) {
                        parcel.writeInt(1);
                        gnssNavigationMessage.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(1, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onGnssNavigationMessageReceived(gnssNavigationMessage);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onStatusChanged(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(2, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onStatusChanged(n);
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

