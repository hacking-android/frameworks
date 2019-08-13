/*
 * Decompiled with CFR 0.145.
 */
package android.view.textservice;

import android.os.Parcel;
import android.os.Parcelable;
import com.android.internal.util.ArrayUtils;

public final class SuggestionsInfo
implements Parcelable {
    public static final Parcelable.Creator<SuggestionsInfo> CREATOR;
    private static final String[] EMPTY;
    public static final int RESULT_ATTR_HAS_RECOMMENDED_SUGGESTIONS = 4;
    public static final int RESULT_ATTR_IN_THE_DICTIONARY = 1;
    public static final int RESULT_ATTR_LOOKS_LIKE_TYPO = 2;
    private int mCookie;
    private int mSequence;
    private final String[] mSuggestions;
    private final int mSuggestionsAttributes;
    private final boolean mSuggestionsAvailable;

    static {
        EMPTY = ArrayUtils.emptyArray(String.class);
        CREATOR = new Parcelable.Creator<SuggestionsInfo>(){

            @Override
            public SuggestionsInfo createFromParcel(Parcel parcel) {
                return new SuggestionsInfo(parcel);
            }

            public SuggestionsInfo[] newArray(int n) {
                return new SuggestionsInfo[n];
            }
        };
    }

    public SuggestionsInfo(int n, String[] arrstring) {
        this(n, arrstring, 0, 0);
    }

    public SuggestionsInfo(int n, String[] arrstring, int n2, int n3) {
        if (arrstring == null) {
            this.mSuggestions = EMPTY;
            this.mSuggestionsAvailable = false;
        } else {
            this.mSuggestions = arrstring;
            this.mSuggestionsAvailable = true;
        }
        this.mSuggestionsAttributes = n;
        this.mCookie = n2;
        this.mSequence = n3;
    }

    public SuggestionsInfo(Parcel parcel) {
        this.mSuggestionsAttributes = parcel.readInt();
        this.mSuggestions = parcel.readStringArray();
        this.mCookie = parcel.readInt();
        this.mSequence = parcel.readInt();
        int n = parcel.readInt();
        boolean bl = true;
        if (n != 1) {
            bl = false;
        }
        this.mSuggestionsAvailable = bl;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public int getCookie() {
        return this.mCookie;
    }

    public int getSequence() {
        return this.mSequence;
    }

    public String getSuggestionAt(int n) {
        return this.mSuggestions[n];
    }

    public int getSuggestionsAttributes() {
        return this.mSuggestionsAttributes;
    }

    public int getSuggestionsCount() {
        if (!this.mSuggestionsAvailable) {
            return -1;
        }
        return this.mSuggestions.length;
    }

    public void setCookieAndSequence(int n, int n2) {
        this.mCookie = n;
        this.mSequence = n2;
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeInt(this.mSuggestionsAttributes);
        parcel.writeStringArray(this.mSuggestions);
        parcel.writeInt(this.mCookie);
        parcel.writeInt(this.mSequence);
        parcel.writeInt((int)this.mSuggestionsAvailable);
    }

}

