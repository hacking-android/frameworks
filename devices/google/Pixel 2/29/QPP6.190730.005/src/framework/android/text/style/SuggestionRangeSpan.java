/*
 * Decompiled with CFR 0.145.
 */
package android.text.style;

import android.annotation.UnsupportedAppUsage;
import android.os.Parcel;
import android.text.ParcelableSpan;
import android.text.TextPaint;
import android.text.style.CharacterStyle;

public class SuggestionRangeSpan
extends CharacterStyle
implements ParcelableSpan {
    private int mBackgroundColor;

    @UnsupportedAppUsage
    public SuggestionRangeSpan() {
        this.mBackgroundColor = 0;
    }

    @UnsupportedAppUsage
    public SuggestionRangeSpan(Parcel parcel) {
        this.mBackgroundColor = parcel.readInt();
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
        return 21;
    }

    @UnsupportedAppUsage
    public void setBackgroundColor(int n) {
        this.mBackgroundColor = n;
    }

    @Override
    public void updateDrawState(TextPaint textPaint) {
        textPaint.bgColor = this.mBackgroundColor;
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        this.writeToParcelInternal(parcel, n);
    }

    @Override
    public void writeToParcelInternal(Parcel parcel, int n) {
        parcel.writeInt(this.mBackgroundColor);
    }
}

