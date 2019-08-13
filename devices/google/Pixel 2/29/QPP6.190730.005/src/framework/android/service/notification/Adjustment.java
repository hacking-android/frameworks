/*
 * Decompiled with CFR 0.145.
 */
package android.service.notification;

import android.annotation.SystemApi;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.UserHandle;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@SystemApi
public final class Adjustment
implements Parcelable {
    public static final Parcelable.Creator<Adjustment> CREATOR = new Parcelable.Creator<Adjustment>(){

        @Override
        public Adjustment createFromParcel(Parcel parcel) {
            return new Adjustment(parcel);
        }

        public Adjustment[] newArray(int n) {
            return new Adjustment[n];
        }
    };
    public static final String KEY_CONTEXTUAL_ACTIONS = "key_contextual_actions";
    public static final String KEY_GROUP_KEY = "key_group_key";
    public static final String KEY_IMPORTANCE = "key_importance";
    @SystemApi
    public static final String KEY_PEOPLE = "key_people";
    public static final String KEY_SNOOZE_CRITERIA = "key_snooze_criteria";
    public static final String KEY_TEXT_REPLIES = "key_text_replies";
    public static final String KEY_USER_SENTIMENT = "key_user_sentiment";
    private final CharSequence mExplanation;
    private String mIssuer;
    private final String mKey;
    private final String mPackage;
    private final Bundle mSignals;
    private final int mUser;

    @SystemApi
    protected Adjustment(Parcel parcel) {
        this.mPackage = parcel.readInt() == 1 ? parcel.readString() : null;
        this.mKey = parcel.readInt() == 1 ? parcel.readString() : null;
        this.mExplanation = parcel.readInt() == 1 ? parcel.readCharSequence() : null;
        this.mSignals = parcel.readBundle();
        this.mUser = parcel.readInt();
        this.mIssuer = parcel.readString();
    }

    @SystemApi
    public Adjustment(String string2, String string3, Bundle bundle, CharSequence charSequence, int n) {
        this.mPackage = string2;
        this.mKey = string3;
        this.mSignals = bundle;
        this.mExplanation = charSequence;
        this.mUser = n;
    }

    public Adjustment(String string2, String string3, Bundle bundle, CharSequence charSequence, UserHandle userHandle) {
        this.mPackage = string2;
        this.mKey = string3;
        this.mSignals = bundle;
        this.mExplanation = charSequence;
        this.mUser = userHandle.getIdentifier();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public CharSequence getExplanation() {
        return this.mExplanation;
    }

    public String getIssuer() {
        return this.mIssuer;
    }

    public String getKey() {
        return this.mKey;
    }

    public String getPackage() {
        return this.mPackage;
    }

    public Bundle getSignals() {
        return this.mSignals;
    }

    @SystemApi
    public int getUser() {
        return this.mUser;
    }

    public UserHandle getUserHandle() {
        return UserHandle.of(this.mUser);
    }

    public void setIssuer(String string2) {
        this.mIssuer = string2;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Adjustment{mSignals=");
        stringBuilder.append(this.mSignals);
        stringBuilder.append('}');
        return stringBuilder.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        if (this.mPackage != null) {
            parcel.writeInt(1);
            parcel.writeString(this.mPackage);
        } else {
            parcel.writeInt(0);
        }
        if (this.mKey != null) {
            parcel.writeInt(1);
            parcel.writeString(this.mKey);
        } else {
            parcel.writeInt(0);
        }
        if (this.mExplanation != null) {
            parcel.writeInt(1);
            parcel.writeCharSequence(this.mExplanation);
        } else {
            parcel.writeInt(0);
        }
        parcel.writeBundle(this.mSignals);
        parcel.writeInt(this.mUser);
        parcel.writeString(this.mIssuer);
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface Keys {
    }

}

