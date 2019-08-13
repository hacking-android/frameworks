/*
 * Decompiled with CFR 0.145.
 */
package android.app;

import android.app.IAlarmCompleteListener;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IAlarmListener
extends IInterface {
    public void doAlarm(IAlarmCompleteListener var1) throws RemoteException;

    public static class Default
    implements IAlarmListener {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void doAlarm(IAlarmCompleteListener iAlarmCompleteListener) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IAlarmListener {
        private static final String DESCRIPTOR = "android.app.IAlarmListener";
        static final int TRANSACTION_doAlarm = 1;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IAlarmListener asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IAlarmListener) {
                return (IAlarmListener)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IAlarmListener getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            if (n != 1) {
                return null;
            }
            return "doAlarm";
        }

        public static boolean setDefaultImpl(IAlarmListener iAlarmListener) {
            if (Proxy.sDefaultImpl == null && iAlarmListener != null) {
                Proxy.sDefaultImpl = iAlarmListener;
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
            this.doAlarm(IAlarmCompleteListener.Stub.asInterface(parcel.readStrongBinder()));
            return true;
        }

        private static class Proxy
        implements IAlarmListener {
            public static IAlarmListener sDefaultImpl;
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override
            public IBinder asBinder() {
                return this.mRemote;
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void doAlarm(IAlarmCompleteListener iAlarmCompleteListener) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iAlarmCompleteListener != null ? iAlarmCompleteListener.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (this.mRemote.transact(1, parcel, null, 1)) return;
                    if (Stub.getDefaultImpl() == null) return;
                    Stub.getDefaultImpl().doAlarm(iAlarmCompleteListener);
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

