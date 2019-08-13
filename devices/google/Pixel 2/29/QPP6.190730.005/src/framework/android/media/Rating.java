/*
 * Decompiled with CFR 0.145.
 */
package android.media;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public final class Rating
implements Parcelable {
    public static final Parcelable.Creator<Rating> CREATOR = new Parcelable.Creator<Rating>(){

        @Override
        public Rating createFromParcel(Parcel parcel) {
            return new Rating(parcel.readInt(), parcel.readFloat());
        }

        public Rating[] newArray(int n) {
            return new Rating[n];
        }
    };
    public static final int RATING_3_STARS = 3;
    public static final int RATING_4_STARS = 4;
    public static final int RATING_5_STARS = 5;
    public static final int RATING_HEART = 1;
    public static final int RATING_NONE = 0;
    private static final float RATING_NOT_RATED = -1.0f;
    public static final int RATING_PERCENTAGE = 6;
    public static final int RATING_THUMB_UP_DOWN = 2;
    private static final String TAG = "Rating";
    private final int mRatingStyle;
    private final float mRatingValue;

    private Rating(int n, float f) {
        this.mRatingStyle = n;
        this.mRatingValue = f;
    }

    public static Rating newHeartRating(boolean bl) {
        float f = bl ? 1.0f : 0.0f;
        return new Rating(1, f);
    }

    public static Rating newPercentageRating(float f) {
        if (!(f < 0.0f) && !(f > 100.0f)) {
            return new Rating(6, f);
        }
        Log.e(TAG, "Invalid percentage-based rating value");
        return null;
    }

    public static Rating newStarRating(int n, float f) {
        float f2;
        if (n != 3) {
            if (n != 4) {
                if (n != 5) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Invalid rating style (");
                    stringBuilder.append(n);
                    stringBuilder.append(") for a star rating");
                    Log.e(TAG, stringBuilder.toString());
                    return null;
                }
                f2 = 5.0f;
            } else {
                f2 = 4.0f;
            }
        } else {
            f2 = 3.0f;
        }
        if (!(f < 0.0f) && !(f > f2)) {
            return new Rating(n, f);
        }
        Log.e(TAG, "Trying to set out of range star-based rating");
        return null;
    }

    public static Rating newThumbRating(boolean bl) {
        float f = bl ? 1.0f : 0.0f;
        return new Rating(2, f);
    }

    public static Rating newUnratedRating(int n) {
        switch (n) {
            default: {
                return null;
            }
            case 1: 
            case 2: 
            case 3: 
            case 4: 
            case 5: 
            case 6: 
        }
        return new Rating(n, -1.0f);
    }

    @Override
    public int describeContents() {
        return this.mRatingStyle;
    }

    public float getPercentRating() {
        if (this.mRatingStyle == 6 && this.isRated()) {
            return this.mRatingValue;
        }
        return -1.0f;
    }

    public int getRatingStyle() {
        return this.mRatingStyle;
    }

    public float getStarRating() {
        float f = -1.0f;
        int n = this.mRatingStyle;
        if ((n == 3 || n == 4 || n == 5) && this.isRated()) {
            f = this.mRatingValue;
        }
        return f;
    }

    public boolean hasHeart() {
        int n = this.mRatingStyle;
        boolean bl = false;
        if (n != 1) {
            return false;
        }
        if (this.mRatingValue == 1.0f) {
            bl = true;
        }
        return bl;
    }

    public boolean isRated() {
        boolean bl = this.mRatingValue >= 0.0f;
        return bl;
    }

    public boolean isThumbUp() {
        int n = this.mRatingStyle;
        boolean bl = false;
        if (n != 2) {
            return false;
        }
        if (this.mRatingValue == 1.0f) {
            bl = true;
        }
        return bl;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Rating:style=");
        stringBuilder.append(this.mRatingStyle);
        stringBuilder.append(" rating=");
        float f = this.mRatingValue;
        String string2 = f < 0.0f ? "unrated" : String.valueOf(f);
        stringBuilder.append(string2);
        return stringBuilder.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeInt(this.mRatingStyle);
        parcel.writeFloat(this.mRatingValue);
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface StarStyle {
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface Style {
    }

}

