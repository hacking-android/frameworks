/*
 * Decompiled with CFR 0.145.
 */
package android.text.style;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Parcel;
import android.text.ParcelableSpan;
import android.text.style.ParagraphStyle;

public interface LineBackgroundSpan
extends ParagraphStyle {
    public void drawBackground(Canvas var1, Paint var2, int var3, int var4, int var5, int var6, int var7, CharSequence var8, int var9, int var10, int var11);

    public static class Standard
    implements LineBackgroundSpan,
    ParcelableSpan {
        private final int mColor;

        public Standard(int n) {
            this.mColor = n;
        }

        public Standard(Parcel parcel) {
            this.mColor = parcel.readInt();
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void drawBackground(Canvas canvas, Paint paint, int n, int n2, int n3, int n4, int n5, CharSequence charSequence, int n6, int n7, int n8) {
            n4 = paint.getColor();
            paint.setColor(this.mColor);
            canvas.drawRect(n, n3, n2, n5, paint);
            paint.setColor(n4);
        }

        public final int getColor() {
            return this.mColor;
        }

        @Override
        public int getSpanTypeId() {
            return this.getSpanTypeIdInternal();
        }

        @Override
        public int getSpanTypeIdInternal() {
            return 27;
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

}

