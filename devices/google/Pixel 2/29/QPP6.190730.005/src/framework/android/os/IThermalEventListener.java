/*
 * Decompiled with CFR 0.145.
 */
package android.os;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import android.os.Temperature;

public interface IThermalEventListener
extends IInterface {
    public void notifyThrottling(Temperature var1) throws RemoteException;

    public static class Default
    implements IThermalEventListener {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void notifyThrottling(Temperature temperature) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IThermalEventListener {
        private static final String DESCRIPTOR = "android.os.IThermalEventListener";
        static final int TRANSACTION_notifyThrottling = 1;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IThermalEventListener asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IThermalEventListener) {
                return (IThermalEventListener)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IThermalEventListener getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            if (n != 1) {
                return null;
            }
            return "notifyThrottling";
        }

        public static boolean setDefaultImpl(IThermalEventListener iThermalEventListener) {
            if (Proxy.sDefaultImpl == null && iThermalEventListener != null) {
                Proxy.sDefaultImpl = iThermalEventListener;
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
            object = ((Parcel)object).readInt() != 0 ? Temperature.CREATOR.createFromParcel((Parcel)object) : null;
            this.notifyThrottling((Temperature)object);
            return true;
        }

        private static class Proxy
        implements IThermalEventListener {
            public static IThermalEventListener sDefaultImpl;
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
            public void notifyThrottling(Temperature temperature) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (temperature != null) {
                        parcel.writeInt(1);
                        temperature.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(1, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().notifyThrottling(temperature);
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

