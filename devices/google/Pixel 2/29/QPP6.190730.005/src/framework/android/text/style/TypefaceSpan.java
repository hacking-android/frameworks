/*
 * Decompiled with CFR 0.145.
 */
package android.text.style;

import android.graphics.LeakyTypefaceStorage;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Parcel;
import android.text.ParcelableSpan;
import android.text.TextPaint;
import android.text.style.MetricAffectingSpan;

public class TypefaceSpan
extends MetricAffectingSpan
implements ParcelableSpan {
    private final String mFamily;
    private final Typeface mTypeface;

    public TypefaceSpan(Typeface typeface) {
        this(null, typeface);
    }

    public TypefaceSpan(Parcel parcel) {
        this.mFamily = parcel.readString();
        this.mTypeface = LeakyTypefaceStorage.readTypefaceFromParcel(parcel);
    }

    public TypefaceSpan(String string2) {
        this(string2, null);
    }

    private TypefaceSpan(String string2, Typeface typeface) {
        this.mFamily = string2;
        this.mTypeface = typeface;
    }

    private void applyFontFamily(Paint paint, String object) {
        Typeface typeface = paint.getTypeface();
        int n = typeface == null ? 0 : typeface.getStyle();
        object = Typeface.create((String)object, n);
        n = ((Typeface)object).getStyle() & n;
        if ((n & 1) != 0) {
            paint.setFakeBoldText(true);
        }
        if ((n & 2) != 0) {
            paint.setTextSkewX(-0.25f);
        }
        paint.setTypeface((Typeface)object);
    }

    private void updateTypeface(Paint paint) {
        Object object = this.mTypeface;
        if (object != null) {
            paint.setTypeface((Typeface)object);
        } else {
            object = this.mFamily;
            if (object != null) {
                this.applyFontFamily(paint, (String)object);
            }
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public String getFamily() {
        return this.mFamily;
    }

    @Override
    public int getSpanTypeId() {
        return this.getSpanTypeIdInternal();
    }

    @Override
    public int getSpanTypeIdInternal() {
        return 13;
    }

    public Typeface getTypeface() {
        return this.mTypeface;
    }

    @Override
    public void updateDrawState(TextPaint textPaint) {
        this.updateTypeface(textPaint);
    }

    @Override
    public void updateMeasureState(TextPaint textPaint) {
        this.updateTypeface(textPaint);
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        this.writeToParcelInternal(parcel, n);
    }

    @Override
    public void writeToParcelInternal(Parcel parcel, int n) {
        parcel.writeString(this.mFamily);
        LeakyTypefaceStorage.writeTypefaceToParcel(this.mTypeface, parcel);
    }
}

