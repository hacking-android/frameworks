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
import android.os.StatsLogEventWrapper;

public interface IStatsPullerCallback
extends IInterface {
    public StatsLogEventWrapper[] pullData(int var1, long var2, long var4) throws RemoteException;

    public static class Default
    implements IStatsPullerCallback {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public StatsLogEventWrapper[] pullData(int n, long l, long l2) throws RemoteException {
            return null;
        }
    }

    public static abstract class Stub
    extends Binder
    implements IStatsPullerCallback {
        private static final String DESCRIPTOR = "android.os.IStatsPullerCallback";
        static final int TRANSACTION_pullData = 1;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IStatsPullerCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IStatsPullerCallback) {
                return (IStatsPullerCallback)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IStatsPullerCallback getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            if (n != 1) {
                return null;
            }
            return "pullData";
        }

        public static boolean setDefaultImpl(IStatsPullerCallback iStatsPullerCallback) {
            if (Proxy.sDefaultImpl == null && iStatsPullerCallback != null) {
                Proxy.sDefaultImpl = iStatsPullerCallback;
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
        public boolean onTransact(int n, Parcel arrparcelable, Parcel parcel, int n2) throws RemoteException {
            if (n != 1) {
                if (n != 1598968902) {
                    return super.onTransact(n, (Parcel)arrparcelable, parcel, n2);
                }
                parcel.writeString(DESCRIPTOR);
                return true;
            }
            arrparcelable.enforceInterface(DESCRIPTOR);
            arrparcelable = this.pullData(arrparcelable.readInt(), arrparcelable.readLong(), arrparcelable.readLong());
            parcel.writeNoException();
            parcel.writeTypedArray(arrparcelable, 1);
            return true;
        }

        private static class Proxy
        implements IStatsPullerCallback {
            public static IStatsPullerCallback sDefaultImpl;
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
            public StatsLogEventWrapper[] pullData(int n, long l, long l2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeLong(l);
                    parcel.writeLong(l2);
                    if (!this.mRemote.transact(1, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        StatsLogEventWrapper[] arrstatsLogEventWrapper = Stub.getDefaultImpl().pullData(n, l, l2);
                        return arrstatsLogEventWrapper;
                    }
                    parcel2.readException();
                    StatsLogEventWrapper[] arrstatsLogEventWrapper = parcel2.createTypedArray(StatsLogEventWrapper.CREATOR);
                    return arrstatsLogEventWrapper;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }
        }

    }

}

