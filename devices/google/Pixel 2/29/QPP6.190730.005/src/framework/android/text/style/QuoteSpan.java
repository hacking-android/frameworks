/*
 * Decompiled with CFR 0.145.
 */
package android.text.style;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Parcel;
import android.text.Layout;
import android.text.ParcelableSpan;
import android.text.style.LeadingMarginSpan;

public class QuoteSpan
implements LeadingMarginSpan,
ParcelableSpan {
    public static final int STANDARD_COLOR = -16776961;
    public static final int STANDARD_GAP_WIDTH_PX = 2;
    public static final int STANDARD_STRIPE_WIDTH_PX = 2;
    private final int mColor;
    private final int mGapWidth;
    private final int mStripeWidth;

    public QuoteSpan() {
        this(-16776961, 2, 2);
    }

    public QuoteSpan(int n) {
        this(n, 2, 2);
    }

    public QuoteSpan(int n, int n2, int n3) {
        this.mColor = n;
        this.mStripeWidth = n2;
        this.mGapWidth = n3;
    }

    public QuoteSpan(Parcel parcel) {
        this.mColor = parcel.readInt();
        this.mStripeWidth = parcel.readInt();
        this.mGapWidth = parcel.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void drawLeadingMargin(Canvas canvas, Paint paint, int n, int n2, int n3, int n4, int n5, CharSequence object, int n6, int n7, boolean bl, Layout layout2) {
        object = paint.getStyle();
        n4 = paint.getColor();
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(this.mColor);
        canvas.drawRect(n, n3, this.mStripeWidth * n2 + n, n5, paint);
        paint.setStyle((Paint.Style)((Object)object));
        paint.setColor(n4);
    }

    public int getColor() {
        return this.mColor;
    }

    public int getGapWidth() {
        return this.mGapWidth;
    }

    @Override
    public int getLeadingMargin(boolean bl) {
        return this.mStripeWidth + this.mGapWidth;
    }

    @Override
    public int getSpanTypeId() {
        return this.getSpanTypeIdInternal();
    }

    @Override
    public int getSpanTypeIdInternal() {
        return 9;
    }

    public int getStripeWidth() {
        return this.mStripeWidth;
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        this.writeToParcelInternal(parcel, n);
    }

    @Override
    public void writeToParcelInternal(Parcel parcel, int n) {
        parcel.writeInt(this.mColor);
        parcel.writeInt(this.mStripeWidth);
        parcel.writeInt(this.mGapWidth);
    }
}

