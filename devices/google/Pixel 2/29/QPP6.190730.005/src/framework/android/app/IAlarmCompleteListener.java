/*
 * Decompiled with CFR 0.145.
 */
package android.app;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IAlarmCompleteListener
extends IInterface {
    public void alarmComplete(IBinder var1) throws RemoteException;

    public static class Default
    implements IAlarmCompleteListener {
        @Override
        public void alarmComplete(IBinder iBinder) throws RemoteException {
        }

        @Override
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub
    extends Binder
    implements IAlarmCompleteListener {
        private static final String DESCRIPTOR = "android.app.IAlarmCompleteListener";
        static final int TRANSACTION_alarmComplete = 1;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IAlarmCompleteListener asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IAlarmCompleteListener) {
                return (IAlarmCompleteListener)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IAlarmCompleteListener getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            if (n != 1) {
                return null;
            }
            return "alarmComplete";
        }

        public static boolean setDefaultImpl(IAlarmCompleteListener iAlarmCompleteListener) {
            if (Proxy.sDefaultImpl == null && iAlarmCompleteListener != null) {
                Proxy.sDefaultImpl = iAlarmCompleteListener;
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
                if (n != 1598968902) {
                    return super.onTransact(n, parcel, parcel2, n2);
                }
                parcel2.writeString(DESCRIPTOR);
                return true;
            }
            parcel.enforceInterface(DESCRIPTOR);
            this.alarmComplete(parcel.readStrongBinder());
            parcel2.writeNoException();
            return true;
        }

        private static class Proxy
        implements IAlarmCompleteListener {
            public static IAlarmCompleteListener sDefaultImpl;
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override
            public void alarmComplete(IBinder iBinder) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeStrongBinder(iBinder);
                    if (!this.mRemote.transact(1, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().alarmComplete(iBinder);
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

            @Override
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }
        }

    }

}

