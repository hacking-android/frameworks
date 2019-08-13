/*
 * Decompiled with CFR 0.145.
 */
package android.graphics;

import android.annotation.UnsupportedAppUsage;
import android.graphics.Path;
import android.graphics.Rect;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public final class Outline {
    public static final int MODE_CONVEX_PATH = 2;
    public static final int MODE_EMPTY = 0;
    public static final int MODE_ROUND_RECT = 1;
    private static final float RADIUS_UNDEFINED = Float.NEGATIVE_INFINITY;
    public float mAlpha;
    public int mMode = 0;
    public Path mPath;
    public float mRadius = Float.NEGATIVE_INFINITY;
    @UnsupportedAppUsage
    public final Rect mRect = new Rect();

    public Outline() {
    }

    public Outline(Outline outline) {
        this.set(outline);
    }

    public boolean canClip() {
        boolean bl = this.mMode != 2;
        return bl;
    }

    public float getAlpha() {
        return this.mAlpha;
    }

    public float getRadius() {
        return this.mRadius;
    }

    public boolean getRect(Rect rect) {
        if (this.mMode != 1) {
            return false;
        }
        rect.set(this.mRect);
        return true;
    }

    public boolean isEmpty() {
        boolean bl = this.mMode == 0;
        return bl;
    }

    public void offset(int n, int n2) {
        int n3 = this.mMode;
        if (n3 == 1) {
            this.mRect.offset(n, n2);
        } else if (n3 == 2) {
            this.mPath.offset(n, n2);
        }
    }

    public void set(Outline outline) {
        this.mMode = outline.mMode;
        if (outline.mMode == 2) {
            if (this.mPath == null) {
                this.mPath = new Path();
            }
            this.mPath.set(outline.mPath);
        }
        this.mRect.set(outline.mRect);
        this.mRadius = outline.mRadius;
        this.mAlpha = outline.mAlpha;
    }

    public void setAlpha(float f) {
        this.mAlpha = f;
    }

    public void setConvexPath(Path path) {
        if (path.isEmpty()) {
            this.setEmpty();
            return;
        }
        if (this.mPath == null) {
            this.mPath = new Path();
        }
        this.mMode = 2;
        this.mPath.set(path);
        this.mRect.setEmpty();
        this.mRadius = Float.NEGATIVE_INFINITY;
    }

    public void setEmpty() {
        Path path = this.mPath;
        if (path != null) {
            path.rewind();
        }
        this.mMode = 0;
        this.mRect.setEmpty();
        this.mRadius = Float.NEGATIVE_INFINITY;
    }

    public void setOval(int n, int n2, int n3, int n4) {
        if (n < n3 && n2 < n4) {
            if (n4 - n2 == n3 - n) {
                this.setRoundRect(n, n2, n3, n4, (float)(n4 - n2) / 2.0f);
                return;
            }
            Path path = this.mPath;
            if (path == null) {
                this.mPath = new Path();
            } else {
                path.rewind();
            }
            this.mMode = 2;
            this.mPath.addOval(n, n2, n3, n4, Path.Direction.CW);
            this.mRect.setEmpty();
            this.mRadius = Float.NEGATIVE_INFINITY;
            return;
        }
        this.setEmpty();
    }

    public void setOval(Rect rect) {
        this.setOval(rect.left, rect.top, rect.right, rect.bottom);
    }

    public void setRect(int n, int n2, int n3, int n4) {
        this.setRoundRect(n, n2, n3, n4, 0.0f);
    }

    public void setRect(Rect rect) {
        this.setRect(rect.left, rect.top, rect.right, rect.bottom);
    }

    public void setRoundRect(int n, int n2, int n3, int n4, float f) {
        if (n < n3 && n2 < n4) {
            if (this.mMode == 2) {
                this.mPath.rewind();
            }
            this.mMode = 1;
            this.mRect.set(n, n2, n3, n4);
            this.mRadius = f;
            return;
        }
        this.setEmpty();
    }

    public void setRoundRect(Rect rect, float f) {
        this.setRoundRect(rect.left, rect.top, rect.right, rect.bottom, f);
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface Mode {
    }

}

