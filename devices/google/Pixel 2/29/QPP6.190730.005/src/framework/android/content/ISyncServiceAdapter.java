/*
 * Decompiled with CFR 0.145.
 */
package android.content;

import android.annotation.UnsupportedAppUsage;
import android.content.ISyncContext;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;

public interface ISyncServiceAdapter
extends IInterface {
    @UnsupportedAppUsage
    public void cancelSync(ISyncContext var1) throws RemoteException;

    @UnsupportedAppUsage
    public void startSync(ISyncContext var1, Bundle var2) throws RemoteException;

    public static class Default
    implements ISyncServiceAdapter {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void cancelSync(ISyncContext iSyncContext) throws RemoteException {
        }

        @Override
        public void startSync(ISyncContext iSyncContext, Bundle bundle) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements ISyncServiceAdapter {
        private static final String DESCRIPTOR = "android.content.ISyncServiceAdapter";
        static final int TRANSACTION_cancelSync = 2;
        static final int TRANSACTION_startSync = 1;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static ISyncServiceAdapter asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof ISyncServiceAdapter) {
                return (ISyncServiceAdapter)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static ISyncServiceAdapter getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            if (n != 1) {
                if (n != 2) {
                    return null;
                }
                return "cancelSync";
            }
            return "startSync";
        }

        public static boolean setDefaultImpl(ISyncServiceAdapter iSyncServiceAdapter) {
            if (Proxy.sDefaultImpl == null && iSyncServiceAdapter != null) {
                Proxy.sDefaultImpl = iSyncServiceAdapter;
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
        public boolean onTransact(int n, Parcel object, Parcel object2, int n2) throws RemoteException {
            if (n != 1) {
                if (n != 2) {
                    if (n != 1598968902) {
                        return super.onTransact(n, (Parcel)object, (Parcel)object2, n2);
                    }
                    ((Parcel)object2).writeString(DESCRIPTOR);
                    return true;
                }
                ((Parcel)object).enforceInterface(DESCRIPTOR);
                this.cancelSync(ISyncContext.Stub.asInterface(((Parcel)object).readStrongBinder()));
                return true;
            }
            ((Parcel)object).enforceInterface(DESCRIPTOR);
            object2 = ISyncContext.Stub.asInterface(((Parcel)object).readStrongBinder());
            object = ((Parcel)object).readInt() != 0 ? Bundle.CREATOR.createFromParcel((Parcel)object) : null;
            this.startSync((ISyncContext)object2, (Bundle)object);
            return true;
        }

        private static class Proxy
        implements ISyncServiceAdapter {
            public static ISyncServiceAdapter sDefaultImpl;
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
            public void cancelSync(ISyncContext iSyncContext) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iSyncContext != null ? iSyncContext.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (this.mRemote.transact(2, parcel, null, 1)) return;
                    if (Stub.getDefaultImpl() == null) return;
                    Stub.getDefaultImpl().cancelSync(iSyncContext);
                    return;
                }
                finally {
                    parcel.recycle();
                }
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
            public void startSync(ISyncContext iSyncContext, Bundle bundle) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iSyncContext != null ? iSyncContext.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (bundle != null) {
                        parcel.writeInt(1);
                        bundle.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (this.mRemote.transact(1, parcel, null, 1)) return;
                    if (Stub.getDefaultImpl() == null) return;
                    Stub.getDefaultImpl().startSync(iSyncContext, bundle);
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }
        }

    }

}

