/*
 * Decompiled with CFR 0.145.
 */
package android.text.style;

import android.graphics.Paint;
import android.os.Parcel;
import android.text.ParcelableSpan;
import android.text.TextPaint;
import android.text.style.ParagraphStyle;
import android.text.style.WrapTogetherSpan;
import com.android.internal.util.Preconditions;

public interface LineHeightSpan
extends ParagraphStyle,
WrapTogetherSpan {
    public void chooseHeight(CharSequence var1, int var2, int var3, int var4, int var5, Paint.FontMetricsInt var6);

    public static class Standard
    implements LineHeightSpan,
    ParcelableSpan {
        private final int mHeight;

        public Standard(int n) {
            boolean bl = n > 0;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Height:");
            stringBuilder.append(n);
            stringBuilder.append("must be positive");
            Preconditions.checkArgument(bl, stringBuilder.toString());
            this.mHeight = n;
        }

        public Standard(Parcel parcel) {
            this.mHeight = parcel.readInt();
        }

        @Override
        public void chooseHeight(CharSequence charSequence, int n, int n2, int n3, int n4, Paint.FontMetricsInt fontMetricsInt) {
            n = fontMetricsInt.descent - fontMetricsInt.ascent;
            if (n <= 0) {
                return;
            }
            float f = (float)this.mHeight * 1.0f / (float)n;
            fontMetricsInt.descent = Math.round((float)fontMetricsInt.descent * f);
            fontMetricsInt.ascent = fontMetricsInt.descent - this.mHeight;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public int getHeight() {
            return this.mHeight;
        }

        @Override
        public int getSpanTypeId() {
            return this.getSpanTypeIdInternal();
        }

        @Override
        public int getSpanTypeIdInternal() {
            return 28;
        }

        @Override
        public void writeToParcel(Parcel parcel, int n) {
            this.writeToParcelInternal(parcel, n);
        }

        @Override
        public void writeToParcelInternal(Parcel parcel, int n) {
            parcel.writeInt(this.mHeight);
        }
    }

    public static interface WithDensity
    extends LineHeightSpan {
        public void chooseHeight(CharSequence var1, int var2, int var3, int var4, int var5, Paint.FontMetricsInt var6, TextPaint var7);
    }

}

