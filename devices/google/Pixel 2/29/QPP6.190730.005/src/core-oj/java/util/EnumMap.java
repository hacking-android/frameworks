/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  java.util.EnumMap.EntryIterator.Entry
 *  java.util.EnumMap.EnumMapIterator
 */
package java.util;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.AbstractCollection;
import java.util.AbstractMap;
import java.util.AbstractSet;
import java.util.Arrays;
import java.util.Collection;
import java.util.EnumMap.EntryIterator.Entry;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

public class EnumMap<K extends Enum<K>, V>
extends AbstractMap<K, V>
implements Serializable,
Cloneable {
    private static final Object NULL = new Object(){

        public int hashCode() {
            return 0;
        }

        public String toString() {
            return "java.util.EnumMap.NULL";
        }
    };
    private static final Enum<?>[] ZERO_LENGTH_ENUM_ARRAY = new Enum[0];
    private static final long serialVersionUID = 458661240069192865L;
    private transient Set<Map.Entry<K, V>> entrySet;
    private final Class<K> keyType;
    private transient K[] keyUniverse;
    private transient int size;
    private transient Object[] vals;

    public EnumMap(Class<K> class_) {
        this.size = 0;
        this.keyType = class_;
        this.keyUniverse = EnumMap.getKeyUniverse(class_);
        this.vals = new Object[this.keyUniverse.length];
    }

    public EnumMap(EnumMap<K, ? extends V> enumMap) {
        this.size = 0;
        this.keyType = enumMap.keyType;
        this.keyUniverse = enumMap.keyUniverse;
        this.vals = (Object[])enumMap.vals.clone();
        this.size = enumMap.size;
    }

    public EnumMap(Map<K, ? extends V> enumMap) {
        block4 : {
            block3 : {
                block2 : {
                    this.size = 0;
                    if (!(enumMap instanceof EnumMap)) break block2;
                    enumMap = enumMap;
                    this.keyType = enumMap.keyType;
                    this.keyUniverse = enumMap.keyUniverse;
                    this.vals = (Object[])enumMap.vals.clone();
                    this.size = enumMap.size;
                    break block3;
                }
                if (enumMap.isEmpty()) break block4;
                this.keyType = ((Enum)enumMap.keySet().iterator().next()).getDeclaringClass();
                this.keyUniverse = EnumMap.getKeyUniverse(this.keyType);
                this.vals = new Object[this.keyUniverse.length];
                this.putAll(enumMap);
            }
            return;
        }
        throw new IllegalArgumentException("Specified map is empty");
    }

    static /* synthetic */ int access$210(EnumMap enumMap) {
        int n = enumMap.size;
        enumMap.size = n - 1;
        return n;
    }

    private boolean containsMapping(Object object, Object object2) {
        boolean bl = this.isValidKey(object) && this.maskNull(object2).equals(this.vals[((Enum)object).ordinal()]);
        return bl;
    }

    private int entryHashCode(int n) {
        return ((Enum)this.keyUniverse[n]).hashCode() ^ this.vals[n].hashCode();
    }

    private boolean equals(EnumMap<?, ?> enumMap) {
        Object object = enumMap.keyType;
        Object object2 = this.keyType;
        boolean bl = false;
        if (object != object2) {
            boolean bl2 = bl;
            if (this.size == 0) {
                bl2 = bl;
                if (enumMap.size == 0) {
                    bl2 = true;
                }
            }
            return bl2;
        }
        for (int i = 0; i < this.keyUniverse.length; ++i) {
            object = enumMap.vals[i];
            object2 = this.vals[i];
            if (object == object2 || object != null && object.equals(object2)) continue;
            return false;
        }
        return true;
    }

    private static <K extends Enum<K>> K[] getKeyUniverse(Class<K> class_) {
        return (Enum[])class_.getEnumConstantsShared();
    }

    private boolean isValidKey(Object class_) {
        boolean bl = false;
        if (class_ == null) {
            return false;
        }
        if ((class_ = class_.getClass()) == this.keyType || class_.getSuperclass() == this.keyType) {
            bl = true;
        }
        return bl;
    }

    private Object maskNull(Object object) {
        block0 : {
            if (object != null) break block0;
            object = NULL;
        }
        return object;
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        this.keyUniverse = EnumMap.getKeyUniverse(this.keyType);
        this.vals = new Object[this.keyUniverse.length];
        int n = objectInputStream.readInt();
        for (int i = 0; i < n; ++i) {
            this.put((K)((Enum)objectInputStream.readObject()), (V)objectInputStream.readObject());
        }
    }

    private boolean removeMapping(Object object, Object object2) {
        if (!this.isValidKey(object)) {
            return false;
        }
        int n = ((Enum)object).ordinal();
        if (this.maskNull(object2).equals(this.vals[n])) {
            this.vals[n] = null;
            --this.size;
            return true;
        }
        return false;
    }

    private void typeCheck(K object) {
        Class<?> class_ = object.getClass();
        if (class_ != this.keyType && class_.getSuperclass() != this.keyType) {
            object = new StringBuilder();
            ((StringBuilder)object).append(class_);
            ((StringBuilder)object).append(" != ");
            ((StringBuilder)object).append(this.keyType);
            throw new ClassCastException(((StringBuilder)object).toString());
        }
    }

    private V unmaskNull(Object object) {
        block0 : {
            if (object != NULL) break block0;
            object = null;
        }
        return (V)object;
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        objectOutputStream.writeInt(this.size);
        int n = this.size;
        int n2 = 0;
        while (n > 0) {
            int n3 = n;
            if (this.vals[n2] != null) {
                objectOutputStream.writeObject(this.keyUniverse[n2]);
                objectOutputStream.writeObject(this.unmaskNull(this.vals[n2]));
                n3 = n - 1;
            }
            ++n2;
            n = n3;
        }
    }

    @Override
    public void clear() {
        Arrays.fill(this.vals, null);
        this.size = 0;
    }

    @Override
    public EnumMap<K, V> clone() {
        EnumMap enumMap;
        try {
            enumMap = (EnumMap)super.clone();
        }
        catch (CloneNotSupportedException cloneNotSupportedException) {
            throw new AssertionError();
        }
        enumMap.vals = (Object[])enumMap.vals.clone();
        enumMap.entrySet = null;
        return enumMap;
    }

    @Override
    public boolean containsKey(Object object) {
        boolean bl = this.isValidKey(object) && this.vals[((Enum)object).ordinal()] != null;
        return bl;
    }

    @Override
    public boolean containsValue(Object arrobject) {
        Object object = this.maskNull(arrobject);
        arrobject = this.vals;
        int n = arrobject.length;
        for (int i = 0; i < n; ++i) {
            if (!object.equals(arrobject[i])) continue;
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
        Object object2;
        if (this == object) {
            return true;
        }
        if (object instanceof EnumMap) {
            return this.equals((EnumMap)object);
        }
        if (!(object instanceof Map)) {
            return false;
        }
        if (this.size != (object = (Map)object).size()) {
            return false;
        }
        for (int i = 0; i < ((K[])(object2 = this.keyUniverse)).length; ++i) {
            Object object3 = this.vals;
            if (object3[i] == null) continue;
            object2 = object2[i];
            if (!((object3 = this.unmaskNull(object3[i])) == null ? object.get(object2) != null || !object.containsKey(object2) : !object3.equals(object.get(object2)))) continue;
            return false;
        }
        return true;
    }

    @Override
    public V get(Object object) {
        object = this.isValidKey(object) ? this.unmaskNull(this.vals[((Enum)object).ordinal()]) : null;
        return (V)object;
    }

    @Override
    public int hashCode() {
        int n = 0;
        for (int i = 0; i < this.keyUniverse.length; ++i) {
            int n2 = n;
            if (this.vals[i] != null) {
                n2 = n + this.entryHashCode(i);
            }
            n = n2;
        }
        return n;
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
        this.typeCheck(object);
        int n = ((Enum)object).ordinal();
        Object[] arrobject = this.vals;
        object = arrobject[n];
        arrobject[n] = this.maskNull(v);
        if (object == null) {
            ++this.size;
        }
        return this.unmaskNull(object);
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> enumMap) {
        if (enumMap instanceof EnumMap) {
            enumMap = enumMap;
            if (enumMap.keyType != this.keyType) {
                if (enumMap.isEmpty()) {
                    return;
                }
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(enumMap.keyType);
                stringBuilder.append(" != ");
                stringBuilder.append(this.keyType);
                throw new ClassCastException(stringBuilder.toString());
            }
            for (int i = 0; i < this.keyUniverse.length; ++i) {
                Object object = enumMap.vals[i];
                if (object == null) continue;
                if (this.vals[i] == null) {
                    ++this.size;
                }
                this.vals[i] = object;
            }
        } else {
            super.putAll(enumMap);
        }
    }

    @Override
    public V remove(Object object) {
        if (!this.isValidKey(object)) {
            return null;
        }
        int n = ((Enum)object).ordinal();
        Object[] arrobject = this.vals;
        object = arrobject[n];
        arrobject[n] = null;
        if (object != null) {
            --this.size;
        }
        return this.unmaskNull(object);
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
    extends java.util.EnumMap.EnumMapIterator<Map.Entry<K, V>> {
        private java.util.EnumMap.EntryIterator.Entry lastReturnedEntry;

        private EntryIterator() {
        }

        public Map.Entry<K, V> next() {
            if (this.hasNext()) {
                int n = this.index;
                this.index = n + 1;
                this.lastReturnedEntry = new Entry(n);
                return this.lastReturnedEntry;
            }
            throw new NoSuchElementException();
        }

        public void remove() {
            java.util.EnumMap.EntryIterator.Entry entry = this.lastReturnedEntry;
            int n = entry == null ? -1 : ((Entry)entry).index;
            this.lastReturnedIndex = n;
            EnumMapIterator.super.remove();
            ((Entry)this.lastReturnedEntry).index = this.lastReturnedIndex;
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
                int n = this.index;
                boolean bl = true;
                boolean bl2 = true;
                if (n < 0) {
                    if (object != this) {
                        bl2 = false;
                    }
                    return bl2;
                }
                if (!(object instanceof Map.Entry)) {
                    return false;
                }
                Map.Entry entry = (Map.Entry)object;
                object = EnumMap.this.unmaskNull(EnumMap.this.vals[this.index]);
                Object v = entry.getValue();
                bl2 = entry.getKey() == EnumMap.this.keyUniverse[this.index] && (object == v || object != null && object.equals(v)) ? bl : false;
                return bl2;
            }

            @Override
            public K getKey() {
                this.checkIndexForEntryUse();
                return (K)EnumMap.this.keyUniverse[this.index];
            }

            @Override
            public V getValue() {
                this.checkIndexForEntryUse();
                return (V)EnumMap.this.unmaskNull(EnumMap.this.vals[this.index]);
            }

            @Override
            public int hashCode() {
                if (this.index < 0) {
                    return super.hashCode();
                }
                return EnumMap.this.entryHashCode(this.index);
            }

            @Override
            public V setValue(V v) {
                this.checkIndexForEntryUse();
                Object object = EnumMap.this.unmaskNull(EnumMap.this.vals[this.index]);
                EnumMap.access$600((EnumMap)EnumMap.this)[this.index] = EnumMap.this.maskNull(v);
                return (V)object;
            }

            public String toString() {
                if (this.index < 0) {
                    return super.toString();
                }
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(EnumMap.this.keyUniverse[this.index]);
                stringBuilder.append("=");
                stringBuilder.append(EnumMap.this.unmaskNull(EnumMap.this.vals[this.index]));
                return stringBuilder.toString();
            }
        }

    }

    private class EntrySet
    extends AbstractSet<Map.Entry<K, V>> {
        private EntrySet() {
        }

        private Object[] fillEntryArray(Object[] arrobject) {
            int n = 0;
            for (int i = 0; i < EnumMap.this.vals.length; ++i) {
                int n2 = n;
                if (EnumMap.this.vals[i] != null) {
                    Enum enum_ = EnumMap.this.keyUniverse[i];
                    EnumMap enumMap = EnumMap.this;
                    arrobject[n] = new AbstractMap.SimpleEntry<Enum, Object>(enum_, enumMap.unmaskNull(enumMap.vals[i]));
                    n2 = n + 1;
                }
                n = n2;
            }
            return arrobject;
        }

        @Override
        public void clear() {
            EnumMap.this.clear();
        }

        @Override
        public boolean contains(Object object) {
            if (!(object instanceof Map.Entry)) {
                return false;
            }
            object = (Map.Entry)object;
            return EnumMap.this.containsMapping(object.getKey(), object.getValue());
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
            return EnumMap.this.removeMapping(object.getKey(), object.getValue());
        }

        @Override
        public int size() {
            return EnumMap.this.size;
        }

        @Override
        public Object[] toArray() {
            return this.fillEntryArray(new Object[EnumMap.this.size]);
        }

        @Override
        public <T> T[] toArray(T[] arrT) {
            int n = this.size();
            Object[] arrobject = arrT;
            if (arrT.length < n) {
                arrobject = (Object[])Array.newInstance(arrT.getClass().getComponentType(), n);
            }
            if (arrobject.length > n) {
                arrobject[n] = null;
            }
            return this.fillEntryArray(arrobject);
        }
    }

    private abstract class EnumMapIterator<T>
    implements Iterator<T> {
        int index = 0;
        int lastReturnedIndex = -1;

        private EnumMapIterator() {
        }

        private void checkLastReturnedIndex() {
            if (this.lastReturnedIndex >= 0) {
                return;
            }
            throw new IllegalStateException();
        }

        @Override
        public boolean hasNext() {
            Object[] arrobject;
            int n;
            while (this.index < EnumMap.this.vals.length && (arrobject = EnumMap.this.vals)[n = this.index] == null) {
                this.index = n + 1;
            }
            boolean bl = this.index != EnumMap.this.vals.length;
            return bl;
        }

        @Override
        public void remove() {
            this.checkLastReturnedIndex();
            if (EnumMap.this.vals[this.lastReturnedIndex] != null) {
                EnumMap.access$600((EnumMap)EnumMap.this)[this.lastReturnedIndex] = null;
                EnumMap.access$210(EnumMap.this);
            }
            this.lastReturnedIndex = -1;
        }
    }

    private class KeyIterator
    extends java.util.EnumMap.EnumMapIterator<K> {
        private KeyIterator() {
        }

        public K next() {
            if (this.hasNext()) {
                int n = this.index;
                this.index = n + 1;
                this.lastReturnedIndex = n;
                return (K)EnumMap.this.keyUniverse[this.lastReturnedIndex];
            }
            throw new NoSuchElementException();
        }
    }

    private class KeySet
    extends AbstractSet<K> {
        private KeySet() {
        }

        @Override
        public void clear() {
            EnumMap.this.clear();
        }

        @Override
        public boolean contains(Object object) {
            return EnumMap.this.containsKey(object);
        }

        @Override
        public Iterator<K> iterator() {
            return new KeyIterator();
        }

        @Override
        public boolean remove(Object object) {
            int n = EnumMap.this.size;
            EnumMap.this.remove(object);
            boolean bl = EnumMap.this.size != n;
            return bl;
        }

        @Override
        public int size() {
            return EnumMap.this.size;
        }
    }

    private class ValueIterator
    extends java.util.EnumMap.EnumMapIterator<V> {
        private ValueIterator() {
        }

        public V next() {
            if (this.hasNext()) {
                int n = this.index;
                this.index = n + 1;
                this.lastReturnedIndex = n;
                EnumMap enumMap = EnumMap.this;
                return (V)enumMap.unmaskNull(enumMap.vals[this.lastReturnedIndex]);
            }
            throw new NoSuchElementException();
        }
    }

    private class Values
    extends AbstractCollection<V> {
        private Values() {
        }

        @Override
        public void clear() {
            EnumMap.this.clear();
        }

        @Override
        public boolean contains(Object object) {
            return EnumMap.this.containsValue(object);
        }

        @Override
        public Iterator<V> iterator() {
            return new ValueIterator();
        }

        @Override
        public boolean remove(Object object) {
            object = EnumMap.this.maskNull(object);
            for (int i = 0; i < EnumMap.this.vals.length; ++i) {
                if (!object.equals(EnumMap.this.vals[i])) continue;
                EnumMap.access$600((EnumMap)EnumMap.this)[i] = null;
                EnumMap.access$210(EnumMap.this);
                return true;
            }
            return false;
        }

        @Override
        public int size() {
            return EnumMap.this.size;
        }
    }

}

