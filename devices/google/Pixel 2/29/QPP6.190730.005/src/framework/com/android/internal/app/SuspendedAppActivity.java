/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.app;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.pm.SuspendDialogInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.LocaleList;
import android.os.Parcelable;
import android.os.UserHandle;
import android.util.Slog;
import android.view.Window;
import com.android.internal.app.AlertActivity;
import com.android.internal.app.AlertController;
import java.util.Locale;

public class SuspendedAppActivity
extends AlertActivity
implements DialogInterface.OnClickListener {
    public static final String EXTRA_DIALOG_INFO = "com.android.internal.app.extra.DIALOG_INFO";
    public static final String EXTRA_SUSPENDED_PACKAGE = "com.android.internal.app.extra.SUSPENDED_PACKAGE";
    public static final String EXTRA_SUSPENDING_PACKAGE = "com.android.internal.app.extra.SUSPENDING_PACKAGE";
    private static final String PACKAGE_NAME = "com.android.internal.app";
    private static final String TAG = SuspendedAppActivity.class.getSimpleName();
    private Intent mMoreDetailsIntent;
    private PackageManager mPm;
    private SuspendDialogInfo mSuppliedDialogInfo;
    private Resources mSuspendingAppResources;
    private int mUserId;

    public static Intent createSuspendedAppInterceptIntent(String string2, String string3, SuspendDialogInfo suspendDialogInfo, int n) {
        return new Intent().setClassName("android", SuspendedAppActivity.class.getName()).putExtra(EXTRA_SUSPENDED_PACKAGE, string2).putExtra(EXTRA_DIALOG_INFO, suspendDialogInfo).putExtra(EXTRA_SUSPENDING_PACKAGE, string3).putExtra("android.intent.extra.USER_ID", n).setFlags(276824064);
    }

    private CharSequence getAppLabel(String string2) {
        try {
            CharSequence charSequence = this.mPm.getApplicationInfoAsUser(string2, 0, this.mUserId).loadLabel(this.mPm);
            return charSequence;
        }
        catch (PackageManager.NameNotFoundException nameNotFoundException) {
            String string3 = TAG;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Package ");
            stringBuilder.append(string2);
            stringBuilder.append(" not found");
            Slog.e(string3, stringBuilder.toString(), nameNotFoundException);
            return string2;
        }
    }

    private Intent getMoreDetailsActivity(String object, String string2, int n) {
        Intent intent = new Intent("android.intent.action.SHOW_SUSPENDED_APP_DETAILS").setPackage((String)object);
        object = this.mPm.resolveActivityAsUser(intent, 0, n);
        if (object != null && ((ResolveInfo)object).activityInfo != null && "android.permission.SEND_SHOW_SUSPENDED_APP_DETAILS".equals(object.activityInfo.permission)) {
            intent.putExtra("android.intent.extra.PACKAGE_NAME", string2).setFlags(335544320);
            return intent;
        }
        return null;
    }

    private String resolveDialogMessage(String string2, String charSequence) {
        charSequence = this.getAppLabel((String)charSequence);
        Object object = this.mSuppliedDialogInfo;
        if (object != null) {
            int n = ((SuspendDialogInfo)object).getDialogMessageResId();
            String string3 = this.mSuppliedDialogInfo.getDialogMessage();
            if (n != 0 && (object = this.mSuspendingAppResources) != null) {
                try {
                    object = ((Resources)object).getString(n, charSequence);
                    return object;
                }
                catch (Resources.NotFoundException notFoundException) {
                    string3 = TAG;
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Could not resolve string resource id ");
                    stringBuilder.append(n);
                    Slog.e(string3, stringBuilder.toString());
                }
            } else if (string3 != null) {
                return String.format(this.getResources().getConfiguration().getLocales().get(0), string3, charSequence);
            }
        }
        return this.getString(17039514, charSequence, this.getAppLabel(string2));
    }

    private Drawable resolveIcon() {
        Object object = this.mSuppliedDialogInfo;
        int n = object != null ? ((SuspendDialogInfo)object).getIconResId() : 0;
        if (n != 0 && (object = this.mSuspendingAppResources) != null) {
            try {
                object = ((Resources)object).getDrawable(n, this.getTheme());
                return object;
            }
            catch (Resources.NotFoundException notFoundException) {
                String string2 = TAG;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Could not resolve drawable resource id ");
                stringBuilder.append(n);
                Slog.e(string2, stringBuilder.toString());
            }
        }
        return null;
    }

    private String resolveNeutralButtonText() {
        Object object = this.mSuppliedDialogInfo;
        int n = object != null ? ((SuspendDialogInfo)object).getNeutralButtonTextResId() : 0;
        if (n != 0 && (object = this.mSuspendingAppResources) != null) {
            try {
                object = ((Resources)object).getString(n);
                return object;
            }
            catch (Resources.NotFoundException notFoundException) {
                String string2 = TAG;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Could not resolve string resource id ");
                stringBuilder.append(n);
                Slog.e(string2, stringBuilder.toString());
            }
        }
        return this.getString(17039515);
    }

    private String resolveTitle() {
        Object object = this.mSuppliedDialogInfo;
        int n = object != null ? ((SuspendDialogInfo)object).getTitleResId() : 0;
        if (n != 0 && (object = this.mSuspendingAppResources) != null) {
            try {
                object = ((Resources)object).getString(n);
                return object;
            }
            catch (Resources.NotFoundException notFoundException) {
                String string2 = TAG;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Could not resolve string resource id ");
                stringBuilder.append(n);
                Slog.e(string2, stringBuilder.toString());
            }
        }
        return this.getString(17039516);
    }

    @Override
    public void onClick(DialogInterface dialogInterface, int n) {
        if (n == -3) {
            this.startActivityAsUser(this.mMoreDetailsIntent, UserHandle.of(this.mUserId));
            Slog.i(TAG, "Started more details activity");
        }
        this.finish();
    }

    @Override
    public void onCreate(Bundle object) {
        super.onCreate((Bundle)object);
        this.mPm = this.getPackageManager();
        this.getWindow().setType(2008);
        Object object2 = this.getIntent();
        this.mUserId = ((Intent)object2).getIntExtra("android.intent.extra.USER_ID", -1);
        if (this.mUserId < 0) {
            object = TAG;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Invalid user: ");
            stringBuilder.append(this.mUserId);
            Slog.wtf((String)object, stringBuilder.toString());
            this.finish();
            return;
        }
        object = ((Intent)object2).getStringExtra(EXTRA_SUSPENDED_PACKAGE);
        String string2 = ((Intent)object2).getStringExtra(EXTRA_SUSPENDING_PACKAGE);
        this.mSuppliedDialogInfo = (SuspendDialogInfo)((Intent)object2).getParcelableExtra(EXTRA_DIALOG_INFO);
        if (this.mSuppliedDialogInfo != null) {
            try {
                this.mSuspendingAppResources = this.mPm.getResourcesForApplicationAsUser(string2, this.mUserId);
            }
            catch (PackageManager.NameNotFoundException nameNotFoundException) {
                String string3 = TAG;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Could not find resources for ");
                stringBuilder.append(string2);
                Slog.e(string3, stringBuilder.toString(), nameNotFoundException);
            }
        }
        object2 = this.mAlertParams;
        ((AlertController.AlertParams)object2).mIcon = this.resolveIcon();
        ((AlertController.AlertParams)object2).mTitle = this.resolveTitle();
        ((AlertController.AlertParams)object2).mMessage = this.resolveDialogMessage(string2, (String)object);
        ((AlertController.AlertParams)object2).mPositiveButtonText = this.getString(17039370);
        this.mMoreDetailsIntent = this.getMoreDetailsActivity(string2, (String)object, this.mUserId);
        if (this.mMoreDetailsIntent != null) {
            ((AlertController.AlertParams)object2).mNeutralButtonText = this.resolveNeutralButtonText();
        }
        ((AlertController.AlertParams)object2).mNeutralButtonListener = this;
        ((AlertController.AlertParams)object2).mPositiveButtonListener = this;
        this.setupAlert();
    }
}

