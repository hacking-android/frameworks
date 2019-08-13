/*
 * Decompiled with CFR 0.145.
 */
package android.view;

import android.annotation.UnsupportedAppUsage;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.SystemClock;
import android.util.StatsLog;
import android.view.InputEvent;
import android.view.InputEventConsistencyVerifier;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.ViewConfiguration;

public class GestureDetector {
    private static final int DOUBLE_TAP_MIN_TIME;
    private static final int DOUBLE_TAP_TIMEOUT;
    @UnsupportedAppUsage(maxTargetSdk=28)
    private static final int LONGPRESS_TIMEOUT;
    private static final int LONG_PRESS = 2;
    private static final int SHOW_PRESS = 1;
    private static final int TAP = 3;
    private static final int TAP_TIMEOUT;
    private boolean mAlwaysInBiggerTapRegion;
    @UnsupportedAppUsage
    private boolean mAlwaysInTapRegion;
    private OnContextClickListener mContextClickListener;
    private MotionEvent mCurrentDownEvent;
    private MotionEvent mCurrentMotionEvent;
    private boolean mDeferConfirmSingleTap;
    private OnDoubleTapListener mDoubleTapListener;
    private int mDoubleTapSlopSquare;
    private int mDoubleTapTouchSlopSquare;
    private float mDownFocusX;
    private float mDownFocusY;
    private final Handler mHandler;
    private boolean mHasRecordedClassification;
    private boolean mIgnoreNextUpEvent;
    private boolean mInContextClick;
    private boolean mInLongPress;
    private final InputEventConsistencyVerifier mInputEventConsistencyVerifier;
    private boolean mIsDoubleTapping;
    private boolean mIsLongpressEnabled;
    private float mLastFocusX;
    private float mLastFocusY;
    @UnsupportedAppUsage
    private final OnGestureListener mListener;
    private int mMaximumFlingVelocity;
    @UnsupportedAppUsage
    private int mMinimumFlingVelocity;
    private MotionEvent mPreviousUpEvent;
    private boolean mStillDown;
    @UnsupportedAppUsage
    private int mTouchSlopSquare;
    private VelocityTracker mVelocityTracker;

    static {
        LONGPRESS_TIMEOUT = ViewConfiguration.getLongPressTimeout();
        TAP_TIMEOUT = ViewConfiguration.getTapTimeout();
        DOUBLE_TAP_TIMEOUT = ViewConfiguration.getDoubleTapTimeout();
        DOUBLE_TAP_MIN_TIME = ViewConfiguration.getDoubleTapMinTime();
    }

    public GestureDetector(Context context, OnGestureListener onGestureListener) {
        this(context, onGestureListener, null);
    }

    public GestureDetector(Context context, OnGestureListener onGestureListener, Handler handler) {
        InputEventConsistencyVerifier inputEventConsistencyVerifier = InputEventConsistencyVerifier.isInstrumentationEnabled() ? new InputEventConsistencyVerifier(this, 0) : null;
        this.mInputEventConsistencyVerifier = inputEventConsistencyVerifier;
        this.mHandler = handler != null ? new GestureHandler(handler) : new GestureHandler();
        this.mListener = onGestureListener;
        if (onGestureListener instanceof OnDoubleTapListener) {
            this.setOnDoubleTapListener((OnDoubleTapListener)((Object)onGestureListener));
        }
        if (onGestureListener instanceof OnContextClickListener) {
            this.setContextClickListener((OnContextClickListener)((Object)onGestureListener));
        }
        this.init(context);
    }

    public GestureDetector(Context context, OnGestureListener onGestureListener, Handler handler, boolean bl) {
        this(context, onGestureListener, handler);
    }

    @Deprecated
    public GestureDetector(OnGestureListener onGestureListener) {
        this(null, onGestureListener, null);
    }

    @Deprecated
    public GestureDetector(OnGestureListener onGestureListener, Handler handler) {
        this(null, onGestureListener, handler);
    }

    private void cancel() {
        this.mHandler.removeMessages(1);
        this.mHandler.removeMessages(2);
        this.mHandler.removeMessages(3);
        this.mVelocityTracker.recycle();
        this.mVelocityTracker = null;
        this.mIsDoubleTapping = false;
        this.mStillDown = false;
        this.mAlwaysInTapRegion = false;
        this.mAlwaysInBiggerTapRegion = false;
        this.mDeferConfirmSingleTap = false;
        this.mInLongPress = false;
        this.mInContextClick = false;
        this.mIgnoreNextUpEvent = false;
    }

    private void cancelTaps() {
        this.mHandler.removeMessages(1);
        this.mHandler.removeMessages(2);
        this.mHandler.removeMessages(3);
        this.mIsDoubleTapping = false;
        this.mAlwaysInTapRegion = false;
        this.mAlwaysInBiggerTapRegion = false;
        this.mDeferConfirmSingleTap = false;
        this.mInLongPress = false;
        this.mInContextClick = false;
        this.mIgnoreNextUpEvent = false;
    }

    private void dispatchLongPress() {
        this.mHandler.removeMessages(3);
        this.mDeferConfirmSingleTap = false;
        this.mInLongPress = true;
        this.mListener.onLongPress(this.mCurrentDownEvent);
    }

    private void init(Context object) {
        if (this.mListener != null) {
            int n;
            int n2;
            int n3;
            this.mIsLongpressEnabled = true;
            if (object == null) {
                n2 = n = ViewConfiguration.getTouchSlop();
                n3 = ViewConfiguration.getDoubleTapSlop();
                this.mMinimumFlingVelocity = ViewConfiguration.getMinimumFlingVelocity();
                this.mMaximumFlingVelocity = ViewConfiguration.getMaximumFlingVelocity();
            } else {
                object = ViewConfiguration.get((Context)object);
                n = ((ViewConfiguration)object).getScaledTouchSlop();
                n2 = ((ViewConfiguration)object).getScaledDoubleTapTouchSlop();
                n3 = ((ViewConfiguration)object).getScaledDoubleTapSlop();
                this.mMinimumFlingVelocity = ((ViewConfiguration)object).getScaledMinimumFlingVelocity();
                this.mMaximumFlingVelocity = ((ViewConfiguration)object).getScaledMaximumFlingVelocity();
            }
            this.mTouchSlopSquare = n * n;
            this.mDoubleTapTouchSlopSquare = n2 * n2;
            this.mDoubleTapSlopSquare = n3 * n3;
            return;
        }
        throw new NullPointerException("OnGestureListener must not be null");
    }

    private boolean isConsideredDoubleTap(MotionEvent motionEvent, MotionEvent motionEvent2, MotionEvent motionEvent3) {
        boolean bl = this.mAlwaysInBiggerTapRegion;
        boolean bl2 = false;
        if (!bl) {
            return false;
        }
        long l = motionEvent3.getEventTime() - motionEvent2.getEventTime();
        if (l <= (long)DOUBLE_TAP_TIMEOUT && l >= (long)DOUBLE_TAP_MIN_TIME) {
            int n = (int)motionEvent.getX() - (int)motionEvent3.getX();
            int n2 = (int)motionEvent.getY() - (int)motionEvent3.getY();
            int n3 = (motionEvent.getFlags() & 8) != 0 ? 1 : 0;
            if (n * n + n2 * n2 < (n3 = n3 != 0 ? 0 : this.mDoubleTapSlopSquare)) {
                bl2 = true;
            }
            return bl2;
        }
        return false;
    }

    private void recordGestureClassification(int n) {
        if (!this.mHasRecordedClassification && n != 0) {
            if (this.mCurrentDownEvent != null && this.mCurrentMotionEvent != null) {
                StatsLog.write(177, this.getClass().getName(), n, (int)(SystemClock.uptimeMillis() - this.mCurrentMotionEvent.getDownTime()), (float)Math.hypot(this.mCurrentMotionEvent.getRawX() - this.mCurrentDownEvent.getRawX(), this.mCurrentMotionEvent.getRawY() - this.mCurrentDownEvent.getRawY()));
                this.mHasRecordedClassification = true;
                return;
            }
            this.mHasRecordedClassification = true;
            return;
        }
    }

    public boolean isLongpressEnabled() {
        return this.mIsLongpressEnabled;
    }

    public boolean onGenericMotionEvent(MotionEvent motionEvent) {
        InputEventConsistencyVerifier inputEventConsistencyVerifier = this.mInputEventConsistencyVerifier;
        if (inputEventConsistencyVerifier != null) {
            inputEventConsistencyVerifier.onGenericMotionEvent(motionEvent, 0);
        }
        int n = motionEvent.getActionButton();
        int n2 = motionEvent.getActionMasked();
        if (n2 != 11) {
            if (n2 == 12 && this.mInContextClick && (n == 32 || n == 2)) {
                this.mInContextClick = false;
                this.mIgnoreNextUpEvent = true;
            }
        } else if (!(this.mContextClickListener == null || this.mInContextClick || this.mInLongPress || n != 32 && n != 2 || !this.mContextClickListener.onContextClick(motionEvent))) {
            this.mInContextClick = true;
            this.mHandler.removeMessages(2);
            this.mHandler.removeMessages(3);
            return true;
        }
        return false;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    public boolean onTouchEvent(MotionEvent var1_1) {
        block36 : {
            block34 : {
                block35 : {
                    block29 : {
                        block38 : {
                            block40 : {
                                block39 : {
                                    block37 : {
                                        block30 : {
                                            block31 : {
                                                block32 : {
                                                    block33 : {
                                                        var2_2 = this.mInputEventConsistencyVerifier;
                                                        if (var2_2 != null) {
                                                            var2_2.onTouchEvent(var1_1, 0);
                                                        }
                                                        var3_3 = var1_1.getAction();
                                                        var2_2 = this.mCurrentMotionEvent;
                                                        if (var2_2 != null) {
                                                            var2_2.recycle();
                                                        }
                                                        this.mCurrentMotionEvent = MotionEvent.obtain(var1_1);
                                                        if (this.mVelocityTracker == null) {
                                                            this.mVelocityTracker = VelocityTracker.obtain();
                                                        }
                                                        this.mVelocityTracker.addMovement(var1_1);
                                                        var4_4 = (var3_3 & 255) == 6 ? 1 : 0;
                                                        var5_5 = var4_4 != 0 ? var1_1.getActionIndex() : -1;
                                                        var6_6 = (var1_1.getFlags() & 8) != 0 ? 1 : 0;
                                                        var7_7 = 0.0f;
                                                        var8_8 = 0.0f;
                                                        var9_9 = var1_1.getPointerCount();
                                                        for (var10_10 = 0; var10_10 < var9_9; ++var10_10) {
                                                            if (var5_5 == var10_10) continue;
                                                            var7_7 += var1_1.getX(var10_10);
                                                            var8_8 += var1_1.getY(var10_10);
                                                        }
                                                        var4_4 = var4_4 != 0 ? var9_9 - 1 : var9_9;
                                                        var7_7 /= (float)var4_4;
                                                        var11_11 = var8_8 / (float)var4_4;
                                                        var4_4 = var3_3 & 255;
                                                        if (var4_4 == 0) break block29;
                                                        if (var4_4 == 1) break block30;
                                                        if (var4_4 == 2) break block31;
                                                        if (var4_4 == 3) break block32;
                                                        if (var4_4 == 5) break block33;
                                                        if (var4_4 != 6) break block34;
                                                        this.mLastFocusX = var7_7;
                                                        this.mDownFocusX = var7_7;
                                                        this.mLastFocusY = var11_11;
                                                        this.mDownFocusY = var11_11;
                                                        this.mVelocityTracker.computeCurrentVelocity(1000, this.mMaximumFlingVelocity);
                                                        var6_6 = var1_1.getActionIndex();
                                                        var4_4 = var1_1.getPointerId(var6_6);
                                                        var7_7 = this.mVelocityTracker.getXVelocity(var4_4);
                                                        var8_8 = this.mVelocityTracker.getYVelocity(var4_4);
                                                        break block35;
                                                    }
                                                    this.mLastFocusX = var7_7;
                                                    this.mDownFocusX = var7_7;
                                                    this.mLastFocusY = var11_11;
                                                    this.mDownFocusY = var11_11;
                                                    this.cancelTaps();
                                                    break block34;
                                                }
                                                this.cancel();
                                                break block34;
                                            }
                                            if (this.mInLongPress || this.mInContextClick) break block34;
                                            var10_10 = var1_1.getClassification();
                                            var12_12 = this.mHandler.hasMessages(2);
                                            var13_13 = this.mLastFocusX - var7_7;
                                            var8_8 = this.mLastFocusY - var11_11;
                                            if (this.mIsDoubleTapping) {
                                                this.recordGestureClassification(2);
                                                var14_14 = this.mDoubleTapListener.onDoubleTapEvent(var1_1) | false;
                                            } else if (this.mAlwaysInTapRegion) {
                                                var5_5 = (int)(var7_7 - this.mDownFocusX);
                                                var4_4 = (int)(var11_11 - this.mDownFocusY);
                                                var9_9 = var5_5 * var5_5 + var4_4 * var4_4;
                                                var4_4 = var6_6 != 0 ? 0 : this.mTouchSlopSquare;
                                                var5_5 = var10_10 == 1 ? 1 : 0;
                                                var5_5 = var12_12 != false && var5_5 != 0 ? 1 : 0;
                                                if (var5_5 != 0) {
                                                    var15_17 = ViewConfiguration.getAmbiguousGestureMultiplier();
                                                    if (var9_9 > var4_4) {
                                                        this.mHandler.removeMessages(2);
                                                        var16_18 = ViewConfiguration.getLongPressTimeout();
                                                        var2_2 = this.mHandler;
                                                        var2_2.sendMessageAtTime(var2_2.obtainMessage(2, 3, 0), var1_1.getDownTime() + (long)((float)var16_18 * var15_17));
                                                    }
                                                    var4_4 = (int)((float)var4_4 * (var15_17 * var15_17));
                                                }
                                                if (var9_9 > var4_4) {
                                                    this.recordGestureClassification(5);
                                                    var14_14 = this.mListener.onScroll(this.mCurrentDownEvent, var1_1, var13_13, var8_8) ? 1 : 0;
                                                    this.mLastFocusX = var7_7;
                                                    this.mLastFocusY = var11_11;
                                                    this.mAlwaysInTapRegion = false;
                                                    this.mHandler.removeMessages(3);
                                                    this.mHandler.removeMessages(1);
                                                    this.mHandler.removeMessages(2);
                                                } else {
                                                    var14_14 = 0;
                                                }
                                                var4_4 = var6_6 != 0 ? 0 : this.mDoubleTapTouchSlopSquare;
                                                if (var9_9 > var4_4) {
                                                    this.mAlwaysInBiggerTapRegion = false;
                                                }
                                            } else if (!(Math.abs(var13_13) >= 1.0f) && !(Math.abs(var8_8) >= 1.0f)) {
                                                var14_14 = 0;
                                            } else {
                                                this.recordGestureClassification(5);
                                                var14_14 = this.mListener.onScroll(this.mCurrentDownEvent, var1_1, var13_13, var8_8);
                                                this.mLastFocusX = var7_7;
                                                this.mLastFocusY = var11_11;
                                            }
                                            var4_4 = var10_10 == 2 ? 1 : 0;
                                            var18_19 = var14_14;
                                            if (var4_4 != 0) {
                                                var18_19 = var14_14;
                                                if (var12_12) {
                                                    this.mHandler.removeMessages(2);
                                                    var2_2 = this.mHandler;
                                                    var2_2.sendMessage(var2_2.obtainMessage(2, 4, 0));
                                                    var18_19 = var14_14;
                                                }
                                            }
                                            break block36;
                                        }
                                        this.mStillDown = false;
                                        var2_2 = MotionEvent.obtain(var1_1);
                                        if (!this.mIsDoubleTapping) break block37;
                                        this.recordGestureClassification(2);
                                        var14_15 = false | this.mDoubleTapListener.onDoubleTapEvent(var1_1);
                                        break block38;
                                    }
                                    if (!this.mInLongPress) break block39;
                                    this.mHandler.removeMessages(3);
                                    this.mInLongPress = false;
                                    ** GOTO lbl-1000
                                }
                                if (!this.mAlwaysInTapRegion || this.mIgnoreNextUpEvent) break block40;
                                this.recordGestureClassification(1);
                                var14_15 = var18_19 = this.mListener.onSingleTapUp(var1_1);
                                if (this.mDeferConfirmSingleTap) {
                                    var19_20 = this.mDoubleTapListener;
                                    var14_15 = var18_19;
                                    if (var19_20 != null) {
                                        var19_20.onSingleTapConfirmed(var1_1);
                                        var14_15 = var18_19;
                                    }
                                }
                                break block38;
                            }
                            if (this.mIgnoreNextUpEvent) ** GOTO lbl-1000
                            var19_20 = this.mVelocityTracker;
                            var4_4 = var1_1.getPointerId(0);
                            var19_20.computeCurrentVelocity(1000, this.mMaximumFlingVelocity);
                            var8_8 = var19_20.getYVelocity(var4_4);
                            var7_7 = var19_20.getXVelocity(var4_4);
                            if (Math.abs(var8_8) > (float)this.mMinimumFlingVelocity || Math.abs(var7_7) > (float)this.mMinimumFlingVelocity) {
                                var14_15 = this.mListener.onFling(this.mCurrentDownEvent, var1_1, var7_7, var8_8);
                            } else lbl-1000: // 3 sources:
                            {
                                var14_15 = 0;
                            }
                        }
                        var19_20 = this.mPreviousUpEvent;
                        if (var19_20 != null) {
                            var19_20.recycle();
                        }
                        this.mPreviousUpEvent = var2_2;
                        var2_2 = this.mVelocityTracker;
                        if (var2_2 != null) {
                            var2_2.recycle();
                            this.mVelocityTracker = null;
                        }
                        this.mIsDoubleTapping = false;
                        this.mDeferConfirmSingleTap = false;
                        this.mIgnoreNextUpEvent = false;
                        this.mHandler.removeMessages(1);
                        this.mHandler.removeMessages(2);
                        var18_19 = var14_15;
                        break block36;
                    }
                    if (this.mDoubleTapListener == null) ** GOTO lbl175
                    var14_16 = this.mHandler.hasMessages(3);
                    if (var14_16) {
                        this.mHandler.removeMessages(3);
                    }
                    if ((var2_2 = this.mCurrentDownEvent) != null && (var19_21 = this.mPreviousUpEvent) != null && var14_16 && this.isConsideredDoubleTap((MotionEvent)var2_2, var19_21, var1_1)) {
                        this.mIsDoubleTapping = true;
                        this.recordGestureClassification(2);
                        var4_4 = false | this.mDoubleTapListener.onDoubleTap(this.mCurrentDownEvent) | this.mDoubleTapListener.onDoubleTapEvent(var1_1);
                    } else {
                        this.mHandler.sendEmptyMessageDelayed(3, GestureDetector.DOUBLE_TAP_TIMEOUT);
lbl175: // 2 sources:
                        var4_4 = 0;
                    }
                    this.mLastFocusX = var7_7;
                    this.mDownFocusX = var7_7;
                    this.mLastFocusY = var11_11;
                    this.mDownFocusY = var11_11;
                    var2_2 = this.mCurrentDownEvent;
                    if (var2_2 != null) {
                        var2_2.recycle();
                    }
                    this.mCurrentDownEvent = MotionEvent.obtain(var1_1);
                    this.mAlwaysInTapRegion = true;
                    this.mAlwaysInBiggerTapRegion = true;
                    this.mStillDown = true;
                    this.mInLongPress = false;
                    this.mDeferConfirmSingleTap = false;
                    this.mHasRecordedClassification = false;
                    if (this.mIsLongpressEnabled) {
                        this.mHandler.removeMessages(2);
                        var2_2 = this.mHandler;
                        var2_2.sendMessageAtTime(var2_2.obtainMessage(2, 3, 0), this.mCurrentDownEvent.getDownTime() + (long)ViewConfiguration.getLongPressTimeout());
                    }
                    this.mHandler.sendEmptyMessageAtTime(1, this.mCurrentDownEvent.getDownTime() + (long)GestureDetector.TAP_TIMEOUT);
                    var18_19 = var4_4 | this.mListener.onDown(var1_1);
                    break block36;
                }
                for (var5_5 = 0; var5_5 < var9_9; ++var5_5) {
                    if (var5_5 == var6_6 || !(this.mVelocityTracker.getXVelocity(var10_10 = var1_1.getPointerId(var5_5)) * var7_7 + this.mVelocityTracker.getYVelocity(var10_10) * var8_8 < 0.0f)) continue;
                    this.mVelocityTracker.clear();
                    break;
                }
            }
            var18_19 = 0;
        }
        if (var18_19 != 0) return (boolean)var18_19;
        var2_2 = this.mInputEventConsistencyVerifier;
        if (var2_2 == null) return (boolean)var18_19;
        var2_2.onUnhandledEvent(var1_1, 0);
        return (boolean)var18_19;
    }

    public void setContextClickListener(OnContextClickListener onContextClickListener) {
        this.mContextClickListener = onContextClickListener;
    }

    public void setIsLongpressEnabled(boolean bl) {
        this.mIsLongpressEnabled = bl;
    }

    public void setOnDoubleTapListener(OnDoubleTapListener onDoubleTapListener) {
        this.mDoubleTapListener = onDoubleTapListener;
    }

    private class GestureHandler
    extends Handler {
        GestureHandler() {
        }

        GestureHandler(Handler handler) {
            super(handler.getLooper());
        }

        /*
         * Enabled aggressive block sorting
         */
        @Override
        public void handleMessage(Message message) {
            int n = message.what;
            if (n == 1) {
                GestureDetector.this.mListener.onShowPress(GestureDetector.this.mCurrentDownEvent);
                return;
            }
            if (n == 2) {
                GestureDetector.this.recordGestureClassification(message.arg1);
                GestureDetector.this.dispatchLongPress();
                return;
            }
            if (n != 3) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Unknown message ");
                stringBuilder.append(message);
                throw new RuntimeException(stringBuilder.toString());
            }
            if (GestureDetector.this.mDoubleTapListener == null) return;
            if (!GestureDetector.this.mStillDown) {
                GestureDetector.this.recordGestureClassification(1);
                GestureDetector.this.mDoubleTapListener.onSingleTapConfirmed(GestureDetector.this.mCurrentDownEvent);
                return;
            }
            GestureDetector.this.mDeferConfirmSingleTap = true;
        }
    }

    public static interface OnContextClickListener {
        public boolean onContextClick(MotionEvent var1);
    }

    public static interface OnDoubleTapListener {
        public boolean onDoubleTap(MotionEvent var1);

        public boolean onDoubleTapEvent(MotionEvent var1);

        public boolean onSingleTapConfirmed(MotionEvent var1);
    }

    public static interface OnGestureListener {
        public boolean onDown(MotionEvent var1);

        public boolean onFling(MotionEvent var1, MotionEvent var2, float var3, float var4);

        public void onLongPress(MotionEvent var1);

        public boolean onScroll(MotionEvent var1, MotionEvent var2, float var3, float var4);

        public void onShowPress(MotionEvent var1);

        public boolean onSingleTapUp(MotionEvent var1);
    }

    public static class SimpleOnGestureListener
    implements OnGestureListener,
    OnDoubleTapListener,
    OnContextClickListener {
        @Override
        public boolean onContextClick(MotionEvent motionEvent) {
            return false;
        }

        @Override
        public boolean onDoubleTap(MotionEvent motionEvent) {
            return false;
        }

        @Override
        public boolean onDoubleTapEvent(MotionEvent motionEvent) {
            return false;
        }

        @Override
        public boolean onDown(MotionEvent motionEvent) {
            return false;
        }

        @Override
        public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2) {
            return false;
        }

        @Override
        public void onLongPress(MotionEvent motionEvent) {
        }

        @Override
        public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2) {
            return false;
        }

        @Override
        public void onShowPress(MotionEvent motionEvent) {
        }

        @Override
        public boolean onSingleTapConfirmed(MotionEvent motionEvent) {
            return false;
        }

        @Override
        public boolean onSingleTapUp(MotionEvent motionEvent) {
            return false;
        }
    }

}

