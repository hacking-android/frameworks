/*
 * Decompiled with CFR 0.145.
 */
package android.inputmethodservice;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Rect;
import android.os.IBinder;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class SoftInputWindow
extends Dialog {
    private static final boolean DEBUG = false;
    private static final String TAG = "SoftInputWindow";
    private final Rect mBounds = new Rect();
    final Callback mCallback;
    final KeyEvent.DispatcherState mDispatcherState;
    final int mGravity;
    final KeyEvent.Callback mKeyEventCallback;
    final String mName;
    final boolean mTakesFocus;
    private int mWindowState = 0;
    final int mWindowType;

    public SoftInputWindow(Context context, String string2, int n, Callback callback, KeyEvent.Callback callback2, KeyEvent.DispatcherState dispatcherState, int n2, int n3, boolean bl) {
        super(context, n);
        this.mName = string2;
        this.mCallback = callback;
        this.mKeyEventCallback = callback2;
        this.mDispatcherState = dispatcherState;
        this.mWindowType = n2;
        this.mGravity = n3;
        this.mTakesFocus = bl;
        this.initDockWindow();
    }

    private void initDockWindow() {
        int n;
        WindowManager.LayoutParams layoutParams = this.getWindow().getAttributes();
        layoutParams.type = this.mWindowType;
        layoutParams.setTitle(this.mName);
        layoutParams.gravity = this.mGravity;
        this.updateWidthHeight(layoutParams);
        this.getWindow().setAttributes(layoutParams);
        int n2 = 266;
        if (!this.mTakesFocus) {
            n = 256 | 8;
        } else {
            n = 256 | 32;
            n2 = 266 | 32;
        }
        this.getWindow().setFlags(n, n2);
    }

    private static String stateToString(int n) {
        if (n != 0) {
            if (n != 1) {
                if (n != 2) {
                    if (n != 3) {
                        if (n == 4) {
                            return "DESTROYED";
                        }
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("Unknown state=");
                        stringBuilder.append(n);
                        throw new IllegalStateException(stringBuilder.toString());
                    }
                    return "REJECTED_AT_LEAST_ONCE";
                }
                return "SHOWN_AT_LEAST_ONCE";
            }
            return "TOKEN_SET";
        }
        return "TOKEN_PENDING";
    }

    private void updateWidthHeight(WindowManager.LayoutParams layoutParams) {
        if (layoutParams.gravity != 48 && layoutParams.gravity != 80) {
            layoutParams.width = -2;
            layoutParams.height = -1;
        } else {
            layoutParams.width = -1;
            layoutParams.height = -2;
        }
    }

    private void updateWindowState(int n) {
        this.mWindowState = n;
    }

    final void dismissForDestroyIfNecessary() {
        int n = this.mWindowState;
        if (n != 0 && n != 1) {
            if (n != 2) {
                if (n != 3) {
                    if (n != 4) {
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("Unexpected state=");
                        stringBuilder.append(this.mWindowState);
                        throw new IllegalStateException(stringBuilder.toString());
                    }
                    throw new IllegalStateException("dismissForDestroyIfNecessary can be called only once");
                }
                Log.i(TAG, "Not trying to dismiss the window because it is most likely unnecessary.");
                this.updateWindowState(4);
                return;
            }
            try {
                this.getWindow().setWindowAnimations(0);
                this.dismiss();
            }
            catch (WindowManager.BadTokenException badTokenException) {
                Log.i(TAG, "Probably the IME window token is already invalidated. No need to dismiss it.");
            }
            this.updateWindowState(4);
            return;
        }
        this.updateWindowState(4);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent motionEvent) {
        this.getWindow().getDecorView().getHitRect(this.mBounds);
        if (motionEvent.isWithinBoundsNoHistory(this.mBounds.left, this.mBounds.top, this.mBounds.right - 1, this.mBounds.bottom - 1)) {
            return super.dispatchTouchEvent(motionEvent);
        }
        motionEvent = motionEvent.clampNoHistory(this.mBounds.left, this.mBounds.top, this.mBounds.right - 1, this.mBounds.bottom - 1);
        boolean bl = super.dispatchTouchEvent(motionEvent);
        motionEvent.recycle();
        return bl;
    }

    public int getGravity() {
        return this.getWindow().getAttributes().gravity;
    }

    @Override
    public void onBackPressed() {
        Callback callback = this.mCallback;
        if (callback != null) {
            callback.onBackPressed();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onKeyDown(int n, KeyEvent keyEvent) {
        KeyEvent.Callback callback = this.mKeyEventCallback;
        if (callback != null && callback.onKeyDown(n, keyEvent)) {
            return true;
        }
        return super.onKeyDown(n, keyEvent);
    }

    @Override
    public boolean onKeyLongPress(int n, KeyEvent keyEvent) {
        KeyEvent.Callback callback = this.mKeyEventCallback;
        if (callback != null && callback.onKeyLongPress(n, keyEvent)) {
            return true;
        }
        return super.onKeyLongPress(n, keyEvent);
    }

    @Override
    public boolean onKeyMultiple(int n, int n2, KeyEvent keyEvent) {
        KeyEvent.Callback callback = this.mKeyEventCallback;
        if (callback != null && callback.onKeyMultiple(n, n2, keyEvent)) {
            return true;
        }
        return super.onKeyMultiple(n, n2, keyEvent);
    }

    @Override
    public boolean onKeyUp(int n, KeyEvent keyEvent) {
        KeyEvent.Callback callback = this.mKeyEventCallback;
        if (callback != null && callback.onKeyUp(n, keyEvent)) {
            return true;
        }
        return super.onKeyUp(n, keyEvent);
    }

    @Override
    public void onWindowFocusChanged(boolean bl) {
        super.onWindowFocusChanged(bl);
        this.mDispatcherState.reset();
    }

    public void setGravity(int n) {
        WindowManager.LayoutParams layoutParams = this.getWindow().getAttributes();
        layoutParams.gravity = n;
        this.updateWidthHeight(layoutParams);
        this.getWindow().setAttributes(layoutParams);
    }

    public void setToken(IBinder object) {
        int n = this.mWindowState;
        if (n != 0) {
            if (n != 1 && n != 2 && n != 3) {
                if (n == 4) {
                    Log.i(TAG, "Ignoring setToken() because window is already destroyed.");
                    return;
                }
                object = new StringBuilder();
                ((StringBuilder)object).append("Unexpected state=");
                ((StringBuilder)object).append(this.mWindowState);
                throw new IllegalStateException(((StringBuilder)object).toString());
            }
            throw new IllegalStateException("setToken can be called only once");
        }
        WindowManager.LayoutParams layoutParams = this.getWindow().getAttributes();
        layoutParams.token = object;
        this.getWindow().setAttributes(layoutParams);
        this.updateWindowState(1);
    }

    @Override
    public final void show() {
        int n = this.mWindowState;
        if (n != 0) {
            if (n != 1 && n != 2) {
                if (n != 3) {
                    if (n == 4) {
                        Log.i(TAG, "Ignoring show() because the window is already destroyed.");
                        return;
                    }
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Unexpected state=");
                    stringBuilder.append(this.mWindowState);
                    throw new IllegalStateException(stringBuilder.toString());
                }
                Log.i(TAG, "Not trying to call show() because it was already rejected once.");
                return;
            }
            try {
                super.show();
                this.updateWindowState(2);
            }
            catch (WindowManager.BadTokenException badTokenException) {
                Log.i(TAG, "Probably the IME window token is already invalidated. show() does nothing.");
                this.updateWindowState(3);
            }
            return;
        }
        throw new IllegalStateException("Window token is not set yet.");
    }

    public static interface Callback {
        public void onBackPressed();
    }

    @Retention(value=RetentionPolicy.SOURCE)
    private static @interface SoftInputWindowState {
        public static final int DESTROYED = 4;
        public static final int REJECTED_AT_LEAST_ONCE = 3;
        public static final int SHOWN_AT_LEAST_ONCE = 2;
        public static final int TOKEN_PENDING = 0;
        public static final int TOKEN_SET = 1;
    }

}

