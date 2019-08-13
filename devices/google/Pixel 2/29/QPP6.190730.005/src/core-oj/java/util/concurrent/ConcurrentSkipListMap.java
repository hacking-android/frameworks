/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  java.util.concurrent.-$
 *  java.util.concurrent.-$$Lambda
 *  java.util.concurrent.-$$Lambda$ConcurrentSkipListMap
 *  java.util.concurrent.-$$Lambda$ConcurrentSkipListMap$EntrySpliterator
 *  java.util.concurrent.-$$Lambda$ConcurrentSkipListMap$EntrySpliterator$y0KdhWWpZC4eKUM6bCtPBgl2u2o
 */
package java.util.concurrent;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.AbstractCollection;
import java.util.AbstractMap;
import java.util.AbstractSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NavigableMap;
import java.util.NavigableSet;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.Spliterator;
import java.util.concurrent.-$;
import java.util.concurrent.ConcurrentNavigableMap;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent._$$Lambda$ConcurrentSkipListMap$EntrySpliterator$y0KdhWWpZC4eKUM6bCtPBgl2u2o;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import sun.misc.Unsafe;

public class ConcurrentSkipListMap<K, V>
extends AbstractMap<K, V>
implements ConcurrentNavigableMap<K, V>,
Cloneable,
Serializable {
    static final Object BASE_HEADER = new Object();
    private static final int EQ = 1;
    private static final int GT = 0;
    private static final long HEAD;
    private static final int LT = 2;
    private static final Unsafe U;
    private static final long serialVersionUID = -8627078645895051609L;
    final Comparator<? super K> comparator;
    private transient ConcurrentNavigableMap<K, V> descendingMap;
    private transient EntrySet<K, V> entrySet;
    private volatile transient HeadIndex<K, V> head;
    private transient KeySet<K, V> keySet;
    private transient Values<K, V> values;

    static {
        U = Unsafe.getUnsafe();
        try {
            HEAD = U.objectFieldOffset(ConcurrentSkipListMap.class.getDeclaredField("head"));
            return;
        }
        catch (ReflectiveOperationException reflectiveOperationException) {
            throw new Error(reflectiveOperationException);
        }
    }

    public ConcurrentSkipListMap() {
        this.comparator = null;
        this.initialize();
    }

    public ConcurrentSkipListMap(Comparator<? super K> comparator) {
        this.comparator = comparator;
        this.initialize();
    }

    public ConcurrentSkipListMap(Map<? extends K, ? extends V> map) {
        this.comparator = null;
        this.initialize();
        this.putAll(map);
    }

    public ConcurrentSkipListMap(SortedMap<K, ? extends V> sortedMap) {
        this.comparator = sortedMap.comparator();
        this.initialize();
        this.buildFromSorted(sortedMap);
    }

    private void buildFromSorted(SortedMap<K, ? extends V> headIndex) {
        if (headIndex != null) {
            int n;
            HeadIndex headIndex2 = this.head;
            Index index = headIndex2.node;
            ArrayList arrayList = new ArrayList();
            for (n = 0; n <= headIndex2.level; ++n) {
                arrayList.add(null);
            }
            Object object = headIndex2;
            for (n = headIndex2.level; n > 0; --n) {
                arrayList.set(n, object);
                object = ((Index)object).down;
            }
            Iterator<Map.Entry<K, V>> iterator = headIndex.entrySet().iterator();
            object = index;
            headIndex = headIndex2;
            while (iterator.hasNext()) {
                index = iterator.next();
                int n2 = ThreadLocalRandom.current().nextInt();
                n = 0;
                int n3 = 0;
                if ((-2147483647 & n2) == 0) {
                    int n4;
                    n = n3;
                    do {
                        n3 = n + 1;
                        n2 = n4 = n2 >>> 1;
                        n = n3;
                    } while ((n4 & 1) != 0);
                    n = n3;
                    if (n3 > headIndex.level) {
                        n = headIndex.level + 1;
                    }
                }
                headIndex2 = index.getKey();
                index = index.getValue();
                if (headIndex2 != null && index != null) {
                    Node node = new Node(headIndex2, index, null);
                    ((Node)object).next = node;
                    object = node;
                    headIndex2 = headIndex;
                    if (n > 0) {
                        index = null;
                        n3 = 1;
                        do {
                            headIndex2 = headIndex;
                            if (n3 > n) break;
                            index = new Index(node, index, null);
                            headIndex2 = headIndex;
                            if (n3 > headIndex.level) {
                                headIndex2 = new HeadIndex(headIndex.node, headIndex, index, n3);
                            }
                            if (n3 < arrayList.size()) {
                                ((Index)arrayList.get((int)n3)).right = index;
                                arrayList.set(n3, index);
                            } else {
                                arrayList.add(index);
                            }
                            ++n3;
                            headIndex = headIndex2;
                        } while (true);
                    }
                    headIndex = headIndex2;
                    continue;
                }
                throw new NullPointerException();
            }
            this.head = headIndex;
            return;
        }
        throw new NullPointerException();
    }

    private boolean casHead(HeadIndex<K, V> headIndex, HeadIndex<K, V> headIndex2) {
        return U.compareAndSwapObject(this, HEAD, headIndex, headIndex2);
    }

    private void clearIndexToFirst() {
        block0 : do {
            Index index;
            Index index2 = this.head;
            do {
                if ((index = index2.right) != null && index.indexesDeletedNode() && !index2.unlink(index)) continue block0;
                index = index2.down;
                index2 = index;
            } while (index != null);
            break;
        } while (true);
        if (this.head.right == null) {
            this.tryReduceLevel();
        }
    }

    static final int cpr(Comparator comparator, Object object, Object object2) {
        int n = comparator != null ? comparator.compare(object, object2) : ((Comparable)object).compareTo(object2);
        return n;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    private V doGet(Object var1_1) {
        if (var1_1 == null) throw new NullPointerException();
        var2_2 = this.comparator;
        block0 : do {
            var3_4 = this.findPredecessor(var1_1, var2_2);
            var4_6 = var3_4.next;
            do {
                if (var4_6 == null) {
                    return null;
                }
                var5_7 = var4_6.next;
                if (var4_6 != var3_3.next) continue block0;
                var6_8 = var4_6.value;
                if (var6_8 == null) {
                    var4_6.helpDelete(var3_3, var5_7);
                    continue block0;
                }
                if (var3_3.value != null && var6_8 != var4_6) ** break;
                continue block0;
                var7_9 = ConcurrentSkipListMap.cpr(var2_2, var1_1, var4_6.key);
                if (var7_9 == 0) {
                    return (V)var6_8;
                }
                if (var7_9 < 0) {
                    return null;
                }
                var3_5 = var4_6;
                var4_6 = var5_7;
            } while (true);
            break;
        } while (true);
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    private V doPut(K var1_1, V var2_2, boolean var3_3) {
        block21 : {
            var4_18 = this;
            if (var1_1 == null) throw new NullPointerException();
            var5_26 = var4_18.comparator;
            block0 : do {
                var6_27 = var4_18.findPredecessor(var1_1, var5_26);
                var7_29 = var6_27.next;
                while (var7_28 != null) {
                    var8_42 = var7_28.next;
                    if (var7_28 != var6_27.next) continue block0;
                    var9_50 = var7_28.value;
                    if (var9_50 == null) {
                        var7_28.helpDelete(var6_27, var8_42);
                        continue block0;
                    }
                    if (var6_27.value == null || var9_50 == var7_28) continue block0;
                    var10_59 = ConcurrentSkipListMap.cpr(var5_26, var1_1, var7_28.key);
                    if (var10_59 > 0) {
                        var6_27 = var7_28;
                        var7_30 = var8_42;
                        continue;
                    }
                    if (var10_59 != 0) break;
                    if (var3_17 != false) return (V)var9_50;
                    if (!var7_28.casValue(var9_50, var2_2 /* !! */ )) continue block0;
                    return (V)var9_50;
                }
                if (var6_27.casNext((Node<K, V>)var7_28, var8_43 = new Node<K, V>(var1_1, var2_2 /* !! */ , var7_28))) break;
            } while (true);
            var11_60 = ThreadLocalRandom.nextSecondarySeed();
            if ((-2147483647 & var11_60) != 0) return null;
            var10_59 = 1;
            while (((var11_60 >>>= 1) & 1) != 0) {
                ++var10_59;
            }
            var2_3 = null;
            var6_27 = var4_18.head;
            var11_60 = var6_27.level;
            if (var10_59 <= var11_60) {
                for (var11_60 = 1; var11_60 <= var10_59; ++var11_60) {
                    var2_5 = new Index<K, V>(var8_43, var2_4, null);
                }
                var7_31 = var2_4;
            } else {
                var9_51 = new Index[++var11_60 + 1];
                var2_6 = null;
                for (var10_59 = 1; var10_59 <= var11_60; ++var10_59) {
                    var2_8 = var7_33 = new Index<K, V>(var8_43, var2_7, null);
                    var9_51[var10_59] = var7_33;
                }
                do {
                    var4_20 = this;
                    var6_27 = var4_20.head;
                    var12_61 = var6_27.level;
                    if (var11_60 <= var12_61) {
                        var10_59 = var11_60;
                        var7_35 = var2_7;
                        break block21;
                    }
                    var7_36 = var6_27;
                    var8_45 = var6_27.node;
                    for (var10_59 = var12_61 + 1; var10_59 <= var11_60; ++var10_59) {
                        var7_38 = new HeadIndex<K, V>(var8_45, var7_37, var9_51[var10_59], var10_59);
                    }
                } while (!var4_20.casHead(var6_27, (HeadIndex<K, V>)var7_37));
                var2_9 = var9_51[var12_61];
                var10_59 = var12_61;
                var6_27 = var7_37;
                var7_39 = var2_9;
            }
        }
        var12_61 = var10_59;
        block7 : do lbl-1000: // 3 sources:
        {
            var11_60 = var6_27.level;
            var4_22 = var6_27;
            var2_12 = var4_22.right;
            var8_47 = var7_40;
            do {
                block22 : {
                    block23 : {
                        if (var8_48 == null) {
                            return null;
                        }
                        if (var2_13 == null) break block22;
                        var9_54 = var2_13.node;
                        var13_62 = ConcurrentSkipListMap.cpr(var5_26, var1_1, var9_54.key);
                        if (var9_54.value != null) break block23;
                        if (!var4_23.unlink(var2_13)) ** GOTO lbl-1000
                        var2_14 = var4_23.right;
                        continue;
                    }
                    if (var13_62 > 0) {
                        var4_24 = var2_13;
                        var2_15 = var2_13.right;
                        continue;
                    }
                }
                var13_62 = var12_61;
                if (var11_60 == var12_61) {
                    if (!var4_23.link(var2_13, var8_48)) continue block7;
                    if (var8_48.node.value == null) {
                        this.findNode(var1_1);
                        return null;
                    }
                    var13_62 = --var12_61;
                    if (var12_61 == 0) {
                        return null;
                    }
                }
                var9_55 = var8_48;
                if (--var11_60 >= var13_62) {
                    var9_56 = var8_48;
                    if (var11_60 < var10_59) {
                        var9_57 = var8_48.down;
                    }
                }
                var4_25 = var4_23.down;
                var2_16 = var4_25.right;
                var12_61 = var13_62;
                var8_49 = var9_58;
            } while (true);
            break;
        } while (true);
    }

    private Map.Entry<K, V> doRemoveFirstEntry() {
        Object object;
        Node node;
        Node node2;
        Node node3;
        do {
            node2 = this.head.node;
            node = node2.next;
            if (node == null) {
                return null;
            }
            node3 = node.next;
            if (node != node2.next) continue;
            object = node.value;
            if (object == null) {
                node.helpDelete(node2, node3);
                continue;
            }
            if (node.casValue(object, null)) break;
        } while (true);
        if (!node.appendMarker(node3) || !node2.casNext(node, node3)) {
            this.findFirst();
        }
        this.clearIndexToFirst();
        return new AbstractMap.SimpleImmutableEntry(node.key, object);
    }

    private Map.Entry<K, V> doRemoveLastEntry() {
        Node node;
        Node node2;
        Object object;
        Node node3;
        Node node4;
        block0 : do {
            node4 = this.findPredecessorOfLast();
            node2 = node4.next;
            node3 = node4;
            node = node2;
            if (node2 == null) {
                if (!node4.isBaseHeader()) continue;
                return null;
            }
            do {
                node4 = node.next;
                if (node != node3.next) continue block0;
                object = node.value;
                if (object == null) {
                    node.helpDelete(node3, node4);
                    continue block0;
                }
                if (node3.value == null || object == node) continue block0;
                if (node4 == null) break;
                node3 = node;
                node = node4;
            } while (true);
            if (node.casValue(object, null)) break;
        } while (true);
        node2 = node.key;
        if (node.appendMarker(node4) && node3.casNext(node, node4)) {
            this.findPredecessor(node2, this.comparator);
            if (this.head.right == null) {
                this.tryReduceLevel();
            }
        } else {
            this.findNode(node2);
        }
        return new AbstractMap.SimpleImmutableEntry(node2, object);
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    private Node<K, V> findNode(Object var1_1) {
        if (var1_1 == null) throw new NullPointerException();
        var2_2 = this.comparator;
        block0 : do {
            var3_4 = this.findPredecessor(var1_1, var2_2);
            var4_6 = var3_4.next;
            do {
                if (var4_6 == null) {
                    return null;
                }
                var5_7 = var4_6.next;
                if (var4_6 != var3_3.next) continue block0;
                var6_8 = var4_6.value;
                if (var6_8 == null) {
                    var4_6.helpDelete(var3_3, var5_7);
                    continue block0;
                }
                if (var3_3.value != null && var6_8 != var4_6) ** break;
                continue block0;
                var7_9 = ConcurrentSkipListMap.cpr(var2_2, var1_1, var4_6.key);
                if (var7_9 == 0) {
                    return var4_6;
                }
                if (var7_9 < 0) {
                    return null;
                }
                var3_5 = var4_6;
                var4_6 = var5_7;
            } while (true);
            break;
        } while (true);
    }

    private Node<K, V> findPredecessor(Object object, Comparator<? super K> comparator) {
        if (object != null) {
            block0 : do {
                Index index = this.head;
                Index index2 = index.right;
                do {
                    if (index2 != null) {
                        Node node = index2.node;
                        Object k = node.key;
                        if (node.value == null) {
                            if (!index.unlink(index2)) continue block0;
                            index2 = index.right;
                            continue;
                        }
                        if (ConcurrentSkipListMap.cpr(comparator, object, k) > 0) {
                            index = index2;
                            index2 = index2.right;
                            continue;
                        }
                    }
                    if ((index2 = index.down) == null) {
                        return index.node;
                    }
                    index = index2;
                    index2 = index2.right;
                } while (true);
                break;
            } while (true);
        }
        throw new NullPointerException();
    }

    private Node<K, V> findPredecessorOfLast() {
        Index index;
        block0 : do {
            index = this.head;
            do {
                Index index2;
                if ((index2 = index.right) != null) {
                    if (index2.indexesDeletedNode()) {
                        index.unlink(index2);
                        continue block0;
                    }
                    if (index2.node.next != null) {
                        index = index2;
                        continue;
                    }
                }
                if ((index2 = index.down) == null) break block0;
                index = index2;
            } while (true);
            break;
        } while (true);
        return index.node;
    }

    private void initialize() {
        this.keySet = null;
        this.entrySet = null;
        this.values = null;
        this.descendingMap = null;
        this.head = new HeadIndex(new Node(null, BASE_HEADER, null), null, null, 1);
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        Node node;
        HeadIndex<K, V> headIndex;
        int n;
        objectInputStream.defaultReadObject();
        this.initialize();
        HeadIndex<K, V> headIndex2 = this.head;
        Node node2 = headIndex2.node;
        ArrayList<HeadIndex<K, V>> arrayList = new ArrayList<HeadIndex<K, V>>();
        for (n = 0; n <= headIndex2.level; ++n) {
            arrayList.add(null);
        }
        Object object = headIndex2;
        n = headIndex2.level;
        do {
            headIndex = headIndex2;
            node = node2;
            if (n <= 0) break;
            arrayList.set(n, (HeadIndex<K, V>)object);
            object = ((Index)object).down;
            --n;
        } while (true);
        do {
            if ((headIndex2 = objectInputStream.readObject()) == null) {
                this.head = headIndex;
                return;
            }
            object = objectInputStream.readObject();
            if (object == null) break;
            int n2 = ThreadLocalRandom.current().nextInt();
            n = 0;
            int n3 = 0;
            if ((-2147483647 & n2) == 0) {
                int n4;
                n = n3;
                do {
                    n3 = n + 1;
                    n2 = n4 = n2 >>> 1;
                    n = n3;
                } while ((n4 & 1) != 0);
                n = n3;
                if (n3 > headIndex.level) {
                    n = headIndex.level + 1;
                }
            }
            node2 = new Node(headIndex2, object, null);
            node.next = node2;
            node = node2;
            object = headIndex;
            if (n > 0) {
                headIndex2 = null;
                n3 = 1;
                do {
                    object = headIndex;
                    if (n3 > n) break;
                    object = new Index(node2, headIndex2, null);
                    headIndex2 = headIndex;
                    if (n3 > headIndex.level) {
                        headIndex2 = new HeadIndex<K, V>(headIndex.node, headIndex, (Index<K, V>)object, n3);
                    }
                    if (n3 < arrayList.size()) {
                        ((Index)arrayList.get((int)n3)).right = object;
                        arrayList.set(n3, (HeadIndex<K, V>)object);
                    } else {
                        arrayList.add((HeadIndex<K, V>)object);
                    }
                    ++n3;
                    headIndex = headIndex2;
                    headIndex2 = object;
                } while (true);
            }
            headIndex = object;
        } while (true);
        throw new NullPointerException();
    }

    static final <E> List<E> toList(Collection<E> object) {
        ArrayList arrayList = new ArrayList();
        object = object.iterator();
        while (object.hasNext()) {
            arrayList.add(object.next());
        }
        return arrayList;
    }

    private void tryReduceLevel() {
        HeadIndex headIndex;
        HeadIndex headIndex2;
        HeadIndex<K, V> headIndex3 = this.head;
        if (headIndex3.level > 3 && (headIndex2 = (HeadIndex)headIndex3.down) != null && (headIndex = (HeadIndex)headIndex2.down) != null && headIndex.right == null && headIndex2.right == null && headIndex3.right == null && this.casHead(headIndex3, headIndex2) && headIndex3.right != null) {
            this.casHead(headIndex2, headIndex3);
        }
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        Node<K, V> node = this.findFirst();
        while (node != null) {
            V v = node.getValidValue();
            if (v != null) {
                objectOutputStream.writeObject(node.key);
                objectOutputStream.writeObject(v);
            }
            node = node.next;
        }
        objectOutputStream.writeObject(null);
    }

    @Override
    public Map.Entry<K, V> ceilingEntry(K k) {
        return this.getNear(k, 1);
    }

    @Override
    public K ceilingKey(K object) {
        object = (object = this.findNear(object, 1, this.comparator)) == null ? null : ((Node)object).key;
        return object;
    }

    @Override
    public void clear() {
        this.initialize();
    }

    @Override
    public ConcurrentSkipListMap<K, V> clone() {
        try {
            ConcurrentSkipListMap concurrentSkipListMap = (ConcurrentSkipListMap)super.clone();
            concurrentSkipListMap.initialize();
            concurrentSkipListMap.buildFromSorted(this);
            return concurrentSkipListMap;
        }
        catch (CloneNotSupportedException cloneNotSupportedException) {
            throw new InternalError();
        }
    }

    @Override
    public Comparator<? super K> comparator() {
        return this.comparator;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    @Override
    public V compute(K var1_1, BiFunction<? super K, ? super V, ? extends V> var2_2) {
        if (var1_1 == null) throw new NullPointerException();
        if (var2_2 == null) throw new NullPointerException();
        do lbl-1000: // 5 sources:
        {
            block2 : {
                if ((var3_3 = this.findNode(var1_1)) != null) break block2;
                var4_4 = var2_2.apply(var1_1, null);
                if (var4_4 == null) {
                    return null;
                }
                if (this.doPut(var1_1, var4_4, true) != null) ** GOTO lbl-1000
                return (V)var4_4;
            }
            var4_4 = var3_3.value;
            if (var4_4 == null) ** GOTO lbl-1000
            var5_5 = var2_2.apply(var1_1, var4_4);
            if (var5_5 == null) continue;
            if (!var3_3.casValue(var4_4, var5_5)) ** GOTO lbl-1000
            return var5_5;
        } while (this.doRemove(var1_1, var4_4) == null);
        return null;
    }

    @Override
    public V computeIfAbsent(K object, Function<? super K, ? extends V> function) {
        if (object != null && function != null) {
            V v;
            V v2;
            Object object2 = v = (v2 = this.doGet(object));
            if (v2 == null) {
                function = function.apply(object);
                object2 = v;
                if (function != null) {
                    if ((object = this.doPut(object, function, true)) == null) {
                        object = function;
                    }
                    object2 = object;
                }
            }
            return object2;
        }
        throw new NullPointerException();
    }

    @Override
    public V computeIfPresent(K k, BiFunction<? super K, ? super V, ? extends V> biFunction) {
        if (k != null && biFunction != null) {
            Node<K, V> node;
            while ((node = this.findNode(k)) != null) {
                Object object = node.value;
                if (object == null) continue;
                V v = biFunction.apply(k, object);
                if (v != null) {
                    if (!node.casValue(object, v)) continue;
                    return v;
                }
                if (this.doRemove(k, object) == null) continue;
                break;
            }
            return null;
        }
        throw new NullPointerException();
    }

    @Override
    public boolean containsKey(Object object) {
        boolean bl = this.doGet(object) != null;
        return bl;
    }

    @Override
    public boolean containsValue(Object object) {
        if (object != null) {
            Node<K, V> node = this.findFirst();
            while (node != null) {
                V v = node.getValidValue();
                if (v != null && object.equals(v)) {
                    return true;
                }
                node = node.next;
            }
            return false;
        }
        throw new NullPointerException();
    }

    @Override
    public NavigableSet<K> descendingKeySet() {
        return this.descendingMap().navigableKeySet();
    }

    @Override
    public ConcurrentNavigableMap<K, V> descendingMap() {
        ConcurrentNavigableMap<K, V> concurrentNavigableMap = this.descendingMap;
        if (concurrentNavigableMap == null) {
            this.descendingMap = concurrentNavigableMap = new SubMap(this, null, false, null, false, true);
        }
        return concurrentNavigableMap;
    }

    final V doRemove(Object object, Object object2) {
        if (object != null) {
            Node node;
            Node node2;
            Object object3;
            Node node3;
            Comparator<? super K> comparator = this.comparator;
            block0 : do {
                block8 : {
                    node = this.findPredecessor(object, comparator);
                    node2 = node.next;
                    while (node2 != null) {
                        node3 = node2.next;
                        if (node2 != node.next) continue block0;
                        object3 = node2.value;
                        if (object3 == null) {
                            node2.helpDelete(node, node3);
                            continue block0;
                        }
                        if (node.value == null || object3 == node2) continue block0;
                        int n = ConcurrentSkipListMap.cpr(comparator, object, node2.key);
                        if (n < 0) break;
                        if (n > 0) {
                            node = node2;
                            node2 = node3;
                            continue;
                        }
                        if (object2 == null || object2.equals(object3)) break block8;
                    }
                    return null;
                }
                if (node2.casValue(object3, null)) break;
            } while (true);
            if (node2.appendMarker(node3) && node.casNext(node2, node3)) {
                this.findPredecessor(object, comparator);
                if (this.head.right == null) {
                    this.tryReduceLevel();
                }
            } else {
                this.findNode(object);
            }
            return (V)object3;
        }
        throw new NullPointerException();
    }

    @Override
    public Set<Map.Entry<K, V>> entrySet() {
        EntrySet<K, V> entrySet = this.entrySet;
        if (entrySet == null) {
            this.entrySet = entrySet = new EntrySet(this);
        }
        return entrySet;
    }

    final EntrySpliterator<K, V> entrySpliterator() {
        Node node;
        HeadIndex<K, V> headIndex;
        Comparator<? super K> comparator = this.comparator;
        do {
            headIndex = this.head;
            Node node2 = headIndex.node;
            node = node2.next;
            if (node == null || node.value != null) break;
            node.helpDelete(node2, node.next);
        } while (true);
        int n = node == null ? 0 : Integer.MAX_VALUE;
        return new EntrySpliterator<Object, V>(comparator, headIndex, node, null, n);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public boolean equals(Object iterator) {
        if (iterator == this) {
            return true;
        }
        if (!(iterator instanceof Map)) {
            return false;
        }
        iterator = (Map)((Object)iterator);
        try {
            Iterator<Map.Entry<K, V>> iterator2;
            boolean bl;
            Map.Entry<K, V> entry2;
            for (Map.Entry<K, V> entry2 : this.entrySet()) {
                if (entry2.getValue().equals(iterator.get(entry2.getKey()))) continue;
                return false;
            }
            iterator = iterator.entrySet().iterator();
            do {
                if (!iterator.hasNext()) return true;
                entry2 = iterator.next();
                iterator2 = entry2.getKey();
                entry2 = entry2.getValue();
                if (iterator2 == null) return false;
                if (entry2 == null) return false;
            } while (bl = ((Object)entry2).equals(this.get(iterator2)));
            return false;
        }
        catch (NullPointerException nullPointerException) {
            return false;
        }
        catch (ClassCastException classCastException) {
            return false;
        }
    }

    final Node<K, V> findFirst() {
        do {
            Node node = this.head.node;
            Node node2 = node.next;
            if (node2 == null) {
                return null;
            }
            if (node2.value != null) {
                return node2;
            }
            node2.helpDelete(node, node2.next);
        } while (true);
    }

    final Node<K, V> findLast() {
        Object object = this.head;
        do {
            Object object2;
            if ((object2 = ((Index)object).right) != null) {
                if (((Index)object2).indexesDeletedNode()) {
                    ((Index)object).unlink(object2);
                    object = this.head;
                    continue;
                }
                object = object2;
                continue;
            }
            object2 = ((Index)object).down;
            if (object2 != null) {
                object = object2;
                continue;
            }
            object = ((Index)object).node;
            object2 = ((Node)object).next;
            do {
                if (object2 == null) {
                    if (((Node)object).isBaseHeader()) {
                        object = null;
                    }
                    return object;
                }
                Node<K, V> node = ((Node)object2).next;
                if (object2 != ((Node)object).next) break;
                Object object3 = ((Node)object2).value;
                if (object3 == null) {
                    ((Node)object2).helpDelete(object, node);
                    break;
                }
                if (((Node)object).value == null || object3 == object2) break;
                object = object2;
                object2 = node;
            } while (true);
            object = this.head;
        } while (true);
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    final Node<K, V> findNear(K var1_1, int var2_2, Comparator<? super K> var3_3) {
        if (var1_1 /* !! */  == null) throw new NullPointerException();
        block0 : do {
            var4_11 = this.findPredecessor(var1_1 /* !! */ , (Comparator<? super K>)var3_10);
            var5_12 = var4_11.next;
            do {
                var6_13 = null;
                var7_14 = null;
                if (var5_12 == null) {
                    var1_2 = var7_14;
                    if ((var2_9 & 2) == 0) return var1_5;
                    if (var4_11.isBaseHeader()) {
                        var1_3 = var7_14;
                        return var1_5;
                    }
                    var1_4 = var4_11;
                    return var1_5;
                }
                var7_14 = var5_12.next;
                if (var5_12 != var4_11.next) continue block0;
                var8_15 = var5_12.value;
                if (var8_15 == null) {
                    var5_12.helpDelete(var4_11, var7_14);
                    continue block0;
                }
                if (var4_11.value != null && var8_15 != var5_12) ** break;
                continue block0;
                var9_16 = ConcurrentSkipListMap.cpr((Comparator)var3_10, var1_1 /* !! */ , var5_12.key);
                if (var9_16 == 0) {
                    if ((var2_9 & true) != 0) return var5_12;
                }
                if (var9_16 < 0 && (var2_9 & 2) == 0) {
                    return var5_12;
                }
                if (var9_16 <= 0 && (var2_9 & 2) != 0) {
                    if (var4_11.isBaseHeader()) {
                        var1_6 = var6_13;
                        return var1_8;
                    }
                    var1_7 = var4_11;
                    return var1_8;
                }
                var4_11 = var5_12;
                var5_12 = var7_14;
            } while (true);
            break;
        } while (true);
    }

    @Override
    public Map.Entry<K, V> firstEntry() {
        Object object;
        do {
            if ((object = this.findFirst()) != null) continue;
            return null;
        } while ((object = ((Node)object).createSnapshot()) == null);
        return object;
    }

    @Override
    public K firstKey() {
        Node<K, V> node = this.findFirst();
        if (node != null) {
            return node.key;
        }
        throw new NoSuchElementException();
    }

    @Override
    public Map.Entry<K, V> floorEntry(K k) {
        return this.getNear(k, 3);
    }

    @Override
    public K floorKey(K object) {
        object = (object = this.findNear(object, 3, this.comparator)) == null ? null : ((Node)object).key;
        return object;
    }

    @Override
    public void forEach(BiConsumer<? super K, ? super V> biConsumer) {
        if (biConsumer != null) {
            Node<K, V> node = this.findFirst();
            while (node != null) {
                V v = node.getValidValue();
                if (v != null) {
                    biConsumer.accept(node.key, v);
                }
                node = node.next;
            }
            return;
        }
        throw new NullPointerException();
    }

    @Override
    public V get(Object object) {
        return this.doGet(object);
    }

    final AbstractMap.SimpleImmutableEntry<K, V> getNear(K k, int n) {
        Object object;
        Comparator<? super K> comparator = this.comparator;
        do {
            if ((object = this.findNear(k, n, comparator)) != null) continue;
            return null;
        } while ((object = ((Node)object).createSnapshot()) == null);
        return object;
    }

    @Override
    public V getOrDefault(Object object, V v) {
        block0 : {
            if ((object = this.doGet(object)) != null) break block0;
            object = v;
        }
        return (V)object;
    }

    @Override
    public ConcurrentNavigableMap<K, V> headMap(K k) {
        return this.headMap((Object)k, false);
    }

    @Override
    public ConcurrentNavigableMap<K, V> headMap(K k, boolean bl) {
        if (k != null) {
            return new SubMap(this, null, false, k, bl, false);
        }
        throw new NullPointerException();
    }

    @Override
    public Map.Entry<K, V> higherEntry(K k) {
        return this.getNear(k, 0);
    }

    @Override
    public K higherKey(K object) {
        object = (object = this.findNear(object, 0, this.comparator)) == null ? null : ((Node)object).key;
        return object;
    }

    @Override
    public boolean isEmpty() {
        boolean bl = this.findFirst() == null;
        return bl;
    }

    @Override
    public NavigableSet<K> keySet() {
        KeySet<K, V> keySet = this.keySet;
        if (keySet == null) {
            this.keySet = keySet = new KeySet(this);
        }
        return keySet;
    }

    final KeySpliterator<K, V> keySpliterator() {
        Node node;
        HeadIndex<K, V> headIndex;
        Comparator<? super K> comparator = this.comparator;
        do {
            headIndex = this.head;
            Node node2 = headIndex.node;
            node = node2.next;
            if (node == null || node.value != null) break;
            node.helpDelete(node2, node.next);
        } while (true);
        int n = node == null ? 0 : Integer.MAX_VALUE;
        return new KeySpliterator<Object, V>(comparator, headIndex, node, null, n);
    }

    @Override
    public Map.Entry<K, V> lastEntry() {
        Object object;
        do {
            if ((object = this.findLast()) != null) continue;
            return null;
        } while ((object = ((Node)object).createSnapshot()) == null);
        return object;
    }

    @Override
    public K lastKey() {
        Node<K, V> node = this.findLast();
        if (node != null) {
            return node.key;
        }
        throw new NoSuchElementException();
    }

    @Override
    public Map.Entry<K, V> lowerEntry(K k) {
        return this.getNear(k, 2);
    }

    @Override
    public K lowerKey(K object) {
        object = (object = this.findNear(object, 2, this.comparator)) == null ? null : ((Node)object).key;
        return object;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    @Override
    public V merge(K var1_1, V var2_2, BiFunction<? super V, ? super V, ? extends V> var3_3) {
        if (var1_1 == null) throw new NullPointerException();
        if (var2_2 == null) throw new NullPointerException();
        if (var3_3 == null) throw new NullPointerException();
        do lbl-1000: // 5 sources:
        {
            block1 : {
                if ((var4_4 = this.findNode(var1_1)) != null) break block1;
                if (this.doPut(var1_1, var2_2, true) != null) ** GOTO lbl-1000
                return var2_2;
            }
            var5_5 = var4_4.value;
            if (var5_5 == null) ** GOTO lbl-1000
            var6_6 = var3_3.apply(var5_5, var2_2);
            if (var6_6 == null) continue;
            if (!var4_4.casValue(var5_5, var6_6)) ** GOTO lbl-1000
            return var6_6;
        } while (this.doRemove(var1_1, var5_5) == null);
        return null;
    }

    @Override
    public NavigableSet<K> navigableKeySet() {
        KeySet<K, V> keySet = this.keySet;
        if (keySet == null) {
            this.keySet = keySet = new KeySet(this);
        }
        return keySet;
    }

    @Override
    public Map.Entry<K, V> pollFirstEntry() {
        return this.doRemoveFirstEntry();
    }

    @Override
    public Map.Entry<K, V> pollLastEntry() {
        return this.doRemoveLastEntry();
    }

    @Override
    public V put(K k, V v) {
        if (v != null) {
            return this.doPut(k, v, false);
        }
        throw new NullPointerException();
    }

    @Override
    public V putIfAbsent(K k, V v) {
        if (v != null) {
            return this.doPut(k, v, true);
        }
        throw new NullPointerException();
    }

    @Override
    public V remove(Object object) {
        return this.doRemove(object, null);
    }

    @Override
    public boolean remove(Object object, Object object2) {
        if (object != null) {
            boolean bl = object2 != null && this.doRemove(object, object2) != null;
            return bl;
        }
        throw new NullPointerException();
    }

    boolean removeEntryIf(Predicate<? super Map.Entry<K, V>> predicate) {
        if (predicate != null) {
            boolean bl = false;
            Node<K, V> node = this.findFirst();
            while (node != null) {
                V v = node.getValidValue();
                boolean bl2 = bl;
                if (v != null) {
                    Object k = node.key;
                    bl2 = bl;
                    if (predicate.test(new AbstractMap.SimpleImmutableEntry(k, v))) {
                        bl2 = bl;
                        if (this.remove(k, v)) {
                            bl2 = true;
                        }
                    }
                }
                node = node.next;
                bl = bl2;
            }
            return bl;
        }
        throw new NullPointerException();
    }

    boolean removeValueIf(Predicate<? super V> predicate) {
        if (predicate != null) {
            boolean bl = false;
            Node<K, V> node = this.findFirst();
            while (node != null) {
                V v = node.getValidValue();
                boolean bl2 = bl;
                if (v != null) {
                    Object k = node.key;
                    bl2 = bl;
                    if (predicate.test(v)) {
                        bl2 = bl;
                        if (this.remove(k, v)) {
                            bl2 = true;
                        }
                    }
                }
                node = node.next;
                bl = bl2;
            }
            return bl;
        }
        throw new NullPointerException();
    }

    @Override
    public V replace(K k, V v) {
        if (k != null && v != null) {
            Object object;
            Node<K, V> node;
            do {
                if ((node = this.findNode(k)) != null) continue;
                return null;
            } while ((object = node.value) == null || !node.casValue(object, v));
            return (V)object;
        }
        throw new NullPointerException();
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    @Override
    public boolean replace(K var1_1, V var2_2, V var3_3) {
        if (var1_1 == null) throw new NullPointerException();
        if (var2_2 == null) throw new NullPointerException();
        if (var3_3 == null) throw new NullPointerException();
        do lbl-1000: // 3 sources:
        {
            if ((var4_4 = this.findNode(var1_1)) == null) {
                return false;
            }
            var5_5 = var4_4.value;
            if (var5_5 == null) ** GOTO lbl-1000
            if (var2_2.equals(var5_5)) continue;
            return false;
        } while (!var4_4.casValue(var5_5, var3_3));
        return true;
    }

    @Override
    public void replaceAll(BiFunction<? super K, ? super V, ? extends V> biFunction) {
        if (biFunction != null) {
            Node<K, V> node = this.findFirst();
            while (node != null) {
                V v;
                while ((v = node.getValidValue()) != null) {
                    V v2 = biFunction.apply(node.key, v);
                    if (v2 != null) {
                        if (!node.casValue(v, v2)) continue;
                        break;
                    }
                    throw new NullPointerException();
                }
                node = node.next;
            }
            return;
        }
        throw new NullPointerException();
    }

    @Override
    public int size() {
        long l = 0L;
        Node<K, V> node = this.findFirst();
        while (node != null) {
            long l2 = l;
            if (node.getValidValue() != null) {
                l2 = l + 1L;
            }
            node = node.next;
            l = l2;
        }
        int n = l >= Integer.MAX_VALUE ? Integer.MAX_VALUE : (int)l;
        return n;
    }

    @Override
    public ConcurrentNavigableMap<K, V> subMap(K k, K k2) {
        return this.subMap((Object)k, true, (Object)k2, false);
    }

    @Override
    public ConcurrentNavigableMap<K, V> subMap(K k, boolean bl, K k2, boolean bl2) {
        if (k != null && k2 != null) {
            return new SubMap(this, k, bl, k2, bl2, false);
        }
        throw new NullPointerException();
    }

    @Override
    public ConcurrentNavigableMap<K, V> tailMap(K k) {
        return this.tailMap((Object)k, true);
    }

    @Override
    public ConcurrentNavigableMap<K, V> tailMap(K k, boolean bl) {
        if (k != null) {
            return new SubMap(this, k, bl, null, false, false);
        }
        throw new NullPointerException();
    }

    final ValueSpliterator<K, V> valueSpliterator() {
        Node node;
        HeadIndex<K, V> headIndex;
        Comparator<? super K> comparator = this.comparator;
        do {
            headIndex = this.head;
            Node node2 = headIndex.node;
            node = node2.next;
            if (node == null || node.value != null) break;
            node.helpDelete(node2, node.next);
        } while (true);
        int n = node == null ? 0 : Integer.MAX_VALUE;
        return new ValueSpliterator<Object, V>(comparator, headIndex, node, null, n);
    }

    @Override
    public Collection<V> values() {
        Values<K, V> values = this.values;
        if (values == null) {
            this.values = values = new Values(this);
        }
        return values;
    }

    static abstract class CSLMSpliterator<K, V> {
        final Comparator<? super K> comparator;
        Node<K, V> current;
        int est;
        final K fence;
        Index<K, V> row;

        CSLMSpliterator(Comparator<? super K> comparator, Index<K, V> index, Node<K, V> node, K k, int n) {
            this.comparator = comparator;
            this.row = index;
            this.current = node;
            this.fence = k;
            this.est = n;
        }

        public final long estimateSize() {
            return this.est;
        }
    }

    final class EntryIterator
    extends ConcurrentSkipListMap<K, V> {
        EntryIterator() {
        }

        public Map.Entry<K, V> next() {
            Node node = this.next;
            Object object = this.nextValue;
            this.advance();
            return new AbstractMap.SimpleImmutableEntry(node.key, object);
        }
    }

    static final class EntrySet<K, V>
    extends AbstractSet<Map.Entry<K, V>> {
        final ConcurrentNavigableMap<K, V> m;

        EntrySet(ConcurrentNavigableMap<K, V> concurrentNavigableMap) {
            this.m = concurrentNavigableMap;
        }

        @Override
        public void clear() {
            this.m.clear();
        }

        @Override
        public boolean contains(Object object) {
            boolean bl = object instanceof Map.Entry;
            boolean bl2 = false;
            if (!bl) {
                return false;
            }
            object = (Map.Entry)object;
            Object v = this.m.get(object.getKey());
            bl = bl2;
            if (v != null) {
                bl = bl2;
                if (v.equals(object.getValue())) {
                    bl = true;
                }
            }
            return bl;
        }

        @Override
        public boolean equals(Object object) {
            boolean bl = true;
            if (object == this) {
                return true;
            }
            if (!(object instanceof Set)) {
                return false;
            }
            object = (Collection)object;
            try {
                boolean bl2;
                if (!this.containsAll((Collection<?>)object) || !(bl2 = object.containsAll(this))) {
                    bl = false;
                }
                return bl;
            }
            catch (NullPointerException nullPointerException) {
                return false;
            }
            catch (ClassCastException classCastException) {
                return false;
            }
        }

        @Override
        public boolean isEmpty() {
            return this.m.isEmpty();
        }

        @Override
        public Iterator<Map.Entry<K, V>> iterator() {
            AbstractMap abstractMap = this.m;
            if (abstractMap instanceof ConcurrentSkipListMap) {
                abstractMap = abstractMap;
                Objects.requireNonNull(abstractMap);
                abstractMap = (ConcurrentSkipListMap)abstractMap.new EntryIterator();
            } else {
                abstractMap = (SubMap)abstractMap;
                Objects.requireNonNull(abstractMap);
                abstractMap = (SubMap)abstractMap.new SubMap.SubMapEntryIterator();
            }
            return abstractMap;
        }

        @Override
        public boolean remove(Object object) {
            if (!(object instanceof Map.Entry)) {
                return false;
            }
            object = (Map.Entry)object;
            return this.m.remove(object.getKey(), object.getValue());
        }

        @Override
        public boolean removeIf(Predicate<? super Map.Entry<K, V>> predicate) {
            if (predicate != null) {
                Object object = this.m;
                if (object instanceof ConcurrentSkipListMap) {
                    return ((ConcurrentSkipListMap)object).removeEntryIf(predicate);
                }
                object = (SubMap)object;
                Objects.requireNonNull(object);
                SubMap.SubMapEntryIterator subMapEntryIterator = (SubMap)object.new SubMap.SubMapEntryIterator();
                boolean bl = false;
                while (subMapEntryIterator.hasNext()) {
                    object = (Map.Entry)subMapEntryIterator.next();
                    boolean bl2 = bl;
                    if (predicate.test((Map.Entry<K, V>)object)) {
                        bl2 = bl;
                        if (this.m.remove(object.getKey(), object.getValue())) {
                            bl2 = true;
                        }
                    }
                    bl = bl2;
                }
                return bl;
            }
            throw new NullPointerException();
        }

        @Override
        public int size() {
            return this.m.size();
        }

        @Override
        public Spliterator<Map.Entry<K, V>> spliterator() {
            Object object = this.m;
            if (object instanceof ConcurrentSkipListMap) {
                object = ((ConcurrentSkipListMap)object).entrySpliterator();
            } else {
                object = (SubMap)object;
                Objects.requireNonNull(object);
                object = (SubMap)object.new SubMap.SubMapEntryIterator();
            }
            return object;
        }

        @Override
        public Object[] toArray() {
            return ConcurrentSkipListMap.toList(this).toArray();
        }

        @Override
        public <T> T[] toArray(T[] arrT) {
            return ConcurrentSkipListMap.toList(this).toArray(arrT);
        }
    }

    static final class EntrySpliterator<K, V>
    extends CSLMSpliterator<K, V>
    implements Spliterator<Map.Entry<K, V>> {
        EntrySpliterator(Comparator<? super K> comparator, Index<K, V> index, Node<K, V> node, K k, int n) {
            super(comparator, index, node, k, n);
        }

        static /* synthetic */ int lambda$getComparator$d5a01062$1(Map.Entry entry, Map.Entry entry2) {
            return ((Comparable)entry.getKey()).compareTo(entry2.getKey());
        }

        @Override
        public int characteristics() {
            return 4373;
        }

        @Override
        public void forEachRemaining(Consumer<? super Map.Entry<K, V>> consumer) {
            if (consumer != null) {
                Object k;
                Comparator comparator = this.comparator;
                Object object = this.fence;
                Node node = this.current;
                this.current = null;
                while (node != null && ((k = node.key) == null || object == null || ConcurrentSkipListMap.cpr(comparator, object, k) > 0)) {
                    Object object2 = node.value;
                    if (object2 != null && object2 != node) {
                        consumer.accept(new AbstractMap.SimpleImmutableEntry(k, object2));
                    }
                    node = node.next;
                }
                return;
            }
            throw new NullPointerException();
        }

        @Override
        public final Comparator<Map.Entry<K, V>> getComparator() {
            if (this.comparator != null) {
                return Map.Entry.comparingByKey(this.comparator);
            }
            return (Comparator)((Object)((Serializable)_$$Lambda$ConcurrentSkipListMap$EntrySpliterator$y0KdhWWpZC4eKUM6bCtPBgl2u2o.INSTANCE));
        }

        @Override
        public boolean tryAdvance(Consumer<? super Map.Entry<K, V>> consumer) {
            if (consumer != null) {
                Object object;
                Comparator comparator = this.comparator;
                Object object2 = this.fence;
                Node node = this.current;
                do {
                    object = node;
                    if (node == null) break;
                    Object k = node.key;
                    if (k != null && object2 != null && ConcurrentSkipListMap.cpr(comparator, object2, k) <= 0) {
                        object = null;
                        break;
                    }
                    object = node.value;
                    if (object != null && object != node) {
                        this.current = node.next;
                        consumer.accept(new AbstractMap.SimpleImmutableEntry(k, object));
                        return true;
                    }
                    node = node.next;
                } while (true);
                this.current = object;
                return false;
            }
            throw new NullPointerException();
        }

        public EntrySpliterator<K, V> trySplit() {
            Object object;
            Comparator comparator = this.comparator;
            Object object2 = this.fence;
            Node node = this.current;
            if (node != null && (object = node.key) != null) {
                Index index = this.row;
                while (index != null) {
                    Node node2;
                    Node node3;
                    Index index2 = index.right;
                    if (index2 != null && (node3 = index2.node) != null && (node2 = node3.next) != null && node2.value != null && (node3 = node2.key) != null && ConcurrentSkipListMap.cpr(comparator, node3, object) > 0 && (object2 == null || ConcurrentSkipListMap.cpr(comparator, node3, object2) < 0)) {
                        this.current = node2;
                        object = index.down;
                        index = index2.right != null ? index2 : index2.down;
                        this.row = index;
                        this.est -= this.est >>> 2;
                        return new EntrySpliterator(comparator, (Index<K, V>)object, node, (K)node3, this.est);
                    }
                    this.row = index = index.down;
                }
            }
            return null;
        }
    }

    static final class HeadIndex<K, V>
    extends Index<K, V> {
        final int level;

        HeadIndex(Node<K, V> node, Index<K, V> index, Index<K, V> index2, int n) {
            super(node, index, index2);
            this.level = n;
        }
    }

    static class Index<K, V> {
        private static final long RIGHT;
        private static final Unsafe U;
        final Index<K, V> down;
        final Node<K, V> node;
        volatile Index<K, V> right;

        static {
            U = Unsafe.getUnsafe();
            try {
                RIGHT = U.objectFieldOffset(Index.class.getDeclaredField("right"));
                return;
            }
            catch (ReflectiveOperationException reflectiveOperationException) {
                throw new Error(reflectiveOperationException);
            }
        }

        Index(Node<K, V> node, Index<K, V> index, Index<K, V> index2) {
            this.node = node;
            this.down = index;
            this.right = index2;
        }

        final boolean casRight(Index<K, V> index, Index<K, V> index2) {
            return U.compareAndSwapObject(this, RIGHT, index, index2);
        }

        final boolean indexesDeletedNode() {
            boolean bl = this.node.value == null;
            return bl;
        }

        final boolean link(Index<K, V> index, Index<K, V> index2) {
            Node<K, V> node = this.node;
            index2.right = index;
            boolean bl = node.value != null && this.casRight(index, index2);
            return bl;
        }

        final boolean unlink(Index<K, V> index) {
            boolean bl = this.node.value != null && this.casRight(index, index.right);
            return bl;
        }
    }

    abstract class Iter<T>
    implements Iterator<T> {
        Node<K, V> lastReturned;
        Node<K, V> next;
        V nextValue;

        Iter() {
            block1 : {
                Object object;
                do {
                    object = ConcurrentSkipListMap.this.findFirst();
                    this.next = object;
                    if (object == null) break block1;
                } while ((object = this.next.value) == null || object == this.next);
                this.nextValue = object;
            }
        }

        final void advance() {
            Node<K, V> node = this.next;
            if (node != null) {
                block2 : {
                    this.lastReturned = node;
                    do {
                        node = this.next.next;
                        this.next = node;
                        if (node == null) break block2;
                    } while ((node = this.next.value) == null || node == this.next);
                    this.nextValue = node;
                }
                return;
            }
            throw new NoSuchElementException();
        }

        @Override
        public final boolean hasNext() {
            boolean bl = this.next != null;
            return bl;
        }

        @Override
        public void remove() {
            Node<K, V> node = this.lastReturned;
            if (node != null) {
                ConcurrentSkipListMap.this.remove(node.key);
                this.lastReturned = null;
                return;
            }
            throw new IllegalStateException();
        }
    }

    final class KeyIterator
    extends ConcurrentSkipListMap<K, V> {
        KeyIterator() {
        }

        public K next() {
            Node node = this.next;
            this.advance();
            return node.key;
        }
    }

    static final class KeySet<K, V>
    extends AbstractSet<K>
    implements NavigableSet<K> {
        final ConcurrentNavigableMap<K, V> m;

        KeySet(ConcurrentNavigableMap<K, V> concurrentNavigableMap) {
            this.m = concurrentNavigableMap;
        }

        @Override
        public K ceiling(K k) {
            return this.m.ceilingKey(k);
        }

        @Override
        public void clear() {
            this.m.clear();
        }

        @Override
        public Comparator<? super K> comparator() {
            return this.m.comparator();
        }

        @Override
        public boolean contains(Object object) {
            return this.m.containsKey(object);
        }

        @Override
        public Iterator<K> descendingIterator() {
            return this.descendingSet().iterator();
        }

        @Override
        public NavigableSet<K> descendingSet() {
            return new KeySet<K, V>((ConcurrentNavigableMap<K, V>)this.m.descendingMap());
        }

        @Override
        public boolean equals(Object object) {
            boolean bl = true;
            if (object == this) {
                return true;
            }
            if (!(object instanceof Set)) {
                return false;
            }
            object = (Collection)object;
            try {
                boolean bl2;
                if (!this.containsAll((Collection<?>)object) || !(bl2 = object.containsAll(this))) {
                    bl = false;
                }
                return bl;
            }
            catch (NullPointerException nullPointerException) {
                return false;
            }
            catch (ClassCastException classCastException) {
                return false;
            }
        }

        @Override
        public K first() {
            return this.m.firstKey();
        }

        @Override
        public K floor(K k) {
            return this.m.floorKey(k);
        }

        @Override
        public NavigableSet<K> headSet(K k) {
            return this.headSet(k, false);
        }

        @Override
        public NavigableSet<K> headSet(K k, boolean bl) {
            return new KeySet<K, V>((ConcurrentNavigableMap<K, V>)this.m.headMap((Object)k, bl));
        }

        @Override
        public K higher(K k) {
            return this.m.higherKey(k);
        }

        @Override
        public boolean isEmpty() {
            return this.m.isEmpty();
        }

        @Override
        public Iterator<K> iterator() {
            AbstractMap abstractMap = this.m;
            if (abstractMap instanceof ConcurrentSkipListMap) {
                abstractMap = abstractMap;
                Objects.requireNonNull(abstractMap);
                abstractMap = (ConcurrentSkipListMap)abstractMap.new KeyIterator();
            } else {
                abstractMap = (SubMap)abstractMap;
                Objects.requireNonNull(abstractMap);
                abstractMap = (SubMap)abstractMap.new SubMap.SubMapKeyIterator();
            }
            return abstractMap;
        }

        @Override
        public K last() {
            return this.m.lastKey();
        }

        @Override
        public K lower(K k) {
            return this.m.lowerKey(k);
        }

        @Override
        public K pollFirst() {
            Map.Entry entry = this.m.pollFirstEntry();
            entry = entry == null ? null : entry.getKey();
            return (K)entry;
        }

        @Override
        public K pollLast() {
            Map.Entry entry = this.m.pollLastEntry();
            entry = entry == null ? null : entry.getKey();
            return (K)entry;
        }

        @Override
        public boolean remove(Object object) {
            boolean bl = this.m.remove(object) != null;
            return bl;
        }

        @Override
        public int size() {
            return this.m.size();
        }

        @Override
        public Spliterator<K> spliterator() {
            Object object = this.m;
            if (object instanceof ConcurrentSkipListMap) {
                object = ((ConcurrentSkipListMap)object).keySpliterator();
            } else {
                object = (SubMap)object;
                Objects.requireNonNull(object);
                object = (SubMap)object.new SubMap.SubMapKeyIterator();
            }
            return object;
        }

        @Override
        public NavigableSet<K> subSet(K k, K k2) {
            return this.subSet(k, true, k2, false);
        }

        @Override
        public NavigableSet<K> subSet(K k, boolean bl, K k2, boolean bl2) {
            return new KeySet<K, V>((ConcurrentNavigableMap<K, V>)this.m.subMap((Object)k, bl, (Object)k2, bl2));
        }

        @Override
        public NavigableSet<K> tailSet(K k) {
            return this.tailSet(k, true);
        }

        @Override
        public NavigableSet<K> tailSet(K k, boolean bl) {
            return new KeySet<K, V>((ConcurrentNavigableMap<K, V>)this.m.tailMap((Object)k, bl));
        }

        @Override
        public Object[] toArray() {
            return ConcurrentSkipListMap.toList(this).toArray();
        }

        @Override
        public <T> T[] toArray(T[] arrT) {
            return ConcurrentSkipListMap.toList(this).toArray(arrT);
        }
    }

    static final class KeySpliterator<K, V>
    extends CSLMSpliterator<K, V>
    implements Spliterator<K> {
        KeySpliterator(Comparator<? super K> comparator, Index<K, V> index, Node<K, V> node, K k, int n) {
            super(comparator, index, node, k, n);
        }

        @Override
        public int characteristics() {
            return 4373;
        }

        @Override
        public void forEachRemaining(Consumer<? super K> consumer) {
            if (consumer != null) {
                Object k;
                Comparator comparator = this.comparator;
                Object object = this.fence;
                Node node = this.current;
                this.current = null;
                while (node != null && ((k = node.key) == null || object == null || ConcurrentSkipListMap.cpr(comparator, object, k) > 0)) {
                    Object object2 = node.value;
                    if (object2 != null && object2 != node) {
                        consumer.accept(k);
                    }
                    node = node.next;
                }
                return;
            }
            throw new NullPointerException();
        }

        @Override
        public final Comparator<? super K> getComparator() {
            return this.comparator;
        }

        @Override
        public boolean tryAdvance(Consumer<? super K> consumer) {
            if (consumer != null) {
                Object object;
                Comparator comparator = this.comparator;
                Object object2 = this.fence;
                Node node = this.current;
                do {
                    object = node;
                    if (node == null) break;
                    Object k = node.key;
                    if (k != null && object2 != null && ConcurrentSkipListMap.cpr(comparator, object2, k) <= 0) {
                        object = null;
                        break;
                    }
                    object = node.value;
                    if (object != null && object != node) {
                        this.current = node.next;
                        consumer.accept(k);
                        return true;
                    }
                    node = node.next;
                } while (true);
                this.current = object;
                return false;
            }
            throw new NullPointerException();
        }

        public KeySpliterator<K, V> trySplit() {
            Object k;
            Comparator comparator = this.comparator;
            Index index = this.fence;
            Node node = this.current;
            if (node != null && (k = node.key) != null) {
                Index index2 = this.row;
                while (index2 != null) {
                    Node node2;
                    Node node3;
                    Index index3 = index2.right;
                    if (index3 != null && (node3 = index3.node) != null && (node2 = node3.next) != null && node2.value != null && (node3 = node2.key) != null && ConcurrentSkipListMap.cpr(comparator, node3, k) > 0 && (index == null || ConcurrentSkipListMap.cpr(comparator, node3, index) < 0)) {
                        this.current = node2;
                        index = index2.down;
                        index2 = index3.right != null ? index3 : index3.down;
                        this.row = index2;
                        this.est -= this.est >>> 2;
                        return new KeySpliterator(comparator, index, node, node3, this.est);
                    }
                    this.row = index2 = index2.down;
                }
            }
            return null;
        }
    }

    static final class Node<K, V> {
        private static final long NEXT;
        private static final Unsafe U;
        private static final long VALUE;
        final K key;
        volatile Node<K, V> next;
        volatile Object value;

        static {
            U = Unsafe.getUnsafe();
            try {
                VALUE = U.objectFieldOffset(Node.class.getDeclaredField("value"));
                NEXT = U.objectFieldOffset(Node.class.getDeclaredField("next"));
                return;
            }
            catch (ReflectiveOperationException reflectiveOperationException) {
                throw new Error(reflectiveOperationException);
            }
        }

        Node(K k, Object object, Node<K, V> node) {
            this.key = k;
            this.value = object;
            this.next = node;
        }

        Node(Node<K, V> node) {
            this.key = null;
            this.value = this;
            this.next = node;
        }

        boolean appendMarker(Node<K, V> node) {
            return this.casNext(node, new Node<K, V>(node));
        }

        boolean casNext(Node<K, V> node, Node<K, V> node2) {
            return U.compareAndSwapObject(this, NEXT, node, node2);
        }

        boolean casValue(Object object, Object object2) {
            return U.compareAndSwapObject(this, VALUE, object, object2);
        }

        AbstractMap.SimpleImmutableEntry<K, V> createSnapshot() {
            Object object = this.value;
            if (object != null && object != this && object != BASE_HEADER) {
                return new AbstractMap.SimpleImmutableEntry<K, Object>(this.key, object);
            }
            return null;
        }

        V getValidValue() {
            Object object = this.value;
            if (object != this && object != BASE_HEADER) {
                return (V)object;
            }
            return null;
        }

        void helpDelete(Node<K, V> node, Node<K, V> node2) {
            if (node2 == this.next && this == node.next) {
                if (node2 != null && node2.value == node2) {
                    node.casNext(this, node2.next);
                } else {
                    this.casNext(node2, new Node<K, V>(node2));
                }
            }
        }

        boolean isBaseHeader() {
            boolean bl = this.value == BASE_HEADER;
            return bl;
        }

        boolean isMarker() {
            boolean bl = this.value == this;
            return bl;
        }
    }

    static final class SubMap<K, V>
    extends AbstractMap<K, V>
    implements ConcurrentNavigableMap<K, V>,
    Cloneable,
    Serializable {
        private static final long serialVersionUID = -7647078645895051609L;
        private transient Set<Map.Entry<K, V>> entrySetView;
        private final K hi;
        private final boolean hiInclusive;
        final boolean isDescending;
        private transient KeySet<K, V> keySetView;
        private final K lo;
        private final boolean loInclusive;
        final ConcurrentSkipListMap<K, V> m;
        private transient Collection<V> valuesView;

        SubMap(ConcurrentSkipListMap<K, V> concurrentSkipListMap, K k, boolean bl, K k2, boolean bl2, boolean bl3) {
            Comparator comparator = concurrentSkipListMap.comparator;
            if (k != null && k2 != null && ConcurrentSkipListMap.cpr(comparator, k, k2) > 0) {
                throw new IllegalArgumentException("inconsistent range");
            }
            this.m = concurrentSkipListMap;
            this.lo = k;
            this.hi = k2;
            this.loInclusive = bl;
            this.hiInclusive = bl2;
            this.isDescending = bl3;
        }

        @Override
        public Map.Entry<K, V> ceilingEntry(K k) {
            return this.getNearEntry(k, 1);
        }

        @Override
        public K ceilingKey(K k) {
            return this.getNearKey(k, 1);
        }

        void checkKeyBounds(K k, Comparator<? super K> comparator) {
            if (k != null) {
                if (this.inBounds(k, comparator)) {
                    return;
                }
                throw new IllegalArgumentException("key out of range");
            }
            throw new NullPointerException();
        }

        @Override
        public void clear() {
            Comparator comparator = this.m.comparator;
            Node node = this.loNode(comparator);
            while (this.isBeforeEnd(node, comparator)) {
                if (node.getValidValue() != null) {
                    this.m.remove(node.key);
                }
                node = node.next;
            }
        }

        @Override
        public Comparator<? super K> comparator() {
            Comparator<K> comparator = this.m.comparator();
            if (this.isDescending) {
                return Collections.reverseOrder(comparator);
            }
            return comparator;
        }

        @Override
        public boolean containsKey(Object object) {
            if (object != null) {
                boolean bl = this.inBounds(object, this.m.comparator) && this.m.containsKey(object);
                return bl;
            }
            throw new NullPointerException();
        }

        @Override
        public boolean containsValue(Object object) {
            if (object != null) {
                Comparator comparator = this.m.comparator;
                Node node = this.loNode(comparator);
                while (this.isBeforeEnd(node, comparator)) {
                    V v = node.getValidValue();
                    if (v != null && object.equals(v)) {
                        return true;
                    }
                    node = node.next;
                }
                return false;
            }
            throw new NullPointerException();
        }

        @Override
        public NavigableSet<K> descendingKeySet() {
            return ((SubMap)this.descendingMap()).navigableKeySet();
        }

        @Override
        public SubMap<K, V> descendingMap() {
            return new SubMap<K, V>(this.m, this.lo, this.loInclusive, this.hi, this.hiInclusive, this.isDescending ^ true);
        }

        @Override
        public Set<Map.Entry<K, V>> entrySet() {
            Set<Map.Entry<K, V>> set = this.entrySetView;
            if (set == null) {
                this.entrySetView = set = new EntrySet(this);
            }
            return set;
        }

        @Override
        public Map.Entry<K, V> firstEntry() {
            Map.Entry<K, V> entry = this.isDescending ? this.highestEntry() : this.lowestEntry();
            return entry;
        }

        @Override
        public K firstKey() {
            K k = this.isDescending ? this.highestKey() : this.lowestKey();
            return k;
        }

        @Override
        public Map.Entry<K, V> floorEntry(K k) {
            return this.getNearEntry(k, 3);
        }

        @Override
        public K floorKey(K k) {
            return this.getNearKey(k, 3);
        }

        @Override
        public V get(Object object) {
            if (object != null) {
                object = !this.inBounds(object, this.m.comparator) ? null : this.m.get(object);
                return (V)object;
            }
            throw new NullPointerException();
        }

        Map.Entry<K, V> getNearEntry(K object, int n) {
            Comparator comparator = this.m.comparator;
            int n2 = n;
            if (this.isDescending) {
                n2 = (n & 2) == 0 ? n | 2 : n & -3;
            }
            boolean bl = this.tooLow(object, comparator);
            Node node = null;
            Object k = null;
            if (bl) {
                object = (n2 & 2) != 0 ? k : this.lowestEntry();
                return object;
            }
            if (this.tooHigh(object, comparator)) {
                object = node;
                if ((n2 & 2) != 0) {
                    object = this.highestEntry();
                }
                return object;
            }
            while ((node = this.m.findNear(object, n2, comparator)) != null && this.inBounds(node.key, comparator)) {
                k = node.key;
                if ((node = node.getValidValue()) == null) continue;
                return new AbstractMap.SimpleImmutableEntry(k, node);
            }
            return null;
        }

        K getNearKey(K object, int n) {
            Node node;
            Comparator comparator = this.m.comparator;
            int n2 = n;
            if (this.isDescending) {
                n2 = (n & 2) == 0 ? n | 2 : n & -3;
            }
            if (this.tooLow(object, comparator)) {
                if ((n2 & 2) == 0 && this.isBeforeEnd((Node<K, V>)(object = this.loNode(comparator)), comparator)) {
                    return ((Node)object).key;
                }
                return null;
            }
            if (this.tooHigh(object, comparator)) {
                if ((n2 & 2) != 0 && (object = this.hiNode(comparator)) != null && this.inBounds(object = ((Node)object).key, comparator)) {
                    return object;
                }
                return null;
            }
            while ((node = this.m.findNear(object, n2, comparator)) != null && this.inBounds(node.key, comparator)) {
                Object k = node.key;
                if (node.getValidValue() == null) continue;
                return k;
            }
            return null;
        }

        @Override
        public SubMap<K, V> headMap(K k) {
            return this.headMap((Object)k, false);
        }

        @Override
        public SubMap<K, V> headMap(K k, boolean bl) {
            if (k != null) {
                return this.newSubMap(null, false, k, bl);
            }
            throw new NullPointerException();
        }

        Node<K, V> hiNode(Comparator<? super K> comparator) {
            K k = this.hi;
            if (k == null) {
                return this.m.findLast();
            }
            if (this.hiInclusive) {
                return this.m.findNear((K)k, 3, comparator);
            }
            return this.m.findNear((K)k, 2, comparator);
        }

        @Override
        public Map.Entry<K, V> higherEntry(K k) {
            return this.getNearEntry(k, 0);
        }

        @Override
        public K higherKey(K k) {
            return this.getNearKey(k, 0);
        }

        Map.Entry<K, V> highestEntry() {
            Object object;
            Comparator comparator = this.m.comparator;
            while ((object = this.hiNode(comparator)) != null && this.inBounds(((Node)object).key, comparator)) {
                if ((object = ((Node)object).createSnapshot()) == null) continue;
                return object;
            }
            return null;
        }

        K highestKey() {
            Comparator comparator = this.m.comparator;
            Node<Object, V> node = this.hiNode(comparator);
            if (node != null && this.inBounds(node = node.key, comparator)) {
                return (K)node;
            }
            throw new NoSuchElementException();
        }

        boolean inBounds(Object object, Comparator<? super K> comparator) {
            boolean bl = !this.tooLow(object, comparator) && !this.tooHigh(object, comparator);
            return bl;
        }

        boolean isBeforeEnd(Node<K, V> node, Comparator<? super K> comparator) {
            if (node == null) {
                return false;
            }
            if (this.hi == null) {
                return true;
            }
            node = node.key;
            if (node == null) {
                return true;
            }
            int n = ConcurrentSkipListMap.cpr(comparator, node, this.hi);
            return n <= 0 && (n != 0 || this.hiInclusive);
            {
            }
        }

        @Override
        public boolean isEmpty() {
            Comparator comparator = this.m.comparator;
            return this.isBeforeEnd(this.loNode(comparator), comparator) ^ true;
        }

        @Override
        public NavigableSet<K> keySet() {
            KeySet<K, V> keySet = this.keySetView;
            if (keySet == null) {
                this.keySetView = keySet = new KeySet(this);
            }
            return keySet;
        }

        @Override
        public Map.Entry<K, V> lastEntry() {
            Map.Entry<K, V> entry = this.isDescending ? this.lowestEntry() : this.highestEntry();
            return entry;
        }

        @Override
        public K lastKey() {
            K k = this.isDescending ? this.lowestKey() : this.highestKey();
            return k;
        }

        Node<K, V> loNode(Comparator<? super K> comparator) {
            K k = this.lo;
            if (k == null) {
                return this.m.findFirst();
            }
            if (this.loInclusive) {
                return this.m.findNear((K)k, 1, comparator);
            }
            return this.m.findNear((K)k, 0, comparator);
        }

        @Override
        public Map.Entry<K, V> lowerEntry(K k) {
            return this.getNearEntry(k, 2);
        }

        @Override
        public K lowerKey(K k) {
            return this.getNearKey(k, 2);
        }

        Map.Entry<K, V> lowestEntry() {
            Object object;
            Comparator comparator = this.m.comparator;
            do {
                if (this.isBeforeEnd((Node<K, V>)(object = this.loNode(comparator)), comparator)) continue;
                return null;
            } while ((object = ((Node)object).createSnapshot()) == null);
            return object;
        }

        K lowestKey() {
            Comparator comparator = this.m.comparator;
            Node node = this.loNode(comparator);
            if (this.isBeforeEnd(node, comparator)) {
                return node.key;
            }
            throw new NoSuchElementException();
        }

        @Override
        public NavigableSet<K> navigableKeySet() {
            KeySet<K, V> keySet = this.keySetView;
            if (keySet == null) {
                this.keySetView = keySet = new KeySet(this);
            }
            return keySet;
        }

        SubMap<K, V> newSubMap(K k, boolean bl, K k2, boolean bl2) {
            int n;
            Comparator comparator = this.m.comparator;
            K k3 = k;
            boolean bl3 = bl;
            K k4 = k2;
            boolean bl4 = bl2;
            if (this.isDescending) {
                bl4 = bl;
                k4 = k;
                bl3 = bl2;
                k3 = k2;
            }
            k2 = this.lo;
            k = k3;
            bl = bl3;
            if (k2 != null) {
                if (k3 == null) {
                    k = this.lo;
                    bl = this.loInclusive;
                } else {
                    n = ConcurrentSkipListMap.cpr(comparator, k3, k2);
                    if (n >= 0 && (n != 0 || this.loInclusive || !bl3)) {
                        k = k3;
                        bl = bl3;
                    } else {
                        throw new IllegalArgumentException("key out of range");
                    }
                }
            }
            k3 = this.hi;
            k2 = k4;
            bl2 = bl4;
            if (k3 != null) {
                if (k4 == null) {
                    k2 = this.hi;
                    bl2 = this.hiInclusive;
                } else {
                    n = ConcurrentSkipListMap.cpr(comparator, k4, k3);
                    if (n <= 0 && (n != 0 || this.hiInclusive || !bl4)) {
                        k2 = k4;
                        bl2 = bl4;
                    } else {
                        throw new IllegalArgumentException("key out of range");
                    }
                }
            }
            return new SubMap<K, V>(this.m, k, bl, k2, bl2, this.isDescending);
        }

        @Override
        public Map.Entry<K, V> pollFirstEntry() {
            Map.Entry<K, V> entry = this.isDescending ? this.removeHighest() : this.removeLowest();
            return entry;
        }

        @Override
        public Map.Entry<K, V> pollLastEntry() {
            Map.Entry<K, V> entry = this.isDescending ? this.removeLowest() : this.removeHighest();
            return entry;
        }

        @Override
        public V put(K k, V v) {
            this.checkKeyBounds(k, this.m.comparator);
            return this.m.put(k, v);
        }

        @Override
        public V putIfAbsent(K k, V v) {
            this.checkKeyBounds(k, this.m.comparator);
            return this.m.putIfAbsent(k, v);
        }

        @Override
        public V remove(Object object) {
            object = !this.inBounds(object, this.m.comparator) ? null : this.m.remove(object);
            return (V)object;
        }

        @Override
        public boolean remove(Object object, Object object2) {
            boolean bl = this.inBounds(object, this.m.comparator) && this.m.remove(object, object2);
            return bl;
        }

        Map.Entry<K, V> removeHighest() {
            Node<Object, V> node;
            V v;
            Comparator comparator = this.m.comparator;
            do {
                if ((node = this.hiNode(comparator)) == null) {
                    return null;
                }
                node = node.key;
                if (this.inBounds(node, comparator)) continue;
                return null;
            } while ((v = this.m.doRemove(node, null)) == null);
            return new AbstractMap.SimpleImmutableEntry(node, v);
        }

        Map.Entry<K, V> removeLowest() {
            Node<Object, V> node;
            Object k;
            Comparator comparator = this.m.comparator;
            do {
                if ((node = this.loNode(comparator)) == null) {
                    return null;
                }
                k = node.key;
                if (this.inBounds(k, comparator)) continue;
                return null;
            } while ((node = this.m.doRemove(k, null)) == null);
            return new AbstractMap.SimpleImmutableEntry(k, node);
        }

        @Override
        public V replace(K k, V v) {
            this.checkKeyBounds(k, this.m.comparator);
            return this.m.replace(k, v);
        }

        @Override
        public boolean replace(K k, V v, V v2) {
            this.checkKeyBounds(k, this.m.comparator);
            return this.m.replace(k, v, v2);
        }

        @Override
        public int size() {
            Comparator comparator = this.m.comparator;
            long l = 0L;
            Node node = this.loNode(comparator);
            while (this.isBeforeEnd(node, comparator)) {
                long l2 = l;
                if (node.getValidValue() != null) {
                    l2 = l + 1L;
                }
                node = node.next;
                l = l2;
            }
            int n = l >= Integer.MAX_VALUE ? Integer.MAX_VALUE : (int)l;
            return n;
        }

        @Override
        public SubMap<K, V> subMap(K k, K k2) {
            return this.subMap((Object)k, true, (Object)k2, false);
        }

        @Override
        public SubMap<K, V> subMap(K k, boolean bl, K k2, boolean bl2) {
            if (k != null && k2 != null) {
                return this.newSubMap(k, bl, k2, bl2);
            }
            throw new NullPointerException();
        }

        @Override
        public SubMap<K, V> tailMap(K k) {
            return this.tailMap((Object)k, true);
        }

        @Override
        public SubMap<K, V> tailMap(K k, boolean bl) {
            if (k != null) {
                return this.newSubMap(k, bl, null, false);
            }
            throw new NullPointerException();
        }

        boolean tooHigh(Object object, Comparator<? super K> comparator) {
            int n;
            K k = this.hi;
            boolean bl = k != null && ((n = ConcurrentSkipListMap.cpr(comparator, object, k)) > 0 || n == 0 && !this.hiInclusive);
            return bl;
        }

        boolean tooLow(Object object, Comparator<? super K> comparator) {
            int n;
            K k = this.lo;
            boolean bl = k != null && ((n = ConcurrentSkipListMap.cpr(comparator, object, k)) < 0 || n == 0 && !this.loInclusive);
            return bl;
        }

        @Override
        public Collection<V> values() {
            Collection<V> collection = this.valuesView;
            if (collection == null) {
                this.valuesView = collection = new Values(this);
            }
            return collection;
        }

        final class SubMapEntryIterator
        extends SubMap<K, V> {
            SubMapEntryIterator() {
            }

            public int characteristics() {
                return 1;
            }

            public Map.Entry<K, V> next() {
                Node node = this.next;
                Object object = this.nextValue;
                this.advance();
                return new AbstractMap.SimpleImmutableEntry(node.key, object);
            }
        }

        abstract class SubMapIter<T>
        implements Iterator<T>,
        Spliterator<T> {
            Node<K, V> lastReturned;
            Node<K, V> next;
            V nextValue;

            SubMapIter() {
                block3 : {
                    Object object;
                    Node node;
                    Comparator comparator = SubMap.this.m.comparator;
                    do {
                        node = SubMap.this.isDescending ? SubMap.this.hiNode(comparator) : SubMap.this.loNode(comparator);
                        this.next = node;
                        node = this.next;
                        if (node == null) break block3;
                    } while ((object = node.value) == null || object == (node = this.next));
                    if (!SubMap.this.inBounds(node.key, comparator)) {
                        this.next = null;
                    } else {
                        this.nextValue = object;
                    }
                }
            }

            private void ascend() {
                Node<K, V> node;
                Comparator comparator = SubMap.this.m.comparator;
                while ((node = (this.next = this.next.next)) != null) {
                    Object object = node.value;
                    if (object == null || object == (node = this.next)) continue;
                    if (SubMap.this.tooHigh(node.key, comparator)) {
                        this.next = null;
                        break;
                    }
                    this.nextValue = object;
                    break;
                }
            }

            private void descend() {
                Node<K, V> node;
                Comparator comparator = SubMap.this.m.comparator;
                while ((node = (this.next = SubMap.this.m.findNear(this.lastReturned.key, 2, comparator))) != null) {
                    Object object = node.value;
                    if (object == null || object == (node = this.next)) continue;
                    if (SubMap.this.tooLow(node.key, comparator)) {
                        this.next = null;
                        break;
                    }
                    this.nextValue = object;
                    break;
                }
            }

            final void advance() {
                Node<K, V> node = this.next;
                if (node != null) {
                    this.lastReturned = node;
                    if (SubMap.this.isDescending) {
                        this.descend();
                    } else {
                        this.ascend();
                    }
                    return;
                }
                throw new NoSuchElementException();
            }

            @Override
            public long estimateSize() {
                return Long.MAX_VALUE;
            }

            @Override
            public void forEachRemaining(Consumer<? super T> consumer) {
                while (this.hasNext()) {
                    consumer.accept(this.next());
                }
            }

            @Override
            public final boolean hasNext() {
                boolean bl = this.next != null;
                return bl;
            }

            @Override
            public void remove() {
                Node<K, V> node = this.lastReturned;
                if (node != null) {
                    SubMap.this.m.remove(node.key);
                    this.lastReturned = null;
                    return;
                }
                throw new IllegalStateException();
            }

            @Override
            public boolean tryAdvance(Consumer<? super T> consumer) {
                if (this.hasNext()) {
                    consumer.accept(this.next());
                    return true;
                }
                return false;
            }

            @Override
            public Spliterator<T> trySplit() {
                return null;
            }
        }

        final class SubMapKeyIterator
        extends SubMap<K, V> {
            SubMapKeyIterator() {
            }

            public int characteristics() {
                return 21;
            }

            public final Comparator<? super K> getComparator() {
                return SubMap.this.comparator();
            }

            public K next() {
                Node node = this.next;
                this.advance();
                return node.key;
            }
        }

        final class SubMapValueIterator
        extends SubMap<K, V> {
            SubMapValueIterator() {
            }

            public int characteristics() {
                return 0;
            }

            public V next() {
                Object object = this.nextValue;
                this.advance();
                return (V)object;
            }
        }

    }

    final class ValueIterator
    extends ConcurrentSkipListMap<K, V> {
        ValueIterator() {
        }

        public V next() {
            Object object = this.nextValue;
            this.advance();
            return (V)object;
        }
    }

    static final class ValueSpliterator<K, V>
    extends CSLMSpliterator<K, V>
    implements Spliterator<V> {
        ValueSpliterator(Comparator<? super K> comparator, Index<K, V> index, Node<K, V> node, K k, int n) {
            super(comparator, index, node, k, n);
        }

        @Override
        public int characteristics() {
            return 4368;
        }

        @Override
        public void forEachRemaining(Consumer<? super V> consumer) {
            if (consumer != null) {
                Object object;
                Comparator comparator = this.comparator;
                Object object2 = this.fence;
                Node node = this.current;
                this.current = null;
                while (node != null && ((object = node.key) == null || object2 == null || ConcurrentSkipListMap.cpr(comparator, object2, object) > 0)) {
                    object = node.value;
                    if (object != null && object != node) {
                        consumer.accept(object);
                    }
                    node = node.next;
                }
                return;
            }
            throw new NullPointerException();
        }

        @Override
        public boolean tryAdvance(Consumer<? super V> consumer) {
            if (consumer != null) {
                Object object;
                Comparator comparator = this.comparator;
                Object object2 = this.fence;
                Node node = this.current;
                do {
                    object = node;
                    if (node == null) break;
                    object = node.key;
                    if (object != null && object2 != null && ConcurrentSkipListMap.cpr(comparator, object2, object) <= 0) {
                        object = null;
                        break;
                    }
                    object = node.value;
                    if (object != null && object != node) {
                        this.current = node.next;
                        consumer.accept(object);
                        return true;
                    }
                    node = node.next;
                } while (true);
                this.current = object;
                return false;
            }
            throw new NullPointerException();
        }

        public ValueSpliterator<K, V> trySplit() {
            Object k;
            Comparator comparator = this.comparator;
            Index index = this.fence;
            Node node = this.current;
            if (node != null && (k = node.key) != null) {
                Index index2 = this.row;
                while (index2 != null) {
                    Node node2;
                    Node node3;
                    Index index3 = index2.right;
                    if (index3 != null && (node3 = index3.node) != null && (node2 = node3.next) != null && node2.value != null && (node3 = node2.key) != null && ConcurrentSkipListMap.cpr(comparator, node3, k) > 0 && (index == null || ConcurrentSkipListMap.cpr(comparator, node3, index) < 0)) {
                        this.current = node2;
                        index = index2.down;
                        index2 = index3.right != null ? index3 : index3.down;
                        this.row = index2;
                        this.est -= this.est >>> 2;
                        return new ValueSpliterator(comparator, index, node, node3, this.est);
                    }
                    this.row = index2 = index2.down;
                }
            }
            return null;
        }
    }

    static final class Values<K, V>
    extends AbstractCollection<V> {
        final ConcurrentNavigableMap<K, V> m;

        Values(ConcurrentNavigableMap<K, V> concurrentNavigableMap) {
            this.m = concurrentNavigableMap;
        }

        @Override
        public void clear() {
            this.m.clear();
        }

        @Override
        public boolean contains(Object object) {
            return this.m.containsValue(object);
        }

        @Override
        public boolean isEmpty() {
            return this.m.isEmpty();
        }

        @Override
        public Iterator<V> iterator() {
            AbstractMap abstractMap = this.m;
            if (abstractMap instanceof ConcurrentSkipListMap) {
                abstractMap = abstractMap;
                Objects.requireNonNull(abstractMap);
                abstractMap = (ConcurrentSkipListMap)abstractMap.new ValueIterator();
            } else {
                abstractMap = (SubMap)abstractMap;
                Objects.requireNonNull(abstractMap);
                abstractMap = (SubMap)abstractMap.new SubMap.SubMapValueIterator();
            }
            return abstractMap;
        }

        @Override
        public boolean removeIf(Predicate<? super V> predicate) {
            if (predicate != null) {
                Object object = this.m;
                if (object instanceof ConcurrentSkipListMap) {
                    return ((ConcurrentSkipListMap)object).removeValueIf(predicate);
                }
                object = (SubMap)object;
                Objects.requireNonNull(object);
                SubMap.SubMapEntryIterator subMapEntryIterator = (SubMap)object.new SubMap.SubMapEntryIterator();
                boolean bl = false;
                while (subMapEntryIterator.hasNext()) {
                    object = (Map.Entry)subMapEntryIterator.next();
                    Object v = object.getValue();
                    boolean bl2 = bl;
                    if (predicate.test(v)) {
                        bl2 = bl;
                        if (this.m.remove(object.getKey(), v)) {
                            bl2 = true;
                        }
                    }
                    bl = bl2;
                }
                return bl;
            }
            throw new NullPointerException();
        }

        @Override
        public int size() {
            return this.m.size();
        }

        @Override
        public Spliterator<V> spliterator() {
            Object object = this.m;
            if (object instanceof ConcurrentSkipListMap) {
                object = ((ConcurrentSkipListMap)object).valueSpliterator();
            } else {
                object = (SubMap)object;
                Objects.requireNonNull(object);
                object = (SubMap)object.new SubMap.SubMapValueIterator();
            }
            return object;
        }

        @Override
        public Object[] toArray() {
            return ConcurrentSkipListMap.toList(this).toArray();
        }

        @Override
        public <T> T[] toArray(T[] arrT) {
            return ConcurrentSkipListMap.toList(this).toArray(arrT);
        }
    }

}

