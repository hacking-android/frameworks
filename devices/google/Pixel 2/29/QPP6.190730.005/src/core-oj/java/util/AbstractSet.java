/*
 * Decompiled with CFR 0.145.
 */
package java.util;

import java.util.AbstractCollection;
import java.util.Collection;
import java.util.Iterator;
import java.util.Objects;
import java.util.Set;

public abstract class AbstractSet<E>
extends AbstractCollection<E>
implements Set<E> {
    protected AbstractSet() {
    }

    @Override
    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (!(object instanceof Set)) {
            return false;
        }
        if ((object = (Collection)object).size() != this.size()) {
            return false;
        }
        try {
            boolean bl = this.containsAll((Collection<?>)object);
            return bl;
        }
        catch (NullPointerException nullPointerException) {
            return false;
        }
        catch (ClassCastException classCastException) {
            return false;
        }
    }

    @Override
    public int hashCode() {
        int n = 0;
        for (Object e : this) {
            int n2 = n;
            if (e != null) {
                n2 = n + e.hashCode();
            }
            n = n2;
        }
        return n;
    }

    @Override
    public boolean removeAll(Collection<?> object) {
        Objects.requireNonNull(object);
        boolean bl = false;
        boolean bl2 = false;
        if (this.size() > object.size()) {
            object = object.iterator();
            while (object.hasNext()) {
                bl2 |= this.remove(object.next());
            }
            bl = bl2;
        } else {
            Iterator iterator = this.iterator();
            bl2 = bl;
            do {
                bl = bl2;
                if (!iterator.hasNext()) break;
                if (!object.contains(iterator.next())) continue;
                iterator.remove();
                bl2 = true;
            } while (true);
        }
        return bl;
    }
}

