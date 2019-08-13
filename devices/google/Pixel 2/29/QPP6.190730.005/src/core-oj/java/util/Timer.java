/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  dalvik.annotation.optimization.ReachabilitySensitive
 */
package java.util;

import dalvik.annotation.optimization.ReachabilitySensitive;
import java.util.Date;
import java.util.TaskQueue;
import java.util.TimerTask;
import java.util.TimerThread;
import java.util.concurrent.atomic.AtomicInteger;

public class Timer {
    private static final AtomicInteger nextSerialNumber = new AtomicInteger(0);
    @ReachabilitySensitive
    private final TaskQueue queue;
    @ReachabilitySensitive
    private final TimerThread thread;
    private final Object threadReaper;

    public Timer() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Timer-");
        stringBuilder.append(Timer.serialNumber());
        this(stringBuilder.toString());
    }

    public Timer(String string) {
        this.queue = new TaskQueue();
        this.thread = new TimerThread(this.queue);
        this.threadReaper = new Object(){

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            protected void finalize() throws Throwable {
                TaskQueue taskQueue = Timer.this.queue;
                synchronized (taskQueue) {
                    Timer.access$100((Timer)Timer.this).newTasksMayBeScheduled = false;
                    Timer.this.queue.notify();
                    return;
                }
            }
        };
        this.thread.setName(string);
        this.thread.start();
    }

    public Timer(String string, boolean bl) {
        this.queue = new TaskQueue();
        this.thread = new TimerThread(this.queue);
        this.threadReaper = new /* invalid duplicate definition of identical inner class */;
        this.thread.setName(string);
        this.thread.setDaemon(bl);
        this.thread.start();
    }

    public Timer(boolean bl) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Timer-");
        stringBuilder.append(Timer.serialNumber());
        this(stringBuilder.toString(), bl);
    }

    static /* synthetic */ TimerThread access$100(Timer timer) {
        return timer.thread;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void sched(TimerTask object, long l, long l2) {
        if (l < 0L) {
            throw new IllegalArgumentException("Illegal execution time.");
        }
        if (Math.abs(l2) > 0x3FFFFFFFFFFFFFFFL) {
            l2 >>= 1;
        }
        TaskQueue taskQueue = this.queue;
        synchronized (taskQueue) {
            if (!this.thread.newTasksMayBeScheduled) {
                object = new IllegalStateException("Timer already cancelled.");
                throw object;
            }
            Object object2 = ((TimerTask)object).lock;
            synchronized (object2) {
                if (((TimerTask)object).state != 0) {
                    object = new IllegalStateException("Task already scheduled or cancelled");
                    throw object;
                }
                ((TimerTask)object).nextExecutionTime = l;
                ((TimerTask)object).period = l2;
                ((TimerTask)object).state = 1;
            }
            this.queue.add((TimerTask)object);
            if (this.queue.getMin() == object) {
                this.queue.notify();
            }
            return;
        }
    }

    private static int serialNumber() {
        return nextSerialNumber.getAndIncrement();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void cancel() {
        TaskQueue taskQueue = this.queue;
        synchronized (taskQueue) {
            this.thread.newTasksMayBeScheduled = false;
            this.queue.clear();
            this.queue.notify();
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public int purge() {
        int n = 0;
        TaskQueue taskQueue = this.queue;
        synchronized (taskQueue) {
            for (int i = this.queue.size(); i > 0; --i) {
                int n2 = n;
                if (this.queue.get((int)i).state == 3) {
                    this.queue.quickRemove(i);
                    n2 = n + 1;
                }
                n = n2;
            }
            if (n != 0) {
                this.queue.heapify();
            }
            return n;
        }
    }

    public void schedule(TimerTask timerTask, long l) {
        if (l >= 0L) {
            this.sched(timerTask, System.currentTimeMillis() + l, 0L);
            return;
        }
        throw new IllegalArgumentException("Negative delay.");
    }

    public void schedule(TimerTask timerTask, long l, long l2) {
        if (l >= 0L) {
            if (l2 > 0L) {
                this.sched(timerTask, System.currentTimeMillis() + l, -l2);
                return;
            }
            throw new IllegalArgumentException("Non-positive period.");
        }
        throw new IllegalArgumentException("Negative delay.");
    }

    public void schedule(TimerTask timerTask, Date date) {
        this.sched(timerTask, date.getTime(), 0L);
    }

    public void schedule(TimerTask timerTask, Date date, long l) {
        if (l > 0L) {
            this.sched(timerTask, date.getTime(), -l);
            return;
        }
        throw new IllegalArgumentException("Non-positive period.");
    }

    public void scheduleAtFixedRate(TimerTask timerTask, long l, long l2) {
        if (l >= 0L) {
            if (l2 > 0L) {
                this.sched(timerTask, System.currentTimeMillis() + l, l2);
                return;
            }
            throw new IllegalArgumentException("Non-positive period.");
        }
        throw new IllegalArgumentException("Negative delay.");
    }

    public void scheduleAtFixedRate(TimerTask timerTask, Date date, long l) {
        if (l > 0L) {
            this.sched(timerTask, date.getTime(), l);
            return;
        }
        throw new IllegalArgumentException("Non-positive period.");
    }

}

