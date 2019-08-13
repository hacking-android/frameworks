/*
 * Decompiled with CFR 0.145.
 */
package android.view;

import android.annotation.UnsupportedAppUsage;
import android.os.Looper;
import android.os.MessageQueue;
import android.view.FrameMetrics;
import android.view.Window;
import com.android.internal.util.VirtualRefBasePtr;
import java.lang.ref.WeakReference;

public class FrameMetricsObserver {
    @UnsupportedAppUsage
    private FrameMetrics mFrameMetrics;
    Window.OnFrameMetricsAvailableListener mListener;
    @UnsupportedAppUsage
    private MessageQueue mMessageQueue;
    public VirtualRefBasePtr mNative;
    private WeakReference<Window> mWindow;

    FrameMetricsObserver(Window window, Looper looper, Window.OnFrameMetricsAvailableListener onFrameMetricsAvailableListener) {
        if (looper != null) {
            this.mMessageQueue = looper.getQueue();
            if (this.mMessageQueue != null) {
                this.mFrameMetrics = new FrameMetrics();
                this.mWindow = new WeakReference<Window>(window);
                this.mListener = onFrameMetricsAvailableListener;
                return;
            }
            throw new IllegalStateException("invalid looper, null message queue\n");
        }
        throw new NullPointerException("looper cannot be null");
    }

    @UnsupportedAppUsage
    private void notifyDataAvailable(int n) {
        Window window = (Window)this.mWindow.get();
        if (window != null) {
            this.mListener.onFrameMetricsAvailable(window, this.mFrameMetrics, n);
        }
    }
}

