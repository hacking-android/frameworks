/*
 * Decompiled with CFR 0.145.
 */
package java.util;

import java.io.IOException;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.AbstractSet;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.Spliterator;

public class HashSet<E>
extends AbstractSet<E>
implements Set<E>,
Cloneable,
Serializable {
    private static final Object PRESENT = new Object();
    static final long serialVersionUID = -5024744406713321676L;
    private transient HashMap<E, Object> map;

    public HashSet() {
        this.map = new HashMap();
    }

    public HashSet(int n) {
        this.map = new HashMap(n);
    }

    public HashSet(int n, float f) {
        this.map = new HashMap(n, f);
    }

    HashSet(int n, float f, boolean bl) {
        this.map = new LinkedHashMap<E, Object>(n, f);
    }

    public HashSet(Collection<? extends E> collection) {
        this.map = new HashMap(Math.max((int)((float)collection.size() / 0.75f) + 1, 16));
        this.addAll(collection);
    }

    private void readObject(ObjectInputStream object) throws IOException, ClassNotFoundException {
        ((ObjectInputStream)object).defaultReadObject();
        int n = ((ObjectInputStream)object).readInt();
        if (n >= 0) {
            float f = ((ObjectInputStream)object).readFloat();
            if (!(f <= 0.0f) && !Float.isNaN(f)) {
                int n2 = ((ObjectInputStream)object).readInt();
                if (n2 >= 0) {
                    n = (int)Math.min((float)n2 * Math.min(1.0f / f, 4.0f), 1.07374182E9f);
                    Object object2 = this instanceof LinkedHashSet ? new LinkedHashMap(n, f) : new HashMap(n, f);
                    this.map = object2;
                    for (n = 0; n < n2; ++n) {
                        object2 = ((ObjectInputStream)object).readObject();
                        this.map.put(object2, PRESENT);
                    }
                    return;
                }
                object = new StringBuilder();
                ((StringBuilder)object).append("Illegal size: ");
                ((StringBuilder)object).append(n2);
                throw new InvalidObjectException(((StringBuilder)object).toString());
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("Illegal load factor: ");
            ((StringBuilder)object).append(f);
            throw new InvalidObjectException(((StringBuilder)object).toString());
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Illegal capacity: ");
        ((StringBuilder)object).append(n);
        throw new InvalidObjectException(((StringBuilder)object).toString());
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        objectOutputStream.writeInt(this.map.capacity());
        objectOutputStream.writeFloat(this.map.loadFactor());
        objectOutputStream.writeInt(this.map.size());
        Iterator<E> iterator = this.map.keySet().iterator();
        while (iterator.hasNext()) {
            objectOutputStream.writeObject(iterator.next());
        }
    }

    @Override
    public boolean add(E e) {
        boolean bl = this.map.put(e, PRESENT) == null;
        return bl;
    }

    @Override
    public void clear() {
        this.map.clear();
    }

    public Object clone() {
        try {
            HashSet hashSet = (HashSet)Object.super.clone();
            hashSet.map = (HashMap)this.map.clone();
            return hashSet;
        }
        catch (CloneNotSupportedException cloneNotSupportedException) {
            throw new InternalError(cloneNotSupportedException);
        }
    }

    @Override
    public boolean contains(Object object) {
        return this.map.containsKey(object);
    }

    @Override
    public boolean isEmpty() {
        return this.map.isEmpty();
    }

    @Override
    public Iterator<E> iterator() {
        return this.map.keySet().iterator();
    }

    @Override
    public boolean remove(Object object) {
        boolean bl = this.map.remove(object) == PRESENT;
        return bl;
    }

    @Override
    public int size() {
        return this.map.size();
    }

    @Override
    public Spliterator<E> spliterator() {
        return new HashMap.KeySpliterator<E, Object>(this.map, 0, -1, 0, 0);
    }
}

