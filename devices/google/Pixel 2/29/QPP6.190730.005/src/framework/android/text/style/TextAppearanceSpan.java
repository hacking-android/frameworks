/*
 * Decompiled with CFR 0.145.
 */
package android.text.style;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.LeakyTypefaceStorage;
import android.graphics.Typeface;
import android.os.LocaleList;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.ParcelableSpan;
import android.text.TextPaint;
import android.text.style.MetricAffectingSpan;
import com.android.internal.R;

public class TextAppearanceSpan
extends MetricAffectingSpan
implements ParcelableSpan {
    private final boolean mElegantTextHeight;
    private final String mFamilyName;
    private final String mFontFeatureSettings;
    private final String mFontVariationSettings;
    private final boolean mHasElegantTextHeight;
    private final boolean mHasLetterSpacing;
    private final float mLetterSpacing;
    private final int mShadowColor;
    private final float mShadowDx;
    private final float mShadowDy;
    private final float mShadowRadius;
    private final int mStyle;
    private final ColorStateList mTextColor;
    private final ColorStateList mTextColorLink;
    private final int mTextFontWeight;
    private final LocaleList mTextLocales;
    private final int mTextSize;
    private final Typeface mTypeface;

    public TextAppearanceSpan(Context context, int n) {
        this(context, n, -1);
    }

    public TextAppearanceSpan(Context object, int n, int n2) {
        Object object2;
        TypedArray typedArray = ((Context)object).obtainStyledAttributes(n, R.styleable.TextAppearance);
        ColorStateList colorStateList = typedArray.getColorStateList(3);
        this.mTextColorLink = typedArray.getColorStateList(6);
        this.mTextSize = typedArray.getDimensionPixelSize(0, -1);
        this.mStyle = typedArray.getInt(2, 0);
        this.mTypeface = !((Context)object).isRestricted() && ((Context)object).canLoadUnsafeResources() ? typedArray.getFont(12) : null;
        this.mFamilyName = this.mTypeface != null ? null : ((object2 = typedArray.getString(12)) != null ? object2 : ((n = typedArray.getInt(1, 0)) != 1 ? (n != 2 ? (n != 3 ? null : "monospace") : "serif") : "sans"));
        this.mTextFontWeight = typedArray.getInt(18, -1);
        object2 = typedArray.getString(19);
        this.mTextLocales = object2 != null ? (!((LocaleList)(object2 = LocaleList.forLanguageTags((String)object2))).isEmpty() ? object2 : null) : null;
        this.mShadowRadius = typedArray.getFloat(10, 0.0f);
        this.mShadowDx = typedArray.getFloat(8, 0.0f);
        this.mShadowDy = typedArray.getFloat(9, 0.0f);
        this.mShadowColor = typedArray.getInt(7, 0);
        this.mHasElegantTextHeight = typedArray.hasValue(13);
        this.mElegantTextHeight = typedArray.getBoolean(13, false);
        this.mHasLetterSpacing = typedArray.hasValue(14);
        this.mLetterSpacing = typedArray.getFloat(14, 0.0f);
        this.mFontFeatureSettings = typedArray.getString(15);
        this.mFontVariationSettings = typedArray.getString(16);
        typedArray.recycle();
        if (n2 >= 0) {
            object = ((Context)object).obtainStyledAttributes(16973829, R.styleable.Theme);
            colorStateList = ((TypedArray)object).getColorStateList(n2);
            ((TypedArray)object).recycle();
        }
        this.mTextColor = colorStateList;
    }

    public TextAppearanceSpan(Parcel parcel) {
        this.mFamilyName = parcel.readString();
        this.mStyle = parcel.readInt();
        this.mTextSize = parcel.readInt();
        this.mTextColor = parcel.readInt() != 0 ? ColorStateList.CREATOR.createFromParcel(parcel) : null;
        this.mTextColorLink = parcel.readInt() != 0 ? ColorStateList.CREATOR.createFromParcel(parcel) : null;
        this.mTypeface = LeakyTypefaceStorage.readTypefaceFromParcel(parcel);
        this.mTextFontWeight = parcel.readInt();
        this.mTextLocales = (LocaleList)parcel.readParcelable(LocaleList.class.getClassLoader());
        this.mShadowRadius = parcel.readFloat();
        this.mShadowDx = parcel.readFloat();
        this.mShadowDy = parcel.readFloat();
        this.mShadowColor = parcel.readInt();
        this.mHasElegantTextHeight = parcel.readBoolean();
        this.mElegantTextHeight = parcel.readBoolean();
        this.mHasLetterSpacing = parcel.readBoolean();
        this.mLetterSpacing = parcel.readFloat();
        this.mFontFeatureSettings = parcel.readString();
        this.mFontVariationSettings = parcel.readString();
    }

    public TextAppearanceSpan(String string2, int n, int n2, ColorStateList colorStateList, ColorStateList colorStateList2) {
        this.mFamilyName = string2;
        this.mStyle = n;
        this.mTextSize = n2;
        this.mTextColor = colorStateList;
        this.mTextColorLink = colorStateList2;
        this.mTypeface = null;
        this.mTextFontWeight = -1;
        this.mTextLocales = null;
        this.mShadowRadius = 0.0f;
        this.mShadowDx = 0.0f;
        this.mShadowDy = 0.0f;
        this.mShadowColor = 0;
        this.mHasElegantTextHeight = false;
        this.mElegantTextHeight = false;
        this.mHasLetterSpacing = false;
        this.mLetterSpacing = 0.0f;
        this.mFontFeatureSettings = null;
        this.mFontVariationSettings = null;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public String getFamily() {
        return this.mFamilyName;
    }

    public String getFontFeatureSettings() {
        return this.mFontFeatureSettings;
    }

    public String getFontVariationSettings() {
        return this.mFontVariationSettings;
    }

    public ColorStateList getLinkTextColor() {
        return this.mTextColorLink;
    }

    public int getShadowColor() {
        return this.mShadowColor;
    }

    public float getShadowDx() {
        return this.mShadowDx;
    }

    public float getShadowDy() {
        return this.mShadowDy;
    }

    public float getShadowRadius() {
        return this.mShadowRadius;
    }

    @Override
    public int getSpanTypeId() {
        return this.getSpanTypeIdInternal();
    }

    @Override
    public int getSpanTypeIdInternal() {
        return 17;
    }

    public ColorStateList getTextColor() {
        return this.mTextColor;
    }

    public int getTextFontWeight() {
        return this.mTextFontWeight;
    }

    public LocaleList getTextLocales() {
        return this.mTextLocales;
    }

    public int getTextSize() {
        return this.mTextSize;
    }

    public int getTextStyle() {
        return this.mStyle;
    }

    public Typeface getTypeface() {
        return this.mTypeface;
    }

    public boolean isElegantTextHeight() {
        return this.mElegantTextHeight;
    }

    @Override
    public void updateDrawState(TextPaint textPaint) {
        int n;
        this.updateMeasureState(textPaint);
        ColorStateList colorStateList = this.mTextColor;
        if (colorStateList != null) {
            textPaint.setColor(colorStateList.getColorForState(textPaint.drawableState, 0));
        }
        if ((colorStateList = this.mTextColorLink) != null) {
            textPaint.linkColor = colorStateList.getColorForState(textPaint.drawableState, 0);
        }
        if ((n = this.mShadowColor) != 0) {
            textPaint.setShadowLayer(this.mShadowRadius, this.mShadowDx, this.mShadowDy, n);
        }
    }

    @Override
    public void updateMeasureState(TextPaint textPaint) {
        int n = 0;
        int n2 = 0;
        Object object = this.mTypeface;
        if (object != null) {
            n2 = this.mStyle;
            object = Typeface.create((Typeface)object, n2);
        } else if (this.mFamilyName == null && this.mStyle == 0) {
            object = null;
            n2 = n;
        } else {
            Typeface typeface = textPaint.getTypeface();
            if (typeface != null) {
                n2 = typeface.getStyle();
            }
            n2 |= this.mStyle;
            object = this.mFamilyName;
            object = object != null ? Typeface.create((String)object, n2) : (typeface == null ? Typeface.defaultFromStyle(n2) : Typeface.create(typeface, n2));
        }
        if (object != null) {
            n = this.mTextFontWeight;
            if (n >= 0) {
                n = Math.min(1000, n);
                boolean bl = (n2 & 2) != 0;
                object = textPaint.setTypeface(Typeface.create((Typeface)object, n, bl));
            }
            if (((n2 = ((Typeface)object).getStyle() & n2) & 1) != 0) {
                textPaint.setFakeBoldText(true);
            }
            if ((n2 & 2) != 0) {
                textPaint.setTextSkewX(-0.25f);
            }
            textPaint.setTypeface((Typeface)object);
        }
        if ((n2 = this.mTextSize) > 0) {
            textPaint.setTextSize(n2);
        }
        if ((object = this.mTextLocales) != null) {
            textPaint.setTextLocales((LocaleList)object);
        }
        if (this.mHasElegantTextHeight) {
            textPaint.setElegantTextHeight(this.mElegantTextHeight);
        }
        if (this.mHasLetterSpacing) {
            textPaint.setLetterSpacing(this.mLetterSpacing);
        }
        if ((object = this.mFontFeatureSettings) != null) {
            textPaint.setFontFeatureSettings((String)object);
        }
        if ((object = this.mFontVariationSettings) != null) {
            textPaint.setFontVariationSettings((String)object);
        }
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        this.writeToParcelInternal(parcel, n);
    }

    @Override
    public void writeToParcelInternal(Parcel parcel, int n) {
        parcel.writeString(this.mFamilyName);
        parcel.writeInt(this.mStyle);
        parcel.writeInt(this.mTextSize);
        if (this.mTextColor != null) {
            parcel.writeInt(1);
            this.mTextColor.writeToParcel(parcel, n);
        } else {
            parcel.writeInt(0);
        }
        if (this.mTextColorLink != null) {
            parcel.writeInt(1);
            this.mTextColorLink.writeToParcel(parcel, n);
        } else {
            parcel.writeInt(0);
        }
        LeakyTypefaceStorage.writeTypefaceToParcel(this.mTypeface, parcel);
        parcel.writeInt(this.mTextFontWeight);
        parcel.writeParcelable(this.mTextLocales, n);
        parcel.writeFloat(this.mShadowRadius);
        parcel.writeFloat(this.mShadowDx);
        parcel.writeFloat(this.mShadowDy);
        parcel.writeInt(this.mShadowColor);
        parcel.writeBoolean(this.mHasElegantTextHeight);
        parcel.writeBoolean(this.mElegantTextHeight);
        parcel.writeBoolean(this.mHasLetterSpacing);
        parcel.writeFloat(this.mLetterSpacing);
        parcel.writeString(this.mFontFeatureSettings);
        parcel.writeString(this.mFontVariationSettings);
    }
}

