/*
 * Decompiled with CFR 0.145.
 */
package android.accounts;

import android.accounts.Account;
import android.accounts.IAccountAuthenticatorResponse;
import android.annotation.UnsupportedAppUsage;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;

public interface IAccountAuthenticator
extends IInterface {
    @UnsupportedAppUsage
    public void addAccount(IAccountAuthenticatorResponse var1, String var2, String var3, String[] var4, Bundle var5) throws RemoteException;

    public void addAccountFromCredentials(IAccountAuthenticatorResponse var1, Account var2, Bundle var3) throws RemoteException;

    @UnsupportedAppUsage
    public void confirmCredentials(IAccountAuthenticatorResponse var1, Account var2, Bundle var3) throws RemoteException;

    @UnsupportedAppUsage
    public void editProperties(IAccountAuthenticatorResponse var1, String var2) throws RemoteException;

    public void finishSession(IAccountAuthenticatorResponse var1, String var2, Bundle var3) throws RemoteException;

    public void getAccountCredentialsForCloning(IAccountAuthenticatorResponse var1, Account var2) throws RemoteException;

    @UnsupportedAppUsage
    public void getAccountRemovalAllowed(IAccountAuthenticatorResponse var1, Account var2) throws RemoteException;

    @UnsupportedAppUsage
    public void getAuthToken(IAccountAuthenticatorResponse var1, Account var2, String var3, Bundle var4) throws RemoteException;

    @UnsupportedAppUsage
    public void getAuthTokenLabel(IAccountAuthenticatorResponse var1, String var2) throws RemoteException;

    @UnsupportedAppUsage
    public void hasFeatures(IAccountAuthenticatorResponse var1, Account var2, String[] var3) throws RemoteException;

    public void isCredentialsUpdateSuggested(IAccountAuthenticatorResponse var1, Account var2, String var3) throws RemoteException;

    public void startAddAccountSession(IAccountAuthenticatorResponse var1, String var2, String var3, String[] var4, Bundle var5) throws RemoteException;

    public void startUpdateCredentialsSession(IAccountAuthenticatorResponse var1, Account var2, String var3, Bundle var4) throws RemoteException;

    @UnsupportedAppUsage
    public void updateCredentials(IAccountAuthenticatorResponse var1, Account var2, String var3, Bundle var4) throws RemoteException;

    public static class Default
    implements IAccountAuthenticator {
        @Override
        public void addAccount(IAccountAuthenticatorResponse iAccountAuthenticatorResponse, String string2, String string3, String[] arrstring, Bundle bundle) throws RemoteException {
        }

        @Override
        public void addAccountFromCredentials(IAccountAuthenticatorResponse iAccountAuthenticatorResponse, Account account, Bundle bundle) throws RemoteException {
        }

        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void confirmCredentials(IAccountAuthenticatorResponse iAccountAuthenticatorResponse, Account account, Bundle bundle) throws RemoteException {
        }

        @Override
        public void editProperties(IAccountAuthenticatorResponse iAccountAuthenticatorResponse, String string2) throws RemoteException {
        }

        @Override
        public void finishSession(IAccountAuthenticatorResponse iAccountAuthenticatorResponse, String string2, Bundle bundle) throws RemoteException {
        }

        @Override
        public void getAccountCredentialsForCloning(IAccountAuthenticatorResponse iAccountAuthenticatorResponse, Account account) throws RemoteException {
        }

        @Override
        public void getAccountRemovalAllowed(IAccountAuthenticatorResponse iAccountAuthenticatorResponse, Account account) throws RemoteException {
        }

        @Override
        public void getAuthToken(IAccountAuthenticatorResponse iAccountAuthenticatorResponse, Account account, String string2, Bundle bundle) throws RemoteException {
        }

        @Override
        public void getAuthTokenLabel(IAccountAuthenticatorResponse iAccountAuthenticatorResponse, String string2) throws RemoteException {
        }

        @Override
        public void hasFeatures(IAccountAuthenticatorResponse iAccountAuthenticatorResponse, Account account, String[] arrstring) throws RemoteException {
        }

        @Override
        public void isCredentialsUpdateSuggested(IAccountAuthenticatorResponse iAccountAuthenticatorResponse, Account account, String string2) throws RemoteException {
        }

        @Override
        public void startAddAccountSession(IAccountAuthenticatorResponse iAccountAuthenticatorResponse, String string2, String string3, String[] arrstring, Bundle bundle) throws RemoteException {
        }

        @Override
        public void startUpdateCredentialsSession(IAccountAuthenticatorResponse iAccountAuthenticatorResponse, Account account, String string2, Bundle bundle) throws RemoteException {
        }

        @Override
        public void updateCredentials(IAccountAuthenticatorResponse iAccountAuthenticatorResponse, Account account, String string2, Bundle bundle) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IAccountAuthenticator {
        private static final String DESCRIPTOR = "android.accounts.IAccountAuthenticator";
        static final int TRANSACTION_addAccount = 1;
        static final int TRANSACTION_addAccountFromCredentials = 10;
        static final int TRANSACTION_confirmCredentials = 2;
        static final int TRANSACTION_editProperties = 6;
        static final int TRANSACTION_finishSession = 13;
        static final int TRANSACTION_getAccountCredentialsForCloning = 9;
        static final int TRANSACTION_getAccountRemovalAllowed = 8;
        static final int TRANSACTION_getAuthToken = 3;
        static final int TRANSACTION_getAuthTokenLabel = 4;
        static final int TRANSACTION_hasFeatures = 7;
        static final int TRANSACTION_isCredentialsUpdateSuggested = 14;
        static final int TRANSACTION_startAddAccountSession = 11;
        static final int TRANSACTION_startUpdateCredentialsSession = 12;
        static final int TRANSACTION_updateCredentials = 5;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IAccountAuthenticator asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IAccountAuthenticator) {
                return (IAccountAuthenticator)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IAccountAuthenticator getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            switch (n) {
                default: {
                    return null;
                }
                case 14: {
                    return "isCredentialsUpdateSuggested";
                }
                case 13: {
                    return "finishSession";
                }
                case 12: {
                    return "startUpdateCredentialsSession";
                }
                case 11: {
                    return "startAddAccountSession";
                }
                case 10: {
                    return "addAccountFromCredentials";
                }
                case 9: {
                    return "getAccountCredentialsForCloning";
                }
                case 8: {
                    return "getAccountRemovalAllowed";
                }
                case 7: {
                    return "hasFeatures";
                }
                case 6: {
                    return "editProperties";
                }
                case 5: {
                    return "updateCredentials";
                }
                case 4: {
                    return "getAuthTokenLabel";
                }
                case 3: {
                    return "getAuthToken";
                }
                case 2: {
                    return "confirmCredentials";
                }
                case 1: 
            }
            return "addAccount";
        }

        public static boolean setDefaultImpl(IAccountAuthenticator iAccountAuthenticator) {
            if (Proxy.sDefaultImpl == null && iAccountAuthenticator != null) {
                Proxy.sDefaultImpl = iAccountAuthenticator;
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
            if (n != 1598968902) {
                switch (n) {
                    default: {
                        return super.onTransact(n, (Parcel)object, (Parcel)object2, n2);
                    }
                    case 14: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        IAccountAuthenticatorResponse iAccountAuthenticatorResponse = IAccountAuthenticatorResponse.Stub.asInterface(((Parcel)object).readStrongBinder());
                        object2 = ((Parcel)object).readInt() != 0 ? Account.CREATOR.createFromParcel((Parcel)object) : null;
                        this.isCredentialsUpdateSuggested(iAccountAuthenticatorResponse, (Account)object2, ((Parcel)object).readString());
                        return true;
                    }
                    case 13: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        IAccountAuthenticatorResponse iAccountAuthenticatorResponse = IAccountAuthenticatorResponse.Stub.asInterface(((Parcel)object).readStrongBinder());
                        object2 = ((Parcel)object).readString();
                        object = ((Parcel)object).readInt() != 0 ? Bundle.CREATOR.createFromParcel((Parcel)object) : null;
                        this.finishSession(iAccountAuthenticatorResponse, (String)object2, (Bundle)object);
                        return true;
                    }
                    case 12: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        IAccountAuthenticatorResponse iAccountAuthenticatorResponse = IAccountAuthenticatorResponse.Stub.asInterface(((Parcel)object).readStrongBinder());
                        object2 = ((Parcel)object).readInt() != 0 ? Account.CREATOR.createFromParcel((Parcel)object) : null;
                        String string2 = ((Parcel)object).readString();
                        object = ((Parcel)object).readInt() != 0 ? Bundle.CREATOR.createFromParcel((Parcel)object) : null;
                        this.startUpdateCredentialsSession(iAccountAuthenticatorResponse, (Account)object2, string2, (Bundle)object);
                        return true;
                    }
                    case 11: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        IAccountAuthenticatorResponse iAccountAuthenticatorResponse = IAccountAuthenticatorResponse.Stub.asInterface(((Parcel)object).readStrongBinder());
                        object2 = ((Parcel)object).readString();
                        String string3 = ((Parcel)object).readString();
                        String[] arrstring = ((Parcel)object).createStringArray();
                        object = ((Parcel)object).readInt() != 0 ? Bundle.CREATOR.createFromParcel((Parcel)object) : null;
                        this.startAddAccountSession(iAccountAuthenticatorResponse, (String)object2, string3, arrstring, (Bundle)object);
                        return true;
                    }
                    case 10: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        IAccountAuthenticatorResponse iAccountAuthenticatorResponse = IAccountAuthenticatorResponse.Stub.asInterface(((Parcel)object).readStrongBinder());
                        object2 = ((Parcel)object).readInt() != 0 ? Account.CREATOR.createFromParcel((Parcel)object) : null;
                        object = ((Parcel)object).readInt() != 0 ? Bundle.CREATOR.createFromParcel((Parcel)object) : null;
                        this.addAccountFromCredentials(iAccountAuthenticatorResponse, (Account)object2, (Bundle)object);
                        return true;
                    }
                    case 9: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object2 = IAccountAuthenticatorResponse.Stub.asInterface(((Parcel)object).readStrongBinder());
                        object = ((Parcel)object).readInt() != 0 ? Account.CREATOR.createFromParcel((Parcel)object) : null;
                        this.getAccountCredentialsForCloning((IAccountAuthenticatorResponse)object2, (Account)object);
                        return true;
                    }
                    case 8: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object2 = IAccountAuthenticatorResponse.Stub.asInterface(((Parcel)object).readStrongBinder());
                        object = ((Parcel)object).readInt() != 0 ? Account.CREATOR.createFromParcel((Parcel)object) : null;
                        this.getAccountRemovalAllowed((IAccountAuthenticatorResponse)object2, (Account)object);
                        return true;
                    }
                    case 7: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        IAccountAuthenticatorResponse iAccountAuthenticatorResponse = IAccountAuthenticatorResponse.Stub.asInterface(((Parcel)object).readStrongBinder());
                        object2 = ((Parcel)object).readInt() != 0 ? Account.CREATOR.createFromParcel((Parcel)object) : null;
                        this.hasFeatures(iAccountAuthenticatorResponse, (Account)object2, ((Parcel)object).createStringArray());
                        return true;
                    }
                    case 6: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.editProperties(IAccountAuthenticatorResponse.Stub.asInterface(((Parcel)object).readStrongBinder()), ((Parcel)object).readString());
                        return true;
                    }
                    case 5: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        IAccountAuthenticatorResponse iAccountAuthenticatorResponse = IAccountAuthenticatorResponse.Stub.asInterface(((Parcel)object).readStrongBinder());
                        object2 = ((Parcel)object).readInt() != 0 ? Account.CREATOR.createFromParcel((Parcel)object) : null;
                        String string4 = ((Parcel)object).readString();
                        object = ((Parcel)object).readInt() != 0 ? Bundle.CREATOR.createFromParcel((Parcel)object) : null;
                        this.updateCredentials(iAccountAuthenticatorResponse, (Account)object2, string4, (Bundle)object);
                        return true;
                    }
                    case 4: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.getAuthTokenLabel(IAccountAuthenticatorResponse.Stub.asInterface(((Parcel)object).readStrongBinder()), ((Parcel)object).readString());
                        return true;
                    }
                    case 3: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        IAccountAuthenticatorResponse iAccountAuthenticatorResponse = IAccountAuthenticatorResponse.Stub.asInterface(((Parcel)object).readStrongBinder());
                        object2 = ((Parcel)object).readInt() != 0 ? Account.CREATOR.createFromParcel((Parcel)object) : null;
                        String string5 = ((Parcel)object).readString();
                        object = ((Parcel)object).readInt() != 0 ? Bundle.CREATOR.createFromParcel((Parcel)object) : null;
                        this.getAuthToken(iAccountAuthenticatorResponse, (Account)object2, string5, (Bundle)object);
                        return true;
                    }
                    case 2: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        IAccountAuthenticatorResponse iAccountAuthenticatorResponse = IAccountAuthenticatorResponse.Stub.asInterface(((Parcel)object).readStrongBinder());
                        object2 = ((Parcel)object).readInt() != 0 ? Account.CREATOR.createFromParcel((Parcel)object) : null;
                        object = ((Parcel)object).readInt() != 0 ? Bundle.CREATOR.createFromParcel((Parcel)object) : null;
                        this.confirmCredentials(iAccountAuthenticatorResponse, (Account)object2, (Bundle)object);
                        return true;
                    }
                    case 1: 
                }
                ((Parcel)object).enforceInterface(DESCRIPTOR);
                object2 = IAccountAuthenticatorResponse.Stub.asInterface(((Parcel)object).readStrongBinder());
                String string6 = ((Parcel)object).readString();
                String string7 = ((Parcel)object).readString();
                String[] arrstring = ((Parcel)object).createStringArray();
                object = ((Parcel)object).readInt() != 0 ? Bundle.CREATOR.createFromParcel((Parcel)object) : null;
                this.addAccount((IAccountAuthenticatorResponse)object2, string6, string7, arrstring, (Bundle)object);
                return true;
            }
            ((Parcel)object2).writeString(DESCRIPTOR);
            return true;
        }

        private static class Proxy
        implements IAccountAuthenticator {
            public static IAccountAuthenticator sDefaultImpl;
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void addAccount(IAccountAuthenticatorResponse iAccountAuthenticatorResponse, String string2, String string3, String[] arrstring, Bundle bundle) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iAccountAuthenticatorResponse != null ? iAccountAuthenticatorResponse.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    parcel.writeString(string2);
                    parcel.writeString(string3);
                    parcel.writeStringArray(arrstring);
                    if (bundle != null) {
                        parcel.writeInt(1);
                        bundle.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (this.mRemote.transact(1, parcel, null, 1)) return;
                    if (Stub.getDefaultImpl() == null) return;
                    Stub.getDefaultImpl().addAccount(iAccountAuthenticatorResponse, string2, string3, arrstring, bundle);
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
            public void addAccountFromCredentials(IAccountAuthenticatorResponse iAccountAuthenticatorResponse, Account account, Bundle bundle) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iAccountAuthenticatorResponse != null ? iAccountAuthenticatorResponse.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
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
                    if (this.mRemote.transact(10, parcel, null, 1)) return;
                    if (Stub.getDefaultImpl() == null) return;
                    Stub.getDefaultImpl().addAccountFromCredentials(iAccountAuthenticatorResponse, account, bundle);
                    return;
                }
                finally {
                    parcel.recycle();
                }
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
            public void confirmCredentials(IAccountAuthenticatorResponse iAccountAuthenticatorResponse, Account account, Bundle bundle) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iAccountAuthenticatorResponse != null ? iAccountAuthenticatorResponse.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
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
                    Stub.getDefaultImpl().confirmCredentials(iAccountAuthenticatorResponse, account, bundle);
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
            public void editProperties(IAccountAuthenticatorResponse iAccountAuthenticatorResponse, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iAccountAuthenticatorResponse != null ? iAccountAuthenticatorResponse.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    parcel.writeString(string2);
                    if (this.mRemote.transact(6, parcel, null, 1)) return;
                    if (Stub.getDefaultImpl() == null) return;
                    Stub.getDefaultImpl().editProperties(iAccountAuthenticatorResponse, string2);
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
            public void finishSession(IAccountAuthenticatorResponse iAccountAuthenticatorResponse, String string2, Bundle bundle) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iAccountAuthenticatorResponse != null ? iAccountAuthenticatorResponse.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    parcel.writeString(string2);
                    if (bundle != null) {
                        parcel.writeInt(1);
                        bundle.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (this.mRemote.transact(13, parcel, null, 1)) return;
                    if (Stub.getDefaultImpl() == null) return;
                    Stub.getDefaultImpl().finishSession(iAccountAuthenticatorResponse, string2, bundle);
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
            public void getAccountCredentialsForCloning(IAccountAuthenticatorResponse iAccountAuthenticatorResponse, Account account) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iAccountAuthenticatorResponse != null ? iAccountAuthenticatorResponse.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (account != null) {
                        parcel.writeInt(1);
                        account.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (this.mRemote.transact(9, parcel, null, 1)) return;
                    if (Stub.getDefaultImpl() == null) return;
                    Stub.getDefaultImpl().getAccountCredentialsForCloning(iAccountAuthenticatorResponse, account);
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
            public void getAccountRemovalAllowed(IAccountAuthenticatorResponse iAccountAuthenticatorResponse, Account account) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iAccountAuthenticatorResponse != null ? iAccountAuthenticatorResponse.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (account != null) {
                        parcel.writeInt(1);
                        account.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (this.mRemote.transact(8, parcel, null, 1)) return;
                    if (Stub.getDefaultImpl() == null) return;
                    Stub.getDefaultImpl().getAccountRemovalAllowed(iAccountAuthenticatorResponse, account);
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
            public void getAuthToken(IAccountAuthenticatorResponse iAccountAuthenticatorResponse, Account account, String string2, Bundle bundle) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iAccountAuthenticatorResponse != null ? iAccountAuthenticatorResponse.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (account != null) {
                        parcel.writeInt(1);
                        account.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeString(string2);
                    if (bundle != null) {
                        parcel.writeInt(1);
                        bundle.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (this.mRemote.transact(3, parcel, null, 1)) return;
                    if (Stub.getDefaultImpl() == null) return;
                    Stub.getDefaultImpl().getAuthToken(iAccountAuthenticatorResponse, account, string2, bundle);
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
            public void getAuthTokenLabel(IAccountAuthenticatorResponse iAccountAuthenticatorResponse, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iAccountAuthenticatorResponse != null ? iAccountAuthenticatorResponse.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    parcel.writeString(string2);
                    if (this.mRemote.transact(4, parcel, null, 1)) return;
                    if (Stub.getDefaultImpl() == null) return;
                    Stub.getDefaultImpl().getAuthTokenLabel(iAccountAuthenticatorResponse, string2);
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
            public void hasFeatures(IAccountAuthenticatorResponse iAccountAuthenticatorResponse, Account account, String[] arrstring) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iAccountAuthenticatorResponse != null ? iAccountAuthenticatorResponse.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (account != null) {
                        parcel.writeInt(1);
                        account.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeStringArray(arrstring);
                    if (this.mRemote.transact(7, parcel, null, 1)) return;
                    if (Stub.getDefaultImpl() == null) return;
                    Stub.getDefaultImpl().hasFeatures(iAccountAuthenticatorResponse, account, arrstring);
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
            public void isCredentialsUpdateSuggested(IAccountAuthenticatorResponse iAccountAuthenticatorResponse, Account account, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iAccountAuthenticatorResponse != null ? iAccountAuthenticatorResponse.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (account != null) {
                        parcel.writeInt(1);
                        account.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeString(string2);
                    if (this.mRemote.transact(14, parcel, null, 1)) return;
                    if (Stub.getDefaultImpl() == null) return;
                    Stub.getDefaultImpl().isCredentialsUpdateSuggested(iAccountAuthenticatorResponse, account, string2);
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
            public void startAddAccountSession(IAccountAuthenticatorResponse iAccountAuthenticatorResponse, String string2, String string3, String[] arrstring, Bundle bundle) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iAccountAuthenticatorResponse != null ? iAccountAuthenticatorResponse.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    parcel.writeString(string2);
                    parcel.writeString(string3);
                    parcel.writeStringArray(arrstring);
                    if (bundle != null) {
                        parcel.writeInt(1);
                        bundle.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (this.mRemote.transact(11, parcel, null, 1)) return;
                    if (Stub.getDefaultImpl() == null) return;
                    Stub.getDefaultImpl().startAddAccountSession(iAccountAuthenticatorResponse, string2, string3, arrstring, bundle);
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
            public void startUpdateCredentialsSession(IAccountAuthenticatorResponse iAccountAuthenticatorResponse, Account account, String string2, Bundle bundle) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iAccountAuthenticatorResponse != null ? iAccountAuthenticatorResponse.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (account != null) {
                        parcel.writeInt(1);
                        account.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeString(string2);
                    if (bundle != null) {
                        parcel.writeInt(1);
                        bundle.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (this.mRemote.transact(12, parcel, null, 1)) return;
                    if (Stub.getDefaultImpl() == null) return;
                    Stub.getDefaultImpl().startUpdateCredentialsSession(iAccountAuthenticatorResponse, account, string2, bundle);
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
            public void updateCredentials(IAccountAuthenticatorResponse iAccountAuthenticatorResponse, Account account, String string2, Bundle bundle) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iAccountAuthenticatorResponse != null ? iAccountAuthenticatorResponse.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (account != null) {
                        parcel.writeInt(1);
                        account.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeString(string2);
                    if (bundle != null) {
                        parcel.writeInt(1);
                        bundle.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (this.mRemote.transact(5, parcel, null, 1)) return;
                    if (Stub.getDefaultImpl() == null) return;
                    Stub.getDefaultImpl().updateCredentials(iAccountAuthenticatorResponse, account, string2, bundle);
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }
        }

    }

}

