/*
 * Decompiled with CFR 0.145.
 */
package android.net;

import android.net.ScoredNetwork;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import java.util.ArrayList;
import java.util.List;

public interface INetworkScoreCache
extends IInterface {
    public void clearScores() throws RemoteException;

    public void updateScores(List<ScoredNetwork> var1) throws RemoteException;

    public static class Default
    implements INetworkScoreCache {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void clearScores() throws RemoteException {
        }

        @Override
        public void updateScores(List<ScoredNetwork> list) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements INetworkScoreCache {
        private static final String DESCRIPTOR = "android.net.INetworkScoreCache";
        static final int TRANSACTION_clearScores = 2;
        static final int TRANSACTION_updateScores = 1;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static INetworkScoreCache asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof INetworkScoreCache) {
                return (INetworkScoreCache)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static INetworkScoreCache getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            if (n != 1) {
                if (n != 2) {
                    return null;
                }
                return "clearScores";
            }
            return "updateScores";
        }

        public static boolean setDefaultImpl(INetworkScoreCache iNetworkScoreCache) {
            if (Proxy.sDefaultImpl == null && iNetworkScoreCache != null) {
                Proxy.sDefaultImpl = iNetworkScoreCache;
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
                if (n != 2) {
                    if (n != 1598968902) {
                        return super.onTransact(n, parcel, parcel2, n2);
                    }
                    parcel2.writeString(DESCRIPTOR);
                    return true;
                }
                parcel.enforceInterface(DESCRIPTOR);
                this.clearScores();
                return true;
            }
            parcel.enforceInterface(DESCRIPTOR);
            this.updateScores(parcel.createTypedArrayList(ScoredNetwork.CREATOR));
            return true;
        }

        private static class Proxy
        implements INetworkScoreCache {
            public static INetworkScoreCache sDefaultImpl;
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override
            public IBinder asBinder() {
                return this.mRemote;
            }

            @Override
            public void clearScores() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(2, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().clearScores();
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

            @Override
            public void updateScores(List<ScoredNetwork> list) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeTypedList(list);
                    if (!this.mRemote.transact(1, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().updateScores(list);
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

