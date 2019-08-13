/*
 * Decompiled with CFR 0.145.
 */
package java.util;

import java.io.Serializable;
import java.util.AbstractCollection;
import java.util.AbstractSet;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public abstract class AbstractMap<K, V>
implements Map<K, V> {
    transient Set<K> keySet;
    transient Collection<V> values;

    protected AbstractMap() {
    }

    private static boolean eq(Object object, Object object2) {
        boolean bl = object == null ? object2 == null : object.equals(object2);
        return bl;
    }

    @Override
    public void clear() {
        this.entrySet().clear();
    }

    protected Object clone() throws CloneNotSupportedException {
        AbstractMap abstractMap = (AbstractMap)super.clone();
        abstractMap.keySet = null;
        abstractMap.values = null;
        return abstractMap;
    }

    @Override
    public boolean containsKey(Object object) {
        Iterator<Map.Entry<K, V>> iterator = this.entrySet().iterator();
        if (object == null) {
            while (iterator.hasNext()) {
                if (iterator.next().getKey() != null) continue;
                return true;
            }
        } else {
            while (iterator.hasNext()) {
                if (!object.equals(iterator.next().getKey())) continue;
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean containsValue(Object object) {
        Iterator<Map.Entry<K, V>> iterator = this.entrySet().iterator();
        if (object == null) {
            while (iterator.hasNext()) {
                if (iterator.next().getValue() != null) continue;
                return true;
            }
        } else {
            while (iterator.hasNext()) {
                if (!object.equals(iterator.next().getValue())) continue;
                return true;
            }
        }
        return false;
    }

    @Override
    public abstract Set<Map.Entry<K, V>> entrySet();

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (!(object instanceof Map)) {
            return false;
        }
        Map map = (Map)object;
        if (map.size() != this.size()) {
            return false;
        }
        try {
            Map.Entry<K, V> entry;
            boolean bl;
            Iterator<Map.Entry<K, V>> iterator = this.entrySet().iterator();
            do {
                if (!iterator.hasNext()) {
                    return true;
                }
                entry = iterator.next();
                object = entry.getKey();
            } while (!((entry = entry.getValue()) == null ? map.get(object) != null || !map.containsKey(object) : !(bl = ((Object)entry).equals(map.get(object)))));
            return false;
        }
        catch (NullPointerException nullPointerException) {
            return false;
        }
        catch (ClassCastException classCastException) {
            return false;
        }
    }

    @Override
    public V get(Object entry) {
        Iterator<Map.Entry<K, V>> iterator = this.entrySet().iterator();
        if (entry == null) {
            while (iterator.hasNext()) {
                entry = iterator.next();
                if (entry.getKey() != null) continue;
                return entry.getValue();
            }
        } else {
            while (iterator.hasNext()) {
                Map.Entry<K, V> entry2 = iterator.next();
                if (!((Object)entry).equals(entry2.getKey())) continue;
                return entry2.getValue();
            }
        }
        return null;
    }

    @Override
    public int hashCode() {
        int n = 0;
        Iterator<Map.Entry<K, V>> iterator = this.entrySet().iterator();
        while (iterator.hasNext()) {
            n += iterator.next().hashCode();
        }
        return n;
    }

    @Override
    public boolean isEmpty() {
        boolean bl = this.size() == 0;
        return bl;
    }

    @Override
    public Set<K> keySet() {
        AbstractSet<K> abstractSet;
        AbstractSet<K> abstractSet2 = abstractSet = this.keySet;
        if (abstractSet == null) {
            this.keySet = abstractSet2 = new AbstractSet<K>(){

                @Override
                public void clear() {
                    AbstractMap.this.clear();
                }

                @Override
                public boolean contains(Object object) {
                    return AbstractMap.this.containsKey(object);
                }

                @Override
                public boolean isEmpty() {
                    return AbstractMap.this.isEmpty();
                }

                @Override
                public Iterator<K> iterator() {
                    return new Iterator<K>(){
                        private Iterator<Map.Entry<K, V>> i;
                        {
                            this.i = AbstractMap.this.entrySet().iterator();
                        }

                        @Override
                        public boolean hasNext() {
                            return this.i.hasNext();
                        }

                        @Override
                        public K next() {
                            return this.i.next().getKey();
                        }

                        @Override
                        public void remove() {
                            this.i.remove();
                        }
                    };
                }

                @Override
                public int size() {
                    return AbstractMap.this.size();
                }

            };
        }
        return abstractSet2;
    }

    @Override
    public V put(K k, V v) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> object) {
        for (Map.Entry<K, V> entry : object.entrySet()) {
            this.put(entry.getKey(), entry.getValue());
        }
    }

    @Override
    public V remove(Object entry) {
        Iterator<Map.Entry<K, V>> iterator = this.entrySet().iterator();
        Map.Entry<K, V> entry2 = null;
        Map.Entry<K, V> entry3 = null;
        if (entry == null) {
            entry = entry3;
            do {
                entry3 = entry;
                if (entry == null) {
                    entry3 = entry;
                    if (iterator.hasNext()) {
                        entry2 = iterator.next();
                        if (entry2.getKey() != null) continue;
                        entry = entry2;
                        continue;
                    }
                }
                break;
            } while (true);
        } else {
            do {
                entry3 = entry2;
                if (entry2 != null) break;
                entry3 = entry2;
                if (!iterator.hasNext()) break;
                entry3 = iterator.next();
                if (!((Object)entry).equals(entry3.getKey())) continue;
                entry2 = entry3;
            } while (true);
        }
        entry = null;
        if (entry3 != null) {
            entry = entry3.getValue();
            iterator.remove();
        }
        return (V)entry;
    }

    @Override
    public int size() {
        return this.entrySet().size();
    }

    public String toString() {
        Iterator<Map.Entry<K, V>> iterator = this.entrySet().iterator();
        if (!iterator.hasNext()) {
            return "{}";
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append('{');
        do {
            Object object = iterator.next();
            Object object2 = object.getKey();
            V v = object.getValue();
            object = "(this Map)";
            if (object2 == this) {
                object2 = "(this Map)";
            }
            stringBuilder.append(object2);
            stringBuilder.append('=');
            object2 = v == this ? object : v;
            stringBuilder.append(object2);
            if (!iterator.hasNext()) {
                stringBuilder.append('}');
                return stringBuilder.toString();
            }
            stringBuilder.append(',');
            stringBuilder.append(' ');
        } while (true);
    }

    @Override
    public Collection<V> values() {
        AbstractCollection<V> abstractCollection;
        AbstractCollection<V> abstractCollection2 = abstractCollection = this.values;
        if (abstractCollection == null) {
            this.values = abstractCollection2 = new AbstractCollection<V>(){

                @Override
                public void clear() {
                    AbstractMap.this.clear();
                }

                @Override
                public boolean contains(Object object) {
                    return AbstractMap.this.containsValue(object);
                }

                @Override
                public boolean isEmpty() {
                    return AbstractMap.this.isEmpty();
                }

                @Override
                public Iterator<V> iterator() {
                    return new Iterator<V>(){
                        private Iterator<Map.Entry<K, V>> i;
                        {
                            this.i = AbstractMap.this.entrySet().iterator();
                        }

                        @Override
                        public boolean hasNext() {
                            return this.i.hasNext();
                        }

                        @Override
                        public V next() {
                            return this.i.next().getValue();
                        }

                        @Override
                        public void remove() {
                            this.i.remove();
                        }
                    };
                }

                @Override
                public int size() {
                    return AbstractMap.this.size();
                }

            };
        }
        return abstractCollection2;
    }

    public static class SimpleEntry<K, V>
    implements Map.Entry<K, V>,
    Serializable {
        private static final long serialVersionUID = -8499721149061103585L;
        private final K key;
        private V value;

        public SimpleEntry(K k, V v) {
            this.key = k;
            this.value = v;
        }

        public SimpleEntry(Map.Entry<? extends K, ? extends V> entry) {
            this.key = entry.getKey();
            this.value = entry.getValue();
        }

        @Override
        public boolean equals(Object object) {
            boolean bl = object instanceof Map.Entry;
            boolean bl2 = false;
            if (!bl) {
                return false;
            }
            object = (Map.Entry)object;
            bl = bl2;
            if (AbstractMap.eq(this.key, object.getKey())) {
                bl = bl2;
                if (AbstractMap.eq(this.value, object.getValue())) {
                    bl = true;
                }
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

    public static class SimpleImmutableEntry<K, V>
    implements Map.Entry<K, V>,
    Serializable {
        private static final long serialVersionUID = 7138329143949025153L;
        private final K key;
        private final V value;

        public SimpleImmutableEntry(K k, V v) {
            this.key = k;
            this.value = v;
        }

        public SimpleImmutableEntry(Map.Entry<? extends K, ? extends V> entry) {
            this.key = entry.getKey();
            this.value = entry.getValue();
        }

        @Override
        public boolean equals(Object object) {
            boolean bl = object instanceof Map.Entry;
            boolean bl2 = false;
            if (!bl) {
                return false;
            }
            object = (Map.Entry)object;
            bl = bl2;
            if (AbstractMap.eq(this.key, object.getKey())) {
                bl = bl2;
                if (AbstractMap.eq(this.value, object.getValue())) {
                    bl = true;
                }
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
            throw new UnsupportedOperationException();
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(this.key);
            stringBuilder.append("=");
            stringBuilder.append(this.value);
            return stringBuilder.toString();
        }
    }

}

