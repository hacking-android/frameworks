/*
 * Decompiled with CFR 0.145.
 */
package android.text.style;

import android.os.Parcel;
import android.text.ParcelableSpan;
import android.text.TextPaint;
import android.text.style.MetricAffectingSpan;

public class SuperscriptSpan
extends MetricAffectingSpan
implements ParcelableSpan {
    public SuperscriptSpan() {
    }

    public SuperscriptSpan(Parcel parcel) {
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
        return 14;
    }

    @Override
    public void updateDrawState(TextPaint textPaint) {
        textPaint.baselineShift += (int)(textPaint.ascent() / 2.0f);
    }

    @Override
    public void updateMeasureState(TextPaint textPaint) {
        textPaint.baselineShift += (int)(textPaint.ascent() / 2.0f);
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        this.writeToParcelInternal(parcel, n);
    }

    @Override
    public void writeToParcelInternal(Parcel parcel, int n) {
    }
}

