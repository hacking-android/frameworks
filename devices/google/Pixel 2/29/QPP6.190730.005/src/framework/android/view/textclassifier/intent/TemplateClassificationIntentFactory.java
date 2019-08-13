/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  com.google.android.textclassifier.AnnotatorModel
 *  com.google.android.textclassifier.AnnotatorModel$ClassificationResult
 *  com.google.android.textclassifier.RemoteActionTemplate
 */
package android.view.textclassifier.intent;

import android.content.Context;
import android.view.textclassifier.Log;
import android.view.textclassifier.intent.ClassificationIntentFactory;
import android.view.textclassifier.intent.LabeledIntent;
import android.view.textclassifier.intent.TemplateIntentFactory;
import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.util.Preconditions;
import com.google.android.textclassifier.AnnotatorModel;
import com.google.android.textclassifier.RemoteActionTemplate;
import java.time.Instant;
import java.util.Collections;
import java.util.List;

@VisibleForTesting(visibility=VisibleForTesting.Visibility.PACKAGE)
public final class TemplateClassificationIntentFactory
implements ClassificationIntentFactory {
    private static final String TAG = "androidtc";
    private final ClassificationIntentFactory mFallback;
    private final TemplateIntentFactory mTemplateIntentFactory;

    public TemplateClassificationIntentFactory(TemplateIntentFactory templateIntentFactory, ClassificationIntentFactory classificationIntentFactory) {
        this.mTemplateIntentFactory = Preconditions.checkNotNull(templateIntentFactory);
        this.mFallback = Preconditions.checkNotNull(classificationIntentFactory);
    }

    @Override
    public List<LabeledIntent> create(Context context, String string2, boolean bl, Instant object, AnnotatorModel.ClassificationResult classificationResult) {
        if (classificationResult == null) {
            return Collections.emptyList();
        }
        RemoteActionTemplate[] arrremoteActionTemplate = classificationResult.getRemoteActionTemplates();
        if (arrremoteActionTemplate == null) {
            Log.w(TAG, "RemoteActionTemplate is missing, fallback to LegacyClassificationIntentFactory.");
            return this.mFallback.create(context, string2, bl, (Instant)object, classificationResult);
        }
        object = this.mTemplateIntentFactory.create(arrremoteActionTemplate);
        if (bl) {
            ClassificationIntentFactory.insertTranslateAction((List<LabeledIntent>)object, context, string2.trim());
        }
        return object;
    }
}

