/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  dalvik.system.CloseGuard
 */
package android.view;

import android.annotation.UnsupportedAppUsage;
import android.os.Looper;
import android.os.MessageQueue;
import android.util.Log;
import android.view.InputChannel;
import android.view.InputEvent;
import android.view.KeyEvent;
import android.view.MotionEvent;
import dalvik.system.CloseGuard;
import java.lang.ref.WeakReference;

public abstract class InputEventSender {
    private static final String TAG = "InputEventSender";
    private final CloseGuard mCloseGuard = CloseGuard.get();
    private InputChannel mInputChannel;
    private MessageQueue mMessageQueue;
    private long mSenderPtr;

    public InputEventSender(InputChannel inputChannel, Looper looper) {
        if (inputChannel != null) {
            if (looper != null) {
                this.mInputChannel = inputChannel;
                this.mMessageQueue = looper.getQueue();
                this.mSenderPtr = InputEventSender.nativeInit(new WeakReference<InputEventSender>(this), inputChannel, this.mMessageQueue);
                this.mCloseGuard.open("dispose");
                return;
            }
            throw new IllegalArgumentException("looper must not be null");
        }
        throw new IllegalArgumentException("inputChannel must not be null");
    }

    @UnsupportedAppUsage
    private void dispatchInputEventFinished(int n, boolean bl) {
        this.onInputEventFinished(n, bl);
    }

    private void dispose(boolean bl) {
        long l;
        CloseGuard closeGuard = this.mCloseGuard;
        if (closeGuard != null) {
            if (bl) {
                closeGuard.warnIfOpen();
            }
            this.mCloseGuard.close();
        }
        if ((l = this.mSenderPtr) != 0L) {
            InputEventSender.nativeDispose(l);
            this.mSenderPtr = 0L;
        }
        this.mInputChannel = null;
        this.mMessageQueue = null;
    }

    private static native void nativeDispose(long var0);

    private static native long nativeInit(WeakReference<InputEventSender> var0, InputChannel var1, MessageQueue var2);

    private static native boolean nativeSendKeyEvent(long var0, int var2, KeyEvent var3);

    private static native boolean nativeSendMotionEvent(long var0, int var2, MotionEvent var3);

    public void dispose() {
        this.dispose(false);
    }

    protected void finalize() throws Throwable {
        try {
            this.dispose(true);
            return;
        }
        finally {
            super.finalize();
        }
    }

    public void onInputEventFinished(int n, boolean bl) {
    }

    public final boolean sendInputEvent(int n, InputEvent inputEvent) {
        if (inputEvent != null) {
            long l = this.mSenderPtr;
            if (l == 0L) {
                Log.w("InputEventSender", "Attempted to send an input event but the input event sender has already been disposed.");
                return false;
            }
            if (inputEvent instanceof KeyEvent) {
                return InputEventSender.nativeSendKeyEvent(l, n, (KeyEvent)inputEvent);
            }
            return InputEventSender.nativeSendMotionEvent(l, n, (MotionEvent)inputEvent);
        }
        throw new IllegalArgumentException("event must not be null");
    }
}

