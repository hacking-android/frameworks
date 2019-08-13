/*
 * Decompiled with CFR 0.145.
 */
package android.location;

import android.location.GnssMeasurementsEvent;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;

public interface IGnssMeasurementsListener
extends IInterface {
    public void onGnssMeasurementsReceived(GnssMeasurementsEvent var1) throws RemoteException;

    public void onStatusChanged(int var1) throws RemoteException;

    public static class Default
    implements IGnssMeasurementsListener {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void onGnssMeasurementsReceived(GnssMeasurementsEvent gnssMeasurementsEvent) throws RemoteException {
        }

        @Override
        public void onStatusChanged(int n) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IGnssMeasurementsListener {
        private static final String DESCRIPTOR = "android.location.IGnssMeasurementsListener";
        static final int TRANSACTION_onGnssMeasurementsReceived = 1;
        static final int TRANSACTION_onStatusChanged = 2;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IGnssMeasurementsListener asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IGnssMeasurementsListener) {
                return (IGnssMeasurementsListener)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IGnssMeasurementsListener getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            if (n != 1) {
                if (n != 2) {
                    return null;
                }
                return "onStatusChanged";
            }
            return "onGnssMeasurementsReceived";
        }

        public static boolean setDefaultImpl(IGnssMeasurementsListener iGnssMeasurementsListener) {
            if (Proxy.sDefaultImpl == null && iGnssMeasurementsListener != null) {
                Proxy.sDefaultImpl = iGnssMeasurementsListener;
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
            object = ((Parcel)object).readInt() != 0 ? GnssMeasurementsEvent.CREATOR.createFromParcel((Parcel)object) : null;
            this.onGnssMeasurementsReceived((GnssMeasurementsEvent)object);
            return true;
        }

        private static class Proxy
        implements IGnssMeasurementsListener {
            public static IGnssMeasurementsListener sDefaultImpl;
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
            public void onGnssMeasurementsReceived(GnssMeasurementsEvent gnssMeasurementsEvent) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (gnssMeasurementsEvent != null) {
                        parcel.writeInt(1);
                        gnssMeasurementsEvent.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(1, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onGnssMeasurementsReceived(gnssMeasurementsEvent);
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

