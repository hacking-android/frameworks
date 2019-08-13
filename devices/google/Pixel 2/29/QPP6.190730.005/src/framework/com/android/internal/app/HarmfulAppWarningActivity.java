/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.app;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.ApplicationInfo;
import android.content.pm.IPackageDeleteObserver;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.android.internal.app.AlertActivity;
import com.android.internal.app.AlertController;
import com.android.internal.app.EventLogTags;

public class HarmfulAppWarningActivity
extends AlertActivity
implements DialogInterface.OnClickListener {
    private static final String EXTRA_HARMFUL_APP_WARNING = "harmful_app_warning";
    private static final String TAG = HarmfulAppWarningActivity.class.getSimpleName();
    private String mHarmfulAppWarning;
    private String mPackageName;
    private IntentSender mTarget;

    public static Intent createHarmfulAppWarningIntent(Context context, String string2, IntentSender intentSender, CharSequence charSequence) {
        Intent intent = new Intent();
        intent.setClass(context, HarmfulAppWarningActivity.class);
        intent.putExtra("android.intent.extra.PACKAGE_NAME", string2);
        intent.putExtra("android.intent.extra.INTENT", intentSender);
        intent.putExtra(EXTRA_HARMFUL_APP_WARNING, charSequence);
        return intent;
    }

    private View createView(ApplicationInfo applicationInfo) {
        View view = this.getLayoutInflater().inflate(17367161, null);
        ((TextView)view.findViewById(16908731)).setText(applicationInfo.loadSafeLabel(this.getPackageManager(), 500.0f, 5));
        ((TextView)view.findViewById(16908299)).setText(this.mHarmfulAppWarning);
        return view;
    }

    @Override
    public void onClick(DialogInterface object, int n) {
        if (n != -2) {
            if (n == -1) {
                this.getPackageManager().deletePackage(this.mPackageName, null, 0);
                EventLogTags.writeHarmfulAppWarningUninstall(this.mPackageName);
                this.finish();
            }
        } else {
            this.getPackageManager().setHarmfulAppWarning(this.mPackageName, null);
            object = (IntentSender)this.getIntent().getParcelableExtra("android.intent.extra.INTENT");
            try {
                this.startIntentSenderForResult((IntentSender)object, -1, null, 0, 0, 0);
            }
            catch (IntentSender.SendIntentException sendIntentException) {
                Log.e(TAG, "Error while starting intent sender", sendIntentException);
            }
            EventLogTags.writeHarmfulAppWarningLaunchAnyway(this.mPackageName);
            this.finish();
        }
    }

    @Override
    protected void onCreate(Bundle object) {
        Object object2;
        super.onCreate((Bundle)object);
        Intent intent = this.getIntent();
        this.mPackageName = intent.getStringExtra("android.intent.extra.PACKAGE_NAME");
        this.mTarget = (IntentSender)intent.getParcelableExtra("android.intent.extra.INTENT");
        this.mHarmfulAppWarning = intent.getStringExtra(EXTRA_HARMFUL_APP_WARNING);
        if (this.mPackageName == null || this.mTarget == null || this.mHarmfulAppWarning == null) {
            object = TAG;
            object2 = new StringBuilder();
            ((StringBuilder)object2).append("Invalid intent: ");
            ((StringBuilder)object2).append(intent.toString());
            Log.wtf((String)object, ((StringBuilder)object2).toString());
            this.finish();
        }
        try {
            object2 = this.getPackageManager().getApplicationInfo(this.mPackageName, 0);
            object = this.mAlertParams;
        }
        catch (PackageManager.NameNotFoundException nameNotFoundException) {
            Log.e(TAG, "Could not show warning because package does not exist ", nameNotFoundException);
            this.finish();
            return;
        }
        ((AlertController.AlertParams)object).mTitle = this.getString(17040082);
        ((AlertController.AlertParams)object).mView = this.createView((ApplicationInfo)object2);
        ((AlertController.AlertParams)object).mPositiveButtonText = this.getString(17040083);
        ((AlertController.AlertParams)object).mPositiveButtonListener = this;
        ((AlertController.AlertParams)object).mNegativeButtonText = this.getString(17040081);
        ((AlertController.AlertParams)object).mNegativeButtonListener = this;
        this.mAlert.installContent(this.mAlertParams);
    }
}

