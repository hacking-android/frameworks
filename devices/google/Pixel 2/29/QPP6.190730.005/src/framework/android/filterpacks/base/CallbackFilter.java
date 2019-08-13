/*
 * Decompiled with CFR 0.145.
 */
package android.filterpacks.base;

import android.filterfw.core.Filter;
import android.filterfw.core.FilterContext;
import android.filterfw.core.Frame;
import android.filterfw.core.GenerateFieldPort;
import android.filterfw.core.GenerateFinalPort;
import android.os.Handler;
import android.os.Looper;

public class CallbackFilter
extends Filter {
    @GenerateFinalPort(hasDefault=true, name="callUiThread")
    private boolean mCallbacksOnUiThread = true;
    @GenerateFieldPort(hasDefault=true, name="listener")
    private FilterContext.OnFrameReceivedListener mListener;
    private Handler mUiThreadHandler;
    @GenerateFieldPort(hasDefault=true, name="userData")
    private Object mUserData;

    public CallbackFilter(String string2) {
        super(string2);
    }

    @Override
    public void prepare(FilterContext filterContext) {
        if (this.mCallbacksOnUiThread) {
            this.mUiThreadHandler = new Handler(Looper.getMainLooper());
        }
    }

    @Override
    public void process(FilterContext object) {
        Frame frame = this.pullInput("frame");
        object = this.mListener;
        if (object != null) {
            if (this.mCallbacksOnUiThread) {
                frame.retain();
                object = new CallbackRunnable(this.mListener, this, frame, this.mUserData);
                if (!this.mUiThreadHandler.post((Runnable)object)) {
                    throw new RuntimeException("Unable to send callback to UI thread!");
                }
            } else {
                object.onFrameReceived(this, frame, this.mUserData);
            }
            return;
        }
        throw new RuntimeException("CallbackFilter received frame, but no listener set!");
    }

    @Override
    public void setupPorts() {
        this.addInputPort("frame");
    }

    private class CallbackRunnable
    implements Runnable {
        private Filter mFilter;
        private Frame mFrame;
        private FilterContext.OnFrameReceivedListener mListener;
        private Object mUserData;

        public CallbackRunnable(FilterContext.OnFrameReceivedListener onFrameReceivedListener, Filter filter, Frame frame, Object object) {
            this.mListener = onFrameReceivedListener;
            this.mFilter = filter;
            this.mFrame = frame;
            this.mUserData = object;
        }

        @Override
        public void run() {
            this.mListener.onFrameReceived(this.mFilter, this.mFrame, this.mUserData);
            this.mFrame.release();
        }
    }

}

