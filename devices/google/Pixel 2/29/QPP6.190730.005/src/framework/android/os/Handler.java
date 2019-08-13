/*
 * Decompiled with CFR 0.145.
 */
package android.os;

import android.annotation.UnsupportedAppUsage;
import android.os.Binder;
import android.os.IMessenger;
import android.os.Looper;
import android.os.Message;
import android.os.MessageQueue;
import android.os.SystemClock;
import android.os.ThreadLocalWorkSource;
import android.util.Log;
import android.util.Printer;

public class Handler {
    private static final boolean FIND_POTENTIAL_LEAKS = false;
    private static Handler MAIN_THREAD_HANDLER = null;
    private static final String TAG = "Handler";
    final boolean mAsynchronous;
    @UnsupportedAppUsage
    final Callback mCallback;
    @UnsupportedAppUsage
    final Looper mLooper;
    @UnsupportedAppUsage
    IMessenger mMessenger;
    final MessageQueue mQueue;

    public Handler() {
        this(null, false);
    }

    public Handler(Callback callback) {
        this(callback, false);
    }

    public Handler(Callback object, boolean bl) {
        Looper looper = this.mLooper = Looper.myLooper();
        if (looper != null) {
            this.mQueue = looper.mQueue;
            this.mCallback = object;
            this.mAsynchronous = bl;
            return;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Can't create handler inside thread ");
        ((StringBuilder)object).append(Thread.currentThread());
        ((StringBuilder)object).append(" that has not called Looper.prepare()");
        throw new RuntimeException(((StringBuilder)object).toString());
    }

    public Handler(Looper looper) {
        this(looper, null, false);
    }

    public Handler(Looper looper, Callback callback) {
        this(looper, callback, false);
    }

    @UnsupportedAppUsage
    public Handler(Looper looper, Callback callback, boolean bl) {
        this.mLooper = looper;
        this.mQueue = looper.mQueue;
        this.mCallback = callback;
        this.mAsynchronous = bl;
    }

    @UnsupportedAppUsage
    public Handler(boolean bl) {
        this(null, bl);
    }

    public static Handler createAsync(Looper looper) {
        if (looper != null) {
            return new Handler(looper, null, true);
        }
        throw new NullPointerException("looper must not be null");
    }

    public static Handler createAsync(Looper looper, Callback callback) {
        if (looper != null) {
            if (callback != null) {
                return new Handler(looper, callback, true);
            }
            throw new NullPointerException("callback must not be null");
        }
        throw new NullPointerException("looper must not be null");
    }

    private boolean enqueueMessage(MessageQueue messageQueue, Message message, long l) {
        message.target = this;
        message.workSourceUid = ThreadLocalWorkSource.getUid();
        if (this.mAsynchronous) {
            message.setAsynchronous(true);
        }
        return messageQueue.enqueueMessage(message, l);
    }

    @UnsupportedAppUsage
    public static Handler getMain() {
        if (MAIN_THREAD_HANDLER == null) {
            MAIN_THREAD_HANDLER = new Handler(Looper.getMainLooper());
        }
        return MAIN_THREAD_HANDLER;
    }

    private static Message getPostMessage(Runnable runnable) {
        Message message = Message.obtain();
        message.callback = runnable;
        return message;
    }

    @UnsupportedAppUsage
    private static Message getPostMessage(Runnable runnable, Object object) {
        Message message = Message.obtain();
        message.obj = object;
        message.callback = runnable;
        return message;
    }

    private static void handleCallback(Message message) {
        message.callback.run();
    }

    public static Handler mainIfNull(Handler handler) {
        block0 : {
            if (handler != null) break block0;
            handler = Handler.getMain();
        }
        return handler;
    }

    public void dispatchMessage(Message message) {
        if (message.callback != null) {
            Handler.handleCallback(message);
        } else {
            Callback callback = this.mCallback;
            if (callback != null && callback.handleMessage(message)) {
                return;
            }
            this.handleMessage(message);
        }
    }

    public final void dump(Printer printer, String string2) {
        Object object = new StringBuilder();
        ((StringBuilder)object).append(string2);
        ((StringBuilder)object).append(this);
        ((StringBuilder)object).append(" @ ");
        ((StringBuilder)object).append(SystemClock.uptimeMillis());
        printer.println(((StringBuilder)object).toString());
        object = this.mLooper;
        if (object == null) {
            object = new StringBuilder();
            ((StringBuilder)object).append(string2);
            ((StringBuilder)object).append("looper uninitialized");
            printer.println(((StringBuilder)object).toString());
        } else {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(string2);
            stringBuilder.append("  ");
            ((Looper)object).dump(printer, stringBuilder.toString());
        }
    }

    public final void dumpMine(Printer printer, String string2) {
        Object object = new StringBuilder();
        ((StringBuilder)object).append(string2);
        ((StringBuilder)object).append(this);
        ((StringBuilder)object).append(" @ ");
        ((StringBuilder)object).append(SystemClock.uptimeMillis());
        printer.println(((StringBuilder)object).toString());
        object = this.mLooper;
        if (object == null) {
            object = new StringBuilder();
            ((StringBuilder)object).append(string2);
            ((StringBuilder)object).append("looper uninitialized");
            printer.println(((StringBuilder)object).toString());
        } else {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(string2);
            stringBuilder.append("  ");
            ((Looper)object).dump(printer, stringBuilder.toString(), this);
        }
    }

    public final boolean executeOrSendMessage(Message message) {
        if (this.mLooper == Looper.myLooper()) {
            this.dispatchMessage(message);
            return true;
        }
        return this.sendMessage(message);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @UnsupportedAppUsage
    final IMessenger getIMessenger() {
        MessageQueue messageQueue = this.mQueue;
        synchronized (messageQueue) {
            if (this.mMessenger != null) {
                return this.mMessenger;
            }
            IMessenger iMessenger = new MessengerImpl();
            this.mMessenger = iMessenger;
            return this.mMessenger;
        }
    }

    public final Looper getLooper() {
        return this.mLooper;
    }

    public String getMessageName(Message message) {
        if (message.callback != null) {
            return message.callback.getClass().getName();
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("0x");
        stringBuilder.append(Integer.toHexString(message.what));
        return stringBuilder.toString();
    }

    public String getTraceName(Message message) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.getClass().getName());
        stringBuilder.append(": ");
        if (message.callback != null) {
            stringBuilder.append(message.callback.getClass().getName());
        } else {
            stringBuilder.append("#");
            stringBuilder.append(message.what);
        }
        return stringBuilder.toString();
    }

    public void handleMessage(Message message) {
    }

    public final boolean hasCallbacks(Runnable runnable) {
        return this.mQueue.hasMessages(this, runnable, null);
    }

    public final boolean hasMessages(int n) {
        return this.mQueue.hasMessages(this, n, null);
    }

    public final boolean hasMessages(int n, Object object) {
        return this.mQueue.hasMessages(this, n, object);
    }

    public final boolean hasMessagesOrCallbacks() {
        return this.mQueue.hasMessages(this);
    }

    public final Message obtainMessage() {
        return Message.obtain(this);
    }

    public final Message obtainMessage(int n) {
        return Message.obtain(this, n);
    }

    public final Message obtainMessage(int n, int n2, int n3) {
        return Message.obtain(this, n, n2, n3);
    }

    public final Message obtainMessage(int n, int n2, int n3, Object object) {
        return Message.obtain(this, n, n2, n3, object);
    }

    public final Message obtainMessage(int n, Object object) {
        return Message.obtain(this, n, object);
    }

    public final boolean post(Runnable runnable) {
        return this.sendMessageDelayed(Handler.getPostMessage(runnable), 0L);
    }

    public final boolean postAtFrontOfQueue(Runnable runnable) {
        return this.sendMessageAtFrontOfQueue(Handler.getPostMessage(runnable));
    }

    public final boolean postAtTime(Runnable runnable, long l) {
        return this.sendMessageAtTime(Handler.getPostMessage(runnable), l);
    }

    public final boolean postAtTime(Runnable runnable, Object object, long l) {
        return this.sendMessageAtTime(Handler.getPostMessage(runnable, object), l);
    }

    public final boolean postDelayed(Runnable runnable, int n, long l) {
        return this.sendMessageDelayed(Handler.getPostMessage(runnable).setWhat(n), l);
    }

    public final boolean postDelayed(Runnable runnable, long l) {
        return this.sendMessageDelayed(Handler.getPostMessage(runnable), l);
    }

    public final boolean postDelayed(Runnable runnable, Object object, long l) {
        return this.sendMessageDelayed(Handler.getPostMessage(runnable, object), l);
    }

    public final void removeCallbacks(Runnable runnable) {
        this.mQueue.removeMessages(this, runnable, null);
    }

    public final void removeCallbacks(Runnable runnable, Object object) {
        this.mQueue.removeMessages(this, runnable, object);
    }

    public final void removeCallbacksAndMessages(Object object) {
        this.mQueue.removeCallbacksAndMessages(this, object);
    }

    public final void removeMessages(int n) {
        this.mQueue.removeMessages(this, n, null);
    }

    public final void removeMessages(int n, Object object) {
        this.mQueue.removeMessages(this, n, object);
    }

    public final boolean runWithScissors(Runnable runnable, long l) {
        if (runnable != null) {
            if (l >= 0L) {
                if (Looper.myLooper() == this.mLooper) {
                    runnable.run();
                    return true;
                }
                return new BlockingRunnable(runnable).postAndWait(this, l);
            }
            throw new IllegalArgumentException("timeout must be non-negative");
        }
        throw new IllegalArgumentException("runnable must not be null");
    }

    public final boolean sendEmptyMessage(int n) {
        return this.sendEmptyMessageDelayed(n, 0L);
    }

    public final boolean sendEmptyMessageAtTime(int n, long l) {
        Message message = Message.obtain();
        message.what = n;
        return this.sendMessageAtTime(message, l);
    }

    public final boolean sendEmptyMessageDelayed(int n, long l) {
        Message message = Message.obtain();
        message.what = n;
        return this.sendMessageDelayed(message, l);
    }

    public final boolean sendMessage(Message message) {
        return this.sendMessageDelayed(message, 0L);
    }

    public final boolean sendMessageAtFrontOfQueue(Message object) {
        MessageQueue messageQueue = this.mQueue;
        if (messageQueue == null) {
            object = new StringBuilder();
            ((StringBuilder)object).append(this);
            ((StringBuilder)object).append(" sendMessageAtTime() called with no mQueue");
            object = new RuntimeException(((StringBuilder)object).toString());
            Log.w("Looper", ((Throwable)object).getMessage(), (Throwable)object);
            return false;
        }
        return this.enqueueMessage(messageQueue, (Message)object, 0L);
    }

    public boolean sendMessageAtTime(Message object, long l) {
        MessageQueue messageQueue = this.mQueue;
        if (messageQueue == null) {
            object = new StringBuilder();
            ((StringBuilder)object).append(this);
            ((StringBuilder)object).append(" sendMessageAtTime() called with no mQueue");
            object = new RuntimeException(((StringBuilder)object).toString());
            Log.w("Looper", ((Throwable)object).getMessage(), (Throwable)object);
            return false;
        }
        return this.enqueueMessage(messageQueue, (Message)object, l);
    }

    public final boolean sendMessageDelayed(Message message, long l) {
        long l2 = l;
        if (l < 0L) {
            l2 = 0L;
        }
        return this.sendMessageAtTime(message, SystemClock.uptimeMillis() + l2);
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Handler (");
        stringBuilder.append(this.getClass().getName());
        stringBuilder.append(") {");
        stringBuilder.append(Integer.toHexString(System.identityHashCode(this)));
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    private static final class BlockingRunnable
    implements Runnable {
        private boolean mDone;
        private final Runnable mTask;

        public BlockingRunnable(Runnable runnable) {
            this.mTask = runnable;
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public boolean postAndWait(Handler handler, long l) {
            if (!handler.post(this)) {
                return false;
            }
            synchronized (this) {
                if (l > 0L) {
                    long l2 = SystemClock.uptimeMillis();
                    while (!this.mDone) {
                        long l3 = l2 + l - SystemClock.uptimeMillis();
                        if (l3 <= 0L) {
                            return false;
                        }
                        try {
                            this.wait(l3);
                        }
                        catch (InterruptedException interruptedException) {}
                    }
                } else {
                    boolean bl;
                    while (!(bl = this.mDone)) {
                        try {
                            this.wait();
                        }
                        catch (InterruptedException interruptedException) {}
                    }
                }
                return true;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void run() {
            try {
                this.mTask.run();
                return;
            }
            finally {
                synchronized (this) {
                    this.mDone = true;
                    this.notifyAll();
                }
            }
        }
    }

    public static interface Callback {
        public boolean handleMessage(Message var1);
    }

    private final class MessengerImpl
    extends IMessenger.Stub {
        private MessengerImpl() {
        }

        @Override
        public void send(Message message) {
            message.sendingUid = Binder.getCallingUid();
            Handler.this.sendMessage(message);
        }
    }

}

