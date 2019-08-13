/*
 * Decompiled with CFR 0.145.
 */
package android.app.slice;

import android.app.slice.Slice;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;

public interface ISliceListener
extends IInterface {
    public void onSliceUpdated(Slice var1) throws RemoteException;

    public static class Default
    implements ISliceListener {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void onSliceUpdated(Slice slice) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements ISliceListener {
        private static final String DESCRIPTOR = "android.app.slice.ISliceListener";
        static final int TRANSACTION_onSliceUpdated = 1;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static ISliceListener asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof ISliceListener) {
                return (ISliceListener)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static ISliceListener getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            if (n != 1) {
                return null;
            }
            return "onSliceUpdated";
        }

        public static boolean setDefaultImpl(ISliceListener iSliceListener) {
            if (Proxy.sDefaultImpl == null && iSliceListener != null) {
                Proxy.sDefaultImpl = iSliceListener;
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
            object = ((Parcel)object).readInt() != 0 ? Slice.CREATOR.createFromParcel((Parcel)object) : null;
            this.onSliceUpdated((Slice)object);
            return true;
        }

        private static class Proxy
        implements ISliceListener {
            public static ISliceListener sDefaultImpl;
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
            public void onSliceUpdated(Slice slice) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (slice != null) {
                        parcel.writeInt(1);
                        slice.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(1, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onSliceUpdated(slice);
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

