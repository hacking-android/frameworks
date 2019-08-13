/*
 * Decompiled with CFR 0.145.
 */
package android.icu.impl;

import android.icu.util.Freezable;
import java.lang.reflect.Constructor;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

public class Relation<K, V>
implements Freezable<Relation<K, V>> {
    private Map<K, Set<V>> data;
    volatile boolean frozen;
    Object[] setComparatorParam;
    Constructor<? extends Set<V>> setCreator;

    public Relation(Map<K, Set<V>> map, Class<?> class_) {
        this(map, class_, null);
    }

    /*
     * WARNING - combined exceptions agressively - possible behaviour change.
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public Relation(Map<K, Set<V>> var1_1, Class<?> var2_3, Comparator<V> var3_4) {
        block5 : {
            super();
            this.frozen = false;
            if (var3_4 != null) break block5;
            var4_5 = null;
            ** GOTO lbl9
        }
        try {
            var4_5 = new Object[]{var3_4};
lbl9: // 2 sources:
            this.setComparatorParam = var4_5;
            if (var3_4 == null) {
                this.setCreator = var2_3.getConstructor(new Class[0]);
                this.setCreator.newInstance(this.setComparatorParam);
            } else {
                this.setCreator = var2_3.getConstructor(new Class[]{Comparator.class});
                this.setCreator.newInstance(this.setComparatorParam);
            }
            if (var1_1 == null) {
                var1_1 = new HashMap<K, V>();
            }
            this.data = var1_1;
            return;
        }
        catch (Exception var1_2) {
            throw (RuntimeException)new IllegalArgumentException("Can't create new set").initCause(var1_2);
        }
    }

    private Set<V> newSet() {
        try {
            Set<V> set = this.setCreator.newInstance(this.setComparatorParam);
            return set;
        }
        catch (Exception exception) {
            throw (RuntimeException)new IllegalArgumentException("Can't create new set").initCause(exception);
        }
    }

    public static <K, V> Relation<K, V> of(Map<K, Set<V>> map, Class<?> class_) {
        return new Relation<K, V>(map, class_);
    }

    public static <K, V> Relation<K, V> of(Map<K, Set<V>> map, Class<?> class_, Comparator<V> comparator) {
        return new Relation<K, V>(map, class_, comparator);
    }

    public Relation<K, V> addAllInverted(Relation<V, K> relation) {
        for (K k : relation.data.keySet()) {
            Iterator<V> iterator = relation.data.get(k).iterator();
            while (iterator.hasNext()) {
                this.put(iterator.next(), k);
            }
        }
        return this;
    }

    public Relation<K, V> addAllInverted(Map<V, K> object) {
        for (Map.Entry entry : object.entrySet()) {
            this.put(entry.getValue(), entry.getKey());
        }
        return this;
    }

    public void clear() {
        this.data.clear();
    }

    @Override
    public Relation<K, V> cloneAsThawed() {
        throw new UnsupportedOperationException();
    }

    public boolean containsKey(Object object) {
        return this.data.containsKey(object);
    }

    public boolean containsValue(Object object) {
        Iterator<Set<V>> iterator = this.data.values().iterator();
        while (iterator.hasNext()) {
            if (!iterator.next().contains(object)) continue;
            return true;
        }
        return false;
    }

    public final Set<Map.Entry<K, V>> entrySet() {
        return this.keyValueSet();
    }

    public boolean equals(Object object) {
        if (object == null) {
            return false;
        }
        if (object.getClass() != this.getClass()) {
            return false;
        }
        return this.data.equals(((Relation)object).data);
    }

    @Override
    public Relation<K, V> freeze() {
        if (!this.frozen) {
            for (K k : this.data.keySet()) {
                Map<K, Set<Set<V>>> map = this.data;
                map.put(k, Collections.unmodifiableSet(map.get(k)));
            }
            this.data = Collections.unmodifiableMap(this.data);
            this.frozen = true;
        }
        return this;
    }

    public Set<V> get(Object object) {
        return this.data.get(object);
    }

    public Set<V> getAll(Object object) {
        return this.data.get(object);
    }

    public int hashCode() {
        return this.data.hashCode();
    }

    public boolean isEmpty() {
        return this.data.isEmpty();
    }

    @Override
    public boolean isFrozen() {
        return this.frozen;
    }

    public Set<K> keySet() {
        return this.data.keySet();
    }

    public Set<Map.Entry<K, V>> keyValueSet() {
        LinkedHashSet<Map.Entry<K, V>> linkedHashSet = new LinkedHashSet<Map.Entry<K, V>>();
        for (K k : this.data.keySet()) {
            Iterator<V> iterator = this.data.get(k).iterator();
            while (iterator.hasNext()) {
                linkedHashSet.add(new SimpleEntry<K, V>(k, iterator.next()));
            }
        }
        return linkedHashSet;
    }

    public Set<Map.Entry<K, Set<V>>> keyValuesSet() {
        return this.data.entrySet();
    }

    public V put(K k, V v) {
        Set<V> set;
        Set<V> set2 = set = this.data.get(k);
        if (set == null) {
            Map<K, Set<Set<V>>> map = this.data;
            set2 = set = this.newSet();
            map.put(k, set);
        }
        set2.add(v);
        return v;
    }

    public V putAll(K object, Collection<? extends V> collection) {
        Set<V> set;
        Set<V> set2 = set = this.data.get(object);
        if (set == null) {
            Map<K, Set<Set<V>>> map = this.data;
            set2 = set = this.newSet();
            map.put(object, set);
        }
        set2.addAll(collection);
        object = collection.size() == 0 ? null : collection.iterator().next();
        return (V)object;
    }

    public V putAll(Collection<K> collection, V v) {
        Object var3_3 = null;
        Iterator<K> iterator = collection.iterator();
        collection = var3_3;
        while (iterator.hasNext()) {
            collection = this.put(iterator.next(), v);
        }
        return (V)collection;
    }

    public void putAll(Relation<? extends K, ? extends V> relation) {
        for (K k : relation.keySet()) {
            Iterator<V> iterator = relation.getAll(k).iterator();
            while (iterator.hasNext()) {
                this.put(k, iterator.next());
            }
        }
    }

    public void putAll(Map<? extends K, ? extends V> object) {
        for (Map.Entry entry : object.entrySet()) {
            this.put(entry.getKey(), entry.getValue());
        }
    }

    public boolean remove(K k, V v) {
        Set<V> set;
        block4 : {
            try {
                set = this.data.get(k);
                if (set != null) break block4;
                return false;
            }
            catch (NullPointerException nullPointerException) {
                return false;
            }
        }
        boolean bl = set.remove(v);
        if (set.size() == 0) {
            this.data.remove(k);
        }
        return bl;
    }

    public Set<V> removeAll(K object) {
        try {
            object = this.data.remove(object);
            return object;
        }
        catch (NullPointerException nullPointerException) {
            return null;
        }
    }

    public Set<V> removeAll(Collection<K> object) {
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        object = object.iterator();
        while (object.hasNext()) {
            Object object2 = object.next();
            try {
                if ((object2 = this.data.remove(object2)) == null) continue;
            }
            catch (NullPointerException nullPointerException) {}
            linkedHashSet.addAll(object2);
        }
        return linkedHashSet;
    }

    @SafeVarargs
    public final Set<V> removeAll(K ... arrK) {
        return this.removeAll((Collection<K>)Arrays.asList(arrK));
    }

    public boolean removeAll(Relation<K, V> relation) {
        boolean bl = false;
        for (K k : relation.keySet()) {
            boolean bl2;
            block4 : {
                Set<V> set;
                try {
                    set = relation.getAll(k);
                    bl2 = bl;
                    if (set == null) break block4;
                }
                catch (NullPointerException nullPointerException) {
                    continue;
                }
                bl2 = this.removeAll(k, (Iterable<V>)set);
                bl2 = bl | bl2;
            }
            bl = bl2;
        }
        return bl;
    }

    public boolean removeAll(K k, Iterable<V> object) {
        boolean bl = false;
        object = object.iterator();
        while (object.hasNext()) {
            bl |= this.remove(k, object.next());
        }
        return bl;
    }

    public int size() {
        return this.data.size();
    }

    public String toString() {
        return this.data.toString();
    }

    public <C extends Collection<V>> C values(C c) {
        Iterator<Map.Entry<K, Set<V>>> iterator = this.data.entrySet().iterator();
        while (iterator.hasNext()) {
            c.addAll((Collection)iterator.next().getValue());
        }
        return c;
    }

    public Set<V> values() {
        return this.values(new LinkedHashSet());
    }

    static class SimpleEntry<K, V>
    implements Map.Entry<K, V> {
        K key;
        V value;

        public SimpleEntry(K k, V v) {
            this.key = k;
            this.value = v;
        }

        public SimpleEntry(Map.Entry<K, V> entry) {
            this.key = entry.getKey();
            this.value = entry.getValue();
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
        public V setValue(V v) {
            V v2 = this.value;
            this.value = v;
            return v2;
        }
    }

}

