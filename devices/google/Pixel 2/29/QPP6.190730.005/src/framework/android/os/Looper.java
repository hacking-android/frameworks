/*
 * Decompiled with CFR 0.145.
 */
package android.os;

import android.annotation.UnsupportedAppUsage;
import android.os.Binder;
import android.os.Handler;
import android.os.Message;
import android.os.MessageQueue;
import android.os.Process;
import android.os.SystemClock;
import android.os.SystemProperties;
import android.os.ThreadLocalWorkSource;
import android.os.Trace;
import android.util.Log;
import android.util.Printer;
import android.util.Slog;
import android.util.proto.ProtoOutputStream;

public final class Looper {
    private static final String TAG = "Looper";
    @UnsupportedAppUsage
    private static Looper sMainLooper;
    private static Observer sObserver;
    @UnsupportedAppUsage
    static final ThreadLocal<Looper> sThreadLocal;
    @UnsupportedAppUsage
    private Printer mLogging;
    @UnsupportedAppUsage
    final MessageQueue mQueue;
    private long mSlowDeliveryThresholdMs;
    private long mSlowDispatchThresholdMs;
    final Thread mThread;
    private long mTraceTag;

    static {
        sThreadLocal = new ThreadLocal();
    }

    private Looper(boolean bl) {
        this.mQueue = new MessageQueue(bl);
        this.mThread = Thread.currentThread();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static Looper getMainLooper() {
        synchronized (Looper.class) {
            return sMainLooper;
        }
    }

    /*
     * Loose catch block
     * WARNING - void declaration
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public static void loop() {
        long l2;
        long l;
        void var4_10;
        block25 : {
            Object object3;
            Object object2;
            Object object;
            void var1_4;
            block26 : {
                Looper looper = Looper.myLooper();
                if (looper == null) throw new RuntimeException("No Looper; Looper.prepare() wasn't called on this thread.");
                MessageQueue messageQueue = looper.mQueue;
                Binder.clearCallingIdentity();
                long l3 = Binder.clearCallingIdentity();
                object2 = new StringBuilder();
                ((StringBuilder)object2).append("log.looper.");
                ((StringBuilder)object2).append(Process.myUid());
                ((StringBuilder)object2).append(".");
                ((StringBuilder)object2).append(Thread.currentThread().getName());
                ((StringBuilder)object2).append(".slow");
                int n = SystemProperties.getInt(((StringBuilder)object2).toString(), 0);
                boolean bl = false;
                do {
                    long l6;
                    Printer printer;
                    boolean bl2;
                    boolean bl3;
                    long l4;
                    long l5;
                    block24 : {
                        if ((object = messageQueue.next()) == null) {
                            return;
                        }
                        printer = looper.mLogging;
                        if (printer != null) {
                            object2 = new StringBuilder();
                            ((StringBuilder)object2).append(">>>>> Dispatching to ");
                            ((StringBuilder)object2).append(((Message)object).target);
                            ((StringBuilder)object2).append(" ");
                            ((StringBuilder)object2).append(((Message)object).callback);
                            ((StringBuilder)object2).append(": ");
                            ((StringBuilder)object2).append(((Message)object).what);
                            printer.println(((StringBuilder)object2).toString());
                        }
                        object3 = sObserver;
                        l2 = looper.mTraceTag;
                        l5 = looper.mSlowDispatchThresholdMs;
                        l4 = looper.mSlowDeliveryThresholdMs;
                        if (n > 0) {
                            l5 = n;
                            l4 = n;
                        }
                        boolean bl4 = true;
                        bl2 = l4 > 0L && ((Message)object).when > 0L;
                        bl3 = l5 > 0L;
                        boolean bl5 = bl4;
                        if (!bl2) {
                            bl5 = bl3 ? bl4 : false;
                        }
                        if (l2 != 0L && Trace.isTagEnabled(l2)) {
                            Trace.traceBegin(l2, ((Message)object).target.getTraceName((Message)object));
                        }
                        l6 = bl5 ? SystemClock.uptimeMillis() : 0L;
                        object2 = null;
                        if (object3 != null) {
                            object2 = object3.messageDispatchStarting();
                        }
                        l = ThreadLocalWorkSource.setUid(((Message)object).workSourceUid);
                        ((Message)object).target.dispatchMessage((Message)object);
                        if (object3 == null) break block24;
                        try {
                            object3.messageDispatched(object2, (Message)object);
                        }
                        catch (Throwable throwable) {
                            break block25;
                        }
                        catch (Exception exception) {
                            break block26;
                        }
                    }
                    long l7 = bl3 ? SystemClock.uptimeMillis() : 0L;
                    ThreadLocalWorkSource.restore(l);
                    if (l2 != 0L) {
                        Trace.traceEnd(l2);
                    }
                    if (bl2) {
                        if (bl) {
                            if (l6 - ((Message)object).when <= 10L) {
                                Slog.w(TAG, "Drained");
                                bl = false;
                            }
                        } else if (Looper.showSlowLog(l4, ((Message)object).when, l6, "delivery", (Message)object)) {
                            bl = true;
                        }
                    }
                    object2 = object;
                    if (bl3) {
                        Looper.showSlowLog(l5, l6, l7, "dispatch", (Message)object2);
                    }
                    if (printer != null) {
                        object3 = new StringBuilder();
                        ((StringBuilder)object3).append("<<<<< Finished to ");
                        object = object2;
                        ((StringBuilder)object3).append(((Message)object).target);
                        ((StringBuilder)object3).append(" ");
                        ((StringBuilder)object3).append(((Message)object).callback);
                        printer.println(((StringBuilder)object3).toString());
                    }
                    if (l3 != (l5 = Binder.clearCallingIdentity())) {
                        object = new StringBuilder();
                        ((StringBuilder)object).append("Thread identity changed from 0x");
                        ((StringBuilder)object).append(Long.toHexString(l3));
                        ((StringBuilder)object).append(" to 0x");
                        ((StringBuilder)object).append(Long.toHexString(l5));
                        ((StringBuilder)object).append(" while dispatching to ");
                        ((StringBuilder)object).append(((Message)object2).target.getClass().getName());
                        ((StringBuilder)object).append(" ");
                        ((StringBuilder)object).append(((Message)object2).callback);
                        ((StringBuilder)object).append(" what=");
                        ((StringBuilder)object).append(((Message)object2).what);
                        Log.wtf(TAG, ((StringBuilder)object).toString());
                    }
                    ((Message)object2).recycleUnchecked();
                } while (true);
                catch (Throwable throwable) {
                    break block25;
                }
                catch (Exception exception) {
                    // empty catch block
                }
            }
            if (object3 == null) throw var1_4;
            try {
                object3.dispatchingThrewException(object2, (Message)object, (Exception)var1_4);
                throw var1_4;
            }
            catch (Throwable throwable) {
                // empty catch block
            }
        }
        ThreadLocalWorkSource.restore(l);
        if (l2 == 0L) throw var4_10;
        Trace.traceEnd(l2);
        throw var4_10;
    }

    public static Looper myLooper() {
        return sThreadLocal.get();
    }

    public static MessageQueue myQueue() {
        return Looper.myLooper().mQueue;
    }

    public static void prepare() {
        Looper.prepare(true);
    }

    private static void prepare(boolean bl) {
        if (sThreadLocal.get() == null) {
            sThreadLocal.set(new Looper(bl));
            return;
        }
        throw new RuntimeException("Only one Looper may be created per thread");
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static void prepareMainLooper() {
        Looper.prepare(false);
        synchronized (Looper.class) {
            if (sMainLooper == null) {
                sMainLooper = Looper.myLooper();
                return;
            }
            IllegalStateException illegalStateException = new IllegalStateException("The main Looper has already been prepared.");
            throw illegalStateException;
        }
    }

    public static void setObserver(Observer observer) {
        sObserver = observer;
    }

    private static boolean showSlowLog(long l, long l2, long l3, String string2, Message message) {
        if ((l2 = l3 - l2) < l) {
            return false;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Slow ");
        stringBuilder.append(string2);
        stringBuilder.append(" took ");
        stringBuilder.append(l2);
        stringBuilder.append("ms ");
        stringBuilder.append(Thread.currentThread().getName());
        stringBuilder.append(" h=");
        stringBuilder.append(message.target.getClass().getName());
        stringBuilder.append(" c=");
        stringBuilder.append(message.callback);
        stringBuilder.append(" m=");
        stringBuilder.append(message.what);
        Slog.w(TAG, stringBuilder.toString());
        return true;
    }

    public void dump(Printer printer, String string2) {
        Object object = new StringBuilder();
        ((StringBuilder)object).append(string2);
        ((StringBuilder)object).append(this.toString());
        printer.println(((StringBuilder)object).toString());
        object = this.mQueue;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(string2);
        stringBuilder.append("  ");
        ((MessageQueue)object).dump(printer, stringBuilder.toString(), null);
    }

    public void dump(Printer printer, String string2, Handler handler) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(string2);
        stringBuilder.append(this.toString());
        printer.println(stringBuilder.toString());
        MessageQueue messageQueue = this.mQueue;
        stringBuilder = new StringBuilder();
        stringBuilder.append(string2);
        stringBuilder.append("  ");
        messageQueue.dump(printer, stringBuilder.toString(), handler);
    }

    public MessageQueue getQueue() {
        return this.mQueue;
    }

    public Thread getThread() {
        return this.mThread;
    }

    public boolean isCurrentThread() {
        boolean bl = Thread.currentThread() == this.mThread;
        return bl;
    }

    public void quit() {
        this.mQueue.quit(false);
    }

    public void quitSafely() {
        this.mQueue.quit(true);
    }

    public void setMessageLogging(Printer printer) {
        this.mLogging = printer;
    }

    public void setSlowLogThresholdMs(long l, long l2) {
        this.mSlowDispatchThresholdMs = l;
        this.mSlowDeliveryThresholdMs = l2;
    }

    @UnsupportedAppUsage
    public void setTraceTag(long l) {
        this.mTraceTag = l;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Looper (");
        stringBuilder.append(this.mThread.getName());
        stringBuilder.append(", tid ");
        stringBuilder.append(this.mThread.getId());
        stringBuilder.append(") {");
        stringBuilder.append(Integer.toHexString(System.identityHashCode(this)));
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    public void writeToProto(ProtoOutputStream protoOutputStream, long l) {
        l = protoOutputStream.start(l);
        protoOutputStream.write(1138166333441L, this.mThread.getName());
        protoOutputStream.write(1112396529666L, this.mThread.getId());
        MessageQueue messageQueue = this.mQueue;
        if (messageQueue != null) {
            messageQueue.writeToProto(protoOutputStream, 1146756268035L);
        }
        protoOutputStream.end(l);
    }

    public static interface Observer {
        public void dispatchingThrewException(Object var1, Message var2, Exception var3);

        public Object messageDispatchStarting();

        public void messageDispatched(Object var1, Message var2);
    }

}

