/*
 * Decompiled with CFR 0.145.
 */
package android.view.textclassifier;

import android.content.Context;
import android.os.Bundle;
import android.os.LocaleList;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.Spannable;
import android.text.style.ClickableSpan;
import android.text.style.URLSpan;
import android.view.View;
import android.view.textclassifier.EntityConfidence;
import android.view.textclassifier.TextClassificationManager;
import android.view.textclassifier.TextClassifier;
import android.view.textclassifier.TextLinksParams;
import android.widget.TextView;
import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.util.Preconditions;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.function.Function;

public final class TextLinks
implements Parcelable {
    public static final int APPLY_STRATEGY_IGNORE = 0;
    public static final int APPLY_STRATEGY_REPLACE = 1;
    public static final Parcelable.Creator<TextLinks> CREATOR = new Parcelable.Creator<TextLinks>(){

        @Override
        public TextLinks createFromParcel(Parcel parcel) {
            return new TextLinks(parcel);
        }

        public TextLinks[] newArray(int n) {
            return new TextLinks[n];
        }
    };
    public static final int STATUS_DIFFERENT_TEXT = 3;
    public static final int STATUS_LINKS_APPLIED = 0;
    public static final int STATUS_NO_LINKS_APPLIED = 2;
    public static final int STATUS_NO_LINKS_FOUND = 1;
    public static final int STATUS_UNSUPPORTED_CHARACTER = 4;
    private final Bundle mExtras;
    private final String mFullText;
    private final List<TextLink> mLinks;

    private TextLinks(Parcel parcel) {
        this.mFullText = parcel.readString();
        this.mLinks = parcel.createTypedArrayList(TextLink.CREATOR);
        this.mExtras = parcel.readBundle();
    }

    private TextLinks(String string2, ArrayList<TextLink> arrayList, Bundle bundle) {
        this.mFullText = string2;
        this.mLinks = Collections.unmodifiableList(arrayList);
        this.mExtras = bundle;
    }

    public int apply(Spannable spannable, int n, Function<TextLink, TextLinkSpan> function) {
        Preconditions.checkNotNull(spannable);
        return new TextLinksParams.Builder().setApplyStrategy(n).setSpanFactory(function).build().apply(spannable, this);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public Bundle getExtras() {
        return this.mExtras;
    }

    public Collection<TextLink> getLinks() {
        return this.mLinks;
    }

    public String getText() {
        return this.mFullText;
    }

    public String toString() {
        return String.format(Locale.US, "TextLinks{fullText=%s, links=%s}", this.mFullText, this.mLinks);
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeString(this.mFullText);
        parcel.writeTypedList(this.mLinks);
        parcel.writeBundle(this.mExtras);
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface ApplyStrategy {
    }

    public static final class Builder {
        private Bundle mExtras;
        private final String mFullText;
        private final ArrayList<TextLink> mLinks;

        public Builder(String string2) {
            this.mFullText = Preconditions.checkNotNull(string2);
            this.mLinks = new ArrayList();
        }

        private Builder addLink(int n, int n2, Map<String, Float> map, Bundle bundle, URLSpan uRLSpan) {
            this.mLinks.add(new TextLink(n, n2, new EntityConfidence(map), bundle, uRLSpan));
            return this;
        }

        public Builder addLink(int n, int n2, Map<String, Float> map) {
            return this.addLink(n, n2, map, Bundle.EMPTY, null);
        }

        public Builder addLink(int n, int n2, Map<String, Float> map, Bundle bundle) {
            return this.addLink(n, n2, map, bundle, null);
        }

        Builder addLink(int n, int n2, Map<String, Float> map, URLSpan uRLSpan) {
            return this.addLink(n, n2, map, Bundle.EMPTY, uRLSpan);
        }

        public TextLinks build() {
            Bundle bundle;
            String string2 = this.mFullText;
            ArrayList<TextLink> arrayList = this.mLinks;
            Bundle bundle2 = bundle = this.mExtras;
            if (bundle == null) {
                bundle2 = Bundle.EMPTY;
            }
            return new TextLinks(string2, arrayList, bundle2);
        }

        public Builder clearTextLinks() {
            this.mLinks.clear();
            return this;
        }

        public Builder setExtras(Bundle bundle) {
            this.mExtras = bundle;
            return this;
        }
    }

    public static final class Request
    implements Parcelable {
        public static final Parcelable.Creator<Request> CREATOR = new Parcelable.Creator<Request>(){

            @Override
            public Request createFromParcel(Parcel parcel) {
                return Request.readFromParcel(parcel);
            }

            public Request[] newArray(int n) {
                return new Request[n];
            }
        };
        private String mCallingPackageName;
        private final LocaleList mDefaultLocales;
        private final TextClassifier.EntityConfig mEntityConfig;
        private final Bundle mExtras;
        private final boolean mLegacyFallback;
        private final CharSequence mText;

        private Request(CharSequence charSequence, LocaleList localeList, TextClassifier.EntityConfig entityConfig, boolean bl, Bundle bundle) {
            this.mText = charSequence;
            this.mDefaultLocales = localeList;
            this.mEntityConfig = entityConfig;
            this.mLegacyFallback = bl;
            this.mExtras = bundle;
        }

        private static Request readFromParcel(Parcel object) {
            String string2 = ((Parcel)object).readString();
            LocaleList localeList = (LocaleList)((Parcel)object).readParcelable(null);
            TextClassifier.EntityConfig entityConfig = (TextClassifier.EntityConfig)((Parcel)object).readParcelable(null);
            String string3 = ((Parcel)object).readString();
            object = new Request(string2, localeList, entityConfig, true, ((Parcel)object).readBundle());
            ((Request)object).setCallingPackageName(string3);
            return object;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public String getCallingPackageName() {
            return this.mCallingPackageName;
        }

        public LocaleList getDefaultLocales() {
            return this.mDefaultLocales;
        }

        public TextClassifier.EntityConfig getEntityConfig() {
            return this.mEntityConfig;
        }

        public Bundle getExtras() {
            return this.mExtras;
        }

        public CharSequence getText() {
            return this.mText;
        }

        public boolean isLegacyFallback() {
            return this.mLegacyFallback;
        }

        @VisibleForTesting(visibility=VisibleForTesting.Visibility.PACKAGE)
        public void setCallingPackageName(String string2) {
            this.mCallingPackageName = string2;
        }

        @Override
        public void writeToParcel(Parcel parcel, int n) {
            parcel.writeString(this.mText.toString());
            parcel.writeParcelable(this.mDefaultLocales, n);
            parcel.writeParcelable(this.mEntityConfig, n);
            parcel.writeString(this.mCallingPackageName);
            parcel.writeBundle(this.mExtras);
        }

        public static final class Builder {
            private LocaleList mDefaultLocales;
            private TextClassifier.EntityConfig mEntityConfig;
            private Bundle mExtras;
            private boolean mLegacyFallback = true;
            private final CharSequence mText;

            public Builder(CharSequence charSequence) {
                this.mText = Preconditions.checkNotNull(charSequence);
            }

            public Request build() {
                Bundle bundle;
                CharSequence charSequence = this.mText;
                LocaleList localeList = this.mDefaultLocales;
                TextClassifier.EntityConfig entityConfig = this.mEntityConfig;
                boolean bl = this.mLegacyFallback;
                Bundle bundle2 = bundle = this.mExtras;
                if (bundle == null) {
                    bundle2 = Bundle.EMPTY;
                }
                return new Request(charSequence, localeList, entityConfig, bl, bundle2);
            }

            public Builder setDefaultLocales(LocaleList localeList) {
                this.mDefaultLocales = localeList;
                return this;
            }

            public Builder setEntityConfig(TextClassifier.EntityConfig entityConfig) {
                this.mEntityConfig = entityConfig;
                return this;
            }

            public Builder setExtras(Bundle bundle) {
                this.mExtras = bundle;
                return this;
            }

            public Builder setLegacyFallback(boolean bl) {
                this.mLegacyFallback = bl;
                return this;
            }
        }

    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface Status {
    }

    public static final class TextLink
    implements Parcelable {
        public static final Parcelable.Creator<TextLink> CREATOR = new Parcelable.Creator<TextLink>(){

            @Override
            public TextLink createFromParcel(Parcel parcel) {
                return TextLink.readFromParcel(parcel);
            }

            public TextLink[] newArray(int n) {
                return new TextLink[n];
            }
        };
        private final int mEnd;
        private final EntityConfidence mEntityScores;
        private final Bundle mExtras;
        private final int mStart;
        private final URLSpan mUrlSpan;

        private TextLink(int n, int n2, EntityConfidence entityConfidence, Bundle bundle, URLSpan uRLSpan) {
            Preconditions.checkNotNull(entityConfidence);
            boolean bl = entityConfidence.getEntities().isEmpty();
            boolean bl2 = true;
            Preconditions.checkArgument(bl ^ true);
            if (n > n2) {
                bl2 = false;
            }
            Preconditions.checkArgument(bl2);
            Preconditions.checkNotNull(bundle);
            this.mStart = n;
            this.mEnd = n2;
            this.mEntityScores = entityConfidence;
            this.mUrlSpan = uRLSpan;
            this.mExtras = bundle;
        }

        private static TextLink readFromParcel(Parcel parcel) {
            EntityConfidence entityConfidence = EntityConfidence.CREATOR.createFromParcel(parcel);
            return new TextLink(parcel.readInt(), parcel.readInt(), entityConfidence, parcel.readBundle(), null);
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public float getConfidenceScore(String string2) {
            return this.mEntityScores.getConfidenceScore(string2);
        }

        public int getEnd() {
            return this.mEnd;
        }

        public String getEntity(int n) {
            return this.mEntityScores.getEntities().get(n);
        }

        public int getEntityCount() {
            return this.mEntityScores.getEntities().size();
        }

        public Bundle getExtras() {
            return this.mExtras;
        }

        public int getStart() {
            return this.mStart;
        }

        public String toString() {
            return String.format(Locale.US, "TextLink{start=%s, end=%s, entityScores=%s, urlSpan=%s}", this.mStart, this.mEnd, this.mEntityScores, this.mUrlSpan);
        }

        @Override
        public void writeToParcel(Parcel parcel, int n) {
            this.mEntityScores.writeToParcel(parcel, n);
            parcel.writeInt(this.mStart);
            parcel.writeInt(this.mEnd);
            parcel.writeBundle(this.mExtras);
        }

    }

    public static class TextLinkSpan
    extends ClickableSpan {
        public static final int INVOCATION_METHOD_KEYBOARD = 1;
        public static final int INVOCATION_METHOD_TOUCH = 0;
        public static final int INVOCATION_METHOD_UNSPECIFIED = -1;
        private final TextLink mTextLink;

        public TextLinkSpan(TextLink textLink) {
            this.mTextLink = textLink;
        }

        public final TextLink getTextLink() {
            return this.mTextLink;
        }

        @VisibleForTesting(visibility=VisibleForTesting.Visibility.PRIVATE)
        public final String getUrl() {
            if (this.mTextLink.mUrlSpan != null) {
                return this.mTextLink.mUrlSpan.getURL();
            }
            return null;
        }

        @Override
        public void onClick(View view) {
            this.onClick(view, -1);
        }

        public final void onClick(View view, int n) {
            if (view instanceof TextView) {
                if (TextClassificationManager.getSettings((view = (TextView)view).getContext()).isSmartLinkifyEnabled()) {
                    if (n != 0) {
                        ((TextView)view).handleClick(this);
                    } else {
                        ((TextView)view).requestActionMode(this);
                    }
                } else if (this.mTextLink.mUrlSpan != null) {
                    this.mTextLink.mUrlSpan.onClick(view);
                } else {
                    ((TextView)view).handleClick(this);
                }
            }
        }

        @Retention(value=RetentionPolicy.SOURCE)
        public static @interface InvocationMethod {
        }

    }

}

