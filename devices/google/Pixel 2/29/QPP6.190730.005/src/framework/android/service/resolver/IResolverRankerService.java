/*
 * Decompiled with CFR 0.145.
 */
package android.service.resolver;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import android.service.resolver.IResolverRankerResult;
import android.service.resolver.ResolverTarget;
import java.util.ArrayList;
import java.util.List;

public interface IResolverRankerService
extends IInterface {
    public void predict(List<ResolverTarget> var1, IResolverRankerResult var2) throws RemoteException;

    public void train(List<ResolverTarget> var1, int var2) throws RemoteException;

    public static class Default
    implements IResolverRankerService {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void predict(List<ResolverTarget> list, IResolverRankerResult iResolverRankerResult) throws RemoteException {
        }

        @Override
        public void train(List<ResolverTarget> list, int n) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IResolverRankerService {
        private static final String DESCRIPTOR = "android.service.resolver.IResolverRankerService";
        static final int TRANSACTION_predict = 1;
        static final int TRANSACTION_train = 2;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IResolverRankerService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IResolverRankerService) {
                return (IResolverRankerService)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IResolverRankerService getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            if (n != 1) {
                if (n != 2) {
                    return null;
                }
                return "train";
            }
            return "predict";
        }

        public static boolean setDefaultImpl(IResolverRankerService iResolverRankerService) {
            if (Proxy.sDefaultImpl == null && iResolverRankerService != null) {
                Proxy.sDefaultImpl = iResolverRankerService;
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
                this.train(parcel.createTypedArrayList(ResolverTarget.CREATOR), parcel.readInt());
                return true;
            }
            parcel.enforceInterface(DESCRIPTOR);
            this.predict(parcel.createTypedArrayList(ResolverTarget.CREATOR), IResolverRankerResult.Stub.asInterface(parcel.readStrongBinder()));
            return true;
        }

        private static class Proxy
        implements IResolverRankerService {
            public static IResolverRankerService sDefaultImpl;
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
            public void predict(List<ResolverTarget> list, IResolverRankerResult iResolverRankerResult) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeTypedList(list);
                    IBinder iBinder = iResolverRankerResult != null ? iResolverRankerResult.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (this.mRemote.transact(1, parcel, null, 1)) return;
                    if (Stub.getDefaultImpl() == null) return;
                    Stub.getDefaultImpl().predict(list, iResolverRankerResult);
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void train(List<ResolverTarget> list, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeTypedList(list);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(2, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().train(list, n);
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

