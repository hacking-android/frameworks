/*
 * Decompiled with CFR 0.145.
 */
package java.util;

import java.util.Arrays;
import java.util.TimerTask;

class TaskQueue {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private TimerTask[] queue = new TimerTask[128];
    private int size = 0;

    TaskQueue() {
    }

    private void fixDown(int n) {
        int n2 = n;
        do {
            int n3 = n = n2 << 1;
            int n4 = this.size;
            if (n > n4 || n3 <= 0) break;
            n = n3;
            if (n3 < n4) {
                n = n3;
                if (this.queue[n3].nextExecutionTime > this.queue[n3 + 1].nextExecutionTime) {
                    n = n3 + 1;
                }
            }
            if (this.queue[n2].nextExecutionTime <= this.queue[n].nextExecutionTime) break;
            TimerTask[] arrtimerTask = this.queue;
            TimerTask timerTask = arrtimerTask[n];
            arrtimerTask[n] = arrtimerTask[n2];
            arrtimerTask[n2] = timerTask;
            n2 = n;
        } while (true);
    }

    private void fixUp(int n) {
        while (n > 1) {
            int n2 = n >> 1;
            if (this.queue[n2].nextExecutionTime <= this.queue[n].nextExecutionTime) break;
            TimerTask[] arrtimerTask = this.queue;
            TimerTask timerTask = arrtimerTask[n2];
            arrtimerTask[n2] = arrtimerTask[n];
            arrtimerTask[n] = timerTask;
            n = n2;
        }
    }

    void add(TimerTask timerTask) {
        int n = this.size;
        TimerTask[] arrtimerTask = this.queue;
        if (n + 1 == arrtimerTask.length) {
            this.queue = Arrays.copyOf(arrtimerTask, arrtimerTask.length * 2);
        }
        arrtimerTask = this.queue;
        this.size = n = this.size + 1;
        arrtimerTask[n] = timerTask;
        this.fixUp(this.size);
    }

    void clear() {
        for (int i = 1; i <= this.size; ++i) {
            this.queue[i] = null;
        }
        this.size = 0;
    }

    TimerTask get(int n) {
        return this.queue[n];
    }

    TimerTask getMin() {
        return this.queue[1];
    }

    void heapify() {
        for (int i = this.size / 2; i >= 1; --i) {
            this.fixDown(i);
        }
    }

    boolean isEmpty() {
        boolean bl = this.size == 0;
        return bl;
    }

    void quickRemove(int n) {
        TimerTask[] arrtimerTask = this.queue;
        int n2 = this.size;
        arrtimerTask[n] = arrtimerTask[n2];
        this.size = n2 - 1;
        arrtimerTask[n2] = null;
    }

    void removeMin() {
        TimerTask[] arrtimerTask = this.queue;
        int n = this.size;
        arrtimerTask[1] = arrtimerTask[n];
        this.size = n - 1;
        arrtimerTask[n] = null;
        this.fixDown(1);
    }

    void rescheduleMin(long l) {
        this.queue[1].nextExecutionTime = l;
        this.fixDown(1);
    }

    int size() {
        return this.size;
    }
}

