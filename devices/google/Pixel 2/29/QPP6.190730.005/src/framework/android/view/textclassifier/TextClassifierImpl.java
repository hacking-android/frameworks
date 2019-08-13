/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.icu.util.ULocale
 *  android.view.textclassifier.-$
 *  android.view.textclassifier.-$$Lambda
 *  android.view.textclassifier.-$$Lambda$0biFK4yZBmWN1EO2wtnXskzuEcE
 *  android.view.textclassifier.-$$Lambda$9N8WImc0VBjy2oxI_Gk5_Pbye_A
 *  android.view.textclassifier.-$$Lambda$NxwbyZSxofZ4Z5SQhfXmtLQ1nxk
 *  android.view.textclassifier.-$$Lambda$TextClassifierImpl
 *  android.view.textclassifier.-$$Lambda$TextClassifierImpl$RRbXefHgcUymI9-P95ArUyMvfbw
 *  android.view.textclassifier.-$$Lambda$TextClassifierImpl$naj1VfHYH1Qfut8yLHu8DlsggQE
 *  android.view.textclassifier.-$$Lambda$XeE_KI7QgMKzF9vYRSoFWAolyuA
 *  android.view.textclassifier.-$$Lambda$jJq8RXuVdjYF3lPq-77PEw1NJLM
 *  com.google.android.textclassifier.ActionsSuggestionsModel
 *  com.google.android.textclassifier.ActionsSuggestionsModel$ActionSuggestion
 *  com.google.android.textclassifier.ActionsSuggestionsModel$ActionSuggestionOptions
 *  com.google.android.textclassifier.ActionsSuggestionsModel$Conversation
 *  com.google.android.textclassifier.ActionsSuggestionsModel$ConversationMessage
 *  com.google.android.textclassifier.AnnotatorModel
 *  com.google.android.textclassifier.AnnotatorModel$AnnotatedSpan
 *  com.google.android.textclassifier.AnnotatorModel$AnnotationOptions
 *  com.google.android.textclassifier.AnnotatorModel$AnnotationUsecase
 *  com.google.android.textclassifier.AnnotatorModel$ClassificationOptions
 *  com.google.android.textclassifier.AnnotatorModel$ClassificationResult
 *  com.google.android.textclassifier.AnnotatorModel$SelectionOptions
 *  com.google.android.textclassifier.LangIdModel
 *  com.google.android.textclassifier.LangIdModel$LanguageResult
 *  com.google.android.textclassifier.NamedVariant
 */
package android.view.textclassifier;

import android.app.RemoteAction;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.icu.util.ULocale;
import android.os.Bundle;
import android.os.LocaleList;
import android.os.ParcelFileDescriptor;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.Pair;
import android.view.View;
import android.view.textclassifier.-$;
import android.view.textclassifier.ActionsModelParamsSupplier;
import android.view.textclassifier.ActionsSuggestionsHelper;
import android.view.textclassifier.ConversationAction;
import android.view.textclassifier.ConversationActions;
import android.view.textclassifier.EntityConfidence;
import android.view.textclassifier.ExtrasUtils;
import android.view.textclassifier.GenerateLinksLogger;
import android.view.textclassifier.Log;
import android.view.textclassifier.ModelFileManager;
import android.view.textclassifier.SelectionEvent;
import android.view.textclassifier.SelectionSessionLogger;
import android.view.textclassifier.TextClassification;
import android.view.textclassifier.TextClassificationConstants;
import android.view.textclassifier.TextClassifier;
import android.view.textclassifier.TextClassifierEvent;
import android.view.textclassifier.TextClassifierEventTronLogger;
import android.view.textclassifier.TextLanguage;
import android.view.textclassifier.TextLinks;
import android.view.textclassifier.TextSelection;
import android.view.textclassifier._$$Lambda$0biFK4yZBmWN1EO2wtnXskzuEcE;
import android.view.textclassifier._$$Lambda$9N8WImc0VBjy2oxI_Gk5_Pbye_A;
import android.view.textclassifier._$$Lambda$NxwbyZSxofZ4Z5SQhfXmtLQ1nxk;
import android.view.textclassifier._$$Lambda$TextClassifierImpl$RRbXefHgcUymI9_P95ArUyMvfbw;
import android.view.textclassifier._$$Lambda$TextClassifierImpl$ftq_sQqJYwUdrdbbr9jz3p4AWos;
import android.view.textclassifier._$$Lambda$TextClassifierImpl$iSt_Guet_O6Vtdk0MA4z_Z4lzaM;
import android.view.textclassifier._$$Lambda$TextClassifierImpl$naj1VfHYH1Qfut8yLHu8DlsggQE;
import android.view.textclassifier._$$Lambda$XeE_KI7QgMKzF9vYRSoFWAolyuA;
import android.view.textclassifier._$$Lambda$jJq8RXuVdjYF3lPq_77PEw1NJLM;
import android.view.textclassifier.intent.ClassificationIntentFactory;
import android.view.textclassifier.intent.LabeledIntent;
import android.view.textclassifier.intent.LegacyClassificationIntentFactory;
import android.view.textclassifier.intent.TemplateClassificationIntentFactory;
import android.view.textclassifier.intent.TemplateIntentFactory;
import com.android.internal.annotations.GuardedBy;
import com.android.internal.util.IndentingPrintWriter;
import com.android.internal.util.Preconditions;
import com.google.android.textclassifier.ActionsSuggestionsModel;
import com.google.android.textclassifier.AnnotatorModel;
import com.google.android.textclassifier.LangIdModel;
import com.google.android.textclassifier.NamedVariant;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Supplier;

/*
 * Illegal identifiers - consider using --renameillegalidents true
 */
public final class TextClassifierImpl
implements TextClassifier {
    private static final String ACTIONS_FACTORY_MODEL_FILENAME_REGEX = "actions_suggestions\\.(.*)\\.model";
    private static final String ANNOTATOR_FACTORY_MODEL_FILENAME_REGEX = "textclassifier\\.(.*)\\.model";
    private static final File ANNOTATOR_UPDATED_MODEL_FILE;
    private static final boolean DEBUG = false;
    private static final File FACTORY_MODEL_DIR;
    private static final String LANG_ID_FACTORY_MODEL_FILENAME_REGEX = "lang_id.model";
    private static final String LOG_TAG = "androidtc";
    private static final File UPDATED_ACTIONS_MODEL;
    private static final File UPDATED_LANG_ID_MODEL_FILE;
    @GuardedBy(value={"mLock"})
    private ModelFileManager.ModelFile mActionModelInUse;
    @GuardedBy(value={"mLock"})
    private ActionsSuggestionsModel mActionsImpl;
    private final ModelFileManager mActionsModelFileManager;
    private final Supplier<ActionsModelParamsSupplier.ActionsModelParams> mActionsModelParamsSupplier;
    @GuardedBy(value={"mLock"})
    private AnnotatorModel mAnnotatorImpl;
    private final ModelFileManager mAnnotatorModelFileManager;
    @GuardedBy(value={"mLock"})
    private ModelFileManager.ModelFile mAnnotatorModelInUse;
    private final ClassificationIntentFactory mClassificationIntentFactory;
    private final Context mContext;
    private final TextClassifier mFallback;
    private final GenerateLinksLogger mGenerateLinksLogger;
    @GuardedBy(value={"mLock"})
    private LangIdModel mLangIdImpl;
    private final ModelFileManager mLangIdModelFileManager;
    @GuardedBy(value={"mLock"})
    private ModelFileManager.ModelFile mLangIdModelInUse;
    private final Object mLock = new Object();
    private final SelectionSessionLogger mSessionLogger = new SelectionSessionLogger();
    private final TextClassificationConstants mSettings;
    private final TemplateIntentFactory mTemplateIntentFactory;
    private final TextClassifierEventTronLogger mTextClassifierEventTronLogger = new TextClassifierEventTronLogger();

    static {
        FACTORY_MODEL_DIR = new File("/etc/textclassifier/");
        ANNOTATOR_UPDATED_MODEL_FILE = new File("/data/misc/textclassifier/textclassifier.model");
        UPDATED_LANG_ID_MODEL_FILE = new File("/data/misc/textclassifier/lang_id.model");
        UPDATED_ACTIONS_MODEL = new File("/data/misc/textclassifier/actions_suggestions.model");
    }

    public TextClassifierImpl(Context context, TextClassificationConstants textClassificationConstants) {
        this(context, textClassificationConstants, TextClassifier.NO_OP);
    }

    public TextClassifierImpl(Context object, TextClassificationConstants textClassificationConstants, TextClassifier textClassifier) {
        this.mContext = Preconditions.checkNotNull(object);
        this.mFallback = Preconditions.checkNotNull(textClassifier);
        this.mSettings = Preconditions.checkNotNull(textClassificationConstants);
        this.mGenerateLinksLogger = new GenerateLinksLogger(this.mSettings.getGenerateLinksLogSampleRate());
        this.mAnnotatorModelFileManager = new ModelFileManager(new ModelFileManager.ModelFileSupplierImpl(FACTORY_MODEL_DIR, ANNOTATOR_FACTORY_MODEL_FILENAME_REGEX, ANNOTATOR_UPDATED_MODEL_FILE, (Function<Integer, Integer>)_$$Lambda$jJq8RXuVdjYF3lPq_77PEw1NJLM.INSTANCE, (Function<Integer, String>)_$$Lambda$NxwbyZSxofZ4Z5SQhfXmtLQ1nxk.INSTANCE));
        this.mLangIdModelFileManager = new ModelFileManager(new ModelFileManager.ModelFileSupplierImpl(FACTORY_MODEL_DIR, LANG_ID_FACTORY_MODEL_FILENAME_REGEX, UPDATED_LANG_ID_MODEL_FILE, (Function<Integer, Integer>)_$$Lambda$0biFK4yZBmWN1EO2wtnXskzuEcE.INSTANCE, (Function<Integer, String>)_$$Lambda$TextClassifierImpl$RRbXefHgcUymI9_P95ArUyMvfbw.INSTANCE));
        this.mActionsModelFileManager = new ModelFileManager(new ModelFileManager.ModelFileSupplierImpl(FACTORY_MODEL_DIR, ACTIONS_FACTORY_MODEL_FILENAME_REGEX, UPDATED_ACTIONS_MODEL, (Function<Integer, Integer>)_$$Lambda$9N8WImc0VBjy2oxI_Gk5_Pbye_A.INSTANCE, (Function<Integer, String>)_$$Lambda$XeE_KI7QgMKzF9vYRSoFWAolyuA.INSTANCE));
        this.mTemplateIntentFactory = new TemplateIntentFactory();
        object = this.mSettings.isTemplateIntentFactoryEnabled() ? new TemplateClassificationIntentFactory(this.mTemplateIntentFactory, new LegacyClassificationIntentFactory()) : new LegacyClassificationIntentFactory();
        this.mClassificationIntentFactory = object;
        this.mActionsModelParamsSupplier = new ActionsModelParamsSupplier(this.mContext, new _$$Lambda$TextClassifierImpl$iSt_Guet_O6Vtdk0MA4z_Z4lzaM(this));
    }

    private static String concatenateLocales(LocaleList object) {
        object = object == null ? "" : ((LocaleList)object).toLanguageTags();
        return object;
    }

    private TextClassification createClassificationResult(AnnotatorModel.ClassificationResult[] object, String string2, int n, int n2, Instant object2) {
        int n3;
        Object object3 = string2.substring(n, n2);
        TextClassification.Builder builder = new TextClassification.Builder().setText((String)object3);
        int n4 = ((AnnotatorModel.ClassificationResult[])object).length;
        boolean bl = false;
        Object object4 = n4 > 0 ? object[0] : null;
        Object object5 = object4;
        for (n3 = 0; n3 < n4; ++n3) {
            builder.setEntityType(object[n3]);
            object4 = object5;
            if (object[n3].getScore() > object5.getScore()) {
                object4 = object[n3];
            }
            object5 = object4;
        }
        object = this.generateLanguageBundles(string2, n, n2);
        object4 = (Bundle)((Pair)object).first;
        Object object6 = (Bundle)((Pair)object).second;
        builder.setForeignLanguageExtra((Bundle)object6);
        n3 = 1;
        object = this.mClassificationIntentFactory;
        Object object7 = this.mContext;
        if (object6 != null) {
            bl = true;
        }
        object2 = object.create((Context)object7, (String)object3, bl, (Instant)object2, (AnnotatorModel.ClassificationResult)object5);
        object = _$$Lambda$TextClassifierImpl$naj1VfHYH1Qfut8yLHu8DlsggQE.INSTANCE;
        object5 = object2.iterator();
        while (object5.hasNext()) {
            object3 = (LabeledIntent)object5.next();
            object6 = ((LabeledIntent)object3).resolve(this.mContext, (LabeledIntent.TitleChooser)object, (Bundle)object4);
            if (object6 == null) continue;
            object7 = ((LabeledIntent.Result)object6).resolvedIntent;
            object6 = ((LabeledIntent.Result)object6).remoteAction;
            if (n3 != 0) {
                builder.setIcon(((RemoteAction)object6).getIcon().loadDrawable(this.mContext));
                builder.setLabel(((RemoteAction)object6).getTitle().toString());
                builder.setIntent((Intent)object7);
                builder.setOnClickListener(TextClassification.createIntentOnClickListener(TextClassification.createPendingIntent(this.mContext, (Intent)object7, ((LabeledIntent)object3).requestCode)));
                n3 = 0;
            }
            builder.addAction((RemoteAction)object6, (Intent)object7);
        }
        return builder.setId(this.createId(string2, n, n2)).build();
    }

    /*
     * WARNING - void declaration
     */
    private ConversationActions createConversationActionResult(ConversationActions.Request request, ActionsSuggestionsModel.ActionSuggestion[] object) {
        void var2_6;
        Object object2;
        Collection<String> collection = this.resolveActionTypesFromRequest(request);
        ArrayList<ConversationAction> arrayList = new ArrayList<ConversationAction>();
        for (Object object3 : object) {
            String string2 = object3.getActionType();
            if (!collection.contains(string2)) continue;
            LabeledIntent.Result result = ActionsSuggestionsHelper.createLabeledIntentResult(this.mContext, this.mTemplateIntentFactory, (ActionsSuggestionsModel.ActionSuggestion)object3);
            object2 = null;
            Bundle bundle = new Bundle();
            if (result != null) {
                object2 = result.remoteAction;
                ExtrasUtils.putActionIntent(bundle, result.resolvedIntent);
            }
            ExtrasUtils.putSerializedEntityData(bundle, object3.getSerializedEntityData());
            ExtrasUtils.putEntitiesExtras(bundle, TemplateIntentFactory.nameVariantsToBundle(object3.getEntityData()));
            arrayList.add(new ConversationAction.Builder(string2).setConfidenceScore(object3.getScore()).setTextReply(object3.getResponseText()).setAction((RemoteAction)object2).setExtras(bundle).build());
        }
        object2 = ActionsSuggestionsHelper.removeActionsWithDuplicates(arrayList);
        List<ConversationAction> list = object2;
        if (request.getMaxSuggestions() >= 0) {
            List<ConversationAction> list2 = object2;
            if (object2.size() > request.getMaxSuggestions()) {
                List<ConversationAction> list3 = object2.subList(0, request.getMaxSuggestions());
            }
        }
        return new ConversationActions((List<ConversationAction>)var2_6, ActionsSuggestionsHelper.createResultId(this.mContext, request.getConversation(), this.mActionModelInUse.getVersion(), this.mActionModelInUse.getSupportedLocales()));
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private String createId(String string2, int n, int n2) {
        Object object = this.mLock;
        synchronized (object) {
            return SelectionSessionLogger.createId(string2, n, n2, this.mContext, this.mAnnotatorModelInUse.getVersion(), this.mAnnotatorModelInUse.getSupportedLocales());
        }
    }

    private String detectLanguageTagsFromText(CharSequence object) {
        if (!this.mSettings.isDetectLanguagesFromTextEnabled()) {
            return null;
        }
        float f = this.getLangIdThreshold();
        if (!(f < 0.0f) && !(f > 1.0f)) {
            ULocale uLocale;
            TextLanguage textLanguage = this.detectLanguage(new TextLanguage.Request.Builder((CharSequence)object).build());
            int n = textLanguage.getLocaleHypothesisCount();
            object = new ArrayList();
            for (int i = 0; i < n && !(textLanguage.getConfidenceScore(uLocale = textLanguage.getLocale(i)) < f); ++i) {
                object.add(uLocale.toLanguageTag());
            }
            if (object.isEmpty()) {
                return null;
            }
            return String.join((CharSequence)",", (Iterable<? extends CharSequence>)object);
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("[detectLanguageTagsFromText] unexpected threshold is found: ");
        ((StringBuilder)object).append(f);
        Log.w(LOG_TAG, ((StringBuilder)object).toString());
        return null;
    }

    private EntityConfidence detectLanguages(String object) throws FileNotFoundException {
        LangIdModel.LanguageResult[] arrlanguageResult = this.getLangIdImpl().detectLanguages((String)object);
        object = new ArrayMap();
        for (LangIdModel.LanguageResult languageResult : arrlanguageResult) {
            object.put(languageResult.getLanguage(), Float.valueOf(languageResult.getScore()));
        }
        return new EntityConfidence((Map<String, Float>)object);
    }

    private EntityConfidence detectLanguages(String object, int n, int n2) throws FileNotFoundException {
        boolean bl = n >= 0;
        Preconditions.checkArgument(bl);
        bl = n2 <= ((String)object).length();
        Preconditions.checkArgument(bl);
        bl = n <= n2;
        Preconditions.checkArgument(bl);
        Object object2 = this.mSettings.getLangIdContextSettings();
        int n3 = (int)object2[0];
        float f = object2[1];
        float f2 = object2[2];
        float f3 = 1.0f - f2;
        Log.v(LOG_TAG, String.format(Locale.US, "LangIdContextSettings: minimumTextSize=%d, penalizeRatio=%.2f, subjectTextScoreRatio=%.2f, moreTextScoreRatio=%.2f", n3, Float.valueOf(f), Float.valueOf(f2), Float.valueOf(f3)));
        if (n2 - n < n3 && f <= 0.0f) {
            return new EntityConfidence(Collections.<String, Float>emptyMap());
        }
        Object object3 = ((String)object).substring(n, n2);
        object2 = this.detectLanguages((String)object3);
        if (((String)object3).length() < n3 && ((String)object3).length() != ((String)object).length() && !(f2 * f >= 1.0f)) {
            object = f3 >= 0.0f ? this.detectLanguages(TextClassifier.Utils.getSubString((String)object, n, n2, n3)) : new EntityConfidence(Collections.<String, Float>emptyMap());
            object3 = new ArrayMap();
            Object object4 = new ArraySet<String>();
            object4.addAll(((EntityConfidence)object2).getEntities());
            object4.addAll(((EntityConfidence)object).getEntities());
            Iterator iterator = object4.iterator();
            while (iterator.hasNext()) {
                object4 = (String)iterator.next();
                object3.put(object4, Float.valueOf((((EntityConfidence)object2).getConfidenceScore((String)object4) * f2 + ((EntityConfidence)object).getConfidenceScore((String)object4) * f3) * f));
            }
            return new EntityConfidence((Map<String, Float>)object3);
        }
        return object2;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive exception aggregation
     */
    private Pair<Bundle, Bundle> generateLanguageBundles(String var1_1, int var2_3, int var3_4) {
        if (!this.mSettings.isTranslateInClassificationEnabled()) {
            return null;
        }
        var4_5 = this.getLangIdThreshold();
        if (var4_5 < 0.0f || var4_5 > 1.0f) ** GOTO lbl26
        var5_6 = this.detectLanguages((String)var1_1, var2_3, var3_4);
        if (var5_6.getEntities().isEmpty()) {
            return Pair.create(null, null);
        }
        var1_1 = new Pair<Object, Object>();
        ExtrasUtils.putTopLanguageScores(var1_1, (EntityConfidence)var5_6);
        var6_7 = var5_6.getEntities().get(0);
        var7_8 = var5_6.getConfidenceScore((String)var6_7);
        if (!(var7_8 < var4_5)) ** GOTO lbl17
        return Pair.create(var1_1, null);
lbl17: // 1 sources:
        Log.v("androidtc", String.format(Locale.US, "Language detected: <%s:%.2f>", new Object[]{var6_7, Float.valueOf(var7_8)}));
        var5_6 = new Locale((String)var6_7);
        var6_7 = LocaleList.getDefault();
        var3_4 = var6_7.size();
        for (var2_3 = 0; var2_3 < var3_4; ++var2_3) {
            if (!var6_7.get(var2_3).getLanguage().equals(var5_6.getLanguage())) continue;
            return Pair.create(var1_1, null);
        }
        try {
            return Pair.create(var1_1, ExtrasUtils.createForeignLanguageExtra(var5_6.getLanguage(), var7_8, this.getLangIdImpl().getVersion()));
lbl26: // 1 sources:
            var1_1 = new Pair<Object, Object>();
            var1_1.append("[detectForeignLanguage] unexpected threshold is found: ");
            var1_1.append(var4_5);
            Log.w("androidtc", var1_1.toString());
            var1_1 = Pair.create(null, null);
            return var1_1;
        }
        catch (Throwable var1_2) {
            Log.e("androidtc", "Error generating language bundles.", var1_2);
            return Pair.create(null, null);
        }
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    private ActionsSuggestionsModel getActionsImpl() throws FileNotFoundException {
        block9 : {
            var1_1 = this.mLock;
            // MONITORENTER : var1_1
            var2_2 = this.mActionsModelFileManager.findBestModelFile(LocaleList.getDefault());
            if (var2_2 == null) {
                // MONITOREXIT : var1_1
                return null;
            }
            if (this.mActionsImpl != null && Objects.equals(this.mActionModelInUse, var2_2)) break block9;
            var3_4 = new StringBuilder();
            var3_4.append("Loading ");
            var3_4.append(var2_2);
            Log.d("androidtc", var3_4.toString());
            var3_4 = new File(var2_2.getPath());
            var3_4 = ParcelFileDescriptor.open((File)var3_4, 268435456);
            if (var3_4 != null) ** GOTO lbl25
            var4_5 = new StringBuilder();
            var4_5.append("Failed to read the model file: ");
            var4_5.append(var2_2.getPath());
            Log.d("androidtc", var4_5.toString());
            return null;
lbl25: // 1 sources:
            var5_7 = this.mActionsModelParamsSupplier.get();
            this.mActionsImpl = var4_6 = new ActionsSuggestionsModel(var3_4.getFd(), var5_7.getSerializedPreconditions(var2_2));
            this.mActionModelInUse = var2_2;
            TextClassifierImpl.maybeCloseAndLogError((ParcelFileDescriptor)var3_4);
        }
        var2_2 = this.mActionsImpl;
        // MONITOREXIT : var1_1
        return var2_2;
        finally {
            TextClassifierImpl.maybeCloseAndLogError((ParcelFileDescriptor)var3_4);
        }
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    private AnnotatorModel getAnnotatorImpl(LocaleList var1_1) throws FileNotFoundException {
        block6 : {
            block7 : {
                block8 : {
                    var2_3 = this.mLock;
                    // MONITORENTER : var2_3
                    if (var1_1 != null) ** GOTO lbl6
                    var1_1 = LocaleList.getDefault();
lbl6: // 2 sources:
                    if ((var3_4 = this.mAnnotatorModelFileManager.findBestModelFile((LocaleList)var1_1)) == null) break block6;
                    if (this.mAnnotatorImpl != null && Objects.equals(this.mAnnotatorModelInUse, var3_4)) break block7;
                    var1_1 = new StringBuilder();
                    var1_1.append("Loading ");
                    var1_1.append(var3_4);
                    Log.d("androidtc", var1_1.toString());
                    var1_1 = new File(var3_4.getPath());
                    var1_1 = ParcelFileDescriptor.open((File)var1_1, 268435456);
                    if (var1_1 == null) break block8;
                    {
                        catch (Throwable var1_2) {
                            throw var1_2;
                        }
                    }
                    try {
                        this.mAnnotatorImpl = var4_6 = new AnnotatorModel(var1_1.getFd());
                        this.mAnnotatorModelInUse = var3_4;
                    }
                    catch (Throwable var3_5) {
                        TextClassifierImpl.maybeCloseAndLogError((ParcelFileDescriptor)var1_1);
                        throw var3_5;
                    }
                }
                TextClassifierImpl.maybeCloseAndLogError((ParcelFileDescriptor)var1_1);
            }
            var1_1 = this.mAnnotatorImpl;
            // MONITOREXIT : var2_3
            return var1_1;
        }
        var3_4 = new StringBuilder();
        var3_4.append("No annotator model for ");
        var3_4.append(var1_1.toLanguageTags());
        var4_7 = new FileNotFoundException(var3_4.toString());
        throw var4_7;
    }

    private Collection<String> getEntitiesForHints(Collection<String> collection) {
        boolean bl = collection.contains("android.text_is_editable");
        boolean bl2 = bl == collection.contains("android.text_is_not_editable");
        if (bl2) {
            return this.mSettings.getEntityListDefault();
        }
        if (bl) {
            return this.mSettings.getEntityListEditable();
        }
        return this.mSettings.getEntityListNotEditable();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private LangIdModel getLangIdImpl() throws FileNotFoundException {
        Object object = this.mLock;
        synchronized (object) {
            Object object2 = this.mLangIdModelFileManager.findBestModelFile(null);
            if (object2 == null) {
                object2 = new FileNotFoundException("No LangID model is found");
                throw object2;
            }
            if (this.mLangIdImpl != null) {
                if (Objects.equals(this.mLangIdModelInUse, object2)) return this.mLangIdImpl;
            }
            Object object3 = new StringBuilder();
            ((StringBuilder)object3).append("Loading ");
            ((StringBuilder)object3).append(object2);
            Log.d(LOG_TAG, ((StringBuilder)object3).toString());
            object3 = new File(((ModelFileManager.ModelFile)object2).getPath());
            object3 = ParcelFileDescriptor.open((File)object3, 268435456);
            if (object3 == null) return this.mLangIdImpl;
            try {
                LangIdModel langIdModel;
                this.mLangIdImpl = langIdModel = new LangIdModel(((ParcelFileDescriptor)object3).getFd());
                this.mLangIdModelInUse = object2;
                return this.mLangIdImpl;
            }
            finally {
                TextClassifierImpl.maybeCloseAndLogError((ParcelFileDescriptor)object3);
            }
        }
    }

    private float getLangIdThreshold() {
        try {
            float f = this.mSettings.getLangIdThresholdOverride() >= 0.0f ? this.mSettings.getLangIdThresholdOverride() : this.getLangIdImpl().getLangIdThreshold();
            return f;
        }
        catch (FileNotFoundException fileNotFoundException) {
            Log.v(LOG_TAG, "Using default foreign language threshold: 0.5");
            return 0.5f;
        }
    }

    private String getResourceLocalesString() {
        try {
            String string2 = this.mContext.getResources().getConfiguration().getLocales().toLanguageTags();
            return string2;
        }
        catch (NullPointerException nullPointerException) {
            return LocaleList.getDefault().toLanguageTags();
        }
    }

    static /* synthetic */ CharSequence lambda$createClassificationResult$2(LabeledIntent labeledIntent, ResolveInfo resolveInfo) {
        return labeledIntent.titleWithoutEntity;
    }

    public static /* synthetic */ String lambda$ftq-sQqJYwUdrdbbr9jz3p4AWos(TextClassifierImpl textClassifierImpl, CharSequence charSequence) {
        return textClassifierImpl.detectLanguageTagsFromText(charSequence);
    }

    static /* synthetic */ String lambda$new$0(Integer n) {
        return "*";
    }

    private static void maybeCloseAndLogError(ParcelFileDescriptor parcelFileDescriptor) {
        if (parcelFileDescriptor == null) {
            return;
        }
        try {
            parcelFileDescriptor.close();
        }
        catch (IOException iOException) {
            Log.e(LOG_TAG, "Error closing file.", iOException);
        }
    }

    private Collection<String> resolveActionTypesFromRequest(ConversationActions.Request request) {
        List<String> list = request.getHints().contains("notification") ? this.mSettings.getNotificationConversationActionTypes() : this.mSettings.getInAppConversationActionTypes();
        return request.getTypeConfig().resolveEntityListModifications(list);
    }

    @Override
    public TextClassification classifyText(TextClassification.Request request) {
        Preconditions.checkNotNull(request);
        TextClassifier.Utils.checkMainThread();
        try {
            int n = request.getEndIndex();
            int n2 = request.getStartIndex();
            String string2 = request.getText().toString();
            if (string2.length() > 0 && n - n2 <= this.mSettings.getClassifyTextMaxRangeLength()) {
                String string3 = TextClassifierImpl.concatenateLocales(request.getDefaultLocales());
                AnnotatorModel.ClassificationResult[] arrclassificationResult = this.detectLanguageTagsFromText(request.getText());
                Object object = request.getReferenceTime() != null ? request.getReferenceTime() : ZonedDateTime.now();
                AnnotatorModel annotatorModel = this.getAnnotatorImpl(request.getDefaultLocales());
                n = request.getStartIndex();
                n2 = request.getEndIndex();
                AnnotatorModel.ClassificationOptions classificationOptions = new AnnotatorModel.ClassificationOptions(object.toInstant().toEpochMilli(), ((ZonedDateTime)object).getZone().getId(), string3, (String)arrclassificationResult);
                arrclassificationResult = annotatorModel.classifyText(string2, n, n2, classificationOptions, (Object)this.mContext, this.getResourceLocalesString());
                if (arrclassificationResult.length > 0) {
                    object = this.createClassificationResult(arrclassificationResult, string2, request.getStartIndex(), request.getEndIndex(), object.toInstant());
                    return object;
                }
            }
        }
        catch (Throwable throwable) {
            Log.e(LOG_TAG, "Error getting text classification info.", throwable);
        }
        return this.mFallback.classifyText(request);
    }

    @Override
    public TextLanguage detectLanguage(TextLanguage.Request request) {
        Preconditions.checkNotNull(request);
        TextClassifier.Utils.checkMainThread();
        Object object = new TextLanguage.Builder();
        LangIdModel.LanguageResult[] arrlanguageResult = this.getLangIdImpl().detectLanguages(request.getText().toString());
        int n = 0;
        do {
            if (n >= arrlanguageResult.length) break;
            ((TextLanguage.Builder)object).putLocale(ULocale.forLanguageTag((String)arrlanguageResult[n].getLanguage()), arrlanguageResult[n].getScore());
            ++n;
        } while (true);
        try {
            object = ((TextLanguage.Builder)object).build();
            return object;
        }
        catch (Throwable throwable) {
            Log.e(LOG_TAG, "Error detecting text language.", throwable);
            return this.mFallback.detectLanguage(request);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void dump(IndentingPrintWriter indentingPrintWriter) {
        Object object = this.mLock;
        synchronized (object) {
            indentingPrintWriter.println("TextClassifierImpl:");
            indentingPrintWriter.increaseIndent();
            indentingPrintWriter.println("Annotator model file(s):");
            indentingPrintWriter.increaseIndent();
            Iterator<ModelFileManager.ModelFile> iterator = this.mAnnotatorModelFileManager.listModelFiles().iterator();
            while (iterator.hasNext()) {
                indentingPrintWriter.println(iterator.next().toString());
            }
            indentingPrintWriter.decreaseIndent();
            indentingPrintWriter.println("LangID model file(s):");
            indentingPrintWriter.increaseIndent();
            iterator = this.mLangIdModelFileManager.listModelFiles().iterator();
            while (iterator.hasNext()) {
                indentingPrintWriter.println(iterator.next().toString());
            }
            indentingPrintWriter.decreaseIndent();
            indentingPrintWriter.println("Actions model file(s):");
            indentingPrintWriter.increaseIndent();
            iterator = this.mActionsModelFileManager.listModelFiles().iterator();
            do {
                if (!iterator.hasNext()) {
                    indentingPrintWriter.decreaseIndent();
                    indentingPrintWriter.printPair("mFallback", this.mFallback);
                    indentingPrintWriter.decreaseIndent();
                    indentingPrintWriter.println();
                    return;
                }
                indentingPrintWriter.println(iterator.next().toString());
            } while (true);
        }
    }

    /*
     * WARNING - combined exceptions agressively - possible behaviour change.
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    @Override
    public TextLinks generateLinks(TextLinks.Request var1_1) {
        Preconditions.checkNotNull(var1_1);
        TextClassifier.Utils.checkTextLength(var1_1.getText(), this.getMaxGenerateLinksTextLength());
        TextClassifier.Utils.checkMainThread();
        if (!this.mSettings.isSmartLinkifyEnabled() && var1_1.isLegacyFallback()) {
            return TextClassifier.Utils.generateLegacyLinks(var1_1);
        }
        var2_2 = var1_1.getText().toString();
        var3_3 = new TextLinks.Builder((String)var2_2);
        try {
            var4_4 = System.currentTimeMillis();
            var6_5 = ZonedDateTime.now();
            var7_8 = var1_1.getEntityConfig() != null ? var1_1.getEntityConfig().resolveEntityListModifications(this.getEntitiesForHints(var1_1.getEntityConfig().getHints())) : this.mSettings.getEntityListDefault();
            var8_10 = TextClassifierImpl.concatenateLocales(var1_1.getDefaultLocales());
            var9_11 = this.detectLanguageTagsFromText(var1_1.getText());
            var10_12 = this.getAnnotatorImpl(var1_1.getDefaultLocales());
            var11_13 = ExtrasUtils.isSerializedEntityDataEnabled(var1_1);
            var12_14 = new AnnotatorModel.AnnotationOptions(var6_5.toInstant().toEpochMilli(), var6_5.getZone().getId(), (String)var8_10, (String)var9_11, var7_8, AnnotatorModel.AnnotationUsecase.SMART.getValue(), var11_13);
            var8_10 = var10_12.annotate((String)var2_2, var12_14);
            var13_15 = var8_10.length;
            var14_16 = 0;
            do lbl-1000: // 2 sources:
            {
                if (var14_16 < var13_15) {
                    var6_7 = var8_10[var14_16];
                    var10_12 = var6_7.getClassification();
                    if (var10_12.length == 0 || !var7_8.contains(var10_12[0].getCollection())) ** break block10
                    var2_2 = new ArrayMap<String, Float>();
                } else {
                    var3_3 = var3_3.build();
                    var16_18 = System.currentTimeMillis();
                    var7_8 = var1_1.getCallingPackageName() == null ? this.mContext.getPackageName() : var1_1.getCallingPackageName();
                    this.mGenerateLinksLogger.logGenerateLinks(var1_1.getText(), (TextLinks)var3_3, (String)var7_8, var16_18 - var4_4);
                    return var3_3;
                }
                for (var15_17 = 0; var15_17 < var10_12.length; ++var15_17) {
                    var2_2.put(var10_12[var15_17].getCollection(), Float.valueOf(var10_12[var15_17].getScore()));
                }
                var9_11 = new Bundle();
                if (var11_13) {
                    ExtrasUtils.putEntities((Bundle)var9_11, var10_12);
                }
                var3_3.addLink(var6_7.getStartIndex(), var6_7.getEndIndex(), var2_2, (Bundle)var9_11);
                break;
            } while (true);
        }
        catch (Throwable var7_9) {
            Log.e("androidtc", "Error getting links info.", var7_9);
            return this.mFallback.generateLinks(var1_1);
        }
        {
            ++var14_16;
            ** while (true)
        }
    }

    @Override
    public int getMaxGenerateLinksTextLength() {
        return this.mSettings.getGenerateLinksMaxTextLength();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public /* synthetic */ void lambda$new$1$TextClassifierImpl() {
        Object object = this.mLock;
        synchronized (object) {
            this.mActionsImpl = null;
            this.mActionModelInUse = null;
            return;
        }
    }

    @Override
    public void onSelectionEvent(SelectionEvent selectionEvent) {
        this.mSessionLogger.writeEvent(selectionEvent);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void onTextClassifierEvent(TextClassifierEvent textClassifierEvent) {
        try {
            SelectionEvent selectionEvent = textClassifierEvent.toSelectionEvent();
            if (selectionEvent != null) {
                this.mSessionLogger.writeEvent(selectionEvent);
                return;
            }
            this.mTextClassifierEventTronLogger.writeEvent(textClassifierEvent);
            return;
        }
        catch (Exception exception) {
            Log.e(LOG_TAG, "Error writing event", exception);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public ConversationActions suggestConversationActions(ConversationActions.Request request) {
        Preconditions.checkNotNull(request);
        TextClassifier.Utils.checkMainThread();
        try {
            ActionsSuggestionsModel actionsSuggestionsModel = this.getActionsImpl();
            if (actionsSuggestionsModel == null) {
                return this.mFallback.suggestConversationActions(request);
            }
            ActionsSuggestionsModel.ConversationMessage[] arrconversationMessage = request.getConversation();
            _$$Lambda$TextClassifierImpl$ftq_sQqJYwUdrdbbr9jz3p4AWos _$$Lambda$TextClassifierImpl$ftq_sQqJYwUdrdbbr9jz3p4AWos = new _$$Lambda$TextClassifierImpl$ftq_sQqJYwUdrdbbr9jz3p4AWos(this);
            if ((arrconversationMessage = ActionsSuggestionsHelper.toNativeMessages(arrconversationMessage, _$$Lambda$TextClassifierImpl$ftq_sQqJYwUdrdbbr9jz3p4AWos)).length == 0) {
                return this.mFallback.suggestConversationActions(request);
            }
            _$$Lambda$TextClassifierImpl$ftq_sQqJYwUdrdbbr9jz3p4AWos = new ActionsSuggestionsModel.Conversation(arrconversationMessage);
            return this.createConversationActionResult(request, actionsSuggestionsModel.suggestActionsWithIntents((ActionsSuggestionsModel.Conversation)_$$Lambda$TextClassifierImpl$ftq_sQqJYwUdrdbbr9jz3p4AWos, null, (Object)this.mContext, this.getResourceLocalesString(), this.getAnnotatorImpl(LocaleList.getDefault())));
        }
        catch (Throwable throwable) {
            Log.e(LOG_TAG, "Error suggesting conversation actions.", throwable);
            return this.mFallback.suggestConversationActions(request);
        }
    }

    /*
     * WARNING - combined exceptions agressively - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public TextSelection suggestSelection(TextSelection.Request request) {
        Preconditions.checkNotNull(request);
        TextClassifier.Utils.checkMainThread();
        try {
            Object object;
            int n = request.getEndIndex();
            int n2 = request.getStartIndex();
            String string2 = request.getText().toString();
            if (string2.length() <= 0) return this.mFallback.suggestSelection(request);
            if (n - n2 > this.mSettings.getSuggestSelectionMaxRangeLength()) return this.mFallback.suggestSelection(request);
            String string3 = TextClassifierImpl.concatenateLocales(request.getDefaultLocales());
            String string4 = this.detectLanguageTagsFromText(request.getText());
            AnnotatorModel.ClassificationResult[] arrclassificationResult = ZonedDateTime.now();
            AnnotatorModel annotatorModel = this.getAnnotatorImpl(request.getDefaultLocales());
            if (this.mSettings.isModelDarkLaunchEnabled() && !request.isDarkLaunchAllowed()) {
                n = request.getStartIndex();
                n2 = request.getEndIndex();
            } else {
                n2 = request.getStartIndex();
                n = request.getEndIndex();
                object = new AnnotatorModel.SelectionOptions(string3, string4);
                object = annotatorModel.suggestSelection(string2, n2, n, (AnnotatorModel.SelectionOptions)object);
                n = object[0];
                n2 = object[1];
            }
            if (n < n2 && n >= 0 && n2 <= string2.length() && n <= request.getStartIndex() && n2 >= request.getEndIndex()) {
                object = new TextSelection.Builder(n, n2);
                AnnotatorModel.ClassificationOptions classificationOptions = new AnnotatorModel.ClassificationOptions(arrclassificationResult.toInstant().toEpochMilli(), arrclassificationResult.getZone().getId(), string3, string4);
                arrclassificationResult = annotatorModel.classifyText(string2, n, n2, classificationOptions, null, null);
                n2 = arrclassificationResult.length;
            } else {
                Log.d(LOG_TAG, "Got bad indices for input text. Ignoring result.");
                return this.mFallback.suggestSelection(request);
            }
            for (n = 0; n < n2; ++n) {
                ((TextSelection.Builder)object).setEntityType(arrclassificationResult[n].getCollection(), arrclassificationResult[n].getScore());
            }
            return ((TextSelection.Builder)object).setId(this.createId(string2, request.getStartIndex(), request.getEndIndex())).build();
        }
        catch (Throwable throwable) {
            Log.e(LOG_TAG, "Error suggesting selection for text. No changes to selection suggested.", throwable);
        }
        return this.mFallback.suggestSelection(request);
    }
}

