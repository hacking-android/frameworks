/*
 * Decompiled with CFR 0.145.
 */
package android.accounts;

import android.accounts.Account;
import android.accounts.AuthenticatorDescription;
import android.accounts.IAccountManagerResponse;
import android.content.IntentSender;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import android.os.UserHandle;
import java.util.HashMap;
import java.util.Map;

public interface IAccountManager
extends IInterface {
    public boolean accountAuthenticated(Account var1) throws RemoteException;

    public void addAccount(IAccountManagerResponse var1, String var2, String var3, String[] var4, boolean var5, Bundle var6) throws RemoteException;

    public void addAccountAsUser(IAccountManagerResponse var1, String var2, String var3, String[] var4, boolean var5, Bundle var6, int var7) throws RemoteException;

    public boolean addAccountExplicitly(Account var1, String var2, Bundle var3) throws RemoteException;

    public boolean addAccountExplicitlyWithVisibility(Account var1, String var2, Bundle var3, Map var4) throws RemoteException;

    public void addSharedAccountsFromParentUser(int var1, int var2, String var3) throws RemoteException;

    public void clearPassword(Account var1) throws RemoteException;

    public void confirmCredentialsAsUser(IAccountManagerResponse var1, Account var2, Bundle var3, boolean var4, int var5) throws RemoteException;

    public void copyAccountToUser(IAccountManagerResponse var1, Account var2, int var3, int var4) throws RemoteException;

    public IntentSender createRequestAccountAccessIntentSenderAsUser(Account var1, String var2, UserHandle var3) throws RemoteException;

    public void editProperties(IAccountManagerResponse var1, String var2, boolean var3) throws RemoteException;

    public void finishSessionAsUser(IAccountManagerResponse var1, Bundle var2, boolean var3, Bundle var4, int var5) throws RemoteException;

    public void getAccountByTypeAndFeatures(IAccountManagerResponse var1, String var2, String[] var3, String var4) throws RemoteException;

    public int getAccountVisibility(Account var1, String var2) throws RemoteException;

    public Account[] getAccounts(String var1, String var2) throws RemoteException;

    public Map getAccountsAndVisibilityForPackage(String var1, String var2) throws RemoteException;

    public Account[] getAccountsAsUser(String var1, int var2, String var3) throws RemoteException;

    public void getAccountsByFeatures(IAccountManagerResponse var1, String var2, String[] var3, String var4) throws RemoteException;

    public Account[] getAccountsByTypeForPackage(String var1, String var2, String var3) throws RemoteException;

    public Account[] getAccountsForPackage(String var1, int var2, String var3) throws RemoteException;

    public void getAuthToken(IAccountManagerResponse var1, Account var2, String var3, boolean var4, boolean var5, Bundle var6) throws RemoteException;

    public void getAuthTokenLabel(IAccountManagerResponse var1, String var2, String var3) throws RemoteException;

    public AuthenticatorDescription[] getAuthenticatorTypes(int var1) throws RemoteException;

    public Map getPackagesAndVisibilityForAccount(Account var1) throws RemoteException;

    public String getPassword(Account var1) throws RemoteException;

    public String getPreviousName(Account var1) throws RemoteException;

    public Account[] getSharedAccountsAsUser(int var1) throws RemoteException;

    public String getUserData(Account var1, String var2) throws RemoteException;

    public boolean hasAccountAccess(Account var1, String var2, UserHandle var3) throws RemoteException;

    public void hasFeatures(IAccountManagerResponse var1, Account var2, String[] var3, String var4) throws RemoteException;

    public void invalidateAuthToken(String var1, String var2) throws RemoteException;

    public void isCredentialsUpdateSuggested(IAccountManagerResponse var1, Account var2, String var3) throws RemoteException;

    public void onAccountAccessed(String var1) throws RemoteException;

    public String peekAuthToken(Account var1, String var2) throws RemoteException;

    public void registerAccountListener(String[] var1, String var2) throws RemoteException;

    public void removeAccount(IAccountManagerResponse var1, Account var2, boolean var3) throws RemoteException;

    public void removeAccountAsUser(IAccountManagerResponse var1, Account var2, boolean var3, int var4) throws RemoteException;

    public boolean removeAccountExplicitly(Account var1) throws RemoteException;

    public boolean removeSharedAccountAsUser(Account var1, int var2) throws RemoteException;

    public void renameAccount(IAccountManagerResponse var1, Account var2, String var3) throws RemoteException;

    public boolean renameSharedAccountAsUser(Account var1, String var2, int var3) throws RemoteException;

    public boolean setAccountVisibility(Account var1, String var2, int var3) throws RemoteException;

    public void setAuthToken(Account var1, String var2, String var3) throws RemoteException;

    public void setPassword(Account var1, String var2) throws RemoteException;

    public void setUserData(Account var1, String var2, String var3) throws RemoteException;

    public boolean someUserHasAccount(Account var1) throws RemoteException;

    public void startAddAccountSession(IAccountManagerResponse var1, String var2, String var3, String[] var4, boolean var5, Bundle var6) throws RemoteException;

    public void startUpdateCredentialsSession(IAccountManagerResponse var1, Account var2, String var3, boolean var4, Bundle var5) throws RemoteException;

    public void unregisterAccountListener(String[] var1, String var2) throws RemoteException;

    public void updateAppPermission(Account var1, String var2, int var3, boolean var4) throws RemoteException;

    public void updateCredentials(IAccountManagerResponse var1, Account var2, String var3, boolean var4, Bundle var5) throws RemoteException;

    public static class Default
    implements IAccountManager {
        @Override
        public boolean accountAuthenticated(Account account) throws RemoteException {
            return false;
        }

        @Override
        public void addAccount(IAccountManagerResponse iAccountManagerResponse, String string2, String string3, String[] arrstring, boolean bl, Bundle bundle) throws RemoteException {
        }

        @Override
        public void addAccountAsUser(IAccountManagerResponse iAccountManagerResponse, String string2, String string3, String[] arrstring, boolean bl, Bundle bundle, int n) throws RemoteException {
        }

        @Override
        public boolean addAccountExplicitly(Account account, String string2, Bundle bundle) throws RemoteException {
            return false;
        }

        @Override
        public boolean addAccountExplicitlyWithVisibility(Account account, String string2, Bundle bundle, Map map) throws RemoteException {
            return false;
        }

        @Override
        public void addSharedAccountsFromParentUser(int n, int n2, String string2) throws RemoteException {
        }

        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void clearPassword(Account account) throws RemoteException {
        }

        @Override
        public void confirmCredentialsAsUser(IAccountManagerResponse iAccountManagerResponse, Account account, Bundle bundle, boolean bl, int n) throws RemoteException {
        }

        @Override
        public void copyAccountToUser(IAccountManagerResponse iAccountManagerResponse, Account account, int n, int n2) throws RemoteException {
        }

        @Override
        public IntentSender createRequestAccountAccessIntentSenderAsUser(Account account, String string2, UserHandle userHandle) throws RemoteException {
            return null;
        }

        @Override
        public void editProperties(IAccountManagerResponse iAccountManagerResponse, String string2, boolean bl) throws RemoteException {
        }

        @Override
        public void finishSessionAsUser(IAccountManagerResponse iAccountManagerResponse, Bundle bundle, boolean bl, Bundle bundle2, int n) throws RemoteException {
        }

        @Override
        public void getAccountByTypeAndFeatures(IAccountManagerResponse iAccountManagerResponse, String string2, String[] arrstring, String string3) throws RemoteException {
        }

        @Override
        public int getAccountVisibility(Account account, String string2) throws RemoteException {
            return 0;
        }

        @Override
        public Account[] getAccounts(String string2, String string3) throws RemoteException {
            return null;
        }

        @Override
        public Map getAccountsAndVisibilityForPackage(String string2, String string3) throws RemoteException {
            return null;
        }

        @Override
        public Account[] getAccountsAsUser(String string2, int n, String string3) throws RemoteException {
            return null;
        }

        @Override
        public void getAccountsByFeatures(IAccountManagerResponse iAccountManagerResponse, String string2, String[] arrstring, String string3) throws RemoteException {
        }

        @Override
        public Account[] getAccountsByTypeForPackage(String string2, String string3, String string4) throws RemoteException {
            return null;
        }

        @Override
        public Account[] getAccountsForPackage(String string2, int n, String string3) throws RemoteException {
            return null;
        }

        @Override
        public void getAuthToken(IAccountManagerResponse iAccountManagerResponse, Account account, String string2, boolean bl, boolean bl2, Bundle bundle) throws RemoteException {
        }

        @Override
        public void getAuthTokenLabel(IAccountManagerResponse iAccountManagerResponse, String string2, String string3) throws RemoteException {
        }

        @Override
        public AuthenticatorDescription[] getAuthenticatorTypes(int n) throws RemoteException {
            return null;
        }

        @Override
        public Map getPackagesAndVisibilityForAccount(Account account) throws RemoteException {
            return null;
        }

        @Override
        public String getPassword(Account account) throws RemoteException {
            return null;
        }

        @Override
        public String getPreviousName(Account account) throws RemoteException {
            return null;
        }

        @Override
        public Account[] getSharedAccountsAsUser(int n) throws RemoteException {
            return null;
        }

        @Override
        public String getUserData(Account account, String string2) throws RemoteException {
            return null;
        }

        @Override
        public boolean hasAccountAccess(Account account, String string2, UserHandle userHandle) throws RemoteException {
            return false;
        }

        @Override
        public void hasFeatures(IAccountManagerResponse iAccountManagerResponse, Account account, String[] arrstring, String string2) throws RemoteException {
        }

        @Override
        public void invalidateAuthToken(String string2, String string3) throws RemoteException {
        }

        @Override
        public void isCredentialsUpdateSuggested(IAccountManagerResponse iAccountManagerResponse, Account account, String string2) throws RemoteException {
        }

        @Override
        public void onAccountAccessed(String string2) throws RemoteException {
        }

        @Override
        public String peekAuthToken(Account account, String string2) throws RemoteException {
            return null;
        }

        @Override
        public void registerAccountListener(String[] arrstring, String string2) throws RemoteException {
        }

        @Override
        public void removeAccount(IAccountManagerResponse iAccountManagerResponse, Account account, boolean bl) throws RemoteException {
        }

        @Override
        public void removeAccountAsUser(IAccountManagerResponse iAccountManagerResponse, Account account, boolean bl, int n) throws RemoteException {
        }

        @Override
        public boolean removeAccountExplicitly(Account account) throws RemoteException {
            return false;
        }

        @Override
        public boolean removeSharedAccountAsUser(Account account, int n) throws RemoteException {
            return false;
        }

        @Override
        public void renameAccount(IAccountManagerResponse iAccountManagerResponse, Account account, String string2) throws RemoteException {
        }

        @Override
        public boolean renameSharedAccountAsUser(Account account, String string2, int n) throws RemoteException {
            return false;
        }

        @Override
        public boolean setAccountVisibility(Account account, String string2, int n) throws RemoteException {
            return false;
        }

        @Override
        public void setAuthToken(Account account, String string2, String string3) throws RemoteException {
        }

        @Override
        public void setPassword(Account account, String string2) throws RemoteException {
        }

        @Override
        public void setUserData(Account account, String string2, String string3) throws RemoteException {
        }

        @Override
        public boolean someUserHasAccount(Account account) throws RemoteException {
            return false;
        }

        @Override
        public void startAddAccountSession(IAccountManagerResponse iAccountManagerResponse, String string2, String string3, String[] arrstring, boolean bl, Bundle bundle) throws RemoteException {
        }

        @Override
        public void startUpdateCredentialsSession(IAccountManagerResponse iAccountManagerResponse, Account account, String string2, boolean bl, Bundle bundle) throws RemoteException {
        }

        @Override
        public void unregisterAccountListener(String[] arrstring, String string2) throws RemoteException {
        }

        @Override
        public void updateAppPermission(Account account, String string2, int n, boolean bl) throws RemoteException {
        }

        @Override
        public void updateCredentials(IAccountManagerResponse iAccountManagerResponse, Account account, String string2, boolean bl, Bundle bundle) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IAccountManager {
        private static final String DESCRIPTOR = "android.accounts.IAccountManager";
        static final int TRANSACTION_accountAuthenticated = 29;
        static final int TRANSACTION_addAccount = 24;
        static final int TRANSACTION_addAccountAsUser = 25;
        static final int TRANSACTION_addAccountExplicitly = 11;
        static final int TRANSACTION_addAccountExplicitlyWithVisibility = 43;
        static final int TRANSACTION_addSharedAccountsFromParentUser = 33;
        static final int TRANSACTION_clearPassword = 20;
        static final int TRANSACTION_confirmCredentialsAsUser = 28;
        static final int TRANSACTION_copyAccountToUser = 15;
        static final int TRANSACTION_createRequestAccountAccessIntentSenderAsUser = 50;
        static final int TRANSACTION_editProperties = 27;
        static final int TRANSACTION_finishSessionAsUser = 39;
        static final int TRANSACTION_getAccountByTypeAndFeatures = 9;
        static final int TRANSACTION_getAccountVisibility = 45;
        static final int TRANSACTION_getAccounts = 4;
        static final int TRANSACTION_getAccountsAndVisibilityForPackage = 46;
        static final int TRANSACTION_getAccountsAsUser = 7;
        static final int TRANSACTION_getAccountsByFeatures = 10;
        static final int TRANSACTION_getAccountsByTypeForPackage = 6;
        static final int TRANSACTION_getAccountsForPackage = 5;
        static final int TRANSACTION_getAuthToken = 23;
        static final int TRANSACTION_getAuthTokenLabel = 30;
        static final int TRANSACTION_getAuthenticatorTypes = 3;
        static final int TRANSACTION_getPackagesAndVisibilityForAccount = 42;
        static final int TRANSACTION_getPassword = 1;
        static final int TRANSACTION_getPreviousName = 35;
        static final int TRANSACTION_getSharedAccountsAsUser = 31;
        static final int TRANSACTION_getUserData = 2;
        static final int TRANSACTION_hasAccountAccess = 49;
        static final int TRANSACTION_hasFeatures = 8;
        static final int TRANSACTION_invalidateAuthToken = 16;
        static final int TRANSACTION_isCredentialsUpdateSuggested = 41;
        static final int TRANSACTION_onAccountAccessed = 51;
        static final int TRANSACTION_peekAuthToken = 17;
        static final int TRANSACTION_registerAccountListener = 47;
        static final int TRANSACTION_removeAccount = 12;
        static final int TRANSACTION_removeAccountAsUser = 13;
        static final int TRANSACTION_removeAccountExplicitly = 14;
        static final int TRANSACTION_removeSharedAccountAsUser = 32;
        static final int TRANSACTION_renameAccount = 34;
        static final int TRANSACTION_renameSharedAccountAsUser = 36;
        static final int TRANSACTION_setAccountVisibility = 44;
        static final int TRANSACTION_setAuthToken = 18;
        static final int TRANSACTION_setPassword = 19;
        static final int TRANSACTION_setUserData = 21;
        static final int TRANSACTION_someUserHasAccount = 40;
        static final int TRANSACTION_startAddAccountSession = 37;
        static final int TRANSACTION_startUpdateCredentialsSession = 38;
        static final int TRANSACTION_unregisterAccountListener = 48;
        static final int TRANSACTION_updateAppPermission = 22;
        static final int TRANSACTION_updateCredentials = 26;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IAccountManager asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IAccountManager) {
                return (IAccountManager)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IAccountManager getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            switch (n) {
                default: {
                    return null;
                }
                case 51: {
                    return "onAccountAccessed";
                }
                case 50: {
                    return "createRequestAccountAccessIntentSenderAsUser";
                }
                case 49: {
                    return "hasAccountAccess";
                }
                case 48: {
                    return "unregisterAccountListener";
                }
                case 47: {
                    return "registerAccountListener";
                }
                case 46: {
                    return "getAccountsAndVisibilityForPackage";
                }
                case 45: {
                    return "getAccountVisibility";
                }
                case 44: {
                    return "setAccountVisibility";
                }
                case 43: {
                    return "addAccountExplicitlyWithVisibility";
                }
                case 42: {
                    return "getPackagesAndVisibilityForAccount";
                }
                case 41: {
                    return "isCredentialsUpdateSuggested";
                }
                case 40: {
                    return "someUserHasAccount";
                }
                case 39: {
                    return "finishSessionAsUser";
                }
                case 38: {
                    return "startUpdateCredentialsSession";
                }
                case 37: {
                    return "startAddAccountSession";
                }
                case 36: {
                    return "renameSharedAccountAsUser";
                }
                case 35: {
                    return "getPreviousName";
                }
                case 34: {
                    return "renameAccount";
                }
                case 33: {
                    return "addSharedAccountsFromParentUser";
                }
                case 32: {
                    return "removeSharedAccountAsUser";
                }
                case 31: {
                    return "getSharedAccountsAsUser";
                }
                case 30: {
                    return "getAuthTokenLabel";
                }
                case 29: {
                    return "accountAuthenticated";
                }
                case 28: {
                    return "confirmCredentialsAsUser";
                }
                case 27: {
                    return "editProperties";
                }
                case 26: {
                    return "updateCredentials";
                }
                case 25: {
                    return "addAccountAsUser";
                }
                case 24: {
                    return "addAccount";
                }
                case 23: {
                    return "getAuthToken";
                }
                case 22: {
                    return "updateAppPermission";
                }
                case 21: {
                    return "setUserData";
                }
                case 20: {
                    return "clearPassword";
                }
                case 19: {
                    return "setPassword";
                }
                case 18: {
                    return "setAuthToken";
                }
                case 17: {
                    return "peekAuthToken";
                }
                case 16: {
                    return "invalidateAuthToken";
                }
                case 15: {
                    return "copyAccountToUser";
                }
                case 14: {
                    return "removeAccountExplicitly";
                }
                case 13: {
                    return "removeAccountAsUser";
                }
                case 12: {
                    return "removeAccount";
                }
                case 11: {
                    return "addAccountExplicitly";
                }
                case 10: {
                    return "getAccountsByFeatures";
                }
                case 9: {
                    return "getAccountByTypeAndFeatures";
                }
                case 8: {
                    return "hasFeatures";
                }
                case 7: {
                    return "getAccountsAsUser";
                }
                case 6: {
                    return "getAccountsByTypeForPackage";
                }
                case 5: {
                    return "getAccountsForPackage";
                }
                case 4: {
                    return "getAccounts";
                }
                case 3: {
                    return "getAuthenticatorTypes";
                }
                case 2: {
                    return "getUserData";
                }
                case 1: 
            }
            return "getPassword";
        }

        public static boolean setDefaultImpl(IAccountManager iAccountManager) {
            if (Proxy.sDefaultImpl == null && iAccountManager != null) {
                Proxy.sDefaultImpl = iAccountManager;
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
            if (n != 1598968902) {
                boolean bl = false;
                boolean bl2 = false;
                boolean bl3 = false;
                boolean bl4 = false;
                switch (n) {
                    default: {
                        return super.onTransact(n, (Parcel)object, parcel, n2);
                    }
                    case 51: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.onAccountAccessed(((Parcel)object).readString());
                        parcel.writeNoException();
                        return true;
                    }
                    case 50: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        Account account = ((Parcel)object).readInt() != 0 ? Account.CREATOR.createFromParcel((Parcel)object) : null;
                        String string2 = ((Parcel)object).readString();
                        object = ((Parcel)object).readInt() != 0 ? UserHandle.CREATOR.createFromParcel((Parcel)object) : null;
                        object = this.createRequestAccountAccessIntentSenderAsUser(account, string2, (UserHandle)object);
                        parcel.writeNoException();
                        if (object != null) {
                            parcel.writeInt(1);
                            ((IntentSender)object).writeToParcel(parcel, 1);
                        } else {
                            parcel.writeInt(0);
                        }
                        return true;
                    }
                    case 49: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        Account account = ((Parcel)object).readInt() != 0 ? Account.CREATOR.createFromParcel((Parcel)object) : null;
                        String string3 = ((Parcel)object).readString();
                        object = ((Parcel)object).readInt() != 0 ? UserHandle.CREATOR.createFromParcel((Parcel)object) : null;
                        n = this.hasAccountAccess(account, string3, (UserHandle)object) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 48: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.unregisterAccountListener(((Parcel)object).createStringArray(), ((Parcel)object).readString());
                        parcel.writeNoException();
                        return true;
                    }
                    case 47: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.registerAccountListener(((Parcel)object).createStringArray(), ((Parcel)object).readString());
                        parcel.writeNoException();
                        return true;
                    }
                    case 46: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getAccountsAndVisibilityForPackage(((Parcel)object).readString(), ((Parcel)object).readString());
                        parcel.writeNoException();
                        parcel.writeMap((Map)object);
                        return true;
                    }
                    case 45: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        Account account = ((Parcel)object).readInt() != 0 ? Account.CREATOR.createFromParcel((Parcel)object) : null;
                        n = this.getAccountVisibility(account, ((Parcel)object).readString());
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 44: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        Account account = ((Parcel)object).readInt() != 0 ? Account.CREATOR.createFromParcel((Parcel)object) : null;
                        n = this.setAccountVisibility(account, ((Parcel)object).readString(), ((Parcel)object).readInt()) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 43: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        Account account = ((Parcel)object).readInt() != 0 ? Account.CREATOR.createFromParcel((Parcel)object) : null;
                        String string4 = ((Parcel)object).readString();
                        Bundle bundle = ((Parcel)object).readInt() != 0 ? Bundle.CREATOR.createFromParcel((Parcel)object) : null;
                        n = this.addAccountExplicitlyWithVisibility(account, string4, bundle, ((Parcel)object).readHashMap(this.getClass().getClassLoader())) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 42: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = ((Parcel)object).readInt() != 0 ? Account.CREATOR.createFromParcel((Parcel)object) : null;
                        object = this.getPackagesAndVisibilityForAccount((Account)object);
                        parcel.writeNoException();
                        parcel.writeMap((Map)object);
                        return true;
                    }
                    case 41: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        IAccountManagerResponse iAccountManagerResponse = IAccountManagerResponse.Stub.asInterface(((Parcel)object).readStrongBinder());
                        Account account = ((Parcel)object).readInt() != 0 ? Account.CREATOR.createFromParcel((Parcel)object) : null;
                        this.isCredentialsUpdateSuggested(iAccountManagerResponse, account, ((Parcel)object).readString());
                        parcel.writeNoException();
                        return true;
                    }
                    case 40: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = ((Parcel)object).readInt() != 0 ? Account.CREATOR.createFromParcel((Parcel)object) : null;
                        n = this.someUserHasAccount((Account)object) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 39: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        IAccountManagerResponse iAccountManagerResponse = IAccountManagerResponse.Stub.asInterface(((Parcel)object).readStrongBinder());
                        Bundle bundle = ((Parcel)object).readInt() != 0 ? Bundle.CREATOR.createFromParcel((Parcel)object) : null;
                        bl4 = ((Parcel)object).readInt() != 0;
                        Bundle bundle2 = ((Parcel)object).readInt() != 0 ? Bundle.CREATOR.createFromParcel((Parcel)object) : null;
                        this.finishSessionAsUser(iAccountManagerResponse, bundle, bl4, bundle2, ((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 38: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        IAccountManagerResponse iAccountManagerResponse = IAccountManagerResponse.Stub.asInterface(((Parcel)object).readStrongBinder());
                        Account account = ((Parcel)object).readInt() != 0 ? Account.CREATOR.createFromParcel((Parcel)object) : null;
                        String string5 = ((Parcel)object).readString();
                        bl4 = ((Parcel)object).readInt() != 0;
                        object = ((Parcel)object).readInt() != 0 ? Bundle.CREATOR.createFromParcel((Parcel)object) : null;
                        this.startUpdateCredentialsSession(iAccountManagerResponse, account, string5, bl4, (Bundle)object);
                        parcel.writeNoException();
                        return true;
                    }
                    case 37: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        IAccountManagerResponse iAccountManagerResponse = IAccountManagerResponse.Stub.asInterface(((Parcel)object).readStrongBinder());
                        String string6 = ((Parcel)object).readString();
                        String string7 = ((Parcel)object).readString();
                        String[] arrstring = ((Parcel)object).createStringArray();
                        bl4 = ((Parcel)object).readInt() != 0;
                        object = ((Parcel)object).readInt() != 0 ? Bundle.CREATOR.createFromParcel((Parcel)object) : null;
                        this.startAddAccountSession(iAccountManagerResponse, string6, string7, arrstring, bl4, (Bundle)object);
                        parcel.writeNoException();
                        return true;
                    }
                    case 36: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        Account account = ((Parcel)object).readInt() != 0 ? Account.CREATOR.createFromParcel((Parcel)object) : null;
                        n = this.renameSharedAccountAsUser(account, ((Parcel)object).readString(), ((Parcel)object).readInt()) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 35: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = ((Parcel)object).readInt() != 0 ? Account.CREATOR.createFromParcel((Parcel)object) : null;
                        object = this.getPreviousName((Account)object);
                        parcel.writeNoException();
                        parcel.writeString((String)object);
                        return true;
                    }
                    case 34: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        IAccountManagerResponse iAccountManagerResponse = IAccountManagerResponse.Stub.asInterface(((Parcel)object).readStrongBinder());
                        Account account = ((Parcel)object).readInt() != 0 ? Account.CREATOR.createFromParcel((Parcel)object) : null;
                        this.renameAccount(iAccountManagerResponse, account, ((Parcel)object).readString());
                        parcel.writeNoException();
                        return true;
                    }
                    case 33: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.addSharedAccountsFromParentUser(((Parcel)object).readInt(), ((Parcel)object).readInt(), ((Parcel)object).readString());
                        parcel.writeNoException();
                        return true;
                    }
                    case 32: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        Account account = ((Parcel)object).readInt() != 0 ? Account.CREATOR.createFromParcel((Parcel)object) : null;
                        n = this.removeSharedAccountAsUser(account, ((Parcel)object).readInt()) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 31: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getSharedAccountsAsUser(((Parcel)object).readInt());
                        parcel.writeNoException();
                        parcel.writeTypedArray((Parcelable[])object, 1);
                        return true;
                    }
                    case 30: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.getAuthTokenLabel(IAccountManagerResponse.Stub.asInterface(((Parcel)object).readStrongBinder()), ((Parcel)object).readString(), ((Parcel)object).readString());
                        parcel.writeNoException();
                        return true;
                    }
                    case 29: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = ((Parcel)object).readInt() != 0 ? Account.CREATOR.createFromParcel((Parcel)object) : null;
                        n = this.accountAuthenticated((Account)object) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 28: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        IAccountManagerResponse iAccountManagerResponse = IAccountManagerResponse.Stub.asInterface(((Parcel)object).readStrongBinder());
                        Account account = ((Parcel)object).readInt() != 0 ? Account.CREATOR.createFromParcel((Parcel)object) : null;
                        Bundle bundle = ((Parcel)object).readInt() != 0 ? Bundle.CREATOR.createFromParcel((Parcel)object) : null;
                        bl4 = ((Parcel)object).readInt() != 0;
                        this.confirmCredentialsAsUser(iAccountManagerResponse, account, bundle, bl4, ((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 27: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        IAccountManagerResponse iAccountManagerResponse = IAccountManagerResponse.Stub.asInterface(((Parcel)object).readStrongBinder());
                        String string8 = ((Parcel)object).readString();
                        if (((Parcel)object).readInt() != 0) {
                            bl4 = true;
                        }
                        this.editProperties(iAccountManagerResponse, string8, bl4);
                        parcel.writeNoException();
                        return true;
                    }
                    case 26: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        IAccountManagerResponse iAccountManagerResponse = IAccountManagerResponse.Stub.asInterface(((Parcel)object).readStrongBinder());
                        Account account = ((Parcel)object).readInt() != 0 ? Account.CREATOR.createFromParcel((Parcel)object) : null;
                        String string9 = ((Parcel)object).readString();
                        bl4 = ((Parcel)object).readInt() != 0;
                        object = ((Parcel)object).readInt() != 0 ? Bundle.CREATOR.createFromParcel((Parcel)object) : null;
                        this.updateCredentials(iAccountManagerResponse, account, string9, bl4, (Bundle)object);
                        parcel.writeNoException();
                        return true;
                    }
                    case 25: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        IAccountManagerResponse iAccountManagerResponse = IAccountManagerResponse.Stub.asInterface(((Parcel)object).readStrongBinder());
                        String string10 = ((Parcel)object).readString();
                        String string11 = ((Parcel)object).readString();
                        String[] arrstring = ((Parcel)object).createStringArray();
                        bl4 = ((Parcel)object).readInt() != 0;
                        Bundle bundle = ((Parcel)object).readInt() != 0 ? Bundle.CREATOR.createFromParcel((Parcel)object) : null;
                        this.addAccountAsUser(iAccountManagerResponse, string10, string11, arrstring, bl4, bundle, ((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 24: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        IAccountManagerResponse iAccountManagerResponse = IAccountManagerResponse.Stub.asInterface(((Parcel)object).readStrongBinder());
                        String string12 = ((Parcel)object).readString();
                        String string13 = ((Parcel)object).readString();
                        String[] arrstring = ((Parcel)object).createStringArray();
                        bl4 = ((Parcel)object).readInt() != 0;
                        object = ((Parcel)object).readInt() != 0 ? Bundle.CREATOR.createFromParcel((Parcel)object) : null;
                        this.addAccount(iAccountManagerResponse, string12, string13, arrstring, bl4, (Bundle)object);
                        parcel.writeNoException();
                        return true;
                    }
                    case 23: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        IAccountManagerResponse iAccountManagerResponse = IAccountManagerResponse.Stub.asInterface(((Parcel)object).readStrongBinder());
                        Account account = ((Parcel)object).readInt() != 0 ? Account.CREATOR.createFromParcel((Parcel)object) : null;
                        String string14 = ((Parcel)object).readString();
                        bl4 = ((Parcel)object).readInt() != 0;
                        bl = ((Parcel)object).readInt() != 0;
                        object = ((Parcel)object).readInt() != 0 ? Bundle.CREATOR.createFromParcel((Parcel)object) : null;
                        this.getAuthToken(iAccountManagerResponse, account, string14, bl4, bl, (Bundle)object);
                        parcel.writeNoException();
                        return true;
                    }
                    case 22: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        Account account = ((Parcel)object).readInt() != 0 ? Account.CREATOR.createFromParcel((Parcel)object) : null;
                        String string15 = ((Parcel)object).readString();
                        n = ((Parcel)object).readInt();
                        bl4 = bl;
                        if (((Parcel)object).readInt() != 0) {
                            bl4 = true;
                        }
                        this.updateAppPermission(account, string15, n, bl4);
                        parcel.writeNoException();
                        return true;
                    }
                    case 21: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        Account account = ((Parcel)object).readInt() != 0 ? Account.CREATOR.createFromParcel((Parcel)object) : null;
                        this.setUserData(account, ((Parcel)object).readString(), ((Parcel)object).readString());
                        parcel.writeNoException();
                        return true;
                    }
                    case 20: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = ((Parcel)object).readInt() != 0 ? Account.CREATOR.createFromParcel((Parcel)object) : null;
                        this.clearPassword((Account)object);
                        parcel.writeNoException();
                        return true;
                    }
                    case 19: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        Account account = ((Parcel)object).readInt() != 0 ? Account.CREATOR.createFromParcel((Parcel)object) : null;
                        this.setPassword(account, ((Parcel)object).readString());
                        parcel.writeNoException();
                        return true;
                    }
                    case 18: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        Account account = ((Parcel)object).readInt() != 0 ? Account.CREATOR.createFromParcel((Parcel)object) : null;
                        this.setAuthToken(account, ((Parcel)object).readString(), ((Parcel)object).readString());
                        parcel.writeNoException();
                        return true;
                    }
                    case 17: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        Account account = ((Parcel)object).readInt() != 0 ? Account.CREATOR.createFromParcel((Parcel)object) : null;
                        object = this.peekAuthToken(account, ((Parcel)object).readString());
                        parcel.writeNoException();
                        parcel.writeString((String)object);
                        return true;
                    }
                    case 16: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.invalidateAuthToken(((Parcel)object).readString(), ((Parcel)object).readString());
                        parcel.writeNoException();
                        return true;
                    }
                    case 15: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        IAccountManagerResponse iAccountManagerResponse = IAccountManagerResponse.Stub.asInterface(((Parcel)object).readStrongBinder());
                        Account account = ((Parcel)object).readInt() != 0 ? Account.CREATOR.createFromParcel((Parcel)object) : null;
                        this.copyAccountToUser(iAccountManagerResponse, account, ((Parcel)object).readInt(), ((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 14: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = ((Parcel)object).readInt() != 0 ? Account.CREATOR.createFromParcel((Parcel)object) : null;
                        n = this.removeAccountExplicitly((Account)object) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 13: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        IAccountManagerResponse iAccountManagerResponse = IAccountManagerResponse.Stub.asInterface(((Parcel)object).readStrongBinder());
                        Account account = ((Parcel)object).readInt() != 0 ? Account.CREATOR.createFromParcel((Parcel)object) : null;
                        bl4 = bl2;
                        if (((Parcel)object).readInt() != 0) {
                            bl4 = true;
                        }
                        this.removeAccountAsUser(iAccountManagerResponse, account, bl4, ((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 12: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        IAccountManagerResponse iAccountManagerResponse = IAccountManagerResponse.Stub.asInterface(((Parcel)object).readStrongBinder());
                        Account account = ((Parcel)object).readInt() != 0 ? Account.CREATOR.createFromParcel((Parcel)object) : null;
                        bl4 = bl3;
                        if (((Parcel)object).readInt() != 0) {
                            bl4 = true;
                        }
                        this.removeAccount(iAccountManagerResponse, account, bl4);
                        parcel.writeNoException();
                        return true;
                    }
                    case 11: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        Account account = ((Parcel)object).readInt() != 0 ? Account.CREATOR.createFromParcel((Parcel)object) : null;
                        String string16 = ((Parcel)object).readString();
                        object = ((Parcel)object).readInt() != 0 ? Bundle.CREATOR.createFromParcel((Parcel)object) : null;
                        n = this.addAccountExplicitly(account, string16, (Bundle)object) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 10: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.getAccountsByFeatures(IAccountManagerResponse.Stub.asInterface(((Parcel)object).readStrongBinder()), ((Parcel)object).readString(), ((Parcel)object).createStringArray(), ((Parcel)object).readString());
                        parcel.writeNoException();
                        return true;
                    }
                    case 9: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.getAccountByTypeAndFeatures(IAccountManagerResponse.Stub.asInterface(((Parcel)object).readStrongBinder()), ((Parcel)object).readString(), ((Parcel)object).createStringArray(), ((Parcel)object).readString());
                        parcel.writeNoException();
                        return true;
                    }
                    case 8: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        IAccountManagerResponse iAccountManagerResponse = IAccountManagerResponse.Stub.asInterface(((Parcel)object).readStrongBinder());
                        Account account = ((Parcel)object).readInt() != 0 ? Account.CREATOR.createFromParcel((Parcel)object) : null;
                        this.hasFeatures(iAccountManagerResponse, account, ((Parcel)object).createStringArray(), ((Parcel)object).readString());
                        parcel.writeNoException();
                        return true;
                    }
                    case 7: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getAccountsAsUser(((Parcel)object).readString(), ((Parcel)object).readInt(), ((Parcel)object).readString());
                        parcel.writeNoException();
                        parcel.writeTypedArray((Parcelable[])object, 1);
                        return true;
                    }
                    case 6: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getAccountsByTypeForPackage(((Parcel)object).readString(), ((Parcel)object).readString(), ((Parcel)object).readString());
                        parcel.writeNoException();
                        parcel.writeTypedArray((Parcelable[])object, 1);
                        return true;
                    }
                    case 5: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getAccountsForPackage(((Parcel)object).readString(), ((Parcel)object).readInt(), ((Parcel)object).readString());
                        parcel.writeNoException();
                        parcel.writeTypedArray((Parcelable[])object, 1);
                        return true;
                    }
                    case 4: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getAccounts(((Parcel)object).readString(), ((Parcel)object).readString());
                        parcel.writeNoException();
                        parcel.writeTypedArray((Parcelable[])object, 1);
                        return true;
                    }
                    case 3: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getAuthenticatorTypes(((Parcel)object).readInt());
                        parcel.writeNoException();
                        parcel.writeTypedArray((Parcelable[])object, 1);
                        return true;
                    }
                    case 2: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        Account account = ((Parcel)object).readInt() != 0 ? Account.CREATOR.createFromParcel((Parcel)object) : null;
                        object = this.getUserData(account, ((Parcel)object).readString());
                        parcel.writeNoException();
                        parcel.writeString((String)object);
                        return true;
                    }
                    case 1: 
                }
                ((Parcel)object).enforceInterface(DESCRIPTOR);
                object = ((Parcel)object).readInt() != 0 ? Account.CREATOR.createFromParcel((Parcel)object) : null;
                object = this.getPassword((Account)object);
                parcel.writeNoException();
                parcel.writeString((String)object);
                return true;
            }
            parcel.writeString(DESCRIPTOR);
            return true;
        }

        private static class Proxy
        implements IAccountManager {
            public static IAccountManager sDefaultImpl;
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
            public boolean accountAuthenticated(Account account) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean bl = true;
                    if (account != null) {
                        parcel.writeInt(1);
                        account.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(29, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        bl = Stub.getDefaultImpl().accountAuthenticated(account);
                        parcel2.recycle();
                        parcel.recycle();
                        return bl;
                    }
                    parcel2.readException();
                    int n = parcel2.readInt();
                    if (n == 0) {
                        bl = false;
                    }
                    parcel2.recycle();
                    parcel.recycle();
                    return bl;
                }
                catch (Throwable throwable) {
                    parcel2.recycle();
                    parcel.recycle();
                    throw throwable;
                }
            }

            /*
             * Loose catch block
             * WARNING - void declaration
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             * Lifted jumps to return sites
             */
            @Override
            public void addAccount(IAccountManagerResponse iAccountManagerResponse, String string2, String string3, String[] arrstring, boolean bl, Bundle bundle) throws RemoteException {
                Parcel parcel;
                void var1_7;
                Parcel parcel2;
                block14 : {
                    block13 : {
                        parcel2 = Parcel.obtain();
                        parcel = Parcel.obtain();
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        IBinder iBinder = iAccountManagerResponse != null ? iAccountManagerResponse.asBinder() : null;
                        parcel2.writeStrongBinder(iBinder);
                        try {
                            parcel2.writeString(string2);
                        }
                        catch (Throwable throwable) {
                            break block14;
                        }
                        try {
                            parcel2.writeString(string3);
                        }
                        catch (Throwable throwable) {
                            break block14;
                        }
                        try {
                            parcel2.writeStringArray(arrstring);
                            int n = bl ? 1 : 0;
                            parcel2.writeInt(n);
                            if (bundle != null) {
                                parcel2.writeInt(1);
                                bundle.writeToParcel(parcel2, 0);
                                break block13;
                            }
                            parcel2.writeInt(0);
                        }
                        catch (Throwable throwable) {}
                    }
                    try {
                        if (!this.mRemote.transact(24, parcel2, parcel, 0) && Stub.getDefaultImpl() != null) {
                            Stub.getDefaultImpl().addAccount(iAccountManagerResponse, string2, string3, arrstring, bl, bundle);
                            parcel.recycle();
                            parcel2.recycle();
                            return;
                        }
                        parcel.readException();
                        parcel.recycle();
                        parcel2.recycle();
                        return;
                    }
                    catch (Throwable throwable) {}
                    break block14;
                    catch (Throwable throwable) {
                        // empty catch block
                    }
                }
                parcel.recycle();
                parcel2.recycle();
                throw var1_7;
            }

            /*
             * Loose catch block
             * WARNING - void declaration
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             * Lifted jumps to return sites
             */
            @Override
            public void addAccountAsUser(IAccountManagerResponse iAccountManagerResponse, String string2, String string3, String[] arrstring, boolean bl, Bundle bundle, int n) throws RemoteException {
                Parcel parcel;
                void var1_7;
                Parcel parcel2;
                block14 : {
                    block13 : {
                        parcel = Parcel.obtain();
                        parcel2 = Parcel.obtain();
                        parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                        IBinder iBinder = iAccountManagerResponse != null ? iAccountManagerResponse.asBinder() : null;
                        parcel.writeStrongBinder(iBinder);
                        try {
                            parcel.writeString(string2);
                        }
                        catch (Throwable throwable) {
                            break block14;
                        }
                        try {
                            parcel.writeString(string3);
                        }
                        catch (Throwable throwable) {
                            break block14;
                        }
                        try {
                            parcel.writeStringArray(arrstring);
                            int n2 = bl ? 1 : 0;
                            parcel.writeInt(n2);
                            if (bundle != null) {
                                parcel.writeInt(1);
                                bundle.writeToParcel(parcel, 0);
                                break block13;
                            }
                            parcel.writeInt(0);
                        }
                        catch (Throwable throwable) {}
                    }
                    try {
                        parcel.writeInt(n);
                        if (!this.mRemote.transact(25, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                            Stub.getDefaultImpl().addAccountAsUser(iAccountManagerResponse, string2, string3, arrstring, bl, bundle, n);
                            parcel2.recycle();
                            parcel.recycle();
                            return;
                        }
                        parcel2.readException();
                        parcel2.recycle();
                        parcel.recycle();
                        return;
                    }
                    catch (Throwable throwable) {}
                    break block14;
                    catch (Throwable throwable) {
                        // empty catch block
                    }
                }
                parcel2.recycle();
                parcel.recycle();
                throw var1_7;
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public boolean addAccountExplicitly(Account account, String string2, Bundle bundle) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean bl = true;
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
                    if (!this.mRemote.transact(11, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        bl = Stub.getDefaultImpl().addAccountExplicitly(account, string2, bundle);
                        parcel2.recycle();
                        parcel.recycle();
                        return bl;
                    }
                    parcel2.readException();
                    int n = parcel2.readInt();
                    if (n == 0) {
                        bl = false;
                    }
                    parcel2.recycle();
                    parcel.recycle();
                    return bl;
                }
                catch (Throwable throwable) {
                    parcel2.recycle();
                    parcel.recycle();
                    throw throwable;
                }
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public boolean addAccountExplicitlyWithVisibility(Account account, String string2, Bundle bundle, Map map) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean bl = true;
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
                    parcel.writeMap(map);
                    if (!this.mRemote.transact(43, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        bl = Stub.getDefaultImpl().addAccountExplicitlyWithVisibility(account, string2, bundle, map);
                        parcel2.recycle();
                        parcel.recycle();
                        return bl;
                    }
                    parcel2.readException();
                    int n = parcel2.readInt();
                    if (n == 0) {
                        bl = false;
                    }
                    parcel2.recycle();
                    parcel.recycle();
                    return bl;
                }
                catch (Throwable throwable) {
                    parcel2.recycle();
                    parcel.recycle();
                    throw throwable;
                }
            }

            @Override
            public void addSharedAccountsFromParentUser(int n, int n2, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(33, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().addSharedAccountsFromParentUser(n, n2, string2);
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

            @Override
            public IBinder asBinder() {
                return this.mRemote;
            }

            @Override
            public void clearPassword(Account account) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (account != null) {
                        parcel.writeInt(1);
                        account.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(20, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().clearPassword(account);
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

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void confirmCredentialsAsUser(IAccountManagerResponse iAccountManagerResponse, Account account, Bundle bundle, boolean bl, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iAccountManagerResponse != null ? iAccountManagerResponse.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    int n2 = 1;
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
                    if (!bl) {
                        n2 = 0;
                    }
                    parcel.writeInt(n2);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(28, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().confirmCredentialsAsUser(iAccountManagerResponse, account, bundle, bl, n);
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

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void copyAccountToUser(IAccountManagerResponse iAccountManagerResponse, Account account, int n, int n2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iAccountManagerResponse != null ? iAccountManagerResponse.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (account != null) {
                        parcel.writeInt(1);
                        account.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(15, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().copyAccountToUser(iAccountManagerResponse, account, n, n2);
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

            /*
             * WARNING - void declaration
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public IntentSender createRequestAccountAccessIntentSenderAsUser(Account parcelable, String string2, UserHandle userHandle) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    void var3_8;
                    void var2_7;
                    void var1_5;
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (parcelable != null) {
                        parcel.writeInt(1);
                        ((Account)parcelable).writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeString((String)var2_7);
                    if (var3_8 != null) {
                        parcel.writeInt(1);
                        var3_8.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(50, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        IntentSender intentSender = Stub.getDefaultImpl().createRequestAccountAccessIntentSenderAsUser((Account)parcelable, (String)var2_7, (UserHandle)var3_8);
                        parcel2.recycle();
                        parcel.recycle();
                        return intentSender;
                    }
                    parcel2.readException();
                    if (parcel2.readInt() != 0) {
                        IntentSender intentSender = IntentSender.CREATOR.createFromParcel(parcel2);
                    } else {
                        Object var1_4 = null;
                    }
                    parcel2.recycle();
                    parcel.recycle();
                    return var1_5;
                }
                catch (Throwable throwable) {
                    parcel2.recycle();
                    parcel.recycle();
                    throw throwable;
                }
            }

            @Override
            public void editProperties(IAccountManagerResponse iAccountManagerResponse, String string2, boolean bl) throws RemoteException {
                Parcel parcel;
                IBinder iBinder;
                Parcel parcel2;
                block8 : {
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (iAccountManagerResponse == null) break block8;
                    iBinder = iAccountManagerResponse.asBinder();
                }
                iBinder = null;
                parcel.writeStrongBinder(iBinder);
                parcel.writeString(string2);
                int n = bl ? 1 : 0;
                try {
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(27, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().editProperties(iAccountManagerResponse, string2, bl);
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

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void finishSessionAsUser(IAccountManagerResponse iAccountManagerResponse, Bundle bundle, boolean bl, Bundle bundle2, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iAccountManagerResponse != null ? iAccountManagerResponse.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (bundle != null) {
                        parcel.writeInt(1);
                        bundle.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    int n2 = bl ? 1 : 0;
                    parcel.writeInt(n2);
                    if (bundle2 != null) {
                        parcel.writeInt(1);
                        bundle2.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(39, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().finishSessionAsUser(iAccountManagerResponse, bundle, bl, bundle2, n);
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

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void getAccountByTypeAndFeatures(IAccountManagerResponse iAccountManagerResponse, String string2, String[] arrstring, String string3) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iAccountManagerResponse != null ? iAccountManagerResponse.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    parcel.writeString(string2);
                    parcel.writeStringArray(arrstring);
                    parcel.writeString(string3);
                    if (!this.mRemote.transact(9, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().getAccountByTypeAndFeatures(iAccountManagerResponse, string2, arrstring, string3);
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

            @Override
            public int getAccountVisibility(Account account, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (account != null) {
                        parcel.writeInt(1);
                        account.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(45, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        int n = Stub.getDefaultImpl().getAccountVisibility(account, string2);
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
            public Account[] getAccounts(String arraccount, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString((String)arraccount);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(4, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        arraccount = Stub.getDefaultImpl().getAccounts((String)arraccount, string2);
                        return arraccount;
                    }
                    parcel2.readException();
                    arraccount = parcel2.createTypedArray(Account.CREATOR);
                    return arraccount;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public Map getAccountsAndVisibilityForPackage(String object, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString((String)object);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(46, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        object = Stub.getDefaultImpl().getAccountsAndVisibilityForPackage((String)object, string2);
                        return object;
                    }
                    parcel2.readException();
                    object = parcel2.readHashMap(this.getClass().getClassLoader());
                    return object;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public Account[] getAccountsAsUser(String arraccount, int n, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString((String)arraccount);
                    parcel.writeInt(n);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(7, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        arraccount = Stub.getDefaultImpl().getAccountsAsUser((String)arraccount, n, string2);
                        return arraccount;
                    }
                    parcel2.readException();
                    arraccount = parcel2.createTypedArray(Account.CREATOR);
                    return arraccount;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void getAccountsByFeatures(IAccountManagerResponse iAccountManagerResponse, String string2, String[] arrstring, String string3) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iAccountManagerResponse != null ? iAccountManagerResponse.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    parcel.writeString(string2);
                    parcel.writeStringArray(arrstring);
                    parcel.writeString(string3);
                    if (!this.mRemote.transact(10, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().getAccountsByFeatures(iAccountManagerResponse, string2, arrstring, string3);
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

            @Override
            public Account[] getAccountsByTypeForPackage(String arraccount, String string2, String string3) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString((String)arraccount);
                    parcel.writeString(string2);
                    parcel.writeString(string3);
                    if (!this.mRemote.transact(6, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        arraccount = Stub.getDefaultImpl().getAccountsByTypeForPackage((String)arraccount, string2, string3);
                        return arraccount;
                    }
                    parcel2.readException();
                    arraccount = parcel2.createTypedArray(Account.CREATOR);
                    return arraccount;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public Account[] getAccountsForPackage(String arraccount, int n, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString((String)arraccount);
                    parcel.writeInt(n);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(5, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        arraccount = Stub.getDefaultImpl().getAccountsForPackage((String)arraccount, n, string2);
                        return arraccount;
                    }
                    parcel2.readException();
                    arraccount = parcel2.createTypedArray(Account.CREATOR);
                    return arraccount;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            /*
             * Loose catch block
             * WARNING - void declaration
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             * Lifted jumps to return sites
             */
            @Override
            public void getAuthToken(IAccountManagerResponse iAccountManagerResponse, Account account, String string2, boolean bl, boolean bl2, Bundle bundle) throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                void var1_5;
                block12 : {
                    block11 : {
                        block10 : {
                            parcel2 = Parcel.obtain();
                            parcel = Parcel.obtain();
                            parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                            IBinder iBinder = iAccountManagerResponse != null ? iAccountManagerResponse.asBinder() : null;
                            parcel2.writeStrongBinder(iBinder);
                            if (account != null) {
                                parcel2.writeInt(1);
                                account.writeToParcel(parcel2, 0);
                                break block10;
                            }
                            parcel2.writeInt(0);
                        }
                        try {
                            parcel2.writeString(string2);
                            int n = bl ? 1 : 0;
                            parcel2.writeInt(n);
                            n = bl2 ? 1 : 0;
                            parcel2.writeInt(n);
                            if (bundle != null) {
                                parcel2.writeInt(1);
                                bundle.writeToParcel(parcel2, 0);
                                break block11;
                            }
                            parcel2.writeInt(0);
                        }
                        catch (Throwable throwable) {}
                    }
                    try {
                        if (!this.mRemote.transact(23, parcel2, parcel, 0) && Stub.getDefaultImpl() != null) {
                            Stub.getDefaultImpl().getAuthToken(iAccountManagerResponse, account, string2, bl, bl2, bundle);
                            parcel.recycle();
                            parcel2.recycle();
                            return;
                        }
                        parcel.readException();
                        parcel.recycle();
                        parcel2.recycle();
                        return;
                    }
                    catch (Throwable throwable) {}
                    break block12;
                    catch (Throwable throwable) {
                        // empty catch block
                    }
                }
                parcel.recycle();
                parcel2.recycle();
                throw var1_5;
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void getAuthTokenLabel(IAccountManagerResponse iAccountManagerResponse, String string2, String string3) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iAccountManagerResponse != null ? iAccountManagerResponse.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    parcel.writeString(string2);
                    parcel.writeString(string3);
                    if (!this.mRemote.transact(30, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().getAuthTokenLabel(iAccountManagerResponse, string2, string3);
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

            @Override
            public AuthenticatorDescription[] getAuthenticatorTypes(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(3, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        AuthenticatorDescription[] arrauthenticatorDescription = Stub.getDefaultImpl().getAuthenticatorTypes(n);
                        return arrauthenticatorDescription;
                    }
                    parcel2.readException();
                    AuthenticatorDescription[] arrauthenticatorDescription = parcel2.createTypedArray(AuthenticatorDescription.CREATOR);
                    return arrauthenticatorDescription;
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
            public Map getPackagesAndVisibilityForAccount(Account object) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (object != null) {
                        parcel.writeInt(1);
                        ((Account)object).writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(42, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        object = Stub.getDefaultImpl().getPackagesAndVisibilityForAccount((Account)object);
                        return object;
                    }
                    parcel2.readException();
                    object = parcel2.readHashMap(this.getClass().getClassLoader());
                    return object;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public String getPassword(Account object) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (object != null) {
                        parcel.writeInt(1);
                        ((Account)object).writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(1, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        object = Stub.getDefaultImpl().getPassword((Account)object);
                        return object;
                    }
                    parcel2.readException();
                    object = parcel2.readString();
                    return object;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public String getPreviousName(Account object) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (object != null) {
                        parcel.writeInt(1);
                        ((Account)object).writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(35, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        object = Stub.getDefaultImpl().getPreviousName((Account)object);
                        return object;
                    }
                    parcel2.readException();
                    object = parcel2.readString();
                    return object;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public Account[] getSharedAccountsAsUser(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(31, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Account[] arraccount = Stub.getDefaultImpl().getSharedAccountsAsUser(n);
                        return arraccount;
                    }
                    parcel2.readException();
                    Account[] arraccount = parcel2.createTypedArray(Account.CREATOR);
                    return arraccount;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public String getUserData(Account object, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (object != null) {
                        parcel.writeInt(1);
                        ((Account)object).writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(2, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        object = Stub.getDefaultImpl().getUserData((Account)object, string2);
                        return object;
                    }
                    parcel2.readException();
                    object = parcel2.readString();
                    return object;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public boolean hasAccountAccess(Account account, String string2, UserHandle userHandle) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean bl = true;
                    if (account != null) {
                        parcel.writeInt(1);
                        account.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeString(string2);
                    if (userHandle != null) {
                        parcel.writeInt(1);
                        userHandle.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(49, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        bl = Stub.getDefaultImpl().hasAccountAccess(account, string2, userHandle);
                        parcel2.recycle();
                        parcel.recycle();
                        return bl;
                    }
                    parcel2.readException();
                    int n = parcel2.readInt();
                    if (n == 0) {
                        bl = false;
                    }
                    parcel2.recycle();
                    parcel.recycle();
                    return bl;
                }
                catch (Throwable throwable) {
                    parcel2.recycle();
                    parcel.recycle();
                    throw throwable;
                }
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void hasFeatures(IAccountManagerResponse iAccountManagerResponse, Account account, String[] arrstring, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iAccountManagerResponse != null ? iAccountManagerResponse.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (account != null) {
                        parcel.writeInt(1);
                        account.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeStringArray(arrstring);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(8, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().hasFeatures(iAccountManagerResponse, account, arrstring, string2);
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

            @Override
            public void invalidateAuthToken(String string2, String string3) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    parcel.writeString(string3);
                    if (!this.mRemote.transact(16, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().invalidateAuthToken(string2, string3);
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

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void isCredentialsUpdateSuggested(IAccountManagerResponse iAccountManagerResponse, Account account, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iAccountManagerResponse != null ? iAccountManagerResponse.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (account != null) {
                        parcel.writeInt(1);
                        account.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(41, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().isCredentialsUpdateSuggested(iAccountManagerResponse, account, string2);
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

            @Override
            public void onAccountAccessed(String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(51, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onAccountAccessed(string2);
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

            @Override
            public String peekAuthToken(Account object, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (object != null) {
                        parcel.writeInt(1);
                        ((Account)object).writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(17, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        object = Stub.getDefaultImpl().peekAuthToken((Account)object, string2);
                        return object;
                    }
                    parcel2.readException();
                    object = parcel2.readString();
                    return object;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void registerAccountListener(String[] arrstring, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeStringArray(arrstring);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(47, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().registerAccountListener(arrstring, string2);
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

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void removeAccount(IAccountManagerResponse iAccountManagerResponse, Account account, boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iAccountManagerResponse != null ? iAccountManagerResponse.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    int n = 1;
                    if (account != null) {
                        parcel.writeInt(1);
                        account.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!bl) {
                        n = 0;
                    }
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(12, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().removeAccount(iAccountManagerResponse, account, bl);
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

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void removeAccountAsUser(IAccountManagerResponse iAccountManagerResponse, Account account, boolean bl, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iAccountManagerResponse != null ? iAccountManagerResponse.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    int n2 = 1;
                    if (account != null) {
                        parcel.writeInt(1);
                        account.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!bl) {
                        n2 = 0;
                    }
                    parcel.writeInt(n2);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(13, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().removeAccountAsUser(iAccountManagerResponse, account, bl, n);
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

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public boolean removeAccountExplicitly(Account account) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean bl = true;
                    if (account != null) {
                        parcel.writeInt(1);
                        account.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(14, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        bl = Stub.getDefaultImpl().removeAccountExplicitly(account);
                        parcel2.recycle();
                        parcel.recycle();
                        return bl;
                    }
                    parcel2.readException();
                    int n = parcel2.readInt();
                    if (n == 0) {
                        bl = false;
                    }
                    parcel2.recycle();
                    parcel.recycle();
                    return bl;
                }
                catch (Throwable throwable) {
                    parcel2.recycle();
                    parcel.recycle();
                    throw throwable;
                }
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public boolean removeSharedAccountAsUser(Account account, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean bl = true;
                    if (account != null) {
                        parcel.writeInt(1);
                        account.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(32, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        bl = Stub.getDefaultImpl().removeSharedAccountAsUser(account, n);
                        parcel2.recycle();
                        parcel.recycle();
                        return bl;
                    }
                    parcel2.readException();
                    n = parcel2.readInt();
                    if (n == 0) {
                        bl = false;
                    }
                    parcel2.recycle();
                    parcel.recycle();
                    return bl;
                }
                catch (Throwable throwable) {
                    parcel2.recycle();
                    parcel.recycle();
                    throw throwable;
                }
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void renameAccount(IAccountManagerResponse iAccountManagerResponse, Account account, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iAccountManagerResponse != null ? iAccountManagerResponse.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (account != null) {
                        parcel.writeInt(1);
                        account.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(34, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().renameAccount(iAccountManagerResponse, account, string2);
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

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public boolean renameSharedAccountAsUser(Account account, String string2, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean bl = true;
                    if (account != null) {
                        parcel.writeInt(1);
                        account.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeString(string2);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(36, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        bl = Stub.getDefaultImpl().renameSharedAccountAsUser(account, string2, n);
                        parcel2.recycle();
                        parcel.recycle();
                        return bl;
                    }
                    parcel2.readException();
                    n = parcel2.readInt();
                    if (n == 0) {
                        bl = false;
                    }
                    parcel2.recycle();
                    parcel.recycle();
                    return bl;
                }
                catch (Throwable throwable) {
                    parcel2.recycle();
                    parcel.recycle();
                    throw throwable;
                }
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public boolean setAccountVisibility(Account account, String string2, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean bl = true;
                    if (account != null) {
                        parcel.writeInt(1);
                        account.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeString(string2);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(44, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        bl = Stub.getDefaultImpl().setAccountVisibility(account, string2, n);
                        parcel2.recycle();
                        parcel.recycle();
                        return bl;
                    }
                    parcel2.readException();
                    n = parcel2.readInt();
                    if (n == 0) {
                        bl = false;
                    }
                    parcel2.recycle();
                    parcel.recycle();
                    return bl;
                }
                catch (Throwable throwable) {
                    parcel2.recycle();
                    parcel.recycle();
                    throw throwable;
                }
            }

            @Override
            public void setAuthToken(Account account, String string2, String string3) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (account != null) {
                        parcel.writeInt(1);
                        account.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeString(string2);
                    parcel.writeString(string3);
                    if (!this.mRemote.transact(18, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setAuthToken(account, string2, string3);
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

            @Override
            public void setPassword(Account account, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (account != null) {
                        parcel.writeInt(1);
                        account.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(19, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setPassword(account, string2);
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

            @Override
            public void setUserData(Account account, String string2, String string3) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (account != null) {
                        parcel.writeInt(1);
                        account.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeString(string2);
                    parcel.writeString(string3);
                    if (!this.mRemote.transact(21, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setUserData(account, string2, string3);
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

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public boolean someUserHasAccount(Account account) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean bl = true;
                    if (account != null) {
                        parcel.writeInt(1);
                        account.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(40, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        bl = Stub.getDefaultImpl().someUserHasAccount(account);
                        parcel2.recycle();
                        parcel.recycle();
                        return bl;
                    }
                    parcel2.readException();
                    int n = parcel2.readInt();
                    if (n == 0) {
                        bl = false;
                    }
                    parcel2.recycle();
                    parcel.recycle();
                    return bl;
                }
                catch (Throwable throwable) {
                    parcel2.recycle();
                    parcel.recycle();
                    throw throwable;
                }
            }

            /*
             * Loose catch block
             * WARNING - void declaration
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             * Lifted jumps to return sites
             */
            @Override
            public void startAddAccountSession(IAccountManagerResponse iAccountManagerResponse, String string2, String string3, String[] arrstring, boolean bl, Bundle bundle) throws RemoteException {
                Parcel parcel;
                void var1_7;
                Parcel parcel2;
                block14 : {
                    block13 : {
                        parcel2 = Parcel.obtain();
                        parcel = Parcel.obtain();
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        IBinder iBinder = iAccountManagerResponse != null ? iAccountManagerResponse.asBinder() : null;
                        parcel2.writeStrongBinder(iBinder);
                        try {
                            parcel2.writeString(string2);
                        }
                        catch (Throwable throwable) {
                            break block14;
                        }
                        try {
                            parcel2.writeString(string3);
                        }
                        catch (Throwable throwable) {
                            break block14;
                        }
                        try {
                            parcel2.writeStringArray(arrstring);
                            int n = bl ? 1 : 0;
                            parcel2.writeInt(n);
                            if (bundle != null) {
                                parcel2.writeInt(1);
                                bundle.writeToParcel(parcel2, 0);
                                break block13;
                            }
                            parcel2.writeInt(0);
                        }
                        catch (Throwable throwable) {}
                    }
                    try {
                        if (!this.mRemote.transact(37, parcel2, parcel, 0) && Stub.getDefaultImpl() != null) {
                            Stub.getDefaultImpl().startAddAccountSession(iAccountManagerResponse, string2, string3, arrstring, bl, bundle);
                            parcel.recycle();
                            parcel2.recycle();
                            return;
                        }
                        parcel.readException();
                        parcel.recycle();
                        parcel2.recycle();
                        return;
                    }
                    catch (Throwable throwable) {}
                    break block14;
                    catch (Throwable throwable) {
                        // empty catch block
                    }
                }
                parcel.recycle();
                parcel2.recycle();
                throw var1_7;
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void startUpdateCredentialsSession(IAccountManagerResponse iAccountManagerResponse, Account account, String string2, boolean bl, Bundle bundle) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iAccountManagerResponse != null ? iAccountManagerResponse.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (account != null) {
                        parcel.writeInt(1);
                        account.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeString(string2);
                    int n = bl ? 1 : 0;
                    parcel.writeInt(n);
                    if (bundle != null) {
                        parcel.writeInt(1);
                        bundle.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(38, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().startUpdateCredentialsSession(iAccountManagerResponse, account, string2, bl, bundle);
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

            @Override
            public void unregisterAccountListener(String[] arrstring, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeStringArray(arrstring);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(48, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().unregisterAccountListener(arrstring, string2);
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

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void updateAppPermission(Account account, String string2, int n, boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    int n2 = 1;
                    if (account != null) {
                        parcel.writeInt(1);
                        account.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeString(string2);
                    parcel.writeInt(n);
                    if (!bl) {
                        n2 = 0;
                    }
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(22, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().updateAppPermission(account, string2, n, bl);
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

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void updateCredentials(IAccountManagerResponse iAccountManagerResponse, Account account, String string2, boolean bl, Bundle bundle) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iAccountManagerResponse != null ? iAccountManagerResponse.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (account != null) {
                        parcel.writeInt(1);
                        account.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeString(string2);
                    int n = bl ? 1 : 0;
                    parcel.writeInt(n);
                    if (bundle != null) {
                        parcel.writeInt(1);
                        bundle.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(26, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().updateCredentials(iAccountManagerResponse, account, string2, bl, bundle);
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

