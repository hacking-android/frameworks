/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.view.textclassifier.-$
 *  android.view.textclassifier.-$$Lambda
 *  android.view.textclassifier.-$$Lambda$ActionsSuggestionsHelper
 *  android.view.textclassifier.-$$Lambda$ActionsSuggestionsHelper$6oTtcn9bDE-u-8FbiyGdntqoQG0
 *  android.view.textclassifier.-$$Lambda$ActionsSuggestionsHelper$YTQv8oPvlmJL4tITUFD4z4JWKRk
 *  android.view.textclassifier.-$$Lambda$ActionsSuggestionsHelper$sY0w9od2zcl4YFel0lG4VB3vf7I
 *  android.view.textclassifier.-$$Lambda$OGSS2qx6njxlnp0dnKb4lA3jnw8
 *  com.google.android.textclassifier.ActionsSuggestionsModel
 *  com.google.android.textclassifier.ActionsSuggestionsModel$ActionSuggestion
 *  com.google.android.textclassifier.ActionsSuggestionsModel$ConversationMessage
 *  com.google.android.textclassifier.RemoteActionTemplate
 */
package android.view.textclassifier;

import android.app.Person;
import android.app.RemoteAction;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.Pair;
import android.view.textclassifier.-$;
import android.view.textclassifier.ConversationAction;
import android.view.textclassifier.ConversationActions;
import android.view.textclassifier.ExtrasUtils;
import android.view.textclassifier.Log;
import android.view.textclassifier.SelectionSessionLogger;
import android.view.textclassifier._$$Lambda$ActionsSuggestionsHelper$6oTtcn9bDE_u_8FbiyGdntqoQG0;
import android.view.textclassifier._$$Lambda$ActionsSuggestionsHelper$YTQv8oPvlmJL4tITUFD4z4JWKRk;
import android.view.textclassifier._$$Lambda$ActionsSuggestionsHelper$sY0w9od2zcl4YFel0lG4VB3vf7I;
import android.view.textclassifier._$$Lambda$OGSS2qx6njxlnp0dnKb4lA3jnw8;
import android.view.textclassifier.intent.LabeledIntent;
import android.view.textclassifier.intent.TemplateIntentFactory;
import com.android.internal.annotations.VisibleForTesting;
import com.google.android.textclassifier.ActionsSuggestionsModel;
import com.google.android.textclassifier.RemoteActionTemplate;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.StringJoiner;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.ToIntFunction;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@VisibleForTesting(visibility=VisibleForTesting.Visibility.PACKAGE)
public final class ActionsSuggestionsHelper {
    private static final int FIRST_NON_LOCAL_USER = 1;
    private static final String TAG = "ActionsSuggestions";
    private static final int USER_LOCAL = 0;

    private ActionsSuggestionsHelper() {
    }

    public static LabeledIntent.Result createLabeledIntentResult(Context object, TemplateIntentFactory object2, ActionsSuggestionsModel.ActionSuggestion object3) {
        RemoteActionTemplate[] arrremoteActionTemplate = object3.getRemoteActionTemplates();
        if (arrremoteActionTemplate == null) {
            object = new StringBuilder();
            ((StringBuilder)object).append("createRemoteAction: Missing template for type ");
            ((StringBuilder)object).append(object3.getActionType());
            Log.w(TAG, ((StringBuilder)object).toString());
            return null;
        }
        if ((object2 = ((TemplateIntentFactory)object2).create(arrremoteActionTemplate)).isEmpty()) {
            return null;
        }
        object3 = ActionsSuggestionsHelper.createTitleChooser(object3.getActionType());
        return ((LabeledIntent)object2.get(0)).resolve((Context)object, (LabeledIntent.TitleChooser)object3, null);
    }

    public static String createResultId(Context context, List<ConversationActions.Message> list, int n, List<Locale> object) {
        StringJoiner stringJoiner = new StringJoiner(",");
        object = object.iterator();
        while (object.hasNext()) {
            stringJoiner.add(((Locale)object.next()).toLanguageTag());
        }
        return SelectionSessionLogger.SignatureParser.createSignature("androidtc", String.format(Locale.US, "%s_v%d", stringJoiner.toString(), n), Objects.hash(list.stream().mapToInt(_$$Lambda$ActionsSuggestionsHelper$YTQv8oPvlmJL4tITUFD4z4JWKRk.INSTANCE), context.getPackageName(), System.currentTimeMillis()));
    }

    public static LabeledIntent.TitleChooser createTitleChooser(String string2) {
        if ("open_url".equals(string2)) {
            return _$$Lambda$ActionsSuggestionsHelper$sY0w9od2zcl4YFel0lG4VB3vf7I.INSTANCE;
        }
        return null;
    }

    private static Pair<String, String> getRepresentation(ConversationAction conversationAction) {
        Parcelable parcelable = conversationAction.getAction();
        String string2 = null;
        if (parcelable == null) {
            return null;
        }
        parcelable = ExtrasUtils.getActionIntent(conversationAction.getExtras()).getComponent();
        if (parcelable != null) {
            string2 = ((ComponentName)parcelable).getPackageName();
        }
        return new Pair<String, String>(conversationAction.getAction().getTitle().toString(), string2);
    }

    private static int hashMessage(ConversationActions.Message message) {
        return Objects.hash(message.getAuthor(), message.getText(), message.getReferenceTime());
    }

    public static /* synthetic */ int lambda$YTQv8oPvlmJL4tITUFD4z4JWKRk(ConversationActions.Message message) {
        return ActionsSuggestionsHelper.hashMessage(message);
    }

    static /* synthetic */ CharSequence lambda$createTitleChooser$1(LabeledIntent labeledIntent, ResolveInfo resolveInfo) {
        if (resolveInfo.handleAllWebDataURI) {
            return labeledIntent.titleWithEntity;
        }
        if ("android".equals(resolveInfo.activityInfo.packageName)) {
            return labeledIntent.titleWithEntity;
        }
        return labeledIntent.titleWithoutEntity;
    }

    static /* synthetic */ boolean lambda$toNativeMessages$0(ConversationActions.Message message) {
        return TextUtils.isEmpty(message.getText()) ^ true;
    }

    public static List<ConversationAction> removeActionsWithDuplicates(List<ConversationAction> object) {
        Pair<String, String> pair;
        ArrayMap<Object, Integer> arrayMap = new ArrayMap<Object, Integer>();
        Object object2 = object.iterator();
        while (object2.hasNext()) {
            pair = ActionsSuggestionsHelper.getRepresentation(object2.next());
            if (pair == null) continue;
            arrayMap.put(pair, arrayMap.getOrDefault(pair, 0) + 1);
        }
        object2 = new ArrayList();
        pair = object.iterator();
        while (pair.hasNext()) {
            object = (ConversationAction)pair.next();
            Pair<String, String> pair2 = ActionsSuggestionsHelper.getRepresentation((ConversationAction)object);
            if (pair2 != null && arrayMap.getOrDefault(pair2, 0) != 1) continue;
            object2.add(object);
        }
        return object2;
    }

    public static ActionsSuggestionsModel.ConversationMessage[] toNativeMessages(List<ConversationActions.Message> object, Function<CharSequence, String> function) {
        List list = (List)object.stream().filter(_$$Lambda$ActionsSuggestionsHelper$6oTtcn9bDE_u_8FbiyGdntqoQG0.INSTANCE).collect(Collectors.toCollection(_$$Lambda$OGSS2qx6njxlnp0dnKb4lA3jnw8.INSTANCE));
        if (list.isEmpty()) {
            return new ActionsSuggestionsModel.ConversationMessage[0];
        }
        ArrayDeque<ActionsSuggestionsModel.ConversationMessage> arrayDeque = new ArrayDeque<ActionsSuggestionsModel.ConversationMessage>();
        PersonEncoder personEncoder = new PersonEncoder();
        for (int i = list.size() - 1; i >= 0; --i) {
            ConversationActions.Message message = (ConversationActions.Message)list.get(i);
            long l = message.getReferenceTime() == null ? 0L : message.getReferenceTime().toInstant().toEpochMilli();
            object = message.getReferenceTime() == null ? null : message.getReferenceTime().getZone().getId();
            arrayDeque.push(new ActionsSuggestionsModel.ConversationMessage(personEncoder.encode(message.getAuthor()), message.getText().toString(), l, (String)object, function.apply(message.getText())));
        }
        return arrayDeque.toArray((T[])new ActionsSuggestionsModel.ConversationMessage[arrayDeque.size()]);
    }

    private static final class PersonEncoder {
        private final Map<Person, Integer> mMapping = new ArrayMap<Person, Integer>();
        private int mNextUserId = 1;

        private PersonEncoder() {
        }

        private int encode(Person person) {
            Integer n;
            if (ConversationActions.Message.PERSON_USER_SELF.equals(person)) {
                return 0;
            }
            Integer n2 = n = this.mMapping.get(person);
            if (n == null) {
                this.mMapping.put(person, this.mNextUserId);
                n2 = this.mNextUserId;
                ++this.mNextUserId;
            }
            return n2;
        }
    }

}

