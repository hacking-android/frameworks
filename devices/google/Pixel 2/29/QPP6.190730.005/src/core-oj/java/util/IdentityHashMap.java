/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  java.util.IdentityHashMap.IdentityHashMapIterator
 */
package java.util;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.io.StreamCorruptedException;
import java.lang.reflect.Array;
import java.util.AbstractCollection;
import java.util.AbstractMap;
import java.util.AbstractSet;
import java.util.Collection;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Set;
import java.util.Spliterator;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;

public class IdentityHashMap<K, V>
extends AbstractMap<K, V>
implements Map<K, V>,
Serializable,
Cloneable {
    private static final int DEFAULT_CAPACITY = 32;
    private static final int MAXIMUM_CAPACITY = 536870912;
    private static final int MINIMUM_CAPACITY = 4;
    static final Object NULL_KEY = new Object();
    private static final long serialVersionUID = 8188218128353913216L;
    private transient Set<Map.Entry<K, V>> entrySet;
    transient int modCount;
    int size;
    transient Object[] table;

    public IdentityHashMap() {
        this.init(32);
    }

    public IdentityHashMap(int n) {
        if (n >= 0) {
            this.init(IdentityHashMap.capacity(n));
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("expectedMaxSize is negative: ");
        stringBuilder.append(n);
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    public IdentityHashMap(Map<? extends K, ? extends V> map) {
        this((int)((double)(map.size() + 1) * 1.1));
        this.putAll(map);
    }

    private static int capacity(int n) {
        n = n > 178956970 ? 536870912 : (n <= 2 ? 4 : Integer.highestOneBit((n << 1) + n));
        return n;
    }

    private void closeDeletion(int n) {
        Object object;
        Object[] arrobject = this.table;
        int n2 = arrobject.length;
        int n3 = IdentityHashMap.nextKeyIndex(n, n2);
        int n4 = n;
        n = n3;
        while ((object = arrobject[n]) != null) {
            block4 : {
                block3 : {
                    int n5 = IdentityHashMap.hash(object, n2);
                    if (n < n5 && (n5 <= n4 || n4 <= n)) break block3;
                    n3 = n4;
                    if (n5 > n4) break block4;
                    n3 = n4;
                    if (n4 > n) break block4;
                }
                arrobject[n4] = object;
                arrobject[n4 + 1] = arrobject[n + 1];
                arrobject[n] = null;
                arrobject[n + 1] = null;
                n3 = n;
            }
            n = IdentityHashMap.nextKeyIndex(n, n2);
            n4 = n3;
        }
    }

    private boolean containsMapping(Object object, Object object2) {
        object = IdentityHashMap.maskNull(object);
        Object[] arrobject = this.table;
        int n = arrobject.length;
        int n2 = IdentityHashMap.hash(object, n);
        do {
            Object object3 = arrobject[n2];
            boolean bl = false;
            if (object3 == object) {
                if (arrobject[n2 + 1] == object2) {
                    bl = true;
                }
                return bl;
            }
            if (object3 == null) {
                return false;
            }
            n2 = IdentityHashMap.nextKeyIndex(n2, n);
        } while (true);
    }

    private static int hash(Object object, int n) {
        int n2 = System.identityHashCode(object);
        return (n2 << 1) - (n2 << 8) & n - 1;
    }

    private void init(int n) {
        this.table = new Object[n * 2];
    }

    private static Object maskNull(Object object) {
        block0 : {
            if (object != null) break block0;
            object = NULL_KEY;
        }
        return object;
    }

    private static int nextKeyIndex(int n, int n2) {
        n = n + 2 < n2 ? (n += 2) : 0;
        return n;
    }

    private void putForCreate(K object, V v) throws StreamCorruptedException {
        Object object2;
        object = IdentityHashMap.maskNull(object);
        Object[] arrobject = this.table;
        int n = arrobject.length;
        int n2 = IdentityHashMap.hash(object, n);
        while ((object2 = arrobject[n2]) != null) {
            if (object2 != object) {
                n2 = IdentityHashMap.nextKeyIndex(n2, n);
                continue;
            }
            throw new StreamCorruptedException();
        }
        arrobject[n2] = object;
        arrobject[n2 + 1] = v;
    }

    private void readObject(ObjectInputStream object) throws IOException, ClassNotFoundException {
        ((ObjectInputStream)object).defaultReadObject();
        int n = ((ObjectInputStream)object).readInt();
        if (n >= 0) {
            this.init(IdentityHashMap.capacity(n));
            for (int i = 0; i < n; ++i) {
                this.putForCreate(((ObjectInputStream)object).readObject(), ((ObjectInputStream)object).readObject());
            }
            return;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Illegal mappings count: ");
        ((StringBuilder)object).append(n);
        throw new StreamCorruptedException(((StringBuilder)object).toString());
    }

    private boolean removeMapping(Object object, Object object2) {
        Object object3 = IdentityHashMap.maskNull(object);
        Object[] arrobject = this.table;
        int n = arrobject.length;
        int n2 = IdentityHashMap.hash(object3, n);
        do {
            if ((object = arrobject[n2]) == object3) {
                if (arrobject[n2 + 1] != object2) {
                    return false;
                }
                ++this.modCount;
                --this.size;
                arrobject[n2] = null;
                arrobject[n2 + 1] = null;
                this.closeDeletion(n2);
                return true;
            }
            if (object == null) {
                return false;
            }
            n2 = IdentityHashMap.nextKeyIndex(n2, n);
        } while (true);
    }

    private boolean resize(int n) {
        int n2 = n * 2;
        Object[] arrobject = this.table;
        int n3 = arrobject.length;
        if (n3 == 1073741824) {
            if (this.size != 536870911) {
                return false;
            }
            throw new IllegalStateException("Capacity exhausted.");
        }
        if (n3 >= n2) {
            return false;
        }
        Object[] arrobject2 = new Object[n2];
        for (n = 0; n < n3; n += 2) {
            Object object = arrobject[n];
            if (object == null) continue;
            Object object2 = arrobject[n + 1];
            arrobject[n] = null;
            arrobject[n + 1] = null;
            int n4 = IdentityHashMap.hash(object, n2);
            while (arrobject2[n4] != null) {
                n4 = IdentityHashMap.nextKeyIndex(n4, n2);
            }
            arrobject2[n4] = object;
            arrobject2[n4 + 1] = object2;
        }
        this.table = arrobject2;
        return true;
    }

    static final Object unmaskNull(Object object) {
        block0 : {
            if (object != NULL_KEY) break block0;
            object = null;
        }
        return object;
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        objectOutputStream.writeInt(this.size);
        Object[] arrobject = this.table;
        for (int i = 0; i < arrobject.length; i += 2) {
            Object object = arrobject[i];
            if (object == null) continue;
            objectOutputStream.writeObject(IdentityHashMap.unmaskNull(object));
            objectOutputStream.writeObject(arrobject[i + 1]);
        }
    }

    @Override
    public void clear() {
        ++this.modCount;
        Object[] arrobject = this.table;
        for (int i = 0; i < arrobject.length; ++i) {
            arrobject[i] = null;
        }
        this.size = 0;
    }

    @Override
    public Object clone() {
        try {
            IdentityHashMap identityHashMap = (IdentityHashMap)super.clone();
            identityHashMap.entrySet = null;
            identityHashMap.table = (Object[])this.table.clone();
            return identityHashMap;
        }
        catch (CloneNotSupportedException cloneNotSupportedException) {
            throw new InternalError(cloneNotSupportedException);
        }
    }

    @Override
    public boolean containsKey(Object object) {
        Object object2 = IdentityHashMap.maskNull(object);
        Object[] arrobject = this.table;
        int n = arrobject.length;
        int n2 = IdentityHashMap.hash(object2, n);
        while ((object = arrobject[n2]) != object2) {
            if (object == null) {
                return false;
            }
            n2 = IdentityHashMap.nextKeyIndex(n2, n);
        }
        return true;
    }

    @Override
    public boolean containsValue(Object object) {
        Object[] arrobject = this.table;
        for (int i = 1; i < arrobject.length; i += 2) {
            if (arrobject[i] != object || arrobject[i - 1] == null) continue;
            return true;
        }
        return false;
    }

    @Override
    public Set<Map.Entry<K, V>> entrySet() {
        EntrySet entrySet = this.entrySet;
        if (entrySet != null) {
            return entrySet;
        }
        this.entrySet = entrySet = new EntrySet();
        return entrySet;
    }

    @Override
    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (object instanceof IdentityHashMap) {
            if (((IdentityHashMap)(object = (IdentityHashMap)object)).size() != this.size) {
                return false;
            }
            Object[] arrobject = ((IdentityHashMap)object).table;
            for (int i = 0; i < arrobject.length; i += 2) {
                object = arrobject[i];
                if (object == null || this.containsMapping(object, arrobject[i + 1])) continue;
                return false;
            }
            return true;
        }
        if (object instanceof Map) {
            object = (Map)object;
            return this.entrySet().equals(object.entrySet());
        }
        return false;
    }

    @Override
    public void forEach(BiConsumer<? super K, ? super V> biConsumer) {
        Objects.requireNonNull(biConsumer);
        int n = this.modCount;
        Object[] arrobject = this.table;
        for (int i = 0; i < arrobject.length; i += 2) {
            Object object = arrobject[i];
            if (object != null) {
                biConsumer.accept(IdentityHashMap.unmaskNull(object), arrobject[i + 1]);
            }
            if (this.modCount == n) {
                continue;
            }
            throw new ConcurrentModificationException();
        }
    }

    @Override
    public V get(Object arrobject) {
        Object object = IdentityHashMap.maskNull(arrobject);
        arrobject = this.table;
        int n = arrobject.length;
        int n2 = IdentityHashMap.hash(object, n);
        Object object2;
        while ((object2 = arrobject[n2]) != object) {
            if (object2 == null) {
                return null;
            }
            n2 = IdentityHashMap.nextKeyIndex(n2, n);
        }
        return (V)arrobject[n2 + 1];
    }

    @Override
    public int hashCode() {
        int n = 0;
        Object[] arrobject = this.table;
        for (int i = 0; i < arrobject.length; i += 2) {
            Object object = arrobject[i];
            int n2 = n;
            if (object != null) {
                n2 = n + (System.identityHashCode(IdentityHashMap.unmaskNull(object)) ^ System.identityHashCode(arrobject[i + 1]));
            }
            n = n2;
        }
        return n;
    }

    @Override
    public boolean isEmpty() {
        boolean bl = this.size == 0;
        return bl;
    }

    @Override
    public Set<K> keySet() {
        Set set;
        Set set2 = set = this.keySet;
        if (set == null) {
            this.keySet = set2 = new KeySet();
        }
        return set2;
    }

    @Override
    public V put(K object, V v) {
        int n;
        int n2;
        Object object2 = IdentityHashMap.maskNull(object);
        do {
            K k;
            object = this.table;
            n = ((K)object).length;
            int n3 = IdentityHashMap.hash(object2, n);
            while ((k = object[n3]) != null) {
                if (k == object2) {
                    object2 = object[n3 + 1];
                    object[n3 + 1] = v;
                    return (V)object2;
                }
                n3 = IdentityHashMap.nextKeyIndex(n3, n);
            }
        } while (((n2 = this.size + 1) << 1) + n2 > n && this.resize(n));
        ++this.modCount;
        object[n3] = object2;
        object[n3 + 1] = v;
        this.size = n2;
        return null;
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> object2) {
        int n = object2.size();
        if (n == 0) {
            return;
        }
        if (n > this.size) {
            this.resize(IdentityHashMap.capacity(n));
        }
        for (Map.Entry entry : object2.entrySet()) {
            this.put(entry.getKey(), entry.getValue());
        }
    }

    @Override
    public V remove(Object arrobject) {
        Object object = IdentityHashMap.maskNull(arrobject);
        arrobject = this.table;
        int n = arrobject.length;
        int n2 = IdentityHashMap.hash(object, n);
        do {
            Object object2;
            if ((object2 = arrobject[n2]) == object) {
                ++this.modCount;
                --this.size;
                object2 = arrobject[n2 + 1];
                arrobject[n2 + 1] = null;
                arrobject[n2] = null;
                this.closeDeletion(n2);
                return (V)object2;
            }
            if (object2 == null) {
                return null;
            }
            n2 = IdentityHashMap.nextKeyIndex(n2, n);
        } while (true);
    }

    @Override
    public void replaceAll(BiFunction<? super K, ? super V, ? extends V> biFunction) {
        Objects.requireNonNull(biFunction);
        int n = this.modCount;
        Object[] arrobject = this.table;
        for (int i = 0; i < arrobject.length; i += 2) {
            Object object = arrobject[i];
            if (object != null) {
                arrobject[i + 1] = biFunction.apply(IdentityHashMap.unmaskNull(object), arrobject[i + 1]);
            }
            if (this.modCount == n) {
                continue;
            }
            throw new ConcurrentModificationException();
        }
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public Collection<V> values() {
        Collection collection;
        Collection collection2 = collection = this.values;
        if (collection == null) {
            this.values = collection2 = new Values();
        }
        return collection2;
    }

    private class EntryIterator
    extends java.util.IdentityHashMap.IdentityHashMapIterator<Map.Entry<K, V>> {
        private IdentityHashMap<K, V> lastReturnedEntry;

        private EntryIterator() {
        }

        public Map.Entry<K, V> next() {
            this.lastReturnedEntry = new Entry(this.nextIndex());
            return this.lastReturnedEntry;
        }

        public void remove() {
            IdentityHashMap<K, V> identityHashMap = this.lastReturnedEntry;
            int n = identityHashMap == null ? -1 : identityHashMap.index;
            this.lastReturnedIndex = n;
            IdentityHashMapIterator.super.remove();
            this.lastReturnedEntry.index = this.lastReturnedIndex;
            this.lastReturnedEntry = null;
        }

        private class Entry
        implements Map.Entry<K, V> {
            private int index;

            private Entry(int n) {
                this.index = n;
            }

            private void checkIndexForEntryUse() {
                if (this.index >= 0) {
                    return;
                }
                throw new IllegalStateException("Entry was removed");
            }

            @Override
            public boolean equals(Object object) {
                boolean bl;
                block2 : {
                    if (this.index < 0) {
                        return super.equals(object);
                    }
                    boolean bl2 = object instanceof Map.Entry;
                    bl = false;
                    if (!bl2) {
                        return false;
                    }
                    if ((object = (Map.Entry)object).getKey() != IdentityHashMap.unmaskNull(EntryIterator.this.traversalTable[this.index]) || object.getValue() != EntryIterator.this.traversalTable[this.index + 1]) break block2;
                    bl = true;
                }
                return bl;
            }

            @Override
            public K getKey() {
                this.checkIndexForEntryUse();
                return (K)IdentityHashMap.unmaskNull(EntryIterator.this.traversalTable[this.index]);
            }

            @Override
            public V getValue() {
                this.checkIndexForEntryUse();
                return (V)EntryIterator.this.traversalTable[this.index + 1];
            }

            @Override
            public int hashCode() {
                if (EntryIterator.this.lastReturnedIndex < 0) {
                    return super.hashCode();
                }
                return System.identityHashCode(IdentityHashMap.unmaskNull(EntryIterator.this.traversalTable[this.index])) ^ System.identityHashCode(EntryIterator.this.traversalTable[this.index + 1]);
            }

            @Override
            public V setValue(V v) {
                this.checkIndexForEntryUse();
                Object object = EntryIterator.this.traversalTable[this.index + 1];
                EntryIterator.this.traversalTable[this.index + 1] = v;
                if (EntryIterator.this.traversalTable != IdentityHashMap.this.table) {
                    IdentityHashMap.this.put(EntryIterator.this.traversalTable[this.index], v);
                }
                return (V)object;
            }

            public String toString() {
                if (this.index < 0) {
                    return super.toString();
                }
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(IdentityHashMap.unmaskNull(EntryIterator.this.traversalTable[this.index]));
                stringBuilder.append("=");
                stringBuilder.append(EntryIterator.this.traversalTable[this.index + 1]);
                return stringBuilder.toString();
            }
        }

    }

    private class EntrySet
    extends AbstractSet<Map.Entry<K, V>> {
        private EntrySet() {
        }

        @Override
        public void clear() {
            IdentityHashMap.this.clear();
        }

        @Override
        public boolean contains(Object object) {
            if (!(object instanceof Map.Entry)) {
                return false;
            }
            object = (Map.Entry)object;
            return IdentityHashMap.this.containsMapping(object.getKey(), object.getValue());
        }

        @Override
        public Iterator<Map.Entry<K, V>> iterator() {
            return new EntryIterator();
        }

        @Override
        public boolean remove(Object object) {
            if (!(object instanceof Map.Entry)) {
                return false;
            }
            object = (Map.Entry)object;
            return IdentityHashMap.this.removeMapping(object.getKey(), object.getValue());
        }

        @Override
        public boolean removeAll(Collection<?> collection) {
            Objects.requireNonNull(collection);
            boolean bl = false;
            Iterator<Map.Entry<K, V>> iterator = this.iterator();
            while (iterator.hasNext()) {
                if (!collection.contains(iterator.next())) continue;
                iterator.remove();
                bl = true;
            }
            return bl;
        }

        @Override
        public int size() {
            return IdentityHashMap.this.size;
        }

        @Override
        public Spliterator<Map.Entry<K, V>> spliterator() {
            return new EntrySpliterator(IdentityHashMap.this, 0, -1, 0, 0);
        }

        @Override
        public Object[] toArray() {
            return this.toArray(new Object[0]);
        }

        @Override
        public <T> T[] toArray(T[] object) {
            int n = IdentityHashMap.this.modCount;
            int n2 = this.size();
            Object[] arrobject = object;
            if (((T[])object).length < n2) {
                arrobject = (Object[])Array.newInstance(object.getClass().getComponentType(), n2);
            }
            Object[] arrobject2 = IdentityHashMap.this.table;
            int n3 = 0;
            for (int i = 0; i < arrobject2.length; i += 2) {
                object = arrobject2[i];
                int n4 = n3;
                if (object != null) {
                    if (n3 < n2) {
                        arrobject[n3] = new AbstractMap.SimpleEntry<Object, Object>(IdentityHashMap.unmaskNull(object), arrobject2[i + 1]);
                        n4 = n3 + 1;
                    } else {
                        throw new ConcurrentModificationException();
                    }
                }
                n3 = n4;
            }
            if (n3 >= n2 && n == IdentityHashMap.this.modCount) {
                if (n3 < arrobject.length) {
                    arrobject[n3] = null;
                }
                return arrobject;
            }
            throw new ConcurrentModificationException();
        }
    }

    static final class EntrySpliterator<K, V>
    extends IdentityHashMapSpliterator<K, V>
    implements Spliterator<Map.Entry<K, V>> {
        EntrySpliterator(IdentityHashMap<K, V> identityHashMap, int n, int n2, int n3, int n4) {
            super(identityHashMap, n, n2, n3, n4);
        }

        @Override
        public int characteristics() {
            int n = this.fence >= 0 && this.est != this.map.size ? 0 : 64;
            return n | 1;
        }

        @Override
        public void forEachRemaining(Consumer<? super Map.Entry<K, V>> consumer) {
            if (consumer != null) {
                Object[] arrobject;
                IdentityHashMap identityHashMap = this.map;
                if (identityHashMap != null && (arrobject = identityHashMap.table) != null) {
                    int n;
                    int n2 = n = this.index;
                    if (n >= 0) {
                        this.index = n = this.getFence();
                        if (n <= arrobject.length) {
                            while (n2 < n) {
                                Object object = arrobject[n2];
                                if (object != null) {
                                    consumer.accept(new AbstractMap.SimpleImmutableEntry<Object, Object>(IdentityHashMap.unmaskNull(object), arrobject[n2 + 1]));
                                }
                                n2 += 2;
                            }
                            if (identityHashMap.modCount == this.expectedModCount) {
                                return;
                            }
                        }
                    }
                }
                throw new ConcurrentModificationException();
            }
            throw new NullPointerException();
        }

        @Override
        public boolean tryAdvance(Consumer<? super Map.Entry<K, V>> consumer) {
            if (consumer != null) {
                Object[] arrobject = this.map.table;
                int n = this.getFence();
                while (this.index < n) {
                    Object object = arrobject[this.index];
                    Object object2 = arrobject[this.index + 1];
                    this.index += 2;
                    if (object == null) continue;
                    consumer.accept(new AbstractMap.SimpleImmutableEntry<Object, Object>(IdentityHashMap.unmaskNull(object), object2));
                    if (this.map.modCount == this.expectedModCount) {
                        return true;
                    }
                    throw new ConcurrentModificationException();
                }
                return false;
            }
            throw new NullPointerException();
        }

        public EntrySpliterator<K, V> trySplit() {
            Object object;
            int n = this.getFence();
            int n2 = this.index;
            if (n2 >= (n = n2 + n >>> 1 & -2)) {
                object = null;
            } else {
                int n3;
                object = this.map;
                this.index = n;
                this.est = n3 = this.est >>> 1;
                object = new EntrySpliterator<K, V>((IdentityHashMap<K, V>)object, n2, n, n3, this.expectedModCount);
            }
            return object;
        }
    }

    private abstract class IdentityHashMapIterator<T>
    implements Iterator<T> {
        int expectedModCount;
        int index;
        boolean indexValid;
        int lastReturnedIndex;
        Object[] traversalTable;

        private IdentityHashMapIterator() {
            int n = IdentityHashMap.this.size != 0 ? 0 : IdentityHashMap.this.table.length;
            this.index = n;
            this.expectedModCount = IdentityHashMap.this.modCount;
            this.lastReturnedIndex = -1;
            this.traversalTable = IdentityHashMap.this.table;
        }

        @Override
        public boolean hasNext() {
            Object[] arrobject = this.traversalTable;
            for (int i = this.index; i < arrobject.length; i += 2) {
                if (arrobject[i] == null) continue;
                this.index = i;
                this.indexValid = true;
                return true;
            }
            this.index = arrobject.length;
            return false;
        }

        protected int nextIndex() {
            if (IdentityHashMap.this.modCount == this.expectedModCount) {
                int n;
                if (!this.indexValid && !this.hasNext()) {
                    throw new NoSuchElementException();
                }
                this.indexValid = false;
                this.lastReturnedIndex = n = this.index;
                this.index = n + 2;
                return this.lastReturnedIndex;
            }
            throw new ConcurrentModificationException();
        }

        @Override
        public void remove() {
            block6 : {
                block7 : {
                    Object object;
                    int n;
                    if (this.lastReturnedIndex == -1) break block6;
                    if (IdentityHashMap.this.modCount != this.expectedModCount) break block7;
                    Object[] arrobject = IdentityHashMap.this;
                    arrobject.modCount = n = arrobject.modCount + 1;
                    this.expectedModCount = n;
                    int n2 = this.lastReturnedIndex;
                    this.lastReturnedIndex = -1;
                    this.index = n2;
                    this.indexValid = false;
                    arrobject = this.traversalTable;
                    int n3 = arrobject.length;
                    int n4 = n2;
                    Object[] arrobject2 = arrobject[n4];
                    arrobject[n4] = null;
                    arrobject[n4 + 1] = null;
                    if (arrobject != IdentityHashMap.this.table) {
                        IdentityHashMap.this.remove(arrobject2);
                        this.expectedModCount = IdentityHashMap.this.modCount;
                        return;
                    }
                    arrobject2 = IdentityHashMap.this;
                    --arrobject2.size;
                    n = IdentityHashMap.nextKeyIndex(n4, n3);
                    while ((object = arrobject[n]) != null) {
                        int n5;
                        block9 : {
                            block8 : {
                                int n6 = IdentityHashMap.hash(object, n3);
                                if (n < n6 && (n6 <= n4 || n4 <= n)) break block8;
                                n5 = n4;
                                if (n6 > n4) break block9;
                                n5 = n4;
                                if (n4 > n) break block9;
                            }
                            if (n < n2 && n4 >= n2 && this.traversalTable == IdentityHashMap.this.table) {
                                n5 = n3 - n2;
                                arrobject2 = new Object[n5];
                                System.arraycopy(arrobject, n2, arrobject2, 0, n5);
                                this.traversalTable = arrobject2;
                                this.index = 0;
                            }
                            arrobject[n4] = object;
                            arrobject[n4 + 1] = arrobject[n + 1];
                            arrobject[n] = null;
                            arrobject[n + 1] = null;
                            n5 = n;
                        }
                        n = IdentityHashMap.nextKeyIndex(n, n3);
                        n4 = n5;
                    }
                    return;
                }
                throw new ConcurrentModificationException();
            }
            throw new IllegalStateException();
        }
    }

    static class IdentityHashMapSpliterator<K, V> {
        int est;
        int expectedModCount;
        int fence;
        int index;
        final IdentityHashMap<K, V> map;

        IdentityHashMapSpliterator(IdentityHashMap<K, V> identityHashMap, int n, int n2, int n3, int n4) {
            this.map = identityHashMap;
            this.index = n;
            this.fence = n2;
            this.est = n3;
            this.expectedModCount = n4;
        }

        public final long estimateSize() {
            this.getFence();
            return this.est;
        }

        final int getFence() {
            int n;
            int n2 = n = this.fence;
            if (n < 0) {
                this.est = this.map.size;
                this.expectedModCount = this.map.modCount;
                this.fence = n2 = this.map.table.length;
            }
            return n2;
        }
    }

    private class KeyIterator
    extends java.util.IdentityHashMap.IdentityHashMapIterator<K> {
        private KeyIterator() {
        }

        public K next() {
            return (K)IdentityHashMap.unmaskNull(this.traversalTable[this.nextIndex()]);
        }
    }

    private class KeySet
    extends AbstractSet<K> {
        private KeySet() {
        }

        @Override
        public void clear() {
            IdentityHashMap.this.clear();
        }

        @Override
        public boolean contains(Object object) {
            return IdentityHashMap.this.containsKey(object);
        }

        @Override
        public int hashCode() {
            int n = 0;
            Iterator<K> iterator = this.iterator();
            while (iterator.hasNext()) {
                n += System.identityHashCode(iterator.next());
            }
            return n;
        }

        @Override
        public Iterator<K> iterator() {
            return new KeyIterator();
        }

        @Override
        public boolean remove(Object object) {
            int n = IdentityHashMap.this.size;
            IdentityHashMap.this.remove(object);
            boolean bl = IdentityHashMap.this.size != n;
            return bl;
        }

        @Override
        public boolean removeAll(Collection<?> collection) {
            Objects.requireNonNull(collection);
            boolean bl = false;
            Iterator<K> iterator = this.iterator();
            while (iterator.hasNext()) {
                if (!collection.contains(iterator.next())) continue;
                iterator.remove();
                bl = true;
            }
            return bl;
        }

        @Override
        public int size() {
            return IdentityHashMap.this.size;
        }

        @Override
        public Spliterator<K> spliterator() {
            return new KeySpliterator(IdentityHashMap.this, 0, -1, 0, 0);
        }

        @Override
        public Object[] toArray() {
            return this.toArray(new Object[0]);
        }

        @Override
        public <T> T[] toArray(T[] arrobject) {
            int n = IdentityHashMap.this.modCount;
            int n2 = this.size();
            Object[] arrobject2 = arrobject;
            if (arrobject.length < n2) {
                arrobject2 = (Object[])Array.newInstance(arrobject.getClass().getComponentType(), n2);
            }
            arrobject = IdentityHashMap.this.table;
            int n3 = 0;
            for (int i = 0; i < arrobject.length; i += 2) {
                T t = arrobject[i];
                int n4 = n3;
                if (t != null) {
                    if (n3 < n2) {
                        arrobject2[n3] = IdentityHashMap.unmaskNull(t);
                        n4 = n3 + 1;
                    } else {
                        throw new ConcurrentModificationException();
                    }
                }
                n3 = n4;
            }
            if (n3 >= n2 && n == IdentityHashMap.this.modCount) {
                if (n3 < arrobject2.length) {
                    arrobject2[n3] = null;
                }
                return arrobject2;
            }
            throw new ConcurrentModificationException();
        }
    }

    static final class KeySpliterator<K, V>
    extends IdentityHashMapSpliterator<K, V>
    implements Spliterator<K> {
        KeySpliterator(IdentityHashMap<K, V> identityHashMap, int n, int n2, int n3, int n4) {
            super(identityHashMap, n, n2, n3, n4);
        }

        @Override
        public int characteristics() {
            int n = this.fence >= 0 && this.est != this.map.size ? 0 : 64;
            return n | 1;
        }

        @Override
        public void forEachRemaining(Consumer<? super K> consumer) {
            if (consumer != null) {
                Object[] arrobject;
                IdentityHashMap identityHashMap = this.map;
                if (identityHashMap != null && (arrobject = identityHashMap.table) != null) {
                    int n;
                    int n2 = n = this.index;
                    if (n >= 0) {
                        this.index = n = this.getFence();
                        if (n <= arrobject.length) {
                            while (n2 < n) {
                                Object object = arrobject[n2];
                                if (object != null) {
                                    consumer.accept(IdentityHashMap.unmaskNull(object));
                                }
                                n2 += 2;
                            }
                            if (identityHashMap.modCount == this.expectedModCount) {
                                return;
                            }
                        }
                    }
                }
                throw new ConcurrentModificationException();
            }
            throw new NullPointerException();
        }

        @Override
        public boolean tryAdvance(Consumer<? super K> consumer) {
            if (consumer != null) {
                Object[] arrobject = this.map.table;
                int n = this.getFence();
                while (this.index < n) {
                    Object object = arrobject[this.index];
                    this.index += 2;
                    if (object == null) continue;
                    consumer.accept(IdentityHashMap.unmaskNull(object));
                    if (this.map.modCount == this.expectedModCount) {
                        return true;
                    }
                    throw new ConcurrentModificationException();
                }
                return false;
            }
            throw new NullPointerException();
        }

        public KeySpliterator<K, V> trySplit() {
            Object object;
            int n = this.getFence();
            int n2 = this.index;
            if (n2 >= (n = n2 + n >>> 1 & -2)) {
                object = null;
            } else {
                int n3;
                object = this.map;
                this.index = n;
                this.est = n3 = this.est >>> 1;
                object = new KeySpliterator<K, V>((IdentityHashMap<K, V>)object, n2, n, n3, this.expectedModCount);
            }
            return object;
        }
    }

    private class ValueIterator
    extends java.util.IdentityHashMap.IdentityHashMapIterator<V> {
        private ValueIterator() {
        }

        public V next() {
            return (V)this.traversalTable[this.nextIndex() + 1];
        }
    }

    static final class ValueSpliterator<K, V>
    extends IdentityHashMapSpliterator<K, V>
    implements Spliterator<V> {
        ValueSpliterator(IdentityHashMap<K, V> identityHashMap, int n, int n2, int n3, int n4) {
            super(identityHashMap, n, n2, n3, n4);
        }

        @Override
        public int characteristics() {
            int n = this.fence >= 0 && this.est != this.map.size ? 0 : 64;
            return n;
        }

        @Override
        public void forEachRemaining(Consumer<? super V> consumer) {
            if (consumer != null) {
                Object[] arrobject;
                IdentityHashMap identityHashMap = this.map;
                if (identityHashMap != null && (arrobject = identityHashMap.table) != null) {
                    int n;
                    int n2 = n = this.index;
                    if (n >= 0) {
                        this.index = n = this.getFence();
                        if (n <= arrobject.length) {
                            while (n2 < n) {
                                if (arrobject[n2] != null) {
                                    consumer.accept(arrobject[n2 + 1]);
                                }
                                n2 += 2;
                            }
                            if (identityHashMap.modCount == this.expectedModCount) {
                                return;
                            }
                        }
                    }
                }
                throw new ConcurrentModificationException();
            }
            throw new NullPointerException();
        }

        @Override
        public boolean tryAdvance(Consumer<? super V> consumer) {
            if (consumer != null) {
                Object[] arrobject = this.map.table;
                int n = this.getFence();
                while (this.index < n) {
                    Object object = arrobject[this.index];
                    Object object2 = arrobject[this.index + 1];
                    this.index += 2;
                    if (object == null) continue;
                    consumer.accept(object2);
                    if (this.map.modCount == this.expectedModCount) {
                        return true;
                    }
                    throw new ConcurrentModificationException();
                }
                return false;
            }
            throw new NullPointerException();
        }

        public ValueSpliterator<K, V> trySplit() {
            Object object;
            int n = this.index;
            int n2 = this.getFence();
            int n3 = n + n2 >>> 1 & -2;
            if (n >= n3) {
                object = null;
            } else {
                object = this.map;
                this.index = n3;
                this.est = n2 = this.est >>> 1;
                object = new ValueSpliterator<K, V>((IdentityHashMap<K, V>)object, n, n3, n2, this.expectedModCount);
            }
            return object;
        }
    }

    private class Values
    extends AbstractCollection<V> {
        private Values() {
        }

        @Override
        public void clear() {
            IdentityHashMap.this.clear();
        }

        @Override
        public boolean contains(Object object) {
            return IdentityHashMap.this.containsValue(object);
        }

        @Override
        public Iterator<V> iterator() {
            return new ValueIterator();
        }

        @Override
        public boolean remove(Object object) {
            Iterator<V> iterator = this.iterator();
            while (iterator.hasNext()) {
                if (iterator.next() != object) continue;
                iterator.remove();
                return true;
            }
            return false;
        }

        @Override
        public int size() {
            return IdentityHashMap.this.size;
        }

        @Override
        public Spliterator<V> spliterator() {
            return new ValueSpliterator(IdentityHashMap.this, 0, -1, 0, 0);
        }

        @Override
        public Object[] toArray() {
            return this.toArray(new Object[0]);
        }

        @Override
        public <T> T[] toArray(T[] arrobject) {
            int n = IdentityHashMap.this.modCount;
            int n2 = this.size();
            Object[] arrobject2 = arrobject;
            if (arrobject.length < n2) {
                arrobject2 = (Object[])Array.newInstance(arrobject.getClass().getComponentType(), n2);
            }
            arrobject = IdentityHashMap.this.table;
            int n3 = 0;
            for (int i = 0; i < arrobject.length; i += 2) {
                int n4 = n3;
                if (arrobject[i] != null) {
                    if (n3 < n2) {
                        arrobject2[n3] = arrobject[i + 1];
                        n4 = n3 + 1;
                    } else {
                        throw new ConcurrentModificationException();
                    }
                }
                n3 = n4;
            }
            if (n3 >= n2 && n == IdentityHashMap.this.modCount) {
                if (n3 < arrobject2.length) {
                    arrobject2[n3] = null;
                }
                return arrobject2;
            }
            throw new ConcurrentModificationException();
        }
    }

}

