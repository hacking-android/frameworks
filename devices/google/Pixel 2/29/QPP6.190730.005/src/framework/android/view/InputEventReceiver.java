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
import android.util.SparseIntArray;
import android.view.InputChannel;
import android.view.InputEvent;
import dalvik.system.CloseGuard;
import java.lang.ref.WeakReference;

public abstract class InputEventReceiver {
    private static final String TAG = "InputEventReceiver";
    private final CloseGuard mCloseGuard = CloseGuard.get();
    private InputChannel mInputChannel;
    private MessageQueue mMessageQueue;
    private long mReceiverPtr;
    private final SparseIntArray mSeqMap = new SparseIntArray();

    public InputEventReceiver(InputChannel inputChannel, Looper looper) {
        if (inputChannel != null) {
            if (looper != null) {
                this.mInputChannel = inputChannel;
                this.mMessageQueue = looper.getQueue();
                this.mReceiverPtr = InputEventReceiver.nativeInit(new WeakReference<InputEventReceiver>(this), inputChannel, this.mMessageQueue);
                this.mCloseGuard.open("dispose");
                return;
            }
            throw new IllegalArgumentException("looper must not be null");
        }
        throw new IllegalArgumentException("inputChannel must not be null");
    }

    @UnsupportedAppUsage
    private void dispatchBatchedInputEventPending() {
        this.onBatchedInputEventPending();
    }

    @UnsupportedAppUsage
    private void dispatchInputEvent(int n, InputEvent inputEvent) {
        this.mSeqMap.put(inputEvent.getSequenceNumber(), n);
        this.onInputEvent(inputEvent);
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
        if ((l = this.mReceiverPtr) != 0L) {
            InputEventReceiver.nativeDispose(l);
            this.mReceiverPtr = 0L;
        }
        this.mInputChannel = null;
        this.mMessageQueue = null;
    }

    private static native boolean nativeConsumeBatchedInputEvents(long var0, long var2);

    private static native void nativeDispose(long var0);

    private static native void nativeFinishInputEvent(long var0, int var2, boolean var3);

    private static native long nativeInit(WeakReference<InputEventReceiver> var0, InputChannel var1, MessageQueue var2);

    public final boolean consumeBatchedInputEvents(long l) {
        long l2 = this.mReceiverPtr;
        if (l2 == 0L) {
            Log.w("InputEventReceiver", "Attempted to consume batched input events but the input event receiver has already been disposed.");
            return false;
        }
        return InputEventReceiver.nativeConsumeBatchedInputEvents(l2, l);
    }

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

    public final void finishInputEvent(InputEvent inputEvent, boolean bl) {
        if (inputEvent != null) {
            if (this.mReceiverPtr == 0L) {
                Log.w("InputEventReceiver", "Attempted to finish an input event but the input event receiver has already been disposed.");
            } else {
                int n = this.mSeqMap.indexOfKey(inputEvent.getSequenceNumber());
                if (n < 0) {
                    Log.w("InputEventReceiver", "Attempted to finish an input event that is not in progress.");
                } else {
                    int n2 = this.mSeqMap.valueAt(n);
                    this.mSeqMap.removeAt(n);
                    InputEventReceiver.nativeFinishInputEvent(this.mReceiverPtr, n2, bl);
                }
            }
            inputEvent.recycleIfNeededAfterDispatch();
            return;
        }
        throw new IllegalArgumentException("event must not be null");
    }

    public void onBatchedInputEventPending() {
        this.consumeBatchedInputEvents(-1L);
    }

    @UnsupportedAppUsage
    public void onInputEvent(InputEvent inputEvent) {
        this.finishInputEvent(inputEvent, false);
    }

    public static interface Factory {
        public InputEventReceiver createInputEventReceiver(InputChannel var1, Looper var2);
    }

}

