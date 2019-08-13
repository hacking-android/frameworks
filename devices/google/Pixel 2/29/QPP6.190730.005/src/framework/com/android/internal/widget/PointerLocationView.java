/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.widget;

import android.annotation.UnsupportedAppUsage;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Region;
import android.hardware.input.InputManager;
import android.os.Handler;
import android.os.RemoteException;
import android.os.SystemProperties;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.Slog;
import android.view.DisplayCutout;
import android.view.ISystemGestureExclusionListener;
import android.view.InputDevice;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.WindowInsets;
import android.view.WindowManagerGlobal;
import android.view.WindowManagerPolicyConstants;
import com.android.internal.widget._$$Lambda$PointerLocationView$1$utsjc18145VWAe5S9LSLblHeqxc;
import java.util.ArrayList;

public class PointerLocationView
extends View
implements InputManager.InputDeviceListener,
WindowManagerPolicyConstants.PointerEventListener {
    private static final String ALT_STRATEGY_PROPERY_KEY = "debug.velocitytracker.alt";
    private static final String TAG = "Pointer";
    private int mActivePointerId;
    private final VelocityTracker mAltVelocity;
    @UnsupportedAppUsage
    private boolean mCurDown;
    @UnsupportedAppUsage
    private int mCurNumPointers;
    private final Paint mCurrentPointPaint;
    private int mHeaderBottom;
    private int mHeaderPaddingTop = 0;
    private final InputManager mIm;
    @UnsupportedAppUsage
    private int mMaxNumPointers;
    private final Paint mPaint;
    private final Paint mPathPaint;
    @UnsupportedAppUsage
    private final ArrayList<PointerState> mPointers = new ArrayList();
    @UnsupportedAppUsage
    private boolean mPrintCoords = true;
    private RectF mReusableOvalRect = new RectF();
    private final Region mSystemGestureExclusion = new Region();
    private ISystemGestureExclusionListener mSystemGestureExclusionListener = new ISystemGestureExclusionListener.Stub(){

        public /* synthetic */ void lambda$onSystemGestureExclusionChanged$0$PointerLocationView$1(Region region) {
            PointerLocationView.this.mSystemGestureExclusion.set(region);
            region.recycle();
            PointerLocationView.this.invalidate();
        }

        @Override
        public void onSystemGestureExclusionChanged(int n, Region region) {
            region = Region.obtain(region);
            Handler handler = PointerLocationView.this.getHandler();
            if (handler != null) {
                handler.post(new _$$Lambda$PointerLocationView$1$utsjc18145VWAe5S9LSLblHeqxc(this, region));
            }
        }
    };
    private final Paint mSystemGestureExclusionPaint;
    private final Path mSystemGestureExclusionPath = new Path();
    private final Paint mTargetPaint;
    private final MotionEvent.PointerCoords mTempCoords = new MotionEvent.PointerCoords();
    private final FasterStringBuilder mText = new FasterStringBuilder();
    private final Paint mTextBackgroundPaint;
    private final Paint mTextLevelPaint;
    private final Paint.FontMetricsInt mTextMetrics = new Paint.FontMetricsInt();
    private final Paint mTextPaint;
    private final ViewConfiguration mVC;
    private final VelocityTracker mVelocity;

    public PointerLocationView(Context object) {
        super((Context)object);
        this.setFocusableInTouchMode(true);
        this.mIm = ((Context)object).getSystemService(InputManager.class);
        this.mVC = ViewConfiguration.get((Context)object);
        this.mTextPaint = new Paint();
        this.mTextPaint.setAntiAlias(true);
        this.mTextPaint.setTextSize(this.getResources().getDisplayMetrics().density * 10.0f);
        this.mTextPaint.setARGB(255, 0, 0, 0);
        this.mTextBackgroundPaint = new Paint();
        this.mTextBackgroundPaint.setAntiAlias(false);
        this.mTextBackgroundPaint.setARGB(128, 255, 255, 255);
        this.mTextLevelPaint = new Paint();
        this.mTextLevelPaint.setAntiAlias(false);
        this.mTextLevelPaint.setARGB(192, 255, 0, 0);
        this.mPaint = new Paint();
        this.mPaint.setAntiAlias(true);
        this.mPaint.setARGB(255, 255, 255, 255);
        this.mPaint.setStyle(Paint.Style.STROKE);
        this.mPaint.setStrokeWidth(2.0f);
        this.mCurrentPointPaint = new Paint();
        this.mCurrentPointPaint.setAntiAlias(true);
        this.mCurrentPointPaint.setARGB(255, 255, 0, 0);
        this.mCurrentPointPaint.setStyle(Paint.Style.STROKE);
        this.mCurrentPointPaint.setStrokeWidth(2.0f);
        this.mTargetPaint = new Paint();
        this.mTargetPaint.setAntiAlias(false);
        this.mTargetPaint.setARGB(255, 0, 0, 192);
        this.mPathPaint = new Paint();
        this.mPathPaint.setAntiAlias(false);
        this.mPathPaint.setARGB(255, 0, 96, 255);
        this.mPaint.setStyle(Paint.Style.STROKE);
        this.mPaint.setStrokeWidth(1.0f);
        this.mSystemGestureExclusionPaint = new Paint();
        this.mSystemGestureExclusionPaint.setARGB(25, 255, 0, 0);
        this.mSystemGestureExclusionPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        object = new PointerState();
        this.mPointers.add((PointerState)object);
        this.mActivePointerId = 0;
        this.mVelocity = VelocityTracker.obtain();
        String string2 = SystemProperties.get(ALT_STRATEGY_PROPERY_KEY);
        if (string2.length() != 0) {
            object = new StringBuilder();
            ((StringBuilder)object).append("Comparing default velocity tracker strategy with ");
            ((StringBuilder)object).append(string2);
            Log.d(TAG, ((StringBuilder)object).toString());
            this.mAltVelocity = VelocityTracker.obtain(string2);
        } else {
            this.mAltVelocity = null;
        }
    }

    private void drawOval(Canvas canvas, float f, float f2, float f3, float f4, float f5, Paint paint) {
        canvas.save(1);
        canvas.rotate((float)((double)(180.0f * f5) / 3.141592653589793), f, f2);
        RectF rectF = this.mReusableOvalRect;
        rectF.left = f - f4 / 2.0f;
        rectF.right = f4 / 2.0f + f;
        rectF.top = f2 - f3 / 2.0f;
        rectF.bottom = f3 / 2.0f + f2;
        canvas.drawOval(rectF, paint);
        canvas.restore();
    }

    private void logCoords(String string2, int n, int n2, MotionEvent.PointerCoords pointerCoords, int n3, MotionEvent motionEvent) {
        String string3;
        int n4 = motionEvent.getToolType(n2);
        int n5 = motionEvent.getButtonState();
        switch (n & 255) {
            default: {
                string3 = Integer.toString(n);
                break;
            }
            case 10: {
                string3 = "HOVER EXIT";
                break;
            }
            case 9: {
                string3 = "HOVER ENTER";
                break;
            }
            case 8: {
                string3 = "SCROLL";
                break;
            }
            case 7: {
                string3 = "HOVER MOVE";
                break;
            }
            case 6: {
                if (n2 == (n & 65280) >> 8) {
                    string3 = "UP";
                    break;
                }
                string3 = "MOVE";
                break;
            }
            case 5: {
                if (n2 == (n & 65280) >> 8) {
                    string3 = "DOWN";
                    break;
                }
                string3 = "MOVE";
                break;
            }
            case 4: {
                string3 = "OUTSIDE";
                break;
            }
            case 3: {
                string3 = "CANCEL";
                break;
            }
            case 2: {
                string3 = "MOVE";
                break;
            }
            case 1: {
                string3 = "UP";
                break;
            }
            case 0: {
                string3 = "DOWN";
            }
        }
        Log.i(TAG, this.mText.clear().append(string2).append(" id ").append(n3 + 1).append(": ").append(string3).append(" (").append(pointerCoords.x, 3).append(", ").append(pointerCoords.y, 3).append(") Pressure=").append(pointerCoords.pressure, 3).append(" Size=").append(pointerCoords.size, 3).append(" TouchMajor=").append(pointerCoords.touchMajor, 3).append(" TouchMinor=").append(pointerCoords.touchMinor, 3).append(" ToolMajor=").append(pointerCoords.toolMajor, 3).append(" ToolMinor=").append(pointerCoords.toolMinor, 3).append(" Orientation=").append((float)((double)(pointerCoords.orientation * 180.0f) / 3.141592653589793), 1).append("deg").append(" Tilt=").append((float)((double)(pointerCoords.getAxisValue(25) * 180.0f) / 3.141592653589793), 1).append("deg").append(" Distance=").append(pointerCoords.getAxisValue(24), 1).append(" VScroll=").append(pointerCoords.getAxisValue(9), 1).append(" HScroll=").append(pointerCoords.getAxisValue(10), 1).append(" BoundingBox=[(").append(motionEvent.getAxisValue(32), 3).append(", ").append(motionEvent.getAxisValue(33), 3).append(")").append(", (").append(motionEvent.getAxisValue(34), 3).append(", ").append(motionEvent.getAxisValue(35), 3).append(")]").append(" ToolType=").append(MotionEvent.toolTypeToString(n4)).append(" ButtonState=").append(MotionEvent.buttonStateToString(n5)).toString());
    }

    private void logInputDeviceState(int n, String string2) {
        Object object = this.mIm.getInputDevice(n);
        if (object != null) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(string2);
            stringBuilder.append(": ");
            stringBuilder.append(object);
            Log.i(TAG, stringBuilder.toString());
        } else {
            object = new StringBuilder();
            ((StringBuilder)object).append(string2);
            ((StringBuilder)object).append(": ");
            ((StringBuilder)object).append(n);
            Log.i(TAG, ((StringBuilder)object).toString());
        }
    }

    private void logInputDevices() {
        int[] arrn = InputDevice.getDeviceIds();
        for (int i = 0; i < arrn.length; ++i) {
            this.logInputDeviceState(arrn[i], "Device Enumerated");
        }
    }

    private void logMotionEvent(String string2, MotionEvent motionEvent) {
        int n;
        int n2;
        int n3 = motionEvent.getAction();
        int n4 = motionEvent.getHistorySize();
        int n5 = motionEvent.getPointerCount();
        for (n2 = 0; n2 < n4; ++n2) {
            for (n = 0; n < n5; ++n) {
                int n6 = motionEvent.getPointerId(n);
                motionEvent.getHistoricalPointerCoords(n, n2, this.mTempCoords);
                this.logCoords(string2, n3, n, this.mTempCoords, n6, motionEvent);
            }
        }
        for (n2 = 0; n2 < n5; ++n2) {
            n = motionEvent.getPointerId(n2);
            motionEvent.getPointerCoords(n2, this.mTempCoords);
            this.logCoords(string2, n3, n2, this.mTempCoords, n, motionEvent);
        }
    }

    private static boolean shouldLogKey(int n) {
        boolean bl;
        block3 : {
            bl = true;
            switch (n) {
                default: {
                    if (!KeyEvent.isGamepadButton(n) && !KeyEvent.isModifierKey(n)) break;
                    break block3;
                }
                case 19: 
                case 20: 
                case 21: 
                case 22: 
                case 23: {
                    return true;
                }
            }
            bl = false;
        }
        return bl;
    }

    private static boolean shouldShowSystemGestureExclusion() {
        return SystemProperties.getBoolean("debug.pointerlocation.showexclusion", false);
    }

    @Override
    public WindowInsets onApplyWindowInsets(WindowInsets windowInsets) {
        this.mHeaderPaddingTop = windowInsets.getDisplayCutout() != null ? windowInsets.getDisplayCutout().getSafeInsetTop() : 0;
        return super.onApplyWindowInsets(windowInsets);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.mIm.registerInputDeviceListener(this, this.getHandler());
        if (PointerLocationView.shouldShowSystemGestureExclusion()) {
            try {
                WindowManagerGlobal.getWindowManagerService().registerSystemGestureExclusionListener(this.mSystemGestureExclusionListener, this.mContext.getDisplayId());
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        } else {
            this.mSystemGestureExclusion.setEmpty();
        }
        this.logInputDevices();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.mIm.unregisterInputDeviceListener(this);
        try {
            WindowManagerGlobal.getWindowManagerService().unregisterSystemGestureExclusionListener(this.mSystemGestureExclusionListener, this.mContext.getDisplayId());
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        float f;
        float f2;
        float f3;
        Paint paint;
        float f4;
        PointerState pointerState;
        int n;
        int n2 = this.getWidth();
        int n3 = n2 / 7;
        int n4 = this.mHeaderPaddingTop - this.mTextMetrics.ascent + 1;
        int n5 = this.mHeaderBottom;
        int n6 = this.mPointers.size();
        if (!this.mSystemGestureExclusion.isEmpty()) {
            this.mSystemGestureExclusionPath.reset();
            this.mSystemGestureExclusion.getBoundaryPath(this.mSystemGestureExclusionPath);
            canvas.drawPath(this.mSystemGestureExclusionPath, this.mSystemGestureExclusionPaint);
        }
        if ((n = this.mActivePointerId) >= 0) {
            pointerState = this.mPointers.get(n);
            canvas.drawRect(0.0f, this.mHeaderPaddingTop, n3 - 1, n5, this.mTextBackgroundPaint);
            canvas.drawText(this.mText.clear().append("P: ").append(this.mCurNumPointers).append(" / ").append(this.mMaxNumPointers).toString(), 1.0f, n4, this.mTextPaint);
            n = pointerState.mTraceCount;
            if (this.mCurDown && pointerState.mCurDown || n == 0) {
                canvas.drawRect(n3, this.mHeaderPaddingTop, n3 * 2 - 1, n5, this.mTextBackgroundPaint);
                canvas.drawText(this.mText.clear().append("X: ").append(PointerState.access$200((PointerState)pointerState).x, 1).toString(), n3 + 1, n4, this.mTextPaint);
                canvas.drawRect(n3 * 2, this.mHeaderPaddingTop, n3 * 3 - 1, n5, this.mTextBackgroundPaint);
                canvas.drawText(this.mText.clear().append("Y: ").append(PointerState.access$200((PointerState)pointerState).y, 1).toString(), n3 * 2 + 1, n4, this.mTextPaint);
            } else {
                f4 = pointerState.mTraceX[n - 1] - pointerState.mTraceX[0];
                f3 = pointerState.mTraceY[n - 1] - pointerState.mTraceY[0];
                float f5 = n3;
                float f6 = this.mHeaderPaddingTop;
                f = n3 * 2 - 1;
                f2 = n5;
                paint = Math.abs(f4) < (float)this.mVC.getScaledTouchSlop() ? this.mTextBackgroundPaint : this.mTextLevelPaint;
                canvas.drawRect(f5, f6, f, f2, paint);
                canvas.drawText(this.mText.clear().append("dX: ").append(f4, 1).toString(), n3 + 1, n4, this.mTextPaint);
                f5 = n3 * 2;
                f4 = this.mHeaderPaddingTop;
                f2 = n3 * 3 - 1;
                f = n5;
                paint = Math.abs(f3) < (float)this.mVC.getScaledTouchSlop() ? this.mTextBackgroundPaint : this.mTextLevelPaint;
                canvas.drawRect(f5, f4, f2, f, paint);
                canvas.drawText(this.mText.clear().append("dY: ").append(f3, 1).toString(), n3 * 2 + 1, n4, this.mTextPaint);
            }
            canvas.drawRect(n3 * 3, this.mHeaderPaddingTop, n3 * 4 - 1, n5, this.mTextBackgroundPaint);
            canvas.drawText(this.mText.clear().append("Xv: ").append(pointerState.mXVelocity, 3).toString(), n3 * 3 + 1, n4, this.mTextPaint);
            canvas.drawRect(n3 * 4, this.mHeaderPaddingTop, n3 * 5 - 1, n5, this.mTextBackgroundPaint);
            canvas.drawText(this.mText.clear().append("Yv: ").append(pointerState.mYVelocity, 3).toString(), n3 * 4 + 1, n4, this.mTextPaint);
            canvas.drawRect(n3 * 5, this.mHeaderPaddingTop, n3 * 6 - 1, n5, this.mTextBackgroundPaint);
            canvas.drawRect(n3 * 5, this.mHeaderPaddingTop, (float)(n3 * 5) + PointerState.access$200((PointerState)pointerState).pressure * (float)n3 - 1.0f, n5, this.mTextLevelPaint);
            canvas.drawText(this.mText.clear().append("Prs: ").append(PointerState.access$200((PointerState)pointerState).pressure, 2).toString(), n3 * 5 + 1, n4, this.mTextPaint);
            canvas.drawRect(n3 * 6, this.mHeaderPaddingTop, n2, n5, this.mTextBackgroundPaint);
            canvas.drawRect(n3 * 6, this.mHeaderPaddingTop, (float)(n3 * 6) + PointerState.access$200((PointerState)pointerState).size * (float)n3 - 1.0f, n5, this.mTextLevelPaint);
            canvas.drawText(this.mText.clear().append("Size: ").append(PointerState.access$200((PointerState)pointerState).size, 2).toString(), n3 * 6 + 1, n4, this.mTextPaint);
        }
        for (n = 0; n < n6; ++n) {
            pointerState = this.mPointers.get(n);
            int n7 = pointerState.mTraceCount;
            paint = this.mPaint;
            int n8 = 128;
            paint.setARGB(255, 128, 255, 255);
            n5 = 0;
            n4 = 0;
            f3 = 0.0f;
            f4 = 0.0f;
            for (int i = 0; i < n7; ++i) {
                f2 = pointerState.mTraceX[i];
                f = pointerState.mTraceY[i];
                if (Float.isNaN(f2)) {
                    n5 = 0;
                    continue;
                }
                if (n5 != 0) {
                    canvas.drawLine(f3, f4, f2, f, this.mPathPaint);
                    paint = pointerState.mTraceCurrent[i] ? this.mCurrentPointPaint : this.mPaint;
                    canvas.drawPoint(f3, f4, paint);
                    n4 = 1;
                }
                f3 = f2;
                f4 = f;
                n5 = 1;
            }
            if (n4 != 0) {
                this.mPaint.setARGB(255, 255, 64, n8);
                canvas.drawLine(f3, f4, f3 + pointerState.mXVelocity * 16.0f, f4 + pointerState.mYVelocity * 16.0f, this.mPaint);
                if (this.mAltVelocity != null) {
                    this.mPaint.setARGB(255, 64, 255, n8);
                    canvas.drawLine(f3, f4, f3 + pointerState.mAltXVelocity * 16.0f, f4 + 16.0f * pointerState.mAltYVelocity, this.mPaint);
                }
            }
            if (!this.mCurDown || !pointerState.mCurDown) continue;
            canvas.drawLine(0.0f, PointerState.access$200((PointerState)pointerState).y, this.getWidth(), PointerState.access$200((PointerState)pointerState).y, this.mTargetPaint);
            canvas.drawLine(PointerState.access$200((PointerState)pointerState).x, 0.0f, PointerState.access$200((PointerState)pointerState).x, this.getHeight(), this.mTargetPaint);
            n5 = (int)(PointerState.access$200((PointerState)pointerState).pressure * 255.0f);
            this.mPaint.setARGB(255, n5, 255, 255 - n5);
            canvas.drawPoint(PointerState.access$200((PointerState)pointerState).x, PointerState.access$200((PointerState)pointerState).y, this.mPaint);
            this.mPaint.setARGB(255, n5, 255 - n5, n8);
            this.drawOval(canvas, PointerState.access$200((PointerState)pointerState).x, PointerState.access$200((PointerState)pointerState).y, PointerState.access$200((PointerState)pointerState).touchMajor, PointerState.access$200((PointerState)pointerState).touchMinor, PointerState.access$200((PointerState)pointerState).orientation, this.mPaint);
            this.mPaint.setARGB(255, n5, 128, 255 - n5);
            this.drawOval(canvas, PointerState.access$200((PointerState)pointerState).x, PointerState.access$200((PointerState)pointerState).y, PointerState.access$200((PointerState)pointerState).toolMajor, PointerState.access$200((PointerState)pointerState).toolMinor, PointerState.access$200((PointerState)pointerState).orientation, this.mPaint);
            f3 = PointerState.access$200((PointerState)pointerState).toolMajor * 0.7f;
            if (f3 < 20.0f) {
                f3 = 20.0f;
            }
            this.mPaint.setARGB(255, n5, 255, 0);
            f4 = (float)(Math.sin(PointerState.access$200((PointerState)pointerState).orientation) * (double)f3);
            f = (float)(-Math.cos(PointerState.access$200((PointerState)pointerState).orientation) * (double)f3);
            if (pointerState.mToolType != 2 && pointerState.mToolType != 4) {
                canvas.drawLine(PointerState.access$200((PointerState)pointerState).x - f4, PointerState.access$200((PointerState)pointerState).y - f, PointerState.access$200((PointerState)pointerState).x + f4, PointerState.access$200((PointerState)pointerState).y + f, this.mPaint);
            } else {
                canvas.drawLine(PointerState.access$200((PointerState)pointerState).x, PointerState.access$200((PointerState)pointerState).y, PointerState.access$200((PointerState)pointerState).x + f4, PointerState.access$200((PointerState)pointerState).y + f, this.mPaint);
            }
            f3 = (float)Math.sin(pointerState.mCoords.getAxisValue(25));
            canvas.drawCircle(PointerState.access$200((PointerState)pointerState).x + f4 * f3, PointerState.access$200((PointerState)pointerState).y + f * f3, 3.0f, this.mPaint);
            if (!pointerState.mHasBoundingBox) continue;
            canvas.drawRect(pointerState.mBoundingLeft, pointerState.mBoundingTop, pointerState.mBoundingRight, pointerState.mBoundingBottom, this.mPaint);
        }
    }

    @Override
    public boolean onGenericMotionEvent(MotionEvent motionEvent) {
        int n = motionEvent.getSource();
        if ((n & 2) != 0) {
            this.onPointerEvent(motionEvent);
        } else if ((n & 16) != 0) {
            this.logMotionEvent("Joystick", motionEvent);
        } else if ((n & 8) != 0) {
            this.logMotionEvent("Position", motionEvent);
        } else {
            this.logMotionEvent("Generic", motionEvent);
        }
        return true;
    }

    @Override
    public void onInputDeviceAdded(int n) {
        this.logInputDeviceState(n, "Device Added");
    }

    @Override
    public void onInputDeviceChanged(int n) {
        this.logInputDeviceState(n, "Device Changed");
    }

    @Override
    public void onInputDeviceRemoved(int n) {
        this.logInputDeviceState(n, "Device Removed");
    }

    @Override
    public boolean onKeyDown(int n, KeyEvent keyEvent) {
        if (PointerLocationView.shouldLogKey(n)) {
            n = keyEvent.getRepeatCount();
            if (n == 0) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Key Down: ");
                stringBuilder.append(keyEvent);
                Log.i(TAG, stringBuilder.toString());
            } else {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Key Repeat #");
                stringBuilder.append(n);
                stringBuilder.append(": ");
                stringBuilder.append(keyEvent);
                Log.i(TAG, stringBuilder.toString());
            }
            return true;
        }
        return super.onKeyDown(n, keyEvent);
    }

    @Override
    public boolean onKeyUp(int n, KeyEvent keyEvent) {
        if (PointerLocationView.shouldLogKey(n)) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Key Up: ");
            stringBuilder.append(keyEvent);
            Log.i(TAG, stringBuilder.toString());
            return true;
        }
        return super.onKeyUp(n, keyEvent);
    }

    @Override
    protected void onMeasure(int n, int n2) {
        super.onMeasure(n, n2);
        this.mTextPaint.getFontMetricsInt(this.mTextMetrics);
        this.mHeaderBottom = this.mHeaderPaddingTop - this.mTextMetrics.ascent + this.mTextMetrics.descent + 2;
    }

    @Override
    public void onPointerEvent(MotionEvent object) {
        int n;
        int n2;
        Object object2;
        Object object3;
        int n3 = ((MotionEvent)object).getAction();
        int n4 = this.mPointers.size();
        int n5 = 1;
        if (n3 != 0 && (n3 & 255) != 5) {
            n = n4;
        } else {
            if (n3 == 0) {
                for (n = 0; n < n4; ++n) {
                    object2 = this.mPointers.get(n);
                    ((PointerState)object2).clearTrace();
                    ((PointerState)object2).mCurDown = false;
                }
                this.mCurDown = true;
                this.mCurNumPointers = 0;
                this.mMaxNumPointers = 0;
                this.mVelocity.clear();
                object2 = this.mAltVelocity;
                if (object2 != null) {
                    ((VelocityTracker)object2).clear();
                }
            }
            ++this.mCurNumPointers;
            n = this.mMaxNumPointers;
            n2 = this.mCurNumPointers;
            if (n < n2) {
                this.mMaxNumPointers = n2;
            }
            n = ((MotionEvent)object).getPointerId((n3 & 65280) >> 8);
            while (n4 <= n) {
                object2 = new PointerState();
                this.mPointers.add((PointerState)object2);
                ++n4;
            }
            n2 = this.mActivePointerId;
            if (n2 < 0 || !this.mPointers.get(n2).mCurDown) {
                this.mActivePointerId = n;
            }
            object3 = this.mPointers.get(n);
            ((PointerState)object3).mCurDown = true;
            object2 = InputDevice.getDevice(((MotionEvent)object).getDeviceId());
            boolean bl = object2 != null && ((InputDevice)object2).getMotionRange(32) != null;
            ((PointerState)object3).mHasBoundingBox = bl;
            n = n4;
        }
        int n6 = ((MotionEvent)object).getPointerCount();
        this.mVelocity.addMovement((MotionEvent)object);
        this.mVelocity.computeCurrentVelocity(1);
        object2 = this.mAltVelocity;
        if (object2 != null) {
            ((VelocityTracker)object2).addMovement((MotionEvent)object);
            this.mAltVelocity.computeCurrentVelocity(1);
        }
        n2 = ((MotionEvent)object).getHistorySize();
        for (n4 = 0; n4 < n2; ++n4) {
            for (int i = 0; i < n6; ++i) {
                int n7 = ((MotionEvent)object).getPointerId(i);
                object2 = this.mCurDown ? this.mPointers.get(n7) : null;
                object3 = object2 != null ? ((PointerState)object2).mCoords : this.mTempCoords;
                ((MotionEvent)object).getHistoricalPointerCoords(i, n4, (MotionEvent.PointerCoords)object3);
                if (this.mPrintCoords) {
                    this.logCoords(TAG, n3, i, (MotionEvent.PointerCoords)object3, n7, (MotionEvent)object);
                }
                if (object2 == null) continue;
                ((PointerState)object2).addTrace(((MotionEvent.PointerCoords)object3).x, ((MotionEvent.PointerCoords)object3).y, false);
            }
        }
        for (n4 = 0; n4 < n6; ++n4) {
            n2 = ((MotionEvent)object).getPointerId(n4);
            object2 = this.mCurDown ? this.mPointers.get(n2) : null;
            object3 = object2 != null ? ((PointerState)object2).mCoords : this.mTempCoords;
            ((MotionEvent)object).getPointerCoords(n4, (MotionEvent.PointerCoords)object3);
            if (this.mPrintCoords) {
                this.logCoords(TAG, n3, n4, (MotionEvent.PointerCoords)object3, n2, (MotionEvent)object);
            }
            if (object2 == null) continue;
            ((PointerState)object2).addTrace(((MotionEvent.PointerCoords)object3).x, ((MotionEvent.PointerCoords)object3).y, true);
            ((PointerState)object2).mXVelocity = this.mVelocity.getXVelocity(n2);
            ((PointerState)object2).mYVelocity = this.mVelocity.getYVelocity(n2);
            this.mVelocity.getEstimator(n2, ((PointerState)object2).mEstimator);
            object3 = this.mAltVelocity;
            if (object3 != null) {
                ((PointerState)object2).mAltXVelocity = ((VelocityTracker)object3).getXVelocity(n2);
                ((PointerState)object2).mAltYVelocity = this.mAltVelocity.getYVelocity(n2);
                this.mAltVelocity.getEstimator(n2, ((PointerState)object2).mAltEstimator);
            }
            ((PointerState)object2).mToolType = ((MotionEvent)object).getToolType(n4);
            if (!((PointerState)object2).mHasBoundingBox) continue;
            ((PointerState)object2).mBoundingLeft = ((MotionEvent)object).getAxisValue(32, n4);
            ((PointerState)object2).mBoundingTop = ((MotionEvent)object).getAxisValue(33, n4);
            ((PointerState)object2).mBoundingRight = ((MotionEvent)object).getAxisValue(34, n4);
            ((PointerState)object2).mBoundingBottom = ((MotionEvent)object).getAxisValue(35, n4);
        }
        if (n3 == 1 || n3 == 3 || (n3 & 255) == 6) {
            n2 = (65280 & n3) >> 8;
            n4 = ((MotionEvent)object).getPointerId(n2);
            if (n4 >= n) {
                object = new StringBuilder();
                ((StringBuilder)object).append("Got pointer ID out of bounds: id=");
                ((StringBuilder)object).append(n4);
                ((StringBuilder)object).append(" arraysize=");
                ((StringBuilder)object).append(n);
                ((StringBuilder)object).append(" pointerindex=");
                ((StringBuilder)object).append(n2);
                ((StringBuilder)object).append(" action=0x");
                ((StringBuilder)object).append(Integer.toHexString(n3));
                Slog.wtf(TAG, ((StringBuilder)object).toString());
                return;
            }
            object2 = this.mPointers.get(n4);
            ((PointerState)object2).mCurDown = false;
            if (n3 != 1 && n3 != 3) {
                --this.mCurNumPointers;
                if (this.mActivePointerId == n4) {
                    n4 = n2 == 0 ? n5 : 0;
                    this.mActivePointerId = ((MotionEvent)object).getPointerId(n4);
                }
                ((PointerState)object2).addTrace(Float.NaN, Float.NaN, false);
            } else {
                this.mCurDown = false;
                this.mCurNumPointers = 0;
            }
        }
        this.invalidate();
    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        this.onPointerEvent(motionEvent);
        if (motionEvent.getAction() == 0 && !this.isFocused()) {
            this.requestFocus();
        }
        return true;
    }

    @Override
    public boolean onTrackballEvent(MotionEvent motionEvent) {
        this.logMotionEvent("Trackball", motionEvent);
        return true;
    }

    public void setPrintCoords(boolean bl) {
        this.mPrintCoords = bl;
    }

    private static final class FasterStringBuilder {
        private char[] mChars = new char[64];
        private int mLength;

        private int reserve(int n) {
            int n2 = this.mLength;
            int n3 = this.mLength;
            char[] arrc = this.mChars;
            int n4 = arrc.length;
            if (n3 + n > n4) {
                char[] arrc2 = new char[n4 * 2];
                System.arraycopy(arrc, 0, arrc2, 0, n2);
                this.mChars = arrc2;
            }
            return n2;
        }

        public FasterStringBuilder append(float f, int n) {
            int n2 = 1;
            for (int i = 0; i < n; ++i) {
                n2 *= 10;
            }
            if ((int)(f = (float)(Math.rint((float)n2 * f) / (double)n2)) == 0 && f < 0.0f) {
                this.append("-");
            }
            this.append((int)f);
            if (n != 0) {
                this.append(".");
                f = Math.abs(f);
                f = (float)((double)f - Math.floor(f));
                this.append((int)((float)n2 * f), n);
            }
            return this;
        }

        public FasterStringBuilder append(int n) {
            return this.append(n, 0);
        }

        public FasterStringBuilder append(int n, int n2) {
            int n3;
            int n4;
            int n5;
            int n6 = n < 0 ? 1 : 0;
            int n7 = n;
            if (n6 != 0) {
                n7 = n = -n;
                if (n < 0) {
                    this.append("-2147483648");
                    return this;
                }
            }
            n = this.reserve(11);
            char[] arrc = this.mChars;
            if (n7 == 0) {
                arrc[n] = (char)48;
                ++this.mLength;
                return this;
            }
            if (n6 != 0) {
                n6 = n + 1;
                arrc[n] = (char)45;
            } else {
                n6 = n;
            }
            n = 1000000000;
            int n8 = 10;
            do {
                n5 = n6;
                n4 = n;
                n3 = n7;
                if (n7 >= n) break;
                n5 = n / 10;
                n4 = n8 - 1;
                n = n5;
                n8 = n4;
                if (n4 >= n2) continue;
                arrc[n6] = (char)48;
                ++n6;
                n = n5;
                n8 = n4;
            } while (true);
            do {
                n2 = n3 / n4;
                n3 -= n2 * n4;
                n = n5 + 1;
                arrc[n5] = (char)(n2 + 48);
                if ((n4 /= 10) == 0) {
                    this.mLength = n;
                    return this;
                }
                n5 = n;
            } while (true);
        }

        public FasterStringBuilder append(String string2) {
            int n = string2.length();
            int n2 = this.reserve(n);
            string2.getChars(0, n, this.mChars, n2);
            this.mLength += n;
            return this;
        }

        public FasterStringBuilder clear() {
            this.mLength = 0;
            return this;
        }

        public String toString() {
            return new String(this.mChars, 0, this.mLength);
        }
    }

    public static class PointerState {
        private VelocityTracker.Estimator mAltEstimator = new VelocityTracker.Estimator();
        private float mAltXVelocity;
        private float mAltYVelocity;
        private float mBoundingBottom;
        private float mBoundingLeft;
        private float mBoundingRight;
        private float mBoundingTop;
        private MotionEvent.PointerCoords mCoords = new MotionEvent.PointerCoords();
        @UnsupportedAppUsage
        private boolean mCurDown;
        private VelocityTracker.Estimator mEstimator = new VelocityTracker.Estimator();
        private boolean mHasBoundingBox;
        private int mToolType;
        private int mTraceCount;
        private boolean[] mTraceCurrent = new boolean[32];
        private float[] mTraceX = new float[32];
        private float[] mTraceY = new float[32];
        private float mXVelocity;
        private float mYVelocity;

        public void addTrace(float f, float f2, boolean bl) {
            Object[] arrobject;
            int n = this.mTraceCount;
            float[] arrf = this.mTraceX;
            int n2 = arrf.length;
            if (n == n2) {
                arrobject = new float[n2 *= 2];
                System.arraycopy(arrf, 0, arrobject, 0, n);
                this.mTraceX = arrobject;
                arrobject = new float[n2];
                System.arraycopy(this.mTraceY, 0, arrobject, 0, this.mTraceCount);
                this.mTraceY = arrobject;
                arrobject = new boolean[n2];
                System.arraycopy(this.mTraceCurrent, 0, arrobject, 0, this.mTraceCount);
                this.mTraceCurrent = arrobject;
            }
            arrobject = this.mTraceX;
            n = this.mTraceCount;
            arrobject[n] = f;
            this.mTraceY[n] = f2;
            this.mTraceCurrent[n] = bl;
            this.mTraceCount = n + 1;
        }

        public void clearTrace() {
            this.mTraceCount = 0;
        }
    }

}

