/*
 * Decompiled with CFR 0.145.
 */
package android.text.style;

import android.annotation.UnsupportedAppUsage;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.SystemClock;
import android.text.ParcelableSpan;
import android.text.TextPaint;
import android.text.style.CharacterStyle;
import android.util.AttributeSet;
import android.util.Log;
import com.android.internal.R;
import java.util.Arrays;
import java.util.Locale;

public class SuggestionSpan
extends CharacterStyle
implements ParcelableSpan {
    @Deprecated
    public static final String ACTION_SUGGESTION_PICKED = "android.text.style.SUGGESTION_PICKED";
    public static final Parcelable.Creator<SuggestionSpan> CREATOR = new Parcelable.Creator<SuggestionSpan>(){

        @Override
        public SuggestionSpan createFromParcel(Parcel parcel) {
            return new SuggestionSpan(parcel);
        }

        public SuggestionSpan[] newArray(int n) {
            return new SuggestionSpan[n];
        }
    };
    public static final int FLAG_AUTO_CORRECTION = 4;
    public static final int FLAG_EASY_CORRECT = 1;
    public static final int FLAG_MISSPELLED = 2;
    public static final int SUGGESTIONS_MAX_SIZE = 5;
    @Deprecated
    public static final String SUGGESTION_SPAN_PICKED_AFTER = "after";
    @Deprecated
    public static final String SUGGESTION_SPAN_PICKED_BEFORE = "before";
    @Deprecated
    public static final String SUGGESTION_SPAN_PICKED_HASHCODE = "hashcode";
    private static final String TAG = "SuggestionSpan";
    private int mAutoCorrectionUnderlineColor;
    private float mAutoCorrectionUnderlineThickness;
    @UnsupportedAppUsage
    private int mEasyCorrectUnderlineColor;
    @UnsupportedAppUsage
    private float mEasyCorrectUnderlineThickness;
    private int mFlags;
    private final int mHashCode;
    private final String mLanguageTag;
    private final String mLocaleStringForCompatibility;
    private int mMisspelledUnderlineColor;
    private float mMisspelledUnderlineThickness;
    private final String[] mSuggestions;

    public SuggestionSpan(Context context, Locale object, String[] object2, int n, Class<?> object3) {
        this.mSuggestions = Arrays.copyOf(object2, Math.min(5, ((String[])object2).length));
        this.mFlags = n;
        if (object == null) {
            if (context != null) {
                object = context.getResources().getConfiguration().locale;
            } else {
                Log.e(TAG, "No locale or context specified in SuggestionSpan constructor");
                object = null;
            }
        }
        object3 = "";
        object2 = object == null ? "" : ((Locale)object).toString();
        this.mLocaleStringForCompatibility = object2;
        object = object == null ? object3 : ((Locale)object).toLanguageTag();
        this.mLanguageTag = object;
        this.mHashCode = SuggestionSpan.hashCodeInternal(this.mSuggestions, this.mLanguageTag, this.mLocaleStringForCompatibility);
        this.initStyle(context);
    }

    public SuggestionSpan(Context context, String[] arrstring, int n) {
        this(context, null, arrstring, n, null);
    }

    public SuggestionSpan(Parcel parcel) {
        this.mSuggestions = parcel.readStringArray();
        this.mFlags = parcel.readInt();
        this.mLocaleStringForCompatibility = parcel.readString();
        this.mLanguageTag = parcel.readString();
        this.mHashCode = parcel.readInt();
        this.mEasyCorrectUnderlineColor = parcel.readInt();
        this.mEasyCorrectUnderlineThickness = parcel.readFloat();
        this.mMisspelledUnderlineColor = parcel.readInt();
        this.mMisspelledUnderlineThickness = parcel.readFloat();
        this.mAutoCorrectionUnderlineColor = parcel.readInt();
        this.mAutoCorrectionUnderlineThickness = parcel.readFloat();
    }

    public SuggestionSpan(Locale locale, String[] arrstring, int n) {
        this(null, locale, arrstring, n, null);
    }

    private static int hashCodeInternal(String[] arrstring, String string2, String string3) {
        return Arrays.hashCode(new Object[]{SystemClock.uptimeMillis(), arrstring, string2, string3});
    }

    private void initStyle(Context object) {
        if (object == null) {
            this.mMisspelledUnderlineThickness = 0.0f;
            this.mEasyCorrectUnderlineThickness = 0.0f;
            this.mAutoCorrectionUnderlineThickness = 0.0f;
            this.mMisspelledUnderlineColor = -16777216;
            this.mEasyCorrectUnderlineColor = -16777216;
            this.mAutoCorrectionUnderlineColor = -16777216;
            return;
        }
        TypedArray typedArray = ((Context)object).obtainStyledAttributes(null, R.styleable.SuggestionSpan, 17957085, 0);
        this.mMisspelledUnderlineThickness = typedArray.getDimension(1, 0.0f);
        this.mMisspelledUnderlineColor = typedArray.getColor(0, -16777216);
        typedArray = ((Context)object).obtainStyledAttributes(null, R.styleable.SuggestionSpan, 17957084, 0);
        this.mEasyCorrectUnderlineThickness = typedArray.getDimension(1, 0.0f);
        this.mEasyCorrectUnderlineColor = typedArray.getColor(0, -16777216);
        object = ((Context)object).obtainStyledAttributes(null, R.styleable.SuggestionSpan, 17957083, 0);
        this.mAutoCorrectionUnderlineThickness = ((TypedArray)object).getDimension(1, 0.0f);
        this.mAutoCorrectionUnderlineColor = ((TypedArray)object).getColor(0, -16777216);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public boolean equals(Object object) {
        boolean bl = object instanceof SuggestionSpan;
        boolean bl2 = false;
        if (bl) {
            if (((SuggestionSpan)object).hashCode() == this.mHashCode) {
                bl2 = true;
            }
            return bl2;
        }
        return false;
    }

    public int getFlags() {
        return this.mFlags;
    }

    @Deprecated
    public String getLocale() {
        return this.mLocaleStringForCompatibility;
    }

    public Locale getLocaleObject() {
        Locale locale = this.mLanguageTag.isEmpty() ? null : Locale.forLanguageTag(this.mLanguageTag);
        return locale;
    }

    @Deprecated
    @UnsupportedAppUsage
    public String getNotificationTargetClassName() {
        return null;
    }

    @Override
    public int getSpanTypeId() {
        return this.getSpanTypeIdInternal();
    }

    @Override
    public int getSpanTypeIdInternal() {
        return 19;
    }

    public String[] getSuggestions() {
        return this.mSuggestions;
    }

    public int getUnderlineColor() {
        int n = this.mFlags;
        boolean bl = true;
        n = (n & 2) != 0 ? 1 : 0;
        boolean bl2 = (this.mFlags & 1) != 0;
        if ((this.mFlags & 4) == 0) {
            bl = false;
        }
        if (bl2) {
            if (n == 0) {
                return this.mEasyCorrectUnderlineColor;
            }
            return this.mMisspelledUnderlineColor;
        }
        if (bl) {
            return this.mAutoCorrectionUnderlineColor;
        }
        return 0;
    }

    public int hashCode() {
        return this.mHashCode;
    }

    @Deprecated
    @UnsupportedAppUsage
    public void notifySelection(Context context, String string2, int n) {
        Log.w(TAG, "notifySelection() is deprecated.  Does nothing.");
    }

    public void setFlags(int n) {
        this.mFlags = n;
    }

    @Override
    public void updateDrawState(TextPaint textPaint) {
        int n = this.mFlags;
        boolean bl = false;
        n = (n & 2) != 0 ? 1 : 0;
        boolean bl2 = (this.mFlags & 1) != 0;
        if ((this.mFlags & 4) != 0) {
            bl = true;
        }
        if (bl2) {
            if (n == 0) {
                textPaint.setUnderlineText(this.mEasyCorrectUnderlineColor, this.mEasyCorrectUnderlineThickness);
            } else if (textPaint.underlineColor == 0) {
                textPaint.setUnderlineText(this.mMisspelledUnderlineColor, this.mMisspelledUnderlineThickness);
            }
        } else if (bl) {
            textPaint.setUnderlineText(this.mAutoCorrectionUnderlineColor, this.mAutoCorrectionUnderlineThickness);
        }
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        this.writeToParcelInternal(parcel, n);
    }

    @Override
    public void writeToParcelInternal(Parcel parcel, int n) {
        parcel.writeStringArray(this.mSuggestions);
        parcel.writeInt(this.mFlags);
        parcel.writeString(this.mLocaleStringForCompatibility);
        parcel.writeString(this.mLanguageTag);
        parcel.writeInt(this.mHashCode);
        parcel.writeInt(this.mEasyCorrectUnderlineColor);
        parcel.writeFloat(this.mEasyCorrectUnderlineThickness);
        parcel.writeInt(this.mMisspelledUnderlineColor);
        parcel.writeFloat(this.mMisspelledUnderlineThickness);
        parcel.writeInt(this.mAutoCorrectionUnderlineColor);
        parcel.writeFloat(this.mAutoCorrectionUnderlineThickness);
    }

}

