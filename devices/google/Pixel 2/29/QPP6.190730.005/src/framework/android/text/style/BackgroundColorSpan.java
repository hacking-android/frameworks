/*
 * Decompiled with CFR 0.145.
 */
package android.text.style;

import android.os.Parcel;
import android.text.ParcelableSpan;
import android.text.TextPaint;
import android.text.style.CharacterStyle;
import android.text.style.UpdateAppearance;

public class BackgroundColorSpan
extends CharacterStyle
implements UpdateAppearance,
ParcelableSpan {
    private final int mColor;

    public BackgroundColorSpan(int n) {
        this.mColor = n;
    }

    public BackgroundColorSpan(Parcel parcel) {
        this.mColor = parcel.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public int getBackgroundColor() {
        return this.mColor;
    }

    @Override
    public int getSpanTypeId() {
        return this.getSpanTypeIdInternal();
    }

    @Override
    public int getSpanTypeIdInternal() {
        return 12;
    }

    @Override
    public void updateDrawState(TextPaint textPaint) {
        textPaint.bgColor = this.mColor;
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

