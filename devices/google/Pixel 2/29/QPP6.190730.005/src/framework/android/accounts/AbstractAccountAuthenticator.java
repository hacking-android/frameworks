/*
 * Decompiled with CFR 0.145.
 */
package android.accounts;

import android.accounts.Account;
import android.accounts.AccountAuthenticatorResponse;
import android.accounts.AccountManager;
import android.accounts.IAccountAuthenticator;
import android.accounts.IAccountAuthenticatorResponse;
import android.accounts.NetworkErrorException;
import android.content.Context;
import android.os.BaseBundle;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcelable;
import android.os.RemoteException;
import android.text.TextUtils;
import android.util.Log;
import java.util.Arrays;
import java.util.Set;

public abstract class AbstractAccountAuthenticator {
    private static final String KEY_ACCOUNT = "android.accounts.AbstractAccountAuthenticator.KEY_ACCOUNT";
    private static final String KEY_AUTH_TOKEN_TYPE = "android.accounts.AbstractAccountAuthenticato.KEY_AUTH_TOKEN_TYPE";
    public static final String KEY_CUSTOM_TOKEN_EXPIRY = "android.accounts.expiry";
    private static final String KEY_OPTIONS = "android.accounts.AbstractAccountAuthenticator.KEY_OPTIONS";
    private static final String KEY_REQUIRED_FEATURES = "android.accounts.AbstractAccountAuthenticator.KEY_REQUIRED_FEATURES";
    private static final String TAG = "AccountAuthenticator";
    private final Context mContext;
    private Transport mTransport = new Transport();

    public AbstractAccountAuthenticator(Context context) {
        this.mContext = context;
    }

    private void checkBinderPermission() {
        int n = Binder.getCallingUid();
        if (this.mContext.checkCallingOrSelfPermission("android.permission.ACCOUNT_MANAGER") == 0) {
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("caller uid ");
        stringBuilder.append(n);
        stringBuilder.append(" lacks ");
        stringBuilder.append("android.permission.ACCOUNT_MANAGER");
        throw new SecurityException(stringBuilder.toString());
    }

    private void handleException(IAccountAuthenticatorResponse iAccountAuthenticatorResponse, String string2, String charSequence, Exception exception) throws RemoteException {
        if (exception instanceof NetworkErrorException) {
            if (Log.isLoggable(TAG, 2)) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(string2);
                stringBuilder.append("(");
                stringBuilder.append((String)charSequence);
                stringBuilder.append(")");
                Log.v(TAG, stringBuilder.toString(), exception);
            }
            iAccountAuthenticatorResponse.onError(3, exception.getMessage());
        } else if (exception instanceof UnsupportedOperationException) {
            if (Log.isLoggable(TAG, 2)) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(string2);
                stringBuilder.append("(");
                stringBuilder.append((String)charSequence);
                stringBuilder.append(")");
                Log.v(TAG, stringBuilder.toString(), exception);
            }
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append(string2);
            ((StringBuilder)charSequence).append(" not supported");
            iAccountAuthenticatorResponse.onError(6, ((StringBuilder)charSequence).toString());
        } else if (exception instanceof IllegalArgumentException) {
            if (Log.isLoggable(TAG, 2)) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(string2);
                stringBuilder.append("(");
                stringBuilder.append((String)charSequence);
                stringBuilder.append(")");
                Log.v(TAG, stringBuilder.toString(), exception);
            }
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append(string2);
            ((StringBuilder)charSequence).append(" not supported");
            iAccountAuthenticatorResponse.onError(7, ((StringBuilder)charSequence).toString());
        } else {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(string2);
            stringBuilder.append("(");
            stringBuilder.append((String)charSequence);
            stringBuilder.append(")");
            Log.w(TAG, stringBuilder.toString(), exception);
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append(string2);
            ((StringBuilder)charSequence).append(" failed");
            iAccountAuthenticatorResponse.onError(1, ((StringBuilder)charSequence).toString());
        }
    }

    public abstract Bundle addAccount(AccountAuthenticatorResponse var1, String var2, String var3, String[] var4, Bundle var5) throws NetworkErrorException;

    public Bundle addAccountFromCredentials(final AccountAuthenticatorResponse accountAuthenticatorResponse, Account account, Bundle bundle) throws NetworkErrorException {
        new Thread(new Runnable(){

            @Override
            public void run() {
                Bundle bundle = new Bundle();
                bundle.putBoolean("booleanResult", false);
                accountAuthenticatorResponse.onResult(bundle);
            }
        }).start();
        return null;
    }

    public abstract Bundle confirmCredentials(AccountAuthenticatorResponse var1, Account var2, Bundle var3) throws NetworkErrorException;

    public abstract Bundle editProperties(AccountAuthenticatorResponse var1, String var2);

    public Bundle finishSession(AccountAuthenticatorResponse parcelable, String object, Bundle bundle) throws NetworkErrorException {
        if (TextUtils.isEmpty((CharSequence)object)) {
            Log.e("AccountAuthenticator", "Account type cannot be empty.");
            parcelable = new Bundle();
            ((BaseBundle)((Object)parcelable)).putInt("errorCode", 7);
            ((BaseBundle)((Object)parcelable)).putString("errorMessage", "accountType cannot be empty.");
            return parcelable;
        }
        if (bundle == null) {
            Log.e("AccountAuthenticator", "Session bundle cannot be null.");
            parcelable = new Bundle();
            ((BaseBundle)((Object)parcelable)).putInt("errorCode", 7);
            ((BaseBundle)((Object)parcelable)).putString("errorMessage", "sessionBundle cannot be null.");
            return parcelable;
        }
        if (!bundle.containsKey("android.accounts.AbstractAccountAuthenticato.KEY_AUTH_TOKEN_TYPE")) {
            object = new Bundle();
            ((BaseBundle)object).putInt("errorCode", 6);
            ((BaseBundle)object).putString("errorMessage", "Authenticator must override finishSession if startAddAccountSession or startUpdateCredentialsSession is overridden.");
            ((AccountAuthenticatorResponse)parcelable).onResult((Bundle)object);
            return object;
        }
        String string2 = bundle.getString("android.accounts.AbstractAccountAuthenticato.KEY_AUTH_TOKEN_TYPE");
        Bundle bundle2 = bundle.getBundle("android.accounts.AbstractAccountAuthenticator.KEY_OPTIONS");
        String[] arrstring = bundle.getStringArray("android.accounts.AbstractAccountAuthenticator.KEY_REQUIRED_FEATURES");
        Account account = (Account)bundle.getParcelable("android.accounts.AbstractAccountAuthenticator.KEY_ACCOUNT");
        boolean bl = bundle.containsKey("android.accounts.AbstractAccountAuthenticator.KEY_ACCOUNT");
        bundle = new Bundle(bundle);
        bundle.remove("android.accounts.AbstractAccountAuthenticato.KEY_AUTH_TOKEN_TYPE");
        bundle.remove("android.accounts.AbstractAccountAuthenticator.KEY_REQUIRED_FEATURES");
        bundle.remove("android.accounts.AbstractAccountAuthenticator.KEY_OPTIONS");
        bundle.remove("android.accounts.AbstractAccountAuthenticator.KEY_ACCOUNT");
        if (bundle2 != null) {
            bundle2.putAll(bundle);
            bundle = bundle2;
        }
        if (bl) {
            return this.updateCredentials((AccountAuthenticatorResponse)parcelable, account, string2, bundle2);
        }
        return this.addAccount((AccountAuthenticatorResponse)parcelable, (String)object, string2, arrstring, bundle);
    }

    public Bundle getAccountCredentialsForCloning(final AccountAuthenticatorResponse accountAuthenticatorResponse, Account account) throws NetworkErrorException {
        new Thread(new Runnable(){

            @Override
            public void run() {
                Bundle bundle = new Bundle();
                bundle.putBoolean("booleanResult", false);
                accountAuthenticatorResponse.onResult(bundle);
            }
        }).start();
        return null;
    }

    public Bundle getAccountRemovalAllowed(AccountAuthenticatorResponse parcelable, Account account) throws NetworkErrorException {
        parcelable = new Bundle();
        ((BaseBundle)((Object)parcelable)).putBoolean("booleanResult", true);
        return parcelable;
    }

    public abstract Bundle getAuthToken(AccountAuthenticatorResponse var1, Account var2, String var3, Bundle var4) throws NetworkErrorException;

    public abstract String getAuthTokenLabel(String var1);

    public final IBinder getIBinder() {
        return this.mTransport.asBinder();
    }

    public abstract Bundle hasFeatures(AccountAuthenticatorResponse var1, Account var2, String[] var3) throws NetworkErrorException;

    public Bundle isCredentialsUpdateSuggested(AccountAuthenticatorResponse parcelable, Account account, String string2) throws NetworkErrorException {
        parcelable = new Bundle();
        ((BaseBundle)((Object)parcelable)).putBoolean("booleanResult", false);
        return parcelable;
    }

    public Bundle startAddAccountSession(final AccountAuthenticatorResponse accountAuthenticatorResponse, String string2, final String string3, final String[] arrstring, final Bundle bundle) throws NetworkErrorException {
        new Thread(new Runnable(){

            @Override
            public void run() {
                Bundle bundle3 = new Bundle();
                bundle3.putString(AbstractAccountAuthenticator.KEY_AUTH_TOKEN_TYPE, string3);
                bundle3.putStringArray(AbstractAccountAuthenticator.KEY_REQUIRED_FEATURES, arrstring);
                bundle3.putBundle(AbstractAccountAuthenticator.KEY_OPTIONS, bundle);
                Bundle bundle2 = new Bundle();
                bundle2.putBundle("accountSessionBundle", bundle3);
                accountAuthenticatorResponse.onResult(bundle2);
            }
        }).start();
        return null;
    }

    public Bundle startUpdateCredentialsSession(final AccountAuthenticatorResponse accountAuthenticatorResponse, final Account account, final String string2, final Bundle bundle) throws NetworkErrorException {
        new Thread(new Runnable(){

            @Override
            public void run() {
                Bundle bundle3 = new Bundle();
                bundle3.putString(AbstractAccountAuthenticator.KEY_AUTH_TOKEN_TYPE, string2);
                bundle3.putParcelable(AbstractAccountAuthenticator.KEY_ACCOUNT, account);
                bundle3.putBundle(AbstractAccountAuthenticator.KEY_OPTIONS, bundle);
                Bundle bundle2 = new Bundle();
                bundle2.putBundle("accountSessionBundle", bundle3);
                accountAuthenticatorResponse.onResult(bundle2);
            }
        }).start();
        return null;
    }

    public abstract Bundle updateCredentials(AccountAuthenticatorResponse var1, Account var2, String var3, Bundle var4) throws NetworkErrorException;

    private class Transport
    extends IAccountAuthenticator.Stub {
        private Transport() {
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void addAccount(IAccountAuthenticatorResponse iAccountAuthenticatorResponse, String string2, String charSequence, String[] arrobject, Bundle bundle) throws RemoteException {
            Object object;
            Object object2;
            if (Log.isLoggable(AbstractAccountAuthenticator.TAG, 2)) {
                object = new StringBuilder();
                ((StringBuilder)object).append("addAccount: accountType ");
                ((StringBuilder)object).append(string2);
                ((StringBuilder)object).append(", authTokenType ");
                ((StringBuilder)object).append((String)charSequence);
                ((StringBuilder)object).append(", features ");
                object2 = arrobject == null ? "[]" : Arrays.toString(arrobject);
                ((StringBuilder)object).append((String)object2);
                Log.v(AbstractAccountAuthenticator.TAG, ((StringBuilder)object).toString());
            }
            AbstractAccountAuthenticator.this.checkBinderPermission();
            try {
                object = AbstractAccountAuthenticator.this;
                object2 = new AccountAuthenticatorResponse(iAccountAuthenticatorResponse);
                arrobject = ((AbstractAccountAuthenticator)object).addAccount((AccountAuthenticatorResponse)object2, string2, (String)charSequence, (String[])arrobject, bundle);
                if (Log.isLoggable(AbstractAccountAuthenticator.TAG, 2)) {
                    if (arrobject != null) {
                        arrobject.keySet();
                    }
                    charSequence = new StringBuilder();
                    ((StringBuilder)charSequence).append("addAccount: result ");
                    ((StringBuilder)charSequence).append(AccountManager.sanitizeResult((Bundle)arrobject));
                    Log.v(AbstractAccountAuthenticator.TAG, ((StringBuilder)charSequence).toString());
                }
                if (arrobject != null) {
                    iAccountAuthenticatorResponse.onResult((Bundle)arrobject);
                    return;
                }
                iAccountAuthenticatorResponse.onError(5, "null bundle returned");
                return;
            }
            catch (Exception exception) {
                AbstractAccountAuthenticator.this.handleException(iAccountAuthenticatorResponse, "addAccount", string2, exception);
            }
        }

        @Override
        public void addAccountFromCredentials(IAccountAuthenticatorResponse iAccountAuthenticatorResponse, Account account, Bundle bundle) throws RemoteException {
            block3 : {
                AbstractAccountAuthenticator.this.checkBinderPermission();
                AbstractAccountAuthenticator abstractAccountAuthenticator = AbstractAccountAuthenticator.this;
                AccountAuthenticatorResponse accountAuthenticatorResponse = new AccountAuthenticatorResponse(iAccountAuthenticatorResponse);
                bundle = abstractAccountAuthenticator.addAccountFromCredentials(accountAuthenticatorResponse, account, bundle);
                if (bundle == null) break block3;
                try {
                    iAccountAuthenticatorResponse.onResult(bundle);
                }
                catch (Exception exception) {
                    AbstractAccountAuthenticator.this.handleException(iAccountAuthenticatorResponse, "addAccountFromCredentials", account.toString(), exception);
                }
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void confirmCredentials(IAccountAuthenticatorResponse iAccountAuthenticatorResponse, Account account, Bundle object) throws RemoteException {
            Object object2;
            if (Log.isLoggable(AbstractAccountAuthenticator.TAG, 2)) {
                object2 = new StringBuilder();
                ((StringBuilder)object2).append("confirmCredentials: ");
                ((StringBuilder)object2).append(account);
                Log.v(AbstractAccountAuthenticator.TAG, ((StringBuilder)object2).toString());
            }
            AbstractAccountAuthenticator.this.checkBinderPermission();
            try {
                AbstractAccountAuthenticator abstractAccountAuthenticator = AbstractAccountAuthenticator.this;
                object2 = new AccountAuthenticatorResponse(iAccountAuthenticatorResponse);
                object2 = abstractAccountAuthenticator.confirmCredentials((AccountAuthenticatorResponse)object2, account, (Bundle)object);
                if (Log.isLoggable(AbstractAccountAuthenticator.TAG, 2)) {
                    if (object2 != null) {
                        ((BaseBundle)object2).keySet();
                    }
                    object = new StringBuilder();
                    ((StringBuilder)object).append("confirmCredentials: result ");
                    ((StringBuilder)object).append(AccountManager.sanitizeResult((Bundle)object2));
                    Log.v(AbstractAccountAuthenticator.TAG, ((StringBuilder)object).toString());
                }
                if (object2 == null) return;
                iAccountAuthenticatorResponse.onResult((Bundle)object2);
                return;
            }
            catch (Exception exception) {
                AbstractAccountAuthenticator.this.handleException(iAccountAuthenticatorResponse, "confirmCredentials", account.toString(), exception);
            }
        }

        @Override
        public void editProperties(IAccountAuthenticatorResponse iAccountAuthenticatorResponse, String string2) throws RemoteException {
            block3 : {
                AbstractAccountAuthenticator.this.checkBinderPermission();
                AbstractAccountAuthenticator abstractAccountAuthenticator = AbstractAccountAuthenticator.this;
                Parcelable parcelable = new AccountAuthenticatorResponse(iAccountAuthenticatorResponse);
                parcelable = abstractAccountAuthenticator.editProperties((AccountAuthenticatorResponse)parcelable, string2);
                if (parcelable == null) break block3;
                try {
                    iAccountAuthenticatorResponse.onResult((Bundle)parcelable);
                }
                catch (Exception exception) {
                    AbstractAccountAuthenticator.this.handleException(iAccountAuthenticatorResponse, "editProperties", string2, exception);
                }
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void finishSession(IAccountAuthenticatorResponse iAccountAuthenticatorResponse, String string2, Bundle bundle) throws RemoteException {
            Object object;
            if (Log.isLoggable(AbstractAccountAuthenticator.TAG, 2)) {
                object = new StringBuilder();
                ((StringBuilder)object).append("finishSession: accountType ");
                ((StringBuilder)object).append(string2);
                Log.v(AbstractAccountAuthenticator.TAG, ((StringBuilder)object).toString());
            }
            AbstractAccountAuthenticator.this.checkBinderPermission();
            try {
                object = AbstractAccountAuthenticator.this;
                AccountAuthenticatorResponse accountAuthenticatorResponse = new AccountAuthenticatorResponse(iAccountAuthenticatorResponse);
                bundle = ((AbstractAccountAuthenticator)object).finishSession(accountAuthenticatorResponse, string2, bundle);
                if (bundle != null) {
                    bundle.keySet();
                }
                if (Log.isLoggable(AbstractAccountAuthenticator.TAG, 2)) {
                    object = new StringBuilder();
                    ((StringBuilder)object).append("finishSession: result ");
                    ((StringBuilder)object).append(AccountManager.sanitizeResult(bundle));
                    Log.v(AbstractAccountAuthenticator.TAG, ((StringBuilder)object).toString());
                }
                if (bundle == null) return;
                iAccountAuthenticatorResponse.onResult(bundle);
                return;
            }
            catch (Exception exception) {
                AbstractAccountAuthenticator.this.handleException(iAccountAuthenticatorResponse, "finishSession", string2, exception);
            }
        }

        @Override
        public void getAccountCredentialsForCloning(IAccountAuthenticatorResponse iAccountAuthenticatorResponse, Account account) throws RemoteException {
            block3 : {
                AbstractAccountAuthenticator.this.checkBinderPermission();
                Object object = AbstractAccountAuthenticator.this;
                AccountAuthenticatorResponse accountAuthenticatorResponse = new AccountAuthenticatorResponse(iAccountAuthenticatorResponse);
                object = ((AbstractAccountAuthenticator)object).getAccountCredentialsForCloning(accountAuthenticatorResponse, account);
                if (object == null) break block3;
                try {
                    iAccountAuthenticatorResponse.onResult((Bundle)object);
                }
                catch (Exception exception) {
                    AbstractAccountAuthenticator.this.handleException(iAccountAuthenticatorResponse, "getAccountCredentialsForCloning", account.toString(), exception);
                }
            }
        }

        @Override
        public void getAccountRemovalAllowed(IAccountAuthenticatorResponse iAccountAuthenticatorResponse, Account account) throws RemoteException {
            block3 : {
                AbstractAccountAuthenticator.this.checkBinderPermission();
                Object object = AbstractAccountAuthenticator.this;
                AccountAuthenticatorResponse accountAuthenticatorResponse = new AccountAuthenticatorResponse(iAccountAuthenticatorResponse);
                object = ((AbstractAccountAuthenticator)object).getAccountRemovalAllowed(accountAuthenticatorResponse, account);
                if (object == null) break block3;
                try {
                    iAccountAuthenticatorResponse.onResult((Bundle)object);
                }
                catch (Exception exception) {
                    AbstractAccountAuthenticator.this.handleException(iAccountAuthenticatorResponse, "getAccountRemovalAllowed", account.toString(), exception);
                }
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void getAuthToken(IAccountAuthenticatorResponse iAccountAuthenticatorResponse, Account account, String string2, Bundle object) throws RemoteException {
            Object object2;
            if (Log.isLoggable(AbstractAccountAuthenticator.TAG, 2)) {
                object2 = new StringBuilder();
                ((StringBuilder)object2).append("getAuthToken: ");
                ((StringBuilder)object2).append(account);
                ((StringBuilder)object2).append(", authTokenType ");
                ((StringBuilder)object2).append(string2);
                Log.v(AbstractAccountAuthenticator.TAG, ((StringBuilder)object2).toString());
            }
            AbstractAccountAuthenticator.this.checkBinderPermission();
            try {
                AbstractAccountAuthenticator abstractAccountAuthenticator = AbstractAccountAuthenticator.this;
                object2 = new AccountAuthenticatorResponse(iAccountAuthenticatorResponse);
                object2 = abstractAccountAuthenticator.getAuthToken((AccountAuthenticatorResponse)object2, account, string2, (Bundle)object);
                if (Log.isLoggable(AbstractAccountAuthenticator.TAG, 2)) {
                    if (object2 != null) {
                        ((BaseBundle)object2).keySet();
                    }
                    object = new StringBuilder();
                    ((StringBuilder)object).append("getAuthToken: result ");
                    ((StringBuilder)object).append(AccountManager.sanitizeResult((Bundle)object2));
                    Log.v(AbstractAccountAuthenticator.TAG, ((StringBuilder)object).toString());
                }
                if (object2 == null) return;
                iAccountAuthenticatorResponse.onResult((Bundle)object2);
                return;
            }
            catch (Exception exception) {
                AbstractAccountAuthenticator abstractAccountAuthenticator = AbstractAccountAuthenticator.this;
                object = new StringBuilder();
                ((StringBuilder)object).append(account.toString());
                ((StringBuilder)object).append(",");
                ((StringBuilder)object).append(string2);
                abstractAccountAuthenticator.handleException(iAccountAuthenticatorResponse, "getAuthToken", ((StringBuilder)object).toString(), exception);
            }
        }

        @Override
        public void getAuthTokenLabel(IAccountAuthenticatorResponse iAccountAuthenticatorResponse, String string2) throws RemoteException {
            StringBuilder stringBuilder;
            if (Log.isLoggable(AbstractAccountAuthenticator.TAG, 2)) {
                stringBuilder = new StringBuilder();
                stringBuilder.append("getAuthTokenLabel: authTokenType ");
                stringBuilder.append(string2);
                Log.v(AbstractAccountAuthenticator.TAG, stringBuilder.toString());
            }
            AbstractAccountAuthenticator.this.checkBinderPermission();
            try {
                Bundle bundle = new Bundle();
                bundle.putString("authTokenLabelKey", AbstractAccountAuthenticator.this.getAuthTokenLabel(string2));
                if (Log.isLoggable(AbstractAccountAuthenticator.TAG, 2)) {
                    bundle.keySet();
                    stringBuilder = new StringBuilder();
                    stringBuilder.append("getAuthTokenLabel: result ");
                    stringBuilder.append(AccountManager.sanitizeResult(bundle));
                    Log.v(AbstractAccountAuthenticator.TAG, stringBuilder.toString());
                }
                iAccountAuthenticatorResponse.onResult(bundle);
            }
            catch (Exception exception) {
                AbstractAccountAuthenticator.this.handleException(iAccountAuthenticatorResponse, "getAuthTokenLabel", string2, exception);
            }
        }

        @Override
        public void hasFeatures(IAccountAuthenticatorResponse iAccountAuthenticatorResponse, Account account, String[] object) throws RemoteException {
            block3 : {
                AbstractAccountAuthenticator.this.checkBinderPermission();
                AbstractAccountAuthenticator abstractAccountAuthenticator = AbstractAccountAuthenticator.this;
                AccountAuthenticatorResponse accountAuthenticatorResponse = new AccountAuthenticatorResponse(iAccountAuthenticatorResponse);
                object = abstractAccountAuthenticator.hasFeatures(accountAuthenticatorResponse, account, (String[])object);
                if (object == null) break block3;
                try {
                    iAccountAuthenticatorResponse.onResult((Bundle)object);
                }
                catch (Exception exception) {
                    AbstractAccountAuthenticator.this.handleException(iAccountAuthenticatorResponse, "hasFeatures", account.toString(), exception);
                }
            }
        }

        @Override
        public void isCredentialsUpdateSuggested(IAccountAuthenticatorResponse iAccountAuthenticatorResponse, Account account, String object) throws RemoteException {
            block3 : {
                AbstractAccountAuthenticator.this.checkBinderPermission();
                AbstractAccountAuthenticator abstractAccountAuthenticator = AbstractAccountAuthenticator.this;
                AccountAuthenticatorResponse accountAuthenticatorResponse = new AccountAuthenticatorResponse(iAccountAuthenticatorResponse);
                object = abstractAccountAuthenticator.isCredentialsUpdateSuggested(accountAuthenticatorResponse, account, (String)object);
                if (object == null) break block3;
                try {
                    iAccountAuthenticatorResponse.onResult((Bundle)object);
                }
                catch (Exception exception) {
                    AbstractAccountAuthenticator.this.handleException(iAccountAuthenticatorResponse, "isCredentialsUpdateSuggested", account.toString(), exception);
                }
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void startAddAccountSession(IAccountAuthenticatorResponse iAccountAuthenticatorResponse, String string2, String charSequence, String[] arrobject, Bundle bundle) throws RemoteException {
            Object object;
            Object object2;
            if (Log.isLoggable(AbstractAccountAuthenticator.TAG, 2)) {
                object = new StringBuilder();
                ((StringBuilder)object).append("startAddAccountSession: accountType ");
                ((StringBuilder)object).append(string2);
                ((StringBuilder)object).append(", authTokenType ");
                ((StringBuilder)object).append((String)charSequence);
                ((StringBuilder)object).append(", features ");
                object2 = arrobject == null ? "[]" : Arrays.toString(arrobject);
                ((StringBuilder)object).append((String)object2);
                Log.v(AbstractAccountAuthenticator.TAG, ((StringBuilder)object).toString());
            }
            AbstractAccountAuthenticator.this.checkBinderPermission();
            try {
                object2 = AbstractAccountAuthenticator.this;
                object = new AccountAuthenticatorResponse(iAccountAuthenticatorResponse);
                arrobject = ((AbstractAccountAuthenticator)object2).startAddAccountSession((AccountAuthenticatorResponse)object, string2, (String)charSequence, (String[])arrobject, bundle);
                if (Log.isLoggable(AbstractAccountAuthenticator.TAG, 2)) {
                    if (arrobject != null) {
                        arrobject.keySet();
                    }
                    charSequence = new StringBuilder();
                    ((StringBuilder)charSequence).append("startAddAccountSession: result ");
                    ((StringBuilder)charSequence).append(AccountManager.sanitizeResult((Bundle)arrobject));
                    Log.v(AbstractAccountAuthenticator.TAG, ((StringBuilder)charSequence).toString());
                }
                if (arrobject == null) return;
                iAccountAuthenticatorResponse.onResult((Bundle)arrobject);
                return;
            }
            catch (Exception exception) {
                AbstractAccountAuthenticator.this.handleException(iAccountAuthenticatorResponse, "startAddAccountSession", string2, exception);
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void startUpdateCredentialsSession(IAccountAuthenticatorResponse iAccountAuthenticatorResponse, Account account, String string2, Bundle object) throws RemoteException {
            Object object2;
            if (Log.isLoggable(AbstractAccountAuthenticator.TAG, 2)) {
                object2 = new StringBuilder();
                ((StringBuilder)object2).append("startUpdateCredentialsSession: ");
                ((StringBuilder)object2).append(account);
                ((StringBuilder)object2).append(", authTokenType ");
                ((StringBuilder)object2).append(string2);
                Log.v(AbstractAccountAuthenticator.TAG, ((StringBuilder)object2).toString());
            }
            AbstractAccountAuthenticator.this.checkBinderPermission();
            try {
                object2 = AbstractAccountAuthenticator.this;
                AccountAuthenticatorResponse accountAuthenticatorResponse = new AccountAuthenticatorResponse(iAccountAuthenticatorResponse);
                object2 = ((AbstractAccountAuthenticator)object2).startUpdateCredentialsSession(accountAuthenticatorResponse, account, string2, (Bundle)object);
                if (Log.isLoggable(AbstractAccountAuthenticator.TAG, 2)) {
                    if (object2 != null) {
                        ((BaseBundle)object2).keySet();
                    }
                    object = new StringBuilder();
                    ((StringBuilder)object).append("startUpdateCredentialsSession: result ");
                    ((StringBuilder)object).append(AccountManager.sanitizeResult((Bundle)object2));
                    Log.v(AbstractAccountAuthenticator.TAG, ((StringBuilder)object).toString());
                }
                if (object2 == null) return;
                iAccountAuthenticatorResponse.onResult((Bundle)object2);
                return;
            }
            catch (Exception exception) {
                object2 = AbstractAccountAuthenticator.this;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(account.toString());
                stringBuilder.append(",");
                stringBuilder.append(string2);
                ((AbstractAccountAuthenticator)object2).handleException(iAccountAuthenticatorResponse, "startUpdateCredentialsSession", stringBuilder.toString(), exception);
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void updateCredentials(IAccountAuthenticatorResponse iAccountAuthenticatorResponse, Account account, String string2, Bundle bundle) throws RemoteException {
            Object object;
            if (Log.isLoggable(AbstractAccountAuthenticator.TAG, 2)) {
                object = new StringBuilder();
                ((StringBuilder)object).append("updateCredentials: ");
                ((StringBuilder)object).append(account);
                ((StringBuilder)object).append(", authTokenType ");
                ((StringBuilder)object).append(string2);
                Log.v(AbstractAccountAuthenticator.TAG, ((StringBuilder)object).toString());
            }
            AbstractAccountAuthenticator.this.checkBinderPermission();
            try {
                AbstractAccountAuthenticator abstractAccountAuthenticator = AbstractAccountAuthenticator.this;
                object = new AccountAuthenticatorResponse(iAccountAuthenticatorResponse);
                bundle = abstractAccountAuthenticator.updateCredentials((AccountAuthenticatorResponse)object, account, string2, bundle);
                if (Log.isLoggable(AbstractAccountAuthenticator.TAG, 2)) {
                    if (bundle != null) {
                        bundle.keySet();
                    }
                    object = new StringBuilder();
                    ((StringBuilder)object).append("updateCredentials: result ");
                    ((StringBuilder)object).append(AccountManager.sanitizeResult(bundle));
                    Log.v(AbstractAccountAuthenticator.TAG, ((StringBuilder)object).toString());
                }
                if (bundle == null) return;
                iAccountAuthenticatorResponse.onResult(bundle);
                return;
            }
            catch (Exception exception) {
                object = AbstractAccountAuthenticator.this;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(account.toString());
                stringBuilder.append(",");
                stringBuilder.append(string2);
                ((AbstractAccountAuthenticator)object).handleException(iAccountAuthenticatorResponse, "updateCredentials", stringBuilder.toString(), exception);
            }
        }
    }

}

