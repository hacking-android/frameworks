/*
 * Decompiled with CFR 0.145.
 */
package android.service.carrier;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import android.os.ResultReceiver;
import android.service.carrier.CarrierIdentifier;

public interface ICarrierService
extends IInterface {
    public void getCarrierConfig(CarrierIdentifier var1, ResultReceiver var2) throws RemoteException;

    public static class Default
    implements ICarrierService {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void getCarrierConfig(CarrierIdentifier carrierIdentifier, ResultReceiver resultReceiver) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements ICarrierService {
        private static final String DESCRIPTOR = "android.service.carrier.ICarrierService";
        static final int TRANSACTION_getCarrierConfig = 1;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static ICarrierService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof ICarrierService) {
                return (ICarrierService)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static ICarrierService getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            if (n != 1) {
                return null;
            }
            return "getCarrierConfig";
        }

        public static boolean setDefaultImpl(ICarrierService iCarrierService) {
            if (Proxy.sDefaultImpl == null && iCarrierService != null) {
                Proxy.sDefaultImpl = iCarrierService;
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
        public boolean onTransact(int n, Parcel object, Parcel object2, int n2) throws RemoteException {
            if (n != 1) {
                if (n != 1598968902) {
                    return super.onTransact(n, (Parcel)object, (Parcel)object2, n2);
                }
                ((Parcel)object2).writeString(DESCRIPTOR);
                return true;
            }
            ((Parcel)object).enforceInterface(DESCRIPTOR);
            object2 = ((Parcel)object).readInt() != 0 ? CarrierIdentifier.CREATOR.createFromParcel((Parcel)object) : null;
            object = ((Parcel)object).readInt() != 0 ? ResultReceiver.CREATOR.createFromParcel((Parcel)object) : null;
            this.getCarrierConfig((CarrierIdentifier)object2, (ResultReceiver)object);
            return true;
        }

        private static class Proxy
        implements ICarrierService {
            public static ICarrierService sDefaultImpl;
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override
            public IBinder asBinder() {
                return this.mRemote;
            }

            @Override
            public void getCarrierConfig(CarrierIdentifier carrierIdentifier, ResultReceiver resultReceiver) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (carrierIdentifier != null) {
                        parcel.writeInt(1);
                        carrierIdentifier.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (resultReceiver != null) {
                        parcel.writeInt(1);
                        resultReceiver.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(1, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().getCarrierConfig(carrierIdentifier, resultReceiver);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }
        }

    }

}

