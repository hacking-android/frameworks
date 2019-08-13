/*
 * Decompiled with CFR 0.145.
 */
package android.content;

import android.accounts.Account;
import android.annotation.UnsupportedAppUsage;
import android.content.ISyncAdapterUnsyncableAccountCallback;
import android.content.ISyncContext;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;

public interface ISyncAdapter
extends IInterface {
    @UnsupportedAppUsage
    public void cancelSync(ISyncContext var1) throws RemoteException;

    @UnsupportedAppUsage
    public void onUnsyncableAccount(ISyncAdapterUnsyncableAccountCallback var1) throws RemoteException;

    @UnsupportedAppUsage
    public void startSync(ISyncContext var1, String var2, Account var3, Bundle var4) throws RemoteException;

    public static class Default
    implements ISyncAdapter {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void cancelSync(ISyncContext iSyncContext) throws RemoteException {
        }

        @Override
        public void onUnsyncableAccount(ISyncAdapterUnsyncableAccountCallback iSyncAdapterUnsyncableAccountCallback) throws RemoteException {
        }

        @Override
        public void startSync(ISyncContext iSyncContext, String string2, Account account, Bundle bundle) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements ISyncAdapter {
        private static final String DESCRIPTOR = "android.content.ISyncAdapter";
        static final int TRANSACTION_cancelSync = 3;
        static final int TRANSACTION_onUnsyncableAccount = 1;
        static final int TRANSACTION_startSync = 2;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static ISyncAdapter asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof ISyncAdapter) {
                return (ISyncAdapter)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static ISyncAdapter getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            if (n != 1) {
                if (n != 2) {
                    if (n != 3) {
                        return null;
                    }
                    return "cancelSync";
                }
                return "startSync";
            }
            return "onUnsyncableAccount";
        }

        public static boolean setDefaultImpl(ISyncAdapter iSyncAdapter) {
            if (Proxy.sDefaultImpl == null && iSyncAdapter != null) {
                Proxy.sDefaultImpl = iSyncAdapter;
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
                    if (n != 3) {
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
                ISyncContext iSyncContext = ISyncContext.Stub.asInterface(((Parcel)object).readStrongBinder());
                String string2 = ((Parcel)object).readString();
                object2 = ((Parcel)object).readInt() != 0 ? Account.CREATOR.createFromParcel((Parcel)object) : null;
                object = ((Parcel)object).readInt() != 0 ? Bundle.CREATOR.createFromParcel((Parcel)object) : null;
                this.startSync(iSyncContext, string2, (Account)object2, (Bundle)object);
                return true;
            }
            ((Parcel)object).enforceInterface(DESCRIPTOR);
            this.onUnsyncableAccount(ISyncAdapterUnsyncableAccountCallback.Stub.asInterface(((Parcel)object).readStrongBinder()));
            return true;
        }

        private static class Proxy
        implements ISyncAdapter {
            public static ISyncAdapter sDefaultImpl;
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
                    if (this.mRemote.transact(3, parcel, null, 1)) return;
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
            public void onUnsyncableAccount(ISyncAdapterUnsyncableAccountCallback iSyncAdapterUnsyncableAccountCallback) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iSyncAdapterUnsyncableAccountCallback != null ? iSyncAdapterUnsyncableAccountCallback.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (this.mRemote.transact(1, parcel, null, 1)) return;
                    if (Stub.getDefaultImpl() == null) return;
                    Stub.getDefaultImpl().onUnsyncableAccount(iSyncAdapterUnsyncableAccountCallback);
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
            public void startSync(ISyncContext iSyncContext, String string2, Account account, Bundle bundle) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iSyncContext != null ? iSyncContext.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    parcel.writeString(string2);
                    if (account != null) {
                        parcel.writeInt(1);
                        account.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (bundle != null) {
                        parcel.writeInt(1);
                        bundle.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (this.mRemote.transact(2, parcel, null, 1)) return;
                    if (Stub.getDefaultImpl() == null) return;
                    Stub.getDefaultImpl().startSync(iSyncContext, string2, account, bundle);
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }
        }

    }

}

