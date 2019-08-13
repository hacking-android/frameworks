/*
 * Decompiled with CFR 0.145.
 */
package android.text.style;

import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Parcel;
import android.text.ParcelableSpan;
import android.text.TextPaint;
import android.text.style.MetricAffectingSpan;

public class StyleSpan
extends MetricAffectingSpan
implements ParcelableSpan {
    private final int mStyle;

    public StyleSpan(int n) {
        this.mStyle = n;
    }

    public StyleSpan(Parcel parcel) {
        this.mStyle = parcel.readInt();
    }

    private static void apply(Paint paint, int n) {
        Typeface typeface = paint.getTypeface();
        int n2 = typeface == null ? 0 : typeface.getStyle();
        n = n2 | n;
        typeface = typeface == null ? Typeface.defaultFromStyle(n) : Typeface.create(typeface, n);
        n = typeface.getStyle() & n;
        if ((n & 1) != 0) {
            paint.setFakeBoldText(true);
        }
        if ((n & 2) != 0) {
            paint.setTextSkewX(-0.25f);
        }
        paint.setTypeface(typeface);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public int getSpanTypeId() {
        return this.getSpanTypeIdInternal();
    }

    @Override
    public int getSpanTypeIdInternal() {
        return 7;
    }

    public int getStyle() {
        return this.mStyle;
    }

    @Override
    public void updateDrawState(TextPaint textPaint) {
        StyleSpan.apply(textPaint, this.mStyle);
    }

    @Override
    public void updateMeasureState(TextPaint textPaint) {
        StyleSpan.apply(textPaint, this.mStyle);
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        this.writeToParcelInternal(parcel, n);
    }

    @Override
    public void writeToParcelInternal(Parcel parcel, int n) {
        parcel.writeInt(this.mStyle);
    }
}

