/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.app;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Parcelable;
import android.os.UserHandle;
import android.os.UserManager;
import android.util.Log;

public class UnlaunchableAppActivity
extends Activity
implements DialogInterface.OnDismissListener,
DialogInterface.OnClickListener {
    private static final String EXTRA_UNLAUNCHABLE_REASON = "unlaunchable_reason";
    private static final String TAG = "UnlaunchableAppActivity";
    private static final int UNLAUNCHABLE_REASON_QUIET_MODE = 1;
    private int mReason;
    private IntentSender mTarget;
    private int mUserId;

    private static final Intent createBaseIntent() {
        Intent intent = new Intent();
        intent.setComponent(new ComponentName("android", UnlaunchableAppActivity.class.getName()));
        intent.setFlags(276824064);
        return intent;
    }

    public static Intent createInQuietModeDialogIntent(int n) {
        Intent intent = UnlaunchableAppActivity.createBaseIntent();
        intent.putExtra(EXTRA_UNLAUNCHABLE_REASON, 1);
        intent.putExtra("android.intent.extra.user_handle", n);
        return intent;
    }

    public static Intent createInQuietModeDialogIntent(int n, IntentSender intentSender) {
        Intent intent = UnlaunchableAppActivity.createInQuietModeDialogIntent(n);
        intent.putExtra("android.intent.extra.INTENT", intentSender);
        return intent;
    }

    @Override
    public void onClick(DialogInterface dialogInterface, int n) {
        if (this.mReason == 1 && n == -1) {
            UserManager.get(this).requestQuietModeEnabled(false, UserHandle.of(this.mUserId), this.mTarget);
        }
    }

    @Override
    protected void onCreate(Bundle object) {
        super.onCreate((Bundle)object);
        this.requestWindowFeature(1);
        object = this.getIntent();
        this.mReason = ((Intent)object).getIntExtra(EXTRA_UNLAUNCHABLE_REASON, -1);
        this.mUserId = ((Intent)object).getIntExtra("android.intent.extra.user_handle", -10000);
        this.mTarget = (IntentSender)((Intent)object).getParcelableExtra("android.intent.extra.INTENT");
        if (this.mUserId == -10000) {
            object = new StringBuilder();
            ((StringBuilder)object).append("Invalid user id: ");
            ((StringBuilder)object).append(this.mUserId);
            ((StringBuilder)object).append(". Stopping.");
            Log.wtf(TAG, ((StringBuilder)object).toString());
            this.finish();
            return;
        }
        if (this.mReason == 1) {
            object = this.getResources().getString(17041313);
            String string2 = this.getResources().getString(17041312);
            object = new AlertDialog.Builder(this).setTitle((CharSequence)object).setMessage(string2).setOnDismissListener(this);
            if (this.mReason == 1) {
                ((AlertDialog.Builder)object).setPositiveButton(17041314, (DialogInterface.OnClickListener)this).setNegativeButton(17039360, null);
            } else {
                ((AlertDialog.Builder)object).setPositiveButton(17039370, null);
            }
            ((AlertDialog.Builder)object).show();
            return;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Invalid unlaunchable type: ");
        ((StringBuilder)object).append(this.mReason);
        Log.wtf(TAG, ((StringBuilder)object).toString());
        this.finish();
    }

    @Override
    public void onDismiss(DialogInterface dialogInterface) {
        this.finish();
    }
}

