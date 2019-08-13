/*
 * Decompiled with CFR 0.145.
 */
package java.util;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.AbstractSet;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Map;
import java.util.NavigableMap;
import java.util.NavigableSet;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.Spliterator;
import java.util.TreeMap;

public class TreeSet<E>
extends AbstractSet<E>
implements NavigableSet<E>,
Cloneable,
Serializable {
    private static final Object PRESENT = new Object();
    private static final long serialVersionUID = -2479143000061671589L;
    private transient NavigableMap<E, Object> m;

    public TreeSet() {
        this(new TreeMap());
    }

    public TreeSet(Collection<? extends E> collection) {
        this();
        this.addAll(collection);
    }

    public TreeSet(Comparator<? super E> comparator) {
        this(new TreeMap(comparator));
    }

    TreeSet(NavigableMap<E, Object> navigableMap) {
        this.m = navigableMap;
    }

    public TreeSet(SortedSet<E> sortedSet) {
        this(sortedSet.comparator());
        this.addAll(sortedSet);
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        TreeMap<E, Object> treeMap = new TreeMap<E, Object>((Comparator)objectInputStream.readObject());
        this.m = treeMap;
        treeMap.readTreeSet(objectInputStream.readInt(), objectInputStream, PRESENT);
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        objectOutputStream.writeObject(this.m.comparator());
        objectOutputStream.writeInt(this.m.size());
        Iterator iterator = this.m.keySet().iterator();
        while (iterator.hasNext()) {
            objectOutputStream.writeObject(iterator.next());
        }
    }

    @Override
    public boolean add(E e) {
        boolean bl = this.m.put(e, PRESENT) == null;
        return bl;
    }

    @Override
    public boolean addAll(Collection<? extends E> collection) {
        Comparator comparator;
        if (this.m.size() == 0 && collection.size() > 0 && collection instanceof SortedSet && (comparator = this.m) instanceof TreeMap) {
            Comparator comparator2;
            SortedSet sortedSet = (SortedSet)collection;
            TreeMap treeMap = (TreeMap)((Object)comparator);
            comparator = sortedSet.comparator();
            if (comparator == (comparator2 = treeMap.comparator()) || comparator != null && comparator.equals(comparator2)) {
                treeMap.addAllForTreeSet(sortedSet, PRESENT);
                return true;
            }
        }
        return super.addAll(collection);
    }

    @Override
    public E ceiling(E e) {
        return this.m.ceilingKey(e);
    }

    @Override
    public void clear() {
        this.m.clear();
    }

    public Object clone() {
        try {
            TreeSet treeSet = (TreeSet)Object.super.clone();
            treeSet.m = new TreeMap<E, Object>((SortedMap<E, Object>)this.m);
            return treeSet;
        }
        catch (CloneNotSupportedException cloneNotSupportedException) {
            throw new InternalError(cloneNotSupportedException);
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
        return new TreeSet<E>(this.m.descendingMap());
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
    public NavigableSet<E> headSet(E e, boolean bl) {
        return new TreeSet<E>(this.m.headMap(e, bl));
    }

    @Override
    public SortedSet<E> headSet(E e) {
        return this.headSet(e, false);
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
        Map.Entry<Object, Object> entry = this.m.pollFirstEntry();
        entry = entry == null ? null : entry.getKey();
        return (E)entry;
    }

    @Override
    public E pollLast() {
        Map.Entry<Object, Object> entry = this.m.pollLastEntry();
        entry = entry == null ? null : entry.getKey();
        return (E)entry;
    }

    @Override
    public boolean remove(Object object) {
        boolean bl = this.m.remove(object) == PRESENT;
        return bl;
    }

    @Override
    public int size() {
        return this.m.size();
    }

    @Override
    public Spliterator<E> spliterator() {
        return TreeMap.keySpliteratorFor(this.m);
    }

    @Override
    public NavigableSet<E> subSet(E e, boolean bl, E e2, boolean bl2) {
        return new TreeSet<E>(this.m.subMap(e, bl, e2, bl2));
    }

    @Override
    public SortedSet<E> subSet(E e, E e2) {
        return this.subSet(e, true, e2, false);
    }

    @Override
    public NavigableSet<E> tailSet(E e, boolean bl) {
        return new TreeSet<E>(this.m.tailMap(e, bl));
    }

    @Override
    public SortedSet<E> tailSet(E e) {
        return this.tailSet(e, true);
    }
}

