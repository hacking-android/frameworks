/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.policy;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.Rect;
import android.os.Parcelable;
import android.util.Size;
import android.view.Gravity;
import java.io.PrintWriter;
import java.util.ArrayList;

public class PipSnapAlgorithm {
    private static final float CORNER_MAGNET_THRESHOLD = 0.3f;
    private static final int SNAP_MODE_CORNERS_AND_SIDES = 1;
    private static final int SNAP_MODE_CORNERS_ONLY = 0;
    private static final int SNAP_MODE_EDGE = 2;
    private static final int SNAP_MODE_EDGE_MAGNET_CORNERS = 3;
    private static final int SNAP_MODE_LONG_EDGE_MAGNET_CORNERS = 4;
    private final Context mContext;
    private final float mDefaultSizePercent;
    private final int mDefaultSnapMode;
    private final int mFlingDeceleration;
    private boolean mIsMinimized;
    private final float mMaxAspectRatioForMinSize;
    private final float mMinAspectRatioForMinSize;
    private final int mMinimizedVisibleSize;
    private int mOrientation = 0;
    private final ArrayList<Integer> mSnapGravities = new ArrayList();
    private int mSnapMode = 3;

    public PipSnapAlgorithm(Context context) {
        this.mDefaultSnapMode = 3;
        Resources resources = context.getResources();
        this.mContext = context;
        this.mMinimizedVisibleSize = resources.getDimensionPixelSize(17105360);
        this.mDefaultSizePercent = resources.getFloat(17105067);
        this.mMaxAspectRatioForMinSize = resources.getFloat(17105065);
        this.mMinAspectRatioForMinSize = 1.0f / this.mMaxAspectRatioForMinSize;
        this.mFlingDeceleration = this.mContext.getResources().getDimensionPixelSize(17105359);
        this.onConfigurationChanged();
    }

    private void calculateSnapTargets() {
        block4 : {
            block2 : {
                block3 : {
                    this.mSnapGravities.clear();
                    int n = this.mSnapMode;
                    if (n == 0) break block2;
                    if (n == 1) break block3;
                    if (n == 3 || n == 4) break block2;
                    break block4;
                }
                if (this.mOrientation == 2) {
                    this.mSnapGravities.add(49);
                    this.mSnapGravities.add(81);
                } else {
                    this.mSnapGravities.add(19);
                    this.mSnapGravities.add(21);
                }
            }
            this.mSnapGravities.add(51);
            this.mSnapGravities.add(53);
            this.mSnapGravities.add(83);
            this.mSnapGravities.add(85);
        }
    }

    private float distanceToPoint(Point point, int n, int n2) {
        return PointF.length(point.x - n, point.y - n2);
    }

    private Point findClosestPoint(int n, int n2, Point[] arrpoint) {
        Point point = null;
        float f = Float.MAX_VALUE;
        for (Point point2 : arrpoint) {
            float f2 = this.distanceToPoint(point2, n, n2);
            float f3 = f;
            if (f2 < f) {
                point = point2;
                f3 = f2;
            }
            f = f3;
        }
        return point;
    }

    private int findX(float f, float f2, float f3) {
        return (int)((f3 - f2) / f);
    }

    private int findY(float f, float f2, float f3) {
        return (int)(f * f3 + f2);
    }

    private void snapRectToClosestEdge(Rect rect, Rect rect2, Rect rect3) {
        int n = Math.max(rect2.left, Math.min(rect2.right, rect.left));
        int n2 = Math.max(rect2.top, Math.min(rect2.bottom, rect.top));
        rect3.set(rect);
        if (this.mIsMinimized) {
            rect3.offsetTo(n, n2);
            return;
        }
        int n3 = Math.abs(rect.left - rect2.left);
        int n4 = Math.abs(rect.top - rect2.top);
        int n5 = Math.abs(rect2.right - rect.left);
        int n6 = Math.abs(rect2.bottom - rect.top);
        n6 = this.mSnapMode == 4 ? (this.mOrientation == 2 ? Math.min(n4, n6) : Math.min(n3, n5)) : Math.min(Math.min(n3, n5), Math.min(n4, n6));
        if (n6 == n3) {
            rect3.offsetTo(rect2.left, n2);
        } else if (n6 == n4) {
            rect3.offsetTo(n, rect2.top);
        } else if (n6 == n5) {
            rect3.offsetTo(rect2.right, n2);
        } else {
            rect3.offsetTo(n, rect2.bottom);
        }
    }

    public void applyMinimizedOffset(Rect rect, Rect rect2, Point point, Rect rect3) {
        if (rect.left <= rect2.centerX()) {
            rect.offsetTo(rect3.left + this.mMinimizedVisibleSize - rect.width(), rect.top);
        } else {
            rect.offsetTo(point.x - rect3.right - this.mMinimizedVisibleSize, rect.top);
        }
    }

    public void applySnapFraction(Rect rect, Rect rect2, float f) {
        if (f < 1.0f) {
            rect.offsetTo(rect2.left + (int)((float)rect2.width() * f), rect2.top);
        } else if (f < 2.0f) {
            int n = rect2.top;
            int n2 = (int)((float)rect2.height() * (f - 1.0f));
            rect.offsetTo(rect2.right, n + n2);
        } else if (f < 3.0f) {
            rect.offsetTo(rect2.left + (int)((1.0f - (f - 2.0f)) * (float)rect2.width()), rect2.bottom);
        } else {
            int n = rect2.top;
            int n3 = (int)((1.0f - (f - 3.0f)) * (float)rect2.height());
            rect.offsetTo(rect2.left, n + n3);
        }
    }

    public void dump(PrintWriter printWriter, String charSequence) {
        CharSequence charSequence2 = new StringBuilder();
        charSequence2.append((String)charSequence);
        charSequence2.append("  ");
        charSequence2 = charSequence2.toString();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append((String)charSequence);
        stringBuilder.append(PipSnapAlgorithm.class.getSimpleName());
        printWriter.println(stringBuilder.toString());
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append((String)charSequence2);
        ((StringBuilder)charSequence).append("mSnapMode=");
        ((StringBuilder)charSequence).append(this.mSnapMode);
        printWriter.println(((StringBuilder)charSequence).toString());
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append((String)charSequence2);
        ((StringBuilder)charSequence).append("mOrientation=");
        ((StringBuilder)charSequence).append(this.mOrientation);
        printWriter.println(((StringBuilder)charSequence).toString());
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append((String)charSequence2);
        ((StringBuilder)charSequence).append("mMinimizedVisibleSize=");
        ((StringBuilder)charSequence).append(this.mMinimizedVisibleSize);
        printWriter.println(((StringBuilder)charSequence).toString());
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append((String)charSequence2);
        ((StringBuilder)charSequence).append("mIsMinimized=");
        ((StringBuilder)charSequence).append(this.mIsMinimized);
        printWriter.println(((StringBuilder)charSequence).toString());
    }

    public Rect findClosestSnapBounds(Rect parcelable, Rect rect) {
        Parcelable parcelable2 = new Rect(parcelable.left, parcelable.top, parcelable.right + rect.width(), parcelable.bottom + rect.height());
        Rect rect2 = new Rect(rect);
        int n = this.mSnapMode;
        if (n != 4 && n != 3) {
            if (n == 2) {
                this.snapRectToClosestEdge(rect, (Rect)parcelable, rect2);
            } else {
                parcelable = new Rect();
                Point[] arrpoint = new Point[this.mSnapGravities.size()];
                for (n = 0; n < this.mSnapGravities.size(); ++n) {
                    Gravity.apply(this.mSnapGravities.get(n), rect.width(), rect.height(), parcelable2, 0, 0, parcelable);
                    arrpoint[n] = new Point(parcelable.left, parcelable.top);
                }
                parcelable = this.findClosestPoint(rect.left, rect.top, arrpoint);
                rect2.offsetTo(((Point)parcelable).x, ((Point)parcelable).y);
            }
        } else {
            Rect rect3 = new Rect();
            Point[] arrpoint = new Point[this.mSnapGravities.size()];
            for (n = 0; n < this.mSnapGravities.size(); ++n) {
                Gravity.apply(this.mSnapGravities.get(n), rect.width(), rect.height(), parcelable2, 0, 0, rect3);
                arrpoint[n] = new Point(rect3.left, rect3.top);
            }
            parcelable2 = this.findClosestPoint(rect.left, rect.top, arrpoint);
            if (this.distanceToPoint((Point)parcelable2, rect.left, rect.top) < (float)Math.max(rect.width(), rect.height()) * 0.3f) {
                rect2.offsetTo(((Point)parcelable2).x, ((Point)parcelable2).y);
            } else {
                this.snapRectToClosestEdge(rect, (Rect)parcelable, rect2);
            }
        }
        return rect2;
    }

    public Rect findClosestSnapBounds(Rect rect, Rect parcelable, float f, float f2, Point point) {
        Rect rect2 = new Rect((Rect)parcelable);
        parcelable = this.getEdgeIntersect((Rect)parcelable, rect, f, f2, point);
        rect2.offsetTo(((Point)parcelable).x, ((Point)parcelable).y);
        return this.findClosestSnapBounds(rect, rect2);
    }

    public Point getEdgeIntersect(Rect parcelable, Rect rect, float f, float f2, Point point) {
        int n = this.mOrientation == 2 ? 1 : 0;
        int n2 = parcelable.left;
        int n3 = parcelable.top;
        float f3 = f2 / f;
        float f4 = (float)n3 - (float)n2 * f3;
        Point point2 = new Point();
        Point point3 = new Point();
        int n4 = f > 0.0f ? rect.right : rect.left;
        point2.x = n4;
        point2.y = this.findY(f3, f4, point2.x);
        n4 = f2 > 0.0f ? rect.bottom : rect.top;
        point3.y = n4;
        point3.x = this.findX(f3, f4, point3.y);
        n4 = n != 0 ? (f > 0.0f ? rect.right - parcelable.left : parcelable.left - rect.left) : (f2 > 0.0f ? rect.bottom - parcelable.top : parcelable.top - rect.top);
        if (n4 > 0) {
            int n5 = n != 0 ? point.y : point.x;
            int n6 = n != 0 ? point3.y : point3.x;
            int n7 = rect.centerX();
            if (n5 < n7 && n6 < n7 || n5 > n7 && n6 > n7) {
                double d = n != 0 ? (double)f : (double)f2;
                n4 = Math.min((int)(0.0 - Math.pow(d, 2.0)) / (this.mFlingDeceleration * 2), n4);
                if (n != 0) {
                    n = parcelable.left;
                    if (!(f > 0.0f)) {
                        n4 = -n4;
                    }
                    point3.x = n + n4;
                } else {
                    n = parcelable.top;
                    if (!(f2 > 0.0f)) {
                        n4 = -n4;
                    }
                    point3.y = n + n4;
                }
                return point3;
            }
        }
        double d = Math.hypot(point2.x - n2, point2.y - n3);
        double d2 = Math.hypot(point3.x - n2, point3.y - n3);
        if (d == 0.0) {
            return point3;
        }
        if (d2 == 0.0) {
            return point2;
        }
        parcelable = Math.abs(d) > Math.abs(d2) ? point3 : point2;
        return parcelable;
    }

    public void getMovementBounds(Rect rect, Rect rect2, Rect rect3, int n) {
        rect3.set(rect2);
        rect3.right = Math.max(rect2.left, rect2.right - rect.width());
        rect3.bottom = Math.max(rect2.top, rect2.bottom - rect.height());
        rect3.bottom -= n;
    }

    public Size getSizeForAspectRatio(float f, float f2, int n, int n2) {
        n = (int)Math.max(f2, (float)Math.min(n, n2) * this.mDefaultSizePercent);
        if (!(f <= this.mMinAspectRatioForMinSize) && !(f > (f2 = this.mMaxAspectRatioForMinSize))) {
            f2 = PointF.length(f2 * (float)n, n);
            n2 = (int)Math.round(Math.sqrt(f2 * f2 / (f * f + 1.0f)));
            n = Math.round((float)n2 * f);
        } else if (f <= 1.0f) {
            n2 = Math.round((float)n / f);
        } else {
            n2 = n;
            n = Math.round((float)n2 * f);
        }
        return new Size(n, n2);
    }

    public float getSnapFraction(Rect rect, Rect rect2) {
        Rect rect3 = new Rect();
        this.snapRectToClosestEdge(rect, rect2, rect3);
        float f = (float)(rect3.left - rect2.left) / (float)rect2.width();
        float f2 = (float)(rect3.top - rect2.top) / (float)rect2.height();
        if (rect3.top == rect2.top) {
            return f;
        }
        if (rect3.left == rect2.right) {
            return 1.0f + f2;
        }
        if (rect3.top == rect2.bottom) {
            return 1.0f - f + 2.0f;
        }
        return 1.0f - f2 + 3.0f;
    }

    public void onConfigurationChanged() {
        Resources resources = this.mContext.getResources();
        this.mOrientation = resources.getConfiguration().orientation;
        this.mSnapMode = resources.getInteger(17694873);
        this.calculateSnapTargets();
    }

    public void setMinimized(boolean bl) {
        this.mIsMinimized = bl;
    }
}

