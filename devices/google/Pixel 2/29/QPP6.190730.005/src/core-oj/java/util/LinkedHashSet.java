/*
 * Decompiled with CFR 0.145.
 */
package java.util;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.Spliterator;
import java.util.Spliterators;

public class LinkedHashSet<E>
extends HashSet<E>
implements Set<E>,
Cloneable,
Serializable {
    private static final long serialVersionUID = -2851667679971038690L;

    public LinkedHashSet() {
        super(16, 0.75f, true);
    }

    public LinkedHashSet(int n) {
        super(n, 0.75f, true);
    }

    public LinkedHashSet(int n, float f) {
        super(n, f, true);
    }

    public LinkedHashSet(Collection<? extends E> collection) {
        super(Math.max(collection.size() * 2, 11), 0.75f, true);
        this.addAll(collection);
    }

    @Override
    public Spliterator<E> spliterator() {
        return Spliterators.spliterator(this, 17);
    }
}

