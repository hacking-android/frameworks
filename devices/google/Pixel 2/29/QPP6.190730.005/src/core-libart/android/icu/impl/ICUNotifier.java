/*
 * Decompiled with CFR 0.145.
 */
package android.icu.impl;

import java.util.ArrayList;
import java.util.EventListener;
import java.util.Iterator;
import java.util.List;

public abstract class ICUNotifier {
    private List<EventListener> listeners;
    private final Object notifyLock = new Object();
    private NotifyThread notifyThread;

    protected abstract boolean acceptsListener(EventListener var1);

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void addListener(EventListener eventListener) {
        if (eventListener == null) {
            throw new NullPointerException();
        }
        if (!this.acceptsListener(eventListener)) {
            throw new IllegalStateException("Listener invalid for this notifier.");
        }
        Object object = this.notifyLock;
        synchronized (object) {
            if (this.listeners == null) {
                ArrayList<EventListener> arrayList = new ArrayList<EventListener>();
                this.listeners = arrayList;
            } else {
                Iterator<EventListener> iterator = this.listeners.iterator();
                while (iterator.hasNext()) {
                    if (iterator.next() != eventListener) continue;
                    return;
                }
            }
            this.listeners.add(eventListener);
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void notifyChanged() {
        Object object = this.notifyLock;
        synchronized (object) {
            if (this.listeners != null) {
                if (this.notifyThread == null) {
                    NotifyThread notifyThread;
                    this.notifyThread = notifyThread = new NotifyThread(this);
                    this.notifyThread.setDaemon(true);
                    this.notifyThread.start();
                }
                this.notifyThread.queue(this.listeners.toArray(new EventListener[this.listeners.size()]));
            }
            return;
        }
    }

    protected abstract void notifyListener(EventListener var1);

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void removeListener(EventListener eventListener) {
        if (eventListener == null) {
            throw new NullPointerException();
        }
        Object object = this.notifyLock;
        synchronized (object) {
            if (this.listeners != null) {
                Iterator<EventListener> iterator = this.listeners.iterator();
                while (iterator.hasNext()) {
                    if (iterator.next() != eventListener) continue;
                    iterator.remove();
                    if (this.listeners.size() == 0) {
                        this.listeners = null;
                    }
                    return;
                }
            }
            return;
        }
    }

    private static class NotifyThread
    extends Thread {
        private final ICUNotifier notifier;
        private final List<EventListener[]> queue = new ArrayList<EventListener[]>();

        NotifyThread(ICUNotifier iCUNotifier) {
            this.notifier = iCUNotifier;
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public void queue(EventListener[] arreventListener) {
            synchronized (this) {
                this.queue.add(arreventListener);
                this.notify();
                return;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         * Converted monitor instructions to comments
         * Lifted jumps to return sites
         */
        @Override
        public void run() {
            block7 : do {
                if (this.queue.isEmpty()) {
                    this.wait();
                    continue;
                }
                EventListener[] arreventListener = this.queue;
                int n = 0;
                arreventListener = arreventListener.remove(0);
                // MONITOREXIT : this
                try {
                    do {
                        if (n >= arreventListener.length) continue block7;
                        this.notifier.notifyListener(arreventListener[n]);
                        ++n;
                    } while (true);
                }
                catch (InterruptedException interruptedException) {
                    continue;
                }
                break;
            } while (true);
        }
    }

}

