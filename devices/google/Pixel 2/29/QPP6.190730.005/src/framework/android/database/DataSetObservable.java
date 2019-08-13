/*
 * Decompiled with CFR 0.145.
 */
package android.database;

import android.database.DataSetObserver;
import android.database.Observable;
import java.util.ArrayList;

public class DataSetObservable
extends Observable<DataSetObserver> {
    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void notifyChanged() {
        ArrayList arrayList = this.mObservers;
        synchronized (arrayList) {
            int n = this.mObservers.size() - 1;
            while (n >= 0) {
                ((DataSetObserver)this.mObservers.get(n)).onChanged();
                --n;
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void notifyInvalidated() {
        ArrayList arrayList = this.mObservers;
        synchronized (arrayList) {
            int n = this.mObservers.size() - 1;
            while (n >= 0) {
                ((DataSetObserver)this.mObservers.get(n)).onInvalidated();
                --n;
            }
            return;
        }
    }
}

