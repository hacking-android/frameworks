/*
 * Decompiled with CFR 0.145.
 */
package android.accounts;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

public class CantAddAccountActivity
extends Activity {
    public static final String EXTRA_ERROR_CODE = "android.accounts.extra.ERROR_CODE";

    public void onCancelButtonClicked(View view) {
        this.onBackPressed();
    }

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.setContentView(17367094);
    }
}

