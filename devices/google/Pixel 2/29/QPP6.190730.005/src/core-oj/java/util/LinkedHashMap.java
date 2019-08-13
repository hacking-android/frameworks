/*
 * Decompiled with CFR 0.145.
 */
package java.util;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.AbstractCollection;
import java.util.AbstractSet;
import java.util.Collection;
import java.util.ConcurrentModificationException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;

public class LinkedHashMap<K, V>
extends HashMap<K, V>
implements Map<K, V> {
    private static final long serialVersionUID = 3801124242820219131L;
    final boolean accessOrder;
    transient LinkedHashMapEntry<K, V> head;
    transient LinkedHashMapEntry<K, V> tail;

    public LinkedHashMap() {
        this.accessOrder = false;
    }

    public LinkedHashMap(int n) {
        super(n);
        this.accessOrder = false;
    }

    public LinkedHashMap(int n, float f) {
        super(n, f);
        this.accessOrder = false;
    }

    public LinkedHashMap(int n, float f, boolean bl) {
        super(n, f);
        this.accessOrder = bl;
    }

    public LinkedHashMap(Map<? extends K, ? extends V> map) {
        this.accessOrder = false;
        this.putMapEntries(map, false);
    }

    private void linkNodeLast(LinkedHashMapEntry<K, V> linkedHashMapEntry) {
        LinkedHashMapEntry<K, V> linkedHashMapEntry2 = this.tail;
        this.tail = linkedHashMapEntry;
        if (linkedHashMapEntry2 == null) {
            this.head = linkedHashMapEntry;
        } else {
            linkedHashMapEntry.before = linkedHashMapEntry2;
            linkedHashMapEntry2.after = linkedHashMapEntry;
        }
    }

    private void transferLinks(LinkedHashMapEntry<K, V> linkedHashMapEntry, LinkedHashMapEntry<K, V> linkedHashMapEntry2) {
        LinkedHashMapEntry linkedHashMapEntry3;
        linkedHashMapEntry2.before = linkedHashMapEntry3 = linkedHashMapEntry.before;
        linkedHashMapEntry = linkedHashMapEntry.after;
        linkedHashMapEntry2.after = linkedHashMapEntry;
        if (linkedHashMapEntry3 == null) {
            this.head = linkedHashMapEntry2;
        } else {
            linkedHashMapEntry3.after = linkedHashMapEntry2;
        }
        if (linkedHashMapEntry == null) {
            this.tail = linkedHashMapEntry2;
        } else {
            linkedHashMapEntry.before = linkedHashMapEntry2;
        }
    }

    @Override
    void afterNodeAccess(HashMap.Node<K, V> linkedHashMapEntry) {
        if (this.accessOrder) {
            LinkedHashMapEntry linkedHashMapEntry2;
            LinkedHashMapEntry linkedHashMapEntry3 = linkedHashMapEntry2 = this.tail;
            if (linkedHashMapEntry2 != linkedHashMapEntry) {
                linkedHashMapEntry2 = linkedHashMapEntry;
                linkedHashMapEntry = linkedHashMapEntry2.before;
                LinkedHashMapEntry linkedHashMapEntry4 = linkedHashMapEntry2.after;
                linkedHashMapEntry2.after = null;
                if (linkedHashMapEntry == null) {
                    this.head = linkedHashMapEntry4;
                } else {
                    linkedHashMapEntry.after = linkedHashMapEntry4;
                }
                if (linkedHashMapEntry4 != null) {
                    linkedHashMapEntry4.before = linkedHashMapEntry;
                    linkedHashMapEntry = linkedHashMapEntry3;
                }
                if (linkedHashMapEntry == null) {
                    this.head = linkedHashMapEntry2;
                } else {
                    linkedHashMapEntry2.before = linkedHashMapEntry;
                    linkedHashMapEntry.after = linkedHashMapEntry2;
                }
                this.tail = linkedHashMapEntry2;
                ++this.modCount;
            }
        }
    }

    @Override
    void afterNodeInsertion(boolean bl) {
        Object object;
        if (bl && (object = this.head) != null && this.removeEldestEntry((Map.Entry<K, V>)object)) {
            object = ((LinkedHashMapEntry)object).key;
            this.removeNode(LinkedHashMap.hash(object), object, null, false, true);
        }
    }

    @Override
    void afterNodeRemoval(HashMap.Node<K, V> linkedHashMapEntry) {
        linkedHashMapEntry = linkedHashMapEntry;
        LinkedHashMapEntry linkedHashMapEntry2 = linkedHashMapEntry.before;
        LinkedHashMapEntry linkedHashMapEntry3 = linkedHashMapEntry.after;
        linkedHashMapEntry.after = null;
        linkedHashMapEntry.before = null;
        if (linkedHashMapEntry2 == null) {
            this.head = linkedHashMapEntry3;
        } else {
            linkedHashMapEntry2.after = linkedHashMapEntry3;
        }
        if (linkedHashMapEntry3 == null) {
            this.tail = linkedHashMapEntry2;
        } else {
            linkedHashMapEntry3.before = linkedHashMapEntry2;
        }
    }

    @Override
    public void clear() {
        super.clear();
        this.tail = null;
        this.head = null;
    }

    @Override
    public boolean containsValue(Object object) {
        LinkedHashMapEntry<K, V> linkedHashMapEntry = this.head;
        while (linkedHashMapEntry != null) {
            Object object2 = linkedHashMapEntry.value;
            if (!(object2 == object || object != null && object.equals(object2))) {
                linkedHashMapEntry = linkedHashMapEntry.after;
                continue;
            }
            return true;
        }
        return false;
    }

    public Map.Entry<K, V> eldest() {
        return this.head;
    }

    @Override
    public Set<Map.Entry<K, V>> entrySet() {
        Set set;
        block0 : {
            set = this.entrySet;
            if (set != null) break block0;
            this.entrySet = set = new LinkedEntrySet();
        }
        return set;
    }

    @Override
    public void forEach(BiConsumer<? super K, ? super V> biConsumer) {
        if (biConsumer != null) {
            int n = this.modCount;
            LinkedHashMapEntry<K, V> linkedHashMapEntry = this.head;
            while (this.modCount == n && linkedHashMapEntry != null) {
                biConsumer.accept(linkedHashMapEntry.key, linkedHashMapEntry.value);
                linkedHashMapEntry = linkedHashMapEntry.after;
            }
            if (this.modCount == n) {
                return;
            }
            throw new ConcurrentModificationException();
        }
        throw new NullPointerException();
    }

    @Override
    public V get(Object node) {
        if ((node = this.getNode(LinkedHashMap.hash(node), node)) == null) {
            return null;
        }
        if (this.accessOrder) {
            this.afterNodeAccess(node);
        }
        return node.value;
    }

    @Override
    public V getOrDefault(Object node, V v) {
        if ((node = this.getNode(LinkedHashMap.hash(node), node)) == null) {
            return v;
        }
        if (this.accessOrder) {
            this.afterNodeAccess(node);
        }
        return node.value;
    }

    @Override
    void internalWriteEntries(ObjectOutputStream objectOutputStream) throws IOException {
        LinkedHashMapEntry<K, V> linkedHashMapEntry = this.head;
        while (linkedHashMapEntry != null) {
            objectOutputStream.writeObject(linkedHashMapEntry.key);
            objectOutputStream.writeObject(linkedHashMapEntry.value);
            linkedHashMapEntry = linkedHashMapEntry.after;
        }
    }

    @Override
    public Set<K> keySet() {
        Set set;
        Set set2 = set = this.keySet;
        if (set == null) {
            this.keySet = set2 = new LinkedKeySet();
        }
        return set2;
    }

    @Override
    HashMap.Node<K, V> newNode(int n, K object, V v, HashMap.Node<K, V> node) {
        object = new LinkedHashMapEntry<K, V>(n, object, v, node);
        this.linkNodeLast((LinkedHashMapEntry<K, V>)object);
        return object;
    }

    @Override
    HashMap.TreeNode<K, V> newTreeNode(int n, K object, V v, HashMap.Node<K, V> node) {
        object = new HashMap.TreeNode<K, V>(n, object, v, node);
        this.linkNodeLast((LinkedHashMapEntry<K, V>)object);
        return object;
    }

    @Override
    void reinitialize() {
        super.reinitialize();
        this.tail = null;
        this.head = null;
    }

    protected boolean removeEldestEntry(Map.Entry<K, V> entry) {
        return false;
    }

    @Override
    public void replaceAll(BiFunction<? super K, ? super V, ? extends V> biFunction) {
        if (biFunction != null) {
            int n = this.modCount;
            LinkedHashMapEntry<K, V> linkedHashMapEntry = this.head;
            while (this.modCount == n && linkedHashMapEntry != null) {
                linkedHashMapEntry.value = biFunction.apply(linkedHashMapEntry.key, linkedHashMapEntry.value);
                linkedHashMapEntry = linkedHashMapEntry.after;
            }
            if (this.modCount == n) {
                return;
            }
            throw new ConcurrentModificationException();
        }
        throw new NullPointerException();
    }

    @Override
    HashMap.Node<K, V> replacementNode(HashMap.Node<K, V> linkedHashMapEntry, HashMap.Node<K, V> node) {
        linkedHashMapEntry = linkedHashMapEntry;
        node = new LinkedHashMapEntry<Object, Object>(linkedHashMapEntry.hash, linkedHashMapEntry.key, linkedHashMapEntry.value, node);
        this.transferLinks(linkedHashMapEntry, (LinkedHashMapEntry<K, V>)node);
        return node;
    }

    @Override
    HashMap.TreeNode<K, V> replacementTreeNode(HashMap.Node<K, V> linkedHashMapEntry, HashMap.Node<K, V> node) {
        linkedHashMapEntry = linkedHashMapEntry;
        node = new HashMap.TreeNode<Object, Object>(linkedHashMapEntry.hash, linkedHashMapEntry.key, linkedHashMapEntry.value, node);
        this.transferLinks(linkedHashMapEntry, (LinkedHashMapEntry<K, V>)node);
        return node;
    }

    @Override
    public Collection<V> values() {
        Collection collection;
        Collection collection2 = collection = this.values;
        if (collection == null) {
            this.values = collection2 = new LinkedValues();
        }
        return collection2;
    }

    final class LinkedEntryIterator
    extends LinkedHashMap<K, V>
    implements Iterator<Map.Entry<K, V>> {
        LinkedEntryIterator() {
        }

        @Override
        public final Map.Entry<K, V> next() {
            return this.nextNode();
        }
    }

    final class LinkedEntrySet
    extends AbstractSet<Map.Entry<K, V>> {
        LinkedEntrySet() {
        }

        @Override
        public final void clear() {
            LinkedHashMap.this.clear();
        }

        @Override
        public final boolean contains(Object object) {
            boolean bl = object instanceof Map.Entry;
            boolean bl2 = false;
            if (!bl) {
                return false;
            }
            object = (Map.Entry)object;
            Object object2 = object.getKey();
            object2 = LinkedHashMap.this.getNode(HashMap.hash(object2), object2);
            bl = bl2;
            if (object2 != null) {
                bl = bl2;
                if (((HashMap.Node)object2).equals(object)) {
                    bl = true;
                }
            }
            return bl;
        }

        @Override
        public final void forEach(Consumer<? super Map.Entry<K, V>> consumer) {
            if (consumer != null) {
                int n = LinkedHashMap.this.modCount;
                LinkedHashMapEntry linkedHashMapEntry = LinkedHashMap.this.head;
                while (linkedHashMapEntry != null && n == LinkedHashMap.this.modCount) {
                    consumer.accept(linkedHashMapEntry);
                    linkedHashMapEntry = linkedHashMapEntry.after;
                }
                if (LinkedHashMap.this.modCount == n) {
                    return;
                }
                throw new ConcurrentModificationException();
            }
            throw new NullPointerException();
        }

        @Override
        public final Iterator<Map.Entry<K, V>> iterator() {
            return new LinkedEntryIterator();
        }

        @Override
        public final boolean remove(Object object) {
            boolean bl = object instanceof Map.Entry;
            boolean bl2 = false;
            if (bl) {
                Map.Entry entry = (Map.Entry)object;
                object = entry.getKey();
                entry = entry.getValue();
                if (LinkedHashMap.this.removeNode(HashMap.hash(object), object, entry, true, true) != null) {
                    bl2 = true;
                }
                return bl2;
            }
            return false;
        }

        @Override
        public final int size() {
            return LinkedHashMap.this.size;
        }

        @Override
        public final Spliterator<Map.Entry<K, V>> spliterator() {
            return Spliterators.spliterator(this, 81);
        }
    }

    abstract class LinkedHashIterator {
        LinkedHashMapEntry<K, V> current;
        int expectedModCount;
        LinkedHashMapEntry<K, V> next;

        LinkedHashIterator() {
            this.next = LinkedHashMap.this.head;
            this.expectedModCount = LinkedHashMap.this.modCount;
            this.current = null;
        }

        public final boolean hasNext() {
            boolean bl = this.next != null;
            return bl;
        }

        final LinkedHashMapEntry<K, V> nextNode() {
            LinkedHashMapEntry<K, V> linkedHashMapEntry = this.next;
            if (LinkedHashMap.this.modCount == this.expectedModCount) {
                if (linkedHashMapEntry != null) {
                    this.current = linkedHashMapEntry;
                    this.next = linkedHashMapEntry.after;
                    return linkedHashMapEntry;
                }
                throw new NoSuchElementException();
            }
            throw new ConcurrentModificationException();
        }

        public final void remove() {
            LinkedHashMapEntry<K, V> linkedHashMapEntry = this.current;
            if (linkedHashMapEntry != null) {
                if (LinkedHashMap.this.modCount == this.expectedModCount) {
                    this.current = null;
                    linkedHashMapEntry = linkedHashMapEntry.key;
                    LinkedHashMap.this.removeNode(HashMap.hash(linkedHashMapEntry), linkedHashMapEntry, null, false, false);
                    this.expectedModCount = LinkedHashMap.this.modCount;
                    return;
                }
                throw new ConcurrentModificationException();
            }
            throw new IllegalStateException();
        }
    }

    static class LinkedHashMapEntry<K, V>
    extends HashMap.Node<K, V> {
        LinkedHashMapEntry<K, V> after;
        LinkedHashMapEntry<K, V> before;

        LinkedHashMapEntry(int n, K k, V v, HashMap.Node<K, V> node) {
            super(n, k, v, node);
        }
    }

    final class LinkedKeyIterator
    extends LinkedHashMap<K, V>
    implements Iterator<K> {
        LinkedKeyIterator() {
        }

        @Override
        public final K next() {
            return this.nextNode().getKey();
        }
    }

    final class LinkedKeySet
    extends AbstractSet<K> {
        LinkedKeySet() {
        }

        @Override
        public final void clear() {
            LinkedHashMap.this.clear();
        }

        @Override
        public final boolean contains(Object object) {
            return LinkedHashMap.this.containsKey(object);
        }

        @Override
        public final void forEach(Consumer<? super K> consumer) {
            if (consumer != null) {
                int n = LinkedHashMap.this.modCount;
                LinkedHashMapEntry linkedHashMapEntry = LinkedHashMap.this.head;
                while (linkedHashMapEntry != null && LinkedHashMap.this.modCount == n) {
                    consumer.accept(linkedHashMapEntry.key);
                    linkedHashMapEntry = linkedHashMapEntry.after;
                }
                if (LinkedHashMap.this.modCount == n) {
                    return;
                }
                throw new ConcurrentModificationException();
            }
            throw new NullPointerException();
        }

        @Override
        public final Iterator<K> iterator() {
            return new LinkedKeyIterator();
        }

        @Override
        public final boolean remove(Object object) {
            boolean bl = LinkedHashMap.this.removeNode(HashMap.hash(object), object, null, false, true) != null;
            return bl;
        }

        @Override
        public final int size() {
            return LinkedHashMap.this.size;
        }

        @Override
        public final Spliterator<K> spliterator() {
            return Spliterators.spliterator(this, 81);
        }
    }

    final class LinkedValueIterator
    extends LinkedHashMap<K, V>
    implements Iterator<V> {
        LinkedValueIterator() {
        }

        @Override
        public final V next() {
            return (V)this.nextNode().value;
        }
    }

    final class LinkedValues
    extends AbstractCollection<V> {
        LinkedValues() {
        }

        @Override
        public final void clear() {
            LinkedHashMap.this.clear();
        }

        @Override
        public final boolean contains(Object object) {
            return LinkedHashMap.this.containsValue(object);
        }

        @Override
        public final void forEach(Consumer<? super V> consumer) {
            if (consumer != null) {
                int n = LinkedHashMap.this.modCount;
                LinkedHashMapEntry linkedHashMapEntry = LinkedHashMap.this.head;
                while (linkedHashMapEntry != null && LinkedHashMap.this.modCount == n) {
                    consumer.accept(linkedHashMapEntry.value);
                    linkedHashMapEntry = linkedHashMapEntry.after;
                }
                if (LinkedHashMap.this.modCount == n) {
                    return;
                }
                throw new ConcurrentModificationException();
            }
            throw new NullPointerException();
        }

        @Override
        public final Iterator<V> iterator() {
            return new LinkedValueIterator();
        }

        @Override
        public final int size() {
            return LinkedHashMap.this.size;
        }

        @Override
        public final Spliterator<V> spliterator() {
            return Spliterators.spliterator(this, 80);
        }
    }

}

