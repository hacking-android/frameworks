/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.view.textclassifier.intent.-$
 *  android.view.textclassifier.intent.-$$Lambda
 *  android.view.textclassifier.intent.-$$Lambda$LabeledIntent
 *  android.view.textclassifier.intent.-$$Lambda$LabeledIntent$LaL7EfxShgNu4lrdo3mv85g49Jg
 */
package android.view.textclassifier.intent;

import android.app.PendingIntent;
import android.app.RemoteAction;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Icon;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.TextUtils;
import android.view.textclassifier.ExtrasUtils;
import android.view.textclassifier.Log;
import android.view.textclassifier.TextClassification;
import android.view.textclassifier.intent.-$;
import android.view.textclassifier.intent._$$Lambda$LabeledIntent$LaL7EfxShgNu4lrdo3mv85g49Jg;
import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.util.Preconditions;

@VisibleForTesting(visibility=VisibleForTesting.Visibility.PACKAGE)
public final class LabeledIntent {
    public static final int DEFAULT_REQUEST_CODE = 0;
    private static final TitleChooser DEFAULT_TITLE_CHOOSER = _$$Lambda$LabeledIntent$LaL7EfxShgNu4lrdo3mv85g49Jg.INSTANCE;
    private static final String TAG = "LabeledIntent";
    public final String description;
    public final String descriptionWithAppName;
    public final Intent intent;
    public final int requestCode;
    public final String titleWithEntity;
    public final String titleWithoutEntity;

    public LabeledIntent(String string2, String string3, String string4, String string5, Intent intent, int n) {
        if (TextUtils.isEmpty(string3) && TextUtils.isEmpty(string2)) {
            throw new IllegalArgumentException("titleWithEntity and titleWithoutEntity should not be both null");
        }
        this.titleWithoutEntity = string2;
        this.titleWithEntity = string3;
        this.description = Preconditions.checkNotNull(string4);
        this.descriptionWithAppName = string5;
        this.intent = Preconditions.checkNotNull(intent);
        this.requestCode = n;
    }

    private String getApplicationName(ResolveInfo resolveInfo, PackageManager packageManager) {
        if (resolveInfo.activityInfo == null) {
            return null;
        }
        if ("android".equals(resolveInfo.activityInfo.packageName)) {
            return null;
        }
        if (resolveInfo.activityInfo.applicationInfo == null) {
            return null;
        }
        return (String)packageManager.getApplicationLabel(resolveInfo.activityInfo.applicationInfo);
    }

    private Bundle getFromTextClassifierExtra(Bundle bundle) {
        if (bundle != null) {
            Bundle bundle2 = new Bundle();
            ExtrasUtils.putTextLanguagesExtra(bundle2, bundle);
            return bundle2;
        }
        return Bundle.EMPTY;
    }

    static /* synthetic */ CharSequence lambda$static$0(LabeledIntent labeledIntent, ResolveInfo resolveInfo) {
        if (!TextUtils.isEmpty(labeledIntent.titleWithEntity)) {
            return labeledIntent.titleWithEntity;
        }
        return labeledIntent.titleWithoutEntity;
    }

    private String resolveDescription(ResolveInfo object, PackageManager packageManager) {
        if (!TextUtils.isEmpty(this.descriptionWithAppName) && !TextUtils.isEmpty((CharSequence)(object = this.getApplicationName((ResolveInfo)object, packageManager)))) {
            return String.format(this.descriptionWithAppName, object);
        }
        return this.description;
    }

    public Result resolve(Context object, TitleChooser object2, Bundle parcelable) {
        PackageManager packageManager = ((Context)object).getPackageManager();
        ResolveInfo resolveInfo = packageManager.resolveActivity(this.intent, 0);
        if (resolveInfo != null && resolveInfo.activityInfo != null) {
            String string2 = resolveInfo.activityInfo.packageName;
            String string3 = resolveInfo.activityInfo.name;
            if (string2 != null && string3 != null) {
                Intent intent = new Intent(this.intent);
                intent.putExtra("android.view.textclassifier.extra.FROM_TEXT_CLASSIFIER", this.getFromTextClassifierExtra((Bundle)parcelable));
                boolean bl = false;
                Parcelable parcelable2 = null;
                boolean bl2 = bl;
                parcelable = parcelable2;
                if (!"android".equals(string2)) {
                    intent.setComponent(new ComponentName(string2, string3));
                    bl2 = bl;
                    parcelable = parcelable2;
                    if (resolveInfo.activityInfo.getIconResource() != 0) {
                        parcelable = Icon.createWithResource(string2, resolveInfo.activityInfo.getIconResource());
                        bl2 = true;
                    }
                }
                parcelable2 = parcelable;
                if (parcelable == null) {
                    parcelable2 = Icon.createWithResource("android", 17302731);
                }
                parcelable = TextClassification.createPendingIntent((Context)object, intent, this.requestCode);
                object = object2 == null ? DEFAULT_TITLE_CHOOSER : object2;
                object = object2 = object.chooseTitle(this, resolveInfo);
                if (TextUtils.isEmpty((CharSequence)object2)) {
                    Log.w(TAG, "Custom titleChooser return null, fallback to the default titleChooser");
                    object = DEFAULT_TITLE_CHOOSER.chooseTitle(this, resolveInfo);
                }
                object = new RemoteAction((Icon)parcelable2, (CharSequence)object, this.resolveDescription(resolveInfo, packageManager), (PendingIntent)parcelable);
                ((RemoteAction)object).setShouldShowIcon(bl2);
                return new Result(intent, (RemoteAction)object);
            }
            Log.w(TAG, "packageName or className is null");
            return null;
        }
        Log.w(TAG, "resolveInfo or activityInfo is null");
        return null;
    }

    public static final class Result {
        public final RemoteAction remoteAction;
        public final Intent resolvedIntent;

        public Result(Intent intent, RemoteAction remoteAction) {
            this.resolvedIntent = Preconditions.checkNotNull(intent);
            this.remoteAction = Preconditions.checkNotNull(remoteAction);
        }
    }

    public static interface TitleChooser {
        public CharSequence chooseTitle(LabeledIntent var1, ResolveInfo var2);
    }

}

