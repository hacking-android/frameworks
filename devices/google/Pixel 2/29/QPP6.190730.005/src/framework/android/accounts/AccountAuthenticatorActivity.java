/*
 * Decompiled with CFR 0.145.
 */
package android.accounts;

import android.accounts.AccountAuthenticatorResponse;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;

public class AccountAuthenticatorActivity
extends Activity {
    private AccountAuthenticatorResponse mAccountAuthenticatorResponse = null;
    private Bundle mResultBundle = null;

    @Override
    public void finish() {
        AccountAuthenticatorResponse accountAuthenticatorResponse = this.mAccountAuthenticatorResponse;
        if (accountAuthenticatorResponse != null) {
            Bundle bundle = this.mResultBundle;
            if (bundle != null) {
                accountAuthenticatorResponse.onResult(bundle);
            } else {
                accountAuthenticatorResponse.onError(4, "canceled");
            }
            this.mAccountAuthenticatorResponse = null;
        }
        super.finish();
    }

    @Override
    protected void onCreate(Bundle parcelable) {
        super.onCreate((Bundle)parcelable);
        this.mAccountAuthenticatorResponse = (AccountAuthenticatorResponse)this.getIntent().getParcelableExtra("accountAuthenticatorResponse");
        parcelable = this.mAccountAuthenticatorResponse;
        if (parcelable != null) {
            ((AccountAuthenticatorResponse)parcelable).onRequestContinued();
        }
    }

    public final void setAccountAuthenticatorResult(Bundle bundle) {
        this.mResultBundle = bundle;
    }
}

