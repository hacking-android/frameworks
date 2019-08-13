/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.app;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageItemInfo;
import android.content.pm.PackageManager;
import android.content.pm.UserInfo;
import android.os.Bundle;
import android.os.Parcelable;
import android.os.PersistableBundle;
import android.os.UserManager;
import android.util.Log;
import com.android.internal.app.AlertActivity;
import com.android.internal.app.AlertController;

public class ConfirmUserCreationActivity
extends AlertActivity
implements DialogInterface.OnClickListener {
    private static final String TAG = "CreateUser";
    private String mAccountName;
    private PersistableBundle mAccountOptions;
    private String mAccountType;
    private boolean mCanProceed;
    private UserManager mUserManager;
    private String mUserName;

    private String checkUserCreationRequirements() {
        Object object = this.getCallingPackage();
        if (object != null) {
            boolean bl;
            boolean bl2;
            block4 : {
                try {
                    object = this.getPackageManager().getApplicationInfo((String)object, 0);
                    boolean bl3 = this.mUserManager.hasUserRestriction("no_add_user") || !this.mUserManager.isAdminUser();
                    bl2 = this.mUserManager.canAddMoreUsers();
                    Account account = new Account(this.mAccountName, this.mAccountType);
                    bl = this.mAccountName != null && this.mAccountType != null && AccountManager.get(this).someUserHasAccount(account) | this.mUserManager.someUserHasSeedAccount(this.mAccountName, this.mAccountType);
                    this.mCanProceed = true;
                    object = ((PackageItemInfo)object).loadLabel(this.getPackageManager()).toString();
                    if (!bl3) break block4;
                }
                catch (PackageManager.NameNotFoundException nameNotFoundException) {
                    throw new SecurityException("Cannot find the calling package");
                }
                this.setResult(1);
                return null;
            }
            if (bl2 ^ true) {
                this.setResult(2);
                return null;
            }
            object = bl ? this.getString(17041180, object, this.mAccountName) : this.getString(17041181, object, this.mAccountName);
            return object;
        }
        throw new SecurityException("User Creation intent must be launched with startActivityForResult");
    }

    @Override
    public void onClick(DialogInterface object, int n) {
        this.setResult(0);
        if (n == -1 && this.mCanProceed) {
            Log.i(TAG, "Ok, creating user");
            object = this.mUserManager.createUser(this.mUserName, 0);
            if (object == null) {
                Log.e(TAG, "Couldn't create user");
                this.finish();
                return;
            }
            this.mUserManager.setSeedAccountData(((UserInfo)object).id, this.mAccountName, this.mAccountType, this.mAccountOptions);
            this.setResult(-1);
        }
        this.finish();
    }

    @Override
    public void onCreate(Bundle object) {
        super.onCreate((Bundle)object);
        object = this.getIntent();
        this.mUserName = ((Intent)object).getStringExtra("android.os.extra.USER_NAME");
        this.mAccountName = ((Intent)object).getStringExtra("android.os.extra.USER_ACCOUNT_NAME");
        this.mAccountType = ((Intent)object).getStringExtra("android.os.extra.USER_ACCOUNT_TYPE");
        this.mAccountOptions = (PersistableBundle)((Intent)object).getParcelableExtra("android.os.extra.USER_ACCOUNT_OPTIONS");
        this.mUserManager = this.getSystemService(UserManager.class);
        String string2 = this.checkUserCreationRequirements();
        if (string2 == null) {
            this.finish();
            return;
        }
        object = this.mAlertParams;
        ((AlertController.AlertParams)object).mMessage = string2;
        ((AlertController.AlertParams)object).mPositiveButtonText = this.getString(17039370);
        ((AlertController.AlertParams)object).mPositiveButtonListener = this;
        if (this.mCanProceed) {
            ((AlertController.AlertParams)object).mNegativeButtonText = this.getString(17039360);
            ((AlertController.AlertParams)object).mNegativeButtonListener = this;
        }
        this.setupAlert();
    }
}

