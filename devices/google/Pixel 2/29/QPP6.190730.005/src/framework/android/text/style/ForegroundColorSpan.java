/*
 * Decompiled with CFR 0.145.
 */
package android.text.style;

import android.os.Parcel;
import android.text.ParcelableSpan;
import android.text.TextPaint;
import android.text.style.CharacterStyle;
import android.text.style.UpdateAppearance;

public class ForegroundColorSpan
extends CharacterStyle
implements UpdateAppearance,
ParcelableSpan {
    private final int mColor;

    public ForegroundColorSpan(int n) {
        this.mColor = n;
    }

    public ForegroundColorSpan(Parcel parcel) {
        this.mColor = parcel.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public int getForegroundColor() {
        return this.mColor;
    }

    @Override
    public int getSpanTypeId() {
        return this.getSpanTypeIdInternal();
    }

    @Override
    public int getSpanTypeIdInternal() {
        return 2;
    }

    @Override
    public void updateDrawState(TextPaint textPaint) {
        textPaint.setColor(this.mColor);
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        this.writeToParcelInternal(parcel, n);
    }

    @Override
    public void writeToParcelInternal(Parcel parcel, int n) {
        parcel.writeInt(this.mColor);
    }
}

