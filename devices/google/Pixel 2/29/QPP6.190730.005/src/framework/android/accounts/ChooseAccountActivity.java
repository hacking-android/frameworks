/*
 * Decompiled with CFR 0.145.
 */
package android.accounts;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AccountManagerResponse;
import android.accounts.AuthenticatorDescription;
import android.app.Activity;
import android.app.ActivityTaskManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.BaseBundle;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcelable;
import android.os.RemoteException;
import android.os.UserHandle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import java.util.HashMap;

public class ChooseAccountActivity
extends Activity {
    private static final String TAG = "AccountManager";
    private AccountManagerResponse mAccountManagerResponse = null;
    private Parcelable[] mAccounts = null;
    private String mCallingPackage;
    private int mCallingUid;
    private Bundle mResult;
    private HashMap<String, AuthenticatorDescription> mTypeToAuthDescription = new HashMap();

    private void getAuthDescriptions() {
        for (AuthenticatorDescription authenticatorDescription : AccountManager.get(this).getAuthenticatorTypes()) {
            this.mTypeToAuthDescription.put(authenticatorDescription.type, authenticatorDescription);
        }
    }

    private Drawable getDrawableForType(String string2) {
        Object object;
        block4 : {
            AuthenticatorDescription authenticatorDescription = null;
            AuthenticatorDescription authenticatorDescription2 = null;
            object = authenticatorDescription;
            if (!this.mTypeToAuthDescription.containsKey(string2)) break block4;
            try {
                object = this.mTypeToAuthDescription.get(string2);
                object = this.createPackageContext(((AuthenticatorDescription)object).packageName, 0).getDrawable(((AuthenticatorDescription)object).iconId);
            }
            catch (Resources.NotFoundException notFoundException) {
                object = authenticatorDescription;
                if (Log.isLoggable(TAG, 5)) {
                    object = new StringBuilder();
                    ((StringBuilder)object).append("No icon resource for account type ");
                    ((StringBuilder)object).append(string2);
                    Log.w(TAG, ((StringBuilder)object).toString());
                    object = authenticatorDescription;
                }
            }
            catch (PackageManager.NameNotFoundException nameNotFoundException) {
                object = authenticatorDescription2;
                if (!Log.isLoggable(TAG, 5)) break block4;
                object = new StringBuilder();
                ((StringBuilder)object).append("No icon name for account type ");
                ((StringBuilder)object).append(string2);
                Log.w(TAG, ((StringBuilder)object).toString());
                object = authenticatorDescription2;
            }
        }
        return object;
    }

    @Override
    public void finish() {
        AccountManagerResponse accountManagerResponse = this.mAccountManagerResponse;
        if (accountManagerResponse != null) {
            Bundle bundle = this.mResult;
            if (bundle != null) {
                accountManagerResponse.onResult(bundle);
            } else {
                accountManagerResponse.onError(4, "canceled");
            }
        }
        super.finish();
    }

    @Override
    public void onCreate(Bundle arraccountInfo) {
        Object object;
        super.onCreate((Bundle)arraccountInfo);
        this.mAccounts = this.getIntent().getParcelableArrayExtra("accounts");
        this.mAccountManagerResponse = (AccountManagerResponse)this.getIntent().getParcelableExtra("accountManagerResponse");
        if (this.mAccounts == null) {
            this.setResult(0);
            this.finish();
            return;
        }
        try {
            arraccountInfo = this.getActivityToken();
            this.mCallingUid = ActivityTaskManager.getService().getLaunchedFromUid((IBinder)arraccountInfo);
            this.mCallingPackage = ActivityTaskManager.getService().getLaunchedFromPackage((IBinder)arraccountInfo);
        }
        catch (RemoteException remoteException) {
            object = this.getClass().getSimpleName();
            arraccountInfo = new StringBuilder();
            arraccountInfo.append("Unable to get caller identity \n");
            arraccountInfo.append(remoteException);
            Log.w((String)object, arraccountInfo.toString());
        }
        if (UserHandle.isSameApp(this.mCallingUid, 1000) && this.getIntent().getStringExtra("androidPackageName") != null) {
            this.mCallingPackage = this.getIntent().getStringExtra("androidPackageName");
        }
        if (!UserHandle.isSameApp(this.mCallingUid, 1000) && this.getIntent().getStringExtra("androidPackageName") != null) {
            arraccountInfo = this.getClass().getSimpleName();
            object = new StringBuilder();
            ((StringBuilder)object).append("Non-system Uid: ");
            ((StringBuilder)object).append(this.mCallingUid);
            ((StringBuilder)object).append(" tried to override packageName \n");
            Log.w((String)arraccountInfo, ((StringBuilder)object).toString());
        }
        this.getAuthDescriptions();
        arraccountInfo = new AccountInfo[this.mAccounts.length];
        for (int i = 0; i < ((Parcelable[])(object = this.mAccounts)).length; ++i) {
            arraccountInfo[i] = new AccountInfo(((Account)object[i]).name, this.getDrawableForType(((Account)this.mAccounts[i]).type));
        }
        this.setContentView(17367116);
        object = (ListView)this.findViewById(16908298);
        ((ListView)object).setAdapter(new AccountArrayAdapter(this, 17367043, arraccountInfo));
        ((AbsListView)object).setChoiceMode(1);
        ((AbsListView)object).setTextFilterEnabled(true);
        ((AdapterView)object).setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int n, long l) {
                ChooseAccountActivity.this.onListItemClick((ListView)adapterView, view, n, l);
            }
        });
    }

    protected void onListItemClick(ListView object, View object2, int n, long l) {
        object = (Account)this.mAccounts[n];
        AccountManager accountManager = AccountManager.get(this);
        object2 = accountManager.getAccountVisibility((Account)object, this.mCallingPackage);
        if (object2 != null && (Integer)object2 == 4) {
            accountManager.setAccountVisibility((Account)object, this.mCallingPackage, 2);
        }
        object2 = new StringBuilder();
        ((StringBuilder)object2).append("selected account ");
        ((StringBuilder)object2).append(object);
        Log.d(TAG, ((StringBuilder)object2).toString());
        object2 = new Bundle();
        ((BaseBundle)object2).putString("authAccount", ((Account)object).name);
        ((BaseBundle)object2).putString("accountType", ((Account)object).type);
        this.mResult = object2;
        this.finish();
    }

    private static class AccountArrayAdapter
    extends ArrayAdapter<AccountInfo> {
        private AccountInfo[] mInfos;
        private LayoutInflater mLayoutInflater;

        public AccountArrayAdapter(Context context, int n, AccountInfo[] arraccountInfo) {
            super(context, n, arraccountInfo);
            this.mInfos = arraccountInfo;
            this.mLayoutInflater = (LayoutInflater)context.getSystemService("layout_inflater");
        }

        @Override
        public View getView(int n, View view, ViewGroup object) {
            if (view == null) {
                view = this.mLayoutInflater.inflate(17367117, null);
                object = new ViewHolder();
                ((ViewHolder)object).text = (TextView)view.findViewById(16908667);
                ((ViewHolder)object).icon = (ImageView)view.findViewById(16908666);
                view.setTag(object);
            } else {
                object = (ViewHolder)view.getTag();
            }
            ((ViewHolder)object).text.setText(this.mInfos[n].name);
            ((ViewHolder)object).icon.setImageDrawable(this.mInfos[n].drawable);
            return view;
        }
    }

    private static class AccountInfo {
        final Drawable drawable;
        final String name;

        AccountInfo(String string2, Drawable drawable2) {
            this.name = string2;
            this.drawable = drawable2;
        }
    }

    private static class ViewHolder {
        ImageView icon;
        TextView text;

        private ViewHolder() {
        }
    }

}

