/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.input;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface ITabletModeChangedListener
extends IInterface {
    public void onTabletModeChanged(long var1, boolean var3) throws RemoteException;

    public static class Default
    implements ITabletModeChangedListener {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void onTabletModeChanged(long l, boolean bl) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements ITabletModeChangedListener {
        private static final String DESCRIPTOR = "android.hardware.input.ITabletModeChangedListener";
        static final int TRANSACTION_onTabletModeChanged = 1;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static ITabletModeChangedListener asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof ITabletModeChangedListener) {
                return (ITabletModeChangedListener)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static ITabletModeChangedListener getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            if (n != 1) {
                return null;
            }
            return "onTabletModeChanged";
        }

        public static boolean setDefaultImpl(ITabletModeChangedListener iTabletModeChangedListener) {
            if (Proxy.sDefaultImpl == null && iTabletModeChangedListener != null) {
                Proxy.sDefaultImpl = iTabletModeChangedListener;
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
            long l = parcel.readLong();
            boolean bl = parcel.readInt() != 0;
            this.onTabletModeChanged(l, bl);
            return true;
        }

        private static class Proxy
        implements ITabletModeChangedListener {
            public static ITabletModeChangedListener sDefaultImpl;
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
            public void onTabletModeChanged(long l, boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                parcel.writeLong(l);
                int n = bl ? 1 : 0;
                try {
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(1, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onTabletModeChanged(l, bl);
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

