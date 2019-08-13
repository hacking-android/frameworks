/*
 * Decompiled with CFR 0.145.
 */
package android.accounts;

import android.accounts.Account;
import android.accounts.AccountManagerCallback;
import android.accounts.AccountManagerFuture;
import android.accounts.AuthenticatorDescription;
import android.accounts.AuthenticatorException;
import android.accounts.IAccountManager;
import android.accounts.IAccountManagerResponse;
import android.accounts.OnAccountsUpdateListener;
import android.accounts.OperationCanceledException;
import android.annotation.SystemApi;
import android.annotation.UnsupportedAppUsage;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentSender;
import android.content.pm.ApplicationInfo;
import android.content.res.Resources;
import android.database.SQLException;
import android.os.BaseBundle;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Parcelable;
import android.os.RemoteException;
import android.os.UserHandle;
import android.text.TextUtils;
import android.util.Log;
import com.google.android.collect.Maps;
import java.io.IOException;
import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class AccountManager {
    public static final String ACCOUNT_ACCESS_TOKEN_TYPE = "com.android.AccountManager.ACCOUNT_ACCESS_TOKEN_TYPE";
    public static final String ACTION_ACCOUNT_REMOVED = "android.accounts.action.ACCOUNT_REMOVED";
    public static final String ACTION_AUTHENTICATOR_INTENT = "android.accounts.AccountAuthenticator";
    public static final String ACTION_VISIBLE_ACCOUNTS_CHANGED = "android.accounts.action.VISIBLE_ACCOUNTS_CHANGED";
    public static final String AUTHENTICATOR_ATTRIBUTES_NAME = "account-authenticator";
    public static final String AUTHENTICATOR_META_DATA_NAME = "android.accounts.AccountAuthenticator";
    public static final int ERROR_CODE_BAD_ARGUMENTS = 7;
    public static final int ERROR_CODE_BAD_AUTHENTICATION = 9;
    public static final int ERROR_CODE_BAD_REQUEST = 8;
    public static final int ERROR_CODE_CANCELED = 4;
    public static final int ERROR_CODE_INVALID_RESPONSE = 5;
    public static final int ERROR_CODE_MANAGEMENT_DISABLED_FOR_ACCOUNT_TYPE = 101;
    public static final int ERROR_CODE_NETWORK_ERROR = 3;
    public static final int ERROR_CODE_REMOTE_EXCEPTION = 1;
    public static final int ERROR_CODE_UNSUPPORTED_OPERATION = 6;
    public static final int ERROR_CODE_USER_RESTRICTED = 100;
    public static final String KEY_ACCOUNTS = "accounts";
    public static final String KEY_ACCOUNT_ACCESS_ID = "accountAccessId";
    public static final String KEY_ACCOUNT_AUTHENTICATOR_RESPONSE = "accountAuthenticatorResponse";
    public static final String KEY_ACCOUNT_MANAGER_RESPONSE = "accountManagerResponse";
    public static final String KEY_ACCOUNT_NAME = "authAccount";
    public static final String KEY_ACCOUNT_SESSION_BUNDLE = "accountSessionBundle";
    public static final String KEY_ACCOUNT_STATUS_TOKEN = "accountStatusToken";
    public static final String KEY_ACCOUNT_TYPE = "accountType";
    public static final String KEY_ANDROID_PACKAGE_NAME = "androidPackageName";
    public static final String KEY_AUTHENTICATOR_TYPES = "authenticator_types";
    public static final String KEY_AUTHTOKEN = "authtoken";
    public static final String KEY_AUTH_FAILED_MESSAGE = "authFailedMessage";
    public static final String KEY_AUTH_TOKEN_LABEL = "authTokenLabelKey";
    public static final String KEY_BOOLEAN_RESULT = "booleanResult";
    public static final String KEY_CALLER_PID = "callerPid";
    public static final String KEY_CALLER_UID = "callerUid";
    public static final String KEY_ERROR_CODE = "errorCode";
    public static final String KEY_ERROR_MESSAGE = "errorMessage";
    public static final String KEY_INTENT = "intent";
    public static final String KEY_LAST_AUTHENTICATED_TIME = "lastAuthenticatedTime";
    public static final String KEY_NOTIFY_ON_FAILURE = "notifyOnAuthFailure";
    public static final String KEY_PASSWORD = "password";
    public static final String KEY_USERDATA = "userdata";
    public static final String LOGIN_ACCOUNTS_CHANGED_ACTION = "android.accounts.LOGIN_ACCOUNTS_CHANGED";
    public static final String PACKAGE_NAME_KEY_LEGACY_NOT_VISIBLE = "android:accounts:key_legacy_not_visible";
    public static final String PACKAGE_NAME_KEY_LEGACY_VISIBLE = "android:accounts:key_legacy_visible";
    private static final String TAG = "AccountManager";
    public static final int VISIBILITY_NOT_VISIBLE = 3;
    public static final int VISIBILITY_UNDEFINED = 0;
    public static final int VISIBILITY_USER_MANAGED_NOT_VISIBLE = 4;
    public static final int VISIBILITY_USER_MANAGED_VISIBLE = 2;
    public static final int VISIBILITY_VISIBLE = 1;
    private final BroadcastReceiver mAccountsChangedBroadcastReceiver = new BroadcastReceiver(){

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void onReceive(Context object, Intent arraccount) {
            arraccount = AccountManager.this.getAccounts();
            object = AccountManager.this.mAccountsUpdatedListeners;
            synchronized (object) {
                Iterator iterator = AccountManager.this.mAccountsUpdatedListeners.entrySet().iterator();
                while (iterator.hasNext()) {
                    Map.Entry entry = iterator.next();
                    AccountManager.this.postToHandler((Handler)entry.getValue(), (OnAccountsUpdateListener)entry.getKey(), arraccount);
                }
                return;
            }
        }
    };
    private final HashMap<OnAccountsUpdateListener, Handler> mAccountsUpdatedListeners = Maps.newHashMap();
    private final HashMap<OnAccountsUpdateListener, Set<String>> mAccountsUpdatedListenersTypes = Maps.newHashMap();
    @UnsupportedAppUsage
    private final Context mContext;
    private final Handler mMainHandler;
    private final IAccountManager mService;

    @UnsupportedAppUsage
    public AccountManager(Context context, IAccountManager iAccountManager) {
        this.mContext = context;
        this.mService = iAccountManager;
        this.mMainHandler = new Handler(this.mContext.getMainLooper());
    }

    @UnsupportedAppUsage
    public AccountManager(Context context, IAccountManager iAccountManager, Handler handler) {
        this.mContext = context;
        this.mService = iAccountManager;
        this.mMainHandler = handler;
    }

    private Exception convertErrorToException(int n, String string2) {
        if (n == 3) {
            return new IOException(string2);
        }
        if (n == 6) {
            return new UnsupportedOperationException(string2);
        }
        if (n == 5) {
            return new AuthenticatorException(string2);
        }
        if (n == 7) {
            return new IllegalArgumentException(string2);
        }
        return new AuthenticatorException(string2);
    }

    private void ensureNotOnMainThread() {
        Object object = Looper.myLooper();
        if (object != null && object == this.mContext.getMainLooper()) {
            object = new IllegalStateException("calling this from your main thread can lead to deadlock");
            Log.e(TAG, "calling this from your main thread can lead to deadlock and/or ANRs", (Throwable)object);
            if (this.mContext.getApplicationInfo().targetSdkVersion >= 8) {
                throw object;
            }
        }
    }

    public static AccountManager get(Context context) {
        if (context != null) {
            return (AccountManager)context.getSystemService("account");
        }
        throw new IllegalArgumentException("context is null");
    }

    private void getAccountByTypeAndFeatures(final String string2, final String[] arrstring, AccountManagerCallback<Bundle> accountManagerCallback, Handler handler) {
        new AmsTask(null, handler, accountManagerCallback){

            @Override
            public void doWork() throws RemoteException {
                AccountManager.this.mService.getAccountByTypeAndFeatures(this.mResponse, string2, arrstring, AccountManager.this.mContext.getOpPackageName());
            }
        }.start();
    }

    @Deprecated
    public static Intent newChooseAccountIntent(Account account, ArrayList<Account> arrayList, String[] arrstring, boolean bl, String string2, String string3, String[] arrstring2, Bundle bundle) {
        return AccountManager.newChooseAccountIntent(account, arrayList, arrstring, string2, string3, arrstring2, bundle);
    }

    public static Intent newChooseAccountIntent(Account account, List<Account> list, String[] arrstring, String string2, String string3, String[] arrstring2, Bundle bundle) {
        Intent intent = new Intent();
        ComponentName componentName = ComponentName.unflattenFromString(Resources.getSystem().getString(17039686));
        intent.setClassName(componentName.getPackageName(), componentName.getClassName());
        list = list == null ? null : new ArrayList<Account>(list);
        intent.putExtra("allowableAccounts", (Serializable)((Object)list));
        intent.putExtra("allowableAccountTypes", arrstring);
        intent.putExtra("addAccountOptions", bundle);
        intent.putExtra("selectedAccount", account);
        intent.putExtra("descriptionTextOverride", string2);
        intent.putExtra("authTokenType", string3);
        intent.putExtra("addAccountRequiredFeatures", arrstring2);
        return intent;
    }

    private void postToHandler(Handler handler, final AccountManagerCallback<Bundle> accountManagerCallback, final AccountManagerFuture<Bundle> accountManagerFuture) {
        if (handler == null) {
            handler = this.mMainHandler;
        }
        handler.post(new Runnable(){

            @Override
            public void run() {
                accountManagerCallback.run(accountManagerFuture);
            }
        });
    }

    private void postToHandler(Handler handler, final OnAccountsUpdateListener onAccountsUpdateListener, Account[] arraccount) {
        final Account[] arraccount2 = new Account[arraccount.length];
        System.arraycopy(arraccount, 0, arraccount2, 0, arraccount2.length);
        if (handler == null) {
            handler = this.mMainHandler;
        }
        handler.post(new Runnable(){

            /*
             * WARNING - combined exceptions agressively - possible behaviour change.
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void run() {
                HashMap hashMap = AccountManager.this.mAccountsUpdatedListeners;
                synchronized (hashMap) {
                    Throwable throwable2;
                    block10 : {
                        try {
                            if (AccountManager.this.mAccountsUpdatedListeners.containsKey(onAccountsUpdateListener)) {
                                Set set = (Set)AccountManager.this.mAccountsUpdatedListenersTypes.get(onAccountsUpdateListener);
                                if (set == null) {
                                    onAccountsUpdateListener.onAccountsUpdated(arraccount2);
                                } else {
                                    ArrayList<Account> arrayList = new ArrayList<Account>();
                                    for (Account account : arraccount2) {
                                        if (!set.contains(account.type)) continue;
                                        arrayList.add(account);
                                    }
                                    onAccountsUpdateListener.onAccountsUpdated(arrayList.toArray(new Account[arrayList.size()]));
                                }
                            }
                        }
                        catch (Throwable throwable2) {
                            break block10;
                        }
                        catch (SQLException sQLException) {
                            Log.e(AccountManager.TAG, "Can't update accounts", sQLException);
                        }
                        return;
                    }
                    throw throwable2;
                }
            }
        });
    }

    public static Bundle sanitizeResult(Bundle bundle) {
        if (bundle != null && bundle.containsKey(KEY_AUTHTOKEN) && !TextUtils.isEmpty(bundle.getString(KEY_AUTHTOKEN))) {
            bundle = new Bundle(bundle);
            bundle.putString(KEY_AUTHTOKEN, "<omitted for logging purposes>");
            return bundle;
        }
        return bundle;
    }

    public AccountManagerFuture<Bundle> addAccount(final String string2, final String string3, final String[] arrstring, Bundle bundle, final Activity activity, AccountManagerCallback<Bundle> accountManagerCallback, Handler handler) {
        if (string2 != null) {
            final Bundle bundle2 = new Bundle();
            if (bundle != null) {
                bundle2.putAll(bundle);
            }
            bundle2.putString(KEY_ANDROID_PACKAGE_NAME, this.mContext.getPackageName());
            return new AmsTask(activity, handler, accountManagerCallback){

                @Override
                public void doWork() throws RemoteException {
                    IAccountManager iAccountManager = AccountManager.this.mService;
                    IAccountManagerResponse iAccountManagerResponse = this.mResponse;
                    String string22 = string2;
                    String string32 = string3;
                    String[] arrstring2 = arrstring;
                    boolean bl = activity != null;
                    iAccountManager.addAccount(iAccountManagerResponse, string22, string32, arrstring2, bl, bundle2);
                }
            }.start();
        }
        throw new IllegalArgumentException("accountType is null");
    }

    public AccountManagerFuture<Bundle> addAccountAsUser(final String string2, final String string3, final String[] arrstring, Bundle bundle, final Activity activity, AccountManagerCallback<Bundle> accountManagerCallback, Handler handler, final UserHandle userHandle) {
        if (string2 != null) {
            if (userHandle != null) {
                final Bundle bundle2 = new Bundle();
                if (bundle != null) {
                    bundle2.putAll(bundle);
                }
                bundle2.putString(KEY_ANDROID_PACKAGE_NAME, this.mContext.getPackageName());
                return new AmsTask(activity, handler, accountManagerCallback){

                    @Override
                    public void doWork() throws RemoteException {
                        IAccountManager iAccountManager = AccountManager.this.mService;
                        IAccountManagerResponse iAccountManagerResponse = this.mResponse;
                        String string22 = string2;
                        String string32 = string3;
                        String[] arrstring2 = arrstring;
                        boolean bl = activity != null;
                        iAccountManager.addAccountAsUser(iAccountManagerResponse, string22, string32, arrstring2, bl, bundle2, userHandle.getIdentifier());
                    }
                }.start();
            }
            throw new IllegalArgumentException("userHandle is null");
        }
        throw new IllegalArgumentException("accountType is null");
    }

    public boolean addAccountExplicitly(Account account, String string2, Bundle bundle) {
        if (account != null) {
            try {
                boolean bl = this.mService.addAccountExplicitly(account, string2, bundle);
                return bl;
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
        throw new IllegalArgumentException("account is null");
    }

    public boolean addAccountExplicitly(Account account, String string2, Bundle bundle, Map<String, Integer> map) {
        if (account != null) {
            try {
                boolean bl = this.mService.addAccountExplicitlyWithVisibility(account, string2, bundle, map);
                return bl;
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
        throw new IllegalArgumentException("account is null");
    }

    public void addOnAccountsUpdatedListener(OnAccountsUpdateListener onAccountsUpdateListener, Handler handler, boolean bl) {
        this.addOnAccountsUpdatedListener(onAccountsUpdateListener, handler, bl, null);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void addOnAccountsUpdatedListener(OnAccountsUpdateListener object, Handler handler, boolean bl, String[] arrstring) {
        if (object == null) {
            throw new IllegalArgumentException("the listener is null");
        }
        HashMap<OnAccountsUpdateListener, Handler> hashMap = this.mAccountsUpdatedListeners;
        synchronized (hashMap) {
            Object object2;
            if (this.mAccountsUpdatedListeners.containsKey(object)) {
                object = new IllegalStateException("this listener is already added");
                throw object;
            }
            boolean bl2 = this.mAccountsUpdatedListeners.isEmpty();
            this.mAccountsUpdatedListeners.put((OnAccountsUpdateListener)object, handler);
            if (arrstring != null) {
                object2 = this.mAccountsUpdatedListenersTypes;
                HashSet<String> hashSet = new HashSet<String>(Arrays.asList(arrstring));
                ((HashMap)object2).put((OnAccountsUpdateListener)object, hashSet);
            } else {
                this.mAccountsUpdatedListenersTypes.put((OnAccountsUpdateListener)object, null);
            }
            if (bl2) {
                object2 = new IntentFilter();
                ((IntentFilter)object2).addAction(ACTION_VISIBLE_ACCOUNTS_CHANGED);
                ((IntentFilter)object2).addAction("android.intent.action.DEVICE_STORAGE_OK");
                this.mContext.registerReceiver(this.mAccountsChangedBroadcastReceiver, (IntentFilter)object2);
            }
            try {
                this.mService.registerAccountListener(arrstring, this.mContext.getOpPackageName());
                // MONITOREXIT [1, 3] lbl27 : MonitorExitStatement: MONITOREXIT : var5_6
                if (bl) {
                    this.postToHandler(handler, (OnAccountsUpdateListener)object, this.getAccounts());
                }
                return;
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
    }

    public void addSharedAccountsFromParentUser(UserHandle userHandle, UserHandle userHandle2) {
        try {
            this.mService.addSharedAccountsFromParentUser(userHandle.getIdentifier(), userHandle2.getIdentifier(), this.mContext.getOpPackageName());
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public String blockingGetAuthToken(Account account, String string2, boolean bl) throws OperationCanceledException, IOException, AuthenticatorException {
        if (account != null) {
            if (string2 != null) {
                Object object = this.getAuthToken(account, string2, bl, null, null).getResult();
                if (object == null) {
                    object = new StringBuilder();
                    ((StringBuilder)object).append("blockingGetAuthToken: null was returned from getResult() for ");
                    ((StringBuilder)object).append(account);
                    ((StringBuilder)object).append(", authTokenType ");
                    ((StringBuilder)object).append(string2);
                    Log.e(TAG, ((StringBuilder)object).toString());
                    return null;
                }
                return ((BaseBundle)object).getString(KEY_AUTHTOKEN);
            }
            throw new IllegalArgumentException("authTokenType is null");
        }
        throw new IllegalArgumentException("account is null");
    }

    public void clearPassword(Account account) {
        if (account != null) {
            try {
                this.mService.clearPassword(account);
                return;
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
        throw new IllegalArgumentException("account is null");
    }

    public AccountManagerFuture<Bundle> confirmCredentials(Account account, Bundle bundle, Activity activity, AccountManagerCallback<Bundle> accountManagerCallback, Handler handler) {
        return this.confirmCredentialsAsUser(account, bundle, activity, accountManagerCallback, handler, this.mContext.getUser());
    }

    @UnsupportedAppUsage
    public AccountManagerFuture<Bundle> confirmCredentialsAsUser(final Account account, final Bundle bundle, final Activity activity, AccountManagerCallback<Bundle> accountManagerCallback, Handler handler, UserHandle userHandle) {
        if (account != null) {
            return new AmsTask(activity, handler, accountManagerCallback, userHandle.getIdentifier()){
                final /* synthetic */ int val$userId;
                {
                    this.val$userId = n;
                    super(activity3, handler, accountManagerCallback);
                }

                @Override
                public void doWork() throws RemoteException {
                    IAccountManager iAccountManager = AccountManager.this.mService;
                    IAccountManagerResponse iAccountManagerResponse = this.mResponse;
                    Account account2 = account;
                    Bundle bundle2 = bundle;
                    boolean bl = activity != null;
                    iAccountManager.confirmCredentialsAsUser(iAccountManagerResponse, account2, bundle2, bl, this.val$userId);
                }
            }.start();
        }
        throw new IllegalArgumentException("account is null");
    }

    public AccountManagerFuture<Boolean> copyAccountToUser(final Account account, final UserHandle userHandle, final UserHandle userHandle2, AccountManagerCallback<Boolean> accountManagerCallback, Handler handler) {
        if (account != null) {
            if (userHandle2 != null && userHandle != null) {
                return new Future2Task<Boolean>(handler, accountManagerCallback){

                    @Override
                    public Boolean bundleToResult(Bundle bundle) throws AuthenticatorException {
                        if (bundle.containsKey(AccountManager.KEY_BOOLEAN_RESULT)) {
                            return bundle.getBoolean(AccountManager.KEY_BOOLEAN_RESULT);
                        }
                        throw new AuthenticatorException("no result in response");
                    }

                    @Override
                    public void doWork() throws RemoteException {
                        AccountManager.this.mService.copyAccountToUser(this.mResponse, account, userHandle.getIdentifier(), userHandle2.getIdentifier());
                    }
                }.start();
            }
            throw new IllegalArgumentException("fromUser and toUser cannot be null");
        }
        throw new IllegalArgumentException("account is null");
    }

    public IntentSender createRequestAccountAccessIntentSenderAsUser(Account parcelable, String string2, UserHandle userHandle) {
        try {
            parcelable = this.mService.createRequestAccountAccessIntentSenderAsUser((Account)parcelable, string2, userHandle);
            return parcelable;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public AccountManagerFuture<Bundle> editProperties(final String string2, final Activity activity, AccountManagerCallback<Bundle> accountManagerCallback, Handler handler) {
        if (string2 != null) {
            return new AmsTask(activity, handler, accountManagerCallback){

                @Override
                public void doWork() throws RemoteException {
                    IAccountManager iAccountManager = AccountManager.this.mService;
                    IAccountManagerResponse iAccountManagerResponse = this.mResponse;
                    String string22 = string2;
                    boolean bl = activity != null;
                    iAccountManager.editProperties(iAccountManagerResponse, string22, bl);
                }
            }.start();
        }
        throw new IllegalArgumentException("accountType is null");
    }

    public AccountManagerFuture<Bundle> finishSession(Bundle bundle, Activity activity, AccountManagerCallback<Bundle> accountManagerCallback, Handler handler) {
        return this.finishSessionAsUser(bundle, activity, this.mContext.getUser(), accountManagerCallback, handler);
    }

    @SystemApi
    public AccountManagerFuture<Bundle> finishSessionAsUser(final Bundle bundle, final Activity activity, final UserHandle userHandle, AccountManagerCallback<Bundle> accountManagerCallback, Handler handler) {
        if (bundle != null) {
            final Bundle bundle2 = new Bundle();
            bundle2.putString(KEY_ANDROID_PACKAGE_NAME, this.mContext.getPackageName());
            return new AmsTask(activity, handler, accountManagerCallback){

                @Override
                public void doWork() throws RemoteException {
                    IAccountManager iAccountManager = AccountManager.this.mService;
                    IAccountManagerResponse iAccountManagerResponse = this.mResponse;
                    Bundle bundle3 = bundle;
                    boolean bl = activity != null;
                    iAccountManager.finishSessionAsUser(iAccountManagerResponse, bundle3, bl, bundle2, userHandle.getIdentifier());
                }
            }.start();
        }
        throw new IllegalArgumentException("sessionBundle is null");
    }

    public int getAccountVisibility(Account account, String string2) {
        if (account != null) {
            try {
                int n = this.mService.getAccountVisibility(account, string2);
                return n;
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
        throw new IllegalArgumentException("account is null");
    }

    public Account[] getAccounts() {
        try {
            Account[] arraccount = this.mService.getAccounts(null, this.mContext.getOpPackageName());
            return arraccount;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public Map<Account, Integer> getAccountsAndVisibilityForPackage(String object, String string2) {
        try {
            object = this.mService.getAccountsAndVisibilityForPackage((String)object, string2);
            return object;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public Account[] getAccountsAsUser(int n) {
        try {
            Account[] arraccount = this.mService.getAccountsAsUser(null, n, this.mContext.getOpPackageName());
            return arraccount;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public Account[] getAccountsByType(String string2) {
        return this.getAccountsByTypeAsUser(string2, this.mContext.getUser());
    }

    public AccountManagerFuture<Account[]> getAccountsByTypeAndFeatures(final String string2, final String[] arrstring, AccountManagerCallback<Account[]> accountManagerCallback, Handler handler) {
        if (string2 != null) {
            return new Future2Task<Account[]>(handler, accountManagerCallback){

                @Override
                public Account[] bundleToResult(Bundle arrparcelable) throws AuthenticatorException {
                    if (arrparcelable.containsKey(AccountManager.KEY_ACCOUNTS)) {
                        arrparcelable = arrparcelable.getParcelableArray(AccountManager.KEY_ACCOUNTS);
                        Account[] arraccount = new Account[arrparcelable.length];
                        for (int i = 0; i < arrparcelable.length; ++i) {
                            arraccount[i] = (Account)arrparcelable[i];
                        }
                        return arraccount;
                    }
                    throw new AuthenticatorException("no result in response");
                }

                @Override
                public void doWork() throws RemoteException {
                    AccountManager.this.mService.getAccountsByFeatures(this.mResponse, string2, arrstring, AccountManager.this.mContext.getOpPackageName());
                }
            }.start();
        }
        throw new IllegalArgumentException("type is null");
    }

    @UnsupportedAppUsage
    public Account[] getAccountsByTypeAsUser(String arraccount, UserHandle userHandle) {
        try {
            arraccount = this.mService.getAccountsAsUser((String)arraccount, userHandle.getIdentifier(), this.mContext.getOpPackageName());
            return arraccount;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public Account[] getAccountsByTypeForPackage(String arraccount, String string2) {
        try {
            arraccount = this.mService.getAccountsByTypeForPackage((String)arraccount, string2, this.mContext.getOpPackageName());
            return arraccount;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public Account[] getAccountsForPackage(String arraccount, int n) {
        try {
            arraccount = this.mService.getAccountsForPackage((String)arraccount, n, this.mContext.getOpPackageName());
            return arraccount;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public AccountManagerFuture<Bundle> getAuthToken(final Account account, final String string2, Bundle bundle, Activity activity, AccountManagerCallback<Bundle> accountManagerCallback, Handler handler) {
        if (account != null) {
            if (string2 != null) {
                final Bundle bundle2 = new Bundle();
                if (bundle != null) {
                    bundle2.putAll(bundle);
                }
                bundle2.putString(KEY_ANDROID_PACKAGE_NAME, this.mContext.getPackageName());
                return new AmsTask(activity, handler, accountManagerCallback){

                    @Override
                    public void doWork() throws RemoteException {
                        AccountManager.this.mService.getAuthToken(this.mResponse, account, string2, false, true, bundle2);
                    }
                }.start();
            }
            throw new IllegalArgumentException("authTokenType is null");
        }
        throw new IllegalArgumentException("account is null");
    }

    public AccountManagerFuture<Bundle> getAuthToken(final Account account, final String string2, Bundle bundle, final boolean bl, AccountManagerCallback<Bundle> accountManagerCallback, Handler handler) {
        if (account != null) {
            if (string2 != null) {
                final Bundle bundle2 = new Bundle();
                if (bundle != null) {
                    bundle2.putAll(bundle);
                }
                bundle2.putString(KEY_ANDROID_PACKAGE_NAME, this.mContext.getPackageName());
                return new AmsTask(null, handler, accountManagerCallback){

                    @Override
                    public void doWork() throws RemoteException {
                        AccountManager.this.mService.getAuthToken(this.mResponse, account, string2, bl, false, bundle2);
                    }
                }.start();
            }
            throw new IllegalArgumentException("authTokenType is null");
        }
        throw new IllegalArgumentException("account is null");
    }

    @Deprecated
    public AccountManagerFuture<Bundle> getAuthToken(Account account, String string2, boolean bl, AccountManagerCallback<Bundle> accountManagerCallback, Handler handler) {
        return this.getAuthToken(account, string2, null, bl, accountManagerCallback, handler);
    }

    public AccountManagerFuture<Bundle> getAuthTokenByFeatures(String object, String string2, String[] arrstring, Activity activity, Bundle bundle, Bundle bundle2, AccountManagerCallback<Bundle> accountManagerCallback, Handler handler) {
        if (object != null) {
            if (string2 != null) {
                object = new GetAuthTokenByTypeAndFeaturesTask((String)object, string2, arrstring, activity, bundle, bundle2, accountManagerCallback, handler);
                ((AmsTask)object).start();
                return object;
            }
            throw new IllegalArgumentException("authTokenType is null");
        }
        throw new IllegalArgumentException("account type is null");
    }

    public AccountManagerFuture<String> getAuthTokenLabel(final String string2, final String string3, AccountManagerCallback<String> accountManagerCallback, Handler handler) {
        if (string2 != null) {
            if (string3 != null) {
                return new Future2Task<String>(handler, accountManagerCallback){

                    @Override
                    public String bundleToResult(Bundle bundle) throws AuthenticatorException {
                        if (bundle.containsKey(AccountManager.KEY_AUTH_TOKEN_LABEL)) {
                            return bundle.getString(AccountManager.KEY_AUTH_TOKEN_LABEL);
                        }
                        throw new AuthenticatorException("no result in response");
                    }

                    @Override
                    public void doWork() throws RemoteException {
                        AccountManager.this.mService.getAuthTokenLabel(this.mResponse, string2, string3);
                    }
                }.start();
            }
            throw new IllegalArgumentException("authTokenType is null");
        }
        throw new IllegalArgumentException("accountType is null");
    }

    public AuthenticatorDescription[] getAuthenticatorTypes() {
        try {
            AuthenticatorDescription[] arrauthenticatorDescription = this.mService.getAuthenticatorTypes(UserHandle.getCallingUserId());
            return arrauthenticatorDescription;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public AuthenticatorDescription[] getAuthenticatorTypesAsUser(int n) {
        try {
            AuthenticatorDescription[] arrauthenticatorDescription = this.mService.getAuthenticatorTypes(n);
            return arrauthenticatorDescription;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public Map<String, Integer> getPackagesAndVisibilityForAccount(Account object) {
        if (object != null) {
            try {
                return this.mService.getPackagesAndVisibilityForAccount((Account)object);
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
        object = new IllegalArgumentException("account is null");
        throw object;
    }

    public String getPassword(Account object) {
        if (object != null) {
            try {
                object = this.mService.getPassword((Account)object);
                return object;
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
        throw new IllegalArgumentException("account is null");
    }

    public String getPreviousName(Account object) {
        if (object != null) {
            try {
                object = this.mService.getPreviousName((Account)object);
                return object;
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
        throw new IllegalArgumentException("account is null");
    }

    public Account[] getSharedAccounts(UserHandle arraccount) {
        try {
            arraccount = this.mService.getSharedAccountsAsUser(arraccount.getIdentifier());
            return arraccount;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public String getUserData(Account object, String string2) {
        if (object != null) {
            if (string2 != null) {
                try {
                    object = this.mService.getUserData((Account)object, string2);
                    return object;
                }
                catch (RemoteException remoteException) {
                    throw remoteException.rethrowFromSystemServer();
                }
            }
            throw new IllegalArgumentException("key is null");
        }
        throw new IllegalArgumentException("account is null");
    }

    public boolean hasAccountAccess(Account account, String string2, UserHandle userHandle) {
        try {
            boolean bl = this.mService.hasAccountAccess(account, string2, userHandle);
            return bl;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public AccountManagerFuture<Boolean> hasFeatures(final Account account, final String[] arrstring, AccountManagerCallback<Boolean> accountManagerCallback, Handler handler) {
        if (account != null) {
            if (arrstring != null) {
                return new Future2Task<Boolean>(handler, accountManagerCallback){

                    @Override
                    public Boolean bundleToResult(Bundle bundle) throws AuthenticatorException {
                        if (bundle.containsKey(AccountManager.KEY_BOOLEAN_RESULT)) {
                            return bundle.getBoolean(AccountManager.KEY_BOOLEAN_RESULT);
                        }
                        throw new AuthenticatorException("no result in response");
                    }

                    @Override
                    public void doWork() throws RemoteException {
                        AccountManager.this.mService.hasFeatures(this.mResponse, account, arrstring, AccountManager.this.mContext.getOpPackageName());
                    }
                }.start();
            }
            throw new IllegalArgumentException("features is null");
        }
        throw new IllegalArgumentException("account is null");
    }

    public void invalidateAuthToken(String string2, String string3) {
        if (string2 != null) {
            if (string3 != null) {
                try {
                    this.mService.invalidateAuthToken(string2, string3);
                }
                catch (RemoteException remoteException) {
                    throw remoteException.rethrowFromSystemServer();
                }
            }
            return;
        }
        throw new IllegalArgumentException("accountType is null");
    }

    public AccountManagerFuture<Boolean> isCredentialsUpdateSuggested(final Account account, final String string2, AccountManagerCallback<Boolean> accountManagerCallback, Handler handler) {
        if (account != null) {
            if (!TextUtils.isEmpty(string2)) {
                return new Future2Task<Boolean>(handler, accountManagerCallback){

                    @Override
                    public Boolean bundleToResult(Bundle bundle) throws AuthenticatorException {
                        if (bundle.containsKey(AccountManager.KEY_BOOLEAN_RESULT)) {
                            return bundle.getBoolean(AccountManager.KEY_BOOLEAN_RESULT);
                        }
                        throw new AuthenticatorException("no result in response");
                    }

                    @Override
                    public void doWork() throws RemoteException {
                        AccountManager.this.mService.isCredentialsUpdateSuggested(this.mResponse, account, string2);
                    }
                }.start();
            }
            throw new IllegalArgumentException("status token is empty");
        }
        throw new IllegalArgumentException("account is null");
    }

    public boolean notifyAccountAuthenticated(Account account) {
        if (account != null) {
            try {
                boolean bl = this.mService.accountAuthenticated(account);
                return bl;
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
        throw new IllegalArgumentException("account is null");
    }

    public String peekAuthToken(Account object, String string2) {
        if (object != null) {
            if (string2 != null) {
                try {
                    object = this.mService.peekAuthToken((Account)object, string2);
                    return object;
                }
                catch (RemoteException remoteException) {
                    throw remoteException.rethrowFromSystemServer();
                }
            }
            throw new IllegalArgumentException("authTokenType is null");
        }
        throw new IllegalArgumentException("account is null");
    }

    @Deprecated
    public AccountManagerFuture<Boolean> removeAccount(final Account account, AccountManagerCallback<Boolean> accountManagerCallback, Handler handler) {
        if (account != null) {
            return new Future2Task<Boolean>(handler, accountManagerCallback){

                @Override
                public Boolean bundleToResult(Bundle bundle) throws AuthenticatorException {
                    if (bundle.containsKey(AccountManager.KEY_BOOLEAN_RESULT)) {
                        return bundle.getBoolean(AccountManager.KEY_BOOLEAN_RESULT);
                    }
                    throw new AuthenticatorException("no result in response");
                }

                @Override
                public void doWork() throws RemoteException {
                    AccountManager.this.mService.removeAccount(this.mResponse, account, false);
                }
            }.start();
        }
        throw new IllegalArgumentException("account is null");
    }

    public AccountManagerFuture<Bundle> removeAccount(final Account account, final Activity activity, AccountManagerCallback<Bundle> accountManagerCallback, Handler handler) {
        if (account != null) {
            return new AmsTask(activity, handler, accountManagerCallback){

                @Override
                public void doWork() throws RemoteException {
                    IAccountManager iAccountManager = AccountManager.this.mService;
                    IAccountManagerResponse iAccountManagerResponse = this.mResponse;
                    Account account2 = account;
                    boolean bl = activity != null;
                    iAccountManager.removeAccount(iAccountManagerResponse, account2, bl);
                }
            }.start();
        }
        throw new IllegalArgumentException("account is null");
    }

    @Deprecated
    public AccountManagerFuture<Boolean> removeAccountAsUser(final Account account, AccountManagerCallback<Boolean> accountManagerCallback, Handler handler, final UserHandle userHandle) {
        if (account != null) {
            if (userHandle != null) {
                return new Future2Task<Boolean>(handler, accountManagerCallback){

                    @Override
                    public Boolean bundleToResult(Bundle bundle) throws AuthenticatorException {
                        if (bundle.containsKey(AccountManager.KEY_BOOLEAN_RESULT)) {
                            return bundle.getBoolean(AccountManager.KEY_BOOLEAN_RESULT);
                        }
                        throw new AuthenticatorException("no result in response");
                    }

                    @Override
                    public void doWork() throws RemoteException {
                        AccountManager.this.mService.removeAccountAsUser(this.mResponse, account, false, userHandle.getIdentifier());
                    }
                }.start();
            }
            throw new IllegalArgumentException("userHandle is null");
        }
        throw new IllegalArgumentException("account is null");
    }

    public AccountManagerFuture<Bundle> removeAccountAsUser(final Account account, final Activity activity, AccountManagerCallback<Bundle> accountManagerCallback, Handler handler, final UserHandle userHandle) {
        if (account != null) {
            if (userHandle != null) {
                return new AmsTask(activity, handler, accountManagerCallback){

                    @Override
                    public void doWork() throws RemoteException {
                        IAccountManager iAccountManager = AccountManager.this.mService;
                        IAccountManagerResponse iAccountManagerResponse = this.mResponse;
                        Account account2 = account;
                        boolean bl = activity != null;
                        iAccountManager.removeAccountAsUser(iAccountManagerResponse, account2, bl, userHandle.getIdentifier());
                    }
                }.start();
            }
            throw new IllegalArgumentException("userHandle is null");
        }
        throw new IllegalArgumentException("account is null");
    }

    public boolean removeAccountExplicitly(Account account) {
        if (account != null) {
            try {
                boolean bl = this.mService.removeAccountExplicitly(account);
                return bl;
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
        throw new IllegalArgumentException("account is null");
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void removeOnAccountsUpdatedListener(OnAccountsUpdateListener onAccountsUpdateListener) {
        if (onAccountsUpdateListener == null) {
            throw new IllegalArgumentException("listener is null");
        }
        HashMap<OnAccountsUpdateListener, Handler> hashMap = this.mAccountsUpdatedListeners;
        synchronized (hashMap) {
            if (!this.mAccountsUpdatedListeners.containsKey(onAccountsUpdateListener)) {
                Log.e(TAG, "Listener was not previously added");
                return;
            }
            Object object = this.mAccountsUpdatedListenersTypes.get(onAccountsUpdateListener);
            object = object != null ? object.toArray(new String[object.size()]) : null;
            this.mAccountsUpdatedListeners.remove(onAccountsUpdateListener);
            this.mAccountsUpdatedListenersTypes.remove(onAccountsUpdateListener);
            if (this.mAccountsUpdatedListeners.isEmpty()) {
                this.mContext.unregisterReceiver(this.mAccountsChangedBroadcastReceiver);
            }
            try {
                this.mService.unregisterAccountListener((String[])object, this.mContext.getOpPackageName());
                return;
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
    }

    public boolean removeSharedAccount(Account account, UserHandle userHandle) {
        try {
            boolean bl = this.mService.removeSharedAccountAsUser(account, userHandle.getIdentifier());
            return bl;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public AccountManagerFuture<Account> renameAccount(final Account account, final String string2, AccountManagerCallback<Account> accountManagerCallback, Handler handler) {
        if (account != null) {
            if (!TextUtils.isEmpty(string2)) {
                return new Future2Task<Account>(handler, accountManagerCallback){

                    @Override
                    public Account bundleToResult(Bundle bundle) throws AuthenticatorException {
                        return new Account(bundle.getString(AccountManager.KEY_ACCOUNT_NAME), bundle.getString(AccountManager.KEY_ACCOUNT_TYPE), bundle.getString(AccountManager.KEY_ACCOUNT_ACCESS_ID));
                    }

                    @Override
                    public void doWork() throws RemoteException {
                        AccountManager.this.mService.renameAccount(this.mResponse, account, string2);
                    }
                }.start();
            }
            throw new IllegalArgumentException("newName is empty or null.");
        }
        throw new IllegalArgumentException("account is null.");
    }

    public boolean setAccountVisibility(Account account, String string2, int n) {
        if (account != null) {
            try {
                boolean bl = this.mService.setAccountVisibility(account, string2, n);
                return bl;
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
        throw new IllegalArgumentException("account is null");
    }

    public void setAuthToken(Account account, String string2, String string3) {
        if (account != null) {
            if (string2 != null) {
                try {
                    this.mService.setAuthToken(account, string2, string3);
                    return;
                }
                catch (RemoteException remoteException) {
                    throw remoteException.rethrowFromSystemServer();
                }
            }
            throw new IllegalArgumentException("authTokenType is null");
        }
        throw new IllegalArgumentException("account is null");
    }

    public void setPassword(Account account, String string2) {
        if (account != null) {
            try {
                this.mService.setPassword(account, string2);
                return;
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
        throw new IllegalArgumentException("account is null");
    }

    public void setUserData(Account account, String string2, String string3) {
        if (account != null) {
            if (string2 != null) {
                try {
                    this.mService.setUserData(account, string2, string3);
                    return;
                }
                catch (RemoteException remoteException) {
                    throw remoteException.rethrowFromSystemServer();
                }
            }
            throw new IllegalArgumentException("key is null");
        }
        throw new IllegalArgumentException("account is null");
    }

    public boolean someUserHasAccount(Account account) {
        try {
            boolean bl = this.mService.someUserHasAccount(account);
            return bl;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public AccountManagerFuture<Bundle> startAddAccountSession(final String string2, final String string3, final String[] arrstring, Bundle bundle, final Activity activity, AccountManagerCallback<Bundle> accountManagerCallback, Handler handler) {
        if (string2 != null) {
            final Bundle bundle2 = new Bundle();
            if (bundle != null) {
                bundle2.putAll(bundle);
            }
            bundle2.putString(KEY_ANDROID_PACKAGE_NAME, this.mContext.getPackageName());
            return new AmsTask(activity, handler, accountManagerCallback){

                @Override
                public void doWork() throws RemoteException {
                    IAccountManager iAccountManager = AccountManager.this.mService;
                    IAccountManagerResponse iAccountManagerResponse = this.mResponse;
                    String string22 = string2;
                    String string32 = string3;
                    String[] arrstring2 = arrstring;
                    boolean bl = activity != null;
                    iAccountManager.startAddAccountSession(iAccountManagerResponse, string22, string32, arrstring2, bl, bundle2);
                }
            }.start();
        }
        throw new IllegalArgumentException("accountType is null");
    }

    public AccountManagerFuture<Bundle> startUpdateCredentialsSession(final Account account, final String string2, Bundle bundle, final Activity activity, AccountManagerCallback<Bundle> accountManagerCallback, Handler handler) {
        if (account != null) {
            final Bundle bundle2 = new Bundle();
            if (bundle != null) {
                bundle2.putAll(bundle);
            }
            bundle2.putString(KEY_ANDROID_PACKAGE_NAME, this.mContext.getPackageName());
            return new AmsTask(activity, handler, accountManagerCallback){

                @Override
                public void doWork() throws RemoteException {
                    IAccountManager iAccountManager = AccountManager.this.mService;
                    IAccountManagerResponse iAccountManagerResponse = this.mResponse;
                    Account account2 = account;
                    String string22 = string2;
                    boolean bl = activity != null;
                    iAccountManager.startUpdateCredentialsSession(iAccountManagerResponse, account2, string22, bl, bundle2);
                }
            }.start();
        }
        throw new IllegalArgumentException("account is null");
    }

    public void updateAppPermission(Account account, String string2, int n, boolean bl) {
        try {
            this.mService.updateAppPermission(account, string2, n, bl);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public AccountManagerFuture<Bundle> updateCredentials(final Account account, final String string2, final Bundle bundle, final Activity activity, AccountManagerCallback<Bundle> accountManagerCallback, Handler handler) {
        if (account != null) {
            return new AmsTask(activity, handler, accountManagerCallback){

                @Override
                public void doWork() throws RemoteException {
                    IAccountManager iAccountManager = AccountManager.this.mService;
                    IAccountManagerResponse iAccountManagerResponse = this.mResponse;
                    Account account2 = account;
                    String string22 = string2;
                    boolean bl = activity != null;
                    iAccountManager.updateCredentials(iAccountManagerResponse, account2, string22, bl, bundle);
                }
            }.start();
        }
        throw new IllegalArgumentException("account is null");
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface AccountVisibility {
    }

    private abstract class AmsTask
    extends FutureTask<Bundle>
    implements AccountManagerFuture<Bundle> {
        @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
        final Activity mActivity;
        final AccountManagerCallback<Bundle> mCallback;
        @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
        final Handler mHandler;
        @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
        final IAccountManagerResponse mResponse;

        public AmsTask(Activity activity, Handler handler, AccountManagerCallback<Bundle> accountManagerCallback) {
            super(new Callable<Bundle>(){

                @Override
                public Bundle call() throws Exception {
                    throw new IllegalStateException("this should never be called");
                }
            });
            this.mHandler = handler;
            this.mCallback = accountManagerCallback;
            this.mActivity = activity;
            this.mResponse = new Response();
        }

        /*
         * Exception decompiling
         */
        private Bundle internalGetResult(Long var1_1, TimeUnit var2_9) throws OperationCanceledException, IOException, AuthenticatorException {
            // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
            // org.benf.cfr.reader.util.ConfusedCFRException: Started 2 blocks at once
            // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.getStartingBlocks(Op04StructuredStatement.java:404)
            // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:482)
            // org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:607)
            // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:696)
            // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:184)
            // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:129)
            // org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:96)
            // org.benf.cfr.reader.entities.Method.analyse(Method.java:397)
            // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:906)
            // org.benf.cfr.reader.entities.ClassFile.analyseInnerClassesPass1(ClassFile.java:778)
            // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:886)
            // org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:797)
            // org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:225)
            // org.benf.cfr.reader.Driver.doJar(Driver.java:109)
            // org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:65)
            // org.benf.cfr.reader.Main.main(Main.java:48)
            throw new IllegalStateException("Decompilation failed");
        }

        public abstract void doWork() throws RemoteException;

        @Override
        protected void done() {
            AccountManagerCallback<Bundle> accountManagerCallback = this.mCallback;
            if (accountManagerCallback != null) {
                AccountManager.this.postToHandler(this.mHandler, accountManagerCallback, this);
            }
        }

        @Override
        public Bundle getResult() throws OperationCanceledException, IOException, AuthenticatorException {
            return this.internalGetResult(null, null);
        }

        @Override
        public Bundle getResult(long l, TimeUnit timeUnit) throws OperationCanceledException, IOException, AuthenticatorException {
            return this.internalGetResult(l, timeUnit);
        }

        @Override
        protected void set(Bundle bundle) {
            if (bundle == null) {
                Log.e("AccountManager", "the bundle must not be null", new Exception());
            }
            super.set(bundle);
        }

        public final AccountManagerFuture<Bundle> start() {
            try {
                this.doWork();
            }
            catch (RemoteException remoteException) {
                this.setException(remoteException);
            }
            return this;
        }

        private class Response
        extends IAccountManagerResponse.Stub {
            private Response() {
            }

            @Override
            public void onError(int n, String string2) {
                if (n != 4 && n != 100 && n != 101) {
                    AmsTask amsTask = AmsTask.this;
                    amsTask.setException(amsTask.AccountManager.this.convertErrorToException(n, string2));
                    return;
                }
                AmsTask.this.cancel(true);
            }

            @Override
            public void onResult(Bundle bundle) {
                if (bundle == null) {
                    this.onError(5, "null bundle returned");
                    return;
                }
                Intent intent = (Intent)bundle.getParcelable(AccountManager.KEY_INTENT);
                if (intent != null && AmsTask.this.mActivity != null) {
                    AmsTask.this.mActivity.startActivity(intent);
                } else if (bundle.getBoolean("retry")) {
                    try {
                        AmsTask.this.doWork();
                    }
                    catch (RemoteException remoteException) {
                        throw remoteException.rethrowFromSystemServer();
                    }
                } else {
                    AmsTask.this.set(bundle);
                }
            }
        }

    }

    private abstract class BaseFutureTask<T>
    extends FutureTask<T> {
        final Handler mHandler;
        public final IAccountManagerResponse mResponse;

        public BaseFutureTask(Handler handler) {
            super(new Callable<T>(){

                @Override
                public T call() throws Exception {
                    throw new IllegalStateException("this should never be called");
                }
            });
            this.mHandler = handler;
            this.mResponse = new Response();
        }

        public abstract T bundleToResult(Bundle var1) throws AuthenticatorException;

        public abstract void doWork() throws RemoteException;

        protected void postRunnableToHandler(Runnable runnable) {
            Handler handler;
            Handler handler2 = handler = this.mHandler;
            if (handler == null) {
                handler2 = AccountManager.this.mMainHandler;
            }
            handler2.post(runnable);
        }

        protected void startTask() {
            try {
                this.doWork();
            }
            catch (RemoteException remoteException) {
                this.setException(remoteException);
            }
        }

        protected class Response
        extends IAccountManagerResponse.Stub {
            protected Response() {
            }

            @Override
            public void onError(int n, String string2) {
                if (n != 4 && n != 100 && n != 101) {
                    BaseFutureTask baseFutureTask = BaseFutureTask.this;
                    baseFutureTask.setException(baseFutureTask.AccountManager.this.convertErrorToException(n, string2));
                    return;
                }
                BaseFutureTask.this.cancel(true);
            }

            @Override
            public void onResult(Bundle bundle) {
                block4 : {
                    bundle = BaseFutureTask.this.bundleToResult(bundle);
                    if (bundle != null) break block4;
                    return;
                }
                try {
                    BaseFutureTask.this.set(bundle);
                    return;
                }
                catch (AuthenticatorException authenticatorException) {
                }
                catch (ClassCastException classCastException) {
                    // empty catch block
                }
                this.onError(5, "no result in response");
            }
        }

    }

    private abstract class Future2Task<T>
    extends BaseFutureTask<T>
    implements AccountManagerFuture<T> {
        final AccountManagerCallback<T> mCallback;

        public Future2Task(Handler handler, AccountManagerCallback<T> accountManagerCallback) {
            super(handler);
            this.mCallback = accountManagerCallback;
        }

        /*
         * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
         * Loose catch block
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         * Lifted jumps to return sites
         */
        private T internalGetResult(Long l, TimeUnit object) throws OperationCanceledException, IOException, AuthenticatorException {
            block13 : {
                Throwable throwable2222;
                block12 : {
                    if (!this.isDone()) {
                        AccountManager.this.ensureNotOnMainThread();
                    }
                    if (l == null) {
                        l = this.get();
                        this.cancel(true);
                        return (T)l;
                    }
                    try {
                        l = this.get(l, (TimeUnit)((Object)object));
                    }
                    catch (CancellationException cancellationException) {
                        break block13;
                    }
                    catch (TimeoutException timeoutException) {
                        break block13;
                    }
                    catch (InterruptedException interruptedException) {
                        // empty catch block
                    }
                    this.cancel(true);
                    return (T)l;
                    {
                        catch (Throwable throwable2222) {
                            break block12;
                        }
                        catch (ExecutionException executionException) {}
                        {
                            Throwable throwable3 = executionException.getCause();
                            if (throwable3 instanceof IOException) throw (IOException)throwable3;
                            if (throwable3 instanceof UnsupportedOperationException) {
                                object = new AuthenticatorException(throwable3);
                                throw object;
                            }
                            if (throwable3 instanceof AuthenticatorException) throw (AuthenticatorException)throwable3;
                            if (throwable3 instanceof RuntimeException) throw (RuntimeException)throwable3;
                            if (throwable3 instanceof Error) {
                                throw (Error)throwable3;
                            }
                            object = new IllegalStateException(throwable3);
                            throw object;
                        }
                    }
                }
                this.cancel(true);
                throw throwable2222;
            }
            this.cancel(true);
            throw new OperationCanceledException();
        }

        @Override
        protected void done() {
            if (this.mCallback != null) {
                this.postRunnableToHandler(new Runnable(){

                    @Override
                    public void run() {
                        Future2Task.this.mCallback.run(Future2Task.this);
                    }
                });
            }
        }

        @Override
        public T getResult() throws OperationCanceledException, IOException, AuthenticatorException {
            return this.internalGetResult(null, null);
        }

        @Override
        public T getResult(long l, TimeUnit timeUnit) throws OperationCanceledException, IOException, AuthenticatorException {
            return this.internalGetResult(l, timeUnit);
        }

        public Future2Task<T> start() {
            this.startTask();
            return this;
        }

    }

    private class GetAuthTokenByTypeAndFeaturesTask
    extends AmsTask
    implements AccountManagerCallback<Bundle> {
        final String mAccountType;
        final Bundle mAddAccountOptions;
        @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
        final String mAuthTokenType;
        final String[] mFeatures;
        volatile AccountManagerFuture<Bundle> mFuture;
        @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
        final Bundle mLoginOptions;
        @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
        final AccountManagerCallback<Bundle> mMyCallback;
        private volatile int mNumAccounts;

        GetAuthTokenByTypeAndFeaturesTask(String string2, String string3, String[] arrstring, Activity activity, Bundle bundle, Bundle bundle2, AccountManagerCallback<Bundle> accountManagerCallback, Handler handler) {
            super(activity, handler, accountManagerCallback);
            this.mFuture = null;
            this.mNumAccounts = 0;
            if (string2 != null) {
                this.mAccountType = string2;
                this.mAuthTokenType = string3;
                this.mFeatures = arrstring;
                this.mAddAccountOptions = bundle;
                this.mLoginOptions = bundle2;
                this.mMyCallback = this;
                return;
            }
            throw new IllegalArgumentException("account type is null");
        }

        @Override
        public void doWork() throws RemoteException {
            AccountManager.this.getAccountByTypeAndFeatures(this.mAccountType, this.mFeatures, new AccountManagerCallback<Bundle>(){

                /*
                 * Loose catch block
                 * Enabled aggressive block sorting
                 * Enabled unnecessary exception pruning
                 * Enabled aggressive exception aggregation
                 * Lifted jumps to return sites
                 */
                @Override
                public void run(AccountManagerFuture<Bundle> object) {
                    Object object2;
                    block8 : {
                        object2 = (Bundle)object.getResult();
                        object = ((BaseBundle)object2).getString(AccountManager.KEY_ACCOUNT_NAME);
                        object2 = ((BaseBundle)object2).getString(AccountManager.KEY_ACCOUNT_TYPE);
                        if (object != null) break block8;
                        if (GetAuthTokenByTypeAndFeaturesTask.this.mActivity != null) {
                            object = GetAuthTokenByTypeAndFeaturesTask.this;
                            ((GetAuthTokenByTypeAndFeaturesTask)object).mFuture = ((GetAuthTokenByTypeAndFeaturesTask)object).AccountManager.this.addAccount(GetAuthTokenByTypeAndFeaturesTask.this.mAccountType, GetAuthTokenByTypeAndFeaturesTask.this.mAuthTokenType, GetAuthTokenByTypeAndFeaturesTask.this.mFeatures, GetAuthTokenByTypeAndFeaturesTask.this.mAddAccountOptions, GetAuthTokenByTypeAndFeaturesTask.this.mActivity, GetAuthTokenByTypeAndFeaturesTask.this.mMyCallback, GetAuthTokenByTypeAndFeaturesTask.this.mHandler);
                            return;
                        }
                        object = new Bundle();
                        ((BaseBundle)object).putString(AccountManager.KEY_ACCOUNT_NAME, null);
                        ((BaseBundle)object).putString(AccountManager.KEY_ACCOUNT_TYPE, null);
                        ((BaseBundle)object).putString(AccountManager.KEY_AUTHTOKEN, null);
                        ((Bundle)object).putBinder(AccountManager.KEY_ACCOUNT_ACCESS_ID, null);
                        try {
                            GetAuthTokenByTypeAndFeaturesTask.this.mResponse.onResult((Bundle)object);
                            return;
                        }
                        catch (RemoteException remoteException) {
                            return;
                        }
                    }
                    GetAuthTokenByTypeAndFeaturesTask.this.mNumAccounts = 1;
                    object = new Account((String)object, (String)object2);
                    if (GetAuthTokenByTypeAndFeaturesTask.this.mActivity == null) {
                        object2 = GetAuthTokenByTypeAndFeaturesTask.this;
                        ((GetAuthTokenByTypeAndFeaturesTask)object2).mFuture = ((GetAuthTokenByTypeAndFeaturesTask)object2).AccountManager.this.getAuthToken((Account)object, GetAuthTokenByTypeAndFeaturesTask.this.mAuthTokenType, false, GetAuthTokenByTypeAndFeaturesTask.this.mMyCallback, GetAuthTokenByTypeAndFeaturesTask.this.mHandler);
                        return;
                    }
                    object2 = GetAuthTokenByTypeAndFeaturesTask.this;
                    ((GetAuthTokenByTypeAndFeaturesTask)object2).mFuture = ((GetAuthTokenByTypeAndFeaturesTask)object2).AccountManager.this.getAuthToken((Account)object, GetAuthTokenByTypeAndFeaturesTask.this.mAuthTokenType, GetAuthTokenByTypeAndFeaturesTask.this.mLoginOptions, GetAuthTokenByTypeAndFeaturesTask.this.mActivity, GetAuthTokenByTypeAndFeaturesTask.this.mMyCallback, GetAuthTokenByTypeAndFeaturesTask.this.mHandler);
                    return;
                    catch (AuthenticatorException authenticatorException) {
                        GetAuthTokenByTypeAndFeaturesTask.this.setException(authenticatorException);
                        return;
                    }
                    catch (IOException iOException) {
                        GetAuthTokenByTypeAndFeaturesTask.this.setException(iOException);
                        return;
                    }
                    catch (OperationCanceledException operationCanceledException) {
                        GetAuthTokenByTypeAndFeaturesTask.this.setException(operationCanceledException);
                        return;
                    }
                }
            }, this.mHandler);
        }

        @Override
        public void run(AccountManagerFuture<Bundle> object) {
            try {
                Object object2 = object.getResult();
                if (this.mNumAccounts == 0) {
                    String string2 = ((BaseBundle)object2).getString(AccountManager.KEY_ACCOUNT_NAME);
                    object = ((BaseBundle)object2).getString(AccountManager.KEY_ACCOUNT_TYPE);
                    if (!TextUtils.isEmpty(string2) && !TextUtils.isEmpty((CharSequence)object)) {
                        object2 = ((BaseBundle)object2).getString(AccountManager.KEY_ACCOUNT_ACCESS_ID);
                        Account account = new Account(string2, (String)object, (String)object2);
                        this.mNumAccounts = 1;
                        AccountManager.this.getAuthToken(account, this.mAuthTokenType, null, this.mActivity, this.mMyCallback, this.mHandler);
                        return;
                    }
                    object = new AuthenticatorException("account not in result");
                    this.setException((Throwable)object);
                    return;
                }
                this.set((Bundle)object2);
            }
            catch (AuthenticatorException authenticatorException) {
                this.setException(authenticatorException);
            }
            catch (IOException iOException) {
                this.setException(iOException);
            }
            catch (OperationCanceledException operationCanceledException) {
                this.cancel(true);
            }
        }

    }

}

