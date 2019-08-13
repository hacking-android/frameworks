/*
 * Decompiled with CFR 0.145.
 */
package java.util;

import java.util.Observer;
import java.util.Vector;

public class Observable {
    private boolean changed = false;
    private Vector<Observer> obs = new Vector();

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void addObserver(Observer object) {
        synchronized (this) {
            Throwable throwable2;
            if (object != null) {
                try {
                    if (!this.obs.contains(object)) {
                        this.obs.addElement((Observer)object);
                    }
                    return;
                }
                catch (Throwable throwable2) {}
            } else {
                object = new NullPointerException();
                throw object;
            }
            throw throwable2;
        }
    }

    protected void clearChanged() {
        synchronized (this) {
            this.changed = false;
            return;
        }
    }

    public int countObservers() {
        synchronized (this) {
            int n = this.obs.size();
            return n;
        }
    }

    public void deleteObserver(Observer observer) {
        synchronized (this) {
            this.obs.removeElement(observer);
            return;
        }
    }

    public void deleteObservers() {
        synchronized (this) {
            this.obs.removeAllElements();
            return;
        }
    }

    public boolean hasChanged() {
        synchronized (this) {
            boolean bl = this.changed;
            return bl;
        }
    }

    public void notifyObservers() {
        this.notifyObservers(null);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void notifyObservers(Object object) {
        Object[] arrobject;
        synchronized (this) {
            if (!this.hasChanged()) {
                return;
            }
            arrobject = this.obs.toArray();
            this.clearChanged();
        }
        int n = arrobject.length - 1;
        while (n >= 0) {
            ((Observer)arrobject[n]).update(this, object);
            --n;
        }
        return;
    }

    protected void setChanged() {
        synchronized (this) {
            this.changed = true;
            return;
        }
    }
}

