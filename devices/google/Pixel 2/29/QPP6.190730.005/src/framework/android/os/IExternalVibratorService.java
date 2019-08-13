/*
 * Decompiled with CFR 0.145.
 */
package android.os;

import android.os.Binder;
import android.os.ExternalVibration;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;

public interface IExternalVibratorService
extends IInterface {
    public static final int SCALE_HIGH = 1;
    public static final int SCALE_LOW = -1;
    public static final int SCALE_MUTE = -100;
    public static final int SCALE_NONE = 0;
    public static final int SCALE_VERY_HIGH = 2;
    public static final int SCALE_VERY_LOW = -2;

    public int onExternalVibrationStart(ExternalVibration var1) throws RemoteException;

    public void onExternalVibrationStop(ExternalVibration var1) throws RemoteException;

    public static class Default
    implements IExternalVibratorService {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public int onExternalVibrationStart(ExternalVibration externalVibration) throws RemoteException {
            return 0;
        }

        @Override
        public void onExternalVibrationStop(ExternalVibration externalVibration) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IExternalVibratorService {
        private static final String DESCRIPTOR = "android.os.IExternalVibratorService";
        static final int TRANSACTION_onExternalVibrationStart = 1;
        static final int TRANSACTION_onExternalVibrationStop = 2;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IExternalVibratorService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IExternalVibratorService) {
                return (IExternalVibratorService)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IExternalVibratorService getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            if (n != 1) {
                if (n != 2) {
                    return null;
                }
                return "onExternalVibrationStop";
            }
            return "onExternalVibrationStart";
        }

        public static boolean setDefaultImpl(IExternalVibratorService iExternalVibratorService) {
            if (Proxy.sDefaultImpl == null && iExternalVibratorService != null) {
                Proxy.sDefaultImpl = iExternalVibratorService;
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
                object = ((Parcel)object).readInt() != 0 ? ExternalVibration.CREATOR.createFromParcel((Parcel)object) : null;
                this.onExternalVibrationStop((ExternalVibration)object);
                parcel.writeNoException();
                return true;
            }
            ((Parcel)object).enforceInterface(DESCRIPTOR);
            object = ((Parcel)object).readInt() != 0 ? ExternalVibration.CREATOR.createFromParcel((Parcel)object) : null;
            n = this.onExternalVibrationStart((ExternalVibration)object);
            parcel.writeNoException();
            parcel.writeInt(n);
            return true;
        }

        private static class Proxy
        implements IExternalVibratorService {
            public static IExternalVibratorService sDefaultImpl;
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
            public int onExternalVibrationStart(ExternalVibration externalVibration) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (externalVibration != null) {
                        parcel.writeInt(1);
                        externalVibration.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(1, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        int n = Stub.getDefaultImpl().onExternalVibrationStart(externalVibration);
                        return n;
                    }
                    parcel2.readException();
                    int n = parcel2.readInt();
                    return n;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void onExternalVibrationStop(ExternalVibration externalVibration) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (externalVibration != null) {
                        parcel.writeInt(1);
                        externalVibration.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(2, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onExternalVibrationStop(externalVibration);
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
        }

    }

}

