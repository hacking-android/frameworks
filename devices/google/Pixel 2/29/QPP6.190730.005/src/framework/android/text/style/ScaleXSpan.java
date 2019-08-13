/*
 * Decompiled with CFR 0.145.
 */
package android.text.style;

import android.os.Parcel;
import android.text.ParcelableSpan;
import android.text.TextPaint;
import android.text.style.MetricAffectingSpan;

public class ScaleXSpan
extends MetricAffectingSpan
implements ParcelableSpan {
    private final float mProportion;

    public ScaleXSpan(float f) {
        this.mProportion = f;
    }

    public ScaleXSpan(Parcel parcel) {
        this.mProportion = parcel.readFloat();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public float getScaleX() {
        return this.mProportion;
    }

    @Override
    public int getSpanTypeId() {
        return this.getSpanTypeIdInternal();
    }

    @Override
    public int getSpanTypeIdInternal() {
        return 4;
    }

    @Override
    public void updateDrawState(TextPaint textPaint) {
        textPaint.setTextScaleX(textPaint.getTextScaleX() * this.mProportion);
    }

    @Override
    public void updateMeasureState(TextPaint textPaint) {
        textPaint.setTextScaleX(textPaint.getTextScaleX() * this.mProportion);
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

