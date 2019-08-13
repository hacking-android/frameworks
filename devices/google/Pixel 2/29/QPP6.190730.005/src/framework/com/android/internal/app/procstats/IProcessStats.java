/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.app.procstats;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.Parcelable;
import android.os.RemoteException;
import java.util.ArrayList;
import java.util.List;

public interface IProcessStats
extends IInterface {
    public long getCommittedStats(long var1, int var3, boolean var4, List<ParcelFileDescriptor> var5) throws RemoteException;

    public int getCurrentMemoryState() throws RemoteException;

    public byte[] getCurrentStats(List<ParcelFileDescriptor> var1) throws RemoteException;

    public ParcelFileDescriptor getStatsOverTime(long var1) throws RemoteException;

    public static class Default
    implements IProcessStats {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public long getCommittedStats(long l, int n, boolean bl, List<ParcelFileDescriptor> list) throws RemoteException {
            return 0L;
        }

        @Override
        public int getCurrentMemoryState() throws RemoteException {
            return 0;
        }

        @Override
        public byte[] getCurrentStats(List<ParcelFileDescriptor> list) throws RemoteException {
            return null;
        }

        @Override
        public ParcelFileDescriptor getStatsOverTime(long l) throws RemoteException {
            return null;
        }
    }

    public static abstract class Stub
    extends Binder
    implements IProcessStats {
        private static final String DESCRIPTOR = "com.android.internal.app.procstats.IProcessStats";
        static final int TRANSACTION_getCommittedStats = 4;
        static final int TRANSACTION_getCurrentMemoryState = 3;
        static final int TRANSACTION_getCurrentStats = 1;
        static final int TRANSACTION_getStatsOverTime = 2;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IProcessStats asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IProcessStats) {
                return (IProcessStats)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IProcessStats getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            if (n != 1) {
                if (n != 2) {
                    if (n != 3) {
                        if (n != 4) {
                            return null;
                        }
                        return "getCommittedStats";
                    }
                    return "getCurrentMemoryState";
                }
                return "getStatsOverTime";
            }
            return "getCurrentStats";
        }

        public static boolean setDefaultImpl(IProcessStats iProcessStats) {
            if (Proxy.sDefaultImpl == null && iProcessStats != null) {
                Proxy.sDefaultImpl = iProcessStats;
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
                    if (n != 3) {
                        if (n != 4) {
                            if (n != 1598968902) {
                                return super.onTransact(n, (Parcel)object, parcel, n2);
                            }
                            parcel.writeString(DESCRIPTOR);
                            return true;
                        }
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        long l = ((Parcel)object).readLong();
                        n = ((Parcel)object).readInt();
                        boolean bl = ((Parcel)object).readInt() != 0;
                        l = this.getCommittedStats(l, n, bl, new ArrayList<ParcelFileDescriptor>());
                        parcel.writeNoException();
                        parcel.writeLong(l);
                        return true;
                    }
                    ((Parcel)object).enforceInterface(DESCRIPTOR);
                    n = this.getCurrentMemoryState();
                    parcel.writeNoException();
                    parcel.writeInt(n);
                    return true;
                }
                ((Parcel)object).enforceInterface(DESCRIPTOR);
                object = this.getStatsOverTime(((Parcel)object).readLong());
                parcel.writeNoException();
                if (object != null) {
                    parcel.writeInt(1);
                    ((ParcelFileDescriptor)object).writeToParcel(parcel, 1);
                } else {
                    parcel.writeInt(0);
                }
                return true;
            }
            ((Parcel)object).enforceInterface(DESCRIPTOR);
            object = this.getCurrentStats(new ArrayList<ParcelFileDescriptor>());
            parcel.writeNoException();
            parcel.writeByteArray((byte[])object);
            return true;
        }

        private static class Proxy
        implements IProcessStats {
            public static IProcessStats sDefaultImpl;
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override
            public IBinder asBinder() {
                return this.mRemote;
            }

            @Override
            public long getCommittedStats(long l, int n, boolean bl, List<ParcelFileDescriptor> list) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                parcel.writeLong(l);
                parcel.writeInt(n);
                int n2 = bl ? 1 : 0;
                try {
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(4, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        l = Stub.getDefaultImpl().getCommittedStats(l, n, bl, list);
                        return l;
                    }
                    parcel2.readException();
                    l = parcel2.readLong();
                    return l;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public int getCurrentMemoryState() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(3, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        int n = Stub.getDefaultImpl().getCurrentMemoryState();
                        return n;
                    }
                    parcel2.readException();
                    int n = parcel2.readInt();
                    return n;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public byte[] getCurrentStats(List<ParcelFileDescriptor> arrby) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(1, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        arrby = Stub.getDefaultImpl().getCurrentStats((List<ParcelFileDescriptor>)arrby);
                        return arrby;
                    }
                    parcel2.readException();
                    arrby = parcel2.createByteArray();
                    return arrby;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            @Override
            public ParcelFileDescriptor getStatsOverTime(long l) throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                block3 : {
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    try {
                        parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel.writeLong(l);
                        if (this.mRemote.transact(2, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block3;
                        ParcelFileDescriptor parcelFileDescriptor = Stub.getDefaultImpl().getStatsOverTime(l);
                        parcel2.recycle();
                        parcel.recycle();
                        return parcelFileDescriptor;
                    }
                    catch (Throwable throwable) {
                        parcel2.recycle();
                        parcel.recycle();
                        throw throwable;
                    }
                }
                parcel2.readException();
                ParcelFileDescriptor parcelFileDescriptor = parcel2.readInt() != 0 ? ParcelFileDescriptor.CREATOR.createFromParcel(parcel2) : null;
                parcel2.recycle();
                parcel.recycle();
                return parcelFileDescriptor;
            }
        }

    }

}

