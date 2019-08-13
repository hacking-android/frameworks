/*
 * Decompiled with CFR 0.145.
 */
package android.view.textclassifier;

import android.os.Bundle;
import android.os.LocaleList;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.SpannedString;
import android.util.ArrayMap;
import android.view.textclassifier.EntityConfidence;
import android.view.textclassifier.TextClassifier;
import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.util.Preconditions;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public final class TextSelection
implements Parcelable {
    public static final Parcelable.Creator<TextSelection> CREATOR = new Parcelable.Creator<TextSelection>(){

        @Override
        public TextSelection createFromParcel(Parcel parcel) {
            return new TextSelection(parcel);
        }

        public TextSelection[] newArray(int n) {
            return new TextSelection[n];
        }
    };
    private final int mEndIndex;
    private final EntityConfidence mEntityConfidence;
    private final Bundle mExtras;
    private final String mId;
    private final int mStartIndex;

    private TextSelection(int n, int n2, Map<String, Float> map, String string2, Bundle bundle) {
        this.mStartIndex = n;
        this.mEndIndex = n2;
        this.mEntityConfidence = new EntityConfidence(map);
        this.mId = string2;
        this.mExtras = bundle;
    }

    private TextSelection(Parcel parcel) {
        this.mStartIndex = parcel.readInt();
        this.mEndIndex = parcel.readInt();
        this.mEntityConfidence = EntityConfidence.CREATOR.createFromParcel(parcel);
        this.mId = parcel.readString();
        this.mExtras = parcel.readBundle();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public float getConfidenceScore(String string2) {
        return this.mEntityConfidence.getConfidenceScore(string2);
    }

    public String getEntity(int n) {
        return this.mEntityConfidence.getEntities().get(n);
    }

    public int getEntityCount() {
        return this.mEntityConfidence.getEntities().size();
    }

    public Bundle getExtras() {
        return this.mExtras;
    }

    public String getId() {
        return this.mId;
    }

    public int getSelectionEndIndex() {
        return this.mEndIndex;
    }

    public int getSelectionStartIndex() {
        return this.mStartIndex;
    }

    public String toString() {
        return String.format(Locale.US, "TextSelection {id=%s, startIndex=%d, endIndex=%d, entities=%s}", this.mId, this.mStartIndex, this.mEndIndex, this.mEntityConfidence);
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeInt(this.mStartIndex);
        parcel.writeInt(this.mEndIndex);
        this.mEntityConfidence.writeToParcel(parcel, n);
        parcel.writeString(this.mId);
        parcel.writeBundle(this.mExtras);
    }

    public static final class Builder {
        private final int mEndIndex;
        private final Map<String, Float> mEntityConfidence = new ArrayMap<String, Float>();
        private Bundle mExtras;
        private String mId;
        private final int mStartIndex;

        public Builder(int n, int n2) {
            boolean bl = true;
            boolean bl2 = n >= 0;
            Preconditions.checkArgument(bl2);
            bl2 = n2 > n ? bl : false;
            Preconditions.checkArgument(bl2);
            this.mStartIndex = n;
            this.mEndIndex = n2;
        }

        public TextSelection build() {
            Bundle bundle;
            int n = this.mStartIndex;
            int n2 = this.mEndIndex;
            Map<String, Float> map = this.mEntityConfidence;
            String string2 = this.mId;
            Bundle bundle2 = bundle = this.mExtras;
            if (bundle == null) {
                bundle2 = Bundle.EMPTY;
            }
            return new TextSelection(n, n2, map, string2, bundle2);
        }

        public Builder setEntityType(String string2, float f) {
            Preconditions.checkNotNull(string2);
            this.mEntityConfidence.put(string2, Float.valueOf(f));
            return this;
        }

        public Builder setExtras(Bundle bundle) {
            this.mExtras = bundle;
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
        private final boolean mDarkLaunchAllowed;
        private final LocaleList mDefaultLocales;
        private final int mEndIndex;
        private final Bundle mExtras;
        private final int mStartIndex;
        private final CharSequence mText;

        private Request(CharSequence charSequence, int n, int n2, LocaleList localeList, boolean bl, Bundle bundle) {
            this.mText = charSequence;
            this.mStartIndex = n;
            this.mEndIndex = n2;
            this.mDefaultLocales = localeList;
            this.mDarkLaunchAllowed = bl;
            this.mExtras = bundle;
        }

        private static Request readFromParcel(Parcel object) {
            CharSequence charSequence = ((Parcel)object).readCharSequence();
            int n = ((Parcel)object).readInt();
            int n2 = ((Parcel)object).readInt();
            LocaleList localeList = (LocaleList)((Parcel)object).readParcelable(null);
            String string2 = ((Parcel)object).readString();
            object = new Request(charSequence, n, n2, localeList, false, ((Parcel)object).readBundle());
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

        public LocaleList getDefaultLocales() {
            return this.mDefaultLocales;
        }

        public int getEndIndex() {
            return this.mEndIndex;
        }

        public Bundle getExtras() {
            return this.mExtras;
        }

        public int getStartIndex() {
            return this.mStartIndex;
        }

        public CharSequence getText() {
            return this.mText;
        }

        public boolean isDarkLaunchAllowed() {
            return this.mDarkLaunchAllowed;
        }

        @VisibleForTesting(visibility=VisibleForTesting.Visibility.PACKAGE)
        public void setCallingPackageName(String string2) {
            this.mCallingPackageName = string2;
        }

        @Override
        public void writeToParcel(Parcel parcel, int n) {
            parcel.writeCharSequence(this.mText);
            parcel.writeInt(this.mStartIndex);
            parcel.writeInt(this.mEndIndex);
            parcel.writeParcelable(this.mDefaultLocales, n);
            parcel.writeString(this.mCallingPackageName);
            parcel.writeBundle(this.mExtras);
        }

        public static final class Builder {
            private boolean mDarkLaunchAllowed;
            private LocaleList mDefaultLocales;
            private final int mEndIndex;
            private Bundle mExtras;
            private final int mStartIndex;
            private final CharSequence mText;

            public Builder(CharSequence charSequence, int n, int n2) {
                TextClassifier.Utils.checkArgument(charSequence, n, n2);
                this.mText = charSequence;
                this.mStartIndex = n;
                this.mEndIndex = n2;
            }

            public Request build() {
                Bundle bundle;
                SpannedString spannedString = new SpannedString(this.mText);
                int n = this.mStartIndex;
                int n2 = this.mEndIndex;
                LocaleList localeList = this.mDefaultLocales;
                boolean bl = this.mDarkLaunchAllowed;
                Bundle bundle2 = bundle = this.mExtras;
                if (bundle == null) {
                    bundle2 = Bundle.EMPTY;
                }
                return new Request(spannedString, n, n2, localeList, bl, bundle2);
            }

            public Builder setDarkLaunchAllowed(boolean bl) {
                this.mDarkLaunchAllowed = bl;
                return this;
            }

            public Builder setDefaultLocales(LocaleList localeList) {
                this.mDefaultLocales = localeList;
                return this;
            }

            public Builder setExtras(Bundle bundle) {
                this.mExtras = bundle;
                return this;
            }
        }

    }

}

