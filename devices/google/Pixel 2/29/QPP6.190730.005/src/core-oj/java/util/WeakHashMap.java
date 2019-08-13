/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  java.util.WeakHashMap.HashIterator
 */
package java.util;

import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.util.AbstractCollection;
import java.util.AbstractMap;
import java.util.AbstractSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Set;
import java.util.Spliterator;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;

public class WeakHashMap<K, V>
extends AbstractMap<K, V>
implements Map<K, V> {
    private static final int DEFAULT_INITIAL_CAPACITY = 16;
    private static final float DEFAULT_LOAD_FACTOR = 0.75f;
    private static final int MAXIMUM_CAPACITY = 1073741824;
    private static final Object NULL_KEY = new Object();
    private transient Set<Map.Entry<K, V>> entrySet;
    private final float loadFactor;
    int modCount;
    private final ReferenceQueue<Object> queue = new ReferenceQueue();
    private int size;
    Entry<K, V>[] table;
    private int threshold;

    public WeakHashMap() {
        this(16, 0.75f);
    }

    public WeakHashMap(int n) {
        this(n, 0.75f);
    }

    public WeakHashMap(int n, float f) {
        if (n >= 0) {
            int n2 = n;
            if (n > 1073741824) {
                n2 = 1073741824;
            }
            if (!(f <= 0.0f) && !Float.isNaN(f)) {
                for (n = 1; n < n2; n <<= 1) {
                }
                this.table = this.newTable(n);
                this.loadFactor = f;
                this.threshold = (int)((float)n * f);
                return;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Illegal Load factor: ");
            stringBuilder.append(f);
            throw new IllegalArgumentException(stringBuilder.toString());
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Illegal Initial Capacity: ");
        stringBuilder.append(n);
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    public WeakHashMap(Map<? extends K, ? extends V> map) {
        this(Math.max((int)((float)map.size() / 0.75f) + 1, 16), 0.75f);
        this.putAll(map);
    }

    private boolean containsNullValue() {
        Entry<K, V>[] arrentry = this.getTable();
        int n = arrentry.length;
        do {
            int n2 = n - 1;
            if (n <= 0) break;
            Entry<K, V> entry = arrentry[n2];
            while (entry != null) {
                if (entry.value == null) {
                    return true;
                }
                entry = entry.next;
            }
            n = n2;
        } while (true);
        return false;
    }

    private static boolean eq(Object object, Object object2) {
        boolean bl = object == object2 || object.equals(object2);
        return bl;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void expungeStaleEntries() {
        Reference<Object> reference;
        while ((reference = this.queue.poll()) != null) {
            ReferenceQueue<Object> referenceQueue = this.queue;
            synchronized (referenceQueue) {
                Entry entry = (Entry)reference;
                int n = WeakHashMap.indexFor(entry.hash, this.table.length);
                Reference reference2 = this.table[n];
                reference = reference2;
                while (reference != null) {
                    Entry entry2 = ((Entry)reference).next;
                    if (reference == entry) {
                        if (reference2 == entry) {
                            this.table[n] = entry2;
                        } else {
                            reference2.next = entry2;
                        }
                        entry.value = null;
                        --this.size;
                        break;
                    }
                    reference2 = reference;
                    reference = entry2;
                }
            }
        }
        return;
    }

    private Entry<K, V>[] getTable() {
        this.expungeStaleEntries();
        return this.table;
    }

    private static int indexFor(int n, int n2) {
        return n2 - 1 & n;
    }

    private static Object maskNull(Object object) {
        block0 : {
            if (object != null) break block0;
            object = NULL_KEY;
        }
        return object;
    }

    private Entry<K, V>[] newTable(int n) {
        return new Entry[n];
    }

    private void transfer(Entry<K, V>[] arrentry, Entry<K, V>[] arrentry2) {
        for (int i = 0; i < arrentry.length; ++i) {
            Entry<K, V> entry = arrentry[i];
            arrentry[i] = null;
            while (entry != null) {
                Entry entry2 = entry.next;
                if (entry.get() == null) {
                    entry.next = null;
                    entry.value = null;
                    --this.size;
                } else {
                    int n = WeakHashMap.indexFor(entry.hash, arrentry2.length);
                    entry.next = arrentry2[n];
                    arrentry2[n] = entry;
                }
                entry = entry2;
            }
        }
    }

    static Object unmaskNull(Object object) {
        block0 : {
            if (object != NULL_KEY) break block0;
            object = null;
        }
        return object;
    }

    @Override
    public void clear() {
        while (this.queue.poll() != null) {
        }
        ++this.modCount;
        Arrays.fill(this.table, null);
        this.size = 0;
        while (this.queue.poll() != null) {
        }
    }

    @Override
    public boolean containsKey(Object object) {
        boolean bl = this.getEntry(object) != null;
        return bl;
    }

    @Override
    public boolean containsValue(Object object) {
        if (object == null) {
            return this.containsNullValue();
        }
        Entry<K, V>[] arrentry = this.getTable();
        int n = arrentry.length;
        do {
            int n2 = n - 1;
            if (n <= 0) break;
            Entry<K, V> entry = arrentry[n2];
            while (entry != null) {
                if (object.equals(entry.value)) {
                    return true;
                }
                entry = entry.next;
            }
            n = n2;
        } while (true);
        return false;
    }

    @Override
    public Set<Map.Entry<K, V>> entrySet() {
        EntrySet entrySet = this.entrySet;
        if (entrySet == null) {
            this.entrySet = entrySet = new EntrySet();
        }
        return entrySet;
    }

    @Override
    public void forEach(BiConsumer<? super K, ? super V> biConsumer) {
        Objects.requireNonNull(biConsumer);
        int n = this.modCount;
        for (Entry<K, V> entry : this.getTable()) {
            while (entry != null) {
                Object t = entry.get();
                if (t != null) {
                    biConsumer.accept(WeakHashMap.unmaskNull(t), entry.value);
                }
                entry = entry.next;
                if (n == this.modCount) continue;
                throw new ConcurrentModificationException();
            }
        }
    }

    @Override
    public V get(Object object) {
        Object object2 = WeakHashMap.maskNull(object);
        int n = this.hash(object2);
        object = this.getTable();
        object = object[WeakHashMap.indexFor(n, ((Entry<K, V>[])object).length)];
        while (object != null) {
            if (((Entry)object).hash == n && WeakHashMap.eq(object2, ((Reference)object).get())) {
                return ((Entry)object).value;
            }
            object = ((Entry)object).next;
        }
        return null;
    }

    Entry<K, V> getEntry(Object object) {
        Object object2 = WeakHashMap.maskNull(object);
        int n = this.hash(object2);
        object = this.getTable();
        object = object[WeakHashMap.indexFor(n, ((Entry<K, V>[])object).length)];
        while (!(object == null || ((Entry)object).hash == n && WeakHashMap.eq(object2, ((Reference)object).get()))) {
            object = ((Entry)object).next;
        }
        return object;
    }

    final int hash(Object object) {
        int n = object.hashCode();
        n ^= n >>> 20 ^ n >>> 12;
        return n >>> 7 ^ n ^ n >>> 4;
    }

    @Override
    public boolean isEmpty() {
        boolean bl = this.size() == 0;
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
        Object object2 = WeakHashMap.maskNull(object);
        int n = this.hash(object2);
        Object object3 = this.getTable();
        int n2 = WeakHashMap.indexFor(n, ((Entry<K, V>[])object3).length);
        object = object3[n2];
        while (object != null) {
            if (n == ((Entry)object).hash && WeakHashMap.eq(object2, ((Reference)object).get())) {
                object3 = ((Entry)object).value;
                if (v != object3) {
                    ((Entry)object).value = v;
                }
                return (V)object3;
            }
            object = ((Entry)object).next;
        }
        ++this.modCount;
        object = object3[n2];
        object3[n2] = new Entry(object2, v, this.queue, n, object);
        this.size = n2 = this.size + 1;
        if (n2 >= this.threshold) {
            this.resize(((Entry<K, V>[])object3).length * 2);
        }
        return null;
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> object) {
        int n = object.size();
        if (n == 0) {
            return;
        }
        if (n > this.threshold) {
            int n2;
            n = n2 = (int)((float)n / this.loadFactor + 1.0f);
            if (n2 > 1073741824) {
                n = 1073741824;
            }
            for (n2 = this.table.length; n2 < n; n2 <<= 1) {
            }
            if (n2 > this.table.length) {
                this.resize(n2);
            }
        }
        for (Map.Entry entry : object.entrySet()) {
            this.put(entry.getKey(), entry.getValue());
        }
    }

    @Override
    public V remove(Object entry) {
        Entry<K, V> entry2;
        Object object = WeakHashMap.maskNull(entry);
        int n = this.hash(object);
        Entry<K, V>[] arrentry = this.getTable();
        int n2 = WeakHashMap.indexFor(n, arrentry.length);
        entry = entry2 = arrentry[n2];
        while (entry != null) {
            Entry entry3 = entry.next;
            if (n == entry.hash && WeakHashMap.eq(object, entry.get())) {
                ++this.modCount;
                --this.size;
                if (entry2 == entry) {
                    arrentry[n2] = entry3;
                } else {
                    entry2.next = entry3;
                }
                return entry.value;
            }
            entry2 = entry;
            entry = entry3;
        }
        return null;
    }

    boolean removeMapping(Object entry) {
        Entry<K, V> entry2;
        if (!(entry instanceof Map.Entry)) {
            return false;
        }
        Entry<K, V>[] arrentry = this.getTable();
        Map.Entry entry3 = entry;
        int n = this.hash(WeakHashMap.maskNull(entry3.getKey()));
        int n2 = WeakHashMap.indexFor(n, arrentry.length);
        entry = entry2 = arrentry[n2];
        while (entry != null) {
            Entry entry4 = entry.next;
            if (n == entry.hash && entry.equals(entry3)) {
                ++this.modCount;
                --this.size;
                if (entry2 == entry) {
                    arrentry[n2] = entry4;
                } else {
                    entry2.next = entry4;
                }
                return true;
            }
            entry2 = entry;
            entry = entry4;
        }
        return false;
    }

    @Override
    public void replaceAll(BiFunction<? super K, ? super V, ? extends V> biFunction) {
        Objects.requireNonNull(biFunction);
        int n = this.modCount;
        for (Entry<K, V> entry : this.getTable()) {
            while (entry != null) {
                Object t = entry.get();
                if (t != null) {
                    entry.value = biFunction.apply(WeakHashMap.unmaskNull(t), entry.value);
                }
                entry = entry.next;
                if (n == this.modCount) continue;
                throw new ConcurrentModificationException();
            }
        }
    }

    void resize(int n) {
        Entry<K, V>[] arrentry = this.getTable();
        if (arrentry.length == 1073741824) {
            this.threshold = Integer.MAX_VALUE;
            return;
        }
        Entry<K, V>[] arrentry2 = this.newTable(n);
        this.transfer(arrentry, arrentry2);
        this.table = arrentry2;
        if (this.size >= this.threshold / 2) {
            this.threshold = (int)((float)n * this.loadFactor);
        } else {
            this.expungeStaleEntries();
            this.transfer(arrentry2, arrentry);
            this.table = arrentry;
        }
    }

    @Override
    public int size() {
        if (this.size == 0) {
            return 0;
        }
        this.expungeStaleEntries();
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

    private static class Entry<K, V>
    extends WeakReference<Object>
    implements Map.Entry<K, V> {
        final int hash;
        Entry<K, V> next;
        V value;

        Entry(Object object, V v, ReferenceQueue<Object> referenceQueue, int n, Entry<K, V> entry) {
            super(object, referenceQueue);
            this.value = v;
            this.hash = n;
            this.next = entry;
        }

        @Override
        public boolean equals(Object object) {
            Object k;
            if (!(object instanceof Map.Entry)) {
                return false;
            }
            object = (Map.Entry)object;
            Object object2 = this.getKey();
            return (object2 == (k = object.getKey()) || object2 != null && object2.equals(k)) && ((object2 = this.getValue()) == (object = object.getValue()) || object2 != null && object2.equals(object));
            {
            }
        }

        @Override
        public K getKey() {
            return (K)WeakHashMap.unmaskNull(this.get());
        }

        @Override
        public V getValue() {
            return this.value;
        }

        @Override
        public int hashCode() {
            K k = this.getKey();
            V v = this.getValue();
            return Objects.hashCode(k) ^ Objects.hashCode(v);
        }

        @Override
        public V setValue(V v) {
            V v2 = this.value;
            this.value = v;
            return v2;
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(this.getKey());
            stringBuilder.append("=");
            stringBuilder.append(this.getValue());
            return stringBuilder.toString();
        }
    }

    private class EntryIterator
    extends java.util.WeakHashMap.HashIterator<Map.Entry<K, V>> {
        private EntryIterator() {
        }

        public Map.Entry<K, V> next() {
            return this.nextEntry();
        }
    }

    private class EntrySet
    extends AbstractSet<Map.Entry<K, V>> {
        private EntrySet() {
        }

        private List<Map.Entry<K, V>> deepCopy() {
            ArrayList<Map.Entry<K, V>> arrayList = new ArrayList<Map.Entry<K, V>>(this.size());
            Iterator<Map.Entry<K, V>> iterator = this.iterator();
            while (iterator.hasNext()) {
                arrayList.add(new AbstractMap.SimpleEntry<K, V>(iterator.next()));
            }
            return arrayList;
        }

        @Override
        public void clear() {
            WeakHashMap.this.clear();
        }

        @Override
        public boolean contains(Object entry) {
            boolean bl = entry instanceof Map.Entry;
            boolean bl2 = false;
            if (!bl) {
                return false;
            }
            Map.Entry entry2 = entry;
            entry = WeakHashMap.this.getEntry(entry2.getKey());
            bl = bl2;
            if (entry != null) {
                bl = bl2;
                if (entry.equals(entry2)) {
                    bl = true;
                }
            }
            return bl;
        }

        @Override
        public Iterator<Map.Entry<K, V>> iterator() {
            return new EntryIterator();
        }

        @Override
        public boolean remove(Object object) {
            return WeakHashMap.this.removeMapping(object);
        }

        @Override
        public int size() {
            return WeakHashMap.this.size();
        }

        @Override
        public Spliterator<Map.Entry<K, V>> spliterator() {
            return new EntrySpliterator(WeakHashMap.this, 0, -1, 0, 0);
        }

        @Override
        public Object[] toArray() {
            return this.deepCopy().toArray();
        }

        @Override
        public <T> T[] toArray(T[] arrT) {
            return this.deepCopy().toArray(arrT);
        }
    }

    static final class EntrySpliterator<K, V>
    extends WeakHashMapSpliterator<K, V>
    implements Spliterator<Map.Entry<K, V>> {
        EntrySpliterator(WeakHashMap<K, V> weakHashMap, int n, int n2, int n3, int n4) {
            super(weakHashMap, n, n2, n3, n4);
        }

        @Override
        public int characteristics() {
            return 1;
        }

        /*
         * Unable to fully structure code
         * Enabled aggressive block sorting
         * Lifted jumps to return sites
         */
        @Override
        public void forEachRemaining(Consumer<? super Map.Entry<K, V>> var1_1) {
            block6 : {
                if (var1_1 == null) throw new NullPointerException();
                var2_2 = this.map;
                var3_3 = var2_2.table;
                var5_5 = var4_4 = this.fence;
                if (var4_4 < 0) {
                    this.expectedModCount = var4_4 = var2_2.modCount;
                    this.fence = var5_5 = var3_3.length;
                } else {
                    var4_4 = this.expectedModCount;
                }
                if (var3_3.length < var5_5) break block6;
                var7_7 = var6_6 = this.index;
                if (var6_6 < 0) break block6;
                this.index = var5_5;
                if (var7_7 >= var5_5 && this.current == null) break block6;
                var8_8 = this.current;
                this.current = null;
                do lbl-1000: // 3 sources:
                {
                    if (var8_8 == null) {
                        var9_9 = var3_3[var7_7];
                        var6_6 = var7_7 + 1;
                    } else {
                        var10_10 = var8_8.get();
                        var11_11 = var8_8.value;
                        var8_8 = var8_8.next;
                        var9_9 = var8_8;
                        var6_6 = var7_7;
                        if (var10_10 != null) {
                            var1_1.accept(new AbstractMap.SimpleImmutableEntry<Object, V>(WeakHashMap.unmaskNull(var10_10), var11_11));
                            var6_6 = var7_7;
                            var9_9 = var8_8;
                        }
                    }
                    var8_8 = var9_9;
                    var7_7 = var6_6;
                    if (var9_9 != null) ** GOTO lbl-1000
                    var8_8 = var9_9;
                    var7_7 = var6_6;
                } while (var6_6 < var5_5);
            }
            if (var2_2.modCount != var4_4) throw new ConcurrentModificationException();
        }

        @Override
        public boolean tryAdvance(Consumer<? super Map.Entry<K, V>> consumer) {
            if (consumer != null) {
                Entry<K, V>[] arrentry = this.map.table;
                int n = arrentry.length;
                int n2 = this.getFence();
                if (n >= n2 && this.index >= 0) {
                    while (this.current != null || this.index < n2) {
                        if (this.current == null) {
                            n = this.index;
                            this.index = n + 1;
                            this.current = arrentry[n];
                            continue;
                        }
                        Object t = this.current.get();
                        Object v = this.current.value;
                        this.current = this.current.next;
                        if (t == null) continue;
                        consumer.accept(new AbstractMap.SimpleImmutableEntry(WeakHashMap.unmaskNull(t), v));
                        if (this.map.modCount == this.expectedModCount) {
                            return true;
                        }
                        throw new ConcurrentModificationException();
                    }
                }
                return false;
            }
            throw new NullPointerException();
        }

        public EntrySpliterator<K, V> trySplit() {
            Object object;
            int n = this.index;
            int n2 = this.getFence();
            int n3 = n + n2 >>> 1;
            if (n >= n3) {
                object = null;
            } else {
                object = this.map;
                this.index = n3;
                this.est = n2 = this.est >>> 1;
                object = new EntrySpliterator<K, V>((WeakHashMap<K, V>)object, n, n3, n2, this.expectedModCount);
            }
            return object;
        }
    }

    private abstract class HashIterator<T>
    implements Iterator<T> {
        private Object currentKey;
        private Entry<K, V> entry;
        private int expectedModCount;
        private int index;
        private Entry<K, V> lastReturned;
        private Object nextKey;

        HashIterator() {
            this.expectedModCount = WeakHashMap.this.modCount;
            int n = WeakHashMap.this.isEmpty() ? 0 : WeakHashMap.this.table.length;
            this.index = n;
        }

        @Override
        public boolean hasNext() {
            Entry<K, V>[] arrentry = WeakHashMap.this.table;
            while (this.nextKey == null) {
                Entry<K, V> entry = this.entry;
                int n = this.index;
                while (entry == null && n > 0) {
                    entry = arrentry[--n];
                }
                this.entry = entry;
                this.index = n;
                if (entry == null) {
                    this.currentKey = null;
                    return false;
                }
                this.nextKey = entry.get();
                if (this.nextKey != null) continue;
                this.entry = this.entry.next;
            }
            return true;
        }

        protected Entry<K, V> nextEntry() {
            if (WeakHashMap.this.modCount == this.expectedModCount) {
                if (this.nextKey == null && !this.hasNext()) {
                    throw new NoSuchElementException();
                }
                Entry<K, V> entry = this.entry;
                this.lastReturned = entry;
                this.entry = entry.next;
                this.currentKey = this.nextKey;
                this.nextKey = null;
                return this.lastReturned;
            }
            throw new ConcurrentModificationException();
        }

        @Override
        public void remove() {
            if (this.lastReturned != null) {
                if (WeakHashMap.this.modCount == this.expectedModCount) {
                    WeakHashMap.this.remove(this.currentKey);
                    this.expectedModCount = WeakHashMap.this.modCount;
                    this.lastReturned = null;
                    this.currentKey = null;
                    return;
                }
                throw new ConcurrentModificationException();
            }
            throw new IllegalStateException();
        }
    }

    private class KeyIterator
    extends WeakHashMap<K, V> {
        private KeyIterator() {
        }

        public K next() {
            return this.nextEntry().getKey();
        }
    }

    private class KeySet
    extends AbstractSet<K> {
        private KeySet() {
        }

        @Override
        public void clear() {
            WeakHashMap.this.clear();
        }

        @Override
        public boolean contains(Object object) {
            return WeakHashMap.this.containsKey(object);
        }

        @Override
        public Iterator<K> iterator() {
            return new KeyIterator();
        }

        @Override
        public boolean remove(Object object) {
            if (WeakHashMap.this.containsKey(object)) {
                WeakHashMap.this.remove(object);
                return true;
            }
            return false;
        }

        @Override
        public int size() {
            return WeakHashMap.this.size();
        }

        @Override
        public Spliterator<K> spliterator() {
            return new KeySpliterator(WeakHashMap.this, 0, -1, 0, 0);
        }
    }

    static final class KeySpliterator<K, V>
    extends WeakHashMapSpliterator<K, V>
    implements Spliterator<K> {
        KeySpliterator(WeakHashMap<K, V> weakHashMap, int n, int n2, int n3, int n4) {
            super(weakHashMap, n, n2, n3, n4);
        }

        @Override
        public int characteristics() {
            return 1;
        }

        /*
         * Unable to fully structure code
         * Enabled aggressive block sorting
         * Lifted jumps to return sites
         */
        @Override
        public void forEachRemaining(Consumer<? super K> var1_1) {
            block6 : {
                if (var1_1 == null) throw new NullPointerException();
                var2_2 = this.map;
                var3_3 = var2_2.table;
                var5_5 = var4_4 = this.fence;
                if (var4_4 < 0) {
                    this.expectedModCount = var4_4 = var2_2.modCount;
                    this.fence = var5_5 = var3_3.length;
                } else {
                    var4_4 = this.expectedModCount;
                }
                if (var3_3.length < var5_5) break block6;
                var7_7 = var6_6 = this.index;
                if (var6_6 < 0) break block6;
                this.index = var5_5;
                if (var7_7 >= var5_5 && this.current == null) break block6;
                var8_8 = this.current;
                this.current = null;
                do lbl-1000: // 3 sources:
                {
                    if (var8_8 == null) {
                        var9_9 = var3_3[var7_7];
                        var6_6 = var7_7 + 1;
                    } else {
                        var10_10 = var8_8.get();
                        var8_8 = var8_8.next;
                        var9_9 = var8_8;
                        var6_6 = var7_7;
                        if (var10_10 != null) {
                            var1_1.accept(WeakHashMap.unmaskNull(var10_10));
                            var6_6 = var7_7;
                            var9_9 = var8_8;
                        }
                    }
                    var8_8 = var9_9;
                    var7_7 = var6_6;
                    if (var9_9 != null) ** GOTO lbl-1000
                    var8_8 = var9_9;
                    var7_7 = var6_6;
                } while (var6_6 < var5_5);
            }
            if (var2_2.modCount != var4_4) throw new ConcurrentModificationException();
        }

        @Override
        public boolean tryAdvance(Consumer<? super K> consumer) {
            if (consumer != null) {
                Entry<K, V>[] arrentry = this.map.table;
                int n = arrentry.length;
                int n2 = this.getFence();
                if (n >= n2 && this.index >= 0) {
                    while (this.current != null || this.index < n2) {
                        if (this.current == null) {
                            n = this.index;
                            this.index = n + 1;
                            this.current = arrentry[n];
                            continue;
                        }
                        Object t = this.current.get();
                        this.current = this.current.next;
                        if (t == null) continue;
                        consumer.accept(WeakHashMap.unmaskNull(t));
                        if (this.map.modCount == this.expectedModCount) {
                            return true;
                        }
                        throw new ConcurrentModificationException();
                    }
                }
                return false;
            }
            throw new NullPointerException();
        }

        public KeySpliterator<K, V> trySplit() {
            Object object;
            int n = this.getFence();
            int n2 = this.index;
            if (n2 >= (n = n2 + n >>> 1)) {
                object = null;
            } else {
                int n3;
                object = this.map;
                this.index = n;
                this.est = n3 = this.est >>> 1;
                object = new KeySpliterator<K, V>((WeakHashMap<K, V>)object, n2, n, n3, this.expectedModCount);
            }
            return object;
        }
    }

    private class ValueIterator
    extends WeakHashMap<K, V> {
        private ValueIterator() {
        }

        public V next() {
            return this.nextEntry().value;
        }
    }

    static final class ValueSpliterator<K, V>
    extends WeakHashMapSpliterator<K, V>
    implements Spliterator<V> {
        ValueSpliterator(WeakHashMap<K, V> weakHashMap, int n, int n2, int n3, int n4) {
            super(weakHashMap, n, n2, n3, n4);
        }

        @Override
        public int characteristics() {
            return 0;
        }

        /*
         * Unable to fully structure code
         * Enabled aggressive block sorting
         * Lifted jumps to return sites
         */
        @Override
        public void forEachRemaining(Consumer<? super V> var1_1) {
            block6 : {
                if (var1_1 == null) throw new NullPointerException();
                var2_2 = this.map;
                var3_3 = var2_2.table;
                var5_5 = var4_4 = this.fence;
                if (var4_4 < 0) {
                    this.expectedModCount = var4_4 = var2_2.modCount;
                    this.fence = var5_5 = var3_3.length;
                } else {
                    var4_4 = this.expectedModCount;
                }
                if (var3_3.length < var5_5) break block6;
                var7_7 = var6_6 = this.index;
                if (var6_6 < 0) break block6;
                this.index = var5_5;
                if (var7_7 >= var5_5 && this.current == null) break block6;
                var8_8 = this.current;
                this.current = null;
                do lbl-1000: // 3 sources:
                {
                    if (var8_8 == null) {
                        var9_9 = var3_3[var7_7];
                        var6_6 = var7_7 + 1;
                    } else {
                        var10_10 = var8_8.get();
                        var11_11 = var8_8.value;
                        var8_8 = var8_8.next;
                        var9_9 = var8_8;
                        var6_6 = var7_7;
                        if (var10_10 != null) {
                            var1_1.accept(var11_11);
                            var6_6 = var7_7;
                            var9_9 = var8_8;
                        }
                    }
                    var8_8 = var9_9;
                    var7_7 = var6_6;
                    if (var9_9 != null) ** GOTO lbl-1000
                    var8_8 = var9_9;
                    var7_7 = var6_6;
                } while (var6_6 < var5_5);
            }
            if (var2_2.modCount != var4_4) throw new ConcurrentModificationException();
        }

        @Override
        public boolean tryAdvance(Consumer<? super V> consumer) {
            if (consumer != null) {
                Entry<K, V>[] arrentry = this.map.table;
                int n = arrentry.length;
                int n2 = this.getFence();
                if (n >= n2 && this.index >= 0) {
                    while (this.current != null || this.index < n2) {
                        if (this.current == null) {
                            n = this.index;
                            this.index = n + 1;
                            this.current = arrentry[n];
                            continue;
                        }
                        Object t = this.current.get();
                        Object v = this.current.value;
                        this.current = this.current.next;
                        if (t == null) continue;
                        consumer.accept(v);
                        if (this.map.modCount == this.expectedModCount) {
                            return true;
                        }
                        throw new ConcurrentModificationException();
                    }
                }
                return false;
            }
            throw new NullPointerException();
        }

        public ValueSpliterator<K, V> trySplit() {
            Object object;
            int n = this.index;
            int n2 = this.getFence();
            int n3 = n + n2 >>> 1;
            if (n >= n3) {
                object = null;
            } else {
                object = this.map;
                this.index = n3;
                this.est = n2 = this.est >>> 1;
                object = new ValueSpliterator<K, V>((WeakHashMap<K, V>)object, n, n3, n2, this.expectedModCount);
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
            WeakHashMap.this.clear();
        }

        @Override
        public boolean contains(Object object) {
            return WeakHashMap.this.containsValue(object);
        }

        @Override
        public Iterator<V> iterator() {
            return new ValueIterator();
        }

        @Override
        public int size() {
            return WeakHashMap.this.size();
        }

        @Override
        public Spliterator<V> spliterator() {
            return new ValueSpliterator(WeakHashMap.this, 0, -1, 0, 0);
        }
    }

    static class WeakHashMapSpliterator<K, V> {
        Entry<K, V> current;
        int est;
        int expectedModCount;
        int fence;
        int index;
        final WeakHashMap<K, V> map;

        WeakHashMapSpliterator(WeakHashMap<K, V> weakHashMap, int n, int n2, int n3, int n4) {
            this.map = weakHashMap;
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
                WeakHashMap<K, V> weakHashMap = this.map;
                this.est = weakHashMap.size();
                this.expectedModCount = weakHashMap.modCount;
                this.fence = n2 = weakHashMap.table.length;
            }
            return n2;
        }
    }

}

