/*
 * Decompiled with CFR 0.145.
 */
package android.accounts;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AccountManagerCallback;
import android.accounts.AccountManagerFuture;
import android.accounts.AuthenticatorDescription;
import android.accounts.AuthenticatorException;
import android.accounts.ChooseAccountTypeActivity;
import android.accounts.OperationCanceledException;
import android.app.Activity;
import android.app.ActivityTaskManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.BaseBundle;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Parcelable;
import android.os.RemoteException;
import android.os.UserHandle;
import android.os.UserManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import com.google.android.collect.Sets;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class ChooseTypeAndAccountActivity
extends Activity
implements AccountManagerCallback<Bundle> {
    public static final String EXTRA_ADD_ACCOUNT_AUTH_TOKEN_TYPE_STRING = "authTokenType";
    public static final String EXTRA_ADD_ACCOUNT_OPTIONS_BUNDLE = "addAccountOptions";
    public static final String EXTRA_ADD_ACCOUNT_REQUIRED_FEATURES_STRING_ARRAY = "addAccountRequiredFeatures";
    public static final String EXTRA_ALLOWABLE_ACCOUNTS_ARRAYLIST = "allowableAccounts";
    public static final String EXTRA_ALLOWABLE_ACCOUNT_TYPES_STRING_ARRAY = "allowableAccountTypes";
    @Deprecated
    public static final String EXTRA_ALWAYS_PROMPT_FOR_ACCOUNT = "alwaysPromptForAccount";
    public static final String EXTRA_DESCRIPTION_TEXT_OVERRIDE = "descriptionTextOverride";
    public static final String EXTRA_SELECTED_ACCOUNT = "selectedAccount";
    private static final String KEY_INSTANCE_STATE_ACCOUNTS_LIST = "accountsList";
    private static final String KEY_INSTANCE_STATE_EXISTING_ACCOUNTS = "existingAccounts";
    private static final String KEY_INSTANCE_STATE_PENDING_REQUEST = "pendingRequest";
    private static final String KEY_INSTANCE_STATE_SELECTED_ACCOUNT_NAME = "selectedAccountName";
    private static final String KEY_INSTANCE_STATE_SELECTED_ADD_ACCOUNT = "selectedAddAccount";
    private static final String KEY_INSTANCE_STATE_VISIBILITY_LIST = "visibilityList";
    public static final int REQUEST_ADD_ACCOUNT = 2;
    public static final int REQUEST_CHOOSE_TYPE = 1;
    public static final int REQUEST_NULL = 0;
    private static final int SELECTED_ITEM_NONE = -1;
    private static final String TAG = "AccountChooser";
    private LinkedHashMap<Account, Integer> mAccounts;
    private String mCallingPackage;
    private int mCallingUid;
    private String mDescriptionOverride;
    private boolean mDisallowAddAccounts;
    private boolean mDontShowPicker;
    private Parcelable[] mExistingAccounts = null;
    private Button mOkButton;
    private int mPendingRequest = 0;
    private ArrayList<Account> mPossiblyVisibleAccounts;
    private String mSelectedAccountName = null;
    private boolean mSelectedAddNewAccount = false;
    private int mSelectedItemIndex;
    private Set<Account> mSetOfAllowableAccounts;
    private Set<String> mSetOfRelevantAccountTypes;

    private LinkedHashMap<Account, Integer> getAcceptableAccountChoices(AccountManager object) {
        Map<Account, Integer> map = ((AccountManager)object).getAccountsAndVisibilityForPackage(this.mCallingPackage, null);
        Account[] arraccount = ((AccountManager)object).getAccounts();
        object = new LinkedHashMap(map.size());
        for (Account account : arraccount) {
            Set<Object> set = this.mSetOfAllowableAccounts;
            if (set != null && !set.contains(account) || (set = this.mSetOfRelevantAccountTypes) != null && !set.contains(account.type) || map.get(account) == null) continue;
            ((HashMap)object).put(account, map.get(account));
        }
        return object;
    }

    private Set<Account> getAllowableAccountSet(Intent cloneable) {
        HashSet<Account> hashSet = null;
        Object object = cloneable.getParcelableArrayListExtra(EXTRA_ALLOWABLE_ACCOUNTS_ARRAYLIST);
        cloneable = hashSet;
        if (object != null) {
            hashSet = new HashSet<Account>(((ArrayList)object).size());
            object = ((ArrayList)object).iterator();
            do {
                cloneable = hashSet;
                if (!object.hasNext()) break;
                hashSet.add((Account)((Parcelable)object.next()));
            } while (true);
        }
        return cloneable;
    }

    private int getItemIndexToSelect(ArrayList<Account> arrayList, String string2, boolean bl) {
        if (bl) {
            return arrayList.size();
        }
        for (int i = 0; i < arrayList.size(); ++i) {
            if (!arrayList.get((int)i).name.equals(string2)) continue;
            return i;
        }
        return -1;
    }

    private String[] getListOfDisplayableOptions(ArrayList<Account> arrayList) {
        String[] arrstring = new String[arrayList.size() + (this.mDisallowAddAccounts ^ true)];
        for (int i = 0; i < arrayList.size(); ++i) {
            arrstring[i] = arrayList.get((int)i).name;
        }
        if (!this.mDisallowAddAccounts) {
            arrstring[arrayList.size()] = this.getResources().getString(17039464);
        }
        return arrstring;
    }

    private Set<String> getReleventAccountTypes(Intent object) {
        block1 : {
            Object object2 = ((Intent)object).getStringArrayExtra(EXTRA_ALLOWABLE_ACCOUNT_TYPES_STRING_ARRAY);
            AuthenticatorDescription[] arrauthenticatorDescription = AccountManager.get(this).getAuthenticatorTypes();
            object = new HashSet(arrauthenticatorDescription.length);
            int n = arrauthenticatorDescription.length;
            for (int i = 0; i < n; ++i) {
                object.add(arrauthenticatorDescription[i].type);
            }
            if (object2 == null) break block1;
            object2 = Sets.newHashSet(object2);
            object2.retainAll((Collection<?>)object);
            object = object2;
        }
        return object;
    }

    private void onAccountSelected(Account account) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("selected account ");
        stringBuilder.append(account);
        Log.d(TAG, stringBuilder.toString());
        this.setResultAndFinish(account.name, account.type);
    }

    private void overrideDescriptionIfSupplied(String string2) {
        TextView textView = (TextView)this.findViewById(16908878);
        if (!TextUtils.isEmpty(string2)) {
            textView.setText(string2);
        } else {
            textView.setVisibility(8);
        }
    }

    private final void populateUIAccountList(String[] object) {
        ListView listView = (ListView)this.findViewById(16908298);
        listView.setAdapter(new ArrayAdapter<String>((Context)this, 17367055, (T[])object));
        listView.setChoiceMode(1);
        listView.setItemsCanFocus(false);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int n, long l) {
                ChooseTypeAndAccountActivity.this.mSelectedItemIndex = n;
                ChooseTypeAndAccountActivity.this.mOkButton.setEnabled(true);
            }
        });
        int n = this.mSelectedItemIndex;
        if (n != -1) {
            listView.setItemChecked(n, true);
            if (Log.isLoggable(TAG, 2)) {
                object = new StringBuilder();
                ((StringBuilder)object).append("List item ");
                ((StringBuilder)object).append(this.mSelectedItemIndex);
                ((StringBuilder)object).append(" should be selected");
                Log.v(TAG, ((StringBuilder)object).toString());
            }
        }
    }

    private void setNonLabelThemeAndCallSuperCreate(Bundle bundle) {
        this.setTheme(16974132);
        super.onCreate(bundle);
    }

    private void setResultAndFinish(String string2, String string3) {
        Account account = new Account(string2, string3);
        Object object = AccountManager.get(this).getAccountVisibility(account, this.mCallingPackage);
        if (object != null && (Integer)object == 4) {
            AccountManager.get(this).setAccountVisibility(account, this.mCallingPackage, 2);
        }
        if (object != null && (Integer)object == 3) {
            this.setResult(0);
            this.finish();
            return;
        }
        object = new Bundle();
        ((BaseBundle)object).putString("authAccount", string2);
        ((BaseBundle)object).putString("accountType", string3);
        this.setResult(-1, new Intent().putExtras((Bundle)object));
        if (Log.isLoggable(TAG, 2)) {
            object = new StringBuilder();
            ((StringBuilder)object).append("ChooseTypeAndAccountActivity.setResultAndFinish: selected account ");
            ((StringBuilder)object).append(string2);
            ((StringBuilder)object).append(", ");
            ((StringBuilder)object).append(string3);
            Log.v(TAG, ((StringBuilder)object).toString());
        }
        this.finish();
    }

    private void startChooseAccountTypeActivity() {
        if (Log.isLoggable(TAG, 2)) {
            Log.v(TAG, "ChooseAccountTypeActivity.startChooseAccountTypeActivity()");
        }
        Intent intent = new Intent(this, ChooseAccountTypeActivity.class);
        intent.setFlags(524288);
        intent.putExtra(EXTRA_ALLOWABLE_ACCOUNT_TYPES_STRING_ARRAY, this.getIntent().getStringArrayExtra(EXTRA_ALLOWABLE_ACCOUNT_TYPES_STRING_ARRAY));
        intent.putExtra(EXTRA_ADD_ACCOUNT_OPTIONS_BUNDLE, this.getIntent().getBundleExtra(EXTRA_ADD_ACCOUNT_OPTIONS_BUNDLE));
        intent.putExtra(EXTRA_ADD_ACCOUNT_REQUIRED_FEATURES_STRING_ARRAY, this.getIntent().getStringArrayExtra(EXTRA_ADD_ACCOUNT_REQUIRED_FEATURES_STRING_ARRAY));
        intent.putExtra(EXTRA_ADD_ACCOUNT_AUTH_TOKEN_TYPE_STRING, this.getIntent().getStringExtra(EXTRA_ADD_ACCOUNT_AUTH_TOKEN_TYPE_STRING));
        this.startActivityForResult(intent, 1);
        this.mPendingRequest = 1;
    }

    @Override
    protected void onActivityResult(int n, int n2, Intent object) {
        block14 : {
            block16 : {
                Object object2;
                block18 : {
                    Object object3;
                    CharSequence charSequence;
                    block17 : {
                        block15 : {
                            if (Log.isLoggable(TAG, 2)) {
                                if (object != null && object.getExtras() != null) {
                                    object.getExtras().keySet();
                                }
                                object3 = object != null ? object.getExtras() : null;
                                charSequence = new StringBuilder();
                                ((StringBuilder)charSequence).append("ChooseTypeAndAccountActivity.onActivityResult(reqCode=");
                                ((StringBuilder)charSequence).append(n);
                                ((StringBuilder)charSequence).append(", resCode=");
                                ((StringBuilder)charSequence).append(n2);
                                ((StringBuilder)charSequence).append(", extras=");
                                ((StringBuilder)charSequence).append(object3);
                                ((StringBuilder)charSequence).append(")");
                                Log.v(TAG, ((StringBuilder)charSequence).toString());
                            }
                            this.mPendingRequest = 0;
                            if (n2 == 0) {
                                if (this.mPossiblyVisibleAccounts.isEmpty()) {
                                    this.setResult(0);
                                    this.finish();
                                }
                                return;
                            }
                            if (n2 != -1) break block14;
                            if (n != 1) break block15;
                            if (object != null && (object = object.getStringExtra("accountType")) != null) {
                                this.runAddAccountForAuthenticator((String)object);
                                return;
                            }
                            Log.d(TAG, "ChooseTypeAndAccountActivity.onActivityResult: unable to find account type, pretending the request was canceled");
                            break block16;
                        }
                        if (n != 2) break block16;
                        object3 = null;
                        charSequence = null;
                        if (object != null) {
                            object3 = object.getStringExtra("authAccount");
                            charSequence = object.getStringExtra("accountType");
                        }
                        if (object3 == null) break block17;
                        object = object3;
                        object2 = charSequence;
                        if (charSequence != null) break block18;
                    }
                    Account[] arraccount = AccountManager.get(this).getAccountsForPackage(this.mCallingPackage, this.mCallingUid);
                    HashSet<Account> hashSet = new HashSet<Account>();
                    object = this.mExistingAccounts;
                    n2 = ((Parcelable[])object).length;
                    for (n = 0; n < n2; ++n) {
                        hashSet.add((Account)object[n]);
                    }
                    n2 = arraccount.length;
                    n = 0;
                    do {
                        object = object3;
                        object2 = charSequence;
                        if (n >= n2) break;
                        object2 = arraccount[n];
                        if (!hashSet.contains(object2)) {
                            object = ((Account)object2).name;
                            object2 = ((Account)object2).type;
                            break;
                        }
                        ++n;
                    } while (true);
                }
                if (object != null || object2 != null) {
                    this.setResultAndFinish((String)object, (String)object2);
                    return;
                }
            }
            Log.d(TAG, "ChooseTypeAndAccountActivity.onActivityResult: unable to find added account, pretending the request was canceled");
        }
        if (Log.isLoggable(TAG, 2)) {
            Log.v(TAG, "ChooseTypeAndAccountActivity.onActivityResult: canceled");
        }
        this.setResult(0);
        this.finish();
    }

    public void onCancelButtonClicked(View view) {
        this.onBackPressed();
    }

    @Override
    public void onCreate(Bundle object) {
        String[] arrstring;
        if (Log.isLoggable(TAG, 2)) {
            arrstring = new StringBuilder();
            arrstring.append("ChooseTypeAndAccountActivity.onCreate(savedInstanceState=");
            arrstring.append(object);
            arrstring.append(")");
            Log.v(TAG, arrstring.toString());
        }
        try {
            arrstring = this.getActivityToken();
            this.mCallingUid = ActivityTaskManager.getService().getLaunchedFromUid((IBinder)arrstring);
            this.mCallingPackage = ActivityTaskManager.getService().getLaunchedFromPackage((IBinder)arrstring);
            if (this.mCallingUid != 0 && this.mCallingPackage != null) {
                UserManager object22 = UserManager.get(this);
                arrstring = new UserHandle(UserHandle.getUserId(this.mCallingUid));
                this.mDisallowAddAccounts = object22.getUserRestrictions((UserHandle)arrstring).getBoolean("no_modify_accounts", false);
            }
        }
        catch (RemoteException remoteException) {
            String string2 = this.getClass().getSimpleName();
            arrstring = new StringBuilder();
            arrstring.append("Unable to get caller identity \n");
            arrstring.append(remoteException);
            Log.w(string2, arrstring.toString());
        }
        arrstring = this.getIntent();
        this.mSetOfAllowableAccounts = this.getAllowableAccountSet((Intent)arrstring);
        this.mSetOfRelevantAccountTypes = this.getReleventAccountTypes((Intent)arrstring);
        this.mDescriptionOverride = arrstring.getStringExtra(EXTRA_DESCRIPTION_TEXT_OVERRIDE);
        if (object != null) {
            this.mPendingRequest = ((BaseBundle)object).getInt(KEY_INSTANCE_STATE_PENDING_REQUEST);
            this.mExistingAccounts = ((Bundle)object).getParcelableArray(KEY_INSTANCE_STATE_EXISTING_ACCOUNTS);
            this.mSelectedAccountName = ((BaseBundle)object).getString(KEY_INSTANCE_STATE_SELECTED_ACCOUNT_NAME);
            this.mSelectedAddNewAccount = ((BaseBundle)object).getBoolean(KEY_INSTANCE_STATE_SELECTED_ADD_ACCOUNT, false);
            Parcelable[] arrparcelable = ((Bundle)object).getParcelableArray(KEY_INSTANCE_STATE_ACCOUNTS_LIST);
            arrstring = ((Bundle)object).getIntegerArrayList(KEY_INSTANCE_STATE_VISIBILITY_LIST);
            this.mAccounts = new LinkedHashMap();
            for (int i = 0; i < arrparcelable.length; ++i) {
                this.mAccounts.put((Account)arrparcelable[i], (Integer)arrstring.get(i));
            }
        } else {
            this.mPendingRequest = 0;
            this.mExistingAccounts = null;
            if ((arrstring = (Account)arrstring.getParcelableExtra(EXTRA_SELECTED_ACCOUNT)) != null) {
                this.mSelectedAccountName = arrstring.name;
            }
            this.mAccounts = this.getAcceptableAccountChoices(AccountManager.get(this));
        }
        if (Log.isLoggable(TAG, 2)) {
            arrstring = new StringBuilder();
            arrstring.append("selected account name is ");
            arrstring.append(this.mSelectedAccountName);
            Log.v(TAG, arrstring.toString());
        }
        this.mPossiblyVisibleAccounts = new ArrayList(this.mAccounts.size());
        for (Map.Entry entry : this.mAccounts.entrySet()) {
            if (3 == (Integer)entry.getValue()) continue;
            this.mPossiblyVisibleAccounts.add((Account)entry.getKey());
        }
        boolean bl = this.mPossiblyVisibleAccounts.isEmpty();
        boolean bl2 = true;
        if (bl && this.mDisallowAddAccounts) {
            this.requestWindowFeature(1);
            this.setContentView(17367094);
            this.mDontShowPicker = true;
        }
        if (this.mDontShowPicker) {
            super.onCreate((Bundle)object);
            return;
        }
        if (this.mPendingRequest == 0 && this.mPossiblyVisibleAccounts.isEmpty()) {
            this.setNonLabelThemeAndCallSuperCreate((Bundle)object);
            if (this.mSetOfRelevantAccountTypes.size() == 1) {
                this.runAddAccountForAuthenticator(this.mSetOfRelevantAccountTypes.iterator().next());
            } else {
                this.startChooseAccountTypeActivity();
            }
        }
        arrstring = this.getListOfDisplayableOptions(this.mPossiblyVisibleAccounts);
        this.mSelectedItemIndex = this.getItemIndexToSelect(this.mPossiblyVisibleAccounts, this.mSelectedAccountName, this.mSelectedAddNewAccount);
        super.onCreate((Bundle)object);
        this.setContentView(17367119);
        this.overrideDescriptionIfSupplied(this.mDescriptionOverride);
        this.populateUIAccountList(arrstring);
        this.mOkButton = (Button)this.findViewById(16908314);
        object = this.mOkButton;
        if (this.mSelectedItemIndex == -1) {
            bl2 = false;
        }
        ((TextView)object).setEnabled(bl2);
    }

    @Override
    protected void onDestroy() {
        if (Log.isLoggable(TAG, 2)) {
            Log.v(TAG, "ChooseTypeAndAccountActivity.onDestroy()");
        }
        super.onDestroy();
    }

    public void onOkButtonClicked(View view) {
        if (this.mSelectedItemIndex == this.mPossiblyVisibleAccounts.size()) {
            this.startChooseAccountTypeActivity();
        } else {
            int n = this.mSelectedItemIndex;
            if (n != -1) {
                this.onAccountSelected(this.mPossiblyVisibleAccounts.get(n));
            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle bundle) {
        int n;
        super.onSaveInstanceState(bundle);
        bundle.putInt(KEY_INSTANCE_STATE_PENDING_REQUEST, this.mPendingRequest);
        if (this.mPendingRequest == 2) {
            bundle.putParcelableArray(KEY_INSTANCE_STATE_EXISTING_ACCOUNTS, this.mExistingAccounts);
        }
        if ((n = this.mSelectedItemIndex) != -1) {
            if (n == this.mPossiblyVisibleAccounts.size()) {
                bundle.putBoolean(KEY_INSTANCE_STATE_SELECTED_ADD_ACCOUNT, true);
            } else {
                bundle.putBoolean(KEY_INSTANCE_STATE_SELECTED_ADD_ACCOUNT, false);
                bundle.putString(KEY_INSTANCE_STATE_SELECTED_ACCOUNT_NAME, this.mPossiblyVisibleAccounts.get((int)this.mSelectedItemIndex).name);
            }
        }
        Parcelable[] arrparcelable = new Parcelable[this.mAccounts.size()];
        ArrayList<Integer> arrayList = new ArrayList<Integer>(this.mAccounts.size());
        n = 0;
        for (Map.Entry<Account, Integer> entry : this.mAccounts.entrySet()) {
            arrparcelable[n] = entry.getKey();
            arrayList.add(entry.getValue());
            ++n;
        }
        bundle.putParcelableArray(KEY_INSTANCE_STATE_ACCOUNTS_LIST, arrparcelable);
        bundle.putIntegerArrayList(KEY_INSTANCE_STATE_VISIBILITY_LIST, arrayList);
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    @Override
    public void run(AccountManagerFuture<Bundle> object) {
        try {
            object = (Intent)((Bundle)object.getResult()).getParcelable("intent");
            if (object != null) {
                this.mPendingRequest = 2;
                this.mExistingAccounts = AccountManager.get(this).getAccountsForPackage(this.mCallingPackage, this.mCallingUid);
                ((Intent)object).setFlags(((Intent)object).getFlags() & -268435457);
                this.startActivityForResult((Intent)object, 2);
                return;
            }
        }
        catch (AuthenticatorException authenticatorException) {
        }
        catch (IOException iOException) {
            // empty catch block
        }
        object = new Bundle();
        ((BaseBundle)object).putString("errorMessage", "error communicating with server");
        this.setResult(-1, new Intent().putExtras((Bundle)object));
        this.finish();
        return;
        catch (OperationCanceledException operationCanceledException) {
            this.setResult(0);
            this.finish();
            return;
        }
    }

    protected void runAddAccountForAuthenticator(String string2) {
        Object object;
        if (Log.isLoggable(TAG, 2)) {
            object = new StringBuilder();
            ((StringBuilder)object).append("runAddAccountForAuthenticator: ");
            ((StringBuilder)object).append(string2);
            Log.v(TAG, ((StringBuilder)object).toString());
        }
        object = this.getIntent().getBundleExtra(EXTRA_ADD_ACCOUNT_OPTIONS_BUNDLE);
        String[] arrstring = this.getIntent().getStringArrayExtra(EXTRA_ADD_ACCOUNT_REQUIRED_FEATURES_STRING_ARRAY);
        String string3 = this.getIntent().getStringExtra(EXTRA_ADD_ACCOUNT_AUTH_TOKEN_TYPE_STRING);
        AccountManager.get(this).addAccount(string2, string3, arrstring, (Bundle)object, null, this, null);
    }

}

