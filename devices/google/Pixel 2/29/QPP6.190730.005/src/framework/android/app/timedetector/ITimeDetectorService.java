/*
 * Decompiled with CFR 0.145.
 */
package android.app.timedetector;

import android.app.timedetector.TimeSignal;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;

public interface ITimeDetectorService
extends IInterface {
    public void suggestTime(TimeSignal var1) throws RemoteException;

    public static class Default
    implements ITimeDetectorService {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void suggestTime(TimeSignal timeSignal) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements ITimeDetectorService {
        private static final String DESCRIPTOR = "android.app.timedetector.ITimeDetectorService";
        static final int TRANSACTION_suggestTime = 1;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static ITimeDetectorService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof ITimeDetectorService) {
                return (ITimeDetectorService)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static ITimeDetectorService getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            if (n != 1) {
                return null;
            }
            return "suggestTime";
        }

        public static boolean setDefaultImpl(ITimeDetectorService iTimeDetectorService) {
            if (Proxy.sDefaultImpl == null && iTimeDetectorService != null) {
                Proxy.sDefaultImpl = iTimeDetectorService;
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
            object = ((Parcel)object).readInt() != 0 ? TimeSignal.CREATOR.createFromParcel((Parcel)object) : null;
            this.suggestTime((TimeSignal)object);
            parcel.writeNoException();
            return true;
        }

        private static class Proxy
        implements ITimeDetectorService {
            public static ITimeDetectorService sDefaultImpl;
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
            public void suggestTime(TimeSignal timeSignal) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (timeSignal != null) {
                        parcel.writeInt(1);
                        timeSignal.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(1, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().suggestTime(timeSignal);
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

