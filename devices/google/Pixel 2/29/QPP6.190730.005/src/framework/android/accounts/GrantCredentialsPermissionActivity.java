/*
 * Decompiled with CFR 0.145.
 */
package android.accounts;

import android.accounts.Account;
import android.accounts.AccountAuthenticatorResponse;
import android.accounts.AccountManager;
import android.accounts.AccountManagerCallback;
import android.accounts.AccountManagerFuture;
import android.accounts.AuthenticatorDescription;
import android.accounts.AuthenticatorException;
import android.accounts.OperationCanceledException;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.io.IOException;

public class GrantCredentialsPermissionActivity
extends Activity
implements View.OnClickListener {
    public static final String EXTRAS_ACCOUNT = "account";
    public static final String EXTRAS_AUTH_TOKEN_TYPE = "authTokenType";
    public static final String EXTRAS_REQUESTING_UID = "uid";
    public static final String EXTRAS_RESPONSE = "response";
    private Account mAccount;
    private String mAuthTokenType;
    protected LayoutInflater mInflater;
    private Bundle mResultBundle = null;
    private int mUid;

    private String getAccountLabel(Account account) {
        for (AuthenticatorDescription object : AccountManager.get(this).getAuthenticatorTypes()) {
            if (!object.type.equals(account.type)) continue;
            try {
                String notFoundException = this.createPackageContext(object.packageName, 0).getString(object.labelId);
                return notFoundException;
            }
            catch (Resources.NotFoundException nameNotFoundException) {
                return account.type;
            }
            catch (PackageManager.NameNotFoundException nameNotFoundException) {
                return account.type;
            }
        }
        return account.type;
    }

    private View newPackageView(String string2) {
        View view = this.mInflater.inflate(17367221, null);
        ((TextView)view.findViewById(16909211)).setText(string2);
        return view;
    }

    @Override
    public void finish() {
        AccountAuthenticatorResponse accountAuthenticatorResponse = (AccountAuthenticatorResponse)this.getIntent().getParcelableExtra(EXTRAS_RESPONSE);
        if (accountAuthenticatorResponse != null) {
            Bundle bundle = this.mResultBundle;
            if (bundle != null) {
                accountAuthenticatorResponse.onResult(bundle);
            } else {
                accountAuthenticatorResponse.onError(4, "canceled");
            }
        }
        super.finish();
    }

    @Override
    public void onClick(View object) {
        int n = ((View)object).getId();
        if (n != 16908718) {
            if (n == 16908877) {
                AccountManager.get(this).updateAppPermission(this.mAccount, this.mAuthTokenType, this.mUid, false);
                this.setResult(0);
            }
        } else {
            AccountManager.get(this).updateAppPermission(this.mAccount, this.mAuthTokenType, this.mUid, true);
            object = new Intent();
            ((Intent)object).putExtra("retry", true);
            this.setResult(-1, (Intent)object);
            this.setAccountAuthenticatorResult(((Intent)object).getExtras());
        }
        this.finish();
    }

    /*
     * WARNING - void declaration
     */
    @Override
    protected void onCreate(Bundle object2) {
        super.onCreate((Bundle)object2);
        this.setContentView(17367160);
        this.setTitle(17040074);
        this.mInflater = (LayoutInflater)this.getSystemService("layout_inflater");
        Bundle illegalArgumentException = this.getIntent().getExtras();
        if (illegalArgumentException == null) {
            this.setResult(0);
            this.finish();
            return;
        }
        this.mAccount = (Account)illegalArgumentException.getParcelable(EXTRAS_ACCOUNT);
        this.mAuthTokenType = illegalArgumentException.getString(EXTRAS_AUTH_TOKEN_TYPE);
        this.mUid = illegalArgumentException.getInt(EXTRAS_REQUESTING_UID);
        PackageManager packageManager = this.getPackageManager();
        String[] arrstring = packageManager.getPackagesForUid(this.mUid);
        Account account = this.mAccount;
        if (account != null && this.mAuthTokenType != null && arrstring != null) {
            String string2;
            try {
                string2 = this.getAccountLabel(account);
            }
            catch (IllegalArgumentException illegalArgumentException2) {
                this.setResult(0);
                this.finish();
                return;
            }
            final TextView textView = (TextView)this.findViewById(16908739);
            textView.setVisibility(8);
            AccountManagerCallback<String> accountManagerCallback = new AccountManagerCallback<String>(){

                @Override
                public void run(AccountManagerFuture<String> object) {
                    try {
                        final String string2 = object.getResult();
                        if (!TextUtils.isEmpty(string2)) {
                            object = GrantCredentialsPermissionActivity.this;
                            Runnable runnable = new Runnable(){

                                @Override
                                public void run() {
                                    if (!GrantCredentialsPermissionActivity.this.isFinishing()) {
                                        textView.setText(string2);
                                        textView.setVisibility(0);
                                    }
                                }
                            };
                            ((Activity)object).runOnUiThread(runnable);
                        }
                    }
                    catch (AuthenticatorException authenticatorException) {
                    }
                    catch (IOException iOException) {
                    }
                    catch (OperationCanceledException operationCanceledException) {
                        // empty catch block
                    }
                }

            };
            if (!"com.android.AccountManager.ACCOUNT_ACCESS_TOKEN_TYPE".equals(this.mAuthTokenType)) {
                AccountManager.get(this).getAuthTokenLabel(this.mAccount.type, this.mAuthTokenType, accountManagerCallback, null);
            }
            ((View)this.findViewById(16908718)).setOnClickListener(this);
            ((View)this.findViewById(16908877)).setOnClickListener(this);
            LinearLayout linearLayout = (LinearLayout)this.findViewById(16909212);
            for (String string3 : arrstring) {
                void var1_9;
                try {
                    String string4;
                    String string5 = string4 = packageManager.getApplicationLabel(packageManager.getApplicationInfo(string3, 0)).toString();
                }
                catch (PackageManager.NameNotFoundException nameNotFoundException) {
                    // empty catch block
                }
                linearLayout.addView(this.newPackageView((String)var1_9));
            }
            ((TextView)this.findViewById(16908665)).setText(this.mAccount.name);
            ((TextView)this.findViewById(16908668)).setText(string2);
            return;
        }
        this.setResult(0);
        this.finish();
    }

    public final void setAccountAuthenticatorResult(Bundle bundle) {
        this.mResultBundle = bundle;
    }

}

