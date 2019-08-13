/*
 * Decompiled with CFR 0.145.
 */
package android.gesture;

import android.content.Context;
import android.content.res.TypedArray;
import android.gesture.Gesture;
import android.gesture.GesturePoint;
import android.gesture.GestureStroke;
import android.gesture.GestureUtils;
import android.gesture.OrientedBoundingBox;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Parcelable;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import com.android.internal.R;
import java.util.ArrayList;

public class GestureOverlayView
extends FrameLayout {
    private static final boolean DITHER_FLAG = true;
    private static final int FADE_ANIMATION_RATE = 16;
    private static final boolean GESTURE_RENDERING_ANTIALIAS = true;
    public static final int GESTURE_STROKE_TYPE_MULTIPLE = 1;
    public static final int GESTURE_STROKE_TYPE_SINGLE = 0;
    public static final int ORIENTATION_HORIZONTAL = 0;
    public static final int ORIENTATION_VERTICAL = 1;
    private int mCertainGestureColor = -256;
    private int mCurrentColor;
    private Gesture mCurrentGesture;
    private float mCurveEndX;
    private float mCurveEndY;
    private long mFadeDuration = 150L;
    private boolean mFadeEnabled = true;
    private long mFadeOffset = 420L;
    private float mFadingAlpha = 1.0f;
    private boolean mFadingHasStarted;
    private final FadeOutRunnable mFadingOut = new FadeOutRunnable();
    private long mFadingStart;
    private final Paint mGesturePaint = new Paint();
    private float mGestureStrokeAngleThreshold = 40.0f;
    private float mGestureStrokeLengthThreshold = 50.0f;
    private float mGestureStrokeSquarenessTreshold = 0.275f;
    private int mGestureStrokeType = 0;
    private float mGestureStrokeWidth = 12.0f;
    private boolean mGestureVisible = true;
    private boolean mHandleGestureActions;
    private boolean mInterceptEvents = true;
    private final AccelerateDecelerateInterpolator mInterpolator = new AccelerateDecelerateInterpolator();
    private final Rect mInvalidRect = new Rect();
    private int mInvalidateExtraBorder = 10;
    private boolean mIsFadingOut = false;
    private boolean mIsGesturing = false;
    private boolean mIsListeningForGestures;
    private final ArrayList<OnGestureListener> mOnGestureListeners = new ArrayList();
    private final ArrayList<OnGesturePerformedListener> mOnGesturePerformedListeners = new ArrayList();
    private final ArrayList<OnGesturingListener> mOnGesturingListeners = new ArrayList();
    private int mOrientation = 1;
    private final Path mPath = new Path();
    private boolean mPreviousWasGesturing = false;
    private boolean mResetGesture;
    private final ArrayList<GesturePoint> mStrokeBuffer = new ArrayList(100);
    private float mTotalLength;
    private int mUncertainGestureColor = 1224736512;
    private float mX;
    private float mY;

    public GestureOverlayView(Context context) {
        super(context);
        this.init();
    }

    public GestureOverlayView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 17956940);
    }

    public GestureOverlayView(Context context, AttributeSet attributeSet, int n) {
        this(context, attributeSet, n, 0);
    }

    public GestureOverlayView(Context object, AttributeSet attributeSet, int n, int n2) {
        super((Context)object, attributeSet, n, n2);
        object = ((Context)object).obtainStyledAttributes(attributeSet, R.styleable.GestureOverlayView, n, n2);
        this.mGestureStrokeWidth = ((TypedArray)object).getFloat(1, this.mGestureStrokeWidth);
        this.mInvalidateExtraBorder = Math.max(1, (int)this.mGestureStrokeWidth - 1);
        this.mCertainGestureColor = ((TypedArray)object).getColor(2, this.mCertainGestureColor);
        this.mUncertainGestureColor = ((TypedArray)object).getColor(3, this.mUncertainGestureColor);
        this.mFadeDuration = ((TypedArray)object).getInt(5, (int)this.mFadeDuration);
        this.mFadeOffset = ((TypedArray)object).getInt(4, (int)this.mFadeOffset);
        this.mGestureStrokeType = ((TypedArray)object).getInt(6, this.mGestureStrokeType);
        this.mGestureStrokeLengthThreshold = ((TypedArray)object).getFloat(7, this.mGestureStrokeLengthThreshold);
        this.mGestureStrokeAngleThreshold = ((TypedArray)object).getFloat(9, this.mGestureStrokeAngleThreshold);
        this.mGestureStrokeSquarenessTreshold = ((TypedArray)object).getFloat(8, this.mGestureStrokeSquarenessTreshold);
        this.mInterceptEvents = ((TypedArray)object).getBoolean(10, this.mInterceptEvents);
        this.mFadeEnabled = ((TypedArray)object).getBoolean(11, this.mFadeEnabled);
        this.mOrientation = ((TypedArray)object).getInt(0, this.mOrientation);
        ((TypedArray)object).recycle();
        this.init();
    }

    private void cancelGesture(MotionEvent motionEvent) {
        ArrayList<OnGestureListener> arrayList = this.mOnGestureListeners;
        int n = arrayList.size();
        for (int i = 0; i < n; ++i) {
            arrayList.get(i).onGestureCancelled(this, motionEvent);
        }
        this.clear(false);
    }

    private void clear(boolean bl, boolean bl2, boolean bl3) {
        this.setPaintAlpha(255);
        this.removeCallbacks(this.mFadingOut);
        this.mResetGesture = false;
        FadeOutRunnable fadeOutRunnable = this.mFadingOut;
        fadeOutRunnable.fireActionPerformed = bl2;
        fadeOutRunnable.resetMultipleStrokes = false;
        if (bl && this.mCurrentGesture != null) {
            this.mFadingAlpha = 1.0f;
            this.mIsFadingOut = true;
            this.mFadingHasStarted = false;
            long l = AnimationUtils.currentAnimationTimeMillis();
            long l2 = this.mFadeOffset;
            this.mFadingStart = l + l2;
            this.postDelayed(this.mFadingOut, l2);
        } else {
            this.mFadingAlpha = 1.0f;
            this.mIsFadingOut = false;
            this.mFadingHasStarted = false;
            if (bl3) {
                this.mCurrentGesture = null;
                this.mPath.rewind();
                this.invalidate();
            } else if (bl2) {
                this.postDelayed(this.mFadingOut, this.mFadeOffset);
            } else if (this.mGestureStrokeType == 1) {
                fadeOutRunnable = this.mFadingOut;
                fadeOutRunnable.resetMultipleStrokes = true;
                this.postDelayed(fadeOutRunnable, this.mFadeOffset);
            } else {
                this.mCurrentGesture = null;
                this.mPath.rewind();
                this.invalidate();
            }
        }
    }

    private void fireOnGesturePerformed() {
        ArrayList<OnGesturePerformedListener> arrayList = this.mOnGesturePerformedListeners;
        int n = arrayList.size();
        for (int i = 0; i < n; ++i) {
            arrayList.get(i).onGesturePerformed(this, this.mCurrentGesture);
        }
    }

    private void init() {
        this.setWillNotDraw(false);
        Paint paint = this.mGesturePaint;
        paint.setAntiAlias(true);
        paint.setColor(this.mCertainGestureColor);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStrokeWidth(this.mGestureStrokeWidth);
        paint.setDither(true);
        this.mCurrentColor = this.mCertainGestureColor;
        this.setPaintAlpha(255);
    }

    private boolean processEvent(MotionEvent parcelable) {
        int n = parcelable.getAction();
        if (n != 0) {
            if (n != 1) {
                if (n != 2) {
                    if (n == 3 && this.mIsListeningForGestures) {
                        this.touchUp((MotionEvent)parcelable, true);
                        this.invalidate();
                        return true;
                    }
                } else if (this.mIsListeningForGestures) {
                    if ((parcelable = this.touchMove((MotionEvent)parcelable)) != null) {
                        this.invalidate((Rect)parcelable);
                    }
                    return true;
                }
            } else if (this.mIsListeningForGestures) {
                this.touchUp((MotionEvent)parcelable, false);
                this.invalidate();
                return true;
            }
            return false;
        }
        this.touchDown((MotionEvent)parcelable);
        this.invalidate();
        return true;
    }

    private void setCurrentColor(int n) {
        this.mCurrentColor = n;
        if (this.mFadingHasStarted) {
            this.setPaintAlpha((int)(this.mFadingAlpha * 255.0f));
        } else {
            this.setPaintAlpha(255);
        }
        this.invalidate();
    }

    private void setPaintAlpha(int n) {
        int n2 = this.mCurrentColor;
        this.mGesturePaint.setColor(n2 << 8 >>> 8 | (n2 >>> 24) * (n + (n >> 7)) >> 8 << 24);
    }

    private void touchDown(MotionEvent motionEvent) {
        Object object;
        this.mIsListeningForGestures = true;
        float f = motionEvent.getX();
        float f2 = motionEvent.getY();
        this.mX = f;
        this.mY = f2;
        this.mTotalLength = 0.0f;
        this.mIsGesturing = false;
        if (this.mGestureStrokeType != 0 && !this.mResetGesture) {
            object = this.mCurrentGesture;
            if ((object == null || ((Gesture)object).getStrokesCount() == 0) && this.mHandleGestureActions) {
                this.setCurrentColor(this.mUncertainGestureColor);
            }
        } else {
            if (this.mHandleGestureActions) {
                this.setCurrentColor(this.mUncertainGestureColor);
            }
            this.mResetGesture = false;
            this.mCurrentGesture = null;
            this.mPath.rewind();
        }
        if (this.mFadingHasStarted) {
            this.cancelClearAnimation();
        } else if (this.mIsFadingOut) {
            this.setPaintAlpha(255);
            this.mIsFadingOut = false;
            this.mFadingHasStarted = false;
            this.removeCallbacks(this.mFadingOut);
        }
        if (this.mCurrentGesture == null) {
            this.mCurrentGesture = new Gesture();
        }
        this.mStrokeBuffer.add(new GesturePoint(f, f2, motionEvent.getEventTime()));
        this.mPath.moveTo(f, f2);
        int n = this.mInvalidateExtraBorder;
        this.mInvalidRect.set((int)f - n, (int)f2 - n, (int)f + n, (int)f2 + n);
        this.mCurveEndX = f;
        this.mCurveEndY = f2;
        object = this.mOnGestureListeners;
        int n2 = ((ArrayList)object).size();
        for (n = 0; n < n2; ++n) {
            ((OnGestureListener)((ArrayList)object).get(n)).onGestureStarted(this, motionEvent);
        }
    }

    private Rect touchMove(MotionEvent motionEvent) {
        Rect rect = null;
        float f = motionEvent.getX();
        float f2 = motionEvent.getY();
        float f3 = this.mX;
        float f4 = this.mY;
        float f5 = Math.abs(f - f3);
        float f6 = Math.abs(f2 - f4);
        if (f5 >= 3.0f || f6 >= 3.0f) {
            ArrayList<OnGesturingListener> arrayList;
            rect = this.mInvalidRect;
            int n = this.mInvalidateExtraBorder;
            float f7 = this.mCurveEndX;
            int n2 = (int)f7;
            float f8 = this.mCurveEndY;
            rect.set(n2 - n, (int)f8 - n, (int)f7 + n, (int)f8 + n);
            this.mCurveEndX = f8 = (f + f3) / 2.0f;
            this.mCurveEndY = f7 = (f2 + f4) / 2.0f;
            this.mPath.quadTo(f3, f4, f8, f7);
            rect.union((int)f3 - n, (int)f4 - n, (int)f3 + n, (int)f4 + n);
            rect.union((int)f8 - n, (int)f7 - n, (int)f8 + n, (int)f7 + n);
            this.mX = f;
            this.mY = f2;
            this.mStrokeBuffer.add(new GesturePoint(f, f2, motionEvent.getEventTime()));
            if (this.mHandleGestureActions && !this.mIsGesturing) {
                this.mTotalLength += (float)Math.hypot(f5, f6);
                if (this.mTotalLength > this.mGestureStrokeLengthThreshold) {
                    arrayList = GestureUtils.computeOrientedBoundingBox(this.mStrokeBuffer);
                    f4 = f = Math.abs(((OrientedBoundingBox)arrayList).orientation);
                    if (f > 90.0f) {
                        f4 = 180.0f - f;
                    }
                    if (((OrientedBoundingBox)arrayList).squareness > this.mGestureStrokeSquarenessTreshold || (this.mOrientation == 1 ? f4 < this.mGestureStrokeAngleThreshold : f4 > this.mGestureStrokeAngleThreshold)) {
                        this.mIsGesturing = true;
                        this.setCurrentColor(this.mCertainGestureColor);
                        arrayList = this.mOnGesturingListeners;
                        n = arrayList.size();
                        for (n2 = 0; n2 < n; ++n2) {
                            arrayList.get(n2).onGesturingStarted(this);
                        }
                    }
                }
            }
            arrayList = this.mOnGestureListeners;
            n = arrayList.size();
            for (n2 = 0; n2 < n; ++n2) {
                arrayList.get(n2).onGesture(this, motionEvent);
            }
        }
        return rect;
    }

    private void touchUp(MotionEvent object, boolean bl) {
        int n;
        int n2;
        this.mIsListeningForGestures = false;
        Object object2 = this.mCurrentGesture;
        if (object2 != null) {
            ((Gesture)object2).addStroke(new GestureStroke(this.mStrokeBuffer));
            if (!bl) {
                object2 = this.mOnGestureListeners;
                n2 = ((ArrayList)object2).size();
                for (n = 0; n < n2; ++n) {
                    ((OnGestureListener)((ArrayList)object2).get(n)).onGestureEnded(this, (MotionEvent)object);
                }
                bl = this.mHandleGestureActions;
                boolean bl2 = true;
                bl = bl && this.mFadeEnabled;
                if (!this.mHandleGestureActions || !this.mIsGesturing) {
                    bl2 = false;
                }
                this.clear(bl, bl2, false);
            } else {
                this.cancelGesture((MotionEvent)object);
            }
        } else {
            this.cancelGesture((MotionEvent)object);
        }
        this.mStrokeBuffer.clear();
        this.mPreviousWasGesturing = this.mIsGesturing;
        this.mIsGesturing = false;
        object = this.mOnGesturingListeners;
        n2 = ((ArrayList)object).size();
        for (n = 0; n < n2; ++n) {
            ((OnGesturingListener)((ArrayList)object).get(n)).onGesturingEnded(this);
        }
    }

    public void addOnGestureListener(OnGestureListener onGestureListener) {
        this.mOnGestureListeners.add(onGestureListener);
    }

    public void addOnGesturePerformedListener(OnGesturePerformedListener onGesturePerformedListener) {
        this.mOnGesturePerformedListeners.add(onGesturePerformedListener);
        if (this.mOnGesturePerformedListeners.size() > 0) {
            this.mHandleGestureActions = true;
        }
    }

    public void addOnGesturingListener(OnGesturingListener onGesturingListener) {
        this.mOnGesturingListeners.add(onGesturingListener);
    }

    public void cancelClearAnimation() {
        this.setPaintAlpha(255);
        this.mIsFadingOut = false;
        this.mFadingHasStarted = false;
        this.removeCallbacks(this.mFadingOut);
        this.mPath.rewind();
        this.mCurrentGesture = null;
    }

    public void cancelGesture() {
        int n;
        this.mIsListeningForGestures = false;
        this.mCurrentGesture.addStroke(new GestureStroke(this.mStrokeBuffer));
        long l = SystemClock.uptimeMillis();
        Object object = MotionEvent.obtain(l, l, 3, 0.0f, 0.0f, 0);
        ArrayList<OnGestureListener> arrayList = this.mOnGestureListeners;
        int n2 = arrayList.size();
        for (n = 0; n < n2; ++n) {
            arrayList.get(n).onGestureCancelled(this, (MotionEvent)object);
        }
        ((MotionEvent)object).recycle();
        this.clear(false);
        this.mIsGesturing = false;
        this.mPreviousWasGesturing = false;
        this.mStrokeBuffer.clear();
        object = this.mOnGesturingListeners;
        n2 = ((ArrayList)object).size();
        for (n = 0; n < n2; ++n) {
            ((OnGesturingListener)((ArrayList)object).get(n)).onGesturingEnded(this);
        }
    }

    public void clear(boolean bl) {
        this.clear(bl, false, true);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent motionEvent) {
        if (this.isEnabled()) {
            Gesture gesture;
            boolean bl = (this.mIsGesturing || (gesture = this.mCurrentGesture) != null && gesture.getStrokesCount() > 0 && this.mPreviousWasGesturing) && this.mInterceptEvents;
            this.processEvent(motionEvent);
            if (bl) {
                motionEvent.setAction(3);
            }
            super.dispatchTouchEvent(motionEvent);
            return true;
        }
        return super.dispatchTouchEvent(motionEvent);
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        if (this.mCurrentGesture != null && this.mGestureVisible) {
            canvas.drawPath(this.mPath, this.mGesturePaint);
        }
    }

    public ArrayList<GesturePoint> getCurrentStroke() {
        return this.mStrokeBuffer;
    }

    public long getFadeOffset() {
        return this.mFadeOffset;
    }

    public Gesture getGesture() {
        return this.mCurrentGesture;
    }

    public int getGestureColor() {
        return this.mCertainGestureColor;
    }

    public Paint getGesturePaint() {
        return this.mGesturePaint;
    }

    public Path getGesturePath() {
        return this.mPath;
    }

    public Path getGesturePath(Path path) {
        path.set(this.mPath);
        return path;
    }

    public float getGestureStrokeAngleThreshold() {
        return this.mGestureStrokeAngleThreshold;
    }

    public float getGestureStrokeLengthThreshold() {
        return this.mGestureStrokeLengthThreshold;
    }

    public float getGestureStrokeSquarenessTreshold() {
        return this.mGestureStrokeSquarenessTreshold;
    }

    public int getGestureStrokeType() {
        return this.mGestureStrokeType;
    }

    public float getGestureStrokeWidth() {
        return this.mGestureStrokeWidth;
    }

    public int getOrientation() {
        return this.mOrientation;
    }

    public int getUncertainGestureColor() {
        return this.mUncertainGestureColor;
    }

    public boolean isEventsInterceptionEnabled() {
        return this.mInterceptEvents;
    }

    public boolean isFadeEnabled() {
        return this.mFadeEnabled;
    }

    public boolean isGestureVisible() {
        return this.mGestureVisible;
    }

    public boolean isGesturing() {
        return this.mIsGesturing;
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.cancelClearAnimation();
    }

    public void removeAllOnGestureListeners() {
        this.mOnGestureListeners.clear();
    }

    public void removeAllOnGesturePerformedListeners() {
        this.mOnGesturePerformedListeners.clear();
        this.mHandleGestureActions = false;
    }

    public void removeAllOnGesturingListeners() {
        this.mOnGesturingListeners.clear();
    }

    public void removeOnGestureListener(OnGestureListener onGestureListener) {
        this.mOnGestureListeners.remove(onGestureListener);
    }

    public void removeOnGesturePerformedListener(OnGesturePerformedListener onGesturePerformedListener) {
        this.mOnGesturePerformedListeners.remove(onGesturePerformedListener);
        if (this.mOnGesturePerformedListeners.size() <= 0) {
            this.mHandleGestureActions = false;
        }
    }

    public void removeOnGesturingListener(OnGesturingListener onGesturingListener) {
        this.mOnGesturingListeners.remove(onGesturingListener);
    }

    public void setEventsInterceptionEnabled(boolean bl) {
        this.mInterceptEvents = bl;
    }

    public void setFadeEnabled(boolean bl) {
        this.mFadeEnabled = bl;
    }

    public void setFadeOffset(long l) {
        this.mFadeOffset = l;
    }

    public void setGesture(Gesture parcelable) {
        if (this.mCurrentGesture != null) {
            this.clear(false);
        }
        this.setCurrentColor(this.mCertainGestureColor);
        this.mCurrentGesture = parcelable;
        Path path = this.mCurrentGesture.toPath();
        parcelable = new RectF();
        path.computeBounds((RectF)parcelable, true);
        this.mPath.rewind();
        this.mPath.addPath(path, -((RectF)parcelable).left + ((float)this.getWidth() - ((RectF)parcelable).width()) / 2.0f, -((RectF)parcelable).top + ((float)this.getHeight() - ((RectF)parcelable).height()) / 2.0f);
        this.mResetGesture = true;
        this.invalidate();
    }

    public void setGestureColor(int n) {
        this.mCertainGestureColor = n;
    }

    public void setGestureStrokeAngleThreshold(float f) {
        this.mGestureStrokeAngleThreshold = f;
    }

    public void setGestureStrokeLengthThreshold(float f) {
        this.mGestureStrokeLengthThreshold = f;
    }

    public void setGestureStrokeSquarenessTreshold(float f) {
        this.mGestureStrokeSquarenessTreshold = f;
    }

    public void setGestureStrokeType(int n) {
        this.mGestureStrokeType = n;
    }

    public void setGestureStrokeWidth(float f) {
        this.mGestureStrokeWidth = f;
        this.mInvalidateExtraBorder = Math.max(1, (int)f - 1);
        this.mGesturePaint.setStrokeWidth(f);
    }

    public void setGestureVisible(boolean bl) {
        this.mGestureVisible = bl;
    }

    public void setOrientation(int n) {
        this.mOrientation = n;
    }

    public void setUncertainGestureColor(int n) {
        this.mUncertainGestureColor = n;
    }

    private class FadeOutRunnable
    implements Runnable {
        boolean fireActionPerformed;
        boolean resetMultipleStrokes;

        private FadeOutRunnable() {
        }

        @Override
        public void run() {
            if (GestureOverlayView.this.mIsFadingOut) {
                long l = AnimationUtils.currentAnimationTimeMillis() - GestureOverlayView.this.mFadingStart;
                if (l > GestureOverlayView.this.mFadeDuration) {
                    if (this.fireActionPerformed) {
                        GestureOverlayView.this.fireOnGesturePerformed();
                    }
                    GestureOverlayView.this.mPreviousWasGesturing = false;
                    GestureOverlayView.this.mIsFadingOut = false;
                    GestureOverlayView.this.mFadingHasStarted = false;
                    GestureOverlayView.this.mPath.rewind();
                    GestureOverlayView.this.mCurrentGesture = null;
                    GestureOverlayView.this.setPaintAlpha(255);
                } else {
                    GestureOverlayView.this.mFadingHasStarted = true;
                    float f = Math.max(0.0f, Math.min(1.0f, (float)l / (float)GestureOverlayView.this.mFadeDuration));
                    GestureOverlayView gestureOverlayView = GestureOverlayView.this;
                    gestureOverlayView.mFadingAlpha = 1.0f - gestureOverlayView.mInterpolator.getInterpolation(f);
                    gestureOverlayView = GestureOverlayView.this;
                    gestureOverlayView.setPaintAlpha((int)(gestureOverlayView.mFadingAlpha * 255.0f));
                    GestureOverlayView.this.postDelayed(this, 16L);
                }
            } else if (this.resetMultipleStrokes) {
                GestureOverlayView.this.mResetGesture = true;
            } else {
                GestureOverlayView.this.fireOnGesturePerformed();
                GestureOverlayView.this.mFadingHasStarted = false;
                GestureOverlayView.this.mPath.rewind();
                GestureOverlayView.this.mCurrentGesture = null;
                GestureOverlayView.this.mPreviousWasGesturing = false;
                GestureOverlayView.this.setPaintAlpha(255);
            }
            GestureOverlayView.this.invalidate();
        }
    }

    public static interface OnGestureListener {
        public void onGesture(GestureOverlayView var1, MotionEvent var2);

        public void onGestureCancelled(GestureOverlayView var1, MotionEvent var2);

        public void onGestureEnded(GestureOverlayView var1, MotionEvent var2);

        public void onGestureStarted(GestureOverlayView var1, MotionEvent var2);
    }

    public static interface OnGesturePerformedListener {
        public void onGesturePerformed(GestureOverlayView var1, Gesture var2);
    }

    public static interface OnGesturingListener {
        public void onGesturingEnded(GestureOverlayView var1);

        public void onGesturingStarted(GestureOverlayView var1);
    }

}

