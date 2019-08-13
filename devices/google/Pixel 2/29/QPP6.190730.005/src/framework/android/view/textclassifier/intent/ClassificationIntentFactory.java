/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  com.google.android.textclassifier.AnnotatorModel
 *  com.google.android.textclassifier.AnnotatorModel$ClassificationResult
 */
package android.view.textclassifier.intent;

import android.content.Context;
import android.content.Intent;
import android.view.textclassifier.intent.LabeledIntent;
import com.google.android.textclassifier.AnnotatorModel;
import java.time.Instant;
import java.util.List;

public interface ClassificationIntentFactory {
    public static void insertTranslateAction(List<LabeledIntent> list, Context context, String string2) {
        list.add(new LabeledIntent(context.getString(17041147), null, context.getString(17041148), null, new Intent("android.intent.action.TRANSLATE").putExtra("android.intent.extra.TEXT", string2), string2.hashCode()));
    }

    public List<LabeledIntent> create(Context var1, String var2, boolean var3, Instant var4, AnnotatorModel.ClassificationResult var5);
}

