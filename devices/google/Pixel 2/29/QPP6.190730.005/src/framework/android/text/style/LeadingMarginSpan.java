/*
 * Decompiled with CFR 0.145.
 */
package android.text.style;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Parcel;
import android.text.Layout;
import android.text.ParcelableSpan;
import android.text.style.ParagraphStyle;
import android.text.style.WrapTogetherSpan;

public interface LeadingMarginSpan
extends ParagraphStyle {
    public void drawLeadingMargin(Canvas var1, Paint var2, int var3, int var4, int var5, int var6, int var7, CharSequence var8, int var9, int var10, boolean var11, Layout var12);

    public int getLeadingMargin(boolean var1);

    public static interface LeadingMarginSpan2
    extends LeadingMarginSpan,
    WrapTogetherSpan {
        public int getLeadingMarginLineCount();
    }

    public static class Standard
    implements LeadingMarginSpan,
    ParcelableSpan {
        private final int mFirst;
        private final int mRest;

        public Standard(int n) {
            this(n, n);
        }

        public Standard(int n, int n2) {
            this.mFirst = n;
            this.mRest = n2;
        }

        public Standard(Parcel parcel) {
            this.mFirst = parcel.readInt();
            this.mRest = parcel.readInt();
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void drawLeadingMargin(Canvas canvas, Paint paint, int n, int n2, int n3, int n4, int n5, CharSequence charSequence, int n6, int n7, boolean bl, Layout layout2) {
        }

        @Override
        public int getLeadingMargin(boolean bl) {
            int n = bl ? this.mFirst : this.mRest;
            return n;
        }

        @Override
        public int getSpanTypeId() {
            return this.getSpanTypeIdInternal();
        }

        @Override
        public int getSpanTypeIdInternal() {
            return 10;
        }

        @Override
        public void writeToParcel(Parcel parcel, int n) {
            this.writeToParcelInternal(parcel, n);
        }

        @Override
        public void writeToParcelInternal(Parcel parcel, int n) {
            parcel.writeInt(this.mFirst);
            parcel.writeInt(this.mRest);
        }
    }

}

