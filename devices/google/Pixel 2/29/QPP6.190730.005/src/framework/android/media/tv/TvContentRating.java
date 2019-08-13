/*
 * Decompiled with CFR 0.145.
 */
package android.media.tv;

import android.text.TextUtils;
import com.android.internal.util.Preconditions;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public final class TvContentRating {
    private static final String DELIMITER = "/";
    public static final TvContentRating UNRATED = new TvContentRating("null", "null", "null", null);
    private final String mDomain;
    private final int mHashCode;
    private final String mRating;
    private final String mRatingSystem;
    private final String[] mSubRatings;

    private TvContentRating(String string2, String string3, String string4, String[] arrstring) {
        this.mDomain = string2;
        this.mRatingSystem = string3;
        this.mRating = string4;
        if (arrstring != null && arrstring.length != 0) {
            Arrays.sort(arrstring);
            this.mSubRatings = arrstring;
        } else {
            this.mSubRatings = null;
        }
        this.mHashCode = Objects.hash(this.mDomain, this.mRating) * 31 + Arrays.hashCode(this.mSubRatings);
    }

    public static TvContentRating createRating(String string2, String string3, String string4, String ... arrstring) {
        if (!TextUtils.isEmpty(string2)) {
            if (!TextUtils.isEmpty(string3)) {
                if (!TextUtils.isEmpty(string4)) {
                    return new TvContentRating(string2, string3, string4, arrstring);
                }
                throw new IllegalArgumentException("rating cannot be empty");
            }
            throw new IllegalArgumentException("ratingSystem cannot be empty");
        }
        throw new IllegalArgumentException("domain cannot be empty");
    }

    public static TvContentRating unflattenFromString(String arrstring) {
        if (!TextUtils.isEmpty((CharSequence)arrstring)) {
            Object object = arrstring.split(DELIMITER);
            if (((String[])object).length >= 3) {
                if (((String[])object).length > 3) {
                    arrstring = new String[((String[])object).length - 3];
                    System.arraycopy(object, 3, arrstring, 0, arrstring.length);
                    return new TvContentRating((String)object[0], (String)object[1], (String)object[2], arrstring);
                }
                return new TvContentRating((String)object[0], (String)object[1], (String)object[2], null);
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("Invalid rating string: ");
            ((StringBuilder)object).append((String)arrstring);
            throw new IllegalArgumentException(((StringBuilder)object).toString());
        }
        throw new IllegalArgumentException("ratingString cannot be empty");
    }

    public final boolean contains(TvContentRating object) {
        Preconditions.checkNotNull(object);
        if (!((TvContentRating)object).getMainRating().equals(this.mRating)) {
            return false;
        }
        if (((TvContentRating)object).getDomain().equals(this.mDomain) && ((TvContentRating)object).getRatingSystem().equals(this.mRatingSystem) && ((TvContentRating)object).getMainRating().equals(this.mRating)) {
            List<String> list = this.getSubRatings();
            object = ((TvContentRating)object).getSubRatings();
            if (list == null && object == null) {
                return true;
            }
            if (list == null && object != null) {
                return false;
            }
            if (list != null && object == null) {
                return true;
            }
            return list.containsAll((Collection<?>)object);
        }
        return false;
    }

    public boolean equals(Object object) {
        if (!(object instanceof TvContentRating)) {
            return false;
        }
        object = (TvContentRating)object;
        if (this.mHashCode != ((TvContentRating)object).mHashCode) {
            return false;
        }
        if (!TextUtils.equals(this.mDomain, ((TvContentRating)object).mDomain)) {
            return false;
        }
        if (!TextUtils.equals(this.mRatingSystem, ((TvContentRating)object).mRatingSystem)) {
            return false;
        }
        if (!TextUtils.equals(this.mRating, ((TvContentRating)object).mRating)) {
            return false;
        }
        return Arrays.equals(this.mSubRatings, ((TvContentRating)object).mSubRatings);
    }

    public String flattenToString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.mDomain);
        stringBuilder.append(DELIMITER);
        stringBuilder.append(this.mRatingSystem);
        stringBuilder.append(DELIMITER);
        stringBuilder.append(this.mRating);
        String[] arrstring = this.mSubRatings;
        if (arrstring != null) {
            for (String string2 : arrstring) {
                stringBuilder.append(DELIMITER);
                stringBuilder.append(string2);
            }
        }
        return stringBuilder.toString();
    }

    public String getDomain() {
        return this.mDomain;
    }

    public String getMainRating() {
        return this.mRating;
    }

    public String getRatingSystem() {
        return this.mRatingSystem;
    }

    public List<String> getSubRatings() {
        String[] arrstring = this.mSubRatings;
        if (arrstring == null) {
            return null;
        }
        return Collections.unmodifiableList(Arrays.asList(arrstring));
    }

    public int hashCode() {
        return this.mHashCode;
    }
}

