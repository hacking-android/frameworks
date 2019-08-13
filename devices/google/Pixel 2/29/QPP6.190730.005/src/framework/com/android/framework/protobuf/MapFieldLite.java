/*
 * Decompiled with CFR 0.145.
 */
package com.android.framework.protobuf;

import com.android.framework.protobuf.Internal;
import com.android.framework.protobuf.MutabilityOracle;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public final class MapFieldLite<K, V>
implements MutabilityOracle {
    private static final MapFieldLite EMPTY_MAP_FIELD = new MapFieldLite(Collections.emptyMap());
    private boolean isMutable;
    private MutatabilityAwareMap<K, V> mapData;

    static {
        EMPTY_MAP_FIELD.makeImmutable();
    }

    private MapFieldLite(Map<K, V> map) {
        this.mapData = new MutatabilityAwareMap<K, V>(this, map);
        this.isMutable = true;
    }

    static <K, V> int calculateHashCodeForMap(Map<K, V> object) {
        int n = 0;
        for (Map.Entry entry : object.entrySet()) {
            n += MapFieldLite.calculateHashCodeForObject(entry.getKey()) ^ MapFieldLite.calculateHashCodeForObject(entry.getValue());
        }
        return n;
    }

    private static int calculateHashCodeForObject(Object object) {
        if (object instanceof byte[]) {
            return Internal.hashCode((byte[])object);
        }
        if (!(object instanceof Internal.EnumLite)) {
            return object.hashCode();
        }
        throw new UnsupportedOperationException();
    }

    private static Object copy(Object arrby) {
        if (arrby instanceof byte[]) {
            arrby = arrby;
            return Arrays.copyOf(arrby, arrby.length);
        }
        return arrby;
    }

    static <K, V> Map<K, V> copy(Map<K, V> object2) {
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        for (Map.Entry entry : object2.entrySet()) {
            linkedHashMap.put(entry.getKey(), MapFieldLite.copy(entry.getValue()));
        }
        return linkedHashMap;
    }

    public static <K, V> MapFieldLite<K, V> emptyMapField() {
        return EMPTY_MAP_FIELD;
    }

    private static boolean equals(Object object, Object object2) {
        if (object instanceof byte[] && object2 instanceof byte[]) {
            return Arrays.equals((byte[])object, (byte[])object2);
        }
        return object.equals(object2);
    }

    static <K, V> boolean equals(Map<K, V> entry2, Map<K, V> map) {
        if (entry2 == map) {
            return true;
        }
        if (entry2.size() != map.size()) {
            return false;
        }
        for (Map.Entry<K, V> entry2 : entry2.entrySet()) {
            if (!map.containsKey(entry2.getKey())) {
                return false;
            }
            if (MapFieldLite.equals(entry2.getValue(), map.get(entry2.getKey()))) continue;
            return false;
        }
        return true;
    }

    public static <K, V> MapFieldLite<K, V> newMapField() {
        return new MapFieldLite(new LinkedHashMap());
    }

    public void clear() {
        this.mapData.clear();
    }

    public MapFieldLite<K, V> copy() {
        return new MapFieldLite<K, V>(MapFieldLite.copy(this.mapData));
    }

    @Override
    public void ensureMutable() {
        if (this.isMutable()) {
            return;
        }
        throw new UnsupportedOperationException();
    }

    public boolean equals(Object object) {
        if (!(object instanceof MapFieldLite)) {
            return false;
        }
        object = (MapFieldLite)object;
        return MapFieldLite.equals(this.mapData, ((MapFieldLite)object).mapData);
    }

    public Map<K, V> getMap() {
        return Collections.unmodifiableMap(this.mapData);
    }

    public Map<K, V> getMutableMap() {
        return this.mapData;
    }

    public int hashCode() {
        return MapFieldLite.calculateHashCodeForMap(this.mapData);
    }

    public boolean isMutable() {
        return this.isMutable;
    }

    public void makeImmutable() {
        this.isMutable = false;
    }

    public void mergeFrom(MapFieldLite<K, V> mapFieldLite) {
        this.mapData.putAll(MapFieldLite.copy(mapFieldLite.mapData));
    }

    private static class MutatabilityAwareCollection<E>
    implements Collection<E> {
        private final Collection<E> delegate;
        private final MutabilityOracle mutabilityOracle;

        MutatabilityAwareCollection(MutabilityOracle mutabilityOracle, Collection<E> collection) {
            this.mutabilityOracle = mutabilityOracle;
            this.delegate = collection;
        }

        @Override
        public boolean add(E e) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean addAll(Collection<? extends E> collection) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void clear() {
            this.mutabilityOracle.ensureMutable();
            this.delegate.clear();
        }

        @Override
        public boolean contains(Object object) {
            return this.delegate.contains(object);
        }

        @Override
        public boolean containsAll(Collection<?> collection) {
            return this.delegate.containsAll(collection);
        }

        @Override
        public boolean equals(Object object) {
            return this.delegate.equals(object);
        }

        @Override
        public int hashCode() {
            return this.delegate.hashCode();
        }

        @Override
        public boolean isEmpty() {
            return this.delegate.isEmpty();
        }

        @Override
        public Iterator<E> iterator() {
            return new MutatabilityAwareIterator<E>(this.mutabilityOracle, this.delegate.iterator());
        }

        @Override
        public boolean remove(Object object) {
            this.mutabilityOracle.ensureMutable();
            return this.delegate.remove(object);
        }

        @Override
        public boolean removeAll(Collection<?> collection) {
            this.mutabilityOracle.ensureMutable();
            return this.delegate.removeAll(collection);
        }

        @Override
        public boolean retainAll(Collection<?> collection) {
            this.mutabilityOracle.ensureMutable();
            return this.delegate.retainAll(collection);
        }

        @Override
        public int size() {
            return this.delegate.size();
        }

        @Override
        public Object[] toArray() {
            return this.delegate.toArray();
        }

        @Override
        public <T> T[] toArray(T[] arrT) {
            return this.delegate.toArray(arrT);
        }

        public String toString() {
            return this.delegate.toString();
        }
    }

    private static class MutatabilityAwareIterator<E>
    implements Iterator<E> {
        private final Iterator<E> delegate;
        private final MutabilityOracle mutabilityOracle;

        MutatabilityAwareIterator(MutabilityOracle mutabilityOracle, Iterator<E> iterator) {
            this.mutabilityOracle = mutabilityOracle;
            this.delegate = iterator;
        }

        public boolean equals(Object object) {
            return this.delegate.equals(object);
        }

        @Override
        public boolean hasNext() {
            return this.delegate.hasNext();
        }

        public int hashCode() {
            return this.delegate.hashCode();
        }

        @Override
        public E next() {
            return this.delegate.next();
        }

        @Override
        public void remove() {
            this.mutabilityOracle.ensureMutable();
            this.delegate.remove();
        }

        public String toString() {
            return this.delegate.toString();
        }
    }

    static class MutatabilityAwareMap<K, V>
    implements Map<K, V> {
        private final Map<K, V> delegate;
        private final MutabilityOracle mutabilityOracle;

        MutatabilityAwareMap(MutabilityOracle mutabilityOracle, Map<K, V> map) {
            this.mutabilityOracle = mutabilityOracle;
            this.delegate = map;
        }

        @Override
        public void clear() {
            this.mutabilityOracle.ensureMutable();
            this.delegate.clear();
        }

        @Override
        public boolean containsKey(Object object) {
            return this.delegate.containsKey(object);
        }

        @Override
        public boolean containsValue(Object object) {
            return this.delegate.containsValue(object);
        }

        @Override
        public Set<Map.Entry<K, V>> entrySet() {
            return new MutatabilityAwareSet<Map.Entry<K, V>>(this.mutabilityOracle, this.delegate.entrySet());
        }

        @Override
        public boolean equals(Object object) {
            return this.delegate.equals(object);
        }

        @Override
        public V get(Object object) {
            return this.delegate.get(object);
        }

        @Override
        public int hashCode() {
            return this.delegate.hashCode();
        }

        @Override
        public boolean isEmpty() {
            return this.delegate.isEmpty();
        }

        @Override
        public Set<K> keySet() {
            return new MutatabilityAwareSet<K>(this.mutabilityOracle, this.delegate.keySet());
        }

        @Override
        public V put(K k, V v) {
            this.mutabilityOracle.ensureMutable();
            return this.delegate.put(k, v);
        }

        @Override
        public void putAll(Map<? extends K, ? extends V> map) {
            this.mutabilityOracle.ensureMutable();
            this.delegate.putAll(map);
        }

        @Override
        public V remove(Object object) {
            this.mutabilityOracle.ensureMutable();
            return this.delegate.remove(object);
        }

        @Override
        public int size() {
            return this.delegate.size();
        }

        public String toString() {
            return this.delegate.toString();
        }

        @Override
        public Collection<V> values() {
            return new MutatabilityAwareCollection<V>(this.mutabilityOracle, this.delegate.values());
        }
    }

    private static class MutatabilityAwareSet<E>
    implements Set<E> {
        private final Set<E> delegate;
        private final MutabilityOracle mutabilityOracle;

        MutatabilityAwareSet(MutabilityOracle mutabilityOracle, Set<E> set) {
            this.mutabilityOracle = mutabilityOracle;
            this.delegate = set;
        }

        @Override
        public boolean add(E e) {
            this.mutabilityOracle.ensureMutable();
            return this.delegate.add(e);
        }

        @Override
        public boolean addAll(Collection<? extends E> collection) {
            this.mutabilityOracle.ensureMutable();
            return this.delegate.addAll(collection);
        }

        @Override
        public void clear() {
            this.mutabilityOracle.ensureMutable();
            this.delegate.clear();
        }

        @Override
        public boolean contains(Object object) {
            return this.delegate.contains(object);
        }

        @Override
        public boolean containsAll(Collection<?> collection) {
            return this.delegate.containsAll(collection);
        }

        @Override
        public boolean equals(Object object) {
            return this.delegate.equals(object);
        }

        @Override
        public int hashCode() {
            return this.delegate.hashCode();
        }

        @Override
        public boolean isEmpty() {
            return this.delegate.isEmpty();
        }

        @Override
        public Iterator<E> iterator() {
            return new MutatabilityAwareIterator<E>(this.mutabilityOracle, this.delegate.iterator());
        }

        @Override
        public boolean remove(Object object) {
            this.mutabilityOracle.ensureMutable();
            return this.delegate.remove(object);
        }

        @Override
        public boolean removeAll(Collection<?> collection) {
            this.mutabilityOracle.ensureMutable();
            return this.delegate.removeAll(collection);
        }

        @Override
        public boolean retainAll(Collection<?> collection) {
            this.mutabilityOracle.ensureMutable();
            return this.delegate.retainAll(collection);
        }

        @Override
        public int size() {
            return this.delegate.size();
        }

        @Override
        public Object[] toArray() {
            return this.delegate.toArray();
        }

        @Override
        public <T> T[] toArray(T[] arrT) {
            return this.delegate.toArray(arrT);
        }

        public String toString() {
            return this.delegate.toString();
        }
    }

}

