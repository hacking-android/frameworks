/*
 * Decompiled with CFR 0.145.
 */
package android.app.trust;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IStrongAuthTracker
extends IInterface {
    public void onStrongAuthRequiredChanged(int var1, int var2) throws RemoteException;

    public static class Default
    implements IStrongAuthTracker {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void onStrongAuthRequiredChanged(int n, int n2) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IStrongAuthTracker {
        private static final String DESCRIPTOR = "android.app.trust.IStrongAuthTracker";
        static final int TRANSACTION_onStrongAuthRequiredChanged = 1;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IStrongAuthTracker asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IStrongAuthTracker) {
                return (IStrongAuthTracker)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IStrongAuthTracker getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            if (n != 1) {
                return null;
            }
            return "onStrongAuthRequiredChanged";
        }

        public static boolean setDefaultImpl(IStrongAuthTracker iStrongAuthTracker) {
            if (Proxy.sDefaultImpl == null && iStrongAuthTracker != null) {
                Proxy.sDefaultImpl = iStrongAuthTracker;
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
            this.onStrongAuthRequiredChanged(parcel.readInt(), parcel.readInt());
            return true;
        }

        private static class Proxy
        implements IStrongAuthTracker {
            public static IStrongAuthTracker sDefaultImpl;
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
            public void onStrongAuthRequiredChanged(int n, int n2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(1, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onStrongAuthRequiredChanged(n, n2);
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

