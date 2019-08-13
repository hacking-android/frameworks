/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  com.google.android.textclassifier.NamedVariant
 *  com.google.android.textclassifier.RemoteActionTemplate
 */
package android.view.textclassifier.intent;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.textclassifier.Log;
import android.view.textclassifier.intent.LabeledIntent;
import com.android.internal.annotations.VisibleForTesting;
import com.google.android.textclassifier.NamedVariant;
import com.google.android.textclassifier.RemoteActionTemplate;
import java.util.ArrayList;
import java.util.List;

@VisibleForTesting(visibility=VisibleForTesting.Visibility.PACKAGE)
public final class TemplateIntentFactory {
    private static final String TAG = "androidtc";

    private static Intent createIntent(RemoteActionTemplate remoteActionTemplate) {
        Intent intent = new Intent(remoteActionTemplate.action);
        boolean bl = TextUtils.isEmpty(remoteActionTemplate.data);
        String[] arrstring = null;
        Object object = bl ? null : Uri.parse(remoteActionTemplate.data).normalizeScheme();
        if (!TextUtils.isEmpty(remoteActionTemplate.type)) {
            arrstring = Intent.normalizeMimeType(remoteActionTemplate.type);
        }
        intent.setDataAndType((Uri)object, (String)arrstring);
        object = remoteActionTemplate.flags;
        int n = 0;
        int n2 = object == null ? 0 : remoteActionTemplate.flags;
        intent.setFlags(n2);
        if (remoteActionTemplate.category != null) {
            arrstring = remoteActionTemplate.category;
            int n3 = arrstring.length;
            for (n2 = n; n2 < n3; ++n2) {
                object = arrstring[n2];
                if (object == null) continue;
                intent.addCategory((String)object);
            }
        }
        intent.putExtras(TemplateIntentFactory.nameVariantsToBundle(remoteActionTemplate.extras));
        return intent;
    }

    private static boolean isValidTemplate(RemoteActionTemplate remoteActionTemplate) {
        if (remoteActionTemplate == null) {
            Log.w(TAG, "Invalid RemoteActionTemplate: is null");
            return false;
        }
        if (TextUtils.isEmpty(remoteActionTemplate.titleWithEntity) && TextUtils.isEmpty(remoteActionTemplate.titleWithoutEntity)) {
            Log.w(TAG, "Invalid RemoteActionTemplate: title is null");
            return false;
        }
        if (TextUtils.isEmpty(remoteActionTemplate.description)) {
            Log.w(TAG, "Invalid RemoteActionTemplate: description is null");
            return false;
        }
        if (!TextUtils.isEmpty(remoteActionTemplate.packageName)) {
            Log.w(TAG, "Invalid RemoteActionTemplate: package name is set");
            return false;
        }
        if (TextUtils.isEmpty(remoteActionTemplate.action)) {
            Log.w(TAG, "Invalid RemoteActionTemplate: intent action not set");
            return false;
        }
        return true;
    }

    public static Bundle nameVariantsToBundle(NamedVariant[] arrnamedVariant) {
        if (arrnamedVariant == null) {
            return Bundle.EMPTY;
        }
        Bundle bundle = new Bundle();
        block8 : for (NamedVariant namedVariant : arrnamedVariant) {
            if (namedVariant == null) continue;
            switch (namedVariant.getType()) {
                default: {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Unsupported type found in nameVariantsToBundle : ");
                    stringBuilder.append(namedVariant.getType());
                    Log.w(TAG, stringBuilder.toString());
                    continue block8;
                }
                case 6: {
                    bundle.putString(namedVariant.getName(), namedVariant.getString());
                    continue block8;
                }
                case 5: {
                    bundle.putBoolean(namedVariant.getName(), namedVariant.getBool());
                    continue block8;
                }
                case 4: {
                    bundle.putDouble(namedVariant.getName(), namedVariant.getDouble());
                    continue block8;
                }
                case 3: {
                    bundle.putFloat(namedVariant.getName(), namedVariant.getFloat());
                    continue block8;
                }
                case 2: {
                    bundle.putLong(namedVariant.getName(), namedVariant.getLong());
                    continue block8;
                }
                case 1: {
                    bundle.putInt(namedVariant.getName(), namedVariant.getInt());
                }
            }
        }
        return bundle;
    }

    public List<LabeledIntent> create(RemoteActionTemplate[] arrremoteActionTemplate) {
        if (arrremoteActionTemplate.length == 0) {
            return new ArrayList<LabeledIntent>();
        }
        ArrayList<LabeledIntent> arrayList = new ArrayList<LabeledIntent>();
        for (RemoteActionTemplate remoteActionTemplate : arrremoteActionTemplate) {
            if (!TemplateIntentFactory.isValidTemplate(remoteActionTemplate)) {
                Log.w(TAG, "Invalid RemoteActionTemplate skipped.");
                continue;
            }
            String string2 = remoteActionTemplate.titleWithoutEntity;
            String string3 = remoteActionTemplate.titleWithEntity;
            String string4 = remoteActionTemplate.description;
            String string5 = remoteActionTemplate.descriptionWithAppName;
            Intent intent = TemplateIntentFactory.createIntent(remoteActionTemplate);
            int n = remoteActionTemplate.requestCode == null ? 0 : remoteActionTemplate.requestCode;
            arrayList.add(new LabeledIntent(string2, string3, string4, string5, intent, n));
        }
        return arrayList;
    }
}

