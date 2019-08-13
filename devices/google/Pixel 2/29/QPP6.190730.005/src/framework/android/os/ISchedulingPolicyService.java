/*
 * Decompiled with CFR 0.145.
 */
package android.os;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface ISchedulingPolicyService
extends IInterface {
    public int requestCpusetBoost(boolean var1, IBinder var2) throws RemoteException;

    public int requestPriority(int var1, int var2, int var3, boolean var4) throws RemoteException;

    public static class Default
    implements ISchedulingPolicyService {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public int requestCpusetBoost(boolean bl, IBinder iBinder) throws RemoteException {
            return 0;
        }

        @Override
        public int requestPriority(int n, int n2, int n3, boolean bl) throws RemoteException {
            return 0;
        }
    }

    public static abstract class Stub
    extends Binder
    implements ISchedulingPolicyService {
        private static final String DESCRIPTOR = "android.os.ISchedulingPolicyService";
        static final int TRANSACTION_requestCpusetBoost = 2;
        static final int TRANSACTION_requestPriority = 1;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static ISchedulingPolicyService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof ISchedulingPolicyService) {
                return (ISchedulingPolicyService)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static ISchedulingPolicyService getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            if (n != 1) {
                if (n != 2) {
                    return null;
                }
                return "requestCpusetBoost";
            }
            return "requestPriority";
        }

        public static boolean setDefaultImpl(ISchedulingPolicyService iSchedulingPolicyService) {
            if (Proxy.sDefaultImpl == null && iSchedulingPolicyService != null) {
                Proxy.sDefaultImpl = iSchedulingPolicyService;
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
            boolean bl = false;
            boolean bl2 = false;
            if (n != 1) {
                if (n != 2) {
                    if (n != 1598968902) {
                        return super.onTransact(n, parcel, parcel2, n2);
                    }
                    parcel2.writeString(DESCRIPTOR);
                    return true;
                }
                parcel.enforceInterface(DESCRIPTOR);
                if (parcel.readInt() != 0) {
                    bl2 = true;
                }
                n = this.requestCpusetBoost(bl2, parcel.readStrongBinder());
                parcel2.writeNoException();
                parcel2.writeInt(n);
                return true;
            }
            parcel.enforceInterface(DESCRIPTOR);
            int n3 = parcel.readInt();
            n = parcel.readInt();
            n2 = parcel.readInt();
            bl2 = bl;
            if (parcel.readInt() != 0) {
                bl2 = true;
            }
            n = this.requestPriority(n3, n, n2, bl2);
            parcel2.writeNoException();
            parcel2.writeInt(n);
            return true;
        }

        private static class Proxy
        implements ISchedulingPolicyService {
            public static ISchedulingPolicyService sDefaultImpl;
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
            public int requestCpusetBoost(boolean bl, IBinder iBinder) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                int n = bl ? 1 : 0;
                try {
                    parcel.writeInt(n);
                    parcel.writeStrongBinder(iBinder);
                    if (!this.mRemote.transact(2, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        n = Stub.getDefaultImpl().requestCpusetBoost(bl, iBinder);
                        return n;
                    }
                    parcel2.readException();
                    n = parcel2.readInt();
                    return n;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public int requestPriority(int n, int n2, int n3, boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                parcel.writeInt(n);
                parcel.writeInt(n2);
                parcel.writeInt(n3);
                int n4 = bl ? 1 : 0;
                try {
                    parcel.writeInt(n4);
                    if (!this.mRemote.transact(1, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        n = Stub.getDefaultImpl().requestPriority(n, n2, n3, bl);
                        return n;
                    }
                    parcel2.readException();
                    n = parcel2.readInt();
                    return n;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }
        }

    }

}

