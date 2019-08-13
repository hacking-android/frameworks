/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  java.util.-$
 *  java.util.-$$Lambda
 *  java.util.-$$Lambda$TreeMap
 *  java.util.-$$Lambda$TreeMap$EntrySpliterator
 *  java.util.-$$Lambda$TreeMap$EntrySpliterator$YqCulUmBGNzQr1PJ_ERFnbxUmTQ
 *  java.util.TreeMap$NavigableSubMap.SubMapIterator
 *  java.util.TreeMap.PrivateEntryIterator
 */
package java.util;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.invoke.SerializedLambda;
import java.util.-$;
import java.util.AbstractCollection;
import java.util.AbstractMap;
import java.util.AbstractSet;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.Map;
import java.util.NavigableMap;
import java.util.NavigableSet;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.Spliterator;
import java.util.TreeMap;
import java.util._$$Lambda$TreeMap$EntrySpliterator$YqCulUmBGNzQr1PJ_ERFnbxUmTQ;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;

public class TreeMap<K, V>
extends AbstractMap<K, V>
implements NavigableMap<K, V>,
Cloneable,
Serializable {
    private static final boolean BLACK = true;
    private static final boolean RED = false;
    private static final Object UNBOUNDED = new Object();
    private static final long serialVersionUID = 919286545866124006L;
    private final Comparator<? super K> comparator;
    private transient NavigableMap<K, V> descendingMap;
    private transient TreeMap<K, V> entrySet;
    private transient int modCount = 0;
    private transient KeySet<K> navigableKeySet;
    private transient TreeMapEntry<K, V> root;
    private transient int size = 0;

    public TreeMap() {
        this.comparator = null;
    }

    public TreeMap(Comparator<? super K> comparator) {
        this.comparator = comparator;
    }

    public TreeMap(Map<? extends K, ? extends V> map) {
        this.comparator = null;
        this.putAll(map);
    }

    public TreeMap(SortedMap<K, ? extends V> sortedMap) {
        this.comparator = sortedMap.comparator();
        try {
            this.buildFromSorted(sortedMap.size(), sortedMap.entrySet().iterator(), null, null);
        }
        catch (ClassNotFoundException classNotFoundException) {
        }
        catch (IOException iOException) {
            // empty catch block
        }
    }

    private final TreeMapEntry<K, V> buildFromSorted(int n, int n2, int n3, int n4, Iterator<?> object, ObjectInputStream objectInputStream, V v) throws IOException, ClassNotFoundException {
        Object object2;
        Object object3;
        if (n3 < n2) {
            return null;
        }
        int n5 = n2 + n3 >>> 1;
        TreeMapEntry<K, V> treeMapEntry = null;
        if (n2 < n5) {
            treeMapEntry = this.buildFromSorted(n + 1, n2, n5 - 1, n4, (Iterator<?>)object, objectInputStream, v);
        }
        if (object != null) {
            if (v == null) {
                object2 = (Map.Entry)object.next();
                object3 = object2.getKey();
                object2 = object2.getValue();
            } else {
                object3 = object.next();
                object2 = v;
            }
        } else {
            Object object4 = objectInputStream.readObject();
            object3 = v != null ? v : objectInputStream.readObject();
            object2 = object3;
            object3 = object4;
        }
        object3 = new TreeMapEntry(object3, object2, null);
        if (n == n4) {
            ((TreeMapEntry)object3).color = false;
        }
        if (treeMapEntry != null) {
            ((TreeMapEntry)object3).left = treeMapEntry;
            treeMapEntry.parent = object3;
        }
        if (n5 < n3) {
            ((TreeMapEntry)object3).right = object = this.buildFromSorted(n + 1, n5 + 1, n3, n4, (Iterator<?>)object, objectInputStream, v);
            ((TreeMapEntry)object).parent = object3;
        }
        return object3;
    }

    private void buildFromSorted(int n, Iterator<?> iterator, ObjectInputStream objectInputStream, V v) throws IOException, ClassNotFoundException {
        this.size = n;
        this.root = this.buildFromSorted(0, 0, n - 1, TreeMap.computeRedLevel(n), iterator, objectInputStream, v);
    }

    private static <K, V> boolean colorOf(TreeMapEntry<K, V> treeMapEntry) {
        boolean bl = treeMapEntry == null ? true : treeMapEntry.color;
        return bl;
    }

    private static int computeRedLevel(int n) {
        int n2 = 0;
        --n;
        while (n >= 0) {
            ++n2;
            n = n / 2 - 1;
        }
        return n2;
    }

    private void deleteEntry(TreeMapEntry<K, V> treeMapEntry) {
        ++this.modCount;
        --this.size;
        TreeMapEntry<K, V> treeMapEntry2 = treeMapEntry;
        if (treeMapEntry.left != null) {
            treeMapEntry2 = treeMapEntry;
            if (treeMapEntry.right != null) {
                treeMapEntry2 = TreeMap.successor(treeMapEntry);
                treeMapEntry.key = treeMapEntry2.key;
                treeMapEntry.value = treeMapEntry2.value;
            }
        }
        if ((treeMapEntry = treeMapEntry2.left != null ? treeMapEntry2.left : treeMapEntry2.right) != null) {
            treeMapEntry.parent = treeMapEntry2.parent;
            if (treeMapEntry2.parent == null) {
                this.root = treeMapEntry;
            } else if (treeMapEntry2 == treeMapEntry2.parent.left) {
                treeMapEntry2.parent.left = treeMapEntry;
            } else {
                treeMapEntry2.parent.right = treeMapEntry;
            }
            treeMapEntry2.parent = null;
            treeMapEntry2.right = null;
            treeMapEntry2.left = null;
            if (treeMapEntry2.color) {
                this.fixAfterDeletion(treeMapEntry);
            }
        } else if (treeMapEntry2.parent == null) {
            this.root = null;
        } else {
            if (treeMapEntry2.color) {
                this.fixAfterDeletion(treeMapEntry2);
            }
            if (treeMapEntry2.parent != null) {
                if (treeMapEntry2 == treeMapEntry2.parent.left) {
                    treeMapEntry2.parent.left = null;
                } else if (treeMapEntry2 == treeMapEntry2.parent.right) {
                    treeMapEntry2.parent.right = null;
                }
                treeMapEntry2.parent = null;
            }
        }
    }

    static <K, V> Map.Entry<K, V> exportEntry(TreeMapEntry<K, V> entry) {
        entry = entry == null ? null : new AbstractMap.SimpleImmutableEntry<K, V>(entry);
        return entry;
    }

    private void fixAfterDeletion(TreeMapEntry<K, V> treeMapEntry) {
        while (treeMapEntry != this.root && TreeMap.colorOf(treeMapEntry)) {
            TreeMapEntry<K, V> treeMapEntry2;
            TreeMapEntry<K, V> treeMapEntry3;
            if (treeMapEntry == TreeMap.leftOf(TreeMap.parentOf(treeMapEntry))) {
                treeMapEntry3 = treeMapEntry2 = TreeMap.rightOf(TreeMap.parentOf(treeMapEntry));
                if (!TreeMap.colorOf(treeMapEntry2)) {
                    TreeMap.setColor(treeMapEntry2, true);
                    TreeMap.setColor(TreeMap.parentOf(treeMapEntry), false);
                    this.rotateLeft(TreeMap.parentOf(treeMapEntry));
                    treeMapEntry3 = TreeMap.rightOf(TreeMap.parentOf(treeMapEntry));
                }
                if (TreeMap.colorOf(TreeMap.leftOf(treeMapEntry3)) && TreeMap.colorOf(TreeMap.rightOf(treeMapEntry3))) {
                    TreeMap.setColor(treeMapEntry3, false);
                    treeMapEntry = TreeMap.parentOf(treeMapEntry);
                    continue;
                }
                treeMapEntry2 = treeMapEntry3;
                if (TreeMap.colorOf(TreeMap.rightOf(treeMapEntry3))) {
                    TreeMap.setColor(TreeMap.leftOf(treeMapEntry3), true);
                    TreeMap.setColor(treeMapEntry3, false);
                    this.rotateRight(treeMapEntry3);
                    treeMapEntry2 = TreeMap.rightOf(TreeMap.parentOf(treeMapEntry));
                }
                TreeMap.setColor(treeMapEntry2, TreeMap.colorOf(TreeMap.parentOf(treeMapEntry)));
                TreeMap.setColor(TreeMap.parentOf(treeMapEntry), true);
                TreeMap.setColor(TreeMap.rightOf(treeMapEntry2), true);
                this.rotateLeft(TreeMap.parentOf(treeMapEntry));
                treeMapEntry = this.root;
                continue;
            }
            treeMapEntry3 = treeMapEntry2 = TreeMap.leftOf(TreeMap.parentOf(treeMapEntry));
            if (!TreeMap.colorOf(treeMapEntry2)) {
                TreeMap.setColor(treeMapEntry2, true);
                TreeMap.setColor(TreeMap.parentOf(treeMapEntry), false);
                this.rotateRight(TreeMap.parentOf(treeMapEntry));
                treeMapEntry3 = TreeMap.leftOf(TreeMap.parentOf(treeMapEntry));
            }
            if (TreeMap.colorOf(TreeMap.rightOf(treeMapEntry3)) && TreeMap.colorOf(TreeMap.leftOf(treeMapEntry3))) {
                TreeMap.setColor(treeMapEntry3, false);
                treeMapEntry = TreeMap.parentOf(treeMapEntry);
                continue;
            }
            treeMapEntry2 = treeMapEntry3;
            if (TreeMap.colorOf(TreeMap.leftOf(treeMapEntry3))) {
                TreeMap.setColor(TreeMap.rightOf(treeMapEntry3), true);
                TreeMap.setColor(treeMapEntry3, false);
                this.rotateLeft(treeMapEntry3);
                treeMapEntry2 = TreeMap.leftOf(TreeMap.parentOf(treeMapEntry));
            }
            TreeMap.setColor(treeMapEntry2, TreeMap.colorOf(TreeMap.parentOf(treeMapEntry)));
            TreeMap.setColor(TreeMap.parentOf(treeMapEntry), true);
            TreeMap.setColor(TreeMap.leftOf(treeMapEntry2), true);
            this.rotateRight(TreeMap.parentOf(treeMapEntry));
            treeMapEntry = this.root;
        }
        TreeMap.setColor(treeMapEntry, true);
    }

    private void fixAfterInsertion(TreeMapEntry<K, V> treeMapEntry) {
        treeMapEntry.color = false;
        while (treeMapEntry != null && treeMapEntry != this.root && !treeMapEntry.parent.color) {
            TreeMapEntry<K, V> treeMapEntry2;
            if (TreeMap.parentOf(treeMapEntry) == TreeMap.leftOf(TreeMap.parentOf(TreeMap.parentOf(treeMapEntry)))) {
                treeMapEntry2 = TreeMap.rightOf(TreeMap.parentOf(TreeMap.parentOf(treeMapEntry)));
                if (!TreeMap.colorOf(treeMapEntry2)) {
                    TreeMap.setColor(TreeMap.parentOf(treeMapEntry), true);
                    TreeMap.setColor(treeMapEntry2, true);
                    TreeMap.setColor(TreeMap.parentOf(TreeMap.parentOf(treeMapEntry)), false);
                    treeMapEntry = TreeMap.parentOf(TreeMap.parentOf(treeMapEntry));
                    continue;
                }
                treeMapEntry2 = treeMapEntry;
                if (treeMapEntry == TreeMap.rightOf(TreeMap.parentOf(treeMapEntry))) {
                    treeMapEntry2 = TreeMap.parentOf(treeMapEntry);
                    this.rotateLeft(treeMapEntry2);
                }
                TreeMap.setColor(TreeMap.parentOf(treeMapEntry2), true);
                TreeMap.setColor(TreeMap.parentOf(TreeMap.parentOf(treeMapEntry2)), false);
                this.rotateRight(TreeMap.parentOf(TreeMap.parentOf(treeMapEntry2)));
                treeMapEntry = treeMapEntry2;
                continue;
            }
            treeMapEntry2 = TreeMap.leftOf(TreeMap.parentOf(TreeMap.parentOf(treeMapEntry)));
            if (!TreeMap.colorOf(treeMapEntry2)) {
                TreeMap.setColor(TreeMap.parentOf(treeMapEntry), true);
                TreeMap.setColor(treeMapEntry2, true);
                TreeMap.setColor(TreeMap.parentOf(TreeMap.parentOf(treeMapEntry)), false);
                treeMapEntry = TreeMap.parentOf(TreeMap.parentOf(treeMapEntry));
                continue;
            }
            treeMapEntry2 = treeMapEntry;
            if (treeMapEntry == TreeMap.leftOf(TreeMap.parentOf(treeMapEntry))) {
                treeMapEntry2 = TreeMap.parentOf(treeMapEntry);
                this.rotateRight(treeMapEntry2);
            }
            TreeMap.setColor(TreeMap.parentOf(treeMapEntry2), true);
            TreeMap.setColor(TreeMap.parentOf(TreeMap.parentOf(treeMapEntry2)), false);
            this.rotateLeft(TreeMap.parentOf(TreeMap.parentOf(treeMapEntry2)));
            treeMapEntry = treeMapEntry2;
        }
        this.root.color = true;
    }

    static <K> K key(TreeMapEntry<K, ?> treeMapEntry) {
        if (treeMapEntry != null) {
            return treeMapEntry.key;
        }
        throw new NoSuchElementException();
    }

    static <K, V> K keyOrNull(TreeMapEntry<K, V> treeMapEntry) {
        treeMapEntry = treeMapEntry == null ? null : treeMapEntry.key;
        return (K)treeMapEntry;
    }

    static <K> Spliterator<K> keySpliteratorFor(NavigableMap<K, ?> navigableMap) {
        if (navigableMap instanceof TreeMap) {
            return ((TreeMap)navigableMap).keySpliterator();
        }
        if (navigableMap instanceof DescendingSubMap) {
            DescendingSubMap descendingSubMap = (DescendingSubMap)navigableMap;
            TreeMap treeMap = descendingSubMap.m;
            if (descendingSubMap == treeMap.descendingMap) {
                return treeMap.descendingKeySpliterator();
            }
        }
        return ((NavigableSubMap)navigableMap).keySpliterator();
    }

    private static <K, V> TreeMapEntry<K, V> leftOf(TreeMapEntry<K, V> treeMapEntry) {
        treeMapEntry = treeMapEntry == null ? null : treeMapEntry.left;
        return treeMapEntry;
    }

    private static <K, V> TreeMapEntry<K, V> parentOf(TreeMapEntry<K, V> treeMapEntry) {
        treeMapEntry = treeMapEntry == null ? null : treeMapEntry.parent;
        return treeMapEntry;
    }

    static <K, V> TreeMapEntry<K, V> predecessor(TreeMapEntry<K, V> treeMapEntry) {
        if (treeMapEntry == null) {
            return null;
        }
        if (treeMapEntry.left != null) {
            treeMapEntry = treeMapEntry.left;
            while (treeMapEntry.right != null) {
                treeMapEntry = treeMapEntry.right;
            }
            return treeMapEntry;
        }
        TreeMapEntry treeMapEntry2 = treeMapEntry.parent;
        TreeMapEntry<K, V> treeMapEntry3 = treeMapEntry;
        treeMapEntry = treeMapEntry2;
        while (treeMapEntry != null && treeMapEntry3 == treeMapEntry.left) {
            treeMapEntry3 = treeMapEntry;
            treeMapEntry = treeMapEntry.parent;
        }
        return treeMapEntry;
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        this.buildFromSorted(objectInputStream.readInt(), null, objectInputStream, null);
    }

    private static <K, V> TreeMapEntry<K, V> rightOf(TreeMapEntry<K, V> treeMapEntry) {
        treeMapEntry = treeMapEntry == null ? null : treeMapEntry.right;
        return treeMapEntry;
    }

    private void rotateLeft(TreeMapEntry<K, V> treeMapEntry) {
        if (treeMapEntry != null) {
            TreeMapEntry treeMapEntry2 = treeMapEntry.right;
            treeMapEntry.right = treeMapEntry2.left;
            if (treeMapEntry2.left != null) {
                treeMapEntry2.left.parent = treeMapEntry;
            }
            treeMapEntry2.parent = treeMapEntry.parent;
            if (treeMapEntry.parent == null) {
                this.root = treeMapEntry2;
            } else if (treeMapEntry.parent.left == treeMapEntry) {
                treeMapEntry.parent.left = treeMapEntry2;
            } else {
                treeMapEntry.parent.right = treeMapEntry2;
            }
            treeMapEntry2.left = treeMapEntry;
            treeMapEntry.parent = treeMapEntry2;
        }
    }

    private void rotateRight(TreeMapEntry<K, V> treeMapEntry) {
        if (treeMapEntry != null) {
            TreeMapEntry treeMapEntry2 = treeMapEntry.left;
            treeMapEntry.left = treeMapEntry2.right;
            if (treeMapEntry2.right != null) {
                treeMapEntry2.right.parent = treeMapEntry;
            }
            treeMapEntry2.parent = treeMapEntry.parent;
            if (treeMapEntry.parent == null) {
                this.root = treeMapEntry2;
            } else if (treeMapEntry.parent.right == treeMapEntry) {
                treeMapEntry.parent.right = treeMapEntry2;
            } else {
                treeMapEntry.parent.left = treeMapEntry2;
            }
            treeMapEntry2.right = treeMapEntry;
            treeMapEntry.parent = treeMapEntry2;
        }
    }

    private static <K, V> void setColor(TreeMapEntry<K, V> treeMapEntry, boolean bl) {
        if (treeMapEntry != null) {
            treeMapEntry.color = bl;
        }
    }

    static <K, V> TreeMapEntry<K, V> successor(TreeMapEntry<K, V> treeMapEntry) {
        if (treeMapEntry == null) {
            return null;
        }
        if (treeMapEntry.right != null) {
            treeMapEntry = treeMapEntry.right;
            while (treeMapEntry.left != null) {
                treeMapEntry = treeMapEntry.left;
            }
            return treeMapEntry;
        }
        TreeMapEntry treeMapEntry2 = treeMapEntry.parent;
        TreeMapEntry<K, V> treeMapEntry3 = treeMapEntry;
        treeMapEntry = treeMapEntry2;
        while (treeMapEntry != null && treeMapEntry3 == treeMapEntry.right) {
            treeMapEntry3 = treeMapEntry;
            treeMapEntry = treeMapEntry.parent;
        }
        return treeMapEntry;
    }

    static final boolean valEquals(Object object, Object object2) {
        boolean bl = object == null ? object2 == null : object.equals(object2);
        return bl;
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        objectOutputStream.writeInt(this.size);
        for (Map.Entry<K, V> entry : this.entrySet()) {
            objectOutputStream.writeObject(entry.getKey());
            objectOutputStream.writeObject(entry.getValue());
        }
    }

    void addAllForTreeSet(SortedSet<? extends K> sortedSet, V v) {
        try {
            this.buildFromSorted(sortedSet.size(), sortedSet.iterator(), null, v);
        }
        catch (ClassNotFoundException classNotFoundException) {
        }
        catch (IOException iOException) {
            // empty catch block
        }
    }

    @Override
    public Map.Entry<K, V> ceilingEntry(K k) {
        return TreeMap.exportEntry(this.getCeilingEntry(k));
    }

    @Override
    public K ceilingKey(K k) {
        return TreeMap.keyOrNull(this.getCeilingEntry(k));
    }

    @Override
    public void clear() {
        ++this.modCount;
        this.size = 0;
        this.root = null;
    }

    @Override
    public Object clone() {
        TreeMap treeMap;
        try {
            treeMap = (TreeMap)super.clone();
            treeMap.root = null;
            treeMap.size = 0;
            treeMap.modCount = 0;
            treeMap.entrySet = null;
            treeMap.navigableKeySet = null;
            treeMap.descendingMap = null;
        }
        catch (CloneNotSupportedException cloneNotSupportedException) {
            throw new InternalError(cloneNotSupportedException);
        }
        try {
            treeMap.buildFromSorted(this.size, this.entrySet().iterator(), null, null);
        }
        catch (ClassNotFoundException classNotFoundException) {
        }
        catch (IOException iOException) {
            // empty catch block
        }
        return treeMap;
    }

    @Override
    public Comparator<? super K> comparator() {
        return this.comparator;
    }

    final int compare(Object object, Object object2) {
        Comparator<K> comparator = this.comparator;
        int n = comparator == null ? ((Comparable)object).compareTo(object2) : comparator.compare(object, object2);
        return n;
    }

    @Override
    public boolean containsKey(Object object) {
        boolean bl = this.getEntry(object) != null;
        return bl;
    }

    @Override
    public boolean containsValue(Object object) {
        TreeMapEntry<K, V> treeMapEntry = this.getFirstEntry();
        while (treeMapEntry != null) {
            if (TreeMap.valEquals(object, treeMapEntry.value)) {
                return true;
            }
            treeMapEntry = TreeMap.successor(treeMapEntry);
        }
        return false;
    }

    Iterator<K> descendingKeyIterator() {
        return new DescendingKeyIterator(this.getLastEntry());
    }

    @Override
    public NavigableSet<K> descendingKeySet() {
        return this.descendingMap().navigableKeySet();
    }

    final Spliterator<K> descendingKeySpliterator() {
        return new DescendingKeySpliterator(this, null, null, 0, -2, 0);
    }

    @Override
    public NavigableMap<K, V> descendingMap() {
        NavigableMap<K, V> navigableMap = this.descendingMap;
        if (navigableMap == null) {
            this.descendingMap = navigableMap = new DescendingSubMap(this, true, null, true, true, null, true);
        }
        return navigableMap;
    }

    @Override
    public Set<Map.Entry<K, V>> entrySet() {
        TreeMap<K, V> treeMap = this.entrySet;
        if (treeMap == null) {
            this.entrySet = treeMap = new EntrySet();
        }
        return treeMap;
    }

    @Override
    public Map.Entry<K, V> firstEntry() {
        return TreeMap.exportEntry(this.getFirstEntry());
    }

    @Override
    public K firstKey() {
        return TreeMap.key(this.getFirstEntry());
    }

    @Override
    public Map.Entry<K, V> floorEntry(K k) {
        return TreeMap.exportEntry(this.getFloorEntry(k));
    }

    @Override
    public K floorKey(K k) {
        return TreeMap.keyOrNull(this.getFloorEntry(k));
    }

    @Override
    public void forEach(BiConsumer<? super K, ? super V> biConsumer) {
        Objects.requireNonNull(biConsumer);
        int n = this.modCount;
        TreeMapEntry<K, V> treeMapEntry = this.getFirstEntry();
        while (treeMapEntry != null) {
            biConsumer.accept(treeMapEntry.key, treeMapEntry.value);
            if (n == this.modCount) {
                treeMapEntry = TreeMap.successor(treeMapEntry);
                continue;
            }
            throw new ConcurrentModificationException();
        }
    }

    @Override
    public V get(Object treeMapEntry) {
        treeMapEntry = (treeMapEntry = this.getEntry(treeMapEntry)) == null ? null : treeMapEntry.value;
        return (V)treeMapEntry;
    }

    final TreeMapEntry<K, V> getCeilingEntry(K object) {
        TreeMapEntry<K, V> treeMapEntry = this.root;
        while (treeMapEntry != null) {
            int n = this.compare(object, treeMapEntry.key);
            if (n < 0) {
                if (treeMapEntry.left != null) {
                    treeMapEntry = treeMapEntry.left;
                    continue;
                }
                return treeMapEntry;
            }
            if (n > 0) {
                if (treeMapEntry.right != null) {
                    treeMapEntry = treeMapEntry.right;
                    continue;
                }
                object = treeMapEntry.parent;
                while (object != null && treeMapEntry == ((TreeMapEntry)object).right) {
                    treeMapEntry = object;
                    object = ((TreeMapEntry)object).parent;
                }
                return object;
            }
            return treeMapEntry;
        }
        return null;
    }

    final TreeMapEntry<K, V> getEntry(Object treeMapEntry) {
        if (this.comparator != null) {
            return this.getEntryUsingComparator(treeMapEntry);
        }
        if (treeMapEntry != null) {
            Comparable comparable = (Comparable)((Object)treeMapEntry);
            treeMapEntry = this.root;
            while (treeMapEntry != null) {
                int n = comparable.compareTo(treeMapEntry.key);
                if (n < 0) {
                    treeMapEntry = treeMapEntry.left;
                    continue;
                }
                if (n > 0) {
                    treeMapEntry = treeMapEntry.right;
                    continue;
                }
                return treeMapEntry;
            }
            return null;
        }
        throw new NullPointerException();
    }

    final TreeMapEntry<K, V> getEntryUsingComparator(Object object) {
        Comparator<K> comparator = this.comparator;
        if (comparator != null) {
            TreeMapEntry<K, V> treeMapEntry = this.root;
            while (treeMapEntry != null) {
                int n = comparator.compare(object, treeMapEntry.key);
                if (n < 0) {
                    treeMapEntry = treeMapEntry.left;
                    continue;
                }
                if (n > 0) {
                    treeMapEntry = treeMapEntry.right;
                    continue;
                }
                return treeMapEntry;
            }
        }
        return null;
    }

    final TreeMapEntry<K, V> getFirstEntry() {
        TreeMapEntry<K, V> treeMapEntry;
        TreeMapEntry<K, V> treeMapEntry2 = treeMapEntry = this.root;
        if (treeMapEntry != null) {
            do {
                treeMapEntry2 = treeMapEntry;
                if (treeMapEntry.left == null) break;
                treeMapEntry = treeMapEntry.left;
            } while (true);
        }
        return treeMapEntry2;
    }

    final TreeMapEntry<K, V> getFloorEntry(K object) {
        TreeMapEntry<K, V> treeMapEntry = this.root;
        while (treeMapEntry != null) {
            int n = this.compare(object, treeMapEntry.key);
            if (n > 0) {
                if (treeMapEntry.right != null) {
                    treeMapEntry = treeMapEntry.right;
                    continue;
                }
                return treeMapEntry;
            }
            if (n < 0) {
                if (treeMapEntry.left != null) {
                    treeMapEntry = treeMapEntry.left;
                    continue;
                }
                object = treeMapEntry.parent;
                while (object != null && treeMapEntry == ((TreeMapEntry)object).left) {
                    treeMapEntry = object;
                    object = ((TreeMapEntry)object).parent;
                }
                return object;
            }
            return treeMapEntry;
        }
        return null;
    }

    final TreeMapEntry<K, V> getHigherEntry(K object) {
        TreeMapEntry<K, V> treeMapEntry = this.root;
        while (treeMapEntry != null) {
            if (this.compare(object, treeMapEntry.key) < 0) {
                if (treeMapEntry.left != null) {
                    treeMapEntry = treeMapEntry.left;
                    continue;
                }
                return treeMapEntry;
            }
            if (treeMapEntry.right != null) {
                treeMapEntry = treeMapEntry.right;
                continue;
            }
            object = treeMapEntry.parent;
            while (object != null && treeMapEntry == ((TreeMapEntry)object).right) {
                treeMapEntry = object;
                object = ((TreeMapEntry)object).parent;
            }
            return object;
        }
        return null;
    }

    final TreeMapEntry<K, V> getLastEntry() {
        TreeMapEntry<K, V> treeMapEntry;
        TreeMapEntry<K, V> treeMapEntry2 = treeMapEntry = this.root;
        if (treeMapEntry != null) {
            do {
                treeMapEntry2 = treeMapEntry;
                if (treeMapEntry.right == null) break;
                treeMapEntry = treeMapEntry.right;
            } while (true);
        }
        return treeMapEntry2;
    }

    final TreeMapEntry<K, V> getLowerEntry(K object) {
        TreeMapEntry<K, V> treeMapEntry = this.root;
        while (treeMapEntry != null) {
            if (this.compare(object, treeMapEntry.key) > 0) {
                if (treeMapEntry.right != null) {
                    treeMapEntry = treeMapEntry.right;
                    continue;
                }
                return treeMapEntry;
            }
            if (treeMapEntry.left != null) {
                treeMapEntry = treeMapEntry.left;
                continue;
            }
            object = treeMapEntry.parent;
            while (object != null && treeMapEntry == ((TreeMapEntry)object).left) {
                treeMapEntry = object;
                object = ((TreeMapEntry)object).parent;
            }
            return object;
        }
        return null;
    }

    @Override
    public NavigableMap<K, V> headMap(K k, boolean bl) {
        return new AscendingSubMap(this, true, null, true, false, k, bl);
    }

    @Override
    public SortedMap<K, V> headMap(K k) {
        return this.headMap(k, false);
    }

    @Override
    public Map.Entry<K, V> higherEntry(K k) {
        return TreeMap.exportEntry(this.getHigherEntry(k));
    }

    @Override
    public K higherKey(K k) {
        return TreeMap.keyOrNull(this.getHigherEntry(k));
    }

    Iterator<K> keyIterator() {
        return new KeyIterator(this.getFirstEntry());
    }

    @Override
    public Set<K> keySet() {
        return this.navigableKeySet();
    }

    final Spliterator<K> keySpliterator() {
        return new KeySpliterator(this, null, null, 0, -1, 0);
    }

    @Override
    public Map.Entry<K, V> lastEntry() {
        return TreeMap.exportEntry(this.getLastEntry());
    }

    @Override
    public K lastKey() {
        return TreeMap.key(this.getLastEntry());
    }

    @Override
    public Map.Entry<K, V> lowerEntry(K k) {
        return TreeMap.exportEntry(this.getLowerEntry(k));
    }

    @Override
    public K lowerKey(K k) {
        return TreeMap.keyOrNull(this.getLowerEntry(k));
    }

    @Override
    public NavigableSet<K> navigableKeySet() {
        KeySet<Object> keySet = this.navigableKeySet;
        if (keySet == null) {
            this.navigableKeySet = keySet = new KeySet(this);
        }
        return keySet;
    }

    @Override
    public Map.Entry<K, V> pollFirstEntry() {
        TreeMapEntry<K, V> treeMapEntry = this.getFirstEntry();
        Map.Entry<K, V> entry = TreeMap.exportEntry(treeMapEntry);
        if (treeMapEntry != null) {
            this.deleteEntry(treeMapEntry);
        }
        return entry;
    }

    @Override
    public Map.Entry<K, V> pollLastEntry() {
        TreeMapEntry<K, V> treeMapEntry = this.getLastEntry();
        Map.Entry<K, V> entry = TreeMap.exportEntry(treeMapEntry);
        if (treeMapEntry != null) {
            this.deleteEntry(treeMapEntry);
        }
        return entry;
    }

    @Override
    public V put(K object, V v) {
        block15 : {
            TreeMapEntry<K, V> treeMapEntry;
            block12 : {
                int n;
                block14 : {
                    Comparator<K> comparator;
                    TreeMapEntry<K, V> treeMapEntry2;
                    block13 : {
                        block11 : {
                            TreeMapEntry treeMapEntry3;
                            treeMapEntry2 = this.root;
                            if (treeMapEntry2 == null) {
                                this.compare(object, object);
                                this.root = new TreeMapEntry<K, V>(object, v, null);
                                this.size = 1;
                                ++this.modCount;
                                return null;
                            }
                            comparator = this.comparator;
                            if (comparator == null) break block13;
                            do {
                                treeMapEntry = treeMapEntry2;
                                n = comparator.compare(object, treeMapEntry2.key);
                                if (n < 0) {
                                    treeMapEntry3 = treeMapEntry2.left;
                                } else {
                                    if (n <= 0) break block11;
                                    treeMapEntry3 = treeMapEntry2.right;
                                }
                                treeMapEntry2 = treeMapEntry3;
                            } while (treeMapEntry3 != null);
                            break block14;
                        }
                        return treeMapEntry2.setValue(v);
                    }
                    if (object == null) break block15;
                    comparator = (Comparable)object;
                    TreeMapEntry<K, V> treeMapEntry4 = treeMapEntry2;
                    do {
                        treeMapEntry = treeMapEntry4;
                        n = comparator.compareTo(treeMapEntry.key);
                        if (n < 0) {
                            treeMapEntry2 = treeMapEntry.left;
                        } else {
                            if (n <= 0) break block12;
                            treeMapEntry2 = treeMapEntry.right;
                        }
                        treeMapEntry4 = treeMapEntry2;
                    } while (treeMapEntry2 != null);
                }
                object = new TreeMapEntry<K, V>(object, v, treeMapEntry);
                if (n < 0) {
                    treeMapEntry.left = object;
                } else {
                    treeMapEntry.right = object;
                }
                this.fixAfterInsertion((TreeMapEntry<K, V>)object);
                ++this.size;
                ++this.modCount;
                return null;
            }
            return treeMapEntry.setValue(v);
        }
        throw new NullPointerException();
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> map) {
        Comparator<? super K> comparator;
        Comparator comparator2;
        int n = map.size();
        if (this.size == 0 && n != 0 && map instanceof SortedMap && ((comparator2 = ((SortedMap)map).comparator()) == (comparator = this.comparator) || comparator2 != null && comparator2.equals(comparator))) {
            ++this.modCount;
            try {
                this.buildFromSorted(n, map.entrySet().iterator(), null, null);
            }
            catch (ClassNotFoundException classNotFoundException) {
            }
            catch (IOException iOException) {
                // empty catch block
            }
            return;
        }
        super.putAll(map);
    }

    void readTreeSet(int n, ObjectInputStream objectInputStream, V v) throws IOException, ClassNotFoundException {
        this.buildFromSorted(n, null, objectInputStream, v);
    }

    @Override
    public V remove(Object object) {
        TreeMapEntry<K, V> treeMapEntry = this.getEntry(object);
        if (treeMapEntry == null) {
            return null;
        }
        object = treeMapEntry.value;
        this.deleteEntry(treeMapEntry);
        return (V)object;
    }

    @Override
    public V replace(K object, V v) {
        if ((object = this.getEntry(object)) != null) {
            Object v2 = ((TreeMapEntry)object).value;
            ((TreeMapEntry)object).value = v;
            return v2;
        }
        return null;
    }

    @Override
    public boolean replace(K object, V v, V v2) {
        if ((object = this.getEntry(object)) != null && Objects.equals(v, ((TreeMapEntry)object).value)) {
            ((TreeMapEntry)object).value = v2;
            return true;
        }
        return false;
    }

    @Override
    public void replaceAll(BiFunction<? super K, ? super V, ? extends V> biFunction) {
        Objects.requireNonNull(biFunction);
        int n = this.modCount;
        TreeMapEntry<K, V> treeMapEntry = this.getFirstEntry();
        while (treeMapEntry != null) {
            treeMapEntry.value = biFunction.apply(treeMapEntry.key, treeMapEntry.value);
            if (n == this.modCount) {
                treeMapEntry = TreeMap.successor(treeMapEntry);
                continue;
            }
            throw new ConcurrentModificationException();
        }
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public NavigableMap<K, V> subMap(K k, boolean bl, K k2, boolean bl2) {
        return new AscendingSubMap(this, false, k, bl, false, k2, bl2);
    }

    @Override
    public SortedMap<K, V> subMap(K k, K k2) {
        return this.subMap(k, true, k2, false);
    }

    @Override
    public NavigableMap<K, V> tailMap(K k, boolean bl) {
        return new AscendingSubMap(this, false, k, bl, true, null, true);
    }

    @Override
    public SortedMap<K, V> tailMap(K k) {
        return this.tailMap(k, true);
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

    static final class AscendingSubMap<K, V>
    extends NavigableSubMap<K, V> {
        private static final long serialVersionUID = 912986545866124060L;

        AscendingSubMap(TreeMap<K, V> treeMap, boolean bl, K k, boolean bl2, boolean bl3, K k2, boolean bl4) {
            super(treeMap, bl, k, bl2, bl3, k2, bl4);
        }

        @Override
        public Comparator<? super K> comparator() {
            return this.m.comparator();
        }

        @Override
        Iterator<K> descendingKeyIterator() {
            return new NavigableSubMap.DescendingSubMapKeyIterator(this.absHighest(), this.absLowFence());
        }

        @Override
        public NavigableMap<K, V> descendingMap() {
            DescendingSubMap descendingSubMap = this.descendingMapView;
            if (descendingSubMap == null) {
                this.descendingMapView = descendingSubMap = new DescendingSubMap(this.m, this.fromStart, this.lo, this.loInclusive, this.toEnd, this.hi, this.hiInclusive);
            }
            return descendingSubMap;
        }

        @Override
        public Set<Map.Entry<K, V>> entrySet() {
            Object object = this.entrySetView;
            if (object == null) {
                this.entrySetView = object = new AscendingEntrySetView();
            }
            return object;
        }

        @Override
        public NavigableMap<K, V> headMap(K k, boolean bl) {
            if (!this.inRange(k) && (this.toEnd || this.m.compare(k, this.hi) != 0 || this.hiInclusive || bl)) {
                throw new IllegalArgumentException("toKey out of range");
            }
            return new AscendingSubMap<Object, V>(this.m, this.fromStart, this.lo, this.loInclusive, false, k, bl);
        }

        @Override
        Iterator<K> keyIterator() {
            return new NavigableSubMap.SubMapKeyIterator(this.absLowest(), this.absHighFence());
        }

        @Override
        Spliterator<K> keySpliterator() {
            return new NavigableSubMap.SubMapKeyIterator(this.absLowest(), this.absHighFence());
        }

        @Override
        TreeMapEntry<K, V> subCeiling(K k) {
            return this.absCeiling(k);
        }

        @Override
        TreeMapEntry<K, V> subFloor(K k) {
            return this.absFloor(k);
        }

        @Override
        TreeMapEntry<K, V> subHigher(K k) {
            return this.absHigher(k);
        }

        @Override
        TreeMapEntry<K, V> subHighest() {
            return this.absHighest();
        }

        @Override
        TreeMapEntry<K, V> subLower(K k) {
            return this.absLower(k);
        }

        @Override
        TreeMapEntry<K, V> subLowest() {
            return this.absLowest();
        }

        @Override
        public NavigableMap<K, V> subMap(K k, boolean bl, K k2, boolean bl2) {
            if (this.inRange(k, bl)) {
                if (this.inRange(k2, bl2)) {
                    return new AscendingSubMap<K, V>(this.m, false, k, bl, false, k2, bl2);
                }
                throw new IllegalArgumentException("toKey out of range");
            }
            throw new IllegalArgumentException("fromKey out of range");
        }

        @Override
        public NavigableMap<K, V> tailMap(K k, boolean bl) {
            if (!this.inRange(k) && (this.fromStart || this.m.compare(k, this.lo) != 0 || this.loInclusive || bl)) {
                throw new IllegalArgumentException("fromKey out of range");
            }
            return new AscendingSubMap<Object, V>(this.m, false, k, bl, this.toEnd, this.hi, this.hiInclusive);
        }

        final class AscendingEntrySetView
        extends NavigableSubMap<K, V> {
            AscendingEntrySetView() {
            }

            public Iterator<Map.Entry<K, V>> iterator() {
                AscendingSubMap ascendingSubMap = AscendingSubMap.this;
                return ascendingSubMap.new NavigableSubMap.SubMapEntryIterator(ascendingSubMap.absLowest(), AscendingSubMap.this.absHighFence());
            }
        }

    }

    final class DescendingKeyIterator
    extends java.util.TreeMap.PrivateEntryIterator<K> {
        DescendingKeyIterator(TreeMapEntry<K, V> treeMapEntry) {
            super(treeMapEntry);
        }

        public K next() {
            return this.prevEntry().key;
        }

        public void remove() {
            if (this.lastReturned != null) {
                if (TreeMap.this.modCount == this.expectedModCount) {
                    TreeMap.this.deleteEntry(this.lastReturned);
                    this.lastReturned = null;
                    this.expectedModCount = TreeMap.this.modCount;
                    return;
                }
                throw new ConcurrentModificationException();
            }
            throw new IllegalStateException();
        }
    }

    static final class DescendingKeySpliterator<K, V>
    extends TreeMapSpliterator<K, V>
    implements Spliterator<K> {
        DescendingKeySpliterator(TreeMap<K, V> treeMap, TreeMapEntry<K, V> treeMapEntry, TreeMapEntry<K, V> treeMapEntry2, int n, int n2, int n3) {
            super(treeMap, treeMapEntry, treeMapEntry2, n, n2, n3);
        }

        @Override
        public int characteristics() {
            int n = this.side == 0 ? 64 : 0;
            return n | 1 | 16;
        }

        @Override
        public void forEachRemaining(Consumer<? super K> consumer) {
            if (consumer != null) {
                TreeMapEntry treeMapEntry;
                if (this.est < 0) {
                    this.getEstimate();
                }
                TreeMapEntry treeMapEntry2 = this.fence;
                TreeMapEntry treeMapEntry3 = treeMapEntry = this.current;
                if (treeMapEntry != null && treeMapEntry3 != treeMapEntry2) {
                    this.current = treeMapEntry2;
                    do {
                        TreeMapEntry treeMapEntry4;
                        consumer.accept(treeMapEntry3.key);
                        treeMapEntry = treeMapEntry4 = treeMapEntry3.left;
                        TreeMapEntry treeMapEntry5 = treeMapEntry3;
                        if (treeMapEntry4 != null) {
                            treeMapEntry3 = treeMapEntry;
                            while ((treeMapEntry = treeMapEntry3.right) != null) {
                                treeMapEntry3 = treeMapEntry;
                            }
                        } else {
                            do {
                                treeMapEntry3 = treeMapEntry = (treeMapEntry4 = treeMapEntry5.parent);
                                if (treeMapEntry4 == null) break;
                                treeMapEntry3 = treeMapEntry;
                                if (treeMapEntry5 != treeMapEntry.left) break;
                                treeMapEntry5 = treeMapEntry;
                            } while (true);
                        }
                        treeMapEntry = treeMapEntry3;
                        if (treeMapEntry3 == null) break;
                        treeMapEntry3 = treeMapEntry;
                    } while (treeMapEntry != treeMapEntry2);
                    if (this.tree.modCount != this.expectedModCount) {
                        throw new ConcurrentModificationException();
                    }
                }
                return;
            }
            throw new NullPointerException();
        }

        @Override
        public boolean tryAdvance(Consumer<? super K> consumer) {
            if (consumer != null) {
                TreeMapEntry treeMapEntry;
                if (this.est < 0) {
                    this.getEstimate();
                }
                if ((treeMapEntry = this.current) != null && treeMapEntry != this.fence) {
                    this.current = TreeMap.predecessor(treeMapEntry);
                    consumer.accept(treeMapEntry.key);
                    if (this.tree.modCount == this.expectedModCount) {
                        return true;
                    }
                    throw new ConcurrentModificationException();
                }
                return false;
            }
            throw new NullPointerException();
        }

        public DescendingKeySpliterator<K, V> trySplit() {
            if (this.est < 0) {
                this.getEstimate();
            }
            int n = this.side;
            TreeMapEntry treeMapEntry = this.current;
            Object object = this.fence;
            TreeMapEntry treeMapEntry2 = treeMapEntry != null && treeMapEntry != object ? (n == 0 ? this.tree.root : (n < 0 ? treeMapEntry.left : (n > 0 && object != null ? ((TreeMapEntry)object).right : null))) : null;
            if (treeMapEntry2 != null && treeMapEntry2 != treeMapEntry && treeMapEntry2 != object && this.tree.compare(treeMapEntry.key, treeMapEntry2.key) > 0) {
                this.side = 1;
                object = this.tree;
                this.current = treeMapEntry2;
                this.est = n = this.est >>> 1;
                return new DescendingKeySpliterator((TreeMap<K, V>)object, treeMapEntry, treeMapEntry2, -1, n, this.expectedModCount);
            }
            return null;
        }
    }

    static final class DescendingSubMap<K, V>
    extends NavigableSubMap<K, V> {
        private static final long serialVersionUID = 912986545866120460L;
        private final Comparator<? super K> reverseComparator;

        DescendingSubMap(TreeMap<K, V> treeMap, boolean bl, K k, boolean bl2, boolean bl3, K k2, boolean bl4) {
            super(treeMap, bl, k, bl2, bl3, k2, bl4);
            this.reverseComparator = Collections.reverseOrder(this.m.comparator);
        }

        @Override
        public Comparator<? super K> comparator() {
            return this.reverseComparator;
        }

        @Override
        Iterator<K> descendingKeyIterator() {
            return new NavigableSubMap.SubMapKeyIterator(this.absLowest(), this.absHighFence());
        }

        @Override
        public NavigableMap<K, V> descendingMap() {
            AscendingSubMap ascendingSubMap = this.descendingMapView;
            if (ascendingSubMap == null) {
                this.descendingMapView = ascendingSubMap = new AscendingSubMap(this.m, this.fromStart, this.lo, this.loInclusive, this.toEnd, this.hi, this.hiInclusive);
            }
            return ascendingSubMap;
        }

        @Override
        public Set<Map.Entry<K, V>> entrySet() {
            Object object = this.entrySetView;
            if (object == null) {
                this.entrySetView = object = new DescendingEntrySetView();
            }
            return object;
        }

        @Override
        public NavigableMap<K, V> headMap(K k, boolean bl) {
            if (!this.inRange(k) && (this.fromStart || this.m.compare(k, this.lo) != 0 || this.loInclusive || bl)) {
                throw new IllegalArgumentException("toKey out of range");
            }
            return new DescendingSubMap<Object, V>(this.m, false, k, bl, this.toEnd, this.hi, this.hiInclusive);
        }

        @Override
        Iterator<K> keyIterator() {
            return new NavigableSubMap.DescendingSubMapKeyIterator(this.absHighest(), this.absLowFence());
        }

        @Override
        Spliterator<K> keySpliterator() {
            return new NavigableSubMap.DescendingSubMapKeyIterator(this.absHighest(), this.absLowFence());
        }

        @Override
        TreeMapEntry<K, V> subCeiling(K k) {
            return this.absFloor(k);
        }

        @Override
        TreeMapEntry<K, V> subFloor(K k) {
            return this.absCeiling(k);
        }

        @Override
        TreeMapEntry<K, V> subHigher(K k) {
            return this.absLower(k);
        }

        @Override
        TreeMapEntry<K, V> subHighest() {
            return this.absLowest();
        }

        @Override
        TreeMapEntry<K, V> subLower(K k) {
            return this.absHigher(k);
        }

        @Override
        TreeMapEntry<K, V> subLowest() {
            return this.absHighest();
        }

        @Override
        public NavigableMap<K, V> subMap(K k, boolean bl, K k2, boolean bl2) {
            if (this.inRange(k, bl)) {
                if (this.inRange(k2, bl2)) {
                    return new DescendingSubMap<K, V>(this.m, false, k2, bl2, false, k, bl);
                }
                throw new IllegalArgumentException("toKey out of range");
            }
            throw new IllegalArgumentException("fromKey out of range");
        }

        @Override
        public NavigableMap<K, V> tailMap(K k, boolean bl) {
            if (!this.inRange(k) && (this.toEnd || this.m.compare(k, this.hi) != 0 || this.hiInclusive || bl)) {
                throw new IllegalArgumentException("fromKey out of range");
            }
            return new DescendingSubMap<Object, V>(this.m, this.fromStart, this.lo, this.loInclusive, false, k, bl);
        }

        final class DescendingEntrySetView
        extends NavigableSubMap<K, V> {
            DescendingEntrySetView() {
            }

            public Iterator<Map.Entry<K, V>> iterator() {
                DescendingSubMap descendingSubMap = DescendingSubMap.this;
                return descendingSubMap.new NavigableSubMap.DescendingSubMapEntryIterator(descendingSubMap.absHighest(), DescendingSubMap.this.absLowFence());
            }
        }

    }

    final class EntryIterator
    extends java.util.TreeMap.PrivateEntryIterator<Map.Entry<K, V>> {
        EntryIterator(TreeMapEntry<K, V> treeMapEntry) {
            super(treeMapEntry);
        }

        public Map.Entry<K, V> next() {
            return this.nextEntry();
        }
    }

    class EntrySet
    extends AbstractSet<Map.Entry<K, V>> {
        EntrySet() {
        }

        @Override
        public void clear() {
            TreeMap.this.clear();
        }

        @Override
        public boolean contains(Object object) {
            boolean bl = object instanceof Map.Entry;
            boolean bl2 = false;
            if (!bl) {
                return false;
            }
            TreeMapEntry treeMapEntry = (TreeMapEntry)object;
            object = treeMapEntry.getValue();
            treeMapEntry = TreeMap.this.getEntry(treeMapEntry.getKey());
            bl = bl2;
            if (treeMapEntry != null) {
                bl = bl2;
                if (TreeMap.valEquals(treeMapEntry.getValue(), object)) {
                    bl = true;
                }
            }
            return bl;
        }

        @Override
        public Iterator<Map.Entry<K, V>> iterator() {
            TreeMap treeMap = TreeMap.this;
            return treeMap.new EntryIterator(treeMap.getFirstEntry());
        }

        @Override
        public boolean remove(Object object) {
            if (!(object instanceof Map.Entry)) {
                return false;
            }
            TreeMapEntry treeMapEntry = (TreeMapEntry)object;
            object = treeMapEntry.getValue();
            if ((treeMapEntry = TreeMap.this.getEntry(treeMapEntry.getKey())) != null && TreeMap.valEquals(treeMapEntry.getValue(), object)) {
                TreeMap.this.deleteEntry(treeMapEntry);
                return true;
            }
            return false;
        }

        @Override
        public int size() {
            return TreeMap.this.size();
        }

        @Override
        public Spliterator<Map.Entry<K, V>> spliterator() {
            return new EntrySpliterator(TreeMap.this, null, null, 0, -1, 0);
        }
    }

    static final class EntrySpliterator<K, V>
    extends TreeMapSpliterator<K, V>
    implements Spliterator<Map.Entry<K, V>> {
        EntrySpliterator(TreeMap<K, V> treeMap, TreeMapEntry<K, V> treeMapEntry, TreeMapEntry<K, V> treeMapEntry2, int n, int n2, int n3) {
            super(treeMap, treeMapEntry, treeMapEntry2, n, n2, n3);
        }

        static /* synthetic */ int lambda$getComparator$d5a01062$1(Map.Entry entry, Map.Entry entry2) {
            return ((Comparable)entry.getKey()).compareTo(entry2.getKey());
        }

        @Override
        public int characteristics() {
            int n = this.side == 0 ? 64 : 0;
            return n | 1 | 4 | 16;
        }

        @Override
        public void forEachRemaining(Consumer<? super Map.Entry<K, V>> consumer) {
            if (consumer != null) {
                TreeMapEntry treeMapEntry;
                if (this.est < 0) {
                    this.getEstimate();
                }
                TreeMapEntry treeMapEntry2 = this.fence;
                TreeMapEntry treeMapEntry3 = treeMapEntry = this.current;
                if (treeMapEntry != null && treeMapEntry3 != treeMapEntry2) {
                    this.current = treeMapEntry2;
                    do {
                        TreeMapEntry treeMapEntry4;
                        consumer.accept(treeMapEntry3);
                        treeMapEntry = treeMapEntry4 = treeMapEntry3.right;
                        TreeMapEntry treeMapEntry5 = treeMapEntry3;
                        if (treeMapEntry4 != null) {
                            treeMapEntry3 = treeMapEntry;
                            while ((treeMapEntry = treeMapEntry3.left) != null) {
                                treeMapEntry3 = treeMapEntry;
                            }
                        } else {
                            do {
                                treeMapEntry3 = treeMapEntry = (treeMapEntry4 = treeMapEntry5.parent);
                                if (treeMapEntry4 == null) break;
                                treeMapEntry3 = treeMapEntry;
                                if (treeMapEntry5 != treeMapEntry.right) break;
                                treeMapEntry5 = treeMapEntry;
                            } while (true);
                        }
                        treeMapEntry = treeMapEntry3;
                        if (treeMapEntry3 == null) break;
                        treeMapEntry3 = treeMapEntry;
                    } while (treeMapEntry != treeMapEntry2);
                    if (this.tree.modCount != this.expectedModCount) {
                        throw new ConcurrentModificationException();
                    }
                }
                return;
            }
            throw new NullPointerException();
        }

        @Override
        public Comparator<Map.Entry<K, V>> getComparator() {
            if (this.tree.comparator != null) {
                return Map.Entry.comparingByKey(this.tree.comparator);
            }
            return (Comparator)((Object)((Serializable)_$$Lambda$TreeMap$EntrySpliterator$YqCulUmBGNzQr1PJ_ERFnbxUmTQ.INSTANCE));
        }

        @Override
        public boolean tryAdvance(Consumer<? super Map.Entry<K, V>> consumer) {
            if (consumer != null) {
                TreeMapEntry treeMapEntry;
                if (this.est < 0) {
                    this.getEstimate();
                }
                if ((treeMapEntry = this.current) != null && treeMapEntry != this.fence) {
                    this.current = TreeMap.successor(treeMapEntry);
                    consumer.accept(treeMapEntry);
                    if (this.tree.modCount == this.expectedModCount) {
                        return true;
                    }
                    throw new ConcurrentModificationException();
                }
                return false;
            }
            throw new NullPointerException();
        }

        public EntrySpliterator<K, V> trySplit() {
            if (this.est < 0) {
                this.getEstimate();
            }
            int n = this.side;
            TreeMapEntry treeMapEntry = this.current;
            Object object = this.fence;
            TreeMapEntry treeMapEntry2 = treeMapEntry != null && treeMapEntry != object ? (n == 0 ? this.tree.root : (n > 0 ? treeMapEntry.right : (n < 0 && object != null ? ((TreeMapEntry)object).left : null))) : null;
            if (treeMapEntry2 != null && treeMapEntry2 != treeMapEntry && treeMapEntry2 != object && this.tree.compare(treeMapEntry.key, treeMapEntry2.key) < 0) {
                this.side = 1;
                object = this.tree;
                this.current = treeMapEntry2;
                this.est = n = this.est >>> 1;
                return new EntrySpliterator((TreeMap<K, V>)object, treeMapEntry, treeMapEntry2, -1, n, this.expectedModCount);
            }
            return null;
        }
    }

    final class KeyIterator
    extends java.util.TreeMap.PrivateEntryIterator<K> {
        KeyIterator(TreeMapEntry<K, V> treeMapEntry) {
            super(treeMapEntry);
        }

        public K next() {
            return this.nextEntry().key;
        }
    }

    static final class KeySet<E>
    extends AbstractSet<E>
    implements NavigableSet<E> {
        private final NavigableMap<E, ?> m;

        KeySet(NavigableMap<E, ?> navigableMap) {
            this.m = navigableMap;
        }

        @Override
        public E ceiling(E e) {
            return this.m.ceilingKey(e);
        }

        @Override
        public void clear() {
            this.m.clear();
        }

        @Override
        public Comparator<? super E> comparator() {
            return this.m.comparator();
        }

        @Override
        public boolean contains(Object object) {
            return this.m.containsKey(object);
        }

        @Override
        public Iterator<E> descendingIterator() {
            NavigableMap<E, ?> navigableMap = this.m;
            if (navigableMap instanceof TreeMap) {
                return ((TreeMap)navigableMap).descendingKeyIterator();
            }
            return ((NavigableSubMap)navigableMap).descendingKeyIterator();
        }

        @Override
        public NavigableSet<E> descendingSet() {
            return new KeySet<E>(this.m.descendingMap());
        }

        @Override
        public E first() {
            return (E)this.m.firstKey();
        }

        @Override
        public E floor(E e) {
            return this.m.floorKey(e);
        }

        @Override
        public NavigableSet<E> headSet(E e, boolean bl) {
            return new KeySet<E>(this.m.headMap(e, bl));
        }

        @Override
        public SortedSet<E> headSet(E e) {
            return this.headSet(e, false);
        }

        @Override
        public E higher(E e) {
            return this.m.higherKey(e);
        }

        @Override
        public boolean isEmpty() {
            return this.m.isEmpty();
        }

        @Override
        public Iterator<E> iterator() {
            NavigableMap<E, ?> navigableMap = this.m;
            if (navigableMap instanceof TreeMap) {
                return ((TreeMap)navigableMap).keyIterator();
            }
            return ((NavigableSubMap)navigableMap).keyIterator();
        }

        @Override
        public E last() {
            return (E)this.m.lastKey();
        }

        @Override
        public E lower(E e) {
            return this.m.lowerKey(e);
        }

        @Override
        public E pollFirst() {
            Map.Entry<Object, Object> entry = this.m.pollFirstEntry();
            entry = entry == null ? null : entry.getKey();
            return (E)entry;
        }

        @Override
        public E pollLast() {
            Map.Entry<Object, Object> entry = this.m.pollLastEntry();
            entry = entry == null ? null : entry.getKey();
            return (E)entry;
        }

        @Override
        public boolean remove(Object object) {
            int n = this.size();
            this.m.remove(object);
            boolean bl = this.size() != n;
            return bl;
        }

        @Override
        public int size() {
            return this.m.size();
        }

        @Override
        public Spliterator<E> spliterator() {
            return TreeMap.keySpliteratorFor(this.m);
        }

        @Override
        public NavigableSet<E> subSet(E e, boolean bl, E e2, boolean bl2) {
            return new KeySet<E>(this.m.subMap(e, bl, e2, bl2));
        }

        @Override
        public SortedSet<E> subSet(E e, E e2) {
            return this.subSet(e, true, e2, false);
        }

        @Override
        public NavigableSet<E> tailSet(E e, boolean bl) {
            return new KeySet<E>(this.m.tailMap(e, bl));
        }

        @Override
        public SortedSet<E> tailSet(E e) {
            return this.tailSet(e, true);
        }
    }

    static final class KeySpliterator<K, V>
    extends TreeMapSpliterator<K, V>
    implements Spliterator<K> {
        KeySpliterator(TreeMap<K, V> treeMap, TreeMapEntry<K, V> treeMapEntry, TreeMapEntry<K, V> treeMapEntry2, int n, int n2, int n3) {
            super(treeMap, treeMapEntry, treeMapEntry2, n, n2, n3);
        }

        @Override
        public int characteristics() {
            int n = this.side == 0 ? 64 : 0;
            return n | 1 | 4 | 16;
        }

        @Override
        public void forEachRemaining(Consumer<? super K> consumer) {
            if (consumer != null) {
                TreeMapEntry treeMapEntry;
                if (this.est < 0) {
                    this.getEstimate();
                }
                TreeMapEntry treeMapEntry2 = this.fence;
                TreeMapEntry treeMapEntry3 = treeMapEntry = this.current;
                if (treeMapEntry != null && treeMapEntry3 != treeMapEntry2) {
                    this.current = treeMapEntry2;
                    do {
                        TreeMapEntry treeMapEntry4;
                        consumer.accept(treeMapEntry3.key);
                        treeMapEntry = treeMapEntry4 = treeMapEntry3.right;
                        TreeMapEntry treeMapEntry5 = treeMapEntry3;
                        if (treeMapEntry4 != null) {
                            treeMapEntry3 = treeMapEntry;
                            while ((treeMapEntry = treeMapEntry3.left) != null) {
                                treeMapEntry3 = treeMapEntry;
                            }
                        } else {
                            do {
                                treeMapEntry3 = treeMapEntry = (treeMapEntry4 = treeMapEntry5.parent);
                                if (treeMapEntry4 == null) break;
                                treeMapEntry3 = treeMapEntry;
                                if (treeMapEntry5 != treeMapEntry.right) break;
                                treeMapEntry5 = treeMapEntry;
                            } while (true);
                        }
                        treeMapEntry = treeMapEntry3;
                        if (treeMapEntry3 == null) break;
                        treeMapEntry3 = treeMapEntry;
                    } while (treeMapEntry != treeMapEntry2);
                    if (this.tree.modCount != this.expectedModCount) {
                        throw new ConcurrentModificationException();
                    }
                }
                return;
            }
            throw new NullPointerException();
        }

        @Override
        public final Comparator<? super K> getComparator() {
            return this.tree.comparator;
        }

        @Override
        public boolean tryAdvance(Consumer<? super K> consumer) {
            if (consumer != null) {
                TreeMapEntry treeMapEntry;
                if (this.est < 0) {
                    this.getEstimate();
                }
                if ((treeMapEntry = this.current) != null && treeMapEntry != this.fence) {
                    this.current = TreeMap.successor(treeMapEntry);
                    consumer.accept(treeMapEntry.key);
                    if (this.tree.modCount == this.expectedModCount) {
                        return true;
                    }
                    throw new ConcurrentModificationException();
                }
                return false;
            }
            throw new NullPointerException();
        }

        public KeySpliterator<K, V> trySplit() {
            if (this.est < 0) {
                this.getEstimate();
            }
            int n = this.side;
            TreeMapEntry treeMapEntry = this.current;
            Object object = this.fence;
            TreeMapEntry treeMapEntry2 = treeMapEntry != null && treeMapEntry != object ? (n == 0 ? this.tree.root : (n > 0 ? treeMapEntry.right : (n < 0 && object != null ? ((TreeMapEntry)object).left : null))) : null;
            if (treeMapEntry2 != null && treeMapEntry2 != treeMapEntry && treeMapEntry2 != object && this.tree.compare(treeMapEntry.key, treeMapEntry2.key) < 0) {
                this.side = 1;
                object = this.tree;
                this.current = treeMapEntry2;
                this.est = n = this.est >>> 1;
                return new KeySpliterator((TreeMap<K, V>)object, treeMapEntry, treeMapEntry2, -1, n, this.expectedModCount);
            }
            return null;
        }
    }

    static abstract class NavigableSubMap<K, V>
    extends AbstractMap<K, V>
    implements NavigableMap<K, V>,
    Serializable {
        private static final long serialVersionUID = 2765629423043303731L;
        transient NavigableMap<K, V> descendingMapView;
        transient NavigableSubMap<K, V> entrySetView;
        final boolean fromStart;
        final K hi;
        final boolean hiInclusive;
        final K lo;
        final boolean loInclusive;
        final TreeMap<K, V> m;
        transient KeySet<K> navigableKeySetView;
        final boolean toEnd;

        NavigableSubMap(TreeMap<K, V> treeMap, boolean bl, K k, boolean bl2, boolean bl3, K k2, boolean bl4) {
            if (!bl && !bl3) {
                if (treeMap.compare(k, k2) > 0) {
                    throw new IllegalArgumentException("fromKey > toKey");
                }
            } else {
                if (!bl) {
                    treeMap.compare(k, k);
                }
                if (!bl3) {
                    treeMap.compare(k2, k2);
                }
            }
            this.m = treeMap;
            this.fromStart = bl;
            this.lo = k;
            this.loInclusive = bl2;
            this.toEnd = bl3;
            this.hi = k2;
            this.hiInclusive = bl4;
        }

        final TreeMapEntry<K, V> absCeiling(K object) {
            if (this.tooLow(object)) {
                return this.absLowest();
            }
            if ((object = this.m.getCeilingEntry(object)) == null || this.tooHigh(((TreeMapEntry)object).key)) {
                object = null;
            }
            return object;
        }

        final TreeMapEntry<K, V> absFloor(K object) {
            if (this.tooHigh(object)) {
                return this.absHighest();
            }
            if ((object = this.m.getFloorEntry(object)) == null || this.tooLow(((TreeMapEntry)object).key)) {
                object = null;
            }
            return object;
        }

        final TreeMapEntry<K, V> absHighFence() {
            TreeMapEntry<K, V> treeMapEntry = this.toEnd ? null : (this.hiInclusive ? this.m.getHigherEntry(this.hi) : this.m.getCeilingEntry(this.hi));
            return treeMapEntry;
        }

        final TreeMapEntry<K, V> absHigher(K object) {
            if (this.tooLow(object)) {
                return this.absLowest();
            }
            if ((object = this.m.getHigherEntry(object)) == null || this.tooHigh(((TreeMapEntry)object).key)) {
                object = null;
            }
            return object;
        }

        final TreeMapEntry<K, V> absHighest() {
            TreeMapEntry<K, V> treeMapEntry = this.toEnd ? this.m.getLastEntry() : (this.hiInclusive ? this.m.getFloorEntry(this.hi) : this.m.getLowerEntry(this.hi));
            if (treeMapEntry == null || this.tooLow(treeMapEntry.key)) {
                treeMapEntry = null;
            }
            return treeMapEntry;
        }

        final TreeMapEntry<K, V> absLowFence() {
            TreeMapEntry<K, V> treeMapEntry = this.fromStart ? null : (this.loInclusive ? this.m.getLowerEntry(this.lo) : this.m.getFloorEntry(this.lo));
            return treeMapEntry;
        }

        final TreeMapEntry<K, V> absLower(K object) {
            if (this.tooHigh(object)) {
                return this.absHighest();
            }
            if ((object = this.m.getLowerEntry(object)) == null || this.tooLow(((TreeMapEntry)object).key)) {
                object = null;
            }
            return object;
        }

        final TreeMapEntry<K, V> absLowest() {
            TreeMapEntry<K, V> treeMapEntry = this.fromStart ? this.m.getFirstEntry() : (this.loInclusive ? this.m.getCeilingEntry(this.lo) : this.m.getHigherEntry(this.lo));
            if (treeMapEntry == null || this.tooHigh(treeMapEntry.key)) {
                treeMapEntry = null;
            }
            return treeMapEntry;
        }

        @Override
        public final Map.Entry<K, V> ceilingEntry(K k) {
            return TreeMap.exportEntry(this.subCeiling(k));
        }

        @Override
        public final K ceilingKey(K k) {
            return TreeMap.keyOrNull(this.subCeiling(k));
        }

        @Override
        public final boolean containsKey(Object object) {
            boolean bl = this.inRange(object) && this.m.containsKey(object);
            return bl;
        }

        abstract Iterator<K> descendingKeyIterator();

        @Override
        public NavigableSet<K> descendingKeySet() {
            return this.descendingMap().navigableKeySet();
        }

        @Override
        public final Map.Entry<K, V> firstEntry() {
            return TreeMap.exportEntry(this.subLowest());
        }

        @Override
        public final K firstKey() {
            return TreeMap.key(this.subLowest());
        }

        @Override
        public final Map.Entry<K, V> floorEntry(K k) {
            return TreeMap.exportEntry(this.subFloor(k));
        }

        @Override
        public final K floorKey(K k) {
            return TreeMap.keyOrNull(this.subFloor(k));
        }

        @Override
        public final V get(Object object) {
            object = !this.inRange(object) ? null : this.m.get(object);
            return (V)object;
        }

        @Override
        public final SortedMap<K, V> headMap(K k) {
            return this.headMap(k, false);
        }

        @Override
        public final Map.Entry<K, V> higherEntry(K k) {
            return TreeMap.exportEntry(this.subHigher(k));
        }

        @Override
        public final K higherKey(K k) {
            return TreeMap.keyOrNull(this.subHigher(k));
        }

        final boolean inClosedRange(Object object) {
            boolean bl = !(!this.fromStart && this.m.compare(object, this.lo) < 0 || !this.toEnd && this.m.compare(this.hi, object) < 0);
            return bl;
        }

        final boolean inRange(Object object) {
            boolean bl = !this.tooLow(object) && !this.tooHigh(object);
            return bl;
        }

        final boolean inRange(Object object, boolean bl) {
            bl = bl ? this.inRange(object) : this.inClosedRange(object);
            return bl;
        }

        @Override
        public boolean isEmpty() {
            boolean bl = this.fromStart && this.toEnd ? this.m.isEmpty() : this.entrySet().isEmpty();
            return bl;
        }

        abstract Iterator<K> keyIterator();

        @Override
        public final Set<K> keySet() {
            return this.navigableKeySet();
        }

        abstract Spliterator<K> keySpliterator();

        @Override
        public final Map.Entry<K, V> lastEntry() {
            return TreeMap.exportEntry(this.subHighest());
        }

        @Override
        public final K lastKey() {
            return TreeMap.key(this.subHighest());
        }

        @Override
        public final Map.Entry<K, V> lowerEntry(K k) {
            return TreeMap.exportEntry(this.subLower(k));
        }

        @Override
        public final K lowerKey(K k) {
            return TreeMap.keyOrNull(this.subLower(k));
        }

        @Override
        public final NavigableSet<K> navigableKeySet() {
            KeySet<Object> keySet = this.navigableKeySetView;
            if (keySet == null) {
                this.navigableKeySetView = keySet = new KeySet<E>(this);
            }
            return keySet;
        }

        @Override
        public final Map.Entry<K, V> pollFirstEntry() {
            TreeMapEntry<K, V> treeMapEntry = this.subLowest();
            Map.Entry<K, V> entry = TreeMap.exportEntry(treeMapEntry);
            if (treeMapEntry != null) {
                this.m.deleteEntry(treeMapEntry);
            }
            return entry;
        }

        @Override
        public final Map.Entry<K, V> pollLastEntry() {
            TreeMapEntry<K, V> treeMapEntry = this.subHighest();
            Map.Entry<K, V> entry = TreeMap.exportEntry(treeMapEntry);
            if (treeMapEntry != null) {
                this.m.deleteEntry(treeMapEntry);
            }
            return entry;
        }

        @Override
        public final V put(K k, V v) {
            if (this.inRange(k)) {
                return this.m.put(k, v);
            }
            throw new IllegalArgumentException("key out of range");
        }

        @Override
        public final V remove(Object object) {
            object = !this.inRange(object) ? null : this.m.remove(object);
            return (V)object;
        }

        @Override
        public int size() {
            int n = this.fromStart && this.toEnd ? this.m.size() : this.entrySet().size();
            return n;
        }

        abstract TreeMapEntry<K, V> subCeiling(K var1);

        abstract TreeMapEntry<K, V> subFloor(K var1);

        abstract TreeMapEntry<K, V> subHigher(K var1);

        abstract TreeMapEntry<K, V> subHighest();

        abstract TreeMapEntry<K, V> subLower(K var1);

        abstract TreeMapEntry<K, V> subLowest();

        @Override
        public final SortedMap<K, V> subMap(K k, K k2) {
            return this.subMap(k, true, k2, false);
        }

        @Override
        public final SortedMap<K, V> tailMap(K k) {
            return this.tailMap(k, true);
        }

        final boolean tooHigh(Object object) {
            int n;
            return !this.toEnd && ((n = this.m.compare(object, this.hi)) > 0 || n == 0 && !this.hiInclusive);
        }

        final boolean tooLow(Object object) {
            int n;
            return !this.fromStart && ((n = this.m.compare(object, this.lo)) < 0 || n == 0 && !this.loInclusive);
        }

        final class DescendingSubMapEntryIterator
        extends java.util.TreeMap$NavigableSubMap.SubMapIterator<Map.Entry<K, V>> {
            DescendingSubMapEntryIterator(TreeMapEntry<K, V> treeMapEntry, TreeMapEntry<K, V> treeMapEntry2) {
                super(treeMapEntry, treeMapEntry2);
            }

            public Map.Entry<K, V> next() {
                return this.prevEntry();
            }

            public void remove() {
                this.removeDescending();
            }
        }

        final class DescendingSubMapKeyIterator
        extends NavigableSubMap<K, V>
        implements Spliterator<K> {
            DescendingSubMapKeyIterator(TreeMapEntry<K, V> treeMapEntry, TreeMapEntry<K, V> treeMapEntry2) {
                super(treeMapEntry, treeMapEntry2);
            }

            @Override
            public int characteristics() {
                return 17;
            }

            @Override
            public long estimateSize() {
                return Long.MAX_VALUE;
            }

            @Override
            public void forEachRemaining(Consumer<? super K> consumer) {
                while (this.hasNext()) {
                    consumer.accept(this.next());
                }
            }

            public K next() {
                return this.prevEntry().key;
            }

            public void remove() {
                this.removeDescending();
            }

            @Override
            public boolean tryAdvance(Consumer<? super K> consumer) {
                if (this.hasNext()) {
                    consumer.accept(this.next());
                    return true;
                }
                return false;
            }

            @Override
            public Spliterator<K> trySplit() {
                return null;
            }
        }

        abstract class EntrySetView
        extends AbstractSet<Map.Entry<K, V>> {
            private transient int size = -1;
            private transient int sizeModCount;

            EntrySetView() {
            }

            @Override
            public boolean contains(Object object) {
                boolean bl;
                block2 : {
                    boolean bl2 = object instanceof Map.Entry;
                    bl = false;
                    if (!bl2) {
                        return false;
                    }
                    Object object2 = (object = (Map.Entry)object).getKey();
                    if (!NavigableSubMap.this.inRange(object2)) {
                        return false;
                    }
                    if ((object2 = NavigableSubMap.this.m.getEntry(object2)) == null || !TreeMap.valEquals(((TreeMapEntry)object2).getValue(), object.getValue())) break block2;
                    bl = true;
                }
                return bl;
            }

            @Override
            public boolean isEmpty() {
                TreeMapEntry treeMapEntry = NavigableSubMap.this.absLowest();
                boolean bl = treeMapEntry == null || NavigableSubMap.this.tooHigh(treeMapEntry.key);
                return bl;
            }

            @Override
            public boolean remove(Object object) {
                if (!(object instanceof Map.Entry)) {
                    return false;
                }
                Object object2 = (object = (Map.Entry)object).getKey();
                if (!NavigableSubMap.this.inRange(object2)) {
                    return false;
                }
                if ((object2 = NavigableSubMap.this.m.getEntry(object2)) != null && TreeMap.valEquals(((TreeMapEntry)object2).getValue(), object.getValue())) {
                    NavigableSubMap.this.m.deleteEntry((TreeMapEntry)object2);
                    return true;
                }
                return false;
            }

            @Override
            public int size() {
                if (NavigableSubMap.this.fromStart && NavigableSubMap.this.toEnd) {
                    return NavigableSubMap.this.m.size();
                }
                if (this.size == -1 || this.sizeModCount != NavigableSubMap.this.m.modCount) {
                    this.sizeModCount = NavigableSubMap.this.m.modCount;
                    this.size = 0;
                    Iterator iterator = this.iterator();
                    while (iterator.hasNext()) {
                        ++this.size;
                        iterator.next();
                    }
                }
                return this.size;
            }
        }

        final class SubMapEntryIterator
        extends java.util.TreeMap$NavigableSubMap.SubMapIterator<Map.Entry<K, V>> {
            SubMapEntryIterator(TreeMapEntry<K, V> treeMapEntry, TreeMapEntry<K, V> treeMapEntry2) {
                super(treeMapEntry, treeMapEntry2);
            }

            public Map.Entry<K, V> next() {
                return this.nextEntry();
            }

            public void remove() {
                this.removeAscending();
            }
        }

        abstract class SubMapIterator<T>
        implements Iterator<T> {
            int expectedModCount;
            final Object fenceKey;
            TreeMapEntry<K, V> lastReturned;
            TreeMapEntry<K, V> next;

            SubMapIterator(TreeMapEntry<K, V> treeMapEntry, TreeMapEntry<K, V> treeMapEntry2) {
                this.expectedModCount = ((NavigableSubMap)NavigableSubMap.this).m.modCount;
                this.lastReturned = null;
                this.next = treeMapEntry;
                NavigableSubMap.this = treeMapEntry2 == null ? UNBOUNDED : treeMapEntry2.key;
                this.fenceKey = NavigableSubMap.this;
            }

            @Override
            public final boolean hasNext() {
                TreeMapEntry<K, V> treeMapEntry = this.next;
                boolean bl = treeMapEntry != null && treeMapEntry.key != this.fenceKey;
                return bl;
            }

            final TreeMapEntry<K, V> nextEntry() {
                TreeMapEntry<K, V> treeMapEntry = this.next;
                if (treeMapEntry != null && treeMapEntry.key != this.fenceKey) {
                    if (NavigableSubMap.this.m.modCount == this.expectedModCount) {
                        this.next = TreeMap.successor(treeMapEntry);
                        this.lastReturned = treeMapEntry;
                        return treeMapEntry;
                    }
                    throw new ConcurrentModificationException();
                }
                throw new NoSuchElementException();
            }

            final TreeMapEntry<K, V> prevEntry() {
                TreeMapEntry<K, V> treeMapEntry = this.next;
                if (treeMapEntry != null && treeMapEntry.key != this.fenceKey) {
                    if (NavigableSubMap.this.m.modCount == this.expectedModCount) {
                        this.next = TreeMap.predecessor(treeMapEntry);
                        this.lastReturned = treeMapEntry;
                        return treeMapEntry;
                    }
                    throw new ConcurrentModificationException();
                }
                throw new NoSuchElementException();
            }

            final void removeAscending() {
                if (this.lastReturned != null) {
                    if (NavigableSubMap.this.m.modCount == this.expectedModCount) {
                        if (this.lastReturned.left != null && this.lastReturned.right != null) {
                            this.next = this.lastReturned;
                        }
                        NavigableSubMap.this.m.deleteEntry(this.lastReturned);
                        this.lastReturned = null;
                        this.expectedModCount = NavigableSubMap.this.m.modCount;
                        return;
                    }
                    throw new ConcurrentModificationException();
                }
                throw new IllegalStateException();
            }

            final void removeDescending() {
                if (this.lastReturned != null) {
                    if (NavigableSubMap.this.m.modCount == this.expectedModCount) {
                        NavigableSubMap.this.m.deleteEntry(this.lastReturned);
                        this.lastReturned = null;
                        this.expectedModCount = NavigableSubMap.this.m.modCount;
                        return;
                    }
                    throw new ConcurrentModificationException();
                }
                throw new IllegalStateException();
            }
        }

        final class SubMapKeyIterator
        extends NavigableSubMap<K, V>
        implements Spliterator<K> {
            SubMapKeyIterator(TreeMapEntry<K, V> treeMapEntry, TreeMapEntry<K, V> treeMapEntry2) {
                super(treeMapEntry, treeMapEntry2);
            }

            @Override
            public int characteristics() {
                return 21;
            }

            @Override
            public long estimateSize() {
                return Long.MAX_VALUE;
            }

            @Override
            public void forEachRemaining(Consumer<? super K> consumer) {
                while (this.hasNext()) {
                    consumer.accept(this.next());
                }
            }

            @Override
            public final Comparator<? super K> getComparator() {
                return NavigableSubMap.this.comparator();
            }

            public K next() {
                return this.nextEntry().key;
            }

            public void remove() {
                this.removeAscending();
            }

            @Override
            public boolean tryAdvance(Consumer<? super K> consumer) {
                if (this.hasNext()) {
                    consumer.accept(this.next());
                    return true;
                }
                return false;
            }

            @Override
            public Spliterator<K> trySplit() {
                return null;
            }
        }

    }

    abstract class PrivateEntryIterator<T>
    implements Iterator<T> {
        int expectedModCount;
        TreeMapEntry<K, V> lastReturned;
        TreeMapEntry<K, V> next;

        PrivateEntryIterator(TreeMapEntry<K, V> treeMapEntry) {
            this.expectedModCount = TreeMap.this.modCount;
            this.lastReturned = null;
            this.next = treeMapEntry;
        }

        @Override
        public final boolean hasNext() {
            boolean bl = this.next != null;
            return bl;
        }

        final TreeMapEntry<K, V> nextEntry() {
            TreeMapEntry<K, V> treeMapEntry = this.next;
            if (treeMapEntry != null) {
                if (TreeMap.this.modCount == this.expectedModCount) {
                    this.next = TreeMap.successor(treeMapEntry);
                    this.lastReturned = treeMapEntry;
                    return treeMapEntry;
                }
                throw new ConcurrentModificationException();
            }
            throw new NoSuchElementException();
        }

        final TreeMapEntry<K, V> prevEntry() {
            TreeMapEntry<K, V> treeMapEntry = this.next;
            if (treeMapEntry != null) {
                if (TreeMap.this.modCount == this.expectedModCount) {
                    this.next = TreeMap.predecessor(treeMapEntry);
                    this.lastReturned = treeMapEntry;
                    return treeMapEntry;
                }
                throw new ConcurrentModificationException();
            }
            throw new NoSuchElementException();
        }

        @Override
        public void remove() {
            if (this.lastReturned != null) {
                if (TreeMap.this.modCount == this.expectedModCount) {
                    if (this.lastReturned.left != null && this.lastReturned.right != null) {
                        this.next = this.lastReturned;
                    }
                    TreeMap.this.deleteEntry(this.lastReturned);
                    this.expectedModCount = TreeMap.this.modCount;
                    this.lastReturned = null;
                    return;
                }
                throw new ConcurrentModificationException();
            }
            throw new IllegalStateException();
        }
    }

    private class SubMap
    extends AbstractMap<K, V>
    implements SortedMap<K, V>,
    Serializable {
        private static final long serialVersionUID = -6520786458950516097L;
        private K fromKey;
        private boolean fromStart = false;
        private boolean toEnd = false;
        private K toKey;

        private SubMap() {
        }

        private Object readResolve() {
            return new AscendingSubMap(TreeMap.this, this.fromStart, this.fromKey, true, this.toEnd, this.toKey, false);
        }

        @Override
        public Comparator<? super K> comparator() {
            throw new InternalError();
        }

        @Override
        public Set<Map.Entry<K, V>> entrySet() {
            throw new InternalError();
        }

        @Override
        public K firstKey() {
            throw new InternalError();
        }

        @Override
        public SortedMap<K, V> headMap(K k) {
            throw new InternalError();
        }

        @Override
        public K lastKey() {
            throw new InternalError();
        }

        @Override
        public SortedMap<K, V> subMap(K k, K k2) {
            throw new InternalError();
        }

        @Override
        public SortedMap<K, V> tailMap(K k) {
            throw new InternalError();
        }
    }

    static final class TreeMapEntry<K, V>
    implements Map.Entry<K, V> {
        boolean color = true;
        K key;
        TreeMapEntry<K, V> left;
        TreeMapEntry<K, V> parent;
        TreeMapEntry<K, V> right;
        V value;

        TreeMapEntry(K k, V v, TreeMapEntry<K, V> treeMapEntry) {
            this.key = k;
            this.value = v;
            this.parent = treeMapEntry;
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
            if (TreeMap.valEquals(this.key, object.getKey())) {
                bl = bl2;
                if (TreeMap.valEquals(this.value, object.getValue())) {
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

    static class TreeMapSpliterator<K, V> {
        TreeMapEntry<K, V> current;
        int est;
        int expectedModCount;
        TreeMapEntry<K, V> fence;
        int side;
        final TreeMap<K, V> tree;

        TreeMapSpliterator(TreeMap<K, V> treeMap, TreeMapEntry<K, V> treeMapEntry, TreeMapEntry<K, V> treeMapEntry2, int n, int n2, int n3) {
            this.tree = treeMap;
            this.current = treeMapEntry;
            this.fence = treeMapEntry2;
            this.side = n;
            this.est = n2;
            this.expectedModCount = n3;
        }

        public final long estimateSize() {
            return this.getEstimate();
        }

        final int getEstimate() {
            int n;
            int n2;
            int n3 = n2 = (n = this.est);
            if (n < 0) {
                TreeMap<K, V> treeMap = this.tree;
                if (treeMap != null) {
                    TreeMapEntry<K, V> treeMapEntry = n2 == -1 ? treeMap.getFirstEntry() : treeMap.getLastEntry();
                    this.current = treeMapEntry;
                    this.est = n3 = treeMap.size;
                    this.expectedModCount = treeMap.modCount;
                } else {
                    this.est = 0;
                    n3 = 0;
                }
            }
            return n3;
        }
    }

    final class ValueIterator
    extends java.util.TreeMap.PrivateEntryIterator<V> {
        ValueIterator(TreeMapEntry<K, V> treeMapEntry) {
            super(treeMapEntry);
        }

        public V next() {
            return this.nextEntry().value;
        }
    }

    static final class ValueSpliterator<K, V>
    extends TreeMapSpliterator<K, V>
    implements Spliterator<V> {
        ValueSpliterator(TreeMap<K, V> treeMap, TreeMapEntry<K, V> treeMapEntry, TreeMapEntry<K, V> treeMapEntry2, int n, int n2, int n3) {
            super(treeMap, treeMapEntry, treeMapEntry2, n, n2, n3);
        }

        @Override
        public int characteristics() {
            int n = this.side == 0 ? 64 : 0;
            return n | 16;
        }

        @Override
        public void forEachRemaining(Consumer<? super V> consumer) {
            if (consumer != null) {
                TreeMapEntry treeMapEntry;
                if (this.est < 0) {
                    this.getEstimate();
                }
                TreeMapEntry treeMapEntry2 = this.fence;
                TreeMapEntry treeMapEntry3 = treeMapEntry = this.current;
                if (treeMapEntry != null && treeMapEntry3 != treeMapEntry2) {
                    this.current = treeMapEntry2;
                    do {
                        TreeMapEntry treeMapEntry4;
                        consumer.accept(treeMapEntry3.value);
                        treeMapEntry = treeMapEntry4 = treeMapEntry3.right;
                        TreeMapEntry treeMapEntry5 = treeMapEntry3;
                        if (treeMapEntry4 != null) {
                            treeMapEntry3 = treeMapEntry;
                            while ((treeMapEntry = treeMapEntry3.left) != null) {
                                treeMapEntry3 = treeMapEntry;
                            }
                        } else {
                            do {
                                treeMapEntry3 = treeMapEntry = (treeMapEntry4 = treeMapEntry5.parent);
                                if (treeMapEntry4 == null) break;
                                treeMapEntry3 = treeMapEntry;
                                if (treeMapEntry5 != treeMapEntry.right) break;
                                treeMapEntry5 = treeMapEntry;
                            } while (true);
                        }
                        treeMapEntry = treeMapEntry3;
                        if (treeMapEntry3 == null) break;
                        treeMapEntry3 = treeMapEntry;
                    } while (treeMapEntry != treeMapEntry2);
                    if (this.tree.modCount != this.expectedModCount) {
                        throw new ConcurrentModificationException();
                    }
                }
                return;
            }
            throw new NullPointerException();
        }

        @Override
        public boolean tryAdvance(Consumer<? super V> consumer) {
            if (consumer != null) {
                TreeMapEntry treeMapEntry;
                if (this.est < 0) {
                    this.getEstimate();
                }
                if ((treeMapEntry = this.current) != null && treeMapEntry != this.fence) {
                    this.current = TreeMap.successor(treeMapEntry);
                    consumer.accept(treeMapEntry.value);
                    if (this.tree.modCount == this.expectedModCount) {
                        return true;
                    }
                    throw new ConcurrentModificationException();
                }
                return false;
            }
            throw new NullPointerException();
        }

        public ValueSpliterator<K, V> trySplit() {
            if (this.est < 0) {
                this.getEstimate();
            }
            int n = this.side;
            TreeMapEntry treeMapEntry = this.current;
            Object object = this.fence;
            TreeMapEntry treeMapEntry2 = treeMapEntry != null && treeMapEntry != object ? (n == 0 ? this.tree.root : (n > 0 ? treeMapEntry.right : (n < 0 && object != null ? ((TreeMapEntry)object).left : null))) : null;
            if (treeMapEntry2 != null && treeMapEntry2 != treeMapEntry && treeMapEntry2 != object && this.tree.compare(treeMapEntry.key, treeMapEntry2.key) < 0) {
                this.side = 1;
                object = this.tree;
                this.current = treeMapEntry2;
                this.est = n = this.est >>> 1;
                return new ValueSpliterator((TreeMap<K, V>)object, treeMapEntry, treeMapEntry2, -1, n, this.expectedModCount);
            }
            return null;
        }
    }

    class Values
    extends AbstractCollection<V> {
        Values() {
        }

        @Override
        public void clear() {
            TreeMap.this.clear();
        }

        @Override
        public boolean contains(Object object) {
            return TreeMap.this.containsValue(object);
        }

        @Override
        public Iterator<V> iterator() {
            TreeMap treeMap = TreeMap.this;
            return treeMap.new ValueIterator(treeMap.getFirstEntry());
        }

        @Override
        public boolean remove(Object object) {
            TreeMapEntry treeMapEntry = TreeMap.this.getFirstEntry();
            while (treeMapEntry != null) {
                if (TreeMap.valEquals(treeMapEntry.getValue(), object)) {
                    TreeMap.this.deleteEntry(treeMapEntry);
                    return true;
                }
                treeMapEntry = TreeMap.successor(treeMapEntry);
            }
            return false;
        }

        @Override
        public int size() {
            return TreeMap.this.size();
        }

        @Override
        public Spliterator<V> spliterator() {
            return new ValueSpliterator(TreeMap.this, null, null, 0, -1, 0);
        }
    }

}

