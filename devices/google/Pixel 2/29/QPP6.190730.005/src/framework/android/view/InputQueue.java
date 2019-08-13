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
import android.util.LongSparseArray;
import android.util.Pools;
import android.view.InputEvent;
import android.view.KeyEvent;
import android.view.MotionEvent;
import dalvik.system.CloseGuard;
import java.lang.ref.WeakReference;

public final class InputQueue {
    private final LongSparseArray<ActiveInputEvent> mActiveEventArray = new LongSparseArray(20);
    private final Pools.Pool<ActiveInputEvent> mActiveInputEventPool = new Pools.SimplePool<ActiveInputEvent>(20);
    private final CloseGuard mCloseGuard = CloseGuard.get();
    private long mPtr = InputQueue.nativeInit(new WeakReference<InputQueue>(this), Looper.myQueue());

    public InputQueue() {
        this.mCloseGuard.open("dispose");
    }

    @UnsupportedAppUsage
    private void finishInputEvent(long l, boolean bl) {
        int n = this.mActiveEventArray.indexOfKey(l);
        if (n >= 0) {
            ActiveInputEvent activeInputEvent = this.mActiveEventArray.valueAt(n);
            this.mActiveEventArray.removeAt(n);
            activeInputEvent.mCallback.onFinishedInputEvent(activeInputEvent.mToken, bl);
            this.recycleActiveInputEvent(activeInputEvent);
        }
    }

    private static native void nativeDispose(long var0);

    private static native long nativeInit(WeakReference<InputQueue> var0, MessageQueue var1);

    private static native long nativeSendKeyEvent(long var0, KeyEvent var2, boolean var3);

    private static native long nativeSendMotionEvent(long var0, MotionEvent var2);

    private ActiveInputEvent obtainActiveInputEvent(Object object, FinishedInputEventCallback finishedInputEventCallback) {
        ActiveInputEvent activeInputEvent;
        ActiveInputEvent activeInputEvent2 = activeInputEvent = this.mActiveInputEventPool.acquire();
        if (activeInputEvent == null) {
            activeInputEvent2 = new ActiveInputEvent();
        }
        activeInputEvent2.mToken = object;
        activeInputEvent2.mCallback = finishedInputEventCallback;
        return activeInputEvent2;
    }

    private void recycleActiveInputEvent(ActiveInputEvent activeInputEvent) {
        activeInputEvent.recycle();
        this.mActiveInputEventPool.release(activeInputEvent);
    }

    public void dispose() {
        this.dispose(false);
    }

    public void dispose(boolean bl) {
        long l;
        CloseGuard closeGuard = this.mCloseGuard;
        if (closeGuard != null) {
            if (bl) {
                closeGuard.warnIfOpen();
            }
            this.mCloseGuard.close();
        }
        if ((l = this.mPtr) != 0L) {
            InputQueue.nativeDispose(l);
            this.mPtr = 0L;
        }
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

    public long getNativePtr() {
        return this.mPtr;
    }

    public void sendInputEvent(InputEvent inputEvent, Object object, boolean bl, FinishedInputEventCallback finishedInputEventCallback) {
        object = this.obtainActiveInputEvent(object, finishedInputEventCallback);
        long l = inputEvent instanceof KeyEvent ? InputQueue.nativeSendKeyEvent(this.mPtr, (KeyEvent)inputEvent, bl) : InputQueue.nativeSendMotionEvent(this.mPtr, (MotionEvent)inputEvent);
        this.mActiveEventArray.put(l, (ActiveInputEvent)object);
    }

    private final class ActiveInputEvent {
        public FinishedInputEventCallback mCallback;
        public Object mToken;

        private ActiveInputEvent() {
        }

        public void recycle() {
            this.mToken = null;
            this.mCallback = null;
        }
    }

    public static interface Callback {
        public void onInputQueueCreated(InputQueue var1);

        public void onInputQueueDestroyed(InputQueue var1);
    }

    public static interface FinishedInputEventCallback {
        public void onFinishedInputEvent(Object var1, boolean var2);
    }

}

