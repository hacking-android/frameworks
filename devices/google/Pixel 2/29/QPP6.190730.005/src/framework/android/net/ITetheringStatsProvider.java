/*
 * Decompiled with CFR 0.145.
 */
package android.net;

import android.net.NetworkStats;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;

public interface ITetheringStatsProvider
extends IInterface {
    public static final int QUOTA_UNLIMITED = -1;

    public NetworkStats getTetherStats(int var1) throws RemoteException;

    public void setInterfaceQuota(String var1, long var2) throws RemoteException;

    public static class Default
    implements ITetheringStatsProvider {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public NetworkStats getTetherStats(int n) throws RemoteException {
            return null;
        }

        @Override
        public void setInterfaceQuota(String string2, long l) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements ITetheringStatsProvider {
        private static final String DESCRIPTOR = "android.net.ITetheringStatsProvider";
        static final int TRANSACTION_getTetherStats = 1;
        static final int TRANSACTION_setInterfaceQuota = 2;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static ITetheringStatsProvider asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof ITetheringStatsProvider) {
                return (ITetheringStatsProvider)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static ITetheringStatsProvider getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            if (n != 1) {
                if (n != 2) {
                    return null;
                }
                return "setInterfaceQuota";
            }
            return "getTetherStats";
        }

        public static boolean setDefaultImpl(ITetheringStatsProvider iTetheringStatsProvider) {
            if (Proxy.sDefaultImpl == null && iTetheringStatsProvider != null) {
                Proxy.sDefaultImpl = iTetheringStatsProvider;
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
                if (n != 2) {
                    if (n != 1598968902) {
                        return super.onTransact(n, (Parcel)object, parcel, n2);
                    }
                    parcel.writeString(DESCRIPTOR);
                    return true;
                }
                ((Parcel)object).enforceInterface(DESCRIPTOR);
                this.setInterfaceQuota(((Parcel)object).readString(), ((Parcel)object).readLong());
                parcel.writeNoException();
                return true;
            }
            ((Parcel)object).enforceInterface(DESCRIPTOR);
            object = this.getTetherStats(((Parcel)object).readInt());
            parcel.writeNoException();
            if (object != null) {
                parcel.writeInt(1);
                ((NetworkStats)object).writeToParcel(parcel, 1);
            } else {
                parcel.writeInt(0);
            }
            return true;
        }

        private static class Proxy
        implements ITetheringStatsProvider {
            public static ITetheringStatsProvider sDefaultImpl;
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
            public NetworkStats getTetherStats(int n) throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                block3 : {
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    try {
                        parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel.writeInt(n);
                        if (this.mRemote.transact(1, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block3;
                        NetworkStats networkStats = Stub.getDefaultImpl().getTetherStats(n);
                        parcel2.recycle();
                        parcel.recycle();
                        return networkStats;
                    }
                    catch (Throwable throwable) {
                        parcel2.recycle();
                        parcel.recycle();
                        throw throwable;
                    }
                }
                parcel2.readException();
                NetworkStats networkStats = parcel2.readInt() != 0 ? NetworkStats.CREATOR.createFromParcel(parcel2) : null;
                parcel2.recycle();
                parcel.recycle();
                return networkStats;
            }

            @Override
            public void setInterfaceQuota(String string2, long l) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    parcel.writeLong(l);
                    if (!this.mRemote.transact(2, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setInterfaceQuota(string2, l);
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
        }

    }

}

