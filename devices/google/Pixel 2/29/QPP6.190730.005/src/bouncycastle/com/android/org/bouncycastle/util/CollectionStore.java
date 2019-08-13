/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.util;

import com.android.org.bouncycastle.util.Iterable;
import com.android.org.bouncycastle.util.Selector;
import com.android.org.bouncycastle.util.Store;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

public class CollectionStore<T>
implements Store<T>,
Iterable<T> {
    private Collection<T> _local;

    public CollectionStore(Collection<T> collection) {
        this._local = new ArrayList<T>(collection);
    }

    @Override
    public Collection<T> getMatches(Selector<T> selector) {
        if (selector == null) {
            return new ArrayList<T>(this._local);
        }
        ArrayList<T> arrayList = new ArrayList<T>();
        for (T t : this._local) {
            if (!selector.match(t)) continue;
            arrayList.add(t);
        }
        return arrayList;
    }

    @Override
    public Iterator<T> iterator() {
        return this.getMatches(null).iterator();
    }
}

