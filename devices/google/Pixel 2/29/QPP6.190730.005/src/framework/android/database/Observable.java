/*
 * Decompiled with CFR 0.145.
 */
package android.database;

import java.util.ArrayList;

public abstract class Observable<T> {
    protected final ArrayList<T> mObservers = new ArrayList();

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void registerObserver(T t) {
        if (t == null) {
            throw new IllegalArgumentException("The observer is null.");
        }
        ArrayList<T> arrayList = this.mObservers;
        synchronized (arrayList) {
            if (!this.mObservers.contains(t)) {
                this.mObservers.add(t);
                return;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Observer ");
            stringBuilder.append(t);
            stringBuilder.append(" is already registered.");
            IllegalStateException illegalStateException = new IllegalStateException(stringBuilder.toString());
            throw illegalStateException;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void unregisterAll() {
        ArrayList<T> arrayList = this.mObservers;
        synchronized (arrayList) {
            this.mObservers.clear();
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void unregisterObserver(T t) {
        if (t == null) {
            throw new IllegalArgumentException("The observer is null.");
        }
        ArrayList<T> arrayList = this.mObservers;
        synchronized (arrayList) {
            int n = this.mObservers.indexOf(t);
            if (n != -1) {
                this.mObservers.remove(n);
                return;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Observer ");
            stringBuilder.append(t);
            stringBuilder.append(" was not registered.");
            IllegalStateException illegalStateException = new IllegalStateException(stringBuilder.toString());
            throw illegalStateException;
        }
    }
}

