/*
 * Decompiled with CFR 0.145.
 */
package com.android.framework.protobuf;

import com.android.framework.protobuf.FieldSet;
import java.util.AbstractMap;
import java.util.AbstractSet;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

class SmallSortedMap<K extends Comparable<K>, V>
extends AbstractMap<K, V> {
    private List<SmallSortedMap<K, V>> entryList;
    private boolean isImmutable;
    private volatile SmallSortedMap<K, V> lazyEntrySet;
    private final int maxArraySize;
    private Map<K, V> overflowEntries;

    private SmallSortedMap(int n) {
        this.maxArraySize = n;
        this.entryList = Collections.emptyList();
        this.overflowEntries = Collections.emptyMap();
    }

    private int binarySearchInArray(K k) {
        int n = 0;
        int n2 = this.entryList.size() - 1;
        int n3 = n;
        int n4 = n2;
        if (n2 >= 0) {
            int n5 = k.compareTo((Object)((Entry)((Object)this.entryList.get(n2))).getKey());
            if (n5 > 0) {
                return -(n2 + 2);
            }
            n3 = n;
            n4 = n2;
            if (n5 == 0) {
                return n2;
            }
        }
        while (n3 <= n4) {
            n2 = (n3 + n4) / 2;
            n = k.compareTo((Object)((Entry)((Object)this.entryList.get(n2))).getKey());
            if (n < 0) {
                n4 = n2 - 1;
                continue;
            }
            if (n > 0) {
                n3 = n2 + 1;
                continue;
            }
            return n2;
        }
        return -(n3 + 1);
    }

    private void checkMutable() {
        if (!this.isImmutable) {
            return;
        }
        throw new UnsupportedOperationException();
    }

    private void ensureEntryArrayMutable() {
        this.checkMutable();
        if (this.entryList.isEmpty() && !(this.entryList instanceof ArrayList)) {
            this.entryList = new ArrayList<SmallSortedMap<K, V>>(this.maxArraySize);
        }
    }

    private SortedMap<K, V> getOverflowEntriesMutable() {
        this.checkMutable();
        if (this.overflowEntries.isEmpty() && !(this.overflowEntries instanceof TreeMap)) {
            this.overflowEntries = new TreeMap();
        }
        return (SortedMap)this.overflowEntries;
    }

    static <FieldDescriptorType extends FieldSet.FieldDescriptorLite<FieldDescriptorType>> SmallSortedMap<FieldDescriptorType, Object> newFieldMap(int n) {
        return new SmallSortedMap<FieldDescriptorType, Object>(n){

            @Override
            public void makeImmutable() {
                if (!this.isImmutable()) {
                    for (int i = 0; i < this.getNumArrayEntries(); ++i) {
                        Map.Entry entry = this.getArrayEntryAt(i);
                        if (!((FieldSet.FieldDescriptorLite)entry.getKey()).isRepeated()) continue;
                        entry.setValue(Collections.unmodifiableList((List)entry.getValue()));
                    }
                    for (Map.Entry entry : this.getOverflowEntries()) {
                        if (!((FieldSet.FieldDescriptorLite)entry.getKey()).isRepeated()) continue;
                        entry.setValue(Collections.unmodifiableList((List)entry.getValue()));
                    }
                }
                super.makeImmutable();
            }
        };
    }

    static <K extends Comparable<K>, V> SmallSortedMap<K, V> newInstanceForTest(int n) {
        return new SmallSortedMap<K, V>(n);
    }

    private V removeArrayEntryAt(int n) {
        this.checkMutable();
        Object v = ((Entry)((Object)this.entryList.remove(n))).getValue();
        if (!this.overflowEntries.isEmpty()) {
            Iterator<Map.Entry<K, V>> iterator = this.getOverflowEntriesMutable().entrySet().iterator();
            this.entryList.add((SmallSortedMap<K, V>)((Object)new Entry(iterator.next())));
            iterator.remove();
        }
        return v;
    }

    @Override
    public void clear() {
        this.checkMutable();
        if (!this.entryList.isEmpty()) {
            this.entryList.clear();
        }
        if (!this.overflowEntries.isEmpty()) {
            this.overflowEntries.clear();
        }
    }

    @Override
    public boolean containsKey(Object object) {
        boolean bl = this.binarySearchInArray(object = (Comparable)object) >= 0 || this.overflowEntries.containsKey(object);
        return bl;
    }

    @Override
    public Set<Map.Entry<K, V>> entrySet() {
        if (this.lazyEntrySet == null) {
            this.lazyEntrySet = new EntrySet();
        }
        return this.lazyEntrySet;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof SmallSortedMap)) {
            return super.equals(object);
        }
        object = (SmallSortedMap)object;
        int n = this.size();
        if (n != ((SmallSortedMap)object).size()) {
            return false;
        }
        int n2 = this.getNumArrayEntries();
        if (n2 != ((SmallSortedMap)object).getNumArrayEntries()) {
            return this.entrySet().equals(((SmallSortedMap)object).entrySet());
        }
        for (int i = 0; i < n2; ++i) {
            if (this.getArrayEntryAt(i).equals(((SmallSortedMap)object).getArrayEntryAt(i))) continue;
            return false;
        }
        if (n2 != n) {
            return this.overflowEntries.equals(((SmallSortedMap)object).overflowEntries);
        }
        return true;
    }

    @Override
    public V get(Object object) {
        int n = this.binarySearchInArray(object = (Comparable)object);
        if (n >= 0) {
            return ((Entry)((Object)this.entryList.get(n))).getValue();
        }
        return this.overflowEntries.get(object);
    }

    public Map.Entry<K, V> getArrayEntryAt(int n) {
        return (Map.Entry)((Object)this.entryList.get(n));
    }

    public int getNumArrayEntries() {
        return this.entryList.size();
    }

    public int getNumOverflowEntries() {
        return this.overflowEntries.size();
    }

    public Iterable<Map.Entry<K, V>> getOverflowEntries() {
        Iterable<Map.Entry<K, V>> iterable = this.overflowEntries.isEmpty() ? EmptySet.iterable() : this.overflowEntries.entrySet();
        return iterable;
    }

    @Override
    public int hashCode() {
        int n;
        int n2 = 0;
        int n3 = this.getNumArrayEntries();
        for (n = 0; n < n3; ++n) {
            n2 += ((Entry)((Object)this.entryList.get(n))).hashCode();
        }
        n = n2;
        if (this.getNumOverflowEntries() > 0) {
            n = n2 + this.overflowEntries.hashCode();
        }
        return n;
    }

    public boolean isImmutable() {
        return this.isImmutable;
    }

    public void makeImmutable() {
        if (!this.isImmutable) {
            Map map = this.overflowEntries.isEmpty() ? Collections.emptyMap() : Collections.unmodifiableMap(this.overflowEntries);
            this.overflowEntries = map;
            this.isImmutable = true;
        }
    }

    @Override
    public V put(K k, V v) {
        this.checkMutable();
        int n = this.binarySearchInArray(k);
        if (n >= 0) {
            return ((Entry)((Object)this.entryList.get(n))).setValue(v);
        }
        this.ensureEntryArrayMutable();
        int n2 = -(n + 1);
        if (n2 >= this.maxArraySize) {
            return this.getOverflowEntriesMutable().put(k, v);
        }
        int n3 = this.entryList.size();
        if (n3 == (n = this.maxArraySize)) {
            Entry entry = (Entry)((Object)this.entryList.remove(n - 1));
            this.getOverflowEntriesMutable().put(entry.getKey(), entry.getValue());
        }
        this.entryList.add(n2, (SmallSortedMap<K, V>)((Object)new Entry(this, k, v)));
        return null;
    }

    @Override
    public V remove(Object object) {
        this.checkMutable();
        object = (Comparable)object;
        int n = this.binarySearchInArray(object);
        if (n >= 0) {
            return this.removeArrayEntryAt(n);
        }
        if (this.overflowEntries.isEmpty()) {
            return null;
        }
        return this.overflowEntries.remove(object);
    }

    @Override
    public int size() {
        return this.entryList.size() + this.overflowEntries.size();
    }

    private static class EmptySet {
        private static final Iterable<Object> ITERABLE;
        private static final Iterator<Object> ITERATOR;

        static {
            ITERATOR = new Iterator<Object>(){

                @Override
                public boolean hasNext() {
                    return false;
                }

                @Override
                public Object next() {
                    throw new NoSuchElementException();
                }

                @Override
                public void remove() {
                    throw new UnsupportedOperationException();
                }
            };
            ITERABLE = new Iterable<Object>(){

                @Override
                public Iterator<Object> iterator() {
                    return ITERATOR;
                }
            };
        }

        private EmptySet() {
        }

        static <T> Iterable<T> iterable() {
            return ITERABLE;
        }

    }

    private class Entry
    implements Map.Entry<K, V>,
    Comparable<SmallSortedMap<K, V>> {
        private final K key;
        private V value;

        Entry(K k, V v) {
            this.key = k;
            this.value = v;
        }

        Entry(Map.Entry<K, V> entry) {
            this(smallSortedMap, (Comparable)entry.getKey(), entry.getValue());
        }

        private boolean equals(Object object, Object object2) {
            boolean bl = object == null ? object2 == null : object.equals(object2);
            return bl;
        }

        @Override
        public int compareTo(SmallSortedMap<K, V> smallSortedMap) {
            return this.getKey().compareTo(((Entry)((Object)smallSortedMap)).getKey());
        }

        @Override
        public boolean equals(Object object) {
            boolean bl = true;
            if (object == this) {
                return true;
            }
            if (!(object instanceof Map.Entry)) {
                return false;
            }
            if (!this.equals(this.key, (object = (Map.Entry)object).getKey()) || !this.equals(this.value, object.getValue())) {
                bl = false;
            }
            return bl;
        }

        @Override
        public K getKey() {
            return this.key;
        }

        @Override
        public V getValue() {
            return this.value;
        }

        @Override
        public int hashCode() {
            Object object = this.key;
            int n = 0;
            int n2 = object == null ? 0 : object.hashCode();
            object = this.value;
            if (object != null) {
                n = object.hashCode();
            }
            return n2 ^ n;
        }

        @Override
        public V setValue(V v) {
            this$0.checkMutable();
            V v2 = this.value;
            this.value = v;
            return v2;
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(this.key);
            stringBuilder.append("=");
            stringBuilder.append(this.value);
            return stringBuilder.toString();
        }
    }

    private class EntryIterator
    implements Iterator<Map.Entry<K, V>> {
        private Iterator<Map.Entry<K, V>> lazyOverflowIterator;
        private boolean nextCalledBeforeRemove;
        private int pos = -1;

        private EntryIterator() {
        }

        private Iterator<Map.Entry<K, V>> getOverflowIterator() {
            if (this.lazyOverflowIterator == null) {
                this.lazyOverflowIterator = SmallSortedMap.this.overflowEntries.entrySet().iterator();
            }
            return this.lazyOverflowIterator;
        }

        @Override
        public boolean hasNext() {
            boolean bl;
            block0 : {
                int n = this.pos;
                bl = true;
                if (n + 1 < SmallSortedMap.this.entryList.size() || this.getOverflowIterator().hasNext()) break block0;
                bl = false;
            }
            return bl;
        }

        @Override
        public Map.Entry<K, V> next() {
            int n;
            this.nextCalledBeforeRemove = true;
            this.pos = n = this.pos + 1;
            if (n < SmallSortedMap.this.entryList.size()) {
                return (Map.Entry)SmallSortedMap.this.entryList.get(this.pos);
            }
            return this.getOverflowIterator().next();
        }

        @Override
        public void remove() {
            if (this.nextCalledBeforeRemove) {
                this.nextCalledBeforeRemove = false;
                SmallSortedMap.this.checkMutable();
                if (this.pos < SmallSortedMap.this.entryList.size()) {
                    SmallSortedMap smallSortedMap = SmallSortedMap.this;
                    int n = this.pos;
                    this.pos = n - 1;
                    smallSortedMap.removeArrayEntryAt(n);
                } else {
                    this.getOverflowIterator().remove();
                }
                return;
            }
            throw new IllegalStateException("remove() was called before next()");
        }
    }

    private class EntrySet
    extends AbstractSet<Map.Entry<K, V>> {
        private EntrySet() {
        }

        @Override
        public boolean add(Map.Entry<K, V> entry) {
            if (!this.contains(entry)) {
                SmallSortedMap.this.put((Comparable)entry.getKey(), entry.getValue());
                return true;
            }
            return false;
        }

        @Override
        public void clear() {
            SmallSortedMap.this.clear();
        }

        @Override
        public boolean contains(Object object) {
            Map.Entry entry = (Map.Entry)object;
            object = SmallSortedMap.this.get(entry.getKey());
            boolean bl = object == (entry = entry.getValue()) || object != null && object.equals(entry);
            return bl;
        }

        @Override
        public Iterator<Map.Entry<K, V>> iterator() {
            return new EntryIterator();
        }

        @Override
        public boolean remove(Object object) {
            if (this.contains(object = (Map.Entry)object)) {
                SmallSortedMap.this.remove(object.getKey());
                return true;
            }
            return false;
        }

        @Override
        public int size() {
            return SmallSortedMap.this.size();
        }
    }

}

