/*
 * Decompiled with CFR 0.145.
 */
package android.view;

import android.annotation.UnsupportedAppUsage;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.os.Handler;
import android.view.GestureDetector;
import android.view.InputEventConsistencyVerifier;
import android.view.MotionEvent;
import android.view.ViewConfiguration;

public class ScaleGestureDetector {
    private static final int ANCHORED_SCALE_MODE_DOUBLE_TAP = 1;
    private static final int ANCHORED_SCALE_MODE_NONE = 0;
    private static final int ANCHORED_SCALE_MODE_STYLUS = 2;
    private static final float SCALE_FACTOR = 0.5f;
    private static final String TAG = "ScaleGestureDetector";
    private static final long TOUCH_STABILIZE_TIME = 128L;
    private int mAnchoredScaleMode = 0;
    private float mAnchoredScaleStartX;
    private float mAnchoredScaleStartY;
    private final Context mContext;
    private float mCurrSpan;
    private float mCurrSpanX;
    private float mCurrSpanY;
    private long mCurrTime;
    private boolean mEventBeforeOrAboveStartingGestureEvent;
    private float mFocusX;
    private float mFocusY;
    private GestureDetector mGestureDetector;
    private final Handler mHandler;
    private boolean mInProgress;
    private float mInitialSpan;
    private final InputEventConsistencyVerifier mInputEventConsistencyVerifier;
    @UnsupportedAppUsage
    private final OnScaleGestureListener mListener;
    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=123768938L)
    private int mMinSpan;
    private float mPrevSpan;
    private float mPrevSpanX;
    private float mPrevSpanY;
    private long mPrevTime;
    private boolean mQuickScaleEnabled;
    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=123768938L)
    private int mSpanSlop;
    private boolean mStylusScaleEnabled;

    public ScaleGestureDetector(Context context, OnScaleGestureListener onScaleGestureListener) {
        this(context, onScaleGestureListener, null);
    }

    public ScaleGestureDetector(Context context, OnScaleGestureListener object, Handler handler) {
        InputEventConsistencyVerifier inputEventConsistencyVerifier = InputEventConsistencyVerifier.isInstrumentationEnabled() ? new InputEventConsistencyVerifier(this, 0) : null;
        this.mInputEventConsistencyVerifier = inputEventConsistencyVerifier;
        this.mContext = context;
        this.mListener = object;
        object = ViewConfiguration.get(context);
        this.mSpanSlop = ((ViewConfiguration)object).getScaledTouchSlop() * 2;
        this.mMinSpan = ((ViewConfiguration)object).getScaledMinimumScalingSpan();
        this.mHandler = handler;
        int n = context.getApplicationInfo().targetSdkVersion;
        if (n > 18) {
            this.setQuickScaleEnabled(true);
        }
        if (n > 22) {
            this.setStylusScaleEnabled(true);
        }
    }

    private boolean inAnchoredScaleMode() {
        boolean bl = this.mAnchoredScaleMode != 0;
        return bl;
    }

    public float getCurrentSpan() {
        return this.mCurrSpan;
    }

    public float getCurrentSpanX() {
        return this.mCurrSpanX;
    }

    public float getCurrentSpanY() {
        return this.mCurrSpanY;
    }

    public long getEventTime() {
        return this.mCurrTime;
    }

    public float getFocusX() {
        return this.mFocusX;
    }

    public float getFocusY() {
        return this.mFocusY;
    }

    public float getPreviousSpan() {
        return this.mPrevSpan;
    }

    public float getPreviousSpanX() {
        return this.mPrevSpanX;
    }

    public float getPreviousSpanY() {
        return this.mPrevSpanY;
    }

    public float getScaleFactor() {
        boolean bl = this.inAnchoredScaleMode();
        float f = 1.0f;
        if (bl) {
            boolean bl2 = this.mEventBeforeOrAboveStartingGestureEvent && this.mCurrSpan < this.mPrevSpan || !this.mEventBeforeOrAboveStartingGestureEvent && this.mCurrSpan > this.mPrevSpan;
            float f2 = Math.abs(1.0f - this.mCurrSpan / this.mPrevSpan) * 0.5f;
            if (!(this.mPrevSpan <= (float)this.mSpanSlop)) {
                f = bl2 ? 1.0f + f2 : 1.0f - f2;
            }
            return f;
        }
        float f3 = this.mPrevSpan;
        if (f3 > 0.0f) {
            f = this.mCurrSpan / f3;
        }
        return f;
    }

    public long getTimeDelta() {
        return this.mCurrTime - this.mPrevTime;
    }

    public boolean isInProgress() {
        return this.mInProgress;
    }

    public boolean isQuickScaleEnabled() {
        return this.mQuickScaleEnabled;
    }

    public boolean isStylusScaleEnabled() {
        return this.mStylusScaleEnabled;
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        block17 : {
            int n;
            InputEventConsistencyVerifier inputEventConsistencyVerifier = this.mInputEventConsistencyVerifier;
            if (inputEventConsistencyVerifier != null) {
                inputEventConsistencyVerifier.onTouchEvent(motionEvent, 0);
            }
            this.mCurrTime = motionEvent.getEventTime();
            int n2 = motionEvent.getActionMasked();
            if (this.mQuickScaleEnabled) {
                this.mGestureDetector.onTouchEvent(motionEvent);
            }
            int n3 = motionEvent.getPointerCount();
            int n4 = (motionEvent.getButtonState() & 32) != 0 ? 1 : 0;
            int n5 = this.mAnchoredScaleMode == 2 && n4 == 0 ? 1 : 0;
            int n6 = n2 != 1 && n2 != 3 && n5 == 0 ? 0 : 1;
            if (n2 == 0 || n6 != 0) {
                if (this.mInProgress) {
                    this.mListener.onScaleEnd(this);
                    this.mInProgress = false;
                    this.mInitialSpan = 0.0f;
                    this.mAnchoredScaleMode = 0;
                } else if (this.inAnchoredScaleMode() && n6 != 0) {
                    this.mInProgress = false;
                    this.mInitialSpan = 0.0f;
                    this.mAnchoredScaleMode = 0;
                }
                if (n6 != 0) {
                    return true;
                }
            }
            if (!this.mInProgress && this.mStylusScaleEnabled && !this.inAnchoredScaleMode() && n6 == 0 && n4 != 0) {
                this.mAnchoredScaleStartX = motionEvent.getX();
                this.mAnchoredScaleStartY = motionEvent.getY();
                this.mAnchoredScaleMode = 2;
                this.mInitialSpan = 0.0f;
            }
            n4 = n2 != 0 && n2 != 6 && n2 != 5 && n5 == 0 ? 0 : 1;
            n6 = n2 == 6 ? 1 : 0;
            n5 = n6 != 0 ? motionEvent.getActionIndex() : -1;
            float f = 0.0f;
            float f2 = 0.0f;
            n6 = n6 != 0 ? n3 - 1 : n3;
            if (this.inAnchoredScaleMode()) {
                f = this.mAnchoredScaleStartX;
                f2 = this.mAnchoredScaleStartY;
                this.mEventBeforeOrAboveStartingGestureEvent = motionEvent.getY() < f2;
            } else {
                for (n = 0; n < n3; ++n) {
                    if (n5 == n) continue;
                    f += motionEvent.getX(n);
                    f2 += motionEvent.getY(n);
                }
                f /= (float)n6;
                f2 /= (float)n6;
            }
            float f3 = 0.0f;
            float f4 = 0.0f;
            for (n = 0; n < n3; ++n) {
                if (n5 == n) continue;
                f3 += Math.abs(motionEvent.getX(n) - f);
                f4 += Math.abs(motionEvent.getY(n) - f2);
            }
            float f5 = (f3 /= (float)n6) * 2.0f;
            f3 = (f4 /= (float)n6) * 2.0f;
            f4 = this.inAnchoredScaleMode() ? f3 : (float)Math.hypot(f5, f3);
            boolean bl = this.mInProgress;
            this.mFocusX = f;
            this.mFocusY = f2;
            if (!this.inAnchoredScaleMode() && this.mInProgress && (f4 < (float)this.mMinSpan || n4 != 0)) {
                this.mListener.onScaleEnd(this);
                this.mInProgress = false;
                this.mInitialSpan = f4;
            }
            if (n4 != 0) {
                this.mCurrSpanX = f5;
                this.mPrevSpanX = f5;
                this.mCurrSpanY = f3;
                this.mPrevSpanY = f3;
                this.mCurrSpan = f4;
                this.mPrevSpan = f4;
                this.mInitialSpan = f4;
            }
            n4 = this.inAnchoredScaleMode() ? this.mSpanSlop : this.mMinSpan;
            if (!this.mInProgress && f4 >= (float)n4 && (bl || Math.abs(f4 - this.mInitialSpan) > (float)this.mSpanSlop)) {
                this.mCurrSpanX = f5;
                this.mPrevSpanX = f5;
                this.mCurrSpanY = f3;
                this.mPrevSpanY = f3;
                this.mCurrSpan = f4;
                this.mPrevSpan = f4;
                this.mPrevTime = this.mCurrTime;
                this.mInProgress = this.mListener.onScaleBegin(this);
            }
            if (n2 != 2) break block17;
            this.mCurrSpanX = f5;
            this.mCurrSpanY = f3;
            this.mCurrSpan = f4;
            bl = true;
            if (this.mInProgress) {
                bl = this.mListener.onScale(this);
            }
            if (bl) {
                this.mPrevSpanX = this.mCurrSpanX;
                this.mPrevSpanY = this.mCurrSpanY;
                this.mPrevSpan = this.mCurrSpan;
                this.mPrevTime = this.mCurrTime;
            }
        }
        return true;
    }

    public void setQuickScaleEnabled(boolean bl) {
        this.mQuickScaleEnabled = bl;
        if (this.mQuickScaleEnabled && this.mGestureDetector == null) {
            GestureDetector.SimpleOnGestureListener simpleOnGestureListener = new GestureDetector.SimpleOnGestureListener(){

                @Override
                public boolean onDoubleTap(MotionEvent motionEvent) {
                    ScaleGestureDetector.this.mAnchoredScaleStartX = motionEvent.getX();
                    ScaleGestureDetector.this.mAnchoredScaleStartY = motionEvent.getY();
                    ScaleGestureDetector.this.mAnchoredScaleMode = 1;
                    return true;
                }
            };
            this.mGestureDetector = new GestureDetector(this.mContext, simpleOnGestureListener, this.mHandler);
        }
    }

    public void setStylusScaleEnabled(boolean bl) {
        this.mStylusScaleEnabled = bl;
    }

    public static interface OnScaleGestureListener {
        public boolean onScale(ScaleGestureDetector var1);

        public boolean onScaleBegin(ScaleGestureDetector var1);

        public void onScaleEnd(ScaleGestureDetector var1);
    }

    public static class SimpleOnScaleGestureListener
    implements OnScaleGestureListener {
        @Override
        public boolean onScale(ScaleGestureDetector scaleGestureDetector) {
            return false;
        }

        @Override
        public boolean onScaleBegin(ScaleGestureDetector scaleGestureDetector) {
            return true;
        }

        @Override
        public void onScaleEnd(ScaleGestureDetector scaleGestureDetector) {
        }
    }

}

