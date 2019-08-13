/*
 * Decompiled with CFR 0.145.
 */
package android.text.style;

import android.annotation.UnsupportedAppUsage;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Parcel;
import android.text.Layout;
import android.text.ParcelableSpan;
import android.text.Spanned;
import android.text.style.LeadingMarginSpan;

public class BulletSpan
implements LeadingMarginSpan,
ParcelableSpan {
    private static final int STANDARD_BULLET_RADIUS = 4;
    private static final int STANDARD_COLOR = 0;
    public static final int STANDARD_GAP_WIDTH = 2;
    private final int mBulletRadius;
    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    private final int mColor;
    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    private final int mGapWidth;
    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    private final boolean mWantColor;

    public BulletSpan() {
        this(2, 0, false, 4);
    }

    public BulletSpan(int n) {
        this(n, 0, false, 4);
    }

    public BulletSpan(int n, int n2) {
        this(n, n2, true, 4);
    }

    public BulletSpan(int n, int n2, int n3) {
        this(n, n2, true, n3);
    }

    private BulletSpan(int n, int n2, boolean bl, int n3) {
        this.mGapWidth = n;
        this.mBulletRadius = n3;
        this.mColor = n2;
        this.mWantColor = bl;
    }

    public BulletSpan(Parcel parcel) {
        this.mGapWidth = parcel.readInt();
        boolean bl = parcel.readInt() != 0;
        this.mWantColor = bl;
        this.mColor = parcel.readInt();
        this.mBulletRadius = parcel.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void drawLeadingMargin(Canvas canvas, Paint paint, int n, int n2, int n3, int n4, int n5, CharSequence object, int n6, int n7, boolean bl, Layout layout2) {
        block3 : {
            if (((Spanned)object).getSpanStart(this) != n6) break block3;
            object = paint.getStyle();
            n4 = 0;
            if (this.mWantColor) {
                n4 = paint.getColor();
                paint.setColor(this.mColor);
            }
            paint.setStyle(Paint.Style.FILL);
            if (layout2 != null) {
                n5 -= layout2.getLineExtra(layout2.getLineForOffset(n6));
            }
            float f = (float)(n3 + n5) / 2.0f;
            n3 = this.mBulletRadius;
            canvas.drawCircle(n2 * n3 + n, f, n3, paint);
            if (this.mWantColor) {
                paint.setColor(n4);
            }
            paint.setStyle((Paint.Style)((Object)object));
        }
    }

    public int getBulletRadius() {
        return this.mBulletRadius;
    }

    public int getColor() {
        return this.mColor;
    }

    public int getGapWidth() {
        return this.mGapWidth;
    }

    @Override
    public int getLeadingMargin(boolean bl) {
        return this.mBulletRadius * 2 + this.mGapWidth;
    }

    @Override
    public int getSpanTypeId() {
        return this.getSpanTypeIdInternal();
    }

    @Override
    public int getSpanTypeIdInternal() {
        return 8;
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        this.writeToParcelInternal(parcel, n);
    }

    @Override
    public void writeToParcelInternal(Parcel parcel, int n) {
        parcel.writeInt(this.mGapWidth);
        parcel.writeInt((int)this.mWantColor);
        parcel.writeInt(this.mColor);
        parcel.writeInt(this.mBulletRadius);
    }
}

