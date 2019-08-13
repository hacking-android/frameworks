/*
 * Decompiled with CFR 0.145.
 */
package android.os;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IProcessInfoService
extends IInterface {
    public void getProcessStatesAndOomScoresFromPids(int[] var1, int[] var2, int[] var3) throws RemoteException;

    public void getProcessStatesFromPids(int[] var1, int[] var2) throws RemoteException;

    public static class Default
    implements IProcessInfoService {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void getProcessStatesAndOomScoresFromPids(int[] arrn, int[] arrn2, int[] arrn3) throws RemoteException {
        }

        @Override
        public void getProcessStatesFromPids(int[] arrn, int[] arrn2) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IProcessInfoService {
        private static final String DESCRIPTOR = "android.os.IProcessInfoService";
        static final int TRANSACTION_getProcessStatesAndOomScoresFromPids = 2;
        static final int TRANSACTION_getProcessStatesFromPids = 1;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IProcessInfoService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IProcessInfoService) {
                return (IProcessInfoService)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IProcessInfoService getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            if (n != 1) {
                if (n != 2) {
                    return null;
                }
                return "getProcessStatesAndOomScoresFromPids";
            }
            return "getProcessStatesFromPids";
        }

        public static boolean setDefaultImpl(IProcessInfoService iProcessInfoService) {
            if (Proxy.sDefaultImpl == null && iProcessInfoService != null) {
                Proxy.sDefaultImpl = iProcessInfoService;
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
                object.enforceInterface(DESCRIPTOR);
                int[] arrn = object.createIntArray();
                n = object.readInt();
                int[] arrn2 = n < 0 ? null : new int[n];
                n = object.readInt();
                object = n < 0 ? null : new int[n];
                this.getProcessStatesAndOomScoresFromPids(arrn, arrn2, (int[])object);
                parcel.writeNoException();
                parcel.writeIntArray(arrn2);
                parcel.writeIntArray((int[])object);
                return true;
            }
            object.enforceInterface(DESCRIPTOR);
            int[] arrn = object.createIntArray();
            n = object.readInt();
            object = n < 0 ? null : new int[n];
            this.getProcessStatesFromPids(arrn, (int[])object);
            parcel.writeNoException();
            parcel.writeIntArray((int[])object);
            return true;
        }

        private static class Proxy
        implements IProcessInfoService {
            public static IProcessInfoService sDefaultImpl;
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
            public void getProcessStatesAndOomScoresFromPids(int[] arrn, int[] arrn2, int[] arrn3) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeIntArray(arrn);
                    if (arrn2 == null) {
                        parcel.writeInt(-1);
                    } else {
                        parcel.writeInt(arrn2.length);
                    }
                    if (arrn3 == null) {
                        parcel.writeInt(-1);
                    } else {
                        parcel.writeInt(arrn3.length);
                    }
                    if (!this.mRemote.transact(2, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().getProcessStatesAndOomScoresFromPids(arrn, arrn2, arrn3);
                        return;
                    }
                    parcel2.readException();
                    parcel2.readIntArray(arrn2);
                    parcel2.readIntArray(arrn3);
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void getProcessStatesFromPids(int[] arrn, int[] arrn2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeIntArray(arrn);
                    if (arrn2 == null) {
                        parcel.writeInt(-1);
                    } else {
                        parcel.writeInt(arrn2.length);
                    }
                    if (!this.mRemote.transact(1, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().getProcessStatesFromPids(arrn, arrn2);
                        return;
                    }
                    parcel2.readException();
                    parcel2.readIntArray(arrn2);
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

