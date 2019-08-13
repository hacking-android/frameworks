/*
 * Decompiled with CFR 0.145.
 */
package android.view.textclassifier;

import android.os.LocaleList;
import android.os.Looper;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.SpannableString;
import android.text.style.URLSpan;
import android.text.util.Linkify;
import android.util.ArrayMap;
import android.view.textclassifier.ConversationAction;
import android.view.textclassifier.ConversationActions;
import android.view.textclassifier.Log;
import android.view.textclassifier.SelectionEvent;
import android.view.textclassifier.TextClassification;
import android.view.textclassifier.TextClassifierEvent;
import android.view.textclassifier.TextLanguage;
import android.view.textclassifier.TextLinks;
import android.view.textclassifier.TextSelection;
import com.android.internal.annotations.GuardedBy;
import com.android.internal.util.IndentingPrintWriter;
import com.android.internal.util.Preconditions;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

public interface TextClassifier {
    public static final String DEFAULT_LOG_TAG = "androidtc";
    public static final String EXTRA_FROM_TEXT_CLASSIFIER = "android.view.textclassifier.extra.FROM_TEXT_CLASSIFIER";
    public static final String HINT_TEXT_IS_EDITABLE = "android.text_is_editable";
    public static final String HINT_TEXT_IS_NOT_EDITABLE = "android.text_is_not_editable";
    public static final int LOCAL = 0;
    public static final TextClassifier NO_OP = new TextClassifier(){

        public String toString() {
            return "TextClassifier.NO_OP";
        }
    };
    public static final int SYSTEM = 1;
    public static final String TYPE_ADDRESS = "address";
    public static final String TYPE_DATE = "date";
    public static final String TYPE_DATE_TIME = "datetime";
    public static final String TYPE_DICTIONARY = "dictionary";
    public static final String TYPE_EMAIL = "email";
    public static final String TYPE_FLIGHT_NUMBER = "flight";
    public static final String TYPE_OTHER = "other";
    public static final String TYPE_PHONE = "phone";
    public static final String TYPE_UNKNOWN = "";
    public static final String TYPE_URL = "url";
    public static final String WIDGET_TYPE_CUSTOM_EDITTEXT = "customedit";
    public static final String WIDGET_TYPE_CUSTOM_TEXTVIEW = "customview";
    public static final String WIDGET_TYPE_CUSTOM_UNSELECTABLE_TEXTVIEW = "nosel-customview";
    public static final String WIDGET_TYPE_EDITTEXT = "edittext";
    public static final String WIDGET_TYPE_EDIT_WEBVIEW = "edit-webview";
    public static final String WIDGET_TYPE_NOTIFICATION = "notification";
    public static final String WIDGET_TYPE_TEXTVIEW = "textview";
    public static final String WIDGET_TYPE_UNKNOWN = "unknown";
    public static final String WIDGET_TYPE_UNSELECTABLE_TEXTVIEW = "nosel-textview";
    public static final String WIDGET_TYPE_WEBVIEW = "webview";

    default public TextClassification classifyText(TextClassification.Request request) {
        Preconditions.checkNotNull(request);
        Utils.checkMainThread();
        return TextClassification.EMPTY;
    }

    default public TextClassification classifyText(CharSequence charSequence, int n, int n2, LocaleList localeList) {
        return this.classifyText(new TextClassification.Request.Builder(charSequence, n, n2).setDefaultLocales(localeList).build());
    }

    default public void destroy() {
    }

    default public TextLanguage detectLanguage(TextLanguage.Request request) {
        Preconditions.checkNotNull(request);
        Utils.checkMainThread();
        return TextLanguage.EMPTY;
    }

    default public void dump(IndentingPrintWriter indentingPrintWriter) {
    }

    default public TextLinks generateLinks(TextLinks.Request request) {
        Preconditions.checkNotNull(request);
        Utils.checkMainThread();
        return new TextLinks.Builder(request.getText().toString()).build();
    }

    default public int getMaxGenerateLinksTextLength() {
        return Integer.MAX_VALUE;
    }

    default public boolean isDestroyed() {
        return false;
    }

    default public void onSelectionEvent(SelectionEvent selectionEvent) {
    }

    default public void onTextClassifierEvent(TextClassifierEvent textClassifierEvent) {
    }

    default public ConversationActions suggestConversationActions(ConversationActions.Request request) {
        Preconditions.checkNotNull(request);
        Utils.checkMainThread();
        return new ConversationActions(Collections.<ConversationAction>emptyList(), null);
    }

    default public TextSelection suggestSelection(TextSelection.Request request) {
        Preconditions.checkNotNull(request);
        Utils.checkMainThread();
        return new TextSelection.Builder(request.getStartIndex(), request.getEndIndex()).build();
    }

    default public TextSelection suggestSelection(CharSequence charSequence, int n, int n2, LocaleList localeList) {
        return this.suggestSelection(new TextSelection.Request.Builder(charSequence, n, n2).setDefaultLocales(localeList).build());
    }

    public static final class EntityConfig
    implements Parcelable {
        public static final Parcelable.Creator<EntityConfig> CREATOR = new Parcelable.Creator<EntityConfig>(){

            @Override
            public EntityConfig createFromParcel(Parcel parcel) {
                return new EntityConfig(parcel);
            }

            public EntityConfig[] newArray(int n) {
                return new EntityConfig[n];
            }
        };
        private final List<String> mExcludedTypes;
        private final List<String> mHints;
        private final boolean mIncludeTypesFromTextClassifier;
        private final List<String> mIncludedTypes;

        private EntityConfig(Parcel parcel) {
            this.mIncludedTypes = new ArrayList<String>();
            parcel.readStringList(this.mIncludedTypes);
            this.mExcludedTypes = new ArrayList<String>();
            parcel.readStringList(this.mExcludedTypes);
            ArrayList<String> arrayList = new ArrayList<String>();
            parcel.readStringList(arrayList);
            this.mHints = Collections.unmodifiableList(arrayList);
            boolean bl = parcel.readByte() != 0;
            this.mIncludeTypesFromTextClassifier = bl;
        }

        private EntityConfig(List<String> list, List<String> list2, List<String> list3, boolean bl) {
            this.mIncludedTypes = Preconditions.checkNotNull(list);
            this.mExcludedTypes = Preconditions.checkNotNull(list2);
            this.mHints = Preconditions.checkNotNull(list3);
            this.mIncludeTypesFromTextClassifier = bl;
        }

        @Deprecated
        public static EntityConfig create(Collection<String> collection, Collection<String> collection2, Collection<String> collection3) {
            return new Builder().setIncludedTypes(collection2).setExcludedTypes(collection3).setHints(collection).includeTypesFromTextClassifier(true).build();
        }

        @Deprecated
        public static EntityConfig createWithExplicitEntityList(Collection<String> collection) {
            return new Builder().setIncludedTypes(collection).includeTypesFromTextClassifier(false).build();
        }

        @Deprecated
        public static EntityConfig createWithHints(Collection<String> collection) {
            return new Builder().includeTypesFromTextClassifier(true).setHints(collection).build();
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public Collection<String> getHints() {
            return this.mHints;
        }

        public Collection<String> resolveEntityListModifications(Collection<String> collection) {
            HashSet<String> hashSet = new HashSet<String>();
            if (this.mIncludeTypesFromTextClassifier) {
                hashSet.addAll(collection);
            }
            hashSet.addAll(this.mIncludedTypes);
            hashSet.removeAll(this.mExcludedTypes);
            return hashSet;
        }

        public boolean shouldIncludeTypesFromTextClassifier() {
            return this.mIncludeTypesFromTextClassifier;
        }

        @Override
        public void writeToParcel(Parcel parcel, int n) {
            parcel.writeStringList(this.mIncludedTypes);
            parcel.writeStringList(this.mExcludedTypes);
            parcel.writeStringList(this.mHints);
            parcel.writeByte((byte)(this.mIncludeTypesFromTextClassifier ? 1 : 0));
        }

        public static final class Builder {
            private Collection<String> mExcludedTypes;
            private Collection<String> mHints;
            private boolean mIncludeTypesFromTextClassifier = true;
            private Collection<String> mIncludedTypes;

            public EntityConfig build() {
                Collection<String> collection = this.mIncludedTypes;
                collection = collection == null ? Collections.emptyList() : new ArrayList<String>(collection);
                Collection<String> collection2 = this.mExcludedTypes;
                collection2 = collection2 == null ? Collections.emptyList() : new ArrayList<String>(collection2);
                Collection<String> collection3 = this.mHints;
                collection3 = collection3 == null ? Collections.emptyList() : Collections.unmodifiableList(new ArrayList<String>(collection3));
                return new EntityConfig((List)collection, (List)collection2, (List)collection3, this.mIncludeTypesFromTextClassifier);
            }

            public Builder includeTypesFromTextClassifier(boolean bl) {
                this.mIncludeTypesFromTextClassifier = bl;
                return this;
            }

            public Builder setExcludedTypes(Collection<String> collection) {
                this.mExcludedTypes = collection;
                return this;
            }

            public Builder setHints(Collection<String> collection) {
                this.mHints = collection;
                return this;
            }

            public Builder setIncludedTypes(Collection<String> collection) {
                this.mIncludedTypes = collection;
                return this;
            }
        }

    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface EntityType {
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface Hints {
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface TextClassifierType {
    }

    public static final class Utils {
        @GuardedBy(value={"WORD_ITERATOR"})
        private static final BreakIterator WORD_ITERATOR = BreakIterator.getWordInstance();

        private static void addLinks(TextLinks.Builder builder, String arruRLSpan, String string2) {
            SpannableString spannableString = new SpannableString((CharSequence)arruRLSpan);
            if (Linkify.addLinks(spannableString, Utils.linkMask(string2))) {
                int n = spannableString.length();
                for (URLSpan uRLSpan : spannableString.getSpans(0, n, URLSpan.class)) {
                    builder.addLink(spannableString.getSpanStart(uRLSpan), spannableString.getSpanEnd(uRLSpan), Utils.entityScores(string2), uRLSpan);
                }
            }
        }

        static void checkArgument(CharSequence charSequence, int n, int n2) {
            boolean bl = true;
            boolean bl2 = charSequence != null;
            Preconditions.checkArgument(bl2);
            bl2 = n >= 0;
            Preconditions.checkArgument(bl2);
            bl2 = n2 <= charSequence.length();
            Preconditions.checkArgument(bl2);
            bl2 = n2 > n ? bl : false;
            Preconditions.checkArgument(bl2);
        }

        static void checkMainThread() {
            if (Looper.myLooper() == Looper.getMainLooper()) {
                Log.w(TextClassifier.DEFAULT_LOG_TAG, "TextClassifier called on main thread");
            }
        }

        static void checkTextLength(CharSequence charSequence, int n) {
            Preconditions.checkArgumentInRange(charSequence.length(), 0, n, "text.length()");
        }

        private static Map<String, Float> entityScores(String string2) {
            ArrayMap<String, Float> arrayMap = new ArrayMap<String, Float>();
            arrayMap.put(string2, Float.valueOf(1.0f));
            return arrayMap;
        }

        public static TextLinks generateLegacyLinks(TextLinks.Request object) {
            String string2 = ((TextLinks.Request)object).getText().toString();
            TextLinks.Builder builder = new TextLinks.Builder(string2);
            if ((object = ((TextLinks.Request)object).getEntityConfig().resolveEntityListModifications(Collections.<String>emptyList())).contains(TextClassifier.TYPE_URL)) {
                Utils.addLinks(builder, string2, TextClassifier.TYPE_URL);
            }
            if (object.contains(TextClassifier.TYPE_PHONE)) {
                Utils.addLinks(builder, string2, TextClassifier.TYPE_PHONE);
            }
            if (object.contains(TextClassifier.TYPE_EMAIL)) {
                Utils.addLinks(builder, string2, TextClassifier.TYPE_EMAIL);
            }
            return builder.build();
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public static String getSubString(String string2, int n, int n2, int n3) {
            boolean bl = true;
            boolean bl2 = n >= 0;
            Preconditions.checkArgument(bl2);
            bl2 = n2 <= string2.length();
            Preconditions.checkArgument(bl2);
            bl2 = n <= n2 ? bl : false;
            Preconditions.checkArgument(bl2);
            if (string2.length() < n3) {
                return string2;
            }
            int n4 = n2 - n;
            if (n4 >= n3) {
                return string2.substring(n, n2);
            }
            n = Math.max(0, Math.min(n - (n3 - n4) / 2, string2.length() - n3));
            n2 = Math.min(string2.length(), n + n3);
            BreakIterator breakIterator = WORD_ITERATOR;
            synchronized (breakIterator) {
                WORD_ITERATOR.setText(string2);
                if (!WORD_ITERATOR.isBoundary(n)) {
                    n = Math.max(0, WORD_ITERATOR.preceding(n));
                }
                if (!WORD_ITERATOR.isBoundary(n2)) {
                    n2 = Math.max(n2, WORD_ITERATOR.following(n2));
                }
                WORD_ITERATOR.setText(TextClassifier.TYPE_UNKNOWN);
                return string2.substring(n, n2);
            }
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        private static int linkMask(String string2) {
            int n = string2.hashCode();
            if (n != 116079) {
                if (n != 96619420) {
                    if (n != 106642798) return 0;
                    if (!string2.equals(TextClassifier.TYPE_PHONE)) return 0;
                    return 4;
                }
                if (!string2.equals(TextClassifier.TYPE_EMAIL)) return 0;
                return 2;
            }
            if (!string2.equals(TextClassifier.TYPE_URL)) return 0;
            return 1;
        }
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface WidgetType {
    }

}

