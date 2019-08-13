/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.icu.util.ULocale
 *  com.google.android.textclassifier.AnnotatorModel
 *  com.google.android.textclassifier.AnnotatorModel$ClassificationResult
 */
package android.view.textclassifier;

import android.app.RemoteAction;
import android.content.Intent;
import android.icu.util.ULocale;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.textclassifier.EntityConfidence;
import android.view.textclassifier.TextClassification;
import android.view.textclassifier.TextLinks;
import com.android.internal.util.ArrayUtils;
import com.google.android.textclassifier.AnnotatorModel;
import java.util.ArrayList;
import java.util.List;

public final class ExtrasUtils {
    private static final String ACTIONS_INTENTS = "actions-intents";
    private static final String ACTION_INTENT = "action-intent";
    private static final String ENTITIES = "entities";
    private static final String ENTITIES_EXTRAS = "entities-extras";
    private static final String ENTITY_TYPE = "entity-type";
    private static final String FOREIGN_LANGUAGE = "foreign-language";
    private static final String IS_SERIALIZED_ENTITY_DATA_ENABLED = "is-serialized-entity-data-enabled";
    private static final String MODEL_NAME = "model-name";
    private static final String MODEL_VERSION = "model-version";
    private static final String SCORE = "score";
    private static final String SERIALIZED_ENTITIES_DATA = "serialized-entities-data";
    private static final String TEXT_LANGUAGES = "text-languages";

    private ExtrasUtils() {
    }

    static Bundle createForeignLanguageExtra(String charSequence, float f, int n) {
        Bundle bundle = new Bundle();
        bundle.putString(ENTITY_TYPE, (String)charSequence);
        bundle.putFloat(SCORE, f);
        bundle.putInt(MODEL_VERSION, n);
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append("langId_v");
        ((StringBuilder)charSequence).append(n);
        bundle.putString(MODEL_NAME, ((StringBuilder)charSequence).toString());
        return bundle;
    }

    public static RemoteAction findAction(TextClassification textClassification, String string2) {
        if (textClassification != null && string2 != null) {
            ArrayList<Intent> arrayList = ExtrasUtils.getActionsIntents(textClassification);
            if (arrayList != null) {
                int n = arrayList.size();
                for (int i = 0; i < n; ++i) {
                    Intent intent = arrayList.get(i);
                    if (intent == null || !string2.equals(intent.getAction())) continue;
                    return textClassification.getActions().get(i);
                }
            }
            return null;
        }
        return null;
    }

    public static RemoteAction findTranslateAction(TextClassification textClassification) {
        return ExtrasUtils.findAction(textClassification, "android.intent.action.TRANSLATE");
    }

    public static Intent getActionIntent(Bundle bundle) {
        return (Intent)bundle.getParcelable(ACTION_INTENT);
    }

    public static ArrayList<Intent> getActionsIntents(TextClassification textClassification) {
        if (textClassification == null) {
            return null;
        }
        return textClassification.getExtras().getParcelableArrayList(ACTIONS_INTENTS);
    }

    public static String getCopyText(Bundle bundle) {
        if ((bundle = (Bundle)bundle.getParcelable(ENTITIES_EXTRAS)) == null) {
            return null;
        }
        return bundle.getString("text");
    }

    public static List<Bundle> getEntities(Bundle bundle) {
        return bundle.getParcelableArrayList(ENTITIES);
    }

    public static String getEntityType(Bundle bundle) {
        if (bundle == null) {
            return null;
        }
        return bundle.getString(ENTITY_TYPE);
    }

    public static Bundle getForeignLanguageExtra(TextClassification textClassification) {
        if (textClassification == null) {
            return null;
        }
        return textClassification.getExtras().getBundle(FOREIGN_LANGUAGE);
    }

    public static String getModelName(Bundle bundle) {
        if (bundle == null) {
            return null;
        }
        return bundle.getString(MODEL_NAME);
    }

    public static float getScore(Bundle bundle) {
        if (bundle == null) {
            return -1.0f;
        }
        return bundle.getFloat(SCORE, -1.0f);
    }

    public static byte[] getSerializedEntityData(Bundle bundle) {
        return bundle.getByteArray(SERIALIZED_ENTITIES_DATA);
    }

    public static ULocale getTopLanguage(Intent parcelable) {
        if (parcelable == null) {
            return null;
        }
        if ((parcelable = ((Intent)parcelable).getBundleExtra("android.view.textclassifier.extra.FROM_TEXT_CLASSIFIER")) == null) {
            return null;
        }
        float[] arrf = ((Bundle)parcelable).getBundle(TEXT_LANGUAGES);
        if (arrf == null) {
            return null;
        }
        parcelable = arrf.getStringArray(ENTITY_TYPE);
        arrf = arrf.getFloatArray(SCORE);
        if (parcelable != null && arrf != null && ((Parcelable)parcelable).length != 0 && ((Parcelable)parcelable).length == arrf.length) {
            int n = 0;
            for (int i = 1; i < ((Parcelable)parcelable).length; ++i) {
                int n2 = n;
                if (arrf[n] < arrf[i]) {
                    n2 = i;
                }
                n = n2;
            }
            return ULocale.forLanguageTag((String)((Object)parcelable[n]));
        }
        return null;
    }

    public static boolean isSerializedEntityDataEnabled(TextLinks.Request request) {
        return request.getExtras().getBoolean(IS_SERIALIZED_ENTITY_DATA_ENABLED);
    }

    public static void putActionIntent(Bundle bundle, Intent intent) {
        bundle.putParcelable(ACTION_INTENT, intent);
    }

    static void putActionsIntents(Bundle bundle, ArrayList<Intent> arrayList) {
        bundle.putParcelableArrayList(ACTIONS_INTENTS, arrayList);
    }

    public static void putEntities(Bundle bundle, AnnotatorModel.ClassificationResult[] arrclassificationResult) {
        if (ArrayUtils.isEmpty(arrclassificationResult)) {
            return;
        }
        ArrayList<Bundle> arrayList = new ArrayList<Bundle>();
        for (AnnotatorModel.ClassificationResult classificationResult : arrclassificationResult) {
            if (classificationResult == null) continue;
            Bundle bundle2 = new Bundle();
            bundle2.putString(ENTITY_TYPE, classificationResult.getCollection());
            bundle2.putByteArray(SERIALIZED_ENTITIES_DATA, classificationResult.getSerializedEntityData());
            arrayList.add(bundle2);
        }
        if (!arrayList.isEmpty()) {
            bundle.putParcelableArrayList(ENTITIES, arrayList);
        }
    }

    public static void putEntitiesExtras(Bundle bundle, Bundle bundle2) {
        bundle.putParcelable(ENTITIES_EXTRAS, bundle2);
    }

    static void putForeignLanguageExtra(Bundle bundle, Bundle bundle2) {
        bundle.putParcelable(FOREIGN_LANGUAGE, bundle2);
    }

    public static void putIsSerializedEntityDataEnabled(Bundle bundle, boolean bl) {
        bundle.putBoolean(IS_SERIALIZED_ENTITY_DATA_ENABLED, bl);
    }

    public static void putSerializedEntityData(Bundle bundle, byte[] arrby) {
        bundle.putByteArray(SERIALIZED_ENTITIES_DATA, arrby);
    }

    public static void putTextLanguagesExtra(Bundle bundle, Bundle bundle2) {
        bundle.putBundle(TEXT_LANGUAGES, bundle2);
    }

    static void putTopLanguageScores(Bundle bundle, EntityConfidence entityConfidence) {
        int n = Math.min(3, entityConfidence.getEntities().size());
        String[] arrstring = entityConfidence.getEntities().subList(0, n).toArray(new String[0]);
        float[] arrf = new float[arrstring.length];
        for (n = 0; n < arrstring.length; ++n) {
            arrf[n] = entityConfidence.getConfidenceScore(arrstring[n]);
        }
        bundle.putStringArray(ENTITY_TYPE, arrstring);
        bundle.putFloatArray(SCORE, arrf);
    }
}

