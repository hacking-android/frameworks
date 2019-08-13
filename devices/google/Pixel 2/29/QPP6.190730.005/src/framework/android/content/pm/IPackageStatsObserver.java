/*
 * Decompiled with CFR 0.145.
 */
package android.content.pm;

import android.annotation.UnsupportedAppUsage;
import android.content.pm.PackageStats;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;

public interface IPackageStatsObserver
extends IInterface {
    @UnsupportedAppUsage
    public void onGetStatsCompleted(PackageStats var1, boolean var2) throws RemoteException;

    public static class Default
    implements IPackageStatsObserver {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void onGetStatsCompleted(PackageStats packageStats, boolean bl) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IPackageStatsObserver {
        private static final String DESCRIPTOR = "android.content.pm.IPackageStatsObserver";
        static final int TRANSACTION_onGetStatsCompleted = 1;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IPackageStatsObserver asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IPackageStatsObserver) {
                return (IPackageStatsObserver)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IPackageStatsObserver getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            if (n != 1) {
                return null;
            }
            return "onGetStatsCompleted";
        }

        public static boolean setDefaultImpl(IPackageStatsObserver iPackageStatsObserver) {
            if (Proxy.sDefaultImpl == null && iPackageStatsObserver != null) {
                Proxy.sDefaultImpl = iPackageStatsObserver;
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
            object = parcel.readInt() != 0 ? PackageStats.CREATOR.createFromParcel(parcel) : null;
            boolean bl = parcel.readInt() != 0;
            this.onGetStatsCompleted((PackageStats)object, bl);
            return true;
        }

        private static class Proxy
        implements IPackageStatsObserver {
            public static IPackageStatsObserver sDefaultImpl;
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

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void onGetStatsCompleted(PackageStats packageStats, boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    int n = 0;
                    if (packageStats != null) {
                        parcel.writeInt(1);
                        packageStats.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (bl) {
                        n = 1;
                    }
                    parcel.writeInt(n);
                    if (this.mRemote.transact(1, parcel, null, 1)) return;
                    if (Stub.getDefaultImpl() == null) return;
                    Stub.getDefaultImpl().onGetStatsCompleted(packageStats, bl);
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }
        }

    }

}

