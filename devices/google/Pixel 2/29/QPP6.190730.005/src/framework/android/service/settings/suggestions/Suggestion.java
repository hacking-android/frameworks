/*
 * Decompiled with CFR 0.145.
 */
package android.service.settings.suggestions;

import android.annotation.SystemApi;
import android.app.PendingIntent;
import android.graphics.drawable.Icon;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@SystemApi
public final class Suggestion
implements Parcelable {
    public static final Parcelable.Creator<Suggestion> CREATOR = new Parcelable.Creator<Suggestion>(){

        @Override
        public Suggestion createFromParcel(Parcel parcel) {
            return new Suggestion(parcel);
        }

        public Suggestion[] newArray(int n) {
            return new Suggestion[n];
        }
    };
    public static final int FLAG_HAS_BUTTON = 1;
    public static final int FLAG_ICON_TINTABLE = 2;
    private final int mFlags;
    private final Icon mIcon;
    private final String mId;
    private final PendingIntent mPendingIntent;
    private final CharSequence mSummary;
    private final CharSequence mTitle;

    private Suggestion(Parcel parcel) {
        this.mId = parcel.readString();
        this.mTitle = parcel.readCharSequence();
        this.mSummary = parcel.readCharSequence();
        this.mIcon = (Icon)parcel.readParcelable(Icon.class.getClassLoader());
        this.mFlags = parcel.readInt();
        this.mPendingIntent = (PendingIntent)parcel.readParcelable(PendingIntent.class.getClassLoader());
    }

    private Suggestion(Builder builder) {
        this.mId = builder.mId;
        this.mTitle = builder.mTitle;
        this.mSummary = builder.mSummary;
        this.mIcon = builder.mIcon;
        this.mFlags = builder.mFlags;
        this.mPendingIntent = builder.mPendingIntent;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public int getFlags() {
        return this.mFlags;
    }

    public Icon getIcon() {
        return this.mIcon;
    }

    public String getId() {
        return this.mId;
    }

    public PendingIntent getPendingIntent() {
        return this.mPendingIntent;
    }

    public CharSequence getSummary() {
        return this.mSummary;
    }

    public CharSequence getTitle() {
        return this.mTitle;
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeString(this.mId);
        parcel.writeCharSequence(this.mTitle);
        parcel.writeCharSequence(this.mSummary);
        parcel.writeParcelable(this.mIcon, n);
        parcel.writeInt(this.mFlags);
        parcel.writeParcelable(this.mPendingIntent, n);
    }

    public static class Builder {
        private int mFlags;
        private Icon mIcon;
        private final String mId;
        private PendingIntent mPendingIntent;
        private CharSequence mSummary;
        private CharSequence mTitle;

        public Builder(String string2) {
            if (!TextUtils.isEmpty(string2)) {
                this.mId = string2;
                return;
            }
            throw new IllegalArgumentException("Suggestion id cannot be empty");
        }

        public Suggestion build() {
            return new Suggestion(this);
        }

        public Builder setFlags(int n) {
            this.mFlags = n;
            return this;
        }

        public Builder setIcon(Icon icon) {
            this.mIcon = icon;
            return this;
        }

        public Builder setPendingIntent(PendingIntent pendingIntent) {
            this.mPendingIntent = pendingIntent;
            return this;
        }

        public Builder setSummary(CharSequence charSequence) {
            this.mSummary = charSequence;
            return this;
        }

        public Builder setTitle(CharSequence charSequence) {
            this.mTitle = charSequence;
            return this;
        }
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface Flags {
    }

}

