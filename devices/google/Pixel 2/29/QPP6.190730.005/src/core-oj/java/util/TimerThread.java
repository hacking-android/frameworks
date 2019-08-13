/*
 * Decompiled with CFR 0.145.
 */
package java.util;

import java.util.TaskQueue;
import java.util.TimerTask;

class TimerThread
extends Thread {
    boolean newTasksMayBeScheduled = true;
    private TaskQueue queue;

    TimerThread(TaskQueue taskQueue) {
        this.queue = taskQueue;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    private void mainLoop() {
        do {
            TaskQueue taskQueue = this.queue;
            // MONITORENTER : taskQueue
            while (this.queue.isEmpty() && this.newTasksMayBeScheduled) {
                this.queue.wait();
            }
            if (this.queue.isEmpty()) {
                // MONITOREXIT : taskQueue
                return;
            }
            TimerTask timerTask = this.queue.getMin();
            Object object = timerTask.lock;
            // MONITORENTER : object
            if (timerTask.state == 3) {
                this.queue.removeMin();
                // MONITOREXIT : object
                // MONITOREXIT : taskQueue
                continue;
            }
            long l = timerTask.nextExecutionTime;
            long l2 = System.currentTimeMillis();
            boolean bl = l <= l2;
            if (bl) {
                if (timerTask.period == 0L) {
                    this.queue.removeMin();
                    timerTask.state = 2;
                } else {
                    TaskQueue taskQueue2 = this.queue;
                    long l3 = timerTask.period < 0L ? l2 - timerTask.period : timerTask.period + l;
                    taskQueue2.rescheduleMin(l3);
                }
            }
            // MONITOREXIT : object
            if (!bl) {
                this.queue.wait(l - l2);
            }
            // MONITOREXIT : taskQueue
            if (!bl) continue;
            try {
                timerTask.run();
            }
            catch (InterruptedException interruptedException) {
            }
        } while (true);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void run() {
        try {
            this.mainLoop();
            return;
        }
        finally {
            TaskQueue taskQueue = this.queue;
            synchronized (taskQueue) {
                this.newTasksMayBeScheduled = false;
                this.queue.clear();
            }
        }
    }
}

