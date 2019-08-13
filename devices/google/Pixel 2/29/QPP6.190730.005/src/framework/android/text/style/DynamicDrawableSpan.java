/*
 * Decompiled with CFR 0.145.
 */
package android.text.style;

import android.annotation.UnsupportedAppUsage;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.text.style.ReplacementSpan;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.ref.Reference;
import java.lang.ref.WeakReference;

public abstract class DynamicDrawableSpan
extends ReplacementSpan {
    public static final int ALIGN_BASELINE = 1;
    public static final int ALIGN_BOTTOM = 0;
    public static final int ALIGN_CENTER = 2;
    @UnsupportedAppUsage
    private WeakReference<Drawable> mDrawableRef;
    protected final int mVerticalAlignment;

    public DynamicDrawableSpan() {
        this.mVerticalAlignment = 0;
    }

    protected DynamicDrawableSpan(int n) {
        this.mVerticalAlignment = n;
    }

    private Drawable getCachedDrawable() {
        Object object = this.mDrawableRef;
        Drawable drawable2 = null;
        if (object != null) {
            drawable2 = (Drawable)((Reference)object).get();
        }
        object = drawable2;
        if (drawable2 == null) {
            object = this.getDrawable();
            this.mDrawableRef = new WeakReference<Object>(object);
        }
        return object;
    }

    @Override
    public void draw(Canvas canvas, CharSequence object, int n, int n2, float f, int n3, int n4, int n5, Paint paint) {
        object = this.getCachedDrawable();
        canvas.save();
        n = n5 - object.getBounds().bottom;
        n2 = this.mVerticalAlignment;
        if (n2 == 1) {
            n -= paint.getFontMetricsInt().descent;
        } else if (n2 == 2) {
            n = (n5 - n3) / 2 - ((Drawable)object).getBounds().height() / 2;
        }
        canvas.translate(f, n);
        ((Drawable)object).draw(canvas);
        canvas.restore();
    }

    public abstract Drawable getDrawable();

    @Override
    public int getSize(Paint object, CharSequence charSequence, int n, int n2, Paint.FontMetricsInt fontMetricsInt) {
        object = this.getCachedDrawable().getBounds();
        if (fontMetricsInt != null) {
            fontMetricsInt.ascent = -((Rect)object).bottom;
            fontMetricsInt.descent = 0;
            fontMetricsInt.top = fontMetricsInt.ascent;
            fontMetricsInt.bottom = 0;
        }
        return ((Rect)object).right;
    }

    public int getVerticalAlignment() {
        return this.mVerticalAlignment;
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface AlignmentType {
    }

}

