/*
 * Decompiled with CFR 0.145.
 */
package android.view;

import android.annotation.UnsupportedAppUsage;
import android.graphics.FrameInfo;
import android.hardware.display.DisplayManagerGlobal;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.SystemClock;
import android.os.SystemProperties;
import android.os.Trace;
import android.util.Log;
import android.util.TimeUtils;
import android.view.Display;
import android.view.DisplayEventReceiver;
import android.view.DisplayInfo;
import android.view.ThreadedRenderer;
import android.view.animation.AnimationUtils;
import java.io.PrintWriter;

public final class Choreographer {
    public static final int CALLBACK_ANIMATION = 1;
    public static final int CALLBACK_COMMIT = 4;
    public static final int CALLBACK_INPUT = 0;
    public static final int CALLBACK_INSETS_ANIMATION = 2;
    private static final int CALLBACK_LAST = 4;
    private static final String[] CALLBACK_TRACE_TITLES;
    public static final int CALLBACK_TRAVERSAL = 3;
    private static final boolean DEBUG_FRAMES = false;
    private static final boolean DEBUG_JANK = false;
    private static final long DEFAULT_FRAME_DELAY = 10L;
    private static final Object FRAME_CALLBACK_TOKEN;
    private static final int MSG_DO_FRAME = 0;
    private static final int MSG_DO_SCHEDULE_CALLBACK = 2;
    private static final int MSG_DO_SCHEDULE_VSYNC = 1;
    private static final int SKIPPED_FRAME_WARNING_LIMIT;
    private static final String TAG = "Choreographer";
    private static final boolean USE_FRAME_TIME;
    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=123769497L)
    private static final boolean USE_VSYNC;
    private static volatile Choreographer mMainInstance;
    private static volatile long sFrameDelay;
    private static final ThreadLocal<Choreographer> sSfThreadInstance;
    private static final ThreadLocal<Choreographer> sThreadInstance;
    private CallbackRecord mCallbackPool;
    @UnsupportedAppUsage
    private final CallbackQueue[] mCallbackQueues;
    private boolean mCallbacksRunning;
    private boolean mDebugPrintNextFrameTimeDelta;
    @UnsupportedAppUsage
    private final FrameDisplayEventReceiver mDisplayEventReceiver;
    private int mFPSDivisor = 1;
    FrameInfo mFrameInfo = new FrameInfo();
    @UnsupportedAppUsage
    private long mFrameIntervalNanos;
    private boolean mFrameScheduled;
    private final FrameHandler mHandler;
    @UnsupportedAppUsage
    private long mLastFrameTimeNanos;
    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    private final Object mLock = new Object();
    private final Looper mLooper;

    static {
        sFrameDelay = 10L;
        sThreadInstance = new ThreadLocal<Choreographer>(){

            @Override
            protected Choreographer initialValue() {
                Looper looper = Looper.myLooper();
                if (looper != null) {
                    Choreographer choreographer = new Choreographer(looper, 0);
                    if (looper == Looper.getMainLooper()) {
                        mMainInstance = choreographer;
                    }
                    return choreographer;
                }
                throw new IllegalStateException("The current thread must have a looper!");
            }
        };
        sSfThreadInstance = new ThreadLocal<Choreographer>(){

            @Override
            protected Choreographer initialValue() {
                Looper looper = Looper.myLooper();
                if (looper != null) {
                    return new Choreographer(looper, 1);
                }
                throw new IllegalStateException("The current thread must have a looper!");
            }
        };
        USE_VSYNC = SystemProperties.getBoolean("debug.choreographer.vsync", true);
        USE_FRAME_TIME = SystemProperties.getBoolean("debug.choreographer.frametime", true);
        SKIPPED_FRAME_WARNING_LIMIT = SystemProperties.getInt("debug.choreographer.skipwarning", 30);
        FRAME_CALLBACK_TOKEN = new Object(){

            public String toString() {
                return "FRAME_CALLBACK_TOKEN";
            }
        };
        CALLBACK_TRACE_TITLES = new String[]{"input", "animation", "insets_animation", "traversal", "commit"};
    }

    private Choreographer(Looper object, int n) {
        this.mLooper = object;
        this.mHandler = new FrameHandler((Looper)object);
        object = USE_VSYNC ? new FrameDisplayEventReceiver((Looper)object, n) : null;
        this.mDisplayEventReceiver = object;
        this.mLastFrameTimeNanos = Long.MIN_VALUE;
        this.mFrameIntervalNanos = (long)(1.0E9f / Choreographer.getRefreshRate());
        this.mCallbackQueues = new CallbackQueue[5];
        for (n = 0; n <= 4; ++n) {
            this.mCallbackQueues[n] = new CallbackQueue();
        }
        this.setFPSDivisor(SystemProperties.getInt("debug.hwui.fps_divisor", 1));
    }

    private void dispose() {
        this.mDisplayEventReceiver.dispose();
    }

    public static long getFrameDelay() {
        return sFrameDelay;
    }

    public static Choreographer getInstance() {
        return sThreadInstance.get();
    }

    public static Choreographer getMainThreadInstance() {
        return mMainInstance;
    }

    private static float getRefreshRate() {
        return DisplayManagerGlobal.getInstance().getDisplayInfo(0).getMode().getRefreshRate();
    }

    @UnsupportedAppUsage
    public static Choreographer getSfInstance() {
        return sSfThreadInstance.get();
    }

    private boolean isRunningOnLooperThreadLocked() {
        boolean bl = Looper.myLooper() == this.mLooper;
        return bl;
    }

    private CallbackRecord obtainCallbackLocked(long l, Object object, Object object2) {
        CallbackRecord callbackRecord = this.mCallbackPool;
        if (callbackRecord == null) {
            callbackRecord = new CallbackRecord();
        } else {
            this.mCallbackPool = callbackRecord.next;
            callbackRecord.next = null;
        }
        callbackRecord.dueTime = l;
        callbackRecord.action = object;
        callbackRecord.token = object2;
        return callbackRecord;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void postCallbackDelayedInternal(int n, Object object, Object object2, long l) {
        Object object3 = this.mLock;
        synchronized (object3) {
            long l2 = SystemClock.uptimeMillis();
            l = l2 + l;
            this.mCallbackQueues[n].addCallbackLocked(l, object, object2);
            if (l <= l2) {
                this.scheduleFrameLocked(l2);
            } else {
                object = this.mHandler.obtainMessage(2, object);
                ((Message)object).arg1 = n;
                ((Message)object).setAsynchronous(true);
                this.mHandler.sendMessageAtTime((Message)object, l);
            }
            return;
        }
    }

    private void recycleCallbackLocked(CallbackRecord callbackRecord) {
        callbackRecord.action = null;
        callbackRecord.token = null;
        callbackRecord.next = this.mCallbackPool;
        this.mCallbackPool = callbackRecord;
    }

    public static void releaseInstance() {
        Choreographer choreographer = sThreadInstance.get();
        sThreadInstance.remove();
        choreographer.dispose();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void removeCallbacksInternal(int n, Object object, Object object2) {
        Object object3 = this.mLock;
        synchronized (object3) {
            this.mCallbackQueues[n].removeCallbacksLocked(object, object2);
            if (object != null && object2 == null) {
                this.mHandler.removeMessages(2, object);
            }
            return;
        }
    }

    private void scheduleFrameLocked(long l) {
        if (!this.mFrameScheduled) {
            this.mFrameScheduled = true;
            if (USE_VSYNC) {
                if (this.isRunningOnLooperThreadLocked()) {
                    this.scheduleVsyncLocked();
                } else {
                    Message message = this.mHandler.obtainMessage(1);
                    message.setAsynchronous(true);
                    this.mHandler.sendMessageAtFrontOfQueue(message);
                }
            } else {
                l = Math.max(this.mLastFrameTimeNanos / 1000000L + sFrameDelay, l);
                Message message = this.mHandler.obtainMessage(0);
                message.setAsynchronous(true);
                this.mHandler.sendMessageAtTime(message, l);
            }
        }
    }

    @UnsupportedAppUsage
    private void scheduleVsyncLocked() {
        this.mDisplayEventReceiver.scheduleVsync();
    }

    public static void setFrameDelay(long l) {
        sFrameDelay = l;
    }

    public static long subtractFrameDelay(long l) {
        long l2 = sFrameDelay;
        l = l <= l2 ? 0L : (l -= l2);
        return l;
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    void doCallbacks(int n, long l) {
        Object object = this.mLock;
        // MONITORENTER : object
        long l2 = System.nanoTime();
        Object object2 = this.mCallbackQueues[n].extractDueCallbacksLocked(l2 / 1000000L);
        if (object2 == null) {
            // MONITOREXIT : object
            return;
        }
        this.mCallbacksRunning = true;
        if (n == 4) {
            long l3 = l2 - l;
            Trace.traceCounter(8L, "jitterNanos", (int)l3);
            if (l3 >= this.mFrameIntervalNanos * 2L) {
                long l4 = this.mFrameIntervalNanos;
                l = this.mFrameIntervalNanos;
                l = l2 - (l3 % l4 + l);
                this.mLastFrameTimeNanos = l;
                // MONITOREXIT : object
            }
        }
        try {
            Trace.traceBegin(8L, CALLBACK_TRACE_TITLES[n]);
            object = object2;
            while (object != null) {
                ((CallbackRecord)object).run(l);
                object = ((CallbackRecord)object).next;
            }
            Object object3 = this.mLock;
        }
        catch (Throwable throwable) {
            Object object4 = this.mLock;
            // MONITORENTER : object4
            this.mCallbacksRunning = false;
            do {
                object = ((CallbackRecord)object2).next;
                this.recycleCallbackLocked((CallbackRecord)object2);
            } while ((object2 = object) != null);
            // MONITOREXIT : object4
            Trace.traceEnd(8L);
            throw throwable;
        }
        // MONITORENTER : object3
        this.mCallbacksRunning = false;
        do {
            object = ((CallbackRecord)object2).next;
            this.recycleCallbackLocked((CallbackRecord)object2);
            object2 = object;
        } while (object != null);
        // MONITOREXIT : object3
        Trace.traceEnd(8L);
        return;
        catch (Throwable throwable) {
            throw throwable;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    @UnsupportedAppUsage
    void doFrame(long l, int n) {
        Object object = this.mLock;
        // MONITORENTER : object
        if (!this.mFrameScheduled) {
            // MONITOREXIT : object
            return;
        }
        long l3 = System.nanoTime();
        long l2 = l3 - l;
        if (l2 >= this.mFrameIntervalNanos) {
            long l4 = l2 / this.mFrameIntervalNanos;
            if (l4 >= (long)SKIPPED_FRAME_WARNING_LIMIT) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Skipped ");
                stringBuilder.append(l4);
                stringBuilder.append(" frames!  The application may be doing too much work on its main thread.");
                Log.i(TAG, stringBuilder.toString());
            }
            l4 = this.mFrameIntervalNanos;
            l2 = l3 - l2 % l4;
        } else {
            l2 = l;
        }
        if (l2 < this.mLastFrameTimeNanos) {
            this.scheduleVsyncLocked();
            // MONITOREXIT : object
            return;
        }
        if (this.mFPSDivisor > 1 && (l3 = l2 - this.mLastFrameTimeNanos) < this.mFrameIntervalNanos * (long)this.mFPSDivisor && l3 > 0L) {
            this.scheduleVsyncLocked();
            // MONITOREXIT : object
            return;
        }
        this.mFrameInfo.setVsync(l, l2);
        this.mFrameScheduled = false;
        this.mLastFrameTimeNanos = l2;
        // MONITOREXIT : object
        try {
            Trace.traceBegin(8L, "Choreographer#doFrame");
            AnimationUtils.lockAnimationClock(l2 / 1000000L);
            this.mFrameInfo.markInputHandlingStart();
            this.doCallbacks(0, l2);
            this.mFrameInfo.markAnimationsStart();
            this.doCallbacks(1, l2);
            this.doCallbacks(2, l2);
            this.mFrameInfo.markPerformTraversalsStart();
            this.doCallbacks(3, l2);
            this.doCallbacks(4, l2);
            return;
        }
        finally {
            AnimationUtils.unlockAnimationClock();
            Trace.traceEnd(8L);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    void doScheduleCallback(int n) {
        Object object = this.mLock;
        synchronized (object) {
            long l;
            if (!this.mFrameScheduled && this.mCallbackQueues[n].hasDueCallbacksLocked(l = SystemClock.uptimeMillis())) {
                this.scheduleFrameLocked(l);
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    void doScheduleVsync() {
        Object object = this.mLock;
        synchronized (object) {
            if (this.mFrameScheduled) {
                this.scheduleVsyncLocked();
            }
            return;
        }
    }

    void dump(String string2, PrintWriter printWriter) {
        CharSequence charSequence = new StringBuilder();
        charSequence.append(string2);
        charSequence.append("  ");
        charSequence = charSequence.toString();
        printWriter.print(string2);
        printWriter.println("Choreographer:");
        printWriter.print((String)charSequence);
        printWriter.print("mFrameScheduled=");
        printWriter.println(this.mFrameScheduled);
        printWriter.print((String)charSequence);
        printWriter.print("mLastFrameTime=");
        printWriter.println(TimeUtils.formatUptime(this.mLastFrameTimeNanos / 1000000L));
    }

    public long getFrameIntervalNanos() {
        return this.mFrameIntervalNanos;
    }

    @UnsupportedAppUsage
    public long getFrameTime() {
        return this.getFrameTimeNanos() / 1000000L;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @UnsupportedAppUsage
    public long getFrameTimeNanos() {
        Object object = this.mLock;
        synchronized (object) {
            if (!this.mCallbacksRunning) {
                IllegalStateException illegalStateException = new IllegalStateException("This method must only be called as part of a callback while a frame is in progress.");
                throw illegalStateException;
            }
            if (!USE_FRAME_TIME) return System.nanoTime();
            return this.mLastFrameTimeNanos;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public long getLastFrameTimeNanos() {
        Object object = this.mLock;
        synchronized (object) {
            if (!USE_FRAME_TIME) return System.nanoTime();
            return this.mLastFrameTimeNanos;
        }
    }

    public void postCallback(int n, Runnable runnable, Object object) {
        this.postCallbackDelayed(n, runnable, object, 0L);
    }

    public void postCallbackDelayed(int n, Runnable runnable, Object object, long l) {
        if (runnable != null) {
            if (n >= 0 && n <= 4) {
                this.postCallbackDelayedInternal(n, runnable, object, l);
                return;
            }
            throw new IllegalArgumentException("callbackType is invalid");
        }
        throw new IllegalArgumentException("action must not be null");
    }

    public void postFrameCallback(FrameCallback frameCallback) {
        this.postFrameCallbackDelayed(frameCallback, 0L);
    }

    public void postFrameCallbackDelayed(FrameCallback frameCallback, long l) {
        if (frameCallback != null) {
            this.postCallbackDelayedInternal(1, frameCallback, FRAME_CALLBACK_TOKEN, l);
            return;
        }
        throw new IllegalArgumentException("callback must not be null");
    }

    public void removeCallbacks(int n, Runnable runnable, Object object) {
        if (n >= 0 && n <= 4) {
            this.removeCallbacksInternal(n, runnable, object);
            return;
        }
        throw new IllegalArgumentException("callbackType is invalid");
    }

    public void removeFrameCallback(FrameCallback frameCallback) {
        if (frameCallback != null) {
            this.removeCallbacksInternal(1, frameCallback, FRAME_CALLBACK_TOKEN);
            return;
        }
        throw new IllegalArgumentException("callback must not be null");
    }

    void setFPSDivisor(int n) {
        int n2 = n;
        if (n <= 0) {
            n2 = 1;
        }
        this.mFPSDivisor = n2;
        ThreadedRenderer.setFPSDivisor(n2);
    }

    private final class CallbackQueue {
        private CallbackRecord mHead;

        private CallbackQueue() {
        }

        @UnsupportedAppUsage
        public void addCallbackLocked(long l, Object object, Object object2) {
            CallbackRecord callbackRecord = Choreographer.this.obtainCallbackLocked(l, object, object2);
            object2 = this.mHead;
            if (object2 == null) {
                this.mHead = callbackRecord;
                return;
            }
            object = object2;
            if (l < ((CallbackRecord)object2).dueTime) {
                callbackRecord.next = object2;
                this.mHead = callbackRecord;
                return;
            }
            while (((CallbackRecord)object).next != null) {
                if (l < object.next.dueTime) {
                    callbackRecord.next = ((CallbackRecord)object).next;
                    break;
                }
                object = ((CallbackRecord)object).next;
            }
            ((CallbackRecord)object).next = callbackRecord;
        }

        public CallbackRecord extractDueCallbacksLocked(long l) {
            CallbackRecord callbackRecord = this.mHead;
            if (callbackRecord != null && callbackRecord.dueTime <= l) {
                CallbackRecord callbackRecord2 = callbackRecord;
                CallbackRecord callbackRecord3 = callbackRecord2.next;
                while (callbackRecord3 != null) {
                    if (callbackRecord3.dueTime > l) {
                        callbackRecord2.next = null;
                        break;
                    }
                    callbackRecord2 = callbackRecord3;
                    callbackRecord3 = callbackRecord3.next;
                }
                this.mHead = callbackRecord3;
                return callbackRecord;
            }
            return null;
        }

        public boolean hasDueCallbacksLocked(long l) {
            CallbackRecord callbackRecord = this.mHead;
            boolean bl = callbackRecord != null && callbackRecord.dueTime <= l;
            return bl;
        }

        public void removeCallbacksLocked(Object object, Object object2) {
            CallbackRecord callbackRecord = null;
            CallbackRecord callbackRecord2 = this.mHead;
            while (callbackRecord2 != null) {
                CallbackRecord callbackRecord3 = callbackRecord2.next;
                if (object != null && callbackRecord2.action != object || object2 != null && callbackRecord2.token != object2) {
                    callbackRecord = callbackRecord2;
                } else {
                    if (callbackRecord != null) {
                        callbackRecord.next = callbackRecord3;
                    } else {
                        this.mHead = callbackRecord3;
                    }
                    Choreographer.this.recycleCallbackLocked(callbackRecord2);
                }
                callbackRecord2 = callbackRecord3;
            }
        }
    }

    private static final class CallbackRecord {
        public Object action;
        public long dueTime;
        public CallbackRecord next;
        public Object token;

        private CallbackRecord() {
        }

        @UnsupportedAppUsage
        public void run(long l) {
            if (this.token == FRAME_CALLBACK_TOKEN) {
                ((FrameCallback)this.action).doFrame(l);
            } else {
                ((Runnable)this.action).run();
            }
        }
    }

    public static interface FrameCallback {
        public void doFrame(long var1);
    }

    private final class FrameDisplayEventReceiver
    extends DisplayEventReceiver
    implements Runnable {
        private int mFrame;
        private boolean mHavePendingVsync;
        private long mTimestampNanos;

        public FrameDisplayEventReceiver(Looper looper, int n) {
            super(looper, n);
        }

        @Override
        public void onVsync(long l, long l2, int n) {
            Object object;
            long l3 = System.nanoTime();
            l2 = l;
            if (l > l3) {
                object = new StringBuilder();
                ((StringBuilder)object).append("Frame time is ");
                ((StringBuilder)object).append((float)(l - l3) * 1.0E-6f);
                ((StringBuilder)object).append(" ms in the future!  Check that graphics HAL is generating vsync timestamps using the correct timebase.");
                Log.w(Choreographer.TAG, ((StringBuilder)object).toString());
                l2 = l3;
            }
            if (this.mHavePendingVsync) {
                Log.w(Choreographer.TAG, "Already have a pending vsync event.  There should only be one at a time.");
            } else {
                this.mHavePendingVsync = true;
            }
            this.mTimestampNanos = l2;
            this.mFrame = n;
            object = Message.obtain((Handler)Choreographer.this.mHandler, this);
            ((Message)object).setAsynchronous(true);
            Choreographer.this.mHandler.sendMessageAtTime((Message)object, l2 / 1000000L);
        }

        @Override
        public void run() {
            this.mHavePendingVsync = false;
            Choreographer.this.doFrame(this.mTimestampNanos, this.mFrame);
        }
    }

    private final class FrameHandler
    extends Handler {
        public FrameHandler(Looper looper) {
            super(looper);
        }

        @Override
        public void handleMessage(Message message) {
            int n = message.what;
            if (n != 0) {
                if (n != 1) {
                    if (n == 2) {
                        Choreographer.this.doScheduleCallback(message.arg1);
                    }
                } else {
                    Choreographer.this.doScheduleVsync();
                }
            } else {
                Choreographer.this.doFrame(System.nanoTime(), 0);
            }
        }
    }

}

