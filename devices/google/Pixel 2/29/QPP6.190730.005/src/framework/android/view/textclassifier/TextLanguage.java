/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.icu.util.ULocale
 */
package android.view.textclassifier;

import android.icu.util.ULocale;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.ArrayMap;
import android.view.textclassifier.EntityConfidence;
import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.util.Preconditions;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public final class TextLanguage
implements Parcelable {
    public static final Parcelable.Creator<TextLanguage> CREATOR = new Parcelable.Creator<TextLanguage>(){

        @Override
        public TextLanguage createFromParcel(Parcel parcel) {
            return TextLanguage.readFromParcel(parcel);
        }

        public TextLanguage[] newArray(int n) {
            return new TextLanguage[n];
        }
    };
    static final TextLanguage EMPTY = new Builder().build();
    private final Bundle mBundle;
    private final EntityConfidence mEntityConfidence;
    private final String mId;

    private TextLanguage(String string2, EntityConfidence entityConfidence, Bundle bundle) {
        this.mId = string2;
        this.mEntityConfidence = entityConfidence;
        this.mBundle = bundle;
    }

    private static TextLanguage readFromParcel(Parcel parcel) {
        return new TextLanguage(parcel.readString(), EntityConfidence.CREATOR.createFromParcel(parcel), parcel.readBundle());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public float getConfidenceScore(ULocale uLocale) {
        return this.mEntityConfidence.getConfidenceScore(uLocale.toLanguageTag());
    }

    public Bundle getExtras() {
        return this.mBundle;
    }

    public String getId() {
        return this.mId;
    }

    public ULocale getLocale(int n) {
        return ULocale.forLanguageTag((String)this.mEntityConfidence.getEntities().get(n));
    }

    public int getLocaleHypothesisCount() {
        return this.mEntityConfidence.getEntities().size();
    }

    public String toString() {
        return String.format(Locale.US, "TextLanguage {id=%s, locales=%s, bundle=%s}", this.mId, this.mEntityConfidence, this.mBundle);
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeString(this.mId);
        this.mEntityConfidence.writeToParcel(parcel, n);
        parcel.writeBundle(this.mBundle);
    }

    public static final class Builder {
        private Bundle mBundle;
        private final Map<String, Float> mEntityConfidenceMap = new ArrayMap<String, Float>();
        private String mId;

        public TextLanguage build() {
            Bundle bundle;
            Bundle bundle2 = bundle = this.mBundle;
            if (bundle == null) {
                bundle2 = Bundle.EMPTY;
            }
            this.mBundle = bundle2;
            return new TextLanguage(this.mId, new EntityConfidence(this.mEntityConfidenceMap), this.mBundle);
        }

        public Builder putLocale(ULocale uLocale, float f) {
            Preconditions.checkNotNull(uLocale);
            this.mEntityConfidenceMap.put(uLocale.toLanguageTag(), Float.valueOf(f));
            return this;
        }

        public Builder setExtras(Bundle bundle) {
            this.mBundle = Preconditions.checkNotNull(bundle);
            return this;
        }

        public Builder setId(String string2) {
            this.mId = string2;
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
        private final Bundle mExtra;
        private final CharSequence mText;

        private Request(CharSequence charSequence, Bundle bundle) {
            this.mText = charSequence;
            this.mExtra = bundle;
        }

        private static Request readFromParcel(Parcel object) {
            CharSequence charSequence = ((Parcel)object).readCharSequence();
            String string2 = ((Parcel)object).readString();
            object = new Request(charSequence, ((Parcel)object).readBundle());
            ((Request)object).setCallingPackageName(string2);
            return object;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public String getCallingPackageName() {
            return this.mCallingPackageName;
        }

        public Bundle getExtras() {
            return this.mExtra;
        }

        public CharSequence getText() {
            return this.mText;
        }

        @VisibleForTesting(visibility=VisibleForTesting.Visibility.PACKAGE)
        public void setCallingPackageName(String string2) {
            this.mCallingPackageName = string2;
        }

        @Override
        public void writeToParcel(Parcel parcel, int n) {
            parcel.writeCharSequence(this.mText);
            parcel.writeString(this.mCallingPackageName);
            parcel.writeBundle(this.mExtra);
        }

        public static final class Builder {
            private Bundle mBundle;
            private final CharSequence mText;

            public Builder(CharSequence charSequence) {
                this.mText = Preconditions.checkNotNull(charSequence);
            }

            public Request build() {
                Bundle bundle;
                String string2 = this.mText.toString();
                Bundle bundle2 = bundle = this.mBundle;
                if (bundle == null) {
                    bundle2 = Bundle.EMPTY;
                }
                return new Request(string2, bundle2);
            }

            public Builder setExtras(Bundle bundle) {
                this.mBundle = Preconditions.checkNotNull(bundle);
                return this;
            }
        }

    }

}

