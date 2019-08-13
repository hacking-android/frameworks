/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.annotation.UnsupportedAppUsage;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.CanvasProperty;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RecordingCanvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.util.IntArray;
import android.util.SparseArray;
import android.view.AbsSavedState;
import android.view.MotionEvent;
import android.view.RenderNodeAnimator;
import android.view.View;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityManager;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import com.android.internal.R;
import com.android.internal.widget.ExploreByTouchHelper;
import com.android.internal.widget.LockPatternUtils;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class LockPatternView
extends View {
    private static final int ASPECT_LOCK_HEIGHT = 2;
    private static final int ASPECT_LOCK_WIDTH = 1;
    private static final int ASPECT_SQUARE = 0;
    public static final boolean DEBUG_A11Y = false;
    private static final float DRAG_THRESHHOLD = 0.0f;
    private static final float LINE_FADE_ALPHA_MULTIPLIER = 1.5f;
    private static final int MILLIS_PER_CIRCLE_ANIMATING = 700;
    private static final boolean PROFILE_DRAWING = false;
    private static final String TAG = "LockPatternView";
    public static final int VIRTUAL_BASE_VIEW_ID = 1;
    private long mAnimatingPeriodStart;
    private int mAspect;
    private AudioManager mAudioManager;
    private final CellState[][] mCellStates;
    private final Path mCurrentPath = new Path();
    private final int mDotSize;
    private final int mDotSizeActivated;
    private boolean mDrawingProfilingStarted = false;
    private boolean mEnableHapticFeedback = true;
    private int mErrorColor;
    private PatternExploreByTouchHelper mExploreByTouchHelper;
    private boolean mFadePattern = true;
    private final Interpolator mFastOutSlowInInterpolator;
    private float mHitFactor = 0.6f;
    private float mInProgressX = -1.0f;
    private float mInProgressY = -1.0f;
    @UnsupportedAppUsage
    private boolean mInStealthMode = false;
    private boolean mInputEnabled = true;
    private final Rect mInvalidate = new Rect();
    private long[] mLineFadeStart = new long[9];
    private final Interpolator mLinearOutSlowInInterpolator;
    private Drawable mNotSelectedDrawable;
    private OnPatternListener mOnPatternListener;
    @UnsupportedAppUsage
    private final Paint mPaint = new Paint();
    @UnsupportedAppUsage
    private final Paint mPathPaint = new Paint();
    private final int mPathWidth;
    @UnsupportedAppUsage
    private final ArrayList<Cell> mPattern = new ArrayList(9);
    @UnsupportedAppUsage
    private DisplayMode mPatternDisplayMode = DisplayMode.Correct;
    private final boolean[][] mPatternDrawLookup = new boolean[3][3];
    @UnsupportedAppUsage
    private boolean mPatternInProgress = false;
    private int mRegularColor;
    private Drawable mSelectedDrawable;
    @UnsupportedAppUsage
    private float mSquareHeight;
    @UnsupportedAppUsage
    private float mSquareWidth;
    private int mSuccessColor;
    private final Rect mTmpInvalidateRect = new Rect();
    private boolean mUseLockPatternDrawable;

    public LockPatternView(Context context) {
        this(context, null);
    }

    @UnsupportedAppUsage
    public LockPatternView(Context context, AttributeSet object) {
        super(context, (AttributeSet)object);
        object = context.obtainStyledAttributes((AttributeSet)object, R.styleable.LockPatternView, 17956975, 16975042);
        CellState[][] arrcellState = ((TypedArray)object).getString(0);
        this.mAspect = "square".equals(arrcellState) ? 0 : ("lock_width".equals(arrcellState) ? 1 : ("lock_height".equals(arrcellState) ? 2 : 0));
        this.setClickable(true);
        this.mPathPaint.setAntiAlias(true);
        this.mPathPaint.setDither(true);
        this.mRegularColor = ((TypedArray)object).getColor(3, 0);
        this.mErrorColor = ((TypedArray)object).getColor(1, 0);
        this.mSuccessColor = ((TypedArray)object).getColor(4, 0);
        int n = ((TypedArray)object).getColor(2, this.mRegularColor);
        this.mPathPaint.setColor(n);
        this.mPathPaint.setStyle(Paint.Style.STROKE);
        this.mPathPaint.setStrokeJoin(Paint.Join.ROUND);
        this.mPathPaint.setStrokeCap(Paint.Cap.ROUND);
        this.mPathWidth = this.getResources().getDimensionPixelSize(17105270);
        this.mPathPaint.setStrokeWidth(this.mPathWidth);
        this.mDotSize = this.getResources().getDimensionPixelSize(17105271);
        this.mDotSizeActivated = this.getResources().getDimensionPixelSize(17105272);
        this.mUseLockPatternDrawable = this.getResources().getBoolean(17891618);
        if (this.mUseLockPatternDrawable) {
            this.mSelectedDrawable = this.getResources().getDrawable(17302998);
            this.mNotSelectedDrawable = this.getResources().getDrawable(17302996);
        }
        this.mPaint.setAntiAlias(true);
        this.mPaint.setDither(true);
        this.mCellStates = new CellState[3][3];
        for (n = 0; n < 3; ++n) {
            for (int i = 0; i < 3; ++i) {
                this.mCellStates[n][i] = new CellState();
                arrcellState = this.mCellStates;
                arrcellState[n][i].radius = this.mDotSize / 2;
                arrcellState[n][i].row = n;
                arrcellState[n][i].col = i;
            }
        }
        this.mFastOutSlowInInterpolator = AnimationUtils.loadInterpolator(context, 17563661);
        this.mLinearOutSlowInInterpolator = AnimationUtils.loadInterpolator(context, 17563662);
        this.mExploreByTouchHelper = new PatternExploreByTouchHelper(this);
        this.setAccessibilityDelegate(this.mExploreByTouchHelper);
        this.mAudioManager = (AudioManager)this.mContext.getSystemService("audio");
        ((TypedArray)object).recycle();
    }

    private void addCellToPattern(Cell cell) {
        this.mPatternDrawLookup[cell.getRow()][cell.getColumn()] = true;
        this.mPattern.add(cell);
        if (!this.mInStealthMode) {
            this.startCellActivatedAnimation(cell);
        }
        this.notifyCellAdded();
    }

    private float calculateLastSegmentAlpha(float f, float f2, float f3, float f4) {
        return Math.min(1.0f, Math.max(0.0f, ((float)Math.sqrt((f -= f3) * f + (f2 -= f4) * f2) / this.mSquareWidth - 0.3f) * 4.0f));
    }

    private void cancelLineAnimations() {
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 3; ++j) {
                CellState cellState = this.mCellStates[i][j];
                if (cellState.lineAnimator == null) continue;
                cellState.lineAnimator.cancel();
                cellState.lineEndX = Float.MIN_VALUE;
                cellState.lineEndY = Float.MIN_VALUE;
            }
        }
    }

    private Cell checkForNewHit(float f, float f2) {
        int n = this.getRowHit(f2);
        if (n < 0) {
            return null;
        }
        int n2 = this.getColumnHit(f);
        if (n2 < 0) {
            return null;
        }
        if (this.mPatternDrawLookup[n][n2]) {
            return null;
        }
        return Cell.of(n, n2);
    }

    private void clearPatternDrawLookup() {
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 3; ++j) {
                this.mPatternDrawLookup[i][j] = false;
                this.mLineFadeStart[j * 3 + i] = 0L;
            }
        }
    }

    private Cell detectAndAddHit(float f, float f2) {
        Cell cell = this.checkForNewHit(f, f2);
        if (cell != null) {
            Cell cell2 = null;
            ArrayList<Cell> arrayList = this.mPattern;
            if (!arrayList.isEmpty()) {
                cell2 = arrayList.get(arrayList.size() - 1);
                int n = cell.row - cell2.row;
                int n2 = cell.column - cell2.column;
                int n3 = cell2.row;
                int n4 = cell2.column;
                int n5 = Math.abs(n);
                int n6 = -1;
                int n7 = n3;
                if (n5 == 2) {
                    n7 = n3;
                    if (Math.abs(n2) != 1) {
                        n3 = cell2.row;
                        n7 = n > 0 ? 1 : -1;
                        n7 = n3 + n7;
                    }
                }
                n3 = n4;
                if (Math.abs(n2) == 2) {
                    n3 = n4;
                    if (Math.abs(n) != 1) {
                        n4 = cell2.column;
                        n3 = n6;
                        if (n2 > 0) {
                            n3 = 1;
                        }
                        n3 = n4 + n3;
                    }
                }
                cell2 = Cell.of(n7, n3);
            }
            if (cell2 != null && !this.mPatternDrawLookup[cell2.row][cell2.column]) {
                this.addCellToPattern(cell2);
            }
            this.addCellToPattern(cell);
            if (this.mEnableHapticFeedback) {
                this.performHapticFeedback(1, 3);
            }
            return cell;
        }
        return null;
    }

    private void drawCellDrawable(Canvas canvas, int n, int n2, float f, boolean bl) {
        Rect rect = new Rect((int)((float)this.mPaddingLeft + (float)n2 * this.mSquareWidth), (int)((float)this.mPaddingTop + (float)n * this.mSquareHeight), (int)((float)this.mPaddingLeft + (float)(n2 + 1) * this.mSquareWidth), (int)((float)this.mPaddingTop + (float)(n + 1) * this.mSquareHeight));
        canvas.save();
        canvas.clipRect(rect);
        canvas.scale(f /= (float)(this.mDotSize / 2), f, rect.centerX(), rect.centerY());
        if (bl && !(f > 1.0f)) {
            this.mSelectedDrawable.draw(canvas);
        } else {
            this.mNotSelectedDrawable.draw(canvas);
        }
        canvas.restore();
    }

    private void drawCircle(Canvas canvas, float f, float f2, float f3, boolean bl, float f4) {
        this.mPaint.setColor(this.getCurrentColor(bl));
        this.mPaint.setAlpha((int)(255.0f * f4));
        canvas.drawCircle(f, f2, f3, this.mPaint);
    }

    private float getCenterXForColumn(int n) {
        float f = this.mPaddingLeft;
        float f2 = n;
        float f3 = this.mSquareWidth;
        return f + f2 * f3 + f3 / 2.0f;
    }

    private float getCenterYForRow(int n) {
        float f = this.mPaddingTop;
        float f2 = n;
        float f3 = this.mSquareHeight;
        return f + f2 * f3 + f3 / 2.0f;
    }

    private int getColumnHit(float f) {
        float f2 = this.mSquareWidth;
        float f3 = this.mHitFactor * f2;
        float f4 = this.mPaddingLeft;
        float f5 = (f2 - f3) / 2.0f;
        for (int i = 0; i < 3; ++i) {
            float f6 = (float)i * f2 + (f4 + f5);
            if (!(f >= f6) || !(f <= f6 + f3)) continue;
            return i;
        }
        return -1;
    }

    private int getCurrentColor(boolean bl) {
        if (bl && !this.mInStealthMode && !this.mPatternInProgress) {
            if (this.mPatternDisplayMode == DisplayMode.Wrong) {
                return this.mErrorColor;
            }
            if (this.mPatternDisplayMode != DisplayMode.Correct && this.mPatternDisplayMode != DisplayMode.Animate) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("unknown display mode ");
                stringBuilder.append((Object)this.mPatternDisplayMode);
                throw new IllegalStateException(stringBuilder.toString());
            }
            return this.mSuccessColor;
        }
        return this.mRegularColor;
    }

    private int getRowHit(float f) {
        float f2 = this.mSquareHeight;
        float f3 = this.mHitFactor * f2;
        float f4 = this.mPaddingTop;
        float f5 = (f2 - f3) / 2.0f;
        for (int i = 0; i < 3; ++i) {
            float f6 = (float)i * f2 + (f4 + f5);
            if (!(f >= f6) || !(f <= f6 + f3)) continue;
            return i;
        }
        return -1;
    }

    private void handleActionDown(MotionEvent object) {
        this.resetPattern();
        float f = ((MotionEvent)object).getX();
        float f2 = ((MotionEvent)object).getY();
        object = this.detectAndAddHit(f, f2);
        if (object != null) {
            this.setPatternInProgress(true);
            this.mPatternDisplayMode = DisplayMode.Correct;
            this.notifyPatternStarted();
        } else if (this.mPatternInProgress) {
            this.setPatternInProgress(false);
            this.notifyPatternCleared();
        }
        if (object != null) {
            float f3 = this.getCenterXForColumn(((Cell)object).column);
            float f4 = this.getCenterYForRow(((Cell)object).row);
            float f5 = this.mSquareWidth / 2.0f;
            float f6 = this.mSquareHeight / 2.0f;
            this.invalidate((int)(f3 - f5), (int)(f4 - f6), (int)(f3 + f5), (int)(f4 + f6));
        }
        this.mInProgressX = f;
        this.mInProgressY = f2;
    }

    private void handleActionMove(MotionEvent motionEvent) {
        float f = this.mPathWidth;
        int n = motionEvent.getHistorySize();
        this.mTmpInvalidateRect.setEmpty();
        boolean bl = false;
        int n2 = 0;
        do {
            Object object = motionEvent;
            if (n2 >= n + 1) break;
            float f2 = n2 < n ? ((MotionEvent)object).getHistoricalX(n2) : motionEvent.getX();
            float f3 = n2 < n ? ((MotionEvent)object).getHistoricalY(n2) : motionEvent.getY();
            Cell cell = this.detectAndAddHit(f2, f3);
            int n3 = this.mPattern.size();
            if (cell != null && n3 == 1) {
                this.setPatternInProgress(true);
                this.notifyPatternStarted();
            }
            float f4 = Math.abs(f2 - this.mInProgressX);
            float f5 = Math.abs(f3 - this.mInProgressY);
            if (f4 > 0.0f || f5 > 0.0f) {
                bl = true;
            }
            if (this.mPatternInProgress && n3 > 0) {
                object = this.mPattern.get(n3 - 1);
                float f6 = this.getCenterXForColumn(((Cell)object).column);
                f4 = this.getCenterYForRow(((Cell)object).row);
                f5 = Math.min(f6, f2) - f;
                f6 = Math.max(f6, f2) + f;
                f2 = Math.min(f4, f3) - f;
                f4 = Math.max(f4, f3) + f;
                if (cell != null) {
                    f3 = this.mSquareWidth * 0.5f;
                    float f7 = this.mSquareHeight * 0.5f;
                    float f8 = this.getCenterXForColumn(cell.column);
                    float f9 = this.getCenterYForRow(cell.row);
                    f5 = Math.min(f8 - f3, f5);
                    f3 = Math.max(f8 + f3, f6);
                    f2 = Math.min(f9 - f7, f2);
                    f4 = Math.max(f9 + f7, f4);
                } else {
                    f3 = f6;
                }
                this.mTmpInvalidateRect.union(Math.round(f5), Math.round(f2), Math.round(f3), Math.round(f4));
            }
            ++n2;
        } while (true);
        this.mInProgressX = motionEvent.getX();
        this.mInProgressY = motionEvent.getY();
        if (bl) {
            this.mInvalidate.union(this.mTmpInvalidateRect);
            this.invalidate(this.mInvalidate);
            this.mInvalidate.set(this.mTmpInvalidateRect);
        }
    }

    private void handleActionUp() {
        if (!this.mPattern.isEmpty()) {
            this.setPatternInProgress(false);
            this.cancelLineAnimations();
            this.notifyPatternDetected();
            if (this.mFadePattern) {
                this.clearPatternDrawLookup();
                this.mPatternDisplayMode = DisplayMode.Correct;
            }
            this.invalidate();
        }
    }

    private void notifyCellAdded() {
        OnPatternListener onPatternListener = this.mOnPatternListener;
        if (onPatternListener != null) {
            onPatternListener.onPatternCellAdded(this.mPattern);
        }
        this.mExploreByTouchHelper.invalidateRoot();
    }

    private void notifyPatternCleared() {
        this.sendAccessEvent(17040243);
        OnPatternListener onPatternListener = this.mOnPatternListener;
        if (onPatternListener != null) {
            onPatternListener.onPatternCleared();
        }
    }

    @UnsupportedAppUsage
    private void notifyPatternDetected() {
        this.sendAccessEvent(17040244);
        OnPatternListener onPatternListener = this.mOnPatternListener;
        if (onPatternListener != null) {
            onPatternListener.onPatternDetected(this.mPattern);
        }
    }

    private void notifyPatternStarted() {
        this.sendAccessEvent(17040245);
        OnPatternListener onPatternListener = this.mOnPatternListener;
        if (onPatternListener != null) {
            onPatternListener.onPatternStart();
        }
    }

    private void resetPattern() {
        this.mPattern.clear();
        this.clearPatternDrawLookup();
        this.mPatternDisplayMode = DisplayMode.Correct;
        this.invalidate();
    }

    private int resolveMeasured(int n, int n2) {
        int n3 = View.MeasureSpec.getSize(n);
        n = (n = View.MeasureSpec.getMode(n)) != Integer.MIN_VALUE ? (n != 0 ? n3 : n2) : Math.max(n3, n2);
        return n;
    }

    private void sendAccessEvent(int n) {
        this.announceForAccessibility(this.mContext.getString(n));
    }

    private void setPatternInProgress(boolean bl) {
        this.mPatternInProgress = bl;
        this.mExploreByTouchHelper.invalidateRoot();
    }

    private void startCellActivatedAnimation(Cell cell) {
        final CellState cellState = this.mCellStates[cell.row][cell.column];
        this.startRadiusAnimation(this.mDotSize / 2, this.mDotSizeActivated / 2, 96L, this.mLinearOutSlowInInterpolator, cellState, new Runnable(){

            @Override
            public void run() {
                LockPatternView lockPatternView = LockPatternView.this;
                lockPatternView.startRadiusAnimation(lockPatternView.mDotSizeActivated / 2, LockPatternView.this.mDotSize / 2, 192L, LockPatternView.this.mFastOutSlowInInterpolator, cellState, null);
            }
        });
        this.startLineEndAnimation(cellState, this.mInProgressX, this.mInProgressY, this.getCenterXForColumn(cell.column), this.getCenterYForRow(cell.row));
    }

    private void startCellStateAnimationHw(final CellState cellState, float f, float f2, float f3, float f4, float f5, float f6, long l, long l2, Interpolator interpolator2, final Runnable runnable) {
        cellState.alpha = f2;
        cellState.translationY = f4;
        cellState.radius = (float)(this.mDotSize / 2) * f6;
        cellState.hwAnimating = true;
        cellState.hwCenterY = CanvasProperty.createFloat(this.getCenterYForRow(cellState.row) + f3);
        cellState.hwCenterX = CanvasProperty.createFloat(this.getCenterXForColumn(cellState.col));
        cellState.hwRadius = CanvasProperty.createFloat((float)(this.mDotSize / 2) * f5);
        this.mPaint.setColor(this.getCurrentColor(false));
        this.mPaint.setAlpha((int)(255.0f * f));
        cellState.hwPaint = CanvasProperty.createPaint(new Paint(this.mPaint));
        this.startRtFloatAnimation(cellState.hwCenterY, this.getCenterYForRow(cellState.row) + f4, l, l2, interpolator2);
        this.startRtFloatAnimation(cellState.hwRadius, (float)(this.mDotSize / 2) * f6, l, l2, interpolator2);
        this.startRtAlphaAnimation(cellState, f2, l, l2, interpolator2, new AnimatorListenerAdapter(){

            @Override
            public void onAnimationEnd(Animator object) {
                cellState.hwAnimating = false;
                object = runnable;
                if (object != null) {
                    object.run();
                }
            }
        });
        this.invalidate();
    }

    private void startCellStateAnimationSw(final CellState cellState, final float f, final float f2, final float f3, final float f4, final float f5, final float f6, long l, long l2, Interpolator interpolator2, final Runnable runnable) {
        cellState.alpha = f;
        cellState.translationY = f3;
        cellState.radius = (float)(this.mDotSize / 2) * f5;
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0.0f, 1.0f);
        valueAnimator.setDuration(l2);
        valueAnimator.setStartDelay(l);
        valueAnimator.setInterpolator(interpolator2);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener(){

            @Override
            public void onAnimationUpdate(ValueAnimator object) {
                float f7 = ((Float)((ValueAnimator)object).getAnimatedValue()).floatValue();
                object = cellState;
                ((CellState)object).alpha = (1.0f - f7) * f + f2 * f7;
                ((CellState)object).translationY = (1.0f - f7) * f3 + f4 * f7;
                ((CellState)object).radius = (float)(LockPatternView.this.mDotSize / 2) * ((1.0f - f7) * f5 + f6 * f7);
                LockPatternView.this.invalidate();
            }
        });
        valueAnimator.addListener(new AnimatorListenerAdapter(){

            @Override
            public void onAnimationEnd(Animator object) {
                object = runnable;
                if (object != null) {
                    object.run();
                }
            }
        });
        valueAnimator.start();
    }

    private void startLineEndAnimation(final CellState cellState, final float f, final float f2, final float f3, final float f4) {
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0.0f, 1.0f);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener(){

            @Override
            public void onAnimationUpdate(ValueAnimator object) {
                float f5 = ((Float)((ValueAnimator)object).getAnimatedValue()).floatValue();
                object = cellState;
                ((CellState)object).lineEndX = (1.0f - f5) * f + f3 * f5;
                ((CellState)object).lineEndY = (1.0f - f5) * f2 + f4 * f5;
                LockPatternView.this.invalidate();
            }
        });
        valueAnimator.addListener(new AnimatorListenerAdapter(){

            @Override
            public void onAnimationEnd(Animator animator2) {
                cellState.lineAnimator = null;
            }
        });
        valueAnimator.setInterpolator(this.mFastOutSlowInInterpolator);
        valueAnimator.setDuration(100L);
        valueAnimator.start();
        cellState.lineAnimator = valueAnimator;
    }

    private void startRadiusAnimation(float f, float f2, long l, Interpolator interpolator2, final CellState cellState, final Runnable runnable) {
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(f, f2);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener(){

            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                cellState.radius = ((Float)valueAnimator.getAnimatedValue()).floatValue();
                LockPatternView.this.invalidate();
            }
        });
        if (runnable != null) {
            valueAnimator.addListener(new AnimatorListenerAdapter(){

                @Override
                public void onAnimationEnd(Animator animator2) {
                    runnable.run();
                }
            });
        }
        valueAnimator.setInterpolator(interpolator2);
        valueAnimator.setDuration(l);
        valueAnimator.start();
    }

    private void startRtAlphaAnimation(CellState object, float f, long l, long l2, Interpolator interpolator2, Animator.AnimatorListener animatorListener) {
        object = new RenderNodeAnimator(((CellState)object).hwPaint, 1, (int)(255.0f * f));
        ((RenderNodeAnimator)object).setDuration(l2);
        ((RenderNodeAnimator)object).setStartDelay(l);
        ((RenderNodeAnimator)object).setInterpolator(interpolator2);
        ((RenderNodeAnimator)object).setTarget(this);
        ((Animator)object).addListener(animatorListener);
        ((RenderNodeAnimator)object).start();
    }

    private void startRtFloatAnimation(CanvasProperty<Float> object, float f, long l, long l2, Interpolator interpolator2) {
        object = new RenderNodeAnimator((CanvasProperty<Float>)object, f);
        ((RenderNodeAnimator)object).setDuration(l2);
        ((RenderNodeAnimator)object).setStartDelay(l);
        ((RenderNodeAnimator)object).setInterpolator(interpolator2);
        ((RenderNodeAnimator)object).setTarget(this);
        ((RenderNodeAnimator)object).start();
    }

    @UnsupportedAppUsage
    public void clearPattern() {
        this.resetPattern();
    }

    @UnsupportedAppUsage
    public void disableInput() {
        this.mInputEnabled = false;
    }

    @Override
    protected boolean dispatchHoverEvent(MotionEvent motionEvent) {
        return super.dispatchHoverEvent(motionEvent) | this.mExploreByTouchHelper.dispatchHoverEvent(motionEvent);
    }

    @UnsupportedAppUsage
    public void enableInput() {
        this.mInputEnabled = true;
    }

    @UnsupportedAppUsage
    public CellState[][] getCellStates() {
        return this.mCellStates;
    }

    public boolean isInStealthMode() {
        return this.mInStealthMode;
    }

    public boolean isTactileFeedbackEnabled() {
        return this.mEnableHapticFeedback;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        block17 : {
            int n;
            float f;
            int n2;
            float f2;
            Object object;
            float f3;
            float f4;
            int n3;
            Object object2;
            ArrayList<Cell> arrayList = this.mPattern;
            int n4 = arrayList.size();
            boolean[][] arrbl = this.mPatternDrawLookup;
            if (this.mPatternDisplayMode == DisplayMode.Animate) {
                n = (int)(SystemClock.elapsedRealtime() - this.mAnimatingPeriodStart) % ((n4 + 1) * 700);
                n2 = n / 700;
                this.clearPatternDrawLookup();
                for (n3 = 0; n3 < n2; ++n3) {
                    object2 = arrayList.get(n3);
                    arrbl[object2.getRow()][object2.getColumn()] = true;
                }
                n3 = n2 > 0 && n2 < n4 ? 1 : 0;
                if (n3 != 0) {
                    f3 = (float)(n % 700) / 700.0f;
                    object2 = arrayList.get(n2 - 1);
                    f = this.getCenterXForColumn(((Cell)object2).column);
                    f2 = this.getCenterYForRow(((Cell)object2).row);
                    object2 = arrayList.get(n2);
                    f4 = this.getCenterXForColumn(((Cell)object2).column);
                    float f5 = this.getCenterYForRow(((Cell)object2).row);
                    this.mInProgressX = f + (f4 - f) * f3;
                    this.mInProgressY = f2 + (f5 - f2) * f3;
                }
                this.invalidate();
            }
            object2 = this.mCurrentPath;
            ((Path)object2).rewind();
            for (n3 = 0; n3 < 3; ++n3) {
                f = this.getCenterYForRow(n3);
                for (n = 0; n < 3; ++n) {
                    object = this.mCellStates[n3][n];
                    f3 = this.getCenterXForColumn(n);
                    f2 = ((CellState)object).translationY;
                    if (this.mUseLockPatternDrawable) {
                        this.drawCellDrawable(canvas, n3, n, ((CellState)object).radius, arrbl[n3][n]);
                        continue;
                    }
                    if (this.isHardwareAccelerated() && ((CellState)object).hwAnimating) {
                        ((RecordingCanvas)canvas).drawCircle(((CellState)object).hwCenterX, ((CellState)object).hwCenterY, ((CellState)object).hwRadius, ((CellState)object).hwPaint);
                        continue;
                    }
                    this.drawCircle(canvas, (int)f3, (float)((int)f) + f2, ((CellState)object).radius, arrbl[n3][n], ((CellState)object).alpha);
                }
            }
            n = this.mInStealthMode ^ true;
            if (n == 0) break block17;
            this.mPathPaint.setColor(this.getCurrentColor(true));
            n2 = 0;
            f3 = 0.0f;
            f2 = 0.0f;
            long l = SystemClock.elapsedRealtime();
            for (n3 = 0; n3 < n4; ++n3) {
                object = arrayList.get(n3);
                if (!arrbl[((Cell)object).row][((Cell)object).column]) break;
                Object object3 = this.mLineFadeStart;
                if (object3[n3] == 0L) {
                    object3[n3] = SystemClock.elapsedRealtime();
                }
                f4 = this.getCenterXForColumn(((Cell)object).column);
                f = this.getCenterYForRow(((Cell)object).row);
                if (n3 != 0) {
                    n2 = (int)Math.min((float)(l - this.mLineFadeStart[n3]) * 1.5f, 255.0f);
                    object3 = this.mCellStates[((Cell)object).row][((Cell)object).column];
                    ((Path)object2).rewind();
                    object = object2;
                    ((Path)object).moveTo(f3, f2);
                    if (object3.lineEndX != Float.MIN_VALUE && object3.lineEndY != Float.MIN_VALUE) {
                        ((Path)object).lineTo(object3.lineEndX, object3.lineEndY);
                        if (this.mFadePattern) {
                            this.mPathPaint.setAlpha(255 - n2);
                        } else {
                            this.mPathPaint.setAlpha(255);
                        }
                    } else {
                        ((Path)object).lineTo(f4, f);
                        if (this.mFadePattern) {
                            this.mPathPaint.setAlpha(255 - n2);
                        } else {
                            this.mPathPaint.setAlpha(255);
                        }
                    }
                    canvas.drawPath((Path)object, this.mPathPaint);
                }
                f3 = f4;
                f2 = f;
                n2 = 1;
            }
            if ((this.mPatternInProgress || this.mPatternDisplayMode == DisplayMode.Animate) && n2 != 0) {
                ((Path)object2).rewind();
                ((Path)object2).moveTo(f3, f2);
                ((Path)object2).lineTo(this.mInProgressX, this.mInProgressY);
                this.mPathPaint.setAlpha((int)(this.calculateLastSegmentAlpha(this.mInProgressX, this.mInProgressY, f3, f2) * 255.0f));
                canvas.drawPath((Path)object2, this.mPathPaint);
            }
        }
    }

    @Override
    public boolean onHoverEvent(MotionEvent motionEvent) {
        if (AccessibilityManager.getInstance(this.mContext).isTouchExplorationEnabled()) {
            int n = motionEvent.getAction();
            if (n != 7) {
                if (n != 9) {
                    if (n == 10) {
                        motionEvent.setAction(1);
                    }
                } else {
                    motionEvent.setAction(0);
                }
            } else {
                motionEvent.setAction(2);
            }
            this.onTouchEvent(motionEvent);
            motionEvent.setAction(n);
        }
        return super.onHoverEvent(motionEvent);
    }

    @Override
    protected void onMeasure(int n, int n2) {
        int n3 = this.getSuggestedMinimumWidth();
        int n4 = this.getSuggestedMinimumHeight();
        n = this.resolveMeasured(n, n3);
        n2 = this.resolveMeasured(n2, n4);
        n4 = this.mAspect;
        if (n4 != 0) {
            if (n4 != 1) {
                if (n4 == 2) {
                    n = Math.min(n, n2);
                }
            } else {
                n2 = Math.min(n, n2);
            }
        } else {
            n2 = n = Math.min(n, n2);
        }
        this.setMeasuredDimension(n, n2);
    }

    @Override
    protected void onRestoreInstanceState(Parcelable parcelable) {
        parcelable = (SavedState)parcelable;
        super.onRestoreInstanceState(((AbsSavedState)parcelable).getSuperState());
        this.setPattern(DisplayMode.Correct, LockPatternUtils.stringToPattern(((SavedState)parcelable).getSerializedPattern()));
        this.mPatternDisplayMode = DisplayMode.values()[((SavedState)parcelable).getDisplayMode()];
        this.mInputEnabled = ((SavedState)parcelable).isInputEnabled();
        this.mInStealthMode = ((SavedState)parcelable).isInStealthMode();
        this.mEnableHapticFeedback = ((SavedState)parcelable).isTactileFeedbackEnabled();
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        Parcelable parcelable = super.onSaveInstanceState();
        Object object = LockPatternUtils.patternToByteArray(this.mPattern);
        object = object != null ? new String((byte[])object) : null;
        return new SavedState(parcelable, (String)object, this.mPatternDisplayMode.ordinal(), this.mInputEnabled, this.mInStealthMode, this.mEnableHapticFeedback);
    }

    @Override
    protected void onSizeChanged(int n, int n2, int n3, int n4) {
        n = n - this.mPaddingLeft - this.mPaddingRight;
        this.mSquareWidth = (float)n / 3.0f;
        n2 = n2 - this.mPaddingTop - this.mPaddingBottom;
        this.mSquareHeight = (float)n2 / 3.0f;
        this.mExploreByTouchHelper.invalidateRoot();
        if (this.mUseLockPatternDrawable) {
            this.mNotSelectedDrawable.setBounds(this.mPaddingLeft, this.mPaddingTop, n, n2);
            this.mSelectedDrawable.setBounds(this.mPaddingLeft, this.mPaddingTop, n, n2);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (this.mInputEnabled && this.isEnabled()) {
            int n = motionEvent.getAction();
            if (n != 0) {
                if (n != 1) {
                    if (n != 2) {
                        if (n != 3) {
                            return false;
                        }
                        if (this.mPatternInProgress) {
                            this.setPatternInProgress(false);
                            this.resetPattern();
                            this.notifyPatternCleared();
                        }
                        return true;
                    }
                    this.handleActionMove(motionEvent);
                    return true;
                }
                this.handleActionUp();
                return true;
            }
            this.handleActionDown(motionEvent);
            return true;
        }
        return false;
    }

    @UnsupportedAppUsage
    public void setDisplayMode(DisplayMode object) {
        this.mPatternDisplayMode = object;
        if (object == DisplayMode.Animate) {
            if (this.mPattern.size() != 0) {
                this.mAnimatingPeriodStart = SystemClock.elapsedRealtime();
                object = this.mPattern.get(0);
                this.mInProgressX = this.getCenterXForColumn(((Cell)object).getColumn());
                this.mInProgressY = this.getCenterYForRow(((Cell)object).getRow());
                this.clearPatternDrawLookup();
            } else {
                throw new IllegalStateException("you must have a pattern to animate if you want to set the display mode to animate");
            }
        }
        this.invalidate();
    }

    public void setFadePattern(boolean bl) {
        this.mFadePattern = bl;
    }

    @UnsupportedAppUsage
    public void setInStealthMode(boolean bl) {
        this.mInStealthMode = bl;
    }

    @UnsupportedAppUsage
    public void setOnPatternListener(OnPatternListener onPatternListener) {
        this.mOnPatternListener = onPatternListener;
    }

    public void setPattern(DisplayMode displayMode, List<Cell> object) {
        this.mPattern.clear();
        this.mPattern.addAll((Collection<Cell>)object);
        this.clearPatternDrawLookup();
        object = object.iterator();
        while (object.hasNext()) {
            Cell cell = (Cell)object.next();
            this.mPatternDrawLookup[cell.getRow()][cell.getColumn()] = true;
        }
        this.setDisplayMode(displayMode);
    }

    @UnsupportedAppUsage
    public void setTactileFeedbackEnabled(boolean bl) {
        this.mEnableHapticFeedback = bl;
    }

    public void startCellStateAnimation(CellState cellState, float f, float f2, float f3, float f4, float f5, float f6, long l, long l2, Interpolator interpolator2, Runnable runnable) {
        if (this.isHardwareAccelerated()) {
            this.startCellStateAnimationHw(cellState, f, f2, f3, f4, f5, f6, l, l2, interpolator2, runnable);
        } else {
            this.startCellStateAnimationSw(cellState, f, f2, f3, f4, f5, f6, l, l2, interpolator2, runnable);
        }
    }

    public static final class Cell {
        private static final Cell[][] sCells = Cell.createCells();
        @UnsupportedAppUsage
        final int column;
        @UnsupportedAppUsage
        final int row;

        private Cell(int n, int n2) {
            Cell.checkRange(n, n2);
            this.row = n;
            this.column = n2;
        }

        private static void checkRange(int n, int n2) {
            if (n >= 0 && n <= 2) {
                if (n2 >= 0 && n2 <= 2) {
                    return;
                }
                throw new IllegalArgumentException("column must be in range 0-2");
            }
            throw new IllegalArgumentException("row must be in range 0-2");
        }

        private static Cell[][] createCells() {
            Cell[][] arrcell = new Cell[3][3];
            for (int i = 0; i < 3; ++i) {
                for (int j = 0; j < 3; ++j) {
                    arrcell[i][j] = new Cell(i, j);
                }
            }
            return arrcell;
        }

        public static Cell of(int n, int n2) {
            Cell.checkRange(n, n2);
            return sCells[n][n2];
        }

        public int getColumn() {
            return this.column;
        }

        public int getRow() {
            return this.row;
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("(row=");
            stringBuilder.append(this.row);
            stringBuilder.append(",clmn=");
            stringBuilder.append(this.column);
            stringBuilder.append(")");
            return stringBuilder.toString();
        }
    }

    public static class CellState {
        float alpha = 1.0f;
        int col;
        boolean hwAnimating;
        CanvasProperty<Float> hwCenterX;
        CanvasProperty<Float> hwCenterY;
        CanvasProperty<Paint> hwPaint;
        CanvasProperty<Float> hwRadius;
        public ValueAnimator lineAnimator;
        public float lineEndX = Float.MIN_VALUE;
        public float lineEndY = Float.MIN_VALUE;
        float radius;
        int row;
        float translationY;
    }

    public static enum DisplayMode {
        Correct,
        Animate,
        Wrong;
        
    }

    public static interface OnPatternListener {
        public void onPatternCellAdded(List<Cell> var1);

        public void onPatternCleared();

        public void onPatternDetected(List<Cell> var1);

        public void onPatternStart();
    }

    private final class PatternExploreByTouchHelper
    extends ExploreByTouchHelper {
        private final SparseArray<VirtualViewContainer> mItems;
        private Rect mTempRect;

        public PatternExploreByTouchHelper(View view) {
            super(view);
            this.mTempRect = new Rect();
            this.mItems = new SparseArray();
            for (int i = 1; i < 10; ++i) {
                this.mItems.put(i, new VirtualViewContainer(this.getTextForVirtualView(i)));
            }
        }

        private Rect getBoundsForVirtualView(int n) {
            int n2 = n - 1;
            Rect rect = this.mTempRect;
            n = n2 / 3;
            CellState cellState = LockPatternView.this.mCellStates[n][n2 %= 3];
            float f = LockPatternView.this.getCenterXForColumn(n2);
            float f2 = LockPatternView.this.getCenterYForRow(n);
            float f3 = LockPatternView.this.mSquareHeight * LockPatternView.this.mHitFactor * 0.5f;
            float f4 = LockPatternView.this.mSquareWidth * LockPatternView.this.mHitFactor * 0.5f;
            rect.left = (int)(f - f4);
            rect.right = (int)(f + f4);
            rect.top = (int)(f2 - f3);
            rect.bottom = (int)(f2 + f3);
            return rect;
        }

        private CharSequence getTextForVirtualView(int n) {
            return LockPatternView.this.getResources().getString(17040242, n);
        }

        private int getVirtualViewIdForHit(float f, float f2) {
            int n = LockPatternView.this.getRowHit(f2);
            int n2 = Integer.MIN_VALUE;
            if (n < 0) {
                return Integer.MIN_VALUE;
            }
            int n3 = LockPatternView.this.getColumnHit(f);
            if (n3 < 0) {
                return Integer.MIN_VALUE;
            }
            if (LockPatternView.this.mPatternDrawLookup[n][n3]) {
                n2 = n * 3 + n3 + 1;
            }
            return n2;
        }

        private boolean isClickable(int n) {
            if (n != Integer.MIN_VALUE) {
                int n2 = (n - 1) / 3;
                return LockPatternView.this.mPatternDrawLookup[n2][(n - 1) % 3] ^ true;
            }
            return false;
        }

        @Override
        protected int getVirtualViewAt(float f, float f2) {
            return this.getVirtualViewIdForHit(f, f2);
        }

        @Override
        protected void getVisibleVirtualViews(IntArray intArray) {
            if (!LockPatternView.this.mPatternInProgress) {
                return;
            }
            for (int i = 1; i < 10; ++i) {
                intArray.add(i);
            }
        }

        boolean onItemClicked(int n) {
            this.invalidateVirtualView(n);
            this.sendEventForVirtualView(n, 1);
            return true;
        }

        @Override
        protected boolean onPerformActionForVirtualView(int n, int n2, Bundle bundle) {
            if (n2 != 16) {
                return false;
            }
            return this.onItemClicked(n);
        }

        @Override
        public void onPopulateAccessibilityEvent(View view, AccessibilityEvent accessibilityEvent) {
            super.onPopulateAccessibilityEvent(view, accessibilityEvent);
            if (!LockPatternView.this.mPatternInProgress) {
                accessibilityEvent.setContentDescription(LockPatternView.this.getContext().getText(17040240));
            }
        }

        @Override
        protected void onPopulateEventForVirtualView(int n, AccessibilityEvent accessibilityEvent) {
            VirtualViewContainer virtualViewContainer = this.mItems.get(n);
            if (virtualViewContainer != null) {
                accessibilityEvent.getText().add(virtualViewContainer.description);
            }
        }

        @Override
        protected void onPopulateNodeForVirtualView(int n, AccessibilityNodeInfo accessibilityNodeInfo) {
            accessibilityNodeInfo.setText(this.getTextForVirtualView(n));
            accessibilityNodeInfo.setContentDescription(this.getTextForVirtualView(n));
            if (LockPatternView.this.mPatternInProgress) {
                accessibilityNodeInfo.setFocusable(true);
                if (this.isClickable(n)) {
                    accessibilityNodeInfo.addAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_CLICK);
                    accessibilityNodeInfo.setClickable(this.isClickable(n));
                }
            }
            accessibilityNodeInfo.setBoundsInParent(this.getBoundsForVirtualView(n));
        }

        class VirtualViewContainer {
            CharSequence description;

            public VirtualViewContainer(CharSequence charSequence) {
                this.description = charSequence;
            }
        }

    }

    private static class SavedState
    extends View.BaseSavedState {
        public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator<SavedState>(){

            @Override
            public SavedState createFromParcel(Parcel parcel) {
                return new SavedState(parcel);
            }

            public SavedState[] newArray(int n) {
                return new SavedState[n];
            }
        };
        private final int mDisplayMode;
        private final boolean mInStealthMode;
        private final boolean mInputEnabled;
        private final String mSerializedPattern;
        private final boolean mTactileFeedbackEnabled;

        @UnsupportedAppUsage
        private SavedState(Parcel parcel) {
            super(parcel);
            this.mSerializedPattern = parcel.readString();
            this.mDisplayMode = parcel.readInt();
            this.mInputEnabled = (Boolean)parcel.readValue(null);
            this.mInStealthMode = (Boolean)parcel.readValue(null);
            this.mTactileFeedbackEnabled = (Boolean)parcel.readValue(null);
        }

        @UnsupportedAppUsage
        private SavedState(Parcelable parcelable, String string2, int n, boolean bl, boolean bl2, boolean bl3) {
            super(parcelable);
            this.mSerializedPattern = string2;
            this.mDisplayMode = n;
            this.mInputEnabled = bl;
            this.mInStealthMode = bl2;
            this.mTactileFeedbackEnabled = bl3;
        }

        public int getDisplayMode() {
            return this.mDisplayMode;
        }

        public String getSerializedPattern() {
            return this.mSerializedPattern;
        }

        public boolean isInStealthMode() {
            return this.mInStealthMode;
        }

        public boolean isInputEnabled() {
            return this.mInputEnabled;
        }

        public boolean isTactileFeedbackEnabled() {
            return this.mTactileFeedbackEnabled;
        }

        @Override
        public void writeToParcel(Parcel parcel, int n) {
            super.writeToParcel(parcel, n);
            parcel.writeString(this.mSerializedPattern);
            parcel.writeInt(this.mDisplayMode);
            parcel.writeValue(this.mInputEnabled);
            parcel.writeValue(this.mInStealthMode);
            parcel.writeValue(this.mTactileFeedbackEnabled);
        }

    }

}

