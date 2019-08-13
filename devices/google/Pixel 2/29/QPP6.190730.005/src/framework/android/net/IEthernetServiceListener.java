/*
 * Decompiled with CFR 0.145.
 */
package android.net;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IEthernetServiceListener
extends IInterface {
    public void onAvailabilityChanged(String var1, boolean var2) throws RemoteException;

    public static class Default
    implements IEthernetServiceListener {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void onAvailabilityChanged(String string2, boolean bl) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IEthernetServiceListener {
        private static final String DESCRIPTOR = "android.net.IEthernetServiceListener";
        static final int TRANSACTION_onAvailabilityChanged = 1;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IEthernetServiceListener asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IEthernetServiceListener) {
                return (IEthernetServiceListener)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IEthernetServiceListener getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            if (n != 1) {
                return null;
            }
            return "onAvailabilityChanged";
        }

        public static boolean setDefaultImpl(IEthernetServiceListener iEthernetServiceListener) {
            if (Proxy.sDefaultImpl == null && iEthernetServiceListener != null) {
                Proxy.sDefaultImpl = iEthernetServiceListener;
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
        public boolean onTransact(int n, Parcel parcel, Parcel object, int n2) throws RemoteException {
            if (n != 1) {
                if (n != 1598968902) {
                    return super.onTransact(n, parcel, (Parcel)object, n2);
                }
                ((Parcel)object).writeString(DESCRIPTOR);
                return true;
            }
            parcel.enforceInterface(DESCRIPTOR);
            object = parcel.readString();
            boolean bl = parcel.readInt() != 0;
            this.onAvailabilityChanged((String)object, bl);
            return true;
        }

        private static class Proxy
        implements IEthernetServiceListener {
            public static IEthernetServiceListener sDefaultImpl;
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
            public void onAvailabilityChanged(String string2, boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                parcel.writeString(string2);
                int n = bl ? 1 : 0;
                try {
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(1, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onAvailabilityChanged(string2, bl);
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

