/*
 * Decompiled with CFR 0.145.
 */
package android.app.usage;

import android.app.usage.CacheQuotaHint;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteCallback;
import android.os.RemoteException;
import java.util.ArrayList;
import java.util.List;

public interface ICacheQuotaService
extends IInterface {
    public void computeCacheQuotaHints(RemoteCallback var1, List<CacheQuotaHint> var2) throws RemoteException;

    public static class Default
    implements ICacheQuotaService {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void computeCacheQuotaHints(RemoteCallback remoteCallback, List<CacheQuotaHint> list) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements ICacheQuotaService {
        private static final String DESCRIPTOR = "android.app.usage.ICacheQuotaService";
        static final int TRANSACTION_computeCacheQuotaHints = 1;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static ICacheQuotaService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof ICacheQuotaService) {
                return (ICacheQuotaService)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static ICacheQuotaService getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            if (n != 1) {
                return null;
            }
            return "computeCacheQuotaHints";
        }

        public static boolean setDefaultImpl(ICacheQuotaService iCacheQuotaService) {
            if (Proxy.sDefaultImpl == null && iCacheQuotaService != null) {
                Proxy.sDefaultImpl = iCacheQuotaService;
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
            object = parcel.readInt() != 0 ? RemoteCallback.CREATOR.createFromParcel(parcel) : null;
            this.computeCacheQuotaHints((RemoteCallback)object, parcel.createTypedArrayList(CacheQuotaHint.CREATOR));
            return true;
        }

        private static class Proxy
        implements ICacheQuotaService {
            public static ICacheQuotaService sDefaultImpl;
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override
            public IBinder asBinder() {
                return this.mRemote;
            }

            @Override
            public void computeCacheQuotaHints(RemoteCallback remoteCallback, List<CacheQuotaHint> list) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (remoteCallback != null) {
                        parcel.writeInt(1);
                        remoteCallback.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeTypedList(list);
                    if (!this.mRemote.transact(1, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().computeCacheQuotaHints(remoteCallback, list);
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
        }

    }

}

