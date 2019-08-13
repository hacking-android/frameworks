/*
 * Decompiled with CFR 0.145.
 */
package android.text.style;

import android.os.Parcel;
import android.text.ParcelableSpan;
import android.text.TextPaint;
import android.text.style.MetricAffectingSpan;

public class RelativeSizeSpan
extends MetricAffectingSpan
implements ParcelableSpan {
    private final float mProportion;

    public RelativeSizeSpan(float f) {
        this.mProportion = f;
    }

    public RelativeSizeSpan(Parcel parcel) {
        this.mProportion = parcel.readFloat();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public float getSizeChange() {
        return this.mProportion;
    }

    @Override
    public int getSpanTypeId() {
        return this.getSpanTypeIdInternal();
    }

    @Override
    public int getSpanTypeIdInternal() {
        return 3;
    }

    @Override
    public void updateDrawState(TextPaint textPaint) {
        textPaint.setTextSize(textPaint.getTextSize() * this.mProportion);
    }

    @Override
    public void updateMeasureState(TextPaint textPaint) {
        textPaint.setTextSize(textPaint.getTextSize() * this.mProportion);
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        this.writeToParcelInternal(parcel, n);
    }

    @Override
    public void writeToParcelInternal(Parcel parcel, int n) {
        parcel.writeFloat(this.mProportion);
    }
}

