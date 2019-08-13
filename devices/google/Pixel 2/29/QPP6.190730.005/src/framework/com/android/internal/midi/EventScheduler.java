/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.midi;

import java.util.SortedMap;
import java.util.TreeMap;

public class EventScheduler {
    private static final long NANOS_PER_MILLI = 1000000L;
    private boolean mClosed;
    private volatile SortedMap<Long, FastEventQueue> mEventBuffer = new TreeMap<Long, FastEventQueue>();
    private FastEventQueue mEventPool = null;
    private final Object mLock = new Object();
    private int mMaxPoolSize = 200;

    private SchedulableEvent removeNextEventLocked(long l) {
        FastEventQueue fastEventQueue = (FastEventQueue)this.mEventBuffer.get(l);
        if (fastEventQueue.size() == 1) {
            this.mEventBuffer.remove(l);
        }
        return fastEventQueue.remove();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void add(SchedulableEvent schedulableEvent) {
        Object object = this.mLock;
        synchronized (object) {
            FastEventQueue fastEventQueue = (FastEventQueue)this.mEventBuffer.get(schedulableEvent.getTimestamp());
            if (fastEventQueue == null) {
                long l = this.mEventBuffer.isEmpty() ? Long.MAX_VALUE : this.mEventBuffer.firstKey();
                fastEventQueue = new FastEventQueue(schedulableEvent);
                this.mEventBuffer.put(schedulableEvent.getTimestamp(), fastEventQueue);
                if (schedulableEvent.getTimestamp() < l) {
                    this.mLock.notify();
                }
            } else {
                fastEventQueue.add(schedulableEvent);
            }
            return;
        }
    }

    public void addEventToPool(SchedulableEvent schedulableEvent) {
        FastEventQueue fastEventQueue = this.mEventPool;
        if (fastEventQueue == null) {
            this.mEventPool = new FastEventQueue(schedulableEvent);
        } else if (fastEventQueue.size() < this.mMaxPoolSize) {
            this.mEventPool.add(schedulableEvent);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void close() {
        Object object = this.mLock;
        synchronized (object) {
            this.mClosed = true;
            this.mLock.notify();
            return;
        }
    }

    protected void flush() {
        this.mEventBuffer = new TreeMap<Long, FastEventQueue>();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public SchedulableEvent getNextEvent(long l) {
        SchedulableEvent schedulableEvent = null;
        Object object = this.mLock;
        synchronized (object) {
            SchedulableEvent schedulableEvent2 = schedulableEvent;
            if (this.mEventBuffer.isEmpty()) return schedulableEvent2;
            long l2 = this.mEventBuffer.firstKey();
            schedulableEvent2 = schedulableEvent;
            if (l2 > l) return schedulableEvent2;
            return this.removeNextEventLocked(l2);
        }
    }

    public SchedulableEvent removeEventfromPool() {
        SchedulableEvent schedulableEvent = null;
        FastEventQueue fastEventQueue = this.mEventPool;
        SchedulableEvent schedulableEvent2 = schedulableEvent;
        if (fastEventQueue != null) {
            schedulableEvent2 = schedulableEvent;
            if (fastEventQueue.size() > 1) {
                schedulableEvent2 = this.mEventPool.remove();
            }
        }
        return schedulableEvent2;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public SchedulableEvent waitNextEvent() throws InterruptedException {
        SchedulableEvent schedulableEvent = null;
        Object object = this.mLock;
        synchronized (object) {
            do {
                SchedulableEvent schedulableEvent2 = schedulableEvent;
                if (this.mClosed) return schedulableEvent2;
                long l = Integer.MAX_VALUE;
                if (!this.mEventBuffer.isEmpty()) {
                    long l2 = System.nanoTime();
                    l = this.mEventBuffer.firstKey();
                    if (l <= l2) {
                        return this.removeNextEventLocked(l);
                    }
                    l = l2 = (l - l2) / 1000000L + 1L;
                    if (l2 > Integer.MAX_VALUE) {
                        l = Integer.MAX_VALUE;
                    }
                }
                this.mLock.wait((int)l);
            } while (true);
        }
    }

    private class FastEventQueue {
        volatile long mEventsAdded;
        volatile long mEventsRemoved;
        volatile SchedulableEvent mFirst;
        volatile SchedulableEvent mLast;

        FastEventQueue(SchedulableEvent schedulableEvent) {
            this.mLast = this.mFirst = schedulableEvent;
            this.mEventsAdded = 1L;
            this.mEventsRemoved = 0L;
        }

        public void add(SchedulableEvent schedulableEvent) {
            schedulableEvent.mNext = null;
            this.mLast.mNext = schedulableEvent;
            this.mLast = schedulableEvent;
            ++this.mEventsAdded;
        }

        public SchedulableEvent remove() {
            ++this.mEventsRemoved;
            SchedulableEvent schedulableEvent = this.mFirst;
            this.mFirst = schedulableEvent.mNext;
            schedulableEvent.mNext = null;
            return schedulableEvent;
        }

        int size() {
            return (int)(this.mEventsAdded - this.mEventsRemoved);
        }
    }

    public static class SchedulableEvent {
        private volatile SchedulableEvent mNext = null;
        private long mTimestamp;

        public SchedulableEvent(long l) {
            this.mTimestamp = l;
        }

        public long getTimestamp() {
            return this.mTimestamp;
        }

        public void setTimestamp(long l) {
            this.mTimestamp = l;
        }
    }

}

