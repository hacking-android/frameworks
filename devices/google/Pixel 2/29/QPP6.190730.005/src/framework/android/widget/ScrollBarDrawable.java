/*
 * Decompiled with CFR 0.145.
 */
package android.widget;

import android.annotation.UnsupportedAppUsage;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import com.android.internal.widget.ScrollBarUtils;

public class ScrollBarDrawable
extends Drawable
implements Drawable.Callback {
    private int mAlpha = 255;
    private boolean mAlwaysDrawHorizontalTrack;
    private boolean mAlwaysDrawVerticalTrack;
    private boolean mBoundsChanged;
    private ColorFilter mColorFilter;
    private int mExtent;
    private boolean mHasSetAlpha;
    private boolean mHasSetColorFilter;
    private Drawable mHorizontalThumb;
    private Drawable mHorizontalTrack;
    private boolean mMutated;
    private int mOffset;
    private int mRange;
    private boolean mRangeChanged;
    private boolean mVertical;
    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=123768422L)
    private Drawable mVerticalThumb;
    private Drawable mVerticalTrack;

    private void drawThumb(Canvas canvas, Rect rect, int n, int n2, boolean bl) {
        boolean bl2 = this.mRangeChanged || this.mBoundsChanged;
        if (bl) {
            if (this.mVerticalThumb != null) {
                Drawable drawable2 = this.mVerticalThumb;
                if (bl2) {
                    drawable2.setBounds(rect.left, rect.top + n, rect.right, rect.top + n + n2);
                }
                drawable2.draw(canvas);
            }
        } else if (this.mHorizontalThumb != null) {
            Drawable drawable3 = this.mHorizontalThumb;
            if (bl2) {
                drawable3.setBounds(rect.left + n, rect.top, rect.left + n + n2, rect.bottom);
            }
            drawable3.draw(canvas);
        }
    }

    private void drawTrack(Canvas canvas, Rect rect, boolean bl) {
        Drawable drawable2 = bl ? this.mVerticalTrack : this.mHorizontalTrack;
        if (drawable2 != null) {
            if (this.mBoundsChanged) {
                drawable2.setBounds(rect);
            }
            drawable2.draw(canvas);
        }
    }

    private void propagateCurrentState(Drawable drawable2) {
        if (drawable2 != null) {
            if (this.mMutated) {
                drawable2.mutate();
            }
            drawable2.setState(this.getState());
            drawable2.setCallback(this);
            if (this.mHasSetAlpha) {
                drawable2.setAlpha(this.mAlpha);
            }
            if (this.mHasSetColorFilter) {
                drawable2.setColorFilter(this.mColorFilter);
            }
        }
    }

    @Override
    public void draw(Canvas canvas) {
        boolean bl;
        int n;
        boolean bl2 = this.mVertical;
        int n2 = this.mExtent;
        int n3 = this.mRange;
        if (n2 > 0 && n3 > n2) {
            bl = true;
            n = 1;
        } else {
            bl = bl2 ? this.mAlwaysDrawVerticalTrack : this.mAlwaysDrawHorizontalTrack;
            n = 0;
        }
        Rect rect = this.getBounds();
        if (canvas.quickReject(rect.left, rect.top, rect.right, rect.bottom, Canvas.EdgeType.AA)) {
            return;
        }
        if (bl) {
            this.drawTrack(canvas, rect, bl2);
        }
        if (n != 0) {
            n = bl2 ? rect.height() : rect.width();
            int n4 = bl2 ? rect.width() : rect.height();
            n4 = ScrollBarUtils.getThumbLength(n, n4, n2, n3);
            this.drawThumb(canvas, rect, ScrollBarUtils.getThumbOffset(n, n4, n2, n3, this.mOffset), n4, bl2);
        }
    }

    @Override
    public int getAlpha() {
        return this.mAlpha;
    }

    public boolean getAlwaysDrawHorizontalTrack() {
        return this.mAlwaysDrawHorizontalTrack;
    }

    public boolean getAlwaysDrawVerticalTrack() {
        return this.mAlwaysDrawVerticalTrack;
    }

    @Override
    public ColorFilter getColorFilter() {
        return this.mColorFilter;
    }

    public Drawable getHorizontalThumbDrawable() {
        return this.mHorizontalThumb;
    }

    public Drawable getHorizontalTrackDrawable() {
        return this.mHorizontalTrack;
    }

    @Override
    public int getOpacity() {
        return -3;
    }

    public int getSize(boolean bl) {
        int n = 0;
        int n2 = 0;
        if (bl) {
            Drawable drawable2 = this.mVerticalTrack;
            if (drawable2 != null) {
                n2 = drawable2.getIntrinsicWidth();
            } else {
                drawable2 = this.mVerticalThumb;
                if (drawable2 != null) {
                    n2 = drawable2.getIntrinsicWidth();
                }
            }
            return n2;
        }
        Drawable drawable3 = this.mHorizontalTrack;
        if (drawable3 != null) {
            n2 = drawable3.getIntrinsicHeight();
        } else {
            drawable3 = this.mHorizontalThumb;
            n2 = n;
            if (drawable3 != null) {
                n2 = drawable3.getIntrinsicHeight();
            }
        }
        return n2;
    }

    public Drawable getVerticalThumbDrawable() {
        return this.mVerticalThumb;
    }

    public Drawable getVerticalTrackDrawable() {
        return this.mVerticalTrack;
    }

    @Override
    public void invalidateDrawable(Drawable drawable2) {
        this.invalidateSelf();
    }

    @Override
    public boolean isStateful() {
        Drawable drawable2 = this.mVerticalTrack;
        boolean bl = drawable2 != null && drawable2.isStateful() || (drawable2 = this.mVerticalThumb) != null && drawable2.isStateful() || (drawable2 = this.mHorizontalTrack) != null && drawable2.isStateful() || (drawable2 = this.mHorizontalThumb) != null && drawable2.isStateful() || super.isStateful();
        return bl;
    }

    @Override
    public ScrollBarDrawable mutate() {
        if (!this.mMutated && super.mutate() == this) {
            Drawable drawable2 = this.mVerticalTrack;
            if (drawable2 != null) {
                drawable2.mutate();
            }
            if ((drawable2 = this.mVerticalThumb) != null) {
                drawable2.mutate();
            }
            if ((drawable2 = this.mHorizontalTrack) != null) {
                drawable2.mutate();
            }
            if ((drawable2 = this.mHorizontalThumb) != null) {
                drawable2.mutate();
            }
            this.mMutated = true;
        }
        return this;
    }

    @Override
    protected void onBoundsChange(Rect rect) {
        super.onBoundsChange(rect);
        this.mBoundsChanged = true;
    }

    @Override
    protected boolean onStateChange(int[] arrn) {
        boolean bl = super.onStateChange(arrn);
        Drawable drawable2 = this.mVerticalTrack;
        boolean bl2 = bl;
        if (drawable2 != null) {
            bl2 = bl | drawable2.setState(arrn);
        }
        drawable2 = this.mVerticalThumb;
        bl = bl2;
        if (drawable2 != null) {
            bl = bl2 | drawable2.setState(arrn);
        }
        drawable2 = this.mHorizontalTrack;
        bl2 = bl;
        if (drawable2 != null) {
            bl2 = bl | drawable2.setState(arrn);
        }
        drawable2 = this.mHorizontalThumb;
        bl = bl2;
        if (drawable2 != null) {
            bl = bl2 | drawable2.setState(arrn);
        }
        return bl;
    }

    @Override
    public void scheduleDrawable(Drawable drawable2, Runnable runnable, long l) {
        this.scheduleSelf(runnable, l);
    }

    @Override
    public void setAlpha(int n) {
        this.mAlpha = n;
        this.mHasSetAlpha = true;
        Drawable drawable2 = this.mVerticalTrack;
        if (drawable2 != null) {
            drawable2.setAlpha(n);
        }
        if ((drawable2 = this.mVerticalThumb) != null) {
            drawable2.setAlpha(n);
        }
        if ((drawable2 = this.mHorizontalTrack) != null) {
            drawable2.setAlpha(n);
        }
        if ((drawable2 = this.mHorizontalThumb) != null) {
            drawable2.setAlpha(n);
        }
    }

    public void setAlwaysDrawHorizontalTrack(boolean bl) {
        this.mAlwaysDrawHorizontalTrack = bl;
    }

    public void setAlwaysDrawVerticalTrack(boolean bl) {
        this.mAlwaysDrawVerticalTrack = bl;
    }

    @Override
    public void setColorFilter(ColorFilter colorFilter) {
        this.mColorFilter = colorFilter;
        this.mHasSetColorFilter = true;
        Drawable drawable2 = this.mVerticalTrack;
        if (drawable2 != null) {
            drawable2.setColorFilter(colorFilter);
        }
        if ((drawable2 = this.mVerticalThumb) != null) {
            drawable2.setColorFilter(colorFilter);
        }
        if ((drawable2 = this.mHorizontalTrack) != null) {
            drawable2.setColorFilter(colorFilter);
        }
        if ((drawable2 = this.mHorizontalThumb) != null) {
            drawable2.setColorFilter(colorFilter);
        }
    }

    @UnsupportedAppUsage(maxTargetSdk=28)
    public void setHorizontalThumbDrawable(Drawable drawable2) {
        Drawable drawable3 = this.mHorizontalThumb;
        if (drawable3 != null) {
            drawable3.setCallback(null);
        }
        this.propagateCurrentState(drawable2);
        this.mHorizontalThumb = drawable2;
    }

    public void setHorizontalTrackDrawable(Drawable drawable2) {
        Drawable drawable3 = this.mHorizontalTrack;
        if (drawable3 != null) {
            drawable3.setCallback(null);
        }
        this.propagateCurrentState(drawable2);
        this.mHorizontalTrack = drawable2;
    }

    public void setParameters(int n, int n2, int n3, boolean bl) {
        if (this.mVertical != bl) {
            this.mVertical = bl;
            this.mBoundsChanged = true;
        }
        if (this.mRange != n || this.mOffset != n2 || this.mExtent != n3) {
            this.mRange = n;
            this.mOffset = n2;
            this.mExtent = n3;
            this.mRangeChanged = true;
        }
    }

    @UnsupportedAppUsage(maxTargetSdk=28)
    public void setVerticalThumbDrawable(Drawable drawable2) {
        Drawable drawable3 = this.mVerticalThumb;
        if (drawable3 != null) {
            drawable3.setCallback(null);
        }
        this.propagateCurrentState(drawable2);
        this.mVerticalThumb = drawable2;
    }

    public void setVerticalTrackDrawable(Drawable drawable2) {
        Drawable drawable3 = this.mVerticalTrack;
        if (drawable3 != null) {
            drawable3.setCallback(null);
        }
        this.propagateCurrentState(drawable2);
        this.mVerticalTrack = drawable2;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("ScrollBarDrawable: range=");
        stringBuilder.append(this.mRange);
        stringBuilder.append(" offset=");
        stringBuilder.append(this.mOffset);
        stringBuilder.append(" extent=");
        stringBuilder.append(this.mExtent);
        String string2 = this.mVertical ? " V" : " H";
        stringBuilder.append(string2);
        return stringBuilder.toString();
    }

    @Override
    public void unscheduleDrawable(Drawable drawable2, Runnable runnable) {
        this.unscheduleSelf(runnable);
    }
}

