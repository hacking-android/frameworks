/*
 * Decompiled with CFR 0.145.
 */
package android.app;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.IRemoteCallback;
import android.os.Parcel;
import android.os.RemoteException;

public interface IEphemeralResolver
extends IInterface {
    public void getEphemeralIntentFilterList(IRemoteCallback var1, String var2, int var3) throws RemoteException;

    public void getEphemeralResolveInfoList(IRemoteCallback var1, int[] var2, int var3) throws RemoteException;

    public static class Default
    implements IEphemeralResolver {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void getEphemeralIntentFilterList(IRemoteCallback iRemoteCallback, String string2, int n) throws RemoteException {
        }

        @Override
        public void getEphemeralResolveInfoList(IRemoteCallback iRemoteCallback, int[] arrn, int n) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IEphemeralResolver {
        private static final String DESCRIPTOR = "android.app.IEphemeralResolver";
        static final int TRANSACTION_getEphemeralIntentFilterList = 2;
        static final int TRANSACTION_getEphemeralResolveInfoList = 1;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IEphemeralResolver asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IEphemeralResolver) {
                return (IEphemeralResolver)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IEphemeralResolver getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            if (n != 1) {
                if (n != 2) {
                    return null;
                }
                return "getEphemeralIntentFilterList";
            }
            return "getEphemeralResolveInfoList";
        }

        public static boolean setDefaultImpl(IEphemeralResolver iEphemeralResolver) {
            if (Proxy.sDefaultImpl == null && iEphemeralResolver != null) {
                Proxy.sDefaultImpl = iEphemeralResolver;
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
                this.getEphemeralIntentFilterList(IRemoteCallback.Stub.asInterface(parcel.readStrongBinder()), parcel.readString(), parcel.readInt());
                return true;
            }
            parcel.enforceInterface(DESCRIPTOR);
            this.getEphemeralResolveInfoList(IRemoteCallback.Stub.asInterface(parcel.readStrongBinder()), parcel.createIntArray(), parcel.readInt());
            return true;
        }

        private static class Proxy
        implements IEphemeralResolver {
            public static IEphemeralResolver sDefaultImpl;
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override
            public IBinder asBinder() {
                return this.mRemote;
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void getEphemeralIntentFilterList(IRemoteCallback iRemoteCallback, String string2, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iRemoteCallback != null ? iRemoteCallback.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    parcel.writeString(string2);
                    parcel.writeInt(n);
                    if (this.mRemote.transact(2, parcel, null, 1)) return;
                    if (Stub.getDefaultImpl() == null) return;
                    Stub.getDefaultImpl().getEphemeralIntentFilterList(iRemoteCallback, string2, n);
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void getEphemeralResolveInfoList(IRemoteCallback iRemoteCallback, int[] arrn, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iRemoteCallback != null ? iRemoteCallback.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    parcel.writeIntArray(arrn);
                    parcel.writeInt(n);
                    if (this.mRemote.transact(1, parcel, null, 1)) return;
                    if (Stub.getDefaultImpl() == null) return;
                    Stub.getDefaultImpl().getEphemeralResolveInfoList(iRemoteCallback, arrn, n);
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

