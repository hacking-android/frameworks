/*
 * Decompiled with CFR 0.145.
 */
package android.text.style;

import android.os.Parcel;
import android.text.ParcelableSpan;
import android.text.TextPaint;
import android.text.style.MetricAffectingSpan;

public class AbsoluteSizeSpan
extends MetricAffectingSpan
implements ParcelableSpan {
    private final boolean mDip;
    private final int mSize;

    public AbsoluteSizeSpan(int n) {
        this(n, false);
    }

    public AbsoluteSizeSpan(int n, boolean bl) {
        this.mSize = n;
        this.mDip = bl;
    }

    public AbsoluteSizeSpan(Parcel parcel) {
        this.mSize = parcel.readInt();
        boolean bl = parcel.readInt() != 0;
        this.mDip = bl;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public boolean getDip() {
        return this.mDip;
    }

    public int getSize() {
        return this.mSize;
    }

    @Override
    public int getSpanTypeId() {
        return this.getSpanTypeIdInternal();
    }

    @Override
    public int getSpanTypeIdInternal() {
        return 16;
    }

    @Override
    public void updateDrawState(TextPaint textPaint) {
        if (this.mDip) {
            textPaint.setTextSize((float)this.mSize * textPaint.density);
        } else {
            textPaint.setTextSize(this.mSize);
        }
    }

    @Override
    public void updateMeasureState(TextPaint textPaint) {
        if (this.mDip) {
            textPaint.setTextSize((float)this.mSize * textPaint.density);
        } else {
            textPaint.setTextSize(this.mSize);
        }
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        this.writeToParcelInternal(parcel, n);
    }

    @Override
    public void writeToParcelInternal(Parcel parcel, int n) {
        parcel.writeInt(this.mSize);
        parcel.writeInt((int)this.mDip);
    }
}

