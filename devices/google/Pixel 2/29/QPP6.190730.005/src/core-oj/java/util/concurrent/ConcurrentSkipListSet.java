/*
 * Decompiled with CFR 0.145.
 */
package java.util.concurrent;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.AbstractSet;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Map;
import java.util.NavigableMap;
import java.util.NavigableSet;
import java.util.Objects;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.Spliterator;
import java.util.concurrent.ConcurrentNavigableMap;
import java.util.concurrent.ConcurrentSkipListMap;
import sun.misc.Unsafe;

public class ConcurrentSkipListSet<E>
extends AbstractSet<E>
implements NavigableSet<E>,
Cloneable,
Serializable {
    private static final long MAP;
    private static final Unsafe U;
    private static final long serialVersionUID = -2479143111061671589L;
    private final ConcurrentNavigableMap<E, Object> m;

    static {
        U = Unsafe.getUnsafe();
        try {
            MAP = U.objectFieldOffset(ConcurrentSkipListSet.class.getDeclaredField("m"));
            return;
        }
        catch (ReflectiveOperationException reflectiveOperationException) {
            throw new Error(reflectiveOperationException);
        }
    }

    public ConcurrentSkipListSet() {
        this.m = new ConcurrentSkipListMap<E, Object>();
    }

    public ConcurrentSkipListSet(Collection<? extends E> collection) {
        this.m = new ConcurrentSkipListMap<E, Object>();
        this.addAll(collection);
    }

    public ConcurrentSkipListSet(Comparator<? super E> comparator) {
        this.m = new ConcurrentSkipListMap<E, Object>(comparator);
    }

    public ConcurrentSkipListSet(SortedSet<E> sortedSet) {
        this.m = new ConcurrentSkipListMap<E, Object>(sortedSet.comparator());
        this.addAll(sortedSet);
    }

    ConcurrentSkipListSet(ConcurrentNavigableMap<E, Object> concurrentNavigableMap) {
        this.m = concurrentNavigableMap;
    }

    private void setMap(ConcurrentNavigableMap<E, Object> concurrentNavigableMap) {
        U.putObjectVolatile(this, MAP, concurrentNavigableMap);
    }

    @Override
    public boolean add(E e) {
        boolean bl = this.m.putIfAbsent(e, Boolean.TRUE) == null;
        return bl;
    }

    @Override
    public E ceiling(E e) {
        return this.m.ceilingKey(e);
    }

    @Override
    public void clear() {
        this.m.clear();
    }

    public ConcurrentSkipListSet<E> clone() {
        try {
            ConcurrentSkipListSet concurrentSkipListSet = (ConcurrentSkipListSet)Object.super.clone();
            ConcurrentSkipListMap<E, Object> concurrentSkipListMap = new ConcurrentSkipListMap<E, Object>((SortedMap<E, Object>)this.m);
            concurrentSkipListSet.setMap(concurrentSkipListMap);
            return concurrentSkipListSet;
        }
        catch (CloneNotSupportedException cloneNotSupportedException) {
            throw new InternalError();
        }
    }

    @Override
    public Comparator<? super E> comparator() {
        return this.m.comparator();
    }

    @Override
    public boolean contains(Object object) {
        return this.m.containsKey(object);
    }

    @Override
    public Iterator<E> descendingIterator() {
        return this.m.descendingKeySet().iterator();
    }

    @Override
    public NavigableSet<E> descendingSet() {
        return new ConcurrentSkipListSet<E>((ConcurrentNavigableMap<E, Object>)this.m.descendingMap());
    }

    @Override
    public boolean equals(Object object) {
        boolean bl = true;
        if (object == this) {
            return true;
        }
        if (!(object instanceof Set)) {
            return false;
        }
        object = (Collection)object;
        try {
            boolean bl2;
            if (!this.containsAll((Collection<?>)object) || !(bl2 = object.containsAll(this))) {
                bl = false;
            }
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
    public E first() {
        return (E)this.m.firstKey();
    }

    @Override
    public E floor(E e) {
        return this.m.floorKey(e);
    }

    @Override
    public NavigableSet<E> headSet(E e) {
        return this.headSet(e, false);
    }

    @Override
    public NavigableSet<E> headSet(E e, boolean bl) {
        return new ConcurrentSkipListSet<E>((ConcurrentNavigableMap<E, Object>)this.m.headMap((Object)e, bl));
    }

    @Override
    public E higher(E e) {
        return this.m.higherKey(e);
    }

    @Override
    public boolean isEmpty() {
        return this.m.isEmpty();
    }

    @Override
    public Iterator<E> iterator() {
        return this.m.navigableKeySet().iterator();
    }

    @Override
    public E last() {
        return (E)this.m.lastKey();
    }

    @Override
    public E lower(E e) {
        return this.m.lowerKey(e);
    }

    @Override
    public E pollFirst() {
        Map.Entry entry = this.m.pollFirstEntry();
        entry = entry == null ? null : entry.getKey();
        return (E)entry;
    }

    @Override
    public E pollLast() {
        Map.Entry entry = this.m.pollLastEntry();
        entry = entry == null ? null : entry.getKey();
        return (E)entry;
    }

    @Override
    public boolean remove(Object object) {
        return this.m.remove(object, Boolean.TRUE);
    }

    @Override
    public boolean removeAll(Collection<?> object) {
        boolean bl = false;
        object = object.iterator();
        while (object.hasNext()) {
            if (!this.remove(object.next())) continue;
            bl = true;
        }
        return bl;
    }

    @Override
    public int size() {
        return this.m.size();
    }

    @Override
    public Spliterator<E> spliterator() {
        Object object = this.m;
        if (object instanceof ConcurrentSkipListMap) {
            object = ((ConcurrentSkipListMap)object).keySpliterator();
        } else {
            object = (ConcurrentSkipListMap.SubMap)object;
            Objects.requireNonNull(object);
            object = (ConcurrentSkipListMap.SubMap)object.new ConcurrentSkipListMap.SubMap.SubMapKeyIterator();
        }
        return object;
    }

    @Override
    public NavigableSet<E> subSet(E e, E e2) {
        return this.subSet(e, true, e2, false);
    }

    @Override
    public NavigableSet<E> subSet(E e, boolean bl, E e2, boolean bl2) {
        return new ConcurrentSkipListSet<E>((ConcurrentNavigableMap<E, Object>)this.m.subMap((Object)e, bl, (Object)e2, bl2));
    }

    @Override
    public NavigableSet<E> tailSet(E e) {
        return this.tailSet(e, true);
    }

    @Override
    public NavigableSet<E> tailSet(E e, boolean bl) {
        return new ConcurrentSkipListSet<E>((ConcurrentNavigableMap<E, Object>)this.m.tailMap((Object)e, bl));
    }
}

