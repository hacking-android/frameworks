/*
 * Decompiled with CFR 0.145.
 */
package java.util;

import java.io.IOException;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.AbstractCollection;
import java.util.AbstractMap;
import java.util.AbstractSet;
import java.util.Collection;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Set;
import java.util.Spliterator;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

public class HashMap<K, V>
extends AbstractMap<K, V>
implements Map<K, V>,
Cloneable,
Serializable {
    static final int DEFAULT_INITIAL_CAPACITY = 16;
    static final float DEFAULT_LOAD_FACTOR = 0.75f;
    static final int MAXIMUM_CAPACITY = 1073741824;
    static final int MIN_TREEIFY_CAPACITY = 64;
    static final int TREEIFY_THRESHOLD = 8;
    static final int UNTREEIFY_THRESHOLD = 6;
    private static final long serialVersionUID = 362498820763181265L;
    transient Set<Map.Entry<K, V>> entrySet;
    final float loadFactor;
    transient int modCount;
    transient int size;
    transient Node<K, V>[] table;
    int threshold;

    public HashMap() {
        this.loadFactor = 0.75f;
    }

    public HashMap(int n) {
        this(n, 0.75f);
    }

    public HashMap(int n, float f) {
        if (n >= 0) {
            int n2 = n;
            if (n > 1073741824) {
                n2 = 1073741824;
            }
            if (!(f <= 0.0f) && !Float.isNaN(f)) {
                this.loadFactor = f;
                this.threshold = HashMap.tableSizeFor(n2);
                return;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Illegal load factor: ");
            stringBuilder.append(f);
            throw new IllegalArgumentException(stringBuilder.toString());
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Illegal initial capacity: ");
        stringBuilder.append(n);
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    public HashMap(Map<? extends K, ? extends V> map) {
        this.loadFactor = 0.75f;
        this.putMapEntries(map, false);
    }

    static Class<?> comparableClassFor(Object arrtype) {
        if (arrtype instanceof Comparable) {
            Class<?> class_ = arrtype.getClass();
            if (class_ == String.class) {
                return class_;
            }
            arrtype = class_.getGenericInterfaces();
            if (arrtype != null) {
                for (int i = 0; i < arrtype.length; ++i) {
                    Type[] arrtype2 = arrtype[i];
                    if (!(arrtype2 instanceof ParameterizedType) || (arrtype2 = (ParameterizedType)arrtype2).getRawType() != Comparable.class || (arrtype2 = arrtype2.getActualTypeArguments()) == null || arrtype2.length != 1 || arrtype2[0] != class_) continue;
                    return class_;
                }
            }
        }
        return null;
    }

    static int compareComparables(Class<?> class_, Object object, Object object2) {
        int n = object2 != null && object2.getClass() == class_ ? ((Comparable)object).compareTo(object2) : 0;
        return n;
    }

    static final int hash(Object object) {
        int n;
        if (object == null) {
            n = 0;
        } else {
            n = object.hashCode();
            n ^= n >>> 16;
        }
        return n;
    }

    private void readObject(ObjectInputStream object) throws IOException, ClassNotFoundException {
        ((ObjectInputStream)object).defaultReadObject();
        this.reinitialize();
        float f = this.loadFactor;
        if (!(f <= 0.0f) && !Float.isNaN(f)) {
            ((ObjectInputStream)object).readInt();
            int n = ((ObjectInputStream)object).readInt();
            if (n >= 0) {
                if (n > 0) {
                    float f2 = Math.min(Math.max(0.25f, this.loadFactor), 4.0f);
                    f = (float)n / f2 + 1.0f;
                    int n2 = f < 16.0f ? 16 : (f >= 1.07374182E9f ? 1073741824 : HashMap.tableSizeFor((int)f));
                    f = (float)n2 * f2;
                    int n3 = n2 < 1073741824 && f < 1.07374182E9f ? (int)f : Integer.MAX_VALUE;
                    this.threshold = n3;
                    this.table = new Node[n2];
                    for (n2 = 0; n2 < n; ++n2) {
                        Object object2 = ((ObjectInputStream)object).readObject();
                        Object object3 = ((ObjectInputStream)object).readObject();
                        this.putVal(HashMap.hash(object2), object2, object3, false, false);
                    }
                }
                return;
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("Illegal mappings count: ");
            ((StringBuilder)object).append(n);
            throw new InvalidObjectException(((StringBuilder)object).toString());
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Illegal load factor: ");
        ((StringBuilder)object).append(this.loadFactor);
        throw new InvalidObjectException(((StringBuilder)object).toString());
    }

    static final int tableSizeFor(int n) {
        --n;
        n |= n >>> 1;
        n |= n >>> 2;
        n |= n >>> 4;
        n |= n >>> 8;
        int n2 = n | n >>> 16;
        n = 1073741824;
        if (n2 < 0) {
            n = 1;
        } else if (n2 < 1073741824) {
            n = n2 + 1;
        }
        return n;
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        int n = this.capacity();
        objectOutputStream.defaultWriteObject();
        objectOutputStream.writeInt(n);
        objectOutputStream.writeInt(this.size);
        this.internalWriteEntries(objectOutputStream);
    }

    void afterNodeAccess(Node<K, V> node) {
    }

    void afterNodeInsertion(boolean bl) {
    }

    void afterNodeRemoval(Node<K, V> node) {
    }

    final int capacity() {
        int n;
        Node<K, V>[] arrnode = this.table;
        if (arrnode != null) {
            n = arrnode.length;
        } else {
            n = this.threshold;
            if (n <= 0) {
                n = 16;
            }
        }
        return n;
    }

    @Override
    public void clear() {
        ++this.modCount;
        Node<K, V>[] arrnode = this.table;
        if (arrnode != null && this.size > 0) {
            this.size = 0;
            for (int i = 0; i < arrnode.length; ++i) {
                arrnode[i] = null;
            }
        }
    }

    @Override
    public Object clone() {
        try {
            HashMap hashMap = (HashMap)super.clone();
            hashMap.reinitialize();
            hashMap.putMapEntries(this, false);
            return hashMap;
        }
        catch (CloneNotSupportedException cloneNotSupportedException) {
            throw new InternalError(cloneNotSupportedException);
        }
    }

    @Override
    public V compute(K k, BiFunction<? super K, ? super V, ? extends V> biFunction) {
        if (biFunction != null) {
            Node node;
            Node<K, V>[] arrnode;
            int n;
            int n2;
            Node node2;
            Node node3;
            block16 : {
                int n3;
                n = HashMap.hash(k);
                int n4 = 0;
                if (this.size > this.threshold || (arrnode = this.table) == null || (n2 = arrnode.length) == 0) {
                    arrnode = this.resize();
                    n2 = arrnode.length;
                }
                if ((node = arrnode[n3 = n2 - 1 & n]) != null) {
                    if (node instanceof TreeNode) {
                        node3 = (TreeNode)node;
                        node2 = ((TreeNode)node3).getTreeNode(n, k);
                        n2 = 0;
                    } else {
                        node2 = node;
                        n2 = n4;
                        do {
                            if (node2.hash == n && ((node3 = node2.key) == k || k != null && k.equals(node3))) {
                                node3 = null;
                                break block16;
                            }
                            n4 = n2 + 1;
                            node2 = node3 = node2.next;
                            n2 = n4;
                        } while (node3 != null);
                        n2 = n4;
                        node3 = null;
                        node2 = null;
                    }
                } else {
                    n2 = 0;
                    node3 = null;
                    node2 = null;
                }
            }
            Object u = node2 == null ? null : (Object)node2.value;
            biFunction = biFunction.apply(k, u);
            if (node2 != null) {
                if (biFunction != null) {
                    node2.value = biFunction;
                    this.afterNodeAccess(node2);
                } else {
                    this.removeNode(n, k, null, false, true);
                }
            } else if (biFunction != null) {
                if (node3 != null) {
                    ((TreeNode)node3).putTreeVal(this, arrnode, n, k, biFunction);
                } else {
                    arrnode[n3] = this.newNode(n, k, biFunction, node);
                    if (n2 >= 7) {
                        this.treeifyBin(arrnode, n);
                    }
                }
                ++this.modCount;
                ++this.size;
                this.afterNodeInsertion(true);
            }
            return (V)biFunction;
        }
        throw new NullPointerException();
    }

    @Override
    public V computeIfAbsent(K k, Function<? super K, ? extends V> function) {
        if (function != null) {
            Node node;
            int n;
            Node<K, V>[] arrnode;
            TreeNode<K, Function<? super K, ? extends V>> treeNode;
            int n2;
            int n3 = HashMap.hash(k);
            int n4 = 0;
            int n5 = 0;
            TreeNode<K, Function<? super K, ? extends V>> treeNode2 = null;
            TreeNode<K, Function<Object, Object>> treeNode3 = null;
            if (this.size > this.threshold || (arrnode = this.table) == null || (n2 = arrnode.length) == 0) {
                arrnode = this.resize();
                n2 = arrnode.length;
            }
            if ((treeNode = arrnode[n = n2 - 1 & n3]) != null) {
                if (treeNode instanceof TreeNode) {
                    treeNode2 = node = (TreeNode<K, Function<? super K, ? extends V>>)treeNode;
                    node = node.getTreeNode(n3, k);
                    n2 = n4;
                } else {
                    Object object;
                    node = treeNode;
                    n2 = n5;
                    while (node.hash != n3 || (object = node.key) != k && (k == null || !k.equals(object))) {
                        n5 = n2 + 1;
                        object = node.next;
                        node = object;
                        n2 = n5;
                        if (object != null) continue;
                        node = treeNode3;
                        n2 = n5;
                        break;
                    }
                }
                if (node != null && (treeNode3 = node.value) != null) {
                    this.afterNodeAccess(node);
                    return (V)treeNode3;
                }
                treeNode3 = node;
                node = treeNode2;
            } else {
                n2 = 0;
                node = null;
                treeNode3 = null;
            }
            function = function.apply(k);
            if (function == null) {
                return null;
            }
            if (treeNode3 != null) {
                treeNode3.value = function;
                this.afterNodeAccess(treeNode3);
                return (V)function;
            }
            if (node != null) {
                node.putTreeVal(this, arrnode, n3, k, function);
            } else {
                arrnode[n] = this.newNode(n3, k, function, treeNode);
                if (n2 >= 7) {
                    this.treeifyBin(arrnode, n3);
                }
            }
            ++this.modCount;
            ++this.size;
            this.afterNodeInsertion(true);
            return (V)function;
        }
        throw new NullPointerException();
    }

    @Override
    public V computeIfPresent(K k, BiFunction<? super K, ? super V, ? extends V> biFunction) {
        if (biFunction != null) {
            Object v;
            int n = HashMap.hash(k);
            Node<K, V> node = this.getNode(n, k);
            if (node != null && (v = node.value) != null) {
                if ((biFunction = biFunction.apply(k, v)) != null) {
                    node.value = biFunction;
                    this.afterNodeAccess(node);
                    return (V)biFunction;
                }
                this.removeNode(n, k, null, false, true);
            }
            return null;
        }
        throw new NullPointerException();
    }

    @Override
    public boolean containsKey(Object object) {
        boolean bl = this.getNode(HashMap.hash(object), object) != null;
        return bl;
    }

    @Override
    public boolean containsValue(Object object) {
        Node<K, V>[] arrnode = this.table;
        if (arrnode != null && this.size > 0) {
            for (int i = 0; i < arrnode.length; ++i) {
                Node<K, V> node = arrnode[i];
                while (node != null) {
                    Object v = node.value;
                    if (!(v == object || object != null && object.equals(v))) {
                        node = node.next;
                        continue;
                    }
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public Set<Map.Entry<K, V>> entrySet() {
        EntrySet entrySet;
        block0 : {
            entrySet = this.entrySet;
            if (entrySet != null) break block0;
            this.entrySet = entrySet = new EntrySet();
        }
        return entrySet;
    }

    @Override
    public void forEach(BiConsumer<? super K, ? super V> biConsumer) {
        if (biConsumer != null) {
            Node<K, V>[] arrnode;
            if (this.size > 0 && (arrnode = this.table) != null) {
                int n = this.modCount;
                for (int i = 0; i < arrnode.length && n == this.modCount; ++i) {
                    Node<K, V> node = arrnode[i];
                    while (node != null) {
                        biConsumer.accept(node.key, node.value);
                        node = node.next;
                    }
                }
                if (this.modCount != n) {
                    throw new ConcurrentModificationException();
                }
            }
            return;
        }
        throw new NullPointerException();
    }

    @Override
    public V get(Object node) {
        node = (node = this.getNode(HashMap.hash(node), node)) == null ? null : node.value;
        return (V)node;
    }

    final Node<K, V> getNode(int n, Object object) {
        int n2;
        Node<K, V> node;
        Object object2 = this.table;
        if (object2 != null && (n2 = ((Node<K, V>[])object2).length) > 0 && (node = object2[n2 - 1 & n]) != null) {
            if (node.hash == n && ((object2 = node.key) == object || object != null && object.equals(object2))) {
                return node;
            }
            Node node2 = node.next;
            object2 = node2;
            if (node2 != null) {
                if (node instanceof TreeNode) {
                    return ((TreeNode)node).getTreeNode(n, object);
                }
                do {
                    if (object2.hash == n && ((node2 = object2.key) == object || object != null && object.equals(node2))) {
                        return object2;
                    }
                    node2 = object2.next;
                    object2 = node2;
                } while (node2 != null);
            }
        }
        return null;
    }

    @Override
    public V getOrDefault(Object node, V v) {
        if ((node = this.getNode(HashMap.hash(node), node)) != null) {
            v = node.value;
        }
        return v;
    }

    void internalWriteEntries(ObjectOutputStream objectOutputStream) throws IOException {
        Node<K, V>[] arrnode;
        if (this.size > 0 && (arrnode = this.table) != null) {
            for (int i = 0; i < arrnode.length; ++i) {
                Node<K, V> node = arrnode[i];
                while (node != null) {
                    objectOutputStream.writeObject(node.key);
                    objectOutputStream.writeObject(node.value);
                    node = node.next;
                }
            }
        }
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

    final float loadFactor() {
        return this.loadFactor;
    }

    @Override
    public V merge(K k, V v, BiFunction<? super V, ? super V, ? extends V> biFunction) {
        if (v != null) {
            if (biFunction != null) {
                int n;
                Node node;
                Node<K, V>[] arrnode;
                Node node2;
                int n2;
                block16 : {
                    int n3;
                    Node node3;
                    n = HashMap.hash(k);
                    int n4 = 0;
                    if (this.size > this.threshold || (arrnode = this.table) == null || (n2 = arrnode.length) == 0) {
                        arrnode = this.resize();
                        n2 = arrnode.length;
                    }
                    if ((node3 = arrnode[n3 = n2 - 1 & n]) != null) {
                        if (node3 instanceof TreeNode) {
                            node2 = node3;
                            node = node2.getTreeNode(n, k);
                            n2 = 0;
                        } else {
                            node = node3;
                            n2 = n4;
                            do {
                                if (node.hash == n && ((node2 = node.key) == k || k != null && k.equals(node2))) {
                                    node2 = null;
                                    break block16;
                                }
                                n4 = n2 + 1;
                                node = node2 = node.next;
                                n2 = n4;
                            } while (node2 != null);
                            n2 = n4;
                            node2 = null;
                            node = null;
                        }
                    } else {
                        n2 = 0;
                        node2 = null;
                        node = null;
                    }
                }
                if (node != null) {
                    if (node.value != null) {
                        v = biFunction.apply(node.value, v);
                    }
                    if (v != null) {
                        node.value = v;
                        this.afterNodeAccess(node);
                    } else {
                        this.removeNode(n, k, null, false, true);
                    }
                    return v;
                }
                if (node2 != null) {
                    node2.putTreeVal(this, arrnode, n, k, v);
                } else {
                    Node node3;
                    arrnode[n3] = this.newNode(n, k, v, node3);
                    if (n2 >= 7) {
                        this.treeifyBin(arrnode, n);
                    }
                }
                ++this.modCount;
                ++this.size;
                this.afterNodeInsertion(true);
                return v;
            }
            throw new NullPointerException();
        }
        throw new NullPointerException();
    }

    Node<K, V> newNode(int n, K k, V v, Node<K, V> node) {
        return new Node<K, V>(n, k, v, node);
    }

    TreeNode<K, V> newTreeNode(int n, K k, V v, Node<K, V> node) {
        return new TreeNode<K, V>(n, k, v, node);
    }

    @Override
    public V put(K k, V v) {
        return this.putVal(HashMap.hash(k), k, v, false, true);
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> map) {
        this.putMapEntries(map, true);
    }

    @Override
    public V putIfAbsent(K k, V v) {
        return this.putVal(HashMap.hash(k), k, v, true, true);
    }

    final void putMapEntries(Map<? extends K, ? extends V> map, boolean bl) {
        int n = map.size();
        if (n > 0) {
            if (this.table == null) {
                float f = (float)n / this.loadFactor + 1.0f;
                if ((n = f < 1.07374182E9f ? (int)f : 1073741824) > this.threshold) {
                    this.threshold = HashMap.tableSizeFor(n);
                }
            } else if (n > this.threshold) {
                this.resize();
            }
            for (Map.Entry<K, V> entry : map.entrySet()) {
                map = entry.getKey();
                entry = entry.getValue();
                this.putVal(HashMap.hash(map), map, entry, false, bl);
            }
        }
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    final V putVal(int var1_1, K var2_2, V var3_3, boolean var4_4, boolean var5_5) {
        block11 : {
            block10 : {
                block9 : {
                    var6_6 = this.table;
                    if (var6_6 == null || (var7_7 = var6_6.length) == 0) {
                        var6_6 = this.resize();
                        var7_7 = var6_6.length;
                    }
                    var7_7 = var7_7 - 1 & var1_1;
                    var9_16 = var8_8 = var6_6[var7_7];
                    if (var8_8 != null) break block9;
                    var6_6[var7_7] = this.newNode(var1_1, var2_2, var3_3, null);
                    ** GOTO lbl38
                }
                if (var9_16.hash != var1_1 || (var8_9 = var9_16.key) != var2_2 && (var2_2 == null || !var2_2.equals(var8_9))) break block10;
                var2_2 = var9_16;
                ** GOTO lbl31
            }
            if (!(var9_16 instanceof TreeNode)) break block11;
            var2_2 = ((TreeNode)var9_16).putTreeVal(this, var6_6, var1_1, var2_2, var3_3);
            ** GOTO lbl31
        }
        var7_7 = 0;
        var8_11 = var9_16;
        do {
            block14 : {
                block13 : {
                    block12 : {
                        if ((var9_18 = var8_12.next) != null) break block12;
                        var8_12.next = this.newNode(var1_1, var2_2, var3_3, null);
                        if (var7_7 >= 7) {
                            this.treeifyBin(var6_6, var1_1);
                        }
                        break block13;
                    }
                    if (var9_18.hash != var1_1 || (var8_13 = var9_18.key) != var2_2 && (var2_2 == null || !var2_2.equals(var8_13))) break block14;
                }
                var2_2 = var9_18;
lbl31: // 3 sources:
                if (var2_2 != null) {
                    var9_19 = var2_2.value;
                    if (!var4_4 || var9_19 == null) {
                        var2_2.value = var3_3;
                    }
                    this.afterNodeAccess((Node<K, V>)var2_2);
                    return var9_19;
                }
lbl38: // 3 sources:
                ++this.modCount;
                this.size = var1_1 = this.size + 1;
                if (var1_1 > this.threshold) {
                    this.resize();
                }
                this.afterNodeInsertion(var5_5);
                return null;
            }
            ++var7_7;
            var8_15 = var9_18;
        } while (true);
    }

    void reinitialize() {
        this.table = null;
        this.entrySet = null;
        this.keySet = null;
        this.values = null;
        this.modCount = 0;
        this.threshold = 0;
        this.size = 0;
    }

    @Override
    public V remove(Object node) {
        node = (node = this.removeNode(HashMap.hash(node), node, null, false, true)) == null ? null : node.value;
        return (V)node;
    }

    @Override
    public boolean remove(Object object, Object object2) {
        boolean bl = this.removeNode(HashMap.hash(object), object, object2, true, true) != null;
        return bl;
    }

    final Node<K, V> removeNode(int n, Object object, Object object2, boolean bl, boolean bl2) {
        int n2;
        Node<K, V>[] arrnode = this.table;
        if (arrnode != null && (n2 = arrnode.length) > 0) {
            Node<K, V> node;
            n2 = n2 - 1 & n;
            Node<K, V> node2 = node = arrnode[n2];
            if (node != null) {
                Node<K, V> node3;
                Object var10_10 = null;
                if (node2.hash == n && ((node = node2.key) == object || object != null && object.equals(node))) {
                    node = node2;
                    node3 = node2;
                } else {
                    Node node4;
                    Node node5 = node4 = node2.next;
                    node = var10_10;
                    node3 = node2;
                    if (node4 != null) {
                        node3 = node2;
                        node = node5;
                        if (node2 instanceof TreeNode) {
                            node = ((TreeNode)node2).getTreeNode(n, object);
                            node3 = node2;
                        } else {
                            while (node.hash != n || (node2 = node.key) != object && (object == null || !object.equals(node2))) {
                                node2 = node;
                                node5 = node.next;
                                node = node5;
                                node3 = node2;
                                if (node5 != null) continue;
                                node3 = node2;
                                node = var10_10;
                                break;
                            }
                        }
                    }
                }
                if (node != null && (!bl || (object = node.value) == object2 || object2 != null && object2.equals(object))) {
                    if (node instanceof TreeNode) {
                        ((TreeNode)node).removeTreeNode(this, arrnode, bl2);
                    } else if (node == node3) {
                        arrnode[n2] = node.next;
                    } else {
                        node3.next = node.next;
                    }
                    ++this.modCount;
                    --this.size;
                    this.afterNodeRemoval(node);
                    return node;
                }
            }
        }
        return null;
    }

    @Override
    public V replace(K object, V v) {
        if ((object = this.getNode(HashMap.hash(object), object)) != null) {
            Object v2 = ((Node)object).value;
            ((Node)object).value = v;
            this.afterNodeAccess((Node<K, V>)object);
            return v2;
        }
        return null;
    }

    @Override
    public boolean replace(K object, V v, V v2) {
        Node<K, V> node = this.getNode(HashMap.hash(object), object);
        if (node != null && ((object = node.value) == v || object != null && object.equals(v))) {
            node.value = v2;
            this.afterNodeAccess(node);
            return true;
        }
        return false;
    }

    @Override
    public void replaceAll(BiFunction<? super K, ? super V, ? extends V> biFunction) {
        if (biFunction != null) {
            Node<K, V>[] arrnode;
            if (this.size > 0 && (arrnode = this.table) != null) {
                int n = this.modCount;
                for (int i = 0; i < arrnode.length; ++i) {
                    Node<K, V> node = arrnode[i];
                    while (node != null) {
                        node.value = biFunction.apply(node.key, node.value);
                        node = node.next;
                    }
                }
                if (this.modCount != n) {
                    throw new ConcurrentModificationException();
                }
            }
            return;
        }
        throw new NullPointerException();
    }

    Node<K, V> replacementNode(Node<K, V> node, Node<K, V> node2) {
        return new Node(node.hash, node.key, node.value, node2);
    }

    TreeNode<K, V> replacementTreeNode(Node<K, V> node, Node<K, V> node2) {
        return new TreeNode(node.hash, node.key, node.value, node2);
    }

    final Node<K, V>[] resize() {
        int n;
        int n2;
        int n3;
        Node<K, V>[] arrnode = this.table;
        int n4 = arrnode == null ? 0 : arrnode.length;
        int n5 = this.threshold;
        int n6 = 0;
        int n7 = Integer.MAX_VALUE;
        if (n4 > 0) {
            int n8;
            if (n4 >= 1073741824) {
                this.threshold = Integer.MAX_VALUE;
                return arrnode;
            }
            n2 = n8 = n4 << 1;
            n = n6;
            n3 = n2;
            if (n8 < 1073741824) {
                n = n6;
                n3 = n2;
                if (n4 >= 16) {
                    n = n5 << 1;
                    n3 = n2;
                }
            }
        } else if (n5 > 0) {
            n3 = n5;
            n = n6;
        } else {
            n3 = 16;
            n = 12;
        }
        n2 = n;
        if (n == 0) {
            float f = (float)n3 * this.loadFactor;
            n = n3 < 1073741824 && f < 1.07374182E9f ? (int)f : n7;
            n2 = n;
        }
        this.threshold = n2;
        Node[] arrnode2 = new Node[n3];
        this.table = arrnode2;
        if (arrnode != null) {
            for (n = 0; n < n4; ++n) {
                Node<K, V> node;
                Node node2;
                Node<K, V> node3;
                Node<K, V> node4;
                Node<K, V> node5;
                Node<K, V> node6;
                Node<K, V> node7 = node = arrnode[n];
                if (node == null) continue;
                arrnode[n] = null;
                if (node7.next == null) {
                    arrnode2[node7.hash & n3 - 1] = node7;
                    continue;
                }
                if (node7 instanceof TreeNode) {
                    ((TreeNode)node7).split(this, arrnode2, n, n4);
                    continue;
                }
                Node<K, V> node8 = null;
                Node<K, V> node9 = null;
                node = null;
                Node<K, V> node10 = null;
                do {
                    node2 = node7.next;
                    if ((node7.hash & n4) == 0) {
                        if (node9 == null) {
                            node8 = node7;
                        } else {
                            node9.next = node7;
                        }
                        node4 = node8;
                        node3 = node7;
                        node6 = node;
                        node5 = node10;
                    } else {
                        if (node10 == null) {
                            node = node7;
                        } else {
                            node10.next = node7;
                        }
                        node5 = node7;
                        node6 = node;
                        node3 = node9;
                        node4 = node8;
                    }
                    node7 = node2;
                    node8 = node4;
                    node9 = node3;
                    node = node6;
                    node10 = node5;
                } while (node2 != null);
                if (node3 != null) {
                    node3.next = null;
                    arrnode2[n] = node4;
                }
                if (node5 == null) continue;
                node5.next = null;
                arrnode2[n + n4] = node6;
            }
        }
        return arrnode2;
    }

    @Override
    public int size() {
        return this.size;
    }

    final void treeifyBin(Node<K, V>[] arrnode, int n) {
        int n2;
        if (arrnode != null && (n2 = arrnode.length) >= 64) {
            Node<K, V> node;
            n = n2 - 1 & n;
            Node<K, V> node2 = node = arrnode[n];
            if (node != null) {
                Node node3;
                Node node4;
                node = null;
                TreeNode<K, V> treeNode = null;
                do {
                    node3 = this.replacementTreeNode(node2, null);
                    if (treeNode == null) {
                        node4 = node3;
                    } else {
                        node3.prev = treeNode;
                        treeNode.next = node3;
                        node4 = node;
                    }
                    treeNode = node3;
                    node3 = node2.next;
                    node2 = node3;
                    node = node4;
                } while (node3 != null);
                arrnode[n] = node4;
                if (node4 != null) {
                    ((TreeNode)node4).treeify(arrnode);
                }
            }
        } else {
            this.resize();
        }
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

    final class EntryIterator
    extends HashMap<K, V>
    implements Iterator<Map.Entry<K, V>> {
        EntryIterator() {
        }

        @Override
        public final Map.Entry<K, V> next() {
            return this.nextNode();
        }
    }

    final class EntrySet
    extends AbstractSet<Map.Entry<K, V>> {
        EntrySet() {
        }

        @Override
        public final void clear() {
            HashMap.this.clear();
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
            object2 = HashMap.this.getNode(HashMap.hash(object2), object2);
            bl = bl2;
            if (object2 != null) {
                bl = bl2;
                if (((Node)object2).equals(object)) {
                    bl = true;
                }
            }
            return bl;
        }

        @Override
        public final void forEach(Consumer<? super Map.Entry<K, V>> consumer) {
            if (consumer != null) {
                Node<K, V>[] arrnode;
                if (HashMap.this.size > 0 && (arrnode = HashMap.this.table) != null) {
                    int n = HashMap.this.modCount;
                    for (int i = 0; i < arrnode.length && HashMap.this.modCount == n; ++i) {
                        Node node = arrnode[i];
                        while (node != null) {
                            consumer.accept(node);
                            node = node.next;
                        }
                    }
                    if (HashMap.this.modCount != n) {
                        throw new ConcurrentModificationException();
                    }
                }
                return;
            }
            throw new NullPointerException();
        }

        @Override
        public final Iterator<Map.Entry<K, V>> iterator() {
            return new EntryIterator();
        }

        @Override
        public final boolean remove(Object object) {
            boolean bl = object instanceof Map.Entry;
            boolean bl2 = false;
            if (bl) {
                Map.Entry entry = (Map.Entry)object;
                object = entry.getKey();
                entry = entry.getValue();
                if (HashMap.this.removeNode(HashMap.hash(object), object, entry, true, true) != null) {
                    bl2 = true;
                }
                return bl2;
            }
            return false;
        }

        @Override
        public final int size() {
            return HashMap.this.size;
        }

        @Override
        public final Spliterator<Map.Entry<K, V>> spliterator() {
            return new EntrySpliterator(HashMap.this, 0, -1, 0, 0);
        }
    }

    static final class EntrySpliterator<K, V>
    extends HashMapSpliterator<K, V>
    implements Spliterator<Map.Entry<K, V>> {
        EntrySpliterator(HashMap<K, V> hashMap, int n, int n2, int n3, int n4) {
            super(hashMap, n, n2, n3, n4);
        }

        @Override
        public int characteristics() {
            int n = this.fence >= 0 && this.est != this.map.size ? 0 : 64;
            return n | 1;
        }

        /*
         * Unable to fully structure code
         * Enabled aggressive block sorting
         * Lifted jumps to return sites
         */
        @Override
        public void forEachRemaining(Consumer<? super Map.Entry<K, V>> var1_1) {
            if (var1_1 == null) throw new NullPointerException();
            var2_2 = this.map;
            var3_3 = var2_2.table;
            var5_5 = var4_4 = this.fence;
            if (var4_4 < 0) {
                this.expectedModCount = var6_6 = var2_2.modCount;
                var4_4 = var3_3 == null ? 0 : var3_3.length;
                this.fence = var4_4;
                var5_5 = var4_4;
                var4_4 = var6_6;
            } else {
                var4_4 = this.expectedModCount;
            }
            if (var3_3 == null) return;
            if (var3_3.length < var5_5) return;
            var6_6 = var7_7 = this.index;
            if (var7_7 < 0) return;
            this.index = var5_5;
            if (var6_6 >= var5_5) {
                if (this.current == null) return;
            }
            var8_8 = this.current;
            this.current = null;
            var7_7 = var6_6;
            do lbl-1000: // 3 sources:
            {
                if (var8_8 == null) {
                    var9_9 = var3_3[var7_7];
                    var6_6 = var7_7 + 1;
                } else {
                    var1_1.accept(var8_8);
                    var9_9 = var8_8.next;
                    var6_6 = var7_7;
                }
                var8_8 = var9_9;
                var7_7 = var6_6;
                if (var9_9 != null) ** GOTO lbl-1000
                var8_8 = var9_9;
                var7_7 = var6_6;
            } while (var6_6 < var5_5);
            if (var2_2.modCount != var4_4) throw new ConcurrentModificationException();
        }

        @Override
        public boolean tryAdvance(Consumer<? super Map.Entry<K, V>> consumer) {
            if (consumer != null) {
                int n;
                int n2;
                Object object = this.map.table;
                if (object != null && (n2 = ((Node<K, V>[])object).length) >= (n = this.getFence()) && this.index >= 0) {
                    while (this.current != null || this.index < n) {
                        if (this.current == null) {
                            n2 = this.index;
                            this.index = n2 + 1;
                            this.current = object[n2];
                            continue;
                        }
                        object = this.current;
                        this.current = this.current.next;
                        consumer.accept((Map.Entry<K, V>)object);
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
            if (n < n3 && this.current == null) {
                object = this.map;
                this.index = n3;
                this.est = n2 = this.est >>> 1;
                object = new EntrySpliterator<K, V>((HashMap<K, V>)object, n, n3, n2, this.expectedModCount);
            } else {
                object = null;
            }
            return object;
        }
    }

    abstract class HashIterator {
        Node<K, V> current;
        int expectedModCount;
        int index;
        Node<K, V> next;

        HashIterator() {
            this.expectedModCount = ((HashMap)HashMap.this).modCount;
            Node<K, V>[] arrnode = ((HashMap)HashMap.this).table;
            this.next = null;
            this.current = null;
            this.index = 0;
            if (arrnode != null && ((HashMap)HashMap.this).size > 0) {
                int n;
                while ((n = this.index) < arrnode.length) {
                    this.index = n + 1;
                    this.next = HashMap.this = arrnode[n];
                    if (HashMap.this == null) continue;
                }
            }
        }

        public final boolean hasNext() {
            boolean bl = this.next != null;
            return bl;
        }

        final Node<K, V> nextNode() {
            Node<K, V> node = this.next;
            if (HashMap.this.modCount == this.expectedModCount) {
                if (node != null) {
                    this.current = node;
                    Node<K, V>[] arrnode = node.next;
                    this.next = arrnode;
                    if (arrnode == null && (arrnode = HashMap.this.table) != null) {
                        int n;
                        while ((n = this.index) < arrnode.length) {
                            this.index = n + 1;
                            Node node2 = arrnode[n];
                            this.next = node2;
                            if (node2 == null) continue;
                        }
                    }
                    return node;
                }
                throw new NoSuchElementException();
            }
            throw new ConcurrentModificationException();
        }

        public final void remove() {
            Node<K, V> node = this.current;
            if (node != null) {
                if (HashMap.this.modCount == this.expectedModCount) {
                    this.current = null;
                    node = node.key;
                    HashMap.this.removeNode(HashMap.hash(node), node, null, false, false);
                    this.expectedModCount = HashMap.this.modCount;
                    return;
                }
                throw new ConcurrentModificationException();
            }
            throw new IllegalStateException();
        }
    }

    static class HashMapSpliterator<K, V> {
        Node<K, V> current;
        int est;
        int expectedModCount;
        int fence;
        int index;
        final HashMap<K, V> map;

        HashMapSpliterator(HashMap<K, V> hashMap, int n, int n2, int n3, int n4) {
            this.map = hashMap;
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
                Node<K, V>[] arrnode = this.map;
                this.est = arrnode.size;
                this.expectedModCount = arrnode.modCount;
                arrnode = arrnode.table;
                n2 = arrnode == null ? 0 : arrnode.length;
                this.fence = n2;
            }
            return n2;
        }
    }

    final class KeyIterator
    extends HashMap<K, V>
    implements Iterator<K> {
        KeyIterator() {
        }

        @Override
        public final K next() {
            return this.nextNode().key;
        }
    }

    final class KeySet
    extends AbstractSet<K> {
        KeySet() {
        }

        @Override
        public final void clear() {
            HashMap.this.clear();
        }

        @Override
        public final boolean contains(Object object) {
            return HashMap.this.containsKey(object);
        }

        @Override
        public final void forEach(Consumer<? super K> consumer) {
            if (consumer != null) {
                Node<K, V>[] arrnode;
                if (HashMap.this.size > 0 && (arrnode = HashMap.this.table) != null) {
                    int n = HashMap.this.modCount;
                    for (int i = 0; i < arrnode.length && HashMap.this.modCount == n; ++i) {
                        Node node = arrnode[i];
                        while (node != null) {
                            consumer.accept(node.key);
                            node = node.next;
                        }
                    }
                    if (HashMap.this.modCount != n) {
                        throw new ConcurrentModificationException();
                    }
                }
                return;
            }
            throw new NullPointerException();
        }

        @Override
        public final Iterator<K> iterator() {
            return new KeyIterator();
        }

        @Override
        public final boolean remove(Object object) {
            boolean bl = HashMap.this.removeNode(HashMap.hash(object), object, null, false, true) != null;
            return bl;
        }

        @Override
        public final int size() {
            return HashMap.this.size;
        }

        @Override
        public final Spliterator<K> spliterator() {
            return new KeySpliterator(HashMap.this, 0, -1, 0, 0);
        }
    }

    static final class KeySpliterator<K, V>
    extends HashMapSpliterator<K, V>
    implements Spliterator<K> {
        KeySpliterator(HashMap<K, V> hashMap, int n, int n2, int n3, int n4) {
            super(hashMap, n, n2, n3, n4);
        }

        @Override
        public int characteristics() {
            int n = this.fence >= 0 && this.est != this.map.size ? 0 : 64;
            return n | 1;
        }

        /*
         * Unable to fully structure code
         * Enabled aggressive block sorting
         * Lifted jumps to return sites
         */
        @Override
        public void forEachRemaining(Consumer<? super K> var1_1) {
            if (var1_1 == null) throw new NullPointerException();
            var2_2 = this.map;
            var3_3 = var2_2.table;
            var5_5 = var4_4 = this.fence;
            if (var4_4 < 0) {
                this.expectedModCount = var4_4 = var2_2.modCount;
                var5_5 = var3_3 == null ? 0 : var3_3.length;
                this.fence = var5_5;
            } else {
                var4_4 = this.expectedModCount;
            }
            if (var3_3 == null) return;
            if (var3_3.length < var5_5) return;
            var7_7 = var6_6 = this.index;
            if (var6_6 < 0) return;
            this.index = var5_5;
            if (var7_7 >= var5_5) {
                if (this.current == null) return;
            }
            var8_8 = this.current;
            this.current = null;
            do lbl-1000: // 3 sources:
            {
                if (var8_8 == null) {
                    var9_9 = var3_3[var7_7];
                    var6_6 = var7_7 + 1;
                } else {
                    var1_1.accept(var8_8.key);
                    var9_9 = var8_8.next;
                    var6_6 = var7_7;
                }
                var8_8 = var9_9;
                var7_7 = var6_6;
                if (var9_9 != null) ** GOTO lbl-1000
                var8_8 = var9_9;
                var7_7 = var6_6;
            } while (var6_6 < var5_5);
            if (var2_2.modCount != var4_4) throw new ConcurrentModificationException();
        }

        @Override
        public boolean tryAdvance(Consumer<? super K> consumer) {
            if (consumer != null) {
                int n;
                int n2;
                Object object = this.map.table;
                if (object != null && (n2 = ((Node<K, V>[])object).length) >= (n = this.getFence()) && this.index >= 0) {
                    while (this.current != null || this.index < n) {
                        if (this.current == null) {
                            n2 = this.index;
                            this.index = n2 + 1;
                            this.current = object[n2];
                            continue;
                        }
                        object = this.current.key;
                        this.current = this.current.next;
                        consumer.accept(object);
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
            int n = this.index;
            int n2 = this.getFence();
            int n3 = n + n2 >>> 1;
            if (n < n3 && this.current == null) {
                object = this.map;
                this.index = n3;
                this.est = n2 = this.est >>> 1;
                object = new KeySpliterator<K, V>((HashMap<K, V>)object, n, n3, n2, this.expectedModCount);
            } else {
                object = null;
            }
            return object;
        }
    }

    static class Node<K, V>
    implements Map.Entry<K, V> {
        final int hash;
        final K key;
        Node<K, V> next;
        V value;

        Node(int n, K k, V v, Node<K, V> node) {
            this.hash = n;
            this.key = k;
            this.value = v;
            this.next = node;
        }

        @Override
        public final boolean equals(Object object) {
            if (object == this) {
                return true;
            }
            return object instanceof Map.Entry && Objects.equals(this.key, (object = (Map.Entry)object).getKey()) && Objects.equals(this.value, object.getValue());
        }

        @Override
        public final K getKey() {
            return this.key;
        }

        @Override
        public final V getValue() {
            return this.value;
        }

        @Override
        public final int hashCode() {
            return Objects.hashCode(this.key) ^ Objects.hashCode(this.value);
        }

        @Override
        public final V setValue(V v) {
            V v2 = this.value;
            this.value = v;
            return v2;
        }

        public final String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(this.key);
            stringBuilder.append("=");
            stringBuilder.append(this.value);
            return stringBuilder.toString();
        }
    }

    static final class TreeNode<K, V>
    extends LinkedHashMap.LinkedHashMapEntry<K, V> {
        static final /* synthetic */ boolean $assertionsDisabled = false;
        TreeNode<K, V> left;
        TreeNode<K, V> parent;
        TreeNode<K, V> prev;
        boolean red;
        TreeNode<K, V> right;

        TreeNode(int n, K k, V v, Node<K, V> node) {
            super(n, k, v, node);
        }

        static <K, V> TreeNode<K, V> balanceDeletion(TreeNode<K, V> treeNode, TreeNode<K, V> treeNode2) {
            while (treeNode2 != null && treeNode2 != treeNode) {
                TreeNode<K, V> treeNode3;
                TreeNode<K, V> treeNode4;
                block27 : {
                    boolean bl;
                    TreeNode<K, V> treeNode5;
                    block29 : {
                        TreeNode<K, V> treeNode6;
                        TreeNode<K, V> treeNode7;
                        TreeNode<K, V> treeNode8;
                        block28 : {
                            TreeNode<K, V> treeNode9;
                            block23 : {
                                block24 : {
                                    block26 : {
                                        block25 : {
                                            treeNode5 = treeNode3 = treeNode2.parent;
                                            if (treeNode3 == null) {
                                                treeNode2.red = false;
                                                return treeNode2;
                                            }
                                            if (treeNode2.red) {
                                                treeNode2.red = false;
                                                return treeNode;
                                            }
                                            treeNode9 = treeNode3 = treeNode5.left;
                                            treeNode7 = null;
                                            treeNode8 = null;
                                            if (treeNode3 != treeNode2) break block23;
                                            treeNode7 = treeNode5.right;
                                            treeNode9 = treeNode7;
                                            treeNode3 = treeNode5;
                                            treeNode6 = treeNode9;
                                            treeNode4 = treeNode;
                                            if (treeNode7 != null) {
                                                treeNode3 = treeNode5;
                                                treeNode6 = treeNode9;
                                                treeNode4 = treeNode;
                                                if (treeNode9.red) {
                                                    treeNode9.red = false;
                                                    treeNode5.red = true;
                                                    treeNode4 = TreeNode.rotateLeft(treeNode, treeNode5);
                                                    treeNode = treeNode2.parent;
                                                    treeNode3 = treeNode;
                                                    treeNode = treeNode == null ? null : treeNode3.right;
                                                    treeNode6 = treeNode;
                                                }
                                            }
                                            if (treeNode6 == null) {
                                                treeNode2 = treeNode3;
                                                treeNode = treeNode4;
                                                continue;
                                            }
                                            treeNode9 = treeNode6.left;
                                            treeNode7 = treeNode6.right;
                                            if ((treeNode7 == null || !treeNode7.red) && (treeNode9 == null || !treeNode9.red)) break block24;
                                            if (treeNode7 == null) break block25;
                                            treeNode5 = treeNode3;
                                            treeNode = treeNode6;
                                            treeNode3 = treeNode4;
                                            if (treeNode7.red) break block26;
                                        }
                                        if (treeNode9 != null) {
                                            treeNode9.red = false;
                                        }
                                        treeNode6.red = true;
                                        treeNode3 = TreeNode.rotateRight(treeNode4, treeNode6);
                                        treeNode = treeNode2.parent;
                                        treeNode5 = treeNode;
                                        treeNode = treeNode == null ? treeNode8 : treeNode5.right;
                                    }
                                    if (treeNode != null) {
                                        bl = treeNode5 == null ? false : treeNode5.red;
                                        treeNode.red = bl;
                                        treeNode = treeNode.right;
                                        if (treeNode != null) {
                                            treeNode.red = false;
                                        }
                                    }
                                    treeNode = treeNode3;
                                    if (treeNode5 != null) {
                                        treeNode5.red = false;
                                        treeNode = TreeNode.rotateLeft(treeNode3, treeNode5);
                                    }
                                    treeNode2 = treeNode;
                                    continue;
                                }
                                treeNode6.red = true;
                                treeNode2 = treeNode3;
                                treeNode = treeNode4;
                                continue;
                            }
                            treeNode3 = treeNode5;
                            treeNode6 = treeNode9;
                            treeNode4 = treeNode;
                            if (treeNode9 != null) {
                                treeNode3 = treeNode5;
                                treeNode6 = treeNode9;
                                treeNode4 = treeNode;
                                if (treeNode9.red) {
                                    treeNode9.red = false;
                                    treeNode5.red = true;
                                    treeNode4 = TreeNode.rotateRight(treeNode, treeNode5);
                                    treeNode = treeNode2.parent;
                                    treeNode3 = treeNode;
                                    treeNode = treeNode == null ? null : treeNode3.left;
                                    treeNode6 = treeNode;
                                }
                            }
                            if (treeNode6 == null) {
                                treeNode2 = treeNode3;
                                treeNode = treeNode4;
                                continue;
                            }
                            treeNode9 = treeNode6.left;
                            treeNode8 = treeNode6.right;
                            if ((treeNode9 == null || !treeNode9.red) && (treeNode8 == null || !treeNode8.red)) break block27;
                            if (treeNode9 == null) break block28;
                            treeNode5 = treeNode3;
                            treeNode = treeNode6;
                            treeNode3 = treeNode4;
                            if (treeNode9.red) break block29;
                        }
                        if (treeNode8 != null) {
                            treeNode8.red = false;
                        }
                        treeNode6.red = true;
                        treeNode3 = TreeNode.rotateLeft(treeNode4, treeNode6);
                        treeNode = treeNode2.parent;
                        treeNode5 = treeNode;
                        treeNode = treeNode == null ? treeNode7 : treeNode5.left;
                    }
                    if (treeNode != null) {
                        bl = treeNode5 == null ? false : treeNode5.red;
                        treeNode.red = bl;
                        treeNode = treeNode.left;
                        if (treeNode != null) {
                            treeNode.red = false;
                        }
                    }
                    treeNode = treeNode3;
                    if (treeNode5 != null) {
                        treeNode5.red = false;
                        treeNode = TreeNode.rotateRight(treeNode3, treeNode5);
                    }
                    treeNode2 = treeNode;
                    continue;
                }
                treeNode6.red = true;
                treeNode2 = treeNode3;
                treeNode = treeNode4;
            }
            return treeNode;
        }

        static <K, V> TreeNode<K, V> balanceInsertion(TreeNode<K, V> treeNode, TreeNode<K, V> treeNode2) {
            treeNode2.red = true;
            TreeNode<K, V> treeNode3 = treeNode2;
            do {
                TreeNode<K, V> treeNode4;
                TreeNode<K, V> treeNode5;
                TreeNode<K, V> treeNode6;
                TreeNode<K, V> treeNode7 = treeNode3.parent;
                treeNode2 = treeNode7;
                if (treeNode7 == null) {
                    treeNode3.red = false;
                    return treeNode3;
                }
                if (!treeNode2.red) break;
                treeNode7 = treeNode4 = treeNode2.parent;
                if (treeNode4 == null) break;
                treeNode4 = treeNode7.left;
                Object var5_5 = null;
                Object var6_6 = null;
                if (treeNode2 == treeNode4) {
                    treeNode4 = treeNode7.right;
                    if (treeNode4 != null && treeNode4.red) {
                        treeNode4.red = false;
                        treeNode2.red = false;
                        treeNode7.red = true;
                        treeNode3 = treeNode7;
                        continue;
                    }
                    treeNode5 = treeNode2;
                    treeNode6 = treeNode7;
                    treeNode4 = treeNode;
                    treeNode7 = treeNode3;
                    if (treeNode3 == treeNode2.right) {
                        treeNode7 = treeNode2;
                        treeNode4 = TreeNode.rotateLeft(treeNode, treeNode2);
                        treeNode = treeNode7.parent;
                        treeNode2 = treeNode;
                        treeNode = treeNode == null ? var6_6 : treeNode2.parent;
                        treeNode6 = treeNode;
                        treeNode5 = treeNode2;
                    }
                    treeNode = treeNode4;
                    treeNode3 = treeNode7;
                    if (treeNode5 == null) continue;
                    treeNode5.red = false;
                    treeNode = treeNode4;
                    treeNode3 = treeNode7;
                    if (treeNode6 == null) continue;
                    treeNode6.red = true;
                    treeNode = TreeNode.rotateRight(treeNode4, treeNode6);
                    treeNode3 = treeNode7;
                    continue;
                }
                if (treeNode4 != null && treeNode4.red) {
                    treeNode4.red = false;
                    treeNode2.red = false;
                    treeNode7.red = true;
                    treeNode3 = treeNode7;
                    continue;
                }
                treeNode6 = treeNode2;
                treeNode5 = treeNode7;
                treeNode4 = treeNode;
                treeNode7 = treeNode3;
                if (treeNode3 == treeNode2.left) {
                    treeNode7 = treeNode2;
                    treeNode4 = TreeNode.rotateRight(treeNode, treeNode2);
                    treeNode = treeNode7.parent;
                    treeNode2 = treeNode;
                    treeNode = treeNode == null ? var5_5 : treeNode2.parent;
                    treeNode5 = treeNode;
                    treeNode6 = treeNode2;
                }
                treeNode = treeNode4;
                treeNode3 = treeNode7;
                if (treeNode6 == null) continue;
                treeNode6.red = false;
                treeNode = treeNode4;
                treeNode3 = treeNode7;
                if (treeNode5 == null) continue;
                treeNode5.red = true;
                treeNode = TreeNode.rotateLeft(treeNode4, treeNode5);
                treeNode3 = treeNode7;
            } while (true);
            return treeNode;
        }

        static <K, V> boolean checkInvariants(TreeNode<K, V> treeNode) {
            TreeNode<K, V> treeNode2 = treeNode.parent;
            TreeNode<K, V> treeNode3 = treeNode.left;
            TreeNode<K, V> treeNode4 = treeNode.right;
            TreeNode<K, V> treeNode5 = treeNode.prev;
            TreeNode treeNode6 = (TreeNode)treeNode.next;
            if (treeNode5 != null && treeNode5.next != treeNode) {
                return false;
            }
            if (treeNode6 != null && treeNode6.prev != treeNode) {
                return false;
            }
            if (treeNode2 != null && treeNode != treeNode2.left && treeNode != treeNode2.right) {
                return false;
            }
            if (treeNode3 != null && (treeNode3.parent != treeNode || treeNode3.hash > treeNode.hash)) {
                return false;
            }
            if (treeNode4 != null && (treeNode4.parent != treeNode || treeNode4.hash < treeNode.hash)) {
                return false;
            }
            if (treeNode.red && treeNode3 != null && treeNode3.red && treeNode4 != null && treeNode4.red) {
                return false;
            }
            if (treeNode3 != null && !TreeNode.checkInvariants(treeNode3)) {
                return false;
            }
            return treeNode4 == null || TreeNode.checkInvariants(treeNode4);
        }

        static <K, V> void moveRootToFront(Node<K, V>[] object, TreeNode<K, V> treeNode) {
            TreeNode treeNode2;
            int n;
            if (treeNode != null && object != null && (n = ((Node<K, V>[])object).length) > 0 && treeNode != (treeNode2 = (TreeNode)object[n = n - 1 & treeNode.hash])) {
                object[n] = treeNode;
                object = treeNode.prev;
                Node node = treeNode.next;
                if (node != null) {
                    ((TreeNode)node).prev = object;
                }
                if (object != null) {
                    object.next = node;
                }
                if (treeNode2 != null) {
                    treeNode2.prev = treeNode;
                }
                treeNode.next = treeNode2;
                treeNode.prev = null;
            }
        }

        static <K, V> TreeNode<K, V> rotateLeft(TreeNode<K, V> treeNode, TreeNode<K, V> treeNode2) {
            TreeNode<K, V> treeNode3 = treeNode;
            if (treeNode2 != null) {
                TreeNode<K, V> treeNode4 = treeNode2.right;
                treeNode3 = treeNode;
                if (treeNode4 != null) {
                    treeNode3 = treeNode4.left;
                    treeNode2.right = treeNode3;
                    if (treeNode3 != null) {
                        treeNode3.parent = treeNode2;
                    }
                    treeNode3 = treeNode2.parent;
                    treeNode4.parent = treeNode3;
                    if (treeNode3 == null) {
                        treeNode = treeNode4;
                        treeNode4.red = false;
                    } else if (treeNode3.left == treeNode2) {
                        treeNode3.left = treeNode4;
                    } else {
                        treeNode3.right = treeNode4;
                    }
                    treeNode4.left = treeNode2;
                    treeNode2.parent = treeNode4;
                    treeNode3 = treeNode;
                }
            }
            return treeNode3;
        }

        static <K, V> TreeNode<K, V> rotateRight(TreeNode<K, V> treeNode, TreeNode<K, V> treeNode2) {
            TreeNode<K, V> treeNode3 = treeNode;
            if (treeNode2 != null) {
                TreeNode<K, V> treeNode4 = treeNode2.left;
                treeNode3 = treeNode;
                if (treeNode4 != null) {
                    treeNode3 = treeNode4.right;
                    treeNode2.left = treeNode3;
                    if (treeNode3 != null) {
                        treeNode3.parent = treeNode2;
                    }
                    treeNode3 = treeNode2.parent;
                    treeNode4.parent = treeNode3;
                    if (treeNode3 == null) {
                        treeNode = treeNode4;
                        treeNode4.red = false;
                    } else if (treeNode3.right == treeNode2) {
                        treeNode3.right = treeNode4;
                    } else {
                        treeNode3.left = treeNode4;
                    }
                    treeNode4.right = treeNode2;
                    treeNode2.parent = treeNode4;
                    treeNode3 = treeNode;
                }
            }
            return treeNode3;
        }

        static int tieBreakOrder(Object object, Object object2) {
            int n;
            block3 : {
                block2 : {
                    int n2;
                    if (object == null || object2 == null) break block2;
                    n = n2 = object.getClass().getName().compareTo(object2.getClass().getName());
                    if (n2 != 0) break block3;
                }
                n = System.identityHashCode(object) <= System.identityHashCode(object2) ? -1 : 1;
            }
            return n;
        }

        /*
         * Unable to fully structure code
         * Enabled aggressive block sorting
         * Lifted jumps to return sites
         */
        final TreeNode<K, V> find(int var1_1, Object var2_2, Class<?> var3_3) {
            var4_4 = this;
            var5_5 = var3_3;
            do {
                block7 : {
                    block11 : {
                        block10 : {
                            block9 : {
                                block8 : {
                                    block6 : {
                                        var3_3 = var4_4.left;
                                        var6_6 = var4_4.right;
                                        var7_7 = var4_4.hash;
                                        if (var7_7 <= var1_1) break block6;
                                        var8_8 = var5_5;
                                        break block7;
                                    }
                                    if (var7_7 >= var1_1) break block8;
                                    var3_3 = var6_6;
                                    var8_8 = var5_5;
                                    break block7;
                                }
                                var9_9 = var4_4.key;
                                if (var9_9 == var2_2) return var4_4;
                                if (var2_2 != null && var2_2.equals(var9_9)) {
                                    return var4_4;
                                }
                                if (var3_3 != null) break block9;
                                var3_3 = var6_6;
                                var8_8 = var5_5;
                                break block7;
                            }
                            if (var6_6 != null) break block10;
                            var8_8 = var5_5;
                            break block7;
                        }
                        var8_8 = var5_5;
                        if (var5_5 != null) break block11;
                        var5_5 = var8_8 = (var4_4 = HashMap.comparableClassFor(var2_2));
                        if (var4_4 == null) ** GOTO lbl-1000
                    }
                    var7_7 = HashMap.compareComparables(var8_8, var2_2, var9_9);
                    var5_5 = var8_8;
                    if (var7_7 != 0) {
                        if (var7_7 >= 0) {
                            var3_3 = var6_6;
                        }
                    } else lbl-1000: // 2 sources:
                    {
                        if ((var8_8 = var6_6.find(var1_1, var2_2, (Class<?>)var5_5)) != null) {
                            return var8_8;
                        }
                        var8_8 = var5_5;
                    }
                }
                var4_4 = var3_3;
                var5_5 = var8_8;
            } while (var3_3 != null);
            return null;
        }

        final TreeNode<K, V> getTreeNode(int n, Object object) {
            TreeNode<K, V> treeNode = this.parent != null ? this.root() : this;
            return treeNode.find(n, object, null);
        }

        final TreeNode<K, V> putTreeVal(HashMap<K, V> object, Node<K, V>[] arrnode, int n, K k, V v) {
            TreeNode<K, V> treeNode;
            TreeNode<K, V> treeNode2 = null;
            int n2 = 0;
            TreeNode<K, V> treeNode3 = this.parent != null ? this.root() : this;
            Object object2 = treeNode3;
            do {
                TreeNode<K, V> treeNode4;
                int n3;
                block9 : {
                    Object object3;
                    block13 : {
                        block15 : {
                            block14 : {
                                block12 : {
                                    block11 : {
                                        block10 : {
                                            block8 : {
                                                treeNode = object2;
                                                n3 = treeNode.hash;
                                                if (n3 <= n) break block8;
                                                n3 = -1;
                                                object2 = treeNode2;
                                                break block9;
                                            }
                                            if (n3 >= n) break block10;
                                            n3 = 1;
                                            object2 = treeNode2;
                                            break block9;
                                        }
                                        object3 = treeNode.key;
                                        if (object3 == k || k != null && k.equals(object3)) break;
                                        object2 = treeNode2;
                                        if (treeNode2 != null) break block11;
                                        treeNode4 = HashMap.comparableClassFor(k);
                                        treeNode2 = treeNode4;
                                        object2 = treeNode2;
                                        if (treeNode4 == null) break block12;
                                        object2 = treeNode2;
                                    }
                                    if ((n3 = HashMap.compareComparables(object2, k, object3)) != 0) break block9;
                                }
                                n3 = n2;
                                if (n2 != 0) break block13;
                                n2 = 1;
                                treeNode2 = treeNode.left;
                                if (treeNode2 == null) break block14;
                                treeNode4 = treeNode2.find(n, k, (Class<?>)object2);
                                treeNode2 = treeNode4;
                                if (treeNode4 != null) break block15;
                            }
                            treeNode2 = treeNode.right;
                            n3 = n2;
                            if (treeNode2 == null) break block13;
                            treeNode4 = treeNode2.find(n, k, (Class<?>)object2);
                            treeNode2 = treeNode4;
                            n3 = n2;
                            if (treeNode4 == null) break block13;
                        }
                        return treeNode2;
                    }
                    int n4 = TreeNode.tieBreakOrder(k, object3);
                    n2 = n3;
                    n3 = n4;
                }
                treeNode2 = n3 <= 0 ? treeNode.left : treeNode.right;
                treeNode4 = treeNode2;
                if (treeNode2 == null) {
                    object2 = treeNode.next;
                    object = ((HashMap)object).newTreeNode(n, k, v, object2);
                    if (n3 <= 0) {
                        treeNode.left = object;
                    } else {
                        treeNode.right = object;
                    }
                    treeNode.next = object;
                    ((TreeNode)object).prev = treeNode;
                    ((TreeNode)object).parent = treeNode;
                    if (object2 != null) {
                        ((TreeNode)object2).prev = object;
                    }
                    TreeNode.moveRootToFront(arrnode, TreeNode.balanceInsertion(treeNode3, object));
                    return null;
                }
                treeNode2 = object2;
                object2 = treeNode4;
            } while (true);
            return treeNode;
        }

        final void removeTreeNode(HashMap<K, V> treeNode, Node<K, V>[] arrnode, boolean bl) {
            int n;
            if (arrnode != null && (n = arrnode.length) != 0) {
                TreeNode<K, V> treeNode2;
                n = n - 1 & this.hash;
                TreeNode<K, V> treeNode3 = treeNode2 = (TreeNode<K, V>)arrnode[n];
                TreeNode<K, V> treeNode4 = (TreeNode<K, V>)this.next;
                TreeNode<K, V> treeNode5 = this.prev;
                if (treeNode5 == null) {
                    treeNode2 = treeNode4;
                    arrnode[n] = treeNode4;
                } else {
                    treeNode5.next = treeNode4;
                }
                if (treeNode4 != null) {
                    treeNode4.prev = treeNode5;
                }
                if (treeNode2 == null) {
                    return;
                }
                treeNode4 = treeNode3;
                if (treeNode3.parent != null) {
                    treeNode4 = treeNode3.root();
                }
                if (treeNode4 != null && treeNode4.right != null && (treeNode3 = treeNode4.left) != null && treeNode3.left != null) {
                    treeNode2 = this.left;
                    treeNode3 = this.right;
                    if (treeNode2 != null && treeNode3 != null) {
                        treeNode = treeNode3;
                        while ((treeNode5 = treeNode.left) != null) {
                            treeNode = treeNode5;
                        }
                        boolean bl2 = treeNode.red;
                        treeNode.red = this.red;
                        this.red = bl2;
                        treeNode5 = treeNode.right;
                        TreeNode<K, V> treeNode6 = this.parent;
                        if (treeNode == treeNode3) {
                            this.parent = treeNode;
                            treeNode.right = this;
                        } else {
                            TreeNode<K, V> treeNode7;
                            this.parent = treeNode7 = treeNode.parent;
                            if (treeNode7 != null) {
                                if (treeNode == treeNode7.left) {
                                    treeNode7.left = this;
                                } else {
                                    treeNode7.right = this;
                                }
                            }
                            treeNode.right = treeNode3;
                            treeNode3.parent = treeNode;
                        }
                        this.left = null;
                        this.right = treeNode5;
                        if (treeNode5 != null) {
                            treeNode5.parent = this;
                        }
                        treeNode.left = treeNode2;
                        treeNode2.parent = treeNode;
                        treeNode.parent = treeNode6;
                        if (treeNode6 != null) {
                            if (this == treeNode6.left) {
                                treeNode6.left = treeNode;
                            } else {
                                treeNode6.right = treeNode;
                            }
                            treeNode = treeNode4;
                        }
                        treeNode4 = treeNode5 != null ? treeNode5 : this;
                        treeNode3 = treeNode;
                        treeNode = treeNode4;
                        treeNode4 = treeNode3;
                    } else {
                        treeNode = treeNode2 != null ? treeNode2 : (treeNode3 != null ? treeNode3 : this);
                    }
                    if (treeNode != this) {
                        treeNode3 = this.parent;
                        treeNode.parent = treeNode3;
                        if (treeNode3 == null) {
                            treeNode4 = treeNode;
                        } else if (this == treeNode3.left) {
                            treeNode3.left = treeNode;
                        } else {
                            treeNode3.right = treeNode;
                        }
                        this.parent = null;
                        this.right = null;
                        this.left = null;
                    }
                    if (!this.red) {
                        treeNode4 = TreeNode.balanceDeletion(treeNode4, treeNode);
                    }
                    if (treeNode == this) {
                        treeNode = this.parent;
                        this.parent = null;
                        if (treeNode != null) {
                            if (this == treeNode.left) {
                                treeNode.left = null;
                            } else if (this == treeNode.right) {
                                treeNode.right = null;
                            }
                        }
                    }
                    if (bl) {
                        TreeNode.moveRootToFront(arrnode, treeNode4);
                    }
                    return;
                }
                arrnode[n] = treeNode2.untreeify((HashMap<K, V>)((Object)treeNode));
                return;
            }
        }

        final TreeNode<K, V> root() {
            TreeNode<K, V> treeNode = this;
            TreeNode<K, V> treeNode2;
            while ((treeNode2 = treeNode.parent) != null) {
                treeNode = treeNode2;
            }
            return treeNode;
        }

        final void split(HashMap<K, V> hashMap, Node<K, V>[] arrnode, int n, int n2) {
            TreeNode treeNode = null;
            TreeNode treeNode2 = null;
            TreeNode treeNode3 = null;
            TreeNode treeNode4 = null;
            int n3 = 0;
            int n4 = 0;
            TreeNode treeNode5 = this;
            while (treeNode5 != null) {
                TreeNode treeNode6 = (TreeNode)treeNode5.next;
                treeNode5.next = null;
                if ((treeNode5.hash & n2) == 0) {
                    treeNode5.prev = treeNode2;
                    if (treeNode2 == null) {
                        treeNode = treeNode5;
                    } else {
                        treeNode2.next = treeNode5;
                    }
                    ++n3;
                    treeNode2 = treeNode5;
                } else {
                    treeNode5.prev = treeNode4;
                    if (treeNode4 == null) {
                        treeNode3 = treeNode5;
                    } else {
                        treeNode4.next = treeNode5;
                    }
                    ++n4;
                    treeNode4 = treeNode5;
                }
                treeNode5 = treeNode6;
            }
            if (treeNode != null) {
                if (n3 <= 6) {
                    arrnode[n] = treeNode.untreeify(hashMap);
                } else {
                    arrnode[n] = treeNode;
                    if (treeNode3 != null) {
                        treeNode.treeify(arrnode);
                    }
                }
            }
            if (treeNode3 != null) {
                if (n4 <= 6) {
                    arrnode[n + n2] = treeNode3.untreeify(hashMap);
                } else {
                    arrnode[n + n2] = treeNode3;
                    if (treeNode != null) {
                        treeNode3.treeify(arrnode);
                    }
                }
            }
        }

        /*
         * Unable to fully structure code
         * Enabled aggressive block sorting
         * Lifted jumps to return sites
         */
        final void treeify(Node<K, V>[] var1_1) {
            var2_2 = null;
            var3_3 = this;
            block0 : do {
                block11 : {
                    if (var3_3 == null) {
                        TreeNode.moveRootToFront(var1_1, var2_2);
                        return;
                    }
                    var4_4 = (TreeNode)var3_3.next;
                    var3_3.right = null;
                    var3_3.left = null;
                    if (var2_2 != null) break block11;
                    var3_3.parent = null;
                    var3_3.red = false;
                    var2_2 = var3_3;
                    ** GOTO lbl54
                }
                var5_5 = var3_3.key;
                var6_6 = var3_3.hash;
                var7_7 = null;
                var8_8 = var2_2;
                do {
                    block17 : {
                        block13 : {
                            block16 : {
                                block15 : {
                                    block14 : {
                                        block12 : {
                                            var9_9 = var8_8;
                                            var10_10 = var9_9.key;
                                            var11_11 = var9_9.hash;
                                            if (var11_11 <= var6_6) break block12;
                                            var11_11 = -1;
                                            var8_8 = var7_7;
                                            break block13;
                                        }
                                        if (var11_11 >= var6_6) break block14;
                                        var11_11 = 1;
                                        var8_8 = var7_7;
                                        break block13;
                                    }
                                    var8_8 = var7_7;
                                    if (var7_7 != null) break block15;
                                    var7_7 = var8_8 = (var12_12 = HashMap.comparableClassFor(var5_5));
                                    if (var12_12 == null) break block16;
                                }
                                if ((var11_11 = HashMap.compareComparables(var8_8, var5_5, var10_10)) != 0) break block13;
                                var7_7 = var8_8;
                            }
                            var11_11 = TreeNode.tieBreakOrder(var5_5, var10_10);
                            var8_8 = var7_7;
                        }
                        var7_7 = var11_11 <= 0 ? var9_9.left : var9_9.right;
                        var12_12 = var7_7;
                        if (var7_7 != null) break block17;
                        var3_3.parent = var9_9;
                        if (var11_11 <= 0) {
                            var9_9.left = var3_3;
                        } else {
                            var9_9.right = var3_3;
                        }
                        var2_2 = TreeNode.balanceInsertion(var2_2, var3_3);
lbl54: // 2 sources:
                        var3_3 = var4_4;
                        continue block0;
                    }
                    var7_7 = var8_8;
                    var8_8 = var12_12;
                } while (true);
                break;
            } while (true);
        }

        final Node<K, V> untreeify(HashMap<K, V> hashMap) {
            Node<K, V> node = null;
            Node<K, V> node2 = null;
            Node node3 = this;
            while (node3 != null) {
                Node<K, V> node4 = hashMap.replacementNode(node3, null);
                if (node2 == null) {
                    node = node4;
                } else {
                    node2.next = node4;
                }
                node3 = node3.next;
                node2 = node4;
            }
            return node;
        }
    }

    final class ValueIterator
    extends HashMap<K, V>
    implements Iterator<V> {
        ValueIterator() {
        }

        @Override
        public final V next() {
            return this.nextNode().value;
        }
    }

    static final class ValueSpliterator<K, V>
    extends HashMapSpliterator<K, V>
    implements Spliterator<V> {
        ValueSpliterator(HashMap<K, V> hashMap, int n, int n2, int n3, int n4) {
            super(hashMap, n, n2, n3, n4);
        }

        @Override
        public int characteristics() {
            int n = this.fence >= 0 && this.est != this.map.size ? 0 : 64;
            return n;
        }

        /*
         * Unable to fully structure code
         * Enabled aggressive block sorting
         * Lifted jumps to return sites
         */
        @Override
        public void forEachRemaining(Consumer<? super V> var1_1) {
            if (var1_1 == null) throw new NullPointerException();
            var2_2 = this.map;
            var3_3 = var2_2.table;
            var5_5 = var4_4 = this.fence;
            if (var4_4 < 0) {
                this.expectedModCount = var6_6 = var2_2.modCount;
                var4_4 = var3_3 == null ? 0 : var3_3.length;
                this.fence = var4_4;
                var5_5 = var4_4;
                var4_4 = var6_6;
            } else {
                var4_4 = this.expectedModCount;
            }
            if (var3_3 == null) return;
            if (var3_3.length < var5_5) return;
            var6_6 = var7_7 = this.index;
            if (var7_7 < 0) return;
            this.index = var5_5;
            if (var6_6 >= var5_5) {
                if (this.current == null) return;
            }
            var8_8 = this.current;
            this.current = null;
            do lbl-1000: // 3 sources:
            {
                if (var8_8 == null) {
                    var9_9 = var3_3[var6_6];
                    var7_7 = var6_6 + 1;
                } else {
                    var1_1.accept(var8_8.value);
                    var9_9 = var8_8.next;
                    var7_7 = var6_6;
                }
                var8_8 = var9_9;
                var6_6 = var7_7;
                if (var9_9 != null) ** GOTO lbl-1000
                var8_8 = var9_9;
                var6_6 = var7_7;
            } while (var7_7 < var5_5);
            if (var2_2.modCount != var4_4) throw new ConcurrentModificationException();
        }

        @Override
        public boolean tryAdvance(Consumer<? super V> consumer) {
            if (consumer != null) {
                int n;
                int n2;
                Object object = this.map.table;
                if (object != null && (n2 = ((Node<K, V>[])object).length) >= (n = this.getFence()) && this.index >= 0) {
                    while (this.current != null || this.index < n) {
                        if (this.current == null) {
                            n2 = this.index;
                            this.index = n2 + 1;
                            this.current = object[n2];
                            continue;
                        }
                        object = this.current.value;
                        this.current = this.current.next;
                        consumer.accept(object);
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
            if (n < n3 && this.current == null) {
                object = this.map;
                this.index = n3;
                this.est = n2 = this.est >>> 1;
                object = new ValueSpliterator<K, V>((HashMap<K, V>)object, n, n3, n2, this.expectedModCount);
            } else {
                object = null;
            }
            return object;
        }
    }

    final class Values
    extends AbstractCollection<V> {
        Values() {
        }

        @Override
        public final void clear() {
            HashMap.this.clear();
        }

        @Override
        public final boolean contains(Object object) {
            return HashMap.this.containsValue(object);
        }

        @Override
        public final void forEach(Consumer<? super V> consumer) {
            if (consumer != null) {
                Node<K, V>[] arrnode;
                if (HashMap.this.size > 0 && (arrnode = HashMap.this.table) != null) {
                    int n = HashMap.this.modCount;
                    for (int i = 0; i < arrnode.length && HashMap.this.modCount == n; ++i) {
                        Node node = arrnode[i];
                        while (node != null) {
                            consumer.accept(node.value);
                            node = node.next;
                        }
                    }
                    if (HashMap.this.modCount != n) {
                        throw new ConcurrentModificationException();
                    }
                }
                return;
            }
            throw new NullPointerException();
        }

        @Override
        public final Iterator<V> iterator() {
            return new ValueIterator();
        }

        @Override
        public final int size() {
            return HashMap.this.size;
        }

        @Override
        public final Spliterator<V> spliterator() {
            return new ValueSpliterator(HashMap.this, 0, -1, 0, 0);
        }
    }

}

