/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.widget.-$
 *  android.widget.-$$Lambda
 *  android.widget.-$$Lambda$SmartSelectSprite
 *  android.widget.-$$Lambda$SmartSelectSprite$c8eqlh2kO_X0luLU2BexwK921WA
 *  android.widget.-$$Lambda$SmartSelectSprite$mdkXIT1_UNlJQMaziE_E815aIKE
 */
package android.widget;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.Shape;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import android.widget.-$;
import android.widget._$$Lambda$SmartSelectSprite$2pck5xTffRWoiD4l_tkO_IIf5iM;
import android.widget._$$Lambda$SmartSelectSprite$c8eqlh2kO_X0luLU2BexwK921WA;
import android.widget._$$Lambda$SmartSelectSprite$mdkXIT1_UNlJQMaziE_E815aIKE;
import com.android.internal.util.Preconditions;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.function.ToDoubleFunction;

final class SmartSelectSprite {
    private static final int CORNER_DURATION = 50;
    private static final int EXPAND_DURATION = 300;
    static final Comparator<RectF> RECTANGLE_COMPARATOR = Comparator.comparingDouble(_$$Lambda$SmartSelectSprite$c8eqlh2kO_X0luLU2BexwK921WA.INSTANCE).thenComparingDouble(_$$Lambda$SmartSelectSprite$mdkXIT1_UNlJQMaziE_E815aIKE.INSTANCE);
    private Animator mActiveAnimator = null;
    private final Interpolator mCornerInterpolator;
    private Drawable mExistingDrawable = null;
    private RectangleList mExistingRectangleList = null;
    private final Interpolator mExpandInterpolator;
    private final int mFillColor;
    private final Runnable mInvalidator;

    SmartSelectSprite(Context context, int n, Runnable runnable) {
        this.mExpandInterpolator = AnimationUtils.loadInterpolator(context, 17563661);
        this.mCornerInterpolator = AnimationUtils.loadInterpolator(context, 17563663);
        this.mFillColor = n;
        this.mInvalidator = Preconditions.checkNotNull(runnable);
    }

    private static boolean contains(RectF rectF, PointF pointF) {
        float f = pointF.x;
        float f2 = pointF.y;
        boolean bl = f >= rectF.left && f <= rectF.right && f2 >= rectF.top && f2 <= rectF.bottom;
        return bl;
    }

    private Animator createAnimator(RectangleList cloneable, float f, float f2, List<Animator> object, ValueAnimator.AnimatorUpdateListener object2, Runnable runnable) {
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat((Object)cloneable, "rightBoundary", f2, ((RectangleList)cloneable).getTotalWidth());
        ObjectAnimator objectAnimator2 = ObjectAnimator.ofFloat((Object)cloneable, "leftBoundary", f, 0.0f);
        objectAnimator.setDuration(300L);
        objectAnimator2.setDuration(300L);
        objectAnimator.addUpdateListener((ValueAnimator.AnimatorUpdateListener)object2);
        objectAnimator2.addUpdateListener((ValueAnimator.AnimatorUpdateListener)object2);
        objectAnimator.setInterpolator(this.mExpandInterpolator);
        objectAnimator2.setInterpolator(this.mExpandInterpolator);
        cloneable = new AnimatorSet();
        ((AnimatorSet)cloneable).playTogether((Collection<Animator>)object);
        object = new AnimatorSet();
        ((AnimatorSet)object).playTogether(objectAnimator2, objectAnimator);
        object2 = new AnimatorSet();
        ((AnimatorSet)object2).playSequentially(new Animator[]{object, cloneable});
        this.setUpAnimatorListener((Animator)object2, runnable);
        return object2;
    }

    private ObjectAnimator createCornerAnimator(RoundedRectangleShape cloneable, ValueAnimator.AnimatorUpdateListener animatorUpdateListener) {
        cloneable = ObjectAnimator.ofFloat((Object)cloneable, "roundRatio", ((RoundedRectangleShape)cloneable).getRoundRatio(), 0.0f);
        ((ObjectAnimator)cloneable).setDuration(50L);
        ((ValueAnimator)cloneable).addUpdateListener(animatorUpdateListener);
        ((ValueAnimator)cloneable).setInterpolator(this.mCornerInterpolator);
        return cloneable;
    }

    private static int[] generateDirections(RectangleWithTextSelectionLayout rectangleWithTextSelectionLayout, List<RectangleWithTextSelectionLayout> list) {
        int n;
        int[] arrn = new int[list.size()];
        int n2 = list.indexOf(rectangleWithTextSelectionLayout);
        for (n = 0; n < n2 - 1; ++n) {
            arrn[n] = -1;
        }
        arrn[n2] = list.size() == 1 ? 0 : (n2 == 0 ? -1 : (n2 == list.size() - 1 ? 1 : 0));
        for (n = n2 + 1; n < arrn.length; ++n) {
            arrn[n] = 1;
        }
        return arrn;
    }

    static /* synthetic */ double lambda$static$0(RectF rectF) {
        return rectF.bottom;
    }

    static /* synthetic */ double lambda$static$1(RectF rectF) {
        return rectF.left;
    }

    private void removeExistingDrawables() {
        this.mExistingDrawable = null;
        this.mExistingRectangleList = null;
        this.mInvalidator.run();
    }

    private void setUpAnimatorListener(Animator animator2, final Runnable runnable) {
        animator2.addListener(new Animator.AnimatorListener(){

            @Override
            public void onAnimationCancel(Animator animator2) {
            }

            @Override
            public void onAnimationEnd(Animator animator2) {
                SmartSelectSprite.this.mExistingRectangleList.setDisplayType(1);
                SmartSelectSprite.this.mInvalidator.run();
                runnable.run();
            }

            @Override
            public void onAnimationRepeat(Animator animator2) {
            }

            @Override
            public void onAnimationStart(Animator animator2) {
            }
        });
    }

    public void cancelAnimation() {
        Animator animator2 = this.mActiveAnimator;
        if (animator2 != null) {
            animator2.cancel();
            this.mActiveAnimator = null;
            this.removeExistingDrawables();
        }
    }

    public void draw(Canvas canvas) {
        Drawable drawable2 = this.mExistingDrawable;
        if (drawable2 != null) {
            drawable2.draw(canvas);
        }
    }

    public boolean isAnimationActive() {
        Animator animator2 = this.mActiveAnimator;
        boolean bl = animator2 != null && animator2.isRunning();
        return bl;
    }

    public /* synthetic */ void lambda$startAnimation$2$SmartSelectSprite(ValueAnimator valueAnimator) {
        this.mInvalidator.run();
    }

    public void startAnimation(PointF object, List<RectangleWithTextSelectionLayout> object2, Runnable runnable) {
        Object object3;
        ArrayList<Animator> arrayList;
        _$$Lambda$SmartSelectSprite$2pck5xTffRWoiD4l_tkO_IIf5iM _$$Lambda$SmartSelectSprite$2pck5xTffRWoiD4l_tkO_IIf5iM;
        int n;
        int n2;
        RectF rectF;
        ArrayList<Object> arrayList2;
        block4 : {
            this.cancelAnimation();
            _$$Lambda$SmartSelectSprite$2pck5xTffRWoiD4l_tkO_IIf5iM = new _$$Lambda$SmartSelectSprite$2pck5xTffRWoiD4l_tkO_IIf5iM(this);
            n2 = object2.size();
            arrayList2 = new ArrayList<Object>(n2);
            arrayList = new ArrayList<Animator>(n2);
            n = 0;
            Iterator<RectangleWithTextSelectionLayout> iterator = object2.iterator();
            while (iterator.hasNext()) {
                object3 = iterator.next();
                rectF = ((RectangleWithTextSelectionLayout)object3).getRectangle();
                if (!SmartSelectSprite.contains(rectF, (PointF)object)) {
                    n = (int)((float)n + rectF.width());
                    continue;
                }
                break block4;
            }
            object3 = null;
        }
        if (object3 != null) {
            int n3 = (int)((float)n + (object.x - object3.getRectangle().left));
            object = SmartSelectSprite.generateDirections((RectangleWithTextSelectionLayout)object3, object2);
            for (n = 0; n < n2; ++n) {
                object3 = object2.get(n);
                rectF = ((RectangleWithTextSelectionLayout)object3).getRectangle();
                int n4 = object[n];
                boolean bl = ((RectangleWithTextSelectionLayout)object3).getTextSelectionLayout() == 0;
                object3 = new RoundedRectangleShape(rectF, n4, bl);
                arrayList.add(this.createCornerAnimator((RoundedRectangleShape)object3, _$$Lambda$SmartSelectSprite$2pck5xTffRWoiD4l_tkO_IIf5iM));
                arrayList2.add(object3);
            }
            object = new RectangleList(arrayList2);
            object3 = new ShapeDrawable((Shape)object);
            object2 = ((ShapeDrawable)object3).getPaint();
            ((Paint)object2).setColor(this.mFillColor);
            ((Paint)object2).setStyle(Paint.Style.FILL);
            this.mExistingRectangleList = object;
            this.mExistingDrawable = object3;
            this.mActiveAnimator = this.createAnimator((RectangleList)object, n3, n3, arrayList, _$$Lambda$SmartSelectSprite$2pck5xTffRWoiD4l_tkO_IIf5iM, runnable);
            this.mActiveAnimator.start();
            return;
        }
        throw new IllegalArgumentException("Center point is not inside any of the rectangles!");
    }

    private static final class RectangleList
    extends Shape {
        private static final String PROPERTY_LEFT_BOUNDARY = "leftBoundary";
        private static final String PROPERTY_RIGHT_BOUNDARY = "rightBoundary";
        private int mDisplayType = 0;
        private final Path mOutlinePolygonPath;
        private final List<RoundedRectangleShape> mRectangles;
        private final List<RoundedRectangleShape> mReversedRectangles;

        private RectangleList(List<RoundedRectangleShape> list) {
            this.mRectangles = new ArrayList<RoundedRectangleShape>(list);
            this.mReversedRectangles = new ArrayList<RoundedRectangleShape>(list);
            Collections.reverse(this.mReversedRectangles);
            this.mOutlinePolygonPath = RectangleList.generateOutlinePolygonPath(list);
        }

        private void drawPolygon(Canvas canvas, Paint paint) {
            canvas.drawPath(this.mOutlinePolygonPath, paint);
        }

        private void drawRectangles(Canvas canvas, Paint paint) {
            Iterator<RoundedRectangleShape> iterator = this.mRectangles.iterator();
            while (iterator.hasNext()) {
                iterator.next().draw(canvas, paint);
            }
        }

        private static Path generateOutlinePolygonPath(List<RoundedRectangleShape> object) {
            Path path = new Path();
            object = object.iterator();
            while (object.hasNext()) {
                RoundedRectangleShape roundedRectangleShape = (RoundedRectangleShape)object.next();
                Path path2 = new Path();
                path2.addRect(roundedRectangleShape.mBoundingRectangle, Path.Direction.CW);
                path.op(path2, Path.Op.UNION);
            }
            return path;
        }

        private int getTotalWidth() {
            int n = 0;
            for (RoundedRectangleShape roundedRectangleShape : this.mRectangles) {
                n = (int)((float)n + roundedRectangleShape.getBoundingWidth());
            }
            return n;
        }

        private void setLeftBoundary(float f) {
            float f2 = this.getTotalWidth();
            for (RoundedRectangleShape roundedRectangleShape : this.mReversedRectangles) {
                float f3 = f2 - roundedRectangleShape.getBoundingWidth();
                if (f < f3) {
                    roundedRectangleShape.setStartBoundary(0.0f);
                } else if (f > f2) {
                    roundedRectangleShape.setStartBoundary(roundedRectangleShape.getBoundingWidth());
                } else {
                    roundedRectangleShape.setStartBoundary(roundedRectangleShape.getBoundingWidth() - f2 + f);
                }
                f2 = f3;
            }
        }

        private void setRightBoundary(float f) {
            float f2 = 0.0f;
            for (RoundedRectangleShape roundedRectangleShape : this.mRectangles) {
                float f3 = roundedRectangleShape.getBoundingWidth() + f2;
                if (f3 < f) {
                    roundedRectangleShape.setEndBoundary(roundedRectangleShape.getBoundingWidth());
                } else if (f2 > f) {
                    roundedRectangleShape.setEndBoundary(0.0f);
                } else {
                    roundedRectangleShape.setEndBoundary(f - f2);
                }
                f2 = f3;
            }
        }

        @Override
        public void draw(Canvas canvas, Paint paint) {
            if (this.mDisplayType == 1) {
                this.drawPolygon(canvas, paint);
            } else {
                this.drawRectangles(canvas, paint);
            }
        }

        void setDisplayType(int n) {
            this.mDisplayType = n;
        }

        @Retention(value=RetentionPolicy.SOURCE)
        private static @interface DisplayType {
            public static final int POLYGON = 1;
            public static final int RECTANGLES = 0;
        }

    }

    static final class RectangleWithTextSelectionLayout {
        private final RectF mRectangle;
        private final int mTextSelectionLayout;

        RectangleWithTextSelectionLayout(RectF rectF, int n) {
            this.mRectangle = Preconditions.checkNotNull(rectF);
            this.mTextSelectionLayout = n;
        }

        public RectF getRectangle() {
            return this.mRectangle;
        }

        public int getTextSelectionLayout() {
            return this.mTextSelectionLayout;
        }
    }

    private static final class RoundedRectangleShape
    extends Shape {
        private static final String PROPERTY_ROUND_RATIO = "roundRatio";
        private final RectF mBoundingRectangle;
        private final float mBoundingWidth;
        private final Path mClipPath = new Path();
        private final RectF mDrawRect = new RectF();
        private final int mExpansionDirection;
        private final boolean mInverted;
        private float mLeftBoundary = 0.0f;
        private float mRightBoundary = 0.0f;
        private float mRoundRatio = 1.0f;

        private RoundedRectangleShape(RectF rectF, int n, boolean bl) {
            this.mBoundingRectangle = new RectF(rectF);
            this.mBoundingWidth = rectF.width();
            boolean bl2 = bl && n != 0;
            this.mInverted = bl2;
            this.mExpansionDirection = bl ? RoundedRectangleShape.invert(n) : n;
            if (rectF.height() > rectF.width()) {
                this.setRoundRatio(0.0f);
            } else {
                this.setRoundRatio(1.0f);
            }
        }

        private float getAdjustedCornerRadius() {
            return this.getCornerRadius() * this.mRoundRatio;
        }

        private float getBoundingWidth() {
            return (int)(this.mBoundingRectangle.width() + this.getCornerRadius());
        }

        private float getCornerRadius() {
            return Math.min(this.mBoundingRectangle.width(), this.mBoundingRectangle.height());
        }

        private static int invert(int n) {
            return n * -1;
        }

        private void setEndBoundary(float f) {
            if (this.mInverted) {
                this.mLeftBoundary = this.mBoundingWidth - f;
            } else {
                this.mRightBoundary = f;
            }
        }

        private void setStartBoundary(float f) {
            if (this.mInverted) {
                this.mRightBoundary = this.mBoundingWidth - f;
            } else {
                this.mLeftBoundary = f;
            }
        }

        @Override
        public void draw(Canvas canvas, Paint paint) {
            if (this.mLeftBoundary == this.mRightBoundary) {
                return;
            }
            float f = this.getCornerRadius();
            float f2 = this.getAdjustedCornerRadius();
            this.mDrawRect.set(this.mBoundingRectangle);
            this.mDrawRect.left = this.mBoundingRectangle.left + this.mLeftBoundary - f / 2.0f;
            this.mDrawRect.right = this.mBoundingRectangle.left + this.mRightBoundary + f / 2.0f;
            canvas.save();
            this.mClipPath.reset();
            this.mClipPath.addRoundRect(this.mDrawRect, f2, f2, Path.Direction.CW);
            canvas.clipPath(this.mClipPath);
            canvas.drawRect(this.mBoundingRectangle, paint);
            canvas.restore();
        }

        float getRoundRatio() {
            return this.mRoundRatio;
        }

        void setRoundRatio(float f) {
            this.mRoundRatio = f;
        }

        @Retention(value=RetentionPolicy.SOURCE)
        private static @interface ExpansionDirection {
            public static final int CENTER = 0;
            public static final int LEFT = -1;
            public static final int RIGHT = 1;
        }

    }

}

