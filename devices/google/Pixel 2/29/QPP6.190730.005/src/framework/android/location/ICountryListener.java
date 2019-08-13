/*
 * Decompiled with CFR 0.145.
 */
package android.location;

import android.location.Country;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;

public interface ICountryListener
extends IInterface {
    public void onCountryDetected(Country var1) throws RemoteException;

    public static class Default
    implements ICountryListener {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void onCountryDetected(Country country) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements ICountryListener {
        private static final String DESCRIPTOR = "android.location.ICountryListener";
        static final int TRANSACTION_onCountryDetected = 1;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static ICountryListener asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof ICountryListener) {
                return (ICountryListener)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static ICountryListener getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            if (n != 1) {
                return null;
            }
            return "onCountryDetected";
        }

        public static boolean setDefaultImpl(ICountryListener iCountryListener) {
            if (Proxy.sDefaultImpl == null && iCountryListener != null) {
                Proxy.sDefaultImpl = iCountryListener;
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
            object = ((Parcel)object).readInt() != 0 ? Country.CREATOR.createFromParcel((Parcel)object) : null;
            this.onCountryDetected((Country)object);
            return true;
        }

        private static class Proxy
        implements ICountryListener {
            public static ICountryListener sDefaultImpl;
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
            public void onCountryDetected(Country country) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (country != null) {
                        parcel.writeInt(1);
                        country.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(1, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onCountryDetected(country);
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

